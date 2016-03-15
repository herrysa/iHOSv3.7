<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#trainRecordDetailForm").attr("action","saveTrainRecordDetail?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#trainRecordDetailForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="trainRecordDetailForm" method="post"	action="saveTrainRecordDetail?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="trainRecordDetail.id"/>
				</div>
				<div class="unit">
				<s:textfield id="trainRecordDetail_courseName" key="trainRecordDetail.courseName" name="trainRecordDetail.courseName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="trainRecordDetail_personIds" key="trainRecordDetail.personIds" name="trainRecordDetail.personIds" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="trainRecordDetail_personNames" key="trainRecordDetail.personNames" name="trainRecordDetail.personNames" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="trainRecordDetail_trainDate" key="trainRecordDetail.trainDate" name="trainRecordDetail.trainDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="trainRecordDetail_trainHour" key="trainRecordDetail.trainHour" name="trainRecordDetail.trainHour" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="trainRecordDetail.trainRecord.id" list="trainRecordList" listKey="id" listValue="id"></s:select> -->
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
</div>





