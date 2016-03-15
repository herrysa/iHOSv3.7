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
				jQuery("#${random}_modelStatusCloseCount_monthDiv").show();
			}
			if(jQuery.inArray("季", jzTypeArr)>-1){
				jQuery("#${random}_modelStatusCloseCount_seasonDiv").show();
			}
			if(jQuery.inArray("半年", jzTypeArr)>-1){
				jQuery("#${random}_modelStatusCloseCount_halfYearDiv").show();
			}
			if(jQuery.inArray("年", jzTypeArr)>-1){
				jQuery("#${random}_modelStatusCloseCount_yearDiv").show();
			}
		}
		$.ajax({
		    url: 'getAllClosingPeriods',
		    type: 'post',
		    data:{subSystemCode:"${subSystemCode}"},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		    	var rows = data['closingPeriods'];
		    	afterClosePeriod(rows['年'],"年",rows['showYear']);
		    	afterClosePeriod(rows['半年'],"半年");
		    	afterClosePeriod(rows['季'],"季",rows['showSeason']);
		    	afterClosePeriod(rows['月'],"月");
		    	if(rows['年']=="hasClosed"){
		    		jQuery("#${random}_modelStatusClose_periodYear").val(rows['年hasClosed']);
		    	}
		    	if(rows['半年']=="hasClosed"){
		    		jQuery("#${random}_modelStatusClose_periodHalfYear").val(rows['半年hasClosed']);
		    	}
		    	if(rows['季']=="hasClosed"){
		    		jQuery("#${random}_modelStatusClose_periodSeason").val(rows['季hasClosed']);
		    	}
		    	if(rows['月']=="hasClosed"){
		    		jQuery("#${random}_modelStatusClose_periodMonth").val(rows['月hasClosed']);
		    	}
		    	if(realClosingPeriod(rows['半年']) || realClosingPeriod(rows['季']) || realClosingPeriod(rows['月'])){
		    		jQuery("#${random}_modelStatusClosePeriodYear").hide();
		    		jQuery("#${random}_modelStatusHasCloseMsg_periodYear").html("本年度其他期间尚未结账完全").css("color","red").show();
		    	}else{
		    		
		    	}
		    }
		});
	});
	
	function dynamicSHowClosingStatus(str,msg,button,periodType){
		button.hide();
		if(str=='notInit'){
			msg.text("没有可以结账的"+periodType);
			msg.css("color","red");
		}else if(str=='hasClosed'){
			msg.text("已结账");
			msg.css("color","green");
		}
		msg.show();
	}
	
	function realClosingPeriod(str){
		if(str!='notInit' && str!='hasClosed'){
			return true;
		}
		return false;
	}
	
	function closePeriod(periodType){
		var period = "";
		if("年"==periodType){
			period = jQuery("#${random}_modelStatusClose_periodYear_real").val();
		}else if("半年"==periodType){
			period = jQuery("#${random}_modelStatusClose_periodHalfYear").val();
		}else if("季"==periodType){
			period = jQuery("#${random}_modelStatusClose_periodSeason_real").val();
		}else if("月"==periodType){
			period = jQuery("#${random}_modelStatusClose_periodMonth").val();
			$("#progressBar").text("同步数据中，请稍等...");
		}
		alertMsg.confirm("确认结账?", {
			okCall: function(){
				$.ajax({
				    url: 'closePeriod',
				    type: 'post',
				    data:{periodType:periodType,checkperiod:period,subSystemCode:"${subSystemCode}"},
				    dataType: 'json',
				    aysnc:false,
				    error: function(data){
				    	$("#progressBar").text("数据加载中，请稍等...");
				    },
				    success: function(data){
				    	$("#progressBar").text("数据加载中，请稍等...");
				    	formCallBack(data);
				    	if(data.statusCode==200){
				    		afterClosePeriod(data.checkperiod,periodType,data.showPeriod);
				    		if(data.allClosed){
				    			jQuery("#${random}_modelStatusClosePeriodYear").show();
				    			jQuery("#${random}_modelStatusHasCloseMsg_periodYear").hide();
				    		}
				    		setTimeout(function(){
				    			//window.location.reload(true);
				    			//关闭除了main和当前页的其他页面
				    			var navTabLis= jQuery("ul.navTab-tab li");
				    			  var modelStatusTabid = jQuery("ul.navTab-tab li.selected").attr("tabid");
				    			  jQuery.each(navTabLis, function(){
				    			   var tabid=jQuery(this).attr("tabid");
				    			       　     if(tabid!=modelStatusTabid&&tabid!="main"){
				    			       　  	  navTab.closeTab(tabid);
				    			       　     }  
				    			  　　});  
				    		},1000);
				    	}
				    }
				});
			}
		});
	}
	
	function afterClosePeriod(period,periodType,showPeriod){
		//alert(period,periodType);
		if("年"==periodType){
			if(realClosingPeriod(period)){
				jQuery("#${random}_modelStatusClose_periodYear").val(showPeriod);
	    		jQuery("#${random}_modelStatusClose_periodYear_real").val(period);
			}else{
				dynamicSHowClosingStatus(period,jQuery("#${random}_modelStatusHasCloseMsg_periodYear"),jQuery("#${random}_modelStatusClosePeriodYear"),periodType);
			}
		}else if("半年"==periodType){
			if(realClosingPeriod(period)){
	    		jQuery("#${random}_modelStatusClose_periodHalfYear").val(period);
	    	}else{
	    		dynamicSHowClosingStatus(period,jQuery("#${random}_modelStatusHasCloseMsg_periodHalfYear"),jQuery("#${random}_modelStatusClosePeriodHalfYear"),periodType);
	    	}
		}else if("季"==periodType){
			if(realClosingPeriod(period)){
	    		jQuery("#${random}_modelStatusClose_periodSeason").val(showPeriod);
	    		jQuery("#${random}_modelStatusClose_periodSeason_real").val(period);
	    	}else{
	    		dynamicSHowClosingStatus(period,jQuery("#${random}_modelStatusHasCloseMsg_periodSeason"),jQuery("#${random}_modelStatusClosePeriodSeason"),periodType);
	    	}
		}else if("月"==periodType){
			if(realClosingPeriod(period)){
	    		jQuery("#${random}_modelStatusClose_periodMonth").val(period);
	    	}else{
	    		dynamicSHowClosingStatus(period,jQuery("#${random}_modelStatusHasCloseMsg_periodMonth"),jQuery("#${random}_modelStatusClosePeriodMonth"),periodType);
	    	}
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="${random}_modelStatusCloseForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent">
				<br/>
				<br/>
				<div class="unit">
					<span style="margin-left:58px;font-size:15px;">选择结账期间:</span>
				</div>
				<br/>
				<div class="unit" style="display: none;" id="${random}_modelStatusCloseCount_monthDiv">
					<span style="float: left;margin-left:90px;font-size:15px;">月&nbsp;&nbsp;度:
					</span>
					<span style="float: left;margin-left:12px;margin-right:-60px;margin-top:0px;font-size:15px;">
							<input type="text" id="${random}_modelStatusClose_periodMonth" value="" readonly="readonly" size="10"/>
						</span>
					<div class="buttonActive" style="float:left;margin-left:80px;margin-top:-4px;" id="${random}_modelStatusClosePeriodMonth">
						<div class="buttonContent">
							<button type="button" onclick="closePeriod('月')"><s:text name="结账" /></button>
						</div>
					</div>
					<span id="${random}_modelStatusHasCloseMsg_periodMonth" style="float:left;margin-left:80px;font-size:15px;;display:none">已结账</span>
				</div>
				<div class="unit" style="display: none;" id="${random}_modelStatusCloseCount_seasonDiv">
				<br/><br/><br/>
					<span style="float: left;margin-left:90px;font-size:15px;">季&nbsp;&nbsp;度:
						
					</span>
					<span style="float: left;margin-left:12px;margin-right:-60px;margin-top:0px;font-size:15px;">
							<input type="text" id="${random}_modelStatusClose_periodSeason" readonly="readonly" size="10"/>
							<input type="hidden" id="${random}_modelStatusClose_periodSeason_real"/>
						</span>
					<div class="buttonActive" style="float:left;margin-left:80px;margin-top:-4px;" id="${random}_modelStatusClosePeriodSeason">
						<div class="buttonContent">
							<button type="button" onclick="closePeriod('季')"><s:text name="结账" /></button>
						</div>
					</div>
					<span id="${random}_modelStatusHasCloseMsg_periodSeason" style="float:left;margin-left:80px;font-size:15px;;display:none">已结账</span>
				</div>
				<div class="unit" style="display: none;" id="${random}_modelStatusCloseCount_halfYearDiv">
				<br/><br/><br/>
					<span style="float: left;margin-left:90px;font-size:15px;">半&nbsp;&nbsp;年:
						
					</span>
					<span style="float: left;margin-left:12px;margin-right:-60px;margin-top:0px;font-size:15px;">
							<input type="text" id="${random}_modelStatusClose_periodHalfYear" value="" readonly="readonly" size="10"/>
						</span>
					<div class="buttonActive" style="float:left;margin-left:80px;margin-top:-4px;" id="${random}_modelStatusClosePeriodHalfYear">
						<div class="buttonContent">
							<button type="button" onclick="closePeriod('半年')"><s:text name="结账" /></button>
						</div>
					</div>
					<span id="${random}_modelStatusHasCloseMsg_periodHalfYear" style="float:left;margin-left:80px;font-size:15px;;display:none">已结账</span>
				</div>
				<div class="unit" style="display: none;" id="${random}_modelStatusCloseCount_yearDiv">
				<br/><br/><br/>
					<span style="float: left;margin-left:90px;font-size:15px;">年&nbsp;&nbsp;度:
						
					</span>
					<span style="float: left;margin-left:12px;margin-top:0px;margin-right:-60px;font-size:15px;">
							<input type="text" id="${random}_modelStatusClose_periodYear" value="" readonly="readonly" size="10"/>
							<input type="hidden" id="${random}_modelStatusClose_periodYear_real"/>
						</span>
					<div class="buttonActive" style="float:left;margin-left:80px;margin-top:-4px" id="${random}_modelStatusClosePeriodYear">
						<div class="buttonContent">
							<button type="button" onclick="closePeriod('年')"><s:text name="结账" /></button>
						</div>
					</div>
					<span id="${random}_modelStatusHasCloseMsg_periodYear" style="float:left;margin-left:80px;font-size:15px;display:none">已结账</span>
				</div>
			</div>
		</form>
	</div>
</div>





