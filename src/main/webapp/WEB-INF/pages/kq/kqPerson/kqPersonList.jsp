
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var kqPersonLayout;
	var kqPersonGridIdString="#kqPerson_gridtable";
	var kqTypeIds = [];
	jQuery(document).ready(function() { 
		var personFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('kqPerson_container','kqPerson_gridtable',personFullSize);
    	kqPersonLeftTree();
    	var kqTypeJsonStr = jQuery("#kqPerson_kqTypeJsonStr").val();
		var kqTypes = JSON.parse(kqTypeJsonStr);
		var kqTypeHtml = "<option value=''>--</option>";
		var gridKqTypeSelect = "";
		if(kqTypes){
			jQuery.each(kqTypes,function(index,kqType){
				var kqTypeId = kqType.kqTypeId;
				var kqTypeName = kqType.kqTypeName;
				kqTypeHtml += "<option value='"+kqTypeId+"'>"+kqTypeName+"</option>";
				gridKqTypeSelect += kqTypeId + ":"+ kqTypeName+";";
			});
		}
		if(gridKqTypeSelect){
			gridKqTypeSelect = gridKqTypeSelect.substring(0,gridKqTypeSelect.length-1);
		}
		jQuery("#kqPerson_kqTypeSelect").html(kqTypeHtml);
		var initFlag = 0;
		var gridObject = jQuery(kqPersonGridIdString);
		gridObject.jqGrid({
			url : "kqPersonGridList?1=1",
			editurl : "personGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel : [
				{name : 'personId',index : 'personId',align : 'left',width:100,label : '<s:text name="person.personId" />',hidden : false,key : true,highsearch:true},
				{name : 'name',index : 'name',align : 'left',width:100,label : '<s:text name="person.name" />',hidden : false, sortable:true,highsearch:true},
				{name : 'personCode',index : 'personCode',align : 'center',width:70,label : '<s:text name="person.personCode" />',hidden : false, sortable:true,highsearch:true},
				{name : 'department.name',index : 'department.name',align : 'left',width:70,label : '<s:text name="person.departmentName" />',hidden : false,sortable:true,highsearch:true},
				{name : 'branch.branchName',index : 'branch.branchName',align : 'left',width:70,label : '<fmt:message key="hisOrg.branchName" />',hidden : false, sortable:false,highsearch:true},
				{name : 'sex',index : 'sex',align : 'center',width:50,label : '<s:text name="person.sex" />',hidden : false, sortable:true,highsearch:true},
				{name : 'status',index : 'status',align : 'left',width:100,label : '<s:text name="person.status" />',hidden : false, sortable:true,highsearch:true},
				{name : 'postType',index : 'postType',align : 'center',width:80,label : '<s:text name="person.postType" />',hidden : false, sortable:true,highsearch:true},
				{name : 'jobTitle',index : 'jobTitle',align : 'center',width:50,label : '<s:text name="person.jobTitle" />',hidden : false, sortable:true,highsearch:true},
				{name : 'ratio',index : 'ratio',align : 'center',width:100,label : '<s:text name="person.ratio" />',hidden : false, sortable:true,formatter:'currency',
						formatoptions:{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 4, prefix: '', suffix:'', defaultValue: '0.00'},highsearch:true},
				{name : 'idNumber',index : 'idNumber',align : 'left',width:130,label : '<s:text name="person.idNumber" />',hidden : false, sortable:true,highsearch:true},
				{name : 'disable',index : 'disable',align : 'center',width:50,label : '<s:text name="person.disable" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
				{name : 'kqType',index:'kqType',align:'center',width:'70px',label : '<s:text name="person.kqType" />',hidden:false,formatter: "select", editoptions:{value:gridKqTypeSelect},sortable:true,highsearch:true},
				{name : 'stopKq',index : 'stopKq',align : 'center',width:50,label : '<s:text name="person.stopKq" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
				{name : 'stopKqReason',index : 'stopKqReason',align : 'left',width:200,label : '<s:text name="person.stopKqReason" />',hidden : false, sortable:true,highsearch:true}
// 				{name : 'stopSalary',index : 'stopSalary',align : 'center',width:50,label : '<s:text name="person.stopSalary" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
// 				{name : 'jjmark',index : 'jjmark',align : 'center',width:50,label : '<s:text name="person.jjmark" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
// 				{name : 'bank1',index : 'bank1',align : 'left',width:80,label : '<s:text name="person.bank1" />',hidden : false, sortable:true,highsearch:true},
// 				{name : 'salaryNumber',index : 'salaryNumber',align : 'left',width:80,label : '<s:text name="person.salaryNumber" />',hidden : false, sortable:true,highsearch:true},
// 				{name : 'bank2',index : 'bank2',align : 'left',width:80,label : '<s:text name="person.bank2" />',hidden : false, sortable:true,highsearch:true},
// 				{name : 'salaryNumber2',index : 'salaryNumber2',align : 'left',width:80,label : '<s:text name="person.salaryNumber2" />',hidden : false, sortable:true,highsearch:true},
// 				{name : 'taxType',index : 'taxType',align : 'left',width:80,label : '<s:text name="person.taxType" />',hidden : false, sortable:true,highsearch:true},
// 				{name : 'stopReason',index : 'stopReason',align : 'left',width:200,label : '<s:text name="person.stopReason" />',hidden : false, sortable:true,highsearch:true}
			],
			jsonReader : {
				root : "persons", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
			sortname : 'personCode',
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
				gridContainerResize('kqPerson','layout');
				 var rowNum = jQuery(this).getDataIDs().length;
		           if(rowNum<=0){
						var tw = jQuery(this).outerWidth();
			        	jQuery(this).parent().width(tw);
			        	jQuery(this).parent().height(1);
					}
		         kqPersonCountLoad();
				var dataTest = {"id":"kqPerson_gridtable"};
				jQuery.publish("onLoadSelect",dataTest,null);
				initFlag = initColumn('kqPerson_gridtable','com.huge.ihos.system.systemManager.organization.model.Person',initFlag);
	       	} 
			});
		jQuery(gridObject).jqGrid('bindKeys'); 
		
		/*部门树*/
		var sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department where disabled=0 and deptId <> 'XT'"
		sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ";
		sql += " ORDER BY orderCol ";
		jQuery("#kqPerson_depts").treeselect({
			optType : "multi",
			dataType : 'sql',
			sql : sql,
			exceptnullparent : true,
			lazy : false,
			minWidth : '180px',
			selectParent : false
		});
		/*人员类别树*/
		jQuery("#kqPerson_empTypes").treeselect({
			dataType : "sql",
			optType : "multi",
			sql : "SELECT id,name,parentType parent FROM t_personType where disabled=0  ORDER BY code",
			exceptnullparent : false,
			selectParent : false,
			lazy : false,
			minWidth : '120px'
		});
		//gridResize(null,null,'kqPerson_gridtable','single',null,null);
  	});
	function kqPersonLeftTree() {
		$.get("makeDepartmentPersonTree", {
			"_" : $.now()
		}, function(data) {
			var kqPersonTreeData = data.personTreeNodes;
			var kqPersonTree = $.fn.zTree.init($("#kqPersonTreeLeft"),
					ztreesetting_kqPersonLeft, kqPersonTreeData);
			var nodes = kqPersonTree.getNodes();
			kqPersonTree.expandNode(nodes[0], true, false, true);
			kqPersonTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'kqPersonTreeLeft',
		         showCode:jQuery('#kqPerson_showCode')[0],
		         disabledDept:jQuery("#kqPerson_showDisabled")[0],
		         disabledPerson:jQuery('#kqPerson_showDisabledPerson')[0],
		         showCount:jQuery("#kqPerson_showPersonCount")[0] });
		});
		jQuery("#kqPerson_expandTree").text("展开");
	}
	var ztreesetting_kqPersonLeft = {
			view : {
				dblClickExpand : false,
				showLine : true,
				fontCss : setDisabledDeptFontCss,
				selectedMulti : false
			},
			callback : {
				beforeDrag:zTreeBeforeDrag,
				onClick : onkqPersonModuleClick
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
	function zTreeBeforeDrag(treeId, treeNodes) {
	    return false;
	};
	function onkqPersonModuleClick(event, treeId, treeNode, clickFlag) { 
		var urlString = "kqPersonGridList?1=1";
	    var nodeId = treeNode.id;
	    if(nodeId!="-1"){
	    	if(treeNode.subSysTem==='ORG'){
		    	urlString += "&orgCode="+nodeId;
	    	}else if(treeNode.subSysTem==='DEPT'){
		    	urlString += "&departmentId="+nodeId;
	    	}else{
	    		urlString += "&personId="+nodeId;
	    	}
	    }
	    urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		var showDisabledDept = jQuery("#kqPerson_showDisabled").attr("checked");
		var showDisabledPerson = jQuery("#kqPerson_showDisabledPerson").attr("checked");
		if(showDisabledDept){
			urlString += "&showDisabledDept=true";
		}
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=true";
		}
		urlString = encodeURI(urlString);
		jQuery(kqPersonGridIdString).jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	/*停用checkBox*/
	function showDisabledForKqPerson(){
		toggleDisabledOrCount({treeId:'kqPersonTreeLeft',
	         showCode:jQuery('#kqPerson_showCode')[0],
	         disabledDept:jQuery("#kqPerson_showDisabled")[0],
	         disabledPerson:jQuery('#kqPerson_showDisabledPerson')[0],
	         showCount:jQuery("#kqPerson_showPersonCount")[0] });
		var showDisabledDept = jQuery("#kqPerson_showDisabled").attr("checked");
		var showDisabledPerson = jQuery("#kqPerson_showDisabledPerson").attr("checked");
		var urlString = jQuery(kqPersonGridIdString).jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(showDisabledDept){
			urlString += "&showDisabledDept=true";
		}
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=true";
		}
		urlString = encodeURI(urlString);
		jQuery(kqPersonGridIdString).jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	/*统计数据加载*/
	function kqPersonCountLoad(){
		var gridIdTemp = "kqPerson_gridtable";
		var userData = jQuery("#"+gridIdTemp).getGridParam('userData'); 
   	  	 if(userData){
   			 var deptSalarySum = userData.deptSalarySum;//发放总人数
   			 var deptStopSalarySum = userData.deptStopSalarySum;//停发总人数
   			jQuery("#kqPerson_font_deptSalarySum").text(deptSalarySum);
   			jQuery("#kqPerson_font_deptStopSalarySum").text(deptStopSalarySum);
   	  	 }
	}
	/*查询*/
	function kqPersonGridReload(){
		propertyFilterSearch('kqPerson_search_form','kqPerson_gridtable');
	}
	
	/*----------------------------------tooBar start-----------------------------------------------*/
	var kqPerson_menuButtonArrJson = "${menuButtonArrJson}";
	var kqPerson_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(kqPerson_menuButtonArrJson,false)));
	var kqPerson_toolButtonBar = new ToolButtonBar({el:$('#kqPerson_toolbuttonbar'),collection:kqPerson_toolButtonCollection,attributes:{
		tableId : 'kqPerson_gridtable',
		baseName : 'kqPerson',
		width : 600,
		height : 600,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : '<s:text name="personAdd.title"/>',
		editTitle : null
	}}).render();
	
	var kqPerson_function = new scriptFunction();
	kqPerson_function.optBeforeCall = function(e,$this,param){
		var pass = false;
		if(param.yearStarted){
			if('${yearStarted}' == 'true'){
				alertMsg.error("本年度人力资源系统已启用，请到人力资源系统维护！");
				return pass;
			}
		}
		if(param.selectRecord){
			var sid = jQuery("#kqPerson_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
	        	alertMsg.error("请选择记录！");
				return pass;
			}
	        if(param.singleSelect){
	        	if(sid.length != 1){
		        	alertMsg.error("请选择一条记录！");
					return pass;
				}
	        }
		}
        return true;
	};
	//添加
	kqPerson_toolButtonBar.addFunctionAdd('1006010801');
	/* kqPerson_toolButtonBar.addCallBody('1006010801',function(e,$this,param){
		 var url = "editKqPerson?personId=&navTabId=kqPerson_gridtable";
		 var winTitle = '<s:text name="personAdd.title"/>';
		 $.pdialog.open(url,'addkqPerson',winTitle, {mask:true,width : 650,height : 480,maxable:true,resizable:true});  
	},{}); */
	kqPerson_toolButtonBar.addBeforeCall('1006010801',function(e,$this,param){
		var zTree = $.fn.zTree.getZTreeObj('kqPersonTreeLeft');
		var nodes = zTree.getSelectedNodes(); 
	    if(nodes.length!=1 || nodes[0].subSysTem =='ALL' || nodes[0].subSysTem =='ORG'){
	    	alertMsg.error("请选择一个部门。");
      		return;
	    }
	    if(nodes[0].actionUrl == '1'){
	    	alertMsg.error("已停用部门不能添加人员。");
      		return;
	    }
	    if(nodes[0].subSysTem =='DEPT'&& nodes[0].state == 'PARENT'){
	    	console.log(nodes[0]);
	    	alertMsg.error("父级部门不能添加人员。");
      		return;
	    }
	    if(nodes[0].subSysTem =='PERSON' && nodes[0].getParentNode()){
	    	nodes[0] = nodes[0].getParentNode();
	    }
	    kqPerson_toolButtonBar.buttonUtil.userDefineParam = "&deptId="+nodes[0].id;
		return kqPerson_function.optBeforeCall(e,$this,param);
	},{yearStarted:"yearStarted"});
	//删除
	kqPerson_toolButtonBar.addCallBody('1006010802',function(e,$this,param){
		 var sid = jQuery("#kqPerson_gridtable").jqGrid('getGridParam','selarrrow');
		 var url = "personGridEdit?oper=del&id="+sid+"&navTabId=kqPerson_gridtable";
			alertMsg.confirm("确认删除?", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
	},{});
	kqPerson_toolButtonBar.addBeforeCall('1006010802',function(e,$this,param){
		return kqPerson_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",yearStarted:"yearStarted"});
	//修改
	kqPerson_toolButtonBar.addCallBody('1006010803',function(e,$this,param){
		 var gridIdTemp = "kqPerson_gridtable";
		 var sid = jQuery("#"+gridIdTemp).jqGrid('getGridParam','selarrrow');
		 var winTitle = '<s:text name="personEdit.title"/>';
		  var url = "editKqPerson?personId="+sid+"&navTabId=kqPerson_gridtable";
		  $.pdialog.open(url,'editKqPerson'+sid,winTitle, {mask:true,width : 650,height : 480,maxable:true,resizable:true});  
	},{});
	kqPerson_toolButtonBar.addBeforeCall('1006010803',function(e,$this,param){
		return kqPerson_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",singleSelect:"singleSelect"});
	//批量修改
	kqPerson_toolButtonBar.addCallBody('1006010804',function(e,$this,param){
		var sid = jQuery("#kqPerson_gridtable").jqGrid('getGridParam','selarrrow');
		  var url = "batchEditList?navTabId=kqPerson_gridtable&tableCode=t_person&tableKey=personId";
		  var nisStr = "gzTypeId,bank1,salaryNumber,bank2,salaryNumber2,taxType,stopSalary,stopReason";
		  if('${yearStarted}' == 'true'){//人力资源启用后去除不可修改的字段
			  nisStr += ",sex,dept_id,empType,postType,jobTitle,ratio,educationalLevel";
		  }
		  url += "&filterStr=filter_NIS_fieldCode="+nisStr; 
		  var winTitle = '<s:text name="personBatchEdit.title"/>';
		  $.pdialog.open(url,'batchEditPerson',winTitle, {mask:true,width : 650,height : 480,maxable:true,resizable:true});  
	},{});
	kqPerson_toolButtonBar.addBeforeCall('1006010804',function(e,$this,param){
		return kqPerson_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord"});
	//备份
	kqPerson_toolButtonBar.addCallBody('1006010805',function(e,$this,param){
		var gridIdTemp = "kqPerson_gridtable";
		 var sid = jQuery("#"+gridIdTemp).jqGrid('getGridParam','selarrrow');
		 var winTitle = '更新月度职工表';
		 var url = "updateMonthPersonList?id="+sid+"&navTabId=kqPerson_gridtable&tableCode=t_person";
		 var nisStr = "gzTypeId,bank1,salaryNumber,bank2,salaryNumber2,taxType,stopSalary,stopReason";
		  if('${yearStarted}' == 'true'){//人力资源启用后去除不可修改的字段
			  nisStr += ",sex,dept_id,empType,postType,jobTitle,ratio,educationalLevel";
		  }
		  url += "&filter_NIS_fieldCode="+nisStr;
		  url = encodeURI(url);
		 $.pdialog.open(url,'updateMonthPersonList',winTitle, {mask:true,width : 650,height : 480,maxable:true,resizable:true});
	},{});
	kqPerson_toolButtonBar.addBeforeCall('1006010805',function(e,$this,param){
		return kqPerson_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord"});
	//设置表格列
	var kqPerson_setColShowButton = {id:'1006010806',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
			callBody:function(){
				setColShow('kqPerson_gridtable','com.huge.ihos.system.systemManager.organization.model.Person');
			}};
	kqPerson_toolButtonBar.addButton(kqPerson_setColShowButton);
	
	var kqPerson_setColShowButton = {id:'1006010807',buttonLabel:'关闭查询区',className:"settlebutton",show:true,enable:true,
			callBody:function(){
				toggleFilterArea('kqPerson','layout','button_1006010807');
			}};
	kqPerson_toolButtonBar.addButton(kqPerson_setColShowButton);
	/*----------------------------------tooBar end-----------------------------------------------*/	
</script>

<div class="page" id="kqPerson_page">
	<div class="pageHeader" id="kqPerson_pageHeader">
			<div class="searchBar">	
			<div class="searchContent">
			<form id="kqPerson_search_form" class="queryarea-form">
			<input id="kqPerson_kqTypeJsonStr" type="hidden" value='<s:property value="kqTypeJsonStr" escapeHtml="false"/>'>
					<label class="queryarea-label">
						<fmt:message key="hisOrg.branchName" />
						<s:select list="branches" headerKey="" headerValue="--" listKey="branchCode" listValue="branchName" name="filter_LIKES_branch.branchCode"></s:select>
					</label>
					<label  class="queryarea-label"><fmt:message key='person.personId'/>：
							<input type="text" name="filter_LIKES_personId" style="width: 100px;"/>
					</label>
					<label  class="queryarea-label">
						<fmt:message key='person.name'/>：
							<input type="text" name="filter_LIKES_name" style="width: 100px;"/>
					</label>
					<label  class="queryarea-label">
						<fmt:message key='person.personCode'/>：
							<input type="text" name="filter_LIKES_personCode" style="width: 100px;"/>
					</label>
					<label  class="queryarea-label">
						<fmt:message key="person.departmentName"/>：
						<input type="text" id="kqPerson_depts" style="width: 100px;">
						<input type="hidden" id="kqPerson_depts_id" name="filter_INS_department.departmentId">
					</label>
					<label  class="queryarea-label">
						<s:text name="person.kqType"></s:text>
						<select id="kqPerson_kqTypeSelect" name="filter_EQS_kqType" style="float: none;"></select>
					</label>
					<label  class="queryarea-label">
						<fmt:message key='person.postType'/>：
						<s:select 
							list="postTypeList" cssClass="required" listKey="value"
							listValue="content" emptyOption="true" theme="simple" name="filter_EQS_postType"></s:select>
					  </label>
					<label  class="queryarea-label">
						<fmt:message key='person.status'/>：
						<input id="kqPerson_empTypes" type="text" name="filter_INS_status" style="width: 100px;">
						<input id="kqPerson_empTypes_id" type="hidden">
					  </label>
					<label  class="queryarea-label">
						<fmt:message key='person.disable'/>：
						<s:select list="#{'':'--','true':'是','false':'否'}"  name="filter_EQB_disable"></s:select>
					</label>
<!-- 					<label> -->
<%-- 						<fmt:message key='person.jjmark'/>： --%>
<%-- 					<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_jjmark"></s:select> --%>
<!-- 					</label>&nbsp;&nbsp; -->
					<label  class="queryarea-label">
						<s:text name="person.stopKq"/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_stopKq"></s:select>
					</label>
					<div class="buttonActive" style="float: right">
						<div class="buttonContent">
							<button type="button" onclick="kqPersonGridReload()">
								<s:text name='button.search' />
							</button>
						</div>
					</div>
				</form>
				</div>		
				<%-- <div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="kqPersonGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div> --%>
			</div>
	</div>
	
	<div class="pageContent">
<div class="panelBar" id="kqPerson_buttonBar">
			<ul class="toolBar" id="kqPerson_toolbuttonbar">
<!-- 				<li> -->
<%-- 					<a id="kqPerson_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="kqPerson_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><fmt:message key="button.delete" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="kqPerson_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a id="kqPerson_gridtable_batchEdit_custom" class="getdatabutton"><span>批量修改</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a id="kqPerson_gridtable_backUp_custom" class="backupbutton"><span>备份</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%--      				<a class="settlebutton"  href="javaScript:setColShow('kqPerson_gridtable','com.huge.ihos.system.systemManager.organization.model.Person')"><span><s:text name="button.setColShow" /></span></a> --%>
<!--    				 </li> -->
			</ul>
		</div>
		<div style="margin-top:-20px;margin-right:5px;float:right;">
			<span>考勤总人数:<font id="kqPerson_font_deptSalarySum" style="color:red;">0</font></span>&nbsp;&nbsp;
			<span>停止考勤总人数:<font id="kqPerson_font_deptStopSalarySum" style="color:red;">0</font></span>
		</div>
		<div id="kqPerson_container">
		<div id="kqPerson_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
					<span>
						显示机构编码
						<input id="kqPerson_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'kqPersonTreeLeft',showCode:this,disabledDept:jQuery('#kqPerson_showDisabled')[0],disabledPerson:jQuery('#kqPerson_showDisabledPerson')[0],showCount:jQuery('#kqPerson_showPersonCount')[0]})"/>
					</span>
					<span>
						显示人员数
						<input id="kqPerson_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'kqPersonTreeLeft',showCode:jQuery('#kqPerson_showCode')[0],disabledDept:jQuery('#kqPerson_showDisabled')[0],disabledPerson:jQuery('#kqPerson_showDisabledPerson')[0],showCount:this})"/>
					</span>
					<label id="kqPerson_expandTree" onclick="toggleExpandHrTree(this,'kqPersonTreeLeft')">展开</label>
				</div>
				<div class="treeTopCheckBox">
					<span>
						显示停用部门
						<input id="kqPerson_showDisabled" type="checkbox" onclick="showDisabledForKqPerson()"/>
					</span>
					 <span>
          			 	显示停用人员
          			<input id="kqPerson_showDisabledPerson" type="checkbox" onclick="showDisabledForKqPerson()"/>
          			</span>
				</div>
				<div class="treeTopCheckBox">
					<span>
						快速查询：
						<input type="text" onKeyUp="searchTree('kqPersonTreeLeft',this)"/>
					</span>
				</div>
				<div id="kqPersonTreeLeft" class="ztree"></div>
			</div>
			<div id="kqPerson_layout-center" class="pane ui-layout-center">
		<div id="kqPerson_gridtable_div"  class="grid-wrapdiv" style="">
			<table id="kqPerson_gridtable"></table>
		</div>
		<div id="kqPerson_pageBar" class="panelBar">
			<div class="pages">
				<span><s:text name="pager.perPage" /> </span> <select
					id="kqPerson_gridtable_numPerPage">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span><s:text name="pager.items" />. <s:text
						name="pager.total" /><label id="kqPerson_gridtable_totals"></label>
					<s:text name="pager.items" /> </span>
			</div>
			<div id="kqPerson_gridtable_pagination" class="pagination"
				targetType="navTab" totalCount="200" numPerPage="20"
				pageNumShown="10" currentPage="1"></div>
		</div>
			</div><!-- center -->
		</div><!-- layout -->
	</div>
</div>