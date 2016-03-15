<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <%@ page errorPage="/error.jsp"%>
<%@page import="com.huge.util.HaspDogHandler"%>
<%@page import="java.util.Map,com.huge.ihos.multidatasource.DynamicDatabaseManager,com.huge.webapp.util.SpringContextHelper"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/links.jsp"%>
<%@ include file="/scripts/login.js"%>
<html>
<%

HaspDogHandler.getInstance(  ).getDogService(  ).login(  );
HaspDogHandler.getInstance(  ).getDogService(  ).logout(  );
request.setAttribute("selectedDataSource",((DynamicDatabaseManager)SpringContextHelper.getBean("selectedDataSource")).getSelectedDataSource());
%>
<head>
<!-- <link rel="stylesheet" type="text/css" href="styles/login.css" /> -->
<script>
if (window != top)
	top.location.href = location.href;
</script>
<style type="text/css">
body {
	font-size: 12px;
}

.td1 {
	width: 600px;
	float: left;
	font-size: 35px;
	color: #0098CC;
	font-weight: bolder;
	font-family: "楷体";
	margin-top: 10px;
}


#top {
	width: 900px;
	margin-top: 70px;
	margin-left: 70px;
}

#logo {
	width: 600px;
	float: left;
	font-size: 35px;
	color: #0098CC;
	font-weight: bolder;
	font-family: "楷体";
	margin-top: 10px;
}

#logo img {
	width: 300px;
}

#top_nav {
	width: 690px;
	margin-left: 305px;
}

#top_nav img {
	float: right;
	width: 100px;
}

#top_nav div {
	height: 29px;
	line-height: 22px;
	width: 55px;
	float: right;
	margin-left: 2px;
}

.clear {
	clear: both;
}

#main_img {
	width: 900px;
	margin: 0 auto;
	margin-top: 10px;
	height: 16px;
	background: url(${ctx}/styles/themes/${themeName}/ihos_images/bg2.png) repeat-x;
}

#main_img img {
	width: 1000px;
}

#main_box {
	width: 900px;
	margin: 0 auto;
	margin-top: 10px;
	background: url(${ctx}/styles/themes/${themeName}/ihos_images/loginbj.jpg) no-repeat;
	height: 600px;
	padding-top: 1px;
}

#left_box {
	width: 250px;
	float: left;
}

#right_box {
	width: 700px;
	margin-left: 250px;
	border-left: 1px dashed #999999;
	margin-left: 265px;
	padding-left: 15px;
}

.title {
	height: 25px;
	line-height: 25px;
	background-color: #F6F1F6;
	font-size: 14px;
	font-weight: bolder;
}

.login_box {
	margin-bottom: 20px;
}

.login_box txt {
	width: 150px;
}

.login_box span {
	margin-left: 20px;
}

.login-item {
	margin-top: 10px;
}

.btns {
	padding-left: 40px;
}

.btns input {
	margin-left: 30px;
}

.login_box ul {
	list-style: none;
	margin: 0px;
	padding: 0px;
	margin-top: 10px;
}

.login_box ul li {
	margin: 0px;
	padding: 0px;
	margin-left: 15px;
	height: 20px;
	line-height: 20px;
}

.cnt_title {
	border-bottom: 1px dashed #999999;
	height: 28px;
}

.cnt_title img {
	float: left;
}

.cnt_title div {
	height: 27px;
	line-height: 27px;
	width: 55px;
	float: left;
	margin-left: 2px;
	font-weight: bolder;
}

.cnt_m {
	float: left;
}

.cnt_date {
	float: right;
	color: #999999;
}

#right_box ul {
	margin: 0px;
	padding: 0px;
	margin-top: 10px;
	padding-left: 10px;
}

#right_box ul li {
	margin-left: 15px;
	height: 20px;
	line-height: 20px;
}

.more {
	text-align: right;
	height: 30px;
	line-height: 30px;
}

#footer {
	width: 900px;
	margin: 0 auto;
	border-top: 3px solid #F78956;
	margin-top: 20px;
	line-height: 25px;
}

#login {
	width: 300px;
	height: 200px;
	margin: 0 auto;
	margin-top: 130px;
	margin-left: 450px;
	padding-top: 20px;
}

#login table {
	line-height: 30px;;
}

#login table th {
	font-size: 20px;
	
}
</style>
<script>
jQuery(document).ready(
		function() {
		    
		    
		    
		    
			var fullSiz = jQuery(document).height();
			var topMargin = (fullSiz-600)/2;
			jQuery("#main_box").css("margin-top",topMargin+"px");
		jQuery("#dengluButton").mouseover(function(){
			//jQuery(this).html("<img src='${ctx}/styles/themes/${themeName}/ihos_images/dengluSelected.png'/>");
			jQuery(this).find("img").attr("src",'${ctx}/styles/themes/${themeName}/ihos_images/dengluSelected.png');
		}).mouseout(function(){
			jQuery(this).find("img").attr("src",'${ctx}/styles/themes/${themeName}/ihos_images/denglu.png');
		});
		jQuery("#quxiaoButton").mouseover(function(){
			jQuery(this).find("img").attr("src",'${ctx}/styles/themes/${themeName}/ihos_images/quxiaoSelected.png');
		}).mouseout(function(){
			jQuery(this).find("img").attr("src",'${ctx}/styles/themes/${themeName}/ihos_images/quxiao.png');
		});
		jQuery("#password").keydown(function(event){
			if(event.keyCode==13) {
				jQuery("#loginForm").submit();
			}
		});
		var dataSourceNum = '<%=((Map)request.getAttribute("selectedDataSource")).size()%>';
		if(dataSourceNum>1){
			jQuery("#dataSource").show();
		}
});
function denglu(){
	jQuery("#loginForm").submit();
}
function quxiao(){
	jQuery("#userName").val("");
	jQuery("#password").val("");
}
</script>
</head>

<body style="background-color: #0177C1">
	<%-- <div id="top">

		<table width="100%">
			<tr>
				<td class="td1">数字化医院综合运营管理系统</td>
				<td class="td2" valign="bottom"><img
					src="${ctx}/styles/themes/${themeName}/ihos_images/logo.jpg" /></td>
			</tr>
		</table>
	</div> --%>
	<!-- <div class="clear"></div>
	<div id="main_img"></div> -->
	<div id="main_box">
		<div id="top">
		<table width="100%">
			<tr>
				<td class="td2" valign="bottom"><img
					src="${ctx}/styles/themes/${themeName}/ihos_images/title.png" /></td>
			</tr>
		</table>
		</div>
		<div id="login">
			<form method="post" id="loginForm"
							action="<c:url value='/j_security_check'/>">
			<table width="100%">
				<!-- <tr>
					<td align="right">数据库：</td>
					<td><input class="txt" style="width: 100px;" name="j_username" /></td>
				</tr> -->
				<tr  id="dataSource" style="height:40px;display:none">
					<td align="right" style="font-size:14px;color:white;font-weight:bold">数据库：</td>
					<td>
						<s:select list="#request.selectedDataSource" name="selectedDataSource" style="width:135px;height:27px"></s:select>
					</td>
				</tr>
				<tr style="height:40px">
					<td align="right" style="font-size: 16px;"><img style="border: 0" src="${ctx}/styles/themes/${themeName}/ihos_images/username.png""/></td>
					<td><input id="userName" class="txt" style="width: 130px;height:20px" name="j_username" /></td>
				</tr>
				<tr style="height:40px">
					<td  align="right" style="font-size: 16px;"><label style="color: white;font-family: '微软雅黑'"><img style="border: 0" src="${ctx}/styles/themes/${themeName}/ihos_images/password.png""/></label></td>
					<td><input id="password" type="password" style="width: 130px;height:20px" class="txt"
						name="j_password" /></td>
				</tr>
				<tr style="height:60px">
					<td colspan="2" align="center">
					
					<a id="dengluButton" href="javaScript:denglu()"><img style="border: 0" src="${ctx}/styles/themes/${themeName}/ihos_images/denglu.png""/></a>
					<a id="quxiaoButton" href="javaScript:quxiao()"><img style="border: 0" src="${ctx}/styles/themes/${themeName}/ihos_images/quxiao.png""/></a>
						</td>
				</tr>
			</table>
			</form>
		</div>
		<div style="width:230px;margin-left: 70px">
			<label  style="color:silver;font-family: '微软雅黑'">北京瑞志龙腾科技有限公司 2012 版权所有</label>
		</div>
	</div>

</body>
</html>