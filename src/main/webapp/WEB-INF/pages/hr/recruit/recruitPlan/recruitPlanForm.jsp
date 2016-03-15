<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=="view"){
			readOnlyForm("recruitPlanForm");
			jQuery("#recruitPlanaddFromNeedbutton").css("display","none");
		}else{
			jQuery("#recruitNeed_orgCode").addClass("required");
			jQuery("#recruitPlan_post").addClass("required");
			jQuery("#recruitPlan_name").addClass("required");
			jQuery("#recruitPlan_recruitNumber").addClass("required");
		}
		if('${entityIsNew}'!="true"){
			jQuery("#recruitPlanaddFromNeedbutton").css('display','none');
		}
		jQuery('#saveRecruitPlanlink').click(function() {
			jQuery("#recruitPlanForm").attr("action","saveRecruitPlan?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}");
			jQuery("#recruitPlanForm").submit();
		});
		jQuery("#recruitPlan_addFromNeed").click(function(){
			var url = "viewRecruitNeed?popup=true&navTabId=recruitPlan_gridtable";
			var winTitle='选择招聘需求';
			$.pdialog.open(url,'selectRecruitNeed',winTitle, {ifr:true,mask:true,width : 800,height : 600,resizable:false});
			stopPropagation();
		});
		jQuery("#recruitNeed_orgCode").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon FROM v_hr_org_current WHERE disabled = 0",
			exceptnullparent : false,
			lazy : false,
			selectParent:true,
			minWidth:"200px",
			callback : {
				afterClick : function() {
					jQuery("#recruitPlan_post").val("");
					jQuery("#recruitPlan_post_id").val("");
					initRecruitPlanPostTreeSelect();
				}
			}
		});
		adjustFormInput("recruitPlanForm");
	});
	function validateRecruitPlanAge(obj,compareId){
		var value = obj.value;
		var compareValue = jQuery("#"+compareId).val();
		if(compareValue){
			if(compareId.indexOf('Start')>0){
				if(value<compareValue){
					alertMsg.error("最高年龄不能低于最低年龄！");
					obj.value="";
				}
			}else{
				if(value>compareValue){
					alertMsg.error("最低年龄不能高于最高年龄！");
					obj.value="";
				}
			}
		}
	}
	function checkRecruitPlanOrgExists(type){
		var orgCode = jQuery("#recruitNeed_orgCode_id").val();
		if(!orgCode){
			alertMsg.error("请填写单位！");
			return;
		}
		initRecruitPlanPostTreeSelect();
	}
	function initRecruitPlanPostTreeSelect(){
		var orgCode =jQuery("#recruitNeed_orgCode_id").val();
		jQuery("#recruitPlan_post").treeselect({
			dataType:"url",
			optType:"single",
			url:'getPostForOrg?orgCode='+orgCode,
			exceptnullparent:false,
			lazy:false
		});
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="recruitPlanForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="recruitPlan.id"/>
					<s:hidden key="recruitPlan.checkDate"/>
					<s:hidden key="recruitPlan.makeDate"/>
					<s:hidden key="recruitPlan.code"/>
					<s:hidden key="recruitPlan.state"/>
					<s:hidden key="recruitPlan.maker.personId"/>
					<s:hidden key="recruitPlan.checker.personId"/>
					<s:hidden key="recruitPlan.releasedDate"/>
					<s:hidden key="recruitPlan.releaseder.personId"/>
					<s:hidden key="recruitPlan.breakDate"/>
					<s:hidden key="recruitPlan.breaker.personId"/>
					<s:hidden key="recruitPlan.needIds" id="recruitPlan_needIds"/>
					<s:hidden key="recruitPlan.yearMonth"/>
				</div>
				<div class="unit">
					<label><s:text name='recruitPlan.orgCode' />:</label>
					<input type="text" id="recruitNeed_orgCode"  value="${recruitPlan.hrOrg.orgname}"/>
					<input type="hidden" id="recruitNeed_orgCode_id" name="recruitPlan.orgCode" value="${recruitPlan.orgCode}"/>
					<label><s:text name='recruitPlan.post' />:</label>
					<input type="text" id="recruitPlan_post"  value="${recruitPlan.post}" onfocus="checkRecruitPlanOrgExists()"/>
					<input type="hidden" id="recruitPlan_post_id" name="recruitPlan.post" value="${recruitPlan.post}"/>
				</div>
				<div class="unit">
					<s:textfield id="recruitPlan_name" key="recruitPlan.name" name="recruitPlan.name"  maxlength="10"/>
					<s:textfield id="recruitPlan_recruitNumber" key="recruitPlan.recruitNumber" name="recruitPlan.recruitNumber" cssClass="digits"/>
				</div>
				<div class="unit">
					<s:textfield id="recruitPlan_ageStart" key="recruitPlan.ageStart" name="recruitPlan.ageStart" cssClass="digits" onblur="validateRecruitPlanAge(this,'recruitPlan_ageEnd')"/>
					<s:textfield id="recruitPlan_ageEnd" key="recruitPlan.ageEnd" name="recruitPlan.ageEnd" cssClass="digits" onblur="validateRecruitPlanAge(this,'recruitPlan_ageStart')"/>
				</div>
				<div class="unit">
					<s:textfield id="recruitPlan_workExperience" key="recruitPlan.workExperience" name="recruitPlan.workExperience" cssClass="" maxlength="30"/>
					<label><s:text name='recruitPlan.sex' />:</label>
					<span class="formspan" style="float: left; width: 138px"> <s:select id="recruitPlan_sex"
    					   key="recruitPlan.sex" style="font-size:9pt;"
    					   maxlength="20" list="sexList" listKey="value"
    					   listValue="content" emptyOption="false" theme="simple"></s:select>
   				    </span>
				</div>
				<div class="unit">
					<label><s:text name='recruitPlan.educationLevel' />:</label>
					<span class="formspan" style="float:left;width:140px"> <s:select id="recruitPlan_educationLevel"
    					   key="recruitPlan.educationLevel" style="font-size:9pt;"
    					   maxlength="20" list="educationList" listKey="value"
    					   listValue="content" emptyOption="false" theme="simple"></s:select>
   				    </span>
    				<label><s:text name='recruitPlan.profession' />:</label>
					<span class="formspan" style="float: left; width: 140px"> <s:select id="recruitPlan_profession"
    					   key="recruitPlan.profession" style="font-size:9pt;"
    					   maxlength="30" list="professionalList" listKey="value"
    					   listValue="content" emptyOption="false" theme="simple"></s:select>
   				    </span>
				</div>
				<div class="unit">
					<label><s:text name='recruitPlan.maritalStatus' />:</label>
					<span class="formspan" style="float: left; width: 140px"> <s:select id="recruitPlan_maritalStatus"
    					   key="recruitPlan.maritalStatus" style="font-size:9pt;"
    					   maxlength="20" list="maritalStatusList" listKey="value"
    					   listValue="content" emptyOption="false" theme="simple"></s:select>
   				    </span>
    				<label><s:text name='recruitPlan.politicsStatus' />:</label>
					<span class="formspan" style="float: left; width: 140px"> <s:select id="recruitPlan_politicsStatus"
    					   key="recruitPlan.politicsStatus" style="font-size:9pt;"
    					   maxlength="20" list="personPolList" listKey="value"
    					   listValue="content" emptyOption="false" theme="simple"></s:select>
   				    </span>
				</div>
				<div class="unit">
					<label><s:text name='recruitPlan.channel' />:</label>
					<span class="formspan" style="float: left; width: 140px"> <s:select id="recruitPlan_channel"
    					   key="recruitPlan.channel" style="font-size:9pt;"
    					   maxlength="20" list="recruitChannelList" listKey="value"
    					   listValue="content" emptyOption="true" theme="simple"></s:select>
   				    </span>
    				<label><s:text name='recruitPlan.recruitTarget' />:</label>
					<span class="formspan" style="float: left; width: 140px"> <s:select id="recruitPlan_recruitTarget"
    					   key="recruitPlan.recruitTarget" style="font-size:9pt;"
    					   maxlength="40" list="recruitTargetList" listKey="value"
    					   listValue="content" emptyOption="true" theme="simple"></s:select>
   				    </span>
				</div>
				<div class="unit">
					<label style="float:left;white-space:nowrap"><s:text name='recruitPlan.startDate' />:</label>
					<input id="recruitPlan_startDate" name="recruitPlan.startDate" type="text" class="Wdate" style="height:15px"
						onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'recruitPlan_endDate\')}'})"
						value="<fmt:formatDate value="${recruitPlan.startDate}" pattern="yyyy-MM-dd"/>"
						onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'recruitPlan_endDate\')}'})" />	
					<label style="float:left;white-space:nowrap"><s:text name='recruitPlan.endDate' />:</label>
					<input id="recruitPlan_endDate" name="recruitPlan.endDate" type="text" class="Wdate" style="height:15px"
						onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'recruitPlan_startDate\')}'})"
						value="<fmt:formatDate value="${recruitPlan.endDate}" pattern="yyyy-MM-dd"/>"
						onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'recruitPlan_startDate\')}'})" />
				</div>
				<div class="unit">
					<s:textfield id="recruitPlan_workplace" key="recruitPlan.workplace" name="recruitPlan.workplace" cssClass="" maxlength="20"/>
					<label><s:text name='recruitPlan.salaryLevel' />:</label>
					<span class="formspan" style="float: left; width: 152px"> <s:select id="recruitPlan_salaryLevel"
    					   key="recruitPlan.salaryLevel" style="font-size:9pt;"
    					   maxlength="20" list="salaryLevelList" listKey="value"
    					   listValue="content" emptyOption="false" theme="simple"></s:select>
   				    </span>
				</div>
				<div class="unit">
					<label style="float:left;white-space:nowrap"><s:text name='recruitPlan.hotPost' />:</label>
					<span class="formspan" style="float:left;width:140px">
						<s:checkbox key="recruitPlan.hotPost" theme="simple"></s:checkbox>
					</span>
				</div>
				<div class="unit">				
				      <s:textarea id="recruitPlan_postResponsibility" key="recruitPlan.postResponsibility" required="false" maxlength="4000" rows="4" cols="54" cssClass="input-xlarge " />
				</div>
				<div class="unit">				
				      <s:textarea id="recruitPlan_jobRequirements" key="recruitPlan.jobRequirements" required="false" maxlength="4000" rows="4" cols="54" cssClass="input-xlarge " />
				</div>
				<div class="unit">				
				      <s:textarea id="recruitPlan_otherRequirements" key="recruitPlan.otherRequirements" required="false" maxlength="4000" rows="4" cols="54" cssClass="input-xlarge " />
				</div>	
				<div class="unit">				
				      <s:textarea key="recruitPlan.remark" required="false" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
				</div>	
				<s:if test="%{oper=='view'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="recruitPlan.maker"></s:text>:</label>
								<input type="text" value="${recruitPlan.maker.name}"></input>
							</span>
							<span>
								<label><s:text name="recruitPlan.checker"></s:text>:</label>
								<input type="text" value="${recruitPlan.checker.name}"></input>
							</span>
							<span>
								<label><s:text name="recruitPlan.releaseder"></s:text>:</label>
								<input type="text" value="${recruitPlan.releaseder.name}"></input>
							</span>
							<span>
								<label><s:text name="recruitPlan.breaker"></s:text>:</label>
								<input type="text" value="${recruitPlan.breaker.name}"></input>
							</span>
						</div>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li  id="recruitPlanaddFromNeedbutton">
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="recruitPlan_addFromNeed"><s:text name="需求计划引入" /></button>
							</div>
						</div>
					</li>
					<li id="recruitPlanFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveRecruitPlanlink"><s:text name="button.save" /></button>
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





