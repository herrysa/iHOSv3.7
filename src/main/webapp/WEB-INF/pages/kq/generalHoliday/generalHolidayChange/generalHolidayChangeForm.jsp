<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#generalHolidayChangeForm").attr("action","saveGeneralHolidayChange?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#generalHolidayChangeForm").submit();
		});*/
	});
	function generalHolidayChangeFormSaveCallBack(data) {
		formCallBack(data);
		reloadGeneralHolidayChangeGrid();
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="generalHolidayChangeForm" method="post"	action="saveGeneralHolidayChange?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,generalHolidayChangeFormSaveCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="generalHolidayChange.changeId"/>
				</div>
				<div class="unit">
				<label><s:text name="generalHolidayChange.oldDate" />:</label>
				<input id="generalHolidayChange_oldDate" name="generalHolidayChange.oldDate" type="text" class="Wdate required" style="height:15px"
					onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
					value="<fmt:formatDate value="${generalHolidayChange.oldDate}" pattern="yyyy-MM-dd"/>"
					onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />
				</div>
				<div class="unit">
				<label><s:text name="generalHolidayChange.newDate" />:</label>
				<input id="generalHolidayChange_newDate" name="generalHolidayChange.newDate" type="text" class="Wdate required" style="height:15px"
					onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"
					value="<fmt:formatDate value="${generalHolidayChange.newDate}" pattern="yyyy-MM-dd"/>"
					onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true})" />
				</div>
				<div class="unit">
				<label><s:text name="generalHolidayChange.changeDesc" />:</label>
				<s:textarea id="generalHolidayChange_changeDesc" name="generalHolidayChange.changeDesc" cssStyle="width:175px;height: 40px;"></s:textarea>
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





