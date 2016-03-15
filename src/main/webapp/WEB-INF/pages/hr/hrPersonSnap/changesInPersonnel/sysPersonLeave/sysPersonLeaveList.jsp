
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var sysPersonLeaveGridIdString="#sysPersonLeave_gridtable";
	
	jQuery(document).ready(function() {
		jQuery("#sysPersonLeave_pageHeader").find("select").css("font-size","12px");
		var sysPersonLeaveFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('sysPersonLeave_container','sysPersonLeave_gridtable',sysPersonLeaveFullSize);
		var initFlag = 0;
		var sysPersonLeaveGrid = jQuery(sysPersonLeaveGridIdString);
	    sysPersonLeaveGrid.jqGrid({
	    	url : "sysPersonLeaveGridList?1=1",
	    	editurl:"sysPersonLeaveGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="sysPersonLeave.id" />',hidden:true,key:true},		
				{name:'code',index:'code',align:'left',width:120,label : '<s:text name="sysPersonLeave.code" />',hidden:false,highsearch:true},		
				{name:'name',index:'name',align:'left',width:100,label : '<s:text name="sysPersonLeave.name" />',hidden:false,highsearch:true},	
				{name:'hrPersonHis.hrPersonPk.personId',index:'hrPersonHis.hrPersonPk.personId',align:'left',width:100,label : '<s:text name="sysPersonLeave.personId" />',hidden:true},
				{name:'hrPersonHis.name',index:'hrPersonHis.name',width:80,align:'left',label : '<s:text name="sysPersonLeave.person" />',hidden:false,highsearch:true},
				{name:'hrPersonHis.hrPersonPk.personId',index:'hrPersonHis.hrPersonPk.personId',width:80,align:'left',label : '<s:text name="person.name" />',hidden:true},
				{name:'changedSnapCode',index:'changedSnapCode',width:80,align:'left',label : '<s:text name="person.name" />',hidden:true},
				{name:'hrPersonHis.hrOrg.orgname',index:'hrPersonHis.hrOrg.orgname',width:120,align:'left',label : '<s:text name="sysPersonLeave.orgCode" />',hidden:false,highsearch:true,sortable:false},
				{name:'hrPersonHis.hisSnapId',index:'hrPersonHis.hisSnapId',width:100,align:'left',label : '<s:text name="person.hisSnapId" />',hidden:true},
				{name:'hrPersonHis.departmentHis.name',index:'hrPersonHis.departmentHis.name',width:80,align:'left',label : '<s:text name="sysPersonLeave.hrDept" />',hidden:false,highsearch:true,sortable:false},
				{name:'hrPersonHis.empType.name',index:'hrPersonHis.empType.name',width:80,align:'left',label : '<s:text name="sysPersonLeave.empType" />',hidden:false,highsearch:true,sortable:false},
				{name:'type',index:'type',align:'left',width:60,label : '<s:text name="sysPersonLeave.type" />',hidden:false,highsearch:true},
				{name:'reason',index:'reason',align:'left',width:200,label : '<s:text name="sysPersonLeave.reason" />',hidden:false,highsearch:true},
// 				{name:'leaveDate',index:'leaveDate',align:'center',width:80,label : '<s:text name="sysPersonLeave.leaveDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="hrPerson.yearMonth" />',hidden:false,highsearch:true},	
				<c:if test="${personNeedCheck=='1'}">
				{name:'maker.name',index:'maker.name',align:'left',width:60,label : '<s:text name="sysPersonLeave.maker" />',hidden:false,highsearch:true},	
				{name:'makeDate',index:'makeDate',align:'center',width:70,label : '<s:text name="sysPersonLeave.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
				{name:'checker.name',index:'checker.name',align:'left',width:60,label : '<s:text name="sysPersonLeave.checker" />',hidden:false,highsearch:true},	
				{name:'checkDate',index:'checkDate',align:'center',width:70,label : '<s:text name="sysPersonLeave.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'doner.name',index:'doner.name',align:'left',width:60,label : '<s:text name="sysPersonLeave.doner" />',hidden:false,highsearch:true},			
				{name:'doneDate',index:'doneDate',align:'center',width:70,label : '<s:text name="sysPersonLeave.doneDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				</c:if>
				{name:'state',index:'state',align:'center',width:60,label : '<s:text name="sysPersonLeave.state" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '0:新建;1:已审核;2:已执行;'},highsearch:true},		
				{name:'orgCode',index:'orgCode',align:'left',width:100,label : '<s:text name="sysPersonLeave.orgCode" />',hidden:true},				
				{name:'remark',index:'remark',align:'left',width:250,label : '<s:text name="sysPersonLeave.remark" />',hidden:false,highsearch:true}				
	        ],
	        jsonReader : {
				root : "sysPersonLeaves", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'code',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="sysPersonLeaveList.title" />',
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
				/*2015.08.28 Layout change*/
				gridContainerResize('sysPersonLeave','layout');
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
	              	  	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPersonLeaveRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
	              		setCellText(this,id,'hrPersonHis.name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrPersonRecord(\''+snapId+'\');">'+ret[i]["hrPersonHis.name"]+'</a>');
	              	}
	            }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
           		var dataTest = {"id":"sysPersonLeave_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('sysPersonLeave_gridtable','com.huge.ihos.hr.changesInPersonnel.model.SysPersonLeave',initFlag);
       		} 
    	});
    	jQuery(sysPersonLeaveGrid).jqGrid('bindKeys');
	    hrPersonTreeInPersonLeave();
	    
	  //实例化ToolButtonBar
	     var sysPersonLeave_menuButtonArrJson = "${menuButtonArrJson}";
	     var sysPersonLeave_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(sysPersonLeave_menuButtonArrJson,false)));
	     var sysPersonLeave_toolButtonBar = new ToolButtonBar({el:$('#sysPersonLeave_buttonBar'),collection:sysPersonLeave_toolButtonCollection,attributes:{
	      tableId : 'sysPersonLeave_gridtable',
	      baseName : 'sysPersonLeave',
	      width : 600,
	      height : 600,
	      base_URL : null,
	      optId : null,
	      fatherGrid : null,
	      extraParam : null,
	      selectNone : "请选择记录。",
	      selectMore : "只能选择一条记录。",
	      addTitle : '<s:text name="sysPersonLeaveNew.title"/>',
	      editTitle : null
	     }}).render();
	     //实例化结束
	     var sysPersonLeave_function = new scriptFunction();
	     sysPersonLeave_function.optBeforeCall = function(e,$this,param){
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
	      sysPersonLeave_toolButtonBar.addCallBody('1002020401',function(e,$this,param){
	    	  var zTree = $.fn.zTree.getZTreeObj("sysPersonLeaveTypeTreeLeft"); 
		    	var nodes = zTree.getSelectedNodes(); 
			    if(nodes.length!=1 || nodes[0].subSysTem!='PERSON'){
			    	alertMsg.error("请选择人员。");
		      		return;
			    }
			    if(nodes[0].actionUrl == '1'){
			    	alertMsg.error("已停用人员不能离职。");
		      		return;
			    }
				var url = "editSysPersonLeave?navTabId=sysPersonLeave_gridtable&personId="+nodes[0].id;
				var winTitle='<s:text name="sysPersonLeaveNew.title"/>';
				$.pdialog.open(url,'addSysPersonLeave',winTitle, {mask:true,width : 666,height : 352,maxable:false,resizable:false});
	      },{});
	      sysPersonLeave_toolButtonBar.addBeforeCall('1002020401',function(e,$this,param){
				return sysPersonLeave_function.optBeforeCall(e,$this,param);
	    	},{checkPeriod:"checkPeriod"});
	    //删除
	      sysPersonLeave_toolButtonBar.addCallBody('1002020402',function(e,$this,param){
	    	  var url = "${ctx}/sysPersonLeaveGridEdit?oper=del"
	  			var sid = jQuery("#sysPersonLeave_gridtable").jqGrid('getGridParam','selarrrow');
	  			if (sid == null || sid.length == 0) {
	  				alertMsg.error("请选择记录。");
	  				return;
	  			} else {
	  				for(var i = 0;i < sid.length; i++){
	  					var rowId = sid[i];
	  					var row = jQuery("#sysPersonLeave_gridtable").jqGrid('getRowData',rowId);
	  					if(row['state']!='0'){
	  						alertMsg.error("只能删除处于新建状态的记录!");
	  						return;
	  					}
	  				}
	  				url = url+"&id="+sid+"&navTabId=sysPersonLeave_gridtable";
	  				alertMsg.confirm("确认删除？", {
	  					okCall : function() {
	  						$.post(url,function(data) {
	  							formCallBack(data);
	  						});
	  					}
	  				});
	  			}
	      },{});
	      sysPersonLeave_toolButtonBar.addBeforeCall('1002020402',function(e,$this,param){
				return sysPersonLeave_function.optBeforeCall(e,$this,param);
	    	},{checkPeriod:"checkPeriod"});
	    //修改
	      sysPersonLeave_toolButtonBar.addCallBody('1002020403',function(e,$this,param){
	    	 	var sid = jQuery("#sysPersonLeave_gridtable").jqGrid('getGridParam','selarrrow');
		       	if(sid==null || sid.length !=1){
		       		alertMsg.error("请选择一条记录。");
					return;
				}
		       	var row = jQuery("#sysPersonLeave_gridtable").jqGrid('getRowData',sid[0]);
		       	if(row['state']!='0'){
					alertMsg.error("只能修改处于新建状态的记录!");
					return;
				}
				var winTitle='<s:text name="sysPersonLeaveEdit.title"/>';
				var url = "editSysPersonLeave?id="+sid+"&navTabId=sysPersonLeave_gridtable";
				$.pdialog.open(url,'editSysPersonLeave',winTitle, {mask:true,width : 666,height : 352,maxable:false,resizable:false});
	      },{});
	      sysPersonLeave_toolButtonBar.addBeforeCall('1002020403',function(e,$this,param){
				return sysPersonLeave_function.optBeforeCall(e,$this,param);
	    	},{checkPeriod:"checkPeriod"});
	    //审核
	      sysPersonLeave_toolButtonBar.addCallBody('1002020404',function(e,$this,param){
	    	  var url = "${ctx}/sysPersonLeaveGridEdit?oper=check"
	  			var sid = jQuery("#sysPersonLeave_gridtable").jqGrid('getGridParam','selarrrow');
	  			if (sid == null || sid.length == 0) {
	  				alertMsg.error("请选择记录。");
	  				return;
	  			} else {
	  				for(var i = 0;i < sid.length; i++){
	  					var rowId = sid[i];
	  					var row = jQuery("#sysPersonLeave_gridtable").jqGrid('getRowData',rowId);
	  					if(row['state']!='0'){
	  						alertMsg.error("只能审核处于新建状态的记录!");
	  						return;
	  					}
	  				}
	  				url = url+"&id="+sid+"&navTabId=sysPersonLeave_gridtable";
	  				alertMsg.confirm("确认审核？", {
	  					okCall : function() {
	  						$.post(url,function(data) {
	  							formCallBack(data);
	  						});
	  					}
	  				});
	  			}
	      },{});
	      sysPersonLeave_toolButtonBar.addBeforeCall('1002020404',function(e,$this,param){
				return sysPersonLeave_function.optBeforeCall(e,$this,param);
	    	},{checkPeriod:"checkPeriod"});
	    //销审
	      sysPersonLeave_toolButtonBar.addCallBody('1002020405',function(e,$this,param){
	    	  var url = "${ctx}/sysPersonLeaveGridEdit?oper=cancelCheck"
	  			var sid = jQuery("#sysPersonLeave_gridtable").jqGrid('getGridParam','selarrrow');
	  			if(sid==null|| sid.length == 0){       	
	  				alertMsg.error("请选择记录。");
	  				return;
	  			} else {
	  				for(var i = 0;i < sid.length; i++){
	  					var rowId = sid[i];
	  					var row = jQuery("#sysPersonLeave_gridtable").jqGrid('getRowData',rowId);
	  					
	  					if(row['state']!='1'){
	  						alertMsg.error("只有已审核的记录才能够被销审!");
	  						return;
	  					}
	  				}
	  				url = url+"&id="+sid+"&navTabId=sysPersonLeave_gridtable";
	  				alertMsg.confirm("确认销审？", {
	  					okCall : function() {
	  						$.post(url,function(data) {
	  							formCallBack(data);
	  						});
	  					}
	  				});
	  			}
	      },{});
	      sysPersonLeave_toolButtonBar.addBeforeCall('1002020405',function(e,$this,param){
				return sysPersonLeave_function.optBeforeCall(e,$this,param);
	    	},{checkPeriod:"checkPeriod"});
	    //执行
	      sysPersonLeave_toolButtonBar.addCallBody('1002020406',function(e,$this,param){
	    	  var url = "${ctx}/sysPersonLeaveGridEdit?oper=done"
	  	        var sid = jQuery("#sysPersonLeave_gridtable").jqGrid('getGridParam','selarrrow');
	  	        if(sid==null|| sid.length == 0){       	
	  	   			alertMsg.error("请选择记录。");
	  	   			return;
	  	   		} else {
	  				for(var i = 0;i < sid.length; i++){
	  					var rowId = sid[i];
	  					var row = jQuery("#sysPersonLeave_gridtable").jqGrid('getRowData',rowId);
	  					
	  					if(row['state']!='1'){
	  						alertMsg.error("只能执行处于已审核状态的记录!");
	  						return;
	  					}
	  				}
	  				url = url+"&id="+sid+"&navTabId=sysPersonLeave_gridtable";
	  				var pactUrl="${ctx}/sysPersonLeaveGridEdit?oper=donePact";
	  				pactUrl = pactUrl+"&id="+sid+"&navTabId=sysPersonLeave_gridtable";
	  				alertMsg.confirm("确认执行？", {
	  					okCall : function() {
	  						$.post(url,function(data) {
	  							if(data.statusCode==200){
	  								formCallBack(data);
	  								for(var i = 0;i < sid.length; i++){
			    						var rowId = sid[i];
			    						var row = jQuery("#sysPersonLeave_gridtable").jqGrid('getRowData',rowId);
			    						var personId = row['hrPersonHis.hrPersonPk.personId'];
			    						dealHrTreeNode('sysPersonLeaveTypeTreeLeft',{id:personId},'disable','person');
			    					}
	  								if(data.pactExist){
	  									alertMsg.confirm("确认解除合同？", {
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
	      sysPersonLeave_toolButtonBar.addBeforeCall('1002020406',function(e,$this,param){
				return sysPersonLeave_function.optBeforeCall(e,$this,param);
	    	},{checkPeriod:"checkPeriod"});
	    //设置表格列
	      var sysPersonLeave_setColShowButton = {id:'1002020407',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
	        callBody:function(){
	         setColShow('sysPersonLeave_gridtable','com.huge.ihos.hr.changesInPersonnel.model.SysPersonLeave');
	        }};
	      sysPersonLeave_toolButtonBar.addButton(sysPersonLeave_setColShowButton);//实例化ToolButtonBar
  	});
	function viewPersonLeaveRecord(id){
		var url = "editSysPersonLeave?oper=view&id="+id;
		$.pdialog.open(url,'viewSysPersonLeave','人员离职信息', {mask:true,width : 666,height : 352,maxable:false,resizable:false});
	}
	function hrPersonTreeInPersonLeave() {
		var url = "makeHrPersonTree?";
		$.get(url, {"_" : $.now()}, function(data) {
			var hrPersonTreeData = data.hrPersonTreeNodes;
			var hrPersonTree = $.fn.zTree.init($("#sysPersonLeaveTypeTreeLeft"),ztreesetting_hrPersonTreeInPersonLeave, hrPersonTreeData);
			var nodes = hrPersonTree.getNodes();
			hrPersonTree.expandNode(nodes[0], true, false, true);
			hrPersonTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'sysPersonLeaveTypeTreeLeft',
		         showCode:jQuery('#sysPersonLeave_showCode')[0],
		         disabledDept:jQuery("#sysPersonLeave_showDisabledDept")[0],
		         disabledPerson: jQuery("#sysPersonLeave_showDisabledPerson")[0],
		         showCount:jQuery('#sysPostMove_showPersonCount')[0] });
		});
		jQuery("#sysPersonLeave_expandTree").text("展开");
	}
	
	var ztreesetting_hrPersonTreeInPersonLeave = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false,
			fontCss : setDisabledDeptFontCss
		},
		callback : {
			beforeDrag:function(){return false},
			onClick : function(event, treeId, treeNode, clickFlag){
				var urlString = "sysPersonLeaveGridList?1=1";
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
			    var showDisabledDept = jQuery("#sysPersonLeave_showDisabledDept").attr("checked");
			    if(showDisabledDept){
			    	urlString += "&showDisabledDept=true"
			    }
			    var showDisabledPerson = jQuery("#sysPersonLeave_showDisabledPerson").attr("checked");
			    if(showDisabledPerson){
			    	urlString += "&showDisabledPerson=true"
			    }
				urlString=encodeURI(urlString);
				jQuery("#sysPersonLeave_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
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
	function showDisabledDeptInPersonLeave(obj){
		var urlString = jQuery("#sysPersonLeave_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledDept=true";
		}
		/* var urlString = "sysPersonLeaveGridList?1=1";
		if(obj.checked){
			urlString += "&showDisabledDept=true";
		} */
		var showDisabledPerson = jQuery("#sysPersonLeave_showDisabledPerson").attr("checked");
	    if(showDisabledPerson){
	    	urlString += "&showDisabledPerson=true"
	    }
		//hrPersonTreeInPersonLeave();
		toggleDisabledOrCount({treeId:'sysPersonLeaveTypeTreeLeft',
		         showCode:jQuery('#sysPersonLeave_showCode')[0],
		         disabledDept:jQuery("#sysPersonLeave_showDisabledDept")[0],
		         disabledPerson: jQuery("#sysPersonLeave_showDisabledPerson")[0],
		         showCount:jQuery('#sysPostMove_showPersonCount')[0] });
		urlString=encodeURI(urlString);
		jQuery("#sysPersonLeave_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	function showDisabledPersonInPersonLeave(obj){
		var urlString = jQuery("#sysPersonLeave_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledPerson=true"
		}
		var showDisabledDept = jQuery("#sysPersonLeave_showDisabledDept").attr("checked");
	    if(showDisabledDept){
	    	urlString += "&showDisabledDept=true"
	    }
	    toggleDisabledOrCount({treeId:'sysPersonLeaveTypeTreeLeft',
	         showCode:jQuery('#sysPersonLeave_showCode')[0],
	         disabledDept:jQuery("#sysPersonLeave_showDisabledDept")[0],
	         disabledPerson: jQuery("#sysPersonLeave_showDisabledPerson")[0],
	         showCount:jQuery('#sysPostMove_showPersonCount')[0] });
		urlString=encodeURI(urlString);
		jQuery("#sysPersonLeave_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}

</script>

<div class="page">
	<div class="pageHeader" id="sysPersonLeave_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="sysPersonLeave_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name="sysPersonLeave.code"/>:
						<input type="text"  id="search_sysPersonLeave_code" name="filter_LIKES_code"  style="width:70px"/>
					</label>
					<label class="queryarea-label">
						<s:text name="sysPersonLeave.name"/>:
						<input type="text"  id="search_sysPersonLeave_name" name="filter_LIKES_name"  style="width:70px"/>
					</label>
					<label class="queryarea-label">
						<s:text name="person.name"/>:
						<input type="text" name="filter_LIKES_hrPersonHis.name" style="width:70px"/>
					</label>
					<label class="queryarea-label">
       					<s:text name='sysPersonLeave.type'/>:
      					<s:select list="leaveTypeList" listKey="value" name="filter_EQS_type" headerKey="" headerValue="--" 
          				   listValue="content" emptyOption="false" theme="simple" style="font-size:9pt;"></s:select>
      				</label>
					<label class="queryarea-label">
						<s:text name="sysPersonLeave.reason"/>:
						<input type="text"  id="search_sysPersonLeave_reason" name="filter_LIKES_reason" style="width:80px"/>
					</label>
                    <label class="queryarea-label">
						<s:select id="personLeave_searchTime"   list="#{'0':'执行日期','1':'申请日期','2':'审核日期'}" style="width:100px;font-size:9pt;" ></s:select>
						<s:text name=" 从"/>:
						<input type="text" id="personLeavebeginDate"  name="hrPerson.beginDate" onclick="changeSysTimeType(personLeave,'doneDate','makeDate','checkDate')" onblur="checkQueryDate('personLeavebeginDate','personLeaveendDate',0,'personLeave_beginDate','personLeave_endDate')" style="width:65px"/>
					</label><label class="queryarea-label">
						<s:text name="至"/>:
						<input type="text" id="personLeaveendDate" name="hrPerson.endDate" onclick="changeSysTimeType(personLeave,'doneDate','makeDate','checkDate')"  onblur="checkQueryDate('personLeavebeginDate','personLeaveendDate',1,'personLeave_beginDate','personLeave_endDate')" style="width:65px"/>
						<input type="hidden" id="personLeave_beginDate" name="hrPerson.endDate"  />
						<input type="hidden" id="personLeave_endDate" name="hrPerson.endDate"  />
    				 </label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='sysPersonLeave.leaveDate'/>:
						<input type="text"	id="sysPersonLeave_search_leaveDate_from" name="filter_GED_leaveDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'sysPersonLeave_search_leaveDate_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="sysPersonLeave_search_leaveDate_to" name="filter_LED_leaveDate" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'sysPersonLeave_search_leaveDate_from\')}'})">
					</label> --%>
					<label class="queryarea-label">
						<s:text name='sysPersonLeave.state'/>:
						<s:select name="filter_EQS_state"   list="#{'':'--','0':'新建','1':'已审核','2':'已执行'}" style="width:60px;font-size:9pt;" ></s:select>
					</label>
					<label class="queryarea-label">
						<s:text name="sysPersonLeave.remark"/>:
						<input type="text" name="filter_LIKES_remark" style="width:80px"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('sysPersonLeave_search_form','sysPersonLeave_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('sysPersonLeave_search_form','sysPersonLeave_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div></li> -->
				
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="sysPersonLeave_buttonBar">
<!-- 			<ul class="toolBar"> -->
<!-- 				<li> -->
<%-- 					<a id="sysPersonLeave_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPersonLeave_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPersonLeave_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPersonLeave_gridtable_check" class="checkbutton"  href="javaScript:"><span><s:text name="button.check" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPersonLeave_gridtable_cancelCheck" class="delallbutton"  href="javaScript:"><span><s:text name="button.cancelCheck" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="sysPersonLeave_gridtable_done" class="confirmbutton"  href="javaScript:"><span><s:text name="button.done" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a class="settlebutton"  href="javaScript:setColShow('sysPersonLeave_gridtable','com.huge.ihos.hr.changesInPersonnel.model.SysPersonLeave')"><span><s:text name="button.setColShow" /></span></a> --%>
<!-- 				</li> -->
<!-- 			</ul> -->
		</div>
		
		<div id="sysPersonLeave_container">
			<div id="sysPersonLeave_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
		  <div class="treeTopCheckBox">
          <span>
          		 显示编码
           <input id="sysPostMove_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'sysPersonLeaveTypeTreeLeft',showCode:this,disabledDept:jQuery('#sysPersonLeave_showDisabledDept')[0],disabledPerson:jQuery('#sysPersonLeave_showDisabledPerson')[0],showCount:jQuery('#sysPostMove_showPersonCount')[0]})"/>
         </span>
         <span>
				显示人员数
				<input id="sysPostMove_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'sysPersonLeaveTypeTreeLeft',showCode:jQuery('#sysPostMove_showCode')[0],disabledDept:jQuery('#sysPersonLeave_showDisabledDept')[0],disabledPerson:jQuery('#sysPersonLeave_showDisabledPerson')[0],showCount:this})"/>
			</span>
         <label id="sysPersonLeave_expandTree" onclick="toggleExpandHrTree(this,'sysPersonLeaveTypeTreeLeft')">展开</label>
        </div>
       <div class="treeTopCheckBox">
       <span>
          		 显示停用部门
          <input id="sysPersonLeave_showDisabledDept" type="checkbox" onclick="showDisabledDeptInPersonLeave(this)"/>
         </span>
          <span>
          		 显示停用人员
          <input id="sysPersonLeave_showDisabledPerson" type="checkbox" onclick="showDisabledPersonInPersonLeave(this)"/>
          </span>
        </div>
        <div class="treeTopCheckBox">
         <span>
           		快速查询：
          <input type="text" onKeyUp="searchTree('sysPersonLeaveTypeTreeLeft',this)"/>
         </span>
   		</div>
    			<div id="sysPersonLeaveTypeTreeLeft" class="ztree"></div>
  		 	</div>
			<div id="sysPersonLeave_layout-center" class="pane ui-layout-center">
				<div id="sysPersonLeave_gridtable_div" class="grid-wrapdiv" 
					style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="sysPersonLeave_gridtable_navTabId" value="${sessionScope.navTabId}">
					<label style="display: none" id="sysPersonLeave_gridtable_addTile">
						<s:text name="sysPersonLeaveNew.title"/>
					</label> 
					<label style="display: none"
						id="sysPersonLeave_gridtable_editTile">
						<s:text name="sysPersonLeaveEdit.title"/>
					</label>
					<label style="display: none"
						id="sysPersonLeave_gridtable_selectNone">
						<s:text name='list.selectNone'/>
					</label>
					<label style="display: none"
						id="sysPersonLeave_gridtable_selectMore">
						<s:text name='list.selectMore'/>
					</label>
					<div id="load_sysPersonLeave_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 					<table id="sysPersonLeave_gridtable"></table>
				</div>
				<div class="panelBar" id="sysPersonLeave_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="sysPersonLeave_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="sysPersonLeave_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
			
					<div id="sysPersonLeave_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>