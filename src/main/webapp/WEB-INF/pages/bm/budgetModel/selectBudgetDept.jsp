<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
jQuery(document).ready(function() { 
	var ztreesetting_budgetDept = {
			check : { //为节点添加checkbox
				enable : true,
				chkStyle : "checkbox",
				chkboxType : {
					"Y" : "ps",
					"N" : "ps"
				}
			//checkbox无关联效果
			},
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false
			},
			callback : {
				onClick : null
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
	$.get("getBmDepartmentTree", {
		"_" : $.now()
	}, function(data) {
		var bmDepartmentTreeData = data.bmDepartmentNodes;
		var bmDepartmentTree = $.fn.zTree.init($("#bmDepartmentTree"),
				ztreesetting_budgetDept, bmDepartmentTreeData);
		var nodes = bmDepartmentTree.getNodes();
		//bmDepartmentTree.expandNode(nodes[0], true, false, true);
		//bmDepartmentTree.selectNode(nodes[0]);
		var deptId = "${deptId}";
		var deptIdArr = null;
		deptIdArr = deptId.split(",");
		for(var i in deptIdArr){
			var nodeId = deptIdArr[i];
			if(nodeId){
				var node = bmDepartmentTree.getNodeByParam("id",nodeId,null);
				bmDepartmentTree.checkNode(node, true);
			}
		}
		
	});
	
	jQuery("#bmDepartment_save").click(function(){
		var bmDepartmentTree = $.fn.zTree.getZTreeObj("bmDepartmentTree");
		var nodes = bmDepartmentTree.getCheckedNodes();
		var s = "";
		for ( var i = 0; i < nodes.length; i++) {
			var node = nodes[i];
			if(!node.isParent){
				s += node.id+",";
			}
		}
		$.get("saveBmDepartment", {
			"_" : $.now(),deptId:s,modelId:"${modelId}",navTabId:'bmsDepartment_gridtable'
		}, function(data) {
			formCallBack(data);
		});
	});
});
</script>

<div class="page">
	<div class="pageContent">
		<form id="bmModelDepartmentForm" method="post"	action="saveBmModelDepartment?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="59" style="text-align: center;">
			<a style="position: relative; float: right;top:5px" id="bmDepartment_expandTree" href="javaScript:">展开</a>
			<script>
				jQuery("#bmDepartment_expandTree").click(function(){
					var thisText = jQuery(this).text();
					var bmDepartmentTree = $.fn.zTree.getZTreeObj("bmDepartmentTree");
					if(thisText=="展开"){
						bmDepartmentTree.expandAll(true);
						jQuery(this).text("折叠");
					}else{
						bmDepartmentTree.expandAll(false);
						jQuery(this).text("展开");
					}
				});
			</script>
			<div id="bmDepartmentTree" class="ztree" style="width:300px;margin-left:auto;margin-right:auto;"></div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button id="bmDepartment_save" type="Button"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>