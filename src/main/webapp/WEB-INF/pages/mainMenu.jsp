<!DOCTYPE html>

<%@page import="com.huge.util.HaspDogHandler"%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/links.jsp"%>
<%@ include file="/common/dwzLinks.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主窗口</title>
<%-- <link REL="SHORTCUT ICON" HREF="${ctx}/style/themes/rzlt_theme/ihos_images/3.png"/> --%>
<link rel="stylesheet" type="text/css" media="all"	href="${ctx}/scripts/zTree/css/zTreeStyle/zTreeStyle.css" />
<%-- <link rel="stylesheet" href="${ctx}/scripts/qunit/qunit.css">
<script src="${ctx}/scripts/qunit/qunit.js"></script>
<script src="${ctx}/scripts/qunittest.js"></script> --%>
<script type="text/javascript">
		

//jQuery.noConflict();
<%response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
response.flushBuffer();%> 

var navTabMaxNum = "${navTabNum}";
var leftMenuType = "${leftMenuType}";

var menuTree;
function menuClick(menuId){
	//navTab.openTab(tabid, url, { title:title, fresh:true, data:{} });
	if(menuId){
		var tab = navTab._getTab("navTab"+menuId);
		nanTabNum = jQuery(".navTab-tab").find("li").length;
		if(!tab){
			if(nanTabNum>=navTabMaxNum){
				navTab._closeTab(1);
			}
		}
	}
	//alert(nanTabNum++);
}
var mainMenuSetting = {
		view: {
			showLine: true,
			selectedMulti: false,
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		async : {
			enable : false,
			url : "${ctx}/getAllRootMenu",
			autoParam : [ "id", "pId" ],
			dataFilter : mainMenuFilter
		},
		callback: {
			onClick: mainMenuOnClick,
			onAsyncSuccess:dealMainMenuTree
		}
	};
	function dealMainMenuTree(event, treeId, treeNode, msg){
		menuTreeId = treeId;
		if(!leftMenuType){
			leftMenuType = "catlog";
		}
		if(leftMenuType=="catlog"){
			jQuery("#menuTree").css("padding","0");
			jQuery("#menuTree").children("li").each(function(){
				$id = jQuery(this).attr("id");
				$a = jQuery(this).find("a").eq(0);
				$icon = $a.find("span").css("background");
				if(jQuery.browser.msie){
					$icon = $a.find("span").css("background-image");
				}
				$ul = jQuery(this).find("ul").eq(0);
				jQuery("#menuTree").append("<div class='accordionHeader' ><h2><span style='background: "+$icon+";background-repeat:no-repeat;background-position:center;'></span>"+$a.text()+"</h2></div><div id='"+$id+"Tree' class='accordionContent' style='margin-left:5px'></div>");
				jQuery("#"+$id+"Tree").append($ul);
				jQuery(this).remove();
			});
			jQuery("#menuBar").removeClass("dwz-accordion");
			$('div.accordion').each(function(){
				var $this = $(this);
				$this.accordion({fillSpace:$this.attr("fillSpace"),alwaysOpen:false,active:0});
			}); 
		}else{
			 $.fn.zTree.getZTreeObj(treeId).expandAll(false);
		}
		//$.ajaxSettings.global = true;
		if (jQuery.browser.msie) {
			window.setTimeout("CollectGarbage();", 10);
		}
	}
	function mainMenuFilter(treeId, parentNode, childNodes) {
		if (!childNodes)
			return null;
		for ( var i = 0, l = childNodes.length; i < l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
		}
		return childNodes;
	}

	function mainMenuOnClick(e, treeId, node) {
		var url = node.actionUrl;
		var param = url.substring(url.indexOf("?")+1);
		if(!node.isParent&&url){
			var openType = 'navTab',title,windowW , windowH;
			if(param){
				openType = getQueryString(param,'openType')||'navTab';
				windowW = getQueryString(param,'width')||600;
				windowH = getQueryString(param,'height')||500;
				title = getQueryString(param,'title')||node.name;
				if(title.indexOf("{")>-1){
					var matchStr = matchRegExp(title);
					if(matchStr='{menuName}'){
						title = title.replace(matchStr,node.name);
					}
				}
			}
			if(openType=='pdialog'){
				$.pdialog.open("${ctx}/menuClicked?menuId="+node.id, "dialog"+node.id, title, {ifr:true,hasSupcan:'all',mask:true,width:windowW,height:windowH}); 
			}else{
				var hideType = getQueryString(param,'hideType')||'0';
				menuClick(node.id);
				navTab.openTab("navTab"+node.id, "${ctx}/menuClicked?menuId="+node.id+"&radomJsp="+Math.floor(Math.random()*10), {hideType:hideType, title:node.name, fresh:false, data:{} });
			}
		}else{
			var thisChildren = node.children;
			if(thisChildren&&thisChildren.length>0){
				$.fn.zTree.getZTreeObj(treeId).expandNode(node, true, false, true);
			}
		}
	}
	function getQueryString(u,name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
		var r = u.match(reg);  //获取url中"?"符后的字符串并正则匹配
		var context = "";  
		if (r != null)  
         context = r[2];  
		reg = null;  
		r = null;  
		return context == null || context == "" || context == "undefined" ? "" : context;  
	}
jQuery(function(){
	jQuery.fn.setSelectionForInput = function(selectionStart, selectionEnd) {
		if(this.lengh == 0) return this;
		input = this[0];
		if (input.createTextRange) {
				var range = input.createTextRange();
				//range.collapse(true);
				range.moveStart('character', selectionStart);
				range.moveEnd('character', selectionEnd);
				range.select();
		} else if (input.setSelectionRange) {
				input.focus();
				input.setSelectionRange(selectionStart, selectionEnd);
		}

		return this;
	}
	
	/* var   wsh   =   new   ActiveXObject("WScript.Shell");   
	 wsh.sendKeys("{F11}")    */
	 
	 if(leftMenuType=="catlog"){
			jQuery("#expand").parent().hide();
	 }
	 
	 DWZ.init("${ctx}/dwz/dwz.frag.xml", {
//			loginUrl:"loginsub.html", loginTitle:"登录",	// 弹出登录对话框
			loginUrl:"login.html",	// 跳到登录页面
			debug:false,	// 调试模式 【true|false】
			callback:function(){
				initEnv();
				jQuery("#themeList").theme({themeBase:"/iHOS/dwz/themes"});
				//alert(jQuery("#sidebar").height());
				//jQuery("#menuBar").height(jQuery("#sidebar").height()-25);
				//$.pdialog.open("initSystemVariable?pagefrom=login", "setSystemVariable", "设置工作环境", {mask:true,maxable:false,width:400,height:200}); 
				jQuery.ajax({
				    url: 'chargeSystemVariable',
				    type: 'post',
				    dataType: 'json',
				    async:false,
				    error: function(data){
				    },
				    success: function(data){
				    	if(data&&data.message=='0'){
							$.pdialog.open("initSystemVariable?pagefrom=login", "setSystemVariable", "设置工作环境", {mask:true,width:470,height:250}); 
				    	}else{
				    		jQuery("#dataBaseName").text(data.systemVariable.dataBaseName);
				        	//jQuery("#orgName").text(data.systemVariable.orgName);
				        	//jQuery("#copyName").text(data.systemVariable.copyName);
				        	jQuery("#businessDate").text(data.systemVariable.businessDate);
				        	//jQuery("#orgCode").text(data.systemVariable.orgCode);
				        	//jQuery("#copyCode").text(data.systemVariable.copyCode);
				        	jQuery("#currentPeriod").text(data.systemVariable.period);
				    		bulidMenuTree();
				    		jQuery("#ihosLogo").removeClass();
				        	var logoName = "";
				        	if(data.systemVariable.currentRootMenu=='10'){
				        		logoName = "hr";
				        	}else if(data.systemVariable.currentRootMenu=='01'){
				        		logoName = "cb";
				        	}else if(data.systemVariable.currentRootMenu=='06'){
				        		logoName = "jx";
				        	}else if(data.systemVariable.currentRootMenu=='11'){
				        		logoName = "gz";
				        	}
				        	jQuery("#ihosLogo").addClass("logo"+logoName);
				        	var url = data.systemVariable.subSysMainPage;
				        	url = url.substring(1);
				        	if(url&&url!='empty.jsp'){
				        		jQuery("#mainPage").loadUrl(url);
				        	}else{
				        		jQuery("#mainPage").loadUrl("mainPage");
				        	}
				    	}
				    }
				});
				
				//menuTree = $.fn.zTree.init($("#menuTree"), setting);
				/* var waitSv = setInterval(function(){
					if(systemVariableReay==1){
						
						bulidMenuTree();
						clearInterval(waitSv);
					}
				},100); */
			}
		}); 
	 
	
	//var data = {"id":"aaa"};
	//jQuery.publish("subscribeTest",data,null);
	//jQuery("#rightPanel").css("margin-left",5); 
	var fullSize = $(window).height() - jQuery("#header").height() - 34;
	var triH = (fullSize-170)/3;
	var halH = (fullSize-126)/2;
	jQuery("#leftPanel").find(".panel").each(function(){
		jQuery(this).find("div").eq(0).height(triH);
	});
	jQuery("#rightPanel").find(".panel").each(function(){
		jQuery(this).find("div").eq(0).height(halH);
	});
/* 	setInterval(function(){
		$("#background,#progressBar").remove();
		$.ajax({
		    url: 'userIsTimeout',
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    ajaxStart:function(){
		    	$("#background,#progressBar").hide();
		    },
		    error: function(data){
		        //jQuery('#name').attr("value",data.responseText);
		       // alert('error');
		    },
		    success: function(data){
		        // do something with xml
		        if(data.message=='true'){
		        	$.ajax({
		    		    url: 'alreadyTichu',
		    		    type: 'post',
		    		    dataType: 'json',
		    		    aysnc:false,
		    		    success: function(data){
		    		    	alert("您已被系统管理员踢出！");
	    		        	window.location.href = "${ctx }/j_security_logout";
		    		    }
		    		});loading
		        }
		    }
		});
	},5000); */
	
	
	
	
	jQuery("#bulletinTitle").find("span").each(function(){
		var title = jQuery(this).find("a").eq(0).text();
		if(title.length>30){
			title = title.substring(0,30)+"...";
			jQuery(this).find("a").eq(0).text(title);
		}
	});
	var treeStatus = 0;
	jQuery("#expand").click(function(){
		if(treeStatus==1){
			menuTree.expandAll(false);
			treeStatus = 0;
			jQuery(this).text("展开");
		}else{
			menuTree.expandAll(true);
			treeStatus = 1;
			jQuery(this).text("收缩");
		}
		
	});

	if(false){
		var bodyhtml = jQuery("body").html();
		jQuery("body").html("<div id='qunit'><div id='qunit-fixture'/>"+bodyhtml+"</div></div>");
		exequnittest();
	}
});

function bulidMenuTree(){
	$.get("findAlowMuens", {
		"_" : $.now()
	}, function(data) {
		var alowMenus = data.alowMenus;
		$.fn.zTree.init($("#menuTree"), mainMenuSetting,alowMenus);
		dealMainMenuTree();
	});
}

function setSv(){
	/* J('#systemvariableDiv').dialog({ title:'示例', cover:true,
		html:'<p>lhgdialog</p>'}); */
	$.pdialog.open("initSystemVariable?pagefrom=mainmenu", "setSystemVariable", "设置工作环境", {ifr:true,hasSupcan:'all',mask:true,maxable:false,width:470,height:250}); 
}

//清理浏览器内存,只对IE起效,FF不需要
if (jQuery.browser.msie) {
	window.setInterval("CollectGarbage();", 10000);
}




//绑定控件 调用此函数实现调用调色板
// function ShowColorControl(controlId)
// {
//     $("#" + controlId).bind("click", OnDocumentClick);
// }
//<div id="colorpanel" style="position:absolute;display:none;width:253px;height:177px;"></div> 在页面中加载此HTML
// var ColorHex = new Array('00', '33', '66', '99', 'CC', 'FF')
// var SpColorHex = new Array('FF0000', '00FF00', '0000FF', 'FFFF00', '00FFFF', 'FF00FF')
// var current = null

//初始化调色板
// function initPanel() {
//     var colorTable = ''
//     for (i = 0; i < 2; i++) {
//         for (j = 0; j < 6; j++) {
//             colorTable = colorTable + '<tr style="height:12px;">'
//             colorTable = colorTable + '<td  style="width=11px;height:12px;background-color:#000000">'

//             if (i == 0) 
//                 colorTable = colorTable + '<td style="width=11px;height:12px;background-color:#' + ColorHex[j] + ColorHex[j] + ColorHex[j] + '">'
//             else 
//                 colorTable = colorTable + '<td style="width=11px;height:12px;background-color:#' + SpColorHex[j] + '">'

//             colorTable = colorTable + '<td style="width=11px;height:12px;background-color:#000000">'
//             for (k = 0; k < 3; k++) {
//                 for (l = 0; l < 6; l++) 
//                     colorTable = colorTable + '<td style="width=11px;height:12px;background-color:#' + ColorHex[k + i * 3] + ColorHex[l] + ColorHex[j] + '">'
//             }
//         }
//     }
//     colorTable = '<table width=253 border="0" cellspacing="0" cellpadding="0" style="border:1px #000000 solid;border-bottom:none;border-collapse: collapse" bordercolor="000000">'
//            + '<tr height="30px"><td colspan=21 bgcolor=#cccccc>'
//            + '<table cellpadding="0" cellspacing="1" border="0" style="border-collapse: collapse">'
//            + '<tr><td width="3"><input type="text" id="DisColor" size="6" disabled style="border:solid 1px #000000;background-color:#ffff00">'
//            + '<td width="3"><input type="text" id="HexColor" size="7" style="border:inset 1px;font-family:Arial;" value="#000000"><td align="right" width="100%"><span id="spnClose" style="cursor:hand;">Ⅹ</span>&nbsp;</tr></table></table>'
//            + '<table  width=253  id="tblColor" border="1" cellspacing="0" cellpadding="0" style="border-collapse: collapse" bordercolor="000000"  style="cursor:hand;">'
//            + colorTable + '</table>';
//     $("#colorpanel").html(colorTable);
//     $("#tblColor").bind("mouseover", doOver);
//     $("#tblColor").bind("mouseout", doOut);
//     $("#tblColor").bind("click", doclick);
//     $("#spnClose").bind("click", function() { $("#colorpanel").css("display","none"); });
// }

//鼠标覆盖事件
// function doOver(event) {

//     var e = $.event.fix(event);
//     var element = e.target;
//     if ((element.tagName == "TD") && (current != element)) {

//         if (current != null) { current.style.backgroundColor = current.style.background; }
//         element.style.background = element.style.backgroundColor;
//         $("#DisColor").css("backgroundColor", element.style.backgroundColor);
//         var clr = element.style.backgroundColor.toUpperCase();
//         if (clr.indexOf('RGB') > -1) {
//             clr = clr.substring(clr.length - 18);
//             clr = rgb2hex(clr);
//         }
//         $("#HexColor").val(clr);
//         current = element;
//     }
// }
//鼠标移开事件
// function doOut(event) {
//     if (current != null) current.style.backgroundColor = current.style.background.toUpperCase();
// }
//鼠标点击事件
// function doclick(event) {
//     var e = $.event.fix(event);
//     if (e.target.tagName == "TD") {
//         var clr = e.target.style.background;
//         clr = clr.toUpperCase();
//         if (targetElement) {
//             if (clr.indexOf('RGB') > -1) {
//                 clr = clr.substring(clr.length - 18);
//                 clr = rgb2hex(clr);
//             }
//             targetElement.value = clr; 
//         }
//         DisplayClrDlg(false, e);
//         return clr;
//     }
// }
// var targetElement = null;
// function OnDocumentClick(event) {
//     var e = $.event.fix(event);
//     var srcElement = e.target;
//         targetElement = srcElement;
//         DisplayClrDlg(true, e);
// }

//显示颜色对话框
//display 决定显示还是隐藏
//自动确定显示位置
// function DisplayClrDlg(display, event) {

//     var clrPanel = $("#colorpanel");
//     if (display) {
//         var left = document.body.scrollLeft + event.clientX;
//         var top = document.body.scrollTop + event.clientY;
//         if (event.clientX + clrPanel.width > document.body.clientWidth) {
//             //对话框显示在鼠标右方时，会出现遮挡，将其显示在鼠标左方
//             left -= clrPanel.width;
//         }
//         if (event.clientY + clrPanel.height > document.body.clientHeight) {
//             //对话框显示在鼠标下方时，会出现遮挡，将其显示在鼠标上方
//             top -= clrPanel.height;
//         }

//         clrPanel.css("left", left);
//         clrPanel.css("top", top);
//         clrPanel.css("display", "block");
//     }
//     else 
//         clrPanel.css("display", "none");
// }

// $(document).ready(function() {
//     initPanel();
// });

//RGB转十六进制颜色值
// function zero_fill_hex(num, digits) {
//     var s = num.toString(16);
//     while (s.length < digits)
//         s = "0" + s;
//     return s;
// }

// function rgb2hex(rgb) {
//     if (rgb.charAt(0) == '#')
//         return rgb;
//     var n = Number(rgb);
//     var ds = rgb.split(/\D+/);
//     var decimal = Number(ds[1]) * 65536 + Number(ds[2]) * 256 + Number(ds[3]);
//     return "#" + zero_fill_hex(decimal, 6);
// }
/* jQuery.subscribe('subscribeTest', function(event, data) {
	alert(data.id);
}); */
</script>

<!-- <style type="text/css">
.ztree li a.level0 {width:200px;height: 20px; text-align: center; display:block; background-color: #0B61A4; border:1px silver solid;}
.ztree li a.level0.cur {background-color: #66A3D2; }
.ztree li a.level0 span {display: block; color: white; padding-top:3px; font-size:12px; font-weight: bold;word-spacing: 2px;}
.ztree li a.level0 span.button {	float:right; margin-left: 10px; visibility: visible;display:none;}
.ztree li span.button.switch.level0 {display:none;}
	</style> -->
<style type="text/css">
.ztree {margin:0; padding:0px; color:#000}
ul.ztree {margin-top: 0px;border:0px;background: white;width:200px;height:100%;overflow-y:auto;overflow-x:auto;}
.ztree li {line-height:20px}

</style>
<style media="print">
  .Noprint { DISPLAY: none }
  .PageNext{ PAGE-BREAK-AFTER: always }
</style>
<!-- <style>
.ui-state-highlight, .ui-widget-content .ui-state-highlight, .ui-widget-header .ui-state-highlight  {border: 1px solid #fad42e; background: red url(images/ui-bg_flat_55_fbec88_40x100.png) 50% 50% repeat-x; color: #363636; }
.ui-state-hover, .ui-widget-content .ui-state-hover, .ui-widget-header .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus, .ui-widget-header .ui-state-focus { border: 1px solid #79b7e7; background: #d0e5f5 url(images/ui-bg_glass_75_d0e5f5_1x400.png) 50% 50% repeat-x; font-weight: bold; color: #1d5987; }
</style> -->

<!-- <style>

.ui-jqgrid tr.jqgfirstrow td {
padding: 0;
border-right-width: 0px;
border-right-style: solid;
}

</style> -->


</head> 


<body>
	<div id="layout">
		<div id="header">
			<div class="headerNav">
				<a id="ihosLogo" class="logo" href="javaScript:" style="width:350px;cursor: default;"></a>
				<%-- <label style="position: relative;left:40%;top:50%">单位名称：客户演示五</label>
				<label style="position: relative;left:50%;top:50%">核算期间：201212</label>
				<label style="position: relative;left:60%;top:50%">部门：系统</label>
				<label style="position: relative;left:70%;top:50%">操作员：系统管理员</label>
				<a href="${ctx}/logout"><img
					style="position: absolute;top:13px;right:60px;width:22px;height:22px;" src="${ctx}/styles/themes/${themeName}/ihos_images/log-out.png" />
				</a> --%>
				<ul class="nav" style="margin-top:18px">
					<li style="line-height: 18px ;color:gold  " id="optOrgLi">单位名称：<%=HaspDogHandler.getInstance(  ).getDogService(  ).getHospitalName(  ) %></li>
					<%-- <li style="line-height: 18px;color:gold " id="curPeriodInMain">核算期间：${currentPeriod}</li> --%>
					<li style="line-height: 18px;color:gold " id="curPeriodInMain">核算期间：<label id="currentPeriod"></label></li>
					<li style="line-height: 18px;color:gold ">部门：${currentUser.person.department.name}</li>
					<li style="line-height: 18px;color:gold ">操作员：${currentUser.person.name}</li>
					<li style="line-height: 18px;color:gold;display:none" id="curPerson">${currentUser.person.personId}</li>
					<li style="line-height: 18px;">
					<a href="javaScript:setSv()">环境设置</a></li>
					<li style="line-height: 18px;">
					<a href="${ctx }/j_security_logout">退出</a></li>
					<!-- <li><a href="login.html">退出</a></li> -->
				</ul>
				<!-- <ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div></li>
					<li theme="green"><div>绿色</div></li>
					<li theme="red"><div>红色</div></li>
					<li theme="purple"><div>紫色</div></li>
					<li theme="silver"><div>银色</div></li>
					<li theme="azure"><div>天蓝</div></li>
				</ul> -->
			</div>

			<!-- navMenu -->
			
		</div>
<div style="z-index:300;position:absolute;left:150px;top:65px;font-size:10px">
					<a style="TEXT-DECORATION:none" href="javaScript:" id="expand">展开</a></div>
		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>
				<div id="menuBar" class="accordion" fillSpace="sidebar">
					<ul id="menuTree" class="ztree" style="overflow:auto;" >
					</ul>
				</div>
			</div>
		</div>
		<div id="container" >
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div id="mainPage" class="page unitBox" style="height:100%">
						<div class="pageFormContent" style="background:#e4ebf6;height:100%">
						<%-- <div id="leftPanel" style="width:60%;float: left;margin-top:-5px" class="unitBox">
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
					</div>	--%>
					</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<div id="footer">
	
	<ul style="float:left;width:100%">
			<li style="float:right;width:33%;text-align: right">
				<label style="margin-right:10px">业务日期：<span id="businessDate"></span></label>
			</li>
			
			<li style="float:right;width:33%">
				<label>Copyright 2012 北京瑞志龙腾科技有限公司</label>
			</li>
			<li style="float:right;width:33%;text-align: left">
				<label style="margin-left:10px">当前数据库：<span id="dataBaseName"></span></label>
			</li>
		</ul>
    </div>
    <div class="progress" style="display:none">
    	<span class="progress-val">0%</span>
    	<span class="progress-bar"><span class="progress-in" style="width: 0%"></span></span>
  	</div>
<!-- <div id="colorpanel"style="position:absolute;display:none;width:253px;height:177px; z-index:1000"></div> -->
<sj:dialog id="infodialog"
	buttons="{'OK':function() { jQuery( this ).dialog( 'close' ); }}" autoOpen="false"
	modal="true" title="%{getText('messageDialog.title')}" />

	<script>
	function testFunction2(){
			
		}
	</script>
</body>
</html>
