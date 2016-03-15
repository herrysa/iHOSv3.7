<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#incomeTaxRateForm").attr("action","saveIncomeTaxRate?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#incomeTaxRateForm").submit();
		});*/
		
		if("${entityIsNew}" != "true"){
			jQuery("#incomeTaxRate_incomeFloor").attr("readonly","true").removeClass("required");
			jQuery("#incomeTaxRate_incomeTopLimit").attr("readonly","true").removeClass("required");
			jQuery("#incomeTaxRate_fullTaxCost").attr("readonly","true").removeClass("required");
			jQuery("#incomeTaxRate_taxRate").attr("readonly","true").removeClass("required");
			jQuery("#incomeTaxRate_extraCost").attr("readonly","true").removeClass("required");
		}
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="incomeTaxRateForm" method="post"	action="saveIncomeTaxRate?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="incomeTaxRate.rateId"/>
				</div>
				<div class="unit">
					<s:textfield id="incomeTaxRate_baseNum" key="incomeTaxRate.baseNum" name="incomeTaxRate.baseNum" cssClass="digits required"/>
				    <s:textfield id="incomeTaxRate_extraCost" key="incomeTaxRate.extraCost" name="incomeTaxRate.extraCost" cssClass="number"/>
				</div>
				<div class="unit">
					<s:textfield id="incomeTaxRate_incomeFloor" key="incomeTaxRate.incomeFloor" name="incomeTaxRate.incomeFloor" cssClass="digits required"/>
					<s:textfield id="incomeTaxRate_incomeTopLimit" key="incomeTaxRate.incomeTopLimit" name="incomeTaxRate.incomeTopLimit" cssClass="digits required"/>
				</div>
				<div class="unit">
					<s:textfield id="incomeTaxRate_taxRate" key="incomeTaxRate.taxRate" name="incomeTaxRate.taxRate" cssClass="number required"/>
					<s:textfield id="incomeTaxRate_fullTaxCost" key="incomeTaxRate.fullTaxCost" name="incomeTaxRate.fullTaxCost" cssClass="number required"/>
				</div>
				<div class="unit">
					<s:textfield id="incomeTaxRate_level" key="incomeTaxRate.level" name="incomeTaxRate.level" cssClass="digits required"/>
					<s:if test="entityIsNew">
						<s:hidden key="incomeTaxRate.disabled"/>
					</s:if>
					<s:else>
						<s:label value="停用:"/>
						<s:checkbox  id="incomeTaxRate_disabled" key="incomeTaxRate.disabled" name="incomeTaxRate.disabled" theme="simple"/>
					</s:else>
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





