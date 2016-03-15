
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
 var seOrSo = "so";
 jQuery(document).ready(function(){
	 
	 jQuery("#saveColumnSet").click(function(){
		 var inspectTemplId = jQuery("#inspectTempl_gridtable").jqGrid('getGridParam','selarrrow');
		 var columnName = "",status = "",columnWidth="";
		 jQuery("input[name=columnCheckBox]").each(function(){
			 columnName += jQuery(this).attr("id")+",";
			 if(jQuery(this).attr("checked")=="checked"){
				 status += "true"+",";
			 }else{
				 status += "false"+",";
			 }
		 });
		 jQuery("input[name=columnWidth]").each(function(){
			 columnWidth +=  jQuery(this).val()+",";
		 });
		 $.ajax({
			    url: 'saveColumnSet?inspectTemplId='+inspectTemplId+'&columnNames='+columnName+'&statuss='+status+'&columnWidths='+columnWidth,
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			        jQuery('#name').attr("value",data.responseText);
			    },
			    success: function(data){
			        // do something with xml
			        $.pdialog.closeCurrent();
			        reloadInspectBSC('reload');
			        alert(data.message);
			    }
			});
	 });
	 jQuery("input[name='columnCheckBox']").mousedown(function(e){ 
	    	//alert();
	    	e.stopPropagation();
	  });
	 jQuery("input[name='columnWidth']").mousedown(function(e){ 
	    	//alert();
	    	e.stopPropagation();
	  });
}); 


</script>
<div id="bscColumnDiv" class="page">
			<div class="pageContent">
			<!-- <div class="panelBar">
			<ul class="toolBar">
				<li><a  class="addbutton" href="javaScript:selectStatus()" ><span>输入
					</span>
				</a>
				</li>
				<li><a class="delbutton"  href="javaScript:sortStatus()"><span>排序</span>
				</a>
				</li>
			
			</ul>
			</div> -->
				<form id="inspectTemplForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
					<div class="pageFormContent sortDrag" layoutH="57">
					<s:iterator value="columnMap">
						<div style="border:1px solid #B8D0D6;padding:5px;margin:5px">
						<input id="${key}" type="checkbox" name="columnCheckBox" <s:if test="value.status==true">checked</s:if>/>
						<s:text name="%{key}"></s:text>
						 <span style="margin-right:20px;float:right">列宽：<input style="float:none" name="columnWidth" type="text" size="3" value="${value.width }"/>%</span>
						</div>
					</s:iterator>
				</div>
				</form>
				<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveColumnSet"><s:text name="button.save" /></button>
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
			</div>
</div>