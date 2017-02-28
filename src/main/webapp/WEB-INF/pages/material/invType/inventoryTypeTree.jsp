<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<SCRIPT type="text/javascript">
	var invTypeTreeObj;
	jQuery(document).ready(function() { 
		var invTypeFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#inventoryType_container").css("height",invTypeFullSize);
		$('#inventoryType_container').layout({ 
			applyDefaultStyles: false ,
			west__size : 250,
			spacing_open:5,//边框的间隙  
			spacing_closed:5,//关闭时边框的间隙 
			resizable :true,
			resizerClass :"ui-layout-resizer-blue",
			slidable :true,
			resizerDragOpacity :1, 
			resizerTip:"可调整大小"//鼠标移到边框时，提示语
			
		});
		initInventoryTypeTree();
		var orgForInvType = "${fns:userContextParam('orgCode')}";
		var copyForInvType = "${fns:userContextParam('copyCode')}";
	 	/* jQuery("#invTypeName_search").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT  InvtypeId id,Invtype name,parent_id parent FROM mm_invType where parent_id is not null and orgCode='"+orgForInvType+"' and copyCode='"+copyForInvType+"'",
			exceptnullparent:false,
			lazy:false
		});  */
	 	
	 	jQuery("#costItemName_search").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT costItemId id,costItem as name,parentId parent FROM t_costitem",
			exceptnullparent:false,
			lazy:false
		});
	});
	function initInventoryTypeTree(){
		$.get("makeInventoryTypeTree",{ "_": $.now() },function(data) {
			var invTypeTreeData = data.inventoryTypeTreeList;
			invTypeTreeObj = $.fn.zTree.init($("#inventoryTypeTree"), ztreesetting_inventoryType,invTypeTreeData);
			invTypeTreeObj.expandAll(true);
			var nodes = invTypeTreeObj.transformToArray(invTypeTreeObj.getNodes());
			for(var i=0,len=nodes.length;i<len;i++){
				if(!nodes[i].isParent){
					invTypeTreeObj.selectNode(nodes[i]);
					var url = "inventoryTypeForm?popup=true&id="+nodes[i].id;
					url = encodeURI(url);
					showInvTypeInfo(url);
					break;
				}
			}
		});
	}
	
	var ztreesetting_inventoryType = {
			async: {
				enable: true,
				type:"get",
				url: "refreshInvTypeNode",
				autoParam: ["id"],
				dataFilter: ajaxDataFilter
			},
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : true
			},
			edit : {
				enable : true,
				showRemoveBtn : false,
				showRenameBtn : false
			},
			callback : {
				beforeDrag:zTreeBeforeDrag,
				onClick:reloadInvTypeForm
			},
			data : {
				key : {
					name : "invTypeNameWithCode"
				},
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parent"
				}
			}
			
	};
	
	function zTreeBeforeDrag(treeId, treeNodes) {
	    return false;
	};

	function ajaxDataFilter(treeId, parentNode, responseData) {
	 	try{
	   		 return responseData.parentAndChildList;
		}catch(e){
		}
	}
	
	function reloadInvTypeForm(e, treeId, treeNode){
		var url = "inventoryTypeForm?popup=true";
		var parentId =treeNode.id;
		url += "&id="+parentId;
		if(parentId==null ){
			alertMsg.error("请选择类别！");
			return;
		}
		if(treeNode.level==0){
			return;
		}
		url = encodeURI(url);
		showInvTypeInfo(url);
		
	}
	
	function beforeDoClick(type){
		var nodes = invTypeTreeObj.getSelectedNodes();
		if(nodes.length==0){
			if(type=="add" || type=="save"){
				return invTypeTreeObj.getNodes()[0];
			}
			alertMsg.error("请选择类别");
			return;
		}
		return nodes[0];
	}
	
	function delInvType(){
		var node = beforeDoClick();
		if(node.level==0){
			alertMsg.error("根节点不能删除");
			return;
		}
		if(node.leaf==false){
			alertMsg.error("当前节点非末级，不能删除！");
			return;
		}
		var asyncnode = node.getParentNode();
		if(node.getPreNode()==null && node.getNextNode()==null){
			//alertMsg.info("最后一个节点");
			if(asyncnode.level!=0){
				asyncnode = asyncnode.getParentNode();
			}
		}
		//alert(asyncnode.invTypeName);
		var url = "delInventoryType?id="+node.id
		url = encodeURI(url);
		alertMsg.confirm("确认删除节点:"+node.invTypeName+"？", {
			okCall: function(){
				$.get(url,function(data) {
					if(data.statusCode==200){
						invTypeTreeObj.reAsyncChildNodes(asyncnode, "refresh");
						jQuery("#inventoryTypeDetail").html("");
					}
					formCallBack(data);
				},"json");
			}
		});
	}
	
	function showInvTypeInfo(url){
		jQuery('#inventoryTypeDetail').load(url, function(response, status, xhr) {
		  	if (status == "error") {
			 	 var msg = "Sorry but there was an error: ";
			 	 promptMsg(status, msg + xhr.status + " " + xhr.statusText)
			}else{//initTree();
			}
		}); 
	}
	/*
	 *保存之后的回调函数
	*/
	function addOrUpdateInvType(data){
		var inv = data.addInvType;
		var node = invTypeTreeObj.getNodeByParam("id",inv['parentNode']['id'],null)
		if(node.children){
			invTypeTreeObj.reAsyncChildNodes(node, "refresh");
		}else{
			invTypeTreeObj.reAsyncChildNodes(node, null);
		}
		
		var url = "inventoryTypeForm?popup=true&id="+inv['id'];
		url = encodeURI(url);
		showInvTypeInfo(url);
		//formCallBack(data);
	}
	
	function addInvType(){
		var node = beforeDoClick("add");
		node = node.isParent?node:node.getParentNode();
		var url = "inventoryTypeForm?popup=true&entityIsNew=${entityIsNew}&parentId="+node.id;
		url = encodeURI(url);
		showInvTypeInfo(url);
	}
	
	function saveInvType(){
		var entityIsNew = jQuery("#currentInvTypeEntityState").val();
		var parentId = jQuery("#inventoryType_parentNode_id").val();
		//alert("parentId:"+parentId);
		var node = beforeDoClick("save");
		//修改的时候，如果修改了末级，才需要做这个判断
		var leaff = jQuery("#inventoryType_leaf").attr("checked");
		if(entityIsNew=="false" && leaff=="checked"){
			var hasChild = false;
			jQuery.ajax({
				url:'hasInvTypeChildren?id='+node.id,
				type:'get',
				dataType:'json',
				async:false,
				success:function(data){
					if(data.hasInvTypeChildren){
						alertMsg.error("当前类别下有子级，不能将末级标志置为末级");
						hasChild = true;
					}
				}
			});	
			if(hasChild) return;
		}
		jQuery("#inventoryTypeForm").attr("action","saveInventoryType?popup=true&entityIsNew="+entityIsNew+"&id="+node.id+"&parentId="+parentId);
		jQuery("#inventoryTypeForm").submit();
	}
	
	
	function rebuildTree(){
		$.get("reBuildTree",function(data) {
			initInventoryTypeTree();
		});
	}
	
	function changeButton(){
		var isFold = jQuery("#foldInvTypeTree").children("input").val();
		if(isFold=='fold'){
			jQuery("#foldInvTypeTree").children("span").html("折叠树");
			jQuery("#foldInvTypeTree").removeClass().addClass("unfoldbutton");
			jQuery("#foldInvTypeTree").children("input").val("unfold");
			invTypeTreeObj.expandAll(true);
		}
		if(isFold=='unfold'){
			jQuery("#foldInvTypeTree").removeClass().addClass("foldbutton");
			jQuery("#foldInvTypeTree").children().html("展开树");
			jQuery("#foldInvTypeTree").children("input").val("fold");
			invTypeTreeObj.expandAll(false);
		}
	}
	var currentSearchNodes;
	function searchInvType(){
		jQuery("#inventoryTypeDetail").html("");
		invTypeTreeObj.cancelSelectedNode();
		
		var searchNodes = invTypeTreeObj.getNodesByFilter(invTypeSearchFilter); 
		currentSearchNodes = searchNodes;
		//alert(searchNodes.length);
		if(searchNodes.length>0){
			var node;
			for(var i=0,length = searchNodes.length;i<length;i++){
				node = searchNodes[i];
				invTypeTreeObj.selectNode(node,true);
				invTypeTreeObj.expandNode(node.getParentNode(),true,false,true,false);
			}
			var url = "inventoryTypeForm?popup=true&entityIsNew=${entityIsNew}&id="+searchNodes[0].id;
			url = encodeURI(url);
			showInvTypeInfo(url);
		}
	}
	
	function invTypeSearchFilter(node){
		var treeName = node.invTypeName;
		var code = jQuery("#invTypeCode_search").val();
		var name = jQuery("#invTypeName_search").val();
		var costItem = jQuery("#costItemName_search_id").val();
		if(""!=code.trim() && ""!==name.trim() && ""!=costItem.trim()){
			return (node.invTypeCode==code && treeName.indexOf(name)>-1 && node.costItem!=null && node.costItem.costItemId==costItem);
		}
		
		if(""!=code.trim() && ""!==name.trim()){
			return (node.invTypeCode==code && treeName.indexOf(name)>-1);
		}
		if(""!==name.trim() && ""!=costItem.trim()){
			return (treeName.indexOf(name)>-1 && node.costItem!=null && node.costItem.costItemId==costItem);
		}
		if(""!=code.trim() && ""!=costItem.trim()){
			return (node.invTypeCode==code && node.costItem!=null && node.costItem.costItemId==costItem);
		}
		
		if(""!=code.trim()){
			return (node.invTypeCode==code);
		}
		if(""!==name.trim()){
			return (treeName.indexOf(name)>-1);
		}
		if(""!=costItem.trim()){
			return (node.costItem!=null && node.costItem.costItemId==costItem);
		}
		
		return (node.id !=null);
	}
	
	
</SCRIPT>
<style type="text/css">

.ztree li span.button {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle;float:none}
.selectButton{color: green;background-color: blue;}
</style>
</HEAD>

<BODY>
	<div class="page">
		<div id="inventoryType_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="inventoryType_search_form" >
					
					<label style="float:none;white-space:nowrap" >
						<s:text name='inventoryType.invTypeCode'/>:
						<input id="invTypeCode_search" type="text" name="filter_EQS_invTypeCode"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='inventoryType.invTypeName'/>:
						<input id="invTypeName_search" type="text" name="filter_LIKES_invTypeName"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='inventoryType.costItem.costItemName'/>:
						<input id="costItemName_search_id" type="hidden" name="filter_LIKES_costItem.costItemId"/>
						<input id="costItemName_search" type="text" name="filter_exclude_costItem.costItemName" style="width:150px"/>
					</label>&nbsp;&nbsp;
				</form>
					<%-- <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearchInAccountList('','')"><s:text name='button.search'/></button>
						</div>
					</div> --%>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="searchInvType()"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
						<%-- <li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="highSearch('#account_gridtable','Account')"><s:text name='button.highsearch'/></button>
								</div>
							</div>
						</li> --%>
					</ul>
				</div>
			</div>
	</div>
		<div class="pageContent">
			<div class="panelBar">
			<ul class="toolBar">
				<!-- <li>
					<a id="" class="particularbutton" onclick="rebuildTree()" ><span>重建树</span></a>
				</li> -->
				<!-- <li>
					<a id="foldInvTypeTree" class="unfoldbutton" onclick="changeButton()">
						<span>折叠树</span>
						<input type="hidden" value="unfold"/>
					</a>
				</li> -->
				<li>
					<a id="" class="addbutton" onclick="addInvType()" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a class="delbutton" onclick="delInvType()" ><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="" class="savebutton"  onclick="saveInvType()"><span><s:text name="button.save" /></span></a>
				</li>
				
			</ul>
			</div>
			<div id="inventoryType_container" >
				<div id="inventoryType_layout-center" class="pane ui-layout-center"
					style="padding: 2px">
					<div id="inventoryTypeDetail">
					</div>
				</div>
				
				<div id="inventoryType_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
					<div id="inventoryTypeTree" class="ztree">
					</div>
				</div>
			</div>
		</div>
	</div>
</BODY>
</HTML>


