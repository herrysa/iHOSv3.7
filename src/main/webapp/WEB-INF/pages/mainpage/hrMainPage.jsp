<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<script>
	jQuery(function(){
		var fullSize = $(window).height() - jQuery("#header").height() - 34;
		var triH = (fullSize-170)/3;
		var halH = (fullSize-126)/2;
		jQuery("#hr_leftPanel").find(".panel").each(function(){
			jQuery(this).find("div").eq(0).height(triH);
		});
		jQuery("#hr_rightPanel").find(".panel").each(function(){
			jQuery(this).find("div").eq(0).height(halH);
		});
		var arrowMoveSpace = 0;
		setInterval(function(){
			if(arrowMoveSpace==5){
				arrowMoveSpace=0;
			}
			jQuery("span[name=alertArrow]").css("margin-left",(arrowMoveSpace++)+'px');
			jQuery("span[name=alertArrowSpace]").css("width",(5-arrowMoveSpace)+'px');
		},100); 
	});
</script>

<!-- <div class="page"> -->
	<div class="pageFormContent" >
	<div id="hr_leftPanel" style="width:60%;height:200px;float: left;margin-top:-5px" class="unitBox">
		<div class="panel" >
			<h1>合同到期预警<span style="float:right;margin-top:7px;"><a href="pactExpireViewList" target="navTab" title="到期合同列表" style="color:#15428B" rel="bulletin">更多>></a></span></h1>
			<div>
				<div style="margin-left:20px;margin-top:10px" id="expirePact">
				<s:iterator value="pacts" status="it" >
					<span style="margin-top:10px">
					<s:property value='#it.index+1'/>、
					<span name='alertArrow'><img alt="" src="${ctx}/styles/themes/rzlt_theme/ihos_images/rightArrow.png"></span>
					<span name='alertArrowSpace' style="width:5px;display: inline-block;"></span>
					<a href="editPact?oper=view&id=<s:property value='id'/>" target="dialog" title="查看合同信息" width="620" height="420" style="color:blue"><s:property value='hrPerson.name'/></a>
					</span>
					<span style="float:right;margin-right:20px">
						<s:date name="operateDate" format="yyyy-MM-dd HH:mm:ss" id="operateDate"/>
						<s:property value='%{operateDate}'/>
					</span>
					<br/><br/>
				</s:iterator>
				</div>
			</div>
		</div>
	
		<div class="panel" style="margin-top:5px">
			<h1>待办任务<span style="float:right;margin-top:7px;"><a href="javaScript:" target="navTab" title="" style="color:#15428B">更多>></a></span></h1>
			<div>
				<div style="margin-left:20px;margin-top:10px" >
				<s:iterator value="byLawList" status="it" >
					<span style="margin-top:10px">
					<s:property value='#it.index+1'/>、 
					<img alt="" src="${ctx}/styles/themes/rzlt_theme/ihos_images/rightArrow.png">&nbsp;&nbsp;
					<a href="showByLaw?byLawId=<s:property value='byLawId'/>" target="dialog" width="880" title="规章制度" height="600" style="color:blue"><s:property value='title'/></a>
					</span>
					<br/><br/>
				</s:iterator>
				</div>
			</div>
		</div>
	
		<div class="panel"  style="margin-top:5px">
			<h1>常用统计<span style="float:right;margin-top:7px;"><a href="statisticsItemCheckList" target="navTab" title="常用统计列表"  style="color:#15428B">更多>></a></span></h1>
			<div>
				<div style="margin-left:20px;margin-top:10px" >
				<s:iterator value="statisticsItems" status="it" >
					<span style="margin-top:10px">
					<s:property value='#it.index+1'/>、 
					<img alt="" src="${ctx}/styles/themes/rzlt_theme/ihos_images/rightArrow.png">&nbsp;&nbsp;
					<a href="previewStatisticsItem?popup=true&id=<s:property value='id'/>&statisticsCode=hr_person" target="dialog" width="800" title="<s:property value='name'/>" [mask=true,width=800] height="600" style="color:blue"><s:property value='name'/></a>
					</span>
					<br/><br/>
				</s:iterator>
				</div>
			</div>
		</div>
		</div>		
		<div id="hr_rightPanel" style="margin-left:60.3%;margin-top:-5px" class="unitBox">
			<div class="panel" >
				<h1>通知<span style="float:right;margin-top:7px;"><a href="userBulletinList" target="navTab" title="通知列表" style="color:#15428B">更多>></a></span></h1>
				<div>
					<div style="margin-left:20px;margin-top:10px" id="bulletinTitle">
					<s:iterator value="bulletins" status="it" >
						<span style="margin-top:10px">
						<s:property value='#it.index+1'/>、 
						<img alt="" src="${ctx}/styles/themes/rzlt_theme/ihos_images/rightArrow.png">&nbsp;&nbsp;
						<a href="showBulletin?bulletinId=<s:property value='bulletinId'/>" target="dialog" title="公告" width="880" height="600" style="color:blue"><s:property value='subject'/></a>
						</span>
						<span style="float:right;margin-right:20px">
							<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" id="createTimeId"/>
							<s:property value='%{createTimeId}'/>
						</span>
						<br/><br/>
					</s:iterator>
					</div>
				</div>
			</div>
			<div class="panel" style="margin-top:5px">
				<h1>常用查询<span style="float:right;margin-top:7px;"><a href="queryCommonCheckList" target="navTab" title="常用查询列表" style="color:#15428B">更多>></a></span></h1>
				<div>
					<div style="margin-left:20px;margin-top:10px" >
					<s:iterator value="queryCommons" status="it" >
						<span style="margin-top:10px">
						<s:property value='#it.index+1'/>、 
						<img alt="" src="${ctx}/styles/themes/rzlt_theme/ihos_images/rightArrow.png">&nbsp;&nbsp;
						<a href="previewQueryCommon?popup=true&id=<s:property value='id'/>" target="dialog" width="880" title="<s:property value='name'/>" height="600" style="color:blue"><s:property value='name'/></a>
						</span>
						<br/><br/>
					</s:iterator>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- </div> -->