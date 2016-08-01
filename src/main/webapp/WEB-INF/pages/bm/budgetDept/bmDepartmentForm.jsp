<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />

<script type="text/javascript">
	jQuery(function() {
		jQuery("#departmentIdC").result(function(event, row, formatted) {
					if (row == "没有结果") {
						return;
					}
					jQuery("#departmentparentDeptdepartmentId").attr("value",
							getId(row));
					//alert(jQuery("#zxDeptId").attr("value"));
				});
		
		jQuery("#departmentYsNameC").autocomplete("autocomplete",{
					width : 300,
					multiple : false,
					autoFill : false,
					matchContains : true,
					matchCase : true,
					dataType : 'json',
					parse : function(test) {
						var data = test.autocompleteList;
						if (data == null || data == "") {
							var rows = [];
							rows[0] = {
								data : "没有结果",
								value : "",
								result : ""
							};
							return rows;
						} else {
							var rows = [];
							for ( var i = 0; i < data.length; i++) {
								rows[rows.length] = {
									data : data[i].departmentId + ","
											+ data[i].deptCode + ","
											+ data[i].cnCode + ","
											+ data[i].name + ":"
											+ data[i].departmentId,
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
						extra1 : " disabled=false and (",
						extra2 : ")"
					},
					formatItem : function(row) {
						return dropId(row);
					},
					formatResult : function(row) {
						return dropId(row);
					}
				});
		jQuery("#departmentYsNameC").result(function(event, row, formatted) {
					if (row == "没有结果") {
						return;
					}
					jQuery("#departmentysDeptId").attr("value",getId(row));
				});
		
		
		jQuery("#departmentJJNameC").autocomplete("autocomplete",{
					width : 300,
					multiple : false,
					autoFill : false,
					matchContains : true,
					matchCase : true,
					dataType : 'json',
					parse : function(test) {
						var data = test.autocompleteList;
						if (data == null || data == "") {
							var rows = [];
							rows[0] = {
								data : "没有结果",
								value : "",
								result : ""
							};
							return rows;
						} else {
							var rows = [];
							for ( var i = 0; i < data.length; i++) {
								rows[rows.length] = {
									data : data[i].departmentId + ","
											+ data[i].deptCode + ","
											+ data[i].cnCode + ","
											+ data[i].name + ":"
											+ data[i].departmentId,
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
						extra1 : " disabled=false and (",
						extra2 : ")"
					},
					formatItem : function(row) {
						return dropId(row);
					},
					formatResult : function(row) {
						return dropId(row);
					}
				});
		jQuery("#departmentJJNameC").result(function(event, row, formatted) {
					if (row == "没有结果") {
						return;
					}
					jQuery("#departmentjjDeptId").attr("value",getId(row));
					//alert(jQuery("#departmentjjDeptId").attr("value"));
				});
		jQuery("#departmentYjNameC").autocomplete("autocomplete",{
			width : 300,
			multiple : false,
			autoFill : false,
			matchContains : true,
			matchCase : true,
			dataType : 'json',
			parse : function(test) {
				var data = test.autocompleteList;
				if (data == null || data == "") {
					var rows = [];
					rows[0] = {
						data : "没有结果",
						value : "",
						result : ""
					};
					return rows;
				} else {
					var rows = [];
					for ( var i = 0; i < data.length; i++) {
						rows[rows.length] = {
							data : data[i].departmentId + ","
									+ data[i].deptCode + ","
									+ data[i].cnCode + ","
									+ data[i].name + ":"
									+ data[i].departmentId,
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
				extra1 : " disabled=false and (",
				extra2 : ")"
			},
			formatItem : function(row) {
				return dropId(row);
			},
			formatResult : function(row) {
				return dropId(row);
			}
		});
		jQuery("#departmentYjNameC").result(function(event, row, formatted) {
			if(row == "没有结果") {
				return;
			}
			jQuery("#departmentYjDeptId").attr("value",getId(row));
		});
		var s1 = jQuery("#departmentIdC");
		if (s1.attr("value") == null || s1.attr("value") == "")
			s1.attr("value", "拼音/汉字/编码");
		var s2 = jQuery("#departmentYsNameC");
		if (s2.attr("value") == null || s2.attr("value") == "")
			s2.attr("value", "拼音/汉字/编码");
		var s3 = jQuery("#departmentJJNameC");
		if (s3.attr("value") == null || s3.attr("value") == "")
			s3.attr("value", "拼音/汉字/编码");
		var s4 = jQuery("#departmentYjNameC");
		if(s4.attr("value") == null || s4.attr("value") == "") {
			s4.attr("value","拼音/汉字/编码");
		}
	});

	
</script>

<script>
	function getPinYinSelectCode(selStr) {
		var arr = selStr.split(" ");
		return arr[0];
	}
	function initDepartmentTreeSelect(){
		var	orgCode = jQuery("#department_orgCode_id").val();
		var deptId = jQuery("department_departmentId").val();
		if(!deptId){
			deptId = '';
		}
		jQuery("#department_parentDept").treeselect({
		   dataType:"sql",
		   optType:"single",
		   sql : "SELECT  deptId id,name,parentDept_id parent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon FROM t_department where disabled = 0 and orgCode='"+orgCode+"' and leaf = 0 and deptId <>'"+deptId+"' and deptId <>'XT' order by deptCode",
		   exceptnullparent:false,
		   lazy:false,
		   minWidth:'200px',
		   callback : {
				afterClick : function() {
				}
			}
		});
// 		jQuery("#departmentIdC").autocomplete("autocomplete",{
// 			width : 300,
// 			multiple : false,
// 			autoFill : false,
// 			matchContains : true,
// 			matchCase : true,
// 			dataType : 'json',
// 			parse : function(test) {
// 				var data = test.autocompleteList;
// 				if (data == null || data == "") {
// 					var rows = [];
// 					rows[0] = {
// 						data : "没有结果",
// 						value : "",
// 						result : ""
// 					};
// 					return rows;
// 				} else {
// 					var rows = [];
// 					for ( var i = 0; i < data.length; i++) {
// 						rows[rows.length] = {
// 							data : data[i].departmentId + ","
// 									+ data[i].deptCode + ","
// 									+ data[i].cnCode + ","
// 									+ data[i].name + ":"
// 									+ data[i].departmentId,
// 							value : data[i].name,
// 							result : data[i].name
// 						};
// 					}
// 					return rows;
// 				}
// 			},
// 			extraParams : {
// 				flag : 2,
// 				entity : "Department",
// 				cloumnsStr : "departmentId,deptCode,name,cnCode",
// 				extra1 : " disabled=false and leaf =false and (",
// 				extra2 : ")"
// 			},
// 			formatItem : function(row) {
// 				return dropId(row);
// 			},
// 			formatResult : function(row) {
// 				return dropId(row);
// 			}
// 		});
	}
	jQuery(document).ready(function() {
		jQuery("#department_orgCode").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon  FROM T_Org WHERE disabled = 0 and orgCode<>'XT' order by orgCode",
			exceptnullparent : false,
			lazy : false,
			minWidth:'200px',
			selectParent:true,
			callback : {
				afterClick : function() {
					jQuery('#department_parentDept').val('');
					jQuery('#department_parentDept_id').val('');
					initDepartmentTreeSelect();
					checkDepartmentName();
				}
			}
		});
		jQuery("#department_branchCode").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT b.branchCode id,b.branchName name,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon FROM t_branch b WHERE b.disabled = 0 ORDER BY b.branchCode",
			exceptnullparent : false,
			lazy : false,
			minWidth:'200px',
			selectParent:true,
			callback : {
				/* afterClick : function() {
					jQuery('#department_parentDept').val('');
					jQuery('#department_parentDept_id').val('');
					initDepartmentTreeSelect();
					checkDepartmentName();
				} */
			}
		});
		initDepartmentTreeSelect();
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			/* var branch = jQuery("#department_branchCode_id").val();
			if(!branch) {
				jQuery("#department_branchCode").val("");
			} */
			//alert("ssss");
			jQuery("#departmentForm").attr("action","saveDepartment?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#departmentForm").submit();
		});
		jQuery('input:text:first').focus();
		if('${yearStarted}' == 'true'){
			jQuery(".departmentFormReadOnly").removeClass('Wdate').attr('readOnly',"true").removeAttr("onclick").removeAttr("onfocus").unbind();
			jQuery("#department_disabled").bind('click',function(){return false;})
			jQuery("#department_leaf").bind('click',function(){return false;})
			jQuery("#department_cbLeaf").bind('click',function(){return false;})
			jQuery("#department_xmLeaf").bind('click',function(){return false;})
			jQuery("#department_crLeaf").bind('click',function(){return false;})
			jQuery("#department_zcLeaf").bind('click',function(){return false;})
			jQuery("#department_jjLeaf").bind('click',function(){return false;})
			jQuery("select[name='department.jjDeptType.khDeptTypeId']").attr('disabled',true);
			jQuery("select[name='department.outin']").attr('disabled',true);
			jQuery("select[name='department.branchCode']").attr('disabled',true);
			jQuery("select[name='department.dgroup']").attr('disabled',true);
			jQuery("#department_leaf").removeAttr("onclick").removeAttr("onfocus").bind('click',function(){return false;})
		}
		adjustFormInput("departmentForm");
	});
	jQuery(function() {
		var editType = "${editType}";
		if(editType == "0") {
			jQuery("#department_deptCode").blur(function() {
				var deptCode = jQuery(this).val();
				var deptId = "";
				var autoPrefixId = "${autoPrefixId}";
				if(autoPrefixId == "1") {
					var orgCode = jQuery("#department_orgCode_id").val();
					deptId += orgCode + "_D";
				}
				if(deptCode) {
					deptId += deptCode;
				}
				jQuery("#department_departmentId").val(deptId);
				jQuery("#department_internalCode").val(deptCode);
			});
		}
			jQuery("#department_name").blur(function() {
				var name = jQuery(this).val();
				if(name) {
					jQuery("#department_shortnName").val(name);
				}
			});
		
	});
// 	function checkDepartmentName(obj){
// 		var entityIsNew = "${entityIsNew}";
// 		if(entityIsNew=='false'){
// 			var origVal = "${department.name}";
// 			if(obj.value==origVal){
				
// 			}else{
// 				checkId(obj,'Department','name','此部门名称已存在！')
// 			}
// 		}else{
// 			checkId(obj,'Department','name','此部门名称已存在！')
// 		}
		
// 	}
	
	/*检查部门名称*/
	function checkDepartmentName(){
		var fieldName = "name";
		var fieldValue = jQuery('#department_name').val();
		if(!fieldValue){
			return;
		}
		var returnMessage = "该部门";
		var orignalDeptName = "${department.name}";
		if(orignalDeptName===fieldValue){
			return;
		}
		returnMessage += "名称";
		returnMessage += "已存在。";
		var orgCode = jQuery('#department_orgCode_id').val();
		$.ajax({
		    url: "checkDepartmentName",
		    type: 'post',
		    data:{fieldName:fieldName,fieldValue:fieldValue,orgCode:orgCode,returnMessage:returnMessage},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    	alertMsg.error('系统错误！');
		    	return;
		    },
		    success: function(data){
		        if(data!=null){
		        	 alertMsg.error(data.message);
		        	 jQuery('#department_name').val('');
		        }
		    }
		});
	}
	function deptFormCallBack(data) {
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}
		var treeNode = data.deptTreeNode;
		if(!treeNode){
			return ;
		}
		var flag = "${entityIsNew}";
		if(flag == "true"){
			dealTreeNodeC("bmDepartmentLeftTree",treeNode,"add");
		} else {
			dealTreeNodeC("bmDepartmentLeftTree",treeNode,"change");
		}
	}
</script>
	<div class="page">
		<div class="pageContent">
			<form id="departmentForm" method="post"
				action="saveDepartment?popup=true"
				class="pageForm required-validate"
				onsubmit="return validateCallback(this,deptFormCallBack);">
				<div class="pageFormContent" layoutH="56">
					<div class="unit">
						<s:url id="saveDepartment" action="saveDepartment" />
						<%-- <s:url id="dicSelectList" action="dictionaryItemSelectList"	namespace="/system">
	<s:param name="dicCode">radioyesno</s:param>
</s:url> --%>
						<s:url id="deptAuto" action="deptAllPinYinJson"
							namespace="/pinyin" />
						<s:hidden name="editType" value="%{editType}" />
						<s:hidden id="departmentjjDeptId" name="department.jjDept.departmentId" value="%{department.jjDept.departmentId}" />
						<s:hidden id="departmentysDeptId" name="department.ysDept.departmentId"  value="%{department.ysDept.departmentId}" />
						<s:hidden id="departmentYjDeptId" name="department.yjDept.departmentId" value="%{department.yjDept.departmentId}"/>
<%-- 						<s:hidden id="departmentparentDeptdepartmentId" --%>
<%-- 							name="department.parentDept.departmentId" cssClass="input-small" --%>
<%-- 							value="%{department.parentDept.departmentId}" /> --%>
						<s:if test="%{editType==1}">
							<s:textfield key="department.deptCode" id="department_deptCode" cssClass="required departmentFormReadOnly"
							maxlength="20"  />
							<s:textfield id="department_name" key="department.name" cssClass="required departmentFormReadOnly" oldValue="'${department.name}'"
							maxlength="50" notrepeat='部门名称已存在' validatorParam="from Department dept where dept.name=%value% and orgCode='${department.org.orgCode}'"/>
						</s:if>
						<s:else>
							<s:textfield key="department.deptCode" id="department_deptCode" cssClass="required departmentFormReadOnly"
							maxlength="20"  notrepeat='部门编码已存在' validatorParam="from Department dept where dept.deptCode=%value% and orgCode='${department.org.orgCode}'" />
							<s:textfield id="department_name" key="department.name" cssClass="required departmentFormReadOnly"
							maxlength="50" notrepeat='部门名称已存在' validatorParam="from Department dept where dept.name=%value% and orgCode='${department.org.orgCode}'"/>
						</s:else>
					</div>
					<div class="unit">
						<s:if test="%{editType == 1}">
							<s:textfield id="department_departmentId" key="department.departmentId" cssClass="required departmentFormReadOnly"
								maxlength="30" readonly="true" />
						</s:if>
						<s:else>
							<s:textfield id="department_departmentId" key="department.departmentId" cssClass="required departmentFormReadOnly" maxlength="30" 
							notrepeat='部门ID已存在' validatorParam="from Department dept where dept.departmentId=%value%"/>
						</s:else>
						
							<s:textfield id="department_shortnName" key="department.shortnName" cssClass="required departmentFormReadOnly"
							maxlength="50" />
					</div>
					<div class="unit">
						
						<s:textfield key="department.internalCode" id="department_internalCode" required="false"
							maxlength="20" cssClass="input-small departmentFormReadOnly" />
							<s:textfield key="department.cnCode" required="false"
							maxlength="20" readonly="true" cssClass="input-small departmentFormReadOnly" />
					</div>
					<div class="unit">
						<s:textfield key="department.clevel" required="false"
							cssClass="input-small departmentFormReadOnly" />
						<label><fmt:message key='department.deptClass' />:</label> 
						<span style="float: left; width: 140px"> 
							<s:if test="%{inUse||yearStarted}">
								<s:hidden key="department.deptClass"/>
								<s:select key="department.deptClass" maxlength="20"
								list="deptClassList" listKey="deptTypeName" cssClass="departmentFormReadOnly"
								listValue="deptTypeName" emptyOption="true" theme="simple" disabled="true"></s:select>
							</s:if>
							<s:else>
								<s:select key="department.deptClass" maxlength="20"
								list="deptClassList" cssClass="required departmentFormReadOnly" listKey="deptTypeName"
								listValue="deptTypeName" emptyOption="true" theme="simple"></s:select>
							</s:else>
						</span>
						
					</div>
					<div class="unit">
						<label><fmt:message key='department.outin' />:</label> <span class="formspan" style="float: left; width: 140px">
						 <s:select key="department.outin" name="department.outin" value="%{department.outin}" cssClass="input-small departmentFormReadOnly" id="department.outin" required="false" maxlength="10" list="outinList" listKey="value" listValue="content" emptyOption="true" theme="simple"></s:select>
						</span> 
						<label><fmt:message key='department.subClass' />:</label> <span
							style="float: left; width: 140px"> 
							<s:if test="%{yearStarted}">
							<s:hidden key="department.subClass"/>
							<s:select 
								key="department.subClass" required="false" maxlength="20"
								list="subClassList" cssClass="input-small departmentFormReadOnly" listKey="value"
								listValue="content" emptyOption="true" theme="simple" disabled="true"></s:select>
							</s:if>
							<s:else>
							<s:select 
								key="department.subClass" required="false" maxlength="20"
								list="subClassList" cssClass="input-small departmentFormReadOnly" listKey="value"
								listValue="content" emptyOption="true" theme="simple"></s:select>
							</s:else>
						</span>
					</div>
					
					
					<div class="unit">
						<label><s:text name='department.orgCode' />:</label> 
						<input type="text" id="department_orgCode" class="required departmentFormReadOnly" value="${department.org.orgname}"/>
						<input type="hidden" id="department_orgCode_id" value="${department.org.orgCode}" name="department.orgCode"/>
						<label><s:text name='department.parentDeptName' />:</label> 
						<input type="text" id="department_parentDept"  value="${department.parentDeptName}" class="departmentFormReadOnly"/>
						<input type="hidden" id="department_parentDept_id" value="${department.parentDept.departmentId}" name="department.parentDept.departmentId"/>
<%-- 						<s:textfield key="department.parentDeptName" required="false" --%>
<%-- 							id="departmentIdC" cssClass="input-small departmentFormReadOnly" maxlength="30" --%>
<%-- 							label="%{getText('department.parentDeptName')}" --%>
<%-- 							value="%{department.parentDept.name}" --%>
<%-- 							onfocus="clearInput(this)" onblur="setDefaultValue(this,jQuery('#departmentparentDeptdepartmentId'))" onkeydown="setTextColor(this)"/> --%>
					</div>
					<div class="unit">
						<%-- <label><s:text name="department.branchCode" />:</label>
						<input type="text" id="department_branchCode" class="required departmentFormReadOnly" value="${department.branch.branchName}" />
						<input type="hidden" id="department_branchCode_id" value="${department.branch.branchCode}" name="department.branchCode"/> --%>
						<label><s:text name="department.branchCode"></s:text>:</label>
						<span class="formspan" style="float: left; width: 140px"> 
						<s:select list="branches" listKey="branchCode" listValue="branchName" emptyOption="true" name="department.branchCode" value="department.branchCode"></s:select>
						</span>
						<label><s:text name="department.dgroup" />:</label>
						<span class="formspan" style="float: left; width: 140px"> <s:select 
								key="department.dgroup" required="false" maxlength="20"
								list="dgroupList" cssClass="input-small departmentFormReadOnly" listKey="value"
								listValue="content" emptyOption="true" theme="simple"></s:select></span>
					</div>
					<div class="unit">
						<label><fmt:message key='department.leaf' />:</label> <span class="formspan"
							style="float: left; width: 140px"> 
								<s:checkbox	key="department.leaf" id="department_leaf" required="false" theme="simple" onclick="return %{!inUse}"></s:checkbox>
						</span>
						<s:textfield key="department.manager" required="false"
							maxlength="20" cssClass="input-small departmentFormReadOnly" />
					</div>
					<div class="unit">
						<label><s:text name='department.cbLeaf' />:</label> <span class="formspan"
							style="float: left; width: 140px"> <s:checkbox
								key="department.cbLeaf" required="false" theme="simple"></s:checkbox>
						</span>
						<label><s:text name='department.xmLeaf' />:</label> <span
							style="float: left; width: 140px"> <s:checkbox
								key="department.xmLeaf" required="false" theme="simple"></s:checkbox>
						</span>
					</div>
					<div class="unit">
						<label><s:text name='department.crLeaf' />:</label> <span class="formspan"
							style="float: left; width: 140px"> <s:checkbox
								key="department.crLeaf" required="false" theme="simple"></s:checkbox>
						</span>
						<label><s:text name='department.zcLeaf' />:</label> <span
							style="float: left; width: 140px"> <s:checkbox
								key="department.zcLeaf" required="false" theme="simple"></s:checkbox>
						</span>
					</div>
					<div class="unit">
						<s:textfield required="false"
							id="departmentYsNameC" cssClass="input-small" maxlength="30"
							label="%{getText('department.ysName')}"
							value="%{department.ysDept.name}"
							onfocus="clearInput(this)" onblur="setDefaultValue(this,jQuery('#departmentysDeptId'))" onkeydown="setTextColor(this)"/>
							
						<s:textfield required="false"
							id="departmentJJNameC" cssClass="input-small departmentFormReadOnly" maxlength="30"
							label="%{getText('department.jjName')}"
							value="%{department.jjDept.name}"
							onfocus="clearInput(this)" onblur="setDefaultValue(this,jQuery('#departmentjjDeptId'))" onkeydown="setTextColor(this)"/>
						
					</div>
					<div class="unit">
						<label><fmt:message key='department.ysLeaf' />:</label> <span class="formspan"
							style="float: left; width: 140px"> <s:checkbox
								key="department.ysLeaf" required="false" theme="simple"></s:checkbox>
						</span>
						<label><fmt:message key='department.jjLeaf' />:</label> <span
							style="float: left; width: 140px"> <s:checkbox
								id="department_jjLeaf" key="department.jjLeaf" required="false" theme="simple"></s:checkbox>
						</span>
					</div>
					<div class="unit">
						<s:textfield required="false" 
							id="departmentYjNameC" cssClass="input-small departmentFormReadOnly" maxlength="30" name="department.yjDeptName"
							label="%{getText('department.yjDeptId')}"
							onfocus="clearInput(this)" onblur="setDefaultValue(this,jQuery('#departmentYjDeptId'))" onkeydown="setTextColor(this)" />
					</div>
					<div class="unit">
						<label><fmt:message key='department.jjDeptType' />:</label> <span class="formspan" style="float: left; width: 140px">
						 <s:select key="department.jjDeptType" name="department.jjDeptType.khDeptTypeId" value="%{department.jjDeptType.khDeptTypeId}" cssClass="input-small departmentFormReadOnly" id="department.jjDeptType" required="false" maxlength="10" list="jjDeptTypeList" listKey="khDeptTypeId" listValue="khDeptTypeName" theme="simple"></s:select>
						</span>
						<s:textfield key="department.contactPhone" required="false"
							maxlength="20" cssClass="input-small departmentFormReadOnly" />
					</div>
					
					<div class="unit">
						<s:textfield key="department.phone" required="false"
							maxlength="20" cssClass="input-small departmentFormReadOnly" />
						<s:textfield key="department.contact" required="false"
							maxlength="20" cssClass="input-small departmentFormReadOnly" />
					</div>
					<div class="unit">
						<label><fmt:message key='department.invalidDate' />:</label><input
							class="input-small departmentFormReadOnly" name="department.invalidDate"
							type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
							value="<fmt:formatDate value="${department.invalidDate}" pattern="yyyy-MM-dd"/>"
							onFocus="WdatePicker({isShowClear:true,readOnly:true})" />
						<label><fmt:message key='department.disabled' />:</label> <span
							style="float: left; width: 140px"> <s:checkbox id="department_disabled"
								key="department.disabled" required="false" theme="simple" />
						</span>
					</div>
					<div class="unit">
						<s:textarea key="department.note" required="false" maxlength="100"
							rows="2" cols="54" cssClass="input-xlarge departmentFormReadOnly" />


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
			</form>
		</div>
	</div>
