<!DOCTYPE html>
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
	<div class="${random}_chartTitle"><a  href="#"><b><font color='#15428B'>1.均次收费水平</font></b></a><br>&nbsp;</div>
	<div class="${random}_chartContent" style="overflow-y:auto;clear:both;border-collapse: collapse;display:block;">
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="margin-bottom: 100px">
	    <tr> 
	    <td valign="top" class="text" align="center"><br><br> <div id="${random}_chartdiv0" align="center"> </div>
		    <c:if  test="${chartStr[0]!=null&&chartStr[0]!=''}">
		      <script type="text/javascript">
				   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/MSSpline.swf", "ChartId", "600", "400", "0", "0");
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
	<!-- -----------------------------------图表END----------------------------------- -->



	<!-- -----------------------------------图表START----------------------------------- -->
	<div class="${random}_chartTitle"><a  href="#"><b><font color='#15428B'>2.药品收入比例</font></b></a><br>&nbsp;</div>
	<div class="${random}_chartContent" style="overflow-y:auto;clear:both;border-collapse: collapse;display:block;">
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="margin-bottom: 100px">
	    <tr> 
	    <td valign="top" class="text" align="center"><br><br> <div id="${random}_chartdiv1" align="center"> </div>
		    <c:if  test="${chartStr[1]!=null&&chartStr[1]!=''}">
		      <script type="text/javascript">
				   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/MSSpline.swf", "ChartId", "600", "400", "0", "0");
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

	<!-- -----------------------------------图表END----------------------------------- -->




	</div>
</div>
