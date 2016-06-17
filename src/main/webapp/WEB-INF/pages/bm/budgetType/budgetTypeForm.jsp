<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#budgetTypeForm").attr("action","saveBudgetType?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#budgetTypeForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="budgetTypeForm" method="post"	action="saveBudgetType?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield key="budgetType.budgetTypeCode" cssClass="required" notrepeat='预算类型编码已存在' validatorParam="from BudgetType entity where entity.budgetTypeCode=%value%"/>
					</s:if>
					<s:else>
						<s:textfield key="budgetType.budgetTypeCode" readonly="true" cssClass="required"/>
					</s:else>
				</div>
				<div class="unit">
				<s:textfield id="budgetType_budgetTypeName" key="budgetType.budgetTypeName" name="budgetType.budgetTypeName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="budgetType_exceedBudgetType" key="budgetType.exceedBudgetType" name="budgetType.exceedBudgetType" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				</div>
				<div class="unit">
				<s:textfield id="budgetType_warningPercent" key="budgetType.warningPercent" name="budgetType.warningPercent" cssClass="				
				digits
				       "/>
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





