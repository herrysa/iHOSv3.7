
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var voucherLayout;
	var voucherGridIdString="#${random }_voucher_gridtable";
	
	jQuery(document).ready(function() { 
		var voucherGrid = jQuery(voucherGridIdString);
    	voucherGrid.jqGrid({
    		url : "voucherGridList?state=${state}",
    		editurl:"voucherGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
				{name:'voucherId',index:'voucherId',align:'center',label : '<s:text name="voucher.voucherId" />',hidden:true,key:true},
				{name:'periodMonth',index:'periodMonth',align:'center',label : '<s:text name="voucher.kjPeriod" />',hidden:false,width:80},
				{name:'voucherFromCode',index:'voucherFromCode',align:'center',label : '<s:text name="voucher.voucherFromCode" />',hidden:false,width:70},
				{name:'voucherNo',index:'voucherNo',align:'center',label : '<s:text name="voucher.voucherNo" />',hidden:true,width:80},        	
				{name:'voucherNum',index:'voucherNum',align:'center',label : '<s:text name="voucher.voucherNo" />',hidden:false,width:80},        	
				{name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="voucher.orgCode" />',hidden:true},
				{name:'copyCode',index:'copyCode',align:'center',label : '<s:text name="voucher.copyCode" />',hidden:true},
				{name:'voucherDate',index:'voucherDate',align:'center',label : '<s:text name="voucher.voucherDate" />',hidden:false},
				{name:'je',index:'je',align:'right',label : '<s:text name="voucher.je" />',hidden:false,width:80,formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'}},
				{name:'abstractStr',index:'abstractStr',align:'center',label : '<s:text name="voucher.abstractStr" />',hidden:false,width:80},
				{name:'attachNum',index:'attachNum',align:'right',label : '<s:text name="voucher.attachNum" />',hidden:false,formatter:'integer',width:80},
				{name:'billId',index:'billId',align:'center',label : '<s:text name="voucher.billId" />',hidden:true},
				{name:'billName',index:'billName',align:'left',label : '<s:text name="voucher.billName" />',hidden:false,width:80},
				{name:'billDate',index:'billDate',align:'center',label : '<s:text name="voucher.billDate" />',hidden:false,width:80},
				{name:'checkId',index:'checkId',align:'center',label : '<s:text name="voucher.checkId" />',hidden:true},
				{name:'checkName',index:'checkName',align:'left',label : '<s:text name="voucher.checkName" />',hidden:false,width:80},
				{name:'checkDate',index:'checkDate',align:'center',label : '<s:text name="voucher.checkDate" />',hidden:false,width:80},
				{name:'auditId',index:'auditId',align:'center',label : '<s:text name="voucher.auditId" />',hidden:true},
				{name:'auditName',index:'auditName',align:'left',label : '<s:text name="voucher.auditName" />',hidden:false,width:80},
				{name:'auditDate',index:'auditDate',align:'center',label : '<s:text name="voucher.auditDate" />',hidden:false,width:80},
				{name:'businessPerson',index:'businessPerson',align:'center',label : '<s:text name="voucher.businessPerson" />',hidden:false,width:80},
				{name:'kjManager',index:'kjManager',align:'center',label : '<s:text name="voucher.kjManager" />',hidden:true},
				{name:'pzhzbz',index:'pzhzbz',align:'center',label : '<s:text name="voucher.pzhzbz" />',hidden:true},
				{name:'pzhzkmdy',index:'pzhzkmdy',align:'center',label : '<s:text name="voucher.pzhzkmdy" />',hidden:true},
				{name:'remark',index:'remark',align:'center',label : '<s:text name="voucher.remark" />',hidden:false},
				{name:'status',index:'status',align:'center',label : '<s:text name="voucher.status" />',hidden:true,formatter:'integer'},
				],
        	jsonReader : {
				root : "vouchers", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'voucherId',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="voucherList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:true,
			autowidth:true,
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	reFormatVoucherData(this);
           	var dataTest = {"id":"${random }_voucher_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	//makepager("${random }_voucher_gridtable");
       		} 

    	});
    jQuery(voucherGrid).jqGrid('bindKeys');
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
			    data:{voucherIds:voucherIds,navTabId:"${random }_voucher_gridtable",state:2},
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
			    data:{voucherIds:voucherIds,navTabId:"${random }_voucher_gridtable",state:3},
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
		    url: 'closeAccountMonth',
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
</script>

<div class="page">
	<div id="voucher_pageHeader" class="pageHeader">
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
						日期范围:
						<select>
							<option value="0">本日
							</option>
							<option value="1">本月
							</option>
						</select>
					</label>
					<label style="float:none;white-space:nowrap" >
						起止日期:
						<input type="text" name="" disabled value='2016-01-01' size=8/>
						<input type="text" name="" disabled value='2016-12-31' size=8/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucher.voucherFromCode'/>:
						<input type="text" name="filter_EQS_voucherFromCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						凭证类型:
						<input type="text" name="filter_EQS_voucherType"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						凭证号:
						<input type="text" name="filter_EQS_voucherType" size=3/>
						到<input type="text" name="" size=3/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='voucher.kjManager'/>:
						<input type="text" name="filter_EQS_kjManager"/>
					</label> --%>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='voucher.kjPeriod'/>:
						<input type="text" name="filter_EQS_kjPeriod"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucher.orgCode'/>:
						<input type="text" name="filter_EQS_orgCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucher.pzhzbz'/>:
						<input type="text" name="filter_EQS_pzhzbz"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucher.pzhzkmdy'/>:
						<input type="text" name="filter_EQS_pzhzkmdy"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='voucher.remark'/>:
						<input type="text" name="filter_EQS_remark"/>
					</label> --%>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='voucher.status'/>:
						<input type="text" name="filter_EQS_status"/>
					</label> --%>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='voucher.voucherDate'/>:
						<input type="text" name="filter_EQS_voucherDate"/>
					</label> --%>
					
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='voucher.voucherNo'/>:
						<input type="text" name="filter_EQS_voucherNo"/>
					</label> --%>
				</form>
					<%-- <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch(voucher_search_form,${random }_voucher_gridtable)"><s:text name='button.search'/></button>
						</div>
					</div> --%>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch(voucher_search_form,${random }_voucher_gridtable)"><s:text name='button.search'/></button>
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
		</div>
		<div id="${random }_voucher_gridtable_div" layoutH="145" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="${random }_voucher_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="${random }_voucher_gridtable_addTile">
				<s:text name="voucherNew.title"/>
			</label> 
			<label style="display: none"
				id="${random }_voucher_gridtable_editTile">
				<s:text name="voucherEdit.title"/>
			</label>
			<div id="load_${random }_voucher_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="${random }_voucher_gridtable"></table>
			<!--<div id="voucherPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random }_voucher_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random }_voucher_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${random }_voucher_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>