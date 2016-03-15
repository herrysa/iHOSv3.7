
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script language="javascript" type="text/javascript" src="${ctx}/scripts/DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
 
	function jjAllotGridReload(){
		var urlString = "jjAllotGridList";
		var checkPeriodTxt = jQuery("#checkPeriodTxt").val();
		/* var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val(); */
	
		urlString=urlString+"?filter_EQS_checkPeriod="+checkPeriodTxt;//+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#jjAllot_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var jjAllotLayout;
			  var jjAllotGridIdString="#jjAllot_gridtable";
	
	jQuery(document).ready(function() { 
		jjAllotLayout = makeLayout({
			baseName: 'jjAllot', 
			tableIds: 'jjAllot_gridtable'
		}, null);
var jjAllotGrid = jQuery(jjAllotGridIdString);
    jjAllotGrid.jqGrid({
    	url : "jjAllotGridList",
    	editurl:"jjAllotGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="jjAllot.id" />',hidden:true,key:true},				
{name:'checkPeriod',index:'checkPeriod',align:'left',label : '<s:text name="jjAllot.checkPeriod" />',hidden:false,width:'50'},				
{name:'allotDeptId.name',index:'allotDeptId.name',align:'left',label : '<s:text name="jjAllot.allotDeptId" />',hidden:false,width:'100'},				
{name:'jjDeptId.name',index:'jjDeptId.name',align:'left',label : '<s:text name="jjAllot.jjDeptId" />',hidden:false,width:'100'},				
{name:'itemName',index:'itemName',align:'left',label : '<s:text name="jjAllot.itemName" />',hidden:false,width:'100'},				
{name:'amount',index:'amount',align:'right',label : '<s:text name="jjAllot.amount" />',hidden:false,formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},width:'50'},				
{name:'remark',index:'remark',align:'center',label : '<s:text name="jjAllot.remark" />',hidden:false,width:'200'}				

        ],
        jsonReader : {
			root : "jjAllots", // (2)
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
        //caption:'<s:text name="jjAllotList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:true,
		postData:{filter_EQS_checkPeriod :"${currentPeriod}"},
        onSelectRow: function(rowid) {
       
       	},
		 gridComplete:function(){
			 gridContainerResize("jjAllot","div",0,36);
           var dataTest = {"id":"jjAllot_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("jjAllot_gridtable");
       	} 

    });
    jQuery(jjAllotGrid).jqGrid('bindKeys');
  	});
	
	var quanXian = "${quanXian}";
	function addJjAllot(){
		 if(quanXian=="true"){
			alertMsg.error("您没有奖金科室间分配的权限，请确认后再试！");
			return;
		}
		var url = "editJjAllot?popup=true&navTabId=jjAllot_gridtable";
		var winTitle="新增科室分配信息";
		url = encodeURI(url);
		$.pdialog.open(url, 'addJjAllot', winTitle, {mask:false,width:500,height:300}); 
	}
	function editJjAllot(){
		 if(quanXian=="true"){
			alertMsg.error("您没有奖金科室间分配的权限，请确认后再试！");
			return;
		}
		 var sid = jQuery("#jjAllot_gridtable").jqGrid('getGridParam','selarrrow');
		 //alertMsg.error(sid);
		if(sid==null || sid.length ==0){
			alertMsg.error("请选择数据！");
			return;
		}
	    if(sid.length>1){
			alertMsg.error("一次只能编辑一条数据！");
			return;
		}else{
			var url = "editJjAllot?popup=true&id="+sid+"&navTabId=jjAllot_gridtable";
			var winTitle="新增科室分配信息";
			url = encodeURI(url);
			$.pdialog.open(url, 'editJjAllot', winTitle, {mask:false,width:500,height:300});  
		}
		 
	}
</script>

<div class="page" id="jjAllot_page">
<div id="jjAllot_container">
			<div id="jjAllot_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader" id="jjAllot_pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="jjAllot_form" class="queryarea-form">
					<label class="queryarea-label"><s:text name='jjAllot.checkPeriod'/>:
							<input type="text"	name="filter_EQS_checkPeriod"  onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"  value="${currentPeriod}" >
					</label>
						<%-- <td><s:text name='jjAllot.allotDeptId'/>:
						 	<input type="hidden" id="allotDept_name_id"	name="filter_EQS_allotDeptId.departmentId" >
						 	<input type="text" id="allotDept_name"	name="_exclude_allotDeptId.name" >
						 	<script>
						 	jQuery("#allotDept_name").treeselect({
								dataType:"sql",
								optType:"multi",
								sql:"select deptId id,name,parentDept_id parent from t_department where deptId<>'XT' and jjleaf=1 order by internalCode asc",
								exceptnullparent:false,
								lazy:false,
								callback : {}
							});
						 	</script>
						 </td> --%>
						<label class="queryarea-label"><s:text name='jjAllot.jjDeptId'/>:
						 	<input type="hidden"	id="jjAllot_name_id"	name="filter_EQS_jjDeptId.departmentId" >
						 	<input type="text"	id="jjAllot_name"	name="_exclude_jjDeptId.name" >
						 	<script>
						 	var sql = "select v.deptId id,v.name name,v.jjdepttype+v.orgCode parent,0 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol from v_jj_department v where v.disabled = '0' and v.jjleaf = '1'"
								sql += " union select vj.jjdepttype+vj.orgCode id,k.jjDeptTypeName name ,vj.orgCode parent,1 isParent,null icon, k.jjDeptTypeName orderCol from v_jj_department vj left JOIN KH_Dict_jjDeptType k ON vj.jjdepttype =k.jjDeptTypeId"
								var herpType = "${fns:getHerpType()}";
								if(herpType == "M") {
									sql += " union select t.orgCode id,t.orgname name,'1' parent ,1 isParent ,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,t.orgCode orderCol from T_Org t where t.orgCode <> 'XT' and t.disabled = '0'";
								}
								sql += " order by orderCol";
						 	jQuery("#jjAllot_name").treeselect({
								dataType:"sql",
								optType:"multi",
								sql:sql,
								exceptnullparent:false,
								lazy:false,
								minWidth:'220px',
								callback : {}
							});
						 	</script>
						</label>
						<label class="queryarea-label"><s:text name='jjAllot.itemName'/>:
						 	<input type="text"		name="filter_EQS_itemName" >
						</label>
						<div class="buttonActive" style="float: right;">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearch('jjAllot_form','jjAllot_gridtable')"><s:text name='button.search'/></button>
								</div>
							</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="jjAllot_buttonBar">
			<ul class="toolBar">
				<li><a id="jjAllot_gridtable_add_quanXian" class="addbutton" href="javaScript:addJjAllot()" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="jjAllot_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="jjAllot_gridtable_edit_quanXian" class="changebutton"  href="javaScript:editJjAllot()"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="jjAllot_gridtable_div" 
			class="grid-wrapdiv"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="jjAllot_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="jjAllot_gridtable_addTile">
				<s:text name="jjAllotNew.title"/>
			</label> 
			<label style="display: none"
				id="jjAllot_gridtable_editTile">
				<s:text name="jjAllotEdit.title"/>
			</label>
			<label style="display: none"
				id="jjAllot_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="jjAllot_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_jjAllot_gridtable" style="display:none" class='loading ui-state-default ui-state-active'></div>
 <table id="jjAllot_gridtable"></table>
		<div id="jjAllotPager"></div>
</div>
	</div>
	<div class="panelBar" id="jjAllot_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="jjAllot_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="jjAllot_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="jjAllot_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>