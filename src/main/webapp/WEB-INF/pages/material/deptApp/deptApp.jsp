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
	                       <li><a href="${ctx}/deptAppList" class="j-ajax ajax-once"><span>科室申领</span></a></li>
	                       <li><a href="${ctx}/deptAppHisList?docPreview=state" class="j-ajax ajax-once"><span>申领单据状态查询</span></a></li>
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