<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		var sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department where disabled=0 and deptId <> 'XT'"
		sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ";
		sql += " ORDER BY orderCol ";
		jQuery("#gzPersonForm_dept").treeselect({
			optType : "single",
			dataType : 'sql',
			sql : sql,
			exceptnullparent : true,
			lazy : false,
			minWidth : '180px',
			selectParent : false,
			callback : {
				afterClick : function(treeId,vi,v) {
				/* 	var treeObj = $.fn.zTree.getZTreeObj(treeId);
					var node = treeObj.getNodeByParam("id",vi,null);
					var orgNode = getOrgNode(treeObj,node);
					jQuery("#gzPerson_branchName").val(orgNode.name);
					jQuery("#gzPerson_branchCode").val(orgNode.name); */
					jQuery.post("getDeptById",{id:vi},function(data){
						var branch = data.department.branch;
						jQuery("#gzPerson_branchName").val(branch.branchName);
						jQuery("#gzPerson_branchCode").val(branch.branchCode);
					});
				}
			}
		});
		jQuery("#person_bank1").treeselect({
			optType : "single",
			dataType : 'sql',
			sql : "SELECT bankName id,bankName name FROM sy_bank WHERE disabled = '0'",
			exceptnullparent : true,
			lazy : false,
			minWidth : '180px',
			selectParent : false
		});
		jQuery("#person_bank2").treeselect({
			optType : "single",
			dataType : 'sql',
			sql : "SELECT bankName id,bankName name FROM sy_bank WHERE disabled = '0'",
			exceptnullparent : true,
			lazy : false,
			minWidth : '180px',
			selectParent : false
		});
		/*查询框初始化*/
		jQuery("#gzPersonForm_personType").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT t.name id,t.name,(select name from t_personType b where b.id =t.parentType)  parent FROM t_personType t where t.disabled=0  ORDER BY t.code",
			exceptnullparent:false,
			selectParent:false,
			lazy:false
		});
		if('${yearStarted}' == 'true'){
			jQuery(".gzPersonFormReadonly").removeClass('Wdate').attr('readOnly',"true").removeClass("required").unbind().
			removeAttr("onClick").removeAttr("onblur").removeAttr("onfocus");
			jQuery("#gzPerson_disable").bind('click',function(){return false;})
		}
		
		adjustFormInput("gzPersonForm");
		
		if("${entityIsNew}"=="true") {
			jQuery("#gzPerson_personCode").blur(function() {
				var personCode = jQuery(this).val();
				var orgCode = jQuery("#gzPerson_orgCode").val();
				var personId = orgCode + "_P";
				if(personCode) {
					var start = personCode.substring(0,1);
					if(start == "p" || start == "P") {
						personId = orgCode + "_" + personCode;
					} else {
						personId += personCode;
					}
				}
				jQuery("#gzPerson_personId").val(personId);
			});	
		}
	});
	/*保存*/
	function gzPersonFormSave(){
		var gzType = jQuery("#person_gzType").val();
		var gzType2 = jQuery("#person_gzType2").val();
		if(gzType2){
			if(!gzType){
				alertMsg.error("如果该人员只属于一个工资类别,请填入第一项！");
				return ;
			}else if(gzType2==gzType){
				alertMsg.error("'工资类别' 和  '工资类别2' 不能相同！");
				return ;
			}
		}
		var urlString = "savePerson?entityIsNew=${entityIsNew}&navTabId=${navTabId}";
		urlString = encodeURI(urlString);
		jQuery("#gzPersonForm").attr("action",urlString);
    	jQuery("#gzPersonForm").submit();  
	}
	function gzPersonFormCallBack(data){
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}
	}
	
	/* function getOrgNode(treeObj,node) {
		var parentNode = node.getParentNode();
		if(parentNode == null || parentNode == '' || parentNode.id == '-1') {
			return node;
		} else {
			return getOrgNode(treeObj,parentNode);
		}
	} */
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="gzPersonForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,gzPersonFormCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden id="gzPerson_orgCode" key="person.orgCode"/>
					<s:hidden key="person.kqType"/>
					<s:hidden key="person.stopKq"/>
					<s:hidden key="person.stopKqReason"/>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield key="person.personCode" maxlength="20" readonly="true"/>
				<%-- <label><fmt:message key='person.personCode' />:</label>
				<input type="text" name="person.personCode" maxlength="20" value="${person.personCode}" class="required gzPersonFormReadonly" oldValue="${person.personCode}"  notrepeat="人员编码已存在" validatorParam="from Person person where person.personCode=%value%"/> --%>
				</s:if>
				<s:else>
				<s:textfield key="person.personCode" id="gzPerson_personCode" maxlength="20" cssClass="required gzPersonFormReadonly"  notrepeat="人员编码已存在" validatorParam="from Person person where person.personCode=%value%"/>
				</s:else>
					
					<s:textfield key="person.name" maxlength="20" cssClass="required gzPersonFormReadonly"/>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
						<s:textfield key="person.personId" name="person.personId" readonly="true"/>
					</s:if>
					<s:else>
					<s:textfield key="person.personId" id="gzPerson_personId" maxlength="30" cssClass="required" notrepeat="人员ID已存在" validatorParam="from Person person where person.personId=%value%"/>
					</s:else>
					<label><fmt:message key='person.sex' />:</label>
					<span style="float: left; width: 140px" class="formspan">
					<s:if test="%{yearStarted}">
						<s:hidden key="person.sex"/>
							 <s:select key="person.sex" required="false" maxlength="20" list="sexList"
								listKey="value" cssClass="required" listValue="content"
								emptyOption="true" theme="simple"  disabled="true"></s:select>
					</s:if>
					<s:else>
					 <s:select key="person.sex" required="false" maxlength="20" list="sexList"
								listKey="value" cssClass="required" listValue="content"
								emptyOption="true" theme="simple"></s:select>
					</s:else>
					</span>
				</div>
				<div class="unit">
				 	<label><s:text name='person.department.name' />:</label>
				 	<span style="float: left; width: 140px" class="formspan">
				 		<input type="text" id="gzPersonForm_dept"  value="${person.department.name}" class="gzPersonFormReadonly">
				 		<input type="hidden" id="gzPersonForm_dept_id" name="person.department.departmentId" value="${person.department.departmentId}">
					</span>
					<label><fmt:message key="hisOrg.branchName" />:</label>
						<s:textfield theme="simple" readonly="true" id="gzPerson_branchName" value="%{person.branch.branchName}" />
						<s:hidden id="gzPerson_branchCode" key="person.branchCode" />
				</div>
				<div class="unit">
					<label><fmt:message key='person.status' />:</label>
					 <span style="float: left; width: 140px" class="formspan">
					 <input id="gzPersonForm_personType" value="${person.status}"  type="text" class="required gzPersonFormReadonly">
					 <input id="gzPersonForm_personType_id" type="hidden" value="${person.status}" name="person.status">
<%-- 					 	<s:select key="person.status" cssClass="required" --%>
<%-- 								maxlength="20" list="statusList" listKey="value" --%>
<%-- 								listValue="content" emptyOption="false" theme="simple"></s:select> --%>
					 </span>
					<label><fmt:message key='person.postType' />:</label> 
					<span style="float: left; width: 140px" class="formspan">
					<s:if test="%{yearStarted}">
					<s:hidden key="person.postType"/>
							<s:select key="person.postType"
								maxlength="20" list="postTypeList" listKey="value"
								listValue="content" emptyOption="true" theme="simple" cssClass="required" disabled="true"></s:select>
					</s:if>
					<s:else>
					<s:select key="person.postType"
								maxlength="20" list="postTypeList" listKey="value"
								listValue="content" emptyOption="true" theme="simple" cssClass="required"></s:select>
					</s:else>
					</span>
				</div>
				<div class="unit">
					<s:textfield key="person.ratio" cssClass="number gzPersonFormReadonly"/>
					<label><fmt:message key='person.jobTitle' />:</label> 
					<span style="float: left; width: 140px" class="formspan">
					<s:if test="%{yearStarted}">
						<s:hidden key="person.jobTitle"/>
						<s:select key="person.jobTitle" required="false" cssClass="input-small"
								maxlength="20" list="jobTitleList" listKey="value"
								listValue="content" emptyOption="true" theme="simple" disabled="true"></s:select>
					</s:if>
					<s:else>
					<s:select key="person.jobTitle" required="false" cssClass="input-small"
								maxlength="20" list="jobTitleList" listKey="value"
								listValue="content" emptyOption="true" theme="simple"></s:select>
					</s:else>
					</span>
				</div>
				
				<div class="unit">
				<label><fmt:message key='person.jjmark' />:</label>
					<span style="float:left;width:140px" class="formspan">
						<s:checkbox key="person.jjmark" theme="simple"></s:checkbox>
					</span>
					<label><fmt:message key='person.disable' />:</label>
					<span style="float:left;width:140px" class="formspan">
						<s:checkbox key="person.disable" id="gzPerson_disable" theme="simple"></s:checkbox>
					</span>
				</div>
				<div class="unit">
						<label><fmt:message key='person.birthday' />:</label><input name="person.birthday" type="text"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="gzPersonFormReadonly"
								value="<fmt:formatDate value="${person.birthday}" pattern="yyyy-MM-dd"/>"/>
						<label><fmt:message key='person.educationalLevel' />:</label> 
						<span style="float: left; width: 140px">
						<s:if test="%{yearStarted}">
							<s:hidden key="person.educationalLevel"/>
								<s:select key="person.educationalLevel" required="false"
									cssClass="input-small" maxlength="20"
									list="educationalLevelList" listKey="value" listValue="content"
									emptyOption="true" theme="simple" disabled="true"></s:select>
						</s:if>
						<s:else>
						<s:select key="person.educationalLevel" required="false"
									cssClass="input-small" maxlength="20"
									list="educationalLevelList" listKey="value" listValue="content"
									emptyOption="true" theme="simple"></s:select>
						</s:else>
						</span>
					</div>
					<div class="unit">
						<s:textfield key="person.idNumber" maxlength="20" cssClass="gzPersonFormReadonly"/>
						<s:textfield key="person.taxType" maxlength="20" cssClass=""/>
					</div>
					<div class="unit">
						<label><s:text name='person.gzType'/>:</label>
						<span  class="formspan" style="float: left; width: 140px">
			    			<s:select key="person.gzType" headerKey=""   headerValue=""
								list="#request.gztypes" listKey="gzTypeId" listValue="gzTypeName" 
					   	 		emptyOption="false"  maxlength="50" width="50px" theme="simple">
			       			</s:select>
			       		</span>
			       		<label>工资类别2:</label>
						<span  class="formspan" style="float: left; width: 140px">
			    			<s:select key="person.gzType2" headerKey=""   headerValue=""
								list="#request.gztypes" listKey="gzTypeId" listValue="gzTypeName"
					   	 		emptyOption="false"  maxlength="50" width="50px" theme="simple">
			       			</s:select>
			       		</span>
					</div>
					<div class="unit">
						<label><s:text name="person.bank1"/>:</label>
						<span  class="formspan" style="float: left; width: 140px">
							<input id="person_bank1" type="text" name="person.bank1" maxlength="10" value="${person.bank1}">
							<input id="person_bank1_id" type="hidden" value="${person.bank1}">
						</span>
						<s:textfield key="person.salaryNumber" maxlength="20" cssClass=""/>
					</div>
					<div class="unit">
						<label><s:text name="person.bank2"/>:</label>
						<span  class="formspan" style="float: left; width: 140px">
							<input id="person_bank2" type="text" name="person.bank2" maxlength="10" value="${person.bank2}">
							<input id="person_bank2_id" type="hidden" value="${person.bank2}">
						</span>
						<s:textfield key="person.salaryNumber2" maxlength="20" cssClass=""/>
					</div>
					<div class="unit">
						<label><s:text name='person.stopSalary'/>:</label>
						<span  class="formspan" style="float: left; width: 140px">
							<s:checkbox key="person.stopSalary" theme="simple"/>
						</span>
					</div>
					<div class="unit">
						<s:textarea key="person.stopReason" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
					</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div id="gzPerson_buttonActive" class="buttonActive">
							<div class="buttonContent">
								<button type="Button" onclick="gzPersonFormSave()"><s:text name="button.save" /></button>
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





