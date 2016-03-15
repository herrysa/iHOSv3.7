
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>




<script type="text/javascript">
	var kpiItemTree;
	var kpiItemContent = "${kpiItemContent}";
	var inspectTemplId = "${inspectTemplId}";
	var treeStatus = 0;
	var periodType = '${requestScope.periodType}';
	var kpiItemTreeSetting = {

		check : { //为节点添加checkbox
			enable : true,
			chkStyle : "checkbox",
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			}
		//checkbox无关联效果
		},

		view : {
			showLine : true,
			selectedMulti : false,
			dblClickExpand : false,
			expandSpeed : (jQuery.browser.msie && parseInt(jQuery.browser.version) <= 6) ? ""
					: "fast"
		},
		data: {
			key:{
				name:"keyName"
			},
			simpleData: {
				enable: true,
				idKey:"id",
				pIdKey:"parent"
			}
		},
		/* async : {
			enable : true,
			url : "${ctx}/kpiTreeJson",
			autoParam : [ "id","keyName", "parent" ],
			//otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter : kpiItemFilter
		}, */
		callback : {
			
		}
		
	};
	
	function kpiItemFilter(treeId, parentNode, childNodes) {
		if (!childNodes)
			return null;
		for ( var i = 0, l = childNodes.length; i < l; i++) {
			childNodes[i].keyName = childNodes[i].keyName.replace(/\.n/g, '.');
			for ( var vi = 0; vi < kpiItemContent.split(',').length - 1; vi++) {
				if (kpiItemContent.split(',')[vi] === childNodes[i].id) {
					getZTreeObj(treeId).checkNode(childNodes[i], true, true);
					/* if (!childNodes[i].isParent) {
						getZTreeObj(treeId).setChkDisabled(childNodes[i], true);
					} */
					/* else{
						alert(childNodes[i].name);
						var checkedNodes = zTreeObj.getNodeByParam("checked",true,null);
						var childNodesByParent = zTreeObj.getNodeByParam("","",childNodes[i]);
						alert(checkedNodes);
						alert(childNodesByParent);
						//alert(childNodesByParent.length);
					} */
				}

			}
		}
		return childNodes;
	}

	function getKpiItemNodeValue() {
		var nodes = kpiItemTree.getCheckedNodes();
		var s = '';//选中节点ids  
		//遍历选中的节点，为s赋值  
		for ( var i = 0; i < nodes.length; i++) {
			var isParent = nodes[i].isParent;
			if(isParent!=true){
			if (s != '')s += ',';  
			s += nodes[i].id;  
			}
			/* if (s != '')
				s += ','; */
			
			/* if(nodes[i].isisParent==true||nodes[i].chkDisabled==false){
				s += nodes[i].id;
			} */
		}
		jQuery("#selectedKpi").val(s);
		//alert(jQuery("#content").val());
		return s;
	}
	
	function initTree() {
		$.get("kpiTreeJson", { "_": $.now() },function(data) {
			var treeDataM = data.monKpiTreeList;
			var treeDataS = data.seasonKpiTreeList;
			var treeDataH = data.halfKpiTreeList;
			var treeDataY = data.yearKpiTreeList;
			
			//zTreeM = $.fn.zTree.init($("#kpiTreeM"), setting, treeDataM);
			//zTreeS = $.fn.zTree.init($("#kpiTreeS"), setting, treeDataS);
			//zTreeH = $.fn.zTree.init($("#kpiTreeH"), setting, treeDataH);
			//zTreeY = $.fn.zTree.init($("#kpiTreeY"), setting, treeDataY);
			var showDate ;
			if(periodType=="月"){
				showDate = treeDataM;
			}else if(periodType=="季"){
				showDate = treeDataS;
			}else if(periodType=="半年"){
				showDate = treeDataH;
			}else if(periodType=="一年"){
				showDate = treeDataY;
			}
			kpiItemTree = $.fn.zTree.init($("#kpiItemTree"), kpiItemTreeSetting, showDate);
			var kpiNodes = kpiItemTree.getNodes();
			for(var ki=0;ki<kpiNodes.length;ki++){
				checkedKpiNode(kpiItemTree,kpiNodes[ki]);
			}
			//zTree.expandAll(true);
		});
	}
	function checkedKpiNode(kpiItemTree,node){
		if(node.isParent){
			var childrenNode = node.children;
			if(childrenNode){
				for(var ki=0;ki<childrenNode.length;ki++){
					checkedKpiNode(kpiItemTree,childrenNode[ki]);
				}
			}
		}else{
			for ( var vi = 0; vi < kpiItemContent.split(',').length - 1; vi++) {
				if (kpiItemContent.split(',')[vi] == node.id) {
					kpiItemTree.checkNode(node, true, true);
				}
			}
		}
	}
	jQuery(document).ready(function() {
		//kpiItemTree = jQuery.fn.zTree.init(jQuery("#kpiItemTree"), kpiItemTreeSetting);
		kpiItemTree = initTree();

		jQuery("#saveKpiItems").bind("click", function() {
			var s= getKpiItemNodeValue();
			//jQuery.fancybox.close();
			/* if(type=="allForTab"){
			$.ajax({
			    url: 'isaveUser?popup=true&id='+userId+"&content="+s+"&editType=2",
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			        
			    },
			    success: function(data){
			        // do something with xml
			    	//jQuery('#userMenus_gridtable').trigger("reloadGrid");
			    }
			});
			} */
			reloadInspectBSC('init');
			$.pdialog.closeCurrent();
		});
		jQuery("#checkKpiItemAll").click(function(){
			kpiItemTree.checkAllNodes(true);
		});
		jQuery("#reversKpiItemCheck").click(function(){
			kpiItemTree.checkAllNodes(false);
		});
		jQuery("#expandKpiItemTree").click(function(){
			if(treeStatus==1){
				kpiItemTree.expandAll(false);
				treeStatus = 0;
				jQuery(this).text("展开");
			}else{
				kpiItemTree.expandAll(true);
				treeStatus = 1;
				jQuery(this).text("收缩");
			}
			
		});
		
	});
</script>

	<div class="page">
		<div class="pageContent">
			<form method="post" action="ajaxDone.html"
				class="pageForm required-validate"
				onsubmit="return validateCallback(this, navTabAjaxDone);">
				<div class="pageFormContent" layoutH="56">
				<div style="position: absolute;left:330px;top:15px;z-index:100;">
								<a id="expandKpiItemTree" href="javaScript:">展开</a>
							</div>
					<div id="treediv" class="container">
						<div style="margin-left: 130px">
							<DIV id="kpiItemTree" class="ztree"></DIV>
						</div>
						<br />
					</div>
				</div>
				<div class="formBar">
					<ul>
						<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="checkKpiItemAll">全选</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="Button" id="reversKpiItemCheck">清空</button>
								</div>
							</div></li>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button id="saveKpiItems" type="button">确定</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="Button" onclick="$.pdialog.closeCurrent();">取消</button>
								</div>
							</div></li>
					</ul>
				</div>
			</form>
		</div>
	</div>
