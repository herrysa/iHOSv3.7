<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=="view"){
			readOnlyForm("${random}_pactRelieveForm");
		}
		jQuery('#${random}_savePactRelievelink').click(function() {
			jQuery("#${random}_pactRelieveForm").attr("action","savePactRelieve?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}&addFrom=${addFrom}");
			jQuery("#${random}_pactRelieveForm").submit();
		});
	});
	
	function savePactRelieveCallback(data){
		var personId = "${pactRelieve.pact.hrPerson.personId}";
		formCallBack(data);
		if(data.statusCode==200){
			if(data.addFrom && data.addFrom=='noCheck'){
				alertMsg.confirm("是否修改人员状态为离职？", {
					okCall : function() {
						var afterUrl = "cascadeUpdatePersonByRelievePact?id="+data.pactIds+"&navTabId=${navTabId}";;//被解除合同id
						$.post(afterUrl,function(data) {
							formCallBack(data);
							if(data.statusCode==200){
								if(data.navTabId=='pact_gridtable'){
									dealHrTreeNode('hrPersonTreeInPact',{id:personId},'disable','person');
								}else{
									dealHrTreeNode('hrPersonTreeInPactRelieve',{id:personId},'disable','person');
								}
							}
						});
					}
				});
			}
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="${random}_pactRelieveForm" method="post" action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,savePactRelieveCallback);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:hidden key="pactRelieve.state"/>
					<s:hidden key="pactRelieve.relieveDate"/>
					<s:hidden key="pactRelieve.makePerson.personId"/>
					<s:hidden key="pactRelieve.checkPerson.personId"/>
					<s:hidden key="pactRelieve.confirmPerson.personId"/>
					<s:hidden key="pactRelieve.makeDate"/>
					<s:hidden key="pactRelieve.checkDate"/>
					<s:hidden key="pactRelieve.confirmDate"/>
					<s:hidden key="pactRelieve.yearMonth"/>
					<label><s:text name='解除人员' />:</label>
					<s:if test="%{entityIsNew}">
						<s:hidden name="pactIds"/>
						<s:textarea value="%{relieveNames}" cssClass="readonly" readonly="true" rows="2" cols="53" style="resize:none"/>
					</s:if>
					<s:else>	
						<s:hidden key="pactRelieve.id"/>
						<s:hidden key="pactRelieve.relieveNo"/>
						<s:hidden key="pactRelieve.pact.id"/>
						<s:textarea value="%{pactRelieve.pact.hrPerson.name}" cssClass="readonly" readonly="true" rows="2" cols="53" style="resize:none"/>
					</s:else>
				</div>
				<%-- <div class="unit">
					<s:textfield key="pactRelieve.relieveDate" name="pactRelieve.relieveDate" cssClass="Wdate" style="height:15px"
					onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})"/>
				</div> --%>
				<div class="unit">
					<s:textarea key="pactRelieve.relieveReason" name="pactRelieve.relieveReason" rows="3" cols="53" maxLength="200"/>
				</div>
				<div class="unit">
					<s:textarea key="pactRelieve.remark" name="pactRelieve.remark" rows="3" cols="53" maxLength="200"/>
				</div>
				<s:if test="%{oper=='view'}">
					<c:if test="${pactNeedCheck=='1'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="pactRelieve.makePerson"></s:text>:</label>
								<input type="text" value="${pactRelieve.makePerson.name}"></input>
							</span>
							<span>
								<label><s:text name="pactRelieve.checkPerson"></s:text>:</label>
								<input type="text" value="${pactRelieve.checkPerson.name}"></input>
							</span>
							<span>
								<label><s:text name="pactRelieve.confirmPerson"></s:text>:</label>
								<input type="text" value="${pactRelieve.confirmPerson.name}"></input>
							</span>
						</div>
					</c:if>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li id="${random}_pactRelieveFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}_savePactRelievelink"><s:text name="button.save" /></button>
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





