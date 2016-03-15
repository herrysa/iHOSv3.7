<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="payinItemList.title"/></title>
    <meta name="heading" content="<fmt:message key='payinItemList.heading'/>"/>
    <meta name="menu" content="PayinItemMenu"/>
    
    <script type="text/javascript">
    function refreshGridCurrentPage(){
    	var jq = jQuery('#payinItem_gridtable');
        var currentPage = jq.jqGrid('getGridParam', 'page');  
    	jQuery('#payinItem_gridtable').trigger('reloadGrid', [{page: currentPage }]);
    }
    	function addRecord(){
			var url = "editPayinItem?popup=true";
			var winTitle='<fmt:message key="payinItemNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#payinItem_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editPayinItem?popup=true&payinItemId=" + sid;
				var winTitle='<fmt:message key="payinItemNew.title"/>';
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
		/* var payinItemLayout;
		jQuery(document).ready(function() { 
			payinItemLayout = makeLayout({
				baseName: 'payinItem', 
				tableIds: 'payinItem_gridtable'
			}, null);
			payinItemLayout.resizeAll();
    	}); */
		jQuery(document).ready(function() {
			
			var initPayinItemFlag = 0;
			var payinItemGrid = jQuery("#payinItem_gridtable");
			payinItemGrid.jqGrid({
		    	url : "payinItemGridList",
		    	editurl:"payinItemGridEdit",
				datatype : "json",
				mtype : "GET",
		        colModel:[
					{name:'payinItemId',index:'payinItemId',align:'left',width:"400",label : '<s:text name="payinItem.payinItemId" />',sortable:true,hidden:false,highsearch:false,key:true},				
					{name:'payinItemName',index:'payinItemName',align:'left',width:"400",label : '<s:text name="payinItem.payinItemName" />',sortable:true,hidden:false,highsearch:false},				
					{name:'disabled',index:'disabled',align:'center',width:"400",label : '<s:text name="payinItem.disabled" />',hidden:false,highsearch:true,edittype:"checkbox",formatter:"checkbox"},				
					{name:'remark',index:'remark',align:'left',width:"400",label : '<s:text name="payinItem.remark" />',sortable:true,hidden:false,highsearch:true}				
					
				],
		        jsonReader : {
					root : "payinItems", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				},
		        sortname: 'payinItemId',
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
					 
					 gridContainerResize("payinItem","div");
		           var dataTest = {"id":"payinItem_gridtable"};
		      	   jQuery.publish("onLoadSelect",dataTest,null);
		      	   makepager("payinItem_gridtable");
		      	 initPayinItemFlag = initColumn('payinItem_gridtable','com.huge.ihos.inout.model.PayinItem',initPayinItemFlag);
		       	} 
		    });
		    jQuery(payinItemGrid).jqGrid('bindKeys');
		});
	</script>

</head>
<div class="page">
	<!-- <div class="pageContent">
	<div id="payinItem_container">
			<div id="payinItem_layout-center"
				class="pane ui-layout-center"> -->
 <%-- <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="payinItemGridEdit"/> 
<s:url id="remoteurl" action="payinItemGridList"/>  --%>
<div class="panelBar" id="payinItem_buttonBar">
			<ul class="toolBar">
				<li><a id="payinItem_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="payinItem_gridtable_del" class="delbutton"  href="javaScript:"><span>删除</span>
				</a>
				</li>
				<li><a id="payinItem_gridtable_edit" class="changebutton"  href="javaScript:"
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
		<div id="payinItem_gridtable_div" 
			class="grid-wrapdiv"
			buttonBar="width:570;height:280">
			<input type="hidden" id="payinItem_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="payinItem_gridtable_addTile">
				<fmt:message key="costItemNew.title"/>
			</label> 
			<label style="display: none"
				id="payinItem_gridtable_editTile">
				<fmt:message key="costItemEdit.title"/>
			</label>
			<label style="display: none"
				id="payinItem_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="payinItem_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
<div id="load_payinItem_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
<table id="payinItem_gridtable"></table>
<%--  <sjg:grid 
    	id="payinItem_gridtable" 
    	dataType="json" 
    	gridModel="payinItems"
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
  		autowidth="true"
		onCompleteTopics="onLoadSelect"
  		draggable="true"
  		altRows="false"
 
    >
    <sjg:gridColumn 
    	    name="payinItemId" 
    	    search="false" 
    	    index="payinItemId" 
    	    title="%{getText('payinItem.payinItemId')}" 
    	    hidden="false"
    	    key="true"
    	    />
       <sjg:gridColumn 
 	    name="payinItemName" 
 	    index="payinItemName" 
 	    title="%{getText('payinItem.payinItemName')}" 
 	    sortable="true"
 	    search="false"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    /> --%>

<%--     <sjg:gridColumn 
    	name="disabled" 
	    index="disabled" 
	    title="%{getText('payinItem.disabled')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"/> 

   <sjg:gridColumn 
 	    name="remark" 
 	    index="remark" 
 	    title="%{getText('payinItem.remark')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
  </sjg:grid> --%>
	
	</div>
	<div class="panelBar" id="payinItem_pageBar">
		<div class="pages">
			<span>显示</span>
			<select id="payinItem_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共<label id="payinItem_gridtable_totals"></label>条</span>
		</div>
		
		<div id="payinItem_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<!-- </div>
</div>
</div> -->