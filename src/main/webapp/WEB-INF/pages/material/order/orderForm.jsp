<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	function invDictOrderComboGrid(elem) {
		var elemId = jQuery(elem).attr("id");
		elemId=elemId.replace(".","_");
		jQuery(elem).attr("id",elemId);
		var sql = "select invId,invCode,invName,invModel,firstUnit,isNULL(refCost,0) AS refCost from mm_inv_dict where invId in ( select distinct invId from v_mm_invInstore where orgCode='"+"${fns:userContextParam('orgCode')}"+"' and copyCode='"+"${fns:userContextParam('copyCode')}"+"')";
		//var sql = "select invId,invCode,invName,invModel,firstUnit,isNULL(refCost,0) refCost from mm_inv_dict where disabled = 0 and orgCode='"+jQuery("#orgCode").html()+"' and copyCode='"+jQuery("#copyCode").html()+"'";
		var selectInvDictByVendor = "${selectInvDictByVendor}";
		if("${selectInvDictByVendor}"=="1"){
			var vendorId = jQuery("#${random}_order_vendor_id").val();
			if(vendorId){
				sql += " and vendor_id='"+vendorId+"'";
			}
		}
		$(elem).combogrid({
			url : '${ctx}/comboGridSqlList',
			queryParams : {
				sql:sql,
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
			}
			],
			_create: function( event, item ) {
				//alert();
			},
			select : function(event, ui) {
				//$(elem).attr("value",ui.item.INVNAME);
				var thisTr = jQuery("#${random}_orderTable").getTr($(elem));
				var rowid = thisTr.attr("id");
				thisTr.find("input[name='invDict.invId']").val(ui.item.INVID).blur();
				thisTr.find("input[name='invDict.invName']").val(ui.item.INVNAME).blur();
				thisTr.find("input[name='invDict.invCode']").val(ui.item.INVCODE).blur();
				thisTr.find("input[name='invDict.invModel']").val(ui.item.INVMODEL).blur();
				thisTr.find("input[name='invDict.firstUnit']").val(ui.item.FIRSTUNIT).blur();
				thisTr.find("input[name='price']").val(ui.item.REFCOST).blur();
				thisTr.attr("used","true");
				return false;
			}
		});
	}
	var odt = docTemplate("${docTemp.listMap}","${docTemp.title}","${fn:length(docTemp.inputNames)}","${fn:length(docTemp.footNames)}");
	jQuery(document).ready(function() {
		odt.setDocLayOut("${random}_orderForm","${random}_orderTable_div","${random}_orderForm_foot");
		if("${docPreview}"=="preview"){
			odt.disableButton("${random}_order_toolBar");
			odt.clearData("${random}_orderCard");
		}
		var orderId = "${order.orderId}";
		if(orderId==null || orderId==""){
			orderId = "new";
		}
		var zhGridSetting_purchasePlan = {
			url : "orderDetailGridList?filter_EQS_order.orderId="+orderId,
			datatype : "json",
			mtype : "GET",
			trHeight:25,
			formId:'${random}_orderForm',
			paramName:'orderDetailJson',
			saveUrl:"${ctx}/saveOrder?navTabId=order_gridtable&entityIsNew=${entityIsNew}&saveType=saveStay",
			initColumn:"com.huge.ihos.material.order.model.OrderDetail",
			afterSaveFun : function(data) {
				if("saveStay" == data.callbackType){
					$.pdialog.closeCurrent();
					$.pdialog.open("${ctx}/editOrder?orderId="+data.forwardUrl,"editOrder","订单明细", {mask:true,width:width,height:height});
				}
			},
			colModel:[
				{name:'orderDetailId',index:'orderDetailId',align:'center',label : '<s:text name="orderDetail.orderDetailId" />',hidden:true,key:true},				
				{name:'invDict.invId',index:'invDict.invId',align:'center',label : '<s:text name="inventoryDict.invId" />',hidden:true,sortable:false},	
				{name:'invDict.invName',index:'invDict.invName',align:'left',width:150,label : odt.getListName("invDict.invName"),hidden:!(odt.containColumn("invDict.invName")),highsearch:odt.containColumn("invDict.invName"),editable : true,edittype : 'text',editoptions : {dataInit : invDictOrderComboGrid}},	
				{name:'invDict.invCode',index:'invDict.invCode',align:'left',width:80,label : odt.getListName("invDict.invCode"),hidden:!(odt.containColumn("invDict.invCode")),highsearch:odt.containColumn("invDict.invCode")},	
				{name:'invDict.invModel',index:'invDict.invModel',align:'left',width:120,label : odt.getListName("invDict.invModel"),hidden:!(odt.containColumn("invDict.invModel")),highsearch:odt.containColumn("invDict.invModel")},	
				{name:'invDict.firstUnit',index:'invDict.firstUnit',align:'center',width:80,label : odt.getListName("invDict.firstUnit"),hidden:!(odt.containColumn("invDict.firstUnit")),highsearch:odt.containColumn("invDict.firstUnit")},	
				{name:'amount',index:'amount',align:'right',width:100,label : odt.getListName("amount"),hidden:!(odt.containColumn("amount")),highsearch:odt.containColumn("amount"),editable : true,formatter : 'number'},				
				{name:'price',index:'price',align:'right',width:100,label : odt.getListName("price"),hidden:!(odt.containColumn("price")),highsearch:odt.containColumn("price"),editable : true,formatter : 'number'},				
				{name:'money',index:'money',align:'right',width:110,label : odt.getListName("money"),hidden:!(odt.containColumn("money")),highsearch:odt.containColumn("money"),formatter : 'currency',sortable:false},	
				{name:'arrivalDate',index:'arrivalDate',align:'center',width:80,label : odt.getListName("arrivalDate"),hidden:!(odt.containColumn("arrivalDate")),highsearch:odt.containColumn("arrivalDate"),editable:true,formatter : 'date',formatoptions: {srcformat: 'Y-m-d H:i:s',newformat: 'Y-m-d'}, 
					editoptions : {dataInit :function(elem){jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});jQuery('#ui-datepicker-div').css("z-index", 2000);}}
				},
				{name:'remark',index:'remark',align:'left',width:150,label : odt.getListName("remark"),hidden:!(odt.containColumn("remark")),highsearch:odt.containColumn("remark"),editable : true}				
			],
			jsonReader : {
				root : "orderDetails", // (2)
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
	       		jQuery("#${random}_orderTable").footData([
  				       {name:'invDict.invName',value:'合计:'},
  				       {name:'amount',value:'sum'},
  				       {name:'money',value:'sum'}
   				 ]);
	       	},
	       	beforeEditCell : function(rowid, cellname,value, iRow, iCol) {
	       		/* var store_id = jQuery("#${random}_order_dept").val();
				if(store_id == '' || store_id=='null'){
					alertMsg.error("请选择科室。");
					return;
				}   */
			},
			afterEditCell : function(rowid, cellname,value, iRow, iCol) {
				if(cellname==='amount'){
					var amount= jQuery("#${random}_orderTable").getCellData(rowid,'amount');
					var price= jQuery("#${random}_orderTable").getCellData(rowid,'price');
					amount = isNaN(parseFloat(amount))?0:parseFloat(amount);
					price = isNaN(parseFloat(price))?0:parseFloat(price);
	                jQuery("#${random}_orderTable").setCellData(rowid,'money',amount*price);
				}if(cellname==='invDict.invName'){
					var invId = jQuery("#${random}_orderTable").getCellData(rowid,'invDict.invId');
					var arrivalDate = jQuery("#${random}_orderTable").getCellData(rowid,'arrivalDate');
					if(invId){
						 if(arrivalDate){
				    	   }else{
				    		   jQuery("#${random}_orderTable").setCellData(rowid,'arrivalDate',"${currentSystemVariable.businessDate}");
				    	   }
					}
				}
			},
			afterSaveCell : function(rowid, cellname,value, iRow, iCol) {
			}
		}
		jQuery("#${random}_orderTable").zhGrid(zhGridSetting_purchasePlan);
		var state = "${order.state}";
	if("${docPreview}"!="preview"){
		if("${docPreview}"=="refer"){//从别处通过超链接查看单据
			disableLink(["${random}_order_new","${random}_order_save","${random}_order_importDict","${random}_order_save","${random}_order_check","${random}_order_cancelCheck","${random}_order_abandon","${random}_order_print","${random}_order_printSet"]);
			clearInputClassInOrder();
		}else{
		if(${entityIsNew}){
			disableLink(["${random}_order_check","${random}_order_abandon"]);
		}
		if(state!=0){//审核、作废、完成、部分完成
			disableLink(["${random}_order_save","${random}_order_check","${random}_order_abandon","${random}_order_importDict","${random}_order_print","${random}_order_printSet"]);
			if(state==2 || state ==3 || state==4){//完成
				disableLink(["${random}_order_cancelCheck"]);
			}
			clearInputClassInOrder();
		}else if(state==0){
			jQuery("#${random}_orderTable").fullEdit();
			disableLink(["${random}_order_new","${random}_order_cancelCheck","${random}_order_print","${random}_order_printSet"]);
			jQuery("#${random}_order_dept").treeselect({
				dataType:"sql",
				optType:"single",
				sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 and ysPurchasingDepartment = 1 ORDER BY internalCode",
				exceptnullparent:false,
				lazy:false,
				callback : {
					afterClick : function() {
						$("#${random}_order_buyPerson_id").val("");
						$("#${random}_order_buyPerson").val("");
						orderBuyPersonTreeSelect();
					}
				}
			});
			if(jQuery("#${random}_order_dept").val()){
				orderBuyPersonTreeSelect();
			}
		    jQuery('#${random}_order_makeDate').unbind( 'click' ).bind("click",function(){
		    	WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:"${fns:userContextParam('periodBeginDateStr')}",maxDate:"${fns:userContextParam('periodEndDateStr')}"});
		    });
		    jQuery("#${random}_order_vendor").combogrid({
				url : '${ctx}/comboGridSqlList',
				queryParams : {
					sql : "SELECT v.vendorId vid,v.vendorName vname,v.shortName sname,v.vendorCode vcode from sy_vendor v where disabled = 0 and isMate = 1 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
					cloumns : 'vendorName,cncode,vendorCode'
				},
				rows:10,
				sidx:"VID",
				showOn:false,
				colModel : [ {
					'columnName' : 'VID',
					'width' : '10',
					'label' : 'VID',
					hidden : true,
				}, {
					'columnName' : 'VCODE',
					'width' : '30',
					'align' : 'left',
					'label' : '供应商编码'
				},{
					'columnName' : 'VNAME',
					'width' : '60',
					'align' : 'left',
					'label' : '供应商名称'
				}
				],
				select : function(event, ui) {
						 $(event.target).val(ui.item.VNAME);
						$("#${random}_order_vendor_id").val(ui.item.VID);
					return false; 
				}
			});
		}
		}
	}
	});
	function orderBuyPersonTreeSelect(){
		setTimeout(function(){
			var sql = "SELECT p.personId id ,p.name name,1 parent FROM t_person p where p.disabled = 0 and p.dept_id='"+$("#${random}_order_dept_id").val()+"'";
			 jQuery("#${random}_order_buyPerson").treeselect({
				dataType:"sql",
				optType:"single",
				sql:sql,
				exceptnullparent:false,
				lazy:false
			}); 
		},50);
	}
	function clearInputClassInOrder(){
		jQuery("#${random}_order_procType").removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_order_vendor").removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_order_dept").removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_order_makeDate").removeClass('Wdate').removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_order_buyPerson").removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_order_transType").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_order_payCon").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_order_cost").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_order_bargain").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_order_taxRate").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_order_arrAddr").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_order_remark").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
	}
</script>
<script type="text/javascript">
	function newOrder(){
		$.pdialog.closeCurrent();
		$.pdialog.open('${ctx}/editOrder','addOrder',"添加订单", {mask:true,width : width,height : height,resizable:false});
	}
	function importDictToOrder(){
		var store_id = jQuery("#${random}_order_store_id").val();
		if(store_id == '' || store_id=='null'){
			alertMsg.error("请选择仓库。");
			return;
		} 
		var url = "${ctx}/invInStoreList?popup=true&fromType=order&random=${random}";
		if("${selectInvDictByVendor}"=="1"){
			var vendorId = jQuery("#${random}_order_vendor_id").val();
			if(vendorId){
				url += "&vendorId="+vendorId;
			}
		}
		var winTitle="<s:text name='材料选择'/>";
		url = encodeURI(url);
		$.pdialog.open(url, 'importDictToOrder', winTitle, {mask:true,width:width,height:height,resizable:false,maxable:false});
	}
	function saveOrder(){
		jQuery("#${random}_orderTable").saveGrid();
	}
	function auditOrder(){
		var gridEdited = jQuery("#${random}_orderTable")[0].p.gridEdited;
		if(gridEdited){
			alertMsg.error("单据内容有修改，请先保存！");
			return ;
			
		}
		var editUrl = "orderGridEdit?id=${order.orderId}&navTabId=order_gridtable&oper=check";
		alertMsg.confirm("确认审核？", {
			okCall : function() {
				$.post(editUrl,function(data) {
					$.pdialog.reload("${ctx}/editOrder?popup=true&orderId=${order.orderId}",{dialogId:'editOrder'});
					formCallBack(data);
				});
			}
		});
	}
	function antiAuditOrder(){
		var editUrl = "orderGridEdit?id=${order.orderId}&navTabId=order_gridtable&oper=cancelCheck";
		alertMsg.confirm("确认销审？", {
			okCall : function() {
				$.post(editUrl,function(data) {
					$.pdialog.reload("${ctx}/editOrder?popup=true&orderId=${order.orderId}",{dialogId:'editOrder'});
					formCallBack(data);
				});
			}
		});
	}
	function abandonOrStopOrder(oper){
		var editUrl = "orderGridEdit?id=${order.orderId}&navTabId=order_gridtable&oper="+oper;
		alertMsg.confirm("确认"+(oper=="abandon"?"作废":"中止")+"订单？", {
			okCall : function() {
				$.post(editUrl,function(data) {
					$.pdialog.closeCurrent();
					formCallBack(data);
				});
			}
		});
	}
	function printOrder(){
		/* var orderCardHtml = jQuery("#orderCard").html();
		jQuery("body").append("<div id='page1'>"+orderCardHtml+"</div>");
		jQuery("#orderTable_head",'#page1').css('width','99%');
		jQuery("#orderTable",'#page1').css('width','99%');
		jQuery("#orderTable_foot",'#page1').css('width','99%');
		jQuery("#orderTable_div",'#page1').css('width','auto');
		jQuery("#orderTable_div",'#page1').css('height','auto');
		return;
		doPrint(); */
	}
	function printSetOrder(){
		
	}
	function closeOrder(){
		var gridEdited = jQuery("#${random}_orderTable")[0].p.gridEdited;
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
</head>

<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar" id="${random}_order_toolBar">
				<li>
					<a id="${random}_order_new" class="addbutton" href="javaScript:newOrder()" ><span><s:text name="button.newDoc" /></span></a>
				</li>
				<li>
					<a id="${random}_order_importDict" class="getdatabutton" href="javaScript:importDictToOrder()"><span>选择材料</span></a>
				</li>
				<li>
					<a id="${random}_order_save" class="savebutton" href="javaScript:saveOrder()"><span><s:text name="button.save" /></span> </a>
				</li>
				<li>
					<a id="${random}_order_check" class="checkbutton"  href="javaScript:auditOrder()"><span><s:text name="button.check" /></span></a>
				</li>
				<li>
					<a id="${random}_order_cancelCheck" class="delallbutton"  href="javaScript:antiAuditOrder()"><span><s:text name="button.cancelCheck" /></span></a>
				</li>
				<li>
					<a id="${random}_order_abandon" class="disablebutton"  href="javaScript:abandonOrStopOrder('abandon')"><span><s:text name="button.abandon" /></span></a>
				</li>
				<%-- <li>
					<a id="${random}_order_stop" class="confirmbutton"  href="javaScript:abandonOrStopOrder('stop')" style="display:none"><span><s:text name="中止订单" /></span></a>
				</li> --%>
				<li>
					<a id="${random}_order_print" class="printbutton" href="javaScript:printOrder()"><span><s:text name="button.print" /></span> </a>
				</li>
				<li>
					<a id="${random}_order_printSet" class="settingbutton" href="javaScript:printOrder()"><span><s:text name="button.printSet" /></span> </a>
				</li>
				<li>
					<a id="${random}_order_setColShow" class="settlebutton"  href="javaScript:setColShow('${random}_orderTable','com.huge.ihos.material.order.model.OrderDetail','zhGrid')"><span><s:text name="button.setColShow" /></span></a>
				</li>
				<li>
					<a id="${random}_order_close" class="closebutton" href="javaScript:closeOrder()"><span><s:text name="button.close" /></span> </a>
				</li>
			</ul>
		</div>
		<div id="${random}_orderCard" autoBreakPage='*' style="margin:5px 10px 5px 5px">
			<form id="${random}_orderForm">
				<s:if test="%{!entityIsNew}">
					<span style="position:absolute;right:14px;font-size:18px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px">No.${order.orderNo}</span>
				</s:if>
				<div align="center" style="font-size:25px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px;margin:0 auto;">
					${docTemp.title}
					<hr style="color:rgb(24, 127, 207);margin-top:0px"/>
					<hr style="color:rgb(24, 127, 207);margin-top:-11px"/>
				</div>
				<div>
					<s:hidden key="order.orderId"/>
					<s:hidden key="order.orderNo"/>
					<s:hidden key="order.orgCode"/>
					<s:hidden key="order.copyCode"/>
					<s:hidden key="order.periodMonth"/>
					<s:hidden key="order.isNotify"/>
					<s:hidden key="order.state"/>
					<s:hidden key="order.docTemId"/>
					<s:hidden key="order.makePerson.personId"/>
				</div>
				<div style="border:0;width:100%;table-layout:fixed;margin-top:-10px;">
					<c:forEach items="${docTemp.inputList}" var="input">
						<span class="docInputArea" id="${random}_${input.value}_${input.necessary}">&nbsp;&nbsp;&nbsp; 
							<c:set var="inputType" value="${input.type}" scope="page"/> 
							<c:set var="inputKey" value="${input.value}" scope="page"/>
							<c:set var="referId" value="${input.referId}" scope="page"/>
							<c:set var="referName" value="${input.referName}" scope="page"/>
							<c:if test="${inputKey!='procType' && inputKey!='transType' && inputKey!='payCon'}" >
								<label><c:out value="${input.name}"/>:</label>
							</c:if>
							<c:if test="${inputType=='refer'}" >
								<input type="text" id="${random}_order_${inputKey}" name="order.${inputKey}.${referName}" value="${order[inputKey][referName]}" />
								<input type="hidden" id="${random}_order_${inputKey}_id" name="order.${inputKey}.${referId}" value="${order[inputKey][referId]}"/>
							</c:if>
							<c:if test="${inputType=='date'}" >
								<input type="text" id="${random}_order_${inputKey}" name="order.${inputKey}" value="<fmt:formatDate value='${order[inputKey]}' pattern='yyyy-MM-dd' />" class="Wdate"  style="height:15px;width:80px"/>
							</c:if>
							<c:if test="${inputType=='select'}" >
								<c:if test="${inputKey=='procType'}">
									<s:if  test="%{order.state==0}">
										<s:select key="order.procType" name="order.procType.typeId" value="%{order.procType.typeId}" cssClass="required" cssStyle="width:100px"
										list="procTypeList" listKey="typeId" listValue="typeName" emptyOption="true"></s:select>
									</s:if>
									<s:else>
										<label><c:out value="${input.name}"/>:</label>
										<input id="${random}_order_procType" value="${order.procType.typeName}" name="order.procType.typeName" class="required"/>
										<input type="hidden" id="${random}_order_procType_id" name="order.procType.typeId" value="${order.procType.typeId}"/>
									</s:else>									
									</c:if>
								<c:if test="${inputKey=='transType'}">
								<s:if test="%{order.state==0}">
										<s:select key="order.transType" name="order.transType" list="#{'':'--','1':'航空运输','2':'河海运输','3':'汽车运输','4':'铁路运输','5':'邮件运输'}" style="width:100px" ></s:select>
								</s:if>
									<s:else>
										<label><c:out value="${input.name}"/>:</label>
									<c:choose>  
       										<c:when test="${order.transType=='1'}">
       										<input type="text" id="${random}_order_transType" value="航空运输" name="order.transType" class=""/>
       										</c:when>
       										<c:when test="${order.transType=='2'}">
       										<input type="text" id="${random}_order_transType" value="河海运输" name="order.transType" class=""/>
       										</c:when>
       										<c:when test="${order.transType=='3'}">
       										<input type="text" id="${random}_order_transType" value="汽车运输" name="order.transType" class=""/>
       										</c:when>
       										<c:when test="${order.transType=='4'}">
       										<input type="text" id="${random}_order_transType" value="铁路运输" name="order.transType" class=""/>
       										</c:when>
       										<c:when test="${order.transType=='5'}">
       										<input type="text" id="${random}_order_transType" value="邮件运输" name="order.transType" class=""/>
       										</c:when>
       										 <c:otherwise>  
       										 <input type="text" id="${random}_order_transType" value="" name="order.transType" class=""/>
       										 </c:otherwise>
       									</c:choose>   
									</s:else>								
								</c:if>
								<c:if test="${inputKey=='payCon'}">
							    <s:if  test="%{order.state==0}">
										<s:select key="order.payCon" name="order.payCon.payConId" value="%{order.payCon.payConId}" cssClass="" cssStyle="width:80px"
										list="payConList" listKey="payConId" listValue="payConName" emptyOption="false"></s:select>
								</s:if>
									<s:else>
										<label><c:out value="${input.name}"/>:</label>
										<input type="text" id="${random}_order_payCon" value="${order.payCon.payConName}" name="order.payCon.payConName" class=""/>
										<input type="hidden" id="${random}_order_payCon_id" name="order.payCon.payConId" value="${order.payCon.payConId}"/>
									</s:else>
								</c:if>
								</c:if>
							<c:if test="${inputType=='simple'}" >
								<input type="text" id="${random}_order_${inputKey}" name="order.${inputKey}" value="${order[inputKey]}" />
							</c:if>
							<script>
								if("${order.state}"=="0" && ${input.necessary}){
									jQuery("#${random}_${input.value}_${input.necessary} input[type='text']").addClass("required");
								}
							</script>
						</span>
					</c:forEach> 
				</div>
			</form>
			<div id="${random}_orderTable_div" class="zhGrid_div">
				<table id="${random}_orderTable"></table>
			</div>
			<div style="height:26px" id="${random}_orderForm_foot">
				<ul style="float:left;width:100%;height:20px;margin-top:8px;text-align:center">
					<c:forEach items="${docTemp.footMap}" var="foot">
						<li style="float:left;">
							<label><c:out value="${foot.value}"/>:</label>
							<c:if test="${foot.key!='sign'}">
								<c:set var="footKey" value="${foot.key}" scope="page"/>
								<input class="lineInput" size="8" value="${order[footKey].name}" readonly="readonly"/>
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





