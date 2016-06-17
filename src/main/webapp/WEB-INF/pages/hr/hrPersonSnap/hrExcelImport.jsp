<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#hrExcelImportlink').click(function() {
			if("${oper}"=="hrDept"){
				jQuery("#hrExcelImportForm").attr("action","importHrDeptFromExcel?popup=true&navTabId=${navTabId}&random=${random}");
				jQuery("#hrExcelImportForm").submit();
			}else{
				var msg = jQuery("#hrPersonImportcheckMsg").val();
				if(msg=='检查成功!'){
					jQuery("#hrExcelImportForm").attr("action","importHrPersonFromExcel?popup=true&navTabId=${navTabId}&random=${random}");
					jQuery("#hrExcelImportForm").submit();
				}else{
					if(!msg){
						alertMsg.error("请先检查！");
					}else{
						alertMsg.error(msg);
					}
				}
			}
		});
		jQuery("#hrExcelChecklink").click(function(){
			var personCodeOrIdNumber = jQuery("input[name=personCodeOrIdNumber]").val();
			jQuery("#hrExcelImportForm").attr("action","checkHrPersonFromExcel?popup=true&navTabId=${navTabId}&random=${random}&personCodeOrIdNumber="+personCodeOrIdNumber);
			jQuery("#hrExcelImportForm").submit();
		});
		jQuery("#hrExcelLoglink").click(function() {
			var msg = jQuery("#hrPersonImportcheckMsg").val();
			if(!msg){
				alertMsg.error("请先检查！");
				return ;
			}
			var url = "showHrImportDataLog?taskInterId=ImportHrPerson";
			var winTitle="导入日志";
			url = encodeURI(url);
			$.pdialog.open(url,"showImportDataLog",winTitle, {ifr:true,mask:true,resizable:true,maxable:true,width : 685,height : 450});
			stopPropagation();
		});
		jQuery("#hrExcelTemplelink").click(function(){
			window.location.href = "exportHrPersonExcelTemple";
		});
	});
	
	function personImportCallback(data){
		if(data){
		var msg = data.message;
		jQuery("#hrPersonImportcheckMsg").val(msg);
		}
		formCallBack(data);
	}
</script>
</head>

<div class="pageContent">
	<form id="hrExcelImportForm" method="post" action="" enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback(this,personImportCallback);">
		<div class="pageFormContent" layoutH="56">
		<div class="unit">
			<label>唯一标识类型：</label>
			<input type="radio" name="personCodeOrIdNumber" value="1" checked='checked'/>单位+人员编码
			<input type="radio" name="personCodeOrIdNumber" value="2"/>身份证号
			</div>
			<div class="unit">
				<label>请选择文件：</label>
				<s:file name="excelfile"></s:file> 
				<s:hidden id="hrPersonImportcheckMsg"></s:hidden>
<%-- 				<input name="navTabId" id="navTabId" type="hidden" value="${navTabId}"/> --%>
			</div>
			
		</div>
		<div class="formBar">
			<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="hrExcelTemplelink">导出模板</button>
							</div>
						</div>
					</li>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="hrExcelChecklink">检查</button>
							</div>
						</div>
					</li>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="hrExcelLoglink">日志</button>
							</div>
						</div>
					</li>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="hrExcelImportlink"><s:text name="button.save" /></button>
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





