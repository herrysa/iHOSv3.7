<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#businessTypeParamForm").attr("action","saveBusinessTypeParam?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#businessTypeParamForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="businessTypeParamForm" method="post"	action="saveBusinessTypeParam?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
			<s:hidden key="businessTypeParam.paramId"/>
			<s:hidden name="businessTypeParam.businessType.businessId"
					value="%{businessId}" id="businessTypeParam_businessId" />
				<div class="unit">
					<s:if test="%{!entityIsNew}">
						<s:textfield id="businessTypeParam_paramCode"
							key="businessTypeParam.paramCode" name="businessTypeParam.paramCode"
							cssClass="required" notrepeat='参数键已存在 ' oldValue="'${businessTypeParam.paramCode }'"
							validatorParam="from BusinessTypeParam type where type.paramCode=%value% and type.businessType.businessId = '${businessTypeParam.businessType.businessId}'" />
					</s:if>
					<s:else>
						<s:textfield id="businessTypeParam_paramCode"
							key="businessTypeParam.paramCode" name="businessTypeParam.paramCode"
							cssClass="required" notrepeat='参数键已存在 '
							validatorParam="from BusinessTypeParam type where type.paramCode=%value% and type.businessType.businessId = '${businessId}'" />
					</s:else>
				</div>
				<div class="unit">
					<%-- <s:textfield id="businessTypeParam_paramValue" key="businessTypeParam.paramValue"
						name="businessTypeParam.paramValue" cssClass="required" /> --%>
					<label><s:text name="businessTypeParam.paramValue" /></label>
					<s:select id="businessTypeParam_paramValue" name="businessTypeParam.paramValue" key="businessTypeParam.paramValue" list="voucherMap" listKey="key" listValue="value" emptyOption="true" theme="simple"></s:select>
				</div>
				<div class="unit">
					<s:textfield id="businessTypeParam_sn" key="businessTypeParam.sn"
						name="businessTypeParam.sn" cssClass="required digits" />
				</div>
				<div class="unit">
					<label><s:text name="businessTypeParam.disabled" />:</label>
					<s:checkbox id="businessTypeParam_disabled"
						key="businessTypeParam.disabled" name="businessTypeParam.disabled"
						theme="simple" />
				</div>
				<div class="unit">
					<s:textarea id="businessTypeParam_remark" key="businessTypeParam.remark"
						name="businessTypeParam.remark" cssStyle="width:220px;height:40px;" />
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





