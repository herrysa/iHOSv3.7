
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var balanceLayout;
	var checkBalanceGridIdString="#check_assit_balance_gridtable";
	
	function checkBalanceGridReload(){
		var urlString = "checkBalanceListGrid";
		var acctCode = jQuery("#check_balance_acctCode").val();
		var acctname = jQuery("#check_balance_acctname").val();
		var direction = jQuery("#check_balance_direction").val();
		var isAssitBalance = jQuery("#assitBalance").val();
	
		urlString=urlString+"?filter_LIKES_account.acctCode="+acctCode+"&filter_LIKES_account.acctname="+acctname+"&filter_EQS_account.direction="+direction+"&assitBalance="+isAssitBalance;
		urlString=encodeURI(urlString);
		jQuery("#check_assit_balance_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		
	}
	
	jQuery(document).ready(function() { 
		
		var checkBalanceGrid = jQuery(checkBalanceGridIdString);
		    checkBalanceGrid.jqGrid({
		    	url : "checkBalanceListGrid",
				datatype : "json",
				mtype : "GET",
		        colModel:[
{name:'assistMap.isBalance',index:'assitBalance.isBalance',align:'center',label : '<s:text name="assistMap.isBalance" />',hidden:false,width:30,sortable:false,formatter:'string'},
{name:'balanceId',index:'balanceId',align:'center',label : '<s:text name="balance.balanceId" />',hidden:true,key:true,width:60,sortable:false,formatter:'string',editable:true},				
{name:'account.acctCode',index:'account.acctCode',align:'left',label : '<s:text name="account.acctCode" />',hidden:false,sortable:false,width:120,formatter:'string'},				
{name:'account.acctname',index:'account.acctname',align:'left',label : '<s:text name="account.acctname" />',hidden:false,sortable:false,formatter:'string'},				
{name:'account.direction',index:'account.direction',align:'center',label : '<s:text name="account.direction" />',hidden:false,sortable:false,width:'50px',formatter:'string'},
{name:'initBalance',index:'total',align:'right',label : '<s:text name="balance.initBalance" />',hidden:false,sortable:false,width:80,formatter:'number'},			
{name:'assistMap.assitType',index:'account.AssistTypes',align:'center',label : '<s:text name="assistMap.assitType" />',hidden:false,sortable:false},				
{name:'assistMap.initAssistBalance',align:'right',label : '<s:text name="assistMap.initAssistBalance" />',hidden:false,sortable:false,width:80,formatter:'number'},		
		        ],
		        jsonReader : {
		        	root : "balances", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				// (4)
				},
		        //caption:'<s:text name="balanceList.title" />',
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
					reFormatBalanceAssitColumnData(this);
		            var dataTest = {"id":"check_assit_balance_gridtable"};
		      	    jQuery.publish("onLoadSelect",dataTest,null);
		      	    makepager("check_assit_balance_gridtable");
		       	},
		    });
    	jQuery(checkBalanceGrid).jqGrid('bindKeys');
	//balanceLayout.resizeAll();
  	});
	
	
	function reFormatBalanceAssitColumnData(grid){
		 var rowNum = jQuery(grid).getDataIDs().length;
		 var ret = jQuery(grid).jqGrid('getRowData');
	     if(rowNum > 0){
	    	 var rowIds = jQuery(grid).getDataIDs();
	    	 var i=0
	    	 for (i=0;i<rowNum;i++){
	    		 var id = rowIds[i];
	    		 var isBalance = $.trim(ret[i]["assistMap.isBalance"]);
	    		 if(isBalance==true||isBalance=='true'){
	    			 setCellText(grid,id,'assistMap.isBalance','<font color=green>平衡</font>');
	    		 } else {
    				 var hrefData = ret[i]["account.acctCode"];
	    			 var callUrl = "'${ctx}/balanceAssistList?balanceId="+ret[i]["balanceId"]+"'";
	    			 setCellText(grid,id,'assistMap.isBalance','<font color=red>不平衡</font>');
   		 			 setCellText(grid,id,'account.acctCode','<a style="color:blue;text-decoration:underline;cursor:pointer;"  onclick="editAssitBalanceOfAcct('+callUrl+')" target="dialog" width="880" height="600">'+hrefData+"</a>")
	    		 }
	    	 }
	    }
	}
	
	function editAssitBalanceOfAcct(url){
		$.pdialog.open(url, 'editBalance', '科室期初录入', {mask:true,width:850,height:650});　
	}
	
</script>
<div class="page">
<div id="check_assit_balance_container">
			<div id="check_assit_balance_layout-center"
				class="pane ui-layout-center">
				<div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td><s:text name='account.acctCode'/>:
							<input type="text"	id="check_balance_acctCode" >
						</td>
						<td><s:text name='account.acctname'/>:
						 	<input type="text"	id="check_balance_acctname" >
						 </td>
						 <td>
						 	<s:text name='account.direction'/>:
						 	<s:select name="subSystemC" id="check_balance_direction"  maxlength="20"
								list="#{'':'全部','借':'借','贷':'贷'}"  listKey="key"
								listValue="value" theme="simple"></s:select>
						 </td>
						 <td>
						 	<s:text name='assistMap.isBalance'/>:
						 	<s:select name="subSystemC" id="assitBalance"  maxlength="20"
								list="#{'':'全部','1':'一致','0':'不一致'}"  listKey="key"
								listValue="value" theme="simple"></s:select>
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="checkBalanceGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div id="check_assit_balance_gridtable_div" layoutH="90"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:balanceId">
			<input type="hidden" id="check_assit_balance_gridtable_navTabId" value="${sessionScope.navTabId}">
 			<table id="check_assit_balance_gridtable"></table>
		<div id="balancePager"></div>
	</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="check_assit_balance_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="check_assit_balance_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="check_assit_balance_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	</div>
	</div>
</div>
