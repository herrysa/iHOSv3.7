
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script language="JavaScript" src="${ctx}/fusionCharts/JSClass/FusionCharts.js"></script>
<script type="text/javascript">
var column2DMultiSeriesChart='';
var line2DMultiSeriesChart='';
var itemId="";
var itemIdSecond="";
var jsonStr="";
var jsonStrAll="";
var mccKeyId="";
var title='';
var subTitle='';
var baseFontSize='';
var deptFlag=false;
var deptSecondFlag=false;
	jQuery(document).ready(function() { 
		jQuery("#multiChart_addFilter").unbind( 'click' ).bind("click",function(){
			if(!itemId||!itemIdSecond){
				alertMsg.error("请选择统计项。");
		        return;
			}
			if(itemId==itemIdSecond){
				alertMsg.error("请选择两个不同的统计项。");
		        return;
			}
			if(jQuery("#multiChart_search_dept").hasClass('required')&&!jQuery("#multiChart_search_dept_id").val()||jQuery("#multiChart_search_item").hasClass('required')&&!jQuery("#multiChart_search_item_id").val()||jQuery("#multiChart_search_itemSecond").hasClass('required')&&!jQuery("#multiChart_search_itemSecond_id").val()){
				  alertMsg.error("请选择需要查询的部门！");
				  return;
			}
			var url = "editMultiChartFilter?id="+itemId+"&itemIdSecond="+itemIdSecond;
			url=url+"&statisticsCode="+"${statisticsCode}";
			var winTitle='<s:text name="singleChart.filterEdit"/>';
			$.pdialog.open(url,'editMultiChartFilter',winTitle, {mask:true,width : 700,height : 500});
		   });
		
		if("${statisticsDetail.shijianName}"){
   			jQuery("#multiChart_date")[0].innerHTML="${statisticsDetail.shijianName}";
   		}else{
   			jQuery("#multiChart_datelabel").hide();
   		}
   		if(!"${statisticsDetail.deptFK}"){
   			jQuery("#multiChart_deptlabel").hide();
   		}
   		/* var sql="";
   		sql="select deptId id,name,ISNULL(parentDept_id,orgCode) parent,(1-leaf) isParent  from v_hr_department_current where disabled=0 and deleted=0 "
		sql=sql+" union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent from T_Org where disabled=0 ";
   	 	jQuery("#multiChart_search_dept").treeselect({
  			optType:"multi",
  			dataType:'sql',
  			sql:sql,
  			exceptnullparent:true,
  			lazy:false,
  			selectParent:false,
  			callback:null
  		}); */
   		//导出图片
//     	jQuery("#multiChart_exportPicture").unbind( 'click' ).bind("click",function(){
//     		alert("请期待");
//     	});
    	//导出表格
		jQuery("#multiChart_exportExcel").unbind( 'click' ).bind("click",function(){
			if(!jsonStrAll){
    			alertMsg.error("无数据可以导出。");
          		return;
    		}
    		var gridAllDatas='{"edit":'+jsonStrAll+'}';
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
	function multiChartLoad(jsonStr){
		var myChartId = null;
		var chart = null;
		var tableHtmlDiv = null;
		var tableTop = 10;
		if(!line2DMultiSeriesChart){
			jQuery("#statisticsMultiChart_tableDiv").css("overflow","");
			jQuery("#statisticsMultiChart_line2DSingleDiv").css("overflow","");
			jQuery("#statisticsMultiChart_column2DSingleDiv").css("overflow","");
			jQuery("#statisticsMultiChart_line2DSingleDiv").text('无数据');
			jQuery("#statisticsMultiChart_line2DSingleDiv").css('top','200px');
			jQuery("#statisticsMultiChart_column2DSingleDiv").text('无数据');
			jQuery("#statisticsMultiChart_column2DSingleDiv").css('top','200px');
			jQuery("#statisticsMultiChart_tableDiv").text('无数据');
			jQuery("#statisticsMultiChart_tableDiv").css('top','200px');
			return;
		}else{
			var tabsContentWidth = jQuery("#multiChart_tabsContent").width();
			var tabsContentHeight = jQuery("#multiChart_tabsContent").height();
			tabsContentWidth = tabsContentWidth - 100;
			tabsContentHeight = tabsContentHeight - 15;
			if(tabsContentWidth <= 200){
				tabsContentWidth = 200;
			}
			if(tabsContentHeight <= 100){
				tabsContentHeight = 100;
			}
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
			jQuery("#statisticsMultiChart_tableDiv").css("overflow","auto");
			jQuery("#statisticsMultiChart_line2DSingleDiv").css("overflow","auto");
			jQuery("#statisticsMultiChart_column2DSingleDiv").css("overflow","auto");
			jQuery("#statisticsMultiChart_tableDiv").css("top","");
			jQuery("#statisticsMultiChart_line2DSingleDiv").css("top","");
			jQuery("#statisticsMultiChart_column2DSingleDiv").css("top","");
			myChartId ="ChartId"+ new Date().getTime();
	   	   chart =new FusionCharts("${ctx}/fusionCharts/Charts/MSLine.swf", myChartId, tabsContentWidth, tabsContentHeight, "0", "0");
	  	   chart.setTransparent(true);
	  	   chart.addParam("wmode","Opaque");
	  	   chart.setDataXML(appendChartStyles(line2DMultiSeriesChart));
	  	   chart.render("statisticsMultiChart_line2DSingleDiv");
	  	 	myChartId ="ChartId"+ new Date().getTime();
	  	 	chart =new FusionCharts("${ctx}/fusionCharts/Charts/MSColumn3D.swf", myChartId, tabsContentWidth, tabsContentHeight, "0", "0");
	  	   chart.setTransparent(true);
	  	   chart.addParam("wmode","Opaque");
	  	   chart.setDataXML(appendChartStyles(column2DMultiSeriesChart));
	  	   chart.render("statisticsMultiChart_column2DSingleDiv");
		}
	  	 multiChartTableLoad(jsonStr,tableTop,tableHtmlDiv);
	}
	function multiChartTableLoad(jsonStr,tableTop,tableHtmlDiv){
		if(!tableHtmlDiv){
		    tableHtmlDiv = '<div>';
		}
		var tableDivHtml = tableHtmlDiv+ '<br><div style="margin-top:'+tableTop+'px"><b style="font-size:'+baseFontSize+'">'+title+'</b></div><div style="height:5px"/><div><b style="font-size:'+baseFontSize+'">'+subTitle+'</b></div><div style="height:5px"/>';
		//jQuery("#statisticsMultiChart_tableDiv")[0].innerHTML='<br><div><b style="font-size:'+baseFontSize+'">'+title+'</b></div><div style="height:5px"/><div><b style="font-size:'+baseFontSize+'">'+subTitle+'</b></div><div style="height:5px"/>';
		if(jsonStr){
				tableDivHtml += '<table class="gridtable">';
	    	    //var table=jQuery("<table class=\"gridtable\">");
	    		//table.appendTo(jQuery("#statisticsMultiChart_tableDiv"));
	   			var jsonobj=eval(jsonStr);
	   			var columnNames=new Array();
	   			var rowNames=new Array();
	   			jQuery.each(jsonobj,function(key,value){  
 					if(jQuery.inArray(value.columnName, columnNames)=="-1"){
 						columnNames.push(value.columnName);
 					}
 					if(jQuery.inArray(value.rowName, rowNames)=="-1"){
 						rowNames.push(value.rowName);
 					}
 				});    
	   			tableDivHtml += '<tr>';
	   			//var tr=jQuery("<tr></tr>");
	   			//var td='';
   			//tr.appendTo(table);
   			tableDivHtml += '<td class="chartThStyle">'+' '+'</td>';
   			//td=$("<td class='chartThStyle'>"+' '+"</td>");
   			//td.appendTo(tr);
	   			for(var i=0;i<columnNames.length;i++){
	   				tableDivHtml += '<td class="chartThStyle">'+columnNames[i]+'</td>';
	   				//td=$("<td class='chartThStyle'>"+columnNames[i]+"</td>");
	            	//td.appendTo(tr);
	   			}
	   			tableDivHtml += '<td class="chartThStyle">'+'合计'+'</td></tr>';
	   			//td=$("<td class='chartThStyle'>"+'合计'+"</td>");
				//td.appendTo(tr);
				jsonStrAll='[';
	   			for(var rowi=0;rowi<rowNames.length;rowi++){
	   				var rowName=rowNames[rowi];
	   				tableDivHtml += '<tr>';
	   				//tr=jQuery("<tr></tr>");
	   		 		//tr.appendTo(table);
	   		 		tableDivHtml += '<td class="chartThStyle">'+rowName+'</td>';
	   		 		//td=$("<td class='chartThStyle'>"+rowName+"</td>");
         			//td.appendTo(tr);
         			var columnSumValue=0;
         			for(var columni=0;columni<columnNames.length;columni++){
         				var columnName=columnNames[columni];
         				jQuery.each(jsonobj,function(i,n){
         					if(jsonobj[i].columnName==columnName&&jsonobj[i].rowName==rowName){
         						tableDivHtml += '<td class="chartTdStyle">'+jsonobj[i].value+'</td>';
         						//td=$("<td class='chartTdStyle'>"+jsonobj[i].value+"</td>");
     	            			//td.appendTo(tr);
     	            			columnSumValue+=parseInt(jsonobj[i].value);
     	            			jsonStrAll+='{"rowName":'+'"'+rowName+'",'+'"columnName":'+'"'+columnName+'",'+'"value":'+'"'+jsonobj[i].value+'"},';
         					}
         				});
         			}
         			tableDivHtml += '<td class="chartTdStyle"><b>'+columnSumValue+'</b></td></tr>';
         			//td=$("<td class='chartTdStyle'><b>"+columnSumValue+"</b></td>");
         			jsonStrAll+='{"rowName":'+'"'+rowName+'",'+'"columnName":'+'"合计",'+'"value":'+'"'+columnSumValue+'"},';
         			//td.appendTo(tr);
	   			}
	   			tableDivHtml += '<tr>';
	   			//tr=jQuery("<tr></tr>");
	 			//tr.appendTo(table);
	 			tableDivHtml += '<td class="chartThStyle">'+'合计'+'</td>';
	 			//td=$("<td class='chartThStyle'>"+'合计'+"</td>");
				//td.appendTo(tr);
	   			var allRowSumValue=0;
	   			for(var columni=0;columni<columnNames.length;columni++){
    			var columnName=columnNames[columni];
    			var rowSumValue=0;
    			for(var rowi=0;rowi<rowNames.length;rowi++){
		   				var rowName=rowNames[rowi];
		   				jQuery.each(jsonobj,function(i,n){
     				if(jsonobj[i].columnName==columnName&&jsonobj[i].rowName==rowName){
     					rowSumValue+=parseInt(jsonobj[i].value);
     					}
    				});
	   				}
    			allRowSumValue += rowSumValue;
    			tableDivHtml += '<td class="chartTdStyle"><b>'+rowSumValue+'</b></td>';
    			//td=$("<td class='chartTdStyle'><b>"+rowSumValue+"</b></td>");
    			jsonStrAll+='{"rowName":'+'"合计",'+'"columnName":'+'"'+columnName+'",'+'"value":'+'"'+rowSumValue+'"},';
         		//td.appendTo(tr);
	   			}
	   			tableDivHtml += '<td class="chartTdStyle"><b>'+allRowSumValue+'</b></td>';
	   			//td=$("<td class='chartTdStyle'><b>"+allRowSumValue+"</b></td>");
	   			jsonStrAll+='{"rowName":'+'"合计",'+'"columnName":'+'"合计",'+'"value":'+'"'+allRowSumValue+'"}';
	   			jsonStrAll+="]";
	   			tableDivHtml += '</tr></table>';
	   			jQuery("#statisticsMultiChart_tableDiv")[0].innerHTML = tableDivHtml;
    			//td.appendTo(tr);
	    		//$("#statisticsMultiChart_tableDiv").append("</table>");
	    		}
	}
	function multiChartItemChange(value){
		 var otherValue=jQuery("#multiChart_itemSecond").val();
		  if(otherValue==value){
			  alertMsg.error("不能选择两个相同的统计项。");
			  jQuery("#multiChart_item").val(itemId);
			  return;
		  }
		  itemId=value;
		  jQuery("#multiChart_search_item").removeAttr("onclick").removeAttr("onfocus");
		  jQuery("#multiChart_search_item").val("");
		  jQuery("#multiChart_search_item_id").val("");
		  if(value){
	      jQuery.ajax({
	             url: 'statisticsItemGridList?filter_EQS_id='+value,
	             data: {},
	             type: 'post',
	             dataType: 'json',
	             async:false,
	             error: function(data){
	             },
	             success: function(data){
	             	if(data.statisticsItems&&data.statisticsItems.length>0){
	             		jQuery("#multiChart_itemLabel").show();
	             		var statisticsItem = data.statisticsItems[0];
	             		var name = statisticsItem.name;
	             		var dynamicStatistics = statisticsItem.dynamicStatistics;
	             		var dynamicTable = statisticsItem.dynamicTable;
	             		var dynamicTablePk = statisticsItem.dynamicTablePk;
	             		var dynamicCode = statisticsItem.dynamicCode;
	             		var deptRequired = statisticsItem.deptRequired;
	             		if(deptRequired){
	             			jQuery("#multiChart_search_dept").addClass('required');
	       			 	}else{
	       				 	jQuery("#multiChart_search_dept").removeClass('required');
	       			 	}
	             		jQuery("#multiChart_itemName")[0].innerHTML=name;
	             		var hisTime =jQuery("#multiChart_search_snapTime").val();
	             		var sql="";
	             		jQuery("#multiChart_search_item").removeClass('required');
	             		if(dynamicStatistics){
	             			if( dynamicTable=="v_hr_department_current"||dynamicTable=="v_hr_department_his"){
	             				jQuery("#multiChart_search_item").treeselect({
	             		 			dataType:"url",
	             		 			optType:"multi",
	             		 			url:'getHrDeptHisNode?hisTime='+hisTime,
	             		 			exceptnullparent:false,
	             		 			selectParent:false,
	             		 			lazy:false
	             		 		});
	             				if(deptRequired){
	    	             			jQuery("#multiChart_search_item").addClass('required');
	    	       			 	}else{
	    	       				 	jQuery("#multiChart_search_item").removeClass('required');
	    	       			 	}
	             				deptFlag=true;
	             			}else{
	             				 deptFlag=false;
	             				sql="select "+dynamicTablePk+" id,"+dynamicCode+" name from " +dynamicTable+" order by "+dynamicTablePk;
	             				 jQuery("#multiChart_search_item").treeselect({
	     	         	    		optType:"multi",
	     	         	    		dataType:'sql',
	     	         	    		sql:sql,
	     	         	    		exceptnullparent:true,
	     	         	    		lazy:false,
	     	         	    		selectParent:false,
	     	         	    		callback:null
	     	         	    	});
	             			}
	             		}else{
	             			 deptFlag=false;
	             			 sql="select id,name from sy_statistics_condition where parentItemId ='"+value+"' order by sn asc";
	             			 jQuery("#multiChart_search_item").treeselect({
	 	         	    		optType:"multi",
	 	         	    		dataType:'sql',
	 	         	    		sql:sql,
	 	         	    		exceptnullparent:true,
	 	         	    		lazy:false,
	 	         	    		selectParent:false,
	 	         	    		callback:null
	 	         	    	});
	             		}
	             	}else{
	             		jQuery("#multiChart_itemLabel").hide();
	             	}
	             }
	         });
		  }else{
			  deptFlag=false;
			  jQuery("#multiChart_itemLabel").hide();
		  }
		  if(deptFlag||deptSecondFlag){
			  jQuery("#multiChart_search_dept").removeClass('required');
			  jQuery("#multiChart_deptlabel").hide();
			  jQuery("#multiChart_search_dept_id").val("");
			  jQuery("#multiChart_search_dept").val("");
		  }else{
			  jQuery("#multiChart_deptlabel").show();
		  }
	}
	function multiChartItemSecondChange(value){
		  var otherValue=jQuery("#multiChart_item").val();
		  if(otherValue==value){
			  alertMsg.error("不能选择两个相同的统计项。");
			  jQuery("#multiChart_itemSecond").val(itemIdSecond);
			  return;
		  }
		  itemIdSecond=value;
		  jQuery("#multiChart_search_itemSecond").removeAttr("onclick").removeAttr("onfocus");
		  jQuery("#multiChart_search_itemSecond").val("");
		  jQuery("#multiChart_search_itemSecond_id").val("");
		  if(value){
	      jQuery.ajax({
	             url: 'statisticsItemGridList?filter_EQS_id='+value,
	             data: {},
	             type: 'post',
	             dataType: 'json',
	             async:false,
	             error: function(data){
	             },
	             success: function(data){
	             	if(data.statisticsItems&&data.statisticsItems.length>0){
	             		jQuery("#multiChart_itemSecondLabel").show();
	             		var statisticsItem = data.statisticsItems[0];
	             		var name = statisticsItem.name;
	             		var dynamicStatistics = statisticsItem.dynamicStatistics;
	             		var dynamicTable = statisticsItem.dynamicTable;
	             		var dynamicTablePk = statisticsItem.dynamicTablePk;
	             		var dynamicCode = statisticsItem.dynamicCode;
	             		var deptRequired = statisticsItem.deptRequired;
	             		if(deptRequired){
	             			jQuery("#multiChart_search_dept").addClass('required');
	       			 	}else{
	       				 	jQuery("#multiChart_search_dept").removeClass('required');
	       			 	}
	             		jQuery("#multiChart_itemSecondName")[0].innerHTML = name;
	             		var hisTime = jQuery("#multiChart_search_snapTime").val();
	             		var sql = "";
	             		jQuery("#multiChart_search_itemSecond").removeClass('required');
	             		if(dynamicStatistics){
	             			if( dynamicTable=="v_hr_department_current"||dynamicTable=="v_hr_department_his"){
	             		 		jQuery("#multiChart_search_itemSecond").treeselect({
	             		 			dataType:"url",
	             		 			optType:"multi",
	             		 			url:'getHrDeptHisNode?hisTime='+hisTime,
	             		 			exceptnullparent:false,
	             		 			selectParent:false,
	             		 			lazy:false
	             		 		});
	             		 		if(deptRequired){
	    	             			jQuery("#multiChart_search_itemSecond").addClass('required');
	    	       			 	}else{
	    	       				 	jQuery("#multiChart_search_itemSecond").removeClass('required');
	    	       			 	}
	             				deptSecondFlag=true;
	             			}else{
	             				deptSecondFlag=false;
	             				sql="select "+dynamicTablePk+" id,"+dynamicCode+" name from " +dynamicTable+" order by "+dynamicTablePk;
	             				 jQuery("#multiChart_search_itemSecond").treeselect({
	     	         	    		optType:"multi",
	     	         	    		dataType:'sql',
	     	         	    		sql:sql,
	     	         	    		exceptnullparent:true,
	     	         	    		lazy:false,
	     	         	    		selectParent:false,
	     	         	    		callback:null
	     	         	    	});
	             			}
	             		}else{
	             			deptSecondFlag = false;
	             			sql="select id,name from sy_statistics_condition where parentItemId ='"+value+"' order by sn asc";
	             			 jQuery("#multiChart_search_itemSecond").treeselect({
	 	         	    		optType:"multi",
	 	         	    		dataType:'sql',
	 	         	    		sql:sql,
	 	         	    		exceptnullparent:true,
	 	         	    		lazy:false,
	 	         	    		selectParent:false,
	 	         	    		callback:null
	 	         	    	});
	             		}
	             	}else{
	             		jQuery("#multiChart_itemSecondLabel").hide();
	             	}
	             	
	             }
	         });
		  }else{
			  deptSecondFlag=false;
			  jQuery("#multiChart_itemSecondLabel").hide();
		  }
		  if(deptFlag||deptSecondFlag){
			  jQuery("#multiChart_search_dept").removeClass('required');
			  jQuery("#multiChart_deptlabel").hide();
			  jQuery("#multiChart_search_dept_id").val("");
			  jQuery("#multiChart_search_dept").val("");
		  }else{
			  jQuery("#multiChart_deptlabel").show();
		  }
	}
	function multiChartReload(){
			if(!itemId||!itemIdSecond){
				alertMsg.error("请选择统计项。");
		        return;
			}
			if(itemId==itemIdSecond){
				alertMsg.error("请选择两个不同的统计项。");
		        return;
			}
			if(jQuery("#multiChart_search_dept").hasClass('required')&&!jQuery("#multiChart_search_dept_id").val()||jQuery("#multiChart_search_item").hasClass('required')&&!jQuery("#multiChart_search_item_id").val()||jQuery("#multiChart_search_itemSecond").hasClass('required')&&!jQuery("#multiChart_search_itemSecond_id").val()){
				  alertMsg.error("请选择需要查询的部门！");
				  return;
			}
			var conditionIds=jQuery("#multiChart_search_item_id").val();
			var conditionSecondIds=jQuery("#multiChart_search_itemSecond_id").val();
			var deptIds=jQuery("#multiChart_search_dept_id").val();
			var searchDateFrom=jQuery("#multiChart_search_date_from").val();
			var searchDateTo=jQuery("#multiChart_search_date_to").val();
			var hisTime=jQuery("#multiChart_search_snapTime").val();
	        var urlString = "${ctx}/statisticsTargetGridEdit";
	        jQuery.ajax({
	              url:urlString,
	              data: {itemId:itemId,itemIdSecond:itemIdSecond,oper:"chart",deptIds:deptIds,conditionIds:conditionIds,conditionSecondIds:conditionSecondIds,searchDateFrom:searchDateFrom,searchDateTo:searchDateTo,hisTime:hisTime},
	              type: 'post',
	              dataType: 'json',
	              async:false,
	              error: function(data){
	            	alertMsg.error(data.message);
	   		        return;
	              },
	              success: function(data){
	               if(data.message!="success"){
	               		alertMsg.error(data.message);
	                	return;
	               }
	               column2DMultiSeriesChart=data.chartXMLMap["column2DMultiSeriesChart"];	
	               line2DMultiSeriesChart=data.chartXMLMap["line2DMultiSeriesChart"];	
	               jsonStr=data.chartXMLMap["jsonStr"];
	               title=data.chartXMLMap["title"];
	               subTitle=data.chartXMLMap["subTitle"];
		 	   	   baseFontSize=data.chartXMLMap["baseFontSize"];
	               multiChartLoad(jsonStr);
	              }
	       });
	}
	function getmultiChartFilterDeptHisNode(){
		var hisTime =jQuery("#multiChart_search_snapTime").val();
		jQuery("#multiChart_search_dept").treeselect({
			dataType:"url",
			optType:"multi",
			url:'getHrDeptHisNode?hisTime='+hisTime,
			exceptnullparent:false,
			selectParent:false,
  			minWidth:'230px',
			lazy:false
		});
	}
	function multiChartTreeLoad(obj){
		var hisTime = obj.value;
		if(hisTime){
			if(deptFlag){
				jQuery("#multiChart_search_item_id").val("");
				jQuery("#multiChart_search_item").val("");
				jQuery("#multiChart_search_item").treeselect({
		 			dataType:"url",
		 			optType:"multi",
		 			url:'getHrDeptHisNode?hisTime='+hisTime,
		 			exceptnullparent:false,
		 			selectParent:false,
		 			lazy:false
		 		});
			}
			if(deptSecondFlag){
				jQuery("#multiChart_search_itemSecond_id").val("");
				jQuery("#multiChart_search_itemSecond").val("");
				jQuery("#multiChart_search_itemSecond").treeselect({
 		 			dataType:"url",
 		 			optType:"multi",
 		 			url:'getHrDeptHisNode?hisTime='+hisTime,
 		 			exceptnullparent:false,
 		 			selectParent:false,
 		 			lazy:false
 		 		});
			}
		}
	}
/* 	function multiChartFormSubmit(data){
		formCallBack(data);
	} */
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
	background-color:   #F0F0F0;
}
</style>
<div class="page" id="statisticsMultiChart_page">
<div class="pageHeader" id="statisticsMultiChart_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="statisticsMultiChart_search_form" class="queryarea-form">
					<label class="queryarea-label" >
						<s:text name='multiChart.item'/>:
					 	<s:select id="multiChart_item" headerKey="" headerValue="--" 
							list="itemList" listKey="value" listValue="content"  onchange="multiChartItemChange(this.value)"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
					</label>
					<label class="queryarea-label"  >
						<s:text name='multiChart.itemSecond'/>:
					 	<s:select id="multiChart_itemSecond" headerKey="" headerValue="--" 
							list="itemList" listKey="value" listValue="content"   onchange="multiChartItemSecondChange(this.value)"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
					</label>
					<label class="queryarea-label" id="multiChart_snapTimelabel">
					<s:text name='历史时间'/>:
					<input type="text"  id="multiChart_search_snapTime"  class="Wdate" style="height:15px" onFocus="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd HH:mm:ss',onpicked:multiChartTreeLoad(this)})"/>
					</label>
					<label class="queryarea-label" style="dislay:none" id="multiChart_deptlabel">
						<s:text name='singleChartFilter.dept'/>:
						<input type="hidden" id="multiChart_search_dept_id">
					 	<input type="text" id="multiChart_search_dept" onfocus="getmultiChartFilterDeptHisNode()">
					</label>
					<label class="queryarea-label"  id="multiChart_datelabel">
						<span id="multiChart_date"></span>:
						<input type="text"	id="multiChart_search_date_from" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'multiChart_search_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="multiChart_search_date_to" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'multiChart_search_date_from\')}'})">
					</label>
					
					<label class="queryarea-label" style="display:none" id="multiChart_itemLabel">
						<span id="multiChart_itemName"></span>:
						<input type="hidden" id="multiChart_search_item_id"/>
					 	<input type="text" id="multiChart_search_item" />
					</label>
					<label class="queryarea-label" style="display:none" id="multiChart_itemSecondLabel">
						<span id="multiChart_itemSecondName"></span>:
						<input type="hidden" id="multiChart_search_itemSecond_id"/>
					 	<input type="text" id="multiChart_search_itemSecond" />
					</label>
					<%-- <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="multiChartReload()"><s:text name='button.search'/></button>
						</div>
					</div> --%>
						<div class="buttonActive" style="float:right;">
							<div class="buttonContent">
								<button type="button" onclick="multiChartReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
			</div>
		</div>
	</div>
	<div class="pageContent" style="overflow:hidden">
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a id="multiChart_addFilter" class="changebutton" href="javaScript:" ><span><s:text name="statisticsSingleChart.addFilter" /></span></a>
				</li>
<!-- 				<li> -->
<!-- 					<a id="multiChart_exportPicture" class=exportbutton href="javaScript:" ><span>导出图片</span></a> -->
<!-- 				</li> -->
				<li>
					<a id="multiChart_exportExcel" class="excelbutton" href="javaScript:" ><span>导出表格</span></a>
				</li>
			</ul>
		</div>
	<div class="pageContent" style="overflow:hidden">
	<div class="tabs">  
    			<div class="tabsHeader">  
       				 <div class="tabsHeaderContent">  
           				 <ul>  
              				 <li class="tabsss">  
                    			 <span>表格</span>  
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
    		<div class="tabsContent" style="overflow:hidden" id="multiChart_tabsContent">
    			<div style="background-color:#FFF;margin: 0 auto;" layoutH="134" ><div id="statisticsMultiChart_tableDiv"  style="margin: 0 auto; width: 99%;  position: absolute;text-align: center;overflow:auto;font-weight: bold;"></div> </div>  
    			<div style="background-color:#FFF;margin: 0 auto;" layoutH="134" ><div id="statisticsMultiChart_column2DSingleDiv"  style="margin: 0 auto; width: 99%;  position: absolute;text-align: center;font-weight: bold;"></div> </div>  
    			<div style="background-color:#FFF;margin: 0 auto;" layoutH="134" ><div id="statisticsMultiChart_line2DSingleDiv"  style="margin: 0 auto; width: 99%;  position: absolute;text-align: center;font-weight: bold;"></div> </div>  
       		 </div>  
		 </div>  
	</div>
	</div>
</div>
