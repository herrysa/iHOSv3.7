<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	/* jQuery(document).ready(function(){
		var tabHeight = jQuery("#container").innerHeight();
		jQuery("#mainTaskTab").css("padding",0);
		jQuery("#mainTaskTab").css("height",tabHeight);
	}); */
	jQuery(function() {
		console.log("${menuId}");
	});
</script>
<div class="pageContent">
	<div class="tabs" currentIndex="0" eventType="click" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="query?searchName=jj2cx&checkPeriod=%25HSQJ%25&popup=true&reportType=2" class="j-ajax"><span>下发绩效工资查询</span></a></li>
					<li><a href="jjAllotList?popup=true&menuId=${menuId}" class="j-ajax"><span>科室间绩效分配</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div>
			</div>
			<div></div>
			<div></div>
		</div>
		
	</div>
	
</div>