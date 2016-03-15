<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#${random}savelink').click(function() {
			var fileName = jQuery("#bylaw_appendixfile").val();
			jQuery("#byLawForm").attr("action","saveByLaw?popup=true&fileName="+fileName+"&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#byLawForm").submit();
		});
	});
	function bylawChangeFile(){
		jQuery("#bylaw_fileName").text("");
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="byLawForm" method="post"	action="" class="pageForm required-validate"	enctype="multipart/form-data"	onsubmit="return iframeCallback(this,uploadCallback);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="byLaw.byLawId"/>
				</div>
				<div class="unit">
				<s:textfield id="byLaw_title" key="byLaw.title" name="byLaw.title" cssClass="required" size="50"/>
				</div>
				<div class="unit">
				<label><s:text name="byLaw.body"/></label>
				<div style="margin-left:130px;margin-right:100px;">
				<s:textarea cssClass="editor" id="byLaw_body" name="byLaw.body" rows="20" cols="80"></s:textarea>
				</div>
				</div>
				<div class="unit">
				<s:file key="byLaw.appendix" id="bylaw_appendixfile" name="bylaw_appendixfile" onchange="bylawChangeFile();"></s:file>
				<span id="bylaw_fileName" style="margin-left:5px">${byLaw.fileName}</span>
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





