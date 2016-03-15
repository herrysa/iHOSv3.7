<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function(){
		jQuery('#gzNextPeriodForm_saveLink').click(function() {
			var nextPeriod = jQuery("#gzNextPeriod_value").val();
			jQuery.ajax({
				url: 'gzModelStatusNextPeriodCheck',
				data: {nextPeriod:nextPeriod},
				type: 'post',
				dataType: 'json',
				async:true,
				error: function(data){
					alertMsg.error("系统错误!");
				},
				success: function(data){
					if(data.message){
						var url = "gzModelStatusClose?navTabId=${navTabId}&id=${id}&oper=closed";
						url = encodeURI(url);
						alertMsg.confirm(data.message+"确认继续结账？", {
													okCall : function() {
														jQuery("#gzNextPeriodForm").attr("action",url);
														jQuery("#gzNextPeriodForm").submit();
													}
												});
					}else{
						var url = "gzModelStatusClose?navTabId=${navTabId}&id=${id}&oper=closed&nextPeriod="+nextPeriod;
						url = encodeURI(url);
						alertMsg.confirm("确认结账？", {
													okCall : function() {
														jQuery("#gzNextPeriodForm").attr("action",url);
														jQuery("#gzNextPeriodForm").submit();
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
		<form id="gzNextPeriodForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<label>下一次期间</label>
					<input id="gzNextPeriod_value"  value="${nextPeriod}" onclick="javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}" class="required">
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" id="gzNextPeriodForm_saveLink"><s:text name="button.save" /></button>
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





