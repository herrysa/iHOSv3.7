<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script type="text/javascript" src="${ctx}/scripts/date.js"></script>
<script>
	jQuery(document).ready(function() {
		var signState = "${pact.signState}";
		if(signState=='4'){
			jQuery("#pact_breakArea").show();
		}else if(signState=='5'){
			jQuery("#pact_relieveArea").show();
		}
		jQuery('#savePactNewlink').click(function() {
			snycSetProbationDate();
			jQuery("#pactForm").attr("action","savePact?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}");
			jQuery("#pactForm").submit();
		});
		if("${oper}"=='view'){
			readOnlyForm("pactForm");
		} else{
			jQuery("#pactNew_orgCode").addClass("required");
			jQuery("#pactNew_dept").addClass("required");
			jQuery("#pact_beginDate").addClass("required");
			jQuery("#pact_compSignDate").addClass("required");
			jQuery("#pactNew_orgCode").treeselect({
				dataType : "sql",
				optType : "single",
				sql : "SELECT orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon FROM v_hr_org_current WHERE disabled = 0 order by orgCode",
				exceptnullparent : false,
				lazy : false,
				minWidth:'200px',
				selectParent:true,
				callback : {
					afterClick : function() {
						jQuery("#pactNew_dept").val("");
						jQuery("#pactNew_dept_id").val("");
						jQuery("#pactNew_post").val("");
						jQuery("#pactNew_post_id").val("");
						initPactDeptTreeSelect();
					}
				}
			});
			initPactDeptTreeSelect();
			initPactPostTreeSelect();
		}
		/* jQuery("#pact_compSignPerson").treeselect({
			dataType : "url",
			optType : "single",
			url : "getPersonTreeSelect",
			exceptnullparent : true,
			lazy : false
		}); */
	});
	
	function initPactDeptTreeSelect(){
		jQuery("#pactNew_dept").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  deptId id,name,parentDept_id parent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon FROM v_hr_department_current where disabled = 0 and orgCode='"+jQuery("#pactNew_orgCode_id").val()+"' order by deptCode",
			exceptnullparent : false,
			selectParent:true,
			lazy : true,
			minWidth:'200px',
			callback : {
				afterClick : function() {
					jQuery("#pactNew_post").val("");
					jQuery("#pactNew_post_id").val("");
					initPactPostTreeSelect();
					
				}
			}
		});
	}
	function initPactPostTreeSelect(){
// 		jQuery("#pactNew_post").treeselect({
// 			dataType : "sql",
// 			optType : "single",
// 			sql : "SELECT  id,name FROM hr_post where disabled = 0 and deptId='"+jQuery("#pactNew_dept_id").val()+"'",
// 			exceptnullparent : false,
// 			lazy : true
// 		});
		var departmentId =jQuery("#pactNew_dept_id").val();
		jQuery("#pactNew_post").treeselect({
			dataType:"url",
			optType:"single",
			url:'getPostForRecruitNeed?deptId='+departmentId,
			exceptnullparent:false,
			lazy:false
		});
	}
	
	function syncUpdateEndDate(obj){
		var year = obj.value;
		if(isInt(year)){
			year = parseInt(year);
			var beginDate = jQuery("#pact_beginDate").val();
			if(beginDate){
				var date = new Date(beginDate);
				date = date.DateAdd("y",year);
				date = date.DateAdd("d",-1);
				var endDate = date.format("yyyy-MM-dd");
				jQuery("#pact_endDate").val(endDate);
			}
		}
	}
	function snycCalEndDate(obj){
		var beginDate = obj.value;
		if(beginDate){
			var year = jQuery("#pact_signYear").val();
			if(isInt(year)){
				year = parseInt(year);
				var date = new Date(beginDate);
				date = date.DateAdd("y",year);
				date = date.DateAdd("d",-1);
				var endDate = date.format("yyyy-MM-dd");
				jQuery("#pact_endDate").val(endDate);
			}
		}
	}
	function snycSetProbationDate(){
		var month = jQuery("#pact_probationMonth").val();
		if(isInt(month)){
			var beginDate = jQuery("#pact_beginDate").val();
			var date = new Date(beginDate);
			month = parseInt(month);
			date = date.DateAdd("m",month);
			var endDate = date.format("yyyy-MM-dd");
			jQuery("#pact_probationBeginDate").val(beginDate);
			jQuery("#pact_probationEndDate").val(endDate);
		}
	}
	
	function closePactFormDialog(){
		if("${oper}"!='view'){
			$.pdialog.closeCurrent();
		}else{
			$.pdialog.close('viewPact_${pact.id}');
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="pactForm" method="post" action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<s:hidden key="pact.id"/>
				<s:hidden key="pact.hrPerson.personId"/>
				<s:hidden key="pact.personSnapCode"/>
				<s:hidden key="pact.deptSnapCode"/>
				<s:hidden key="pact.operator.personId"/>
				<s:hidden key="pact.operateDate"/>
				<s:hidden key="pact.checkPerson.personId"/>
				<s:hidden key="pact.checkDate"/>
				<s:hidden key="pact.confirmPerson.personId"/>
				<s:hidden key="pact.confirmDate"/>
				<s:hidden key="pact.signState"/>
				<s:hidden key="pact.pactState"/>
				<s:hidden key="pact.signTimes"/>
				<s:hidden key="pact.code"/>
				<s:hidden key="pact.lockType"/>
				<s:hidden key="pact.yearMonth"/>
				<s:hidden key="pact.probationBeginDate" id="pact_probationBeginDate"/>
				<s:hidden key="pact.probationEndDate" id="pact_probationEndDate"/>
				<div class="unit">
					<label><s:text name='pact.hrPerson' />:</label>
					<input type="text" value="${pact.hrPerson.name}" readonly="readonly"/>
					<label><s:text name='pact.orgCode' />:</label>
					<input type="text" id="pactNew_orgCode" value="${orgName}"  maxlength="50"/>
					<input type="hidden" id="pactNew_orgCode_id" value="${orgCode}"/>
				</div>
				<div class="unit">
					<label><s:text name='pact.dept' />:</label>
					<input type="text" id="pactNew_dept" value="${pact.dept.name}"  maxlength="50"/>
					<input type="hidden" name="pact.dept.departmentId" id="pactNew_dept_id" value="${pact.dept.departmentId}"/>
					<label><s:text name='pact.post' />:</label>
					<input type="text" id="pactNew_post" value="${pact.post.name}" class="" maxlength="50"/>
					<input type="hidden" name="pact.post.id" id="pactNew_post_id" value="${pact.post.id}"/>
				</div>
				<div class="unit">
					<s:textarea key="pact.workContent" name="pact.workContent" rows="2" cols="53" maxLength="200"/>
				</div>
				<div class="unit">
					<%-- <s:select key="pact.signYear" name="pact.signYear" list="#{'1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9','10':'10'}"/> --%>
					<s:textfield id="pact_signYear" key="pact.signYear" name="pact.signYear" cssClass="digits required" onblur="syncUpdateEndDate(this)"/>
					<s:textfield id="pact_probationMonth" key="pact.probationMonth" name="pact.probationMonth" cssClass="digits"/><!--  onfocus="snycInitProbationBeginDate()" onblur="syncUpdateProbationDate(this)" -->
				</div>
				<div class="unit">
					<s:textfield id="pact_beginDate" key="pact.beginDate" name="pact.beginDate" cssClass="Wdate" style="height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
						onFocus="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true,onpicked:snycCalEndDate(this)})"/>
					<%-- <s:textfield id="pact_probationBeginDate" key="pact.probationBeginDate" name="pact.probationBeginDate" cssClass="Wdate" style="height:15px" readonly="true" />
					 --%><s:textfield id="pact_endDate" key="pact.endDate" name="pact.endDate" cssClass="Wdate" style="height:15px" readonly="true"/>
				</div>
				<%-- <div class="unit">
					<s:textfield id="pact_probationEndDate" key="pact.probationEndDate" name="pact.probationEndDate" cssClass="Wdate" style="height:15px" readonly="true"/>
				</div> --%>
				<div class="unit">
					<s:textfield id="pact_compSignDate" key="pact.compSignDate" name="pact.compSignDate" cssClass="Wdate" style="height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />
					<s:textfield key="pact.compSignPeople" name="pact.compSignPeople" maxlength="20"/>
					<%-- <s:textfield id="pact_compSignPerson" key="pact.compSignPerson" name="pact.compSignPerson.name" maxlength="50"/>
					<s:hidden id="pact_compSignPerson_id" name="pact.compSignPerson.personId"/> --%>
				</div>
				<div class="unit" style="display:none" id="pact_breakArea">
					<s:textfield key="pact.breakDate" name="pact.breakDate" cssClass="Wdate" style="height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />
					<s:textfield key="pact.breakReason" name="pact.breakReason" maxlength="200" cssClass=""/>
				</div>
				<div class="unit" style="display:none" id="pact_relieveArea">
					<s:textfield key="pact.relieveDate" name="pact.relieveDate" cssClass="Wdate" style="height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" onFocus="WdatePicker({skin:'ext'isShowClear:true,readOnly:true})" />
					<s:textfield key="pact.relieveReason" name="pact.relieveReason" maxlength="200" cssClass=""/>
				</div>
				<div class="unit">				
				      <s:textarea key="pact.remark" name="pact.remark"rows="4" cols="53" maxlength="200"/>
				</div>
				<s:if test="%{oper=='view' && viewFrom != null}">
					<c:if test="${pactNeedCheck=='1'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="pact.operator"></s:text>:</label>
								<input type="text" value="${pact.operator.name}"></input>
							</span>
							<span>
								<label><s:text name="pact.checkPerson"></s:text>:</label>
								<input type="text" value="${pact.checkPerson.name}"></input>
							</span>
							<span>
								<label><s:text name="pact.confirmPerson"></s:text>:</label>
								<input type="text" value="${pact.confirmPerson.name}"></input>
							</span>
						</div>
					</c:if>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li id="pactFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savePactNewlink"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="closePactFormDialog()"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





