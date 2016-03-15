<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#trainCategoryForm").attr("action","saveTrainCategory?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#trainCategoryForm").submit();
		});
		if("${oper}"=="view"){
			readOnlyForm("trainCategoryForm");
		}else{
			jQuery("#trainCategory_code").addClass("required");
			jQuery("#trainCategory_name").addClass("required");
		}
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="trainCategoryForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="trainCategory.id"/>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="trainCategory_code" key="trainCategory.code" name="trainCategory.code" readonly="true"/>
				<s:textfield id="trainCategory_name" key="trainCategory.name" name="trainCategory.name" maxlength="50" oldValue="'${trainCategory.name}'"
				notrepeat='项目名称已存在' validatorParam="from TrainCategory tc where tc.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="trainCategory_code" key="trainCategory.code" name="trainCategory.code" maxlength="50" 
				notrepeat='项目编码已存在' validatorParam="from TrainCategory tc where tc.code=%value%"/>
				<s:textfield id="trainCategory_name" key="trainCategory.name" name="trainCategory.name" maxlength="50" 
				notrepeat='项目名称已存在' validatorParam="from TrainCategory tc where tc.name=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<s:textfield id="trainCategory_goal" key="trainCategory.goal" name="trainCategory.goal" cssClass="" maxlength="50"/>
			    <label><s:text name='trainCategory.target' />:</label>
					<span
      					 style="float: left; width: 140px"> <s:select
     					   key="trainCategory.target"  style="font-size:9pt;"
     					   maxlength="50" list="trainTargetList" listKey="value"
     					   listValue="content" emptyOption="false" theme="simple"></s:select>
    				  </span>
				</div>
				<div class="unit">
				<label><s:text name='trainCategory.disabled' />:</label> <span
     				  style="float: left; width: 140px"> <s:checkbox
    				    key="trainCategory.disabled" required="false" theme="simple" />
    		    </span>
				</div>
				<div class="unit">
						<s:textarea key="trainCategory.content" required="false" maxlength="100"
							rows="4" cols="54" cssClass="input-xlarge" />
				</div>
				<div class="unit">
						<s:textarea key="trainCategory.remark" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="trainCategoryFormSaveButton">
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





