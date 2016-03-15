<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title><fmt:message key="dictionaryItemsList.title" /></title>
<meta name="heading" content="<fmt:message key='dictionaryItemsList.heading'/>" />
<script>
	function getPinYinSelectCode(selStr) {
		var arr = selStr.split(" ");
		return arr[0];
	}
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
	jQuery('#${random}savelink').click(function() {
			jQuery("#dictionaryItemForm").attr("action","saveDictionaryItem?popup=true&navTabId="+'${navTabId}');
			jQuery("#dictionaryItemForm").submit();
	}); 
		jQuery('input:text:first').focus();
	});
</script>
</head>
<body onload="vaildeP()">
	<div class="page">
		<div class="pageContent">
			<s:form id="dictionaryItemForm" method="post" action="saveDictionaryItem?popup=true"
				cssClass="pageForm required-validate"
				onsubmit="return validateCallback(this,formCallBack);">
				<div class="pageFormContent" layoutH="56">
					<div class="unit">
					<s:hidden id="dictionaryId" name="dictionaryId" value="%{dictionaryId}"/>
					<s:hidden name="dictionaryItem.dictionaryItemId" value="%{dictionaryItem.dictionaryItemId}"/>
						<s:textfield key="dictionaryItem.value" cssClass="required" maxlength="20" />
					</div>
					<div class="unit">
						<s:textfield key="dictionaryItem.content" cssClass="required" maxlength="20"/>
					</div>	
					<div class="unit">
						<s:textfield key="dictionaryItem.orderNo" cssClass="required digits" maxlength="20"/>
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
			</s:form>
		</div>
	</div>

</body>
</html>