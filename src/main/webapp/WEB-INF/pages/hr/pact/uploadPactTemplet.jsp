<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#confirmUploadPactTemplet').click(function() {
			var pactTempletFile  = jQuery("#pactTempletFile").val();
			if(pactTempletFile){
				if(!pactTempletFile.endWith(".doc") && !pactTempletFile.endWith(".docx")){
					alertMsg.error("只能上传.doc或者.docx文件。");
					return;
				}
			}
			jQuery("#pactDocFileName").val(pactTempletFile);
			jQuery("#uploadPactTempletFrom").attr("action","confirmUploadPactTemplet");
			$.ajax({
			    url: "checkPactTemplateExist",
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			    },
			    success: function(data){
			    	if(data==null){
						jQuery("#uploadPactTempletFrom").submit();
			    	}else{
			    		alertMsg.confirm("模板已存在，继续上传将会覆盖旧模板，确定覆盖吗?", {
							okCall : function() {
								jQuery("#uploadPactTempletFrom").submit();
							}
						});
			    	}
			    }
			}); 
			
		});
	});
	String.prototype.endWith=function(str){
		if(str==null||str==""||this.length==0||str.length>this.length)
		  return false;
		if(this.substring(this.length-str.length)==str)
		  return true;
		else
		  return false;
		return true;
	}
</script>
</head>
<div class="page">
	<div class="pageContent">
		<form id="uploadPactTempletFrom" method="post" enctype="multipart/form-data" action="" class="pageForm required-validate"	onsubmit="return iframeCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit"></div>
				<div class="unit">	
					<span style="margin-left:74px">系统允许上传的合同模板最大值为2M</span>	
				</div>
				<!-- <div class="unit">	
					<label>模板名称:</label>
					<input type="text" class="required" name="pactTempletName" value="合同模板-01"/>
				</div> -->
				<div class="unit">	
					<input type="hidden" name="pactDocFileName" id="pactDocFileName"/>
					<input type="file" style="margin-left:74px" name="pactTempletFile" id="pactTempletFile" accept=".docx,.doc"/>			
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li id="pactFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="confirmUploadPactTemplet"><s:text name="上传" /></button>
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





