<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="sourcecostDetail.title" />
</title>
<meta name="heading"
	content="<fmt:message key='sourcepayinDetail.heading'/>" />

<script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />


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
		var extra1Str = " leaf=true and departmentId !='XT' "+orgPrivSql+branchPrivSql+" and ( ";
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
											+ data[i].branchName + ","
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
									result : data[i].name,
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
		jQuery("#departmentIdCForm").result(function(event, row, formatted) {
			if (row.data == "没有结果") {
				return;
			}
			jQuery("#deptIdForm").attr("value", getId(row.data));
			var rule = "${sourcecostRule}";
			/* if(rule == "0") {
				jQuery("#sourcecost_costOrgForm").val(row.orgName);
			} */
			if(rule == "0") {
				jQuery("#sourcecost_costOrgForm_id").val(row.orgCode);
				jQuery("#sourcecost_cbBranchForm").val(row.branchName);
				jQuery("#sourcecost_cbBranchForm_id").val(row.branchCode);
			}
			//alert(jQuery("#zxDeptId").attr("value"));
		});
	})
	jQuery(function() {
		jQuery("#costItemIdCForm").autocomplete(
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
		jQuery("#costItemIdCForm").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#costItemIdForm").attr("value", getId(row));
			//alert(jQuery("#costItemId").attr("value"));
		});
	})

</script>
<script>
	jQuery(document)
			.ready(
					function() {
						jQuery('#cancellink').click(function() {
							window.close();
						});
						jQuery('#${random}savelink')
								.click(
										function() {
											/* var costOrgCode = jQuery("#sourcecost_costOrgForm_id").val();
											if(!costOrgCode) {
												jQuery("#sourcecost_costOrgForm").val("");
											} */
											var cbBranchCode = jQuery("#sourcecost_cbBranchForm_id").val();
											if(!cbBranchCode) {
												jQuery("#sourcecost_cbBranchForm").val("");
											}
											jQuery("#sourcecostForm").attr("action","saveSourcecost?popup=true&navTabId="+'${navTabId}');
											jQuery("#sourcecostForm").submit();
										});
						jQuery('#continuelink')
								.click(
										function() {
											/* var costOrgCode = jQuery("#sourcecost_costOrgForm_id").val();
											if(!costOrgCode) {
												jQuery("#sourcecost_costOrgForm").val("");
											} */
											var cbBranchCode = jQuery("#sourcecost_cbBranchForm_id").val();
											if(!cbBranchCode) {
												jQuery("#sourcecost_cbBranchForm").val("");
											}
											jQuery("#sourcecostForm").attr("action","saveSourcecost?popup=true&continueNew=true&navTabId="+'${navTabId}');
											jQuery("#sourcecostForm").submit();
											//var action = sourcecostForm.action = "saveSourcecost?popup=true&continueNew=true";
											//sourcecostForm.submit;
										});
						var s1 = jQuery("#costItemIdCForm");
						if (s1.attr("value") == null || s1.attr("value") == "")
							s1.attr("value", "拼音/汉字/编码");

						var s2 = jQuery("#departmentIdCForm");
						if (s2.attr("value") == null || s2.attr("value") == "")
							s2.attr("value", "拼音/汉字/编码");
						
						var sourcecostRule = "${sourcecostRule}";
						if(sourcecostRule == '1') {
							/* var sql = "select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT'";
							var orgPriv = "${fns:u_writeDPSql('org_dp')}";	
							if(orgPriv) {
								sql += " and orgCode in " + orgPriv;
							} else {
								sql += " and 1=2";
							}
							sql += " ORDER BY orderCol"; 
							jQuery("#sourcecost_costOrgForm").treeselect({
								optType : "single",
								dataType : 'sql',
								sql : sql,
								exceptnullparent : true,
								lazy : false,
								minWidth : '180px',
								selectParent : false
							}); */
							/* var sql = "select branchCode id,branchName name, '-1' parent ,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,branchCode orderCol from t_branch where disabled=0 and branchCode<>'XT'";
							var branchPriv = "${fns:u_writeDPSql('cbdata_branch')}";
							if(branchPriv) {
								sql += " and  branchCode in "+branchPriv;
							} else {
								sql += " and 1=2 ";
							}
							sql += " order by orderCol ";
							jQuery("#sourcecost_cbBranchForm").treeselect({
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
							jQuery("#sourcecost_cbBranch_div").hide();
						} */
					});
	function sourcecoseFormCallBack(json){
		var urlsc = "sourcecostGridList?filter_GES_checkPeriod=${currentPeriod}&filter_LES_checkPeriod=${currentPeriod}";
		var cbBranchDP = "${fns:u_readDPSql('cbdata_branch')}";
		var operatorId = "${fns:userContextParam('loginPersonId')}";
		var sqlString = "sourceCostId IN (SELECT t.sourceCostId FROM t_sourcecost t WHERE t.operatorId = '" + operatorId +"'";
			sqlString += " OR t.cbBranchCode in " + cbBranchDP;
			sqlString += " )";
		urlsc += "&filter_SQS_sourceCostId="+sqlString;
		//var url = "sourcepayinGridList?filter_INL_sourcePayinId=";
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if (json.navTabId){
				//navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
				
				//url += addedIds;
				jQuery('#'+json.navTabId).jqGrid('setGridParam',{url:urlsc,page:1}).trigger("reloadGrid");
				if ("closeCurrent" != json.callbackType) {
				jQuery('#'+json.navTabId+"_temp").jqGrid('setGridParam',{rowNum:10,page:1}).trigger("reloadGrid");
				}
			} else if (json.rel) {
				navTabPageBreak({}, json.rel);
			}
			if ("closeCurrent" == json.callbackType) {
				$.pdialog.closeCurrent();
			}
		}
	}
	jQuery.subscribe('onLoadComplete', function(event, data) {
		//var width = jQuery("#"+data.id+"_div").width();
		//var height = jQuery("#"+data.id+"_div").height();
		/*jQuery("#sourcecost_gridtable_temp").jqGrid('setGridHeight',265); 
		jQuery("#sourcecost_gridtable_temp").jqGrid('setGridWidth', 772); */
		gridResize(null,null,data.id,"single"); 
		jQuery("#gview_sourcecost_gridtable_temp").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
			jQuery(this).find("th").each(function(){
				//jQuery(this).css("vertical-align","middle");
				//jQuery(this).find("div").eq(0).css("margin-top","5px");
				jQuery(this).find("div").eq(0).css("line-height","18px");
			});
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="sourcecostForm" method="post"
			action="saveSourcecost?popup=true" class="pageForm required-validate"
			onsubmit="return validateCallback(this,sourcecoseFormCallBack);">
			<div class="pageFormContent" layoutH="56">

				<s:url id="saveSourcecost" action="saveSourcecost" />
				<s:url id="costItemAuto" action="costItemPinYinJson"
					namespace="/pinyin" />
				<s:url id="deptAuto" action="deptPinYinJson" namespace="/pinyin" />

				<s:hidden key="sourcecost.sourceCostId" />
				<s:hidden key="sourcecost.status"/>
				<s:hidden key="sourcecost.manual" required="false" />
				<s:hidden key="sourcecost.freemark" required="false" maxlength="30" />
				<s:hidden key="sourcecost.processDate" />
				<s:hidden key="sourcecost.deptartment.departmentId" id="deptIdForm" />
				<s:hidden key="sourcecost.costItemId.costItemId" id="costItemIdForm" />
				<div class="unit">
					<s:textfield key="sourcecost.checkPeriod" readonly="true"
						cssClass="required"></s:textfield>
					<s:textfield cssClass="required defaultValueStyle" id="departmentIdCForm" name="departmentIdCForm" maxlength="30"
						label="%{getText('sourcecost.dept')}"
						value="%{sourcecost.deptartment.name}" onfocus="clearInput(this)" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"/>
				</div>
				<div class="unit">
					<s:textfield cssClass="required defaultValueStyle" id="costItemIdCForm" name="costItemIdCForm" maxlength="30"
						label="%{getText('costItem.costItemName')}"
						value="%{sourcecost.costItemId.costItemName}"
						onfocus="clearInput(this)" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"/>
					<s:textfield key="sourcecost.amount" cssClass="required number" />
				</div>
				<div class="unit">
					<s:hidden key="sourcecost.operator.personId"/>
					<s:textfield key="sourcecost.operator.name" required="false" readonly="true"
						maxlength="50" />
					<%-- <label><fmt:message key='sourcecost.ifallot' />:</label>
					<span style="float: left; width: 140px"> <s:checkbox
							key="sourcecost.ifallot" theme="simple"></s:checkbox> </span> --%>
					<s:hidden key="sourcecost.ifallot"></s:hidden>
					<div style="${herpType=='S2'?'':'display:none'}">
					<s:if test="sourcecostRule == 1">
						<label><fmt:message key="sourcecost.costOrg"></fmt:message><fmt:message key="hisOrg.branchName"></fmt:message>:</label>
						<s:select list="branches" listKey="branchCode" listValue="branchName" name="sourcecost.cbBranch.branchCode" value="sourcecost.cbBranch.branchCode" ></s:select>
<%-- 						<input type="text" id="sourcecost_costOrgForm" class="required" value="${sourcecost.costOrg.orgname }"/>
						<input type="hidden" id="sourcecost_costOrgForm_id" name="sourcecost.costOrg.orgCode" value="${sourcecost.costOrg.orgCode }"/> --%>
					</s:if>
					<s:else>
						<label><fmt:message key="sourcecost.costOrg"></fmt:message><fmt:message key="hisOrg.branchName"></fmt:message>:</label>
						<input type="hidden" id="sourcecost_cbBranchForm_id" name="sourcecost.cbBranch.branchCode" value="${sourcecost.cbBranch.branchCode}" />
						<input type="text" id="sourcecost_cbBranchForm" readonly="true" value="${sourcecost.cbBranch.branchName}"/>
 						<input type="hidden" id="sourcecost_costOrgForm_id" name="sourcecost.costOrg.orgCode" value="${sourcecost.costOrg.orgCode }" />
						<%--<input type="text" id="sourcecost_costOrgForm" readonly="true" value="${sourcecost.costOrg.orgname }"/> --%>
					</s:else>
					</div>
				</div>
				<div class="unit">
					<s:textarea key="sourcecost.remarks" required="false"
						maxlength="128" rows="3" cols="61" />
				</div>
				<s:if test="%{sourcecost.sourceCostId==null}">
				<br><br><br><br>
				<div>
					<span style="text-align:left;font-size:12px ">历史记录：</span>
					<s:url id="editurl" action="sourcecostGridList" />
		<s:url id="remoteurl" action="sourcecostGridList" escapeAmp="false">
			<s:param name="filter_GES_checkPeriod" value="%{currentPeriod}"></s:param> 
			<s:param name="filter_LES_checkPeriod" value="%{currentPeriod}"></s:param> 
			<s:param name="filter_EQB_manual" value="1"></s:param>
			<s:param name="filter_SQS_sourceCostId" value="%{sqlStr}"/> 
		</s:url>
		<script>
		</script>
		<div id="sourcecost_gridtable_temp_div" class="grid-wrapdiv" layoutH=210>
		<div id="load_sourcecost_gridtable_temp" class='loading ui-state-default ui-state-active'></div>
					<sjg:grid 
    	id="sourcecost_gridtable_temp" 
    	dataType="json" 
    	gridModel="sourcecosts"
    	
    	href="%{remoteurl}"    	
    	editurl="%{editurl}"
    	rowList="10,15,20,30,40,50,60,70,80,90,100"
    	rowNum="10"
    	rownumbers="true"
    	pager="false" 
    	page="1"
    	pagerButtons="false"
    	pagerInput="false"
    	pagerPosition="right"
		navigator="false"
		navigatorAdd="false"
        navigatorEdit="false"
		navigatorDelete="false"
		navigatorDeleteOptions="{reloadAfterSubmit:true,left:screen.availWidth/4, top:screen.availHeight/4}"   
        navigatorSearch="false"
        navigatorSearchOptions="{multipleSearch:true,  showQuery: true,left:screen.availWidth/4, top:screen.availHeight/4}"
 		navigatorRefresh="false"
    	multiselect="false"
  		multiboxonly="true"
  		resizable="true"
  		onCompleteTopics="onLoadComplete"
  		autowidth="true"
  		draggable="true"
  		sortname="sourceCostId" sortorder="desc"
    >
    <sjg:gridColumn 
    	    name="sourceCostId" 
    	    search="false" 
    	    index="sourceCostId" 
    	    title="%{getText('sourcecost.sourceCostId')}" 
    	    hidden="true"
    	    key="true"
    	    width="100"
    	    />
      <sjg:gridColumn 
 	    name="checkPeriod" 
 	    index="checkPeriod" 
 	    title="%{getText('sourcecost.checkPeriod')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="80"
 	    /> 
   <sjg:gridColumn 
 	    name="remarks" 
 	    index="remarks" 
 	    title="%{getText('sourcecost.remarks')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="100"
 	    /> 	  
 	       <sjg:gridColumn 
 	    name="deptartment.name" 
 	    index="department.name" 
 	    title="%{getText('department.name')}" 
 	    sortable="true"
 	    search="false"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="80"
 	    /> 	  
 	    <sjg:gridColumn 
 	    name="costItemId.costItemName" 
 	    index="costItemId.costItemName" 
 	    title="%{getText('costItem.costItemName')}" 
 	    sortable="true"
 	    search="false"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="80"
 	    />
 	    
 	    
   <sjg:gridColumn 
 	    name="amount" 
 	    index="amount" 
 	    title="%{getText('sourcecost.amount')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		align="right"
 		formatter="currency"
 		formatoptions="{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 2, prefix: '', suffix:'', defaultValue: '0.00'}"
 		width="100"
 	    />
   <sjg:gridColumn 
 	    name="processDate" 
 	    index="processDate" 
 	    title="%{getText('sourcecost.processDate')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		formatter="date"
 		formatoptions="{newformat: 'Y-m-d'}"
 		align="right"
 		width="100"
 	    />
   <sjg:gridColumn 
 	    name="operator.name" 
 	    index="operator.name" 
 	    title="%{getText('sourcecost.operator.name')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		align="right"
 		width="70"
 	    /> 	    
 	      <sjg:gridColumn 
    	name="manual" 
	    index="manual" 
	    title="%{getText('sourcecost.manual')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"
      align="center"
      width="40"
      />   
       	     	       <sjg:gridColumn 
 	    name="costItemId.costItemId" 
 	    index="costItemId.costItemId" 
 	    title="%{getText('costItem.costItemId')}" 
 	    sortable="true"
 	    search="false"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 		width="80"
 	    />
  
<%--     <sjg:gridColumn 
    	name="ifallot" 
	    index="ifallot" 
	    title="%{getText('sourcecost.ifallot')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"/>  --%>



<%--    <sjg:gridColumn 
 	    name="userdefine1" 
 	    index="userdefine1" 
 	    title="%{getText('sourcecost.userdefine1')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="userdefine2" 
 	    index="userdefine2" 
 	    title="%{getText('sourcecost.userdefine2')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="userdefine3" 
 	    index="userdefine3" 
 	    title="%{getText('sourcecost.userdefine3')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    /> --%>
  </sjg:grid>
			</div>
		
	</div>
	</s:if>		
				
			</div>
			<div class="formBar">
				<ul>
					<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink">保存</button>
							</div>
						</div></li>
						<s:if test="%{sourcecost.sourceCostId==null}">
						<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="continuelink">保存并继续</button>
							</div>
						</div></li>
						</s:if>
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


