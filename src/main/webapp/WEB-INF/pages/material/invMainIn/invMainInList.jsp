
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var width=960 , height = 628;
 function insertInvMainInForm(){
		$.pdialog.open("${ctx}/editInvMainIn","addInvMainIn","添加入库单", {mask:true,width:width,height:height});
	}

	function editInvMainInForm(did) {
		var sid=[];// = jQuery("#invMainIn_gridtable").jqGrid('getGridParam', 'selarrrow');
		if(did)
			sid.push(did);
		else
			sid = jQuery("#invMainIn_gridtable").jqGrid('getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		}
		if (sid.length > 1) {
			alertMsg.error("请选择一条记录。");
			return;
		} else {
			$.pdialog.open("${ctx}/editInvMainIn?ioId="+sid,"editInvMainIn","修改入库单", {mask:true,width:width,height:height});
			 }
		}
	function delInvMainIn() {
		var url = "${ctx}/invMainInGridEdit?oper=del"
		var sid = jQuery("#invMainIn_gridtable").jqGrid('getGridParam',
				'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#invMainIn_gridtable").jqGrid('getRowData',rowId);
				
				if(row['status']==1){
					alertMsg.error("您选择的入库单["+row['ioBillNumber']+"]已审核.不能删除!");
					return;
				}else if(row['status']==2){
					alertMsg.error("您选择的入库单["+row['ioBillNumber']+"]已记账.不能删除!");
					return;
				}else if(row['status']==3){
					alertMsg.error("您选择的入库单["+row['ioBillNumber']+"]已生成凭证.不能删除!");
					return;
				}
			}
			url = url+"&id="+sid+"&navTabId=invMainIn_gridtable";
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					jQuery.post(url, function(data) {
						formCallBack(data);
					});
				}
			});
		}
	}
	function confirmInvMainInAll() {
		var url = "${ctx}/invMainInConfirm"
		var sid = jQuery("#invMainIn_gridtable").jqGrid('getGridParam',
				'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#invMainIn_gridtable").jqGrid('getRowData',rowId);
				if(row['status']==0){
					alertMsg.error("您选择的入库单["+row['ioBillNumber']+"]尚未审核.请审核!");
					return;
				}else if(row['status']==2){
					alertMsg.error("您选择的入库单["+row['ioBillNumber']+"]已记账.不能重复记账!");
					return;
				}else if(row['status']==3){
					alertMsg.error("您选择的入库单["+row['ioBillNumber']+"]已生成凭证.不能记账!");
					return;
				}
			}
			url = url+"?id="+sid+"&navTabId=invMainIn_gridtable";
			alertMsg.confirm("确认记账？", {
				okCall : function() {
					jQuery.post(url, function(data) {
						formCallBack(data);
					});
				}
			});
		}
	}
	function auditInvMainInAll() {
		var url = "${ctx}/invMainInAudit"
		var sid = jQuery("#invMainIn_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#invMainIn_gridtable").jqGrid('getRowData',rowId);
				
				if(row['status']==1){
					alertMsg.error("您选择的入库单["+row['ioBillNumber']+"]已审核.不能重复审核!");
					return;
				}else if(row['status']==2){
					alertMsg.error("您选择的入库单["+row['ioBillNumber']+"]已记账.不能审核!");
					return;
				}else if(row['status']==3){
					alertMsg.error("您选择的入库单["+row['ioBillNumber']+"]已生成凭证.不能审核!");
					return;
				}
			}
			url = url+"?id="+sid+"&navTabId=invMainIn_gridtable";
			alertMsg.confirm("确认审核？", {
				okCall : function() {
					jQuery.post(url, function(data) {
						formCallBack(data);
					});
				}
			});
		}
	}
	function unauditInvMainInAll() {
		var url = "${ctx}/invMainInUnAudit"
		var sid = jQuery("#invMainIn_gridtable").jqGrid('getGridParam',
				'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#invMainIn_gridtable").jqGrid('getRowData',rowId);
				
				if(row['status']==0){
					alertMsg.error("您选择的入库单["+row['ioBillNumber']+"]尚未审核.不能销审!");
					return;
				}else if(row['status']==2){
					alertMsg.error("您选择的入库单["+row['ioBillNumber']+"]已记账.不能销审!");
					return;
				}else if(row['status']==3){
					alertMsg.error("您选择的入库单["+row['ioBillNumber']+"]已生成凭证.不能销审!");
					return;
				}
			}
			url = url+"?id="+sid+"&navTabId=invMainIn_gridtable";
			alertMsg.confirm("确认取消审核？", {
				okCall : function() {
					jQuery.post(url, function(data) {
						formCallBack(data);
					});

				}
			});
		}
	}
	function invMainInGridReload() {
		var urlString = "invMainInGridList?filter_EQS_ioType=1&filter_NES_busType.typeCode=39";
		var invMainIn_search_make_date_from = jQuery("#invMainIn_search_make_date_from").val();
		var invMainIn_search_make_date_to = jQuery("#invMainIn_search_make_date_to").val();
		var invMainIn_search_confirm_date_from = jQuery("#invMainIn_search_confirm_date_from").val();
		var invMainIn_search_confirm_date_to = jQuery("#invMainIn_search_confirm_date_to").val();
		var search_store = jQuery("#invMainIn_search_store_id").val();
		var	search_vendor = jQuery("#invMainIn_search_vendor_id").val();
		var search_status = jQuery("#invMainIn_search_status_id").val();
		var search_busTypeCode = jQuery("#invMainIn_search_busTypeCode_id").val();

		urlString = urlString
				+ "&filter_GED_makeDate=" + invMainIn_search_make_date_from
				+ "&filter_LED_makeDate=" + invMainIn_search_make_date_to
				+ "&filter_GED_confirmDate=" + invMainIn_search_confirm_date_from
				+ "&filter_LED_confirmDate=" + invMainIn_search_confirm_date_to
				+ "&filter_EQS_store.id=" + search_store
				+ "&filter_EQS_vendorId=" + search_vendor
				+ "&filter_EQS_status=" + search_status
				+ "&filter_EQS_busType.typeCode=" + search_busTypeCode;
		urlString = encodeURI(urlString);
		jQuery("#invMainIn_gridtable").jqGrid('setGridParam', {
			url : urlString,
			page : 1
		}).trigger("reloadGrid");
	}
	
	var invMainGridIdString = "#invMainIn_gridtable";
	jQuery(document).ready(function() {
		var initFlag = 0;
		var invMainGrid = jQuery(invMainGridIdString);
		invMainGrid.jqGrid({
			url : "invMainInGridList?filter_EQS_ioType=1&filter_NES_busType.typeCode=39&filter_GED_makeDate=${currentPeriodBeginDate}&filter_LED_makeDate=${currentSystemVariable.businessDate}",
			editurl : "invMainInGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel : [
					{name : 'ioId',index : 'ioId',align : 'left',label : '<s:text name="invMain.ioId" />',hidden : true,key : true},
					{name : 'ioBillNumber',index : 'ioBillNumber',align : 'left',label : '<s:text name="invMain.ioBillNumber" />',hidden : false,highsearch:true},
					{name : 'store.storeName',index : 'store.storeName',align : 'left',width : 100,label : '<s:text name="invMain.store" />',hidden : false,highsearch:true},
					{name : 'busType.typeName',index : 'busType.typeName',align : 'left',width : 100,label : '<s:text name="业务类型" />',hidden : false,highsearch:true},
					{name : 'makeDate',index : 'makeDate',align : 'center',width : 100,label : '<s:text name="invMain.makeDate" />',hidden : false,highsearch:true},
					{name : 'makePerson.name',index : 'makePerson',align : 'left',width : 70,label : '<s:text name="invMain.makePerson" />',hidden : false,highsearch:true},
					{name : 'confirmDate',index : 'confirmDate',align : 'center',width : 100,label : '<s:text name="invMain.confirmDate" />',hidden : false,highsearch:true},
					{name : 'totalMoney',index : 'totalMoney',align : 'right',width : 120,label : '<s:text name="invMain.totalMoney" />',hidden : false,highsearch:true,sortable :true,formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces:2}  },
					{name:'vendorName',index:'vendorName',align:'center',width:200,label : '<s:text name="invMain.vendorName" />',hidden:false,highsearch:true},	
					{name : 'remark',index : 'remark',align : 'left',width:200,label : '<s:text name="invMain.remark" />',hidden : false,highsearch:true},
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
			height : 300,
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
				jQuery("#invMianIn_allSum").text(formatNum(parseFloat(ss).toFixed(2)));
				var amount = jQuery(this).getCol('totalMoney',false,'sum');
				jQuery("#invMianIn_pageSum").text(formatNum(amount.toFixed(2)));
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
		            	  
		            	  editUrl = "'${ctx}/editInvMainIn?ioId="+ret[i]["ioId"]+"'";
		   	        	  setCellText(this,id,'ioBillNumber','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showInvMainIn('+editUrl+');">'+ret[i]["ioBillNumber"]+'</a>');
		   	        	 
		              }
		            }
				var dataTest = {
					"id" : "invMainIn_gridtable"
				};
				jQuery.publish("onLoadSelect",
						dataTest, null);
				makepager("invMainIn_gridtable");
				initFlag = initColumn('invMainIn_gridtable','com.huge.ihos.material.model.InvMainIn',initFlag);
			}

		});
		jQuery(invMainGrid).jqGrid('bindKeys');
					
		jQuery("#invMainIn_search_store").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where is_book = 1 and is_lock = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		});
		jQuery("#invMainIn_search_busTypeCode").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT TYPECODE id, TYPENAME name FROM MM_BUSINESSTYPE WHERE DISABLED=0 AND INOUT = '1' AND typeCode != '39'",
			exceptnullparent : false,
			lazy : false
		});
		jQuery("#invMainIn_search_vendor").combogrid({
			url : '${ctx}/comboGridSqlList',
			queryParams : {
				sql : "SELECT v.vendorId vid,v.vendorName vname,v.shortName sname,v.vendorCode vcode from sy_vendor v where isMate = 1 and disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
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
					$("#invMainIn_search_vendor_id").val(ui.item.VID);
				return false; 
			}
		});
	});
	
	function showInvMainIn(url){
		$.pdialog.open(url,'showInvMainIn','入库单明细', {mask:true,width : 960,height : 628});
	}
	
</script>

<div class="page">
<div id="invMainIn_container">
			<div id="invMainIn_layout-center"
				class="pane ui-layout-center">
	<div id="invMainIn_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="invMainIn_search_form" >
					<!-- <label style="float:none;white-space:nowrap" >入库日期:<input type="text" style="width:80px;height:15px" id="invMainIn_search_confirm_date_from" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" >
					</label>
					<label style="float:none;white-space:nowrap" >至&nbsp;&nbsp;&nbsp;<input type="text" style="width:80px;height:15px"	id="invMainIn_search_confirm_date_to"  class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" >
					</label>&nbsp;&nbsp; -->
					<label style="float:none;white-space:nowrap" >仓库:
							 	<input type="hidden"		id="invMainIn_search_store_id" >
							 	<input type="text"		id="invMainIn_search_store" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >制单日期:
						<input type="text" style="width:80px;height:15px"	id="invMainIn_search_make_date_from" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'invMainIn_search_make_date_to\')}'})" >
						至
					 	<input type="text" style="width:80px;height:15px"	id="invMainIn_search_make_date_to"  class="Wdate" value="${fns:userContextParam('periodEndDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'invMainIn_search_make_date_from\')}'})" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" ><s:text name='invMain.confirmDate'/>:
							<input type="text" style="width:80px;height:15px"	id="invMainIn_search_confirm_date_from" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'invMainIn_search_confirm_date_to\')}'})" >
						至
					 	<input type="text" style="width:80px;height:15px"	id="invMainIn_search_confirm_date_to"  class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'invMainIn_search_confirm_date_from\')}'})" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='invMain.busType'/>:
				 		<input type="hidden" id="invMainIn_search_busTypeCode_id" >
				 		<input type="text"	id="invMainIn_search_busTypeCode" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >供货商:
						<input type="hidden"		id="invMainIn_search_vendor_id" >
					 	<input type="text"		id="invMainIn_search_vendor" value="拼音/汉字/编码"
					 	class="defaultValueStyle" onblur="setDefaultValue(this,jQuery('#invMainIn_search_vendor_id'))" 
						onfocus="clearInput(this,jQuery('#invMainIn_search_vendor_id'))" onkeyDown="setTextColor(this)" >
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='invMain.status'/>:
						<s:select list="#{'':'','0' :'新建','1': '已审核','2': '已记账','3': '已生成凭证'}" name="invMainIn_search_status_id" style="width:125px"></s:select>
					</label>&nbsp;&nbsp;
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="invMainInGridReload();"><s:text name='button.search'/></button>
						</div>
					</div>
						</form>
					</div>
				<%-- <div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="invMainInGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					</ul>
				</div> --%>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="invMainIn_gridtable_add_custom" class="addbutton" href="javaScript:insertInvMainInForm();" ><span>新单
					</span>
				</a>
				</li>
				<%-- <li><a id="invMainIn_gridtable_edit_custom" class="changebutton"  href="javaScript:editInvMainInForm();"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li> --%>
				<li><a id="invMainIn_gridtable_del_custom" class="delbutton"  href="javaScript:delInvMainIn()"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="invMainIn_gridtable_check" class="checkbutton"  href="javaScript:auditInvMainInAll()"><span><s:text name="审核" /></span>
				</a>
				</li>
				<li><a id="invMainIn_gridtable_cancelcheck" class="delallbutton"  href="javaScript:unauditInvMainInAll()"><span><s:text name="销审" /></span>
				</a>
				
				</li>
				<li><a id="invMainIn_gridtable_confirm" class="confirmbutton"  href="javaScript:confirmInvMainInAll()"><span>记账</span>
				</a>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('invMainIn_gridtable','com.huge.ihos.material.model.InvMainIn')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			<!-- 	</li>
				<li><a id="invMainIn_gridtable_confirm" class="delbutton"  href="javaScript:confirmInvMainBook()"><span>期初记账</span>
				</a>
				</li> -->
			
			</ul>
		</div>
		<div align="right"  style="margin-top: -20px;margin-right:5px">
		本页金额合计：<label id="invMianIn_pageSum"></label>&nbsp;&nbsp;总计：<label id="invMianIn_allSum" ></label>
		</div>
		<div id="invMainIn_gridtable_div" layoutH="118"
			style="margin-left: 2px; margin-top: 10px; overflow: hidden"
			buttonBar="optId:ioId;width:850;height:600">
			<input type="hidden" id="invMainIn_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="invMainIn_gridtable_addTile">
				<s:text name="invMainNew.title"/>
			</label> 
			<label style="display: none"
				id="invMainIn_gridtable_editTile">
				<s:text name="invMainInEdit.title"/>
			</label>
			<label style="display: none"
				id="invMainIn_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="invMainIn_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_invMainIn_gridtable" style="display:none" class='loading ui-state-default ui-state-active'></div>
 <table id="invMainIn_gridtable"></table>
		<div id="invMainInPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="invMainIn_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="invMainIn_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="invMainIn_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>