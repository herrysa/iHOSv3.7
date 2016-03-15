<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<title><fmt:message key="reportDetail.title" /></title>
<meta name="heading" content="<fmt:message key='reportDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			jQuery("#reportForm").attr("action","saveReport?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#reportForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<s:form id="reportForm" method="post" action="saveReport?popup=true"
			cssClass="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				
<s:url id="saveReport" action="saveReport" />

	<s:hidden key="report.reportId"/>
	<s:textfield key="report.reportName" cssClass="required" style="width:380px" />
	</div>
	<div class="unit">
	<s:textfield key="report.groupName" cssClass="required" maxlength="30" />
	<s:select key="report.reportType" required="false" maxlength="20"
		list="reportTypeList" listKey="value" cssClass="required"
		listValue="content" emptyOption="true"></s:select>
	</div>
	<div class="unit">
	<sj:textfield key="report.reqNo" cssClass="required digits" />
</div>
<div class="unit">
	<sj:textarea key="report.url" required="false" maxlength="255" rows="5"
		cols="61" />
	</div>
	<div class="unit">
	<sj:textarea key="report.remark" required="false" maxlength="100"
		rows="5" cols="61" />
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
