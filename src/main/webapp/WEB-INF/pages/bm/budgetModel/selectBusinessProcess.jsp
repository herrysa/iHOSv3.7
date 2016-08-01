
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var bpsLayout;
	var bpsGridIdString="#bps_gridtable";
	
	jQuery(document).ready(function() { 
		var bpsGrid = jQuery(bpsGridIdString);
    	bpsGrid.jqGrid({
    		url : "businessProcessStepGridList?filter_EQS_businessProcess.processCode=${bmCheckProcessCode}&filter_NIS_stepCode=${stepCode}", 
    		editurl:"businessProcessStepGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'stepCode',index:'stepCode',align:'left',label : '<s:text name="bps.stepCode" />',hidden:false,key:true,width:100},
			{name:'stepName',index:'stepName',align:'left',label : '<s:text name="bps.stepName" />',hidden:false,width:150},
			{name:'okName',index:'okName',align:'left',label : '<s:text name="bps.okName" />',hidden:false,width:100},
			{name:'noName',index:'noName',align:'left',label : '<s:text name="bps.noName" />',hidden:false,width:100},
			{name:'roleId',index:'roleId',align:'left',label : '<s:text name="bps.roleId" />',hidden:true},
			{name:'roleName',index:'roleName',align:'left',label : '<s:text name="bps.roleName" />',hidden:false,width:150},
			{name:'state',index:'state',align:'right',label : '<s:text name="bps.state" />',hidden:false,formatter:'integer',width:70},
			{name:'stepInfo',index:'stepInfo',align:'center',label : '<s:text name="bps.stepInfo" />',hidden:false,formatter:'checkbox',width:70},
			{name:'unionCheck',index:'unionCheck',align:'center',label : '<s:text name="bps.unionCheck" />',hidden:false,formatter:'checkbox',width:70},
			{name:'condition',index:'condition',align:'left',label : '<s:text name="bps.condition" />',hidden:false,width:100},
			{name:'remark',index:'remark',align:'left',label : '<s:text name="bps.remark" />',hidden:false,width:200}
			],
        	jsonReader : {
				root : "businessProcessSteps", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'state',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="businessProcessStepList.title" />',
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
           	//gridContainerResize('businessProcessStep','div');
           	var dataTest = {"id":"bps_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 
			
    	});
    
    jQuery("#bps_gridtable_select").click(function(){
    	var sid = jQuery("#bps_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择步骤记录！");
    		return;
    	}
    	$.ajax({
            url: 'selectedBpsToModel?navTabId=modelProcess_gridtable&modelId=${modelId}&stepCode='+sid,
            type: 'post',
            dataType: 'json',
            async:false,
            error: function(data){
            	alertMsg.error("系统错误！");
            },
            success: function(data){
                formCallBack(data);
            }
        });
    });
  	});
</script>

<div class="page">
	<div class="pageContent">
		<div id="bps_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li><a id="bps_gridtable_select" class="addbutton" href="javaScript:" ><span>确定
					</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="bps_gridtable_div" layoutH="60" class="grid-wrapdiv" buttonBar="width:500;height:300" >
			<div id="load_bps_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="bps_gridtable"></table>
		</div>
	</div>
	<div id="bps_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="bps_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="bps_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="bps_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>