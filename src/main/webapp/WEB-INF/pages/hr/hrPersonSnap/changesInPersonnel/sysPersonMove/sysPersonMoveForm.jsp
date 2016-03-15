<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#sysPersonMoveFormSavelink').click(function() {
			jQuery("#sysPersonMoveForm").attr("action","saveSysPersonMove?popup=true&navTabId="+'${navTabId}&oper=${oper}&entityIsNew=${entityIsNew}');
			jQuery("#sysPersonMoveForm").submit();
		});
		if("${oper}"=='view'){
			readOnlyForm("sysPersonMoveForm");
		}else{
			jQuery("#sysPersonMove_orgCodeNew").addClass("required");
			jQuery("#sysPersonMove_hrDeptNew").addClass("required");
			jQuery("#sysPersonMove_orgCodeNew").treeselect({
				dataType : "sql",
				optType : "single",
				sql : "SELECT orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon  FROM v_hr_org_current WHERE disabled = 0 order by orgCode",
				exceptnullparent : false,
				lazy : false,
				minWidth:'200px',
				selectParent:true,
				callback : {
					afterClick : function() {
						jQuery("#sysPersonMove_hrDeptNew").val("");
						jQuery("#sysPersonMove_hrDeptNew_id").val("");
						jQuery("#sysPersonMove_duty").val("");
						jQuery("#sysPersonMove_duty_id").val("");
						initHrDeptTreeSelect();
					}
				}
			});
			if('${entityIsNew}'!="true"){
				initHrDeptTreeSelect();
				initPostTreeSelect();
			}
		}
	});
	
	function checkOrgExists(type){
		var orgCode = jQuery("#sysPersonMove_orgCodeNew_id").val();
		if(!orgCode){
			alertMsg.error("请填写新单位！");
			return;
		}
	}
	
	function checkDeptExists(){
		var deptId = jQuery("#sysPersonMove_hrDeptNew_id").val();
		if(!deptId){
			alertMsg.error("请填写新部门！");
			return;
		}
	}
	
	function initHrDeptTreeSelect(){
		var	orgCode = jQuery("#sysPersonMove_orgCodeNew_id").val();
		var oldDeptId = "${sysPersonMove.hrPersonHis.department.departmentId}";
		
		jQuery("#sysPersonMove_hrDeptNew").treeselect({
		   dataType:"sql",
		   optType:"single",
		   sql : "SELECT  deptId id,name,parentDept_id parent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon FROM v_hr_department_current where disabled = 0 and orgCode='"+orgCode+"' order by deptCode",
		   exceptnullparent:false,
		   lazy:false,
		   minWidth:'200px',
		   callback : {
				afterClick : function() {
					if(jQuery("#sysPersonMove_hrDeptNew_id").val()==oldDeptId){
						jQuery("#sysPersonMove_hrDeptNew").val("");
						jQuery("#sysPersonMove_hrDeptNew_id").val("");
						alertMsg.error("不能调动到原部门！");
						return;
					}
					jQuery("#sysPersonMove_duty").val("");
					jQuery("#sysPersonMove_duty_id").val("");
					initPostTreeSelect();
				}
			}
		});
	}
	// 新岗位 :是部门 
	function initPostTreeSelect(){
		var departmentId =jQuery("#sysPersonMove_hrDeptNew_id").val();
		jQuery("#sysPersonMove_duty").treeselect({
			dataType:"url",
			optType:"single",
			url:'getPostForRecruitNeed?deptId='+departmentId,
			exceptnullparent:false,
			lazy:false
		});
	}
	 function saveSysPersonMoveCallback(data){
			formCallBack(data);
			if(data.statusCode!=200){
				return;
			}
			if("${oper}"=="done"){
				var delTreeNodes = data.delTreeNodes;
				var addTreeNodes = data.addTreeNodes;
				if(delTreeNodes){
					if(data.navTabId=='hrPersonCurrent_gridtable'){
						$.each(delTreeNodes, function(){      
							dealHrTreeNode('hrPersonCurrentLeftTree',this,'del','person');  
						});  
					}else{
						$.each(delTreeNodes, function(){      
							dealHrTreeNode('sysPersonMoveTypeTreeLeft',this,'del','person');  
						});  
					}
				}
				if(addTreeNodes){
					if(data.navTabId=='hrPersonCurrent_gridtable'){
						$.each(addTreeNodes, function(){      
							dealHrTreeNode("hrPersonCurrentLeftTree",this,"add","person");  
						});  
					}else{
						$.each(addTreeNodes, function(){   
							dealHrTreeNode("sysPersonMoveTypeTreeLeft",this,"add","person");  
						});  
					}
				}
				if(data.pactExist){
					var pactUrl="${ctx}/sysPersonMoveGridEdit?oper=donePact";
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
		}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="sysPersonMoveForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,saveSysPersonMoveCallback);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="sysPersonMove.id"/>
					<s:hidden key="sysPersonMove.code"/>
					<s:hidden key="sysPersonMove.checkDate"/>
					<s:hidden key="sysPersonMove.makeDate"/>
					<s:hidden key="sysPersonMove.state"/>
					<s:hidden key="sysPersonMove.maker.personId"/>
					<s:hidden key="sysPersonMove.checker.personId"/>
					<s:hidden key="sysPersonMove.personSnapCode" />
					<s:hidden key="sysPersonMove.deptSnapCodeNew" />
					<s:hidden key="sysPersonMove.doneDate"/>
					<s:hidden key="sysPersonMove.doner.personId"/>
					<s:hidden key="sysPersonMove.hrPerson.personId"/>
					<s:hidden key="sysPersonMove.hrPersonHis.hrPersonPk.personId"/>
					<s:hidden key="sysPersonMove.hrPersonHis.hrPersonPk.snapCode"/>
					<s:hidden key="sysPersonMove.yearMonth"/>
					<s:hidden key="sysPersonMove.moveDate"/>
				</div>
				<div class="unit">
					<s:textfield key="person.name" value="%{sysPersonMove.hrPersonHis.name}" readonly="true"/>	
					<s:textfield key="sysPersonMove.name" cssClass="required" maxlength="20"/>	
				</div>
      			<div class="unit">
      				<s:textfield key="sysPersonMove.orgCode" value="%{sysPersonMove.hrPersonHis.hrOrg.orgname}" name="oldOrgCode" readonly="true"/>	
      				<s:textfield key="sysPersonMove.orgCodeNew" id="sysPersonMove_orgCodeNew" name="orgName" value="%{orgName}"  maxlength="50"/>	
      				<s:hidden id="sysPersonMove_orgCodeNew_id" value="%{orgCode}" name="newOrgCode"/>
      			</div>
      			<div class="unit">
      				<s:textfield key="oldDepartment.name" value="%{sysPersonMove.hrPersonHis.departmentHis.name}" readonly="true"/>
      				<s:textfield id="sysPersonMove_hrDeptNew" key="newDepartment.name"  name="sysPersonMove.hrDeptNew.name" value="%{sysPersonMove.hrDeptNew.name}"  onfocus="checkOrgExists()" maxlength="50"/>	
      				<s:hidden id="sysPersonMove_hrDeptNew_id" value="%{sysPersonMove.hrDeptNew.departmentId}" name="sysPersonMove.hrDeptNew.departmentId"/>
				</div>
				<div class="unit">
					<s:textfield key="sysPersonMove.oldPostType" value="%{sysPersonMove.hrPersonHis.postType}" readonly="true"/>
					<label><s:text name='sysPersonMove.postType' />:</label> 
									<span style="float: left; width: 140px"> 
								    	<s:select key="sysPersonMove.postType" cssClass="input-small" maxlength="20"
				        						 list="postTypeList" listKey="value" listValue="content"
				        						 emptyOption="false" theme="simple" >
				        				</s:select>
				        			</span>
				</div>
      			<div class="unit">
      				<s:textfield key="sysPersonMove.oldDuty" value="%{sysPersonMove.hrPersonHis.duty.name}" readonly="true"/>
      				<s:textfield id="sysPersonMove_duty" key="sysPersonMove.duty"  name="sysPersonMove.duty.name" cssClass="" onfocus="checkDeptExists()" maxlength="50"/>	
      				<s:hidden id="sysPersonMove_duty_id" value="%{sysPersonMove.duty.id}" name="sysPersonMove.duty.id"/>
      			</div>
<!--        			<div class="unit">  -->
<%--        				<label style="float:left;white-space:nowrap"><s:text name='sysPersonMove.moveDate' />:</label><input  --%>
<!--         						 name="sysPersonMove.moveDate" type="text"    -->
<!--        							 onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"   -->
<%--         							 value="<fmt:formatDate value="${sysPersonMove.moveDate}" pattern="yyyy-MM-dd"/>"  --%>
<!--        							  onFocus="WdatePicker({isShowClear:true,readOnly:true})" class="sysPersonMoveFormInput"/>  -->
<!--  				</div>  -->
				<div class="unit">
					<s:textarea key="sysPersonMove.reason" maxlength="200" rows="5" cols="54" cssClass="input-xlarge" />
				</div>
				<div class="unit">
					<s:textarea key="sysPersonMove.remark" maxlength="200" rows="5" cols="54" cssClass="input-xlarge" />
				</div>
				<s:if test="%{oper=='view'}">
					<c:if test="${personNeedCheck=='1'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="maker.name"></s:text>:</label>
								<input type="text" value="${sysPersonMove.maker.name}"></input>
							</span>
							<span>
								<label><s:text name="checker.name"></s:text>:</label>
								<input type="text" value="${sysPersonMove.checker.name}"></input>
							</span>
							<span>
								<label><s:text name="sysPersonMove.doner"></s:text>:</label>
								<input type="text" value="${sysPersonMove.doner.name}"></input>
							</span>
						</div>
					</c:if>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li id="sysPersonMoveFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="sysPersonMoveFormSavelink"><s:text name="button.save" /></button>
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





