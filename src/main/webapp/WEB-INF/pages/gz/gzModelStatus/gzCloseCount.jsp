
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var gzCloseCountLayout;
	var gzCloseCountGridIdString="#gzCloseCount_gridtable";
	
	jQuery(document).ready(function() { 
		var gzCloseCountGrid = jQuery(gzCloseCountGridIdString);
		gzCloseCountGrid.jqGrid({
    		url : "gzCloseCountGridList",
    		editurl:"",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="gzCloseCount.id" />',hidden:true,key:true},
{name:'gzTypeName',index:'gzTypeName',align:'left',width:'80px',label : '<s:text name="工资类别" />',hidden:false,sortable:false},
{name:'checkNumber',index:'checkNumber',align:'right',width:'80px',label : '<s:text name="发放次数" />',hidden:false,formatter:'integer',sortable:false},        	
{name:'checkperiod',index:'checkperiod',align:'center',width:'80px',label : '<s:text name="期间" />',hidden:false,sortable:false},
{name:'issueType',index:'issueType',align:'center',width:'80px',label : '<s:text name="发放类别" />',hidden:false,formatter: "select", editoptions:{value:"0:月结;1:次结"},sortable:false}
],
        	jsonReader : {
				root : "gzModelStatusList", // (2)
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
        	//caption:'<s:text name="gzCloseCountList.title" />',
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
		 		/*2015.08.27 form search change*/
		 		gridContainerResize('gzCloseCount','div',0,26);
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"gzCloseCount_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	//makepager("gzCloseCount_gridtable");
       	  } 

    	});
    jQuery(gzCloseCountGrid).jqGrid('bindKeys');
    
  	});
	/*结账*/
	function gzCloseCount(){
		 var sid = jQuery("#gzCloseCount_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录！");
				return;
			} else {
				jQuery.ajax({
					url: 'gzModelStatusNextPeriodCheck',
					data: {id:sid,oper:"anti"},
					type: 'post',
					dataType: 'json',
					async:true,
					error: function(data){
						alertMsg.error("系统错误！");
					},
					success: function(data){
						if(data.oper == "anti"){
							var url = "gzModelStatusClose?id="+sid+"&navTabId=gzCloseCount_gridtable&oper=anti";
							url = encodeURI(url);
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
								var url = "gzModelStatusClose?id="+sid+"&navTabId=gzCloseCount_gridtable&oper=closed";
								url = encodeURI(url);
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
								var row = jQuery("#gzCloseCount_gridtable").jqGrid('getRowData',rowId);
				 				if(row['issueType']=='1'){
			 						var winTitle='<s:text name="选择下一次的期间"/>';
			 						var url = "gzModelStatusNextPeriod?id="+sid+"&navTabId=gzCloseCount_gridtable&oper=closed&nextPeriod="+data.nextPeriod;
			 						url = encodeURI(url);
			 						$.pdialog.open(url,'gzModelStatusNextPeriod',winTitle, {mask:true,width : 400,height : 200});
			 					}else{
									var url = "gzModelStatusClose?id="+sid+"&navTabId=gzCloseCount_gridtable&oper=closed&nextPeriod="+data.nextPeriod;
									url = encodeURI(url);
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
				}
			});
		}
	}
	/*刷新*/
	function gzRefreshCount(){
		var url = "gzRefreshCount?navTabId=gzCloseCount_gridtable";
		url = encodeURI(url);
		alertMsg.confirm("确认刷新？", {
			okCall : function() {
				$.post(url,function(data) {
					formCallBack(data);
					});
				}
			});
	}
	//删除
	function gzDelCount(){
		 var sid = jQuery("#gzCloseCount_gridtable").jqGrid('getGridParam','selarrrow');
	     if(sid == null){       	
			alertMsg.error("请选择记录！");
			return;
		 }
	     jQuery.ajax({
				url: 'checkGzDelCount',
				data: {id:sid},
				type: 'post',
				dataType: 'json',
				async:true,
				error: function(data){
					alertMsg.error("系统错误！");
				},
				success: function(data){
					if(data.message){
						alertMsg.error(data.message);
						return;
					}
					alertMsg.confirm("确认删除？",{
			    		okCall:function(){
			    			var url = "modelStatusGridEdit?id="+sid+"&popup=true&navTabId=gzCloseCount_gridtable&oper=del";  
		    		    	$.post(url,{},function(data){
		    		    		formCallBack(data);
		    		    	});
			    		}
					});
				}
			});
	}
</script>

<div class="page">
	<div id="gzCloseCount_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="gzCloseCount_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='工资类别'/>:
				    	<s:select id="gz_type" name='gzTypeId' headerKey=""   headerValue="--"
							list="#request.gztypes" listKey="gzTypeId" listValue="gzTypeName"
						    emptyOption="false"  maxlength="50" width="50px" >
				       </s:select>
				    </label>
				    <label class="queryarea-label">
						<s:text name='发放次数'/>:
						<select name="checkNumber">
						<option value="">--</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
						<option value="13">13</option>
						</select>
					</label>
					<label class="queryarea-label">
						<s:text name='期间'/>:
						<input type="text" name="checkperiod" style="width:80px" 
						onclick = "javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}" />
					</label>
					<label class="queryarea-label">
					     <s:text name='发放类别'/>:
					   <s:select name='issueType' headerKey=""   style="font-size:12px"
							list="#{'':'--','0':'月结','1':'次结'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('gzCloseCount_search_form','gzCloseCount_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="propertyFilterSearch('gzCloseCount_search_form','gzCloseCount_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="gzCloseCount_buttonBar">
			<ul class="toolBar">
				<li>
					<a  class="initbutton" href="javaScript:gzRefreshCount()" ><span><s:text name="刷新"/></span></a>
				</li>
				<li>
					<a  class="closebutton" href="javaScript:gzCloseCount()" ><span><s:text name="结账"/></span></a>
				</li>
				<li>
					<a  class="delbutton"  href="javaScript:gzDelCount()"><span><s:text name="button.delete" /></span></a>
				</li>
			</ul>
		</div>
		<div id="gzCloseCount_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="gzCloseCount_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="gzCloseCount_gridtable_addTile">
				<s:text name="gzCloseCountNew.title"/>
			</label> 
			<label style="display: none"
				id="gzCloseCount_gridtable_editTile">
				<s:text name="gzCloseCountEdit.title"/>
			</label>
			<div id="load_gzCloseCount_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="gzCloseCount_gridtable"></table>
			<!--<div id="gzCloseCountPager"></div>-->
		</div>
	</div>
<!-- 	<div class="panelBar"> -->
<!-- 		<div class="pages"> -->
<%-- 			<span><s:text name="pager.perPage" /></span> <select id="gzCloseCount_gridtable_numPerPage"> --%>
<!-- 				<option value="20">20</option> -->
<!-- 				<option value="50">50</option> -->
<!-- 				<option value="100">100</option> -->
<!-- 				<option value="200">200</option> -->
<%-- 			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="gzCloseCount_gridtable_totals"></label><s:text name="pager.items" /></span> --%>
<!-- 		</div> -->
<!-- 		<div id="gzCloseCount_gridtable_pagination" class="pagination" -->
<!-- 			targetType="navTab" totalCount="200" numPerPage="20" -->
<!-- 			pageNumShown="10" currentPage="1"> -->
<!-- 		</div> -->
<!-- 	</div> -->
</div>