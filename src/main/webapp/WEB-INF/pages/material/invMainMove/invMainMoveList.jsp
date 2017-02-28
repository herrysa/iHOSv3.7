
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var width=972 , height = 628;
	 function insertInvMoveMainForm(storeId){
			$.pdialog.open('${ctx}/editInvMainMove?docType=移库单', 'addInvMainMove', '添加移库单', {mask:true,width:width,height:height});　
	 }
	
	function delInvMoveMain() {
		var url = "${ctx}/invMainOutGridEdit?oper=del&ioType=3"
		var sid = jQuery("#invMainMove_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var state = jQuery("#invMainMove_gridtable").jqGrid('getCell',sid[i],"status");
				if(state!=0){
					alertMsg.error("只能删除新建状态的单据!");
					return;
				}
			}
			url = url+"&id="+sid+"&navTabId=invMainMove_gridtable";
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					jQuery.post(url, function(data) {
						formCallBack(data);
					});

				}
			});
		}
	}

	function confirmInvMoveMainAll() {
		var url = "${ctx}/invMainOutConfirm?ioType=3"
		var sid = jQuery("#invMainMove_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var state = jQuery("#invMainMove_gridtable").jqGrid('getCell',sid[i],"status");
				if(state!=1){
					alertMsg.error("只能移入已经移出的单据!");
					return;
				} 
			}
			alertMsg.confirm("确认移入？", {
				okCall : function() {
					jQuery.post(url,{id:sid,navTabId:'invMainMove_gridtable'}, function(data) {
						formCallBack(data);
					});

				}
			});
		}
	}
	
	function auditInvMoveMainAll() {
		var url = "${ctx}/invMainOutAudit?ioType=3"
		var sid = jQuery("#invMainMove_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
			    var state = jQuery("#invMainMove_gridtable").jqGrid('getCell',sid[i],"status");
			    alert(state);
				if(state!=0){
					alertMsg.error("只能移出新建的单据!");
					return;
				} 
			}
			
			alertMsg.confirm("确认移出？", {
				okCall : function() {
					jQuery.post(url,{id:sid,navTabId:'invMainMove_gridtable'}, function(data) {
						formCallBack(data);
					});

				}
			});
		}
	}
	function notAuditInvMoveMainAll() {
		var url = "${ctx}/invMainOutAuditNot?ioType=3"
		var sid = jQuery("#invMainMove_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var state = jQuery("#invMainMove_gridtable").jqGrid('getCell',sid[i],"status");
				if(state!=1){
					alertMsg.error("只能取消已经移出的单据!");
					return;
				}
			}
			alertMsg.confirm("确认取消移出？", {
				okCall : function() {
					jQuery.post(url, {id:sid,navTabId:'invMainMove_gridtable'},function(data) {
						formCallBack(data);
					});

				}
			});
		}
	}
	function reloadInvMainMoveList(){
		var urlString = "invMainOutGridList?filter_EQS_ioType=4";
		var invMainMove_search_make_date_from = jQuery("#invMainMove_search_make_date_from").val();
		var invMainMove_search_make_date_to = jQuery("#invMainMove_search_make_date_to").val();
		var invMainMove_search_confirm_date_from = jQuery("#invMainMove_search_confirm_date_from").val();
		var invMainMove_search_confirm_date_to = jQuery("#invMainMove_search_confirm_date_to").val();
		var search_store = jQuery("#invMainMove_search_store_id").val();
		var search_storeIn = jQuery("#invMainMove_search_storeIn_id").val();
		var search_status = jQuery("#invMainMove_search_status_id").val();
		var search_busTypeCode = jQuery("#invMainMove_search_businessCode_id").val();

		urlString = urlString
				+ "&filter_GED_makeDate=" + invMainMove_search_make_date_from
				+ "&filter_LED_makeDate=" + invMainMove_search_make_date_to
				+ "&filter_GED_confirmDate=" + invMainMove_search_confirm_date_from
				+ "&filter_LED_confirmDate=" + invMainMove_search_confirm_date_to
				+ "&filter_EQS_store.id=" + search_store
				+ "&filter_EQS_exchStoreId.id=" + search_storeIn
				+ "&filter_EQS_status=" + search_status
				+ "&filter_INS_busType.typeCode=" + search_busTypeCode;
		urlString = encodeURI(urlString);
		jQuery("#invMainMove_gridtable").jqGrid('setGridParam', {
			url : urlString,
			page : 1
		}).trigger("reloadGrid");
	}
	var invMoveMainGridIdString = "#invMainMove_gridtable";
	jQuery(document).ready(function() {
		var initFlag = 0;
		var invMoveMainGrid = jQuery(invMoveMainGridIdString);
		invMoveMainGrid.jqGrid({
			url : "invMainOutGridList?filter_EQS_ioType=4&filter_GED_makeDate=${currentPeriodBeginDate}&filter_LED_makeDate=${currentSystemVariable.businessDate}",
			editurl : "invMainMoveGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel : [
				{name : 'ioId',index : 'ioId',align : 'center',label : '<s:text name="invMain.ioId" />',hidden : true,key : true},
				{name : 'ioBillNumber',index : 'ioBillNumber',align : 'left',label : '<s:text name="invMain.ioBillNumber" />',hidden : false,highsearch:true},
				{name : 'makeDate',index : 'makeDate',align : 'center',width : 80,label : '<s:text name="invMain.makeDate" />',hidden : false,highsearch:true},
				{name : 'store.storeName',index : 'store.storeName',align : 'left',width : 100,label : '<s:text name="invMain.storeOut" />',hidden : false,highsearch:true},
				{name : 'exchStoreId.storeName',index : 'exchStoreId.storeName',align : 'left',width : 100,label : '<s:text name="invMain.storeIn" />',hidden : false,highsearch:true},
				{name : 'busType.typeName',index : 'busType.typeName',align : 'left',width : 100,label : '<s:text name="invMain.busType" />',hidden : true},
				{name : 'applyDept.name',index : 'applyDept.name',align : 'left',width : 100,label : '<s:text name="invMain.applyDept" />',hidden : true},
				{name : 'applyPersion.name',index : 'applyPersion.name',align : 'left',width : 60,label : '<s:text name="invMain.applyPersion" />',hidden : true},
				{name : 'makePerson.name',index : 'makePerson',align : 'left',width : 60,label : '<s:text name="invMain.makePerson" />',hidden : false,highsearch:true},
				{name : 'totalMoney',index : 'totalMoney',align : 'right',width : 80,label : '<s:text name="invMain.totalMoney" />',hidden : false,highsearch:true,sortable :true,formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces:2}},
				{name : 'checkPerson.name',index : 'checkPerson.name',align : 'center',width : 80,label : '<s:text name="invMain.outPerson" />',hidden : false,highsearch:true,sortable :false},
				{name : 'confirmPerson.name',index : 'confirmPerson.name',align : 'center',width : 80,label : '<s:text name="invMain.inPerson" />',hidden : false,highsearch:true,sortable :false},
				{name : 'confirmDate',index : 'confirmDate',align : 'center',width : 100,label : '<s:text name="移库日期" />',hidden : false,highsearch:true},
				{name : 'exchIo',index : 'exchIo',align : 'left',width : 130,label : '<s:text name="入库单号" />',hidden : false,highsearch:true},
				{name : 'remark',index : 'remark',align : 'left',width : 200,label : '<s:text name="invMain.remark" />',hidden : false,highsearch:true}, 
				{name : 'status',index : 'status',align : 'center',label : '<s:text name="invMain.status" />',width : 60,hidden : false,highsearch:true,formatter : 'select',editable : false,edittype : "select",editoptions : {value : "0 :新建; 1: 移出确认; 2: 移入确认; 3: 已生成凭证"}} 
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
				jQuery("#invMianMove_allSum").text(formatNum(parseFloat(ss).toFixed(2)));
				var amount = jQuery(this).getCol('totalMoney',false,'sum');
				jQuery("#invMianMove_pageSum").text(formatNum(amount.toFixed(2)));
				var rowNum = jQuery(this).getDataIDs().length;
				if(rowNum>0){
		              var rowIds = jQuery(this).getDataIDs();
		              var ret = jQuery(this).jqGrid('getRowData');
		              var id='';
		              for(var i=0;i<rowNum;i++){
		            	  id=rowIds[i];
		            	  if(ret[i]['status']==0){
		            		  setCellText(this,id,'status','<span >新建</span>');
		            	  }else if(ret[i]['status']==1){
		            		  setCellText(this,id,'status','<span style="color:green" >移出确认</span>');
		            	  }else if(ret[i]['status']==2){
		            		  setCellText(this,id,'status','<span style="color:blue" >移入确认</span>');
		            	  }else if(ret[i]['status']==3){
		            		  setCellText(this,id,'status','<span style="color:red" >已生成凭证</span>');
		            	  }
		            	  var editMoveUrl = "'${ctx}/editInvMainMove?docType=移库单&ioId="+ret[i]["ioId"]+"'";
		   	        	  setCellText(this,id,'ioBillNumber','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showInvMainMove('+editMoveUrl+');">'+ret[i]["ioBillNumber"]+'</a>');
		   	        	  var editRKUrl = "'${ctx}/editInvMainIn?docPreview=refer&invMainInNo="+ret[i]["exchIo"]+"'";
		   	        	  setCellText(this,id,'exchIo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showInvMainIn('+editRKUrl+');">'+ret[i]["exchIo"]+'</a>');
		              }
		            }
				var dataTest = {"id" : "invMainMove_gridtable"};
				jQuery.publish("onLoadSelect",dataTest, null);
				makepager("invMainMove_gridtable");
				initFlag = initColumn('invMainMove_gridtable','com.huge.ihos.material.model.InvMainMove',initFlag);
			}

		});
		jQuery(invMoveMainGrid).jqGrid('bindKeys');

		initStoreSelectInInvMainMove("invMainMove_search_store");
		initStoreSelectInInvMainMove("invMainMove_search_storeIn");
	});
	function showInvMainMove(url){
		$.pdialog.open(url,'showInvMainMove','移库单明细', {mask:true,width : width,height : height});
	}
	function showInvMainIn(url){
		$.pdialog.open(url,'showInvMainIn','入库单明细', {mask:true,width : width,height : height});
	}
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

<div class="page">
	<div id="invMainMove_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id='invMainMoveSearchForm'>
					<label style="float:none;white-space:nowrap" >
						<s:text name="invMain.storeOut" />:
					 	<input type="hidden" name='filter_EQS_store.id' id="invMainMove_search_store_id" >
					 	<input type="text"	id="invMainMove_search_store" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name="invMain.storeIn" />:
					 	<input type="hidden" name='filter_EQS_exchStoreId.id' id="invMainMove_search_storeIn_id" >
					 	<input type="text"	id="invMainMove_search_storeIn" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" ><s:text name="invMain.makeDate" />:
						<input type="text"	id="invMainMove_search_make_date_from" name='filter_GED_makeDate' class="Wdate" style="width:80px;height:15px" value="${fns:userContextParam('periodBeginDateStr') }" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'invMainMove_search_make_date_to\')}'})" >
						至
					 	<input type="text"	id="invMainMove_search_make_date_to" name='filter_LED_makeDate' class="Wdate" value="${fns:userContextParam('periodEndDateStr') }" style="width:80px;height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'invMainMove_search_make_date_from\')}'})" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >移库日期:
						<input type="text"	id="invMainMove_search_confirm_date_from" name='filter_GED_confirmDate' class="Wdate" style="width:80px;height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'invMainMove_search_confirm_date_to\')}'})" >
						至
					 	<input type="text"	id="invMainMove_search_confirm_date_to" name='filter_LED_confirmDate' class="Wdate"  style="width:80px;height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'invMainMove_search_confirm_date_from\')}'})" >
					</label>&nbsp;&nbsp;
					
					<input type="hidden" name='filter_EQS_busType.typeCode' id="invMainMove_search_businessCode_id" value="13,14">
					
					<label style="float:none;white-space:nowrap" >
						<s:text name='invMain.status'/>:
						<s:select list="#{'':'','0' :'新建','1': '移出确认','2': '移入确认','3': '已生成凭证'}" id="invMainMove_search_status_id" name="filter_EQS_status" style="width:125px"></s:select>
					</label>&nbsp;&nbsp;
					<%-- <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="reloadInvMainMoveList();"><s:text name='button.search'/></button>
						</div>
					</div> --%>
				</form>
			</div>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="reloadInvMainMoveList();"><s:text name='button.search'/></button>
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
					<a class="addbutton" href="javaScript:insertInvMoveMainForm();" ><span>新单</span></a>
				</li>
				<li>
					<a class="delbutton"  href="javaScript:delInvMoveMain()"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a class="checkbutton"  href="javaScript:auditInvMoveMainAll()"><span>移出确认</span></a>
				</li>
				<li>
					<a class="delallbutton"  href="javaScript:notAuditInvMoveMainAll()"><span>取消移出确认</span></a>
				</li>
				<li>
					<a class="confirmbutton"  href="javaScript:confirmInvMoveMainAll()"><span>移入确认</span></a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('invMainMove_gridtable','com.huge.ihos.material.model.InvMainMove')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			</ul>
		</div>
		<div align="right"  style="margin-top: -20px;margin-right:5px">
		本页金额合计：<label id="invMianMove_pageSum"></label>&nbsp;&nbsp;总计：<label id="invMianMove_allSum" ></label>
		</div>
		<div id="invMainMove_gridtable_div" layoutH="118" style="margin-left: 2px; margin-top: 10px; overflow: hidden"
			buttonBar="optId:ioId;width:850;height:600">
			<input type="hidden" id="invMainMove_gridtable_navTabId" value="${sessionScope.navTabId}">
 			<table id="invMainMove_gridtable"></table>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="invMainMove_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="invMainMove_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="invMainMove_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>
	</div>
</div>
