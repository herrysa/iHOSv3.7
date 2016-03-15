<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#bankForm").attr("action","saveBank?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#bankForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="bankForm" method="post"	action="saveBank?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:if test="%{!entityIsNew}">
						<s:textfield key="bank.bankId" readonly="true"/>
						<s:textfield key="bank.bankName" cssClass="required" maxlength="50" oldValue="'${bank.bankName}'" notrepeat='银行名称已存在' validatorParam="from Bank bank where bank.bankName=%value%" />
					</s:if>
					<s:else>
						<s:textfield key="bank.bankId" cssClass="required" maxlength="50" notrepeat='银行行号已存在' validatorParam="from Bank bank where bank.bankId=%value%" />
						<s:textfield key="bank.bankName"cssClass="required" maxlength="50" notrepeat='银行名称已存在' validatorParam="from Bank bank where bank.bankName=%value%" />
					</s:else>
				</div>
				<div class="unit">
					<label><s:text name='bank.disabled'/>:</label>
							<span  class="formspan" style="float: left; width: 140px">
								<s:checkbox key="bank.disabled" theme="simple"/>
							</span>
				</div>
				<div class="unit">
				<s:textarea id="bank_remark" key="bank.remark" name="bank.remark" cssStyle="width:300px;height:50px;" />
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





