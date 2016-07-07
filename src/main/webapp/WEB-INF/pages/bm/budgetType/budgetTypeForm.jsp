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
				<s:textfield id="budgetType_budgetTypeName" key="budgetType.budgetTypeName" name="budgetType.budgetTypeName" cssClass="required			
				
				       "/>
				</div>
				<div class="unit">
					<s:hidden key="budgetType.parentId.budgetTypeCode" name="budgetType.parentId.budgetTypeCode" cssClass="				
				
				       "/>
				   <label><s:text name="budgetType.parent"/>:</label>
			       <s:textfield key="budgetType.parentId.budgetTypeName" name="budgetType.parentId.budgetTypeName" readonly="true" cssClass="				
			
			       " theme="simple"/>
				</div>
				<div class="unit">
					<label><s:text name="budgetType.exceedBudgetType"/>:</label>
					<s:select list="#{'禁止':'禁止','警告':'警告' }" key="budgetType.exceedBudgetType" name="budgetType.exceedBudgetType" theme="simple"></s:select>
				</div>
				<div class="unit">
				<s:textfield id="budgetType_warningPercent" key="budgetType.warningPercent" name="budgetType.warningPercent" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
					<label><s:text name="budgetType.leaf"/>:</label>
					<s:checkbox id="budgetType_leaf" key="budgetType.leaf" name="budgetType.leaf" theme="simple"/>
				</div>
				<div class="unit">
					<label><s:text name="budgetType.disabled"/>:</label>
					<s:checkbox id="budgetType_disabled" key="budgetType.disabled" name="budgetType.disabled" theme="simple" />
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





