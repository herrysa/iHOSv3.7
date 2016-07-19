<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script type="text/javascript">
var showIds = null;
function bmDepartmentGridReload(){
	propertyFilterSearch('bmDepartment_search_form','bmDepartment_gridtable');
	deptTreeReShow();
}
		function deptTreeReShow(){
			var postData = jQuery("#bmDepartment_gridtable").jqGrid('getGridParam',"postData");
			var urlString = 'departmentGridList?1=1';
			var isShowAll=true;
			jQuery.each(postData, function(key, val) {
				if(val){
				switch(key){
					case 'filter_LIKES_departmentId':
						urlString+="&filter_LIKES_departmentId="+val;
						isShowAll=false;
						break;
					case 'filter_LIKES_name':
						urlString+="&filter_LIKES_name="+val;
						isShowAll=false;
						break;
					case 'filter_LIKES_deptCode':
						urlString+="&filter_LIKES_deptCode="+val;
						isShowAll=false;
						break;
					case 'filter_EQS_jjDeptType.khDeptTypeId':
						urlString+="&filter_EQS_jjDeptType.khDeptTypeId="+val;
						isShowAll=false;
						break;
					case 'filter_EQS_deptClass':
						urlString+="&filter_EQS_deptClass="+val;
						isShowAll=false;
						break;
					case 'filter_EQS_subClass':
						urlString+="&filter_EQS_subClass="+val;
						isShowAll=false;
						break;
					case 'filter_EQB_disabled':
						urlString+="&filter_EQB_disabled="+val;
						isShowAll=false;
						break;
					case 'filter_EQS_outin':
						urlString+="&filter_EQS_outin="+val;
						isShowAll=false;
						break;
					case 'filter_EQI_clevel':
						urlString+="&filter_EQI_clevel="+val;
						isShowAll=false;
						break;
					case 'filter_EQB_leaf':
						urlString+="&filter_EQB_leaf="+val;
						isShowAll=false;
						break;
					case 'filter_LIKES_note':
						urlString+="&filter_LIKES_note="+val;
						isShowAll=false;
						break;
				}    
				}
		 　　});   
			var showDisabledDept = jQuery("#bmDepartment_showDisabled").attr("checked");
			if(showDisabledDept){
				urlString += "&showDisabled=true";
			}
			var treeObj = $.fn.zTree.getZTreeObj("bmDepartmentLeftTree");
			 if(!treeObj){
	        	return; 
	         }
			 if(isShowAll){
				 showIds = null;
			 }else{
				 showIds = new Array();
				 jQuery.ajax({
				       url: urlString,
				       data: {},
				       type: 'post',
				       dataType: 'json',
				       async:false,
				       error: function(data){
				       },
				       success: function(data){
				    	   showIds = data.departmentAll;
// 				        if(data.departmentAll){
// 				        	jQuery.each(data.departmentAll, function(i,val) { 
// 					        	showIds[i] = val.departmentId;
// 					         });
// 				        }
				   }
				  });
			 }
			 toggleDisabledOrCount({treeId:'bmDepartmentLeftTree',
		         showCode:jQuery('#bmDepartment_showCode')[0],
		         disabledDept:jQuery("#bmDepartment_showDisabled")[0],
		         disabledPerson: false,
		         showCount:jQuery("#bmDepartment_showPersonCount")[0],
		         showIds:showIds}); 
		}

		jQuery(document).ready(function() {
			var bmDepartmentGridIdString="#bmDepartment_gridtable";
			var bmDepartmentFullSize = jQuery("#container").innerHeight()-118;
			setLeftTreeLayout('bmDepartment_container','bmDepartment_gridtable',bmDepartmentFullSize);
			bmDepartmentLeftTree();
			var initFlag_bmDepartment = 0;
			var bmDepartmentGrid = jQuery(bmDepartmentGridIdString);
			bmDepartmentGrid.jqGrid({
				url : "departmentGridList?1=1",
				editurl : "departmentGridEdit",
				datatype : "json",
				mtype : "GET",
				colModel : [
					{name : 'departmentId',index : 'departmentId',align : 'left',width:100,label : '<s:text name="department.departmentId" />',hidden : false,key : true,highsearch:true},
					{name : 'name',index : 'name',align : 'left',width:120,label : '<s:text name="department.name" />',hidden : false, sortable:true,highsearch:true},
					{name : 'shortnName',index : 'shortnName',align : 'left',width:120,label : '<s:text name="department.shortnName" />',hidden : false, sortable:true,highsearch:true},
					{name : 'deptCode',index : 'deptCode',align : 'left',width:100,label : '<s:text name="department.deptCode" />',hidden : false, sortable:true,highsearch:true},
					{name : 'org.orgname',index : 'org.orgname',align : 'left',width:130,label : '<s:text name="department.orgCode" />',hidden : false,highsearch:true},
					{name : 'branch.branchName',index : 'branch.branchName',align : 'left',width:130,label : '<s:text name="department.branchCode" />',hidden : false,highsearch:true},
					{name : 'deptClass',index : 'deptClass',align : 'center',width:70,label : '<s:text name="department.deptClass" />',hidden : false, sortable:true,highsearch:true},
					{name : 'outin',index : 'outin',align : 'left',width:70,label : '<s:text name="department.outin" />',hidden : false, sortable:true,highsearch:true},
					{name : 'dgroup',index : 'dgroup',align : 'left',width:70,label : '<s:text name="department.dgroup" />',hidden : false, sortable:true,highsearch:true},
					{name : 'clevel',index : 'clevel',align : 'center',width:50,label : '<s:text name="department.clevel" />',hidden : false, sortable:true,highsearch:true},
					{name : 'leaf',index : 'leaf',align : 'center',width:50,label : '<s:text name="department.leaf" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
					{name : 'parentDept.name',index : 'parentDept.name',align : 'left',width:100,label : '<s:text name="department.parentDeptName" />',hidden : false, sortable:true,highsearch:true},
					{name : 'subClass',index : 'subClass',align : 'center',width:100,label : '<s:text name="department.subClass" />',hidden : false, sortable:true,highsearch:true},
					{name : 'cnCode',index : 'cnCode',align : 'left',width:100,label : '<s:text name="department.cnCode" />',hidden : false, sortable:true,highsearch:true},
					{name : 'jjDeptType.khDeptTypeName',index : 'jjDeptType.khDeptTypeName',align : 'center',width:100,label : '<s:text name="department.jjDeptType" />',hidden : false, sortable:true,highsearch:true},
					{name : 'internalCode',index : 'internalCode',align : 'left',width:100,label : '<s:text name="department.internalCode" />',hidden : false, sortable:true,highsearch:true},
					{name : 'manager',index : 'manager',align : 'left',width:100,label : '<s:text name="department.manager" />',hidden : false, sortable:true,highsearch:true},
					{name : 'cbLeaf',index : 'cbLeaf',align : 'center',width:50,label : '<s:text name="department.cbLeaf" />',hidden : true, sortable:true,formatter:'checkbox',highsearch:true},
					{name : 'xmLeaf',index : 'xmLeaf',align : 'center',width:50,label : '<s:text name="department.xmLeaf" />',hidden : true, sortable:true,formatter:'checkbox',highsearch:true},
					{name : 'crLeaf',index : 'crLeaf',align : 'center',width:50,label : '<s:text name="department.crLeaf" />',hidden : true, sortable:true,formatter:'checkbox',highsearch:true},
					{name : 'zcLeaf',index : 'zcLeaf',align : 'center',width:50,label : '<s:text name="department.zcLeaf" />',hidden : true, sortable:true,formatter:'checkbox',highsearch:true},
					{name : 'ysDeptName',index : 'ysDeptName',align : 'left',width:100,label : '<s:text name="department.ysName" />',hidden : true, sortable:true,highsearch:true},
					{name : 'ysLeaf',index : 'ysLeaf',align : 'center',width:50,label : '<s:text name="department.ysLeaf" />',hidden : true, sortable:true,formatter:'checkbox',highsearch:true},
					{name : 'jjDeptName',index : 'jjDeptName',align : 'left',width:100,label : '<s:text name="department.jjName" />',hidden : true, sortable:true,highsearch:true},
					{name : 'jjLeaf',index : 'jjLeaf',align : 'center',width:50,label : '<s:text name="department.jjLeaf" />',hidden : true, sortable:true,formatter:'checkbox',highsearch:true},
					{name : 'yjDeptName',index : 'yjDeptName',align : 'left',width:100,label : '<s:text name="department.yjDeptId" />',hidden : true, sortable:true,highsearch:true},
					{name : 'note',index : 'note',align : 'left',width:120,label : '<s:text name="department.note" />',hidden : false, sortable:true,highsearch:true},
					{name : 'disabled',index : 'disabled',align : 'center',width:50,label : '<s:text name="department.disabled" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
					{name : 'orgCode',index : 'orgCode',align : 'center',width:50,label : '<s:text name="department.orgCode" />',hidden : true},
					{name : 'branchCode',index : 'branchCode',align : 'center',width:50,label : '<s:text name="department.branchCode" />',hidden : true},
					{name : 'phone',index : 'phone',align : 'center',width:50,label : '<s:text name="department.phone" />',hidden : true, sortable:true,highsearch:true}
					],
				jsonReader : {
					root : "departments", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				},
				sortname : 'orgCode,deptCode',
				viewrecords : true,
				sortorder : 'asc',
				height : 300,
				gridview : true,
				rownumbers : true,
				loadui : "disable",
				multiselect : true,
				multiboxonly : true,
				shrinkToFit : false,
				autowidth : false,
				onSelectRow : function(rowid) {

				},
				gridComplete : function() {
			 		/*2015.08.27 form search change*/
			 		gridContainerResize('bmDepartment','layout',0,35);
					 var rowNum = jQuery(this).getDataIDs().length;
			           if(rowNum>0){
						var ztree = $.fn.zTree.getZTreeObj("bmDepartmentLeftTree");
							if(ztree){
		 	                	var selectNode = ztree.getSelectedNodes();
		 						if(selectNode && selectNode.length==1){
		 							var selectid = selectNode[0].id;
		 							jQuery(this).jqGrid('setSelection',selectid);
		 						}
		 	                }
						}else{
							var tw = jQuery(this).outerWidth();
				        	jQuery(this).parent().width(tw);
				        	jQuery(this).parent().height(1);
						}
			           
					var dataTest = {
						"id" : "bmDepartment_gridtable"
					};
					jQuery.publish("onLoadSelect",
							dataTest, null);
					initFlag_bmDepartment = initColumn('bmDepartment_gridtable','com.huge.ihos.system.systemManager.organization.model.bmDepartment',initFlag_bmDepartment);
				}
    		}); 
			jQuery(bmDepartmentGrid).jqGrid('bindKeys');
			
			 /*--------------------------------------tooBar start-------------------------------------------*/
	    	var bmDepartment_menuButtonArrJson = "${menuButtonArrJson}";
	    	var bmDepartment_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(bmDepartment_menuButtonArrJson,false)));
	    	var bmDepartment_toolButtonBar = new ToolButtonBar({el:$('#bmDepartment_buttonBar'),collection:bmDepartment_toolButtonCollection,attributes:{
	    		tableId : 'bmDepartment_gridtable',
	    		baseName : 'bmDepartment',
	    		width : 700,
	    		height : 580,
	    		base_URL : null,
	    		optId : null,
	    		fatherGrid : null,
	    		extraParam : null,
	    		selectNone : "请选择记录。",
	    		selectMore : "只能选择一条记录。",
	    		addTitle : "添加科室",
	    		editTitle : "修改科室"
	    	}}).render();
	    	
	    	var bmDepartment_function = new scriptFunction();
	    	bmDepartment_function.optBeforeCall = function(e,$this,param){
	    		var pass = false;
				if('${yearStarted}' == 'true'){
					alertMsg.error("本年度人力资源系统已启用，请到人力资源系统维护!");
	    			return pass;
				}
		        return true;
			};
			bmDepartment_toolButtonBar.addFunctionAdd('1201010101');
			bmDepartment_toolButtonBar.addBeforeCall('1201010101',function(e,$this,param){
				var bmDepartmentTreeObj = $.fn.zTree.getZTreeObj('bmDepartmentLeftTree');
				var selectedNodes = bmDepartmentTreeObj.getSelectedNodes();
				if(selectedNodes){
					var selectedNode = selectedNodes[0];
					if(selectedNode.subSysTem==='ORG'){
						bmDepartment_toolButtonBar.buttonUtil.userDefineParam = '&orgCode='+selectedNode.id;
					}else if(selectedNode.subSysTem==='DEPT'){
						bmDepartment_toolButtonBar.buttonUtil.userDefineParam = '&pDeptId='+selectedNode.id;
			    	}else if(selectedNode.subSysTem==='ALL') {
			    		alertMsg.info("请选择一个上属单位。");
			    		return false;
			    	}
				}else{
					bmDepartment_toolButtonBar.buttonUtil.userDefineParam = null;
				}
				return bmDepartment_function.optBeforeCall(e,$this,param);
	    	},{});
			//删除
			bmDepartment_toolButtonBar.addCallBody('1201010102',function(e,$this,param) {
				var sid = jQuery(bmDepartmentGridIdString).jqGrid("getGridParam","selarrrow");
				var urlString = "departmentGridEdit?id="+sid+"&navTabId=bmDepartment_gridtable&oper=del";
				alertMsg.confirm("确认删除？",{
					okCall:function() {
						jQuery.post(urlString,function(data) {
							formCallBack(data);
							if(data.statusCode != 200) {
								return;
							}
							for(var i = 0;i < sid.length; i++) {
								dealTreeNodeC("bmDepartmentLeftTree",{id:sid[i]},"del");
							}
						});
					}
				});
			});//addFunctionDel('50010202');
			bmDepartment_toolButtonBar.addBeforeCall('1201010102',function(e,$this,param){
				return bmDepartment_function.optBeforeCall(e,$this,param);
	    	},{});
			
			bmDepartment_toolButtonBar.addFunctionEdit('1201010103');
// 			department_toolButtonBar.addBeforeCall('50010203',function(e,$this,param){
// 				return department_function.optBeforeCall(e,$this,param);
// 	    	},{});
			//检查
 			bmDepartment_toolButtonBar.addCallBody('50010204',function(e,$this,param){
 				jQuery.post("checkDeptRationality",function(data) {
					formCallBack(data);
				});
			},{});
			//查看日志
 			bmDepartment_toolButtonBar.addCallBody('50010205',function(e,$this,param){
				var url = "viewInterLoggerList?taskInterId=deptCheck";
				var winTitle="部门检查日志";
				url = encodeURI(url);
				$.pdialog.open(url,"viewInterLoggerList",winTitle, {ifr:true,mask:true,resizable:true,maxable:true,width : 685,height : 450});
			},{});
			
			
			//设置表格列
			var bmDepartment_setColShowButton = {id:'50010288',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
	   			callBody:function(){
	   				setColShow('bmDepartment_gridtable','com.huge.ihos.system.systemManager.organization.model.bmDepartment');
	   			}};
			bmDepartment_toolButtonBar.addButton(bmDepartment_setColShowButton);
			
		});
		function dealTreeNodeC(treeId,node,opt) {
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			var nodeId = node.id;
			var oldNode = treeObj.getNodeByParam("id", nodeId, null);
			if(node) {
				switch(opt) {
				case 'add' :
					var parentNode = treeObj.getNodeByParam("id", node.pId, null);
					node = treeObj.addNodes(parentNode,node);
					toggleDisabledOrCount({treeId:'bmDepartmentLeftTree',
						showCode:jQuery('#bmDepartment_showCode')[0],
						disabledDept:jQuery("#bmDepartment_showDisabled")[0],
						disabledPerson: false,
						showCount:jQuery("#bmDepartment_showPersonCount")[0] });
					break;
				case 'change' :
					if(oldNode) {
						oldNode.nameWithoutPerson = node.name;
						oldNode.code = node.code;
						treeObj.updateNode(oldNode);
						toggleDisabledOrCount({treeId:'bmDepartmentLeftTree',
							showCode:jQuery('#bmDepartment_showCode')[0],
							disabledDept:jQuery("#bmDepartment_showDisabled")[0],
							disabledPerson: false,
							showCount:jQuery("#bmDepartment_showPersonCount")[0] });
					}
					break;
				case 'del' :
					treeObj.removeNode(oldNode);
					break;
				}
			}
		}
		function bmDepartmentLeftTree(){
			var url = "makeDepartmentTree";
			$.get(url, {"_" : $.now()}, function(data) {
				var departmentTreeData = data.deptTreeNodes;
				var departmentTree = $.fn.zTree.init($("#bmDepartmentLeftTree"),ztreesetting_bmDepartmentTree, departmentTreeData);
				var nodes = departmentTree.getNodes();
				departmentTree.expandNode(nodes[0], true, false, true);
				departmentTree.selectNode(nodes[0]);
				toggleDisabledOrCount({treeId:'bmDepartmentLeftTree',
					showCode:jQuery('#bmDepartment_showCode')[0],
					disabledDept:jQuery("#bmDepartment_showDisabled")[0],
					disabledPerson: false,
					showCount:jQuery("#bmDepartment_showPersonCount")[0] });
			});
			jQuery("#bmDepartment_expandTree").text("展开");
		}
		var ztreesetting_bmDepartmentTree = {
				view : {
					dblClickExpand : false,
					showLine : true,
					selectedMulti : false,
					fontCss : setDisabledDeptFontCss
				},
				callback : {
					beforeDrag:function(){return false},
					onClick : function(event, treeId, treeNode, clickFlag){
						var urlString = "departmentGridList?1=1";
					    var nodeId = treeNode.id;
					    if(nodeId!="-1"){
					    	if(treeNode.subSysTem==='ORG'){
						    	urlString += "&orgCode="+nodeId;
					    	}else if(treeNode.subSysTem==='DEPT'){
						    	urlString += "&departmentId="+nodeId;
					    	}
					    }
					    var showDisabled = jQuery("#bmDepartment_showDisabled").attr("checked");
					    if(showDisabled){
					    	urlString += "&showDisabled=true";
					    }
						urlString=encodeURI(urlString);
						jQuery("#bmDepartment_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
					}
				},
				data : {
					key : {
						name : "name"
					},
					simpleData : {
						enable : true,
						idKey : "id",
						pIdKey : "pId"
					}
				}
		};
		function showDisabledbmDepartment(obj){
			var urlString = jQuery("#bmDepartment_gridtable").jqGrid('getGridParam',"url");
			urlString = urlString.replace('showDisabled','');
			if(obj.checked){
				urlString += "&showDisabled=true";
			}
			jQuery("#bmDepartment_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
			deptTreeReShow();
		}
	</script>
</head>

<div class="page">
			<div id="bmDepartment_pageHeader" class="pageHeader">
				<form  id="bmDepartment_search_form" class="queryarea-form">
					<div class="searchBar">
						<div class="searchContent">
								<%-- <label class="queryarea-label">
									<s:text name="bmDepartment.branchCode"></s:text>
									<s:select list="branches" headerKey="" headerValue="--" listKey="branchCode" listValue="branchName" name="filter_LIKES_branchCode"></s:select>
								</label> --%>
								<label class="queryarea-label"><fmt:message key='department.departmentId' />： <input
									type="text"  size="15" style="width:90px" name="filter_LIKES_departmentId">
								</label>
								<label class="queryarea-label"><fmt:message key='department.name' />： <input
									type="text"  size="15" style="width:90px" name="filter_LIKES_name">
								</label>
								<label class="queryarea-label"><fmt:message key='department.deptCode' />： <input
									type="text" size="15" style="width:90px" name="filter_LIKES_deptCode">
								</label>
								<label class="queryarea-label"><fmt:message key='department.deptClass' />：
										<s:select   
							list="deptClassList" cssClass="required" listKey="deptTypeName"
							listValue="deptTypeName" emptyOption="true" theme="simple" name="filter_EQS_deptClass"></s:select>
								</label>
								<label class="queryarea-label"><fmt:message key='department.subClass' />： 
								<s:select
							list="subClassList" cssClass="required" listKey="value"
							listValue="content" emptyOption="true" theme="simple" name="filter_EQS_subClass"></s:select>
								</label>
								<label class="queryarea-label"><fmt:message key='department.jjDeptType' />：
									<s:select name="filter_EQS_jjDeptType.khDeptTypeId"  list="jjDeptTypeList" cssClass="required"  listKey="khDeptTypeId" listValue="khDeptTypeName"   emptyOption="true" theme="simple"></s:select>
								</label>
								<label class="queryarea-label"><fmt:message key='department.disabled' />： 
									<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_disabled"></s:select>
								</label>
								
								
						<label class="queryarea-label">
							<s:text name='department.outin'/>:
					 		<s:select name="filter_EQS_outin" headerKey="" headerValue="--" 
								list="outinList" listKey="value" listValue="content" 
								emptyOption="false"  maxlength="10" theme="simple">
							</s:select>
						</label>
						<label class="queryarea-label">
							<s:text name='department.clevel'/>:
					 		<input type="text"  style="width:50px" name="filter_EQI_clevel" />
						</label>
						<label class="queryarea-label">
							<s:text name='department.leaf'/>:
					 		<s:select name="filter_EQB_leaf" headerKey="" headerValue="--" 
								list="#{'1':'是','0':'否' }" listKey="key" listValue="value"
								emptyOption="false"  maxlength="10" theme="simple">
							</s:select>
						</label>
						<label class="queryarea-label">
							<s:text name="department.dgroup" />
							<s:select name="filter_EQS_dgroup" headerKey="" headerValue="--" maxlength="20"
								list="dgroupList" listKey="value" listValue="content" emptyOption="false" theme="simple"></s:select>
						</label>
						<label class="queryarea-label">
							<s:text name='department.zcLeaf'/>:
					 		<s:select name="filter_EQB_zcLeaf" headerKey="" headerValue="--" 
								list="#{'1':'是','0':'否' }" listKey="key" listValue="value"
								emptyOption="false"  maxlength="10" theme="simple">
							</s:select>
						</label>
						<label class="queryarea-label">
							<s:text name='department.crLeaf'/>:
					 		<s:select name="filter_EQB_crLeaf" headerKey="" headerValue="--" 
								list="#{'1':'是','0':'否' }" listKey="key" listValue="value"
								emptyOption="false"  maxlength="10" theme="simple">
							</s:select>
						</label>
						<label class="queryarea-label">
							<s:text name='department.note'/>:
					 		<input type="text" style="width:100px" name="filter_LIKES_note" />
						</label>
					
								
								<div class="buttonActive" style="float:right">
										<div class="buttonContent">
											<button type="button" onclick="bmDepartmentGridReload()">
												<fmt:message key='button.search' />
											</button>
										</div>
								</div>
						</div>
					</div>
				</form>
			</div>
			<div class="pageContent">

				<sj:dialog id="mybuttondialog"
					buttons="{'OK':function() { okButton(); }}" autoOpen="false"
					modal="true" title="%{getText('messageDialog.title')}" />

				<s:url id="editurl" action="departmentGridEdit" />
				<s:url id="remoteurl" action="departmentGridList" />
				<div id="bmDepartment_buttonBar" class="panelBar">
					<%-- <ul class="toolBar">
						<li><a id="department_gridtable_add" class="addbutton"
							href="javaScript:"><span><fmt:message
										key="button.add" /> </span> </a></li>
						<li><a id="department_gridtable_del" class="delbutton"
							href="javaScript:"><span>删除</span> </a></li>
						<li><a id="department_gridtable_edit" class="changebutton"
							href="javaScript:"><span><fmt:message
										key="button.edit" /> </span> </a></li>
						<!-- <li><a  class="changebutton"
							href="javaScript:executeSp('checkPKtoFK',null,null,null,'executeSp?proArgsStr=gl_account, , ');"><span>检查数据</span> 
							</a></li> -->
						<li>
							<a  class="delbutton"  href="javaScript:setColShow('department_gridtable','com.huge.ihos.system.systemManager.organization.model.Department')"><span><s:text name="button.setColShow" /></span></a>
						</li>
						<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
					</ul> --%>
				</div>
				
				<div id="bmDepartment_container">
			<div id="bmDepartment_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
					<div class="treeTopCheckBox">
					<span>
						显示机构编码
						<input id="bmDepartment_showCode" checked="checked" type="checkbox" onclick="toggleDisabledOrCount({treeId:'bmDepartmentLeftTree',showCode:this,disabledDept:jQuery('#bmDepartment_showDisabled')[0],disabledPerson: false,showCount:jQuery('#bmDepartment_showPersonCount')[0],showIds:showIds})"/>
					</span>
					<span>
						显示人员数
						<input id="bmDepartment_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'bmDepartmentLeftTree',showCode:jQuery('#bmDepartment_showCode')[0],disabledDept:jQuery('#bmDepartment_showDisabled')[0],disabledPerson: false,showCount:this,showIds:showIds})"/>
					</span>
					<label id="bmDepartment_expandTree" onclick="toggleExpandTree(this,'bmDepartmentLeftTree')">展开</label>
				</div>
				<div class="treeTopCheckBox">
					<span>
						显示停用部门
						<input id="bmDepartment_showDisabled" type="checkbox" onclick="showDisabledbmDepartment(this)"/>
					</span>
				</div>
				<div class="treeTopCheckBox">
					<span>
						按部门检索：
						<input type="text" onKeyUp="searchTree('bmDepartmentLeftTree',this)"/>
					</span>
				</div>
				<div id="bmDepartmentLeftTree" class="ztree"></div>
			</div>
			<div id="bmDepartment_layout-center" class="pane ui-layout-center">

				<div id="bmDepartment_gridtable_div" class="grid-wrapdiv"
					buttonBar="width:700;height:580">
					<input type="hidden" id="bmDepartment_gridtable_navTabId"
						value="${sessionScope.navTabId}"> <label
						style="display: none" id="bmDepartment_gridtable_addTile"> <fmt:message
							key="departmentNew.title" />
					</label> <label style="display: none" id="bmDepartment_gridtable_editTile">
						<fmt:message key="departmentEdit.title" />
					</label> <label style="display: none" id="bmDepartment_gridtable_selectNone">
						<fmt:message key='list.selectNone' />
					</label> <label style="display: none" id="bmDepartment_gridtable_selectMore">
						<fmt:message key='list.selectMore' />
					</label>
					<div id="load_bmDepartment_gridtable"
						class='loading ui-state-default ui-state-active'
						style="display: none"></div>
					<table id="bmDepartment_gridtable"></table>
				</div>
				<div class="panelBar" id="bmDepartment_pageBar">
					<div class="pages">
						<span>显示</span> <select id="bmDepartment_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span>条，共<label id="bmDepartment_gridtable_totals"></label>条
						</span>
					</div>

					<div id="bmDepartment_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1"></div>

				</div>
			</div>
			<!-- center -->
		</div><!-- layout -->
</div>
</div>