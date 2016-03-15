<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script language="javascript" type="text/javascript"
	src="${ctx}/scripts/DatePicker/WdatePicker.js"></script>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#trainTeacherForm").attr("action","saveTrainTeacher?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#trainTeacherForm").submit();
		});
		if("${oper}"=="view"){
			readOnlyForm("trainTeacherForm");
		}else{
			jQuery("#trainTeacher_code").addClass("required");
			jQuery("#trainTeacher_name").addClass("required");
		}
		adjustFormInput("trainTeacherForm");
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="trainTeacherForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="trainTeacher.id"/>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="trainTeacher_code" key="trainTeacher.code" name="trainTeacher.code" maxlength="50" readonly="true"/>
				<s:textfield id="trainTeacher_name" key="trainTeacher.name" name="trainTeacher.name" maxlength="20" oldValue="'${trainTeacher.name}'"
				notrepeat='教师名称已存在' validatorParam="from TrainTeacher tt where tt.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="trainTeacher_code" key="trainTeacher.code" name="trainTeacher.code" maxlength="50" 
				notrepeat='教师编码已存在' validatorParam="from TrainTeacher tt where tt.code=%value%"/>
				<s:textfield id="trainTeacher_name" key="trainTeacher.name" name="trainTeacher.name" maxlength="20" 
				notrepeat='教师名称已存在' validatorParam="from TrainTeacher tt where tt.name=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<label><s:text name='trainTeacher.sex' />:</label>
							<span class="formspan"
      							 style="float: left; width: 138px"> <s:select
      							  key="trainTeacher.sex" cssClass="" style="font-size:9pt;"
      							  maxlength="2" list="sexList" listKey="value"
      							  listValue="content" emptyOption="true" theme="simple"></s:select>
     						 </span>
				<label style="float:left;white-space:nowrap"><s:text name='trainTeacher.birthDay' />:</label><input
        						 name="trainTeacher.birthDay" type="text" 
      							  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
       							 value="<fmt:formatDate value="${trainTeacher.birthDay}" pattern="yyyy-MM-dd"/>"
      							  onFocus="WdatePicker({isShowClear:true,readOnly:true})" /> 
				</div>
				<div class="unit">
				<s:textfield id="trainTeacher_phone" key="trainTeacher.phone" name="trainTeacher.phone" cssClass="" maxlength="20"/>
			    <s:textfield id="trainTeacher_email" key="trainTeacher.email" name="trainTeacher.email" cssClass="" maxlength="20"/>
				</div>
				<div class="unit">
				<label><s:text name='trainTeacher.category' />:</label>
							<span class="formspan"
      							 style="float: left; width: 138px"> <s:select
      							  key="trainTeacher.category" cssClass="" style="font-size:9pt;"
      							  maxlength="20" list="teacherCategoryList" listKey="value"
      							  listValue="content" emptyOption="true" theme="simple"></s:select>
     						 </span>
				<label><s:text name='trainTeacher.educationLevel' />:</label>
							<span class="formspan"
      							 style="float: left; width: 140px"> <s:select
      							  key="trainTeacher.educationLevel" cssClass="" style="font-size:9pt;"
      							  maxlength="20" list="educationList" listKey="value"
      							  listValue="content" emptyOption="true" theme="simple"></s:select>
     						 </span>
				</div>
				<div class="unit">
				<label><s:text name='trainTeacher.profession' />:</label>
							<span class="formspan"
      							 style="float: left; width: 138px"> <s:select
      							  key="trainTeacher.profession" cssClass="" style="font-size:9pt;"
      							  maxlength="20" list="professionalList" listKey="value"
      							  listValue="content" emptyOption="true" theme="simple"></s:select>
     						 </span>
				<s:textfield id="trainTeacher_school" key="trainTeacher.school" name="trainTeacher.school" cssClass="" maxlength="30"/>
				</div>
				<div class="unit">
				<s:textfield id="trainTeacher_courses" key="trainTeacher.courses" name="trainTeacher.courses" cssClass="" maxlength="50"/>
				<s:textfield id="trainTeacher_workUnit" key="trainTeacher.workUnit" name="trainTeacher.workUnit" cssClass="" maxlength="50"/>
				</div>
				<div class="unit">
				<label><s:text name='trainTeacher.disabled' />:</label> <span
      					 style="float: left; width: 140px"> <s:checkbox
      					  key="trainTeacher.disabled" required="false" theme="simple" />
    				  </span>
				</div>
				<div class="unit">
						<s:textarea key="trainTeacher.remark" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="trainTeacherFormSaveButton">
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





