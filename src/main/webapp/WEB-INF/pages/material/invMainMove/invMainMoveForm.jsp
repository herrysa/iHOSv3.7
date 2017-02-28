
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	function invDictMoveComboGrid(elem) {
		var elemId = jQuery(elem).attr("id");
		elemId=elemId.replace(".","\.");
		jQuery(elem).attr("id",elemId);
		$(elem).combogrid({
			url : '${ctx}/comboGridSqlList',
			queryParams : {
				sql:"select invId,invCode,invName,invModel,firstUnit,batchId,batchNo,productionDate,price,curAmount "+
			    	"from v_mm_invOutStore where curAmount > 0 "+
			    	"and storeId='"+$("#${random}_invMainMove_store_id").val()+"' "+
			    	"and orgCode='"+$('#${random}_invMainMove_orgCode').val()+"' "+
			    	"and copyCode='"+$('#${random}_invMainMove_copyCode').val()+"' "+
			    	"and yearMonth='"+$('#${random}_invMainMove_yearMonth').val()+"'",
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
				var thisTr = jQuery("#${random}_invMainMoveTable").getTr($(elem));
				var rowid = thisTr.attr("id");
				jQuery("#${random}_invMainMoveTable").setCellData(rowid,'invDict.invName',ui.item.INVNAME);
				jQuery("#${random}_invMainMoveTable").setCellData(rowid,'invDict.invId',ui.item.INVID);
				jQuery("#${random}_invMainMoveTable").setCellData(rowid,'invDict.invCode',ui.item.INVCODE);
				jQuery("#${random}_invMainMoveTable").setCellData(rowid,'invDict.invModel',ui.item.INVMODEL);
				jQuery("#${random}_invMainMoveTable").setCellData(rowid,'invDict.firstUnit',ui.item.FIRSTUNIT);
				jQuery("#${random}_invMainMoveTable").setCellData(rowid,'currentStock',ui.item.CURAMOUNT);
				jQuery("#${random}_invMainMoveTable").setCellData(rowid,'invBatch.batchNo',ui.item.BATCHNO);
				jQuery("#${random}_invMainMoveTable").setCellData(rowid,'invBatch.id',ui.item.BATCHID);
				jQuery("#${random}_invMainMoveTable").setCellData(rowid,'inPrice',ui.item.PRICE);
				thisTr.attr("used","true");
				return false;
			}
		});
	}
	function invMainMoveApplyPersonTreeSelect(){
		setTimeout(function(){
			var sql = "SELECT p.personId id ,p.name name FROM t_person p where p.dept_id='"+$("#${random}_invMainMove_applyDept_id").val()+"'";
			 jQuery("#${random}_invMainMove_applyPersion").treeselect({
				dataType:"sql",
				optType:"single",
				sql:sql,
				exceptnullparent:false,
				lazy:false
			}); 
		},50);
	}
	
	function getInvMainMoveCurAmount(invDictId,batchId){
		var curAmount = 0;
		jQuery.ajax({
			url:'getInvMainOutCurAmount?invDictId='+invDictId+'&batchId='+batchId+'&storeId='+$('#${random}_invMainMove_store_id').val(),
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
	var imvdt = docTemplate("${docTemp.listMap}","${docTemp.title}","${fn:length(docTemp.inputNames)}","${fn:length(docTemp.footNames)}");

	jQuery(document).ready(function() {
		imvdt.setDocLayOut("${random}_invMainMoveForm","${random}_invMainMoveTable_div","${random}_invMainMoveForm_foot");
		if("${docPreview}"=="preview"){
			imvdt.disableButton("${random}_invMainMove_toolBar");
			jQuery("#${random}_invMainMoveCard input").val("");
		}
		var invMainMoveId = "${invMain.ioId}";
		if(invMainMoveId==null || invMainMoveId==""){
			invMainMoveId = "new";
		}
		var url = "invDetailGridList?filter_EQS_invMain.ioId="+invMainMoveId;
		var saveUrl = "${ctx}/saveInvMainOut?navTabId=invMainMove_gridtable&entityIsNew=${entityIsNew}&saveType=saveStay&docType=移库单";
		if("${docPreview}"=="deptAppMove"){
			url += "&docPreview=deptAppOut&deptAppId=${deptAppId}&deptAppDetailIds=${deptAppDetailIds}";
			saveUrl += "&saveFrom=deptAppOut&deptAppId=${deptAppId}&deptAppDetailIds=${deptAppDetailIds}"
		}
		var zhGridSetting_invMainMove={
			url : url,
			datatype : "json",
			mtype : "GET",
			formId:'${random}_invMainMoveForm',
			paramName:'invDetailJson',
			saveUrl:saveUrl,
			initColumn:"com.huge.ihos.material.model.InvMainMoveDetail",
			afterSaveFun : function(data) {
				if("saveStay" == data.callbackType){
					$.pdialog.closeCurrent();
					$.pdialog.open("${ctx}/editInvMainMove?docType=移库单&ioId="+data.forwardUrl,"showInvMainMove","移库单明细", {mask:true,width:972,height:628});
				}
			},			
			colModel : [
				{name:'invDetialId',index : 'invDetialId',align : 'center',label : '<s:text name="invDetail.invDetialId" />',hidden : true,key : true,editable : false,sortable:false},
				{name:'invDict.invId',index : 'invDict_invId',align : 'left',label : '<s:text name="inventoryDict.invId" />',hidden : true,editable : true,edittype : 'text',sortable:false},
				{name:'invDict.invName',index : 'invDict_invName',align : 'left',width:150,label : imvdt.getListName("invDict.invName"),hidden : !(imvdt.containColumn("invDict.invName")),highsearch : imvdt.containColumn("invDict.invName"),
					editable : true,edittype : 'text',editoptions : {dataInit : invDictMoveComboGrid},sortable:false},
				{name:'invDict.invCode',index : 'invDict_invCode',align : 'left',width:80,label : imvdt.getListName("invDict.invCode"),hidden : !(imvdt.containColumn("invDict.invCode")),highsearch : imvdt.containColumn("invDict.invCode"),sortable:false},
				{name:'invDict.invModel',index : 'invDict_invModel',align : 'left',width:120,label : imvdt.getListName("invDict.invModel"),hidden : !(imvdt.containColumn("invDict.invModel")),highsearch : imvdt.containColumn("invDict.invModel"),sortable:false},
				{name:'invDict.firstUnit',index : 'invDict_firstUnit',align : 'center',width:80,label : imvdt.getListName("invDict.firstUnit"),hidden : !(imvdt.containColumn("invDict.firstUnit")),highsearch : imvdt.containColumn("invDict.firstUnit"),sortable:false},
				{name:'inAmount',index : 'inAmount',align : 'right',width:100,label : imvdt.getListName("inAmount"),hidden : !(imvdt.containColumn("inAmount")),highsearch : imvdt.containColumn("inAmount"),formatter : 'number',editable : true,
					editrules : {integer : true,required : true}
				},
				{name : 'inPrice',index : 'inPrice',align : 'right',width:100,label : imvdt.getListName("inPrice"),hidden : !(imvdt.containColumn("inPrice")),highsearch : imvdt.containColumn("inPrice"),formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces: 2},editable : false,
					editrules : {number : true,required : true}
				},
				{name : 'inMoney',index : 'inMoney',align : 'right',width:110,label : imvdt.getListName("inMoney"),hidden : !(imvdt.containColumn("inMoney")),highsearch : imvdt.containColumn("inMoney"),formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces: 2},editable : false,
					editrules : {number : true,required : true}
				},
				{name:'invBatch.batchNo',index : 'invDict_batchNo',align : 'left',	width:120,label : imvdt.getListName("invBatch.batchNo"),hidden : !(imvdt.containColumn("invBatch.batchNo")),highsearch : imvdt.containColumn("invBatch.batchNo"),sortable:false},
				{name:'invBatch.id',index : 'invBatch_id',align : 'left',	label : '<s:text name="invBatch.id" />',hidden :true,sortable:false},
				{name : 'snCode',index : 'snCode',align : 'left',width:120,label : imvdt.getListName("snCode"),hidden : !(imvdt.containColumn("snCode")),highsearch : imvdt.containColumn("snCode"),editable : true}
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
				 jQuery("#${random}_invMainMoveTable").footData([
  				       {name:'invDict.invName',value:'合计:'},
  				       {name:'inMoney',value:'sum'}
   				 ]);
			},
			beforeEditCell : function(rowid, cellname,value, iRow, iCol) {
				var store_id = jQuery("#${random}_invMainMove_store_id").val();
				if(store_id == '' || store_id=='null'){
					alertMsg.error("请选择移出仓库。");
					return;
				}  
			}, 
			afterEditCell : function(rowid, cellname,value, iRow, iCol) {
				   if(cellname ==='inAmount'){
					var amount = value*1;
					var invDictId= jQuery("#${random}_invMainMoveTable").getCellData(rowid,'invDict.invId');
					var batchId= jQuery("#${random}_invMainMoveTable").getCellData(rowid,'invBatch.id');
					var currentStock = getInvMainMoveCurAmount(invDictId,batchId);
					if(amount > currentStock){
						jQuery("#${random}_invMainMoveTable").setCellData(rowid,'inAmount',0);
						alertMsg.error("移出数量大于当前库存, 请重新输入");
						return "0";
					}
				} 
				if(cellname==='inPrice' || cellname==='inAmount'){
					var price= jQuery("#${random}_invMainMoveTable").getCellData(rowid,'inPrice');
					 var amount= jQuery("#${random}_invMainMoveTable").getCellData(rowid,'inAmount');
					 price = isNaN(parseFloat(price))?0:parseFloat(price);
					 amount = isNaN(parseFloat(amount))?0:parseFloat(amount);
	                jQuery("#${random}_invMainMoveTable").setCellData(rowid,'inMoney',price*amount);
				}
			}
		}
		jQuery("#${random}_invMainMoveTable").zhGrid(zhGridSetting_invMainMove);
	if("${docPreview}"!="preview"){
		if(${entityIsNew}){
			disableLink(["${random}_invMainMoveAudit"]);
		}
		if("${invMain.status}"=="0"){
			jQuery("#${random}_invMainMoveTable").fullEdit();
			disableLink(["${random}_invMainMove_new","${random}_invMainMovePrint","${random}_invMainMoveAntiAudit","${random}_invMainMoveConfirm"]);
			initStoreSelectInInvMainMove("${random}_invMainMove_store");
			initStoreSelectInInvMainMove("${random}_invMainMove_exchStoreId");
			jQuery("#${random}_invMainMove_applyDept").treeselect({
				dataType:"sql",
				optType:"single",
				sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
				exceptnullparent:false,
				lazy:false,
				callback : {
					afterClick : function() {
						$("#${random}_invMainMove_usedDept").val($("#${random}_invMainMove_applyDept").val());
						$("#${random}_invMainMove_usedDept_id").val($("#${random}_invMainMove_applyDept_id").val());
						$("#${random}_invMainMove_applyPersion_id").val("");
						$("#${random}_invMainMove_applyPersion").val("");
						invMainMoveApplyPersonTreeSelect();
					}
				}
			});
			jQuery("#${random}_invMainMove_usedDept").treeselect({
				dataType : "sql",
				optType : "single",
				sql : "SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
				exceptnullparent : false,
				lazy : false
			});
			if(jQuery("#${random}_invMainMove_applyDept").val()){
				invMainMoveApplyPersonTreeSelect();
			}
			jQuery('#${random}_invMainMove_makeDate').unbind( 'click' ).bind("click",function(){
				var storeId = jQuery("#${random}_invMainMove_store_id").val();
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
				disableLink(["${random}_invMainMovePrint","${random}_invMainMoveAudit","${random}_invMainMoveSave","${random}_invMainMove_chooseInvDetail"]);
			}else{
				disableLink(["${random}_invMainMoveAntiAudit","${random}_invMainMoveSave","${random}_invMainMoveAudit","${random}_invMainMoveConfirm","${random}_invMainMove_chooseInvDetail"]);
			}
			jQuery("#${random}_invMainMove_busType").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"true");
	    	jQuery("#${random}_invMainMove_store").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"true");
	    	jQuery("#${random}_invMainMove_exchStoreId").removeClass('textInput').removeClass('required').addClass('lineInput').attr('readOnly',"true");
	    	jQuery("#${random}_invMainMove_makeDate").removeClass('Wdate').removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
	    	jQuery("#${random}_invMainMove_remark").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		}
	}
	});
	function initStoreSelectInInvMainMove(id){
		jQuery("#"+id).treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where is_book = 1 and is_lock = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		});
	}
</script>
<script type="text/javascript">
	function newInvMainMove(){
		$.pdialog.closeCurrent();
		$.pdialog.open('${ctx}/editInvMainMove?docType=移库单', 'addInvMainMove', '添加移库单', {mask:true,width:width,height:height});　
	}
	function importDictToInvMainMove(){
		var store_id = jQuery("#${random}_invMainMove_store_id").val();
		if(store_id == '' || store_id=='null'){
			alertMsg.error("请选择移出仓库。");
			return;
		} 
		var url = "${ctx}/batchAddOutInvs?popup=true&storeId="+store_id+"&navTabId=${random}_invMainMoveTable&random=${random}";
		var winTitle="<s:text name='材料选择'/>";
		url = encodeURI(url);
		$.pdialog.open(url, 'importDictToInvMainMove', winTitle, {mask:true,width:width,height:height,resizable:false,maxable:false});
	
	}
	function saveInvMainMove(){
		jQuery("#${random}_invMainMoveTable").saveGrid();
	}
	function auditInvMainMove(){
		var gridEdited = jQuery("#${random}_invMainMoveTable")[0].p.gridEdited;
		if(gridEdited){
			alertMsg.error("单据内容有修改，请先保存！");
			return ;
			
		}
		var editUrl = "${ctx}/invMainOutAudit?ioType=3&id=${invMain.ioId}&navTabId=invMainMove_gridtable";
		alertMsg.confirm("确认移出？", {
			okCall : function() {
				jQuery.post(editUrl, function(data) {
					$.pdialog.reload("${ctx}/editInvMainMove?docType=移库单&ioId=${invMain.ioId}&popup=true",{dialogId:'showInvMainMove'});
					formCallBack(data);
				});
			}
		});
	}
	function antiAuditMainMove(){
		var editUrl = "${ctx}/invMainOutAuditNot?ioType=3&id=${invMain.ioId}&navTabId=invMainMove_gridtable";
		alertMsg.confirm("确认取消移出？", {
			okCall : function() {
				jQuery.post(editUrl, function(data) {
					$.pdialog.reload("${ctx}/editInvMainMove?docType=移库单&ioId=${invMain.ioId}&popup=true",{dialogId:'showInvMainMove'});
					formCallBack(data);
				});
			}
		});
	}
	function confirmInvMainMove(){
		if("${invMain.status}"=="0"){
			alertMsg.error("该单据尚未移出，不能移入！。");
			return;
		}
		var editUrl = "${ctx}/invMainOutConfirm?ioType=3&id=${invMain.ioId}&navTabId=invMainMove_gridtable";
		alertMsg.confirm("确认移入？", {
			okCall : function() {
				jQuery.post(editUrl, function(data) {
					$.pdialog.reload("${ctx}/editInvMainMove?docType=移库单&ioId=${invMain.ioId}&popup=true",{dialogId:'showInvMainMove'});
					formCallBack(data);
				});
			}
		});
	}
	function printInvMainMove(){
		/* var invMainOutCardHtml = jQuery("#invMainOutCard").html();
		jQuery("body").append("<div id='page1'>"+invMainOutCardHtml+"</div>");
		jQuery("#invMainMoveTable_head",'#page1').css('width','99%');
		jQuery("#invMainMoveTable",'#page1').css('width','99%');
		jQuery("#invMainMoveTable_foot",'#page1').css('width','99%');
		jQuery("#invMainMoveTable_div",'#page1').css('width','auto');
		jQuery("#invMainMoveTable_div",'#page1').css('height','auto');
		doPrint(); */
	}
	function invMainMoveClose(){
		var gridEdited = jQuery("#${random}_invMainMoveTable")[0].p.gridEdited;
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
			<ul class="toolBar" id="${random}_invMainMove_toolBar">
				<li>
					<a id="${random}_invMainMove_new" class="addbutton" href="javaScript:newInvMainMove()"><span><s:text name="button.newDoc" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainMove_chooseInvDetail" class="getdatabutton" href="javaScript:importDictToInvMainMove()"><span>选择材料</span></a>
				</li>
				<li>
					<a id="${random}_invMainMoveSave" class="savebutton" href="javaScript:saveInvMainMove()"><span><s:text name="button.save" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainMoveAudit" class="checkbutton" href="javaScript:auditInvMainMove()"><span>移出确认</span> </a>
				</li>
				<li>
					<a id="${random}_invMainMoveAntiAudit" class="delallbutton" href="javaScript:antiAuditMainMove()"><span>取消移出确认</span> </a>
				</li>
				<li>
					<a id="${random}_invMainMoveConfirm" class="confirmbutton" href="javaScript:confirmInvMainMove()"><span>移入确认</span> </a>
				</li>
				<li>
					<a id="${random}_invMainMovePrint" class="printbutton" href="javaScript:printInvMainMove()"><span><s:text name="button.print" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainMovePrintSet" class="settingbutton" href="javaScript:printSetInvMainMove()"><span><s:text name="button.printSet" /></span> </a>
				</li>
				<li>
					<a id="${random}_invMainMoveSetColShow" class="settlebutton"  href="javaScript:setColShow('${random}_invMainMoveTable','com.huge.ihos.material.model.InvMainMoveDetail','zhGrid')"><span><s:text name="button.setColShow" /></span></a>
				</li>
				<li>
					<a id="${random}_invMainMoveClose" class="closebutton" href="javaScript:invMainMoveClose()"><span><s:text name="button.close" /></span> </a>
				</li>
			</ul>
		</div>
		<div id="${random}_invMainMoveCard" autoBreakPage='*' style="margin:5px 10px 5px 5px">
			<form id="${random}_invMainMoveForm">
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
					<s:hidden key="invMain.makePerson.personId" />
					<s:hidden key="invMain.docTemId" />
					<s:hidden key="invMain.status" />
					<input type="hidden" id="${random}_invMainMove_orgCode" name="invMain.orgCode" value="${invMain.orgCode}"/>
					<input type="hidden" id="${random}_invMainMove_copyCode" name="invMain.copyCode" value="${invMain.copyCode}"/>
					<input type="hidden" id="${random}_invMainMove_yearMonth" name="invMain.yearMonth" value="${invMain.yearMonth}"/>
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
								<input type="text" id="${random}_invMainMove_${inputKey}" name="invMain.${inputKey}.${referName}" value="${invMain[inputKey][referName]}" ${inputKey=='busType'?"readonly='readonly'":"" }/>
								<input type="hidden" id="${random}_invMainMove_${inputKey}_id" name="invMain.${inputKey}.${referId}" value="${invMain[inputKey][referId]}"/>
							</c:if>
							<c:if test="${inputType=='date'}" >
								<input type="text" id="${random}_invMainMove_${inputKey}" name="invMain.${inputKey}" value="<fmt:formatDate value='${invMain[inputKey]}' pattern='yyyy-MM-dd' />" class="Wdate"  style="height:15px;width:80px"/>
							</c:if>
							<c:if test="${inputType=='simple'}" >
								<input type="text" id="${random}_invMainMove_${inputKey}" name="invMain.${inputKey}" value="${invMain[inputKey]}"/>
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
			<div id="${random}_invMainMoveTable_div" class="zhGrid_div">
				<table id="${random}_invMainMoveTable"></table>
			</div>
			<div style="height:26px" id="${random}_invMainMoveForm_foot">
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