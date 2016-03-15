<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
var treeSqlArr = new Array();
var treeIdArr = new Array();
	jQuery(document).ready(function() {
		jQuery('#statisticsSingleFieldFilter_savelink').click(function() {
			var code = null;
			var dataType = null;
			var userTag = null;
			var parameter = null;
			var parameterName = null;
			var curInputObj = null;
			var jsonStr="";
			var obj = jQuery("#statisticsSingleFieldFilter_gridtable").jqGrid("getRowData");
// 			var obj = jQuery("#statisticsTarget_gridtable>tbody>tr");
			    jQuery(obj).each(function(){
			    	jsonStr = jsonStr+"{";
			    	if(this.id.indexOf("target") >=0){
			    		jsonStr = jsonStr+'"id":"",';
			    	}else{
			    		jsonStr = jsonStr+'"id":"'+this.id+'",';
			    	}
			    	var row = jQuery("#statisticsSingleFieldFilter_gridtable").jqGrid('getRowData',this.id);
			    	jsonStr = jsonStr+'"tableName":"'+row['tableName']+'",';
			    	jsonStr = jsonStr+'"name":"'+row['name']+'",';
			    	curInputObj = jQuery("#"+this.id).find(".operationSelect");
			    	jsonStr = jsonStr+'"operation":"'+curInputObj.val()+'",';
			    	dataType = row['fieldInfo.dataType'];
			    	userTag = row['fieldInfo.userTag'];
	        		parameter = row['fieldInfo.parameter1'];
	        		parameterName = row['fieldInfo.parameter2'];
	        		code = row['id'];
	        		switch(dataType){
						case '1':
							if(userTag == 'select'){
								curInputObj = jQuery("#"+this.id).find(".select");
		   					}else if(userTag == 'treeSelect'){
		   						curInputObj = jQuery("#"+this.id).find("input[type=hidden]");
		   					}else{ 
		   						curInputObj = jQuery("#"+this.id).find("input[type=text]");
		   					}
							jsonStr += '"targetValue"'+":"+'"'+curInputObj.val()+'"';
							break;
						case '2':
						case '5':
						case '6':
						case '7':
						case '4':
							curInputObj = jQuery("#"+this.id).find("input[type=text]");
							jsonStr += '"targetValue"'+":"+'"'+curInputObj.val()+'"';
							break;
						case '3':
							curInputObj = jQuery("#"+this.id).find(".yesNoSelect");
							jsonStr += '"targetValue"'+":"+'"'+curInputObj.val()+'"';
							break;
						default:
							jsonStr += '"targetValue":""';
						}
	        		jsonStr += ',"fieldInfoId":'+'"'+row['fieldInfo.fieldId'] +'"},';
			    });
			    if(jsonStr.length>1){
			    	 jsonStr = jsonStr.substring(0,jsonStr.length-1);
			    }
			jsonStr = "{"+'"'+"edit"+'"'+":[" +jsonStr+"]}";
			jQuery("#statisticsSingleFieldFilter_gridAllDatas").val(jsonStr);
			var mainTableId = jQuery("#statisticsSingleFieldTableList").find("option:selected").attr('title');
			var subset = jQuery("#statisticsSingleFieldTableList").find("option:selected").attr('class');
			var deptFK = jQuery("#statisticsSingleFieldTableList").find("option:selected").attr('name');
			var fieldId = jQuery("#statisticsSingleFieldLeftsel").find("option:selected").val();
			var deptIds = jQuery("#statisticsSingleField_search_dept_id").val();
			var searchDateFrom = jQuery("#statisticsSingleField_search_date_from").val();
			var searchDateTo = jQuery("#statisticsSingleField_search_date_to").val(); 
			var shijianFK = "${shijianFK}";
			jQuery("#statisticsSingleFieldFilter_deptIds").val(deptIds);
			jQuery("#statisticsSingleField_gridAllDatas").val(jsonStr);
			jQuery("#statisticsSingleFieldFilter_bdInfoId").val(mainTableId);
			jQuery("#statisticsSingleFieldFilter_deptFK").val(deptFK);
			jQuery("#statisticsSingleFieldFilter_searchDateFrom").val(searchDateFrom);
			jQuery("#statisticsSingleFieldFilter_searchDateTo").val(searchDateTo);
			jQuery("#statisticsSingleFieldFilter_shijianFK").val(shijianFK);
			
			
 			jQuery("#statisticsSingleFieldFilterForm").attr("action","statisticsTargetGridEdit?oper=singleField&id="+fieldId);
 			jQuery("#statisticsSingleFieldFilterForm").submit();
		});
		 var statisticsSingleFieldFilterGridIdString="#statisticsSingleFieldFilter_gridtable";
		 var statisticsSingleFieldFilterGrid = jQuery(statisticsSingleFieldFilterGridIdString);
		 statisticsSingleFieldFilterGrid.jqGrid({
		    	url : null,
		    	editurl:null,
				datatype : "json",
				mtype : "GET",
		        colModel:[
		{name:'id',index:'id',align:'center',label : '<s:text name="statisticsTarget.id" />',hidden:true,key:true},	
		{name:'tableName',index:'tableName',width:'110',align:'left',label : '<s:text name="statisticsTarget.tableName" />',hidden:false},
		{name:'name',index:'name',width:'110',align:'left',label : '<s:text name="statisticsTarget.name" />',hidden:false},	
		{name:'operation',index:'operation',width:'40',align:'center',label : '<s:text name="statisticsTarget.operation" />',hidden:false,
			formatter:function(cellvalue, options, rowObject) {
				var editvalue;
				editvalue='<select class="operationSelect">';
				editvalue+='<option value ="=">=</option>';
				editvalue+='<option value =">">></option>';
				editvalue+='<option value =">=">>=</option>';
				editvalue+='<option value ="<"><</option>';
				editvalue+='<option value ="<="><=</option>';
				editvalue+='<option value ="<>"><></option>';
				editvalue+='<option value ="like">like</option>';
				editvalue += '</select>';
				return editvalue;
				}},
		{name:'targetValue',index:'targetValue',width:'140',align:'left',label : '<s:text name="statisticsTarget.targetValue" />',hidden:false,
			formatter:function(cellvalue, options, rowObject) {
				var dataType = null;
				var code = null;
				var userTag = null;
				var parameter = null;
				var parameterName = null;
				if(rowObject['fieldInfo.dataType']){
					dataType = rowObject['fieldInfo.dataType'];
					code = rowObject['fieldInfo.fieldId'];
					userTag = rowObject['fieldInfo.userTag'];
					parameter = rowObject['fieldInfo.parameter1'];
					parameterName = rowObject['fieldInfo.parameter2'];
				}else{
					dataType = rowObject['fieldInfo']['dataType'];
					code = rowObject['fieldInfo']['fieldId'];
					userTag = rowObject['fieldInfo']['userTag'];
					parameter = rowObject['fieldInfo']['parameter1'];
					parameterName = rowObject['fieldInfo']['parameter2'];
				}
				var editvalue;
				switch(dataType){
					case '1':
						if(userTag == 'select'){
							editvalue = '<select  class="select">';
							editvalue += '<option value ="">--</option>';
							jQuery.ajax({
		 	    		          	 url: 'getFiledDataList',
		 	    		          	 data: {code:parameterName},
		 	    		          	 type: 'post',
		 	    		             dataType: 'json',
		 	    		          	 async:false,
		 	    		             error: function(data){
		 	    		             },
		 	    		           success: function(data){ 
		 	    		        	   for(var optionItem = 0; optionItem<data.filedDataByCode.length;optionItem++){
		 	    		        			  editvalue += ' <option value ='+data.filedDataByCode[optionItem].value+'>'+data.filedDataByCode[optionItem].value+'</option>';
		 	    		        		   }
		    		        	   }
		 	    		           });
							editvalue += '</select>';
						}else if(userTag == 'treeSelect'){
							var mydate = new Date();
							var inputId = "statisticsSingleField" + mydate.getTime()+code;
							editvalue = '<input type="text"  id="'+inputId+'"></input>';
							treeIdArr.push(inputId);
							treeSqlArr.push(parameter);
							inputId += "_id";
							editvalue += '<input type="hidden"  id="'+inputId+'"></input>';
						}else{ 
	   						editvalue = '<input type="text"></input>';
	   					}
						break;
					case '2':
					case '5':
					case '6':
					case '7':
						editvalue = '<input type="text"></input>';
						break;
					case '3':
						var editvalue;
						editvalue='<select class="yesNoSelect">';
						editvalue+='<option value ="">--</option>';
						editvalue+='<option value ="1">是</option>';
						editvalue+='<option value ="0">否</option>';
						editvalue += '</select>';
						break;
					case '4':
						editvalue = '<input onclick="WdatePicker({skin:\'ext\',dateFmt:\'yyyy-MM-dd\'})" type="text"></input>';
						break;
					default:
						editvalue="";
				}
				return editvalue;
	     	}},
		{name:'fieldInfo.fieldId',index:'fieldInfo.fieldId',align:'center',label : '<s:text name="statisticsTarget.code" />',hidden:true},	
		{name:'fieldInfo.userTag',index:'fieldInfo.userTag',align:'center',label : '<s:text name="queryCommonDetail.userTag" />',hidden:true},	
		{name:'fieldInfo.parameter1',index:'fieldInfo.parameter1',align:'center',label : '<s:text name="queryCommonDetail.parameter" />',hidden:true},				
		{name:'fieldInfo.parameter2',index:'fieldInfo.parameter2',align:'center',label : '<s:text name="queryCommonDetail.parameterName" />',hidden:true},				
		{name:'fieldInfo.dataType',index:'fieldInfo.dataType',align:'center',label : '<s:text name="statisticsTarget.dataType" />',hidden:true}			

		        ],
		        jsonReader : {
					root : "statisticsTargets", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				// (4)
				},
		        sortname: 'id',
		        viewrecords: true,
		        sortorder: 'asc',
		        //caption:'<s:text name="statisticsTargetList.title" />',
		        height:300,
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
					 if(treeIdArr){
						   $.each(treeIdArr, function(key,val){      
								 jQuery("#"+val).treeselect({
									   dataType:"sql",
									   optType:"single",
									   sql:treeSqlArr[key],
									   exceptnullparent:false,
									   selectParent:false,
									   lazy:false
									});
							  }); 
					   }
		           if(jQuery(this).getDataIDs().length>0){
		        	   jQuery("#statisticsSingleFieldFilter_expression").addClass('required');
		            }else{
		            	jQuery("#statisticsSingleFieldFilter_expression").removeClass('required');
		            }
		           var dataTest = {"id":"statisticsSingleFieldFilter_gridtable"};
		      	   jQuery.publish("onLoadSelect",dataTest,null);
		       	} 

		    });
		    jQuery(statisticsSingleFieldFilterGrid).jqGrid('bindKeys');
		    jQuery("#statisticsSingleFieldFilter_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
 				var url = "editStatisticsTarget?popup=true&navTabId=statisticsSingleFieldFilter_gridtable";
 				url = url+ "&itemId="+"${id}";
 				url = url+ "&oper="+"tableId";
 				if("${subset}" == '1'){
 					url = url+"&subSetType=subSet";
 				}else{
 					url = url+"&subSetType=";
 				}
 				
 				var winTitle = '<s:text name="statisticsTargetNew.title"/>';
 				$.pdialog.open(url,'addStatisticsTarget',winTitle, {mask:true,resizable:false,maxable:false,width : 500,height : 450});
 				stopPropagation();
		    }); 
		    jQuery("#statisticsSingleFieldFilter_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
				var sid = jQuery("#statisticsSingleFieldFilter_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
		        	alertMsg.error("请选择记录。");
					return;
					}else {
						alertMsg.confirm("确认删除？", {
							okCall : function() {
						for(var i = sid.length;i > 0; i--){
							var rowId = sid[i-1];
							jQuery("#statisticsSingleFieldFilter_gridtable").delRowData(rowId);
							}
						}
						});
					}
	 		});
	});
	function statisticsSingleFieldFilterFormSubmit(data){
		formCallBack(data);
		if(data.message == '查询成功'){
		countValue = data.chartXMLMap["count"];
    	sumValue = data.chartXMLMap["sum"];
    	avgValue = data.chartXMLMap["avg"];
    	maxValue = data.chartXMLMap["max"];
    	minValue = data.chartXMLMap["min"];
    	jQuery("#statisticsSingleField_resultSumValue").val(sumValue);
   		jQuery("#statisticsSingleField_resultAvgValue").val(avgValue);
   		jQuery("#statisticsSingleField_resultMinValue").val(minValue);
   		jQuery("#statisticsSingleField_resultMaxValue").val(maxValue);
   		jQuery("#statisticsSingleField_countValue").val(countValue);
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="statisticsSingleFieldFilterForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,statisticsSingleFieldFilterFormSubmit);">
			<div class="pageFormContent" layoutH="56">
			 	<div>
			 	<s:hidden name="deptIds" id="statisticsSingleFieldFilter_deptIds"/>
			 	<s:hidden name="gridAllDatas" id="statisticsSingleFieldFilter_gridAllDatas"/>
			 	<s:hidden name="bdInfoId" id="statisticsSingleFieldFilter_bdInfoId"/>
			 	<s:hidden name="deptFK" id="statisticsSingleFieldFilter_deptFK"/>
			 	<s:hidden name="searchDateFrom" id="statisticsSingleFieldFilter_searchDateFrom"/>
			 	<s:hidden name="searchDateTo" id="statisticsSingleFieldFilter_searchDateTo"/>
			 	<s:hidden name="shijianFK" id="statisticsSingleFieldFilter_shijianFK"/>
				<s:textfield id="statisticsSingleFieldFilter_expression" key="singleChartFilter.expression" name="expression" maxlength="100"/>
				</div>
			 	<div class="unit">
				<div id="statisticsSingleFieldFilter_gridtable_div" layoutH="190"
					  class="grid-wrapdiv" class="unitBox" style="position:absolute;left:98px;width:480px"
						buttonBar="optId:id;width:500;height:300">
					<div id="load_statisticsSingleFieldFilter_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 						<table id="statisticsSingleFieldFilter_gridtable"></table>
				</div>
				<div style="position:absolute;left:580px">
					<br><br>
					<button type="button" id="statisticsSingleFieldFilter_gridtable_add_custom"><s:text name="button.add" /></button>
					<br><br>
					<button type="button" id="statisticsSingleFieldFilter_gridtable_del_custom"><s:text name="button.delete" /></button>
				</div>							
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="statisticsSingleFieldFilter_savelink">确定</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.close('editSingleFieldFilter');"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





