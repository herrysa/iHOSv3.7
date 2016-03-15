<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		var sql = "select deptId id,name,parentDept_id parent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon  from t_department where snapCode = '0' and deptId <> 'XT'";
		jQuery("#asyncToHrData_dept").treeselect({
			optType:"multi",
			dataType:'sql',
			sql:sql,
			exceptnullparent:true,
			lazy:false,
			selectParent:true
		});
		sql="select orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon from T_Org where disabled=0";
		jQuery("#asyncToHrData_orgName").treeselect({
			optType:"single",
			dataType:'sql',
			sql:sql,
			exceptnullparent:true,
			lazy:false,
			selectParent:true,
			callback : {
				afterClick : function() { 
					var orgCode=jQuery("#asyncToHrData_orgName_id").val();
					jQuery("#asyncToHrData_orgCode").val(orgCode);
				}
			}
		});
	});
	function confirmAsyncToHrData(){
		var orgCode = jQuery("#asyncToHrData_orgCode").val();
		var orgName = jQuery("#asyncToHrData_orgName").val();
		var hrOrgCheckChanged = jQuery("#asyncToHrData_hrOrgCheck").attr("checked");
		var deptIds = jQuery("#asyncToHrData_dept_id").val();
		if(!orgCode){
			alertMsg.error("请输入单位编码。");
			return;
		}
		if(!orgName){
			alertMsg.error("请输入单位名称。");
			return;
		}
		if(!deptIds){
			alertMsg.error("请选择需要同步的部门。");
			return;
		}
		var orgFromHr=0;
		if(hrOrgCheckChanged){
			orgFromHr=1;
		}
		alertMsg.confirm("确认将部门与人员同步到HR中该单位下面？", {
   			okCall : function() {
   			 jQuery.ajax({
   		       url: 'confirmAsyncToHrData?orgCode='+orgCode+"&orgName="+orgName+"&orgFromHr="+orgFromHr,
   		       data: {deptIds:deptIds},
   		       type: 'post',
   		       dataType: 'json',
   		       async:false,
   		       error: function(data){
   		       },
   		       success: function(data){
   		    	 formCallBack(data);
   		    	if(data.statusCode!=200){
   					return;
   				}
   		    	jQuery("#asyncToHrData_dept").val("");
   		    	jQuery("#asyncToHrData_dept_id").val("");
   		    	var sql = "select deptId id,name,parentDept_id parent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon  from t_department where snapCode = '0' and deptId <> 'XT'";
   				jQuery("#asyncToHrData_dept").treeselect({
   					optType:"multi",
   					dataType:'sql',
   					sql:sql,
   					exceptnullparent:true,
   					lazy:false,
   					selectParent:true
   				});
   		       }
   		   });
   		}
   		});		
	}
	function asyncToHrDataHrOrgCheckChanged(){
		jQuery("#asyncToHrData_orgCode").val("");
		jQuery("#asyncToHrData_orgName_id").val("");
		jQuery("#asyncToHrData_orgName").val("");
		var hrOrgCheckChanged = jQuery("#asyncToHrData_hrOrgCheck").attr("checked");
		if(hrOrgCheckChanged){
			var sql="select orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon from v_hr_org_current where disabled=0";
			jQuery("#asyncToHrData_orgName").treeselect({
				optType:"single",
				dataType:'sql',
				sql:sql,
				exceptnullparent:true,
				lazy:false,
				selectParent:true,
				callback : {
					afterClick : function() { 
						var orgCode=jQuery("#asyncToHrData_orgName_id").val();
						jQuery("#asyncToHrData_orgCode").val(orgCode);
					}
				}
			});
		}else{
			var sql="select orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon from T_Org where disabled=0";
			jQuery("#asyncToHrData_orgName").treeselect({
				optType:"single",
				dataType:'sql',
				sql:sql,
				exceptnullparent:true,
				lazy:false,
				selectParent:true,
				callback : {
					afterClick : function() { 
						var orgCode=jQuery("#asyncToHrData_orgName_id").val();
						jQuery("#asyncToHrData_orgCode").val(orgCode);
					}
				}
			});
		}
	}
</script>
<style type="text/css">
	.asyncDataTable{
		border:solid 0px black;
		position:relative;
		left:200px;
		margin-top:100px
	}
	.asyncDataTable td{
		padding:10px
	}
</style>
</head>

<div class="page" >
	<div layoutH="30" style="background:#eef4f5">
		<table class="asyncDataTable">
			<tr>
				<td>
				<label>
					<s:text name='使用HR中单位'/>:
				 	<input id="asyncToHrData_hrOrgCheck" type="checkbox" onclick="asyncToHrDataHrOrgCheckChanged()"/>
			 	</label>
			 	</td>
			</tr>
			<tr>
				<td>
					<label>
						<s:text name='单位名称'/>:
						<input id="asyncToHrData_orgName" type="text" class="Wdate" style="height:15px" readonly="readonly"/>
						<input id="asyncToHrData_orgName_id" type="hidden"/>
					</label>
				</td>
				<td>
					<label>
						<s:text name='单位编码'/>:
						<input id="asyncToHrData_orgCode" type="text" class="Wdate" style="height:15px" readonly="readonly"/>
					</label>&nbsp;&nbsp;
				 </td>
				</tr>
				<tr>
				<td colSpan="2">
					<s:hidden id="asyncToHrData_dept_id"/>
					<label><s:text name="部门"></s:text>:
						<s:textarea id="asyncToHrData_dept" rows="4"  cols="54" cssClass="" readonly="true"></s:textarea>
					</label>
				</td>
				</tr>
			<tr>
			  <td></td>
				<td>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="confirmAsyncToHrData()"><s:text name='同步'/></button>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>