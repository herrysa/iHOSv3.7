<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="commandScript.title" /></title>
<meta name="heading" content="<fmt:message key='commandScript.title'/>" />
<script language="JavaScript" src="${ctx}/fusionCharts/JSClass/FusionCharts.js"></script>

<%-- <script type="text/javascript" src="${ctx}/farbtastic/farbtastic/farbtastic.js"></script>
<link rel="stylesheet" href="${ctx}/farbtastic/farbtastic/farbtastic.css" type="text/css" />

<script type="text/javascript">
	$(document).ready(function() {
		$('#colorpicker').farbtastic('#color');
	});
</script> --%>
</head>
<form>
<table width="98%" border="0" cellspacing="0" cellpadding="3">
<!-- <tr> 
    <td valign="top" class="text" align="left"> <div id="chartdiv2" align="left"> </div>
      <script type="text/javascript">
		   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/Pie2D.swf", "ChartId", "1000", "800", "0", "0");
		   //chart.setDataURL("${ctx}/data/Pie2D.xml");
		   chart.setDataXML("<?xml version='1.0' encoding='UTF-16'?><chart   caption='医院' subCaption='没事'  baseFont='黑体' baseFontSize='16' baseFontColor='#990000' decimalPrecision='0.00' bgColor='#99ccff' showPercentValues='1'><set label='执行科室3.00' value='3.00'/><set label='血液科3.56' value='3.56'/><set label='放射科4.00' value='4.00'/><set label='会计科2.50' value='2.50'/><set label='肿瘤科3.60' value='3.60'/><set label='口腔科2.70' value='2.70'/><set label='眼科4.10' value='4.10'/><set label='喉科3.80' value='3.80'/><set label='理疗科4.30' value='4.30'/><set label='美容科2.40' value='2.40'/><set label='其他:1.30' value='1.30'/></chart>");
		   chart.render("chartdiv2");
		</script></td>
    
  </tr> -->
    <tr> 
    <td valign="top" class="text" align="center"> <div id="chartdiv3" align="center"> 
        FusionCharts. </div>
      <script type="text/javascript">
		   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/Radar.swf", "ChartId", "800", "700", "0", "0");
		   //chart.setDataURL("${ctx}/data/MSLine.xml");		 
		   chart.setDataXML("<chart caption='雷达图'  baseFontSize='16' bgColor='FFFFFF'  plotFillAlpha='5'  numberPrefix='￥' formatNumber='1' numDivLines='2' legendPosition='left'><categories font='Arial' fontSize='16'><category label='Jan'/><category label='Feb'/><category label='Mar'/><category label='Apr'/><category label='May'/><category label='Jun'/><category label='Jul'/><category label='Aug'/><category label='Sep'/><category label='Oct'/><category label='Nov'/><category label='Dec'/></categories><dataset seriesname='消化科' color='CD6AC0' anchorSides='3' anchorRadius='5' anchorBgColor='CD6AC0' anchorBgAlpha='100'><set value='1127654'/><set value='1226234'/><set value='1299456'/><set value='1311565'/><set value='1324454'/><set value='1357654'/><set value='1296234'/><set value='1359456'/><set value='1391565'/><set value='1414454'/><set value='1671565'/><set value='1134454'/></dataset><dataset seriesname='呼吸科' color='0099FF' anchorSides='3' anchorBgColor='0099FF' anchorRadius='4'><set value='534241'/><set value='556728'/><set value='575619'/><set value='676713'/><set value='665520'/><set value='634241'/><set value='656728'/><set value='675619'/><set value='776713'/><set value='865520'/><set value='976713'/><set value='665520'/></dataset></chart>");
		   chart.render("chartdiv3");
		</script></td>
    <td valign="top" class="text" align="center"> <div id="chartdiv12" align="center"> 
        FusionCharts. </div>
      <script type="text/javascript">
		   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/Bubble.swf", "ChartId", "800", "700", "0", "0");
		   //chart.setDataURL("${ctx}/data/MSLine.xml");		 
		   chart.setDataXML("<?xml version='1.0' encoding='UTF-16'?><chart caption='泡泡图' numberPrefix='￥' is3D='1' animation='1'  xAxisMaxValue='100' showPlotBorder='0' xAxisName='阿萨收到德飞' yAxisName='阿萨德飞' chartRightMargin='50'><categories><category label='0%' x='0'/><category label='20%' x='20' showVerticalLine='1'/><category label='40%' x='40' showVerticalLine='1'/><category label='60%' x='60' showVerticalLine='1'/><category label='80%' x='80' showVerticalLine='1'/><category label='100%' x='100' showVerticalLine='1'/></categories><dataSet showValues='0'><set x='30' y='1.3' z='39' name='Traders'/><set x='32' y='3.5' z='112' name='Farmers'/><set x='8' y='2.1' z='16.8' name='Individuals'/><set x='62' y='2.5' z='155' name='Medium Business Houses'/><set x='78' y='2.3' z='179.4' name='Corporate Group A'/><set x='75' y='1.4' z='105' name='Corporate Group C'/><set x='68' y='3.7' z='251.6' name='HNW Individuals'/><set x='50' y='2.1' z='105' name='Small Business Houses'/></dataSet></chart>");
		   chart.render("chartdiv12");
		</script></td>
  </tr>
<!--   <tr> 
    <td valign="top" class="text" align="center"> <div id="chartdiv4" align="center"> 
        FusionCharts. </div>
      <script type="text/javascript">
		   var chart = new FusionCharts("${ctx}/fusionCharts/Charts/MSColumn3D.swf", "ChartId", "1400", "700", "0", "0");
		   //chart.setDataURL("${ctx}/data/MSColumn3D.xml");		
		  					// <?xml version='1.0' encoding='UTF-16'?><chart caption='柱形图测试' subCaption='嘿嘿'  showValues='1' yAxisName='使得法国' xAxisName='使得法国' rotateYAxisName='0'  baseFontSize='15'  bgColor='#ffffff' bgAlpha='10,10'  bgColor='#ffffff,#cc3300' yAxisNameWidth='20'><categories><category label= '一月' /><category label= '二月' /><category label= '三月' /><category label= '四月' /><category label= '五月' /><category label= '六月' /><category label= '七月' /><category label= '八月' /><category label= '九月' /><category label= '十月' /><category label= '十一月' /><category label= '十二月' /></categories><trendlines><line startValue='1' endValue='8' color='000033' thickness='1' alpha='60' showOnTop='1' isTrendZone='0'/></trendlines><dataset seriesName='友谊医院' color='AFD8F8' ><set value='0.7' /><set value='3' /><set value='3.555' /><set value='4' /><set value='2.5' /><set value='3.6' /><set value='2.7' /><set value='4.1' /><set value='0.6' /><set value='3.8' /><set value='4.3' /><set value='2.4' /></dataset><dataset seriesName='肿瘤医院' color='F6BD0F' ><set value='12' /><set value='12.9' /><set value='16' /><set value='16.5' /><set value='12.4' /><set value='14.9' /><set value='15.1' /><set value='16.3' /><set value='12.4' /><set value='13.3' /><set value='14.6' /><set value='16.2' /></dataset><dataset seriesName='祥和医院' color='8BBA00' ><set value='20' /><set value='18.9' /><set value='21.6' /><set value='20.4' /><set value='18.6' /><set value='19.5' /><set value='22.3' /><set value='21.7' /><set value='20.6' /><set value='18.5' /><set value='18.1' /><set value='19.4' /></dataset><dataset seriesName='儿童医院' color='DBDC25' ><set value='6.4' /><set value='7.3' /><set value='2.4' /><set value='6' /><set value='10' /><set value='9' /><set value='7' /><set value='6.9' /><set value='12.4' /><set value='6.1' /><set value='9.3' /><set value='10' /></dataset></chart>
		  																 //  caption='柱形图测试' subCaption='嘿嘿' xAxisName='好日子' yAxisName='万元' rotateYAxisName='0'  baseFontSize='15'  bgColor='#ffffff' bgAlpha='10,10' bgColor='#ffffff,#cc3300'  yAxisNameWidth='20'
		   chart.setDataXML("<?xml version='1.0' encoding='UTF-16'?><chart  caption='柱形图测试' subCaption='嘿嘿' xAxisName='好日子' yAxisName='万元' rotateYAxisName='0' baseFontSize='15' bgColor='#ffffff' bgAlpha='10,10' bgColor='#ffffff,#cc3300' yAxisNameWidth='20'><categories><category label= '一月' /><category label= '二月' /><category label= '三月' /><category label= '四月' /><category label= '五月' /><category label= '六月' /><category label= '七月' /><category label= '八月' /><category label= '九月' /><category label= '十月' /><category label= '十一月' /><category label= '十二月' /></categories><trendlines><line startValue='1' endValue='8' color='000033' thickness='1' alpha='60' showOnTop='1' isTrendZone='0'/></trendlines><dataset seriesName='友谊医院' color='AFD8F8' ><set value='0.7' /><set value='3' /><set value='3.555' /><set value='4' /><set value='2.5' /><set value='3.6' /><set value='2.7' /><set value='4.1' /><set value='0.6' /><set value='3.8' /><set value='4.3' /><set value='2.4' /></dataset><dataset seriesName='肿瘤医院' color='F6BD0F' ><set value='12' /><set value='12.9' /><set value='16' /><set value='16.5' /><set value='12.4' /><set value='14.9' /><set value='15.1' /><set value='16.3' /><set value='12.4' /><set value='13.3' /><set value='14.6' /><set value='16.2' /></dataset><dataset seriesName='祥和医院' color='8BBA00' ><set value='20' /><set value='18.9' /><set value='21.6' /><set value='20.4' /><set value='18.6' /><set value='19.5' /><set value='22.3' /><set value='21.7' /><set value='20.6' /><set value='18.5' /><set value='18.1' /><set value='19.4' /></dataset><dataset seriesName='儿童医院' color='DBDC25' ><set value='6.4' /><set value='7.3' /><set value='2.4' /><set value='6' /><set value='10' /><set value='9' /><set value='7' /><set value='6.9' /><set value='12.4' /><set value='6.1' /><set value='9.3' /><set value='10' /></dataset></chart>");
		   chart.render("chartdiv4");
		</script></td>
  </tr> -->
<!--   <tr>
  <td align="center">本月本科室实现收入所在级别</td>
  </tr>
   <tr> 
    <td valign="top" class="text" align="center"> <div id="chartdiv5" align="center">Chart will load here</div>
        <script type="text/javascript">
			//if (GALLERY_RENDERER && GALLERY_RENDERER.search(/javascript|flash/i)==0)  FusionCharts.setCurrentRenderer(GALLERY_RENDERER); 
            var chart = new FusionCharts("${ctx}/fusionCharts/Charts/AngularGauge.swf", "ChartId", "700", "350");
            chart.setDataXML("<?xml version='1.0' encoding='gbk'?><Chart  fillAngle='45'  upperLimit='12'  majorTMNumber='10' majorTMHeight='10' showGaugeBorder='1' gaugeOuterRadius='250'gaugeOriginX='350' gaugeOriginY='300'  formatNumberScale='1' numberSuffix='万亿' numberPrefix='' displayValueDistance='30' ecimalPrecision='2'  pivotRadius='17'   pivotBorderThickness='5' pivotFillMix='ffffff,330000'><colorRange><color minValue='0' maxValue='4' code='399E38'/> <color minValue='4' maxValue='8' code='E48739'/><color minValue='8' maxValue='12' code='B41527'/></colorRange><dials><dial value='8' borderAlpha='0' bgColor='330000' baseWidth='28' topWidth='1' radius='250'/></dials></Chart>" );
            chart.render("chartdiv5");
        </script>
  </tr> -->
    <%--    <tr> 
    <td valign="top" class="text" align="center"> <div id="chartdiv" align="center">Chart will load here</div>
        <script type="text/javascript">
			//if (GALLERY_RENDERER && GALLERY_RENDERER.search(/javascript|flash/i)==0)  FusionCharts.setCurrentRenderer(GALLERY_RENDERER); 
            var chart = new FusionCharts("${ctx}/fusionCharts/Charts/MSSpline.swf", "ChartId", "700", "700", "0", "0" );
            chart.setDataXML("${zheXianStr}");
            chart.render("chartdiv");
        </script>
        </td>
  </tr> --%>
<!--     <tr> 
    <td>
    <input type="text" id="color" name="color" value="#123456" />
    </td>
  </tr> -->
</table>
</form>
<div id="colorpicker"></div>
