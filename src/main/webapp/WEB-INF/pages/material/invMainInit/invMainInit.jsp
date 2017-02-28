<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	</head>
	<body>
		<div class="page">    
	    <div class="pageContent">
	        <div class="tabs" currentIndex="0" eventType="click">
	     		<div class="tabsHeader">
	                <div class="tabsHeaderContent">
	                    <ul>
	                       <li><a href="${ctx}/invMainInitList" class="j-ajax ajax-once"><span>初始帐</span></a></li>
	                       <li><a href="${ctx}/bookStoreList" class="j-ajax ajax-once"><span>期初记账</span></a></li>
	                    </ul>
	                </div>
	            </div>
	         	<div class="tabsContent" style="padding: 0">
	          		 <div id="tab1"></div>
	           		 <div id="tab2"></div>
	       		 </div>
	        </div>
	    </div>
	</div>
	</body>
</html>