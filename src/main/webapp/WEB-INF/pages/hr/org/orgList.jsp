
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var hrOrgGridIdString="#hrOrg_gridtable";
	jQuery(document).ready(function() { 
		var hrOrgFullSize = jQuery("#container").innerHeight()-116;
		jQuery("#hrOrg_container").css("height",hrOrgFullSize);
		$('#hrOrg_container').layout({ 
			applyDefaultStyles: false ,
			west__size : 250,
			spacing_open:5,//边框的间隙  
			spacing_closed:5,//关闭时边框的间隙 
			resizable :true,
			resizerClass :"ui-layout-resizer-blue",
			slidable :true,
			resizerDragOpacity :1, 
			resizerTip:"可调整大小",//鼠标移到边框时，提示语
			onresize_end : function(paneName,element,state,options,layoutName){
				//zzhJsTest.debug("resize:"+paneName);
				if("center" == paneName){
					gridResize(null,null,"hrOrg_gridtable","single");
				}
			}
			
		});
		loadOrgTree();
		var initFlag = 0;
		var orgGrid = jQuery(hrOrgGridIdString);
	    orgGrid.jqGrid({
	    	url : "orgGridList",
	    	editurl:"orgGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'orgCode',index:'orgCode',align:'left',width:90,label : '<s:text name="org.orgCode" />',hidden:false,key:true},				
				{name:'orgname',index:'orgname',align:'left',width:160,label : '<s:text name="org.orgname" />',hidden:false,highsearch:true},				
				{name:'shortName',index:'shortName',align:'left',width:120,label : '<s:text name="org.shortName" />',hidden:false,highsearch:true},				
				{name:'internal',index:'internal',align:'left',width:70,label : '<s:text name="org.internal" />',hidden:false,highsearch:true},				
				{name:'address',index:'address',align:'left',width:140,label : '<s:text name="org.address" />',hidden:true,highsearch:true},				
				{name:'homePage',index:'homePage',align:'left',width:90,label : '<s:text name="org.homePage" />',hidden:true,highsearch:true},				
				{name:'email',index:'email',align:'left',width:120,label : '<s:text name="org.email" />',hidden:false,highsearch:true},				
				{name:'phone',index:'phone',align:'left',width:100,label : '<s:text name="org.phone" />',hidden:false,highsearch:true},				
				{name:'fax',index:'fax',align:'left',width:100,label : '<s:text name="org.fax" />',hidden:false,highsearch:true},				
				{name:'contact',index:'contact',align:'left',width:90,label : '<s:text name="org.contact" />',hidden:false,highsearch:true},				
				{name:'contactPhone',index:'contactPhone',align:'left',width:90,label : '<s:text name="org.contactPhone" />',hidden:false,highsearch:true},				
				{name:'disabled',index:'disabled',align:'center',width:50,label : '<s:text name="org.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},				
				{name:'invalidDate',index:'invalidDate',align:'center',width:90,label : '<s:text name="org.invalidDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'parentOrgCode.orgname',index:'ownerOrg',align:'left',width:90,label : '<s:text name="org.ownerOrg" />',hidden:true},				
				// {name:'type',index:'type',align:'left',width:90,label : '<s:text name="org.type" />',hidden:false,highsearch:true},		
				{name:'note',index:'note',align:'left',width:150,label : '<s:text name="org.note" />',hidden:false,highsearch:true}	
	        ],
	        jsonReader : {
				root : "orgs", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'orgCode',
	        viewrecords: true,
	        sortorder: 'asc',
	        //caption:'<s:text name="orgList.title" />',
	        height:300,
	        gridview:true,
	        rownumbers:true,
	        loadui: "disable",
	        multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
	        onSelectRow: function(rowid) {
	       
	       	},
			gridComplete:function(){
				 /*2015.08.27 form search change*/
				 gridContainerResize('hrOrg','layout');
	           	var dataTest = {"id":"hrOrg_gridtable"};
	      	   	jQuery.publish("onLoadSelect",dataTest,null);
	      	 	initFlag = initColumn('hrOrg_gridtable','com.huge.ihos.hr.hrOrg.model.HrOrg',initFlag);
	       	} 
	    });
    	jQuery(orgGrid).jqGrid('bindKeys');
    	 /*--------------------------------------tooBar start-------------------------------------------*/
    	var hrOrg_menuButtonArrJson = "${menuButtonArrJson}";
    	var hrOrg_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(hrOrg_menuButtonArrJson,false)));
    	var hrOrg_toolButtonBar = new ToolButtonBar({el:$('#hrOrg_buttonBar'),collection:hrOrg_toolButtonCollection,attributes:{
    		tableId : 'hrOrg_gridtable',
    		baseName : 'hrOrg',
    		width : 600,
    		height : 600,
    		base_URL : null,
    		optId : null,
    		fatherGrid : null,
    		extraParam : null,
    		selectNone : "请选择记录。",
    		selectMore : "只能选择一条记录。",
    		addTitle : null,
    		editTitle : null
    	}}).render();
    	
    	var hrOrg_function = new scriptFunction();
    	hrOrg_function.optBeforeCall = function(e,$this,param){
    		var pass = false;
    		if(param.checkPeriod){
				if('${yearStarted}' != 'true'){
					alertMsg.error("本年度人力资源系统未启用!");
	    			return pass;
				}
			}
    		if(param.selectRecord){
    			var sid = jQuery("#hrOrg_gridtable").jqGrid('getGridParam','selarrrow');
    	        if(sid==null || sid.length ==0){
    	        	alertMsg.error("请选择记录.");
    				return pass;
    			}
    	        if(param.singleSelect){
    	        	if(sid.length != 1){
    		        	alertMsg.error("只能选择一条记录.");
    					return pass;
    				}
    	        }
    		}
	        return true;
		};
    	//添加
		hrOrg_toolButtonBar.addCallBody('10010101',function(e,$this,param){
			var zTree = $.fn.zTree.getZTreeObj("hrOrgTree");  
			var nodes = zTree.getSelectedNodes(); 
			var selectId = "";
			if(nodes.length!=0){
				selectId = nodes[0].id;
				if(selectId=="-1"){
					selectId = "";
				}
		    }
			var url = "editHrOrg?popup=true&orgCode="+selectId+"&navTabId=hrOrg_gridtable&editType=new";
			var winTitle="<s:text name='orgNew.title'/>";
			url = encodeURI(url);
			$.pdialog.open(url, 'addOrg', winTitle, {mask:false,width:580,height:400,resizable:false,maxable:false});
		},{});
		hrOrg_toolButtonBar.addBeforeCall('10010101',function(e,$this,param){
			return hrOrg_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
		//删除
		hrOrg_toolButtonBar.addCallBody('10010102',function(e,$this,param){
			var sid = jQuery("#hrOrg_gridtable").jqGrid('getGridParam','selarrrow');
			var editUrl = "orgGridEdit?oper=del&navTabId=hrOrg_gridtable"
			for(var i=0;i<sid.length;i++){
				rowData = jQuery("#hrOrg_gridtable").jqGrid('getRowData',sid[i]);
				if(rowData["disabled"]=='No'){
					alertMsg.error("只能删除已停用的单位！");
					return;
				}
			} 
			editUrl += "&id="+sid;
	        alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, refreshOrgTreeBack, "json");
				}
			});
		},{});
		hrOrg_toolButtonBar.addBeforeCall('10010102',function(e,$this,param){
			return hrOrg_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
		// 修改
		hrOrg_toolButtonBar.addCallBody('10010103',function(e,$this,param){
			var sid = jQuery("#hrOrg_gridtable").jqGrid('getGridParam','selarrrow');
			var url = "editHrOrg?popup=true&orgCode="+sid+"&navTabId=hrOrg_gridtable";
			var winTitle="<s:text name='orgEdit.title'/>";
			url = encodeURI(url);
			$.pdialog.open(url, 'editOrg', winTitle, {mask:false,width:580,height:400,resizable:false,maxable:false});
		},{});
		hrOrg_toolButtonBar.addBeforeCall('10010103',function(e,$this,param){
			return hrOrg_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",singleSelect:"单选",checkPeriod:"checkPeriod"});
		// 启用
		hrOrg_toolButtonBar.addCallBody('10010104',function(e,$this,param){
			var sid = jQuery("#hrOrg_gridtable").jqGrid('getGridParam','selarrrow');
			var editUrl = "orgGridEdit?oper=enable&navTabId=hrOrg_gridtable"
			for(var i=0;i<sid.length;i++){
				rowData = jQuery("#hrOrg_gridtable").jqGrid('getRowData',sid[i]);
				if(rowData["disabled"]=='No'){
					alertMsg.error("你选择的单位已启用！");
					return;
				}
			} 
			editUrl += "&id="+sid;
			alertMsg.confirm("确认启用？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, formCallBack, "json");
				}
			});
		},{});
		hrOrg_toolButtonBar.addBeforeCall('10010104',function(e,$this,param){
			return hrOrg_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
		// 停用
		hrOrg_toolButtonBar.addCallBody('10010105',function(e,$this,param){
			var sid = jQuery("#hrOrg_gridtable").jqGrid('getGridParam','selarrrow');
			var editUrl = "orgGridEdit?oper=disable&navTabId=hrOrg_gridtable"
			for(var i=0;i<sid.length;i++){
				rowData = jQuery("#hrOrg_gridtable").jqGrid('getRowData',sid[i]);
				if(rowData["disabled"]=='Yes'){
					alertMsg.error("你选择的单位已停用！");
					return;
				}
			} 
			editUrl += "&id="+sid;
			alertMsg.confirm("停用后，此单位下部门和人员将无法显示，是否停用？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, formCallBack, "json");
				}
			});
		},{});
		hrOrg_toolButtonBar.addBeforeCall('10010105',function(e,$this,param){
			return hrOrg_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
		//设置表格列
		var hrOrg_setColShowButton = {id:'10010106',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
   			callBody:function(){
   				setColShow('hrOrg_gridtable','com.huge.ihos.hr.hrOrg.model.HrOrg');
   			}};
		hrOrg_toolButtonBar.addButton(hrOrg_setColShowButton);
  	});
	
	var ztreesetting_HrOrg = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false
		},
		callback : {
			beforeDrag:function(){return false},
			onClick : function(event, treeId, treeNode, clickFlag){
				var treeId = treeNode.id;
				var urlString = "orgGridList";
				if(treeId!='-1' && treeId !==""){
					urlString += "?orgCode="+treeId;
				}
				urlString=encodeURI(urlString);
				jQuery("#hrOrg_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
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
	function refreshOrgTreeBack(data){
		loadOrgTree();
		formCallBack(data);
	}
	function loadOrgTree(){
		var treeUrl = "makeOrgTree";
		$.get(treeUrl, {"_" : $.now()}, function(data) {
			var orgTreeData = data.ztreeList;
			var orgTree = $.fn.zTree.init($("#hrOrgTree"),ztreesetting_HrOrg, orgTreeData);
			var nodes = orgTree.getNodes();
			orgTree.expandNode(nodes[0], true, false, true);
			orgTree.selectNode(nodes[0]);
		});
		jQuery("#hrOrg_expandTree").text("展开");
	}
	
	/*function addOrg(){
		var zTree = $.fn.zTree.getZTreeObj("hrOrgTree");  
		var nodes = zTree.getSelectedNodes(); 
		var selectId = "";
		if(nodes.length!=0){
			selectId = nodes[0].id;
			if(selectId=="-1"){
				selectId = "";
			}
	    }
		var url = "editHrOrg?popup=true&orgCode="+selectId+"&navTabId=hrOrg_gridtable&editType=new";
		var winTitle="<s:text name='orgNew.title'/>";
		url = encodeURI(url);
		$.pdialog.open(url, 'addOrg', winTitle, {mask:false,width:580,height:450,resizable:false,maxable:false});
	}
	function editOrg(){
		var sid = jQuery("#hrOrg_gridtable").jqGrid('getGridParam','selarrrow');
		if(sid==null || sid.length !=1){
        	alertMsg.error("请选择一条记录.");
			return;
		}
		var url = "editHrOrg?popup=true&orgCode="+sid+"&navTabId=hrOrg_gridtable";
		var winTitle="<s:text name='orgEdit.title'/>";
		url = encodeURI(url);
		$.pdialog.open(url, 'editOrg', winTitle, {mask:false,width:580,height:450,resizable:false,maxable:false});
	}
	function delOrg(){
		var sid = jQuery("#hrOrg_gridtable").jqGrid('getGridParam','selarrrow');
		var editUrl = "orgGridEdit?oper=del&navTabId=hrOrg_gridtable"
        if(sid==null || sid.length ==0){
        	alertMsg.error("请选择记录.");
			return;
		}else{
			for(var i=0;i<sid.length;i++){
				rowData = jQuery("#hrOrg_gridtable").jqGrid('getRowData',sid[i]);
				if(rowData["disabled"]=='No'){
					alertMsg.error("只能删除已停用的单位！");
					return;
				}
			} 
			editUrl += "&id="+sid;
	        alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, refreshOrgTreeBack, "json");
				}
			});
		}
	}
	
	function enableOrDisableHrOrg(type){
		var sid = jQuery("#hrOrg_gridtable").jqGrid('getGridParam','selarrrow');
		var editUrl = "orgGridEdit?oper="+type+"&navTabId=hrOrg_gridtable"
        if(sid==null || sid.length ==0){
        	alertMsg.error("请选择记录.");
			return;
		}else{
			if(type=="enable"){
				for(var i=0;i<sid.length;i++){
					rowData = jQuery("#hrOrg_gridtable").jqGrid('getRowData',sid[i]);
					if(rowData["disabled"]=='No'){
						alertMsg.error("你选择的单位已启用！");
						return;
					}
				} 
			}else{
				for(var i=0;i<sid.length;i++){
					rowData = jQuery("#hrOrg_gridtable").jqGrid('getRowData',sid[i]);
					if(rowData["disabled"]=='Yes'){
						alertMsg.error("你选择的单位已停用！");
						return;
					}
				} 
			}
			editUrl += "&id="+sid;
			alertMsg.confirm("确认"+(type=='enable'?'启用':'停用')+"？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, formCallBack, "json");
				}
			});
		}
	}*/
	
</script>

<div class="page">
	<div class="pageHeader" id="hrOrg_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="hrOrg_search_form" style="white-space: break-all;word-wrap:break-word;" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='org.orgCode'/>:
      					<input type="text" name="filter_LIKES_orgCode" id="hrOrg_orgCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='org.orgname'/>:
      					<input type="text" name="filter_LIKES_orgname"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='org.type'/>:
      					<input type="text" name="filter_LIKES_type"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='org.disabled'/>:
      					<s:select list="#{'':'--','1':'是','0':'否'}" name="filter_EQB_disabled" style="width:60px"></s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('hrOrg_search_form','hrOrg_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>	
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('hrOrg_search_form','hrOrg_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div id="hrOrg_buttonBar" class="panelBar">
			<!-- <ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:addOrg()" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a class="delbutton" onclick="delOrg()" ><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a class="changebutton"  href="javaScript:editOrg()"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a class="enablebutton" href="javaScript:enableOrDisableHrOrg('enable')"><span><s:text name="button.enable" /></span> </a></li>
				<li>
					<a class="disablebutton" href="javaScript:enableOrDisableHrOrg('disable')"><span><s:text name="button.disable" /></span> </a></li>
				<li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('hrOrg_gridtable','com.huge.ihos.hr.hrOrg.model.HrOrg')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			
			</ul> -->
		</div>
		<div id="hrOrg_container">
			<div id="hrOrg_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div style="margin-left:20px;margin-bottom:2px;float:right">
					<span id="hrOrg_expandTree" style="vertical-align:middle;text-decoration:underline;cursor:pointer;margin-left:10px" onclick="toggleExpandTree(this,'hrOrgTree')">展开</span>
				</div>
				<DIV id="hrOrgTree" class="ztree"></DIV>
			</div>
			<div id="hrOrg_layout-center" class="pane ui-layout-center">
				<div id="hrOrg_gridtable_div" class="grid-wrapdiv" style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="hrOrg_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_hrOrg_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
				 	<table id="hrOrg_gridtable"></table>
				</div>
				<div class="panelBar" id="hrOrg_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="hrOrg_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="hrOrg_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="hrOrg_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>