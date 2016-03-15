<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=="view"){
			readOnlyForm("recruitResumeMessageForm");
			if("${recruitResume.interviewState}"!='1'){
				jQuery("#noticeStateArea").hide();
			}
		}else{
			jQuery("#recruitResume_interviewSpace").addClass("required");
			jQuery("#recruitResume_interviewContacts").addClass("required");
			jQuery("#recruitResume_interviewContactWay").addClass("required");
		}
		jQuery('#saveResumeMessagelink').click(function() {
  			var examinerDate=jQuery("#recruitResume_examinerDate").val();
			jQuery("#recruitResumeMessageForm").attr("action","saveRecruitResume?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}&saveFrom=setInverview"+"&examinerDate="+examinerDate);
			jQuery("#recruitResumeMessageForm").submit();
		});
	});
	
	function changeResumeInterviewState(obj){
		if(obj.value!=1){
			jQuery("#recruitResumeMessageForm").find(".notice").removeClass("required");
			jQuery("#noticeStateArea").hide();
		}else{
			jQuery("#recruitResumeMessageForm").find(".notice").addClass("required");
			jQuery("#noticeStateArea").show();
		}
	}
	
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="recruitResumeMessageForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
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
					<s:select key="recruitResume.interviewState" name="recruitResume.interviewState" onchange="changeResumeInterviewState(this)" list="#{'1':'已通知','2':'联系不上','3':'已有工作','4':'个人放弃'}" style="font-size:9pt;width:100px" ></s:select>
				</div>
				<div id="noticeStateArea">
					<div class="unit">
						<s:textfield id="recruitResume_interviewSpace" key="recruitResume.interviewSpace" name="recruitResume.interviewSpace" maxlength="30" cssClass="required notice"/>
						<label style="float:left;white-space:nowrap"><s:text name='recruitResume.examinerDate' />:</label>
						<input id="recruitResume_examinerDate" name="recruitResume.examinerDate" type="text"  class="Wdate required notice" style="height:15px"
							onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd  HH:mm'})"
							value="<fmt:formatDate value="${recruitResume.examinerDate}" pattern="yyyy-MM-dd HH:mm"/>"
							onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,dateFmt:'yyyy-MM-dd  HH:mm'})" />
					</div>
					<div class="unit">
						<s:textfield id="recruitResume_interviewContacts" key="recruitResume.interviewContacts" name="recruitResume.interviewContacts" cssClass="notice" maxlength="20"/>
						<s:textfield id="recruitResume_interviewContactWay" key="recruitResume.interviewContactWay" name="recruitResume.interviewContactWay" cssClass="notice" maxlength="20"/>
					</div>
					<div class="unit">
						<s:textfield key="recruitResume.professionalExaminer" name="recruitResume.professionalExaminer" cssClass="" maxlength="20"/>
						<s:textfield key="recruitResume.foreignLanguageExaminer" name="recruitResume.foreignLanguageExaminer" cssClass="" maxlength="30"/>
					</div>
				</div>
				<div class="unit">				
			     	 <s:textarea key="recruitResume.remark" required="false" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
				</div>
				<div>
					<s:hidden key="recruitResume.id"/>
					<s:hidden key="recruitResume.sex"/>
					<s:hidden key="recruitResume.favoriteState"/>
					<s:hidden key="recruitResume.favoriteName.personId"/>
					<s:hidden key="recruitResume.favoriteDate"/>
					<s:hidden key="recruitResume.talentPoolName.personId"/>
					<s:hidden key="recruitResume.talentPoolDate"/>
					<s:hidden key="recruitResume.state"/>
					<s:hidden key="recruitResume.examineGrade"/>
					<s:hidden key="recruitResume.examineEvaluate"/>
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
					<s:hidden key="recruitResume.recruitDate"/>
					<s:hidden key="recruitResume.deptSnapCode" />
					<s:hidden key="recruitResume.hrDept.departmentId" />
					<s:hidden key="recruitResume.post.id" />
					<s:hidden key="recruitResume.viewState"/>
					<s:hidden key="recruitResume.age"/>
					<s:hidden key="recruitResume.yearMonth"/>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li id="recruitResumeMessageFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveResumeMessagelink"><s:text name="button.save" /></button>
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





