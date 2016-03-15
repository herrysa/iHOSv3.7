<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="chargeItemList.title"/></title>
    <meta name="heading" content="<fmt:message key='chargeItemList.heading'/>"/>
    <meta name="menu" content="ChargeItemMenu"/>
    
    <script type="text/javascript">
    function refreshGridCurrentPage(){
    	var jq = jQuery('#chargeItem_gridtable');
        var currentPage = jq.jqGrid('getGridParam', 'page');  
    	jQuery('#chargeItem_gridtable').trigger('reloadGrid', [{page: currentPage }]);
    }
    	function addRecord(){
			var url = "editChargeItem?popup=true";
			var winTitle='<fmt:message key="chargeItemNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editChargeItemRecord(){
	        var sid = jQuery("#chargeItem_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
	        	alertMsg.error("请选择记录。");
				return;
			}
	        if(sid.length>1){
	        	alertMsg.error("只能选择一条记录。");
				return;
			}else{
				sid = sid[0].replace(/\&/g,"%26");
				var url = "editChargeItem?popup=true&navTabId=chargeItem_gridtable&chargeItemId=" + sid;
				var winTitle='<fmt:message key="chargeItemNew.title"/>';
				$.pdialog.open(url, 'editChargeItem', winTitle, {mask:false,width:500,height:300});　
			}
		}
		
		function delChargeItemRecords(){
			 var sid = jQuery("#chargeItem_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
		        	alertMsg.error("请选择记录。");
					return;
				}else{
					for(var i=0;i<sid.length;i++){
						sid[i] = sid[i].replace(/\&/g,"%26");
					}
					var editUrl = "chargeItemGridEdit?oper=del&navTabId=chargeItem_gridtable&id=" + sid;
					alertMsg.confirm("确认删除？", {
						okCall: function(){
							jQuery.post(editUrl, {
							}, formCallBack, "json");
							
						}
					});
				}
		}
	    function okButton(){
	    	 jQuery('#mybuttondialog').dialog('close');
	    };		
		datePick = function(elem){
		        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
		        jQuery('#ui-datepicker-div').css("z-index", 2000);
		};
		function chargeItemGridReload(){
			var urlString = "chargeItemGridList";
			var chargeItemIdTxt = jQuery("#chargeItemIdTxt").val();
			if(chargeItemIdTxt){
				chargeItemIdTxt = chargeItemIdTxt.replace(/\&/g,"%26");
			}
			var chargeItemNameTxt = jQuery("#chargeItemNameTxt").val();
			var chargeItem_chargeTypeName = jQuery("#chargeItem_chargeTypeName").val();
			var priceFrom = jQuery("#priceFrom").val();
			var priceTo = jQuery("#priceTo").val();
			var chargeItem_disabled = jQuery("#chargeItem_disabled").val();
			//var chargeTypeCnCodeTxt = jQuery("#chargeTypeCnCodeTxt").val();

			urlString=urlString+"?filter_LIKES_chargeItemId="+chargeItemIdTxt+"&filter_LIKES_chargeItemName="+chargeItemNameTxt
					+"&filter_LIKES_chargeType.chargeTypeName="+chargeItem_chargeTypeName
					+"&filter_GEG_price="+priceFrom
					+"&filter_LEG_price="+priceTo
					+"&filter_EQB_disabled="+chargeItem_disabled;
			urlString=encodeURI(urlString);
			jQuery("#chargeItem_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		}


		jQuery(document).ready(function() {
			
			var initChargeItemFlag = 0;
			var chargeItemGrid = jQuery("#chargeItem_gridtable");
			chargeItemGrid.jqGrid({
				url : "chargeItemGridList",
				editurl:"chargeItemGridEdit",
				datatype : "json",
				mtype : "GET",
				colModel:[
					{name:'chargeItemNo',index:'chargeItemNo',align:'center',label : '<s:text name="chargeItem.chargeItemNo" />',hidden:true},				
					{name:'chargeItemId',index:'chargeItemId',align:'left',width:"336",label : '<s:text name="chargeItem.chargeItemId" />',sortable:true,hidden:false,highsearch:true,key:true},				
					{name:'chargeType.chargeTypeName',index:'chargeType.chargeTypeName',width:"170",align:'left',label : '<s:text name="chargeType.chargeTypeName" />',hidden:false,highsearch:false},				
					{name:'chargeItemName',index:'chargeItemName',align:'left',width:"336",label : '<s:text name="chargeItem.chargeItemName" />',sortable:true,hidden:false,highsearch:true},				
					{name:'specification',index:'specification',align:'left',width:"170",label : '<s:text name="chargeItem.specification" />',sortable:true,hidden:false,highsearch:true},				
					{name:'unit',index:'unit',align:'left',width:"175",label : '<s:text name="chargeItem.unit" />',sortable:true,hidden:false,highsearch:true},				
					{name:'price',index:'price',align:'right',width:"175",label : '<s:text name="chargeItem.price" />',sortable:true,hidden:false,highsearch:true},				
					{name:'pyCode',index:'pyCode',align:'left',width:"175",label : '<s:text name="chargeItem.pyCode" />',sortable:true,hidden:false,highsearch:true},				
					{name:'disabled',index:'disabled',align:'center',width:"60",label : '<s:text name="chargeItem.disabled" />',hidden:false,highsearch:true,edittype:"checkbox",formatter:"checkbox"}				
					
				],
				jsonReader : {
					root : "chargeItems", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				},
				sortname: 'chargeItemNo',
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
					 
					 gridContainerResize("chargeItem","div");
		           var dataTest = {"id":"chargeItem_gridtable"};
		      	   jQuery.publish("onLoadSelect",dataTest,null);
		      	   makepager("chargeItem_gridtable");
		      	 initChargeItemFlag = initColumn('chargeItem_gridtable','com.huge.ihos.inout.model.ChargeItem',initChargeItemFlag);
		       	} 
		    });
		    jQuery(chargeItemGrid).jqGrid('bindKeys');
		});
	</script>
</head>

<div class="page">
<!-- <div id="chargeItem_container">
			<div id="chargeItem_layout-center"
				class="pane ui-layout-center"> -->
	<div id="chargeItem_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="chargeItem_search_form" class="queryarea-form">
					<label class="queryarea-label" >
						<fmt:message key='chargeItem.chargeItemId'/>:
						<input type="text"	id="chargeItemIdTxt" class="input-small">
					</label>
					<label class="queryarea-label" >
						<fmt:message key='chargeItem.chargeItemName'/>:
						<input type="text"	id="chargeItemNameTxt" class="input-small">
					</label>
					<label class="queryarea-label" >
						<fmt:message key='chargeType.chargeTypeName'/>:
						<input type="text"	id="chargeItem_chargeTypeName" class="input-small">
					</label>
					<label class="queryarea-label" >
						<fmt:message key='chargeItem.price'/>:
						<input type="text" id="priceFrom" class="input-mini" size="8"/>-<input type="text" size="8" id="priceTo" class="input-mini" />
					</label>
					<label class="queryarea-label" >
						<fmt:message key='chargeItem.disabled'/>:
						<appfuse:singleSelect htmlFieldName="chargeItem_disabled" paraDisString="否;是" paraValueString="false;true" cssClass="input-small"></appfuse:singleSelect>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="chargeItemGridReload()"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
			<%-- <div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="chargeItemGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
					</li>
				</ul>
			</div> --%>
		</div>
		<%-- <form onsubmit="return navTabSearch(this);" action="userGridList" method="post">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td><fmt:message key='chargeItem.chargeItemId'/>：
							<input type="text"	id="chargeItemIdTxt" class="input-small">
						</td>
						<td><fmt:message key='chargeItem.chargeItemName'/>：
						 	<input type="text"	id="chargeItemNameTxt" class="input-small">
						 </td>
						<!-- <td><fmt:message key='chargeItem.chargeItemName'/>：
						 	<input type="text"		id="chargeTypeCnCodeTxt" class="input-small">
						 </td> -->
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="chargeItemGridReload()">查询</button>
								</div>
							</div></li>
						<!-- <li><a class="button" href="demo_page6.html" target="dialog"
							rel="dlg_page1" title="查询框"><span>高级检索</span>
						</a>
						</li> -->
					</ul>
				</div>
			</div>
		</form> --%>
	</div>
	<div class="pageContent">



<%--  <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/> --%>

<%-- <s:url id="editurl" action="chargeItemGridEdit"/> 
<s:url id="remoteurl" action="chargeItemGridList"/>  --%>

<div class="panelBar" id="chargeItem_buttonBar">
			<ul class="toolBar">
				<li><a id="chargeItem_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="chargeItem_gridtable_del_custom" class="delbutton"  href="javaScript:delChargeItemRecords()"><span>删除</span>
				</a>
				</li>
				<li><a id="chargeItem_gridtable_edit_custom" class="changebutton"  href="javaScript:editChargeItemRecord()"
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
		<div id="chargeItem_gridtable_div" 
			class="grid-wrapdiv"
			buttonBar="optId:chargeItemNo;width:500;height:300">
			<input type="hidden" id="chargeItem_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="chargeItem_gridtable_addTile">
				<fmt:message key="chargeItemNew.title"/>
			</label> 
			<label style="display: none"
				id="chargeItem_gridtable_editTile">
				<fmt:message key="chargeItemEdit.title"/>
			</label>
			<label style="display: none"
				id="chargeItem_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="chargeItem_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
<div id="load_chargeItem_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
<table id="chargeItem_gridtable"></table>

</div>
	</div>
	<div class="panelBar" id="chargeItem_pageBar">
		<div class="pages">
			<span>显示</span> <select id="chargeItem_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="chargeItem_gridtable_totals"></label>条</span>
		</div>

		<div id="chargeItem_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
<!-- </div>
</div> -->