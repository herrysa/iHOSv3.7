<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
response.flushBuffer();
Cookie killMyCookie = new Cookie("mycookie", null);   
killMyCookie.setMaxAge(0);   
killMyCookie.setPath("/");   
response.addCookie(killMyCookie); 
%>
<title><fmt:message key="" /></title>
<meta name="heading" content="<fmt:message key='commandScript.title'/>" />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
<META HTTP-EQUIV="Expires" CONTENT="0">
<script>
	jQuery(document).ready(function() {
		jQuery('#cancellink').click(function() {
			window.close();
		});
		if('${str}'!=null||'${str}'!=''){
			jQuery("span[name=wu]").each(function(){ 
				jQuery(this).html('<font color="red" >${str}</font>');
			});
		}else{
			jQuery("span[name=wu]").each(function(){ 
				jQuery(this).html("");
			});
		}
	});
	function ff(){
		 var k1=jQuery("#k1").attr("value");
		var k2=jQuery("#k2").attr("value");
		//alert(jQuery("#k1").attr("value"));
		 if(k2>k1){
			alert("请输入一个小于销售线斜率的数");
			jQuery("#k2").attr("value",0);
		}  
	}
</script>

<!-- <s:form id="graphicsDisplayForm" action="graphicsDisplayForm?popup=true" method="post" validate="false" 
cssClass="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">-->
<BODY onload="">
	<table width="100%">  
	<tr><td>&nbsp;&nbsp;&nbsp;</td></tr>
	<tr align="center"><td height="550" width="900"> 
	<table><tr><td align="center" height="550" width="900" >
	<c:if  test="${str==null||str==''}">
	<img id="bllfx" alt="量本利分析图" src="${ctx}${bllPath}">
	</c:if>
	<span name="wu"><font color=""></font></span>
	</td></tr>
	</table></td></tr></table>
</body>
	
	
	</td></tr>
	</table>
	<!-- <tr align="center"><td>图形名称:&nbsp;&nbsp;<input type="text" id="picName" name="picName" value="量本利分析"/></td></tr>
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

</s:form> -->

