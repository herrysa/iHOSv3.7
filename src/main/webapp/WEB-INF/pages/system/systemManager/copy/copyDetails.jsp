<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	jQuery(document).ready(function(){
		var tabHeight = jQuery("#container").innerHeight();
		jQuery("#copyDetailsMain").css("padding",0);
		jQuery("#copyDetailsMain").outerHeight(tabHeight-50);
	});
</script>
<div class="page">
<div class="pageContent">
	<div class="tabs" currentIndex="0" eventType="click" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="showMainInfoDetails"  class="j-ajax" ><span><s:text name="copyDetails.mainInfo" /></span></a></li>
					<li><a href="showNormalInfoDetails"  class="j-ajax"><span><s:text name='copyDetails.normalInfo'/></span></a></li>
					<li><a href="showVoucherControlDetails"  class="j-ajax"><span><s:text name='copyDetails.voucherControl'/></span></a></li>
					<li><a href="showBudgetControlDetails"  class="j-ajax"><span><s:text name='copyDetails.budgetControl'/></span></a></li>
					<li><a href="showAuditControlDetails"  class="j-ajax"><span><s:text name='copyDetails.auditControl'/></span></a></li>
				</ul>
			</div>
		</div>
		<div id="copyDetailsMain" class="tabsContent" >
			<div></div>
			<div></div>
			<div></div>
			<div></div>
			<div></div>
		</div>
	</div>
</div>
</div>
