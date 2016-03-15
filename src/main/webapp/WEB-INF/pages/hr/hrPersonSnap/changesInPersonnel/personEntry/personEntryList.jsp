
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var personEntryLayout;
			  var personEntryGridIdString="#personEntry_gridtable";
	
	jQuery(document).ready(function() { 
		var sysPersonMoveFullSize = jQuery("#container").innerHeight()-118;
		setLeftTreeLayout('personEntry_container','personEntry_gridtable',sysPersonMoveFullSize);
		
var personEntryGrid = jQuery(personEntryGridIdString);
    personEntryGrid.jqGrid({
    	url : "personEntryGridList?1=1",
    	editurl:"personEntryGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="personEntry.id" />',hidden:true,key:true},	
{name:'code',index:'code',align:'left',width : 120,label : '<s:text name="personEntry.code" />',hidden:false,highsearch:true},
{name:'name',index:'name',width : 80,align:'left',label : '<s:text name="personEntry.name" />',hidden:false,highsearch:true},
{name:'sex',index:'sex',width : 40,align:'center',label : '<s:text name="personEntry.sex" />',hidden:false,highsearch:true},
{name:'hrDept.hrOrg.orgname',index:'hrDept.hrOrg.orgname',align:'left',width : 130,label : '<s:text name="personEntry.orgCode" />',hidden:false,highsearch:true},
{name:'hrDept.name',index:'hrDept.name',width : 80,align:'left',label : '<s:text name="personEntry.hrDept" />',hidden:false,highsearch:true},
{name:'hrDept.hrOrg.orgCode',index:'hrDept.hrOrg.orgCode',align:'left',width : 100,label : '<s:text name="personEntry.orgCode" />',hidden:true},
{name:'hrDept.leaf',index:'hrDept.leaf',width : 80,align:'left',label : '<s:text name="personEntry.leaf" />',hidden:true},
{name:'postType.name',index:'postType.name',width : 60,align:'left',label : '<s:text name="personEntry.postType" />',hidden:false,highsearch:true},
{name:'empType.name',index:'empType.name',width : 60,align:'left',label : '<s:text name="personEntry.empType" />',hidden:false,highsearch:true},
{name:'empType.code',index:'empType.code',width : 60,align:'left',label : '<s:text name="personEntry.empType" />',hidden:true},
{name:'entryDate',index:'entryDate',width:70,align:'center',label : '<s:text name="personEntry.entryDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
{name:'personCode',index:'personCode',width : 80,align:'left',label : '<s:text name="personEntry.personCode" />',hidden:false,highsearch:true},
{name:'birthday',index:'birthday',width : 70,align:'center',label : '<s:text name="personEntry.birthday" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
{name:'people',index:'people',width : 60,align:'left',label : '<s:text name="personEntry.people" />',hidden:false,highsearch:true},
{name:'personPolCode',index:'personPolCode',width : 60,align:'left',label : '<s:text name="personEntry.personPolCode" />',hidden:false,highsearch:true},	
{name:'idNumber',index:'idNumber',width : 100,align:'left',label : '<s:text name="personEntry.idNumber" />',hidden:false,highsearch:true},
{name:'educationalLevel',index:'educationalLevel',width : 60,align:'left',label : '<s:text name="personEntry.educationalLevel" />',hidden:false,highsearch:true},
{name:'degree',index:'degree',align:'left',width : 60,label : '<s:text name="personEntry.degree" />',hidden:false,highsearch:true},				
{name:'school',index:'school',align:'left',width : 80,label : '<s:text name="personEntry.school" />',hidden:false,highsearch:true},	
{name:'professional',index:'professional',width : 60,align:'left',label : '<s:text name="personEntry.professional" />',hidden:false,highsearch:true},
{name:'graduateDay',index:'graduateDay',width : 70,align:'center',label : '<s:text name="personEntry.graduateDay" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
{name:'mobilePhone',index:'mobilePhone',width : 80,align:'left',label : '<s:text name="personEntry.mobilePhone" />',hidden:false,highsearch:true},			
{name:'email',index:'email',align:'left',width : 100,label : '<s:text name="personEntry.email" />',hidden:false,highsearch:true},				
{name:'imagePath',index:'imagePath',align:'center',label : '<s:text name="personEntry.imagePath" />',hidden:true},	
//{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="hrPerson.yearMonth" />',hidden：false ,highsearch:true},	
<c:if test="${personNeedCheck=='1'}">
{name:'maker.name',index:'maker.name',align:'left',width : 60,label : '<s:text name="personEntry.maker" />',hidden:false,highsearch:true},
{name:'makeDate',index:'makeDate',align:'center',width : 70,label : '<s:text name="personEntry.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
{name:'checker.name',index:'checker.name',align:'left',width : 60,label : '<s:text name="personEntry.checker" />',hidden:false,highsearch:true},
{name:'checkDate',index:'checkDate',align:'center',width : 70,label : '<s:text name="personEntry.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
{name:'doner.name',index:'doner.name',align:'left',width:60,label : '<s:text name="personEntry.doner" />',hidden:false,highsearch:true,highsearch:true},
{name:'doneDate',index:'doneDate',align:'center',width:70,label : '<s:text name="personEntry.doneDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
</c:if>
{name:'state',index:'state',align:'center',width : 60,label : '<s:text name="personEntry.state" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '0:新建;1:已审核;2:已执行;'},highsearch:true},				
{name:'remark',index:'remark',align:'left',width : 250,label : '<s:text name="personEntry.remark" />',hidden:false,highsearch:true}				


        ],
        jsonReader : {
			root : "personEntries", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'code',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="personEntryList.title" />',
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
			 gridContainerResize('personEntry','layout');
			 var rowNum = jQuery(this).getDataIDs().length;
			  if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  id=rowIds[i];
	              	  if(ret[i]['state']=="0"){
	              		  setCellText(this,id,'state','<span style="color:black">新建</span>');
	              	  }else if(ret[i]['state']=="1"){
	              		setCellText(this,id,'state','<span style="color:green">已审核</span>');
	              	  }else if(ret[i]['state']=="2"){
	              		setCellText(this,id,'state','<span style="color:blue">已执行</span>');
	              	  }
	              	 setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPersonEntryRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
	                }
			  }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
	              	  
			 
//            if(jQuery(this).getDataIDs().length>0){
//               jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
//             }
           var dataTest = {"id":"personEntry_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
       	} 

    });
    jQuery(personEntryGrid).jqGrid('bindKeys');
    
  //实例化ToolButtonBar
    var personEntry_menuButtonArrJson = "${menuButtonArrJson}";
    var personEntry_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(personEntry_menuButtonArrJson,false)));
    var personEntry_toolButtonBar = new ToolButtonBar({el:$('#personEntry_buttonBar'),collection:personEntry_toolButtonCollection,attributes:{
     tableId : 'personEntry_gridtable',
     baseName : 'personEntry',
     width : 600,
     height : 600,
     base_URL : null,
     optId : null,
     fatherGrid : null,
     extraParam : null,
     selectNone : "请选择记录。",
     selectMore : "只能选择一条记录。",
     addTitle : '<s:text name="personEntryNew.title"/>',
     editTitle : null
    }}).render();
    //实例化结束
    
    var personEntry_function = new scriptFunction();
    personEntry_function.optBeforeCall = function(e,$this,param){
		var pass = false;
		if(param.checkPeriod){
			if('${yearStarted}'!='true'){
				alertMsg.error("本年度人力资源系统未启用。");
    			return pass;
			}
		}
        return true;
	};
    
    //添加
    personEntry_toolButtonBar.addCallBody('1002020101',function(e,$this,param){
    	var zTree = $.fn.zTree.getZTreeObj("hrDepartmentPersonEntryTreeLeft");  
	    var nodes = zTree.getSelectedNodes(); 
	    if(nodes.length!=1 || nodes[0].subSysTem!='DEPT'){
	    	alertMsg.error("请选择一个部门。");
      		return;
	    }
	    if(nodes[0].actionUrl == '1'){
	    	alertMsg.error("已停用部门不能添加人员。");
      		return;
	    }
	    if(nodes[0].isParent){
	    	alertMsg.error("父级部门不能添加人员。");
      		return;
	    }
		var url = "editPersonEntry?navTabId=personEntry_gridtable&deptId="+nodes[0].id;
		var winTitle='<s:text name="personEntryNew.title"/>';
		$.pdialog.open(url,'addPersonEntry',winTitle, {mask:true,width : 700,height : 600});
    },{});
    personEntry_toolButtonBar.addBeforeCall('1002020101',function(e,$this,param){
		return personEntry_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
    //删除
     personEntry_toolButtonBar.addCallBody('1002020102',function(e,$this,param){
    	 var url = "${ctx}/personEntryGridEdit?oper=del";
         var sid = jQuery("#personEntry_gridtable").jqGrid('getGridParam','selarrrow');
         if(sid==null|| sid.length == 0){       	
 			alertMsg.error("请选择记录。");
 			return;
 			}else {
 				for(var i = 0;i < sid.length; i++){
 					var rowId = sid[i];
 					var row = jQuery("#personEntry_gridtable").jqGrid('getRowData',rowId);
 					if(row['state']=='-1'){
 					}else{
 						if(row['state']!='0'){
 						alertMsg.error("只能删除处于新建状态的记录!");
 						return;
 					}
 					}
 					
 				}
 				url = url+"&id="+sid+"&navTabId=personEntry_gridtable";
 				alertMsg.confirm("确认删除？", {
 					okCall : function() {
 						$.post(url,function(data) {
 							formCallBack(data);
 						});
 					}
 				});
 			}
    },{});
     personEntry_toolButtonBar.addBeforeCall('1002020102',function(e,$this,param){
 		return personEntry_function.optBeforeCall(e,$this,param);
 	},{checkPeriod:"checkPeriod"});
    //修改
     personEntry_toolButtonBar.addCallBody('1002020103',function(e,$this,param){
    	 var sid = jQuery("#personEntry_gridtable").jqGrid('getGridParam','selarrrow');
         if(sid==null || sid.length !=1){
         	alertMsg.error("请选择一条记录。");
  			return;
  			}
     		var row = jQuery("#personEntry_gridtable").jqGrid('getRowData',sid[0]);
     		if(row['state']!='0'){
  			alertMsg.error("只能修改处于新建状态的记录!");
  			return;
  		}
  		var winTitle='<s:text name="personEntryEdit.title"/>';
  		var url = "editPersonEntry?id="+sid+"&navTabId=personEntry_gridtable";
  		$.pdialog.open(url,'editPersonEntry',winTitle, {mask:true,width : 700,height : 600});
    },{});
     personEntry_toolButtonBar.addBeforeCall('1002020103',function(e,$this,param){
 		return personEntry_function.optBeforeCall(e,$this,param);
 	},{checkPeriod:"checkPeriod"});
    //审核
     personEntry_toolButtonBar.addCallBody('1002020104',function(e,$this,param){
    	 var url = "${ctx}/personEntryGridEdit?oper=check";
         var sid = jQuery("#personEntry_gridtable").jqGrid('getGridParam','selarrrow');
         if(sid==null|| sid.length == 0){       	
 			alertMsg.error("请选择记录。");
 			return;
 			}else {
 				for(var i = 0;i < sid.length; i++){
 					var rowId = sid[i];
 					var row = jQuery("#personEntry_gridtable").jqGrid('getRowData',rowId);
 					if(!row['personCode']){
 						alertMsg.error("只能审核有人员编码的记录!");
 						return;
 					}
 					if(row['state']!='0'){
 						alertMsg.error("只能审核处于新建状态的记录!");
 						return;
 					}
 				}
 				url = url+"&id="+sid+"&navTabId=personEntry_gridtable";
 				alertMsg.confirm("确认审核？", {
 					okCall : function() {
 						$.post(url,function(data) {
 							formCallBack(data);
 						});
 					}
 				});
 			}
    },{});
     personEntry_toolButtonBar.addBeforeCall('1002020104',function(e,$this,param){
 		return personEntry_function.optBeforeCall(e,$this,param);
 	},{checkPeriod:"checkPeriod"});
    //销审
     personEntry_toolButtonBar.addCallBody('1002020105',function(e,$this,param){
    	 var url = "${ctx}/personEntryGridEdit?oper=cancelCheck"
    	        var sid = jQuery("#personEntry_gridtable").jqGrid('getGridParam','selarrrow');
    	        if(sid==null|| sid.length == 0){       	
    				alertMsg.error("请选择记录。");
    				return;
    				} else {
    						for(var i = 0;i < sid.length; i++){
    							var rowId = sid[i];
    							var row = jQuery("#personEntry_gridtable").jqGrid('getRowData',rowId);
    							
    							if(row['state']!='1'){
    								alertMsg.error("只有已审核的记录才能够被销审!");
    								return;
    							}
    						}
    						url = url+"&id="+sid+"&navTabId=personEntry_gridtable";
    						alertMsg.confirm("确认销审？", {
    							okCall : function() {
    								$.post(url,function(data) {
    									formCallBack(data);
    								});
    							}
    						});
    					}
    },{});
     personEntry_toolButtonBar.addBeforeCall('1002020105',function(e,$this,param){
 		return personEntry_function.optBeforeCall(e,$this,param);
 	},{checkPeriod:"checkPeriod"});
    //执行
     personEntry_toolButtonBar.addCallBody('1002020106',function(e,$this,param){
    	 var url = "${ctx}/personEntryGridEdit?oper=done"
             var sid = jQuery("#personEntry_gridtable").jqGrid('getGridParam','selarrrow');
        	  var pactIds = new Array();
        	  if(sid==null|| sid.length == 0){       	
     			alertMsg.error("请选择记录。");
     			return;
     			} else {
     					var pactDoneFlag = false;
     					for(var i = 0;i < sid.length; i++){
     						pactIds[i]=sid[i];
     						var rowId = sid[i];
     						var row = jQuery("#personEntry_gridtable").jqGrid('getRowData',rowId);
     						if(row['state']!='1'){
     							alertMsg.error("只能执行处于已审核状态的记录!");
     							return;
     						}
     						if(row['hrDept.leaf']=="false"){
     							alertMsg.error("只有末级部门才能够添加人员!");
     							return;
     						}
     						if(row['empType.code']=='PT0102'){
     							pactDoneFlag = true;
     						}
     					}
     					url = url+"&id="+sid+"&navTabId=personEntry_gridtable";
     					var pactUrl="${ctx}/personEntryGridEdit?oper=donePact";
     					pactUrl = pactUrl+"&id="+pactIds+"&navTabId=personEntry_gridtable";
     					alertMsg.confirm("确认执行？", {
     						okCall : function() {
     							$.post(url,function(data) {
     								if(data.statusCode==200){
     									formCallBack(data);
     									var treeNodes = data.treeNodes;
     									if(treeNodes){
     										$.each(treeNodes, function(){      
     											dealHrTreeNode("hrDepartmentPersonEntryTreeLeft",this,"editPC","person");    
         									});  
     									}
     									if(pactDoneFlag){
     									alertMsg.confirm("确认生成合同？", {
     				   						okCall : function() {
     				   							$.post(pactUrl,function(data) {
     				   								formCallBack(data);
     				   							});
     				   						}
     				   					});
     								}
     								}else{
     									formCallBack(data);
     								}
     								
     							});
     						}
     					});
     				}
    },{});
     personEntry_toolButtonBar.addBeforeCall('1002020106',function(e,$this,param){
 		return personEntry_function.optBeforeCall(e,$this,param);
 	},{checkPeriod:"checkPeriod"});
   //设置表格列
     var personEntry_setColShowButton = {id:'1002020188',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
       callBody:function(){
        setColShow('personEntry_gridtable','com.huge.ihos.hr.changesInPersonnel.model.PersonEntry');
       }};
     personEntry_toolButtonBar.addButton(personEntry_setColShowButton);//实例化ToolButtonBar
	//personEntryLayout.resizeAll();
    
    hrDepartmentCurrentLeftTreeInPersonEntry();
  	});
	function hrDepartmentCurrentLeftTreeInPersonEntry() {
		var url = "makeHrDeptTree";
		/* if(type && type==="showDisabled"){
			url += "?showDisabled=true"; 
		} */
		$.get(url, {"_" : $.now()}, function(data) {
			var hrDeptTreeData = data.hrDeptTreeNodes;
			var hrDeptTree = $.fn.zTree.init($("#hrDepartmentPersonEntryTreeLeft"),ztreesetting_hrDeptTreeInPersonEntry, hrDeptTreeData);
			var nodes = hrDeptTree.getNodes();
			hrDeptTree.expandNode(nodes[0], true, false, true);
			hrDeptTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'hrDepartmentPersonEntryTreeLeft',
			    showCode:jQuery('#personEntry_showCode')[0],
			    disabledDept:jQuery("#personEntry_showDisabled")[0],
			    disabledPerson:false,
			    showCount:jQuery("#personEntry_showPersonCount")[0] });
			});
		jQuery("#personEntry_expandTree").text("展开");
	}
	function setFontCss(treeId, treeNode) {
		var color;
		if(treeNode.actionUrl == '1') {
			color = {
				color : "black",
				'font-style' : 'italic',
				'text-decoration' : 'line-through'
			};
		}else{
			color = {
				color : "black"
			};
		}
		return color;
	};
	var ztreesetting_hrDeptTreeInPersonEntry = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				fontCss : setFontCss
			},
			callback : {
				beforeDrag:zTreeBeforeDrag,
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
	function zTreeBeforeDrag(treeId, treeNodes) {
	    return false;
	};
	function onModuleClick(event, treeId, treeNode, clickFlag) { 
	    var urlString = "personEntryGridList?1=1";
	    var nodeId = treeNode.id;
	    if(nodeId!="-1"){
	    	if(treeNode.subSysTem==='ORG'){
		    	urlString += "&orgCode="+nodeId;
	    	}else{
		    	urlString += "&departmentId="+nodeId;
	    	}
	    }
	    var showDisabled = jQuery("#personEntry_showDisabled").attr("checked");
	    if(showDisabled){
	    	urlString += "&showDisabled=true"
	    }
		urlString=encodeURI(urlString);
		jQuery("#personEntry_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	function showDisabledDeptInPersonEntry(obj){
		var urlString = jQuery("#personEntry_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabled','');
		if(obj.checked){
			urlString += "&showDisabled=true";
		}
		toggleDisabledOrCount({treeId:'hrDepartmentPersonEntryTreeLeft',
		    showCode:jQuery('#personEntry_showCode')[0],
		    disabledDept:jQuery("#personEntry_showDisabled")[0],
		    disabledPerson:false,
		    showCount:jQuery("#personEntry_showPersonCount")[0] });
		jQuery("#personEntry_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	function viewPersonEntryRecord(sid){
		var winTitle='<s:text name="personEntryView.title"/>';
		var url = "editPersonEntry?id="+sid+"&navTabId=personEntry_gridtable";
		url+="&oper=view";
		$.pdialog.open(url,'viewPersonEntry',winTitle, {mask:true,width : 700,height : 600});
	}

  
</script>

<div class="page">
	<div class="pageHeader" id="personEntry_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
			<form id="personEntry_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name="personEntry.code"/>:
						<input type="text"  id="search_personEntry_code" name="filter_LIKES_code" style="width:100px"/>
					</label>
					<label class="queryarea-label">
						<s:text name="personEntry.name"/>:
						<input type="text"  id="search_personEntry_name" name="filter_LIKES_name" style="width:100px"/>
					</label>	
					<label class="queryarea-label">
      					 <s:text name='personEntry.sex'/>:
      					 <s:select
            				 key="personEntry.sex" id="search_personEntry_sex"  style="font-size:9pt;"
            				 maxlength="50" list="sexList" listKey="value" headerKey="" headerValue="--" 
            				 listValue="content" emptyOption="false" theme="simple" name="filter_EQS_sex"></s:select>
     				 </label>
					<label class="queryarea-label">
						<s:text name='personEntry.state'/>:
						<s:select id="personEntry_search_state"   name="filter_EQS_state" list="#{'':'--','0':'新建','1':'已审核','2':'已执行'}" style="width:80px;font-size:9pt;" ></s:select>
						</label>	
					<label class="queryarea-label">
						<s:text name="personEntry.idNumber"/>:
						<input type="text"  id="search_personEntry_idNumber" name="filter_LIKES_idNumber" style="width:100px"/>
					</label>
					<label class="queryarea-label">
						<s:select id="personEntry_searchTime"   list="#{'0':'执行日期','1':'申请日期','2':'审核日期'}" style="width:100px;font-size:9pt;" ></s:select>
						<s:text name=" 从"/>:
						<input type="text" id="personEntryBeginDate"  name="hrPerson.beginDate" onclick="changeSysTimeType('personEntry','doneDate','makeDate','checkDate')" onblur="checkQueryDate('personEntryBeginDate','personEntryEndDate',0,'personEntry_beginDate','personEntry_endDate')" style="width:65px"/>
					</label><label class="queryarea-label">
						<s:text name="至"/>:
						<input type="text" id="personEntryEndDate" name="hrPerson.endDate" onclick="changeSysTimeType('personEntry','doneDate','makeDate','checkDate')"  onblur="checkQueryDate('personEntryBeginDate','personEntryEndDate',1,'personEntry_beginDate','personEntry_endDate')" style="width:65px"/>
						<input type="hidden" id="personEntry_beginDate" name="hrPerson.endDate"  />
						<input type="hidden" id="personEntry_endDate" name="hrPerson.endDate"  />
					</label>	
					<label class="queryarea-label">
						<s:text name="personEntry.remark"/>:
						<input type="text"  id="search_personEntry_remark" name="filter_LIKES_remark"/>
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%-- 					<s:text name='personEntry.entryDate'/>: --%>
<!-- 						<input type="text"	id="personEntry_search_entryDate_from" name="filter_GED_entryDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'personEntry_search_entryDate_to\')}'})"> -->
<%-- 						<s:text name='至'/> --%>
<!-- 					 	<input type="text"	id="personEntry_search_entryDate_to" name="filter_LED_entryDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'personEntry_search_entryDate_from\')}'})"> -->
<!-- 					</label> -->
					<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="propertyFilterSearch('personEntry_search_form','personEntry_gridtable')"><s:text name='button.search'/></button>
							</div>
						</div>		
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="propertyFilterSearch('personEntry_search_form','personEntry_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">




	<div class="panelBar" id="personEntry_buttonBar">

<!-- 			<ul class="toolBar"> -->
<!-- 				<li> -->
<%-- 					<a id="personEntry_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="personEntry_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="personEntry_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="personEntry_gridtable_check" class="checkbutton"  href="javaScript:"><span><s:text name="button.check" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="personEntry_gridtable_cancelCheck" class="delallbutton"  href="javaScript:"><span><s:text name="button.cancelCheck" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="personEntry_gridtable_done" class="confirmbutton"  href="javaScript:"><span><s:text name="button.done" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a class="settlebutton"  href="javaScript:setColShow('personEntry_gridtable','com.huge.ihos.hr.changesInPersonnel.model.PersonEntry')"><span><s:text name="button.setColShow" /></span></a> --%>
<!-- 				</li> -->
<!-- 			</ul> -->

		</div>
		<div id="personEntry_container">
			<div id="personEntry_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
			
			  <div class="treeTopCheckBox">
     			<span>
      				显示机构编码
      			<input id="personEntry_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrDepartmentPersonEntryTreeLeft',showCode:this,disabledDept:jQuery('#personEntry_showDisabled')[0],disabledPerson:false,showCount:jQuery('#personEntry_showPersonCount')[0] })"/>
     			</span>
     			<span>
      				显示人员数
      			<input id="personEntry_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrDepartmentPersonEntryTreeLeft',showCode:jQuery('#personEntry_showCode')[0],disabledDept:jQuery('#personEntry_showDisabled')[0],disabledPerson:false,showCount:this })"/>
     			</span>
    		    <label id="personEntry_expandTree" onclick="toggleExpandTree(this,'hrDepartmentPersonEntryTreeLeft')">展开</label>
    			</div>
    			<div class="treeTopCheckBox">
     			<span>
      				显示停用部门
      			<input id="personEntry_showDisabled" type="checkbox" onclick="showDisabledDeptInPersonEntry(this)"/>
     			</span>
    			</div>
    			<div class="treeTopCheckBox">
    			 <span>
      				快速查询：
      			<input type="text" onKeyUp="searchTree('hrDepartmentPersonEntryTreeLeft',this)"/>
    			 </span>
    			</div>
				<div id="hrDepartmentPersonEntryTreeLeft" class="ztree"></div>
			</div>
		<div id="personEntry_layout-center" class="pane ui-layout-center">
		<div id="personEntry_gridtable_div" class="grid-wrapdiv" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:500;height:300">
			<input type="hidden" id="personEntry_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="personEntry_gridtable_addTile">
				<s:text name="personEntryNew.title"/>
			</label> 
			<label style="display: none"
				id="personEntry_gridtable_editTile">
				<s:text name="personEntryEdit.title"/>
			</label>
			<label style="display: none"
				id="personEntry_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="personEntry_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_personEntry_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="personEntry_gridtable"></table>
		<div id="personEntryPager"></div>
</div>
	<div class="panelBar"  id="personEntry_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="personEntry_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="personEntry_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="personEntry_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>
</div>