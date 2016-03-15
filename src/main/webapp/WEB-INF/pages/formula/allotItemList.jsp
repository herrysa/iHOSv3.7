<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title><fmt:message key="allotItemList.title" />
</title>
<meta name="heading"
	content="<fmt:message key='allotItemList.heading'/>" />
<meta name="menu" content="AllotItemMenu" />
<script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
<script type="text/javascript">
	function refreshMasterGridCurrentPage() {
		var jq = jQuery('#allotItem_gridtable');
		var currentPage = jq.jqGrid('getGridParam', 'page');
		jQuery('#allotItem_gridtable').trigger('reloadGrid', [ {
			page : currentPage
		} ]);
	}

	function addRecord() {
		var url = "editAllotItem?popup=true";
		var winTitle = '<fmt:message key="allotItemNew.title"/>';
		openWindow(url, winTitle, " width=1000");
	}
	function editRecord() {
		var sid = jQuery("#allotItem_gridtable").jqGrid('getGridParam',
				'selarrrow');
		if (sid == null || sid.length == 0) {
			//alert("<fmt:message key='list.selectNone'/>");
			jQuery('div.#mybuttondialog').html(
					"<fmt:message key='list.selectNone'/>");
			jQuery('#mybuttondialog').dialog('open');
			return;
		}
		if (sid.length > 1) {
			//alert("<fmt:message key='list.selectMore'/>");
			jQuery('div.#mybuttondialog').html(
					"<fmt:message key='list.selectMore'/>");
			jQuery('#mybuttondialog').dialog('open');
			return;
		} else {
			jQuery("#gridinfo").html('<p>Loading..... ID : ' + sid + '</p>');
			var url = "editAllotItem?popup=true&allotItemId=" + sid;
			var winTitle = '<fmt:message key="allotItemNew.title"/>';
			openWindow(url, winTitle, " width=1000");
		}
	}
	function okButton() {
		jQuery('#mybuttondialog').dialog('close');
	};
	datePick = function(elem) {
		jQuery(elem).datepicker({
			dateFormat : "<fmt:message key='date.format'/>"
		});
		jQuery('#ui-datepicker-div').css("z-index", 2000);
	};

	var selectedSearchId = null;

	var allotItemLayout;
	/* var fullSize = jQuery("#container").innerHeight() + 7;
	var southSize = fullSize / 2;
	var isShowSubGrid = 0;
	var layoutSettings_allotItem = {
		applyDefaultStyles : true // basic styling for testing & demo purposes
		//  	,	south__size:				500
		//	,	south__minheight:			300
		,
		south__resizable : true // OVERRIDE the pane-default of 'resizable=true'
		,
		south__spacing_open : 0 // no resizer-bar when open (zero height)
		,
		south__spacing_closed : 0 // big resizer-bar when open (zero height)
		//,
		//south__maxHeight : southSize // 1/2 screen height
		,
		south__closable : true,
		south__size : southSize,
		onresize_end : "resizeGrid,allotItem_gridtable;allotItemDetail_gridtable"
	/*,
	south__initHidden:true
	};*/
	/*function reloadSubGrid(){
		var url = "allotItemDetailGridList?popup=true&allotItemId="
			+ selectedSearchId;
		jQuery("#allotItemDetail_gridtable").jqGrid('setGridParam',
			{
				url : url
			}).trigger("reloadGrid");
		$("#background,#progressBar").hide();
	} */
	/* function resizeGrid(paneName,element,state,options,layoutName,tableIds){
		var tableIdsArr = tableIds.split(";");
		/* var centerWidth = allotItemLayout.state.center.innerWidth;
		var centerHeight = allotItemLayout.state.center.innerHeight;
		var southWidth = allotItemLayout.state.south.innerWidth;
		var southHeight = allotItemLayout.state.south.innerHeight; 
		var width = state.innerWidth;
		var height = state.innerHeight;
		
		//jQuery("#allotItem_gridtable").jqGrid('setGridHeight',centerHeight-117);
		if(isShowSubGrid==1){
			width -= 25;
		}else{
			width -= 10;
		}
		if(paneName=="center"){
			jQuery("#"+tableIdsArr[0]).jqGrid('setGridWidth',width);
		}else{
			jQuery("#"+tableIdsArr[1]).jqGrid('setGridHeight',height-117);
			jQuery("#"+tableIdsArr[1]).jqGrid('setGridWidth',width-10);
		}
		
	} */
	jQuery(document).ready(
			function() {
				/* jQuery("#allotItem_container").css("height", fullSize);
				allotItemLayout = jQuery('#allotItem_container').layout(
						layoutSettings_allotItem);
				jQuery.subscribe('rowselect', function(event, data) {

					var row = jQuery("#allotItem_gridtable").jqGrid(
							'getRowData', event.originalEvent.id);
					selectedSearchId = row['allotItemId'];

					// alert("selectedSearchId is :" + selectedSearchId);
					//loadCurrentDetailTab(selectedSearchId);
					reloadSubGrid();
				});
				jQuery("#allotItem_layout-center").css("padding", 0);
				jQuery("#allotItem_layout-south").css("padding", 0);
				//loadCurrentDetailTab();

				jQuery("#allotItem_unfold").bind("click", function() {
					allotItemLayout.sizePane('south', fullSize);
				});
				jQuery("#allotItem_fold").bind("click", function() {
					allotItemLayout.sizePane('south', southSize);
				});
				jQuery("#allotItem_close").bind("click", function() {
					isShowSubGrid = 0;
					allotItemLayout.sizePane("south", southSize);
					allotItemLayout.toggle('south');
				});
				setTimeout("allotItemLayout.close('south')", "50"); */
				var allotItemChangeData = function(selectedSearchId){
									var url = "allotItemDetailGridList?popup=true&allotItemId="+selectedSearchId;
									//var gridHeight = jQuery("#allotItemDetail_gridtable").jqGrid('getGridParam','height');
									//jQuery("#asdf").text(gridHeight);
									jQuery("#allotItemDetail_gridtable").jqGrid('setGridParam',{url : url}).trigger("reloadGrid");
									//jQuery("#allotItemDetail_gridtable").jqGrid('setGridHeight', gridHeight); 
									$("#background,#progressBar").hide();
								 }
				allotItemLayout = makeLayout({'baseName':'allotItem','tableIds':'allotItem_gridtable;allotItemDetail_gridtable','proportion':2,'key':'allotItemId'},allotItemChangeData);
				allotItemLayout.resizeAll();
			});
	/* function toggle(){
		isShowSubGrid = 1;
		allotItemLayout.toggle('south');
	} */
	function inheritanceAllotItem(){
		var url = "eiditInheritanceAlloItem?navTabId=allotItem_gridtable";
		$.pdialog.open(url, 'inheritanceAllotItem', "继承历史分摊设置", {mask:false,width:500,height:300});　
	}
	
	jQuery(function() {
		jQuery("#allotItem_costItem").autocomplete(
				"autocomplete",
				{
					width : 200,
					multiple : false,
					autoFill : false,
					matchContains : true,
					matchCase : true,
					dataType : 'json',
					parse : function(test) {
						//alert(test.dicList.length)
						var data = test.autocompleteList;
						if (data == null || data == "") {
							var rows = [];
							rows[0] = {
								data : "没有结果",
								value : "",
								result : ""
							};
							return rows;
						} else {
							var rows = [];
							for ( var i = 0; i < data.length; i++) {
								rows[rows.length] = {
									data : data[i].costItemId + ","
											+ data[i].costItemName + ","
											+ data[i].cnCode + ":"
											+ data[i].costItemId,
									value : data[i].costItemName,
									result : data[i].costItemName
								};
							}
							return rows;
						}
					},
					extraParams : {
						flag : 2,
						entity : "CostItem",
						cloumnsStr : "costItemId,costItemName,cnCode",
						extra1 : " disabled=false and leaf=1 and (",
						extra2 : ")"
					},
					formatItem : function(row) {
						return dropId(row);
					},
					formatResult : function(row) {
						return dropId(row);
					}
				});
		jQuery("#allotItem_costItem").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#allotItem_costItem_id").attr("value", getId(row));
		});
	});

	jQuery(document).ready(function() {
		var initAllotItemFlag = 0;
		var allotItemGrid = jQuery("#allotItem_gridtable");
    	allotItemGrid.jqGrid({
    		url : "allotItemGridList",
    		editurl:"allotItemGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'allotItemId',index:'allotItemId',align:'left',width:80,label : '<s:text name="allotItem.allotItemId" />',hidden:false,key:true,highsearch:true},
{name:'allotSet.allotSetName',index:'allotSet.allotSetName',align:'left',width:150,label : '<s:text name="allotSet.allotSetName" />',hidden:false,highsearch:true},
{name:'checkPeriod',index:'checkPeriod',align:'left',width:70,label : '<s:text name="allotItem.checkPeriod" />',hidden:false,highsearch:true},       	
{name:'allotCost',index:'allotCost',align:'right',width:100,label : '<s:text name="allotItem.allotCost" />',hidden:false,formatter:"currency",formatoptions:"{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 2, prefix: '', suffix:'', defaultValue: '0.00'}",highsearch:true},
{name:'calcType',index:'calcType',align:'center',width:70,label : '<s:text name="allotItem.calcType" />',hidden:false,highsearch:true},       	
{name:'costType',index:'costType',align:'left',width:100,label : '<s:text name="allotItem.costType" />',hidden:false,highsearch:true},       	
{name:'costItem.costItemName',index:'costItem.costItemName',align:'left',width:170,label : '<s:text name="costItem.costItemName" />',hidden:false,highsearch:true},       	
<c:if test="${herpType == 'M2'}">
{name:'org.orgname',index:'org.orgname',align:'left',width:150,label : '<s:text name="hisOrg.orgName" />',hidden:false,highsearch:true},       	
</c:if>
<c:if test="${herpType == 'S2'}">
{name:'branch.branchName',index:'branch.branchName',align:'left',width:150,label : '<s:text name="hisOrg.branchName" />',hidden:false,highsearch:true},       	
</c:if>
{name:'department.name',index:'department.name',align:'left',width:150,label : '<s:text name="department.name" />',hidden:false,highsearch:true},       	
{name:'cjflag',index:'cjflag',align:'center',width:75,label : '<s:text name="allotItem.cjflag" />',hidden:false,highsearch:true,formatter:'checkbox'},       	
{name:'remark',index:'remark',align:'left',width:300,label : '<s:text name="allotItem.remark" />',hidden:false,highsearch:true}	
        	],jsonReader : {
				root : "allotItems", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'costType',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="allotItemList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
			ondblClickRow:function(){
				allotItemLayout.optDblclick();
			},
			onSelectRow: function(rowid) {
	        	setTimeout(function(){
	        		allotItemLayout.optClick();
	        	},100);
	       	},
		 	gridComplete:function(){
		 		gridContainerResize('allotItem','div');
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"allotItem_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("allotItem_gridtable");
      	  initAllotItemFlag = initColumn('allotItem_gridtable','com.huge.ihos.formula.model.AllotItem',initAllotItemFlag);
       		} 

    	});
    jQuery(allotItemGrid).jqGrid('bindKeys');
    
    /* if("${herpType}" != "S2") {
    	jQuery("#allotItem_branch_label").hide();
    } */
	});
</script>
</head>

<body>
	<div id="allotItem_page" class="page">
		<div id="allotItem_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<form id="allotItem_search_form" onsubmit="return navTabSearch(this);" action=""
						method="post" class="queryarea-form">
							<label class="queryarea-label">
								<fmt:message key="allotSet.allotSetName" />:
								<input type="text" name="filter_LIKES_allotSet.allotSetName" />
							</label>
							<label class="queryarea-label">
								<fmt:message key="allotItem.costType" />:
								<s:select list="#{'':'--','直接成本':'直接成本','管理成本':'管理成本','医辅成本':'医辅成本','医技成本':'医技成本'}" name="filter_EQS_costType"  listKey="key" listValue="value"
									emptyOption="false"  maxlength="50" width="50px"></s:select>
							</label>
							<label class="queryarea-label">
								<fmt:message key="costItem.costItemName" />:
								<s:hidden id="allotItem_costItem_id" name="filter_EQS_costItem.costItemId" />
								<input id="allotItem_costItem" value="拼音/汉字/编码" size="15"
									onfocus="clearInput(this,jQuery('#allotItem_costItem_id'))"
									class="input-medium defaultValueStyle" onblur="setDefaultValue(this,jQuery('#allotItem_costItem_id'))" onkeydown="setTextColor(this)"/>
							</label>
							<label class="queryarea-label">
								<fmt:message key="department.name" />
								<input type="text" name="filter_LIKES_department.name" />
							</label>
							<label class="queryarea-label" id="allotItem_branch_label" style="${herpType=='S2'?'':'display:none'}">
								<fmt:message key="hisOrg.branchName" />
								<s:select list="branches" headerKey="" headerValue="--" listKey="branchCode" listValue="branchName" name="filter_EQS_branch.branchCode"></s:select>
							</label>
							<div class="buttonActive" style="float:right">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('allotItem_search_form','allotItem_gridtable')"><s:text name='button.search'/></button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="pageContent">	
				<div class="panelBar" id="allotItem_buttonBar">
						<ul class="toolBar">
							<li><a id="allotItem_gridtable_add" class="addbutton"
								external="true" href="javaScript:"><span><fmt:message
											key="button.add" /> </span> </a></li>
							<li><a id="allotItem_gridtable_del" class="delbutton"
								external="true" href="javaScript:" title="确定要删除吗?"><span>删除</span>
							</a></li>
							<li><a id="allotItem_gridtable_edit" class="changebutton"
								external="true" href="javaScript:"><span><fmt:message
											key="button.edit" /> </span> </a></li>
							<li><a class="particularbutton"
								external="true" href="javaScript:allotItemLayout.optDblclick();"><span>明细</span> </a></li>
							<li><a class="inheritbutton"
								external="true" href="javaScript:inheritanceAllotItem();"><span>继承</span> </a></li>
							<li>
								<a class="settlebutton" href="javaScript:setColShow('allotItem_gridtable','com.huge.ihos.formula.model.AllotItem');"><span><s:text name="设置表格列" /></span></a>
							</li>
							<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
						</ul>
				</div>
				<div id="allotItem_container">
					<div id="allotItem_layout-center" class="pane ui-layout-center"
						style="padding: 2px">
					<div id="allotItem_gridtable_div" 
						class="grid-wrapdiv"
						buttonBar="width:650;height:450">
						<input type="hidden" id="allotItem_gridtable_navTabId"
							value="${sessionScope.navTabId}"> 
						<input type="hidden" id="allotItem_gridtable_isShowSouth"
							value="0"/>
						<label
							style="display: none" id="allotItem_gridtable_addTile"> <fmt:message key="allotItemNew.title"/> </label> <label style="display: none"
							id="allotItem_gridtable_editTile"> <fmt:message key="allotItemEdit.title"/> </label> <label style="display: none"
							id="allotItem_gridtable_selectNone"> <fmt:message
								key='list.selectNone' /> </label> <label style="display: none"
							id="allotItem_gridtable_selectMore"> <fmt:message
								key='list.selectMore' /> </label>
						<sj:dialog id="mybuttondialog"
							buttons="{'OK':function() { okButton(); }}" autoOpen="false"
							modal="true" title="%{getText('messageDialog.title')}" />
						
						<s:hidden name="currentPeriod" value="currentPeriod" />
						<s:url id="editurl" action="allotItemGridEdit" />
						<s:url id="remoteurl" action="allotItemGridList" escapeAmp="false">
							<s:param name="filter_GES_checkPeriod" value="%{currentPeriod}"></s:param>
							<s:param name="filter_LES_checkPeriod" value="%{currentPeriod}"></s:param>
						</s:url>
						<%-- <div align="left">
	<sj:submit id="add_button" value="%{getText('button.add')}" onClickTopics="addRowRecord" button="true" onclick="addRecord();"/>
	<sj:submit id="editSelectRow_button" value="%{getText('button.edit')}"  button="true" onclick="editRecord();"/>
</div> --%>
<div id="load_allotItem_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
<table id="allotItem_gridtable"></table>
						<%-- <sjg:grid id="allotItem_gridtable" dataType="json"
							gridModel="allotItems" href="%{remoteurl}" editurl="%{editurl}"
							rowList="10,15,20,30,40,50,60,70,80,90,100" rowNum="20"
							rownumbers="true" pager="false" page="1" pagerButtons="false"
							pagerInput="false" pagerPosition="right" navigator="false"
							navigatorAdd="false" navigatorEdit="false"
							navigatorDelete="false"
							navigatorDeleteOptions="{reloadAfterSubmit:true,left:screen.availWidth/4, top:screen.availHeight/4}"
							navigatorSearch="false"
							navigatorSearchOptions="{multipleSearch:true,  showQuery: true}"
							navigatorRefresh="false" multiselect="true" multiboxonly="true"
							resizable="true" height="500" autowidth="true" draggable="true"
							onclick="allotItemLayout.optClick()" 
							userDataOnFooter="false"
							footerrow="false" 
							onCompleteTopics="onLoadSelect" sortable="true" sortname="costType" sortorder="asc"
							ondblclick="allotItemLayout.optDblclick();">
							<sjg:gridColumn name="allotItemId" search="false"
								index="allotItemId" title="%{getText('allotItem.allotItemId')}"
								hidden="false" key="true" width="80" />
							<sjg:gridColumn name="allotSet.allotSetName"
								index="allotSet.allotSetName"
								title="%{getText('allotSet.allotSetName')}" />
							<sjg:gridColumn name="checkPeriod" index="checkPeriod"
								title="%{getText('allotItem.checkPeriod')}" sortable="true"
								search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
								width="80" />
							<sjg:gridColumn name="allotCost" index="allotCost"
								title="%{getText('allotItem.allotCost')}" sortable="true"
								search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
								align="right" formatter="currency"
								formatoptions="{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 2, prefix: '', suffix:'', defaultValue: '0.00'}" />
							<sjg:gridColumn name="calcType" index="calcType"
								title="%{getText('allotItem.calcType')}" sortable="true"
								search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
								width="90" />
							<sjg:gridColumn name="costType" index="costType"
								title="%{getText('allotItem.costType')}" sortable="true"
								search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
								width="90" />

							<sjg:gridColumn name="costItem.costItemName"
								index="costItem.costItemName"
								title="%{getText('costItem.costItemName')}" width="150" />
							<sjg:gridColumn name="org.orgname" index="org.orgname"
								title="%{getText('hisOrg.orgName')}" />
							<sjg:gridColumn name="branch.branchName" index="branch.branchName"
								title="%{getText('hisOrg.branchName')}" />
							<sjg:gridColumn name="department.name" index="department.name"
								title="%{getText('department.name')}" />
							<sjg:gridColumn name="cjflag" index="cjflag"
								title="%{getText('allotItem.cjflag')}" sortable="true"
								edittype="checkbox" formatter="checkbox" search="true"
								searchoptions="{sopt:['eq','ne']}" editrules="{required: true}"
								width="80" />
							<sjg:gridColumn name="remark" index="remark"
								title="%{getText('allotItem.remark')}" sortable="true"
								search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" /> --%>

					</div>
					<div id="allotItem_pageBar" class="panelBar">
						<div class="pages">
							<span>显示</span> <select id="allotItem_gridtable_numPerPage">
								<option value="20">20</option>
								<option value="50">50</option>
								<option value="100">100</option>
								<option value="200">200</option>
							</select> <span>条，共<label id="allotItem_gridtable_totals"></label>条</span>
						</div>

						<div id="allotItem_gridtable_pagination" class="pagination"
							targetType="navTab" totalCount="200" numPerPage="20"
							pageNumShown="10" currentPage="1"></div>

					</div>
				</div>

				<DIV id="allotItem_layout-south" class="pane ui-layout-south"
					style="padding: 2px">
					<s:url id="remoteurl1" action="allotItemDetailList?popup=true" />

					<a id="allotItemDetaile" rel="allotItemDetaile"></a>

					<div class="panelBar">
						<ul class="toolBar">
							<li><a id="allotItemDetail_gridtable_add" class="addbutton"
								external="true" href="javaScript:"><span><fmt:message
											key="button.add" /> </span> </a></li>
							<li><a id="allotItemDetail_gridtable_del" class="delbutton"
								external="true" href="javaScript:" title="确定要删除吗?"><span>删除</span>
							</a></li>
							<li><a id="allotItemDetail_gridtable_edit" class="changebutton"
								external="true" href="javaScript:"><span><fmt:message
											key="button.edit" /> </span> </a></li>
							<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
							<li style="float: right;"><a id="allotItem_close" class="closebutton"
								href="javaScript:"><span><fmt:message
											key="button.close" />
								</span>
							</a></li>

							<li class="line" style="float: right">line</li>
							<li style="float: right;"><a id="allotItem_fold" class="foldbutton"
								href="javaScript:"><span><fmt:message
											key="button.fold" />
								</span>
							</a></li>
							<li class="line" style="float: right">line</li>
							<li style="float: right"><a id="allotItem_unfold" class="unfoldbutton"
								href="javaScript:"><span><fmt:message
											key="button.unfold" />
								</span>
							</a></li>
						</ul>
					</div>
					<div id="allotItemDetail_gridtable_div"  extraHeight=147 tablecontainer="allotItem_layout-south"
						class="grid-wrapdiv"
						buttonBar="fatherGrid:allotItem_gridtable;extraParam:allotItemId;width:550;height:350">
						<input type="hidden" id="allotItemDetail_gridtable_navTabId"
							value="${sessionScope.navTabId}"> <label
							style="display: none" id="allotItemDetail_gridtable_addTile">
							<fmt:message key="allotItemDetailNew.title"/> </label> <label style="display: none"
							id="allotItemDetail_gridtable_editTile"> <fmt:message key="allotItemDetailEdit.title"/> </label> <label style="display: none"
							id="allotItemDetail_gridtable_selectNone"> <fmt:message
								key='list.selectNone' /> </label> <label style="display: none"
							id="allotItemDetail_gridtable_selectMore"> <fmt:message
								key='list.selectMore' /> </label>

						<sj:dialog id="mybuttondialog"
							buttons="{'OK':function() { okButton(); }}" autoOpen="false"
							modal="true" title="%{getText('messageDialog.title')}" />

						<s:url id="editurl" action="allotItemDetailGridEdit" />
						<s:url id="remoteurl" action="allotItemDetailGridList">
							<s:param name="allotItemId" value="#parameters.allotItemId"></s:param>

						</s:url>
						<%-- <div align="left">
	<sj:submit id="addDetail_button" value="%{getText('button.add')}" onClickTopics="addRowRecord" button="true" onclick="addDetailRecord();"/>
	<sj:submit id="editSelectRowDetail_button" value="%{getText('button.edit')}"  button="true" onclick="editDetailRecord();"/>
</div> --%>
<div id="load_allotItemDetail_gridtable" class='loading ui-state-default ui-state-active'></div>
						<sjg:grid id="allotItemDetail_gridtable" dataType="json"
							gridModel="allotItemDetails" href="%{remoteurl}"
							editurl="%{editurl}" rowList="10,15,20,30,40,50,60,70,80,90,100"
							rowNum="20" rownumbers="true" pager="false" page="1"
							pagerButtons="false" pagerInput="false" pagerPosition="right"
							navigator="false" navigatorAdd="false" navigatorEdit="false"
							navigatorDelete="false"
							navigatorDeleteOptions="{reloadAfterSubmit:true}"
							navigatorSearch="false"
							navigatorSearchOptions="{multipleSearch:true,  showQuery: true}"
							navigatorRefresh="false" multiselect="true" multiboxonly="true"
							resizable="true" draggable="true" autowidth="true"
							onCompleteTopics="onLoadSelect">
							<sjg:gridColumn name="allotItemDetailId" search="false"
								index="allotItemDetailId"
								title="%{getText('allotItemDetail.allotItemDetailId')}"
								hidden="true" formatter="integer" key="true" />

							<sjg:gridColumn name="costRatio" index="costRatio"
								title="%{getText('allotItemDetail.costRatiox')}" sortable="true"
								search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" />
							<sjg:gridColumn name="principle.allotPrincipleName"
								index="principle.allotPrincipleName"
								title="%{getText('allotItemDetail.principle')}" />
							<sjg:gridColumn name="bakPrinciple.allotPrincipleName"
								index="bakPrinciple.allotPrincipleName"
								title="%{getText('allotItemDetail.backPrinciple')}" />
							<sjg:gridColumn name="realPrinciple.allotPrincipleName"
								index="realPrinciple.allotPrincipleName"
								title="%{getText('allotItemDetail.realPrinciple')}" />

							<sjg:gridColumn name="remark" index="remark"
								title="%{getText('allotItemDetail.remark')}" sortable="true"
								search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" />
						</sjg:grid>
					</div>
					<div id="allotItemDetail_gridtable_footBar" class="panelBar">
						<div class="pages">
							<span>显示</span> <select id="allotItemDetail_gridtable_numPerPage">
								<option value="20">20</option>
								<option value="50">50</option>
								<option value="100">100</option>
								<option value="200">200</option>
							</select> <span>条，共<label id="allotItemDetail_gridtable_totals"></label>条</span>
						</div>

						<div id="allotItemDetail_gridtable_pagination" class="pagination"
							targetType="navTab" totalCount="200" numPerPage="20"
							pageNumShown="10" currentPage="1"></div>

					</div>
					<%-- <sj:tabbedpanel id="localtabs"  onChangeTopics="tabchange">
		<sj:tab id="tab1" href="%{remoteurl1}"
			label="%{getText('allotItemDetailList.title')}"  />
	</sj:tabbedpanel> --%>

				</DIV>
			</div>
		</div>
	</div>
</body>
</html>