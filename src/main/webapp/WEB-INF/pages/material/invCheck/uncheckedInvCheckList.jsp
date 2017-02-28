
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	jQuery(document).ready(function() {
		var uncheckedInvCheckGrid = jQuery("#uncheckedInvCheck_gridtable");
		uncheckedInvCheckGrid.jqGrid({
	    	url : "",
	    	editurl:"",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="invCheck.id" />',hidden:true,key:true},	
				{name:'checkNo',index:'checkNo',align:'center',label : '<s:text name="invCheck.checkNo" />',hidden:false},	
				{name:'checkDate',index:'checkDate',align:'center',label : '<s:text name="invCheck.checkDate" />',hidden:false},	
				{name:'remark',index:'remark',align:'center',label : '<s:text name="invCheck.remark" />',hidden:false},	
				{name:'store.storeName',index:'store.storeName',align:'center',label : '<s:text name="invCheck.store" />',hidden:false},	
				{name:'makePerson.name',index:'makePerson.name',align:'center',label : '<s:text name="invCheck.makePerson" />',hidden:false},	
				{name:'store.keeper.name',index:'store.keeper.name',align:'center',label : '<s:text name="库管员" />',hidden:false},	
					
				{name:'state',index:'state',align:'center',label : '<s:text name="invCheck.state" />',hidden:false}	
	        ],
	        jsonReader : {
				root : "uncheckedInvChecks", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'checkDate',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="invCheckList.title" />',
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
	           if(jQuery(this).getDataIDs().length>0){
	              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
	            }
	           var dataTest = {"id":"uncheckedInvCheck_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("uncheckedInvCheck_gridtable");
	       	} 

	    });
	    jQuery(uncheckedInvCheckGrid).jqGrid('bindKeys');
	});
</script>

<div class="page">
	<div id="uncheckedInvCheck_container">
		<div id="uncheckedInvCheck_layout-center" class="pane ui-layout-center">
			<div class="pageHeader">
				<div class="searchBar">
					<div class="searchContent">
						<form id="uncheckedInvCheck_search_form" >
							<label style="float:none;white-space:nowrap" >
								<s:text name='结账年月'/>:
								<input type="text"	id="invCheck_search_person" name="filter_EQS_">
							</label>&nbsp;&nbsp;
						</form>
					</div>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive">
									<div class="buttonContent">
										<button type="button" onclick="propertyFilterSearch('uncheckedInvCheck_search_form','uncheckedInvCheck_gridtable')"><s:text name='button.search'/></button>
									</div>
								</div></li>
						
						</ul>
					</div>
				</div>
			</div>
			<div class="pageContent">
				<div class="panelBar" style="display:none">
				</div>
				<div id="uncheckedInvCheck_gridtable_div" layoutH="90"
					style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="uncheckedInvCheck_gridtable_navTabId" value="${sessionScope.navTabId}">
					<table id="uncheckedInvCheck_gridtable"></table>
				</div>
			</div>
			<div class="panelBar">
				<div class="pages">
					<span><s:text name="pager.perPage" /></span> <select id="uncheckedInvCheck_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="uncheckedInvCheck_gridtable_totals"></label><s:text name="pager.items" /></span>
				</div>
		
				<div id="uncheckedInvCheck_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>
		
			</div>
		</div>
	</div>
</div>