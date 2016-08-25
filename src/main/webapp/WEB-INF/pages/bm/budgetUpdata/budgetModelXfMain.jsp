<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		tabResize();
	});
</script>
</head>

<div class="page" style="height:100%">
	<div class="pageContent" style="height:100%;overflow: hidden;">
		<div class="tabs" currentIndex="1" eventType="click" id="bmModelXfTabs" tabcontainer="bmModelXfPage" extraHeight=1 extraWidth=2>
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<li><a href="budgetModelXfList?state=0" class="j-ajax"><span>未上报</span> </a></li>
						<li><a href="budgetModelXfList?state=1" class="j-ajax"><span>上报中</span> </a></li>
						<li><a href="budgetModelXfList?state=2" class="j-ajax"><span>已上报</span> </a></li>
						<li><a href="budgetModelXfList?state=3" class="j-ajax"><span>已过期</span> </a></li>
					</ul>
				</div>
			</div>
			<div id="bmModelXfTabsContent" class="tabsContent" style="padding:0px">
				<div></div>
				<div></div>
				<div></div>
				<div></div>
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
	</div>
</div>





