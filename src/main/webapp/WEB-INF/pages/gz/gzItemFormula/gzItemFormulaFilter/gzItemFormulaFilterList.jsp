
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var gzItemFormulaFilterLayout;
	var gzItemFormulaFilterGridIdString="#gzItemFormulaFilter_gridtable";
	
	jQuery(document).ready(function() { 
		var gzItemFormulaFilterGrid = jQuery(gzItemFormulaFilterGridIdString);
    	gzItemFormulaFilterGrid.jqGrid({
    		url : "gzItemFormulaFilterGridList",
    		editurl:"gzItemFormulaFilterGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'filterId',index:'filterId',align:'center',label : '<s:text name="gzItemFormulaFilter.filterId" />',hidden:false,key:true},
{name:'code',index:'code',align:'center',label : '<s:text name="gzItemFormulaFilter.code" />',hidden:false},
{name:'name',index:'name',align:'center',label : '<s:text name="gzItemFormulaFilter.name" />',hidden:false},
{name:'oper',index:'oper',align:'center',label : '<s:text name="gzItemFormulaFilter.oper" />',hidden:false},
{name:'searchValue',index:'searchValue',align:'center',label : '<s:text name="gzItemFormulaFilter.searchValue" />',hidden:false}       
],
        	jsonReader : {
				root : "gzItemFormulaFilters", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'filterId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="gZItemFormulaFilterList.title" />',
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
           	var dataTest = {"id":"gzItemFormulaFilter_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("gzItemFormulaFilter_gridtable");
       		} 

    	});
    jQuery(gzItemFormulaFilterGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="gzItemFormulaFilter_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="gzItemFormulaFilter_search_form" class="queryarea-form" >
					<label class="queryarea-label" >
						<s:text name='gzItemFormulaFilter.conditionId'/>:
						<input type="text" name="filter_EQS_conditionId"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='gzItemFormulaFilter.code'/>:
						<input type="text" name="filter_EQS_code"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='gzItemFormulaFilter.gzItemFormula'/>:
						<input type="text" name="filter_EQS_gzItemFormula"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='gzItemFormulaFilter.name'/>:
						<input type="text" name="filter_EQS_name"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='gzItemFormulaFilter.oper'/>:
						<input type="text" name="filter_EQS_oper"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='gzItemFormulaFilter.searchValue'/>:
						<input type="text" name="filter_EQS_searchValue"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(gzItemFormulaFilter_search_form,gzItemFormulaFilter_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(gzItemFormulaFilter_search_form,gzItemFormulaFilter_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="gzItemFormulaFilter_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="gzItemFormulaFilter_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="gzItemFormulaFilter_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="gzItemFormulaFilter_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="gzItemFormulaFilter_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="gzItemFormulaFilter_gridtable_addTile">
				<s:text name="gzItemFormulaFilterNew.title"/>
			</label> 
			<label style="display: none"
				id="gzItemFormulaFilter_gridtable_editTile">
				<s:text name="gzItemFormulaFilterEdit.title"/>
			</label>
			<div id="load_gzItemFormulaFilter_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="gzItemFormulaFilter_gridtable"></table>
			<!--<div id="gZItemFormulaFilterPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="gzItemFormulaFilter_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="gzItemFormulaFilter_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="gzItemFormulaFilter_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>