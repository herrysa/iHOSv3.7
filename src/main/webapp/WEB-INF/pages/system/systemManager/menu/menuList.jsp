<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
function menuGridReload(){
	var urlString = "menuGridList";
	 var menuIdC = jQuery("#menuIdC").val();
	 var menuNameC = jQuery("#menuNameC").val();
	 var subSystemC = jQuery("#subSystemC").val();

	urlString=urlString+"?filter_LIKES_menuId="+menuIdC+"&filter_LIKES_menuName="+menuNameC+"&filter_LIKES_subSystem.menuName="+subSystemC;
	urlString=encodeURI(urlString);
	jQuery("#menu_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
	//alert(urlString);
}
var menuGridIdString="#menu_gridtable";
jQuery(document).ready(function(){
	var initFlag = 0;
	var menuGrid = jQuery(menuGridIdString);
	menuGrid.jqGrid({
		url : "menuGridList",
		editurl:"menuGridEdit",
		datatype : "json",
		mtype : "GET",
    	colModel:[
{name:'menuId',index:'menuId',align:'left',width:'120px',label : '<s:text name="menu.menuId" />',hidden:false,key:true,highsearch:true},
{name:'menuName',index:'menuName',align:'left',width:'180px',label : '<s:text name="menu.menuName" />',hidden:false,highsearch:true},
{name:'menuAction',index:'menuAction',align:'left',width:'240px',label : '<s:text name="menu.menuAction" />',hidden:false,highsearch:true},
{name:'menuLevel',index:'menuLevel',align:'center',width:'80px',label : '<s:text name="menu.menuLevel" />',hidden:false,highsearch:true},
{name:'displayOrder',index:'displayOrder',align:'center',width:'80px',label : '<s:text name="menu.displayOrder" />',hidden:false,highsearch:true},
{name:'leaf',index:'leaf',align:'center',width:'50px',label : '<s:text name="menu.leaf" />',hidden:false,formatter:"checkbox",highsearch:true},
{name:'disabled',index:'disabled',align:'center',width:'50px',label : '<s:text name="menu.disabled" />',hidden:false,formatter:"checkbox",highsearch:true},
{name:'entityName',index:'entityName',align:'left',width:'250px',label : '<s:text name="menu.entityName" />',hidden:false,highsearch:true}
],
    	jsonReader : {
			root : "menus", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
			// (4)
		},
    	sortname: 'menuId',
    	viewrecords: true,
    	sortorder: 'asc',
    	//caption:'<s:text name="menuList.title" />',
    	height:300,
    	gridview:true,
    	rownumbers:true,
    	loadui: "disable",
    	multiselect: true,
		multiboxonly:true,
		shrinkToFit:false,
		autowidth:false,
    	onSelectRow: function(rowid) {
   		},
	 	gridComplete:function(){
			gridContainerResize('menu','div');
	 		var rowNum = jQuery(this).getDataIDs().length;
            if(rowNum<=0){
            	var tw = jQuery(this).outerWidth();
				jQuery(this).parent().width(tw);
				jQuery(this).parent().height(1);
            }else{
            }
       	var dataTest = {"id":"menu_gridtable"};
  	   	jQuery.publish("onLoadSelect",dataTest,null);
  	  initFlag = initColumn('menu_gridtable','com.huge.ihos.system.systemManager.menu.model.Menu',initFlag);
  	   	//makepager("menu_gridtable");
   		} 

	});
jQuery(menuGrid).jqGrid('bindKeys');

//===============工具栏=======================
 jQuery("#menu_gridtable_add_custom").click(function(){
		var url = "editMenu?popup=true&navTabId=menu_gridtable";
		var winTitle = '<s:text name="menuNew.title"/>';
		url = encodeURI(url);
    	$.pdialog.open(url,'editMenu',winTitle, {mask:true,width : 650,height : 350,maxable:true,resizable:true});  
 });
 jQuery("#menu_gridtable_edit_custom").click(function(){
		var sid = jQuery("#menu_gridtable").jqGrid('getGridParam','selarrrow');
		if(sid==null || sid.length != 1){
			alertMsg.error("请选择一条记录！");
			return;
		}
    	var url = "editMenu?menuId="+sid+"&popup=true&navTabId=menu_gridtable";
    	url = encodeURI(url);
    	var winTitle = '<s:text name="menuEdit.title"/>';
    	$.pdialog.open(url,'editMenu',winTitle, {mask:true,width : 650,height : 350,maxable:true,resizable:true});    
});
});
// 	function addRecord(){
// 		var url = "editMenu?popup=true";
// 		var winTitle='<fmt:message key="menuNew.title"/>';
// 		popUpWindow(url, winTitle, "width=500");
// 	}
	function displayTree(){
		var url = "menuTreetree?popup=true";
		var winTitle='menuTree';
		popUpWindow(url, winTitle, "width=500");
	}
// 	function editRecord(){
//         var sid = jQuery("#menu_gridtable").jqGrid('getGridParam','selarrrow');
//         if(sid==null || sid.length ==0){
// 			//alert("<fmt:message key='list.selectNone'/>");
// 			jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectNone'/>");
// 			jQuery('#mybuttondialog').dialog('open');
// 			return;
// 			}
//         if(sid.length>1){
// 			  //alert("<fmt:message key='list.selectMore'/>");
// 		  jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectMore'/>");
// 		  jQuery('#mybuttondialog').dialog('open');
// 			return;
// 		  }else{
//          jQuery("#gridinfo").html('<p>Loading..... ID : '+sid+'</p>');
// 			var url = "editMenu?popup=true&menuId=" + sid;
// 			var winTitle='<fmt:message key="menuNew.title"/>';
// 			popUpWindow(url, winTitle, "width=500");
// 		}
// 	}
    function okButton(){
    	 jQuery('#mybuttondialog').dialog('close');
    };		
	datePick = function(elem){
	        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
	        jQuery('#ui-datepicker-div').css("z-index", 2000);
	};
	function setModuleFlag(){
		//var userid = "<s:property value='#parameters.id'/>";
		var url = "moduleFlag";
		$.pdialog.open(url, "moduleFlag", "设置模块标识", {mask:true,height:600});　
	}
</script>

<div class="page" id="menu_page">
	<div class="pageHeader" id="menu_pageHeader">
	<div class="searchBar">
				<div class="searchContent">
		<form id="menu_search_form" class="queryarea-form">
			<label class="queryarea-label">
				<s:text name='menu.menuId'/>:
				<input type="text" id="menuIdC"/>
			</label>
			<label class="queryarea-label">
				<s:text name='menu.menuName'/>:
				<input type="text" id="menuNameC"/>
			</label>
			<label class="queryarea-label">
				<s:text name='menu.subSystem'/>:
				<s:select name="subSystemC" id="subSystemC"  maxlength="20"
							list="subSystems"  listKey="menuName"
							listValue="menuName" emptyOption="true" theme="simple"></s:select>
			</label>
			<div class="buttonActive" style="float:right">
				<div class="buttonContent">
					<button type="button" onclick="menuGridReload()"><s:text name='button.search'/></button>
				</div>
			</div>
		</form>
	</div>
	</div>
	</div>
	<div class="pageContent">
			<div class="panelBar" id="menu_buttonBar">
			<ul class="toolBar" id="menu_toolbuttonbar">
				<li><a id="menu_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="menu_gridtable_del" class="delbutton"  href="javaScript:"><span>删除</span>
				</a>
				</li>
				<li><a id="menu_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><fmt:message key="button.edit" />
					</span>
				</a>
				</li>
				<li>
     				<a class="settlebutton"  href="javaScript:setColShow('menu_gridtable','com.huge.ihos.system.systemManager.menu.model.Menu')"><span><s:text name="button.setColShow" /></span></a>
   				 </li>
				<!-- <li><a class="changebutton"  href="javaScript:setModuleFlag()"
					><span>设置模块标识
					</span>
				</a>
				</li> -->
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul>
		</div>
		<div id="menu_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="menu_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="menu_gridtable_addTile">
				<s:text name="menuNew.title"/>
			</label> 
			<label style="display: none"
				id="menu_gridtable_editTile">
				<s:text name="menuEdit.title"/>
			</label>
			<div id="load_menu_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="menu_gridtable"></table>
			<!--<div id="menuPager"></div>-->
		</div>
	</div>
	<div class="panelBar" id="menu_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="menu_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="menu_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="menu_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>