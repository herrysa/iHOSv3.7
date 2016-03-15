<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#saveTrainExpenselink').click(function() {
				jQuery("#trainExpenseForm").attr("action","saveTrainExpense?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
				jQuery("#trainExpenseForm").submit();
		});
		if("${oper}"=="view"){
			readOnlyForm("trainExpenseForm");
		}else{
			jQuery("#trainExpense_trainNeed").addClass("required");
			jQuery("#trainExpense_name").addClass("required");
		jQuery("#trainExpense_trainPlan").treeselect({
		   dataType:"sql",
		   optType:"single",
		   sql:"SELECT  id,name FROM hr_train_plan where state='2' ORDER BY code",
		   exceptnullparent:false,
		   lazy:false,
		   callback : {
				afterClick : function() {
					jQuery("#trainExpense_trainNeed").val("");
					jQuery("#trainExpense_trainNeed_id").val("");
					jQuery("#trainExpense_trainNeed").treeselect({
					   dataType:"sql",
					   optType:"single",
					   sql:"SELECT id,name FROM hr_train_class where state='2' and train_plan='"+jQuery("#trainExpense_trainPlan_id").val()+"' ORDER BY code",
					   exceptnullparent:false,
					   lazy:false
					});
				}
			}
		});
		jQuery("#trainExpense_trainNeed").treeselect({
		   dataType:"sql",
		   optType:"single",
		   sql:"SELECT id,name FROM hr_train_class where state='2' ORDER BY code",
		   exceptnullparent:false,
		   lazy:false
		});
		}
	});
	function sumExpense(obj){
		var totalExpense=0;
		if(jQuery("#trainExpense_cateringExpense").val()){
			totalExpense+=parseFloat(jQuery("#trainExpense_cateringExpense").val());
		}
		if(jQuery("#trainExpense_equipmentExpense").val()){
			totalExpense+=parseFloat(jQuery("#trainExpense_equipmentExpense").val());
		}
		if(jQuery("#trainExpense_courseExpense").val()){
			totalExpense+=parseFloat(jQuery("#trainExpense_courseExpense").val());
		}
		if(jQuery("#trainExpense_teachingMaterialExpense").val()){
			totalExpense+=parseFloat(jQuery("#trainExpense_teachingMaterialExpense").val());
		}
		if(jQuery("#trainExpense_internalTrainExpense").val()){
			totalExpense+=parseFloat(jQuery("#trainExpense_internalTrainExpense").val());
		}
		if(jQuery("#trainExpense_externalTrainExpense").val()){
			totalExpense+=parseFloat(jQuery("#trainExpense_externalTrainExpense").val());
		}
		if(jQuery("#trainExpense_onJobStudyExpense").val()){
			totalExpense+=parseFloat(jQuery("#trainExpense_onJobStudyExpense").val());
		}
		if(jQuery("#trainExpense_vocationalCertificateExpense").val()){
			totalExpense+=parseFloat(jQuery("#trainExpense_vocationalCertificateExpense").val());
		}
		if(jQuery("#trainExpense_trainSiteExpense").val()){
			totalExpense+=parseFloat(jQuery("#trainExpense_trainSiteExpense").val());
		}
		if(jQuery("#trainExpense_travelExpense").val()){
			totalExpense+=parseFloat(jQuery("#trainExpense_travelExpense").val());
		}
		if(jQuery("#trainExpense_otherExpense").val()){
			totalExpense+=parseFloat(jQuery("#trainExpense_otherExpense").val());
		}
		jQuery("#trainExpense_totalExpense").val(totalExpense);
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="trainExpenseForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="trainExpense.id"/>
					<s:hidden key="trainExpense.makeDate"/>
					<s:hidden key="trainExpense.maker.personId"/>
					<s:hidden key="trainExpense.code"/>
					<s:hidden key="trainExpense.yearMonth"/>
				</div>
				<div class="unit">
					<s:textfield id="trainExpense_trainNeed" key="trainExpense.trainNeed" name="trainExpense.trainNeed.name"/>
					<s:hidden id="trainExpense_trainNeed_id" key="trainExpense.trainNeed.id"/>
					<label><s:text name='trainExpense.makeDate' />:</label>
				    <input name="trainExpense.makeDate" type="text" class="Wdate" style="height:15px"
					onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
					value="<fmt:formatDate value="${trainExpense.makeDate}" pattern="yyyy-MM-dd"/>"
					onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" /> 
				</div>
				<div class="unit">
				<s:if test="%{entityIsNew}">
				<s:textfield id="trainExpense_name" key="trainExpense.name" name="trainExpense.name" maxlength="20" 
					notrepeat='费用名称已存在' validatorParam="from TrainExpense te where te.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="trainExpense_name" key="trainExpense.name" name="trainExpense.name" maxlength="20" oldValue="'${trainExpense.name}'"
					notrepeat='费用名称已存在' validatorParam="from TrainExpense te where te.name=%value%"/>
				</s:else>
				</div>
				<div class="unit">
					<s:textfield id="trainExpense_cateringExpense" key="trainExpense.cateringExpense" name="trainExpense.cateringExpense" cssClass="number" onblur="sumExpense(this)"/>
					<s:textfield id="trainExpense_equipmentExpense" key="trainExpense.equipmentExpense" name="trainExpense.equipmentExpense" cssClass="number" onblur="sumExpense(this)"/>
				</div>
				<div class="unit">
					<s:textfield id="trainExpense_courseExpense" key="trainExpense.courseExpense" name="trainExpense.courseExpense" cssClass="number" onblur="sumExpense(this)"/>
					<s:textfield id="trainExpense_teachingMaterialExpense" key="trainExpense.teachingMaterialExpense" name="trainExpense.teachingMaterialExpense" cssClass="number" onblur="sumExpense(this)"/>
				</div>
				<div class="unit">
					<s:textfield id="trainExpense_internalTrainExpense" key="trainExpense.internalTrainExpense" name="trainExpense.internalTrainExpense" cssClass="number" onblur="sumExpense(this)"/>
					<s:textfield id="trainExpense_externalTrainExpense" key="trainExpense.externalTrainExpense" name="trainExpense.externalTrainExpense" cssClass="number" onblur="sumExpense(this)"/>
				</div>
				<div class="unit">
					<s:textfield id="trainExpense_onJobStudyExpense" key="trainExpense.onJobStudyExpense" name="trainExpense.onJobStudyExpense" cssClass="number" onblur="sumExpense(this)"/>
					<s:textfield id="trainExpense_vocationalCertificateExpense" key="trainExpense.vocationalCertificateExpense" name="trainExpense.vocationalCertificateExpense" cssClass="number" onblur="sumExpense(this)"/>
				</div>
				<div class="unit">
					<s:textfield id="trainExpense_trainSiteExpense" key="trainExpense.trainSiteExpense" name="trainExpense.trainSiteExpense" cssClass="number" onblur="sumExpense(this)"/>
					<s:textfield id="trainExpense_travelExpense" key="trainExpense.travelExpense" name="trainExpense.travelExpense" cssClass="number" onblur="sumExpense(this)"/>
				</div>
				<div class="unit">
					<s:textfield id="trainExpense_otherExpense" key="trainExpense.otherExpense" name="trainExpense.otherExpense" cssClass="number" onblur="sumExpense(this)"/>
				</div>
				<div class="unit">
					<s:textfield id="trainExpense_totalExpense" key="trainExpense.totalExpense" name="trainExpense.totalExpense" cssClass="readonly" readonly="true"/>
				</div>
				<div class="unit">
					<s:textarea key="trainExpense.remark" required="false" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="trainExpenseFormSaveButton">
							<div class="buttonContent">
								<button type="button" id="saveTrainExpenselink"><s:text name="button.save" /></button>
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





