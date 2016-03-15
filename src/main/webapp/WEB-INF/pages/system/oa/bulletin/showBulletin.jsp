<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
	});
	
</script>
</head>

<div class="page">
	<div class="pageContent" layoutH=1>
		<div style="margin-left:5%;margin-right:5%;">
		<div style="font-size:18px;font-weight: bold;margin-bottom: 10px;margin-top:20px;text-align: center;">${bulletin.subject }</div>
		<hr>
		<div style="color:gray;text-align: center;">发布单位：${bulletin.department} &nbsp;&nbsp; 发布人：${bulletin.creator} &nbsp;&nbsp;发布日期：
		<s:date name="bulletin.createTime" format="yyyy-MM-dd HH:mm:ss" id="bulletin_createTime"/>
		<s:property value='%{bulletin_createTime}'/> &nbsp;&nbsp;点击次数：${bulletin.openNum}</div>
		<div id="bulletinConten" style="margin-top:20px"><s:property escapeHtml='false' value='bulletin.content'/></div>
		<div style="text-align: left;margin-top:20px">附件下载：<a href="dowloadAppendix?bulletinId=${bulletin.bulletinId }" style="color:blue;">${bulletin.fileName }</a></div>
		</div>
		
	</div>
</div>





