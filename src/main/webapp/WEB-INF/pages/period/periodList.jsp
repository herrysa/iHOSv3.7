<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%-- <%@ include file="/common/links.jsp"%> --%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="periodList.title"/></title>
    <meta name="heading" content="<fmt:message key='periodList.heading'/>"/>
    <meta name="menu" content="PeriodMenu"/>
    
    <script type="text/javascript">
    	function addRecord(){
			var url = "editPeriod?popup=true";
			var winTitle='<fmt:message key="periodNew.title"/>';
			openWindow(url, winTitle, " width=850");
		}
		function editRecord(){
	        var sid = jQuery("#period_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editPeriod?popup=true&periodId=" + sid;
				var winTitle='<fmt:message key="periodNew.title"/>';
				openWindow(url, winTitle, " width=850");
			}
		}

		function setCurrentPeriod(){
	        var sid = jQuery("#period_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
				//alert("<fmt:message key='list.selectNone'/>");
				//jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectNone'/>");
				//jQuery('#mybuttondialog').dialog('open');
				alertMsg.error("<fmt:message key='list.selectNone'/>");
				return;
				}
	        if(sid.length>1){
				  //alert("<fmt:message key='list.selectMore'/>");
			  //jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectMore'/>");
			  //jQuery('#mybuttondialog').dialog('open');
			  alertMsg.error("<fmt:message key='list.selectMore'/>");
				return;
			  }else{

				var url = "setCurrentPeriod?periodId=" + sid+"&navTabId=period_gridtable";

				// window.location = url;
				jQuery.ajax({
				    url: url,
				    type: 'post',
				    dataType: 'json',
				    aysnc:false,
				    error: function(data){
				        //jQuery('#name').attr("value",data.responseText);
				    },
				    success: function(data){
				        // do something with xml
				    	formCallBackForPerid(data);
				    }
				});
			}
		}
		function openDataColl(){
		    var sid = jQuery("#period_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
					alertMsg.error("<fmt:message key='list.selectNone'/>");
					return;
					}
		        if(sid.length>1){
				  alertMsg.error("<fmt:message key='list.selectMore'/>");
					return;
				  }else{
				var url = "openDataPeriod?periodId=" + sid+"&navTabId=period_gridtable";
				//window.location = url;
				jQuery.ajax({
				    url: url,
				    type: 'post',
				    dataType: 'json',
				    aysnc:false,
				    error: function(data){
				        //jQuery('#name').attr("value",data.responseText);
				    },
				    success: function(data){
				        // do something with xml
				    	formCallBack(data);
				    }
				});
				  }
				/* navTab.reload(url, {
					title : "New Tab",
					fresh : false,
					data : {}
				}); */

		}
		function closeDataColl(){
		    var sid = jQuery("#period_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
					alertMsg.error("<fmt:message key='list.selectNone'/>");
					return;
					}
		        if(sid.length>1){
				  alertMsg.error("<fmt:message key='list.selectMore'/>");
					return;
				  }else{
			var url = "closeDataPeriod?periodId=" + sid+"&navTabId=period_gridtable";
			//window.location = url;
			/* navTab.reload(url, {
				title : "New Tab",
				fresh : false,
				data : {}
			}); */
			jQuery.ajax({
			    url: url,
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			        //jQuery('#name').attr("value",data.responseText);
			    },
			    success: function(data){
			        // do something with xml
			    	formCallBack(data);
			    }
			});
				  }

	}
	    function okButton(){
	    	 jQuery('#mybuttondialog').dialog('close');
	    };		
		
		function checkGridOperation(response,postdata){
			 var gridresponse = gridresponse || {};
			    gridresponse = jQuery.parseJSON(response.responseText);
			    var msg = gridresponse["gridOperationMessage"];
			    //alert(msg);
			   //jQuery("#gridinfo").html(msg);
			   jQuery('div.#mybuttondialog').html(msg);
				  jQuery('#mybuttondialog').dialog('open');
		        return [true,""];    
		}
    
		datePick = function(elem){
		        jQuery(elem).datepicker({dateFormat:"<fmt:message key='datepicker.format'/>"});
		        jQuery('#ui-datepicker-div').css("z-index", 2000);
		};
		var periodLayout;
		jQuery(document).ready(function() { 
			/* jQuery.subscribe('rowselect', function(event,data) {
		    	jQuery("#gridinfo").html('<p>Edit Mode for Row : '+event.originalEvent.id+'</p>');
		    }); */
			
			jQuery.subscribe('handleJsonResult', function(event,data) {
				//jQuery('#result').html("<ul id='languagesList'></ul>");
		        //var list = jQuery('#languagesList');
		        //jQuery.each(event.originalEvent.data, function(index, value) { 
		        //                list.append('<li>'+value+'</li>\n');
		        //        });
		        var msg = event.originalEvent.data["gridOperationMessage"];
		        //list.append('<li>'+event.originalEvent.data["gridOperationMessage"]+'</li>\n');
		    	jQuery('div.#mybuttondialog').html(msg);
				jQuery('#mybuttondialog').dialog('open');
				return;
		        
		    });
		    
		   /*  periodLayout = makeLayout({
				baseName: 'period', 
				tableIds: 'period_gridtable'
			}, null);
		    periodLayout.resizeAll(); */
    	});
		function formCallBackForPerid(json){
			DWZ.ajaxDone(json);
			if (json.statusCode == DWZ.statusCode.ok){
				jQuery('#curPeriodInMain').text("核算期间:"+json.currentPeriod);
				if (json.navTabId){
					//navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
					jQuery('#'+json.navTabId).trigger("reloadGrid");
				} else if (json.rel) {
					navTabPageBreak({}, json.rel);
				}
				if ("closeCurrent" == json.callbackType) {
					$.pdialog.closeCurrent();
				}
			}
		}
	</script>
</head>


<div class="page">
	<!-- <div class="pageContent">
<div id="period_container">
			<div id="period_layout-center"
				class="pane ui-layout-center"> -->

 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="periodGridEdit"/> 
<s:url id="remoteurl" action="periodGridList"/> 
	<s:url id="searchBooleanSelectList" action="searchSelectBooleanList?dicCode=radioOpenClose"	namespace="/system">
		
	</s:url>


<div class="panelBar">
			<ul class="toolBar">
				<li><a id="period_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="period_gridtable_del" class="delbutton"  href="javaScript:"><span>删除</span>
				</a>
				</li>
				<li><a id="period_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><fmt:message key="button.edit" />
					</span>
				</a>
				</li>
				<%--
				<li><a  class="edit"  href="javaScript:setCurrentPeriod();"
					><span><fmt:message key="period.setCurrent" />
					</span>
				</a>
				</li>
				 <li><a id="set_current_button" class="edit"  href="javaScript:setCurrentPeriod();"
					><span><fmt:message key="period.openDataCollection" />
					</span>
				</a>
				</li> --%>
				<%-- <li>
				<li><a id="open_data_coll_button" class="edit"  href="javaScript:openDataColl();"
					><span><fmt:message key="period.openDataCollection" />
					</span>
				</a>
				</li>
				<li><a id="close_data_coll_button" class="edit"  href="javaScript:closeDataColl();"
					><span><fmt:message key="period.closeDataCollection" />
					</span>
				</a>
				</li>
					<s:url id="jsonurl" action="closeDataPeriod"/> 
					<a id="jsonurl" class="edit" target="ajaxTodo" href="%{jsonurl}"
					><span><fmt:message key="period.closeDataCollection" />
					</span>
				</a>
				</li> --%>
				
        
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul>
		</div>
		<div id="period_gridtable_div" layoutH="57"
			class="grid-wrapdiv"
			buttonBar="width:550;height:350">
			<input type="hidden" id="period_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="period_gridtable_addTile">
				<fmt:message key="periodNew.title"/>
			</label> 
			<label style="display: none"
				id="period_gridtable_editTile">
				<fmt:message key="periodEdit.title"/>
			</label>
			<label style="display: none"
				id="period_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="period_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
			
<div id="load_period_gridtable" class='loading ui-state-default ui-state-active'></div>
<sjg:grid 
    	id="period_gridtable" 
    	dataType="json" 
    	gridModel="periods"

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
		navigatorDeleteOptions="{reloadAfterSubmit:true,afterSubmit:checkGridOperation,left:screen.availWidth/4, top:screen.availHeight/4}"   
        navigatorSearch="false"
        navigatorSearchOptions="{multipleSearch:true,  showQuery: true,left:screen.availWidth/4, top:screen.availHeight/4}"
 		navigatorRefresh="false"

    	multiselect="true"
  		multiboxonly="true"
  		
  		resizable="true"
  		
  		onSelectRowTopics="rowselect"
  		
  		draggable="true"
	autowidth="true"
	onCompleteTopics="onLoadSelect"
	sortname="periodCode"
	sortorder="asc"
	    >
    <sjg:gridColumn 
    	    name="periodId" 
    	    search="false" 
    	    index="periodId" 
    	    title="%{getText('period.periodId')}" 
    	    hidden="true"
    	    formatter="integer" 
    	    sortable="false"
    	    editable="true" 
    	    key="true"
    	    />
       <sjg:gridColumn 
 	    name="periodCode" 
 	    index="periodCode" 
 	    title="%{getText('period.periodCode')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />
 	       <sjg:gridColumn 
 	    name="cyear" 
 	    index="cyear" 
 	    title="%{getText('period.cyear')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />
 	       <sjg:gridColumn 
 	    name="cmonth" 
 	    index="cmonth" 
 	    title="%{getText('period.cmonth')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />
 	      <sjg:gridColumn 
    	name="startDate" 
	    index="startDate" 
	    title="%{getText('period.startDate')}" 
	    sortable="true"
	    editable="true" 
      editoptions="{ dataInit:datePick}"
      formatter="date"
      formatoptions="{newformat : '%{datePattern}'}"
      width="90"
      search="true"
      searchoptions="{sopt:['eq','ne','lt','gt'], dataInit:datePick}"
      editrules="{required: true}"/> 
      
    <sjg:gridColumn 
    	name="endDate" 
	    index="endDate" 
	    title="%{getText('period.endDate')}" 
	    sortable="true"
	    editable="true" 
      editoptions="{ dataInit:datePick}"
      formatter="date"
      formatoptions="{newformat : '%{datePattern}'}"
      width="90"
      search="true"
      searchoptions="{sopt:['eq','ne','lt','gt'], dataInit:datePick}"
      editrules="{required: true}"/> 
      
 
    <sjg:gridColumn 
    	name="cpFlag" 
	    index="cpFlag" 
	    title="%{getText('period.cpFlag')}" 
	    sortable="true"
	    editable="true" 
      edittype="checkbox"
      formatter="checkbox"
      hidden="true"
      width="90"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"/> 
    <sjg:gridColumn 
    	name="cdpFlag" 
	    index="cdpFlag" 
	    title="%{getText('period.cdpFlag')}" 
	    sortable="true"
	    hidden="true"
      formatter="checkbox"
      width="90"
      search="true"
      searchoptions="{sopt:['eq','ne'], stype:'select', dataUrl:'%{searchBooleanSelectList}'}"
/> 
       



   <sjg:gridColumn 
 	    name="note" 
 	    index="note" 
 	    title="%{getText('period.note')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />

  
  </sjg:grid>
</div>
	
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select id="period_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select>
			<span>条，共<label id="period_gridtable_totals"></label>条</span>
		</div>
		
		<div id="period_gridtable_pagination" class="pagination" targetType="navTab" totalCount="200" numPerPage="20" pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<!-- </div>
</div></div> -->