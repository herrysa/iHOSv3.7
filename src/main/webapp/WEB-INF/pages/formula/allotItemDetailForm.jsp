<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="allotItemDetailDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='allotItemDetailDetail.heading'/>" />
<script type="text/javascript"
	src="${ctx}/scripts/fancyBox/source/jquery.fancybox.js?v=2.0.6"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/scripts/fancyBox/source/jquery.fancybox.css?v=2.0.6"
	media="screen" />

<script>
	jQuery(document)
			.ready(
					function() {
						jQuery('#cancellink').click(function() {
							window.close();
						});
						jQuery('#${random}savelink').click(function() {
							jQuery("#allotItemDetailForm").attr("action","saveAllotItemDetail?popup=true&navTabId="+'${navTabId}');
							jQuery("#allotItemDetailForm").submit();
						});
						jQuery('input:text:first').focus();
					});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<s:form id="allotItemDetailForm" method="post"
			action="saveAllotItemDetail?popup=true"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<s:url id="saveAllotItemDetail" action="saveAllotItemDetail" />


				<s:hidden key="allotItemDetail.allotItemDetailId" />
				<s:textfield key="allotItemDetail.allotItem.allotItemId" readonly="true"></s:textfield>

			</div>
			<div class="unit">

				<sj:textfield key="allotItemDetail.costRatio" cssClass="required number" />
			</div>
			<div class="unit">
				<s:select label="%{getText('allotItemDetail.principle')}"
					name="allotItemDetail.principle.allotPrincipleId"
					value="%{allotItemDetail.principle.allotPrincipleId}"
					id="allotItemDetail.principle.allotPrincipleId" cssClass="required"
					maxlength="50" list="allotPrinciples" listKey="allotPrincipleId"
					listValue="allotPrincipleName" emptyOption="true"></s:select>
			</div>
			<div class="unit">
				<s:select label="%{getText('allotItemDetail.backPrinciple')}"
					key="allotItemDetail.bakPrinciple"
					name="allotItemDetail.bakPrinciple.allotPrincipleId"
					value="%{allotItemDetail.bakPrinciple.allotPrincipleId}"
					id="allotItemDetail.bakPrinciple.allotPrincipleId" cssClass="required"
					maxlength="50" list="allotPrinciples" listKey="allotPrincipleId"
					listValue="allotPrincipleName" emptyOption="true"></s:select>
			</div>
			<div class="unit">
				<%-- 	<s:select label="%{getText('allotItemDetail.realPrinciple')}" key="allotItemDetail.realPrinciple" 
		name="allotItemDetail.realPrinciple.allotPrincipleId"
		value="%{allotItemDetail.realPrinciple.allotPrincipleId}"
			id="allotItemDetail.realPrinciple.allotPrincipleId"
		required="false"
		 maxlength="50"
			list="allotPrinciples"
			listKey="allotPrincipleId" 
			listValue="allotPrincipleName"  emptyOption="true"
			></s:select>	 --%>

				<sj:textarea key="allotItemDetail.remark" required="false"
					maxlength="100" rows="5" cols="50" />
			</div>
				<!--      <input id="idField" name="idField" type="text"/>
<input id="nameField" name="nameField" type="text"/>
	<a id="various2" href="#inline2">选择</a>   <div id="inline2">
    <a href="javascript:;" onclick="jQuery.fancybox.close();">Close</a>
   </div>    -->
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