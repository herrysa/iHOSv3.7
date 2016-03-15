<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#saveTrainNeedlink').click(function() {
			var value=jQuery("#trainNeed_type").val();
			if(value=="季度计划"){
				if(!jQuery("#trainNeed_quarter").val()){
					alertMsg.error("计划季度为必填项。");
					return;
				}
			}else if(value=="月计划"){
				if(!jQuery("#trainNeed_quarter").val()){
					alertMsg.error("计划季度为必填项。");
					return;
				}
				if(!jQuery("#trainNeed_month").val()){
					alertMsg.error("计划月份为必填项。");
					return;
				}
			}
			jQuery("#trainNeedForm").attr("action","saveTrainNeed?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#trainNeedForm").submit();
		});

		if('${entityIsNew}'=="true"){
		if(jQuery("#trainNeed_type").val()){
			typeSelected(jQuery("#trainNeed_type").val());
		}}else{
			if(jQuery("#trainNeed_type").val()){
				displayChangedByType(jQuery("#trainNeed_type").val());
			}
		}
		if("${oper}"=="view"){
			readOnlyForm("trainNeedForm");
		}else{
			jQuery("#trainNeed_name").addClass("required");
			jQuery("#trainNeed_trainSite").treeselect({
			   dataType:"sql",
			   optType:"single",
			   sql:"SELECT id,name FROM hr_train_site where disabled = 0 ORDER BY code",
			   exceptnullparent:false,
			   lazy:false
			});
			jQuery("#trainNeed_trainInstitution").treeselect({
			   dataType:"sql",
			   optType:"single",
			   sql:"SELECT id,name FROM hr_train_institution where disabled = 0 ORDER BY code",
			   exceptnullparent:false,
			   lazy:false
			});
			jQuery("#trainNeed_trainEquipment").treeselect({
				optType:"multi",
				dataType:'sql',
				sql:"select id,name,category parent from hr_train_equipment where state = '正常' order by code",
				exceptnullparent:true,
				lazy:false,
				selectParent:false
			});
			jQuery("#trainNeed_trainPlan").treeselect({
				dataType:"sql",
				optType:"single",
				sql:"SELECT id,name FROM hr_train_plan where state='2' ORDER BY code",
				exceptnullparent:false,
				lazy:false,
				callback : {
					afterClick : function() { 
						jQuery.ajax({
							url: 'trainPlanGridList?filter_EQS_id='+jQuery("#trainNeed_trainPlan_id").val(),
							data: {},
							type: 'post',
							dataType: 'json',
							async:false,
							error: function(data){
							},
							success: function(data){ 
								if(jQuery("#trainNeed_trainPlan_id").val()){
									var trainPlan = data.trainPlans[0];
									jQuery("#trainNeed_peopleNumber").val(trainPlan["peopleNumber"]);
					                jQuery("#trainNeed_content").val(trainPlan["content"]);
					                jQuery("#trainNeed_goal").val(trainPlan["goal"]);
					                jQuery("#trainNeed_person").val(trainPlan["personNames"]);
					                jQuery("#trainNeed_person_id").val(trainPlan["personIds"]);
					                jQuery("#trainNeed_type").val(trainPlan["type"]);
					                jQuery("#trainNeed_year").val(trainPlan["year"]);
					                jQuery("#trainNeed_quarter").val(trainPlan["quarter"]);
					                jQuery("#trainNeed_month").val(trainPlan["month"]); 
					                jQuery("#trainNeed_expense").val(trainPlan["budgetAmount"]);
					                jQuery("#trainNeed_target").val(trainPlan["target"]);
								}else{
									jQuery("#trainNeed_peopleNumber").val("");
					                jQuery("#trainNeed_content").val("");
					                jQuery("#trainNeed_goal").val("");
					                jQuery("#trainNeed_person").val("");
					                jQuery("#trainNeed_person_id").val("");
					                jQuery("#trainNeed_type").val("");
					                jQuery("#trainNeed_year").val("");
					                jQuery("#trainNeed_quarter").val("");
					                jQuery("#trainNeed_month").val("");
					                jQuery("#trainNeed_expense").val("");
					                jQuery("#trainNeed_target").val("");
								}
								if(jQuery("#trainNeed_type").val()){
				        			typeSelected(jQuery("#trainNeed_type").val());
				        		}
							}
						});
					}
				}
			});
			
			var sql="select personId id,name,dept_id parent,0 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/person.png' icon,personCode orderCol from v_hr_person_current where disabled=0 and deleted=0 ";
				sql=sql+" union select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from v_hr_department_current where disabled=0 and deleted=0 "
				sql=sql+" union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from v_hr_org_current where disabled=0 ";
				sql += " ORDER BY orderCol ";
			jQuery("#trainNeed_person").treeselect({
				optType:"multi",
				dataType:'sql',
				sql:sql,
				exceptnullparent:true,
				lazy:false,
				selectParent:false
			});
		}
		
		adjustFormInput("trainNeedForm");
	});
	
	function displayChangedByType(value){
		if(value=="年度计划"){
			jQuery("#trainNeed_quarter_display").hide();
			jQuery("#trainNeed_month_display").hide();
			jQuery("#trainNeed_year_display").show();
			jQuery("#trainNeed_tempPlanDate").hide();
		}else if(value=="季度计划"){
			jQuery("#trainNeed_quarter_display").show();
			jQuery("#trainNeed_month_display").hide();
			jQuery("#trainNeed_year_display").show();
			jQuery("#trainNeed_tempPlanDate").hide();
		}else if(value=="月计划"){
			jQuery("#trainNeed_quarter_display").show();
			jQuery("#trainNeed_month_display").show();
			jQuery("#trainNeed_year_display").show();
			jQuery("#trainNeed_tempPlanDate").hide();
		}else{
			jQuery("#trainNeed_tempPlanDate").show();
			jQuery("#trainNeed_year_display").hide();
			jQuery("#trainNeed_quarter_display").hide();
			jQuery("#trainNeed_month_display").hide();
		}
	}
	
	function typeSelected(value){
		if(value=="年度计划"){
			jQuery("#trainNeed_quarter_display").hide();
			jQuery("#trainNeed_month_display").hide();
			jQuery("#trainNeed_quarter").val("");
			jQuery("#trainNeed_month").val("");
			jQuery("#trainNeed_studyPeriod").val("12个月");
			if(jQuery("#trainNeed_year").val()){
				yearSelected(jQuery("#trainNeed_year").val());
			}
			jQuery("#trainNeed_tempPlanDate").hide();
			jQuery("#trainNeed_year_display").show();
			jQuery("#trainNeed_year").addClass('required');
			jQuery("#trainNeed_tempPlanStartDate").removeClass('required');
			jQuery("#trainNeed_tempPlanEndDate").removeClass('required');
			jQuery("#trainNeed_tempPlanStartDate").val("");
			jQuery("#trainNeed_tempPlanEndDate").val("");
			
		}else if(value=="季度计划"){
			jQuery("#trainNeed_quarter_display").show();
			jQuery("#trainNeed_month_display").hide();
			jQuery("#trainNeed_month").val("");
			jQuery("#trainNeed_studyPeriod").val("3个月");
			if(jQuery("#trainNeed_quarter").val()){
				quarterSelected(jQuery("#trainNeed_quarter").val());
			}
			jQuery("#trainNeed_tempPlanDate").hide();
			jQuery("#trainNeed_year_display").show();
			jQuery("#trainNeed_year").addClass('required');
			jQuery("#trainNeed_tempPlanStartDate").removeClass('required');
			jQuery("#trainNeed_tempPlanEndDate").removeClass('required');
			jQuery("#trainNeed_tempPlanStartDate").val("");
			jQuery("#trainNeed_tempPlanEndDate").val("");
		}else if(value=="月计划"){
			jQuery("#trainNeed_quarter_display").show();
			jQuery("#trainNeed_month_display").show();
			jQuery("#trainNeed_studyPeriod").val("1个月");
			if(jQuery("#trainNeed_quarter").val()){
				quarterSelected(jQuery("#trainNeed_quarter").val());
			}
			jQuery("#trainNeed_tempPlanDate").hide();
			jQuery("#trainNeed_year_display").show();
			jQuery("#trainNeed_year").addClass('required');
			jQuery("#trainNeed_tempPlanStartDate").removeClass('required');
			jQuery("#trainNeed_tempPlanEndDate").removeClass('required');
			jQuery("#trainNeed_tempPlanStartDate").val("");
			jQuery("#trainNeed_tempPlanEndDate").val("");
		}else{
			jQuery("#trainNeed_quarter_display").hide();
			jQuery("#trainNeed_month_display").hide();
			jQuery("#trainNeed_year_display").hide();
			jQuery("#trainNeed_year").removeClass('required');
			jQuery("#trainNeed_tempPlanStartDate").addClass('required');
			jQuery("#trainNeed_tempPlanEndDate").addClass('required');
			jQuery("#trainNeed_quarter").val("");
			jQuery("#trainNeed_month").val("");
			jQuery("#trainNeed_year").val("");
			jQuery("#trainNeed_tempPlanDate").show();
		}
	}
	function quarterSelected(value){
		jQuery("#trainNeed_month").empty();
		if(jQuery("#trainNeed_type").val()=="月计划"){
		if(value=="第一季度"){
			jQuery("#trainNeed_month").append("<option value=''>--</option>");
			jQuery("#trainNeed_month").append("<option value='一月'>一月</option>");
			jQuery("#trainNeed_month").append("<option value='二月'>二月</option>");
			jQuery("#trainNeed_month").append("<option value='三月'>三月</option>");
		}else if(value=="第二季度"){
			jQuery("#trainNeed_month").append("<option value=''>--</option>");
			jQuery("#trainNeed_month").append("<option value='四月'>四月</option>");
			jQuery("#trainNeed_month").append("<option value='五月'>五月</option>");
			jQuery("#trainNeed_month").append("<option value='六月'>六月</option>");
		}else if(value=="第三季度"){
			jQuery("#trainNeed_month").append("<option value=''>--</option>");
			jQuery("#trainNeed_month").append("<option value='七月'>七月</option>");
			jQuery("#trainNeed_month").append("<option value='八月'>八月</option>");
			jQuery("#trainNeed_month").append("<option value='九月'>九月</option>");
		}else if(value=="第四季度"){
			jQuery("#trainNeed_month").append("<option value=''>--</option>");
			jQuery("#trainNeed_month").append("<option value='十月'>十月</option>");
			jQuery("#trainNeed_month").append("<option value='十一月'>十一月</option>");
			jQuery("#trainNeed_month").append("<option value='十二月'>十二月</option>");
		}else{
			jQuery("#trainNeed_month").val("");
		}
		if(jQuery("#trainNeed_month").val()){
			monthSelected(jQuery("#trainNeed_month").val());
		}
		}else{
			var year=jQuery("#trainNeed_year").val();
			if(!year||!value){
				return;
			}
			var applyStartDate="";
			var applyEndDate="";
			var startDate="";
			var endDate="";
			if(value=="第一季度"){
				applyStartDate=(parseInt(year)-1)+"-12-1";
			    applyEndDate=(parseInt(year)-1)+"-12-31";
			    startDate=year+"-1-1";
			    endDate=year+"-3-31";
			}else if(value=="第二季度"){
				applyStartDate=year+"-3-1";
			    applyEndDate=year+"-3-31";
			    startDate=year+"-4-1";
			    endDate=year+"-6-30";
			}else if(value=="第三季度"){
				applyStartDate=year+"-6-1";
			    applyEndDate=year+"-6-30";
			    startDate=year+"-7-1";
			    endDate=year+"-9-30";
			}else if(value=="第四季度"){
				applyStartDate=year+"-9-1";
			    applyEndDate=year+"-9-30";
			    startDate=year+"-10-1";
			    endDate=year+"-12-31";
			}
			jQuery("#trainNeed_applyStartDate").val(applyStartDate);
			jQuery("#trainNeed_applyEndDate").val(applyEndDate);
			jQuery("#trainNeed_startDate").val(startDate);
			jQuery("#trainNeed_endDate").val(endDate);
			jQuery("#trainNeed_month").val("");
		}
	}
	function monthSelected(value){
		if(jQuery("#trainNeed_type").val()=="月计划"){
		var year=jQuery("#trainNeed_year").val();
		if(!year||!value){
			return;
		}
		var month=value;
		var applyStartDate="";
		var applyEndDate="";
		var startDate="";
		var endDate="";
		switch(month){
		case('一月'):
			applyStartDate=(parseInt(year)-1)+"-12-1";
		    applyEndDate=(parseInt(year)-1)+"-12-31";
		    startDate=year+"-1-1";
		    endDate=year+"-1-31";
			break;
		case('二月'):
		applyStartDate=(parseInt(year)-1)+"-1-1";
	    applyEndDate=(parseInt(year)-1)+"-1-31";
	    if (((parseInt(year) % 4)==0) && ((parseInt(year) % 100)!=0) || ((parseInt(year) % 400)==0)) {
	    	startDate=year+"-2-1";
		    endDate=year+"-2-29";
	    }else{
	    	startDate=year+"-2-1";
	 	    endDate=year+"-2-28";
	    }
			break;
		case('三月'):
		if (((parseInt(year) % 4)==0) && ((parseInt(year) % 100)!=0) || ((parseInt(year) % 400)==0)) {
		applyStartDate=(parseInt(year)-1)+"-2-1";
	    applyEndDate=(parseInt(year)-1)+"-2-29";
		}else{
		applyStartDate=(parseInt(year)-1)+"-2-1";
		applyEndDate=(parseInt(year)-1)+"-2-28";
		}
	    startDate=year+"-3-1";
	    endDate=year+"-3-31";
			break;
		case('四月'):
		applyStartDate=year+"-3-1";
	    applyEndDate=year+"-3-31";
	    startDate=year+"-4-1";
	    endDate=year+"-4-30";
			break;
		case('五月'):
		applyStartDate=year+"-4-1";
	    applyEndDate=year+"-4-30";
	    startDate=year+"-5-1";
	    endDate=year+"-5-31";
			break;
		case('六月'):
		applyStartDate=year+"-5-1";
	    applyEndDate=year+"-5-31";
	    startDate=year+"-6-1";
	    endDate=year+"-6-30";
			break;
		case('七月'):
		applyStartDate=year+"-6-1";
	    applyEndDate=year+"-6-30";
	    startDate=year+"-7-1";
	    endDate=year+"-7-31";
			break;
		case('八月'):
		applyStartDate=year+"-7-1";
	    applyEndDate=year+"-7-31";
	    startDate=year+"-8-1";
	    endDate=year+"-8-31";
			break;
		case('九月'):
		applyStartDate=year+"-8-1";
	    applyEndDate=year+"-8-31";
	    startDate=year+"-9-1";
	    endDate=year+"-9-30";
			break;
		case('十月'):
		applyStartDate=year+"-9-1";
	    applyEndDate=year+"-9-30";
	    startDate=year+"-10-1";
	    endDate=year+"-10-31";
			break;
		case('十一月'):
		applyStartDate=year+"-10-1";
	    applyEndDate=year+"-10-31";
	    startDate=year+"-11-1";
	    endDate=year+"-11-30";
			break;
		case('十二月'):			
		applyStartDate=year+"-11-1";
	    applyEndDate=year+"-11-30";
	    startDate=year+"-12-1";
	    endDate=year+"-12-31";
			break;
			default:
			applyStartDate="";
		    applyEndDate="";
		    startDate="";
		    endDate="";
		    break;
		}
		jQuery("#trainNeed_applyStartDate").val(applyStartDate);
		jQuery("#trainNeed_applyEndDate").val(applyEndDate);
		jQuery("#trainNeed_startDate").val(startDate);
		jQuery("#trainNeed_endDate").val(endDate);
		}
	}
	function yearSelected(value){
		if(!value){
			return;
		}
		if(jQuery("#trainNeed_type").val()=="年度计划"){
			var year=value;
			var applyStartDate="";
			var applyEndDate="";
			var startDate="";
			var endDate="";
			applyStartDate=(parseInt(year)-1)+"-12-1";
		    applyEndDate=(parseInt(year)-1)+"-12-30";
		    startDate=year+"-1-1";
		    endDate=year+"-12-31";
		    jQuery("#trainNeed_applyStartDate").val(applyStartDate);
			jQuery("#trainNeed_applyEndDate").val(applyEndDate);
			jQuery("#trainNeed_startDate").val(startDate);
			jQuery("#trainNeed_endDate").val(endDate);
		}else if(jQuery("#trainNeed_type").val()=="季度计划"){
			if(jQuery("#trainNeed_quarter").val()){
				quarterSelected(jQuery("#trainNeed_quarter").val());
			}
		}else if(jQuery("#trainNeed_type").val()=="月计划"){
			if(jQuery("#trainNeed_month").val()){
				monthSelected(jQuery("#trainNeed_month").val());
			}
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="trainNeedForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
					<s:hidden key="trainNeed.id"/>
					<s:hidden key="trainNeed.approvalOpinion"/>
					<s:hidden key="trainNeed.checkDate"/>
					<s:hidden key="trainNeed.checker.personId"/>
					<s:hidden key="trainNeed.makeDate"/>
					<s:hidden key="trainNeed.maker.personId"/>
					<s:hidden key="trainNeed.state"/>
					<s:hidden key="trainNeed.peopleNumber"/>
					<s:hidden key="trainNeed.code"/>
					<s:hidden key="trainNeed.courseArrangement"/>
					<s:hidden key="trainNeed.syllabus"/>
					<s:hidden key="trainNeed.yearMonth"/>
				</div>
				<div class="unit">
				<s:if test="%{entityIsNew}">
					<s:textfield id="trainNeed_name" key="trainNeed.name" name="trainNeed.name" maxlength="20" 
					notrepeat='培训班名称已存在' validatorParam="from TrainNeed tn where tn.name=%value%"/>
				</s:if>
				<s:else>
					<s:textfield id="trainNeed_name" key="trainNeed.name" name="trainNeed.name" maxlength="20" oldValue="'${trainNeed.name}'"
					notrepeat='培训班名称已存在' validatorParam="from TrainNeed tn where tn.name=%value%"/>
				</s:else>
					<label><s:text name='trainNeed.target' />:</label>
					<span style="float: left; width: 140px"> 
					<s:select key="trainNeed.target" style="font-size:9pt;"
     					   maxlength="50" list="trainTargetList" listKey="value"
     					   listValue="content" emptyOption="false" theme="simple"></s:select>
    				</span>
				</div>
				<div class="unit">
					<s:textfield id="trainNeed_trainSite" key="trainNeed.trainSite" name="trainNeed.trainSite.name"/>
					<s:hidden id="trainNeed_trainSite_id" key="trainNeed.trainSite.id"/>
					<s:textfield id="trainNeed_sponsor" key="trainNeed.sponsor" name="trainNeed.sponsor" cssClass="" maxlength="30"/>
				</div>
				<div class="unit">
					<s:textfield id="trainNeed_trainInstitution" key="trainNeed.trainInstitution" name="trainNeed.trainInstitution.name"/>
					<s:hidden id="trainNeed_trainInstitution_id" key="trainNeed.trainInstitution.id"/>
					<s:textfield id="trainNeed_trainPlan" key="trainNeed.trainPlan" name="trainNeed.trainPlan.name"/>
					<s:hidden id="trainNeed_trainPlan_id" key="trainNeed.trainPlan.id"/>
				</div>
				<div class="unit">
					<label><s:text name='trainNeed.trainType' />:</label>
					<span class="formspan" style="float: left; width: 152px"> 
						<s:select key="trainNeed.trainType" style="font-size:9pt;"
     					   	maxlength="20" list="trainTypeList" listKey="value"
      						listValue="content" emptyOption="true" theme="simple"></s:select>
   					</span>
					<label><s:text name='trainNeed.trainMethod' />:</label>
					<span class="formspan" style="float: left; width: 140px"> 
						<s:select key="trainNeed.trainMethod" style="font-size:9pt;"
     					   	maxlength="20" list="trainMethodList" listKey="value"
      						listValue="content" emptyOption="true" theme="simple"></s:select>
   					</span>
				</div>
				<div class="unit">
					<s:textfield id="trainNeed_principal" key="trainNeed.principal" name="trainNeed.principal" cssClass="" maxlength="20"/>
					<s:textfield id="trainNeed_goal" key="trainNeed.goal" name="trainNeed.goal" cssClass="" maxlength="30"/>
				</div>
				<div class="unit">
					<s:textarea key="trainNeed.content" maxlength="200" rows="4" cols="54" cssClass="input-xlarge " />
				</div>
				<div class="unit">
					<label><s:text name='trainNeed.type' />:</label>
					<span class="formspan" style="float: left; width: 152px"> 
					<s:select  id="trainNeed_type" key="trainNeed.type" onchange="typeSelected(this.value)"
     					maxlength="20" list="typeList" listKey="value"  style="font-size:9pt;"
      					listValue="content" emptyOption="false" theme="simple"></s:select>
   					</span>
   					<div id="trainNeed_year_display">
   					<label><s:text name='trainNeed.year' />:</label>
   					<input name="trainNeed.year" type="text"  id="trainNeed_year"
   					onFocus="WdatePicker({skin:'ext',dateFmt:'yyyy',onpicked:yearSelected(this.value)})"
      				value="${trainNeed.year}"/>
      				</div>
				</div>
				<div class="unit" id="trainNeed_quarter_month">
					<div id="trainNeed_quarter_display" style="display:none;">
						<label><s:text name='trainNeed.quarter' />:</label>
						<span class="formspan" style="float: left; width: 152px"> 
							<s:select key="trainNeed.quarter"  id="trainNeed_quarter" style="font-size:9pt;" headerKey="" headerValue="--" 
	     					   	maxlength="20" list="quarterList" listKey="value" onchange="quarterSelected(this.value)"
	      						listValue="content" emptyOption="false" theme="simple"></s:select>
	   					</span>
	   				</div>
					<div id="trainNeed_month_display" style="display:none;">
						<label><s:text name='trainNeed.month' />:</label>
						<span class="formspan" style="float: left; width: 140px"> 
							<s:select id="trainNeed_month" key="trainNeed.month" style="font-size:9pt;" headerKey="" headerValue="--" 
	     					   	maxlength="20" list="monthList" listKey="value"  onchange="monthSelected(this.value)"
	      						listValue="content" emptyOption="false" theme="simple"></s:select>
	   					</span>
	   				</div>
   				</div>
   				<div class="unit" id="trainNeed_tempPlanDate" style="display:none;">
   					<label><s:text name='trainNeed.tempPlanStartDate' />:</label>
					<input name="trainNeed.tempPlanStartDate" type="text" id="trainNeed_tempPlanStartDate" class="Wdate" style="height:15px"
      					onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'trainNeed_tempPlanEndDate\')}'})" class=""
       					value="<fmt:formatDate value="${trainNeed.tempPlanStartDate}" pattern="yyyy-MM-dd"/>"
      					onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'trainNeed_tempPlanEndDate\')}'})" /> 
      				<label><s:text name='trainNeed.tempPlanEndDate' />:</label>
      				<input name="trainNeed.tempPlanEndDate" type="text" class="Wdate" style="height:15px" id="trainNeed_tempPlanEndDate"
      					onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'trainNeed_tempPlanStartDate\')}'})"  
       					value="<fmt:formatDate value="${trainNeed.tempPlanStartDate}" pattern="yyyy-MM-dd"/>"
      					onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'trainNeed_tempPlanStartDate\')}'})" /> 
   				</div>
				<div class="unit">
					<label><s:text name='trainNeed.applyStartDate' />:</label>
					<input name="trainNeed.applyStartDate" type="text" id="trainNeed_applyStartDate" class="Wdate" style="height:15px"
      					onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'trainNeed_applyEndDate\')}'})" class=""
       					value="<fmt:formatDate value="${trainNeed.applyStartDate}" pattern="yyyy-MM-dd"/>"
      					onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'trainNeed_applyEndDate\')}'})" /> 
      				<label><s:text name='trainNeed.applyEndDate' />:</label>
      				<input name="trainNeed.applyEndDate" type="text" class="Wdate" style="height:15px" id="trainNeed_applyEndDate"
      					onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'trainNeed_applyStartDate\')}'})"  
       					value="<fmt:formatDate value="${trainNeed.applyEndDate}" pattern="yyyy-MM-dd"/>"
      					onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'trainNeed_applyStartDate\')}'})" /> 
				</div>
				<div class="unit">
					<label><s:text name='trainNeed.startDate' />:</label>
					<input name="trainNeed.startDate" type="text" class="Wdate" style="height:15px" id="trainNeed_startDate"
						onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'trainNeed_endDate\')}'})" 
						value="<fmt:formatDate value="${trainNeed.startDate}" pattern="yyyy-MM-dd"/>"
						onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'trainNeed_applyEndDate\')}'})" /> 
      				<label><s:text name='trainNeed.endDate' />:</label>
      				<input name="trainNeed.endDate" type="text" class="Wdate" style="height:15px" id="trainNeed_endDate"
      					onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'trainNeed_endDate\')}'})"  
       					value="<fmt:formatDate value="${trainNeed.endDate}" pattern="yyyy-MM-dd"/>"
      					onFocus="WdatePicker({skin:'ext',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'trainNeed_startDate\')}'})" /> 	
				</div>
				<div class="unit">
					<s:textfield key="trainNeed.expense" name="trainNeed.expense" cssClass="number"/>
					<s:textfield key="trainNeed.studyPeriod" name="trainNeed.studyPeriod" cssClass="" maxlength="20"/>
				</div>
   				<div class="unit">
					<s:hidden id="trainNeed_person_id" name="trainNeed.personIds" maxlength="8000" value="%{trainNeed.personIds}"/>
					<label><s:text name="trainNeed.personNames"></s:text>:</label>
					<span style="float:left;width:140px">
						<s:textarea id="trainNeed_person" name="trainNeed.personNames" rows="4" maxlength="8000" cols="54" cssClass="" value="%{trainNeed.personNames}" readonly="true" title="培训人员列表"></s:textarea>
					</span>
				</div>
				<div class="unit">
					<s:hidden id="trainNeed_trainEquipment_id" name="trainNeed.trainEquipmentIds" maxlength="2000" value="%{trainNeed.trainEquipmentIds}"/>
					<label><s:text name="trainNeed.trainEquipmentNames"></s:text>:</label>
					<span class="formspan" style="float:left;width:140px">
						<s:textarea id="trainNeed_trainEquipment" name="trainNeed.trainEquipmentNames"  maxlength="2000" rows="4" cols="54" cssClass="" value="%{trainNeed.trainEquipmentNames}" readonly="true" title="培训设备列表"></s:textarea>
					</span>
				</div>
<!-- 				<div class="unit"> -->
<%-- 					<s:textarea key="trainNeed.courseArrangement" maxlength="200" rows="4" cols="54" cssClass="input-xlarge " /> --%>
<!-- 				</div> -->
<!-- 				<div class="unit"> -->
<%-- 					<s:textarea key="trainNeed.syllabus" maxlength="200" rows="4" cols="54" cssClass="input-xlarge " /> --%>
<!-- 				</div> -->
				<div class="unit">
					<s:textarea key="trainNeed.remark" maxlength="200" rows="4" cols="54" cssClass="input-xlarge" />
				</div>
				<s:if test="%{oper=='view'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="trainNeed.maker"></s:text>:</label>
								<input type="text" value="${trainNeed.maker.name}"></input>
							</span>
							<span>
								<label><s:text name="trainNeed.checker"></s:text>:</label>
								<input type="text" value="${trainNeed.checker.name}"></input>
							</span>
						</div>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li id="trainNeedFormSaveButton"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveTrainNeedlink"><s:text name="button.save" /></button>
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





