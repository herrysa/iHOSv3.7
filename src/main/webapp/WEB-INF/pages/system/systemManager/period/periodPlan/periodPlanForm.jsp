<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#periodPlanForm").attr("action","savePeriodPlan?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#periodPlanForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="periodPlanForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="periodPlan.planId"/>
				</div>
				<div class="unit">
				<s:textfield id="periodPlan_memo" key="periodPlan.memo" name="periodPlan.memo" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="periodPlan_planName" key="periodPlan.planName" name="periodPlan.planName" cssClass="				
				
				       "/>
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savelink"><s:text name="button.save" /></button>
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





