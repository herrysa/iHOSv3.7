
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>




<script type="text/javascript">
	var zTreeObj;
	var content = "${content}";
	var contentUser = "${contentUser}";
	var roleId = "${roleId}";
	var ztreesetting = {

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
		data : {
			simpleData : {
				enable : true
			}
		},
		async : {
			enable : true,
			url : "${ctx}/menuTreeWithCheckAjax?hasUserRole=true&roleId="
					+ roleId,
			autoParam : [ "id", "pId" ],
			//otherParam:{"otherParam":"zTreeAsyncTest"},
			dataFilter : filter
		},
		callback : {
			
		}
		
	};

	function filter(treeId, parentNode, childNodes) {
		if (!childNodes)
			return null;
		for ( var i = 0, l = childNodes.length; i < l; i++) {
			childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			for ( var vi = 0; vi < content.split(',').length - 1; vi++) {
				if (content.split(',')[vi] === childNodes[i].id) {
					zTreeObj.checkNode(childNodes[i], true, true);
					if (!childNodes[i].isParent) {
						zTreeObj.setChkDisabled(childNodes[i], true);
					}
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
			for ( var vi = 0; vi < contentUser.split(',').length - 1; vi++) {
				if (contentUser.split(',')[vi] === childNodes[i].id) {
					zTreeObj.checkNode(childNodes[i], true, true);
				}

			}
		}
		return childNodes;
	}

	function getNodeValue() {
		var nodes = zTreeObj.getCheckedNodes();
		var s = '';//选中节点ids  
		//遍历选中的节点，为s赋值  
		for ( var i = 0; i < nodes.length; i++) {
			/*var isParent = nodes[i].isParent;
			if(isParent!=true){
			if (s != '')s += ',';  
			s += nodes[i].id;  
			}*/
			if (s != '')
				s += ',';
			if(!isUserContent(nodes[i].id)){
				s += nodes[i].id;
			}
			/* if(nodes[i].isisParent==true||nodes[i].chkDisabled==false){
				s += nodes[i].id;
			} */
		}
		jQuery("#content").val(s);
		//alert(jQuery("#content").val());
		return s;
	}
	
	function isUserContent(menuid){
		var roleMenuArr = content.split(",");
		var isUserContent = false;
		for(var rolemenuid in roleMenuArr){
			if(roleMenuArr[rolemenuid]==menuid){
				isUserContent = true;
				break;
			}
		}
		return isUserContent;
	}
	
	var userId = "${userId}";
	var type = "${type}";
	jQuery(document).ready(function() {
		jQuery.fn.zTree.init(jQuery("#MenuTreeForUser"), ztreesetting);
		zTreeObj = jQuery.fn.zTree.getZTreeObj("MenuTreeForUser");

		jQuery("#saveExtraRole").bind("click", function() {
			var s= getNodeValue();
			//jQuery.fancybox.close();
			if(type=="allForTab"){
			$.ajax({
			    url: 'isaveUser?popup=true&id='+userId+"&content="+s+"&editType=2",
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			        
			    },
			    success: function(data){
			        // do something with xml
			        formCallBack(data);
			    	jQuery('#userMenus_gridtable').trigger("reloadGrid");
			    }
			});
			}
			$.pdialog.closeCurrent();
		});
		jQuery("#checkAll").click(function(){
			zTreeObj.checkAllNodes(true);
		});
		jQuery("#reversCheck").click(function(){
			zTreeObj.checkAllNodes(false);
		});
		jQuery("#expandTree").click(function(){
			if(treeStatus==1){
				zTreeObj.expandAll(false);
				treeStatus = 0;
				jQuery(this).text("展开");
			}else{
				zTreeObj.expandAll(true);
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
								<a id="expandTree" href="javaScript:">收缩</a>
							</div>
						<div style="margin-left: 130px">
							<DIV id="MenuTreeForUser" class="ztree"></DIV>
						</div>
				</div>
				<div class="formBar">
					<ul>
						<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="checkAll">全选</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="Button" id="reversCheck">清空</button>
								</div>
							</div></li>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button id="saveExtraRole" type="button">保存</button>
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
