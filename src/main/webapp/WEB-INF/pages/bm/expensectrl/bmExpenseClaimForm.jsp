
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var bmExpenseClaimFormDefine = {
		key:"bmExpenseClaimForm_${random}",
		main:{
			Build : 'getFormDesignerXml?formId=expenseClaim',
			Load : 'getDataXml?sql=select * from bm_expenseClaim where claimId=\'${suId}\''
		},
		event:{
			"Toolbar":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				if(p1=="104"){
					var formXml = grid.func("GetFileXML", "");
					$.ajax({
			            url: 'saveFormDesignerXml',
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
			},
			EditChanged : function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				var text = grid.func("GetText",p1);
				if(p1=='personId'){
					grid.func("SetValue", "personName\r\n"+text);
				}else if(p1=='deptId'){
					grid.func("SetValue", "deptName\r\n"+text);
				}else if(p1=='orgId'){
					grid.func("SetValue", "orgName\r\n"+text);
				}else if(p1=='assumeDeptId'){
					grid.func("SetValue", "assumeDeptName\r\n"+text);
				}
			},
			'expenseClaimDetail.EditChanged' : function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				if(p2=='apply'){
					sumClaimApplyMoney();
				}
			},
			'expenseClaimDetail.Load' : function( id,p1, p2, p3, p4){
				sumClaimApplyMoney();
			}
			
		},
		callback:{
			onComplete:function(id){
				var grid = eval("("+id+")");
				//grid.func("callfunc", "102 \r\n id=Table1;asForm=true");
				grid.func("expenseClaimDetail.Build", "getFormDetailDesignerXml?formId=expenseClaim"); 
				grid.func("expenseClaimDetail.Load", 'getDataXml?sql=select * from bm_expenseClaim_detail where claimId=\'${suId}\''); 
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
					grid.func("SetValue","claimCode\r\n${suCode}");
					grid.func("expenseClaimDetail.insertRows", "0 \r\n 10"); 
				}else{
					if("${editable}"!="0"){
						grid.func("expenseClaimDetail.insertRows", "-1 \r\n 1"); 
					}
				}
			}
		}
}
var printReportDefine_${random} = {
		key:"printReport_${random}",
		main:{
			//SetSource : 'getPrintReportDataSourceXml?formId=expenseClaim',
			Build : 'getPrintReportXml?formId=expenseClaim',
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
	supcanGridMap.put('bmExpenseClaimForm_${random}',bmExpenseClaimFormDefine);
	supcanGridMap.put('printReport_${random}',printReportDefine_${random});
	jQuery(document).ready(function() {
		insertFreeFormToDiv('bmExpenseClaimForm_${random}_container','bmExpenseClaimForm_${random}', '');
		insertReportToDiv('bmExpenseClaim_print_${random}_container','printReport_${random}', '');
		
		jQuery("#saveBmExpenseClaim_${random}").click(function(){
			var grid = eval("(bmExpenseClaimForm_${random})");
			var formDataXml = grid.func("GetChangedXML","");
			var gridDataXml = grid.func("expenseClaimDetail.GetChangedXML","");
			if(formDataXml==0||gridDataXml==0){
				alertMsg.error("没有修改内容，无需保存！");
				return ;
			}
			$.post("saveBmExpenseClaim", {
	    		"_" : $.now(),entityIsNew:'${entityIsNew}',formDataXml:formDataXml,gridDataXml:gridDataXml,navTabId:'bmExpenseClaim_gridtable'
			}, function(data) {
				formCallBack(data);
			});
		});
		
	    jQuery("#claimPrintpreview_${random}").click(function(){
	    	var gridForm = eval("(bmExpenseClaimForm_${random})");
	    	//var gridDetail = eval("(bmExpenseClaimForm_${random}.expenseClaimDetail)");
	    	var gridReport = eval("(printReport_${random})");
	    	gridReport.func("SetSource", "form \r\n " + gridForm.func("Export", ""));
	    	
	    	gridReport.func("SetSource", "detail \r\n " + gridForm.func("expenseClaimDetail.Export", ""));
	    	gridReport.func("Calc", "");
	    	gridReport.func("callfunc", "18");
	    });
	    
	    jQuery("#bmExpenseClaim_gridtable_print_${random}").click(function(){
	    	var gridForm = eval("(bmExpenseClaimForm_${random})");
	    	//var gridDetail = eval("(bmExpenseClaimForm_${random}.expenseClaimDetail)");
	    	var gridReport = eval("(printReport_${random})");
	    	gridReport.func("SetSource", "form \r\n " + gridForm.func("Export", ""));
	    	
	    	gridReport.func("SetSource", "detail \r\n " + gridForm.func("expenseClaimDetail.Export", ""));
	    	gridReport.func("Calc", "");
	    	gridReport.func("callfunc", "11");
	    });
	    jQuery("#claimPrintset_${random}").click(function(){
			var fullHeight = jQuery("#container").innerHeight();
        	var fullWidth = jQuery("#container").innerWidth();
            var url = "designPrintReport?formId=expenseClaim&from=user";
    		var winTitle='设置打印报表';
    		$.pdialog.open(url,'setPrintReport',winTitle, {mask:true,max:true,width : fullWidth,height : fullHeight});
    		stopPropagation();
		});
	});
	function sumClaimApplyMoney(){
		var grid = eval("(bmExpenseClaimForm_${random})");
		var rows = grid.func("expenseClaimDetail.getRows", "");
		var appaySum = 0 ;
		for(var i=0;i<rows;i++){
			var applyMoney = grid.func("expenseClaimDetail.GetCellData",i+" \r\napply");
			if(applyMoney){
				applyMoney = parseFloat(applyMoney);
				appaySum += applyMoney;
			}
			console.log(".."+appaySum);
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
					<li><a id="saveBmExpenseClaim_${random}" class="savebutton"  href="javaScript:"><span>保存</span></a></li>
				</s:if>
				<s:else>
					<li><a class="savebutton" style='color:#808080;' href="javaScript:"><span>保存</span></a></li>
				</s:else>
				<%-- <s:if test="editable!=0">
					<li><a id="bmExpenseClaim_gridtable_del" class="checkbutton"  href="javaScript:"><span>审核</span></a></li>
				</s:if>
				<s:else>
					<li><a class="checkbutton" style='color:#808080;' href="javaScript:"><span>审核</span></a></li>
				</s:else>
				<s:if test="editable!=0">
					<li><a id="bmExpenseClaim_gridtable_del" class="checkbutton"  href="javaScript:"><span>审核</span></a></li>
				</s:if>
				<s:else>
					<li><a class="checkbutton" style='color:#808080;' href="javaScript:"><span>审核</span></a></li>
				</s:else>
				<li><a id="bmExpenseClaim_gridtable_del" class="checkbutton"  href="javaScript:"><span>消审</span></a></li> --%>
				<li><a id="claimPrintset_${random}" class="settlebutton"  href="javaScript:"><span>打印设置</span></a></li>
				<li><a id="claimPrintpreview_${random}" class="previewbutton"  href="javaScript:"><span>打印预览</span></a></li>
				<li><a id="bmExpenseClaim_gridtable_print_${random}" class="printbutton"  href="javaScript:"><span>打印</span></a></li>
			</ul>
		</div>
		<div id="bmExpenseClaimForm_${random}_container" layoutH="30" class="grid-wrapdiv">
			
		</div>
		<div id="bmExpenseClaim_print_${random}_container" style="height:1px">
			
		</div>
	</div>
</div>