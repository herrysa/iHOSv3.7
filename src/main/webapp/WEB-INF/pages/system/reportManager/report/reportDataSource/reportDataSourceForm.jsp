<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#reportDataSourceForm").attr("action","saveReportDataSource?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#reportDataSourceForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="reportDataSourceForm" method="post"	action="saveReportDataSource?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:textfield key="reportDataSource.code" required="true" cssClass="validate[required]"/>
				</div>
				<div class="unit">
				<s:textfield id="reportDataSource_dataSource" key="reportDataSource.dataSource" name="reportDataSource.dataSource" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportDataSource_dsDesc" key="reportDataSource.dsDesc" name="reportDataSource.dsDesc" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportDataSource_name" key="reportDataSource.name" name="reportDataSource.name" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportDataSource_remark" key="reportDataSource.remark" name="reportDataSource.remark" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportDataSource_reportId" key="reportDataSource.reportId" name="reportDataSource.reportId" cssClass="				
				
				       "/>
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit"><s:text name="button.save" /></button>
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





