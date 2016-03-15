<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function bulletinGridReload(){
		var urlString = "bulletinGridList";
		var bulletin_subject_search = jQuery("#bulletin_subject_search").val();
		var bulletin_secretLevel_search = jQuery("#bulletin_secretLevel_search").val();
		var bulletin_createTime_search = jQuery("#bulletin_createTime_search").val();
		var bulletin_creator_search = jQuery("#bulletin_creator_search").val();
		var bulletin_department_search = jQuery("#bulletin_department_search").val();
	
		var startTime = "";
		var endTime = "";
		if(bulletin_createTime_search!=null&&bulletin_createTime_search!=""){
			startTime = bulletin_createTime_search+" 00:00:00";
			endTime = bulletin_createTime_search+" 23:59:59";
		}
		
		urlString=urlString+"?filter_LIKES_subject="+bulletin_subject_search+"&filter_EQS_secretLevel="+bulletin_secretLevel_search+"&filter_GET_createTime="+startTime+"&filter_LET_createTime="+endTime+"&filter_LIKES_department="+bulletin_department_search;
		urlString=encodeURI(urlString);
		jQuery("#bulletin_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var bulletinLayout;
			  var bulletinGridIdString="#bulletin_gridtable";
	
	jQuery(document).ready(function() { 
		/* bulletinLayout = makeLayout({
			baseName: 'bulletin', 
			tableIds: 'bulletin_gridtable'
		}, null); */
var bulletinGrid = jQuery(bulletinGridIdString);
    bulletinGrid.jqGrid({
    	url : "bulletinGridList",
    	editurl:"bulletinGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'bulletinId',index:'bulletinId',align:'left',label : '<s:text name="bulletin.bulletinId" />',hidden:true,key:true,formatter:'integer'},				
{name:'subject',index:'subject',align:'left',label : '<s:text name="bulletin.subject" />',hidden:false,width:250},				
{name:'secretLevel',index:'secretLevel',align:'left',label : '<s:text name="bulletin.secretLevel" />',hidden:false},
{name:'department',index:'department',align:'left',label : '<s:text name="bulletin.department" />',hidden:false},
{name:'createTime',index:'createTime',align:'center',label : '<s:text name="bulletin.createTime" />',hidden:false,formatter:'date',formatoptions:{srcformat: 'Y-m-d H:i:s',newformat:"Y-m-d H:i:s"}},				
{name:'creator',index:'creator',align:'left',label : '<s:text name="bulletin.creator" />',hidden:false}				
        ],
        jsonReader : {
			root : "bulletins", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'bulletinId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="bulletinList.title" />',
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
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"bulletin_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   
      	   //makepager("bulletin_gridtable");
       	} 

    });
    jQuery(bulletinGrid).jqGrid('bindKeys');
    
	
	addTreeSelect("list","sync","selectDeptName","selectDeptId","single",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"parentDept_id",filter:""});
	addTreeSelect("list","sync","selectDeptName2","selectDeptId2","single",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"parentDept_id",filter:""});
	///addTreeSelect("tree","sync","selectDeptNamemul","selectDeptIdmul","multi",{tableName:"t_menu",treeId:"menuId",treeName:"menuName",parentId:"parentId",filter:"",classTable:"",classTreeId:"",classTreeName:"",classFilter:""});
	addTreeSelect("tree","sync","selectDeptNamemul","selectDeptIdmul","multi",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"jjdepttype",filter:"",classTable:"KH_Dict_JjDeptType",classTreeId:"jjDeptTypeId",classTreeName:"jjDeptTypeName",classFilter:""});
	//addTreeSelect("tree","async","selectDeptNamemul","selectDeptIdmul","multi",{tableName:"t_formulaField",treeId:"fieldName",treeName:"fieldTitle",parentId:"fieldTitle",filter:"formulaEntityId=2"});
	//bulletinLayout.resizeAll();
	//alert(window.screen.availWidth);
	/* var header = jQuery("#bulletin_pageHeader").outerHeight(true);
	var valve = 63;
	if(jQuery("#bulletin_pageHeader").outerHeight(true)>valve){
		jQuery("#bulletin_pageHeader").find(".subBar").hide();
	}else{
		jQuery("#bulletin_pageHeader").find(".searchContent").find(".buttonActive").hide();
	} */
  	});
	function preViewBulletin(){
		var sid = jQuery("#bulletin_gridtable").jqGrid('getGridParam','selarrrow');
	    if(sid==null || sid.length ==0){
			alertMsg.error("");
			return;
		}
	    if(sid.length>1){
			alertMsg.error("");
			return;
		}else{
			var url = "showBulletin?bulletinId="+ sid;
			var winTitle="公告预览";
			//alert(url);
			url = encodeURI(url);
			$.pdialog.open(url, 'preViewBulletin', winTitle, {mask:false,width:880,height:600});　
		}
	}
</script>
<div class="page">
<!-- <div id="bulletin_container">
			<div id="bulletin_layout-center"
				class="pane ui-layout-center"> -->
	<div id="bulletin_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<label style="float:none;white-space:nowrap" >
					<s:text name='bulletin.subject'/>:
						 	<input type="text"	id="bulletin_subject_search" >
				</label>&nbsp;&nbsp;
				<label style="float:none;white-space:nowrap" >
					<s:text name='bulletin.secretLevel'/>:
						 <s:select key="bulletin.secretLevel"
					name="bulletin.secretLevel" value="%{bulletin.secretLevel}"
					id="bulletin_secretLevel_search" cssClass="required" maxlength="50"
					list="secretLeveList" listKey="value" listValue="content" headerKey="" headerValue="全部"
					emptyOption="false" theme="simple"></s:select>
					</label>&nbsp;&nbsp;
						 <label style="float:none;white-space:nowrap" >
						<s:text name='bulletin.createTime'/>:
						 	<input type="text"	onClick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"	id="bulletin_createTime_search" >
						 </label>&nbsp;&nbsp;
						 <label style="float:none;white-space:nowrap" >
						 <s:text name='bulletin.creator'/>:
						 	<input type="text"		id="bulletin_creator_search" />
						 </label>&nbsp;&nbsp;
						 <label style="float:none;white-space:nowrap" >
						 <s:text name='bulletin.department'/>:
						 	<s:select id="bulletin_department_search" list="departList" listKey="name" listValue="name" headerKey="" headerValue="--"/>
						 </label>&nbsp;&nbsp;
						 <label style="float:none;white-space:nowrap" >
						 <s:text name='bulletin.department'/>:
						 	<input id="selectDeptId" type="hidden"/>
						 	<input id="selectDeptName" />
						 </label>&nbsp;&nbsp;
						 <label style="float:none;white-space:nowrap" >
						 <s:text name='bulletin.department'/>:
						 	<input id="selectDeptId2" type="hidden"/>
						 	<input id="selectDeptName2" />
						 </label>&nbsp;&nbsp;
						 <label style="float:none;white-space:nowrap" >
						 <s:text name='bulletin.department'/>:
						 	<input id="selectDeptIdmul" type="hidden"/>
						 	<input id="selectDeptNamemul" />
						 </label>
					<div class="buttonActive" style="float:right">
								<div class="buttonContent">
									<button type="button" onclick="bulletinGridReload()"><s:text name='button.search'/></button>
								</div>
							</div>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="bulletinGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="bulletin_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="bulletin_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="bulletin_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<li><a  class="previewbutton"  href="javaScript:preViewBulletin()"
					><span><s:text name="button.testPreview" />
					</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="bulletin_gridtable_div" layoutH="120"
			class="grid-wrapdiv"
			buttonBar="optId:bulletinId;width:880;height:600">
			<input type="hidden" id="bulletin_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="bulletin_gridtable_addTile">
				<s:text name="bulletinNew.title"/>
			</label> 
			<label style="display: none"
				id="bulletin_gridtable_editTile">
				<s:text name="bulletinEdit.title"/>
			</label>
			<label style="display: none"
				id="bulletin_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="bulletin_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
			<div id="load_bulletin_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="bulletin_gridtable"></table>
			<div id="bulletinPager"></div>
		</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="bulletin_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="bulletin_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="bulletin_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	</div>
</div>
<!-- </div>
</div> -->
