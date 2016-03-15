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
		<div style="font-size:18px;font-weight: bold;margin-bottom: 10px;margin-top:20px;text-align: center;">${byLaw.title }</div>
		<hr>
		<div style="color:gray;text-align: center;">添加单位：${byLaw.department}&nbsp;&nbsp; 添加人：${byLaw.creator}&nbsp;&nbsp; 添加日期：
		<s:date name="byLaw.createTime" format="yyyy-MM-dd HH:mm:ss" id="byLaw_createTime"/>
		<s:property value='%{byLaw_createTime}'/> 
		</div> 
		<div id="byLawConten" style="margin-top:20px"><s:property escapeHtml='false' value='byLaw.body'/></div>
		<div style="text-align: left;margin-top:20px">附件下载：<a href="byLaw_dowloadAppendix?byLawId=${byLaw.byLawId }" style="color:blue;">${byLaw.fileName }</a></div>
		</div>
		
	</div>
</div>





