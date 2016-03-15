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
				jQuery("#${random}_modelStatusAntiCount_monthDiv").show();
			}
			if(jQuery.inArray("季", jzTypeArr)>-1){
				jQuery("#${random}_modelStatusAntiCount_seasonDiv").show();
			}
			if(jQuery.inArray("半年", jzTypeArr)>-1){
				jQuery("#${random}_modelStatusAntiCount_halfYearDiv").show();
			}
			if(jQuery.inArray("年", jzTypeArr)>-1){
				jQuery("#${random}_modelStatusAntiCount_yearDiv").show();
			}
		}
		$.ajax({
		    url: 'getAllAntiPeriods',
		    data:{subSystemCode:"${subSystemCode}"},
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		    	var rows = data['antiPeriods'];
		    	if(rows['年']!='noAnti'){
		    		jQuery("#${random}_modelStatusAnti_periodYear").val(rows['showYear']);
		    		jQuery("#${random}_modelStatusAnti_periodYear_real").val(rows['年']);
		    		if(rows['showNot']){
		    			jQuery("#${random}_modelStatusAntiPeriodYear").hide();
		    			jQuery("#${random}_modelStatusNotClosedMsg_periodYear").html(rows['currentYear']+"年没有已结账的期间时才可以反结账").show();
		    		}else{
		    			//下边三个提示年度已结账，隐藏按钮
			    		 var msg = "本年度已结账";
			    		jQuery("#${random}_modelStatusNotClosedMsg_periodHalfYear").html(msg).show();
			    		jQuery("#${random}_modelStatusNotClosedMsg_periodSeason").html(msg).show();
			    		jQuery("#${random}_modelStatusNotClosedMsg_periodMonth").html(msg).show();
			    		
			    		jQuery("#${random}_modelStatusAntiPeriodHalfYear").hide();
			    		jQuery("#${random}_modelStatusAntiPeriodSeason").hide();
			    		jQuery("#${random}_modelStatusAntiPeriodMonth").hide();
		    		}
		    		
		    		dynamicShowAntiModelStatusData("半年",rows['半年']);
    				dynamicShowAntiModelStatusData("季",rows['季'],rows['showSeason']);
    				dynamicShowAntiModelStatusData("月",rows['月']);
		    	}else{
		    		jQuery("#${random}_modelStatusAnti_periodYear").val("");
		    		jQuery("#${random}_modelStatusAntiPeriodYear").hide();
		    		jQuery("#${random}_modelStatusNotClosedMsg_periodYear").html("没有可以反结账的年").show();
		    		dynamicShowAntiModelStatusData("半年",rows['半年']);
    				dynamicShowAntiModelStatusData("季",rows['季'],rows['showSeason']);
    				dynamicShowAntiModelStatusData("月",rows['月']);
		    	}
				
		    }
		});
	});
	
	function antiPeriod(periodType){
		var period = "";
		if("年"==periodType){
			period = jQuery("#${random}_modelStatusAnti_periodYear_real").val();
		}else if("半年"==periodType){
			period = jQuery("#${random}_modelStatusAnti_periodHalfYear").val();
		}else if("季"==periodType){
			period = jQuery("#${random}_modelStatusAnti_periodSeason_real").val();
		}else if("月"==periodType){
			period = jQuery("#${random}_modelStatusAnti_periodMonth").val();
		}
		alertMsg.confirm("确认反结账?", {
			okCall: function(){
				$.ajax({
				    url: 'antiPeriod',
				    type: 'post',
				    data:{periodType:periodType,checkperiod:period,subSystemCode:"${subSystemCode}"},
				    dataType: 'json',
				    aysnc:false,
				    error: function(data){
				        
				    },
				    success: function(data){
				    	formCallBack(data);
				    	if(data.statusCode==200){
				    		if("年"==periodType){
			    				jQuery("#${random}_modelStatusAnti_periodYear").val("");
			    				jQuery("#${random}_modelStatusAntiPeriodYear").hide();
			    				jQuery("#${random}_modelStatusNotClosedMsg_periodYear").html("没有可以反结账的年").show();
			    				
			    				jQuery("#${random}_modelStatusAntiPeriodHalfYear").show();
			    				jQuery("#${random}_modelStatusAntiPeriodSeason").show();
			    				jQuery("#${random}_modelStatusAntiPeriodMonth").show();
			    				jQuery("#${random}_modelStatusNotClosedMsg_periodHalfYear").hide();
			    				jQuery("#${random}_modelStatusNotClosedMsg_periodSeason").hide();
			    				jQuery("#${random}_modelStatusNotClosedMsg_periodMonth").hide();
			    				
			    				var rows = data['antiPeriods'];
			    				dynamicShowAntiModelStatusData("半年",rows['半年']);
			    				dynamicShowAntiModelStatusData("季",rows['季'],rows['showSeason']);
			    				dynamicShowAntiModelStatusData("月",rows['月']);
				    		}else{
				    			var rows = data['antiPeriods'];
				    			if(rows['showDo']){
				    				jQuery("#${random}_modelStatusAntiPeriodYear").show();
				    				jQuery("#${random}_modelStatusNotClosedMsg_periodYear").hide();
				    			}
				    			dynamicShowAntiModelStatusData(periodType,data.checkperiod,data.showPeriod);
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
	
	function dynamicShowAntiModelStatusData(periodType,period,showPeriod){
		var msg = "没有可以反结账的";
		if("半年"==periodType){
			if(period!='noAnti'){
    			jQuery("#${random}_modelStatusAnti_periodHalfYear").val(period);
			}else{
				jQuery("#${random}_modelStatusAnti_periodHalfYear").val("");
    			jQuery("#${random}_modelStatusAntiPeriodHalfYear").hide();
    			jQuery("#${random}_modelStatusNotClosedMsg_periodHalfYear").html(msg+"半年").show();
			}
		}else if("季"==periodType){
			if(period!='noAnti'){
				jQuery("#${random}_modelStatusAnti_periodSeason").val(showPeriod);
	    		jQuery("#${random}_modelStatusAnti_periodSeason_real").val(period);
			}else{
				jQuery("#${random}_modelStatusAnti_periodSeason").val("");
				jQuery("#${random}_modelStatusAntiPeriodSeason").hide();
    			jQuery("#${random}_modelStatusNotClosedMsg_periodSeason").html(msg+"季").show();
			}
		}else if("月"==periodType){
			if(period!='noAnti'){
				jQuery("#${random}_modelStatusAnti_periodMonth").val(period);
			}else{
				jQuery("#${random}_modelStatusAnti_periodMonth").val("");
				jQuery("#${random}_modelStatusAntiPeriodMonth").hide();
    			jQuery("#${random}_modelStatusNotClosedMsg_periodMonth").html(msg+"月").show();
			}
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="${random}_modelStatusAntiForm" method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent">
				<br/>
				<br/>
				<div class="unit">
					<span style="margin-left:58px;font-size:15px;">选择反结账期间:</span>
				</div>
				<br/>
				<div class="unit" style="display: none;" id="${random}_modelStatusAntiCount_yearDiv">
					<span style="float: left;margin-left:90px;font-size:15px;">年&nbsp;&nbsp;度:
						
					</span>
					<span style="float: left;margin-left:12px;margin-top:0px;margin-right:-60px;font-size:15px;">
							<input type="text" id="${random}_modelStatusAnti_periodYear" value="" readonly="readonly" size="10"/>
							<input type="hidden" id="${random}_modelStatusAnti_periodYear_real"/>
						</span>
					<div class="buttonActive" style="float:left;margin-left:80px;margin-top:-5px" id="${random}_modelStatusAntiPeriodYear">
						<div class="buttonContent">
							<button type="button" onclick="antiPeriod('年')"><s:text name="反结账" /></button>
						</div>
					</div>
					<span id="${random}_modelStatusNotClosedMsg_periodYear" style="float:left;margin-left:80px;color:red;font-size:15px;display:none">未结账</span>
				</div>
				<div class="unit" style="display: none;" id="${random}_modelStatusAntiCount_halfYearDiv">
				<br/><br/><br/>
					<span style="float: left;margin-left:90px;font-size:15px;">半&nbsp;&nbsp;年:
						
					</span>
					<span style="float: left;margin-left:12px;margin-right:-60px;margin-top:0px;font-size:15px;">
							<input type="text" id="${random}_modelStatusAnti_periodHalfYear" value="" readonly="readonly" size="10"/>
						</span>
					<div class="buttonActive" style="float:left;margin-left:80px;margin-top:-5px;" id="${random}_modelStatusAntiPeriodHalfYear">
						<div class="buttonContent">
							<button type="button" onclick="antiPeriod('半年')"><s:text name="反结账" /></button>
						</div>
					</div>
					<span id="${random}_modelStatusNotClosedMsg_periodHalfYear" style="float:left;margin-left:80px;color:red;font-size:15px;;display:none">未结账</span>
				</div>
				<div class="unit" style="display: none;" id="${random}_modelStatusAntiCount_seasonDiv">
				<br/><br/><br/>
					<span style="float: left;margin-left:90px;font-size:15px;">季&nbsp;&nbsp;度:
						
					</span>
					<span style="float: left;margin-left:12px;margin-right:-60px;margin-top:0px;font-size:15px;">
							<input type="text" id="${random}_modelStatusAnti_periodSeason" readonly="readonly" size="10"/>
							<input type="hidden" id="${random}_modelStatusAnti_periodSeason_real"/>
						</span>
					<div class="buttonActive" style="float:left;margin-left:80px;margin-top:-5px;" id="${random}_modelStatusAntiPeriodSeason">
						<div class="buttonContent">
							<button type="button" onclick="antiPeriod('季')"><s:text name="反结账" /></button>
						</div>
					</div>
					<span id="${random}_modelStatusNotClosedMsg_periodSeason" style="float:left;margin-left:80px;color:red;font-size:15px;;display:none">未结账</span>
				</div>
				<div class="unit" style="display: none;" id="${random}_modelStatusAntiCount_monthDiv">
				<br/><br/><br/>
					<span style="float: left;margin-left:90px;font-size:15px;">月&nbsp;&nbsp;度:
						
					</span>
					<span style="float: left;margin-left:12px;margin-right:-60px;margin-top:0px;font-size:15px;">
							<input type="text" id="${random}_modelStatusAnti_periodMonth" value="" readonly="readonly" size="10"/>
						</span>
					<div class="buttonActive" style="float:left;margin-left:80px;margin-top:-5px;" id="${random}_modelStatusAntiPeriodMonth">
						<div class="buttonContent">
							<button type="button" onclick="antiPeriod('月')"><s:text name="反结账" /></button>
						</div>
					</div>
					<span id="${random}_modelStatusNotClosedMsg_periodMonth" style="float:left;margin-left:80px;color:red;font-size:15px;;display:none">未结账</span>
				</div>
			</div>
		</form>
	</div>
</div>






