<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(
		function() {
			jQuery('#savelink').click(function() {
				var isAllValue = jQuery("#isAll").attr("checked");
				if(isAllValue!='checked'){
					var beginTime = jQuery("#beginTime").val();
					var endTime = jQuery("#endTime").val();
					if(beginTime==""){
						alertMsg.error("开始日期不能为空！");
						return;
					}else if(endTime==""){
						alertMsg.error("截止日期不能为空！");
						return;
					}
				}
				var type=jQuery("#type").val();
				var url = "";
				if(type=="b"){
					url = "backupOperateLog";
					jQuery("#deleteAndBackupForm").removeAttr("onsubmit");
				}else{
					url = "deleteOperateLog";
				}
				
					jQuery("#deleteAndBackupForm").attr("action",url+"?popup=true&navTabId=operateLog_gridtable&entityIsNew=${entityIsNew}");
					jQuery("#deleteAndBackupForm").submit();
				});
			jQuery("#isAll").click(function(){
				var thisValue = jQuery(this).attr("checked");
				if(thisValue=="checked"){
					jQuery("#beginTime").attr("disabled",true);
					jQuery("#endTime").attr("disabled",true);
				}else{
					jQuery("#beginTime").attr("disabled",false);
					jQuery("#endTime").attr("disabled",false);
				}
			});
	});
</script>

</head>

<div class="page">
	<div id="deleteAndBackupDiv" class="pageContent">
		<form id="deleteAndBackupForm" action="" class="pageForm required-validate" method="post" onsubmit="return validateCallback(this,formCallBack);">
			<div layoutH="56" class="pageFormContent">
				<div class="unit">
					<label><s:text name='time.begin' />:</label> <input type="text" name="beginTime" id="beginTime" class="requried"
						onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
						id="byLaw_createTime_search">
				</div>
				<div class="unit">
					<label><s:text name='time.end' />:</label> <input type="text" name="endTime" id="endTime" class="requried"
						onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
						id="byLaw_createTime_search">
				</div>
				<div class="unit">
					<input type="hidden" value="${param.type }" name="type" id="type"/>
					<label>全部：</label>
					<input type="checkbox" name="isAll" id="isAll" value="isAll"/>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savelink">
									<s:text name="button.confirm" />
								</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">
									<s:text name="button.cancel" />
								</button>
							</div>
						</div></li>
				</ul>
			</div>
		</form>
	</div>
</div>





