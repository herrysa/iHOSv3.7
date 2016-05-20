<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	var gzItemNodeObj = {};
	var gzItemResultNodeObj = {};
	var gzItemFormulaGridRowObj = {};
	var gzItemFormulaCheckObj = {};
	var gzItemCode = "";
	var expFlag = true;
	var gzTypeId;
	jQuery(document).ready(function() {
		var gzItemFormulaCheckStr = jQuery("#gzItemFormulaCheckStr").val();
		if(gzItemFormulaCheckStr){
			gzItemFormulaCheckObj = JSON.parse(gzItemFormulaCheckStr);
		}
		gzTypeId = "${gzItemFormula.gzItem.gzType.gzTypeId}";
		gzItemCode = "${gzItemFormula.gzItem.itemCode}";
		var otherExpType = "${gzItemFormula.otherExpType}";
		if(otherExpType){
			jQuery("input[name='gzItemRadio'][value='"+otherExpType+"']").attr("checked",true);
		}
		if(!"${gzItemFormula.expContent}"){
			jQuery("#gzItemFormula_finalExp").val("如果;那么");
		}
		jQuery("#gzItemFormula_finalExp").attr("title",jQuery("#gzItemFormula_finalExp").val());
		/*jQuery('#savelink').click(function() {
			jQuery("#gzItemFormulaForm").attr("action","saveGzItemFormula?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#gzItemFormulaForm").submit();
		});*/
		 jQuery.ajax({
 		       url: 'getGzItemNodesForFormula',
 		       data: {gzTypeId:gzTypeId,itemCode:gzItemCode},
 		       type: 'post',
 		       dataType: 'json',
 		       async:false,
 		       error: function(data){
 		       },
 		       success: function(data){
 		    	var gzItemNodes = data.gzItemNodes;
 		    	if(gzItemNodes){
		    		  jQuery.each(gzItemNodes,function(index,gzItemNode){
		    			  var code = gzItemNode["id"];
		    			  var name = gzItemNode["name"];
		    			  var type = gzItemNode["type"]+"";
		    			  var values = gzItemNode["values"];
		    			  gzItemNodeObj[code] = {code:code,name:name,type:type,values:values};
		    		  });
 		    	}
 		       }
 		   });
		
		 var gzItemFormulaFilterGridIdString="#gzItemFormulaFilter_gridtable";
		 var gzItemFormulaFilterGrid = jQuery(gzItemFormulaFilterGridIdString);
		 gzItemFormulaFilterGrid.jqGrid({
		    	url : "gzItemFormulaFilterGridList?formulaId=${gzItemFormula.formulaId}",
		    	editurl:"gZItemFormulaFilterGridEdit",
				datatype : "json",
				mtype : "GET",
		        colModel:[
		{name:'filterId',index:'filterId',align:'center',label : '<s:text name="gzItemFormulaFilter.filterId" />',hidden:true,key:true},	
		{name:'name',index:'name',width:'120px',align:'left',label : '<s:text name="gzItemFormulaFilter.name" />',hidden:false,sortable:false},
		{name:'code',index:'code',align:'center',label : '<s:text name="gzItemFormulaFilter.code" />',hidden:true},	
		{name:'oper',index:'oper',width:'80px',align:'center',label : '<s:text name="gzItemFormulaFilter.oper" />',hidden:false,sortable:false,
			formatter:function(cellvalue, options, rowObject) {
				var editvalue;
				editvalue='<select class="operSelect" style="float:clear">';
				if(cellvalue=="="){
					editvalue+='<option value ="=" selected>=</option>';
				}else{
					editvalue+='<option value ="=">=</option>';
				}
				if(cellvalue==">"){
					editvalue+='<option value =">" selected>></option>';
				}else{
					editvalue+='<option value =">">></option>';
				}
				if(cellvalue==">="){
					editvalue+='<option value =">=" selected>>=</option>';
				}else{
					editvalue+='<option value =">=">>=</option>';
				}
				if(cellvalue=="<"){
					editvalue+='<option value ="<" selected><</option>';
				}else{
					editvalue+='<option value ="<"><</option>';
				}
				if(cellvalue=="<="){
					editvalue+='<option value ="<=" selected><=</option>';
				}else{
					editvalue+='<option value ="<="><=</option>';
				}
				if(cellvalue=="<>"){
					editvalue+='<option value ="<>" selected><></option>';
				}else{
					editvalue+='<option value ="<>"><></option>';
				}
				if(cellvalue=="like"){
					editvalue+='<option value ="like" selected>like</option>';
				}else{
					editvalue+='<option value ="like">like</option>';
				}
				
				if(cellvalue=="not like"){
					editvalue+='<option value ="not like" selected>not like</option>';
				}else{
					editvalue+='<option value ="not like">not like</option>';
				}
				editvalue += '</select>';
				return editvalue;
				}
		},	
		{name:'searchValue',width:'200px',index:'searchValue',align:'left',label : '<s:text name="gzItemFormulaFilter.searchValue" />',hidden:false,sortable:false,
			formatter:function(cellvalue, options, rowObject) {
				var code = rowObject['code'];
				var editvalue = null;
				if(cellvalue){
					editvalue = '<input class="searchValue" type="text" value="'+cellvalue +'">';
				}else{
					editvalue = '<input class="searchValue" type="text">';
				}
				var gzItemNodeObjTemp = gzItemNodeObj[code];
				if(gzItemNodeObjTemp){
					var type = gzItemNodeObjTemp.type;
	    			if(type == "4"){
	    				if(cellvalue){
	    					editvalue = '<input class="searchValue" onclick="WdatePicker({skin:\'ext\',dateFmt:\'yyyy-MM-dd\'})" type="text" value="'+cellvalue+'"></input>';
	    				}else{
	    					editvalue = '<input class="searchValue" onclick="WdatePicker({skin:\'ext\',dateFmt:\'yyyy-MM-dd\'})" type="text"></input>';
	    				}
	    				
	    			}else if(type == "5"){
	    				editvalue = '<select  class="searchValue" style="float:clear">';
	    				editvalue += '<option value ="">--</option>';
	    				var values = gzItemNodeObjTemp.values;
	    				if(values){
	    					 $.each(values, function(i,val){ 
	    						 if(cellvalue == val){
	    							 editvalue += ' <option value ='+val+' selected>'+val+'</option>';
	    						 }else{
	    							 editvalue += ' <option value ='+val+'>'+val+'</option>';
	    						 }
	    					  });   
	    				}
	    				editvalue += '</select>';
	    			}else if(type == "2"){
	    				if(cellvalue){
	    					editvalue = '<input class="searchValue number" type="text" value="'+cellvalue +'">';
	    				}else{
	    					editvalue = '<input class="searchValue number" type="text">';
	    				}
	    			}else if(type == "3"){
	    				if(cellvalue){
	    					editvalue = '<input class="searchValue digits" type="text" value="'+cellvalue +'">';
	    				}else{
	    					editvalue = '<input class="searchValue digits" type="text">';
	    				}
	    			}
				}
				return editvalue;
			}
			}				
		        ],
		        jsonReader : {
					root : "gzItemFormulaFilters", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				// (4)
				},
		        sortname: 'filterId',
		        viewrecords: true,
		        sortorder: 'asc',
		        height:95,
		        gridview:true,
		        rownumbers:true,
		        loadui: "disable",
		        multiselect: true,
				multiboxonly:true,
				shrinkToFit:false,
				autowidth:false,
		        onSelectRow: function(rowid) {
		       
		       	},
				 gridComplete:function(){
					 gzItemFormulaGridRowObj = {};
					 var rowNum = jQuery(this).getDataIDs().length;
					 var rowIds = jQuery(this).getDataIDs();
					 var id='';
				     for(var i=0;i<rowNum;i++){
				        id = rowIds[i];
				        gzItemFormulaGridRowObj[i+1] = id;
				     }
		           var dataTest = {"id":"gzItemFormulaFilter_gridtable"};
		      	   jQuery.publish("onLoadSelect",dataTest,null);
		       	} 

		    });
		    jQuery(gzItemFormulaFilterGrid).jqGrid('bindKeys');
		    var url = "makeGzItemTree?gzTypeId="+gzTypeId+"&itemCode="+gzItemCode;
		    url = encodeURI(url);
			$.get(url, {"_" : $.now()}, function(data) {
				var itemNodeData = data.itemNodes;
				var itemTree = $.fn.zTree.init($("#gzItemFormula_gzItemTree"),ztreesetting_gzItemForFormula, itemNodeData);
 				var nodes = itemTree.getNodes();
 				if(nodes){
 					for(index in nodes){
 						var node = nodes[index];
 						var name = node.name;
 						var type = node.actionUrl;
 						var code = node.subSysTem;
 						gzItemResultNodeObj[name] = {name:name,type:type,code:code};
 					}
 				}
// 				itemTree.expandNode(nodes[0], true, false, true);
			});
			
			var funcStr = gzFuncs;
			if(funcStr){
				var funcArr = funcStr.split(";");
				jQuery.each(funcArr, function (key, value) {
					 jQuery("#gzItemFormula_funcs").get(0).options.add(new Option(value,value));
		            });
			}
			
			jQuery("#gzItemFormula_funcs").bind('change',function(){
				var value = this.value;
				jQuery("#gzItemFormula_resultFormula").insertAtCousor(value);
			});
	});
	var ztreesetting_gzItemForFormula = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
			},
			callback : {
				beforeDrag:function(){return false},
				beforeClick:function(treeId, treeNode, clickFlag) {
					
				},
				onClick : function(event, treeId, treeNode, clickFlag){
					var nodeName = treeNode.name;
					nodeName = "[" + nodeName + "]";
					jQuery("#gzItemFormula_resultFormula").insertAtCousor(nodeName);
				}
			},
			data : {
				key : {
					name : "name"
				},
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "pId"
				}
			}
	};
	function gzItemFormulaOperBtnClick(obj){
		var value = jQuery(obj).text();
		switch(value){
			case "＋":
				value = "+";
				break;
			case "－":
				value = "-";
				break;
			case "×":
				value = "*";
				break;
			case "÷":
				value = "/";
				break;
			case "（":
				value = "(";
				break;
			case "）":
				value = ")";
				break;
		}
		jQuery("#gzItemFormula_resultFormula").insertAtCousor(value);
	}
	function gzItemFormulaAddRow(){
		var winTitle='<s:text name="gzItemCheckList.title"/>';
		var url = "checkGzItemCheckList?popup=true&navTabId=gzItemFormulaFilter_gridtable&gzTypeId="+gzTypeId+"&oper=gzItemFormula_conditionExp";
		url += "&filterItemCode=${gzItemFormula.gzItem.itemCode}";
		url = encodeURI(url);
		$.pdialog.open(url,'checkGzItemCheckList',winTitle, {mask:true,resizable:false,maxable:false,width : 300,height : 400});	 		
		stopPropagation();
	}
	function gzItemFormulaDelRow(){
		var sid = jQuery("#gzItemFormulaFilter_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null || sid.length ==0){
        	alertMsg.error("请选择记录！");
			return;
			}else {
				alertMsg.confirm("确认删除？", {
					okCall : function() {
				for(var i = sid.length;i > 0; i--){
					var rowId = sid[i-1];
					jQuery("#gzItemFormulaFilter_gridtable").delRowData(rowId);
					}
				}
				});
			}
	}
	
	function conditionExpChange(){
		expFlag = true;
		var value = jQuery("#gzItemFormula_conditionExp").val();
		var newValue = "";
		var parameters = new Array();
		var parameter = "";
		var errorMessage = "";
		if(value){
			 for (var i = 0; i < value.length; i++) {
	                var s = value.charAt(i);
	                switch (s) {
	                    case '(':
	                    case ')':
	                    case '+':
	                    case '*':
	                    	if(parameter){
	                    		parameters.push(parameter);
	                    	}
	                    	parameter = "";
	                    	parameters.push(s);
	                    	break;
	                    case '0':
	                    case '1':
	                    case '2':
	                    case '3':
	                    case '4':
	                    case '5':
	                    case '6':
	                    case '7':
	                    case '8':
	                    case '9':
	                    	parameter = parameter + s;
	                    	break;
	                    default :
	                    	errorMessage = "条件公式中存在非法字符"+s+" ";
	                        break;
	                }
	            }
			 if(parameter){
         		parameters.push(parameter);
         	}
		}
		var gridRowIds = {};
        if(parameters){
        	var maxRowNum = getJsonLength(gzItemFormulaGridRowObj);
			jQuery.each(parameters, function(i,val){
				if(!isNaN(val)){
			    	  val = +val;
			    	  if(val<1||val>maxRowNum){
			    		  errorMessage += "条件公式中对应数字"+val+"不存在相应记录。" 
			    	  }else{
			    		  var filterId = gzItemFormulaGridRowObj[val];
			    		  gridRowIds[filterId] = val;
			    	  }
				}
			  });   
		}
		if(errorMessage){
			expFlag = false;
			alertMsg.error(errorMessage);
			return;
		}
		try {
            var result = eval(value);
        } catch (e) {
       	 	expFlag = false;
       	 	alertMsg.error("条件公式错误！");
			return;
        }
        
		/*条件拼接Start*/
		 errorMessage = "";
		var conditionFormulaObj = {};
        if(gridRowIds){
        	for(var rowId in gridRowIds){
        		var content = "";
        		var cpDt = '0';
		    	var conditionFormulaContent = "";
        		var rowSn = gridRowIds[rowId];
        		var curInputObj = null;
        		var row = jQuery("#gzItemFormulaFilter_gridtable").jqGrid('getRowData',rowId);
        		var rowName = row['name'];
        		var rowCode = row['code'];
        		if(gzItemFormulaCheckObj){
        			for(var index in gzItemFormulaCheckObj){
        				var gzItemFormulaCheck = gzItemFormulaCheckObj[index];
        				var itemCodeCheck = gzItemFormulaCheck["itemCode"];
        				var itemNameCheck = gzItemFormulaCheck["itemName"];
        				var formulaNameCheck = gzItemFormulaCheck["formulaName"]; 
        				if(itemCodeCheck == rowCode && gzItemFormulaCheck){
        					var cpCheck = gzItemFormulaCheck["cps"][gzItemCode];
        					var rpCheck = gzItemFormulaCheck["rps"][gzItemCode];
        					var ems = "工资项:"+itemNameCheck+" 公式:"+formulaNameCheck+" ";
        					if(cpCheck){
        						errorMessage += ems + "条件中使用了该工资项。"
        					}
        					if(rpCheck){
        						errorMessage += ems + "结果中使用了该工资项。"
        					}
        				}
        			}
        		}
        		content = "[" + rowName + "]";
        		conditionFormulaContent = "[" + rowCode + "]";
			    var gzItemNodeObjTemp = gzItemNodeObj[rowCode];
			    var type = '1';
			    if(gzItemNodeObjTemp){
			    	type = gzItemNodeObjTemp.type;
			    }
			    switch(type){
    			case '1':
    			case '5':
    				cpDt = '1';//字符型
    				break;
    			case '2':
    				cpDt = '0';//数字型
    				break;
    			case '3':
    				cpDt = '3';//整型
    				break;
    			case '4':
    				cpDt = '2';//日期型
    				break;
    			default:
    				cpDt = '1';//字符型
					break;
    			}
			    conditionParameter = conditionParameter + rowCode +",";	
			    curInputObj = jQuery("#"+rowId).find(".operSelect");
			    var operSelect = curInputObj.val();
			    switch(operSelect){
	    			case '=':
	    				conditionFormulaContent = conditionFormulaContent + '==';
	    				content = content + '等于';
	    				break;
	    			case '>':
	    				conditionFormulaContent = conditionFormulaContent + '>';
	    				content = content + '大于';
	    				break;
	    			case '<':
	    				conditionFormulaContent = conditionFormulaContent + '<';
	    				content = content + '小于';
	    				break;
	    			case '<=':
	    				conditionFormulaContent = conditionFormulaContent + '<=';
	    				content = content + '小于等于';
	    				break;
	    			case '>=':
	    				conditionFormulaContent = conditionFormulaContent + '>=';
	    				content = content + '大于等于';
	    				break;
	    			case '<>':
	    				conditionFormulaContent = conditionFormulaContent + '!=';
	    				content = content + '不等于';
	    				break;
	    			case 'like':
	    				conditionFormulaContent = conditionFormulaContent + '.indexOf(';
	    				content = content + '包含';
	    				break;
    				case 'not like':
	    				conditionFormulaContent = conditionFormulaContent + '.indexOf(';
	    				content = content + '不包含';
	    				break;
	    			default:
	    				break;
	    		}   			
			    curInputObj = jQuery("#"+rowId).find(".searchValue");
			    var searchValue = curInputObj.val();
		    	if(!searchValue){
		    		errorMessage += "第"+rowSn+"行比较值不能为空。" 
		    	}
		    	content = content + "'" + searchValue + "'";
		    	if(operSelect == 'like'){
		    		var startStr = searchValue.substring(0,1);
		    		var endStr = searchValue.substring(searchValue.length-1,searchValue.length);
		    		var sv = searchValue;
		    		var operator = ">=0 ";
		    		if(endStr=='*'){
		    			operator = "=0 ";
		    			sv = sv.substring(0,searchValue.length-1);
		    		}
		    		conditionFormulaContent = conditionFormulaContent + " '" + sv+ "')"+operator;
		    	}else if(operSelect == 'not like'){
		    		var startStr = searchValue.substring(0,1);
		    		var endStr = searchValue.substring(searchValue.length-1,searchValue.length);
		    		var sv = searchValue;
		    		var operator = "<0 ";
		    		if(endStr=='*'){
		    			operator = "<>0 ";
		    			sv = sv.substring(0,searchValue.length-1);
		    		}
		    		conditionFormulaContent = conditionFormulaContent + " '" + sv+ "')"+operator;
		    	}else{
		    		conditionFormulaContent = conditionFormulaContent + " '" + searchValue+ "' ";
		    	}    	
		    	conditionFormulaObj[rowSn] = {code:rowCode,name:rowName,
		    			oper:operSelect,searchValue:searchValue,type:type,cpDt:cpDt,content:content
		    			,conditionFormulaContent:conditionFormulaContent};
			  }
        }
		/*条件拼接End*/
        if(errorMessage){
			expFlag = false;
			alertMsg.error(errorMessage);
			return;
		}
		/*条件参数*/
		var conditionParameter = "";
		var conditionParameterDT = "";
		if(conditionFormulaObj){
			for(var index in conditionFormulaObj){
				var code = conditionFormulaObj[index].code;
				var cpDt = conditionFormulaObj[index].cpDt;
				conditionParameter += code + ",";
				conditionParameterDT += cpDt + ",";
			}
			conditionParameter = conditionParameter.substring(0,conditionParameter.length-1);
			conditionParameterDT = conditionParameterDT.substring(0,conditionParameterDT.length-1);
		}
		jQuery("#gzItemFormula_conditionParameter").val(conditionParameter);
		jQuery("#gzItemFormula_conditionParameterDataType").val(conditionParameterDT);		
		
		newValue = "";
		var conditionFormula = "";
		if(parameters){
			jQuery.each(parameters, function(i,val){      
			      if(val == "("||val == ")"){
			    	  newValue += val;
			    	  conditionFormula += val;
			      }else if(val == "*"){
			    	  newValue += "并且";
			    	  conditionFormula += "&&";
			      }else if(val == "+"){
			    	  newValue += "或者";
			    	  conditionFormula += "||";
			      }else if(!isNaN(val)){
			    	  newValue += conditionFormulaObj[val].content;
			    	  conditionFormula += conditionFormulaObj[val].conditionFormulaContent;
			      }else{
			    	  errorMessage = "条件公式错误！" 
			      }
			  });   
		}
		if(errorMessage){
			expFlag = false;
			alertMsg.error(errorMessage);
			return;
		}
		jQuery("#gzItemFormula_conditionFormula").val(conditionFormula);
		newValue = "如果" + newValue;
		var finalValue =  jQuery("#gzItemFormula_finalExp").val();
		if(finalValue&&finalValue.indexOf(";那么")>=0){
			var valueArr = finalValue.split(";那么");
			if(valueArr.length > 0){
				finalValue = ";那么" + valueArr[1];
			}
		}
		finalValue = newValue + finalValue;
		jQuery("#gzItemFormula_finalExp").val(finalValue);
		jQuery("#gzItemFormula_finalExp").attr("title",jQuery("#gzItemFormula_finalExp").val());
	}
	function resultFormulaChange(){
		var value = jQuery("#gzItemFormula_resultFormula").val();
		value = ";那么" + value;
		var finalValue =  jQuery("#gzItemFormula_finalExp").val();
		if(finalValue&&finalValue.indexOf(";那么")>=0){
			var valueArr = finalValue.split(";那么");
			finalValue = valueArr[0];
		}
		finalValue = finalValue + value;
		jQuery("#gzItemFormula_finalExp").val(finalValue);
		jQuery("#gzItemFormula_finalExp").attr("title",jQuery("#gzItemFormula_finalExp").val());
	}
	function resultFormulaCheck(isSave){
		 conditionExpChange();
		 resultFormulaChange();
		 var expression = jQuery("#gzItemFormula_resultFormula").val();
		 var resultFormulaExp = expression;
		 if(!expFlag){
			 return;
		 }
		 if(!expression){
			 expFlag = false;
			 alertMsg.error("结果不能为空！");
			 return;
		 }
		 var parameters = getExpressionParameters(expression);
		 var i = 1;
		 var errorMessage = "";
		 var resultFormulaObj = {};
		 jQuery.each(parameters, function (key, value) {
			 var gzItemResultNodeObjTemp = gzItemResultNodeObj[value];
			 if(!gzItemResultNodeObjTemp){
				 errorMessage += "结果表达式工资项["+value+"]不存在。";
			 }else{
				 var code = gzItemResultNodeObjTemp.code;
				 var type = gzItemResultNodeObjTemp.type;
				 var name = gzItemResultNodeObjTemp.name;
				 if(gzItemFormulaCheckObj){
	        			for(var index in gzItemFormulaCheckObj){
	        				var gzItemFormulaCheck = gzItemFormulaCheckObj[index];
	        				var itemCodeCheck = gzItemFormulaCheck["itemCode"];
	        				var itemNameCheck = gzItemFormulaCheck["itemName"];
	        				var formulaNameCheck = gzItemFormulaCheck["formulaName"];
	        				if(itemCodeCheck == code && gzItemFormulaCheck){
	        					var cpCheck = gzItemFormulaCheck["cps"][gzItemCode];
	        					var rpCheck = gzItemFormulaCheck["rps"][gzItemCode];
	        					var ems = "工资项:"+itemNameCheck+" 公式:"+formulaNameCheck+" ";
	        					if(cpCheck){
	        						errorMessage += ems + "条件中使用了该工资项。"
	        					}
	        					if(rpCheck){
	        						errorMessage += ems + "结果中使用了该工资项。"
	        					}
	        				}
	        			}
	        		}
				 resultFormulaObj[code] = type;
				 resultFormulaExp = resultFormulaExp.replaceAll("\\["+value+"\\]","["+code+"]");
			 }
			 expression = expression.replaceAll('\\[' + value + '\\]', ' ' + i++ +' ');
		 });
		 if(expression.indexOf("%")){
			 resultFormulaExp = resultFormulaExp.replaceAll('%',"/100");
			 expression = expression.replaceAll('%',"/100");
		 }
		 if(errorMessage){
				expFlag = false;
				alertMsg.error(errorMessage);
				return;
		 }
		 /*结果参数*/
		 var resultParameter = "";
		 var resultParameterDT = "";
		 if(resultFormulaObj){
			 for(var code in resultFormulaObj){
				 var type = resultFormulaObj[code];
				 resultParameter += code + ",";
				 resultParameterDT += type + ",";
			 }
			 resultParameter = resultParameter.substring(0,resultParameter.length-1);
			 resultParameterDT = resultParameterDT.substring(0,resultParameterDT.length-1);
		 }
		 jQuery("#gzItemFormula_resultParameter").val(resultParameter);
		 jQuery("#gzItemFormula_resultParameterDataType").val(resultParameterDT);
		 jQuery("#gzItemFormula_resultFormulaExp").val(resultFormulaExp);
		 try {
             var result = eval(expression);
         } catch (e) {
        	 expFlag = false;
        	 alertMsg.error("结果表达式错误！");
			 return;
         }
         if(!isSave){
        	 alertMsg.correct("验证成功。");
         }
	}
	//字符串转换为数组
	function getExpressionParameters(expression) {
        var parameters = new Array();
        var parameter = null;
        for (var i = 0; i < expression.length; i++) {
            var s = expression.charAt(i);
            switch (s) {
                case '[':
                    parameter = '';
                    break;
                case ']':
                    parameters.push(parameter);
                    parameter = null;
                    break;
                default :
                    if (parameter != null) {
                        parameter += s;
                    }
                    break;
            }
        }
        return parameters;
    }
	/*保存页面*/
	function gzItemFormulaFormSave(){
		expFlag = true;
		resultFormulaCheck("isSave");
		if(!expFlag){
			return;
		}
		var otherExpedChecked = jQuery("#gzItemFormula_otherExped").attr("checked");
		var otherExpType = $("input[name='gzItemRadio']:checked").val();
		var otherExpValue = jQuery("#gzItemFormula_otherExpValue").val();
		if(otherExpedChecked == "checked"){
			if(!otherExpType){
				 alertMsg.error("附加类型不能为空！");
				 return;
			}
			if(!otherExpValue){
				 alertMsg.error("附加值不能为空！");
				 return;
			}
		}
		jQuery("#gzItemFormula_otherExpType").val(otherExpType);
		var gridObj = jQuery("#gzItemFormulaFilter_gridtable").jqGrid("getRowData");
		var jsonStr = "";
		var curInputObj = null;
		var rowId = null;
		var row = null;
	    jQuery(gridObj).each(function(key,value){
	    	rowId = value.filterId;
	    	jsonStr = jsonStr+"{";
	    	if(rowId.indexOf("formulaFilter") >=0){
	    		jsonStr = jsonStr+'"filterId":"",';
	    	}else{
	    		jsonStr = jsonStr+'"filterId":"'+rowId+'",';
	    	}
	    	row = jQuery("#gzItemFormulaFilter_gridtable").jqGrid('getRowData',rowId);
	    	jsonStr = jsonStr+'"name":"'+row['name']+'",';
	    	jsonStr = jsonStr+'"code":"'+row['code']+'",';
	    	curInputObj = jQuery("#"+rowId).find(".operSelect");
	    	jsonStr = jsonStr+'"oper":"'+curInputObj.val()+'",';
	    	curInputObj = jQuery("#"+rowId).find(".searchValue");
	    	jsonStr = jsonStr+'"searchValue":"'+curInputObj.val()+'"},';
	    });
	    if(jsonStr.length>1){
	    	 jsonStr = jsonStr.substring(0,jsonStr.length-1);
	    }
		jsonStr = "{"+'"'+"edit"+'"'+":[" +jsonStr+"]}";
		jQuery("#gzItemFormula_jsonStr").val(jsonStr);
		jQuery("#gzItemFormulaForm").attr("action","saveGzItemFormula?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
		jQuery("#gzItemFormulaForm").submit();
	}
	/*公式名称检测*/
	function checkGzItemFormulaName(obj){
		var fieldValue = obj.value;
		if(!fieldValue){
			return;
		}
		var orignalName = "${gzItemFormula.name}";
		if(orignalName === fieldValue){
			return;
		}
		var itemId = "${gzItemFormula.gzItem.itemId}";
		jQuery.ajax({
		    url: "gzItemFormulaNameCheck",
		    type: 'post',
		    data:{itemId:itemId,name:fieldValue},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		        if(data!=null){
		        	 alertMsg.error(data.message);
				     obj.value="";
		        }
		    }
		});
	}
	/*函数说明*/
	function gzItemFormulaFuncsImport(){
		var winTitle='<s:text name="gzItemFuncsList.title"/>';
	    var url = "gzItemFuncsList?navTabId=gzItemFormula_resultFormula";
	    url = encodeURI(url);
	 	$.pdialog.open(url,'gzItemFuncsList',winTitle, {mask:true,resizable:false,maxable:false,width : 300,height : 400});	 		
	 	stopPropagation();
	}
	
	function showPosion(){
		var cp = jQuery("#gzItemFormula_resultFormula").getCursorPosition();
		var cps = jQuery("#gzItemFormula_resultFormula").getSelectionStart();
		var cpe = jQuery("#gzItemFormula_resultFormula").getSelectionEnd();
		alert(cp+":s:"+cps+":e:"+cpe);
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="gzItemFormulaForm" method="post"	action="saveGzItemFormula?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<input id="gzItemFormulaCheckStr" type="hidden" value='<s:property value="gzItemFormulaCheckStr" escapeHtml="false"/>'>
					<s:hidden key="gzItemFormula.formulaId"/>
					<s:hidden key="gzItemFormula.gzItem.itemId"/>
					<s:hidden key="gzItemFormula.inUsed"/>
					<s:hidden key="gzItemFormula.conditionFormula" id="gzItemFormula_conditionFormula"/>
					<s:hidden key="gzItemFormula.conditionParameter" id="gzItemFormula_conditionParameter"/>
					<s:hidden key="gzItemFormula.resultFormulaExp" id="gzItemFormula_resultFormulaExp"/>
					<s:hidden key="gzItemFormula.resultParameter" id="gzItemFormula_resultParameter"/>
					<s:hidden key="gzItemFormula.otherExpType" id="gzItemFormula_otherExpType"/>
					<s:hidden key="gzItemFormulaJsonStr" id="gzItemFormula_jsonStr"/>
					<s:hidden key="gzItemFormula.resultParameterDataType" id="gzItemFormula_resultParameterDataType"></s:hidden>
					<s:hidden key="gzItemFormula.conditionParameterDataType" id="gzItemFormula_conditionParameterDataType"></s:hidden>
				</div>
				<div style="position:absolute;left:10px;top:5px;width:480px">
					<span>
						<s:text name="gzItemFormula.name"></s:text>:
						<s:if test="%{!entityIsNew}">
							<input name="gzItemFormula.name" type="text" style="float:none" maxlength="50" class="required" value="${gzItemFormula.name}" oldValue="${gzItemFormula.name}" notrepeat='公式名称已存在' validatorParam="from GzItemFormula gzItemFormula where gzItemFormula.name=%value% and gzItemFormula.gzItem.itemId='${gzItemFormula.gzItem.itemId}'">
						</s:if>
						<s:else>
							<input name="gzItemFormula.name" type="text" style="float:none" maxlength="50" class="required" value="${gzItemFormula.name}"  notrepeat='公式名称已存在' validatorParam="from GzItemFormula gzItemFormula where gzItemFormula.name=%value% and gzItemFormula.gzItem.itemId='${gzItemFormula.gzItem.itemId}'">
						</s:else>
					</span>
					<span style="float:right">
						<s:text name="gzItemFormula.sn" ></s:text>:
						<input name="gzItemFormula.sn" type="text" style="float:none" value="${gzItemFormula.sn}"   class="required digits">
					</span>
				</div>
				<fieldset style="border:1px lightskyblue;border-style:solid;position:absolute;left:10px;top:30px;width:480px;height:175px;">
					<legend>如果满足下列条件:</legend>
					<div style="float:left">
						<div class="buttonActive" style="margin-left: 5px;">
							<div class="buttonContent">
								<button type="Button" onclick="gzItemFormulaAddRow()">添加行</button>
							</div>
						</div>
						<div class="button" style="margin-left: 5px">
							<div class="buttonContent">
								<button type="Button" onclick="gzItemFormulaDelRow()">删除行</button>
							</div>
						</div>
<!-- 						<div class="button" style="margin-left: 5px"> -->
<!-- 							<div class="buttonContent"> -->
<!-- 								<button type="Button" onclick="showPosion()">删除行</button> -->
<!-- 							</div> -->
<!-- 						</div> -->
					</div>
					<span style="float:right;margin-right: 5px;">
						<s:text name="gzItemFormula.conditionExp"></s:text>:
						<input id="gzItemFormula_conditionExp" style="float:none" type="text" name="gzItemFormula.conditionExp" maxlength="50" value="${gzItemFormula.conditionExp}" onblur="conditionExpChange()">
						</span>
					<div style="clear: both"></div>
					<div id="gzItemFormulaFilter_gridtable_div" style="width:475px;heigth:50px;margin: auto;overflow: auto;">
						<div id="load_gzItemFormulaFilter_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 						<table id="gzItemFormulaFilter_gridtable"></table>
					</div>
				</fieldset>
				<fieldset style="border:1px lightskyblue;border-style:solid;position:absolute;left:510px;top:5px;width:150px;height:200px;overflow:auto;">
					<legend>注意：</legend>
						<ol style="margin: 3px">
						  <li style="margin: 3px;">1.条件公式描述条件列表中各个条件的逻辑关系</li>
						  <li style="margin: 3px;">2.条件公式中只能包含<font color="#FF0000">数字</font>,<font color="#FF0000">+</font>,<font color="#FF0000">*</font>和<font color="#FF0000">()</font></li>
						  <li style="margin: 3px;">3.条件公式中<font color="#FF0000">数字</font>表示列表中<font color="#FF0000">对应行</font>的条件</li>
						  <li style="margin: 3px;">4.条件公式中<font color="#FF0000">+</font>表示前后两个条件为<font color="#FF0000">或者</font>关系</li>
						  <li style="margin: 3px;">5.条件公式中<font color="#FF0000">*</font>表示前后两个条件为<font color="#FF0000">并且</font>关系</li>
						  <li style="margin: 3px;">6.条件公式中<font color="#FF0000">()</font>表示括号内的条件<font color="#FF0000">优先</font>判断</li>
						  <li style="margin: 3px;">7.如果条件公式为<font color="#FF0000">空</font>则表示条件为<font color="#FF0000">真</font></li>
						</ol>
				</fieldset>
				<fieldset style="border:1px lightskyblue;border-style:solid;position:absolute;left:10px;top:220px;width:480px;190px;">
					<legend>则结果等于:</legend>
					<div style="float:left">
						<s:textarea id="gzItemFormula_resultFormula" name="gzItemFormula.resultFormula" style="height:90px;width:390px" onblur="resultFormulaChange()" cssClass="required"></s:textarea>
						<script>
						jQuery("#gzItemFormula_resultFormula").focus(function(){
							var cp = jQuery("#gzItemFormula_resultFormula").getCursorPosition();
							jQuery("#gzItemFormula_resultFormula").data("cursorPosition",cp);
						});
						jQuery("#gzItemFormula_resultFormula").click(function(){
							var cp = jQuery("#gzItemFormula_resultFormula").getCursorPosition();
							jQuery("#gzItemFormula_resultFormula").data("cursorPosition",cp);
						});
						</script>
					</div >
					<div style="width:70px;float:right;margin-left:10px">
						<div style="clear: both;"></div>
					<div style="float:right;width:65px;">
						<div style="float:left;width:65px;">
						<div class="button">
							<div class="buttonContent" style="width:10px;">
								<button type="Button" onclick="gzItemFormulaOperBtnClick(this)">＋</button>
							</div>
						</div>
						<div class="button">
							<div class="buttonContent" style="width:10px;">
								<button type="Button"  onclick="gzItemFormulaOperBtnClick(this)">－</button>
							</div>
						</div>
						<div class="button">
							<div class="buttonContent" style="width:10px;">
								<button type="Button"  onclick="gzItemFormulaOperBtnClick(this)">×</button>
							</div>
						</div>
					</div>
					<div style="float:left;width:65px;">
						<div class="button">
							<div class="buttonContent" style="width:10px;">
								<button type="Button"  onclick="gzItemFormulaOperBtnClick(this)">÷</button>
							</div>
						</div>
						<div class="button">
							<div class="buttonContent" style="width:10px;">
								<button type="Button"  onclick="gzItemFormulaOperBtnClick(this)">&lt;</button>
							</div>
						</div>
						<div class="button">
							<div class="buttonContent" style="width:10px;">
								<button type="Button"  onclick="gzItemFormulaOperBtnClick(this)">&gt;</button>
							</div>
						</div>
					</div>
					<div style="float:left;width:65px;">
						<div class="button">
							<div class="buttonContent" style="width:10px;">
								<button type="Button"  onclick="gzItemFormulaOperBtnClick(this)">（</button>
							</div>
						</div>
						<div class="button">
							<div class="buttonContent" style="width:10px;">
								<button type="Button"  onclick="gzItemFormulaOperBtnClick(this)">）</button>
							</div>
						</div>
						<div class="button">
							<div class="buttonContent" style="width:10px;">
								<button type="Button"  onclick="gzItemFormulaOperBtnClick(this)">＝</button>
							</div>
						</div>
					</div>
					<div style="float:left;width:65px;">
						<div class="button">
							<div class="buttonContent">
								<button type="Button"  onclick="gzItemFormulaFuncsImport()">函数说明</button>
							</div>
						</div>
					</div>
				</div>
				</div>
				<div style="clear: both;"></div>
				<div style="margin-top: 5px">
					<div style="float: left">
						<s:checkbox key="gzItemFormula.otherExped" id="gzItemFormula_otherExped" required="false" theme="simple" />
						在本工资项基础上:
					</div>
					<div style="float:right;margin-right: 5px;">
						函数:
						<span>
							<select id="gzItemFormula_funcs" style="width: 100px;float: none;"></select>
						</span>
					</div>
				</div>
				<div style="clear: both;"></div>	
				<div style="margin-top: 5px;">
					<input type="radio"  name="gzItemRadio" value="1">增加
					<input type="radio"  name="gzItemRadio" value="2">减少
					<input type="radio"  name="gzItemRadio" value="3">乘系数
				</div>
				<div style="margin-top: 5px;">
					<input type="text" id="gzItemFormula_otherExpValue" name="gzItemFormula.otherExpValue" class="number" value="${gzItemFormula.otherExpValue}">
				</div>
				</fieldset>
				<fieldset style="border:1px lightskyblue;border-style:solid;position:absolute;left:510px;top:220px;width:150px;height:190px;">
					<legend>工资项：</legend>
					<div  style="float:left;margin-left:6px;margin-top:6px">
						检索
						<input type="text" style="float:none;width: 90px;" onKeyUp="searchTree('gzItemFormula_gzItemTree',this)"/>
					</div>
					<div style="float:left;margin-top:6px;overflow:auto;height:142px;width:145px;">
						<div id="gzItemFormula_gzItemTree"  class="ztree"></div>
					</div>
				</fieldset>
				<fieldset style="border:1px lightskyblue;border-style:solid;position:absolute;left:10px;top:420px;width: 650px;">
					<legend>最终表达式:</legend>
					<input id="gzItemFormula_finalExp" name="gzItemFormula.expContent" type="text" style="width: 590px;float:left" readonly="readonly" value="${gzItemFormula.expContent}">
						<div class="buttonActive" style="margin-left: 10px">
							<div class="buttonContent">
								<button type="Button"  onclick="resultFormulaCheck()">检查</button>
							</div>
						</div>
				</fieldset>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" onclick="gzItemFormulaFormSave()"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.close('dialogGzItemFormula');"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





