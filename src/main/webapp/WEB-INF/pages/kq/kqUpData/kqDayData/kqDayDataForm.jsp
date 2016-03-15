<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#kqDayDataForm").attr("action","saveKqDayData?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#kqDayDataForm").submit();
		});*/
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="kqDayDataForm" method="post"	action="saveKqDayData?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="kqDayData.kqId"/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_attendanceDays" key="kqDayData.attendanceDays" name="kqDayData.attendanceDays" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_attendanceDept" key="kqDayData.attendanceDept" name="kqDayData.attendanceDept" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_checkDate" key="kqDayData.checkDate" name="kqDayData.checkDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_checker" key="kqDayData.checker" name="kqDayData.checker" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day1" key="kqDayData.day1" name="kqDayData.day1" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day10" key="kqDayData.day10" name="kqDayData.day10" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day11" key="kqDayData.day11" name="kqDayData.day11" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day12" key="kqDayData.day12" name="kqDayData.day12" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day13" key="kqDayData.day13" name="kqDayData.day13" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day14" key="kqDayData.day14" name="kqDayData.day14" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day15" key="kqDayData.day15" name="kqDayData.day15" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day16" key="kqDayData.day16" name="kqDayData.day16" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day17" key="kqDayData.day17" name="kqDayData.day17" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day18" key="kqDayData.day18" name="kqDayData.day18" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day19" key="kqDayData.day19" name="kqDayData.day19" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day2" key="kqDayData.day2" name="kqDayData.day2" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day20" key="kqDayData.day20" name="kqDayData.day20" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day21" key="kqDayData.day21" name="kqDayData.day21" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day22" key="kqDayData.day22" name="kqDayData.day22" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day23" key="kqDayData.day23" name="kqDayData.day23" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day24" key="kqDayData.day24" name="kqDayData.day24" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day25" key="kqDayData.day25" name="kqDayData.day25" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day26" key="kqDayData.day26" name="kqDayData.day26" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day27" key="kqDayData.day27" name="kqDayData.day27" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day28" key="kqDayData.day28" name="kqDayData.day28" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day29" key="kqDayData.day29" name="kqDayData.day29" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day3" key="kqDayData.day3" name="kqDayData.day3" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day30" key="kqDayData.day30" name="kqDayData.day30" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day31" key="kqDayData.day31" name="kqDayData.day31" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day4" key="kqDayData.day4" name="kqDayData.day4" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day5" key="kqDayData.day5" name="kqDayData.day5" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day6" key="kqDayData.day6" name="kqDayData.day6" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day7" key="kqDayData.day7" name="kqDayData.day7" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day8" key="kqDayData.day8" name="kqDayData.day8" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_day9" key="kqDayData.day9" name="kqDayData.day9" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_deptCode" key="kqDayData.deptCode" name="kqDayData.deptCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_deptId" key="kqDayData.deptId" name="kqDayData.deptId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_deptName" key="kqDayData.deptName" name="kqDayData.deptName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_empType" key="kqDayData.empType" name="kqDayData.empType" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_kqType" key="kqDayData.kqType" name="kqDayData.kqType" cssClass="required 				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_makeDate" key="kqDayData.makeDate" name="kqDayData.makeDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_maker" key="kqDayData.maker" name="kqDayData.maker" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_orgCode" key="kqDayData.orgCode" name="kqDayData.orgCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_orgName" key="kqDayData.orgName" name="kqDayData.orgName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_period" key="kqDayData.period" name="kqDayData.period" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_personCode" key="kqDayData.personCode" name="kqDayData.personCode" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_personId" key="kqDayData.personId" name="kqDayData.personId" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_personName" key="kqDayData.personName" name="kqDayData.personName" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="kqDayData_status" key="kqDayData.status" name="kqDayData.status" cssClass="				
				
				       "/>
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





