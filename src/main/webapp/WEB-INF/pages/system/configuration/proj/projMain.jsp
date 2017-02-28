<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	jQuery(document).ready(function(){
		//var tabHeight = jQuery("#container").innerHeight();
		jQuery("#projMainTab").css("padding",0);
		tabResize();
		//jQuery("#projMainTab").css("height",tabHeight);
	});
</script>
<div class="pageContent" style="overflow:hidden">
	<div class="tabs" currentIndex="0" eventType="click" tabcontainer="container" extraHeight=30 extraWidth=3>
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="projTypeList" class="j-ajax"><span><s:text name='projTypeList.title'/></span></a></li>
					<li><a href="projNatureList" class="j-ajax"><span><s:text name='projNatureList.title'/></span></a></li>
					<li><a href="projUseList" class="j-ajax"><span><s:text name='projUseList.title'/></span></a></li>
					<li><a href="projList" class="j-ajax"><span><s:text name='projList.title'/></span></a></li>
				</ul>
			</div>
		</div>
		<div id="projMainTab" class="tabsContent" style="height:200px;">
			<div></div>
			<div></div>
			<div></div>
			<div></div>
		</div>
		
	</div>
	
	<p>&nbsp;</p>

</div>
