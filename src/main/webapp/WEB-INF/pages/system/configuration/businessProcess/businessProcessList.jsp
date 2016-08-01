
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var businessProcessGridIdString="#businessProcess_gridtable";
	
	jQuery(document).ready(function() {
		var businessProcessChangeData = function(processId) {
			//reloadTab(selectedSearchId);
			
			jQuery("#businessProcessStepDiv").load("businessProcessStepList?processId="+processId);
		}
		var businessProcessLayout = makeLayout(
				{
					'baseName' : 'businessProcess',
					'tableIds' : 'businessProcess_gridtable;southTabs',
					'key' : 'businessProcess',
					'proportion' : 2
				}, businessProcessChangeData);
		var businessProcessGrid = jQuery(businessProcessGridIdString);
    	businessProcessGrid.jqGrid({
    		url : "businessProcessGridList",
    		editurl:"businessProcessGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'processCode',index:'processCode',align:'left',label : '<s:text name="businessProcess.processCode" />',hidden:false,key:true,width:100},
			{name:'processName',index:'processName',align:'left',label : '<s:text name="businessProcess.processName" />',hidden:false,width:150},
			{name:'processType',index:'processType',align:'left',label : '<s:text name="businessProcess.processType" />',hidden:false,width:100},
			{name:'businessTable',index:'businessTable',align:'left',label : '<s:text name="businessProcess.businessTable" />',hidden:false,width:150},
			{name:'stateColumn',index:'stateColumn',align:'left',label : '<s:text name="businessProcess.stateColumn" />',hidden:false,width:70},
			{name:'remark',index:'remark',align:'left',label : '<s:text name="businessProcess.remark" />',hidden:false,width:200}
			],
        	jsonReader : {
				root : "businessProcesses", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'processCode',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="businessProcessList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
			ondblClickRow:function(){
				businessProcessLayout.optDblclick();
			},
			onSelectRow: function(rowid) {
	        	setTimeout(function(){
	        		businessProcessLayout.optClick();
	        	},100);
	       	},
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	gridContainerResize('businessProcess','fullLayout');
           	var dataTest = {"id":"businessProcess_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    jQuery(businessProcessGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="businessProcess_container">
	<div id="businessProcess_layout-center" class="pane ui-layout-center" style="padding: 2px">
		<div id="businessProcess_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="businessProcess_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='businessProcess.processCode'/>:
						<input type="text" name="filter_LIKES_processCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='businessProcess.businessTable'/>:
						<input type="text" name="filter_LIKES_businessTable"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='businessProcess.processName'/>:
						<input type="text" name="filter_LIKES_processName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='businessProcess.processType'/>:
						<input type="text" name="filter_LIKES_processType"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='businessProcess.remark'/>:
						<input type="text" name="filter_LIKES_remark"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='businessProcess.stateColumn'/>:
						<input type="text" name="filter_EQS_stateColumn"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('businessProcess_search_form','businessProcess_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div> 
	<div class="pageContent">
		<div id="businessProcess_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li><a id="businessProcess_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="businessProcess_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="businessProcess_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="businessProcess_gridtable_div" class="grid-wrapdiv" buttonBar="optId:processCode;width:500;height:300">
			<input type="hidden" id="businessProcess_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="businessProcess_gridtable_addTile">
				<s:text name="businessProcessNew.title"/>
			</label> 
			<label style="display: none"
				id="businessProcess_gridtable_editTile">
				<s:text name="businessProcessEdit.title"/>
			</label>
			<div id="load_businessProcess_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="businessProcess_gridtable"></table>
			<!--<div id="businessProcessPager"></div>-->
		</div>
	</div>
	<div id="businessProcess_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="businessProcess_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="businessProcess_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="businessProcess_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	</div>
	<div id="businessProcess_layout-south" class="pane ui-layout-south" style="padding: 2px;overflow:hidden !important">
		<div class="panelBar">
			<ul class="toolBar">
				<li style="float: right;">
					<a id="businessProcess_close" class="closebutton" href="javaScript:"><span><fmt:message key="button.close" /></span></a>
				</li>
				<li class="line" style="float: right">line</li>
				<li style="float: right;">
					<a id="businessProcess_fold" class="foldbutton" href="javaScript:"><span><fmt:message key="button.fold" /></span></a>
				</li>
				<li class="line" style="float: right">line</li>
				<li style="float: right">
					<a id="businessProcess_unfold" class="unfoldbutton" href="javaScript:"><span><fmt:message key="button.unfold" /></span></a>
				</li>
			</ul>
		</div>
		<div id="businessProcessStepDiv"></div>
	</div>
	</div>
</div>