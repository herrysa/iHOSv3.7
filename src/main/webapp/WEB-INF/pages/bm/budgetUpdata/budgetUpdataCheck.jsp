<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		/*jQuery('#savelink').click(function() {
			jQuery("#budgetModelForm").attr("action","saveBudgetModel?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#budgetModelForm").submit();
		});*/
		tabResize();
	});
</script>
</head>

<div class="page" id="bmUpdataCheckPage" style="height:100%">
	<div class="pageContent" style="height:100%">
		<div class="tabs" currentIndex="0" eventType="click" id="bmUpdataCheckTabs" tabcontainer="container" extraHeight=30 extraWidth=2>
			<div class="tabsHeader">
				<div class="tabsHeaderContent">
					<ul>
						<s:iterator value="bmCheckSteps" status="it">
						<li><a href="budgetUpdataList?upType=1&stepCode=<s:property value="stepCode"/>" class="j-ajax"><span><s:property value="stepName"/></span> </a></li>
						</s:iterator>
					</ul>
				</div>
			</div>
			<div id="bmUpdataCheckTabsContent" class="tabsContent"
				style="height: 250px;padding:0px">
				<s:iterator value="bmCheckSteps" status="it">
				<div></div>
				</s:iterator>
			</div>
			<div class="tabsFooter">
				<div class="tabsFooterContent"></div>
			</div>
		</div>
	</div>
</div>
				