<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<script language="javascript" type="text/javascript"
	src="${ctx}/scripts/DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

</script>
<script>
jQuery(document).ready(function() {
	   jQuery.ajax({
   			  url: 'getTableStructureList',
  			  data: {tableCode:"${subTableCode}"},
   			  type: 'post',
   			  dataType: 'json',
   			  async:false,
   			  error: function(data){
   				 },
    		success: function(data){  
	        	   var dataHtml = jQuery("#sysTableDataChange_dataList")[0].innerHTML;
	        	    var sysTableStructureDatas = data.sysTableStructures;
	       			for(var sysTableRow in sysTableStructureDatas){
	       				switch(sysTableStructureDatas[sysTableRow].dataType)
	       				     {
	       						case '1':
 	       						 	if(sysTableStructureDatas[sysTableRow].codeId){
 	       						 	dataHtml += '<div class="unit"><label>'+sysTableStructureDatas[sysTableRow].name+'</label><select  id='+sysTableStructureDatas[sysTableRow].code+' class="select"></select></div>';
 	       							}else{ 
	       								dataHtml += '<div class="unit"><label>'+sysTableStructureDatas[sysTableRow].name+'</label><input id='+sysTableStructureDatas[sysTableRow].code+'></input></div>';
 	       							}
	       							break;
	       						case '2':
	       						case '5':
	       						case '6':
	       						case '7':
	       						dataHtml += '<div class="unit"><label>'+sysTableStructureDatas[sysTableRow].name+'</label><input id='+sysTableStructureDatas[sysTableRow].code+'></input></div>';
	       							break;
	       						 case '3':
	       							dataHtml += '<div class="unit"><label>'+sysTableStructureDatas[sysTableRow].name+'</label><input type="checkbox" name='+sysTableStructureDatas[sysTableRow].code+' id='+sysTableStructureDatas[sysTableRow].code+'></input></div>';
	       							break;
	       						case '4':
	       							dataHtml += '<div class="unit"><label>'+sysTableStructureDatas[sysTableRow].name+'</label><input id='+sysTableStructureDatas[sysTableRow].code+' class="Wdate input-small" onClick="WdatePicker({dateFmt:\'yyyy-MM-dd\'})" onFocus="WdatePicker({isShowClear:true,readOnly:true})" type="text"></input></div>';
	       							break; 
	       							
	       						default:
	       							dataHtml +="";
	       				     }
 	       			}      
 	       			jQuery("#sysTableDataChange_dataList")[0].innerHTML=dataHtml;
 	       			for(var sysTableRow in sysTableStructureDatas){
 	       				if(sysTableStructureDatas[sysTableRow].dataType==1){
 	       				if(sysTableStructureDatas[sysTableRow].codeId){
 	       			    var code = sysTableStructureDatas[sysTableRow].codeId;
 	       				jQuery.ajax({
 	    		           url: 'getFiledDataList',
 	    		           data: {code:code},
 	    		           type: 'post',
 	    		           dataType: 'json',
 	    		           async:false,
 	    		           error: function(data){
 	    		           },
 	    		           success: function(data){ 
 	    		        	  jQuery("#"+sysTableStructureDatas[sysTableRow].code).get(0).options.add(new Option("",""));
 	    		        	   for(var optionItem = 0; optionItem<data.filedDataByCode.length;optionItem++){
 	    		        		  	jQuery("#"+sysTableStructureDatas[sysTableRow].code).get(0).options.add(new Option(data.filedDataByCode[optionItem].value,data.filedDataByCode[optionItem].value));
 	    		        		   }
 	    		        	   }
 	    		           });
 	       				}
 	       				}
 	       			}
 	       			
    			}
			});
			 jQuery.ajax({
		           url: 'getDataByTableCode',
		           data: {code:"${code}",tableCode:"${subTableCode}"},
		           type: 'post',
		           dataType: 'json',
		           async:false,
		           error: function(data){
		           },
		           success: function(data){ 
		        	for(var dataRows in data.sysTableStructureDatas[0]){
		        		if(jQuery("#"+dataRows).attr("type")=="checkbox"){
		        			if(data.sysTableStructureDatas[0][dataRows]==1){
		        				jQuery("#"+dataRows).attr("checked",true);
		        			}else{
		        				jQuery("#"+dataRows).attr("checked",false);
		        			}
		        		}else{
		        		jQuery("#"+dataRows).val(data.sysTableStructureDatas[0][dataRows]);
		        		}
		        	 }
		         } 
		       });
		jQuery('#sysTableDataChangeSavelink').click(function() {
		     var url="saveSysTableData?oper=update&"				
			 jQuery.ajax({
	   			  url: 'getTableStructureList',
	  			  data: {tableCode:"${subTableCode}"},
	   			  type: 'post',
	   			  dataType: 'json',
	   			  async:false,
	   			  error: function(data){
	   				 },
	    		success: function(data){  
		        	    var sysTableStructureDatas = data.sysTableStructures;
		        	    url += "code="+"${code}&";
		       			for(var sysTableRow in sysTableStructureDatas){
		       				if(jQuery("#"+sysTableStructureDatas[sysTableRow].code).attr("type")=="checkbox"){
			        			if(jQuery("#"+sysTableStructureDatas[sysTableRow].code).attr("checked")=="checked"){
			        				url +=sysTableStructureDatas[sysTableRow].code +"=1&";
			        			}else{
			        				url +=sysTableStructureDatas[sysTableRow].code +"=0&";
			        			}
			        		}else{
		       				url +=sysTableStructureDatas[sysTableRow].code +"=" + jQuery("#"+sysTableStructureDatas[sysTableRow].code).val() +"&";
			        		}
		       			}    
	    			}
				});
		url+="tableCode="+"${subTableCode}" +"&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}' + "&subEntityId="+"${subEntityId}";
		jQuery("#sysTableDataChangeForm").attr("action",url);
		jQuery("#sysTableDataChangeForm").submit();  
	});
}); 
</script>
</head>
	<div class="page">
		<div class="pageContent">
			<form id="sysTableDataChangeForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div id="sysTableDataChange_dataList" class="pageFormContent" layoutH="56">				
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="sysTableDataChangeSavelink"><s:text name="button.save" /></button>
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
</html>