<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=="view"){
			readOnlyForm("${random}_pactRenewForm");
		}else{
			jQuery("#pactRenew_renewYear").addClass("required");
		}
		jQuery('#${random}_savePactRenewlink').click(function() {
			jQuery("#${random}_pactRenewForm").attr("action","savePactRenew?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}&addFrom=${addFrom}");
			jQuery("#${random}_pactRenewForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="${random}_pactRenewForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:hidden key="pactRenew.state"/>
					<s:hidden key="pactRenew.makePerson.personId"/>
					<s:hidden key="pactRenew.checkPerson.personId"/>
					<s:hidden key="pactRenew.confirmPerson.personId"/>
					<s:hidden key="pactRenew.makeDate"/>
					<s:hidden key="pactRenew.checkDate"/>
					<s:hidden key="pactRenew.confirmDate"/>
					<s:hidden key="pactRenew.yearMonth"/>
					<label><s:text name='续签人员' />:</label>
					<s:if test="%{entityIsNew}">
						<s:hidden name="pactIds"/>
						<s:textarea value="%{renewNames}" cssClass="readonly" readonly="true" rows="2" cols="53" style="resize:none"/>
					</s:if>
					<s:else>	
						<s:hidden key="pactRenew.id"/>
						<s:hidden key="pactRenew.renewNo"/>
						<s:hidden key="pactRenew.pact.id"/>
						<s:textarea value="%{pactRenew.pact.hrPerson.name}" cssClass="readonly" readonly="true" rows="2" cols="53" style="resize:none"/>
					</s:else>
				</div>
				<div class="unit">
					<s:textfield id="pactRenew_renewYear" key="pactRenew.renewYear" name="pactRenew.renewYear" cssClass="digits"/>
					<%-- <s:textfield key="pactRenew.validDate" name="pactRenew.validDate" cssClass="Wdate required" style="height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})"/> --%>
				</div>
				<div class="unit">
					<s:textarea key="pactRenew.remark" name="pactRenew.remark" rows="4" cols="53" maxLength="200"/>
				</div>
				<s:if test="%{oper=='view'}">
					<c:if test="${pactNeedCheck=='1'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="pactRenew.makePerson"></s:text>:</label>
								<input type="text" value="${pactRenew.makePerson.name}"></input>
							</span>
							<span>
								<label><s:text name="pactRenew.checkPerson"></s:text>:</label>
								<input type="text" value="${pactRenew.checkPerson.name}"></input>
							</span>
							<span>
								<label><s:text name="pactRenew.confirmPerson"></s:text>:</label>
								<input type="text" value="${pactRenew.confirmPerson.name}"></input>
							</span>
						</div>
					</c:if>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li id="${random}_pactRenewFormSaveButton">
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}_savePactRenewlink"><s:text name="button.save" /></button>
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





