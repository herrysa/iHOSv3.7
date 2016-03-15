<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="deptSplitDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='deptSplitDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			jQuery("#deptSplitForm").attr("action","saveDeptSplit?popup=true&navTabId="+'${navTabId}');
			jQuery("#deptSplitForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="deptSplitForm" method="post"
			action="saveDeptSplit?popup=true" class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:url id="saveDeptSplit" action="saveDeptSplit" />
					<s:hidden key="deptSplit.id" />
					<s:textfield key="deptSplit.costratio" cssClass="required" />
					<s:textfield key="deptSplit.deptid" cssClass="required" maxlength="20" />
				</div>
				<div class="unit">
					<s:textfield key="deptSplit.deptname" cssClass="required"
						maxlength="50" />
					<s:url id="dicSelectList" action="dictionaryItemSelectList"
						namespace="/system">
						<s:param name="dicCode">radioyesno</s:param>
					</s:url>
					<sj:radio key="deptSplit.disabled" cssClass="required"
						href="%{dicSelectList}" list="dictionaryItemsSelectList"
						listKey="value" listValue="content" />
				</div>
				<div class="unit">
					<s:textfield key="deptSplit.payinrattio" required="false" />
					<s:textfield key="deptSplit.pubdeptid" cssClass="required"
						maxlength="20" />
				</div>
				<div class="unit">
					<s:textfield key="deptSplit.pubdeptname" cssClass="required"
						maxlength="50" />
				</div>
				<div class="unit">
					<s:textarea key="deptSplit.remark" required="false"
						maxlength="200" rows="5" cols="61" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink">保存</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
							</div>
						</div></li>
				</ul>
			</div>
		</form>
	</div>
</div>