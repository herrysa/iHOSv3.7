<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	jQuery(document).ready(function(){
		var tabHeight = jQuery("#container").innerHeight();
		jQuery("#mainTaskTab").css("padding",0);
		jQuery("#mainTaskTab").css("height",tabHeight);
		//alert("${param.menuId}");
	});
</script>
<div class="pageContent">
	<div class="tabs" currentIndex="0" eventType="click" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="showPeriodSum?popup=true&menuId=${param.menuId }" class="j-ajax"><span><fmt:message key='dataCollectionTask.sumTab'/></span></a></li>
					<li><a href="dataCollectionCompleteTaskList?popup=true&menuId=${param.menuId}" class="j-ajax"><span><fmt:message key='dataCollectionTask.completedTab'/></span></a></li>
					<li><a href="dataCollectionRemainTaskList?popup=true&menuId=${param.menuId}" class="j-ajax"><span><fmt:message key='dataCollectionTask.remainTab'/></span></a></li>
				</ul>
			</div>
		</div>
		<div id="mainTaskTab" class="tabsContent" style="height:800px;">
			<div>
			</div>
			<div></div>
			<div></div>
		</div>
		
	</div>
	
	<p>&nbsp;</p>

</div>
