
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var budgetModelXfLayout;
	var budgetModelXfGridIdString="#budgetModelXf_gridtable";
	
	jQuery(document).ready(function() { 
		var budgetModelXfGrid = jQuery(budgetModelXfGridIdString);
    	budgetModelXfGrid.jqGrid({
    		url : "budgetModelXfGridList",
    		editurl:"budgetModelXfGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'xfId',index:'xfId',align:'center',label : '<s:text name="budgetModelXf.xfId" />',hidden:true,key:true},
			{name:'modelName',index:'modelName',align:'left',label : '<s:text name="budgetModelXf.model" />',hidden:false},
			{name:'periodYear',index:'periodYear',align:'center',label : '<s:text name="budgetModelXf.periodYear" />',hidden:false},
			{name:'budgetType',index:'state',align:'left',label : '<s:text name="budgetModelXf.budgetType" />',hidden:false,formatter:'integer'},
			{name:'state',index:'state',align:'center',label : '<s:text name="budgetModelXf.state" />',hidden:false,formatter:'integer'},
			{name:'xfDate',index:'xfDate',align:'center',label : '<s:text name="budgetModelXf.xfDate" />',hidden:false,formatter:'date'},
			{name:'xfNum',index:'xfNum',align:'left',label : '<s:text name="budgetModelXf.xfNum" />',hidden:false},
			{name:'updatating',index:'updatating',align:'left',label : '<s:text name="budgetModelXf.updatating" />',hidden:false},
			{name:'updatated',index:'updatated',align:'left',label : '<s:text name="budgetModelXf.updatated" />',hidden:false}
			],
        	jsonReader : {
				root : "budgetModelXfs", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'xfId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="budgetModelXfList.title" />',
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
           	var dataTest = {"id":"budgetModelXf_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("budgetModelXf_gridtable");
       		} 

    	});
    jQuery(budgetModelXfGrid).jqGrid('bindKeys');
    
    jQuery("#budgetModelXf_gridtable_refresh").click(function(){
    	$.get("budgetModelXfRefresh", {
			"_" : $.now()
		}, function(data) {
			formCallBack(data);
		});
    });
  	});
</script>

<div class="page">
	<div id="budgetModelXf_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="budgetModelXf_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelXf.model'/>:
						<input type="text" name="filter_EQS_model"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelXf.state'/>:
						<input type="text" name="filter_EQS_state"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModelXf.xfDate'/>:
						<input type="text" name="filter_EQS_xfDate"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(budgetModelXf_search_form,budgetModelXf_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="budgetModelXf_gridtable_refresh" class="addbutton" href="javaScript:" ><span>刷新
					</span>
				</a>
				</li>
				<li><a id="budgetModelXf_gridtable_xf" class="delbutton"  href="javaScript:"><span>下发</span>
				</a>
				</li>
				<li><a id="budgetModelXf_gridtable_reXf" class="changebutton"  href="javaScript:"
					><span>重新下发
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="budgetModelXf_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="budgetModelXf_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="budgetModelXf_gridtable_addTile">
				<s:text name="budgetModelXfNew.title"/>
			</label> 
			<label style="display: none"
				id="budgetModelXf_gridtable_editTile">
				<s:text name="budgetModelXfEdit.title"/>
			</label>
			<div id="load_budgetModelXf_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="budgetModelXf_gridtable"></table>
			<!--<div id="budgetModelXfPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="budgetModelXf_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="budgetModelXf_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="budgetModelXf_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>