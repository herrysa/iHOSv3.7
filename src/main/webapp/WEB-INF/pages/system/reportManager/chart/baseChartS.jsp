
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script language="JavaScript" src="${ctx}/fusionCharts/JSClass/FusionCharts.js"></script>

	<!-- <s:property value="#request.chartStr"/> -->
	<div class="page">
		<div class="pageContent" layoutH="1" >
		
	<!-- -----------------------------------图表START----------------------------------- -->
		<div id="${random}_chartdiv0" align="center" style="margin-top:20px"> </div>
	    <c:if  test="${chartStr!=null&&chartStr!=''}">
	      <script type="text/javascript">
	      	   var charSwf = "${charSwf}";
	      	   if(!charSwf){
	      	   	charSwf = "MSSpline";
	      	   }
			   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/"+charSwf+".swf", "ChartId", "700", "500", "0", "0");
			   chart.setTransparent(true);
			   chart.addParam("wmode","Opaque");
			   chart.setDataXML("${chartStr}");
			   chart.render("${random}_chartdiv0");
			</script>
		</c:if>
<%--      	<c:choose><c:when test="${chartStr==null||chartStr==''}"> 
		<b><span id="${random}_0"><font color='#999999'>没有可显示数据!</font></span></b>
		</c:when><c:otherwise> 
			<b><span id="${random}_0"></span></b>
		</c:otherwise></c:choose>	 --%>
	<!-- -----------------------------------图表END----------------------------------- -->
	</div>
	</div>




