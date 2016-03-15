<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#trainSiteForm").attr("action","saveTrainSite?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#trainSiteForm").submit();
		});
		if("${oper}"=="view"){
			readOnlyForm("trainSiteForm");
		}else{
			jQuery("#trainSite_code").addClass("required");
			jQuery("#trainSite_name").addClass("required");
		}
		adjustFormInput("trainSiteForm");
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="trainSiteForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="trainSite.id"/>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="trainSite_code" key="trainSite.code" name="trainSite.code" maxlength="50" readonly="true"/>
				<s:textfield id="trainSite_name" key="trainSite.name" name="trainSite.name" maxlength="50" oldValue="'${trainSite.name}'"
				notrepeat='场所名称已存在' validatorParam="from TrainSite ts where ts.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="trainSite_code" key="trainSite.code" name="trainSite.code" maxlength="50" 
				notrepeat='场所编码已存在' validatorParam="from TrainSite ts where ts.code=%value%"/>
				<s:textfield id="trainSite_name" key="trainSite.name" name="trainSite.name" maxlength="50" 
				notrepeat='场所名称已存在' validatorParam="from TrainSite ts where ts.name=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<s:textfield id="trainSite_contacts" key="trainSite.contacts" name="trainSite.contacts" cssClass="" maxlength="20"/>
				<s:textfield id="trainSite_contactNumber" key="trainSite.contactNumber" name="trainSite.contactNumber" cssClass="" maxlength="20"/>
				</div>
				<div class="unit">
				<s:textfield id="trainSite_equipment" key="trainSite.equipment" name="trainSite.equipment" cssClass="" maxlength="50"/>
				<s:textfield id="trainSite_address" key="trainSite.address" name="trainSite.address" cssClass="" maxlength="100"/>
				</div>
				<div class="unit">
				<s:textfield id="trainSite_rentCharge" key="trainSite.rentCharge" name="trainSite.rentCharge" cssClass="number"/>
				<s:textfield id="trainSite_scale" key="trainSite.scale" name="trainSite.scale" cssClass="" maxlength="20"/>
				</div>
				<div class="unit">
				<label><s:text name='trainSite.evaluationGrade' />:</label>
							<span class="formspan"
      							 style="float: left; width: 138px"> <s:select
      							  key="trainSite.evaluationGrade" cssClass="" style="font-size:9pt;"
      							  maxlength="20" list="evaluationGradeList" listKey="value"
      							  listValue="content" emptyOption="true" theme="simple"></s:select>
     						 </span>
     			<label><s:text name='trainSite.disabled' />:</label> <span class="formspan"
       					style="float: left; width: 140px"> <s:checkbox
       					 key="trainSite.disabled" required="false" theme="simple" />
   					   </span>
				</div>
				<div class="unit">
						<s:textarea key="trainSite.remark" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="trainSiteFormSaveButton">
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





