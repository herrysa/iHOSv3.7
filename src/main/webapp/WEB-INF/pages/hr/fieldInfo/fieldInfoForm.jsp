<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#fieldInfoForm").attr("action","saveFieldInfo?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#fieldInfoForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="fieldInfoForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="fieldInfo.id"/>
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_ascDesc" key="fieldInfo.ascDesc" name="fieldInfo.ascDesc" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:checkbox id="fieldInfo_batchEdit" key="fieldInfo.batchEdit" name="fieldInfo.batchEdit" />
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="fieldInfo.bdinfo.id" list="bdinfoList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_changeDate" key="fieldInfo.changeDate" name="fieldInfo.changeDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="fieldInfo.changeUser.id" list="changeUserList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_code" key="fieldInfo.code" name="fieldInfo.code" cssClass="required 				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_codeId" key="fieldInfo.codeId" name="fieldInfo.codeId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_codeName" key="fieldInfo.codeName" name="fieldInfo.codeName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_dataDecimal" key="fieldInfo.dataDecimal" name="fieldInfo.dataDecimal" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_dataLength" key="fieldInfo.dataLength" name="fieldInfo.dataLength" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_dataType" key="fieldInfo.dataType" name="fieldInfo.dataType" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:checkbox id="fieldInfo_disabled" key="fieldInfo.disabled" name="fieldInfo.disabled" />
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_fieldDefault" key="fieldInfo.fieldDefault" name="fieldInfo.fieldDefault" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_groupSn" key="fieldInfo.groupSn" name="fieldInfo.groupSn" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_name" key="fieldInfo.name" name="fieldInfo.name" cssClass="required 				
				
				       "/>
				</div>
				<div class="unit">
				<s:checkbox id="fieldInfo_notNull" key="fieldInfo.notNull" name="fieldInfo.notNull" />
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_orderBySn" key="fieldInfo.orderBySn" name="fieldInfo.orderBySn" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_orgCode" key="fieldInfo.orgCode" name="fieldInfo.orgCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:checkbox id="fieldInfo_read" key="fieldInfo.read" name="fieldInfo.read" />
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_remark" key="fieldInfo.remark" name="fieldInfo.remark" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="fieldInfo_sn" key="fieldInfo.sn" name="fieldInfo.sn" cssClass="				
				digits
				       "/>
				</div>
				<div class="unit">
				<s:checkbox id="fieldInfo_statistics" key="fieldInfo.statistics" name="fieldInfo.statistics" />
				</div>
				<div class="unit">
				<s:checkbox id="fieldInfo_sumFlag" key="fieldInfo.sumFlag" name="fieldInfo.sumFlag" />
				</div>
				<div class="unit">
				<s:checkbox id="fieldInfo_sysFiled" key="fieldInfo.sysFiled" name="fieldInfo.sysFiled" />
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





