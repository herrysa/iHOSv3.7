
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var kqDeptCheckLayout;
	var kqDeptCheckGridIdString="#kqDeptCheck_gridtable";
	
	jQuery(document).ready(function() { 
		var kqDeptCheckGrid = jQuery(kqDeptCheckGridIdString);
		kqDeptCheckGrid.jqGrid({
			url : "kqDeptCheckGridList",
			editurl:"",
			datatype : "json",
			mtype : "GET",
	    	colModel:[
{name:'id',index:'id',align:'left',width:'100px',label : '<s:text name="ID" />',hidden:true,key:true},
{name:'deptName',index:'deptName',align:'left',width:'225px',label : '<s:text name="kqMonthData.deptName" />',hidden:false},
{name:'kqTypeName',index:'kqTypeName',align:'left',width:'225px',label : '<s:text name="kqMonthData.kqType" />',hidden:false},
{name:'status',index:'status',align:'center',width:'125px',label : '<s:text name="kqMonthData.status" />',hidden:false,formatter:'select',editoptions:{value:"0:新增;1:审核;2:提交;3:通过;4:退回"}}
	    	],jsonReader : {
				root : "kqDeptChecks",
				page : "page",
				total : "total",
				records : "records",
				repeatitems : false
			},
	    	sortname: 'kqId',
	    	viewrecords: true,
	    	sortorder: 'desc',
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
				gridContainerResize('kqDeptCheck','div');
				var rowNum = jQuery(this).getDataIDs().length;
				if(rowNum <= 0) {
					var ow = jQuery("#kqDeptCheck_gridtable").outerWidth();
					jQuery("#kqDeptCheck_gridtable").parent().width(ow);
					jQuery("#kqDeptCheck_gridtable").parent().height(1);
				} else {
					var rowIds = jQuery(this).getDataIDs();
					var ret = jQuery(this).jqGrid("getRowData");
					var id = "";
					var period = jQuery("#kqMonthData_period").val();
					for(var i = 0; i < rowIds.length; i++) {
						id = rowIds[i];
						var status = ret[i].status;
						var showText = "";
						switch (status){
							case "0":
								showText = "新增";
								break;
							case "1":
								showText = "审核";
								break;
							case "2":
								showText = "提交";
								break;
							case "3":
								showText = "通过";
								break;
							case "4":
								showText = "退回";
								break;
						}
						setCellText(this,id,"status","<a style='color:blue;text-decoration:underline;cursor:pointer;'  onclick='javascript:kqMonthUpDataRecord(\""+id+"\");' >"+showText+"</a>");
					}
				}
				var dataTest = {"id":"kqDeptCheck_gridtable"};
				jQuery.publish("onLoadSelect",dataTest,null);
				//makepager("kqDeptCheck_gridtable");
			} 
		});
		jQuery(kqDeptCheckGrid).jqGrid('bindKeys');
	});


/*----------------------------------tooBar start-----------------------------------------------*/
	var kqDeptCheck_menuButtonArrJson = "${menuButtonArrJson}";
	var kqDeptCheck_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(kqDeptCheck_menuButtonArrJson,false)));
	var kqDeptCheck_toolButtonBar = new ToolButtonBar({el:$('#kqDeptCheck_buttonBar'),collection:kqDeptCheck_toolButtonCollection,attributes:{
		tableId : 'kqDeptCheck_gridtable',
		baseName : 'kqDeptCheck',
		width : 600,
		height : 600,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : '<s:text name="kqDeptCheckNew.title"/>',
		editTitle : null
	}}).render();
	
	var kqDeptCheck_function = new scriptFunction();
	kqDeptCheck_function.optBeforeCall = function(e,$this,param){
		var pass = false;
		if(param.selectRecord){
			var sid = jQuery("#kqDeptCheck_gridtable").jqGrid('getGridParam','selarrrow');
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
	//审核
	var kqDeptCheck_checkButton = {id:'1006040401',buttonLabel:'审核',className:'checkbutton',show:true,enable:true,
		callBody:function() {
			var sid = jQuery("#kqDeptCheck_gridtable").jqGrid('getGridParam','selarrrow');
			var rowData = jQuery("#kqDeptCheck_gridtable").jqGrid('getRowData',sid);
			if(rowData.status != '2') {
				alertMsg.error("只有提交状态的数据才可以审核！");
				return;
			}
			alertMsg.confirm("确认审核?", {
				okCall : function() {
					jQuery.ajax({
						url:"kqDeptChecked",
						dataType:"json",
						type:"post",
						data:{id:sid,oper:"check"},
						error:function(data) {
							alertMsg.error('系统错误！');
							return;
						},
						success:function(data) {
							if(data.statusCode == 200){
				        		jQuery("#kqDeptCheck_gridtable").jqGrid("setGridParam",{page:1}).trigger("reloadGrid");
				        		alertMsg.correct('审核通过！');
				        	}else{
				        		alertMsg.error(data.message);
				        	}
						}
					});
				}
			});
		}};
	kqDeptCheck_toolButtonBar.addButton(kqDeptCheck_checkButton);
	kqDeptCheck_toolButtonBar.addBeforeCall('1006040401',function(e,$this,param){
		return kqDeptCheck_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",singleSelect:"singleSelect"});
	//否决
	var kqDeptCheck_vetoButton = {id:'1006040402',buttonLabel:'否决',className:'delallbutton',show:true,enable:true,
		callBody:function() {
			var sid = jQuery("#kqDeptCheck_gridtable").jqGrid('getGridParam','selarrrow');
			var rowData = jQuery("#kqDeptCheck_gridtable").jqGrid('getRowData',sid);
			if(rowData.status != '2') {
				alertMsg.error("只有提交状态的数据才可以否决！");
				return;
			}
			alertMsg.confirm("确认否决?", {
				okCall : function() {
					jQuery.ajax({
						url:"kqDeptChecked",
						dataType:"json",
						type:"post",
						data:{id:sid,oper:"veto"},
						error:function(data) {
							alertMsg.error('系统错误！');
							return;
						},
						success:function(data) {
							if(data.statusCode == 200){
				        		jQuery("#kqDeptCheck_gridtable").jqGrid("setGridParam",{page:1}).trigger("reloadGrid");
				        		alertMsg.correct('否决成功！');
				        	}else{
				        		alertMsg.error(data.message);
				        	}
						}
					});
				}
			});
		}};
	kqDeptCheck_toolButtonBar.addButton(kqDeptCheck_vetoButton);	
	kqDeptCheck_toolButtonBar.addBeforeCall('1006040402',function(e,$this,param){
		return kqDeptCheck_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",singleSelect:"singleSelect"});
	
	//否决
	var kqDeptCheck_reCheckButton = {id:'1006040403',buttonLabel:'销审',className:'delallbutton',show:true,enable:true,
		callBody:function() {
			var sid = jQuery("#kqDeptCheck_gridtable").jqGrid('getGridParam','selarrrow');
			var rowData = jQuery("#kqDeptCheck_gridtable").jqGrid('getRowData',sid);
			if(rowData.status != '3') {
				alertMsg.error("只有审核状态的数据才可以销审！");
				return;
			}
			alertMsg.confirm("确认销审?", {
				okCall : function() {
					jQuery.ajax({
						url:"kqDeptChecked",
						dataType:"json",
						type:"post",
						data:{id:sid,oper:"reCheck"},
						error:function(data) {
							alertMsg.error('系统错误！');
							return;
						},
						success:function(data) {
							if(data.statusCode == 200){
				        		jQuery("#kqDeptCheck_gridtable").jqGrid("setGridParam",{page:1}).trigger("reloadGrid");
				        		alertMsg.correct('销审成功！');
				        	}else{
				        		alertMsg.error(data.message);
				        	}
						}
					});
				}
			});
		}};
	kqDeptCheck_toolButtonBar.addButton(kqDeptCheck_reCheckButton);
	
/*----------------------------------tooBar end-----------------------------------------------*/

	function kqMonthUpDataRecord(id){
		var url = "editKqDeptCheck?id="+id; //?id="+id+"&oper=view&temp=first&popup=true&navTabId=kqDeptCheck_gridtable
		$.pdialog.open(url,'kqDeptCheckForm_'+id,'部门考勤明细', {ifr:true,hasSupcan:"all",mask:true,width : 750,height : 450,maxable:true,resizable:true});
	}
</script>
<div class="page">
	<div id="kqDeptCheck_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="kqDeptCheck_search_form" class="queryarea-form">
					<s:hidden name="deptId"></s:hidden>
					<s:hidden name="deptCode"></s:hidden>
					<s:hidden name="period" id="kqMonthData_period"></s:hidden>
					<label class="queryarea-label">
						<s:text name="kqMonthData.deptCode"></s:text>
						<input type="text" name="filter_LIKES_deptCode"/>
					</label>
					<label class="queryarea-label">
						<s:text name="kqMonthData.deptName"></s:text>
						<input type="text" name="filter_LIKES_deptName"/>
					</label>
					<label class="queryarea-label">
						<s:text name="kqMonthData.kqType"></s:text>
						<select name="filter_EQS_kqType">
							<option value="">--</option>
							<c:forEach var="type" items="${requestScope.kqTypes}">
								<option value="${type.kqTypeId}">${type.kqTypeName}</option>
							</c:forEach>
						</select>
					</label>
					<label class="queryarea-label">
						<s:text name="kqMonthData.status"></s:text>
						<s:select list="#{'':'--','2':'提交','3':'通过','4':'否决'}" name="filter_EQS_status"  listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px"></s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('kqDeptCheck_search_form','kqDeptCheck_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="kqDeptCheck_buttonBar">
		</div>
		<div id="kqDeptCheck_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="kqDeptCheck_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_kqDeptCheck_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="kqDeptCheck_gridtable"></table>
			<!--<div id="kqDeptCheckPager"></div>-->
		</div>
		<div class="panelBar" id="kqDeptCheck_pageBar">
			<div class="pages">
				<span><s:text name="pager.perPage" /></span> <select id="kqDeptCheck_gridtable_numPerPage">
					<option value="20">20</option>
					<option value="50">50</option>
					<option value="100">100</option>
					<option value="200">200</option>
				</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="kqDeptCheck_gridtable_totals"></label><s:text name="pager.items" /></span>
			</div>
			<div id="kqDeptCheck_gridtable_pagination" class="pagination"
				targetType="navTab" totalCount="200" numPerPage="20"
				pageNumShown="10" currentPage="1">
			</div>
		</div>
	</div>
</div>