<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="searchItemDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='searchItemDetail.heading'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			jQuery("#searchItemForm").attr("action","saveSearchItem?popup=true&navTabId="+'${navTabId}');
			jQuery("#searchItemForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="searchItemForm" method="post"
			action="saveSearchItem?popup=true" class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">

					<s:url id="saveSearchItem" action="saveSearchItem" />

					<%-- <s:url id="dicSelectList" action="dictionaryItemSelectList"
	namespace="/system">
	<s:param name="dicCode">radioyesno</s:param>
</s:url> --%>
					<s:hidden name="editType" value="%{editType}" label="editType" />
					<s:if test="%{editType==1}">
						<s:textfield key="searchItem.searchItemId" cssClass="required"
							maxlength="30" readonly="true"/>
					</s:if>
					<s:else>
						<s:textfield key="searchItem.searchItemId" cssClass="required"
							maxlength="30" />
					</s:else>

					<%-- 	<s:if test="%{searchItem.searchItemId!=null && searchItem.searchItemId!=searchItem.search.searchId}">
		<s:textfield key="searchItem.searchItemId" required="true"
			maxlength="30" readonly="true" />
	</s:if>
	<s:else>
		<s:textfield key="searchItem.searchItemId" required="true"
			maxlength="30" />
	</s:else> --%>
					<s:textfield key="searchItem.htmlField" cssClass="required"
						maxlength="64" />
				</div>
				<div class="unit">
					<s:textfield key="searchItem.caption" cssClass="required"
						maxlength="64" />
					<s:textfield key="searchItem.field" cssClass="required" maxlength="64" />
				</div>
				<div class="unit">
					<s:textfield key="searchItem.operator" required="false"
						maxlength="4" />
					<s:textfield key="searchItem.oorder" cssClass="required digits" maxlength="4" />
				</div>
				<div class="unit">
					<s:textfield key="searchItem.suffix" maxlength="20"></s:textfield>
					<label><s:text name="searchItem.readOnly"/>:</label>
					<span style="float:left;width:140px">
					<s:checkbox key="searchItem.readOnly"
						labelposition="left" theme="simple"/>
					</span>
				</div>
				<div class="unit">
					<%-- <s:textfield key="searchItem.userTag" required="false"
						maxlength="20" /> --%>
					<s:select key="searchItem.userTag" headerKey="" headerValue="" list="{'yearMonth','checkbox','stringSelect','stringSelectR','dpSelect','dpSelectR','sqlSelect','sqlSelectR','dicSelect','deptTreeS','deptTreeM','deptFormulaSelect','orgFormulaSelect','personTreeS','personTreeM','costitemTreeS','costitemTreeM','treeSelectS','treeSelectM'}"></s:select>
					<%-- <s:radio key="searchItem.searchFlag" required="true"
		href="%{dicSelectList}" list="dictionaryItemsSelectList"
		listKey="value" listValue="content" buttonset="false"/> --%>
					
					<s:textfield key="searchItem.length" cssClass="digits" maxlength="4"></s:textfield>
				</div>
				<div class="unit">
					<label><fmt:message key='searchItem.searchFlag' />:</label>
					<span style="float:left;width:140px">
					<s:checkbox key="searchItem.searchFlag"
						labelposition="left" theme="simple"/>
					</span>
					<label>必填:</label>
					<s:checkbox key="searchItem.required"
						labelposition="left" theme="simple"/>
				</div>

				<div class="unit">
						<label><fmt:message key='searchItem.param1' />:</label>
					<s:textarea key="searchItem.param1" required="false"
						maxlength="255" rows="5" cols="50" theme="simple"/>
				</div>
				<div class="unit">
				<label><fmt:message key='searchItem.param2' />:</label>
					<s:textarea key="searchItem.param2" required="false"
						maxlength="255" rows="5" cols="50" theme="simple"/>
				</div>
				<div class="unit">
				<label><fmt:message key='searchItem.clickEvent' />:</label>
					<s:textarea key="searchItem.clickEvent" required="false"
						maxlength="255" rows="5" cols="50" theme="simple"/>
					<%-- 	<s:radio key="searchItem.mutiSelect" required="false"
		href="%{dicSelectList}" list="dictionaryItemsSelectList"
		listKey="value" listValue="content" /> --%>
				</div>
				<div class="unit">
				<label><fmt:message key='searchItem.mutiSelect' />:</label>
				<span style="float:left;width:140px">
					<s:checkbox key="searchItem.mutiSelect" required="false"
						labelposition="left" theme="simple"/>
						</span>
					<s:textfield key="search.searchId"
						name="searchItem.search.searchId"
						value="%{searchItem.search.searchId}" required="false"
						maxlength="30" readonly="true" />
				</div>
				<div class="unit">
					<s:select list="#{'S1':'S1','S2':'S2','M1':'M1','M2':'M2','G':'G' }" key="searchItem.herpType" listKey="key"
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
