<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>


<head>

    <title><fmt:message key="mainMenu.title"/></title>
    <meta name="heading" content=""/>
    <meta name="menu" content="MainMenu"/>
<!--     <STYLE type="text/css">

#dictionary_container {
		background: #999;
		height: 100%;
		width: 100%;
		min-height: 600px;
		min-width: 700px;
		position: absolute;
		top: 0px; /* margins in pixels */
		bottom: 0px; /* could also use a percent */
		left: 0px;
		right: 0px;
}

.pane {
	display: none; /* will appear when layout inits */
}
</STYLE> -->

    <script>
    function setDictionaryModel(postdata, formid)
    {
  	  //alert("beforesubmit");
  	  postdata['dictionary.dictionaryId'] = postdata['dictionaryId'];
  	  postdata['dictionary.name'] = postdata['name'];
  	  postdata['dictionary.code'] = postdata['code'];
  	 // postdata['person.email'] = postdata['email'];
      return [true,""];
  	  
    }
    function refreshGridCurrentPage(){
    	var jq = jQuery('#dictionaryItems_gridtable');
    	alert(jq);
        var currentPage = jq.jqGrid('getGridParam', 'page');  
    	jQuery('#dictionaryItems_gridtable').trigger('reloadGrid', [{page: currentPage }]);
    }
    
   function refreshMasterGridCurrentPage(){
    	var jq = jQuery('#dictionary_gridtable');
    	//alert(jq);
        var currentPage = jq.jqGrid('getGridParam', 'page');  
    	jQuery('#dictionary_gridtable').trigger('reloadGrid', [{page: currentPage }]);
    }
    function setDictionaryItemModel(postdata, formid)
    {
      postdata['dictionaryItem.dictionaryItemId'] = postdata['dictionaryItemId'];
  	  postdata['dictionaryItem.content'] = postdata['content'];
  	postdata['dictionaryItem.value'] = postdata['value'];
  	postdata['dictionaryItem.orderNo'] = postdata['orderNo'];

	var  s = jQuery("#dictionary_gridtable").jqGrid('getGridParam','selarrrow');
	//alert(s);
  	 postdata['dictionaryId'] = s;
      return [true,""];
  	  
    }
function detailReload(){
	jQuery("#dictionaryItems_gridtable").trigger('reloadGrid');		
	
}

	function checkGridOperation(response,postdata)
	{
	    var gridresponse = gridresponse || {};
	    gridresponse = jQuery.parseJSON(response.responseText);
	    var msg = gridresponse["gridOperationMessage"];
	    //alert(msg);
        if (msg == 'Dictionary Saved')
        {
            return [true,""];
        }
        else
	    {
	        return [false,msg];   
	    }
        
	}


        

/*     var layoutSettings_dictionary = {
    		applyDefaultStyles:				true // basic styling for testing & demo purposes
      	,	east__size:				600
    	,	east__minWidth:			300
    	,	center__minWidth:   	500
    	,	east__closable : false,
    	
    	};
	     */
    jQuery(document).ready(function() { 
    	//jQuery('#dictionary_container').layout(layoutSettings_dictionary);
    	jQuery.subscribe('rowselect', function(event, data) {
   		 var row =jQuery("#dictionary_gridtable").jqGrid('getRowData',event.originalEvent.id);
   		 jQuery("#dictionaryIdC").attr("value",row['dictionaryId']);
   		 jQuery("#addItem_button").attr("disabled",false); 
            var ids = row['dictionaryId'];
      //   			alert("fdc come here.")
       			jQuery("#dictionaryItems_gridtable").jqGrid('setGridParam',{url:"dictionaryItemListJson?dictionaryId="+ids});
       			jQuery("#dictionaryItems_gridtable")
       			//.jqGrid('setCaption',"Invoice Detail: "+ids)
       			.trigger('reloadGrid');			

   	});
    });
	     function addRecordItem(){
				var url = "editDictionaryItem?popup=true";
				var ss = jQuery("#dictionaryIdC").attr("value");
				url=url+"&dictionaryId="+ss;
				if(ss==null||ss==""){
					alert("请选择一个父级字典项");
					return;
				} 
				var winTitle='<fmt:message key="dictionaryItemsList.title"/>';
				//openWindow(url, winTitle, " width=1000");
				$.pdialog.open(url, 'addDictionaryItem', winTitle, {mask:false,width:350,height:200});
			}
	     function addRecord(){
				var url = "editDictionary?popup=true";
				var winTitle='<fmt:message key="dictionaryDetail.title"/>';
				//openWindow(url, winTitle, " width=1000");
				$.pdialog.open(url, 'addDictionary', winTitle, {mask:false,width:350,height:150});
			}
	     function editRecord(){
		        var sid = jQuery("#dictionary_gridtable").jqGrid('getGridParam','selarrrow');
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
					var url = "editDictionary?popup=true&dictionaryId=" + sid;
					var winTitle='<fmt:message key="dictionaryDetail.title"/>';
					//openWindow(url, winTitle, " width=1000");
					$.pdialog.open(url, 'editDictionary', winTitle, {mask:false,width:350,height:150});
				}
			}
	     
	     function delRecord(){
	    	 var sid = jQuery("#dictionary_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
					//alert("<fmt:message key='list.selectNone'/>");
					jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectNone'/>");
					jQuery('#mybuttondialog').dialog('open');
					return;
				}else{
		         	jQuery("#gridinfo").html('<p>Loading..... ID : '+sid+'</p>');
					jQuery("#dictionary_gridtable").jqGrid('delGridRow',sid,{reloadAfterSubmit:false});
				}
	     }
	     
	     function editRecordItem(){
		        var sid = jQuery("#dictionaryItems_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
					//alert("<fmt:message key='list.selectNone'/>");
					jQuery('div.#mybuttondialogItem').html("<fmt:message key='list.selectNone'/>");
					jQuery('#mybuttondialogItem').dialog('open');
					return;
					}
		        if(sid.length>1){
					  //alert("<fmt:message key='list.selectMore'/>");
				  jQuery('div.#mybuttondialogItem').html("<fmt:message key='list.selectMore'/>");
				  jQuery('#mybuttondialogItem').dialog('open');
					return;
				  }else{
		         jQuery("#gridinfo").html('<p>Loading..... ID : '+sid+'</p>');
					var url = "editDictionaryItem?popup=true&dictionaryItemId=" + sid;
					var winTitle='<fmt:message key="dictionaryDetail.title"/>';
					//openWindow(url, winTitle, " width=1000");
					$.pdialog.open(url, 'editDictionaryItem', winTitle, {mask:false,width:350,height:150});
				}
			}
	     
	     function delRecordItem(){
	    	 var sid = jQuery("#dictionaryItems_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
					//alert("<fmt:message key='list.selectNone'/>");
					jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectNone'/>");
					jQuery('#mybuttondialog').dialog('open');
					return;
				}else{
		         	jQuery("#gridinfo").html('<p>Loading..... ID : '+sid+'</p>');
					jQuery("#dictionaryItems_gridtable").jqGrid('delGridRow',sid,{reloadAfterSubmit:false});
				}
	     }
	     
	     function okButton(){
	    	 jQuery('#mybuttondialog').dialog('close');
	    };		
	    
	    function dictionaryReload(){
	    	var urlString = "dictionaryListJson";
			var dictionary_name = jQuery("#dictionary_name").val();
			var dictionary_code = jQuery("#dictionary_code").val();
			
			urlString=urlString+"?filter_LIKES_name="+dictionary_name+"&filter_LIKES_code="+dictionary_code;
			urlString=encodeURI(urlString);
			jQuery("#dictionary_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	    }
	    
	    /* function dictionaryItemGridReload(){
	    	var urlString = "dictionaryItemListJson";
			var dictionaryItem_value = jQuery("#dictionaryItem_value").val();
			var dictionaryItem_content = jQuery("#dictionaryItem_content").val();
			var fId = jQuery("#dictionary_gridtable").jqGrid('getGridParam','selarrrow');
			urlString=urlString+"?filter_LIKES_value="+dictionaryItem_value+"&filter_LIKES_content="+dictionaryItem_content+"&filter_EQS_dictionary.id"=fId;
			urlString=encodeURI(urlString);
			jQuery("#dictionaryItems_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	    } */
    </script>
    
</head>

<div class="page">
<div class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<label><s:text name='dictionary.name'/>:
						 	<input type="text"	id="dictionary_name" >
					</label>
					<label><s:text name='dictionary.code'/>:
						 	<input type="text"	id="dictionary_code" >
					</label>
					<div class="buttonActive" style="float: right;">
						<div class="buttonContent">
							<button type="button" onclick="dictionaryReload()"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
			</div>
			</div>
	<div class="pageContent">
		<div id="dictionaryDiv" class="unitBox"
			style="float: left; display: block; overflow: auto; width: 40%;">
			<div class="panelBar">
				<ul class="toolBar">
					<li><a id="dictionary_gridtable_add" class="addbutton"
						href="javaScript:"><span><fmt:message key="button.add" />
						</span> </a></li>
					<li><a class="delbutton" id="dictionary_gridtable_del"
						href="javaScript:" title="确定要删除吗?"><span><fmt:message
									key="button.delete" /></span> </a></li>
					<li><a class="changebutton" id="dictionary_gridtable_edit"
						href="javaScript:"><span><fmt:message
									key="button.edit" /> </span> </a></li>
					<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
				</ul>
			</div>


			<sj:dialog id="mybuttondialog"
				buttons="{'OK':function() { okButton(); }}" autoOpen="false"
				modal="true" title="%{getText('messageDialog.title')}" />
			<sj:dialog id="mybuttondialogItem"
				buttons="{'OK':function() { okButton(); }}" autoOpen="false"
				modal="true" title="%{getText('messageDialog.title')}" />
			<s:hidden id="dictionaryIdC"></s:hidden>
			<s:url id="masterEditUrl" action="dictionaryEditJson" />
			<s:url id="masterRemoteUrl" action="dictionaryListJson" />
			<s:url id="detailRemoteUrl" action="dictionaryItemListJson" />
			<s:url id="detailEditUrl" action="dictionaryItemEditJson" />
			<s:url id="detailCellEditUrl" action="dictionaryItemCellEditJson" />

			<%-- <%@ include file="/common/messages.jsp"%> --%>
			<div id="dictionary_gridtable_div" layoutH="95"
				class="grid-wrapdiv"
				buttonBar="base_URL:editDictionary;optId:dictionaryId;width:400;height:250">
				<input type="hidden" id="dataSourceDefine_gridtable_navTabId"
					value="${sessionScope.navTabId}"> <label
					style="display: none" id="dictionary_gridtable_addTile"> <fmt:message
						key="dictionaryDetail.title" />
				</label> <label style="display: none" id="dictionary_gridtable_editTile">
					<fmt:message key="dictionaryDetail.title" />
				</label>
				<sjg:grid id="dictionary_gridtable" href="%{masterRemoteUrl}"
					gridModel="dictionaries" dataType="json" editurl="%{masterEditUrl}"
					rownumbers="true" pager="false" page="1" pagerButtons="false"
					pagerInput="false" pagerPosition="center"
					rowList="5,10,15,20,25,30,35,40,45,50" rowNum="20"
					navigator="false" navigatorAdd="false"
					navigatorAddOptions="{reloadAfterSubmit:true,beforeSubmit:setDictionaryModel}"
					navigatorEdit="false"
					navigatorEditOptions="{reloadAfterSubmit:true,beforeSubmit:setDictionaryModel}"
					navigatorDelete="true"
					navigatorDeleteOptions="{reloadAfterSubmit:true,left:screen.availWidth/4, top:screen.availHeight/4}"
					navigatorSearch="false"
					navigatorSearchOptions="{sopt:['cn','bw','eq'],multipleSearch:true,  showQuery: true,left:screen.availWidth/4, top:screen.availHeight/4}"
					navigatorView="false" navigatorViewOptions=""
					navigatorRefresh="false" multiselect="true" multiselectWidth="20"
					multiboxonly="true" resizable="true" onSelectRowTopics="rowselect"
					autowidth="true" draggable="true" onCompleteTopics="onLoadSelect">
					<sjg:gridColumn name="dictionaryId" search="false"
						index="dictionaryId" title="%{getText('dictionary.dictionaryId')}"
						hidden="true" hidedlg="false" formatter="integer" sortable="false"
						width="50" editable="true" key="true" />
					<sjg:gridColumn name="name" index="name"
						title="%{getText('dictionary.name')}" sortable="true"
						editable="true" width="150" hidden="false" hidedlg="false" />
					<sjg:gridColumn name="code" index="code"
						title="%{getText('dictionary.code')}" sortable="true"
						editable="true" width="150" editrules="{required: true}"
						hidden="false" hidedlg="false" />
				</sjg:grid>

			</div>
			<div class="panelBar">
				<div class="pages">
					<span>显示</span> <select id="dictionary_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span>条，共<label id="dictionary_gridtable_totals"></label>条
					</span>
				</div>

				<div id="dictionary_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

			</div>
		</div>
		<div id=dictionaryItemDiv class="unitBox" style="margin-left: 40%;">
			<div class="panelBar">
				<ul class="toolBar">
					<li><a class="addbutton" id="dictionaryItems_gridtable_add"
						href="javaScript:"><span><fmt:message key="button.add" />
						</span> </a></li>
					<li><a class="delbutton" id="dictionaryItems_gridtable_del"
						href="javaScript:" title="确定要删除吗?"><span>删除</span> </a></li>
					<li><a class="changebutton" id="dictionaryItems_gridtable_edit"
						href="javaScript:"><span><fmt:message
									key="button.edit" /> </span> </a></li>
					<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
				</ul>
			</div>
			<div id="dictionaryItems_gridtable_div" layoutH="95"
				class="grid-wrapdiv"
				buttonBar="base_URL:editDictionaryItem;optId:dictionaryItemId;fatherGrid:dictionary_gridtable;extraParam:dictionaryId;width:400;height:250">
				<input type="hidden" id="dataSourceDefine_gridtable_navTabId"
					value="${sessionScope.navTabId}"> <label
					style="display: none" id="dictionaryItems_gridtable_addTile">
					<fmt:message key="dictionaryItemsList.title" />
				</label> <label style="display: none"
					id="dictionaryItems_gridtable_editTile"> <fmt:message
						key="dictionaryItemsList.title" />
				</label>
				<sjg:grid id="dictionaryItems_gridtable" href="%{detailRemoteUrl}"
					gridModel="dictionaryItems" dataType="json"
					editurl="%{detailEditUrl}" pager="false" page="1"
					pagerButtons="false" pagerInput="false" pagerPosition="center"
					rowList="5,10,15,20,25,30,35,40,45,50" rowNum="20"
					rownumbers="true" navigator="false" navigatorAdd="false"
					navigatorAddOptions="{reloadAfterSubmit:true,beforeSubmit:setDictionaryItemModel,afterSubmit:detailReload}"
					navigatorEdit="false"
					navigatorEditOptions="{reloadAfterSubmit:true,beforeSubmit:setDictionaryItemModel,afterSubmit:detailReload}"
					navigatorDelete="false"
					navigatorDeleteOptions="{reloadAfterSubmit:true}"
					navigatorSearch="false"
					navigatorSearchOptions="{sopt:['cn','bw','eq'],multipleSearch:true,  showQuery: true}"
					navigatorView="false" navigatorViewOptions=""
					navigatorRefresh="false" multiselect="true" multiselectWidth="20"
					multiboxonly="true" resizable="true" autowidth="true"
					onCompleteTopics="onLoadSelect">



					<sjg:gridColumn name="dictionaryItemId" search="false"
						index="dictionaryItemId"
						title="%{getText('dictionaryItems.dictionaryItemsId')}"
						hidden="true" formatter="integer" sortable="false" width="50"
						key="true" />
					<sjg:gridColumn name="dictionary.dictionaryId" search="false"
						index="dictionary.dictionaryId"
						title="%{getText('dictionary.dictionaryId')}" hidden="true"
						formatter="integer" sortable="false" width="50" />
					<sjg:gridColumn name="value" index="value"
						title="%{getText('dictionaryItem.value')}" sortable="true"
						width="100" editrules="{required: true}" />
					<sjg:gridColumn name="content" index="content"
						title="%{getText('dictionaryItem.content')}" sortable="true"
						width="100" editrules="{required: true}" />
					<sjg:gridColumn name="orderNo" index="orderNo"
						title="%{getText('dictionaryItem.orderNo')}" sortable="true"
						width="100" editrules="{required: true}" />
				</sjg:grid>
			</div>
			<div class="panelBar">
				<div class="pages">
					<span>显示</span> <select id="dictionaryItems_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span>条，共<label id="dictionaryItems_gridtable_totals"></label>条
					</span>
				</div>

				<div id="dictionaryItems_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

			</div>
		</div>
	</div>
</div>


<!--        
		</DIV>
		<DIV class="pane ui-layout-north">North</DIV>
		<DIV class="pane ui-layout-south">South</DIV>
		<DIV class="pane ui-layout-east"> -->
		
<!-- 		</DIV>
		<DIV class="pane ui-layout-west"></DIV>
	</DIV>
 -->