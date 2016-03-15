<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#kqTypeForm").attr("action","saveKqType?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#kqTypeForm").submit();
		});*/
	});
	/*保存*/
	function kqTypeFormSave(){
		var urlString = "saveKqType?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}";
		if("${entityIsNew}" == "true"){
			alertMsg.confirm("初始化考勤项数据？", {
	            okCall: function(){
	            	urlString += "&oper=initKqUpItem";
	            	jQuery("#kqTypeForm").attr("action",urlString);
	        		jQuery("#kqTypeForm").submit();     
	            	},
	            cancelCall:function(){
	            	jQuery("#kqTypeForm").attr("action",urlString);
	        		jQuery("#kqTypeForm").submit();
	            }
			});
		}else{
			jQuery("#kqTypeForm").attr("action",urlString);
    		jQuery("#kqTypeForm").submit();  
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="kqTypeForm" method="post"	action="saveKqType?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="kqType.kqTypeId"/>
				</div>
				<div class="unit">
					<s:if test="%{!entityIsNew}">
						<s:textfield id= "kqType_kqTypeCode" key="kqType.kqTypeCode" name="kqType.kqTypeCode" cssClass="" readonly="true"/>
					</s:if>
					<s:else>
						<s:textfield id= "kqType_kqTypeCode" key="kqType.kqTypeCode" name="kqType.kqTypeCode" cssClass="required" notrepeat='类别编码已存在 ' validatorParam="from KqType type where type.kqTypeCode=%value%"/>
					</s:else>
				</div>
				<div class="unit">
					<s:if test="%{!entityIsNew}">
						<s:textfield id="kqType_kqTypeName" key="kqType.kqTypeName" name="kqType.kqTypeName" cssClass="required" oldValue="'${kqType.kqTypeName}'" notrepeat='类别名称已存在' validatorParam="from KqType type where type.kqTypeName=%value%" />
					</s:if>
					<s:else>
						<s:textfield id="kqType_kqTypeName" key="kqType.kqTypeName" name="kqType.kqTypeName" cssClass="required" notrepeat='类别名称已存在' validatorParam="from KqType type where type.kqTypeName=%value%" />
					</s:else>
				</div>
				<!--<div class="unit">
					<label><s:text name='kqType.disabled'/>:</label>
					<s:select list="#{'':'--','true':'禁用','false':'启用'}" name="kqType.disabled"  listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px" cssClass="required textInput"></s:select>
				</div>-->
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="kqTypeFormSave()"><s:text name="button.save" /></button>
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





