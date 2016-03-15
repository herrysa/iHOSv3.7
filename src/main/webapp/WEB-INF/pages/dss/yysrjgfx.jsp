<!DOCTYPE html>
<%@page import="com.huge.ihos.system.reportManager.chart.model.IntegrationData"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- -----------------------------------导入脚本START----------------------------------- -->
<%@ include file="/common/baseChartFunctionCondition.jsp"%>
<!-- -----------------------------------导入脚本END  ----------------------------------- -->
	<div class="page">
		<div class="pageContent" layoutH="1" style="margin-left: 30px;">
		<br>
	<div  align="right" style="margin-right: 50px;"><a href="#" ><b><font color='#15428B' class="${random}_allClose">全部折叠</font></b></a> </div>
	
	<!-- -----------------------------------图表START----------------------------------- -->
	<div class="${random}_chartTitle"><a  href="#"><b><font color='#15428B'>1.医院收入结构分析(--按医疗/药品)</font></b></a><br>&nbsp;</div>
	<div class="${random}_chartContent" style="clear:both;border-collapse: collapse;display:block;">
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="margin-bottom: 100px">
	    <tr> 
	    <td align="right" valign="top">
	    <c:if test="${bingDataList[0]!=null}">
	    	<div style="height:30px;" align="left">
				<table width="350px"  border="1" bordercolor="#ffffff">
					<tr bgcolor="#04A0DD" height="30px" >
						<td align="center" width="30%"><span><b><font color="#FFFFFF">名称</font></b></span></td>
						<td align="center" width="40%"><span><b><font color="#FFFFFF">金额</font></b></span></td>
						<td align="center" width="30%"><span><b><font color="#FFFFFF">百分比</font></b></span></td>
					</tr>
			</table>
			</div>
	    	<div style="height:320px;overflow-y:auto;"  align="left">
			<table width="350px" border="1" bordercolor="#ffffff">
				<c:forEach var="dataList" items="${bingDataList[0]}"> 
					<tr bgcolor="#D4E6FA" height="30">
						<td align="center" width="30%">${dataList.dataName}</td>
						<td align="right" width="40%">${dataList.dataStringValue}</td>
						<td align="right" width="30%">${dataList.percent}</td>
					</tr>
				</c:forEach>
			</table>
			</div>
		</c:if>
		<script>
		</script>
		</td>
	    <td valign="top" class="text" align="center"> <div id="${random}_chartdiv0" align="left"> </div>
		    <c:if  test="${chartStr[0]!=null&&chartStr[0]!=''}">
		      <script type="text/javascript">
				   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/Pie${c3dS[0]}D.swf", "ChartId", "600", "350", "0", "0");
				   chart.setDataXML("${chartStr[0]}");
				   chart.setTransparent(true);
					chart.addParam("wmode","Opaque");
				   chart.render("${random}_chartdiv0");
				</script>
			</c:if>
			<c:choose><c:when test="${chartStr[0]==null||chartStr[0]==''}"> 
				<b><span id="${random}_0"><font color='#999999'>没有可显示数据!</font></span></b>
			</c:when><c:otherwise> 
				<b><span id="${random}_0"></span></b>
			</c:otherwise></c:choose>
		</td>
	  </tr>
	</table>
	</div>

		  &nbsp;<br/>

	
	<div class="${random}_chartTitle"><a  href="#"><b><font color='#15428B'>2.医院收入结构分析(--按门诊/住院)</font></b></a><br>&nbsp;</div>
	<!-- -----------------------------------图表START----------------------------------- -->
	<div class="${random}_chartContent"  style="clear:both;border-collapse: collapse;display:block; ">
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="margin-bottom: 100px">
	<tr>
	 	<td align="right" valign="top">
	    <c:if test="${bingDataList[1]!=null}">
	   		<div style="height:30px;" align="left">
				<table width="350px"  border="1" bordercolor="#ffffff">
					<tr bgcolor="#04A0DD" height="30">
						<td align="center" width="30%"><span><b><font color="#FFFFFF">名称</font></b></span></td>
						<td align="center" width="40%"><span><b><font color="#FFFFFF">金额</font></b></span></td>
						<td align="center" width="30%"><span><b><font color="#FFFFFF">百分比</font></b></span></td>
					</tr>
			</table>
			</div>
	    	<div style="height:320px;overflow-y:auto;"  align="left">
			<table width="350px"  border="1" bordercolor="#ffffff">
				<c:forEach var="dataList" items="${bingDataList[1]}"> 
					<tr bgcolor="#D4E6FA" height="30">
						<td align="center" width="30%">${dataList.dataName}</td>
						<td align="right" width="40%">${dataList.dataStringValue}</td>
						<td align="right" width="30%">${dataList.percent}</td>
					</tr>
				</c:forEach>
			</table>
			</div>
		</c:if>
		</td>
		<td valign="top" class="text" align="center"> <div id="${random}_chartdiv1" align="left"> </div>
		    <c:if  test="${chartStr[1]!=null&&chartStr[1]!=''}">
		      <script type="text/javascript">
				   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/Pie${c3dS[1]}D.swf", "ChartId", "600", "350", "0", "0");
				   chart.setDataXML("${chartStr[1]}");
				   chart.setTransparent(true);
					chart.addParam("wmode","Opaque");
				   chart.render("${random}_chartdiv1");
				</script>
			</c:if>
			<c:choose><c:when test="${chartStr[1]==null||chartStr[1]==''}"> 
				<b><span id="${random}_1"><font color='#999999'>没有可显示数据!</font></span></b>
			</c:when><c:otherwise> 
				<b><span id="${random}_1"></span></b>
			</c:otherwise></c:choose>
		</td>
	</tr>
	</table>
	</div>

		  &nbsp;<br/>



	<div class="${random}_chartTitle" ><a  href="#"><b><font color='#15428B'>3.医院收入结构分析(--按收费类别)</font></b></a><br>&nbsp;</div>
	<!-- -----------------------------------图表START----------------------------------- -->
	<div class="${random}_chartContent"  style="clear:both;border-collapse: collapse;display:block; ">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="margin-bottom: 100px">
		<tr>
			<td align="right" valign="top">
		    <c:if test="${bingDataList[2]!=null}">
			    <div style="height:30px;" align="left">
					<table width="350px"  border="1" bordercolor="#ffffff">
						<tr bgcolor="#04A0DD" height="30">
							<td align="center" width="30%"><span><b><font color="#FFFFFF">名称</font></b></span></td>
							<td align="center" width="40%"><span><b><font color="#FFFFFF">金额</font></b></span></td>
							<td align="center" width="30%"><span><b><font color="#FFFFFF">百分比</font></b></span></td>
						</tr>
				</table>
				</div>
		    	<div style="height:320px;overflow-y:auto;"  align="left">
				<table width="350px"  border="1" bordercolor="#ffffff">
					<c:forEach var="dataList" items="${bingDataList[2]}"> 
						<tr bgcolor="#D4E6FA" height="30">
							<td align="center" width="30%">${dataList.dataName}</td>
							<td align="right" width="40%">${dataList.dataStringValue}</td>
							<td align="right" width="30%">${dataList.percent}</td>
						</tr>
					</c:forEach>
				</table>
				</div>
			</c:if>
			</td>
			<td valign="top" class="text" align="center"> <div id="${random}_chartdiv2" align="left"> </div>
			    <c:if  test="${chartStr[2]!=null&&chartStr[2]!=''}">
			      <script type="text/javascript">
					   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/Pie${c3dS[2]}D.swf", "ChartId", "600", "350", "0", "0");
					   chart.setDataXML("${chartStr[2]}");
					   chart.setTransparent(true);
						chart.addParam("wmode","Opaque");
					   chart.render("${random}_chartdiv2");
					</script>
				</c:if>
				<c:choose><c:when test="${chartStr[2]==null||chartStr[2]==''}"> 
				<b><span id="${random}_2"><font color='#999999'>没有可显示数据!</font></span></b>
				</c:when><c:otherwise> 
				<b><span id="${random}_2"></span></b>
			</c:otherwise></c:choose>
			</td>
		</tr>
	</table>
	</div>

		  &nbsp;<br/>


	
	<div class="${random}_chartTitle" ><a  href="#"><b><font color='#15428B'>4.医院收入结构分析(--按收入项目)</font></b></a><br>&nbsp;</div>
	<!-- -----------------------------------图表START----------------------------------- -->
	<div class="${random}_chartContent"  style="clear:both;border-collapse: collapse;display:block; ">
	
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="margin-bottom: 100px">
		<tr>
			<td align="right" valign="top">
		    <c:if test="${bingDataList[3]!=null}">
			    <div style="height:30px;" align="left">
					<table width="350px"  border="1" bordercolor="#ffffff">
						<tr bgcolor="#04A0DD" height="30">
							<td align="center" width="30%"><span><b><font color="#FFFFFF">名称</font></b></span></td>
							<td align="center" width="40%"><span><b><font color="#FFFFFF">金额</font></b></span></td>
							<td align="center" width="30%"><span><b><font color="#FFFFFF">百分比</font></b></span></td>
						</tr>
				</table>
				</div>
		    	<div style="height:320px;overflow-y:auto;"  align="left">
				<table width="350px"  border="1" bordercolor="#ffffff">
					<c:forEach var="dataList" items="${bingDataList[3]}"> 
						<tr bgcolor="#D4E6FA" height="30">
							<td align="center" width="30%">${dataList.dataName}</td>
							<td align="right" width="40%">${dataList.dataStringValue}</td>
							<td align="right" width="30%">${dataList.percent}</td>
						</tr>
					</c:forEach>
				</table>
				</div>
			</c:if>
			</td>
			<td valign="top" class="text" align="center"> <div id="${random}_chartdiv3" align="left"> </div>
			    <c:if  test="${chartStr[3]!=null&&chartStr[3]!=''}">
			      <script type="text/javascript">
					   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/Pie${c3dS[3]}D.swf", "ChartId", "600", "350", "0", "0");
					   chart.setDataXML("${chartStr[3]}");
					   chart.setTransparent(true);
						chart.addParam("wmode","Opaque");
					   chart.render("${random}_chartdiv3");
					</script>
				</c:if>
				<c:choose><c:when test="${chartStr[3]==null||chartStr[3]==''}"> 
				<b><span id="${random}_3"><font color='#999999'>没有可显示数据!</font></span></b>
				</c:when><c:otherwise> 
				<b><span id="${random}_3"></span></b>
			</c:otherwise></c:choose>
			</td>
		</tr>
	</table>
	</div>

		  &nbsp;<br/>


	</div>
	</div>

	<!-- -----------------------------------图表END----------------------------------- -->
