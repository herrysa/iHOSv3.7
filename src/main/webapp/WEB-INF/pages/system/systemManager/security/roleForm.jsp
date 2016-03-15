<%@ page language="java" pageEncoding="UTF-8"%> 
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
var zTreeObj;
var content ="${content}";
var roleId ="${roleId}";
var ztreesetting = {
		
		check: { //为节点添加checkbox
			enable: true,
			chkStyle: "checkbox",
			chkboxType: { "Y": "ps", "N": "ps" } //checkbox无关联效果
			},

		
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
			url:"${ctx}/menuTreeWithCheckAjax?roleId="+roleId,
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
			for(var vi=0;vi<content.split(',').length-1;vi++){
				if(content.split(',')[vi]===childNodes[i].id){
					zTreeObj.checkNode(childNodes[i], true, true);
				}
			
			}
		}
		return childNodes;
	}
	
	function changeAttr(){  
		jQuery('#end').unbind('focus')  
	        jQuery('#end').bind('focus',function(){WdatePicker({skin:'whyGreen',dateFmt:'yyyy年MM月',minDate:'#F{$dp.$D(\'start\')}'});});  
	    }  

	///jQuery(function() {
		/* jQuery("#departmentIdC").autocomplete(
				"autocomplete",
				{
					width : 300,
					multiple : false,
					autoFill : false,
					matchContains : true,
					matchCase : true,
					dataType : 'json',
					parse : function(test) {
						//alert(test.dicList.length)
						var data = test.autocompleteList;
						if (data == null || data == "") {
							var rows = [];
							rows[0] = {
								data : "没有结果",
								value : "",
								result : ""
							};
							return rows;
						} else {
							var rows = [];
							for ( var i = 0; i < data.length; i++) {
								rows[rows.length] = {
									data : data[i].departmentId + ","
											+ data[i].deptCode + ","
											+ data[i].cnCode + ","
											+ data[i].name + ":"
											+ data[i].departmentId,
									value : data[i].name,
									result : data[i].name
								};
							}
							return rows;
						}
					},
					extraParams : {
						flag : 2,
						entity : "Department",
						cloumnsStr : "departmentId,deptCode,name,cnCode",
						extra1 : " leaf=true and (",
						extra2 : ")"
					},
					formatItem : function(row) {
						return dropId(row);
					},
					formatResult : function(row) {
						return dropId(row);
					}
				});
		jQuery("#departmentIdC").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#persondepartmentdepartmentId").attr("value", getId(row));
			//alert(jQuery("#zxDeptId").attr("value"));
		}); */
	//})

	function getId(s) {
		var s = "" + s;
		var p = s.lastIndexOf(":");
		if (p > -1) {
			var t = s.substring(p + 1);
			return t;
		}
		return s;
	}
	function dropId(s) {
		//alert(s)
		var s = "" + s;
		var p = s.lastIndexOf(":");
		if (p > -1) {
			var t = s.substring(0, p);
			var t = s.substring(s.indexOf(",") + 1, p);
			return t;
		}
		return s;
	}
	
	function getNodeValue (){
		var nodes = zTreeObj.getCheckedNodes();
		var s = '';//选中节点ids  
		//遍历选中的节点，为s赋值  
		for(var i=0; i<nodes.length; i++){  
			/*var isParent = nodes[i].isParent;
		 	if(isParent!=true){
		 	if (s != '')s += ',';  
		 	s += nodes[i].id;  
			}*/
		 	if (s != '')s += ',';  
		 	s += nodes[i].id;  
		}  
		jQuery("#content").val(s);
	}
	var treeStatus = 1;
	jQuery(document).ready(function(){
		jQuery.fn.zTree.init(jQuery("#MenuTreeForRole"), ztreesetting);
		zTreeObj = jQuery.fn.zTree.getZTreeObj("MenuTreeForRole");

		jQuery("#${random}savelink").bind("click",function(){
			getNodeValue();
			jQuery("#roleForm").attr("action","saveRole?popup=true&navTabId="+'${navTabId}');
			jQuery("#roleForm").submit();
		});
		if("${editType}"=="2"){
			jQuery("#roleBaseInfo").hide();
		}else{
			jQuery("#roleTree").hide();
			jQuery("#checkAllLi").hide();
			jQuery("#reversCheckLi").hide();
		}
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
</head>
<div class="page">
		<div class="pageContent">
			<form id="roleForm" action="saveRole?popup=true" method="post"
				class="pageForm required-validate"
				onsubmit="return validateCallback(this,formCallBack);">
				
					<div class="pageFormContent" layoutH="56">
						<div id="roleBaseInfo">
						<s:hidden name="editType" value="%{editType}" label="editType" />
								<div style="display:none">
								<s:textfield key="role.id" 
											maxlength="30"  />
								</div>
							<div class="unit">
							
							<s:if test="role.roleType==1">
								<s:textfield key="role.name" cssClass="required" readonly="true"
										maxlength="20" />
										
							</s:if>
							<s:else>
								<s:textfield key="role.name" cssClass="required"
										maxlength="20" />
							</s:else>
							</div>
							<div class="unit">
							<s:textfield key="role.chName" cssClass="required"
										maxlength="20" /></div>
							<div class="unit"><s:textfield key="role.description" size="50"
										 /></div>
							</div>
							<div class="unit" id="roleTree">
							<div style="position: absolute;left:330px;top:15px;z-index:100;">
								<a id="expandTree" href="javaScript:">收缩</a>
							</div>
							<label>模块权限:</label>
									<table><tr><td><DIV id="MenuTreeForRole" class="ztree"></DIV></td></tr></table>
							</div>
								<input type="hidden" id="content" name="content"/>
					</div>

					<div class="formBar">
					<ul>
						<!--<li><a class="buttonActive" href="javascript:void(0)"><span>保存</span></a></li>-->
						<li id="checkAllLi"><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="checkAll">全选</button>
								</div>
							</div>
						</li>
						<li id="reversCheckLi">
							<div class="button">
								<div class="buttonContent">
									<button type="Button" id="reversCheck">清空</button>
								</div>
							</div></li>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="${random}savelink">保存</button>
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

