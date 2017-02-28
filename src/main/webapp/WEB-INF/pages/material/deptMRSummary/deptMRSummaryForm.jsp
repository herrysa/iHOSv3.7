<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	//var dnpdt = docTemplate("${docTemp.listMap}","${docTemp.title}","${fn:length(docTemp.inputNames)}","${fn:length(docTemp.footNames)}");
	jQuery(document).ready(function() {
		<%--dnpdt.setDocLayOut("${random}_deptMRSummaryForm","${random}_deptMRSummaryTable_div","${random}_deptMRSummaryForm_foot");
		if("${docPreview}"=="preview"){
			dnpdt.disableButton("${random}_deptMRSummary_toolBar");
			dnpdt.clearData("${random}_deptMRSummaryCard");
		}--%>		
		 var mrId = "${deptMRSummary.mrId}";
		 var purchaseId="${deptMRSummary.purchaseId}";
		 var urlSummaryList = "deptMRSummaryDetailGridList";
		if(mrId==null || mrId==""){
			mrId = "new";
		}
		var zhGridSetting_deptMRSummary = {
			url : "deptMRSummaryDetailGridList?filter_EQS_deptMRSummary.mrId="+mrId,
			datatype : "json",
			mtype : "GET",
			trHeight:25,
			formId:'${random}_deptMRSummaryForm',
			paramName:'deptMRSummaryDetailJson',
			saveUrl:"${ctx}/saveDeptMRSummary?navTabId=deptMRSummary_gridtable&entityIsNew=${entityIsNew}&saveType=saveStay",
			initColumn:"com.huge.ihos.material.deptplan.model.DeptMRSummaryDetail",
			afterSaveFun : function(data) {
				if("saveStay" == data.callbackType){
					$.pdialog.closeCurrent();
					$.pdialog.open("${ctx}/editDeptMRSummary?mrId="+data.forwardUrl,"editDeptMRSummary","科室需求计划汇总明细", {mask:true,width:width,height:height});
				}
			},
			colModel:[
				{name:'mrDetailId',index:'mrDetailId',align:'center',label : '<s:text name="deptMRSummaryDetail.mrDetailId" />',hidden:true,key:true,editable : false,sortable:false},	
				{name:'invDict.invId',index:'invDict.invId',align:'center',label : '<s:text name="inventoryDict.invId" />',hidden:true,sortable:false},	
				{name:'invDict.invName',index:'invDict.invName',align:'left',width:150,label : '<s:text name="invDict.invName" />',hidden:false,highsearch:true,editable : false,edittype : 'text'},	
				{name:'invDict.invCode',index:'invDict.invCode',align:'left',width:80,label : '<s:text name="invDict.invCode" />',hidden:false,highsearch:true},	
				{name:'invDict.invModel',index:'invDict.invModel',align:'left',width:120,label : '<s:text name="invDict.invModel" />',hidden:false,highsearch:true},	
				{name:'invDict.firstUnit',index:'invDict.firstUnit',align:'center',width:80,label : '<s:text name="invDict.firstUnit" />',hidden:false,highsearch:true},	
				{name:'amount',index:'amount',align:'right',width:100,label : '<s:text name="DeptMRSummaryDetail.amount" />',hidden:false,highsearch:true,editable : false,formatter : 'number'},	
				{name:'price',index:'price',align:'right',width:100,label :'<s:text name="DeptMRSummaryDetail.price" />',hidden:false,highsearch:true,formatter : 'number'},	
				{name:'money',index:'money',align:'right',width:110,label : '<s:text name="DeptMRSummaryDetail.money" />',hidden:false,highsearch:true,formatter : 'currency',sortable:false},
				{name:'makeDate',index:'makeDate',align:'center',width:80,label :'<s:text name="DeptMRSummaryDetail.makeDate" />' ,hidden:false,highsearch:true,editable:false,formatter : 'date',formatoptions: {srcformat: 'Y-m-d H:i:s',newformat: 'Y-m-d'}},
				{name:'remark',index:'remark',align:'left',width:150,label :'<s:text name="DeptMRSummaryDetail.remark" />',hidden:false,highsearch:true,editable : true}
			  ],
		  	jsonReader : {
				root : "deptMRSummaryDetails", // (2)
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
	       		jQuery("#${random}_deptMRSummaryTable").footData([
  				       {name:'invDict.invName',value:'合计:'},
  				       {name:'amount',value:'sum'},
  				       {name:'money',value:'sum'}
   				 ]);
	       	}
		}
		jQuery("#${random}_deptMRSummaryTable").zhGrid(zhGridSetting_deptMRSummary);
		if(!${entityIsNew}){			
				if(purchaseId==null||purchaseId==""){
					if(("${loadtype}"=="select")){
						clearInputClassInDeptNeedPlan();
					}else{
						readonlyInputClassInDeptNeedPlan();
					}
					
				}else{
					clearInputClassInDeptNeedPlan();	
				}					
		}else{
			jQuery("#${random}_deptMRSummaryTable").fullEdit();
			jQuery("#deptMRSummaryDetail_search_store").treeselect({
				dataType : "sql",
				optType : "single",
				sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
				exceptnullparent : false,
				lazy : false
 			}); 
		}

			
			jQuery("#${random}_deptMRSummary_new").unbind( 'click' ).bind("click",function(){
				var storeId = jQuery("#deptMRSummaryDetail_search_store_id").val();
				if(storeId == '' || storeId=='null'){
					alertMsg.error("请选择目标仓库。");
					return;
				}  
				var planType = $("input[name='deptMRSummary.planType']:[checked]").val();
				var urlString = "${ctx}/deptMRSummaryDetailGridList?";
				var checkurl = "${ctx}/deptMRSummaryDetailGridEdit?oper=check";
				urlString = urlString
					+ "&store=" + storeId + "&planType=" + planType; 
				checkurl = checkurl
					+ "&store=" + storeId + "&planType=" + planType; 
				urlString=encodeURI(urlString);
				$.post(checkurl,function(data) {
					if(data.callbackType == "exist"){
						alertMsg.confirm("存在未审核单据,是否继续汇总？", {
							okCall : function() {
								jQuery("#${random}_deptMRSummaryTable").setGridParam({url:urlString}).reload();	
							}
						});		
					}else if(data.callbackType == "error"){
						alertMsg.error("存在未审核单据不能汇总！");
						return;
					}else if(data.callbackType == "paramerror"){
						alertMsg.error("系统参数（是否忽略未审核单据）错误，只能为1或者0");
						return;
					}else{
						jQuery("#${random}_deptMRSummaryTable").setGridParam({url:urlString}).reload();	
					}
				});							
			});
			jQuery("${random}_deptMRSummary_save").unbind( 'click' ).bind("click",function(){
				jQuery("#${random}_deptMRSummaryTable").saveGrid();	
			});
	});
	function clearInputClassInDeptNeedPlan(){
		disableLink(["${random}_deptMRSummary_new","${random}_deptMRSummary_save"]);
		jQuery("#deptMRSummary_planType").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#deptMRSummary_remark").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#deptMRSummaryDetail_search_store").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
	}
	function readonlyInputClassInDeptNeedPlan(){
		disableLink(["${random}_deptMRSummary_new"]);
		jQuery("#deptMRSummary_planType").removeClass('textInput').attr('readOnly',"true");
		//jQuery("#deptMRSummary_remark").removeClass('textInput').addClass('lineInput').attr('readOnly',"true");
		jQuery("#deptMRSummaryDetail_search_store").removeClass('textInput').attr('readOnly',"true");
		jQuery("#${random}_deptMRSummaryTable").fullEdit();
	}
</script>
<script type="text/javascript">
	
	function saveDeptMRSummary(){
		var storeId = jQuery("#deptMRSummaryDetail_search_store_id").val();
		if(storeId == '' || storeId=='null'){
			alertMsg.error("请选择目标仓库。");
			return;
		}  
		var planType = $("input[name='deptMRSummary.planType']:[checked]").val();

		/* var var_name = $(“input[name='deptMRSummary.planType']:checked”).val();
		alert(var_name); */
		var urlString = "${ctx}/deptMRSummaryDetailGridEdit?oper=update";
		urlString = urlString
			+ "&store=" + storeId + "&planType=" + planType; 
		urlString=encodeURI(urlString);
		alertMsg.confirm("确认保存？", {
			okCall : function() {
				$.post(urlString,function(data) {
					formCallBack(data);
					jQuery("#${random}_deptMRSummaryTable").saveGrid();
				});
			}
		});		
	}
	function closeDeptMRSummary(){	
		var gridEdited = jQuery("#${random}_deptMRSummaryTable")[0].p.gridEdited;
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
			<ul class="toolBar" id="${random}_deptMRSummary_toolBar">
				<li>
					<a id="${random}_deptMRSummary_new" class="addbutton" href="javaScript:newDeptMRSummary()" ><span><s:text name="deptMRSummary.summary" /></span></a>
				</li>
				<li>
					<a id="${random}_deptMRSummary_save" class="savebutton" href="javaScript:saveDeptMRSummary()"><span><s:text name="button.save" /></span> </a>
				</li>
				<li>
    			 <a id="${random}_deptMRSummary_setColShow" class="settlebutton" href="javaScript:setColShow('${random}_deptMRSummaryTable','com.huge.ihos.material.deptplan.model.DeptMRSummaryDetail','zhGrid')"><span><s:text name="button.setColShow" /></span></a>
  				  </li>
				<li>
					<a id="${random}_deptMRSummary_close" class="closebutton" href="javaScript:closeDeptMRSummary()"><span><s:text name="button.close" /></span> </a>
				</li>
			</ul>
		</div>
		&nbsp;&nbsp;
		<div id="${random}_deptMRSummaryCard" autoBreakPage='*' style="margin:5px 10px 5px 5px">
			<form id="${random}_deptMRSummaryForm">
				<div>
					<s:hidden key="deptMRSummary.mrId"/>
					<s:hidden key="deptMRSummary.mrNo"/>
					<s:hidden key="deptMRSummary.orgCode"/>
					<s:hidden key="deptMRSummary.copyCode"/>
					<s:hidden key="deptMRSummary.periodMonth"/>
					<s:hidden key="deptMRSummary.docTemId"/>
					<s:hidden key="deptMRSummary.makePerson.personId"/>
					<s:hidden key="deptMRSummary.makeDate"/>
					<c:set var="inputKey" value="store" scope="page"/>
					<c:set var="referId" value="id" scope="page"/>
					<c:set var="referName" value="storeName" scope="page"/>
				</div>
				<div style="border:0;width:100%;table-layout:fixed;margin-top:-10px;">
				<label style="float:none;white-space:nowrap" >
						<s:text name='deptMRSummary.store'/>:
						<input type="text" id="deptMRSummaryDetail_search_store"  name="deptMRSummary.${inputKey}.${referName}" value="${deptMRSummary[inputKey][referName]}" >
						<input type="hidden" id="deptMRSummaryDetail_search_store_id" name="deptMRSummary.${inputKey}.${referId}" value="${deptMRSummary[inputKey][referId]}">
					</label>&nbsp;&nbsp;
					<c:set var="planType" value="${deptMRSummary.planType}" scope="page"/> 
					<c:set var="entityIsNew" value="${entityIsNew}" scope="page"/>
					<label style="float:none;white-space:nowrap" >
					<c:if test="${entityIsNew==true}">
						<c:if test="${planType=='2'}">
						<s:radio id="deptMRSummary_planType" key="deptMRSummary.planType" name="deptMRSummary.planType" list="#{'1':'月计划','2':'追加计划'}" value="2" ></s:radio>						
						</c:if>
						<c:if test="${planType!='2'}">
						<s:radio id="deptMRSummary_planType" key="deptMRSummary.planType" name="deptMRSummary.planType" list="#{'1':'月计划','2':'追加计划'}" value="1" ></s:radio>
						</c:if>	
					</c:if>	
					<c:if test="${entityIsNew!=true}">
					<s:text name='deptMRSummary.planType'/>:
					   <input type="hidden" id="deptMRSummary_planType_id" name="deptMRSummary.planType" value="${planType}" />
					   <input type="text" id="deptMRSummary_planType" name="planTypedisplay" value="${planType==1?'月计划':'追加计划'}" />
					</c:if>&nbsp;&nbsp;								
					</label>
					<label style="float:none;white-space:nowrap" >
					<s:text name="deptMRSummary.remark"/>:
					<input type="text" id="deptMRSummary_remark" name="deptMRSummary.remark" value="${deptMRSummary.remark}" />
					</label>
				<%--<c:forEach items="${docTemp.inputList}" var="input">
						<span class="docInputArea" id="${random}_${input.value}_${input.necessary}">&nbsp;&nbsp;&nbsp;
							<c:set var="inputType" value="${input.type}" scope="page"/> 
							<c:set var="inputKey" value="${input.value}" scope="page"/>
							<c:set var="referId" value="${input.referId}" scope="page"/>
							<c:set var="referName" value="${input.referName}" scope="page"/>
							<c:if test="${inputKey!='planType'}">
								<label><c:out value="${input.name}"/>:</label>
							</c:if>
							<c:if test="${inputType=='refer'}" >
								<input type="text" id="${random}_deptMRSummary_${inputKey}" name="deptMRSummary.${inputKey}.${referName}" value="${deptMRSummary[inputKey][referName]}" />
								<input type="hidden" id="${random}_deptMRSummary_${inputKey}_id" name="deptMRSummary.${inputKey}.${referId}" value="${deptMRSummary[inputKey][referId]}"/>
							</c:if>
							<c:if test="${inputType=='select'}" >
									<s:radio key="deptMRSummary.planType" name="deptMRSummary.planType" list="#{'1':'月计划','2':'追加计划'}"></s:radio>
								<s:else>
									<label><c:out value="${input.name}"/>:</label>
									<input type="text" id="${random}_deptMRSummary_${inputKey}" name="deptMRSummary.${inputKey}" value="${deptMRSummary[inputKey]==1?'月计划':'追加计划'}" />
								</s:else>
							</c:if>
							<c:if test="${inputType=='simple'}" >
								<input type="text" id="${random}_deptMRSummary_${inputKey}" name="deptMRSummary.${inputKey}" value="${deptMRSummary[inputKey]}" />
							</c:if>
							<script>
								if(${input.necessary}){
									jQuery("#${random}_${input.value}_${input.necessary} input[type='text']").addClass("required");
								}
							</script>
						</span>
					</c:forEach> --%>
					
				</div>
			</form>
			<div id="${random}_deptMRSummaryTable_div" class="zhGrid_div" layoutH="80">
				<table id="${random}_deptMRSummaryTable"></table>
			</div>
			<div style="height:26px" id="${random}_deptMRSummaryForm_foot">
				<ul style="float:left;width:100%;height:20px;margin-top:8px;text-align:center">
				<%-- <c:forEach items="${docTemp.footMap}" var="foot">
						<li style="float:left;">
							<label><c:out value="${foot.value}"/>:</label>
							<c:if test="${foot.key!='sign'}">
									<c:set var="footKey" value="${foot.key}" scope="page"/>
									<input class="lineInput" size="8" value="${deptMRSummary[footKey].name}" readonly="readonly"/>
							</c:if>
							<c:if test="${foot.key=='sign'}">
								<input class="lineInput" size="8" value="" readonly="readonly"/>
							</c:if>
						</li>
					</c:forEach>--%>					
				</ul>
			</div>
		</div>
	</div>
</div>