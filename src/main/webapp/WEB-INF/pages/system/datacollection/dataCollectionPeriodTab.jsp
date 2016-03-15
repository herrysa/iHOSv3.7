<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function createPeriodTask() {
		var url = "createPeriodTask";
		//window.location = url;
		//jQuery.post(url, {}, DWZ.ajaxDone, "json");
		navTab.reload(url, {
			title : "New Tab",
			fresh : false,
			data : {}
		});
	}
	function clearPeriodTask() {
		alertMsg.confirm("确认清除采集任务吗？", {
			okCall: function(){
				var url = "clearPeriodTask";
				navTab.reload(url, {
					title : "New Tab",
					fresh : false,
					data : {}
				});
			}
		});
		
		

	}
	function closeDataCollectionPeriod() {
		var url = "closeDataCollectionPeriod";
		navTab.reload(url, {
			title : "New Tab",
			fresh : false,
			data : {}
		});

	}
	/* jQuery(document).ready(function(){
		var statusText = "";
		if("${loginPeriodClosed}" == "true"){
			statusText = "已结账";
		}else if("${loginPeriodStarted}" == "true"){
			statusText = "进行中";
		}else{
			statusText = "未使用";
		}
		var oldText = jQuery("#dataCollectionTask_currentPeriodSpan").text();
		oldText = '<font size="3" color="red">'+oldText+'</font>';
		oldText += "&nbsp;&nbsp;" + statusText;
		jQuery("#dataCollectionTask_currentPeriodSpan").html(oldText);
	}); */
</script>
<div align="left" style="margin-top:10px;margin-left:10px">
	<!-- <span id="dataCollectionTask_currentPeriodSpan">  --><fmt:message key="dataCollectionTask.currentPeriod">
		<fmt:param value="${currentPeriod}"></fmt:param>
	</fmt:message>
	<!-- </span> -->
	<br>
	<br>
	<br>

	<%-- 	<font size="3" color="red">当前的数据采集区间为:${currentPeriod}</font> --%>
	<fmt:message key="dataCollectionTask.totalTaskDefine">
		<fmt:param value="${taskTotal}"></fmt:param>
	</fmt:message>
	<br>
	<br>
	<fmt:message key="dataCollectionTask.periodTasks">
		<fmt:param value="${periodTaskTotal}"></fmt:param>
		<fmt:param value="${periodCompleteTaskNum}"></fmt:param>
		<fmt:param value="${periodRemainTaskNum}"></fmt:param>
	</fmt:message>
	<br>
	<br>
	<%-- <fmt:message key="dataCollectionTask.prompt"></fmt:message>
	<br> <br> --%>

	 <%-- <s:if test="%{currentPeriod==null||currentPeriod=='当前期间未打开'}"> 
	</s:if> 
	<s:else>
	<sj:submit id="create" value="%{getText('dataCollectionTask.creat')}"
			onClickTopics="addRowRecord" button="true"
			onclick="createPeriodTask();" />
	

	<sj:submit id="clear" value="%{getText('dataCollectionTask.clear')}"
		button="true" onclick="clearPeriodTask();" />

	<sj:submit id="close" value="%{getText('dataCollectionTask.close')}"
		button="true" onclick="closeDataCollectionPeriod();" />
	</s:else> --%>

</div>
