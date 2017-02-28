
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var voucherLayout;
	var voucherGridIdString="#accountBalance_gridtable";
	
	jQuery(document).ready(function() {
		var voucherGrid = jQuery(voucherGridIdString);
    	voucherGrid.jqGrid({
    		url : "",
    		editurl:"",
			datatype : "json",
			mtype : "GET",
        	colModel:[
				/* {name:'accountId',index:'accountId',align:'center',label : '<s:text name="voucher.accountId" />',hidden:true,key:true}, */
				{name:'periodmonth',index:'periodMonth',align:'left',label : '<s:text name="voucher.kjPeriod" />',hidden:false,width:100},
				{name:'message',index:'message',align:'left',label : '<s:text name="voucher.abstractStr" />',hidden:false,width:100},
				{name:'lendmoney',index:'lendMoney',align:'right',label : '<s:text name="voucher.lendMoney" />',hidden:false,width:100,formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'}},        	
				{name:'loanmoney',index:'loanMoney',align:'right',label : '<s:text name="voucher.loanMoney" />',hidden:false,width:100,formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'}},        	
				{name:'balance',index:'balance',align:'right',label : '<s:text name="voucher.ye" />',hidden:false,width:100,formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'}}        	
				],
        	jsonReader : {
				root : "voucherCollects", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'accountId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="voucherList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: false,
			multiboxonly:false,
			shrinkToFit:false,
			autowidth:false,
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	reFormatVoucherData(this);
           	var dataTest = {"id":"accountBalance_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	//makepager("accountBalance_gridtable");
       		} 

    	});
    jQuery(voucherGrid).jqGrid('bindKeys');
    
    jQuery("#voucherCollectPeriodMonth>option").each(function(){
    	if(jQuery(this).val().indexOf("${periodMonth}")!=-1){
    		jQuery(this).attr("selected","selected");
    	}
    });
    
    /* jQuery("#accountBalance_gridtable").jqGrid('setComplexGroupHeaders',{
		 useColSpanStyle: true, 
		  groupHeaders:[
			{startColumnName: 'lendMoney', numberOfColumns: 2, titleText: '金额'}
		  ]
	}); */
    
    
    jQuery("#addVoucher").click(function(){
    	var url = "voucherCard";
		navTab.openTab("voucherCard", url, { title:"凭证", fresh:true, data:{} });
    });
    
    jQuery("#voucher_check").click(function(){
    	var voucherIds = jQuery(voucherGrid).jqGrid('getGridParam','selarrrow');
    	if(voucherIds.length==0){
    		alertMsg.error("请选择凭证记录！");
    	}else{
    		$.ajax({
			    url: 'chanegVoucherState',
			    type: 'post',
			    data:{voucherIds:voucherIds,navTabId:"accountBalance_gridtable",state:2},
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			    },
			    success: function(data){
			        formCallBack(data);
			    }
			});
    	}
    });
    jQuery("#voucher_account").click(function(){
    	var voucherIds = jQuery(voucherGrid).jqGrid('getGridParam','selarrrow');
    	if(voucherIds.length==0){
    		alertMsg.error("请选择凭证记录！");
    	}else{
    		$.ajax({
			    url: 'chanegVoucherState',
			    type: 'post',
			    data:{voucherIds:voucherIds,navTabId:"accountBalance_gridtable",state:3},
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			    },
			    success: function(data){
			        formCallBack(data);
			    }
			});
    	}
    });
    jQuery("#closeVoucher").click(function(){
    	$.ajax({
		    url: 'closeVoucher',
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    },
		    success: function(data){
		        formCallBack(data);
		    }
		});
    });
    jQuery("#accountBalance_acct").treeselect({
		dataType:"url",
		url:"makeAccountTreeWithCode",
		optType:"single",
		exceptnullparent:false,
		lazy:false,
		callback : {}
    });
    
  	});
	function reFormatVoucherData(grid){
		 var rowNum = jQuery(grid).getDataIDs().length;
		 var ret = jQuery(grid).jqGrid('getRowData');
	     if(rowNum > 0){
	    	 var rowIds = jQuery(grid).getDataIDs();
	    	 var i=0
	    	 for (i=0;i<rowNum;i++){
	    		 var id = rowIds[i];
	    		 var dataId = ret[i]["voucherId"];
	    		 var data = ret[i]["voucherNum"];
	    		 var status = ret[i]["status"];
	    		 setCellText(grid,id,'voucherNum','<a style="color:blue;text-decoration:underline;cursor:pointer;"  onclick="showVoucher(\''+dataId+'\')" target="dialog" width="880" height="600">'+data+"</a>")
	    	 }
	    }
	}
	function setCellText(grid,rowid,colName,cellTxt){
		 var  tr,cm = grid.p.colModel, iCol = 0, cCol = cm.length;
      for (; iCol<cCol; iCol++) {
          if (cm[iCol].name === colName) {
              tr = grid.rows.namedItem(rowid);
              if (tr) {
                 jQuery(tr.cells[iCol]).html(cellTxt);
              }
              break;
          }
      }
		
	}
	function showVoucher(voucherId){
		var url = "voucherCard?voucherId="+voucherId;
		navTab.openTab("voucherCard", url, { title:"凭证", fresh:true, data:{} });
	}
	
	function changeBEDate(obj){
		var thisValue = jQuery(obj).val();
		if(thisValue!='-1'){
			var beginDate = thisValue.split("_")[1];
			var endDate = thisValue.split("_")[2];
			jQuery("#voucherCollectBeginDate").val(beginDate);
			jQuery("#voucherCollectEndDate").val(endDate);
			jQuery("#voucherCollectBeginDate").attr("disabled","disabled");
			jQuery("#voucherCollectEndDate").attr("disabled","disabled");
		}else{
			jQuery("#voucherCollectBeginDate").removeAttr("disabled");
			jQuery("#voucherCollectEndDate").removeAttr("disabled");
			jQuery("#voucherCollectBeginDate").attr("onClick","WdatePicker({skin:'ext'})");
			jQuery("#voucherCollectEndDate").attr("onClick","WdatePicker({skin:'ext'})");
		}
		
	}
	
	function reloadAccountBalance(){
		var beginMonth = jQuery("#accountBalance_beginPeriod").val();
		var endMonth = jQuery("#accountBalance_endPeriod").val();
		
		var acctId = jQuery("#accountBalance_acct_id").val();
		var acctIdArr = acctId.split("_");
		var acctCode = acctIdArr[acctIdArr.length-1];
		
		var urlString = "accountBalanceGrid";
		urlString += "?acctCode="+acctCode+"&beginPeriod="+beginMonth+"&endPeriod="+endMonth;
		
		urlString=encodeURI(urlString);
		jQuery("#accountBalance_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		/* var vbd = "",vbe="";
		if(month=='-1'){
			vbd = jQuery("#voucherCollectBeginDate").val();
			vbe = jQuery("#voucherCollectEndDate").val();
		}else{
			month = month.split("_")[0];
		}
		
		var from = jQuery("#voucherCollectFrom").val();
		var state = jQuery("#voucherStatus_id").val();
		var type = jQuery("#voucherCollectType").val();
		var urlString = "voucherGridCollect";
		
		urlString += "?periodMonth="+month+"&beginDate="+vbd+"&endDate="+vbe+"&voucherFrom="+from+"&voucherType="+type+"";
		//alert(urlString);
		urlString=encodeURI(urlString);
		jQuery("#accountBalance_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); */
	}
</script>

<div class="page">
	<div id="accountBalance_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="voucher_search_form" >
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='voucher.voucherId'/>:
						<input type="text" name="filter_EQS_voucherId"/>
					</label> --%>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='voucher.abstractStr'/>:
						<input type="text" name="filter_EQS_abstractStr"/>
					</label> --%>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='voucher.attachNum'/>:
						<input type="text" name="filter_EQS_attachNum"/>
					</label> --%>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='voucher.auditDate'/>:
						<input type="text" name="filter_EQS_auditDate"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucher.auditId'/>:
						<input type="text" name="filter_EQS_auditId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucher.auditName'/>:
						<input type="text" name="filter_EQS_auditName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucher.billDate'/>:
						<input type="text" name="filter_EQS_billDate"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucher.billId'/>:
						<input type="text" name="filter_EQS_billId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucher.billName'/>:
						<input type="text" name="filter_EQS_billName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucher.businessPerson'/>:
						<input type="text" name="filter_EQS_businessPerson"/>
					</label> --%>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='voucher.checkDate'/>:
						<input type="text" name="filter_EQS_checkDate"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucher.checkId'/>:
						<input type="text" name="filter_EQS_checkId"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucher.checkName'/>:
						<input type="text" name="filter_EQS_checkName"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:textfield id="accountBalance_acct" key="voucher.account"/>
						<s:hidden id="accountBalance_acct_id" />
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						期间:
						<input type="text" id="accountBalance_beginPeriod" class="input-mini" type="text" id="endTimeId"
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})" size=8/>
					</label>到
					<label style="float:none;white-space:nowrap" >
						<input type="text" id="accountBalance_endPeriod" class="input-mini" type="text" id="endTimeId"
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})" size=8/>
					</label>&nbsp;&nbsp;
					
				</form>
					<%-- <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="reloadAccountBalance()"><s:text name='button.search'/></button>
						</div>
					</div> --%>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="reloadAccountBalance()"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<%-- <div class="panelBar">
			<ul class="toolBar">
				<li><a id="addVoucher" class="addbutton" href="javaScript:" ><span>新单
					</span>
				</a>
				</li>
				<s:if test="state==1">
				<li><a id="voucher_check" class="delbutton"  href="javaScript:"><span>审核</span>
				</a>
				</li>
				</s:if>
				<s:if test="state==2">
				<li><a id="voucher_account" class="changebutton"  href="javaScript:"
					><span>记账
					</span>
				</a>
				</li>
				</s:if>
				<li><a id="closeVoucher" class="addbutton" href="javaScript:" ><span>结账
					</span>
				</a>
				</li>
			</ul>
		</div> --%>
		<div id="accountBalance_gridtable_div" layoutH="100" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="accountBalance_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="accountBalance_gridtable_addTile">
				<s:text name="voucherNew.title"/>
			</label> 
			<label style="display: none"
				id="accountBalance_gridtable_editTile">
				<s:text name="voucherEdit.title"/>
			</label>
			<div id="load_accountBalance_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="accountBalance_gridtable"></table>
			<!--<div id="voucherPager"></div>-->
		</div>
	</div>
	 <div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="accountBalance_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="accountBalance_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="accountBalance_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>