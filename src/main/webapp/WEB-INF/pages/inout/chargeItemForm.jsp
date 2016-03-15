<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="chargeItemDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='chargeItemDetail.heading'/>" />
<script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
<script type="text/javascript">
	jQuery(function() {
		jQuery("#chargeItemchargeTypechargeTypeIdauto").autocomplete(
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
											+data[i].cnCode + ","
											+ data[i].chargeTypeName + ":"
											+ data[i].chargeTypeId,
									value : data[i].chargeTypeId,
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
		jQuery("#chargeItemchargeTypechargeTypeIdauto").result(
				function(event, row, formatted) {
					if (row == "没有结果") {
						return;
					}
					jQuery("#chargeItemchargeTypechargeTypeId").attr("value",
							getId(row));
					//alert(jQuery("#chargeItemchargeTypechargeTypeId").attr("value"));
				});
	})

</script>
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			jQuery("input").each(function(){
				if(jQuery(this).attr("value")==="拼音/汉字/编码"){
					jQuery(this).attr("value","");
				}
			});
			jQuery("#chargeItemForm").attr("action","saveChargeItem?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#chargeItemForm").submit();
		});
		var ss = jQuery("#chargeItemchargeTypechargeTypeIdauto");
		if (ss.attr("value") == null || ss.attr("value") == "") {
			ss.attr("value", "拼音/汉字/编码");
		}
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="chargeItemForm" method="post"
			action="saveChargeItem?popup=true" class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">

				<s:url id="saveChargeItem" action="saveChargeItem" />

				<div class="unit">
					<%-- <s:hidden key="chargeItem.chargeItemNo" /> --%>
					<s:if test="chargeItem.chargeItemId==null">
					<s:textfield key="chargeItem.chargeItemId" cssClass="required"
						maxlength="255" onblur="checkId(this,'ChargeItem','chargeItemId')"/>
					</s:if>
					<s:else>
					<s:textfield key="chargeItem.chargeItemId" cssClass="required"
						maxlength="255" readonly="true"/>
					</s:else>
					
				</div>
				<div class="unit">
					<s:textfield key="chargeItem.chargeItemName" cssClass="required"
						maxlength="100" size="50" />
				</div>
				<div class="unit">
					<s:hidden name="chargeItem.chargeType.chargeTypeId"
						id="chargeItemchargeTypechargeTypeId"></s:hidden>
					<s:textfield key="chargeType.chargeTypeName"
						id="chargeItemchargeTypechargeTypeIdauto"
						name="chargeItemchargeTypechargeTypeIdauto"
						value="%{chargeItem.chargeType.chargeTypeName}"
						onfocus="clearInput(this)" cssClass="required defaultValueStyle" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"></s:textfield>
				</div>
				<div class="unit">
					<s:textfield key="chargeItem.specification" required="false"
						maxlength="64" />
				</div>
				<div class="unit">
					<s:textfield key="chargeItem.unit" required="false" maxlength="15" />
				</div>
				<div class="unit">
					<s:textfield key="chargeItem.price" cssClass="number" required="false" />
				</div>
				<div class="unit">
				<s:if test="%{chargeItem.chargeItemId!=null}">
							<label><fmt:message
							key='chargeItem.disabled' />:</label> <span
						style="float: left; width: 140px"> <s:checkbox
							key="chargeItem.disabled"  theme="simple" /> </span>
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
