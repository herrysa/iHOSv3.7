<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#saveTrainInformationlink').click(function() {
			jQuery("#trainInformationForm").attr("action","saveTrainInformation?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#trainInformationForm").submit();
		});
		if("${oper}"=="view"){
			readOnlyForm("trainInformationForm");
			if("${trainInformation.id}"){
				  jQuery.ajax({
					  	 url: 'attachmentGridList?filter_INS_foreignKey='+"${trainInformation.id}"+'&filter_EQS_type=trainInformation',
			             data: {},
			             type: 'post',
			             dataType: 'json',
			             async:false,
			             error: function(data){
			             },
			             success: function(data){
			            	 if(data.attachments){
			            		 var innerHtml="";
			            		 var attaListIndex=0;
			            		 for(var attIndex=0;attIndex<data.attachments.length;attIndex++){
			            			 var filePath=data.attachments[attIndex].path;
			            			 var name=data.attachments[attIndex].name;
			            			 if(attIndex>0){
			            				 innerHtml+='<br>';
			            			 }
			   	              	 	 if(filePath){
			   	              	 	attaListIndex++;
			   	              		innerHtml+=attaListIndex+'、<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:downLoadAttachmentBytrainInformationForm(\''+filePath+'\',\''+name+'\');">'+name+'</a>'
			   	              	  	}
			            		 }
			            		 jQuery("#trainInformation_attList")[0].innerHTML=innerHtml;
			            	 }
			             }
			         });
			}
		}else{
			jQuery("#trainInformation_code").addClass("required");
			jQuery("#trainInformation_name").addClass("required");
		}
	});
	function downLoadAttachmentBytrainInformationForm(filePath,fileName){
// 		url=encodeURI(url);
		var filePathArr = filePath.split('.');
		if(filePathArr.length > 0){
			fileName += "." + filePathArr[filePathArr.length-1];
		}
	var fileFullPath=jQuery("#trainInformation_fileFullPath").val();
	fileFullPath+=filePath;
	 jQuery.ajax({
         url: 'attachmentFileExist',
         data: {filePath:fileFullPath},
         type: 'post',
         dataType: 'json',
         async:false,
         error: function(data){
         },
         success: function(data){
         	if(data.type){
         		alertMsg.error(data.type);
    			return;
         	}else{
         		var url = "${ctx}/downLoadFile?filePath="+fileFullPath+"&fileName="+fileName+"&delete=0";
          		location.href=url; 
         	}
         }
     });
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="trainInformationForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<input type="hidden" id="trainInformation_fileFullPath" value='<s:property value="fileFullPath" escapeHtml="false" />'/>
				<s:hidden key="trainInformation.id"/>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="trainInformation_code" key="trainInformation.code" name="trainInformation.code" maxlength="50" readonly="true"/>
				<s:textfield id="trainInformation_name" key="trainInformation.name" name="trainInformation.name" maxlength="20" oldValue="'${trainInformation.name}'"
				notrepeat='资料名称已存在' validatorParam="from TrainInformation ti where ti.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="trainInformation_code" key="trainInformation.code" name="trainInformation.code" maxlength="50" 
				notrepeat='资料编码已存在' validatorParam="from TrainInformation ti where ti.code=%value%"/>
				<s:textfield id="trainInformation_name" key="trainInformation.name" name="trainInformation.name" maxlength="20" 
				notrepeat='资料名称已存在' validatorParam="from TrainInformation ti where ti.name=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<s:textfield id="trainInformation_author" key="trainInformation.author" name="trainInformation.author" cssClass="" maxlength="20"/>
				<s:textfield id="trainInformation_press" key="trainInformation.press" name="trainInformation.press" cssClass="" maxlength="30"/>
				</div>
				<div class="unit">
				<label><s:text name='trainInformation.type' />:</label>
							<span
      							 style="float: left; width: 138px"> <s:select
      							  key="trainInformation.type" cssClass=""
      							  maxlength="20" list="informationTypeList" listKey="value" style="font-size:9pt;"
      							  listValue="content" emptyOption="true" theme="simple"></s:select>
     						 </span>
     			<label><s:text name='trainInformation.informationClass' />:</label>
							<span
      							 style="float: left; width: 140px"> <s:select
      							  key="trainInformation.informationClass" cssClass="" style="font-size:9pt;"
      							  maxlength="20" list="informationClassList" listKey="value"
      							  listValue="content" emptyOption="true" theme="simple"></s:select>
     						 </span>
				</div>
				<div class="unit">
				<s:textfield id="trainInformation_expense" key="trainInformation.expense" name="trainInformation.expense" cssClass="number"/>
				<label><s:text name='trainInformation.disabled' />:</label> <span
       				style="float: left; width: 140px"> <s:checkbox
       				 key="trainInformation.disabled" required="false" theme="simple" />
      				</span>
				</div>
				<div class="unit">
						<s:textarea key="trainInformation.remark" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
				</div>
				<div class="unit">
					<label><s:text name='附件'/>:</label>
					<div style="border:1px solid gray;width:380px;height:60px;float: left" >
					<span id="trainInformation_attList" style="float: left">
					</span>
					</div>
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="trainInformationFormSaveButton">
							<div class="buttonContent">
								<button type="button" id="saveTrainInformationlink"><s:text name="button.save" /></button>
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





