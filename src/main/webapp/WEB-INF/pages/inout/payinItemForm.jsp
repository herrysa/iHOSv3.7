<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="payinItemDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='payinItemDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			jQuery("#payinItemForm").attr("action","savePayinItem?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#payinItemForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="payinItemForm" method="post"
			action="savePayinItem?popup=true" class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:url id="savePayinItem" action="savePayinItem" />
					<s:if test="payinItem.payinItemId==null"><s:textfield key="payinItem.payinItemId" cssClass="required" onblur="checkId(this,'PayinItem','payinItemId')"/></s:if>
					<s:else><s:textfield key="payinItem.payinItemId" cssClass="required" readonly="true"/></s:else>
					
				</div>
				
				<div class="unit">
					<s:textfield key="payinItem.payinItemName" cssClass="required"
						maxlength="30" />
				</div>
				<div class="unit">
					<s:textarea key="payinItem.remark" required="false"
						maxlength="100" rows="5" cols="50" />
				</div>
				<div class="unit">
					<s:if test="payinItem.payinItemId!=null">
					<label><fmt:message key='payinItem.disabled' />:</label>
<span style="float:left;width:140px">
						<s:checkbox key="payinItem.disabled" theme="simple"></s:checkbox>
						</span>
					</s:if>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink">保存</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>
