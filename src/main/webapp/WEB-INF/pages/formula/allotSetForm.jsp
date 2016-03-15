<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="allotSetDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='allotSetDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			jQuery("#allotSetForm").attr("action","saveAllotSet?popup=true&navTabId="+'${navTabId}');
			jQuery("#allotSetForm").submit();
		});
	});
</script>
</head>
<div class="page">
	<div class="pageContent">
		<form id="allotSetForm" method="post" action="saveAllotSet?popup=true"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:if test="allotSet.allotSetId==null">
					<s:textfield key="allotSet.allotSetId" cssClass="required"/>
					</s:if>
					<s:else>
					<s:textfield key="allotSet.allotSetId" cssClass="required" readonly="true"/>
					</s:else>
				</div>
				<div class="unit">
					<s:textfield key="allotSet.allotSetName" cssClass="required" />
				</div>
				<div class="unit">
					<s:if test="allotSet.allotSetId!=null">
						<label><fmt:message key='allotSet.disabled' />:</label>
						<s:checkbox key="allotSet.disabled" theme="simple"></s:checkbox>
					</s:if>
				</div>
				<div class="unit">
					<s:textarea key="allotSet.remark" required="false" maxlength="200"
						rows="5" cols="50" />
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
