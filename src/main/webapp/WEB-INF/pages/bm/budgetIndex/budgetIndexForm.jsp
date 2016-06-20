<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#budgetIndexForm").attr("action","saveBudgetIndex?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#budgetIndexForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="budgetIndexForm" method="post"	action="saveBudgetIndex?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield key="budgetIndex.indexCode" cssClass="required" notrepeat='预算指标编码已存在' validatorParam="from BudgetIndex entity where entity.indexCode=%value%"/>
					</s:if>
					<s:else>
						<s:textfield key="budgetIndex.indexCode" readonly="true" cssClass="required"/>
					</s:else>
				</div>
				<div class="unit">
				<s:textfield id="budgetIndex_indexName" key="budgetIndex.indexName" name="budgetIndex.indexName" cssClass="		required		
				
				       "/>
				</div>
				<div class="unit">
					<label><s:text name="budgetIndex.indexType"/>:</label>
					<s:select list="#{'月':'月','季度':'季度','半年':'半年','年':'年'}" key="budgetIndex.indexType" headerKey="" headerValue="--" theme="simple"></s:select>
				</div>
				<div class="unit">
				<label><s:text name="budgetIndex.parentId"/>:</label>
					<s:hidden key="budgetIndex.parentId_indexCode" name="budgetIndex.parentId.indexCode"></s:hidden>
				<s:textfield id="budgetIndex_parentId_indexName" key="budgetIndex.parentId.indexName" name="budgetIndex.parentId.indexName" readonly="true" cssClass="				
				
				       " theme="simple"/>
				</div>
				<div class="unit">
					<label><s:text name="budgetIndex.budgetType"/>:</label>
					<s:hidden id="budgetIndex_budgetType_id" name="budgetIndex.budgetType.budgetTypeCode"/>
					<s:textfield id="budgetIndex_budgetType"></s:textfield>
					<script>
					jQuery("#budgetIndex_budgetType").treeselect({
						optType : "single",
						dataType : 'sql',
						sql : "select type_code id,type_name name,parent_id parent from bm_budgetType",
						exceptnullparent : true,
						lazy : false,
						minWidth : '180px',
						selectParent : false,
						callback : {
						}
					});
					</script>
				</div>
				<div class="unit">
				<s:textfield id="budgetIndex_exceedBudgetType" key="budgetIndex.exceedBudgetType" name="budgetIndex.exceedBudgetType" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="budgetIndex_warningPercent" key="budgetIndex.warningPercent" name="budgetIndex.warningPercent" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
					<label><s:text name="budgetIndex.leaf"/>:</label>
					<s:checkbox id="budgetIndex_leaf" key="budgetIndex.leaf" name="budgetIndex.leaf" theme="simple"/>
				</div>
				<div class="unit">
					<label><s:text name="budgetIndex.disabled"/>:</label>
					<s:checkbox id="budgetIndex_disabled" key="budgetIndex.disabled" name="budgetIndex.disabled" theme="simple"/>
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





