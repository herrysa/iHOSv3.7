<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	jQuery(function() {
		
		var businessAccountFullSize = jQuery("#container").innerHeight() - 27;
		setLeftTreeLayout('${random}businessAccount_container',
				'${random}businessAccount_gridtable', businessAccountFullSize);
		makebusinessAccountLeftTree();
	});
	function makebusinessAccountLeftTree() {
		var url = "makeBusinessTypeTree";
		$.get(url, {
			"_" : $.now()
		}, function(data) {
			var businessAccountTreeData = data.businessTypeTreeNodes;
			var businessAccountTree = $.fn.zTree.init(
					$("#${random}businessAccountLeftTree"),
					ztreesetting_businessAccountTree, businessAccountTreeData);
			var nodes = businessAccountTree.getNodes();
			businessAccountTree.expandNode(nodes[0], true, false, true);
			businessAccountTree.selectNode(nodes[0]);
		});
		jQuery("#businessAccount_expandTree").text("展开");
	}
	var ztreesetting_businessAccountTree = {
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
				var isParent = treeNode.isParent;
				if(!isParent) {
					var businessId = treeNode.id;
					var url = null;
					var selected;
					jQuery("#${random}businessAccount_tabs").find("li").each(function() {
						var url = jQuery(this).find("a").eq(0).attr("href");
						if(url) {
							var stop = url.indexOf("&businessId=");
							if(stop != -1) {
								url = url.substring(0,stop);
							}
							url += "&businessId="+businessId;
						}
						jQuery(this).find("a").eq(0).attr("href",url);
						if (jQuery(this).attr("class") == "selected") {
							$(this).find("a").eq(0).trigger('click');
						}
						
					});
				}else{
					jQuery("#${random}businessAccount_tab").find("div[class='page']").each(function() {
						jQuery(this).hide();
					});
				}
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
		jQuery("#${random}businessAccount_tabs").find("li").each(function() {
			
		});
	} */
	
	function toggleExpandbusinessAccountTree(obj,treeId){
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
		<%-- <div class="panelBar" id="${random}businessAccount_buttonBar">
			<ul class="toolBar">
				<li><a id="${random}businessAccount_gridtable_add"
					class="addbutton" href="javaScript:"><span><fmt:message
								key="button.add" /> </span> </a></li>
				<li><a id="${random}businessAccount_gridtable_del"
					class="delbutton" href="javaScript:"><span><s:text
								name="button.delete" /></span> </a></li>
				<li><a id="${random}businessAccount_gridtable_edit"
					class="changebutton" href="javaScript:"><span><s:text
								name="button.edit" /> </span> </a></li>

			</ul>
		</div> --%>
		<div id="${random}businessAccount_container">
			<div id="${random}businessAccount_layout-west"
				class="pane ui-layout-west"
				style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
					<label id="${random}businessAccount_expandTree"
						onclick="toggleExpandbusinessAccountTree(this,'${random}businessAccountLeftTree')">展开</label>
				</div>
				<div id="${random}businessAccountLeftTree" class="ztree"></div>
			</div>
			<div id="${random}businessAccount_layout-center"
				class="pane ui-layout-center">
				<div class="pageContent">
					<div class="tabs" id="${random}businessAccount_tabs" currentIndex="0" eventType="click" tabcontainer="${random}businessAccount_layout-center" extraHeight="0" extraWidth="0">
						<div class="tabsHeader">
							<div class="tabsHeaderContent">
								<ul>
									<li><a href="businessAccountMap?type=J&random=${random}"
										class="j-ajax"><span><s:text
													name="businessTypeJList.title" /></span></a></li>
									<li><a href="businessAccountMap?&type=D&random=${random}"
										class="j-ajax"><span><s:text
													name="businessTypeDList.title" /></span></a></li>
									<li><a href="businessAccountParam?type=param&random=${random}" class="j-ajax"><span><s:text
													name="businessTypeParamList.title" /></span></a></li>
								</ul>
							</div>
						</div>
						<div id="${random}businessAccount_tab" class="tabsContent" style="padding:0px;" >
							<div></div>
							<div></div>
							<div></div>
						</div>
					</div>
					<script>
					tabResize();
					</script>
				</div>
			</div>
		</div>
	</div>
</div>