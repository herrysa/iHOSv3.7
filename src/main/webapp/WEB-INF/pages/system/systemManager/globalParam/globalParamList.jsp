<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 

	jQuery(document).ready(function() { 
		var globalparamGrid = jQuery("#${random}globalparam_gridtable");
		globalparamGrid.jqGrid({
    		url : "globalparamGridList?subSystem=${subSystem}",
    		editurl:"globalparamGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
				{name:'paramId',index:'paramId',align:'left',label : '<s:text name="globalParam.paramId" />',hidden:true,key:true},
				{name:'paramKey',index:'paramKey',align:'left',width:'150',label : '<s:text name="globalParam.paramKey" />',hidden:false},      	
				{name:'paramName',index:'paramName',align:'left',width:'150',label : '<s:text name="globalParam.paramName" />',hidden:false},      	
				{name:'paramValue',index:'paramValue',align:'left',width:'100',label : '<s:text name="globalParam.paramValue" />',hidden:false},        	
				{name:'paramType',index:'paramType',align:'left',width:'100',label : '<s:text name="globalParam.paramType" />',hidden:false,formatter:"select",editoptions:{value : "1:业务操作;2:数据格式"}},
				{name:'selectOptions',index:'selectOptions',align:'left',width:'480',label : '<s:text name="globalParam.selectOptions" />',hidden:true},
				{name:'description',index:'description',align:'left',width:'480',label : '<s:text name="globalParam.description" />',hidden:false}
				<c:if test="${empty subSystem}">
				,{name:'subSystemId',index:'subSystemId',align:'left',width:'160',label : '<s:text name="globalParam.subSystemId" />',hidden:false}        	
				</c:if>
			],
        	jsonReader : {
				root : "globalParams", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'paramId',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="indicatorTypeList.title" />',
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
		 		gridContainerResize('${random}globalparam','div');
	           	var dataTest = {"id":"${random}globalparam_gridtable"};
	      	   	jQuery.publish("onLoadSelect",dataTest,null);
       		} 

	    });
	    jQuery(globalparamGrid).jqGrid('bindKeys');
	    jQuery("#${random}globalparam_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
	    	var winTitle='<s:text name="globalParamNew.title"/>';
			var url = "editGlobalparam?popup=true&navTabId=${random}globalparam_gridtable&subSystem=${subSystem}&random=${random}";
			$.pdialog.open(url,'addGlobalparam',winTitle, {width : 500,height : 300,maxable:false,resizable:false});
	    });
		jQuery("#${random}globalparam_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){
			var winTitle='<s:text name="globalParamEdit.title"/>';
			var url = "editGlobalparam?popup=true&navTabId=${random}globalparam_gridtable&subSystem=${subSystem}&random=${random}";
			var sid = jQuery("#${random}globalparam_gridtable").jqGrid('getGridParam','selarrrow');
			if(!sid || sid.length==0){
				alertMsg.error("请选择记录.");
				return;
			}
			if(sid.length!=1){
				alertMsg.error("只能选择一条记录.");
				return;
			}
			url += "&paramId="+sid;
			$.pdialog.open(url,'editGlobalparam',winTitle, {width : 500,height : 300,maxable:false,resizable:false});
	    });
	    
		//卡片
		jQuery("#${random}globalparam_gridtable_card_custom").unbind('click').bind('click',function() {
			var url = "globalParamCard?1=1";
			var subSystem = "${subSystem}";
			if(!subSystem) {
				subSystem = jQuery("#${random}globalParam_subSystemId").find("option:selected").val();
				if(subSystem == "薪资管理系统") {
					subSystem = "GZ";
				} else if(subSystem == "人力资源管理系统") {
					subSystem = "HR";
				}
			}
			url += "&subSystem=" + subSystem;
			var winTitle = "卡片";
			$.pdialog.open(url,"globalParamCard",winTitle,{mask:true,width : 470,height : 600,maxable:true,resizable:true});
		});
	});
	
	/* function globalparamGridReload(){
		var urlString = "globalparamGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();

		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#${random}globalparam_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	} */
	
	function addGlobalParam(){
		var winTitle='<s:text name="globalParamNew.title"/>';
		var url = "editGlobalparam?popup=true&navTabId=${random}globalparam_gridtable&subSystem=${subSystem}&random=${random}";
		$.pdialog.open(url,'addGlobalparam',winTitle, {width : 500,height : 300,maxable:false,resizable:false});
	}
	function editGlobalParam(){
		var winTitle='<s:text name="globalParamEdit.title"/>';
		var url = "editGlobalparam?popup=true&navTabId=${random}globalparam_gridtable&subSystem=${subSystem}&random=${random}";
		var sid = jQuery("#${random}globalparam_gridtable").jqGrid('getGridParam','selarrrow');
		if(!sid || sid.length==0){
			alertMsg.error("请选择记录.");
			return;
		}
		if(sid.length!=1){
			alertMsg.error("只能选择一条记录.");
			return;
		}
		url += "&paramId="+sid;
		$.pdialog.open(url,'editGlobalparam',winTitle, {width : 500,height : 300,maxable:false,resizable:false});
	}
</script>
 
<div class="page" id="${random}globalparam_page">
	<div class="pageHeader" id="${random}globalparam_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="${random}globalparam_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='globalParam.paramKey'/>:
						<input type="text" name="filter_LIKES_paramKey"/>
					</label>
					<label class="queryarea-label">
						<s:text name='globalParam.paramName'/>:
						<input type="text" name="filter_LIKES_paramName"/>
					</label>
					<label class="queryarea-label">
						<s:text name='globalParam.paramValue'/>:
						<input type="text" name="filter_LIKES_paramValue"/>
					</label>
					<label class="queryarea-label">
						<s:text name='globalParam.description'/>:
						<input type="text" name="filter_LIKES_description"/>
					</label>
					<label class="queryarea-label">
						<s:text name='globalParam.paramType'/>:
						<s:select list="#{'':'','1':'业务操作','2':'数据格式' }" name="filter_LIKES_paramType" listKey="key"
							listValue="value" emptyOption="false" theme="simple"></s:select>
					</label>
					<c:if test="${empty subSystem}">
					<label class="queryarea-label">
						<s:text name='globalParam.subSystemId'/>:
						<s:select id="%{random}globalParam_subSystemId" name="filter_LIKES_subSystemId" list="subSystems"  listKey="menuName"
							listValue="menuName" emptyOption="true" theme="simple">
						</s:select>
					</label>
					</c:if>
				<div class="buttonActive" style="float:right">
					<div class="buttonContent">
						<button type="button" onclick="propertyFilterSearch('${random}globalparam_search_form','${random}globalparam_gridtable')"><s:text name='button.search'/></button>
					</div>
				</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('${random}globalparam_search_form','${random}globalparam_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<%-- <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>
		 --%><div class="panelBar" id="${random}globalparam_buttonBar">
			<ul class="toolBar" id="${random}globalparam_toolbuttonbar">
				<li>
					<a id="${random}globalparam_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a id="${random}globalparam_gridtable_del" class="delbutton"  href="javaScript:"><span>删除</span></a>
				</li>
				<li>
					<a id="${random}globalparam_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><fmt:message key="button.edit" /></span></a>
				</li>
				<li>
					<a id="${random}globalparam_gridtable_card_custom" class="zbcomputebutton"  href="javaScript:"><span><s:text name="卡片" /></span></a>
				</li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul>
		</div>
		<div id="${random}globalparam_gridtable_div" class="grid-wrapdiv"
			buttonBar="optId:paramId;width:500;height:300">
			<input type="hidden" id="${random}globalparam_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="${random}globalparam_gridtable_addTile">
				<fmt:message key="globalParamNew.title"/>
			</label> 
			<label style="display: none"
				id="${random}globalparam_gridtable_editTile">
				<fmt:message key="globalParamEdit.title"/>
			</label>
			<label style="display: none"
				id="${random}globalparam_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="${random}globalparam_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
			<div id="load_${random}globalparam_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
			<table id="${random}globalparam_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="${random}globalparam_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}globalparam_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text
					name="pager.total" /><label id="${random}globalparam_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="${random}globalparam_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>
