<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#reportPlanFilterForm").attr("action","saveReportPlanFilter?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#reportPlanFilterForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="reportPlanFilterForm" method="post"	action="saveReportPlanFilter?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="reportPlanFilter.filterId"/>
				</div>
				<div class="unit">
				<s:textfield id="reportPlanFilter_filterCode" key="reportPlanFilter.filterCode" name="reportPlanFilter.filterCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportPlanFilter_filterValue" key="reportPlanFilter.filterValue" name="reportPlanFilter.filterValue" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportPlanFilter_planId" key="reportPlanFilter.planId" name="reportPlanFilter.planId" cssClass="				
				
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





