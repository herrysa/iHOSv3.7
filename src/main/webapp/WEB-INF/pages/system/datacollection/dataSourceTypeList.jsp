<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title><fmt:message key="dataSourceTypeList.title"/></title>
    <meta name="heading" content="<fmt:message key='dataSourceTypeList.heading'/>"/>
    <meta name="menu" content="DataSourceTypeMenu"/>
    <script type="text/javascript">
    
    function addDataSourceTypeRecord(){
		var url = "editDataSourceType?popup=true";
		var winTitle='<fmt:message key="dataSourceTypeNew.title"/>';
		//popUpWindow(url, winTitle, "width=500");
		$.pdialog.open(url, 'addDataCollectionTask', winTitle, {mask:false,width:350,height:250});　
	}
	function editDataSourceTypeRecord(){
        var sid = jQuery("#dataSourceType_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null || sid.length ==0){
			//alert("<fmt:message key='list.selectNone'/>");
			jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectNone'/>");
			jQuery('#mybuttondialog').dialog('open');
			return;
			}
        if(sid.length>1){
			  //alert("<fmt:message key='list.selectMore'/>");
		  jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectMore'/>");
		  jQuery('#mybuttondialog').dialog('open');
			return;
		  }else{
         jQuery("#gridinfo").html('<p>Loading..... ID : '+sid+'</p>');
			var url = "editDataSourceType?popup=true&dataSourceTypeId=" + sid;
			var winTitle='<fmt:message key="dataSourceTypeNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
	}
	    function setDataSourceTypeModel(postdata, formid){
	  	  //alert("beforesubmit");
	  	   postdata['model.dataSourceTypeId'] = postdata['dataSourceTypeId'];
	  	   postdata['model.dataSourceTypeName'] = postdata['dataSourceTypeName'];
	  	   postdata['model.fileType'] = postdata['fileType'];
	  	   postdata['model.helperClassName'] = postdata['helperClassName'];
	  	   postdata['model.isNeedFile'] = postdata['isNeedFile'];
	      return [true,""];
	    }
		
		function checkGridOperation(response,postdata){
		    var gridresponse = gridresponse || {};
		    gridresponse = jQuery.parseJSON(response.responseText);
		    var msg = gridresponse["gridOperationMessage"];
		   // alert(msg);
		   jQuery("#gridinfo").html(msg);
	        return [true,""];   
		}
    
		datePick = function(elem){
		        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
		        jQuery('#ui-datepicker-div').css("z-index", 2000);
		};
		/* var dataSourceTypeLayout;
		jQuery(document).ready(function() { 
			jQuery("#bsdata").click(function(){ jQuery("#dataSourceType_gridtable").jqGrid('searchGrid', {multipleSearch:true,sopt:['cn','bw','eq','ne','lt','gt','ew']} ); });
			   
			dataSourceTypeLayout = makeLayout({
				baseName: 'dataSourceType', 
				tableIds: 'dataSourceType_gridtable'
			}, null);
			dataSourceTypeLayout.resizeAll();
    	}); */
	</script>
	
</head>


<div class="page">
	<div class="pageContent">
	<%-- <div id="dataSourceType_container">
			<div id="dataSourceType_layout-center"
				class="pane ui-layout-center">
 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/> --%>

<s:url id="editurl" action="dataSourceTypeGridEdit"/> 
<s:url id="remoteurl" action="dataSourceTypeGridList"/> 

<div class="panelBar">
			<ul class="toolBar">
				<li><a id="dataSourceType_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="dataSourceType_gridtable_del" class="delbutton"  href="javaScript:"><span>删除</span>
				</a>
				</li>
				<li><a id="dataSourceType_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><fmt:message key="button.edit" />
					</span>
				</a>
				</li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul>
		</div>
		<div id="dataSourceType_gridtable_div" layoutH="57"
			class="grid-wrapdiv"
			buttonBar="base_URL:editDataSourceType;optId:dataSourceTypeId;width:500;height:250">
			<input type="hidden" id="dataSourceType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="dataSourceType_gridtable_addTile">
				<fmt:message key="dataSourceTypeNew.title" />
			</label> 
			<label style="display: none"
				id="dataSourceType_gridtable_editTile">
				<fmt:message key="dataSourceTypeEdit.title" />
			</label>
			<label style="display: none"
				id="dataSourceType_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="dataSourceType_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
			<div id="load_dataSourceType_gridtable" class='loading ui-state-default ui-state-active'></div>
 		<sjg:grid 
    	id="dataSourceType_gridtable" 
    	dataType="json" 
    	gridModel="dataSourceTypes"

    	href="%{remoteurl}"    	
    	editurl="%{editurl}"
   	
    	rowList="10,15,20,30,40,50,60,70,80,90,100"
    	rowNum="20"
    	rownumbers="true"
    	
    	pager="false" 
    	page="1"
    	pagerButtons="false"
    	pagerInput="false"
    	pagerPosition="false"
    	
		navigator="false"
		navigatorAdd="false"
        navigatorEdit="false"
		navigatorDelete="false"
		navigatorDeleteOptions="{reloadAfterSubmit:true,afterSubmit:checkGridOperation,left:screen.availWidth/4, top:screen.availHeight/4}"   
        navigatorSearch="false"
        navigatorSearchOptions="{multipleSearch:true,  showQuery: true,left:screen.availWidth/4, top:screen.availHeight/4}"
 		navigatorRefresh="false"

    	multiselect="true"
  		multiboxonly="true"
  		
  		resizable="true"
  		
  		draggable="true"
  		shrinkToFit="true"
  		autowidth="true"
		onCompleteTopics="onLoadSelect"

		cssClass="border:0;padding:0px"
    >
    <sjg:gridColumn 
    	    name="dataSourceTypeId" 
    	    search="false" 
    	    index="dataSourceTypeId" 
    	    title="%{getText('dataSourceType.dataSourceTypeId')}" 
    	    hidden="true"
    	    formatter="integer" 
    	    sortable="false"
    	    key="true"
    	    />
    
   <sjg:gridColumn 
 	    name="dataSourceTypeName" 
 	    index="dataSourceTypeName" 
 	    title="%{getText('dataSourceType.dataSourceTypeName')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
 	    />
   <sjg:gridColumn 
 	    name="fileType" 
 	    index="fileType" 
 	    title="%{getText('dataSourceType.fileType')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
 	    />
   <sjg:gridColumn 
 	    name="helperClassName" 
 	    index="helperClassName" 
 	    title="%{getText('dataSourceType.helperClassName')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
 	    />
   <sjg:gridColumn 
 	    name="urlTemplate" 
 	    index="urlTemplate" 
 	    title="%{getText('dataSourceType.urlTemplate')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
 	    /> 	    
    <sjg:gridColumn 
    	name="isNeedFile" 
	    index="isNeedFile" 
	    title="%{getText('dataSourceType.isNeedFile')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      width="90"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
/> 
  </sjg:grid>
  </div>
	
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="dataSourceType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共<label id="dataSourceType_gridtable_totals"></label>条</span>
		</div>
		
		<div id="dataSourceType_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
<!-- </div>
</div> -->