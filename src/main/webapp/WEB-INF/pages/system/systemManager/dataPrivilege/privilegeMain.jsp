<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script>
	jQuery(document).ready(function() {
		var hrOrgFullSize ;
		var dataPrivilegeType='${dataPrivilegeType}';
		if(dataPrivilegeType=='1'){
			hrOrgFullSize = jQuery("#roleTabsContent").innerHeight();
			jQuery("#${random}dataPrivilege_container").attr("layoutcontainer","roleTabsContent");
		}else{
			hrOrgFullSize = jQuery("#userTabsContent").innerHeight();
			jQuery("#${random}dataPrivilege_container").attr("layoutcontainer","userTabsContent");
		}
		var jLayout = setLeftTreeLayout('${random}dataPrivilege_container','${random}dataPrivilegeItem_gridtable',hrOrgFullSize);
		jQuery("#${random}dataPrivilege_container").data("layoutObj",jLayout);
		
		var ztreeSetting_dataPrivilegeClass = {
				view : {
					dblClickExpand : false,
					showLine : true,
					selectedMulti : false
				},
				callback : {
					beforeDrag:function(){return false},
					onClick : function(event, treeId, treeNode, clickFlag){
						var nodeId = treeNode.id;
						var dataPrivilegeItemUrl = "";
						if(nodeId=="-1"){
							dataPrivilegeItemUrl = "dataPrivilegeItemCard?random=${random}&selectedId=${selectedId}&dataPrivilegeType=${dataPrivilegeType}";
						}else{
							dataPrivilegeItemUrl = "dataPrivilegeItemList?random=${random}&selectedId=${selectedId}&dataPrivilegeType=${dataPrivilegeType}&classCode="+nodeId;
						}
						jQuery("#${random}dataPrivilege_layout-center").loadUrl(dataPrivilegeItemUrl);
					}
				},
				data : {
					key : {
						name : "name"
					},
					simpleData : {
						enable : true,
						idKey : "id",
						pIdKey : "pId"
					}
				}
			};
		$.get("makeDataPrivilegeClassTree", function(data) {
			var dataPrivilegeClassTreeData = data.dataPrivilegeClassNodes;
			var dataPrivilegeClassTree = $.fn.zTree.init($("#${random}dataPrivilegeTree"),ztreeSetting_dataPrivilegeClass, dataPrivilegeClassTreeData);
			var nodes = dataPrivilegeClassTree.getNodes();
			dataPrivilegeClassTree.expandNode(nodes[0], true, false, true);
			dataPrivilegeClassTree.selectNode(nodes[0]);
		});
		jQuery("#${random}dataPrivilege_layout-center").loadUrl("dataPrivilegeItemCard?random=${random}&selectedId=${selectedId}&dataPrivilegeType=${dataPrivilegeType}");
	});
	
	function loadDataPrivilegeItem(classCode){
		
	}
</script>
	
<div class="page">
<%-- 	<div id="dataPrivilege_pageHeader" class="pageHeader">
	<div class="searchBar">
		<div class="searchContent">
			<form id="dataPrivilege_search_form" style="white-space: break-all;word-wrap:break-word;" >
				<label style="float:none;white-space:nowrap" >
					<s:text name='hrOrgSnap.orgCode'/>:
					<input type="text" name="filter_LIKES_orgCode"/>
				</label>
				<label style="float:none;white-space:nowrap" >
					<s:text name='hrOrgSnap.orgname'/>:
					<input type="text" name="filter_LIKES_orgname"/>
				</label>
				 <div class="buttonActive" style="float:right">
					<div class="buttonContent">
						<button type="button" onclick=""><s:text name='button.search'/></button>
					</div>
				</div>
			</form>				
		</div>
	</div> 
	</div>--%>
	<div class="pageContent">
		<!-- <div id="dataPrivilege_buttonBar" class="panelBar">
		</div> -->
		<div id="${random}dataPrivilege_container">
			<div id="${random}dataPrivilege_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div id="${random}dataPrivilegeTree" class="ztree"></div>
			</div>
			<div id="${random}dataPrivilege_layout-center" class="pane ui-layout-center">
				
			</div>
		</div>
	</div>
</div>




