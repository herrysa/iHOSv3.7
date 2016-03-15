<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#copyForm").attr("action","saveCopy?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#copyForm").submit();
		});
	});
	
	function validateCopyCode(obj){
		var codeRule = "${codeRule}";
		if(!codeRule){
			return true;
		}
		if(codeRule){
			var copyCode = obj.value;
			var validateFatherCode = getFather(copyCode,codeRule);
			if(copyCode!=""){
				if(!isMatch(copyCode,codeRule)){
					alertMsg.error("帐套编码格式不正确，请重新输入！");
					obj.value="";
					return;
				}
			}
		}
		//判断 同一单位下的帐套编码是否重复
		jQuery.ajax({
		    url: 'checkCopyCode',
		    data: {copycode:obj.value,orgCode:"${copy.org.orgCode }"},
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		       // alert("failed");
		    },
		    success: function(data){
		    	if(data.exists){
		    		alertMsg.error("当前单位下帐套编码重复，请重新输入！！");
					obj.value="";
		    	}
		    }
		});
	}
	
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="copyForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield key="copy.copycode" id="copyCodeNew" name="copy.copycode" cssClass="required" onblur="validateCopyCode(this)"/>
						<s:if test="%{codeRule!='' && codeRule!=null}">
							<span style="line-height:20px;">&nbsp;&nbsp;规则:${codeRule }</span>
						</s:if>
					</s:if>
					<s:else>
						<s:textfield key="copy.copycode" id="copyCodeNew" name="copy.copycode" cssClass="required" readonly="true"/>
					</s:else>
				</div>
				<div class="unit">
				<s:textfield id="copy_copyname" key="copy.copyname" name="copy.copyname" cssClass="required"/>
				</div>
				<div class="unit">
				<s:textfield id="copy_copyshort" key="copy.copyshort" name="copy.copyshort"/>
				</div>
				<div class="unit">
				<s:textfield id="copy_cwmanager" key="copy.cwmanager" name="copy.cwmanager" cssClass=""/>
				</div>
				<div class="unit">
				<s:textfield id="copy_orgCode" key="copy.org.orgname" readonly="true" name="copy.org.orgname" cssClass="required"/>
				<input type="hidden" name="copy.org.orgCode" value="${copy.org.orgCode }" />
				</div>
				<div class="unit">
					<s:select list="planList" key="copy.periodPlan.planName" listKey="planId" listValue="planName" name="copy.periodPlan.planId" value="copy.periodPlan.planId"></s:select>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savelink"><s:text name="button.save" /></button>
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





