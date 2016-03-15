<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		//jQuery("#appendixfile").val("${bulletin.fileName}");
		jQuery('#${random}savelink').click(function() {
			var fileName = jQuery("#appendixfile").val();
			jQuery("#bulletinForm").attr("action","saveBulletin?popup=true&fileName="+fileName+"&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#bulletinForm").submit();
		});
	});
	function changeFile(){
		jQuery("#bulletin_fileName").text("");
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="bulletinForm" method="post"	action="" class="pageForm required-validate" enctype="multipart/form-data"	onsubmit="return iframeCallback(this,uploadCallback);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="bulletin.bulletinId"/>
				</div>
				<div class="unit">
				<s:textfield id="bulletin_subject" key="bulletin.subject" name="bulletin.subject" cssClass="required" size="50"/>
				</div>
				<div class="unit">
				<s:select key="bulletin.secretLevel"
					name="bulletin.secretLevel" value="%{bulletin.secretLevel}"
					id="bulletin.secretLevel" cssClass="required" maxlength="50"
					list="secretLeveList" listKey="value" listValue="content"
					emptyOption="false"></s:select>
				</div>
				<div class="unit">
				<label><s:text name="bulletin.content"/></label>
				<div style="margin-left:130px;margin-right:100px;">
				<s:textarea cssClass="editor" id="bulletin_content" name="bulletin.content" rows="20" cols="80"></s:textarea>
				</div>
				</div>
				<div class="unit">
				<s:file key="bulletin.appendix" id="appendixfile" name="appendixfile" onchange="changeFile();"></s:file>
				<span id="bulletin_fileName" style="margin-left:5px">${bulletin.fileName}</span>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





