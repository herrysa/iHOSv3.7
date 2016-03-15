<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="commandScript.title" /></title>
<meta name="heading" content="<fmt:message key='commandScript.title'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			var sql = jQuery("#cmdSciptContent").val();
			if (sql != null && sql != "") {
				jQuery("#commandScriptForm").attr("action","commandScriptForm?popup=true&navTabId="+'${navTabId}');
				//alert(action);
				jQuery("#commandScriptForm").submit();
			} else {
				alertMsg.warn('请输入sql语句！');
			}
		});
		jQuery('#cancellink').click(function() {
			jQuery("#cmdSciptContent").attr("value","");
		});
		
	});
	function clearContent(data){
		//jQuery("#cmdSciptContent").attr("value","");
		formCallBack(data);
	}
</script>
</head>
<div class="page">
	<div class="pageContent">
<s:form id="commandScriptForm" action="commandScriptForm?popup=true" method="post" enctype="multipart/form-data"  onsubmit="return validateCallback(this,clearContent);">
			<div class="pageFormContent" layoutH="56">
	<div class="unit">
						<br> <br> <br>
		<pre><s:textarea key="commandScript.content" name="cmdSciptContent" id="cmdSciptContent" style="width: 80%;" rows="28" /></pre>
	</div>
	<div class="unit">
	<dl class="nowrap">
		<dd style="width: 250px">
			<div style="margin-left: 130px" class="button">
				<div class="buttonContent">
					<button type="button" id="savelink">
						&nbsp;&nbsp;<fmt:message key="commandScript.submit" />&nbsp;&nbsp;
					</button>
				</div>
			</div>
		</dd>
		
		<dd style="width: 100px">
			<div class="button">
				<div class="buttonContent">
					<button type="button" id="cancellink">
						&nbsp;&nbsp;<fmt:message key="button.clear" />&nbsp;&nbsp;
					</button>
				</div>
			</div>
		</dd>
	</dl>
	</div></div>
	<%-- <fieldset class="form-actions">
		<s:submit id="savelink" key="commandScript.submit" method="commandScript"
			cssClass="btn btn-primary" theme="simple" />
		<s:reset  id="cancellink" key="button.cancel"
			cssClass="btn" theme="simple" />
	</fieldset>
 --%>
</s:form>
	</div>
</div>
