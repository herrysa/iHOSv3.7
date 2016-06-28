
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var budgetUpdataLayout;
	var budgetUpdataGridIdString="#budgetUpdata_gridtable";
	
	jQuery(document).ready(function() { 
		var budgetUpdataGrid = jQuery(budgetUpdataGridIdString);
    	budgetUpdataGrid.jqGrid({
    		url : "budgetUpdataGridList",
    		editurl:"budgetUpdataGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'updataId',index:'updataId',align:'center',label : '<s:text name="budgetUpdata.updataId" />',hidden:true,key:true},
			{name:'department.name',index:'department.name',align:'left',label : '<s:text name="budgetUpdata.department" />',hidden:false},
			{name:'state',index:'state',align:'center',label : '<s:text name="budgetUpdata.state" />',hidden:false,formatter : 'select',editoptions : {value : '0:上报中;1:已确认;2:科室已审核;3:已上报'}},
			{name:'operator.name',index:'operator.name',align:'left',label : '<s:text name="budgetUpdata.operator" />',hidden:false},
			{name:'optDate',index:'optDate',align:'center',label : '<s:text name="budgetUpdata.optDate" />',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'}},
			{name:'checker.name',index:'checker.name',align:'left',label : '<s:text name="budgetUpdata.checker" />',hidden:false},
			{name:'checkDate',index:'checkDate',align:'center',label : '<s:text name="budgetUpdata.checkDate" />',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'}},
			{name:'submitter.name',index:'submitter.name',align:'left',label : '<s:text name="budgetUpdata.submitter" />',hidden:false},
			{name:'submitDate',index:'submitDate',align:'center',label : '<s:text name="budgetUpdata.submitDate" />',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'}}
			],
        	jsonReader : {
				root : "budgetUpdatas", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'department.internalCode',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="budgetUpdataList.title" />',
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
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"budgetUpdata_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    jQuery(budgetUpdataGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="budgetUpdata_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="budgetUpdata_search_form" >
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='budgetUpdata.checkDate'/>:
						<input type="text" name="filter_EQS_checkDate"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetUpdata.checker'/>:
						<input type="text" name="filter_EQS_checker"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetUpdata.department'/>:
						<input type="text" name="filter_EQS_department"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='budgetUpdata.operator'/>:
						<input type="text" name="filter_EQS_operator"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetUpdata.optDate'/>:
						<input type="text" name="filter_EQS_optDate"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetUpdata.state'/>:
						<input type="text" name="filter_EQS_state"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='budgetUpdata.submitDate'/>:
						<input type="text" name="filter_EQS_submitDate"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetUpdata.submitter'/>:
						<input type="text" name="filter_EQS_submitter"/>
					</label> --%>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(budgetUpdata_search_form,budgetUpdata_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<%-- <li><a id="budgetUpdata_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li> --%>
				<li><a id="budgetUpdata_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<%-- <li><a id="budgetUpdata_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li> --%>
			
			</ul>
		</div>
		<div id="budgetUpdata_gridtable_div" layoutH="90" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="budgetUpdata_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="budgetUpdata_gridtable_addTile">
				<s:text name="budgetUpdataNew.title"/>
			</label> 
			<label style="display: none"
				id="budgetUpdata_gridtable_editTile">
				<s:text name="budgetUpdataEdit.title"/>
			</label>
			<div id="load_budgetUpdata_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="budgetUpdata_gridtable"></table>
			<!--<div id="budgetUpdataPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="budgetUpdata_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="budgetUpdata_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="budgetUpdata_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>