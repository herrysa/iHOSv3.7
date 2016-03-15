<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*判断工资类别是否结账*/
		if("${gzTypeStatus}" == "closed"){
			if("${gzType.issueType}"=="0"){
				jQuery("#gzType_issueType_value").val("月结");
			}else{
				jQuery("#gzType_issueType_value").val("次结");
			}
		}
			if("${oper}"=="view"){
				readOnlyForm("gzTypeForm");
				jQuery("#gzType_buttonActive").hide();
			}else if("${oper}"=="update"){
				var status = jQuery("#status").val();
				if(status == "1"){
					jQuery("#gzType_disabled").attr("disabled",true);
				}
			  }
		adjustFormInput("gzTypeForm");
	});
	/*保存*/
	function gzTypeFormSave(){
		var urlString = "saveGzType?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}";
// 		urlString += "&oper=initGzItem";
		jQuery("#gzTypeForm").attr("action",urlString);
		jQuery("#gzTypeForm").submit();
// 		if("${entityIsNew}" == "true"){
// 			alertMsg.confirm("初始化工资项数据？", {
// 	            okCall: function(){
// 	            	urlString += "&oper=initGzItem";
// 	            	jQuery("#gzTypeForm").attr("action",urlString);
// 	        		jQuery("#gzTypeForm").submit();     
// 	            	},
// 	            cancelCall:function(){
// 	            	jQuery("#gzTypeForm").attr("action",urlString);
// 	        		jQuery("#gzTypeForm").submit();
// 	            }
// 			});
// 		}else{
// 			jQuery("#gzTypeForm").attr("action",urlString);
//     		jQuery("#gzTypeForm").submit();  
// 		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="gzTypeForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="gzType.gzUserIds"/>
					<s:hidden key="gzType.disabled"/>
				</div>
				<div class="unit">
					<s:if test="%{!entityIsNew}">
						<s:textfield id= "gzType_gzTypeId" key="gzType.gzTypeId" name="gzType.gzTypeId" cssClass="" readonly="true"/>
						<s:textfield id="gzType_gzTypeName" key="gzType.gzTypeName" name="gzType.gzTypeName" cssClass="required" oldValue="'${gzType.gzTypeName}'" notrepeat='类别名称已存在' validatorParam="from GzType type where type.gzTypeName=%value%" />
					</s:if>
					<s:else>
						<s:textfield id= "gzType_gzTypeId" key="gzType.gzTypeId" name="gzType.gzTypeId" cssClass="required" notrepeat='类别代码已存在 ' validatorParam="from GzType type where type.gzTypeId=%value%"/>
						<s:textfield id="gzType_gzTypeName" key="gzType.gzTypeName" name="gzType.gzTypeName" cssClass="required" notrepeat='类别名称已存在' validatorParam="from GzType type where type.gzTypeName=%value%" />
					</s:else>
				</div>
				<div class="unit">
					<label><s:text name="gzType.issueType"></s:text></label>
					<span class="formspan" style="float: left; width: 140px">
						<s:if test="gzTypeStatus=='closed'">
							<s:hidden key="gzType.issueType"></s:hidden>
							<input type="text" id="gzType_issueType_value" readonly="readonly">
						</s:if>
						<s:else>
							<s:select name="gzType.issueType" headerKey=""   
								list="#{'0':'月结','1':'次结' }" listKey="key" listValue="value"
								emptyOption="false"  maxlength="20" width="20px">
							</s:select>
						</s:else>
					</span>
					<s:if test="gzTypeStatus=='closed'">
						<s:textfield id="gzType_issueDate" key="gzType.issueDate" name="gzType.issueDate" readonly="true"/>
					</s:if>
					<s:else>
						<s:textfield id="gzType_issueDate" key="gzType.issueDate" name="gzType.issueDate" cssClass="required"
						onclick = "javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}"/>
					</s:else>
				</div>
				<div class="unit">
					<s:if test="gzTypeStatus=='closed'">
						<s:textfield id="gzType_issueNumber" key="gzType.issueNumber" name="gzType.issueNumber" cssClass="digits" readonly="true"/>
					</s:if>
					<s:else>
						<label><s:text name="gzType.issueNumber"></s:text></label>
						<s:select name="gzType.issueNumber" headerKey=""
								list="#{'1':'1','2':'2','3':'3','4':'4','5':'5'
								,'6':'6','7':'7','8':'8','9':'9','10':'10','11':'11'
								,'12':'12','13':'13'}" listKey="key" listValue="value"
								emptyOption="false"  maxlength="20" width="20px">
							</s:select>
					</s:else>
				</div>
			 	<div class="unit">
					<s:textarea id="gzType_remark" key="gzType.remark" name="gzType.remark" cssClass="" 
					cssStyle="width:300px;height:50px;" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div id="gzType_buttonActive" class="buttonActive">
							<div class="buttonContent">
								<button type="Button" onclick="gzTypeFormSave()"><s:text name="button.save" /></button>
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





