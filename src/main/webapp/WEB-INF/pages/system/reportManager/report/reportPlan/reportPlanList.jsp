
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var reportPlanLayout;
	var reportPlanGridIdString="#reportPlan_gridtable";
	
	jQuery(document).ready(function() { 
		var reportPlanGrid = jQuery(reportPlanGridIdString);
    	reportPlanGrid.jqGrid({
    		url : "reportPlanGridList",
    		editurl:"reportPlanGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'planId',index:'planId',align:'center',label : '<s:text name="reportPlan.planId" />',hidden:false,key:true},{name:'customLayout',index:'customLayout',align:'center',label : '<s:text name="reportPlan.customLayout" />',hidden:false},{name:'defineId',index:'defineId',align:'center',label : '<s:text name="reportPlan.defineId" />',hidden:false},{name:'planName',index:'planName',align:'center',label : '<s:text name="reportPlan.planName" />',hidden:false},{name:'sysField',index:'sysField',align:'center',label : '<s:text name="reportPlan.sysField" />',hidden:false,formatter:'checkbox'},{name:'toDepartment',index:'toDepartment',align:'center',label : '<s:text name="reportPlan.toDepartment" />',hidden:false},{name:'toPublic',index:'toPublic',align:'center',label : '<s:text name="reportPlan.toPublic" />',hidden:false},{name:'toRole',index:'toRole',align:'center',label : '<s:text name="reportPlan.toRole" />',hidden:false},{name:'typeId',index:'typeId',align:'center',label : '<s:text name="reportPlan.typeId" />',hidden:false},{name:'userId',index:'userId',align:'center',label : '<s:text name="reportPlan.userId" />',hidden:false}        	],
        	jsonReader : {
				root : "reportPlans", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'planId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="reportPlanList.title" />',
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
           	var dataTest = {"id":"reportPlan_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("reportPlan_gridtable");
       		} 

    	});
    jQuery(reportPlanGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="reportPlan_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="reportPlan_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlan.planId'/>:
						<input type="text" name="filter_EQS_planId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlan.customLayout'/>:
						<input type="text" name="filter_EQS_customLayout"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlan.defineId'/>:
						<input type="text" name="filter_EQS_defineId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlan.planName'/>:
						<input type="text" name="filter_EQS_planName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlan.sysField'/>:
						<input type="text" name="filter_EQS_sysField"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlan.toDepartment'/>:
						<input type="text" name="filter_EQS_toDepartment"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlan.toPublic'/>:
						<input type="text" name="filter_EQS_toPublic"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlan.toRole'/>:
						<input type="text" name="filter_EQS_toRole"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlan.typeId'/>:
						<input type="text" name="filter_EQS_typeId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportPlan.userId'/>:
						<input type="text" name="filter_EQS_userId"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(reportPlan_search_form,reportPlan_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(reportPlan_search_form,reportPlan_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="reportPlan_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="reportPlan_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="reportPlan_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="reportPlan_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="reportPlan_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="reportPlan_gridtable_addTile">
				<s:text name="reportPlanNew.title"/>
			</label> 
			<label style="display: none"
				id="reportPlan_gridtable_editTile">
				<s:text name="reportPlanEdit.title"/>
			</label>
			<div id="load_reportPlan_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="reportPlan_gridtable"></table>
			<!--<div id="reportPlanPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="reportPlan_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="reportPlan_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="reportPlan_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>