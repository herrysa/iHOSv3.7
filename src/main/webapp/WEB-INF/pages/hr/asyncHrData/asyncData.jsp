<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		var tabHeight = jQuery("#container").innerHeight();
		jQuery("#asyncDataMain").css("padding",0);
		jQuery("#asyncDataMain").outerHeight(tabHeight-50);
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<div class="tabs" currentIndex="0" eventType="click" >
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="syncHrDataList?syncType=1"  class="j-ajax" ><span><s:text name="从HR同步" /></span></a></li>
						<li><a href="syncHrDataList?syncType=2"  class="j-ajax" ><span><s:text name="同步到HR" /></span></a></li>
					</ul>
				</div>
			</div>
			<div id="asyncDataMain" class="tabsContent" >
				<div></div>
				<div></div>
				<div></div>
				<div></div>
			</div>
		</div>
	</div>
</div>