<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="costItemList.title"/></title>
    <meta name="heading" content="<fmt:message key='costItemList.heading'/>"/>
    <meta name="menu" content="CostItemMenu"/>
    
    <script type="text/javascript">
	function costItemGridReload(){
		var urlString = "costItemGridList";
		var costItemNameId = jQuery("#costItemNameId").val();
		var costItemIdTxt = jQuery("#costItemIdTxt").val();
		var costUseId = jQuery("#costUseId").val();
		var clevelId = jQuery("#clevelId").val();
		var leafId = jQuery("#leafId").val();
		var disabledId = jQuery("#disabledId").val();
		urlString=urlString+"?filter_LIKES_costItemName="+costItemNameId+"&filter_LIKES_costItemId="+costItemIdTxt+"&filter_EQS_costUse.costUseId="+costUseId+"&filter_EQI_clevel="+clevelId+"&filter_EQB_leaf="+leafId+"&filter_EQB_disabled="+disabledId;
		urlString=encodeURI(urlString);
		jQuery("#costItem_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		//alert(jQuery("#disabledId").val());
	}
    	function addRecord(){
			var url = "editCostItem?popup=true";
			var winTitle='<fmt:message key="costItemNew.title"/>';
			openWindow(url, winTitle, " width=1200");
		}
		function editRecord(){
	        var sid = jQuery("#costItem_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editCostItem?popup=true&costItemId=" + sid;
				var winTitle='<fmt:message key="costItemNew.title"/>';
				openWindow(url, winTitle, " width=1200");
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
		var costItemGridIdString="#costItem_gridtable";
		jQuery(document).ready(function() { 
			var numNameSpace = "${numNameSpace}";
			numNameSpace = parseInt(numNameSpace);
			var nameSpaceUtil = "";
			for(var n=0;n<numNameSpace;n++){
				nameSpaceUtil += " ";
			}
			var initFlag_costItem = 0;
			var costItemGrid = jQuery(costItemGridIdString);
			costItemGrid.jqGrid({
				url : "costItemGridList",
				editurl : "costItemGridEdit",
				datatype : "json",
				mtype : "GET",
				colModel : [
					{name : 'costItemId',index : 'costItemId',align : 'left',width:100,label : '<s:text name="costItem.costItemId" />',hidden : false,key : true,highsearch:true},
					{name : 'costItemName',index : 'costItemName',align : 'left',width:150,label : '<s:text name="costItem.costItemName" />',hidden : false, formatter:spaceFormatter,sortable:true,highsearch:true},
					{name : 'behaviour',index : 'behaviour',align : 'center',width:70,label : '<s:text name="costItem.behaviour" />',hidden : false, sortable:true,highsearch:true},
					{name : 'cnCode',index : 'cnCode',align : 'left',width:70,label : '<s:text name="costItem.cnCode" />',hidden : false, sortable:true,highsearch:true},
					{name : 'clevel',index : 'clevel',align : 'center',width:50,label : '<s:text name="costItem.clevel" />',hidden : false, sortable:true,highsearch:true},
					{name : 'parent.costItemName',index : 'parent.costItemName',align : 'left',width:100,label : '<s:text name="costItem.parentName" />',hidden : false, sortable:true,highsearch:true},
					{name : 'medOrLee',index : 'medOrLee',align : 'center',width:80,label : '<s:text name="costItem.medOrLee" />',hidden : false, sortable:true,highsearch:true},
					{name : 'leaf',index : 'leaf',align : 'center',width:50,label : '<s:text name="costItem.leaf" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
					{name : 'costUse.costUseName',index : 'costUse.costUseName',align : 'center',width:100,label : '<s:text name="costUse.costUseName" />',hidden : false, sortable:true,highsearch:true},
					{name : 'userDefine1',index : 'userDefine1',align : 'left',width:80,label : '<s:text name="costItem.userDefine1" />',hidden : false, sortable:true,highsearch:true},
					{name : 'userDefine2',index : 'userDefine2',align : 'center',width:70,label : '<s:text name="costItem.userDefine2" />',hidden : false, sortable:true,highsearch:true},
					{name : 'userDefine3',index : 'userDefine3',align : 'center',width:70,label : '<s:text name="costItem.userDefine3" />',hidden : false, sortable:true,highsearch:true}, 
					{name : 'disabled',index : 'disabled',align : 'center',width:50,label : '<s:text name="costItem.disabled" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true}
					/* {name : 'controllable',index : 'controllable',align : 'center',width:70,label : '<s:text name="costItem.controllable" />',hidden : false, sortable:true,edittype:'checkbox',formatter:'checkbox'},
					{name : 'costDesc',index : 'costDesc',align : 'center',width:70,label : '<s:text name="costItem.costDesc" />',hidden : false, sortable:true}, */
				],
				jsonReader : {
					root : "costItems", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				},
				sortname : 'costItemId',
				viewrecords : true,
				sortorder : 'asc',
				height : 300,
				gridview : true,
				rownumbers : true,
				loadui : "disable",
				multiselect : true,
				multiboxonly : true,
				shrinkToFit : true,
				autowidth : true,
				onSelectRow : function(rowid) {

				},
				gridComplete : function() {
					
					gridContainerResize("costItem","div");
					var dataTest = {
						"id" : "costItem_gridtable"
					};
					jQuery.publish("onLoadSelect",
							dataTest, null);
					initFlag_costItem = initColumn('costItem_gridtable','com.huge.ihos.inout.model.CostItem',initFlag_costItem);
				}
    		}); 
			jQuery(costItemGrid).jqGrid('bindKeys');
			
			function spaceFormatter (cellvalue, options, rowObject)	{
				if(!cellvalue){
					return "";
				}
				try{
					var clevel = rowObject['clevel'];
					clevel = parseInt(clevel);
					var addSpace = "";
					for(var le=1;le<clevel;le++){
						addSpace += nameSpaceUtil;
					}
					cellvalue = addSpace+cellvalue;
				}catch(err){
					return cellvalue;
				}
				return cellvalue;
			}

		});
	</script>
</head>

<div class="page" id="costItem_page">
<!-- <div id="costItem_container">
			<div id="costItem_layout-center"
				class="pane ui-layout-center"> -->
	<div id="costItem_pageHeader" class="pageHeader">
		<form onsubmit="return navTabSearch(this);" action="userGridList"
			method="post">
			<div class="searchBar">
				<div class="searchContent">
						<label><fmt:message key='costItem.costItemName'/>：
							<input type="text" class="input-small" id="costItemNameId">
						</label>&nbsp;&nbsp;
						<label><fmt:message key='costItem.costItemId'/>：
						 	<input type="text" class="input-small" id="costItemIdTxt">
						 </label>&nbsp;&nbsp;
						<label><fmt:message key='costUse.costUseName'/>：
						 <s:select theme="simple" cssClass="input-small"  id="costUseId"   maxlength="20" list="allCostUseList"   listKey="costUseId" listValue="costUseName" emptyOption="true"></s:select>
						</label>&nbsp;&nbsp;
						<label><fmt:message key='costItem.clevel'/>：<input type="text" size="5" id="clevelId">
						</label>&nbsp;&nbsp;
						<label><fmt:message key='costItem.leaf'/>：<appfuse:singleSelect htmlFieldName="leafId" paraDisString="否;是" paraValueString="false;true" cssClass="input-small"></appfuse:singleSelect>
						</label>&nbsp;&nbsp;
						
						<label><fmt:message key='costItem.disabled'/>：<appfuse:singleSelect htmlFieldName="disabledId" paraDisString="否;是" paraValueString="false;true" cssClass="input-small"></appfuse:singleSelect>
						</label>
						
						<div class="buttonActive" style="float:right">
								<div class="buttonContent">
									<button type="button" onclick="costItemGridReload()"><s:text name='button.search'/></button>
								</div>
							</div>
				</div>
				<!-- <div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="costItemGridReload()">查询</button>
								</div>
							</div></li>
						<li><a class="button" href="demo_page6.html" target="dialog"
							rel="dlg_page1" title="查询框"><span>高级检索</span>
						</a>
						</li>
					</ul>
				</div> -->
			</div>
		</form>
	</div>
	<div class="pageContent">

 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="costItemGridEdit"/> 
<s:url id="remoteurl" action="costItemGridList"/> 
		<div class="panelBar" id="costItem_buttonBar">
			<ul class="toolBar">
				<li><a id="costItem_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="costItem_gridtable_del" class="delbutton"  href="javaScript:"><span>删除</span>
				</a>
				</li>
				<li><a id="costItem_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><fmt:message key="button.edit" />
					</span>
				</a>
				</li>
				<li>
					<a  class="delbutton"  href="javaScript:setColShow('costItem_gridtable','com.huge.ihos.inout.model.CostItem')"><span><s:text name="button.setColShow" /></span></a>
				</li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul>
		</div>
		<div id="costItem_gridtable_div"
			class="grid-wrapdiv"
			buttonBar="width:730;height:400">
			<input type="hidden" id="costItem_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="costItem_gridtable_addTile">
				<fmt:message key="costItemNew.title"/>
			</label> 
			<label style="display: none"
				id="costItem_gridtable_editTile">
				<fmt:message key="costItemEdit.title"/>
			</label>
			<label style="display: none"
				id="costItem_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="costItem_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
			<div id="load_costItem_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
			 <table id="costItem_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="costItem_pageBar">
		<div class="pages">
			<span>显示</span> <select id="costItem_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="costItem_gridtable_totals"></label>条</span>
		</div>

		<div id="costItem_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	</div>
	<!-- </div>
</div> -->