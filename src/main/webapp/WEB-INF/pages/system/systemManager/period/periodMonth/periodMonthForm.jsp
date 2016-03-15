<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#periodMonthForm").attr("action","savePeriodMonth?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#periodMonthForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="periodMonthForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="periodMonth.monthId"/>
				</div>
				<div class="unit">
				<s:textfield id="periodMonth_beginDate" key="periodMonth.beginDate" name="periodMonth.beginDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="periodMonth_endDate" key="periodMonth.endDate" name="periodMonth.endDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="periodMonth_month" key="periodMonth.month" name="periodMonth.month" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="periodMonth.periodSub.id" list="periodSubList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="periodMonth.plan.id" list="planList" listKey="id" listValue="id"></s:select> -->
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





