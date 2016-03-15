<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=="view"){
			var hr_time = "${hr_time}";
			readOnlyForm("syncHrDataForm");
			jQuery("#syncHrData_hr_snap_time").val(hr_time);
		}
		
	   var syncType = '${syncType}';
	   if(syncType == '2'){
 		   jQuery('#syncHrData_orgName').addClass('required');
 		   jQuery('#syncHrData_deptNames').addClass('required');
 		//为第二个实体的两个下拉框绑定
//  			var sql =" select id,name,parent,icon from "
//  			+"(select deptId id,name,ISNULL( parentDept_id , '0') parent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon from t_department "
//  			+"	where snapCode = '0' and deptId <> 'XT'   "
//  			+"		union select  case                    "
//  			+"		    num when '0' then '-1'            "
//  			+"		    else  '0'                         "
//  			+"		    end id ,'全选' name, '0' parent ,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon     "
//  			+"		  from (select count(deptId) num from t_department  "
//  			+"		where snapCode = '0' and deptId <> 'XT' ) a)b         "
//  			+"		where id !='-1'         "
			var sql = "select '-1' id,'全选' name,'' parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,'0' actionUrl,'-1' orderCol,'1' disCheckAble union select deptId id,name,ISNULL(parentDept_id,'-1') parent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,disabled actionUrl,deptCode orderCol,'0' disCheckAble  from t_department where snapCode = '0' and deptId <> 'XT'";
			sql += " ORDER BY orderCol ";
 			jQuery("#syncHrData_deptNames").treeselect({
 				optType:"multi",
 				dataType:'sql',
 				sql:sql,
 				exceptnullparent:true,
 				lazy:false,
 				selectParent:true
 			});
 			sql="select orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon from T_Org where disabled=0 and orgCode<>'XT'";
 			sql += " ORDER BY orgCode ";
 			jQuery("#syncHrData_orgName").treeselect({
 				optType:"single",
 				dataType:'sql',
 				sql:sql,
 				exceptnullparent:true,
 				lazy:false,
 				selectParent:true,
 				callback : {
 					afterClick : function() { 
 						var orgCode=jQuery("#syncHrData_orgName_id").val();
 						jQuery("#syncHrData_orgCode").val(orgCode);
 					}
 				}
 			});
	   }

		
		//---------------- 以下是第一个事件的响应事件--------------------------------------------
	});
	
	//------------------ 以下是第二个实体所对应的响应事件--------------------------------------
	function asyncToHrDataHrOrgCheckChanged(){
		jQuery("#syncHrData_orgCode").val("");
		jQuery("#syncHrData_orgName_id").val("");
		jQuery("#syncHrData_orgName").val("");
		var hrOrgCheckChanged = jQuery("#syncHrData_isUseHR").val();
		if(hrOrgCheckChanged == '1'){
			var sql="select orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon from v_hr_org_current where disabled=0 and orgCode<>'XT'";
			sql += " ORDER BY orgCode ";
			jQuery("#syncHrData_orgName").treeselect({
				optType:"single",
				dataType:'sql',
				sql:sql,
				exceptnullparent:true,
				lazy:false,
				selectParent:true,
				callback : {
					afterClick : function() { 
						var orgCode=jQuery("#syncHrData_orgName_id").val();
						jQuery("#syncHrData_orgCode").val(orgCode);
					}
				}
			});
		}else{
			var sql="select orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon from T_Org where disabled=0 and orgCode<>'XT'";
			sql += " ORDER BY orgCode ";
			jQuery("#syncHrData_orgName").treeselect({
				optType:"single",
				dataType:'sql',
				sql:sql,
				exceptnullparent:true,
				lazy:false,
				selectParent:true,
				callback : {
					afterClick : function() { 
						var orgCode=jQuery("#syncHrData_orgName_id").val();
						jQuery("#syncHrData_orgCode").val(orgCode);
					}
				}
			});
		}
	}
	function syncButtonSaved(){
		if(syncType=="1"){
			confirmAsyncHrDatas();
		    var hr_sync_time = jQuery("#syncHrData_hr_snap_time").val();
		}else{
			confirmAsyncToHrData();
			
		}
	}
	
	function confirmAsyncHrDatas(){
		var hisTime = jQuery("#syncHrData_hr_snap_time").val();
		if(!hisTime){
			alertMsg.error("请输入历史时间。");
			return;
		}
		$("#progressBar").text("同步数据中，请稍等...");
		//验证当前日期问题
         $.post("confirmAsyncHrData?hisTime="+hisTime,function(data) {
   				if(data.statusCode==200){
					jQuery("#syncHrDataForm").submit();
   				}else{
	        	 	 formCallBack(data);
   				}	
   				$("#progressBar").text("数据加载中，请稍等...");
   		 });
	}
	function confirmAsyncToHrData(){
		var orgCode = jQuery("#syncHrData_orgCode").val();
		var orgName = jQuery("#syncHrData_orgName").val();
		var orgFromHr = jQuery("#syncHrData_isUseHR").val();
		var deptIds = jQuery("#syncHrData_deptNames_id").val();
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
		$("#progressBar").text("同步数据中，请稍等...");
		alertMsg.confirm("确认将部门与人员同步到HR中该单位下面？", {
   			okCall : function() {
   			 jQuery.ajax({
   		       url: 'confirmAsyncToHrData?orgCode='+orgCode+"&orgName="+orgName+"&orgFromHr="+orgFromHr,
   		       data: {deptIds:deptIds},
   		       type: 'post',
   		       dataType: 'json',
   		       async:true,
   		       error: function(data){
   		    	$("#progressBar").text("数据加载中，请稍等...");
   		       },
   		       success: function(data){
   		    	$("#progressBar").text("数据加载中，请稍等...");
   		    	if(data.statusCode!=200){
   		    		formCallBack(data);
   					return;
   				}
   				jQuery("#syncHrDataForm").submit();
   		    	jQuery("#syncHrData_deptIds").val("");
   		    	jQuery("#syncHrData_deptNaems_id").val("");
   		    	var sql = "select '-1' id,'全选' name,'' parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,0 actionUrl,'-1' orderCol,'1' disCheckAble union select deptId id,name,ISNULL(parentDept_id,'-1') parent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,disabled actionUrl,deptCode orderCol,'0' disCheckAble  from t_department where snapCode = '0' and deptId <> 'XT'";
   		    	sql += " ORDER BY orderCol ";
   		    	jQuery("#syncHrData_deptNames").treeselect({
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
	
</script>
</head>
<div class="page">
	<div class="pageContent">	
		<form id="syncHrDataForm" method="post"	action="savesyncHrData?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" 	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div >
				<s:hidden key="syncHrData.syncHrId"/>
				</div>
				<div class="unit">
				<s:textfield id="syncHrData_syncHrName" key="syncHrData.syncHrName" name="syncHrData.syncHrName" cssClass="required 				
				
				       "/>
				</div>
	<c:if test="${syncType=='1'}">
	<div class="unit">
	 <s:textfield id="syncHrData_hr_snap_time" key="syncHrData.hr_snap_time" name="syncHrData.temparyTime" cssClass="required" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
	</div>
	</c:if>
	<c:if test="${syncType=='2'}">
				<div class="unit">
				<label>单位来源:</label>
				<s:select name="syncHrData.isUseHR" headerKey=""
							list="#{'0':'系统平台单位','1':'人力资源单位' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="20" width="20px" onchange="asyncToHrDataHrOrgCheckChanged()">
				</s:select>
				</div>
				<div class="unit">
				<s:textfield id="syncHrData_orgName" key="syncHrData.orgName" name="syncHrData.orgName" readonly="true"  cssClass="				
				
				       "/>
				<input id="syncHrData_orgName_id" type="hidden"/>
				</div>
				<div class="unit">
				<s:textfield id="syncHrData_orgCode" key="syncHrData.orgCode" readonly="true" name="syncHrData.orgCode" cssClass="				
				
				       " />
				</div>  
			   <div class="unit">
				<s:textarea id="syncHrData_deptNames" key="syncHrData.deptNames" name="syncHrData.deptNames" cssStyle="width:300px;height:35px;"  cssClass="" />
				<input id="syncHrData_deptNames_id" name="syncHrData.deptIds" type="hidden"/>
				</div>    
				</c:if>			
	            <div class="unit">
				<s:textarea id="syncHrData_remarks" key="syncHrData.remarks" name="syncHrData.remarks" rows="4"  cols ="45" cssClass="" readonly="" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li id="syncHrDataFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="syncButtonSave" onclick="syncButtonSaved()"><s:text name='同步'/></button>
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





