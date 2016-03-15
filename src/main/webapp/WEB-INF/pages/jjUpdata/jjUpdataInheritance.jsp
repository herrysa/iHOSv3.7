<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery("#jjUpdataInheritance_hisPeriod").find("option").each(function(){
			jQuery(this).last().attr("selected","true");
		});
	});
	function inheritJjUpData(){
		var hisPeriod = jQuery("#jjUpdataInheritance_hisPeriod").val();
		var deptId = jQuery("#jjUpdataInheritance_dept").val();
		var deptName = jQuery("#jjUpdataInheritance_dept").find("option:selected").text()
		var itemName = jQuery("#jjUpdataInheritance_itemName").val();
		var amount = jQuery("#jjUpdataInheritance_amount").attr("checked");
		if(amount){
			amount = true;
		}else{
			amount = false;
		}
		var defColumnNames = '';
		jQuery("input[name='jjUpdataDefColumn']:checked").each(function(){
			defColumnNames += this.value+',';
		});
		alertMsg.confirm("确认继承期间为"+hisPeriod+"、上报科室为"+deptName+"的上报数据？", {
			okCall: function(){
				$.ajax({
				    url: 'inheritJjUpdata',
				    type: 'post',
				    data:{hisPeriod:hisPeriod,itemName:itemName,defColumnNames:defColumnNames,deptId:deptId,amount:amount},
				    dataType: 'json',
				    aysnc:false,
				    error: function(data){
				        
				    },
				    success: function(data){
				    	if(data.statusCode==200){
				    		alertMsg.correct(data.message);
				    		jQuery("#jjUpdata_gridtable").jqGrid('setGridParam',{url:'jjUpdataGridList',page:1}).trigger("reloadGrid");
				    		$.pdialog.closeCurrent();
				    	}else{
					    	alertMsg.error(data.message);
				    	}
				    }
				});
			}
		});
		
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form class="pageForm">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<label>历史数据核算期间:</label>
					<s:select id="jjUpdataInheritance_hisPeriod" list="hisPeriods" listKey="periodCode" listValue="periodCode"></s:select>
					<label>新核算期间:</label><input type="text" readonly="readonly" value="${checkPeriod}" style="width:80px"/>
				</div>
				<div class="unit">
					<label><s:text name="jjUpdata.dept"/>:</label>
					<span style="float: left; width: 73px">
						<s:select id="jjUpdataInheritance_dept" list="updataDepts" listKey="departmentId" listValue="name" theme="simple"></s:select>
					</span>
					<label><s:text name="jjUpdata.itemName"/>:</label>
					<input id="jjUpdataInheritance_itemName" type="text" readonly="readonly" value="${itemName}" style="width:80px"/>
				</div>
				<div class="unit">
					<label><s:text name="上报人员"/>:</label>
					<span style="float: left; width: 74px"><input type="checkbox" checked="checked" disabled="disabled"/></span>
					<label><s:text name="jjUpdata.amount"/>:</label>
					<span style="float: left; width: 74px"><input id="jjUpdataInheritance_amount" type="checkbox"/></span>
				</div>
				<div class="unit"></div>
				<c:if test="${!empty jjUpdataDefColumns}">
					<span style="margin-left:16px">继承数据项:</span>
					<div class="unit">
						<s:iterator value="jjUpdataDefColumns" id="defColumn">
							<div style="float:left;padding:0px;margin-right:50px;white-space:nowrap;">
								<label><s:property value="title"/>:</label>
								<span style="width:24px;float:left;white-space:nowrap">
									<input type="checkbox" name="jjUpdataDefColumn" value="<s:property value='columnName'/>"/>
								</span>
							</div>
						</s:iterator>
					</div>
				</c:if>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="inheritJjUpData()"><s:text name="确定" /></button>
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
