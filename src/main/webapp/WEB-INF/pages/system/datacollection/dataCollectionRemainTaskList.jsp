<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="dataCollectionTaskList.title" />
</title>
<meta name="heading"
	content="<fmt:message key='dataCollectionTaskList.heading'/>" />
<meta name="menu" content="DataCollectionTaskMenu" />
<script type="text/javascript">

function stringFormatter (cellvalue, options, rowObject)	{
	try
		{cellvalue= cellvalue.replace(/\r\n/g, "").replace(/\n/g, "");
		}
		catch(err)
		{
			return cellvalue;
		}
	return cellvalue;
}

	function formatDate(cellvalue, options, rowObject) {
		var operateDate = rowObject['operatDate'];
		if(!operateDate){
			return "";
		}
		operateDate = operateDate.replaceAll("T","  ");
		return operateDate;
	}

	function formatLink(cellvalue, options, rowObject) {
		var ids = rowObject['dataCollectionTaskId'];
		//alert("ids is:"+ids)
		var needFile = rowObject['dataCollectionTaskDefine']['dataSourceDefine']['dataSourceType']['isNeedFile'];
		var dataFile = rowObject['dataFile'];

		var interId = rowObject['interRunTimeId'];
		//alert("inter id is :"+interId);
		//alert("ids is : " + ids + "  needFile is : " + needFile	+ " dataFile is : " + dataFile);

		var actions = "";
	/* 	var bt1 = "<button onclick='javascript:importData(" + ids
				+ ")'><fmt:message key='button.dataImport'/>" + "</button>";
		var bt3 = "<button onclick='javascript:showTaskLog(" + ids
				+ ")'>查看运行日志" + "</button>"; */
		var bt2 = "";
		if (needFile == true)
			bt2 = "<button onclick='javascript:uploadDataFile(" + ids
					+ ")'><fmt:message key='button.upLoad'/>" + "</button>"

		if (needFile != true) {
			actions = "";

		} else {
			if (dataFile == null)
				actions = bt2;

			else {
				actions =  bt2;
			}
		}
		//alert(actions)	
		return actions;

	}
	function importData(value) {
		//alert("it's work. para value is: " + value);
		var url = "execDataCollectionTask?dataCollectionTaskId=" + value+"&navTabId=dataCollectionRemainTask_gridtable";
		//window.location = url;
		jQuery.ajax({
		    url: url,
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        //jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		        alert(data.navTabId);
		    	formCallBack(data);
		    }
		});

	}

	function showTaskLog(){
		var sid = jQuery("#dataCollectionRemainTask_gridtable").jqGrid(
				'getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			//alert("<fmt:message key='list.selectNone'/>");
			alertMsg.info("<fmt:message key='list.selectNone'/>");
			return;
		}
		if (sid.length > 1) {
			//alert("<fmt:message key='list.selectNone'/>");
			alertMsg.info("<fmt:message key='list.selectMore'/>");
			return;
		}
		
		 var url = "interLoggerList?dataCollectionTaskId=" + sid[0];
		// alert(url);
		 $.pdialog.open(url, "selectPerson", "查看运行日志", {mask:false,height:650,width:700});
		//navTab.openTab("showDCTaskLog", url, { title:"查看运行日志", fresh:false, data:{} });

	}
	function showTaskStepLog(){
		var sid = jQuery("#dataCollectionTaskRemainStepExecut_gridtable").jqGrid(
				'getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.info("<fmt:message key='list.selectNone'/>");
			return;
		}
		if (sid.length > 1) {
			alertMsg.info("<fmt:message key='list.selectMore'/>");
			return;
		}
		var ret = jQuery("#dataCollectionTaskRemainStepExecut_gridtable").jqGrid('getRowData',sid[0]);
		var taskid=ret["dataCollectionTask.dataCollectionTaskId"];
		var stepName=ret["dataCollectionTaskStep.stepName"];
		stepName=encodeURI(stepName);
		 var url = "interLoggerStepList?stepName="+stepName+"&dataCollectionTaskId=" + taskid ;
		 
		// url=encodeURI(url);
		 //alert(url);
		//navTab.openTab("showDCTaskLog", url, { title:"查看运行日志", fresh:false, data:{} });
		$.pdialog.open(url, "selectPerson", "查看运行日志", {mask:true,height:650,width:700});

	}
	function addTaskExec(){
		//var taskExecId=jQuery("#dataCollectionRemainTask_gridtable").jqGrid('getGridParam', 'selrow');
		var url = "editTaskExec?navTabId=dataCollectionRemainTask_gridtable";
		$.pdialog.open(url, "editTask", "新建任务", {mask:true,height:650,width:700});
	}
	function editTaskExec(){
		var sid = jQuery("#dataCollectionRemainTask_gridtable").jqGrid(
				'getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.info("<fmt:message key='list.selectNone'/>");
			return;
		}
		if (sid.length > 1) {
			alertMsg.info("<fmt:message key='list.selectMore'/>");
			return;
		}
		//var taskExecId=jQuery("#dataCollectionRemainTask_gridtable").jqGrid('getGridParam', 'selrow');
		var taskExecId=sid[0];
		var url = "editTaskExec?taskExecId="+taskExecId+"&navTabId=dataCollectionRemainTask_gridtable";
		$.pdialog.open(url, "editTaskStep", "编辑任务步骤", {mask:true,height:650,width:700});
	}
	function deleteTaskExec(){
		var sid = jQuery("#dataCollectionRemainTask_gridtable").jqGrid(
				'getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.info("<fmt:message key='list.selectNone'/>");
			return;
		}
		var url = "deleteTaskExec?id=" + sid+"&navTabId=dataCollectionRemainTask_gridtable";
		alertMsg.confirm("确认删除？", {
			okCall: function(){
				jQuery.post(url, {
				}, formCallBack, "json");
				
			}
		});
	}
	function updateDisableTask(disabled) {

		var sid = jQuery("#dataCollectionRemainTask_gridtable").jqGrid(
				'getGridParam', 'selarrrow');
		var confirmMess = "是否将所选记录改为启用状态？";
		if (sid == null || sid.length == 0) {
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
		} else {
			if (disabled == 'disable'){
				confirmMess = "是否将所选记录改为停用状态？";
			}
			alertMsg.confirm(confirmMess, {
				okCall: function(){
					var url = "";
					if (disabled == 'disable')
						url = "disabledTaskExecute?id=" + sid+"&navTabId=dataCollectionRemainTask_gridtable";
					if (disabled == 'enable')
						url = "enabledTaskExecute?id=" + sid+"&navTabId=dataCollectionRemainTask_gridtable";
					
					jQuery.post(url, {
					}, formCallBack, "json");
				}
			});
			
			
		}
	}
	
	function editTaskStepExec(){
		var sid = jQuery("#dataCollectionTaskRemainStepExecut_gridtable").jqGrid(
				'getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.info("<fmt:message key='list.selectNone'/>");
			return;
		}
		if (sid.length > 1) {
			alertMsg.info("<fmt:message key='list.selectMore'/>");
			return;
		}
		var taskExecId=jQuery("#dataCollectionRemainTask_gridtable").jqGrid('getGridParam', 'selrow');
		var stepExecId=sid[0];
		var url = "editTaskStepExec?taskExecId="+taskExecId+"&stepExecId=" + stepExecId+"&navTabId=dataCollectionTaskRemainStepExecut_gridtable";
		$.pdialog.open(url, "editTaskStep", "编辑任务步骤", {mask:true,height:650,width:700});
	}
	
	function addTaskStepExec(){
		var taskExecId=jQuery("#dataCollectionRemainTask_gridtable").jqGrid('getGridParam', 'selrow');
		var url = "editTaskStepExec?taskExecId="+taskExecId+"&navTabId=dataCollectionTaskRemainStepExecut_gridtable";
		$.pdialog.open(url, "editTaskStep", "新建任务步骤", {mask:true,height:650,width:700});
	}
	function deleteTaskStepExec(){
		var sid = jQuery("#dataCollectionTaskRemainStepExecut_gridtable").jqGrid(
				'getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.info("<fmt:message key='list.selectNone'/>");
			return;
		}
		var url = "deleteTaskStepExec?deleteStepExecIds=" + sid+"&navTabId=dataCollectionTaskRemainStepExecut_gridtable";
		alertMsg.confirm("确认删除？", {
			okCall: function(){
				jQuery.post(url, {
				}, formCallBack, "json");
				
			}
		});
	}
	function execTasks() {
		var sid = jQuery("#dataCollectionRemainTask_gridtable").jqGrid(
				'getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			//alert("<fmt:message key='list.selectNone'/>");
			alertMsg.info("<fmt:message key='list.selectNone'/>");
			return;
		}
		alertMsg.confirm("确认采集选中的任务？", {
			okCall: function(){
				var url = "execDataCollectionTasks?id=" + sid+"&navTabId=dataCollectionRemainTask_gridtable";
				jQuery.ajax({
				    url: url,
				    type: 'post',
				    dataType: 'json',
				    aysnc:false,
				    error: function(data){
				        //jQuery('#name').attr("value",data.responseText);
				    },
				    success: function(data){
				        // do something with xml
				    	//formCallBack(data);
				         // alert(data.navTabId);
				        // alert(data.message);
				        if(data.statusCode==200){
				        	data.statusCode = 2;
				        }
				        data.statusCode
				    	formCallBack(data);
				    	dataCollectionRemainTaskLayout.closeSouth();
				        
				        
				    }
				});
			}
		});
		
	}

	function refreshTasks(){
		var url = "createPeriodTask";
		$.ajax({
		    url: 'createPeriodTask',
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    },
		    success: function(data){
		        // do something with xml
		        //alert(data);
		        jQuery("#dataCollectionRemainTask_gridtable").jqGrid('setGridParam', {
					page : 1
				}).trigger("reloadGrid");
		    }
		});
	}
	
	function uploadDataFile(value) {
		//alert("it's work. para value is: " + value);
		var url = "dataCollectionTaskSelectFile?popup=true&dataCollectionTaskId="
				+ value+"&navTabId=dataCollectionRemainTask_gridtable";
		//alert("url is :" + url);
		var winTitle = '<fmt:message key="dataCollectionTaskNew.title"/>';
		//popUpWindow(url, winTitle, "width=500");
		$.pdialog.open(url, 'uploadDataFile', winTitle, {
			mask : false,
			width : 400,
			height : 200
		});
		//window.location = url;
	}

	function okButton() {
		jQuery('#mybuttondialog').dialog('close');
	};

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
	var dataCollectionRemainTaskLayout;
	jQuery(document).ready(function() {

						//defineLayout.addCloseBtn("#tbarCloseSouth","south");

						/* jQuery
								.subscribe(
										'rowselect',
										function(event, data) {
											jQuery("#gridinfo")
													.html(
															'<p>Edit Mode for Row : '
																	+ event.originalEvent.id
																	+ '</p>');
											var row = jQuery(
													"#dataCollectionRemainTask_gridtable")
													.jqGrid(
															'getRowData',
															event.originalEvent.id);
											var ids = row['dataCollectionTaskId'];
											//   			alert("fdc come here.")
											jQuery(
													"#dataCollectionTaskRemainStepExecut_gridtable")
													.jqGrid(
															'setGridParam',
															{
																url : "dataCollectionTaskStepExecuteGridList?taskId="
																		+ ids
															});
											jQuery(
													"#dataCollectionTaskRemainStepExecut_gridtable")
											//.jqGrid('setCaption',"Invoice Detail: "+ids)
											.trigger('reloadGrid');

										});
						setTimeout(
								"jQuery('#dataCollectionRemainTaskSouth').css('display','none')",
								"50"); */
				var changeData = function(selectedSearchId){
					var url = "dataCollectionTaskStepExecuteGridList?taskId="+selectedSearchId;
					jQuery("#dataCollectionTaskRemainStepExecut_gridtable").jqGrid('setGridParam',{url : url}).trigger("reloadGrid"); 
					$("#background,#progressBar").hide();
				}
				dataCollectionRemainTaskLayout = makeLayout({'baseName':'dataCollectionRemainTask','tableIds':'dataCollectionRemainTask_gridtable;dataCollectionTaskRemainStepExecut_gridtable','proportion':2,key:'dataCollectionTaskId',differ:29},changeData);
	});
	function updateDisableStep(disabled) {
		var confirmMess = "是否将所选记录改为启用状态？";
		var sid = jQuery("#dataCollectionTaskRemainStepExecut_gridtable").jqGrid(
				'getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {

			/* msgDialog.dialog('option', 'buttons', {
				"<fmt:message key='dialog.ok'/>" : function() {
					jQuery(this).dialog("close");
				}
			});
			jQuery('div.#msgDialog').html(
					"<fmt:message key='list.selectNone'/>");
			msgDialog.dialog('open'); */
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
		} else {
			if (disabled == 'disable'){
				confirmMess = "是否将所选记录改为停用状态？";
			}
			alertMsg.confirm(confirmMess, {
				okCall: function(){
					var url = "";
					if (disabled == 'disable')
						url = "disabledStepExecute?id=" + sid;
					if (disabled == 'enable')
						url = "enabledStepExecute?id=" + sid;
					var jqxhr = jQuery.post(url, {
						dataType : "json",
						async : false
					}).success(
							function(data) {
								var status = data['jsonStatus'];
								if (status == "error") {
									alertMsg.error(data['jsonMessages']);
								} else {
									alertMsg.correct(data['jsonMessages']);
								}
							}).error(function() {
						alert(data['jsonMessages']);
					});

					jqxhr.complete(function() {
						jQuery("#dataCollectionTaskRemainStepExecut_gridtable")
								.jqGrid('setGridParam', {
									page : 1
								}).trigger("reloadGrid");
					});
				}
			});
			
			/* confirmDialog.dialog('option', 'buttons', {
				"<fmt:message key='dialog.confirm'/>" : function() {
					jQuery(this).dialog("close");
					var url = "";
					if (disabled == 'disable')
						url = "disabledStepExecute?id=" + sid;
					if (disabled == 'enable')
						url = "enabledStepExecute?id=" + sid;
					var jqxhr = jQuery.post(url, {
						dataType : "json",
						async : false
					}).success(
							function(data) {
								var status = data['jsonStatus'];
								if (status == "error") {
									jQuery('div.#msgDialog').html(
											"<p class='ui-state-error'>"
													+ data['jsonMessages']
													+ "</p>");
								} else {
									jQuery('div.#msgDialog').html(
											"<p class='ui-state-hover'>"
													+ data['jsonMessages']
													+ "</p>");
								}
								msgDialog.dialog('open');
							}).error(function() {
						alert(data['jsonMessages']);
					});

					jqxhr.complete(function() {
						jQuery("#dataCollectionTaskRemainStepExecut_gridtable")
								.jqGrid('setGridParam', {
									page : 1
								}).trigger("reloadGrid");
					});
				},
				"<fmt:message key='dialog.cancel'/>" : function() {
					jQuery(this).dialog("close");
				}
			}

			);
			jQuery('div.#confirmDialog').html("确认更新所选记录的disabled状态的？");
			confirmDialog.dialog('open'); */
		}
	}
	/* var fullHeight;
	var subGridHright = Math.floor(jQuery(document).height() / 2);
	var toggle = 0;
	/*
	 * 重置pane的方法,使用处：展开、折叠
	 */
	/* function reSizePane(openPane, opt, closePane) {
		if (opt == 0) {
			//defineLayout.sizePane(pane,southSize);
			jQuery("#" + closePane).css("display", "block");
			jQuery("#" + openPane).css("height", subGridHright);
			//jQuery("#"+pan).css("display","block");
			jQuery("#dataCollectionRemainTaskCenterBar")
					.css("display", "block");
		} else {
			//defineLayout.sizePane(pane,size);
			jQuery('#dataCollectionRemainTask_gridtable').jqGrid(
					"setGridHeight", fullHeight - 60);
			jQuery("#" + closePane).css("display", "none");
			jQuery("#" + openPane).css("height", fullHeight + 23);
			jQuery("#dataCollectionRemainTaskCenterBar").css("display", "none");
		}

	} */
	/*
	 * 关闭pane的方法，使用处：关闭
	 */
	/* function closeSouth(closePane, openPane) {
		//defineLayout.sizePane("south",southSize);
		//defineLayout.toggle('south');
		jQuery("#" + closePane).css("display", "none");
		jQuery("#" + openPane).css("height", fullHeight);
		//jQuery("#search_gridtable").jqGrid('setGridHeight', fullHeight-50);
	} */

	/* function togglePane(openPane, narrowPane) {
		//jQuery("#taskRemain").css("display","none");
		//jQuery("#dataCollectionRemainTask_gridtable").jqGrid('setGridParam',{height:500}).trigger("reloadGrid");});
		if (jQuery("#" + openPane).css("display") == "block") {
			jQuery("#" + openPane).css("display", "none");
			jQuery("#" + narrowPane).css("height", fullHeight);
		} else {
			fullHeight = jQuery("#" + narrowPane).height();
			jQuery("#" + narrowPane).css("height", subGridHright);
			//alert(jQuery("#"+narrowPane).css("height"));
			//jQuery("#search_gridtable").jqGrid('setGridHeight', subGridHright-55);
			jQuery("#" + openPane).css("height", subGridHright);
			jQuery("#" + openPane).css("display", "block");
		}
	} */

	/* function updateStepDisable(){
		   var isUsedString;
		   var jqxhr=jQuery.post( "getStepExecuteIsUsedId", {dataType : "json",async:false})
		    .success(function(data)
		    		{
		      	isUsedString = data['isUsedString'];
		      	jQuery("#dataCollectionTaskRemainStepExecut_gridtable").find("input[type=checkbox]").each(function(){
					//jQuery(this).attr("checked","true");
					var iuArr = isUsedString.split(",");
					for(var i=0;i<iuArr.length;i++){
						if(jQuery(this).parent().next().text()==iuArr[i]){
							jQuery(this).attr("checked","true");
							break;
						}
					}
					
					//alert(jQuery(this).attr("value"));
				});
			});
		    //.error(function() {  alert(data['jsonMessages']); }); 
		    //alert();
		
		//alert(jQuery("#dataCollectionTaskRemainStepExecut_gridtable").html());
	} */ 
	jQuery(function() {
		/*============================================mainButtonBar start=============================================*/
		var dataCollectionRemainTask_menuButtonArrJson = "${menuButtonArrJson}";
		var dataCollectionRemainTask_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(dataCollectionRemainTask_menuButtonArrJson,false)));
		var dataCollectionRemainTask_toolButtonBar = new ToolButtonBar({el:$('#dataCollectionRemainTask_buttonBar'),collection:dataCollectionRemainTask_toolButtonCollection,attributes:{
			tableId : 'dataCollectionRemainTask_gridtable',
			baseName : 'dataCollectionRemainTask',
			width : 500,
			height : 350,
			base_URL : null,
			optId : null,
			fatherGrid : null,
			extraParam : null,
			selectNone : "请选择记录。",
			selectMore : "只能选择一条记录。",
			addTitle : '<s:text name="dataCollectionRemainTaskNew.title"/>',
			editTitle : null
		}}).render();
		
		var dataCollectionRemainTask_function = new scriptFunction();
		dataCollectionRemainTask_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.selectRecord){
				var sid = jQuery("#dataCollectionRemainTask_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
		        	alertMsg.error("请选择记录！");
					return pass;
				}
		        if(param.singleSelect){
		        	if(sid.length != 1){
			        	alertMsg.error("只能选择一条记录！");
						return pass;
					}
		        }
			}
	        return true;
		};
		//刷新
		dataCollectionRemainTask_toolButtonBar.addCallBody('000311',function() {
			refreshTasks();
		},{});
		//采集
		dataCollectionRemainTask_toolButtonBar.addCallBody('000312',function() {
			execTasks();
		},{});
		//明细
		dataCollectionRemainTask_toolButtonBar.addCallBody('000313',function() {
			dataCollectionRemainTaskLayout.optDblclick();
		},{});
		//新建
		dataCollectionRemainTask_toolButtonBar.addCallBody('000314',function() {
			addTaskExec();
		},{});
		//编辑
		dataCollectionRemainTask_toolButtonBar.addCallBody('000315',function() {
			editTaskExec();
		},{});
		//删除
		dataCollectionRemainTask_toolButtonBar.addCallBody('000316',function() {
			deleteTaskExec();
		},{});
		//停用
		dataCollectionRemainTask_toolButtonBar.addCallBody('000317',function() {
			updateDisableTask('disable');
		},{});
		//启用
		dataCollectionRemainTask_toolButtonBar.addCallBody('000318',function() {
			updateDisableTask('enable');
		},{});
		/*============================================mainButtonBar end=============================================*/
		/*============================================detailButtonBar start=============================================*/
		var dataCollectionTaskRemainStepExecut_menuButtonArrJson = "${menuButtonDetailArrJson}";
		var dataCollectionTaskRemainStepExecut_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(dataCollectionTaskRemainStepExecut_menuButtonArrJson,false)));
		var dataCollectionTaskRemainStepExecut_toolButtonBar = new ToolButtonBar({el:$('#dataCollectionTaskRemainStepExecut_buttonBar'),collection:dataCollectionTaskRemainStepExecut_toolButtonCollection,attributes:{
			tableId : 'dataCollectionTaskRemainStepExecut_gridtable',
			baseName : 'dataCollectionTaskRemainStepExecut',
			width : 500,
			height : 350,
			base_URL : null,
			optId : null,
			fatherGrid : null,
			extraParam : null,
			selectNone : "请选择记录。",
			selectMore : "只能选择一条记录。",
			addTitle : '<s:text name="dataCollectionTaskRemainStepExecutNew.title"/>',
			editTitle : null
		}}).render();
		
		var dataCollectionTaskRemainStepExecut_function = new scriptFunction();
		dataCollectionTaskRemainStepExecut_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.selectRecord){
				var sid = jQuery("#dataCollectionTaskRemainStepExecut_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
		        	alertMsg.error("请选择记录！");
					return pass;
				}
		        if(param.singleSelect){
		        	if(sid.length != 1){
			        	alertMsg.error("只能选择一条记录！");
						return pass;
					}
		        }
			}
	        return true;
		};
		//新建
		dataCollectionTaskRemainStepExecut_toolButtonBar.addCallBody('000341',function() {
			addTaskStepExec();
		},{});
		//编辑
		dataCollectionTaskRemainStepExecut_toolButtonBar.addCallBody('000342',function() {
			editTaskStepExec();
		},{});
		//删除
		dataCollectionTaskRemainStepExecut_toolButtonBar.addCallBody('000343',function() {
			deleteTaskStepExec();
		},{});
		//查看日志
		dataCollectionTaskRemainStepExecut_toolButtonBar.addCallBody('000344',function() {
			showTaskLog();
		},{});
		//停用
		dataCollectionTaskRemainStepExecut_toolButtonBar.addCallBody('000345',function() {
			updateDisableStep('disable');
		},{});
		//启用
		dataCollectionTaskRemainStepExecut_toolButtonBar.addCallBody('000346',function() {
			updateDisableStep('enable');
		},{});
		
		/*============================================detailButtonBar end=============================================*/
	});
</script>
</head>
<body>

	<div class="page" style="overflow: auto">
		<div class="pageContent" class="layoutBox">
			<div id="dataCollectionRemainTask_container">
				<div id="dataCollectionRemainTask_layout-center" class="pane ui-layout-center"
					style="padding: 2px">
					<div id="dataCollectionRemainTaskCenterBar" class="panelBar">
						<ul class="toolBar"  id="dataCollectionRemainTask_buttonBar">
							<%-- <li><a class="getdatabutton" href="javaScript:refreshTasks();"><span>刷新</span> </a>
							</li>
							<li><a class="getdatabutton" href="javaScript:execTasks();"><span><fmt:message
											key="button.dataImport" /> </span> </a>
							</li>
							<li><a class="particularbutton"
									href="javaScript:dataCollectionRemainTaskLayout.optDblclick();"><span>明细</span> </a>
							</li>
								<li><a class="addbutton"	href="javaScript:addTaskExec();"><span>新建</span></a></li>
								<li><a class="changebutton"	href="javaScript:editTaskExec();"><span>编辑</span></a></li>
								<li><a class="delbutton"	href="javaScript:deleteTaskExec();"><span>删除</span></a></li>
								<li><a class="disablebutton"
									href="javaScript:updateDisableTask('disable');"><span><fmt:message
												key="button.disable" /> </span> </a>
								</li>
								<li><a id="dataCollectionTask_gridtable_edit"
									class="enablebutton" href="javaScript:updateDisableTask('enable');"><span><fmt:message
												key="button.enable" /> </span> </a>
								</li>	 --%>			
						</ul>
					</div>
					<div id="dataCollectionRemainTaskCenter">

						<s:url id="editurl" action="dataCollectionTaskGridEdit" />
						<s:url id="remoteurl" action="dataCollectionRemainTaskGridList" />

						<!--<div align="left">
<sj:submit id="add_button" value="%{getText('button.add')}" onClickTopics="addRowRecord" button="true" onclick="addRecord();"/>
<sj:submit id="editSelectRow_button" value="%{getText('button.edit')}"  button="true" onclick="editRecord();"/>
</div>
 -->
						<div id="dataCollectionRemainTask_gridtable_div" layoutH="87"
							class="grid-wrapdiv"
							buttonBar="">
							<input type="hidden" id="dataCollectionRemainTask_gridtable_isShowSouth"
						value="0"> 
						<div id="load_dataCollectionRemainTask_gridtable" class='loading ui-state-default ui-state-active'></div>
							<sjg:grid id="dataCollectionRemainTask_gridtable" dataType="json"
								gridModel="dataCollectionTasks" editurl="%{editurl}"
								href="%{remoteurl}" rowList="10,15,20,30,40,50,60,70,80,90,100"
								rowNum="20" rownumbers="true" pager="false" page="1"
								pagerButtons="false" pagerInput="false" pagerPosition="right"
								navigator="false" navigatorAdd="false" navigatorEdit="false"
								navigatorDelete="false"
								navigatorDeleteOptions="{reloadAfterSubmit:true,left:screen.availWidth/4, top:screen.availHeight/4}"
								navigatorSearch="false"
								navigatorSearchOptions="{multipleSearch:true,  showQuery: true,left:screen.availWidth/4, top:screen.availHeight/4}"
								navigatorRefresh="false" multiselect="true" multiboxonly="true"
								resizable="true" onclick="dataCollectionRemainTaskLayout.optClick();" draggable="true"
								autowidth="false" onCompleteTopics="onLoadSelect"
								ondblclick="dataCollectionRemainTaskLayout.optDblclick();" sortname="dataCollectionTaskDefine.taskNo" sortorder="asc">
								<sjg:gridColumn name="dataCollectionTaskId" search="false"
									index="dataCollectionTaskId"
									title="%{getText('dataCollectionTask.dataCollectionTaskId')}"
									hidden="true" formatter="integer" sortable="false"
									editable="true" key="true" />
								<sjg:gridColumn name="interperiod"
									index="dataCollectionPeriod.periodCode"
									title="%{getText('period.periodCode')}" sortable="true"
									editable="true" search="true"
									searchoptions="{sopt:['eq','ne','cn','bw']}" width="60"
									editrules="{required: true}" />
								<sjg:gridColumn
									name="dataCollectionTaskDefine.taskNo"
									index="dataCollectionTaskDefine.taskNo"
									title="%{getText('dataCollectionTaskDefine.taskNo')}"
									sortable="true" editable="true" search="true"
									searchoptions="{sopt:['eq','ne','cn','bw']}" width="50"
									editrules="{required: true}" />
								<sjg:gridColumn
									name="dataCollectionTaskDefine.dataCollectionTaskDefineName"
									index="dataCollectionTaskDefine.dataCollectionTaskDefineName"
									title="%{getText('dataCollectionTaskDefine.dataCollectionTaskDefineName')}"
									sortable="true" editable="true" search="true"
									searchoptions="{sopt:['eq','ne','cn','bw']}" width="150"
									editrules="{required: true}" />
								<sjg:gridColumn
									name="dataCollectionTaskDefine.dataSourceDefine.dataSourceName"
									index="dataCollectionTaskDefine.dataSourceDefine.dataSourceName"
									title="%{getText('dataSourceDefine.dataSourceName')}"
									sortable="true" editable="true" search="true"
									searchoptions="{sopt:['eq','ne','cn','bw']}" width="100"
									editrules="{required: true}" />
								<sjg:gridColumn
									name="dataCollectionTaskDefine.dataSourceDefine.dataSourceType.dataSourceTypeName"
									index="dataCollectionTaskDefine.dataSourceDefine.dataSourceType.dataSourceTypeName"
									title="%{getText('dataSourceType.dataSourceTypeName')}"
									sortable="true" editable="true" search="true"
									searchoptions="{sopt:['eq','ne','cn','bw']}" width="80"
									editrules="{required: true}" />
								<sjg:gridColumn
									name="dataCollectionTaskDefine.dataSourceDefine.dataSourceType.isNeedFile"
									index="dataCollectionTaskDefine.dataSourceDefine.dataSourceType.isNeedFile"
									title="%{getText('dataSourceType.isNeedFile')}"
									formatter="checkbox" sortable="true" editable="true"
									search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
									width="60" align="center" editrules="{required: true}" />
								<sjg:gridColumn name="dataFile" index="dataFile"
									title="%{getText('dataCollectionTask.dataFile')}"
									sortable="true" editable="true" search="true"
									searchoptions="{sopt:['eq','ne','cn','bw']}" width="100"
									editrules="{required: true}" />
								<sjg:gridColumn name="isUsed" index="isUsed"
									title="%{getText('dataCollectionTaskStepExecute.isUsed')}"
									sortable="false" formatter="checkbox" search="true"
									searchoptions="{sopt:['eq','ne','cn','bw']}" width="30"
									align="center"/>	
								<sjg:gridColumn name="status" index="status"
									title="%{getText('dataCollectionTask.status')}" sortable="true"
									editable="true" search="true"
									searchoptions="{sopt:['eq','ne','cn','bw']}" width="60"
									editrules="{required: true}" />
								<sjg:gridColumn name="act"
									title="%{getText('grid.column.action')}" width="80"
									formatter="formatLink" sortable="false" />
								<sjg:gridColumn name="operator" width="80" title="%{getText('interLogger.operator')}" sortable="false" align="left" />
								<sjg:gridColumn name="operateDate" width="100" title="%{getText('dataCollectionTask.operatDate')}" formatter="formatDate" sortable="false" align="left" />
								<sjg:gridColumn name="interRunTimeId" hidden="true"
									title="interRunTimeId"></sjg:gridColumn>
							</sjg:grid>
						</div>
						<div class="panelBar">
							<div class="pages">
								<span>显示</span> <select id="dataCollectionRemainTask_gridtable__numPerPage">
									<option value="20">20</option>
									<option value="50">50</option>
									<option value="100">100</option>
									<option value="200">200</option>
								</select> <span>条，共<label
									id="dataCollectionRemainTask_gridtable_totals"></label>条</span>
							</div>

							<div id="dataCollectionRemainTask_gridtable_pagination"
								class="pagination" targetType="navTab" totalCount="200"
								numPerPage="20" pageNumShown="10" currentPage="1"></div>

						</div>
					</div>
				</div>

				<DIV id="dataCollectionRemainTask_layout-south" class="pane ui-layout-south"
					style="padding: 2px">
					<div id="dataCollectionRemainTaskSouth">
						<sj:dialog id="msgDialog"
							buttons="{'%{getText('dialog.ok')}':function() {jQuery( this ).dialog( 'close' ); }}"
							autoOpen="false" modal="true"
							title="%{getText('messageDialog.title')}" />
						<sj:dialog id="confirmDialog"
							buttons="{'%{getText('dialog.confirm')}':function() {jQuery( this ).dialog( 'close' ); },'%{getText('dialog.cancel')}':function() {jQuery( this ).dialog( 'close' ); }}"
							autoOpen="false" modal="true"
							title="%{getText('messageDialog.title')}" />

						<s:url id="stepExecuteEditurl"
							action="dataCollectionTaskStepExecuteGridList" />
						<s:url id="stepRemoteurl" action="dataCollectionTaskStepGridList" />


						<div class="panelBar">
							 <ul class="toolBar" id="dataCollectionTaskRemainStepExecut_buttonBar">
								
								<%-- <li><a class="add"	href="javaScript:addTaskStepExec();"><span>新建</span></a></li>
								<li><a class="edit"	href="javaScript:editTaskStepExec();"><span>编辑</span></a></li>
								<li><a class="delete"	href="javaScript:deleteTaskStepExec();"><span>删除</span></a></li>
								<li><a class="add"	href="javaScript:showTaskLog();"><span>查看日志</span></a></li>
								<li><a class="disablebutton"
									href="javaScript:updateDisableStep('disable');"><span><fmt:message
												key="button.disable" /> </span> </a>
								</li>
								<li><a id="dataCollectionTaskStep_gridtable_edit"
									class="enablebutton" href="javaScript:updateDisableStep('enable');"><span><fmt:message
												key="button.enable" /> </span> </a>
								</li> --%>
								<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
								<li style="float: right;"><a id="dataCollectionRemainTask_close" class="closebutton"
									href="javaScript:"><span><fmt:message
												key="button.close" /> </span> </a>
								</li>

								<li class="line" style="float: right">line</li>
								<li style="float: right;"><a id="dataCollectionRemainTask_fold"  class="foldbutton"
									href="javaScript:"><span><fmt:message
												key="button.fold" /> </span> </a>
								</li>
								<li class="line" style="float: right">line</li>
								<li style="float: right"><a id="dataCollectionRemainTask_unfold" class="unfoldbutton"
									href="javaScript:"><span><fmt:message
												key="button.unfold" /> </span> </a>
								</li>
							</ul>
						</div>
						<div id="dataCollectionTaskRemainStepExecut_gridtable_div"
							class="grid-wrapdiv" extraHeight=82 tablecontainer="dataCollectionRemainTask_layout-south"
							buttonBar="">
<div id="load_dataCollectionTaskRemainStepExecut_gridtable" class='loading ui-state-default ui-state-active'></div>
							<sjg:grid id="dataCollectionTaskRemainStepExecut_gridtable"
								dataType="json" gridModel="dataCollectionTaskStepExecutes"
								editurl="%{stepExecuteEditurl}"
								rowList="10,15,20,30,40,50,60,70,80,90,100" rowNum="20"
								rownumbers="true" pager="false" page="1" pagerButtons="false"
								pagerInput="false" pagerPosition="right" navigator="false"
								navigatorAdd="false" cellEdit="false" editinline="false"
								navigatorEdit="false" navigatorDelete="false"
								navigatorDeleteOptions="{reloadAfterSubmit:true,afterSubmit:checkGridOperation}"
								navigatorSearch="false"
								navigatorSearchOptions="{multipleSearch:true,  showQuery: true}"
								navigatorRefresh="false" multiselect="true" multiboxonly="true"
								resizable="true" draggable="true"
								shrinkToFit="true" autowidth="true" onCompleteTopics="onLoadSelect"
								sortname="dataCollectionTaskStep.execOrder" sortorder="asc"
								>
								<sjg:gridColumn name="stepExecuteId" search="false"
									index="stepExecuteId"
									title="%{getText('dataCollectionTaskStep.stepId')}"
									hidden="true" formatter="integer" sortable="false" key="true" />
<sjg:gridColumn name="dataCollectionTask.dataCollectionTaskId" title="taskId"	hidden="true"		 />


								<%-- <sjg:gridColumn name="dataCollectionTaskStepExecute.isUsed" index="dataCollectionTaskStep.isUsed"
				title="%{getText('dataCollectionTaskStep.isUsed')}" sortable="false"
				formatter="checkbox" 
				editable="true"
				search="true"
				searchoptions="{sopt:['eq','ne','cn','bw']}" width="100"/> --%>
								<sjg:gridColumn name="dataCollectionTaskStep.execOrder"
									index="dataCollectionTaskStep.execOrder"
									title="%{getText('dataCollectionTaskStep.execOrder')}"
									sortable="true" search="true" width="80"
									searchoptions="{sopt:['eq','ne','cn','bw']}" />

								<sjg:gridColumn name="dataCollectionTaskStep.stepName"
									index="dataCollectionTaskStep.stepName"
									title="%{getText('dataCollectionTaskStep.stepName')}"
									sortable="true" search="true"
									searchoptions="{sopt:['eq','ne','cn','bw']}" />
								<sjg:gridColumn name="dataCollectionTaskStep.stepType"
									index="dataCollectionTaskStep.stepType"
									title="%{getText('dataCollectionTaskStep.stepType')}"
									sortable="true" search="true" width="150"
									searchoptions="{sopt:['eq','ne','cn','bw']}" />

								<sjg:gridColumn name="dataCollectionTaskStep.execSql"
									index="dataCollectionTaskStep.execSql"
									title="%{getText('dataCollectionTaskStep.execSql')}"
									sortable="true" search="true" formatter="stringFormatter"
									searchoptions="{sopt:['eq','ne','cn','bw']}" width="300"/>

								<sjg:gridColumn name="isUsed" index="isUsed"
									title="%{getText('dataCollectionTaskStepExecute.isUsed')}"
									sortable="false" formatter="checkbox" search="true"
									searchoptions="{sopt:['eq','ne','cn','bw']}" width="80"/>

								<sjg:gridColumn name="dataCollectionTaskStep.note"
									index="dataCollectionTaskStep.note"
									title="%{getText('dataCollectionTaskStep.note')}"
									sortable="true" search="true"
									searchoptions="{sopt:['eq','ne','cn','bw']}" width="80"/>

								<sjg:gridColumn name="status" index="status"
									title="%{getText('dataCollectionTaskStepExecute.status')}"
									sortable="true" search="true"
									searchoptions="{sopt:['eq','ne','cn','bw']}" width="80"/>
							</sjg:grid>
						</div>
						<div class="panelBar">
							<div class="pages">
								<span>显示</span> <select id="dataCollectionTaskRemainStepExecut_gridtable_numPerPage">
									<option value="20">20</option>
									<option value="50">50</option>
									<option value="100">100</option>
									<option value="200">200</option>
								</select> <span>条，共<label
									id="dataCollectionTaskRemainStepExecut_gridtable_totals"></label>条</span>
							</div>

							<div id="dataCollectionTaskRemainStepExecut_gridtable_pagination"
								class="pagination" targetType="navTab" totalCount="200"
								numPerPage="20" pageNumShown="10" currentPage="1"></div>

						</div>
					</div>
				</DIV>
			</div>
		</div>
	</div>