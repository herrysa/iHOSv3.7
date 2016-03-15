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
	<div class="${random}_chartTitle"><a  href="#"><b><font color='#15428B'>1.门诊人次</font></b></a><br>&nbsp;</div>
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
	<div class="${random}_chartTitle"><a  href="#"><b><font color='#15428B'>2.实际占用床日数</font></b></a>	<br>&nbsp;</div>
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

	<!-- -----------------------------------图表START----------------------------------- -->
	<div class="${random}_chartTitle"><a  href="#"><b><font color='#15428B'>3.入院人数</font></b></a><br>&nbsp;</div>
	<div class="${random}_chartContent" style="overflow-y:auto;clear:both;border-collapse: collapse;display:block;">
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="margin-bottom: 100px">
	    <tr> 
	    <td valign="top" class="text" align="center"><br><br> <div id="${random}_chartdiv2" align="center"> </div>
		    <c:if  test="${chartStr[2]!=null&&chartStr[2]!=''}">
		      <script type="text/javascript">
				   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/MSSpline.swf", "ChartId", "600", "400", "0", "0");
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
	<!-- -----------------------------------图表END----------------------------------- -->

	<!-- -----------------------------------图表START----------------------------------- -->
	<div class="${random}_chartTitle"><a  href="#"><b><font color='#15428B'>4.出院人数</font></b></a><br>&nbsp;</div>
	<div class="${random}_chartContent" style="overflow-y:auto;clear:both;border-collapse: collapse;display:block;">
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="margin-bottom: 100px">
	    <tr> 
	    <td valign="top" class="text" align="center"><br><br> <div id="${random}_chartdiv3" align="center"> </div>
		    <c:if  test="${chartStr[3]!=null&&chartStr[3]!=''}">
		      <script type="text/javascript">
				   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/MSSpline.swf", "ChartId", "600", "400", "0", "0");
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
	<!-- -----------------------------------图表END----------------------------------- -->

	<!-- -----------------------------------图表START----------------------------------- -->
	<div class="${random}_chartTitle"><a  href="#"><b><font color='#15428B'>5.手术例数</font></b></a><br>&nbsp;</div>
	<div class="${random}_chartContent" style="overflow-y:auto;clear:both;border-collapse: collapse;display:block;">
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="margin-bottom: 100px">
	    <tr> 
	    <td valign="top" class="text" align="center"><br><br> <div id="${random}_chartdiv4" align="center"> </div>
		    <c:if  test="${chartStr[4]!=null&&chartStr[4]!=''}">
		      <script type="text/javascript">
				   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/MSSpline.swf", "ChartId", "600", "400", "0", "0");
				   chart.setDataXML("${chartStr[4]}");
				   chart.setTransparent(true);
					chart.addParam("wmode","Opaque");
				   chart.render("${random}_chartdiv4");
				</script>
			</c:if>
			     	<c:choose><c:when test="${chartStr[4]==null||chartStr[4]==''}"> 
					<b><span id="${random}_4"><font color='#999999'>没有可显示数据!</font></span></b>
					</c:when><c:otherwise> 
						<b><span id="${random}_4"></span></b>
					</c:otherwise></c:choose>
		</td>
	  </tr>
	</table>
	</div>	
	<!-- -----------------------------------图表END----------------------------------- -->
	
	<!-- -----------------------------------图表START----------------------------------- -->
	<div class="${random}_chartTitle"><a  href="#"><b><font color='#15428B'>6.床位使用率</font></b></a><br>&nbsp;</div>
	<div class="${random}_chartContent" style="overflow-y:auto;clear:both;border-collapse: collapse;display:block;">
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="margin-bottom: 100px">
	    <tr> 
	    <td valign="top" class="text" align="center"><br><br> <div id="${random}_chartdiv5" align="center"> </div>
		    <c:if  test="${chartStr[5]!=null&&chartStr[5]!=''}">
		      <script type="text/javascript">
				   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/MSSpline.swf", "ChartId", "600", "400", "0", "0");
				   chart.setDataXML("${chartStr[5]}");
				   chart.setTransparent(true);
					chart.addParam("wmode","Opaque");
				   chart.render("${random}_chartdiv5");
				</script>
			</c:if>
			     	<c:choose><c:when test="${chartStr[5]==null||chartStr[5]==''}"> 
					<b><span id="${random}_5"><font color='#999999'>没有可显示数据!</font></span></b>
					</c:when><c:otherwise> 
						<b><span id="${random}_5"></span></b>
					</c:otherwise></c:choose>
		</td>
	  </tr>
	</table>
	</div>	
	<!-- -----------------------------------图表END----------------------------------- -->

	<!-- -----------------------------------图表START----------------------------------- -->
	<div class="${random}_chartTitle"><a  href="#"><b><font color='#15428B'>7.平均住院日</font></b></a><br>&nbsp;</div>
	<div class="${random}_chartContent" style="overflow-y:auto;clear:both;border-collapse: collapse;display:block;">
	<table width="100%" border="0" cellspacing="0" cellpadding="3" style="margin-bottom: 100px">
	    <tr> 
	    <td valign="top" class="text" align="center"><br><br> <div id="${random}_chartdiv6" align="center"> </div>
		    <c:if  test="${chartStr[6]!=null&&chartStr[6]!=''}">
		      <script type="text/javascript">
				   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/MSSpline.swf", "ChartId", "600", "400", "0", "0");
				   chart.setDataXML("${chartStr[6]}");
				   chart.setTransparent(true);
					chart.addParam("wmode","Opaque");
				   chart.render("${random}_chartdiv6");
				</script>
			</c:if>
			     	<c:choose><c:when test="${chartStr[6]==null||chartStr[6]==''}"> 
					<b><span id="${random}_6"><font color='#999999'>没有可显示数据!</font></span></b>
					</c:when><c:otherwise> 
						<b><span id="${random}_6"></span></b>
					</c:otherwise></c:choose>
		</td>
	  </tr>
	</table>
	</div>	
	<!-- -----------------------------------图表END----------------------------------- -->

	</div>
</div>

