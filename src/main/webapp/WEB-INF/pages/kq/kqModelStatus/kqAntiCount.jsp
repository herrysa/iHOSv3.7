
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var kqAntiCountLayout;
	var kqAntiCountGridIdString="#kqAntiCount_gridtable";
	
	jQuery(document).ready(function() { 
		var kqAntiCountGrid = jQuery(kqAntiCountGridIdString);
		kqAntiCountGrid.jqGrid({
			url : "kqAntiCountGridList",
			editurl:"",
			datatype : "json",
			mtype : "GET",
			colModel:[
{name:'id',index:'id',align:'center',label : '',hidden:true,key:true},//<s:text name="kqAntiCount.id" />
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
			//caption:'<s:text name="kqAntiCountList.title" />',
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
					gridContainerResize('kqAntiCount','div',0,26);
					//if(jQuery(this).getDataIDs().length>0){
					//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
					// }
					var dataTest = {"id":"kqAntiCount_gridtable"};
			 		jQuery.publish("onLoadSelect",dataTest,null);
			 		//makepager("kqAntiCount_gridtable");
			 } 
			
			});
			jQuery(kqAntiCountGrid).jqGrid('bindKeys');
			
	});
	function kqAntiCount(){
		 var sid = jQuery("#kqAntiCount_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
			} else {
				var url = "kqModelStatusAnti?id="+sid+"&navTabId=kqAntiCount_gridtable";
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
	<div id="kqAntiCount_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="kqAntiCount_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='考勤类别'/>:
				    	<s:select id="kq_type" name='kqTypeId' headerKey=""   headerValue="--"
							list="#request.kqtypes" listKey="kqTypeId" listValue="kqTypeName"
						    emptyOption="false"  maxlength="50" width="50px" >
				       </s:select>
				    </label>
					<label class="queryarea-label">
						<s:text name='期间'/>:
						<input type="text" name="checkperiod" style="width:80px" 
						onclick = "javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}" />
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('kqAntiCount_search_form','kqAntiCount_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="kqAntiCount_buttonBar">
			<ul class="toolBar">
				<li>
					<a  class="openbutton" href="javaScript:kqAntiCount()" ><span><s:text name="反结账"/></span></a>
				</li>
			</ul>
		</div>
		<div id="kqAntiCount_gridtable_div" layoutH="118" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="kqAntiCount_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="kqAntiCount_gridtable_addTile">
				<s:text name="kqAntiCountNew.title"/>
			</label> 
			<label style="display: none"
				id="kqAntiCount_gridtable_editTile">
				<s:text name="kqAntiCountEdit.title"/>
			</label>
			<div id="load_kqAntiCount_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="kqAntiCount_gridtable"></table>
			<!--<div id="kqAntiCountPager"></div>-->
		</div>
		<div class="panelBar" id="kqAntiCount_pageBar">
			<div class="pages">
				<span><s:text name="pager.perPage" /></span> <select id="kqAntiCount_gridtable_numPerPage">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="kqAntiCount_gridtable_totals"></label><s:text name="pager.items" /></span>
			</div>
			<div id="kqAntiCount_gridtable_pagination" class="pagination"
				targetType="navTab" totalCount="200" numPerPage="20"
				pageNumShown="10" currentPage="1">
			</div>
		</div>
	</div>
</div>