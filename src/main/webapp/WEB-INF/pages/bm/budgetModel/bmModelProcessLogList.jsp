
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var bmModelProcessLogLayout;
	var bmModelProcessLogGridIdString="#bmModelProcessLog_gridtable";
	
	jQuery(document).ready(function() { 
		var bmModelProcessLogGrid = jQuery(bmModelProcessLogGridIdString);
    	bmModelProcessLogGrid.jqGrid({
    		url : "bmModelProcessLogGridList",
    		editurl:"bmModelProcessLogGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'logId',index:'logId',align:'center',label : '<s:text name="bmModelProcessLog.logId" />',hidden:false,key:true},{name:'deptId',index:'deptId',align:'center',label : '<s:text name="bmModelProcessLog.deptId" />',hidden:false},{name:'deptName',index:'deptName',align:'center',label : '<s:text name="bmModelProcessLog.deptName" />',hidden:false},{name:'info',index:'info',align:'center',label : '<s:text name="bmModelProcessLog.info" />',hidden:false},{name:'modelId',index:'modelId',align:'center',label : '<s:text name="bmModelProcessLog.modelId" />',hidden:false},{name:'operation',index:'operation',align:'center',label : '<s:text name="bmModelProcessLog.operation" />',hidden:false},{name:'optTime',index:'optTime',align:'center',label : '<s:text name="bmModelProcessLog.optTime" />',hidden:false,formatter:'date'},{name:'personId',index:'personId',align:'center',label : '<s:text name="bmModelProcessLog.personId" />',hidden:false},{name:'personName',index:'personName',align:'center',label : '<s:text name="bmModelProcessLog.personName" />',hidden:false},{name:'state',index:'state',align:'center',label : '<s:text name="bmModelProcessLog.state" />',hidden:false,formatter:'integer'},{name:'stepCode',index:'stepCode',align:'center',label : '<s:text name="bmModelProcessLog.stepCode" />',hidden:false},{name:'updataId',index:'updataId',align:'center',label : '<s:text name="bmModelProcessLog.updataId" />',hidden:false}        	],
        	jsonReader : {
				root : "bmModelProcessLogs", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'logId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="bmModelProcessLogList.title" />',
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
           	var dataTest = {"id":"bmModelProcessLog_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("bmModelProcessLog_gridtable");
       		} 

    	});
    jQuery(bmModelProcessLogGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="bmModelProcessLog_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="bmModelProcessLog_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.logId'/>:
						<input type="text" name="filter_EQS_logId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.deptId'/>:
						<input type="text" name="filter_EQS_deptId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.deptName'/>:
						<input type="text" name="filter_EQS_deptName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.info'/>:
						<input type="text" name="filter_EQS_info"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.modelId'/>:
						<input type="text" name="filter_EQS_modelId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.operation'/>:
						<input type="text" name="filter_EQS_operation"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.optTime'/>:
						<input type="text" name="filter_EQS_optTime"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.personId'/>:
						<input type="text" name="filter_EQS_personId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.personName'/>:
						<input type="text" name="filter_EQS_personName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.state'/>:
						<input type="text" name="filter_EQS_state"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.stepCode'/>:
						<input type="text" name="filter_EQS_stepCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bmModelProcessLog.updataId'/>:
						<input type="text" name="filter_EQS_updataId"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(bmModelProcessLog_search_form,bmModelProcessLog_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(bmModelProcessLog_search_form,bmModelProcessLog_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="bmModelProcessLog_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="bmModelProcessLog_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="bmModelProcessLog_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
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