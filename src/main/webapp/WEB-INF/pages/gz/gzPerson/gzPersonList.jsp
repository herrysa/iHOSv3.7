
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var gzPersonLayout;
	var gzPersonGridIdString="#gzPerson_gridtable";
	var gzTypeIds = [];
	var gzPersonTabPanel;
	jQuery(document).ready(function() { 
		var personFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('gzPerson_container','gzPerson_gridtable',personFullSize);
    	gzPersonLeftTree();
    	var gzTypeJsonStr = jQuery("#gzPerson_gzTypeJsonStr").val();
		var gzTypes = JSON.parse(gzTypeJsonStr);
		var gzTypeHtml = "<option value=''>--</option>";
		var gridGzTypeSelect = "";
		if(gzTypes){
			jQuery.each(gzTypes,function(index,gzType){
				var gzTypeId = gzType.gzTypeId;
				var gzTypeName = gzType.gzTypeName;
				gzTypeHtml += "<option value='"+gzTypeId+"'>"+gzTypeName+"</option>";
				gridGzTypeSelect += gzTypeId + ":"+ gzTypeName+";";
			});
		}
		if(gridGzTypeSelect){
			gridGzTypeSelect = gridGzTypeSelect.substring(0,gridGzTypeSelect.length-1);
		}
		jQuery("#gzPerson_gzTypeSelect").html(gzTypeHtml);
		jQuery("#gzPerson_gzType2Select").html(gzTypeHtml);
		var initFlag = 0;
		var gridObject = jQuery(gzPersonGridIdString);
		gridObject.jqGrid({
			url : "gzPersonGridList?1=1",
			editurl : "personGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel : [
				{name : 'personId',index : 'personId',align : 'left',width:100,label : '<s:text name="person.personId" />',hidden : false,key : true,highsearch:true},
				{name : 'name',index : 'name',align : 'left',width:100,label : '<s:text name="person.name" />',hidden : false, sortable:true,highsearch:true},
				{name : 'personCode',index : 'personCode',align : 'center',width:70,label : '<s:text name="person.personCode" />',hidden : false, sortable:true,highsearch:true},
				{name : 'department.name',index : 'department.name',align : 'left',width:70,label : '<s:text name="person.departmentName" />',hidden : false,sortable:true,highsearch:true},
				{name : 'branch.branchName',index : 'branch.branchName',align : 'left',width:70,label : '<fmt:message key="hisOrg.branchName" />',hidden : false, sortable:false,highsearch:true},
				{name : 'gzType',index:'gzType',align:'left',width:'70px',label : '<s:text name="person.gzType" />',hidden:false,formatter: "select", editoptions:{value:gridGzTypeSelect},sortable:true,highsearch:true},
				{name : 'gzType2',index:'gzType2',align:'left',width:'70px',label : '工资类别2',hidden:false,formatter: "select", editoptions:{value:gridGzTypeSelect},sortable:true,highsearch:true},
				{name : 'sex',index : 'sex',align : 'center',width:50,label : '<s:text name="person.sex" />',hidden : false, sortable:true,highsearch:true},
				{name : 'status',index : 'status',align : 'left',width:100,label : '<s:text name="person.status" />',hidden : false, sortable:true,highsearch:true},
				{name : 'postType',index : 'postType',align : 'center',width:80,label : '<s:text name="person.postType" />',hidden : false, sortable:true,highsearch:true},
				{name : 'jobTitle',index : 'jobTitle',align : 'center',width:50,label : '<s:text name="person.jobTitle" />',hidden : false, sortable:true,highsearch:true},
				{name : 'ratio',index : 'ratio',align : 'center',width:100,label : '<s:text name="person.ratio" />',hidden : false, sortable:true,formatter:'currency',
						formatoptions:{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 4, prefix: '', suffix:'', defaultValue: '0.00'},highsearch:true},
				{name : 'idNumber',index : 'idNumber',align : 'left',width:130,label : '<s:text name="person.idNumber" />',hidden : false, sortable:true,highsearch:true},
				{name : 'disable',index : 'disable',align : 'center',width:50,label : '<s:text name="person.disable" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
				{name : 'stopSalary',index : 'stopSalary',align : 'center',width:50,label : '<s:text name="person.stopSalary" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
				{name : 'jjmark',index : 'jjmark',align : 'center',width:50,label : '<s:text name="person.jjmark" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
				{name : 'bank1',index : 'bank1',align : 'left',width:80,label : '<s:text name="person.bank1" />',hidden : false, sortable:true,highsearch:true},
				{name : 'salaryNumber',index : 'salaryNumber',align : 'left',width:80,label : '<s:text name="person.salaryNumber" />',hidden : false, sortable:true,highsearch:true},
				{name : 'bank2',index : 'bank2',align : 'left',width:80,label : '<s:text name="person.bank2" />',hidden : false, sortable:true,highsearch:true},
				{name : 'salaryNumber2',index : 'salaryNumber2',align : 'left',width:80,label : '<s:text name="person.salaryNumber2" />',hidden : false, sortable:true,highsearch:true},
				{name : 'taxType',index : 'taxType',align : 'left',width:80,label : '<s:text name="person.taxType" />',hidden : false, sortable:true,highsearch:true},
				{name : 'stopReason',index : 'stopReason',align : 'left',width:200,label : '<s:text name="person.stopReason" />',hidden : false, sortable:true,highsearch:true}
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
				gridContainerResize('gzPerson','layout');
				 var rowNum = jQuery(this).getDataIDs().length;
		           if(rowNum<=0){
						var tw = jQuery(this).outerWidth();
			        	jQuery(this).parent().width(tw);
			        	jQuery(this).parent().height(1);
					}
		         gzPersonCountLoad();
				var dataTest = {"id":"gzPerson_gridtable"};
				jQuery.publish("onLoadSelect",dataTest,null);
				initFlag = initColumn('gzPerson_gridtable','com.huge.ihos.system.systemManager.organization.model.Person',initFlag);
	       	} 
			});
		jQuery(gridObject).jqGrid('bindKeys'); 
		
		/*部门树*/
		var sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department where disabled=0 and deptId <> 'XT'"
		sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ";
		sql += " ORDER BY orderCol ";
		jQuery("#gzPerson_depts").treeselect({
			optType : "multi",
			dataType : 'sql',
			sql : sql,
			exceptnullparent : true,
			lazy : false,
			minWidth : '180px',
			selectParent : false
		});
		/*人员类别树*/
		jQuery("#gzPerson_empTypes").treeselect({
			dataType : "sql",
			optType : "multi",
			sql : "SELECT id,name,parentType parent FROM t_personType where disabled=0  ORDER BY code",
			exceptnullparent : false,
			selectParent : false,
			lazy : false,
			minWidth : '120px'
		});
		//gridResize(null,null,'gzPerson_gridtable','single',null,null);
  	});
	function gzPersonLeftTree() {
		$.get("makeDepartmentPersonTree", {
			"_" : $.now()
		}, function(data) {
			var gzPersonTreeData = data.personTreeNodes;
			var gzPersonTree = $.fn.zTree.init($("#gzPersonTreeLeft"),
					ztreesetting_gzPersonLeft, gzPersonTreeData);
			var nodes = gzPersonTree.getNodes();
			gzPersonTree.expandNode(nodes[0], true, false, true);
			gzPersonTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'gzPersonTreeLeft',
		         showCode:jQuery('#gzPerson_showCode')[0],
		         disabledDept:jQuery("#gzPerson_showDisabled")[0],
		         disabledPerson:jQuery('#gzPerson_showDisabledPerson')[0],
		         showCount:jQuery("#gzPerson_showPersonCount")[0] });
		});
		jQuery("#gzPerson_expandTree").text("展开");
	}
	var ztreesetting_gzPersonLeft = {
			view : {
				dblClickExpand : false,
				showLine : true,
				fontCss : setDisabledDeptFontCss,
				selectedMulti : false
			},
			callback : {
				beforeDrag:zTreeBeforeDrag,
				onClick : onGzPersonModuleClick
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
	function onGzPersonModuleClick(event, treeId, treeNode, clickFlag) { 
		var urlString = "gzPersonGridList?1=1";
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
		var showDisabledDept = jQuery("#gzPerson_showDisabled").attr("checked");
		var showDisabledPerson = jQuery("#gzPerson_showDisabledPerson").attr("checked");
		if(showDisabledDept){
			urlString += "&showDisabledDept=true";
		}
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=true";
		}
		urlString = encodeURI(urlString);
		jQuery(gzPersonGridIdString).jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	/*停用checkBox*/
	function showDisabledForGzPerson(){
		toggleDisabledOrCount({treeId:'gzPersonTreeLeft',
	         showCode:jQuery('#gzPerson_showCode')[0],
	         disabledDept:jQuery("#gzPerson_showDisabled")[0],
	         disabledPerson:jQuery('#gzPerson_showDisabledPerson')[0],
	         showCount:jQuery("#gzPerson_showPersonCount")[0] });
		var showDisabledDept = jQuery("#gzPerson_showDisabled").attr("checked");
		var showDisabledPerson = jQuery("#gzPerson_showDisabledPerson").attr("checked");
		var urlString = jQuery(gzPersonGridIdString).jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(showDisabledDept){
			urlString += "&showDisabledDept=true";
		}
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=true";
		}
		urlString = encodeURI(urlString);
		jQuery(gzPersonGridIdString).jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	/*统计数据加载*/
	function gzPersonCountLoad(){
		var gridIdTemp = "gzPerson_gridtable";
		var userData = jQuery("#"+gridIdTemp).getGridParam('userData'); 
   	  	 if(userData){
   			 var deptSalarySum = userData.deptSalarySum;//发放总人数
   			 var deptStopSalarySum = userData.deptStopSalarySum;//停发总人数
   			jQuery("#gzPerson_font_deptSalarySum").text(deptSalarySum);
   			jQuery("#gzPerson_font_deptStopSalarySum").text(deptStopSalarySum);
   	  	 }
	}
	/*查询*/
	function gzPersonGridReload(){
		propertyFilterSearch('gzPerson_search_form','gzPerson_gridtable');
	}
	
	/*----------------------------------tooBar start-----------------------------------------------*/
	var gzPerson_menuButtonArrJson = "${menuButtonArrJson}";
	var gzPerson_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(gzPerson_menuButtonArrJson,false)));
	var gzPerson_toolButtonBar = new ToolButtonBar({el:$('#gzPerson_toolbuttonbar'),collection:gzPerson_toolButtonCollection,attributes:{
		tableId : 'gzPerson_gridtable',
		baseName : 'gzPerson',
		width : 650,
		height : 480,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : '<s:text name="personAdd.title"/>',
		editTitle : null
	}}).render();
	
	var gzPerson_function = new scriptFunction();
	gzPerson_function.optBeforeCall = function(e,$this,param){
		var pass = false;
		if(param.yearStarted){
			if('${yearStarted}' == 'true'){
				alertMsg.error("本年度人力资源系统已启用，请到人力资源系统维护！");
				return pass;
			}
		}
		if(param.selectRecord){
			var sid = jQuery("#gzPerson_gridtable").jqGrid('getGridParam','selarrrow');
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
	gzPerson_toolButtonBar.addFunctionAdd('11010701');
	/* gzPerson_toolButtonBar.addCallBody('11010701',function(e,$this,param){
			
		 var url = "editGzPerson?personId=&navTabId=gzPerson_gridtable";
		 var userDefineParam = gzPerson_toolButtonBar.buttonUtil.userDefineParam;
		 if(userDefineParam!=null&&userDefineParam!=""&&userDefineParam!="undefined"){
			url += this.userDefineParam;
		 }
		 var winTitle = '<s:text name="personAdd.title"/>';
		 $.pdialog.open(url,'addGzPerson',winTitle, {mask:true,width : 650,height : 480,maxable:true,resizable:true});  
	},{}); */
	gzPerson_toolButtonBar.addBeforeCall('11010701',function(e,$this,param){
		var zTree = $.fn.zTree.getZTreeObj('gzPersonTreeLeft');
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
	    gzPerson_toolButtonBar.buttonUtil.userDefineParam = "&deptId="+nodes[0].id;
		return gzPerson_function.optBeforeCall(e,$this,param);
	},{yearStarted:"yearStarted"});
	//删除
	gzPerson_toolButtonBar.addCallBody('11010702',function(e,$this,param){
		 var sid = jQuery("#gzPerson_gridtable").jqGrid('getGridParam','selarrrow');
		 var url = "personGridEdit?oper=del&id="+sid+"&navTabId=gzPerson_gridtable";
			alertMsg.confirm("确认删除?", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
	},{});
	gzPerson_toolButtonBar.addBeforeCall('11010702',function(e,$this,param){
		return gzPerson_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",yearStarted:"yearStarted"});
	//修改
	gzPerson_toolButtonBar.addCallBody('11010703',function(e,$this,param){
		 var gridIdTemp = "gzPerson_gridtable";
		 var sid = jQuery("#"+gridIdTemp).jqGrid('getGridParam','selarrrow');
		 var winTitle = '<s:text name="personEdit.title"/>';
		  var url = "editGzPerson?personId="+sid+"&navTabId=gzPerson_gridtable";
		  $.pdialog.open(url,'editGzPerson'+sid,winTitle, {mask:true,width : 650,height : 480,maxable:true,resizable:true});  
	},{});
	gzPerson_toolButtonBar.addBeforeCall('11010703',function(e,$this,param){
		return gzPerson_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",singleSelect:"singleSelect"});
	//批量修改
	gzPerson_toolButtonBar.addCallBody('11010704',function(e,$this,param){
		var sid = jQuery("#gzPerson_gridtable").jqGrid('getGridParam','selarrrow');
		  var url = "batchEditList?navTabId=gzPerson_gridtable&tableCode=t_person&tableKey=personId";
		  var nisStr = "kqTypeId,stopKq,stopKqReason";
		  if('${yearStarted}' == 'true'){//人力资源启用后去除不可修改的字段
			  nisStr += ",sex,dept_id,empType,postType,jobTitle,ratio,educationalLevel"; 
		  }
		  url += "&filterStr=filter_NIS_fieldCode="+nisStr;
		  url = encodeURI(url);
		  var winTitle = '<s:text name="personBatchEdit.title"/>';
		  $.pdialog.open(url,'batchEditPerson',winTitle, {mask:true,width : 650,height : 480,maxable:true,resizable:true});  
	},{});
	gzPerson_toolButtonBar.addBeforeCall('11010704',function(e,$this,param){
		return gzPerson_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord"});
	//备份
	gzPerson_toolButtonBar.addCallBody('11010705',function(e,$this,param){
		 var gridIdTemp = "gzPerson_gridtable";
		 var sid = jQuery("#"+gridIdTemp).jqGrid('getGridParam','selarrrow');
		 var winTitle = '更新月度职工表';
		 var url = "updateMonthPersonList?id="+sid+"&navTabId=gzPerson_gridtable&tableCode=t_person";
		 var nisStr = "kqTypeId,stopKq,stopKqReason";
		  if('${yearStarted}' == 'true'){//人力资源启用后去除不可修改的字段
			  nisStr += ",sex,dept_id,empType,postType,jobTitle,ratio,educationalLevel"; 
		  }
		  url += "&filter_NIS_fieldCode="+nisStr;
		  url = encodeURI(url);
		 $.pdialog.open(url,'updateMonthPersonList',winTitle, {mask:true,width : 650,height : 480,maxable:true,resizable:true});
// 		var taskName ="sp_personbakup" ;
// 		var proArgsStr ="${currentPeriod}";
// 		var url = 'backUpPerson?taskName='+taskName+'&proArgsStr='+proArgsStr;
// 		url = encodeURI(url);
// 		alertMsg.confirm("确认备份？", {
// 			okCall: function(){
// 				$.ajax({
// 				    url: url,
// 				    type: 'post',
// 				    dataType: 'json',
// 				    aysnc:false,
// 				    error: function(data){
				        
// 				    },
// 				    success: function(data){
// 				    	formCallBack(data);
// 				    }
// 				});
// 			}
// 		});
	},{});
	gzPerson_toolButtonBar.addBeforeCall('11010705',function(e,$this,param){
		return gzPerson_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord"});
	//设置表格列
	var gzPerson_setColShowButton = {id:'11010706',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
			callBody:function(){
				setColShow('gzPerson_gridtable','com.huge.ihos.system.systemManager.organization.model.Person');
			}};
	gzPerson_toolButtonBar.addButton(gzPerson_setColShowButton);
	
	var gzPerson_setColShowButton = {id:'11010707',buttonLabel:'关闭查询区',className:"settlebutton",show:true,enable:true,
			callBody:function(){
				toggleFilterArea('gzPerson','layout','button_11010707');
			}};
	gzPerson_toolButtonBar.addButton(gzPerson_setColShowButton);
	/*----------------------------------tooBar end-----------------------------------------------*/	
</script>

<div class="page" id="gzPerson_page">
	<div class="pageHeader" id="gzPerson_pageHeader">
			<div class="searchBar">	
			<div class="searchContent">
			<form id="gzPerson_search_form" class="queryarea-form">
			<input id="gzPerson_gzTypeJsonStr" type="hidden" value='<s:property value="gzTypeJsonStr" escapeHtml="false"/>'>
					<label class="queryarea-label">
						<fmt:message key="hisOrg.branchName" />
						<s:select list="branches" headerKey="" headerValue="--" listKey="branchCode" listValue="branchName" name="filter_LIKES_branch.branchCode"></s:select>
					</label>
					<label class="queryarea-label">
						<fmt:message key='person.personId'/>：
						<input type="text" name="filter_LIKES_personId" style="width: 100px;"/>
					</label>
					<label class="queryarea-label">
						<fmt:message key='person.name'/>：
						<input type="text" name="filter_LIKES_name" style="width: 100px;"/>
					</label>
					<label class="queryarea-label">
						<fmt:message key='person.personCode'/>：
						<input type="text" name="filter_LIKES_personCode" style="width: 100px;"/>
					</label>
					<label class="queryarea-label">
						<fmt:message key="person.departmentName"/>：
						<input type="text" id="gzPerson_depts" style="width: 100px;">
						<input type="hidden" id="gzPerson_depts_id" name="filter_INS_department.departmentId">
					</label>
					<label class="queryarea-label">
						<s:text name="person.gzType"></s:text>：
						<select id="gzPerson_gzTypeSelect" name="filter_EQS_gzType" style="float: none;"></select>
					</label>
					<label class="queryarea-label">
						工资类别2：
						<select id="gzPerson_gzType2Select" name="filter_EQS_gzType2" style="float: none;"></select>
					</label>
					<label class="queryarea-label">
						<fmt:message key='person.postType'/>：
						<s:select 
							list="postTypeList" cssClass="required" listKey="value"
							listValue="content" emptyOption="true" theme="simple" name="filter_EQS_postType"></s:select>
					  </label>
					<label class="queryarea-label">
						<fmt:message key='person.status'/>：
						<input id="gzPerson_empTypes" type="text" name="filter_INS_status" style="width: 100px;">
						<input id="gzPerson_empTypes_id" type="hidden">
					  </label>
					<label class="queryarea-label">
						<fmt:message key='person.disable'/>：
						<s:select list="#{'':'--','true':'是','false':'否'}"  name="filter_EQB_disable"></s:select>
					</label>
<!-- 					<label> -->
<%-- 						<fmt:message key='person.jjmark'/>： --%>
<%-- 					<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_jjmark"></s:select> --%>
<!-- 					</label>&nbsp;&nbsp; -->
					<label class="queryarea-label">
						<s:text name="person.stopSalary"/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_stopSalary"></s:select>
					</label>
					<div class="buttonActive" style="float: right">
						<div class="buttonContent">
							<button type="button" onclick="gzPersonGridReload()">
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
									<button type="button" onclick="gzPersonGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div> --%>
			</div>
	</div>
	
	<div class="pageContent">
<div class="panelBar" id="gzPerson_buttonBar">
			<ul class="toolBar" id="gzPerson_toolbuttonbar">
<!-- 				<li> -->
<%-- 					<a id="gzPerson_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="gzPerson_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><fmt:message key="button.delete" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="gzPerson_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a id="gzPerson_gridtable_batchEdit_custom" class="getdatabutton"><span>批量修改</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a id="gzPerson_gridtable_backUp_custom" class="backupbutton"><span>备份</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%--      				<a class="settlebutton"  href="javaScript:setColShow('gzPerson_gridtable','com.huge.ihos.system.systemManager.organization.model.Person')"><span><s:text name="button.setColShow" /></span></a> --%>
<!--    				 </li> -->
			</ul>
		</div>
		<div style="margin-top:-20px;margin-right:5px;float:right;">
			<span>发放总人数:<font id="gzPerson_font_deptSalarySum" style="color:red;">0</font></span>&nbsp;&nbsp;
			<span>停发总人数:<font id="gzPerson_font_deptStopSalarySum" style="color:red;">0</font></span>
		</div>
		<div id="gzPerson_container">
		<div id="gzPerson_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
					<span>
						显示机构编码
						<input id="gzPerson_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'gzPersonTreeLeft',showCode:this,disabledDept:jQuery('#gzPerson_showDisabled')[0],disabledPerson:jQuery('#gzPerson_showDisabledPerson')[0],showCount:jQuery('#gzPerson_showPersonCount')[0]})"/>
					</span>
					<span>
						显示人员数
						<input id="gzPerson_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'gzPersonTreeLeft',showCode:jQuery('#gzPerson_showCode')[0],disabledDept:jQuery('#gzPerson_showDisabled')[0],disabledPerson:jQuery('#gzPerson_showDisabledPerson')[0],showCount:this})"/>
					</span>
					<label id="gzPerson_expandTree" onclick="toggleExpandHrTree(this,'gzPersonTreeLeft')">展开</label>
				</div>
				<div class="treeTopCheckBox">
					<span>
						显示停用部门
						<input id="gzPerson_showDisabled" type="checkbox" onclick="showDisabledForGzPerson()"/>
					</span>
					 <span>
          			 	显示停用人员
          			<input id="gzPerson_showDisabledPerson" type="checkbox" onclick="showDisabledForGzPerson()"/>
          			</span>
				</div>
				<div class="treeTopCheckBox">
					<span>
						快速查询：
						<input type="text" onKeyUp="searchTree('gzPersonTreeLeft',this)"/>
					</span>
				</div>
				<div id="gzPersonTreeLeft" class="ztree"></div>
			</div>
			<div id="gzPerson_layout-center" class="pane ui-layout-center">
		<div id="gzPerson_gridtable_div"  class="grid-wrapdiv" style="">
			<table id="gzPerson_gridtable"></table>
		</div>
		<div id="gzPerson_pageBar" class="panelBar">
			<div class="pages">
				<span><s:text name="pager.perPage" /> </span> <select
					id="gzPerson_gridtable_numPerPage">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span><s:text name="pager.items" />. <s:text
						name="pager.total" /><label id="gzPerson_gridtable_totals"></label>
					<s:text name="pager.items" /> </span>
			</div>
			<div id="gzPerson_gridtable_pagination" class="pagination"
				targetType="navTab" totalCount="200" numPerPage="20"
				pageNumShown="10" currentPage="1"></div>
		</div>
			</div><!-- center -->
		</div><!-- layout -->
	</div>
</div>