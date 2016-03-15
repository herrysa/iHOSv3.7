<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html>
<head>
<%-- <%@ include file="/common/links.jsp"%>
	<link rel="stylesheet" href="${ctx}/scripts/zTree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="${ctx}/scripts/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${ctx}/scripts/zTree/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/zTree/js/jquery.ztree.excheck-3.5.js"></script>
			<script type="text/javascript" src="${ctx}/scripts/zTree/js/jquery.ztree.exedit-3.5.js"></script> --%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<SCRIPT type="text/javascript">

	var zTree, rMenu;
	function setFontCss(treeId, treeNode) {
		var color;

		if (treeNode.level == 0)
			color = {
				color : "red"
			};
		if (treeNode.level == 1)
			color = {
				color : "blue"
			};
		if (treeNode.level == 2)
			color = {
				color : "green"
			};
		if (treeNode.level == 3) {
			if (treeNode.disabled == 1)
				color = {
					color : "black",
					'font-style' : 'italic',
					'text-decoration' : 'line-through'
				};
			else
				color = {
					color : "black"
				};
		}
			return color;
	};
	function onKpiClick(event, treeId, treeNode, clickFlag) {
		//alert(treeNode.tId);
		var url = "editKpiItem?popup=true";
		var parentId =treeNode.id;
			url += "&kpiItemId="+parentId;

		if(parentId==null ){
				alertMsg.error(selectNone);
				return;
			}
		if(treeNode.level==0){
			return;
		}
	url = encodeURI(url);
	var jqxhr = jQuery('#kpiDetail').load(url, function(response, status, xhr) {
		  if (status == "error") {
			  var msg = "Sorry but there was an error: ";
			  promptMsg(status, msg + xhr.status + " " + xhr.statusText)
			
			  }else{//initTree();
			  }
			}); 
	
	}
	
	function addKpiItem(treeId,periodType){
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var nodes = treeObj.getSelectedNodes();
		if(nodes.length>1 || nodes.length==0){
			alert("请选择一个上级节点,且最多只能选择一个上级节点.")
			return ;
			}
		else{
			if(nodes[0].level==3){
				alert("不能添加四级条目,指标库最大级数为3")
				return;
			}
		
		//alert("dsfdf");
		var url = "editKpiItem?popup=true";
		var parentId = nodes[0].id;
			url += "&parentId="+parentId+"&periodType="+periodType;

		if(parentId==null ){
				alertMsg.error(selectNone);
				return;
			}
	
	url = encodeURI(url);
	var jqxhr = jQuery('#kpiDetail').load(url, function(response, status, xhr) {
		  if (status == "error") {
			  var msg = "Sorry but there was an error: ";
			  promptMsg(status, msg + xhr.status + " " + xhr.statusText)
			
			  }
			}); 
		}
	}
	

	
	function onRemove(e, treeId, treeNode) {
		//showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		$.post('deleteKpiItem', {
			kpiItemId : treeNode.id
				}, function(data) {
					
					refreshTreePNode(treeNode.periodType,treeNode.parent,"remove");
					url = encodeURI("empty.jsp");
					var jqxhr = jQuery('#kpiDetail').load(url, function(response, status, xhr) {
						  if (status == "error") {
							  var msg = "Sorry but there was an error: ";
							  promptMsg(status, msg + xhr.status + " " + xhr.statusText)
							
							  }else{//initTree();
							  }
							}); 
					//initTree('',treeNode.periodType);
					alert(data.message);
				});
		return false;
	}
	function beforeRemove(treeId, treeNode) {
		//className = (className === "dark" ? "":"dark");
		//showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
		if(treeNode.level==0){
			alert("根节点不能删除.");
			return false;
		}else
		return confirm("确认删除 节点 -- " + treeNode.keyNameWithCode + " 吗？");
	}
	
	
	
	var newCount = 1;
	function addHoverDom(treeId, treeNode) {
		
		var sObj = $("#" + treeNode.tId + "_span");
		if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0  || treeNode.level==3) return;
		
		var addStr = "<span class='button add' id='addBtn_" + treeNode.id
			+ "' title='add node' ></span>";
		sObj.after(addStr);
		var btn = $("#addBtn_"+treeNode.id);
		if (btn) btn.bind("click", function(){
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			var periodType;
			
			if(treeId=="kpiTreeM") periodType="月";
			if(treeId=="kpiTreeS") periodType="季";
			if(treeId=="kpiTreeH") periodType="半年";
			if(treeId=="kpiTreeY") periodType="年";
			
			
			//zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
			//alert("阿打发斯蒂芬");
			addKpiItem(treeId,periodType);
			return false;
		});
	};
	function removeHoverDom(treeId, treeNode) {
		//alert(treeId);
		$("#addBtn_"+treeNode.id).unbind().remove();
	};
	
	var setting = {
			async: {
				enable: true,
				url: "refreshNode",
				autoParam: ["id"],
				dataFilter: ajaxDataFilter

			}
,
		edit : {
			enable : true,
			showRemoveBtn : true,
			showRenameBtn : false
		},
		view : {
			dblClickExpand : false,
			fontCss : setFontCss
			,addHoverDom: addHoverDom
			,removeHoverDom: removeHoverDom
		},
		check : {
			enable : false
		},
		callback : {
		onClick:onKpiClick,
		beforeRemove: beforeRemove,
		onRemove: onRemove
		},
		data : {
			key : {
				name : "keyNameWithCode"
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parent"
			}
		}
	};

	function initTree(rootId,periodTypeOpen) {
		//alert(rootId);
		//${rootId}
		
		if(rootId==null || rootId=="")
			rootId="-1";
		//alert(rootId);
		$.get("kpiTreeJson?rootId="+rootId, { "_": $.now() },function(data) {
			var treeDataM = data.monKpiTreeList;
			var treeDataS = data.seasonKpiTreeList;
			var treeDataH = data.halfKpiTreeList;
			var treeDataY = data.yearKpiTreeList;
	
			zTreeM = $.fn.zTree.init($("#kpiTreeM"), setting, treeDataM);
			zTreeS = $.fn.zTree.init($("#kpiTreeS"), setting, treeDataS);
			zTreeH = $.fn.zTree.init($("#kpiTreeH"), setting, treeDataH);
			zTreeY = $.fn.zTree.init($("#kpiTreeY"), setting, treeDataY);
			
			
			/* var rootM = zTreeM.getNodeByParam("level", 0);
			rootM.keyNameWithCode="月度指标";
			zTreeM.updateNode(rootM);
			
			var rootS = zTreeS.getNodeByParam("level", 0);
			rootS.keyNameWithCode="季度指标";
			zTreeS.updateNode(rootS);
			
			var rootH = zTreeH.getNodeByParam("level", 0);
			rootH.keyNameWithCode="半年指标";
			zTreeH.updateNode(rootH);
			
			var rootY = zTreeY.getNodeByParam("level", 0);
			rootY.keyNameWithCode="全年指标";
			zTreeY.updateNode(rootY); */
			
			
			if(periodTypeOpen=="月") zTreeM.expandAll(true);
			else if(periodTypeOpen=="季") zTreeS.expandAll(true);
			else if(periodTypeOpen=="半年") zTreeH.expandAll(true);
			else if(periodTypeOpen=="年") zTreeY.expandAll(true);
			else zTreeM.expandAll(true);

		});
	}
	
	function ajaxDataFilter(treeId, parentNode, responseData) {
	    /* if (responseData) {
	      for(var i =0; i < responseData.length; i++) {
	        responseData[i].name += "_filter";
	      }
	    } */
	   // alert(responseData.parentAndChildList);
	 try{
	    return responseData.parentAndChildList;
	}catch(e){
		//alert(e.message);
	}
	}
function refreshTreePNode(periodTypeOpen,pnodeId,type){
	try{
		//alert(periodTypeOpen);
		
		//alert(pnodeId);
		//treeObj.reAsyncChildNodes(pnode, "refresh");
		var treeObj=null;
		if(periodTypeOpen=="月") treeObj=zTreeM;
			else if(periodTypeOpen=="季") treeObj=zTreeS;
			else if(periodTypeOpen=="半年") treeObj=zTreeH;
			else if(periodTypeOpen=="年") treeObj=zTreeY;
			else treeObj=zTreeM;
		
		
		//alert(pnodeId);
		var node = treeObj.getNodeByParam("id", pnodeId, null);
		if(type=="add"&&node.isParent==false){
			node.isParent = true;
		}
		treeObj.refresh();
		treeObj.reAsyncChildNodes(node, "refresh");
		//treeObj.expandNode(node, true, true, true);

		
	}catch(e){
		//alert(e.message);
	}
}
	//var treeData;
//var rMenu;
	$(document).ready(function() {
		//initTree("-1","");
		initTree("${rootId}","");
		
		gridResize(null,null,"kpi_defTable","single");
		var kpiFullSize = jQuery("#container").innerHeight()-28;
		jQuery("#kpi_container").css("height",kpiFullSize);
		$('#kpi_container').layout({ 
			applyDefaultStyles: false ,
			west__size : 300,
			spacing_open:8,//边框的间隙  
			spacing_closed:8,//关闭时边框的间隙 
			resizable :true,
			resizerClass :"ui-layout-resizer-blue",
			slidable :true,
			resizerDragOpacity :1, 
			resizerTip:"可调整大小"//鼠标移到边框时，提示语
			
		});
		//rMenu = $("#rMenu");
		// $( "#kpiTreeToolBar" ).buttonset();
	});
	
	
	function itemTypeButtonClick(buttonId,kpitreeId){
		//alert(buttonId);
		$( "#deptButtonId").removeClass( "selectButton");
		$( "#orgButtonId").removeClass( "selectButton");
		$( "#personButtonId" ).removeClass( "selectButton");
		$( "#managerButtonId" ).removeClass( "selectButton");
		$( "#"+buttonId ).addClass( "selectButton");
		initTree(kpitreeId,'')
	//	alert(buttonId);
	    //  return false;
	}
	

</SCRIPT>
<style type="text/css">

.ztree li span.button {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle;float:none}
.selectButton{color: green;background-color: blue;}
</style>
</HEAD>

<BODY>
	<div class="page">
		<!-- <div class="pageHeader"></div> -->
		<div class="pageContent">

			<div id="kpi_container">
				<div id="kpi_layout-center" class="pane ui-layout-center"
					style="padding: 2px">
					<div id="kpiDetail"></div>
				</div>
				<DIV id="kpi_layout-west" class="pane ui-layout-west"
					style="padding: 2px">
					<div class='accordionHeader'><h2><span style='ddddddddd'></span></h2></div><div id='kpiTreeM' class='accordionContent ztree' style='margin-left:5px'></div>
					<div class='accordionHeader'><h2><span style='ddddddddd'></span></h2></div><div id='kpiTreeS' class='accordionContent ztree' style='margin-left:5px'></div>
					<div class='accordionHeader'><h2><span style='ddddddddd'></span></h2></div><div id='kpiTreeH' class='accordionContent ztree' style='margin-left:5px'></div>
					<div class='accordionHeader'><h2><span style='ddddddddd'></span></h2></div><div id='kpiTreeY' class='accordionContent ztree' style='margin-left:5px'></div>
				</div>
			<!-- <div class="panelBar">
				<ul id="kpiTreeToolBar" class="toolBar">
					<li><a id="deptButtonId"  class="changebutton selectButton"	href="javaScript:itemTypeButtonClick('deptButtonId','-1')"><span>部门指标 </span></a></li>
					<li><a id="orgButtonId"  class="changebutton"	href="javaScript:itemTypeButtonClick('orgButtonId','-2')"><span>医院指标 </span></a></li>
					<li><a id="personButtonId"  class="changebutton"	href="javaScript:itemTypeButtonClick('personButtonId','-3')"><span>员工指标 </span></a></li>
					<li><a id="managerButtonId"  class="changebutton"	href="javaScript:itemTypeButtonClick('managerButtonId','-4')"><span>负责人指标 </span></a></li> 
						
				
				</ul>
				
				
			</div>-->
			<!-- <div id="kpi_defTable_div" tablecontainer="container">
			<table id="kpi_defTable" border="0" width="100%" height="100%">
				<tr>
					<td width="350px" valign="top">
					<div 
				style="float: left; display: block; overflow:auto; width: 350px;" layoutH="45">
			
				<div class='accordionHeader'><h2><span style='ddddddddd'></span></h2></div><div id='kpiTreeM' class='accordionContent ztree' style='margin-left:5px'></div>
				<div class='accordionHeader'><h2><span style='ddddddddd'></span></h2></div><div id='kpiTreeS' class='accordionContent ztree' style='margin-left:5px'></div>
				<div class='accordionHeader'><h2><span style='ddddddddd'></span></h2></div><div id='kpiTreeH' class='accordionContent ztree' style='margin-left:5px'></div>
				<div class='accordionHeader'><h2><span style='ddddddddd'></span></h2></div><div id='kpiTreeY' class='accordionContent ztree' style='margin-left:5px'></div>
			
			
			
			</div>
					</td>
					<td width="1"  bgcolor="#04A0DD">&nbsp;</td>
					<td width="100%">
					
			<div id="jbsxBox3" class="unitBox"  layoutH="45">
			
				</div>
					</td>
				</tr>
			</table>
			
			</div> -->
			
		</div>
	</div>
</BODY>
</HTML>




















