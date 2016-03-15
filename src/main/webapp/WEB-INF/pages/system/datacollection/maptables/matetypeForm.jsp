<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="matetypeDetail.title" /></title>
<meta name="heading"
	content="<fmt:message key='matetypeDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#savelink').click(function() {
			var action=matetypeForm.action="saveMatetype?popup=true";
			matetypeForm.submit;
		});
	});

</script>
</head>
<s:url id="saveMatetype" action="saveMatetype" />

<s:form id="matetypeForm" action="saveMatetype" method="post"
	validate="true"  cssClass="well  form-horizontal">
	<s:textfield key="matetype.mateTypeId" required="true"
		cssClass="text medium" />

	<sj:textfield key="matetype.costItemId" required="false" maxlength="40" />







	<sj:textfield key="matetype.costitem" id="costitemHId" required="false"
		maxlength="40" />







	<sj:textfield key="matetype.mateType" required="true" maxlength="60" />






</s:form>

<sj:a id="savelink" formIds="matetypeForm" indicator="indicator"
	button="true">
	<fmt:message key="button.save" />
</sj:a>
<sj:a id="cancellink" value="Cancel" button="true">
	<fmt:message key="button.cancel" />
</sj:a>

<script type="text/javascript">
    Form.focusFirstElement($("matetypeForm"));
</script>
