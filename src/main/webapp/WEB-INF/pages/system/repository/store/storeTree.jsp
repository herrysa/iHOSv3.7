<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<SCRIPT type="text/javascript">
	var storeTreeObj;
	jQuery(document).ready(function() {
		var storeFullSize = jQuery("#container").innerHeight()-145;
		jQuery("#store_container").css("height",storeFullSize);
		$('#store_container').layout({ 
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
		initStoreLeftTree();
		var orgForStore = "${fns:userContextParam('orgCode')}";
		jQuery("#storeName_search").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT  storeId id,storeName name,parent_id parent FROM sy_store where parent_id is not null and orgCode='"+orgForStore+"'",
			exceptnullparent:false,
			lazy:false
		});
	});
	
	function initStoreLeftTree() {
		$.get("makeStoreTree", {
			"_" : $.now()
		}, function(data) {
			var storeTreeData = data.storeTree;
			storeTreeObj = $.fn.zTree.init($("#storeTreeLeft"),
					ztreesetting_storeLeft, storeTreeData);
			storeTreeObj.expandAll(true);
			var nodes = storeTreeObj.transformToArray(storeTreeObj.getNodes());
			if(nodes.length>=2){
				storeTreeObj.selectNode(nodes[1]);
				var url = "storeForm?popup=true&id="+nodes[1].id;
				url = encodeURI(url);
				showStoreInfo(url);
			}
		});
	}
	function rebuildTree(){
		$.get("reBuildTree",function(data) {
			initStoreLeftTree();
		});
	}
	var ztreesetting_storeLeft = {
			async: {
				enable: true,
				type:"get",
				url: "refreshStoreNode",
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
				onClick:reloadStoreForm
			},
			data : {
				key : {
					name : "storeNameWithCode"
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
	function reloadStoreForm(e, treeId, treeNode){
		var url = "storeForm?popup=true";
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
		showStoreInfo(url);
	}
	function showStoreInfo(url){
		jQuery('#storeDetail').load(url, function(response, status, xhr) {
		  	if (status == "error") {
			 	 var msg = "Sorry but there was an error: ";
			 	 promptMsg(status, msg + xhr.status + " " + xhr.statusText)
			}else{//initTree();
			}
		}); 
	}
	function changeButton(){
		var isFold = jQuery("#foldStoreTree").children("input").val();
		if(isFold=='fold'){
			jQuery("#foldStoreTree").children("span").html("折叠树");
			jQuery("#foldStoreTree").removeClass().addClass("unfoldbutton");
			jQuery("#foldStoreTree").children("input").val("unfold");
			storeTreeObj.expandAll(true);
		}
		if(isFold=='unfold'){
			jQuery("#foldStoreTree").removeClass().addClass("foldbutton");
			jQuery("#foldStoreTree").children().html("展开树");
			jQuery("#foldStoreTree").children("input").val("fold");
			storeTreeObj.expandAll(false);
		}
	}
	function beforeDoClick(type){
		var nodes = storeTreeObj.getSelectedNodes();
		if(nodes.length==0){
			if(type=="add" || type=="save"){
				return storeTreeObj.getNodes()[0];
			}
			alertMsg.error("请选择仓库");
			return;
		}
		return nodes[0];
	}
	function addStore(){
		var node = beforeDoClick("add");
		node = node.isParent?node:node.getParentNode();
		var url = "storeForm?popup=true&entityIsNew=${entityIsNew}&parentId="+node.id;
		url = encodeURI(url);
		showStoreInfo(url);
	}
	function delStore(){
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
			if(asyncnode.level!=0){
				asyncnode = asyncnode.getParentNode();
			}
		}
		var url = "delStore?id="+node.id
		url = encodeURI(url);
		alertMsg.confirm("确认删除节点:"+node.storeName+"？", {
			okCall: function(){
				$.get(url,function(data) {
					if(data.statusCode==200){
						storeTreeObj.reAsyncChildNodes(asyncnode, "refresh");
						jQuery("#storeDetail").html("");
					}
					formCallBack(data);
				},"json");
			}
		});
	}
	function saveStore(){
		var entityIsNew = jQuery("#currentStoreEntityState").val();
		var parentId = jQuery("#store_parentNode_id").val();
		var node = beforeDoClick("save");
		var leaff = jQuery("#store_leaf").attr("checked");
		var is_pos = jQuery("#store_isPos").attr("checked");
		/* if(node.isParent && is_pos!="checked"){
			var hasPos = false;
			jQuery.ajax({
				url:'hasStorePos?id='+node.id,
				type:'get',
				dataType:'json',
				async:false,
				success:function(data){
					if(data.hasStorePos){
						alertMsg.error("当前仓库下子级仓库有货位管理，不能修改父级标志");
						hasPos = true;
					}
				}
			});
			if(hasPos) return;
		} */
		if(entityIsNew=="false" && leaff=="checked"){
			var hasChild = false;
			jQuery.ajax({
				url:'hasStoreChildren?id='+node.id,
				type:'get',
				dataType:'json',
				async:false,
				success:function(data){
					if(data.hasStoreChildren){
						alertMsg.error("当前仓库下有子级，不能将末级标志置为末级");
						hasChild = true;
					}
				}
			});	
			if(hasChild) return;
		}
		jQuery("#storeForm").attr("action","saveStore?popup=true&entityIsNew="+entityIsNew+"&id="+node.id+"&parentId="+parentId);
		jQuery("#storeForm").submit();
	}
	function addOrUpdateStore(data){
		var store = data.addStore;
		var node = storeTreeObj.getNodeByParam("id",store['parentNode']['id'],null);
		if(node.children){
			storeTreeObj.reAsyncChildNodes(node, "refresh");
		}else{
			storeTreeObj.reAsyncChildNodes(node, null);
		}
		var url = "storeForm?popup=true&id="+store['id'];
		url = encodeURI(url);
		showStoreInfo(url);
		formCallBack(data);
	}
	
	function isNotNull(value){
		if(""==value.trim()){
			return false;
		}
		return true;
	}
	function searchInvType(){
		var storeCode = jQuery("#storeCode_search").val();
		var storeName = jQuery("#storeName_search").val();
		var storeType = jQuery("#storeType_search").val();
		var isPos = jQuery("#isPos_search").val();
		var disabled = jQuery("#disabled_search").val();
		var url = "getSearchStores?1=1";
		if(isNotNull(storeCode)){
			url += "&storeCode="+storeCode;
		}
		if(isNotNull(storeName)){
			url += "&storeName="+storeName;
		}
		if(isNotNull(storeType)){
			url += "&storeType="+storeType;
		}
		if(isNotNull(isPos)){
			url += "&isPos="+(isPos=='true'?'1':'0');
		}
		if(isNotNull(disabled)){
			url += "&disabled="+(disabled=='true'?'1':'0');
		}
		storeTreeObj.cancelSelectedNode();
		var nodes = storeTreeObj.transformToArray(storeTreeObj.getNodes())
		url = encodeURI(url);
		$.get(url,function(data) {
			//alert(data.searchStoreList.length);
			if(data.searchStoreList.length>0){
				var stores = data.searchStoreList;
				for(var i=0;i<stores.length;i++){
					var storeId = stores[i]['id'];
					for(var j=0;j<nodes.length;j++){
						if(storeId==nodes[j].id){
							//alert(nodes[j].id);
							storeTreeObj.selectNode(nodes[j],true);
							storeTreeObj.expandNode(nodes[j].getParentNode(),true,false,true,false);
						}
					}
				}
				var urll = "storeForm?popup=true&entityIsNew=${entityIsNew}&id="+stores[0]['id'];
				urll = encodeURI(urll);
				showStoreInfo(urll);
			}
		});
	}
</SCRIPT>
<style type="text/css">
.ztree li span.button {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle;
	float: none
}

.selectButton {
	color: green;
	background-color: blue;
}
</style>
</HEAD>

<BODY>
	<div class="page">
		<div id="store_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="store_search_form" >
					
					<label style="float:none;white-space:nowrap" >
						<s:text name='store.storeCode'/>:
						<input id="storeCode_search" type="text" name="" size="15"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='store.storeName'/>:
						<input id="storeName_search" type="text" name="" size="15"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='store.storeType'/>:
						<s:select id="storeType_search" list="#{'':'--','0':'一般库房','1':'科室库房','2':'其它库房','9':'虚拟库房'}" name="" style="width:100px"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='store.isPos'/>:
						<s:select id="isPos_search" list="#{'':'--','true':'是','false':'否'}" name="" style="width:60px"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='store.disabled'/>:
						<s:select id="disabled_search" list="#{'':'--','true':'是','false':'否'}" name="" style="width:60px"></s:select>
					</label>
				</form>
				
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="searchInvType()"><s:text name='button.search'/></button>
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
					<a id="" class="particularbutton" onclick="rebuildTree()" ><span>重建树</span></a>
				</li> -->
				<!-- <li>
					<a id="foldStoreTree" class="unfoldbutton" onclick="changeButton()">
						<span>折叠树</span>
						<input type="hidden" value="unfold"/>
					</a>
				</li> -->
				<li>
					<a id="" class="addbutton" onclick="addStore()" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a class="delbutton" onclick="delStore()" ><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="" class="savebutton"  onclick="saveStore()"><span><s:text name="button.save" /></span></a>
				</li>
			</ul>
			</div>
			<div id="store_container" >
				<div id="store_layout-center" class="pane ui-layout-center" style="padding: 2px">
					<div id="storeDetail"></div>
				</div>
				
				<div id="store_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
					<div id="storeTreeLeft" class="ztree"></div>
				</div>
			</div>
		</div>
	</div>
</BODY>
</HTML>


