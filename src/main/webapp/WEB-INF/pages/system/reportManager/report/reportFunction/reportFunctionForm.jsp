<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#reportFunctionForm").attr("action","saveReportFunction?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#reportFunctionForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="reportFunctionForm" method="post"	action="saveReportFunction?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:if test="%{entityIsNew}">
						<s:textfield key="reportFunction.code" cssClass="required" notrepeat='报表编码已存在' validatorParam="from DefineReport entity where entity.code=%value%"/>
					</s:if>
					<s:else>
						<s:textfield key="reportFunction.code" readonly="true" cssClass="required"/>
					</s:else>
				</div>
				<div class="unit">
				<s:textfield id="reportFunction_name" key="reportFunction.name" name="reportFunction.name" cssClass="required				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportFunction_category" key="reportFunction.category" name="reportFunction.category" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportFunction_dataType" key="reportFunction.dataType" name="reportFunction.dataType" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="reportFunction_params" key="reportFunction.params" name="reportFunction.params" cssClass="				
				
				       " style="width:350px;"/>
				</div>
				<div class="unit">
				<s:textarea id="reportFunction_funcSql" key="reportFunction.funcSql" name="reportFunction.funcSql" cssClass="				
				
				       " style="width:350px;height:100px"/>
				</div>
				<div class="unit">
					<s:hidden id="reportFunction_subSystem_id" name="reportFunction.subSystem"/>
					<label><s:text name="reportFunction.subSystem"/>:</label>
					<s:textfield id="reportFunction_subSystem" theme="simple"/>
					<script>
					jQuery("#reportFunction_subSystem").treeselect({
						optType : "single",
						dataType : 'sql',
						sql : "SELECT code id,menu.menuName name,'1' parent FROM sy_model m left join t_menu menu on m.menuId=menu.menuId",
						exceptnullparent : true,
						initSelect : "${reportFunction.subSystem}",
						lazy : false,
						minWidth : '280px',
						selectParent : false,
						callback : {
						}
					});
					</script>
				</div>
				<div class="unit">
				<s:textarea id="reportFunction_remark" key="reportFunction.remark" name="reportFunction.remark" cssClass="				
				
				       " style="width:350px;height:30px"/>
				</div>
				<%-- <div class="unit">
				<s:textfield id="reportFunction_rsType" key="reportFunction.rsType" name="reportFunction.rsType" cssClass="				
				
				       "/>
				</div> --%>
			
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





