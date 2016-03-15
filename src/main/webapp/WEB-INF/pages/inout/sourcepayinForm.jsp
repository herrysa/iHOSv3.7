<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="sourcepayinDetail.title" /></title>
<meta name="heading"
	content="<fmt:message key='sourcepayinDetail.heading'/>" />
<script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />

<script type="text/javascript">
	function getPinYinSelectCode(selStr) {
		var arr = selStr.split(" ");
		return arr[0];
	}

	jQuery(document).ready(function() {
						jQuery('#cancellink').click(function() {
							window.close();
						});
						jQuery('#${random}savelink').click(
										function() {
											/* var costOrgCode = jQuery("#sourcepayin_costForm_id").val();
											if(!costOrgCode) {
												jQuery("#sourcepayin_costForm").val("");
											} */
											var srBranchCode = jQuery("#sourcepayin_srBranchForm_id").val();
											if(!srBranchCode) {
												jQuery("#sourcepayin_srBranchForm").val("");
											}
											jQuery("input").each(function(){
												if(jQuery(this).attr("value")==="拼音/汉字/编码"){
													jQuery(this).attr("value","");
												}
											});
											//window.location.href="saveSourcepayin?popup=true&navTabId="+'${navTabId}';
											jQuery("#sourcepayinForm").attr("action","saveSourcepayin?popup=true&navTabId="+'${navTabId}'+"&formId=sourcepayinForm");
											//alert(jQuery("#sourcepayinForm").attr("action"));
											jQuery("#sourcepayinForm").submit();
										});
						jQuery('#continuelink').click(
										function() {
											/* var costOrgCode = jQuery("#sourcepayin_costForm_id").val();
											if(!costOrgCode) {
												jQuery("#sourcepayin_costForm").val("");
											} */
											var srBranchCode = jQuery("#sourcepayin_srBranchForm_id").val();
											if(!srBranchCode) {
												jQuery("#sourcepayin_srBranchForm").val("");
											}
											jQuery("input").each(function(){
												if(jQuery(this).attr("value")==="拼音/汉字/编码"){
													jQuery(this).attr("value","");
												}
											});
											var action = sourcepayinForm.action = "saveSourcepayin?popup=true&continueNew=true";
											jQuery("#sourcepayinForm").attr("action","saveSourcepayin?popup=true&continueNew=true&navTabId="+'${navTabId}');
											jQuery("#sourcepayinForm").submit();
										});
						var s1 = jQuery("#departmentIdC1Form");
						if (s1.attr("value") == null || s1.attr("value") == "")
							s1.attr("value", "拼音/汉字/编码");

						var s2 = jQuery("#departmentIdC2Form");
						if (s2.attr("value") == null || s2.attr("value") == "")
							s2.attr("value", "拼音/汉字/编码");

						var s3 = jQuery("#departmentIdC3Form");
						if (s3.attr("value") == null || s3.attr("value") == "")
							s3.attr("value", "拼音/汉字/编码");

						var s4 = jQuery("#departmentIdC4Form");
						if (s4.attr("value") == null || s4.attr("value") == "")
							s4.attr("value", "拼音/汉字/编码");

						var s5 = jQuery("#departmentIdC5Form");
						if (s5.attr("value") == null || s5.attr("value") == "")
							s5.attr("value", "拼音/汉字/编码");

						var s6 = jQuery("#chargeTypeMingCForm");
						if (s6.attr("value") == null || s6.attr("value") == "")
							s6.attr("value", "拼音/汉字/编码");

						var s7 = jQuery("#chargeItemIdCForm");
						if (s7.attr("value") == null || s7.attr("value") == "")
							s7.attr("value", "拼音/汉字/编码");
						var s8 = jQuery("#departmentIdC6Form");
						if (s8.attr("value") == null || s8.attr("value") == "")
							s8.attr("value", "拼音/汉字/编码");
						var s9 = jQuery("#liciIdForm");
						if (s9.attr("value") == null || s9.attr("value") == "")
							s9.attr("value", "0");
						
						//单位树
						var sourcepayinRule = "${sourcepayinRule}";
						if(sourcepayinRule == '2') {
							/* var sql = "select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT'";
							var orgPriv = "${fns:u_writeDPSql('org_dp')}";
							if(orgPriv) {
								sql += " and orgCode in " + orgPriv;
							} else {
								sql += " and 1=2 ";
							}
							sql += " ORDER BY orderCol ";
							jQuery("#sourcepayin_costForm").treeselect({
								optType : "single",
								dataType : 'sql',
								sql : sql,
								exceptnullparent : true,
								lazy : false,
								minWidth : '180px',
								selectParent : false
							});	 */
							/* var sql = "select branchCode id,branchName name,'-1' parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,branchCode orderCol from t_branch where disabled=0 AND branchCode<>'XT'";
							var branchPriv = "${fns:u_writeDPSql('srdata_branch')}";
							if(branchPriv) {
								sql += " and branchCode in " + branchPriv;
							} else {
								sql += " and 1=2 ";
							}
							sql += " ORDER BY orderCol ";
							jQuery("#sourcepayin_srBranchForm").treeselect({
								optType : "single",
								dataType : 'sql',
								sql : sql,
								exceptnullparent : true,
								lazy : false,
								minWidth : '180px',
								selectParent : false
							});	 */
						}	
						
						/* if("${herpType}" != "S2") {
							jQuery("#sourcepayin_srBranch_div").hide();
						} */
						
						//调整input的自适应宽度
						adjustFormInput("sourcepayinForm");
	});
	function vaildeP() {

	}
	function change() {
		var content = document.getElementById("content");
		content.style.display = (content.style.display == "none") ? "block"
				: "none";
		document.getElementById("link1").innerText = (content.style.display == "none") ? "其他信息(展开)"
				: "其他信息(隐藏)";
		if(content.style.display == "none"){
			jQuery("#sourcepayin_gridtable_temp").jqGrid('setGridHeight',245); 
			jQuery("#sourcepayin_gridtable_temp").jqGrid('setGridWidth', 722); 
		} else{
			jQuery("#sourcepayin_gridtable_temp").jqGrid('setGridHeight',150); 
			jQuery("#sourcepayin_gridtable_temp").jqGrid('setGridWidth', 722);
		}
	}
	var addedIds = "";
	function sourcepayinFormCallBack(json){
		var urlF = "sourcepayinGridList?filter_GES_checkPeriod=${currentPeriod}&filter_LES_checkPeriod=${currentPeriod}";
		var sqlString = "";
		if(${herpType=='S2'}){
			var kdBranchDP = "${fns:u_readDPSql('kd_branch_dp')}";
			var zxBranchDP = "${fns:u_readDPSql('zx_branch_dp')}";
			var srBranchDP = "${fns:u_readDPSql('srdata_branch')}";
			var operatorId = "${fns:userContextParam('loginPersonId')}";
			sqlString = "sourcePayinId IN (SELECT t.sourcePayinId FROM t_sourcepayin t WHERE t.operatorId = '" + operatorId + "'";
				sqlString += " OR t.kdBranchCode IN " + kdBranchDP;
				sqlString += " OR t.zxBranchCode IN " + zxBranchDP;
				sqlString += " OR t.srBranchCode IN " + srBranchDP;
				sqlString += " )";
		}
		urlF += "&filter_SQS_sourcePayinId="+sqlString;
		if (json.fieldErrors != null && json.fieldErrors.length !== 0) {
			
			//alert(json.formId);
			alertMsg.error(json2str(json.fieldErrors));
			//$("#"+json.formId).validate().showErrors(json.fieldErrors);
		}else{
		DWZ.ajaxDone(json);
		if (json.statusCode == DWZ.statusCode.ok){
			if (json.navTabId){
				//navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
				
				//url += addedIds;
				jQuery('#'+json.navTabId).jqGrid('setGridParam',{url:urlF,page:1}).trigger("reloadGrid");
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
	}
	jQuery.subscribe('onpayinLoadComplete', function(event, data) {
		//var width = jQuery("#"+data.id+"_div").width();
		//var height = jQuery("#"+data.id+"_div").height();
		/*jQuery("#sourcepayin_gridtable_temp").jqGrid('setGridHeight',255); 
		jQuery("#sourcepayin_gridtable_temp").jqGrid('setGridWidth', 722);*/
		gridResize(null,null,data.id,"single"); 
		jQuery("#gview_sourcepayin_gridtable_temp").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
			jQuery(this).find("th").each(function(){
				//jQuery(this).css("vertical-align","middle");
				//jQuery(this).find("div").eq(0).css("margin-top","5px");
				jQuery(this).find("div").eq(0).css("line-height","18px");
			});
		});
		
		/* if(!(jQuery.browser.msie||jQuery.browser.mozilla)){
			jQuery("#sourcepayin_gridtable_temp").closest(".ui-jqgrid-bdiv").css({'overflow-x':'hidden'});
		} */
		
	});
	
</script>
</head>
<body>
	<div class="page">
		<div class="pageContent">
			<s:form id="sourcepayinForm" method="post"
				action="" class="pageForm required-validate"
			onsubmit="return validateCallback(this,sourcepayinFormCallBack);">
				<div class="pageFormContent" layoutH="56">


					<s:hidden key="sourcepayin.sourcePayinId" />
					<s:hidden key="sourcepayin.status"/>
					<s:hidden key="sourcepayin.manual"  />
					<s:hidden key="sourcepayin.processDate"  />
					<s:hidden key="sourcepayin.freemark"  />
					<s:hidden key="sourcepayin.chargeType.chargeTypeId" id="chargeTypeIdCForm" required="true" />
					<s:hidden key="sourcepayin.kdDept.departmentId" id="kdDeptIdCForm" />
					<s:hidden key="sourcepayin.zxDept.departmentId" id="zxDeptIdCForm" />
					<s:hidden key="sourcepayin.hlDept.departmentId" id="hlDeptIdCForm" />
					<s:hidden key="sourcepayin.kdOrg.orgCode" id="kdOrgCodeForm"></s:hidden>
					<s:hidden key="sourcepayin.zxOrg.orgCode" id="zxOrgCodeForm"></s:hidden>
					<s:hidden key="sourcepayin.kdBranch.branchCode" id="kdBranchCodeForm" />
					<s:hidden key="sourcepayin.zxBranch.branchCode" id="zxBranchCodeForm" />
					<div class="unit">
						<s:textfield key="sourcepayin.checkPeriod"
							value="%{currentPeriod}" disabled="true" cssClass="input-small" />

						<s:textfield  id="departmentIdC3Form" maxlength="30"
							label="%{getText('sourcepayin.kdDept')}"
							name='departmentIdC3Form'
							value="%{sourcepayin.kdDept.name}" onfocus="clearInput(this)"
							cssClass="required defaultValueStyle" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"/>
					</div>
					<div class="unit">
						<s:textfield required="false" id="departmentIdC4Form" name="departmentIdC4Form" maxlength="30"
							label="%{getText('sourcepayin.zxDept')}"
							value="%{sourcepayin.zxDept.name}"  onfocus="clearInput(this)"
							cssClass="required defaultValueStyle" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"/>
						<s:textfield cssClass="required defaultValueStyle" id="chargeTypeMingCForm" name="chargeTypeMingCForm" maxlength="30"
							label="%{getText('chargeType.chargeTypeName')}"
							value="%{sourcepayin.chargeType.chargeTypeName}"
							onfocus="clearInput(this)" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"/>
					</div>
					<div class="unit">
						<label><fmt:message key='sourcepayin.outin' />:</label> 
						<span class="formspan" style="float: left; width: 152px"> <s:select
								key="sourcepayin.outin" name="sourcepayin.outin"
								value="%{sourcepayin.outin}" 
								cssClass="required" maxlength="50" list="dicOutInList"
								listKey="value" listValue="content" emptyOption="true"
								theme="simple" /> 
							</span>
						<s:textfield key="sourcepayin.amount" cssClass="required number"/>
					</div>
					<div class="unit">
						<s:hidden key="sourcepayin.operator.personId"/>
						<s:textfield key="sourcepayin.operator.name" readonly="true"
							/>
							<div style="${herpType=='S2'?'':'display:none'}">
						<s:if test="sourcepayinRule == 2">
							<label><fmt:message key='sourcepayin.costOrg' /><fmt:message key='hisOrg.branchName' />:</label>
							<s:select list="srBranches" listKey="branchCode" listValue="branchName" name="sourcepayin.srBranch.branchCode" value="sourcepayin.srBranch.branchCode" ></s:select>
						</s:if>
						<s:else>
							<label><fmt:message key='sourcepayin.costOrg' /><fmt:message key='hisOrg.branchName' />:</label>
							<input type="hidden" id="sourcepayin_costForm_id" name="sourcepayin.costOrg.orgCode" value="${sourcepayin.costOrg.orgCode }" />
							<input type="text" id="sourcepayin_srBranchForm" readonly="true" value="${sourcepayin.srBranch.branchName }">
							<input type="hidden" id="sourcepayin_srBranchForm_id" name="sourcepayin.srBranch.branchCode"  value="${sourcepayin.srBranch.branchCode }">
						</s:else>
						</div>
					</div>
					<div class="unit">
						<s:textarea key="sourcepayin.remarks" required="false"
							maxlength="128" rows="1" cols="54" 
							cssClass="input-small" />
					</div>
					<div class="unit">
						<a id="link1" href="javascript:change();" style="font-size: 12px">其他信息(展开)</a>
					</div>



					<div id="content" style="display: none">
						<s:hidden key="sourcepayin.jzDept.departmentId" required="true"
							id="jzDeptIdCForm" />
						<s:hidden key="sourcepayin.chargeItemId" required="true"
							id="chargeItemIdSForm" />

						<div class="unit">
							<s:textfield required="false" id="departmentIdC2Form" maxlength="30"
								label="%{getText('sourcepayin.jzDept')}"
								value="%{sourcepayin.jzDept.name}" onfocus="clearInput(this)" 
							cssClass="required defaultValueStyle" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"/>
							<s:textfield key="sourcepayin.kdDoctorid" required="false"
								maxlength="20" cssClass="input-small" />
						</div>
						<div class="unit">
							<s:textfield key="sourcepayin.kdDoctorName" required="false"
								maxlength="50" cssClass="input-small" />

							<s:textfield id="departmentIdC6Form"
								label="%{getText('sourcepayin.hlDept')}"
								value="%{sourcepayin.hlDept.name}"  onfocus="clearInput(this)"
							cssClass="required defaultValueStyle" onblur="setDefaultValue(this)" onkeydown="setTextColor(this)"/>
						</div>
						<div class="unit">
							<s:textfield label="%{getText('sourcepayin.lc')}"
								key="sourcepayin.lici"
								value="%{sourcepayin.lici}" id="liciIdForm" cssClass="number" />
							<s:textfield key="sourcepayin.patientId" required="false"
								maxlength="20" cssClass="input-small" />
						</div>
						<div class="unit">
							<s:textfield key="sourcepayin.patientName" required="false"
								maxlength="50" cssClass="input-small" />
							<label><fmt:message key='sourcepayin.patientType' />
							:</label>
							<span  class="formspan" style="float: left; width: 152px">
							<appfuse:dictionary code="patientType" name="dutyId"
								cssClass="input-small"></appfuse:dictionary>
								</span>

						</div>
					</div>
					<s:if test="%{sourcepayin.sourcePayinId==null}">
					<br><br>
					<div>
					<span style="text-align:left;font-size:12px ">历史记录：</span>
					<s:url id="editurl" action="sourcepayinGridEdit" />
		<s:url id="remoteurl" action="sourcepayinGridList" escapeAmp="false">
			<s:param name="filter_GES_checkPeriod" value="%{currentPeriod}"></s:param> 
			<s:param name="filter_LES_checkPeriod" value="%{currentPeriod}"></s:param> 
			<s:param name="filter_EQB_manual" value="1"></s:param> 
			<s:param name="filter_SQS_sourcePayinId" value="%{sqlStr}" />
			</s:url>
		<div id="sourcepayin_gridtable_temp_div" class="grid-wrapdiv" layoutH=260>
		<div id="load_sourcepayin_gridtable_temp" class='loading ui-state-default ui-state-active'></div>

					<sjg:grid id="sourcepayin_gridtable_temp" gridModel="sourcepayins"
				href="%{remoteurl}" editurl="%{editurl}"
				rowList="10,15,20,30,40,50,60,70,80,90,100" rowNum="10"
				rownumbers="true" pager="false" page="1" pagerButtons="false"
				pagerInput="false" pagerPosition="right" navigator="false"
				navigatorAdd="false" navigatorEdit="false" navigatorDelete="false"
				navigatorDeleteOptions="{reloadAfterSubmit:true,left:screen.availWidth/4, top:screen.availHeight/4}"
				navigatorSearch="false"
				navigatorSearchOptions="{multipleSearch:true,  showQuery: true,left:screen.availWidth/4, top:screen.availHeight/4}"
				navigatorRefresh="false" multiselect="false" multiboxonly="false"
				resizable="true" draggable="true" autowidth="true"
				sortname="sourcePayinId" sortorder="desc" onCompleteTopics="onpayinLoadComplete"
				>
				<sjg:gridColumn name="sourcePayinId" search="false"
					index="sourcePayinId"
					title="%{getText('sourcepayin.sourcePayinId')}" hidden="true"
					formatter="integer" key="true" />
				<sjg:gridColumn name="checkPeriod" index="checkPeriod"
					title="%{getText('sourcepayin.checkPeriod')}" sortable="true"
					search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" 
					width="80"/>

				<sjg:gridColumn name="kdDept.name" index="kdDept.name"
					title="%{getText('sourcepayin.kdDept')}" sortable="true"
					search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" width="115"/>
				<sjg:gridColumn name="zxDept.name" index="zxDept.name"
					title="%{getText('sourcepayin.zxDept')}" search="true"
					searchoptions="{sopt:['eq','ne','cn','bw']}" width="115"/>
				<sjg:gridColumn name="chargeType.chargeTypeName"
					index="chargeType.chargeTypeName"
					title="%{getText('sourcepayin.chargeType')}" search="true"
					searchoptions="{sopt:['eq','ne','cn','bw']}" width="75"/>

				<sjg:gridColumn name="amount" index="amount"
					title="%{getText('sourcepayin.amount')}" sortable="true"
					search="true" searchoptions="{sopt:['eq','ne','cn','bw']}"
					align="right" formatter="currency"
					formatoptions="{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 2, prefix: '', suffix:'', defaultValue: '0.00'}" width="70"/>
				<sjg:gridColumn name="outin" index="outin"
					title="%{getText('sourcepayin.outin')}" sortable="true"
					search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" 
					width="60" align="center"/>
				
				<sjg:gridColumn name="operator.name" index="operator.name"
					title="%{getText('sourcepayin.operator.name')}" sortable="true"
					search="true" searchoptions="{sopt:['eq','ne','cn','bw']}" width="80"/>
				<sjg:gridColumn name="manual" index="manual"
					title="%{getText('sourcepayin.manual')}" sortable="true"
					edittype="checkbox" formatter="checkbox" search="true"
					searchoptions="{sopt:['eq','ne']}" editrules="{required: true}"
					width="50" align="center"/>

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
							</div>
						</li>
						<s:if test="%{sourcepayin.sourcePayinId==null}">
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="continuelink">保存并继续</button>
								</div>
							</div>
						</li>
						</s:if>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
								</div>
							</div></li>
					</ul>
				</div>
			</s:form>
		</div>
	</div>

</body>

<script type="text/javascript">
	jQuery(function() {
		jQuery("#chargeTypeMingCForm").autocomplete(
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
									data : data[i].chargeTypeId + ","
											+data[i].chargeTypeName + ","
											+ data[i].cnCode + ","
											+data[i].chargeTypeId + ":"
											+ data[i].chargeTypeId,
									value : data[i].chargeTypeId,
									result : data[i].chargeTypeName
								};
							}
							return rows;
						}
					},
					extraParams : {
						flag : 2,
						entity : "ChargeType",
						cloumnsStr : "reportmark,chargeTypeId,chargeTypeName,cnCode",
						extra1 : " disabled=false and leaf=true  and (",
						extra2 : ")"
					},
					formatItem : function(row) {
						return dropId(row);
					},
					formatResult : function(row) {
						return dropId(row);
					}
				});
		jQuery("#chargeTypeMingCForm").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#chargeTypeIdCForm").attr("value", getId(row));
			//alert(jQuery("#chargeTypeIdC").attr("value"));
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
		var extra1Str = " leaf=true and departmentId !='XT' and disabled=false "+orgPrivSql+branchPrivSql+" and ( ";
		jQuery("#departmentIdC2Form").autocomplete(
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
										+ data[i].branchName + ","
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
					},
					
				});
		jQuery("#departmentIdC2Form").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#jzDeptIdCForm").attr("value", getId(row));
		});
	})
	jQuery(function() {
		var orgPriv = "${fns:u_writeDPSql('org_dp')}";
		var branchPriv = "${fns:u_writeDPSql('kd_branch_dp')}";
		var orgPrivSql = "and orgCode in "+orgPriv;
		if(orgPriv.indexOf("select") != -1 || orgPriv.indexOf("SELECT") != -1){
			orgPrivSql = "";
		}
		var branchPrivSql = " and branchCode in "+branchPriv;
		if(${herpType=="S1"}||branchPriv.indexOf("select") != -1 || branchPriv.indexOf("SELECT") != -1){
			branchPrivSql = "";
		}
		var extra1Str = " leaf=true and deptClass='医疗' "+orgPrivSql+branchPrivSql+" and ( ";
		jQuery("#departmentIdC3Form").autocomplete(
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
		jQuery("#departmentIdC3Form").result(function(event, row, formatted) {
			if (row.data == "没有结果") {
				return;
			}
			jQuery("#kdDeptIdCForm").attr("value", getId(row.data));
			jQuery("#kdOrgCodeForm").attr("value",row.orgCode);
			jQuery("#kdBranchCodeForm").attr("value",row.branchCode);
			var rule = "${sourcepayinRule}";
			/* if(rule == '0') {
				jQuery("#sourcepayin_costForm").val(row.orgName);
			} */
			if(rule == '0') {
				jQuery("#sourcepayin_costForm_id").val(row.orgCode);
				jQuery("#sourcepayin_srBranchForm_id").val(row.branchCode);
				jQuery("#sourcepayin_srBranchForm").val(row.branchName);
			}
			//alert(jQuery("#kdDeptIdCForm").attr("value"));
		});
	})
	jQuery(function() {
		var orgPriv = "${fns:u_writeDPSql('org_dp')}";
		var branchPriv = "${fns:u_writeDPSql('zx_branch_dp')}";
		var orgPrivSql = "and orgCode in "+orgPriv;
		if(orgPriv.indexOf("select") != -1 || orgPriv.indexOf("SELECT") != -1){
			orgPrivSql = "";
		}
		var branchPrivSql = " and branchCode in "+branchPriv;
		if(${herpType=="S1"}||branchPriv.indexOf("select") != -1 || branchPriv.indexOf("SELECT") != -1){
			branchPrivSql = "";
		}
		var extra1Str = " leaf=true and deptClass in ('医疗','医技') "+orgPrivSql+branchPrivSql+" and ( ";
		jQuery("#departmentIdC4Form").autocomplete(
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
							var rowData = {data:"没有结果",orgName:"",orgCode:"",branchCode:"",branchName:""};
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
		jQuery("#departmentIdC4Form").result(function(event, row, formatted) {
			if (row.data == "没有结果") {
				return;
			}
			jQuery("#zxDeptIdCForm").attr("value", getId(row.data));
			jQuery("#zxOrgCodeForm").attr("value",row.orgCode);
			jQuery("#zxBranchCodeForm").attr("value",row.branchCode);
			var rule = "${sourcepayinRule}";
			/* if(rule == '1') {
				jQuery("#sourcepayin_costForm").val(row.orgName);
			} */
			if(rule == '1') {
				jQuery("#sourcepayin_costForm_id").val(row.orgCode);
				jQuery("#sourcepayin_srBranchForm_id").val(row.branchCode);
				jQuery("#sourcepayin_srBranchForm").val(row.branchName);
			}
			// alert(jQuery("#zxDeptIdCForm").attr("value"));
		});
	})
	jQuery(function() {
		
		jQuery("#departmentIdC5Form").autocomplete(
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
						extra1 : " leaf=true and departmentId !='XT' and disabled=false and (",
						extra2 : ")"
					},
					formatItem : function(row) {
						return dropId(row);
					},
					formatResult : function(row) {
						return dropId(row);
					}
				});
		jQuery("#departmentIdC5Form").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#jzDeptIdCForm").attr("value", getId(row));
			// alert(jQuery("#jzDeptIdCForm").attr("value"));
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
		var extra1Str = " leaf=true and departmentId !='XT' and disabled=false "+orgPrivSql+branchPrivSql+" and ( ";
		jQuery("#departmentIdC6Form").autocomplete(
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
										+ data[i].branchName + ","
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
		jQuery("#departmentIdC6Form").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#hlDeptIdCForm").attr("value", getId(row));
			//alert(jQuery("#hlDeptId").attr("value"));
		});
	})
	jQuery(function() {
		jQuery("#chargeItemIdCForm")
				.autocomplete(
						"autocomplete",
						{
							width : 400,
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
											data : data[i].chargeItemId + ","
													+ data[i].chargeItemName
													+ ","
													+ data[i].chargeItemNo
													+ "," + data[i].price + ":"
													+ data[i].chargeItemId,
											value : data[i].chargeItemId,
											result : data[i].chargeItemId
										};
									}
									return rows;
								}
							},
							extraParams : {
								flag : 2,
								entity : "ChargeItem",
								cloumnsStr : "chargeItemId,chargeItemName,chargeItemNo,price",
								extra1 : "",
								extra2 : ""
							},
							formatItem : function(row) {
								return dropId(row);
							},
							formatResult : function(row) {
								return dropId(row);
							}
						});
		jQuery("#chargeItemIdCForm").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#chargeItemIdSForm").attr("value", getId(row));
			//alert(jQuery("#chargeItemIdSForm").attr("value"));
		});
	})
</script>
