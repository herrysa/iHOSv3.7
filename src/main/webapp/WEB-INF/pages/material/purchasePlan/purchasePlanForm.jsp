<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	function invDictPurchasePlanComboGrid(elem) {
		var elemId = jQuery(elem).attr("id");
		elemId=elemId.replace(".","_");
		jQuery(elem).attr("id",elemId);
// 		jQuery("#${random}_purchasePlanTable").unlisten();
		$(elem).combogrid({
			url : '${ctx}/comboGridSqlList',
			queryParams : {
				sql : "select invId,invCode,invName,invModel,firstUnit,isNULL(refCost,0) refCost,factory,0 curStock,0 storeStock,0 needAmount "+
					  "from v_mm_invInstore where storeId in ('"+jQuery("#${random}_purchasePlan_store_id").val()+"','1') and orgCode='"+"${fns:userContextParam('orgCode')}"+"' and copyCode='"+"${fns:userContextParam('copyCode')}"+"'",
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
			}, {
				'columnName' : 'CURSTOCK',
				'width' : '0',
				'align' : 'right',
				'label' : '当前库存',
				hidden:true
			}, {
				'columnName' : 'STORESTOCK',
				'width' : '0',
				'align' : 'right',
				'label' : '对应仓库库存',
				hidden:true
			}, {
				'columnName' : 'NEEDAMOUNT',
				'width' : '0',
				'align' : 'right',
				'label' : '科室需求数量',
				hidden:true
			}, {
				'columnName' : 'FACTORY',
				'width' : '0',
				'align' : 'left',
				'label' : '生产厂商',
				hidden:true
			}
			],
			_create: function( event, item ) {
				//alert();
			},
			select : function(event, ui) {
				//$(elem).attr("value",ui.item.INVNAME);
// 			jQuery("#${random}_purchasePlanTable").listen();
// 			$(elem).val(ui.item.INVNAME);
// 			var thisTr = jQuery("#${random}_purchasePlanTable").getTr($(elem));
// 			var rowid = thisTr.attr("id");
// 			var rowData = { invDict:
// 									{ invId   : ui.item.INVID,
// 									  invName : ui.item.INVNAME,
// 									  invCode : ui.item.INVCODE,
// 									  invModel: ui.item.INVMODEL,
// 									  firstUnit:ui.item.FIRSTUNIT,
// 									  factory:ui.item.FACTORY
// 									},
// 							price : ui.item.REFCOST,
// 						};
				
				
				var thisTr = jQuery("#${random}_purchasePlanTable").getTr($(elem));
				var rowid = thisTr.attr("id");
				thisTr.find("input[name='invDict.invId']").val(ui.item.INVID).blur();
				thisTr.find("input[name='invDict.invName']").val(ui.item.INVNAME).blur();
				thisTr.find("input[name='invDict.invCode']").val(ui.item.INVCODE).blur();
				thisTr.find("input[name='invDict.invModel']").val(ui.item.INVMODEL).blur();
				thisTr.find("input[name='invDict.firstUnit']").val(ui.item.FIRSTUNIT).blur();
				thisTr.find("input[name='invDict.factory']").val(ui.item.FACTORY).blur();
				thisTr.find("input[name='price']").val(ui.item.REFCOST).blur();
// 				thisTr.find("input[name='curStock']").val(ui.item.CURSTOCK).blur();
// 				thisTr.find("input[name='storeStock']").val(ui.item.STORESTOCK).blur();
// 				thisTr.find("input[name='needAmount']").val(ui.item.NEEDAMOUNT).blur();
// 				thisTr.find("input[name='arrivalDate']").val("${currentSystemVariable.businessDate}").blur();
// 				var invId = ui.item.INVID;
// 				var store_id = jQuery("#${random}_purchasePlan_store_id").val();
// 				var periodMonth = "${purchasePlan.periodMonth}";
// 				var orgCode = "${purchasePlan.orgCode}";
// 			    var copyCode = "${purchasePlan.copyCode}";
// 			    jQuery.ajax({
// 				       url: 'getStoreAndNeedAmount',
// 				       data: {invId:invId,store_id:store_id,periodMonth:periodMonth,orgCode:orgCode,copyCode:copyCode},
// 				       type: 'post',
// 				       dataType: 'json',
// 				       async:false,
// 				       error: function(data){
// 				       },
// 				       success: function(data){
// 				        	 thisTr.find("input[name='curStock']").val(data.amounts.curStock).blur();
// 				        	 thisTr.find("input[name='storeStock']").val(data.amounts.storeStock).blur();
// 				        	 thisTr.find("input[name='needAmount']").val(data.amounts.needAmount).blur(); 
// 				       }
// 				   });
				 thisTr.attr("used","true"); 
// 				jQuery("#${random}_purchasePlanTable").rowChange(rowid,rowData);
				return false;
			}
		});
	}
	function invDictVendorNameComboGrid(elem) {
		var elemId = jQuery(elem).attr("id");
		elemId=elemId.replace(".","_");
		jQuery(elem).attr("id",elemId);
// 		jQuery("#${random}_purchasePlanTable").unlisten();
		$(elem).combogrid({
			url : '${ctx}/comboGridSqlList',
			queryParams : {
				sql : "select vendorId,vendorCode,vendorName,city,prov from sy_vendor where disabled = 0 and isMate = 1 ",
				cloumns : 'vendorName,city,prov,vendorCode'
			},
			autoFocus : false,
			showOn : false, 
			rows:10,
			width:540,
			sidx:"VENDORCODE",
			colModel : [ {
				'columnName' : 'VENDORID',
				'width' : '0',
				'label' : 'VENDORID',
				hidden : true
			}, {
				'columnName' : 'VENDORCODE',
				'width' : '15',
				'align' : 'left',
				'label' : '编号'
			} ,{
				'columnName' : 'VENDORNAME',
				'width' : '40',
				'align' : 'left',
				'label' : '供应商名称'
			}, {
				'columnName' : 'CITY',
				'width' : '20',
				'align' : 'left',
				'label' : '所在市'
			}, {
				'columnName' : 'PROV',
				'width' : '20',
				'align' : 'left',
				'label' : '所在省'
			}
			],
			_create: function( event, item ) {
				//alert();
			},
			select : function(event, ui) {
				//$(elem).attr("value",ui.item.INVNAME);
// 				   jQuery("#${random}_purchasePlanTable").listen();
//   		     	   $(elem).val(ui.item.INVNAME);
//    				   var thisTr = jQuery("#${random}_purchasePlanTable").getTr($(elem));
//                    var rowid = thisTr.attr("id");
//                    var rowData = { vendor: 
//                    							{ vendorId : ui.item.VENDORID,
//                 	                          vendorCode : ui.item.VENDORCODE,
//                 	                          vendorName : ui.item.VENDORNAME,
//                                               invModel: ui.item.INVMODEL,
//                                               city:ui.item.CITY,
//                                               prov:ui.item.PROV
//                                              },
//                                  }; 
//                    jQuery("#${random}_purchasePlanTable").rowChange(rowid,rowData);
//                    return false;

				
				var thisTr = jQuery("#${random}_purchasePlanTable").getTr($(elem));
				var rowid = thisTr.attr("id");
				thisTr.find("input[name='vendor.vendorId']").val(ui.item.VENDORID).blur();
				thisTr.find("input[name='vendor.vendorCode']").val(ui.item.VENDORCODE).blur();
				thisTr.find("input[name='vendor.vendorName']").val(ui.item.VENDORNAME).blur();
				thisTr.find("input[name='vendor.city']").val(ui.item.CITY).blur();
				thisTr.find("input[name='vendor.prov']").val(ui.item.PROV).blur();
				thisTr.attr("used","true");
				return false;
			}
		});
	}
	function invDictPurchaserComboGrid(elem) {
		var elemId = jQuery(elem).attr("id");
		elemId=elemId.replace(".","—");
		jQuery(elem).attr("id",elemId);
// 		jQuery("#${random}_purchasePlanTable").unlisten();
		$(elem).combogrid({
			url : '${ctx}/comboGridSqlList',
			queryParams : {
				sql : "select personId,personCode,name,sex,isNULL(duty,'') duty,isNULL(jobTitle,'') jobTitle,isNULL(postType,'') postType,isNULL(empType,'') empType from t_person where disabled = 0 and dept_id in ('"+jQuery("#${random}_purchasePlan_dept_id").val()+"','1') ",
				cloumns : 'personCode,name,sex,duty,jobTitle,postType,empType'
			},
			autoFocus : false,
			showOn : false, 
			rows:10,
			width:540,
			sidx:"PERSONCODE",
			colModel : [ {
				'columnName' : 'PERSONID',
				'width' : '0',
				'label' : 'PERSONID',
				hidden : true
			}, {
				'columnName' : 'PERSONCODE',
				'width' : '20',
				'align' : 'left',
				'label' : '人员编码'
			}, {
				'columnName' : 'NAME',
				'width' : '10',
				'align' : 'left',
				'label' : '姓名'
			}, {
				'columnName' : 'SEX',
				'width' : '10',
				'align' : 'center',
				'label' : '性别'
			}, {
				'columnName' : 'DUTY',
				'width' : '10',
				'align' : 'left',
				'label' : '职务'
			}, {
				'columnName' : 'JOBTITLE',
				'width' : '10',
				'align' : 'left',
				'label' : '职称'
			}, {
				'columnName' : 'POSTTYPE',
				'width' : '10',
				'align' : 'left',
				'label' : '岗位'
			}, {
				'columnName' : 'EMPTYPE',
				'width' : '10',
				'align' : 'left',
				'label' : '职工类别'
			}
			],
			_create: function( event, item ) {
				//alert();
			},
			select : function(event, ui) {
				//$(elem).attr("value",ui.item.INVNAME);
// 				jQuery("#${random}_purchasePlanTable").listen();
				var thisTr = jQuery("#${random}_purchasePlanTable").getTr($(elem));
				var rowid = thisTr.attr("id");
				thisTr.find("input[name='purchaser.personId']").val(ui.item.PERSONID).blur();
				thisTr.find("input[name='purchaser.personCode']").val(ui.item.PERSONCODE).blur();
				thisTr.find("input[name='purchaser.name']").val(ui.item.NAME).blur();
				thisTr.find("input[name='purchaser.sex']").val(ui.item.SEX).blur();
				thisTr.find("input[name='purchaser.duty']").val(ui.item.DUTY).blur();
				thisTr.find("input[name='purchaser.jobTitle']").val(ui.item.JOBTITLE).blur();
				thisTr.find("input[name='purchaser.postType']").val(ui.item.POSTTYPE).blur();
				thisTr.find("input[name='purchaser.emptype']").val(ui.item.EMPTYPE).blur();
				thisTr.attr("used","true");
				return false;
			}
		});
	}
	var ppdt = docTemplate("${docTemp.listMap}","${docTemp.title}","${fn:length(docTemp.inputNames)}","${fn:length(docTemp.footNames)}");
	jQuery(document).ready(function() {
		ppdt.setDocLayOut("${random}_purchasePlanForm","${random}_purchasePlanTable_div","${random}_purchasePlanForm_foot");
		if("${docPreview}"=="preview"){
			ppdt.disableButton("${random}_purchasePlan_toolBar");
			ppdt.clearData("${random}_purchasePlanCard");
		}
		var purchaseId = "${purchasePlan.purchaseId}";
		if(purchaseId==null || purchaseId==""){
			purchaseId = "new";
		}else{
			disableLink(["purchasePlan_gridtable_extend"]);
		}
		var zhGridSetting_purchasePlan = {
			url : "purchasePlanDetailGridList?filter_EQS_purchasePlan.purchaseId="+purchaseId,
			datatype : "json",
			mtype : "GET",
			trHeight:25,
			formId:'${random}_purchasePlanForm',
			paramName:'purchasePlanDetailJson',
			saveUrl:"${ctx}/savePurchasePlan?navTabId=purchasePlan_gridtable&entityIsNew=${entityIsNew}&saveType=saveStay",
			initColumn:"com.huge.ihos.material.purchaseplan.model.PurchasePlanDetail",
			afterSaveFun : function(data) {
				if("saveStay" == data.callbackType){
					var purchaseIdtemp = data.forwardUrl;
					var getmrIdval=$('#getmrId').val();
					/* delCookie('mrId'); */
					var editDeptMRSUrl="${ctx}/deptMRSummaryGridEdit?oper=editpurchaseId&purchaseId="+purchaseIdtemp+"&mrId="+getmrIdval;					
					$.post(editDeptMRSUrl,function(data) {
						$.pdialog.closeCurrent();
						$.pdialog.open("${ctx}/editPurchasePlan?purchaseId="+purchaseIdtemp,"editPurchasePlan","采购计划单明细", {mask:true,width:purchasePlanFormWidth,height:purchasePlanFormHeight});
					});		
					
				}
			},
			colModel:[
				{name:'purchaseDetailId',index:'purchaseDetailId',align:'center',label : '<s:text name="purchasePlanDetail.purchaseDetailId" />',hidden:true,key:true,editable : false,sortable:false},	
				{name:'invDict.invId',index:'invDict.invId',align:'center',label : '<s:text name="inventoryDict.invId" />',hidden:true,sortable:false},	
				{name:'invDict.invName',index:'invDict.invName',align:'left',width:150,label : ppdt.getListName("invDict.invName"),hidden:!(ppdt.containColumn("invDict.invName")),highsearch:ppdt.containColumn("invDict.invName"),editable : true,edittype : 'text',editoptions : {dataInit : invDictPurchasePlanComboGrid}},	
				{name:'invDict.invCode',index:'invDict.invCode',align:'left',width:80,label : ppdt.getListName("invDict.invCode"),hidden:!(ppdt.containColumn("invDict.invCode")),highsearch:ppdt.containColumn("invDict.invCode")},	
				{name:'invDict.invModel',index:'invDict.invModel',align:'left',width:120,label : ppdt.getListName("invDict.invModel"),hidden:!(ppdt.containColumn("invDict.invModel")),highsearch:ppdt.containColumn("invDict.invModel")},	
				{name:'invDict.firstUnit',index:'invDict.firstUnit',align:'center',width:80,label : ppdt.getListName("invDict.firstUnit"),hidden:!(ppdt.containColumn("invDict.firstUnit")),highsearch:ppdt.containColumn("invDict.firstUnit")},	
				{name:'curStock',index:'curStock',align:'right',width:100,label : ppdt.getListName("curStock"),hidden:!(ppdt.containColumn("curStock")),highsearch:ppdt.containColumn("curStock"),editable : false,formatter : 'number',sortable:false},	
				{name:'storeStock',index:'storeStock',align:'right',width:100,label : ppdt.getListName("storeStock"),hidden:!(ppdt.containColumn("storeStock")),highsearch:ppdt.containColumn("storeStock"),editable : false,formatter : 'number',sortable:false},	
				{name:'needAmount',index:'needAmount',align:'right',width:100,label : ppdt.getListName("needAmount"),hidden:!(ppdt.containColumn("needAmount")),highsearch:ppdt.containColumn("needAmount"),formatter : 'number'},	
				{name:'amount',index:'amount',align:'right',width:100,label : ppdt.getListName("amount"),hidden:!(ppdt.containColumn("amount")),highsearch:ppdt.containColumn("amount"),editable : true,editrules:{required:true},formatter : 'number'},	
				{name:'price',index:'price',align:'right',width:100,label : ppdt.getListName("price"),hidden:!(ppdt.containColumn("price")),highsearch:ppdt.containColumn("price"),editable:true,formatter : 'number'},	
				{name:'money',index:'money',align:'right',width:110,label : ppdt.getListName("money"),hidden:!(ppdt.containColumn("money")),highsearch:ppdt.containColumn("money"),formatter : 'currency',sortable:false},	
				{name:'arrivalDate',index:'arrivalDate',align:'center',width:80,label : ppdt.getListName("arrivalDate"),hidden:!(ppdt.containColumn("arrivalDate")),highsearch:ppdt.containColumn("arrivalDate"),editable:true,editrules:{required:true},formatter : 'date',formatoptions: {srcformat: 'Y-m-d H:i:s',newformat: 'Y-m-d'}, 
					editoptions : {dataInit :function(elem){jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});jQuery('#ui-datepicker-div').css("z-index", 2000);}}
				},	
				{name:'purchaser.personId',index:'purchaser.personId',align:'left',width:10,label : '<s:text name="purchasePlanDetail.purchaser" />',hidden:true,sortable:false},	
				{name:'purchaser.name',index:'purchaser.name',align:'left',width:60,label : '<s:text name="purchasePlanDetail.purchaser" />',hidden:false,highsearch:true,editable : true,editrules:{required:true},edittype : 'text',editoptions : {dataInit : invDictPurchaserComboGrid}},	
				 
				{name:'vendor.vendorId',index:'vendor.vendorId',align:'center',label : '',hidden:true,sortable:false},	
				{name:'vendor.vendorName',index:'vendor.vendorName',align:'left',width:150,label : '<s:text name ="vendor.vendorName"/>',hidden:false,highsearch:true,editable : true,editrules:{required:true},edittype : 'text',editoptions : {dataInit : invDictVendorNameComboGrid}},	
				//{name:'remark',index:'remark',align:'left',width:150,label : ppdt.getListName("remark"),hidden:!(ppdt.containColumn("remark")),highsearch:ppdt.containColumn("remark"),editable : true}
				{name:'remark',index:'remark',align:'left',width:150,label : ppdt.getListName("remark"),hidden:!(ppdt.containColumn("remark")),highsearch:ppdt.containColumn("remark"),editable : true}				
				/*{name:'needDept.name',index:'needDept.name',align:'left',width:80,label : '<s:text name="purchasePlanDetail.needDept" />',hidden:false,highsearch:true,editable:true},	
				{name:'needDept.departmentId',index:'needDept.departmentId',align:'left',width:20,label : '<s:text name="purchasePlanDetail.needDept" />',hidden:true,highsearch:false,editable:true},	
				 */
				/* {name:'invDict.factory',index:'invDict.factory',align:'left',width:120,label : ppdt.getListName("invDict.factory"),hidden:!(ppdt.containColumn("invDict.factory")),highsearch:ppdt.containColumn("invDict.factory")},	
				{name:'invDict.vendor.vendorName',index:'invDict.vendor.vendorName',align:'left',width:120,label : ppdt.getListName("invDict.vendor.vendorName"),hidden:!(ppdt.containColumn("invDict.vendor.vendorName")),highsearch:ppdt.containColumn("invDict.vendor.vendorName")} */
				/* {name:'invDict.vendor.vendorCode',index:'invDict.vendor.vendorCode',align:'left',width:60,label : ppdt.getListName("invDict.vendor.vendorCode"),hidden:!(ppdt.containColumn("invDict.vendor.vendorCode")),highsearch:ppdt.containColumn("invDict.vendor.vendorCode")}	 */
			 ],
			jsonReader : {
				root : "purchasePlanDetails", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
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
	       		jQuery("#${random}_purchasePlanTable").footData([
  				       {name:'invDict.invName',value:'合计:'},
  				       {name:'amount',value:'sum'},
  				       {name:'money',value:'sum'}
   				 ]);
	       	},
	       	beforeEditCell : function(rowid, cellname,value, iRow, iCol) {
	       		var store_id = jQuery("#${random}_purchasePlan_store").val();
				if(store_id == '' || store_id=='null'){
					alertMsg.error("请选择目标仓库。");
					return;
				}  
				var dept_id = jQuery("#${random}_purchasePlan_dept").val();
				if(dept_id == '' || dept_id == 'null'){
					alertMsg.error("请选择科室。");
					return;
				}
			},
			afterEditCell : function(rowid, cellname,value, iRow, iCol) {
				if(cellname=='amount'){
					var amount= jQuery("#${random}_purchasePlanTable").getCellData(rowid,'amount');
					var price= jQuery("#${random}_purchasePlanTable").getCellData(rowid,'price');
					amount = isNaN(parseFloat(amount))?0:parseFloat(amount);
					price = isNaN(parseFloat(price))?0:parseFloat(price);
	               /*  jQuery("#${random}_purchasePlanTable").setCellData(rowid,'curStock',0);
	                jQuery("#${random}_purchasePlanTable").setCellData(rowid,'storeStock',0);
	                jQuery("#${random}_purchasePlanTable").setCellData(rowid,'needAmount',0); */
	                jQuery("#${random}_purchasePlanTable").setCellData(rowid,'money',amount*price);
				}if(cellname=='invDict.invName'){
					var invId = jQuery("#${random}_purchasePlanTable").getCellData(rowid,'invDict.invId');
					var arrivalDate = jQuery("#${random}_purchasePlanTable").getCellData(rowid,'arrivalDate');
					var needAmount = jQuery("#${random}_purchasePlanTable").getCellData(rowid,'needAmount');
					if(invId){
						var store_id = jQuery("#${random}_purchasePlan_store_id").val();
						var periodMonth = "${purchasePlan.periodMonth}";
						var orgCode = "${purchasePlan.orgCode}";
					    var copyCode = "${purchasePlan.copyCode}";
					    jQuery.ajax({
						       url: 'getStoreAndNeedAmount',
						       data: {invId:invId,store_id:store_id,periodMonth:periodMonth,orgCode:orgCode,copyCode:copyCode},
						       type: 'post',
						       dataType: 'json',
						       async:false,
						       error: function(data){
						       },
						       success: function(data){
						    	   jQuery("#${random}_purchasePlanTable").setCellData(rowid,'curStock',data.amounts.curStock);
						    	   jQuery("#${random}_purchasePlanTable").setCellData(rowid,'storeStock',data.amounts.storeStock);
						    	   if(needAmount){
						    	   }else{
						    		   jQuery("#${random}_purchasePlanTable").setCellData(rowid,'needAmount',data.amounts.needAmount);   
						    	   }		
						    	   if(arrivalDate){
						    	   }else{
						    		   jQuery("#${random}_purchasePlanTable").setCellData(rowid,'arrivalDate',"${currentSystemVariable.businessDate}");
						    	   }
						       }
						   });
					}
				}
			},
			afterSaveCell : function(rowid, cellname,value, iRow, iCol) {
			}
		}
		jQuery("#${random}_purchasePlanTable").zhGrid(zhGridSetting_purchasePlan);
		var state = "${purchasePlan.state}";
		if("${docPreview}"!="preview"){
			if("${docPreview}"=="refer"){//从别处通过超链接查看单据
				disableLink(["${random}_purchasePlan_new","${random}_purchasePlan_importDict","${random}_purchasePlan_save","${random}_purchasePlan_check","${random}_purchasePlan_cancelCheck","${random}_purchasePlan_stop","${random}_purchasePlan_abandon","${random}_purchasePlan_print","${random}_purchasePlan_printSet"]);
				clearInputClassInPurchasePlan();
			}else{
				if(${entityIsNew}){
					disableLink(["${random}_purchasePlan_check","${random}_purchasePlan_stop","${random}_purchasePlan_abandon"]);
				}
				
				if(state!=0){//审核、作废、中止
					disableLink(["${random}_purchasePlan_save","${random}_purchasePlan_check","${random}_purchasePlan_importDict","${random}_purchasePlan_print","${random}_purchasePlan_printSet"]);
				   if(state==1){//已审核
					   disableLink(["${random}_purchasePlan_stop"]);
				   }else if(state==2){//已完成
						disableLink(["${random}_purchasePlan_stop","${random}_purchasePlan_cancelCheck"]);
					}else if(state==3){//中止
						disableLink(["${random}_purchasePlan_stop","${random}_purchasePlan_cancelCheck"]);
					}else if(state==4){//作废
						disableLink(["${random}_purchasePlan_stop","${random}_purchasePlan_cancelCheck","${random}_purchasePlan_abandon"]);
					}	
					clearInputClassInPurchasePlan();
				}else if(state==0){//新建
					jQuery("#${random}_purchasePlanTable").fullEdit();
					disableLink(["${random}_purchasePlan_new","${random}_purchasePlan_cancelCheck","${random}_purchasePlan_print","${random}_purchasePlan_printSet"]);
					jQuery("#${random}_purchasePlan_store").treeselect({
						dataType : "sql",
						optType : "single",
						sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
						exceptnullparent : false,
						lazy : false
					}); 
				    jQuery("#${random}_purchasePlan_dept").treeselect({
						dataType:"sql",
						optType:"single",
						sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 and ysPurchasingDepartment = 1 ORDER BY internalCode",
						exceptnullparent:false,
						lazy:false
					});
				    jQuery('#${random}_purchasePlan_makeDate').unbind( 'click' ).bind("click",function(){
				    	WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'${fns:userContextParam('periodBeginDateStr')}',maxDate:'${fns:userContextParam('periodBeginDateStr')}'});
				    });
				}
			}
		}
	});
	function clearInputClassInPurchasePlan(){
		jQuery("#${random}_purchasePlan_store").removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_purchasePlan_dept").removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_purchasePlan_makeDate").removeClass('Wdate').removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_purchasePlan_remark").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
	}
</script>
<script type="text/javascript">
	function newPurchasePlan(){
		$.pdialog.closeCurrent();
		$.pdialog.open('${ctx}/editPurchasePlan','addPurchasePlan',"添加采购计划单", {mask:true,width : purchasePlanFormWidth,height : purchasePlanFormHeight,resizable:false});
	}
	function importDictToPurchasePlan(){
		var store_id = jQuery("#${random}_purchasePlan_store_id").val();
		if(store_id == '' || store_id=='null'){
			alertMsg.error("请选择仓库。");
			return;
		} 
		var url = "${ctx}/invInStoreList?popup=true&storeId="+store_id+"&fromType=purchasePlan&random=${random}";
		var winTitle="<s:text name='材料选择'/>";
		url = encodeURI(url);
		$.pdialog.open(url, 'importDictToPurchasePlan', winTitle, {mask:true,width:960,height:628});
	}
	
	function savePurchasePlan(){
		jQuery("#${random}_purchasePlanTable").saveGrid();
	}
	function auditPurchasePlan(){
		var gridEdited = jQuery("#${random}_purchasePlanTable")[0].p.gridEdited;
		if(gridEdited){
			alertMsg.error("单据内容有修改，请先保存！");
			return ;
			
		}
		var editUrl = "purchasePlanGridEdit?id=${purchasePlan.purchaseId}&navTabId=purchasePlan_gridtable&oper=check";
		alertMsg.confirm("确认审核？", {
			okCall : function() {
				$.post(editUrl,function(data) {
					$.pdialog.reload("${ctx}/editPurchasePlan?popup=true&purchaseId=${purchasePlan.purchaseId}",{dialogId:'editPurchasePlan'});
					formCallBack(data);
				});
			}
		});
	}
	function antiAuditPurchasePlan(){
		var editUrl = "purchasePlanGridEdit?id=${purchasePlan.purchaseId}&navTabId=purchasePlan_gridtable&oper=cancelCheck";
		alertMsg.confirm("确认销审？", {
			okCall : function() {
				$.post(editUrl,function(data) {
					$.pdialog.reload("${ctx}/editPurchasePlan?popup=true&purchaseId=${purchasePlan.purchaseId}",{dialogId:'editPurchasePlan'});
					formCallBack(data);
				});
			}
		});
	}
	function abandonOrStopPurchasePlan(oper){
		var editUrl = "purchasePlanGridEdit?id=${purchasePlan.purchaseId}&navTabId=purchasePlan_gridtable&oper="+oper;
		alertMsg.confirm("确认"+(oper=="abandon"?"作废":"中止")+"计划？", {
			okCall : function() {
				$.post(editUrl,function(data) {
					$.pdialog.closeCurrent();
					formCallBack(data);
				});
			}
		});
	}
	
	function printPurchasePlan(){
		/* var purchasePlanCardHtml = jQuery("#purchasePlanCard").html();
		jQuery("body").append("<div id='page1'>"+purchasePlanCardHtml+"</div>");
		jQuery("#purchasePlanTable_head",'#page1').css('width','99%');
		jQuery("#purchasePlanTable",'#page1').css('width','99%');
		jQuery("#purchasePlanTable_foot",'#page1').css('width','99%');
		jQuery("#purchasePlanTable_div",'#page1').css('width','auto');
		jQuery("#purchasePlanTable_div",'#page1').css('height','auto');
		return;
		doPrint(); */
	}
	function printSetPurchasePlan(){
		
	}
	function closePurchasePlan(){
		var gridEdited = jQuery("#${random}_purchasePlanTable")[0].p.gridEdited;
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
	function extendPurchasePlan(){
		 var storeId = jQuery("#${random}_purchasePlan_store_id").val();
		if(storeId && storeId!=''){
			$.pdialog.open('${ctx}/selectDeptMRSummaryList?random=${random}&storeId='+storeId,'selectDeptMRSummary',"选择需求计划汇总", {mask:true,width : 960,height : 628,resizable:false});
// 			$.pdialog.open('${ctx}/selectDeptNeedPlanList?storeId='+storeId,'selectDeptNeedPlan',"选择需求计划", {mask:true,width : 960,height : 628,resizable:false}); 
		}else{
			alertMsg.error("请选择仓库");
			return;
		} 
		
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar" id="${random}_purchasePlan_toolBar">
				<li>
					<a id="${random}_purchasePlan_new" class="addbutton" href="javaScript:newPurchasePlan()" ><span><s:text name="新单" /></span></a>
				</li>
				<li>
					<a id="purchasePlan_gridtable_extend" class="deliverbutton" href="javaScript:extendPurchasePlan()" ><span><s:text name="需求计划引入" /></span></a>
				</li>
				<li>
					<a id="${random}_purchasePlan_importDict" class="getdatabutton" href="javaScript:importDictToPurchasePlan()"><span>选择材料</span></a>
				</li>
				<%-- <li>
					<a id="${random}_purchasePlan_extend" class="deliverbutton" href="javaScript:extendPurchasePlan()" ><span><s:text name="根据需求生成" /></span></a>
				</li> --%>
				<li>
					<a id="${random}_purchasePlan_save" class="savebutton" href="javaScript:savePurchasePlan()"><span>保存</span> </a>
				</li>
				<li>
					<a id="${random}_purchasePlan_check" class="checkbutton"  href="javaScript:auditPurchasePlan()"><span><s:text name="审核" /></span></a>
				</li>
				<li>
					<a id="${random}_purchasePlan_cancelCheck" class="delallbutton"  href="javaScript:antiAuditPurchasePlan()"><span><s:text name="销审" /></span></a>
				</li>
				<li>
					<a id="${random}_purchasePlan_abandon" class="disablebutton"  href="javaScript:abandonOrStopPurchasePlan('abandon')" style="display:none"><span><s:text name="button.abandon" /></span></a>
				</li>
				<li>
					<a id="${random}_purchasePlan_stop" class="confirmbutton"  href="javaScript:abandonOrStopPurchasePlan('stop')"><span><s:text name="中止计划" /></span></a>
				</li>
				<li>
					<a id="${random}_purchasePlan_print" class="printbutton" href="javaScript:printPurchasePlan()"><span>打印</span> </a>
				</li>
				<li>
					<a id="${random}_purchasePlan_printSet" class="settingbutton" href="javaScript:printSetPurchasePlan()"><span>打印设置</span> </a>
				</li>
				<li>
					<a id="${random}_purchasePlan_setColShow" class="settlebutton"  href="javaScript:setColShow('${random}_purchasePlanTable','com.huge.ihos.material.purchaseplan.model.PurchasePlanDetail','zhGrid')"><span><s:text name="button.setColShow" /></span></a>
				</li>
				<li>
					<a id="${random}_purchasePlan_close" class="closebutton" href="javaScript:closePurchasePlan()"><span>关闭</span> </a>
				</li>
			</ul>
		</div>
		<div id="${random}_purchasePlanCard" autoBreakPage='*' style="margin:5px 10px 5px 5px">
			<form id="${random}_purchasePlanForm">
				<s:if test="%{!entityIsNew}">
					<span style="position:absolute;right:14px;font-size:18px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px">No.${purchasePlan.purchaseNo}</span>
				</s:if>
				<div align="center" style="font-size:25px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px;margin:0 auto;">
					${docTemp.title}
					<hr style="color:rgb(24, 127, 207);margin-top:0px"/>
					<hr style="color:rgb(24, 127, 207);margin-top:-11px"/>
				</div>
				<div>
					<s:hidden key="purchasePlan.purchaseId"/>
					<s:hidden key="purchasePlan.purchaseNo"/>
					<s:hidden key="purchasePlan.orgCode"/>
					<s:hidden key="purchasePlan.copyCode"/>
					<s:hidden key="purchasePlan.periodMonth"/>
					<s:hidden key="purchasePlan.state"/>
					<s:hidden key="purchasePlan.docTemId"/>
					<s:hidden key="purchasePlan.makePerson.personId"/>
					<s:hidden key="getmrId"></s:hidden>
				</div>
				<div style="border:0;width:100%;table-layout:fixed;margin-top:-10px;">
					<c:forEach items="${docTemp.inputList}" var="input">
						<span class="docInputArea" id="${random}_${input.value}_${input.necessary}">&nbsp;&nbsp;&nbsp; 
							<label><c:out value="${input.name}"/>:</label>
							<c:set var="inputType" value="${input.type}" scope="page"/> 
							<c:set var="inputKey" value="${input.value}" scope="page"/>
							<c:set var="referId" value="${input.referId}" scope="page"/>
							<c:set var="referName" value="${input.referName}" scope="page"/>
							<c:if test="${inputType=='refer'}" >
								<input type="text" id="${random}_purchasePlan_${inputKey}" name="purchasePlan.${inputKey}.${referName}" value="${purchasePlan[inputKey][referName]}" />
								<input type="hidden" id="${random}_purchasePlan_${inputKey}_id" name="purchasePlan.${inputKey}.${referId}" value="${purchasePlan[inputKey][referId]}"/>
							</c:if>
							<c:if test="${inputType=='date'}" >
								<input type="text" id="${random}_purchasePlan_${inputKey}" name="purchasePlan.${inputKey}" value="<fmt:formatDate value='${purchasePlan[inputKey]}' pattern='yyyy-MM-dd' />" class="Wdate"  style="height:15px;width:80px"/>
							</c:if>
							<c:if test="${inputType=='simple'}" >
								<input type="text" id="${random}_purchasePlan_${inputKey}" name="purchasePlan.${inputKey}" value="${purchasePlan[inputKey]}" />
							</c:if>
							<script>
								if("${purchasePlan.state}"=="0" && ${input.necessary}){
									jQuery("#${random}_${input.value}_${input.necessary} input[type='text']").addClass("required");
								}
							</script>
						</span>
					</c:forEach> 
				</div>
			</form>
			<div id="${random}_purchasePlanTable_div" class="zhGrid_div">
				<table id="${random}_purchasePlanTable"></table>
			</div>
			<div style="height:26px" id="${random}_purchasePlanForm_foot">
				<ul style="float:left;width:100%;height:20px;margin-top:8px;text-align:center">
					<c:forEach items="${docTemp.footMap}" var="foot">
						<li style="float:left;">
							<label><c:out value="${foot.value}"/>:</label>
							<c:if test="${foot.key!='sign'}">
									<c:set var="footKey" value="${foot.key}" scope="page"/>
									<input class="lineInput" size="8" value="${purchasePlan[footKey].name}" readonly="readonly"/>
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





