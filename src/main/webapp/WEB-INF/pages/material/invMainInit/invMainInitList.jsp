
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
var width=960 , height = 628;
	function insertInvMainForm(storeId) {
		$.pdialog.open("${ctx}/editInvMainInit?radomJsp="+Math.floor(Math.random()*10),"addInvMainInit","新建期初入库", {mask:true,width:width,height:height});
	}
	
	function delInvMainInit(){
		var url = "${ctx}/invMainInitGridEdit?oper=del"

		var sid = jQuery("#invMainInit_gridtable").jqGrid('getGridParam',
				'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#invMainInit_gridtable").jqGrid('getRowData',rowId);
				
				if(row['status']!=0){
					alertMsg.error("您选择的入库单["+row['ioBillNumber']+"]已记账.不能删除!");
					return;
				}
			}
			url = url + "&id=" + sid+"&navTabId=invMainInit_gridtable";
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					jQuery.post(url, function(data) {
						formCallBack(data);
					});
				}
			});
		}
	}
	function confirmInvMainInitAll() {
		var url = "${ctx}/invMainInitConfirm"
		var sid = jQuery("#invMainInit_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#invMainInit_gridtable").jqGrid('getRowData',rowId);
				
				if(row['status']!=0){
					alertMsg.error("您选择的入库单["+row['ioBillNumber']+"]已记账.不能重复记账!");
					return;
				}
			}
			url = url + "?id=" + sid+"&navTabId=invMainInit_gridtable";
			alertMsg.confirm("确认记账？", {
				okCall : function() {
					jQuery.post(url, function(data) {
						formCallBack(data);
					});
				}
			});
		}
	}
	function invMainGridReload() {
		var urlString = "invMainInitGridList?filter_EQS_ioType=1&filter_EQS_busType.typeCode=39";
		var invMainInit_search_make_date_from = jQuery("#invMainInit_search_make_date_from").val();
		var invMainInit_search_make_date_to = jQuery("#invMainInit_search_make_date_to").val();
		var invMainInit_search_confirm_date_from = jQuery("#invMainInit_search_confirm_date_from").val();
		var invMainInit_search_confirm_date_to = jQuery("#invMainInit_search_confirm_date_to").val();
		var search_store = jQuery("#search_store_id").val();
		var invMainInit_status = jQuery("#invMainInit_search_status_id").val();
		urlString = urlString + "&filter_GED_makeDate="+invMainInit_search_make_date_from +
					"&filter_LED_makeDate="+ invMainInit_search_make_date_to + 
					"&filter_GED_confirmDate="+invMainInit_search_confirm_date_from +
					"&filter_LED_confirmDate="+ invMainInit_search_confirm_date_to + 
				"&filter_EQS_store.id="
				+ search_store+"&filter_EQS_status="+invMainInit_status;
		urlString = encodeURI(urlString);
		jQuery("#invMainInit_gridtable").jqGrid('setGridParam', {
			url : urlString,
			page : 1
		}).trigger("reloadGrid");
	}
	var invMainGridIdString = "#invMainInit_gridtable";

	jQuery(document).ready(function() {
		var initFlag = 0;
		var invMainGrid = jQuery(invMainGridIdString);
		invMainGrid.jqGrid({
			url : "invMainInitGridList?filter_EQS_ioType=1&filter_EQS_busType.typeCode=39&filter_GED_makeDate=${currentPeriodBeginDate}&filter_LED_makeDate=${currentSystemVariable.businessDate}",
			editurl : "invMainInitGridEdit",
			datatype : "json",
			mtype : "GET",
			colModel : [
				{name : 'ioId',index : 'ioId',align : 'center',label : '<s:text name="invMain.ioId" />',hidden : true,key : true},
				{name : 'ioBillNumber',index : 'ioBillNumber',align : 'left',label : '<s:text name="invMain.ioBillNumber" />',hidden : false,highsearch:true},
				{name : 'store.storeName',index : 'store.storeName',align : 'left',label : '<s:text name="invMain.store" />',hidden : false,highsearch:true},
				{name : 'busType.typeName',index : 'busType.typeName',align : 'left',label : '<s:text name="invMain.busType" />',hidden : false,highsearch:true},
				{name : 'makeDate',index : 'makeDate',align : 'center',label : '<s:text name="invMain.makeDate" />',hidden : false,highsearch:true},
				{name : 'makePerson.name',index : 'makePerson',align : 'left',width : 60,label : '<s:text name="invMain.makePerson" />',hidden : false,highsearch:true},
				{name : 'confirmDate',index : 'confirmDate',align : 'center',width : 100,label : '<s:text name="invMain.confirmDate" />',hidden : false,highsearch:true},
				{name : 'totalMoney',index : 'totalMoney',align : 'right',width : 120,label : '<s:text name="invMain.totalMoney" />',hidden : false,highsearch:true,sortable :true,formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces:2}  },
				{name : 'remark',index : 'remark',align : 'center',label : '<s:text name="invMain.remark" />',hidden : false,highsearch:true},
				{name : 'status',index : 'status',align : 'center',label : '<s:text name="invMain.status" />',hidden : false,highsearch:true,formatter : 'select',editable : false,edittype : "select",editoptions : {value : "0 :新建; 1: 已审核; 2: 已记账; 3: 已生成凭证"}} 
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
				jQuery("#invMianInit_allSum").text(formatNum(parseFloat(ss).toFixed(2)));
				var amount = jQuery(this).getCol('totalMoney',false,'sum');
				jQuery("#invMianInit_pageSum").text(formatNum(amount.toFixed(2)));
				var rowNum = jQuery(this).getDataIDs().length;
		           if(rowNum>0){
		              //jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
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
		            	  editUrl = "'${ctx}/editInvMainInit?ioId="+ret[i]["ioId"]+"'";
		   	        	  setCellText(this,id,'ioBillNumber','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showInvMainInit('+editUrl+');">'+ret[i]["ioBillNumber"]+'</a>');
		              }
		            }
				var dataTest = {"id" : "invMainInit_gridtable"};
				jQuery.publish("onLoadSelect",dataTest, null);
				makepager("invMainInit_gridtable");
				initFlag = initColumn('invMainInit_gridtable','com.huge.ihos.material.model.InvMainInit',initFlag);
			}
		});
		jQuery(invMainGrid).jqGrid('bindKeys');
	
		jQuery("#search_store").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where parent_id is not null and disabled = 0 and  orgCode='"+ "${fns:userContextParam('orgCode')}" + "'",
			exceptnullparent : false,
			lazy : false
		});
	});
	
	function showInvMainInit(url){
		$.pdialog.open(url,"showInvMainInit","期初入库单明细", {mask:true,width:width,height:height});
	}
	
</script>

<div class="page">
	<div id="invMainInit_container">
		<div id="invMainInit_layout-center" class="pane ui-layout-center">
			<div id="invMainInit_pageHeader" class="pageHeader">
				<div class="searchBar">
					<div class="searchContent">
						<form id="invMainInit_search_form" >
							<label style="float:none;white-space:nowrap" >仓库:
							 	<input type="hidden"		id="search_store_id" >
							 	<input type="text"		id="search_store" >
							</label>&nbsp;&nbsp;
							<label style="float:none;white-space:nowrap" >制单日期:
								<input type="text" style="width:80px;height:15px"	id="invMainInit_search_make_date_from" class="Wdate" value="${currentPeriodBeginDate}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'invMainInit_search_make_date_to\')}'})" >
								至
							 	<input type="text" style="width:80px;height:15px"	id="invMainInit_search_make_date_to"  class="Wdate" value="${currentSystemVariable.businessDate}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'invMainInit_search_make_date_from\')}'})" >
							</label>&nbsp;&nbsp;
							<label style="float:none;white-space:nowrap" ><s:text name="invMain.confirmDate" />:
								<input type="text" style="width:80px;height:15px"	id="invMainInit_search_confirm_date_from" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'invMainInit_search_confirm_date_to\')}'})" >
								至
							 	<input type="text" style="width:80px;height:15px"	id="invMainInit_search_confirm_date_to"  class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'invMainInit_search_confirm_date_from\')}'})" >
							</label>&nbsp;&nbsp;
							<label style="float:none;white-space:nowrap" >状态:
								<select id="invMainInit_search_status_id">
									 <option ></option>
									 <option value="0">新建</option>
									 <option value="2">已记账</option>
									 <option value="3">已生成凭证</option>
								</select> 
							</label>&nbsp;&nbsp;
							<%-- <div class="buttonActive" style="float:right">
									<div class="buttonContent">
										<button type="button" onclick="invMainGridReload()">
											<s:text name='button.search' />
										</button>
									</div>
							</div> --%>
						</form>
					</div>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive">
									<div class="buttonContent">
										<button type="button" onclick="invMainGridReload()">
											<s:text name='button.search' />
										</button>
									</div>
								</div></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="pageContent">
				<div class="panelBar">
					<ul class="toolBar">
						<li><a id="invMainInit_gridtable_add_custom" class="addbutton" href="javaScript:insertInvMainForm();"><span><s:text name="新单" /></span> </span> </a></li>
						 <li><a id="invMainInit_gridtable_del_custom" class="delbutton" href="javaScript:delInvMainInit()"><span><s:text name="button.delete" /></span> </a></li>
						<li><a id="invMainInit_gridtable_confirm" class="confirmbutton" href="javaScript:confirmInvMainInitAll()"><span>记账</span> </a></li>
						<li>
							<a class="settlebutton"  href="javaScript:setColShow('invMainInit_gridtable','com.huge.ihos.material.model.InvMainInit')"><span><s:text name="button.setColShow" /></span></a>
						</li>
					</ul>
				</div>
				<div align="right"  style="margin-top: -20px;margin-right:5px">
				本页金额合计：<label id="invMianInit_pageSum"></label>&nbsp;&nbsp;总计：<label id="invMianInit_allSum" ></label>
				</div>
				<div id="invMainInit_gridtable_div" layoutH="145" style="margin-left: 2px; margin-top: 10px; overflow: hidden" buttonBar="optId:ioId;width:850;height:600">
					<input type="hidden" id="invMainInit_gridtable_navTabId" value="${sessionScope.navTabId}"> <label style="display: none" id="invMainInit_gridtable_addTile"> <s:text
							name="invMainNew.title" />
					</label> <label style="display: none" id="invMainInit_gridtable_editTile"> <s:text name="invMainEdit.title" />
					</label> <label style="display: none" id="invMainInit_gridtable_selectNone"> <s:text name='list.selectNone' />
					</label> <label style="display: none" id="invMainInit_gridtable_selectMore"> <s:text name='list.selectMore' />
					</label>
					<div id="load_invMainInit_gridtable" class='loading ui-state-default ui-state-active' style="display: none;"></div>
					<table id="invMainInit_gridtable"></table>
					<div id="invMainPager"></div>
				</div>
			</div>
			<div class="panelBar">
				<div class="pages">
					<span><s:text name="pager.perPage" /></span> <select id="invMainInit_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="invMainInit_gridtable_totals"></label>
					<s:text name="pager.items" /></span>
				</div>

				<div id="invMainInit_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>

			</div>
		</div>
	</div>
</div>