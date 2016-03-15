<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="periodDetail.title" /></title>
<meta name="heading" content="<fmt:message key='periodDetail.heading'/>" />
<script language="javascript" type="text/javascript"
	src="${ctx}/scripts/DatePicker/WdatePicker.js"></script>
<script>
	jQuery(document).ready(function() {
		jQuery('#${random}savelink').click(function() {
			jQuery("#periodForm").attr("action","savePeriod?popup=true&navTabId="+'${navTabId}');
			jQuery("#periodForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<s:form id="periodForm" method="post" action="savePeriod"
			cssClass="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:url id="savePeriod" action="savePeriod" />
					<s:url id="list" action="periodList" />


					<s:hidden key="period.periodId" />

					<s:textfield key="period.periodCode" maxlength="6"
						cssClass="required" />
				</div>
				<div class="unit">
					<s:textfield key="period.cyear" maxlength="4"
						cssClass="required" onClick="WdatePicker({skin:'ext',dateFmt:'yyyy'})" />
				</div>
				<div class="unit">
					<s:textfield key="period.cmonth" maxlength="2"
						cssClass="required" onClick="WdatePicker({skin:'ext',dateFmt:'MM'})"/>
				</div>
				<div class="unit">
					<label><fmt:message key='period.startDate' />:</label><input
						class="input-small" name="period.startDate" type="text"
						onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value="${period.startDate}" pattern="yyyy-MM-dd"/>"
						/>
				</div>
				<div class="unit">
					<label><fmt:message key='period.endDate' />:</label><input
						class="input-small" name="period.endDate" type="text"
						onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
						value="<fmt:formatDate value="${period.endDate}" pattern="yyyy-MM-dd"/>"
						/>
				</div>
				<div class="unit">
					<sj:textarea key="period.note" required="false" maxlength="200"
						rows="3" cols="50" cssClass="input-small" />
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
