<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#jjDeptMapForm").attr("action","saveJjDeptMap?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			//alert(jQuery("#jjDeptMap_personId").val());
			/* var deptIds=$("#jjDeptMap_dept_Id").val().split(",");
			if(deptIds.length>10){
				alertMsg.error("奖金科室最多分配10个！");
				$("#jjDeptMap_dept_Id").attr("value","");
				$("#jjDeptMap_dept_name").attr("value","");
				return;
			} */
			jQuery("#jjDeptMapForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="jjDeptMapForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<%-- <div class="unit">
				<s:textfield key="jjDeptMap.id" required="true" cssClass="required" onblur="checkId(this,'JjDeptMap','id')"/>
				</div> --%>
				<div class="unit">
				
				<s:hidden id="jjDeptMap_personId" name="jjDeptMap.personId.personId" value="%{jjDeptMap.personId.personId}"/>
					<label><s:text name="jjDeptMap.personId"></s:text>:</label>
					<span style="float:left;width:140px"><!-- onblur="checkId(document.getElementById('jjDeptMap_personId'),'JjDeptMap','personId','该人员已在列表中存在,请重新选择！')" -->
						<s:textfield id="jjDeptMap_person_name" rows="2" cols="50" cssClass="required" value="%{jjDeptMap.personId.name}"></s:textfield>
					</span>
					
					<script>																			   //{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"jjdepttype",filter:"",initSelect:"${jjDeptMap.deptIds}",disabledSelect:"${selected_dept_id}",classTable:"KH_Dict_JjDeptType",classTreeId:"jjDeptTypeId",classTreeName:"jjDeptTypeName",classFilter:""}
						//addTreeSelect("class","sync","jjDeptMap_person_name","jjDeptMap_personId","single",{tableName:"t_person",treeId:"personId",treeName:"name",parentId:"dept_id",filter:" disabled=0 ",classTable:"t_department",classTreeId:"deptId",classTreeName:"name"});
						
						var sql = "SELECT p.personId id ,p.name name,ISNULL(dept_id,dept_id) parent,0 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/person.png' icon,personCode orderCol FROM t_person p where p.disabled = 0 and personId <> 'XT' ";
							sql += " union select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department where disabled=0 and deptId <> 'XT'"
							var herpType = "${fns:getHerpType()}";
							if(herpType == "M") {
								sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ";
							}
							sql += " ORDER BY orderCol "; 
							
						 jQuery("#jjDeptMap_person_name").treeselect({
							idId:"jjDeptMap_personId",
							dataType:"sql",
							optType:"single",
							sql:sql,
							minWidth:"175px",
							exceptnullparent:false,
							lazy:false
						});
					</script>
				
				</div>
				<div class="unit">
				<s:hidden id="" name="jjDeptMap.id" value="%{jjDeptMap.id}"/>
					<s:hidden id="jjDeptMap_dept_Id" name="jjDeptMap.deptIds" value="%{jjDeptMap.deptIds}"/>
					<label><s:text name="jjDeptMap.deptIds"></s:text>:</label>
					<span style="float:left;width:140px">
						<s:textarea id="jjDeptMap_dept_name" name="jjDeptMap.deptNames" rows="2" cols="43" cssClass="required" value="%{jjDeptMap.deptNames}" title="奖金科室最多分配10个"></s:textarea>
					</span>
					
					<script>
						//addTreeSelect("tree","sync","jjDeptMap_dept_name","jjDeptMap_dept_Id","multi",{tableName:"v_jj_department",treeId:"deptId",treeName:"name",parentId:"jjdepttype",filter:" jjleaf=1",initSelect:"${jjDeptMap.deptIds}",disabledSelect:"${selected_dept_id}",classTable:"KH_Dict_JjDeptType",classTreeId:"jjDeptTypeId",classTreeName:"jjDeptTypeName",classFilter:""});
						var sql = "select v.deptId id,v.name name,v.jjdepttype+v.orgCode parent,0 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol from v_jj_department v where v.disabled = '0' and v.jjleaf = '1'"
							sql += " union select vj.jjdepttype+vj.orgCode id,k.jjDeptTypeName name ,vj.orgCode parent,1 isParent,null icon, k.jjDeptTypeName orderCol from v_jj_department vj left JOIN KH_Dict_jjDeptType k ON vj.jjdepttype =k.jjDeptTypeId"
						var herpType = "${fns:getHerpType()}";
						if(herpType == "M") {
							sql += " union select t.orgCode id,t.orgname name,'1' parent ,1 isParent ,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,t.orgCode orderCol from T_Org t where t.orgCode <> 'XT' and t.disabled = '0'";
						}
							sql += " order by orderCol";
						jQuery("#jjDeptMap_dept_name").treeselect({
							idId:"jjDeptMap_dept_Id",
							dataType:"sql",
							optType:"multi",
							sql:sql,
							exceptnullparent:false,
							lazy:false
						});
					</script>
				</div>
				
				
				<div class="unit">
				<s:textarea id="jjDeptMap_remark" key="jjDeptMap.remark" name="jjDeptMap.remark" rows="2" cols="43" required="false" />
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savelink"><s:text name="button.save" /></button>
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





