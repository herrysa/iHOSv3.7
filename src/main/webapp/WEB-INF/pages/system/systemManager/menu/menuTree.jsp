<%-- <%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/links.jsp"%> --%>
<!DOCTYPE HTML>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>




<c:set var="ctx" value="${pageContext.request.contextPath}"/>



<%--  <%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>

 <%@ taglib uri="/struts-jquery-tags" prefix="sj" %>
<%@ taglib uri="/struts-jquery-grid-tags" prefix="sjg"%> --%>

<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title><fmt:message key="menuDetail.title" /></title>
<meta name="heading" content="<fmt:message key='menuDetail.heading'/>" />

  <script type="text/javascript"	src="${ctx}/scripts/zTree/js/jquery.ztree.all-3.1.js"></script>
<link rel="stylesheet" type="text/css" media="all"	href="${ctx}/scripts/zTree/css/zTreeStyle/zTreeStyle.css" />
<SCRIPT type="text/javascript">
   
	var ztreesetting = {
			view: {
				showLine: true,
				selectedMulti: false,
				dblClickExpand: false,
				expandSpeed: (jQuery.browser.msie && parseInt(jQuery.browser.version)<=6)?"":"fast"
			},
			data: {
				simpleData: {
					enable: true
				}},
			async: {
				enable: true,
				url:"${ctx}/menuTreeAjax",
				autoParam:["id", "pId"],
				//otherParam:{"otherParam":"zTreeAsyncTest"},
				dataFilter: filter
			},
				callback: {
				//	onNodeCreated: this.onNodeCreated,
					//beforeClick: this.beforeClick,
					onClick: this.onClick
				}
		};

		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}
	/* 	function beforeClick(treeId, node) {
			if (node.isParent) {
				if (node.level === 0) {
					var pNode = curMenu;
					while (pNode && pNode.level !==0) {
						pNode = pNode.getParentNode();
					}
					if (pNode !== node) {
						var a = jQuery("#" + pNode.tId + "_a");
						a.removeClass("cur");
						zTree_Menu.expandNode(pNode, false);
					}
					a = jQuery("#" + node.tId + "_a");
					a.addClass("cur");

					var isOpen = false;
					for (var i=0,l=node.children.length; i<l; i++) {
						if(node.children[i].open) {
							isOpen = true;
							break;
						}
					}
					if (isOpen) {
						zTree_Menu.expandNode(node, true);
						curMenu = node;
					} else {
						zTree_Menu.expandNode(node.children[0].isParent?node.children[0]:node, true);
						curMenu = node.children[0];
					}
				} else {
					zTree_Menu.expandNode(node);
				}
			}
			return !node.isParent;
		} */
		function onClick(e, treeId, node) {
			 var url = node.actionUrl;
				//document.location="${ctx}/menuClicked?targetUrl="+url+"&menuId="+node.id;
				//alert("${ctx}/menuClicked?targetUrl="+url+"&menuId="+node.id);
				mainFrame.location.href = "${ctx}/menuClicked?menuId="+node.id;
		}
		var treeStatus = 1;
		jQuery(document).ready(function(){
			var ztreeObj = jQuery.fn.zTree.init(jQuery("#leftMenuTree"), ztreesetting);
			jQuery("#expand").click(function(){
				if(treeStatus==1){
					ztreeObj.expandAll(false);
					treeStatus = 0;
					jQuery(this).text("展开");
				}else{
					ztreeObj.expandAll(true);
					treeStatus = 1;
					jQuery(this).text("收缩");
				}
				
			});
		});
    </SCRIPT>
</head>
<body>
<div style="z-index:2;position:absolute;left:160px;top:5px;font-size:10px">
<a style="TEXT-DECORATION:none" href="javaScript:" id="expand">收缩</a></div>
<DIV id="leftMenuTree" class="ztree"></DIV>
</body>
</html>