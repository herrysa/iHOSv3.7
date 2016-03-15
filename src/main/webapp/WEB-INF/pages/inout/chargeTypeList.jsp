<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="chargeTypeList.title"/></title>
    <meta name="heading" content="<fmt:message key='chargeTypeList.heading'/>"/>
    <meta name="menu" content="ChargeTypeMenu"/>
    
    <script type="text/javascript">
    function refreshGridCurrentPage(){
    	var jq = jQuery('#chargeType_gridtable');
        var currentPage = jq.jqGrid('getGridParam', 'page');  
    	jQuery('#chargeType_gridtable').trigger('reloadGrid', [{page: currentPage }]);
    }
    	function addRecord(){
			var url = "editChargeType?popup=true";
			var winTitle='<fmt:message key="chargeTypeNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#chargeType_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editChargeType?popup=true&chargeTypeId=" + sid;
				var winTitle='<fmt:message key="chargeTypeNew.title"/>';
				popUpWindow(url, winTitle, "width=500");
			}
		}
		function checkGridDeleteOperation(response,postdata){
		    var gridresponse = gridresponse || {};
		    gridresponse = jQuery.parseJSON(response.responseText);
		    var msg = gridresponse["gridOperationMessage"];
		    //alert(msg);
		   //jQuery("#gridinfo").html(msg);
		   jQuery('div.#mybuttondialog').html(msg);
			  jQuery('#mybuttondialog').dialog('open');
	        return [true,""];   
		}
	    function okButton(){
	    	 jQuery('#mybuttondialog').dialog('close');
	    };		
		datePick = function(elem){
		        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
		        jQuery('#ui-datepicker-div').css("z-index", 2000);
		};
		function chargeTypeGridReload(){
			var urlString = "chargeTypeGridList";
			var chargeTypeIdTxt = jQuery("#chargeTypeIdTxt").val();
			var chargeTypeNameTxt = jQuery("#chargeTypeNameTxt").val();
			var medOrLeeTxt = jQuery("#medOrLeeTxt").val();
			var payinItemIdTxt = jQuery("#payinItemIdTxt").val();
			var reportmarkTxt = jQuery("#reportmarkTxt").val();
			var disabledTxt = jQuery("#disabledTxt").val();

			
			urlString=urlString+"?filter_LIKES_chargeTypeId="+chargeTypeIdTxt+"&filter_LIKES_chargeTypeName="+chargeTypeNameTxt+"&filter_EQS_medOrLee="+medOrLeeTxt+"&filter_EQS_payinItem.payinItemId="+payinItemIdTxt+"&filter_EQS_reportmark="+reportmarkTxt+"&filter_EQB_disabled="+disabledTxt;
			urlString=encodeURI(urlString);
			jQuery("#chargeType_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		}
		/* var chargeTypeLayout;
		jQuery(document).ready(function() { 
			chargeTypeLayout = makeLayout({
				baseName: 'chargeType', 
				tableIds: 'chargeType_gridtable'
			}, null);
			chargeTypeLayout.resizeAll();
    	}); */

		jQuery(document).ready(function() {
			
			var initChargeTypeFlag = 0;
			var chargeTypeGrid = jQuery("#chargeType_gridtable");
			chargeTypeGrid.jqGrid({
		    	url : "chargeTypeGridList",
		    	editurl:"chargeTypeGridEdit",
				datatype : "json",
				mtype : "GET",
		        colModel:[
					{name:'chargeTypeId',index:'chargeTypeId',align:'left',width:"175",label : '<s:text name="chargeType.chargeTypeId" />',sortable:true,hidden:false,highsearch:false,key:true},				
					{name:'chargeTypeName',index:'chargeTypeName',align:'left',width:"175",label : '<s:text name="chargeType.chargeTypeName" />',sortable:true,hidden:false,highsearch:true},				
					{name:'cnCode',index:'cnCode',align:'left',width:"175",label : '<s:text name="chargeType.cnCode" />',sortable:true,hidden:false,highsearch:true},				
					{name:'medOrLee',index:'medOrLee',align:'left',width:"175",label : '<s:text name="chargeType.medOrLee" />',sortable:true,hidden:false,highsearch:true},				
					{name:'payinItem.payinItemName',index:'payinItem.payinItemName',align:'left',width:"175",label : '<s:text name="payinItem.payinItemName" />',hidden:false},				
					{name:'parent.chargeTypeName',index:'parent.chargeTypeName',align:'left',width:"180",label : '<s:text name="chargeType.parentId" />',hidden:false},				
					{name:'reportmark',index:'reportmark',align:'left',width:"180",label : '<s:text name="chargeType.reportmark" />',sortable:true,hidden:false,highsearch:true},				
					{name:'disabled',index:'disabled',align:'center',width:"180",label : '<s:text name="chargeType.disabled" />',hidden:false,highsearch:true,edittype:"checkbox",formatter:"checkbox"},				
					{name:'leaf',index:'leaf',align:'center',width:"180",label : '<s:text name="chargeType.leaf" />',hidden:false,highsearch:true,edittype:"checkbox",formatter:"checkbox"}				
					
				],
		        jsonReader : {
					root : "chargeTypes", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				},
		        sortname: 'chargeTypeId',
		        viewrecords: true,
		        sortorder: 'asc',
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
					 
					 gridContainerResize("chargeType","div");
		           var dataTest = {"id":"chargeType_gridtable"};
		      	   jQuery.publish("onLoadSelect",dataTest,null);
		      	   makepager("chargeType_gridtable");
		      	 initChargeTypeFlag = initColumn('chargeType_gridtable','com.huge.ihos.inout.model.ChargeType',initChargeTypeFlag);
		       	} 
		    });
		    jQuery(chargeTypeGrid).jqGrid('bindKeys');
		});
	</script>
</head>

<div class="page">
	<!-- <div id="chargeType_container">
			<div id="chargeType_layout-center"
				class="pane ui-layout-center"> -->
	<div id="chargeType_pageHeader" class="pageHeader">
		<form id="chargeType_search_form" onsubmit="return navTabSearch(this);" action="userGridList"
			method="post" class="queryarea-form">
			<div class="searchBar">
				<div class="searchContent">
						<label class="queryarea-label"><fmt:message key='chargeType.chargeTypeId'/>：
							<input type="text"	id="chargeTypeIdTxt" class="input-small" size="10">
						</label>
						<label class="queryarea-label"><fmt:message key='chargeType.chargeTypeName'/>：
						 	<input type="text"	id="chargeTypeNameTxt" class="input-small">
						 </label>
						<label class="queryarea-label"><fmt:message key='chargeType.medOrLee'/>：
						 <appfuse:dictionary code="medOrLee" name="medOrLeeTxt" cssClass="input-small"></appfuse:dictionary>
						</label>
						<label class="queryarea-label"><fmt:message key='payinItem.payinItemName'/>：
							<s:select theme="simple" key="payinItem.payinItemName" name="chargeType.payinItem.payinItemId" cssClass="input-small" value="%{chargeType.payinItem.payinItemId}" id="payinItemIdTxt" required="false" list="payinItemList" listKey="payinItemId" listValue="payinItemName" emptyOption="true" />
						</label>
						<label class="queryarea-label"><fmt:message key='chargeType.reportmark'/>：
							<input type="text"	id="reportmarkTxt" class="input-small" size="10">
						</label>
						<label class="queryarea-label"><fmt:message key='chargeType.disabled'/>：
							<appfuse:singleSelect htmlFieldName="disabledTxt" paraDisString="否;是" paraValueString="false;true" cssClass="input-small"></appfuse:singleSelect>
						</label>
						
						<div class="buttonActive" style="float:right">
								<div class="buttonContent">
									<button type="button" onclick="chargeTypeGridReload()"><s:text name='button.search'/></button>
								</div>
							</div>
						
				</div>
				<%-- <div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="chargeTypeGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
						<!-- <li><a class="button" href="demo_page6.html" target="dialog"
							rel="dlg_page1" title="查询框"><span>高级检索</span>
						</a>
						</li> -->
					</ul>
				</div> --%>
			</div>
		</form>
	</div>
	<div class="pageContent">



<%--  <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="chargeTypeGridEdit"/> 
<s:url id="remoteurl" action="chargeTypeGridList"/>  --%>

<div class="panelBar" id="chargeType_buttonBar">
			<ul class="toolBar">
				<li><a id="chargeType_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="chargeType_gridtable_del" class="delbutton"  href="javaScript:"><span>删除</span>
				</a>
				</li>
				<li><a id="chargeType_gridtable_edit" class="changebutton"  href="javaScript:"
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
		<div id="chargeType_gridtable_div" 
			class="grid-wrapdiv"
			buttonBar="width:600;height:300">
			<input type="hidden" id="chargeType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="chargeType_gridtable_addTile">
				<fmt:message key="chargeTypeNew.title"/>
			</label> 
			<label style="display: none"
				id="chargeType_gridtable_editTile">
				<fmt:message key="chargeTypeEdit.title"/>
			</label>
			<label style="display: none"
				id="chargeType_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="chargeType_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
	<div id="load_chargeType_gridtable" class='loading ui-state-default ui-state-active' style="display: none;"></div>
 	<table id="chargeType_gridtable"></table>
 <%-- <sjg:grid 
    	id="chargeType_gridtable" 
    	dataType="json" 
    	gridModel="chargeTypes"
    	href="%{remoteurl}"    	
    	editurl="%{editurl}"
    	rowList="10,15,20,30,40,50,60,70,80,90,100"
    	rowNum="20"
    	rownumbers="true"
    	pager="false" 
    	page="1"
    	pagerButtons="false"
    	pagerInput="false"
    	pagerPosition="right"
		navigator="false"
		navigatorAdd="false"
        navigatorEdit="false"
		navigatorDelete="false"
		navigatorDeleteOptions="{reloadAfterSubmit:true,afterSubmit:checkGridDeleteOperation,left:screen.availWidth/4, top:screen.availHeight/4}"   
        navigatorSearch="false"
        navigatorSearchOptions="{multipleSearch:true,  showQuery: true,left:screen.availWidth/4, top:screen.availHeight/4}"
 		navigatorRefresh="false"
    	multiselect="true"
  		multiboxonly="true"
  		resizable="true"
  		onCompleteTopics="onLoadSelect"
  		autowidth="true"
  		draggable="true"
    >
    <sjg:gridColumn 
    	    name="chargeTypeId" 
    	    search="false" 
    	    index="chargeTypeId" 
    	    title="%{getText('chargeType.chargeTypeId')}" 
    	    hidden="false"
    	    key="true"
    	    />
    

   <sjg:gridColumn 
 	    name="chargeTypeName" 
 	    index="chargeTypeName" 
 	    title="%{getText('chargeType.chargeTypeName')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="cnCode" 
 	    index="cnCode" 
 	    title="%{getText('chargeType.cnCode')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    /> 	  
   <sjg:gridColumn 
 	    name="medOrLee" 
 	    index="medOrLee" 
 	    title="%{getText('chargeType.medOrLee')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    /> 	 
 	   <sjg:gridColumn 
 	    name="payinItem.payinItemName" 
 	    index="payinItem.payinItemName" 
 	    title="%{getText('payinItem.payinItemName')}" 

 	    />  	         
 	   <sjg:gridColumn 
 	    name="parent.chargeTypeName" 
 	    index="parent.chargeTypeName" 
 	    title="%{getText('chargeType.parentId')}" 

 	    />    
    <sjg:gridColumn 
 	    name="reportmark" 
 	    index="reportmark" 
 	    title="%{getText('chargeType.reportmark')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
 	        <sjg:gridColumn 
    	name="disabled" 
	    index="disabled" 
	    title="%{getText('chargeType.disabled')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"/>  --%>
<%--    <sjg:gridColumn 
 	    name="clevel" 
 	    index="clevel" 
 	    title="%{getText('chargeType.clevel')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />


   <sjg:gridColumn 
 	    name="displayMark" 
 	    index="displayMark" 
 	    title="%{getText('chargeType.displayMark')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />--%>
 <%--    <sjg:gridColumn 
    	name="leaf" 
	    index="leaf" 
	    title="%{getText('chargeType.leaf')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"/>  
  </sjg:grid> --%>
</div>
	</div>
	<div class="panelBar" id="chargeType_pageBar">
		<div class="pages">
			<span>显示</span> <select id="chargeType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="chargeType_gridtable_totals"></label>条</span>
		</div>

		<div id="chargeType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	</div>
	<!-- </div>
	</div>
</div> -->