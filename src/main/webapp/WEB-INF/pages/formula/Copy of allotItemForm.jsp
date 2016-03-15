<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="allotItemDetail.title" /></title>
<meta name="heading"
	content="<fmt:message key='allotItemDetail.heading'/>" />



<script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />

<script type="text/javascript">
	jQuery(function() {
		jQuery("#departmentIdC").autocomplete(
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
											+ data[i].deptCode + ","
											+ data[i].name + ","
											+ data[i].deptCode + ":"
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
						extra1 : " leaf=true and (",
						extra2 : ")"
					},
					formatItem : function(row) {
						return dropId(row);
					},
					formatResult : function(row) {
						return dropId(row);
					}
				});
		jQuery("#departmentIdC").result(
				function(event, row, formatted) {
					if (row == "没有结果") {
						return;
					}
					jQuery("#allotItemdepartmentdepartmentId").attr("value",
							getId(row));
				});
	})

	function getId(s) {
		var s = "" + s;
		var p = s.lastIndexOf(":");
		if (p > -1) {
			var t = s.substring(p + 1);
			return t;
		}
		return s;
	}
	function dropId(s) {
		//alert(s)
		var s = "" + s;
		var p = s.lastIndexOf(":");
		if (p > -1) {
			var t = s.substring(0, p);
			var t = s.substring(s.indexOf(",") + 1, p);
			return t;
		}
		return s;
	}
</script>
<script>
	function getPinYinSelectCode(selStr) {
		var arr = selStr.split(" ");
		return arr[0];
	}

	jQuery(document).ready(function() {
		var s1 = jQuery("#costItemForm");
		if (s1.attr("value") == null || s1.attr("value") == "")
			s1.attr("value", "拼音/汉字/编码");
		jQuery("#costItemForm").autocomplete(
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
		jQuery("#costItemForm").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#costItemIdForm").attr("value", getId(row));
			//alert(jQuery("#costItemId").attr("value"));
		});
		
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#savelink').click(function() {
			jQuery("#allotItemForm").attr("action","saveAllotItem?popup=true&navTabId="+'${navTabId}');
			jQuery("#allotItemForm").submit();
		});

		jQuery.subscribe('processDepartmentPin', function(event, data) {
			var chargeid = document.getElementById("departmentPinYin").value;
			chargeid = getPinYinSelectCode(chargeid);
			//alert(chargeid);
			//document.getElementById("allotItem.department.departmentId").value = chargeid;
			jQuery("#allotItemdepartmentdepartmentId").attr("value", chargeid);

		});
		jQuery('input:text:first').focus();
	});
	function clearInput(o) {
		if (o.value == '拼音/汉字/编码') {
			o.value = "";
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="allotItemForm" method="post"
			action="saveAllotItem?popup=true" class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">

<s:url id="saveAllotItem" action="saveAllotItem" />

	<s:hidden key="allotItem.allotItemId" />
<label><fmt:message key='allotSet.allotSetName' />:</label>
<span style="float:left;width:140px">

	<s:select key="allotSet.allotSetName"
		name="allotItem.allotSet.allotSetId"
		value="%{allotItem.allotSet.allotSetId}"
		id="allotItem.allotSet.allotSetId" cssClass="required" maxlength="50"
		list="allAllotSets" listKey="allotSetId" listValue="allotSetName"
		emptyOption="true" theme="simple"></s:select>
		</span>
	<s:textfield key="allotItem.checkPeriod" cssClass="required" maxlength="6" readonly="true"/>
	</div>
	<div class="unit">
	<s:textfield key="allotItem.allotCost" cssClass="required number" />

	<%--   		<sj:textfield key="allotItem.calcType" required="false"	maxlength="10"   /> --%>
	<label><fmt:message key='allotItem.calcType' />:</label>

<span style="float:left;width:140px">

	<s:select list="#{'分摊':'分摊','计算':'计算'}" key="allotItem.calcType"
		listKey="key" listValue="value" cssClass="required" theme="simple"/>
</span>
	</div>
	<div class="unit">
	<s:hidden key="costItem.costItemId" id="costItemIdForm"/>
	<s:textfield key="costItem.costItemName" id="costItemForm" value="拼音/汉字/编码"
		onfocus="clearInput(this)"></s:textfield>
<label><fmt:message key='allotItem.costType' />:</label>

<span style="float:left;width:140px">

	<%--  		<sj:textfield key="allotItem.costType" required="false"	maxlength="10"   /> --%>
	<s:select
		list="#{'直接成本':'直接成本','管理成本':'管理成本','医辅成本':'医辅成本','医技成本':'医技成本'}"
		key="allotItem.costType" listKey="key" listValue="value"
		cssClass="required" theme="simple"/>
		</span>
	<%-- <s:url id="deptAuto" action="deptPinYinJson" namespace="/pinyin" /> --%>
	</div>
	<div class="unit">

	<%-- <sj:textfield key="allotItem.department.departmentId" id="allotItem.department.departmentId"/> --%>
	<s:hidden key="allotItem.department.departmentId"
		id="allotItemdepartmentdepartmentId" />
	<s:textfield key="department.name" required="false" id="departmentIdC"
		maxlength="30" label="%{getText('department.name')}" value="拼音/汉字/编码"
		onfocus="clearInput(this)" />
	<label><fmt:message key='allotItem.cjflag' />:</label>

<span style="float:left;width:140px">
	<s:checkbox key="allotItem.cjflag" theme="simple"></s:checkbox>
	</span>
	</div>
<div class="unit">
<label><fmt:message key='allotItem.dxflag' />:</label>

<span style="float:left;width:140px">
	<s:checkbox key="allotItem.dxflag" theme="simple">
	</s:checkbox>
	</span>
	<s:hidden key="allotItem.dxDeptIds" required="false" />
	<s:textfield key="allotItem.freeMark" required="false" maxlength="50" />
	</div>
	<div class="unit">
	<s:textarea key="allotItem.dxDeptNames" required="false"
		maxlength="8000" rows="5" cols="54" />
	</div>
	<div class="unit">
	
	<s:textarea key="allotItem.remark" required="false" maxlength="500"
		rows="5" cols="54" />
</div>
	</div>
			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savelink">保存</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
							</div>
						</div></li>
				</ul>
			</div>
		</form>
	</div>
</div>