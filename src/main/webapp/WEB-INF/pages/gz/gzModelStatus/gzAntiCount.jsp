
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var gzAntiCountLayout;
	var gzAntiCountGridIdString="#gzAntiCount_gridtable";
	
	jQuery(document).ready(function() { 
		var gzAntiCountGrid = jQuery(gzAntiCountGridIdString);
		gzAntiCountGrid.jqGrid({
			url : "gzAntiCountGridList",
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
        	rowNum:'10000',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="gzAntiCountList.title" />',
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
		 		gridContainerResize('gzAntiCount','div',0,26);
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"gzAntiCount_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	//makepager("gzAntiCount_gridtable");
       	  } 

    	});
    jQuery(gzAntiCountGrid).jqGrid('bindKeys');
    
  	});
	function gzAntiCount(){
		 var sid = jQuery("#gzAntiCount_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
			} else {
				var url = "gzModelStatusAnti?id="+sid+"&navTabId=gzAntiCount_gridtable";
				url = encodeURI(url);
				alertMsg.confirm("确认反结账？", {
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
</script>

<div class="page">
	<div id="gzAntiCount_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="gzAntiCount_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='工资类别'/>:
				    	<s:select id="gz_type" name='gzTypeId' headerKey=""   headerValue="--"
							list="#request.gztypes" listKey="gzTypeId" listValue="gzTypeName"
						    emptyOption="false"  maxlength="50" width="50px" >
				       </s:select>
				    </label>
				    <label class="queryarea-label">
						<s:text name='发放次数'/>:
						<select type="text" name="checkNumber">
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
							<button type="button" onclick="propertyFilterSearch('gzAntiCount_search_form','gzAntiCount_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="propertyFilterSearch('gzAntiCount_search_form','gzAntiCount_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="gzAntiCount_buttonBar">
			<ul class="toolBar">
				<li>
					<a  class="openbutton" href="javaScript:gzAntiCount()" ><span><s:text name="反结账"/></span></a>
				</li>
			</ul>
		</div>
		<div id="gzAntiCount_gridtable_div" layoutH="118" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="gzAntiCount_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="gzAntiCount_gridtable_addTile">
				<s:text name="gzAntiCountNew.title"/>
			</label> 
			<label style="display: none"
				id="gzAntiCount_gridtable_editTile">
				<s:text name="gzAntiCountEdit.title"/>
			</label>
			<div id="load_gzAntiCount_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="gzAntiCount_gridtable"></table>
			<!--<div id="gzAntiCountPager"></div>-->
		</div>
	</div>
<!-- 	<div class="panelBar"> -->
<!-- 		<div class="pages"> -->
<%-- 			<span><s:text name="pager.perPage" /></span> <select id="gzAntiCount_gridtable_numPerPage"> --%>
<!-- 				<option value="20">20</option> -->
<!-- 				<option value="50">50</option> -->
<!-- 				<option value="100">100</option> -->
<!-- 				<option value="200">200</option> -->
<%-- 			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="gzAntiCount_gridtable_totals"></label><s:text name="pager.items" /></span> --%>
<!-- 		</div> -->
<!-- 		<div id="gzAntiCount_gridtable_pagination" class="pagination" -->
<!-- 			targetType="navTab" totalCount="200" numPerPage="20" -->
<!-- 			pageNumShown="10" currentPage="1"> -->
<!-- 		</div> -->
<!-- 	</div> -->
</div>