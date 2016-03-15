
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var kqCloseCountLayout;
	var kqCloseCountGridIdString="#kqCloseCount_gridtable";
	
	jQuery(document).ready(function() { 
		var kqCloseCountGrid = jQuery(kqCloseCountGridIdString);
		kqCloseCountGrid.jqGrid({
			url : "kqCloseCountGridList",
			editurl:"",
			datatype : "json",
			mtype : "GET",
	    	colModel:[
{name:'id',index:'id',align:'center',label : '',hidden:true,key:true},//<s:text name="kqCloseCount.id" />
{name:'kqTypeName',index:'kqTypeName',align:'left',width:'225px',label : '<s:text name="考勤类别" />',hidden:false,sortable:false},
{name:'checkperiod',index:'checkperiod',align:'center',width:'225px',label : '<s:text name="期间" />',hidden:false,sortable:false}
			],jsonReader : {
				root : "kqModelStatusList", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
			sortname: 'id',
			viewrecords: true,
			sortorder: 'desc',
			rowNum:'10000',
			//caption:'<s:text name="kqCloseCountList.title" />',
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
					gridContainerResize('kqCloseCount','div',0,26);
					//if(jQuery(this).getDataIDs().length>0){
					//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
					// }
					var dataTest = {"id":"kqCloseCount_gridtable"};
			 		jQuery.publish("onLoadSelect",dataTest,null);
			 		//makepager("kqCloseCount_gridtable");
			 } 
			
			});
			jQuery(kqCloseCountGrid).jqGrid('bindKeys');
			
	});
	/*结账*/
	function kqCloseCount(){
		 var sid = jQuery("#kqCloseCount_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录！");
				return;
			} else {
				jQuery.ajax({
					url: 'kqModelStatusNextPeriodCheck',
					data: {id:sid,oper:"anti"},
					type: 'post',
					dataType: 'json',
					async:true,
					error: function(data){
						alertMsg.error("系统错误！");
					},
					success: function(data){
						if(data.oper == "anti"){
							var url = "kqModelStatusClose?id="+sid+"&navTabId=kqCloseCount_gridtable&oper=anti";
							alertMsg.confirm("确认结账？", {
														okCall : function() {
															$.post(url,function(data) {
																formCallBack(data);
																if(data.statusCode == 200){
																	setTimeout(function(){
														    			//关闭除了main和当前页的其他页面
														    			var navTabLis= jQuery("ul.navTab-tab li");
														    			  var curTabid = jQuery("ul.navTab-tab li.selected").attr("tabid");
														    			  jQuery.each(navTabLis, function(){
														    			   var tabid = jQuery(this).attr("tabid");
														    			       　     if(tabid != curTabid&&tabid != "main"){
														    			       　  	  navTab.closeTab(tabid);
														    			       　     }  
														    			  　　});  
														    		},100);
																}
															});
														}
													});
						}else if(data.oper == "closed"){
							if(data.message){
								var url = "kqModelStatusClose?id="+sid+"&navTabId=kqCloseCount_gridtable&oper=closed";
								alertMsg.confirm(data.message+"确认继续结账？", {
															okCall : function() {
																$.post(url,function(data) {
																	formCallBack(data);
																	if(data.statusCode == 200){
																		setTimeout(function(){
															    			//关闭除了main和当前页的其他页面
															    			var navTabLis= jQuery("ul.navTab-tab li");
															    			  var curTabid = jQuery("ul.navTab-tab li.selected").attr("tabid");
															    			  jQuery.each(navTabLis, function(){
															    			   var tabid = jQuery(this).attr("tabid");
															    			       　     if(tabid != curTabid&&tabid != "main"){
															    			       　  	  navTab.closeTab(tabid);
															    			       　     }  
															    			  　　});  
															    		},100);
																	}
																});
															}
														});
							}else{
								var rowId = sid[0];
								var row = jQuery("#kqCloseCount_gridtable").jqGrid('getRowData',rowId);
								var url = "kqModelStatusClose?id="+sid+"&navTabId=kqCloseCount_gridtable&oper=closed&nextPeriod="+data.nextPeriod;
								alertMsg.confirm("确认结账？", {
									okCall : function() {
										$.post(url,function(data) {
											formCallBack(data);
											if(data.statusCode == 200){
												setTimeout(function(){
									    			//关闭除了main和当前页的其他页面
									    			var navTabLis= jQuery("ul.navTab-tab li");
									    			  var curTabid = jQuery("ul.navTab-tab li.selected").attr("tabid");
									    			  jQuery.each(navTabLis, function(){
									    			   var tabid = jQuery(this).attr("tabid");
									    			       　     if(tabid != curTabid&&tabid != "main"){
									    			       　  	  navTab.closeTab(tabid);
									    			       　     }  
									    			  　　});  
									    		},100);
											}
											});
										}
									});	
							}
						}
				}
			});
		}
	}
	
	/*刷新*/
	function kqRefreshCount(){
		var url = "kqRefreshCount?navTabId=kqCloseCount_gridtable";
		alertMsg.confirm("确认刷新？", {
			okCall : function() {
				$.post(url,function(data) {
					formCallBack(data);
					});
				}
			});
	}
</script>
<div class="page">
	<div id="kqCloseCount_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="kqCloseCount_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='考勤类别'/>:
				    	<s:select id="kq_type" name='kqTypeId' headerKey=""   headerValue="--"
							list="#request.kqtypes" listKey="kqTypeId" listValue="kqTypeName"
						    emptyOption="false"  maxlength="50" width="50px" >
				       </s:select>
				    </label>
					<label class="queryarea-label" >
						<s:text name='期间'/>:
						<input type="text" name="checkperiod" style="width:80px" 
						onclick = "javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}" />
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('kqCloseCount_search_form','kqCloseCount_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>

			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="kqCloseCount_buttonBar">
			<ul class="toolBar">
				<li>
					<a  class="initbutton" href="javaScript:kqRefreshCount()" ><span><s:text name="刷新"/></span></a>
				</li>
				<li>
					<a  class="closebutton" href="javaScript:kqCloseCount()" ><span><s:text name="结账"/></span></a>
				</li>
			</ul>
		</div>
		<div id="kqCloseCount_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="kqCloseCount_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="kqCloseCount_gridtable_addTile">
				<s:text name="kqCloseCountNew.title"/>
			</label> 
			<label style="display: none"
				id="kqCloseCount_gridtable_editTile">
				<s:text name="kqCloseCountEdit.title"/>
			</label>
			<div id="load_kqCloseCount_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="kqCloseCount_gridtable"></table>
			<!--<div id="kqCloseCountPager"></div>-->
		</div>
		<div class="panelBar" id="kqCloseCount_pageBar">
			<div class="pages">
				<span><s:text name="pager.perPage" /></span> <select id="kqCloseCount_gridtable_numPerPage">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="kqCloseCount_gridtable_totals"></label><s:text name="pager.items" /></span>
			</div>
			<div id="kqCloseCount_gridtable_pagination" class="pagination"
				targetType="navTab" totalCount="200" numPerPage="20"
				pageNumShown="10" currentPage="1">
			</div>
		</div>
	</div>
</div>