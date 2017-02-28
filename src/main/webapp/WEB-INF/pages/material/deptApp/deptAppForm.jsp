<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script type="text/javascript">
function invDictDeptAppComboGrid(elem) {
	var elemId = jQuery(elem).attr("id");
	elemId=elemId.replace(".","_");
	jQuery(elem).attr("id",elemId);
	$(elem).combogrid({
		url : '${ctx}/comboGridSqlList',
		queryParams : {
			sql : "select invId,invCode,invName,invModel,firstUnit,isNULL(refCost,0) refCost,factory from v_mm_invInstore "+
				  "where orgCode='"+"${fns:userContextParam('orgCode')}"+"' and copyCode='"+"${fns:userContextParam('copyCode')}"+"' "+
				  "and storeId in ('1','"+jQuery("#${random}_deptApp_store_id").val()+"') and isApply = 1",
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
			'width' : '15',
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
			'columnName' : 'FACTORY',
			'width' : '30',
			'align' : 'left',
			'label' : '生产厂商'
		  }, {
			'columnName' : 'REFCOST',
			'width' : '10',
			'align' : 'right',
			'label' : '参考进价'
		  }
		],
		_create: function( event, item ) {
		},
		select : function(event, ui) {
			var thisTr = jQuery("#${random}_deptAppTable").getTr($(elem));
			var rowid = thisTr.attr("id");
			thisTr.find("input[name='invDict.invId']").val(ui.item.INVID).blur();
			thisTr.find("input[name='invDict.invName']").val(ui.item.INVNAME).blur();
			thisTr.find("input[name='invDict.invCode']").val(ui.item.INVCODE).blur();
			thisTr.find("input[name='invDict.invModel']").val(ui.item.INVMODEL).blur();
			thisTr.find("input[name='invDict.firstUnit']").val(ui.item.FIRSTUNIT).blur();
			thisTr.find("input[name='invDict.factory']").val(ui.item.FACTORY).blur();
			thisTr.find("input[name='appPrice']").val(ui.item.REFCOST).blur();
			thisTr.attr("used","true");
			return false;
		}
	});
} 
var dadt = docTemplate("${docTemp.listMap}","${docTemp.title}","${fn:length(docTemp.inputNames)}","${fn:length(docTemp.footNames)}");
</script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		dadt.setDocLayOut("${random}_deptAppForm","${random}_deptAppTable_div","${random}_deptAppForm_foot");
		if("${docPreview}"=="preview"){
			dadt.disableButton("${random}_deptApp_toolBar");
			dadt.clearData("${random}_deptAppCard");
		}else{
			if("${docPreview}"=="refer"){//从别处通过超链接查看单据
				disableLink(["${random}_deptApp_new","${random}_deptApp_select_dict","${random}_deptApp_import_deptNeedPlan","${random}_deptApp_save","${random}_deptApp_check","${random}_deptApp_cancelCheck","${random}_deptApp_send","${random}_deptApp_abandon","${random}_deptApp_print","${random}_deptApp_printSet"]);
				clearInputClassInDeptApp();
			}else{
				if(${entityIsNew}){
					disableLink(["${random}_deptApp_new","${random}_deptApp_check","${random}_deptApp_abandon"]);
				}
				if("${deptApp.appState}"=="0"){//------------------------initialize button state and input data for add or appState=0
					disableLink(["${random}_deptApp_cancelCheck","${random}_deptApp_send","${random}_deptApp_print","${random}_deptApp_printSet"]);
					jQuery("#${random}_deptApp_store").treeselect({
						dataType : "sql",
						optType : "single",
						sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
						exceptnullparent : false,
						lazy : false
					}); 
					jQuery("#${random}_deptApp_appDept").treeselect({
						dataType:"sql",
						optType:"single",
						sql:"SELECT deptId id,name,parentDept_id parentId ,internalCode FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode",
						exceptnullparent:false,
						lazy:false,
						callback : {
							afterClick : function() {
								jQuery("#${random}_deptApp_appPerson").val("");
								jQuery("#${random}_deptApp_appPerson_id").val("");
							}
						}
					});
					jQuery('#${random}_deptApp_appDate').unbind( 'click' ).bind("click",function(){
				    	WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:"${fns:userContextParam('periodBeginDateStr')}",maxDate:"${fns:userContextParam('periodEndDateStr')}"});
				    });
					jQuery("#${random}_deptApp_appPerson").focus(function(){
						var appDept = jQuery("#${random}_deptApp_appDept").val();
						if(appDept && appDept!=''){
							jQuery("#${random}_deptApp_appPerson").treeselect({
								dataType:"sql",
								optType:"single",
								sql:"SELECT p.personId id ,p.name name FROM t_person p where p.disabled = 0 and p.dept_id='"+$("#${random}_deptApp_appDept_id").val()+"'",
								exceptnullparent:false,
								lazy:false
							}); 
						}else{
							alertMsg.error("请先填写申领科室");
							return;
						}
					});
				}else{//-----------------------appState==1[checked]
					disableLink(["${random}_deptApp_select_dict","${random}_deptApp_import_deptNeedPlan","${random}_deptApp_save","${random}_deptApp_check","${random}_deptApp_print","${random}_deptApp_printSet"]);
					if("${deptApp.appState}"=="1"){
						disableLink(["${random}_deptApp_abandon"]);
					}else if("${deptApp.appState}"=="2"){
						disableLink(["${random}_deptApp_send","${random}_deptApp_cancelCheck","${random}_deptApp_abandon"]);
					}
					clearInputClassInDeptApp();
				}
			}
		}
		//---------------------------------init deptAppDetialGridList
		var deptAppId = "${deptApp.deptAppId}";
		if(deptAppId==null || deptAppId==""){
			deptAppId = "new";
		}
		var zhGridSetting_deptApp = {
			url : "deptAppDetailGridList?filter_EQS_deptApp.deptAppId="+deptAppId,
			datatype : "json",
			mtype : "GET",
			trHeight:25,
			formId:'${random}_deptAppForm',
			paramName:'deptAppDetailJson',
			saveUrl:"${ctx}/saveDeptApp?navTabId=deptApp_gridtable&entityIsNew=${entityIsNew}&saveType=saveStay",
			initColumn:"com.huge.ihos.material.deptapp.model.DeptAppDetail",
			afterSaveFun : function(data) {
				formCallBack(data);
				if("saveStay" == data.callbackType){
					$.pdialog.closeCurrent();
					$.pdialog.open("${ctx}/editDeptApp?deptAppId="+data.forwardUrl,"editDeptApp","科室申领单明细", {mask:true,width:width,height:height});
				}
			},
			colModel:[
				{name:'deptAppDetailId',index:'deptAppDetailId',align:'center',label : '<s:text name="deptAppDetail.deptAppDetailId" />',hidden:true,key:true,editable : false,sortable:false},	
				{name:'invDict.invId',index:'invDict.invId',align:'center',label : '<s:text name="inventoryDict.invId" />',hidden:true,sortable:false},	
				{name:'invDict.invName',index:'invDict.invName',align:'left',width:150,label : dadt.getListName("invDict.invName"),hidden:!(dadt.containColumn("invDict.invName")),highsearch:dadt.containColumn("invDict.invName"),editable : true,edittype : 'text',editoptions : {dataInit : invDictDeptAppComboGrid}},	
				{name:'invDict.invCode',index:'invDict.invCode',align:'left',width:80,label : dadt.getListName("invDict.invCode"),hidden:!(dadt.containColumn("invDict.invCode")),highsearch:dadt.containColumn("invDict.invCode")},	
				{name:'invDict.invModel',index:'invDict.invModel',align:'left',width:120,label : dadt.getListName("invDict.invModel"),hidden:!(dadt.containColumn("invDict.invModel")),highsearch:dadt.containColumn("invDict.invModel")},	
				{name:'invDict.firstUnit',index:'invDict.firstUnit',align:'center',width:80,label : dadt.getListName("invDict.firstUnit"),hidden:!(dadt.containColumn("invDict.firstUnit")),highsearch:dadt.containColumn("invDict.firstUnit")},	
				{name:'invDict.factory',index:'invDict.factory',align:'center',width:150,label : dadt.getListName("invDict.factory"),hidden:!(dadt.containColumn("invDict.factory")),highsearch:dadt.containColumn("invDict.factory")},	
				{name:'appPrice',index:'appPrice',align:'right',width:100,label : dadt.getListName("appPrice"),hidden:!(dadt.containColumn("appPrice")),highsearch:dadt.containColumn("appPrice"),formatter : 'currency'},	
				{name:'appAmount',index:'appAmount',align:'right',width:100,label : dadt.getListName("appAmount"),hidden:!(dadt.containColumn("appAmount")),highsearch:dadt.containColumn("appAmount"),editable : true,formatter : 'number'},	
				{name:'appMoney',index:'appMoney',align:'right',width:100,label : dadt.getListName("appMoney"),hidden:!(dadt.containColumn("appMoney")),highsearch:dadt.containColumn("appMoney"),formatter : 'currency'},	
				{name:'remark',index:'remark',align:'left',width:150,label : dadt.getListName("remark"),hidden:!(dadt.containColumn("remark")),highsearch:dadt.containColumn("remark"),editable : true}
			  ],
		  	jsonReader : {
				root : "deptAppDetails", // (2)
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
	       		jQuery("#${random}_deptAppTable").footData([
  				       {name:'invDict.invName',value:'合计:'},
  				       {name:'appAmount',value:'sum'},
  				       {name:'appMoney',value:'sum'}
   				 ]);
	       	},
	       	beforeEditCell : function(rowid, cellname,value, iRow, iCol) {
	       		var store_id = jQuery("#${random}_deptApp_store").val();
				if(store_id == '' || store_id=='null'){
					alertMsg.error("请选择仓库。");
					return;
				}  
			},
			afterEditCell : function(rowid, cellname,value, iRow, iCol) {
				if(cellname==='appAmount'){
					var amount= jQuery("#${random}_deptAppTable").getCellData(rowid,'appAmount');
					var price= jQuery("#${random}_deptAppTable").getCellData(rowid,'appPrice');
					amount = isNaN(parseFloat(amount))?0:parseFloat(amount);
					price = isNaN(parseFloat(price))?0:parseFloat(price);
	                jQuery("#${random}_deptAppTable").setCellData(rowid,'appMoney',price*amount);
				}
			},
			afterSaveCell : function(rowid, cellname,value, iRow, iCol) {
			}
		}
		jQuery("#${random}_deptAppTable").zhGrid(zhGridSetting_deptApp);
		if("${deptApp.appState}"=="0" &&　"${docPreview}"!="preview"){
			jQuery("#${random}_deptAppTable").fullEdit();
		}
	});
	function clearInputClassInDeptApp(){
		jQuery("#${random}_deptApp_store").removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_deptApp_appDept").removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_deptApp_appPerson").removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_deptApp_appDate").removeClass('Wdate').removeClass('required').removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#${random}_deptApp_remark").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
	}
</script>
<script type="text/javascript">
	//---------------------------------functions for button in tooBar 
	function newDeptApp(){
		$.pdialog.closeCurrent();
		$.pdialog.open('${ctx}/editDeptApp','addDeptApp',"添加科室申领单", {mask:true,width : width,height : height,resizable:false});
	}
	function selectDictForDeptApp(){
		var store_id = jQuery("#${random}_deptApp_store_id").val();
		if(store_id == '' || store_id=='null'){
			alertMsg.error("请选择仓库。");
			return;
		} 
		var url = "${ctx}/invInStoreList?popup=true&storeId="+store_id+"&fromType=deptApp&random=${random}";
		var winTitle="<s:text name='材料选择'/>";
		url = encodeURI(url);
		$.pdialog.open(url, 'selectDictForDeptApp', winTitle, {mask:true,width:width,height:height,resizable:false,maxable:false});
	}
	function importDeptNeedToApp(){
		var store_id = jQuery("#${random}_deptApp_store_id").val();
		var dept_id = jQuery("#${random}_deptApp_appDept_id").val();
		if(store_id == '' || store_id=='null'){
			alertMsg.error("请选择仓库。");
			return;
		} 
		if(dept_id == '' || dept_id=='null'){
			alertMsg.error("请选择申领科室。");
			return;
		} 
		$.pdialog.open('${ctx}/selectDeptNeedPlanList?random=${random}&storeId='+store_id+'&needDeptId='+dept_id,'selectDeptNeedPlan',"选择需求计划", {mask:true,width : 960,height : 628,resizable:false});
	}
	function saveDeptApp(){
		/* var storeId = jQuery("#${random}_deptApp_store_id").val();
		var appDeptId = jQuery("#${random}_deptApp_appDept_id").val();
		var appPersonId = jQuery("#${random}_deptApp_appPerson_id").val();
		if(!storeId){
			alertMsg.error("请选择有效的仓库");
			return;
		}
		if(!appDeptId){
			alertMsg.error("请选择有效的申领科室");
			return;
		}
		if(!appPersonId){
			alertMsg.error("请选择有效的申领人");
			return;
		} */
		jQuery("#${random}_deptAppTable").saveGrid();
	}
	function deptAppFormEditOper(type){
    	if(type=="check"){
    		var gridEdited = jQuery("#${random}_deptAppTable")[0].p.gridEdited;
    		if(gridEdited){
    			alertMsg.error("单据内容有修改，请先保存！");
    			return ;
    		}
    	}
		var editUrl = "deptAppGridEdit?id=${deptApp.deptAppId}&navTabId=deptApp_gridtable&oper="+type;
		alertMsg.confirm("确认"+(type=="check"?"审核":(type=="cancelCheck"?"销审":(type=="send"?"发送":"作废")))+"？", {
			okCall : function() {
				$.post(editUrl,function(data) {
					if(type=="send"){
						$.pdialog.closeCurrent();					
					}else{
						$.pdialog.reload("${ctx}/editDeptApp?popup=true&deptAppId=${deptApp.deptAppId}",{dialogId:'editDeptApp'});
					}
					formCallBack(data);
				});
			}
		});
	}
	function printDeptApp(){
		/* var deptAppCardHtml = jQuery("#deptAppCard").html();
		jQuery("body").append("<div id='page1'>"+deptAppCardHtml+"</div>");
		jQuery("#deptAppTable_head",'#page1').css('width','99%');
		jQuery("#deptAppTable",'#page1').css('width','99%');
		jQuery("#deptAppTable_foot",'#page1').css('width','99%');
		jQuery("#deptAppTable_div",'#page1').css('width','auto');
		jQuery("#deptAppTable_div",'#page1').css('height','auto');
		return;
		doPrint(); */
	}
	function printSetDeptApp(){}
	function closeDeptApp(){
		var gridEdited = jQuery("#${random}_deptAppTable")[0].p.gridEdited;
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
			<ul class="toolBar" id="${random}_deptApp_toolBar">
				<li>
					<a id="${random}_deptApp_new" class="addbutton" href="javaScript:newDeptApp()" ><span><s:text name="button.newDoc" /></span></a>
				</li>
				<li>
					<a id="${random}_deptApp_select_dict" class="getdatabutton" href="javaScript:selectDictForDeptApp()" ><span>选择材料</span></a>
				</li>
				<li>
					<a id="${random}_deptApp_import_deptNeedPlan" class="inheritbutton" href="javaScript:importDeptNeedToApp()" ><span>需求计划导入</span></a>
				</li>
				<li>
					<a id="${random}_deptApp_save" class="savebutton" href="javaScript:saveDeptApp()"><span><s:text name="button.save" /></span> </a>
				</li>
				<li>
					<a id="${random}_deptApp_check" class="checkbutton"  href="javaScript:deptAppFormEditOper('check')"><span><s:text name="button.check" /></span></a>
				</li>
				<li>
					<a id="${random}_deptApp_cancelCheck" class="delallbutton"  href="javaScript:deptAppFormEditOper('cancelCheck')"><span><s:text name="button.cancelCheck" /></span></a>
				</li>
				<li>
					<a id="${random}_deptApp_send" class="confirmbutton"  href="javaScript:deptAppFormEditOper('send')"><span>发送</span></a>
				</li>
				<li>
					<a id="${random}_deptApp_abandon" class="disablebutton"  href="javaScript:deptAppFormEditOper('abandon')"><span><s:text name="button.abandon" /></span></a>
				</li>
				<li>
					<a id="${random}_deptApp_print" class="printbutton" href="javaScript:printDeptApp()"><span><s:text name="button.print" /></span> </a>
				</li>
				<li>
					<a id="${random}_deptApp_printSet" class="settingbutton" href="javaScript:printSetDeptApp()"><span><s:text name="button.printSet" /></span> </a>
				</li>
				<li>
					<a id="${random}_deptApp_setColShow" class="settlebutton"  href="javaScript:setColShow('${random}_deptAppTable','com.huge.ihos.material.deptapp.model.DeptAppDetail','zhGrid')"><span><s:text name="button.setColShow" /></span></a>
				</li>
				<li>
					<a id="${random}_deptApp_close" class="closebutton" href="javaScript:closeDeptApp()"><span><s:text name="button.close" /></span> </a>
				</li>
			</ul>
		</div>
		<div id="${random}_deptAppCard" autoBreakPage='*' style="margin:5px 10px 5px 5px">
			<form id="${random}_deptAppForm">
				<s:if test="%{!entityIsNew}">
					<span style="position:absolute;right:14px;font-size:18px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px">No.${deptApp.appNo}</span>
				</s:if>
				<div align="center" style="font-size:25px;color:rgb(24, 127, 207);font-family:宋体;margin-top:2px;margin:0 auto;">
					 ${docTemp.title}
					<hr style="color:rgb(24, 127, 207);margin-top:0px"/>
					<hr style="color:rgb(24, 127, 207);margin-top:-11px"/>
				</div>
				<div>
					<s:hidden key="deptApp.deptAppId"/>
					<s:hidden key="deptApp.appNo"/>
					<s:hidden key="deptApp.orgCode"/>
					<s:hidden key="deptApp.copyCode"/>
					<s:hidden key="deptApp.yearMonth"/>
					<s:hidden key="deptApp.appState"/>
					<s:hidden key="deptApp.docTemId"/>
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
								<input type="text" id="${random}_deptApp_${inputKey}" name="deptApp.${inputKey}.${referName}" value="${deptApp[inputKey][referName]}" />
								<input type="hidden" id="${random}_deptApp_${inputKey}_id" name="deptApp.${inputKey}.${referId}" value="${deptApp[inputKey][referId]}"/>
							</c:if>
							<c:if test="${inputType=='date'}" >
								<input type="text" id="${random}_deptApp_${inputKey}" name="deptApp.${inputKey}" value="<fmt:formatDate value='${deptApp[inputKey]}' pattern='yyyy-MM-dd' />" class="Wdate"  style="height:15px;width:80px"/>
							</c:if>
							<c:if test="${inputType=='simple'}" >
								<input type="text" id="${random}_deptApp_${inputKey}" name="deptApp.${inputKey}" value="${deptApp[inputKey]}"/>
							</c:if>
							<script>
								if("${deptApp.appState}"=="0" && ${input.necessary}){
									jQuery("#${random}_${input.value}_${input.necessary} input[type='text']").addClass("required");
								}
							</script>
						</span>
					</c:forEach>
				</div>
			</form>
			<div id="${random}_deptAppTable_div" class="zhGrid_div">
				<table id="${random}_deptAppTable"></table>
			</div>
			<div id="${random}_deptAppForm_foot" style="height:26px">
				<ul style="float:left;width:100%;height:20px;margin-top:8px;text-align:center">
					<c:forEach items="${docTemp.footMap}" var="foot">
						<li style="float:left;">
							<label><c:out value="${foot.value}"/>:</label>
							<c:if test="${foot.key!='sign'}">
									<c:set var="footKey" value="${foot.key}" scope="page"/>
									<input class="lineInput" size="8" value="${deptApp[footKey].name}" readonly="readonly"/>
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





