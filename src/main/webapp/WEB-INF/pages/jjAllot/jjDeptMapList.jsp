
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/scripts/jqgrid/js/jquery.jqGrid.src.js"></script>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function jjDeptMapGridReload(){
		var urlString = "jjDeptMapGridList";
		var deptIdList = jQuery("#deptIdList_id").val();
		var personName = jQuery("#personName").val();
		var deptIdNameStr = jQuery("#deptIdNameStr").val();
		urlString=urlString+"?filter_INS_deptId.departmentId="+deptIdList+"&filter_LIKES_personId.name="+personName+"&filter_LIKES_deptNames="+deptIdNameStr;
	//alert(urlString);
		urlString=encodeURI(urlString);
		jQuery("#jjDeptMap_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var jjDeptMapLayout;
			  var jjDeptMapGridIdString="#jjDeptMap_gridtable";
	
	jQuery(document).ready(function() { 
		/* jjDeptMapLayout = makeLayout({
			baseName: 'jjDeptMap', 
			tableIds: 'jjDeptMap_gridtable'
		}, null); */
var jjDeptMapGrid = jQuery(jjDeptMapGridIdString);
    jjDeptMapGrid.jqGrid({
    	url : "jjDeptMapGridList",
    	editurl:"jjDeptMapGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="jjDeptMap.id" />',hidden:true,key:true},				
{name:'deptId.name',index:'deptId.name',align:'left',label : '<s:text name="jjDeptMap.deptId" />',hidden:false,width:'100'},							
{name:'personId.name',index:'personId.name',align:'left',label : '<s:text name="jjDeptMap.personId" />',hidden:false,width:'60'},				
{name:'deptNames',index:'deptNames',align:'left',label : '<s:text name="jjDeptMap.deptNames" />',hidden:false,width:'250',editable:true,edittype:'text'},				
{name:'remark',index:'remark',align:'left',label : '<s:text name="jjDeptMap.remark" />',hidden:false,width:'100',editable:true,edittype:'text'}				

        ],
        jsonReader : {
			root : "jjDeptMaps", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'id',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="jjDeptMapList.title" />',
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
			 gridContainerResize('jjDeptMap','div');
           var dataTest = {"id":"jjDeptMap_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("jjDeptMap_gridtable");
       	} 

    });
    jQuery(jjDeptMapGrid).jqGrid('bindKeys');

    /*部门树*/
	var sql = "select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department where disabled=0 and deptId <> 'XT'"
	var herpType = "${fns:getHerpType()}";
	if(herpType == "M") {
		sql += " union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ";
	}
	sql += " ORDER BY orderCol ";

	jQuery("#deptIdList").treeselect({
		optType : "multi",
		dataType : 'sql',
		sql : sql,
		exceptnullparent : true,
		lazy : false,
		minWidth : '180px',
		selectParent : false
	});
});
</script>
<div class="page"  id="jjDeptMap_page">
	<div id="jjDeptMap_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="jjDeptMap_search_form" style="white-space: break-all;word-wrap:break-word;">
						<label>科室:
							<!-- <input type="text"	 id="deptIdList"/> -->
							<input type="text" id="deptIdList" style="width: 146px;">
							<input type="hidden" id="deptIdList_id">
						</label>
						<label>姓名:
							<input type="text"	id="personName" >
						</label>
						<label>分管科室:
							<input type="text"	id="deptIdNameStr" />
						</label>
				<div class="subBar" style="float: right;">
					<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="jjDeptMapGridReload()"><s:text name='button.search'/></button>
								</div>
					</div>
				</div>
				</form>
			</div>
	</div>
	</div>
	<div class="pageContent">

<div class="panelBar"  id="jjDeptMap_buttonBar">
			<ul class="toolBar" id="jjDeptMap_toolbuttonbar">
				<li><a id="jjDeptMap_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				<li><a id="jjDeptMap_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="jjDeptMap_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="jjDeptMap_gridtable_div"
			class="grid-wrapdiv"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="jjDeptMap_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="jjDeptMap_gridtable_addTile">
				<s:text name="jjDeptMapNew.title"/>
			</label> 
			<label style="display: none"
				id="jjDeptMap_gridtable_editTile">
				<s:text name="jjDeptMapEdit.title"/>
			</label>
			<label style="display: none"
				id="jjDeptMap_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="jjDeptMap_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_jjDeptMap_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="jjDeptMap_gridtable"></table>
		<div id="jjDeptMapPager"></div>
</div>
	</div>
	<div class="panelBar" id="jjDeptMap_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="jjDeptMap_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="jjDeptMap_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="jjDeptMap_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
