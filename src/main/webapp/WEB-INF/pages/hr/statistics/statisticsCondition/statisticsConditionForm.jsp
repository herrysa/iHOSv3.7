<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
var treeSqlArr = new Array();
var treeIdArr = new Array();
	jQuery(document).ready(function() {
		jQuery('#statisticsConditionSavelink').click(function() {
			var code = null;
			var dataType = null;
			var userTag = null;
			var parameter = null;
			var parameterName = null;
			var curInputObj = null;
			var jsonStr = "";
			var obj = jQuery("#statisticsTarget_gridtable").jqGrid("getRowData");
// 			var obj = jQuery("#statisticsTarget_gridtable>tbody>tr");
			    jQuery(obj).each(function(){
			    	jsonStr = jsonStr+"{";
			    	if(this.id.indexOf("target") >=0){
			    		jsonStr = jsonStr+'"id":"",';
			    	}else{
			    		jsonStr = jsonStr+'"id":"'+this.id+'",';
			    	}
			    	var row = jQuery("#statisticsTarget_gridtable").jqGrid('getRowData',this.id);
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
			jQuery("#statisticsCondition_gridAllDatas").val(jsonStr);
			jQuery("#statisticsConditionForm").attr("action","saveStatisticsCondition?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#statisticsConditionForm").submit();
		});
		 var url="statisticsTargetGridList?filter_EQS_parentCondition.id="
		 if("${statisticsCondition.id}"){
			 url=url+"${statisticsCondition.id}";
		 }else{
			 url=url+"statisticsTarget";
		 }
		 var statisticsTargetGridIdString="#statisticsTarget_gridtable";
		 var statisticsTargetGrid = jQuery(statisticsTargetGridIdString);
		    statisticsTargetGrid.jqGrid({
		    	url : url,
		    	editurl:"statisticsTargetGridEdit",
				datatype : "json",
				mtype : "GET",
		        colModel:[
		{name:'id',index:'id',align:'center',label : '<s:text name="statisticsTarget.id" />',hidden:true,key:true},	
		{name:'tableName',index:'tableName',width:'110',align:'left',label : '<s:text name="statisticsTarget.tableName" />',hidden:false,sortable:false},
// 		{name:'sn',index:'sn',width:'100',align:'right',label : '<s:text name="statisticsTarget.sn" />',hidden:false,formatter:'integer',editable:true,edittype:'text'},
		{name:'name',index:'name',width:'110',align:'left',label : '<s:text name="statisticsTarget.name" />',hidden:false,sortable:false},	
		{name:'operation',index:'operation',width:'40',align:'center',label : '<s:text name="statisticsTarget.operation" />',hidden:false,sortable:false,
			formatter:function(cellvalue, options, rowObject) {
				var editvalue;
				editvalue='<select class="operationSelect">';
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
				editvalue += '</select>';
				return editvalue;
				}},
		{name:'targetValue',index:'targetValue',width:'140',align:'left',label : '<s:text name="statisticsTarget.targetValue" />',hidden:false,sortable:false,
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
							editvalue='<select  class="select">';
							editvalue+='<option value ="">--</option>';
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
		 	    		        		   if(cellvalue==data.filedDataByCode[optionItem].value){
		 	    		        			  editvalue+=' <option value ='+data.filedDataByCode[optionItem].value+' selected>'+data.filedDataByCode[optionItem].value+'</option>';
		 	    		        		   }else{
		 	    		        			  editvalue+=' <option value ='+data.filedDataByCode[optionItem].value+'>'+data.filedDataByCode[optionItem].value+'</option>';
		 	    		        		   }
		 	    		        		   }
		    		        	   }
		 	    		           });
							editvalue += '</select>';
						}else if(userTag == 'treeSelect'){
							var mydate = new Date();
							var inputId = "staticsCondition"+mydate.getTime()+code;
							editvalue = '<input type="text"  id="'+inputId+'"></input>';
							treeIdArr.push(inputId);
							treeSqlArr.push(parameter);
							inputId += "_id";
							if(cellvalue){
								editvalue += '<input type="hidden"  id="'+inputId+'"></input>';
							}else{
								editvalue += '<input type="hidden"  id="'+inputId+'" value="'+cellvalue +'"></input>';
							}
						}else{  
	   						if(cellvalue){
	   							editvalue = '<input type="text" value="'+cellvalue +'"></input>';
	   						}else{
	   							editvalue = '<input type="text"></input>';
	   						}
	   					}
						break;
					case '2':
					case '5':
					case '6':
					case '7':
						if(cellvalue){
							editvalue = '<input type="text" value="'+cellvalue +'"></input>';
						}else{
							editvalue = '<input type="text"></input>';
						}
						break;
					case '3':
						var editvalue;
						editvalue = '<select class="yesNoSelect">';
						editvalue += '<option value ="">--</option>';
						if(cellvalue=="1"){
							editvalue += '<option value ="1" selected>是</option>';
						}else{
							editvalue += '<option value ="1">是</option>';
						}
						if(cellvalue=="0"){
							editvalue += '<option value ="0" selected>否</option>';
						}else{
							editvalue += '<option value ="0">否</option>';
						}
						editvalue += '</select>';
						break;
					case '4':
						if(cellvalue){
							editvalue = '<input onclick="WdatePicker({skin:\'ext\',dateFmt:\'yyyy-MM-dd\'})" type="text" value="'+cellvalue+'"></input>';
						}else{
							editvalue = '<input onclick="WdatePicker({skin:\'ext\',dateFmt:\'yyyy-MM-dd\'})" type="text"></input>';
						}
						break;
					default:
						editvalue="";
				}
				return editvalue;
	     	}},
// 		{name:'changeDate',index:'changeDate',align:'center',label : '<s:text name="statisticsTarget.changeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
		{name:'fieldInfo.fieldId',index:'fieldInfo.fieldId',align:'center',label : '<s:text name="statisticsTarget.code" />',hidden:true},	
// 		{name:'fieldInfo.code',index:'fieldInfo.code',align:'center',label : '<s:text name="statisticsTarget.code" />',hidden:true},				
		{name:'fieldInfo.userTag',index:'fieldInfo.userTag',align:'center',label : '<s:text name="queryCommonDetail.userTag" />',hidden:true},	
		{name:'fieldInfo.parameter1',index:'fieldInfo.parameter1',align:'center',label : '<s:text name="queryCommonDetail.parameter" />',hidden:true},				
		{name:'fieldInfo.parameter2',index:'fieldInfo.parameter2',align:'center',label : '<s:text name="queryCommonDetail.parameterName" />',hidden:true},				
		{name:'fieldInfo.dataType',index:'fieldInfo.dataType',align:'center',label : '<s:text name="statisticsTarget.dataType" />',hidden:true}			
// 		{name:'disabled',index:'disabled',width:'60',align:'center',label : '<s:text name="statisticsTarget.disabled" />',hidden:false,formatter:'checkbox'},				
// 		{name:'remark',index:'remark',width:'250',align:'left',label : '<s:text name="statisticsTarget.remark" />',hidden:true},				
// 		{name:'sysFiled',index:'sysFiled',width:'60',align:'center',label : '<s:text name="statisticsTarget.sysFiled" />',hidden:true,formatter:'checkbox'}				

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
			        	   jQuery("#statisticsCondition_expression").addClass('required');
			            }else{
			            	jQuery("#statisticsCondition_expression").removeClass('required');
			            }
		           var dataTest = {"id":"statisticsTarget_gridtable"};
		      	   jQuery.publish("onLoadSelect",dataTest,null);
//		       	   makepager("statisticsTarget_gridtable");
		       	} 

		    });
		    jQuery(statisticsTargetGrid).jqGrid('bindKeys');
		    
		    jQuery("#statisticsTarget_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
				var url = "editStatisticsTarget?popup=true&navTabId=statisticsTarget_gridtable";
				url=url+ "&itemId="+"${statisticsCondition.parentItem.id}";
				var winTitle='<s:text name="statisticsTargetNew.title"/>';
				$.pdialog.open(url,'addStatisticsTarget',winTitle, {mask:true,resizable:false,maxable:false,width : 500,height : 450});
				stopPropagation();
		    }); 
		    jQuery("#statisticsTarget_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
				var sid = jQuery("#statisticsTarget_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
		        	alertMsg.error("请选择记录。");
					return;
					}else {
						alertMsg.confirm("确认删除？", {
							okCall : function() {
						for(var i = sid.length;i > 0; i--){
							var rowId = sid[i-1];
							jQuery("#statisticsTarget_gridtable").delRowData(rowId);
							}
						}
						});
					}
	 		});
	});
	 function checkStatisticsConditionName(obj){
		  var value = obj.value;
		  if(!value){
		   return;
		  }
		  if('${entityIsNew}'!="true"){
		   var oldName = "${statisticsCondition.name}";
		   if(value==oldName){
		    return;
		   }
		  }
		  var itemId = "${statisticsCondition.parentItem.id}";
		  jQuery.ajax({
		            url: 'checkStatisticsConditionName',
		            data: {itemId:itemId,conditionName:value},
		            type: 'post',
		            dataType: 'json',
		            async:false,
		            error: function(data){
		            },
		            success: function(data){
		             if(data){
		              alertMsg.error("此统计条件名称已存在！");
		              obj.value="";
		             }
		            }
		        });
		 }
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="statisticsConditionForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden name="gridAllDatas" id="statisticsCondition_gridAllDatas"/>
				<s:hidden key="statisticsCondition.id"/>
				<s:hidden key="statisticsCondition.changeUser.personId"/>
				<s:hidden key="statisticsCondition.changeDate"/>
				<s:hidden key="statisticsCondition.sysFiled"/>
				</div>
				<div class="unit">
				<s:if test="%{entityIsNew}">
				<s:textfield id="statisticsCondition_code" key="statisticsCondition.code" name="statisticsCondition.code" cssClass="required" maxlength="30" 
					notrepeat='统计条件编码已存在' validatorParam="from StatisticsCondition condition where condition.code=%value%"/>
					<s:textfield id="statisticsCondition_name" key="statisticsCondition.name" name="statisticsCondition.name" cssClass="required" maxlength="50" 
					notrepeat='统计条件名称已存在' validatorParam="from StatisticsCondition condition where condition.name=%value% and condition.parentItem.id='${statisticsCondition.parentItem.id}'"/>
				</s:if>
				<s:else>
				<s:textfield id="statisticsCondition_code" key="statisticsCondition.code" name="statisticsCondition.code" cssClass="required" maxlength="30" oldValue="'${statisticsCondition.code}'"
					notrepeat='统计条件编码已存在' validatorParam="from StatisticsCondition condition where condition.code=%value%"/>
					<s:textfield id="statisticsCondition_name" key="statisticsCondition.name" name="statisticsCondition.name" cssClass="required" maxlength="50"  oldValue="'${statisticsCondition.name}'"
					notrepeat='统计条件名称已存在' validatorParam="from StatisticsCondition condition where condition.name=%value% and condition.parentItem.id='${statisticsCondition.parentItem.id}'"/>
				</s:else>
				</div>
				<div class="unit">
				<s:textfield id="statisticsCondition_sn" key="statisticsCondition.sn" name="statisticsCondition.sn" cssClass="digits required"/>
					<label><s:text name='statisticsCondition.parentItem' />:</label>
						<input type="text" id="statisticsCondition_parentItem"  name="statisticsCondition.parentItem.name" value="${statisticsCondition.parentItem.name}" class="required" readonly="readonly"/>
						<input type="hidden" id="statisticsCondition_parentItem_id" name="statisticsCondition.parentItem.id" value="${statisticsCondition.parentItem.id}"/>
				</div>
				<div class="unit">
				<s:textfield id="statisticsCondition_remark" key="statisticsCondition.remark" name="statisticsCondition.remark" maxlength="200"/>
				 <label><s:text name='statisticsCondition.disabled' />:</label> <span
     				  style="float: left; width: 140px"> <s:checkbox
    				    key="statisticsCondition.disabled" required="false" theme="simple" />
    		    </span>
				</div>
				<div class="unit">
				<s:textfield id="statisticsCondition_expression" style="width:401px" key="statisticsCondition.expression" name="statisticsCondition.expression" maxlength="100"/>
				</div>
				<div class="unit">
				<div id="statisticsTarget_gridtable_div" layoutH="190"
					  class="grid-wrapdiv" class="unitBox" style="position:absolute;left:98px;width:480px"
						buttonBar="optId:id;width:500;height:300">
					<div id="load_statisticsTarget_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 						<table id="statisticsTarget_gridtable"></table>
				</div>
				<div style="position:absolute;left:580px">
					<br><br>
					<button type="button" id="statisticsTarget_gridtable_add_custom"><s:text name="button.add" /></button>
					<br><br>
					<button type="button" id="statisticsTarget_gridtable_del_custom"><s:text name="button.delete" /></button>
				</div>							
				</div>
			</div>
			
			
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="statisticsConditionSavelink"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.close('editStatisticsCondition');"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





