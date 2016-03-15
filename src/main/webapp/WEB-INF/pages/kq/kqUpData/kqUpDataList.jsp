<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		var tabHeight = jQuery("#container").innerHeight();
		jQuery("#kqUpDataMain").css("padding",0);
		jQuery("#kqUpDataMain").outerHeight(tabHeight-50);
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<div class="tabs" currentIndex="0" eventType="click" >
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="kqDayDataList"  class="j-ajax" ><span>日考勤上报</span></a></li>
						<li><a href="kqMonthDataList"  class="j-ajax" ><span>月考勤上报</span></a></li>
					</ul>
				</div>
			</div>
			<div id="kqUpDataMain" class="tabsContent" >
				<div></div>
				<div></div>
				<div></div>
				<div></div>
			</div>
		</div>
	</div>
</div>