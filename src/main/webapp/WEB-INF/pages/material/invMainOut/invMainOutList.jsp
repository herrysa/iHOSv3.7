
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var width=960 , height = 628;
	 function insertInvOutMainForm(storeId){
			$.pdialog.open('${ctx}/editInvMainOut?docType=出库单', 'addInvMainOut', '添加出库单', {mask:true,width:width,height:height});　
	 }
	
	function delInvOutMain() {
		var url = "${ctx}/invMainOutGridEdit?oper=del"
			var sid = jQuery("#invMainOut_gridtable").jqGrid('getGridParam',
					'selarrrow');
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#invMainOut_gridtable").jqGrid('getRowData',rowId);
					
					if(row['status']==1){
						alertMsg.error("您选择的出库单["+row['ioBillNumber']+"]已审核.不能删除!");
						return;
					}else if(row['status']==2){
						alertMsg.error("您选择的出库单["+row['ioBillNumber']+"]已记账.不能删除!");
						return;
					}else if(row['status']==3){
						alertMsg.error("您选择的出库单["+row['ioBillNumber']+"]已生成凭证.不能删除!");
						return;
					}
				}
				url = url+"&id="+sid+"&navTabId=invMainOut_gridtable";
				alertMsg.confirm("确认删除？", {
					okCall : function() {
						jQuery.post(url, function(data) {
							formCallBack(data);
						});

					}
				});
			}
	}

	function confirmInvOutMainAll() {
		var url = "${ctx}/invMainOutConfirm"
		var sid = jQuery("#invMainOut_gridtable").jqGrid('getGridParam',
				'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#invMainOut_gridtable").jqGrid('getRowData',rowId);
				
				if(row['status']==0){
					alertMsg.error("您选择的出库单["+row['ioBillNumber']+"]尚未审核.请审核!");
					return;
				}else if(row['status']==2){
					alertMsg.error("您选择的出库单["+row['ioBillNumber']+"]已记账.不能重复记账!");
					return;
				}else if(row['status']==3){
					alertMsg.error("您选择的出库单["+row['ioBillNumber']+"]已生成凭证.不能记账!");
					return;
				}
			}
			
			url = url+"?id="+sid+"&navTabId=invMainOut_gridtable";
			alertMsg.confirm("确认记账？", {
				okCall : function() {
					jQuery.post(url, function(data) {
						formCallBack(data);
					});

				}
			});
		}
	}
	
	function auditInvOutMainAll() {
		var url = "${ctx}/invMainOutAudit"
		var sid = jQuery("#invMainOut_gridtable").jqGrid('getGridParam',
				'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#invMainOut_gridtable").jqGrid('getRowData',rowId);
				

				if(row['status']==1){
					alertMsg.error("您选择的出库单["+row['ioBillNumber']+"]已审核.不能重复审核!");
					return;
				}else if(row['status']==2){
					alertMsg.error("您选择的出库单["+row['ioBillNumber']+"]已记账.不能审核!");
					return;
				}else if(row['status']==3){
					alertMsg.error("您选择的出库单["+row['ioBillNumber']+"]已生成凭证.不能审核!");
					return;
				}
			}
			
			url = url+"?id="+sid+"&navTabId=invMainOut_gridtable";
			alertMsg.confirm("确认审核？", {
				okCall : function() {
					jQuery.post(url, function(data) {
						formCallBack(data);
					});

				}
			});
		}
	}
	function notAuditInvOutMainAll() {
		var url = "${ctx}/invMainOutAuditNot"
		var sid = jQuery("#invMainOut_gridtable").jqGrid('getGridParam',
				'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#invMainOut_gridtable").jqGrid('getRowData',rowId);
				
				if(row['status']==0){
					alertMsg.error("您选择的出库单["+row['ioBillNumber']+"]尚未审核.不能销审!");
					return;
				}else if(row['status']==2){
					alertMsg.error("您选择的出库单["+row['ioBillNumber']+"]已记账.不能销审!");
					return;
				}else if(row['status']==3){
					alertMsg.error("您选择的出库单["+row['ioBillNumber']+"]已生成凭证.不能销审!");
					return;
				}
			}
			
			url = url+"?id="+sid+"&navTabId=invMainOut_gridtable";
			alertMsg.confirm("确认取消审核？", {
				okCall : function() {
					jQuery.post(url, function(data) {
						formCallBack(data);
					});

				}
			});
		}
	}
	function reloadInvMainOutList(){
		var urlString = "invMainOutGridList?filter_EQS_ioType=2";
		var invMainOut_search_make_date_from = jQuery("#invMainOut_search_make_date_from").val();
		var invMainOut_search_make_date_to = jQuery("#invMainOut_search_make_date_to").val();
		var invMainOut_search_confirm_date_from = jQuery("#invMainOut_search_confirm_date_from").val();
		var invMainOut_search_confirm_date_to = jQuery("#invMainOut_search_confirm_date_to").val();
		var search_store = jQuery("#invMainOut_search_store_id").val();
		var search_status = jQuery("#invMainOut_search_status_id").val();
		var search_busTypeCode = jQuery("#invMainOut_search_businessCode_id").val();

		urlString = urlString
				+ "&filter_GED_makeDate=" + invMainOut_search_make_date_from
				+ "&filter_LED_makeDate=" + invMainOut_search_make_date_to
				+ "&filter_GED_confirmDate=" + invMainOut_search_confirm_date_from
				+ "&filter_LED_confirmDate=" + invMainOut_search_confirm_date_to
				+ "&filter_EQS_store.id=" + search_store
				+ "&filter_EQS_status=" + search_status
				+ "&filter_EQS_busType.typeCode=" + search_busTypeCode;
		urlString = encodeURI(urlString);
		jQuery("#invMainOut_gridtable").jqGrid('setGridParam', {
			url : urlString,
			page : 1
		}).trigger("reloadGrid");
	}
	var invOutMainGridIdString = "#invMainOut_gridtable";
	jQuery(document).ready(function() {
		var initFlag = 0;
		var invMainOutGrid = jQuery(invOutMainGridIdString);
		invMainOutGrid.jqGrid({
			url : "invMainOutGridList?filter_EQS_ioType=2&filter_GED_makeDate=${currentPeriodBeginDate}&filter_LED_makeDate=${currentSystemVariable.businessDate}",
			editurl : "invMainOutGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel : [
					{name : 'ioId',index : 'ioId',align : 'center',label : '<s:text name="invMain.ioId" />',hidden : true,key : true},
					{name : 'ioBillNumber',index : 'ioBillNumber',align : 'left',label : '<s:text name="invMain.ioBillNumber" />',hidden : false,highsearch:true},
					{name : 'store.storeName',index : 'store.storeName',align : 'left',width : 100,label : '<s:text name="invMain.store" />',hidden : false,highsearch:true},
					{name : 'busType.typeName',index : 'busType.typeName',align : 'left',width : 100,label : '<s:text name="invMain.busType" />',hidden : false,highsearch:true},
					{name : 'makeDate',index : 'makeDate',align : 'center',width : 80,label : '<s:text name="invMain.makeDate" />',hidden : false,highsearch:true},
					{name : 'applyDept.name',index : 'applyDept.name',align : 'left',width : 100,label : '<s:text name="invMain.applyDept" />',hidden : false,highsearch:true},
					{name : 'applyPersion.name',index : 'applyPersion.name',align : 'left',width : 60,label : '<s:text name="invMain.applyPersion" />',hidden : false,highsearch:true},
					{name : 'makePerson.name',index : 'makePerson',align : 'left',width : 60,label : '<s:text name="invMain.makePerson" />',hidden : false,highsearch:true},
					{name : 'confirmDate',index : 'confirmDate',align : 'center',width : 100,label : '<s:text name="invMain.confirmDate" />',hidden : false,highsearch:true},
					{name : 'totalMoney',index : 'totalMoney',align : 'right',width : 120,label : '<s:text name="invMain.totalMoney" />',hidden : false,highsearch:true,sortable :true,formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces:2}  },
					{name : 'store.keeper.name',index : 'store.keeper.name',align : 'center',width : 60,label : '<s:text name="invMain.keeper" />',hidden : false,highsearch:true,sortable :false,},
					{name : 'remark',index : 'remark',align : 'left',width : 200,label : '<s:text name="invMain.remark" />',hidden : false,highsearch:true},
					{name : 'status',index : 'status',align : 'center',label : '<s:text name="invMain.status" />',hidden : false,highsearch:true,formatter : 'select',editable : false,edittype : "select",editoptions : {value : "0 :新建; 1: 已审核; 2: 已记账; 3: 已生成凭证"}} //0 新建 1 已审核 2 已确认 3 已记帐（生成凭证）
			],
			jsonReader : {
				root : "invMains", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
			sortname : 'ioBillNumber',
			viewrecords : true,
			sortorder : 'desc',
			//caption:'<s:text name="invMainList.title" />',
			height : 280,
			gridview : true,
			rownumbers : true,
			loadui : "disable",
			multiselect : true,
			multiboxonly : true,
			shrinkToFit : false,
			autowidth : false,
			onSelectRow : function(rowid) {

			},
			gridComplete : function() {
				var sumDataStr = jQuery(this).getGridParam('userData'); 
				var ss = 0;
				if(sumDataStr){
					ss = sumDataStr.sum;
				}
				jQuery("#invMianOut_allSum").text(formatNum(parseFloat(ss).toFixed(2)));
				var amount = jQuery(this).getCol('totalMoney',false,'sum');
				jQuery("#invMianOut_pageSum").text(formatNum(amount.toFixed(2)));
				var rowNum = jQuery(this).getDataIDs().length;
				if(rowNum>0){
		             // jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
		              var rowIds = jQuery(this).getDataIDs();
		              var ret = jQuery(this).jqGrid('getRowData');
		              var id='';
		              for(var i=0;i<rowNum;i++){
		            	  id=rowIds[i];
		            	  if(ret[i]['status']==0){
		            		  setCellText(this,id,'status','<span >新建</span>');
		            	  }else if(ret[i]['status']==1){
		            		  setCellText(this,id,'status','<span style="color:green" >已审核</span>');
		            	  }else if(ret[i]['status']==2){
		            		  setCellText(this,id,'status','<span style="color:blue" >已记账</span>');
		            	  }
		            	  else if(ret[i]['status']==3){
		            		  setCellText(this,id,'status','<span style="color:red" >已生成凭证</span>');
		            	  }
		            	  editUrl = "'${ctx}/editInvMainOut?docType=出库单&ioId="+ret[i]["ioId"]+"'";
		   	        	  setCellText(this,id,'ioBillNumber','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showInvMainOut('+editUrl+');">'+ret[i]["ioBillNumber"]+'</a>');
		   	        	 
		              }
		            }
				var dataTest = {
					"id" : "invMainOut_gridtable"
				};
				jQuery.publish("onLoadSelect",dataTest, null);
				makepager("invMainOut_gridtable");
				initFlag = initColumn('invMainOut_gridtable','com.huge.ihos.material.model.InvMainOut',initFlag);
			}

		});
		jQuery(invMainOutGrid).jqGrid('bindKeys');

		jQuery("#invMainOut_search_store").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where is_book = 1 and is_lock = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		});
		jQuery("#invMainOut_search_businessCode").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT TYPECODE id, TYPENAME name FROM MM_BUSINESSTYPE WHERE DISABLED=0 AND INOUT = '2' ",
			exceptnullparent : false,
			lazy : false
		});
	});
	function showInvMainOut(url){
		$.pdialog.open(url,'showInvMainOut','出库单明细', {mask:true,width : width,height : height});
	}
</script>

<div class="page">
<div id="invMain_container">
			<div id="invMain_layout-center"
				class="pane ui-layout-center">
	<div id="invMainOut_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id='invMainOutSearchForm'>
					<label style="float:none;white-space:nowrap" >
						<s:text name="invMain.store" />:
					 	<input type="hidden" name='filter_EQS_store.id' id="invMainOut_search_store_id" >
					 	<input type="text"	id="invMainOut_search_store" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" ><s:text name="invMain.makeDate" />:
						<input type="text"	id="invMainOut_search_make_date_from" name='filter_GED_makeDate' class="Wdate" style="width:80px;height:15px" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'invMainOut_search_make_date_to\')}'})" >
						至
					 	<input type="text"	id="invMainOut_search_make_date_to" name='filter_LED_makeDate' class="Wdate" value="${fns:userContextParam('periodEndDateStr')}" style="width:80px;height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'invMainOut_search_make_date_from\')}'})" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" ><s:text name="invMain.confirmDate" />:
						<input type="text"	id="invMainOut_search_confirm_date_from" name='filter_GED_confirmDate' class="Wdate" style="width:80px;height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'invMainOut_search_confirm_date_to\')}'})" >
						至
					 	<input type="text"	id="invMainOut_search_confirm_date_to" name='filter_LED_confirmDate' class="Wdate"  style="width:80px;height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'invMainOut_search_confirm_date_from\')}'})" >
					</label>&nbsp;&nbsp;
					
					<label style="float:none;white-space:nowrap" >
					 	<s:text name='invMain.busType'/>:
					 	<input type="hidden" name='filter_EQS_busType.typeCode' id="invMainOut_search_businessCode_id" >
					 	<input type="text"	id="invMainOut_search_businessCode" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='invMain.status'/>:
						<s:select list="#{'':'','0' :'新建','1': '已审核','2': '已记账','3': '已生成凭证'}" id="invMainOut_search_status_id" name="filter_EQS_status" style="width:125px"></s:select>
					</label>
					<%-- <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="reloadInvMainOutList();"><s:text name='button.search'/></button>
						</div>
					</div> --%>
				</form>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="reloadInvMainOutList();"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">

	<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a id="invMainOut_gridtable_add_custom" class="addbutton" href="javaScript:insertInvOutMainForm();" >
						<span>
							新单
						</span>
					</a>
				</li>
				<li>
					<a id="invMainOut_gridtable_del_custom" class="delbutton"  href="javaScript:delInvOutMain()">
						<span><s:text name="button.delete" /></span>
					</a>
				</li>
				<li>
					<a id="invMainOut_gridtable_confirm" class="checkbutton"  href="javaScript:auditInvOutMainAll()">
						<span>审核</span>
					</a>
				</li>
				<li>
					<a id="invMainOut_gridtable_confirm" class="delallbutton"  href="javaScript:notAuditInvOutMainAll()">
						<span>销审</span>
					</a>
				</li>
				<li>
					<a id="invMainOut_gridtable_confirm" class="confirmbutton"  href="javaScript:confirmInvOutMainAll()">
						<span>记账</span>
					</a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('invMainOut_gridtable','com.huge.ihos.material.model.InvMainOut')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			</ul>
		</div>
		<div align="right"  style="margin-top: -20px;margin-right:5px">
		本页金额合计：<label id="invMianOut_pageSum"></label>&nbsp;&nbsp;总计：<label id="invMianOut_allSum" ></label>
		</div>
		<div id="invMainOut_gridtable_div" layoutH="118"
			style="margin-left: 2px; margin-top: 10px; overflow: hidden"
			buttonBar="optId:ioId;width:960;height:628">
			<input type="hidden" id="invMainOut_gridtable_navTabId" value="${sessionScope.navTabId}">
			
 		<table id="invMainOut_gridtable"></table>
		<div id="invMainPager"></div>
	</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="invMainOut_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="invMainOut_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="invMainOut_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>