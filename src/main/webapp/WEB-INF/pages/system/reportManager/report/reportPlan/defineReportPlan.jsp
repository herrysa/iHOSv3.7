<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
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
				console.log(11111);
			}
		},
		callback:{
			
		}
	}; 
	
    supcanGridMap.put('reportPlan_gridtable_${random}',reportPlanDefine); 
 	jQuery(document).ready(function(){
 		//reportPlanDefine.main.Build = initreportColModel();
 		//alert(reportPlanDefine.main.Build);
 		insertReportToDiv("${random}_reportPlan_gridtable_container","reportPlan_gridtable_${random}","","100%");
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
 </script>
 <div class="page">
	<div class="pageContent">
		<div id="${random}_reportPlan_gridtable_div"  layoutH=50 style="margin:0px;background-color: #DFF1FE;" buttonBar="width:500;height:300">
			<input type="hidden" id="${random}_reportPlan_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="${random}_reportPlan_gridtable_container" layoutH=50 style="margin-left:15px;margin-right:15px;margin-buttom:15px"></div>
		</div>
	</div> 
 </div>