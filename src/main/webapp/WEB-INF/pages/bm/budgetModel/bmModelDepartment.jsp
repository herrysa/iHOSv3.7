<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script type="text/javascript">
var bmsDepartmentGridIdString="#${random}_bmsDepartment_gridtable";
jQuery(document).ready(function() { 
	var bmsDepartmentGrid = jQuery(bmsDepartmentGridIdString);
	var modelId = "${modelId}";
	if(!modelId){
		modelId = "null";
	}
	bmsDepartmentGrid.jqGrid({
		url : "bmsDepartmentGridList?1=1&filter_EQS_bmModel.modelId="+modelId,
		editurl : "",
		datatype : "json",
		mtype : "GET",
		colModel : [
			{name : 'bmDeptId',index : 'bmDeptId',align : 'left',width:100,label : '<s:text name="bmDepartment.bmDeptId" />',hidden : true,key : true},
			{name : 'bmDepartment.departmentId',index : 'bmDepartment.departmentId',align : 'left',width:150,label : '<s:text name="department.departmentId" />',hidden : true,highsearch:true},
			{name : 'bmDepartment.deptCode',index : 'bmDepartment.deptCode',align : 'left',width:150,label : '<s:text name="预算责任中心编码" />',hidden : false, sortable:true,highsearch:true},
			{name : 'bmDepartment.name',index : 'bmDepartment.name',align : 'left',width:200,label : '<s:text name="预算责任中心名称" />',hidden : false, sortable:true,highsearch:true},
			/* {name : 'bmDepartment.org.orgname',index : 'org.orgname',align : 'left',width:130,label : '<s:text name="department.orgCode" />',hidden : false,highsearch:true},
			{name : 'bmDepartment.branch.branchName',index : 'branch.branchName',align : 'left',width:130,label : '<s:text name="department.branchCode" />',hidden : false,highsearch:true}, */
			{name : 'bmDepartment.deptClass',index : 'bmDepartment.deptClass',align : 'center',width:150,label : '<s:text name="科室类别" />',hidden : false, sortable:true,highsearch:true},
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
				"id" : "${random}_bmsDepartment_gridtable"
			};
			jQuery.publish("onLoadSelect", dataTest, null);
		}
	});
	jQuery("#${random}_bmsDepartment_selectDepartment").click(function(){
		if("${modelType}"=="2"){
			alertMsg.error("汇总模板不需要选择预算责任中心！");
			return ;
		}
		var sid = jQuery("#${random}_bmsDepartment_gridtable").jqGrid('getDataIDs');
		var deptId = "";
		for(var i in sid){
			var id = sid[i];
			var rowData = jQuery("#${random}_bmsDepartment_gridtable").jqGrid('getRowData',id);
			deptId += rowData['bmDepartment.departmentId']+",";
		}
		//var url = "selectBmModelDepartment?modelId=${modelId}&navTabId=${random}_bmsDepartment_gridtable&deptId="+deptId;
		var url = "bmvDepartmentList?modelId=${modelId}&navTabId=${random}_bmsDepartment_gridtable&departmentId="+deptId;
		var winTitle='选择预算责任中心';
		$.pdialog.open(url,'sellectDepartment',winTitle, {mask:true,width : 800,height : 500});
		stopPropagation();
	});
	jQuery("#${random}_bmsDepartment_delDepartment").click(function(){
		var sid = jQuery("#${random}_bmsDepartment_gridtable").jqGrid('getGridParam','selarrrow');
		if(sid==null|| sid.length == 0){       	
			alertMsg.error("请选择预算责任中心。");
			return;
		}
		$.post("delBmModelDepartment", {
			"_" : $.now(),navTabId:'${random}_bmsDepartment_gridtable',deptId:sid
		}, function(data) {
			formCallBack(data);
			
		});
	});
});
</script>
</head>
<div class="page">
	<div id="${random}_bmsDepartment_pageHeader" class="pageHeader">
		<form  id="${random}_bmsDepartment_search_form" class="queryarea-form">
			<div class="searchBar">
				<div class="searchContent">
					<label class="queryarea-label">
						<s:text name="预算责任中心编码"></s:text>
						<input type="text" name="filter_LIKES_bmDepartment.deptCode"/>
					</label>
					<label class="queryarea-label">
						<s:text name="预算责任中心名称"></s:text>
						<input type="text" name="filter_LIKES_bmDepartment.name"/>
					</label>
				<div class="buttonActive" style="float:right">
					<div class="buttonContent">
						<button type="button" onclick="propertyFilterSearch('${random}_bmsDepartment_search_form','${random}_bmsDepartment_gridtable')">
							<fmt:message key='button.search' />
						</button>
					</div>
				</div>
				</div>
			</div>
		</form>
	</div>
	<div class="pageContent">
		<div id="${random}_bmsDepartment_buttonBar" class="panelBar">
			<ul class="toolBar">
				<s:if test="modelId!=null&&modelId!=''">
					<li>
						<a id=${random}_bmsDepartment_selectDepartment class="addbutton" href="javaScript:"><span>添加预算责任中心</span></a>
					</li>
				</s:if>
				<s:else>
					<li id="addBmsDeptLi">
						<a id="addBmsDeptA" class="addbutton" style='color:#808080;'  href="javaScript:"><span>添加预算责任中心</span></a>
					</li>
					<script>
						jQuery("#addBmsDeptLi").unbind("hover");
						jQuery("#addBmsDeptA").hover(function(e){
				    		e.stopPropagation();
				    	});
					</script>
				</s:else>
				<s:if test="modelId!=null&&modelId!=''">
					<li>
						<a id=${random}_bmsDepartment_delDepartment class="delbutton"  href="javaScript:"><span>删除预算责任中心</span></a>
					</li>
				</s:if>
				<s:else>
					<li id="delBmsDeptLi">
						<a id="delBmsDeptA" class="addbutton" style='color:#808080;'  href="javaScript:"><span>删除预算责任中心</span></a>
					</li>
					<script>
						jQuery("#delBmsDeptLi").unbind("hover");
						jQuery("#delBmsDeptA").hover(function(e){
				    		e.stopPropagation();
				    	});
					</script>
				</s:else>
			</ul>
		</div>
		<div id="${random}_bmsDepartment_gridtable_div" class="grid-wrapdiv" layoutH=100>
			<table id="${random}_bmsDepartment_gridtable"></table>
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
