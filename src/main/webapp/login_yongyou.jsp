<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/links.jsp"%>
 <%@ include file="/scripts/login.js" %>
<html>
	
	<head>
		<link rel="stylesheet" type="text/css" href="styles/login.css" />
	</head>

	<body style="padding: 0px;">
		<div id="top" style="margin-top: 5px;">
			<div id="logo"><img src="${ctx}/styles/themes/${themeName}/ihos_images/logo.jpg"/></div>
			<div id="top_nav">
				<div>添加收藏</div><img src="${ctx}/styles/themes/${themeName}/ihos_images/3.png"/>
				<div>设为首页</div><img src="${ctx}/styles/themes/${themeName}/ihos_images/2.png"/>
				<div>系统说明</div><img src="${ctx}/styles/themes/${themeName}/ihos_images/1.png"/>
			</div>
		</div>
		<div class="clear"></div>
		<div id="main_img">
			<img src="${ctx}/styles/themes/${themeName}/ihos_images/4.png"/>
		</div>
		<div id="main_box">
			<div id="left_box" style="width: 200px;">
				<div class="login_box">
					<form method="post" id="loginForm"
							action="<c:url value='/j_security_check'/>"
							onsubmit="saveUsername(this);return validateForm(this)"
							class="form-horizontal">
						<div class="title">&nbsp;登录窗口</div>
						<div class="login-item">
							<span>用户名：</span><input class="txt" style="width: 100px;" name="j_username"/>
						</div>
						<div class="login-item">
							<span>&nbsp;&nbsp;&nbsp;&nbsp;密码：</span><input type="password" style="width: 100px;" class="txt" name="j_password"/>
						</div>
						<div class="login-item btns">
							<input type="submit" value="登陆"/><input type="button" value="清除"/>
						</div>
					</form>
				</div>

				<div class="login_box">
					<div class="title">&nbsp;网络服务</div>
					<div>
						<ul>
							<li>人员信息查询</li>
							<li>网上调查</li>
						</ul>
					</div>
				</div>

				<div class="login_box">
					<div class="title">&nbsp;内部链接</div>
					<div>
						<ul>
							<li>财务部</li>
							<li>信息中心</li>
						</ul>
					</div>
				</div>
			</div>
			<div id="right_box">
				<div class="cnt_title">
					<img src="${ctx}/styles/themes/${themeName}/ihos_images/5.jpg"/><div>最新公告</div>
				</div>
				<div>
					<ul>
						<li>临床医技科室用户名<span class="cnt_date">改革办 2011-04-12</span></li>
						<li>成本核算科室字典<span class="cnt_date">系统 2010-12-13</span></li>
						<li>科室培训ppt<span class="cnt_date">系统 2010-12-10</span></li>
					</ul>
					<div class="more">更多内容 >></div>
				</div>
				<div class="cnt_title">
					<img src="${ctx}/styles/themes/${themeName}/ihos_images/5.jpg"/><div>规章制度</div>
				</div>
				<div>
					<ul>
						<li>成本核算管理制度<span class="cnt_date">系统 2011-04-12</span></li>
						<li>临床医技科室用户名<span class="cnt_date">改革办 2011-04-12</span></li>
						<li>成本核算科室字典<span class="cnt_date">系统 2010-12-13</span></li>
						<li>科室培训ppt<span class="cnt_date">系统 2010-12-10</span></li>
					</ul>
					<div class="more">更多内容 >></div>
				</div>
				<div class="cnt_title">
					<img src="${ctx}/styles/themes/${themeName}/ihos_images/5.jpg"/><div>问题解答</div>
				</div>
				<div>
					<ul>
						<li>临床医技科室用户名<span class="cnt_date">改革办 2011-04-12</span></li>
						<li>成本核算科室字典<span class="cnt_date">系统 2010-12-13</span></li>
						<li>科室培训ppt<span class="cnt_date">系统 2010-12-10</span></li>
					</ul>
					<div class="more">更多内容 >></div>
				</div>
			</div>
		</div>

		<div id="footer">
			Copyright @ 2009 - 2012
		</div>
	</body>
</html>