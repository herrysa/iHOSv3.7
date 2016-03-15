
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var sysPersonMoveGridIdString="#sysPersonMove_gridtable";
	jQuery(document).ready(function() {
		jQuery("#sysPersonMove_pageHeader").find("select").css("font-size","12px");
		var sysPersonMoveFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('sysPersonMove_container','sysPersonMove_gridtable',sysPersonMoveFullSize);
		var initFlag = 0;
		var sysPersonMoveGrid = jQuery(sysPersonMoveGridIdString);
	    sysPersonMoveGrid.jqGrid({
	    	url : "sysPersonMoveGridList?1=1",
	    	editurl:"sysPersonMoveGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="sysPersonMove.id" />',hidden:true,key:true},
				{name:'code',index:'code',align:'left',width:120,label : '<s:text name="sysPersonMove.code" />',hidden:false,highsearch:true},
				{name:'name',index:'name',align:'left',width:100,label : '<s:text name="sysPersonMove.name" />',hidden:false,highsearch:true},
				{name:'hrPersonHis.name',index:'hrPersonHis.name',width:80,align:'left',label : '<s:text name="person.name" />',hidden:false,highsearch:true},
				{name:'hrPersonHis.hrPersonPk.personId',index:'hrPersonHis.hrPersonPk.personId',width:80,align:'left',label : '<s:text name="person.name" />',hidden:true},
				{name:'changedSnapCode',index:'changedSnapCode',width:80,align:'left',label : '<s:text name="person.name" />',hidden:true},
				{name:'hrPersonHis.hrOrg.orgname',index:'hrPersonHis.hrOrg.orgname',width:120,align:'left',label : '<s:text name="sysPersonMove.orgCode" />',hidden:false,highsearch:true,sortable:false},
				{name:'hrPersonHis.hisSnapId',index:'hrPersonHis.hisSnapId',width:100,align:'left',label : '<s:text name="person.hisSnapId" />',hidden:true},
				{name:'hrPersonHis.departmentHis.name',index:'hrPersonHis.departmentHis.name',width:80,align:'left',label : '<s:text name="oldDepartment.name" />',hidden:false,highsearch:true,sortable:false},
				{name:'hrPersonHis.postType',index:'hrPersonHis.postType',align:'left',width:60,label : '<s:text name="sysPersonMove.oldPostType" />',hidden:false,highsearch:true,sortable:false},
				{name:'hrPersonHis.duty.name',index:'hrPersonHis.duty.name',align:'left',width:60,label : '<s:text name="sysPersonMove.oldDuty" />',hidden:false,highsearch:true,sortable:false},
				{name:'hrDeptHisNew.hrOrg.orgname',index:'hrDeptHisNew.hrOrg.orgname',width:120,align:'left',label : '<s:text name="sysPersonMove.orgCodeNew" />',hidden:false,highsearch:true},
				{name:'hrDeptHisNew.name',index:'hrDeptHisNew.name',width:80,align:'left',label : '<s:text name="newDepartment.name" />',hidden:false,highsearch:true},
				{name:'postType',index:'postType',width:60,align:'left',label : '<s:text name="sysPersonMove.postType" />',hidden:false,highsearch:true},
				{name:'duty.name',index:'duty.name',width:60,align:'left',label : '<s:text name="sysPersonMove.duty" />',hidden:false,highsearch:true},
				{name:'reason',index:'reason',width:200,align:'left',label : '<s:text name="sysPersonMove.reason" />',hidden:false,highsearch:true},
// 				{name:'moveDate',index:'moveDate',align:'center',width:80,label : '<s:text name="sysPersonMove.moveDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="hrPerson.yearMonth" />',hidden:false,highsearch:true},	
				<c:if test="${personNeedCheck=='1'}">
				{name:'maker.name',index:'maker.name',width:60,align:'left',label : '<s:text name="maker.name" />',hidden:false,highsearch:true},
				{name:'makeDate',index:'makeDate',width:70,align:'center',label : '<s:text name="sysPersonMove.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'checker.name',index:'checker.name',width:60,align:'left',label : '<s:text name="checker.name" />',hidden:false,highsearch:true},
				{name:'checkDate',index:'checkDate',width:70,align:'center',label : '<s:text name="sysPersonMove.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'doner.name',index:'doner.name',align:'left',width:60,label : '<s:text name="sysPersonMove.doner" />',hidden:false,highsearch:true,highsearch:true},
				{name:'doneDate',index:'doneDate',align:'center',width:70,label : '<s:text name="sysPersonMove.doneDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				</c:if>
				//{name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="sysPersonMove.orgCode" />',hidden:true},				
				{name:'state',index:'state',width:60,align:'center',label : '<s:text name="sysPersonMove.state" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '0:新建;1:已审核;2:已执行;'},highsearch:true},
				{name:'remark',index:'remark',width:250,align:'left',label : '<s:text name="sysPersonMove.remark" />',hidden:false,highsearch:true}
	        ],
	        jsonReader : {
				root : "sysPersonMoves", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'code',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="sysPersonMoveList.title" />',
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
				 /*2015.08.28 form search change*/
				 gridContainerResize('sysPersonMove','layout');
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
	              		setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPersonMoveRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
	              		setCellText(this,id,'hrPersonHis.name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrPersonRecord(\''+snapId+'\');">'+ret[i]["hrPersonHis.name"]+'</a>');
	              	}
	            }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
           		var dataTest = {"id":"sysPersonMove_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('sysPersonMove_gridtable','com.huge.ihos.hr.changesInPersonnel.model.SysPersonMove',initFlag);
       		} 
    	});
    	jQuery(sysPersonMoveGrid).jqGrid('bindKeys');
    	hrPersonTreeInPersonMove();
    	//实例化ToolButtonBar
        var sysPersonMove_menuButtonArrJson = "${menuButtonArrJson}";
        var sysPersonMove_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(sysPersonMove_menuButtonArrJson,false)));
        var sysPersonMove_toolButtonBar = new ToolButtonBar({el:$('#sysPersonMove_buttonBar'),collection:sysPersonMove_toolButtonCollection,attributes:{
         tableId : 'sysPersonMove_gridtable',
         baseName : 'sysPersonMove',
         width : 600,
         height : 600,
         base_URL : null,
         optId : null,
         fatherGrid : null,
         extraParam : null,
         selectNone : "请选择记录。",
         selectMore : "只能选择一条记录。",
         addTitle : '<s:text name="sysPersonMoveNew.title"/>',
         editTitle : null
        }}).render();
        //实例化结束
        var sysPersonMove_function = new scriptFunction();
        sysPersonMove_function.optBeforeCall = function(e,$this,param){
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
    	sysPersonMove_toolButtonBar.addCallBody('1002020201',function(e,$this,param){
    		var zTree = $.fn.zTree.getZTreeObj("sysPersonMoveTypeTreeLeft"); 
	    	var nodes = zTree.getSelectedNodes(); 
		    if(nodes.length!=1 || nodes[0].subSysTem!='PERSON'){
		    	alertMsg.error("请选择人员。");
	      		return;
		    }
		    if(nodes[0].actionUrl == '1'){
		    	alertMsg.error("已停用人员不能调动。");
	      		return;
		    }
			var url = "editSysPersonMove?navTabId=sysPersonMove_gridtable&personId="+nodes[0].id;
			var winTitle='<s:text name="sysPersonMoveNew.title"/>';
			$.pdialog.open(url,'addSysPersonMove',winTitle, {mask:true,width : 666,height : 400,maxable:false,resizable:false});
    	},{});
    	sysPersonMove_toolButtonBar.addBeforeCall('1002020201',function(e,$this,param){
			return sysPersonMove_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
    	//删除
    	sysPersonMove_toolButtonBar.addCallBody('1002020202',function(e,$this,param){
    		var url = "${ctx}/sysPersonMoveGridEdit?oper=del"
    			var sid = jQuery("#sysPersonMove_gridtable").jqGrid('getGridParam','selarrrow');
    			if (sid == null || sid.length == 0) {
    				alertMsg.error("请选择记录。");
    				return;
    			} else {
    				for(var i = 0;i < sid.length; i++){
    					var rowId = sid[i];
    					var row = jQuery("#sysPersonMove_gridtable").jqGrid('getRowData',rowId);
    					
    					if(row['state']!='0'){
    						alertMsg.error("只能删除处于新建状态的记录!");
    						return;
    					}
    				}
    				url = url+"&id="+sid+"&navTabId=sysPersonMove_gridtable";
    				alertMsg.confirm("确认删除？", {
    					okCall : function() {
    						$.post(url,function(data) {
    							formCallBack(data);
    						});
    					}
    				});
    			}
    	},{});
    	sysPersonMove_toolButtonBar.addBeforeCall('1002020202',function(e,$this,param){
			return sysPersonMove_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
    	//修改
    	sysPersonMove_toolButtonBar.addCallBody('1002020203',function(e,$this,param){
    	 	var sid = jQuery("#sysPersonMove_gridtable").jqGrid('getGridParam','selarrrow');
	       	if(sid==null || sid.length !=1){
	       		alertMsg.error("请选择一条记录。");
				return;
			}
	       	var row = jQuery("#sysPersonMove_gridtable").jqGrid('getRowData',sid[0]);
	       	if(row['state']!='0'){
				alertMsg.error("只能修改处于新建状态的记录!");
				return;
			}
			var winTitle='<s:text name="sysPersonMoveEdit.title"/>';
			var url = "editSysPersonMove?id="+sid+"&navTabId=sysPersonMove_gridtable";
			$.pdialog.open(url,'editSysPersonMove',winTitle, {mask:true,width : 666,height : 400,maxable:false,resizable:false});
    	},{});
    	sysPersonMove_toolButtonBar.addBeforeCall('1002020203',function(e,$this,param){
			return sysPersonMove_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
    	//审核
    	sysPersonMove_toolButtonBar.addCallBody('1002020204',function(e,$this,param){
    		var url = "${ctx}/sysPersonMoveGridEdit?oper=check"
    			var sid = jQuery("#sysPersonMove_gridtable").jqGrid('getGridParam','selarrrow');
    			if (sid == null || sid.length == 0) {
    				alertMsg.error("请选择记录。");
    				return;
    			} else {
    				for(var i = 0;i < sid.length; i++){
    					var rowId = sid[i];
    					var row = jQuery("#sysPersonMove_gridtable").jqGrid('getRowData',rowId);
    					
    					if(row['state']!='0'){
    						alertMsg.error("只能审核处于新建状态的记录!");
    						return;
    					}
    				}
    				url = url+"&id="+sid+"&navTabId=sysPersonMove_gridtable";
    				alertMsg.confirm("确认审核？", {
    					okCall : function() {
    						$.post(url,function(data) {
    							formCallBack(data);
    						});
    					}
    				});
    			}
    	},{});
    	sysPersonMove_toolButtonBar.addBeforeCall('1002020204',function(e,$this,param){
			return sysPersonMove_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
    	//销审
    	sysPersonMove_toolButtonBar.addCallBody('1002020205',function(e,$this,param){
    		var url = "${ctx}/sysPersonMoveGridEdit?oper=cancelCheck"
    	        var sid = jQuery("#sysPersonMove_gridtable").jqGrid('getGridParam','selarrrow');
    	        if(sid==null|| sid.length == 0){       	
    	  			alertMsg.error("请选择记录。");
    	  			return;
    	  		} else {
    				for(var i = 0;i < sid.length; i++){
    					var rowId = sid[i];
    					var row = jQuery("#sysPersonMove_gridtable").jqGrid('getRowData',rowId);
    					
    					if(row['state']!='1'){
    						alertMsg.error("只有已审核的记录才能够被销审!");
    						return;
    					}
    				}
    				url = url+"&id="+sid+"&navTabId=sysPersonMove_gridtable";
    				alertMsg.confirm("确认销审？", {
    					okCall : function() {
    						$.post(url,function(data) {
    							formCallBack(data);
    						});
    					}
    				});
    			}
    	},{});
    	sysPersonMove_toolButtonBar.addBeforeCall('1002020205',function(e,$this,param){
			return sysPersonMove_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
    	//执行
    	sysPersonMove_toolButtonBar.addCallBody('1002020206',function(e,$this,param){
    		var url = "${ctx}/sysPersonMoveGridEdit?oper=done"
    	        var sid = jQuery("#sysPersonMove_gridtable").jqGrid('getGridParam','selarrrow');
    	        if(sid==null|| sid.length == 0){       	
    	  			alertMsg.error("请选择记录。");
    	  			return;
    	  		} else {
    				for(var i = 0;i < sid.length; i++){
    					var rowId = sid[i];
    					var row = jQuery("#sysPersonMove_gridtable").jqGrid('getRowData',rowId);
    					
    					if(row['state']!='1'){
    						alertMsg.error("只能执行处于已审核状态的记录!");
    						return;
    					}
    				}
    				url = url+"&id="+sid+"&navTabId=sysPersonMove_gridtable";
    				var pactUrl="${ctx}/sysPersonMoveGridEdit?oper=donePact";
    				pactUrl = pactUrl+"&id="+sid+"&navTabId=sysPersonMove_gridtable";
    				alertMsg.confirm("确认执行？", {
    					okCall : function() {
    						$.post(url,function(data) {
    							if(data.statusCode==200){
    								formCallBack(data);
    								var delTreeNodes = data.delTreeNodes;
    								var addTreeNodes = data.addTreeNodes;
    								if(delTreeNodes){
    									$.each(delTreeNodes, function(){ 
    										dealHrTreeNode('sysPersonMoveTypeTreeLeft',this,'del','person');  
    									});  

    								}
    								if(addTreeNodes){
    									$.each(addTreeNodes, function(){ 
    										dealHrTreeNode("sysPersonMoveTypeTreeLeft",this,"add","person");  
    									});  

    								}
    								if(data.pactExist){
    									alertMsg.confirm("确认修改合同？", {
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
    	sysPersonMove_toolButtonBar.addBeforeCall('1002020206',function(e,$this,param){
			return sysPersonMove_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
    	//设置表格列
        var sysPersonMove_setColShowButton = {id:'1002020207',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
          callBody:function(){
           setColShow('sysPersonMove_gridtable','com.huge.ihos.hr.changesInPersonnel.model.SysPersonMove');
          }};
        sysPersonMove_toolButtonBar.addButton(sysPersonMove_setColShowButton);//实例化ToolButtonBar
  	});
	
	function viewPersonMoveRecord(id){
		var url = "editSysPersonMove?oper=view&id="+id;
		$.pdialog.open(url,'viewSysPersonMove','人员调动信息', {mask:true,width : 666,height : 400,maxable:false,resizable:false});
	}
	/*-----------------------------tree method area------------------------------*/
	function hrPersonTreeInPersonMove() {
		var url = "makeHrPersonTree?";
		$.get(url, {"_" : $.now()}, function(data) {
		   	var hrPersonTreeData = data.hrPersonTreeNodes;
		   	var hrPersonTree = $.fn.zTree.init($("#sysPersonMoveTypeTreeLeft"),ztreesetting_hrPersonTreeInPersonMove, hrPersonTreeData);
		   	var nodes = hrPersonTree.getNodes();
		   	hrPersonTree.expandNode(nodes[0], true, false, true);
		   	hrPersonTree.selectNode(nodes[0]);
		    toggleDisabledOrCount({treeId:'sysPersonMoveTypeTreeLeft',
		        showCode:jQuery('#sysPersonMove_showCode')[0],
		        disabledDept:jQuery("#sysPersonMove_showDisabledDept")[0],
		        disabledPerson:jQuery("#sysPersonMove_showDisabledPerson")[0],
		        showCount:jQuery('#sysPersonMove_showPersonCount')[0]}); 
// 		   	toogleShowDisabledDeptWithPerson(jQuery("#sysPersonMove_showDisabledDept")[0],jQuery("#sysPersonMove_showDisabledPerson")[0],'sysPersonMoveTypeTreeLeft');
// 			toogleShowDisabledPerson(jQuery("#sysPersonMove_showDisabledDept")[0],jQuery("#sysPersonMove_showDisabledPerson")[0],'sysPersonMoveTypeTreeLeft');
		});
		jQuery("#sysPersonMove_expandTree").text("展开");
	}
	
	var ztreesetting_hrPersonTreeInPersonMove = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false,
			fontCss : setDisabledDeptFontCss
		},
		callback : {
			beforeDrag:function(){return false},
			onClick : function(event, treeId, treeNode, clickFlag){
				var urlString = "sysPersonMoveGridList?1=1";
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
			    var showDisabledDept = jQuery("#sysPersonMove_showDisabledDept").attr("checked");
			    if(showDisabledDept){
			    	urlString += "&showDisabledDept=true"
			    }
			    var showDisabledPerson = jQuery("#sysPersonMove_showDisabledPerson").attr("checked");
			    if(showDisabledPerson){
			    	urlString += "&showDisabledPerson=true"
			    }
				urlString=encodeURI(urlString);
				jQuery("#sysPersonMove_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
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
	
	function showDisabledDeptInPersonMove(obj){
		var urlString = jQuery("#sysPersonMove_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledDept=true";
		}
		/* var urlString = "sysPersonMoveGridList?1=1";
		if(obj.checked){
			urlString += "&showDisabledDept=true";
		} */
		var showDisabledPerson = jQuery("#sysPersonMove_showDisabledPerson").attr("checked");
	    if(showDisabledPerson){
	    	urlString += "&showDisabledPerson=true"
	    }
		//hrPersonTreeInPersonMove();
		 toggleDisabledOrCount({treeId:'sysPersonMoveTypeTreeLeft',
		        showCode:jQuery('#sysPersonMove_showCode')[0],
		        disabledDept:jQuery("#sysPersonMove_showDisabledDept")[0],
		        disabledPerson: jQuery("#sysPersonMove_showDisabledPerson")[0],
		        showCount:jQuery('#sysPersonMove_showPersonCount')[0] });
// 		toogleShowDisabledDeptWithPerson(obj,jQuery("#sysPersonMove_showDisabledPerson")[0],'sysPersonMoveTypeTreeLeft');
		urlString=encodeURI(urlString);
		jQuery("#sysPersonMove_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	function showDisabledPersonInPersonMove(obj){
		var urlString = jQuery("#sysPersonMove_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledPerson=true"
		}
		var showDisabledDept = jQuery("#sysPersonMove_showDisabledDept").attr("checked");
	    if(showDisabledDept){
	    	urlString += "&showDisabledDept=true"
	    }
	    toggleDisabledOrCount({treeId:'sysPersonMoveTypeTreeLeft',
	        showCode:jQuery('#sysPersonMove_showCode')[0],
	        disabledDept:jQuery("#sysPersonMove_showDisabledDept")[0],
	        disabledPerson: jQuery("#sysPersonMove_showDisabledPerson")[0],
	        showCount:jQuery('#sysPersonMove_showPersonCount')[0] });
		//toogleShowDisabledPerson(jQuery("#sysPersonMove_showDisabledDept")[0],obj,'sysPersonMoveTypeTreeLeft');
		urlString=encodeURI(urlString);
		jQuery("#sysPersonMove_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
</script>

<div class="page">
	<div id="sysPersonMove_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="sysPersonMove_search_form" class="queryarea-form">
					<input type="hidden" name="filter_EQB_hrPerson.disable" id="sysPersonMove_search_form_showDisabledPerson" value=""/>
					<label class="queryarea-label">
						<s:text name="sysPersonMove.code"/>:
						<input type="text" name="filter_LIKES_code" style="width:80px;"/>
					</label>  
					<label class="queryarea-label">
						<s:text name="sysPersonMove.name"/>:
						<input type="text" name="filter_LIKES_name"  style="width:80px;"/>
					</label>  
					<label class="queryarea-label">
						<s:text name="person.name"/>:
						<input type="text" name="filter_LIKES_hrPersonHis.name"  style="width:80px;"/>
					</label>  
					<label class="queryarea-label">
						<s:text name='sysPersonMove.state'/>:
						<s:select name="filter_EQS_state"  list="#{'':'--','0':'新建','1':'已审核','2':'已执行'}" style="width:60px;font-size:9pt;" ></s:select>
					</label>  
					<label class="queryarea-label">
						<s:text name="sysPersonMove.reason"/>:
						<input type="text" name="filter_LIKES_reason"  style="width:80px;"/>
					</label>  
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='sysPersonMove.moveDate'/>:
						<input type="text"	id="sysPersonMove_search_moveDate_from" name="filter_GED_moveDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'sysPersonMove_search_moveDate_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="sysPersonMove_search_moveDate_to" name="filter_LED_moveDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'sysPersonMove_search_moveDate_from\')}'})">
					</label>   --%>
					<label class="queryarea-label">
						<s:select id="personMove_searchTime"   list="#{'0':'执行日期','1':'申请日期','2':'审核日期'}" style="width:100px;font-size:9pt;" ></s:select>
						<s:text name=" 从"/>:
						<input type="text" id="personMovebeginDate"  name="hrPerson.beginDate" onclick="changeSysTimeType('personMove','doneDate','makeDate','checkDate')" onblur="checkQueryDate('personMovebeginDate','personMoveendDate',0,'personMove_beginDate','personMove_endDate')" style="width:65px"/>
					</label><label class="queryarea-label">
						<s:text name="至"/>:
						<input type="text" id="personMoveendDate" name="hrPerson.endDate" onclick="changeSysTimeType('personMove','doneDate','makeDate','checkDate')"  onblur="checkQueryDate('personMovebeginDate','personMoveendDate',1,'personMove_beginDate','personMove_endDate')" style="width:65px"/>
						<input type="hidden" id="personMove_beginDate" name="hrPerson.endDate"  />
						<input type="hidden" id="personMove_endDate" name="hrPerson.endDate"  />
					</label>  
					<label class="queryarea-label">
						<s:text name="sysPersonMove.remark"/>:
						<input type="text" name="filter_LIKES_remark"/>
					</label>  
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('sysPersonMove_search_form','sysPersonMove_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('sysPersonMove_search_form','sysPersonMove_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="sysPersonMove_buttonBar">
<!-- 			<ul class="toolBar"> -->
<!-- 				<li> -->
<%-- 					<a id="sysPersonMove_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPersonMove_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPersonMove_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span> --%>
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPersonMove_gridtable_check" class="checkbutton"  href="javaScript:"><span><s:text name="button.check" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPersonMove_gridtable_cancelCheck" class="delallbutton"  href="javaScript:"><span><s:text name="button.cancelCheck" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPersonMove_gridtable_done" class="confirmbutton"  href="javaScript:"><span><s:text name="button.done" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a class="settlebutton"  href="javaScript:setColShow('sysPersonMove_gridtable','com.huge.ihos.hr.changesInPersonnel.model.SysPersonMove')"><span><s:text name="button.setColShow" /></span></a> --%>
<!-- 				</li> -->
<!-- 			</ul> -->
		</div>
		<div id="sysPersonMove_container">
			<div id="sysPersonMove_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
   		 	 <div class="treeTopCheckBox">
     			<span>
    			  显示编码
      			<input id="sysPersonMove_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'sysPersonMoveTypeTreeLeft',showCode:this,disabledDept:jQuery('#sysPersonMove_showDisabledDept')[0],disabledPerson:jQuery('#sysPersonMove_showDisabledPerson')[0],showCount:jQuery('#sysPersonMove_showPersonCount')[0]})"/>
    		 </span>
    		 <span>
				显示人员数
				<input id="sysPersonMove_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'sysPersonMoveTypeTreeLeft',showCode:jQuery('#sysPersonMove_showCode')[0],disabledDept:jQuery('#sysPersonMove_showDisabledDept')[0],disabledPerson:jQuery('#sysPersonMove_showDisabledPerson')[0],showCount:this})"/>
			</span>
     		<label id="sysPersonMove_expandTree" onclick="toggleExpandHrTree(this,'sysPersonMoveTypeTreeLeft')">展开</label>
    		</div>
    		<div class="treeTopCheckBox">
    		 <span>
      			显示停用部门
      		<input id="sysPersonMove_showDisabledDept" type="checkbox" onclick="showDisabledDeptInPersonMove(this)"/>
     		</span>
     		 <span>
      			显示停用人员
      		<input id="sysPersonMove_showDisabledPerson" type="checkbox" onclick="showDisabledPersonInPersonMove(this)"/>
     		</span>
    		</div>
    		<div class="treeTopCheckBox">
     		<span>
      			快速查询：
      		<input type="text" onKeyUp="searchTree('sysPersonMoveTypeTreeLeft',this)"/>
     		</span>
    		</div>
   		 		<div id="sysPersonMoveTypeTreeLeft" class="ztree"></div>
   		 	</div>
			<div id="sysPersonMove_layout-center" class="pane ui-layout-center">
				<div id="sysPersonMove_gridtable_div" class="grid-wrapdiv" 
						style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="sysPersonMove_gridtable_navTabId" value="${sessionScope.navTabId}">
					<label style="display: none" id="sysPersonMove_gridtable_addTile">
						<s:text name="sysPersonMoveNew.title"/>
					</label> 
					<label style="display: none"
						id="sysPersonMove_gridtable_editTile">
						<s:text name="sysPersonMoveEdit.title"/>
					</label>
					<label style="display: none"
						id="sysPersonMove_gridtable_selectNone">
						<s:text name='list.selectNone'/>
					</label>
					<label style="display: none"
						id="sysPersonMove_gridtable_selectMore">
						<s:text name='list.selectMore'/>
					</label>
					<div id="load_sysPersonMove_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
	 				<table id="sysPersonMove_gridtable"></table>
				</div>
	
				<div class="panelBar" id="sysPersonMove_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="sysPersonMove_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="sysPersonMove_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
			
					<div id="sysPersonMove_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
			 	</div> 
			</div>
		</div>
	</div>
</div>