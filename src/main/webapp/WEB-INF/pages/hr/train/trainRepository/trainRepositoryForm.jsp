<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#trainRepositoryForm").attr("action","saveTrainRepository?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#trainRepositoryForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="trainRepositoryForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="trainRepository.id"/>
				</div>
				<div class="unit">
				<s:textfield id="trainRepository_code" key="trainRepository.code" name="trainRepository.code" cssClass="required" maxlength="50"/>
				<s:textfield id="trainRepository_name" key="trainRepository.name" name="trainRepository.name" cssClass="" maxlength="20"/>
				</div>
				<div class="unit">
				<s:textfield id="trainRepository_contentClass" key="trainRepository.contentClass" name="trainRepository.contentClass" cssClass="" maxlength="20"/>
				<s:textfield id="trainRepository_classNumber" key="trainRepository.classNumber" name="trainRepository.classNumber" cssClass="" maxlength="20"/>
				</div>
				<div class="unit">
				<s:textfield id="trainRepository_timeliness" key="trainRepository.timeliness" name="trainRepository.timeliness" cssClass="" maxlength="20"/>
				<s:textfield id="trainRepository_type" key="trainRepository.type" name="trainRepository.type" cssClass="" maxlength="20"/>
				</div>
				<div class="unit">
				<s:textfield id="trainRepository_issueUnit" key="trainRepository.issueUnit" name="trainRepository.issueUnit" cssClass="" maxlength="50"/>
				<label style="float:left;white-space:nowrap"><s:text name='trainRepository.issueDate' />:</label><input
        						class="Wdate input-small" name="trainRepository.issueDate" type="text"
      							  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
       							 value="<fmt:formatDate value="${trainRepository.issueDate}" pattern="yyyy-MM-dd"/>"
      							  onFocus="WdatePicker({isShowClear:true,readOnly:true})" /> 
				</div>
				<div class="unit">
				<label style="float:left;white-space:nowrap"><s:text name='trainRepository.expiryDate' />:</label><input
        						class="Wdate input-small" name="trainRepository.expiryDate" type="text"
      							  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
       							 value="<fmt:formatDate value="${trainRepository.expiryDate}" pattern="yyyy-MM-dd"/>"
      							  onFocus="WdatePicker({isShowClear:true,readOnly:true})" />
      			<label style="float:left;white-space:nowrap"><s:text name='trainRepository.implementDate' />:</label><input
        						class="Wdate input-small" name="trainRepository.implementDate" type="text"
      							  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"
       							 value="<fmt:formatDate value="${trainRepository.implementDate}" pattern="yyyy-MM-dd"/>"
      							  onFocus="WdatePicker({isShowClear:true,readOnly:true})" />
				</div>
				<div class="unit">
				<s:textfield id="trainRepository_creditLine" key="trainRepository.creditLine" name="trainRepository.creditLine" cssClass="" maxlength="20"/>
				<s:textfield id="trainRepository_referenceNumber" key="trainRepository.referenceNumber" name="trainRepository.referenceNumber" cssClass="" maxlength="20"/>
				</div>
				<div class="unit">
						<s:textarea key="trainRepository.remark" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
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





