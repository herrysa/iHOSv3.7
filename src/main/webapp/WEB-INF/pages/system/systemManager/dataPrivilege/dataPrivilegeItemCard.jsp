<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	jQuery(document).ready(function() {
	});
</script>
<div class="page" style="background-color: #e4ebf6;height:100%;overflow:auto;">
	<s:iterator value="dataPriviCardList" status="dpl">
		<div style="margin:5px">
		<fieldset style="height: 150px;width:300px; float: left; margin: 3px; color: #333; border: #06c solid 1px;">
			<legend style="color: #06c; font-weight: 800; background: #e4ebf6;"><s:property value="className"/></legend>
			<div style="height: 135px;overflow: auto;">
				<label style="font-weight: bold;margin:3px">角色权限:</label><s:property value="rolePriviStr"/>
				<s:if test="dataPrivilegeType==2">
				<br><br>
				<label style="font-weight: bold;margin:3px">用户权限:</label><s:property value="userPriviStr"/>
				</s:if>
			</div>
		</fieldset>
		</div>
	</s:iterator>
</div>