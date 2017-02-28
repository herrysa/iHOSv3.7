
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function newInvMain(){
		$.pdialog.closeCurrent();
		$.pdialog.open("${ctx}/editInvMainInit","addInvMainInit","新建期初入库", {mask:true,width:width,height:height});
	}
	function saveInvmainInit(){
		jQuery("#${random}_invMainInitTable").saveGrid();
	}
	function invMainInitConfirm(){
		var gridEdited = jQuery("#${random}_invMainInitTable")[0].p.gridEdited;
		if(gridEdited){
			alertMsg.error("单据内容有修改，请先保存！");
			return ;
			
		}
		var url = "${ctx}/invMainInitConfirm?id=${invMain.ioId}&navTabId=invMainInit_gridtable";
		alertMsg.confirm("确认记账？", {
			okCall : function() {
				jQuery.post(url, function(data) {
					$.pdialog.reload("${ctx}/editInvMainInit?ioId=${invMain.ioId}&popup=true",{dialogId:'showInvMainInit'});
					formCallBack(data);
				});
			}
		});
	}

	function invMainInitClose(){
		var gridEdited = jQuery("#${random}_invMainInitTable")[0].p.gridEdited;
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
	function invMainInitPrint(){
		/* var invMainInitCardHtml = jQuery("#invMainInitCard").html();
		jQuery("body").append("<div id='page1'>"+invMainInitCardHtml+"</div>");
		jQuery("#invMainInitTable_head",'#page1').css('width','99%');
		jQuery("#invMainInitTable",'#page1').css('width','99%');
		jQuery("#invMainInitTable_foot",'#page1').css('width','99%');
		jQuery("#invMainInitTable_div",'#page1').css('width','auto');
		jQuery("#invMainInitTable_div",'#page1').css('height','auto');
		doPrint(); */
		//jQuery("#page1").remove();
	}
	function invDictComboGrid(elem) {
		var elemId = jQuery(elem).attr("id");
		elemId=elemId.replace(".","\.");
		jQuery(elem).attr("id",elemId);
		$(elem).combogrid({
			url : '${ctx}/comboGridSqlList',
			queryParams : {
				sql : "select invId,invCode,invName,invModel,firstUnit,isNULL(refCost,0) refCost from v_mm_invInstore where storeId in ('"+jQuery("#${random}_invMainInit_store_id").val()+"','1') and orgCode='"+"${fns:userContextParam('orgCode')}"+"' and copyCode='"+"${fns:userContextParam('copyCode')}"+"'",
				cloumns : 'invName,invCode,cncode'
			},
			autoFocus : false,
			showOn : false, 
			rows:10,
			width:440,
			sidx:"INVCODE",
			colModel : [ {
				'columnName' : 'INVID',
				'width' : '0',
				'label' : 'INVID',
				hidden : true,
			}, {
				'columnName' : 'INVCODE',
				'width' : '25',
				'align' : 'left',
				'label' : '材料编码'
			}, {
				'columnName' : 'INVNAME',
				'width' : '25',
				'align' : 'left',
				'label' : '材料名称'
			}, {
				'columnName' : 'INVMODEL',
				'width' : '20',
				'align' : 'left',
				'label' : '型号规格'
			}, {
				'columnName' : 'FIRSTUNIT',
				'width' : '15',
				'label' : '计量单位'
			}, {
				'columnName' : 'REFCOST',
				'width' : '15',
				'align' : 'right',
				'label' : '参考进价'
			}

			],
			_create: function( event, item ) {
				alert();
			},
			select : function(event, ui) {
				$(elem).attr("value",ui.item.INVNAME);
				var thisTr = jQuery("#${random}_invMainInitTable").getTr($(elem));
				jQuery("#${random}_invMainInitTable").formatterRow(thisTr.attr('id'));
				thisTr.find("input[name='invDict.invId']").val(ui.item.INVID).blur();
				thisTr.find("input[name='invDict.invCode']").val(ui.item.INVCODE).blur();
				thisTr.find("input[name='invDict.invModel']").val(ui.item.INVMODEL).blur();
				thisTr.find("input[name='invDict.firstUnit']").val(ui.item.FIRSTUNIT).blur();
				thisTr.find("input[name='inPrice']").val(ui.item.REFCOST).blur();
				thisTr.attr("used","true");
				return false;
			}
		});
	}
 	var iitdt = docTemplate("${docTemp.listMap}","${docTemp.title}","${fn:length(docTemp.inputNames)}","${fn:length(docTemp.footNames)}");
	jQuery(document).ready(function() {
		iitdt.setDocLayOut("${random}_invMainInitForm","${random}_invMainInitTable_div","${random}_invMainInitForm_foot");
		if("${docPreview}"=="preview"){
			iitdt.disableButton("${random}_invMainInit_toolBar");
			jQuery("#${random}_invMainInitCard input").val("");
		}else{
			if(${entityIsNew}){
				disableLink(["${random}_invMainInitConfirm"]);
			}
		    if("${invMain.status}"=="0"){
		    	jQuery("#${random}_invMainInit_store").treeselect({
					dataType : "sql",
					optType : "single",
					sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where parent_id is not null and disabled = 0 and is_Book = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
					exceptnullparent : false,
					lazy : false
				});
		    	jQuery('#${random}_invMainInit_makeDate').bind("click",function(){
		    		WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'});
		    	});	
		    	disableLink(["${random}_newInvMainInit","${random}_invMainInitPrint","${random}_invMainInitPrintSet"]);
		    }else{
		    	disableLink(["${random}_invMainInit_chooseInvDetail","${random}_invMainInitConfirm","${random}_invMainInitSave"]);
		    	jQuery("#${random}_invMainInit_busType").attr('readOnly',"true").removeClass('textInput').addClass('lineInput');
		    	jQuery("#${random}_invMainInit_store").attr('readOnly',"true").removeClass('textInput').removeClass('required').addClass('lineInput');
		    	jQuery("#${random}_invMainInit_makeDate").attr('readOnly',"true").removeClass('Wdate').removeClass('required');
		    	jQuery("#${random}_invMainInit_makeDate").removeClass('textInput').addClass('lineInput');
		    	jQuery("#${random}_invMainInit_remark").attr('readOnly',"true").removeClass('textInput').addClass('lineInput');
		    }
		}
		var invMainInitId = "${invMain.ioId}";
		if(invMainInitId==null || invMainInitId==""){
			invMainInitId = "new";
		}
		var zhGridSetting = {
			url : "invDetailGridList?filter_EQS_invMain.ioId="+invMainInitId,
			datatype : "json",
			mtype : "GET",
			trHeight:25,
			formId:'${random}_invMainInitForm',
			paramName:'invDetailJson',
			saveUrl:"${ctx}/saveInvMainInit?navTabId=invMainInit_gridtable&entityIsNew=${entityIsNew}&saveType=saveStay",
			initColumn:"com.huge.ihos.material.model.InvMainInitDetail",
			afterSaveFun : function(data) {
				if("saveStay" == data.callbackType){
					$.pdialog.closeCurrent();
					$.pdialog.open("${ctx}/editInvMainInit?ioId="+data.forwardUrl,"showInvMainInit","期初入库单明细", {mask:true,width:960,height:628});
				}
			},
			colModel : [
				{name : 'invDetialId',index : 'invDetialId',align : 'center',label : '<s:text name="invDetail.invDetialId" />',hidden : true,key : true,editable : false,sortable:false},
				{name : 'invDict.invId',index : 'invDict_invId',align : 'left',label : '<s:text name="inventoryDict.invId" />',hidden : true,editable : true,edittype : 'text',sortable:false},
				{name : 'invDict.invName',index : 'invDict_invName',align : 'left',width : 100,label : iitdt.getListName("invDict.invName"),hidden :!(iitdt.containColumn("invDict.invName")),editable : true,edittype : 'text',editoptions : {dataInit : invDictComboGrid},sortable:false,highsearch : iitdt.containColumn("invDict.invName")},
				{name : 'invDict.invCode',index : 'invDict_invCode',align : 'left',width : 80,label : iitdt.getListName("invDict.invCode"),hidden :!(iitdt.containColumn("invDict.invCode")),sortable:false,highsearch : iitdt.containColumn("invDict.invCode")},
				{name : 'invDict.invModel',index : 'invDict_invModel',align : 'left',width : 80,label : iitdt.getListName("invDict.invModel"),hidden : !(iitdt.containColumn("invDict.invModel")),sortable:false,highsearch : iitdt.containColumn("invDict.invModel")},
				{name : 'invDict.firstUnit',index : 'invDict_firstUnit',align : 'center',width : 60,label : iitdt.getListName("invDict.firstUnit"),hidden : !(iitdt.containColumn("invDict.firstUnit")),sortable:false,highsearch : iitdt.containColumn("invDict.firstUnit")},
				{name : 'inAmount',index : 'inAmount',align : 'right',width : 100,label : iitdt.getListName("inAmount"),hidden : !(iitdt.containColumn("inAmount")),formatter : 'number',editable : true,highsearch : iitdt.containColumn("inAmount")},
				{name : 'inPrice',index : 'inPrice',align : 'right',width : 100,label : iitdt.getListName("inPrice"),hidden : !(iitdt.containColumn("inPrice")),formatter : 'currency',formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},editable : true,editrules : {number : true,required : true},highsearch : iitdt.containColumn("inPrice")},
				{name : 'inMoney',index : 'inMoney',align : 'right',width : 110,label : iitdt.getListName("inMoney"),hidden : !(iitdt.containColumn("inMoney")),formatter : 'currency',formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},highsearch : iitdt.containColumn("inMoney")},
				{name : 'addRate',index : 'addRate',align : 'right',width : 100,label : iitdt.getListName("addRate"),hidden : !(iitdt.containColumn("addRate")),formatter : 'number',formatoptions:{defaultValue: '0.00'},editable : true,editrules : {number : true,required : true},highsearch : iitdt.containColumn("addRate")},
				{name : 'salePrice',index : 'salePrice',align : 'right',width : 100,label : iitdt.getListName("salePrice"),hidden : !(iitdt.containColumn("salePrice")),formatter : 'currency',formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},editable : true,editrules : {number : true,required : false},highsearch : iitdt.containColumn("salePrice")},
				{name : 'invBatch.id',index : 'invBatch.id',align : 'left',label : '<s:text name="invBatch.id" />',hidden : true},
				{name : 'invBatch.batchNo',index : 'invBatch.batchNo',align : 'left',label : iitdt.getListName("invBatch.batchNo"),hidden : !(iitdt.containColumn("invBatch.batchNo")),width : 80,editable : true,edittype : 'text',sortable:false,highsearch : iitdt.containColumn("invBatch.batchNo")},
				{name : 'invDict.isQuality',index : 'invDict_isQuality',align : 'center',width : 110,label : iitdt.getListName("invDict.isQuality"),hidden : !(iitdt.containColumn("invDict.isQuality")),sortable:false,formatter : 'checkbox',editable : false,edittype : "checkbox",editoptions : {value : "true:false"},highsearch : iitdt.containColumn("invDict.isQuality")},
				{name : 'invBatch.validityDate',index : 'invBatch.validityDate',align : 'center',label : iitdt.getListName("invBatch.validityDate"),hidden : !(iitdt.containColumn("invBatch.validityDate")),width : '80',
						editable : true,formatter: 'date',formatoptions: {srcformat: 'Y-m-d H:i:s',newformat: 'Y-m-d'}, 
						editoptions : {dataInit :function(elem){jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
								jQuery('#ui-datepicker-div').css("z-index", 2000);}},highsearch : iitdt.containColumn("invBatch.validityDate")},
				{name : 'invDict.isBar',index : 'invDict_isBar',align : 'center',width : 80,label : iitdt.getListName("invDict.isBar"),hidden : !(iitdt.containColumn("invDict.isBar")),sortable:false,formatter : 'checkbox',editable : false,edittype : "checkbox",editoptions : {value : "true:false"},highsearch : iitdt.containColumn("invDict.isBar")},
				{name : 'snCode',index : 'snCode',align : 'left',width : 100,label : iitdt.getListName("snCode"),hidden : !(iitdt.containColumn("snCode")),editable : true,highsearch : iitdt.containColumn("snCode")},
				{name : 'isPerBar',index : 'isPerBar',align : 'center',width : 70,label : iitdt.getListName("isPerBar"),hidden : !(iitdt.containColumn("isPerBar")),formatter : 'checkbox',editable : true,edittype : "checkbox",editoptions : {value : "true:false"},highsearch : iitdt.containColumn("isPerBar")},
				{name : 'unitBarCode',index : 'unitBarCode',align : 'left',width : 100,label : iitdt.getListName("unitBarCode"),hidden : !(iitdt.containColumn("unitBarCode")),editable : true,highsearch : iitdt.containColumn("unitBarCode")}
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
				jQuery("#${random}_invMainInitTable").footData([{name:'invDict.invName',value:'合计:'},{name:'inMoney',value:'sum'}]);
			},
			beforeEditCell : function(rowid, cellname,value, iRow, iCol) {
				var store_id = jQuery("#${random}_invMainInit_store_id").val();
				if(store_id == '' || store_id=='null'){
					alertMsg.error("请选择目标仓库。");
					return;
				} 
			}, 
			afterEditCell : function(rowid, cellname,value, iRow, iCol) {
				if(cellname==='inPrice' || cellname==='inAmount'){
					var price= jQuery("#${random}_invMainInitTable").getCellData(rowid,'inPrice');
					 var amount= jQuery("#${random}_invMainInitTable").getCellData(rowid,'inAmount');
					 if(!price||!amount){
						 return ;
					 }
					 price = isNaN(parseFloat(price))?0:parseFloat(price);
					 amount = isNaN(parseFloat(amount))?0:parseFloat(amount);
	                jQuery("#${random}_invMainInitTable").setCellData(rowid,'inMoney',price*amount);
				}
				 if(cellname==='invBatch.batchNo'){//验证批号是否重复
					var batchNo= jQuery("#${random}_invMainInitTable").getCellData(rowid,'invBatch.batchNo');
					var invDictId = jQuery("#${random}_invMainInitTable").getCellData(rowid,'invDict.invId');
					if(checkBatchNo(invDictId,batchNo)){
						jQuery("#${random}_invMainInitTable").setCellData(rowid,'invBatch.batchNo',"默认批号");
						alertMsg.error("批号重复，已设为默认批号，如需修改，请重新输入");
					}
				}
				if(cellname==='invBatch.validityDate'){
	                var isQuality= jQuery("#${random}_invMainInitTable").getCellData(rowid,'invDict.isQuality');
	                if(isQuality=='true' ){
		                if(!value){
		                	alertMsg.error("有效期管理材料，有效期必填");	
		                }
	                }
				}
			},
			afterSaveCell : function(rowid, cellname,value, iRow, iCol) {
			},
		}
		jQuery("#${random}_invMainInitTable").zhGrid(zhGridSetting);
		if("${invMain.status}"=="0" &&　"${docPreview}"!="preview"){
	    	jQuery("#${random}_invMainInitTable").fullEdit();
		}
	});

</script>
<script type="text/javascript">
	function checkBatchNo(invDictId,batchNo){
		if(!batchNo){
			return false;
		}
		if(batchNo.trim()=="" || batchNo=="默认批号"){
			return false;
		}
		var isDouble = false;
		jQuery.ajax({
			url:'checkBatchNo?invDictId='+invDictId+'&batchNo='+batchNo+'&storeId='+jQuery("#${random}_invMainInit_store_id").val(),
			type:'get',
			dataType:'json',
			async:false,
			success:function(data){
				isDouble = data.doubleBatchNo;	
			}
		});	
		return isDouble;
	}

	function invDetailBatchAdd(obj) {
		var store_id = jQuery("#${random}_invMainInit_store_id").val();
		if(store_id == '' || store_id=='null'){
			alertMsg.error("请选择目标仓库。");
			return;
		} 
		var edit_URL = "invInStoreList" , tableId = "storeInvSet_gridtable";
		var url = edit_URL+"?popup=true&storeId="+store_id+"&fromType=invMainInit"+"&random="+${random};
		var winTitle="<s:text name='材料选择'/>";
		url = encodeURI(url);
		$.pdialog.open(url, 'batchAddStoreInvs', winTitle, {mask:true,width:width,height:height,resizable:false,maxable:false});
	}
</script>

<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar" id="${random}_invMainInit_toolBar">
				<li>
					<a id="${random}_newInvMainInit" class="addbutton" href="javaScript:newInvMain()"><span><s:text name="button.newDoc" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainInit_chooseInvDetail" class="getdatabutton" href="javaScript:invDetailBatchAdd(jQuery('#invDetailInit_gridtable'))"><span>选择材料</span></a>
				</li>
				<li>
					<a id="${random}_invMainInitSave" class="savebutton" href="javaScript:saveInvmainInit()"><span><s:text name="button.save" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainInitConfirm" class="confirmbutton" href="javaScript:invMainInitConfirm()"><span>记账</span> </a>
				</li>
				<li>
					<a id="${random}_invMainInitPrint" class="printbutton" href="javaScript:invMainInitPrint()"><span><s:text name="button.print" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainInitPrintSet" class="settingbutton" href="javaScript:invMainInitPrintSet()"><span><s:text name="button.printSet" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainInitSetColShow" class="settlebutton"  href="javaScript:setColShow('${random}_invMainInitTable','com.huge.ihos.material.model.InvMainInitDetail','zhGrid')"><span><s:text name="button.setColShow" /></span></a>
				</li>
				<li>
					<a id="${random}_invMainInitClose" class="closebutton" href="javaScript:invMainInitClose()"><span><s:text name="button.close" /></span> </a>
				</li>
			</ul>
		</div>
		
		<div id="${random}_invMainInitCard" autoBreakPage='*' style="margin:5px 10px 5px 5px">
			<form id="${random}_invMainInitForm">
				<s:if test="%{!entityIsNew}">
					<span style="position:absolute;right:14px;font-size:18px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px">No.${invMain.ioBillNumber}</span>
				</s:if>
				<div align="center" style="font-size:25px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px;margin:0 auto;";>
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
					<s:hidden key="invMain.docTemId"/>
					<s:hidden key="invMain.status"/>
					<s:hidden key="invMain.makePerson.personId" />
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
								<input type="text" id="${random}_invMainInit_${inputKey}" name="invMain.${inputKey}.${referName}" value="${invMain[inputKey][referName]}" ${inputKey=='busType'?"readonly='readonly'":"" }/>
								<input type="hidden" id="${random}_invMainInit_${inputKey}_id" name="invMain.${inputKey}.${referId}" value="${invMain[inputKey][referId]}"/>
							</c:if>
							<c:if test="${inputType=='date'}" >
								<input type="text" id="${random}_invMainInit_${inputKey}" name="invMain.${inputKey}" value="<fmt:formatDate value='${invMain[inputKey]}' pattern='yyyy-MM-dd' />" class="Wdate"  style="height:15px;width:80px"/>
							</c:if>
							<c:if test="${inputType=='simple'}" >
								<input type="text" id="${random}_invMainInit_${inputKey}" name="invMain.${inputKey}" value="${invMain[inputKey]}" />
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
			<div id="${random}_invMainInitTable_div" class="zhGrid_div">
				<table id="${random}_invMainInitTable"></table>
			</div>
			<div id="${random}_invMainInitForm_foot" style="height:26px">
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