<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#balancePeriodForm").attr("action","saveBalancePeriod?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#balancePeriodForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="balancePeriodForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="balancePeriod.balancePeriodId"/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="balancePeriod.balance.id" list="balanceList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="balancePeriod_lend" key="balancePeriod.lend" name="balancePeriod.lend" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="balancePeriod_loan" key="balancePeriod.loan" name="balancePeriod.loan" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="balancePeriod_periodMonth" key="balancePeriod.periodMonth" name="balancePeriod.periodMonth" cssClass="				
				
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





