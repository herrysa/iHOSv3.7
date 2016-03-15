<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<head>
<title><fmt:message key="sourcepayinList.title" />
</title>
<meta name="heading"
	content="<fmt:message key='sourcepayinList.heading'/>" />
<meta name="menu" content="SourcepayinMenu" />
<script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
<script type="text/javascript">


	jQuery(function() {
		jQuery("#chargeTypeMingC").autocomplete(
				"autocomplete",
				{
					width : 300,
					multiple : false,
					autoFill : false,
					matchContains : true,
					matchCase : true,
					dataType : 'json',
					parse : function(test) {
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
									data : {
										showValue : data[i].chargeTypeName + ","+ data[i].cnCode + ","+data[i].chargeTypeId,
										id : data[i].chargeTypeId,
										name : data[i].chargeTypeName
									},
									value : data[i].chargeTypeId,
									result : data[i].chargeTypeName
								};
								/* rows[rows.length] = {
									data : data[i].chargeTypeId + ","
											+data[i].medOrLee + ","
											+ data[i].cnCode + ","
											+data[i].chargeTypeId + ":"
											+ data[i].chargeTypeId,
									value : data[i].chargeTypeId,
									result : data[i].chargeTypeName
								}; */
							}
							return rows;
						}
					},
					extraParams : {
						flag : 2,
						entity : "ChargeType",
						cloumnsStr : "chargeTypeName,medOrLee,cnCode",
						extra1 : " disabled=false  and leaf=true  and (",
						extra2 : ")"
					},
					formatItem : function(row) {
						if(typeof(row)=='string'){
							return row
						}else{
							return row.showValue;
						}
						//return dropId(row);
					},
					formatResult : function(row) {
						return row.showValue;
						//return dropId(row);
					}
				});
		jQuery("#chargeTypeMingC").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			//alert(row.id);
			jQuery("#chargeTypeIdC3").attr("value", row.id);
			//alert(jQuery("#chargeTypeId").attr("value"));
		});
	})
	jQuery(function() {
		var orgPriv = "${fns:u_readDPSql('org_dp')}";
		var branchPriv = "${fns:u_readDPSql('zx_branch_dp')}";
		var orgPrivSql = "and orgCode in "+orgPriv;
		if(orgPriv.indexOf("select") != -1 || orgPriv.indexOf("SELECT") != -1){
			orgPrivSql = "";
		}
		var branchPrivSql = " and branchCode in "+branchPriv;
		if(${herpType=="S1"} || branchPriv.indexOf("select") != -1 || branchPriv.indexOf("SELECT") != -1){
			branchPrivSql = "";
		}
		var extra1Str = " leaf=true and departmentId !='XT' "+orgPrivSql+branchPrivSql+" and ( ";
		jQuery("#deptIdC1").autocomplete(
				"autocomplete",
				{
					width : 300,
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
									data : data[i].departmentId + ","
									<c:if test="${herpType == 'M'}" >
											+ data[i].org.orgname + ","
									</c:if>
									<c:if test="${herpType == 'S2'}" >
											+ data[i].branchName + ","
									</c:if>
											+ data[i].deptCode + ","
											+ data[i].cnCode + ","
											+ data[i].name + ":"
											+ data[i].departmentId,
									value : data[i].name,
									result : data[i].name
								};
							}
							return rows;
						}
					},
					extraParams : {
						flag : 2,
						entity : "Department",
						cloumnsStr : "departmentId,deptCode,name,cnCode",
						extra1 : extra1Str,
						extra2 : ")"
					},
					formatItem : function(row) {
						return dropId(row);
					},
					formatResult : function(row) {
						return dropId(row);
					}
				});
		jQuery("#deptIdC1").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#departmentIdC1").attr("value", getId(row));
			//alert(jQuery("#zxDeptId").attr("value"));
		});
	});
	jQuery(function() {
		var orgPriv = "${fns:u_readDPSql('org_dp')}";
		var branchPriv = "${fns:u_readDPSql('kd_branch_dp')}";
		var orgPrivSql = "and orgCode in "+orgPriv;
		if(orgPriv.indexOf("select") != -1 || orgPriv.indexOf("SELECT") != -1){
			orgPrivSql = "";
		}
		var branchPrivSql = " and branchCode in "+branchPriv;
		if(${herpType=="S1"} || branchPriv.indexOf("select") != -1 || branchPriv.indexOf("SELECT") != -1){
			branchPrivSql = "";
		}
		var extra1Str = " leaf=true and departmentId !='XT' "+orgPrivSql+branchPrivSql+" and ( ";
		jQuery("#deptIdC2").autocomplete(
				"autocomplete",
				{
					width : 300,
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
									data : data[i].departmentId + ","
									<c:if test="${herpType == 'M'}" >
											+ data[i].org.orgname + ","
									</c:if>
									<c:if test="${herpType == 'S2'}" >
											+ data[i].branchName + ","
									</c:if>
											+ data[i].deptCode + ","
											+ data[i].cnCode + ","
											+ data[i].name + ":"
											+ data[i].departmentId,
									value : data[i].name,
									result : data[i].name
								};
							}
							return rows;
						}
					},
					extraParams : {
						flag : 2,
						entity : "Department",
						cloumnsStr : "departmentId,deptCode,name,cnCode",
						extra1 : extra1Str,
						extra2 : ")"
					},
					formatItem : function(row) {
						return dropId(row);
					},
					formatResult : function(row) {
						return dropId(row);
					}
				});
		jQuery("#deptIdC2").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#departmentIdC2").attr("value", getId(row));
			//alert(jQuery("#zxDeptId").attr("value"));
		});
	});
	jQuery(function() {
		jQuery("#chargeItemIdC").autocomplete("autocomplete",{
			width : 400,
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
								data : data[i].chargeItemId + ","
									+ data[i].chargeItemName+ ","
									// + data[i].chargeItemNo
									+ data[i].pyCode+ ","
									+ data[i].price + ":"
									+ data[i].chargeItemId,
								value : data[i].chargeItemId,
								result : data[i].chargeItemName
						};
					}
					return rows;
				}
			},
			extraParams : {
				flag : 2,
				entity : "ChargeItem",
				cloumnsStr : "chargeItemId,chargeItemName,price,pyCode",
				extra1 : "",
				extra2 : ""
			},
			formatItem : function(row) {
				return dropId(row);
			},
			formatResult : function(row) {
				return dropId(row);
			}
		});
		jQuery("#chargeItemIdC").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#chargeItemIdC4").attr("value", getId(row));
			//alert(jQuery("#zxDeptId").attr("value"));
		});
	});
	/*----------------------------------tooBar start-----------------------------------------------*/
	jQuery(function() {
		var sourcepayin_menuButtonArrJson = "${menuButtonArrJson}";
		var sourcepayin_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(sourcepayin_menuButtonArrJson,false)));
		var sourcepayin_toolButtonBar = new ToolButtonBar({el:$('#sourcepayin_buttonBar'),collection:sourcepayin_toolButtonCollection,attributes:{
			tableId : 'sourcepayin_gridtable',
			baseName : 'sourcepayin',
			width : 750,
			height : 600,
			base_URL : null,
			optId : null,
			fatherGrid : null,
			extraParam : null,
			selectNone : "请选择记录。",
			selectMore : "只能选择一条记录。",
			addTitle : '<s:text name="sourcepayinNew.title"/>',
			editTitle : null
		}}).render();
		
		var sourcepayin_function = new scriptFunction();
		sourcepayin_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.selectRecord){
				var sid = jQuery("#sourcepayin_gridtable").jqGrid('getGridParam','selarrrow');
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
		//添加
/* 		sourcepayin_toolButtonBar.addCallBody('01020101',function(){
			var url = "editSourcepayin?popup=true";
			var winTitle = '<fmt:message key="sourcepayinNew.title"/>';
			$.pdialog.open(url,"editSourcepayin", winTitle,{mask:true,width:750,height:600,maxable:true,resizable:true});
		},{}); */
		sourcepayin_toolButtonBar.addFunctionAdd('01020101');
		//删除
		sourcepayin_toolButtonBar.addCallBody('01020102',function(){
			delSourcepayinRecord();
		},{});
		//修改
		sourcepayin_toolButtonBar.addCallBody('01020103',function() {
			editSourcepayinRecord();
		},{});
		//导出本页数据
		sourcepayin_toolButtonBar.addCallBody('01020104',function() {
			//exportToExcel('sourcepayin_gridtable','Sourcepayin','收入数据','page');
			exportToExcelByAction('sourcepayin_gridtable','Sourcepayin','收入数据','page');
		},{});
		//导出当前全部数据
		sourcepayin_toolButtonBar.addCallBody('01020105',function() {
			exportToExcelByAction('sourcepayin_gridtable','Sourcepayin','收入数据','all');
		},{});
		//设置表格列
		var sourcepayin_setColShowButton = {id:'01020120',buttonLabel:'设置表格列',className:"settlebutton",show:true,enable:true,
				callBody : function() {
					setColShow('sourcepayin_gridtable','com.huge.ihos.inout.model.Sourcepayin');
				}};
		sourcepayin_toolButtonBar.addButton(sourcepayin_setColShowButton);
	});
	/*----------------------------------tooBar end -----------------------------------------------*/
	function exportToExcelByAction(gridId,entityName,title,outPutType){
	var url = jQuery("#"+gridId).jqGrid('getGridParam','url');
	var formData = jQuery("#"+gridId).jqGrid('getGridParam','formData');
	//var param = url.split("?")[1];
	//alert(json2str(jQuery("#sourcepayin_gridtable")[0].p.colModel));
	//return ;
	var colModel = jQuery("#"+gridId).jqGrid('getGridParam','colModel');
	var colDefine = new Array();
	var colDefineIndex = 0;
	for(var mi=0;mi<colModel.length;mi++){
		var col = colModel[mi];
		if(col.name!="rn"&&col.name!="cb"&&!col.hidden){
			var label = col.label?col.label:col.name;
			var type = col.formatter?col.formatter:1;
			var align = col.align?col.align:"left";
			var width = col.width?parseInt(col.width)*20:50*20;
			colDefine[colDefineIndex] = {name:col.name,type:type,align:align,width:width,label:label};
			colDefineIndex++;
		}
	}
	var colDefineStr = json2str(colDefine);
	var page=1,pageSize=20,sortname,sortorder;
	page = jQuery("#"+gridId).jqGrid('getGridParam','page');
	pageSize = jQuery("#"+gridId).jqGrid('getGridParam','rowNum');
	if(outPutType=='all'){
		pageSize = 100000;
		page = 1;
	}
	sortname = jQuery("#"+gridId).jqGrid('getGridParam','sortname');
	sortorder = jQuery("#"+gridId).jqGrid('getGridParam','sortorder');
	var searchParam = "&rows="+pageSize+"&page="+page+"&sidx="+sortname+"&sord="+sortorder;
	url += searchParam;
	//var u =  'outPutExcel?'+param+"&entityName="+entityName;
	$.ajax({
		url: url,
		type: 'post',
		data:{outputExcel:true,title:title,colDefineStr:colDefineStr},
		dataType: 'json',
		async:false,
		error: function(data){
			alertMsg.error("系统错误！");
		},
		success: function(data){
			var downLoadFileName = data["userdata"]["excelFullPath"];
			var filePathAndName = downLoadFileName.split("@@");
			var url = "${ctx}/downLoadExel?filePath="+filePathAndName[0]+"&fileName="+filePathAndName[1];
	 		//url=encodeURI(url);
	 		location.href=url;
		}
	}); 
}
	function refreshGridCurrentPage() {
		var jq = jQuery('#sourcepayin_gridtable');
		var currentPage = jq.jqGrid('getGridParam', 'page');
		jQuery('#sourcepayin_gridtable').trigger('reloadGrid', [ {
			page : currentPage
		} ]);
	}
	function addRecord() {
		var url = "editSourcepayin?popup=true";
		var winTitle = '<fmt:message key="sourcepayinNew.title"/>';
		openWindow(url, winTitle, " width=900");
	}
	function editSourcepayinRecord() {
		var sid = jQuery("#sourcepayin_gridtable").jqGrid('getGridParam',
		'selarrrow');
		if (sid == null || sid.length == 0) {
			//alert("<fmt:message key='list.selectNone'/>");
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
		}
		if (sid.length > 1) {
			//alert("<fmt:message key='list.selectMore'/>");
			alertMsg.error("<fmt:message key='list.selectMore'/>");
			return;
		} else {
			var row = jQuery("#sourcepayin_gridtable").jqGrid('getRowData',sid);
			var selectOper = row['operator.personId'];
			var currentPerson = '${currentUser.person.personId}';
			if(selectOper===currentPerson||selectOper===""){
				var url = "editSourcepayin?popup=true&sourcePayinId=" + sid+"&navTabId=sourcepayin_gridtable";
				var winTitle = '<fmt:message key="sourcepayinEdit.title"/>';
				$.pdialog.open(url, 'editSourcepayin', winTitle, {maxable:false,mask:false,width:750,height:530});　
			}else{
				alertMsg.error("您没有操作权限！");
			}
			
		}
	}
	function delSourcepayinRecord(){
		var sid = jQuery("#sourcepayin_gridtable").jqGrid('getGridParam','selarrrow');
		var editUrl = jQuery("#sourcepayin_gridtable").jqGrid('getGridParam', 'editurl');
		if (sid == null || sid.length == 0) {
			//alert("<fmt:message key='list.selectNone'/>");
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
		}else {
			for(var s=0;s<sid.length;s++){
				var row = jQuery("#sourcepayin_gridtable").jqGrid('getRowData',sid[s]);
				var selectOper = row['operator.personId'];
				var currentPerson = '${currentUser.person.personId}';
				if(selectOper===currentPerson||selectOper===""){
					alertMsg.confirm("确认删除？", {
						okCall: function(){
							jQuery.post(editUrl+"?id=" + sid+"&navTabId=sourcepayin_gridtable&oper=del", {
							}, formCallBack, "json");
						}
					});
				}else{
					alertMsg.error("您没有操作权限！");
					return;
				}
			}
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
	/*  window.onresize = function() {
		 var size = getWidthAndHeigh(true);
		jQuery("#sourcepayin_gridtable").jqGrid('setGridHeight', size.height).jqGrid('setGridWidth', size.width); 
		//alert(getWidthAndHeigh(true));
	};  */

	function sourcepayinGridReload() {
		var urlString = "sourcepayinGridList?1=1";
		var sqlString = "";
		if(${herpType=='S2'}){
			var kdBranchDP = "${fns:u_readDPSql('kd_branch_dp')}";
			var zxBranchDP = "${fns:u_readDPSql('zx_branch_dp')}";
			var srBranchDP = "${fns:u_readDPSql('srdata_branch')}";
			var operatorId = "${fns:userContextParam('loginPersonId')}";
			sqlString = "sourcePayinId IN (SELECT t.sourcePayinId FROM t_sourcepayin t WHERE t.operatorId = '" + operatorId + "'";
				sqlString += " OR t.kdBranchCode IN " + kdBranchDP;
				sqlString += " OR t.zxBranchCode IN " + zxBranchDP;
				sqlString += " OR t.srBranchCode IN " + srBranchDP;
				sqlString += " )";
			urlString += "&filter_SQS_sourcePayinId="+sqlString;
		}
		var startTimeId = jQuery("#startTimeId").val();
		var endTimeId = jQuery("#endTimeId").val();
		var departmentIdC1 = jQuery("#departmentIdC1").val();
		var departmentIdC2 = jQuery("#departmentIdC2").val();
		var outin = jQuery("#outin").val();
		var chargeTypeIdC3 = jQuery("#chargeTypeIdC3").val();
		var chargeItemIdC4 = jQuery("#chargeItemIdC4").val();
		/*处理收费项目代码包含&的情况*/
		chargeItemIdC4=chargeItemIdC4.replace(/\&/g,"%26"); 
		var remarks = jQuery("#remarks").val();
		var sourcepayinIsManual = jQuery("#sourcepayinIsManual").val();
		var sourcepayinOperatorName = jQuery("#sourcepayinOperatorName").val();
		var amountFrom = jQuery("#sourcepayin_amountFrom").val();
		var amountTo = jQuery("#sourcepayin_amountTo").val();
		var costOrg = jQuery("#sourcepayin_costOrg_id").val();
		var kdOrg = jQuery("#sourcepayin_kdOrg_id").val();
		var zxOrg = jQuery("#sourcepayin_zxOrg_id").val();
		var srBranch = jQuery("#sourcepayin_srBranch").val();
		var kdBranch = jQuery("#sourcepayin_kdBranch").val();
		var zxBranch = jQuery("#sourcepayin_zxBranch").val();
		urlString = urlString + "&filter_GES_checkPeriod=" + startTimeId
				+ "&filter_LES_checkPeriod=" + endTimeId
				+ "&filter_EQS_zxDept.departmentId=" + departmentIdC1
				+ "&filter_EQS_kdDept.departmentId=" + departmentIdC2
				+ "&filter_INS_costOrg.orgCode=" + costOrg
				+ "&filter_INS_kdOrg.orgCode=" + kdOrg
				+ "&filter_INS_zxOrg.orgCode=" + zxOrg
				+ "&filter_EQS_srBranch.branchCode=" + srBranch
				+ "&filter_EQS_kdBranch.branchCode=" + kdBranch
				+ "&filter_EQS_zxBranch.branchCode=" + zxBranch
				+ "&filter_EQS_chargeType.chargeTypeId=" + chargeTypeIdC3
				+ "&filter_GEG_amount="+amountFrom+"&filter_LEG_amount="+amountTo
				+ "&filter_EQS_outin=" + outin + "&filter_LIKES_remarks="
				+ remarks + "&filter_EQS_chargeItem.chargeItemId=" + chargeItemIdC4+"&filter_EQB_manual="+sourcepayinIsManual + "&filter_LES_operator.name="+sourcepayinOperatorName;
		//alert(urlString);
		urlString = encodeURI(urlString);
		jQuery("#sourcepayin_gridtable").jqGrid('setGridParam', {
			url : urlString,
			page : 1
		}).trigger("reloadGrid");
		//alert(urlString);
	}
	
	function onBlurClearContent(objZ,objH){
		if(objZ.val()==""){
			objH.attr("value","");
		}
	}
	
	function selectMonthToInput(yearID, monthID, year_monthID) {

		if (year_monthID.value != ''
				&& (yearID.options[yearID.selectedIndex].value == '' || monthID.options[monthID.selectedIndex].value == '')) {
			yearID.options[0].selected = true;
			monthID.options[0].selected = true;
			year_monthID.value = '';
			return false;
		}

		if (year_monthID.value == ''
				&& yearID.options[yearID.selectedIndex].value == '') {
			yearID.options[1].selected = true;
		}

		if (year_monthID.value == ''
				&& monthID.options[monthID.selectedIndex].value == '') {
			monthID.options[1].selected = true;
		}

		year_monthID.value = yearID.value + monthID.value;
		// alert(year_monthID.value);
	}
	//var sourcepayinLayout;
	jQuery(document).ready(function() { 
		/* sourcepayinLayout = makeLayout({
			baseName: 'sourcepayin', 
			tableIds: 'sourcepayin_gridtable'
		}, null);
		sourcepayinLayout.resizeAll(); */
		initSourcePayinGrid();
		var amountSum = "${amountSum}";
		jQuery("#allSum").text(parseFloat(amountSum).toFixed(2));
		/* jQuery.subscribe('onGridComplete', function(event, data) {
			
			
			
		}); */
		//权限控制
			/*单位权限控制*/
		jQuery(".sourcepayin_orgtype").each(function() {
			var id = jQuery(this).attr("id");
			var orgNumber = "${orgNumber}";
			if(orgNumber == "1") {
				jQuery("#"+id+"_label").hide();
			}
			var herpType = "${fns:getHerpType()}";
			if(herpType == "M") {
				var orgPriv = "";
				var sql = "select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT'";
				if(id == "sourcepayin_kdOrg") {
					orgPriv = "${fns:u_readDP('org_dp')}";
					if(orgPriv) {
						sql += " and orgCode in ${fns:u_readDPSql('org_dp')} ";
					} else {
						sql += " and 1=2";
					}
				} else if(id == "sourcepayin_zxOrg") {
					orgPriv = "${fns:u_readDP('org_dp')}";
					if(orgPriv) {
						sql += " and orgCode in ${fns:u_readDPSql('org_dp')} ";
					} else {
						sql += " and 1=2";
					}
				} else if(id == "sourcepayin_costOrg") {
					orgPriv = "${fns:u_readDP('org_dp')}";
					if(orgPriv) {
						sql += " and orgCode in ${fns:u_readDPSql('org_dp')} ";
					} else {
						sql += " and 1=2";
					}
				}
				sql += " ORDER BY orderCol"; 
				jQuery("#" + id).treeselect({
					optType : "multi",
					dataType : 'sql',
					sql : sql,
					exceptnullparent : true,
					lazy : false,
					minWidth : '180px',
					selectParent : false
				});
				/*var orgPrivArr = orgPriv.split(",");
				 if(orgPrivArr.length == 1 && orgPrivArr[0] != "") {
					jQuery("#" + id).attr("readonly","readonly");
					jQuery("#" + id).unbind('click');
				} */
			} else {
				jQuery("#"+id+"_label").hide();
			}
		});
			/*院区权限控制*/
			/* jQuery(".sourcepayin_branchtype").each(function() {
				var id = jQuery(this).attr("id");
				var herpType = "${fns:getHerpType()}";
				if(herpType != "S2") {
					jQuery("#"+id+"_label").hide();
				}
			
			 }); */
		/* jQuery(".sourcepayin_branchtype").each(function() {
			var id = jQuery(this).attr("id");
			
			var herpType = "${fns:getHerpType()}";
			if(herpType == "S2") {
				var orgPriv = "";
				var sql = "select branchCode id,branchName name,'-1' parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,branchCode orderCol from t_branch where disabled=0 AND branchCode<>'XT'";
				if(id == "sourcepayin_kdBranch") {
					orgPriv = "${fns:u_readDP('kd_branch_dp')}";
					if(orgPriv) {
						sql += " and branchCode in ${fns:u_readDPSql('kd_branch_dp')} ";
					} else {
						sql += " and 1=2";
					}
				} else if(id == "sourcepayin_zxBranch") {
					orgPriv = "${fns:u_readDP('zx_branch_dp')}";
					if(orgPriv) {
						sql += " and branchCode in ${fns:u_readDPSql('zx_branch_dp')} ";
					} else {
						sql += " and 1=2";
					}
				} else if(id == "sourcepayin_srBranch") {
					orgPriv = "${fns:u_readDP('srdata_branch')}";
					if(orgPriv) {
						sql += " and branchCode in ${fns:u_readDPSql('srdata_branch')} ";
					} else {
						sql += " and 1=2";
					}
				}
				sql += " ORDER BY orderCol"; 
				jQuery("#" + id).treeselect({
					optType : "multi",
					dataType : 'sql',
					sql : sql,
					exceptnullparent : true,
					lazy : false,
					minWidth : '180px',
					selectParent : false
				});
				
			} else {
				jQuery("#"+id+"_label").hide();
			}
			
		}); */
		/* var herpType = "${fns:getHerpType()}";
		if(herpType == "M") {
			var orgPriv = "${fns:u_readDP('org_dp')}";
			var orgPrivArr = orgPriv.split(",");
			var sql = "select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT'";
			if(orgPriv) {
				sql += " and orgCode in ${fns:u_readDPSql('org_dp')} ";
			}
			sql += " ORDER BY orderCol"; 
			jQuery("#sourcepayin_costOrg").treeselect({
				initSelect : orgPriv,
				optType : "multi",
				dataType : 'sql',
				sql : sql,
				exceptnullparent : true,
				lazy : false,
				minWidth : '180px',
				selectParent : false
			});
			if(orgPrivArr.length == 1 && orgPrivArr[0] != "") {
				jQuery("#sourcepayin_costOrg").attr("readonly","readonly");
				jQuery("#sourcepayin_costOrg").unbind('click');
			}
		} else if(herpType == "S") {
			jQuery("#sourcepayin_costOrg_label").hide();
			jQuery("#sourcepayin_zxOrg_label").hide();
			jQuery("#sourcepayin_kdOrg_label").hide();
		} */
		
	});
	
	/* function chargeValidate(){
		alert();
	}
	var currentPerson = '${currentUser.person.personId}';
	var currentCheckPeriod = '${currentPeriod}';
	var ii=0; */
	
	function onSourcePayinGridComplete(){
		var sumDataJson = jQuery("#sourcepayin_gridtable").getGridParam('userData'); 
		var sumDataStr = sumDataJson.amount;
		jQuery("#allSum").text(formatNum(parseFloat(sumDataStr).toFixed(2)));
		var amount = jQuery("#sourcepayin_gridtable").getCol('amount',false,'sum');
		jQuery("#pageSum").text(formatNum(amount.toFixed(2)));
		
		jQuery("#sourcepayin_gridtable").find("tr").each(function(){
			var checkPeriod= jQuery(this).find("td").eq(3).text();
			var person= jQuery(this).find("td").eq(jQuery(this).find("td").length-4).html();
			if(person==""){
				person = "";
			}
			var priviliage = checkPriviliage(checkPeriod,person);
			if(!priviliage){
				jQuery(this).find("td").each(function(){
					if(jQuery(this).children().eq(0).attr("type")=="checkbox"){
						jQuery(this).children().eq(0).attr("disabled","true");
						/* jQuery(this).parent().bind("click",function(){
							alert(jQuery(this).html());
							jQuery(this).removeClass("ui-widget-content jqgrow ui-row-ltr ui-state-highlight");
							jQuery(this).addClass("ui-widget-content jqgrow ui-row-ltr");
						}); */
					}
			});
			}
		});
	}
	function initSourcePayinGrid(){
		var initSourcepayinFlag = 0;
		var sourcePayinGrid = jQuery("#sourcepayin_gridtable");
		var urlString = "sourcepayinGridList?filter_GES_checkPeriod=${currentPeriod}&filter_LES_checkPeriod=${currentPeriod}";
		var sqlString = "";
		if(${herpType=='S2'}){
			var kdBranchDP = "${fns:u_readDPSql('kd_branch_dp')}";
			var zxBranchDP = "${fns:u_readDPSql('zx_branch_dp')}";
			var srBranchDP = "${fns:u_readDPSql('srdata_branch')}";
			var operatorId = "${fns:userContextParam('loginPersonId')}";
			sqlString = "sourcePayinId IN (SELECT t.sourcePayinId FROM t_sourcepayin t WHERE t.operatorId = '" + operatorId + "'";
				sqlString += " OR t.kdBranchCode IN " + kdBranchDP;
				sqlString += " OR t.zxBranchCode IN " + zxBranchDP;
				sqlString += " OR t.srBranchCode IN " + srBranchDP;
				sqlString += " )";
			urlString += "&filter_SQS_sourcePayinId="+sqlString;
		}
		sourcePayinGrid.jqGrid({
	    	url : urlString,
	    	editurl:"sourcepayinGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'sourcePayinId',index:'sourcePayinId',align:'center',label : '<s:text name="sourcepayin.sourcePayinId" />',hidden:true,key:true},				
				{name:'checkPeriod',index:'checkPeriod',align:'left',width:"90",label : '<s:text name="sourcepayin.checkPeriod" />',hidden:false,highsearch:true},				
				{name:'kdDept.name',index:'kdDept.name',align:'left',width:"130",label : '<s:text name="sourcepayin.kdDept" />',hidden:false,highsearch:true},				
				{name:'chargeType.chargeTypeName',index:'chargeType.chargeTypeName',align:'left',width:"90",label : '<s:text name="sourcepayin.chargeType" />',hidden:false,highsearch:true},				
				<c:if test="${herpType == 'M'}">
				{name:'kdOrg.shortName',index:'kdOrg.shortName',align:'left',width:"130",label : '<s:text name="hisOrg.kd" /><s:text name="hisOrg.orgName" />',hidden:true,highsearch:true},				
				{name:'zxOrg.shortName',index:'zxOrg.shortName',align:'left',width:"130",label : '<s:text name="hisOrg.zx" /><s:text name="hisOrg.orgName" />',hidden:true,highsearch:true},				
				{name:'costOrg.shortName',index:'costOrg.shortName',align:'left',width:"130",label : '<s:text name="sourcepayin.costOrg" /><s:text name="hisOrg.orgName" />',hidden:true,highsearch:true},				
				</c:if>
				<c:if test="${herpType == 'S2'}">
				{name:'kdBranch.branchName',index:'kdBranch.branchName',align:'left',width:"130",label:'<s:text name="hisOrg.kd" /><s:text name="hisOrg.branchName" />',hidden:true,highsearch:true},
				{name:'zxBranch.branchName',index:'zxBranch.branchName',align:'left',width:"130",label:'<s:text name="hisOrg.zx" /><s:text name="hisOrg.branchName" />',hidden:true,highsearch:true},
				{name:'srBranch.branchName',index:'srBranch.branchName',align:'left',width:"130",label:'<s:text name="sourcepayin.costOrg" /><s:text name="hisOrg.branchName" />',hidden:true,highsearch:true},
				</c:if>
				{name:'zxDept.name',index:'zxDept.name',align:'left',width:"90",label : '<s:text name="sourcepayin.zxDept" />',hidden:false,highsearch:true},				
				{name:'amount',index:'amount',align:'right',width:"90",label : '<s:text name="sourcepayin.amount" />',hidden:false,highsearch:true,formatter:"currency",formatoptions:{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 2, prefix: '', suffix:'', defaultValue: '0.00'}},				
				{name:'outin',index:'outin',align:'center',width:"100",label : '<s:text name="sourcepayin.outin" />',hidden:false,highsearch:true},				
				{name:'chargeItem.chargeItemId',index:'chargeItem.chargeItemId',align:'left',width:"90",label : '<s:text name="chargeItem.chargeItemId" />',hidden:false,highsearch:true},				
				{name:'chargeItem.chargeItemName',index:'chargeItem.chargeItemName',align:'left',width:"180",label : '<s:text name="chargeItem.chargeItemName" />',hidden:false,highsearch:true},				
				{name:'lici',index:'lici',align:'right',width:"70",label : '<s:text name="sourcepayin.lc" />',hidden:false,highsearch:true},				
				{name:'operator.name',index:'operator.name',align:'left',width:"90",label : '<s:text name="sourcepayin.operator.name" />',hidden:false,highsearch:true},				
				{name:'operator.personId',index:'operator.personId',align:'left',width:"9",label : '<s:text name="sourcepayin.operator.personId" />',hidden:true},				
				{name:'manual',index:'manual',align:'center',width:"50",label : '<s:text name="sourcepayin.manual" />',hidden:false,formatter:"checkbox",highsearch:true},
				{name:'status',index:'status',align:'left',width:"70",label : '<s:text name="sourcepayin.status" />',formatter:'select',editoptions:{value:"0:新建;1:已结帐"},hidden:true,highsearch:true},	
				{name:'remarks',index:'remarks',align:'left',width:"150",label : '<s:text name="sourcepayin.remarks" />',hidden:false,highsearch:true}	
			],
	        jsonReader : {
				root : "sourcepayins", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'sourcePayinId',
	        viewrecords: true,
	        sortorder: 'desc',
	        height:300,
	        gridview:true,
	        rownumbers:true,
	        loadui: "disable",
	        multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
	        onSelectRow: function(rowid) {
	       
	       	},
			 gridComplete:function(){
				 var rowNum = jQuery(this).getDataIDs().length;
				 if(rowNum <= 0) {
					 var tw = jQuery("#sourcepayin_gridtable").outerWidth();
					 jQuery("#sourcepayin_gridtable").parent().width(tw);
					 jQuery("#sourcepayin_gridtable").parent().height(1);

				 }
				 gridContainerResize("sourcepayin","div");
				 onSourcePayinGridComplete();
	           var dataTest = {"id":"sourcepayin_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("sourcepayin_gridtable");
	      	 initSourcepayinFlag = initColumn('sourcepayin_gridtable','com.huge.ihos.inout.model.Sourcepayin',initSourcepayinFlag);
	       	} 
	    });
	    jQuery(sourcePayinGrid).jqGrid('bindKeys');
	}
</script>

</head>
<!-- <div id="testss"></div> -->
<div class="page" id="sourcepayin_page">
	<div id="sourcepayin_container">
		<div id="sourcepayin_layout-center" class="pane ui-layout-center">
			<div id="sourcepayin_pageHeader" class="pageHeader">
				<form onsubmit="return navTabSearch(this);" action="userGridList"
					method="post" class="queryarea-form">
					<div class="searchBar">
						<div class="searchContent">
								<label class="queryarea-label"><fmt:message key='sourcepayin.checkPeriod' />：从 <input
									class="input-mini" type="text" id="startTimeId"
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"
									value="${currentPeriod}" size="8" />到<input
									class="input-mini" type="text" id="endTimeId"
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"
									value="${currentPeriod}" size="8" /> <s:hidden
										id="departmentIdC1" /> <s:hidden id="departmentIdC2" /> <s:hidden
										id="chargeTypeIdC3" /> <s:hidden id="chargeItemIdC4" />
								</label>
								
								<label class="queryarea-label"><fmt:message key='sourcepayin.kdDept' />： <input
									id="deptIdC2" value="拼音/汉字/编码" size="15"
									onfocus="clearInput(this,jQuery('#departmentIdC2'))"
									class="input-medium defaultValueStyle" onblur="setDefaultValue(this,jQuery('#departmentIdC2'))" onkeydown="setTextColor(this)"/>
								</label>
								
								<label class="queryarea-label"><fmt:message key='sourcepayin.zxDept' />： <input
									id="deptIdC1" value="拼音/汉字/编码" size="15"
									onfocus="clearInput(this,jQuery('#departmentIdC1'))"
									class="defaultValueStyle" onblur="setDefaultValue(this,jQuery('#departmentIdC1'))" onkeydown="setTextColor(this)"/>
								</label>
								
								<label class="queryarea-label">
									<fmt:message key="sourcecost.operator.name"></fmt:message>：
									<input type="text" id="sourcepayinOperatorName" class="input-medium" size="10"/>
								</label>
								
								<label class="queryarea-label"><fmt:message key='sourcepayin.chargeType' />： <input
									id="chargeTypeMingC" value="拼音/汉字/编码" size="14"
									onfocus="clearInput(this,jQuery('#chargeTypeIdC3'))"
									class="defaultValueStyle" onblur="setDefaultValue(this,jQuery('#chargeTypeIdC3'))" onkeydown="setTextColor(this)"/>
								</label>
								
							<!-- </tr>
							<tr> -->
								<label class="queryarea-label"><fmt:message key='sourcepayin.outin' />： <appfuse:singleSelect
										htmlFieldName="outin" paraDisString="住院;门诊"
										paraValueString="住院;门诊" cssClass="input-small"></appfuse:singleSelect>
								</label>
								
								<label class="queryarea-label">
									<fmt:message key='sourcepayin.chargeItemId' />： <input
									id="chargeItemIdC" value="拼音/汉字/编码" size="14"
									onfocus="clearInput(this,jQuery('#chargeItemIdC4'))"
									class="defaultValueStyle" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"/>
								</label>
								
								<label class="queryarea-label">
									<fmt:message key="sourcepayin.amount"></fmt:message>：
									<input type="text" id="sourcepayin_amountFrom" class="input-mini" size="8"/>-<input type="text" size="8" id="sourcepayin_amountTo" class="input-mini" />
								</label>
								<label class="queryarea-label">
								<fmt:message key='sourcepayin.remarks' />： <input
									type="text" class="input-big" id="remarks" size="15">
								</label>
								
								<label class="queryarea-label"><fmt:message key="sourcecost.manual"></fmt:message>:
								<appfuse:singleSelect htmlFieldName="sourcepayinIsManual" paraDisString="否;是" paraValueString="false;true" cssClass="input-small"></appfuse:singleSelect>
								</label>
								<label class="queryarea-label" id="sourcepayin_kdOrg_label">
									<fmt:message key='hisOrg.kd' /><fmt:message key='hisOrg.orgName' />:
									<input type="text" class="sourcepayin_orgtype" id="sourcepayin_kdOrg"/>
									<input type="hidden" id="sourcepayin_kdOrg_id">
								</label>
								<label class="queryarea-label" id="sourcepayin_zxOrg_label">
									<fmt:message key='hisOrg.zx' /><fmt:message key='hisOrg.orgName' />:
									<input type="text" class="sourcepayin_orgtype" id="sourcepayin_zxOrg"/>
									<input type="hidden" id="sourcepayin_zxOrg_id">
								</label>
								<label class="queryarea-label" id="sourcepayin_costOrg_label">
									<fmt:message key='sourcepayin.costOrg' /><fmt:message key='hisOrg.orgName' />:
									<input type="text" class="sourcepayin_orgtype" id="sourcepayin_costOrg"/>
									<input type="hidden" id="sourcepayin_costOrg_id">
								</label>
								<label class="queryarea-label" id="sourcepayin_kdBranch_label" style="${herpType=='S2'?'':'display:none'}">
									<fmt:message key='hisOrg.kd' /><fmt:message key='hisOrg.branchName' />:
									<s:select list="kdBranches" headerKey="" headerValue="--" listKey="branchCode" listValue="branchName" id="sourcepayin_kdBranch"  cssClass="sourcepayin_branchtype" ></s:select>
								</label>
								<label class="queryarea-label" id="sourcepayin_zxBranch_label" style="${herpType=='S2'?'':'display:none'}">
									<fmt:message key='hisOrg.zx' /><fmt:message key='hisOrg.branchName' />:
									<s:select list="zxBranches" headerKey="" headerValue="--" listKey="branchCode" listValue="branchName" id="sourcepayin_zxBranch"  cssClass="sourcepayin_branchtype" ></s:select>
								</label>
								<label class="queryarea-label" id="sourcepayin_srBranch_label" style="${herpType=='S2'?'':'display:none'}">
									<fmt:message key='sourcepayin.costOrg' /><fmt:message key='hisOrg.branchName' />:
									<s:select list="srBranches" headerKey="" headerValue="--" listKey="branchCode" listValue="branchName" id="sourcepayin_srBranch" cssClass="sourcepayin_branchtype" ></s:select>
								</label>
								<div class="buttonActive" style="float:right">
								<div class="buttonContent">
									<button type="button" onclick="sourcepayinGridReload()"><s:text name='button.search'/></button>
								</div>
							</div>
							
							</div>
							
							<%-- <div class="subBar">
								<ul>
									<li>
									<div class="buttonActive">
									<div class="buttonContent">
									<button type="button" onclick="sourcepayinGridReload()"><s:text name='button.search'/></button>
									</div>
									</div>
									</li>
								</ul>
							</div> --%>
								<%-- <td valign="bottom"><ul>
										<li><div class="buttonActive">
												<div class="buttonContent">
													<button type="button" onclick="sourcepayinGridReload()">
														<fmt:message key='button.search' />
													</button>
												</div>
											</div></li>
									</ul>
								</td>
							</tr>
						</table> --%>

					</div>
				</form>
			</div>
			<div class="pageContent">
				<sj:dialog id="mybuttondialog"
					buttons="{'OK':function() { okButton(); }}" autoOpen="false"
					modal="true" title="%{getText('messageDialog.title')}" />
				<s:hidden name="currentPeriod" value="currentPeriod" />
				<s:url id="editurl" action="sourcepayinGridEdit" />
				<s:url id="remoteurl" action="sourcepayinGridList" escapeAmp="false">
					<s:param name="filter_LES_checkPeriod">
						<s:property value="%{currentPeriod}"/>
					</s:param>
					<s:param name="filter_GES_checkPeriod" value="%{currentPeriod}">
						<s:property value="%{currentPeriod}"/>
					</s:param>
				</s:url>
				<div class="panelBar" id="sourcepayin_buttonBar">
					<%-- <ul class="toolBar">
						<li><a id="sourcepayin_gridtable_add" class="addbutton"
							href="javaScript:"><span><fmt:message key="button.add" />
							</span> </a>
						</li>
						<li><a class="delbutton"
							href="javaScript:delSourcepayinRecord()"><span>删除</span> </a>
						</li>
						<li><a class="changebutton" href="javaScript:editSourcepayinRecord()"><span><fmt:message
										key="button.edit" /> </span> </a>
						</li>
						<li><a class="excelbutton" href="javaScript:exportToExcel('sourcepayin_gridtable','Sourcepayin','收入数据','page')"><span>导出本页数据 </span> </a>
						</li>
						<li><a class="excelbutton" href="javaScript:exportToExcel('sourcepayin_gridtable','Sourcepayin','收入数据','all')"><span>导出当前全部数据 </span> </a>
						</li>
						<li>
							<a class="settlebutton"  href="javaScript:setColShow('sourcepayin_gridtable','ccom.huge.ihos.inout.model.Sourcepayin')"><span><s:text name="button.setColShow" /></span></a>
						</li>
						<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
					</ul> --%>
				</div>
				<div align="right"  style="margin-top: -20px;margin-right:5px">
				本页金额合计：<label id="pageSum"></label>总计：<label id="allSum" ></label>
				</div>
				<div id="sourcepayin_gridtable_div"
					style="margin-left: 2px; margin-top: 10px;" class="grid-wrapdiv"
					buttonBar="optId:sourcePayinId;width:750;height:600">
					<input type="hidden" id="sourcepayin_gridtable_navTabId"
						value="${sessionScope.navTabId}"> <label
						style="display: none" id="sourcepayin_gridtable_addTile">
						<fmt:message key="sourcepayinNew.title" /> </label> <label
						style="display: none" id="sourcepayin_gridtable_editTile">
						<fmt:message key="sourcepayinEdit.title" /> </label> <label
						style="display: none" id="sourcepayin_gridtable_selectNone">
						<fmt:message key='list.selectNone' /> </label> <label
						style="display: none" id="sourcepayin_gridtable_selectMore">
						<fmt:message key='list.selectMore' /> </label>
						<div id="load_sourcepayin_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
						<table id="sourcepayin_gridtable"></table>
				</div>
			</div>
			<div class="panelBar" id="sourcepayin_pageBar">
				<div class="pages">
					<span>显示</span> <select id="sourcepayin_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span>条，共<label id="sourcepayin_gridtable_totals"></label>条</span>
				</div>

				<div id="sourcepayin_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

			</div>
		</div>
	</div>
</div>