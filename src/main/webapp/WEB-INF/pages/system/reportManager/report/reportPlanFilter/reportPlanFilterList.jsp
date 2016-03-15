
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var reportPlanFilterLayout;
	var reportPlanFilterGridIdString="#reportPlanFilter_gridtable";
	
	jQuery(document).ready(function() { 
		var reportPlanFilterGrid = jQuery(reportPlanFilterGridIdString);
    	reportPlanFilterGrid.jqGrid({
    		url : "reportPlanFilterGridList",
    		editurl:"reportPlanFilterGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'filterId',index:'filterId',align:'center',label : '<s:text name="reportPlanFilter.filterId" />',hidden:false,key:true},{name:'filterCode',index:'filterCode',align:'center',label : '<s:text name="reportPlanFilter.filterCode" />',hidden:false},{name:'filterValue',index:'filterValue',align:'center',label : '<s:text name="reportPlanFilter.filterValue" />',hidden:false},{name:'planId',index:'planId',align:'center',label : '<s:text name="reportPlanFilter.planId" />',hidden:false}        	],
        	jsonReader : {
				root : "reportPlanFilters", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'filterId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="reportPlanFilterList.title" />',
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
           	var dataTest = {"id":"reportPlanFilter_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("reportPlanFilter_gridtable");
       		} 

    	});
    jQuery(reportPlanFilterGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="reportPlanFilter_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="reportPlanFilter_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlanFilter.filterId'/>:
						<input type="text" name="filter_EQS_filterId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlanFilter.filterCode'/>:
						<input type="text" name="filter_EQS_filterCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlanFilter.filterValue'/>:
						<input type="text" name="filter_EQS_filterValue"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlanFilter.planId'/>:
						<input type="text" name="filter_EQS_planId"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(reportPlanFilter_search_form,reportPlanFilter_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(reportPlanFilter_search_form,reportPlanFilter_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="reportPlanFilter_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="reportPlanFilter_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="reportPlanFilter_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="reportPlanFilter_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="reportPlanFilter_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="reportPlanFilter_gridtable_addTile">
				<s:text name="reportPlanFilterNew.title"/>
			</label> 
			<label style="display: none"
				id="reportPlanFilter_gridtable_editTile">
				<s:text name="reportPlanFilterEdit.title"/>
			</label>
			<div id="load_reportPlanFilter_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="reportPlanFilter_gridtable"></table>
			<!--<div id="reportPlanFilterPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="reportPlanFilter_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="reportPlanFilter_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="reportPlanFilter_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>