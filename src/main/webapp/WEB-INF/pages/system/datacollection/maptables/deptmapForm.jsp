<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="deptmapDetail.title" /></title>
<meta name="heading"
	content="<fmt:message key='deptmapDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#savelink').click(function() {
			var action=deptmapForm.action="saveDeptmap?popup=true";
			deptmapForm.submit;
		});
	});
</script>
</head>
<s:url id="saveDeptmap" action="saveDeptmap" />

<s:form id="deptmapForm" action="saveDeptmap" method="post"
	validate="true"  cssClass="well  form-horizontal">

	<s:hidden key="deptmap.id" />


	<sj:textfield key="deptmap.marktype" required="true" maxlength="60" />







	<sj:textfield key="deptmap.sourcecode" required="true" maxlength="50" />







	<sj:textarea key="deptmap.sourcename" required="true" maxlength="100"
		rows="5" cols="50" />







	<sj:textfield key="deptmap.targetcode" required="false" maxlength="50" />







	<sj:textarea key="deptmap.targetname" required="false" maxlength="100"
		rows="5" cols="50" />






</s:form>

<sj:a id="savelink" formIds="deptmapForm" indicator="indicator"
	button="true">
	<fmt:message key="button.save" />
</sj:a>
<sj:a id="cancellink" value="Cancel" button="true">
	<fmt:message key="button.cancel" />
</sj:a>

<script type="text/javascript">
    Form.focusFirstElement($("deptmapForm"));
</script>
