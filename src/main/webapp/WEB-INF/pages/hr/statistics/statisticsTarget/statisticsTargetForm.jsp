<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#statisticsTargetSavelink').click(function() {
			//var url="saveStatisticsTarget?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}';
			var toAdds = jQuery("#statisticsTargetRightsel option");
			if(toAdds.length > 0){
				var toAddsJson = {};
				var fieldIdStr = "";
				jQuery.each(toAdds,function(index,obj){
					var text = jQuery(obj).text();
					var value = jQuery(obj).val();
					var tableName = jQuery(obj).attr('class');
					toAddsJson[value] = {"text":text,"tableName":tableName};
					fieldIdStr += value + ",";
				});
				if(fieldIdStr){
					fieldIdStr = fieldIdStr.substring(0,fieldIdStr.length-1);
				}
				jQuery.ajax({
			           url: 'fieldInfoGridList',
			           data: {oper:"all","filter_INS_fieldId":fieldIdStr},
			           type: 'post',
			           dataType: 'json',
			           async:false,
			           error: function(data){
			           },
			           success: function(data){
			        	   var fieldInfoes = data.fieldInfoes;
			        	   if(fieldInfoes){
			        		   for(var index = 0;index< fieldInfoes.length;index++){
			        			   	var fieldInfo = fieldInfoes[index];
			        			   	var userTag = fieldInfo.userTag;
					        		var parameter = fieldInfo.parameter1;
					        		var parameterName = fieldInfo.parameter2;
					        		var fieldType = fieldInfo.dataType;
					        		var fieldId = fieldInfo.fieldId;
					        		var fieldText = toAddsJson[fieldId]["text"];
					        		var tableName = toAddsJson[fieldId]["tableName"];
					        		var mydate = new Date();
					        		 jQuery("#"+"${navTabId}").addRowData("target"+mydate.getTime(), {
				 						 "id":"target"+mydate.getTime(),
				 						 "tableName":tableName,
										 "name":fieldText,
										 "operation":"=",
										 "targetValue":null,
										 "fieldInfo.fieldId":fieldId,
										 "fieldInfo.userTag":userTag,
										 "fieldInfo.parameter1":parameter,
										 "fieldInfo.parameter2":parameterName,
				 						 "fieldInfo.dataType":fieldType
										 }, "last");
					        	 } 
			        	   }
			           }
			      });
			}
			$.pdialog.close('addStatisticsTarget');
// 			jQuery("#statisticsTargetForm").attr("action",url);
// 			jQuery("#statisticsTargetForm").submit();
		});
		 var itemId = "${itemId}";
		   subSetType="";
		   jQuery.ajax({
	           url: 'getStatisticsTargetBdInfoList',
	           data: {itemId:itemId,oper:"${oper}",subSetType:"${subSetType}"},
	           type: 'post',
	           dataType: 'json',
	           async:false,
	           error: function(data){
	           },
	           success: function(data){
	        	 subSetType = data.subSetType;
	        	 for(var num = 0;num< data.bdInfoes.length;num++){
	        		jQuery("#statisticsTargetMenuList").get(0).options.add(new Option(data.bdInfoes[num].bdInfoName,data.bdInfoes[num].bdInfoId));
	        	 	} 
	           }
	       });
	});
	function statisticsTargetChange(value){
		jQuery("#statisticsTargetLeftsel").empty();
		if(value == "--"){
			return;
		}
		   jQuery.ajax({
	           url: 'getStatisticsTargetFieldInfoList',
	           data: {subSetType:subSetType,bdInfoId:value},
	           type: 'post',
	           dataType: 'json',
	           async:false,
	           error: function(data){
	           },
	           success: function(data){
	        	 var optionHtml = "";
	        	 for(var num = 0;num< data.fieldInfoes.length;num++){
	        		 optionHtml += "<option value='"+data.fieldInfoes[num].fieldId+"' class='"+jQuery("#statisticsTargetMenuList").find("option:selected").text()+"'>"+data.fieldInfoes[num].fieldName+"</option>";
	        	 	} 
	        	 jQuery("#statisticsTargetLeftsel").html(optionHtml);
	           }
	       });
	}
	 function statisticsTargetmove(opertion) {
	        if(opertion == "add"){
	        	var toAdds = jQuery("#statisticsTargetLeftsel option:selected");
	 	       if(toAdds.length == 0){ 
	 	    	 alertMsg.error("请选择要添加的值。");
	 	    	  return;  
	 	       }else{
	 	    	  toAdds.each(function(){  
	 	    		 jQuery("#statisticsTargetRightsel").append("<option value='"+jQuery(this).val()+"' class='"+jQuery(this).attr('class')+"'>"+jQuery(this).text()+"</option");  
	 	    	 });  
	 	       }
	        }
	        if(opertion == "del"){
	        	var toAdds = jQuery("#statisticsTargetRightsel option:selected");
	 	       if(toAdds.length == 0){ 
	 	    	 alertMsg.error("请选择要删除的值。");
	 	    	  return;  
	 	       }else{
	 	    	  toAdds.each(function(){  
	 	    		 jQuery(this).remove();    
	 	    	 });  
	 	       }
	        }
	    }
	 
	 function closeStatisticsTargetFromDialog(){
		$.pdialog.close("addStatisticsTarget");
		//$.pdialog.closeCurrent();
	 }
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="statisticsTargetForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<table style="margin:auto;">
					<tr>
						<td style="font-size:13px;">备选指标
						</td>
						<td>
						</td>
						<td style="font-size:13px;">已选指标
						</td>
					</tr>
					<tr style="align:left">
						<td>
						<select id="statisticsTargetMenuList" onchange=statisticsTargetChange(this.value)>
						<option value ="--">--</option>
						</select>
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td rowspan="4">
						<select multiple="multiple" style="height: 300px; width: 150px;" id="statisticsTargetLeftsel">
            			</select>
            			</td>
           				<td><input onclick="statisticsTargetmove('add')" type="button" value="添加"></td>
           				 <td rowspan="5"><select multiple="multiple" style="height: 300px; width: 150px;" id="statisticsTargetRightsel">
          				 </select>
          				 </td>
					</tr>
					 <tr>
           				 <td><input onclick="statisticsTargetmove('del')" type="button" value="删除"></td>
        			</tr>
				</table>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="statisticsTargetSavelink"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="closeStatisticsTargetFromDialog();"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
</div>





