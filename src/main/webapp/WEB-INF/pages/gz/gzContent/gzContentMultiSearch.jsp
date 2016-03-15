<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	var expFlag = true;
	var gridObj;
	var gzItemHtmlObj = {};
	jQuery(document).ready(function() {
		 var gzContentMultiSearchFilterGridIdString="#gzContentMultiSearchFilter_gridtable";
		 var gzContentMultiSearchFilterGrid = jQuery(gzContentMultiSearchFilterGridIdString);
		 gzContentMultiSearchFilterGrid.jqGrid({
		    	url : "",
		    	editurl:"",
				datatype : "json",
				mtype : "GET",
		        colModel:[
		{name:'filterId',index:'filterId',align:'center',label : '<s:text name="gzContentMultiSearchFilter.filterId" />',hidden:true,key:true},	
		{name:'name',index:'name',width:'120px',align:'left',label : '<s:text name="gzContentMultiSearchFilter.name" />',hidden:false,sortable:false},
		{name:'code',index:'code',align:'center',label : '<s:text name="gzContentMultiSearchFilter.code" />',hidden:true},	
		{name:'oper',index:'oper',width:'80px',align:'center',label : '<s:text name="gzContentMultiSearchFilter.oper" />',hidden:false,sortable:false},	
		{name:'searchValue',width:'200px',index:'searchValue',align:'left',label : '<s:text name="gzContentMultiSearchFilter.searchValue" />',hidden:false,sortable:false}				
		        ],
		        jsonReader : {
					root : "gzContentMultiSearchFilters", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				// (4)
				},
		        sortname: 'filterId',
		        viewrecords: true,
		        sortorder: 'asc',
		        height:262,
		        gridview:true,
		        rownumbers:true,
		        loadui: "disable",
		        multiselect: false,
				multiboxonly:true,
				shrinkToFit:false,
				autowidth:false,
		        onSelectRow: function(rowid) {
		       
		       	},
				 gridComplete:function(){
					 gridObj = this;
					 var rowNum = jQuery(gridObj).getDataIDs().length;
					 if(rowNum == 10){
						 /*初始化数据*/
						    jQuery.ajax({
					 		       url: 'getGzItemNodesForFormula',
					 		       data: {gzTypeId:"${gzTypeId}"},
					 		       type: 'post',
					 		       dataType: 'json',
					 		       async:false,
					 		       error: function(data){
					 		       },
					 		       success: function(data){
					 		    	  var gzItemNodes = data.gzItemNodes;
					 		    	  var gzItemSelectHtml = '<select style="float:clear" name="searchName" onchange="gzContentMSearchItemChange(this)">';
					 		    	  gzItemSelectHtml+='<option value =""></option>';
					 		    	  if(gzItemNodes){
					 		    		  jQuery.each(gzItemNodes,function(index,gzItemNode){
					 		    			  var code = gzItemNode["id"];
					 		    			  var name = gzItemNode["name"];
					 		    			  var type = gzItemNode["type"]+"";
					 		    			  var values = gzItemNode["values"];
					 		    			  gzItemSelectHtml+='<option value ="'+code+'">'+name+'</option>';
					 		    			  var searchValueHtml = '<input name="searchValue" type="text">';
					 		    			  switch(type){
					 		    			  	case '1':
					 		    			  		searchValueHtml = '<input name="searchValue" type="text">';
					 		    			  		break;
					 		    			  	case '2':
					 		    			  		searchValueHtml = '<input name="searchValue" type="text">';
					 		    			  		break;
					 		    			  	case '3':
					 		    			  		searchValueHtml = '<input name="searchValue" type="text">';
					 		    			  		break;
					 		    			  	case '4':
					 		    			  		searchValueHtml = '<input name="searchValue" onclick="WdatePicker({skin:\'ext\',dateFmt:\'yyyy-MM-dd\'})" type="text">';
					 		    			  		break;
					 		    			  	case '5':
					 		    			  		searchValueHtml = '<select name="searchValue" style="float:clear">';
					 		    			  		if(values){
					 			    					 jQuery.each(values, function(i,val){ 
					 			    						searchValueHtml += ' <option value ='+val+'>'+val+'</option>';
					 			    					  });   
					 			    				}
					 		    			  		searchValueHtml += '</select>';
					 		    			  		break;
					 		    			  }
					 		    			  gzItemHtmlObj[code] = {code:code,name:name,type:type,searchValueHtml:searchValueHtml};
					 		    		  });
					 		    	  }
					 		    	 gzItemSelectHtml +="</select>";
					 		    	 var operSelectHtml = '<select style="float:clear" name="searchOper">';
					 		    	 operSelectHtml += '<option value ="=">=</option>';
					 		    	 operSelectHtml += '<option value =">">></option>';
					 		    	 operSelectHtml += '<option value =">=">>=</option>';
					 		    	 operSelectHtml += '<option value ="<"><</option>';
					 		    	 operSelectHtml += '<option value ="<="><=</option>';
					 		    	 operSelectHtml += '<option value ="<>"><></option>';
					 		    	 operSelectHtml += '<option value ="like">like</option>';
					 		    	operSelectHtml += '<option value ="not like">not like</option>';
					 		    	 operSelectHtml += '</select>';
						 		     var rowIds = jQuery(gridObj).getDataIDs();
								     var ret = jQuery(gridObj).jqGrid('getRowData');
								     var id='';
								     for(var i=0;i<rowNum;i++){
								        id = rowIds[i];
								        var name = ret[i]["name"];
								        var oper = ret[i]["oper"];
								        var searchValue = ret[i]["searchValue"];
								    	setCellText(gridObj,id,'name',gzItemSelectHtml);
									   	setCellText(gridObj,id,'oper',operSelectHtml);
								        if(name&&oper&&searchValue){
								        	jQuery("#"+id).find("[name='searchName']").val(name);
								        	jQuery("#"+id).find("[name='searchOper']").val(oper);
								        	var searchValueHtml = gzItemHtmlObj[name].searchValueHtml;
											setCellText(gridObj,id,'searchValue',searchValueHtml);
											jQuery("#"+id).find("[name='searchValue']").val(searchValue);
								        }
								     }
					 		       }
					 		   });
					 }
		           var dataTest = {"id":"gzContentMultiSearchFilter_gridtable"};
		      	   jQuery.publish("onLoadSelect",dataTest,null);
		       	} 

		    });
		    jQuery(gzContentMultiSearchFilterGrid).jqGrid('bindKeys');
		    var conditionExp = jQuery("#gzContent_multiSearch_conditionExp").val(conditionExp);
		    var searchFilters = jQuery("#gzContent_multiSearch_filters").val(searchFilters);
		    var multiSearch = jQuery("#gzContent_multiSearch").val();
		    if(conditionExp){
		    	 jQuery("#gzContentMultiSearch_conditionExp").val(conditionExp);
		    }else{
		    	 //jQuery("#gzContentMultiSearch_conditionExp").val("1*2*3*4*5*6*7*8*9*10");
		    }
		    jQuery("#gzContentMultiSearch_finalExp").val(multiSearch);
		    var gridIndex = 1;
		    if(searchFilters){
		    	searchFilters = JSON.parse(searchFilters); 
		    	for(var index in searchFilters){
		    		var code = searchFilters[index].code;
		    		var oper = searchFilters[index].oper;
		    		var searchValue = searchFilters[index].searchValue;
		    		var filterId = "gzContentMSearch_filter"+gridIndex;
		    		jQuery("#gzContentMultiSearchFilter_gridtable").addRowData(filterId, {
			 			 "filterId":filterId,
			 			 "name":code,
			 			 "code":"",
			 			 "oper":oper,
			 			 "searchValue":searchValue
			 			 }, "last");  
		    		gridIndex ++;
		    	}
		    }
		    /*初始化数据*/
		    for(var index = gridIndex;index<11;index++){
		    	var filterId = "gzContentMSearch_filter"+index;
		 		 jQuery("#gzContentMultiSearchFilter_gridtable").addRowData(filterId, {
		 			 "filterId":filterId,
		 			 "name":"",
		 			 "code":"",
		 			 "oper":"",
		 			 "searchValue":""
		 			 }, "last");  
	    	 }
		   
	});
	function gzContentMSearchItemChange(obj){
		var value = obj.value;
		var tr = jQuery(obj).parent().parent();
		var id = tr.attr("id");
		if(value){
			var searchValueHtml = gzItemHtmlObj[value].searchValueHtml;
			setCellText(gridObj,id,'searchValue',searchValueHtml);
		}else{
			setCellText(gridObj,id,'searchValue',"");
		}
	}
	function conditionExpChange(isSave){
		var conditionExp = jQuery("#gzContentMultiSearch_conditionExp").val();
		if(!conditionExp){
			var index = 0;
			jQuery("#gzContentMultiSearchFilter_gridtable>tbody>tr").each(function(){
  				var rowId=this.id;
  				var row = jQuery("#gzContentMultiSearchFilter_gridtable").jqGrid('getRowData',rowId);
				curInputObj = jQuery("#"+rowId).find("[name='searchName']");
		    	var rowName = curInputObj.find("option:selected").text();
		    	if(rowName){
		    		if(!conditionExp){
		    			conditionExp = index;
		    		}else{
		    			conditionExp += "*"+index;
		    		}
		    	}
		    	index ++;
			});
			if(conditionExp){
				jQuery("#gzContentMultiSearch_conditionExp").val(conditionExp);
			}
		}
		expFlag = true;
		var value = jQuery("#gzContentMultiSearch_conditionExp").val();
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
	                    	errorMessage = "条件公式中存在+,*,小括号,数字之外的非法字符。"
	                        break;
	                }
	            }
			 if(parameter){
         		parameters.push(parameter);
         	}
		}
		 var gridRowIds = {};
	        if(parameters){
				jQuery.each(parameters, function(i,val){
					if(!isNaN(val)){
				    	  val = +val;
				    	  if(val<1||val>10){
				    		  errorMessage = "条件公式中对应数字不存在相应记录"+val+"。" 
				    	  }else{
				    		  var filterId = "gzContentMSearch_filter"+val;
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
		    	var row = jQuery("#gzContentMultiSearchFilter_gridtable").jqGrid('getRowData',rowId);
				curInputObj = jQuery("#"+rowId).find("[name='searchName']");
		    	var rowName = curInputObj.find("option:selected").text(); 
		    	var rowCode = curInputObj.val();
		    	var type = '1';
		    	if(!rowCode){
		    		errorMessage += "第"+rowSn+"行项目名称比较值不能为空。" 
		    		break;
		    	}else{
		    		type = gzItemHtmlObj[rowCode].type;
		    	}
		    	content = "[" + rowName + "]";
		    	conditionFormulaContent = " " + rowCode + " ";
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
// 		    	conditionParameter = conditionParameter + rowCode +",";
		    	curInputObj = jQuery("#"+rowId).find("[name='searchOper']");
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
		    			conditionFormulaContent = 'indexOf(' +conditionFormulaContent + ',';
		    			content = content + '包含';
		    			break;
	    			case 'not like':
		    			conditionFormulaContent = 'indexOf(' +conditionFormulaContent + ',';
		    			content = content + '不包含';
		    			break;
		    		default:
		    			break;
		    	}
		    	curInputObj = jQuery("#"+rowId).find("[name='searchValue']");
		    	var searchValue = curInputObj.val();
		    	if(!searchValue){
		    		errorMessage += "第"+rowSn+"行比较值不能为空。" 
		    		break;
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
		    		if(type == '2'|| type == '3'){
		    			conditionFormulaContent = conditionFormulaContent + " " + sv+ ")"+operator;
		    		}else{
		    			conditionFormulaContent = conditionFormulaContent + " '" + sv+ "')"+operator;
		    		}
		    	}else if(operSelect == 'not like'){
		    		var startStr = searchValue.substring(0,1);
		    		var endStr = searchValue.substring(searchValue.length-1,searchValue.length);
		    		var sv = searchValue;
		    		var operator = "<0 ";
		    		if(endStr=='*'){
		    			operator = "<>0 ";
		    			sv = sv.substring(0,searchValue.length-1);
		    		}
		    		if(type == '2'|| type == '3'){
		    			conditionFormulaContent = conditionFormulaContent + " " + sv+ ")"+operator;
		    		}else{
		    			conditionFormulaContent = conditionFormulaContent + " '" + sv+ "')"+operator;
		    		}
		    	}else{
		    		if(type == '2'|| type == '3'){
		    			conditionFormulaContent = conditionFormulaContent + " " + searchValue+ " ";
		    		}else{
		    			conditionFormulaContent = conditionFormulaContent + " '" + searchValue + "' ";
		    		}
		    	}
		    	conditionFormulaObj[rowSn] = {code:rowCode,name:rowName,
		    			oper:operSelect,searchValue:searchValue,type:type,cpDt:cpDt,content:content
		    			,conditionFormulaContent:conditionFormulaContent};
        	}
        }
        if(errorMessage){
			expFlag = false;
			alertMsg.error(errorMessage);
			return;
		}
		/*条件拼接End*/
		/*条件参数*/
// 		var conditionParameter = "";
// 		var conditionParameterDT = "";
// 		if(conditionFormulaObj){
// 			for(var index in conditionFormulaObj){
// 				var code = conditionFormulaObj[index].code;
// 				var cpDt = conditionFormulaObj[index].cpDt;
// 				conditionParameter += code + ",";
// 				conditionParameterDT += cpDt + ",";
// 			}
// 			conditionParameter = conditionParameter.substring(0,conditionParameter.length-1);
// 			conditionParameterDT = conditionParameterDT.substring(0,conditionParameterDT.length-1);
// 		}
//		jQuery("#gzContentMultiSearch_conditionParameter").val(conditionParameter);
//		jQuery("#gzContentMultiSearch_conditionParameterDataType").val(conditionParameterDT);		
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
		if(!isSave){
       	 alertMsg.correct("验证成功。");
        }
		jQuery("#gzContentMultiSearch_filters").val(JSON.stringify(conditionFormulaObj));
		jQuery("#gzContentMultiSearch_conditionFormula").val(conditionFormula);
		jQuery("#gzContentMultiSearch_finalExp").val(newValue);
		jQuery("#gzContentMultiSearch_finalExp").attr("title",newValue);
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
	 function gzContentMultiSearchFormSave(){
		conditionExpChange("isSave");
		if(!expFlag){
			return;
		}
		var searchFilters = jQuery("#gzContentMultiSearch_filters").val();
		var conditionFormula = jQuery("#gzContentMultiSearch_conditionFormula").val();
		var finalExp = jQuery("#gzContentMultiSearch_finalExp").val();
		var conditionExp = jQuery("#gzContentMultiSearch_conditionExp").val();
		jQuery("#gzContent_multiSearch_conditionExp").val(conditionExp);
		jQuery("#gzContent_multiSearch_filters").val(searchFilters);
		jQuery("#gzContent_multiSearch_content").val(conditionFormula);
		jQuery("#gzContent_multiSearch").val(finalExp);
		jQuery("#gzContent_multiSearch").attr("title",finalExp);
		$.pdialog.closeCurrent();
		gzContentSearchFormReLoad();
	} 
	function gzContentMultiSearchFormClear(){
		alertMsg.confirm("重置后将清空所有内容，确认重置?", {
			okCall : function() {
				jQuery("#gzContentMultiSearch_finalExp").val("");
				jQuery("#gzContentMultiSearch_conditionExp").val("");
				jQuery("#gzContentMultiSearchFilter_gridtable>tbody>tr").each(function(){
						var rowId=this.id;
						var row = jQuery("#gzContentMultiSearchFilter_gridtable").jqGrid('getRowData',rowId);
						curInputObj = jQuery("#"+rowId).find("[name='searchName']");
						curInputObj.val("");
			    		var tr = curInputObj.parent().parent();
			    		var trid = tr.attr("id");
			    		setCellText(gridObj,trid,'searchValue',"");
			    		curInputObj = jQuery("#"+rowId).find("[name='searchOper']");
						curInputObj.val("");
				});
			}
		});
	}
	function gzContentMultiSearchConditionExpFocus(obj){
		if(!obj.value){
			conditionExpChange("isSave");
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="gzContentMultiSearchForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="gzContentMultiSearch.conditionFormula" id="gzContentMultiSearch_conditionFormula"/>
<%-- 					<s:hidden key="gzContentMultiSearch.conditionParameter" id="gzContentMultiSearch_conditionParameter"/> --%>
<%-- 					<s:hidden key="gzContentMultiSearch.conditionParameterDataType" id="gzContentMultiSearch_conditionParameterDataType"/> --%>
					<s:hidden id="gzContentMultiSearch_filters"/>
				</div>
				<fieldset style="border:1px lightskyblue;border-style:solid;position:absolute;left:10px;top:5px;width:480px;height:300px;">
					<legend>如果满足下列条件:</legend>
					<div>
						<s:text name="gzContentMultiSearch.conditionExp"></s:text>:
						<input id="gzContentMultiSearch_conditionExp" style="float:none;width:400px;" type="text"  maxlength="50" onfocus="gzContentMultiSearchConditionExpFocus(this)">
					</div>
					<div id="gzContentMultiSearchFilter_gridtable_div" style="width:475px;heigth:50px;margin: auto;overflow: auto;margin-top: 2px;">
						<div id="load_gzContentMultiSearchFilter_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 						<table id="gzContentMultiSearchFilter_gridtable"></table>
					</div>
				</fieldset>
				<fieldset style="border:1px lightskyblue;border-style:solid;position:absolute;left:510px;top:5px;width:150px;height:300px;overflow:auto;">
					<legend>注意：</legend>
						<ol style="margin: 3px">
						  <li style="margin: 3px;">1.条件公式描述条件列表中各个条件的逻辑关系</li>
						  <li style="margin: 3px;">2.条件公式中只能包含<font color="#FF0000">数字</font>,<font color="#FF0000">+</font>,<font color="#FF0000">*</font>和<font color="#FF0000">()</font></li>
						  <li style="margin: 3px;">3.条件公式中<font color="#FF0000">数字</font>表示列表中<font color="#FF0000">对应行</font>的条件</li>
						  <li style="margin: 3px;">4.条件公式中<font color="#FF0000">+</font>表示前后两个条件为<font color="#FF0000">或者</font>关系</li>
						  <li style="margin: 3px;">5.条件公式中<font color="#FF0000">*</font>表示前后两个条件为<font color="#FF0000">并且</font>关系</li>
						  <li style="margin: 3px;">6.条件公式中<font color="#FF0000">()</font>表示括号内的条件<font color="#FF0000">优先</font>判断</li>
						  <li style="margin: 3px;">7.如果条件公式为<font color="#FF0000">空</font>则表示高级查询条件为<font color="#FF0000">空</font></li>
						</ol>
				</fieldset>
				<fieldset style="border:1px lightskyblue;border-style:solid;position:absolute;left:10px;top:320px">
					<legend>最终表达式:</legend>
					<s:textarea id="gzContentMultiSearch_finalExp" style="width: 600px;float:left;height:110px;" readonly="true"></s:textarea>
					<div class="buttonActive" style="margin-left: 10px;margin-top: 90px;">
						<div class="buttonContent">
								<button type="Button"  onclick="conditionExpChange()">检查</button>
							</div>
					</div>
				</fieldset>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" onclick="gzContentMultiSearchFormClear()">重置</button>
							</div>
						</div>
					</li>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" onclick="gzContentMultiSearchFormSave()">确定</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





