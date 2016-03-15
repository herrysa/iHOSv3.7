<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#saveTrainPlanlink').click(function() {
			var value=jQuery("#trainPlan_type").val();
			if(value=="季度计划"){
				if(!jQuery("#trainPlan_quarter").val()){
					alertMsg.error("计划季度为必填项。");
					return;
				}
			}else if(value=="月计划"){
				if(!jQuery("#trainPlan_quarter").val()){
					alertMsg.error("计划季度为必填项。");
					return;
				}
				if(!jQuery("#trainPlan_month").val()){
					alertMsg.error("计划月份为必填项。");
					return;
				}
			}
			jQuery("#trainPlanForm").attr("action","saveTrainPlan?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}");
			jQuery("#trainPlanForm").submit();
		});
		typeSelected(jQuery("#trainPlan_type").val());
		if("${oper}"=="view"){
			readOnlyForm("trainPlanForm");
		}else{
			jQuery("#trainPlan_name").addClass("required");
			jQuery("#trainPlan_year").addClass("required");
			jQuery("#trainPlan_trainCategory").treeselect({
				dataType:"sql",
				optType:"single",
				sql:"SELECT id,name FROM hr_train_category where disabled = 0  ORDER BY code",
				exceptnullparent:false,
				lazy:false,
				callback : {
			        afterClick : function() { 
			        	jQuery.ajax({
			            	url: 'trainCategoryGridList?filter_EQS_id='+jQuery("#trainPlan_trainCategory_id").val(),
			               	data: {},
			               	type: 'post',
			               	dataType: 'json',
			               	async:false,
			               	error: function(data){
			               	},
			               	success: function(data){ 
			     				if(data.trainCategories){
			                 		jQuery("#trainPlan_goal").val(data.trainCategories[0]["goal"]);
			                 		jQuery("#trainPlan_content").val(data.trainCategories[0]["content"]);
			                 		jQuery("#trainPlan_target").val(data.trainCategories[0]["target"]);
			                	}
			              	}
			           });
			        }
				}
			});
			
			var sql="select personId id,name,dept_id parent,0 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/person.png' icon,personCode orderCol from v_hr_person_current where disabled=0 and dept_id in (select deptId from v_hr_department_current where disabled=0 and orgCode in (select orgCode from v_hr_org_current where disabled=0)) ";
				sql=sql+" union select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from v_hr_department_current where disabled=0 and orgCode in (select orgCode from v_hr_org_current where disabled=0) "
				sql=sql+" union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from v_hr_org_current where disabled=0 ";
				sql += " ORDER BY orderCol ";
			jQuery("#trainPlan_person").treeselect({
				optType:"multi",
				dataType:'sql',
				sql:sql,
				exceptnullparent:true,
				lazy:false,
				selectParent:false
			});
		}
		adjustFormInput("trainPlanForm");
	});
	function typeSelected(value){
		if(value=="年度计划"){
			jQuery("#trainPlan_quarter_month").hide();
			jQuery("#trainPlan_quarter").val("");
			jQuery("#trainPlan_month").val("");
			jQuery("#trainPlan_tempPlanDate").hide();
			jQuery("#trainPlan_year_display").show();
			jQuery("#trainPlan_year").addClass('required');
			jQuery("#trainPlan_tempPlanStartDate").removeClass('required');
			jQuery("#trainPlan_tempPlanEndDate").removeClass('required');
			jQuery("#trainPlan_tempPlanStartDate").val("");
			jQuery("#trainPlan_tempPlanEndDate").val("");
		}else if(value=="季度计划"){
			jQuery("#trainPlan_quarter_month").show();
			jQuery("#trainPlan_quarter_display").show();
			jQuery("#trainPlan_month_display").hide();
			jQuery("#trainPlan_month").val("");
			jQuery("#trainPlan_tempPlanDate").hide();
			jQuery("#trainPlan_year_display").show();
			jQuery("#trainPlan_year").addClass('required');
			jQuery("#trainPlan_tempPlanStartDate").removeClass('required');
			jQuery("#trainPlan_tempPlanEndDate").removeClass('required');
			jQuery("#trainPlan_tempPlanStartDate").val("");
			jQuery("#trainPlan_tempPlanEndDate").val("");
		}else if(value=="月计划"){
			jQuery("#trainPlan_quarter_month").show();
			jQuery("#trainPlan_quarter_display").show();
			jQuery("#trainPlan_month_display").show();
			quarterSelected(jQuery("#trainPlan_quarter").val());
			jQuery("#trainPlan_tempPlanDate").hide();
			jQuery("#trainPlan_year_display").show();
			jQuery("#trainPlan_year").addClass('required');
			jQuery("#trainPlan_tempPlanStartDate").removeClass('required');
			jQuery("#trainPlan_tempPlanEndDate").removeClass('required');
			jQuery("#trainPlan_tempPlanStartDate").val("");
			jQuery("#trainPlan_tempPlanEndDate").val("");
		}else{
			jQuery("#trainPlan_quarter_month").hide();
			jQuery("#trainPlan_year_display").hide();
			jQuery("#trainPlan_year").removeClass('required');
			jQuery("#trainPlan_tempPlanStartDate").addClass('required');
			jQuery("#trainPlan_tempPlanEndDate").addClass('required');
			jQuery("#trainPlan_quarter").val("");
			jQuery("#trainPlan_month").val("");
			jQuery("#trainPlan_year").val("");
			jQuery("#trainPlan_tempPlanDate").show();
		}
	}
	function quarterSelected(value){
		var month = jQuery("#trainPlan_month").val();
		jQuery("#trainPlan_month").empty();
		if(jQuery("#trainPlan_type").val()=="月计划"){
			if(value=="第一季度"){
				jQuery("#trainPlan_month").append("<option value=''>--</option>");
				jQuery("#trainPlan_month").append("<option value='一月'>一月</option>");
				jQuery("#trainPlan_month").append("<option value='二月'>二月</option>");
				jQuery("#trainPlan_month").append("<option value='三月'>三月</option>");
			}else if(value=="第二季度"){
				jQuery("#trainPlan_month").append("<option value=''>--</option>");
				jQuery("#trainPlan_month").append("<option value='四月'>四月</option>");
				jQuery("#trainPlan_month").append("<option value='五月'>五月</option>");
				jQuery("#trainPlan_month").append("<option value='六月'>六月</option>");
			}else if(value=="第三季度"){
				jQuery("#trainPlan_month").append("<option value=''>--</option>");
				jQuery("#trainPlan_month").append("<option value='七月'>七月</option>");
				jQuery("#trainPlan_month").append("<option value='八月'>八月</option>");
				jQuery("#trainPlan_month").append("<option value='九月'>九月</option>");
			}else if(value=="第四季度"){
				jQuery("#trainPlan_month").append("<option value=''>--</option>");
				jQuery("#trainPlan_month").append("<option value='十月'>十月</option>");
				jQuery("#trainPlan_month").append("<option value='十一月'>十一月</option>");
				jQuery("#trainPlan_month").append("<option value='十二月'>十二月</option>");
			}else{
				jQuery("#trainPlan_month").val("");
			}
			if(month && "${entityIsNew}" == "false") {
				jQuery("#trainPlan_month").val(month);
			}
		}else{
			jQuery("#trainPlan_month").val("");
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="trainPlanForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="trainPlan.id"/>
					<s:hidden key="trainPlan.approvalOpinion"/>
					<s:hidden key="trainPlan.checkDate"/>
					<s:hidden key="trainPlan.checker.personId"/>
					<s:hidden key="trainPlan.makeDate"/>
					<s:hidden key="trainPlan.maker.personId"/>
					<s:hidden key="trainPlan.state"/>
					<s:hidden key="trainPlan.peopleNumber"/>
					<s:hidden key="trainPlan.code"/>
					<s:hidden key="trainPlan.yearMonth"/>
				</div>
				<div class="unit">
				<s:if test="%{entityIsNew}">
				<s:textfield id="trainPlan_name" key="trainPlan.name" name="trainPlan.name" maxlength="20" 
					notrepeat='计划名称已存在' validatorParam="from TrainPlan tp where tp.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="trainPlan_name" key="trainPlan.name" name="trainPlan.name" maxlength="20" oldValue="'${trainPlan.name}'"
					notrepeat='计划名称已存在' validatorParam="from TrainPlan tp where tp.name=%value%"/>
				</s:else>
					<s:textfield key="trainPlan.budgetAmount" name="trainPlan.budgetAmount" cssClass="number"/>
				</div>
				<div class="unit">
					<label><s:text name='trainPlan.type' />:</label>
					<span class="formspan" style="float: left; width: 152px"> 
					<s:select id="trainPlan_type" key="trainPlan.type" onchange="typeSelected(this.value)"
     					maxlength="20" list="typeList" listKey="value" style="font-size:9pt;"
      					listValue="content" emptyOption="false" theme="simple"></s:select>
   					</span>
   					<div id="trainPlan_year_display">
   					<label><s:text name='trainPlan.year'/>:</label>
   					<input id="trainPlan_year" name="trainPlan.year" id="trainPlan_year" type="text" onClick="WdatePicker({skin:'ext',dateFmt:'yyyy'})" value="${trainPlan.year}"/>
   					</div>
				</div>
				<div class="unit" id="trainPlan_quarter_month" style="display:none;">
					<div id="trainPlan_quarter_display" style="display:inline;">
						<label><s:text name='trainPlan.quarter' />:</label>
						<span class="formspan" style="float: left; width: 152px"> 
						<s:select id="trainPlan_quarter" key="trainPlan.quarter" onchange="quarterSelected(this.value)"
		     				maxlength="20" list="quarterList" listKey="value" style="font-size:9pt;" headerKey="" headerValue="--" 
		      				listValue="content" emptyOption="false" theme="simple" ></s:select>
		   				</span>
					</div>
					<div id="trainPlan_month_display" style="display:inline;">
						<label><s:text name='trainPlan.month' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
						<s:select id="trainPlan_month" key="trainPlan.month"
     					   	maxlength="20" list="monthList" listKey="value" style="font-size:9pt;" headerKey="" headerValue="--" 
      						listValue="content" emptyOption="false" theme="simple"></s:select>
   						</span>
   					</div>
   				</div>
   				<div class="unit" id="trainPlan_tempPlanDate" style="display:none;">
   					<label><s:text name='trainPlan.tempPlanStartDate' />:</label>
					<input name="trainPlan.tempPlanStartDate" type="text" id="trainPlan_tempPlanStartDate" class="Wdate" style="height:15px"
      					onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'trainPlan_tempPlanEndDate\')}'})" class=""
       					value="<fmt:formatDate value="${trainPlan.tempPlanStartDate}" pattern="yyyy-MM-dd"/>"
      					onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'trainPlan_tempPlanEndDate\')}'})" /> 
      				<label><s:text name='trainPlan.tempPlanEndDate' />:</label>
      				<input name="trainPlan.tempPlanEndDate" type="text" class="Wdate" style="height:15px" id="trainPlan_tempPlanEndDate"
      					onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'trainPlan_tempPlanStartDate\')}'})"  
       					value="<fmt:formatDate value="${trainPlan.tempPlanStartDate}" pattern="yyyy-MM-dd"/>"
      					onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'trainPlan_tempPlanStartDate\')}'})" /> 
   				</div>
				<div class="unit">
					<label><s:text name='trainPlan.trainCategory'/>:</label>
					<span class="formspan" style="float: left; width: 152px">
      					<input type="hidden" id="trainPlan_trainCategory_id" name="trainPlan.trainCategory.id" value="${trainPlan.trainCategory.id}"/>
      					<input type="text" id="trainPlan_trainCategory" name="trainPlan.trainCategory.name" value="${trainPlan.trainCategory.name}" class="required"/> 
      				</span> 
      				<label><s:text name='trainPlan.target' />:</label>
					<span class="formspan" style="float: left; width: 140px"> 
						<s:select key="trainPlan.target" style="font-size:9pt;"
     					   maxlength="50" list="trainTargetList" listKey="value"
     					   listValue="content" emptyOption="false" theme="simple"></s:select>
    				</span>						 
				</div>
				<div class="unit">
					<s:textfield id="trainPlan_goal" key="trainPlan.goal" name="trainPlan.goal" maxlength="30"/>
				</div>
				<div class="unit">
					<s:textarea id="trainPlan_content" key="trainPlan.content" maxlength="200" rows="4" cols="54" cssClass="input-xlarge"/>
				</div>
				<div class="unit">
					<s:hidden id="trainPlan_person_id" name="trainPlan.personIds" value="%{trainPlan.personIds}"/>
					<label><s:text name="trainPlan.personNames"></s:text>:</label>
					<span style="float:left;width:140px">
						<s:textarea id="trainPlan_person" name="trainPlan.personNames" maxlength="8000" rows="4" cols="54" value="%{trainPlan.personNames}" readonly="true" title="培训人员列表"></s:textarea>
					</span>
				</div>
				<div class="unit">
					<s:textarea key="trainPlan.remark" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
				</div>
				<s:if test="%{oper=='view'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="trainPlan.maker"></s:text>:</label>
								<input type="text" value="${trainPlan.maker.name}"></input>
							</span>
							<span>
								<label><s:text name="trainPlan.checker"></s:text>:</label>
								<input type="text" value="${trainPlan.checker.name}"></input>
							</span>
						</div>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li id="trainPlanFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveTrainPlanlink"><s:text name="button.save" /></button>
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





