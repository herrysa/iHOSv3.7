<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#generalHolidayForm").attr("action","saveGeneralHoliday?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#generalHolidayForm").submit();
		});*/
	});
	function generalHolidayFormSaveCallBack(data) {
		formCallBack(data);
		reloadGeneralHolidayGrid();
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="generalHolidayForm" method="post"	action="saveGeneralHoliday?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,generalHolidayFormSaveCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="generalHoliday.holidayId"/>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="generalHoliday_dateCode" key="generalHoliday.dateCode" name="generalHoliday.dateCode" cssClass="" readonly="true"/>
				</s:if>
				<s:else>
				<s:textfield id="generalHoliday_dateCode" key="generalHoliday.dateCode" name="generalHoliday.dateCode" cssClass="required" notrepeat='日期序号已存在 ' validatorParam="from GeneralHoliday g where g.dateCode=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="generalHoliday_number" key="generalHoliday.number" name="generalHoliday.number" cssClas="required"/>
				</s:if>
				<s:else>
				<s:textfield id="generalHoliday_number" value="1.0" key="generalHoliday.number" name="generalHoliday.number" cssClas="required"/>
				</s:else>
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





