
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function userByLawGridReload(){
		var urlString = "byLawGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#userByLaw_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var userByLawLayout;
			  var userByLawGridIdString="#userByLaw_gridtable";
	
	jQuery(document).ready(function() { 
		userByLawLayout = makeLayout({
			baseName: 'userByLaw', 
			tableIds: 'userByLaw_gridtable'
		}, null);
var userByLawGrid = jQuery(userByLawGridIdString);
    userByLawGrid.jqGrid({
    	url : "byLawGridList",
    	editurl:"byLawGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'byLawId',index:'byLawId',align:'left',label : '<s:text name="byLaw.byLawId" />',hidden:true,key:true,formatter:'integer'},				
{name:'title',index:'title',align:'left',label : '<s:text name="byLaw.title" />',hidden:false,width:500},				
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
        multiselect: false,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:false,
        onSelectRow: function(rowid) {
       
       	},
		 gridComplete:function(){
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           reFormatColumnData(this);
           var dataTest = {"id":"userByLaw_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("userByLaw_gridtable");
       	} 

    });
    jQuery(userByLawGrid).jqGrid('bindKeys');
    
	
	
	
	//byLawLayout.resizeAll();
  	});
	function reFormatColumnData(grid){
		 var rowNum = jQuery(grid).getDataIDs().length;
		 var ret = jQuery(grid).jqGrid('getRowData');
	     if(rowNum > 0){
	    	 var rowIds = jQuery(grid).getDataIDs();
	    	 var i=0
	    	 for (i=0;i<rowNum;i++){
	    		 var id = rowIds[i];
	    		 var data = ret[i]["title"];
	    		 setCellText(grid,id,'title','<a style="color:blue;text-decoration:underline;cursor:pointer;"  onclick="showUserByLaw(\'showByLaw?byLawId='+id+'\',\''+data+'\')" target="dialog" width="880" height="600">'+data+"</a>")
	    	 }
	    }
	}
	function setCellText(grid,rowid,colName,cellTxt){
		 var  tr,cm = grid.p.colModel, iCol = 0, cCol = cm.length;
       for (; iCol<cCol; iCol++) {
           if (cm[iCol].name === colName) {
               tr = grid.rows.namedItem(rowid);
               if (tr) {
                  jQuery(tr.cells[iCol]).html(cellTxt);
               }
               break;
           }
       }
		
	}
	function showUserByLaw(url,title){
		$.pdialog.open(url, 'preViewUserByLaw', "规章制度", {mask:false,width:880,height:600});　
	}
</script>

<div class="page">
<div id="userByLaw_container">
			<div id="userByLaw_layout-center"
				class="pane ui-layout-center">
	<%-- <div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td><s:text name='byLaw.paramKey'/>:
							<input type="text"	id="paramKeyTxt" >
						</td>
						<td><s:text name='byLaw.paramValue'/>:
						 	<input type="text"	id="paramValueTxt" >
						 </td>
						<td><s:text name='byLaw.description'/>:
						 	<input type="text"		id="descriptionTxt" >
						 </td>
						 <td><s:text name='byLaw.subSystemId'/>:
						 	<s:select name="subSystemC" id="subSystemTxt"  maxlength="20"
					list="subSystems"  listKey="menuName"
					listValue="menuName" emptyOption="true" theme="simple"></s:select>
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="userByLawGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div> --%>
	<div class="pageContent">





<%-- <div class="panelBar">
			<ul class="toolBar">
				<li><a id="userByLaw_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="userByLaw_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="userByLaw_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div> --%>
		<div id="userByLaw_gridtable_div" layoutH="32"
			class="grid-wrapdiv"
			buttonBar="optId:paramId;width:500;height:300">
			<input type="hidden" id="userByLaw_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="userByLaw_gridtable_addTile">
				<s:text name="userByLawNew.title"/>
			</label> 
			<label style="display: none"
				id="userByLaw_gridtable_editTile">
				<s:text name="userByLawEdit.title"/>
			</label>
			<label style="display: none"
				id="userByLaw_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="userByLaw_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_userByLaw_gridtable" class='loading ui-state-default ui-state-active'></div>
 <table id="userByLaw_gridtable"></table>
		<div id="userByLawPager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="userByLaw_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="userByLaw_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="userByLaw_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>