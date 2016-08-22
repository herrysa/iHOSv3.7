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
		if("${budgetModel.disabled}"=="false"){
			jQuery("input","#budgetModelForm").attr("readOnly","true");
			jQuery("select","#budgetModelForm").attr("disabled","true");
			jQuery("textarea","#budgetModelForm").attr("readOnly","true");
			jQuery("input[type=checkbox]","#budgetModelForm").attr("disabled","true");
		}
		tabResize();
	});
</script>
</head>

<div class="page" id="bmModelFormPage" style="height:100%">
	<div class="pageContent" style="height:100%;overflow: hidden;">
		<div class="tabs" currentIndex="0" eventType="click" id="bmModelFormTabs" tabcontainer="bmModelFormPage" extraHeight=1 extraWidth=2>
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
			<div class="pageFormContent" layoutH="93">
				<div class="unit">
					<s:if test="modelType=='copy'">
						<s:hidden id="budgetModel_modelId" key="budgetModel.modelId"/>
						<s:textfield key="budgetModel.modelCopyId" cssClass="required" notrepeat='预算模板ID已存在' oldValue="${budgetModel.modelId}" validatorParam="from BudgetModel entity where entity.modelId=%value%"/>
					</s:if>
					<s:else>
						<s:if test="%{entityIsNew}">
							<s:textfield key="budgetModel.modelId" cssClass="required" notrepeat='预算模板ID已存在' validatorParam="from BudgetModel entity where entity.modelId=%value%"/>
						</s:if>
						<s:else>
							<s:textfield key="budgetModel.modelId"  readOnly="true" cssClass="required"/>
						</s:else>
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
					<s:select list="#{'1':'科室填报(自下而上)','2':'预算汇总(自下而上)','3':'职能代编(自上而下)'}" key="budgetModel.modelType" headerKey="" headerValue="--" theme="simple"></s:select>
				</div>
				<div class="unit">
					<label><s:text name="budgetModel.periodType"/>:</label>
					<s:select list="#{'月度':'月度','季度':'季度','半年':'半年','年度':'年度'}" key="budgetModel.periodType" headerKey="" headerValue="--" theme="simple"></s:select>
				</div>
				<%-- <div class="unit">
					<label>年度:</label>
					<s:select list="periodYearList" key="budgetModel.periodYear" theme="simple"></s:select>
				</div> --%>
				<%-- <div class="unit">
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
				</div> --%>
				<div class="unit">
					<label><s:text name="budgetModel.isHz"/>:</label>
					<s:checkbox id="budgetModel_isHz" key="budgetModel.isHz" name="budgetModel.isHz" theme="simple"/>
					<script>
						var isHzChecked = jQuery("#budgetModel_isHz").attr("checked");
						if(isHzChecked=="checked"){
							jQuery("#budgetModel_hzModelId_div").show();
						}else{
							jQuery("#budgetModel_hzModelId_div").hide();
						}
						jQuery("#budgetModel_isHz").click(function(){
							var checked = jQuery(this).attr("checked");
							if(checked=="checked"){
								jQuery("#budgetModel_hzModelId_div").show();
							}else{
								jQuery("#budgetModel_hzModelId_div").hide();
							}
						});
					</script>
				</div>
				<div id="budgetModel_hzModelId_div" class="unit">
					<s:hidden id="budgetModel_hzModelId_id" name="budgetModel.hzModelId.modelId"/>
					<s:textfield id="budgetModel_hzModelId" key="budgetModel.hzModelId" name="budgetModel.hzModelId.modelName" style="width:350px"/>
					<script>
					jQuery("#budgetModel_hzModelId").treeselect({
						optType : "single",
						dataType : 'sql',
						sql : "SELECT modelId id,modelCode+' '+modelName name,'1' parent FROM bm_model where modelId<>'${budgetModel.modelId}' and disabled=0 ORDER BY modelCode asc",
						exceptnullparent : true,
						initSelect : "${budgetModel.hzModelId.modelId}",
						lazy : false,
						minWidth : '280px',
						selectParent : false,
						callback : {
						}
					});
					</script>
				</div>
				<%-- <s:if test="%{!entityIsNew}">
				<div class="unit">
					<label><s:text name="budgetModel.disabled"/>:</label>
					<s:checkbox id="budgetModel_disabled" key="budgetModel.disabled" name="budgetModel.disabled" theme="simple"/>
				</div>
				</s:if> --%>
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
								<button type="Button" id="cancelBmModelForm"><s:text name="button.cancel" /></button>
								<script>
								jQuery("#cancelBmModelForm").click(function(){
									var modelType = "${modelType}";
									if(modelType=='copy'){
									var id = jQuery("#budgetModel_modelId").val();
									$.post("delBudgetModel?navTabId=budgetModel_gridtable&modelId="+id,{},function(data){
										$.pdialog.closeCurrent();
									});
									}else{
										$.pdialog.closeCurrent();
									}
								});
								</script>
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





