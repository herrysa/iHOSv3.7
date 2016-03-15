<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<title><fmt:message key="upload.title" /></title>
<meta name="heading" content="<fmt:message key='upload.heading'/>" />
<head>
<script>
jQuery('#uploadButton').click(
		function() {
			var fileName = jQuery('#upLoadfile').val();
			if(fileName&&fileName.indexOf(".")!=-1){
				var suffixArr = fileName.split(".");
				var suffix = suffixArr[suffixArr.length-1];
				if(suffix=='xls'||suffix=='xlsx'){
					jQuery("#uploadFileForm").attr("action","dataCollectionTaskSelectFile!upload?popup=true&navTabId="+'${navTabId}');
					jQuery("#uploadFileForm").submit();
				}else{
					alertMsg.error('请选择excel文件！');
				}
			}
			//jQuery("#uploadFileForm").attr("action","dataCollectionTaskSelectFile!upload?popup=true&navTabId="+'${navTabId}');
			//jQuery("#uploadFileForm").submit();
		});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<s:form id="uploadFileForm" method="post" enctype="multipart/form-data"
			action=""
			class="pageForm required-validate"
			onsubmit="return iframeCallback(this,uploadCallback);">
			<div class="pageFormContent" layoutH="56">

	<li class="info"><fmt:message key="upload.message" /></li>

	<s:hidden name="dataCollectionTaskId"
		value="%{dataCollectionTask.dataCollectionTaskId}"></s:hidden>
		<div class="unit">
	<s:file id="upLoadfile" name="file" label="%{getText('uploadForm.file')}"
		cssClass="text file" required="true"/>

</div>
	
</div>
			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="uploadButton"><fmt:message key="button.upload"/></button>
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
		</s:form>
	</div>
</div>

