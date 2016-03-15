<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#deptInspectForm").attr("action","saveDeptInspect?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#deptInspectForm").submit();
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="deptInspectForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="deptInspect.deptinspectId"/>
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_aValue" key="deptInspect.aValue" name="deptInspect.aValue" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_checkperiod" key="deptInspect.checkperiod" name="deptInspect.checkperiod" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="deptInspect.department.id" list="departmentList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_dscore" key="deptInspect.dscore" name="deptInspect.dscore" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="deptInspect.inspectTempl.id" list="inspectTemplList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="deptInspect.inspectdept.id" list="inspectdeptList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_jjdepttype" key="deptInspect.jjdepttype" name="deptInspect.jjdepttype" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="deptInspect.kpiItem.id" list="kpiItemList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_money1" key="deptInspect.money1" name="deptInspect.money1" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_money2" key="deptInspect.money2" name="deptInspect.money2" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_operateDate" key="deptInspect.operateDate" name="deptInspect.operateDate" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_operateDate1" key="deptInspect.operateDate1" name="deptInspect.operateDate1" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_operateDate2" key="deptInspect.operateDate2" name="deptInspect.operateDate2" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="deptInspect.operator.id" list="operatorList" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="deptInspect.operator1.id" list="operator1List" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
    <!-- todo: change this to read the identifier field from the other pojo -->
    <!-- <s:select name="deptInspect.operator2.id" list="operator2List" listKey="id" listValue="id"></s:select> -->
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_periodType" key="deptInspect.periodType" name="deptInspect.periodType" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_remark" key="deptInspect.remark" name="deptInspect.remark" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_remark2" key="deptInspect.remark2" name="deptInspect.remark2" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_remark3" key="deptInspect.remark3" name="deptInspect.remark3" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_score" key="deptInspect.score" name="deptInspect.score" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_state" key="deptInspect.state" name="deptInspect.state" cssClass="				
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_tValue" key="deptInspect.tValue" name="deptInspect.tValue" cssClass="				number
				
				       "/>
				</div>
				<div class="unit">
				<s:textfield id="deptInspect_weight" key="deptInspect.weight" name="deptInspect.weight" cssClass="				number
				
				       "/>
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





