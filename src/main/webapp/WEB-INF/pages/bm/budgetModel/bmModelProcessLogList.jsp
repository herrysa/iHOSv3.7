
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var bmModelProcessLogLayout;
	var bmModelProcessLogGridIdString="#bmModelProcessLog_gridtable";
	
	jQuery(document).ready(function() { 
		var bmModelProcessLogGrid = jQuery(bmModelProcessLogGridIdString);
    	bmModelProcessLogGrid.jqGrid({
    		url : "bmModelProcessLogGridList?filter_EQS_updataId=${updataId}",
    		editurl:"bmModelProcessLogGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'logId',index:'logId',align:'center',label : '<s:text name="bmModelProcessLog.logId" />',hidden:true,key:true},
			{name:'deptId',index:'deptId',align:'center',label : '<s:text name="bmModelProcessLog.deptId" />',hidden:true},
			{name:'deptName',index:'deptName',align:'left',label : '<s:text name="bmModelProcessLog.dept" />',hidden:false,width:150},
			{name:'personName',index:'personName',align:'left',label : '<s:text name="bmModelProcessLog.person" />',hidden:false,width:100},
			{name:'modelId',index:'modelId',align:'left',label : '<s:text name="bmModelProcessLog.modelId" />',hidden:false,width:150},
			{name:'stepName',index:'stepName',align:'left',label : '<s:text name="bmModelProcessLog.stepName" />',hidden:false,width:100},
			{name:'operation',index:'operation',align:'left',label : '<s:text name="bmModelProcessLog.operation" />',hidden:false,width:60},
			{name:'optTime',index:'optTime',align:'center',label : '<s:text name="bmModelProcessLog.optTime" />',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'},width:80},
			{name:'info',index:'info',align:'left',label : '<s:text name="bmModelProcessLog.info" />',hidden:false,width:150},
			{name:'personId',index:'personId',align:'left',label : '<s:text name="bmModelProcessLog.personId" />',hidden:true},
			{name:'state',index:'state',align:'right',label : '<s:text name="bmModelProcessLog.state" />',hidden:false,formatter:'integer',width:60},
			{name:'stepCode',index:'stepCode',align:'left',label : '<s:text name="bmModelProcessLog.stepCode" />',hidden:true},
			{name:'updataId',index:'updataId',align:'left',label : '<s:text name="bmModelProcessLog.updataId" />',hidden:true} 
			],
        	jsonReader : {
				root : "bmModelProcessLogs", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'optTime',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="bmModelProcessLogList.title" />',
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
           	var dataTest = {"id":"bmModelProcessLog_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("bmModelProcessLog_gridtable");
       		} 

    	});
    jQuery(bmModelProcessLogGrid).jqGrid('bindKeys');
    jQuery("#bmModelProcessLog_gridtable_graph").click(function(){
    	alert("敬请期待！");
    });
  	});
</script>

<div class="page">
	<div id="bmModelProcessLog_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="bmModelProcessLog_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.dept'/>:
						<input type="text" name="filter_LIKES_deptName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.person'/>:
						<input type="text" name="filter_LIKES_personName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.info'/>:
						<input type="text" name="filter_LIKES_info"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.modelId'/>:
						<input type="text" name="filter_EQS_modelId"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.optTime'/>:
						从<input type="text" name="filter_GED_optTime" class="input-mini" type="text" 
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
									value="" size="8"/>
						到&nbsp;<input type="text" name="filter_LED_optTime" class="input-mini" type="text" 
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
									value="" size="8"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('bmModelProcessLog_search_form','bmModelProcessLog_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				
				<li><a id="bmModelProcessLog_gridtable_graph" class="previewbutton"  href="javaScript:"><span>流程图</span>
				</a>
				</li>
				
			
			</ul>
		</div>
		<div id="bmModelProcessLog_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="bmModelProcessLog_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="bmModelProcessLog_gridtable_addTile">
				<s:text name="bmModelProcessLogNew.title"/>
			</label> 
			<label style="display: none"
				id="bmModelProcessLog_gridtable_editTile">
				<s:text name="bmModelProcessLogEdit.title"/>
			</label>
			<div id="load_bmModelProcessLog_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="bmModelProcessLog_gridtable"></table>
			<!--<div id="bmModelProcessLogPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="bmModelProcessLog_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="bmModelProcessLog_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="bmModelProcessLog_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>