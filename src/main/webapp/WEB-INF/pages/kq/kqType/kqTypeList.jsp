
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var kqTypeLayout;
	var kqTypeGridIdString="#kqType_gridtable";
	
	jQuery(document).ready(function() { 
		var kqTypeGrid = jQuery(kqTypeGridIdString);
    	kqTypeGrid.jqGrid({
    		url : "kqTypeGridList",
    		editurl:"kqTypeGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'kqTypeId',index:'kqTypeId',align:'left',width:'100px',label : '<s:text name="kqType.kqTypeId" />',hidden:true,key:true},
{name:'kqTypeCode',index:'kqTypeCode',align:'left',width:'225px',label : '<s:text name="kqType.kqTypeCode" />',hidden:false},
{name:'kqTypeName',index:'kqTypeName',align:'left',width:'225px',label : '<s:text name="kqType.kqTypeName" />',hidden:false},       	
{name:'disabled',index:'disabled',align:'center',width:'100px',label : '<s:text name="kqType.disabled" />',hidden:false,formatter:'checkbox'}        	
        	],jsonReader : {
				root : "kqTypes", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'kqTypeCode',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="kqTypeList.title" />',
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
		 		gridContainerResize('kqType','div');
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"kqType_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("kqType_gridtable");
       		} 

    	});
    jQuery(kqTypeGrid).jqGrid('bindKeys');
  	});
  	/*----------------------------------tooBar start-----------------------------------------------*/
	var kqType_menuButtonArrJson = "${menuButtonArrJson}";
	var kqType_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(kqType_menuButtonArrJson,false)));
	var kqType_toolButtonBar = new ToolButtonBar({el:$('#kqType_buttonBar'),collection:kqType_toolButtonCollection,attributes:{
		tableId : 'kqType_gridtable',
		baseName : 'kqType',
		width : 600,
		height : 600,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : '<s:text name="kqTypeNew.title"/>',
		editTitle : null
	}}).render();
	
	var kqType_function = new scriptFunction();
	kqType_function.optBeforeCall = function(e,$this,param){
		var pass = false;
		if(param.selectRecord){
			var sid = jQuery("#kqType_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
	        	alertMsg.error("请选择记录！");
				return pass;
			}
	        if(param.singleSelect){
	        	if(sid.length != 1){
		        	alertMsg.error("只能选择一条记录！");
					return pass;
				}
	        }
		}
        return true;
	};
	/*添加*/
	kqType_toolButtonBar.addCallBody('1006010101',function(e,$this,param){
		var url = "editKqType?popup=true&navTabId=kqType_gridtable";
		var winTitle = '<s:text name="kqTypeNew.title"/>';
		url = encodeURI(url);
    	$.pdialog.open(url,'editKqType',winTitle, {mask:true,width : 400,height : 185,maxable:true,resizable:true});  
	},{});
	/*删除*/
	kqType_toolButtonBar.addCallBody('1006010102',function(e,$this,param){
		var sid = jQuery(kqTypeGridIdString).jqGrid("getGridParam","selarrrow");
		/*for(var i = 0;i < sid.length; i++) {
			var rowId = sid[i]; 
			var row = jQuery(kqTypeGridIdString).jqGrid("getRowData",rowId);
			if(row['status'] == "Yes"){
				alertMsg.error("当前类别不能删除！");
				return;
			}
		}*/
	    jQuery.ajax({
			url: 'checkKqTypeDel',
			data: {id:sid},
			type: 'post',
			dataType: 'json',
			async:true,
			error: function(data){
				alertMsg.error("系统错误！");
			},
			success: function(data){
				if(data.message){
					alertMsg.error(data.message);
					return;
				}
				alertMsg.confirm("确认要删除该类别？",{
					okCall : function() {
						var url = "kqTypeGridEdit?id="+sid+"&popup=true&navTabId=kqType_gridtable&oper=del";
						$.post(url,{},function(data){
							formCallBack(data);
						});
					}
				});
			}
		});

	},{});
	kqType_toolButtonBar.addBeforeCall('1006010102',function(e,$this,param){
		return kqType_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord"});
	/*修改*/
	kqType_toolButtonBar.addCallBody('1006010103',function(e,$this,param){
		var sid = jQuery("#kqType_gridtable").jqGrid('getGridParam','selarrrow');
		var url = "editKqType?kqTypeId=" + sid + "&popup=true&navTabId=kqType_gridtable&oper=update";
		var editTitle='<s:text name="kqTypeEdit.title"/>';
		url = encodeURI(url);
		$.pdialog.open(url,'editKqType',editTitle, {mask:true,width : 400,height : 185,maxable:true,resizable:true});  
	},{});
	kqType_toolButtonBar.addBeforeCall('1006010103',function(e,$this,param){
		return kqType_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",singleSelect:"singleSelect"});
	/*启用*/
	var kqType_enableButton = {id:'1006010104',buttonLabel:'启用',className:'enablebutton',show:true,enable:true,
			callBody : function() {
				var sid = jQuery("#kqType_gridtable").jqGrid("getGridParam","selarrrow");
				for(var i = 0;i < sid.length; i ++) {
					var rowId = sid[i];
					var row = jQuery("#kqType_gridtable").jqGrid("getRowData",rowId);
					if(row["disabled"] != "Yes") {
						alertMsg.error("该类别已启用！");
						return;
					}
				}
				var url = "kqTypeGridEdit?id="+sid+"&popup=true&navTabId=kqType_gridtable&oper=enable";  
				alertMsg.confirm("确认启用？",{
					okCall : function() {
						jQuery.post(url,function(data) {
							formCallBack(data);
						})
					}
				});
			}
	};
	kqType_toolButtonBar.addButton(kqType_enableButton);
	kqType_toolButtonBar.addBeforeCall('1006010104',function(e,$this,param){
		return kqType_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord"});
	/*停用*/
	var kqType_disableButton = {id:'1006010105',buttonLabel:'停用',className:'disablebutton',show:true,enable:true,
			callBody : function() {
				var sid = jQuery("#kqType_gridtable").jqGrid("getGridParam","selarrrow");
				for(var i = 0;i < sid.length; i ++) {
					var rowId = sid[i];
					var row = jQuery("#kqType_gridtable").jqGrid("getRowData",rowId);
					if(row["disabled"] != "No") {
						alertMsg.error("该类别已停用！");
						return;
					}
				}
				var url = "kqTypeGridEdit?id="+sid+"&popup=true&navTabId=kqType_gridtable&oper=disable";  
				alertMsg.confirm("确认停用？",{
					okCall : function() {
						jQuery.post(url,function(data) {
							formCallBack(data);
						})
					}
				});
			}
	};
	kqType_toolButtonBar.addButton(kqType_disableButton);
	kqType_toolButtonBar.addBeforeCall('1006010105',function(e,$this,param){
		return kqType_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord"});
	/*----------------------------------tooBar end -----------------------------------------------*/
</script>

<div class="page">
	<div id="kqType_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="kqType_search_form" class="queryarea-form">
					<!--<label style="float:none;white-space:nowrap" >
						<s:text name='kqType.kqTypeId'/>:
						<input type="text" name="filter_EQS_kqTypeId"/>
					</label>-->
					<label class="queryarea-label">
						<s:text name='kqType.kqTypeCode'/>:
						<input type="text" name="filter_LIKES_kqTypeCode"/>
					</label>
					<label class="queryarea-label">
						<s:text name='kqType.kqTypeName'/>:
						<input type="text" name="filter_LIKES_kqTypeName"/>
					</label>
					<label class="queryarea-label">
						<s:text name='kqType.disabled'/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_disabled"  listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px"></s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('kqType_search_form','kqType_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="kqType_buttonBar">
			<!--<ul class="toolBar">
				<li><a id="kqType_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="kqType_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="kqType_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>-->
		</div>
		<div id="kqType_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="kqType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="kqType_gridtable_addTile">
				<s:text name="kqTypeNew.title"/>
			</label> 
			<label style="display: none"
				id="kqType_gridtable_editTile">
				<s:text name="kqTypeEdit.title"/>
			</label>
			<div id="load_kqType_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="kqType_gridtable"></table>
			<!--<div id="kqTypePager"></div>-->
		</div>
	<div class="panelBar" id="kqType_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="kqType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="kqType_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="kqType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
	</div>
</div>