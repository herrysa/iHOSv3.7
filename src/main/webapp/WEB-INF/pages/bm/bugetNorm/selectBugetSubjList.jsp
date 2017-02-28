
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	jQuery(document).ready(function() { 
		var sbugetSubjGridIdString="#sbugetSubj_gridtable";
		var sbugetSubjGrid = jQuery(sbugetSubjGridIdString);
    	sbugetSubjGrid.jqGrid({
    		url : "bugetSubjGridList?filter_NIS_bugetSubjId=${bugetSubjId}&filter_EQB_leaf=true",
    		editurl:"bugetSubjGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'bugetSubjId',index:'bugetSubjId',align:'left',label : '<s:text name="bugetSubj.bugetSubjId" />',hidden:true,key:true},
			{name:'bugetSubjCode',index:'bugetSubjCode',align:'left',label : '<s:text name="bugetSubj.bugetSubjCode" />',hidden:false},
			{name:'bugetSubjName',index:'bugetSubjName',align:'left',label : '<s:text name="bugetSubj.bugetSubjName" />',hidden:false},
			{name:'cnCode',index:'cnCode',align:'left',label : '<s:text name="bugetSubj.cnCode" />',hidden:false},
			{name:'subjTypeCode',index:'subjTypeCode',align:'left',label : '<s:text name="bugetSubj.subjTypeCode" />',hidden:true},
			{name:'subjTypeName',index:'subjTypeName',align:'left',label : '<s:text name="bugetSubj.subjTypeCode" />',hidden:false},
			{name:'centralDeptId.name',index:'centralDeptId.name',align:'left',label : '<s:text name="bugetSubj.centralDeptId" />',hidden:false},
			{name:'ctrlPeriod',index:'ctrlPeriod',align:'left',label : '<s:text name="bugetSubj.ctrlPeriod" />',hidden:false},
			{name:'slevel',index:'slevel',align:'center',label : '<s:text name="bugetSubj.slevel" />',hidden:false,formatter:'integer',width:'70px'},
			{name:'isCarry',index:'isCarry',align:'center',label : '<s:text name="bugetSubj.isCarry" />',hidden:false,formatter:'checkbox',width:'70px'},
			{name:'isprocessctrl',index:'isprocessctrl',align:'center',label : '<s:text name="bugetSubj.isprocessctrl" />',hidden:false,formatter:'checkbox',width:'80px'},
			{name:'leaf',index:'leaf',align:'center',label : '<s:text name="bugetSubj.leaf" />',hidden:false,formatter:'checkbox',width:'70px'},
			],
        	jsonReader : {
				root : "bugetSubjs", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'bugetSubjCode',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="bugetSubjList.title" />',
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
           	//if(jQuery(this).getDataIDs().length>0){
           	//  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
           	// }
           	var dataTest = {"id":"sbugetSubj_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("sbugetSubj_gridtable");
       		} 

    	});
    jQuery("#sbugetSubj_gridtable_selected").click(function(){
    	var sid = jQuery("#sbugetSubj_gridtable").jqGrid('getGridParam','selarrrow');
    	if(sid.length==0){
    		alertMsg.error("请选择记录！");
    		return;
    	}
        $.post("selectedBugetSubj", {
    		"_" : $.now(),bugetSubjId:sid,deptId:"${deptId}",navTabId:'bugetNorm_gridtable'
		}, function(data) {
			formCallBack(data);
		});
    });
  	});
</script>

<div class="page" id="sbugetSubj_page">
	<div id="sbugetSubj_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="sbugetSubj_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.bugetSubjCode'/>:
						<input type="text" name="filter_LIKES_bugetSubjCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.bugetSubjName'/>:
						<input type="text" name="filter_LIKES_bugetSubjName"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.subjTypeCode'/>:
						<s:hidden id="sbugetSubj_subjTypeCode_id" name="filter_EQS_subjTypeCode"/>
						<s:textfield id="sbugetSubj_subjTypeCode" name="_exclude_bugetSubj_subjTypeCode"/>
						<script>
						jQuery("#sbugetSubj_subjTypeCode").treeselect({
							optType : "single",
							dataType : 'sql',
							sql : "SELECT AccttypeId id,Accttype name,'1' parent FROM GL_accountType ORDER BY Accttypecode asc",
							exceptnullparent : true,
							lazy : false,
							selectParent : false,
							callback : {
							}
						});
						</script>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.centralDeptId'/>:
						<s:hidden id="sbugetSubj_centralDeptId_id" name="filter_EQS_centralDeptId.departmentId"/>
						<s:textfield id="sbugetSubj_centralDeptId" name="_exclude_centralDeptId" />
							<script>
							jQuery("#sbugetSubj_centralDeptId").treeselect({
								optType : "single",
								dataType : 'sql',
								sql : "select deptId id,name,parentDept_id parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from v_bm_department where ysleaf=1 and disabled=0 and deptId <> 'XT'",
								exceptnullparent : true,
								lazy : false,
								minWidth : '280px',
								selectParent : false,
								callback : {
								}
							});
							</script>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.orgCode'/>:
						<input type="text" name="filter_EQS_orgCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.copyCode'/>:
						<input type="text" name="filter_EQS_copyCode"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.ctrlPeriod'/>:
						<s:select list="#{'年度':'年度','季度':'季度','月度':'月度'}" name="filter_EQS_ctrlPeriod" headerKey="" headerValue="--" theme="simple"></s:select>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.isCarry'/>:
						<input type="text" name="filter_EQS_isCarry"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.isprocessctrl'/>:
						<input type="text" name="filter_EQS_isprocessctrl"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='bugetSubj.slevel'/>:
						<input type="text" name="filter_EQS_slevel"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('sbugetSubj_search_form','sbugetSubj_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="sbugetSubj_buttonBar">
			<ul class="toolBar">
				<li><a id="sbugetSubj_gridtable_selected" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="sbugetSubj_gridtable_div" layoutH="120" class="grid-wrapdiv" buttonBar="width:600;height:400">
			<div id="load_sbugetSubj_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="sbugetSubj_gridtable"></table>
		</div>
		</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="sbugetSubj_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="sbugetSubj_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="sbugetSubj_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>