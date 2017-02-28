<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	var vendorTypeTreeObj;
	jQuery(document).ready(function() {
		var vendorTypeFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#vendorType_container").css("height",vendorTypeFullSize);
		$('#vendorType_container').layout({ 
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
		$.get("makeVendorTypeTree",{ "_": $.now() },function(data) {
			var vendorTypeTreeData = data.vendorTypeTreeList;
			vendorTypeTreeObj = $.fn.zTree.init($("#vendorTypeTree"), ztreesetting_vendorType,vendorTypeTreeData);
			vendorTypeTreeObj.expandAll(true);
			var nodes = vendorTypeTreeObj.transformToArray(vendorTypeTreeObj.getNodes());
			for(var i=0,len=nodes.length;i<len;i++){
				if(!nodes[i].isParent){
					vendorTypeTreeObj.selectNode(nodes[i]);
					var url = "vendorTypeForm?popup=true&id="+nodes[i].id;
					url = encodeURI(url);
					showVendorTypeInfo(url);
					break;
				}
			}

		});
		var orgForVendorType = "${fns:userContextParam('orgCode')}";
		if(!orgForVendorType){
			orgForVendorType = "${fns:userContextParam('loginOrgId')}";
		}
		jQuery("#vendorTypeName_search").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT  vendorTypeId id,vendorTypeName name,parent_id parent FROM sy_vendorType where parent_id is not null and orgCode='"+orgForVendorType+"'",
			exceptnullparent:false,
			lazy:false
		});
	});
	
	var ztreesetting_vendorType = {
			async: {
				enable: true,
				type:"get",
				url: "refreshVendorTypeNode",
				autoParam: ["id"],
				dataFilter: function(treeId, parentNode, responseData) {
				 	try{
				   		 return responseData.parentAndChildList;
					}catch(e){
					}
				}
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
				onClick:reloadVendorTypeForm
			},
			data : {
				key : {
					name : "vendorTypeNameWithCode"
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
	
	function reloadVendorTypeForm(e, treeId, treeNode){
		var url = "vendorTypeForm?popup=true";
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
		showVendorTypeInfo(url);
	}
	
	function showVendorTypeInfo(url){
		jQuery('#vendorTypeDetail').load(url, function(response, status, xhr) {
		  	if (status == "error") {
			 	 var msg = "Sorry but there was an error: ";
			 	 promptMsg(status, msg + xhr.status + " " + xhr.statusText)
			}else{//initTree();
			}
		}); 
	}
	
	function changeFoldButton(){
		var isFold = jQuery("#foldVendorTypeTree").children("input").val();
		if(isFold=='fold'){
			jQuery("#foldVendorTypeTree").children("span").html("折叠树");
			jQuery("#foldVendorTypeTree").removeClass().addClass("unfoldbutton");
			jQuery("#foldVendorTypeTree").children("input").val("unfold");
			vendorTypeTreeObj.expandAll(true);
		}
		if(isFold=='unfold'){
			jQuery("#foldVendorTypeTree").removeClass().addClass("foldbutton");
			jQuery("#foldVendorTypeTree").children().html("展开树");
			jQuery("#foldVendorTypeTree").children("input").val("fold");
			vendorTypeTreeObj.expandAll(false);
		}
	}
	function beforeDoClickInVendorType(type){
		var nodes = vendorTypeTreeObj.getSelectedNodes();
		if(nodes.length==0){
			if(type=="add" || type=="save"){
				return vendorTypeTreeObj.getNodes()[0];
			}
			alertMsg.error("请选择类别");
			return;
		}
		return nodes[0];
	}
	function addVendorType(){
		var node = beforeDoClickInVendorType("add");
		node = node.isParent?node:node.getParentNode();
		var url = "vendorTypeForm?popup=true&entityIsNew=${entityIsNew}&parentId="+node.id;
		url = encodeURI(url);
		showVendorTypeInfo(url);
	}
	function delVendorType(){
		var node = beforeDoClickInVendorType();
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
			if(asyncnode.level!=0){
				asyncnode = asyncnode.getParentNode();
			}
		}
		var url = "delVendorType?id="+node.id
		url = encodeURI(url);
		alertMsg.confirm("确认删除节点:"+node.vendorTypeName+"？", {
			okCall: function(){
				$.get(url,function(data) {
					if(data.statusCode==200){
						vendorTypeTreeObj.reAsyncChildNodes(asyncnode, "refresh");
						jQuery("#vendorTypeDetail").html("");
					}
					formCallBack(data);
				},"json");
			}
		});
	}
	function addOrUpdateVendorType(data){
		var vendor = data.addVendorType;
		var node = vendorTypeTreeObj.getNodeByParam("id",vendor['parentNode']['id'],null)
		if(node.children){
			vendorTypeTreeObj.reAsyncChildNodes(node, "refresh");
		}else{
			vendorTypeTreeObj.reAsyncChildNodes(node, null);
		}
		
		var url = "vendorTypeForm?popup=true&id="+vendor['id'];
		url = encodeURI(url);
		showVendorTypeInfo(url);
		formCallBack(data);
	}
	function saveVendorType(){
		var entityIsNew = jQuery("#currentVendorTypeEntityState").val();
		var parentId = jQuery("#vendorType_parentNode_id").val();
		var node = beforeDoClickInVendorType("save");
		//修改的时候，如果修改了末级，才需要做这个判断
		var leaff = jQuery("#vendorType_leaf").attr("checked");
		if(entityIsNew=="false" && leaff=="checked"){
			var hasChild = false;
			jQuery.ajax({
				url:'hasVendorTypeChildren?id='+node.id,
				type:'get',
				dataType:'json',
				async:false,
				success:function(data){
					if(data.hasVendorTypeChildren){
						alertMsg.error("当前类别下有子级，不能将末级标志置为末级");
						hasChild = true;
					}
				}
			});	
			if(hasChild) return;
		}
		jQuery("#vendorTypeForm").attr("action","saveVendorType?popup=true&entityIsNew="+entityIsNew+"&id="+node.id+"&parentId="+parentId);
		jQuery("#vendorTypeForm").submit();
	}
	function isNotNull(value){
		if(""==value.trim()){
			return false;
		}
		return true;
	}
	function searchVendorType(){
		var vendorTypeCode = jQuery("#vendorTypeCode_search").val();
		var vendorTypeName = jQuery("#vendorTypeName_search").val();
		var disabled = jQuery("#vendorType_disabled_search").val();
		var url = "getSearchVendorTypes?1=1";
		if(isNotNull(vendorTypeCode)){
			url += "&vendorTypeCode="+vendorTypeCode;
		}
		if(isNotNull(vendorTypeName)){
			url += "&vendorTypeName="+vendorTypeName;
		}
		if(isNotNull(disabled)){
			url += "&disabled="+disabled;
		}
		vendorTypeTreeObj.cancelSelectedNode();
		var nodes = vendorTypeTreeObj.transformToArray(vendorTypeTreeObj.getNodes())
		url = encodeURI(url);
		$.get(url,function(data) {
			//alert(data.searchStoreList.length);
			if(data.searchVendorTypeList.length>0){
				var vendorTypes = data.searchVendorTypeList;
				for(var i=0;i<vendorTypes.length;i++){
					var vendorTypeId = vendorTypes[i]['id'];
					for(var j=0;j<nodes.length;j++){
						if(vendorTypeId==nodes[j].id){
							//alert(nodes[j].id);
							vendorTypeTreeObj.selectNode(nodes[j],true);
							vendorTypeTreeObj.expandNode(nodes[j].getParentNode(),true,false,true,false);
						}
					}
				}
				var urll = "vendorTypeForm?popup=true&entityIsNew=${entityIsNew}&id="+vendorTypes[0]['id'];
				urll = encodeURI(urll);
				showVendorTypeInfo(urll);
			}
		});
	}
</script>
</head>

<div class="page">
	<div id="vendorType_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="vendorType_search_form" >
					
					<label style="float:none;white-space:nowrap" >
						<s:text name='vendorType.vendorTypeCode'/>:
						<input id="vendorTypeCode_search" type="text" name=""/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='vendorType.vendorTypeName'/>:
						<input id="vendorTypeName_search" type="text" name="" style="width:150px"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='vendorType.disabled'/>:
						<s:select id="vendorType_disabled_search" list="#{'':'--','1':'是','0':'否'}" name="" style="width:60px"></s:select>
					</label>&nbsp;&nbsp;
				</form>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="searchVendorType()"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<!-- <li>
					<a id="foldVendorTypeTree" class="unfoldbutton" onclick="changeFoldButton()">
						<span>折叠树</span>
						<input type="hidden" value="unfold"/>
					</a>
				</li> -->
				<li>
					<a id="" class="addbutton" onclick="addVendorType()" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a class="delbutton" onclick="delVendorType()" ><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="" class="savebutton"  onclick="saveVendorType()"><span><s:text name="button.save" /></span></a>
				</li>
				
			</ul>
		</div>
		<div id="vendorType_container" >
				<div id="vendorType_layout-center" class="pane ui-layout-center"
					style="padding: 2px">
					<div id="vendorTypeDetail">
					</div>
				</div>
				<div id="vendorType_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
					<div id="vendorTypeTree" class="ztree">
					</div>
				</div>
		</div>
	</div>
</div>





