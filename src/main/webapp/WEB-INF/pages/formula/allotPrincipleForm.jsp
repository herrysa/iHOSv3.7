<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="allotPrincipleDetail.title" /></title>
<meta name="heading"
	content="<fmt:message key='allotPrincipleDetail.heading'/>" />
<script>
	jQuery(document)
			.ready(
					function() {
						jQuery('#cancellink').click(function() {
							window.close();
						});
						jQuery('#${random}savelink')
								.click(
										function() {
											jQuery("#allotPrincipleForm").attr("action","saveAllotPrinciple?popup=true&navTabId="+'${navTabId}');
											jQuery("#allotPrincipleForm").submit();
										});
					});
</script>
</head>
<div class="page">
	<div class="pageContent">
		<form id="allotPrincipleForm" method="post" action="saveAllotPrinciple"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
	<s:textfield key="allotPrinciple.allotPrincipleId" cssClass="required"/>
		 </div>
		 <div class="unit">
	<s:textfield key="allotPrinciple.allotPrincipleName" cssClass="required"  />
</div>
<div class="unit">
	<label><fmt:message key='allotPrinciple.paramed' />:</label>
	<span style="float:left;width:140px">
	<s:checkbox key="allotPrinciple.paramed" theme="simple"></s:checkbox>
	</span>
	</div>
		 <div class="unit">
	<s:textfield key="allotPrinciple.param1" required="false" cssClass="input-big" />
	</div>
	

	<s:if test="allotPrinciple.allotPrincipleId!=null">
	<div class="unit">
	<label><fmt:message key='allotPrinciple.disabled' />:</label>
	<span style="float:left;width:140px">
	<s:checkbox key="allotPrinciple.disabled" theme="simple"></s:checkbox>
	</span>
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

