<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#trainEquipmentForm").attr("action","saveTrainEquipment?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#trainEquipmentForm").submit();
		});
		if("${oper}"=="view"){
			readOnlyForm("trainEquipmentForm");
		}else{
			jQuery("#trainEquipment_code").addClass("required");
			jQuery("#trainEquipment_name").addClass("required");
		}
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="trainEquipmentForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="trainEquipment.id"/>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="trainEquipment_code" key="trainEquipment.code" name="trainEquipment.code"  maxlength="50" readonly="true"/>
				<s:textfield id="trainEquipment_name" key="trainEquipment.name" name="trainEquipment.name"  maxlength="50" oldValue="'${trainEquipment.name}'"
				notrepeat='设备名称已存在' validatorParam="from TrainEquipment te where te.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="trainEquipment_code" key="trainEquipment.code" name="trainEquipment.code"  maxlength="50" 
				notrepeat='设备编码已存在' validatorParam="from TrainEquipment te where te.code=%value%"/>
				<s:textfield id="trainEquipment_name" key="trainEquipment.name" name="trainEquipment.name"  maxlength="50" 
				notrepeat='设备名称已存在' validatorParam="from TrainEquipment te where te.name=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<label style="float:left;white-space:nowrap"><s:text name='trainEquipment.acquisitionDate' />:</label><input
        						 name="trainEquipment.acquisitionDate" type="text"
      							  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
       							 value="<fmt:formatDate value="${trainEquipment.acquisitionDate}" pattern="yyyy-MM-dd"/>"
      							  onFocus="WdatePicker({isShowClear:true,readOnly:true})" /> 
      		   <s:textfield id="trainEquipment_acquisitionAmount" key="trainEquipment.acquisitionAmount" name="trainEquipment.acquisitionAmount" cssClass="" maxlength="20"/>
				</div>
				<div class="unit">
				<s:textfield id="trainEquipment_acquisitonExpenses" key="trainEquipment.acquisitonExpenses" name="trainEquipment.acquisitonExpenses" cssClass="number"/>
				<label><s:text name='trainEquipment.category' />:</label>
     			<span
     		       style="float: left; width: 140px"> <s:select
         		    key="trainEquipment.category" style="font-size:9pt;"
         		    maxlength="50" list="categoryList" listKey="value"
         		    listValue="content" emptyOption="false" theme="simple"></s:select>
      		    </span>
				</div>
				<div class="unit">
				<label><s:text name='trainEquipment.state' />:</label>
     			<span
     		       style="float: left; width: 140px"> <s:select
         		    key="trainEquipment.state" style="font-size:9pt;"
         		    maxlength="50" list="stateList" listKey="value"
         		    listValue="content" emptyOption="false" theme="simple"></s:select>
      		    </span>
				</div>
				<div class="unit">
						<s:textarea key="trainEquipment.remark" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
				</div>
				
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="trainEquipmentFormSaveButton">
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





