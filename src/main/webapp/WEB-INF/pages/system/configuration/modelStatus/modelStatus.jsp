<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		var tabHeight = jQuery("#container").innerHeight();
		jQuery("#${random}_modelStatusMain").css("padding",0);
		jQuery("#${random}_modelStatusMain").outerHeight(tabHeight-50);
		var hrefTemp = "modelStatusGeneral?subSystemCode=${subSystemCode}&jzType=${jzType}";
		hrefTemp = encodeURI(hrefTemp);
		jQuery("#${random}_modelStatusGeneral").attr("href",hrefTemp);
		
		hrefTemp = "modelStatusInit?subSystemCode=${subSystemCode}&jzType=${jzType}";
		hrefTemp = encodeURI(hrefTemp);
		jQuery("#${random}_modelStatusInit").attr("href",hrefTemp);
		
		hrefTemp = "modelStatusCloseCount?subSystemCode=${subSystemCode}&jzType=${jzType}";
		hrefTemp = encodeURI(hrefTemp);
		jQuery("#${random}_modelStatusCloseCount").attr("href",hrefTemp);
		
		hrefTemp = "modelStatusAntiCount?subSystemCode=${subSystemCode}&jzType=${jzType}";
		hrefTemp = encodeURI(hrefTemp);
		jQuery("#${random}_modelStatusAntiCount").attr("href",hrefTemp);
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<div class="tabs" currentIndex="0" eventType="click" >
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a id="${random}_modelStatusGeneral"  class="j-ajax" ><span><s:text name="概况" /></span></a></li>
						<li><a id="${random}_modelStatusInit"  class="j-ajax" ><span><s:text name="启用" /></span></a></li>
						<li><a id="${random}_modelStatusCloseCount" class="j-ajax"><span><s:text name='结账'/></span></a></li>
						<li><a id="${random}_modelStatusAntiCount"  class="j-ajax"><span><s:text name='反结账'/></span></a></li>
					</ul>
				</div>
			</div>
			<div id="${random}_modelStatusMain" class="tabsContent" >
				<div></div>
				<div></div>
				<div></div>
				<div></div>
			</div>
		</div>
	</div>
</div>





