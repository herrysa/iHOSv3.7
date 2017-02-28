
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function invDictInComboGrid(elem) {
		var elemId = jQuery(elem).attr("id");
		elemId=elemId.replace(".","\.");
		jQuery(elem).attr("id",elemId);
		var sql = "select invId,invCode,invName,invModel,firstUnit,isNULL(refCost,0) refCost,replace(replace(isBar,1,'是'),0,'否') as isBar,replace(replace(isQuality,1,'是'),0,'否') as isQuality from v_mm_invInstore where storeId in ('"+jQuery("#${random}_invMainIn_store_id").val()+"','1') and orgCode='"+"${fns:userContextParam('orgCode')}"+"' and copyCode='"+"${fns:userContextParam('copyCode')}"+"'";
		var selectInvDictByVendor = "${selectInvDictByVendor}";
		if("${selectInvDictByVendor}"=="1"){
			var vendorId = jQuery("#${random}_invMainIn_vendorName_id").val();
			if(vendorId){
				sql += " and vendorId='"+vendorId+"'";
			}
		}
		$(elem).combogrid({
			url : '${ctx}/comboGridSqlList',
			queryParams : {
				//sql : "select invId,invCode,invName,invModel,firstUnit,isNULL(refCost,0) refCost,replace(replace(isBar,1,'是'),0,'否') as isBar,replace(replace(isQuality,1,'是'),0,'否') as isQuality from mm_inv_dict where invId in (select invId from mm_storeInvSet where storeId = '"+jQuery("#${random}_invMainIn_store_id").val()+"' union select invId from mm_inv_dict where isPublic = 1 and orgCode='"+jQuery("#orgCode").html()+"' and copyCode='"+jQuery("#copyCode").html()+"')",
				sql:sql,
				cloumns : 'invName,invCode,cncode'
			},
			autoFocus : false,
			showOn : false, 
			rows:10,
			width:600,
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
				'width' : '10',
				'align' : 'left',
				'label' : '型号规格'
			}, {
				'columnName' : 'FIRSTUNIT',
				'width' : '10',
				'label' : '计量单位'
			}, {
				'columnName' : 'REFCOST',
				'width' : '15',
				'align' : 'right',
				'label' : '参考进价'
			}, {
				'columnName' : 'ISBAR',
				'width' : '20',
				'label' : '是否条码管理'
			}, {
				'columnName' : 'ISQUALITY',
				'width' : '20',
				'label' : '是否保质期管理',
				hidden:true
			}
			],
			_create: function( event, item ) {
				//alert();
			},
			select : function(event, ui) {
				//$(elem).attr("value",ui.item.INVNAME);
				var thisTr = jQuery("#${random}_invMainInTable").getTr($(elem));
				var rowid = thisTr.attr("id");
				thisTr.find("input[name='invDict.invId']").val(ui.item.INVID).blur();;
				thisTr.find("input[name='invDict.invCode']").val(ui.item.INVCODE).blur();;
				thisTr.find("input[name='invDict.invModel']").val(ui.item.INVMODEL).blur();;
				thisTr.find("input[name='invDict.firstUnit']").val(ui.item.FIRSTUNIT).blur();;
				thisTr.find("input[name='inPrice']").val(ui.item.REFCOST).blur();
				jQuery("#${random}_invMainInTable").setCellData(rowid,'invDict.isBar',ui.item.ISBAR=="是");
				jQuery("#${random}_invMainInTable").setCellData(rowid,'invDict.isQuality',ui.item.ISQUALITY=="是");
				jQuery("#${random}_invMainInTable").setCellData(rowid,'invDict.invName',ui.item.INVNAME);
				thisTr.attr("used","true");
				return false;
			}
		});
	} 
	function checkBatchNo(invDictId,batchNo){
		if(!batchNo){
			return false;
		}
		if(batchNo.trim()=="" || batchNo=="默认批号"){
			return false;
		}
		var isDouble = false;
		jQuery.ajax({
			url:'checkBatchNo?invDictId='+invDictId+'&batchNo='+batchNo+'&storeId='+$('#${random}_invMainIn_store_id').val(),
			type:'get',
			dataType:'json',
			async:false,
			success:function(data){
				isDouble = data.doubleBatchNo;	
			}
		});	
		return isDouble;
	} 
	
	function invMainInBuyPersonTreeSelect(){
		setTimeout(function(){
			var sql = "SELECT p.personId id ,p.name name FROM t_person p where p.disabled = 0 and p.dept_id='"+$("#${random}_invMainIn_buyDept_id").val()+"'";
			 jQuery("#${random}_invMainIn_buyPerson").treeselect({
				dataType:"sql",
				optType:"single",
				sql:sql,
				exceptnullparent:false,
				lazy:false
			}); 
		},50);
	}
</script>
<script type="text/javascript">
	var iidt = docTemplate("${docTemp.listMap}","${docTemp.title}","${fn:length(docTemp.inputNames)}","${fn:length(docTemp.footNames)}");

	jQuery(document).ready(function() {
		iidt.setDocLayOut("${random}_invMainInForm","${random}_invMainInTable_div","${random}_invMainInForm_foot");
		if("${docPreview}"=="preview"){
			iidt.disableButton("${random}_invMainIn_toolBar");
			iidt.clearData("${random}_invMainInCard");
		}
		var invMainInId = "${invMain.ioId}";
		if(invMainInId==null || invMainInId==""){
			invMainInId = "new";
		}
		var zhGridSetting_invMainIn={
			url : "invDetailGridList?filter_EQS_invMain.ioId="+invMainInId,
			datatype : "json",
			mtype : "GET",
			formId:'${random}_invMainInForm',
			paramName:'invDetailJson',
			saveUrl:"${ctx}/saveInvMainIn?navTabId=invMainIn_gridtable&entityIsNew=${entityIsNew}&saveType=saveStay",
			initColumn:"com.huge.ihos.material.model.InvMainInDetail",
			afterSaveFun : function(data) {
				if("saveStay" == data.callbackType){
					$.pdialog.closeCurrent();
					$.pdialog.open("${ctx}/editInvMainIn?ioId="+data.forwardUrl,"showInvMainIn","入库单明细", {mask:true,width:width,height:height});
				}
			},	
			colModel : [
				{name : 'invDetialId',index : 'invDetialId',align : 'center',label : '<s:text name="invDetail.invDetialId" />',hidden : true,key : true,editable : false,sortable:false},
				{name : 'invDict.invId',index : 'invDict_invId',align : 'left',label : '<s:text name="inventoryDict.invId" />',hidden : true,editable : true,edittype : 'text',sortable:false},
				{name : 'invDict.invName',index : 'invDict_invName',align : 'left',width : 150,label : iidt.getListName("invDict.invName"),hidden : !(iidt.containColumn("invDict.invName")),highsearch : iidt.containColumn("invDict.invName"),editable : true,edittype : 'text',editoptions : {dataInit : invDictInComboGrid},sortable:false},
				{name : 'invDict.invCode',index : 'invDict_invCode',align : 'left',width : 80,label : iidt.getListName("invDict.invCode"),hidden : !(iidt.containColumn("invDict.invCode")),highsearch : iidt.containColumn("invDict.invCode"),sortable:false},
				{name : 'invDict.invModel',index : 'invDict_invModel',align : 'left',width : 120,label : iidt.getListName("invDict.invModel"),hidden : !(iidt.containColumn("invDict.invModel")),highsearch : iidt.containColumn("invDict.invModel"),sortable:false},
				{name : 'invDict.firstUnit',index : 'invDict_firstUnit',align : 'center',width : 80,label : iidt.getListName("invDict.firstUnit"),hidden : !(iidt.containColumn("invDict.firstUnit")),highsearch : iidt.containColumn("invDict.firstUnit"),sortable:false},
				{name : 'inAmount',index : 'inAmount',align : 'right',width : 100,label : iidt.getListName("inAmount"),hidden : !(iidt.containColumn("inAmount")),highsearch : iidt.containColumn("inAmount"),formatter : 'number',editable : true,},
				{name : 'inPrice',index : 'inPrice',align : 'right',width : 100,label : iidt.getListName("inPrice"),hidden : !(iidt.containColumn("inPrice")),highsearch : iidt.containColumn("inPrice"),formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces: 2},editable : true,editrules : {number : true,required : true}},
				{name : 'inMoney',index : 'inMoney',align : 'right',width : 110,label : iidt.getListName("inMoney"),hidden : !(iidt.containColumn("inMoney")),highsearch : iidt.containColumn("inMoney"),formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces: 2}},
				{name : 'addRate',index : 'addRate',align : 'right',width : 100,label : iidt.getListName("addRate"),hidden : !(iidt.containColumn("addRate")),highsearch : iidt.containColumn("addRate"),formatter : 'number',editable : true,editrules : {number : true,required : true}},
				{name : 'salePrice',index : 'salePrice',align : 'right',width : 100,label : iidt.getListName("salePrice"),hidden : !(iidt.containColumn("salePrice")),highsearch : iidt.containColumn("salePrice"),formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces: 2},editable : true},
				{name : 'invBatch.batchNo',index : 'invBatch.batchNo',align : 'left',width : 120,label : iidt.getListName("invBatch.batchNo"),hidden : !(iidt.containColumn("invBatch.batchNo")),highsearch : iidt.containColumn("invBatch.batchNo"),editable : true,edittype : 'text',sortable:false},
				{name : 'invDict.isQuality',index : 'invDict_isQuality',align : 'center',width : 110,label : iidt.getListName("invDict.isQuality"),hidden : !(iidt.containColumn("invDict.isQuality")),highsearch : iidt.containColumn("invDict.isQuality"),sortable:false,formatter : 'checkbox',editable : false,edittype : "checkbox",editoptions : {value : "true:false"} },
				{name : 'invBatch.validityDate',index : 'invBatch.validityDate',align : 'center',width : 100,label : iidt.getListName("invBatch.validityDate"),hidden : !(iidt.containColumn("invBatch.validityDate")),highsearch : iidt.containColumn("invBatch.validityDate"),editable : true,formatter: 'date',
				           formatoptions: {srcformat: 'Y-m-d H:i:s',newformat: 'Y-m-d'}, 
							editoptions : {
								dataInit :function(elem){
							        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
							        jQuery('#ui-datepicker-div').css("z-index", 2000);
							    }
							}
				},
				{name : 'invDict.isBar',index : 'invDict_isBar',align : 'center',width : 100,label : iidt.getListName("invDict.isBar"),hidden : !(iidt.containColumn("invDict.isBar")),highsearch : iidt.containColumn("invDict.isBar"),sortable:false,formatter : 'checkbox',editable : false,edittype : "checkbox",editoptions : {value : "true:false"} },
				{name : 'snCode',index : 'snCode',align : 'left',width : 150,label : iidt.getListName("snCode"),hidden : !(iidt.containColumn("snCode")),highsearch : iidt.containColumn("snCode"),editable : true},
				{name : 'isPerBar',index : 'isPerBar',align : 'center',width : 80,label : iidt.getListName("isPerBar"),hidden : !(iidt.containColumn("isPerBar")),highsearch : iidt.containColumn("isPerBar"),formatter : 'checkbox',editable : true,edittype : "checkbox",editoptions : {value : "true:false"}},
				{name : 'unitBarCode',index : 'unitBarCode',align : 'left',width : 150,label : iidt.getListName("unitBarCode"),hidden : !(iidt.containColumn("unitBarCode")),highsearch : iidt.containColumn("unitBarCode"),editable : true},
				{name : 'invBatch.id',index : 'invBatch.id',align : 'left',label : '<s:text name="invDetail.id" />',hidden : true}

			],
			jsonReader : {
				root : "invDetails", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
			sortname : 'invDetialId',
			viewrecords : true,
			sortorder : 'desc',
			height : 300,
			width : 100,
			gridview : true,
			rowNum:500,
			rownumbers : true,
			loadui : "disable",
			multiselect : true,
			multiboxonly : true,
			shrinkToFit : false,
			autowidth : false,
			cellEdit : true,
			cellsubmit : 'clientArray',
			onSelectRow : function(rowid, status, e) {
			},

			gridComplete : function() {
				 jQuery("#${random}_invMainInTable").footData([
  				       {name:'invDict.invName',value:'合计:'},
  				       {name:'inMoney',value:'sum'}
   				 ]);
			},
			beforeEditCell : function(rowid, cellname,value, iRow, iCol) {
				var store_id = jQuery("#${random}_invMainIn_store_id").val();
				if(store_id == '' || store_id=='null'){
					alertMsg.error("请选择目标仓库。");
					return;
				}  
			},
			afterEditCell : function(rowid, cellname,value, iRow, iCol) {
				if(cellname==='inPrice' || cellname==='inAmount'){
					var price= jQuery("#${random}_invMainInTable").getCellData(rowid,'inPrice');
					 var amount= jQuery("#${random}_invMainInTable").getCellData(rowid,'inAmount');
					 price = isNaN(parseFloat(price))?0:parseFloat(price);
					 amount = isNaN(parseFloat(amount))?0:parseFloat(amount);
	                jQuery("#${random}_invMainInTable").setCellData(rowid,'inMoney',price*amount);
				}
				 if(cellname==='invBatch.batchNo'){//验证批号是否重复
					var batchNo= jQuery("#${random}_invMainInTable").getCellData(rowid,'invBatch.batchNo');
					var invDictId = jQuery("#${random}_invMainInTable").getCellData(rowid,'invDict.invId');
					if(checkBatchNo(invDictId,batchNo)){
						jQuery("#${random}_invMainInTable").setCellData(rowid,'invBatch.batchNo',"默认批号");
						alertMsg.error("批号重复，已设为默认批号，如需修改，请重新输入");
					}
				} 
				if(cellname==='invBatch.validityDate'){
	                var isQuality= jQuery("#${random}_invMainInTable").getCellData(rowid,'invDict.isQuality');
	               // var validityDate= jQuery("#${random}_invMainInTable").getCellData(rowid,'invBatch.validityDate');
	                if(isQuality=='true' ){
	                	//alert(validityDate);
		                if(!value){
		                	alertMsg.error("有效期管理材料，有效期必填");	
		                }
	                }
				}
			},
			afterSaveCell : function(rowid, cellname,value, iRow, iCol) {
			}
		}//end setting
		jQuery("#${random}_invMainInTable").zhGrid(zhGridSetting_invMainIn);
	if("${docPreview}"!="preview"){
		if("${docPreview}"=="refer"){
			disableLink(["${random}_invMainIn_new","${random}_invMainIn_chooseInvDetail","${random}_invMainIn_import_order","${random}_invMainInSave","${random}_invMainInAudit","${random}_invMainInAntiAudit","${random}_invMainInConfirm","${random}_invMainInPrint","${random}_invMainInPrintSet"]);
			clearInputClassInInvMainIn();
		}else{
			if(${entityIsNew}){
				disableLink(["${random}_invMainInAudit"]);
			}
			if("${invMain.status}"=="0"){
				jQuery("#${random}_invMainInTable").fullEdit();
				disableLink(["${random}_invMainIn_new","${random}_invMainInPrint","${random}_invMainInPrintSet","${random}_invMainInAntiAudit","${random}_invMainInConfirm"]);
				jQuery("#${random}_invMainIn_busType").treeselect({//业务类型
					dataType : "sql",
					optType : "single",
					sql : "SELECT TYPECODE id, TYPENAME name FROM MM_BUSINESSTYPE WHERE DISABLED=0 AND INOUT = '1' ",
					exceptnullparent : false,
					lazy : false
				});
				
				jQuery("#${random}_invMainIn_vendorName").combogrid({
					url : '${ctx}/comboGridSqlList',
					queryParams : {
						sql : "SELECT v.vendorId vid,v.vendorName vname,v.shortName sname,v.vendorCode vcode from sy_vendor v where isMate = 1 and disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
						cloumns : 'vendorName,cncode,vendorCode'
					},
					 autoFocus : false,
					autoChoose : false,
					debug : false,
					resetButton : false,
					resetFields : null,
					searchButton : false,
					searchIcon : false,
					okIcon : false,
					showOn : false, 
					rows:10,
					width:400,
					sidx:"VID",
					colModel : [ {
						'columnName' : 'VID',
						'width' : '10',
						'label' : 'VID',
						hidden : true,
					},{
						'columnName' : 'VCODE',
						'width' : '30',
						'align' : 'left',
						'label' : '供货商编码'
					},{
						'columnName' : 'VNAME',
						'width' : '60',
						'align' : 'left',
						'label' : '供货商名称'
					}
					],
					select : function(event, ui) {
						$(event.target).val(ui.item.VNAME);
						$("#${random}_invMainIn_vendorName_id").val(ui.item.VID);
						return false; 
					}
				});
				jQuery("#${random}_invMainIn_store").treeselect({
					dataType : "sql",
					optType : "single",
					sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where is_book = 1 and is_lock = 0 and disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
					exceptnullparent : false,
					lazy : false,
					callback : {
						afterClick : function() {
							jQuery.ajax({
								type:'get',
								url:'${ctx}/getDeptByStore',
								data:{id:jQuery("#${random}_invMainIn_store_id").val()},
								dataType:'json',
								async:false,
								error:function(){
								},
								success:function(data){
									var dept = data.department;
									if(dept){
										jQuery("#${random}_invMainIn_buyDept").val(dept['name']);
										jQuery("#${random}_invMainIn_buyDept_id").val(dept['departmentId']);
										$("#${random}_invMainIn_buyPerson_id").val("");
										$("#${random}_invMainIn_buyPerson").val("");
										invMainInBuyPersonTreeSelect();
									}
								}
							});
						}
					}
				});
				jQuery("#${random}_invMainIn_buyDept").treeselect({
					dataType:"sql",
					optType:"single",
					sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 and ysPurchasingDepartment = 1 ORDER BY internalCode",
					exceptnullparent:false,
					lazy:false,
					callback : {
						afterClick : function() {
							$("#${random}_invMainIn_buyPerson_id").val("");
							$("#${random}_invMainIn_buyPerson").val("");
							invMainInBuyPersonTreeSelect();
						}
					}
				});
				jQuery('#${random}_invMainIn_makeDate').unbind( 'click' ).bind("click",function(){
					var storeId = jQuery("#${random}_invMainIn_store_id").val();
					if(storeId){
						jQuery.ajax({
							type:'get',
							url:'${ctx}/getDeptByStore',
							data:{id:storeId},
							dataType:'json',
							async:false,
							error:function(){
								
							},
							success:function(data){
						    	WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:data.storeStartDate});
							}
						});
					}else{
						alertMsg.error("请选择目标仓库。");
						return;
					}
		    	});
				if(jQuery("#${random}_invMainIn_buyDept").val()){
					invMainInBuyPersonTreeSelect();
				}
			}else{
				if("${invMain.status}"=="1"){
					disableLink(["${random}_invMainIn_import_order","${random}_invMainInPrint","${random}_invMainInPrintSet","${random}_invMainInAudit","${random}_invMainInSave","${random}_invMainIn_chooseInvDetail"]);
				}else{
					disableLink(["${random}_invMainIn_import_order","${random}_invMainInAntiAudit","${random}_invMainInSave","${random}_invMainInAudit","${random}_invMainInConfirm","${random}_invMainIn_chooseInvDetail"]);
				}
				clearInputClassInInvMainIn();
		    }
		}
	}
	});
	function clearInputClassInInvMainIn(){
		jQuery("#${random}_invMainIn_busType").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"true");
    	jQuery("#${random}_invMainIn_store").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"true");
    	jQuery("#${random}_invMainIn_buyPerson").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"true");
    	jQuery("#${random}_invMainIn_orderNo").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
    	jQuery("#${random}_invMainIn_makeDate").removeClass('Wdate').removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"readonly");
    	
    	jQuery("#${random}_invMainIn_proctypeCode").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"readonly");
    	jQuery("#${random}_invMainIn_buyDept").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"readonly");
    	jQuery("#${random}_invMainIn_vendorName").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"readonly");
    	jQuery("#${random}_invMainIn_invoiceNo").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
    	jQuery("#${random}_invMainIn_invoiceDate").removeClass('Wdate').removeClass('textInput').addClass('lineInput').attr('readOnly',"readonly");
    	
    	jQuery("#${random}_invMainIn_allotCompany").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
    	
    	jQuery("#${random}_invMainIn_remark").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");

	}
</script>
<script type="text/javascript">
	function newInvMainIn(){
		$.pdialog.closeCurrent();
		$.pdialog.open("${ctx}/editInvMainIn?radomJsp="+Math.floor(Math.random()*10),"addInvMainIn","添加入库单", {mask:true,width:width,height:height});
	}
	function importDictToInvMainIn(){
		var store_id = jQuery("#${random}_invMainIn_store_id").val();
		if(store_id == '' || store_id=='null'){
			alertMsg.error("请选择目标仓库。");
			return;
		} 
		var url = "${ctx}/invInStoreList?popup=true&storeId="+store_id+"&fromType=invMainIn"+"&random="+${random};
		if("${selectInvDictByVendor}"=="1"){
			var vendorId = jQuery("#${random}_invMainIn_vendorName_id").val();
			if(vendorId){
				url += "&vendorId="+vendorId;
			}
		}
		var winTitle="<s:text name='材料选择'/>";
		url = encodeURI(url);
		$.pdialog.open(url, 'importDictToInvMainIn', winTitle, {mask:true,width:width,height:height,resizable:false,maxable:false});
	}
	function saveInvMainIn(){
		if(fromOrderData) {
			var url = "${ctx}/saveInvMainIn?navTabId=invMainIn_gridtable&entityIsNew=${entityIsNew}&saveType=saveStay&fromOrderData="+fromOrderData;
			jQuery("#${random}_invMainInTable").jqGrid('setGridParam',{saveUrl:url});
		}
		jQuery("#${random}_invMainInTable").saveGrid();
	}
	function auditInvMainIn(){
		var gridEdited = jQuery("#${random}_invMainInTable")[0].p.gridEdited;
		if(gridEdited){
			alertMsg.error("单据内容有修改，请先保存！");
			return ;
			
		}
		var editUrl = "${ctx}/invMainInAudit?id=${invMain.ioId}&navTabId=invMainIn_gridtable";
		alertMsg.confirm("确认审核？", {
			okCall : function() {
				jQuery.post(editUrl, function(data) {
					$.pdialog.reload("${ctx}/editInvMainIn?ioId=${invMain.ioId}&popup=true",{dialogId:'editInvMainIn'});
					formCallBack(data);
				});
			}
		});
	}
	function antiAuditMainIn(){
		var editUrl = "${ctx}/invMainInUnAudit?id=${invMain.ioId}&navTabId=invMainIn_gridtable";
		alertMsg.confirm("确认销审？", {
			okCall : function() {
				jQuery.post(editUrl, function(data) {
					$.pdialog.reload("${ctx}/editInvMainIn?ioId=${invMain.ioId}&popup=true",{dialogId:'editInvMainIn'});
					formCallBack(data);
				});
			}
		});
	}
	function confirmInvMainIn(){
		if("${invMain.status}"=="0"){
			alertMsg.error("该单据尚未审核，不能记账！。");
			return;
		}
		var editUrl = "${ctx}/invMainInConfirm?id=${invMain.ioId}&navTabId=invMainIn_gridtable";
		alertMsg.confirm("确认记账？", {
			okCall : function() {
				jQuery.post(editUrl, function(data) {
					$.pdialog.reload("${ctx}/editInvMainIn?ioId=${invMain.ioId}&popup=true",{dialogId:'editInvMainIn'});
					formCallBack(data);
				});
			}
		});
	}
	function printInvMainIn(){
		/* var invMainInCardHtml = jQuery("#invMainInCard").html();
		jQuery("body").append("<div id='page1'>"+invMainInCardHtml+"</div>");
		jQuery("#invMainInTable_head",'#page1').css('width','99%');
		jQuery("#invMainInTable",'#page1').css('width','99%');
		jQuery("#invMainInTable_foot",'#page1').css('width','99%');
		jQuery("#invMainInTable_div",'#page1').css('width','auto');
		jQuery("#invMainInTable_div",'#page1').css('height','auto');
		doPrint(); */
	} 
	function invMainInClose(){
		var gridEdited = jQuery("#${random}_invMainInTable")[0].p.gridEdited;
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
	var fromOrderData;
	function importOrderToInvMainIn(){
		var store_id = jQuery("#${random}_invMainIn_store_id").val();
		if(!store_id){
			alertMsg.error("请选择仓库。");
			return;
		}
		var url = "${ctx}/orderMainAndDetailList?random=${random}";
		var importOrderMap = new Map();
		importOrderMap.put("storeId",store_id);
		var procTypeId = jQuery("#${random}_invMainInForm").find("select[name='invMain.proctypeCode.typeId']").find("option:selected").val();
		if(procTypeId){
			importOrderMap.put("procTypeId",procTypeId);
		}
		var buyDeptName = jQuery("#${random}_invMainIn_buyDept").val();
		var buyDeptId = jQuery("#${random}_invMainIn_buyDept_id").val();
		 if(buyDeptId){
			importOrderMap.put("buyDeptId",buyDeptId);
			importOrderMap.put("buyDeptName",buyDeptName);
		}
		var buyPersonName = jQuery("#${random}_invMainIn_buyPerson").val();
		var buyPersonId = jQuery("#${random}_invMainIn_buyPerson_id").val();
		if(buyPersonId){
			importOrderMap.put("buyPersonId",buyPersonId);
			importOrderMap.put("buyPersonName",buyPersonName);
		}
		var vendorName = jQuery("#${random}_invMainIn_vendorName").val();
		var vendorId = jQuery("#${random}_invMainIn_vendorName_id").val();
		if(vendorId){
			importOrderMap.put("vendorId",vendorId);
			importOrderMap.put("vendorName",vendorName);
		}
		var orderNo = jQuery("#${random}_invMainIn_orderNo").val();
		if(orderNo){
			importOrderMap.put("orderNo",orderNo);
		}
		var json = JSON.stringify(importOrderMap);
		url += "&importOrderCons="+json; 
		url = encodeURI(url);
		$.pdialog.open(url, 'importOrderToInvMainIn', '订单引入', {mask:true,width:width,height:height});
	}
</script>

<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar" id="${random}_invMainIn_toolBar">
				<li>
					<a id="${random}_invMainIn_new" class="addbutton" href="javaScript:newInvMainIn()"><span><s:text name="button.newDoc" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainIn_chooseInvDetail" class="getdatabutton" href="javaScript:importDictToInvMainIn()"><span>选择材料</span></a>
				</li>
				<li>
					<a id="${random}_invMainIn_import_order" class="importbutton" href="javaScript:importOrderToInvMainIn()"><span>订单引入</span></a>
				</li>
				<li>
					<a id="${random}_invMainInSave" class="savebutton" href="javaScript:saveInvMainIn()"><span><s:text name="button.save" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainInAudit" class="checkbutton" href="javaScript:auditInvMainIn()"><span><s:text name="button.check" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainInAntiAudit" class="delallbutton" href="javaScript:antiAuditMainIn()"><span><s:text name="button.cancelCheck" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainInConfirm" class="confirmbutton" href="javaScript:confirmInvMainIn()"><span>记账</span> </a>
				</li>
				<li>
					<a id="${random}_invMainInPrint" class="printbutton" href="javaScript:printInvMainIn()"><span><s:text name="button.print" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainInPrintSet" class="settingbutton" href="javaScript:printSetInvMainIn()"><span><s:text name="button.printSet" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainInSetColShow" class="settlebutton"  href="javaScript:setColShow('${random}_invMainInTable','com.huge.ihos.material.model.InvMainInDetail','zhGrid')"><span><s:text name="button.setColShow" /></span></a>
				</li>
				<li>
					<a id="${random}_invMainInClose" class="closebutton" href="javaScript:invMainInClose()"><span><s:text name="button.close" /></span> </a>
				</li>
			</ul>
		</div>
		<div id="${random}_invMainInCard" autoBreakPage='*' style="margin:5px 10px 5px 5px">
			<form id="${random}_invMainInForm">
				<s:if test="%{!entityIsNew}">
					<span style="position:absolute;right:14px;font-size:18px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px">No.${invMain.ioBillNumber}</span>
				</s:if>
				<div align="center" style="font-size:25px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px;margin:0 auto;">
				    ${docTemp.title}
					<hr style="color:rgb(24, 127, 207);margin-top:0px"/>
					<hr style="color:rgb(24, 127, 207);margin-top:-11px"/>
				</div>
				<div>
					<s:hidden key="invMain.ioId" />
					<s:hidden key="invMain.ioBillNumber" />
					<s:hidden key="invMain.orgCode" />
					<s:hidden key="invMain.copyCode" />
					<s:hidden key="invMain.ioType" />
					<s:hidden key="invMain.yearMonth" />
					<s:hidden key="invMain.docTemId" />
					<s:hidden key="invMain.status" />
					<s:hidden key="invMain.makePerson.personId" />
				</div>
				<div style="border:0;width:100%;margin-top:-10px;table-layout:fixed;">
					<c:forEach items="${docTemp.inputList}" var="input">
						<span class="docInputArea" id="${random}_${input.value}_${input.necessary}">&nbsp;&nbsp;&nbsp;
							<c:set var="inputType" value="${input.type}" scope="page"/> 
							<c:set var="inputKey" value="${input.value}" scope="page"/>
							<c:set var="referId" value="${input.referId}" scope="page"/>
							<c:set var="referName" value="${input.referName}" scope="page"/>
							<c:if test="${inputKey!='proctypeCode'}">
								<label><c:out value="${input.name}"/>:</label>
							</c:if>
							<c:if test="${inputType=='refer'}" >
								<input type="text" id="${random}_invMainIn_${inputKey}" name="invMain.${inputKey}.${referName}" value="${invMain[inputKey][referName]}" />
								<input type="hidden" id="${random}_invMainIn_${inputKey}_id" name="invMain.${inputKey}.${referId}" value="${invMain[inputKey][referId]}"/>
							</c:if>
							<c:if test="${inputType=='date'}" >
								<c:if test="${inputKey=='makeDate'}">
									<input type="text" id="${random}_invMainIn_${inputKey}" name="invMain.${inputKey}" value="<fmt:formatDate value='${invMain[inputKey]}' pattern='yyyy-MM-dd' />" class="Wdate"  style="height:15px;width:80px" />
								</c:if>
								<c:if test="${inputKey=='invoiceDate'}">
									<input type="text" id="${random}_invMainIn_${inputKey}" name="invMain.${inputKey}" value="<fmt:formatDate value='${invMain[inputKey]}' pattern='yyyy-MM-dd' />" class="Wdate"  style="height:15px;width:80px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"/>
								</c:if>
							</c:if>
							<c:if test="${inputType=='simple'}" >
								<c:choose>
									<c:when test="${inputKey=='vendorName'}">
										<input type="text" id="${random}_invMainIn_${inputKey}" name="invMain.${inputKey}" value="${invMain[inputKey]}"
										 	onblur="setDefaultValue(this,jQuery('#${random}_invMainIn_vendorName_id'))" 
											onfocus="clearInput(this,jQuery('#${random}_invMainIn_vendorName_id'))"/>
										<input type="hidden" id="${random}_invMainIn_${inputKey}_id" name="invMain.vendorId" value="${invMain.vendorId}"/>
									</c:when>
									<c:otherwise>
										<input type="text" id="${random}_invMainIn_${inputKey}" name="invMain.${inputKey}" value="${invMain[inputKey]}" />
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${inputType=='select'}" >
								<s:if test="%{invMain.status==0}">
									<s:select key="invMain.proctypeCode" name="invMain.proctypeCode.typeId" value="%{invMain.proctypeCode.typeId}" 
										list="procTypeList" listKey="typeId" listValue="typeName" emptyOption="true" style="width:90px"></s:select>
								</s:if>
								<s:else>
									<label><c:out value="${input.name}"/>:</label>
									<input type="text" id="${random}_invMainIn_${inputKey}" name="invMain.${inputKey}.typeName" value="${invMain[inputKey].typeName}" />
									<input type="hidden" id="${random}_invMainIn_${inputKey}_id" name="invMain.${inputKey}.typeId" value="${invMain[inputKey].typeId}"/>
								</s:else>
							</c:if>
							<script>
								if("${invMain.status}"=="0" && ${input.necessary}){
									jQuery("#${random}_${input.value}_${input.necessary} input[type='text']").addClass("required");
								}
							</script>
						</span>
					</c:forEach>
				</div>
			</form>
			<div id="${random}_invMainInTable_div" class="zhGrid_div">
				<table id="${random}_invMainInTable"></table>
			</div>
			<div style="height:26px" id="${random}_invMainInForm_foot">
				<ul style="float:left;width:100%;height:20px;margin-top:8px;text-align:center">
					<c:forEach items="${docTemp.footMap}" var="foot">
						<li style="float:left;">
							<label><c:out value="${foot.value}"/>:</label>
							<c:if test="${foot.key!='sign'}">
									<c:set var="footKey" value="${foot.key}" scope="page"/>
									<input class="lineInput" size="8" value="${invMain[footKey].name}" readonly="readonly"/>
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