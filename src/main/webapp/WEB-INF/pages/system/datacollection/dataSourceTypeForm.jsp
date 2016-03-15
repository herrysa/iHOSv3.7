<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="dataSourceTypeDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='dataSourceTypeDetail.heading'/>" />
<script>
	jQuery(document)
			.ready(
					function() {
						jQuery('#cancellink').click(function() {
							window.close();
						});
						jQuery('#${random}savelink')
								.click(
										function() {
											jQuery("#dataSourceTypeForm").attr("action","saveDataSourceType?popup=true&navTabId="+'${navTabId}');
											jQuery("#dataSourceTypeForm").submit();
										});
					});
</script>
</head>
<div class="page">
	<div class="pageContent">
		<form id="dataSourceTypeForm" method="post"
			action="saveDataSourceType" class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<s:url id="saveDataSourceType" action="saveDataSourceType" />
				<s:url id="list" action="dataSourceTypeList" />

				<s:hidden key="dataSourceType.dataSourceTypeId" />
				<div class="unit">
					<s:textfield key="dataSourceType.dataSourceTypeName"
						cssClass="required" maxlength="100" size="50"/>
				</div>
				<div class="unit">
					<s:textfield key="dataSourceType.fileType"	maxlength="100"  size="50"/>
				</div>
				<div class="unit">
					<s:textfield key="dataSourceType.helperClassName" cssClass="required"
						maxlength="100"  size="50"/>
				</div>
				<div class="unit">
					<s:textfield key="dataSourceType.urlTemplate" cssClass="required"
						maxlength="100"  size="50"/>
				</div>
				<div class="unit">
				<label><fmt:message key='dataSourceType.isNeedFile' /></label>
					<s:checkbox key="dataSourceType.isNeedFile" theme="simple"
						labelposition="left" />
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

<%-- <sj:a id="savelink" formIds="dataSourceTypeForm" indicator="indicator"	button="true"><fmt:message key="button.save" /></sj:a>
<sj:a id="cancellink" value="Cancel" button="true" href="%{list}"><fmt:message key="button.cancel" /></sj:a> --%>


