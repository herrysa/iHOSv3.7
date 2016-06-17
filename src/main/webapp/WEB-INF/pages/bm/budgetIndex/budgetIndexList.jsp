
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var budgetIndexLayout;
	var budgetIndexGridIdString="#budgetIndex_gridtable";
	
	jQuery(document).ready(function() { 
		var budgetIndexGrid = jQuery(budgetIndexGridIdString);
    	budgetIndexGrid.jqGrid({
    		url : "budgetIndexGridList",
    		editurl:"budgetIndexGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel:[
			{name:'indexCode',index:'indexCode',align:'left',label : '<s:text name="budgetIndex.indexCode" />',hidden:false,key:true},
			{name:'indexName',index:'indexName',align:'left',label : '<s:text name="budgetIndex.indexName" />',hidden:false},
			{name:'parentId',index:'parentId',align:'left',label : '<s:text name="budgetIndex.parentId" />',hidden:false},
			{name:'indexType',index:'indexType',align:'left',label : '<s:text name="budgetIndex.indexType" />',hidden:false},
			{name:'exceedBudgetType',index:'exceedBudgetType',align:'left',label : '<s:text name="budgetIndex.exceedBudgetType" />',hidden:false},
			{name:'warningPercent',index:'warningPercent',align:'left',label : '<s:text name="budgetIndex.warningPercent" />',hidden:false,formatter:'integer'}
			{name:'disabeld',index:'disabeld',align:'center',label : '<s:text name="budgetIndex.disabeld" />',hidden:false,formatter:'checkbox'},
			],
			jsonReader : {
				root : "budgetIndices", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'indexCode',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="budgetIndexList.title" />',
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
           	var dataTest = {"id":"budgetIndex_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("budgetIndex_gridtable");
       		} 

    	});
    jQuery(budgetIndexGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="budgetIndex_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="budgetIndex_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.indexCode'/>:
						<input type="text" name="filter_EQS_indexCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.budgetType'/>:
						<input type="text" name="filter_EQS_budgetType"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.disabeld'/>:
						<input type="text" name="filter_EQS_disabeld"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.exceedBudgetType'/>:
						<input type="text" name="filter_EQS_exceedBudgetType"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.indexName'/>:
						<input type="text" name="filter_EQS_indexName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.indexType'/>:
						<input type="text" name="filter_EQS_indexType"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.parentId'/>:
						<input type="text" name="filter_EQS_parentId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetIndex.warningPercent'/>:
						<input type="text" name="filter_EQS_warningPercent"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(budgetIndex_search_form,budgetIndex_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(budgetIndex_search_form,budgetIndex_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="budgetIndex_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="budgetIndex_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="budgetIndex_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="budgetIndex_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="budgetIndex_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="budgetIndex_gridtable_addTile">
				<s:text name="budgetIndexNew.title"/>
			</label> 
			<label style="display: none"
				id="budgetIndex_gridtable_editTile">
				<s:text name="budgetIndexEdit.title"/>
			</label>
			<div id="load_budgetIndex_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="budgetIndex_gridtable"></table>
			<!--<div id="budgetIndexPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="budgetIndex_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="budgetIndex_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="budgetIndex_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>