
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var invBillNumberSettingGridIdString="#invBillNumberSetting_gridtable";
	
jQuery(document).ready(function() { 
	var invBillNumberSettingGrid = jQuery(invBillNumberSettingGridIdString);
	    invBillNumberSettingGrid.jqGrid({
	    	url : "invBillNumberSettingGridList",
	    	editurl:"invBillNumberSettingGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
	{name:'id',index:'id',align:'center',label : '<s:text name="invBillNumberSetting.id" />',hidden:true,key:true,formatter:'integer'},				
	{name:'serialCode',index:'serialCode',align:'left',label : '<s:text name="invBillNumberSetting.serialCode" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : 'MMRK:材料入库;MMPD:库存盘点;MMCK:材料出库;DPSL:科室申领;MMYK:材料移库;CGJH:采购计划;KSXQ:科室需求;CGDD:采购订单;MRS:需求汇总'}},				
	{name:'serialName',index:'serialName',align:'left',label : '<s:text name="invBillNumberSetting.serialName" />',hidden:false},			
	{name:'serialLenth',index:'serialLenth',align:'center',label : '<s:text name="invBillNumberSetting.serialLenth" />',hidden:false,formatter:'integer'},				
	{name:'needPrefix',index:'needPrefix',align:'center',label : '<s:text name="invBillNumberSetting.needPrefix" />',hidden:false,formatter:'checkbox'},				
	{name:'prefix',index:'prefix',align:'left',label : '<s:text name="invBillNumberSetting.prefix" />',hidden:false},				
	{name:'needYearMonth',index:'needYearMonth',align:'center',label : '<s:text name="invBillNumberSetting.needYearMonth" />',hidden:false,formatter:'checkbox'},				
	/* {name:'inArow',index:'inArow',align:'center',label : '<s:text name="invBillNumberSetting.inArow" />',hidden:false,formatter:'checkbox'},				
	 */{name:'disabled',index:'disabled',align:'center',label : '<s:text name="invBillNumberSetting.disabled" />',hidden:false,formatter:'checkbox'},				
	{name:'copyCode',index:'copyCode',align:'center',label : '<s:text name="invBillNumberSetting.copyCode" />',hidden:true},				
	{name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="invBillNumberSetting.orgCode" />',hidden:true}				
	
	        ],
	        jsonReader : {
				root : "invBillNumberSettings", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'serialName',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="invBillNumberSettingList.title" />',
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
	           if(jQuery(this).getDataIDs().length>0){
	              //jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
	            }
	           var dataTest = {"id":"invBillNumberSetting_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("invBillNumberSetting_gridtable");
	       	} 
	
	    });
	    jQuery(invBillNumberSettingGrid).jqGrid('bindKeys');
 	});
 	
	function enableOrDisableIBNS(flag){
		var url = "${ctx}/invBillNumberSettingGridEdit?oper="+flag;
		var sid = jQuery("#invBillNumberSetting_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#invBillNumberSetting_gridtable").jqGrid('getRowData',rowId);
				if(flag=='enable'){
					if(row["disabled"]=='No'){
						alertMsg.error("您选择的记录中包含已启用记录.不能重复启用!");
						return;
					}
				}
				if(flag=='disable'){
					if(row["disabled"]=='Yes'){
						alertMsg.error("您选择的记录中包含已停用记录.不能重复停用!");
						return;
					}
				}
			}
			url = url+"&id="+sid+"&navTabId=invBillNumberSetting_gridtable";
			jQuery.post(url, function(data) {
				formCallBack(data);
			});
		}
		
	}
	
	jQuery("#invBillNumberSetting_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
		var sid = jQuery("#invBillNumberSetting_gridtable").jqGrid('getGridParam','selarrrow');
		var editUrl = jQuery("#invBillNumberSetting_gridtable").jqGrid('getGridParam', 'editurl');
		editUrl+="?id=" + sid+"&navTabId=invBillNumberSetting_gridtable&oper=del";
		editUrl = encodeURI(editUrl);
	    if(sid==null || sid.length ==0){
	    	alertMsg.error("请选择记录。");
			return;
		}else{
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#invBillNumberSetting_gridtable").jqGrid('getRowData',rowId);
				
				if(row["disabled"]=='No'){
					alertMsg.error("您选择的记录中包含已启用记录.不能删除!");
					return;
				}
			}
			alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, formCallBack, "json");
					
				}
			});
		}
		
	});
	jQuery("#invBillNumberSetting_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){
		var sid = jQuery("#invBillNumberSetting_gridtable").jqGrid('getGridParam','selarrrow');
	    if(sid==null || sid.length ==0){
	    	alertMsg.error("请选择记录。");
			return;
		}if(sid.length>1){
			alertMsg.error("只能选择一条记录。");
			return;
		}else{
			var row = jQuery("#invBillNumberSetting_gridtable").jqGrid('getRowData',sid);
			var disabled = row["disabled"];
			if(disabled=='No'){
				alertMsg.error("启用记录不能修改。");
				return;
			}
			var url = "editInvBillNumberSetting?popup=true&id="+ sid+"&navTabId=invBillNumberSetting_gridtable";
			var winTitle="修改序列号设置";
			url = encodeURI(url);
			$.pdialog.open(url, 'editInvBillNumberSetting', winTitle, {mask:false,width:400,height:300});　
		}
		
	});
</script>

<div class="page">
	<div id="invBillNumberSetting_container">
	<div id="invBillNumberSetting_layout-center" class="pane ui-layout-center">
		<div id="invBillNumberSetting_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="invBillNumberSetting_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='invBillNumberSetting.serialCode'/>:
						<s:select id="invBillNumberSetting_search_form_serialCode"  name="filter_LIKES_serialCode" list="#{'':'--','MMRK':'材料入库','MMPD':'库存盘点','MMCK':'材料出库','DPSL':'科室申领','MMYK':'材料移库','CGJH':'采购计划','KSXQ':'科室需求','CGDD':'采购订单','MRS':'需求汇总'}" style="width:90px" ></s:select>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='invBillNumberSetting.serialName'/>:
						<input type="text" id="invBillNumberSetting_search_form_serialName" name="filter_LIKES_serialName"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='invBillNumberSetting.prefix'/>:
						<input type="text" id="invBillNumberSetting_search_form_prefix" name="filter_LIKES_prefix"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='invBillNumberSetting.disabled'/>:
						 	<select id="invBillNumberSetting_disabled" name="filter_EQB_disabled">
						 		<option value="">--</option>
						 		<option value="0">启用</option>
						 		<option value="1">停用</option>
						 	</select>
					</label>&nbsp;&nbsp;
				</form>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('invBillNumberSetting_search_form','invBillNumberSetting_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('invBillNumberSetting_search_form','invBillNumberSetting_gridtable')"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
		</div>
	<div class="pageContent">

<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a id="invBillNumberSetting_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a id="invBillNumberSetting_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="invBillNumberSetting_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a class="enablebutton" external="true" href="javaScript:enableOrDisableIBNS('enable')"><span>启用</span> </a>
				</li>
				<li>
					<a class="disablebutton" external="true" href="javaScript:enableOrDisableIBNS('disable')"><span>停用</span> </a>
				</li>
			</ul>
		</div>
		<div id="invBillNumberSetting_gridtable_div" layoutH="118"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:400;height:300">
			<input type="hidden" id="invBillNumberSetting_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="invBillNumberSetting_gridtable_addTile">
				<s:text name="invBillNumberSettingNew.title"/>
			</label> 
			<label style="display: none"
				id="invBillNumberSetting_gridtable_editTile">
				<s:text name="invBillNumberSettingEdit.title"/>
			</label>
			<label style="display: none"
				id="invBillNumberSetting_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="invBillNumberSetting_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
			<div id="load_invBillNumberSetting_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
		 	<table id="invBillNumberSetting_gridtable"></table>
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="invBillNumberSetting_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="invBillNumberSetting_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="invBillNumberSetting_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>