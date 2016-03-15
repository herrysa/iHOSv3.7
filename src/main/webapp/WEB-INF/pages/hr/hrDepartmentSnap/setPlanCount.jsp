<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var setPlanCountGridIdString="#setPlanCount_gridtable";
	
	jQuery(document).ready(function() {
		jQuery("#setPlanCount_pageHeader").find("select").css("font-size","12px");
		/*------------------------------load rightGrid-----------------------------------------*/
		var initFlag = 0;
		var setPlanCountGrid = jQuery(setPlanCountGridIdString);
		setPlanCountGrid.jqGrid({
	    	url : "setPlanCountGridList?1=1",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'departmentId',index:'departmentId',align:'left',width:'20',label : '<s:text name="hrDepartmentSnap.snapId" />',hidden:true,key:true,highsearch:false},
				{name:'hrOrg.orgname',index:'hrOrg.orgname',align:'left',width:'0',label : '<s:text name="hrDepartmentSnap.orgName" />',hidden:false,highsearch:true},			
				{name:'orgCode',index:'orgCode',align:'left',width:'0',label : '<s:text name="hrDepartmentSnap.orgName" />',hidden:true},			
				{name:'deptCode',index:'deptCode',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.deptCode" />',hidden:false,highsearch:true},				
				{name:'snapCode',index:'snapCode',align:'left',width:'80',label : '<s:text name="hrDepartmentSnap.snapCode" />',hidden:true,highsearch:false},
				{name:'name',index:'name',align:'left',width:'200',label : '<s:text name="hrDepartmentSnap.name" />',hidden:false,highsearch:true},				
				{name:'planCount',index:'planCount',align:'right',width:'80',label : '<s:text name="hrDepartmentSnap.planCount" />',hidden:false,highsearch:true,editable:true,editoptions:{dataEvents:[{type:'blur',fn:function (e){afterEditCell(this);}},{type:'keyup',fn:function (e){chargeKeyPress(setPlanCountGridIdString,e)}},{type:'click',fn:function (e){selectRow(this)}}]}},				
				{name:'realCount',index:'realCount',align:'right',width:'80',label : '<s:text name="hrDepartmentSnap.realCount" />',hidden:false,highsearch:true},				
				{name:'realZbCount',index:'realZbCount',align:'right',width:'80',label : '<s:text name="hrDepartmentSnap.realZbCount" />',hidden:false,highsearch:true},				
				{name:'diffCount',index:'diffCount',align:'right',width:'80',label : '<s:text name="hrDepartmentSnap.diffCount" />',hidden:false,highsearch:true}
				,{name:'overCount',index:'overCount',align:'right',width:'80',label : '<s:text name="hrDepartmentSnap.overCount" />',hidden:false,highsearch:true}
				],
	        jsonReader : {
				root : "hrDepartmentCurrents", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'orgCode',
	        viewrecords: true,
	        sortorder: 'asc',
	        gridview:true,
	        keys:false,
//	        cellEdit:true,
	        rownumbers:true,
	        loadui: "disable",
			shrinkToFit:false,
			multiselect: true,
			autowidth:false,
			grouping : true , // 是否分组,默认为false
			groupingView : {
				sortname: 'deptCode',
		        sortorder: 'asc',
				groupField : [ 'hrOrg.orgname' ], // 按照哪一列进行分组
				groupColumnShow : [ false ], // 是否显示分组列
				groupText : [ '<b>{0}</b>' ], // 表头显示的数据以及相应的数据量
				groupCollapse : false , // 加载数据时是否只显示分组的组信息
				groupDataSorted : true  // 分组中的数据是否排序
			},
			gridComplete:function(){
			 	/*2015.08.27 form search change*/
			 	gridContainerResize('setPlanCount','div');
				var rowNum = jQuery(this).getDataIDs().length;
 	            if(rowNum>0){
 	                var rowIds = jQuery(this).getDataIDs();
 	                var ret = jQuery(this).jqGrid('getRowData');
 	                var id='';
 	                for(var i=0;i<rowNum;i++){
 	                	id=rowIds[i];
 	                	var snapId=ret[i]["departmentId"]+'_'+ret[i]["snapCode"];
 	                	displayOverCount(ret[i]["overCount"],id,ret[i]);
 	                	if(ret[i]["name"]=="合计："){
 	                		jQuery("#"+ret[i]["departmentId"]).find("td").css("background-color", "#99CCFF");
 	                	}else{
 		   	        	setCellText(this,id,'name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrDeptRecord(\''+snapId+'\',${random});">'+ret[i]["name"]+'</a>');
 	                	}
 	                }
	                
 	            }
	            var dataTest = {"id":"setPlanCount_gridtable"};
	      	    jQuery.publish("onLoadSelect",dataTest,null);
	      	    initFlag = initColumn('setPlanCount_gridtable','com.huge.ihos.hr.hrDeptment.model.authorizedSnap',initFlag);
	       	}
    	});
    	jQuery(setPlanCountGrid).jqGrid('bindKeys');
    	
    	/*----------------------------------tooBar start-----------------------------------------------*/
    	var setPlanCount_menuButtonArrJson = "${menuButtonArrJson}";
    	var setPlanCount_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(setPlanCount_menuButtonArrJson,false)));
    	var setPlanCount_toolButtonBar = new ToolButtonBar({el:$('#setPlanCount_buttonBar'),collection:setPlanCount_toolButtonCollection,attributes:{
    		tableId : 'setPlanCount_gridtable',
    		baseName : 'setPlanCount',
    		width : 600,
    		height : 900,
    		base_URL : null,
    		optId : null,
    		fatherGrid : null,
    		extraParam : null,
    		selectNone : "请选择记录。",
    		selectMore : "只能选择一条记录。",
    		addTitle : '<s:text name="authorizedSnapNew.title"/>',
    		editTitle : null
    	}}).render();
    	// 编辑
		setPlanCount_toolButtonBar.addCallBody('10010401',function(e,$this,param){
			fullGridEdit2("#setPlanCount_gridtable");
			},{});
    	//取消编辑
    	setPlanCount_toolButtonBar.addCallBody('10010402',function(e,$this,param){
    		//setPlanCountSearchFormReaload();
    		fullGridUnEdit("#setPlanCount_gridtable");
               
		},{});
    	//导出Excel
    	setPlanCount_toolButtonBar.addCallBody('10010404',function(e,$this,param){
    		exportToExcelPlanList('setPlanCount_gridtable','HrDepartmentCurrent','编制数据','all');
		},{});
    	//保存
        setPlanCount_toolButtonBar.addCallBody('10010403',function(e,$this,param){
        	var data="";
        	var rowData = $("#setPlanCount_gridtable").jqGrid("getRowData");  
        	    if (rowData.length < 1) { alert("没有数据！"); return; }  
        	    	var rowNum = jQuery("#setPlanCount_gridtable").getDataIDs().length;
        	    	var rowData = jQuery("#setPlanCount_gridtable").jqGrid("getRowData");  
     	            if(rowNum>0){
     	                var rowIds = jQuery("#setPlanCount_gridtable").getDataIDs();
     	                var id='';
     	                for(var i=0;i<rowNum;i++){
     	                	id=rowIds[i];
     	                	var snapId='#'+rowData[i]["departmentId"]+'_planCount';
     	                	var id = rowData[i].departmentId+"_"+rowData[i]["snapCode"];
     	                	var planCount = jQuery(snapId)[0].value;
     	                	var realZbCount = rowData[i].realZbCount
     	                	planCount=parseInt(planCount);
     	                	var row = i+1; //记录第几行
     	                	if(isNaN(planCount)){
     	                		alertMsg.error("您第"+row+"行输入的为非数字，请重新输入!");
     	                		return;
     	                	}
     	                	
     	                	data+=id+","+planCount+":"+realZbCount+"&";
        	            }
     	         }
        	    jQuery.post("planCountSave",{info:data},function(result){
        	    	   if(result!=null){
        	    		   setPlanCountSearchFormReaload();
        	    	   }
        	    });
       
    		},{});  
    	function afterEditCell(rowId){
    		  //该方法还要验证输入的是数字并且为正整数
    		  var regex = "^[0-9]*$";
    		  if(rowId==document){
    			  return;
    		  }
    	      var id=jQuery('#setPlanCount_gridtable').jqGrid('getGridParam','selrow');
    	      if(id==null){
    	    	  return;
    	      }
    		  var shapId = rowId.parentElement.parentElement.rowIndex;
    		  shapId = "#"+id+"_planCount"
	      	  var rowdata = jQuery("#setPlanCount_gridtable").jqGrid("getRowData",id);
	      	  var planCount = jQuery(shapId)[0].value;
	      	  if((!planCount.match(regex))||typeof(planCount)==undefined){
	      		  jQuery(shapId)[0].value = "";
	      		  alertMsg.error("您输入的内容必须为正整数，请重新输入!");
	      		  return;
	      	  }
	      	  var realZbCount = rowdata.realZbCount;
	      	  var diffCount = planCount -realZbCount;
	      	  var overCount = realZbCount- planCount;
	      	  
	      	  if(diffCount>0){
	      	  jQuery("#setPlanCount_gridtable").jqGrid("setRowData",id,{"diffCount":diffCount});
	      	  }else{
	      		jQuery("#setPlanCount_gridtable").jqGrid("setRowData",id,{"diffCount":0}); 
	      	  }
	      	  if(overCount>0){
	      		jQuery("#setPlanCount_gridtable").jqGrid("setRowData",id,{"overCount":overCount});
	      	  }else{
	      		jQuery("#setPlanCount_gridtable").jqGrid("setRowData",id,{"overCount":0}); 
	      	  }
	      	  
    	}	  
    	//设置表格列
		var setPlanCount_setColShowButton = {id:'1001020188',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
   			callBody:function(){
   				setColShow('setPlanCount_gridtable','com.huge.ihos.hr.hrDeptment.model.authorizedSnap');
   			}};
		setPlanCount_toolButtonBar.addButton(setPlanCount_setColShowButton);
    	/*----------------------------------tooBar end-----------------------------------------------*/
  	});
   
	function reloadHrDeptCurrentGrid(data){
		if(data.statusCode==200){
			alertMsg.correct(data.message);
		}else{
			alertMsg.error(data.message);
			return;
		}
		var urlString = jQuery("#setPlanCount_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('orgCode','');
		urlString = urlString.replace('departmentId','');
	    jQuery("#setPlanCount_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");

	}
	function setPlanCountSearchFormReaload(){
		propertyFilterSearch('setPlanCount_search_form','setPlanCount_gridtable');
	}
	//每次编辑时选中当前行 
	function selectRow (rowId) {
		   if(rowId==document){
			  return;
		  }
		  var rowid = rowId.parentElement.parentElement.cells[1].title;
		  jQuery("#setPlanCount_gridtable").jqGrid('resetSelection');
		  jQuery("#setPlanCount_gridtable").jqGrid('setSelection',rowid);
		  
	}
	jQuery("#auth_search_form_orgCode").treeselect({
		dataType : "sql",
		optType : "single",
		sql : "SELECT orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon  FROM v_hr_org_current WHERE disabled = 0 order by orgCode",
		exceptnullparent : false,
		lazy : false,
		minWidth:'200px',
		selectParent:true,
		callback : {
			afterClick : function() {
				jQuery("#auth_search_form_dept").val("");
				jQuery("#auth_search_form_dept").treeselect({
					dataType : "sql",
					optType : "multi",
					sql : "SELECT  deptId id,name,parentDept_id parent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon FROM v_hr_department_current where disabled = 0 and orgCode='"+jQuery("#auth_search_form_orgCode_id").val()+"' order by deptCode",
					exceptnullparent : false,
					selectParent:true,
					lazy : false,
					minWidth:'150px',
					callback:{
					}
				});
			}
		}
	});
	
     function chaMethod(){
    	 var org_name = jQuery("#auth_search_form_orgCode").val();
    	 if(org_name==""||org_name==undefined){
    		 jQuery("#auth_search_form_dept").treeselect(hideTree);
    		 jQuery("#auth_search_form_dept").attr("name","filter_LIKES_name");
    	 }
     }
 	function exportToExcelPlanList(gridId,entityName,title,outPutType){
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
		 var u =  'outPutExcelForPlanList?'+param+"&entityName="+entityName;
		 var postData = cloneObj(jQuery("#"+gridId).jqGrid("getGridParam", "postData"));
		 postData['entityName']=entityName;
		 postData['title']=title;
		 postData['outPutType']=outPutType;
		 postData['page']=page;
		 postData['pageSize']=pageSize;
		 postData['sortname']=sortname;
		 postData['sortorder']=sortorder;
		 postData['colDefineStr']=colDefineStr;
		 var excelSourceDataType="HrPersonHis";
		 postData['excelSourceDataType']=excelSourceDataType;
		
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
 	function displayOverCount(cellvalue, rowId, rowObject){
		if(cellvalue==undefined){
			var overCount = rowObject.realZbCount-rowObject.planCount;
			if(overCount>0){
				jQuery("#setPlanCount_gridtable").jqGrid("setRowData",rowId,{"overCount":overCount});
			}else{
				jQuery("#setPlanCount_gridtable").jqGrid("setRowData",rowId,{"overCount":0});
			}
		}else{
			jQuery("#setPlanCount_gridtable").jqGrid("setRowData",rowId,{"overCount":cellvalue});
		}
	}
 	function fullGridEdit2(gridId){
 		try{
 		var ids = jQuery(gridId).jqGrid('getDataIDs');
 	for(var i=0;i < ids.length;i++){
 		var cl = ids[i];
 		var data = jQuery(gridId).jqGrid('getRowData',cl);
 		if(data.name=="合计"){
 			continue;
 		}
 		jQuery(gridId).editRow(cl);
 	}	
 		}catch(err){
 			alert(err);
 		}
 	}
 	function fullGridUnEdit(gridId){
 		try{
 		var ids = jQuery(gridId).jqGrid('getDataIDs');
 	for(var i=0;i < ids.length;i++){
 		var cl = ids[i];
 		jQuery(gridId).restoreRow(cl);
 	}	
 		}catch(err){
 			alert(err);
 		}
 	}
</script>

<div class="page" id="setPlanCount_page">
	<div id="setPlanCount_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="setPlanCount_search_form"  style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrDepartmentSnap.orgName'/>:
						<input type="hidden"  name="filter_INS_orgCode" id = "auth_search_form_orgCode_id"/>
      					<input type="text" id="auth_search_form_orgCode"/>
					 	</label>  
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrDepartmentSnap.name'/>:
						<input type="hidden" name="filter_INS_departmentId" id="auth_search_form_dept_id"/>
      					<input type="text" id="auth_search_form_dept" onclick="chaMethod()"/>
					</label>  
                        <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="setPlanCountSearchFormReaload()"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 				<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="setPlanCountSearchFormReaload()"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
<div class="panelBar" id="setPlanCount_buttonBar">

			<%-- <ul class="toolBar">
				<li>
					<a id="setPlanCount_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a id="setPlanCount_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="setPlanCount_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a class="enablebutton" href="javaScript:enableOrDisableHrDept('enable')"><span><s:text name="button.enable" /></span> </a></li>
				<li>
					<a class="disablebutton" href="javaScript:enableOrDisableHrDept('disable')"><span><s:text name="button.disable" /></span> </a></li>
				<li>
				<li>
					<a class="delallbutton"  href="javaScript:authorizedRescind()"><span>撤销</span></a>
				</li>
				<li>
					<a class="savebutton"  href="javaScript:authorizedMerge()"><span>合并</span></a>
				</li>
				<li>
					<a class="getdatabutton"  href="javaScript:authorizedTransfer()"><span>划转</span></a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('setPlanCount_gridtable','com.huge.ihos.hr.hrDeptment.model.authorizedSnap')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			</ul> --%>

		</div>
				<div id="setPlanCount_gridtable_div" class="grid-wrapdiv" style="overflow: hidden">
					<input type="hidden" id="setPlanCount_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_setPlanCount_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
		 			<table id="setPlanCount_gridtable"></table>
				</div>
				<div class="panelBar" id="setPlanCount_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> 
						<select id="setPlanCount_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100" selected="selected">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="setPlanCount_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="setPlanCount_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="100"
						pageNumShown="5" currentPage="1">
					</div>
				</div>
			</div>
</div>
