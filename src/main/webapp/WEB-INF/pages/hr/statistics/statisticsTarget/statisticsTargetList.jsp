
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function statisticsTargetGridReload(){
		var urlString = "statisticsTargetGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#statisticsTarget_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var statisticsTargetLayout;
			  var statisticsTargetGridIdString="#statisticsTarget_gridtable";
	
	jQuery(document).ready(function() { 
// 		statisticsTargetLayout = makeLayout({
// 			baseName: 'statisticsTarget', 
// 			tableIds: 'statisticsTarget_gridtable'
// 		}, null);
var statisticsTargetGrid = jQuery(statisticsTargetGridIdString);
    statisticsTargetGrid.jqGrid({
    	url : "statisticsTargetGridList",
    	editurl:"statisticsTargetGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="statisticsTarget.id" />',hidden:true,key:true},	
{name:'sn',index:'sn',align:'center',width : 100,label : '<s:text name="statisticsTarget.sn" />',hidden:false,formatter:'integer'},	
{name:'name',index:'name',align:'left',width : 100,label : '<s:text name="statisticsTarget.name" />',hidden:false},	
{name:'operation',index:'operation',align:'center',width : 100,label : '<s:text name="statisticsTarget.operation" />',hidden:false},	
{name:'targetValue',index:'targetValue',align:'left',width : 100,label : '<s:text name="statisticsTarget.targetValue" />',hidden:false},
{name:'changeDate',index:'changeDate',align:'center',label : '<s:text name="statisticsTarget.changeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
{name:'code',index:'code',align:'center',label : '<s:text name="statisticsTarget.code" />',hidden:false},				
{name:'codeId',index:'codeId',align:'center',label : '<s:text name="statisticsTarget.codeId" />',hidden:false},				
{name:'codeName',index:'codeName',align:'center',label : '<s:text name="statisticsTarget.codeName" />',hidden:false},				
{name:'dataType',index:'dataType',align:'center',label : '<s:text name="statisticsTarget.dataType" />',hidden:false},				
{name:'disabled',index:'disabled',align:'center',width : 100,label : '<s:text name="statisticsTarget.disabled" />',hidden:false,formatter:'checkbox'},				
{name:'mainTable',index:'mainTable',align:'center',label : '<s:text name="statisticsTarget.mainTable" />',hidden:false,formatter:'checkbox'},				
{name:'remark',index:'remark',align:'center',width : 100,label : '<s:text name="statisticsTarget.remark" />',hidden:false},				
{name:'sysFiled',index:'sysFiled',align:'center',label : '<s:text name="statisticsTarget.sysFiled" />',hidden:false,formatter:'checkbox'},				
{name:'tableCode',index:'tableCode',align:'center',label : '<s:text name="statisticsTarget.tableCode" />',hidden:false},				
{name:'tableName',index:'tableName',align:'center',label : '<s:text name="statisticsTarget.tableName" />',hidden:false},
{name:'tablePK',index:'tablePK',align:'center',label : '<s:text name="statisticsTarget.tablePK" />',hidden:false}				

        ],
        jsonReader : {
			root : "statisticsTargets", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'id',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="statisticsTargetList.title" />',
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
			 gridContainerResize('statisticsTarget','div');
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"statisticsTarget_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
//       	   makepager("statisticsTarget_gridtable");
       	} 

    });
    jQuery(statisticsTargetGrid).jqGrid('bindKeys');
    
	
	
	
	//statisticsTargetLayout.resizeAll();
  	});
</script>

<div class="page">
<div id="statisticsTarget_container">
			<div id="statisticsTarget_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader" id="statisticsTarget_pageHeader">
			<div class="searchBar">
			<%--	<table class="searchContent">
					<tr>
						<td><s:text name='statisticsTarget.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='statisticsTarget.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='statisticsTarget.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='statisticsTarget.subSystemId'/>:
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
<%-- 									<button type="button" onclick="statisticsTargetGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="statisticsTarget_buttonBar">
			<ul class="toolBar">
				<li><a id="statisticsTarget_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="statisticsTarget_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="statisticsTarget_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="statisticsTarget_gridtable_div" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="statisticsTarget_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="statisticsTarget_gridtable_addTile">
				<s:text name="statisticsTargetNew.title"/>
			</label> 
			<label style="display: none"
				id="statisticsTarget_gridtable_editTile">
				<s:text name="statisticsTargetEdit.title"/>
			</label>
			<label style="display: none"
				id="statisticsTarget_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="statisticsTarget_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_statisticsTarget_gridtable" class='loading ui-state-default ui-state-active'></div>
 <table id="statisticsTarget_gridtable"></table>
		<div id="statisticsTargetPager"></div>
</div>
	</div>
	<div class="panelBar" id="statisticsTarget_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="statisticsTarget_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="statisticsTarget_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="statisticsTarget_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>