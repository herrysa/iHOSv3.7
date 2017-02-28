<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script>
	jQuery(document).ready(function(){
		tabResize();
	});
</script>
<div class="page">
	<div class="pageContent">
	<div class="tabs" currentIndex="0" eventType="click" id="bmAssistTypeTabs" tabcontainer="bmModelFormPage" extraHeight=35 extraWidth=6>
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<s:iterator value="budgetAssistTypes" status="it">
						<li><a href="bmModelAssistList?layoutH=162&modelId=${modelId }&assistCode=<s:property value="assistType.typeCode"/>" class="j-ajax"><span><s:property value="colName"/></span> </a></li>
					</s:iterator>
				</ul>
			</div>
		</div>
		<div id="bmAssistTypeTabsContent" class="tabsContent" style="height: 250px;padding:0px">
			<div></div>
			<div></div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
	</div>
</div>