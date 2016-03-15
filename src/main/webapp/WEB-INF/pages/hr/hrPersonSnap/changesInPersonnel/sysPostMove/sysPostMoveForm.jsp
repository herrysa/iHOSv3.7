<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#sysPostMoveForm").attr("action","saveSysPostMove?popup=true&navTabId="+'${navTabId}&oper=${oper}&entityIsNew=${entityIsNew}');
			jQuery("#sysPostMoveForm").submit();
		});
		
		if("${oper}"=="view"){
			readOnlyForm("sysPostMoveForm");
		}else{
			jQuery("#sysPostMove_name").addClass("required");
			jQuery("#sysPostMove_duty").addClass("required");
			var departmentId = "${sysPostMove.hrPersonHis.department.departmentId}";
			var postId = "${sysPostMove.hrPersonHis.duty.id}";
			jQuery("#sysPostMove_duty").treeselect({
				dataType:"url",
				optType:"single",
				url:'getPostForRecruitNeed?deptId='+departmentId+'&oldPostId='+postId,
				exceptnullparent:false,
				lazy:false
			});
		}
	});
	 function saveSysPostMoveCallback(data){
			formCallBack(data);
			if(data.statusCode!=200){
				return;
			}
			if("${oper}"=="done"&&data.pactExist){
				var pactUrl="${ctx}/sysPostMoveGridEdit?oper=donePact";
				pactUrl = pactUrl+"&id="+data.id+"&navTabId=${navTabId}";
				alertMsg.confirm("确认修改合同？", {
					okCall : function() {
						$.post(pactUrl,function(data) {
							formCallBack(data);
						});
					}
				});
			}
		}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="sysPostMoveForm" method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this,saveSysPostMoveCallback);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="sysPostMove.id" />
					<s:hidden key="sysPostMove.code" />
					<s:hidden key="sysPostMove.maker.personId" />
					<s:hidden key="sysPostMove.makeDate" />
					<s:hidden key="sysPostMove.checker.personId" />
					<s:hidden key="sysPostMove.checkDate" />
					<s:hidden key="sysPostMove.state" />
					<s:hidden key="sysPostMove.personSnapCode" />
					<s:hidden key="sysPostMove.doneDate"/>
					<s:hidden key="sysPostMove.doner.personId"/>
					<s:hidden key="sysPostMove.hrPerson.personId"/>
					<s:hidden key="sysPostMove.hrPersonHis.hrPersonPk.personId"/>
					<s:hidden key="sysPostMove.hrPersonHis.hrPersonPk.snapCode"/>
					<s:hidden key="sysPostMove.yearMonth"/>
					<s:hidden key="sysPostMove.moveDate"/>
				</div>
				<div class="unit">
					<s:textfield key="sysPostMove.person" value="%{sysPostMove.hrPersonHis.name}" readonly="true"/>
					<s:textfield id="sysPostMove_name" key="sysPostMove.name" maxlength="20" />
				</div>
				<div class="unit">
					<s:textfield key="sysPostMove.orgCode" name="orgCode" value="%{sysPostMove.hrPersonHis.hrOrg.orgname}" readonly="true"/>
					<s:textfield key="sysPostMove.dept" value="%{sysPostMove.hrPersonHis.departmentHis.name}" readonly="true"/>
				</div>
				<div class="unit">
				<s:textfield key="sysPostMove.oldPostType" value="%{sysPostMove.hrPersonHis.postType}" readonly="true"/>
				<label><s:text name='sysPostMove.postType' />:</label> 
									<span style="float: left; width: 140px"> 
								    	<s:select key="sysPostMove.postType" cssClass="input-small" maxlength="20"
				        						 list="postTypeList" listKey="value" listValue="content"
				        						 emptyOption="false" theme="simple">
				        				</s:select>
				        			</span>
				</div>
				<div class="unit">
					<s:textfield key="sysPostMove.oldDuty" value="%{sysPostMove.hrPersonHis.duty.name}" readonly="true"/>
					<s:textfield id="sysPostMove_duty" key="sysPostMove.duty" value="%{sysPostMove.duty.name}" name="sysPostMove.duty.name"  maxlength="50"/>
					<s:hidden id="sysPostMove_duty_id" key="sysPostMove.duty.id"/>
				</div>
<!-- 				<div class="unit"> -->
<%-- 					<label style="float:left;white-space:nowrap"><s:text name='sysPostMove.moveDate' />:</label><input --%>
<!--         						 name="sysPostMove.moveDate" type="text"  -->
<!--       							  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" -->
<%--        							 value="<fmt:formatDate value="${sysPostMove.moveDate}" pattern="yyyy-MM-dd"/>" --%>
<!--       							  onFocus="WdatePicker({isShowClear:true,readOnly:true})" class="sysPostMoveFormInput"/>  -->
<!-- 				</div>  -->
				<div class="unit">
					<s:textarea key="sysPostMove.reason" maxlength="200" rows="5" cols="54" cssClass="input-xlarge" />
				</div>
				<div class="unit">
					<s:textarea key="sysPostMove.remark" maxlength="200" rows="5" cols="54" cssClass="input-xlarge" />
				</div>
				<s:if test="%{oper=='view'}">
				<c:if test="${personNeedCheck=='1'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="sysPostMove.maker"></s:text>:</label>
								<input type="text" value="${sysPostMove.maker.name}"></input>
							</span>
							<span>
								<label><s:text name="sysPostMove.checker"></s:text>:</label>
								<input type="text" value="${sysPostMove.checker.name}"></input>
							</span>
							<span>
								<label><s:text name="sysPostMove.doner"></s:text>:</label>
								<input type="text" value="${sysPostMove.doner.name}"></input>
							</span>
						</div>
				</c:if>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li id="sysPostMoveFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savelink">
									<s:text name="button.save" />
								</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">
									<s:text name="button.cancel" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





