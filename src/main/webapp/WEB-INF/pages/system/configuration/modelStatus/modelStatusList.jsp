
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var modelStatusLayout;
	var modelStatusGridIdString="#modelStatus_gridtable";
	
	jQuery(document).ready(function() { 
		var modelStatusGrid = jQuery(modelStatusGridIdString);
    	modelStatusGrid.jqGrid({
    		url : "modelStatusGridList",
    		editurl:"modelStatusGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="modelStatus.id" />',hidden:false,key:true},{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="modelStatus.checkperiod" />',hidden:false},{name:'modelId',index:'modelId',align:'center',label : '<s:text name="modelStatus.modelId" />',hidden:false},{name:'status',index:'status',align:'center',label : '<s:text name="modelStatus.status" />',hidden:false}        	],
        	jsonReader : {
				root : "modelStatuss", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'id',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="modelStatusList.title" />',
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
           	var dataTest = {"id":"modelStatus_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("modelStatus_gridtable");
       		} 

    	});
    jQuery(modelStatusGrid).jqGrid('bindKeys');
  	});
</script>

<div class="page">
	<div id="modelStatus_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="modelStatus_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='modelStatus.id'/>:
						<input type="text" name="filter_EQS_id"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='modelStatus.checkperiod'/>:
						<input type="text" name="filter_EQS_checkperiod"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='modelStatus.modelId'/>:
						<input type="text" name="filter_EQS_modelId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='modelStatus.status'/>:
						<input type="text" name="filter_EQS_status"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(modelStatus_search_form,modelStatus_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(modelStatus_search_form,modelStatus_gridtable)"><s:text name='button.search'/></button>
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
				<li><a id="modelStatus_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="modelStatus_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="modelStatus_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="modelStatus_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="modelStatus_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="modelStatus_gridtable_addTile">
				<s:text name="modelStatusNew.title"/>
			</label> 
			<label style="display: none"
				id="modelStatus_gridtable_editTile">
				<s:text name="modelStatusEdit.title"/>
			</label>
			<div id="load_modelStatus_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="modelStatus_gridtable"></table>
			<!--<div id="modelStatusPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="modelStatus_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="modelStatus_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="modelStatus_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>