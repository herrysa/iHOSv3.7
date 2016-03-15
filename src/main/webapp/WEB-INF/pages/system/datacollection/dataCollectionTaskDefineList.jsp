<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="dataCollectionTaskDefineList.title" />
</title>
<meta name="heading"
	content="<fmt:message key='dataCollectionTaskDefineList.heading'/>" />
<meta name="menu" content="DataCollectionTaskDefineMenu" />

<script type="text/javascript">
function stringFormatter (cellvalue, options, rowObject)	{
	return cellvalue.replace(/\r\n/g, "").replace(/\n/g, "");
}
	function refreshMasterGridCurrentPage() {
		var jq = jQuery('#dataCollectionTaskDefine_gridtable');
		var currentPage = jq.jqGrid('getGridParam', 'page');
		jQuery('#dataCollectionTaskDefine_gridtable').trigger('reloadGrid', [ {
			page : currentPage
		} ]);
	}
	function refreshGridCurrentPage() {
		var jq = jQuery('#dataCollectionTaskStepDefine_gridtable');
		var currentPage = jq.jqGrid('getGridParam', 'page');
		jQuery('#dataCollectionTaskStepDefine_gridtable').trigger('reloadGrid', [ {
			page : currentPage
		} ]);
	}
	function addRecord() {
		var url = "editDataCollectionTaskDefine?popup=true";
		var winTitle = '<fmt:message key="dataCollectionTaskDefineNew.title"/>';
		popUpWindow(url, winTitle, "width=500");
	}
	function editRecord() {
		var sid = jQuery("#dataCollectionTaskDefine_gridtable").jqGrid(
				'getGridParam', 'selarrrow');
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
			var url = "editDataCollectionTaskDefine?popup=true&dataCollectionTaskDefineId="
					+ sid;
			var winTitle = '<fmt:message key="dataCollectionTaskDefineNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
	}
	function okButton() {
		jQuery('#mybuttondialog').dialog('close');
	};

	function addStepRecord(){
		var  s = jQuery("#dataCollectionTaskDefine_gridtable").jqGrid('getGridParam','selarrrow');
		
		var url = "editDataCollectionTaskStep?popup=true&parentId="+s;
		var winTitle='<fmt:message key="dataCollectionTaskStepNew.title"/>';
		//popUpWindow(url, winTitle, "width=500");
		$.pdialog.open(url, 'addDataTaskStepDefine', winTitle, {mask:false,width:580,height:630});　
	}
	function editStepRecord(){
        var sid = jQuery("#dataCollectionTaskStepDefine_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null || sid.length ==0){
			//alert("<fmt:message key='list.selectNone'/>");
			jQuery('div.#stepMsgDialog').html("<fmt:message key='list.selectNone'/>");
			jQuery('#stepMsgDialog').dialog('open');
			return;
			}
        if(sid.length>1){
			  //alert("<fmt:message key='list.selectMore'/>");
		  jQuery('div.#stepMsgDialog').html("<fmt:message key='list.selectMore'/>");
		  jQuery('#stepMsgDialog').dialog('open');
			return;
		  }else{
         jQuery("#gridinfo").html('<p>Loading..... ID : '+sid+'</p>');
			var url = "editDataCollectionTaskStep?popup=true&stepId=" + sid;
			var winTitle='<fmt:message key="dataCollectionTaskStepNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
	}
	function okStepButton() {
		jQuery('#stepMsgDialog').dialog('close');
	};
	function setDataCollectionTaskDefineModel(postdata, formid) {
		//alert("beforesubmit");
		postdata['model.dataCollectionTaskDefineId'] = postdata['dataCollectionTaskDefineId'];
		postdata['model.dataSourceDefine'] = postdata['dataSourceDefine'];
		postdata['model.isUsed'] = postdata['isUsed'];
		postdata['model.targetTableName'] = postdata['targetTableName'];
		postdata['model.temporaryTableName'] = postdata['temporaryTableName'];
		return [ true, "" ];
	}

	function checkGridOperation(response, postdata) {
		var gridresponse = gridresponse || {};
		gridresponse = jQuery.parseJSON(response.responseText);
		var msg = gridresponse["gridOperationMessage"];
		// alert(msg);
		jQuery("#gridinfo").html(msg);
		return [ true, "" ];
	}

	datePick = function(elem) {
		jQuery(elem).datepicker({
			dateFormat : "<fmt:message key='date.format'/>"
		});
		jQuery('#ui-datepicker-div').css("z-index", 2000);
	};
	/* jQuery.subscribe('rowselect', function(event, data) {
		var row = jQuery("#dataCollectionTaskDefine_gridtable").jqGrid(
				'getRowData', event.originalEvent.id);
		var ids = row['dataCollectionTaskDefineId'];
		//   			alert("fdc come here.")
		jQuery("#dataCollectionTaskStepDefine_gridtable").jqGrid('setGridParam', {
			url : "dataCollectionTaskStepGridList?parentId=" + ids
		});
		jQuery("#dataCollectionTaskStepDefine_gridtable")
		//.jqGrid('setCaption',"Invoice Detail: "+ids)
		.trigger('reloadGrid');

	}); */
	/* function resizeGrid(){
		jQuery('#dataCollectionTaskDefine_gridtable').jqGrid('setGridHeight',jQuery(document).height()-185);
	} */
	var dataCollectionTaskDefineLayout;
	jQuery(document).ready(function() {
						//setTimeout("resizeGrid()","100");
						/* setTimeout(
								"jQuery('#dataCollectionTaskStepDefine_gridtable_layout').css('display','none')",
								"50"); */
		var changeData = function(
				selectedSearchId) {
			var url = "dataCollectionTaskStepGridList?parentId="
					+ selectedSearchId;
			//alert(url);
			jQuery("#dataCollectionTaskStepDefine_gridtable").jqGrid(
					'setGridParam', {
						url : url
					}).trigger("reloadGrid");
			$("#background,#progressBar").hide();
		}
		dataCollectionTaskDefineLayout = makeLayout({
			'baseName' : 'dataCollectionTaskDefine', 
			'proportion' : 2,
			'tableIds': 'dataCollectionTaskDefine_gridtable;dataCollectionTaskStepDefine_gridtable',
			'key' : 'dataCollectionTaskDefineId'
		}, changeData);
		
		//复制
		jQuery("#dataCollectionTaskDefine_gridtable_copy_custom").click(function() {
			var sid = jQuery("#dataCollectionTaskDefine_gridtable").jqGrid("getGridParam","selarrrow");
			if (sid == null || sid.length == 0) {
				alertMsg.error("<fmt:message key='list.selectNone'/>");
				return;
			}
			if (sid.length > 1) {
				alertMsg.error("<fmt:message key='list.selectMore'/>");
				return;
			} else {
				var jq = jQuery('#dataCollectionTaskStepDefine_gridtable');
				var currentPage = jq.jqGrid('getGridParam', 'page');
				alertMsg.confirm("确认复制？",{
					okCall:function() {
						jQuery.ajax({
							url:'copyDataCollectionTaskDefine?id='+sid[0],
							dataType:'json',
							type:'get',
							success:function(data) {
								if(data.statusCode == 200) {
									alertMsg.correct("复制成功！");
									jQuery("#dataCollectionTaskDefine_gridtable").jqGrid('setGridParam', {page : currentPage}).trigger("reloadGrid");
								} else {
									alertMsg.error(data.message);
								}
							}
						});
					}
				});
			}
		});
	});

	/* var fullHeight;
	var subGridHright;
	//var subGridHright;
	var toggle = 0; */
	/*
	 * 重置pane的方法,使用处：展开、折叠
	 */
	/* function reSizePane(openPane,opt,closePane){
		if(opt==0){
			//defineLayout.sizePane(pane,southSize);
			jQuery("#"+closePane+"_layout").css("display","block");
			jQuery("#"+openPane+"_layout").css("height",subGridHright);
			jQuery("#"+openPane+"_div").css("height",subGridHright-85);
			jQuery('#'+openPane).jqGrid("setGridHeight",subGridHright-120);
			//jQuery("#"+pan).css("display","block");
			jQuery("#centerBar").css("display","block");
		}else{
			//defineLayout.sizePane(pane,size);
			jQuery('#'+openPane+"_layout").css("height",fullHeight+25);
			jQuery('#'+openPane+"_div").css("height",fullHeight-30);
			jQuery('#'+openPane).jqGrid("setGridHeight",fullHeight-80);
			jQuery("#"+closePane+"_layout").css("display","none");
			jQuery("#centerBar").css("display","none");
		}
		
	} */
	/*
	 * 关闭pane的方法，使用处：关闭
	 */
	/* function closeSouth(closePane,openPane){
		//defineLayout.sizePane("south",southSize);
		//defineLayout.toggle('south');
		jQuery("#"+closePane+"_layout").css("display","none");
		jQuery("#"+openPane+"_layout").css("height",fullHeight);
		jQuery("#"+openPane+"_div").css("height",fullHeight-30);
		jQuery("#"+openPane).jqGrid("setGridHeight",fullHeight-60);
		//jQuery("#search_gridtable").jqGrid('setGridHeight', fullHeight-50);
	} */
	
	/* function togglePane(openPane,narrowPane){
		//jQuery("#taskRemain").css("display","none");
		//jQuery("#dataCollectionRemainTask_gridtable").jqGrid('setGridParam',{height:500}).trigger("reloadGrid");});
		if(jQuery("#"+openPane+"_layout").css("display")=="block"){
			jQuery("#"+openPane+"_layout").css("display","none");
			jQuery("#"+narrowPane+"_layout").css("height",fullHeight);
			jQuery("#"+narrowPane+"_div").css("height",fullHeight-30);
			jQuery("#"+narrowPane).jqGrid("setGridHeight",fullHeight-60);
		}else{
			fullHeight=jQuery("#"+narrowPane+"_layout").height();
			subGridHright=jQuery("#dataCollectionTaskDefineContent").height() / 2;
			jQuery('#'+openPane).jqGrid("setGridHeight",subGridHright-150);
			jQuery("#"+openPane+"_layout").css("height",subGridHright);
			jQuery("#"+openPane+"_div").css("height",subGridHright-85);
			jQuery("#"+narrowPane+"_layout").css("height",subGridHright);
			jQuery('#'+narrowPane).jqGrid("setGridHeight",subGridHright-60);
			jQuery("#"+narrowPane+"_div").css("height",subGridHright-30);
			//alert(jQuery("#"+narrowPane).css("height"));
			//jQuery("#search_gridtable").jqGrid('setGridHeight', subGridHright-55);
			
			jQuery("#"+openPane+"_layout").css("display","block");
		}
	} */
	/* function unfoldbutton(){
		jQuery("#dataCollectionTaskStepDefine_gridtable").jqGrid('setGridHeight',100);
	} */
</script>



</head>
<div class="page">
	<div id="dataCollectionTaskDefineContent" class="pageContent">
		<div id="dataCollectionTaskDefine_container">
			<div id="dataCollectionTaskDefine_layout-center"
				class="pane ui-layout-center">
				<div id="centerBar" class="panelBar">
					<ul class="toolBar">
						<li><a id="dataCollectionTaskDefine_gridtable_add"
							class="addbutton" href="javaScript:"><span><fmt:message
										key="button.add" /> </span> </a>
						</li>
						<li><a id="dataCollectionTaskDefine_gridtable_del"
							class="delbutton" href="javaScript:"><span>删除</span>
						</a>
						</li>
						<li><a id="dataCollectionTaskDefine_gridtable_edit"
							class="changebutton" href="javaScript:"><span><fmt:message
										key="button.edit" /> </span> </a>
						</li>
						<li><a id="dataCollectionTaskDefine_gridtable_copy_custom"
							class="insertrowbutton" href="javaScript:"><span>复制</span>
						</a>
						</li>
						<li><a id="dataCollectionTaskDefine_gridtable_edit"
							class="particularbutton"
							href="javaScript:dataCollectionTaskDefineLayout.optDblclick();"><span>明细</span>
						</a>
						</li>
						<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
					</ul>
				</div>
				<!-- <div id="dataCollectionTaskDefine_gridtable_layout" class="layoutBox" layoutH="27"> -->
				<sj:dialog id="mybuttondialog"
					buttons="{'OK':function() { okButton(); }}" autoOpen="false"
					modal="true" title="%{getText('messageDialog.title')}" />

				<s:url id="editurl" action="dataCollectionTaskDefineGridEdit" />
				<s:url id="remoteurl" action="dataCollectionTaskDefineGridList" />
				<div id="dataCollectionTaskDefine_gridtable_div" layoutH="57"
					class="grid-wrapdiv"
					buttonBar="width:600;height:400">
					<input type="hidden"
						id="dataCollectionTaskDefine_gridtable_navTabId"
						value="${sessionScope.navTabId}"> 
					<input type="hidden" id="dataCollectionTaskDefine_gridtable_isShowSouth"
						value="0"> 
					<label
						style="display: none"
						id="dataCollectionTaskDefine_gridtable_addTile"> <fmt:message
							key="dataCollectionTaskDefineNew.title" /> </label> <label
						style="display: none"
						id="dataCollectionTaskDefine_gridtable_editTile"> <fmt:message
							key="dataCollectionTaskDefineEdit.title" /> </label> <label
						style="display: none"
						id="dataCollectionTaskDefine_gridtable_selectNone"> <fmt:message
							key='list.selectNone' /> </label> <label style="display: none"
						id="dataCollectionTaskDefine_gridtable_selectMore"> <fmt:message
							key='list.selectMore' /> </label>
					<div id="load_dataCollectionTaskDefine_gridtable" class='loading ui-state-default ui-state-active'></div>
					<sjg:grid id="dataCollectionTaskDefine_gridtable" dataType="json"
						gridModel="dataCollectionTaskDefines" href="%{remoteurl}"
						editurl="%{editurl}" rowList="10,15,20,30,40,50,60,70,80,90,100"
						rowNum="20" rownumbers="true" pager="false" page="1"
						pagerButtons="false" pagerInput="false" pagerPosition="right"
						navigator="false" navigatorAdd="false" navigatorEdit="false"
						navigatorDelete="false"
						navigatorDeleteOptions="{reloadAfterSubmit:true,left:screen.availWidth/4, top:screen.availHeight/4}"
						navigatorSearch="false"
						navigatorSearchOptions="{multipleSearch:true,  showQuery: true,left:screen.availWidth/4, top:screen.availHeight/4}"
						navigatorRefresh="false" multiselect="true" multiboxonly="true"
						resizable="false" onclick="dataCollectionTaskDefineLayout.optClick();" draggable="false"
						shrinkToFit="true"   autowidth="true" height="300" onCompleteTopics="onLoadSelect"
						ondblclick="dataCollectionTaskDefineLayout.optDblclick();"
						sortable="true" sortname="taskNo" sortorder="asc">
						<sjg:gridColumn name="dataCollectionTaskDefineId" search="false"
							index="dataCollectionTaskDefineId"
							title="%{getText('dataCollectionTaskDefine.dataCollectionTaskDefineId')}"
							hidden="true" formatter="integer" sortable="false" key="true" />
						<sjg:gridColumn name="dataSourceDefine.dataSourceName"
							index="dataSourceDefine.dataSourceName"
							title="%{getText('dataSourceDefine.dataSourceName')}"
							sortable="true" search="true"
							searchoptions="{sopt:['eq','ne','cn','bw']}" width="120" />
						<sjg:gridColumn
							name="dataSourceDefine.dataSourceType.dataSourceTypeName"
							index="dataSourceDefine.dataSourceType.dataSourceTypeName"
							title="%{getText('dataSourceType.dataSourceTypeName')}"
							sortable="true" search="true"
							searchoptions="{sopt:['eq','ne','cn','bw']}" width="100" />
						<sjg:gridColumn name="dataCollectionTaskDefineName"
							index="dataCollectionTaskDefineName"
							title="%{getText('dataCollectionTaskDefine.dataCollectionTaskDefineName')}"
							sortable="true" search="true"
							searchoptions="{sopt:['eq','ne','cn','bw']}" width="150" />
						<sjg:gridColumn name="deptName"
							index="deptName"
							title="%{getText('dataCollectionTaskDefine.dept')}"
							sortable="true" search="true"
							searchoptions="{sopt:['eq','ne','cn','bw']}" width="100" />
						<sjg:gridColumn name="taskNo" index="taskNo"
							title="%{getText('dataCollectionTaskDefine.taskNo')}"
							sortable="true" search="true"
							searchoptions="{sopt:['eq','ne','cn','bw']}" width="70" />

						<sjg:gridColumn name="temporaryTableName"
							index="temporaryTableName"
							title="%{getText('dataCollectionTaskDefine.temporaryTableName')}"
							sortable="true" search="true"
							searchoptions="{sopt:['eq','ne','cn','bw']}" width="100" />

						<sjg:gridColumn name="targetTableName" index="targetTableName"
							title="%{getText('dataCollectionTaskDefine.targetTableName')}"
							sortable="true" search="true"
							searchoptions="{sopt:['eq','ne','cn','bw']}" width="100" />

						<sjg:gridColumn name="remark" index="remark"
							title="%{getText('dataCollectionTaskDefine.remark')}"
							sortable="true" search="true"
							searchoptions="{sopt:['eq','ne','cn','bw']}" width="200" />
						<sjg:gridColumn name="isUsed" index="isUsed"
							title="%{getText('dataCollectionTaskDefine.isUsed')}"
							sortable="true" edittype="checkbox" formatter="checkbox"
							width="80" search="true" searchoptions="{sopt:['eq','ne']}" />

					</sjg:grid>
				</div>
				<div id="dataCollectionTaskDefine_gridtable_footBar"
					class="panelBar">
					<div class="pages">
						<span>显示</span> <select id="dataCollectionTaskDefine_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span>条，共<label
							id="dataCollectionTaskDefine_gridtable_totals"></label>条</span>
					</div>

					<div id="dataCollectionTaskDefine_gridtable_pagination"
						class="pagination" targetType="navTab" totalCount="200"
						numPerPage="20" pageNumShown="10" currentPage="1"></div>

				</div>
			</div>

			<DIV id="dataCollectionTaskDefine_layout-south"
				class="pane ui-layout-south">
				<sj:dialog id="stepMsgDialog"
					buttons="{'OK':function() { okStepButton(); }}" autoOpen="false"
					modal="true" title="%{getText('messageDialog.title')}" />

				<s:url id="stepEditurl" action="dataCollectionTaskStepGridEdit" />
				<s:url id="stepRemoteurl" action="dataCollectionTaskStepGridList" />

				<div class="panelBar">
					<ul class="toolBar">
						<li><a id="dataCollectionTaskStepDefine_gridtable_add" class="add" href="javaScript:"><span><fmt:message
										key="button.add" /> </span> </a>
						</li>
						<li><a id="dataCollectionTaskStepDefine_gridtable_del"
							class="delete" href="javaScript:"><span>删除</span> </a>
						</li>
						<li><a id="dataCollectionTaskStepDefine_gridtable_edit"
							class="edit" href="javaScript:"><span><fmt:message
										key="button.edit" /> </span> </a>
						</li>
						<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
						<li style="float: right;"><a id="dataCollectionTaskDefine_close" class="closebutton"
							href="javaScript:"><span><fmt:message
										key="button.close" /> </span> </a>
						</li>

						<li class="line" style="float: right">line</li>
						<li style="float: right;"><a id="dataCollectionTaskDefine_fold" class="foldbutton"
							href="javaScript:"><span><fmt:message
										key="button.fold" /> </span> </a>
						</li>
						<li class="line" style="float: right">line</li>
						<li style="float: right"><a id="dataCollectionTaskDefine_unfold" class="unfoldbutton"
							href="javaScript:"><span><fmt:message
										key="button.unfold" /> </span> </a>
						</li>
					</ul>
				</div>
				<div id="dataCollectionTaskStepDefine_gridtable_div" extraHeight=83 tablecontainer="dataCollectionTaskDefine_layout-south"
					class="grid-wrapdiv" 
					buttonBar="base_URL:editDataCollectionTaskStep;optId:stepId;fatherGrid:dataCollectionTaskDefine_gridtable;extraParam:parentId;width:580;height:630">
					<input type="hidden"
						id="dataCollectionTaskStepDefine_gridtable_navTabId"
						value="${sessionScope.navTabId}"> 
					<label style="display: none"
						id="dataCollectionTaskStepDefine_gridtable_addTile"> 
						<fmt:message key="dataCollectionTaskStepNew.title"/> </label> <label
						style="display: none"
						id="dataCollectionTaskStepDefine_gridtable_editTile"> 
						<fmt:message key="dataCollectionTaskStepEdit.title"/> </label> <label
						style="display: none"
						id="dataCollectionTaskStepDefine_gridtable_selectNone"> <fmt:message
							key='list.selectNone' /> </label> <label style="display: none"
						id="dataCollectionTaskStepDefine_gridtable_selectMore"> <fmt:message
							key='list.selectMore' /> </label>
					<div id="load_dataCollectionTaskStepDefine_gridtable" class='loading ui-state-default ui-state-active'></div>
					<sjg:grid id="dataCollectionTaskStepDefine_gridtable" dataType="json"
						gridModel="dataCollectionTaskSteps" editurl="%{stepEditurl}"
						rowList="10,15,20,30,40,50,60,70,80,90,100" rowNum="20"
						rownumbers="true" pager="false" page="1" pagerButtons="false"
						pagerInput="false" pagerPosition="right" navigator="false"
						navigatorAdd="false" navigatorEdit="false" navigatorDelete="false"
						navigatorDeleteOptions="{reloadAfterSubmit:true,afterSubmit:checkGridOperation}"
						navigatorSearch="false"
						navigatorSearchOptions="{multipleSearch:true,  showQuery: true}"
						navigatorRefresh="false" multiselect="true" multiboxonly="true"
						resizable="true" draggable="true" shrinkToFit="true"
						autowidth="true" onCompleteTopics="onLoadSelect" sortname="execOrder" sortorder="asc">
						<sjg:gridColumn name="stepId" search="false" index="stepId"
							title="%{getText('dataCollectionTaskStep.stepId')}" hidden="true"
							formatter="integer" sortable="false" key="true" />
						<sjg:gridColumn name="stepName" index="stepName"
							title="%{getText('dataCollectionTaskStep.stepName')}"
							sortable="true" search="true"
							searchoptions="{sopt:['eq','ne','cn','bw']}" width="150" />
						<sjg:gridColumn name="execOrder" index="execOrder"
							title="%{getText('dataCollectionTaskStep.execOrder')}"
							sortable="true" search="true" 
							searchoptions="{sopt:['eq','ne','cn','bw']}" width="70" />
						<sjg:gridColumn name="execSql" index="execSql"
							title="%{getText('dataCollectionTaskStep.execSql')}"
							sortable="true" search="true" formatter="stringFormatter"
							searchoptions="{sopt:['eq','ne','cn','bw']}" width="300" />
						<sjg:gridColumn name="stepType" index="stepType"
							title="%{getText('dataCollectionTaskStep.stepType')}"
							sortable="true" search="true"
							searchoptions="{sopt:['eq','ne','cn','bw']}" width="80" />
						<sjg:gridColumn name="note" index="note"
							title="%{getText('dataCollectionTaskStep.note')}" sortable="true"
							search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
							width="130" />
						<sjg:gridColumn name="isUsed" index="isUsed"
							title="%{getText('dataCollectionTaskStep.isUsed')}"
							sortable="true" edittype="checkbox" formatter="checkbox"
							width="60" search="true" searchoptions="{sopt:['eq','ne']}" />
					</sjg:grid>
				</div>
				<div class="panelBar">
					<div class="pages">
						<span>显示</span> <select id="dataCollectionTaskStepDefine_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span>条，共<label
							id="dataCollectionTaskStepDefine_gridtable_totals"></label>条</span>
					</div>

					<div id="dataCollectionTaskStepDefine_gridtable_pagination"
						class="pagination" targetType="navTab" totalCount="200"
						numPerPage="20" pageNumShown="10" currentPage="1"></div>

				</div>
			</DIV>
		</div>
	</div>
</div>