<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#excelTemplateSavelink').click(function() {
			jQuery("#excelTemplateForm").attr("action","saveExcelTemplate?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}'+"&excelTemplatePath="+"${excelTemplate.path}"+"&fileNewName="+jQuery('#excelTemplate_fileNewName').val());
			jQuery("#excelTemplateForm").submit();
		});
		jQuery('#excelTemplateFileInput').uploadify({
		    debug:true,
		    swf:'${ctx}/dwz/uploadify/scripts/uploadify.swf',
		    uploader:'${ctx}/uploadExcelTemplate',
		    formData:{PHPSESSID:'xxx', ajax:1},
		    buttonText:'请选择文件',
		    fileObjName: 'excelTemplateFile', 
		    fileSizeLimit:0,
		    fileTypeDesc:'*.xls;',
		    fileTypeExts:'*.xls;',
		    queueSizeLimit : 1,
		    height: 20,
            //设置按钮的高度(单位px)，默认为30.(不要在值里写上单位，并且要求一个整数，width也一样)
            width: 80,
		    auto:true,
		    multi:false,
		    wmode: 'transparent' , 
		    rollover: false,
		    queueID:'fileQueueExcelTemplate',
		         'onInit': function () { //载入时触发，将flash设置到最小
//  		        	 jQuery('#fileQueueAttachment').hide();
		               $('#fileQueueExcelTemplate').hide();
		                      },
		    onUploadStart: function(file) {//上传开始时触发（每个文件触发一次）
		    	$('#fileQueueExcelTemplate').show();	
		    },
		    onUploadSuccess: function(file, data, response) {
		                                  var retdata = eval('(' + data + ')');
		                                  if(retdata.statusCode != "200"){
		                                	  alertMsg.error("模版已存在！");
		                                	  $('#fileQueueExcelTemplate').hide();
		                                	  return;
		                                  }
		                                  jQuery('#excelTemplate_path').attr('value',retdata.fileOldPath);
		                                  jQuery('#excelTemplate_templateName').attr('value',retdata.fileOldName);
		                                  jQuery('#excelTemplate_fileNewName').val(retdata.fileNewName);
		                                  if(response){
		                                	  alertMsg.correct("模版上传成功!");
		                                  }
		                                  $('#fileQueueExcelTemplate').hide();
		                             }
		    
		   }); 	
	});
	function excelTemplateFormCallBack(data){
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}else{
			 jQuery("#excelTemplate_type").treeselect({
					dataType : "sql",
					optType : "multi",
					sql : "SELECT type id,type name FROM t_ExcelTemplate group by type",
					exceptnullparent : false,
					lazy : false
				});
		}
	}
</script>
<style type="text/css" media="screen">
.my-uploadify-button {
 background:none;
 border: none;
 text-shadow: none;
 border-radius:0;
}

.uploadify:hover .my-uploadify-button {
 background:none;
 border: none;
}

.fileQueue {
 width: 400px;
 height: 150px;
 overflow: auto;
 border: 1px solid #E5E5E5;
 margin-bottom: 10px;
}
.uploadWrapper {background-color: red;}
</style>
</head>
<div class="page">
	<div class="pageContent">
		<form id="excelTemplateForm" method="post"	action="saveExcelTemplate?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,excelTemplateFormCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="excelTemplate.templateId"/>
				<s:hidden key="excelTemplate.makeDate"/>
				<s:hidden key="excelTemplate.maker.personId"/>
				<s:hidden key="excelTemplate.disabled"/>
				<s:hidden id="excelTemplate_fileNewName"/>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="excelTemplate_templateCode" key="excelTemplate.templateCode" name="excelTemplate.templateCode" cssClass="" maxlength="50" readonly="true"/>
				</s:if>
				<s:else>
				<s:textfield id="excelTemplate_templateCode" key="excelTemplate.templateCode" name="excelTemplate.templateCode" cssClass="required" maxlength="50" onblur="checkId(this,'ExcelTemplate','templateCode','模版编码已存在!')"/>
				</s:else>
				<s:textfield id="excelTemplate_templateName" key="excelTemplate.templateName" name="excelTemplate.templateName" cssClass="required" maxlength="50" onblur="checkId(this,'ExcelTemplate','templateName','模版名称已存在!')"/>
				</div>
				<div class="unit">
				<s:textfield id="excelTemplate_type" key="excelTemplate.type" name="excelTemplate.type" cssClass="required" maxlength="50"/>
<%-- 				<label><s:text name='excelTemplate.disabled' />:</label> <span --%>
<%--        				style="float: left; width: 140px"> <s:checkbox --%>
<%--        				 key="excelTemplate.disabled" required="false" theme="simple" /> --%>
<!--       				</span> -->
				</div>
				<div class="unit">
<%-- 				<s:hidden id="excelTemplate_path" key="excelTemplate.path" name="excelTemplate.path"/> --%>
				<table id="excelTemplate_upload">
				<tr>
				<td><label style="float:left;white-space:nowrap">上传:</label></td>
				<td><input id="excelTemplateFileInput" type="file"/></td>
				<td>&nbsp;&nbsp;<input id="excelTemplate_path"  name="excelTemplate.path" value="${excelTemplate.path}" readonly="readonly" style="width:330px"/></td>
				</tr>
				</table>
				<div id="fileQueueExcelTemplate" class="fileQueue" style="height:60px;width:300px"></div> 
				</div>
				<div class="unit">
						<s:textarea key="excelTemplate.remark" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
				</div>	
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" id="excelTemplateSavelink"><s:text name="button.save" /></button>
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





