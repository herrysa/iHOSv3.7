<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/links.jsp"%>
<head>
    <title><fmt:message key="singleEntitySampleDetail.title"/></title>
    <meta name="heading" content="<fmt:message key='singleEntitySampleDetail.heading'/>"/>
    <script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#savelink').click(function() {
			var action=singleEntitySampleForm.action="saveSingleEntitySample?popup=true";
			singleEntitySampleForm.submit;
		});
	});
</script>
</head>
<s:url id="saveSingleEntitySample" action="saveSingleEntitySample" />

<s:form id="singleEntitySampleForm" action="saveSingleEntitySample" method="post" validate="true"  theme="xhtml">

        <s:hidden key="singleEntitySample.pkid"/>

        
 		<sj:textfield key="singleEntitySample.bigDecimalField" required="true"	/>
        
        
        
        	       
        	
<s:url id="dicSelectList" action="dictionaryItemSelectList"	namespace="/system">
	<s:param name="dicCode">radioyesno</s:param>
</s:url>

<sj:radio key="singleEntitySample.booleanField" required="true"  href="%{dicSelectList}"	list="dictionaryItemsSelectList" listKey="value" listValue="content"/>
 		<sj:datepicker key="singleEntitySample.dateField" required="true" size="11"	buttonImageOnly="true"  displayFormat="%{getText('datepicker.format')}"/>
        
        
 		<sj:textfield key="singleEntitySample.doubleField" required="true"	/>
        
        
        
        	       
        	
        
 		<sj:textfield key="singleEntitySample.floatField" required="true"	/>
        
        
        
        	       
        	
        
 		<sj:textfield key="singleEntitySample.intField" required="true"	/>
        
        
        
        	       
        	
        
 		<sj:textfield key="singleEntitySample.stringField" required="true"	maxlength="50"   />
        	
        
        
        
        	       
        	
</s:form>

<sj:a id="savelink" formIds="singleEntitySampleForm" indicator="indicator"	button="true"><fmt:message key="button.save" /></sj:a>
<sj:a id="cancellink" value="Cancel" button="true"><fmt:message key="button.cancel" /></sj:a>

<script type="text/javascript">
    Form.focusFirstElement($("singleEntitySampleForm"));
</script>
