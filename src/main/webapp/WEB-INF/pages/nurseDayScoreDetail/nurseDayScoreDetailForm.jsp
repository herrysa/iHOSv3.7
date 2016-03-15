<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	var selectedPerson= '${selectedPerson}';
	selectedPerson = selectedPerson.replace(/&#039;/g,"'");
	jQuery(document).ready(function() {
		jQuery('#${random}savelink').click(function() {
			jQuery("#nurseDayScoreDetailForm").attr("action","saveNurseDayScoreDetail?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#nurseDayScoreDetailForm").submit();
		});
		addTreeSelect("tree","sync","nurseName","nurseId","multi",{tableName:"jj_t_monthNurseList",treeId:"id",treeName:"name",parentId:"ownerdeptid",order:"",filter:" checkperiod='${checkPeriod}' and ownerdeptid='${deptId}' and personid not in "+selectedPerson});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="nurseDayScoreDetailForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="nurseDayScoreDetail.dayScoreDetailID" required="true" cssClass="validate[required]"/>
				<s:hidden key="nurseDayScoreDetail.dayScoreID.DayScoreID" required="true" cssClass="validate[required]"/>
				</div>
				<div class="unit">
				<s:textfield id="nurseDayScoreDetail_checkperiod" key="nurseDayScoreDetail.checkperiod" name="nurseDayScoreDetail.checkperiod" cssClass="				
				
				       " readonly="true"/>
				</div>
				<div class="unit">
				<s:textfield id="nurseDayScoreDetail_scoredate" key="nurseDayScoreDetail.scoredate" name="nurseDayScoreDetail.scoredate" cssClass="				
				
				       " readonly="true"/>
				</div>
				<div class="unit">
				<s:textfield id="nurseDayScoreDetail_groupname" key="nurseDayScoreDetail.groupname" name="nurseDayScoreDetail.groupname" cssClass="				
				
				       " readonly="true"/>
				</div>
				<div class="unit">
				<s:hidden type="hidden" id="nurseId" key="nurseDayScoreDetail.personid.personId" theme="simple"/>
				<label>人员姓名:</label>
				<input type="text" id="nurseName" class="required">
				</div>
				<div class="unit">
				<label>状态标记:</label><span style="float: left; width: 140px;line-height:21px">新建</span>
				</div>
				<div class="unit">
				<s:textarea id="nurseDayScoreDetail_remark" key="nurseDayScoreDetail.remark" name="nurseDayScoreDetail.remark" cssClass="" cols="40" rows="3"/>
				</div>
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink"><s:text name="button.save" /></button>
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





