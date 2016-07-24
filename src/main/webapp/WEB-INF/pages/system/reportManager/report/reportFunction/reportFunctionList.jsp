
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var reportFunctionLayout;
	var reportFunctionGridIdString="#reportFunction_gridtable";
	
	jQuery(document).ready(function() { 
		var reportFunctionGrid = jQuery(reportFunctionGridIdString);
    	reportFunctionGrid.jqGrid({
    		url : "reportFunctionGridList",
    		editurl:"reportFunctionGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'code',index:'code',align:'left',label : '<s:text name="reportFunction.code" />',hidden:false,key:true,width:100},
			{name:'name',index:'name',align:'left',label : '<s:text name="reportFunction.name" />',hidden:false,width:150},
			{name:'category',index:'category',align:'left',label : '<s:text name="reportFunction.category" />',hidden:false,width:100},
			{name:'dataType',index:'dataType',align:'left',label : '<s:text name="reportFunction.dataType" />',hidden:false,width:100},
			{name:'params',index:'params',align:'left',label : '<s:text name="reportFunction.params" />',hidden:false,width:200},
			{name:'funcSql',index:'funcSql',align:'left',label : '<s:text name="reportFunction.funcSql" />',hidden:false,width:250},
			{name:'remark',index:'remark',align:'left',label : '<s:text name="reportFunction.remark" />',hidden:false,width:200},
			{name:'rsType',index:'rsType',align:'left',label : '<s:text name="reportFunction.rsType" />',hidden:false}
			],
        	jsonReader : {
				root : "reportFunctions", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'code',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="reportFunctionList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	gridContainerResize('reportFunction','div');
           	var dataTest = {"id":"reportFunction_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    jQuery(reportFunctionGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="reportFunction_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="reportFunction_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportFunction.code'/>:
						<input type="text" name="filter_EQS_code"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportFunction.name'/>:
						<input type="text" name="filter_EQS_name"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportFunction.category'/>:
						<input type="text" name="filter_EQS_category"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportFunction.remark'/>:
						<input type="text" name="filter_EQS_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('reportFunction_search_form','reportFunction_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div id="reportFunction_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li><a id="reportFunction_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="reportFunction_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="reportFunction_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="reportFunction_gridtable_div" class="grid-wrapdiv" buttonBar="optId:code;width:600;height:400">
			<input type="hidden" id="reportFunction_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="reportFunction_gridtable_addTile">
				<s:text name="reportFunctionNew.title"/>
			</label> 
			<label style="display: none"
				id="reportFunction_gridtable_editTile">
				<s:text name="reportFunctionEdit.title"/>
			</label>
			<div id="load_reportFunction_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="reportFunction_gridtable"></table>
			<!--<div id="reportFunctionPager"></div>-->
		</div>
	</div>
	<div id="reportFunction_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="reportFunction_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="reportFunction_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="reportFunction_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>