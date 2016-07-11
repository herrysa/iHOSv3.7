<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script>
	jQuery(document).ready(function(){
	});
</script>
<div class="page">
	<div class="pageContent">
	<div class="tabs" currentIndex="0" eventType="click" id="bmDeptTabs" tabcontainer="container" extraHeight=30 extraWidth=6>
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="bmDepartmentList" class="j-ajax"><span>预算部门定义</span> </a></li>
					<li><a href="#" ><span>关系查询</span> </a></li>
				</ul>
			</div>
		</div>
		<div id="bmDeptTabsContent" class="tabsContent"
			style="height: 250px;padding:0px">
			<div></div>
			<div></div>
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
	</div>
</div>
