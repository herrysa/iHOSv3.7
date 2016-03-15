<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#hrPersonSortSavelink').click(function() {
			
			
		});
		hrPersonSortLeftselLoad();
	});
	function hrPersonSortLeftselLoad(){
		jQuery("#hrPersonSortLeftsel").append("<option value='personCode' name='S'>人员编码</option>");
		jQuery("#hrPersonSortLeftsel").append("<option value='name' name='S'>姓名</option>");
		jQuery("#hrPersonSortLeftsel").append("<option value='duty' name='S'>职位</option>");
		jQuery("#hrPersonSortLeftsel").append("<option value='birthday' name='D'>出生日期</option>");
		jQuery("#hrPersonSortLeftsel").append("<option value='idNumber' name='S'>身份证号</option>");
	}
	 function move(opertion) {
	        if(opertion == "addASC"){
	        	var toAdds = jQuery("#hrPersonSortLeftsel option:selected");
	 	       if(toAdds.length == 0){ 
	 	    	 alertMsg.error("请选择要添加的值。");
	 	    	  return;  
	 	       }else{
	 	    	  toAdds.each(function(){  
	 	    		 jQuery("#hrPersonSortRightselASC").append("<option value='"+jQuery(this).val()+"' class='"+jQuery(this).attr('class')+"'>"+jQuery(this).text()+"</option");  
	 	    	 });  
	 	       }
	        }
	        if(opertion == "delASC"){
	        	var toAdds = jQuery("#hrPersonSortRightselASC option:selected");
	 	       if(toAdds.length == 0){ 
	 	    	 alertMsg.error("请选择要删除的值。");
	 	    	  return;  
	 	       }else{
	 	    	  toAdds.each(function(){  
	 	    		 jQuery(this).remove();    
	 	    	 });  
	 	       }
	        }
	        if(opertion == "addDESC"){
	        	var toAdds = jQuery("#hrPersonSortLeftsel option:selected");
	 	       if(toAdds.length == 0){ 
	 	    	 alertMsg.error("请选择要添加的值。");
	 	    	  return;  
	 	       }else{
	 	    	  toAdds.each(function(){  
	 	    		 jQuery("#hrPersonSortRightselDESC").append("<option value='"+jQuery(this).val()+"' class='"+jQuery(this).attr('class')+"'>"+jQuery(this).text()+"</option");  
	 	    	 });  
	 	       }
	        }
	        if(opertion == "del"){
	        	var toAdds = jQuery("#hrPersonSortRightselDESC option:selected");
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
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="hrPersonSortForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<table style="margin:auto;">
					<tr>
						<td style="font-size:13px;">备选指标
						</td>
						<td>
						</td>
						<td style="font-size:13px;">升序指标
						</td>
						<td style="font-size:13px;">降序指标
						</td>
					</tr>
					<tr>
						<td rowspan="4">
						<select multiple="multiple" style="height: 300px; width: 150px;" id="hrPersonSortLeftsel">
            			</select>
            			</td>
           				<td>
           				<input onclick="move('addASC')" type="button" value="添加升序"><br><br>
           				<input onclick="move('delASC')" type="button" value="删除升序"><br><br>
           				<input onclick="move('addDESC')" type="button" value="添加降序"><br><br>
           				<input onclick="move('delDESC')" type="button" value="删除降序">
           				</td>
           				 <td rowspan="5"><select multiple="multiple" style="height: 300px; width: 150px;" id="hrPersonSortRightselASC">
          				 </select>
          				 </td>
          				  <td rowspan="5"><select multiple="multiple" style="height: 300px; width: 150px;" id="hrPersonSortRightselDESC">
          				 </select>
          				 </td>
					</tr>
				</table>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="hrPersonSortSavelink"><s:text name="button.save" /></button>
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