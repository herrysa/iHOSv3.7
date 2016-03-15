<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#kqMonthDataForm").attr("action","saveKqMonthData?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#kqMonthDataForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="kqMonthDataForm" method="post"	action="saveKqMonthData?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="kqMonthData.kqId"/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_attendanceDays" key="kqMonthData.attendanceDays" name="kqMonthData.attendanceDays" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_attendanceDept" key="kqMonthData.attendanceDept" name="kqMonthData.attendanceDept" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_checkDate" key="kqMonthData.checkDate" name="kqMonthData.checkDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_checker" key="kqMonthData.checker" name="kqMonthData.checker" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_deptCode" key="kqMonthData.deptCode" name="kqMonthData.deptCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_deptId" key="kqMonthData.deptId" name="kqMonthData.deptId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_deptName" key="kqMonthData.deptName" name="kqMonthData.deptName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_empType" key="kqMonthData.empType" name="kqMonthData.empType" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_kqType" key="kqMonthData.kqType" name="kqMonthData.kqType" cssClass="required 				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_makeDate" key="kqMonthData.makeDate" name="kqMonthData.makeDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_maker" key="kqMonthData.maker" name="kqMonthData.maker" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_orgCode" key="kqMonthData.orgCode" name="kqMonthData.orgCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_orgName" key="kqMonthData.orgName" name="kqMonthData.orgName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_period" key="kqMonthData.period" name="kqMonthData.period" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_personCode" key="kqMonthData.personCode" name="kqMonthData.personCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_personId" key="kqMonthData.personId" name="kqMonthData.personId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_personName" key="kqMonthData.personName" name="kqMonthData.personName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqMonthData_status" key="kqMonthData.status" name="kqMonthData.status" cssClass="				
				
				       "/>
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit"><s:text name="button.save" /></button>
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





