<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var reportDefineCode = "${code}";
var reportDefineReportXml = "${reportXml}";
var reportPlanDefine = {
		key:"${random}_reportPlan_gridtable",
		main:{
			SetSource : '${ctx}/home/supcan/userdefine/datasource.xml',
			Build : '${ctx}/home/supcan/userdefine/blank.xml',
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
			},
			"Toolbar":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				if(p1=="104"){
					var reportXml = grid.func("GetFileXML", "");
					$.ajax({
			            url: 'saveDefineReportXml?code='+reportDefineCode+'&reportXml='+reportXml,
			            type: 'post',
			            dataType: 'json',
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
 		grid.func("build",reportDefineReportXml);
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
 	function sourcepayinSum(checkperiod1,checkperiod2,deptId,chargeType){
 		//return checkperiod1;
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
	</div>
	<div id="${random}_reportPlan_gridtable_container" layoutH=75 style="margin-left:2px;margin-right:2px;margin-buttom:2px"></div>
	</div> 
 </div>