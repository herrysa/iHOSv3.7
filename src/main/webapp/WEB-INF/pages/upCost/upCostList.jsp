
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<%-- <script type="text/javascript" src="${ctx}/scripts/jqgrid/js/jquery.jqGrid.src.js"></script> --%>
<script type="text/javascript" src="${ctx}/struts2/js/plugins/grid.grouping.js"></script>
<script type="text/javascript">
	function upCostGridReload(random){
		var url = "upCostGridList?upItemId="+jQuery("#"+random+"_upItemId").val();
		var costOrg = jQuery("#"+random+"upCost_costOrg_id").val();
		var branchCode = jQuery("#" + random + "upCost_branch_id").val();
		var deptName = jQuery("#"+random+"upCost_dept").val();
		var amountMin = jQuery("#"+random+"upCost_amount_min").val();
		var amountMax = jQuery("#"+random+"upCost_amount_max").val();
		url += "&filter_INS_costOrg.orgCode="+costOrg+"&filter_INS_branch.branchCode="+branchCode+"&filter_LIKES_deptId.name="+deptName+"&filter_GEG_amount="+amountMin+"&filter_LEG_amount="+amountMax;
		url = encodeURI(url);
		jQuery("#"+random+"_upCost_gridtable").jqGrid('setGridParam', {
			url : url,
			page : 1
		}).trigger("reloadGrid"); 
		/*  var url = "upCostGridList?upItemId="+jQuery("#${random}_upItemId").val();
		jQuery("#${random}_upCost_search_form").attr("action",url);
		propertyFilterSearch('${random}_upCost_search_form','${random}_upCost_gridtable'); */
	}
	var upCostLayout;
			  var upCostGridIdString="#${random}_upCost_gridtable";
	
	jQuery(document).ready(function() { 
		/* upCostLayout = makeLayout({
			baseName: 'upCost', 
			tableIds: '${random}_upCost_gridtable'
		}, null); */
var upCostGrid = jQuery(upCostGridIdString);
    upCostGrid.jqGrid({
    	url : "upCostGridList?upItemId="+jQuery("#${random}_upItemId").val(),
    	editurl:"upCostGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'upcostId',index:'upcostId',align:'center',label : '<s:text name="upCost.upcostId" />',hidden:true,key:true,formatter:'integer',summaryType:'count'},				
{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="upCost.checkperiod" />',width:100,hidden:false},				
{name:'upitemName',index:'upitemName',align:'left',label : '<s:text name="upCost.upitemName" />',hidden:false},				
{name:'costItemName',index:'costItemName',align:'left',label : '<s:text name="upCost.costItemName" />',hidden:false},				
{name:'deptInternalCode',index:'deptInternalCode',align:'left',label : '<s:text name="upCost.deptInternalCode" />',hidden:true},				


<c:if test="${upItemType==1}">
{name:'deptId.name',index:'deptId.name',align:'left',label : '<s:text name="上报科室" />',hidden:false,width:250},	
{name:'personCode',index:'personCode',align:'left',label : '<s:text name="upCost.personCode" />',hidden:true},	
{name:'personName',index:'personName',align:'left',label : '<s:text name="upCost.personName" />',hidden:false},	
{name:'owerdeptName',index:'owerdeptName',align:'left',label : '<s:text name="upCost.owerdeptName" />',hidden:false},	
</c:if>
<c:if test="${upItemType!=1}">
{name:'deptName',index:'deptName',align:'left',label : '<s:text name="upCost.deptName" />',hidden:false,width:250},	
</c:if>
{name:'amount',index:'amount',align:'right',label : '<s:text name="upCost.amount" />',hidden:false,formatter:'number'
	,formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},edittype:"text",editrules:{number:true},editable:true,
	editoptions:{dataEvents:[{type:'blur',fn: function(e) { }}]},summaryType:'sum'},		

/* {name:'auditorDate',index:'auditorDate',align:'center',label : '<s:text name="upCost.auditorDate" />',hidden:false,formatter:'date'},				
{name:'auditorName',index:'auditorName',align:'center',label : '<s:text name="upCost.auditorName" />',hidden:false},				
{name:'operateDate',index:'operateDate',align:'center',label : '<s:text name="upCost.operateDate" />',hidden:false,formatter:'date'},				
{name:'operatorName',index:'operatorName',align:'center',label : '<s:text name="upCost.operatorName" />',hidden:false},		 */		
{name:'remark',index:'remark',align:'left',label : '<s:text name="upCost.remark" />',hidden:false,edittype:"text",editable:true},				
{name:'operatorName',index:'operatorName',align:'left',label : '<s:text name="upCost.operatorName" />',hidden:false,width:80},				
{name:'state',index:'state',align:'center',label : '<s:text name="upCost.state" />',hidden:false,formatter:'select',edittype:"select",editoptions:{value:"0:新建;1:已确认;2:已审核"},width:60},				
{name:'state',index:'state',align:'center',label : '<s:text name="upCost.state" />',hidden:true,formatter:addOne,edittype:"text",editable:true}				
        ],
        jsonReader : {
			root : "upCosts", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        viewrecords: true,
        //caption:'<s:text name="upCostList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:true,
		<c:if test="${upItemType==1}">
		sortname: 'personId.personCode',
        sortorder: 'asc',
		grouping:true,
	   	groupingView : {
	   		groupField : ['deptId.name'],
	   		//groupSummary : [true],
			groupColumnShow : [true],
			groupText : ['<b>{0}</b> (记录数:{1}) '],
			groupDataSorted :[false]
	   	},
	   	</c:if>
	   	<c:if test="${upItemType!=1}">
	   	sortname: 'deptId.internalCode',
        sortorder: 'asc',
	   	</c:if>
        onSelectRow: function(rowid) {
       
       	},
		 gridComplete:function(){
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           //fullGridEdit(upCostGridIdString);
           var dataTest = {"id":"${random}_upCost_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("${random}_upCost_gridtable");
      	   
      	 var sumDataJson = jQuery("#${random}_upCost_gridtable").getGridParam('userData');
      	var sumDataStr = sumDataJson.amount;
      	 jQuery("#${random}_allSumUpcost").text(formatNum(parseFloat(sumDataStr).toFixed(2)));
			var amount = jQuery("#${random}_upCost_gridtable").getCol('amount',false,'sum');
			//alert(amount);
			jQuery("#${random}_pageSumUpcost").text(formatNum(amount.toFixed(2)));
       	} 

    });
    jQuery(upCostGrid).jqGrid('bindKeys');
    
	
	//upCostLayout.resizeAll();
	jQuery("#${random}_upCost_gridtable").unbind("keyup").bind("keyup",function(e){
		chargeKeyPress(jQuery("#${random}_upCost_gridtable"),e);
	});
    
	jQuery("#${random}_upCost_gridtable_init").unbind("click").bind("click",function(){
		if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
			alertMsg.error("您没有维护权限！");
			return;
		}
		$.ajax({
		    url: 'initUpCost',
		    type: 'post',
		    data:{upItemId:jQuery("#${random}_upItemId").val()},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		        // do something with xml
		        data.navTabId='${random}_upCost_gridtable';
		        formCallBack(data);
		    }
		});
	});
	
	
	jQuery("#${random}_upCost_gridtable_addUp").unbind("click").bind("click",function(){
		if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
			alertMsg.error("您没有维护权限！");
			return;
		}
		jQuery.ajax({
		    url: 'isUpdata',
		    type: 'post',
		    data:{upItemId:jQuery("#${random}_upItemId").val()},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(json){
		        // do something with xml
		        if (json.statusCode === undefined && json.message === undefined) {
					if (alertMsg)
						return alertMsg.error(json);
					else
						return alert(json);
				}
				if (json.statusCode == DWZ.statusCode.error) {
					if (json.message && alertMsg)
						alertMsg.error(json.message);
				} else if (json.statusCode == DWZ.statusCode.timeout) {
					if (alertMsg)
						alertMsg.error(json.message || DWZ.msg("sessionTimout"), {
							okCall : DWZ.loadLogin
						});
					else
						DWZ.loadLogin();
				} else {
					var url = "editUpCost?popup=true&navTabId=${random}_upCost_gridtable&upItemId="+jQuery("#${random}_upItemId").val()+"&random=${random}";
		    		var upItemType = jQuery("#${random}_upItemType").val();
		    		var winTitle="添加人员上报数据";
		    		if(upItemType==0){
		    			winTitle="添加科室上报数据";
		    		}
		    		//alert(url);
		    		url = encodeURI(url);
		    		$.pdialog.open(url, 'editUpCost', winTitle, {mask:false,width:500,height:350});
				}
		    }
		});
	});
	
	
	jQuery("#${random}_upCost_gridtable_save").unbind("click").bind("click",function(){
		if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
			alertMsg.error("您没有维护权限！");
			return;
		}
		if(jQuery("#${random}_editType").val()=='0'){
			alertMsg.error("请在编辑状态下保存！");
			return;
		}else{
			
		}
		saveGridData(jQuery("#${random}_upCost_gridtable"),upCostNeedSaveCol,breakCondition);
	});
	
	jQuery("#${random}_upCost_gridtable_submit").unbind("click").bind("click",function(){if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
		alertMsg.error("您没有维护权限！");
		return;
	}
	//saveGridData(jQuery(upCostGridIdString),upCostNeedSubmitCol,breakCondition);
	//upitemid.taskName
	var taskName = "${taskName}";
	if(taskName==null||taskName==''){
		$.ajax({
		    url: 'submitUpcost',
		    type: 'post',
		    data:{upItemId:jQuery("#${random}_upItemId").val(),navTabId:'${random}_upCost_gridtable'},
		    dataType: 'json',
		    async:false,
		    error: function(data2){
		        //jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data2){
		        // do something with xml
		        formCallBack(data2);
		    }
		}); 
	}else{
		var proArgsStr ="${checkPeriod},"+jQuery("#${random}_upItemId").val()+",${personId}";
		$.ajax({
				    url: 'executeSp',
				    type: 'post',
				    data:{taskName:taskName,proArgsStr:proArgsStr},
				    dataType: 'json',
				    async:false,
				    error: function(data){
				        
				    },
				    success: function(data){
				    	if(data.statusCode >= 1&&data.statusCode<300){
				    		$.ajax({
				    		    url: 'submitUpcost',
				    		    type: 'post',
				    		    data:{upItemId:jQuery("#${random}_upItemId").val(),navTabId:'${random}_upCost_gridtable'},
				    		    dataType: 'json',
				    		    async:false,
				    		    error: function(data2){
				    		        //jQuery('#name').attr("value",data.responseText);
				    		    },
				    		    success: function(data2){
				    		        // do something with xml
				    		        formCallBack(data2);
				    		    }
				    		}); 
				    	}else{
				    		formCallBack(data);
				    	}
				    }
		});
		
	}
	
		
	});
	
	jQuery("#${random}_upCost_gridtable_editUp").unbind("click").bind("click",function(){
		jQuery("#${random}_editType").val(1);
		fullGridEdit("#${random}_upCost_gridtable");
	});
	
	jQuery("#${random}_upCost_gridtable_canceledit").unbind("click").bind("click",function(){
		jQuery("#${random}_editType").val(0);
		jQuery("#${random}_upCost_gridtable").trigger("reloadGrid");
	});
	
	jQuery("#${random}_upCost_gridtable_unfold").unbind("click").bind("click",function(){
		if(jQuery("#${random}_extend").val()=='0'){
			jQuery("#${random}_upCost_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['deptId.name'],groupCollapse:true}}).trigger("reloadGrid");
			jQuery("#${random}_extend").val(1);
		}else{
			jQuery("#${random}_upCost_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['deptId.name'],groupCollapse:false}}).trigger("reloadGrid");
			jQuery("#${random}_extend").val(0);
		}
	});
	
	jQuery("#${random}_upCost_gridtable_inherit").unbind("click").bind("click",function(){
		if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
			alertMsg.error("您没有维护权限！");
			return;
		}
		var url = "eiditInheritanceUpCost?navTabId=${random}_upCost_gridtable&upItemId=${upItemId }&upItemType=${upItemType}";
		$.pdialog.open(url, 'inheritanceUpCost', "继承上报数据", {mask:false,width:500,height:240});
	});
	
	/* jQuery("#${random}_extendORCollapse").unbind('click').bind('click',function(){
		if(jQuery("#${random}_extend").val()=='0'){
			jQuery("#${random}_upCost_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['deptId.name'],groupCollapse:true}}).trigger("reloadGrid");
			jQuery("#${random}_extend").val(1);
		}else{
			jQuery("#${random}_upCost_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['deptId.name'],groupCollapse:false}}).trigger("reloadGrid");
			jQuery("#${random}_extend").val(0);
		}
	}); */
	/*----------------------------------tooBar start -----------------------------------------------*/
	var upCost_menuButtonArrJson = "${menuButtonArrJson}";
	var upCost_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(upCost_menuButtonArrJson,false)));
	var upCost_toolButtonBar = new ToolButtonBar({el:$('#${random}_upCost_buttonBar'),collection:upCost_toolButtonCollection,attributes:{
		tableId : '${random}_upCost_gridtable',
		baseName : '${random}_upCost',
		width : 500,
		height : 350,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : '<s:text name="upCostNew.title"/>',
		editTitle : null
	}}).render();
	
	var upCost_function = new scriptFunction();
	upCost_function.optBeforeCall = function(e,$this,param){
		var pass = false;
		if(param.selectRecord){
			var sid = jQuery("#${random}_upCost_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
	        	alertMsg.error("请选择记录！");
				return pass;
			}
	        if(param.singleSelect){
	        	if(sid.length != 1){
		        	alertMsg.error("只能选择一条记录！");
					return pass;
				}
	        }
		}
        return true;
	};
	//初始化
	upCost_toolButtonBar.addCallBody('01030201',function(){
		if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
			alertMsg.error("您没有维护权限！");
			return;
		}
		$.ajax({
		    url: 'initUpCost',
		    type: 'post',
		    data:{upItemId:jQuery("#${random}_upItemId").val()},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		        // do something with xml
		        data.navTabId='${random}_upCost_gridtable';
		        formCallBack(data);
		    }
		});
	},{});
	upCost_toolButtonBar.addBeforeCall('01030201',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	//添加
	upCost_toolButtonBar.addCallBody('01030202',function(){
		if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
			alertMsg.error("您没有维护权限！");
			return;
		}
		jQuery.ajax({
		    url: 'isUpdata',
		    type: 'post',
		    data:{upItemId:jQuery("#${random}_upItemId").val()},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(json){
		        // do something with xml
		        if (json.statusCode === undefined && json.message === undefined) {
					if (alertMsg)
						return alertMsg.error(json);
					else
						return alert(json);
				}
				if (json.statusCode == DWZ.statusCode.error) {
					if (json.message && alertMsg)
						alertMsg.error(json.message);
				} else if (json.statusCode == DWZ.statusCode.timeout) {
					if (alertMsg)
						alertMsg.error(json.message || DWZ.msg("sessionTimout"), {
							okCall : DWZ.loadLogin
						});
					else
						DWZ.loadLogin();
				} else {
					var url = "editUpCost?popup=true&navTabId=${random}_upCost_gridtable&upItemId="+jQuery("#${random}_upItemId").val()+"&random=${random}";
		    		var upItemType = jQuery("#${random}_upItemType").val();
		    		var winTitle="添加人员上报数据";
		    		if(upItemType==0){
		    			winTitle="添加科室上报数据";
		    		}
		    		//alert(url);
		    		url = encodeURI(url);
		    		$.pdialog.open(url, 'editUpCost', winTitle, {mask:false,width:500,height:350});
				}
		    }
		}); 
	},{});
	upCost_toolButtonBar.addBeforeCall('01030202',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	//删除
	upCost_toolButtonBar.addFunctionDel("01030203");
	upCost_toolButtonBar.addBeforeCall('01030203',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord"});
	//保存
	upCost_toolButtonBar.addCallBody('01030204',function(){
		if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
			alertMsg.error("您没有维护权限！");
			return;
		}
		if(jQuery("#${random}_editType").val()=='0'){
			alertMsg.error("请在编辑状态下保存！");
			return;
		}else{
			
		}
		saveGridData(jQuery("#${random}_upCost_gridtable"),upCostNeedSaveCol,breakCondition);
	},{});
	upCost_toolButtonBar.addBeforeCall('01030204',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	//提交
	upCost_toolButtonBar.addCallBody('01030205',function(){
		if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
			alertMsg.error("您没有维护权限！");
			return;
		}
		//saveGridData(jQuery(upCostGridIdString),upCostNeedSubmitCol,breakCondition);
		//upitemid.taskName
		var taskName = "${taskName}";
		if(taskName==null||taskName==''){
			$.ajax({
    		    url: 'submitUpcost',
    		    type: 'post',
    		    data:{upItemId:jQuery("#${random}_upItemId").val(),navTabId:'${random}_upCost_gridtable'},
    		    dataType: 'json',
    		    async:false,
    		    error: function(data2){
    		        //jQuery('#name').attr("value",data.responseText);
    		    },
    		    success: function(data2){
    		        // do something with xml
    		        formCallBack(data2);
    		    }
    		}); 
		}else{
			var proArgsStr ="${checkPeriod},"+jQuery("#${random}_upItemId").val()+",${personId}";
			$.ajax({
					    url: 'executeSp',
					    type: 'post',
					    data:{taskName:taskName,proArgsStr:proArgsStr},
					    dataType: 'json',
					    async:false,
					    error: function(data){
					        
					    },
					    success: function(data){
					    	if(data.statusCode >= 1&&data.statusCode<300){
					    		$.ajax({
					    		    url: 'submitUpcost',
					    		    type: 'post',
					    		    data:{upItemId:jQuery("#${random}_upItemId").val(),navTabId:'${random}_upCost_gridtable'},
					    		    dataType: 'json',
					    		    async:false,
					    		    error: function(data2){
					    		        //jQuery('#name').attr("value",data.responseText);
					    		    },
					    		    success: function(data2){
					    		        // do something with xml
					    		        formCallBack(data2);
					    		    }
					    		}); 
					    	}else{
					    		formCallBack(data);
					    	}
					    }
			});
			
		}
		
	},{});
	upCost_toolButtonBar.addBeforeCall('01030205',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	//编辑
	upCost_toolButtonBar.addCallBody('01030206',function(){
		jQuery("#${random}_editType").val(1);
		fullGridEdit("#${random}_upCost_gridtable");
	},{});
	upCost_toolButtonBar.addBeforeCall('01030206',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	//取消编辑
	upCost_toolButtonBar.addCallBody('01030207',function(){
		jQuery("#${random}_editType").val(0);
		jQuery("#${random}_upCost_gridtable").trigger("reloadGrid");
	},{});
	upCost_toolButtonBar.addBeforeCall('01030207',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	//继承
	upCost_toolButtonBar.addCallBody('01030208',function(){
		if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
			alertMsg.error("您没有维护权限！");
			return;
		}
		var url = "eiditInheritanceUpCost?navTabId=${random}_upCost_gridtable&upItemId=${upItemId }&upItemType=${upItemType}";
		$.pdialog.open(url, 'inheritanceUpCost', "继承上报数据", {mask:false,width:500,height:240});
	},{});
	upCost_toolButtonBar.addBeforeCall('01030208',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	//展开收缩
	upCost_toolButtonBar.addCallBody('01030209',function(){
		if(jQuery("#${random}_extend").val()=='0'){
			jQuery("#${random}_upCost_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['deptId.name'],groupCollapse:true}}).trigger("reloadGrid");
			jQuery("#${random}_extend").val(1);
		}else{
			jQuery("#${random}_upCost_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['deptId.name'],groupCollapse:false}}).trigger("reloadGrid");
			jQuery("#${random}_extend").val(0);
		}
	},{});
	upCost_toolButtonBar.addBeforeCall('01030209',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	
	/*----------------------------------tooBar doublestart -----------------------------------------------*/
		//初始化
	upCost_toolButtonBar.addCallBody('01030301',function(){
		if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
			alertMsg.error("您没有维护权限！");
			return;
		}
		$.ajax({
		    url: 'initUpCost',
		    type: 'post',
		    data:{upItemId:jQuery("#${random}_upItemId").val()},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		        // do something with xml
		        data.navTabId='${random}_upCost_gridtable';
		        formCallBack(data);
		    }
		});
	},{});
	upCost_toolButtonBar.addBeforeCall('01030301',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	//添加
	upCost_toolButtonBar.addCallBody('01030302',function(){
		if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
			alertMsg.error("您没有维护权限！");
			return;
		}
		jQuery.ajax({
		    url: 'isUpdata',
		    type: 'post',
		    data:{upItemId:jQuery("#${random}_upItemId").val()},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(json){
		        // do something with xml
		        if (json.statusCode === undefined && json.message === undefined) {
					if (alertMsg)
						return alertMsg.error(json);
					else
						return alert(json);
				}
				if (json.statusCode == DWZ.statusCode.error) {
					if (json.message && alertMsg)
						alertMsg.error(json.message);
				} else if (json.statusCode == DWZ.statusCode.timeout) {
					if (alertMsg)
						alertMsg.error(json.message || DWZ.msg("sessionTimout"), {
							okCall : DWZ.loadLogin
						});
					else
						DWZ.loadLogin();
				} else {
					var url = "editUpCost?popup=true&navTabId=${random}_upCost_gridtable&upItemId="+jQuery("#${random}_upItemId").val()+"&random=${random}";
		    		var upItemType = jQuery("#${random}_upItemType").val();
		    		var winTitle="添加人员上报数据";
		    		if(upItemType==0){
		    			winTitle="添加科室上报数据";
		    		}
		    		//alert(url);
		    		url = encodeURI(url);
		    		$.pdialog.open(url, 'editUpCost', winTitle, {mask:false,width:500,height:350});
				}
		    }
		}); 
	},{});
	upCost_toolButtonBar.addBeforeCall('01030302',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	//删除
	upCost_toolButtonBar.addFunctionDel("01030303");
	upCost_toolButtonBar.addBeforeCall('01030203',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord"});
	//保存
	upCost_toolButtonBar.addCallBody('01030304',function(){
		if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
			alertMsg.error("您没有维护权限！");
			return;
		}
		if(jQuery("#${random}_editType").val()=='0'){
			alertMsg.error("请在编辑状态下保存！");
			return;
		}else{
			
		}
		saveGridData(jQuery("#${random}_upCost_gridtable"),upCostNeedSaveCol,breakCondition);
	},{});
	upCost_toolButtonBar.addBeforeCall('01030304',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	//提交
	upCost_toolButtonBar.addCallBody('01030305',function(){
		if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
			alertMsg.error("您没有维护权限！");
			return;
		}
		//saveGridData(jQuery(upCostGridIdString),upCostNeedSubmitCol,breakCondition);
		//upitemid.taskName
		var taskName = "${taskName}";
		if(taskName==null||taskName==''){
			$.ajax({
    		    url: 'submitUpcost',
    		    type: 'post',
    		    data:{upItemId:jQuery("#${random}_upItemId").val(),navTabId:'${random}_upCost_gridtable'},
    		    dataType: 'json',
    		    async:false,
    		    error: function(data2){
    		        //jQuery('#name').attr("value",data.responseText);
    		    },
    		    success: function(data2){
    		        // do something with xml
    		        formCallBack(data2);
    		    }
    		}); 
		}else{
			var proArgsStr ="${checkPeriod},"+jQuery("#${random}_upItemId").val()+",${personId}";
			$.ajax({
					    url: 'executeSp',
					    type: 'post',
					    data:{taskName:taskName,proArgsStr:proArgsStr},
					    dataType: 'json',
					    async:false,
					    error: function(data){
					        
					    },
					    success: function(data){
					    	if(data.statusCode >= 1&&data.statusCode<300){
					    		$.ajax({
					    		    url: 'submitUpcost',
					    		    type: 'post',
					    		    data:{upItemId:jQuery("#${random}_upItemId").val(),navTabId:'${random}_upCost_gridtable'},
					    		    dataType: 'json',
					    		    async:false,
					    		    error: function(data2){
					    		        //jQuery('#name').attr("value",data.responseText);
					    		    },
					    		    success: function(data2){
					    		        // do something with xml
					    		        formCallBack(data2);
					    		    }
					    		}); 
					    	}else{
					    		formCallBack(data);
					    	}
					    }
			});
			
		}
		
	},{});
	upCost_toolButtonBar.addBeforeCall('01030305',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	//编辑
	upCost_toolButtonBar.addCallBody('01030306',function(){
		jQuery("#${random}_editType").val(1);
		fullGridEdit("#${random}_upCost_gridtable");
	},{});
	upCost_toolButtonBar.addBeforeCall('01030306',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	//取消编辑
	upCost_toolButtonBar.addCallBody('01030307',function(){
		jQuery("#${random}_editType").val(0);
		jQuery("#${random}_upCost_gridtable").trigger("reloadGrid");
	},{});
	upCost_toolButtonBar.addBeforeCall('01030307',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	//继承
	upCost_toolButtonBar.addCallBody('01030308',function(){
		if((jQuery("#${random}_isHaveRight").val()=='0') && (jQuery("#${random}_upItemType").val()=='1')){
			alertMsg.error("您没有维护权限！");
			return;
		}
		var url = "eiditInheritanceUpCost?navTabId=${random}_upCost_gridtable&upItemId=${upItemId }&upItemType=${upItemType}";
		$.pdialog.open(url, 'inheritanceUpCost', "继承上报数据", {mask:false,width:500,height:240});
	},{});
	upCost_toolButtonBar.addBeforeCall('01030308',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	//展开收缩
	upCost_toolButtonBar.addCallBody('01030309',function(){
		if(jQuery("#${random}_extend").val()=='0'){
			jQuery("#${random}_upCost_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['deptId.name'],groupCollapse:true}}).trigger("reloadGrid");
			jQuery("#${random}_extend").val(1);
		}else{
			jQuery("#${random}_upCost_gridtable").jqGrid('setGridParam',{groupingView:{groupField : ['deptId.name'],groupCollapse:false}}).trigger("reloadGrid");
			jQuery("#${random}_extend").val(0);
		}
	},{});
	upCost_toolButtonBar.addBeforeCall('01030309',function(e,$this,param){
		return upCost_function.optBeforeCall(e,$this,param);
	},{});
	/*----------------------------------tooBar end -----------------------------------------------*/
	var herpType = "${fns:getHerpType()}";
	if(herpType == "M") {
		//var orgPrivArr = orgPriv.split(",");
		var orgPriv = "${fns:u_readDP('org_dp')}";
		var sql = "select orgCode id,orgname name,ISNULL(parentOrgCode,'-1') parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT'";
		if(orgPriv) {
			sql += " and orgCode in ${fns:u_readDPSql('org_dp')}";
		} else {
			sql += " and 1=2";
		}
			sql += " ORDER BY orderCol"; 
		jQuery("#${random}upCost_costOrg").treeselect({
    		optType : "multi",
			dataType : 'sql',
			sql : sql,
			exceptnullparent : true,
			lazy : false,
			minWidth : '180px',
			selectParent : false
		});
		/* if(orgPrivArr.length == 1 && orgPrivArr[0] != "") {
			jQuery("#${random}upCost_costOrg").attr("readonly","readonly");
			jQuery("#${random}upCost_costOrg").unbind('click');
		} */
	} else {
		jQuery("#${random}upCost_costOrg_label").hide();
	}
	if(herpType == "S2") {
		/* var branchPriv = "${fns:u_readDP('branch_dp')}";
		var sql = "select branchCode id,branchName name,'-1' parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,branchCode orderCol from t_branch where disabled=0 AND branchCode<>'XT'";
		if(branchPriv) {
			sql += " and branchCode in ${fns:u_readDPSql('branch_dp')}";
		} else {
			sql += " and 1=2";
		}
			sql += " ORDER BY orderCol"; 
		jQuery("#${random}upCost_branch").treeselect({
    		optType : "multi",
			dataType : 'sql',
			sql : sql,
			exceptnullparent : true,
			lazy : false,
			minWidth : '180px',
			selectParent : false
		}); */
	} else {
		//jQuery("#${random}upCost_branch_label").hide();
	}
	
	/* jQuery("#${random}upCost_org").treeselect({
		optType : "multi",
		dataType : 'sql',
		sql : sqlStr,
		exceptnullparent : true,
		lazy : false,
		minWidth : '180px',
		selectParent : false
	}); */
	//结账状态显示
	var statusText = "";
	if("${loginPeriodClosed}" == "true"){
		statusText = "<font color='red'>&nbsp;&nbsp;已结账</font>";
		jQuery("#${random}_upCost_modelStatus").html(statusText);
	} /* else {
		if("${loginPeriodStarted}" != "true") {
			statusText = "<font color='red'>&nbsp;&nbsp;未使用</font>";
			jQuery("#${random}_upCost_modelStatus").html(statusText);
		}
	} */
	
  	});
	
	/* function initUpCpstList(){
		if(jQuery("#${random}_isHaveRight").val()=='0'){
			alertMsg.error("您没有维护权限！");
			return;
		}
		$.ajax({
		    url: 'initUpCost',
		    type: 'post',
		    data:{upItemId:jQuery("#${random}_upItemId").val()},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		        // do something with xml
		        data.navTabId='${random}_upCost_gridtable';
		        formCallBack(data);
		    }
		});
	} */
	
	
	/* function addUpCost(){
		if(jQuery("#${random}_isHaveRight").val()=='0'){
			alertMsg.error("您没有维护权限！");
			return;
		}
		
		var url = "editUpCost?popup=true&navTabId=${random}_upCost_gridtable&upItemId="+jQuery("#${random}_upItemId").val()+"&random=${random}";
		var upItemType = jQuery("#${random}_upItemType").val();
		var winTitle="添加人员上报数据";
		if(upItemType==0){
			winTitle="添加科室上报数据";
		}
		//alert(url);
		url = encodeURI(url);
		$.pdialog.open(url, 'editUpCost', winTitle, {mask:false,width:500,height:350});
	} */
	
	var upCostNeedSaveCol = ["amount,text","remark,text"];
	var upCostNeedSubmitCol = ["state,text"];
	var breakCondition = function(grid){
		var selid = grid.jqGrid('getGridParam', 'selarrrow');
		for(var i=0;i<selid.length;i++){
			var row = grid.jqGrid('getRowData',selid[i]);
			var state = row['state'];
			var upitemName = row['upitemName'];
			var checkperiod = row['checkperiod'];
			if(state==1){
				alertMsg.error(checkperiod+"的"+upitemName+" 的记录已审核！");
				return;
			}
		}
	}

	function saveGridData(grid,needSaveCol,breakCondition,beforeCall,afterCall){
		
		/* var sid = grid.jqGrid('getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			//alert("<fmt:message key='list.selectNone'/>");
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
		}else{
			if(breakCondition&&typeof(breakCondition)=="function"){
				breakCondition(grid);
			}
		} */
		var editData = "id=";
		var url = "saveUpCostInfo";
		var ids = grid.jqGrid('getDataIDs');
		for(var idI=0;idI<ids.length;idI++){
			editData += ids[idI]+",";
		}
		editData += "end@";
		for(var i=0;i<needSaveCol.length;i++){
			if(needSaveCol[i].split(",")[1]=="text"){
				editData += needSaveCol[i].split(",")[0]+"=";
				jQuery("input[name='"+needSaveCol[i].split(",")[0]+"']").each(function(){
					editData += jQuery(this).val()+",";
				});
				editData += "end@";
			}else
			if(needSaveCol[i].split(",")[1]=="checkbox"){
				editData += needSaveCol[i].split(",")[0]+"=";
				jQuery(":checkbox[name='"+needSaveCol[i].split(",")[0]+"']").each(function(){
					var checked = jQuery(this).attr("checked");
					if(checked=="checked"){
						editData += 1+",";
					}else{
						editData += 0+",";
					}
				});
				editData += "end@";
			}else if(needSaveCol[i].split(",")[1]=="textarea"){
				editData += needSaveCol[i].split(",")[0]+"=";
				jQuery("textarea[name='"+needSaveCol[i].split(",")[0]+"']").each(function(){
					editData += jQuery(this).val()+",";
				});
				editData += "end@";
			}else if(needSaveCol[i].split(",")[1]=="select"){
				editData += needSaveCol[i].split(",")[0]+"=";
				jQuery("select[name='"+needSaveCol[i].split(",")[0]+"']").each(function(){
					editData += jQuery(this).val()+",";
				});
				editData += "end@";
			}
		}
		$.ajax({
		    url: url,
		    type: 'post',
		    data:{defData_editData:editData,navTabId:'${random}_upCost_gridtable'},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        //jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		        jQuery("#${random}_editType").val(0);
		        grid.trigger("reloadGrid");
		        formCallBack(data);
		    }
		}); 
	}
	/* function submitUpCosts(grid,isHaveRight,state,breakCondition,beforeCall,afterCall){
		
	} */
	function addOne(cellvalue, options, rowObject){
		cellvalue = cellvalue+1;
		return cellvalue;
	}
	
</script>
<div class="page">
<div class="pageHeader">
	<div class="searchBar">
		<div class="searchContent">
			<form id="${random}_upCost_search_form" action="" class="queryarea-form">
			
				<label class="queryarea-label" id="${random}upCost_costOrg_label">
					<fmt:message key="hisOrg.orgName"></fmt:message>:
					<input type="text" id="${random}upCost_costOrg" />
					<input type="hidden" id="${random}upCost_costOrg_id"/>
				</label>
				<label class="queryarea-label" id="${random}upCost_branch_label" style="${herpType=='S2'?'':'display:none'}">
					<fmt:message key="hisOrg.branchName"></fmt:message>:
					<%-- <input type="text" id="${random}upCost_branch" />
					<input type="hidden" id="${random}upCost_branch_id"/> --%>
					<s:select id="%{random}upCost_branch_id" list="branches" headerKey="" headerValue="--" listKey="branchCode" listValue="branchName" emptyOption="false" theme="simple"></s:select>
				</label>
				
				<label class="queryarea-label" id="${random}upCost_dept_label">
					<s:text name="科室名称"></s:text>:
					<input type="text" id="${random}upCost_dept" />
				</label>
				<label class="queryarea-label">
					<s:text name="upCost.amount" />:
					<input type="text" class="input-mini" style="width:75px;" id="${random}upCost_amount_min" />-
					<input type="text" class="input-mini" style="width:75px;" id="${random}upCost_amount_max" />
				</label>
				<div class="buttonActive" style="float:right;">
					<div class="buttonContent">
						<button type="button" onclick="upCostGridReload('${random}')"><s:text name='button.search'/></button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<!-- <div id="upCost_container">
			<div id="upCost_layout-center"
				class="pane ui-layout-center"> -->
	<%-- <div class="pageHeader">
			<div class="searchBar">
				<form id="${random}_nurseDayScore_search_form" >
					<s:if test="upItemType==1">
						<label style="float:none;white-space:nowrap" >
						<s:text name="upCost.jjdeptName"/>:
						<s:textfield key="upCost.costitemid" theme="simple"></s:textfield>
						</label>
						<label style="float:none;white-space:nowrap" >
						<s:text name="upCost.personName"/>:
						<input type="hidden" id="upCost_personId_bcs" name="filter_EQS_personId.personId"/>
						<s:textfield id="upCost_personName_bcs" name="filter_exclude_personId.personId" theme="simple"></s:textfield>
						<script>
						addTreeSelect("tree","sync","upCost_personName_bcs","upCost_personId_bcs","single"
								,{tableName:"t_person",treeId:"personId",treeName:"name",parentId:"dept_id",filter:" disabled=0"
								,classTable:"t_department",classTreeId:"deptId",classTreeName:"name",classFilter:" disabled=0"});
						</script>
						</label>
					</s:if>
					<s:else>
					<label style="float:none;white-space:nowrap" >
						<s:text name="upCost.deptName"/>:
						<input type="hidden" id="upCost_cbDeptId" name="filter_EQS_deptId.departmentId"/>
						<s:textfield id="upCost_cbDeptName" name="filter_exclude_deptId.departmentId" theme="simple" ></s:textfield>
						<script type="text/javascript">
						addTreeSelect("tree","sync","upCost_cbDeptName","upCost_cbDeptId","single"
								,{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"deptType",filter:" disabled=0"
								,classTable:"t_deptType",classTreeId:"deptTypeName",classTreeName:"deptTypeName",classFilter:" disabled=0"});
						</script>
					</label>
					</s:else>
				</form>
				<table class="searchContent">
					<tr>
						<td><s:text name='upCost.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='upCost.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='upCost.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='upCost.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="upCostGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div> --%>
	<div class="pageContent">
		<div class="panelBar" id="${random}_upCost_buttonBar">
			<%--  <ul class="toolBar">
				<li><a  id="${random}_upCost_gridtable_init" class="initbutton" href="javaScript:" ><span>初始化
					</span>
				</a>
				</li>
				<li><a id="${random}_upCost_gridtable_addUp" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="${random}_upCost_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="${random}_upCost_gridtable_save" class="savebutton"  href="javaScript:"
					><span>保存
					</span>
				</a>
				</li>
				<li><a id="${random}_upCost_gridtable_submit" class="submitbutton"  href="javaScript:"
					><span>提交
					</span>
				</a>
				</li>
				<li><a id="${random}_upCost_gridtable_editUp" class="edit"  href="javaScript:"
					><span>编辑
					</span>
				</a>
				</li>
				<li><a id="${random}_upCost_gridtable_canceledit" class="canceleditbutton"  href="javaScript:"
					><span>取消编辑
					</span>
				</a>
				</li>
				<li><a id="${random}_upCost_gridtable_inherit" class="inheritbutton"  href="javaScript:"
					><span>继承
					</span>
				</a>
				</li>
				<s:if test="upItemType==1">
				<li><a id="${random}_upCost_gridtable_unfold" class="unfoldbutton"   href="javaScript:"
					><span>展开/收缩
					</span>
				</a>
				</li>
				</s:if>
			</ul>  --%>
		</div>
		<input type="hidden" id="${random}_editType" value="0"/>
		<input type="hidden" id="${random}_upItemId" value="${upItemId }"/>
		<input type="hidden" id="${random}_upItemClass" value="${upItemClass }"/>
		<input type="hidden" id="${random}_upItemType" value="${upItemType }"/>
		<input type="hidden" id="${random}_isHaveRight" value="${isHaveRight }"/>
		<input type="hidden" id="${random}_extend" value="0"/>
		
		<div align="right"  style="margin-top: -20px;margin-right:5px;height:20px">
				本页金额合计：<label id="${random}_pageSumUpcost"></label>&nbsp;&nbsp;总计：<label id="${random}_allSumUpcost" ></label><label id="${random}_upCost_modelStatus"></label>
		</div>
		
		
		<div id="${random}_upCost_gridtable_div" tablecontainer="${tablecontainer }" extraHeight="151"
			class="grid-wrapdiv"
			buttonBar="optId:upcostId;width:500;height:300">
			<input type="hidden" id="${random}_upCost_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="${random}_upCost_gridtable_addTile">
				<s:text name="upCostNew.title"/>
			</label> 
			<label style="display: none"
				id="${random}_upCost_gridtable_editTile">
				<s:text name="upCostEdit.title"/>
			</label>
			<label style="display: none"
				id="${random}_upCost_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="${random}_upCost_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_${random}_upCost_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="${random}_upCost_gridtable"></table>
		<div id="upCostPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}_upCost_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="60">60</option>
				<option value="70">70</option>
				<option value="80">80</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}_upCost_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="${random}_upCost_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<!-- </div>
</div> -->