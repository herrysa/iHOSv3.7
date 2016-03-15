<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="commandScript.title" /></title>
<meta name="heading" content="<fmt:message key='commandScript.title'/>" />
<link rel="stylesheet" href="${ctx}/fusionCharts/css/Style.css" type="text/css" />
<script language="JavaScript" src="${ctx}/fusionCharts/JSClass/FusionCharts.js"></script>
<script language="javascript" type="text/javascript" src="${ctx}/scripts/DatePicker/WdatePicker.js"></script>
<%-- <script type="text/javascript" src="${ctx}/farbtastic/farbtastic/farbtastic.js"></script>
<link rel="stylesheet" href="${ctx}/farbtastic/farbtastic/farbtastic.css" type="text/css" />

<script type="text/javascript">
	$(document).ready(function() {
		$('#colorpicker').farbtastic('#color');
	});
</script> --%>
<script type="text/javascript">
/* var maxValue='${maxValue}';
var currentValue='${currentValue}';  */
var color1 = "blue"; //文字的颜色
var color2 = "red"; //转换的颜色
var style ="font-size:20pt;"; //字体大小
var speed = 600; //转换速度 (1000 = 1 秒)
var i = 0;
function changeCharColor(maxValue,currentValue,sss,text) {
	//alert(currentValue);
	var str = "<center><font face=arial style =" + style + "><font color=" + color1 + ">";
	if(i%2==0)
			str += "<font face=arial color=" + color2 + ">" + text + "</span></font>";
	str += "</span></font></span></font></center>";
	jQuery("#"+sss).html(str);
	(i == text.length) ? i=0 : i++;
}

</script>
</head>

<form name="biaoPanForm" action="biaoPanCharts?popup=true"  method="post" validate="false" cssClass="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
<input type="hidden" name="type" value="biaoPan,biaoPan">
<input type="hidden" name="ckey" value="B6,Y508">
<table width="100%" border="1" bordercolor="#15428B" bgcolor="#DCE7F6" cellspacing="0" cellpadding="3" >

	<tr>
		<td  height="40">
			<font color="#15428B" size="16" face="宋体"><b>核算期间：</b>
			<input type="text" id="" name="conditionO" onClick="WdatePicker({dateFmt:'yyyyMM'})" />
			</font>
			<s:submit id="savelink" key="commandScript.submit" method="xuanCharts" cssClass="btn btn-primary" theme="simple" />
	</td>
	</tr>
</table>
</form>
<table width="98%" border="0" cellspacing="0" cellpadding="3">
	 <tr>
	  <td align="center" height="50">${biaoTiS[0]}</td>
	  <td align="center" height="50">${biaoTiS[1]}</td>
	  <td align="center" height="50"></td>
	  </tr>
	  <tr> 
	    <td valign="top" class="text" align="center" > 
	    <div id="chartdiv1" align="center">Chart will load here</div>
	        <script type="text/javascript">
	            var chart = new FusionCharts("${ctx}/fusionCharts/Charts/AngularGauge.swf", "ChartId", "400", "180");
	            chart.setDataXML("${chartStr[0]}" );
	            chart.render("chartdiv1");
	            
	            if((parseFloat(${maxValueS[0]}))<=(parseFloat(${currentValueS[0]}))){
	            	setInterval("changeCharColor('${maxValueS[0]}','${currentValueS[0]}','chartdiv1uu','${biaoZhuS[0]}+${currentValueS[0]}')", speed);
	            }else{
	            	jQuery("#chartdiv1uu").html("<font face=arial color='blue'>'${biaoZhuS[0]}+${currentValueS[0]}'</font>");
	            }
	        </script>
	        
	    </td>
	    <td valign="top" class="text" align="center"> 
	    	<div id="chartdiv2" align="center">Chart will load here</div>
	        <script type="text/javascript">
	            var chart = new FusionCharts("${ctx}/fusionCharts/Charts/AngularGauge.swf", "ChartId", "400", "180");
	            chart.setDataXML("${chartStr[1]}" );
	            chart.render("chartdiv2");
	            
 	            if((parseFloat(${maxValueS[1]}))<=(parseFloat(${currentValueS[1]}))){
	            	setInterval("changeCharColor('${maxValueS[1]}','${currentValueS[1]}','chartdiv2uu','${biaoZhuS[1]}+${currentValueS[1]}')", speed);
	            }else{
	            	jQuery("#chartdiv2uu").html("<font face=arial color='blue'>'${biaoZhuS[1]}+${currentValueS[1]}'</font>");
	            }  
	        </script>
	    </td>
	   
	  </tr>
	  <tr>
		  <td align="left" height="50"><a id="chartdiv1uu" href="${ctx}/query?searchName=DEPTKEY_MAI" target="_blank"></a></td>
		  <td align="left" height="50"><a id="chartdiv2uu" href="12"></a></td>
	  </tr>
</table>
<script>

</script>

<div id="colorpicker"></div>
