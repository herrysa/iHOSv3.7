
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var bmLoanbillFormDefine = {
		key:"bmLoanbillForm_${random}",
		main:{
			Build : 'getFormDesignerXml?formId=loanbill',
			Load : 'getDataXml?sql=select * from bm_loanbill where billId=\'${billId}\''
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
				}
				if(p1=='expendDeptId'){
					grid.func("SetValue", "expendDeptName\r\n"+text);
				}
				if(p1=='centralDeptId'){
					grid.func("SetValue", "centralDeptIdName\r\n"+text);
				}
			},
			'loanBillDetail.EditChanged' : function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				if(p2=='apply'){
					var rows = grid.func("loanBillDetail.getRows", "");
					var appaySum = 0 ;
					for(var i=0;i<rows;i++){
						var applyMoney = grid.func("loanBillDetail.GetCellData",i+" \r\napply");
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
			}
			
		},
		callback:{
			onComplete:function(id){
				var grid = eval("("+id+")");
				//grid.func("callfunc", "102 \r\n id=Table1;asForm=true");
				grid.func("loanBillDetail.Build", "getFormDetailDesignerXml?formId=loanbill"); 
				grid.func("loanBillDetail.Load", 'getDataXml?sql=select * from bm_loanbill_detail where billId=\'${billId}\''); 
				//grid.func("loanBillDetail.insertRows", "0 \r\n 10"); 
				
				if("${entityIsNew}"=="true"){
					grid.func("SetValue","billCode\r\n${billCode}");
					grid.func("loanBillDetail.insertRows", "0 \r\n 10"); 
				}
			}
		}
}
var loanBillDetailDefine = {
		key:"loanBillDetail",
		main:{
			Build : '',
			Load : ''
		},
		event:{
			
			EditChanged : function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				console.log(p2);
				if(p2=='apply'){
					var rows = grid.func("getRows", "");
					var appaySum = 0 ;
					for(var i=0;i<rows;i++){
						var applyMoney = grid.func("GetCellData",i+" \r\n"+apply);
						applyMoney += appaySum;
					}
					bmLoanbillForm_${random}.func("SetValue", "money\r\n"+applyMoney);
				}
			}
			
		},
		callback:{
		}
}
	supcanGridMap.put('bmLoanbillForm_${random}',bmLoanbillFormDefine);
	supcanGridMap.put('loanBillDetail',loanBillDetailDefine);
	jQuery(document).ready(function() {
		insertFreeFormToDiv('bmLoanbillForm_${random}_container','bmLoanbillForm_${random}', '');
		
		jQuery("#saveBmLoanbill_${random}").click(function(){
			var grid = eval("(bmLoanbillForm_${random})");
			var formDataXml = grid.func("GetChangedXML","");
			console.log(formDataXml);
			var gridDataXml = grid.func("loanBillDetail.GetChangedXML","");
			console.log(gridDataXml);
			if(formDataXml==0){
				return ;
			}
			$.post("saveBmLoanbill", {
	    		"_" : $.now(),entityIsNew:'${entityIsNew}',formDataXml:formDataXml,gridDataXml:gridDataXml,navTabId:'bmLoanbill_gridtable'
			}, function(data) {
				formCallBack(data);
			});
		});
	});
</script>

<div class="page" style="height: 100%;">
	<div class="pageContent" style="height: 100%;">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="bmLoanbill_gridtable_add_c" class="addbutton" href="javaScript:" ><span>新单</span></a></li>
				<li><a id="saveBmLoanbill_${random}" class="savebutton"  href="javaScript:"><span>保存</span></a></li>
				<li><a id="bmLoanbill_gridtable_del" class="checkbutton"  href="javaScript:"><span>审核</span></a></li>
				<li><a id="bmLoanbill_gridtable_del" class="checkbutton"  href="javaScript:"><span>消审</span></a></li>
				<li><a id="bmLoanbill_gridtable_del" class="previewbutton"  href="javaScript:"><span>打印预览</span></a></li>
				<li><a id="bmLoanbill_gridtable_del" class="printbutton"  href="javaScript:"><span>打印</span></a></li>
			</ul>
		</div>
		<div id="bmLoanbillForm_${random}_container" layoutH="30" class="grid-wrapdiv">
			
		</div>
	</div>
</div>