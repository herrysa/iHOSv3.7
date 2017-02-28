<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>

<script>
    var state = "${state}",showMessage="${message}";
	jQuery(document).ready(function(){
		
		
		
		
		
		
		
		makeVoucherEvent("${random}",state);
		if(state=='0'){
			alertMsg.error(showMessage);
		}
		
		/* jQuery("#findVoucherNo").blur(function(){
			var voucherNo = jQuery("#findVoucherNo").val();
			var voucherType = jQuery("#voucherType").val();
			$.ajax({
			    url: 'isExitVoucherDetailData',
			    type: 'post',
			    data:{voucherType:voucherType,voucherNo:voucherNo},
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			    },
			    success: function(data){
			        // do something with xml
			        alert(data);
			    }
			});
		}); */
		
		
		/* jQuery("input[name=assistTable_checkbox]").click(function(e){
			e.stopPropagation();
			jQuery(this).parent().parent().trigger('click');
		});
		jQuery("#assistTable>tbody>tr").unbind('click').bind('click',function(){
			//jQuery()
		}); */
		jQuery("#${random}addNewVoucher").click(function(){
	    	var url = "voucherCard";
			navTab.openTab("${random}voucherCard", url, { title:"凭证填制", fresh:true, data:{} });
	    });
		 jQuery("#${random}voucher_check").click(function(){
		    	var voucherIds = jQuery("#${random}VoucherId").val();
		    	if(voucherIds){
		    		$.ajax({
					    url: 'chanegVoucherState',
					    type: 'post',
					    data:{voucherIds:voucherIds,state:2},
					    dataType: 'json',
					    aysnc:false,
					    error: function(data){
					    },
					    success: function(data){
					        formCallBack(data);
					        navTab.reload("voucherCard", {
								title : "",
								fresh : false,
								data : {voucherId:voucherIds}
							});
					    }
					});
		    	}
		    });
		    jQuery("#${random}voucher_account").click(function(){
		    	var voucherIds = jQuery("#${random}VoucherId").val();
		    	if(voucherIds){
		    		$.ajax({
					    url: 'chanegVoucherState',
					    type: 'post',
					    data:{voucherIds:voucherIds,state:3},
					    dataType: 'json',
					    aysnc:false,
					    error: function(data){
					    },
					    success: function(data){
					        formCallBack(data);
					        navTab.reload("voucherCard", {
								title : "",
								fresh : false,
								data : {voucherId:voucherIds}
							});
					    }
					});
		    	}
		    });
		    jQuery("#${random}voucher_zf").click(function(){
		    	var voucherIds = jQuery("#${random}VoucherId").val();
		    	if(voucherIds){
		    		$.ajax({
					    url: 'chanegVoucherState',
					    type: 'post',
					    data:{voucherIds:voucherIds,state:4},
					    dataType: 'json',
					    aysnc:false,
					    error: function(data){
					    },
					    success: function(data){
					        formCallBack(data);
					        navTab.reload("voucherCard", {
								title : "",
								fresh : false,
								data : {voucherId:voucherIds}
							});
					    }
					});
		    	}
		    });
		    jQuery("#${random}voucher_error").click(function(){
		    	var voucherIds = jQuery("#${random}VoucherId").val();
		    	if(voucherIds){
		    		$.ajax({
					    url: 'chanegVoucherState',
					    type: 'post',
					    data:{voucherIds:voucherIds,state:5},
					    dataType: 'json',
					    aysnc:false,
					    error: function(data){
					    },
					    success: function(data){
					        formCallBack(data);
					        navTab.reload("voucherCard", {
								title : "",
								fresh : false,
								data : {voucherId:voucherIds}
							});
					    }
					});
		    	}
		    });
		    var keyCodeMap = {
		    		'8' : 'BackSpace',
		    		'9' : 'Tab',
		    		'12' : 'Clear ',
		    		'13' : 'Enter ',
		    		'16' : 'Shift_L',
		    		'17' : 'Ctrl',
		    		'18' : 'Alt_L',
		    		'19' : 'Pause',
		    		'20' : 'Caps_Lock',
		    		'27' : 'Escape',
		    		'32' : 'space',
		    		'35' : 'End',
		    		'36' : 'Home',
		    		'37' : 'Left',
		    		'38' : 'Up',
		    		'39' : 'Right',
		    		'40' : 'Down',
		    		'46' : 'Delete',
		    		'65' : 'A',
		    		'66' : 'B',
		    		'67' : 'C',
		    		'68' : 'D',
		    		'69' : 'E',
		    		'70' : 'F',
		    		'71' : 'G',
		    		'72' : 'H',
		    		'73' : 'I',
		    		'74' : 'J',
		    		'75' : 'K',
		    		'76' : 'L',
		    		'77' : 'M',
		    		'78' : 'N',
		    		'79' : 'O',
		    		'80' : 'P',
		    		'81' : 'Q',
		    		'82' : 'R',
		    		'83' : 'S',
		    		'84' : 'T',
		    		'85' : 'U',
		    		'86' : 'V',
		    		'87' : 'W',
		    		'88' : 'X',
		    		'89' : 'Y',
		    		'90' : 'Z'
		    };
		    
		    function keyEventRoute($this,evt){
		    	var ctrlKey = evt.ctrlKey;
		    	var keyCode = evt.keyCode;
		    	var funcKey = "";
		    	if(ctrlKey){
		    		funcKey += 'Ctrl';
		    	}
		    	var keyChar = keyCodeMap[keyCode];
		    	if(keyChar){
		    		funcKey += keyChar;
		    	}
		    	var func = voucherKeyEventMap[funcKey];
		    	if(func){
			    	func($this);
		    	}
		    }
		    
		    /* var voucherKeyEventMap = {
		    		'Ctrl+Right' : function($this){
		    			var focusInput = $this.find("input:focus");
		            	var thisName = focusInput.attr('name');
		            	if(thisName.indexOf("account")!=-1){
		            		if(jQuery("#${random}jeLineType").val()=="no"){
		            			focusInput.parent().parent().parent().find("td[name=noLine_lend]").trigger('click');
		            			return ;
		            		}
		            	}
		            	if(thisName.indexOf("withLine_loan")!=-1){
		            		focusInput.parent().parent().parent().next().find("td[name=abstract]").trigger('click');
		        			return ;
		            	}
		            	if(thisName.indexOf("noLine_loan")!=-1){
		            		focusInput.parent().parent().parent().next().find("td[name=abstract]").trigger('click');
		        			return ;
		            	}
						var thisTd = focusInput.parent().parent();
						thisTd.next().trigger('click');
		    		},
		    		'Ctrl+Left' : function($this){
		    			var focusInput = $this.find("input:focus");
						var thisName = focusInput.attr('name');
						if(thisName.indexOf("abstract")!=-1){
		            		if(jQuery("#${random}jeLineType").val()=="no"){
		            			focusInput.parent().parent().parent().prev().find("td[name=noLine_loan]").trigger('click');
		            			return ;
		            		}else{
		            			focusInput.parent().parent().parent().prev().find("td[name=withLine_loan]:first").trigger('click');
		            		}
		            	}
		            	if(thisName.indexOf("withLine_lend")!=-1){
		            		focusInput.parent().parent().parent().find("td[name=account]").trigger('click');
		        			return ;
		            	}
		            	if(thisName.indexOf("noLine_lend")!=-1){
		            		focusInput.parent().parent().parent().find("td[name=account]").trigger('click');
		        			return ;
		            	}
		            	if(thisName.indexOf("withLine_loan")!=-1){
		            		focusInput.parent().parent().parent().find("td[name=withLine_lend]:first").trigger('click');
		        			return ;
		            	}
						var thisTd = focusInput.parent().parent();
						thisTd.prev().trigger('click');
		    		},
		    		'Ctrl+Up' : function($this){
		    			var focusInput = $this.find("input:focus");
		            	var thisTr = focusInput.parent().parent().parent();
						var thisTdIndex = focusInput.parent().parent().index();
						var nextTd = thisTr.prev().find("td").eq(thisTdIndex);
						nextTd.trigger('click');
		    		},
		    		'Ctrl+Down' : function($this){
		    			var focusInput = $this.find("input:focus");
		            	var thisTr = focusInput.parent().parent().parent();
						var thisTdIndex = focusInput.parent().parent().index();
						var nextTd = thisTr.next().find("td").eq(thisTdIndex);
						nextTd.trigger('click');
		    		}
		    };
		    jQuery("#${random}voucherCardTable").unbind('keydown').bind('keydown',function (evt){
		    	var $this = jQuery(this);
		    	//keyEventRoute($this,evt);
            });
		   jQuery("#${random}voucherCardTable").unbind('keydown').bind('keydown', 'Ctrl+left', function (evt){
				
			});
			jQuery("#${random}voucherCardTable").unbind('keydown').bind('keydown', 'Ctrl+up', function (evt){
            	
            });
            
            jQuery("#${random}voucherCardTable").unbind('keydown').bind('keydown', 'Ctrl+down', function (evt){
            	
            });
            
            jQuery("#${random}assistTable").unbind('keydown').bind('keydown', 'Ctrl+right', function (evt){
		    	var focusInput = jQuery(this).find("input:focus");
				var thisTd = focusInput.parent().parent();
				thisTd.next().trigger('click');
            });
		   jQuery("#${random}assistTable").unbind('keydown').bind('keydown', 'Ctrl+left', function (evt){
				var focusInput = jQuery(this).find("input:focus");
				var thisTd = focusInput.parent().parent();
				thisTd.prev().trigger('click');
			});
			jQuery("#${random}assistTable").unbind('keydown').bind('keydown', 'Ctrl+up', function (evt){
            	var focusInput = jQuery(this).find("input:focus");
            	var thisTr = focusInput.parent().parent().parent();
				var thisTdIndex = focusInput.parent().parent().index();
				var nextTd = thisTr.prev().find("td").eq(thisTdIndex);
				nextTd.trigger('click');
            });
            
            jQuery("#${random}assistTable").unbind('keydown').bind('keydown', 'Ctrl+down', function (evt){
            	var focusInput = jQuery(this).find("input:focus");
            	var thisTr = focusInput.parent().parent().parent();
				var thisTdIndex = focusInput.parent().parent().index();
				var nextTd = thisTr.next().find("td").eq(thisTdIndex);
				nextTd.trigger('click');
            });
            jQuery("#${random}assistTable").unbind('keydown').bind('keydown', 'return', function (evt){
            	var focusInput = jQuery(this).find("input:focus");
            	var thisTr = focusInput.parent().parent().parent();
            	var lastOne = thisTr.find("td").last().index();
				var thisTdIndex = focusInput.parent().parent().index();
				if(thisTdIndex==lastOne){
					var nextTr = thisTr.next();
					nextTr.find("td").eq(2).trigger('click');;
				}else{
					var nextTd = focusInput.parent().parent().next();
					nextTd.trigger('click');
				}
            });
            
            jQuery("#${random}assisForm").unbind('keydown').bind('keydown', 'Ctrl+A', function (evt){
            	jQuery("#${random}addAssitsData").trigger('click');
            });
            jQuery("#${random}assisForm").unbind('keydown').bind('keydown', 'Ctrl+I', function (evt){
            	insertAssitsData('${random}');
            });
            jQuery("#${random}assisForm").unbind('keydown').bind('keydown', 'Ctrl+D', function (evt){
            	deleteAssitsData('${random}');
            });
            jQuery("#${random}assisForm").unbind('keydown').bind('keydown', 'Ctrl+return', function (evt){
            	jQuery("#${random}saveVoucherDetail").trigger('click');
            });
            jQuery("#${random}assisForm").unbind('keydown').bind('keydown', 'esc', function (evt){
            	jQuery("#${random}cancelVoucherDetail").trigger('click');
            }); */
            /* jQuery("#${random}voucherCardTable").unbind('keydown').bind('keydown',function(e){
            	alert(e.keyCode);
            }); */
            /* jQuery("#${random}voucherCardTable").unbind('keydown').bind('keydown',function (evt){
            	console.log(evt.ctrlKey+":"+evt.keyCode);
            }); */
	});
	
	function selectVoucherCard(type,radom){
		var thisValue = jQuery("#"+radom+"findVoucherNo").val();
		var voucherType = jQuery("#"+radom+"voucherInfoTable").find("select[name=voucherType]").val();
		if(thisValue){
			thisValue = thisValue*1;
			if(type=="first"){
				thisValue = 1;
			}else if(type=="next"){
				thisValue += 1;
			}else if(type=="prev"){
				thisValue -= 1;
			}
		}
		navTab.reload("voucherCard", {
			title : "",
			fresh : false,
			data : {voucherType:voucherType,voucherNo:thisValue}
		});
	}
</script>

<style>


 
</style>
<style>
 .voucherCard{
 	width:auto;
 	border-left: solid 1px rgb(24, 127, 207) !important;
 	border-right: solid 1px rgb(24, 127, 207) !important;
 	table-layout:fixed;
 	margin-right: 2px;
 	margin-left: 2px;
 }
 .voucherCard_head{
 	width:auto;
 	/* height:60px; */
 	table-layout:fixed;
 	border-left: solid 1px rgb(24, 127, 207) !important;
 	border-right: solid 1px rgb(24, 127, 207) !important;
 	border-top: solid 1px rgb(24, 127, 207) !important;
 	margin-left: 2px;
 	margin-right: 2px;
 	margin-top: 2px;
 }
 .voucherCard_foot{
 	width:auto;
 	/* height:60px; */
 	table-layout:fixed;
 	border-left: solid 1px rgb(24, 127, 207) !important;
 	border-right: solid 1px rgb(24, 127, 207) !important;
 	border-bottom: solid 1px rgb(24, 127, 207) !important;
 	margin-left: 2px;
 	margin-right: 2px;
 	margin-bottom: 2px;
 }
 .voucherCard tbody tr{
 		height:60px
 }
 .voucherCard .firstTr{
	height:0px;
	padding: 0 0 0 0;
 }
 .voucherCard .firstTr th{
	height:0px;
	padding: 0 0 0 0;
	border-top:0px;
	border-bottom:0px;
 }
 .voucherCard tfoot tr th{
 	border-bottom : 0px !important;
 } 

 .voucherCard td{
 	border-right: solid 1px #E0D6D6; 
 	border-bottom: solid 1px #E0D6D6;
 }
 .voucherCard_div{
	border: solid 2px rgb(24, 127, 207);
	margin:3px
 }

 .voucherCard_head th{
 	font-weight: normal !important;
 	font-family: 宋体 !important;
 }
 .voucherCard_foot th{
 	border-top : 0px !important;
 	border-bottom : 0px !important;
 	font-weight: normal !important;
 }
 .pzCell{
		border-right-color:green !important;
		border-right:6px
	}
.pzCell_red{
	border-right-color:red !important;
	border-right:6px
}
.pzCell_end{
	border-right-color:green !important;
	border-right:6px;
	border-right-width:2px !important;
}
.pzCell_color{
	color:rgb(0, 90, 204);
	font-weight: normal !important;
}
.pzCell_size{
	text-align:center;
	padding: 0px !important;
}
.moneyInput{
	background:transparent;
	border:1px solid #ffffff;
	padding: 0px;
}
.pzPage{
		border-width:1px;  border-color:black; border-style:solid;
		-moz-box-shadow: 3px 3px 0 1px rgba(0, 0, 0, 0.3);
		-webkit-box-shadow: 3px 3px 0 1px rgba(0, 0, 0, 0.3);
		box-shadow: 3px 3px 0 1px rgba(0, 0, 0, 0.3);
	}
 /* #assistTable tbody tr{
 		height:20px
 } */
 .moneyTh{
 	padding:0px !important;
 }
 
 .pzFootTable{
 	border: solid 1px #89BAEA !important;
 	table-layout:fixed;
 	margin: 2px
 }
</style>
<div class="page">
	<div class="pageContent">
	<div id="${random }voucherDataField" name="voucherData" style="display:none">${voucherData}</div>
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="${random}addNewVoucher" class="addbutton" href="javaScript:" ><span>新单
					</span>
				</a>
				</li>
				<li><a id="${random}saveVoucher" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.save" />
					</span>
				</a>
				</li>
				<s:if test="voucher.tsStr!=''">
				<s:if test="voucher.status==1">
				<li><a id="${random}voucher_check" class="delbutton"  href="javaScript:"><span>审核</span>
				</a>
				</li>
				</s:if>
				<s:if test="voucher.status==2">
				<li><a id="${random}voucher_account" class="changebutton"  href="javaScript:"
					><span>记账
					</span>
				</a>
				</li>
				</s:if>
				<s:if test="voucher.status==1">
				<li><a id="${random}voucher_zf" class="delbutton"  href="javaScript:"><span>作废</span>
				</a>
				</li>
				<li><a id="${random}voucher_error" class="delbutton"  href="javaScript:"><span>错误凭证</span>
				</a>
				</li>
				</s:if>
				<li><a class="changebutton"  href="javaScript:selectVoucherCard('first','${random}')"
					><span>首张
					</span>
				</a>
				</li>
				<li><a class="changebutton"  href="javaScript:selectVoucherCard('prev','${random}')"
					><span>上张
					</span>
				</a>
				</li>
				<li><a class="changebutton"  href="javaScript:selectVoucherCard('next','${random}')"
					><span>下张
					</span>
				</a>
				</li>
				</s:if>
				<li><a  class="changebutton"  href="javaScript:" onclick="hideVoucherJeLine(this,'${random}')"
					><span>隐藏金额线
					</span>
				</a>
				</li>
				<!-- <li><a  class="changebutton"  href="javaScript:resizeCol()"
					><span>隐藏金额线sdf
					</span>
				</a>
				</li> -->
			
			</ul>
		</div>
		<div class="pzPage" style="margin:5px 10px 5px 5px">
		<div>
			<div align="center" style="font-size:25px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px"><span style="font-size:25px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px" id="${random}voucherTypeText">记 账</span> 凭 证<hr style="width:135px;color:rgb(24, 127, 207);margin-top:0px"/><hr style="width:135px;color:rgb(24, 127, 207);margin-top:-11px"/></div>
			<table id="${random}voucherInfoTable" border=0 width=100% style="margin-top:-12px;table-layout:fixed;">
				<tr>
					<td width="33%">
						&nbsp;&nbsp;凭证字号:<s:select list="voucherTypes" name="voucherType" listKey="vouchertype" listValue="vouchertype" onchange="changeVtText(this)"></s:select>
						<%-- &nbsp;&nbsp;<s:property value="voucherNo"/>
						&nbsp;&nbsp; --%><input type="text" id="${random}findVoucherNo" size=3 value="<s:property value='voucherNo'/>"/><input type="hidden" size=3 value="<s:property value='voucherNo'/>"/><input type="hidden" size=3 value="<s:property value='voucher.tsStr'/>"/>
						<input type="hidden" size=3 id="${random}VoucherId" value="<s:property value='voucher.voucherId'/>"/>
					</td>
					<td width="33%" align="center">
						日期:&nbsp;<input id="${random}voucherDate" type="text" size="10" onClick="WdatePicker({skin:'ext'})" value="${optDate}"/>
					</td>
					<td width="33%" align="right">
						附单据:&nbsp;<input id="${random}attachNum" size=2 value="${attachNum}"/>张&nbsp;&nbsp;
					</td>
				</tr>
			</table>
			  
			</div>
	<div id="${random}voucherCardTable_div" class="voucherCard_div" layoutH=140>
	<input type="hidden" id="${random}jeLineType" value="yes"/>
	<table id="${random}voucherCardTable_head" class="list voucherCard_head">
	<thead>
			<tr>
				<th colspan="1" name="abstract" rowspan="2" align="center" perWidth="30" class="pzCell_color">摘要</th>
				<th colspan="1" name="account" rowspan="2" align="center" perWidth="40" class="pzCell_color">科目</th>
				<th colspan="11" name="withLine" align="center" perWidth="15" class="pzCell_color">借方金额</th>
				<th colspan="11" name="withLine" align="center" perWidth="15" class="pzCell_color">贷方金额</th>
				<th colspan="1" name="noLine" align="center" perWidth="15" style="display:none" class="pzCell_color">借方金额</th>
				<th colspan="1" name="noLine" align="center" perWidth="15" style="display:none" class="pzCell_color">贷方金额</th>
				<th name="scrollbar" rowspan="2"  style="display:none" class="firstTr"></th>
			</tr>
			<tr>
				<th name="withLine" class="pzCell_color moneyTh">亿</th>
				<th name="withLine" class="pzCell_color moneyTh">千</th>
				<th name="withLine" class="pzCell_color moneyTh">百</th>
				<th name="withLine" class="pzCell_color moneyTh">十</th>
				<th name="withLine"  class="pzCell_color moneyTh">万</th>
				<th name="withLine"  class="pzCell_color moneyTh">千</th>
				<th name="withLine" class="pzCell_color moneyTh">百</th>
				<th name="withLine" class="pzCell_color moneyTh">十</th>
				<th name="withLine" class="pzCell_color moneyTh">元</th>
				<th name="withLine"  class="pzCell_color moneyTh">角</th>
				<th name="withLine"  class="pzCell_color moneyTh">分</th>
				<th name="withLine"  class="pzCell_color moneyTh">亿</th>
				<th name="withLine"  class="pzCell_color moneyTh">千</th>
				<th name="withLine" class="pzCell_color moneyTh">百</th>
				<th name="withLine"  class="pzCell_color moneyTh">十</th>
				<th name="withLine" class="pzCell_color moneyTh">万</th>
				<th name="withLine" class="pzCell_color moneyTh">千</th>
				<th name="withLine"  class="pzCell_color moneyTh">百</th>
				<th name="withLine" class="pzCell_color moneyTh">十</th>
				<th name="withLine"  class="pzCell_color moneyTh">元</th>
				<th name="withLine" class="pzCell_color moneyTh">角</th>
				<th name="withLine" class="pzCell_color moneyTh">分</th>
			</tr>
		</thead>
	</table>
	<div id="${random}voucherCardTableDiv" style="overflow: auto;">
	<table id="${random}voucherCardTable" class="voucherCard list" targetType="navTab" asc="asc" desc="desc">
		<tbody>
		<tr class="firstTr">
			<th name="abstract" class="firstTr">
			</th>
			<th name="account" class="firstTr">
			</th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="withLine" class="firstTr"></th>
			<th name="noLine" style="display:none" class="firstTr"></th>
			<th name="noLine" style="display:none" class="firstTr"></th>
		</tr>
			<tr target="sid_user" rel="1">
				<td name="abstract" style="border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input name="abstract" type="text"/>
				</span>
				</td>
				<td name="account" style="border-right: solid 1px #E0D6D6; "><span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input name="account" type="text"/>
					<input name="account_id" type="hidden" />
					<input name="account_assists" type="hidden" />
				</span></td>
				<td align="center" name="withLine_lend" class="pzCell_size" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input name="withLine_lend" type="text" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_lend" class="pzCell_size" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input name="withLine_lend" type="text" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_lend" class="pzCell pzCell_size" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input name="withLine_lend" type="text" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_lend" class="pzCell_size" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input name="withLine_lend" type="text" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_lend" class="pzCell_size" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input name="withLine_lend" type="text" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_lend" class="pzCell pzCell_size" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input name="withLine_lend" type="text" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_lend" class="pzCell_size" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input name="withLine_lend" type="text" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_lend" class="pzCell_size" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input name="withLine_lend" type="text" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_lend" class="pzCell_red pzCell_size" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input name="withLine_lend" type="text" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_lend" class="pzCell_size" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input name="withLine_lend" type="text" class="moneyInput"/>
				</span></td>
				<td align="center" align="center" name="withLine_lend" class="pzCell_end pzCell_size" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input name="withLine_lend" type="text" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_loan" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input type="text" name="withLine_loan" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_loan" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input type="text" name="withLine_loan" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_loan" class="pzCell" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input type="text" name="withLine_loan" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_loan" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input type="text" name="withLine_loan" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_loan" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input type="text" name="withLine_loan" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_loan" class="pzCell" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow"  class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input type="text" name="withLine_loan" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_loan" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input type="text" name="withLine_loan" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_loan" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input type="text" name="withLine_loan" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_loan" class="pzCell_red" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input type="text" name="withLine_loan" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_loan" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input type="text" name="withLine_loan" class="moneyInput"/>
				</span></td>
				<td align="center" name="withLine_loan" class="pzCell_end" style="padding:0px;border-right: solid 1px #E0D6D6; ">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input type="text" name="withLine_loan" class="moneyInput"/>
				</span></td>
				<td name="noLine_lend" align="right" style="display:none;border-right: solid 1px #E0D6D6;">
					<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="noLine_lend"/>
				</span>
				</td>
				<td name="noLine_loan" align="right" style="display:none;border-right: solid 1px #E0D6D6;">
					<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" style="display:none">
					<input type="text" name="noLine_loan"/>
				</span>
				</td>
			</tr>
			
		</tbody>
	</table>
	</div>
	<table id="${random}voucherCardTable_foot" class="list voucherCard_foot">
	<tbody>
		<tr>
			<th colspan="2" align="left">
			<label style="margin-left:20px;margin-right:5px">合计金额:</label><span name="sumVoucherDetailMoney" style="margin-left:20px"><label style="color: blue">/</label>&nbsp;亿&nbsp;<label  style="color: blue">/</label>&nbsp;千&nbsp;<label  style="color: blue">/</label>&nbsp;百&nbsp;<label style="color: blue">/</label>&nbsp;拾&nbsp;<label style="color: blue">/</label>&nbsp;万&nbsp;<label style="color: blue">/</label>&nbsp;千&nbsp;<label style="color: blue">/</label>&nbsp;百&nbsp;<label style="color: blue">/</label>&nbsp;拾&nbsp;<label style="color: blue">/</label>&nbsp;元&nbsp;<label style="color: blue">/</label>&nbsp;角&nbsp;<label style="color: blue">/</label>&nbsp;分</span>
			</th>
			<!-- <th name="account">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</th> -->
			<th name="withLine" class="moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_lend"/>
				</span>
			</th>
			<th name="withLine"  class="moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_lend"/>
				</span>
			</th>
			<th name="withLine" class="pzCell moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_lend"/>
				</span>
			</th>
			<th name="withLine"  class="moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_lend"/>
				</span>
			</th>
			<th name="withLine"  class="moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_lend"/>
				</span>
			</th>
			<th name="withLine" class="pzCell moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_lend"/>
				</span>
			</th>
			<th name="withLine" class="moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_lend"/>
				</span>
			</th>
			<th name="withLine" class="moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_lend"/>
				</span>
			</th>
			<th name="withLine" class="pzCell_red moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_lend"/>
				</span>
			</th>
			<th name="withLine" class="moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_lend"/>
				</span>
			</th>
			<th name="withLine" class="pzCell_end moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_lend"/>
				</span>
			</th>
			<th name="withLine" class="moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_loan"/>
				</span>
			</th>
			<th name="withLine" class="moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_loan"/>
				</span>
			</th>
			<th name="withLine" class="pzCell moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_loan"/>
				</span>
			</th>
			<th name="withLine" class="moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_loan"/>
				</span>
			</th>
			<th name="withLine" class="moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_loan"/>
				</span>
			</th>
			<th name="withLine" class="pzCell moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_loan"/>
				</span>
			</th>
			<th name="withLine" class="moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_loan"/>
				</span>
			</th>
			<th name="withLine" class="moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_loan"/>
				</span>
			</th>
			<th name="withLine" class="pzCell_red moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_loan"/>
				</span>
			</th>
			<th name="withLine" class="moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_loan"/>
				</span>
			</th>
			<th name="withLine" class="pzCell_end moneyTh">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="withLine_loan"/>
				</span>
			</th>
			<th name="noLine" style="display:none">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="noLine_lend"/>
				</span>
			</th>
			<th name="noLine" style="display:none">
				<span name="toShow" class="pzCell_color">
				</span>
				<span name="toEdit" align="right" style="display:none">
					<input type="text" name="noLine_loan"/>
				</span>
			</th>
			<th name="scrollbar" style="display:none" class="firstTr"></th>
		</tr>
	</tbody>
	</table>
	<table id="${random}assistTable_read" class="list pzFootTable">
		<thead style="font-weight: normal"><tr></tr></thead>
		<tbody><tr></tr></tbody>
		<tfoot></tfoot>
	</table>
	</div>
	<table border=0 width="90%" style="margin-left:5px;margin-bottom:5px;margin-top: 5px"><tr><td width="10%">主管:&nbsp;<label id="kjManager" style="color:red"></label></td><td width="10%">记账:&nbsp;<label id="accoutName"></label></td><td width="10%">审核:&nbsp;<label id="checkName"></label></td><td width="10%">制单:&nbsp;<label id="billName">${billName }</label></td><td width="13%">出纳:&nbsp;<input type="text" id="cnPerson" size=8/></td><td width="13%">经手人:&nbsp;<input type="text" id="cnPerson" size=8/></td><td width="13%">备注:&nbsp;<input type="text" id="cnPerson" size=8/></td></tr></table>
	</div>
	</div>
</div>

<div class="page" id="${random}assisForm_div" style="display:none">
	<div id="${random}assisForm" class="pageContent">
		<form  method="post"	action="saveAssistType?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<input type="hidden" id="${random}voucherDetailNum" />
				<div class="unit" style="color:#06c;padding:5px 10px;font-weight:500; border:0">
					分录信息
				</div>
				<div class="unit">
					<label>科目:</label><span id="${random}assis_accountName" style='line-height:21px'></span>
				</div>
				<div class="unit">
					<label><s:text name='voucherDetail.abstract'/>:</label><input id="${random}assis_abstract" class="textInput" value=''/>
					<label><s:text name='voucherDetail.je'/>:</label><input id="${random}assis_money" value='' class="textInput"/>
				</div>
				<%-- <div class="unit">
					<label><s:text name='voucherDetail.je'/>:</label><input value=''/>
				</div> --%>
				<br><br>
				<hr/>
				<div class="unit" style="color:#06c;padding:5px 10px;font-weight:500; border:0">
					辅助核算
				</div>
				<div id="${random}assistInput" ></div>
				<br><br>
				<hr/>
				<div class="unit">
				  <div style="padding:0px 10px 5px;font-weight:500; border:0">  借贷方向 :<input type="radio" name="lendOrLoan" value="lend" checked="checked">借<input type="radio" name="lendOrLoan" value="loan" >贷&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;金额合计：<span id="${random}assistSumMoney" style="color: blue;">0.00</span></div>
					<table id="${random}assistTable" class="list" width="99%" layoutH="255" style="table-layout:fixed;margin-left: 5px;font-weight: normal">
						<thead style="font-weight: normal"><tr></tr></thead>
						<tbody><tr></tr></tbody>
						<tfoot></tfoot>
					</table>
				</div>
				
			</div>
			<div class="formBar">
								<ul style="float: left;">
										<li><div class="buttonActive">
												<div class="buttonContent">
													<button id="${random}addAssitsData"  type="button">增加(A)</button>
												</div>
											</div>
										</li>
										<li><div class="buttonActive">
												<div class="buttonContent">
													<button id="${random}insertAssitsData" onclick="insertAssitsData('${random}')"  type="button">插入(I)</button>
												</div>
											</div>
										</li>
										<li><div class="buttonActive">
												<div class="buttonContent">
													<button id="${random}deleteAssitsData" onclick="deleteAssitsData('${random}')" type="button">删除(D)</button>
												</div>
											</div>
										</li>
									</ul>
									<ul>
										<li><div class="buttonActive">
												<div class="buttonContent">
													<button id="${random}saveVoucherDetail" type="button">确定(Enter)</button>
												</div>
											</div>
										</li>
										<li><div class="button">
												<div class="buttonContent">
													<button id="${random}cancelVoucherDetail" type="button" class="close">取消(Esc)</button>
												</div>
											</div>
										</li>
									</ul>
								</div>
		</form>
	</div>
</div>
<div id="${random}accountTreeDialog" style="display:none">
</div>