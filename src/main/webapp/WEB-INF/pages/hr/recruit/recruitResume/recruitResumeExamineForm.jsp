<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=="view"){
			readOnlyForm("recruitResumeExamineForm");
		}else{
			jQuery("#recruitResume_examineGrade").addClass("required");
			jQuery("#recruitResume_examineEvaluate").addClass("required");
		}
		jQuery('#saveResumeExaminelink').click(function() {
			jQuery("#recruitResumeExamineForm").attr("action","saveRecruitResume?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}&saveFrom=examine");
			jQuery("#recruitResumeExamineForm").submit();
		});
	});
	
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="recruitResumeExamineForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:textfield key="recruitResume.code" name="recruitResume.code" cssClass="readonly" readonly="true"/>
					<s:textfield key="recruitResume.name" name="recruitResume.name" cssClass="readonly" readonly="true"/>
				</div>
				<div class="unit">
					<label><s:text name='recruitResume.planOrg' />:</label>
					<input type="text" value="${recruitResume.recruitPlan.hrOrg.orgname}" readonly="readonly"/>
					<s:textfield key="recruitResume.recruitPlan" name="recruitResume.recruitPlan.post" cssClass="readonly" readonly="true"/>
				</div>
				<div class="unit">
					<s:textfield key="recruitResume.contactWay" name="recruitResume.contactWay" cssClass="readonly" readonly="true"/>
					<s:textfield key="recruitResume.email" name="recruitResume.email" cssClass="readonly" readonly="true"/>
				</div>
				<div class="unit">
					<s:textfield id="recruitResume_examineGrade" key="recruitResume.examineGrade" name="recruitResume.examineGrade" cssClass="number"/>
				</div>
				<div class="unit">				
				    <s:textarea id="recruitResume_examineEvaluate" key="recruitResume.examineEvaluate" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
				</div>
				<div class="unit">				
			     	<s:textarea key="recruitResume.remark" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
				</div>
				<div>
					<s:hidden key="recruitResume.id"/>
					<s:hidden key="recruitResume.favoriteState"/>
					<s:hidden key="recruitResume.favoriteName.personId"/>
					<s:hidden key="recruitResume.favoriteDate"/>
					<s:hidden key="recruitResume.sex"/>
					<s:hidden key="recruitResume.talentPoolName.personId"/>
					<s:hidden key="recruitResume.talentPoolDate"/>
					<s:hidden key="recruitResume.state"/>
					<s:hidden key="recruitResume.reportDate"/>
					<s:hidden key="recruitResume.reportContacts"/>
					<s:hidden key="recruitResume.reportContactWay"/>
					<s:hidden key="recruitResume.photo"/>
					<s:hidden key="recruitResume.birthday"/>
					<s:hidden key="recruitResume.workStartDate"/>
					<s:hidden key="recruitResume.domicilePlace"/>
					<s:hidden key="recruitResume.presentResidence"/>
					<s:hidden key="recruitResume.maritalStatus"/>
					<s:hidden key="recruitResume.overseaAssignment"/>
					<s:hidden key="recruitResume.typeOfId"/>
					<s:hidden key="recruitResume.idNumber"/>
					<s:hidden key="recruitResume.nation"/>
					<s:hidden key="recruitResume.politicsStatus"/>
					<s:hidden key="recruitResume.foreignLanguage"/>
					<s:hidden key="recruitResume.foreignLanguageLevel"/>
					<s:hidden key="recruitResume.interests"/>
					<s:hidden key="recruitResume.selfAssessment"/>
					<s:hidden key="recruitResume.recruitPlan.id"/>
					<s:hidden key="recruitResume.expectJobCategory"/>
					<s:hidden key="recruitResume.expectWorkplace"/>
					<s:hidden key="recruitResume.expectOccupation"/>
					<s:hidden key="recruitResume.expectIndustry"/>
					<s:hidden key="recruitResume.expectMonthlyPay"/>
					<s:hidden key="recruitResume.workingCondition"/>
					<s:hidden key="recruitResume.companyNameFirst"/>
					<s:hidden key="recruitResume.industryCategoryFirst"/>
					<s:hidden key="recruitResume.positionCategoryFirst"/>
					<s:hidden key="recruitResume.positionNameFirst"/>
					<s:hidden key="recruitResume.workStartDateFirst"/>
					<s:hidden key="recruitResume.workEndDateFirst"/>
					<s:hidden key="recruitResume.workMonthlyPayFirst"/>
					<s:hidden key="recruitResume.workSpecificationFirst"/>
					<s:hidden key="recruitResume.companyNameSecond"/>
					<s:hidden key="recruitResume.industryCategorySecond"/>
					<s:hidden key="recruitResume.positionCategorySecond"/>
					<s:hidden key="recruitResume.positionNameSecond"/>
					<s:hidden key="recruitResume.workStartDateSecond"/>
					<s:hidden key="recruitResume.workEndDateSecond"/>
					<s:hidden key="recruitResume.workMonthlyPaySecond"/>
					<s:hidden key="recruitResume.workSpecificationSecond"/>
					<s:hidden key="recruitResume.companyNameThird"/>
					<s:hidden key="recruitResume.industryCategoryThird"/>
					<s:hidden key="recruitResume.positionCategoryThird"/>
					<s:hidden key="recruitResume.positionNameThird"/>
					<s:hidden key="recruitResume.workStartDateThird"/>
					<s:hidden key="recruitResume.workEndDateThird"/>
					<s:hidden key="recruitResume.workMonthlyPayThird"/>
					<s:hidden key="recruitResume.workSpecificationThird"/>
					<s:hidden key="recruitResume.eduStartDateFirst"/>
					<s:hidden key="recruitResume.eduEndDateFirst"/>
					<s:hidden key="recruitResume.schoolFirst"/>
					<s:hidden key="recruitResume.professionFirst"/>
					<s:hidden key="recruitResume.eduLevelFirst"/>
					<s:hidden key="recruitResume.eduStartDateSecond"/>
					<s:hidden key="recruitResume.eduEndDateSecond"/>
					<s:hidden key="recruitResume.schoolSecond"/>
					<s:hidden key="recruitResume.professionSecond"/>
					<s:hidden key="recruitResume.eduLevelSecond"/>
					<s:hidden key="recruitResume.eduStartDateThird"/>
					<s:hidden key="recruitResume.eduEndDateThird"/>
					<s:hidden key="recruitResume.schoolThird"/>
					<s:hidden key="recruitResume.professionThird"/>
					<s:hidden key="recruitResume.eduLevelThird"/>
					<s:hidden key="recruitResume.projectExperience"/>
					<s:hidden key="recruitResume.professionalSkill"/>
					<s:hidden key="recruitResume.interviewState"/>
					<s:hidden key="recruitResume.interviewSpace"/>
					<s:hidden key="recruitResume.examinerDate"/>
					<s:hidden key="recruitResume.professionalExaminer"/>
					<s:hidden key="recruitResume.foreignLanguageExaminer"/>
					<s:hidden key="recruitResume.interviewContacts"/>
					<s:hidden key="recruitResume.interviewContactWay"/>
					<s:hidden key="recruitResume.deptSnapCode" />
					<s:hidden key="recruitResume.hrDept.departmentId" />
					<s:hidden key="recruitResume.post.id" />
					<s:hidden key="recruitResume.viewState"/>
					<s:hidden key="recruitResume.age"/>
					<s:hidden key="recruitResume.recruitDate"/>
					<s:hidden key="recruitResume.yearMonth"/>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li id="recruitResumeExamineFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveResumeExaminelink"><s:text name="button.save" /></button>
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





