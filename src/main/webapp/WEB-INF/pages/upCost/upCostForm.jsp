<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	var deptAndPersonSql = "";
	//var currentOrgModel = "${fns:userContextParam('currentOrgModel')}";
	var herpType = "${fns:getHerpType()}";
	deptAndPersonSql = "select personId id,name,dept_id parent ,0 isparent,'/scripts/zTree/css/zTreeStyle/img/diy/person.png' icon,personCode orderCol from v_upcost_person where personId<>'XT' and disabled=0 @preplace@ ";
	if(herpType == "S2") {
		var branchPriv = "${fns:u_writeDPSql('branch_dp')}";
		deptAndPersonSql += " and branchCode in " +branchPriv;
	}
	deptAndPersonSql += " UNION SELECT deptId id,name,ISNULL(parentDept_id,orgCode) parent ,1 isparent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol FROM t_department where deptId<>'XT' and disabled=0 @dreplace@";
	if(herpType == "S2") {
		var branchPriv = "${fns:u_writeDPSql('branch_dp')}";
		deptAndPersonSql += " and branchCode in " +branchPriv;
	}
	/* if(currentOrgModel == '2') {
		var orgCode = "${fns:userContextParam('orgCode')}";
		deptAndPersonSql += " and orgCode = '"+orgCode+"'";
	} */
	if("${fns:getHerpType()}" == "M") {
		//if(currentOrgModel == '1') {
			/* var orgCode = "${fns:userContextParam('orgCode')}";
			deptAndPersonSql += " and orgCode = '"+orgCode+"'"; */
			deptAndPersonSql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT'";
		//}
	}
	deptAndPersonSql += " ORDER BY orderCol";
	/* var deptId ,deptIds_cb,selectedPerson,cbDepts,selectedDept,upCostType,selectedPersons_cb;
	upCostType = "${upCost.upItemType}";
	deptId = "${deptIds}";
	deptIds_cb = "${deptIds_cb}";
	deptIds_cb = deptIds_cb.replace(/&#039;/g,"'");
	deptIds_cb = deptIds_cb.replace(/&#034;/g,"\"");
	deptIds_cb = eval("("+deptIds_cb+")");
	
	selectedPersons_cb = "${selectedPersons_cb}";
	selectedPersons_cb = selectedPersons_cb.replace(/&#039;/g,"'");
	selectedPersons_cb = selectedPersons_cb.replace(/&#034;/g,"\"");
	selectedPersons_cb = eval("("+selectedPersons_cb+")");
	
	deptId = deptId.replace(/&#039;/g,"'");
	selectedPerson = "${selectedPerson}";
	selectedPerson = selectedPerson.replace(/&#039;/g,"'");
	
	selectedDept = "${selectedDept}";
	selectedDept = selectedDept.replace(/&#039;/g,"'");
	
	if("${upCost.upItemType}"==1){
		makeCbDept('all');
	} */
	if("${upCost.upItemType}"=="1"){
		makeCbDept('all');
	}
	var item_isUpOtherDept = "${upCost.upitemid.isUpOtherDept}";
	
	jQuery(document).ready(function() {
		jQuery('#${random}savelink').click(function() {
			if(jQuery("#deptId_departmentId").val()=='-1'){
				alertMsg.error("请选择上报科室！");
				return;
			}
			
			jQuery("#upCostForm").attr("action","saveUpCost?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#upCostForm").submit();
		});
	});
	function changeDept(obj){
		var seldeptId = jQuery(obj).val();
		if(seldeptId=="-1"){
			makeCbDept('all');
			
		}else{
			makeCbDept(seldeptId);
		}
	}
	function makeCbDept(jjdept){
		var selectFilter = "",selectFilter2="",deptAndPersonSqlTemp="";
		if(jjdept=='all'){
			if(item_isUpOtherDept=="true"){
				selectFilter = " personId not in (SELECT personId from t_UpCost where checkperiod='${upCost.checkperiod}' and upitemid='${upCost.upitemid.id}')";
				deptAndPersonSqlTemp = deptAndPersonSql.replace("@preplace@"," and "+selectFilter);
				deptAndPersonSqlTemp = deptAndPersonSqlTemp.replace("@dreplace@",selectFilter2);
			}else{
				jQuery("#${random}_upCost_personName").val("");
				jQuery("#${random}_upCost_personName_id").val("");
				selectFilter = " dept_id in (SELECT deptId from t_department where jjdeptId in (SELECT deptId FROM t_department where deptId in ${fns:u_readDP('jjdept_dp')})) and personId not in (SELECT personId from t_UpCost where checkperiod='${upCost.checkperiod}' and upitemid='${upCost.upitemid.id}')";
				selectFilter2 = " deptId in (SELECT deptId from t_department where jjdeptId in (SELECT deptId FROM t_department where deptId in ${fns:u_readDP('jjdept_dp')})) ";
				deptAndPersonSqlTemp = deptAndPersonSql.replace("@preplace@"," and "+selectFilter);
				deptAndPersonSqlTemp = deptAndPersonSqlTemp.replace("@dreplace@"," and "+selectFilter2);
			}
			jQuery("#${random}_upCost_personName").treeselect({
				optType:"multi",
				dataType:"sql",
				sql:deptAndPersonSqlTemp,
				exceptnullparent:true,
				lazy:false,
				minWidth:'200px'
			});
			
		}else{
			if(item_isUpOtherDept=="true"){
				//selectedPerson = selectedPersons_cb[jjdept];
				selectFilter = " personId not in (SELECT personId from t_UpCost where checkperiod='${upCost.checkperiod}' and upitemid='${upCost.upitemid.id}' and deptId='"+jjdept+"')";
				deptAndPersonSqlTemp = deptAndPersonSql.replace("@preplace@"," and "+selectFilter);
				deptAndPersonSqlTemp = deptAndPersonSqlTemp.replace("@dreplace@",selectFilter2);
			}else{
				/* var cbDeptsTemp = deptIds_cb[jjdept];
				cbDeptsTemp = cbDeptsTemp.replace(/@/g,",");
				cbDepts = cbDeptsTemp; */
				jQuery("#${random}_upCost_personName").val("");
				jQuery("#${random}_upCost_personName_id").val("");
				
				selectFilter = " dept_id in (SELECT deptId from t_department where jjdeptId in (SELECT deptId FROM t_department where deptId ='"+jjdept+"')) and personId not in (SELECT personId from t_UpCost where checkperiod='${upCost.checkperiod}' and upitemid='${upCost.upitemid.id}')";
				selectFilter2 = " deptId in (SELECT deptId from t_department where jjdeptId in (SELECT deptId FROM t_department where deptId ='"+jjdept+"'))";
				deptAndPersonSqlTemp = deptAndPersonSql.replace("@preplace@"," and "+selectFilter);
				deptAndPersonSqlTemp = deptAndPersonSqlTemp.replace("@dreplace@"," and "+selectFilter2);
				
			}
			jQuery("#${random}_upCost_personName").treeselect({
				optType:"multi",
				dataType:"sql",
				sql:deptAndPersonSqlTemp,
				exceptnullparent:true,
				lazy:false,
				minWidth:'200px'
			});
			
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="upCostForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="upCost.upitemid.id" required="true" cssClass="required"/>
				<s:hidden key="upCost.costitemid.costItemId" required="true" cssClass=""/>
				</div>
				<div class="unit">
				<s:textfield id="upCost_checkperiod" key="upCost.checkperiod" name="upCost.checkperiod" cssClass="				
				
				       " readonly="true"/>
				</div>
				<div class="unit">
				<s:textfield id="upCost_upitemName" key="upCost.upitemName" name="upCost.upitemName" cssClass="				
				
				       " readonly="true"/>
				</div>
				<div class="unit">
				<s:textfield id="upCost_costItemName" key="upCost.costItemName" name="upCost.costItemName" cssClass="				
				
				       " readonly="true"/>
				</div>
				<s:if test="upCost.upItemType==1">
				<div class="unit">
				<label><s:text name="上报科室"></s:text></label>
					
						<s:select id="deptId_departmentId" list="mapDepts" listKey="deptId" listValue="name" key="upCost.deptId.departmentId" headerKey="-1" headerValue="--" theme="simple" onchange="changeDept(this)"></s:select>
				</div>
				<div class="unit">
				<%-- <s:hidden type="hidden" id="${random}_upCost_personId" key="upCost.personId.personId" theme="simple"/> --%>
				<input  type="hidden" id="${random}_upCost_personName_id" name="upCost.personId.personId"/>
				<label>人员姓名:</label>
				<input type="text" id="${random}_upCost_personName" class="required"  style="width:146px">
				</div>
				</s:if>
				<s:else>
				<div class="unit">
				<input type="hidden" id="${random}_upCost_deptName_id" name="upCost.deptId.departmentId" />
				<label>科室:</label>
				<input type="text" id="${random}_upCost_deptName" class="required" style="width:146px">
				</div>
				<script>
				jQuery(function() {
					
					//"select deptId id,name,deptType parent from t_department where  disabled=0 and leaf=1 and deptId not in "+selectedDept+" and deptId<>'XT' UNION select deptTypeName id,deptTypeName name ,'0' parent from t_deptType where  disabled=0 ",
					/* var orgPriv = "${fns:u_writeDPSql('org_dp')}";
					var currentOrgModel = "${fns:userContextParam('currentOrgModel')}";
					var sql = "select d.deptId id,d.name name, case d.deptType when '' THEN '' ELSE d.deptType+d.orgCode end parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department d where leaf=1 and d.disabled = '0' and d.deptId <> 'XT'";
					if(currentOrgModel == '2') {
						var orgCode = "${fns:userContextParam('orgCode')}";
						sql += " and orgCode = '"+orgCode+"'";
					}
					if(orgPriv) {
						sql += " and orgCode in "+orgPriv;
					} else {
						sql += " and 1=2";
					}
						sql +=" union select case gd.deptType when '' THEN '' ELSE gd.deptType+gd.orgCode  end id,case gd.deptType WHEN '' THEN '未分类' ELSE deptType END name,gd.orgCode parent,1 isParent,null icon,deptType orderCol from t_department gd where leaf=1 and gd.disabled = '0'  and gd.deptId <> 'XT' ";
					if(orgPriv) {
							sql += " and orgCode in ${fns:u_readDPSql('org_dp')}";
						} else {
							sql += " and 1=2";
						}
						sql += " GROUP BY deptType,orgCode";
					if(herpType == 'M') {
						if(currentOrgModel == '1') {
							sql +=" union select orgCode id,orgname name,ISNULL(orgCode, '') parent ,1 isParent ,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled = '0' and orgCode <> 'XT'";
							if(orgPriv) {
								sql += " and orgCode in "+orgPriv;
							} else {
								sql += " and 1=2";
							}
						} 
					}
						sql +=" order by orderCol"; */
					var herpType = "${fns:getHerpType()}";
					var sql = "select d.deptId id,d.name name, case d.deptType when '' THEN '' ELSE d.deptType+d.orgCode end parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department d where leaf=1 and d.disabled = '0' and d.deptId <> 'XT' and deptId not in (SELECT deptId from t_UpCost where checkperiod='${upCost.checkperiod}' and upitemid='${upCost.upitemid.id}')";
					if(herpType == "S2") {
						var branchPriv = "${fns:u_writeDPSql('branch_dp')}";
						sql += " and branchCode in " + branchPriv;
					}
					sql +=" union select case gd.deptType when '' THEN '' ELSE gd.deptType+gd.orgCode  end id,case gd.deptType WHEN '' THEN '未分类' ELSE deptType END name,gd.orgCode parent,1 isParent,null icon,deptType orderCol from t_department gd where leaf=1 and gd.disabled = '0'  and gd.deptId <> 'XT' and gd.deptId not in (SELECT deptId from t_UpCost where checkperiod='${upCost.checkperiod}' and upitemid='${upCost.upitemid.id}')";
					if(herpType == "S2") {
						var branchPriv = "${fns:u_writeDPSql('branch_dp')}";
						sql += " and branchCode in " + branchPriv;
					}
					if(herpType == "M") {
						var orgPriv = "${fns:u_writeDPSql('org_dp')}"
						//if(currentOrgModel == '1') {
						sql +=" union select orgCode id,orgname name,ISNULL(orgCode, '') parent ,1 isParent ,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled = '0' and orgCode <> 'XT'";
						if(orgPriv) {
							sql += " and orgCode in "+orgPriv;
						} else {
							sql += " and 1=2";
						}
						//} 
					}
					sql +=" order by orderCol";
					jQuery("#${random}_upCost_deptName").treeselect({
						optType:"multi",
						dataType:"sql",
						sql:sql,
						exceptnullparent:true,
						lazy:false,
						minWidth:'200px'
					});
				});
				</script>
				</s:else>
				<div class="unit">
				<s:textfield id="upCost_amount" key="upCost.amount" name="upCost.amount" cssClass="number			
				
				       " />
				</div>
				<div class="unit">
				<label>状态标记:</label><span style="float: left; width: 140px;line-height:21px">新建</span>
				<s:hidden key="upCost.state" required="true" cssClass="required"/>
				</div>
				<div class="unit">
				<s:textarea id="upCost_remark" key="upCost.remark" name="upCost.remark" cssClass="" cols="40" rows="3"/>
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





