<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet" href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
<head>
<script>
	jQuery(document).ready(function() {
		var s1 = jQuery("#upItem_costItemName_form");
		if (s1.attr("value") == null || s1.attr("value") == "")
			s1.attr("value", "拼音/汉字/编码");
		var s2 = jQuery("#upItem_sbdeptName_form");
		if (s2.attr("value") == null || s2.attr("value") == "")
			s2.attr("value", "拼音/汉字/编码");
		var s3 = jQuery("#upItem_auditDeptName_form");
		if (s3.attr("value") == null || s3.attr("value") == "")
			s3.attr("value", "拼音/汉字/编码");
		
		jQuery('#${random}savelink').click(function() {
			if(s1.attr("value")==="拼音/汉字/编码")
				s1.attr("value","");
			if(s3.attr("value")==="拼音/汉字/编码")
				s3.attr("value","");
			jQuery("#upItemForm").attr("action","saveUpItem?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#upItemForm").submit();
		});
		var contentFrom= document.getElementById("sbDisPlayFrom");
		if($("#upItem_ItemClass_Select").val()=="本科室"){
			$("#upItem_sbdeptName_form").attr("disabled",true);
			$("#upItem_sbdeptId_form").attr("value","");
			$("#upItem_sbdeptName_form").attr("value","拼音/汉字/编码"); 
			contentFrom.style.display ="none";
			
			$("#upItem_sbPersonId").attr("disabled",true);
			jQuery("#upItem_sbPersonId").attr("disabled",true);
			jQuery("#upItem_sbPersonId_id").attr("value","");
			jQuery("#upItem_sbPersonId").attr("value","");
			jQuery("#sbPersonDisPlayFrom").hide();
			
			jQuery("#upItem_isUpOtherDept_div").show();
		} else {
			jQuery("#upItem_isUpOtherDept_div").hide();
		}
		
		/* var sql = "select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,0 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ORDER BY orderCol";
		jQuery("#upItem_costOrg_form").treeselect({
			idId : 'upItem_costOrg_form_id',
			optType : "single",
			dataType : "sql",
			sql : sql,
			exceptnullparent : true,
			lazy : false,
			selectParent : false
			
		}); */
		loadUpItemPersonTree();
	});
	jQuery(function(){
		jQuery("#upItem_costItemName_form").autocomplete("autocomplete",{
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
							data : data[i].costItemId + ","
									+ data[i].costItemId + ","
									+ data[i].cnCode + ","
									+ data[i].costItemName + ":"
									+ data[i].costItemId,
							value : data[i].costItemName,
							result : data[i].costItemName
						};
					}
					return rows;
				}
			},
			extraParams : {
				flag : 2,
				entity : "CostItem",
				cloumnsStr : "costItemId,costItemName,cnCode",
				extra1 : " leaf=true and (",
				extra2 : ")"
			},
			formatItem : function(row) {
				return dropId(row);
			},
			formatResult : function(row) {
				return dropId(row);
			}
		});
		jQuery("#upItem_costItemName_form").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#upItem_costItemId_form").attr("value",
					getId(row));
			//alert(jQuery("#upItem_costItemId_form").attr("value"));
		});
		
	});
	
	function changeClassSelect(value){
		var contentFrom2= document.getElementById("sbDisPlayFrom");
		if(value=="本科室"){
		$("#upItem_sbdeptName_form").attr("disabled",true);
		$("#upItem_sbdeptName_form").attr("value","拼音/汉字/编码"); 
		$("#upItem_sbdeptId_form").attr("value","");
		contentFrom2.style.display ="none";
		
		jQuery("#upItem_sbPersonId").attr("disabled",true);
		jQuery("#upItem_sbPersonId_id").attr("value","");
		jQuery("#upItem_sbPersonId").attr("value","");
		jQuery("#sbPersonDisPlayFrom").hide();
		
		jQuery("#upItem_isUpOtherDept_div").show();
		}else{
		contentFrom2.style.display ="block";
		$("#upItem_sbdeptName_form").attr("disabled",false);
		
		jQuery("#sbPersonDisPlayFrom").show();
		jQuery("#upItem_sbPersonId").attr("disabled",false);
		
		jQuery("#upItem_isUpOtherDept_div").hide();
		}
	}
	function loadUpItemPersonTree() {
		var deptId = jQuery("#upItem_sbdeptId_form").val();
		//console.log(deptId);
		 if(deptId) {
			var sql = "select personId id,name,dept_id parent,personCode orderCol from t_person where disabled = '0' and personId <> 'XT' and dept_id = '" + deptId +"' order by orderCol";
			jQuery("#upItem_sbPersonId").treeselect({
				dataType:"sql",
				optType:"single",
				sql:sql,
				lazy:false,
				exceptnullparent:false,
				callback : {}
			});
		} else {
			return;
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="upItemForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="upItem.id"/>
				</div>
				
				<div class="unit">
				<s:textfield id="upItem_itemName" key="upItem.itemName" name="upItem.itemName" cssClass="required" size="50"/>
				</div>
				
				<div class="unit">
				<s:select id="upItem_ItemClass_Select" key="upItem.itemClass" style="width:133px;" list='#{"全院":"全院","本科室":"本科室"}' cssClass="required"  onchange="changeClassSelect(this.value)" />
				<div id="upItem_isUpOtherDept_div" style="display:inline;"><label><s:text name="upItem.isUpOtherDept"></s:text>:</label>
				<s:checkbox id="upItem_isUpOtherDept" key="upItem.isUpOtherDept" name="upItem.isUpOtherDept" theme="simple"/></div>
				</div>
				
				
				<div class="unit">
				<s:hidden id="upItem_costItemId_form" name="upItem.costItemId.costItemId" value="%{upItem.costItemId.costItemId}"></s:hidden>
				<label><s:text name="upItem.costItemId"></s:text>:</label>
					<span style="float:left;width:140px">
				<s:textfield id="upItem_costItemName_form"  name="upItem_costItemName_form" cssClass="defaultValueStyle" value="%{upItem.costItemId.costItemName}"  size="50" onfocus="clearInput(this)" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"/></span>
				</div>
				
				
				<div class="unit" id="sbDisPlayFrom">
					<s:hidden id="upItem_sbdeptId_form" name="upItem.sbdeptId.departmentId" value="%{upItem.sbdeptId.departmentId}"/>
					
					<label><s:text name="upItem.sbdeptId"></s:text>:</label>
					<span style="float:left;width:140px">
					<s:textfield id="upItem_sbdeptName_form" name="upItem_sbdeptName_form" value="%{upItem.sbdeptId.name}"   cssClass="required defaultValueStyle"   onfocus="clearInput(this)" onblur="setDefaultValue(this)" size="50" onkeydown="setTextColor(this)"></s:textfield>
					</span>
				</div>
				<div class="unit" id="sbPersonDisPlayFrom">
					<input type="hidden" id="upItem_sbPersonId_id" name="upItem.sbPersonId.personId" value="${upItem.sbPersonId.personId }"/>
					<label><s:text name="upItem.sbPersonId"></s:text>:</label>
					<input type="text" id="upItem_sbPersonId" value="${upItem.sbPersonId.name }" size="50"  />
				</div>
				
				<div class="unit">
					<s:hidden id="upItem_auditDeptId_form" key="upItem.auditDeptId.departmentId" value="%{upItem.auditDeptId.departmentId}"/>
					
					<label><s:text name="upItem.auditDeptId"></s:text>:</label>
					<span style="float:left;width:140px">
						<s:textfield id="upItem_auditDeptName_form" value="%{upItem.auditDeptId.name}"   cssClass="required defaultValueStyle"   onfocus="clearInput(this)" onblur="setDefaultValue(this)" size="50" onkeydown="setTextColor(this)"></s:textfield>
					</span>
					
				</div>
				<%-- <div class="unit">
					<label><fmt:message key="sourcecost.costOrg" /><fmt:message key="hisOrg.orgName" />:</label>
					<input type="text"  size="50" id="upItem_costOrg_form"  value="${upItem.costOrg.orgname}" class="required" />
					<input type="hidden" id="upItem_costOrg_form_id" name="upItem.costOrg.orgCode" value="${upItem.costOrg.orgCode }" class="required"  />
				</div> --%>
				<div class="unit">
				<s:textarea id="upItem_remark" key="upItem.remark" name="upItem.remark"  cols="40" rows="2"/>
				</div>
				
				<s:if test="%{upItem.id!=null}">
				<div class="unit">
				<label><s:text name="upItem.disabled"></s:text>:</label>
					<span style="float:left;width:140px">
				<s:checkbox id="upItem_disabled" key="upItem.disabled" name="upItem.disabled" theme="simple"/></span>
				</div>
				</s:if>
			
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
	jQuery("#upItem_sbdeptName_form").autocomplete(
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
	jQuery("#upItem_sbdeptName_form").result(function(event, row, formatted) {
		if (row == "没有结果") {
			return;
		}
		jQuery("#upItem_sbdeptId_form").attr("value", getId(row));
		loadUpItemPersonTree();
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
	jQuery("#upItem_auditDeptName_form").autocomplete(
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
	jQuery("#upItem_auditDeptName_form").result(function(event, row, formatted) {
		if (row == "没有结果") {
			return;
		}
		jQuery("#upItem_auditDeptId_form").attr("value", getId(row));
		//alert(jQuery("#specialSource_deptId_departmentId").attr("value"));
	});
	
});

</script>





