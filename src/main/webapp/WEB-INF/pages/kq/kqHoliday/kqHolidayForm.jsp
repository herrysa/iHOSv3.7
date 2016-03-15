<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#kqHolidayForm").attr("action","saveKqHoliday?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#kqHolidayForm").submit();
		});*/
	});
	function kqHolidayFormSaveCallBack(data) {
		formCallBack(data);
		reloadKqHolidayGrid();
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="kqHolidayForm" method="post"	action="saveKqHoliday?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,kqHolidayFormSaveCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="kqHoliday.holidayId"/>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<s:textfield id="kqHoliday_holidayCode" key="kqHoliday.holidayCode" name="kqHoliday.holidayCode" cssClass="" readonly="true"/>
				<s:textfield id="kqHoliday_holidayName" key="kqHoliday.holidayName" name="kqHoliday.holidayName" cssClass="required"  oldValue="'${kqHoliday.holidayName}'" notrepeat='名称已存在 ' validatorParam="from KqHoliday temp where temp.holidayName=%value%"/>
				</s:if>
				<s:else>
				<s:textfield id="kqHoliday_holidayCode" key="kqHoliday.holidayCode" name="kqHoliday.holidayCode" cssClass="required" notrepeat='编码已存在 ' validatorParam="from KqHoliday temp where temp.holidayCode=%value%"/>
				<s:textfield id="kqHoliday_holidayName" key="kqHoliday.holidayName" name="kqHoliday.holidayName" cssClass="required" notrepeat='名称已存在 ' validatorParam="from KqHoliday temp where temp.holidayName=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<label><s:text name="kqHoliday.beginDate" />:</label>
				<input id="kqHoliday_beginDate" name="kqHoliday.beginDate" type="text" class="Wdate" style="height:15px"
						onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'kqHoliday_endDate\')}'})"
						value="<fmt:formatDate value="${kqHoliday.beginDate}" pattern="yyyy-MM-dd"/>"
						onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'kqHoliday_endDate\')}'})" />	
				<label style="float:left;white-space:nowrap"><s:text name='kqHoliday.endDate' />:</label>
				<input id="kqHoliday_endDate" name="kqHoliday.endDate" type="text" class="Wdate" style="height:15px"
						onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'kqHoliday_beginDate\')}'})"
						value="<fmt:formatDate value="${kqHoliday.endDate}" pattern="yyyy-MM-dd"/>"
						onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'kqHoliday_beginDate\')}'})" />	
				</div>
				<div class="unit">
				<s:textfield id="kqHoliday_dayNumber" key="kqHoliday.dayNumber" value="1.0" name="kqHoliday.dayNumber" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<label><s:text name="kqHoliday.requirement"></s:text>:
				<label><input type="button" value="公式设置" style="color: #15428B;padding:0px;margin-right: 3px;"/></label></label>
				<s:textarea id="kqHoliday_requirement" name="kqHoliday.requirement" cssStyle="width:300px;height: 65px;"></s:textarea>
				</div>
				<div class="unit">
				<label><s:text name="kqHoliday.holidayDesc"></s:text>:</label>
				<s:textarea id="kqHoliday_holidayDesc" name="kqHoliday.holidayDesc" cssStyle="width:300px;height: 40px;"></s:textarea>
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





