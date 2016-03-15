<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="searchOptionDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='searchOptionDetail.heading'/>" />
<script>
	jQuery(document).ready(
			function() {
				jQuery('#cancellink').click(function() {
					window.close();
				});
				jQuery('#${random}savelink').click(
						function() {
							jQuery("#searchOptionForm").attr(
									"action",
									"saveSearchOption?popup=true&navTabId="
											+ '${navTabId}');
							jQuery("#searchOptionForm").submit();
						});
				var c = jQuery("#searchOption_isForm").attr("checked");
				if(c=="checked"){
					jQuery("#searchOption_table_form").show();
				}else{
					jQuery("#searchOption_table_form").hide();
				}
				jQuery("#searchOption_isForm").click(function(){
					var c = jQuery(this).attr("checked");
					if(c=="checked"){
						jQuery("#searchOption_table_form").show();
					}else{
						jQuery("#searchOption_table_form").hide();
					}
				});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="searchOptionForm" method="post"
			action="saveSearchOption?popup=true"
			class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">

					<s:url id="saveSearchOption" action="saveSearchOption" />
					<s:url id="dicSelectList" action="dictionaryItemSelectList"
						namespace="/">
						<s:param name="dicCode">radioyesno</s:param>
					</s:url>
					<s:hidden name="editType" value="%{editType}" label="editType" />
					<s:if test="%{editType==1}">
						<s:textfield key="searchOption.searchOptionId" cssClass="required"
							maxlength="30" readonly="true" />
					</s:if>
					<s:else>
						<s:textfield key="searchOption.searchOptionId" cssClass="required"
							maxlength="30" />
					</s:else>
					<s:textfield key="searchOption.fieldName" cssClass="required"
						maxlength="30" />
				</div>
				<div class="unit">
					<s:textfield key="searchOption.title" cssClass="required"
						maxlength="50" />
					<label><fmt:message key='searchOption.visible' />:</label>
					<span style="float:left;width:140px">
						<s:checkbox key="searchOption.visible" theme="simple"/>
					</span>
				</div>
				<div class="unit">
				<label><fmt:message key='searchOption.sortable' />:</label>
					<span style="float:left;width:140px">
					<s:checkbox key="searchOption.sortable"  theme="simple"/>
					</span>
					<label><fmt:message key='searchOption.frozen' />:</label>
					<span style="float:left;width:140px">
					<s:checkbox key="searchOption.frozen"  theme="simple"/>
					</span>
				</div>
				<div class="unit">
					<s:textfield key="searchOption.oorder" cssClass="required digits" />

					<s:textfield key="search.searchId"
						name="searchOption.search.searchId"
						value="%{searchOption.search.searchId}" required="false"
						maxlength="30" readonly="true" />
				</div>
				<div class="unit">
					<s:url id="alignTypeSelectList" action="dictionaryItemSelectList"
						namespace="/">
						<s:param name="dicCode">searchOption_alignType</s:param>
					</s:url>
					<label><fmt:message key='searchOption.alignType' />:</label>
					<span style="float:left;width:140px">
					<sj:select href="%{alignTypeSelectList}"
						key="searchOption.alignType" cssClass="required"
						list="dictionaryItemsSelectList" listKey="value"
						listValue="content" emptyOption="false"/>
					</span>

					<s:textfield key="searchOption.displayFormat" required="false"
						maxlength="50" />
				</div>
				<div class="unit">
					<s:url id="fieldtypeSelectList" action="dictionaryItemSelectList"
						namespace="/">
						<s:param name="dicCode">search_fieldtype</s:param>
					</s:url>
					<label><fmt:message key='searchOption.fieldType' />:</label>
					<span style="float:left;width:140px">
					<sj:select href="%{fieldtypeSelectList}"
						key="searchOption.fieldType" cssClass="required"
						list="dictionaryItemsSelectList" listKey="value"
						listValue="content" emptyOption="true" />
					</span>
					<s:textfield key="searchOption.displayWidth" required="false"
						maxlength="5" />
				</div>
				<div class="unit">
				<label><fmt:message key='searchOption.printable' />:</label>
					<span style="float:left;width:140px">
					<s:checkbox key="searchOption.printable" theme="simple"/>
					</span>
					<s:url id="edittypeSelectList" action="dictionaryItemSelectList"
						namespace="/">
						<s:param name="dicCode">search_edittype</s:param>
					</s:url>
					<label><fmt:message key='searchOption.editType' />:</label>
					<span style="float:left;width:140px">
					<sj:select href="%{edittypeSelectList}" key="searchOption.editType"
						cssClass="required" list="dictionaryItemsSelectList" listKey="value"
						listValue="content" emptyOption="true"/>
					</span>
				</div>
				<div class="unit">
					<label><fmt:message key='searchOption.merge' />:</label>
					<span style="float:left;width:140px">
					<s:checkbox key="searchOption.merge" theme="simple"/>
					</span>
					<label><s:text name='searchOption.isForm' />:</label>
					<span style="float:left;width:140px">
					<s:checkbox id="searchOption_isForm" key="searchOption.isForm" theme="simple"/>
					</span>
				</div>
				<div id="searchOption_table_form">
					<div class="unit">
					<s:textfield key="searchOption.tableField" required="false"
						maxlength="50" />
					<%-- <s:textfield key="searchOption.tableKey" required="false"
					maxlength="50" /> --%>
					<s:select key="searchOption.userTag" headerKey="" headerValue="" list="{'yearMonth','yearMonthDate','checkbox','stringSelect','stringSelectR','sqlSelect','sqlSelectR','dicSelect','deptTreeS','deptTreeM','deptFormulaSelect','orgFormulaSelect','personTreeS','personTreeM','costitemTreeS','costitemTreeM','treeSelectS','treeSelectM'}"></s:select>
					</div>
					<div class="unit">
						<s:textfield key="searchOption.formTitle"></s:textfield>
						<s:textfield key="searchOption.length" cssClass="digits" maxlength="4"></s:textfield>
						<%-- <label><s:text name='searchOption.alone' />:</label>
						<span style="float:left;width:140px">
						<s:checkbox key="searchOption.alone" theme="simple"/>
						</span> --%>
						
					</div>
					<div class="unit">
						<label><s:text name='searchOption.required' />:</label>
						<span style="float:left;width:140px">
						<s:checkbox key="searchOption.required" theme="simple"/>
						</span>
						<label><s:text name='searchOption.readOnly' />:</label>
						<span style="float:left;width:140px">
						<s:checkbox key="searchOption.readOnly" theme="simple"/>
						</span>
					</div>
				</div>
				<div class="unit">
						<s:textarea key="searchOption.param1" required="false"
						maxlength="255" rows="5" cols="54" />
				</div>
				<div class="unit">

					<s:textarea key="searchOption.param2" required="false"
						maxlength="255" rows="5" cols="54" />
				</div>
				<div class="unit">
					<s:url id="calctypeSelectList" action="dictionaryItemSelectList"
						namespace="/">
						<s:param name="dicCode">search_calctype</s:param>
					</s:url>
					<label><fmt:message key='searchOption.calcType' />:</label>
					<span style="float:left;width:140px">
					<sj:select href="%{calctypeSelectList}" key="searchOption.calcType"
						required="false" list="dictionaryItemsSelectList" listKey="value"
						listValue="content" emptyOption="true" />
					</span>
				</div>
				<div class="unit">
					<s:textarea key="searchOption.url" required="false"
						maxlength="255" rows="5" cols="54" />
				</div>
				<div class="unit">
					<s:textarea key="searchOption.linkField" required="false"
						maxlength="255" rows="5" cols="54" />
				</div>
				<div class="unit">
					<s:textarea key="searchOption.threshold" required="false"
						maxlength="255" rows="3" cols="54" />
				</div>
				<div class="unit">
					<s:textarea key="searchOption.thresholdR" required="false"
						maxlength="255" rows="3" cols="54" />
				</div>
				<div class="unit">
					<s:select list="#{'S1':'S1','S2':'S2','M1':'M1','M2':'M2','G':'G' }" key="searchOption.herpType" listKey="key"
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
