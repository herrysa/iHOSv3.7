<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#${random}savelink').click(function() {
			jQuery("#jjUpdataDefColumnForm").attr("action","saveJjUpdataDefColumn?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#jjUpdataDefColumnForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="jjUpdataDefColumnForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="jjUpdataDefColumn.columnId"/>
				</div>
				<div class="unit">
				<s:textfield id="jjUpdataDefColumn_columnName" key="jjUpdataDefColumn.columnName" name="jjUpdataDefColumn.columnName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="jjUpdataDefColumn_columnType" key="jjUpdataDefColumn.columnType" name="jjUpdataDefColumn.columnType" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:checkbox id="jjUpdataDefColumn_disable" key="jjUpdataDefColumn.disable" name="jjUpdataDefColumn.disable" />
				</div>
				<div class="unit">
				<s:textfield id="jjUpdataDefColumn_formula" key="jjUpdataDefColumn.formula" name="jjUpdataDefColumn.formula" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="jjUpdataDefColumn_title" key="jjUpdataDefColumn.title" name="jjUpdataDefColumn.title" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="jjUpdataDefColumn_type" key="jjUpdataDefColumn.type" name="jjUpdataDefColumn.type" cssClass="				
				
				       "/>
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink"><s:text name="button.save" /></button>
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





