
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var businessTypeJLayout;
	var businessTypeJGridIdString="#businessTypeJ_gridtable";
	
	jQuery(document).ready(function() { 
		var businessTypeJGrid = jQuery(businessTypeJGridIdString);
		var businessId = jQuery("#businessTypeJ_gridtable_businessId").val();
    	businessTypeJGrid.jqGrid({
    		url : "businessTypeJGridList?businessId="+businessId,
    		editurl:"businessTypeJGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'id',index:'id',align:'left',width:120,label : '<s:text name="businessTypeJ.id" />',hidden:false,key:true},
{name:'colName',index:'colName',align:'left',width:150,label : '<s:text name="businessTypeJ.colName" />',hidden:false},
{name:'fieldName',index:'fieldName',align:'left',width:120,label : '<s:text name="businessTypeJ.fieldName" />',hidden:false},
{name:'sn',index:'sn',align:'right',width:50,label : '<s:text name="businessTypeJ.sn" />',hidden:false,formatter:'integer'},
{name:'dataSourceType',index:'dataSourceType',align:'left',width:75,label : '<s:text name="businessTypeJ.dataSourceType" />',hidden:false,formatter:'select',editoptions:{value:"0:本地;1:中间库;2:财务库"}},
{name:'sourceTable',index:'sourceTable',align:'left',width:120,label : '<s:text name="businessTypeJ.sourceTable" />',hidden:false},
{name:'rowToCol',index:'rowToCol',align:'center',width:50,label : '<s:text name="businessTypeJ.rowToCol" />',hidden:false,formatter:'checkbox'},
{name:'accCol',index:'accCol',align:'center',width:75,label : '<s:text name="businessTypeJ.accCol" />',hidden:false,formatter:'checkbox'},
{name:'disabled',index:'disabled',align:'center',width:50,label : '<s:text name="businessTypeJ.disabled" />',hidden:false,formatter:'checkbox'},
{name:'remark',index:'remark',align:'left',width:200,label : '<s:text name="businessTypeJ.remark" />',hidden:false}
],
        	jsonReader : {
				root : "businessTypeJs", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'sn',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="businessTypeJList.title" />',
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
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"businessTypeJ_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("businessTypeJ_gridtable");
       		} 

    	});
    jQuery(businessTypeJGrid).jqGrid('bindKeys');
  //添加
    jQuery("#businessTypeJ_gridtable_add_custom").unbind("click").bind("click",function(){
    	var businessId = jQuery("#businessTypeJ_gridtable_businessId").val();
    	var url = "editBusinessTypeJ?popup=true&navTabId=businessTypeJ_gridtable"+"&businessId="+businessId;
    	url = encodeURI(url);
    	var winTitle = '<s:text name="businessTypeJNew.title"/>';
		$.pdialog.open(url,"addBusinessTypeJ",winTitle,{mask:true,width:425,height:450,maxable:false,resizable:false});

    });
  //删除
	jQuery("#businessTypeJ_gridtable_del_custom").unbind("click").bind("click",function() {
		var sid = jQuery("#businessTypeJ_gridtable").jqGrid("getGridParam","selarrrow");
		if(sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		}
		var editUrl = jQuery("#businessTypeJ_gridtable").jqGrid('getGridParam', 'editurl');
		editUrl+="?id=" + sid+"&navTabId=businessTypeJ_gridtable"+"&oper=del";
		editUrl = encodeURI(editUrl);
		jQuery.ajax({
			url : "checkBusinessTypeJDel?id="+sid,
			dataType : "json",
			type : "get",
			success : function(data) {
				if(data.message) {
					if(data.statusCode == "200") {
						alertMsg.confirm(data.message,{
							okCall : function() {
								jQuery.post(editUrl, {
								}, formCallBack, "json");
							}
						});
					} else {
						alert(111);
					}
				} else {
					alertMsg.confirm("确认删除？", {
						okCall: function(){
							jQuery.post(editUrl, {
							}, formCallBack, "json");
							
						}
					});
				}
			}
		});
		
	});
  //修改
  jQuery("#businessTypeJ_gridtable_edit_custom").unbind("click").bind("click",function() {
		var sid = jQuery("#businessTypeJ_gridtable").jqGrid("getGridParam","selarrrow");
		if(sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		}
		if(sid.length != 1) {
			alertMsg.error("只能选择一条记录。");
			return;
		}
		var url = "editBusinessTypeJ?popup=true&navTabId=businessTypeJ_gridtable&id="+sid[0];
		url = encodeURI(url);
		var winTitle = '<s:text name="businessTypeJEdit.title"/>';
		$.pdialog.open(url,"editBusinessTypeJ",winTitle,{mask:true,width:425,height:450,maxable:false,resizable:false});

  });
  	});
</script>

<div class="page">
	<div id="businessTypeJ_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="businessTypeJ_search_form" class="queryarea-form" >
					<label class="queryarea-label" >
						<s:text name='businessTypeJ.id'/>:
						<input type="text" name="filter_LIKES_id"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='businessTypeJ.colName'/>:
						<input type="text" name="filter_LIKES_colName"/>
					</label>
					<%-- <label class="queryarea-label" >
						<s:text name='businessTypeJ.dataSourceType'/>:
						<s:select list="#{'':'--','0':'本地','1':'中间库','2':'财务库' }" name="filter_EQS_dataSourceType" theme="simple" />
					</label> --%>
					<label class="queryarea-label" >
						<s:text name='businessTypeJ.disabled'/>:
						<s:select list="#{'':'--','1':'是','0':'否' }" name="filter_EQB_disabled" theme="simple" />
					</label>
					<%-- <label class="queryarea-label" >
						<s:text name='businessTypeJ.sourceTable'/>:
						<input type="text" name="filter_LIKES_sourceTable"/>
					</label> --%>
					<label class="queryarea-label" >
						<s:text name='businessTypeJ.fieldName'/>:
						<input type="text" name="filter_LIKES_fieldName"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='businessTypeJ.rowToCol'/>:
						<s:select list="#{'':'--','1':'是','0':'否' }" name="filter_EQB_rowToCol" theme="simple" />
					</label>
					<label class="queryarea-label" >
						<s:text name='businessTypeJ.accCol'/>:
						<s:select list="#{'':'--','1':'是','0':'否' }" name="filter_EQB_accCol" theme="simple" />
					</label>
					<label class="queryarea-label" >
						<s:text name='businessTypeJ.remark'/>:
						<input type="text" name="filter_LIKES_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('businessTypeJ_search_form','businessTypeJ_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="businessTypeJ_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="businessTypeJ_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="businessTypeJ_gridtable_edit_custom" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="businessTypeJ_gridtable_div" tablecontainer="businessTypeTabsContent" extraHeight="118" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="businessTypeJ_gridtable_navTabId" value="${sessionScope.navTabId}">
			<input type="hidden" id="businessTypeJ_gridtable_businessId" value="${businessId}" />
			<label style="display: none" id="businessTypeJ_gridtable_addTile">
				<s:text name="businessTypeJNew.title"/>
			</label> 
			<label style="display: none"
				id="businessTypeJ_gridtable_editTile">
				<s:text name="businessTypeJEdit.title"/>
			</label>
			<div id="load_businessTypeJ_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="businessTypeJ_gridtable"></table>
			<!--<div id="businessTypeJPager"></div>-->
		</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="businessTypeJ_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="businessTypeJ_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="businessTypeJ_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>