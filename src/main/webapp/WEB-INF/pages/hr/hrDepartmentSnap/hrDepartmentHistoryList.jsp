<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var hrDepartmentHistoryGridIdString="#hrDepartmentHistory_gridtable";
	jQuery(document).ready(function() {
		jQuery("#hrDepartmentHistory_pageHeader").find("select").css("font-size","12px");
		/*------------------------------set layout-----------------------------------------*/
		var hrDepartmentHistoryFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('hrDepartmentHistory_container','hrDepartmentHistory_gridtable',hrDepartmentHistoryFullSize);
		/*------------------------------load leftTree-----------------------------------------*/
		hrDepartmentHistoryLeftTree();
		/*------------------------------load rightGrid-----------------------------------------*/
		var initFlag = 0;
		var hrDepartmentHistoryGrid = jQuery(hrDepartmentHistoryGridIdString);
    	hrDepartmentHistoryGrid.jqGrid({
	    	url : "hrDepartmentSnapGridList?filter_GEI_state=3",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'snapId',index:'snapId',align:'center',label : '<s:text name="hrDepartmentSnap.snapId" />',hidden:true,key:true,highsearch:false},				
				{name:'deptCode',index:'deptCode',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.deptCode" />',hidden:false,highsearch:true},				
				{name:'name',index:'name',align:'left',width:'120',label : '<s:text name="hrDepartmentSnap.name" />',hidden:false,highsearch:true},				
				{name:'hrOrgHis.orgname',index:'hrOrgHis.orgname',align:'left',width:'150',label : '<s:text name="hrDepartmentSnap.orgName" />',hidden:false,highsearch:true},				
				{name:'shortName',index:'shortName',align:'left',width:'120',label : '<s:text name="hrDepartmentSnap.shortName" />',hidden:false,highsearch:true},				
				{name:'deptType',index:'deptType',align:'center',width:'70',label : '<s:text name="hrDepartmentSnap.deptType" />',hidden:false,highsearch:true},				
				{name:'attrCode',index:'attrCode',align:'center',width:'70',label : '<s:text name="hrDepartmentSnap.attrCode" />',hidden:false,highsearch:true},				
				{name:'clevel',index:'clevel',align:'center',width:'50',label : '<s:text name="hrDepartmentSnap.clevel" />',hidden:false,formatter:'integer',highsearch:true},				
				{name:'leaf',index:'leaf',align:'center',width:'50',label : '<s:text name="hrDepartmentSnap.leaf" />',hidden:false,formatter:'checkbox',highsearch:true},				
				{name:'hisParentDept.name',index:'hisParentDept.name',width:'100',align:'left',label : '<s:text name="hrDepartmentSnap.parentDept" />',hidden:false,highsearch:true},				
				{name:'subClass',index:'subClass',align:'center',width:'100',label : '<s:text name="hrDepartmentSnap.subClass" />',hidden:false,highsearch:true},				
				{name:'cnCode',index:'cnCode',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.cnCode" />',hidden:false,highsearch:true},				
				{name:'jjDeptType.khDeptTypeName',index:'jjDeptType.khDeptTypeName',align:'center',width:'100',label : '<s:text name="hrDepartmentSnap.jjDeptType" />',hidden:false,highsearch:true},				
				{name:'internalCode',index:'internalCode',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.internalCode" />',hidden:false,highsearch:true},				
				{name:'manager',index:'manager',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.manager" />',hidden:false,highsearch:true},				
				{name:'remark',index:'remark',align:'left',width:'120',label : '<s:text name="hrDepartmentSnap.remark" />',hidden:false,highsearch:true},				
				//{name:'purchaseDept',index:'purchaseDept',width:'80',align:'center',label : '<s:text name="hrDepartmentSnap.purchaseDept" />',hidden:false,formatter:'checkbox',highsearch:true},
				{name:'planCount',index:'planCount',align:'right',width:'60',label : '<s:text name="hrDepartmentSnap.planCount" />',hidden:false,formatter:'integer',highsearch:true},				
				{name:'realCount',index:'realCount',align:'right',width:'60',label : '<s:text name="hrDepartmentSnap.realCount" />',hidden:false,formatter:'integer',highsearch:true},				
				{name:'realZbCount',index:'realZbCount',align:'right',width:'75',label : '<s:text name="hrDepartmentSnap.realZbCount" />',hidden:false,formatter:'integer',highsearch:true},				
				{name:'diffCount',index:'diffCount',align:'right',width:'60',label : '<s:text name="hrDepartmentSnap.diffCount" />',hidden:false,formatter:'integer',highsearch:true},							
				{name:'disabled',index:'disabled',align:'center',width:'50',label : '<s:text name="hrDepartmentSnap.disabled" />',hidden:false,formatter:'checkbox',highsearch:true}				
			
			],
	        jsonReader : {
				root : "hrDepartmentSnaps", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'orgCode,deptCode',
	        viewrecords: true,
	        sortorder: 'asc',
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
			 	gridContainerResize('hrDepartmentHistory','layout');
				var rowNum = jQuery(this).getDataIDs().length;
 	            if(rowNum>0){
 	                var rowIds = jQuery(this).getDataIDs();
 	                var ret = jQuery(this).jqGrid('getRowData');
 	                var id='';
 	                for(var i=0;i<rowNum;i++){
 	                	id=rowIds[i];
 		   	        	setCellText(this,id,'name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrDeptHisRecord(\''+id+'\',${random});">'+ret[i]["name"]+'</a>');
 	                }
 	               	var ztree = $.fn.zTree.getZTreeObj("hrDepartmentHistoryLeftTree");
 	               	if(ztree){
	 	               	var selectNode = ztree.getSelectedNodes();
						if(selectNode && selectNode.length==1){
							var node = selectNode[0];
							var selectid = node.id+'_'+node.snapCode;
							jQuery(this).jqGrid('setSelection',selectid);
						}
 	               	}
 	            }else{
 	               var tw = jQuery(this).outerWidth();
 	               jQuery(this).parent().width(tw);
 	               jQuery(this).parent().height(1);
 	            }
	           var dataTest = {"id":"hrDepartmentHistory_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   initFlag = initColumn('hrDepartmentHistory_gridtable','com.huge.ihos.hr.hrDeptment.model.HrDepartmentHistory',initFlag);
	       	} 
    	});
    	jQuery(hrDepartmentHistoryGrid).jqGrid('bindKeys');
    	 /*--------------------------------------tooBar start-------------------------------------------*/
    	var hrDepartmentHistory_menuButtonArrJson = "${menuButtonArrJson}";
    	var hrDepartmentHistory_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(hrDepartmentHistory_menuButtonArrJson,false)));
    	var hrDepartmentHistory_toolButtonBar = new ToolButtonBar({el:$('#hrDepartmentHistory_buttonBar'),collection:hrDepartmentHistory_toolButtonCollection,attributes:{
    		tableId : 'hrDepartmentHistory_gridtable',
    		baseName : 'hrDepartmentHistory',
    		width : 600,
    		height : 600,
    		base_URL : null,
    		optId : null,
    		fatherGrid : null,
    		extraParam : null,
    		selectNone : "请选择记录。",
    		selectMore : "只能选择一条记录。",
    		addTitle : null,
    		editTitle : null
    	}}).render();
    	var hrDepartmentHistory_function = new scriptFunction();
    	hrDepartmentHistory_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.checkPeriod){
				if('${yearStarted}'!='true'){
					alertMsg.error("本年度人力资源系统未启用。");
	    			return pass;
				}
			}
	        return true;
		};
    	//导出本页数据
		var hrDepartmentHistory_exportPageDataButton = {id:'1001020501',buttonLabel:'导出本页数据',className:"excelbutton",show:true,enable:true,
   			callBody:function(){
   				exportToExcelHrDeptHis('hrDepartmentHistory_gridtable','HrDepartmentSnap','部门数据','page')
   			}};
		//导出当前全部数据
		var hrDepartmentHistory_exportAllDataButton = {id:'1001020502',buttonLabel:'导出当前全部数据',className:"excelbutton",show:true,enable:true,
   			callBody:function(){
   				exportToExcelHrDeptHis('hrDepartmentHistory_gridtable','HrDepartmentSnap','部门数据','all')
   			}};
    	//设置表格列
		var hrDepartmentHistory_setColShowButton = {id:'1001020503',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
   			callBody:function(){
   				setColShow('hrDepartmentHistory_gridtable','com.huge.ihos.hr.hrDeptment.model.HrDepartmentHistory');
   			}};
		hrDepartmentHistory_toolButtonBar.addButton(hrDepartmentHistory_exportPageDataButton);
		hrDepartmentHistory_toolButtonBar.addButton(hrDepartmentHistory_exportAllDataButton);
		hrDepartmentHistory_toolButtonBar.addButton(hrDepartmentHistory_setColShowButton);
		hrDepartmentHistory_toolButtonBar.addBeforeCall('1001020501',function(e,$this,param){
			return hrDepartmentHistory_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
		hrDepartmentHistory_toolButtonBar.addBeforeCall('1001020502',function(e,$this,param){
			return hrDepartmentHistory_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
		
  	});
	function viewHrDeptHisRecord(snapId,random){
		var url = "editHrDepartmentSnap?snapId="+snapId+"&oper=view&random="+random;
		var hisTime = jQuery("#hrDepartmentHistory_search_form_snapTime").val();
		if(hisTime){
			url += "&hisTime="+hisTime;
		}
		$.pdialog.open(url,'viewHrDepartmentSnap_'+snapId,'查看部门信息', {mask:true,width : 680,height : 518,maxable:false,resizable:false});
	}
	function hrDepartmentHistoryLeftTree() {
		var url = "makeHrDeptSnapTree?1=1&loadFrom=his";
		var hisTime = jQuery("#hrDepartmentHistory_search_form_snapTime").val();
		//var showDisabled = jQuery("#hrDepartmentHistory_showDisabled").attr("checked");
		if(hisTime){
			url += "&hisTime="+hisTime;
		}
		/* if(showDisabled){
			url += "&showDisabled=true"; 
		} */
		$.get(url, {"_" : $.now()}, function(data) {
			var hrDeptHisTreeData = data.hrDeptHisTreeNodes;
			var hrDeptHisTree = $.fn.zTree.init($("#hrDepartmentHistoryLeftTree"),ztreesetting_hrDeptHisTree, hrDeptHisTreeData);
			var nodes = hrDeptHisTree.getNodes();
			hrDeptHisTree.expandNode(nodes[0], true, false, true);
			hrDeptHisTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'hrDepartmentHistoryLeftTree',
    		showCode:jQuery('#hrDepartmentHistory_showCode')[0],
    		disabledDept:jQuery("#hrDepartmentHistory_showDisabled")[0],
    		disabledPerson:false,
    		showCount:jQuery("#hrDepartmentHistory_showPersonCount")[0] });
			//toogleShowDisabledDept(jQuery("#hrDepartmentHistory_showDisabled")[0],jQuery("#hrDepartmentHistory_showPersonCount")[0],'hrDepartmentHistoryLeftTree');
			//toogleShowPersonCount(jQuery("#hrDepartmentHistory_showDisabled")[0],jQuery("#hrDepartmentHistory_showPersonCount")[0],'hrDepartmentHistoryLeftTree');
		});
		jQuery("#hrDepartmentHistory_expandTree").text("展开");
	}
	
	var ztreesetting_hrDeptHisTree = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				fontCss : setDisabledDeptFontCss
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var urlString = "hrDepartmentSnapGridList?1=1&filter_GEI_state=3";
				    var nodeId = treeNode.id;
				    if(nodeId!="-1"){
				    	if(treeNode.subSysTem==='ORG'){
					    	urlString += "&orgCode="+nodeId;
				    	}else{
					    	urlString += "&snapId="+(nodeId+'_'+treeNode.snapCode);
				    	}
				    }
				    urlString=encodeURI(urlString);
				    jQuery("#hrDepartmentHistory_gridtable").jqGrid('setGridParam',{url:urlString});
				    propertyFilterSearch('hrDepartmentHistory_search_form','hrDepartmentHistory_gridtable');
				}
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
	
	function showDisabledHisDept(obj){
		if(obj.checked){
			jQuery("#hrDepartmentHistory_search_form_showDisabled").val("1");
		}else{
			jQuery("#hrDepartmentHistory_search_form_showDisabled").val("");
		}
		//hrDepartmentHistoryLeftTree();
			toggleDisabledOrCount({treeId:'hrDepartmentHistoryLeftTree',
    		showCode:jQuery('#hrDepartmentHistory_showCode')[0],
    		disabledDept:jQuery("#hrDepartmentHistory_showDisabled")[0],
    		disabledPerson:false,
    		showCount:jQuery("#hrDepartmentHistory_showPersonCount")[0] });
		//toogleShowDisabledDept(obj,jQuery("#hrDepartmentHistory_showPersonCount")[0],'hrDepartmentHistoryLeftTree');
		propertyFilterSearch('hrDepartmentHistory_search_form','hrDepartmentHistory_gridtable');
	}
	
	var hisTime;
	function reloadHrDepartmentHistoryGrid(){
		var newTime = jQuery("#hrDepartmentHistory_search_form_snapTime").val();
		if(newTime && newTime!=hisTime){
			hrDepartmentHistoryLeftTree();
			hisTime = newTime;
			// 去除url里的orgCode和snapId
			var url = $("#hrDepartmentHistory_gridtable").jqGrid("getGridParam", "url");
			url = url.replace('orgCode','');
			url = url.replace('snapId','');
			url=encodeURI(url);
		    jQuery("#hrDepartmentHistory_gridtable").jqGrid('setGridParam',{url:url});
		}
		propertyFilterSearch('hrDepartmentHistory_search_form','hrDepartmentHistory_gridtable');
	}
	function exportToExcelHrDeptHis(gridId,entityName,title,outPutType){
		 var url = jQuery("#"+gridId).jqGrid('getGridParam','url');
		 var formData = jQuery("#"+gridId).jqGrid('getGridParam','postData');
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
		 var postData = cloneObj(jQuery("#"+gridId).jqGrid("getGridParam", "postData"));
		 postData['entityName']=entityName;
		 postData['title']=title;
		 postData['outPutType']=outPutType;
		 postData['page']=page;
		 postData['pageSize']=pageSize;
		 postData['sortname']=sortname;
		 postData['sortorder']=sortorder;
		 postData['colDefineStr']=colDefineStr;
		 var excelSourceDataType="HrDeptHis";
		 postData['excelSourceDataType']=excelSourceDataType;
// 		 var sdata =jQuery("#hrPersonHistory_search_form").serializeObject();
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

<div class="page">
	<div id="hrDepartmentHistory_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="hrDepartmentHistory_search_form" class="queryarea-form">
					<input type="hidden" name="showDisabled" id="hrDepartmentHistory_search_form_showDisabled" value=""/>			
					<label class="queryarea-label">
						<s:text name='历史时间'/>:
					 	<input type="text" name="hisTime" id="hrDepartmentHistory_search_form_snapTime" class="Wdate" style="height:15px;width: 109px;" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					</label> 
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.name'/>:
					 	<input type="text" style="width:100px" name="filter_LIKES_name" />
					</label>  
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.deptCode'/>:
					 	<input type="text" style="width:100px" name="filter_LIKES_deptCode"/>
					</label>  
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.deptType'/>:
					 	<s:select name="filter_EQS_deptType" headerKey="" headerValue="--" 
							list="deptTypeList" listKey="deptTypeName" listValue="deptTypeName"
							maxlength="20" emptyOption="false" theme="simple">
						</s:select>
					</label>  
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.subClass'/>:
					 	<s:select name="filter_EQS_subClass"  headerKey="" headerValue="--" 
							list="deptSubClassList"  listKey="value" listValue="content" 
							emptyOption="false" theme="simple" maxlength="20">
						</s:select> 
					</label>  
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.jjDeptType'/>:
					 	<s:select name="filter_EQS_jjDeptType.khDeptTypeId" headerKey="" headerValue="--" 
							list="jjDeptTypeList" listKey="khDeptTypeId" listValue="khDeptTypeName"
							emptyOption="false"  maxlength="10" theme="simple">
						</s:select>
					</label>  
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.disabled'/>:
					 	<s:select name="filter_EQB_disabled" headerKey="" headerValue="--" 
							list="#{'1':'是','2':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple">
						</s:select>
					</label>  
					
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.attrCode'/>:
					 	<s:select name="filter_EQS_attrCode" headerKey="" headerValue="--" 
							list="deptAttrList" listKey="value" listValue="content" value="0"
							emptyOption="false"  maxlength="10" theme="simple">
						</s:select>
					</label>  
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.clevel'/>:
					 	<input type="text"  style="width:50px" name="filter_EQI_clevel" />
					</label>  
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.leaf'/>:
					 	<s:select name="filter_EQB_leaf" headerKey="" headerValue="--" 
							list="#{'1':'是','0':'否' }" listKey="key" listValue="value" 
							emptyOption="false"  maxlength="10" theme="simple">
						</s:select>
					</label>  
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.remark'/>:
					 	<input type="text" style="width:100px" name="filter_LIKES_remark" />
					</label>  
					
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="reloadHrDepartmentHistoryGrid()"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="reloadHrDepartmentHistoryGrid()"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
<div class="panelBar" id="hrDepartmentHistory_buttonBar">

			<!-- <ul class="toolBar">
				<li>
					<a class="excelbutton" href="javaScript:exportToExcel('hrDepartmentHistory_gridtable','HrDepartmentSnap','部门数据','page')"><span>导出本页数据 </span> </a>
				</li>
				<li>
					<a class="excelbutton" href="javaScript:exportToExcel('hrDepartmentHistory_gridtable','HrDepartmentSnap','部门数据','all')"><span>导出当前全部数据 </span> </a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('hrDepartmentHistory_gridtable','com.huge.ihos.hr.hrDeptment.model.HrDepartmentHistory')"><span><s:text name="button.setColShow" /></span></a>
				</li>-->

		</div>
		<div id="hrDepartmentHistory_container">
			<div id="hrDepartmentHistory_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
     			<span>
      			显示机构编码
      			<input id="hrDepartmentHistory_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrDepartmentHistoryLeftTree',showCode:this,disabledDept:jQuery('#hrDepartmentHistory_showDisabled')[0],disabledPerson:false,showCount:jQuery('#hrDepartmentHistory_showPersonCount')[0] });")"/>
     			</span>
    			<span>
      			显示人员数
      			<input id="hrDepartmentHistory_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrDepartmentHistoryLeftTree',showCode:jQuery('#hrDepartmentHistory_showCode')[0],disabledDept:jQuery('#hrDepartmentHistory_showDisabled')[0],disabledPerson:false,showCount:this });")"/>
     			</span>
     			<label id="hrDepartmentHistory_expandTree" onclick="toggleExpandTree(this,'hrDepartmentHistoryLeftTree')">展开</label>
    			</div>
    			<div class="treeTopCheckBox">
     			<span>
      			显示停用部门
      			<input id="hrDepartmentHistory_showDisabled" type="checkbox" onclick="showDisabledHisDept(this)"/>
     			</span>
    			</div>
    			<div class="treeTopCheckBox">
     			<span>
      			按部门检索：
      			<input type="text" onKeyUp="searchTree('hrDepartmentHistoryLeftTree',this)"/>
     			</span>
    			</div>
				<div id="hrDepartmentHistoryLeftTree" class="ztree"></div>
			</div>
			<div id="hrDepartmentHistory_layout-center" class="pane ui-layout-center">
				<div id="hrDepartmentHistory_gridtable_div" class="grid-wrapdiv" style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="hrDepartmentHistory_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_hrDepartmentHistory_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
		 			<table id="hrDepartmentHistory_gridtable"></table>
				</div>
				<div class="panelBar"  id="hrDepartmentHistory_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="hrDepartmentHistory_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="hrDepartmentHistory_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="hrDepartmentHistory_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div><!-- center -->
		</div><!-- layout -->
	</div>
</div>
