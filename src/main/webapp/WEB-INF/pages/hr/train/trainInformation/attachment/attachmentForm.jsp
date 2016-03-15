<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#saveAttachmentFormlink').click(function() {
			jQuery("#attachmentForm").attr("action","saveAttachment?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#attachmentForm").submit();
		});
		if("${oper}"=="view"){
			readOnlyForm("attachmentForm");
			jQuery("#attachmentFile_upload").css("display","none");
			jQuery("#fileQueueAttachment").css("display","none");
		}else{
		jQuery('#attachmentFileInput').uploadify({
		    debug:true,
		    swf:'${ctx}/dwz/uploadify/scripts/uploadify.swf',
		    uploader:'${ctx}/uploadAttachmentFile',
		    formData:{PHPSESSID:'xxx', ajax:1},
		    buttonText:'请选择文件',
		    fileObjName: 'attachmentFile', 
		    fileSizeLimit:0,
		    fileTypeDesc:'*.*;',
		    fileTypeExts:'*.*;',
		    queueSizeLimit : 1,
		    height: 20,
            //设置按钮的高度(单位px)，默认为30.(不要在值里写上单位，并且要求一个整数，width也一样)
            width: 80,
		    auto:true,
		    multi:false,
		    wmode: 'transparent' , 
		    rollover: false,
		    queueID:'fileQueueAttachment',
		         'onInit': function () { //载入时触发，将flash设置到最小
//  		        	 jQuery('#fileQueueAttachment').hide();
		               $('#fileQueueAttachment').hide();
		                      },
		    onUploadStart: function(file) {//上传开始时触发（每个文件触发一次）
		    	$('#fileQueueAttachment').show();	
		    },
		    onUploadSuccess: function(file, data, response) {
		    //console.log(data);		 
		                                 var retdata = eval('(' + data + ')');
		                                  jQuery('#attachment_path').attr('value',retdata.message);
		                                  jQuery('#attachment_name').attr('value',retdata.message);
		                                  if(response){
		                                	  alertMsg.correct("附件上传成功!");
		                                  }
		                                  $('#fileQueueAttachment').hide();
		                             }
		    
		   }); 	
		}
	});
	
	function closeAttachmentFromDialog(){
		if('${entityIsNew}'=="true"){
			$.pdialog.close("addAttachment");
		}else if("${oper}"=="view"){
			$.pdialog.close("viewAttachment");
		}else{
			$.pdialog.close("editAttachment");
		}
		//$.pdialog.closeCurrent();
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
		<form id="attachmentForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="attachment.id"/>
				<s:hidden key="attachment.foreignKey"/>
				<s:hidden key="attachment.makeDate"/>
				<s:hidden key="attachment.maker.personId"/>
				<s:hidden key="attachment.type"/>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="attachment_code" key="attachment.code" name="attachment.code" cssClass="" maxlength="50" readonly="true"/>
				<s:textfield id="attachment_name" key="attachment.name" name="attachment.name" cssClass="required" maxlength="50" oldValue="'${attachment.name}'"
				notrepeat='项附件名称已存在' validatorParam="from Attachment att where att.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="attachment_code" key="attachment.code" name="attachment.code" cssClass="required" maxlength="50" 
				notrepeat='附件编码已存在' validatorParam="from Attachment att where att.code=%value%"/>
				<s:textfield id="attachment_name" key="attachment.name" name="attachment.name" cssClass="required" maxlength="50" 
				notrepeat='项附件名称已存在' validatorParam="from Attachment att where att.name=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<s:hidden id="attachment_path" key="attachment.path" name="attachment.path"/>
				<table id="attachmentFile_upload">
				<tr>
				<td><label style="float:left;white-space:nowrap">上传:</label></td>
				<td><input id="attachmentFileInput" type="file"/></td>
				</tr>
				</table>
				<div id="fileQueueAttachment" class="fileQueue" style="height:60px;width:300px"></div> 
				</div>
				<div class="unit">
						<s:textarea key="attachment.remark" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
				</div>	
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="attachmentFormSaveButton">
							<div class="buttonContent">
								<button type="button" id="saveAttachmentFormlink"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="closeAttachmentFromDialog();"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





