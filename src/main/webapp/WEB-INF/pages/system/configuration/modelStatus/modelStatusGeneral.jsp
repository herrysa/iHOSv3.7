<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		var jzType = "${jzType}";
		if(jzType){
			var jzTypeArr = jzType.split(",");
			if(jQuery.inArray("月", jzTypeArr)>-1){
				jQuery("#${random}_modelStatusGeneral_monthDiv").show();
			}
			if(jQuery.inArray("季", jzTypeArr)>-1){
				jQuery("#${random}_modelStatusGeneral_seasonDiv").show();
			}
			if(jQuery.inArray("半年", jzTypeArr)>-1){
				jQuery("#${random}_modelStatusGeneral_halfYearDiv").show();
			}
		}
		$.ajax({
		    url: 'getModelStatusGeneralData',
		    data:{subSystemCode:"${subSystemCode}"},
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		    	var rows = data['generalDatas'];
		    	jQuery("#${random}_modelStatusGeneral_year_using").html(rows['年']);
		    	jQuery("#${random}_modelStatusGeneral_month_using").html(rows['月']);
		    	jQuery("#${random}_modelStatusGeneral_season_using").html(rows['季']);
		    	jQuery("#${random}_modelStatusGeneral_halfYear_using").html(rows['半年']);
		    	
		    	jQuery("#${random}_modelStatusGeneral_month_count").html(rows['月count']);
		    	jQuery("#${random}_modelStatusGeneral_season_count").html(rows['季count']);
		    	jQuery("#${random}_modelStatusGeneral_halfYear_count").html(rows['半年count']);
		    	jQuery("#${random}_modelStatusGeneral_period_count").html(rows['periodCount']);
		    	// jQuery("#${random}_modelStatusGeneral_period_closed").html(rows['月count']);
		    }
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="${random}_modelStatusInitForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent">
				<br/>
				<br/>
				<div class="unit">
					<span style="margin-left:58px;font-size:15px;">
						当前年度是：&nbsp;<span id="${random}_modelStatusGeneral_year_using" style="color:green;font-size:15px;"></span>，
						共有期间&nbsp;&nbsp;<span id="${random}_modelStatusGeneral_period_count" style="color:blue;font-size:15px;"></span>&nbsp;&nbsp;个
						<!-- 已结账&nbsp;&nbsp;<span id="${random}_modelStatusGeneral_period_closed" style="color:red;font-size:15px;"></span>&nbsp;&nbsp;个 -->
					</span>
				</div>
				<br/><br/>
				<div class="unit" style="display: none;" id="${random}_modelStatusGeneral_monthDiv">
					<span style="margin-left:58px;font-size:15px;">
						月度已结账&nbsp;&nbsp;<span id="${random}_modelStatusGeneral_month_count" style="color:red;font-size:15px;"></span>&nbsp;&nbsp;个，正在进行中的是：&nbsp;<span id="${random}_modelStatusGeneral_month_using" style="color:green;font-size:15px;"></span>
					</span>
				</div>
				<br/><br/>
				<div class="unit" style="display: none;" id="${random}_modelStatusGeneral_seasonDiv">
					<span style="margin-left:58px;font-size:15px;" >
						季度已结账&nbsp;&nbsp;<span id="${random}_modelStatusGeneral_season_count" style="color:red;font-size:15px;"></span>&nbsp;&nbsp;个，正在进行中的是：&nbsp;<span id="${random}_modelStatusGeneral_season_using" style="color:green;font-size:15px;"></span>
					</span>
				</div>
				<br/><br/>
				<div class="unit" style="display: none;" id="${random}_modelStatusGeneral_halfYearDiv">
					<span style="margin-left:58px;font-size:15px;" >
						半年已结账&nbsp;&nbsp;<span id="${random}_modelStatusGeneral_halfYear_count" style="color:red;font-size:15px;"></span>&nbsp;&nbsp;个，正在进行中的是：&nbsp;<span id="${random}_modelStatusGeneral_halfYear_using" style="color:green;font-size:15px;"></span>
					</span>
				</div>
				<br/>
			</div>
		</form>
	</div>
</div>





