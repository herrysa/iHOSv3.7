<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- -----------------------------------导入脚本START----------------------------------- -->
<%@ include file="/common/baseChartFunctionCondition.jsp"%>
<!-- -----------------------------------导入脚本END  ----------------------------------- -->

<div class="page">
<div class="pageContent" layoutH="1" style="margin-left: 30px;">
	<!-- --------------------------------- 表盘显示(START)--------------------------------- -->
	<br>
	<div  align="right" style="margin-right: 50px;"><a href="#" ><b><font color='#15428B' class="${random}_allClose">全部折叠</font></b></a></div>
	<!-- ---------------------------------第一行表盘(START)--------------------------------- -->
	<!-- ---------------------------------第一行表盘标题(START)--------------------------------- -->
	<div class="${random}_chartTitle"><a  href="#"><b><font color='#15428B'>1.经济指标</font></b></a><br>&nbsp;</div>
	 <!-- <tr>
	  <td align="center" height="50" name="biaoTis"></td>
	  <td align="center" height="50" name="biaoTis"></td>
	  <td align="center" height="50" name="biaoTis"></td>
	  </tr> -->
	<!-- ---------------------------------第一行表盘标题(END)--------------------------------- -->
	
	<!-- ---------------------------------第一行表盘图形(START)--------------------------------- -->
	  <div class="${random}_chartContent"  style="clear:both;border-collapse: collapse;display:block; ">
	  &nbsp;
		<table width="98%">
			<tr>
				<td align="center">
				    <div id="${random}_chartdiv0" align="center"></div>
			    	 <c:if  test="${chartStr[0]!=null&&chartStr[0]!=''}">
				        <script type="text/javascript">
				            var chart = new FusionCharts("${ctx}/fusionCharts/Charts/AngularGauge.swf", "ChartId", "320", "150");
				            chart.setDataXML("${chartStr[0]}" );
				            chart.setTransparent(true);
							chart.addParam("wmode","Opaque");
				            chart.render("${random}_chartdiv0");
				            if((parseFloat(${maxValueS[0]}))<=(parseFloat(${currentValueS[0]}))&&${warningS[0]}){
				            	chartTitleS.push(setInterval("changeCharColor('${maxValueS[0]}','${currentValueS[0]}','${random}_chartdiv0uu','${biaoZhuS[0]}&nbsp;&nbsp;${currentValueS[0]}')", speed));
				            }else{
				            	jQuery("#${random}_chartdiv0uu").html("<center><font face=arial color='black'>${biaoZhuS[0]}&nbsp;&nbsp;${currentValueS[0]}</font></center>");
				            }
				        </script>
				     </c:if>
			     	<c:choose><c:when test="${chartStr[0]==null||chartStr[0]==''}"> 
					<b><span id="${random}_0"><font color='#999999'>没有可显示数据!</font></span></b>
					</c:when><c:otherwise> 
						<b><span id="${random}_0"></span></b>
					</c:otherwise></c:choose>
			    </td>
			    <td align="center">
					<div id="${random}_chartdiv1" align="center"></div>
			    	 <c:if  test="${chartStr[1]!=null&&chartStr[1]!=''}">
					     <script type="text/javascript">
				            var chart = new FusionCharts("${ctx}/fusionCharts/Charts/AngularGauge.swf", "ChartId", "320", "150");
				            chart.setDataXML("${chartStr[1]}" );
				            chart.render("${random}_chartdiv1");
			 	            if((parseFloat(${maxValueS[1]}))<=(parseFloat(${currentValueS[1]}))&&${warningS[1]}){
			 	            	chartTitleS.push(setInterval("changeCharColor('${maxValueS[1]}','${currentValueS[1]}','${random}_chartdiv1uu','${biaoZhuS[1]}&nbsp;&nbsp;${currentValueS[1]}')", speed));
				            }else{
				            	jQuery("#${random}_chartdiv1uu").html("<center><font face=arial color='black'>${biaoZhuS[1]}&nbsp;&nbsp;${currentValueS[1]}</font></center>");
				            }  
				        </script>
					</c:if>
			     	<c:choose><c:when test="${chartStr[1]==null||chartStr[1]==''}"> 
					<b><span id="${random}_1"><font color='#999999'>没有可显示数据!</font></span></b>
					</c:when><c:otherwise> 
						<b><span id="${random}_1"></span></b>
					</c:otherwise></c:choose>
				</td>
			    <td align="center">
				    <div id="${random}_chartdiv2" align="center"></div>
				    <c:if  test="${chartStr[2]!=null&&chartStr[2]!=''}">
				        <script type="text/javascript">
				            var chart = new FusionCharts("${ctx}/fusionCharts/Charts/AngularGauge.swf", "ChartId", "320", "150");
				            chart.setDataXML("${chartStr[2]}" );
				            chart.setTransparent(true);
							chart.addParam("wmode","Opaque");
				            chart.render("${random}_chartdiv2");
				            
			 	            if((parseFloat(${maxValueS[2]}))<=(parseFloat(${currentValueS[2]}))&&${warningS[2]}){
			 	            	chartTitleS.push(setInterval("changeCharColor('${maxValueS[2]}','${currentValueS[2]}','${random}_chartdiv2uu','${biaoZhuS[2]}&nbsp;&nbsp;${currentValueS[2]}')", speed));
				            }else{
				            	jQuery("#${random}_chartdiv2uu").html("<center><font face=arial color='black'>${biaoZhuS[2]}&nbsp;&nbsp;${currentValueS[2]}</font></center>");
				            }  
				        </script>
				    </c:if>
			     	<c:choose><c:when test="${chartStr[2]==null||chartStr[2]==''}"> 
					<b><span id="${random}_2"><font color='#999999'>没有可显示数据!</font></span></b>
					</c:when><c:otherwise> 
						<b><span id="${random}_2"></span></b>
					</c:otherwise></c:choose>
					
				</td>
			</tr>
		
	  <!-- ---------------------------------第一行表盘图形(END)--------------------------------- -->
	  
	  <!-- ---------------------------------第一行表盘标注(START)--------------------------------- -->
		  <tr>
			  <td align="left" height="50"><a id="${random}_chartdiv0uu" href="#"></a></td>
			  <td align="left" height="50"><a id="${random}_chartdiv1uu" href="#"></a></td>
			  <td align="left" height="50"><a id="${random}_chartdiv2uu" href="#"></a></td>
		  </tr>
	  </table>
   </div>
  
        <!-- <td align="left" height="50"><a id="${random}_chartdiv0uu" href="${ctx}/query?searchName=DEPTKEY_MAI" target="_blank"></a></td> -->
       

	  <!-- ---------------------------------第一行表盘标注(END)--------------------------------- -->
	  
	  
	  <!-- ---------------------------------第一行表盘(END)--------------------------------- -->
	  
	  <!-- 第二,三,,,行表盘同第一行表盘样式相同 -->
	  &nbsp;<br/>
	  &nbsp;<br/>
	  <!-- &nbsp;<br/>
	  &nbsp;<br/> -->
	  <!-- ---------------------------------第二行表盘(START)--------------------------------- -->
	<!-- ---------------------------------第二行表盘标题(START)--------------------------------- -->
	<div class="${random}_chartTitle"><a  href="#"><b><font color='#15428B'>2.病人负担</font></b></a><br>&nbsp;</div>
	  <!-- <tr>
		  <td align="center" height="50" name="biaoTis"></td>
		  <td align="center" height="50" name="biaoTis"></td>
		  <td align="center" height="50" name="biaoTis"></td>
	  </tr> -->
	  <!-- ---------------------------------第二行表盘标题(END)--------------------------------- -->
	
	 <!-- ---------------------------------第二行表盘图形(START)--------------------------------- -->
	 <div class="${random}_chartContent" style="clear:both;border-collapse: collapse;display:block; ">
	 &nbsp;
		<table  width="98%">
		 <tr>
		    <td align="center">
		    	<div id="${random}_chartdiv3" align="center"></div>
		    	<c:if  test="${chartStr[3]!=null&&chartStr[3]!=''}">
		        <script type="text/javascript">
		            var chart = new FusionCharts("${ctx}/fusionCharts/Charts/AngularGauge.swf", "ChartId", "320", "150");
		            chart.setDataXML("${chartStr[3]}" );
		            chart.setTransparent(true);
					chart.addParam("wmode","Opaque");
		            chart.render("${random}_chartdiv3");
		            if((parseFloat(${maxValueS[3]}))<=(parseFloat(${currentValueS[3]}))&&${warningS[3]}){
		            	chartTitleS.push(setInterval("changeCharColor('${maxValueS[3]}','${currentValueS[3]}','${random}_chartdiv3uu','${biaoZhuS[3]}&nbsp;&nbsp;${currentValueS[3]}')", speed));
		            }else{
		            	jQuery("#${random}_chartdiv3uu").html("<center><font face=arial color='black'>${biaoZhuS[3]}&nbsp;&nbsp;${currentValueS[3]}</font></center>");
		            }  
		        </script>
		        </c:if>
			     	<c:choose><c:when test="${chartStr[3]==null||chartStr[3]==''}"> 
					<b><span id="${random}_3"><font color='#999999'>没有可显示数据!</font></span></b>
					</c:when><c:otherwise> 
						<b><span id="${random}_3"></span></b>
					</c:otherwise></c:choose>
		    </td>
		    <td align="center"> 
		    	<div id="${random}_chartdiv4" align="center"></div>
		    	<c:if  test="${chartStr[4]!=null&&chartStr[4]!=''}">
		        <script type="text/javascript">
		            var chart = new FusionCharts("${ctx}/fusionCharts/Charts/AngularGauge.swf", "ChartId", "320", "150");
		            chart.setDataXML("${chartStr[4]}" );
		            chart.setTransparent(true);
					chart.addParam("wmode","Opaque");
		            chart.render("${random}_chartdiv4");
		            
			        if((parseFloat(${maxValueS[4]}))<=(parseFloat(${currentValueS[4]}))&&${warningS[4]}){
			        	chartTitleS.push(setInterval("changeCharColor('${maxValueS[4]}','${currentValueS[4]}','${random}_chartdiv4uu','${biaoZhuS[4]}&nbsp;&nbsp;${currentValueS[4]}')", speed));
		            }else{
		            	jQuery("#${random}_chartdiv4uu").html("<center><font face=arial color='black'>${biaoZhuS[4]}&nbsp;&nbsp;${currentValueS[4]}</font></center>");
		            }  
		        </script>
		        </c:if>
			     	<c:choose><c:when test="${chartStr[4]==null||chartStr[4]==''}"> 
					<b><span id="${random}_4"><font color='#999999'>没有可显示数据!</font></span></b>
					</c:when><c:otherwise> 
						<b><span id="${random}_4"></span></b>
					</c:otherwise></c:choose>
		    </td>
		    <td align="center"> 
		    	<div id="${random}_chartdiv5" align="center"></div>
		    	<c:if  test="${chartStr[5]!=null&&chartStr[5]!=''}">
		        <script type="text/javascript">
		            var chart = new FusionCharts("${ctx}/fusionCharts/Charts/AngularGauge.swf", "ChartId", "320", "150");
		            chart.setDataXML("${chartStr[5]}" );
		            chart.setTransparent(true);
					chart.addParam("wmode","Opaque");
		            chart.render("${random}_chartdiv5");
		            
			        if((parseFloat(${maxValueS[5]}))<=(parseFloat(${currentValueS[5]}))&&${warningS[5]}){
			        	chartTitleS.push(setInterval("changeCharColor('${maxValueS[5]}','${currentValueS[5]}','${random}_chartdiv5uu','${biaoZhuS[5]}&nbsp;&nbsp;${currentValueS[5]}')", speed));
		            }else{
		            	jQuery("#${random}_chartdiv5uu").html("<center><font face=arial color='black'>${biaoZhuS[5]}&nbsp;&nbsp;${currentValueS[5]}</font></center>");
		            }  
		        </script>
		        </c:if>
			     	<c:choose><c:when test="${chartStr[5]==null||chartStr[5]==''}"> 
					<b><span id="${random}_5"><font color='#999999'>没有可显示数据!</font></span></b>
					</c:when><c:otherwise> 
						<b><span id="${random}_5"></span></b>
					</c:otherwise></c:choose>
		    </td>
		  </tr>
		  <!-- ---------------------------------第二行表盘图形(END)--------------------------------- -->
		  
		  <!-- ---------------------------------第二行表盘标注(START)--------------------------------- -->
		  <tr>
			  <td align="left" height="50"><a id="${random}_chartdiv3uu" href="#"></a></td>
			  <td align="left" height="50"><a id="${random}_chartdiv4uu" href="#"></a></td>
			  <td align="left" height="50"><a id="${random}_chartdiv5uu" href="#"></a></td>
		  </tr>

		  <!--  <td align="left" height="50"><a id="${random}_chartdiv4uu" href="javaScript:dispalySearchName('${ctx}/query?searchName=sel_person')"></a></td> -->


		  <!-- ---------------------------------第二行表盘标注(END)--------------------------------- -->
		</table>
</div>
</div></div>
	 <!-- --------------------------------- 表盘显示(END)--------------------------------- -->
