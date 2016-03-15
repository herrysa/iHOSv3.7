<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.huge.ihos.system.reportManager.search.model.SearchOption"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<title><fmt:message key="searchUrlList.title" /></title>
<meta name="heading"
	content="<fmt:message key='searchUrlList.heading'/>" />
<meta name="menu" content="SearchUrlMenu" />
<script type="text/javascript"	src="${ctx}/scripts/jqgrid/js/jquery.jqGrid.groupHeader-0.2.js"></script>
<script type="text/javascript"	src="${ctx}/scripts/formater/format.20110630-1100.min.js"></script>
<script type="text/javascript">

function chartGridReload(){
	var urlString="${ctx}/chartQuery?searchName=${searchName}&actionName=queryChartEx&_key=${_vKey}&_field=${_valueField}";

	<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
		<c:forEach items="${searchItems}" var="item">
			var ${item.htmlField} = jQuery("#${random}_${searchName}_${item.htmlField}").val();
		</c:forEach>
	</c:if>
	<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
		urlString=urlString<c:forEach items="${searchItems}" var="item">+"&${item.htmlField}="+${item.htmlField}</c:forEach>;
	</c:if>
	urlString=encodeURI(urlString);
	//console.log(urlString);
	$.pdialog.open(urlString, "txxs", "图形显示",{width:800,height:600});
	/* navTab.openTab("txxs",urlString, {
		title : "图形显示",
		fresh : false,
		data : {}
	}); */

}

	function stringFormatter (cellvalue, options, rowObject)	{
		try
			{cellvalue= cellvalue.replace(/\r\n/g, "").replace(/\n/g, "");
			}
			catch(err)
			{
				return cellvalue;
			}
		return cellvalue;
	}
    function numberFormatter(cellValue, opts, rowObject) {
		try{
    	 	var op =opts.colModel.formatoptions;
    		var ft = op.definedFormat;
    		return  format( ft, cellValue);
		}catch(err){
			alert(err);
		}
       }
    
	function reFormatColumnData(grid){
		 var rowNum = jQuery(grid).getDataIDs().length;
		 var ret = jQuery(grid).jqGrid('getRowData');
	     if(rowNum > 0){
	    	 var rowIds = jQuery(grid).getDataIDs();
	    	 var i=0
	    	 for (i=0;i<rowNum;i++){
	    	 <c:forEach items="${searchOptions}" var="op">
		    	 <c:if test="${op.url!=null && fn:length(op.url)>0 }">
			    	var id = rowIds[i];
			    	var data = ret[i]["${op.fieldNameUpperCase}"];
			    	var linkField="${op.linkField}";
			    	var fields=linkField.split(",");
			    	
			    	var j=0;
			    	var args="";
			    	for(j=0;j<fields.length;j=j+2){
			    		try{
			    		args=args+"&"+fields[j]+"="+ret[i][fields[j+1].toUpperCase()];
			    		}
						catch(err)
						{
						}
			    	}
			    	var hrefUrl = "${ctx}${op.url}"+args;
			    	hrefUrl=encodeURI(hrefUrl);
					setCellText(grid,id,'${op.fieldNameUpperCase}','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="zhuanqu(\''+hrefUrl+'\')" target="navTab">'+data+"</a>")
			    </c:if>
				
				
				 <c:if test="${op.threshold!=null && fn:length(op.threshold)>0 && op.threshold!='null'}">
			    	var id = rowIds[i];
			    	var data = ret[i]["${op.fieldNameUpperCase}"];
			    	var threshold="${op.threshold}";
			    	//alert(threshold);
			    	threshold = unescapeHTML(threshold);
			    	var fields=threshold.split(";");
			    	var j=0;
			    	var args="";
			    	var param;
			    	 for(j=0;j<fields.length;j++){
			    		try{
			    			param=fields[j].split(",");
			    			if(eval(data+param[0]+' && '+ data+param[1])){
			    				jQuery(grid).setCell(id,'${op.fieldNameUpperCase}','',{color:param[2]});
			    				
			    				}
			    		}
						catch(err)
						{
							alert(err);
						}
			    	} 
				</c:if>
	    	 </c:forEach>
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
	
	function unescapeHTML(str) {  
		try{
		str=str.replace(/\r\n/g, "").replace(/\n/g, "");
		  str = String(str).replace(/&gt;/g, '>').replace(/&lt;/g, '<').replace(/&quot;/g, '"').replace(/&amp;/g, '&');
		  
		  //alert(str);
		}catch(e){
			alert(e);
		}
		  return str;  
		}
	var ret;
	var fullSize = jQuery("#container").innerHeight()+17;
	var fullWidth = jQuery("#container").innerWidth()+17;
	var useFooter = "${sumSearchOptionsNum>0}";
	var isOpen = "${!viewDef.opened}";
	var headLeve = "${headLeve}";
	var reportType = "${reportType}";
	//alert(useFooter+","+isOpen+","+headLeve+","+reportType);
	jQuery("#${random}_${searchName}_container").css("height",fullSize);
	function resizeGridTable(paneName,element,state,options,layoutName){
		var gridTableWidth = state.innerWidth;
		var gridTableHeight = state.innerHeight;
		if(jQuery("#${random}_${searchName}_searchDiv").css("display")=="none"){
			gridTableHeight -= 125;
		}else{
			var searchDivHeight = jQuery("#${random}_${searchName}_searchDiv").css('height');
			searchDivHeight = searchDivHeight.replace("px","");
			if(parseInt(searchDivHeight)>40){
				gridTableHeight -= 186;
			}else{
				gridTableHeight -= 162;
			}
			
		}
		if(useFooter=='true'){
			gridTableHeight -= 22;
		}
		if(headLeve=='2'){
			gridTableHeight -= 22;
		}else if(headLeve=='3'){
			gridTableHeight -= 44;
		}
		if(reportType=='1'){
			gridTableHeight -= 20;
		}else if(reportType=='2'){
			gridTableHeight -= 36;
		}
		
		setTimeout(function(){
			jQuery("#${random}_${searchName}_gridTable").setGridWidth(gridTableWidth); 
			jQuery("#${random}_${searchName}_gridTable").setGridHeight(gridTableHeight); 
		},200);
		
	}
	var girdTableLayout;
	jQuery(document).ready(function() {
		jQuery("#${random}_${searchName}_gridTable").jqGrid({
			<c:choose>
			   <c:when test="${!viewDef.opened}">
			   /* "", */
			   </c:when>
			   <c:otherwise> 
			   url :encodeURI("${ctx}/queryExListJson?searchName=${searchName}&_vKey=${_vKey}&_aKey=${_aKey}&_valueField=${_valueField}&_vValues=${_vValues}&_aValues=${_aValues}&_valueType=${_valueType}&_vKeyTitle=${_vKeyTitle}<c:forEach items='${searchItemInit}' var='op'>&${op}</c:forEach>"),
			   </c:otherwise>
			</c:choose>
			editurl:  "${ctx}/pub?searchName=${searchName}",
			
			<c:choose>
			  <c:when test="${!viewDef.opened}">
			  datatype : "local",
			   </c:when>
			   <c:otherwise> 
			   datatype : "json",
			   </c:otherwise>
			   </c:choose>
			mtype : "GET",
			autowidth : true,
			shrinkToFit :false,
			height:"100%",
			colModel : [
			<c:forEach items="${searchOptions}" var="op">
			{
				name : "${op.fieldNameUpperCase }",
				index : "${op.fieldName }",
				label : "${op.title}",
				width : "${op.displayWidth*10}",
				sortable:${op.sortable},
				frozen : ${op.frozen},
			<c:if test="${op.fieldNameUpperCase==viewDef.upperKey}">
					key:true,
					//frozen : false,
			</c:if>
				//<c:if test="${op.editType=='Label'}">
				
		 	 	  	<c:if test="${op.fieldType=='String'}">
						formatter:stringFormatter,
					</c:if> 	 
						
					<c:if test="${op.fieldType=='Money'}">
						<c:choose>
						   <c:when test="${op.displayFormat!=null && fn:length(op.displayFormat)>0 && op.displayFormat!='null'}">
								formatter:numberFormatter, formatoptions:{definedFormat:"${op.displayFormat}"},
						   </c:when>
						   <c:otherwise>
						   		formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},
						   </c:otherwise>
						</c:choose>
					</c:if>	
					<c:if test="${op.fieldType=='Number'}">
						<c:choose>
						   <c:when test="${op.displayFormat!=null && fn:length(op.displayFormat)>0 && op.displayFormat!='null'}">
								formatter:numberFormatter, formatoptions:{definedFormat:"${op.displayFormat}"},
						   </c:when>
						   <c:otherwise>
						   		formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'},
						   </c:otherwise>
						</c:choose>
					</c:if>		
					
					<c:if test="${op.fieldType=='Date'}">
						formatter:'date', formatoptions:{ newformat: 'Y-m-d'},
					</c:if>		
					<c:if test="${op.fieldType=='Integer'}">
						formatter:'integer', formatoptions:{thousandsSeparator: "", defaultValue: ''},
					</c:if>		
						
				//</c:if>
	 		<c:if test="${op.editType=='Input'}">
	 				editable:true,
	 			<c:if test="${op.fieldType=='Money' || op.fieldType=='Number' }">
	 				editrules:{number:true},
	 			</c:if>
	 				<c:if test="${op.fieldType=='Integer'}">
	 				editrules:{integer:true},
	 			</c:if>
			</c:if>
			<c:if test="${op.editType=='stringSelect' || op.editType=='stringSelectR' || op.editType=='sqlSelect' || op.editType=='sqlSelectR' }">
				editable:true,
				edittype:'select', 
				formatter:'select',
				editoptions:{value:'${op.param2}'},
			</c:if>
				
			<c:if test="${op.fieldType=='Boolean'}">
				formatter :"checkbox",
	 			<c:if test="${op.editType=='CheckBox' }">
					// formatter :"checkbox", 
					editable:true,
					edittype:'checkbox', 
					editoptions: { value:"1:0" },
				</c:if> 
			</c:if>
			
			
			
			
			
			<c:if test="${!op.visible}">
				hidden:true,
			</c:if>
				
				align:'${op.alignType}'
			},
			
			</c:forEach>
			            
			     ],
			viewrecords : true,
			rowNum : ${viewDef.pageSize},
			rowList : [${viewDef.pageSize},${viewDef.pageSize},${viewDef.pageSize}*2,${viewDef.pageSize}*3,${viewDef.pageSize}*4,${viewDef.pageSize}*5,${viewDef.pageSize}*6,${viewDef.pageSize}*7,${viewDef.pageSize}*8,${viewDef.pageSize}*9,${viewDef.pageSize}*10],
			prmNames : {
				search : "search"
			}, //(1)
			jsonReader : {
				root : "queryList", // (2)
				records : "records", // (3)
				repeatitems : false	// (4)
			},
			hidegrid : false,
			<c:if test="${viewDef.multiSelect}">
				multiselect: true,
				multiboxonly:true,
			</c:if>
			<c:if test="${sumSearchOptionsNum>0}">
				footerrow : true,
				userDataOnFooter : true,
			</c:if>
			altRows: false,
			rownumbers: true,
			loadui: "disable",
			<c:if test="${viewDef.rownumWidth>0}">
			rownumWidth: ${viewDef.rownumWidth},
			</c:if>
			loadComplete: function(){
			},
			jqGridInlineAfterSaveRow:function(rowid, resp, tmp, o){
				
			},
			jqGridInlineSuccessSaveRow:function(res, rowid, o){
				
			},
			gridComplete: function() {
				//alert("gridComplete");
			     //urlFieldFormatter(jQuery(this));
			     reFormatColumnData(this);
			     var layoutSettings = {
						applyDefaultStyles : true // basic styling for testing & demo purposes
						,
						onresize_end : resizeGridTable
				};
			     makepager("${random}_${searchName}_gridTable");
			     girdTableLayout = jQuery("#${random}_${searchName}_container").layout(layoutSettings);
			     jQuery("#${random}_${searchName}_layout-center").css("padding",0);
				 layoutResize(girdTableLayout);
				 jQuery("#gview_${random}_${searchName}_gridTable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
						jQuery(this).find("th").each(function(){
							jQuery(this).find("div").eq(0).css("line-height","18px");
						});
					});
			}
		}
		
		
		);
	
		jQuery("#${random}_${searchName}_gridTable").jqGrid('bindKeys');
		
		
		
	 
		jQuery("#${random}_${searchName}_searchDivSimple").prepend("<input name='${random}_${searchName}_searchSimpleItem' type='text'/>");
		jQuery("#${random}_${searchName}_searchDivSimple").prepend("<select id='${random}_${searchName}_searchItem' style='width:95px'></select>&nbsp;");
		jQuery("#${random}_${searchName}_searchItem").append("<option value='-1'>--选择条件--</option>");
		jQuery("#${random}_${searchName}_searchDiv").find("label").each(function(){
			
			if(jQuery(this).find(":input").eq(0)!=null&&jQuery(this).find(":input").eq(0).attr("type")=="text"){
				var itemName = jQuery(this).text();
				itemName = itemName.substring(0,itemName.length-1);
				jQuery("#${random}_${searchName}_searchItem").append("<option value='"+jQuery(this).children().eq(0).attr("id")+"'>"+itemName+"</option>");
			}
		});
		jQuery("#${random}_${searchName}_searchItem").change(function(){
			
			jQuery("input[name=${random}_${searchName}_searchSimpleItem]").attr("id",jQuery(this).val());
			jQuery("input[name=${random}_${searchName}_searchSimpleItem]").attr("onClick",jQuery("#"+jQuery(this).val()).attr("onClick"));
			jQuery("input[name=${random}_${searchName}_searchSimpleItem]").attr("value",jQuery("#"+jQuery(this).val()).attr("value"));
		});
		var type = '${viewDef.searchBarType}';
		if(type==null){
			type=1;
		}
		if(type==0){
			jQuery("#${random}_${searchName}_searchDiv").hide();
			jQuery("#${random}_${searchName}_searchDivSimple").hide();
		}else if(type==1){
			jQuery("#${random}_${searchName}_searchDiv").hide();
		}else{
			jQuery("#${random}_${searchName}_higherButton").text("简单");
			jQuery("#${random}_${searchName}_searchDivSimple").hide();
		}
		
		jQuery("#${random}_${searchName}_higherButton").click(function(){
			if(type==1){
				jQuery("#${random}_${searchName}_searchDiv").show();
				jQuery(this).text("简单");
				type=0;
				
			}else{  
				jQuery("#${random}_${searchName}_searchDiv").hide();
				jQuery(this).text("高级");
				type=1;
			}
			layoutResize(girdTableLayout);
		});

		if(isOpen=='true'){
			setTimeout(function(){
				var layoutSettings = {
						applyDefaultStyles : true // basic styling for testing & demo purposes
						,onresize_end : resizeGridTable
				};
			     makepager("${random}_${searchName}_gridTable");
			     girdTableLayout = jQuery("#${random}_${searchName}_container").layout(layoutSettings);
			     jQuery("#${random}_${searchName}_layout-center").css("padding",0);
				 layoutResize(girdTableLayout);
				 jQuery("#gview_${random}_${searchName}_gridTable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
						jQuery(this).find("th").each(function(){
							//jQuery(this).css("vertical-align","middle");
							//jQuery(this).find("div").eq(0).css("margin-top","5px");
							jQuery(this).find("div").eq(0).css("line-height","18px");
						});
					});
			},'150');
		}
		
		jQuery("#${random}_${searchName}_reloadex").unbind("click").bind("click",function(){
			var urlString = "${ctx}/queryEx?searchName=${searchName}&_vKey=${_vKey}&_aKey=${_aKey}&_valueField=${_valueField}&_valueType=${_valueType}&_vKeyTitle=${_vKeyTitle}";
			
			
			
			<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
				<c:forEach items="${searchItems}" var="item">
					var ${item.htmlField} = jQuery("#${random}_${searchName}_${item.htmlField}").val();
				</c:forEach>
			</c:if>
			<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
				urlString=urlString<c:forEach items="${searchItems}" var="item">+"&${item.htmlField}="+${item.htmlField}</c:forEach>;
			</c:if>
			urlString=encodeURI(urlString);

			   try{
			//alert(urlString);
				    navTab.reload(urlString, {
						title : "New Tab",
						fresh : false,
						data : {}
					}); 
				   // jQuery("#${random}_${searchName}_gridTable").jqGrid('setGridParam',{url:urlString,page:1}).jqGrid('setGridParam', { datatype : 'json' }).trigger("reloadGrid");
				   }catch(e){
					alert(e.message)   
				   }
		});
	});
	
	
	
	
	function gridReload(){

				var urlString = "${ctx}/queryEx?searchName=${searchName}&_vKey=${_vKey}&_aKey=${_aKey}&_valueField=${_valueField}&_valueType=${_valueType}&_vKeyTitle=${_vKeyTitle}";
				
		//console.log(urlString);
		
		
		<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
			<c:forEach items="${searchItems}" var="item">
				var ${item.htmlField} = jQuery("#${random}_${searchName}_${item.htmlField}").val();
			</c:forEach>
		</c:if>
		<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
			urlString=urlString<c:forEach items="${searchItems}" var="item">+"&${item.htmlField}="+${item.htmlField}</c:forEach>;
		</c:if>
		//console.log(urlString);
		urlString=encodeURI(urlString);
		//console.log(urlString);

		   try{
		//alert(urlString);
			    navTab.reload(urlString, {
					title : "New Tab",
					fresh : false,
					data : {}
				}); 
			   // jQuery("#${random}_${searchName}_gridTable").jqGrid('setGridParam',{url:urlString,page:1}).jqGrid('setGridParam', { datatype : 'json' }).trigger("reloadGrid");
			   }catch(e){
				alert(e.message)   
			   }
	
		
		
	}
	
	
	function gridReloadSimple(){
		
		var urlString = "${ctx}/queryExListJson?searchName=${searchName}&_vKey=${_vKey}&_aKey=${_aKey}&_valueField=${_valueField}";
		var itemValue = jQuery("input[name=${random}_${searchName}_searchSimpleItem]").val();
		var itemId = jQuery("input[name=${random}_${searchName}_searchSimpleItem]").attr("id");
		itemId = itemId.replace("${random}_${searchName}_","");
		if(itemValue==null||itemValue==-1){
		}else{
			urlString=urlString+"&"+itemId+"="+itemValue;
		}
		urlString=encodeURI(urlString);
		jQuery("#${random}_${searchName}_gridTable").jqGrid('setGridParam',{url:urlString,page:1}).jqGrid('setGridParam', { datatype : 'json' }).trigger("reloadGrid");
	}
	
 	function outputEXCEL(fileName){
 		var urlC=jQuery('#${random}_${searchName}_gridTable').jqGrid('getGridParam','url');
 		var str = urlC.split("?");
		var url = "${ctx}/outputExcel?fileName="+fileName+"&"+str[1];
		//url=encodeURI(url);
		location.href=url; 
	} 
    function confirmDelete(){

        var sid = jQuery("#${random}_${searchName}_gridTable").jqGrid('getGridParam','selarrrow');
        var msgDialog = jQuery('#msgDialog');
        var confirmDialog = jQuery("#confirmDialog");
        if(sid==null || sid.length ==0){
		
				/* msgDialog.dialog('option', 'buttons', { "<fmt:message key='dialog.ok'/>": function() {
						jQuery( this ).dialog( "close" );
					} });
			jQuery('div.#msgDialog').html("<fmt:message key='list.selectNone'/>");
			msgDialog.dialog('open'); */
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
			}else{
				alertMsg.confirm("确认删除？", {
					okCall: function(){
					var url = "pub?searchName=${searchName}&id="+sid+"&actionName=delete";
					var jqxhr=jQuery.post( url, {dataType : "json"}).success(function(data){
				      	var status = data['jsonStatus'];
				    	if(status=="error"){
				    		alertMsg.error(data['jsonMessages']);
				    	}else{
				    		alertMsg.correct(data['jsonMessages']);
				    	}
					});
					}
				});
				
			
		}
	}
    
    function selectConfirmProcess(taskName,args,msg,returnSearch){
	
        var sid = jQuery("#${random}_${searchName}_gridTable").jqGrid('getGridParam','selarrrow');
        var msgDialog = jQuery('#msgDialog');
        var confirmDialog = jQuery("#confirmDialog");
        if(sid==null || sid.length ==0){

			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
			}else{
				alertMsg.confirm(msg, {
					okCall: function(){
					var url = "${ctx}/pub?searchName=${searchName}&id="+sid+"&actionName=process&taskName="+taskName+args;
					var jqxhr=jQuery.post( url, {dataType : "json"}).success(function(data){
				      	var status = data['jsonStatus'];
				    	if(status=="error"){
				    		alertMsg.error(data['jsonMessages']);
				    	}else{
				    		alertMsg.correct(data['jsonMessages']);
				    		jqxhr.complete(function(){
								jQuery("#${random}_${searchName}_gridTable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
								});
				    		//处理成功后进入指定Search
				    		returnSearchFun(returnSearch);
				    	}
					});
					}
				});				

		}
	}
    function noSelectConfirmProcess(taskName,args,msg,returnSearch){
		
        var sid = jQuery("#${random}_${searchName}_gridTable").jqGrid('getGridParam','selarrrow');
        
        alertMsg.confirm(msg, {
			okCall: function(){
			var url = "${ctx}/pub?searchName=${searchName}&id="+sid+"&actionName=process&taskName="+taskName+args;
				var jqxhr=jQuery.post( url, {dataType : "json"}).success(function(data){
		      	var status = data['jsonStatus'];
		    	if(status=="error"){
		    		alertMsg.error(data['jsonMessages']);
		    	}else{
	    			alertMsg.correct(data['jsonMessages']);
		    		jqxhr.complete(function(){
						jQuery("#${random}_${searchName}_gridTable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
					});
		    		//处理成功后进入指定Search
		    		returnSearchFun(returnSearch);
		    	}
			}); 
			}
		});		
        
	}
    function returnSearchFun(returnSearch){
		if(returnSearch!=null&&returnSearch!=""&&returnSearch!="undefined"){
			if(data['jsonMessages']=='处理成功。'){
				var winTitle = '<fmt:message key="searchPreview.title"/>';
		        navTab.openTab("",'query?searchName='+returnSearch, { title:winTitle, fresh:true, data:{} });
			}
		}
	}
    function zhuanqu(url){
    	var paramsArr = url.split("&");
    	var searchName = "";
    	var title = "";
    	for(var i=0;i<paramsArr.length;i++){
    		if(paramsArr[i].indexOf("SearchName")!=-1){
    			searchName = paramsArr[i].split("=")[1];
    		}
    	}
    	if(searchName!=null&&searchName!=""){
    		var entityName = "Search";
    		var searchItem = "searchName";
    		var searchValue = searchName;
    		$.ajax({
			    url: 'searchDictionary?entityName='+entityName+"&searchItem="+searchItem+"&searchValue="+searchValue,
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			    },
			    success: function(data){
			        // do something with xml
			        title = data.message;
			        navTab.openTab("", url, { title:title, fresh:true, data:{} });
			    }
			});
    	}else{
    		navTab.openTab("", url, { title:"明细", fresh:true, data:{} });
    	}
    }
    
    function layoutResize(jLayout){
    	jLayout.resizeAll();
    }
</script>
</head>

<div class="page">
	<div class="pageContent">
		<div id="${random}_${searchName}_container">
			<div id="${random}_${searchName}_layout-center"
				class="pane ui-layout-center" style="padding: 2px">
				<div id="${random}_${searchName}_searchDiv" class="pageHeader">
					<form onsubmit="return navTabSearch(this);" action="" method="post">
						<div class="searchBar">
							<div class="searchContent">
								<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
									<c:forEach items="${searchItems}" var="item" varStatus="it">
										<label style="float: none">${item.caption}： <c:choose>
												<c:when test="${item.userTag=='yearMonth'}">
													<input class="input-small" type="text"
														id="${random}_${searchName}_${item.htmlField}"
														onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"
														value="${item.initValueString}" />
													<%-- <appfuse:yearMonth htmlField="${item.htmlField}" cssClass="input-small"></appfuse:yearMonth> --%>
												</c:when>
												<c:when test="${item.userTag=='checkbox'}">
													<input type="checkbox"
														id="${random}_${searchName}_${item.htmlField}"
														class="input-small" />
												</c:when>
												<c:when test="${item.userTag=='stringSelect'}">
													<appfuse:stringSelect
														htmlFieldName="${random}_${searchName}_${item.htmlField}"
														paraString="${item.param1}"
														initValue="${item.initValueString}" required="false"></appfuse:stringSelect>
												</c:when>
												<c:when test="${item.userTag=='stringSelectR'}">
													<appfuse:stringSelect
														htmlFieldName="${random}_${searchName}_${item.htmlField}"
														paraString="${item.param1}"
														initValue="${item.initValueString}" required="false"></appfuse:stringSelect>
												</c:when>
												<c:when test="${item.userTag=='sqlSelect'}">
													<appfuse:sqlSelect
														htmlFieldName="${random}_${searchName}_${item.htmlField}"
														paraString="${item.param1}"
														initValue="${item.initValueString}"
														required="false"
														></appfuse:sqlSelect>
												</c:when>
												<c:when test="${item.userTag=='sqlSelectR'}">
													<appfuse:sqlSelect
														htmlFieldName="${random}_${searchName}_${item.htmlField}"
														paraString="${item.param1}"
														initValue="${item.initValueString}"
														required="true"
														></appfuse:sqlSelect>
												</c:when>
												<c:when test="${item.userTag=='dicSelect'}">
													<appfuse:dictionary code="${item.param1}"
														name="${random}_${searchName}_${item.htmlField}"
														required="false" cssClass="input-small"
														value="${item.initValueString}" />
												</c:when>
												<c:when test="${item.userTag=='deptTreeS'}">
													<input id="${random}_${searchName}_${item.htmlField}" name="${random}_${searchName}_${item.htmlField}" type="hidden" value="${item.initValueString}"/>
						 							<input id="${random}_${searchName}_${item.htmlField}Name"  readonly="readonly"/>
													<script>
														addTreeSelect("tree","sync","${random}_${searchName}_${item.htmlField}Name","${random}_${searchName}_${item.htmlField}","single",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"parentDept_id",filter:""});
													</script>
												</c:when>
												<c:when test="${item.userTag=='deptTreeM'}">
													<input id="${random}_${searchName}_${item.htmlField}"  type="hidden" value="${item.initValueString}"/>
						 							<input id="${random}_${searchName}_${item.htmlField}Namemul"  />
													<script>
														addTreeSelect("tree","sync","${random}_${searchName}_${item.htmlField}Namemul","${random}_${searchName}_${item.htmlField}","multi",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"parentDept_id",filter:""});
													</script>
												</c:when>
												<c:when test="${item.userTag=='deptFormulaSelect'}">
													<input id="${random}_${searchName}_${item.htmlField}"  type="hidden" value="${item.initValueString}"/>
						 							<input id="${random}_${searchName}_${item.htmlField}Namemul"  />
													<script>
														addTreeSelect("list","sync","${random}_${searchName}_${item.htmlField}Namemul","${random}_${searchName}_${item.htmlField}","multi",{tableName:"t_formulaField",treeId:"fieldName",treeName:"fieldTitle",parentId:"fieldTitle",filter:"formulaEntityId=2"});
													</script>
												</c:when>
												<c:when test="${item.userTag=='orgFormulaSelect'}">
													<input id="${random}_${searchName}_${item.htmlField}"  type="hidden" value="${item.initValueString}"/>
						 							<input id="${random}_${searchName}_${item.htmlField}Namemul"  />
													<script>
														addTreeSelect("list","sync","${random}_${searchName}_${item.htmlField}Namemul","${random}_${searchName}_${item.htmlField}","multi",{tableName:"t_formulaField",treeId:"fieldName",treeName:"fieldTitle",parentId:"fieldTitle",filter:"formulaEntityId=1"});
													</script>
												</c:when>

												<c:otherwise>
													<input type="text"
														id="${random}_${searchName}_${item.htmlField}"
														class="input-small" value="${item.initValueString}" />
												</c:otherwise>
											</c:choose>
										</label>
										<c:if test="${it.index==4}">
										<br/></c:if>
									</c:forEach>
								</c:if>
								<div class="buttonActive" style="float:right">
											<div class="buttonContent">
												<button type="button" id="${random}_${searchName}_reloadex">
													<fmt:message key='button.search' />
												</button>
											</div>
										</div>
							</div>
							
						</div>
					</form>
				</div>


				<div style="z-index: 1">
					<sj:dialog id="msgDialog"
						buttons="{'%{getText('dialog.ok')}':function() {jQuery( this ).dialog( 'close' ); }}"
						autoOpen="false" modal="true"
						title="%{getText('messageDialog.title')}" />
					<sj:dialog id="confirmDialog"
						buttons="{'%{getText('dialog.confirm')}':function() {jQuery( this ).dialog( 'close' ); },'%{getText('dialog.cancel')}':function() {jQuery( this ).dialog( 'close' ); }}"
						autoOpen="false" modal="true"
						title="%{getText('messageDialog.title')}" />
					<sj:dialog id="testDialog"
						buttons="{'OK':function() {jQuery( this ).dialog( 'close' ); }}"
						autoOpen="false" modal="true"
						title="%{getText('messageDialog.title')}" />


					<div class="panelBar">
						<ul class="toolBar">
						<c:forEach items="${searchUrls}" var="op">
							<li><a id="${op.searchUrlId}"
								href="javaScript:${op.realUrl}"><span style="background:url('${ctx}/styles/themes/rzlt_theme/ihos_images/icon_button/${op.icon}') ;background-repeat: no-repeat;background-position:0 4px;">${op.title} </span> </a>
							</li>
						</c:forEach>
						<c:if test="${editOptionsNum>0}">
						<li><a  class="edit" href="javaScript:fullGridEdit('#${random}_${searchName}_gridTable')"><span>编辑</span></a></li>
						<li><a  class="canceleditbutton" href="javaScript:restoreRowGrid('#${random}_${searchName}_gridTable')"><span>取消编辑</span></a></li>
						<li><a  class="savebutton" href="javaScript:saveRowGrid('#${random}_${searchName}_gridTable')"><span>保存</span></a></li>
						</c:if>
						</ul>
					</div>
					<div id="${random}_${searchName}_searchDivSimple" align="right"
						style="margin-top: -26px">
						<button onclick="gridReloadSimple()" class="btn btn-info">
							<fmt:message key='button.search' />
						</button>
						<button id="${random}_${searchName}_higherButton"
							class="btn btn-info">
							<fmt:message key='button.higher' />
						</button>
					</div>

					<div id="${random}_${searchName}_gridTable_div"
						style="margin-left: 2px; margin-top: 2px; overflow: hidden;"
						buttonBar="width:700;height:400">

						<table id="${random}_${searchName}_gridTable"></table>
						<div id="${random}_${searchName}_gridPager"></div>
					</div>
					<div class="panelBar">
						<div class="pages">
							<span>显示</span> <select
								id="${random}_${searchName}_gridTable_numPerPage">
								<option value="20">20</option>
								<option value="50">50</option>
								<option value="100">100</option>
								<option value="200">200</option>
							</select> <span>条，共<label
								id="${random}_${searchName}_gridTable_totals"></label>条
							</span>
						</div>

						<div id="${random}_${searchName}_gridTable_pagination"
							class="pagination" targetType="navTab" totalCount="200"
							numPerPage="20" pageNumShown="10" currentPage="1"></div>

					</div>
				</div>
			</div>

		</div>
	</div>
</div>
