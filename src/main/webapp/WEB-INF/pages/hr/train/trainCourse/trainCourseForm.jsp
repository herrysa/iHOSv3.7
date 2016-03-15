<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#saveTrainCourselink').click(function() {
			jQuery("#trainCourseForm").attr("action","saveTrainCourse?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#trainCourseForm").submit();
		});
		if("${oper}"=="view"){
			readOnlyForm("trainCourseForm");
		}else{
		jQuery("#trainCourse_trainTeacher").treeselect({
		   dataType:"sql",
		   optType:"single",
		   sql:"SELECT id,name FROM hr_train_teacher where disabled = 0 ORDER BY code",
		   exceptnullparent:false,
		   lazy:false
		});
		jQuery("#trainCourse_trainNeed").treeselect({
		   	dataType:"sql",
		   	optType:"single",
		   	sql:"SELECT id,name FROM hr_train_class where state='2' ORDER BY code",
		   	exceptnullparent:false,
		   	lazy:false,
		   	callback :  {
				afterClick : function() { 
					jQuery.ajax({
			        	url: 'trainNeedGridList?filter_EQS_id='+jQuery("#trainCourse_trainNeed_id").val(),
			            data: {},
			            type: 'post',
			            dataType: 'json',
			            async:false,
			            error: function(data){
			            },
			            success: function(data){
			                if(data.trainNeeds){
			                 var startDate=new Date(data.trainNeeds[0]["startDate"]); 
			                 var endDate=new Date(data.trainNeeds[0]["endDate"]); 
			                 jQuery("#trainCourse_startDate").val(startDate.getFullYear()+"-"+(startDate.getMonth()+1)+"-"+startDate.getDate());
			                 jQuery("#trainCourse_endDate").val(endDate.getFullYear()+"-"+(endDate.getMonth()+1)+"-"+endDate.getDate());
			                }
			            }
			        });
				}
			}
		});
		
		jQuery("#trainCourse_trainInformation").treeselect({
			optType:"multi",
			dataType:'sql',
			sql:"select id,name from hr_train_information where disabled = 0 order by code",
			exceptnullparent:true,
			lazy:false,
			selectParent:false,
			callback:null
		});
		}
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="trainCourseForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="trainCourse.id"/>
					<s:hidden key="trainCourse.code"/>
					<s:hidden key="trainCourse.yearMonth"/>
				</div>
				<div class="unit">
				<s:if test="%{entityIsNew}">
				<s:textfield key="trainCourse.name" name="trainCourse.name" cssClass="required" maxlength="20" 
					notrepeat='课程名称已存在' validatorParam="from TrainCourse tc where tc.name=%value%"/>
				</s:if>
				<s:else>
				<s:textfield key="trainCourse.name" name="trainCourse.name" cssClass="required" maxlength="20" oldValue="'${trainCourse.name}'"
					notrepeat='课程名称已存在' validatorParam="from TrainCourse tc where tc.name=%value%"/>
				</s:else>
					<s:textfield key="trainCourse.expense" name="trainCourse.expense" cssClass="number"/>
				</div>
				<div class="unit">
					<s:textfield id="trainCourse_trainNeed" key="trainCourse.trainNeed" name="trainCourse.trainNeed.name"/>
					<s:textfield id="trainCourse_trainTeacher" key="trainCourse.trainTeacher" name="trainCourse.trainTeacher.name"/>
					<s:hidden id="trainCourse_trainNeed_id" key="trainCourse.trainNeed.id"/>
					<s:hidden id="trainCourse_trainTeacher_id" key="trainCourse.trainTeacher.id"/>
				</div>
				<div class="unit">
					<label><s:text name='trainCourse.startDate' />:</label>
					<input name="trainCourse.startDate" type="text" id="trainCourse_startDate" class="Wdate" style="height:15px"
     					onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'trainCourse_endDate\')}'})"
      					value="<fmt:formatDate value="${trainCourse.startDate}" pattern="yyyy-MM-dd"/>"
     					onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'trainCourse_endDate\')}'})" /> 
      				<label><s:text name='trainCourse.endDate' />:</label>
      				<input name="trainCourse.endDate" type="text" id="trainCourse_endDate" class="Wdate" style="height:15px"
      					onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'trainCourse_startDate\')}'})"
       					value="<fmt:formatDate value="${trainCourse.endDate}" pattern="yyyy-MM-dd"/>"
      					onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'trainCourse_startDate\')}'})" /> 			  
				</div>
				<div class="unit">
					<s:textfield key="trainCourse.hour" name="trainCourse.hour" cssClass="number" maxlength="30"/>
				</div>
				<div class="unit">
					<s:hidden id="trainCourse_trainInformation_id" name="trainCourse.trainInformationIds" value="%{trainCourse.trainInformationIds}"/>
					<label><s:text name="trainCourse.trainInformationNames"></s:text>:</label>
					<span style="float:left;width:140px">
						<s:textarea id="trainCourse_trainInformation" name="trainCourse.trainInformationNames" rows="4" cols="54" cssClass="" value="%{trainCourse.trainInformationNames}" readonly="true" title="培训资料列表"></s:textarea>
					</span>
				</div>
				<div class="unit">
					<s:textarea key="trainCourse.remark" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="trainCourseFormSaveButton">
							<div class="buttonContent">
								<button type="button" id="saveTrainCourselink"><s:text name="button.save" /></button>
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





