<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=="view"){
			readOnlyForm("${random}_pactBreakForm");
		}
		jQuery('#${random}_savePactBreaklink').click(function() {
			jQuery("#${random}_pactBreakForm").attr("action","savePactBreak?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}&addFrom=${addFrom}");
			jQuery("#${random}_pactBreakForm").submit();
		});
	});
	function savePactBreakCallback(data){
		var personId = "${pactBreak.pact.hrPerson.personId}";
		formCallBack(data);
		if(data.statusCode==200){
			if(data.addFrom && data.addFrom=='noCheck'){
				alertMsg.confirm("是否修改人员状态为离职？", {
					okCall : function() {
						var afterUrl = "cascadeUpdatePersonByBreakPact?id="+data.pactIds+"&navTabId=${navTabId}";//被终止合同id
						$.post(afterUrl,function(data) {
							formCallBack(data);
							if(data.statusCode==200){
								if(data.navTabId=='pact_gridtable'){
									dealHrTreeNode('hrPersonTreeInPact',{id:personId},'disable','person');
								}else{
									dealHrTreeNode('hrPersonTreeInPactBreak',{id:personId},'disable','person');
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
		<form id="${random}_pactBreakForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,savePactBreakCallback);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:hidden key="pactBreak.state"/>
					<s:hidden key="pactBreak.breakDate"/>
					<s:hidden key="pactBreak.makePerson.personId"/>
					<s:hidden key="pactBreak.checkPerson.personId"/>
					<s:hidden key="pactBreak.confirmPerson.personId"/>
					<s:hidden key="pactBreak.makeDate"/>
					<s:hidden key="pactBreak.checkDate"/>
					<s:hidden key="pactBreak.confirmDate"/>
					<s:hidden key="pactBreak.yearMonth"/>
					<label><s:text name='终止人员' />:</label>
					<s:if test="%{entityIsNew}">
						<s:hidden name="pactIds"/>
						<s:textarea value="%{breakNames}" cssClass="readonly" readonly="true" rows="2" cols="53" style="resize:none"/>
					</s:if>
					<s:else>	
						<s:hidden key="pactBreak.id"/>
						<s:hidden key="pactBreak.breakNo"/>
						<s:hidden key="pactBreak.pact.id"/>
						<s:textarea value="%{pactBreak.pact.hrPerson.name}" cssClass="readonly" readonly="true" rows="2" cols="53" style="resize:none"/>
					</s:else>
				</div>
				<%-- <div class="unit">
					<s:textfield key="pactBreak.breakDate" name="pactBreak.breakDate" cssClass="Wdate" style="height:15px" 
					onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})"/>
				</div> --%>
				<div class="unit">
					<s:textarea key="pactBreak.breakReason" name="pactBreak.breakReason" rows="3" cols="53" maxlength="200"/>
				</div>
				<div class="unit">
					<s:textarea key="pactBreak.remark" name="pactBreak.remark" rows="3" cols="53" maxlength="200"/>
				</div>
				<s:if test="%{oper=='view'}">
					<c:if test="${pactNeedCheck=='1'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="pactBreak.makePerson"></s:text>:</label>
								<input type="text" value="${pactBreak.makePerson.name}"></input>
							</span>
							<span>
								<label><s:text name="pactBreak.checkPerson"></s:text>:</label>
								<input type="text" value="${pactBreak.checkPerson.name}"></input>
							</span>
							<span>
								<label><s:text name="pactBreak.confirmPerson"></s:text>:</label>
								<input type="text" value="${pactRenew.confirmPerson.name}"></input>
							</span>
						</div>
					</c:if>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li id="${random}_pactBreakFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}_savePactBreaklink"><s:text name="button.save" /></button>
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





