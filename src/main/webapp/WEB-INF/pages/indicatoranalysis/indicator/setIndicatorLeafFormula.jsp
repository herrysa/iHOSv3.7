<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	var indicatorFormulaFieldGridIdString="#indicatorFormulaField_gridtable";
	jQuery(document).ready(function() { 
		var indicatorFormulaFieldGrid = jQuery(indicatorFormulaFieldGridIdString);
		indicatorFormulaFieldGrid.jqGrid({
			url : "formulaFieldGridList?filter_EQS_keyClass=财务&filter_EQB_disabled=0",
			datatype : "json",
			mtype : "GET",
	    	colModel:[
				{name:'formulaFieldId',index:'formulaFieldId',align:'left',width:'100',label : '<s:text name="formulaField.formulaFieldId" />',hidden:false,key:true},
				{name:'fieldTitle',index:'fieldTitle',align:'left',width:'120',label : '<s:text name="formulaField.fieldTitle" />',hidden:false},
				{name:'formula.formula',index:'formula.formula',align:'left',width:'200',label : '<s:text name="formulaField.formula.formula" />',hidden:false},
				{name:'formula.condition',index:'formula.formula',align:'left',width:'200',label : '<s:text name="formulaField.formula.condition" />',hidden:false},
				{name:'description',index:'description',align:'left',width:'150',label : '<s:text name="formulaField.description" />',hidden:false}
			],
	    	jsonReader : {
				root : "formulaFields", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
	    	sortname: 'formulaFieldId',
	    	viewrecords: true,
	    	sortorder: 'asc',
	    	//caption:'<s:text name="indicatorAnalysisList.title" />',
	    	height:300,
	    	gridview:true,
	    	rownumbers:true,
	    	loadui: "disable",
	    	multiselect: true,
			multiboxonly:true,
			shrinkToFit:true,
			autowidth:true,
	    	onSelectRow: function(rowid) {
	   		},
		 	gridComplete:function(){
		 		var realFormula = "${realFormula}";
		 		if(realFormula){
		 			var rowNum = jQuery(this).getDataIDs().length;
		            if(rowNum>0){
		                var rowIds = jQuery(this).getDataIDs();
		                var ret = jQuery(this).jqGrid('getRowData');
		                var id='';
		                for(var i=0;i<rowNum;i++){
		              	  id=rowIds[i];
		              	  if(realFormula==id){
		              		  jQuery(this).setSelection(id);
		              		  break;
		              	  }
		              }
		            }
		 		}
	           	var dataTest = {"id":"indicatorFormulaField_gridtable"};
	      	   	jQuery.publish("onLoadSelect",dataTest,null);
	      	   	makepager("indicatorFormulaField_gridtable");
	   		} 
	    });
	    jQuery(indicatorFormulaFieldGrid).jqGrid('bindKeys');
	});
	
	function saveFormulaFieldToIndicator(){
		var sid = jQuery("#indicatorFormulaField_gridtable").jqGrid('getGridParam', 'selarrrow');
		if(sid && sid.length>1){
			alertMsg.error("请选择一条记录！");
			return;
		}else if(sid && sid.length==1){
			var rowData = jQuery("#indicatorFormulaField_gridtable").jqGrid('getRowData',sid[0]);
			jQuery("#indicator_formula").val(rowData['fieldTitle']);
			jQuery("#indicator_realFormula").val(rowData['formulaFieldId']);
		}else {
			jQuery("#indicator_formula").val("");
			jQuery("#indicator_realFormula").val("");
		}
		$.pdialog.closeCurrent();
	}
</script>
</head>
<div class="page">
	<div id="indicatorFormulaField_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="indicatorFormulaField_search_form" >
					<label style="float:none;white-space:nowrap" >
						<fmt:message key="formulaField.fieldTitle" />：
						<input type="text" name="filter_LIKES_fieldTitle"/>
					</label> &nbsp;&nbsp;
				</form>
				<div class="buttonActive" style="float:right">
					<div class="buttonContent">
						<button type="button" onclick="propertyFilterSearch('indicatorFormulaField_search_form','indicatorFormulaField_gridtable')"><s:text name='button.search'/></button>
					</div>
				</div>
			</div>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="propertyFilterSearch('indicatorFormulaField_search_form','indicatorFormulaField_gridtable')"><s:text name='button.search'/></button>
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
				<li>
					<a class="savebutton" href="javaScript:saveFormulaFieldToIndicator()" ><span><s:text name="确定" /></span></a>
				</li>
			</ul>
		</div>
		<div id="indicatorFormulaField_gridtable_div" layoutH="120" class="grid-wrapdiv">
			<input type="hidden" id="indicatorFormulaField_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_indicatorFormulaField_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="indicatorFormulaField_gridtable"></table>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="indicatorFormulaField_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="indicatorFormulaField_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="indicatorFormulaField_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>