
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function statisticsItemCheckGridReload(){
		var urlString = "statisticsItemGridList";
		var code = jQuery("#search_statisticsItemCheck_code").val();
		var name = jQuery("#search_statisticsItemCheck_name").val();
		var remark = jQuery("#search_statisticsItemCheck_remark").val();
	
		urlString=urlString+"?filter_LIKES_code="+code+"&filter_LIKES_name="+name;
		urlString=urlString	+"&filter_LIKES_remark="+remark;
		urlString=urlString +"&statisticsCode="+"hr_person"+"&filter_EQB_disabled=0";
		urlString=encodeURI(urlString);
		jQuery("#statisticsItemCheck_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var statisticsItemCheckLayout;
			  var statisticsItemCheckGridIdString="#statisticsItemCheck_gridtable";
	
	jQuery(document).ready(function() { 
var statisticsItemCheckGrid = jQuery(statisticsItemCheckGridIdString);
    statisticsItemCheckGrid.jqGrid({
    	url : "statisticsItemGridList?statisticsCode="+"hr_person"+"&filter_EQB_disabled=0",
    	editurl:"statisticsItemGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="statisticsItem.id" />',hidden:true,key:true},
{name:'code',index:'code',align:'left',width : 100,label : '<s:text name="statisticsItem.code" />',hidden:false,highsearch:true},	
{name:'name',index:'name',align:'left',width : 100,label : '<s:text name="statisticsItem.name" />',hidden:false,highsearch:true},	
{name:'parentType.name',index:'parentType.name',align:'left',width : 100,label : '<s:text name="statisticsItem.parentType" />',hidden:false,highsearch:true},
// {name:'mccKeyId',index:'mccKeyId',align:'left',width : 100,label : '<s:text name="statisticsItem.mccKeyId" />',hidden:false,highsearch:true},	
// {name:'statisticsBdInfo.bdInfo.tableName',index:'statisticsBdInfo.bdInfo.tableName',align:'left',width : 150,label : '<s:text name="statisticsItem.statisticsBdInfo" />',hidden:false,highsearch:true},
// {name:'statisticsFieldInfo',index:'statisticsFieldInfo',align:'left',width : 100,label : '<s:text name="statisticsItem.statisticsFieldInfo" />',hidden:false,highsearch:true},
// {name:'dynamicStatistics',index:'dynamicStatistics',align:'center',width : 70,label : '<s:text name="statisticsItem.dynamicStatistics" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'remark',index:'remark',align:'left',width : 250,label : '<s:text name="statisticsItem.remark" />',hidden:false,highsearch:true}

        ],
        jsonReader : {
			root : "statisticsItems", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'id',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="statisticsItemList.title" />',
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
			 gridContainerResize('statisticsItemCheck','div');
//            if(jQuery(this).getDataIDs().length>0){
//               jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
//             }
			 var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	                	id=rowIds[i];
// 	                	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewstatisticsItem(\''+id+'\');">'+ret[i]["code"]+'</a>');
	                	//setCellText(this,id,'name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewRecord(\''+snapId+'\');">'+ret[i]["name"]+'</a>');
	                }
	            }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
	            
           var dataTest = {"id":"statisticsItemCheck_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
//       	   makepager("statisticsItem_gridtable");
       	} 

    });
    jQuery(statisticsItemCheckGrid).jqGrid('bindKeys');
	//statisticsItemLayout.resizeAll();
    jQuery("#statisticsItemCheck_gridtable_preview_custom").unbind( 'click' ).bind("click",function(){
   	 var sid = jQuery("#statisticsItemCheck_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
			}
       var winTitle='<s:text name="常用统计预览"/>';
		var url = "previewStatisticsItem?popup=true&id="+sid+"&statisticsCode="+"hr_person";
		$.pdialog.open(url,'previewStatisticsItem',winTitle, {mask:true,resizable:true,maxable:true,width : 700,height : 550});
   });
  	});
	function viewstatisticsItem(id){
		var url = "editStatisticsItem?oper=view&id="+id;
		url = url + "&statisticsCode="+"hr_person";
		$.pdialog.open(url,'viewQueryCommon','查看统计项信息', {mask:true,resizable:false,maxable:true,width : 700,height : 400});
	}
</script>

<div class="page">
	<div class="pageHeader" id="statisticsItemCheck_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
			<form id="statisticsItemCheck_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='statisticsItem.code'/>:
						<input type="text"  id="search_statisticsItemCheck_code" />
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='statisticsItem.name'/>:
						 <input type="text"  id="search_statisticsItemCheck_name" />
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='statisticsItem.remark'/>:
						 <input type="text"  id="search_statisticsItemCheck_remark" />
					</label>
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="statisticsItemCheckGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="statisticsItemCheckGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="statisticsItemCheck_buttonBar">
			<ul class="toolBar">
				<li>
					<a id="statisticsItemCheck_gridtable_preview_custom" class="previewbutton"  href="javaScript:"><span>统计预览</span></a>
				</li>
			</ul>
		</div>
		<div id="statisticsItemCheck_gridtable_div" class="grid-wrapdiv" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:900;height:550">
			<input type="hidden" id="statisticsItemCheck_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="statisticsItemCheck_gridtable_addTile">
				<s:text name="statisticsItemNew.title"/>
			</label> 
			<label style="display: none"
				id="statisticsItemCheck_gridtable_editTile">
				<s:text name="statisticsItemEdit.title"/>
			</label>
			<label style="display: none"
				id="statisticsItemCheck_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="statisticsItemCheck_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_statisticsItemCheck_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="statisticsItemCheck_gridtable"></table>
		<div id="statisticsItemCheckPager"></div>
	</div>
	</div>
	<div class="panelBar" id="statisticsItemCheck_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="statisticsItemCheck_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="statisticsItemCheck_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="statisticsItemCheck_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	</div>
