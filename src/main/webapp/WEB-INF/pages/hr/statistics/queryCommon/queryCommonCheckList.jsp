
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function queryCommonCheckGridReload(){
		var urlString = "queryCommonGridList?filter_EQB_disabled=0";
		var code = jQuery("#search_queryCommonCheck_code").val();
		var name = jQuery("#search_queryCommonCheck_name").val();
		var remark = jQuery("#search_queryCommonCheck_remark").val();
	
		urlString=urlString+"?filter_LIKES_code="+code+"&filter_LIKES_name="+name;
		urlString=urlString+"&filter_LIKES_remark="+remark;
		urlString=encodeURI(urlString);
		jQuery("#queryCommonCheck_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var queryCommonCheckLayout;
			  var queryCommonCheckGridIdString="#queryCommonCheck_gridtable";
	
	jQuery(document).ready(function() { 
// 		queryCommonLayout = makeLayout({
// 			baseName: 'queryCommon', 
// 			tableIds: 'queryCommon_gridtable'
// 		}, null);
var queryCommonCheckGrid = jQuery(queryCommonCheckGridIdString);
    queryCommonCheckGrid.jqGrid({
    	url : "queryCommonGridList?filter_EQB_disabled=0",
    	editurl:"queryCommonGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="queryCommon.id" />',hidden:true,key:true},	
{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="queryCommon.code" />',hidden:false},	
{name:'name',index:'name',width : 100,align:'left',label : '<s:text name="queryCommon.name" />',hidden:false},	
// {name:'sn',index:'sn',width : 60,align:'right',label : '<s:text name="queryCommon.sn" />',hidden:false,formatter:'integer'},
// {name:'expression',index:'expression',width : 100,align:'left',label : '<s:text name="queryCommon.expression" />',hidden:false},
// {name:'disabled',index:'disabled',width : 60,align:'center',label : '<s:text name="queryCommon.disabled" />',hidden:false,formatter:'checkbox'},	
// {name:'changeDate',index:'changeDate',align:'center',label : '<s:text name="queryCommon.changeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
// {name:'changeUser.name',index:'changeUser.name',width : 100,align:'left',label : '<s:text name="queryCommon.changeUser" />',hidden:false},			
{name:'remark',index:'remark',align:'left',width : 250,label : '<s:text name="queryCommon.remark" />',hidden:false}

        ],
        jsonReader : {
			root : "queryCommons", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'sn',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="queryCommonList.title" />',
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
			 gridContainerResize('queryCommonCheck','div');
			  var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	                	id=rowIds[i];
// 	                	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewqueryCommon(\''+id+'\');">'+ret[i]["code"]+'</a>');
	                	//setCellText(this,id,'name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewRecord(\''+snapId+'\');">'+ret[i]["name"]+'</a>');
	                }
	            }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
           var dataTest = {"id":"queryCommonCheck_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
//       	   makepager("queryCommon_gridtable");
       	} 

    });
    jQuery(queryCommonCheckGrid).jqGrid('bindKeys');
    jQuery("#queryCommonCheck_gridtable_preview_custom").unbind( 'click' ).bind("click",function(){
    	 var sid = jQuery("#queryCommonCheck_gridtable").jqGrid('getGridParam','selarrrow');
         if(sid==null|| sid.length != 1){       	
 			alertMsg.error("请选择一条记录。");
 			return;
 			}
        var winTitle='<s:text name="queryCommonPreview.title"/>';
 		var url = "previewQueryCommon?popup=true&id="+sid+"&navTabId=queryCommonCheck_gridtable";
 		$.pdialog.open(url,'previewQueryCommon',winTitle, {mask:true,resizable:true,maxable:true,width : 700,height : 550});
    });
  	});
// 	function viewqueryCommon(id){
// 		var url = "editQueryCommon?oper=view&id="+id;
// 		$.pdialog.open(url,'viewQueryCommon','查看常用查询信息', {mask:true,resizable:false,maxable:false,width : 700,height : 600});
// 	}
</script>

<div class="page">
	<div class="pageHeader"  id="queryCommonCheck_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
					<form id="queryCommonCheck_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
							<s:text name='queryCommon.code'/>:
						 	<input type="text"  id="search_queryCommonCheck_code" />
						</label>
					<label style="float:none;white-space:nowrap" >
							<s:text name='queryCommon.name'/>:
						 	<input type="text"  id="search_queryCommonCheck_name" />
						</label>
    					<label style="float:none;white-space:nowrap" >
							<s:text name='queryCommon.remark'/>:
						 	<input type="text"  id="search_queryCommonCheck_remark" />
						</label>
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="queryCommonCheckGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="queryCommonCheckGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="queryCommonCheck_buttonBar">
			<ul class="toolBar">
				<li>
					<a id="queryCommonCheck_gridtable_preview_custom" class="previewbutton"  href="javaScript:"><span>查询预览</span></a>
				</li>
			</ul>
		</div>
		<div id="queryCommonCheck_gridtable_div" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300" class="grid-wrapdiv" class="unitBox" >
			<input type="hidden" id="queryCommonCheck_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="queryCommonCheck_gridtable_addTile">
				<s:text name="queryCommonNew.title"/>
			</label> 
			<label style="display: none"
				id="queryCommonCheck_gridtable_editTile">
				<s:text name="queryCommonEdit.title"/>
			</label>
			<label style="display: none"
				id="queryCommonCheck_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="queryCommonCheck_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_queryCommonCheck_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="queryCommonCheck_gridtable"></table>
		<div id="queryCommonCheckPager"></div>
</div>
	</div>
	<div class="panelBar" id="queryCommonCheck_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="queryCommonCheck_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="queryCommonCheck_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="queryCommonCheck_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>