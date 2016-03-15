
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var recruitNeedGridIdString="#viewRecruitNeed_gridtable";
	jQuery(document).ready(function() { 
		var viewRecruitNeedFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('viewRecruitNeed_container','viewRecruitNeed_gridtable',viewRecruitNeedFullSize);
/* 		$(window).resize(function(){
			var height = jQuery("#viewRecruitNeed_gridtable_div").height();
			jQuery("#viewRecruitNeed_container").css("height",height+33);
		}); */
		hrDepartmentCurrentLeftTreeInViewRecruitNeed();
		var initFlag = 0;
		var recruitNeedGrid = jQuery(recruitNeedGridIdString);
	    recruitNeedGrid.jqGrid({
	    	url : "recruitNeedGridList?filter_EQS_state=2",
	    	editurl:"recruitNeedGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="recruitNeed.id" />',hidden:true,key:true},	
				{name:'code',index:'code',width : '100',align:'left',label : '<s:text name="recruitNeed.code" />',hidden:false,highsearch:true},
				{name:'codeHidden',index:'codeHidden',width : '80',align:'center',label : 'codeHidden',hidden:true},
				{name:'name',index:'name',width : '100',align:'left',label : '<s:text name="recruitNeed.name" />',hidden:false,highsearch:true},
				{name:'hrDept.hrOrg.orgname',index:'hrDept.hrOrg.orgname',align:'left',width : '130',label : '<s:text name="recruitNeed.orgCode" />',hidden:false,highsearch:true},
				{name:'hrDept.name',index:'hrDept.name',width : '80',align:'left',label : '<s:text name="recruitNeed.dept" />',hidden:false,highsearch:true},
				{name:'post',index:'post',width : '60',align:'left',label : '<s:text name="recruitNeed.post" />',hidden:false,highsearch:true},
				{name:'recruitNumber',index:'recruitNumber',width : '50',align:'right',label : '<s:text name="recruitNeed.recruitNumber" />',hidden:false,formatter:'integer',highsearch:true},				
				{name:'state',index:'state',width : '40',align:'center',label : '<s:text name="recruitNeed.state" />',hidden:true,formatter : 'select',edittype : 'select',editoptions : {value : '1:新建;2:已审核'},highsearch:false},	
				{name:'ageStart',index:'ageStart',width : '50',align:'right',label : '<s:text name="recruitNeed.ageStart" />',hidden:false,formatter:'integer',highsearch:true},
				{name:'ageEnd',index:'ageEnd',width : '50',align:'right',label : '<s:text name="recruitNeed.ageEnd" />',hidden:false,formatter:'integer',highsearch:true},
				{name:'sex',index:'sex',width : '60',align:'center',label : '<s:text name="recruitNeed.sex" />',hidden:false,highsearch:true},	
				{name:'maritalStatus',index:'maritalStatus',width : '60',align:'left',label : '<s:text name="recruitNeed.maritalStatus" />',hidden:false,highsearch:true},
				{name:'educationLevel',index:'educationLevel',width : '60',align:'left',label : '<s:text name="recruitNeed.educationLevel" />',hidden:false,highsearch:true},
				{name:'profession',index:'profession',width : '60',align:'left',label : '<s:text name="recruitNeed.profession" />',hidden:false,highsearch:true},
				{name:'politicsStatus',index:'politicsStatus',width : '60',align:'left',label : '<s:text name="recruitNeed.politicsStatus" />',hidden:false,highsearch:true},
				{name:'startDate',width : '70',index:'startDate',align:'center',label : '<s:text name="recruitNeed.startDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'endDate',index:'endDate',width : '70',align:'center',label : '<s:text name="recruitNeed.endDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'jobRequirements',index:'jobRequirements',width : '150',align:'left',label : '<s:text name="recruitNeed.jobRequirements" />',hidden:true,highsearch:true,formatter:stringFormatter},	
				{name:'postResponsibility',index:'postResponsibility',width : '150',align:'left',label : '<s:text name="recruitNeed.postResponsibility" />',hidden:true,highsearch:true,formatter:stringFormatter},				
				{name:'otherRequirements',index:'otherRequirements',width : '150',align:'left',label : '<s:text name="recruitNeed.otherRequirements" />',hidden:true,highsearch:true,formatter:stringFormatter},
				{name:'workExperience',index:'workExperience',width : '100',align:'left',label : '<s:text name="recruitNeed.workExperience" />',hidden:true,highsearch:true},				
				{name:'workplace',index:'workplace',width : '100',align:'left',label : '<s:text name="recruitNeed.workplace" />',hidden:true,highsearch:true},	
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="期间" />',hidden:false,highsearch:true},	
				{name:'makeDate',index:'makeDate',width : '70',align:'center',label : '<s:text name="recruitNeed.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'maker.name',index:'maker.name',width : '70',align:'left',label : '<s:text name="recruitNeed.maker" />',hidden:false,highsearch:true},
				{name:'checkDate',index:'checkDate',width : '70',align:'center',label : '<s:text name="recruitNeed.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'checker.name',index:'checker.name',width : '70',align:'left',label : '<s:text name="recruitNeed.checker" />',hidden:false,highsearch:true},
				{name:'doneDate',index:'doneDate',width : '70',align:'center',label : '<s:text name="recruitNeed.doneDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'doner.name',index:'doner.name',width : '70',align:'left',label : '<s:text name="recruitNeed.doner" />',hidden:false,highsearch:true},
				{name:'remark',index:'remark',width : '250',align:'left',label : '<s:text name="recruitNeed.remark" />',hidden:false,highsearch:true,formatter:stringFormatter}

	        ],
	        jsonReader : {
				root : "recruitNeeds", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'code',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="recruitNeedList.title" />',
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
				 gridContainerResize('viewRecruitNeed','layout');
				var rowNum = jQuery(this).getDataIDs().length;
		        if(rowNum>0){
		        	var rowIds = jQuery(this).getDataIDs();
		            var ret = jQuery(this).jqGrid('getRowData');
		            var id='';
		            for(var i=0;i<rowNum;i++){
		            	id=rowIds[i];
		            	setCellText(this,id,'codeHidden',ret[i]["code"]);
		            	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewRecruitNeedRecordInSelect(\''+id+'\');">'+ret[i]["code"]+'</a>');
		            }
		        }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
	         	var dataTest = {"id":"viewRecruitNeed_gridtable"};
	      	   	jQuery.publish("onLoadSelect",dataTest,null);
	      	    initFlag = initColumn('viewRecruitNeed_gridtable','com.huge.ihos.hr.recruitNeed.model.RecruitNeed',initFlag);
	       	} 
	    });
	    jQuery(recruitNeedGrid).jqGrid('bindKeys');
   
		jQuery("#viewRecruitNeed_gridtable_addPlan_custom").unbind( 'click' ).bind("click",function(){   
	        var sid = jQuery("#viewRecruitNeed_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null|| sid.length == 0){       	
				alertMsg.error("请选择记录。");
				return;
			} else {
				alertMsg.confirm("确认生成招聘计划,所选招聘需求将合并?", {
					okCall : function() {
						calRecruitNeedToPlan(sid);
						$.pdialog.closeCurrent();
					}
				});
			}
		}); 
	 
  	});
	function calRecruitNeedToPlan(sid){
		var recruitNumber=0;
		var maxRowId="0";
		var needIds="";
		var code="";
		for(var i = 0;i < sid.length; i++){
			var rowId = sid[i];
			if(i==0){
				needIds=rowId;
			}else{
				needIds+=","+rowId;
			}
			var row = jQuery("#viewRecruitNeed_gridtable").jqGrid('getRowData',rowId);
			recruitNumber=parseInt(recruitNumber)+parseInt(row['recruitNumber']);
			if(i==0){
				code=row['codeHidden'];
				maxRowId=rowId;
			}
			//console.log(code);
			if(code<row['codeHidden']){
				maxRowId=rowId;
				code=row['codeHidden'];
			}
		}
		jQuery("#recruitPlan_needIds").val(needIds);
		var maxrow = jQuery("#viewRecruitNeed_gridtable").jqGrid('getRowData',maxRowId);
		jQuery("#recruitPlan_recruitNumber").val(recruitNumber);
		addValueAfterTrim("recruitNeed_orgCode_id",maxrow['orgCode']);
		addValueAfterTrim("recruitNeed_orgCode",maxrow['orgCode']);
		addValueAfterTrim("recruitNeed_orgCode_id",maxrow['org.orgname']);
		addValueAfterTrim("recruitPlan_post_id",maxrow['post']);
		addValueAfterTrim("recruitPlan_post",maxrow['post']);
		addValueAfterTrim("recruitPlan_ageStart",maxrow['ageStart']);
		addValueAfterTrim("recruitPlan_ageEnd",maxrow['ageEnd']);
		addValueAfterTrim("recruitPlan_workExperience",maxrow['workExperience']);
		addValueAfterTrim("recruitPlan_sex",maxrow['sex']);
		addValueAfterTrim("recruitPlan_educationLevel",maxrow['educationLevel']);
		addValueAfterTrim("recruitPlan_profession",maxrow['profession']);
		addValueAfterTrim("recruitPlan_maritalStatus",maxrow['maritalStatus']);
		addValueAfterTrim("recruitPlan_politicsStatus",maxrow['politicsStatus']);
		addValueAfterTrim("recruitPlan_startDate",maxrow['startDate']);
		addValueAfterTrim("recruitPlan_endDate",maxrow['endDate']);
		addValueAfterTrim("recruitPlan_workplace",maxrow['workplace']);
		addValueAfterTrim("recruitPlan_postResponsibility",maxrow['postResponsibility']);
		addValueAfterTrim("recruitPlan_jobRequirements",maxrow['jobRequirements']);
		addValueAfterTrim("recruitPlan_otherRequirements",maxrow['otherRequirements']);
	}
	function addValueAfterTrim(id,value){
		value=$.trim(value);
		if(value){
			jQuery("#"+id).val(value);
		}
	}
	function viewRecruitNeedRecordInSelect(id){
		var url = "editRecruitNeed?popup=true&id="+id+"&oper=view";
		$.pdialog.open(url,'viewRecruitNeed_'+id,'招聘需求信息', {mask:true,width : 700,height : 500,maxable:false});
	}
	function hrDepartmentCurrentLeftTreeInViewRecruitNeed() {
		var url = "makeHrDeptTree";
// 		var showDisabled = jQuery("#viewRecruitNeed_showDisabled").attr("checked");
// 		if(showDisabled){
// 			url += "?showDisabled=true"; 
// 		}
		$.get(url, {"_" : $.now()}, function(data) {
			var hrDeptTreeData = data.hrDeptTreeNodes;
			var hrDeptTree = $.fn.zTree.init($("#hrDepartmentViewRecruitNeedTreeLeft"),ztreesetting_hrDeptTreeInViewRecruitNeed, hrDeptTreeData);
			var nodes = hrDeptTree.getNodes();
			hrDeptTree.expandNode(nodes[0], true, false, true);
			hrDeptTree.selectNode(nodes[0],true);
			toogleShowDisabledDept(jQuery("#viewRecruitNeed_showDisabled")[0],jQuery("#viewRecruitNeed_showPersonCount")[0],'hrDepartmentViewRecruitNeedTreeLeft');
			toogleShowPersonCount(jQuery("#viewRecruitNeed_showDisabled")[0],jQuery("#viewRecruitNeed_showPersonCount")[0],'hrDepartmentViewRecruitNeedTreeLeft');
		});
	}
	var ztreesetting_hrDeptTreeInViewRecruitNeed = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false,
			fontCss : setDisabledDeptFontCss
		},
		callback : {
			beforeDrag:function(){return false},
			onClick : onModuleClick
		},
		data : {
			key : {
				name : "name"
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId"
			}
		}
	};
	
	function onModuleClick(event, treeId, treeNode, clickFlag) { 
	    var urlString = "recruitNeedGridList?filter_EQS_state=2";
	    var nodeId = treeNode.id;
	    if(nodeId!="-1"){
	    	if(treeNode.subSysTem==='ORG'){
		    	urlString += "&orgCode="+nodeId;
	    	}else{
		    	urlString += "&departmentId="+nodeId;
	    	}
	    }
	    var showDisabled = jQuery("#viewRecruitNeed_showDisabled").attr("checked");
	    if(showDisabled){
	    	urlString += "&showDisabled=true"
	    }
		urlString=encodeURI(urlString);
		jQuery("#viewRecruitNeed_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	function showDisabledDeptInViewRecruitNeed(obj){
		var urlString = jQuery("#viewRecruitNeed_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabled','');
		if(obj.checked){
			urlString += "&showDisabled=true";
		}
		toogleShowDisabledDept(obj,jQuery("#viewRecruitNeed_showPersonCount")[0],'hrDepartmentViewRecruitNeedTreeLeft');
		//hrDepartmentCurrentLeftTreeInViewRecruitNeed();
		jQuery("#viewRecruitNeed_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
</script>

<div class="page">
	<div class="pageHeader" id="viewRecruitNeed_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="viewRecruitNeed_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitNeed.code'/>:
					 	<input type="text" name="filter_LIKES_code" style="width:100px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitNeed.name'/>:
					 	<input type="text" name="filter_LIKES_name" style="width:100px"/>
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitNeed.post'/>:
					 	<input type="text" name="filter_LIKES_post" style="width:100px"/>
					</label>	
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%--      				 <s:text name="期间"/>: --%>
<!--      				 <input type="text"  name="filter_EQS_yearMonth" style="width:100px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--      				 </label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitNeed.checkDate'/>:
						<input type="text"	id="viewRecruitNeed_search_check_date_from" name="filter_GED_checkDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'viewRecruitNeed_search_check_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="viewRecruitNeed_search_check_date_to" name="filter_LED_checkDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'viewRecruitNeed_search_check_date_from\')}'})">
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='recruitNeed.remark'/>:
					 	<input type="text" name="filter_LIKES_remark" style="width:100px"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('viewRecruitNeed_search_form','viewRecruitNeed_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('viewRecruitNeed_search_form','viewRecruitNeed_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div></li> -->
				
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="viewRecruitNeed_buttonBar">
			<ul class="toolBar">
				<li>
					<a id="viewRecruitNeed_gridtable_addPlan_custom" class="confirmbutton"  href="javaScript:"><span><s:text name="生成计划" /></span></a>
				</li>
				<li>
     				<a class="settlebutton"  href="javaScript:setColShow('viewRecruitNeed_gridtable','com.huge.ihos.hr.recruitNeed.model.RecruitNeedSelect')"><span><s:text name="button.setColShow" /></span></a>
   				 </li>
			</ul>
		</div>
		<div id="viewRecruitNeed_container">
			<div id="viewRecruitNeed_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
					<span>
						显示停用部门
						<input id="viewRecruitNeed_showDisabled" type="checkbox" onclick="showDisabledDeptInViewRecruitNeed(this)"/>
					</span>
					<span>
						显示人员数
						<input id="viewRecruitNeed_showPersonCount" type="checkbox" onclick="toogleShowPersonCount(jQuery('#viewRecruitNeed_showDisabled')[0],this,'hrDepartmentViewRecruitNeedTreeLeft')"/>
					</span>
					<label id="viewRecruitNeed_expandTree" onclick="toggleExpandTree(this,'hrDepartmentViewRecruitNeedTreeLeft')">展开</label>
				</div>
  	 		 <div id="hrDepartmentViewRecruitNeedTreeLeft" class="ztree"></div>
  			</div>
  			<div id="viewRecruitNeed_layout-center" class="pane ui-layout-center">
				<div id="viewRecruitNeed_gridtable_div" class="grid-wrapdiv">
					<input type="hidden" id="viewRecruitNeed_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_viewRecruitNeed_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 					<table id="viewRecruitNeed_gridtable"></table>
				</div>
				<div class="panelBar" id="viewRecruitNeed_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="viewRecruitNeed_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="viewRecruitNeed_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
			
					<div id="viewRecruitNeed_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>