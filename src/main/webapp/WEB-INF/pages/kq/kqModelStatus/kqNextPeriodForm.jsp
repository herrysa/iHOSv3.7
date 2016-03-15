<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function(){
		jQuery('#kqNextPeriodForm_saveLink').click(function() {
			var nextPeriod = jQuery("#kqNextPeriod_value").val();
			jQuery.ajax({
				url: 'kqModelStatusNextPeriodCheck',
				data: {nextPeriod:nextPeriod},
				type: 'post',
				dataType: 'json',
				async:true,
				error: function(data){
					alertMsg.error("系统错误!");
				},
				success: function(data){
					if(data.message){
						var url = "kqModelStatusClose?navTabId=${navTabId}&id=${id}&oper=closed";
						alertMsg.confirm(data.message+"确认继续结账？", {
													okCall : function() {
														jQuery("#kqNextPeriodForm").attr("action",url);
														jQuery("#kqNextPeriodForm").submit();
													}
												});
					}else{
						var url = "kqModelStatusClose?navTabId=${navTabId}&id=${id}&oper=closed&nextPeriod="+nextPeriod;
						alertMsg.confirm("确认结账？", {
													okCall : function() {
														jQuery("#kqNextPeriodForm").attr("action",url);
														jQuery("#kqNextPeriodForm").submit();
													}
												});
					}
				}
			});	
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="kqNextPeriodForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<label>下一次期间</label>
					<input id="kqNextPeriod_value"  value="${nextPeriod}" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}" class="required">
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" id="kqNextPeriodForm_saveLink"><s:text name="button.save" /></button>
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





