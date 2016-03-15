<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
<head>
<script>
jQuery(document).ready(function() {
		jQuery('#${random}savelink').click(
		function() {
			jQuery("#costItemForm").attr("action","saveCostItem?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#costItemForm").submit();
		});
});
jQuery(function(){
	jQuery("#costItemParentName").autocomplete("autocomplete",{
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
						data : data[i].costItemId + ","
								+ data[i].costItemId + ","
								+ data[i].cnCode + ","
								+ data[i].costItemName + ":"
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
			extra1 : " leaf =false and (",
			extra2 : ")"
		},
		formatItem : function(row) {
			return dropId(row);
		},
		formatResult : function(row) {
			return dropId(row);
		}
	});
	jQuery("#costItemParentName").result(function(event, row, formatted) {
		if (row == "没有结果") {
			return;
		}
		jQuery("#costItemParentID").attr("value",
				getId(row));
		//alert(jQuery("#zxDeptId").attr("value"));
	});
	var s1 = jQuery("#costItemParentName");
	if (s1.attr("value") == null || s1.attr("value") == "")
		s1.attr("value", "拼音/汉字/编码");
	
	jQuery("#costItem_costItemName").blur(function(){
		var thisValue = jQuery(this).val();
		thisValue = thisValue.trim();
		jQuery(this).val(thisValue);
	});
	adjustFormInput("costItemForm");
})

</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="costItemForm" method="post" action=""
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">


				<s:url id="saveCostItem" action="saveCostItem" />
				<div class="unit">
				<s:if test="%{costItem.costItemId==null}">
					<s:textfield key="costItem.costItemId" cssClass="required" onblur="checkId(this,'CostItem','costItemId')"/>
				</s:if>
				<s:else>
					<s:textfield key="costItem.costItemId" cssClass="required" readonly="true"/>
				</s:else>
					<s:textfield id="costItem_costItemName" key="costItem.costItemName" cssClass="required" style="width:230px"
						maxlength="30" />
				</div>
				<div class="unit">
					<label><fmt:message key='costItem.behaviour' />:</label> 
						<span class="formspan" style="float: left; width: 140px">
						<s:select key="costItem.behaviour" cssClass="required" maxlength="20"
							list="dictionaryItemsCost" listKey="value" listValue="content"
							emptyOption="false" theme="simple"></s:select> </span> 
					<label><fmt:message key='costUse.costUseName' />:</label>
						<span class="formspan" style="float: left; width: 140px">
						<s:select key="costUse.costUseName" name="costItem.costUse.costUseId"
							cssClass="required" maxlength="20" list="allCostUseList"
							listKey="costUseId" listValue="costUseName" emptyOption="false"
							theme="simple"></s:select> </span>
				</div>
				<div class="unit">

					<s:textfield key="costItem.cnCode" 
						  readonly="true"/>


					<s:select key="costItem.medOrLee"
					name="costItem.medOrLee" value="%{costItem.medOrLee}"
					id="costItem.medOrLee" cssClass="required" maxlength="50"
					list="medorleeList" listKey="value" listValue="content"
					emptyOption="false"></s:select>
				</div>
				<div class="unit">
					<s:textfield key="costItem.clevel" cssClass="required" value="%{costItem.clevel}" />

					<s:hidden id="costItemParentID" name="costItem.parent.costItemId" value="%{costItem.parent.costItemId}"/>
					<s:textfield key="costItem.parentName" id="costItemParentName"  cssClass="defaultValueStyle" value="%{costItem.parent.costItemName}" onfocus="clearInput(this)" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"/>

				</div>
				<div class="unit">
					<label><fmt:message key='costItem.leaf' />:</label> <span class="formspan" style="float: left; width: 140px"> 
							<s:checkbox key="costItem.leaf"  theme="simple" onclick="return %{!inUse}"/> 
						</span> 
					<s:textfield key="costItem.userDefine1"
						name="costItem.userDefine1" required="false" maxlength="30" />		
				</div>
				<div class="unit">
					<%-- <s:textfield key="costItem.userDefine1"
						name="costItem.userDefine_1" required="false" maxlength="30" /> --%>
					<s:textfield key="costItem.userDefine2"
						name="costItem.userDefine2" required="false" maxlength="30" />
						<s:textfield key="costItem.userDefine3"
						name="costItem.userDefine3" required="false" maxlength="30" />
				</div>
				<%-- <div class="unit">

					<s:textfield key="costItem.userDefine3"
						name="costItem.userDefine_3" required="false" maxlength="30" />
				</div> --%>
				<div class="unit">
					<s:textarea key="costItem.costDesc" required="false"
						maxlength="100" rows="3" cols="61" />
				</div>
				<div class="unit">
				<s:if test="%{costItem.costItemId!=null}">
							<label><fmt:message
							key='costItem.disabled' />:</label> <span
						style="float: left; width: 140px"> <s:checkbox
							key="costItem.disabled"  theme="simple" /> </span>
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
