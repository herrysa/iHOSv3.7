<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="searchList.title" /></title>
<meta name="heading" content="<fmt:message key='searchList.heading'/>" />
<meta name="menu" content="SearchMenu" />
<c:if test="${importResult!=null && importResult!=''}">
    <script type="text/javascript">
        alert("${importResult}");
    </script>
</c:if>
<script>
	jQuery(document).ready(function() {

		jQuery('#importExcel').click(function() {
			//jQuery("#importForm").attr("action","importExcel?navTabId="+'${navTabId}');
			//alert(action);
			//importForm.action = "importExcel?navTabId="+'${navTabId}';
			importForm.submit;
		});
	});
</script>
</head>
<%-- <%-- importResult is: ${importResult} 

<s:form id="importForm" action="importExcel" method="post" enctype="multipart/form-data">
	<sj:textfield  id="exportSearchId" name="exportSearchId" key="search.searchId" value="" required="true" maxlength="30" />
	<sj:textfield  id="exportSearchName" name="exportSearchName" key="search.searchName" value="" required="true" maxlength="30" />
	<s:file id="excelfile" name="excelfile"></s:file>
	
</s:form>
<sj:a id="importExcel" formIds="importForm" indicator="indicator"	button="true">
	<fmt:message key="searchImport.title" />
</sj:a> --%>




<div class="pageContent">
	<form id="importForm" method="post" action="importExcel" enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback(this,uploadCallback);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>请选择文件：</label>
				<input name="excelfile" id="excelfile" type="file" />
				<input name="navTabId" id="navTabId" type="hidden" value="${navTabId}"/>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>





