<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.huge.ihos.system.reportManager.search.model.SearchOption"%>


<head>
<title><fmt:message key="searchUrlList.title" /></title>
<meta name="heading"
	content="<fmt:message key='searchUrlList.heading'/>" />
<meta name="menu" content="SearchUrlMenu" />
<script type="text/javascript"	src="${ctx}/jquerytheme/js/jquery-ui-1.8.19.custom.min.js"></script>
<link rel="stylesheet" type="text/css" media="all"	href="${ctx}/scripts/jqgrid/css/ui.jqgrid.css" />
<script type="text/javascript"	src="${ctx}/scripts/jqgrid/js/jquery.jqGrid.src.js"></script>
<script type="text/javascript"	src="${ctx}/scripts/jqgrid/js/i18n/grid.locale-cn.js"></script>


<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#gridTable").jqGrid({
			url : "queryListJson?searchName=${searchName}",
			editurl:  "pub?searchName=${searchName}",
			datatype : "json",
			mtype : "GET",
			height : "600",
			autowidth : true,
			shrinkToFit :false,
			colModel : [
			<c:forEach items="${searchOptions}" var="op">
			{
				name : "${op.fieldName }",
				index : "${op.fieldName }",
				label : "${op.title}",
				width : "${op.displayWidth*10}",
				<c:if test="${op.fieldName==viewDef.key}">
					key:true,
					frozen : false,
				</c:if>
			<c:if test="${op.fieldType=='Boolean'}">
					formatter :"checkbox",
				</c:if>
				align:'${op.alignType}'
			},
			
			</c:forEach>
			            
			     ],
			viewrecords : true,
			rowNum : ${viewDef.pageSize},
			rowList : [${viewDef.pageSize},${viewDef.pageSize}*2,${viewDef.pageSize}*3,${viewDef.pageSize}*4,${viewDef.pageSize}*5,${viewDef.pageSize}*6,${viewDef.pageSize}*7,${viewDef.pageSize}*8,${viewDef.pageSize}*9,${viewDef.pageSize}*10],
			prmNames : {
				search : "search"
			}, //(1)
			jsonReader : {
				root : "queryList", // (2)
				records : "records", // (3)
				repeatitems : false	// (4)
			},
			pager : "#gridPager",
			caption : "${viewDef.title}",
			hidegrid : false,
			<c:if test="${viewDef.multiSelect}">
			multiselect: true,
			multiboxonly:true,
			</c:if>
			altRows: false,
			rownumbers: true
		});
		jQuery("#gridTable").jqGrid('navGrid', '#gridPager', {edit:false,add:false,del:false,search:false},
				{},
				{},
				{},
				{multipleSearch:false, multipleGroup:false, showQuery: true}
				);
		
		
		
		
		jQuery("#gridTable").jqGrid('setFrozenColumns');
	});
	
	function gridReload(){
		var urlString = "queryListJson?searchName=${searchName}";
		<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
			<c:forEach items="${searchItems}" var="item">
				var ${item.htmlField} = jQuery("#${item.htmlField}").val();
			</c:forEach>
		</c:if>
		<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
			urlString=urlString<c:forEach items="${searchItems}" var="item">+"&${item.htmlField}="+${item.htmlField}</c:forEach>;
		</c:if>
		urlString=encodeURI(urlString);
		jQuery("#gridTable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	

    function confirmDelete(){

        var sid = jQuery("#gridTable").jqGrid('getGridParam','selarrrow');
        var msgDialog = jQuery('#msgDialog');
        var confirmDialog = jQuery("#confirmDialog");
        if(sid==null || sid.length ==0){
		
				msgDialog.dialog('option', 'buttons', {
					"<fmt:message key='dialog.ok'/>": function() {
						jQuery( this ).dialog( "close" );
					}
		    	});
			jQuery('div.#msgDialog').html("<fmt:message key='list.selectNone'/>");
			msgDialog.dialog('open');
			return;
			}else{
				confirmDialog.dialog('option', 'buttons', {
					"<fmt:message key='dialog.confirm'/>": function() {
						jQuery( this ).dialog( "close" );
						var url = "pub?searchName=${searchName}&id="+sid+"&actionName=delete";
						 var jqxhr=jQuery.post( url, {dataType : "json"})
					    .success(function(data)
					    		{
					      	var status = data['jsonStatus'];
					    	if(status=="error"){
						jQuery('div.#msgDialog').html("<p class='ui-state-error'>"+data['gridOperationMessage']+"</p>");
					    	}else{
					    		jQuery('div.#msgDialog').html("<p class='ui-state-hover'>"+data['gridOperationMessage']+"</p>");
					    	}
						msgDialog.dialog('open');
		 				})
					    .error(function() {  alert(data['gridOperationMessage']); });
					   
					    jqxhr.complete(function(){
							jQuery("#gridTable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
							});
					},
					"<fmt:message key='dialog.cancel'/>": function() {
						jQuery( this ).dialog( "close" );
					}
				}
				
			
				);
				jQuery('div.#confirmDialog').html("<fmt:message key='dialog.confirmDelete'/>");
				confirmDialog.dialog('open');
		}
	}
    
    function selectConfirmProcess(taskName,args,msg){

        var sid = jQuery("#gridTable").jqGrid('getGridParam','selarrrow');
        var msgDialog = jQuery('#msgDialog');
        var confirmDialog = jQuery("#confirmDialog");
        if(sid==null || sid.length ==0){
		
				msgDialog.dialog('option', 'buttons', {
					"<fmt:message key='dialog.ok'/>": function() {
						jQuery( this ).dialog( "close" );
					}
		    	});
			jQuery('div.#msgDialog').html("<fmt:message key='list.selectNone'/>");
			msgDialog.dialog('open');
			return;
			}else{
				confirmDialog.dialog('option', 'buttons', {
					"<fmt:message key='dialog.confirm'/>": function() {
						jQuery( this ).dialog( "close" );
						var url = "pub?searchName=${searchName}&id="+sid+"&actionName=process&taskName="+taskName+args;
						//alert(url);
						 var jqxhr=jQuery.post( url, {dataType : "json"})
					    .success(function(data)
					    		{
					      	var status = data['jsonStatus'];
					    	if(status=="error"){
						jQuery('div.#msgDialog').html("<p class='ui-state-error'>"+data['jsonMessages']+"</p>");
					    	}else{
					    		jQuery('div.#msgDialog').html("<p class='ui-state-hover'>"+data['jsonMessages']+"</p>");
					    	}
						msgDialog.dialog('open');
		 				})
					    .error(function() {  alert(data['jsonMessages']); });
					   
					    jqxhr.complete(function(){
							jQuery("#gridTable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
							});
					},
					"<fmt:message key='dialog.cancel'/>": function() {
						jQuery( this ).dialog( "close" );
					}
				}
				
			
				);
				jQuery('div.#confirmDialog').html(msg);
				confirmDialog.dialog('open');
		}
	}
    function noSelectConfirmProcess(taskName,args,msg){
        var sid = jQuery("#gridTable").jqGrid('getGridParam','selarrrow');
        var msgDialog = jQuery('#msgDialog');
        var confirmDialog = jQuery("#confirmDialog");
				confirmDialog.dialog('option', 'buttons', {
					"<fmt:message key='dialog.confirm'/>": function() {
						jQuery( this ).dialog( "close" );
						var url = "pub?searchName=${searchName}&id="+sid+"&actionName=process&taskName="+taskName+args;
						 var jqxhr=jQuery.post( url, {dataType : "json"})
					    .success(function(data)
					    		{
					    	var status = data['jsonStatus'];
					    	if(status=="error"){
						jQuery('div.#msgDialog').html("<p class='ui-state-error'>"+data['jsonMessages']+"</p>");
					    	}else{
					    		jQuery('div.#msgDialog').html("<p class='ui-state-hover'>"+data['jsonMessages']+"</p>");
					    	}
						msgDialog.dialog('open');
		 				})
					    .error(function(responseText, textStatus, XMLHttpRequest) {
					    	
					    	alert( $(this).html() );
					    	alert(responseText);
					    	alert(textStatus);
					    	alert(XMLHttpRequest);
					    
					    
					    });
					   
					    jqxhr.complete(function(){
							jQuery("#gridTable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
							});
					},
					"<fmt:message key='dialog.cancel'/>": function() {
						jQuery( this ).dialog( "close" );
					}
				}
				
			
				);
				jQuery('div.#confirmDialog').html(msg);
				confirmDialog.dialog('open');
	/* 	} */
	}
</script>
</head>
<sj:dialog id="msgDialog"
	buttons="{'%{getText('dialog.ok')}':function() {jQuery( this ).dialog( 'close' ); }}"
	autoOpen="false" modal="true" title="%{getText('messageDialog.title')}" />
<sj:dialog id="confirmDialog"
	buttons="{'%{getText('dialog.confirm')}':function() {jQuery( this ).dialog( 'close' ); },'%{getText('dialog.cancel')}':function() {jQuery( this ).dialog( 'close' ); }}"
	autoOpen="false" modal="true" title="%{getText('messageDialog.title')}" />
<sj:dialog id="testDialog"
	buttons="{'OK':function() {jQuery( this ).dialog( 'close' ); }}"
	autoOpen="false" modal="true" title="%{getText('messageDialog.title')}" />
<div>
	<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
		<c:forEach items="${searchItems}" var="item">
			<s:label>${item.caption}</s:label>
			<c:choose>
				<c:when test="${item.userTag=='yearMonth'}">
					<appfuse:yearMonth htmlField="${item.htmlField}"></appfuse:yearMonth>
				</c:when>
				<c:when test="${item.userTag=='checkbox'}">
					<input type="checkbox" id="${item.htmlField}" />
				</c:when>
				<c:when test="${item.userTag=='stringSelect'}">
					<appfuse:stringSelect htmlFieldName="${item.htmlField}"
						paraString="${item.param1}"></appfuse:stringSelect>
				</c:when>
				<c:when test="${item.userTag=='dicSelect'}">
					<appfuse:dictionary code="${item.param1}" name="${item.htmlField}" required="false" />	
				</c:when>

				<c:otherwise>
					<input type="text" id="${item.htmlField}" />
				</c:otherwise>
			</c:choose>
			<br />
		</c:forEach>
		<button onclick="gridReload()" id="submitButton"
			style="margin-left: 30px;">Search</button>
	</c:if>
</div>

<c:forEach items="${searchUrls}" var="op">
	<button id="${op.searchUrlId}" style="margin-left: 0px;"
		onclick="${op.url}">${op.title}</button>
</c:forEach>
<div>
	<table id="gridTable"></table>
	<div id="gridPager"></div>
</div>
