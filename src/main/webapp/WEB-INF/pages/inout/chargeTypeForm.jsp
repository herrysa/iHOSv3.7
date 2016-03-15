<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.huge.model.*"%>
<%@ page import="java.util.*"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="chargeTypeDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='chargeTypeDetail.heading'/>" />
<script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
<script type="text/javascript">
	jQuery(function() {
		jQuery("#chargeTypeparentchargeTypeIdautoForm").autocomplete(
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
									data : data[i].chargeTypeId + ","
											+ data[i].chargeTypeName + ":"
											+ data[i].chargeTypeId,
									value : data[i].chargeTypeName,
									result : data[i].chargeTypeName
								};
							}
							return rows;
						}
					},
					extraParams : {
						flag : 2,
						entity : "ChargeType",
						cloumnsStr : "chargeTypeId,chargeTypeName,cnCode",
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
		jQuery("#chargeTypeparentchargeTypeIdautoForm").result(
				function(event, row, formatted) {
					if (row == "没有结果") {
						return;
					}
					jQuery("#chargeTypeparentchargeTypeId").attr("value",
							getId(row));
					//alert(jQuery("#chargeTypeparentchargeTypeId").attr("value"));
				});
		adjustFormInput("chargeTypeForm");
	})

</script>
<script>
	jQuery(document).ready(function() {
		jQuery.subscribe('medComplete', function(response) {

			//		 var msg = jQuery.parseJSON(response.responseText);

			//		      alert('med load complete' + msg);
			//	alert('med load complete');
		});

		jQuery.subscribe('medBefore', function(response) {

			//	 alert('med load before');
		});

		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			jQuery("input").each(function(){
				if(jQuery(this).attr("value")==="拼音/汉字/编码"){
					jQuery(this).attr("value","");
				}
			});
			jQuery("#chargeTypeForm").attr("action","saveChargeType?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#chargeTypeForm").submit();
		});
		jQuery('input:text:first').focus();
		var ss = jQuery("#chargeTypeparentchargeTypeIdautoForm");
		if (ss.attr("value") == null || ss.attr("value") == "") {
			ss.attr("value", "拼音/汉字/编码");
		}
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="chargeTypeForm" method="post"
			action="saveChargeType?popup=true" class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">

				<div class="unit">
				<s:if test="chargeType.chargeTypeId==null">
					<s:textfield key="chargeType.chargeTypeId" cssClass="required"
						class="input-small" onblur="checkId(this,'ChargeType','chargeTypeId')"/>
				</s:if>
				<s:else><s:textfield key="chargeType.chargeTypeId" cssClass="required"
						class="input-small" readonly="true"/></s:else>
					
					<s:textfield key="chargeType.chargeTypeName" cssClass="required"
						maxlength="30" class="input-small" />
				</div>
				<div class="unit">
				<label><fmt:message key='payinItem.payinItemName' />:</label>
				<span class="formspan" style="float:left;width:140px">
					<s:select key="payinItem.payinItemName"
						name="chargeType.payinItem.payinItemId" class="input-small"
						value="%{chargeType.payinItem.payinItemId}"
						id="chargeType.payinItem.payinItemId" required="false"
						list="payinItemList" listKey="payinItemId"
						listValue="payinItemName" emptyOption="true" theme="simple"/>
					</span>
					<s:textfield key="chargeType.clevel" cssClass="required" value="1"
						class="input-small" />
				</div>
				<div class="unit">
					<s:textfield key="chargeType.cnCode" required="false"
						maxlength="12" readonly="true" class="input-small" />
					<s:textfield key="chargeType.displayMark" required="false"
						maxlength="12" class="input-small" />
				</div>
				<div class="unit">
				<label><fmt:message key='chargeType.medOrLee' />:</label>
				<span class="formspan" style="float:left;width:140px">
					<s:select key="chargeType.medOrLee" name="chargeType.medOrLee"
						value="%{chargeType.medOrLee}" class="input-small"
						id="chargeType.medOrLee" cssClass="required" maxlength="50"
						list="dictionaryItemsSelectList" listKey="value"
						listValue="content" emptyOption="false" theme="simple"></s:select>
						</span>
					<s:textfield key="chargeType.parentId"
						id="chargeTypeparentchargeTypeIdautoForm"
						name="chargeTypeparentchargeTypeIdautoForm"
						cssClass="defaultValueStyle"
						value="%{chargeType.parent.chargeTypeName}"
						onfocus="clearInput(this)" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"></s:textfield>
				</div>
				<div class="unit">
				<label><fmt:message key='chargeType.leaf' />:</label>
					<span class="formspan" style="float:left;width:140px">
					<s:checkbox key="chargeType.leaf" theme="simple"></s:checkbox>
					</span>
				
					<s:hidden name="chargeType.parent.chargeTypeId"
					id="chargeTypeparentchargeTypeId"></s:hidden>
					<s:textfield key="chargeType.reportmark" required="false"
						maxlength="50" class="input-small" />
				</div>
				<div class="unit">
				
					<s:if test="chargeType.chargeTypeId!=null">
						<label><fmt:message key='chargeType.disabled' />:</label>
						<span class="formspan" style="float:left;width:140px">
						<s:checkbox key="chargeType.disabled" theme="simple"></s:checkbox>
						</span>
					</s:if>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink">保存</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>

