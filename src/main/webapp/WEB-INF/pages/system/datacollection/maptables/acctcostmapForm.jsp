<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/links.jsp"%>
<head>
<title><fmt:message key="acctcostmapDetail.title" /></title>
<meta name="heading"
	content="<fmt:message key='acctcostmapDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#savelink').click(function() {
			var action=acctcostmapForm.action="saveAcctcostmap?popup=true";
			acctcostmapForm.submit;
		});
	});
</script>
</head>
<s:url id="saveAcctcostmap" action="saveAcctcostmap" />

<s:form id="acctcostmapForm" action="saveAcctcostmap" method="post"
	validate="true" cssClass="well  form-horizontal">
	<s:textfield key="acctcostmap.acctcode" required="true"
		cssClass="text medium" />

	<sj:textarea key="acctcostmap.acctname" required="false"
		maxlength="100" rows="5" cols="50" />







	<sj:textarea key="acctcostmap.costitem" required="false"
		maxlength="100" rows="5" cols="50" />







	<sj:textfield key="acctcostmap.costitemid" required="false"
		maxlength="50" />






</s:form>

<sj:a id="savelink" formIds="acctcostmapForm" indicator="indicator"
	button="true">
	<fmt:message key="button.save" />
</sj:a>
<sj:a id="cancellink" value="Cancel" button="true">
	<fmt:message key="button.cancel" />
</sj:a>

<script type="text/javascript">
    Form.focusFirstElement($("acctcostmapForm"));
</script>
