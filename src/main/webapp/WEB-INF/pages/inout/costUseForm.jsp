<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>

<title><fmt:message key="costUseDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='costUseDetail.heading'/>" />
<script>
	jQuery(document).ready(
			function() {
				jQuery('#cancellink').click(function() {
					window.close();
				});
				jQuery('#${random}savelink').click(
						function() {
							jQuery("#costUseForm").attr(
									"action",
									"saveCostUse?popup=true&navTabId="
											+ '${navTabId}&entityIsNew=${entityIsNew}');
							jQuery("#costUseForm").submit();
						});
				jQuery('input:text:first').focus();
			});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="costUseForm" method="post" action="saveCostUse?popup=true"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<s:url id="saveCostUse" action="saveCostUse" />
				<div class="unit">
					<s:if test="%{costUse.costUseId!=null}">
						<s:textfield key="costUse.costUseId" cssClass="required" readonly="true"/>
					</s:if>
					<s:else>
						<s:textfield key="costUse.costUseId" cssClass="required" onblur="checkId(this,'CostUse','costUseId')"/>
					</s:else>
				</div>
				<div class="unit">
					<s:textfield key="costUse.costUseName" cssClass="required"
						maxlength="20" />
				</div>
				<s:if test="%{costUse.costUseId!=null}">
					<div class="unit">
						<label><fmt:message key='costUse.disabled' />:</label>
						<s:checkbox key="costUse.disabled" theme="simple"></s:checkbox>
					</div>
				</s:if>
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

