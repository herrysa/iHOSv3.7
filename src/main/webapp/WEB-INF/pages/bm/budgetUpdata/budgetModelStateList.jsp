
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var bmmStateGridIdString="#bmmState_gridtable";
	
	jQuery(document).ready(function() { 
		var bmmStateChangeData = function(modelId) {
			//reloadTab(selectedSearchId);
			
			jQuery("#bmUpdata").load("budgetDepartmentList?modelId="+modelId);
		}
		var bmmStateLayout = makeLayout(
				{
					'baseName' : 'bmmState',
					'tableIds' : 'bmmState_gridtable;southTabs',
					'key' : 'bmmState',
					'proportion' : 2
				}, budgetModelChangeData);
		var bmmStateGrid = jQuery(bmmStateGridIdString);
		bmmStateGrid.jqGrid({
    		url : "bmmStateGridList",
    		editurl:"bmmStateGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'modelId',index:'modelId',align:'left',label : '<s:text name="budgetModel.modelId" />',width:100,hidden:false,key:true},
			{name:'modelCode',index:'modelCode',align:'left',label : '<s:text name="budgetModel.modelCode" />',width:100,hidden:false},
			{name:'modelName',index:'modelName',align:'left',label : '<s:text name="budgetModel.modelName" />',width:200,hidden:false},
			{name:'modelTypeTxt',index:'modelTypeTxt',align:'left',label : '<s:text name="budgetModel.modelType" />',width:100,hidden:false},
			{name:'periodType',index:'periodType',align:'left',label : '<s:text name="budgetModel.periodType" />',width:100,hidden:false},
			{name:'state',index:'state',align:'left',label : '<s:text name="budgetModel.state" />',width:200,hidden:false}
			],
        	jsonReader : {
				root : "budgetModels", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
			ondblClickRow:function(){
				bmmStateLayout.optDblclick();
			},
			onSelectRow: function(rowid) {
	        	setTimeout(function(){
	        		bmmStateLayout.optClick();
	        	},100);
	       	},
        	sortname: 'modelCode',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="budgetModelList.title" />',
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
           		gridContainerResize('bmmState','fullLayout');
           		var dataTest = {"id":"bmmState_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    jQuery("#budgetModel_gridtable_add_c").click(function(){
        var url = "editBudgetModel?navTabId=budgetModel_gridtable";
		var winTitle='<s:text name="budgetModelNew.title"/>';
		$.pdialog.open(url,'addBudgetModel',winTitle, {mask:true,width : 600,height : 400});
    });
  	});
</script>

<div class="page" id="bmmState_page">
	<div id="bmmState_container">
		<div id="bmmState_layout-center" class="pane ui-layout-center" style="padding: 2px">
			<div id="bmmState_pageHeader" class="pageHeader">
				<div class="searchBar">
					<div class="searchContent">
					<form id="bmmState_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.modelId'/>:
						<input type="text" name="filter_EQS_modelId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.modelCode'/>:
						<input type="text" name="filter_EQS_modelCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.modelName'/>:
						<input type="text" name="filter_EQS_modelName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.modelType'/>:
						<input type="text" name="filter_EQS_modelType"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.periodType'/>:
						<input type="text" name="filter_EQS_periodType"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.creator'/>:
						<input type="text" name="filter_EQS_creator"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.createDate'/>:
						<input type="text" name="filter_EQS_createDate"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.disabled'/>:
						<input type="text" name="filter_EQS_disabled"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.remark'/>:
						<input type="text" name="filter_EQS_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(budgetModel_search_form,budgetModel_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div id="budgetModel_buttonBar" class="panelBar">
			<ul class="toolBar">
				<li><a id="budgetModel_gridtable_add_c" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="budgetModel_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="budgetModel_gridtable_edit_c" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<li><a id="budgetModel_gridtable_editReport" class="changebutton"  href="javaScript:"
					><span>编辑模板
					</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="budgetModel_gridtable_div"  class="grid-wrapdiv" buttonBar="width:600;height:400">
			<input type="hidden" id="budgetModel_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="budgetModel_gridtable_addTile">
				<s:text name="budgetModelNew.title"/>
			</label> 
			<label style="display: none"
				id="budgetModel_gridtable_editTile">
				<s:text name="budgetModelEdit.title"/>
			</label>
			<div id="load_budgetModel_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="budgetModel_gridtable"></table>
			<!--<div id="budgetModelPager"></div>-->
		</div>
	</div>
	<div id="budgetModel_pageBar" class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="budgetModel_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="budgetModel_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="budgetModel_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	</div>
	<div id="budgetModel_layout-south" class="pane ui-layout-south" style="padding: 2px;overflow:hidden !important">
		<div class="panelBar">
			<ul class="toolBar">
				<li style="float: right;">
					<a id="budgetModel_close" class="closebutton" href="javaScript:"><span><fmt:message key="button.close" /></span></a>
				</li>
				<li class="line" style="float: right">line</li>
				<li style="float: right;">
					<a id="budgetModel_fold" class="foldbutton" href="javaScript:"><span><fmt:message key="button.fold" /></span></a>
				</li>
				<li class="line" style="float: right">line</li>
				<li style="float: right">
					<a id="budgetModel_unfold" class="unfoldbutton" href="javaScript:"><span><fmt:message key="button.unfold" /></span></a>
				</li>
			</ul>
		</div>
		<div id="budgetModel_department"></div>
	</div>
	</div>
	
</div>