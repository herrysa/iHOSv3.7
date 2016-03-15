<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="searchLinkDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='searchLinkDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			jQuery("#saveSearchLink").attr("action","saveSearchLink?popup=true&navTabId="+'${navTabId}');
			jQuery("#saveSearchLink").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="saveSearchLink" method="post" action="saveSearch?popup=true"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:url id="saveSearchLink" action="saveSearchLink" />


					<s:hidden name="editType" value="%{editType}" label="editType" />
					<s:if test="%{editType==1}">
						<s:textfield key="searchLink.searchLinkId" cssClass="required"
							maxlength="30" readonly="true" />
					</s:if>
					<s:else>
						<s:textfield key="searchLink.searchLinkId" cssClass="required"
							maxlength="30" />
					</s:else>
				</div>

				<div class="unit">
					<s:textfield key="searchLink.link" cssClass="required" maxlength="50" />

				</div>
				<div class="unit">
					<s:textfield key="searchLink.linkField" cssClass="required" style="width:365px"/>
				</div>
				<div class="unit">

					<s:textarea key="searchLink.url" cssClass="required" maxlength="255"
						rows="5" cols="50" />
				</div>
				<div class="unit">
					<s:textfield key="search.searchId"
						name="searchLink.search.searchId"
						value="%{searchLink.search.searchId}" required="false"
						maxlength="30" readonly="true" />
				</div>
				<div class="unit">
					<s:select list="#{'S1':'S1','S2':'S2','M1':'M1','M2':'M2','G':'G' }" key="searchLink.herpType" listKey="key"
						listValue="value" emptyOption="true" maxlength="50" width="50px"
					></s:select>
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
