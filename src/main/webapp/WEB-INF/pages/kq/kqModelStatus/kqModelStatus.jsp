<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		var tabHeight = jQuery("#container").innerHeight();
		jQuery("#kqModelStatusMain").css("padding",0);
		jQuery("#kqModelStatusMain").outerHeight(tabHeight-50);
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<div class="tabs" currentIndex="0" eventType="click" >
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="kqCloseCount"  class="j-ajax" ><span><s:text name="未结账" /></span></a></li>
						<li><a href="kqAntiCount"  class="j-ajax" ><span><s:text name="已结账" /></span></a></li>
					</ul>
				</div>
			</div>
			<div id="kqModelStatusMain" class="tabsContent" >
				<div></div>
				<div></div>
				<div></div>
				<div></div>
			</div>
		</div>
	</div>
</div>