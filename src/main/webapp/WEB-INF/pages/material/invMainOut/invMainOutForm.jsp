
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function invDictOutComboGrid(elem) {
		var elemId = jQuery(elem).attr("id");
		elemId=elemId.replace(".","\.");
		jQuery(elem).attr("id",elemId);
		$(elem).combogrid({
			url : '${ctx}/comboGridSqlList',
			queryParams : {
				sql:"select invId,invCode,invName,invModel,firstUnit,batchId,batchNo,productionDate,price,curAmount "+
				    "from v_mm_invOutStore where curAmount > 0 "+
				    "and storeId='"+$("#${random}_invMainOut_store_id").val()+"' "+
				    "and orgCode='"+$('#${random}_invMainOut_orgCode').val()+"' "+
				    "and copyCode='"+$('#${random}_invMainOut_copyCode').val()+"' "+
				    "and yearMonth='"+$('#${random}_invMainOut_yearMonth').val()+"'",
				cloumns : ' invName,invCode,cncode'
			},
			autoFocus : false,
			showOn : false,
			rows:10,
			width:600,
			sidx:"invCode,productionDate,batchNo",
			colModel : [ {
				'columnName' : 'INVID',
				'width' : '0',
				'label' : '<s:text name="inventoryDict.invId"/>',
				hidden : true,
			}, {
				'columnName' : 'INVCODE',
				'width' : '15',
				'label' : '<s:text name="inventoryDict.invCode"/>'
			}, {
				'columnName' : 'INVNAME',
				'width' : '20',
				'label' : '<s:text name="inventoryDict.invName"/>'
			}, {
				'columnName' : 'INVMODEL',
				'width' : '15',
				'label' : '<s:text name="inventoryDict.invModel"/>'
			}, {
				'columnName' : 'FIRSTUNIT',
				'width' : '10',
				'label' : '<s:text name="inventoryDict.firstUnit"/>'
			}, {
				'columnName' : 'BATCHNO',
				'width' : '20',
				'label' : '<s:text name="invDetail.batchNo"/>'
			}, {
				'columnName' : 'CURAMOUNT',
				'width' : '10',
				'label' : '<s:text name="inventoryDict.currentStock"/>'
			}, {
				'columnName' : 'PRICE',
				'width' : '10',
				'label' : '单价'
			}
	
			],
			_create: function( event, item ) {
				//alert();
			},
			select : function(event, ui) {
				//$(elem).attr("value",ui.item.INVNAME);
				var thisTr = jQuery("#${random}_invMainOutTable").getTr($(elem));
				var rowid = thisTr.attr("id");
				jQuery("#${random}_invMainOutTable").setCellData(rowid,'invDict.invName',ui.item.INVNAME);
				jQuery("#${random}_invMainOutTable").setCellData(rowid,'invDict.invId',ui.item.INVID);
				jQuery("#${random}_invMainOutTable").setCellData(rowid,'invDict.invCode',ui.item.INVCODE);
				jQuery("#${random}_invMainOutTable").setCellData(rowid,'invDict.invModel',ui.item.INVMODEL);
				jQuery("#${random}_invMainOutTable").setCellData(rowid,'invDict.firstUnit',ui.item.FIRSTUNIT);
				jQuery("#${random}_invMainOutTable").setCellData(rowid,'currentStock',ui.item.CURAMOUNT);
				jQuery("#${random}_invMainOutTable").setCellData(rowid,'invBatch.batchNo',ui.item.BATCHNO);
				jQuery("#${random}_invMainOutTable").setCellData(rowid,'invBatch.id',ui.item.BATCHID);
				jQuery("#${random}_invMainOutTable").setCellData(rowid,'inPrice',ui.item.PRICE);
				jQuery("#${random}_invMainOutTable").setCellData(rowid,'inPrice',ui.item.PRICE);
				thisTr.attr("used","true");
				return false;
			}
		});
	}
	function invMainOutApplyPersonTreeSelect(){
		setTimeout(function(){
			var sql = "SELECT p.personId id ,p.name name FROM t_person p where p.dept_id='"+$("#${random}_invMainOut_applyDept_id").val()+"'";
			 jQuery("#${random}_invMainOut_applyPersion").treeselect({
				dataType:"sql",
				optType:"single",
				sql:sql,
				exceptnullparent:false,
				lazy:false
			}); 
		},50);
	}
	
	function getInvMainOutCurAmount(invDictId,batchId){
		var curAmount = 0;
		jQuery.ajax({
			url:'getInvMainOutCurAmount?invDictId='+invDictId+'&batchId='+batchId+'&storeId='+$('#${random}_invMainOut_store_id').val(),
			type:'get',
			dataType:'json',
			async:false,
			success:function(data){
				curAmount = data.curAmount;	
			}
		});	
		return curAmount;
	}
</script>
<script type="text/javascript">
	var iodt = docTemplate("${docTemp.listMap}","${docTemp.title}","${fn:length(docTemp.inputNames)}","${fn:length(docTemp.footNames)}");
	jQuery(document).ready(function() {
		iodt.setDocLayOut("${random}_invMainOutForm","${random}_invMainOutTable_div","${random}_invMainOutForm_foot");
		if("${docPreview}"=="preview"){
			iodt.disableButton("${random}_invMainOut_toolBar");
			iodt.clearData("${random}_invMainOutCard");
		}
		var invMainOutId = "${invMain.ioId}";
		if(invMainOutId==null || invMainOutId==""){
			invMainOutId = "new";
		}
		var url = "invDetailGridList?filter_EQS_invMain.ioId="+invMainOutId;
		var saveUrl = "${ctx}/saveInvMainOut?navTabId=invMainOut_gridtable&entityIsNew=${entityIsNew}&saveType=saveStay&docType=出库单";
		if("${docPreview}"=="deptAppOut"){
			url += "&docPreview=deptAppOut&deptAppId=${deptAppId}&deptAppDetailIds=${deptAppDetailIds}";
			saveUrl += "&saveFrom=deptAppOut&deptAppId=${deptAppId}&deptAppDetailIds=${deptAppDetailIds}"
		}
		var zhGridSetting_invMainOut={
			url : url,
			datatype : "json",
			mtype : "GET",
			formId:'${random}_invMainOutForm',
			paramName:'invDetailJson',
			saveUrl:saveUrl,
			initColumn:"com.huge.ihos.material.model.InvMainOutDetail",
			afterSaveFun : function(data) {
				if("saveStay" == data.callbackType){
					$.pdialog.closeCurrent();
					$.pdialog.open("${ctx}/editInvMainOut?docType=出库单&ioId="+data.forwardUrl,"showInvMainOut","出库单明细", {mask:true,width:width,height:height});
				}
			},			
			colModel : [
				{name:'invDetialId',index : 'invDetialId',align : 'center',label : '<s:text name="invDetail.invDetialId" />',hidden : true,key : true,editable : false,sortable:false},
				{name:'invDict.invId',index : 'invDict_invId',align : 'left',label : '<s:text name="inventoryDict.invId" />',hidden : true,editable : true,edittype : 'text',sortable:false},
				{name:'invDict.invName',index : 'invDict_invName',align : 'left',width:150,label : iodt.getListName("invDict.invName"),hidden : !(iodt.containColumn("invDict.invName")),highsearch : iodt.containColumn("invDict.invName"),
					editable : true,edittype : 'text',editoptions : {dataInit : invDictOutComboGrid},sortable:false},
				{name:'invDict.invCode',index : 'invDict_invCode',align : 'left',width:80,label : iodt.getListName("invDict.invCode"),hidden : !(iodt.containColumn("invDict.invCode")),highsearch : iodt.containColumn("invDict.invCode"),sortable:false},
				{name:'invDict.invModel',index : 'invDict_invModel',align : 'left',width:120,label : iodt.getListName("invDict.invModel"),hidden : !(iodt.containColumn("invDict.invModel")),highsearch : iodt.containColumn("invDict.invModel"),sortable:false},
				{name:'invDict.firstUnit',index : 'invDict_firstUnit',align : 'center',width:80,label : iodt.getListName("invDict.firstUnit"),hidden : !(iodt.containColumn("invDict.firstUnit")),highsearch : iodt.containColumn("invDict.firstUnit"),sortable:false},
				{name:'inAmount',index : 'inAmount',align : 'right',width:100,label : iodt.getListName("inAmount"),hidden : !(iodt.containColumn("inAmount")),highsearch : iodt.containColumn("inAmount"),formatter : 'number',editable : true,
					editrules : {integer : true,required : true}
				},
				{name : 'inPrice',index : 'inPrice',align : 'right',width:100,label : iodt.getListName("inPrice"),hidden : !(iodt.containColumn("inPrice")),highsearch : iodt.containColumn("inPrice"),formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces: 2},editable : false,
					editrules : {number : true,required : true}
				},
				{name : 'inMoney',index : 'inMoney',align : 'right',width:110,label : iodt.getListName("inMoney"),hidden : !(iodt.containColumn("inMoney")),highsearch : iodt.containColumn("inMoney"),formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces: 2},editable : false,
					editrules : {number : true,required : true}
				},
				{name:'invBatch.batchNo',index : 'invDict_batchNo',align : 'left',	width:120,label : iodt.getListName("invBatch.batchNo"),hidden : !(iodt.containColumn("invBatch.batchNo")),highsearch : iodt.containColumn("invBatch.batchNo"),sortable:false},
				{name:'invBatch.id',index : 'invBatch_id',align : 'left',	label : '<s:text name="invBatch.id" />',hidden :true,sortable:false},
				{name : 'snCode',index : 'snCode',align : 'left',width:120,label : iodt.getListName("snCode"),hidden : !(iodt.containColumn("snCode")),highsearch : iodt.containColumn("snCode"),editable : true}
			],
			jsonReader : {
				root : "invDetails", // (2)
				//page : "page",
				//total : "total",
				//records : "records", // (3)
				repeatitems : false
			},
			sortname : 'invDict.invCode',
			viewrecords : true,
			sortorder : 'desc',
			height : 200,
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
				 jQuery("#${random}_invMainOutTable").footData([
  				       {name:'invDict.invName',value:'合计:'},
  				       {name:'inMoney',value:'sum'}
   				 ]);
			},
			beforeEditCell : function(rowid, cellname,value, iRow, iCol) {
				var store_id = jQuery("#${random}_invMainOut_store_id").val();
				if(store_id == '' || store_id=='null'){
					alertMsg.error("请选择目标仓库。");
					return;
				}  
			}, 
			afterEditCell : function(rowid, cellname,value, iRow, iCol) {
				   if(cellname ==='inAmount'){
					var amount = value*1;
					var invDictId= jQuery("#${random}_invMainOutTable").getCellData(rowid,'invDict.invId');
					var batchId= jQuery("#${random}_invMainOutTable").getCellData(rowid,'invBatch.id');
					var currentStock = getInvMainOutCurAmount(invDictId,batchId);
					if(amount > currentStock){
						jQuery("#${random}_invMainOutTable").setCellData(rowid,'inAmount',0);
						alertMsg.error("出库数量大于当前库存, 请重新输入");
						return "0";
					}
				} 
				if(cellname==='inPrice' || cellname==='inAmount'){
					var price= jQuery("#${random}_invMainOutTable").getCellData(rowid,'inPrice');
					 var amount= jQuery("#${random}_invMainOutTable").getCellData(rowid,'inAmount');
					 price = isNaN(parseFloat(price))?0:parseFloat(price);
					 amount = isNaN(parseFloat(amount))?0:parseFloat(amount);
	                jQuery("#${random}_invMainOutTable").setCellData(rowid,'inMoney',price*amount);
				}
			},
			
			afterSaveCell : function(rowid, cellname,value, iRow, iCol) {
			}
			
		}
		jQuery("#${random}_invMainOutTable").zhGrid(zhGridSetting_invMainOut);
	if("${docPreview}"!="preview"){
		if("${docPreview}"=="refer"){
			disableLink(["${random}_invMainOut_new","${random}_invMainOut_chooseInvDetail","${random}_invMainOutSave","${random}_invMainOutAudit","${random}_invMainOutAntiAudit","${random}_invMainOutConfirm","${random}_invMainOutAudit","${random}_invMainOutPrint","${random}_invMainOutPrintSet"]);
			clearInputClassInInvMainOut();
		}else{
			if(${entityIsNew}){
				disableLink(["${random}_invMainOutAudit"]);
			}
			if("${invMain.status}"=="0"){
				jQuery("#${random}_invMainOutTable").fullEdit();
				disableLink(["${random}_invMainOut_new","${random}_invMainOutPrint","${random}_invMainOutPrintSet","${random}_invMainOutAntiAudit","${random}_invMainOutConfirm"]);
				jQuery("#${random}_invMainOut_busType").treeselect({//业务类型
					dataType : "sql",
					optType : "single",
					sql : "SELECT TYPECODE id, TYPENAME name FROM MM_BUSINESSTYPE WHERE DISABLED=0 AND INOUT = '2' ",
					exceptnullparent : false,
					lazy : false
				});
				jQuery("#${random}_invMainOut_store").treeselect({
					dataType : "sql",
					optType : "single",
					sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where is_book = 1 and is_lock = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
					exceptnullparent : false,
					lazy : false,
					callback : {
						afterClick : function() {
							//alert(jQuery("#invMainOut_store_id").val());		
						}
					}
				});
				jQuery("#${random}_invMainOut_applyDept").treeselect({
					dataType:"sql",
					optType:"single",
					sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
					exceptnullparent:false,
					lazy:false,
					callback : {
						afterClick : function() {
							$("#${random}_invMainOut_usedDept").val($("#${random}_invMainOut_applyDept").val());
							$("#${random}_invMainOut_usedDept_id").val($("#${random}_invMainOut_applyDept_id").val());
							$("#${random}_invMainOut_applyPersion_id").val("");
							$("#${random}_invMainOut_applyPersion").val("");
							invMainOutApplyPersonTreeSelect();
						}
					}
				});
				jQuery("#${random}_invMainOut_usedDept").treeselect({
					dataType : "sql",
					optType : "single",
					sql : "SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
					exceptnullparent : false,
					lazy : false
				});
				if(jQuery("#${random}_invMainOut_applyDept").val()){
					invMainOutApplyPersonTreeSelect();
				}
				jQuery('#${random}_invMainOut_makeDate').unbind( 'click' ).bind("click",function(){
					var storeId = jQuery("#${random}_invMainOut_store_id").val();
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
			}else{
				if("${invMain.status}"=="1"){
					disableLink(["${random}_invMainOutPrint","${random}_invMainOutPrintSet","${random}_invMainOutAudit","${random}_invMainOutSave","${random}_invMainOut_chooseInvDetail"]);
				}else{
					disableLink(["${random}_invMainOutAntiAudit","${random}_invMainOutSave","${random}_invMainOutAudit","${random}_invMainOutConfirm","${random}_invMainOut_chooseInvDetail"]);
				}
				clearInputClassInInvMainOut();
			}
		}
		}
	});
	function clearInputClassInInvMainOut(){
		jQuery("#${random}_invMainOut_busType").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"true");
    	jQuery("#${random}_invMainOut_store").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"true");
    	jQuery("#${random}_invMainOut_applyDept").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"true");
    	jQuery("#${random}_invMainOut_applyPersion").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"true");
    	jQuery("#${random}_invMainOut_makeDate").removeClass('Wdate').removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
    	
    	jQuery("#${random}_invMainOut_allotCompany").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
    	jQuery("#${random}_invMainOut_projId").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"true");
    	jQuery("#${random}_invMainOut_usedDept").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"true");
    	jQuery("#${random}_invMainOut_remark").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
	 
	}
</script>
<script type="text/javascript">
	function newInvMainOut(){
		$.pdialog.closeCurrent();
		$.pdialog.open('${ctx}/editInvMainOut?docType=出库单', 'addInvMainOut', '添加出库单', {mask:true,width:width,height:height});　
	}
	function importDictToInvMainOut(){
		var store_id = jQuery("#${random}_invMainOut_store_id").val();
		if(store_id == '' || store_id=='null'){
			alertMsg.error("请选择目标仓库。");
			return;
		} 
		var url = "${ctx}/batchAddOutInvs?popup=true&storeId="+store_id+"&random="+${random};
		var winTitle="<s:text name='材料选择'/>";
		url = encodeURI(url);
		$.pdialog.open(url, 'importDictToInvMainOut', winTitle, {mask:true,width:width,height:height,resizable:false,maxable:false});
	
	}
	function saveInvMainOut(){
		var hasNull = false;
		var errorMsg = "";
		jQuery(".required").each(function(){
			if(!$(this).val()){
				errorMsg += $(this).prev().text()+",";
				hasNull = true;
			}
		});
		//errorMsg=errorMsg.replaceAll(":",",");
		if(hasNull){
			alertMsg.error(errorMsg+"不能为空！");
			return;
		}
		jQuery("#${random}_invMainOutTable").saveGrid();
	}
	function auditInvMainOut(){
		var gridEdited = jQuery("#${random}_invMainOutTable")[0].p.gridEdited;
		if(gridEdited){
			alertMsg.error("单据内容有修改，请先保存！");
			return ;
			
		}
		var editUrl = "${ctx}/invMainOutAudit?id=${invMain.ioId}&navTabId=invMainOut_gridtable";
		alertMsg.confirm("确认审核？", {
			okCall : function() {
				jQuery.post(editUrl, function(data) {
					$.pdialog.reload("${ctx}/editInvMainOut?docType=出库单&ioId=${invMain.ioId}&popup=true",{dialogId:'showInvMainOut'});
					formCallBack(data);
				});
			}
		});
	}
	function antiAuditMainOut(){
		var editUrl = "${ctx}/invMainOutAuditNot?id=${invMain.ioId}&navTabId=invMainOut_gridtable";
		alertMsg.confirm("确认销审？", {
			okCall : function() {
				jQuery.post(editUrl, function(data) {
					$.pdialog.reload("${ctx}/editInvMainOut?docType=出库单&ioId=${invMain.ioId}&popup=true",{dialogId:'showInvMainOut'});
					formCallBack(data);
				});
			}
		});
	}
	function confirmInvMainOut(){
		if("${invMain.status}"=="0"){
			alertMsg.error("该单据尚未审核，不能记账！。");
			return;
		}
		var editUrl = "${ctx}/invMainOutConfirm?id=${invMain.ioId}&navTabId=invMainOut_gridtable";
		alertMsg.confirm("确认记账？", {
			okCall : function() {
				jQuery.post(editUrl, function(data) {
					$.pdialog.reload("${ctx}/editInvMainOut?docType=出库单&ioId=${invMain.ioId}&popup=true",{dialogId:'showInvMainOut'});
					formCallBack(data);
				});
			}
		});
	}
	function printInvMainOut(){
		/* var invMainOutCardHtml = jQuery("#invMainOutCard").html();
		jQuery("body").append("<div id='page1'>"+invMainOutCardHtml+"</div>");
		jQuery("#invMainOutTable_head",'#page1').css('width','99%');
		jQuery("#invMainOutTable",'#page1').css('width','99%');
		jQuery("#invMainOutTable_foot",'#page1').css('width','99%');
		jQuery("#invMainOutTable_div",'#page1').css('width','auto');
		jQuery("#invMainOutTable_div",'#page1').css('height','auto');
		doPrint(); */
	}
	function invMainOutClose(){
		var gridEdited = jQuery("#${random}_invMainOutTable")[0].p.gridEdited;
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
</script>

<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar" id="${random}_invMainOut_toolBar">
				<li>
					<a id="${random}_invMainOut_new" class="addbutton" href="javaScript:newInvMainOut()"><span><s:text name="button.newDoc" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainOut_chooseInvDetail" class="getdatabutton" href="javaScript:importDictToInvMainOut()"><span>选择材料</span></a>
				</li>
				<li>
					<a id="${random}_invMainOutSave" class="savebutton" href="javaScript:saveInvMainOut()"><span><s:text name="button.save" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainOutAudit" class="checkbutton" href="javaScript:auditInvMainOut()"><span><s:text name="button.check" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainOutAntiAudit" class="delallbutton" href="javaScript:antiAuditMainOut()"><span><s:text name="button.cancelCheck" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainOutConfirm" class="confirmbutton" href="javaScript:confirmInvMainOut()"><span>记账</span> </a>
				</li>
				<li>
					<a id="${random}_invMainOutPrint" class="printbutton" href="javaScript:printInvMainOut()"><span><s:text name="button.print" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainOutPrintSet" class="settingbutton" href="javaScript:printSetInvMainOut()"><span><s:text name="button.printSet" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainOutSetColShow" class="settlebutton"  href="javaScript:setColShow('${random}_invMainOutTable','com.huge.ihos.material.model.InvMainOutDetail','zhGrid')"><span><s:text name="button.setColShow" /></span></a>
				</li>
				<li>
					<a id="${random}_invMainOutClose" class="closebutton" href="javaScript:invMainOutClose()"><span><s:text name="button.close" /></span> </a>
				</li>
			</ul>
		</div>
		<div id="${random}_invMainOutCard" autoBreakPage='*' style="margin:5px 10px 5px 5px">
			<form id="${random}_invMainOutForm">
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
					<s:hidden key="invMain.ioType" />
					<s:hidden key="invMain.docTemId" />
					<s:hidden key="invMain.status" />
					<s:hidden key="invMain.makePerson.personId" />
					<input type="hidden" id="${random}_invMainOut_orgCode" name="invMain.orgCode" value="${invMain.orgCode}"/>
					<input type="hidden" id="${random}_invMainOut_copyCode" name="invMain.copyCode" value="${invMain.copyCode}"/>
					<input type="hidden" id="${random}_invMainOut_yearMonth" name="invMain.yearMonth" value="${invMain.yearMonth}"/>
				</div>
				<div style="border:0;width:100%;margin-top:-10px;table-layout:fixed;">	
					<c:forEach items="${docTemp.inputList}" var="input">
						<span class="docInputArea" id="${random}_${input.value}_${input.necessary}">&nbsp;&nbsp;&nbsp;
							<label><c:out value="${input.name}"/>:</label>
							<c:set var="inputType" value="${input.type}" scope="page"/> 
							<c:set var="inputKey" value="${input.value}" scope="page"/>
							<c:set var="referId" value="${input.referId}" scope="page"/>
							<c:set var="referName" value="${input.referName}" scope="page"/>
							<c:if test="${inputType=='refer'}" >
								<input type="text" id="${random}_invMainOut_${inputKey}" name="invMain.${inputKey}.${referName}" value="${invMain[inputKey][referName]}"/>
								<input type="hidden" id="${random}_invMainOut_${inputKey}_id" name="invMain.${inputKey}.${referId}" value="${invMain[inputKey][referId]}"/>
							</c:if>
							<c:if test="${inputType=='date'}" >
								<input type="text" id="${random}_invMainOut_${inputKey}" name="invMain.${inputKey}" value="<fmt:formatDate value='${invMain[inputKey]}' pattern='yyyy-MM-dd' />" class="Wdate"  style="height:15px;width:80px"/>
							</c:if>
							<c:if test="${inputType=='simple'}" >
								<input type="text" id="${random}_invMainOut_${inputKey}" name="invMain.${inputKey}" value="${invMain[inputKey]}" />
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
			<div id="${random}_invMainOutTable_div" class="zhGrid_div">
				<table id="${random}_invMainOutTable"></table>
			</div>
			<div style="height:26px" id="${random}_invMainOutForm_foot">
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