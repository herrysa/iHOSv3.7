
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var invCheckGridIdString="#invCheck_gridtable";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
var invCheckGrid = jQuery(invCheckGridIdString);
    invCheckGrid.jqGrid({
    	url : "invCheckGridList?filter_GED_makeDate=${currentPeriodBeginDate}&filter_LED_makeDate=${currentSystemVariable.businessDate}",
    	editurl:"invCheckGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'checkId',index:'checkId',align:'center',label : '<s:text name="invCheck.checkId" />',hidden:true,key:true},				
{name:'checkNo',index:'checkNo',align:'left',width:120,label : '<s:text name="invCheck.checkNo" />',hidden:false,highsearch:true}, 				
{name:'store.storeName',index:'store.storeName',align:'left',width:100,label : '<s:text name="invCheck.store" />',hidden:false,highsearch:true},				
{name:'makeDate',index:'makeDate',align:'center',width:100,label : '<s:text name="invCheck.makeDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
{name:'person.name',index:'person.name',align:'left',width:60,label : '<s:text name="invCheck.person" />',hidden:false,highsearch:true},				
{name:'makePerson.name',index:'makePerson.name',align:'left',width:60,label : '<s:text name="invCheck.makePerson" />',hidden:false,highsearch:true},				
{name:'checkPerson.name',index:'checkPerson.name',align:'left',width:60,label : '<s:text name="invCheck.checkPerson" />',hidden:true,highsearch:false},			
{name:'confirmDate',index:'confirmDate',align:'center',width:100,label : '<s:text name="invCheck.confirmDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
{name:'totalDiffMoney',index:'totalDiffMoney',align:'right',width:120,label : '<s:text name="invCheck.totalDiffMoney" />',hidden:false,highsearch:true,formatter : 'currency',formatoptions:{thousandsSeparator: ',',decimalPlaces:2} },			
/* {name:'yearMonth',index:'yearMonth',align:'center',label : '<s:text name="invCheck.yearMonth" />',hidden:false}, */
{name:'invMainIn.ioId',index:'invMainIn.ioId',align:'left',width:130,label : '<s:text name="invCheck.inId" />',hidden:true},				
{name:'invMainIn.ioBillNumber',index:'invMainIn.ioBillNumber',align:'left',width:130,label : '<s:text name="invCheck.inId" />',hidden:false,highsearch:true},				
{name:'invMainOut.ioId',index:'invMainOut.ioId',align:'left',width:130,label : '<s:text name="invCheck.outId" />',hidden:true},
{name:'invMainOut.ioBillNumber',index:'invMainOut.ioBillNumber',align:'left',width:130,label : '<s:text name="invCheck.outId" />',hidden:false,highsearch:true},
{name:'remark',index:'remark',align:'left',width:200,label : '<s:text name="invCheck.remark" />',hidden:false,highsearch:true},		
{name:'state',index:'state',align:'center',width:80,label : '<s:text name="invCheck.state" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '0:<s:text name="invCheck.state.new"/>;1:<s:text name="invCheck.state.checked"/>;2:<s:text name="invCheck.state.completed"/>'}}				

        ],
        jsonReader : {
			root : "invChecks", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'checkNo',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="invCheckList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:false,
		autowidth:false,
        onSelectRow: function(rowid) {
       
       	},
		 gridComplete:function(){
			 var sumDataStr = jQuery(this).getGridParam('userData');
			 var ss = 0;
				if(sumDataStr){
					ss = sumDataStr.sum;
				}
			jQuery("#invCheck_allSum").text(formatNum(parseFloat(ss).toFixed(2)));
			var amount = jQuery(this).getCol('totalDiffMoney',false,'sum');
			jQuery("#invCheck_pageSum").text(formatNum(amount.toFixed(2)));
			 //设置超链接样式 
			var rowNum = jQuery(this).getDataIDs().length;
           if(rowNum>0){
              //jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
              var rowIds = jQuery(this).getDataIDs();
              var ret = jQuery(this).jqGrid('getRowData');
              var id='';
              var editUrl='',inUrl='',outUrl='';
              for(var i=0;i<rowNum;i++){
            	  id=rowIds[i];
            	  if(ret[i]['state']=="0"){
            		  setCellText(this,id,'state','<span >新建</span>');
            	  }else if(ret[i]['state']=="1"){
            		  setCellText(this,id,'state','<span style="color:green" >已审核</span>');
            	  }else if(ret[i]['state']=="2"){
            		  setCellText(this,id,'state','<span style="color:blue" >已完成</span>');
            		  inUrl = "'${ctx}/editInvMainIn?ioId="+ret[i]["invMainIn.ioId"]+"&docPreview=refer'";
       	        	  setCellText(this,id,'invMainIn.ioBillNumber','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showInvCheckIn('+inUrl+');">'+ret[i]["invMainIn.ioBillNumber"]+'</a>');
       	        	  outUrl = "'${ctx}/editInvMainOut?ioId="+ret[i]["invMainOut.ioId"]+"&docPreview=refer&docType=出库单'";
       	        	  setCellText(this,id,'invMainOut.ioBillNumber','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showInvCheckOut('+outUrl+');">'+ret[i]["invMainOut.ioBillNumber"]+'</a>');
            	  }
            	  editUrl = "'${ctx}/editInvCheck?popup=true&checkId="+ret[i]["checkId"]+"'";
   	        	  setCellText(this,id,'checkNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showInvCheckDetail('+editUrl+');">'+ret[i]["checkNo"]+'</a>');
   	        	 }
            }
           var dataTest = {"id":"invCheck_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("invCheck_gridtable");
      	 initFlag = initColumn('invCheck_gridtable','com.huge.ihos.material.model.InvCheck',initFlag);
       	} 
    });
    jQuery(invCheckGrid).jqGrid('bindKeys');
	    
	   jQuery("#invCheck_search_store").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  storeId id,storeName name,parent_id parent FROM sy_store where is_book = 1 and orgCode='"+"${fns:userContextParam('orgCode')}"+"'",
			exceptnullparent : false,
			lazy : false
		}); 
	    
	    jQuery("#invCheck_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
			$.pdialog.open('${ctx}/editInvCheck','addInvCheck',"添加盘点单", {mask:true,width : 960,height : 628,resizable:false});
	    	
	    });
	    jQuery("#invCheck_gridtable_check").unbind( 'click' ).bind("click",function(){
	    	doInvCheck("check");
		});
	    jQuery("#invCheck_gridtable_cancelCheck").unbind( 'click' ).bind("click",function(){
	    	doInvCheck("cancelcheck");
		});
	});
	function delInvCheck(){
		var url = "${ctx}/invCheckGridEdit?oper=del"
		var sid = jQuery("#invCheck_gridtable").jqGrid('getGridParam',
				'selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#invCheck_gridtable").jqGrid('getRowData',rowId);
				
				if(row['state']==1){
					alertMsg.error("您选择的盘点单["+row['checkNo']+"]已审核.不能删除!");
					return;
				}else if(row['state']==2){
					alertMsg.error("您选择的盘点单["+row['checkNo']+"]已完成.不能删除!");
					return;
				}
			}
			url = url+"&id="+sid+"&navTabId=invCheck_gridtable";
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					jQuery.post(url, function(data) {
						formCallBack(data);
					});

				}
			});
		}
	}
	function exportInOutAll(){
		//var editUrl = "${ctx}/invCheckGridEdit?oper=exportInOut";
		var sid = jQuery("#invCheck_gridtable").jqGrid('getGridParam',
				'selarrrow');
		var editUrl = jQuery("#invCheck_gridtable").jqGrid('getGridParam', 'editurl');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#invCheck_gridtable").jqGrid('getRowData',rowId);
				
				if(row['state']==0){
					alertMsg.error("您选择的盘点单["+row['checkNo']+"]尚未审核.不能生成出入库单!");
					return;
				}else if(row['state']==2){
					alertMsg.error("您选择的盘点单["+row['checkNo']+"]已完成.不能生成出入库单!");
					return;
				}
			}
			editUrl = editUrl+"?id="+sid+"&navTabId=invCheck_gridtable&oper=exportInOut";
			alertMsg.confirm("确认生成出入库单？", {
				okCall : function() {
					jQuery.post(editUrl, function(data) {
						formCallBack(data);
					});

				}
			});
		}
	}
	function doInvCheck(oper){
		var checkIds = jQuery("#invCheck_gridtable").jqGrid('getGridParam','selarrrow');
		var editUrl = jQuery("#invCheck_gridtable").jqGrid('getGridParam', 'editurl');
		editUrl+="?id=" + checkIds+"&navTabId=invCheck_gridtable&oper="+oper;
		editUrl = encodeURI(editUrl);
		if(checkIds==null || checkIds.length ==0){
				alertMsg.error("请选择记录！");
				return;
		}else{
			if(oper=="check"){
				for(var i = 0;i < checkIds.length; i++){
					var rowId = checkIds[i];
					var row = jQuery("#invCheck_gridtable").jqGrid('getRowData',rowId);
					if(row['state']==1){
						alertMsg.error("您选择的盘点单["+row['checkNo']+"]已审核.不能重复审核!");
						return;
					}else if(row['state']==2){
						alertMsg.error("您选择的盘点单["+row['checkNo']+"]已完成.不能审核!");
						return;
					}
				}
				alertMsg.confirm("确认审核？", {
					okCall : function() {
						$.post(editUrl,function(data) {
							formCallBack(data);
						});
					}
				});
			}
			if(oper=="cancelcheck"){
				for(var i = 0;i < checkIds.length; i++){
					var rowId = checkIds[i];
					var row = jQuery("#invCheck_gridtable").jqGrid('getRowData',rowId);
					if(row['state']==0){
						alertMsg.error("您选择的盘点单["+row['checkNo']+"]尚未审核.请审核!");
						return;
					}else if(row['state']==2){
						alertMsg.error("您选择的盘点单["+row['checkNo']+"]已完成.不能销审!");
						return;
					}
				}
				alertMsg.confirm("确认销审？", {
					okCall : function() {
						$.post(editUrl,function(data) {
							formCallBack(data);
						});
					}
				});
			}
			
			
		}
	}
	function reloadInvCheckList(){
		var urlString = "invCheckGridList";
		var invCheck_search_make_date_from = jQuery("#invCheck_search_make_date_from").val();
		var invCheck_search_make_date_to = jQuery("#invCheck_search_make_date_to").val();
		var invCheck_search_confirm_date_from = jQuery("#invCheck_search_confirm_date_from").val();
		var invCheck_search_confirm_date_to = jQuery("#invCheck_search_confirm_date_to").val();
		var search_store = jQuery("#invCheck_search_store_id").val();
		var search_state = jQuery("#invCheck_search_state").val();

		urlString = urlString
				+ "?filter_GED_makeDate=" + invCheck_search_make_date_from
				+ "&filter_LED_makeDate=" + invCheck_search_make_date_to
				+ "&filter_GED_confirmDate=" + invCheck_search_confirm_date_from
				+ "&filter_LED_confirmDate=" + invCheck_search_confirm_date_to
				+ "&filter_EQS_store.id=" + search_store
				+ "&filter_EQS_state=" + search_state;
		urlString = encodeURI(urlString);
		jQuery("#invCheck_gridtable").jqGrid('setGridParam', {
			url : urlString,
			page : 1
		}).trigger("reloadGrid");
	}
	function isStoreSelected(){
		var storeId = jQuery("#invCheck_search_store_id").val();
    	if(""==storeId.trim()){
    		alertMsg.error("请选择目标仓库。");
    		return;
    	}
    	return storeId;
	}
	
	function showInvCheckDetail(url){
		$.pdialog.open(url,'showInvCheckDetail','盘点单明细', {mask:true,width : 960,height : 628,resizable:false});
	}
	
	function showInvCheckIn(url){
		$.pdialog.open(url,'showInvCheckIn','盘盈入库单明细', {mask:false,width : 960,height : 628,resizable:false});
	}
	function showInvCheckOut(url){
		$.pdialog.open(url,'showInvCheckOut','盘亏出库单明细', {mask:false,width : 960,height : 628,resizable:false});
	}
</script>

<div class="page">
<div id="invCheck_container">
			<div id="invCheck_layout-center"
				class="pane ui-layout-center">
	<div id="invCheck_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<form id="invCheck_search_form" >
						<label style="float:none;white-space:nowrap" >
							<s:text name='inventoryDict.store'/>:
							<input type="hidden" id="invCheck_search_store_id" name="filter_EQS_store.id">
						 	<input type="text" id="invCheck_search_store" >
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='盘点日期'/>:
							<input type="text"	id="invCheck_search_make_date_from" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodBeginDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'invCheck_search_make_date_to\')}'})" name="filter_GED_makeDate">
							<s:text name='至'/>
						 	<input type="text"	id="invCheck_search_make_date_to" style="width:80px;height:15px" class="Wdate" value="${fns:userContextParam('periodEndDateStr')}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'invCheck_search_make_date_from\')}'})" name="filter_LED_makeDate">
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='invCheck.confirmDate'/>:
							<input type="text"	id="invCheck_search_confirm_date_from" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'invCheck_search_confirm_date_to\')}'})" name="filter_GED_confirmDate">
							<s:text name='至'/>
						 	<input type="text"	id="invCheck_search_confirm_date_to" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'invCheck_search_confirm_date_from\')}'})" name="filter_LED_confirmDate">
						</label>&nbsp;&nbsp;
						<%-- <label style="float:none;white-space:nowrap" >
							<s:text name='invCheck.person'/>:
							<input type="text"	id="invCheck_search_person" style="width:80px;height:15px" name="filter_EQS_person">
						</label>&nbsp;&nbsp; --%>
						<label style="float:none;white-space:nowrap" >
							<s:text name='invCheck.state'/>:
							<s:select id="invCheck_search_state"  list="#{'':'--','0':'新建','1':'已审核','2':'已完成'}" style="width:70px" name="filter_EQS_state"></s:select>
						</label>&nbsp;&nbsp;
						<%-- <label style="float:none;white-space:nowrap" >
							<s:text name='invCheck.remark'/>:
							<input type="text"	id="invCheck_search_remark" name="filter_LIKES_remark">
						</label>&nbsp;&nbsp; --%>
						<%-- <div class="buttonActive" style="float:right">
								<div class="buttonContent">
									<button type="button" onclick="reloadInvCheckList()"><s:text name='button.search'/></button>
								</div>
							</div> --%>
					</form>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="reloadInvCheckList()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">

<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a id="invCheck_gridtable_add_custom" class="addbutton" href="javaScript:" ><span>新单</span></a>
				</li>
				<li>
					<a id="invCheck_gridtable_del_custom" class="delbutton"  href="javaScript:delInvCheck()"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="invCheck_gridtable_check" class="checkbutton"  href="javaScript:"><span><s:text name="审核" /></span></a>
				</li>
				<li>
					<a id="invCheck_gridtable_cancelCheck" class="delallbutton"  href="javaScript:"><span><s:text name="销审" /></span></a>
				</li>
				
				<li>
					<a id="invCheck_gridtable_exportInOut" class="reportbutton" href="javaScript:exportInOutAll()"><span><s:text name="生成出入库单" /></span></a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('invCheck_gridtable','com.huge.ihos.material.model.InvCheck')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			</ul>
		</div>
		<div align="right"  style="margin-top: -20px;margin-right:5px">
			本页盈亏金额合计：<label id="invCheck_pageSum"></label>&nbsp;&nbsp;总计：<label id="invCheck_allSum" ></label>
		</div>
		<div id="invCheck_gridtable_div" layoutH="145"
			style="margin-left: 2px; margin-top: 10px; overflow: hidden"
			buttonBar="optId:checkId;width:1024;height:768">
			<input type="hidden" id="invCheck_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="invCheck_gridtable_addTile">
				<s:text name="invCheckNew.title"/>
			</label> 
			<label style="display: none"
				id="invCheck_gridtable_editTile">
				<s:text name="invCheckEdit.title"/>
			</label>
			<label style="display: none"
				id="invCheck_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="invCheck_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_invCheck_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
 <table id="invCheck_gridtable"></table>
		<div id="invCheckPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="invCheck_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="invCheck_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="invCheck_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>