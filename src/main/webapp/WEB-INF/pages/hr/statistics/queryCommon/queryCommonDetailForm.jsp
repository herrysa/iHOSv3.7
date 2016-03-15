<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
	var	queryCommonViewGridIdString="#queryCommonView_gridtable";
var queryCommonViewGrid = jQuery(queryCommonViewGridIdString);
var initFlag = 0;
   		queryCommonViewGrid.jqGrid({
	    	url : "hrPersonCurrentGridList?queryCommonId="+"${id}",
	    	editurl:"hrPersonCurrentGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
{name:'personId',index:'personId',align:'center',label : '<s:text name="hrPersonSnap.personId" />',hidden:true,key:true,highsearch:false},				
{name:'snapCode',index:'snapCode',align:'center',label : '<s:text name="hrPersonSnap.snapCode" />',hidden:true,highsearch:false},				
{name:'personCode',index:'personCode',align:'left',width:70,label : '<s:text name="hrPersonSnap.personCode" />',hidden:false,highsearch:true},				
{name:'name',index:'name',align:'left',width:80,label : '<s:text name="hrPersonSnap.name" />',hidden:false,highsearch:true},
{name:'hrOrg.orgname',index:'hrOrg.orgname',align:'left',width:130,label : '<s:text name="hrPersonSnap.orgCode" />',hidden:false,highsearch:true},		
{name:'hrOrg.orgCode',index:'hrOrg.orgCode',align:'left',width:130,label : '<s:text name="hrPersonSnap.orgCode" />',hidden:true},		
{name:'department.departmentId',index:'department.departmentId',width:120,align:'left',label : '<s:text name="hrPersonSnap.departmentId" />',hidden:true},				
{name:'department.name',index:'department.name',width:120,align:'left',label : '<s:text name="hrPersonSnap.hrDept" />',hidden:false,highsearch:true},				
{name:'sex',index:'sex',align:'center',width:40,label : '<s:text name="hrPersonSnap.sex" />',hidden:false,highsearch:true},				
{name:'status.name',index:'status.name',align:'left',width:60,label : '<s:text name="hrPersonSnap.empType" />',hidden:false,highsearch:true},	
{name:'postType',index:'postType',align:'left',width:60,label : '<s:text name="hrPersonSnap.postType" />',hidden:false,highsearch:true},				
{name:'duty.name',index:'duty.name',align:'left',width:60,label : '<s:text name="hrPersonSnap.duty" />',hidden:false,highsearch:true},
{name:'jobTitle',index:'jobTitle',align:'left',width:60,label : '<s:text name="hrPersonSnap.jobTitle" />',hidden:false,highsearch:true},	
{name:'age',index:'age',width : '40',align:'right',label : '<s:text name="hrPersonSnap.age" />',hidden:false,formatter:'integer',highsearch:true},
{name:'birthday',index:'birthday',align:'center',width:70,label : '<s:text name="hrPersonSnap.birthday" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
{name:'idNumber',index:'idNumber',align:'left',width:100,label : '<s:text name="hrPersonSnap.idNumber" />',hidden:false,highsearch:true},
{name:'nation',index:'nation',align:'left',width:60,label : '<s:text name="hrPersonSnap.nation" />',hidden:false,highsearch:true},	
{name:'politicalCode',index:'politicalCode',align:'left',width:60,label : '<s:text name="hrPersonSnap.politicalCode" />',hidden:false,highsearch:true},
{name:'workPhone',index:'workPhone',align:'left',width:100,label : '<s:text name="hrPersonSnap.workPhone" />',hidden:false,highsearch:true},
{name:'mobilePhone',index:'mobilePhone',align:'left',width:100,label : '<s:text name="hrPersonSnap.mobilePhone" />',hidden:false,highsearch:true},	
{name:'email',index:'email',align:'left',width:100,label : '<s:text name="hrPersonSnap.email" />',hidden:false,highsearch:true},				
{name:'entryDate',index:'entryDate',align:'center',width:70,label : '<s:text name="hrPersonSnap.entryDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
{name:'educationalLevel',index:'educationalLevel',align:'left',width:60,label : '<s:text name="hrPersonSnap.educationalLevel" />',hidden:false,highsearch:true},
{name:'degree',index:'degree',align:'left',width:60,label : '<s:text name="hrPersonSnap.degree" />',hidden:false,highsearch:true},	
{name:'school',index:'school',align:'left',width:100,label : '<s:text name="hrPersonSnap.school" />',hidden:false,highsearch:true},	
{name:'profession',index:'profession',align:'left',width:80,label : '<s:text name="hrPersonSnap.profession" />',hidden:false,highsearch:true},	
{name:'graduateDate',index:'graduateDate',align:'center',width:70,label : '<s:text name="hrPersonSnap.graduateDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
{name:'ratio',index:'ratio',align:'right',width:60,label : '<s:text name="hrPersonSnap.ratio" />',hidden:false,highsearch:true,formatter:'currency',
	formatoptions:{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 4, prefix: '', suffix:'', defaultValue: '0.0000'}},				
{name:'disable',index:'disable',align:'center',width:40,label : '<s:text name="hrPersonSnap.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},				
{name:'jjmark',index:'jjmark',align:'center',width:40,label : '<s:text name="hrPersonSnap.jjmark" />',hidden:false,formatter:'checkbox',highsearch:true},				
{name:'imagePath',index:'imagePath',align:'center',label : '<s:text name="hrPerson.imagePath" />',hidden:true}
	        ],
	        jsonReader : {
				root : "hrPersonCurrents", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'personCode',
	        viewrecords: true,
	        sortorder: 'asc',
	        //caption:'<s:text name="hrPersonCurrentList.title" />',
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
	            var dataTest = {"id":"queryCommonView_gridtable"};
	      	    jQuery.publish("onLoadSelect",dataTest,null);
// 	      	    makepager("queryCommonView_gridtable");
 				initFlag = initColumn('queryCommonView_gridtable','com.huge.ihos.hr.hrPerson.model.HrPersonCurrent',initFlag);
 					var rowNum = jQuery(this).getDataIDs().length;
	 	            if(rowNum>0){
	 	                var rowIds = jQuery(this).getDataIDs();
	 	                var ret = jQuery(this).jqGrid('getRowData');
	 	                var id='';
	 	                for(var i=0;i<rowNum;i++){
	 	                	  id=rowIds[i];
// 	      					  var snapId=ret[i]["personId"]+'_'+ret[i]["snapCode"];
//   	         			      setCellText(this,id,'name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewRecord(\''+snapId+'\');">'+ret[i]["name"]+'</a>');
	 	                }
	 	            }else{
	 	            	var tw = jQuery(this).outerWidth();
	 	               jQuery(this).parent().width(tw);
	 	               jQuery(this).parent().height(1);
	 	            }
		 	} 
			});
   	 	jQuery(queryCommonViewGrid).jqGrid('bindKeys');
	});
// 	function viewRecord(snapId){
//    		var url = "editHrPersonSnap?snapId="+snapId+"&navTabId=queryCommonView_gridtable";
//    		url=url+"&oper=view";
// 		var winTitle='<s:text name="hrPersonView.title"/>';
// 		$.pdialog.open(url,'editHrPersonSnap',winTitle, {mask:true,width : 800,height : 600});
// 	}

function exportToExcelQueryCommon(gridId,entityName,title,outPutType){
		 var url = jQuery("#"+gridId).jqGrid('getGridParam','url');
		 var formData = jQuery("#"+gridId).jqGrid('getGridParam','formData');
		 var param = url.split("?")[1];
		 //alert(json2str(jQuery("#sourcepayin_gridtable")[0].p.colModel));
		 var colModel = jQuery("#"+gridId).jqGrid('getGridParam','colModel');
		 var colDefine = new Array();
		 var colDefineIndex = 0;
		 for(var mi=0;mi<colModel.length;mi++){
		  var col = colModel[mi];
		  if(col.name!="rn"&&col.name!="cb"&&!col.hidden){
		   var label = col.label?col.label:col.name;
		   var type = col.formatter?col.formatter:1;
		   var align = col.align?col.align:"left";
		   var width = col.width?parseInt(col.width)*20:50*20;
		   colDefine[colDefineIndex] = {name:col.name,type:type,align:align,width:width,label:label};
		   colDefineIndex++;
		  }
		 }
		 var colDefineStr = json2str(colDefine);
		 var page=1,pageSize=20,sortname,sortorder;
		 page = jQuery("#"+gridId).jqGrid('getGridParam','page');
		 pageSize = jQuery("#"+gridId).jqGrid('getGridParam','rowNum');
		 
		 sortname = jQuery("#"+gridId).jqGrid('getGridParam','sortname');
		 sortorder = jQuery("#"+gridId).jqGrid('getGridParam','sortorder');
		 var u =  'outPutExcelForHrPersonSnap?'+param+"&entityName="+entityName;
		 
		 var postData = jQuery("#"+gridId).jqGrid("getGridParam", "postData");
		 postData['entityName']=entityName;
		 postData['title']=title;
		 postData['outPutType']=outPutType;
		 postData['page']=page;
		 postData['pageSize']=pageSize;
		 postData['sortname']=sortname;
		 postData['sortorder']=sortorder;
		 postData['colDefineStr']=colDefineStr;
		 var excelSourceDataType="QueryCommon";
		 postData['excelSourceDataType']=excelSourceDataType;
//  		 var sdata =jQuery("#hrPersonHistory_search_form").serializeObject();
		 $.ajax({
		  url: u,
		  type: 'post',
		  data:postData,
		  dataType: 'json',
		  async:false,
		  error: function(data){
		   alertMsg.error("系统错误！");
		  },
		  success: function(data){
		   var downLoadFileName = data["message"];
		   var filePathAndName = downLoadFileName.split("@@");
		   var url = "${ctx}/downLoadExel?filePath="+filePathAndName[0]+"&fileName="+filePathAndName[1];
		    //url=encodeURI(url);
		    location.href=url; 
		  }
		 }); 
		}
</script>
</head>

<div class="page">
	<div class="pageContent">
	<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="excelbutton" href="javaScript:exportToExcelQueryCommon('queryCommonView_gridtable','HrPersonCurrent','人员数据','page')"><span>导出本页数据 </span> </a>
				</li>
				<li>
					<a class="excelbutton" href="javaScript:exportToExcelQueryCommon('queryCommonView_gridtable','HrPersonCurrent','人员数据','all')"><span>导出当前全部数据 </span> </a>
				</li>
				 <li>
    				 <a class="settlebutton"  href="javaScript:setColShow('queryCommonView_gridtable','com.huge.ihos.hr.hrPerson.model.HrPersonCurrent')"><span><s:text name="button.setColShow" /></span></a>
  				</li>
			</ul>
		</div>
				<div id="queryCommonView_gridtable_div" layoutH="55"  style="margin-left: 2px; margin-top: 2px; overflow: hidden"  class="grid-wrapdiv" class="unitBox" >
					<input type="hidden" id="queryCommonView_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_queryCommonView_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
		 			<table id="queryCommonView_gridtable"></table>
				</div>
				<div class="panelBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="queryCommonView_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="queryCommonView_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="queryCommonView_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
	</div>
</div>





