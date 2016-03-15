<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	jQuery(function() {
		
		setLeftTreeLayout('autoPzPage_container',
				'autoPzPage_gridtable', (jQuery("#container").innerHeight() - 29));
		makeAutoPzPageLeftTree();
	});

	function makeAutoPzPageLeftTree() {
		var url = "makeBusinessTypeTree";
		$.get(url, {
			"_" : $.now()
		}, function(data) {
			var businessAccountTreeData = data.businessTypeTreeNodes;
			var businessAccountTree = $.fn.zTree.init(
					$("#autoPzPageLeftTree"),
					ztreesetting_autoPzPageTree, businessAccountTreeData);
			var nodes = businessAccountTree.getNodes();
			businessAccountTree.expandNode(nodes[0], true, false, true);
			businessAccountTree.selectNode(nodes[0]);
		});
		jQuery("#autoPzPage_expandTree").text("展开");
	}
	var ztreesetting_autoPzPageTree = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false
		},
		callback : {
			beforeDrag : function() {
				return false
			},
			onClick : function(event, treeId, treeNode, clickFlag) {
				var treeId = treeNode.id;
				var searchName = treeNode.searchName;
				var url = "query?searchName="+searchName+"&businessId="+treeId;
				jQuery("#autoPzPage").loadUrl(url);
				/* var urlString = "businessAccountGridList?1=1";
				var nodeId = treeNode.id;
				if(nodeId!="-1"){
					urlString += "&businessId="+nodeId;
				}
				urlString=encodeURI(urlString);
				jQuery("#businessAccount_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); */
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
	
	/* function businessAccountTabReload(businessId) {
		jQuery("#autoPzPage_tabs").find("li").each(function() {
			
		});
	} */
	
	function toggleExpandAutoPzPageTree(obj,treeId){
		var zTree = $.fn.zTree.getZTreeObj(treeId); 
		var obj = jQuery(obj);
		var text = obj.html();
		if(text=='展开'){
			obj.html("折叠");
			zTree.expandAll(true);
		}else{
			obj.html("展开");
			var allNodes = zTree.transformToArray(zTree.getNodes());
			for(var nodeIndex in allNodes){
				var node = allNodes[nodeIndex];
				if(node.subSysTem=='TYPE'){
					zTree.expandNode(node,false,true,true);
				}
			}
		}
	}
</script>
<div class="page">
	<div class="pageContent" >
		<div class="panelBar" id="autoPzPage_buttonBar">
			<ul class="toolBar">
				<li><a id="autoPzPage_gridtable_add"
					class="addbutton" href="javaScript:"><span><fmt:message
								key="button.add" /> </span> </a></li>
				<li><a id="autoPzPage_gridtable_del"
					class="delbutton" href="javaScript:"><span><s:text
								name="button.delete" /></span> </a></li>
				<li><a id="autoPzPage_gridtable_edit"
					class="changebutton" href="javaScript:"><span><s:text
								name="button.edit" /> </span> </a></li>

			</ul>
		</div>
		<div id="autoPzPage_container">
			<div id="autoPzPage_layout-west"
				class="pane ui-layout-west"
				style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
					<label id="autoPzPage_expandTree"
						onclick="toggleExpandAutoPzPageTree(this,'autoPzPageLeftTree')">展开</label>
				</div>
				<div id="autoPzPageLeftTree" class="ztree"></div>
			</div>
			<div id="autoPzPage_layout-center"
				class="pane ui-layout-center">
				<div id="autoPzPage" class="pageContent">
					<%-- <div class="tabs" id="autoPzPage_tabs" currentIndex="0" eventType="click" tabcontainer="autoPzPage_layout-center" extraHeight="0" extraWidth="0">
						<div class="tabsHeader">
							<div class="tabsHeaderContent">
								<ul>
									<li><a href="businessAccountMap?type=J&random=${random}"
										class="j-ajax"><span><s:text
													name="businessTypeJList.title" /></span></a></li>
									<li><a href="businessAccountList?popup=true&type=D"
										class="j-ajax"><span><s:text
													name="businessTypeDList.title" /></span></a></li>
									<li><a href="empty.jsp" class="j-ajax"><span><s:text
													name="businessTypeParamList.title" /></span></a></li>
								</ul>
							</div>
						</div>
						<div id="autoPzPage_tab" class="tabsContent" style="padding:0px;" >
							<div></div>
							<div></div>
							<div></div>
						</div>
					</div>
					<script>
					tabResize();
					</script> --%>
				</div>
			</div>
		</div>
	</div>
</div>