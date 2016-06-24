<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#budgetModelXfForm").attr("action","saveBudgetModelXf?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#budgetModelXfForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="budgetModelXfForm" method="post"	action="saveBudgetModelXf?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="budgetModelXf.xfId"/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="budgetModelXf.modelId.id" list="modelIdList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="budgetModelXf_periodYear" key="budgetModelXf.periodYear" name="budgetModelXf.periodYear" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="budgetModelXf_state" key="budgetModelXf.state" name="budgetModelXf.state" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="budgetModelXf_xfDate" key="budgetModelXf.xfDate" name="budgetModelXf.xfDate" cssClass="				
				
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





