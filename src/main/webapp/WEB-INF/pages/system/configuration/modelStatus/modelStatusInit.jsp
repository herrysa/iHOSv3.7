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
				jQuery("#${random}_modelStatusInit_monthDiv").show();
			}
			if(jQuery.inArray("季", jzTypeArr)>-1){
				jQuery("#${random}_modelStatusInit_seasonDiv").show();
			}
			if(jQuery.inArray("半年", jzTypeArr)>-1){
				jQuery("#${random}_modelStatusInit_halfYearDiv").show();
			}
			if(jQuery.inArray("年", jzTypeArr)>-1){
				jQuery("#${random}_modelStatusInit_yearDiv").show();
			}
		}
		
		
		if("${currentYear}"==""){
			jQuery("#${random}_modelStatusInit_Year").attr("disabled","disabled");
			jQuery("#${random}_modelStatusInit_periodHalfYear").empty();
			jQuery("#${random}_modelStatusInit_periodSeason").empty();
			jQuery("#${random}_modelStatusInit_periodMonth").empty();
			jQuery("#${random}_modelStatusStartPeriodYear").hide();
			jQuery("#${random}_modelStatusStartPeriodHalfYear").hide();
			jQuery("#${random}_modelStatusStartPeriodSeason").hide();
			jQuery("#${random}_modelStatusStartPeriodMonth").hide();
			
		}else{
			jQuery("#${random}_modelStatusInit_Year").val("${currentYear}");
			casUpdateStatus("${currentYear}");
		}
		if("${currentHalfYear}"){
			jQuery("#${random}_modelStatusInit_periodHalfYear").val("${currentHalfYear}");
		}
		if("${currentSeason}"){
			jQuery("#${random}_modelStatusInit_periodSeason").val("${currentSeason}");
		}
		if("${currentMonth}"){
			jQuery("#${random}_modelStatusInit_periodMonth").val("${currentMonth}");
		}
	});
	
	function casUpdateStatus(year){
		$.ajax({
		    url: 'checkPeriodTypeIsStarted?currentYear='+year+"&subSystemCode="+"${subSystemCode}",
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		    	var periods = data['hasInitialized'];
		    	if(periods && periods.indexOf('年')!=-1){
		    		$.each(data.hasInitialized,function(i,val){
			    		dynamicChangeModelStatusEditStatus(val);
			        });
		    	}else{
		    		jQuery("#${random}_modelStatusInit_periodHalfYear").attr("disabled","disabled");
					jQuery("#${random}_modelStatusStartPeriodHalfYear").hide();
					jQuery("#${random}_modelStatusInit_periodSeason").attr("disabled","disabled");
					jQuery("#${random}_modelStatusStartPeriodSeason").hide();
					jQuery("#${random}_modelStatusInit_periodMonth").attr("disabled","disabled");
					jQuery("#${random}_modelStatusStartPeriodMonth").hide();
		    	} 
		    }
		});
	}
	function startPeriod(periodType){
		var period = "";
		if("年"==periodType){
			period = jQuery("#${random}_modelStatusInit_Year").val();
		}else if("半年"==periodType){
			period = jQuery("#${random}_modelStatusInit_periodHalfYear").val();
		}else if("季"==periodType){
			period = jQuery("#${random}_modelStatusInit_periodSeason").val();
		}else if("月"==periodType){
			period = jQuery("#${random}_modelStatusInit_periodMonth").val();
		}
		$.ajax({
		    url: 'startPeriod',
		    type: 'post',
		    data:{periodType:periodType,checkperiod:period,subSystemCode:"${subSystemCode}"},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		    	formCallBack(data);
		    	if(data.statusCode==200){
		    		dynamicChangeModelStatusEditStatus(periodType);
		    		setTimeout(function(){
		    			//window.location.reload(true);
		    			//关闭除了main和当前页的其他页面
		    			var navTabLis= jQuery("ul.navTab-tab li");
		    			  var modelStatusTabid = jQuery("ul.navTab-tab li.selected").attr("tabid");
		    			  jQuery.each(navTabLis, function(){
		    			   var tabid = jQuery(this).attr("tabid");
		    			       　     if(tabid != modelStatusTabid&&tabid!="main"){
		    			       　  	  navTab.closeTab(tabid);
		    			       　     }  
		    			  　　});  
		    		},1000);
		    	}
		    }
		});
	}
	function dynamicChangeModelStatusEditStatus(periodType){
		if("半年"==periodType){
			jQuery("#${random}_modelStatusInit_periodHalfYear").attr("disabled","disabled");
			jQuery("#${random}_modelStatusStartPeriodHalfYear").hide();
			jQuery("#${random}_modelStatusHasStartMsg_periodHalfYear").show();
		}else if("季"==periodType){
			jQuery("#${random}_modelStatusInit_periodSeason").attr("disabled","disabled");
			jQuery("#${random}_modelStatusStartPeriodSeason").hide();
			jQuery("#${random}_modelStatusHasStartMsg_periodSeason").show();
		}else if("月"==periodType){
			jQuery("#${random}_modelStatusInit_periodMonth").attr("disabled","disabled");
			jQuery("#${random}_modelStatusStartPeriodMonth").hide();
			jQuery("#${random}_modelStatusHasStartMsg_periodMonth").show();
		}else if("年"==periodType){
			jQuery("#${random}_modelStatusInit_Year").attr("disabled","disabled");
			jQuery("#${random}_modelStatusStartPeriodYear").hide();
			jQuery("#${random}_modelStatusHasStartMsg_periodYear").show();
			jQuery("#${random}_modelStatusInit_periodHalfYear").removeAttr("disabled");
			jQuery("#${random}_modelStatusStartPeriodHalfYear").show();
			jQuery("#${random}_modelStatusInit_periodSeason").removeAttr("disabled");
			jQuery("#${random}_modelStatusStartPeriodSeason").show();
			jQuery("#${random}_modelStatusInit_periodMonth").removeAttr("disabled");
			jQuery("#${random}_modelStatusStartPeriodMonth").show();
		}
	}
	function initPeriodTypeData(obj){
		var year = obj.value;
		/* jQuery("#${random}_modelStatusInit_periodYear").val(year+"年");
		jQuery("#${random}_modelStatusInit_periodYear_real").val(year); */
		$.ajax({
		    url: 'initPeriodTypeData?year='+year,
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		    	var year = data.currentYear;
		    	jQuery("#${random}_modelStatusInit_periodHalfYear").empty();
				jQuery("#${random}_modelStatusInit_periodHalfYear").append("<option value='"+year+"上半年'>"+year+"上半年</option>");
				jQuery("#${random}_modelStatusInit_periodHalfYear").append("<option value='"+year+"下半年'>"+year+"下半年</option>");
				jQuery("#${random}_modelStatusInit_periodSeason").empty();
				jQuery("#${random}_modelStatusInit_periodSeason").append("<option value='"+year+"-1'>"+year+"年1季度</option>");
				jQuery("#${random}_modelStatusInit_periodSeason").append("<option value='"+year+"-2'>"+year+"年2季度</option>");
				jQuery("#${random}_modelStatusInit_periodSeason").append("<option value='"+year+"-3'>"+year+"年3季度</option>");
				jQuery("#${random}_modelStatusInit_periodSeason").append("<option value='"+year+"-4'>"+year+"年4季度</option>");
				var output = "";
				$.each(data.monthList,function(i,val){
		             output += "<option value='"+val+"'>"+val+"</option>";
		        });
		        if(output!=""){
		            $("#${random}_modelStatusInit_periodMonth").empty().append(output);
		        }
		        casUpdateStatus(year);
		    }
		});
	}
	
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="${random}_modelStatusInitForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent">
				<br/>
				<br/>
				<div class="unit">
					<span style="margin-left:58px;font-size:15px;">选择启用期间:</span>
				</div>
				<br/>
				<div class="" style="display: none;" id="${random}_modelStatusInit_yearDiv">
					<span style="float:left;margin-left:90px;font-size:15px;">年&nbsp;&nbsp;度:
					</span>
					<span style="float:left;margin-left:12px;margin-top:0px;margin-right:-60px;font-size:15px;">
						<select id="${random}_modelStatusInit_Year" style="width:70px;" onchange="initPeriodTypeData(this)">
							<s:iterator value="yearList" id="year">
					        	<option value='<s:property value="#year" />'><s:property value="#year" /></option>
							</s:iterator>
				        </select>
					</span>
					<div class="buttonActive" style="float:left;margin-left:80px;margin-top:-5px;" id="${random}_modelStatusStartPeriodYear">
						<div class="buttonContent">
							<button type="button" onclick="startPeriod('年')"><s:text name="启用" /></button>
						</div>
					</div>
					<span id="${random}_modelStatusHasStartMsg_periodYear" style="float:left;margin-left:80px;color:green;font-size:15px;display:none">已启用</span>
				</div>
				<div class="unit" style="display: none;" id="${random}_modelStatusInit_halfYearDiv">
				<br/><br/><br/>
					<span style="float: left;margin-left:90px;font-size:15px;">半&nbsp;&nbsp;年:
						
					</span>
					<span style="float: left;margin-left:12px;margin-right:-60px;margin-top:0px;font-size:15px;">
						<select id="${random}_modelStatusInit_periodHalfYear" style="width:100px;">
				        	<option value='${currentYear}上半年'>${currentYear}上半年</option>
					        <option value='${currentYear}下半年'>${currentYear}下半年</option>
				        </select>
					</span>
					<div class="buttonActive" style="float:left;margin-left:90px;margin-top:-5px;" id="${random}_modelStatusStartPeriodHalfYear">
						<div class="buttonContent">
							<button type="button" onclick="startPeriod('半年')"><s:text name="启用" /></button>
						</div>
					</div>
					<span id="${random}_modelStatusHasStartMsg_periodHalfYear" style="float:left;margin-left:90px;color:green;font-size:15px;;display:none">已启用</span>
				</div>
				<div class="unit" style="display: none;" id="${random}_modelStatusInit_seasonDiv">
				<br/><br/><br/>
					<span style="float: left;margin-left:90px;font-size:15px;">季&nbsp;&nbsp;度:
						
					</span>
					<span style="float: left;margin-left:12px;margin-right:-60px;margin-top:0px;font-size:15px;">
							<select id="${random}_modelStatusInit_periodSeason" style="width:100px;">
					        	<option value='${currentYear}-1'>${currentYear}年1季度</option>
					        	<option value='${currentYear}-2'>${currentYear}年2季度</option>
					        	<option value='${currentYear}-3'>${currentYear}年3季度</option>
					        	<option value='${currentYear}-4'>${currentYear}年4季度</option>
					        </select>
						</span>
					<div class="buttonActive" style="float:left;margin-left:90px;margin-top:-5px;" id="${random}_modelStatusStartPeriodSeason">
						<div class="buttonContent">
							<button type="button" onclick="startPeriod('季')"><s:text name="启用" /></button>
						</div>
					</div>
					<span id="${random}_modelStatusHasStartMsg_periodSeason" style="float:left;margin-left:90px;color:green;font-size:15px;;display:none">已启用</span>
				</div>
				<div class="unit" style="display: none;" id="${random}_modelStatusInit_monthDiv">
				<br/><br/><br/>
					<span style="float: left;margin-left:90px;font-size:15px;">月&nbsp;&nbsp;度:
						
					</span>
					<span style="float: left;margin-left:12px;margin-right:-60px;margin-top:0px;font-size:15px;">
							<select id="${random}_modelStatusInit_periodMonth" style="width:100px;">
					        	<s:iterator value="monthList" id="month">
						        	<option value='<s:property value="#month" />'><s:property value="#month"/></option>
								</s:iterator>
					        </select>
						</span>
					<div class="buttonActive" style="float:left;margin-left:90px;margin-top:-5px;" id="${random}_modelStatusStartPeriodMonth">
						<div class="buttonContent">
							<button type="button" onclick="startPeriod('月')"><s:text name="启用" /></button>
						</div>
					</div>
					<span id="${random}_modelStatusHasStartMsg_periodMonth" style="float:left;margin-left:90px;color:green;font-size:15px;;display:none">已启用</span>
				</div>
				<br/> 
				<br/>
			</div>
		</form>
	</div>
</div>





