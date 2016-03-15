<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
var treeSqlArr = new Array();
var treeIdArr = new Array();
	jQuery(document).ready(function() {
		jQuery('#queryCommonSavelink').click(function() {
			var code = null;
			var dataType = null;
			var userTag = null;
			var parameter = null;
			var parameterName = null;
			var curInputObj = null;
			var jsonStr = "";
			var obj = jQuery("#queryCommonDetail_gridtable").jqGrid("getRowData");
			    jQuery(obj).each(function(){
			    	jsonStr = jsonStr+"{";
			    	if(this.id.indexOf("target") >=0){
			    		jsonStr = jsonStr+'"id":"",';
			    	}else{
			    		jsonStr = jsonStr+'"id":"'+this.id+'",';
			    	}
			    	var row = jQuery("#queryCommonDetail_gridtable").jqGrid('getRowData',this.id);
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
			    if(jsonStr.length > 1){
			    	 jsonStr = jsonStr.substring(0,jsonStr.length-1);
			    }
			jsonStr = "{"+'"'+"edit"+'"'+":[" +jsonStr+"]}";
			jQuery("#queryCommon_gridAllDatas").val(jsonStr);
			jQuery("#queryCommonForm").attr("action","saveQueryCommon?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#queryCommonForm").submit();
		});
		 var url = "queryCommonDetailGridList?filter_EQS_queryCommon.id="
			 if("${queryCommon.id}"){
				 url = url+"${queryCommon.id}";
			 }else{
				 url = url+"queryCommon";
			 }
			 var queryCommonDetailGridIdString = "#queryCommonDetail_gridtable";
			 var queryCommonDetailGrid = jQuery(queryCommonDetailGridIdString);
			 queryCommonDetailGrid.jqGrid({
			    	url : url,
			    	editurl:"queryCommonDetailGridEdit",
					datatype : "json",
					mtype : "GET",
			        colModel:[
			{name:'id',index:'id',align:'center',label : '<s:text name="queryCommonDetail.id" />',hidden:true,key:true},	
			{name:'tableName',index:'tableName',width:'110',align:'left',label : '<s:text name="queryCommonDetail.tableName" />',hidden:false,sortable:false},
			{name:'name',index:'name',width:'110',align:'left',label : '<s:text name="queryCommonDetail.name" />',hidden:false,sortable:false},	
			{name:'operation',index:'operation',width:'40',align:'center',label : '<s:text name="queryCommonDetail.operation" />',hidden:false,sortable:false,
				formatter:function(cellvalue, options, rowObject) {
					var editvalue;
					editvalue = '<select class="operationSelect">';
					if(cellvalue == "="){
						editvalue += '<option value ="=" selected>=</option>';
					}else{
						editvalue += '<option value ="=">=</option>';
					}
					if(cellvalue == ">"){
						editvalue += '<option value =">" selected>></option>';
					}else{
						editvalue += '<option value =">">></option>';
					}
					if(cellvalue == ">="){
						editvalue += '<option value =">=" selected>>=</option>';
					}else{
						editvalue += '<option value =">=">>=</option>';
					}
					if(cellvalue == "<"){
						editvalue += '<option value ="<" selected><</option>';
					}else{
						editvalue+='<option value ="<"><</option>';
					}
					if(cellvalue == "<="){
						editvalue += '<option value ="<=" selected><=</option>';
					}else{
						editvalue += '<option value ="<="><=</option>';
					}
					if(cellvalue == "<>"){
						editvalue += '<option value ="<>" selected><></option>';
					}else{
						editvalue += '<option value ="<>"><></option>';
					}
					if(cellvalue == "like"){
						editvalue += '<option value ="like" selected>like</option>';
					}else{
						editvalue += '<option value ="like">like</option>';
					}
					editvalue += '</select>';
					return editvalue;
					}},
			{name:'targetValue',index:'targetValue',width:'140',align:'left',label : '<s:text name="queryCommonDetail.targetValue" />',hidden:false,sortable:false,
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
								editvalue='<select   class="select">';
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
								var inputId = "queryCommon"+mydate.getTime() + code;
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
		   							editvalue = '<input  type="text" value="'+cellvalue +'"></input>';
		   						}else{
		   							editvalue = '<input  type="text"></input>';
		   						}
		   					}
							break;
						case '2':
						case '5':
						case '6':
						case '7':
							if(cellvalue){
								editvalue = '<input  type="text" value="'+cellvalue +'"></input>';
							}else{
								editvalue = '<input  type="text"></input>';
							}
							break;
						case '3':
							var editvalue;
							editvalue='<select class="yesNoSelect">';
							editvalue+='<option value ="">--</option>';
							if(cellvalue=="1"){
								editvalue+='<option value ="1" selected>是</option>';
							}else{
								editvalue+='<option value ="1">是</option>';
							}
							if(cellvalue=="0"){
								editvalue+='<option value ="0" selected>否</option>';
							}else{
								editvalue+='<option value ="0">否</option>';
							}
							editvalue += '</select>';
							break;
						case '4':
							if(cellvalue){
								editvalue = '<input   onclick="WdatePicker({skin:\'ext\',dateFmt:\'yyyy-MM-dd\'})" type="text" value="'+cellvalue+'"></input>';
							}else{
								editvalue = '<input   onclick="WdatePicker({skin:\'ext\',dateFmt:\'yyyy-MM-dd\'})" type="text"></input>';
							}
							break;
						default:
							editvalue="";
					}
					return editvalue;
		     	}},
			{name:'fieldInfo.fieldId',index:'fieldInfo.fieldId',align:'center',label : '<s:text name="queryCommonDetail.code" />',hidden:true},	
			{name:'fieldInfo.userTag',index:'fieldInfo.userTag',align:'center',label : '<s:text name="queryCommonDetail.userTag" />',hidden:true},	
			{name:'fieldInfo.parameter1',index:'fieldInfo.parameter1',align:'center',label : '<s:text name="queryCommonDetail.parameter" />',hidden:true},				
			{name:'fieldInfo.parameter2',index:'fieldInfo.parameter2',align:'center',label : '<s:text name="queryCommonDetail.parameterName" />',hidden:true},				
			{name:'fieldInfo.dataType',index:'fieldInfo.dataType',align:'center',label : '<s:text name="queryCommonDetail.dataType" />',hidden:true}			

			        ],
			        jsonReader : {
						root : "queryCommonDetails", // (2)
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
				        	   jQuery("#queryCommon_expression").addClass('required');
				            }else{
				            	jQuery("#queryCommon_expression").removeClass('required');
				            }
			           var dataTest = {"id":"queryCommonDetail_gridtable"};
			      	   jQuery.publish("onLoadSelect",dataTest,null);
			       	} 

			    });
			    jQuery(queryCommonDetailGrid).jqGrid('bindKeys');
			    
			    jQuery("#queryCommonDetail_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
 					var url = "editStatisticsTarget?popup=true&navTabId=queryCommonDetail_gridtable";
 					url = url+ "&itemId="+"v_hr_person_current";
	 				url = url+ "&oper="+"mainTable";
	 				url = url+"&subSetType=subSet";
 					var winTitle = '<s:text name="statisticsTargetNew.title"/>';
 					$.pdialog.open(url,'addStatisticsTarget',winTitle, {mask:true,resizable:false,maxable:false,width : 500,height : 450});
 					stopPropagation();
			    }); 
			    jQuery("#queryCommonDetail_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
					var sid = jQuery("#queryCommonDetail_gridtable").jqGrid('getGridParam','selarrrow');
			        if(sid==null || sid.length ==0){
			        	alertMsg.error("请选择记录。");
						return;
						}else {
							alertMsg.confirm("确认删除？", {
								okCall : function() {
							for(var i = sid.length;i > 0; i--){
								var rowId = sid[i-1];
								jQuery("#queryCommonDetail_gridtable").delRowData(rowId);
								}
							}
							});
						}
		 		});
			    
			    if("${oper}"=="view"){
					readOnlyForm("queryCommonForm");
					jQuery("#queryCommon_buttonActive").css("display","none");
					jQuery("#queryCommonDetail_gridtable_add_custom").css("display","none");
					jQuery("#queryCommonDetail_gridtable_del_custom").css("display","none");
//			 		var obj = jQuery("#trainRecordDetail_gridtable").jqGrid("getRowData");
//			 		    jQuery(obj).each(function(){
//			 		    	jQuery("#"+this.id).find("[type='text']").css("readonly","readonly");
//			 		    });
				}else{
					jQuery("#queryCommon_code").addClass("required");
					jQuery("#queryCommon_name").addClass("required");
					jQuery("#queryCommon_sn").addClass("required");
				}
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="queryCommonForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden name="gridAllDatas" id="queryCommon_gridAllDatas"/>
				<s:hidden key="queryCommon.id"/>
				<s:hidden key="queryCommon.changeUser.personId"/>
				<s:hidden key="queryCommon.changeDate"/>
				<s:hidden key="queryCommon.sysFiled"/>
				</div>
				<div class="unit">
				<s:if test="%{entityIsNew}">
					<s:textfield id="queryCommon_code" key="queryCommon.code" name="queryCommon.code"  maxlength="30" notrepeat='常用查询号已存在' validatorParam="from QueryCommon query where query.code=%value%"/>
					<s:textfield id="queryCommon_name" key="queryCommon.name" name="queryCommon.name"  maxlength="50" notrepeat='常用查询名称已存在' validatorParam="from QueryCommon query where query.name=%value%"/>
				</s:if>
				<s:else>
					<s:textfield id="queryCommon_code" key="queryCommon.code" name="queryCommon.code"  maxlength="30" readonly="true"/>
					<s:textfield id="queryCommon_name" key="queryCommon.name" name="queryCommon.name"  maxlength="50" oldValue="'${queryCommon.name}'" notrepeat='常用查询名称已存在' validatorParam="from QueryCommon query where query.name=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<s:textfield id="queryCommon_sn" key="queryCommon.sn" name="queryCommon.sn" cssClass="digits"/>
				<s:textfield id="queryCommon_remark" key="queryCommon.remark" name="queryCommon.remark" maxlength="200"/>
				</div>
				<div class="unit">
				<s:textfield id="queryCommon_expression" style="width:401px" key="queryCommon.expression" name="queryCommon.expression" maxlength="100"/>
				</div>
				<div class="unit">
				 <label><s:text name='queryCommon.disabled' />:</label> <span
     				  style="float: left; width: 140px"> <s:checkbox
    				    key="queryCommon.disabled" required="false" theme="simple" />
    		    </span>
				</div>
				
				<div class="unit">
				<div id="queryCommonDetail_gridtable_div" layoutH="190"
					  class="grid-wrapdiv" class="unitBox" style="position:absolute;left:98px;width:480px"
						buttonBar="optId:id;width:500;height:300">
					<div id="load_queryCommonDetail_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 						<table id="queryCommonDetail_gridtable"></table>
				</div>
				<div style="position:absolute;left:580px">
					<br><br>
					<button type="button" id="queryCommonDetail_gridtable_add_custom"><s:text name="button.add" /></button>
					<br><br>
					<button type="button" id="queryCommonDetail_gridtable_del_custom"><s:text name="button.delete" /></button>
				</div>							
				</div>
				<s:if test="queryCommon.disabled==true">
     				<div style="position:absolute;left:600px;top:10px;padding:2px;z-index:100;border-style: solid;border-width:1px; border-color:red">
      				<span style='color:red;font-size:12px'>已停用</span>
     				</div>
    			</s:if>
			</div>
			
			
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="queryCommon_buttonActive">
							<div class="buttonContent">
								<button type="button" id="queryCommonSavelink"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.close('editQueryCommon');"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>	
	</div>
</div>





