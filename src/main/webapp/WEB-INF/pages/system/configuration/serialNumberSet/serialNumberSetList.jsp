
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var serialNumberSetGridIdString="#${random}_serialNumberSet_gridtable";
	
	jQuery(document).ready(function() { 
		var serialNumberSetGrid = jQuery(serialNumberSetGridIdString);
    	serialNumberSetGrid.jqGrid({
	    	url : "serialNumberSetGridList?subSystem=${subSystem}",
	    	editurl:"serialNumberSetGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="serialNumberSet.id" />',hidden:true,key:true},				
				{name:'businessCode',index:'businessCode',align:'left',label : '<s:text name="serialNumberSet.businessCode" />',hidden:false,formatter : 'select',editoptions:{value :getBusinessCodeMap()}},				
				{name:'businessName',index:'businessName',align:'left',label : '<s:text name="serialNumberSet.businessName" />',hidden:false},				
				{name:'serialLenth',index:'serialLenth',align:'center',label : '<s:text name="serialNumberSet.serialLenth" />',hidden:false,formatter:'integer'},				
				{name:'needPrefix',index:'needPrefix',align:'center',label : '<s:text name="serialNumberSet.needPrefix" />',hidden:false,formatter:'checkbox'},				
				{name:'prefix',index:'prefix',align:'left',label : '<s:text name="serialNumberSet.prefix" />',hidden:false},				
				{name:'needYearMonth',index:'needYearMonth',align:'center',label : '<s:text name="serialNumberSet.needYearMonth" />',hidden:false,formatter:'checkbox'},				
				{name:'disabled',index:'disabled',align:'center',label : '<s:text name="serialNumberSet.disabled" />',hidden:false,formatter:'checkbox'},				
				{name:'copyCode',index:'copyCode',align:'center',label : '<s:text name="serialNumberSet.copyCode" />',hidden:true},				
				{name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="serialNumberSet.orgCode" />',hidden:true},				
				{name:'subSystem',index:'subSystem',align:'center',label : '<s:text name="serialNumberSet.subSystem" />',hidden:true}				
	        ],
	        jsonReader : {
				root : "serialNumberSets", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'businessCode',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="serialNumberSetList.title" />',
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
				gridContainerResize("${random}_serialNumberSet","div");
	           var dataTest = {"id":"${random}_serialNumberSet_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("${random}_serialNumberSet_gridtable");
	       	} 
	    });
	    jQuery(serialNumberSetGrid).jqGrid('bindKeys');
  	});
	function getBusinessCodeMap(){
		var data = "";
		var bcMap = parseMap("${businessCodeMap}");
		for(var i=0;i<bcMap.size();i++){
			var elem = bcMap.element(i);
			data += elem.key+":"+elem.value+";"
		}
		data = data.substring(0,data.length-1);
		return data;
	}
	function parseMap(map){
		var result = new Map();
		map  = map.substring(1,map.length-1);//去掉{}
		var arr = map.split(",");
		var key = '',value = '';
		for(var i=0;i<arr.length;i++){
			var item = arr[i];
			key = item.substring(0,item.indexOf("=")).trim();
			value = item.substring(item.indexOf("=")+1,item.length).trim();
			result.put(key,value);
		}
		return result;
	}
	function addSerialNumberSet(){
		var url = "editSerialNumberSet?popup=true&subSystem=${subSystem}&random=${random}&navTabId=${random}_serialNumberSet_gridtable";
		var winTitle="新增序列号设置";
		url = encodeURI(url);
		$.pdialog.open(url, 'editSerialNumberSet', winTitle, {mask:false,width:400,height:300});　
	}
	function editSerialNumberSet(){
		var sid = jQuery("#${random}_serialNumberSet_gridtable").jqGrid('getGridParam','selarrrow');
	    if(sid==null || sid.length ==0){
	    	alertMsg.error("请选择记录。");
			return;
		}else if(sid.length>1){
			alertMsg.error("只能选择一条记录。");
			return;
		}else{
			var row = jQuery("#${random}_serialNumberSet_gridtable").jqGrid('getRowData',sid);
			var disabled = row["disabled"];
			if(disabled=='No'){
				alertMsg.error("启用记录不能修改。");
				return;
			}
			var url = "editSerialNumberSet?subSystem=${subSystem}&popup=true&random=${random}&id="+ sid+"&navTabId=${random}_serialNumberSet_gridtable";
			var winTitle="修改序列号设置";
			url = encodeURI(url);
			$.pdialog.open(url, 'editSerialNumberSet', winTitle, {mask:false,width:400,height:300});　
		}
	}
	function delSerialNumberSet(){
		var sid = jQuery("#${random}_serialNumberSet_gridtable").jqGrid('getGridParam','selarrrow');
		var editUrl = "${ctx}/serialNumberSetGridEdit?oper=del";
		
	    if(sid==null || sid.length ==0){
	    	alertMsg.error("请选择记录。");
			return;
		}else{
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#${random}_serialNumberSet_gridtable").jqGrid('getRowData',rowId);
				
				if(row["disabled"]=='No'){
					alertMsg.error("您选择的记录中包含已启用记录.不能删除!");
					return;
				}
			}
			editUrl +="&id=" + sid+"&navTabId=${random}_serialNumberSet_gridtable";
			editUrl = encodeURI(editUrl);
			alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, formCallBack, "json");
					
				}
			});
		}
	}
	function enableOrDisableSerialNumberSet(type){
		var url = "${ctx}/serialNumberSetGridEdit?oper="+type;
		var sid = jQuery("#${random}_serialNumberSet_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#${random}_serialNumberSet_gridtable").jqGrid('getRowData',rowId);
				if(type==='enable'){
					if(row["disabled"]=='No'){
						alertMsg.error("您选择的记录中包含已启用记录.不能重复启用!");
						return;
					}
				}
				if(type==='disable'){
					if(row["disabled"]=='Yes'){
						alertMsg.error("您选择的记录中包含已停用记录.不能重复停用!");
						return;
					}
				}
			}
			url = url+"&id="+sid+"&navTabId=${random}_serialNumberSet_gridtable";
			alertMsg.confirm("确认"+(type==='disable'?'停用':'启用')+"？", {
				okCall: function(){
					jQuery.post(url, function(data) {
						formCallBack(data);
					});
				}
			});
			
		}
	}
</script>

<div class="page" id="${random}_serialNumberSet_page">
	<div class="pageHeader" id="${random}_serialNumberSet_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="${random}_serialNumberSet_search_form" class="queryarea-form" >
					<label class="queryarea-label" >
						<s:text name='serialNumberSet.businessCode'/>:
						<s:select name="filter_LIKES_businessCode" 
							list="businessCodeMap" listKey="key" listValue="value"
							emptyOption="true" theme="simple">
						</s:select>
					</label>
					<label class="queryarea-label" >
						<s:text name='serialNumberSet.businessName'/>:
						<input type="text" name="filter_LIKES_businessName"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='serialNumberSet.prefix'/>:
						<input type="text" name="filter_LIKES_prefix"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='serialNumberSet.disabled'/>:
						 	<select name="filter_EQB_disabled">
						 		<option value="">--</option>
						 		<option value="1">是</option>
						 		<option value="0">否</option>
						 	</select>
					</label>
					<div class="buttonActive" style="float: right;">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('${random}_serialNumberSet_search_form','${random}_serialNumberSet_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
			
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="${random}_serialNumberSet_buttonBar">
			<ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:addSerialNumberSet()" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a class="delbutton"  href="javaScript:delSerialNumberSet()"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a class="changebutton"  href="javaScript:editSerialNumberSet()"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a class="enablebutton" href="javaScript:enableOrDisableSerialNumberSet('enable')"><span><s:text name="button.enable" /></span> </a>
				</li>
				<li>
					<a class="disablebutton" href="javaScript:enableOrDisableSerialNumberSet('disable')"><span><s:text name="button.disable" /></span> </a>
				</li>
			</ul>
		</div>
		<div id="${random}_serialNumberSet_gridtable_div" style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:400;height:300">
			<input type="hidden" id="${random}_serialNumberSet_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_${random}_serialNumberSet_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="${random}_serialNumberSet_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="${random}_serialNumberSet_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}_serialNumberSet_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}_serialNumberSet_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="${random}_serialNumberSet_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>
