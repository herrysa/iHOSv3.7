<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<head>
	<style type="text/css">
		.importFunBtn button {
			width: 78px;
			height: 25px;
			margin: 5px;
			color: #15428B;
		}
	</style>
	<script type="text/javascript">
		var importDataDefineLayout;
		var importDataDefineGridIdString = "#" + ${random}+"importDataDefine_gridtable";
		jQuery(document).ready(function() {
							var importDataDefineGrid = jQuery(importDataDefineGridIdString);
							importDataDefineGrid
									.jqGrid({
										url : '',
										postData : importDataColModels,
										datatype : "local",
										colModel : [
	{name:'detailId',index:'detailId',align:'left',width:'88px',label : '<s:text name="importDataDefineDetail.detailId" />',highsearch:true,hidden:true,key:true},
	{name : 'detailName',index : 'detailName',width:"88px",align : 'left',label : '<s:text name="importDataDefineDetail.detailName" />',highsearch:true,hidden : false},
	{name : 'detailType',index : 'detailType',width:"88px",align : 'left',label : '<s:text name="importDataDefineDetail.detailType" />',highsearch:true,hidden : false,formatter:"select",editoptions:{value:"0:数字型;1:字符型;2:日期型;3:整型"}},
	{name : 'entityName',index : 'entityName',width:"88px",align : 'left',label : '<s:text name="importDataDefineDetail.entityName" />',highsearch:true,hidden : false},
	{name : 'isConstraint',index : 'isConstraint',width:"45px",align : 'center',label : '<s:text name="importDataDefineDetail.isConstraint" />',editable:false,edittype:"checkbox",hidden : false,formatter:"checkbox",highsearch:true},
	{name : 'isUpdate',index : 'isUpdate',width:"55px",align : 'center',label : '<s:text name="importDataDefineDetail.isUpdate" />',editable:true,edittype:"checkbox",hidden : false,formatter:"checkbox",highsearch:true},
	{name : 'sn',index : 'sn',width:"88px",align : 'center',label : '<s:text name="sn" />',sortable:true,highsearch:true,hidden : true}],
										jsonReader : {
											root : "importDataDefineDetails",
											page : "page",
											total : "total",
											records : "records",
											repeatitems : false
										},
										sortname : 'sn',
										viewrecords : true,
										sortorder : 'asc',
										height : 245,
										gridview : true,
										rownumbers : true,
										loadui : "disable",
										multiselect : true,
										multiboxonly : true,
										shrinkToFit : false,
										autowidth : false,
										onSelectRow : function(rowid) {
											/*setTimeout(function() {
												importDataDefineLayout.optClick();
											},100);*/
										},
										gridComplete : function() {
											var dataTest = {
												"id" : "importDataDefine_gridtable"
											};
											//jQuery.publish("onLoadSelect",dataTest, null);
											//makepager("importDataDefine_gridtable");
											gridResize(null,null,"${random}importDataDefine_gridtable","single");
											jQuery("#gview_${random}importDataDefine_gridtable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
												jQuery(this).find("th").each(function(){
													jQuery(this).find("div").eq(0).css("line-height","18px");
												});
											}); 
										}
									});
				jQuery(importDataDefineGrid).jqGrid('bindKeys');
						/*if(importDataColModels) {
							for(var index in importDataColModels) {
								//添加本地数据
								jQuery(importDataDefineGrid).jqGrid("addRowData",index+1,importDataColModels[index]);
							}
						}*/
						
				/*//使得一列可编辑
				fullGridEdit(importDataDefineGridIdString);*/
				jQuery("#${random}btnSaveImportDataDefine").attr("disabled",true);
				jQuery("#${random}btnSaveImportDataDefine").css("color","gray");
				
				var interfaceId = "${interfaceId}" ;
				if(interfaceId) {
					jQuery("#${random}importDataDefineDetail_listOfName").val(interfaceId);
					reloadImportDataDefineGridTable(interfaceId);
				}
				
				/*保存*/
				jQuery("#${random}btnSaveImportDataDefine").unbind("click").bind("click",function() {
					var interfaceName = jQuery("#${random}importDataDefine_interfaceName").val();
					var temp = jQuery("#${random}dvInterfaceList").is(":hidden");
					var flag = jQuery("#${random}importDataDefineDetail_listOfName").val();
					if(temp == false && !flag) {
						alertMsg.info("请选择一个外部数据接口！");
						return;
					}
					if(interfaceName == null || interfaceName.length == 0) {
						alertMsg.error("接口名称不能为空！");
					} else {
							var importDataArray = new Array();
							var selRowData = importDataDefineGrid.jqGrid("getRowData");
							var constraintFlag = 0;
							for(var index in selRowData) {
								var isConstraint = ("Yes"==selRowData[index].isConstraint)?true:false;
								var isUpdate = true;
								if(selRowData[index].isConstraint == "Yes") {
									isUpdate = false;
								} else {
									isUpdate = ("Yes"==selRowData[index].isUpdate)?true:false;
								}
								var importData = {
									detailName : selRowData[index].detailName,
									detailType : selRowData[index].detailType,
									entityName : selRowData[index].entityName,
									isConstraint : isConstraint,
									isUpdate : isUpdate,
									sn : index
								};
								importDataArray.push(importData);
								if(isConstraint) {
									constraintFlag++;
								}
							}
							if(constraintFlag > 0) {
								importData = JSON.stringify(importDataArray);
								jQuery.ajax({
									url:"saveImportDataDefineDetails",
									dataType:"json",
									data:{importData:importData,interfaceName:interfaceName},
									type:"post",
									error:function() {
									},
									success:function(data) {
										if("200" == data.statusCode) {
											alertMsg.correct("操作成功！");
											jQuery("#${random}btnSaveImportDataDefine").attr("disabled",true);
											jQuery("#${random}btnSaveImportDataDefine").css("color","gray");
 											var tableName = jQuery("#${random}importDataDefine_tableName").val();
											var url = "findAllImportDataDefine?tableName="+tableName+"&navTabId=${navTabId}&interfaceId="+data.interfaceId;
											url += "&subSystemCode=GZ&callBackFunc=gzContentGridTableReLoadData()";
											url = encodeURI(url);
											$.pdialog.reload(url,{ifr:true,hasSupcan:true,mask:true,resizable:false,maxable:false,width : 700,height : 550});
											
										}
									}
								});
							}else{
								alertMsg.error("至少要选择一个项目作为约束！");
							}
					}
				});
				/*删除接口*/
				jQuery("#${random}btnDelImportDataDefine").unbind("click").bind("click",function(){
					var removeId = jQuery("#${random}importDataDefineDetail_listOfName").val();
					if(removeId == null || removeId.length <= 0) {
						alertMsg.info("请先选择一个外部数据接口！");
					}else{
						alertMsg.confirm("确认删除？",{
							okCall:function(){
								jQuery.ajax({
									url:"importDataDefineGridEdit?id="+removeId+"&oper=del&navTabId=${navTabId}",
									dataType:"json",
									error:function(){
									},
									success:function(){
										alertMsg.correct("数据已删除！");
										var tableName = jQuery("#${random}importDataDefine_tableName").val();
										var url = "findAllImportDataDefine?tableName="+tableName+"&navTabId=${navTabId}";
										url += "&subSystemCode=GZ&callBackFunc=gzContentGridTableReLoadData()";
										url = encodeURI(url);
										$.pdialog.reload(url,{ifr:true,hasSupcan:true,mask:true,resizable:false,maxable:false,width : 700,height : 550});
									}
								});
							}
						});
					}
				});
				/*修改接口*/
				jQuery("#${random}btnEditImportDataDefine").unbind("click").bind("click",function(){
					var oldInterfaceName = jQuery("#${random}importDataDefineDetail_listOfName").find("option:selected").text();
					if(oldInterfaceName == null || oldInterfaceName.length <= 0) {
						alertMsg.info("请先选择一个外部数据接口！");
					}else{
						jQuery("#${random}importDataDefine_interfaceName").val(oldInterfaceName);
						jQuery("#${random}dvInterfaceName").show();
						var rowIds = $(importDataDefineGridIdString).jqGrid('getDataIDs');
						for(var i = 0;i < rowIds.length; i++) {
							$(importDataDefineGridIdString).jqGrid('delRowData',rowIds[i]);
						}
						for(var index in importDataColModels) {
							jQuery(importDataDefineGrid).jqGrid("addRowData",index+1,importDataColModels[index]);
						}
						fullGridEdit(importDataDefineGridIdString);
								
					}
							
				});
				/*接口定义*/
				jQuery("#${random}btnImportDataDefine").unbind("click").bind("click",function() {
					jQuery("#${random}importDataDefine_interfaceName").val("");
					jQuery("#${random}importDataDefineDetail_listOfName").val("");
					jQuery("#${random}dvInterfaceName").show();
					jQuery("#${random}dvInterfaceList").hide();
					jQuery("#${random}btnDelImportDataDefine").attr("disabled",true);
					jQuery("#${random}btnDelImportDataDefine").css("color","gray");
					jQuery("#${random}btnImportDataDefine").attr("disabled",true);
					jQuery("#${random}btnImportDataDefine").css("color","gray");
					jQuery("#${random}btnShowImportDataLog").attr("disabled",true);
					jQuery("#${random}btnShowImportDataLog").css("color","gray");
					jQuery("#${random}btnSaveImportDataDefine").attr("disabled",false);
					jQuery("#${random}btnSaveImportDataDefine").css("color","#15428B");
					loadImportDataDefineGridCustom();
					var rowIds = $(importDataDefineGridIdString).jqGrid('getDataIDs');
					for(var i = 0;i < rowIds.length; i++) {
						$(importDataDefineGridIdString).jqGrid('delRowData',rowIds[i]);
					}
					var rowIds = $(importDataDefineGridCustom).jqGrid('getDataIDs');
					for(var i = 0;i < rowIds.length; i++) {
						$(importDataDefineGridCustom).jqGrid('delRowData',rowIds[i]);
					}
					for(var index in importDataColModels) {
						jQuery(importDataDefineGridCustom).jqGrid("addRowData",importDataColModels[index].detailId,importDataColModels[index]);
					}
					fullGridEdit(importDataDefineGridCustom);
					var url = "#DIA_inline?inlineId=${random}dvNewInterfaceDefine";
					$.pdialog.open(url,"dvNewInterface","接口定义",{
						ifr : true,
						mask : true,
						width : 500,
						resizable:false,
						maxable:false,
						height : 340
					});
					stopPropagation();
					checkRequiredList();
				});
				/*检查*/
				jQuery("#${random}btnExcelFormCheck").click(function() {
					var oldInterfaceName = jQuery("#${random}importDataDefineDetail_listOfName").find("option:selected").text();
					var inputExcelFile = jQuery("#${random}inputExcelFile").val();
					if(oldInterfaceName == null || oldInterfaceName.length <= 0) {
						alertMsg.info("请选择一个外部数据接口！");
					}else if(inputExcelFile=="" || (!inputExcelFile.endsWith(".xls") && !inputExcelFile.endsWith(".txt"))){
						alertMsg.info("请选择要导入的Excel或者文本文件！");
					}else{
						jQuery("#${random}excelFileName").val(inputExcelFile);
						var tableName = jQuery("#${random}importDataDefine_tableName").val();
						var interfaceId = jQuery("#${random}importDataDefineDetail_listOfName").val();
						jQuery("#${random}excelImportDataForm").attr("action","checkImportDataFile?interfaceId="+interfaceId+"&tableName="+tableName+"&popup=true&navTabId=${navTabId}&subSystemCode=${subSystemCode}");
						jQuery("#${random}btnExcelFormCheck").attr("disabled",true);
						jQuery("#${random}excelImportDataForm").submit();
						
					}
				});
				/*外部数据导入*/
				jQuery("#${random}btnExcelForm").click(function() {
					var oldInterfaceName = jQuery("#${random}importDataDefineDetail_listOfName").find("option:selected").text();
					var inputExcelFile = jQuery("#${random}inputExcelFile").val();
					var checkResult = jQuery("#${random}checkResultData").val();
					if(oldInterfaceName == null || oldInterfaceName.length <= 0) {
						alertMsg.info("请选择一个外部数据接口！");
					}else if(inputExcelFile=="" || (!inputExcelFile.endsWith(".xls") && !inputExcelFile.endsWith(".txt"))){
						alertMsg.info("请选择要导入的Excel或者文本文件！");
					}else if(!checkResult) {
						alertMsg.error("请【检查】通过后再导入！");
					}else{
						alertMsg.confirm("确认导入文件？",{
							okCall:function() {
								var interfaceId = jQuery("#${random}importDataDefineDetail_listOfName").val();
								jQuery.ajax({
									url:"importExcelDataDefine?interfaceId="+interfaceId+"&subSystemCode=${subSystemCode}&systemTime="+checkResult,
									dataType:"json",
									type:"get",
									success:function(data) {
										if(data.statusCode == 200&&"${callBackFunc}"){
											alertMsg.correct(data.message);
											eval("${callBackFunc}");
											$.pdialog.close("findAllImportDataDefine");
										} else {
											alertMsg.error(data.message);
										}
									}
								});
							}
						});
					}
				});
				/*添加*/
				jQuery("#${random}btnAddImportDataDefineDetail").click(function() {
					var temp = jQuery("#${random}dvInterfaceList").is(":hidden");
					var flag = jQuery("#${random}importDataDefineDetail_listOfName").val();
					if(temp == false && !flag) {
						alertMsg.info("请选择一个外部数据接口！");
						return;
					}
					jQuery("#${random}btnDelImportDataDefine").attr("disabled",true);
					jQuery("#${random}btnDelImportDataDefine").css("color","gray");
					jQuery("#${random}btnImportDataDefine").attr("disabled",true);
					jQuery("#${random}btnImportDataDefine").css("color","gray");
					jQuery("#${random}btnSaveImportDataDefine").attr("disabled",false);
					jQuery("#${random}btnSaveImportDataDefine").css("color","#15428B");
					loadImportDataDefineGridCustom();
					var currentRowDatas = jQuery(importDataDefineGridIdString).jqGrid("getRowData");
					var sourceRowDatas = new Array();
					outFor:for(var i in importDataColModels) {
						for(var j in currentRowDatas) {
							if(importDataColModels[i].entityName == currentRowDatas[j].entityName) {
								continue outFor;
							} else {
								continue;
							}
						}
						sourceRowDatas.push(importDataColModels[i]);
					}
					var rowIds = $(importDataDefineGridCustom).jqGrid('getDataIDs');
					for(var i = 0;i < rowIds.length; i++) {
						jQuery(importDataDefineGridCustom).jqGrid('delRowData',rowIds[i]);
					}
					for(var index in sourceRowDatas) {
						jQuery(importDataDefineGridCustom).jqGrid("addRowData",sourceRowDatas[index].detailId,sourceRowDatas[index]);
					}
					fullGridEdit(importDataDefineGridCustom);
					var url = "#DIA_inline?inlineId=${random}dvNewInterfaceDefine";
					$.pdialog.open(url,"dvNewInterface","添加",{
						ifr : true,
						mask : true,
						width : 500,
						resizable:false,
						maxable:false,
						height : 340
					}); 
					stopPropagation();
					checkRequiredList();
				});
				/*删除*/
				jQuery("#${random}btnDelImportDataDefineDetail").click(function() {
					var temp = jQuery("#${random}dvInterfaceList").is(":hidden");
					var flag = jQuery("#${random}importDataDefineDetail_listOfName").val();
					if(temp == false && !flag) {
						alertMsg.info("请选择一个外部数据接口！");
						return;
					}
					jQuery("#${random}btnDelImportDataDefine").attr("disabled",true);
					jQuery("#${random}btnDelImportDataDefine").css("color","gray");
					jQuery("#${random}btnImportDataDefine").attr("disabled",true);
					jQuery("#${random}btnImportDataDefine").css("color","gray");
					jQuery("#${random}btnSaveImportDataDefine").attr("disabled",false);
					jQuery("#${random}btnSaveImportDataDefine").css("color","#15428B");
					var sid =jQuery(importDataDefineGridIdString).jqGrid("getGridParam","selarrrow");
					if(sid == null || sid == "") {
						alertMsg.info("请选择一条或多条记录。");
	 					return;
					} else {
						var sidLen = sid.length;
						var temp = 0;
						for(var i=0;i<sidLen;i++) {
							var rowData = jQuery(importDataDefineGridIdString).jqGrid("getRowData",sid[i]);
							var req = importDataCheck.required;
							for(var j=0;j<req.length;j++) {
								if(req[j] == rowData.entityName) {
									temp++;
								}
							}
						}
						if(temp>0) {
							alertMsg.error("必选主键不可以删除。");
							return;
						}
						alertMsg.confirm("确认删除吗？",{
							okCall:function() {
								
								for(var i=0;i<sidLen;i++) {
									jQuery(importDataDefineGridIdString).jqGrid("delRowData",sid[0]);
								}
							}
						
						});
						
					}
				});
				/*上移*/
				jQuery("#${random}btnColUpMove").click(function() {
					jQuery("#${random}btnSaveImportDataDefine").attr("disabled",false);
					jQuery("#${random}btnSaveImportDataDefine").css("color","#15428B");
					var sid = jQuery(importDataDefineGridIdString).jqGrid("getGridParam","selarrrow");
					if(sid == null || sid.length != 1) {
						//alertMsg.error("请选择一条记录！");
						//jQuery()
			 			return;
					}
					var rowId = sid[0];
					var rowIds = jQuery(importDataDefineGridIdString).jqGrid("getDataIDs");
					var rowSn = jQuery.inArray(rowId, rowIds);
					var rowIdsLength = rowIds.length;
					if(rowSn == 0) {
						//alertMsg.error("已经为第一行不能上移！");
						return;
					}
					var preRowSn = rowSn - 1;
					var preRowId = rowIds[preRowSn];
					var rowData = jQuery(importDataDefineGridIdString).getRowData(rowId);
					var preRowData = jQuery(importDataDefineGridIdString).getRowData(preRowId);
					var rowDataClone = cloneObj(rowData);
					var preRowDataClone = cloneObj(preRowData);
					jQuery(importDataDefineGridIdString).jqGrid("delRowData",rowId);
					jQuery(importDataDefineGridIdString).jqGrid("delRowData",preRowId);
					if(rowSn == rowIdsLength - 1){
			    		jQuery(importDataDefineGridIdString).addRowData(preRowId, preRowDataClone, "last");
			        	jQuery(importDataDefineGridIdString).addRowData(rowId, rowDataClone, "before",preRowId);
			    	}else{
			    		var rowIdTemp = rowIds[rowSn+1];
			    		jQuery(importDataDefineGridIdString).addRowData(preRowId, preRowDataClone, "before",rowIdTemp);
			    		jQuery(importDataDefineGridIdString).addRowData(rowId, rowDataClone, "before",preRowId);
			    	}
					fullGridEdit("#${random}importDataDefine_gridtable");
			    	jQuery(importDataDefineGridIdString).jqGrid("setSelection",rowId);
				});
				/*下移*/
				jQuery("#${random}btnColDownMove").click(function() {
					jQuery("#${random}btnSaveImportDataDefine").attr("disabled",false);
					jQuery("#${random}btnSaveImportDataDefine").css("color","#15428B");
					var sid = jQuery(importDataDefineGridIdString).jqGrid("getGridParam","selarrrow");
					if(sid == null || sid.length != 1) {
						//alertMsg.error("请选择一条记录！");
			 			return;
					}
					var rowId = sid[0];
			    	var rowIds = jQuery(importDataDefineGridIdString).jqGrid("getDataIDs");
			    	var rowSn = jQuery.inArray(rowId, rowIds);
			    	var nextRowSn = rowSn+1;
			    	var rowIdsLength = rowIds.length;
			    	if(nextRowSn == rowIdsLength){
			    		//alertMsg.error("已经为最后一行不能下移！");
			    		return ;
			    	}
			    	var nextRowId = rowIds[nextRowSn];
			    	var rowData = jQuery(importDataDefineGridIdString).getRowData(rowId);
			    	var nextRowData = jQuery(importDataDefineGridIdString).getRowData(nextRowId);
			    	var rowDataClone = cloneObj(rowData);
			    	var nextRowDataClone = cloneObj(nextRowData);
			    	jQuery(importDataDefineGridIdString).delRowData(rowId);
			    	jQuery(importDataDefineGridIdString).delRowData(nextRowId);
			    	if(rowSn == 0){
			    		jQuery(importDataDefineGridIdString).addRowData(nextRowId, nextRowDataClone, "first");
			        	jQuery(importDataDefineGridIdString).addRowData(rowId, rowDataClone, "after",nextRowId);
			    	}else{
			    		var rowIdTemp = rowIds[rowSn-1];
			    		jQuery(importDataDefineGridIdString).addRowData(nextRowId, nextRowDataClone, "after",rowIdTemp);
			    		jQuery(importDataDefineGridIdString).addRowData(rowId, rowDataClone, "after",nextRowId);
			    	}
			    	fullGridEdit("#${random}importDataDefine_gridtable");
					jQuery(importDataDefineGridIdString).jqGrid("setSelection",rowId);
				});
				
				/*查看日志*/
				jQuery("#${random}btnShowImportDataLog").click(function() {
					//alertMsg.info("ShowLog!");
					//var temp = jQuery("#${random}dvInterfaceList").is(":hidden");
					var flag = jQuery("#${random}importDataDefineDetail_listOfName").val();
					if(!flag) {
						alertMsg.info("请选择一个外部数据接口！");
						return;
					}
					var url = "showImportDataLog?taskInterId=" + flag;
					var winTitle="导入日志";
					url = encodeURI(url);
					$.pdialog.open(url,"showImportDataLog",winTitle, {ifr:true,mask:true,resizable:true,maxable:true,width : 685,height : 450});
					stopPropagation();
				});
	});
		function reloadImportDataDefineGridTable(interfaceId) {
			if(interfaceId) {
				jQuery.ajax({
				url:'findImportDataDefineById?interfaceId='+interfaceId,
				dataType:'json',
				error:function() {
				},
				success:function(returnDataList) {
					var details = returnDataList.details;
					var rowIds = $("#${random}importDataDefine_gridtable").jqGrid('getDataIDs');
					for(var i = 0;i < rowIds.length; i++) {
						$(importDataDefineGridIdString).jqGrid('delRowData',rowIds[i]);
					}
					for (var index in details) {
						jQuery("#${random}importDataDefine_gridtable").jqGrid("addRowData",details[index].detailId,details[index]);
					}
					//按照sn排序
					//jQuery("#${random}importDataDefine_gridtable").jqGrid("sortGrid","sn",true);
					fullGridEdit("#${random}importDataDefine_gridtable");
					var oldInterfaceName = jQuery("#${random}importDataDefineDetail_listOfName").find("option:selected").text();
					jQuery("#${random}importDataDefine_interfaceName").val(oldInterfaceName);
					}
				});
			}
		}
		function changeDefineList(data) {
			var oldInterfaceName = jQuery("#${random}importDataDefineDetail_listOfName").find("option:selected").text();
			jQuery("#${random}importDataDefine_interfaceName").val(oldInterfaceName);
			if(!oldInterfaceName) {
				var rowIds = $(importDataDefineGridIdString).jqGrid('getDataIDs');
				for(var i = 0;i < rowIds.length; i++) {
					$(importDataDefineGridIdString).jqGrid('delRowData',rowIds[i]);
				}
			} else {
				var interfaceId = data.value;
				reloadImportDataDefineGridTable(interfaceId);	
			}
		}
		/*取消*/
		function btnCancelImport(){
			jQuery("#${random}dvInterfaceName").hide();
			jQuery("#${random}dvInterfaceList").show();
			jQuery("#${random}btnDelImportDataDefine").attr("disabled",false);
			jQuery("#${random}btnDelImportDataDefine").css("color","#15428B");
			jQuery("#${random}btnImportDataDefine").attr("disabled",false);
			jQuery("#${random}btnImportDataDefine").css("color","#15428B");
			jQuery("#${random}btnShowImportDataLog").attr("disabled",false);
			jQuery("#${random}btnShowImportDataLog").css("color","#15428B");
			jQuery("#${random}btnSaveImportDataDefine").attr("disabled",true);
			jQuery("#${random}btnSaveImportDataDefine").css("color","gray");
			var rowIds = $(importDataDefineGridIdString).jqGrid('getDataIDs');
			for(var i = 0;i < rowIds.length; i++) {
				$(importDataDefineGridIdString).jqGrid('delRowData',rowIds[i]);
			}
		}
		/*Excel模板导出*/
		function btnOutputImportExcel(temp) {
				var interfaceId = jQuery("#${random}importDataDefineDetail_listOfName").val();
				var url = "OutputImportDataExcel";
				if(interfaceId) {
					url += "?interfaceId="+interfaceId;
					if(temp == "Excel") {
						url += "&temp=excel";
					} else if(temp == "Txt") {
						url += "&temp=txt";
					}
					jQuery("#${random}ifile").attr("src",url);
				} else {
					alertMsg.info("请选择一个外部数据接口！");
				}
				
		}
		/*检查必选项*/
		function checkRequiredList() {
			var rowDatas = jQuery(importDataDefineGridCustom).jqGrid("getRowData");
			var rowIds = jQuery(importDataDefineGridCustom).jqGrid("getDataIDs");
			for(var i = 0;i < rowDatas.length; i++) {
				var rowData = rowDatas[i];
				var req = importDataCheck.required;
				for(var j = 0 ;j < req.length; j++) {
					if(rowData.entityName == req[j]) {
						jQuery(importDataDefineGridCustom).jqGrid("setSelection",rowIds[i],false);
						break;
					}
				}
			}
			//jQuery(importDataDefineGridCustom).jqGrid("setGridParam",{page:1}).trigger("reloadGrid");
		}
		function importDataFormCallBack(data) {
			formCallBack(data);
			jQuery("#${random}btnExcelFormCheck").attr("disabled",false);
			if(data.statusCode == 200) {
				jQuery("#${random}checkResultData").val(data.systemTime);
			} else {
				jQuery("#${random}checkResultData").val("");
			}
		}
	</script>
</head>
<div class="page">
	<div class="pageContent">
		<form id="${random}importDataDefineForm" style="background: #e4ebf6;border-bottom: 1px solid #99BBE8;border-top:1px solid #99BBE8;" method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this,formCallBack);">
			<div style="width: 580px; padding: 10px 5px 10px 15px;">
				<div >
					<s:hidden key="tableName" id="%{random}importDataDefine_tableName"></s:hidden>
				</div>
				<div style="color: #15428B;">
					<div id="${random}dvInterfaceList" style="display: block;">
						<%-- <s:select list="importDataDefines" id="<s:property value='random'/>importDataDefineDetail_listOfName" key="importDataDefineDetail.listOfName" listKey="interfaceId" listValue="interfaceName" headerKey="" headerValue="" cssStyle="width:200px" onchange="changeDefineList(this);"></s:select> --%>
						<label><s:text name="importDataDefineDetail.listOfName"></s:text>:</label>
						<select id="${random }importDataDefineDetail_listOfName" style="width:200px" onchange="changeDefineList(this);">
						<option value=""></option>
						<c:forEach items="${importDataDefines }" var="item">
							<option value="${item.interfaceId }">${item.interfaceName}</option>
						</c:forEach>
						</select>
						<!-- <input type="button" value="Excel导出" style="color:#15428B;float:right;" onclick="btnOutputImportExcel();"></input> -->
						<label style = "float:right;margin-top: 4px;"><s:text name="模版导出"/>:
						<a style="text-decoration: underline;color: blue;cursor: pointer;" href="javascript:btnOutputImportExcel('Excel');">EXCEL</a>/<a style="text-decoration: underline;color: blue;cursor: pointer;" href="javascript:btnOutputImportExcel('Txt');">TXT</a>
						</label>
						<iframe id="${random}ifile" style="display:none"></iframe>
					</div>
					<div id="${random}dvInterfaceName" style="display: none;">
						<%-- <s:textfield id="<s:property value='random'/>importDataDefine_interfaceName" key="importDataDefine.interfaceName" cssClass="required" notrepeat='接口名称已存在' validatorParam="from ImportDataDefine define where define.interfaceName=%value%" /> --%>
						<label><s:text name="importDataDefine.interfaceName"></s:text>:</label>
						<input type="text" id="${random }importDataDefine_interfaceName" name="importDataDefine.interfaceName" class="required" notrepeat='接口名称已存在' validatorParam="from ImportDataDefine define where define.interfaceName=%value%" />
						<input type="button" value="取消" style="color:#15428B;float:right;" onclick="btnCancelImport();"></input>
					</div>
				</div>
			</div>
		</form>
		<div style="float: left;width:467px;">
			<div id="${random}importDataDefine_gridtable_div" class="grid-wrapdiv" buttonBar="width:450;height:300" style="height: 272px;">
				<input type="hidden" id="${random}importDataDefine_gridtable_navTabId" value="${sessionScope.navTabId}">
				<label style="display: none" id="${random}importDataDefine_gridtable_addTile">
					<s:text name="importDataDefineNew.title" />
				</label>
				<label style="display: none" id="${random}importDataDefine_gridtable_editTile"> <s:text name="importDataDefineEdit.title" />
				</label>
				<div id="${random}load_importDataDefine_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
				<table id="${random}importDataDefine_gridtable"></table>
				<!--<div id="${random}importDataDefineDetailPager"></div>-->
			</div>
		</div>
		<div style="float: left; background: #e4ebf6; width: 139px; height: 275px;border-left: 1px solid #99BBE8;">
			<div style="float:left;margin-left: 10px;margin-top: 100px;width:25px;">
				<ul>
					<li style="list-style-type:none"><a id="${random}btnColUpMove" href="javascript:"><img style="width:15px;height:25px;border: 1px solid gray;margin-bottom: 10px" src="${ctx}/styles/themes/rzlt_theme/ihos_images/icon_button/up.png"/></a></li>
					<li style="list-style-type:none"><a id="${random}btnColDownMove" href="javascript:"><img style="width:15px;height:25px;border: 1px solid gray;margin-top: 10px" src="${ctx}/styles/themes/rzlt_theme/ihos_images/icon_button/down.png"/></a></li>
				</ul>
			</div>
			<div class="importFunBtn" style="float:left;margin-top: 15px;">
				<button id="${random}btnImportDataDefine">接口定义</button>
				<br />
				<button id="${random}btnDelImportDataDefine">删除接口</button>
				<br />
				<button id="${random}btnAddImportDataDefineDetail">添加</button>
				<br />
				<button id="${random}btnDelImportDataDefineDetail">删除</button>
				<br />
				<button id="${random}btnShowImportDataLog">查看日志</button>
				<br />
				<button id="${random}btnSaveImportDataDefine">保存</button>
				<br />
				<button onclick="$.pdialog.close('findAllImportDataDefine');">退出</button>
			</div>
		</div>
	</div>
	<div style="padding-top:5px;border-top: 1px solid #99BBE8;width: 610px;height: 30px;color:#15428B;">
		<form id="${random}excelImportDataForm" style="width: 471px;margin-left: 30px;float: left;" method="post" action="" enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback(this,importDataFormCallBack);">
			<input type="hidden" id="${random}excelFileName" name="excelFileName" />
			<input type="hidden" id="${random}checkResultData" />
			<label>请选择文件：</label>
			<input type="file" id="${random}inputExcelFile" name="excelFile"/>
		</form>
		<button id="${random}btnExcelFormCheck" style="float: left;margin-right:5px;color:#15428B;">检查</button>
		<button id="${random}btnExcelForm" style="float: left;color:#15428B;">导入</button>
	</div>
</div>
<div class="page" id="${random}dvNewInterfaceDefine" style="display: none;">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="savebutton" id="${random}confirmSelectedImportData" href="javaScript:void(0);"><span><s:text
							name="button.confirm" /></span></a></li>
			<script type="text/javascript">
				jQuery("#${random}confirmSelectedImportData").click(function() {
					var importDataArray = new Array();
					var selRowIdCustom = jQuery(importDataDefineGridCustom).jqGrid("getGridParam","selarrrow");
					var selRowIdCustomLen = selRowIdCustom.length;
					for(var i=0;i<selRowIdCustomLen;i++) {
						var rowId = selRowIdCustom[i];
						var rowData = importDataDefineGridCustom.jqGrid("getRowData",rowId);
						var isConstraint = ("Yes"==rowData.isConstraint)?true:false;
						var isUpdate = ("Yes"==rowData.isUpdate)?true:false;
						var importData = {
								detailName : rowData.detailName,
								detailType : rowData.detailType,
								entityName : rowData.entityName,
								isConstraint : isConstraint,
								isUpdate : isUpdate
						};
						importDataArray.push(importData);
						jQuery(importDataDefineGridIdString).jqGrid("addRowData",rowData.detailId,importData);
						
					}
					for(var i = 0;i<selRowIdCustomLen;i++) {
						importDataDefineGridCustom.jqGrid("delRowData",selRowIdCustom[0]);
					}
					fullGridEdit(importDataDefineGridIdString);
					$.pdialog.closeCurrentDiv("${random}dvNewInterfaceDefine");

				});
			</script>
			<li><a class="canceleditbutton close" href="javaScript:void(0);"><span><s:text
							name="button.cancel" /></span></a></li>
		</ul>
	</div>
	<div class="pageContent" id="${random}detailPageContent">
		<div id="${random}importDataDefine_gridtable_div_custom" class="grid-wrapdiv" style="height: 275px">
			<div id="${random}load_importDataDefine_gridtable_custom" class='loading ui-state-default ui-state-active' style="display: none"></div>
			<table id="${random}importDataDefine_gridtable_custom" style="width: 450px;"></table>
		</div>
	</div>
	<script type="text/javascript">
	var importDataDefineCustomLayout;
	var importDataDefineGridCustom = jQuery("#${random}importDataDefine_gridtable_custom");
	function loadImportDataDefineGridCustom() {
		importDataDefineGridCustom
		.jqGrid({
			url : '',
			postData : importDataColModels,
			datatype : "local",
			colModel : [
	{name:'detailId',index:'detailId',align:'left',width:'88px',label : '<s:text name="importDataDefineDetail.detailId" />',highsearch:true,hidden:true,key:true},
	{name : 'detailName',index : 'detailName',width:"88px",align : 'left',label : '<s:text name="importDataDefineDetail.detailName" />',highsearch:true,hidden : false},
	{name : 'detailType',index : 'detailType',width:"88px",align : 'left',label : '<s:text name="importDataDefineDetail.detailType" />',highsearch:true,hidden : false,formatter:"select",editoptions:{value:"0:数字型;1:字符型;2:日期型;3:整型"}},
	{name : 'entityName',index : 'entityName',width:"88px",align : 'left',label : '<s:text name="importDataDefineDetail.entityName" />',highsearch:true,hidden : false},
	{name : 'isConstraint',index : 'isConstraint',width:"58px",align : 'center',label : '<s:text name="importDataDefineDetail.isConstraint" />',editable:true,edittype:"checkbox",editoptions:{value:"Yes:No",dataEvents:[{type:'click',fn: function(e) {
		var id = jQuery(this).attr("id");
		var temp = id.indexOf("_");
		var rowId = id.substr(0,temp);
		var req = importDataCheck.required;
		for(var j = 0 ;j < req.length; j++) {
			if(rowId == req[j]) {
				if(this.checked) {
					return;
				} else {
					jQuery(this).attr("checked",true);
					
				}
			}
		}
	}}]},hidden : false,formatter:"checkbox",highsearch:true},
	{name : 'isUpdate',index : 'isUpdate',width:"60px",align : 'center',label : '<s:text name="importDataDefineDetail.isUpdate" />',editable:true,edittype:"checkbox",editoptions:{value:"Yes:No",dataEvents:[{type:'click',fn: function(e) {
		var id = jQuery(this).attr("id");
		var temp = id.indexOf("_");
		var rowId = id.substr(0,temp);
		var req = importDataCheck.notUpdate;
		for(var j = 0 ;j < req.length; j++) {
			if(rowId == req[j]) {
				if(this.checked) {
					jQuery(this).attr("checked",false);
				} else {
					return;
				}
			}
		}
	}}]},hidden : false,formatter:"checkbox",highsearch:true},
	{name : 'sn',index : 'sn',width:"88px",align : 'center',label : '<s:text name="sn" />',sortable:true,highsearch:true,hidden : true}],
	jsonReader : {
				root : "importDataDefineDetails",
				page : "page",
				total : "total",
				records : "records",
				repeatitems : false
			},
			sortname : 'detailId',
			viewrecords : true,
			sortorder : 'asc',
			height : 245,
			gridview : true,
			rownumbers : true,
			loadui : "disable",
			multiselect : true,
			multiboxonly : false,
			shrinkToFit : false,
			autowidth : false,
			onSelectRow : function(rowid,status) {
				var rowData = jQuery(importDataDefineGridCustom).jqGrid("getRowData",rowid);
				var req = importDataCheck.required;
				for(var j = 0 ;j < req.length; j++) {
					if(rowData.entityName == req[j]) {
						if(status == true) {
							return;
						}else {
							jQuery(importDataDefineGridCustom).jqGrid("setSelection",rowid);
						}
						//jQuery(importDataDefineGridCustom).jqGrid("setCell",rowid,"isConstraint",{"editable":false}); 
					}
				}
			},
			onSelectAll : function(aRowids,status) {
				var rowids = aRowids;
				if(status == false) {
					var req = importDataCheck.required;
					for(var j = 0 ;j < req.length; j++) {
						jQuery(importDataDefineGridCustom).jqGrid("setSelection",req[j]);
					}
				}
			},
			gridComplete : function() {
				gridResize(null,null,"${random}importDataDefine_gridtable_custom","single");
				jQuery("#gview_${random}importDataDefine_gridtable_custom").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){ 
					jQuery(this).find("th").each(function(){
						jQuery(this).find("div").eq(0).css("line-height","18px");
					});
				});
				/* var dataTest = {
					"id" : "importDataDefine_gridtable_custom"
				};

				jQuery.publish("onLoadSelect",
						dataTest, null); */
				//makepager("importDataDefine_gridtable_custom");
			}
		});
	jQuery(importDataDefineGridCustom).jqGrid('bindKeys');
	}



	</script>
</div>