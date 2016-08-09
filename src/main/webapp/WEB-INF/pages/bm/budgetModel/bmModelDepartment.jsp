<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script type="text/javascript">
var bmsDepartmentGridIdString="#bmsDepartment_gridtable";
jQuery(document).ready(function() { 
	var bmsDepartmentGrid = jQuery(bmsDepartmentGridIdString);
	bmsDepartmentGrid.jqGrid({
		url : "bmsDepartmentGridList?1=1&filter_EQS_bmModel.modelId=${modelId}",
		editurl : "",
		datatype : "json",
		mtype : "GET",
		colModel : [
			{name : 'bmDeptId',index : 'bmDeptId',align : 'left',width:100,label : '<s:text name="bmDepartment.bmDeptId" />',hidden : true,key : true},
			{name : 'bmDepartment.departmentId',index : 'bmDepartment.departmentId',align : 'left',width:100,label : '<s:text name="department.departmentId" />',hidden : true,highsearch:true},
			{name : 'bmDepartment.deptCode',index : 'bmDepartment.deptCode',align : 'left',width:100,label : '<s:text name="department.deptCode" />',hidden : false, sortable:true,highsearch:true},
			{name : 'bmDepartment.name',index : 'bmDepartment.name',align : 'left',width:120,label : '<s:text name="department.name" />',hidden : false, sortable:true,highsearch:true},
			/* {name : 'bmDepartment.org.orgname',index : 'org.orgname',align : 'left',width:130,label : '<s:text name="department.orgCode" />',hidden : false,highsearch:true},
			{name : 'bmDepartment.branch.branchName',index : 'branch.branchName',align : 'left',width:130,label : '<s:text name="department.branchCode" />',hidden : false,highsearch:true}, */
			{name : 'bmDepartment.deptClass',index : 'bmDepartment.deptClass',align : 'center',width:70,label : '<s:text name="department.deptClass" />',hidden : false, sortable:true,highsearch:true},
			{name : 'bmDepartment.outin',index : 'bmDepartment.outin',align : 'left',width:70,label : '<s:text name="department.outin" />',hidden : false, sortable:true,highsearch:true},
			{name : 'bmDepartment.dgroup',index : 'bmDepartment.dgroup',align : 'left',width:70,label : '<s:text name="department.dgroup" />',hidden : false, sortable:true,highsearch:true},
			{name : 'bmDepartment.clevel',index : 'bmDepartment.clevel',align : 'center',width:50,label : '<s:text name="department.clevel" />',hidden : false, sortable:true,highsearch:true},
			{name : 'bmDepartment.subClass',index : 'bmDepartment.subClass',align : 'center',width:100,label : '<s:text name="department.subClass" />',hidden : false, sortable:true,highsearch:true},
			{name : 'bmDepartment.cnCode',index : 'bmDepartment.cnCode',align : 'left',width:100,label : '<s:text name="department.cnCode" />',hidden : false, sortable:true,highsearch:true},
			{name : 'bmDepartment.ysDeptName',index : 'bmDepartment.ysDeptName',align : 'left',width:100,label : '<s:text name="department.ysName" />',hidden : true, sortable:true,highsearch:true},
			{name : 'bmDepartment.ysLeaf',index : 'bmDepartment.ysLeaf',align : 'center',width:50,label : '<s:text name="department.ysLeaf" />',hidden : true, sortable:true,formatter:'checkbox',highsearch:true},
			],
		jsonReader : {
			root : "bmModelDepts", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		},
		sortname : 'bmDepartment.deptCode',
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
	jQuery("#bmsDepartment_selectDepartment").click(function(){
		var sid = jQuery("#bmsDepartment_gridtable").jqGrid('getDataIDs');
		var deptId = "";
		for(var i in sid){
			var id = sid[i];
			var rowData = jQuery("#bmsDepartment_gridtable").jqGrid('getRowData',id);
			deptId += rowData['bmDepartment.departmentId']+",";
		}
		var url = "selectBmModelDepartment?modelId=${modelId}&navTabId=bmsDepartment_gridtable&deptId="+deptId;
		var winTitle='选择部门';
		$.pdialog.open(url,'sellectDepartment',winTitle, {mask:true,width : 700,height : 500});
		stopPropagation();
	});
	jQuery("#bmsDepartment_delDepartment").click(function(){
		var sid = jQuery("#bmsDepartment_gridtable").jqGrid('getGridParam','selarrrow');
		if(sid==null|| sid.length == 0){       	
			alertMsg.error("请选择部门。");
			return;
		}
		$.post("delBmModelDepartment", {
			"_" : $.now(),navTabId:'bmsDepartment_gridtable',deptId:sid
		}, function(data) {
			formCallBack(data);
			
		});
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
						<s:text name="department.deptCode"></s:text>
						<input type="text" name="filter_LIKES_bmDepartment.deptCode"/>
					</label>
					<label class="queryarea-label">
						<s:text name="department.name"></s:text>
						<input type="text" name="filter_LIKES_bmDepartment.name"/>
					</label>
				<div class="buttonActive" style="float:right">
					<div class="buttonContent">
						<button type="button" onclick="propertyFilterSearch('bmsDepartment_search_form','bmsDepartment_gridtable')">
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
					<a id=bmsDepartment_selectDepartment class="addbutton"  href="javaScript:"><span>添加部门</span></a>
				</li>
				<li>
					<a id=bmsDepartment_delDepartment class="delbutton"  href="javaScript:"><span>删除部门</span></a>
				</li>
			</ul>
		</div>
		<div id="bmsDepartment_gridtable_div" class="grid-wrapdiv" layoutH=100>
			<table id="bmsDepartment_gridtable"></table>
		</div>
		<!-- <div class="panelBar" id="bmsDepartment_pageBar">
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

		</div> -->
	</div>
</div>
