<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title><fmt:message key="dictionaryDetail.title" /></title>
<meta name="heading" content="<fmt:message key='dictionaryDetail.heading'/>" />
<script>
	function getPinYinSelectCode(selStr) {
		var arr = selStr.split(" ");
		return arr[0];
	}
	jQuery.subscribe('processDepartmentPin', function(event, data) {
		var chargeid = document.getElementById("departmentPinYin").value;
		chargeid = getPinYinSelectCode(chargeid);
		//alert(chargeid);
		/* document
				.getElementById("person.department.departmentId").value = chargeid; */

		jQuery("#persondepartmentdepartmentId").attr("value", chargeid);
	});
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
 		jQuery('#${random}savelink').click(function() {
			jQuery("#dictionaryForm").attr("action", "saveDictionary?popup=true&navTabId="+'${navTabId}');
			jQuery("#dictionaryForm").submit();
		}); 
		jQuery('input:text:first').focus();
	});
</script>
</head>
<body onload="vaildeP()">
	<div class="page">
		<div class="pageContent">
			<s:form id="dictionaryForm" method="post" action="saveDictionary?popup=true"
				cssClass="pageForm required-validate"
				onsubmit="return validateCallback(this,formCallBack);">
				<div class="pageFormContent" layoutH="56">
					<div class="unit">

						<s:hidden key="dictionary.dictionaryId" maxlength="30"/>

						<s:textfield key="dictionary.name" cssClass="required" maxlength="20" />
					</div>
					<div class="unit">
						<s:textfield key="dictionary.code" cssClass="required" maxlength="20"/>
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