<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<script>
	jQuery(function(){
		var fullSize = $(window).height() - jQuery("#header").height() - 34;
		var triH = (fullSize-170)/3;
		var halH = (fullSize-126)/2;
		jQuery("#leftPanel").find(".panel").each(function(){
			jQuery(this).find("div").eq(0).height(triH);
		});
		jQuery("#rightPanel").find(".panel").each(function(){
			jQuery(this).find("div").eq(0).height(halH);
		});
	});
</script>

<!-- <div class="page"> -->
	<div class="pageFormContent" style="background:#e4ebf6;height:100%">
		<div id="leftPanel" style="width:60%;float: left;margin-top:-5px" class="unitBox">
			<div class="panel">
				<h1>公告<span style="float:right;margin-top:7px;"><a href="userBulletinList" target="navTab" title="公告列表" style="color:#15428B" rel="bulletin">更多>></a></span></h1>
				<div>
					<div style="margin-left:20px;margin-top:10px" id="bulletinTitle">
					<s:iterator value="bulletinList" status="it" >
						<span style="margin-top:10px">
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
				<h1>规章制度<span style="float:right;margin-top:7px;"><a href="userByLawList" target="navTab" title="规整制度列表" style="color:#15428B" rel="bylaw">更多>></a></span></h1>
				<div>
					<div style="margin-left:20px;margin-top:10px" >
					<s:iterator value="byLawList" status="it" >
						<span style="margin-top:10px">
						<img alt="" src="${ctx}/styles/themes/rzlt_theme/ihos_images/rightArrow.png">&nbsp;&nbsp;
						<a href="showByLaw?byLawId=<s:property value='byLawId'/>" target="dialog" width="880" title="规章制度" height="600" style="color:blue"><s:property value='title'/></a>
						</span>
						<br/><br/>
					</s:iterator>
					</div>
				</div>
			</div>

			<div class="panel"  style="margin-top:5px">
				<h1>工作便签<span style="float:right;margin-top:7px;"><a href="javaScript:;" style="color:#15428B">更多>></a></span></h1>
				<div>
					
				</div>
			</div>
		</div>		
	<div id="rightPanel" style="margin-left:60.3%;margin-top:-5px" class="unitBox">
		<div class="panel" >
			<h1>可申请信息<span style="float:right;margin-top:7px;"><a href="javaScript:;" style="color:#15428B">更多>></a></span></h1>
			<div>
				
			</div>
		</div>
		<div class="panel" style="margin-top:5px">
			<h1>计划任务<span style="float:right;margin-top:7px;"><a href="javaScript:;" style="color:#15428B">更多>></a></span></h1>
			<div>
				<div style="margin-left:20px;margin-top:10px">
					
					</div>
			</div>
		</div>
	</div>
	</div>
<!-- </div> -->