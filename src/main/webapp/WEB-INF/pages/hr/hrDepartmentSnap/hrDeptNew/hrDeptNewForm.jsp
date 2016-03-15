<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=='view'){
			readOnlyForm("hrDeptNewForm");
			jQuery("#hrDeptNew_buttonActive").hide();
		}else{
			jQuery("#${random}_hrDeptNew_deptCode").addClass("required");
			jQuery("#${random}_hrDepartmentSnap_name").addClass("required");
			jQuery('#hrDeptNewFormsavelink').click(function() {
			jQuery("#hrDeptNewForm").attr("action","saveHrDeptNew?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#hrDeptNewForm").submit();
		});
			initDeptTreeSelect("hrDeptNew_jjDept","jjLeaf");
		 	initDeptTreeSelect("hrDeptNew_ysDept","ysLeaf");
		}
		adjustFormInput("hrDeptNewForm");
	});
	/*检查部门编码和名称*/
	function checkDuplicateField(obj,fieldName){
		var fieldValue = obj.value;
		if(!fieldValue){
			return;
		}
		var returnMessage = "该部门";
		if("deptCode"===fieldName){
			returnMessage += "编码";
		}else if("name"===fieldName){
			var orignalDeptName = "${hrDeptNew.name}";
			if(orignalDeptName===fieldValue){
				return;
			}
			returnMessage += "名称";
		}
		returnMessage += "已存在。";
		var orgCode = "${hrDeptNew.orgCode}";
		$.ajax({
		    url: "checkHrDeptSnapDuplicateField",
		    type: 'post',
		    data:{fieldName:fieldName,fieldValue:fieldValue,orgCode:orgCode,returnMessage:returnMessage},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		        if(data!=null){
		        	 alertMsg.error(data.message);
				     obj.value="";
		        }
		    }
		});
	}
	/*奖金部门、预算部门的treeSelect*/
	function initDeptTreeSelect(id,column){
		var orgCode = "${hrDeptNew.orgCode}";
		jQuery("#"+id).treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  deptId id,name,parentDept_id parent FROM v_hr_department_current where "+column+"=1 and disabled =0 and orgCode='"+orgCode+"' order by deptCode",
			exceptnullparent : false,
			lazy : true,
			selectParent:true
		});
	}
	
	function saveHrDeptTransferCallback(data){
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}
		if(data.navTabId=='hrDepartmentCurrent_gridtable'){
			 var deptNode = data.deptNode;
			  if(!deptNode){
			   return ;
			  }
			  dealHrTreeNode("hrDepartmentCurrentLeftTree",deptNode,'add','dept');
		}else{
			var state = "${hrDeptNew.state}";
			if(state=='3'){
				 var deptNode = data.deptNode;
				  if(!deptNode){
				   return ;
				  }
				  dealHrTreeNode("hrDeptNewLeftTree",deptNode,"add",'dept');
			}
		}
	}
	function changeHrDeptNewInfo(id) {
		var content=jQuery("#"+id);
		if(content.css("display")=="none"){
			content.css("display","block");
		}else{
			content.css("display","none");
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="hrDeptNewForm" method="post"	action="saveHrDeptNew?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,saveHrDeptTransferCallback);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="hrDeptNew.newNo"/>
				<s:hidden key="hrDeptNew.id"/>
				<s:hidden key="hrDeptNew.state"/>
				<s:hidden key="hrDeptNew.checkPerson.personId"/>
				<s:hidden key="hrDeptNew.checkDate"/>
				<s:hidden key="hrDeptNew.confirmDate"/>
				<s:hidden key="hrDeptNew.confirmPerson.personId"/>
				<s:hidden key="hrDeptNew.makeDate"/>
				<s:hidden key="hrDeptNew.makePerson.personId"/>
				<s:hidden key="hrDeptNew.yearMonth"/>
				<s:hidden key="hrDeptNew.parentDeptSnapCode"/>
				<s:hidden key="hrDeptNew.jjDeptSnapCode"/>
				<s:hidden key="hrDeptNew.ysLeaf"/>
				<s:hidden key="hrDeptNew.ysDeptSnapCode"/>
				<s:hidden key="hrDeptNew.invalidDate"/>
				<s:hidden key="hrDeptNew.outin"/>
				<s:hidden key="hrDeptNew.ysPurchasingDepartment"/>
				</div>
				<div class="unit">
				<s:textfield key="hrDepartmentSnap.orgName" readonly="true" value="%{hrDeptNew.hrOrg.orgname}"/>
				<s:hidden key="hrDeptNew.orgCode"/>
				<s:hidden key="hrDeptNew.orgSnapCode"/>
				<label><s:text name='hrDepartmentSnap.deptCode' />:</label>
				<s:if test="%{entityIsNew}">
				<input type="text" id="${random}_hrDeptNew_deptCode" name="hrDeptNew.deptCode" value="${hrDeptNew.deptCode}" maxLength="20" 
				notrepeat='部门编码已存在' validatorType="sql" validatorParam="SELECT v.deptId AS checkCode FROM v_hr_department_current v WHERE v.deptCode = %value% AND v.orgCode ='${hrDeptNew.orgCode}' UNION SELECT hdn.id AS checkCode FROM hr_department_new hdn WHERE hdn.deptCode = %value% AND hdn.state IN ('1','2') AND hdn.orgCode = '${hrDeptNew.orgCode}'"/>
				</s:if>
				<s:else>
				<input type="text" id="${random}_hrDeptNew_deptCode" name="hrDeptNew.deptCode" value="${hrDeptNew.deptCode}" maxLength="20"  oldValue="${hrDeptNew.deptCode}"
				notrepeat='部门编码已存在' validatorType="sql" validatorParam="SELECT v.deptId AS checkCode FROM v_hr_department_current v WHERE v.deptCode = %value% AND v.orgCode ='${hrDeptNew.orgCode}' UNION SELECT hdn.id AS checkCode FROM hr_department_new hdn WHERE hdn.deptCode = %value% AND hdn.state IN ('1','2') AND hdn.orgCode = '${hrDeptNew.orgCode}'"/>
				</s:else>
				</div>
				<div class="unit">
				<label><s:text name='hrDepartmentSnap.name' />:</label>
				<s:if test="%{entityIsNew}">
				<input type="text" id="${random}_hrDepartmentSnap_name" name="hrDeptNew.name" value="${hrDeptNew.name}" maxLength="50" 
				 notrepeat='部门名称已存在' validatorType="sql"  validatorParam="SELECT v.deptId AS checkCode FROM v_hr_department_current v WHERE v.name = %value% AND v.orgCode ='${hrDeptNew.orgCode}' UNION SELECT hdn.id AS checkCode FROM hr_department_new hdn WHERE hdn.name = %value% AND hdn.state IN ('1','2') AND hdn.orgCode = '${hrDeptNew.orgCode}'"/>
				</s:if>
				<s:else>
				<input type="text" id="${random}_hrDepartmentSnap_name" name="hrDeptNew.name" value="${hrDeptNew.name}" maxLength="50" oldValue="${hrDeptNew.name}"
				 notrepeat='部门名称已存在' validatorType="sql"  validatorParam="SELECT v.deptId AS checkCode FROM v_hr_department_current v WHERE v.name = %value% AND v.orgCode ='${hrDeptNew.orgCode}' UNION SELECT hdn.id AS checkCode FROM hr_department_new hdn WHERE hdn.name = %value% AND hdn.state IN ('1','2') AND hdn.orgCode = '${hrDeptNew.orgCode}'"/>
				</s:else>
				<s:textfield key="hrDepartmentSnap.shortName" name="hrDeptNew.shortnName" cssClass="" maxLength="30"/>
				</div>
				<div class="unit">
				<s:textfield key="hrDepartmentSnap.internalCode" name="hrDeptNew.internalCode" maxLength="20"/>
				<s:textfield key="hrDepartmentSnap.cnCode" name="hrDeptNew.cnCode" readonly="true"/>
				</div>
				<div class="unit">
						<label><s:text name='hrDepartmentSnap.parentDept' />:</label>
						<input type="text" id="hrDeptNew_parentDept" value="${hrDeptNew.parentDeptHis.name}" readonly="readonly"/>
						<input type="hidden" id="hrDeptNew_parentDept_id" name="hrDeptNew.parentDept.departmentId" value="${hrDeptNew.parentDept.departmentId}"/>						
						<label><s:text name='hrDepartmentSnap.deptType'/>:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:select key="hrDeptNew.deptClass" 
								list="deptTypeList" listKey="deptTypeName" listValue="deptTypeName"
								maxlength="20" emptyOption="false" theme="simple">
							</s:select>
						</span>
				</div>
					<div class="unit">
						<label><s:text name='hrDepartmentSnap.attrCode' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:select
								key="hrDeptNew.attrCode" onchange="setDeptOutIn(this)"
								list="deptAttrList" listKey="value" listValue="content"
								 emptyOption="true" theme="simple" maxlength="20">
							</s:select>
						</span>
					<label><s:text name='hrDepartmentSnap.subClass' />:</label> 
					<span class="formspan" style="float: left; width: 140px"> 
					<s:select key="hrDeptNew.subClass"  
						list="deptSubClassList"  listKey="value" listValue="content" 
						emptyOption="true" theme="simple" maxlength="20">
					</s:select> 
					</span>
				</div>	
				<div class="unit">
					<s:textfield key="hrDepartmentSnap.manager" name="hrDeptNew.manager" maxLength="20" cssClass=""/>
					<s:textfield key="hrDepartmentSnap.phone" name="hrDeptNew.phone" maxLength="20" cssClass=""/>
				</div>
				<div class="unit">
					<s:textfield key="hrDepartmentSnap.contact" name="hrDeptNew.contact" maxLength="20" cssClass=""/>
					<s:textfield key="hrDepartmentSnap.contactPhone" name="hrDeptNew.contactPhone" maxLength="20" cssClass=""/>
				</div>
				<div class="unit">
						<s:textfield key="hrDepartmentSnap.planCount" name="hrDeptNew.planCount" cssClass="digits"/>
						<s:textfield id="hrDepartmentSnap_realCount" key="hrDepartmentSnap.realCount" name="hrDeptNew.realCount" value="0" cssClass="digits" readonly="true"/>
					</div>
					<div class="unit">
						<s:textfield id="hrDepartmentSnap_diffCount" key="hrDepartmentSnap.diffCount" name="hrDeptNew.diffCount" cssClass="digits" readonly="true"/>
						<%-- <s:textfield key="hrDepartmentSnap.invalidDate" name="hrDepartmentSnap.invalidDate" cssClass="Wdate" style="height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})"/> --%>
						<s:textfield key="hrDepartmentSnap.clevel" name="hrDeptNew.clevel" readonly="true"/>
					</div>
					<div class="unit">
					<s:textarea key="hrDeptNew.remark" required="false" maxlength="200" rows="2" cols="53" />
					</div>
					<div class="unit">
						<a href="javascript:changeHrDeptNewInfo('contentHrDeptNewOther');" style="font-size: 12px">其他信息</a>
						</div>
					<div id="contentHrDeptNewOther" style="display:none">
					<div class="unit">
					<label><s:text name='hrDepartmentSnap.kindCode' />:</label>
							<span class="formspan" style="float: left; width: 140px"> 
								<s:select key="hrDeptNew.kindCode" 
									list="deptKindList" listKey="value" listValue="content" 
									emptyOption="true" theme="simple"  maxlength="20">
								</s:select>
							</span>
					<label><s:text name='hrDepartmentSnap.jjDeptType' />:</label> 
					<span class="formspan" style="float: left; width: 140px"> 
					<s:select key="hrDepartmentSnap.jjDeptType" name="hrDeptNew.jjDeptType.khDeptTypeId"
							list="jjDeptTypeList" listKey="khDeptTypeId" listValue="khDeptTypeName"
							emptyOption="false"  maxlength="10" theme="simple">
						</s:select>
						</span>
					</div>
				<div class="unit">
					<label><s:text name='hrDepartmentSnap.ysDept' />:</label>
					<input type="text" id="hrDeptNew_ysDept" value="${hrDeptNew.ysDeptHis.name}"/>
					<input type="hidden" id="hrDeptNew_ysDept_id" name="hrDeptNew.ysDept.departmentId" value="${hrDeptNew.ysDept.departmentId}"/>
					<label><s:text name='hrDepartmentSnap.jjDept' />:</label>
					<input type="text" id="hrDeptNew_jjDept" value="${hrDeptNew.jjDeptHis.name}"/>
					<input type="hidden" id="hrDeptNew_jjDept_id" name="hrDeptNew.jjDept.departmentId" value="${hrDeptNew.jjDept.departmentId}"/>
				</div>
					<div class="unit">
						<label><s:text name='hrDepartmentSnap.ysLeaf' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:checkbox key="hrDeptNew.ysLeaf" required="false" theme="simple"></s:checkbox>
						</span>
						<label><s:text name='hrDepartmentSnap.jjLeaf' />:</label> 
						<span class="formspan" style="float: left; width: 140px"> 
							<s:checkbox key="hrDeptNew.jjLeaf" required="false" theme="simple"></s:checkbox>
						</span>
					</div>
					</div>	
					<s:if test="%{oper=='view'}">
					<c:if test="${deptNeedCheck=='1'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="hrDeptNew.makePerson"></s:text>:</label>
								<input type="text" value="${hrDeptNew.makePerson.name}"></input>
							</span>
							<span>
								<label><s:text name="hrDeptNew.checkPerson"></s:text>:</label>
								<input type="text" value="${hrDeptNew.checkPerson.name}"></input>
							</span>
							<span>
								<label><s:text name="hrDeptNew.confirmPerson"></s:text>:</label>
								<input type="text" value="${hrDeptNew.confirmPerson.name}"></input>
							</span>
						</div>
					</c:if>
				</s:if>			
			</div>
			
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="hrDeptNew_buttonActive">
							<div class="buttonContent">
								<button type="Button" id="hrDeptNewFormsavelink"><s:text name="button.save" /></button>
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





