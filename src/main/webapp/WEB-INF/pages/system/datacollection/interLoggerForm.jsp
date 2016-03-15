<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/links.jsp"%>
<head>
<title><fmt:message key="interLoggerDetail.title" /></title>
<meta name="heading"
	content="<fmt:message key='interLoggerDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#savelink').click(function() {
			var action=interLoggerForm.action="saveInterLogger?popup=true";
			interLoggerForm.submit;
		});
	});
</script>
</head>
<s:url id="saveInterLogger" action="saveInterLogger" />

<s:form id="interLoggerForm" action="saveInterLogger" method="post"
	validate="true" cssClass="well  form-horizontal">

	<s:hidden key="interLogger.logId" />

	<sj:datepicker key="interLogger.logDateTime" required="true" size="11"
		buttonImageOnly="true" displayFormat="%{getText('datepicker.format')}" />


	<sj:textfield key="interLogger.logFrom" required="true" maxlength="50" />







	<sj:textfield key="interLogger.logMsg" required="true" maxlength="50" />







	<sj:textfield key="interLogger.taskInterId" required="true"
		maxlength="50" />






</s:form>

<sj:a id="savelink" formIds="interLoggerForm" indicator="indicator"
	button="true">
	<fmt:message key="button.save" />
</sj:a>
<sj:a id="cancellink" value="Cancel" button="true">
	<fmt:message key="button.cancel" />
</sj:a>

<script type="text/javascript">
    Form.focusFirstElement($("interLoggerForm"));
</script>
