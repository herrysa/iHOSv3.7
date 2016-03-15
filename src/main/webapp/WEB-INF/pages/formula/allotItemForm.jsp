<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="allotItemDetail.title" /></title>
<meta name="heading"
	content="<fmt:message key='allotItemDetail.heading'/>" />



<script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />

<script type="text/javascript">
	jQuery(function() {
		/* var orgPriv = "${fns:u_writeDPSql('org_dp')}";
		var branchPriv = "${fns:u_writeDPSql('branch_dp')}"; */
		jQuery("#departmentIdC").autocomplete(
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
							var rowData = {data:"没有结果",orgCode:"",branchCode:""};
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
										<c:if test="${herpType == 'M'}">
											+ data[i].org.orgname + ","
										</c:if>
										<c:if test="${herpType == 'S2'}">
											+ data[i].branch.branchName + ","
										</c:if>
											+ data[i].deptCode + ","
											+ data[i].cnCode + ","
											+ data[i].name + ":"
											+ data[i].departmentId,
										branchCode : data[i].branch.branchCode,
										orgCode : data[i].org.orgCode
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
						extra1 : " leaf=true and disabled=false and departmentId !='XT' and (", //and orgCode in "+orgPriv+" and branchCode in "+branchPriv+" 
						extra2 : ")"
					},
					formatItem : function(row) {
						return dropId(row.data);
					},
					formatResult : function(row) {
						return dropId(row.data);
					}
				});
		jQuery("#departmentIdC").result(
				function(event, row, formatted) {
					if (row.data == "没有结果") {
						return;
					}
					jQuery("#allotItemdepartmentdepartmentId").attr("value",
							getId(row.data));
					jQuery("#allotItem_org_orgCode").attr("value",row.orgCode);
					jQuery("#allotItem_branch_branchCode").attr("value",row.branchCode);
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
	function getPinYinSelectCode(selStr) {
		var arr = selStr.split(" ");
		return arr[0];
	}

	jQuery(document).ready(function() {
		var checkValue=jQuery("#allotItemIdCheckd").attr("checked");
		var deptIdsContent=document.getElementById("allotItem_dxDeptIdSelect");
		if(checkValue=="checked"){
			deptIdsContent.style.display ="block";
		}else{
			deptIdsContent.style.display ="none";
		}
		
		var s1 = jQuery("#costItemForm");
		if (s1.attr("value") == null || s1.attr("value") == "")
			s1.attr("value", "拼音/汉字/编码");
		var s2 = jQuery("#departmentIdC");
		if (s2.attr("value") == null || s2.attr("value") == "")
			s2.attr("value", "拼音/汉字/编码");
		jQuery("#costItemForm").autocomplete(
				"autocomplete",
				{
					width : 200,
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
									data : data[i].costItemId + ","
											+ data[i].costItemName + ","
											+ data[i].cnCode + ":"
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
						extra1 : " disabled=false and leaf=1 and (",
						extra2 : ")"
					},
					formatItem : function(row) {
						return dropId(row);
					},
					formatResult : function(row) {
						return dropId(row);
					}
				});
		jQuery("#costItemForm").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#costItemIdForm").attr("value", getId(row));
			//alert(jQuery("#costItemId").attr("value"));
		});
		
		jQuery('#cancellink').click(function() {
			window.close();
		});
		jQuery('#${random}savelink').click(function() {
			jQuery("input").each(function(){
				if(jQuery(this).attr("value")==="拼音/汉字/编码"){
					jQuery(this).attr("value","");
				}
			});
			jQuery("#allotItemForm").attr("action","saveAllotItem?popup=true&navTabId="+'${navTabId}');
			jQuery("#allotItemForm").submit();
		});

		jQuery.subscribe('processDepartmentPin', function(event, data) {
			var chargeid = document.getElementById("departmentPinYin").value;
			chargeid = getPinYinSelectCode(chargeid);
			//alert(chargeid);
			//document.getElementById("allotItem.department.departmentId").value = chargeid;
			jQuery("#allotItemdepartmentdepartmentId").attr("value", chargeid);

		});
		jQuery('input:text:first').focus();
		adjustFormInput("allotItemForm");
	});
	
	jQuery(function() {
		
		//成本类型为“医辅成本”时，部门必选
		jQuery("#allotItem_costType").change(function() {
			var val = jQuery(this).val();
			if(val == "医辅成本") {
				jQuery("#departmentIdC").addClass("required");
			} else {
				jQuery("#departmentIdC").removeClass("required");
			}
				
		});
	});
	
	function clearInput(o) {
		if (o.value == '拼音/汉字/编码') {
			o.value = "";
		}
	}
	function displayDiv(){
		var deptIdsContent=document.getElementById("allotItem_dxDeptIdSelect");
		var vals=$("#allotItemIdCheckd").attr("checked");
		if(vals=="checked"){
			deptIdsContent.style.display ="block";
		}else{
			deptIdsContent.style.display ="none";
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="allotItemForm" method="post"
			action="saveAllotItem?popup=true" class="pageForm required-validate"
			onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">

<s:url id="saveAllotItem" action="saveAllotItem" />
	<input type="hidden" id="allotItem_org_orgCode" name="allotItem.org.orgCode" />
	<input type="hidden" id="allotItem_branch_branchCode" name="allotItem.branch.branchCode" />
	<s:hidden key="allotItem.allotItemId" />
<label><fmt:message key='allotSet.allotSetName' />:</label>
<span class="formspan" style="float:left;width:140px">

	<s:select key="allotSet.allotSetName"
		name="allotItem.allotSet.allotSetId"
		value="%{allotItem.allotSet.allotSetId}"
		id="allotItem.allotSet.allotSetId" cssClass="required" maxlength="50"
		list="allAllotSets" listKey="allotSetId" listValue="allotSetName"
		emptyOption="true" theme="simple"></s:select>
		</span>
	<s:textfield key="allotItem.checkPeriod" cssClass="required" maxlength="6" readonly="true"/>
	</div>
	<div class="unit">
	<s:textfield key="allotItem.allotCost" cssClass="required number" value="%{allotItem.allotCost}"/>

	<%--   		<sj:textfield key="allotItem.calcType" required="false"	maxlength="10"   /> --%>
	<label><fmt:message key='allotItem.calcType' />:</label>

<span class="formspan" style="float:left;width:140px">

	<s:select list="#{'分摊':'分摊','计算':'计算'}" key="allotItem.calcType"
		listKey="key" listValue="value" cssClass="required" theme="simple"/>
</span>
	</div>
	<div class="unit">
	<s:hidden key="allotItem.costItem.costItemId" id="costItemIdForm"/>
	<s:textfield key="allotItem.costItem.costItemName" id="costItemForm" name="costItemForm" value="%{allotItem.costItem.costItemName}" cssClass="defaultValueStyle"
		onfocus="clearInput(this)" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"></s:textfield>
<label><fmt:message key='allotItem.costType' />:</label>

<span class="formspan" style="float:left;width:140px">

	<%--  		<sj:textfield key="allotItem.costType" required="false"	maxlength="10"   /> --%>
	<s:select id="allotItem_costType" 
		list="#{'直接成本':'直接成本','管理成本':'管理成本','医辅成本':'医辅成本','医技成本':'医技成本'}"
		key="allotItem.costType" listKey="key" listValue="value"
		cssClass="required" theme="simple"/>
		</span>
	<%-- <s:url id="deptAuto" action="deptPinYinJson" namespace="/pinyin" /> --%>
	</div>
	<div class="unit">

	<%-- <sj:textfield key="allotItem.department.departmentId" id="allotItem.department.departmentId"/> --%>
	<s:hidden key="allotItem.department.departmentId"
		id="allotItemdepartmentdepartmentId" />
	<s:textfield key="department.name" required="false" id="departmentIdC" cssClass="defaultValueStyle"
		maxlength="30" label="%{getText('department.name')}" value="%{allotItem.department.name}"
		onfocus="clearInput(this)" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"/>
	<label><fmt:message key='allotItem.cjflag' />:</label>

<span class="formspan" style="float:left;width:140px">
	<s:checkbox key="allotItem.cjflag" theme="simple"></s:checkbox>
	</span>
	</div>
	
	
	
<%-- <div class="unit">



	<s:hidden key="allotItem.dxDeptIds" required="false" />
	<s:textfield key="allotItem.freeMark" required="false" maxlength="50" />
	</div>
	<div class="unit">
	<s:textarea key="allotItem.dxDeptNames" required="false"
		maxlength="8000" rows="5" cols="54" />
	</div> --%>
	
	
	
	<div class="unit">
	<label><fmt:message key='allotItem.dxflag' />:</label>
	<span class="formspan" style="float:left;width:140px">
	<s:checkbox id="allotItemIdCheckd" key="allotItem.dxflag" theme="simple" onclick="displayDiv()">
	</s:checkbox>
	</span>
	</div>
	
	<div class="unit" id="allotItem_dxDeptIdSelect"  style="clear:both;border-collapse: collapse;" >
					<s:hidden id="allotItem_dxDeptIds" name="allotItem.dxDeptIds" value="%{allotItem.dxDeptIds}"/>
					<label><s:text name="allotItem.dxDeptIds"></s:text>:</label>
					<span class="formspan" style="float:left;width:140px">
						<s:textarea id="allotItem_dxDeptNames" name="allotItem.dxDeptNames" rows="2" cols="54" value="%{allotItem.dxDeptNames}" readonly="true" title="奖金科室最多分配400个"></s:textarea>
					</span>
					
					<script>
						//addTreeSelect("tree","sync","allotItem_dxDeptNames","allotItem_dxDeptIds","multi",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"jjdepttype",filter:" jjleaf=1",initSelect:"${allotItem.dxDeptIds}",disabledSelect:"${selected_dept_id}",classTable:"KH_Dict_JjDeptType",classTreeId:"jjDeptTypeId",classTreeName:"jjDeptTypeName",classFilter:""});
						//addTreeSelect("tree","sync","allotItem_dxDeptNames","allotItem_dxDeptIds","multi",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"deptType",order:"internalCode asc",filter:" leaf=1 and disabled=0 and deptId<>'XT' ",initSelect:"",disabledSelect:"",classTable:"t_deptType",classTreeId:"deptTypeName",classTreeName:"deptTypeName",classFilter:""});
						var sql = "select v.deptId id,v.name name,v.jjdepttype+v.orgCode parent,0 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol from v_jj_department v where v.disabled = '0' and v.jjleaf = '1'";
							sql +=" union select vj.jjdepttype+vj.orgCode id,k.jjDeptTypeName name ,vj.orgCode parent,1 isParent,null icon, k.jjDeptTypeName orderCol from v_jj_department vj left JOIN KH_Dict_jjDeptType k ON vj.jjdepttype =k.jjDeptTypeId";
							sql +=" union select t.orgCode id,t.orgname name,'1' parent ,1 isParent ,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,t.orgCode orderCol from T_Org t where t.orgCode <> 'XT' and t.disabled = '0' order by orderCol";
						jQuery("#allotItem_dxDeptNames").treeselect({
							idId : "allotItem_dxDeptIds",
							optType : "multi",
							dataType : "sql",
							sql : sql ,
							exceptnullparent:false,
							lazy : false
						});
						</script>
	</div>
				
	
	
	
	
	<div class="unit">
	
	<s:textarea key="allotItem.remark" required="false" maxlength="500"
		rows="5" cols="54" />
</div>
	</div>
			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink">保存</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
							</div>
						</div></li>
				</ul>
			</div>
		</form>
	</div>
</div>