function makeVoucherEvent(radom,state){
		var voucherData = {},voucherDetailData = {};
		voucherData = jQuery("#"+radom+"voucherDataField").text();
		if(voucherData){
			voucherData = eval("("+voucherData+")");
			voucherDetailData = voucherData["voucherDetailData"];
		}else{
			voucherData = {};
		}
		setTimeout(function(){
			trimVoucherCard(radom);
			addVoucherCardEvent(radom,state,voucherDetailData);
			fillVoucherDetailData(voucherData,voucherDetailData,radom);
			sumVoucherDetailMoney(radom);
			if(state!=1){
				readCardNeed(radom);
				return ;
			}
			addAccountComboGrid(radom);
		},50);
		
		jQuery("#"+radom+"accountTreeDialog").load("accountTree?random="+radom);
		addVoucherButtonEvent(voucherData,voucherDetailData,radom);
	}
	
	function trimVoucherCard(radom){
			//adjust card width and row count
			jQuery("#"+radom+"voucherCardTable").find('tbody>tr').unbind("click");
			jQuery("#"+radom+"voucherCardTable_foot").find('tbody>tr').unbind("click");
			var cardContainerHeight = jQuery("#"+radom+"voucherCardTable_div").innerHeight();
			var cardContainerWidth = jQuery("#"+radom+"voucherCardTable_div").innerWidth();
			var cardWidthPer = (cardContainerWidth-4)/cardContainerWidth*100;
			var cordNum = (cardContainerHeight-160)/60;
			cordNum = Math.floor(cordNum);
			var mainTableDivHeight = cordNum*60+2;
			if(jQuery.browser.msie){
				mainTableDivHeight = cordNum*60+1;
			}
			jQuery("#"+radom+"voucherCardTableDiv").outerHeight(mainTableDivHeight);
			jQuery("#"+radom+"voucherCardTable").css("width",cardWidthPer+"%");
			jQuery("#"+radom+"voucherCardTable_foot").css("width",cardWidthPer+"%");
			jQuery("#"+radom+"voucherCardTable_head").css("width",cardWidthPer+"%");
			
			var countPointer = 0,countAssist = 0;
			var gridTdWidth =new Array();
			jQuery("#"+radom+"voucherCardTable,#"+radom+"voucherCardTable_head,#"+radom+"voucherCardTable_foot").find("th[name=withLine]").each(function(){
				var colspan = jQuery(this).attr("colspan");
				colspan = parseInt(colspan);
				if(colspan>1){
					jQuery(this).outerWidth(165); //moeny parent th width
				}else{
					jQuery(this).outerWidth(15); //money td width
				}
			});
			jQuery("#"+radom+"voucherCardTable,#"+radom+"voucherCardTable_head,#"+radom+"voucherCardTable_foot").find("th[name=noLine]").outerWidth(165);
			//var abstractWidth = cardContainerWidth-4-330-200;
			var abstractWidth = 200;
			if((cardContainerWidth-4-330)<400){
				abstractWidth = Math.floot((cardContainerWidth-4-330)*0.4);
			}
			jQuery("#"+radom+"voucherCardTable,#"+radom+"voucherCardTable_head,#"+radom+"voucherCardTable_foot").find("th[name=abstract]").outerWidth(abstractWidth);
			//jQuery("#voucherCardTable,#voucherCardTable_head,#voucherCardTable_foot").find("th[name=account]").outerWidth(abstractWidth);
			
			var cardBody = jQuery("#"+radom+"voucherCardTable>tbody:first");
			var firstTr = jQuery("#"+radom+"voucherCardTable").find("tbody>tr").eq(1).removeClass("trbg").clone(true);
			
			countPointer = 0;
			firstTr.removeClass("hover");
			
			for(var i=0;i<cordNum-1;i++){
				var cloneTr = firstTr.clone(true);
				//cloneTr.attr("rel",""+(i+2));
				if(i%2==0) cloneTr.addClass("trbg");
				cardBody.append(cloneTr);
			}
			adjustByScrollBar(radom);
			
			//adjust end
			
			// add end
	}
	
	function addAccountComboGrid(radom){
		//add abstract and account combogrid event
		jQuery("#"+radom+"voucherCardTable").find("input[name='abstract']").each(function(i){
			var inputName = jQuery(this).attr("name");
			jQuery(this).attr("id",inputName+i);
			jQuery(this).combogrid({
				url : '${ctx}/comboGridSqlList',
				//searchIcon:true,
				//searchButton : true,
				queryParams:{
					sql : "SELECT * FROM GL_abstract where disabled=0",
					cloumns : 'ABSTRACT,CNCODE'
				},
				colModel : [{
					'columnName' : 'ABSTRACT',
					'width' : '70',
					'label' : '摘要'
				},
				{
					'columnName' : 'CNCODE',
					'width' : '30',
					'label' : '拼音码'
				}],
				select : function(event, ui) {
					//$("#inventoryDict_invType").val(ui.item.ID);
					$(this).val(ui.item.ABSTRACT);
					return false;
				}
			});
		});
		jQuery("#"+radom+"voucherCardTable").find("input[name='account']").each(function(){
			/* jQuery(this).bind("keyup",function(){
				jQuery(this).next().val("");
			}); */
			jQuery(this).combogrid({
				url : '${ctx}/comboGridSqlList',
				//searchIcon:true,
				//searchButton : true,
				width : 500,
				queryParams:{
					sql : "SELECT * FROM GL_account where orgCode='%ORGCODE%' and copycode='%COPYCODE%' and kjYear='%PERIODYEAR%' and disabled=0",
					cloumns : 'ACCTID,ACCTNAME,CNCODE,ACCTFULLNAME',
					specialChar : '['
				},
				colModel : [
				{
					'columnName' : 'ACCTID',
					'width' : '0',
					'label' : 'ID',
					'hidden': true
				},
				{
					'columnName' : 'ACCTCODE',
					'width' : '20',
					'label' : '科目代码',
					'align' : 'left'
				},
				{
					'columnName' : 'ACCTNAME',
					'width' : '60',
					'label' : '科目',
					'align' : 'left'
				},
				{
					'columnName' : 'CNCODE',
					'width' : '20',
					'label' : '拼音码',
					'align' : 'left'
				},
				{
					'columnName' : 'ACCTFULLNAME',
					'width' : '0',
					'label' : '全名',
					'hidden':true
				},
				{
					'columnName' : 'ASSISTTYPES',
					'width' : '0',
					'label' : '辅助核算',
					'hidden':true
				}],
				select : function(event, ui) {
					//$("#inventoryDict_invType").val(ui.item.ID);
					jQuery(this).val(ui.item.ACCTFULLNAME);
					jQuery(this).next().val(ui.item.ACCTID);
					jQuery(this).next().next().val(ui.item.ASSISTTYPES);
					return false;
				}
			});
		});
	}
	
	function addVoucherCardEvent(radom,state,voucherDetailData){
		var inputLendLength = 11;  //the money td count
		var lendData = new Array();
		jQuery("#"+radom+"voucherCardTable").delegate("tbody>tr","click",function(){
			var thisTrIndex = jQuery(this).index();
			var assisReadTbody = jQuery("#"+radom+"assistTable_read>tbody");
			var assisReadThead = jQuery("#"+radom+"assistTable_read>thead");
			assisReadTbody.empty();
			assisReadThead.empty();
			fillAssistsData(thisTrIndex,null,assisReadTbody,assisReadThead,'read',voucherDetailData,radom);
		});
		
		jQuery("#"+radom+"assistTable_read").dblclick(function(){
			jQuery("#assistTable_read_toShow").remove();
			var assistTableRead = jQuery(this).clone();
			assistTableRead.attr("id","assistTable_read_toShow");
			assistTableRead.hide();
			jQuery('body').append(assistTableRead);
			setTimeout(function(){
				$('#assistTable_read_toShow').fancybox();
				$('#assistTable_read_toShow').trigger("click");
				$(document).unbind('click.fb-start');
				
			},30);
		});
		
		jQuery("#"+radom+"voucherCardTable,#"+radom+"voucherCardTable_foot").delegate("input[name='abstract'],input[name='account'],input[name='noLine_lend'],input[name='noLine_loan'],input[name='withLine_lend'],input[name='withLine_loan']","blur",function(){
			var editSpan = jQuery(this).parent();
			/* jQuery(this).css({
				width:"0px",
				height:"0px"
			}); */
			editSpan.prev().text(jQuery(this).val());
			editSpan.prev().show();
			editSpan.hide();
			setTimeout(function(){
				//sumVoucherDetailMoney();
			},50);
		});
		jQuery("#"+radom+"voucherCardTable").delegate("input[name='account']]","blur",function(){
			var $this = jQuery(this);
			var thisValue = jQuery(this).val();
			var thisId = jQuery(this).next().val();
			if(!thisValue&&thisId){
				alertMsg.confirm("是否清除本行记录?", {
    				okCall: function(){
    					$this.next().val("");
    					$this.next().next().val("");
    					var thisTr = $this.parent().parent().parent();
    					var thisLend = thisTr.find("input[name=noLine_lend]");
    					var thisLoan = thisTr.find("input[name=noLine_loan]");
    					thisLend.val("");
    					thisLend.trigger("blur");
    					thisLoan.val("");
    					thisLoan.trigger("blur");
    					var thisAbstract = thisTr.find("input[name=abstract]");
    					thisAbstract.val("");
    					thisAbstract.trigger("blur");
    					var voucherDetailNum = thisTr.index();
    					delete voucherDetailData[voucherDetailNum];
    					sumVoucherDetailMoney(radom);
    				}
    			});
				return ;
			}
			if(thisValue){
				
			}
		});
		jQuery("#"+radom+"voucherCardTable").delegate("input[name='noLine_lend'],input[name='noLine_loan']","blur",function(){
			var thisDirector = jQuery(this).attr('name').split("_")[1];
			var otherDirector = thisDirector=='lend'?'loan':'lend';
			var thisValue = jQuery(this).val();
			var thisWithLines = jQuery(this).parent().parent().parent().find("input[name=withLine_"+thisDirector+"]");
			var otherWithLines = jQuery(this).parent().parent().parent().find("input[name=withLine_"+otherDirector+"]");
			var otherNoLine = jQuery(this).parent().parent().parent().find("input[name=noLine_"+otherDirector+"]");
			if(thisValue){
				thisValue = parseFloat(thisValue).toFixed(2);
				jQuery(this).val(thisValue);
				jQuery(this).parent().prev().text(thisValue);
			}else{
				thisWithLines.val("");
				thisWithLines.trigger("blur");
				return ;
			}
			/* if(thisValue&&thisValue.indexOf(".")==-1){
				thisValue += ".00";
				jQuery(this).val(thisValue);
				jQuery(this).parent().prev().text(thisValue);
			} */
			if(thisValue==0){
				thisWithLines.val("");
				thisWithLines.trigger("blur");
				return ;
			}
			thisValue = thisValue.replace(".","");
			var thisMoneyData = thisValue.split("");
			var beginIndex = inputLendLength-thisMoneyData.length;
			var mi=0;
			thisWithLines.each(function(){
				if(mi>=beginIndex){
					jQuery(this).val(thisMoneyData[mi-beginIndex]);
					jQuery(this).parent().prev().text(thisMoneyData[mi-beginIndex]);
				}else{
					jQuery(this).val("");
					jQuery(this).parent().prev().text("");
				}
				mi++;
			});
			otherWithLines.each(function(){
				jQuery(this).val("");
				jQuery(this).parent().prev().text("");
			});
			otherNoLine.val("");
			otherNoLine.parent().prev().text("");
			sumVoucherDetailMoney(radom);
		});
		jQuery("#"+radom+"voucherCardTable_foot").delegate("input[name='noLine_lend'],input[name='noLine_loan']","blur",function(){
			var thisDirector = jQuery(this).attr('name').split("_")[1];
			var thisValue = jQuery(this).val();
			var thisWithLines = jQuery(this).parent().parent().parent().find("input[name=withLine_"+thisDirector+"]");
			if(thisValue){
				thisValue = parseFloat(thisValue).toFixed(2);
				jQuery(this).val(thisValue);
				jQuery(this).parent().prev().text(thisValue);
			}else{
				thisWithLines.val("");
				thisWithLines.trigger("blur");
				return ;
			}
			if(thisValue==0){
				thisWithLines.val("");
				thisWithLines.trigger("blur");
				return ;
			}
			thisValue = thisValue.replace(".","");
			var thisMoneyData = thisValue.split("");
			var beginIndex = inputLendLength-thisMoneyData.length;
			var mi=0;
			thisWithLines.each(function(){
				if(mi>=beginIndex){
					jQuery(this).val(thisMoneyData[mi-beginIndex]);
					jQuery(this).parent().prev().text(thisMoneyData[mi-beginIndex]);
				}else{
					jQuery(this).val("");
					jQuery(this).parent().prev().text("");
				}
				mi++;
			});
		});
		
		
		if(state!=1){
			return ;
		}
		
		//add abstract,account,noLine_lend,noLine_loan click event
		jQuery("#"+radom+"voucherCardTable").find("td[name='abstract'],td[name='account'],td[name='noLine_lend'],td[name='noLine_loan'],td[name='withLine_lend'],td[name='withLine_loan']").click(function(){
			var thisName = jQuery(this).attr("name");
			var thWidth = jQuery(this).innerWidth();
			var thHeight = jQuery(this).innerHeight();
			var chaWidth = 10;
			//var thWidthPer = Math.floor((thWidth-10)/thWidth*100);
			//alert(thWidth);
			if(thisName.indexOf("withLine")!=-1){
				chaWidth = 6;
			}
			
			/*if(thisName.indexOf("withLine")!=-1||thisName.indexOf("noLine")!=-1){
				var thisTr = jQuery(this).parent();
				if(thisName.split("_")[1]=="lend"){
					var thisLoan = thisTr.find("input[name="+thisName.split("_")[0]+"_loan]");
					thisLoan.val("");
					thisLoan.trigger("blur");
				}else{
					var thisLend = thisTr.find("input[name="+thisName.split("_")[0]+"_lend]");
					thisLend.val("");
					thisLend.trigger("blur");
				}
			}*/
			
			jQuery(this).find("span[name=toEdit]").find("input").eq(0).css({
				width:thWidth-chaWidth+"px",
				height:thHeight-10+"px"
			});
			jQuery(this).find("span[name=toShow]").hide();
			jQuery(this).find("span[name=toEdit]").show();
			jQuery(this).find("span[name=toEdit]").find("input").eq(0).focus();
		});
		
		
		jQuery("#"+radom+"voucherCardTable").find("input[name='withLine_lend'],input[name='withLine_loan']").focus(function(e){
			lendData = new Array();
			var thisName = jQuery(this).attr("name");
			//var thisIndex = jQuery("input[name='"+thisName+"']:visible").index(jQuery(this));
			var thisTr = jQuery(this).parent().parent().parent();
			var thisIndex = thisTr.find("input[name='"+thisName+"']").index(jQuery(this));
			if(thisIndex!=inputLendLength-1){
				if(!(jQuery(this).val())){
					//console.log("l");
					jQuery(this).trigger('blur');
					//jQuery("input[name='"+thisName+"']:visible").last().trigger('focus');
					thisTr.find("td[name='"+thisName+"']:visible").last().trigger('click');
					var thisInput = jQuery(this);
					setTimeout(function(){
						//thisInput.removeClass('focus');
					},10);
					
				}
			}else{
				var lendInputs = thisTr.find("input[name='"+thisName+"']").each(function(){
					var thisValue = jQuery(this).val();
					if(thisValue){
						lendData.push(thisValue);
					}
				});
			}
		});
		jQuery("#"+radom+"voucherCardTable").find("input[name='withLine_lend'],input[name='withLine_loan']").unbind('click').bind('click',function(e){
			e.stopPropagation();
		});
		jQuery("#"+radom+"voucherCardTable").find("input[name='account']").each(function(){
			jQuery(this).detailButton({
				type:"click",
				hostAction:"dblclick",
				dealFuction:function(obj){
					dealAccountDialog(obj,radom);
				}
			});
		});
		/* jQuery("#voucherCardTable").find("input[name='withLine_lend'],input[name='withLine_loan']").unbind('blur').bind('blur',function(e){
			e.stopPropagation();
			console.log("b");
			jQuery(this).removeClass('focus');
			jQuery(this).removeClass('textInput');
		}); */
		
		jQuery("#"+radom+"voucherCardTable").find("input[name='withLine_lend'],input[name='withLine_loan'],input[name='noLine_lend'],input[name='noLine_loan']").unbind('keyup').bind('keyup',function(e){
			e.stopPropagation();
			var thisTr = jQuery(this).parent().parent().parent();
			var thisName = jQuery(this).attr("name");
			if(e.keyCode==13){
				return ;
			}
			if(e.keyCode==187){
				//deal enter = key
				if(thisName.split('_')[0]=='withLine'){
					jQuery(this).val("");
				}else{
					var v = jQuery(this).val();
					jQuery(this).val(v.substring(0,v.length-1));
				}
				balanceLendAndLoan(thisTr,thisName,true,radom);
				return ;
			}
			//var oldValue = jQuery(this).val();
			var editInput = jQuery(this);
			var thisName = editInput.attr('name');
			var lendInputs = thisTr.find("input[name='"+thisName+"']");
			
			var numFlag = false,dataAdded = true;newValue = 0;
			if(e.keyCode>=48&&e.keyCode<=57){
				newValue = e.keyCode - 48;
				numFlag = true;
			}else if(e.keyCode>=96&&e.keyCode<=105){
				newValue = e.keyCode - 96;
				numFlag = true;
			}else if(e.keyCode==8){
				numFlag = true;
				dataAdded = false;
			}
			if(numFlag){
				/* var lendData = new Array();
				lendInputs.each(function(){
					var thisValue = jQuery(this).val();
					if(thisValue){
						lendData.push(thisValue);
					}
				}); */
				//lendData.pop();
				if(thisName.split('_')[0]=='withLine'){
					var thisIndex = lendInputs.index(editInput);
					if(thisIndex==inputLendLength-1){
						/* if(!(lendInputs.eq(inputLendLength-2).val())&&newValue==0){
							alert();
							newValue = "";
						} */
						if(lendData[0]==0){
							lendData.pop();
							return ;
						}
						if(dataAdded){
							lendData.push(newValue);
						}else{
							lendData.pop();
						}
						lendInputs.val("");
						//alert(lendData.length);
						for(var i=0;i<lendData.length;i++){
							var inputTempt = lendInputs.eq(inputLendLength-lendData.length+i);
							inputTempt.val(lendData[i]);
							if(i!=lendData.length-1){
								inputTempt.trigger("blur");
							}
						}
					}else{
						editInput.val(newValue);
						lendData = new Array();
						lendInputs.each(function(){
							if(jQuery(this).val()){
								lendData.push(jQuery(this).val());
							}
						});
					}
					var thisKindValue = "";
					for(var i=0;i<lendData.length;i++){
						thisKindValue += lendData[i];
						if(i==lendData.length-3){
							thisKindValue += ".";
						}
					}
					if(lendData.length<3){
						if(lendData.length==2){
							thisKindValue = "0."+thisKindValue;
						}else if(lendData.length==1){
							thisKindValue = "0.0"+thisKindValue;
						}
					}
					var noLineKind = thisName.split("_")[1];
					var noLineInput = thisTr.find("input[name='noLine_"+noLineKind+"']:first");
					noLineInput.val(thisKindValue);
					noLineInput.trigger('blur');
					//noLineInput.parent().prev().text(thisKindValue);
				}
				/* else if(thisName.split('_')[0]=='noLine'){
					if(dataAdded){
						lendData.push(newValue);
					}else{
						lendData.pop();
					}
					lendInputs = thisTr.find("input[name='withLine_"+thisName.split('_')[1]+"']");
					lendInputs.val("");
					for(var i=0;i<lendData.length;i++){
						lendInputs.eq(inputLendLength-lendData.length+i).val(lendData[i]);
					}
					for(var i=0;i<inputLendLength;i++){
						var lendInputTemp = lendInputs.eq(i);
						//var loanInputTemp = loanInputs.eq(i);
						lendInputTemp.parent().prev().text(lendInputTemp.val());
						//loanInputTemp.parent().prev().text(loanInputTemp.val());
					}
				} */
			}else{
				if(thisName.split('_')[0]=='noLine'){
					!isNaN(parseFloat(jQuery(this).val()))||jQuery(this).val("");
				}else{
					jQuery(this).val("");
				}
			}
			
		});
		
		jQuery("#"+radom+"voucherCardTable").find("input[name='abstract'],input[name='account'],input[name='withLine_lend'],input[name='withLine_loan'],input[name='noLine_lend'],input[name='noLine_loan']").keyup(function(e){
			//console.log(e.keyCode);
			if(e.keyCode==13){
				//deal enter event
				var thisTd = jQuery(this).parent().parent();
				var thisTr = thisTd.parent();
				var thisTrIndex = thisTr.index();
				var nextTd = thisTd.next();
				var nextTr = thisTr.next();
				var thisName = thisTd.attr("name");
				var thisPreName = thisName.split("_")[0];
				var thisAftName ;
				var jeLineType = jQuery("#"+radom+"jeLineType").val();
				if(thisPreName=="withLine"||thisPreName=='noLine'){
					var nextNoLineLendVal = nextTr.find("input[name=noLine_lend]").val();
					var nextNoLineLoanVal = nextTr.find("input[name=noLine_loan]").val();
					thisAftName = thisName.split("_")[1];
					if(thisAftName=='lend'){
						var thisValue = jQuery(this).val();
						if(thisValue){
							nextTd = nextTr.find("td:first");
							jQuery(this).trigger('blur');
							if(!nextNoLineLendVal&&!nextNoLineLoanVal){
								balanceLendAndLoan(nextTr,thisName,false,radom);
							}
						}
					}else{
						if(nextTr.length>0){
							nextTd = nextTr.find("td:first");
						}else{
							var addTr = thisTr.clone(true);
							if(addTr.attr("class")=="trbg"){
								addTr.removeClass("trbg");
							}else{
								addTr.addClass("trbg");
							}
							jQuery("#"+radom+"voucherCardTable>tbody").append(addTr);
							adjustByScrollBar(radom);
							nextTr = thisTr.next();
							nextTd = nextTr.find("td:first");
						}
						jQuery(this).trigger('blur');
						if(!nextNoLineLendVal&&!nextNoLineLoanVal){
							balanceLendAndLoan(nextTr,thisName,false,radom);
						}
					}
					if(!nextTr.find("input[name=abstract]").val()){
						nextTr.find("input[name=abstract]").val(thisTr.find("input[name=abstract]").val());
					}
				}else if(thisName=='account'){
					if(!jQuery(this).next().val()){
						jQuery(this).val("");
						dealAccountDialog(jQuery(this),radom);
						return;
					}else{
						chargeAssistDialog(jQuery(this),voucherDetailData,radom);
					}
					if(jeLineType=='no'){
						var thisTr = nextTd.parent();
						nextTd = thisTr.find("td[name='noLine_lend']:first");
					}
				}
				thisTd.find("span[name='toShow']").show();
				thisTd.find("span[name='toEdit']").hide();
				
				nextTd.trigger('click');
			}
		});
		
		jQuery("#"+radom+"findVoucherNo").unbind('blur').bind('blur',function(){
			chargeVoucherNo(radom);
		});
	}
	
	function balanceLendAndLoan(thisTr,thisName,isFocus,radom){
		var thisTrIndex = jQuery("#"+radom+"voucherCardTable").find("tbody>tr").index(thisTr)-1;
		var lendValue=0,loanValue=0;
		var noLineIndex = 0;
		jQuery("#"+radom+"voucherCardTable").find("td[name='noLine_lend']").each(function(){
			if(noLineIndex!=thisTrIndex){
				var thisValue = jQuery(this).text();
				thisValue = isNaN(parseFloat(thisValue))?0:parseFloat(thisValue);
				lendValue += thisValue;
			}
			noLineIndex++;
		});
		noLineIndex = 0;
		jQuery("#"+radom+"voucherCardTable").find("td[name='noLine_loan']").each(function(){
			if(noLineIndex!=thisTrIndex){
				var thisValue = jQuery(this).text();
				thisValue = isNaN(parseFloat(thisValue))?0:parseFloat(thisValue);
				loanValue += thisValue;
			}
			noLineIndex++;
		});
		//console.log(lendValue+","+loanValue);
		if(lendValue>loanValue){
			// put value in winthLine_lend and noLine_lend
			var chaValue = lendValue - loanValue;
			
			/* var chaValueStrArr = (""+chaValue).replace(".","");
			chaValueStrArr = (""+chaValue).split("");
			var lendInputs = thisTr.find("input[name='withLine_lend']");
			var loanInputs = thisTr.find("input[name='withLine_loan']");
			lendInputs.val("");
			for(var i=0;i<chaValueStrArr.length;i++){
				loanInputs.eq(inputLendLength-chaValueStrArr.length+i).val(chaValueStrArr[i]);
				//loanInputs.eq(inputLendLength-chaValueStrArr.length+i).parent().prev().text(chaValueStrArr[i]);
			}
			for(var i=0;i<inputLendLength;i++){
				var lendInputTemp = lendInputs.eq(i);
				var loanInputTemp = loanInputs.eq(i);
				lendInputTemp.parent().prev().text(lendInputTemp.val());
				loanInputTemp.parent().prev().text(loanInputTemp.val());
			} */
			chaValue = chaValue.toFixed(2);
			var noLineLendTd = thisTr.find("td[name='noLine_lend']");
			var noLineLoanTd = thisTr.find("td[name='noLine_loan']");
			var withLineLendInupts = thisTr.find("input[name='withLine_lend']");
			withLineLendInupts.val("");
			withLineLendInupts.trigger("blur");
			noLineLoanTd.find('span[name=toShow]').text(chaValue);
			noLineLoanTd.find('input[name=noLine_loan]').val(chaValue);
			noLineLoanTd.find('input[name=noLine_loan]').trigger('blur');
			noLineLendTd.find('span[name=toShow]').text("");
			noLineLendTd.find('input[name=noLine_loan]').val("");
			/* if(isFocus){
				if(thisName.split('_')[0]=='withLine'){
					loanInputs.last().parent().parent().trigger('click');
				}else if(thisName.split('_')[0]=='noLine'){
					noLineLoanTd.trigger('click');
				}
			} */
			
			
		}else if(lendValue<loanValue){
			var chaValue = loanValue  - lendValue;
			/* var chaValueStrArr = (""+chaValue).split("");
			var lendInputs = thisTr.find("input[name='withLine_lend']");
			var loanInputs = thisTr.find("input[name='withLine_loan']");
			loanInputs.val("");
			for(var i=0;i<chaValueStrArr.length;i++){
				lendInputs.eq(inputLendLength-chaValueStrArr.length+i).val(chaValueStrArr[i]);
				//loanInputs.eq(inputLendLength-chaValueStrArr.length+i).parent().prev().text(chaValueStrArr[i]);
			}
			for(var i=0;i<inputLendLength;i++){
				var lendInputTemp = lendInputs.eq(i);
				var loanInputTemp = loanInputs.eq(i);
				lendInputTemp.parent().prev().text(lendInputTemp.val());
				loanInputTemp.parent().prev().text(loanInputTemp.val());
			} */
			chaValue = chaValue.toFixed(2);
			var noLineLendTd = thisTr.find("td[name='noLine_lend']");
			var noLineLoanTd = thisTr.find("td[name='noLine_loan']");
			noLineLendTd.find('span[name=toShow]').text(chaValue);
			noLineLendTd.find('input[name=noLine_lend]').val(chaValue);
			noLineLendTd.find('input[name=noLine_lend]').trigger('blur');
			noLineLoanTd.find('span[name=toShow]').text("");
			noLineLoanTd.find('input[name=noLine_lend]').val("");
			var withLineLoanInupts = thisTr.find("input[name='withLine_loan']");
			withLineLoanInupts.val("");
			withLineLoanInupts.trigger("blur");
			/* if(isFocus){
				if(thisName.split('_')[0]=='withLine'){
					lendInputs.last().parent().parent().trigger('click');
				}else if(thisName.split('_')[0]=='noLine'){
					noLineLendTd.trigger('click');
				}
			} */
		}
		sumVoucherDetailMoney(radom);
	}
	
	//判断是否显示辅助核算窗口
	function chargeAssistDialog(thisInput,voucherDetailData,radom){
		var assits = thisInput.next().next().val();
		var acctId = thisInput.next().val();
		var acctCodes = thisInput.next().next().val();
		var acctName = thisInput.val();
		var abstractStr = thisInput.parent().parent().parent().find("input[name='abstract']").val();
		var thisTd = thisInput.parent().parent();
		var thisTr = thisTd.parent();
		var thisTrIndex = jQuery("#"+radom+"voucherCardTable>tbody>tr:visible").index(thisTr);
		
		/* if(acctCodes!=','&&acctCodes.indexof(',')!=-1){
		} */
		$.ajax({
		    url: 'getAssitTypesByAcct',
		    data:{acctId:acctId},
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		    	var assistTypes = data.assistTypes;
		    	if(assistTypes.length>0){
		    		jQuery("#"+radom+"assis_accountName").text(acctName);
		    		jQuery("#"+radom+"assis_abstract").val(abstractStr);
		    		jQuery("#"+radom+"voucherDetailNum").val(thisTrIndex);
		    		var thead = jQuery("#"+radom+"assistTable>thead>tr");
					var tbody = jQuery("#"+radom+"assistTable>tbody");
					thead.empty();
					tbody.empty();
					thead.append("<th align='center' class='pzCell_color' width='20px'></th>");
					thead.append("<th align='center' class='pzCell_color' width='20px'><input type='checkbox' name='assistTable_checkbox' value='-1' onclick='checkAssistsDataAll(this)'/></th>");
					thead.append("<th align='center' class='pzCell_color' >摘要</th>");
					
					tbody.append("<tr style='display:none'><td name='rn' align='center'>"+1+"</td><td name='cb' align='center'><input type='checkbox' name='assistTable_checkbox' value='"+1+"'/></td><td name='editable'><span name='toShow'></span><span name='toEdit' style='display:none'><input type='text' name='assistsAbstract' value=''/><input type='hidden' value=''/></span></td></tr>")
					
					jQuery("#"+radom+"assistInput").empty();
					var assistArr = new Array();
					var assistsData = new Array();
					var assistsMap = new Map();
					var assistsBdinfoMap = new Map();
					var assists = new Array();
					assists.push('assistsAbstract');
		    		for(var item in assistTypes){
		    			var assistData,thisIndex = item;
		    			assistsMap.put(assistTypes[item].typeCode,item);
		    			assists.push(assistTypes[item].typeCode);
						thead.append("<th align='center' class='pzCell_color'>"+assistTypes[item].typeName+"</th>");
						tbody.find("tr:first").append("<td name='editable'><span name='toShow'></span><span name='toEdit' style='display:none'><input type='text' name='"+assistTypes[item].typeCode+"' value=''/><input type='hidden' value=''/></span></td>");
						var odd = parseInt(item)+1;
						if(odd%2==0){
							jQuery("#"+radom+"assistInput>div.unit").append("<label>"+assistTypes[item].typeName+":</label><input id='"+radom+"selectTree"+assistTypes[item].typeCode+"' class='textInput' readonly='readonly'/><input type='hidden' class='textInput' id='"+radom+"selectTree"+assistTypes[item].typeCode+"_id'/>");
						}else{
							jQuery("#"+radom+"assistInput").append("<div class='unit'><label>"+assistTypes[item].typeName+":</label><input id='"+radom+"selectTree"+assistTypes[item].typeCode+"' class='textInput' readonly='readonly'/><input type='hidden' class='textInput' id='"+radom+"selectTree"+assistTypes[item].typeCode+"_id'/></div>");
						}
						var thisBdinfo = assistTypes[item].bdInfo;
						assistsBdinfoMap.put(assistTypes[item].typeCode,thisBdinfo);
						
						
						
						
						
						
						var findSql = "SELECT "+thisBdinfo.tablePkName+" id,"+thisBdinfo.tableDisplayName+" name from "+thisBdinfo.tableName;
						if(assistTypes[item].filter){
							findSql += " "+filter;
						}
						jQuery("#"+radom+"selectTree"+assistTypes[item].typeCode).treeselect({
							dataType:"sql",
							optType:"multi",
							sql:findSql,
							exceptnullparent:false,
							lazy:true,
							callback : {
								afterCheck :function(treeId,ids,names){
									var assistCode = treeId.split("_")[0].replace(radom+"selectTree","");
									var idArr = ids.split(",");
									var nameArr = names.split(",");
									assistData = new Array();
									for(var idIndex=0;idIndex<idArr.length;idIndex++){
										assistData.push(nameArr[idIndex]+","+idArr[idIndex]); 
									}
									assistArr[assistsMap.get(assistCode)] = assistData;
									assistsData = new Array();
									descartesResult(0,null,assistArr,assistsData);
									var assistTrs = new Array();
									var assistTds = new Array();
								
									tbody.empty();
									var assistKeys = assistsMap.keys();
									for(var tt=0;tt<assistsData.length;tt++){
										var thisAbstract = jQuery("#"+radom+"assis_abstract").val();
										var thisTrgb = "";
										if(tt%2==0){
											thisTrgb = "class='trbg'";
										}
										var addTrStr = "<tr "+thisTrgb+"><td name='rn' align='center'>"+(tt+1)+"</td><td name='cb' align='center'><input type='checkbox' name='assistTable_checkbox' value='"+(tt+1)+"'/></td><td name='editable'><span name='toShow'>"+thisAbstract+"</span><span name='toEdit' style='display:none'><input type='text' name='assistsAbstract' value='"+thisAbstract+"'/><input type='hidden' value=''/></span></td>";
										/* if(tt%2==0){
											addTrStr = "<tr class='trbg'><td name='rn' align='center'>"+(tt+1)+"</td><td name='cb' align='center'><input type='checkbox' name='assistTable_checkbox' value='"+(tt+1)+"'/></td>";
										} */
										for(var tdi=0;tdi<assistKeys.length;tdi++){
											var thisAssistValue = tdi<assistsData[tt].length?assistsData[tt][tdi]:"";
											var thisAssistValueName = thisAssistValue.split(",")[0];
											var thisAssistValueId = thisAssistValue.split(",")[1];
											addTrStr+="<td name='editable'><span name='toShow'>"+thisAssistValueName+"</span><span name='toEdit' style='display:none'><input type='text' name='"+assistKeys[tdi]+"' value='"+thisAssistValueName+"'/><input type='hidden' value='"+thisAssistValueId+"'/></span></td>";
										}
										var thisMoney = jQuery("#"+radom+"assis_money").val();
										if(!thisMoney){
											thisMoney = 0;
										}
										addTrStr+="<td align='right' name='editable'><span name='toShow'>"+thisMoney+"</span><span name='toEdit' style='display:none'><input type='text' name='assistsMoney' value='"+thisMoney+"'/><input type='hidden' value='0'/></span></td>";
										addTrStr += "</tr>";
										tbody.append(addTrStr);
									}
									sumAssistMoney(radom);
									addComboGrid(tbody,assistsBdinfoMap);
								//jQuery("#assistTable").cssTable();
								}
							}
						
						});
					
		    	}
		    	if(!voucherDetailData[thisTrIndex]){
		    		voucherDetailData[thisTrIndex] = {};
		    	}
		    	voucherDetailData[thisTrIndex]["assistsBdinfoMap"]=assistsBdinfoMap;
		    	assists.push('assistsMoney');
		    	thead.append("<th align='center' class='pzCell_color'>金额</th>");
		    	tbody.find("tr:first").append("<td align='right' name='editable'><span name='toShow'>"+0+"</span><span name='toEdit' style='display:none'><input type='text' name='assistsMoney' value='"+0+"'/><input type='hidden' value='0'/></span></td>");
		    	
		    	jQuery("#"+radom+"assistTable").delegate("td[name='editable']","click",function(){
		    		jQuery(this).find("span[name=toShow]").hide();
		    		jQuery(this).find("span[name=toEdit]").show();
		    		var thWidth = jQuery(this).innerWidth();
					var thHeight = jQuery(this).innerHeight();
					jQuery(this).find("span[name=toEdit]").find("input").eq(0).css({
						width:thWidth-10+"px",
						height:thHeight-12+"px"
					});
					jQuery(this).find("span[name=toEdit]").find("input").eq(0).focus();
		    		/* jQuery("#assistTable").find("td[name='editable']").not(jQuery(this)).each(function(){
			    		var thisValue = jQuery(this).find("span[name=toEdit]>input").val();
			    		jQuery(this).find("span[name=toShow]").text(thisValue);
		    			jQuery(this).find("span[name=toShow]").show();
			    		jQuery(this).find("span[name=toEdit]").hide();
		    		}); */
		    	});
		    	jQuery("#"+radom+"assistTable").delegate("input[type=text]","blur",function(){
		    		var thisSpan = jQuery(this).parent();
		    		thisSpan.prev().text(jQuery(this).val());
		    		thisSpan.prev().show();
		    		thisSpan.hide();
		    		sumAssistMoney(radom);
		    	});
		    	fillAssistsData(thisTrIndex,assists,tbody,null,null,voucherDetailData,radom);
		    	addComboGrid(tbody,assistsBdinfoMap);
				$.pdialog.open('#DIA_inline?inlineId='+radom+'assisForm_div', 'addAssisForVoucher', '原始凭证信息', {mask:false,width:650,height:500});　
				jQuery("#"+radom+"assis_abstract").focus();
				return ;
		    }
		    }
		});
	}
	
	function adjustByScrollBar(radom){
		var scrollBarWidth = jQuery("#"+radom+"voucherCardTableDiv").outerWidth()-jQuery("#"+radom+"voucherCardTable").outerWidth();
		if(scrollBarWidth>4){
			jQuery("#"+radom+"voucherCardTable_foot").find("th[name=scrollbar]").outerWidth(scrollBarWidth-4).show();
			jQuery("#"+radom+"voucherCardTable_head").find("th[name=scrollbar]").outerWidth(scrollBarWidth-4).show();
		}
	}
	
	function fillVoucherDetailData(voucherData,voucherDetailData,radom){
		if(voucherData){
			//voucherData = eval("("+voucherData+")");
			//voucherDetailData = voucherData["voucherDetailData"];
			//alert(json2str(voucherDetailData));
			var trs = jQuery("#"+radom+"voucherCardTable>tbody>tr");
			for(var item in voucherDetailData){
				var voucherDetail = voucherDetailData[item];
				var tr = trs.eq(parseInt(item));
				tr.find("td[name=abstract]").find("span[name=toShow]").text(voucherDetail["abstractStr"]);
				tr.find("td[name=abstract]").find("span[name=toEdit]>input").val(voucherDetail["abstractStr"]);
				tr.find("td[name=account]").find("span[name=toShow]").text(voucherDetail["accountFullName"]);
				tr.find("td[name=account]").find("span[name=toEdit]>input:first").val(voucherDetail["accountFullName"]);
				tr.find("td[name=account]").find("span[name=toEdit]>input:first").next().val(voucherDetail["accountId"]);
				var direction = voucherDetail["direction"];
				direction = direction=="借"?"lend":"loan";
				voucherDetail["direction"] = direction;
				if(direction=="lend"){
					tr.find("td[name=noLine_lend]").find("span[name=toShow]").text(voucherDetail["money"]);
					tr.find("td[name=noLine_lend]").find("span[name=toEdit]>input").val(voucherDetail["money"]);
					tr.find("td[name=noLine_lend]").find("span[name=toEdit]>input").trigger("blur");
				}else{
					tr.find("td[name=noLine_loan]").find("span[name=toShow]").text(voucherDetail["money"]);
					tr.find("td[name=noLine_loan]").find("span[name=toEdit]>input").val(voucherDetail["money"]);
					tr.find("td[name=noLine_loan]").find("span[name=toEdit]>input").trigger("blur");
				}
			}
			//voucherDetailData = voucherData["voucherDetailData"];
			//alert(json2str(voucherDetailData));
		}else{
			voucherData = {};
		}
	}
	
	
	//填充assistTable
	function fillAssistsData(voucherDetailNum,assists,tbody,thead,type,voucherDetailData,radom){
		var voucherDetail = voucherDetailData[voucherDetailNum];
		var assistsData = voucherDetail&&voucherDetail["assistData"];
		var assistInputs = new Map();
		var assistInputsID = new Map();
		var theadStr = "";
		if(assistsData){
			if(!assists||assists.length==0){
				assists = new Array();
				var accountId = jQuery("#"+radom+"voucherCardTable>tbody>tr").eq(voucherDetailNum).find("input[name=account]:first").next().val();
				if(!accountId){
					return ;
				}
				$.ajax({
				    url: 'getAssitTypesByAcct',
				    data:{acctId:accountId},
				    type: 'post',
				    dataType: 'json',
				    async:false,
				    error: function(data){
				    },
				    success: function(data){
				    	//todo
				    	var assistTypes = data.assistTypes;
				    	if(assistTypes.length>0){
				    		theadStr +="<th align='center' class='pzCell_color' width='20px'></th>";
				    		if(type!='read'){
					    		theadStr +="<th align='center' class='pzCell_color' width='20px'><input type='checkbox' name='assistTable_checkbox' value='-1' onclick='checkAssistsDataAll(this)'/></th>";
				    		}
							theadStr +="<th align='center' class='pzCell_color' >摘要</th>";
							assists.push('assistsAbstract');
							for(var item in assistTypes){
				    			//var assistData,thisIndex = item;
				    			//assistsMap.put(assistTypes[item].typeCode,item);
				    			assists.push(assistTypes[item].typeCode);
				    			theadStr +="<th align='center' class='pzCell_color'>"+assistTypes[item].typeName+"</th>";
				    		}
				    		theadStr +="<th align='center' class='pzCell_color'>金额</th>";
				    		assists.push('assistsMoney');
				    		if(thead){
				    			thead.append(theadStr);
				    		}
				    	}
				    }
				});
			}
		}
		for(var item in assistsData){
			var tt = parseInt(item);
			var assistData = assistsData[item];
			var checkboxStr = "<td name='cb' align='center'><input type='checkbox' name='assistTable_checkbox' value='"+(tt+1)+"'/></td>";
			if(type=='read'){
				checkboxStr = "";
			}
			var addTrStr = "<tr><td name='rn' align='center'>"+(tt+1)+"</td>"+checkboxStr;
			if(tt%2==0){
				addTrStr = "<tr class='trbg'><td name='rn' align='center'>"+(tt+1)+"</td>"+checkboxStr;
			}
			for(var dataIndex=0;dataIndex<assists.length;dataIndex++){
				var dataKey = assists[dataIndex];
				var tdAlign = 'left';
				if(dataKey=='assistsMoney'){
					tdAlign = 'right';
				}
				var thisAssistData = assistData[dataKey];
				var thisAssistDataName = thisAssistData.split(",")[0];
				var thisAssistDataId = thisAssistData.split(",")[1];
				addTrStr+="<td align='"+tdAlign+"' name='editable'><span name='toShow'>"+thisAssistDataName+"</span><span name='toEdit' style='display:none'><input type='text' name='"+dataKey+"' value='"+thisAssistDataName+"'/><input type='hidden' value='"+thisAssistDataId+"'/></span></td>";
				var assistInputStr = assistInputs.get(dataKey)||"";
				var assistInputIdStr = assistInputsID.get(dataKey)||"";
				if(assistInputStr.indexOf(thisAssistDataName)==-1){
					assistInputStr += thisAssistDataName+',';
					assistInputIdStr += thisAssistDataId+',';
				}
				assistInputs.put(dataKey,assistInputStr);
				assistInputsID.put(dataKey,assistInputIdStr);
			}
			addTrStr += "</tr>";
			tbody.append(addTrStr);
		}
		/*var thisFirstTr = tbody.find("tr:first");
		if(thisFirstTr){
			var thisFirstTrClone = thisFirstTr.clone(true);
			thisFirstTrClone.find("input[type=text]").each(function(){
				jQuery(this).val("");
				jQuery(this).parent().prev().text("");
			});
			thisFirstTrClone.hide();
			thisFirstTr.before(thisFirstTrClone);
		}*/
		sumAssistMoney(radom);
		var assistInputsKeys = assistInputs.keys();
		for(var i=0;i<assistInputsKeys.length;i++){
			var assistInputValue = assistInputs.get(assistInputsKeys[i]);
			var assistInputIdValue = assistInputsID.get(assistInputsKeys[i]);
			if(assistInputValue!=""){
				assistInputValue = assistInputValue.substring(0,assistInputValue.length-1);
				assistInputIdValue = assistInputIdValue.substring(0,assistInputIdValue.length-1);
			}
			jQuery("#"+radom+"selectTree"+assistInputsKeys[i]).val(assistInputValue);
			jQuery("#"+radom+"selectTree"+assistInputsKeys[i]+'_id').val(assistInputIdValue);
			//alert(assistInputIdValue);
		}
	}
	

	function rebuildTable(tableId){
		var trIndex = 1;
		jQuery('#'+tableId+'>tbody>tr:visible').each(function(){
			jQuery(this).find('td[name=rn]').each(function(){
				jQuery(this).text(trIndex);
				trIndex++;
			});
		});
		trIndex = 1;
		jQuery('#'+tableId+'>tbody>tr:visible').each(function(){
			jQuery(this).removeClass('trbg');
			if(trIndex%2==0){
				jQuery(this).addClass('trbg');
			}
			trIndex++;
		});
	}
	
	//sum assist Money
	function sumAssistMoney(radom){
		var theSum = 0;
		jQuery("#"+radom+"assistTable").find("input[name=assistsMoney]").each(function(){
			theSum +=parseFloat(jQuery(this).val());
		});
		theSum = theSum.toFixed(2);
		theSum = isNaN(theSum)==true?0:theSum;
		jQuery("#"+radom+"assistSumMoney").text(theSum);
		return theSum;
	}
	
	function sumVoucherDetailMoney(radom){
		//alert('sumVoucherDetailMoney');
		var lendMoney = 0,loanMoney = 0;
		jQuery("#"+radom+"voucherCardTable").find("input[name='noLine_lend']").each(function(){
			lendMoney += isNaN(parseFloat(jQuery(this).val()))?0:parseFloat(jQuery(this).val());
		});
		jQuery("#"+radom+"voucherCardTable").find("input[name='noLine_loan']").each(function(){
			loanMoney += isNaN(parseFloat(jQuery(this).val()))?0:parseFloat(jQuery(this).val());
		});
		//console.log(lendMoney+","+loanMoney);
		jQuery("#"+radom+"voucherCardTable_foot").find("input[name='noLine_lend']:first").val(lendMoney);
		jQuery("#"+radom+"voucherCardTable_foot").find("input[name='noLine_lend']:first").trigger("blur");
		jQuery("#"+radom+"voucherCardTable_foot").find("input[name='noLine_loan']:first").val(loanMoney);
		jQuery("#"+radom+"voucherCardTable_foot").find("input[name='noLine_loan']:first").trigger("blur");
		
		var footMoney = jQuery("#"+radom+"voucherCardTable_foot").find("span[name=sumVoucherDetailMoney]>label");
		var unit = "千百拾亿千百拾万千百拾元角分", str = "";
        //n += "00";
        lendMoney = lendMoney.toFixed(2);
    	var p = lendMoney.indexOf('.');
    	if (p >= 0)
    		lendMoney = lendMoney.substring(0, p) + lendMoney.substr(p+1, 2);
        	//unit = unit.substr(unit.length - lendMoney.length);
        for (var i=0; i < lendMoney.length; i++){
         	str = '零壹贰叁肆伍陆柒捌玖'.charAt(lendMoney.charAt(i));
        footMoney.eq(11-lendMoney.length+i).text(str);
        }
        for(var i=0;i<(11-lendMoney.length);i++){
        	footMoney.eq(i).text("/");
        }
		//jQuery("#voucherCardTable_foot").find("th[name='account']").text(changeToDX(lendMoney));
	}
	
	function addComboGrid(tbody,assistBdinfoMap){
		//alert(json2str(assistBdinfoMap));
		tbody.find("tr:first").find("input[type=text]").each(function(){
			var thisName = jQuery(this).attr("name");
			var thisBdinfo = assistBdinfoMap.get(thisName);
			if(thisBdinfo){
				
			var sql = "SELECT * FROM "+thisBdinfo.tableName+" where ";
			if(thisBdinfo.orgCodeColName){
				sql += thisBdinfo.orgCodeColName+"='%ORGCODE%' and "
			}
			if(thisBdinfo.copyCodeColName){
				sql += thisBdinfo.copyCodeColName+"='%COPYCODE%' and "
			}
			if(thisBdinfo.kjYearColName){
				sql += thisBdinfo.kjYearColName+"='%PERIODYEAR%' and "
			}
			sql += " 1=1 ";
			var cloumns = thisBdinfo.tableDisplayName.toUpperCase();
			var cnCode = thisBdinfo.tableCnCodeColName.toUpperCase();
			tbody.find("input[name='"+thisName+"']").each(function(){
				jQuery(this).combogrid({
					url : '${ctx}/comboGridSqlList',
					queryParams:{
						sql : sql,
						cloumns : cloumns
					},
					colModel : [{
						'columnName' : cloumns,
						'width' : '70',
						'label' : '名称'
					},
					{
						'columnName' : cnCode,
						'width' : '30',
						'label' : '拼音码'
					}],
					select : function(event, ui) {
						//$("#inventoryDict_invType").val(ui.item.ID);
						$(this).val(ui.item[cloumns]);
						return false;
					}
				});
			});
			}
		});
	}
	
	//have some quest
	function addVoucherButtonEvent(voucherData,voucherDetailData,radom){
		//function : save voucherDetail to voucherDetailData;
		jQuery("#"+radom+"saveVoucherDetail").click(function(){
			var voucherDetailStr = {};
			var voucherDetailNum = jQuery("#"+radom+"voucherDetailNum").val();
			var trIndex = 0, tdIndex = 0 ; errorFlag = false;
			var assists = new Array();
			var thisThead = jQuery("#"+radom+"assistTable").find("thead>tr>th");
			jQuery("#"+radom+"assistTable").find("tbody>tr:visible").each(function(){
				//voucherDetailStr = trIndex+""
				var assistRowDataJson = {};
				jQuery(this).find("input[type='text']").each(function(){
					if(!jQuery(this).is("input[type=checkbox]")){
						var thisValue = jQuery(this).val();
						if(!thisValue){
							if(jQuery(this).attr("name")!='assistsAbstract'){
								if(jQuery(this).attr("name")=='assistsMoney'&&thisValue==0){
									alert("第"+(trIndex+1)+"行的金额不能为0！");
									errorFlag = true;
									return false;
								}
								var thisTdIndex = jQuery(this).parent().parent().index();
								alert("第"+(trIndex+1)+"行的"+thisThead.eq(thisTdIndex).text()+"没有数据！");
								errorFlag = true;
								return false;
							}
						}
						assistRowDataJson[jQuery(this).attr("name")]=jQuery(this).val()+","+jQuery(this).next().val();
						assists.push(jQuery(this).attr("name"));
					}
					
				});
				if(errorFlag){
					return false;
				}
				voucherDetailStr[trIndex]=assistRowDataJson;
				trIndex++;
			});
			if(errorFlag){
				return false;
			}
			var moneyDirector = jQuery("#"+radom+"assisForm").find("input[name=lendOrLoan]:checked").val(); 
			var otherDirector = moneyDirector=='lend'?'loan':'lend'; 
			var theSum = sumAssistMoney(radom);
			voucherDetailData[voucherDetailNum] = {};
			voucherDetailData[voucherDetailNum]["assistData"] = voucherDetailStr;
			voucherDetailData[voucherDetailNum]["direction"] = moneyDirector;
			voucherDetailData[voucherDetailNum]["money"] = theSum;
			var thisNoLine = jQuery("#"+radom+"voucherCardTable>tbody>tr:visible").eq(voucherDetailNum).find("input[name=noLine_"+moneyDirector+"]");
			var otherNoLine = jQuery("#"+radom+"voucherCardTable>tbody>tr:visible").eq(voucherDetailNum).find("input[name=noLine_"+otherDirector+"]");
			thisNoLine.val(theSum);
			otherNoLine.val("");
			thisNoLine.trigger('blur');
			otherNoLine.trigger('blur');
			var assisReadTbody = jQuery("#"+radom+"assistTable_read>tbody");
			var assisReadThead = jQuery("#"+radom+"assistTable_read>thead");
			assisReadTbody.empty();
			assisReadThead.empty();
			fillAssistsData(voucherDetailNum,null,assisReadTbody,assisReadThead,'read',voucherDetailData,radom);
			$.pdialog.closeCurrentDiv(radom+'assisForm_div');
			if(jQuery("#"+radom+"jeLineType").val()=="yes"){
				var thisWithLine = jQuery("#"+radom+"voucherCardTable>tbody>tr:visible").eq(voucherDetailNum).find("input[name=withLine_"+moneyDirector+"]");
				thisWithLine.parent().parent().trigger("click");
			}else{
				thisNoLine.parent().parent().trigger("click");
			}
			sumVoucherDetailMoney(radom);
		});
		
		
		jQuery("#"+radom+"addAssitsData").click(function(){
			var lastTr = jQuery("#"+radom+"assistTable").find("tbody>tr:last").clone(false);
			lastTr.find('input[type=checkbox]').removeAttr('checked');
			lastTr.find('span[name=toShow]').show().text('');
			lastTr.find('span[name=toEdit]').hide().find('input').val('');
			lastTr.show();
			jQuery("#"+radom+"assistTable>tbody").append(lastTr);
			rebuildTable(radom+'assistTable');
			lastTr.find("td").eq(2).trigger("click");
			var trNum = jQuery("#"+radom+"voucherDetailNum").val();
			addComboGrid(jQuery("#"+radom+"assistTable").find("tbody"),voucherDetailData[trNum]["assistsBdinfoMap"]);
		});
		
		jQuery("#"+radom+"saveVoucher").click(function(){
			var absIndex = 0;
			var shouldHaveData = 0 , editFlag = false , errorFlag = false;
			//voucherDetailDatavoucherDetailData.length;
			jQuery("#"+radom+"voucherCardTable>tbody>tr:visible").each(function(){
				if(absIndex==0){
					absIndex++;
					return true;
				}
				var thisAbstract = jQuery(this).find("input[name='abstract']").val();
				var thisAccoutId = jQuery(this).find("input[name='account']").next().val();
				var thisLend = jQuery(this).find("input[name='noLine_lend']").val();
				var thisLoan = jQuery(this).find("input[name='noLine_loan']").val();
				var isEdit = false;
				if(thisAbstract||thisAccoutId||thisLend||thisLoan){
					isEdit = true;
				}else{
					shouldHaveData = shouldHaveData!=0?shouldHaveData:absIndex;
				}
				if(isEdit){
					if(shouldHaveData!=0){
						editFlag = true;
					}
					if(!thisAccoutId){
						alert("第"+absIndex+"行记录没有设置科目！");
						jQuery(this).find("td[name='account']").trigger('click');
						errorFlag = true;
						return false;
					}
					if(!thisLend&&!thisLoan){
						alert("第"+absIndex+"行记录没有设置金额！");
						jQuery(this).find("td[name='widthLine_lend']").trigger('click');
						errorFlag = true;
						return false;
					}
					if(!voucherDetailData[absIndex]){
						voucherDetailData[absIndex] = {};
						
					}else{
						var vdm = voucherDetailData[absIndex]['money'];
						var shouldMoney = thisLend||thisLoan;
						if(vdm!=shouldMoney){
							alertMsg.error("第"+absIndex+"行记录金额和辅助核算金额不一致，请检查!");
							errorFlag = true;
							return false;
						}
					}
					voucherDetailData[absIndex]['abstractStr'] = thisAbstract;
					voucherDetailData[absIndex]['accountId'] = thisAccoutId;
					voucherDetailData[absIndex]['direction'] = thisLend?'lend':'loand';
					voucherDetailData[absIndex]['money'] = thisLend||thisLoan;
				}
				absIndex++;
			});
			if(errorFlag){
				return;
			}
			if(editFlag&&shouldHaveData!=0){
				alert("第"+shouldHaveData+"行记录没有数据！");
				return ;
			}
			if(voucherDetailData&&!jQuery.isEmptyObject(voucherDetailData)){
				var voucherType = jQuery("#"+radom+"voucherInfoTable").find("select[name=voucherType]:first").val();
				var voucherNo = jQuery("#"+radom+"findVoucherNo").val();
				var voucherDate = jQuery("#"+radom+"voucherDate").val();
				var attachNum = jQuery("#"+radom+"attachNum").val();
				if(!voucherNo){
					alertMsg.error("凭证号错误！");
					return ;
				}
				if(!voucherDate){
					alertMsg.error("日期错误！");
					return ;
				}
				if(!attachNum){
					alertMsg.error("附单据数错误！");
					return ;
				}
				voucherData["voucherType"] = voucherType;
				voucherData["voucherNo"] = voucherNo;
				voucherData["voucherDate"] = voucherDate;
				voucherData["attachNum"] = attachNum;
				voucherData["voucherDetailData"] = voucherDetailData;
			}else{
				alertMsg.error("没有凭证数据！");
				return ;
			}
			
			if(!chargeVoucherDetailMoney(radom)){
				return ;
			}
			if(!chargeVoucherNo(radom)){
				return ;
			}
			
			var voucherDataStr = json2str(voucherData);
			//console.log(voucherDataStr);
			$.ajax({
			    url: 'saveVoucherDetailData',
			    type: 'post',
			    data:{voucherData:voucherDataStr},
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			    },
			    success: function(data){
			        formCallBack(data);
			    }
			});
			
		});
	}
	
	
	function addAssitsData(radom){
		var lastTr = jQuery("#"+radom+"assistTable").find("tbody>tr:last").clone(true);
		lastTr.find('input[type=checkbox]').removeAttr('checked');
		lastTr.find('span[name=toShow]').show().text('');
		lastTr.find('span[name=toEdit]').hide().find('input').val('');
		lastTr.show();
		jQuery("#"+radom+"assistTable>tbody").append(lastTr);
		rebuildTable(radom+'assistTable');
		lastTr.find("td").eq(2).trigger("click");
		
		//addComboGrid(jQuery("#"+radom+"assistTable").find("tbody"),assistsBdinfoMap);
	}
	function insertAssitsData(radom){
		var thisTr = jQuery("#"+radom+"assistTable>tbody").find("input[name=assistTable_checkbox]:checked").first().parent().parent();
		var thisTrClone = thisTr.clone(true);
		thisTrClone.find('input[type=checkbox]').removeAttr('checked');
		thisTrClone.find('span[name=toShow]').show().text('');
		thisTrClone.find('span[name=toEdit]').hide().find('input').val('');
		thisTr.before(thisTrClone);
		rebuildTable(radom+'assistTable');
	}
	function deleteAssitsData(radom){
		jQuery("#"+radom+"assistTable>tbody>tr:visible").each(function(){
			jQuery(this).find("input[name=assistTable_checkbox]:checked").each(function(){
				if(jQuery(this).val()!=-1){
					jQuery(this).parent().parent().remove();
				}
			});
		});
		
		rebuildTable(radom+'assistTable');
	}
	
	function descartesResult(arrIndex,aresult,arrayContainer,dataContainer)
	{
	  if(arrIndex>=arrayContainer.length) {
		  //alert(arrIndex+","+aresult);
		  dataContainer.push(aresult);return;};
	  var aArr=arrayContainer[arrIndex];
	  if(!aresult) aresult=new Array();
	  for(var i=0;i<aArr.length;i++)
	  {
	    var theResult=aresult.slice(0,aresult.length);
	    theResult.push(aArr[i]);
	    descartesResult(arrIndex+1,theResult,arrayContainer,dataContainer);
	    //dataContainer[dataContainer.length]=theResult;
		//theResult = null;
	    //theResult.push('end');
		//alert(arrIndex+","+dataContainer);
	  }
	}
	
	
	function checkAssistsDataAll(obj){
		var thisName = jQuery(obj).attr('name');
		var thisChecked = jQuery(obj).attr('checked');
		if(thisChecked=='checked'){
			jQuery('input[name='+thisName+']').attr('checked','checked');
		}else{
			jQuery('input[name='+thisName+']').removeAttr('checked');
		}
	}
	
	function showAccountTreeFuction(radom){
		alert(radom);
		var url = "#DIA_inline?inlineId=accountTreeDialog";
		//navTab.openTab("accountTree", url, { title:"会计科目", fresh:true, data:{} });
		$.pdialog.open(url, 'accountTree', "会计科目", {mask:false,width:600,height:500});　
	}
	function hideVoucherJeLine(obj,radom){
		if(jQuery("#"+radom+"jeLineType").val()=="yes"){
			jQuery("#"+radom+"voucherCardTable").find("th[name=withLine]").hide();
			jQuery("#"+radom+"voucherCardTable").find("td[name=withLine_lend]").hide();
			jQuery("#"+radom+"voucherCardTable").find("td[name=withLine_loan]").hide();
			
			jQuery("#"+radom+"voucherCardTable_head,#"+radom+"voucherCardTable_foot").find("th[name=withLine]").hide();
			
			jQuery("#"+radom+"voucherCardTable").find("th[name=noLine]").show();
			jQuery("#"+radom+"voucherCardTable").find("td[name=noLine_lend]").show();
			jQuery("#"+radom+"voucherCardTable").find("td[name=noLine_loan]").show();
			
			jQuery("#"+radom+"voucherCardTable_head,#"+radom+"voucherCardTable_foot").find("th[name=noLine]").show();
			
			jQuery(obj).find("span:first").text("显示金额线");
			jQuery("#"+radom+"jeLineType").val("no");
		}else{
			jQuery("#"+radom+"voucherCardTable").find("th[name=withLine]").show();
			jQuery("#"+radom+"voucherCardTable").find("td[name=withLine_lend]").show();
			jQuery("#"+radom+"voucherCardTable").find("td[name=withLine_loan]").show();
			
			jQuery("#"+radom+"voucherCardTable_head,#"+radom+"voucherCardTable_foot").find("th[name=withLine]").show();
			
			jQuery("#"+radom+"voucherCardTable").find("th[name=noLine]").hide();
			jQuery("#"+radom+"voucherCardTable").find("td[name=noLine_lend]").hide();
			jQuery("#"+radom+"voucherCardTable").find("td[name=noLine_loan]").hide();
			
			jQuery("#"+radom+"voucherCardTable_head,#"+radom+"voucherCardTable_foot").find("th[name=noLine]").hide();
			
			jQuery(obj).find("span:first").text("隐藏额线");
			jQuery("#"+radom+"jeLineType").val("yes");
		}
		
	}
	
	function readCardNeed(radom){
		jQuery("#"+radom+"voucherCardTable,#"+radom+"voucherCardTable_foot").delegate("input[name='noLine_lend'],input[name='noLine_loan']","blur",function(){
			var thisDirector = jQuery(this).attr('name').split("_")[1];
			var thisValue = jQuery(this).val();
			var thisWithLines = jQuery(this).parent().parent().parent().find("input[name=withLine_"+thisDirector+"]");
			if(thisValue){
				thisValue = parseFloat(thisValue).toFixed(2);
				jQuery(this).val(thisValue);
				jQuery(this).parent().prev().text(thisValue);
			}else{
				thisWithLines.val("");
				thisWithLines.trigger("blur");
				return ;
			}
			/* if(thisValue&&thisValue.indexOf(".")==-1){
				thisValue += ".00";
				jQuery(this).val(thisValue);
				jQuery(this).parent().prev().text(thisValue);
			} */
			if(thisValue==0){
				thisWithLines.val("");
				thisWithLines.trigger("blur");
				return ;
			}
			thisValue = thisValue.replace(".","");
			var thisMoneyData = thisValue.split("");
			var beginIndex = inputLendLength-thisMoneyData.length;
			var mi=0;
			thisWithLines.each(function(){
				if(mi>=beginIndex){
					jQuery(this).val(thisMoneyData[mi-beginIndex]);
					jQuery(this).parent().prev().text(thisMoneyData[mi-beginIndex]);
				}
				mi++;
			});
		});
	}
	
	function dealAccountDialog(obj,radom){
		var thisIndex = jQuery("#"+radom+"voucherCardTable").find("input[name='account']").index(obj);
		jQuery("#"+radom+"accountTree").parent().find("input[name=currentRowNum]").val(thisIndex);
		var thisValue = obj.val();
    	var accountTree = $.fn.zTree.getZTreeObj(radom+"accountTree");
    	var nodes = accountTree.getNodesByParamFuzzy("id", thisValue, null);
		var selectNode = null;
    	if(nodes.length>0){
			selectNode = nodes[0];
		}else{
			nodes = accountTree.getNodesByParamFuzzy("name", thisValue, null);
			selectNode = nodes[0];
		}
    	var nodeTop = 0;
    	if(selectNode){
    		accountTree.selectNode(selectNode);
    	}
		var url = "#DIA_inline?inlineId="+radom+"accountTreeDialog";
		//navTab.openTab("accountTree", url, { title:"会计科目", fresh:true, data:{} });
		$.pdialog.open(url, 'accountTree', "会计科目", {mask:true,width:600,height:500});　
		var selectNode_a = jQuery("#"+radom+"accountTree").find("a.curSelectedNode");
		//nodeTop = selectNode_a.offset().top;
		nodeTop = selectNode_a.position().top;
		jQuery("#"+radom+"accountTree").parent().scrollTop(nodeTop-100);
		jQuery("#"+radom+"accountTreeContainer").focus();
	}
	
	function chargeVoucherNo(radom){
		var thisValue = jQuery("#"+radom+"findVoucherNo").val();
		var shouldValue = jQuery("#"+radom+"findVoucherNo").next().val();
		var tsStr = jQuery("#"+radom+"findVoucherNo").next().next().val();;
		var voucherType = jQuery("#"+radom+"voucherInfoTable").find("select[name=voucherType]").val();
		var flag = false;
		$.ajax({
		    url: 'isExistVoucherNo',
		    data:{voucherNo:thisValue,voucherType:voucherType},
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    },
		    success: function(data){
		        // do something with xml
		    	if(thisValue==shouldValue){
		    		if(data.ts!=tsStr){
		    			//alertMsg.error("此凭证已过期！");
		    			alertMsg.confirm("此凭证已过期,是否重新载入凭证?", {
		    				okCall: function(){
		    					navTab.reload("voucherCard", {
		    						title : "",
		    						fresh : false,
		    						data : {voucherType:voucherType,VoucherNo:thisValue}
		    					});
		    				}
		    			});
		    			flag =  false;
		    		}else{
		    			flag =  true;
		    		}
		    	}else{
		    		if(data.isExistVoucherNo){
		    			if(data.isExistVoucherNo=='1'){
		    				alertMsg.error("此凭证号已存在！");
		    				jQuery("#"+radom+"findVoucherNo").val(shouldValue);
		    				flag =  false;
		    			}
		    		}else{
		    			flag = true;
		    		}
		    	}
		    }
		});
		return flag;
	}
	function chargeVoucherDetailMoney(radom){
		var lendMoney = jQuery("#"+radom+"voucherCardTable_foot").find("input[name='noLine_lend']:first").val();
		var loanMoney = jQuery("#"+radom+"voucherCardTable_foot").find("input[name='noLine_loan']:first").val();
		if(lendMoney!=loanMoney){
			alertMsg.error("借贷金额不相等,请检查!");
			return false;
		}else{
			return true;
		}
	}