
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	jQuery(document).ready(function() {
		var countInvCheckGrid = jQuery("#countInvCheck_gridtable");
		countInvCheckGrid.jqGrid({
	    	url : "",
	    	editurl:"",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="invCheckDetail.id" />',hidden:true,key:true},	
				{name:'invDict.invCode',index:'invDict.invCode',align:'center',label : '<s:text name="inventoryDict.invCode" />',hidden:false},	
				{name:'invDict.invName',index:'invDict.invName',align:'center',label : '<s:text name="inventoryDict.invName" />',hidden:false},	
				{name:'invDict.invModel',index:'invDict.invModel',align:'center',label : '<s:text name="inventoryDict.invModel" />',hidden:false},	
				{name:'invDict.firstUnit',index:'invDict.firstUnit',align:'center',label : '<s:text name="inventoryDict.firstUnit" />',hidden:false},	
				{name:'chkAmount',index:'chkAmount',align:'center',label : '<s:text name="invCheckDetail.chkAmount" />',hidden:false},	
				{name:'price',index:'price',align:'center',label : '<s:text name="invCheckDetail.price" />',hidden:false},	
				{name:'diffAmount',index:'diffAmount',align:'center',label : '<s:text name="invCheckDetail.diffAmount" />',hidden:false},	
				{name:'diffMoney',index:'diffMoney',align:'center',label : '<s:text name="invCheckDetail.diffMoney" />',hidden:false}	
	        ],
	        jsonReader : {
				root : "countInvChecks", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'invDict.invCode',
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
	           var dataTest = {"id":"countInvCheck_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("countInvCheck_gridtable");
	       	} 
	
	    });
	    jQuery(countInvCheckGrid).jqGrid('bindKeys');
	    
	    jQuery("#countInvCheck_search_store").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where parent_id is not null and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		});
	});
</script>

<div class="page">
	<div id="countInvCheck_container">
		<div id="countInvCheck_layout-center" class="pane ui-layout-center">
			<div class="pageHeader">
				<div class="searchBar">
					<div class="searchContent">
						<form id="countInvCheck_search_form" >
							<label style="float:none;white-space:nowrap" >
								<s:text name='日期&nbsp;&nbsp;从'/>:
								<input type="text"	id="countInvCheck_search_make_date_from" class="Wdate" style="height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" name="filter_GED_checkDate">
							</label>&nbsp;&nbsp;
							<label style="float:none;white-space:nowrap" >
								<s:text name='到'/>:
							 	<input type="text"	id="countInvCheck_search_make_date_to"  class="Wdate" style="height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" name="filter_LED_checkDate">
							</label>&nbsp;&nbsp;
							<label style="float:none;white-space:nowrap" >
								<s:text name='inventoryDict.store'/>:
								<input type="hidden" id="countInvCheck_search_store_id" name="filter_EQS_store.id">
							 	<input type="text" id="countInvCheck_search_store" >
							</label>&nbsp;&nbsp;
						</form>
					</div>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive">
									<div class="buttonContent">
										<button type="button" onclick="propertyFilterSearch('countInvCheck_search_form','countInvCheck_gridtable')"><s:text name='button.search'/></button>
									</div>
								</div></li>
						
						</ul>
					</div>
				</div>
			</div>
			<div class="pageContent">
				<div id="countInvCheck_gridtable_div" layoutH="90"
					style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="countInvCheck_gridtable_navTabId" value="${sessionScope.navTabId}">
					<table id="countInvCheck_gridtable"></table>
				</div>
			</div>
			<div class="panelBar">
				<div class="pages">
					<span><s:text name="pager.perPage" /></span> <select id="countInvCheck_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="countInvCheck_gridtable_totals"></label><s:text name="pager.items" /></span>
				</div>
		
				<div id="countInvCheck_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>
		
			</div>
		</div>
	</div>
</div>