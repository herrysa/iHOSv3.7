<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#voucherDetailForm").attr("action","saveVoucherDetail?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#voucherDetailForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="voucherDetailForm" method="post"	action="saveVoucherDetail?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="voucherDetail.voucherDetailId"/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="voucherDetail.account.id" list="accountList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="voucherDetail_acctcode" key="voucherDetail.acctcode" name="voucherDetail.acctcode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherDetail_copyCode" key="voucherDetail.copyCode" name="voucherDetail.copyCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherDetail_detailNo" key="voucherDetail.detailNo" name="voucherDetail.detailNo" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherDetail_direction" key="voucherDetail.direction" name="voucherDetail.direction" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherDetail_hl" key="voucherDetail.hl" name="voucherDetail.hl" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherDetail_je" key="voucherDetail.je" name="voucherDetail.je" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherDetail_kjPeriod" key="voucherDetail.kjPeriod" name="voucherDetail.kjPeriod" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherDetail_orgCode" key="voucherDetail.orgCode" name="voucherDetail.orgCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="voucherDetail.voucher.id" list="voucherList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="voucherDetail_voucherFromCode" key="voucherDetail.voucherFromCode" name="voucherDetail.voucherFromCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherDetail_wbcode" key="voucherDetail.wbcode" name="voucherDetail.wbcode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="voucherDetail_wbje" key="voucherDetail.wbje" name="voucherDetail.wbje" cssClass="				number
				
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





