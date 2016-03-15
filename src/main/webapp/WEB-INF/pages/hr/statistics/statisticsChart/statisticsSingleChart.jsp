
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script language="JavaScript" src="${ctx}/fusionCharts/JSClass/FusionCharts.js"></script>
<%-- <script language="JavaScript" src="${ctx}/fusionCharts/JSClass/FusionChartsExportComponent.js"></script> --%>
<script type="text/javascript">
	var statisticsSingleChartLayout;
	var pieChart='';
	var column2DSingleChart='';
	var line2DSingleChart='';
	var jsonStr='';
	var title='';
	var subTitle='';
	var baseFontSize='';
	var charIDPie3D='';
	jQuery(document).ready(function() { 
		var hrDepartmentCurrentFullSize1 = jQuery("#container").innerHeight()-118;
		jQuery("#statisticsSingleChart_container").css("height",hrDepartmentCurrentFullSize1);
		$('#statisticsSingleChart_container').layout({ 
			applyDefaultStyles: false ,
			west__size : 250,
			spacing_open:5,//边框的间隙  
			spacing_closed:5,//关闭时边框的间隙 
			resizable :true,
			resizerClass :"ui-layout-resizer-blue",
			slidable :true,
			resizerDragOpacity :1, 
			resizerTip:"可调整大小",
			onresize_end : function(paneName,element,state,options,layoutName){
			}
		});
   		statisticsSingleChartLeftTree();
   		if("${statisticsDetail.shijianName}"){
   			jQuery("#singleChartFilter_date")[0].innerHTML="${statisticsDetail.shijianName}";
   		}else{
   			jQuery("#singleChartFilter_datelabel").hide();
   		}
   		if(!"${statisticsDetail.deptFK}"){
   			jQuery("#singleChartFilter_deptlabel").hide();
   		}
   	jQuery("#statisticsSingleChart_addFilter").unbind( 'click' ).bind("click",function(){
   		if(jQuery("#singleChartFilter_search_dept").hasClass('required')&&!jQuery("#singleChartFilter_search_dept_id").val()){
			  alertMsg.error("请选择需要查询的部门！");
			  return;
		}
   		var zTree = $.fn.zTree.getZTreeObj("statisticsSingleChartTreeLeft");  
	    var nodes = zTree.getSelectedNodes(); 
	    if(nodes.length!=1 || nodes[0].subSysTem!='item'){
	    	alertMsg.error("请选择一个统计项。");
      		return;
	    }
	    var url = "editSingleChartFilter?id="+nodes[0].id;
	    url=url+"&statisticsCode="+"${statisticsCode}"+"&navTabId=singleChartFilter";
	    var winTitle='<s:text name="singleChart.filterEdit"/>';
		$.pdialog.open(url,'editSingleChartFilter',winTitle, {mask:true,width : 700,height : 600});
   	});
//    	var sql="select deptId id,name,ISNULL(parentDept_id,orgCode) parent,(1-leaf) isParent  from v_hr_department_current where disabled=0 and deleted=0 "
//     	sql=sql+" union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent from T_Org where disabled=0 ";
//     	jQuery("#singleChartFilter_search_dept").treeselect({
//     		optType:"multi",
//     		dataType:'sql',
//     		sql:sql,
//     		exceptnullparent:true,
//     		lazy:false,
//     		selectParent:false,
//     		callback:null
//     	});
    	
    	//导出图片
//     	jQuery("#statisticsSingleChart_exportPicture").unbind( 'click' ).bind("click",function(){
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
		jQuery("#statisticsSingleChart_exportExcel").unbind( 'click' ).bind("click",function(){
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
  	});
	function statisticsSingleChartLeftTree() {
		$.get("makeStatisticsItemTree?statisticsCode="+"${statisticsCode}", {
			"_" : $.now()
		}, function(data) {
			var statisticsSingleChartTreeData = data.statisticsItemTreeNodes;
			var statisticsSingleChartTree = $.fn.zTree.init($("#statisticsSingleChartTreeLeft"),
					ztreesetting_statisticsSingleChartLeft, statisticsSingleChartTreeData);
			var nodes = statisticsSingleChartTree.getNodes();
			statisticsSingleChartTree.expandNode(nodes[0], true, false, true);
		});
	}
	var ztreesetting_statisticsSingleChartLeft = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false
			},
			callback : {
				beforeDrag:zTreeBeforeDrag,
				onClick : onModuleClick
			},
			data : {
				key : {
					name : "name"
				},
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId"
				}
			}
	};
	function zTreeBeforeDrag(treeId, treeNodes) {
	    return false;
	};
	function onModuleClick(event, treeId, treeNode, clickFlag) { 
		var deptIds=jQuery("#singleChartFilter_search_dept_id").val();
		var searchDateFrom=jQuery("#singleChartFilter_search_date_from").val();
		var searchDateTo=jQuery("#singleChartFilter_search_date_to").val();
		var hisTime=jQuery("#singleChartFilter_search_snapTime").val();
		var urlString = "${ctx}/statisticsTargetGridEdit";
		if(treeNode.subSysTem=='item'){
			 if(treeNode.deptRequired){
				 jQuery("#singleChartFilter_search_dept").addClass('required');
			 }else{
				 jQuery("#singleChartFilter_search_dept").removeClass('required');
			 }
 			 if(!treeNode.statisticsAuto){
 				jQuery("#statisticsSingleChart_tableDiv").css("overflow","");
 				jQuery("#statisticsSingleChart_column2DSingleDiv").css("overflow","");
 				jQuery("#statisticsSingleChart_line2DSingleDiv").css("overflow","");
 				jQuery("#statisticsSingleChart_pieDiv").text('请点击查询按钮!');
 				jQuery("#statisticsSingleChart_pieDiv").css('top','200px');
 				jQuery("#statisticsSingleChart_column2DSingleDiv").text('请点击查询按钮!');
 				jQuery("#statisticsSingleChart_column2DSingleDiv").css('top','200px');
 				jQuery("#statisticsSingleChart_line2DSingleDiv").text('请点击查询按钮!');
 				jQuery("#statisticsSingleChart_line2DSingleDiv").css('top','200px');
 				jQuery("#statisticsSingleChart_tableDiv").text('请点击查询按钮!');
 				jQuery("#statisticsSingleChart_tableDiv").css('top','200px');
 				return;
			 }else{
				jQuery("#statisticsSingleChart_tableDiv").css("overflow","auto");
			 }
	    	 jQuery.ajax({
		            url:urlString,
		            data: {oper:"chart",itemId:treeNode.id,deptIds:deptIds,searchDateFrom:searchDateFrom,searchDateTo:searchDateTo,hisTime:hisTime},
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
		}else{
		 	pieChart='';
	    	column2DSingleChart='';
	    	line2DSingleChart='';
	    	jsonStr='';
		}
		statisticsSingleChartLoad();
	}
	function statisticsSingleChartReload(){
		  if(jQuery("#singleChartFilter_search_dept").hasClass('required')&&!jQuery("#singleChartFilter_search_dept_id").val()){
			  alertMsg.error("请选择需要查询的部门！");
			  return;
		  }
		  var zTree = $.fn.zTree.getZTreeObj("statisticsSingleChartTreeLeft"); 
          var nodes = zTree.getSelectedNodes();
          if(nodes.length==1&&nodes[0].subSysTem=='item'){
        	var deptIds=jQuery("#singleChartFilter_search_dept_id").val();
      		var searchDateFrom=jQuery("#singleChartFilter_search_date_from").val();
      		var searchDateTo=jQuery("#singleChartFilter_search_date_to").val();
      		var hisTime=jQuery("#singleChartFilter_search_snapTime").val();
      		var urlString = "${ctx}/statisticsTargetGridEdit?";
      	    	 jQuery.ajax({
      		            url:urlString,
      		            data: {oper:'chart',itemId:nodes[0].id,deptIds:deptIds,searchDateFrom:searchDateFrom,searchDateTo:searchDateTo,hisTime:hisTime},
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
      		statisticsSingleChartLoad();
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
	/*
	function parseXMLString(text){
		var xmlDoc;
		try {//针对IE和基于IE内核的浏览器  
		  	xmlDoc=new ActiveXObject("Microsoft.XMLDOM");  
		  	xmlDoc.async="false";  
		    xmlDoc.loadXML(text);
		}  
		catch(e){  
			try {//针对Firefox, Opera等其它浏览器.  
				var parser=new DOMParser();  
			    xmlDoc=parser.parseFromString(text,"text/xml");  
		  	}  
		  	catch(e) {
			  	alert(e.message)
			}  
		}  
		var html = "<styles>"+
					"<definition><style name='myCaptionSize' type='font' size='14'/></definition>"+
    				"<application><apply toObject='Caption' styles='myCaptionSize' /></application>"+
    				"</styles>";
		var chart = xmlDoc.getElementsByTagName("chart")[0];
		chart.innerHTML += html;
		var chartXml = xmlToString(chart);
		return chartXml;		
	}
	function xmlToString(xmlObj){
		if(document.all){ //IE浏览器
	        return xmlObj.xml;
	    }else {//其他浏览器
	        return (new XMLSerializer()).serializeToString(xmlObj);
	    }
	}
	*/
	function statisticsSingleChartLoad(){
		var myChartId ="ChartId"+ new Date().getTime();
		charIDPie3D = myChartId;
	    var chart = null;
	    var jsonobj = null;
	    var tableHtmlDiv = null;
	    var tableTop = 10;
	    if(!pieChart){
	    	jQuery("#statisticsSingleChart_tableDiv").css("overflow","");
	    	jQuery("#statisticsSingleChart_column2DSingleDiv").css("overflow","");
			jQuery("#statisticsSingleChart_line2DSingleDiv").css("overflow","");
			jQuery("#statisticsSingleChart_pieDiv").text('无数据');
			jQuery("#statisticsSingleChart_pieDiv").css('top','200px');
			jQuery("#statisticsSingleChart_column2DSingleDiv").text('无数据');
			jQuery("#statisticsSingleChart_column2DSingleDiv").css('top','200px');
			jQuery("#statisticsSingleChart_line2DSingleDiv").text('无数据');
			jQuery("#statisticsSingleChart_line2DSingleDiv").css('top','200px');
			jQuery("#statisticsSingleChart_tableDiv").text('无数据');
			jQuery("#statisticsSingleChart_tableDiv").css('top','200px');
			return;
		 }else{
			var tabsContentWidth = jQuery("#statisticsSingleChart_tabsContent").width();
			var tabsContentHeight = jQuery("#statisticsSingleChart_tabsContent").height();
			tabsContentWidth = tabsContentWidth - 100;
			tabsContentHeight = tabsContentHeight - 10;
			if(tabsContentWidth <= 200){
				tabsContentWidth = 200;
			}
			if(tabsContentHeight <= 100){
				tabsContentHeight = 100;
			}
			jQuery("#statisticsSingleChart_tableDiv").css("overflow","auto");
			jQuery("#statisticsSingleChart_column2DSingleDiv").css("overflow","auto");
			jQuery("#statisticsSingleChart_line2DSingleDiv").css("overflow","auto");
			jQuery("#statisticsSingleChart_tableDiv").css("top","");
			jQuery("#statisticsSingleChart_pieDiv").css("top","");
			jQuery("#statisticsSingleChart_column2DSingleDiv").css("top","");
			jQuery("#statisticsSingleChart_line2DSingleDiv").css("top","");
	    	chart =	new FusionCharts("${ctx}/fusionCharts/Charts/Pie3D.swf", myChartId, tabsContentWidth, tabsContentHeight, "0", "1");
			chart.setTransparent(true);
			chart.addParam("wmode","Opaque");
			chart.setDataXML(appendChartStyles(pieChart));
			//chart.setDataXML(pieChart);
			chart.render("statisticsSingleChart_pieDiv");
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
	  		chart.render("statisticsSingleChart_column2DSingleDiv");
	  		myChartId ="ChartId"+ new Date().getTime();
	  		chart =new FusionCharts("${ctx}/fusionCharts/Charts/Line.swf", myChartId, tabsContentWidth, tabsContentHeight, "0", "1");
	 		chart.setTransparent(true);
	 		chart.addParam("wmode","Opaque");
	 		chart.setDataXML(appendChartStyles(line2DSingleChart));
	 		chart.render("statisticsSingleChart_line2DSingleDiv");
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
		jQuery("#statisticsSingleChart_tableDiv")[0].innerHTML = tableDivHtml;
	}
	function getSingleChartFilterDeptHisNode(){
		var hisTime =jQuery("#singleChartFilter_search_snapTime").val();
		jQuery("#singleChartFilter_search_dept").treeselect({
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
<div class="pageHeader" id="statisticsSingleChart_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
			<form id="statisticsSingleChart_search_form" class="queryarea-form" >
					<label class="queryarea-label" id="singleChartFilter_snapTimelabel">
						<s:text name='历史时间'/>:
					 	<input type="text"  id="singleChartFilter_search_snapTime"  class="Wdate" style="height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					</label>
					<label class="queryarea-label" id="singleChartFilter_deptlabel">
						<s:text name='singleChartFilter.dept'/>:
						<input type="hidden" id="singleChartFilter_search_dept_id">
					 	<input type="text" id="singleChartFilter_search_dept"  onfocus="getSingleChartFilterDeptHisNode()">
					</label>
					<label class="queryarea-label" id="singleChartFilter_datelabel">
						<span id="singleChartFilter_date"></span>:
						<input type="text"	id="singleChartFilter_search_date_from" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'singleChartFilter_search_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="singleChartFilter_search_date_to" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'singleChartFilter_search_date_from\')}'})">
					</label>
						<%-- <div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="statisticsSingleChartReload()"><s:text name='button.search'/></button>
							</div>
						</div> --%>
							<div class="buttonActive" style="float: right;">
								<div class="buttonContent">
									<button type="button" onclick="statisticsSingleChartReload()"><s:text name='button.search'/></button>
								</div>
							</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">





	<div class="panelBar" id="statisticsSingleChart_toolbuttonbar">
		<ul class="toolBar">
			<li> 
				<a id="statisticsSingleChart_addFilter" class="changebutton"  href="javaScript:"><span><s:text name="statisticsSingleChart.addFilter" /></span></a>
			</li> 
<!-- 			<li> -->
<!-- 				<a id="statisticsSingleChart_exportPicture" class="exportbutton" href="javaScript:" ><span>导出图片</span></a> -->
<!-- 			</li> -->
			<li>
				<a id="statisticsSingleChart_exportExcel" class="excelbutton" href="javaScript:" ><span>导出表格</span></a>
			</li>
		</ul> 
	</div>
		<div id="statisticsSingleChart_container">
			<div id="statisticsSingleChart_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div style="margin-left:20px;margin-bottom:2px;">
				<span style="vertical-align:middle;text-decoration:underline;cursor:pointer;float: right;" onclick="toggleExpandTreeWithShowFlag(this,'statisticsSingleChartTreeLeft',true)">展开</span>
				</div>
				<div id="statisticsSingleChartTreeLeft" class="ztree"></div>
			</div>
			<div id="statisticsSingleChart_layout-center" class="pane ui-layout-center" style="overflow:hidden">
			<div class="tabs">  
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
    		<div class="tabsContent" style="overflow:hidden" id="statisticsSingleChart_tabsContent"> 
    			<div style="background-color:#FFF;margin: 0 auto;" layoutH="134"><div id="statisticsSingleChart_tableDiv"  style="margin: 0 auto; width: 99%;  position: absolute;text-align: center;overflow:auto;font-weight: bold;"></div> </div>  
    			<div style="background-color:#FFF;margin: 0 auto;" layoutH="134"><div id="statisticsSingleChart_pieDiv"  style="margin: 0 auto; width: 99%;  position: absolute;text-align: center;font-weight: bold;" ></div></div>  
    			<div style="background-color:#FFF;margin: 0 auto;" layoutH="134"><div id="statisticsSingleChart_column2DSingleDiv"  style="margin: 0 auto; width: 99%;  position: absolute;text-align: center;font-weight: bold;" ></div></div>   
      			<div style="background-color:#FFF;margin: 0 auto;" layoutH="134"><div id="statisticsSingleChart_line2DSingleDiv"   style="margin: 0 auto; width: 99%;  position: absolute;text-align: center;font-weight: bold;"></div></div>
       		 </div>  
		 </div>  
		</div>
</div>
</div>
</div>
