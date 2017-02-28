
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var bmLoanbillFormDefine = {
		key:"bmLoanbillForm_${random}",
		main:{
			Build : 'getFormDesignerXml?formId=loanbill',
			Load : 'getDataXml?sql=select * from bm_loanbill where billId=\'${suId}\''
		},
		event:{
			EditChanged : function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				var text = grid.func("GetText",p1);
				if(p1=='personId'){
					grid.func("SetValue", "personName\r\n"+text);
				}else if(p1=='expendDeptId'){
					grid.func("SetValue", "expendDeptName\r\n"+text);
				}else if(p1=='centralDeptId'){
					grid.func("SetValue", "centralDeptIdName\r\n"+text);
				}
			},
			'loanBillDetail.EditChanged' : function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				grid.func("SetValue", "money\r\n1000");
				if(p2=='apply'){
					sumBillApplyMoney();
				}
			},
			'loanBillDetail.Load' : function( id,p1, p2, p3, p4){
				sumBillApplyMoney();
			}
			
		},
		callback:{
			onComplete:function(id){
				var grid = eval("("+id+")");
				//grid.func("callfunc", "102 \r\n id=Table1;asForm=true");
				grid.func("loanBillDetail.Build", "getFormDetailDesignerXml?formId=loanbill"); 
				grid.func("loanBillDetail.Load", 'getDataXml?sql=select * from bm_loanbill_detail where billId=\'${suId}\''); 
				//grid.func("loanBillDetail.insertRows", "0 \r\n 10"); 
				
				if("${editable}"=="0"){
					var inputIds = grid.func("GetObjectIds","");
					var inputIdArr = inputIds.split(",");
					for(var idIndex in inputIdArr){
						var  inputId = inputIdArr[idIndex];
						grid.func("SetObjectProp",inputId+"\r\nenable\r\nfalse");
					}
				}
				
				if("${entityIsNew}"=="true"){
					grid.func("SetValue","billCode\r\n${suCode}");
					grid.func("loanbillDetail.insertRows", "0 \r\n 10"); 
				}else{
					if("${editable}"!="0"){
						grid.func("loanbillDetail.insertRows", "-1 \r\n 1"); 
					}
				}
			}
		}
}
var printReportDefine_${random} = {
		key:"printReport_${random}",
		main:{
			//SetSource : 'getPrintReportDataSourceXml?formId=loanbill',
			Build : 'getPrintReportXml?formId=loanbill&from=user',
			Load :''
		},
		event:{
			
		},
		callback:{
			onComplete:function(id){
				var grid = eval("("+id+")");
			}
		}
}
	supcanGridMap.put('bmLoanbillForm_${random}',bmLoanbillFormDefine);
	supcanGridMap.put('printReport_${random}',printReportDefine_${random});
	jQuery(document).ready(function() {
		insertFreeFormToDiv('bmLoanbillForm_${random}_container','bmLoanbillForm_${random}', '');
		insertReportToDiv('bmLoanbillForm_print_${random}_container','printReport_${random}', '');
		
		jQuery("#saveLoanbill_${random}").click(function(){
			var grid = eval("(bmLoanbillForm_${random})");
			var formDataXml = grid.func("GetChangedXML","");
			var gridDataXml = grid.func("loanbillDetail.GetChangedXML","");
			if(formDataXml==0||gridDataXml==0){
				alertMsg.error("没有修改内容，无需保存！");
				return ;
			}
			$.post("saveBmLoanbill", {
	    		"_" : $.now(),entityIsNew:'${entityIsNew}',formDataXml:formDataXml,gridDataXml:gridDataXml,navTabId:'bmLoanbill_gridtable'
			}, function(data) {
				formCallBack(data);
			});
		});
		
	    jQuery("#printpreview_${random}").click(function(){
	    	var grid = eval("(bmLoanbillForm_${random})");
	    	grid.func("PrintPreview","");
	    });
	    
	    jQuery("#loanbillPrintpreview_${random}").click(function(){
	    	var gridForm = eval("(bmLoanbillForm_${random})");
	    	//var gridDetail = eval("(bmExpenseClaimForm_${random}.expenseClaimDetail)");
	    	var gridReport = eval("(printReport_${random})");
	    	gridReport.func("SetSource", "form \r\n " + gridForm.func("Export", ""));
	    	
	    	gridReport.func("SetSource", "detail \r\n " + gridForm.func("loanbillDetail.Export", ""));
	    	gridReport.func("Calc", "");
	    	gridReport.func("callfunc", "18");
	    });
	    jQuery("#bmLoanbill_gridtable_print_${random}").click(function(){
	    	var gridForm = eval("(bmLoanbillForm_${random})");
	    	//var gridDetail = eval("(bmExpenseClaimForm_${random}.expenseClaimDetail)");
	    	var gridReport = eval("(printReport_${random})");
	    	gridReport.func("SetSource", "form \r\n " + gridForm.func("Export", ""));
	    	
	    	gridReport.func("SetSource", "detail \r\n " + gridForm.func("loanbillDetail.Export", ""));
	    	gridReport.func("Calc", "");
	    	gridReport.func("callfunc", "11");
	    });
	    jQuery("#loanbillPrintset_${random}").click(function(){
			var fullHeight = jQuery("#container").innerHeight();
        	var fullWidth = jQuery("#container").innerWidth();
            var url = "designPrintReport?formId=loanbill&from=user";
    		var winTitle='设置打印报表';
    		$.pdialog.open(url,'setPrintReport',winTitle, {mask:true,max:true,width : fullWidth,height : fullHeight});
    		stopPropagation();
		});
	});
	
	function sumBillApplyMoney(){
		var grid = eval("(bmLoanbillForm_${random})");
		var rows = grid.func("loanBillDetail.getRows", "");
		var appaySum = 0 ;
		for(var i=0;i<rows;i++){
			var applyMoney = grid.func("loanBillDetail.GetCellData",i+" \r\napply");
			if(applyMoney){
				applyMoney = parseFloat(applyMoney);
				appaySum += applyMoney;
			}
			//console.log(".."+appaySum);
		}
		grid.func("SetValue", "money\r\n"+appaySum);
		var money1 = upDigit(appaySum);
		grid.func("SetValue", "ex_money\r\n"+money1);
	}
</script>

<div class="page" style="height: 100%;overflow: hidden">
	<div class="pageContent" style="height: 100%;overflow: hidden">
		<div class="panelBar">
			<ul class="toolBar">
				<s:if test="editable!=0">
					<li><a id="saveLoanbill_${random}" class="savebutton"  href="javaScript:"><span>保存</span></a></li>
				</s:if>
				<s:else>
					<li><a class="savebutton" style='color:#808080;' href="javaScript:"><span>保存</span></a></li>
				</s:else>
				<!-- <li><a id="bmLoanbill_gridtable_del" class="checkbutton"  href="javaScript:"><span>审核</span></a></li>
				<li><a id="bmLoanbill_gridtable_del" class="checkbutton"  href="javaScript:"><span>消审</span></a></li> -->
				<li><a id="loanbillPrintset_${random}" class="settlebutton"  href="javaScript:"><span>打印设置</span></a></li>
				<li><a id="loanbillPrintpreview_${random}" class="previewbutton"  href="javaScript:"><span>打印预览</span></a></li>
				<li><a id="bmLoanbill_gridtable_print_${random}" class="printbutton"  href="javaScript:"><span>打印</span></a></li>
			</ul>
		</div>
		<div id="bmLoanbillForm_${random}_container" layoutH="30" class="grid-wrapdiv">
			
		</div>
		<div id="bmLoanbillForm_print_${random}_container" style="height:1px">
			
		</div>
	</div>
</div>