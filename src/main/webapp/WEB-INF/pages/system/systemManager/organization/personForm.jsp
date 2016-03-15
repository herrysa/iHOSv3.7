<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<title><fmt:message key="personDetail.title" /></title>
<meta name="heading" content="<fmt:message key='personDetail.heading'/>" />
<script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<script language="javascript" type="text/javascript"
	src="${ctx}/scripts/DatePicker/WdatePicker.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />

<script type="text/javascript">
	function changeAttr() {
		jQuery('#end').unbind('focus')
		jQuery('#end').bind('focus', function() {
			WdatePicker({
				skin : 'whyGreen',
				dateFmt : 'yyyy年MM月',
				minDate : '#F{$dp.$D(\'start\')}'
			});
		});
	}

	jQuery(function() {
		jQuery("#departmentIdCForm").autocomplete(
				"autocomplete",
				{
					width : 300,
					multiple : false,
					autoFill : false,
					matchContains : true,
					matchCase : true,
					dataType : 'json',
					parse : function(test) {
						//alert(test.dicList.length)
						var data = test.autocompleteList;
						if (data == null || data == "") {
							var rows = [];
							var rowData = {data:"没有结果",branchCode:"",branchName:""};
							rows[0] = {
								data : rowData,
								value : "",
								result : ""
							};
							return rows;
						} else {
							var rows = [];
							for ( var i = 0; i < data.length; i++) {
								var rowData = {
										data :data[i].departmentId + ","
											+ data[i].deptCode + ","
											+ data[i].cnCode + ","
											+ data[i].name + ":"
											+ data[i].departmentId,
										branchCode : data[i].branch.branchCode,
										branchName : data[i].branch.branchName
									};
								rows[rows.length] = {
									data : rowData,
									value : data[i].name,
									result : data[i].name
								};
								
							}
							return rows;
						}
					},
					extraParams : {
						flag : 2,
						entity : "Department",
						cloumnsStr : "departmentId,deptCode,name,cnCode",
						extra1 : " leaf=true and (",
						extra2 : ")"
					},
					formatItem : function(row) {
						return dropId(row.data);
					},
					formatResult : function(row) {
						return dropId(row.data);
					}
				});
		jQuery("#departmentIdCForm").result(function(event, row, formatted) {
			if (row.data == "没有结果") {
				return;
			}
			jQuery("#persondepartmentdepartmentId").attr("value", getId(row.data));
			jQuery("#person_branchCode").val(row.branchCode);
			jQuery("#person_branchName").val(row.branchName);
			//alert(jQuery("#zxDeptId").attr("value"));
		});
	})

	function getId(s) {
		var s = "" + s;
		var p = s.lastIndexOf(":");
		if (p > -1) {
			var t = s.substring(p + 1);
			return t;
		}
		return s;
	}
	function dropId(s) {
		//alert(s)
		var s = "" + s;
		var p = s.lastIndexOf(":");
		if (p > -1) {
			var t = s.substring(0, p);
			var t = s.substring(s.indexOf(",") + 1, p);
			return t;
		}
		return s;
	}
</script>
<script>
	function vaildeP() {
		alert();
		var ss = jQuery("#departmentIdCForm");
		if (ss.attr("value") == null || ss.attr("value") == "") {
			ss.attr("value", "拼音/汉字/编码");
		}
	}
	function getPinYinSelectCode(selStr) {
		var arr = selStr.split(" ");
		return arr[0];
	}
	jQuery.subscribe('processDepartmentPin', function(event, data) {
		var chargeid = document.getElementById("departmentPinYin").value;
		chargeid = getPinYinSelectCode(chargeid);
		//alert(chargeid);
		/* document
				.getElementById("person.department.departmentId").value = chargeid; */

		jQuery("#persondepartmentdepartmentId").attr("value", chargeid);
	});

	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('.time').bind('focus', function() {
			WdatePicker();
		});
		jQuery('#${random}savelink').click(function() {
			jQuery("#personForm").attr("action","savePerson?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#personForm").submit();
		});
		jQuery('input:text:first').focus();
		if('${yearStarted}' == 'true'){
			jQuery(".personFormReadonly").removeClass('Wdate').attr('readOnly',"true").removeAttr("onclick").removeAttr("onfocus");
			jQuery("#person_disable").bind('click',function(){return false;})
		}
		adjustFormInput("personForm");
	});
	function clearInput(o) {
		if (o.value == '拼音/汉字/编码') {
			o.value = "";
		}
	}
	function change() {
		var content = document.getElementById("content");
		content.style.display = (content.style.display == "none") ? "block"
				: "none";
		document.getElementById("link1").innerText = (content.style.display == "none") ? "其他信息"
				: "隐藏";
	}
	
	function personFormCallBack(data) {
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}
		var treeNode = data.deptTreeNode;
		if(!treeNode){
			return ;
		}
		if("${entityIsNew}"=="true"){
			dealTreeNodeC("personLeftTree",treeNode,"add");
		}else{
			dealTreeNodeC("personLeftTree",treeNode,"change");
		}
	}
	jQuery(document).ready(function(){
		var ss = jQuery("#departmentIdCForm");
		if (ss.attr("value") == null || ss.attr("value") == "") {
			ss.attr("value", "拼音/汉字/编码");
		}
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
	});
	jQuery(function() {
		var editType = "${editType}";
		if(editType == "0") {
			jQuery("#person_personCode").blur(function() {
				var personCode = jQuery(this).val();
				var personId = "";
				var autoPrefix = "${autoPrefixId}";
				if(autoPrefix == "1") {
					var orgCode = jQuery("#person_orgCode").val();
					personId += orgCode + "_P";
				}
				if(personCode) {
					/* var start = personCode.substring(0,1);
					if(start == "p" || start == "P") {
						personId = orgCode + "_" + personCode;
					} else {
						personId += personCode;
					} */
					personId += personCode;
				}
				jQuery("#person_personId").val(personId);
			});	
		}
	});
</script>
</head>
<body onload="vaildeP()">
	<%-- 	<s:url id="savePerson" action="savePerson" />
	<s:url id="deptAuto" action="deptPinYinJson" namespace="/pinyin" /> --%>
	<%-- 	<%@ include file="/common/messages.jsp"%> --%>
	<div class="page">
		<div class="pageContent">
			<s:form id="personForm" method="post" action="savePerson?popup=true"
				cssClass="pageForm required-validate"
				onsubmit="return validateCallback(this,personFormCallBack);">
				<div class="pageFormContent" layoutH="56">
					<div class="unit">
						<s:hidden name="editType" value="%{editType}" label="editType" />

						<s:if test="%{editType==1}">
							<s:textfield key="person.personCode" 
							maxlength="20" id="person_personCode" cssClass="required personFormReadonly" readonly="true"/>
							<%-- <s:textfield key="person.personId" required="true" /> --%>
						</s:if>
						<s:else>
							<s:textfield key="person.personCode" 
							maxlength="20" id="person_personCode" cssClass="required personFormReadonly" notrepeat="人员编码已存在" validatorParam="from Person p where p.personCode=%value%"/>
						</s:else>
						<s:textfield key="person.name" cssClass="required personFormReadonly" maxlength="20"
							/>
					</div>
					<div class="unit">
						<s:if test="%{editType == 1}">
							<s:textfield key="person.personId" id="person_personId" cssClass="required personFormReadonly" maxlength="30"
								readonly="true" />
						</s:if>
						<s:else>
							<s:textfield key="person.personId" id="person_personId" cssClass="required personFormReadonly" maxlength="30" 
							notrepeat='人员ID已存在' validatorParam="from Person person where person.personId=%value%"/>
						</s:else>
						<s:hidden id="persondepartmentdepartmentId"
							key="person.department.departmentId" readonly="true" />
						<label><fmt:message key='person.sex' />:</label> <span
							style="float: left; width: 140px">
							<s:if test="%{yearStarted}">
							<s:hidden key="person.sex"/>
							 <s:select
								key="person.sex" required="false" maxlength="20" list="sexList"
								listKey="value" cssClass="required" listValue="content"
								emptyOption="true" theme="simple"  disabled="true"></s:select>
							</s:if>
							<s:else>
							 <s:select
								key="person.sex" required="false" maxlength="20" list="sexList"
								listKey="value" cssClass="required" listValue="content"
								emptyOption="true" theme="simple"></s:select>
							</s:else>
						</span>
					</div>
					<div class="unit">
						<s:hidden id="person_orgCode" key="person.orgCode"/>
						<s:textfield key="person.department.name" cssClass="required personFormReadonly"
							id="departmentIdCForm"
							name="person.department.name" maxlength="30"
							label="%{getText('person.departmentName')}"
							value="%{person.department.name}" onfocus="clearInput(this)" />
						<label><fmt:message key="hisOrg.branchName" />:</label>
						<s:textfield theme="simple" readonly="true" id="person_branchName" value="%{person.branch.branchName}" />
						<s:hidden id="person_branchCode" key="person.branchCode" />
					</div>
					<div class="unit">
						<label><fmt:message key='person.status' />:</label> <span
							style="float: left; width: 140px"> 
							<s:if test="%{yearStarted}">
							<s:hidden key="person.status"/>
							<s:select
								key="person.status" cssClass="required"
								maxlength="20" list="statusList" listKey="value"
								listValue="content" emptyOption="true" theme="simple"  disabled="true"></s:select>
							</s:if>
							<s:else>
							<s:select
								key="person.status" cssClass="required"
								maxlength="20" list="statusList" listKey="value"
								listValue="content" emptyOption="true" theme="simple"></s:select>
							</s:else>
						</span>
					
						<label><fmt:message key='person.postType' />:</label> <span  class="formspan"
							style="float: left; width: 140px"> 
							<s:if test="%{yearStarted}">
							<s:hidden key="person.postType"/>
							<s:select
								key="person.postType"
								maxlength="20" list="postTypeList" listKey="value"
								listValue="content" emptyOption="true" theme="simple" cssClass="required" disabled="true"></s:select>
							</s:if>
							<s:else>
							<s:select
								key="person.postType"
								maxlength="20" list="postTypeList" listKey="value"
								listValue="content" emptyOption="true" theme="simple" cssClass="required"></s:select>
							</s:else>
						</span> 
					</div>
					<div class="unit">
						<label><fmt:message key='person.jobTitle' />:</label>
						<span style="float: left; width: 140px"> 
							<s:if test="%{yearStarted}">
							<s:hidden key="person.jobTitle"/>
							<s:select
								key="person.jobTitle" required="false" cssClass="input-small"
								maxlength="20" list="jobTitleList" listKey="value"
								listValue="content" emptyOption="true" theme="simple" disabled="true"></s:select>
							</s:if>
							<s:else>
							<s:select
								key="person.jobTitle" required="false" cssClass="input-small"
								maxlength="20" list="jobTitleList" listKey="value"
								listValue="content" emptyOption="true" theme="simple"></s:select>
							</s:else>
						</span>
					
						<s:textfield key="person.ratio" required="false" 
							cssClass="number personFormReadonly" />
					</div>
					<div class="unit">
						<label><fmt:message key='person.jjmark' />:</label>
						<span style="float:left;width:140px">
						<s:checkbox key="person.jjmark" theme="simple"></s:checkbox>
						</span>
						<%-- <fmt:message key='person.status'/>：<appfuse:dictionary code="empType" name="statusId" cssClass="input-small"/> --%>
					
					<s:if test="person.personId!=null">
						<label><fmt:message key='person.disable' />:</label>
						<span style="float:left;width:140px">
						<s:checkbox key="person.disable" id="person_disable" theme="simple"></s:checkbox>
						</span>
					</s:if>
					
				</div>
					<div class="unit">
						<a id="link1" href="javascript:change();" style="font-size: 12px">其他信息</a>
					</div>
					<div id="content" style="display: none">
						<div class="unit">
							<label><fmt:message key='person.birthday' />:</label><input
								class="input-small personFormReadonly" name="person.birthday" type="text"
								onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
								value="<fmt:formatDate value="${person.birthday}" pattern="yyyy-MM-dd"/>"
								onFocus="WdatePicker({isShowClear:true,readOnly:true})" /> <label><fmt:message
									key='person.educationalLevel' />:</label> <span
								style="float: left; width: 140px"> 
							<s:if test="%{yearStarted}">
							<s:hidden key="person.educationalLevel"/>
								<s:select
									key="person.educationalLevel" required="false"
									cssClass="input-small" maxlength="20"
									list="educationalLevelList" listKey="value" listValue="content"
									emptyOption="true" theme="simple" disabled="true"></s:select>
							</s:if>
							<s:else>
							<s:select
									key="person.educationalLevel" required="false"
									cssClass="input-small" maxlength="20"
									list="educationalLevelList" listKey="value" listValue="content"
									emptyOption="true" theme="simple"></s:select>
							</s:else>
							</span>
						</div>
						<div class="unit">
							<s:textfield key="person.idNumber" required="false"
								maxlength="20" cssClass="input-small personFormReadonly" />
							<%-- <s:textfield key="person.salaryNumber" required="false"
								cssClass="input-small personFormReadonly" maxlength="20" />  --%>
							<label><s:text name='person.gzType'/>:</label>
							<span  class="formspan" style="float: left; width: 140px">
				    			<s:select key="person.gzType" headerKey=""   
									list="#request.gztypes" listKey="typeId" listValue="typeName"
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
							<s:textfield key="person.taxType" maxlength="20" cssClass=""/>
							<label><s:text name='person.stopSalary'/>:</label>
							<span  class="formspan" style="float: left; width: 140px">
								<s:checkbox key="person.stopSalary" theme="simple"/>
							</span>
						</div>
						<div class="unit">
						<label><s:text name='person.kqType'/>:</label>
							<span  class="formspan" style="float: left; width: 140px">
				    			<s:select key="person.kqType" headerKey=""   
									list="#request.kqtypes" listKey="typeId" listValue="typeName"
						   	 		emptyOption="false"  maxlength="50" width="50px" theme="simple">
				       			</s:select>
				       		</span>
							<label><s:text name='person.stopKq'/>:</label>
							<span  class="formspan" style="float: left; width: 140px">
								<s:checkbox key="person.stopKq" theme="simple"/>
							</span>
						</div>
						<div class="unit">
							<s:textarea key="person.stopKqReason" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
						</div>
					</div>
				</div>
				<div class="formBar">
					<ul>
						<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="${random}savelink">保存</button>
								</div>
							</div></li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</s:form>
		</div>
	</div>
</body>
</html>