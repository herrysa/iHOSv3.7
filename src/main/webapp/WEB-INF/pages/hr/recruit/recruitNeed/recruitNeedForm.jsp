<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=="view"){
			readOnlyForm("recruitNeedForm");
		}else{
			jQuery("#recruitNeed_orgCode").addClass("required");
			jQuery("#recruitNeed_hrDept").addClass("required");
			jQuery("#recruitNeed_post").addClass("required");
			jQuery("#recruitNeed_recruitNumber").addClass("required");
		}
		jQuery('#saveRecuritNeedlink').click(function() {
			jQuery("#recruitNeedForm").attr("action","saveRecruitNeed?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}");
			jQuery("#recruitNeedForm").submit();
		});
		jQuery("#closeRecuritNeedlink").click(function(){
			if("${oper}"=="view"){
				$.pdialog.close('viewRecruitNeed_${recruitNeed.id}');
			}else{
				$.pdialog.closeCurrent();
			}
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
					jQuery("#recruitNeed_hrDept").val("");
					jQuery("#recruitNeed_hrDept_id").val("");
					jQuery("#recruitNeed_post").val("");
					jQuery("#recruitNeed_post_id").val("");
					initRecruitNeedHrDeptTreeSelect();
				}
			}
		});
		adjustFormInput("recruitNeedForm");
	});
	
	
	function validateRecruitAge(obj,compareId){
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
	function checkRecruitNeedOrgExists(type){
		var orgCode = jQuery("#recruitNeed_orgCode_id").val();
		if(!orgCode){
			alertMsg.error("请填写单位！");
			return;
		}
		initRecruitNeedHrDeptTreeSelect();
	}
	
	function checkRecruitNeedDeptExists(){
		var deptId = jQuery("#recruitNeed_hrDept_id").val();
		if(!deptId){
			alertMsg.error("请填写部门！");
			return;
		}
		initRecruitNeedPostTreeSelect();
	}
	function initRecruitNeedHrDeptTreeSelect(){
		var	orgCode = jQuery("#recruitNeed_orgCode_id").val();		
		jQuery("#recruitNeed_hrDept").treeselect({
		   dataType:"sql",
		   optType:"single",
		   sql:"SELECT deptId id,name,parentDept_id parent,internalCode,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon FROM v_hr_department_current p where disabled=0 and orgCode ='"+orgCode+"'  ORDER BY deptCode",
		   exceptnullparent:false,
		   selectParent:true,
		   lazy:false,
		   callback : {
				afterClick : function() {
					jQuery("#recruitNeed_post").val("");
					jQuery("#recruitNeed_post_id").val("");
					initRecruitNeedPostTreeSelect();
				}
			}
		});
	}
	// 新岗位 :是部门 
	function initRecruitNeedPostTreeSelect(){
		var departmentId =jQuery("#recruitNeed_hrDept_id").val();
		jQuery("#recruitNeed_post").treeselect({
			dataType:"url",
			optType:"single",
			url:'getPostForDept?deptId='+departmentId,
			exceptnullparent:false,
			lazy:false
		});
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="recruitNeedForm" method="post" action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="recruitNeed.id"/>
					<s:hidden key="recruitNeed.checkDate"/>
					<s:hidden key="recruitNeed.makeDate"/>
					<s:hidden key="recruitNeed.doneDate"/>
					<s:hidden key="recruitNeed.code"/>
					<s:hidden key="recruitNeed.state"/>
					<s:hidden key="recruitNeed.maker.personId"/>
					<s:hidden key="recruitNeed.checker.personId"/>
					<s:hidden key="recruitNeed.doner.personId"/>
					<s:hidden key="recruitNeed.deptSnapCode" />
					<s:hidden key="recruitNeed.yearMonth"/>
				</div>
				<div class="unit">
					<label><s:text name='recruitNeed.orgCode' />:</label>
					<input type="text" id="recruitNeed_orgCode"  value="${orgName}"/>
					<input type="hidden" id="recruitNeed_orgCode_id" value="${orgCode}"/>
					<label><s:text name='recruitNeed.dept' />:</label>
					<input type="text" id="recruitNeed_hrDept"  value="${recruitNeed.hrDept.name}" onfocus="checkRecruitNeedOrgExists()"/>
					<input type="hidden" id="recruitNeed_hrDept_id" name="recruitNeed.hrDept.departmentId" value="${recruitNeed.hrDept.departmentId}" />
				</div>
				<div class="unit">
					<s:textfield id="recruitNeed_name" key="recruitNeed.name" name="recruitNeed.name" cssClass="required" maxlength="20"/>
					<label><s:text name='recruitNeed.post' />:</label>
      				<input type="text" id="recruitNeed_post"  value="${recruitNeed.post}"  onfocus="checkRecruitNeedDeptExists()"/>
      				<input type="hidden" id="recruitNeed_post_id" name="recruitNeed.post" value="${recruitNeed.post}"/>
				</div>
				<div class="unit">
					<s:textfield id="recruitNeed_recruitNumber" key="recruitNeed.recruitNumber" name="recruitNeed.recruitNumber" cssClass="digits"/>
					<s:textfield key="recruitNeed.workplace" name="recruitNeed.workplace" maxlength="20"/>
				</div>
				<div class="unit">
					<s:textfield key="recruitNeed.workExperience" name="recruitNeed.workExperience" maxlength="30"/>
					<label><s:text name='recruitNeed.sex' />:</label>
					<span class="formspan" style="float: left; width: 140px"> <s:select
    					   key="recruitNeed.sex"  style="font-size:9pt;"
    					   maxlength="20" list="sexList" listKey="value"
    					   listValue="content" emptyOption="false" theme="simple"></s:select>
   				    </span>
				</div>
				<div class="unit">
					<s:textfield id="recruitNeed_ageStart" key="recruitNeed.ageStart" name="recruitNeed.ageStart" cssClass="digits" onblur="validateRecruitAge(this,'recruitNeed_ageEnd')"/>
					<s:textfield id="recruitNeed_ageEnd" key="recruitNeed.ageEnd" name="recruitNeed.ageEnd" cssClass="digits" onblur="validateRecruitAge(this,'recruitNeed_ageStart')"/>
				</div>
				<div class="unit">
					<label><s:text name='recruitNeed.educationLevel' />:</label>
					<span class="formspan" style="float:left;width:140px"> <s:select
    					   key="recruitNeed.educationLevel"  style="font-size:9pt;"
    					   maxlength="20" list="educationList" listKey="value"
    					   listValue="content" emptyOption="false" theme="simple"></s:select>
   				    </span>
    				<label><s:text name='recruitNeed.profession' />:</label>
					<span class="formspan" style="float: left; width: 140px"> <s:select
    					   key="recruitNeed.profession"  style="font-size:9pt;"
    					   maxlength="20" list="professionalList" listKey="value"
    					   listValue="content" emptyOption="false" theme="simple"></s:select>
   				    </span>
				</div>
				<div class="unit">
					<label><s:text name='recruitNeed.maritalStatus' />:</label>
					<span class="formspan" style="float: left; width: 140px"> <s:select
    					   key="recruitNeed.maritalStatus"  style="font-size:9pt;"
    					   maxlength="20" list="maritalStatusList" listKey="value"
    					   listValue="content" emptyOption="false" theme="simple"></s:select>
   				    </span>
    				<label><s:text name='recruitNeed.politicsStatus' />:</label>
					<span class="formspan" style="float: left; width: 140px"> <s:select
    					   key="recruitNeed.politicsStatus"   style="font-size:9pt;"
    					   maxlength="20" list="personPolList" listKey="value"
    					   listValue="content" emptyOption="false" theme="simple"></s:select>
   				    </span>
				</div>
				<div class="unit">
					<label style="float:left;white-space:nowrap"><s:text name='recruitNeed.startDate' />:</label>
					<input id="recruitNeed_startDate" name="recruitNeed.startDate" type="text" class="Wdate" style="height:15px"
						onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'recruitNeed_endDate\')}'})"
						value="<fmt:formatDate value="${recruitNeed.startDate}" pattern="yyyy-MM-dd"/>"
						onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'recruitNeed_endDate\')}'})" />	
					<label style="float:left;white-space:nowrap"><s:text name='recruitNeed.endDate' />:</label>
					<input id="recruitNeed_endDate" name="recruitNeed.endDate" type="text" class="Wdate" style="height:15px"
						onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'recruitNeed_startDate\')}'})"
						value="<fmt:formatDate value="${recruitNeed.endDate}" pattern="yyyy-MM-dd"/>"
						onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'recruitNeed_startDate\')}'})" />	
				</div>
				<div class="unit">
					<s:textarea key="recruitNeed.postResponsibility" required="false" maxlength="4000" rows="4" cols="54" cssClass="input-xlarge" />
				</div>
				<div class="unit">				
				      <s:textarea key="recruitNeed.jobRequirements" required="false" maxlength="4000" rows="4" cols="54" cssClass="input-xlarge" />
				</div>	
				<div class="unit">
					<s:textarea key="recruitNeed.otherRequirements" required="false" maxlength="4000" rows="4" cols="54" cssClass="input-xlarge" />
				</div>
				<div class="unit">				
				      <s:textarea key="recruitNeed.remark" required="false" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
				</div>	
				<s:if test="%{oper=='view'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="recruitNeed.maker"></s:text>:</label>
								<input type="text" value="${recruitNeed.maker.name}"></input>
							</span>
							<span>
								<label><s:text name="recruitNeed.checker"></s:text>:</label>
								<input type="text" value="${recruitNeed.checker.name}"></input>
							</span>
							<span>
								<label><s:text name="recruitNeed.doner"></s:text>:</label>
								<input type="text" value="${recruitNeed.doner.name}"></input>
							</span>
						</div>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li id="recruitNeedFormSavebutton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveRecuritNeedlink"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" id="closeRecuritNeedlink" ><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





