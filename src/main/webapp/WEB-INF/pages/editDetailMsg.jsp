<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script>
	jQuery(document).ready(function() {
		var htmlField = "${param.htmlField}";
		var textField = "${param.textField}";
		var detailEditor=$('#detailEditor').xheditor({tools:'full',skin:'vista'});
		
		jQuery('#confirmDetailButton').click(function() {
			/* var fileName = jQuery("#bylaw_appendixfile").val();
			jQuery("#byLawForm").attr("action","saveByLaw?popup=true&fileName="+fileName+"&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#byLawForm").submit(); */
			var sHtml = detailEditor.getSource();
			var sText = sHtml.replace(/<[^>]+>/g,"");
			sText = sText.replace(/&nbsp;/g,"");
			//eval(htmlField+"('"+sHtml+"','"+sText+"')");
			//jQuery("#"+htmlField).val(sHtml);
			var textFieldArr = textField.split(".");
			var htmlFieldArr = htmlField.split(".");
			eval("jQuery('#"+textFieldArr[0]+"')."+textFieldArr[1]+"('"+sText+"')");
			eval("jQuery('#"+htmlFieldArr[0]+"')."+htmlFieldArr[1]+"('"+sHtml+"')");
			//alert(textFieldArr[0]);
			$.pdialog.closeCurrent();
		});
	});
</script>

<div class="page">
	<div class="pageContent">
		<form id="byLawForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return iframeCallback(this,uploadCallback);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<div style="margin-left:10px;margin-right:10px;">
				<s:textarea id="detailEditor" name="byLaw.body" rows="20" cols="60"></s:textarea>
				</div>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="confirmDetailButton">确定</button>
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





