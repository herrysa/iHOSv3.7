<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#savelink').click(function() {
			jQuery("#statisticsItemForm").attr("action","saveStatisticsItem?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#statisticsItemForm").submit();
		});
		var sql="SELECT code id,(SELECT tableName FROM t_bdinfo tb WHERE sd.bdInfoId=tb.bdinfoId)  name FROM sy_statistics_detail sd WHERE sd.code ='"+"${statisticsCode}"+"' ";
		jQuery("#statisticsItem_statisticsBdInfo").treeselect({
			optType:"single",
			dataType:'sql',
			sql:sql,
			exceptnullparent:true,
			lazy:false,
			selectParent:false,
			callback:null
		});
		dynamicChange();
		  if("${oper}"=="view"){
				readOnlyForm("statisticsItemForm");
				jQuery("#statisticsItem_buttonActive").css("display","none");
		  }
	});
	function dynamicChange(){
		var dynamic = jQuery("#statisticsItem_dynamicStatistics").attr("checked");
		var content=jQuery("#statisticsItem_dynamicChange");
		if(dynamic=="checked"){
			content.css("display","block");
		}else{
			content.css("display","none");
		}
// 		if(content.css("display")=="none"){
// 			content.css("display","block");
// 		}else{
// 			content.css("display","none");
// 		}
	}
	function savestatisticsItemForm(data){
		formCallBack(data);
		if("${entityIsNew}"=="true"){
			statisticsItemLeftTree();
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="statisticsItemForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,savestatisticsItemForm);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="statisticsItem.id"/>
				<s:hidden key="statisticsItem.changeUser.personId"/>
				<s:hidden key="statisticsItem.changeDate"/>
				<s:hidden key="statisticsItem.sysFiled"/>
				<s:hidden key="statisticsItem.statisticsCode"/>
				<s:hidden key="statisticsItem.statisticsValue"/>
				<s:hidden key="statisticsItem.filterSql"/>
				</div>
				<div class="unit">
				<s:if test="%{entityIsNew}">
					<s:textfield id="statisticsItem_code" key="statisticsItem.code" name="statisticsItem.code" cssClass="required" maxlength="30" 
					notrepeat='统计项编码已存在' validatorParam="from StatisticsItem item where item.code=%value%"/>
					<s:textfield id="statisticsItem_name" key="statisticsItem.name" name="statisticsItem.name" cssClass="required" maxlength="50" 
					notrepeat='统计项名称已存在' validatorParam="from StatisticsItem item where item.name=%value%"/>
				</s:if>
				<s:else>
					<s:textfield id="statisticsItem_code" key="statisticsItem.code" name="statisticsItem.code" cssClass="required" maxlength="30" readonly="true"/>
					<s:textfield id="statisticsItem_name" key="statisticsItem.name" name="statisticsItem.name" cssClass="required" maxlength="50" oldValue="'${statisticsItem.name}'"
					notrepeat='统计项名称已存在' validatorParam="from StatisticsItem item where item.name=%value%"/>
				</s:else>
				</div>
				<div class="unit">
				<s:textfield id="statisticsItem_sn" key="statisticsItem.sn" name="statisticsItem.sn" cssClass="digits required"/>
					<label><s:text name='statisticsItem.parentType' />:</label>
						<input type="text" id="statisticsItem_parentType"  name="statisticsItem.parentType.name" value="${statisticsItem.parentType.name}" class="required" readonly="readonly"/>
						<input type="hidden" id="statisticsItem_parentType_id" name="statisticsItem.parentType.id" value="${statisticsItem.parentType.id}"/>
				</div>
				<div class="unit">
					<label><s:text name='statisticsItem.statisticsBdInfo' />:</label>
						<input type="text" id="statisticsItem_statisticsBdInfo"  value="${statisticsItem.statisticsBdInfo.bdInfo.tableName}" class="required"/>
						<input type="hidden" id="statisticsItem_statisticsBdInfo_id" name="statisticsItem.statisticsBdInfo.code" value="${statisticsItem.statisticsBdInfo.code}"/>
					<s:textfield id="statisticsItem_statisticsFieldInfo" key="statisticsItem.statisticsFieldInfo" name="statisticsItem.statisticsFieldInfo" cssClass="" maxlength="50"/>
				</div>
<!-- 				<div class="unit"> -->
<%-- 				 <s:textarea key="statisticsItem.filterSql" required="false" maxlength="200" --%>
<%-- 							rows="4" cols="54" cssClass="input-xlarge" /> --%>
<!-- 				</div> -->
				<div class="unit">
					  <s:textfield id="statisticsItem_mccKeyId" key="statisticsItem.mccKeyId" name="statisticsItem.mccKeyId" cssClass="required" maxlength="10"/>
    		    	  <label><s:text name='statisticsItem.dynamicStatistics' />:</label> <span
     				  style="float: left; width: 140px"> <s:checkbox id="statisticsItem_dynamicStatistics"
    				    key="statisticsItem.dynamicStatistics" onclick="dynamicChange()" required="false" theme="simple" />
    		   		 </span>
				</div>
				<div id="statisticsItem_dynamicChange" style="display:none">
				<div class="unit">
    		   		 <s:textfield id="statisticsItem_dynamicTable" key="statisticsItem.dynamicTable" name="statisticsItem.dynamicTable" cssClass="" maxlength="50"/>
    		   		 <s:textfield id="statisticsItem_dynamicCode" key="statisticsItem.dynamicCode" name="statisticsItem.dynamicCode" cssClass="" maxlength="50"/>
				</div>
				<div class="unit">
    		        <s:textfield id="statisticsItem_dynamicTablePk" key="statisticsItem.dynamicTablePk" name="statisticsItem.dynamicTablePk" cssClass="" maxlength="50"/>
    		    	<s:textfield id="statisticsItem_dynamicTableForeignKey" key="statisticsItem.dynamicTableForeignKey" name="statisticsItem.dynamicTableForeignKey" cssClass="" maxlength="50"/>
				</div>
				<div class="unit">
				 <s:textarea key="statisticsItem.dynamicFilterSql" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
				</div>
				</div>
				<div class="unit">
				 	<label><s:text name='statisticsItem.disabled' />:</label> <span
     				  style="float: left; width: 140px"> <s:checkbox
    				    key="statisticsItem.disabled" required="false" theme="simple" />
    		    	</span>
    		    	<label><s:text name='statisticsItem.statisticsAuto' />:</label> <span
     				  style="float: left; width: 140px"> <s:checkbox
    				    key="statisticsItem.statisticsAuto" required="false" theme="simple" />
    		    	</span>
				</div>
				<div class="unit">
				 	<label><s:text name='statisticsItem.deptRequired' />:</label> <span
     				  style="float: left; width: 140px"> <s:checkbox
    				    key="statisticsItem.deptRequired" required="false" theme="simple" />
    		    	</span>
				</div>
				<div class="unit">
						<s:textarea key="statisticsItem.remark" required="false" maxlength="200"
							rows="4" cols="54" cssClass="input-xlarge" />
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="statisticsItem_buttonActive">
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





