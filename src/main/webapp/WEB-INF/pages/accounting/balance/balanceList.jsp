
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	 function balanceGridReload(){
		var urlString = "balanceGridList";
		var acctCode = jQuery("#acctCode").val();
		var acctname = jQuery("#acctname").val();
		var leaf = jQuery("#leaf").val();
		var direction = jQuery("#direction").val();
	
		urlString=urlString+"?filter_LIKES_account.acctCode="+acctCode+"&filter_LIKES_account.acctname="+acctname+"&filter_EQB_account.leaf="+leaf+"&filter_EQS_account.direction="+direction;
		urlString=encodeURI(urlString);
		jQuery("#balance_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	var balanceLayout;
			  var balanceGridIdString="#balance_gridtable";
	
	jQuery(document).ready(function() { 
var balanceGrid = jQuery(balanceGridIdString);
    balanceGrid.jqGrid({
    	url : "balanceGridList",
    	editurl:"balanceGridEdit",
    	editinline:true,
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'balanceId',index:'balanceId',align:'center',label : '<s:text name="balance.balanceId" />',hidden:true,key:true,editable:true},				
{name:'account.acctId',index:'account.acctId',align:'center',label : '<s:text name="account.acctId" />',hidden:true,formatter:'string'},				
{name:'account.acctCode',index:'account.acctCode',align:'left',label : '<s:text name="account.acctCode" />',hidden:false,width:70,formatter:'string'},				
{name:'account.acctname',index:'account.acctname',align:'left',label : '<s:text name="account.acctname" />',hidden:false,formatter:'string'},				
{name:'account.leaf',index:'account.leaf',align:'center',label : '<s:text name="account.leaf" />',hidden:true,formatter:'string'},				
{name:'account.direction',index:'account.direction',align:'center',label : '<s:text name="account.direction" />',hidden:false,formatter:'string',width:'50px'},
{name:'beginJie',index:'beginJie',align:'right',label : '<s:text name="balance.beginJie" />',hidden:false,width:50,formatter:'number',edittype:'text',editable:true,editrules:{required:true,number:true}},
{name:'beginDai',index:'beginDai',align:'right',label : '<s:text name="balance.beginDai" />',hidden:false,width:50,formatter:'number',edittype:'text',editable:true,editrules:{required:true,number:true}},
{name:'yearBalance',index:'yearBalance',align:'right',label : '<s:text name="balance.yearBalance" />',hidden:false,width:50,formatter:'number',edittype:'text',editable:true,editable:true,editrules:{required:true,number:true}},				
{name:'initBalance',index:'initBalance',align:'right',label : '<s:text name="balance.initBalance" />',sortable:true,width:50,hidden:false,formatter:'number',editable:true},		
{name:'account.assistTypes',index:'account.assistTypes',align:'right',label : '<s:text name="account.AssistTypes" />',hidden:true,formatter:'string'},		
        ],
        jsonReader : {
			root : "balances", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'balanceId',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="balanceList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:false,
		navigatorSearch:false,
		 beforeSelectRow: function(rowid, e) {
             var cbsdis = $("tr#"+rowid+".jqgrow > td > input.cbox:disabled", jQuery(this)[0]);
             if (cbsdis.length === 0) {
                 return true;    // allow select the row
             } else {
                 return false;   // not allow select the row
             }
         },
         onSelectAll: function(aRowids,status) {
             if (status) {
                 // uncheck "protected" rows
                 var cbs = $("tr.jqgrow > td > input.cbox:disabled", jQuery(this)[0]);
                 cbs.removeAttr("checked");
                 //modify the selarrrow parameter
                 jQuery(this)[0].p.selarrrow = jQuery(this).find("tr.jqgrow:has(td > input.cbox:checked)")
                     .map(function() { return this.id; }) // convert to set of ids
                     .get(); // convert to instance of Array
             }
         } ,
         
		 gridComplete:function(){
			 reFormatBalanceData(this); 	
           var dataTest = {"id":"balance_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("balance_gridtable");
      	 	jQuery("#balance_gridtable").find("tr").each(function(){
 			var leaf= jQuery(this).find("td").eq(6).text();
 			var ids = jQuery(this).find("td").eq(0).text();
 			if(leaf == null || leaf == 'false'|| leaf==false){
 				jQuery(this).find("td").each(function(){
					if(jQuery(this).children().eq(0).attr("type")=="checkbox"){
						jQuery(this).children().eq(0).attr("disabled","true");
				}
			});
 				
 			}
 		});
       	} 
    });
    jQuery(balanceGrid).jqGrid('bindKeys');
  	});
	function editBalanceOfAcct(url){
		$.pdialog.open(url, 'editBalance', '科室期初录入', {mask:false,width:900,height:650});　
	}
	
	function isBalanceCheck(url){
		$.pdialog.open(url, 'isBalance', '试算平衡', {mask:false,width:500,height:350});　
	}
	function checkBalance(url){
		$.pdialog.open(url, 'checkBalanceList', '辅助核算项检查', {mask:true,width:800,height:630});　
	}
	
	function reFormatBalanceData(grid){
		 var rowNum = jQuery(grid).getDataIDs().length;
		 var ret = jQuery(grid).jqGrid('getRowData');
	     if(rowNum > 0){
	    	 var rowIds = jQuery(grid).getDataIDs();
	    	 var i=0
	    	 for (i=0;i<rowNum;i++){
	    		 var id = rowIds[i];
	    		 var leaf = $.trim(ret[i]["account.leaf"]);
	    		 var assistTypes = $.trim(ret[i]["account.assistTypes"]);
	    		 if(leaf==true||leaf=='true'){
	    			 if(assistTypes == ',' || assistTypes == null||assistTypes == ''||assistTypes=='null'){
	    			 } else {
	    				 var hrefData = ret[i]["account.acctCode"];
		    			 var callUrl = "'${ctx}/balanceAssistList?balanceId="+ret[i]["balanceId"]+"'";
		    			 setCellText(grid,id,'account.acctCode','<a style="color:blue;text-decoration:underline;cursor:pointer;"  onclick="editBalanceOfAcct('+callUrl+')" target="dialog" width="880" height="600">'+hrefData+"</a>")
	    			 }
	    		 }
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
	
	function rowEditValidate(grid){
		var rowid = grid.jqGrid('getGridParam','selrow')
		var tr = grid.getRowData(rowid);
		var assistType = $.trim(tr["account.assistTypes"]);
		if(assistType!='' && assistType!=',' && assistType!=null){
			alertMsg.error("对不起，您选择的科室含有辅助核算， 请点击科室代码，进入余额录入页面！");
		} else {
			gridEditRow(grid);
			var $beginJie = grid.find("input[name='beginJie']:visible");
			var $beginDai = grid.find("input[name='beginDai']:visible");
			var $yearBalance = grid.find("input[name='yearBalance']:visible");
			var $initBalance = grid.find("input[name='initBalance']:visible");
			
			$("input[name='beginJie']:visible,input[name='beginDai']:visible,input[name='yearBalance']:visible").unbind('blur').bind('blur',function(){
				var beginJie = $beginJie.val()*1;
				var beginDai = $beginDai.val()*1;
				var yearBalance = $yearBalance.val()*1;
				var initBalance = $initBalance.val()*1;
				initBalance = beginJie-beginDai+yearBalance;
				$initBalance.val(initBalance);
			});
			$($initBalance).unbind('blur').bind('blur',function(){
				var beginJie = $beginJie.val()*1;
				var beginDai = $beginDai.val()*1;
				var initBalance = $initBalance.val()*1;
				var yearBalance = $yearBalance.val()*1;
				yearBalance = initBalance-beginJie+beginDai;
				$yearBalance.val(yearBalance);
			});
		
			
			
		}
	}
	function initBalance(){
		jQuery.ajax({
		    url: 'initBalance',
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		       // alert("failed");
		    },
		    success: function(data){
		    	if(data.statusCode == 200){
		    		alertMsg.correct(data.message);
		    	} else {
		    		alertMsg.error(data.message);
		    	}
		    	balanceGridReload();
		    }
		});
	}
	function setUpBalance(){
		jQuery.ajax({
		    url: 'setUpBalance',
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    	alertMsg.error(data.message);
		    },
		    success: function(data){
		    	formCallBack(data);
		    	navTab.reload("balanceList", {
					title : "",
					fresh : false,
					data : {}
				});
		    }
		});
	}
	function shutDownBalance(){
		jQuery.ajax({
		    url: 'shutDownBalance',
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    	alertMsg.error(data.message);
		    },
		    success: function(data){
		    	alertMsg.correct(data.message);
		    	navTab.reload("balanceList", {
					title : "",
					fresh : true,
					data : {}
				});
		    }
		});
	}
	
</script>

<div class="page">
<div id="balance_container">
			<div id="balance_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td><s:text name='account.acctCode'/>:
							<input type="text"	id="acctCode" >
						</td>
						<td><s:text name='account.acctname'/>:
						 	<input type="text"	id="acctname" >
						 </td>
						<td>
							<s:text name='account.leaf'/>:
						 	<s:select name="leaf" id="leaf"  maxlength="20" 
								list="#{'':'全部','0':'上级','1':'末级'}"  listKey="key"
								listValue="value" theme="simple"></s:select>
						 </td>
						 <td>
						 	<s:text name='account.direction'/>:
						 	<s:select name="subSystemC" id="direction"  maxlength="20"
								list="#{'':'全部','借':'借','贷':'贷'}"  listKey="key"
								listValue="value" theme="simple"></s:select>
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="balanceGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">

		<div class="panelBar">
			<ul class="toolBar">
			<c:if test="${copyBalanceFlag!='true'}">
				<li><a class="editbutton" onclick="javascript:rowEditValidate(jQuery('#balance_gridtable'))" ><span><fmt:message key="button.editRow" /></span></a></li>
				<li><a class="canceleditbutton" onclick="gridRestore(jQuery('#balance_gridtable'))" ><span><fmt:message key="button.restoreRow" /></span></a></li>
				<li><a class="savebutton" onclick="gridSaveRow(jQuery('#balance_gridtable'))" ><span><fmt:message key="button.saveRow" /></span></a></li>
				<li><a class="savebutton" onclick="isBalanceCheck('isBalance')" ><span><s:text name="button.isBalance" /></span></a></li>
				<li><a class="savebutton" onclick="checkBalance('checkBalanceList')" ><span><s:text name="button.checkBalance" /></span></a></li>
				<c:if test="${initBalStatus!='true'}">
				<li><a class="addbutton" onclick="initBalance()" ><span><s:text name="button.initBalance" /></span></a></li>
				</c:if>
				<li><a class="addbutton" onclick="setUpBalance()" ><span><s:text name="button.setUpBalance" /></span></a></li>
			</c:if>
			<c:if test="${copyBalanceFlag=='true'}">
				<li><a class="addbutton" onclick="shutDownBalance()" ><span><s:text name="button.shutDownBalance" /></span></a></li>
			</c:if>
			</ul>
		</div>
		<div id="balance_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="balance_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="balance_gridtable_addTile">
				<s:text name="balanceNew.title"/>
			</label> 
			<label style="display: none"
				id="balance_gridtable_editTile">
				<s:text name="balanceEdit.title"/>
			</label>
			<label style="display: none"
				id="balance_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="balance_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_balance_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="balance_gridtable"></table>
		<div id="balancePager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="balance_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="balance_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="balance_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>