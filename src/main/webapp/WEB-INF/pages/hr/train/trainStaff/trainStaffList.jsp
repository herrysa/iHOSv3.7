
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function trainStaffGridReload(){
		var urlString = "trainStaffGridList";
		var code = jQuery("#search_trainStaff_personCode").val();
		var name = jQuery("#search_trainStaff_name").val();
		
	
		urlString=urlString+"?filter_LIKES_hrPerson.personCode="+code+"&filter_LIKES_hrPerson.name="+name;
		urlString=urlString+"&filter_EQS_trainRequirement.id="+"${requirementId}";
		urlString=encodeURI(urlString);
		jQuery("#trainStaff_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var trainStaffLayout;
			  var trainStaffGridIdString="#trainStaff_gridtable";
	
	jQuery(document).ready(function() { 
		trainStaffLayout = makeLayout({
			baseName: 'trainStaff', 
			tableIds: 'trainStaff_gridtable'
		}, null);
var trainStaffGrid = jQuery(trainStaffGridIdString);
    trainStaffGrid.jqGrid({
    	url : "trainStaffGridList?filter_EQS_trainRequirement.id="+"${requirementId}",
    	editurl:"trainStaffGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="trainStaff.id" />',hidden:true,key:true},				
{name:'hrPerson.hrDepartment.name',index:'hrPerson.hrDepartment.name',align:'left',width : 100,label : '<s:text name="trainStaff.hrDepartment" />',hidden:false,highsearch:true},
{name:'hrPerson.postType.name',index:'hrPerson.postType.name',align:'left',width : 100,label : '<s:text name="trainStaff.postType" />',hidden:false},	
{name:'hrPerson.name',index:'hrPerson.name',align:'left',width : 100,label : '<s:text name="trainStaff.name" />',hidden:false},	
{name:'hrPerson.sex',index:'hrPerson.sex',align:'center',width : 50,label : '<s:text name="trainStaff.sex" />',hidden:false},
{name:'hrPerson.birthday',index:'hrPerson.birthday',align:'center',width : 100,label : '<s:text name="trainStaff.birthday" />',hidden:false,formatter:'date',formatoptions:{newformat : 'Y-m-d'}},
{name:'hrPerson.people',index:'hrPerson.people',align:'center',width : 50,label : '<s:text name="trainStaff.people" />',hidden:false},	
{name:'hrPerson.idNumber',index:'hrPerson.idNumber',align:'left',width : 100,label : '<s:text name="trainStaff.idNumber" />',hidden:false},	
{name:'hrPerson.personId',index:'hrPerson.personId',align:'left',width : 100,label : '<s:text name="trainStaff.personId" />',hidden:true,key:true},		
{name:'hrPerson.personCode',index:'hrPerson.personCode',align:'left',width : 100,label : '<s:text name="trainStaff.personCode" />',hidden:false}
        ],
        jsonReader : {
			root : "trainStaffs", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'id',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="trainStaffList.title" />',
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
			 /*2015.08.27 form search change*/
			 gridContainerResize('trainStaff','div');
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"trainStaff_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("trainStaff_gridtable");
       	} 

    });
    jQuery(trainStaffGrid).jqGrid('bindKeys');
    
	
    jQuery("#trainStaff_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
    	var url="trainStaffAdd?navTabId=trainStaff_gridtable"+"&requirementId="+"${requirementId}";
		var winTitle='<s:text name="trainStaffNew.title"/>';
		$.pdialog.open(url,'addTrainStaff',winTitle, {mask:true,width : 960,height : 630});
	}); 
	
	//trainStaffLayout.resizeAll();
  	});
</script>

<div class="page">
<div id="trainStaff_container">
			<div id="trainStaff_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader" id="trainStaff_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
					<form id="trainRequirement_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
							<s:text name='trainStaff.personCode'/>:
						 	<input type="text"  id="search_trainStaff_personCode" />
						</label>
					<label style="float:none;white-space:nowrap" >
							<s:text name='trainStaff.name'/>:
						 	<input type="text"  id="search_trainStaff_name" />
						</label>	
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="trainStaffGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="trainStaffGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="trainStaff_buttonBar">
			<ul class="toolBar">
				<li><a id="trainStaff_gridtable_add_custom" class="addbutton" href="javaScript:" ><span>人员引入</span>
				</a>
				</li>
				<li><a id="trainStaff_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
			</ul>
		</div>
		<div id="trainStaff_gridtable_div" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="trainStaff_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="trainStaff_gridtable_addTile">
				<s:text name="trainStaffNew.title"/>
			</label> 
			<label style="display: none"
				id="trainStaff_gridtable_editTile">
				<s:text name="trainStaffEdit.title"/>
			</label>
			<label style="display: none"
				id="trainStaff_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="trainStaff_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_trainStaff_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="trainStaff_gridtable"></table>
		<div id="trainStaffPager"></div>
</div>
	</div>
	<div class="panelBar" id="trainStaff_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainStaff_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainStaff_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="trainStaff_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>