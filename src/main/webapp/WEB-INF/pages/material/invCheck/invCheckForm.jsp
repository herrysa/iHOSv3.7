<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script>
 	var icdt = docTemplate("${docTemp.listMap}","${docTemp.title}","${fn:length(docTemp.inputNames)}","${fn:length(docTemp.footNames)}");
	jQuery(document).ready(function() {
		icdt.setDocLayOut("${random}_invCheckForm","${random}_invCheckTable_div","${random}_invCheckForm_foot");
		if("${docPreview}"=="preview"){
			icdt.disableButton("${random}_invCheck_toolBar");
			icdt.clearData("${random}_invCheckCard");
		}
		var invCheckId = "${invCheck.checkId}";
		if(invCheckId==null || invCheckId==""){
			invCheckId = "new";
		}
		var zhGridSetting_invCheck = {
	    	url : "invCheckDetailGridList?filter_EQS_invCheck.checkId="+invCheckId,
			datatype : "json",
			mtype : "GET",
			trHeight:25,
			formId:'${random}_invCheckForm',
			paramName:'invCheckDetailJson',
			saveUrl:"${ctx}/saveInvCheck?navTabId=invCheck_gridtable&entityIsNew=${entityIsNew}&saveType=saveStay",
			initColumn:"com.huge.ihos.material.model.InvCheckDetail",
			afterSaveFun : function(data) {
				if("saveStay" == data.callbackType){
					$.pdialog.closeCurrent();
					$.pdialog.open("${ctx}/editInvCheck?checkId="+data.forwardUrl,"showInvCheckDetail","盘点单明细", {mask:true,width:960,height:628});
				}
			},	
	        colModel:[
				{name:'invCheckDetailId',index:'invCheckDetailId',align:'center',label : '<s:text name="invCheckDetail.invCheckDetailId" />',hidden:true,key:true,editable : false,sortable:false},	
				{name:'invDict.invId',index:'invDict.invId',align:'center',label : '<s:text name="inventoryDict.invId" />',hidden:true,sortable:false},	
				{name:'invDict.invCode',index:'invDict.invCode',align:'left',width:80,label : '<s:text name="inventoryDict.invCode" />',hidden:true},	
				{name:'invDict.invName',index:'invDict.invName',align:'left',width:150,label : icdt.getListName("invDict.invName"),hidden:!(icdt.containColumn("invDict.invName")),highsearch : icdt.containColumn("invDict.invName")},	
				{name:'invDict.invModel',index:'invDict.invModel',align:'left',width:120,label : icdt.getListName("invDict.invModel"),hidden:!(icdt.containColumn("invDict.invModel")),highsearch : icdt.containColumn("invDict.invModel")},	
				{name:'invDict.firstUnit',index:'invDict.firstUnit',align:'center',width:80,label : icdt.getListName("invDict.firstUnit"),hidden:!(icdt.containColumn("invDict.firstUnit")),highsearch : icdt.containColumn("invDict.firstUnit")},	
				{name:'invBatch.id',index:'invBatch.id',align:'center',label : '<s:text name="invBatch.id" />',hidden:true},	
				{name:'invBatch.batchNo',index:'invBatch.batchNo',align:'left',width:120,label : icdt.getListName("invBatch.batchNo"),hidden:!(icdt.containColumn("invBatch.batchNo")),highsearch : icdt.containColumn("invBatch.batchNo")},	
				{name:'price',index:'price',align:'right',width:100,label : icdt.getListName("price"),hidden:!(icdt.containColumn("price")),formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces:2},highsearch : icdt.containColumn("price")},	
				{name:'acctMoney',index:'acctMoney',align:'right',width:120,label : icdt.getListName("acctMoney"),hidden:!(icdt.containColumn("acctMoney")),formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces:2},highsearch : icdt.containColumn("acctMoney")},
				{name:'acctAmount',index:'acctAmount',align:'right',width:100,label : icdt.getListName("acctAmount"),hidden:!(icdt.containColumn("acctAmount")),formatter : 'number',highsearch : icdt.containColumn("acctAmount")},	
				{name:'chkAmount',index:'chkAmount',align:'right',width:100,label : icdt.getListName("chkAmount"),hidden:!(icdt.containColumn("chkAmount")),formatter : 'number',formatoptions:{color:'blue'},editable : true,editrules : {number : true,required : true},highsearch : icdt.containColumn("chkAmount")},	
				{name:'chkMoney',index:'chkMoney',align:'right',width:120,label : icdt.getListName("chkMoney"),hidden:!(icdt.containColumn("chkMoney")),formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces:2},highsearch : icdt.containColumn("chkMoney")},	
				{name:'diffAmount',index:'diffAmount',align:'right',width:100,label : icdt.getListName("diffAmount"),hidden:!(icdt.containColumn("diffAmount")),formatter : 'number',highsearch : icdt.containColumn("diffAmount")},	
				{name:'diffMoney',index:'diffMoney',align:'right',width:120,label : icdt.getListName("diffMoney"),hidden:!(icdt.containColumn("diffMoney")),formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces:2},highsearch : icdt.containColumn("diffMoney")},	
				{name:'note',index:'note',align:'left',width:150,label : icdt.getListName("note"),hidden:!(icdt.containColumn("note")),editable : true,highsearch : icdt.containColumn("note")},	
				{name:'invDict.guarantee',index:'invDict.guarantee',align:'left',width:100,label : icdt.getListName("invDict.guarantee"),hidden:!(icdt.containColumn("invDict.guarantee")),highsearch : icdt.containColumn("invDict.guarantee")},	
				{name:'barCode',index:'barCode',align:'left',width:120,label : icdt.getListName("barCode"),hidden:!(icdt.containColumn("barCode")),highsearch : icdt.containColumn("barCode")}	
	        ],
	        jsonReader : {
				root : "invCheckDetails", // (2)
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
				 jQuery("#${random}_invCheckTable").footData([
				       {name:'invDict.invName',value:'合计:'},
				      // {name:'acctMoney',value:'sum'},
				       //{name:'acctAmount',value:'sum'},
				       //{name:'chkAmount',value:'sum'},
				       //{name:'chkMoney',value:'sum'},
				       {name:'diffAmount',value:'sum'},
				       {name:'diffMoney',value:'sum'}
				 ]);
	       	},
	       	beforeEditCell : function(rowid, cellname,value, iRow, iCol) {
	       		var store_id = jQuery("#${random}_invCheck_store_id").val();
				if(store_id == '' || store_id=='null'){
					alertMsg.error("请选择目标仓库。");
					return;
				} 
			},
	       	afterEditCell : function(rowid, cellname,value, iRow, iCol) {
	       		if(cellname==='chkAmount'){
					var price= jQuery("#${random}_invCheckTable").getCellData(rowid,'price');
					 var chkAmount= jQuery("#${random}_invCheckTable").getCellData(rowid,'chkAmount');
					 var acctAmount= jQuery("#${random}_invCheckTable").getCellData(rowid,'acctAmount');
					 price = isNaN(parseFloat(price))?0:parseFloat(price);
					 chkAmount = isNaN(parseInt(chkAmount))?0:parseInt(chkAmount);
					 acctAmount = isNaN(parseInt(acctAmount))?0:parseInt(acctAmount);
					 diffAmount = chkAmount-acctAmount;
	                jQuery("#${random}_invCheckTable").setCellData(rowid,'chkMoney',price*chkAmount);
	                jQuery("#${random}_invCheckTable").setCellData(rowid,'diffAmount',diffAmount);
	                jQuery("#${random}_invCheckTable").setCellData(rowid,'diffMoney',price*diffAmount);
				}
			},
			afterSaveCell : function(rowid, cellname,
					value, iRow, iCol) {
			}
	    }
	   jQuery("#${random}_invCheckTable").zhGrid(zhGridSetting_invCheck);
	if("${docPreview}"!="preview"){
		if(${entityIsNew}){
			disableLink(["${random}_invCheckAudit"]);
		}
	   if("${invCheck.state}"=="0"){//新建状态
			jQuery("#${random}_invCheckTable").fullEdit();
			disableLink(["${random}_invCheck_new","${random}_invCheckAntiAudit","${random}_invCheckConfirm","${random}_invCheckPrint","${random}_invCheckPrintSet"]);
			jQuery("#${random}_invCheck_store").treeselect({
				dataType : "sql",
				optType : "single",
				sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where is_book = 1 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
				exceptnullparent : false,
				lazy : false,
				callback : {
					afterClick : function() {
						jQuery.ajax({
							type:'get',
							url:'${ctx}/getDeptByStore',
							data:{id:jQuery("#${random}_invCheck_store_id").val()},
							dataType:'json',
							async:false,
							error:function(){
							},
							success:function(data){
								var dept = data.department;
								if(dept){
									jQuery("#${random}_invCheck_dept").val(dept['name']);
									jQuery("#${random}_invCheck_dept_id").val(dept['departmentId']);
									$("#${random}_invCheck_person_id").val("");
									$("#${random}_invCheck_person").val("");
									invCheckPersonTreeSelect();
								}
							}
						});
					}
				}
			});
			jQuery("#${random}_invCheck_dept").treeselect({
				dataType:"sql",
				optType:"single",
				sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
				exceptnullparent:false,
				lazy:false,
				callback : {
					afterClick : function() {
						$("#${random}_invCheck_person_id").val("");
						$("#${random}_invCheck_person").val("");
						invCheckPersonTreeSelect();
					}
				}
			});
			jQuery('#${random}_invCheck_makeDate').unbind( 'click' ).bind("click",function(){
				var storeId = jQuery("#${random}_invCheck_store_id").val();
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
			if(jQuery("#${random}_invCheck_dept").val()){
				invCheckPersonTreeSelect();
			}
	   }else{
		   	if("${invCheck.state}"=="1"){
		   		disableLink(["${random}_invCheck_lockStore","${random}_invCheck_autoSetChkAmount","${random}_invCheck_unlockStore","${random}_invCheck_chooseInvCheckDetail","${random}_invCheckSave","${random}_invCheckAudit",,"${random}_invCheckPrint","${random}_invCheckPrintSet"]);
			}else{
				disableLink(["${random}_invCheck_lockStore","${random}_invCheck_autoSetChkAmount","${random}_invCheck_unlockStore","${random}_invCheck_chooseInvCheckDetail","${random}_invCheckSave","${random}_invCheckAudit","${random}_invCheckAntiAudit","${random}_invCheckConfirm"]);
			}
		   	jQuery("#${random}_invCheck_store").attr('readOnly',"true").removeClass('textInput').removeClass('required').addClass('lineInput');
	    	jQuery("#${random}_invCheck_dept").attr('readOnly',"true").removeClass('textInput').removeClass('required').addClass('lineInput');
	    	jQuery("#${random}_invCheck_person").attr('readOnly',"true").removeClass('textInput').removeClass('required').addClass('lineInput');
	    	jQuery("#${random}_invCheck_makeDate").attr('readOnly',"true").removeClass('textInput').removeClass('Wdate').addClass('lineInput');
	    	jQuery("#${random}_invCheck_remark").attr('readOnly',"true").removeClass('textInput').addClass('lineInput');
	   } 
	}
	});
	
	function invCheckPersonTreeSelect(){
    	setTimeout(function(){
			var sql = "SELECT p.personId id ,p.name name FROM t_person p where p.disabled = 0 and p.dept_id='"+$("#${random}_invCheck_dept_id").val()+"'";
			 jQuery("#${random}_invCheck_person").treeselect({
				dataType:"sql",
				optType:"single",
				sql:sql,
				exceptnullparent:false,
				lazy:false
			}); 
		},300);
    }
</script>
<script type="text/javascript">
	function newInvCheck(){
		$.pdialog.closeCurrent();
		$.pdialog.open('${ctx}/editInvCheck?radomJsp='+Math.floor(Math.random()*10),'addInvCheck',"添加盘点单", {mask:true,width : 960,height : 628,resizable:false});
	}
	
	function invCheckLockStore(){
		var storeId = jQuery("#${random}_invCheck_store_id").val();
    	if(!storeId){
    		alertMsg.error("请选择目标仓库。");
    		return;
    	}
    	$.get("getStoreLockState?id="+storeId,function(data) {
    		if(data.isLock){
    			alertMsg.error("该仓库已封帐！");
        		return;
    		}
    	});
   		alertMsg.confirm("封帐后暂时不能进行出入库操作，确定要封帐吗？", {
   			okCall: function(){
   				$.post("doLockStore?id="+storeId,function(data) {
   					formCallBack(data);
   				});
   			}
   		});
	}
	
	function invCheckUnLockStore(){
		var storeId = jQuery("#${random}_invCheck_store_id").val();
    	if(!storeId){
    		alertMsg.error("请选择目标仓库。");
    		return;
    	}
    	$.get("getStoreLockState?id="+storeId,function(data) {
    		if(!data.isLock){
    			alertMsg.error("该仓库尚未封帐！");
        		return;
    		}
    	});
   		alertMsg.confirm("确定要解封吗？", {
   			okCall: function(){
   				$.post("unLockStore?id="+storeId,function(data) {
   					formCallBack(data);
   				});
   			}
   		});
	}
	
	function invCheckImportDict(){
		var storeId = jQuery("#${random}_invCheck_store_id").val();
    	if(!storeId){
    		alertMsg.error("请选择目标仓库。");
    		return;
    	}
    	$.get("getStoreLockState?id="+storeId,function(data) {
			if(data.isLock){
		    	if(storeId){
		    		//过滤已经存在的明细
		    		var trs = jQuery("#${random}_invCheckTable").getGridData();
		    		var rowid = '';
		    		var invId = '',batchId = '',ivbhId = '',ivbhIds = '';
		    		for(var i=0;i<trs.length;i++){
		    			rowid = trs[i]['rowid'];
		    			invId = jQuery("#${random}_invCheckTable").getCellData(rowid,'invDict.invId');
		    			batchId = jQuery("#${random}_invCheckTable").getCellData(rowid,'invBatch.id');
		    			ivbhId = batchId + '^'+invId;
		    			ivbhIds += ivbhId +',';
		    		}
		    		var url = "invCheckImportDict?popup=true&storeId="+storeId+"&ivbhIds="+ivbhIds+"&random="+${random};
					var winTitle = "材料选择";
					url = encodeURI(url);
					$.pdialog.open(url,'invCheckImportDict',winTitle, {mask:true,width : 990,height : 628,resizable:false,maxable:false});
		    	}
			}else{
				alertMsg.error("当前仓库尚未封帐，请封帐！");
				return;
			}
		});
	}

	function autoSetChkAmount(){
		var trs = jQuery("#${random}_invCheckTable").getGridData();
		var rowid = '';
		var chkAmount = 0;
		var acctAmount = 0,acctMoney = 0;
		for(var i=0;i<trs.length;i++){
			rowid = trs[i]['rowid'];
			chkAmount = jQuery("#${random}_invCheckTable").getCellData(rowid,'chkAmount');
			//alert(chkAmount);
			if(chkAmount && chkAmount != 0){
				continue;
			}
			acctAmount = jQuery("#${random}_invCheckTable").getCellData(rowid,'acctAmount');
			acctMoney = jQuery("#${random}_invCheckTable").getCellData(rowid,'acctMoney');
			jQuery("#${random}_invCheckTable").setCellData(rowid,'chkAmount',acctAmount);
			jQuery("#${random}_invCheckTable").setCellData(rowid,'chkMoney',acctMoney);
		}
	}
	
	function saveInvCheck(){
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
		jQuery("#${random}_invCheckTable").saveGrid();
	}
	
	function auditInvCheck(){
		var gridEdited = jQuery("#${random}_invCheckTable")[0].p.gridEdited;
		if(gridEdited){
			alertMsg.error("单据内容有修改，请先保存！");
			return ;
			
		}
		var editUrl = "invCheckGridEdit?id=${invCheck.checkId}&navTabId=invCheck_gridtable&oper=check";
		alertMsg.confirm("确认审核？", {
			okCall : function() {
				$.post(editUrl,function(data) {
					$.pdialog.reload("${ctx}/editInvCheck?popup=true&checkId=${invCheck.checkId}",{dialogId:'showInvCheckDetail'});
					formCallBack(data);
				});
			}
		});
	}
	
	function antiAuditInvCheck(){
		var editUrl = "invCheckGridEdit?id=${invCheck.checkId}&navTabId=invCheck_gridtable&oper=cancelcheck";
		alertMsg.confirm("确认销审？", {
			okCall : function() {
				$.post(editUrl,function(data) {
					$.pdialog.reload("${ctx}/editInvCheck?popup=true&checkId=${invCheck.checkId}",{dialogId:'showInvCheckDetail'});
					formCallBack(data);
				});
			}
		});
	}
	
	function confirmInvCheck(){
		if("${invCheck.state}"=="0"){
			alertMsg.error("该单据尚未审核！");
			return;
		}
		var editUrl = "invCheckGridEdit?id=${invCheck.checkId}&navTabId=invCheck_gridtable&oper=exportInOut";
		alertMsg.confirm("确认生成出入库单？", {
			okCall : function() {
				$.post(editUrl,function(data) {
					$.pdialog.reload("${ctx}/editInvCheck?popup=true&checkId=${invCheck.checkId}",{dialogId:'showInvCheckDetail'});
					formCallBack(data);
				});
			}
		});
	}
	
	function printInvCheck(){
		/* var invCheckCardHtml = jQuery("#invCheckCard").html();
		jQuery("body").append("<div id='page1'>"+invCheckCardHtml+"</div>");
		jQuery("#invCheckTable_head",'#page1').css('width','99%');
		jQuery("#invCheckTable",'#page1').css('width','99%');
		jQuery("#invCheckTable_foot",'#page1').css('width','99%');
		jQuery("#invCheckTable_div",'#page1').css('width','auto');
		jQuery("#invCheckTable_div",'#page1').css('height','auto');
		return;
		doPrint(); */
	}
	function printSetInvCheck(){
		
	}
	function invCheckClose(){
		var gridEdited = jQuery("#${random}_invCheckTable")[0].p.gridEdited;
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
			<ul class="toolBar" id="${random}_invCheck_toolBar">
				<li>
					<a id="${random}_invCheck_new" class="addbutton" href="javaScript:newInvCheck()"><span><s:text name="button.newDoc" /></span> </a>
				</li>
				<li>
					<a id="${random}_invCheck_lockStore" class="editbutton" href="javaScript:invCheckLockStore()"><span>封帐</span></a>
				</li>
				<li>
					<a id="${random}_invCheck_unlockStore" class="canceleditbutton" href="javaScript:invCheckUnLockStore()"><span>解封</span></a>
				</li>
				<li>
					<a id="${random}_invCheck_chooseInvCheckDetail" class="getdatabutton" href="javaScript:invCheckImportDict()"><span>选择材料</span></a>
				</li>
				<li>
					<a id="${random}_invCheck_autoSetChkAmount" class="settingbutton" href="javaScript:autoSetChkAmount()"><span>自动填充</span></a>
				</li>
				<li>
					<a id="${random}_invCheckSave" class="savebutton" href="javaScript:saveInvCheck()"><span><s:text name="button.save" /></span> </a>
				</li>
				<li>
					<a id="${random}_invCheckAudit" class="checkbutton" href="javaScript:auditInvCheck()"><span><s:text name="button.check" /></span> </a>
				</li>
				<li>
					<a id="${random}_invCheckAntiAudit" class="delallbutton" href="javaScript:antiAuditInvCheck()"><span><s:text name="button.cancelCheck" /></span> </a>
				</li>
				<li>
					<a id="${random}_invCheckConfirm" class="confirmbutton" href="javaScript:confirmInvCheck()"><span>生成出入库单</span> </a>
				</li>
				<li>
					<a id="${random}_invCheckPrint" class="printbutton" href="javaScript:printInvCheck()"><span><s:text name="button.print" /></span> </a>
				</li>
				<li>
					<a id="${random}_invCheckPrintSet" class="settingbutton" href="javaScript:printSetInvCheck()"><span><s:text name="button.printSet" /></span> </a>
				</li>
				<li>
					<a id="${random}_invCheckSetColShow" class="settlebutton"  href="javaScript:setColShow('${random}_invCheckTable','com.huge.ihos.material.model.InvCheckDetail','zhGrid')"><span><s:text name="button.setColShow" /></span></a>
				</li>
				<li>
					<a id="${random}_invCheckClose" class="closebutton" href="javaScript:invCheckClose()"><span><s:text name="button.close" /></span> </a>
				</li>
			</ul>
		</div>
		<div id="${random}_invCheckCard" autoBreakPage='*' style="margin:5px 10px 5px 5px">
			<form id="${random}_invCheckForm">
				<s:if test="%{!entityIsNew}">
					<span style="position:absolute;right:14px;font-size:18px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px">No.${invCheck.checkNo}</span>
				</s:if>
				<div align="center" style="font-size:25px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px;margin:0 auto;">
				    ${docTemp.title}
					<hr style="color:rgb(24, 127, 207);margin-top:0px"/>
					<hr style="color:rgb(24, 127, 207);margin-top:-11px"/>
				</div>
				<div>
					<s:hidden key="invCheck.checkId"/>
					<s:hidden key="invCheck.orgCode"/>
					<s:hidden key="invCheck.copyCode"/>
					<s:hidden key="invCheck.inId"/>
					<s:hidden key="invCheck.outId"/>
					<s:hidden key="invCheck.yearMonth"/>
					<s:hidden key="invCheck.docTemId"/>
					<s:hidden key="invCheck.makePerson.personId" />
					<s:hidden key="invCheck.state" />
					<s:hidden key="invCheck.checkNo" />
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
								<input type="text" id="${random}_invCheck_${inputKey}" name="invCheck.${inputKey}.${referName}" value="${invCheck[inputKey][referName]}" />
								<input type="hidden" id="${random}_invCheck_${inputKey}_id" name="invCheck.${inputKey}.${referId}" value="${invCheck[inputKey][referId]}"/>
							</c:if>
							<c:if test="${inputType=='date'}" >
								<input type="text" id="${random}_invCheck_${inputKey}" name="invCheck.${inputKey}" value="<fmt:formatDate value='${invCheck[inputKey]}' pattern='yyyy-MM-dd' />" class="Wdate"  style="height:15px;width:80px"/>
							</c:if>
							<c:if test="${inputType=='simple'}" >
								<input type="text" id="${random}_invCheck_${inputKey}" name="invCheck.${inputKey}" value="${invCheck[inputKey]}" />
							</c:if>
							<script>
								if("${invCheck.state}"=="0" && ${input.necessary}){
									jQuery("#${random}_${input.value}_${input.necessary} input[type='text']").addClass("required");
								}
							</script>
						</span>
					</c:forEach>
				</div>
			</form>
			<div id="${random}_invCheckTable_div" class="zhGrid_div">
				<table id="${random}_invCheckTable"></table>
			</div>
			<div id="${random}_invCheckForm_foot" style="height:26px">
				<ul style="float:left;width:100%;height:20px;margin-top:8px;text-align:center">
					<c:forEach items="${docTemp.footMap}" var="foot">
						<li style="float:left;">
							<label><c:out value="${foot.value}"/>:</label>
							<c:if test="${foot.key!='sign'}">
									<c:set var="footKey" value="${foot.key}" scope="page"/>
									<input class="lineInput" size="8" value="${invCheck[footKey].name}" readonly="readonly"/>
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
