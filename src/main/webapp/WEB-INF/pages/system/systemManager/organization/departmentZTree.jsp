<%@ include file="/common/taglibs.jsp"%>
<%-- <%@ include file="/common/jstree.jsp"%> --%>
<head>
<title><fmt:message key="departmentDetail.title" /></title>
<meta name="heading"
	content="<fmt:message key='departmentDetail.heading'/>" />
		<link rel="stylesheet" href="/scripts/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="/scripts/zTree/js/jquery.ztree.core-3.1.js"></script>
	<SCRIPT type="text/javascript">
		<!--
		var setting = {
			view: {
				selectedMulti: false
			},
			async: {
				enable: true,
				url:"/displayJsDepartmentTreeJson",
				autoParam:["id", "name=n", "level=lv"],
				otherParam:{"otherParam":"zTreeAsyncTest"},
				dataFilter: filter
			},
			callback: {
				beforeClick: beforeClick,
				beforeAsync: beforeAsync,
				onAsyncError: onAsyncError,
				onAsyncSuccess: onAsyncSuccess
			}
		};

		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}
		function beforeClick(treeId, treeNode) {
			if (!treeNode.isParent) {
				alert("Please select one parent node...");
				return false;
			} else {
				return true;
			}
		}
		var log, className = "dark";
		function beforeAsync(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeAsync ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
			return true;
		}
		function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
			showLog("[ "+getTime()+" onAsyncError ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
		}
		function onAsyncSuccess(event, treeId, treeNode, msg) {
			showLog("[ "+getTime()+" onAsyncSuccess ]&nbsp;&nbsp;&nbsp;&nbsp;" + ((!!treeNode && !!treeNode.name) ? treeNode.name : "root") );
		}

		function showLog(str) {
			if (!log) log = jQuery("#log");
			log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}
		
		function refreshNode(e) {
			var zTree = jQuery.fn.zTree.getZTreeObj("treeDemo"),
			type = e.data.type,
			silent = e.data.silent,
			nodes = zTree.getSelectedNodes();
			if (nodes.length == 0) {
				alert("Please select one parent node at first...");
			}
			for (var i=0, l=nodes.length; i<l; i++) {
				zTree.reAsyncChildNodes(nodes[i], type, silent);
				if (!silent) zTree.selectNode(nodes[i]);
			}
		}

		jQuery(document).ready(function(){
			jQuery.fn.zTree.init(jQuery("#treeDemo"), setting);
			jQuery("#refreshNode").bind("click", {type:"refresh", silent:false}, refreshNode);
			jQuery("#refreshNodeSilent").bind("click", {type:"refresh", silent:true}, refreshNode);
			jQuery("#addNode").bind("click", {type:"add", silent:false}, refreshNode);
			jQuery("#addNodeSilent").bind("click", {type:"add", silent:true}, refreshNode);
		});
		//-->
	</SCRIPT>
</head>

<h1>Standard JSON Data ${ztreejson}</h1>
<h6>[ File Path: core/standardData.html ]</h6>
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="right">
		<ul class="info">
			<li class="title"><h2>1, Explanation of setting</h2>
				<ul class="list">
				<li class="highlight_red">No extrally setting needed for basic functions.</li>
				<li>The setting.view in API documentation is associated with the display of the zTree.</li>
				<li>To change the 'name', 'children', 'title' attribute, please refer to the API documentation about 'setting.data.key'.</li>
				</ul>
			</li>
			<li class="title"><h2>2, Explanation of treeNode</h2>
				<ul class="list">
				<li class="highlight_red">Need to use nested JSON data that include parent-child relationship between nodes
					<div><pre xmlns=""><code>For example:
var nodes = [
	{name: "pNode 01", children: [
		{name: "child 01"},
		{name: "child 02"}
	]}
];</code></pre></div>
				</li>
				<li>To set nodes expanded by default, set treeNode.open attribute.</li>
				<li>No child nodes of parent node, set treeNode.isParent attribute.</li>
				<li>Please refer to the API documentation "treeNode data details" to view other attributes description.</li>
				</ul>
			</li>
		</ul>
	</div>
</div> 
    	 

    	