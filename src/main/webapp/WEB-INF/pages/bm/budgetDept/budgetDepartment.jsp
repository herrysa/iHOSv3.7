<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script type="text/javascript">
var bmsDepartmentGridIdString="#bmsDepartment_gridtable";
jQuery(document).ready(function() { 
	var bmsDepartmentGrid = jQuery(bmsDepartmentGridIdString);
	bmsDepartmentGrid.jqGrid({
		url : "bmsDepartmentGridList?1=1&modelId=${modelId}",
		editurl : "",
		datatype : "json",
		mtype : "GET",
		colModel : [
			{name : 'departmentId',index : 'departmentId',align : 'left',width:100,label : '<s:text name="department.departmentId" />',hidden : false,key : true,highsearch:true},
			{name : 'name',index : 'name',align : 'left',width:120,label : '<s:text name="department.name" />',hidden : false, sortable:true,highsearch:true},
			{name : 'shortnName',index : 'shortnName',align : 'left',width:120,label : '<s:text name="department.shortnName" />',hidden : false, sortable:true,highsearch:true},
			{name : 'deptCode',index : 'deptCode',align : 'left',width:100,label : '<s:text name="department.deptCode" />',hidden : false, sortable:true,highsearch:true},
			{name : 'org.orgname',index : 'org.orgname',align : 'left',width:130,label : '<s:text name="department.orgCode" />',hidden : false,highsearch:true},
			{name : 'branch.branchName',index : 'branch.branchName',align : 'left',width:130,label : '<s:text name="department.branchCode" />',hidden : false,highsearch:true},
			{name : 'deptClass',index : 'deptClass',align : 'center',width:70,label : '<s:text name="department.deptClass" />',hidden : false, sortable:true,highsearch:true},
			{name : 'outin',index : 'outin',align : 'left',width:70,label : '<s:text name="department.outin" />',hidden : false, sortable:true,highsearch:true},
			{name : 'dgroup',index : 'dgroup',align : 'left',width:70,label : '<s:text name="department.dgroup" />',hidden : false, sortable:true,highsearch:true},
			{name : 'clevel',index : 'clevel',align : 'center',width:50,label : '<s:text name="department.clevel" />',hidden : false, sortable:true,highsearch:true},
			{name : 'leaf',index : 'leaf',align : 'center',width:50,label : '<s:text name="department.leaf" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
			{name : 'parentDept.name',index : 'parentDept.name',align : 'left',width:100,label : '<s:text name="department.parentDeptName" />',hidden : false, sortable:true,highsearch:true},
			{name : 'subClass',index : 'subClass',align : 'center',width:100,label : '<s:text name="department.subClass" />',hidden : false, sortable:true,highsearch:true},
			{name : 'cnCode',index : 'cnCode',align : 'left',width:100,label : '<s:text name="department.cnCode" />',hidden : false, sortable:true,highsearch:true},
			{name : 'jjDeptType.khDeptTypeName',index : 'jjDeptType.khDeptTypeName',align : 'center',width:100,label : '<s:text name="department.jjDeptType" />',hidden : false, sortable:true,highsearch:true},
			{name : 'internalCode',index : 'internalCode',align : 'left',width:100,label : '<s:text name="department.internalCode" />',hidden : false, sortable:true,highsearch:true},
			{name : 'manager',index : 'manager',align : 'left',width:100,label : '<s:text name="department.manager" />',hidden : false, sortable:true,highsearch:true},
			{name : 'cbLeaf',index : 'cbLeaf',align : 'center',width:50,label : '<s:text name="department.cbLeaf" />',hidden : true, sortable:true,formatter:'checkbox',highsearch:true},
			{name : 'xmLeaf',index : 'xmLeaf',align : 'center',width:50,label : '<s:text name="department.xmLeaf" />',hidden : true, sortable:true,formatter:'checkbox',highsearch:true},
			{name : 'crLeaf',index : 'crLeaf',align : 'center',width:50,label : '<s:text name="department.crLeaf" />',hidden : true, sortable:true,formatter:'checkbox',highsearch:true},
			{name : 'zcLeaf',index : 'zcLeaf',align : 'center',width:50,label : '<s:text name="department.zcLeaf" />',hidden : true, sortable:true,formatter:'checkbox',highsearch:true},
			{name : 'ysDeptName',index : 'ysDeptName',align : 'left',width:100,label : '<s:text name="department.ysName" />',hidden : true, sortable:true,highsearch:true},
			{name : 'ysLeaf',index : 'ysLeaf',align : 'center',width:50,label : '<s:text name="department.ysLeaf" />',hidden : true, sortable:true,formatter:'checkbox',highsearch:true},
			{name : 'jjDeptName',index : 'jjDeptName',align : 'left',width:100,label : '<s:text name="department.jjName" />',hidden : true, sortable:true,highsearch:true},
			{name : 'jjLeaf',index : 'jjLeaf',align : 'center',width:50,label : '<s:text name="department.jjLeaf" />',hidden : true, sortable:true,formatter:'checkbox',highsearch:true},
			{name : 'yjDeptName',index : 'yjDeptName',align : 'left',width:100,label : '<s:text name="department.yjDeptId" />',hidden : true, sortable:true,highsearch:true},
			{name : 'note',index : 'note',align : 'left',width:120,label : '<s:text name="department.note" />',hidden : false, sortable:true,highsearch:true},
			{name : 'disabled',index : 'disabled',align : 'center',width:50,label : '<s:text name="department.disabled" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
			{name : 'orgCode',index : 'orgCode',align : 'center',width:50,label : '<s:text name="department.orgCode" />',hidden : true},
			{name : 'branchCode',index : 'branchCode',align : 'center',width:50,label : '<s:text name="department.branchCode" />',hidden : true},
			{name : 'phone',index : 'phone',align : 'center',width:50,label : '<s:text name="department.phone" />',hidden : true, sortable:true,highsearch:true}
			],
		jsonReader : {
			root : "departments", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		},
		sortname : 'orgCode,deptCode',
		viewrecords : true,
		sortorder : 'asc',
		height : 300,
		gridview : true,
		rownumbers : true,
		loadui : "disable",
		multiselect : true,
		multiboxonly : true,
		shrinkToFit : false,
		autowidth : false,
		onSelectRow : function(rowid) {

		},
		gridComplete : function() {
	 		/*2015.08.27 form search change*/
	 		//gridContainerResize('bmsDepartment','div');
			 var rowNum = jQuery(this).getDataIDs().length;
	           if(rowNum>0){
				}else{
				}
	           
			var dataTest = {
				"id" : "bmsDepartment_gridtable"
			};
			jQuery.publish("onLoadSelect", dataTest, null);
		}
	});
	jQuery("#budgetModel_selectDepartment").click(function(){
		var sid = jQuery("#budgetModel_gridtable").jqGrid('getGridParam','selarrrow');
		if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择模板。");
			return;
		}
		var url = "bmDepartmentList?navTabId=bmsDepartment_gridtable&modelId="+sid;
		var winTitle='选择部门';
		$.pdialog.open(url,'sellectDepartment',winTitle, {mask:true,width : 700,height : 500});
	});
});
</script>
</head>
<div class="page">
	<div id="bmsDepartment_pageHeader" class="pageHeader">
		<form  id="bmsDepartment_search_form" class="queryarea-form">
			<div class="searchBar">
				<div class="searchContent">
					<label class="queryarea-label">
						<s:text name="department.branchCode"></s:text>
					</label>
				<div class="buttonActive" style="float:right">
					<div class="buttonContent">
						<button type="button" onclick="departmentGridReload()">
							<fmt:message key='button.search' />
						</button>
					</div>
				</div>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="bmsDepartment_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li>
					<a id=budgetModel_selectDepartment class="settingbutton"  href="javaScript:"><span>选择部门</span></a>
				</li>
			</ul>
		</div>
		<div id="bmsDepartment_gridtable_div" class="grid-wrapdiv" layoutH=150 tablecontainer="budgetModel_layout-south" extraHeight=145>
			<table id="bmsDepartment_gridtable"></table>
		</div>
		<div class="panelBar" id="bmsDepartment_pageBar">
			<div class="pages">
				<span>显示</span> <select id="bmsDepartment_gridtable_numPerPage">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span>条，共<label id="bmsDepartment_gridtable_totals"></label>条
				</span>
			</div>

			<div id="bmsDepartment_gridtable_pagination" class="pagination"
				targetType="navTab" totalCount="200" numPerPage="20"
				pageNumShown="10" currentPage="1"></div>

		</div>
	</div>
</div>
