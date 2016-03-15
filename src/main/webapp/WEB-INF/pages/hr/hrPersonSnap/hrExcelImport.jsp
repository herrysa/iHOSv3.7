<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#hrExcelImportlink').click(function() {
			if("${oper}"=="hrDept"){
				jQuery("#hrExcelImportForm").attr("action","importHrDeptFromExcel?popup=true&navTabId=${navTabId}");
			}else{
				jQuery("#hrExcelImportForm").attr("action","importHrPersonFromExcel?popup=true&navTabId=${navTabId}");
			}
			jQuery("#hrExcelImportForm").submit();
		});
	});
</script>
</head>

<div class="pageContent">
	<form id="hrExcelImportForm" method="post" action="" enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback(this,formCallBack);">
		<div class="pageFormContent" layoutH="56">
			<p>
				<label>请选择文件：</label>
				<s:file name="excelfile"></s:file> 
<%-- 				<input name="navTabId" id="navTabId" type="hidden" value="${navTabId}"/> --%>
			</p>
		</div>
		<div class="formBar">
			<ul>
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





