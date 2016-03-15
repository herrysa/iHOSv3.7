<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=="view"){
			readOnlyForm("trainRequirementForm");
		}else{
			jQuery("#trainRequirement_orgCode").addClass("required");
			jQuery("#trainRequirement_hrDept").addClass("required");
			jQuery("#trainRequirement_name").addClass("required");
			jQuery("#trainRequirement_trainCategory").addClass("required");
			jQuery("#trainRequirement_trainCategory").treeselect({
				dataType:"sql",
				optType:"single",
				sql:"SELECT id,name FROM hr_train_category where disabled = 0 ORDER BY code",
				exceptnullparent:false,
				lazy:false,
				callback : {
				 afterClick : function() { 
					 jQuery.ajax({
				        url: 'trainCategoryGridList?filter_EQS_id='+jQuery("#trainRequirement_trainCategory_id").val(),
				        data: {},
				        type: 'post',
				        dataType: 'json',
				        async:false,
				        error: function(data){
				        },
				        success: function(data){ 
				     	   if(data.trainCategories){
				     		   jQuery("#trainRequirement_goal").val(data.trainCategories[0]["goal"]);
				     		   jQuery("#trainRequirement_content").val(data.trainCategories[0]["content"]);
				     	   }
				     	 }
				    });
				 }
				}
			});
			
			jQuery("#trainRequirement_orgCode").treeselect({
				dataType : "sql",
				optType : "single",
				sql : "SELECT orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon FROM v_hr_org_current WHERE disabled = 0",
				exceptnullparent : false,
				lazy : false,
				selectParent:true,
				minWidth:"200px",
				callback : {
					afterClick : function() {
						jQuery("#trainRequirement_hrDept").val("");
						jQuery("#trainRequirement_hrDept_id").val("");
						jQuery("#trainRequirement_person").val("");
						jQuery("#trainRequirement_person_id").val("");
						initTrainRequirementHrDeptTreeSelect();
					}
				}
			});
		}
		jQuery('#saveRrainRequirementlink').click(function() {
			jQuery("#trainRequirementForm").attr("action","saveTrainRequirement?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}");
			jQuery("#trainRequirementForm").submit();
		});
	});
	function checkTrainRequirementOrgExists(type){
		var orgCode = jQuery("#trainRequirement_orgCode_id").val();
		if(!orgCode){
			alertMsg.error("请填写单位！");
			return;
		}
		initTrainRequirementHrDeptTreeSelect();
	}
	
	function checkTrainRequirementDeptExists(){
		var deptId = jQuery("#trainRequirement_hrDept_id").val();
		if(!deptId){
			alertMsg.error("请填写部门！");
			return;
		}
		initTrainRequirementPersonTreeSelect();
	}
	function initTrainRequirementHrDeptTreeSelect(){
		var	orgCode = jQuery("#trainRequirement_orgCode_id").val();		
		jQuery("#trainRequirement_hrDept").treeselect({
		   dataType:"sql",
		   optType:"single",
		   sql:"SELECT deptId id,name,parentDept_id parent,internalCode,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon FROM v_hr_department_current p where disabled=0 and orgCode ='"+orgCode+"'  ORDER BY deptCode",
		   exceptnullparent:false,
		   selectParent:true,
		   lazy:false,
		   callback : {
				afterClick : function() {
					jQuery("#trainRequirement_person").val("");
					jQuery("#trainRequirement_person_id").val("");
					initTrainRequirementPersonTreeSelect();
				}
			}
		});
	}
	function initTrainRequirementPersonTreeSelect(){
		var departmentId =jQuery("#trainRequirement_hrDept_id").val();
		jQuery("#trainRequirement_person").treeselect({
			optType:"multi",
			dataType:'url',
			url:'getHrPersonForDept?deptId='+departmentId,
			exceptnullparent:true,
			lazy:false
		});
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="trainRequirementForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="trainRequirement.id"/>
					<s:hidden key="trainRequirement.checkDate"/>
					<s:hidden key="trainRequirement.checker.personId"/>
					<s:hidden key="trainRequirement.makeDate"/>
					<s:hidden key="trainRequirement.maker.personId"/>
					<s:hidden key="trainRequirement.peopleNumber"/>
					<s:hidden key="trainRequirement.state"/>
					<s:hidden key="trainRequirement.code"/>
					<s:hidden key="trainRequirement.periodMonth"/>
					<s:hidden key="trainRequirement.personSnapCode" />
					<s:hidden key="trainRequirement.yearMonth"/>
				</div>
				<div class="unit">
					<label><s:text name='trainRequirement.orgCode' />:</label>
					<input type="text" id="trainRequirement_orgCode"  value="${orgName}"/>
					<input type="hidden" id="trainRequirement_orgCode_id" value="${orgCode}"/>
					<label><s:text name='trainRequirement.dept' />:</label>
					<input type="text" id="trainRequirement_hrDept"  value="${trainRequirement.hrDept.name}" onfocus="checkTrainRequirementOrgExists()"/>
					<input type="hidden" id="trainRequirement_hrDept_id" name="trainRequirement.hrDept.departmentId" value="${trainRequirement.hrDept.departmentId}" />
				</div>
				<div class="unit">
				<s:if test="%{entityIsNew}">
				<s:textfield id="trainRequirement_name" key="trainRequirement.name" name="trainRequirement.name" maxlength="20" 
					notrepeat='需求名称已存在' validatorParam="from TrainRequirement tr where tr.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="trainRequirement_name" key="trainRequirement.name" name="trainRequirement.name" maxlength="20" oldValue="'${trainRequirement.name}'"
					notrepeat='需求名称已存在' validatorParam="from TrainRequirement tr where tr.name=%value%"/>
				</s:else>
				    <label><s:text name='trainRequirement.trainCategory'/>:</label>
      				<input type="hidden" id="trainRequirement_trainCategory_id" name="trainRequirement.trainCategory.id" value="${trainRequirement.trainCategory.id}"/>
      				<input type="text" id="trainRequirement_trainCategory" name="trainRequirement.trainCategory.name" value="${trainRequirement.trainCategory.name}"/>
				</div>
				<div class="unit">
					<s:textfield id="trainRequirement_goal" key="trainRequirement.goal" name="trainRequirement.goal" maxlength="30"/>
				</div>
				<div class="unit">
					<s:textarea key="trainRequirement.content" maxlength="200" id="trainRequirement_content"
						rows="4" cols="54" cssClass="input-xlarge" />
				</div>
				<div class="unit">
					<s:hidden id="trainRequirement_person_id" maxlength="8000"   name="trainRequirement.personIds" value="%{trainRequirement.personIds}"/>
					<label><s:text name="trainRequirement.personNames"></s:text>:</label>
					<s:textarea id="trainRequirement_person" name="trainRequirement.personNames" maxlength="8000" rows="4" cols="54" value="%{trainRequirement.personNames}" readonly="true" title="培训人员列表" onfocus="checkTrainRequirementDeptExists()"></s:textarea>
				</div>
				
				<div class="unit">
					<s:textarea key="trainRequirement.remark" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
				</div>
				
				<s:if test="%{oper=='view'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="trainRequirement.maker"></s:text>:</label>
								<input type="text" value="${trainRequirement.maker.name}"></input>
							</span>
							<span>
								<label><s:text name="trainRequirement.checker"></s:text>:</label>
								<input type="text" value="${trainRequirement.checker.name}"></input>
							</span>
						</div>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li id="trainRequirementFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveRrainRequirementlink"><s:text name="button.save" /></button>
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





