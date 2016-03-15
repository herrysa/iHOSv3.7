
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var gridComplete = 0,deraction="借";
	var voucherDetailLayout;
	var voucherDetailGridIdString="#voucherDetail_gridtable";
	function pzFormatter(cellValue, opts, rowObject){
		try{
    	 	var op =opts.colModel.formatoptions;
    		var ft = op.definedFormat;
    		return  formatMoney( ft, cellValue);
		}catch(err){
			alert(err);
		}
	}
	function formatMoney(ft, cellValue){
		if(!cellValue){
			return "";
		}
		cellValue = format( ".00", cellValue);
		cellValue = (""+cellValue).replace("\.","");
		var cellValueArr = cellValue.split("");
		//if(cellValueArr[cellValueArr.length-1-ft]&&cellValueArr[cellValueArr.length-1-ft]!=0){
		if(cellValueArr[cellValueArr.length-1-ft]){
			return cellValueArr[cellValueArr.length-1-ft];
		}else{
			return "";
		}
	}
	var vtfullSize = jQuery("#container").innerHeight()+7;
	var vtsouthSize = vtfullSize / 4;
	var voucherDetailLayoutSettings = {
			applyDefaultStyles : true // basic styling for testing & demo
										// purposes
			// , south__size: 500
			// , south__minheight: 300
			,
			south__spacing_open : 0 // no resizer-bar when open (zero height)
			,
			south__spacing_closed : 0 // big resizer-bar when open (zero
										// height)
			,
			south__maxSize : vtfullSize // 1/2 screen height
			,
			south__closable : true,
			south__size : vtsouthSize
			,
			south__onopen_start : function(paneName,element,state,options,layoutName){
				//zzhJsTest.debug("southOpen:1"+layoutName);
				//gridResize();
				//jQuery("#"+tableIds.split(";")[0]+"_isShowSouth").val(1);
			},
			south__onopen_end : function(paneName,element,state,options,layoutName){
				//tabResize();
				//gridResize();
			},
			south__onclose_start : function(paneName,element,state,options,layoutName){
				//zzhJsTest.debug("southOpen:0");
				//jQuery("#"+tableIds.split(";")[0]+"_isShowSouth").val(0);
			},
			onresize_end : function(paneName,element,state,options,layoutName){
				//zzhJsTest.debug("resize:"+paneName);
				
			}
			,
			south__initHidden:false
		};
	var pzwidth = 10,summaryWidth = 150,itemWidth = 350;
	jQuery(document).ready(function() { 
		var selectId,voucherDetailMap;

		voucherDetailLayout = makeLayout({
			baseName: 'voucherDetail', 
			tableIds: 'voucherDetail_gridtable'
		}, null);
		var voucherDetailGrid = jQuery(voucherDetailGridIdString);
    	voucherDetailGrid.jqGrid({
    		url : "voucherDetailGridList",
    		editurl:"voucherDetailGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'voucherDetailId',index:'voucherDetailId',align:'center',label : '<s:text name="voucherDetail.voucherDetailId" />',hidden:true,key:true},
{name:'detailNo',index:'detailNo',align:'center',label : '<s:text name="voucherDetail.detailNo" />',hidden:true},
{name:'direction',index:'direction',align:'center',label : '<s:text name="voucherDetail.direction" />',hidden:true},
{name:'abstractStr',index:'abstractStr',align:'left',label : '<s:text name="voucherDetail.abstract" />',hidden:false,width:summaryWidth,editable:true},
{name:'account.acctFullname',index:'account.acctFullname',align:'left',label : '<s:text name="voucherDetail.account" />',hidden:false,width:itemWidth,editable:true},
{name:'account.acctId',index:'account.acctId',align:'left',label : '<s:text name="voucherDetail.account" />',hidden:true,editable:true},
{name:'account.AssistTypes',index:'account.AssistTypes',align:'left',label : '<s:text name="voucherDetail.account" />',hidden:true,editable:true,width:30},
{name:'je',index:'je',align:'center',label : '<s:text name="voucherDetail.je" />',hidden:true,formatter:'number'},
{name:'showLend',index:'showLend',align:'right',label : '<s:text name="voucher.lend" />',hidden:true,formatter:'number',editable:true},
{name:'showLoan',index:'showLoan',align:'right',label : '<s:text name="voucher.loan" />',hidden:true,formatter:'number',editable:true}, 
{name:'lendFlag',index:'lendFlag',align:'center',label : '<s:text name="voucher.loan" />',hidden:true}, 
{name:'lend',index:'lend',align:'center',label : '<s:text name="voucher.yi" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"10"},editable:true},
{name:'lend',index:'lend',align:'center',label : '<s:text name="voucher.qw" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"9"},editable:true},
{name:'lend',index:'lend',align:'center',label : '<s:text name="voucher.bw" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"8"},editable:true},
{name:'lend',index:'lend',align:'center',label : '<s:text name="voucher.sw" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"7"},editable:true},
{name:'lend',index:'lend',align:'center',label : '<s:text name="voucher.w" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"6"},editable:true},
{name:'lend',index:'lend',align:'center',label : '<s:text name="voucher.q" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"5"},editable:true},
{name:'lend',index:'lend',align:'center',label : '<s:text name="voucher.b" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"4"},editable:true},
{name:'lend',index:'lend',align:'center',label : '<s:text name="voucher.s" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"3"},editable:true},
{name:'lend',index:'lend',align:'center',label : '<s:text name="voucher.y" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"2"},editable:true},
{name:'lend',index:'lend',align:'center',label : '<s:text name="voucher.j" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"1"},editable:true},
{name:'lend',index:'lend',align:'center',label : '<s:text name="voucher.f" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"0"},editable:true},
{name:'loanFlag',index:'loanFlag',align:'center',label : '<s:text name="voucher.loan" />',hidden:true}, 
{name:'loan',index:'loan',align:'center',label : '<s:text name="voucher.yi" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"10"},editable:true},
{name:'loan',index:'loan',align:'center',label : '<s:text name="voucher.qw" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"9"},editable:true},
{name:'loan',index:'loan',align:'center',label : '<s:text name="voucher.bw" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"8"},editable:true},
{name:'loan',index:'loan',align:'center',label : '<s:text name="voucher.sw" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"7"},editable:true},
{name:'loan',index:'loan',align:'center',label : '<s:text name="voucher.w" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"6"},editable:true},
{name:'loan',index:'loan',align:'center',label : '<s:text name="voucher.q" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"5"},editable:true},
{name:'loan',index:'loan',align:'center',label : '<s:text name="voucher.b" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"4"},editable:true},
{name:'loan',index:'loan',align:'center',label : '<s:text name="voucher.s" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"3"},editable:true},
{name:'loan',index:'loan',align:'center',label : '<s:text name="voucher.y" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"2"},editable:true},
{name:'loan',index:'loan',align:'center',label : '<s:text name="voucher.j" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"1"},editable:true},
{name:'loan',index:'loan',align:'center',label : '<s:text name="voucher.f" />',hidden:false,width:pzwidth,formatter:pzFormatter, formatoptions:{definedFormat:"0"},editable:true},
{name:'loanOver',index:'loanOver',align:'center',label : '<s:text name="voucher.loanOver" />',hidden:true}
],
        	jsonReader : {
				root : "voucherDetails", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'voucherDetailId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="voucherDetailList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:false,
        	loadui: "disable",
        	rownumbers:false,
        	multiselect: false,
			multiboxonly:false,
			shrinkToFit:true,
			autowidth:true,
			footerrow: true,
			beforeSelectRow:function(rowid,e){
				return false;
			},
        	onSelectRow: function(rowid) {
        		
       		},
       		ondblClickRow:function(rowid,iRow,e){
       			if(selectId!=rowid){
	       			jQuery(this).jqGrid('restoreRow',selectId);
	       			selectId = rowid;
	       			jQuery(this).jqGrid('editRow',rowid,false);
	       			dealEditRow();
	       			addVoucherAutoCom();
       			}
       		},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	
           	var dataTest = {"id":"voucherDetail_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	//makepager("voucherDetail_gridtable");
      	  	var tdCount = 1;
    	   	jQuery("#voucherDetail_gridtable").find("tr:visible").each(function(){
    	   		tdCount = 1;
    	   		jQuery(this).find("td[aria-describedby='voucherDetail_gridtable_lend']:visible").each(function(){
	      	   		if(tdCount==1){
	      	   			jQuery(this).prev().prev().prev().prev().addClass('pzFirstCell');
	      	   		}
    	   			if(tdCount%3==0&&tdCount!=9){
	      	  			jQuery(this).addClass('pzCell');
	      	  		}
		      	   	if(tdCount==9){
	      	  			jQuery(this).css("border-right-color","red");
	      	  		}
		      	  	if(tdCount==11){
		      	  		jQuery(this).addClass('pzCell');
	      	  			jQuery(this).css("border-right-width","2px");
	      	  		}
	      	   		tdCount++;
    	   		});
    	   		tdCount = 1;
    	   		jQuery(this).find("td[aria-describedby='voucherDetail_gridtable_loan']:visible").each(function(){
	      	   		if(tdCount%3==0&&tdCount!=9){
	      	  			jQuery(this).addClass('pzCell');
	      	  		}
		      	   	if(tdCount==9){
	      	  			jQuery(this).css("border-right-color","red");
	      	  		}
		      	  	if(tdCount==11){
		      	  		jQuery(this).addClass('pzCell');
	      	  			jQuery(this).css("border-right-width","2px");
	      	  		}
	      	   		tdCount++;
	   			});
    	   	});
			var lendValue = jQuery(this).getCol("showLend",false,'sum');
			var loanValue = jQuery(this).getCol("showLoan",false,'sum');
    	 	//alert(sumValue);
    	 	var footerData = '{"abstractStr":"合计","showLend":"'+lendValue+'","showLoan":"'+loanValue+'"}';
    	 	jQuery(this).footerData("set",  eval('(' + footerData + ')'));
     		
     		jQuery("#gview_voucherDetail_gridtable").find(".ui-jqgrid-ftable:first").find("tr:visible").each(function(){
    	   		tdCount = 1;
    	   		jQuery(this).find("td[aria-describedby='pzTestCard_gridtable_lend']:visible").each(function(){
	      	   		if(tdCount==1){
	      	   			jQuery(this).prev().prev().prev().prev().addClass('pzFirstCell');
	      	   			var tdWidth = jQuery(this).prev().prev().prev().prev().width();
	      	  			//jQuery(this).prev().prev().prev().prev().width(242);
	      	   		}
    	   			if(tdCount%3==0&&tdCount!=9){
	      	  			jQuery(this).addClass('pzCell');
	      	  		}
		      	   	if(tdCount==9){
	      	  			jQuery(this).css("border-right-color","red");
	      	  		}
		      	  	if(tdCount==11){
		      	  		jQuery(this).addClass('pzCell');
	      	  			jQuery(this).css("border-right-width","2px");
	      	  			var tdWidth = jQuery(this).css("width");
	      	  			jQuery(this).width("width",7);
	      	  		}
	      	   		tdCount++;
    	   		});
    	   		tdCount = 1;
    	   		jQuery(this).find("td[aria-describedby='pzTestCard_gridtable_loan']:visible").each(function(){
	      	   		if(tdCount%3==0&&tdCount!=9){
	      	  			jQuery(this).addClass('pzCell');
	      	  		}
		      	   	if(tdCount==9){
	      	  			jQuery(this).css("border-right-color","red");
	      	  		}
		      	  	if(tdCount==11){
		      	  		jQuery(this).addClass('pzCell');
	      	  			jQuery(this).css("border-right-width","2px");
	      	  			var tdWidth = jQuery(this).css("width");
	      	  			jQuery(this).width("width",7);
	      	  		}
	      	   		tdCount++;
	   			});
     		});
			var lendFlagTh = jQuery("#voucherDetail_gridtable_lendFlag");
			var loanFlagTh = jQuery("#voucherDetail_gridtable_loanFlag");
			for(var i=0;i<11;i++){
				lendFlagTh = lendFlagTh.next();
				lendFlagTh.css("font-size","50%");
			}
			for(var i=0;i<11;i++){
				loanFlagTh = loanFlagTh.next();
				loanFlagTh.css("font-size","5px");
			}
			//fullGridEdit(jQuery(this));
			gridComplete = 1;
			} 
  	});
    jQuery(voucherDetailGrid).jqGrid('unbindKeys');
    jQuery("#voucherDetail_gridtable").jqGrid('setComplexGroupHeaders',{
		 useColSpanStyle: true, 
		 groupHeaders:[
			{startColumnName: 'lendFlag', numberOfColumns: 12, titleText: '借方金额'},
			{startColumnName: 'loanFlag', numberOfColumns: 12, titleText: '贷方金额'} 
		 ]
  });
    var addTr = setInterval(function(){
    	if(gridComplete==1){
    		for(var i=0;i<10;i++){
    			jQuery("#voucherDetail_gridtable").jqGrid('addRowData', i, {lend:0,loan:0}, "last");
    		}
    		//addSM();
    		jQuery("input[name='lend']").addClass('moneyInput');
    		jQuery("input[name='loan']").addClass('moneyInput');
    		clearInterval(addTr);
    	}
    },100);
    jQuery("#voucherDetail_gridtable").removeAttr("background");
  	});
	
	var jeLineState = 1;
	function changeJeLine(){
		if(jeLineState==1){
			//jQuer("#voucherDetail_gridtable_lend",".ui-jqgrid-htable").show();
			jQuery("#voucherDetail_gridtable").jqGrid('showHideCol',"showLend","block");
			jQuery("#voucherDetail_gridtable").jqGrid('showHideCol',"showLoan","block");
			
			jQuery("#voucherDetail_gridtable").jqGrid('showHideCol',"lend","none");
			jQuery("#voucherDetail_gridtable").jqGrid('showHideCol',"loan","none");
			
			var trHeight = jQuery(".jqg-third-row-header","#gbox_voucherDetail_gridtable").height();
			jQuery(".jqg-third-row-header","#gbox_voucherDetail_gridtable").height(trHeight*2);
			jeLineState=0;
		}else if(jeLineState==0){
			jQuery("#voucherDetail_gridtable").jqGrid('showHideCol',"showLend","none");
			jQuery("#voucherDetail_gridtable").jqGrid('showHideCol',"showLoan","none");
			
			jQuery("#voucherDetail_gridtable").jqGrid('showHideCol',"lend","block");
			jQuery("#voucherDetail_gridtable").jqGrid('showHideCol',"loan","block");
			
			jeLineState=1;
		}
		gridResize(null,null,'voucherDetail_gridtable',"single");
		
	}
	
	function dealEditRow(){
		jQuery("input[name='abstractStr']").addClass('gridFullInput');
		jQuery("input[name='account.acctFullname']").addClass('gridFullInput');
		jQuery("input[name='showLend']").addClass('gridFullInput');
		jQuery("input[name='showLoan']").addClass('gridFullInput');
		jQuery("input[name='lend']").addClass('moneyInput');
		jQuery("input[name='loan']").addClass('moneyInput');
		jQuery("input[name='lend']:visible").each(function(){
			if('voucherDetail_gridtable_loanFlag'!=jQuery(this).parent().next().attr('aria-describedby')){
			jQuery(this).unbind('focus').bind('focus',function(){
				var inputValue = jQuery(this).val();
				//var lastFlag = ('voucherDetail_gridtable_loanFlag'==jQuery(this).parent().next().attr('aria-describedby'));
				//alert(lastFlag);
				if(!inputValue){
					if(inputValue==0){
						var prevValue = jQuery(this).parent().prev().find("input[name='lend']").eq(0).val();
						if(!prevValue){
							jQuery("input[name='lend']:visible").eq(10).focus();
						}
					}
					//jQuery("input[name='lend']:visible").eq(10).val('0');
				}
			});
				
			}
			var editInput ,lendMoneyArr = new Array(); 
			jQuery(this).unbind('keydown').bind('keydown',function(e){
				if(e.keyCode==13){
    				dealEnterEvent();
    			}
				var oldValue = jQuery(this).val();
				editInput = jQuery(this);
				var numFlag = false,newValue = 0;
				if(e.keyCode>=48&&e.keyCode<=57){
					newValue = e.keyCode - 48;
					numFlag = true;
				}
				if(e.keyCode>=96&&e.keyCode<=105){
					newValue = e.keyCode - 96;
					numFlag = true;
				}
				if(numFlag){
				if('voucherDetail_gridtable_loanFlag'!=jQuery(this).parent().next().attr('aria-describedby')){
					var prevValue = jQuery(this).parent().prev().find("input[name='lend']").eq(0).val();
					if(!prevValue&&newValue==0){
						newValue = "";
					}
					setTimeout(function(){
						editInput.val(newValue);
					},100);
				}else{
					
					if(lendMoneyArr.length==0&&newValue==0){
						setTimeout(function(){
    						editInput.val(oldValue);
    					},100);
					}else{
						lendMoneyArr.push(newValue);
						var numStart = jQuery("input[name='lend']:visible").length - lendMoneyArr.length;
						setTimeout(function(){
						var i_temp = 0;
						jQuery("input[name='lend']:visible").each(function(){
							if(i_temp>=numStart){
								//alert(lendMoneyArr[i_temp-numStart]);
								jQuery(this).val(lendMoneyArr[i_temp-numStart]);
							}
							i_temp++;
						});
						},100);
					}
				}
					//jQuery(this).parent().prev().find("input[name='lend']:visible").eq(0).val(e.keyCode);
				}else{
					setTimeout(function(){
						editInput.val(oldValue);
						
					},100);
				}
			});
		});
		
		jQuery("input[name='loan']:visible").each(function(){
			if('voucherDetail_gridtable_loanOver'!=jQuery(this).parent().next().attr('aria-describedby')){
			jQuery(this).unbind('focus').bind('focus',function(){
				var inputValue = jQuery(this).val();
				//var lastFlag = ('voucherDetail_gridtable_loanFlag'==jQuery(this).parent().next().attr('aria-describedby'));
				//alert(lastFlag);
				if(!inputValue){
					if(inputValue==0){
						var prevValue = jQuery(this).parent().prev().find("input[name='loan']").eq(0).val();
						if(!prevValue){
							jQuery("input[name='loan']:visible").eq(10).focus();
						}
					}
					//jQuery("input[name='lend']:visible").eq(10).val('0');
				}
			});
				
			}
			var editInput ,lendMoneyArr = new Array(); 
			jQuery(this).unbind('keydown').bind('keydown',function(e){
				if(e.keyCode==13){
    				dealEnterEvent();
    			}
				var oldValue = jQuery(this).val();
				editInput = jQuery(this);
				var numFlag = false,newValue = 0;
				if(e.keyCode>=48&&e.keyCode<=57){
					newValue = e.keyCode - 48;
					numFlag = true;
				}
				if(e.keyCode>=96&&e.keyCode<=105){
					newValue = e.keyCode - 96;
					numFlag = true;
				}
				if(numFlag){
				if('voucherDetail_gridtable_loanOver'!=jQuery(this).parent().next().attr('aria-describedby')){
					var prevValue = jQuery(this).parent().prev().find("input[name='loan']").eq(0).val();
					if(!prevValue&&newValue==0){
						newValue = "";
					}
					setTimeout(function(){
						editInput.val(newValue);
					},100);
				}else{
					
					if(lendMoneyArr.length==0&&newValue==0){
						setTimeout(function(){
    						editInput.val(oldValue);
    					},100);
					}else{
						lendMoneyArr.push(newValue);
						var numStart = jQuery("input[name='loan']:visible").length - lendMoneyArr.length;
						setTimeout(function(){
						var i_temp = 0;
						jQuery("input[name='loan']:visible").each(function(){
							if(i_temp>=numStart){
								//alert(lendMoneyArr[i_temp-numStart]);
								jQuery(this).val(lendMoneyArr[i_temp-numStart]);
							}
							i_temp++;
						});
						},100);
					}
				}
					//jQuery(this).parent().prev().find("input[name='lend']:visible").eq(0).val(e.keyCode);
				}else{
					setTimeout(function(){
						editInput.val(oldValue);
						
					},100);
				}
			});
		});
		
		jQuery("input[name='abstractStr']:visible").unbind('keydown').bind('keydown',function(e){
			if(e.keyCode==13){
				dealEnterEvent();
			}
		});
		jQuery("input[name='account.acctFullname']:visible").unbind('keydown').bind('keydown',function(e){
			if(e.keyCode==13){
				dealEnterEvent();
			}
		});
	}
	
	function dealEnterEvent(){
		var abstractStr = jQuery("input[name='abstractStr']:visible").eq(0);
		var account_acctFullname = jQuery("input[name='account.acctFullname']:visible").eq(0);
		var lend = jQuery("input[name='lend']:visible").eq(10);
		var loan = jQuery("input[name='loan']:visible").eq(10);
		var absValue = abstractStr.val();
		var accountValue = account_acctFullname.eq(0).val();
		var accountIdValue = jQuery("input[name='account.acctId']").eq(0).val();
		var accountAssValue = jQuery("input[name='account.AssistTypes']").eq(0).val();
		var lendValue = lend.val();
		var loanValue = loan.val();
		
		if(!accountValue){
			jQuery("input[name='account.acctFullname']:visible").eq(0).focus();
			return;
		}else{
			if(!accountIdValue){
				jQuery("input[name='account.acctFullname']:visible").eq(0).val("").focus();
				jQuery("input[name='account.acctFullname']:visible").eq(0).focus();
			}else{
				if(accountAssValue&&accountAssValue!='null'&&accountAssValue!=','){
					//alert('has');
					alert(accountAssValue);
					var accountAssValueArr = accountAssValue.split(",");
					for(var aasInd=0;aasInd<accountAssValueArr.length;aasInd++){
						var asv = accountAssValueArr[aasInd].trim();
						//alert(asv);
						switch(asv){
							case '0':
								jQuery("#assis_dept").show();
								break;

						}
					}
					jQuery("#assis_accountName").text(accountValue);
					$.pdialog.open('#DIA_inline?inlineId=assisForm', 'addAssisForVoucher', '原始凭证信息', {mask:false,width:500,height:500});　
					return;
				}
			}
		}
		
		if((deraction=="借")&&!lendValue){
			jQuery("input[name='lend']:visible").eq(10).focus();
			return;
		}
		if((deraction=="贷")&&!loanValue){
			jQuery("input[name='loan']:visible").eq(10).focus();
			return;
		}
		
		
		
		var abstractStrTd = abstractStr.parent();
		var account_acctFullnameTd = account_acctFullname.parent();
		
		var thisTr = abstractStrTd.parent();
		var showLendTd = thisTr.find("td[aria-describedby='voucherDetail_gridtable_showLend']").eq(0);
		var showLoanTd = thisTr.find("td[aria-describedby='voucherDetail_gridtable_showLoan']").eq(0);
		var showLendVal = '';
		var showLoanVal = '';

		var lendTdArr = new Array();
		var loanTdArr = new Array();
		var lendTdValArr = new Array();
		var loanTdValArr = new Array();
		
		
		
		console.log("lendTdValArr:");
		jQuery("input[name='lend']:visible").each(function(){
			lendTdArr.push(jQuery(this).parent());
			var lendVal = jQuery(this).val();
			if(lendVal){
				lendTdValArr.push(lendVal);
				console.log(lendVal);
			}else{
				lendTdValArr.push('null');
				console.log('null');
			}
		});
		console.log(lendTdValArr.length);
		console.log("loanTdValArr:");
		jQuery("input[name='loan']:visible").each(function(){
			loanTdArr.push(jQuery(this).parent());
			var loanVal = jQuery(this).val();
			if(loanVal){
				loanTdValArr.push(loanVal);
				console.log(loanVal);
			}else{
				loanTdValArr.push('null');
				console.log('null');
			}
		});
		/* if(!absValue){
			jQuery("input[name='abstractStr']:visible").eq(0).focus();
			return;
		} */

		jQuery("#voucherDetail_gridtable").jqGrid('restoreRow',selectId);
		var sInd = jQuery("#voucherDetail_gridtable").jqGrid('getInd',selectId);
		var sids = jQuery("#voucherDetail_gridtable").jqGrid('getDataIDs');
		selectId = sids[sInd];
		jQuery("#voucherDetail_gridtable").jqGrid('editRow',sids[sInd],false);
		dealEditRow();
		abstractStrTd.text(absValue);
		account_acctFullnameTd.text(accountValue);
		console.log("showLenVal:");
		for(var i=0;i<lendTdArr.length;i++){
			var lendTd = lendTdArr[i];
			var loanTd = loanTdArr[i];
			var lendValue = lendTdValArr[i];
			var loanValue = loanTdValArr[i];
			if(lendValue&&lendValue!='null'){
				lendTd.text(lendValue);
				if(i==lendTdArr.length-3){
					showLendVal += lendValue+".";
				}else{
					showLendVal += lendValue+"";
				}
				
			}
			if(loanValue&&loanValue!='null'){
				loanTd.text(loanValue);
				if(i==lendTdArr.length-3){
					showLoanVal += loanValue+".";
				}else{
					showLoanVal += loanValue+"";
				}
			}
		}
		if(showLendVal==''){
				showLendVal='0.00';
		}
		if(showLoanVal==''){
				showLoanVal='0.00';
		}
			console.log(showLendVal);
			console.log(showLoanVal);
		showLendTd.text(showLendVal);
		showLoanTd.text(showLoanVal);

		var lendValue = jQuery("#voucherDetail_gridtable").getCol("showLend",false,'sum');
		var loanValue = jQuery("#voucherDetail_gridtable").getCol("showLoan",false,'sum');
	 	
	 	var footerData = '{"abstractStr":"合计","showLend":"'+lendValue+'","showLoan":"'+loanValue+'","lend":"'+lendValue+'","loan":"'+loanValue+'"}';
	 	jQuery("#voucherDetail_gridtable").footerData("set",  eval('(' + footerData + ')'));
		
		
	}
	
	function changeVtText(s){
		var text = jQuery("select[name=voucherType]").find("option:selected").text(); 
		jQuery("#voucherTypeText").text(text);
	}
	
	function addVoucherAutoCom(){
		$("input[name='abstractStr']:visible").autocomplete("autocompleteBySql",{
	  		width : 270,
	  		multiple : false,
	  		multipleSeparator: "", 
	  		autoFill : false,
	  		matchContains : true,
	  		matchCase : true,
	  		dataType : 'json',
	  		parse : function(json) {
	  			var data = json.autocompleteResult;
	  			if (data == null || data == "") {
	  				var rows = [];
	  				rows[0] = {
	  					data : "没有结果",
	  					value : "",
	  					result : ""
	  				};
	  				return rows;
	  			} else {
	  				var rows = [];
	  				for ( var i = 0; i < data.length; i++) {
	  						
	  					rows[rows.length] = {
	  						data : data[i].abstract,
	  						value : data[i].abstract,
	  						result : data[i].abstract
	  					};
	  				}
	  				return rows;
	  			}
	  		},
	  		extraParams : {
	  			cloumns : "abstract",
	  			sql:"select abstract from GL_abstract where disabled = 0 "
	  		},
	  		formatItem : function(row) {
	  			//return dropId(row);
	  			return row;
	  		},
	  		formatResult : function(row) {
	  			//return dropId(row);
	  			return row;
	  		}
	  	});
		$("input[name='account.acctFullname']:visible").autocomplete("autocompleteBySql",{
	  		width : 370,
	  		multiple : false,
	  		multipleSeparator: "", 
	  		autoFill : false,
	  		matchContains : true,
	  		matchCase : true,
	  		dataType : 'json',
	  		parse : function(json) {
	  			var data = json.autocompleteResult;
	  			if (data == null || data == "") {
	  				var rows = [];
	  				rows[0] = {
	  					data : "没有结果",
	  					value : "",
	  					result : ""
	  				};
	  				return rows;
	  			} else {
	  				var rows = [];
	  				for ( var i = 0; i < data.length; i++) {
	  						
	  					rows[rows.length] = {
	  						data : data[i].acctId+':'+data[i].acctFullname+":"+data[i].AssistTypes,
	  						value : data[i].acctFullname,
	  						result : data[i].acctFullname
	  					};
	  				}
	  				return rows;
	  			}
	  		},
	  		extraParams : {
	  			cloumns : "acctFullname,cnCode",
	  			sql:"select acctId,acctFullname,AssistTypes from GL_account where disabled = 0 "
	  		},
	  		formatItem : function(row) {
	  			//return dropId(row);
	  			if (row != "没有结果") {
		  			row = row.split(':')[1];
	  			}
	  			return row;
	  		},
	  		formatResult : function(row) {
	  			//return dropId(row);
	  			return row;
	  		}
	  	});
	  	jQuery("input[name='account.acctFullname']:visible").result(function(event, row, formatted) {
	  		if (row == "没有结果") {
	  			return;
	  		}
	  		jQuery("input[name='account.acctId']").attr("value", (row.split(':'))[0]); 
	  		jQuery("input[name='account.AssistTypes']").attr("value", (row.split(':'))[2]); 
	  	}); 
  	}
	function saveVoucher(){
		var abstractStrs="",accountIds="",lends="",loans="";
		var voucherType = jQuery("#voucherTypeText").text();
		var sids = jQuery("#voucherDetail_gridtable").jqGrid('getDataIDs');
		 for(var i=0;i<sids.length;i++){
				var ret = jQuery("#voucherDetail_gridtable").jqGrid('getRowData',sids[i]);
				abstractStrs += ret['abstractStr']+",";
				accountIds += ret['account.acctId']+",";
				lends += ret['showLend']+",";
				loans += ret['showLoan']+",";
		}
		 $.ajax({
			    url: 'saveVoucher',
			    type: 'post',
			    data:{voucherType:voucherType,abstractStrs:abstractStrs,accountIds:accountIds,lends:lends,loans:loans},
			    dataType: 'json',
			    async:false,
			    error: function(data){
			    },
			    success: function(data){
			        // do something with xml
			        alert(data);
			    }
			});
	}
	function resizeCol(){
		jQuery("#voucherDetail_gridtable").setColProp('abstractStr',{label:'111',width:600});
		jQuery("#voucherDetail_gridtable").trigger('reloadGrid');
		var colModel  = jQuery("#voucherDetail_gridtable").jqGrid("getGridParam",'colModel')
		alert(colModel[3].name);
		alert(colModel[3].width);
	}

	function saveVouherDeatilMxData(){
		if(voucherDetailMap){
			voucherDetailMap = new Map();
			
		}
	}
</script>
<style>

	.pzCell{
		border-right-color:green !important;
		border-right:6px
	}
	.pzFirstCell{
		border-right-color:green !important;
		border-right-width:2px !important;
	}
	.pzPage{
		border-width:1px;  border-color:black; border-style:solid;
		-moz-box-shadow: 3px 3px 0 1px rgba(0, 0, 0, 0.3);
		-webkit-box-shadow: 3px 3px 0 1px rgba(0, 0, 0, 0.3);
		box-shadow: 3px 3px 0 1px rgba(0, 0, 0, 0.3);
	}
	#voucherDetail_gridtable tr td {  
    	height:60px;    /* row 高度 */  
	}

	#gbox_voucherDetail_gridtable .ui-jqgrid .ui-jqgrid-htable th{
		padding:0;
	}
	#gbox_voucherDetail_gridtable .ui-jqgrid tr .jqgfirstrow td{
		padding:0;
	}
	#gbox_voucherDetail_gridtable .ui-jqgrid tr .footrow td{
		padding:0;
	}
	#gbox_voucherDetail_gridtable{
		-moz-border-radius-topleft: 0px !important; 
		-webkit-border-top-left-radius: 0px !important;
		-khtml-border-top-left-radius: 0px !important;
		border-top-left-radius: 0px !important;

		moz-border-radius-topright: 0px !important;
		-webkit-border-top-right-radius: 0px !important;
		-khtml-border-top-right-radius: 0px !important;
		border-top-right-radius: 0px !important;

		-moz-border-radius-bottomright: 0px !important;
		-webkit-border-bottom-right-radius: 0px !important;
		-khtml-border-bottom-right-radius: 0px !important;
		border-bottom-right-radius: 0px !important;

		moz-border-radius-bottomleft: 0px !important;
		-webkit-border-bottom-left-radius: 0px !important;
		-khtml-border-bottom-left-radius: 0px !important;
		border-bottom-left-radius: 0px !important;
	}
	#gbox_voucherDetail_gridtable .ui-state-default{
		text-align:center;
	}
	.moneyInput{
		padding:0;
		border:0px;
		height:99%
	}
	.gridFullInput{
		padding:0;
		border:0px;
		height:99%
	}
	</style>
<div class="page">
	<%-- <div id="voucherDetail_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="voucherDetail_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetail.voucherDetailId'/>:
						<input type="text" name="filter_EQS_voucherDetailId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetail.account'/>:
						<input type="text" name="filter_EQS_account"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetail.acctcode'/>:
						<input type="text" name="filter_EQS_acctcode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetail.copyCode'/>:
						<input type="text" name="filter_EQS_copyCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetail.detailNo'/>:
						<input type="text" name="filter_EQS_detailNo"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetail.direction'/>:
						<input type="text" name="filter_EQS_direction"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetail.hl'/>:
						<input type="text" name="filter_EQS_hl"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetail.je'/>:
						<input type="text" name="filter_EQS_je"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetail.kjPeriod'/>:
						<input type="text" name="filter_EQS_kjPeriod"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetail.orgCode'/>:
						<input type="text" name="filter_EQS_orgCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetail.voucher'/>:
						<input type="text" name="filter_EQS_voucher"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetail.voucherFromCode'/>:
						<input type="text" name="filter_EQS_voucherFromCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetail.wbcode'/>:
						<input type="text" name="filter_EQS_wbcode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucherDetail.wbje'/>:
						<input type="text" name="filter_EQS_wbje"/>
					</label>
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(voucherDetail_search_form,voucherDetail_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(voucherDetail_search_form,voucherDetail_gridtable)"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
	</div> --%>
	<div class="pageContent">

		<div id="voucherDetail_container">
				<div id="voucherDetail_layout-center" class="pane ui-layout-center"
					style="padding: 2px">

		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="addbutton" href="javaScript:saveVoucher()" ><span><fmt:message
								key="button.save" />
					</span>
				</a>
				</li>
				<li><a id="voucherDetail_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="voucherDetail_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<li><a  class="changebutton"  href="javaScript:changeJeLine()"
					><span>隐藏金额线
					</span>
				</a>
				</li>
				<li><a  class="changebutton"  href="javaScript:resizeCol()"
					><span>隐藏金额线sdf
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div class="pzPage" style="margin:5px 10px 5px 5px">
		<div>
			<div align="center" style="font-size:25px;color:#0099FF;font-family:楷体;margin-top:2px"><span style="font-size:25px;color:#0099FF;font-family:楷体;margin-top:2px" id="voucherTypeText">记 账</span> 凭 证<hr style="width:135px;color:#0099FF;margin-top:0px"/><hr style="width:135px;color:#6699CC;margin-top:-11px"/></div>
			<table border=0 width=100% style="margin-top:-12px">
				<tr>
					<td width="33%">
						&nbsp;&nbsp;凭证字号:<s:select list="voucherTypes" id="voucherType" name="voucherType" listKey="voucherTypeId" listValue="vouchertype" onchange="changeVtText(this)"></s:select>
						&nbsp;&nbsp;<s:property value="voucherNo"/>
						&nbsp;&nbsp;<input type="text" id="findVoucherNo" size=3/>
					</td>
					<td width="33%" align="center">
						日期<input type="text" size="10" onClick="WdatePicker({skin:'ext'})" />
					</td>
					<td width="33%" align="right">
						附单据<input size=2/>张&nbsp;&nbsp;
					</td>
				</tr>
			</table>
			  
			</div>
		<div id="voucherDetail_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:500;height:300" extraHeight=80>
			<input type="hidden" id="voucherDetail_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="voucherDetail_gridtable_addTile">
				<s:text name="voucherDetailNew.title"/>
			</label> 
			<label style="display: none"
				id="voucherDetail_gridtable_editTile">
				<s:text name="voucherDetailEdit.title"/>
			</label>
			<div id="load_voucherDetail_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="voucherDetail_gridtable"></table>
			<!--<div id="voucherDetailPager"></div>-->
		</div>
		<table border=0 width="90%" style="margin-left:5px;margin-bottom:5px"><tr><td width="20%">主管:</td><td width="20%">记账:</td><td width="20%">复核:</td><td width="20%">出纳:</td><td width="20%">系统主管:</td></tr></table></table>
		</div>

		</div>

		<div id="voucherDetail_layout-south" class="pane ui-layout-south"
					style="padding: 2px">
			<div class="panelBar">
						<ul class="toolBar">
							<li style="float: right;"><a id="voucherDetail_close" class="closebutton"
								href="javaScript:"><span><fmt:message
											key="button.close" />
								</span>
							</a></li>
							<li class="line" style="float: right">line</li>
							<li style="float: right;"><a id="voucherDetail_fold" class="foldbutton"
								href="javaScript:"><span><fmt:message
											key="button.fold" />
								</span>
							</a></li>
							<li class="line" style="float: right">line</li>
							<li style="float: right"><a id="voucherDetail_unfold" class="unfoldbutton"
								href="javaScript:"><span><fmt:message
											key="button.unfold" />
								</span>
							</a></li>
						</ul>
			</div>
		
	<%-- <div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="voucherDetail_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="voucherDetail_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="voucherDetail_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div> --%>
</div>

	</div>
</div>

<div class="page" id="assisForm" style="display:none">
	<div class="pageContent">
		<form id="assistTypeForm" method="post"	action="saveAssistType?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				
				<div class="unit" style="color:#06c;padding:5px 10px;font-weight:500; border:0">
					分录信息
				</div>
				<div class="unit">
					<label>科目:</label><span id="assis_accountName" style='line-height:21px'></span>
				</div>
				<div class="unit">
					<label><s:text name='voucherDetail.abstract'/>:</label><input value=''/>
				</div>
				<div class="unit">
					<label><s:text name='voucherDetail.je'/>:</label><input value=''/>
				</div>
				<hr/>
				<div class="unit" style="color:#06c;padding:5px 10px;font-weight:500; border:0">
					辅助核算
				</div>
				<div class="unit" id='assis_dept' style="display:none">
					<label>部门:</label><input value=''/>
				</div>
				
			</div>
			<div class="formBar">
									<ul>
										<li><div class="buttonActive">
												<div class="buttonContent">
													<button type="submit">提交</button>
												</div>
											</div>
										</li>
										<li><div class="button">
												<div class="buttonContent">
													<button type="button" class="close">取消</button>
												</div>
											</div>
										</li>
									</ul>
								</div>
		</form>
	</div>
</div>





