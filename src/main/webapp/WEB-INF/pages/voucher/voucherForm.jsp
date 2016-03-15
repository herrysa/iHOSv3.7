<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#voucherForm").attr("action","saveVoucher?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#voucherForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="voucherForm" method="post"	action="saveVoucher?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="voucher.voucherId"/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_abstractStr" key="voucher.abstractStr" name="voucher.abstractStr" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_attachNum" key="voucher.attachNum" name="voucher.attachNum" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_auditDate" key="voucher.auditDate" name="voucher.auditDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_auditId" key="voucher.auditId" name="voucher.auditId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_auditName" key="voucher.auditName" name="voucher.auditName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_billDate" key="voucher.billDate" name="voucher.billDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_billId" key="voucher.billId" name="voucher.billId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_billName" key="voucher.billName" name="voucher.billName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_businessPerson" key="voucher.businessPerson" name="voucher.businessPerson" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_checkDate" key="voucher.checkDate" name="voucher.checkDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_checkId" key="voucher.checkId" name="voucher.checkId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_checkName" key="voucher.checkName" name="voucher.checkName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_copyCode" key="voucher.copyCode" name="voucher.copyCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_je" key="voucher.je" name="voucher.je" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_kjManager" key="voucher.kjManager" name="voucher.kjManager" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_kjPeriod" key="voucher.kjPeriod" name="voucher.kjPeriod" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_orgCode" key="voucher.orgCode" name="voucher.orgCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_pzhzbz" key="voucher.pzhzbz" name="voucher.pzhzbz" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_pzhzkmdy" key="voucher.pzhzkmdy" name="voucher.pzhzkmdy" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_remark" key="voucher.remark" name="voucher.remark" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_status" key="voucher.status" name="voucher.status" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_voucherDate" key="voucher.voucherDate" name="voucher.voucherDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_voucherFromCode" key="voucher.voucherFromCode" name="voucher.voucherFromCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucher_voucherNo" key="voucher.voucherNo" name="voucher.voucherNo" cssClass="				
				
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





