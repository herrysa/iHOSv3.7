<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">

function changeReport(url){
	
	url = escape(url);
	//alert(url);
	
	//(new RegExp(findStr, 'g'), replaceStr) 
	url=	url.replace(new RegExp("%3F", "g"),"?");
	url=	url.replace(new RegExp("%3D", "g"),"=");
	url=	url.replace(new RegExp("%25", "g"),"%");
	url=	url.replace(new RegExp("%26amp%3B", "g"),"&");
	url=	url.replace(new RegExp("%2F", "g"),"/");
	//url=	url.replaceAll("%3D", "=");
	//url=url.replaceAll("%25", "%");
	//url=url.replaceAll("%26", "&");
	//url=url.replaceAll("%2F", "/");
	//alert(url);
	url +="&reportType=1"
	$("#${random}_reportDIV").html("");
	$("#${random}_reportDIV").loadUrl("${ctx}"+url, {}, function(){$("#${random}_reportDIV").find("[layoutH]").layoutH();});
	 
 	/* jQuery.post("${ctx}"+url, function(result){
		//alert(result);
		jQuery("#reportDIV").html(result);
}); */
 
/* var result = jQuery.get("${ctx}"+url);
jQuery("#reportDIV").html(result); */
//alert(result);
}
	function reportChange() {
		var s = jQuery("#${random}_reportselect").val();
		<c:forEach items="${reportGroupList}" var="op">
		
		if(s=='${op.reportName}')
		//	alert('${op.url}');
		changeReport('${op.url}');
		</c:forEach>
/* var rd = jQuery("#reportDIV");
var rurl = jQuery("#reportUrl");
alert(rurl.val()); */

/* 
		alert(s);
		
		
		var options_reportDIV = {};
		options_reportDIV.jqueryaction = "container";
		options_reportDIV.id = "reportDIV";
		
		options_reportDIV.href = '/reportList';
	  

	jQuery.struts2_jquery.bind(jQuery('#reportDIV'),options_reportDIV); */
		//jQuery.publish('reloaddiv1');
	}
/*  	jQuery.subscribe('reloaddiv1', function(event,data) {
        alert('Before request ');
    });  */
</script>
<div  class="page" style="height: 100%;">
	<div class="pageContent" style="height: 100%;">
	<div>
		&nbsp;&nbsp;&nbsp;&nbsp;<label>请选择：</label>
		<select id="${random}_reportselect" onchange="reportChange();">
			<option value=""></option>
			<c:forEach items="${reportGroupList}" var="report">
					<option value="${report.reportName}">${report.reportName}</option>
			</c:forEach>
		</select>
	</div>
	<div id="${random}_reportDIV" class="layoutBox" layoutH=24 style="overflow:hidden !important"></div>
	</div>
</div>
