
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script language="JavaScript" src="${ctx}/fusionCharts/JSClass/FusionCharts.js"></script>
<%-- <script language="JavaScript" src="${ctx}/fusionCharts/JSClass/FusionChartsExportComponent.js"></script> --%>
<script type="text/javascript">
	var statisticsSingleChartCheckLayout;
	var pieChart='';
	var column2DSingleChart='';
	var line2DSingleChart='';
	var jsonStr='';
	var title='';
	var subTitle='';
	var baseFontSize='';
	var charIDPie3D='';
	var statisticsAuto = true;
	jQuery(document).ready(function() { 
   		if("${statisticsDetail.shijianName}"){
   			jQuery("#singleChartFilterCheck_date")[0].innerHTML="${statisticsDetail.shijianName}";
   		}else{
   			jQuery("#singleChartFilterCheck_datelabel").hide();
   		}
   		if(!"${statisticsDetail.deptFK}"){
   			jQuery("#singleChartFilterCheck_deptlabel").hide();
   		}
//    	jQuery("#statisticsSingleChartCheck_addFilter").unbind( 'click' ).bind("click",function(){
// 	    var url = "editSingleChartFilter?id="+"${id}";
// 	    url=url+"&statisticsCode="+"hr_person"+"&navTabId=singleChartFilterCheck";
// 	    var winTitle='<s:text name="singleChart.filterEdit"/>';
// 		$.pdialog.open(url,'editSingleChartFilter',winTitle, {mask:true,width : 700,height : 600});
//    	});
    	
    	//导出图片
//     	jQuery("#statisticsSingleChartCheck_exportPicture").unbind( 'click' ).bind("click",function(){
//     		if(!charIDPie3D){
//     			alertMsg.error("无数据可以导出。");
//           		return;
//     		}
// //     		var chartObject=getChartFromId(charIDPie3D);
// //     		if(chartObject.hasRendered()){
// //     			chartObject.exportChart();
// //     			alert("1");
// //     		}else{
// //     			alert("2");
// //     		}
//     	});
    	//导出表格
		jQuery("#statisticsSingleChartCheck_exportExcel").unbind( 'click' ).bind("click",function(){
			if(!jsonStr){
    			alertMsg.error("无数据可以导出。");
          		return;
    		}
    		var gridAllDatas='{"edit":'+jsonStr+'}';
    		var url ='exportExcelForSatatiticsItem';
    		$.ajax({
    			url: url,
    			type: 'post',
    			data:{gridAllDatas:gridAllDatas,title:title+"-"+subTitle},
    			dataType: 'json',
    			async:false,
    			error: function(data){
    				alertMsg.error("系统错误！");
    			},
    			success: function(data){
    				var downLoadFileName = data["message"];
    				var filePathAndName = downLoadFileName.split("@@");
    				var url = "${ctx}/downLoadExel?filePath="+filePathAndName[0]+"&fileName="+filePathAndName[1];
//     		 		url=encodeURI(url);
    		 		location.href=url; 
    			}
    		}); 
    	});
    	if("${id}"){
    		 jQuery.ajax({
		            url:"statisticsItemGridList?filter_EQS_id="+"${id}",
		            data: {},
		            type: 'post',
		            dataType: 'json',
		            async:false,
		            error: function(data){
		            },
		            success: function(data){
		            	if(data&&data.statisticsItems){
		            		var statisticsItem = data.statisticsItems[0];
		            		statisticsAuto = statisticsItem.statisticsAuto;
		            		var deptRequired = statisticsItem.deptRequired;
		            		if(deptRequired){
		       				 	jQuery("#singleChartFilterCheck_search_dept").addClass('required');
		       			 	}else{
		       				 	jQuery("#singleChartFilterCheck_search_dept").removeClass('required');
		       			 	}
		            	}
		            }
		        });
    	}
    	if(statisticsAuto){
    		jQuery("#statisticsSingleChartCheck_tableDiv").css("overflow","auto");
    		statisticsSingleChartCheckReload();
    	}else{
    		jQuery("#statisticsSingleChartCheck_tableDiv").css("overflow","");
    		jQuery("#statisticsSingleChartCheck_column2DSingleDiv").css("overflow","");
			jQuery("#statisticsSingleChartCheck_line2DSingleDiv").css("overflow","");
			jQuery("#statisticsSingleChartCheck_pieDiv").text('请点击查询按钮!');
			jQuery("#statisticsSingleChartCheck_pieDiv").css('top','200px');
			jQuery("#statisticsSingleChartCheck_column2DSingleDiv").text('请点击查询按钮!');
			jQuery("#statisticsSingleChartCheck_column2DSingleDiv").css('top','200px');
			jQuery("#statisticsSingleChartCheck_line2DSingleDiv").text('请点击查询按钮!');
			jQuery("#statisticsSingleChartCheck_line2DSingleDiv").css('top','200px');
			jQuery("#statisticsSingleChartCheck_tableDiv").text('请点击查询按钮!');
			jQuery("#statisticsSingleChartCheck_tableDiv").css('top','200px');
    	}
  	});
	function statisticsSingleChartCheckReload(){
		 if(jQuery("#singleChartFilterCheck_search_dept").hasClass('required')&&!jQuery("#singleChartFilterCheck_search_dept_id").val()){
			  alertMsg.error("请选择需要查询的部门！");
			  return;
		  }
          if("${id}"){
        	var deptIds=jQuery("#singleChartFilterCheck_search_dept_id").val();
      		var searchDateFrom=jQuery("#singleChartFilterCheck_search_date_from").val();
      		var searchDateTo=jQuery("#singleChartFilterCheck_search_date_to").val();
      		var hisTime=jQuery("#singleChartFilterCheck_search_snapTime").val();
      		var urlString = "${ctx}/statisticsTargetGridEdit?";
      	    	 jQuery.ajax({
      		            url:urlString,
      		            data: {oper:'chart',itemId:"${id}",deptIds:deptIds,searchDateFrom:searchDateFrom,searchDateTo:searchDateTo,hisTime:hisTime},
      		            type: 'post',
      		            dataType: 'json',
      		            async:false,
      		            error: function(data){
      		            },
      		            success: function(data){
      		 			pieChart=data.chartXMLMap["pieChart"];
      		 	        column2DSingleChart=data.chartXMLMap["column2DSingleChart"];
      		 	        line2DSingleChart=data.chartXMLMap["line2DSingleChart"];
      		 	        jsonStr=data.chartXMLMap["jsonStr"];
      		 	        title=data.chartXMLMap["title"];
      		 	     	subTitle=data.chartXMLMap["subTitle"];
 		 	   	    	baseFontSize=data.chartXMLMap["baseFontSize"];
      		            }
      		        });
      		statisticsSingleChartCheckLoad();
       	  }else{
       		alertMsg.error("请选择统计项。");
	        return;
       	  }
	}
	function appendChartStyles(text){
		var prevText = '';
		if(text){
			prevText = text.substring(0,text.indexOf('</chart>'));
		}
		var html = "<styles>"+
			"<definition><style name='myCaptionSize' type='font' size='14'/></definition>"+
			"<application><apply toObject='Caption' styles='myCaptionSize' /><apply toObject='subCaption' styles='myCaptionSize' /></application>"+
			"</styles>";
		text = prevText+html+"</chart>";
		return text;
	}
	function statisticsSingleChartCheckLoad(){
		var myChartId = null;
		var chart = null;
		var jsonobj = null;
		var tableHtmlDiv = null;
	    var tableTop = 10;
		if(!pieChart){
			jQuery("#statisticsSingleChartCheck_tableDiv").css("overflow","");
			jQuery("#statisticsSingleChartCheck_column2DSingleDiv").css("overflow","");
			jQuery("#statisticsSingleChartCheck_line2DSingleDiv").css("overflow","");
			jQuery("#statisticsSingleChartCheck_pieDiv").text('无数据');
			jQuery("#statisticsSingleChartCheck_pieDiv").css('top','200px');
			jQuery("#statisticsSingleChartCheck_column2DSingleDiv").text('无数据');
			jQuery("#statisticsSingleChartCheck_column2DSingleDiv").css('top','200px');
			jQuery("#statisticsSingleChartCheck_line2DSingleDiv").text('无数据');
			jQuery("#statisticsSingleChartCheck_line2DSingleDiv").css('top','200px');
		}else{
			var tabsContentWidth = jQuery("#statisticsSingleChartCheck_tabsContent").width();
			var tabsContentHeight = jQuery("#statisticsSingleChartCheck_tabsContent").height();
			tabsContentWidth = tabsContentWidth - 100;
			tabsContentHeight = tabsContentHeight - 10;
			if(tabsContentWidth <= 200){
				tabsContentWidth = 200;
			}
			if(tabsContentHeight <= 100){
				tabsContentHeight = 100;
			}
			tabsContentHeight = 300;
			jQuery("#statisticsSingleChartCheck_tableDiv").css("top","");
			jQuery("#statisticsSingleChartCheck_pieDiv").css('top','');
			jQuery("#statisticsSingleChartCheck_tableDiv").css("overflow","auto");
			jQuery("#statisticsSingleChartCheck_column2DSingleDiv").css("overflow","auto");
			jQuery("#statisticsSingleChartCheck_line2DSingleDiv").css("overflow","auto");
			jQuery("#statisticsSingleChartCheck_column2DSingleDiv").css('top','');
			jQuery("#statisticsSingleChartCheck_line2DSingleDiv").css('top','');
			myChartId =	"ChartId"+ new Date().getTime();
			charIDPie3D=myChartId;
			chart = new FusionCharts("${ctx}/fusionCharts/Charts/Pie3D.swf", myChartId, tabsContentWidth, tabsContentHeight, "0", "1");
			chart.setTransparent(true);
			chart.addParam("wmode","Opaque");
			chart.setDataXML(appendChartStyles(pieChart));
			chart.render("statisticsSingleChartCheck_pieDiv");
			myChartId ="ChartId"+ new Date().getTime();
			if(jsonStr){
				jsonobj = eval(jsonStr);
				if(jsonobj.length){
					var jsonobjWidth = 30 * jsonobj.length;
					if(jsonobjWidth > tabsContentWidth){
						tabsContentWidth = jsonobjWidth;
					}
					if(jsonobjWidth > 8000){
						tabsContentWidth = 8000;
					}
				}
			}
			tableTop = tabsContentHeight/4;
			tableHtmlDiv = '<div style="height:'+tabsContentHeight+'px;">';
			chart =new FusionCharts("${ctx}/fusionCharts/Charts/Column3D.swf", myChartId, tabsContentWidth, tabsContentHeight, "0", "1");
	  		chart.setTransparent(true);
	  		chart.addParam("wmode","Opaque");
	  		chart.setDataXML(appendChartStyles(column2DSingleChart));
	  		chart.render("statisticsSingleChartCheck_column2DSingleDiv");
	  		myChartId ="ChartId"+ new Date().getTime();
	  		chart =new FusionCharts("${ctx}/fusionCharts/Charts/Line.swf", myChartId, tabsContentWidth, tabsContentHeight, "0", "1");
	 		chart.setTransparent(true);
	 		chart.addParam("wmode","Opaque");
	 		chart.setDataXML(appendChartStyles(line2DSingleChart));
	 		chart.render("statisticsSingleChartCheck_line2DSingleDiv");
		}
		 if(!tableHtmlDiv){
		    	tableHtmlDiv = '<div>';
		   }
		var tableDivHtml = tableHtmlDiv+ '<br><div style="margin-top:'+tableTop+'px"><b style="font-size:'+baseFontSize+'">'+title+'</b></div><div style="height:5px"/><div><b style="font-size:'+baseFontSize+'">'+subTitle+'</b></div><div style="height:5px"/>';
		if(jsonStr){
			tableDivHtml += '<table class="gridtable">';
 			//var table=$("<table class=\"gridtable\">");
 		    //table.appendTo($("#statisticsSingleChart_tableDiv"));
			tableDivHtml += '<tr>';
			//var tr=$("<tr></tr>");
			//var td='';
			var rowName='';
			//tr.appendTo(table);
			tableDivHtml += '<td class="chartThStyle">'+' '+'</td>';
			//td=$("<td class='chartThStyle'>"+' '+"</td>");
	        //td.appendTo(tr);
 			jQuery.each(jsonobj,function(key,value){  
 				//td=$("<td class='chartThStyle'>"+value.columnName+"</td>");
 	            //td.appendTo(tr);
 	           tableDivHtml += '<td class="chartThStyle">'+value.columnName+'</td>';;
 	           rowName = value.rowName;
 			});
 			tableDivHtml += '</tr><tr>';
 			//tr=$("<tr></tr>");
			//tr.appendTo(table);
			//td=$("<td class='chartThStyle'>"+rowName+"</td>");
	        //td.appendTo(tr);
	        tableDivHtml += '<td class="chartThStyle">'+rowName+'</td>';
 			jQuery.each(jsonobj,function(key,value){  
 				if(value.columnName=='合计'){
 					tableDivHtml += '<td class="chartTdStyle"><b>'+value.value+'</b></td>';
 					//td=$("<td class='chartTdStyle'><b>"+value.value+"</b></td>");
 				}else{
 					tableDivHtml += '<td class="chartTdStyle">'+value.value+'</td>';
 					//td=$("<td class='chartTdStyle'>"+value.value+"</td>");
 				}
 	            //td.appendTo(tr);
 			});   
 			tableDivHtml += '</tr></table></div>';
 			//$("#statisticsSingleChart_tableDiv").append("</table>");
 		}
		jQuery("#statisticsSingleChartCheck_tableDiv")[0].innerHTML = tableDivHtml;
	}
	function getSingleChartFilterCheckDeptHisNode(){
		var hisTime =jQuery("#singleChartFilterCheck_search_snapTime").val();
		jQuery("#singleChartFilterCheck_search_dept").treeselect({
			dataType:"url",
			optType:"multi",
			url:'getHrDeptHisNode?hisTime='+hisTime,
			exceptnullparent:false,
			selectParent:false,
			minWidth:'230px',
			lazy:false
		});
	}
</script>
<style type="text/css">
table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	margin:auto;
	border-width: 1px;
	border-color:  #0066FF;
	border-collapse: collapse;
}
.chartTdStyle{
	text-align:center;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color:  #0066FF;
	background-color: #ffffff;
}
.chartThStyle{
	text-align:center;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color:  #0066FF;
	background-color: #F0F0F0;
}
</style>
<div class="page">
	<div class="pageHeader" id="statisticsSingleChartCheck_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="statisticsSingleChartCheck_search_form" >
					<label style="float:none;white-space:nowrap" id="singleChartFilterCheck_snapTimelabel">
						<s:text name='历史时间'/>:
					 	<input type="text"  id="singleChartFilterCheck_search_snapTime"  class="Wdate" style="height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" id="singleChartFilterCheck_deptlabel">
						<s:text name='singleChartFilter.dept'/>:
						<input type="hidden" id="singleChartFilterCheck_search_dept_id">
					 	<input type="text" id="singleChartFilterCheck_search_dept"  onfocus="getSingleChartFilterCheckDeptHisNode()">
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" id="singleChartFilterCheck_datelabel">
						<span id="singleChartFilterCheck_date"></span>:
						<input type="text"	id="singleChartFilterCheck_search_date_from" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'singleChartFilterCheck_search_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="singleChartFilterCheck_search_date_to" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'singleChartFilterCheck_search_date_from\')}'})">
					</label>&nbsp;&nbsp;
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="statisticsSingleChartCheckReload()"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
				<%-- <div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="statisticsSingleChartCheckReload()"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div> --%>
			</div>
	</div>
	<div class="pageContent" style="overflow:hidden">
	<div class="panelBar" id="statisticsSingleChartCheck_toolbuttonbar">
		<ul class="toolBar">
<!-- 			<li>  -->
<%-- 				<a id="statisticsSingleChartCheck_addFilter" class="changebutton"  href="javaScript:"><span><s:text name="statisticsSingleChart.addFilter" /></span></a> --%>
<!-- 			</li>  -->
<!-- 			<li> -->
<!-- 				<a id="statisticsSingleChartCheck_exportPicture" class="exportbutton" href="javaScript:" ><span>导出图片</span></a> -->
<!-- 			</li> -->
			<li>
				<a id="statisticsSingleChartCheck_exportExcel" class="excelbutton" href="javaScript:" ><span>导出表格</span></a>
			</li>
		</ul> 
	</div>
			<div class="tabs" style="overflow:hidden" layoutH="68">  
    			<div class="tabsHeader">  
       				 <div class="tabsHeaderContent">  
           				 <ul>  
              				 <li class="tabsss">  
                    			 <span>表格</span>  
                			 </li>  
                			 <li class="tabsss">  
                  				 <span>圆饼图</span>  
              				 </li>  
                			 <li class="tabsss">  
                    			<span>直方图</span>  
               				 </li> 
               				  <li class="tabsss">  
                    			<span>折线图</span>  
               				 </li>  
            			</ul>  
       				 </div>  
    			</div>  
    		<div class="tabsContent" style="overflow:hidden" id="statisticsSingleChartCheck_tabsContent"> 
    			<div style="background-color:#FFF;margin: 0 auto;" layoutH="110"><div id="statisticsSingleChartCheck_tableDiv"  style="margin: 0 auto; width: 99%;height: 99%;  position: absolute;text-align: center;"></div> </div>  
    			<div style="background-color:#FFF;margin: 0 auto;" layoutH="110"><div id="statisticsSingleChartCheck_pieDiv"  style="margin: 0 auto; width: 99%;height: 99%;  position: absolute;text-align: center;" ></div></div>  
    			<div style="background-color:#FFF;margin: 0 auto;" layoutH="110"><div id="statisticsSingleChartCheck_column2DSingleDiv"  style="margin: 0 auto;height: 99%; width: 99%;  position: absolute;text-align: center;" ></div></div>   
      			<div style="background-color:#FFF;margin: 0 auto;" layoutH="110"><div id="statisticsSingleChartCheck_line2DSingleDiv"   style="margin: 0 auto;height: 99%; width: 99%;  position: absolute;text-align: center;"></div></div>
       		 </div>  
		 </div>  
		</div>
</div>
