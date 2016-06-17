<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var reportDefineCode = "${code}";
var reportDefineReportXml = "";
var reportPlanDefine = {
		key:"${random}_reportPlan_gridtable",
		main:{
			SetSource : '${ctx}/home/supcan/userdefine/datasource.xml',
			//Build : '${ctx}/home/supcan/userdefine/blank.xml',
			Build : 'getDefineReportXml?code=${code}',
			Load :''
		},
		event:{
			"Load":function( id,p1, p2, p3, p4){
			},
			"Opened":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				grid.func("AddUserFunctions", "${ctx}/home/supcan/userdefine/func.xml");
				grid.func("SetAutoCalc","0");
				grid.func("CallFunc","2");
				grid.func("SetBatchFunctionURL","batchFunc \r\n functions=10000;timeout=9999 \r\n user=normal");
			},
			"Toolbar":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				if(p1=="104"){
					var reportXml = grid.func("GetFileXML", "");
					console.log(reportXml);
					$.ajax({
			            url: 'saveDefineReportXml',
			            type: 'post',
			            dataType: 'json',
			            data :{code:reportDefineCode,reportXml:reportXml},
			            async:false,
			            error: function(data){
			            alertMsg.error("系统错误！");
			            },
			            success: function(data){
			            	formCallBack(data);
			            }
			        });
					grid.func("CancelEvent", "");
				}
			}
		},
		callback:{
			
		}
	}; 
	if(reportDefineReportXml){
		reportDefineReportXml = reportDefineReportXml.replaceAll("&#034;","\"");
		reportDefineReportXml = reportDefineReportXml.replaceAll("&lt;","<");
		reportDefineReportXml = reportDefineReportXml.replaceAll("&gt;",">");
		//alert(reportDefineReportXml);
		//reportPlanDefine.main.Build = reportDefineReportXml;
	}
	
    supcanGridMap.put('reportPlan_gridtable_${random}',reportPlanDefine); 
 	jQuery(document).ready(function(){
 		//reportPlanDefine.main.Build = initreportColModel();
 		//alert(reportPlanDefine.main.Build);
 		tabResize();
 		
 		insertReportToDiv("${random}_reportPlan_gridtable_container","reportPlan_gridtable_${random}","","100%");
 		var grid = eval("(reportPlan_gridtable_${random})");
 		setTimeout(function(){
 		//grid.func("build",reportDefineReportXml);
 		},200);
 	});
 	function initreportColModel(){
			var reportXml = '<?xml version="1.0" encoding="UTF-8" ?>';
			reportXml += '<Report>';
			reportXml += '<WorkSheet name="sheet">';
			reportXml += '<Properties>';
			reportXml += '<BackGround bgColor="#FFFFFF"/>';
			reportXml += '<Other isRowHeightAutoExtendAble="false" AutoBreakLine="1" LineDistance="1"/>';
			reportXml += '</Properties>';
			reportXml += '<Fonts>';
			for(var index in supCanFont){
				var supCanFontTemp = supCanFont[index];
				var faceName = supCanFontTemp.faceName;
				var bold = supCanFontTemp.bold;
				var underline = supCanFontTemp.underline;
				var weight;//400/700 对应 非粗体/粗体
				if(bold === "0"){
					weight = "400";
				}else{
					weight = "700";
				}
				reportXml += '<Font faceName="'+faceName+'" underline="'+underline+'" height="-13" weight="'+weight+'"/>';
			}
//	 		reportXml += '<Font faceName="Verdana" height="-13" weight="400"/>';
//	 		reportXml += '<Font faceName="Verdana" height="-13" weight="400"/>';
			reportXml += '</Fonts>';
			reportXml += '<Table>';
			reportXml += '</Table>';
			
			reportXml += '<PrintPage>';
			reportXml += '<Paper>';
			reportXml += '<Margin left="3" top="3" right="3" bottom="3"/>';
			reportXml += '</Paper>';
			reportXml += '<Page align="center" vAlign="middle" isIgnoreValidBorder="true">';
			//每页打印行数RowsPerPage；垂直打印页数MultiPageV；水平打印页数MultiPageH;printSequence超宽时的打印顺序0 / 1, 先纵后横/先横后纵
			//reportXml += '<Page-break printSequence="0" dataSourceRowsPerPage="1" dataSourceMultiPageV="12" dataSourceMultiPageH="1">';
			//reportXml += '<FixedRowCols headerRows="1" footerRows="0"/>';
			//reportXml += '</Page-break>';
			reportXml += '<PageCode isPrint="false"/>';
			reportXml += '</Page>';
			reportXml += '</PrintPage>';
			reportXml += '</WorkSheet>';
			//reportXml += dataSourceXml;
			reportXml += '</Report>';
	    	return reportXml;
		
    }
 	function sourcepayinSum1(checkperiod1,checkperiod2,deptId,chargeType){
 		//return checkperiod1;
 		console.log(chargeType);
 		var sum;
 		var sql = "select sum(amount) from v_sourcepayin where checkPeriod BETWEEN '"+checkperiod1+"' and '"+checkperiod2+"' and kdDeptId='"+deptId+"'";
 		$.ajax({
            url: 'getBySql?sql='+sql,
            type: 'post',
            dataType: 'json',
            async:false,
            error: function(data){
            alertMsg.error("系统错误！");
            },
            success: function(data){
            	console.log(data.sqlResult);
            	sum = data.sqlResult;
            }
        });
 		
 		return sum;
 	}
 	var batchCell = {time:0,pretreatment:0,cellLength:0,cellNum:0,over:0,rs:{},
 		start:function(){
 			debugger;
	 		batchCell.timer=setInterval(function(){
	 			console.log(batchCell.pretreatment);
	 			if(batchCell.pretreatment=3){
	 				batchCell.doAjax();
	 				clearInterval(batchCell.timer);
	 			}else{
	 				if(batchCell.cellLength>batchCell.cellNum){
	 					batchCell.cellNum = batchCell.cell.length;
	 				}else{
	 					batchCell.pretreatment++;
	 				}
	 			}
	 		},0);
 		},
 		doAjax:function(){
 			console.log("a");
 			$.ajax({
 	            url: 'getListBySql?sql='+batchCell.sql,
 	            type: 'post',
 	            dataType: 'json',
 	            async:false,
 	            error: function(data){
 	            alertMsg.error("系统错误！");
 	            },
 	            success: function(data){
 	            	console.log(this.pretreatment);
 	            	var grid = eval("(reportPlan_gridtable_${random})");
 	            	var sqlMap = data.sqlMap;
 	            	if(sqlMap){
 	            		for(var i in sqlMap){
 	            			var rs = sqlMap[i];
 	            			batchCell.rs[rs.k] = rs.v;
 	            		}
 	            		batchCell.over = 1;
 	            	}
 	            }
 	        });
 		}
 	};
 	
 	function xxx(){
 		
 	}
 	
 	function batchSourcepayinSum(key,checkperiod1,checkperiod2,chargeType){
 		var sql = "select kdDeptId k,sum(amount) v from v_sourcepayin where checkPeriod BETWEEN '"+checkperiod1+"' and '"+checkperiod2+"' GROUP BY kdDeptId";
 		batchCell.pretreatment = 1;
 		batchCell.sql = sql;
 		if(batchCell.over==0){
 			batchCell.doAjax();
 		}
 		var sum;
 		debugger;
 		while(true){
 			console.log("w");
 			if(batchCell.over==1){
 				var value = batchCell.rs[key];
 				if(value){
 					return value;
 				}else{
 					return "";
 				}
 			}
 		}
 	}
 	
 	function showReportFuncion(show){
 		var grid = eval("(reportPlan_gridtable_${random})");
 		if(show==0){
 			grid.func("SetAutoCalc","0");
			grid.func("CallFunc","2");
			grid.func("CallFunc","143");
 		}else{
 			grid.func("SetAutoCalc","10000");
			grid.func("CallFunc","64");
			grid.func("CallFunc","163");
 		}
 	}
 	function ssss(){
 		var grid = eval("(reportPlan_gridtable_${random})");
 		grid.func("SetCellData","D2\r\n1111");
 	}
 	
 	function batchFunc(){
 		var grid = eval("(reportPlan_gridtable_${random})");
 		grid.func("SetBatchFunctionURL","batchFunc \r\n functions=50;timeout=9999 \r\n user=normal");
 	}
 </script>
 <div class="page" style="height: 100%;">
	<div id="defineReportContent" class="pageContent" style="height: 100%;">
	<div style="margin:2px;height:25px">
		<div class="buttonActive" style="float:left">
		<div class="buttonContent">
			<button type="button" onclick="showReportFuncion(0)">编辑报表</button>
		</div>
		</div>
		<div class="buttonActive" style="float:left">
			<div class="buttonContent">
				<button type="button" onclick="showReportFuncion(1)">预览</button>
			</div>
		</div>
		<!-- <div class="buttonActive" style="float:left">
			<div class="buttonContent">
				<button type="button" onclick="ssss(1)">预览</button>
			</div>
		</div> -->
	</div>
	<div id="${random}_reportPlan_gridtable_container" layoutH=75 style="margin-left:2px;margin-right:2px;margin-buttom:2px"></div>
	</div> 
 </div>