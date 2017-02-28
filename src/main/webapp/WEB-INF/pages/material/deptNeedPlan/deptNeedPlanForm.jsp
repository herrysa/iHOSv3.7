<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script type="text/javascript">
function invDictDeptPlanComboGrid(elem) {
	var elemId = jQuery(elem).attr("id");
	elemId=elemId.replace(".","_");
	jQuery(elem).attr("id",elemId);
// 	jQuery("#${random}_deptNeedPlanTable").unlisten();
	$(elem).combogrid({
		url : '${ctx}/comboGridSqlList',
		queryParams : {
			sql : "select invId invId,invCode,invName,invModel,firstUnit,isNULL(refCost,0) refCost,"+
						"0 sumAmount,0 lastAmount , 0 lastCostAmount,0 storeAmount  "+
				  "from v_mm_invInstore "+
				  "where orgCode='"+"${fns:userContextParam('orgCode')}"+"' and copyCode='"+"${fns:userContextParam('copyCode')}"+"' " +
				  "and storeId in ('"+jQuery("#${random}_deptNeedPlan_store_id").val()+"','1')",
			cloumns : 'invName,invCode,cncode'
		},
		autoFocus : false,
		showOn : false, 
		rows:10,
		width:540,
		sidx:"INVCODE",
		colModel : [ {
			'columnName' : 'INVID',
			'width' : '0',
			'label' : 'INVID',
			hidden : true
		}, {
			'columnName' : 'INVCODE',
			'width' : '20',
			'align' : 'left',
			'label' : '材料编码'
		}, {
			'columnName' : 'INVNAME',
			'width' : '20',
			'align' : 'left',
			'label' : '材料名称'
		}, {
			'columnName' : 'INVMODEL',
			'width' : '20',
			'align' : 'left',
			'label' : '型号规格'
		}, {
			'columnName' : 'FIRSTUNIT',
			'width' : '20',
			'label' : '计量单位'
		}, {
			'columnName' : 'REFCOST',
			'width' : '20',
			'align' : 'right',
			'label' : '参考进价'
		}/*  , {
			'columnName' : 'SUMAMOUNT',
			'width' : '20',
			'align' : 'right',
			'label' : '本期已报计划数量',
			hidden:false
		}  , {
			'columnName' : 'LASTAMOUNT',
			'width' : '10',
			'align' : 'right',
			'label' : '上期计划量',
			hidden:false
		}, {
			'columnName' : 'LASTCOSTAMOUNT',
			'width' : '10',
			'align' : 'right',
			'label' : '上期耗用量',
			hidden:false
		}, {
			'columnName' : 'STOREAMOUNT',
			'width' : '0',
			'align' : 'right',
			'label' : '库存量',
			hidden:true
		}  */
		],
		_create: function( event, item ) {
		},
		focus: function( event, ui ) {
			//alert('aa');
		},
		select : function(event, ui) {
// 			jQuery("#${random}_deptNeedPlanTable").listen();
// 			$(elem).val(ui.item.INVNAME);
// 			var thisTr = jQuery("#${random}_deptNeedPlanTable").getTr($(elem));
// 			var rowid = thisTr.attr("id");
// 			var rowData = { invDict:
// 									{ invId   : ui.item.INVID,
// 									  invName : ui.item.INVNAME,
// 									  invCode : ui.item.INVCODE,
// 									  invModel: ui.item.INVMODEL,
// 									  firstUnit:ui.item.FIRSTUNIT
// 									},
// 							price : ui.item.REFCOST
// // 							sumAmount:ui.item.SUMAMOUNT,
// // 							lastAmount:ui.item.REFCOST,
// // 							lastCostAmount:ui.item.LASTCOSTAMOUNT,
// // 							storeAmount:ui.item.STOREAMOUNT
// 						};
// 				jQuery("#${random}_deptNeedPlanTable").rowChange(rowid,rowData);
			thisTr.find("input[name='invDict.invId']").val(ui.item.INVID).blur();
			thisTr.find("input[name='invDict.invName']").val(ui.item.INVNAME).blur();
			thisTr.find("input[name='invDict.invCode']").val(ui.item.INVCODE).blur();
			thisTr.find("input[name='invDict.invModel']").val(ui.item.INVMODEL).blur();
			thisTr.find("input[name='invDict.firstUnit']").val(ui.item.FIRSTUNIT).blur();
			thisTr.find("input[name='price']").val(ui.item.REFCOST).blur();
// 			thisTr.find("input[name='sumAmount']").val(ui.item.SUMAMOUNT).blur();
// 			thisTr.find("input[name='lastAmount']").val(ui.item.LASTAMOUNT).blur();
// 			thisTr.find("input[name='lastCostAmount']").val(ui.item.LASTCOSTAMOUNT).blur();
// 			thisTr.find("input[name='storeAmount']").val(ui.item.STOREAMOUNT).blur();
// 			thisTr.find("input[name='needDate']").val("${currentSystemVariable.businessDate}").blur();
// 			var invId = ui.item.INVID;
// 			var store_id = jQuery("#${random}_deptNeedPlan_store_id").val();
// 			var dept_id = jQuery("#${random}_deptNeedPlan_dept_id").val();
// 			var periodMonth = "${deptNeedPlan.periodMonth}";
// 			var orgCode = "${deptNeedPlan.orgCode}";
// 		    var copyCode = "${deptNeedPlan.copyCode}";
// 		    jQuery.ajax({
// 			       url: 'getLastAndStoreAmount',
// 			       data: {invId:invId,store_id:store_id,dept_id:dept_id,periodMonth:periodMonth,orgCode:orgCode,copyCode:copyCode},
// 			       type: 'post',
// 			       dataType: 'json',
// 			       async:false,
// 			       error: function(data){
// 			       },
// 			       success: function(data){
// 			        	 thisTr.find("input[name='lastAmount']").val(data.amounts.lastAmount).blur();
// 			        	 thisTr.find("input[name='sumAmount']").val(data.amounts.sumAmount).blur();
// 			        	 thisTr.find("input[name='storeAmount']").val(data.amounts.storeAmount).blur();
// 			       }
// 			   });
			thisTr.attr("used","true");			
			return false;
		}
	});
} 
</script>
<script>
	var dnpdt = docTemplate("${docTemp.listMap}","${docTemp.title}","${fn:length(docTemp.inputNames)}","${fn:length(docTemp.footNames)}");
	jQuery(document).ready(function() {
		dnpdt.setDocLayOut("${random}_deptNeedPlanForm","${random}_deptNeedPlanTable_div","${random}_deptNeedPlanForm_foot");
		if("${docPreview}"=="preview"){
			dnpdt.disableButton("${random}_deptNeedPlan_toolBar");
			dnpdt.clearData("${random}_deptNeedPlanCard");
		}
		var needId = "${deptNeedPlan.needId}";
		if(needId==null || needId==""){
			needId = "new";
		}
		var url = "deptNeedPlanDetailGridList?filter_EQS_deptNeedPlan.needId="+needId;
		var saveUrl = "${ctx}/saveDeptNeedPlan?navTabId=deptNeedPlan_gridtable&entityIsNew=${entityIsNew}&saveType=saveStay";
		if("${docPreview}"=="deptAppNeed"){
			url += "&docPreview=deptAppNeed&deptAppDetailIds=${deptAppDetailIds}&deptAppId=${deptAppId}";
			saveUrl += "&saveFrom=deptAppNeed&deptAppDetailIds=${deptAppDetailIds}";
		}
		var zhGridSetting_deptNeedPlan = {
			url : url,
			datatype : "json",
			mtype : "GET",
			trHeight:25,
			formId:'${random}_deptNeedPlanForm',
			paramName:'deptNeedPlanDetailJson',
			saveUrl:saveUrl,
			initColumn:"com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail",
			afterSaveFun : function(data) {
				if("saveStay" == data.callbackType){
					$.pdialog.closeCurrent();
					$.pdialog.open("${ctx}/editDeptNeedPlan?needId="+data.forwardUrl,"editDeptNeedPlan","科室需求计划单明细", {mask:true,width:width,height:height});
				}
			},
			colModel:[
				{name:'needDetailId',index:'needDetailId',align:'center',label : '<s:text name="deptNeedPlanDetail.needDetailId" />',hidden:true,key:true,editable : false,sortable:false},	
				{name:'invDict.invId',index:'invDict.invId',align:'center',label : '<s:text name="inventoryDict.invId" />',hidden:true,sortable:false},	
				{name:'invDict.invName',index:'invDict.invName',align:'left',width:150,label : dnpdt.getListName("invDict.invName"),hidden:!(dnpdt.containColumn("invDict.invName")),highsearch:dnpdt.containColumn("invDict.invName"),editable : true,edittype : 'text',editrules:{required:true},editoptions : {dataInit : invDictDeptPlanComboGrid}},	
				{name:'invDict.invCode',index:'invDict.invCode',align:'left',width:80,label : dnpdt.getListName("invDict.invCode"),hidden:!(dnpdt.containColumn("invDict.invCode")),highsearch:dnpdt.containColumn("invDict.invCode")},	
				{name:'invDict.invModel',index:'invDict.invModel',align:'left',width:120,label : dnpdt.getListName("invDict.invModel"),hidden:!(dnpdt.containColumn("invDict.invModel")),highsearch:dnpdt.containColumn("invDict.invModel")},	
				{name:'invDict.firstUnit',index:'invDict.firstUnit',align:'center',width:80,label : dnpdt.getListName("invDict.firstUnit"),hidden:!(dnpdt.containColumn("invDict.firstUnit")),highsearch:dnpdt.containColumn("invDict.firstUnit")},	
				{name:'lastAmount',index:'deptNeedPlanDetail.lastAmount',align:'right',width:100,label : dnpdt.getListName("lastAmount"),hidden:!(dnpdt.containColumn("lastAmount")),highsearch:dnpdt.containColumn("lastAmount"),editable : false,formatter : 'number',sortable:false},	
				{name:'lastCostAmount',index:'deptNeedPlanDetail.lastCostAmount',align:'right',width:100,label : dnpdt.getListName("lastCostAmount"),hidden:!(dnpdt.containColumn("lastCostAmount")),highsearch:dnpdt.containColumn("lastCostAmount"),editable : false,formatter : 'number',sortable:false},	
				{name:'sumAmount',index:'sumAmount',align:'right',width:100,label : dnpdt.getListName("sumAmount"),hidden:!(dnpdt.containColumn("sumAmount")),highsearch:dnpdt.containColumn("sumAmount"),formatter : 'number'},	
				{name:'amount',index:'amount',align:'right',width:100,label : dnpdt.getListName("amount"),hidden:!(dnpdt.containColumn("amount")),highsearch:dnpdt.containColumn("amount"),editable : true,editrules:{required:true},formatter : 'number'},	
				{name:'price',index:'price',align:'right',width:100,label : dnpdt.getListName("price"),hidden:!(dnpdt.containColumn("price")),highsearch:dnpdt.containColumn("price"),formatter : 'number'},	
				{name:'money',index:'money',align:'right',width:110,label : dnpdt.getListName("money"),hidden:!(dnpdt.containColumn("money")),highsearch:dnpdt.containColumn("money"),formatter : 'currency',sortable:false},	
				{name:'needDate',index:'needDate',align:'center',width:80,label : dnpdt.getListName("needDate"),hidden:!(dnpdt.containColumn("needDate")),highsearch:dnpdt.containColumn("needDate"),editable:true,editrules:{required:true},formatter : 'date',formatoptions: {srcformat: 'Y-m-d H:i:s',newformat: 'Y-m-d'},
					editoptions : {dataInit :function(elem){jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});jQuery('#ui-datepicker-div').css("z-index", 2000);}}
				},	
				{name:'remark',index:'remark',align:'left',width:150,label : dnpdt.getListName("remark"),hidden:!(dnpdt.containColumn("remark")),highsearch:dnpdt.containColumn("remark"),editable : true},
				{name:'storeAmount',index:'deptNeedPlanDetail.storeAmount',align:'right',width:100,label : dnpdt.getListName("storeAmount"),hidden:!(dnpdt.containColumn("storeAmount")),highsearch:dnpdt.containColumn("storeAmount"),editable : false,formatter : 'number'}
			  ],
		  	jsonReader : {
				root : "deptNeedPlanDetails", // (2)
				//page : "page",
				//total : "total",
				//records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'invDict.invCode',
	        viewrecords: true,
	        sortorder: 'desc',
	        height:300,
	        width:100,
	        gridview:true,
	        rowNum:500,
	        rownumbers:true,
	        loadui: "disable",
	        multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
			cellEdit : true,
			cellsubmit : 'clientArray',
	        onSelectRow: function(rowid) {
	       	},
	       	gridComplete:function(){
	       		jQuery("#${random}_deptNeedPlanTable").footData([
  				       {name:'invDict.invName',value:'合计:'},
  				       {name:'amount',value:'sum'},
  				       {name:'money',value:'sum'}
   				 ]);
	       	},
	       	beforeEditCell : function(rowid, cellname,value, iRow, iCol) {
	       		var store_id = jQuery("#${random}_deptNeedPlan_store_id").val();
				if(store_id == '' || store_id=='null'){
					alertMsg.error("请选择目标仓库。");
					return false;
				}else{
					return true;
				}
			},
			afterEditCell : function(rowid, cellname,value, iRow, iCol) {
				if(cellname=='amount'){
					var amount= jQuery("#${random}_deptNeedPlanTable").getCellData(rowid,'amount');
					var price= jQuery("#${random}_deptNeedPlanTable").getCellData(rowid,'price');
					amount = isNaN(parseFloat(amount))?0:parseFloat(amount);
					price = isNaN(parseFloat(price))?0:parseFloat(price);
	                /* jQuery("#${random}_deptNeedPlanTable").setCellData(rowid,'sumAmount',0);
	                jQuery("#${random}_deptNeedPlanTable").setCellData(rowid,'lastAmount',0);
	                jQuery("#${random}_deptNeedPlanTable").setCellData(rowid,'lastCostAmount',0); */
	                jQuery("#${random}_deptNeedPlanTable").setCellData(rowid,'money',amount*price);	             
				}if(cellname=='invDict.invName'){
					var invId = jQuery("#${random}_deptNeedPlanTable").getCellData(rowid,'invDict.invId');
					var needDate = jQuery("#${random}_deptNeedPlanTable").getCellData(rowid,'needDate');
					alert(invId);
					if(invId){
					var store_id = jQuery("#${random}_deptNeedPlan_store_id").val();
					var dept_id = jQuery("#${random}_deptNeedPlan_dept_id").val();
					var periodMonth = "${deptNeedPlan.periodMonth}";
					var orgCode = "${deptNeedPlan.orgCode}";
				    var copyCode = "${deptNeedPlan.copyCode}";
				    jQuery.ajax({
					       url: 'getLastAndStoreAmount',
					       data: {invId:invId,store_id:store_id,dept_id:dept_id,periodMonth:periodMonth,orgCode:orgCode,copyCode:copyCode},
					       type: 'post',
					       dataType: 'json',
					       async:false,
					       error: function(data){
					       },
					       success: function(data){
					    	   jQuery("#${random}_deptNeedPlanTable").setCellData(rowid,'lastAmount',data.amounts.lastAmount);	
					    	   jQuery("#${random}_deptNeedPlanTable").setCellData(rowid,'sumAmount',data.amounts.sumAmount);	
					    	   jQuery("#${random}_deptNeedPlanTable").setCellData(rowid,'storeAmount',data.amounts.storeAmount);
					    	   jQuery("#${random}_deptNeedPlanTable").setCellData(rowid,'lastCostAmount',data.amounts.lastCostAmount);
					    	   if(needDate){   
					    	   }else{
					    		   jQuery("#${random}_deptNeedPlanTable").setCellData(rowid,'needDate',"${currentSystemVariable.businessDate}");
					    	   }			    	   
					       }
					   });
					}
				}
			},
			afterSaveCell : function(rowid, cellname,value, iRow, iCol) {
			}
		}
		jQuery("#${random}_deptNeedPlanTable").zhGrid(zhGridSetting_deptNeedPlan);
		if("${docPreview}"!="preview"){//预览单据
			if("${docPreview}"=="refer"){//从别处通过超链接查看单据
				disableLink(["${random}_deptNeedPlan_new","${random}_deptNeedPlan_importDict","${random}_deptNeedPlan_save","${random}_deptNeedPlan_check","${random}_deptNeedPlan_cancelCheck","${random}_deptNeedPlan_stop","${random}_deptNeedPlan_abandon","${random}_deptNeedPlan_print","${random}_deptNeedPlan_printSet"]);
				clearInputClassInDeptNeedPlan();
			}else{
				var state = "${deptNeedPlan.state}";
				if(${entityIsNew}){
					disableLink(["${random}_deptNeedPlan_check","${random}_deptNeedPlan_stop","${random}_deptNeedPlan_abandon"]);
				}else{
					jQuery("#${random}_deptNeedPlan_planType").removeClass('textInput').attr('readOnly',"true");
				}
				if(state!=0){//审核、作废、中止
					disableLink(["${random}_deptNeedPlan_save","${random}_deptNeedPlan_check","${random}_deptNeedPlan_print","${random}_deptNeedPlan_printSet","${random}_deptNeedPlan_importDict"]);
					if(state==1){
						disableLink(["${random}_deptNeedPlan_stop"]);
					}else if(state==2){
						disableLink([["${random}_deptNeedPlan_stop"],"${random}_deptNeedPlan_cancelCheck"]);
					}else if(state==3){//中止
						disableLink(["${random}_deptNeedPlan_stop","${random}_deptNeedPlan_cancelCheck"]);
					}else if(state==4){//作废
						disableLink(["${random}_deptNeedPlan_stop","${random}_deptNeedPlan_cancelCheck","${random}_deptNeedPlan_abandon"]);
					}
					clearInputClassInDeptNeedPlan();
				}else if(state==0){//新建
					jQuery("#${random}_deptNeedPlanTable").fullEdit();
					disableLink(["${random}_deptNeedPlan_new","${random}_deptNeedPlan_cancelCheck","${random}_deptNeedPlan_print","${random}_deptNeedPlan_printSet"]);
					jQuery("#${random}_deptNeedPlan_store").treeselect({
						dataType : "sql",
						optType : "single",
						sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
						exceptnullparent : false,
						lazy : false
					}); 
				    jQuery("#${random}_deptNeedPlan_dept").treeselect({
						dataType:"sql",
						optType:"single",
						sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
						exceptnullparent:false,
						lazy:false
					});
				    jQuery('#${random}_deptNeedPlan_makeDate').unbind( 'click' ).bind("click",function(){
				    	WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:"${fns:userContextParam('periodBeginDateStr')}",maxDate:"${fns:userContextParam('periodEndDateStr')}"});
				    });
				}	
			}
		}
	});
	function clearInputClassInDeptNeedPlan(){
		jQuery("#${random}_deptNeedPlan_planType").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_deptNeedPlan_store").removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_deptNeedPlan_dept").removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_deptNeedPlan_makeDate").removeClass('Wdate').removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_deptNeedPlan_remark").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
	}
</script>
<script type="text/javascript">
	function newDeptNeedPlan(){
		$.pdialog.closeCurrent();
		$.pdialog.open('${ctx}/editDeptNeedPlan','addDeptNeedPlan',"添加科室需求计划单", {mask:true,width : width,height : height,resizable:false});
	}
	function importDictToDeptNeedPlan(){
		var store_id = jQuery("#${random}_deptNeedPlan_store_id").val();
		if(store_id == '' || store_id=='null'){
			alertMsg.error("请选择仓库。");
			return;
		} 
		var url = "${ctx}/invInStoreList?popup=true&storeId="+store_id+"&fromType=deptNeedPlan&random=${random}";
		var winTitle="<s:text name='材料选择'/>";
		url = encodeURI(url);
		$.pdialog.open(url, 'importDictToDeptNeedPlan', winTitle, {mask:true,width:width,height:height,resizable:false,maxable:false});
	}
	function saveDeptNeedPlan(){
		jQuery("#${random}_deptNeedPlanTable").saveGrid();
	}
	function auditDeptNeedPlan(){
		var gridEdited = jQuery("#${random}_deptNeedPlanTable")[0].p.gridEdited;
		if(gridEdited){
			alertMsg.error("单据内容有修改，请先保存！");
			return ;
			
		}
		var editUrl = "deptNeedPlanGridEdit?id=${deptNeedPlan.needId}&navTabId=deptNeedPlan_gridtable&oper=check";
		alertMsg.confirm("确认审核？", {
			okCall : function() {
				$.post(editUrl,function(data) {
					$.pdialog.reload("${ctx}/editDeptNeedPlan?popup=true&needId=${deptNeedPlan.needId}",{dialogId:'editDeptNeedPlan'});
					formCallBack(data);
				});
			}
		});
	}
	function antiAuditDeptNeedPlan(){
		var editUrl = "deptNeedPlanGridEdit?id=${deptNeedPlan.needId}&navTabId=deptNeedPlan_gridtable&oper=cancelCheck";
		alertMsg.confirm("确认销审？", {
			okCall : function() {
				$.post(editUrl,function(data) {
					$.pdialog.reload("${ctx}/editDeptNeedPlan?popup=true&needId=${deptNeedPlan.needId}",{dialogId:'editDeptNeedPlan'});
					formCallBack(data);
				});
			}
		});
	}
	function abandonOrStopDeptNeedPlan(oper){
		var editUrl = "deptNeedPlanGridEdit?id=${deptNeedPlan.needId}&navTabId=deptNeedPlan_gridtable&oper="+oper;
		alertMsg.confirm("确认"+(oper=="abandon"?"作废":"中止")+"计划？", {
			okCall : function() {
				$.post(editUrl,function(data) {
					$.pdialog.closeCurrent();
					formCallBack(data);
				});
			}
		});
	}
	
	function printDeptNeedPlan(){
		/* var deptNeedPlanCardHtml = jQuery("#deptNeedPlanCard").html();
		jQuery("body").append("<div id='page1'>"+deptNeedPlanCardHtml+"</div>");
		jQuery("#deptNeedPlanTable_head",'#page1').css('width','99%');
		jQuery("#deptNeedPlanTable",'#page1').css('width','99%');
		jQuery("#deptNeedPlanTable_foot",'#page1').css('width','99%');
		jQuery("#deptNeedPlanTable_div",'#page1').css('width','auto');
		jQuery("#deptNeedPlanTable_div",'#page1').css('height','auto');
		return;
		doPrint(); */
	}
	function closeDeptNeedPlan(){
		var gridEdited = jQuery("#${random}_deptNeedPlanTable")[0].p.gridEdited;
		if(gridEdited){
			alertMsg.confirm("单据内容有修改，是否继续关闭？", {
				okCall : function() {
					$.pdialog.closeCurrent();
					return true;
				}
			});
		}else{
			$.pdialog.closeCurrent();
		}
	}
	function showData(){
		var dataSource = jQuery("#${random}_deptNeedPlanTable")[0].grid.dataSource;
		console.log(json2str(dataSource));
	}
	function render(){
		jQuery("#${random}_deptNeedPlanTable").render();
	}
	function showActive(){
		alert(json2str(jQuery("#${random}_deptNeedPlanTable")[0].p.activeCell));
	}
</script>

</head>

<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar" id="${random}_deptNeedPlan_toolBar">
				<li>
					<a id="${random}_deptNeedPlan_new" class="addbutton" href="javaScript:newDeptNeedPlan()" ><span><s:text name="button.newDoc" /></span></a>
				</li>
				<li>
					<a id="${random}_deptNeedPlan_importDict" class="getdatabutton" href="javaScript:importDictToDeptNeedPlan()"><span>选择材料</span></a>
				</li>
				<li>
					<a id="${random}_deptNeedPlan_save" class="savebutton" href="javaScript:saveDeptNeedPlan()"><span><s:text name="button.save" /></span> </a>
				</li>
				<li>
					<a id="${random}_deptNeedPlan_check" class="checkbutton"  href="javaScript:auditDeptNeedPlan()"><span><s:text name="button.check" /></span></a>
				</li>
				<li>
					<a id="${random}_deptNeedPlan_cancelCheck" class="delallbutton"  href="javaScript:antiAuditDeptNeedPlan()"><span><s:text name="button.cancelCheck" /></span></a>
				</li>
				<li>
					<a id="${random}_deptNeedPlan_abandon" class="disablebutton"  href="javaScript:abandonOrStopDeptNeedPlan('abandon')" style="display: none"><span><s:text name="button.abandon" /></span></a>
				</li>
				<li>
					<a id="${random}_deptNeedPlan_stop" class="confirmbutton"  href="javaScript:abandonOrStopDeptNeedPlan('stop')"><span><s:text name="中止计划" /></span></a>
				</li>
				<li>
					<a id="${random}_deptNeedPlan_print" class="printbutton" href="javaScript:printDeptNeedPlan()"><span><s:text name="button.print" /></span> </a>
				</li>
				<li>
					<a id="${random}_deptNeedPlan_printSet" class="settingbutton" href="javaScript:printSetDeptNeedPlan()"><span><s:text name="button.printSet" /></span> </a>
				</li>
				<li>
					<a id="${random}_deptNeedPlan_setColShow" class="settlebutton"  href="javaScript:setColShow('${random}_deptNeedPlanTable','com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail','zhGrid')"><span><s:text name="button.setColShow" /></span></a>
				</li>
				<li>
					<a id="${random}_deptNeedPlan_close" class="closebutton" href="javaScript:closeDeptNeedPlan()"><span><s:text name="button.close" /></span> </a>
				</li>
				<li>
					<a class="closebutton" href="javaScript:showData()" style="display:none"><span><s:text name="button.close" /></span> </a>
				</li>
				<li>
					<a class="closebutton" href="javaScript:render()" style="display:none"><span><s:text name="button.close" /></span> </a>
				</li>
				<li>
					<a class="closebutton" href="javaScript:showActive()" style="display:none"><span><s:text name="button.close" /></span> </a>
				</li>
			</ul>
		</div>
		<div id="${random}_deptNeedPlanCard" autoBreakPage='*' style="margin:5px 10px 5px 5px">
			<form id="${random}_deptNeedPlanForm">
				<s:if test="%{!entityIsNew}">
					<span style="position:absolute;right:14px;font-size:18px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px">No.${deptNeedPlan.needNo}</span>
				</s:if>
				<div align="center" style="font-size:25px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px;margin:0 auto;">
					${docTemp.title}
					<hr style="color:rgb(24, 127, 207);margin-top:0px"/>
					<hr style="color:rgb(24, 127, 207);margin-top:-11px"/>
				</div>
				<div>
					<s:hidden key="deptNeedPlan.needId"/>
					<s:hidden key="deptNeedPlan.needNo"/>
					<s:hidden key="deptNeedPlan.orgCode"/>
					<s:hidden key="deptNeedPlan.copyCode"/>
					<s:hidden key="deptNeedPlan.periodMonth"/>
					<s:hidden key="deptNeedPlan.state"/>
					<s:hidden key="deptNeedPlan.docTemId"/>
					<s:hidden key="deptNeedPlan.makePerson.personId"/>
				</div>	
				<div style="border:0;width:100%;table-layout:fixed;margin-top:-10px;">
					<c:forEach items="${docTemp.inputList}" var="input">
						<span class="docInputArea" id="${random}_${input.value}_${input.necessary}">&nbsp;&nbsp;&nbsp;
							<c:set var="inputType" value="${input.type}" scope="page"/> 
							<c:set var="inputKey" value="${input.value}" scope="page"/>
							<c:set var="referId" value="${input.referId}" scope="page"/>
							<c:set var="referName" value="${input.referName}" scope="page"/>
							<c:set var="entityIsNew" value="${entityIsNew}" scope="page"/>
							<c:if test="${inputKey!='planType'}">
								<label><c:out value="${input.name}"/>:</label>
							</c:if>
							<c:if test="${inputType=='refer'}" >
								<input type="text" id="${random}_deptNeedPlan_${inputKey}" name="deptNeedPlan.${inputKey}.${referName}" value="${deptNeedPlan[inputKey][referName]}" />
								<input type="hidden" id="${random}_deptNeedPlan_${inputKey}_id" name="deptNeedPlan.${inputKey}.${referId}" value="${deptNeedPlan[inputKey][referId]}"/>
							</c:if>
							<c:if test="${inputType=='date'}" >
								<input type="text" id="${random}_deptNeedPlan_${inputKey}" name="deptNeedPlan.${inputKey}" value="<fmt:formatDate value='${deptNeedPlan[inputKey]}' pattern='yyyy-MM-dd' />" class="Wdate"  style="height:15px;width:80px"/>
							</c:if>
							<c:if test="${inputType=='select'}" >
								<s:if test="%{deptNeedPlan.state==0}">
								<c:if test="${entityIsNew}">
									<s:select key="deptNeedPlan.planType"  name="deptNeedPlan.planType" list="#{'1':'月计划','2':'追加计划'}" style="width:90px" ></s:select>
								</c:if>
									<c:if test="${!entityIsNew}">
									<label><c:out value="${input.name}"/>:</label>
									<input type="hidden" id="${random}_deptNeedPlan_${inputKey}_id" name="deptNeedPlan.${inputKey}" value="${deptNeedPlan[inputKey]}" />
									<input type="text" id="${random}_deptNeedPlan_${inputKey}" name="${inputKey}display" value="${deptNeedPlan[inputKey]==1?'月计划':'追加计划'}" style="width:90px"/>
									</c:if>							
								</s:if>
								<s:else>
									<label><c:out value="${input.name}"/>:</label>
									<input type="text" id="${random}_deptNeedPlan_${inputKey}" name="deptNeedPlan.${inputKey}" value="${deptNeedPlan[inputKey]==1?'月计划':'追加计划'}" />
								</s:else>
							</c:if>
							<c:if test="${inputType=='simple'}" >
								<input type="text" id="${random}_deptNeedPlan_${inputKey}" name="deptNeedPlan.${inputKey}" value="${deptNeedPlan[inputKey]}" />
							</c:if>
							<script>
								if("${deptNeedPlan.state}"=="0" && ${input.necessary}){
									jQuery("#${random}_${input.value}_${input.necessary} input[type='text']").addClass("required");
								}
							</script>
						</span>
					</c:forEach>
				</div>
			</form>
			<div id="${random}_deptNeedPlanTable_div" class="zhGrid_div" layoutH="140">
				<table id="${random}_deptNeedPlanTable"></table>
			</div>
			<div style="height:26px" id="${random}_deptNeedPlanForm_foot">
				<ul style="float:left;width:100%;height:20px;margin-top:8px;text-align:center">
					<c:forEach items="${docTemp.footMap}" var="foot">
						<li style="float:left;">
							<label><c:out value="${foot.value}"/>:</label>
							<c:if test="${foot.key!='sign'}">
									<c:set var="footKey" value="${foot.key}" scope="page"/>
									<input class="lineInput" size="8" value="${deptNeedPlan[footKey].name}" readonly="readonly"/>
							</c:if>
							<c:if test="${foot.key=='sign'}">
								<input class="lineInput" size="8" value="" readonly="readonly"/>
							</c:if>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</div>
