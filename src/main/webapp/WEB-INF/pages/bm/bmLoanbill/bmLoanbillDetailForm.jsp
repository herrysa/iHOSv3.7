<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#bmLoanbillDetailForm").attr("action","saveBmLoanbillDetail?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#bmLoanbillDetailForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="bmLoanbillDetailForm" method="post"	action="saveBmLoanbillDetail?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="bmLoanbillDetail.billdetailId"/>
				</div>
				<div class="unit">
				<s:textfield id="bmLoanbillDetail_apply" key="bmLoanbillDetail.apply" name="bmLoanbillDetail.apply" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmLoanbillDetail_balance" key="bmLoanbillDetail.balance" name="bmLoanbillDetail.balance" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmLoanbillDetail_billId" key="bmLoanbillDetail.billId" name="bmLoanbillDetail.billId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmLoanbillDetail_billNum" key="bmLoanbillDetail.billNum" name="bmLoanbillDetail.billNum" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmLoanbillDetail_bmSubjId" key="bmLoanbillDetail.bmSubjId" name="bmLoanbillDetail.bmSubjId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmLoanbillDetail_payModel" key="bmLoanbillDetail.payModel" name="bmLoanbillDetail.payModel" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmLoanbillDetail_purpose" key="bmLoanbillDetail.purpose" name="bmLoanbillDetail.purpose" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmLoanbillDetail_sn" key="bmLoanbillDetail.sn" name="bmLoanbillDetail.sn" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmLoanbillDetail_total" key="bmLoanbillDetail.total" name="bmLoanbillDetail.total" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="bmLoanbillDetail_usable" key="bmLoanbillDetail.usable" name="bmLoanbillDetail.usable" cssClass="				number
				
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





