<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<script>
jQuery(document).ready(function() {
	var tabHeight = jQuery("#container").innerHeight()-28;
	var upCostTabPanel = new TabPanel({  
        renderTo:'${random}upCost_Tabs_List',  
        width:'100%',  
        height:tabHeight+"px",  
        autoResizable:true,
        heightResizable:true,
        widthResizable:true,
        active : 0,
        items : [
			<c:forEach items="${upItems}" var="up_item">
				{id:"upItemTab${up_item.id}",title:"${up_item.itemName}",html:"<div id='${up_item.id}_div' ></div>",closable: true},
			</c:forEach>
        ],
        afterShow : function(){
        	jQuery(window).trigger("gridResize");
        }
    });
	<c:forEach items="${upItems}" var="up_item">
	var itemUrl = "upCostList?upItemId=${up_item.id}&upItemType=${up_item.upItemType}&taskName=${up_item.taskName}&tablecontainer=${random}upCost_Tabs_List&menuId=${menuId}";
	//upCostTabPanel.addTab({id:"upItemTab${up_item.id}",title:"${up_item.itemName}",html:"<div id='${up_item.id}_div'></div>",closable: true});
	jQuery("#${up_item.id}_div").loadUrl(itemUrl);
	</c:forEach>
	//upCostTabPanel.setRenderWH({width:'100%', height:'100%'});
		//setTimeout(function(){
			//upCostTabPanel.show(0,false);
		//},300);
		$(window).resize(function(){
			setTimeout(function(){
				var tabHeightTemp = jQuery("#container").innerHeight()-28;
				jQuery("#${random}upCost_Tabs_List").css("height",tabHeightTemp+"px");
				setTimeout(function(){
					jQuery(window).trigger("gridResize");
				},100);
			},100);
		});
		
	});
</script>

<div class="page">
	<div class="pageContent">
	<div id="${random}upCost_Tabs_List" style="height:100%">
		
	</div>
	<%-- <div class="tabs" currentIndex="0" eventType="click" >
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
				<s:iterator value="upItems">
					<li><a href="upCostList?upItemId=<s:property value='id'/>&upItemType=<s:property value='upItemType'/>&taskName=<s:property value='taskName'/>" class="j-ajax"><span><s:property value="itemName"/></span></a></li>
				</s:iterator>
				</ul>
			</div>
		</div>
		<div id="mainTaskTab" class="tabsContent" style="height:1000px;padding:0">
			<s:iterator value="upItems">
			<div></div>
			</s:iterator>
		</div> --%>
		
	</div>
</div>