
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var budgetModelLayout;
	var budgetModelGridIdString="#budgetModel_gridtable";
	
	jQuery(document).ready(function() { 
		var budgetModelChangeData = function(modelId) {
			//reloadTab(selectedSearchId);
			
			jQuery("#budgetModel_department").load("budgetDepartmentList?modelId="+modelId);
		}
		var budgetModelLayout = makeLayout(
				{
					'baseName' : 'budgetModel',
					'tableIds' : 'budgetModel_gridtable;southTabs',
					'key' : 'budgetModel',
					'proportion' : 2
				}, budgetModelChangeData);
		var budgetModelGrid = jQuery(budgetModelGridIdString);
    	budgetModelGrid.jqGrid({
    		url : "budgetModelGridList",
    		editurl:"budgetModelGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'modelId',index:'modelId',align:'left',label : '<s:text name="budgetModel.modelId" />',width:100,hidden:false,key:true},
			{name:'modelCode',index:'modelCode',align:'left',label : '<s:text name="budgetModel.modelCode" />',width:100,hidden:false},
			{name:'modelName',index:'modelName',align:'left',label : '<s:text name="budgetModel.modelName" />',width:200,hidden:false},
			{name:'modelTypeTxt',index:'modelTypeTxt',align:'left',label : '<s:text name="budgetModel.modelType" />',width:100,hidden:false},
			{name:'periodType',index:'periodType',align:'left',label : '<s:text name="budgetModel.periodType" />',width:100,hidden:false},
			{name:'creator',index:'creator',align:'left',label : '<s:text name="budgetModel.creator" />',width:100,hidden:false},
			{name:'createDate',index:'createDate',align:'left',label : '<s:text name="budgetModel.createDate" />',width:100,hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'}},
			{name:'modifier',index:'modifier',align:'left',label : '<s:text name="budgetModel.modifier" />',width:100,hidden:false},
			{name:'modifydate',index:'modifydate',align:'left',label : '<s:text name="budgetModel.modifydate" />',width:100,hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'}},
			{name:'disabled',index:'disabled',align:'center',label : '<s:text name="budgetModel.disabled" />',width:100,hidden:false,formatter:'checkbox'},
			{name:'remark',index:'remark',align:'left',label : '<s:text name="budgetModel.remark" />',width:200,hidden:false}
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
				budgetModelLayout.optDblclick();
			},
			onSelectRow: function(rowid) {
	        	setTimeout(function(){
	        		budgetModelLayout.optClick();
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
           		gridContainerResize('budgetModel','fullLayout');
           		var dataTest = {"id":"budgetModel_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
       		} 

    	});
    jQuery(budgetModelGrid).jqGrid('bindKeys');
    jQuery("#budgetModel_gridtable_add_c").click(function(){
        var url = "editBudgetModel?navTabId=budgetModel_gridtable";
		var winTitle='<s:text name="budgetModelNew.title"/>';
		$.pdialog.open(url,'addBudgetModel',winTitle, {mask:true,width : 630,height : 450});
    });
    jQuery("#budgetModel_gridtable_edit_c").click(function(){
		var sid = jQuery("#budgetModel_gridtable").jqGrid('getGridParam','selarrrow');
		if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
		}
        var url = "editBudgetModel?navTabId=budgetModel_gridtable&modelId="+sid;
		var winTitle='<s:text name="budgetModelNew.title"/>';
		$.pdialog.open(url,'editBudgetModel',winTitle, {mask:true,width : 630,height : 450});
    });
    jQuery("#budgetModel_gridtable_editReport").click(function(){
    	var fullHeight = jQuery("#container").innerHeight();
    	var fullWidth = jQuery("#container").innerWidth();
    	var sid = jQuery("#budgetModel_gridtable").jqGrid('getGridParam','selarrrow');
    	var url = "editBudgetModelReport?modelId="+sid;
    	url = encodeURI(url);
    	$.pdialog.open(url, 'editBudgetReport', "编辑预算报表", {
    		mask : true,
    		maxable : true,
    		resizable : true,
    		width : fullWidth,
    		height : fullHeight
    	});
    });
  	});
</script>

<div class="page" id="budgetModel_page">
	<div id="budgetModel_container">
				<div id="budgetModel_layout-center" class="pane ui-layout-center" style="padding: 2px">
	<div id="budgetModel_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="budgetModel_search_form" >
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
						<s:select list="modelTypeList" key="filter_EQS_modelType" listKey="displayContent" listValue="value" headerKey="" headerValue="--" theme="simple"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.periodType'/>:
						<s:select list="#{'月度':'月度','季度':'季度','半年':'半年','年度':'年度'}" name="filter_EQS_periodType" headerKey="" headerValue="--" theme="simple"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.creator'/>:
						<input type="text" name="filter_LIKES_creator"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.createDate'/>:
						从<input type="text" name="filter_GED_createDate" class="input-mini" type="text" 
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
									value="" size="8"/>
						到<input type="text" name="filter_LED_createDate" class="input-mini" type="text" 
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
									value="" size="8"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.modifier'/>:
						<input type="text" name="filter_EQS_modifier"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.modifydate'/>:
						<input type="text" name="filter_EQS_modifydate"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.disabled'/>:
						<s:select list="#{'true':'是','false':'否'}" name="filter_EQS_disabled" headerKey="" headerValue="--" theme="simple"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='budgetModel.remark'/>:
						<input type="text" name="filter_EQS_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('budgetModel_search_form','budgetModel_gridtable')"><s:text name='button.search'/></button>
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
				<li><a id="budgetModel_gridtable_editReport" class="reportbutton"  href="javaScript:"
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