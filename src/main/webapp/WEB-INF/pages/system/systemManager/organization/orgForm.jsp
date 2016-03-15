<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#orgForm").attr("action","saveOrg?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#orgForm").submit();
		});
		jQuery("#org_type").val("组织");
	});
	
/* 	function validateOrgCode(obj){
		var parentCode = jQuery('#parentCode').val();
		var codeRule = "${codeRule}";
		if(!codeRule){
			return true;
		}
		if(codeRule){
			var orgCode = obj.value;
			var validateFatherCode = getFather(orgCode,codeRule);
			if(orgCode!=""){
				if(!isMatch(orgCode,codeRule)){
					alertMsg.error("单位编码格式不正确，请重新输入！");
					obj.value=parentCode;
					return;
				}else if(currentParentCode!=validateFatherCode){
					alertMsg.error("上级单位编码不正确");
		    		obj.value=parentCode;
		    		return;
				}
			}
		}
		checkId(obj,'Org','orgCode','单位编码已存在');
	}
	
	function saveOrgForm(){
		if('$(newEntity)'){
			if(validateOrgCode()){
				$('#orgForm').submit();
			} else {
				alertMsg.error("您输入的单位编码不正确，请重新输入！");
				return;
			}
		}
	} */
	
	
	
</script>
</head>
<div class="page">
	<div class="pageContent">
		<form id="orgForm" method="post" action="saveOrg?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate" onsubmit="return validateCallback(this,refreshOrgTreeBack);">
			<div class="pageFormContent" layoutH="56">
				<s:if test="%{entityIsNew}">
					<s:hidden key="org.disabled"/>
<%-- 					<s:if test="%{codeRule!='' && codeRule!=null}"> --%>
<!-- 						<div class="unit" id="orgCodeInputDiv"> -->
<!-- 							<span style="margin-left:87px;line-height:45px;"> -->
<%-- 								规则:${codeRule } --%>
<!-- 							</span> -->
<!-- 						</div> -->
<%-- 					</s:if> --%>
				</s:if>
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield id="orgCodeNew" key="org.orgCode" name="org.orgCode" cssClass="required" 
						notrepeat='单位编码已存在' validatorParam="from Org org where org.orgCode=%value%"/>
						<s:textfield id="org_orgname" key="org.orgname" name="org.orgname" cssClass="required"
						notrepeat='单位名称已存在' validatorParam="from Org org where org.orgname=%value%"/>
					</s:if>
					<s:else>
						<s:textfield id="orgCodeNew" key="org.orgCode" name="org.orgCode" readonly="true" cssClass="textInput readonly"/>
						<s:textfield id="org_orgname" key="org.orgname" name="org.orgname" cssClass="required" oldValue="'${org.orgname}'"
						notrepeat='单位名称已存在' validatorParam="from Org org where org.orgname=%value%"/>
					</s:else>
				</div>
				<div class="unit">
				<s:textfield id="org_ownerOrg" key="org.ownerOrg"  readonly="true" name="org.parentOrgCode.orgname" cssClass=""/>
				<input type="hidden"  name="org.parentOrgCode.orgCode" value="${org.parentOrgCode.orgCode }"/>
				<input type="hidden" id="parentCode" name="parentCode" value="${org.parentOrgCode.orgCode }"/>
				<input type="hidden" name="org.ownerOrg" value="${org.parentOrgCode.orgname }"/>
				<s:textfield id="org_shortName" key="org.shortName" name="org.shortName" cssClass=""/>
				</div>
				<div class="unit">
				<s:textfield id="org_type" key="org.type" name="org.type" cssClass="required"/>
				<s:textfield id="org_internal" key="org.internal" name="org.internal" cssClass=""/>
				</div>
				<div class="unit">
				<s:textfield id="org_contact" key="org.contact" name="org.contact" cssClass=""/>
				<s:textfield id="org_contactPhone" key="org.contactPhone" name="org.contactPhone" cssClass=""/>
				</div>
				<div class="unit">
				<s:textfield id="org_homePage" key="org.homePage" name="org.homePage" cssClass=""/>
				<s:textfield id="org_invalidDate" key="org.invalidDate" name="org.invalidDate" cssClass=""/>
				</div>
				<div class="unit">
				<s:textfield id="org_email" key="org.email" name="org.email" cssClass="email"/>
				<s:textfield id="org_fax" key="org.fax" name="org.fax" cssClass=""/>
				</div>
				<div class="unit">
				<s:textfield id="org_phone" key="org.phone" name="org.phone" cssClass="phone"/>
				<s:textfield id="org_address" key="org.address" name="org.address" cssClass=""/>
				</div>
				<div class="unit">
					<s:textfield key="org.postCode" name="org.postCode" cssClass="postcode"/>
					<label ><s:text name="org.disabled"></s:text>:</label>
					<s:checkbox id="org_disabled" key="org.disabled" name="org.disabled" theme="simple"/>
				</div>
				<div class="unit">
				<s:textarea id="org_note" key="org.note" name="org.note" rows="3" cols="55" cssClass=""/>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button"  id="savelink" onclick=""><s:text name="button.save" /></button>
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





