<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#trainInstitutionForm").attr("action","saveTrainInstitution?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#trainInstitutionForm").submit();
		});
		if("${oper}"=="view"){
			readOnlyForm("trainInstitutionForm");
		}else{
			jQuery("#trainInstitution_code").addClass("required");
			jQuery("#trainInstitution_name").addClass("required");
		}
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="trainInstitutionForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="trainInstitution.id"/>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="trainInstitution_code" key="trainInstitution.code" name="trainInstitution.code"  maxlength="50" readonly="true"/>
				<s:textfield id="trainInstitution_name" key="trainInstitution.name" name="trainInstitution.name"  maxlength="50" oldValue="'${trainInstitution.name}'"
				notrepeat='机构名称已存在' validatorParam="from TrainInstitution ti where ti.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="trainInstitution_code" key="trainInstitution.code" name="trainInstitution.code"  maxlength="50" 
				notrepeat='机构编码已存在' validatorParam="from TrainInstitution ti where ti.code=%value%"/>
				<s:textfield id="trainInstitution_name" key="trainInstitution.name" name="trainInstitution.name"  maxlength="50" 
				notrepeat='机构名称已存在' validatorParam="from TrainInstitution ti where ti.name=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<s:textfield id="trainInstitution_contacts" key="trainInstitution.contacts" name="trainInstitution.contacts" cssClass="" maxlength="20"/>
				<s:textfield id="trainInstitution_contactNumber" key="trainInstitution.contactNumber" name="trainInstitution.contactNumber" cssClass="" maxlength="50"/>
				</div>
				<div class="unit">
						<s:textarea key="trainInstitution.contactAddress" required="false" maxlength="100"
							rows="2" cols="54" cssClass="input-xlarge" />
				</div>
				<div class="unit">
				<s:textfield id="trainInstitution_postCode" key="trainInstitution.postCode" name="trainInstitution.postCode" cssClass="" maxlength="10"/>
				<s:textfield id="trainInstitution_webSite" key="trainInstitution.webSite" name="trainInstitution.webSite" cssClass="" maxlength="50"/>
				</div>
				<div class="unit">
			    <s:textfield id="trainInstitution_email" key="trainInstitution.email" name="trainInstitution.email" cssClass="" maxlength="20"/>
			  	<label><s:text name='trainInstitution.evaluationGrade' />:</label>
						<span
      						 style="float: left; width: 140px"> <s:select
       						 key="trainInstitution.evaluationGrade" cssClass="" style="font-size:9pt;"
       						 maxlength="20" list="evaluationGradeList" listKey="value"
       						 listValue="content" emptyOption="true" theme="simple"></s:select>
      					</span>
				</div>
				<div class="unit">
				<label><s:text name='trainInstitution.disabled' />:</label> <span
     	 				 style="float: left; width: 140px"> <s:checkbox
      					  key="trainInstitution.disabled" required="false" theme="simple" />
    			  </span>
				</div>
				<div class="unit">
						<s:textarea key="trainInstitution.remark" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="trainInstitutionFormSaveButton">
							<div class="buttonContent">
								<button type="button" id="savelink"><s:text name="button.save" /></button>
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





