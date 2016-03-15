<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		var s1 = jQuery("#specialSource_deptId_departmentName");
		if (s1.attr("value") == null || s1.attr("value") == "")
			s1.attr("value", "拼音/汉字/编码");
		var s3 = jQuery("#specialSource_deptid2_departmentName");
		if (s3.attr("value") == null || s3.attr("value") == "")
			s3.attr("value", "拼音/汉字/编码");
		
		var s2 = jQuery("#specialSource_deptid1_departmentName");
		if (s2.attr("value") == null || s2.attr("value") == "")
			s2.attr("value", "拼音/汉字/编码");
		
		var s4 = jQuery("#specialSource_deptid3_departmentName");
		if (s4.attr("value") == null || s4.attr("value") == "")
			s4.attr("value", "拼音/汉字/编码");
		
		jQuery('#${random}savelink').click(function() {
			/* var costOrgCode = jQuery("#specialSource_costOrgForm_id").val();
			if(!costOrgCode) {
				jQuery("#specialSource_costOrgForm").val("");
			} */
			var cbBranchCode = jQuery("#specialSource_cbBranchForm_id").val();
			if(!cbBranchCode) {
				jQuery("#specialSource_cbBranchForm").val("");
			}
			if(s1.attr("value")==="拼音/汉字/编码"){
				s1.attr("value","");
			}
			jQuery("#specialSourceForm").attr("action","saveSpecialSource?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#specialSourceForm").submit();
		});
		var specialSourceRule = "${specialSourceRule}";
		if(specialSourceRule == '1') {
			//var sql = "select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT'";
			var sql = "select branchCode id,branchName name,'-1' parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,branchCode orderCol from t_branch where disabled=0 AND branchCode<>'XT'";
			/* var orgPriv = "${fns:u_writeDPSql('org_dp')}";	//cbdata_org
			if(orgPriv) {
				sql += " and orgCode in " + orgPriv;
			} else {
				sql += " and 1=2 ";
			} */
			var branchPriv = "${fns:u_writeDPSql('cbdata_branch')}";
			if(branchPriv) {
				sql += " and branchCode in "+branchPriv;
			} else {
				sql += " and 1=2 ";
			}
			sql += " ORDER BY orderCol"; 
			/* jQuery("#specialSource_cbBranchForm").treeselect({
				optType : "single",
				dataType : 'sql',
				sql : sql,
				exceptnullparent : true,
				lazy : false,
				minWidth : '180px',
				selectParent : false
			}); */
		}
		
		/* if("${herpType}" != "S2") {
			jQuery("#specialSource_cbBranch_div").hide();
		} */
	});
	function selectItemType(value){
		jQuery.ajax({
			dataType : 'json',
			url:'selectItemType?itemId='+value,
		    type: 'POST',
		    error: function(data){
		    	alertMsg.confirm("");
		    },success: function(data){
		    	$("#specialSource_itemType").attr("value",data["itemType"]);
		    }
		});  
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="specialSourceForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="specialSource.specialSourceId"/>
				<s:hidden key="specialSource.status"/>
				</div>
				
				<div class="unit">
				<s:textfield id="specialSource_checkPeriod" key="specialSource.checkPeriod" name="specialSource.checkPeriod" value="%{specialSource.checkPeriod!=null?specialSource.checkPeriod:currentPeriod}" readonly="true"/>
				</div>
				<div class="unit">
				<label><s:text name="specialSource.itemId"></s:text>:</label>
				<span style="float:left;width:140px">
			    <s:select name="specialSource.itemId.itemId" list="specialItemList" listKey="itemId" listValue="itemName"  cssClass="required" emptyOption="true" onchange="selectItemType(this.value)"></s:select></span>
				</div>
				<div class="unit">
				<label><s:text name="specialSource.itemType"></s:text>:</label>
				<span style="float:left;width:140px">
				<s:textfield id="specialSource_itemType" value="%{specialSource.itemId.itemType}" readonly="true"/></span>
				</div>
				<div class="unit">
				<s:textfield id="specialSource_amount" key="specialSource.amount" name="specialSource.amount" cssClass="number required" />
				</div>
				
				
				<div class="unit">
					<s:hidden id="specialSource_deptId_departmentId" name="specialSource.deptId.departmentId" value="%{specialSource.deptId.departmentId}"/>
					
					<label><s:text name="specialSource.deptId"></s:text>:</label>
					<span style="float:left;width:140px">
						<s:textfield id="specialSource_deptId_departmentName"  value="%{specialSource.deptId.name}"  required="false" cssClass="required defaultValueStyle"   onfocus="clearInput(this)" onblur="setDefaultValue(this)" cssStyle="width:235px;" onkeydown="setTextColor(this)"></s:textfield>
					</span>

				</div>
				<div class="unit" style="${herpType=='S2'?'':'display:none'}">
					<s:if test="specialSourceRule == 1">
					<label><fmt:message key="sourcecost.costOrg"></fmt:message><fmt:message key="hisOrg.branchName"></fmt:message>:</label>
						<%-- <input type="text" id="specialSource_costOrgForm" class="required" value="${specialSource.costOrg.orgname}"/> --%>
						<s:select list="branches" listKey="branchCode" listValue="branchName" name="specialSource.cbBranch.branchCode" value="specialSource.cbBranch.branchCode" ></s:select>
					</s:if>
					<s:else>
						<label><fmt:message key="sourcecost.costOrg"></fmt:message><fmt:message key="hisOrg.branchName"></fmt:message>:</label>
						<input type="hidden" id="specialSource_costOrgForm_id" name="specialSource.costOrg.orgCode" value="${specialSource.costOrg.orgCode}"/>
						<input type="hidden" id="specialSource_cbBranchForm_id" name="specialSource.cbBranch.branchCode" value="${specialSource.cbBranch.branchCode}"/>
						<input type="text" id="specialSource_cbBranchForm" readonly="true" value="${specialSource.cbBranch.branchName}"/>
					</s:else>
				</div>
				<div class="unit">
					<s:hidden id="specialSource_deptid1_departmentId" name="specialSource.deptid1.departmentId" value="%{specialSource.deptid1.departmentId}"/>
					
					<label><s:text name="specialSource.deptid1"></s:text>:</label>
					<span style="float:left;width:140px">
						<s:textfield id="specialSource_deptid1_departmentName"  value="%{specialSource.deptid1.name}"   cssClass="defaultValueStyle"   onfocus="clearInput(this)" onblur="setDefaultValue(this)" cssStyle="width:235px;" onkeydown="setTextColor(this)"></s:textfield>
					</span>
				</div>
				
				
				<div class="unit">
					<s:hidden id="specialSource_deptid2_departmentId" name="specialSource.deptid2.departmentId" value="%{specialSource.deptid2.departmentId}"/>
					
					<label><s:text name="specialSource.deptid2"></s:text>:</label>
					<span style="float:left;width:140px">
						<s:textfield id="specialSource_deptid2_departmentName"  value="%{specialSource.deptid2.name}"   cssClass="defaultValueStyle"   onfocus="clearInput(this)" onblur="setDefaultValue(this)" cssStyle="width:235px;" onkeydown="setTextColor(this)"></s:textfield>
					</span>
				</div>
				
				
				<div class="unit">
					<s:hidden id="specialSource_deptid3_departmentId" name="specialSource.deptid3.departmentId" value="%{specialSource.deptid3.departmentId}"/>
					
					<label><s:text name="specialSource.deptid3"></s:text>:</label>
					<span style="float:left;width:140px">
						<s:textfield id="specialSource_deptid3_departmentName" value="%{specialSource.deptid3.name}"   cssClass="defaultValueStyle"   onfocus="clearInput(this)" onblur="setDefaultValue(this)" cssStyle="width:235px;" onkeydown="setTextColor(this)"></s:textfield>
					</span>
				</div>
				
				
				
				<div class="unit">
				<%-- <s:textfield id="specialSource_remark" key="specialSource.remark" name="specialSource.remark"  size="50"/> --%>
				<s:textarea id="specialSource_remark"  key="specialSource.remark" cssStyle="width:235px;height:45px"></s:textarea>
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink"><s:text name="button.save" /></button>
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
<script type="text/javascript">
jQuery(function() {
	var orgPriv = "${fns:u_writeDPSql('org_dp')}";
	var branchPriv = "${fns:u_writeDPSql('branch_dp')}";
	var orgPrivSql = "and orgCode in "+orgPriv;
	if(orgPriv.indexOf("select") != -1 || orgPriv.indexOf("SELECT") != -1){
		orgPrivSql = "";
	}
	var branchPrivSql = " and branchCode in "+branchPriv;
	if(${herpType=="S1"}||branchPriv.indexOf("select") != -1 || branchPriv.indexOf("SELECT") != -1){
		branchPrivSql = "";
	}
	var extra1Str = " leaf=true and disabled=false and departmentId !='XT' "+orgPrivSql+branchPrivSql+" and ( ";
	jQuery("#specialSource_deptId_departmentName").autocomplete(
			"autocomplete",
			{
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
						var rowData = {data:"没有结果",orgCode:"",orgName:"",branchCode:"",branchName:""};
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
									<c:if test="${herpType == 'M'}" >
										+ data[i].org.orgname + ","
									</c:if>
									<c:if test="${herpType == 'S2'}" >
										+ data[i].branch.branchName + ","
									</c:if>
										+ data[i].deptCode + ","
										+ data[i].cnCode + ","
										+ data[i].name + ":"
										+ data[i].departmentId,
									orgName : data[i].org.orgname,
									orgCode : data[i].org.orgCode,
									branchCode : data[i].branchCode,
									branchName : data[i].branchName
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
					extra1 : extra1Str,
					extra2 : ")"
				},
				formatItem : function(row) {
					return dropId(row.data);
				},
				formatResult : function(row) {
					return dropId(row.data);
				}
			});
	jQuery("#specialSource_deptId_departmentName").result(function(event, row, formatted) {
		if (row.data == "没有结果") {
			return;
		}
		jQuery("#specialSource_deptId_departmentId").attr("value", getId(row.data));
		var rule = "${specialSourceRule}";
		if(rule == "0") {
			/*
			jQuery("#specialSource_costOrgForm").val(row.orgName); */
			jQuery("#specialSource_cbBranchForm_id").val(row.branchCode);
			jQuery("#specialSource_cbBranchForm").val(row.branchName);
			jQuery("#specialSource_costOrgForm_id").val(row.orgCode);
		}
		//alert(jQuery("#specialSource_deptId_departmentId").attr("value"));
	});
	
})
jQuery(function() {
	var orgPriv = "${fns:u_writeDPSql('org_dp')}";
	var branchPriv = "${fns:u_writeDPSql('branch_dp')}";
	var orgPrivSql = "and orgCode in "+orgPriv;
	if(orgPriv.indexOf("select") != -1 || orgPriv.indexOf("SELECT") != -1){
		orgPrivSql = "";
	}
	var branchPrivSql = " and branchCode in "+branchPriv;
	if(${herpType=="S1"}||branchPriv.indexOf("select") != -1 || branchPriv.indexOf("SELECT") != -1){
		branchPrivSql = "";
	}
	var extra1Str = " leaf=true and disabled=false and departmentId !='XT' "+orgPrivSql+branchPrivSql+" and ( ";
	jQuery("#specialSource_deptid1_departmentName").autocomplete(
			"autocomplete",
			{
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
								data :data[i].departmentId + ","
									<c:if test="${herpType == 'M'}" >
										+ data[i].org.orgname + ","
									</c:if>
									<c:if test="${herpType == 'S2'}" >
										+ data[i].branch.branchName + ","
									</c:if>
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
					extra1 :extra1Str, 
					extra2 : ")"
				},
				formatItem : function(row) {
					return dropId(row);
				},
				formatResult : function(row) {
					return dropId(row);
				}
			});
	jQuery("#specialSource_deptid1_departmentName").result(function(event, row, formatted) {
		if (row == "没有结果") {
			return;
		}
		jQuery("#specialSource_deptid1_departmentId").attr("value", getId(row));
		//alert(jQuery("#specialSource_deptId_departmentId").attr("value"));
	});
	
})
jQuery(function() {
	var orgPriv = "${fns:u_writeDPSql('org_dp')}";
	var branchPriv = "${fns:u_writeDPSql('branch_dp')}";
	var orgPrivSql = "and orgCode in "+orgPriv;
	if(orgPriv.indexOf("select") != -1 || orgPriv.indexOf("SELECT") != -1){
		orgPrivSql = "";
	}
	var branchPrivSql = " and branchCode in "+branchPriv;
	if(${herpType=="S1"}||branchPriv.indexOf("select") != -1 || branchPriv.indexOf("SELECT") != -1){
		branchPrivSql = "";
	}
	var extra1Str = " leaf=true and disabled=false and departmentId !='XT' "+orgPrivSql+branchPrivSql+" and ( ";
	jQuery("#specialSource_deptid2_departmentName").autocomplete(
			"autocomplete",
			{
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
								data :data[i].departmentId + ","
									<c:if test="${herpType == 'M'}" >
										+ data[i].org.orgname + ","
									</c:if>
									<c:if test="${herpType == 'S2'}" >
										+ data[i].branch.branchName + ","
									</c:if>
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
					extra1 :extra1Str,
					extra2 : ")"
				},
				formatItem : function(row) {
					return dropId(row);
				},
				formatResult : function(row) {
					return dropId(row);
				}
			});
	jQuery("#specialSource_deptid2_departmentName").result(function(event, row, formatted) {
		if (row == "没有结果") {
			return;
		}
		jQuery("#specialSource_deptid2_departmentId").attr("value", getId(row));
		//alert(jQuery("#specialSource_deptId_departmentId").attr("value"));
	});
	
})
jQuery(function() {
	var orgPriv = "${fns:u_writeDPSql('org_dp')}";
	var branchPriv = "${fns:u_writeDPSql('branch_dp')}";
	var orgPrivSql = "and orgCode in "+orgPriv;
	if(orgPriv.indexOf("select") != -1 || orgPriv.indexOf("SELECT") != -1){
		orgPrivSql = "";
	}
	var branchPrivSql = " and branchCode in "+branchPriv;
	if(${herpType=="S1"}||branchPriv.indexOf("select") != -1 || branchPriv.indexOf("SELECT") != -1){
		branchPrivSql = "";
	}
	var extra1Str = " leaf=true and disabled=false and departmentId !='XT' "+orgPrivSql+branchPrivSql+" and ( ";
	jQuery("#specialSource_deptid3_departmentName").autocomplete(
			"autocomplete",
			{
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
								data :data[i].departmentId + ","
									<c:if test="${herpType == 'M'}" >
										+ data[i].org.orgname + ","
									</c:if>
									<c:if test="${herpType == 'S2'}" >
										+ data[i].branch.branchName + ","
									</c:if>
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
					extra1 : extra1Str,
					extra2 : ")"
				},
				formatItem : function(row) {
					return dropId(row);
				},
				formatResult : function(row) {
					return dropId(row);
				}
			});
	jQuery("#specialSource_deptid3_departmentName").result(function(event, row, formatted) {
		if (row == "没有结果") {
			return;
		}
		jQuery("#specialSource_deptid3_departmentId").attr("value", getId(row));
		//alert(jQuery("#specialSource_deptId_departmentId").attr("value"));
	});
	
})
</script>





				