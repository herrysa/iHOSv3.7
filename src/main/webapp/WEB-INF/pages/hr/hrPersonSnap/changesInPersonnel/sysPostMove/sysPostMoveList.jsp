
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var sysPostMoveGridIdString="#sysPostMove_gridtable";
	
	jQuery(document).ready(function() {
		jQuery("#sysPostMove_pageHeader").find("select").css("font-size","12px");
		var sysPostMoveFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('sysPostMove_container','sysPostMove_gridtable',sysPostMoveFullSize);
		var initFlag = 0;
		var sysPostMoveGrid = jQuery(sysPostMoveGridIdString);
	    sysPostMoveGrid.jqGrid({
	    	url : "sysPostMoveGridList?1=1",
	    	editurl:"sysPostMoveGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="sysPostMove.id" />',hidden:true,key:true},	
				{name:'code',index:'code',align:'left',width:120,label : '<s:text name="sysPostMove.code" />',hidden:false,highsearch:true},
				{name:'name',index:'name',align:'left',width:100,label : '<s:text name="sysPostMove.name" />',hidden:false,highsearch:true},
				{name:'hrPersonHis.name',index:'hrPersonHis.name',align:'left',width:80,label : '<s:text name="sysPostMove.person" />',hidden:false,highsearch:true},
				{name:'hrPersonHis.hrPersonPk.personId',index:'hrPersonHis.hrPersonPk.personId',width:80,align:'left',label : '<s:text name="person.name" />',hidden:true},
				{name:'changedSnapCode',index:'changedSnapCode',width:80,align:'left',label : '<s:text name="person.name" />',hidden:true},
				{name:'hrPersonHis.hisSnapId',index:'hrPersonHis.hisSnapId',align:'left',width:80,label : '<s:text name="sysPostMove.person" />',hidden:true},
				{name:'hrPersonHis.hrOrg.orgname',index:'hrPersonHis.hrOrg.orgname',width:120,align:'left',label : '<s:text name="sysPostMove.orgCode" />',hidden:false,highsearch:true,sortable:false},
				{name:'hrPersonHis.departmentHis.name',index:'hrPersonHis.departmentHis.name',align:'left',width:100,label : '<s:text name="sysPostMove.dept" />',hidden:false,highsearch:true,sortable:false},
				{name:'hrPersonHis.postType',index:'hrPersonHis.postType',align:'left',width:60,label : '<s:text name="sysPostMove.oldPostType" />',hidden:false,highsearch:true,sortable:false},
				{name:'hrPersonHis.duty.name',index:'hrPersonHis.duty.name',align:'left',width:60,label : '<s:text name="sysPostMove.oldDuty" />',hidden:false,highsearch:true,sortable:false},
				{name:'postType',index:'postType',align:'left',width:60,label : '<s:text name="sysPostMove.postType" />',hidden:false,highsearch:true},
				{name:'duty.name',index:'duty.name',align:'left',width:60,label : '<s:text name="sysPostMove.duty" />',hidden:false,highsearch:true},
// 				{name:'moveDate',index:'moveDate',align:'center',width:80,label : '<s:text name="sysPostMove.moveDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'reason',index:'reason',align:'left',width:200,label : '<s:text name="sysPostMove.reason" />',hidden:false,highsearch:true},				
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="hrPerson.yearMonth" />',hidden:false,highsearch:true},	
				<c:if test="${personNeedCheck=='1'}">
				{name:'maker.name',index:'maker.name',align:'left',width:60,label : '<s:text name="sysPostMove.maker" />',hidden:false,highsearch:true},				
				{name:'makeDate',index:'makeDate',align:'center',width:70,label : '<s:text name="sysPostMove.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'checker.name',index:'checker.name',align:'left',width:60,label : '<s:text name="sysPostMove.checker" />',hidden:false,highsearch:true},	
				{name:'checkDate',index:'checkDate',align:'center',width:70,label : '<s:text name="sysPostMove.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'doner.name',index:'doner.name',align:'left',width:60,label : '<s:text name="sysPostMove.doner" />',hidden:false,highsearch:true},			
				{name:'doneDate',index:'doneDate',align:'center',width:80,label : '<s:text name="sysPostMove.doneDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				</c:if>
				{name:'state',index:'state',align:'center',width:60,label : '<s:text name="sysPostMove.state" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '0:新建;1:已审核;2:已执行;'},highsearch:true},					
				{name:'remark',index:'remark',align:'left',width:250,label : '<s:text name="sysPostMove.remark" />',hidden:false,highsearch:true}			
	        ],
	        jsonReader : {
				root : "sysPostMoves", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'code',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="sysPostMoveList.title" />',
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
				 gridContainerResize('sysPostMove','layout');
			 	var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  	id=rowIds[i];
	              	  	if(ret[i]['state']=="0"){
	              		  	setCellText(this,id,'state','<span >新建</span>');
	              	  	}else if(ret[i]['state']=="1"){
	              		  	setCellText(this,id,'state','<span style="color:green" >已审核</span>');
	              	  	}else if(ret[i]['state']=="2"){
	              		  	setCellText(this,id,'state','<span style="color:blue" >已执行</span>');
	              	  	}
	              		var snapId = ret[i]["hrPersonHis.hisSnapId"];
	              	  	if(ret[i]["changedSnapCode"]){
	              	  		snapId = ret[i]["hrPersonHis.hrPersonPk.personId"] + "_" + ret[i]["changedSnapCode"]
	              	  	}
	              	  	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPostMoveRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
	              		setCellText(this,id,'hrPersonHis.name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrPersonRecord(\''+snapId+'\');">'+ret[i]["hrPersonHis.name"]+'</a>');
	              	}
	            }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
           		var dataTest = {"id":"sysPostMove_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('sysPostMove_gridtable','com.huge.ihos.hr.changesInPersonnel.model.SysPostMove',initFlag);
       		} 
    	});
    	jQuery(sysPostMoveGrid).jqGrid('bindKeys');
    	hrPersonTreeInPostMove();
    	
    	//实例化ToolButtonBar
        var sysPostMove_menuButtonArrJson = "${menuButtonArrJson}";
        var sysPostMove_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(sysPostMove_menuButtonArrJson,false)));
        var sysPostMove_toolButtonBar = new ToolButtonBar({el:$('#sysPostMove_buttonBar'),collection:sysPostMove_toolButtonCollection,attributes:{
         tableId : 'sysPostMove_gridtable',
         baseName : 'sysPostMove',
         width : 600,
         height : 600,
         base_URL : null,
         optId : null,
         fatherGrid : null,
         extraParam : null,
         selectNone : "请选择记录。",
         selectMore : "只能选择一条记录。",
         addTitle : '<s:text name="sysPostMoveNew.title"/>',
         editTitle : null
        }}).render();
        //实例化结束
        var sysPostMove_function = new scriptFunction();
        sysPostMove_function.optBeforeCall = function(e,$this,param){
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
    	 sysPostMove_toolButtonBar.addCallBody('1002020301',function(e,$this,param){
    		 var zTree = $.fn.zTree.getZTreeObj("sysPostMoveTypeTreeLeft"); 
 	    	var nodes = zTree.getSelectedNodes(); 
 		    if(nodes.length!=1 || nodes[0].subSysTem!='PERSON'){
 		    	alertMsg.error("请选择人员。");
 	      		return;
 		    }
 		    if(nodes[0].actionUrl == '1'){
 		    	alertMsg.error("已停用人员不能调岗。");
 	      		return;
 		    }
 			var url = "editSysPostMove?navTabId=sysPostMove_gridtable&personId="+nodes[0].id;
 			var winTitle='<s:text name="sysPostMoveNew.title"/>';
 			$.pdialog.open(url,'addSysPostMove',winTitle, {mask:true,width : 666,height : 382,maxable:false,resizable:false});
    	 },{});
    	 sysPostMove_toolButtonBar.addBeforeCall('1002020301',function(e,$this,param){
 			return sysPostMove_function.optBeforeCall(e,$this,param);
     	},{checkPeriod:"checkPeriod"});
    	 //删除
    	 sysPostMove_toolButtonBar.addCallBody('1002020302',function(e,$this,param){
    		 var url = "${ctx}/sysPostMoveGridEdit?oper=del"
    				var sid = jQuery("#sysPostMove_gridtable").jqGrid('getGridParam','selarrrow');
    				if (sid == null || sid.length == 0) {
    					alertMsg.error("请选择记录。");
    					return;
    				} else {
    					for(var i = 0;i < sid.length; i++){
    						var rowId = sid[i];
    						var row = jQuery("#sysPostMove_gridtable").jqGrid('getRowData',rowId);
    						if(row['state']!='0'){
    							alertMsg.error("只能删除处于新建状态的记录!");
    							return;
    						}
    					}
    					url = url+"&id="+sid+"&navTabId=sysPostMove_gridtable";
    					alertMsg.confirm("确认删除？", {
    						okCall : function() {
    							jQuery.post(url,function(data) {
    								formCallBack(data);
    							});
    						}
    					});
    				}
    	 },{});
    	 sysPostMove_toolButtonBar.addBeforeCall('1002020302',function(e,$this,param){
  			return sysPostMove_function.optBeforeCall(e,$this,param);
      	},{checkPeriod:"checkPeriod"});
    	 //修改
    	 sysPostMove_toolButtonBar.addCallBody('1002020303',function(e,$this,param){
    		  	var sid = jQuery("#sysPostMove_gridtable").jqGrid('getGridParam','selarrrow');
    	       	if(sid==null || sid.length !=1){
    	       		alertMsg.error("请选择一条记录。");
    				return;
    			}
    	       	var row = jQuery("#sysPostMove_gridtable").jqGrid('getRowData',sid[0]);
    	       	if(row['state']!='0'){
    				alertMsg.error("只能修改处于新建状态的记录!");
    				return;
    			}
    			var winTitle='<s:text name="sysPostMoveEdit.title"/>';
    			var url = "editSysPostMove?id="+sid+"&navTabId=sysPostMove_gridtable";
    			$.pdialog.open(url,'editSysPostMove',winTitle, {mask:true,width : 666,height : 382,maxable:false,resizable:false}); 
    	 },{});
    	 sysPostMove_toolButtonBar.addBeforeCall('1002020303',function(e,$this,param){
  			return sysPostMove_function.optBeforeCall(e,$this,param);
      	},{checkPeriod:"checkPeriod"});
    	 //审核
    	 sysPostMove_toolButtonBar.addCallBody('1002020304',function(e,$this,param){
    		 var url = "${ctx}/sysPostMoveGridEdit?oper=check"
    				var sid = jQuery("#sysPostMove_gridtable").jqGrid('getGridParam','selarrrow');
    				if (sid == null || sid.length == 0) {
    					alertMsg.error("请选择记录。");
    					return;
    				} else {
    					for(var i = 0;i < sid.length; i++){
    						var rowId = sid[i];
    						var row = jQuery("#sysPostMove_gridtable").jqGrid('getRowData',rowId);
    						if(row['state']!='0'){
    							alertMsg.error("只能审核处于新建状态的记录!");
    							return;
    						}
    					}
    					url = url+"&id="+sid+"&navTabId=sysPostMove_gridtable";
    					alertMsg.confirm("确认审核？", {
    						okCall : function() {
    							$.post(url,function(data) {
    								formCallBack(data);
    							});
    						}
    					});
    				}
    	 },{});
    	 sysPostMove_toolButtonBar.addBeforeCall('1002020304',function(e,$this,param){
  			return sysPostMove_function.optBeforeCall(e,$this,param);
      	},{checkPeriod:"checkPeriod"});
    	 //销审
    	 sysPostMove_toolButtonBar.addCallBody('1002020305',function(e,$this,param){
    		 var url = "${ctx}/sysPostMoveGridEdit?oper=cancelCheck"
    	           	var sid = jQuery("#sysPostMove_gridtable").jqGrid('getGridParam','selarrrow');
    	           	if(sid==null|| sid.length == 0){       	
    	   				alertMsg.error("请选择记录。");
    	   				return;
    	   			} else {
    					for(var i = 0;i < sid.length; i++){
    						var rowId = sid[i];
    						var row = jQuery("#sysPostMove_gridtable").jqGrid('getRowData',rowId);
    						if(row['state']!='1'){
    							alertMsg.error("只有已审核的记录才能够被销审!");
    							return;
    						}
    					}
    					url = url+"&id="+sid+"&navTabId=sysPostMove_gridtable";
    					alertMsg.confirm("确认销审？", {
    						okCall : function() {
    							jQuery.post(url,function(data) {
    								formCallBack(data);
    							});
    						}
    					});
    				}
    	 },{});
    	 sysPostMove_toolButtonBar.addBeforeCall('1002020305',function(e,$this,param){
  			return sysPostMove_function.optBeforeCall(e,$this,param);
      	},{checkPeriod:"checkPeriod"});
    	 //执行
    	 sysPostMove_toolButtonBar.addCallBody('1002020306',function(e,$this,param){
    		 var url = "${ctx}/sysPostMoveGridEdit?oper=done"
    	          	var sid = jQuery("#sysPostMove_gridtable").jqGrid('getGridParam','selarrrow');
    	          	if(sid==null|| sid.length == 0){       	
    	  				alertMsg.error("请选择记录。");
    	  				return;
    	  			} else {
    					for(var i = 0;i < sid.length; i++){
    						var rowId = sid[i];
    						var row = jQuery("#sysPostMove_gridtable").jqGrid('getRowData',rowId);
    						if(row['state']!='1'){
    							alertMsg.error("只能执行处于已审核状态的记录!");
    							return;
    						}
    					}
    					url = url+"&id="+sid+"&navTabId=sysPostMove_gridtable";
    					var pactUrl="${ctx}/sysPostMoveGridEdit?oper=donePact";
    					pactUrl = pactUrl+"&id="+sid+"&navTabId=sysPostMove_gridtable";
    					alertMsg.confirm("确认执行？", {
    						okCall : function() {
    							jQuery.post(url,function(data) {
    								if(data.statusCode==200){
    										formCallBack(data);
    										if(data.pactExist){
    											alertMsg.confirm("确认修改合同？", {
        					   						okCall : function() {
        					   							jQuery.post(pactUrl,function(data) {
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
    	 sysPostMove_toolButtonBar.addBeforeCall('1002020306',function(e,$this,param){
  			return sysPostMove_function.optBeforeCall(e,$this,param);
      	},{checkPeriod:"checkPeriod"});
    	//设置表格列
         var sysPostMove_setColShowButton = {id:'1002020307',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
           callBody:function(){
            setColShow('sysPostMove_gridtable','com.huge.ihos.hr.changesInPersonnel.model.SysPostMove');
           }};
         sysPostMove_toolButtonBar.addButton(sysPostMove_setColShowButton);//实例化ToolButtonBar
  	});
	function viewPostMoveRecord(id){
		var url = "editSysPostMove?oper=view&id="+id;
		$.pdialog.open(url,'viewSysPostMove','人员调岗信息', {mask:true,width : 666,height : 382,maxable:false,resizable:false});
	}
	/*-----------------------------tree method area------------------------------*/
	function hrPersonTreeInPostMove() {
		var url = "makeHrPersonTree?";
		/* var showDisabledDept = jQuery("#sysPostMove_showDisabledDept").attr("checked");
	    if(showDisabledDept){
	    	url += "&showDisabledDept=true"
	    } */
	    /* var showDisabledPerson = jQuery("#sysPostMove_showDisabledPerson").attr("checked");
	    if(showDisabledPerson){
	    	url += "&showDisabledPerson=true"
	    } */
		$.get("makeHrPersonTree", {"_" : $.now()}, function(data) {
		   var hrPersonTreeData = data.hrPersonTreeNodes;
		   var hrPersonTree = $.fn.zTree.init($("#sysPostMoveTypeTreeLeft"),ztreesetting_hrPersonTreeInPostMove, hrPersonTreeData);
		   var nodes = hrPersonTree.getNodes();
		   hrPersonTree.expandNode(nodes[0], true, false, true);
		   hrPersonTree.selectNode(nodes[0]);
		   toggleDisabledOrCount({treeId:'sysPostMoveTypeTreeLeft',
		        showCode:jQuery('#sysPostMove_showCode')[0],
		        disabledDept:jQuery("#sysPostMove_showDisabledDept")[0],
		        disabledPerson: jQuery("#sysPostMove_showDisabledPerson")[0],
		        showCount:jQuery('#sysPostMove_showPersonCount')[0]});
		});
		jQuery("#sysPostMove_expandTree").text("展开");
	}
	var ztreesetting_hrPersonTreeInPostMove = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false,
			fontCss : setDisabledDeptFontCss
		},
		callback : {
			beforeDrag:function(){return false},
			onClick : function(event, treeId, treeNode, clickFlag){
				var urlString = "sysPostMoveGridList?1=1";
				var nodeId = treeNode.id;
			    if(nodeId!="-1"){
			    	if(treeNode.subSysTem==='ORG'){
				    	urlString += "&orgCode="+nodeId;
			    	}else if(treeNode.subSysTem==='DEPT'){
				    	urlString += "&deptId="+nodeId;
			    	}else{
			    		urlString += "&personId="+nodeId;
			    	}
			    }
			    var showDisabledDept = jQuery("#sysPostMove_showDisabledDept").attr("checked");
			    if(showDisabledDept){
			    	urlString += "&showDisabledDept=true"
			    }
			    var showDisabledPerson = jQuery("#sysPostMove_showDisabledPerson").attr("checked");
			    if(showDisabledPerson){
			    	urlString += "&showDisabledPerson=true"
			    }
				urlString=encodeURI(urlString);
				jQuery("#sysPostMove_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
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
	
	function showDisabledDeptInPostMove(obj){
		var urlString = jQuery("#sysPostMove_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledDept=true";
		}
		/* var urlString = "sysPostMoveGridList?1=1";
		if(obj.checked){
			urlString += "&showDisabledDept=true";
		} */
		var showDisabledPerson = jQuery("#sysPostMove_showDisabledPerson").attr("checked");
	    if(showDisabledPerson){
	    	urlString += "&showDisabledPerson=true"
	    }
	    //hrPersonTreeInPostMove();
	     toggleDisabledOrCount({treeId:'sysPostMoveTypeTreeLeft',
		        showCode:jQuery('#sysPostMove_showCode')[0],
		        disabledDept:jQuery("#sysPostMove_showDisabledDept")[0],
		        disabledPerson: jQuery("#sysPostMove_showDisabledPerson")[0],
		        showCount:jQuery('#sysPostMove_showPersonCount')[0]});
		urlString=encodeURI(urlString);
		jQuery("#sysPostMove_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	function showDisabledPersonInPostMove(obj){
		var urlString = jQuery("#sysPostMove_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledPerson=true"
		}
		var showDisabledDept = jQuery("#sysPostMove_showDisabledDept").attr("checked");
	    if(showDisabledDept){
	    	urlString += "&showDisabledDept=true"
	    }
	    toggleDisabledOrCount({treeId:'sysPostMoveTypeTreeLeft',
	        showCode:jQuery('#sysPostMove_showCode')[0],
	        disabledDept:jQuery("#sysPostMove_showDisabledDept")[0],
	        disabledPerson: jQuery("#sysPostMove_showDisabledPerson")[0],
	        showCount:jQuery('#sysPostMove_showPersonCount')[0]});
		urlString=encodeURI(urlString);
		jQuery("#sysPostMove_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
</script>

<div class="page">
	<div class="pageHeader" id="sysPostMove_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="sysPostMove_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name="sysPostMove.code"/>:
						<input type="text" name="filter_LIKES_code" style="width:80px;"/>
					</label>
					<label class="queryarea-label">
						<s:text name="sysPostMove.name"/>:
						<input type="text" name="filter_LIKES_name" style="width:80px;"/>
					</label>
					<label class="queryarea-label">
						<s:text name="sysPostMove.person"/>:
						<input type="text" name="filter_LIKES_hrPersonHis.name" style="width:80px;"/>
					</label>
					<label class="queryarea-label">
						<s:text name="sysPostMove.reason"/>:
						<input type="text" name="filter_LIKES_reason" style="width:80px;"/>
					</label>
                    <label class="queryarea-label">
						<s:select id="PostMove_searchTime"   list="#{'0':'执行日期','1':'申请日期','2':'审核日期'}" style="width:100px;font-size:9pt;" ></s:select>
						<s:text name=" 从"/>:
						<input type="text" id="PostMoveBeginDate"  name="hrPerson.beginDate" onclick="changeSysTimeType('PostMove','doneDate','makeDate','checkDate')" onblur="checkQueryDate('PostMoveBeginDate','PostMoveEndDate',0,'PostMove_beginDate','PostMove_endDate')" style="width:65px"/>
					</label><label class="queryarea-label">
						<s:text name="至"/>:
						<input type="text" id="PostMoveEndDate" name="hrPerson.endDate" onclick="changeSysTimeType('PostMove','doneDate','makeDate','checkDate')"  onblur="checkQueryDate('PostMoveBeginDate','PostMoveEndDate',1,'PostMove_beginDate','PostMove_endDate')" style="width:65px"/>
						<input type="hidden" id="PostMove_beginDate" name="hrPerson.endDate"  />
						<input type="hidden" id="PostMove_endDate" name="hrPerson.endDate"  />
    				 </label>
					<%-- <label style="float:none;white-space:nowrap" >
					<s:text name='sysPostMove.moveDate'/>:
						<input type="text"	id="sysPostMove_search_moveDate_from" name="filter_GED_moveDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'sysPostMove_search_moveDate_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="sysPostMove_search_moveDate_to" name="filter_LED_moveDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'sysPostMove_search_moveDate_from\')}'})">
					</label> --%>
					<label class="queryarea-label">
						<s:text name='sysPostMove.state'/>:
						<s:select name="filter_EQS_state"  list="#{'':'--','0':'新建','1':'已审核','2':'已执行'}" style="width:60px;font-size:9pt;" ></s:select>
					</label>
					<label class="queryarea-label">
						<s:text name="sysPostMove.remark"/>:
						<input type="text" name="filter_LIKES_remark" style="width:80px;"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('sysPostMove_search_form','sysPostMove_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li> -->
<!-- 						<div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('sysPostMove_search_form','sysPostMove_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="sysPostMove_buttonBar">
<!-- 			<ul class="toolBar"> -->
<!-- 				<li> -->
<%-- 					<a id="sysPostMove_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add"/></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPostMove_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPostMove_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPostMove_gridtable_check" class="checkbutton"  href="javaScript:"><span><s:text name="button.check" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPostMove_gridtable_cancelCheck" class="delallbutton"  href="javaScript:"><span><s:text name="button.cancelCheck" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPostMove_gridtable_done" class="confirmbutton"  href="javaScript:"><span><s:text name="button.done" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a class="settlebutton"  href="javaScript:setColShow('sysPostMove_gridtable','com.huge.ihos.hr.changesInPersonnel.model.SysPostMove')"><span><s:text name="button.setColShow" /></span></a> --%>
<!-- 				</li> -->
<!-- 			</ul> -->
		</div>
		<div id="sysPostMove_container">
			<div id="sysPostMove_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
   		 	<div class="treeTopCheckBox">
        	 <span>
         		显示编码
        	 	<input id="sysPostMove_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'sysPostMoveTypeTreeLeft',showCode:this,disabledDept:jQuery('#sysPostMove_showDisabledDept')[0],disabledPerson:jQuery('#sysPostMove_showDisabledPerson')[0],showCount:jQuery('#sysPostMove_showPersonCount')[0]})"/>
      		 </span>
      		  <span>
				显示人员数
				<input id="sysPostMove_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'sysPostMoveTypeTreeLeft',showCode:jQuery('#sysPostMove_showCode')[0],disabledDept:jQuery('#sysPostMove_showDisabledDept')[0],disabledPerson:jQuery('#sysPostMove_showDisabledPerson')[0],showCount:this})"/>
			</span>
       		<label id="sysPostMove_expandTree" onclick="toggleExpandHrTree(this,'sysPostMoveTypeTreeLeft')">展开</label>
      		</div>
     		<div class="treeTopCheckBox">
     		 <span>
         		显示停用部门
        		<input id="sysPostMove_showDisabledDept" type="checkbox" onclick="showDisabledDeptInPostMove(this)"/>
       		</span>
        	 <span>
         		显示停用人员
        		<input id="sysPostMove_showDisabledPerson" type="checkbox" onclick="showDisabledPersonInPostMove(this)"/>
       		 </span>
      		</div>
      		<div class="treeTopCheckBox">
       		<span>
         		快速查询：
        		<input type="text" onKeyUp="searchTree('sysPostMoveTypeTreeLeft',this)"/>
       		</span>
			</div>
    			<div id="sysPostMoveTypeTreeLeft" class="ztree"></div>
  			</div>
			<div id="sysPostMove_layout-center" class="pane ui-layout-center">
				<div id="sysPostMove_gridtable_div" class="grid-wrapdiv" 
					style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="sysPostMove_gridtable_navTabId" value="${sessionScope.navTabId}">
					<label style="display: none" id="sysPostMove_gridtable_addTile">
						<s:text name="sysPostMoveNew.title"/>
					</label> 
					<label style="display: none"
						id="sysPostMove_gridtable_editTile">
						<s:text name="sysPostMoveEdit.title"/>
					</label>
					<label style="display: none"
						id="sysPostMove_gridtable_selectNone">
						<s:text name='list.selectNone'/>
					</label>
					<label style="display: none"
						id="sysPostMove_gridtable_selectMore">
						<s:text name='list.selectMore'/>
					</label>
					<div id="load_sysPostMove_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 					<table id="sysPostMove_gridtable"></table>
				</div>
				<div class="panelBar" id="sysPostMove_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="sysPostMove_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="sysPostMove_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
			
					<div id="sysPostMove_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>