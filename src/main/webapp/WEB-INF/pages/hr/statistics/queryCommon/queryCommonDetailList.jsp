
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function queryCommonDetailGridReload(){
		var urlString = "queryCommonDetailGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#queryCommonDetail_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var queryCommonDetailLayout;
			  var queryCommonDetailGridIdString="#queryCommonDetail_gridtable";
	
	jQuery(document).ready(function() { 
		queryCommonDetailLayout = makeLayout({
			baseName: 'queryCommonDetail', 
			tableIds: 'queryCommonDetail_gridtable'
		}, null);
var queryCommonDetailGrid = jQuery(queryCommonDetailGridIdString);
    queryCommonDetailGrid.jqGrid({
    	url : "queryCommonDetailGridList",
    	editurl:"queryCommonDetailGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="queryCommonDetail.id" />',hidden:false,key:true},				
{name:'changeDate',index:'changeDate',align:'center',label : '<s:text name="queryCommonDetail.changeDate" />',hidden:false},				
{name:'disabled',index:'disabled',align:'center',label : '<s:text name="queryCommonDetail.disabled" />',hidden:false,formatter:'checkbox'},				
{name:'name',index:'name',align:'center',label : '<s:text name="queryCommonDetail.name" />',hidden:false},				
{name:'operation',index:'operation',align:'center',label : '<s:text name="queryCommonDetail.operation" />',hidden:false},				
{name:'remark',index:'remark',align:'center',label : '<s:text name="queryCommonDetail.remark" />',hidden:false},				
{name:'sn',index:'sn',align:'center',label : '<s:text name="queryCommonDetail.sn" />',hidden:false,formatter:'integer'},				
{name:'sysFiled',index:'sysFiled',align:'center',label : '<s:text name="queryCommonDetail.sysFiled" />',hidden:false,formatter:'checkbox'},				
{name:'tableName',index:'tableName',align:'center',label : '<s:text name="queryCommonDetail.tableName" />',hidden:false},				
{name:'targetValue',index:'targetValue',align:'center',label : '<s:text name="queryCommonDetail.targetValue" />',hidden:false}				

        ],
        jsonReader : {
			root : "queryCommonDetails", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'id',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="queryCommonDetailList.title" />',
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
			 gridContainerResize('queryCommonDetail','div');
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"queryCommonDetail_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("queryCommonDetail_gridtable");
       	} 

    });
    jQuery(queryCommonDetailGrid).jqGrid('bindKeys');
    
	
	
	
	//queryCommonDetailLayout.resizeAll();
  	});
</script>

<div class="page">
<div id="queryCommonDetail_container">
			<div id="queryCommonDetail_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader" id="queryCommonDetail_pageHeader">
			<div class="searchBar">
			<%--	<table class="searchContent">
					<tr>
						<td><s:text name='queryCommonDetail.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='queryCommonDetail.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='queryCommonDetail.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='queryCommonDetail.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>--%>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="queryCommonDetailGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="queryCommonDetail_buttonBar">
			<ul class="toolBar">
				<li><a id="queryCommonDetail_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="queryCommonDetail_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="queryCommonDetail_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="queryCommonDetail_gridtable_div" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="queryCommonDetail_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="queryCommonDetail_gridtable_addTile">
				<s:text name="queryCommonDetailNew.title"/>
			</label> 
			<label style="display: none"
				id="queryCommonDetail_gridtable_editTile">
				<s:text name="queryCommonDetailEdit.title"/>
			</label>
			<label style="display: none"
				id="queryCommonDetail_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="queryCommonDetail_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_queryCommonDetail_gridtable" class='loading ui-state-default ui-state-active'></div>
 <table id="queryCommonDetail_gridtable"></table>
		<div id="queryCommonDetailPager"></div>
</div>
	</div>
	<div class="panelBar" id="queryCommonDetail_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="queryCommonDetail_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="queryCommonDetail_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="queryCommonDetail_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>