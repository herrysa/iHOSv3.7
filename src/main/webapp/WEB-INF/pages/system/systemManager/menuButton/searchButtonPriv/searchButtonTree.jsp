
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
var buttonPrivTreeObj;
var roleId = "${roleId}";
var userId = "${userId}";
var content = "${content}";
var contentUser = "${contentUser}";
var buttonType = "${buttonType}";
	var ztreesetting_buttonPriv = {
			view : {
				showLine : true,
				selectedMulti : false,
				dblClickExpand : false,
				expandSpeed : (jQuery.browser.msie && parseInt(jQuery.browser.version) <= 6) ? ""
						: "fast"
			},
			callback : {
				beforeDrag:zTreeBeforeDrag
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			check : { //为节点添加checkbox
				enable : true,
				chkStyle : "checkbox",
				chkboxType : {
					"Y" : "ps",
					"N" : "ps"
				}
			//checkbox无关联效果
			}
	};
	
	function zTreeBeforeDrag(treeId, treeNodes) {
	    return false;
	};

	function setCheckNode() {
		var nodes = buttonPrivTreeObj.transformToArray(buttonPrivTreeObj.getNodes());
		if (!nodes) return;
		for(var i=0;i<nodes.length;i++){
			if(nodes[i].isParent){
				continue;
			}
			
			for ( var vi = 0; vi < content.split(',').length; vi++) {
				if (content.split(',')[vi] === nodes[i].id) {
					buttonPrivTreeObj.checkNode(nodes[i], true, true);
					 if(userId){
						if (!nodes[i].isParent) {
							buttonPrivTreeObj.setChkDisabled(nodes[i], true);
						} 
					} 
					
				}

			}
			 if(userId){
				for ( var vi = 0; vi < contentUser.split(',').length - 1; vi++) {
					if (contentUser.split(',')[vi] === nodes[i].id) {
						buttonPrivTreeObj.checkNode(nodes[i], true, true);
					}

				}
			} 
			
		}
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
	function getNodeValue() {
		var nodes = buttonPrivTreeObj.getCheckedNodes();
		var s = '';//选中节点ids  
		//遍历选中的节点，为s赋值  
		for ( var i = 0; i < nodes.length; i++) {
			if (s != '')
				s += ',';
			if(userId){
			 	if(!isUserContent(nodes[i].id)){
					s += nodes[i].id;
				} 
			}else{
				s += nodes[i].id;
			}
			 
		}
		//jQuery("#content").val(s);
		return s;
	}

	jQuery(document).ready(function() {
		var treeStatus = 0;
		var getTreeUrl = "${ctx}/makeButtonTree?buttonType="+buttonType;
		$.get(getTreeUrl,{ "_": $.now() },function(data) {
			var buttonPrivTreeData = data.buttonPrivTreeList;
			buttonPrivTreeObj = $.fn.zTree.init($("#${random}_buttonPrivTree"), ztreesetting_buttonPriv,buttonPrivTreeData);
			setCheckNode();
		});
		
		 jQuery("#${random}_saveButtonPriv_buttonPriv").bind("click", function() {
			var s= getNodeValue();
			var saveUrl = "addButtonPriv?popup=true&content="+s+"&buttonType="+buttonType;
			if(userId!="" && userId!=null){
				saveUrl +="&userId="+userId;
			}else{
				saveUrl +="&roleId="+roleId;
			}
		//	alert(saveUrl);
			 $.ajax({
			    url: saveUrl,
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			        
			    },
			    success: function(data){
			        // do something with xml
			        formCallBack(data);
			        if(userId){
			        	var reloadUrl = "buttonPrivUserGridList?userId="+userId+"&buttonType="+buttonType;
			        	jQuery('#${random}_buttonPrivUser_gridtable').jqGrid('setGridParam',{url:reloadUrl,page:1}).trigger("reloadGrid");
			        	reloadUrl = "${ctx}/buttonPrivUserDetailGridList?filter_EQS_buttonPrivUser.privId=ihos";
			        	jQuery("#${random}_buttonPrivUserDetail_gridtable").jqGrid('setGridParam',{url:reloadUrl,page:1}).trigger("reloadGrid");
			        }else{
			        	var reloadUrl = "buttonPrivGridList?roleId=${roleId}&buttonType="+buttonType
			        	jQuery('#${random}_buttonPriv_gridtable').jqGrid('setGridParam',{url:reloadUrl,page:1}).trigger("reloadGrid");
			        	reloadUrl = "${ctx}/buttonPrivDetailGridList?filter_EQS_buttonPriv.privId=ihos";
			        	jQuery("#${random}_buttonPrivDetail_gridtable").jqGrid('setGridParam',{url:reloadUrl,page:1}).trigger("reloadGrid");
			        }
			    }
			});
			$.pdialog.closeCurrent(); 
		});
		jQuery("#${random}_checkAll_buttonPriv").click(function(){
			buttonPrivTreeObj.checkAllNodes(true);
		});
		jQuery("#${random}_reversCheck_buttonPriv").click(function(){
			buttonPrivTreeObj.checkAllNodes(false);
		});
		jQuery("#${random}_expandTree_buttonPriv").click(function(){
			if(treeStatus==1){
				buttonPrivTreeObj.expandAll(false);
				treeStatus = 0;
				jQuery(this).text("展开");
			}else{
				buttonPrivTreeObj.expandAll(true);
				treeStatus = 1;
				jQuery(this).text("收缩");
			}
			
		});
		jQuery("#${random}_refreshButtonPrivTree").click(function(){
			var searchOrMenuName = jQuery("#${random}_searchOrMenuName").val();
			var refreshUrl = "${ctx}/makeButtonTree?buttonType="+buttonType;
			if(searchOrMenuName!=null && ""!=searchOrMenuName.trim()){
				refreshUrl += "&searchOrMenuName="+searchOrMenuName;
			}
			$.get(refreshUrl,{ "_": $.now() },function(data) {
				var buttonPrivTreeData = data.buttonPrivTreeList;
				buttonPrivTreeObj = $.fn.zTree.init($("#${random}_buttonPrivTree"), ztreesetting_buttonPriv,buttonPrivTreeData);
				setCheckNode();
			});
		});
		
	});
</script>

	<div class="page">
		<div class="pageContent">
			<form method="post" action="ajaxDone.html"
				class="pageForm required-validate"
				onsubmit="return validateCallback(this, navTabAjaxDone);">
				<div class="pageFormContent" layoutH="56">
					<div style="margin-left:18px">
						<s:if test="%{buttonType==2}">
							<label>模块名称:</label>
						</s:if>
						<s:elseif test="%{buttonType=1}">
							<label>查询名称:</label>
						</s:elseif>
						<input type="text" id="${random}_searchOrMenuName"/>&nbsp;&nbsp; 
						<div class="buttonActive" style="display:inline;margin-left:10px;margin-top:-2px">
							<span class="buttonActive">
								<button type="button" id="${random}_refreshButtonPrivTree" class="">查询</button>
							</span>
						</div>      
					</div>
					<div style="position: absolute;left:400px;top:14px;z-index:100;">
						<a id="${random}_expandTree_buttonPriv" href="javaScript:">展开</a>
					</div>
					<br/><br/>
					<div style="margin-left: 130px">
						<DIV id="${random}_buttonPrivTree" class="ztree"></DIV>
					</div>
				</div>
				<div class="formBar">
					<ul>
						<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="${random}_checkAll_buttonPriv">全选</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="Button" id="${random}_reversCheck_buttonPriv">清空</button>
								</div>
							</div></li>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button id="${random}_saveButtonPriv_buttonPriv" type="button">保存</button>
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
