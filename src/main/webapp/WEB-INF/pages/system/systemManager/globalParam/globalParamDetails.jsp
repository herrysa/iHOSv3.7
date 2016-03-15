<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script type="text/javascript">
	jQuery(document).ready(function() {
		var subSystem="${subSystem}";
		if(subSystem == "HR") {
			jQuery("#${random}globalparam_detail_subSystemId").text("人力资源管理系统");
		} else if(subSystem == "GZ") {
			jQuery("#${random}globalparam_detail_subSystemId").text("薪资管理系统");
		} else {
			jQuery("#${random}globalparam_detail_subSystemId").text("系统管理平台");
		}
	});
</script>
</head>
<div class="page">
	<div class="pageHeader" id="${random}globalparam_detail_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
			<c:choose>
				<c:when test="${empty subSystem}">
					<label>
						<s:text name='当前位置'/>:
						<font id="${random }globalparam_detail_subSystemId" style="color: red;"></font>
					</label>
				</c:when>
				<c:otherwise>
					<label>
						<s:text name='子系统'/>:
						<font id="${random }globalparam_detail_subSystemId" style="color: red;"></font>
					</label>
				</c:otherwise>
			</c:choose>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div style="width: 456px">
				<fieldset style="height: 246px; width: 442px; margin: 3px; color: #333; border: #06c solid 1px;">
					<legend style="color: #06c; font-weight: 800; background: #fff;">业务操作</legend>
					<ul style="width:440px;overflow:auto;zoom:1;height: 232px;">
						<s:iterator value="globalParamCards" var="card">
							<s:if test='#card.paramType eq "1"'>
								<li style="float:left;width:210px;padding:5px"><s:property value="#card.paramName" />:<s:property value="#card.paramValue" /></li>
							</s:if>
						</s:iterator>
					</ul>
				</fieldset>
				<fieldset style="height: 246px; width: 442px; margin: 3px; color: #333; border: #06c solid 1px;">
					<legend style="color: #06c; font-weight: 800; background: #fff;">数据格式</legend>
					<ul style="width:440px;overflow:auto;zoom:1;height: 232px;">
						<s:iterator var="card" value="globalParamCards">
							<s:if test="#card.paramType eq \"2\"">
								<li style="float:left;width:210px;padding:5px"><s:property value="#card.paramName"/>:<s:property value="#card.paramValue"/></li>
							</s:if>
						</s:iterator>
					</ul>
				</fieldset>
		</div>
	</div>
</div>

