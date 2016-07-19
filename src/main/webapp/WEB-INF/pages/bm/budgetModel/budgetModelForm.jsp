<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#budgetModelForm").attr("action","saveBudgetModel?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#budgetModelForm").submit();
		});*/
		tabResize();
	});
</script>
</head>

<div class="page" id="bmModelFormPage" style="height:100%">
	<div class="pageContent" style="height:100%">
		<div class="tabs" currentIndex="0" eventType="click" id="bmModelFormTabs" tabcontainer="bmModelFormPage" extraHeight=10 extraWidth=2>
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="#"><span>主要信息</span> </a></li>
						<li><a href="budgetDepartmentList?modelId=${budgetModel.modelId}" class="j-ajax" ><span>预算部门</span> </a></li>
						<li><a href="modelProcessList?modelId=${budgetModel.modelId}" class="j-ajax" ><span>审核流程</span> </a></li>
					</ul>
				</div>
			</div>
			<div id="bmDeptTabsContent" class="tabsContent"
				style="height: 250px;padding:0px">
				<div>
					<form id="budgetModelForm" method="post"	action="saveBudgetModel?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="130">
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield key="budgetModel.modelId" cssClass="required" notrepeat='预算模板ID已存在' validatorParam="from BudgetModel entity where entity.modelId=%value%"/>
					</s:if>
					<s:else>
						<s:textfield key="budgetModel.modelId" readonly="true" cssClass="required"/>
					</s:else>
				</div>
				<div class="unit">
					<s:if test="%{entityIsNew}">
				<s:textfield id="budgetModel_modelCode" key="budgetModel.modelCode" name="budgetModel.modelCode" notrepeat='预算模板编码已存在' validatorParam="from BudgetModel entity where entity.modelCode=%value%" cssClass="	required			
				
				       "/>
					</s:if>
					<s:else>
				<s:textfield id="budgetModel_modelCode" key="budgetModel.modelCode" name="budgetModel.modelCode" oldValue="${budgetModel.modelCode}" notrepeat='预算模板编码已存在' validatorParam="from BudgetModel entity where entity.modelCode=%value%" cssClass="	required			
				
				       "/>
					</s:else>
				</div>
				<div class="unit">
				<s:textfield id="budgetModel_modelName" key="budgetModel.modelName" name="budgetModel.modelName" style="width:350px;" cssClass="	required			
				
				       "/>
				</div>
				<div class="unit">
					<label><s:text name="budgetModel.modelType"/>:</label>
					<s:select list="modelTypes" key="budgetModel.modelType" listKey="displayContent" listValue="value" headerKey="" headerValue="--" theme="simple"></s:select>
				</div>
				<div class="unit">
					<label><s:text name="budgetModel.periodType"/>:</label>
					<s:select list="#{'月度':'月度','季度':'季度','半年':'半年','年度':'年度'}" key="budgetModel.periodType" headerKey="" headerValue="--" theme="simple"></s:select>
				</div>
				<div class="unit">
				<s:hidden id="budgetModel_dept_id"  name="budgetModel.department"/>
				<label><s:text name="budgetModel.department"/>:</label>
				<s:textarea id="budgetModel_dept" style="width:350px;height:30px" cssClass="				
				
				       "/>
				<script>
					jQuery("#budgetModel_dept").treeselect({
						optType : "multi",
						dataType : 'sql',
						sql : "SELECT deptid id,name,parentDept_id parent FROM t_department where disabled=0 ORDER BY internalCode asc",
						exceptnullparent : true,
						initSelect : "${budgetModel.department}",
						lazy : false,
						minWidth : '180px',
						selectParent : false,
						callback : {
						}
					});
					</script>
				</div>
				<div class="unit">
					<label><s:text name="budgetModel.disabled"/>:</label>
					<s:checkbox id="budgetModel_disabled" key="budgetModel.disabled" name="budgetModel.disabled" theme="simple"/>
				</div>
				<div class="unit">
				<s:textarea id="budgetModel_remark" key="budgetModel.remark" name="budgetModel.remark" style="width:350px;height:50px" cssClass="				
				
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
				<div></div>
				<div></div>
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
	</div>
</div>





