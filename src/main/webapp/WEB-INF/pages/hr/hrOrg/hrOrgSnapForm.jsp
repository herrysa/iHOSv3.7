<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		if("${oper}"=="view"){
			readOnlyForm("hrOrgSnapForm");
		}
		/*jQuery('#savelink').click(function() {
			jQuery("#hrOrgSnapForm").attr("action","saveHrOrgSnap?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#hrOrgSnapForm").submit();
		});*/
	});
	
	function saveHrOrgCallBack(data){
		formCallBack(data);
		if(data.statusCode == 200){
			var hrOrgTreeObj = $.fn.zTree.getZTreeObj("hrOrgTree");
			var pOrgCode = data.parentOrgCode;
			var hrOrgSnap = data.hrOrgSnap;
			if('${entityIsNew}'=="true"){
				if(!pOrgCode){
					pOrgCode = "-1";
				}
				var pNode = hrOrgTreeObj.getNodeByParam("id", pOrgCode, null);
				var orgIcon = "${ctx}/scripts/zTree/css/zTreeStyle/img/diy/1_close.png";
				var newNode = {id:hrOrgSnap.orgCode,pId:pOrgCode,name:hrOrgSnap.orgname,isParent:false,icon:orgIcon,code:hrOrgSnap.orgCode,nameWithoutPerson:hrOrgSnap.orgname,subSysTem:'ORG'};
				hrOrgTreeObj.addNodes(pNode, newNode,false);
			}else{
				var oldOrgName = "${hrOrgSnap.orgname}";
				var newOrgName = hrOrgSnap.orgname;
				if(oldOrgName != newOrgName){
					var orgNodeId = "${hrOrgSnap.orgCode}";
					var updateNode = hrOrgTreeObj.getNodeByParam("id", orgNodeId, null);
					var showCode = jQuery("#hrOrg_showCode").attr("checked");
					if(showCode){
						updateNode.name = updateNode.code+' '+newOrgName;
					}else{
						updateNode.name = newOrgName;
					}
					updateNode.nameWithoutPerson = newOrgName;					
					hrOrgTreeObj.updateNode(updateNode);
					
					var pNode = updateNode.getParentNode();
					hrOrgTreeObj.expandNode(pNode, true, false, true);
					hrOrgTreeObj.selectNode(updateNode);
				}
			}
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="hrOrgSnapForm" method="post"	action="saveHrOrgSnap?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,saveHrOrgCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="hrOrgSnap.snapId"/>
					<s:hidden key="hrOrgSnap.snapCode"/>
					<s:hidden key="hrOrgSnap.deleted"/>
					<s:hidden key="hrOrgSnap.disabled"/>
					<s:hidden key="hrOrgSnap.invalidDate"/>
					<s:hidden key="hrOrgSnap.parentSnapCode"/>
					<s:hidden key="hrOrgSnap.type"/>
					<s:hidden key="hrOrgSnap.parentOrgCode"/>
				</div>
				<div class="unit">
					<s:if test="%{entityIsNew}">
						<s:textfield key="hrOrgSnap.orgCode" name="hrOrgSnap.orgCode" cssClass="required" maxLength="10" 
						notrepeat='单位编码已存在' validatorParam="from HrOrg org where org.orgCode=%value%"/>
						<s:textfield key="hrOrgSnap.orgname" name="hrOrgSnap.orgname" cssClass="required" maxLength="50"
						notrepeat='单位名称已存在' validatorParam="from HrOrg org where org.orgname=%value%"/>
					</s:if>
					<s:else>
						<s:textfield key="hrOrgSnap.orgCode" name="hrOrgSnap.orgCode" cssClass="required" readonly="true"/>
						<s:textfield key="hrOrgSnap.orgname" name="hrOrgSnap.orgname" cssClass="required" maxLength="50" oldValue="'${hrOrgSnap.orgname}'"
						notrepeat='单位名称已存在' validatorParam="from HrOrg org where org.orgname=%value%"/>
					</s:else>
				</div>
				<div class="unit">
					<s:textfield key="hrOrgSnap.ownerOrg" name="hrOrgSnap.ownerOrg" cssClass="readonly" readonly="true"/>
					<s:textfield key="hrOrgSnap.shortName" name="hrOrgSnap.shortName" maxLength="30"/>
				</div>
				<div class="unit">
					<s:textfield key="hrOrgSnap.internal" name="hrOrgSnap.internal" maxLength="20"/>
					<s:textfield key="hrOrgSnap.homePage" name="hrOrgSnap.homePage" maxLength="30" cssClass="url"/>
				</div>
				<div class="unit">
					<s:textfield key="hrOrgSnap.contact" name="hrOrgSnap.contact" maxLength="20"/>
					<s:textfield key="hrOrgSnap.contactPhone" name="hrOrgSnap.contactPhone" maxLength="20"/>
				</div>
				<div class="unit">
					<s:textfield key="hrOrgSnap.email" name="hrOrgSnap.email" maxLength="20" cssClass="email"/>
					<s:textfield key="hrOrgSnap.fax" name="hrOrgSnap.fax" maxLength="20"/>
				</div>
				<div class="unit">
					<s:textfield key="hrOrgSnap.phone" name="hrOrgSnap.phone" maxLength="20"/>
					<s:textfield key="hrOrgSnap.postCode" name="hrOrgSnap.postCode" maxLength="6" cssClass="postcode"/>
				</div>
				<div class="unit">
					<s:textarea key="hrOrgSnap.address" name="hrOrgSnap.address" maxLength="100" cssStyle="width:300px;height:30px;"/>
				</div>
				<div class="unit">
					<s:textarea key="hrOrgSnap.note" name="hrOrgSnap.note" cssStyle="width:300px;height:40px;" maxLength="200"/>
				</div>
				<s:if test="hrOrgSnap.disabled==true">
     				<div style="position:absolute;left:520px;top:10px;padding:2px;z-index:100;border-style: solid;border-width:1px; border-color:red">
      				<span style='color:red;font-size:12px'>已停用</span>
     				</div>
    			</s:if>
				
			</div>
			<div class="formBar">
				<ul>
					<li id="hrOrgSnapFormSaveButton"><div class="buttonActive">
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





