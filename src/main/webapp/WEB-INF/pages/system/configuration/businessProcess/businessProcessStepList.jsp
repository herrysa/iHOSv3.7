
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var businessProcessStepLayout;
	var businessProcessStepGridIdString="#businessProcessStep_gridtable";
	
	jQuery(document).ready(function() { 
		var businessProcessStepGrid = jQuery(businessProcessStepGridIdString);
    	businessProcessStepGrid.jqGrid({
    		url : "businessProcessStepGridList?filter_EQS_businessProcess.processCode=${processId}",
    		editurl:"businessProcessStepGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'stepCode',index:'stepCode',align:'left',label : '<s:text name="businessProcessStep.stepCode" />',hidden:false,key:true},
			{name:'stepName',index:'stepName',align:'left',label : '<s:text name="businessProcessStep.stepName" />',hidden:false},
			{name:'roleId',index:'roleId',align:'left',label : '<s:text name="businessProcessStep.roleId" />',hidden:true},
			{name:'okName',index:'okName',align:'left',label : '<s:text name="businessProcessStep.okName" />',hidden:false},
			{name:'noName',index:'noName',align:'left',label : '<s:text name="businessProcessStep.noName" />',hidden:false},
			{name:'roleName',index:'roleName',align:'left',label : '<s:text name="businessProcessStep.roleName" />',hidden:false},
			{name:'state',index:'state',align:'right',label : '<s:text name="businessProcessStep.state" />',hidden:false,formatter:'integer'},
			{name:'stepInfo',index:'stepInfo',align:'center',label : '<s:text name="businessProcessStep.stepInfo" />',hidden:false,formatter:'checkbox'},
			{name:'unionCheck',index:'unionCheck',align:'center',label : '<s:text name="businessProcessStep.unionCheck" />',hidden:false,formatter:'checkbox'},
			{name:'condition',index:'condition',align:'left',label : '<s:text name="businessProcessStep.condition" />',hidden:false},
			{name:'remark',index:'remark',align:'center',label : '<s:text name="businessProcessStep.remark" />',hidden:false}
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
			shrinkToFit:true,
			autowidth:true,
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	//gridContainerResize('businessProcessStep','div');
           	var dataTest = {"id":"businessProcessStep_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 
			
    	});
    jQuery(businessProcessStepGrid).jqGrid('bindKeys');
    
    jQuery("#businessProcessStep_gridtable_add_c").click(function(){
    	var sid = jQuery("#businessProcess_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条流程记录！");
    		return;
    	}
    	if(sid.length>1){
    		alertMsg.error("只能选择一条流程记录！");
    		return;
    	}
    	var url = "editBusinessProcessStep?navTabId=businessProcessStep_gridtable&processId=${processId}";
		var winTitle='<s:text name="businessProcessStepNew.title"/>';
		$.pdialog.open(url,'addBusinessProcessStep',winTitle, {mask:true,width : 550,height : 450});
    });
    jQuery("#businessProcessStep_gridtable_edit_c").click(function(){
    	var sid = jQuery("#businessProcessStep_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择一条步骤记录！");
    		return;
    	}
    	if(sid.length>1){
    		alertMsg.error("只能选择一条步骤记录！");
    		return;
    	}
    	var url = "editBusinessProcessStep?navTabId=businessProcessStep_gridtable&processId=${processId}&stepCode="+sid;
		var winTitle='<s:text name="businessProcessStepEdit.title"/>';
		$.pdialog.open(url,'editBusinessProcessStep',winTitle, {mask:true,width : 550,height : 450});
    });
  	});
</script>

<div class="page">
	<%-- <div id="businessProcessStep_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="businessProcessStep_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='businessProcessStep.stepCode'/>:
						<input type="text" name="filter_LIKES_stepCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='businessProcessStep.stepName'/>:
						<input type="text" name="filter_LIKES_stepName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='businessProcessStep.roleName'/>:
						<input type="text" name="filter_LIKES_roleName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='businessProcessStep.state'/>:
						<input type="text" name="filter_EQS_state"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='businessProcessStep.remark'/>:
						<input type="text" name="filter_LIKES_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('businessProcessStep_search_form','businessProcessStep_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
					</form>
				</div>
			</div>
	</div> --%>
	<div class="pageContent">
		<div id="businessProcessStep_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li><a id="businessProcessStep_gridtable_add_c" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="businessProcessStep_gridtable_del_c" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="businessProcessStep_gridtable_edit_c" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="businessProcessStep_gridtable_div" layoutH="180" class="grid-wrapdiv" buttonBar="width:500;height:300" tablecontainer="businessProcess_layout-south" extraHeight=105>
			<input type="hidden" id="businessProcessStep_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="businessProcessStep_gridtable_addTile">
				<s:text name="businessProcessStepNew.title"/>
			</label> 
			<label style="display: none"
				id="businessProcessStep_gridtable_editTile">
				<s:text name="businessProcessStepEdit.title"/>
			</label>
			<div id="load_businessProcessStep_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="businessProcessStep_gridtable"></table>
			<!--<div id="businessProcessStepPager"></div>-->
		</div>
	</div>
	<div id="businessProcessStep_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="businessProcessStep_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="businessProcessStep_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="businessProcessStep_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>