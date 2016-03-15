<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
<title><fmt:message key="userDetail.title" />
</title>
<meta name="heading" content="<fmt:message key='userDetail.heading'/>" />
<script type="text/javascript">
	jQuery(document)
			.ready(
					function() {
						jQuery('#cancellink').click(function() {
							window.close();
						});
						jQuery('#${random}savelink').click(
										function() {
											jQuery("#dataSourceDefineForm").attr("action","saveDataSourceDefine?popup=true&navTabId="+'${navTabId}');
											jQuery("#dataSourceDefineForm").submit();
										});
						jQuery('#testLink').click(
								function() {
									/* var form = jQuery('#dataSourceDefineForm').ajaxForm(
												{
													dataType : 'json',
													url : 'testDataSourceDefine?popup=true',
													success : processJson
												}).trigger("submit"); */
								jQuery("#dataSourceDefineForm").attr("action","testDataSourceDefine?popup=true");
								jQuery("#dataSourceDefineForm").submit();
						});
						jQuery('#UrlLink')
								.click(
										function() {
											/* 	var dstId = jQuery("#dataSourceDefinedataSourceTypedataSourceTypeId")
														.attr("value");
												var form = jQuery('#dataSourceDefineForm')
														.ajaxForm(
																{
																	dataType : 'json',
																	url : 'urlTemplateGlance?popup=true&dstId='+ dstId,
																	success : processUrl
																});
												form.submit(); */

											var dstId = jQuery(
													"#dataSourceDefinedataSourceTypedataSourceTypeId")
													.attr("value");
											jQuery
													.ajax({
														dataType : 'json',
														url : 'urlTemplateGlance?popup=true&dstId='
																+ dstId,
														type : 'POST',
														success : function(data) {
															$("#background,#progressBar").hide();
															var url = data["gridOperationMessage"];
															//var forUrl = jQuery('#dataSourceDefine.url').attr('value',"fdc test");
															var forUrl = document
																	.getElementById("dataSourceDefine.url").value;
															document
																	.getElementById("dataSourceDefine.url").value = url;
															forUrl = document
																	.getElementById("dataSourceDefine.url").value;
														}
													});
										});
						jQuery("input[name='dataSourceDefine.password']").val("${dataSourceDefine.password}");
					});

	function processJson(data) {
		//alert("fdc here.");
		var msg = data["gridOperationMessage"];
		//alert(msg);
		jQuery('div.#mybuttondialog').html(msg);
		jQuery('#mybuttondialog').dialog('open');
		return;

	}
	function getUrlTemplate() {
		var dstId = jQuery("#dataSourceDefinedataSourceTypedataSourceTypeId").attr("value");
		jQuery.ajax({
								dataType : 'json',
					url : 'urlTemplateGlance?popup=true&dstId=' + dstId,
					type : 'POST',
					success : function(data) {
						$("#background,#progressBar").hide();
						var url = data["gridOperationMessage"];
						//var forUrl = jQuery('#dataSourceDefine.url').attr('value',"fdc test");
						var forUrl = document.getElementById("dataSourceDefine.url").value;
						//if (forUrl == null || forUrl == "")
							document.getElementById("dataSourceDefine.url").value = url;
						/* forUrl = document
								.getElementById("dataSourceDefine.url").value; */
					}
				});
	};

	function processUrl(data) {
		var url = data["gridOperationMessage"];
		//var forUrl = jQuery('#dataSourceDefine.url').attr('value',"fdc test");
		var forUrl = document.getElementById("dataSourceDefine.url").value;
		if (forUrl == null || forUrl == "")
			document.getElementById("dataSourceDefine.url").value = url;
		forUrl = document.getElementById("dataSourceDefine.url").value;
		//var urlval = forUrl.attr("value");
		//alert(forUrl);
	}
	function okButton() {
		jQuery('#mybuttondialog').dialog('close');
	};
</script>
</head>
<body>

	<div class="page">
		<div class="pageContent">
			<form id="dataSourceDefineForm" method="post" action=""
				class="pageForm required-validate"
				onsubmit="return validateCallback(this,formCallBack);">
				<div class="pageFormContent" layoutH="56">
				<s:url id="saveDataSourceDefine" action="saveDataSourceDefine" />
<s:url id="dstSelectList" action="dataSourceTypeSelectList" />
					<s:hidden key="dataSourceDefine.dataSourceDefineId" />
					<div class="unit">
						<s:textfield key="dataSourceDefine.dataSourceName"
							cssClass="required"></s:textfield>
					</div>
					<div class="unit">
					<label><fmt:message key='dataSourceType.dataSourceTypeName' />:</label>	<sj:select href="%{dstSelectList}"
		key="dataSourceType.dataSourceTypeName"
		name="dataSourceDefine.dataSourceType.dataSourceTypeId"
		value="%{dataSourceDefine.dataSourceType.dataSourceTypeId}"
		id="dataSourceDefinedataSourceTypedataSourceTypeId" cssClass="required"
		maxlength="50" list="dataSourceTypeSelectList"
		listKey="dataSourceTypeId" listValue="dataSourceTypeName"
		onchange="getUrlTemplate()" />
					</div>
					<div class="unit">
						<s:textarea id="dataSourceDefine.url" key="dataSourceDefine.url"
							 maxlength="200" rows="2" cols="50" />
					</div>
					<div class="unit">
						<s:textfield key="dataSourceDefine.userName" ></s:textfield>
					</div>
					<div class="unit">
						<s:password key="dataSourceDefine.password" ></s:password>
					</div>
					<div class="unit">
						<s:textarea key="dataSourceDefine.note" required="false"
							maxlength="100" rows="5" cols="50" />
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
						<li>
							<div class="button">
								<div class="buttonContent">
									<button id="UrlLink" type="Button">URL模版</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button id="testLink" type="Button">测试链接</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</div>
</body>
</html>