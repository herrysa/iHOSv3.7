<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="" /></title>
<meta name="heading" content="<fmt:message key='commandScript.title'/>" />
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
	});
</script>
</head>

<s:form id="graphicsDisplayForm" action="graphicsDisplayForm?popup=true" method="post"
	validate="true" cssClass="well  form-horizontal">

	<table width="100%">  
	<tr align="center"><td height="550" width="550" bgcolor="#FFFFFF" bordercolor="#999999"><table    border=10 cellspacing=0 width=550 bordercolorlight=#0099CC bordercolordark=#174F7B><tr><td>
	<img alt="量本利分析图" src="${ctx}/data/benLiangLi.png"></td></tr>
	</table></td></tr>
	<tr align="center"><td>图形名称:&nbsp;&nbsp;<input type="text" id="picName" name="picName" value="量本利分析"/></td></tr>
	<tr align="center"><td>销售线斜率:<input type="text" name="k1" id="k1" value="0"/></td></tr>
	<tr align="center"><td>成本线斜率:<input type="text"  name="k2" id="k2" onblur="ff()" value="0"/></td></tr>
	<tr align="center"><td>固定成本:&nbsp;&nbsp;<input type="text" id="chengB" name="chengB" value="0"></td></tr>
	<tr align="center"><td colspan="2">
	<fieldset class="form-actions"> 
		<s:submit id="savelink" key="commandScript.submit" method="graphicsDisplay"
			cssClass="btn btn-primary" theme="simple" />
		<s:reset  id="cancellink" key="button.cancel"
			cssClass="btn" theme="simple" />
	</fieldset>
	</td></tr>
	
	</table>

</s:form>

