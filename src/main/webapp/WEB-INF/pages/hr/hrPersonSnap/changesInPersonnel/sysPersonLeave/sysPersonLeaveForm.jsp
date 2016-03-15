<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#saveSysPersonLeavelink').click(function() {
			jQuery("#sysPersonLeaveForm").attr("action","saveSysPersonLeave?popup=true&navTabId="+'${navTabId}&oper=${oper}&entityIsNew=${entityIsNew}');
			jQuery("#sysPersonLeaveForm").submit();
		});
		
		if("${oper}"=="view"){
			readOnlyForm("sysPersonLeaveForm");
		}else{
			jQuery("#sysPersonLeave_name").addClass("required");
		}
	});
	 function saveSysPersonLeaveCallback(data){
			formCallBack(data);
			if(data.statusCode!=200){
				return;
			}
			if("${oper}"=="done"){
				var personId = data.personId;
				if(personId){
					if(data.navTabId=='hrPersonCurrent_gridtable'){
						dealHrTreeNode('hrPersonCurrentLeftTree',{id:personId},'disable','person');
					}else{
						dealHrTreeNode('sysPersonLeaveTypeTreeLeft',{id:personId},'disable','person');
					}
				}
				if(data.pactExist){
					var pactUrl="${ctx}/sysPersonLeaveGridEdit?oper=donePact";
					pactUrl = pactUrl+"&id="+data.id+"&navTabId=${navTabId}";
					alertMsg.confirm("确认解除合同？", {
						okCall : function() {
							$.post(pactUrl,function(data) {
								formCallBack(data);
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
		<form id="sysPersonLeaveForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,saveSysPersonLeaveCallback);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="sysPersonLeave.id"/>
					<s:hidden key="sysPersonLeave.code"/>
					<s:hidden key="sysPersonLeave.maker.personId" />
					<s:hidden key="sysPersonLeave.makeDate" />
					<s:hidden key="sysPersonLeave.checker.personId" />
					<s:hidden key="sysPersonLeave.checkDate" />
					<s:hidden key="sysPersonLeave.state" />
					<s:hidden key="sysPersonLeave.personSnapCode" />
					<s:hidden key="sysPersonLeave.doneDate"/>
					<s:hidden key="sysPersonLeave.doner.personId"/>
					<s:hidden key="sysPersonLeave.hrPerson.personId"/>
					<s:hidden key="sysPersonLeave.hrPersonHis.hrPersonPk.personId"/>
					<s:hidden key="sysPersonLeave.hrPersonHis.hrPersonPk.snapCode"/>
					<s:hidden key="sysPersonLeave.yearMonth"/>
					<s:hidden key="sysPersonLeave.leaveDate"/>
				</div>
				<div class="unit">
					<s:textfield key="sysPersonLeave.person" value="%{sysPersonLeave.hrPersonHis.name}" readonly="true"/>	
      				<s:textfield key="sysPersonLeave.hrDept" value="%{sysPersonLeave.hrPersonHis.departmentHis.name}" readonly="true"/>
				</div>
				<div class="unit">
					<s:textfield id="sysPersonLeave_name" key="sysPersonLeave.name"  maxlength="20" />
					<%-- <s:textfield key="sysPersonLeave.orgCode" value="%{sysPersonLeave.hrPersonHis.org.orgname}" name="orgCode" readonly="true"/>	
					 --%><s:select key="sysPersonLeave.type" name="sysPersonLeave.type"  style="font-size:12px"
     					list="leaveTypeList" listKey="value" listValue="content" emptyOption="false"/>
				</div>
<!-- 				<div class="unit"> -->
<%-- 					<label><s:text name='sysPersonLeave.leaveDate' />:</label> --%>
<!-- 					<input name="sysPersonLeave.leaveDate" type="text"  -->
<!--       					onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" -->
<%--        					value="<fmt:formatDate value="${sysPersonLeave.leaveDate}" pattern="yyyy-MM-dd"/>" --%>
<!--       					onFocus="WdatePicker({isShowClear:true,readOnly:true})" />  -->
     				
<!-- 				</div>  -->
				<div class="unit">
					<s:textarea key="sysPersonLeave.reason" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
				</div>
				<div class="unit">
					<s:textarea key="sysPersonLeave.remark" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
				</div> 
				<s:if test="%{oper=='view'}">
				<c:if test="${personNeedCheck=='1'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="sysPersonLeave.maker"></s:text>:</label>
								<input type="text" value="${sysPersonLeave.maker.name}"></input>
							</span>
							<span>
								<label><s:text name="sysPersonLeave.checker"></s:text>:</label>
								<input type="text" value="${sysPersonLeave.checker.name}"></input>
							</span>
							<span>
								<label><s:text name="sysPersonLeave.doner"></s:text>:</label>
								<input type="text" value="${sysPersonLeave.doner.name}"></input>
							</span>
						</div>
				</c:if>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li id="sysPersonLeaveFormSaveButton" ><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveSysPersonLeavelink"><s:text name="button.save" /></button>
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





