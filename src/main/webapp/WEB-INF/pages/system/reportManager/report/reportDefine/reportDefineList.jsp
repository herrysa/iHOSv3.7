
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var reportDefineLayout;
	var reportDefineGridIdString="#reportDefine_gridtable";
	
	jQuery(document).ready(function() { 
		var reportDefineGrid = jQuery(reportDefineGridIdString);
    	reportDefineGrid.jqGrid({
    		url : "reportDefineGridList",
    		editurl:"reportDefineGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'defineId',index:'defineId',align:'center',label : '<s:text name="reportDefine.defineId" />',hidden:false,key:true},{name:'configurable',index:'configurable',align:'center',label : '<s:text name="reportDefine.configurable" />',hidden:false,formatter:'checkbox'},{name:'defineName',index:'defineName',align:'center',label : '<s:text name="reportDefine.defineName" />',hidden:false},{name:'displayType',index:'displayType',align:'center',label : '<s:text name="reportDefine.displayType" />',hidden:false},{name:'filters',index:'filters',align:'center',label : '<s:text name="reportDefine.filters" />',hidden:false},{name:'groups',index:'groups',align:'center',label : '<s:text name="reportDefine.groups" />',hidden:false},{name:'noItems',index:'noItems',align:'center',label : '<s:text name="reportDefine.noItems" />',hidden:false},{name:'remark',index:'remark',align:'center',label : '<s:text name="reportDefine.remark" />',hidden:false},{name:'requiredFilter',index:'requiredFilter',align:'center',label : '<s:text name="reportDefine.requiredFilter" />',hidden:false},{name:'requiredItems',index:'requiredItems',align:'center',label : '<s:text name="reportDefine.requiredItems" />',hidden:false},{name:'reverseColumn',index:'reverseColumn',align:'center',label : '<s:text name="reportDefine.reverseColumn" />',hidden:false},{name:'subSystem',index:'subSystem',align:'center',label : '<s:text name="reportDefine.subSystem" />',hidden:false}        	],
        	jsonReader : {
				root : "reportDefines", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'defineId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="reportDefineList.title" />',
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
           	var dataTest = {"id":"reportDefine_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("reportDefine_gridtable");
       		} 

    	});
    jQuery(reportDefineGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="reportDefine_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="reportDefine_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDefine.defineId'/>:
						<input type="text" name="filter_EQS_defineId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDefine.configurable'/>:
						<input type="text" name="filter_EQS_configurable"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDefine.defineName'/>:
						<input type="text" name="filter_EQS_defineName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDefine.displayType'/>:
						<input type="text" name="filter_EQS_displayType"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDefine.filters'/>:
						<input type="text" name="filter_EQS_filters"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDefine.groups'/>:
						<input type="text" name="filter_EQS_groups"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDefine.noItems'/>:
						<input type="text" name="filter_EQS_noItems"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDefine.remark'/>:
						<input type="text" name="filter_EQS_remark"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDefine.requiredFilter'/>:
						<input type="text" name="filter_EQS_requiredFilter"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDefine.requiredItems'/>:
						<input type="text" name="filter_EQS_requiredItems"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDefine.reverseColumn'/>:
						<input type="text" name="filter_EQS_reverseColumn"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='reportDefine.subSystem'/>:
						<input type="text" name="filter_EQS_subSystem"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(reportDefine_search_form,reportDefine_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(reportDefine_search_form,reportDefine_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="reportDefine_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="reportDefine_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="reportDefine_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="reportDefine_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="reportDefine_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="reportDefine_gridtable_addTile">
				<s:text name="reportDefineNew.title"/>
			</label> 
			<label style="display: none"
				id="reportDefine_gridtable_editTile">
				<s:text name="reportDefineEdit.title"/>
			</label>
			<div id="load_reportDefine_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="reportDefine_gridtable"></table>
			<!--<div id="reportDefinePager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="reportDefine_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="reportDefine_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="reportDefine_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>