<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		gzContentInheritDeptTree();
	});
	function gzContentInheritDeptTree(){
		var url = "makeDepartmentTree";
		$.get(url, {"_" : $.now()}, function(data) {
			var departmentTreeData = data.deptTreeNodes;
			var departmentTree = $.fn.zTree.init($("#gzContentInheritDeptTree"),ztreesetting_gzContentInheritDeptTree, departmentTreeData);
			var nodes = departmentTree.getNodes();
			departmentTree.expandNode(nodes[0], true, false, true);
// 			departmentTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'gzContentInheritDeptTree',
				showCode:true,
				disabledDept:false,
				disabledPerson:false,
				showCount:false
			});
		});
	}
	var ztreesetting_gzContentInheritDeptTree = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false
			},
			callback : {
				beforeDrag:function(){return false}
			},
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: {"Y":"ps", "N":"ps"}
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
	/*继承上月数据*/
	function gzContentInheritLoad(){
		var inherit = jQuery("#gzContentInherit_inherit").attr('checked') == "checked";
		var year = jQuery("#gzContentInherit_year").val();
		var sn = jQuery("#gzContentInherit_sn").val();
		var fromNoInherit = jQuery("#gzContentInherit_fromNoInherit").attr('checked') == "checked";
		var allItems = jQuery("#gzContentInherit_allItems").attr('checked') == "checked";
		var cover = jQuery("#gzContentInherit_cover").attr('checked') == "checked";
		var refresh = jQuery("#gzContentInherit_refresh").attr('checked') == "checked";
		if(inherit&&(!year||!sn)){
			alertMsg.error('请选择需要继承数据的年份和次数！')
			return;
		}
		var gzContentInheritTreeObj = $.fn.zTree.getZTreeObj("gzContentInheritDeptTree");
		var nodes = gzContentInheritTreeObj.transformToArray(gzContentInheritTreeObj.getNodes());
		var deptIds = "";
		for(index in nodes){
			var node = nodes[index];
			if(node.checked&&node.subSysTem=="DEPT"){
				deptIds += node.id+",";
			}
		}
		if(deptIds){
			deptIds = deptIds.substring(0,deptIds.length-1);
		}else{
			alertMsg.error('请选择需要继承数据的部门！')
			return;
		}
		jQuery.ajax({
			url: 'gzContentInherit',
			data: {inherit:inherit,year:year,sn:sn,fromNoInherit:fromNoInherit,allItems:allItems,
				cover:cover,refresh:refresh,deptIds:deptIds,curPeriod:curPeriod,
				curIssueNumber:curIssueNumber,gzTypeId:gzTypeId,lastPeriod:lastPeriod},
			type: 'post',
			dataType: 'json',
			async:true,
			error: function(data){
				alertMsg.error('系统错误！');
			},
			success: function(data){
				if(data.statusCode == 200){
					alertMsg.correct('继承成功。');
					$.pdialog.closeCurrent();
					gzContentGridTableReLoadData();
					gzContentLeftTree();
				}else{
					alertMsg.error(data.message);
				}
			}
		});
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="gzContentInheritForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div style="width:250px;height:300px;margin: auto;border:1px solid #5bc0de;">
					<div  style="float:left;margin-left:6px;margin-top:6px">
						<s:text name="检索"></s:text>
						<input type="text" style="float:none;width: 150px;" onKeyUp="searchTree('gzContentInheritDeptTree',this)"/>
					</div>
					<div style="float:left;margin-top:6px;overflow:auto;height:266px;">
						<div id="gzContentInheritDeptTree" style="float:left;margin-top:6px;" class="ztree"></div>
					</div>
				</div>
				<div style="width:250px;margin: auto;">
					<div>
						<input type="checkbox" id="gzContentInherit_inherit">继承
						<input type="text" style="float:none;width:50px" id="gzContentInherit_year" onclick = "javascript:{WdatePicker({skin:'ext',dateFmt:'yyyy'});}">年第
						<s:select id="gzContentInherit_sn" headerKey="" style="float:none;"   
								list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'
								,'6':'6','7':'7','8':'8','9':'9','10':'10','11':'11'
								,'12':'12','13':'13'}" listKey="key" listValue="value"
								emptyOption="false"  maxlength="20" width="20px" theme="simple">
							</s:select>次工资数据
					</div>
					<div>
						<input type="checkbox" id="gzContentInherit_fromNoInherit">允许从非继承项继承
					</div>
					<div>
						<input type="checkbox" id="gzContentInherit_allItems">处理所有工资类别
					</div>
					<div>
						<input type="checkbox" id="gzContentInherit_cover">覆盖继承
					</div>
					<div>
						<input type="checkbox" id="gzContentInherit_refresh">更新基本信息(从月职工表)
					</div>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" onclick="gzContentInheritLoad()"><s:text name="继承" /></button>
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





