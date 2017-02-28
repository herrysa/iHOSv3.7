<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">

	function deptAppDistributeGridReload(){
		var urlString = "deptAppGridList?filter_NIS_appState=0,1,2";
		var appDeptId = jQuery("#deptAppDistribute_search_appDept_id").val();
		var storeId = jQuery("#deptAppDistribute_search_store_id").val();
		var appDateFrom = jQuery("#deptAppDistribute_search_app_date_from").val();
		var appDateTo = jQuery("#deptAppDistribute_search_app_date_to").val();
		var confirmDateFrom = jQuery("#deptAppDistribute_search_confirm_date_from").val();
		var confirmDateTo = jQuery("#deptAppDistribute_search_confirm_date_to").val();
		var appState = jQuery("#deptAppDistribute_search_appState").val();
	
		urlString=urlString+"&filter_EQS_store.id="+storeId+"&filter_EQS_appDept.departmentId="+appDeptId
				+"&filter_GED_appDate="+appDateFrom+"&filter_LED_appDate="+appDateTo
				+"&filter_GED_confirmDate="+confirmDateFrom+"&filter_LED_confirmDate="+confirmDateTo+"&filter_EQS_appState="+appState;
		urlString=encodeURI(urlString);
		jQuery("#deptAppDistribute_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}

	var deptAppDistributeGridIdString="#deptAppDistribute_gridtable";
	var width = 960,height = 628;
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var deptAppDistributeGrid = jQuery(deptAppDistributeGridIdString);
		deptAppDistributeGrid.jqGrid({
	    	url : "deptAppGridList?filter_NIS_appState=0,1,2&filter_GED_appDate="+jQuery("#deptAppDistribute_search_app_date_from").val()+"&filter_LED_appDate=${currentSystemVariable.businessDate}",
			datatype : "json",
			mtype : "GET",
			colModel:[
				{name:'deptAppId',index:'deptAppId',align:'center',label : '<s:text name="deptApp.deptAppId" />',hidden:true,key:true},				
				{name:'appNo',index:'appNo',align:'left',width : 120,label : '<s:text name="deptApp.appNo" />',hidden:false,highsearch:true},				
				{name:'store.storeName',index:'store.storeName',align:'left',width : 80,label : '<s:text name="deptApp.store" />',hidden:false,highsearch:true},				
				{name:'store.id',index:'store.id',align:'left',width : 80,label : '<s:text name="deptApp.store" />',hidden:true,highsearch:false},				
				{name:'appDept.name',index:'appDept.name',align:'left',width : 100,label : '<s:text name="deptApp.appDept" />',hidden:false,highsearch:true},				
				{name:'appPerson.name',index:'appPerson.name',align:'left',width : 70,label : '<s:text name="deptApp.appPerson" />',hidden:false,highsearch:true},				
				{name:'appDate',index:'appDate',align:'center',width : 100,label : '<s:text name="deptApp.appDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'confirmDate',index:'confirmDate',align:'center',width : 100,label : '<s:text name="deptApp.confirmDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'storeChecker.name',index:'storeChecker.name',align:'left',width : 80,label : '<s:text name="deptApp.storeChecker" />',hidden:false,highsearch:true},				
				{name:'storeCheckDate',index:'storeCheckDate',align:'center',width : 100,label : '<s:text name="deptApp.storeCheckDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				//{name:'generalBillNo',index:'generalBillNo',align:'center',width : 100,label : '<s:text name="deptApp.generalBillNo" />',hidden:false,highsearch:true},				
				//{name:'generalBillDate',index:'generalBillDate',align:'center',width : 130,label : '<s:text name="deptApp.generalBillDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'remark',index:'remark',align:'left',width : 200,label : '<s:text name="deptApp.remark" />',hidden:false,highsearch:true},			
				{name:'appState',index:'appState',align:'center',width : 100,label : '<s:text name="deptApp.appState" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '3:未处理;5:通过;6:部分通过-其余待处理;7:部分通过-其余未通过;8:未通过'}}				
			],
	        jsonReader : {
				root : "deptApps", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'appNo',
	        viewrecords: true,
	        gridview:true,
	        rownumbers:true,
	        sortorder: 'desc',
	        height:300,
	        loadui: "disable",
	       	multiselect: true,
			multiboxonly:true,
			shrinkToFit:true,
			autowidth:false,
			subGrid:true,
			/* subGridOptions: {
				selectOnExpand : true
			}, */
			subGridRowExpanded:function(subgrid_id,row_id){
				loadDeptAppDistributeSubGrid(subgrid_id,row_id);
			},
			onSelectRow : function(rowid) {//选中子表、反选子表
				var mainIds = jQuery(this).getDataIDs();
				 for(var i=0;i<mainIds.length;i++){
					if(mainIds[i]==rowid){
						continue;
					} 
					var check = jQuery("#"+mainIds[i]).find("td").eq(0).find("input").eq(0).attr("checked");
					if(check && check=="checked"){
						continue;
					}else{
						var sc = jQuery("#"+mainIds[i]).attr("aria-selected");
						if(sc=="true"){
							//jQuery("#deptAppDistribute_gridtable").setSelection(mainIds[i],false);
							cascadeMultiCheck(mainIds[i]); 
						}
					}
				} 
				cascadeMultiCheck(rowid);
			},
			ondblClickRow: function (rowid, iRow, iCol) {//展开子表、关闭子表
				/* var subGridId = "#deptAppDistribute_gridtable_"+rowid+"_t";
				if(jQuery(subGridId).length>0){//子表已展开
					jQuery("#deptAppDistribute_gridtable").collapseSubGridRow(rowid);
				}else{
					jQuery("#deptAppDistribute_gridtable").expandSubGridRow(rowid);
				} */
    		},
			gridComplete:function(){
				var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  id=rowIds[i];
	              	  if(ret[i]['appState']=="3"){
	              		  setCellText(this,id,'appState','<span >未处理</span>');
	              	  }else if(ret[i]['appState']=="5"){
	              		  setCellText(this,id,'appState','<span style="color:blue" >通过</span>');
	              	  }else if(ret[i]['appState']=="6"){
	              		  setCellText(this,id,'appState','<span style="color:green" >部分通过-其余待处理</span>');
	              	  }else if(ret[i]['appState']=="7"){
	              		  setCellText(this,id,'appState','<span style="color:#A500CC" >部分通过-其余未通过</span>');
	              	  }else if(ret[i]['appState']=="8"){
	              		  setCellText(this,id,'appState','<span style="color:red" >未通过</span>');
	              	  }
	   	        	  setCellText(this,id,'appNo','<span style="color:blue" >'+ret[i]["appNo"]+'</span>');
	              }
	            }
	           var dataTest = {"id":"deptAppDistribute_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("deptAppDistribute_gridtable");
	      	   initFlag = initColumn('deptAppDistribute_gridtable','com.huge.ihos.material.deptapp.model.DeptAppDistribute',initFlag);
			} 
		});
		jQuery(deptAppDistributeGrid).jqGrid('bindKeys');
		
		jQuery("#deptAppDistribute_search_store").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		}); 
	    jQuery("#deptAppDistribute_search_appDept").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
			exceptnullparent:false,
			lazy:false
		});
	});
	/*
		针对主记录的操作：结束申领、退回科室，勾选主记录，子记录是否勾选不影响
	*/
	function deptAppDistributeListEditOper(type){
		var msg = (type=="end"?"结束申领":"退回科室");
    	var url = "${ctx}/deptAppGridEdit?oper="+type
		var sid = jQuery("#deptAppDistribute_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var state = jQuery("#deptAppDistribute_gridtable").jqGrid('getCell',rowId,"appState");
				if(type=="back"){
					if(state!='3'){
						alertMsg.error("只能退回[未处理]的记录!");
						return;
					}
				} else if(type=="end"){
					if(state=='5' || state=='7' || state=='8'){
						alertMsg.error("您选择的记录已结束!");
						return;
					}
				}
			}
			url = url+"&id="+sid+"&navTabId=deptAppDistribute_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认"+msg+"？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		}
	}
	/*
		展开、收缩
	*/
	function foldOrUnfoldDeptAppDistribute(type){
		var sid = jQuery("#deptAppDistribute_gridtable").getDataIDs();
		if(type=='fold'){
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				jQuery("#deptAppDistribute_gridtable").collapseSubGridRow(rowId);
			}
		}else if(type=='unfold'){
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				jQuery("#deptAppDistribute_gridtable").expandSubGridRow(rowId);
				//expandSubGridRow(rowId);
			}
		}
	} 
	function cascadeMultiCheck(row_id){
		var subGridId = "#deptAppDistribute_gridtable_"+row_id+"_t";
		if(jQuery(subGridId).length>0){//子表已展开
			//设置子表全部选中
			var mainRowSelect = jQuery("#jqg_deptAppDistribute_gridtable_"+row_id).attr("checked");
			var subIds = jQuery(subGridId).getDataIDs();
			if(mainRowSelect && mainRowSelect=="checked"){//选中主行
				for(var i=0;i<subIds.length;i++){
					var trSelected = jQuery("#"+subIds[i]).find("td").eq(0).find("input").eq(0).attr("checked");
					if(trSelected && trSelected=="checked"){
						continue;
					}
					jQuery(subGridId).setSelection(subIds[i],false);
				}
			}else{//取消子记录勾选
				for(var i=0;i<subIds.length;i++){
					var trSelected = jQuery("#"+subIds[i]).find("td").eq(0).find("input").eq(0).attr("checked");
					if(!trSelected){
						continue;
					}
					jQuery(subGridId).setSelection(subIds[i],false);
				}
			}
		} 
	}	
	var subLayout;
	/*load subGrid*/
	function loadDeptAppDistributeSubGrid(subgrid_id,row_id){
		var mainState = jQuery("#deptAppDistribute_gridtable").jqGrid("getCell",row_id,"appState");
		var deptAppDistributeDetailLayout;
		var subgrid_table_id = subgrid_id+"_t";
		var deptAppDistributeDetailChangeData = function(selectedSearchId){
			subLayout = deptAppDistributeDetailLayout;
			if(selectedSearchId.length==0){
				deptAppDistributeDetailLayout.closeSouth();
				return;
			}
			var mainRow = jQuery("#deptAppDistribute_gridtable").jqGrid("getRowData",row_id);
			var storeId = mainRow['store.id'];
			var subRow = jQuery("#"+subgrid_table_id).jqGrid("getRowData",selectedSearchId);
			var invDictId = subRow['invDict.invId'];
			jQuery("#deptAppDistributeDetail").loadUrl("deptAppDistributeDetailList?storeId="+storeId+"&invDictId="+invDictId+"&subGridId="+subgrid_table_id+"&subRowId="+selectedSearchId);
			$("#background,#progressBar").hide();
		};
		deptAppDistributeDetailLayout = makeLayout({'baseName':'deptAppDistribute','activeGridTable':subgrid_table_id,'tableIds':'deptAppDistribute_gridtable;deptAppDistributeDetail_gridtable','proportion':2},deptAppDistributeDetailChangeData);
       
		jQuery("#"+subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll' border='0' style='margin-bottom:1px'></table>");
        var deptAppDistributeSubGrid = jQuery("#"+subgrid_table_id).jqGrid({
        	url : "deptAppDetailGridList?filter_EQS_deptApp.deptAppId="+row_id,
    		datatype : "json",
    		mtype : "GET",
    		colModel:[
				{name:'deptAppDetailId',index:'deptAppDetailId',align:'center',label : '<s:text name="deptAppDetail.deptAppDetailId" />',hidden:true,key:true,editable : false,sortable:false},	
				{name:'invDict.invId',index:'invDict.invId',align:'center',label : '<s:text name="inventoryDict.invId" />',hidden:true,sortable:false},	
				{name:'invDict.invName',index:'invDict.invName',align:'left',width:150,label : '材料名称',hidden:false},	
				{name:'invDict.invCode',index:'invDict.invCode',align:'left',width:80,label : '材料编码',hidden:false},	
				{name:'invDict.invModel',index:'invDict.invModel',align:'left',width:120,label : '型号规格',hidden:false},	
				{name:'invDict.firstUnit',index:'invDict.firstUnit',align:'center',width:80,label : '计量单位',hidden:false},	
				{name:'invDict.factory',index:'invDict.factory',align:'left',width:150,label : '生产厂商',hidden:false},	
				{name:'appAmount',index:'appAmount',align:'right',width:100,label : '申请数量',hidden:false,formatter : 'number'},	
				{name:'throughAmount',index:'throughAmount',align:'right',width:100,label : '本次通过数量',hidden:false,formatter : 'number',edittype:"text",editable : true,editrules:{number:true},
					editoptions:{dataEvents:[{type:'blur',fn: function(e) { validateDeptAppDetailPassAmount(this); }}]}},	
				{name:'noThroughAmount',index:'noThroughAmount',align:'right',width:100,label : '未通过数量',hidden:false,formatter : 'number'},	
				{name:'waitingAmount',index:'waitingAmount',align:'right',width:100,label : '等待通过数量',hidden:false,formatter : 'number'},	
				//{name:'appPrice',index:'appPrice',align:'right',width:100,label : '单价',hidden:false,formatter : 'currency'},	
				{name:'remark',index:'remark',align:'left',width:250,label : '备注',hidden:false,edittype:"text",editable : true}
			],
			jsonReader : {
				root : "deptAppDetails", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
    		sortname: 'invDict.invCode',
            sortorder: "desc",
            height:"100%", 
        	multiselect: true,
    		multiboxonly:true,
            shrinkToFit:false,
            autowidth:false,
            onSelectRow : function(rowid) {
   	        	var allSubSelected = true;
   	        	var subGridIds = jQuery(this).getDataIDs();
   	        	for(var i=0;i<subGridIds.length;i++){
   	        		var trChecked = jQuery("#"+subGridIds[i]).find("td").eq(0).find("input").eq(0).attr("checked");
					if(!trChecked){
						allSubSelected = false;
						break;
					}
   	        	}
   	        	var mtrCheck = jQuery("#"+row_id).find("td").eq(0).find("input").eq(0).attr("checked");
   	        	if(allSubSelected==false){
   	        		if(mtrCheck && mtrCheck=="checked"){
	   	        		jQuery("#deptAppDistribute_gridtable").setSelection(row_id,false);
   	        		}
   	        	}else{
   	        		if(!mtrCheck){
	   	        		jQuery("#deptAppDistribute_gridtable").setSelection(row_id,false);
   	        		}
   	        	}
   	        	deptAppDistributeDetailLayout.optClick();
    		},
    		ondblClickRow: function (rowid, iRow, iCol) {
    			editSubGrid(deptAppDistributeSubGrid,subgrid_id,rowid,deptAppDistributeDetailLayout);
    		},
            gridComplete:function(){
            	var dataTest = {"id":subgrid_table_id};
           	    jQuery.publish("onLoadSelect",dataTest,null);
           	   
           		var rowNum = jQuery(this).getDataIDs().length;
           		if(rowNum>0){
           			var rowIds = jQuery(this).getDataIDs();
                    var ret = jQuery(this).jqGrid('getRowData');
                    var rowid='';
                    for(var i=0;i<rowNum;i++){
                    	rowid=rowIds[i];
                    	var noThroughAmount = ret[i]['noThroughAmount'];
                    	var waitingAmount = ret[i]['waitingAmount'];
                     	noThroughAmount = convertStringToFloat(noThroughAmount);
                     	waitingAmount = convertStringToFloat(waitingAmount);
                     	if(mainState=="5" || mainState=="6"){//部分通过-其余待处理 、通过
                     		if(waitingAmount==0){
                     			jQuery("#"+rowid).find("td").css("background-color","#BBFFEE");
                     		}
                     	}else if(mainState=="7"){// 部分通过-其余未通过
                     		if(noThroughAmount==0){//待处理
                     			jQuery("#"+rowid).find("td").css("background-color","#BBFFEE");
                     		}else{
                     			jQuery("#"+rowid).find("td").css("background-color","#CCCCFF");
                     		}
                     	}else if(mainState=="8"){//未通过
                     		jQuery("#"+rowid).find("td").css("background-color","#FFCCCC");
                     	}
                    }
	            }
           		cascadeMultiCheck(row_id);
            }
        });
	}
	/*设置明细的一行处于编辑状态，并且展示layout区*/
	function editSubGrid(gridObj,subgrid_id,rowid,layoutObj){
		var isAllRowEditable = getAllRowEditable(subgrid_id);
		if(isAllRowEditable==true){//存在可编辑的行
           	var isRowEditable = getRowEditable(subgrid_id,rowid);
           	if(isRowEditable==true){//设置该行为可编辑
           		gridObj.jqGrid('editRow', rowid,{
           			"keys" : false,  
           		    "oneditfunc" : function(){
           		    	layoutObj.openSouth();
           		    	subLayout = layoutObj;
           		    }
           		});
           	}
		}
	}
	/*通过明细判断一行是否可编辑*/
	function getRowEditable(subgrid_id,rowid){
        var subRow = jQuery("#"+subgrid_id+"_t").jqGrid('getRowData',rowid);
        var waitingAmount = subRow['waitingAmount'];
		waitingAmount = convertStringToFloat(waitingAmount);
		if(waitingAmount==0){
			return false;
		}
        return true;
	}
	
	/*通过主单判断明细是否可编辑*/
	function getAllRowEditable(subgrid_id){
		var appTrId = subgrid_id.substr(subgrid_id.lastIndexOf('_')+1);
 		var gridId = subgrid_id.substring(0,subgrid_id.lastIndexOf('_'));
        var mainRow = jQuery("#"+gridId).getRowData(appTrId);
        var state = mainRow['appState'];
        if(state=="5" || state=="7" || state=="8"){//主单状态已完成则所有明细都不能编辑
        	return false;
        }
        return true;
	}
	
	function numberValidate(obj){
  		var objValue = jQuery(obj).val();
  		if(!isFloatOrNull(objValue)){
  			jQuery(obj).val("")
  			alertMsg.error("请输入数字！");
  			return false;
  		}else{
  			return true;
  		}
  	}
	
	/*编辑通过数量的校验，通过校验鼠标离开后保存，并联动layout区*/
	function validateDeptAppDetailPassAmount(obj){
		if(!numberValidate(obj)){
  			return;
  		}
		var tdObj = jQuery(obj).parent();//td
		var desc = tdObj.attr("aria-describedby");
		var subGridId = desc.substring(0,desc.lastIndexOf('_'));
		var trObj = tdObj.parent();
		var trId = trObj.children().eq(1).text();//tr
		var waitingAmount = jQuery("#"+subGridId).jqGrid("getCell",trId,"waitingAmount");
		var throughAmount = jQuery(obj).val();
		waitingAmount = convertStringToFloat(waitingAmount);
		throughAmount = convertStringToFloat(throughAmount);
		if(throughAmount>waitingAmount){
			jQuery(obj).val("");
			alertMsg.error("通过数量大于等待通过数量！");
			return;
		}
		//联动发放明细表
		var value = jQuery(obj).val();
		if(value==0){
			jQuery("#"+subGridId).jqGrid("saveRow",trId,{url:'clientArray'});
			return;
		}
		var complete = distributeAmount(value);
		if(complete===true){
			jQuery("#"+subGridId).jqGrid("saveRow",trId,{url:'clientArray'});
			updateRealDisData(subGridId,trId);
		}else{
			jQuery(obj).val("").focus();
		}
		//暂存数量
	}
	/*根据subGridId得到主记录的id*/
	function getMainRowIdBySubGridId(subGridId){
		var tempRowId = subGridId.substring(0,subGridId.lastIndexOf('_'));
		var mainRowId = tempRowId.substring(tempRowId.lastIndexOf('_')+1,tempRowId.length);
		return mainRowId;
	}
	/*根据明细输入的数量联动发放区*/
	function distributeAmount(amount){
		var complete = false;//表示是否满足发放
		var storeAmount = new Array();//库存量数组
		var batchIds = jQuery("#deptAppDistributeDetail_gridtable").getDataIDs();
		for(var i=0;i<batchIds.length;i++){
			var curAmount = jQuery("#deptAppDistributeDetail_gridtable").jqGrid("getCell",batchIds[i],"curAmount");
			curAmount = convertStringToFloat(curAmount);
			storeAmount.push(curAmount);
		}
		var realAmount = new Array();//发放量数组
		
		for(var i=0;i<storeAmount.length;i++){
			realAmount.push(0);
		}
		var diff = new Array();
		for(var i=0;i<storeAmount.length;i++){//最佳匹配 单个满足
			if(storeAmount[i]>=amount){
				diff[i] = storeAmount[i]-amount;
				complete = true;
			}else{
				diff[i] = Infinity;
			}
		}
		if(complete==true){//存在最佳匹配
			var minValue = diff[0],minIndex = 0;
			for(var i=0;i<diff.length;i++){//
				if(diff[i]<minValue){
					minValue = diff[i];
					minIndex = i;
				}
			}
			realAmount[minIndex] = amount;
		}
		
		if(complete==false){
			for(var i=0;i<storeAmount.length;i++){//按批号顺序匹配
				if(storeAmount[i]<amount){
					amount = amount- storeAmount[i];
					realAmount[i] = storeAmount[i];
				}else{
					realAmount[i] = amount;
					complete = true;
					break;
				}
			}
		}
		var lastAmount = new Array();//剩余量数组
		for(var i=0;i<storeAmount.length;i++){
			lastAmount.push(storeAmount[i]-realAmount[i]);
		}
		//联动发放区数量
		for(var i=0;i<batchIds.length;i++){
			jQuery("#deptAppDistributeDetail_gridtable").jqGrid("setCell",batchIds[i],"disAmount",realAmount[i]);
		}
		if(complete==false){//数量不满足需求
			alertMsg.error("库存数量不足。");
		}
		return complete;
		
	}
	var disResultMap = new Map();//Map<deptAppId,[{deptAppDetailId,[{{deptAppDetailId:deptAppDetailId,remark:remark,Map<batchId,amount>}}]}]>
	function updateRealDisData(subGridId,deptAppDetailId){
		var deptAppId = getMainRowIdBySubGridId(subGridId);
		var disDetailArray = disResultMap.get(deptAppId);
		if(disDetailArray==null){
			disDetailArray = new Array();
		}
		var disDetailMap;//主单明细数组的每一个元素是一个结构体{}Map<deptAppDetailId,batchArray>
		var disBatchArray = null;
		for(var i=0;i<disDetailArray.length;i++){
			disDetailMap = disDetailArray[i];
			disBatchArray = disDetailMap.get(deptAppDetailId);
			if(disBatchArray!=null){//update
				disDetailArray.splice(i,1);
				break;
			}
		}
		disBatchArray = new Array();//batchArray:[Map<batchId,{disAmount,disPrice}>]
		disDetailMap = new Map();
		var disBatchIds = jQuery("#deptAppDistributeDetail_gridtable").getDataIDs();
		for(var j=0;j<disBatchIds.length;j++){//batchRows
			var disBatchMap = new Map();
			var disBatchId = jQuery("#deptAppDistributeDetail_gridtable").jqGrid("getCell",disBatchIds[j],"batchId");
			var disAmount = jQuery("#deptAppDistributeDetail_gridtable").jqGrid("getCell",disBatchIds[j],"disAmount");
			var disPrice = jQuery("#deptAppDistributeDetail_gridtable").jqGrid("getCell",disBatchIds[j],"price");
			var disInfo = {
					'disAmount':disAmount,
					'disPrice':disPrice
			}
			disBatchMap.put(disBatchId,disInfo);
			disBatchArray.push(disBatchMap);
		}
		var throughAmount = jQuery("#"+subGridId).jqGrid("getCell",deptAppDetailId,"throughAmount");
		var remark = jQuery("#"+subGridId).jqGrid("getCell",deptAppDetailId,"remark");
		var deptAppDetail = {
				'throughAmount':throughAmount,
				'remark':remark,
				'batchDetail':disBatchArray
			}
		disDetailMap.put(deptAppDetailId,deptAppDetail);
		disDetailArray.push(disDetailMap);
		disResultMap.put(deptAppId,disDetailArray);
		
	}
	
	/*
		本次通过的处理逻辑
		状态为5(通过)、7(部分通过-其余未通过)、8(未通过)的单据不允许执行该业务
	*/
	function deptAppDetailPass(){
		if(disResultMap.size()==0){
			alertMsg.error("无通过数据");
			return;
		}
		var values = disResultMap.values();//Array:deptApp
		var sumThroughAmount = 0;
		for(var i=0;i<values.length;i++){
			var detailArray = values[i];//Array deptAppDetail
			for(var j=0;j<detailArray.length;j++){
				var detailMap = detailArray[j];
				var detailValues = detailMap.values();
				for(var k=0;k<detailValues.length;k++){
					var deptAppDetail = detailValues[k];//{}:
					var throuthAmount = deptAppDetail['throughAmount'];
					throuthAmount = convertStringToFloat(throuthAmount);
					sumThroughAmount += throuthAmount;
				} 
			}
		}
		var json =JSON.stringify(disResultMap);
		console.log(json);
		var url = "deptAppDetailPass?deptAppPassJson="+json+"&navTabId=deptAppDistribute_gridtable";
		url=encodeURI(url);
		alertMsg.confirm("确认本次通过(共通过数量："+sumThroughAmount+")？", {
			okCall : function() {
				$.post(url,function(data) {
					formCallBack(data);
					disResultMap.clear();//清空缓存
					//关闭layout.south
					subLayout.closeSouth();
				});
			}
		}); 
	}
	/*
		生成出库单、移库单、科室需求单
		勾选子记录，主记录是否勾选不影响
	*/
	 function createDocByDistribute(type){
		var mainRowIds = jQuery("#deptAppDistribute_gridtable").getDataIDs();
		var validMainId,validSubGridId,validSubGrids;
		for(var i=0,k=0;i<mainRowIds.length;i++){
			var subGridId = "#deptAppDistribute_gridtable_"+mainRowIds[i]+"_t";
			if(jQuery(subGridId).length>0){
				var subRowIds = jQuery(subGridId).jqGrid('getGridParam','selarrrow');
				if(subRowIds && subRowIds.length>0){//子表存在勾选记录
					k++;
					validMainId = mainRowIds[i];
					validSubGridId = subGridId;
					validSubGrids = subRowIds;
				}
			}
			if(k>=2){
				alertMsg.error("请选择同一张申领单下的明细记录！");
				return;
			}
		}
		if(!validSubGrids || validSubGrids.length==0){
			alertMsg.error("请选择记录！");
			return;
		}
		var docType,dialogId;
		if(type=="deptAppOut"){
			docType = "出库单";
			dialogId = "createInvOutByDistribute";
		}else if(type=="deptAppMove"){
			docType = "移库单";
			dialogId = "createInvMoveByDistribute";
		}else if(type=="deptAppNeed"){
			docType = "科室需求计划单";
			dialogId = "createDeptNeedPlanByDistribute";
		}
		//走ajax请求判断是否有可以生成的数据
		jQuery.ajax({
			type:'post',
			url:'${ctx}/beforeCreateByDis?deptAppDetailIds='+validSubGrids+'&deptAppId='+validMainId+'&docType='+type,
			dataType:'json',
			async:false,
			error:function(){
				
			},
			success:function(data){
		    	if(data.statusCode==200){
		    		var url = "";
		    		if(type=="deptAppNeed"){
			    		url = "createDeptNeedPlanByDistribute?docType="+docType+"&docPreview="+type+"&deptAppId="+validMainId+"&deptAppDetailIds="+validSubGrids;
		    		}else{
			    		url = "createInvOutByDistribute?docType="+docType+"&docPreview="+type+"&deptAppId="+validMainId+"&deptAppDetailIds="+validSubGrids;
		    		}
		    		url=encodeURI(url);
		    		alertMsg.confirm("确认生成"+docType+"？", {
		    			okCall : function() {
		    				var width = 960;
		    				if(docType=="移库单"){
		    					width = 972;
		    				}
		    				$.pdialog.open(url, dialogId, '生成'+docType, {mask:true,width:width,height:628});　
		    			}
		    		}); 
		    	}else{
		    		formCallBack(data);
		    	}
			}
		});
	} 
	
</script>
<div class="page">
<div id="deptAppDistribute_container">
	<div id="deptAppDistribute_layout-center" class="pane ui-layout-center">
		<div id="deptAppDistribute_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<form id="deptAppDistribute_search_form" >
						<label style="float:none;white-space:nowrap" >
							<s:text name='deptApp.store'/>:
							<input type="hidden" id="deptAppDistribute_search_store_id">
						 	<input type="text" id="deptAppDistribute_search_store" >
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='deptApp.appDept'/>:
							<input type="hidden" id="deptAppDistribute_search_appDept_id">
						 	<input type="text" id="deptAppDistribute_search_appDept" >
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='deptApp.appDate'/>:
							<input type="text"	id="deptAppDistribute_search_app_date_from" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'deptAppDistribute_search_app_date_to\')}'})">
							<s:text name='至'/>
						 	<input type="text"	id="deptAppDistribute_search_app_date_to" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodEndDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'deptAppDistribute_search_app_date_from\')}'})">
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='deptApp.confirmDate'/>:
							<input type="text"	id="deptAppDistribute_search_confirm_date_from" style="width:80px;height:15px" class="Wdate" value="" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'deptAppDistribute_search_confirm_date_to\')}'})">
							<s:text name='至'/>
						 	<input type="text"	id="deptAppDistribute_search_confirm_date_to" style="width:80px;height:15px" class="Wdate" value="" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'deptAppDistribute_search_confirm_date_from\')}'})">
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='deptApp.appState'/>:
							<s:select id="deptAppDistribute_search_appState"  list="#{'':'--','3':'未处理','5':'通过','6':'部分通过-其余待处理','7':'部分通过-其余未通过','8':'未通过'}" style="width:148px" ></s:select>
						</label>&nbsp;&nbsp;
						<%-- <div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="deptAppDistributeGridReload()"><s:text name='button.search'/></button>
							</div>
						</div> --%>
					</form>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="deptAppDistributeGridReload()"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="pageContent">
			<div class="panelBar">
				<ul class="toolBar">
					<li>
						<a class="confirmbutton" href="javaScript:deptAppDetailPass()" ><span><s:text name="本次通过" /></span></a>
					</li>
					<li>
						<a class="checkbutton" href="javaScript:deptAppDistributeListEditOper('end')" ><span><s:text name="结束申领" /></span></a>
					</li>
					<li>
						<a class="delallbutton" href="javaScript:deptAppDistributeListEditOper('back')" ><span><s:text name="退回科室" /></span></a>
					</li>
					<li>
						<a class="confirmbutton" href="javaScript:createDocByDistribute('deptAppOut')" ><span><s:text name="生成出库单" /></span></a>
					</li>
					<li>
						<a class="confirmbutton" href="javaScript:createDocByDistribute('deptAppMove')" ><span><s:text name="生成移库单" /></span></a>
					</li>
					<li>
						<a class="confirmbutton" href="javaScript:createDocByDistribute('deptAppNeed')" ><span><s:text name="生成科室需求计划单" /></span></a>
					</li>
					<li>
						<a class="settlebutton"  href="javaScript:setColShow('deptAppDistribute_gridtable','com.huge.ihos.material.deptapp.model.DeptAppDistribute')"><span><s:text name="button.setColShow" /></span></a>
					</li>
					<li>
						<a class="foldbutton" href="javaScript:foldOrUnfoldDeptAppDistribute('unfold')"><span><s:text name="button.unfold" /></span> </a>
					</li>
					<li>
						<a class="unfoldbutton" href="javaScript:foldOrUnfoldDeptAppDistribute('fold')"><span><s:text name="button.fold" /></span> </a>
					</li>
				</ul>
			</div>
			<div id="deptAppDistribute_gridtable_div" layoutH="120" style="margin-left: 2px; margin-top: 2px; overflow: hidden"
				buttonBar="optId:deptAppId;width:960;height:628">
				<input type="hidden" id="deptAppDistribute_gridtable_navTabId" value="${sessionScope.navTabId}">
				<div id="load_deptAppDistribute_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
	 			<table id="deptAppDistribute_gridtable"></table>
			</div>
		</div>
	
		<div class="panelBar">
			<div class="pages">
				<span><s:text name="pager.perPage" /></span> <select id="deptAppDistribute_gridtable_numPerPage">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="deptAppDistribute_gridtable_totals"></label><s:text name="pager.items" /></span>
			</div>
			<div id="deptAppDistribute_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20"
				pageNumShown="10" currentPage="1">
			</div>
		</div>
	</div>
	<div id="deptAppDistribute_layout-south" class="pane ui-layout-south" style="padding: 2px">
		<div class="panelBar">
			<ul class="toolBar">
				<li style="float: right;">
					<a id="deptAppDistribute_close" class="closebutton" href="javaScript:"><span><fmt:message key="button.close" /></span></a>
				</li>
				<li class="line" style="float: right">line</li>
				<li style="float: right;">
					<a id="deptAppDistribute_fold" class="foldbutton" href="javaScript:"><span><fmt:message key="button.fold" /></span></a>
				</li>
				<li class="line" style="float: right">line</li>
				<li style="float: right">
					<a id="deptAppDistribute_unfold" class="unfoldbutton" href="javaScript:"><span><fmt:message key="button.unfold" /></span></a>
				</li>
			</ul>
		</div>
		<div id="deptAppDistributeDetail"></div>
	</div>
</div>
</div>