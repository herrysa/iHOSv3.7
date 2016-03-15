
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
function stringFormatter (cellvalue, options, rowObject){
	try
		{cellvalue= cellvalue.replace(/\r\n/g, "").replace(/\n/g, "");
		}
		catch(err)
		{
			return cellvalue;
		}
	return cellvalue;
}
	function byLawGridReload(){
		var urlString = "byLawGridList";
		var byLaw_title_search = jQuery("#byLaw_title_search").val();
		var byLaw_createTime_search = jQuery("#byLaw_createTime_search").val();
		var byLaw_creator_search = jQuery("#byLaw_creator_search").val();
		var byLaw_department_search = jQuery("#byLaw_department_search").val();
		
		var startTime = "";
		var endTime = "";
		if(byLaw_createTime_search!=null&&byLaw_createTime_search!=""){
			startTime = byLaw_createTime_search+" 00:00:00";
			endTime = byLaw_createTime_search+" 23:59:59";
		}
		urlString=urlString+"?filter_LIKES_title="+byLaw_title_search+"&filter_GET_createTime="+startTime+"&filter_LET_createTime="+endTime+"&filter_LIKES_creator="+byLaw_creator_search+"&filter_LIKES_department="+byLaw_department_search;
		urlString=encodeURI(urlString);
		jQuery("#byLaw_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var byLawLayout;
			  var byLawGridIdString="#byLaw_gridtable";
	
	jQuery(document).ready(function() { 
		/* byLawLayout = makeLayout({
			baseName: 'byLaw', 
			tableIds: 'byLaw_gridtable'
		}, null);  */
var byLawGrid = jQuery(byLawGridIdString);
    byLawGrid.jqGrid({
    	url : "byLawGridList",
    	editurl:"byLawGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'byLawId',index:'byLawId',align:'left',label : '<s:text name="byLaw.byLawId" />',hidden:true,key:true,formatter:'integer'},				
{name:'title',index:'title',align:'left',label : '<s:text name="byLaw.title" />',hidden:false,width:250},
{name:'createTime',index:'createTime',align:'center',label : '<s:text name="byLaw.createTime" />',hidden:false,formatter:'date',formatoptions:{srcformat: 'Y-m-d H:i:s',newformat:"Y-m-d H:i:s"}},				
{name:'creator',index:'creator',align:'left',label : '<s:text name="byLaw.creator" />',hidden:false},				
{name:'department',index:'department',align:'left',label : '<s:text name="byLaw.department" />',hidden:false}				

        ],
        jsonReader : {
			root : "byLaws", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'byLawId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="byLawList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:true,
        onSelectRow: function(rowid) {
       
       	},
		 gridComplete:function(){
			 gridContainerResize('byLaw','div');
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"byLaw_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   //makepager("byLaw_gridtable");
       	} 

    });
    jQuery(byLawGrid).jqGrid('bindKeys');
    
	
	
	
	//byLawLayout.resizeAll();
  	});
	function preViewByLaw(){
		var sid = jQuery("#byLaw_gridtable").jqGrid('getGridParam','selarrrow');
	    if(sid==null || sid.length ==0){
			alertMsg.error("");
			return;
		}
	    if(sid.length>1){
			alertMsg.error("");
			return;
		}else{
			var url = "showByLaw?byLawId="+ sid;
			var winTitle="规章制度预览";
			//alert(url);
			url = encodeURI(url);
			$.pdialog.open(url, 'preViewByLaw', winTitle, {mask:false,width:880,height:600});　
		}
	}
</script>

<div class="page" id="byLaw_page">
	<div id="byLaw_pageHeader" class="pageHeader">
			<div class="searchBar">
			<div class="searchContent">
				<form id="byLaw_search_form"  style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
					<s:text name='byLaw.title'/>:
						 	<input type="text"	id="byLaw_title_search" >
					</label>
					<label style="float:none;white-space:nowrap" >
					<s:text name='byLaw.createTime'/>:
						 	<input type="text"	onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"	id="byLaw_createTime_search" >
					</label>
					<label style="float:none;white-space:nowrap" >
					<s:text name='byLaw.creator'/>:
						 	<input type="text"	id="byLaw_creator_search" >
					</label>
					<label style="float:none;white-space:nowrap" >
					<s:text name='byLaw.department'/>:
						 	<s:select id="byLaw_department_search" list="departList" listKey="name" listValue="name" headerKey="" headerValue="--"/>
					</label>
				<div class="subBar" style="float:right">
					<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="byLawGridReload()"><s:text name='button.search'/></button>
								</div>
							</div>
				</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
<div class="panelBar" id="byLaw_buttonBar">
			<ul class="toolBar" id="byLaw_toolbuttonbar">
				<li><a id="byLaw_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="byLaw_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="byLaw_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<li><a  class="previewbutton"  href="javaScript:preViewByLaw()"
					><span><s:text name="button.testPreview" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="byLaw_gridtable_div" 
			class="grid-wrapdiv"
			buttonBar="optId:byLawId;width:880;height:600">
			<input type="hidden" id="byLaw_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="byLaw_gridtable_addTile">
				<s:text name="byLawNew.title"/>
			</label> 
			<label style="display: none"
				id="byLaw_gridtable_editTile">
				<s:text name="byLawEdit.title"/>
			</label>
			<label style="display: none"
				id="byLaw_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="byLaw_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_byLaw_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="byLaw_gridtable"></table>
		<div id="byLawPager"></div>
</div>
	</div>
	<div class="panelBar" id="byLaw_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="byLaw_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="byLaw_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="byLaw_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>