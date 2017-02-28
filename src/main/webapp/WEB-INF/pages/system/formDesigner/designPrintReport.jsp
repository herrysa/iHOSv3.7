<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script>
var printReportDefine = {
		key:"printReport_${random}",
		main:{
			//SetSource : 'getPrintReportDataSourceXml?formId=${formId}',
			Build : 'getPrintReportXml?formId=${formId}&from=${from}',
			Load :''
		},
		event:{
			"OnReady":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				
			},
			"Toolbar":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				if(p1=="104"){
					var formXml = grid.func("GetFileXML", "");
					$.ajax({
			            url: 'savePrintReportXml',
			            type: 'post',
			            dataType: 'json',
			            data :{formId:"${formId}",formXml:formXml},
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
			onComplete:function(id){
				var grid = eval("("+id+")");
				var dsIds = grid.func("GetSources","");
				var dsIdArr = dsIds.split("\n");
				var formDs = false , detailDs = false;
				for(var dsIndex=0;dsIndex<dsIdArr.length;dsIndex++){
				//console.log(dsIdArr[dsIndex]);
					
					if(dsIdArr[dsIndex].trim()=='form'){
						formDs = true;
					}
					if(dsIdArr[dsIndex].trim()=='detail'){
						detailDs = true;
					}
				}
				//
				//console.log(formDs+":"+detailDs);
				if(!formDs){
					grid.func("SetDSXML","getPrintReportFormDataSourceXml?formId=${formId}");
				}
				if(!detailDs){
					grid.func("SetDSXML", "getPrintReportDetailDataSourceXml?formId=${formId}");
				}
				//grid.func("callfunc", "102 \r\n id=Table1;asForm=true");
				//grid.func("SetSource", "ds0 \r\n getDataXml");
				//grid.func("SetSource", "ds0 \r\n getDataXml?sql=select * from bm_loanbill where billId='b48df6c7c0a8660112326d2eaf95be3c'");
				//grid.func("SetSource", "ds1 \r\n getDataXml?sql=select * from bm_loanbill_detail where billId='12'");
				//grid.func("SetSource", "ds0 \r\n getDataXml?sql=select * from bm_loanbill where billId='b48df6c7c0a8660112326d2eaf95be3c'");
				//grid.func("Calc", ""); 
			}
		}
}

supcanGridMap.put('printReport_${random}',printReportDefine); 

	jQuery(document).ready(function() {
		insertReportToDiv('${random}_printReport_container','printReport_${random}', '');
		
		jQuery("#savePrintReport_${random}").click(function(){
			var grid = eval("(printReport_${random})");
			var formXml = grid.func("GetFileXML", "");
			$.ajax({
	            url: 'savePrintReportXml',
	            type: 'post',
	            dataType: 'json',
	            data :{formId:"${formId}",formXml:formXml,from:"${from}"},
	            async:false,
	            error: function(data){
	            alertMsg.error("系统错误！");
	            },
	            success: function(data){
	            	formCallBack(data);
	            }
	        });
		});
		
		
		jQuery("#createPrintReport_${random}").click(function(){
			var grid = eval("(printReport_${random})");
			$.ajax({
	            url: 'createPrintReport',
	            type: 'post',
	            dataType: 'json',
	            data :{formId:"${formId}"},
	            async:false,
	            error: function(data){
	            alertMsg.error("系统错误！");
	            },
	            success: function(data){
	            	formCallBack(data);
	            }
	        });
		}); 
	});
</script>
<div class="page" style="height: 100%;">
	<div class="pageContent" style="height: 100%;">
	<div class="panelBar">
			<ul class="toolBar">
				<li><a id="savePrintReport_${random}" class="savebutton"  href="javaScript:"><span>保存</span></a></li>
				<%-- <li><a id="setDsSql_${random}" class="initbutton"  href="javaScript:"><span>设置表单取数SQL</span></a></li> --%>
			</ul>
	</div>
	<div layoutH=28 style="overflow: hidden;">
		<div id="${random}_printReport_container" style="height:100%;overflow: hidden;"></div>
	</div>
	</div>
</div>