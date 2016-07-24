<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#defineReportForm").attr("action","saveDefineReport?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#defineReportForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="defineReportForm" method="post"	action="saveDefineReport?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield key="defineReport.code" cssClass="required" notrepeat='报表编码已存在' validatorParam="from DefineReport entity where entity.code=%value%"/>
					</s:if>
					<s:else>
						<s:textfield key="defineReport.code" readonly="true" cssClass="required"/>
					</s:else>
				</div>
				<div class="unit">
				<s:textfield id="defineReport_name" key="defineReport.name" name="defineReport.name" cssClass="				
				required
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="defineReport_type" key="defineReport.type" name="defineReport.type" cssClass="				
				
				       "/>
				<div class="unit">
				<s:textfield id="defineReport_searchName" key="defineReport.searchName" name="defineReport.searchName" cssClass="				
				
				       " style="width:300px;"/>
				<script>
					/* jQuery("#defineReport_searchName").treeselect({
						optType : "multi",
						dataType : 'sql',
						idId : 'defineReport_searchName',
						sql : "SELECT searchName id,title name,1 parent FROM t_search ORDER BY searchId asc",
						exceptnullparent : true,
						initSelect : "${defineReport.searchName}",
						lazy : false,
						minWidth : '180px',
						selectParent : false,
						callback : {
						}
					}); */
					</script>
				       </div>
				<div class="unit">
				<s:textarea id="defineReport_remark" key="defineReport.remark" name="defineReport.remark" style="width:300px;height:50px" cssClass="				
				
				       "/>
				</div>
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





