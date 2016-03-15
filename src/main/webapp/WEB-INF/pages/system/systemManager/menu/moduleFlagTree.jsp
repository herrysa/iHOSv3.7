
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>




<script type="text/javascript">
	var zTreeObj_moduleFlag;
	var moduleFlag = "${moduleFlag}";
	var ztreesetting_moduleFlag = {

		check : { //为节点添加checkbox
			enable : true,
			chkStyle : "checkbox",
			chkboxType: { "Y": "", "N": "" }

		//checkbox无关联效果
		},

		view : {
			showLine : true,
			selectedMulti : false,
			dblClickExpand : false,
			expandSpeed : (jQuery.browser.msie && parseInt(jQuery.browser.version) <= 6) ? ""
					: "fast"
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		async : {
			enable : true,
			url : "${ctx}/allMenuTree",
			//otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter : moduleFlagFilter
		},
		callback : {
			onCheck: setChildrenDis
		}
		
	};

	function setChildrenDis(e, treeId, treeNode){
		var zTree = $.fn.zTree.getZTreeObj(treeId),
		nodes = zTree.transformToArray(treeNode.children);
		for(var i=0;i<nodes.length;i++){
			if(treeNode.checked){
				nodes[i].checked = !treeNode.checked;
			}
			
			zTree.setChkDisabled(nodes[i],treeNode.checked);
		}
		
	}
	function moduleFlagFilter(treeId, parentNode, childNodes) {
		if (!childNodes)
			return null;
		/* for(var i = 0, l = childNodes.length; i < l; i++){
			zzhJsTest.debug("----"+childNodes[i].name);
		} */
		
		for ( var i = 0, l = childNodes.length; i < l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			for ( var vi = 0; vi < moduleFlag.split(',').length - 1; vi++) {
				if (moduleFlag.split(',')[vi] === childNodes[i].id) {
					zTreeObj_moduleFlag.checkNode(childNodes[i], true, true);
					
					/* if (!childNodes[i].isParent) {
						zTreeObj.setChkDisabled(childNodes[i], true);
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

	function getModuleFlagValue() {
		var nodes = zTreeObj_moduleFlag.getCheckedNodes();
		var s = '';//选中节点ids  
		//遍历选中的节点，为s赋值  
		for ( var i = 0; i < nodes.length; i++) {
			/*var isParent = nodes[i].isParent;
			if(isParent!=true){
			if (s != '')s += ',';  
			s += nodes[i].id;  
			}*/
			if (s != '')
				s += ','+nodes[i].id;
		}
		return s;
	}
	var treeStatus = true;
	jQuery(document).ready(function() {
		jQuery.fn.zTree.init(jQuery("#moduleFlagTree"), ztreesetting_moduleFlag);
		zTreeObj_moduleFlag = jQuery.fn.zTree.getZTreeObj("moduleFlagTree");
		setTimeout(function(){
			var checkedMoudle = zTreeObj_moduleFlag.getCheckedNodes(true);
			for(var ci=0;ci<checkedMoudle.length;ci++){
				
				var cnodes = zTreeObj_moduleFlag.transformToArray(checkedMoudle[ci].children);
				for(var i=0;i<cnodes.length;i++){
					if(cnodes[i].checked){
						cnodes[i].checked = false;
					}
					zTreeObj_moduleFlag.setChkDisabled(cnodes[i],true);
				}
			}
		},2000);
		
		jQuery("#saveModuleFlag").bind("click", function() {
			var s= getModuleFlagValue();
			//jQuery.fancybox.close();
			//if(type=="allForTab"){
			$.ajax({
			    url: "saveModuleFlag?popup=true&moduleFlag="+s,
			    type: 'post',
			    dataType: 'json',
			    async:false,
			    error: function(data){
			        
			    },
			    success: function(data){
			        // do something with xml
			        formCallBack(data);
			    	//jQuery('#userMenus_gridtable').trigger("reloadGrid");
			    }
			});
			//}
			//$.pdialog.closeCurrent();
		});
		jQuery("#checkModuleFlagAll").click(function(){
			zTreeObj_moduleFlag.checkAllNodes(true);
		});
		jQuery("#reversModuleFlagCheck").click(function(){
			zTreeObj_moduleFlag.checkAllNodes(false);
			//zTreeObj_moduleFlag.get
		});
		
		jQuery("#expandModuleFlagTree").click(function(){
			if(treeStatus){
				//zTreeObj_moduleFlag.expandAll(false);
				//treeStatus = 0;
				jQuery(this).text("展开");
			}else{
				//zTreeObj_moduleFlag.expandAll(true);
				//treeStatus = 1;
				jQuery(this).text("收缩");
			}
			treeStatus = zTreeObj_moduleFlag.expandAll(!treeStatus);
			
			
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
								<a id="expandModuleFlagTree" href="javaScript:">收缩</a>
							</div>
						<div style="margin-left: 130px">
							<DIV id="moduleFlagTree" class="ztree"></DIV>
						</div>
				</div>
				<div class="formBar">
					<ul>
						<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
						<!-- <li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="checkModuleFlagAll">全选</button>
								</div>
							</div>
						</li> -->
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="Button" id="reversModuleFlagCheck">清空</button>
								</div>
							</div></li>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button id="saveModuleFlag" type="button">保存</button>
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
