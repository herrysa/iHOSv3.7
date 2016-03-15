
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<html>
<head>
<script type="text/javascript">
 
	function trainStaffAddGridReload(){
		var urlString = "hrPersonGridList";
	    var trainStaffAddName=jQuery("#trainStaffAdd_search_name").val();
	    var personCode=jQuery("#trainStaffAdd_search_personCode").val();
	    var birthdayFrom=jQuery("#trainStaff_search_birthday_from").val();
	    var birthdayTo=jQuery("#trainStaff_search_birthday_to").val();
	    var sex=jQuery("#trainStaff_search_sex").val();
	    
	    
		urlString=urlString+"?filter_LIKES_name="+trainStaffAddName;
		urlString=urlString+"&filter_LIKES_personCode="+personCode+"&filter_EQS_sex="+sex;
		urlString=urlString+"&filter_GED_birthday="+birthdayFrom+"&filter_LED_birthday="+birthdayTo;
		urlString=urlString+"&requirementId="+"${requirementId}";
		urlString=encodeURI(urlString);
		jQuery("#trainStaffAdd_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var hrPersonLayout;
			  var hrPersonGridIdString="#trainStaffAdd_gridtable";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
// 		hrPersonLayout = makeLayout({
// 			baseName: 'hrPerson', 
// 			tableIds: 'hrPerson_gridtable'
// 		}, null);
var hrPersonGrid = jQuery(hrPersonGridIdString);
    hrPersonGrid.jqGrid({
    	url : "hrPersonGridList?requirementId="+"${requirementId}",
    	editurl:"hrPersonGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'hrDepartment.name',index:'hrDepartment.name',align:'left',width : 100,label : '<s:text name="trainStaff.hrDepartment" />',hidden:false,highsearch:true},
{name:'postType.name',index:'postType.name',align:'left',width : 100,label : '<s:text name="trainStaff.postType" />',hidden:false},	
{name:'name',index:'name',align:'left',width : 100,label : '<s:text name="trainStaff.name" />',hidden:false},	
{name:'sex',index:'sex',align:'center',width : 50,label : '<s:text name="trainStaff.sex" />',hidden:false},
{name:'birthday',index:'birthday',align:'center',width : 100,label : '<s:text name="trainStaff.birthday" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
{name:'people',index:'people',align:'center',width : 50,label : '<s:text name="trainStaff.people" />',hidden:false},	
{name:'idNumber',index:'idNumber',align:'left',width : 100,label : '<s:text name="trainStaff.idNumber" />',hidden:false},	
{name:'personId',index:'personId',align:'left',width : 100,label : '<s:text name="trainStaff.personId" />',hidden:true,key:true},		
{name:'personCode',index:'personCode',align:'left',width : 100,label : '<s:text name="trainStaff.personCode" />',hidden:false}
/*
{name:'cnCode',index:'cnCode',align:'center',label : '<s:text name="hrPerson.cnCode" />',hidden:false},				
{name:'comeFrom',index:'comeFrom',align:'center',label : '<s:text name="hrPerson.comeFrom" />',hidden:false},				
{name:'dealDate',index:'dealDate',align:'center',label : '<s:text name="hrPerson.dealDate" />',hidden:false,formatter:'date'},				
{name:'degree',index:'degree',align:'center',label : '<s:text name="hrPerson.degree" />',hidden:false},				
{name:'disable',index:'disable',align:'center',label : '<s:text name="hrPerson.disable" />',hidden:false,formatter:'checkbox'},				
{name:'duty',index:'duty',align:'center',label : '<s:text name="hrPerson.duty" />',hidden:false},				
{name:'dutyDay',index:'dutyDay',align:'center',label : '<s:text name="hrPerson.dutyDay" />',hidden:false,formatter:'date'},				
{name:'educationalLevel',index:'educationalLevel',align:'center',label : '<s:text name="hrPerson.educationalLevel" />',hidden:false},				
{name:'email',index:'email',align:'center',label : '<s:text name="hrPerson.email" />',hidden:false},				
{name:'foreigner',index:'foreigner',align:'center',label : '<s:text name="hrPerson.foreigner" />',hidden:false,formatter:'checkbox'},				
{name:'formal',index:'formal',align:'center',label : '<s:text name="hrPerson.formal" />',hidden:false,formatter:'checkbox'},				
{name:'graduateDay',index:'graduateDay',align:'center',label : '<s:text name="hrPerson.graduateDay" />',hidden:false,formatter:'date'},				
{name:'insuCode',index:'insuCode',align:'center',label : '<s:text name="hrPerson.insuCode" />',hidden:false},				
{name:'jjmark',index:'jjmark',align:'center',label : '<s:text name="hrPerson.jjmark" />',hidden:false,formatter:'checkbox'},				
{name:'jobLevel',index:'jobLevel',align:'center',label : '<s:text name="hrPerson.jobLevel" />',hidden:false},				
{name:'jobSlry',index:'jobSlry',align:'center',label : '<s:text name="hrPerson.jobSlry" />',hidden:false,formatter:'number'},				
{name:'jobTitle',index:'jobTitle',align:'center',label : '<s:text name="hrPerson.jobTitle" />',hidden:false},				
{name:'mobilePhone',index:'mobilePhone',align:'center',label : '<s:text name="hrPerson.mobilePhone" />',hidden:false},				
{name:'natureCode',index:'natureCode',align:'center',label : '<s:text name="hrPerson.natureCode" />',hidden:false},				
{name:'note',index:'note',align:'center',label : '<s:text name="hrPerson.note" />',hidden:false},				
{name:'nowTitleCode',index:'nowTitleCode',align:'center',label : '<s:text name="hrPerson.nowTitleCode" />',hidden:false},				
{name:'nowTitleTime',index:'nowTitleTime',align:'center',label : '<s:text name="hrPerson.nowTitleTime" />',hidden:false,formatter:'date'},				
{name:'nurseAge',index:'nurseAge',align:'center',label : '<s:text name="hrPerson.nurseAge" />',hidden:false,formatter:'integer'},				
{name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="hrPerson.orgCode" />',hidden:false},				
{name:'passWord',index:'passWord',align:'center',label : '<s:text name="hrPerson.passWord" />',hidden:false},				
{name:'pay',index:'pay',align:'center',label : '<s:text name="hrPerson.pay" />',hidden:false,formatter:'checkbox'},				
{name:'payWay',index:'payWay',align:'center',label : '<s:text name="hrPerson.payWay" />',hidden:false},				
{name:'personAge',index:'personAge',align:'center',label : '<s:text name="hrPerson.personAge" />',hidden:false,formatter:'integer'},				
{name:'personDesc',index:'personDesc',align:'center',label : '<s:text name="hrPerson.personDesc" />',hidden:false},				
{name:'personInDay',index:'personInDay',align:'center',label : '<s:text name="hrPerson.personInDay" />',hidden:false,formatter:'date'},				
{name:'personPolCode',index:'personPolCode',align:'center',label : '<s:text name="hrPerson.personPolCode" />',hidden:false},				
{name:'personStation',index:'personStation',align:'center',label : '<s:text name="hrPerson.personStation" />',hidden:false},				
{name:'personTimeCode',index:'personTimeCode',align:'center',label : '<s:text name="hrPerson.personTimeCode" />',hidden:false},				
{name:'professional',index:'professional',align:'center',label : '<s:text name="hrPerson.professional" />',hidden:false},				
{name:'ratio',index:'ratio',align:'center',label : '<s:text name="hrPerson.ratio" />',hidden:false,formatter:'number'},				
{name:'salary',index:'salary',align:'center',label : '<s:text name="hrPerson.salary" />',hidden:false,formatter:'number'},				
{name:'salaryNumber',index:'salaryNumber',align:'center',label : '<s:text name="hrPerson.salaryNumber" />',hidden:false},				
{name:'school',index:'school',align:'center',label : '<s:text name="hrPerson.school" />',hidden:false},				
{name:'slryBDate',index:'slryBDate',align:'center',label : '<s:text name="hrPerson.slryBDate" />',hidden:false,formatter:'date'},				
{name:'slryLevel',index:'slryLevel',align:'center',label : '<s:text name="hrPerson.slryLevel" />',hidden:false},				
{name:'softWorkAge',index:'softWorkAge',align:'center',label : '<s:text name="hrPerson.softWorkAge" />',hidden:false,formatter:'integer'},				
{name:'status',index:'status',align:'center',label : '<s:text name="hrPerson.status" />',hidden:false},				
{name:'stock',index:'stock',align:'center',label : '<s:text name="hrPerson.stock" />',hidden:false,formatter:'checkbox'},				
{name:'teacherNurse',index:'teacherNurse',align:'center',label : '<s:text name="hrPerson.teacherNurse" />',hidden:false,formatter:'checkbox'},				
{name:'titleDay',index:'titleDay',align:'center',label : '<s:text name="hrPerson.titleDay" />',hidden:false,formatter:'date'},				
{name:'workAge',index:'workAge',align:'center',label : '<s:text name="hrPerson.workAge" />',hidden:false,formatter:'integer'},				
{name:'workBegin',index:'workBegin',align:'center',label : '<s:text name="hrPerson.workBegin" />',hidden:false,formatter:'date'},				
{name:'workPhone',index:'workPhone',align:'center',label : '<s:text name="hrPerson.workPhone" />',hidden:false},				
{name:'zcGraduateDay',index:'zcGraduateDay',align:'center',label : '<s:text name="hrPerson.zcGraduateDay" />',hidden:false,formatter:'date'},				
{name:'zcPersonDegreeCode',index:'zcPersonDegreeCode',align:'center',label : '<s:text name="hrPerson.zcPersonDegreeCode" />',hidden:false},				
{name:'zcSchool',index:'zcSchool',align:'center',label : '<s:text name="hrPerson.zcSchool" />',hidden:false}		*/		

        ],
        jsonReader : {
			root : "hrPersons", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'personId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="hrPersonList.title" />',
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
			 gridContainerResize('trainStaffAdd','div');
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"trainStaffAdd_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("trainStaffAdd_gridtable");
      	   initFlag = initColumn('trainStaffAdd_gridtable','com.huge.ihos.hr.hrPerson.model.HrPerson',initFlag);
      	 }
    });
    jQuery(hrPersonGrid).jqGrid('bindKeys');
    
    
    jQuery("#trainStaffAdd_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){  
    	var url="trainStaffGridEdit?oper=staffSave&requirementId="+"${requirementId}";
        var sid = jQuery("#trainStaffAdd_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length == 0){       	
 			alertMsg.error("请选择记录。");
 			return;
 			} else {
 					url = url+"&id="+sid+"&navTabId=trainStaff_gridtable";
 					alertMsg.confirm("确认选择？", {
 						okCall : function() {
 							$.post(url,function(data) {
 								formCallBack(data);
 								$.pdialog.closeCurrent();
 							});
 						}
 					});
 				}
	}); 
  	});
</script>
</head>
<body>
<div class="page">
<div id="trainStaffAdd_container">
			<div id="trainStaffAdd_layout-center"
				class="pane ui-layout-center">
	<div id="trainStaffAdd_pageHeader" class="pageHeader">
			<div class="searchBar">
			<div class="searchContent">
			<form id="trainStaffAdd_search_form" style="white-space: break-all;word-wrap:break-word;">
				<label style="float:none;white-space:nowrap" >
						<s:text name='trainStaff.name'/>:
      						 <input type="text" id="trainStaffAdd_search_name"/>
						</label>
						<s:text name='trainStaff.personCode'/>:
      						 <input type="text" id="trainStaffAdd_search_personCode"/>
						</label>
						<label style="float:none;white-space:nowrap" >
						<s:text name='trainStaff.birthday'/>:
						<input type="text"	id="trainStaff_search_birthday_from" style="width:80px;height:15px" class="Wdate" value="<fmt:formatDate value='${currentSystemVariable.periodBeginDate}' pattern='yyyy-MM-dd' />" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'trainStaff_search_birthday_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="trainStaff_search_birthday_to" style="width:80px;height:15px" class="Wdate" value="${currentSystemVariable.businessDate}" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'trainStaff_search_birthday_from\')}'})">
						</label>
						<label style="float:none;white-space:nowrap" >
						<s:text name='trainStaff.sex'/>:
						<s:select id="trainStaff_search_sex"  list="#{'':'--','男':'男','女':'女'}" style="width:80px;font-size:9pt;" ></s:select>
						</label>	
				<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="trainStaffAddGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="trainStaffAddGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="trainStaffAdd_buttonBar">
			<ul class="toolBar">
				<li><a id="trainStaffAdd_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span>确定</span>
				</a>
				</li>
			</ul>
		</div>
		<div id="trainStaffAdd_gridtable_div" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:personId;width:900;height:550">
			<input type="hidden" id="trainStaffAdd_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="trainStaffAdd_gridtable_addTile">
				<s:text name="trainStaffAddNew.title"/>
			</label> 
			<label style="display: none"
				id="trainStaffAdd_gridtable_editTile">
				<s:text name="trainStaffAddEdit.title"/>
			</label>
			<label style="display: none"
				id="trainStaffAdd_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="trainStaffAdd_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_trainStaffAdd_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
<div id="trainStaffAdd_table_list" >
 <table id="trainStaffAdd_gridtable"></table></div>
		<div id="trainStaffAddPager"></div>
	</div>
	<div class="panelBar" id="trainStaffAdd_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainStaffAdd_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainStaffAdd_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
	</div>
		<div id="trainStaffAdd_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	</div>
	</div>
</div>
</body>
</html>