
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var showIds = null;
	var pactGridIdString="#pact_gridtable";
	jQuery(document).ready(function() { 
		jQuery("#pact_pageHeader").find("select").css("font-size","12px");
		/*------------------------------set layout-----------------------------------------*/
		var pactFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('pact_container','pact_gridtable',pactFullSize);
		/*------------------------------load leftTree-----------------------------------------*/
		hrPersonTreeInPact();
		/*------------------------------load rightGrid-----------------------------------------*/
		var initFlag = 0;
		var pactGrid = jQuery(pactGridIdString);
   	 	pactGrid.jqGrid({
	    	url : "pactGridList?filter_INI_signState=0,3,4,5",
	    	editurl:"pactGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="pact.id" />',hidden:true,key:true},	
				{name:'code',index:'code',width : '120',align:'left',label : '<s:text name="pact.code" />',hidden:false,highsearch:true},	
				{name:'hrPerson.name',index:'hrPerson.name',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:false,highsearch:true},	
				{name:'hrPerson.personId',index:'hrPerson.personId',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'personSnapCode',index:'personSnapCode',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'personCurrentSnapCode',index:'personCurrentSnapCode',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},
				{name:'dept.hrOrg.orgname',index:'dept.hrOrg.orgname',width : '150',align:'left',label : '<s:text name="pact.orgCode" />',hidden:false,highsearch:true},	
				{name:'dept.name',index:'dept.name',width : '100',align:'left',label : '<s:text name="pact.dept" />',hidden:false,highsearch:true},	
				{name:'post.name',index:'post.name',width : '100',align:'left',label : '<s:text name="pact.post" />',hidden:false,highsearch:true},	
			 	{name:'workContent',index:'workContent',width : '150',align:'left',label : '<s:text name="pact.workContent" />',hidden:false,highsearch:true},		
				{name:'signYear',index:'signYear',width : '60',align:'right',label : '<s:text name="pact.signYear" />',hidden:false,highsearch:true,formatter:'integer'},
				{name:'compSignDate',index:'compSignDate',width : '100',align:'center',label : '<s:text name="pact.compSignDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'beginDate',index:'beginDate',width : '100',align:'center',label : '<s:text name="pact.beginDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
				{name:'endDate',index:'endDate',width : '100',align:'center',label : '<s:text name="pact.endDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
				{name:'probationMonth',index:'probationMonth',width : '80',align:'right',label : '<s:text name="pact.probationMonth" />',hidden:false,highsearch:true,formatter:'integer'},
				{name:'probationBeginDate',index:'probationBeginDate',width : '100',align:'center',label : '<s:text name="pact.probationBeginDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'probationEndDate',index:'probationEndDate',width : '100',align:'center',label : '<s:text name="pact.probationEndDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'breakDate',index:'breakDate',width : '100',align:'center',label : '<s:text name="pact.breakDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'breakReason',index:'breakReason',width : '100',align:'left',label : '<s:text name="pact.breakReason" />',hidden:false,highsearch:true},				
				{name:'relieveDate',index:'relieveDate',width : '100',align:'center',label : '<s:text name="pact.relieveDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'relieveReason',index:'relieveReason',width : '100',align:'left',label : '<s:text name="pact.relieveReason" />',hidden:false,highsearch:true},				
				{name:'path',index:'path',align:'center',label : '<s:text name="pact.path" />',hidden:true},				
				{name:'remark',index:'remark',width : '100',align:'left',label : '<s:text name="pact.remark" />',hidden:false,highsearch:true},	
				{name:'compSignPeople',index:'compSignPeople',align:'left',width : '80',label : '<s:text name="pact.compSignPeople" />',hidden:false,highsearch:true},				
				{name:'pactState',index:'pactState',width : '80',align:'center',label : '<s:text name="pact.pactState" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '1:初签;2:续签'}},				
				{name:'signState',index:'signState',width : '80',align:'center',label : '<s:text name="pact.signState" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '0:草拟;3:有效;4:终止;5:解除'}}				
	
	        ],
	        jsonReader : {
				root : "pacts", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'code',
	        viewrecords: true,
	        sortorder: 'desc',
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
				 gridContainerResize('pact','layout');
		 		var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  	id=rowIds[i];
	              		if(ret[i]['signState']=="0"){
	              		  	setCellText(this,id,'signState','<span>草拟</span>');
	              	  	}else if(ret[i]['signState']=="3"){
	              		  	setCellText(this,id,'signState','<span style="color:green">有效</span>');
	              	  	}else if(ret[i]['signState']=="4"){
	              		  	setCellText(this,id,'signState','<span style="color:red" >终止</span>');
	              	  	}else if(ret[i]['signState']=="5"){
	              		  	setCellText(this,id,'signState','<span style="color:blue" >解除</span>');
	              	  	}
		   	          	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPactRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
		   	          	var snapId = ret[i]['hrPerson.personId'] +'_'+ret[i]['personSnapCode'];
		   	          	if(ret[i]['personCurrentSnapCode']){
		   	          		snapId = ret[i]['hrPerson.personId'] +'_'+ret[i]['personCurrentSnapCode'];
		   	          	}
		   	          	setCellText(this,id,'hrPerson.name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrPersonRecord(\''+snapId+'\');">'+ret[i]["hrPerson.name"]+'</a>');
	              }
	            }else{
	            	var tw = jQuery("#pact_gridtable").outerWidth();
					jQuery("#pact_gridtable").parent().width(tw);
					jQuery("#pact_gridtable").parent().height(1);
	            }
           		var dataTest = {"id":"pact_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('pact_gridtable','com.huge.ihos.hr.pact.model.Pact',initFlag);
       		} 
    	});
    	jQuery(pactGrid).jqGrid('bindKeys');
    	
    	jQuery("#pact_search_form_orgCode").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon  FROM v_hr_org_current WHERE disabled = 0 order by orgCode",
			exceptnullparent : false,
			lazy : false,
			minWidth:'200px',
			selectParent:true,
			callback : {
				afterClick : function() {
					jQuery("#pact_search_form_dept").val("");
					jQuery("#pact_search_form_dept_id").val("");
					jQuery("#pact_search_form_post").val("");
					jQuery("#pact_search_form_post_id").val("");
					jQuery("#pact_search_form_dept").treeselect({
						dataType : "sql",
						optType : "single",
						sql : "SELECT  deptId id,name,parentDept_id parent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon FROM v_hr_department_current where disabled = 0 and orgCode='"+jQuery("#pact_search_form_orgCode_id").val()+"' order by deptCode",
						exceptnullparent : false,
						selectParent:true,
						lazy : false,
						minWidth:'200px',
						callback:{
							afterClick:function(){
								jQuery("#pact_search_form_post").val("");
								jQuery("#pact_search_form_post_id").val("");
								jQuery("#pact_search_form_post").treeselect({
									dataType : "sql",
									optType : "single",
									sql : "SELECT  id,name FROM hr_post where disabled = 0 and deptId='"+jQuery("#pact_search_form_dept_id").val()+"'",
									lazy : false
								});
							}
						}
					});
				}
			}
		});
    	
    	//实例化ToolButtonBar
    	var pact_menuButtonArrJson = "${menuButtonArrJson}";
    	var pact_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(pact_menuButtonArrJson,false)));
    	var pact_toolButtonBar = new ToolButtonBar({el:$('#pact_buttonBar'),collection:pact_toolButtonCollection,attributes:{
    		tableId : 'pact_gridtable',
    		baseName : 'pact',
    		width : 600,
    		height : 600,
    		base_URL : null,
    		optId : null,
    		fatherGrid : null,
    		extraParam : null,
    		selectNone : "请选择记录。",
    		selectMore : "只能选择一条记录。",
    		addTitle : '<s:text name="pactNew.title"/>',
    		editTitle : null
    	}}).render();
    	//实例化结束
    	//添加buttonUtil方法
    	var pact_function = new scriptFunction();
		pact_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.checkPeriod){
				if('${yearStarted}'!='true'){
					alertMsg.error("本年度人力资源系统未启用。");
	    			return pass;
				}
			}
			if(param.selectRecord){
				var sid = jQuery("#pact_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
		        	alertMsg.error("请选择记录。");
					return pass;
				}
		        if(param.singleSelect){
		        	if(sid.length != 1){
			        	alertMsg.error("只能选择一条记录。");
						return pass;
					}
		        }
		        if(param.opt){
		        	for(var i = 0;i < sid.length; i++){
						var rowId = sid[i];
						var row = jQuery("#pact_gridtable").jqGrid('getRowData',rowId);
						if(row['signState']!='3'){
							alertMsg.error("只能"+param.opt+"处于  ["+param.status+"] 状态的记录。");
							return pass;
						}
			        }
		        }
			}
	        return true;
		};
		
		//为button添加方法
		pact_toolButtonBar.addCallBody('10030101',function(e,$this,param){
			var zTree = $.fn.zTree.getZTreeObj("hrPersonTreeInPact");  
    	    var nodes = zTree.getSelectedNodes(); 
    	    if(nodes.length!=1 || nodes[0].subSysTem!='PERSON'){
    	    	alertMsg.error("请选择人员。");
          		return;
    	    }
    	    if(nodes[0].actionUrl == '1'){
    	    	alertMsg.error("已停用人员不能签订合同。");
          		return;
    	    }
    	    /*
    	    	判断该人员能否添加合同
    	    */
    	    $.ajax({
			    url: "checkAddPactForPerson?personId="+nodes[0].id,
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			    },
			    success: function(data){
			        if(data!=null){
			        	alertMsg.error(data.message);
						return;
			        }else{
			        	var url = "editPact?navTabId=pact_gridtable&addFrom=noCheck&personId="+nodes[0].id;
			     		var winTitle='<s:text name="pactNew.title"/>';
			     		$.pdialog.open(url,'addPact',winTitle, {mask:true,width : 620,height : 410,maxable:false,resizable:false});
					}
			    }
			});
    	   
		},{});
		pact_toolButtonBar.addBeforeCall('10030101',function(e,$this,param){
			return pact_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
		
    	pact_toolButtonBar.addCallBody('10030102',function(e,$this,param){
    		var sid = jQuery("#pact_gridtable").jqGrid('getGridParam','selarrrow');
    		for(var i = 0;i < sid.length; i++){
   				var rowId = sid[i];
   				var row = jQuery("#pact_gridtable").jqGrid('getRowData',rowId);
   				if(row['signState']!='0'){
   					alertMsg.error("只能修改处于  [草拟] 状态的合同。");
   					return;
   				}
   	        }
			var winTitle='<s:text name="pactEdit.title"/>';
			var url = "editPact?id="+sid+"&navTabId=pact_gridtable";
			$.pdialog.open(url,'editPact',winTitle, {mask:true,width : 620,height : 410,maxable:false,resizable:false});
    	},{});
    	pact_toolButtonBar.addBeforeCall('10030102',function(e,$this,param){
    		return pact_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",singleSelect:"单选",checkPeriod:"checkPeriod"});
    	
    	pact_toolButtonBar.addFunctionDel('10030103');
    	pact_toolButtonBar.addBeforeCall('10030103',function(e,$this,param){
			return pact_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
    	
    	pact_toolButtonBar.addCallBody('10030104',function(e,$this,param){
    		var sid = jQuery("#pact_gridtable").jqGrid('getGridParam','selarrrow');
   			for(var i = 0;i < sid.length; i++){
   				var rowId = sid[i];
   				var row = jQuery("#pact_gridtable").jqGrid('getRowData',rowId);
   				if(row['signState']!='0'){
   					alertMsg.error("只能生效处于  [草拟] 状态的合同。");
   					return;
   				}
   	        }
   			var url = "${ctx}/pactGridEdit?oper=effect"
   			url = url+"&id="+sid+"&navTabId=pact_gridtable";
   			alertMsg.confirm("确认生效？", {
   				okCall : function() {
   					$.post(url,function(data) {
   						formCallBack(data);
   					});
   				}
   			});
        },{});
    	pact_toolButtonBar.addBeforeCall('10030104',function(e,$this,param){
    		return pact_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
    	
    	//合同办理
    	pact_toolButtonBar.addCallBody('10030105',function(e,$this,param){
    		var buttonId = "button_10030105";
       	    var containerId = 'pact_page';
       	    addSelectButton(buttonId,containerId,[ {id:'button_pactNew',name:'签订合同',className:'edit',callBody:function(e,$$this){
       	    	var zTree = $.fn.zTree.getZTreeObj("hrPersonTreeInPact");  
    		    var nodes = zTree.getSelectedNodes(); 
    		    if(nodes.length!=1 || nodes[0].subSysTem!='PERSON'){
    		    	alertMsg.error("请选择人员。");
    	      		return;
    		    }
    		    if(nodes[0].actionUrl == '1'){
    		    	alertMsg.error("已停用人员不能签订合同。");
    	      		return;
    		    }
    		    $.ajax({
    			    url: "checkAddPactForPerson?personId="+nodes[0].id,
    			    type: 'post',
    			    dataType: 'json',
    			    aysnc:false,
    			    error: function(data){
    			    },
    			    success: function(data){
    			        if(data!=null){
    			        	alertMsg.error(data.message);
    						return;
    			        }else{
    			        	var url = "editPact?navTabId=pactNew_gridtable&addFrom=noCheck&personId="+nodes[0].id;
    			    		var winTitle='<s:text name="pactNew.title"/>';
    			    		$.pdialog.open(url,'addPactNew',winTitle, {mask:true,width : 620,height : 410,maxable:false,resizable:false});
    					}
    			    }
    			});   		    
       	    }},{id:'button_pactRenew',name:'续签处理',className:'edit',callBody:function(e,$$this){
       	 		var sid = jQuery("#pact_gridtable").jqGrid('getGridParam','selarrrow');
       	 	 	if(sid==null || sid.length ==0){
 	        		alertMsg.error("请选择记录。");
 					return;
 				}
       	 		for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#pact_gridtable").jqGrid('getRowData',rowId);
					if(row['signState']!='3'){
						alertMsg.error("只能续签处于  [有效] 状态的记录。");
						return;
					}
	        	}
           	 	var winTitle='<s:text name="pactRenew.title"/>';
    			var url = "editPactRenew?navTabId=pact_gridtable&random=${random}&addFrom=noCheck&pactIds="+sid;
    		$.pdialog.open(url,'addPactRenew',winTitle, {mask:true,width : 600,height : 300,maxable:false,resizable:false});
       	    }},{id:'button_pactBreakNew',name:'终止合同',className:'edit',callBody:function(e,$$this){
       	    	var sid = jQuery("#pact_gridtable").jqGrid('getGridParam','selarrrow');
       	    	if(sid==null || sid.length ==0){
 	        		alertMsg.error("请选择记录。");
 					return;
 				}
       	 		for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#pact_gridtable").jqGrid('getRowData',rowId);
					if(row['signState']!='3'){
						alertMsg.error("只能终止处于  [有效] 状态的记录。");
						return;
					}
	        	}
    			var winTitle='<s:text name="pactBreakNew.title"/>';
    			var url = "editPactBreak?navTabId=pact_gridtable&random=${random}&addFrom=noCheck&pactIds="+sid;
    			$.pdialog.open(url,'addPactBreak',winTitle, {mask:true,width : 600,height : 300,maxable:false,resizable:false});
       	      }},{id:'button_pactRelieveNew',name:'解除合同',className:'edit',callBody:function(e,$$this){
       	    	var sid = jQuery("#pact_gridtable").jqGrid('getGridParam','selarrrow');
    	        if(sid==null || sid.length ==0){
    	        	alertMsg.error("请选择合同。");
    				return;
    			}
    	        for(var i = 0;i < sid.length; i++){
    				var rowId = sid[i];
    				var row = jQuery("#pact_gridtable").jqGrid('getRowData',rowId);
    				if(row['signState']=='0'){
    					alertMsg.error("合同尚未生效，不能解除。");
    					return;
    				}
    				if(row['signState']=='5'){
    					alertMsg.error("不能重复解除合同。");
    					return;
    				}
    	        }
    	        var winTitle='<s:text name="pactRelieveNew.title"/>';
    			var url = "editPactRelieve?navTabId=pact_gridtable&random=${random}&addFrom=noCheck&pactIds="+sid;
    			$.pdialog.open(url,'addPactRelieve',winTitle, {mask:true,width : 600,height : 300,maxable:false,resizable:false});
       	      }}]); 
    	     },{});
    	pact_toolButtonBar.addBeforeCall('10030105',function(e,$this,param){
    		return pact_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
    	
    	
//     	pact_toolButtonBar.addCallBody('10030105',function(e,$this,param){
//     		var sid = jQuery("#pact_gridtable").jqGrid('getGridParam','selarrrow');
//             var winTitle='<s:text name="pactRenew.title"/>';
//     		var url = "editPactRenew?navTabId=pact_gridtable&random=${random}&addFrom=noCheck&pactIds="+sid;
//     		$.pdialog.open(url,'addPactRenew',winTitle, {mask:true,width : 600,height : 300,maxable:false,resizable:false});
//     	},{});
//     	pact_toolButtonBar.addBeforeCall('10030105',function(e,$this,param){
//     		return pact_function.optBeforeCall(e,$this,param);
//     	},{opt:"续签",status:"有效"});
    	
//     	pact_toolButtonBar.addCallBody('10030106',function(e,$this,param){
//     		var sid = jQuery("#pact_gridtable").jqGrid('getGridParam','selarrrow');
// 			var winTitle='<s:text name="pactBreakNew.title"/>';
// 			var url = "editPactBreak?navTabId=pact_gridtable&random=${random}&addFrom=noCheck&pactIds="+sid;
// 			$.pdialog.open(url,'addPactBreak',winTitle, {mask:true,width : 600,height : 300,maxable:false,resizable:false});
//     	},{});
//     	pact_toolButtonBar.addBeforeCall('10030106',function(e,$this,param){
//     		return pact_function.optBeforeCall(e,$this,param);
//     	},{opt:"终止",status:"有效"});
    	
//     	pact_toolButtonBar.addCallBody('10030107',function(e,$this,param){
//     		var sid = jQuery("#pact_gridtable").jqGrid('getGridParam','selarrrow');
// 	        if(sid==null || sid.length ==0){
// 	        	alertMsg.error("请选择合同。");
// 				return;
// 			}
// 	        for(var i = 0;i < sid.length; i++){
// 				var rowId = sid[i];
// 				var row = jQuery("#pact_gridtable").jqGrid('getRowData',rowId);
// 				if(row['signState']=='0'){
// 					alertMsg.error("合同尚未生效，不能解除。");
// 					return;
// 				}
// 				if(row['signState']=='5'){
// 					alertMsg.error("不能重复解除合同。");
// 					return;
// 				}
// 	        }
// 	        var winTitle='<s:text name="pactRelieveNew.title"/>';
// 			var url = "editPactRelieve?navTabId=pact_gridtable&random=${random}&addFrom=noCheck&pactIds="+sid;
// 			$.pdialog.open(url,'addPactRelieve',winTitle, {mask:true,width : 600,height : 300,maxable:false,resizable:false});
//     	},{});
    	
    	pact_toolButtonBar.addCallBody('10030108',function(e,$this,param){
    		var buttonId = "button_10030108";
       	    var containerId = 'pact_page';
       	 	addSelectButton(buttonId,containerId,[
    	     	{id:'viewPactTemplet',name:'查看合同',className:'previewbutton',callBody:function(e,$$this){
	    	    	var sid = jQuery("#pact_gridtable").jqGrid('getGridParam','selarrrow');
	    	        if(sid==null || sid.length ==0){
	    	        	alertMsg.error("请选择记录。");
	    				return;
	    			}
    	        	if(sid.length != 1){
    		        	alertMsg.error("只能选择一条记录。");
    					return;
    				}
	    	    	var winTitle='<s:text name="查看合同"/>';
	   				var url = "viewPactTemplet?id="+sid;
	   				$.ajax({
	   				    url: url,
	   				    type: 'post',
	   				    dataType: 'json',
	   				    aysnc:false,
	   				    error: function(data){
	   				    },
	   				    success: function(data){
	   				    	if(data.statusCode!=200){
	   				    		formCallBack(data);
	   				    	}else{
	   				    		window.location.href="downLoadFile?delete=1&filePath="+data.downloadFilePath+"&fileName="+data.downloadFileName;
	   				    	}
	   				    }
	   				});  
    			}},
    			{id:'downloadPact',name:'导出合同',className:'exportbutton',callBody:function(e,$$this){
    				var sid = jQuery("#pact_gridtable").jqGrid('getGridParam','selarrrow');
    				if(sid==null || sid.length ==0){
	    	        	alertMsg.error("请选择记录。");
	    				return;
	    			}
    				var url = "downloadPact?id="+sid;
    				$.ajax({
    				    url: url,
    				    type: 'post',
    				    dataType: 'json',
    				    aysnc:false,
    				    error: function(data){
    				    },
    				    success: function(data){
    				    	if(data.statusCode==200){
		   				    	window.location.href="downLoadFile?delete=1&filePath="+data.downloadFilePath+"&fileName="+data.downloadFileName;
	   				    	}else{
	   				    		formCallBack(data);
	   				    	}
    				    }
    				});
    			}},
    			{id:'uploadPactTemplet',name:'上传模板',className:'importbutton',callBody:function(e,$$this){
	       	    	var winTitle='<s:text name="pact.uploadTemplet"/>';
	    			var url = "uploadPactTemplet";
	    			$.pdialog.open(url,'uploadPactTemplet',winTitle, {mask:true,width : 400,height : 230,maxable:false,resizable:false});
    	      	}}
    		]); 
    	},{});
    	pact_toolButtonBar.addBeforeCall('10030108',function(e,$this,param){
    		return pact_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
    	var pact_setColShowButton = {id:'10030188',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
    			callBody:function(){
    				setColShow('pact_gridtable','com.huge.ihos.hr.pact.model.Pact');
    			}};
    	pact_toolButtonBar.addButton(pact_setColShowButton);
    	
  	});
	
	function hrPersonTreeInPact() {
		var treeUrl = "makeHrPersonTree?1=1&showPersonType=true";
		//var showDisabledDept = jQuery("#pact_showDisabledDept").attr("checked");
		//var showDisabledPerson = jQuery("#pact_showDisabledPerson").attr("checked");
		/* if(showDisabledDept){
			treeUrl += "&showDisabledDept=show";
		} */
		/* if(showDisabledPerson){
			treeUrl += "&showDisabledPerson=show";	
		} */
		$.get(treeUrl, {"_" : $.now()}, function(data) {
			var hrPersonTreeData = data.hrPersonTreeNodes;
			var hrPersonTree = $.fn.zTree.init($("#hrPersonTreeInPact"),ztreesetting_hrPersonTreeInPact, hrPersonTreeData);
			var nodes = hrPersonTree.getNodes();
			hrPersonTree.expandNode(nodes[0], true, false, true);
			hrPersonTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'hrPersonTreeInPact',
		         showCode:jQuery('#pact_showCode')[0],
		         disabledDept:jQuery("#pact_showDisabledDept")[0],
		         disabledPerson: jQuery("#pact_showDisabledPerson")[0],
		         showCount:true });
		});
		jQuery("#pact_expandTree").text("展开");
	}
	
	var ztreesetting_hrPersonTreeInPact = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				fontCss : setDisabledDeptFontCss
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var urlString = "pactGridList?filter_INI_signState=0,3,4,5";
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
				    var showDisabledDept = jQuery("#pact_showDisabledDept").attr("checked");
					var showDisabledPerson = jQuery("#pact_showDisabledPerson").attr("checked");
					if(showDisabledDept){
						urlString += "&showDisabledDept=show";
					}
					if(showDisabledPerson){
						urlString += "&showDisabledPerson=show";	
					}
					urlString=encodeURI(urlString);
					jQuery("#pact_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
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
	
	function showDisabledDeptInPact(obj){
		//var urlString = "pactGridList?filter_INI_signState=0,3,4,5";
		var urlString = jQuery("#pact_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledDept=show";
		}
		var showDisabledPerson = jQuery("#pact_showDisabledPerson").attr("checked");
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=show";	
		}
		//hrPersonTreeInPact();
		urlString=encodeURI(urlString);
		jQuery("#pact_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		pactTreeReShow();
	}
	
	function showDisbaledPersonInPact(obj){
		//var urlString = "pactGridList?filter_INI_signState=0,3,4,5";
		var urlString = jQuery("#pact_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledPerson=show";
		}
		var showDisabledDept = jQuery("#pact_showDisabledDept").attr("checked");
		if(showDisabledDept){
			urlString += "&showDisabledDept=show";
		}
		//hrPersonTreeInPact();
		urlString=encodeURI(urlString);
		jQuery("#pact_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
		pactTreeReShow();
	}
	//树隐藏与显示
	function pactSearchFormReaload(){
		propertyFilterSearch('pact_search_form','pact_gridtable');
		pactTreeReShow();
	}
	function pactTreeReShow(){
		var postData = jQuery("#pact_gridtable").jqGrid('getGridParam',"postData");
		var urlString = 'pactGridList?filter_INI_signState=0,3,4,5';
		var isShowAll=true;
		jQuery.each(postData, function(key, val) {
			if(val){
			switch(key){
				case 'filter_LIKES_code':
					urlString+="&filter_LIKES_code="+val;
					isShowAll=false;
					break;
				case 'filter_LIKES_hrPerson.name':
					urlString+="&filter_LIKES_hrPerson.name="+val;
					isShowAll=false;
					break;
				case 'filter_EQS_dept.orgCode':
					urlString+="&filter_EQS_dept.orgCode="+val;
					isShowAll=false;
					break;
				case 'filter_EQS_dept.departmentId':
					urlString+="&filter_EQS_dept.departmentId="+val;
					isShowAll=false;
					break;
				case 'filter_EQS_post.id':
					urlString+="&filter_EQS_post.id="+val;
					isShowAll=false;
					break;
				case 'filter_GED_compSignDate':
					urlString+="&filter_GED_compSignDate="+val;
					isShowAll=false;
					break;
				case 'filter_LED_compSignDate':
					urlString+="&filter_LED_compSignDate="+val;
					isShowAll=false;
					break;
				case 'filter_EQI_pactState':
					urlString+="&filter_EQI_pactState="+val;
					isShowAll=false;
					break;
				case 'filter_EQI_signState':
					urlString+="&filter_EQI_signState="+val;
					isShowAll=false;
					break;
			}    
			}
	 　　});   
		var showDisabledDept = jQuery("#pact_showDisabledDept").attr("checked");
		if(showDisabledDept){
			urlString += "&showDisabledDept=show";
		}
		var showDisabledPerson = jQuery("#pact_showDisabledPerson").attr("checked");
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=show";	
		}
		urlString=encodeURI(urlString);
		var treeObj = $.fn.zTree.getZTreeObj("hrPersonTreeInPact");
		 if(!treeObj){
        	return; 
         }
		 if(isShowAll){
			 showIds = null; 
		 }else{
			 showIds = new Array();
			 jQuery.ajax({
			       url: urlString,
			       data: {},
			       type: 'post',
			       dataType: 'json',
			       async:false,
			       error: function(data){
			       },
			       success: function(data){
			        if(data.pactAll){
			        	jQuery.each(data.pactAll, function(i,val) { 
			        		showIds[i] = val.hrPerson.personId;
			         		});
			        }
			   }
			  });
		 }
		toggleDisabledOrCount({treeId:'hrPersonTreeInPact',
	         showCode:jQuery('#pact_showCode')[0],
	         disabledDept:jQuery("#pact_showDisabledDept")[0],
	         disabledPerson: jQuery("#pact_showDisabledPerson")[0],
	         showCount:true,
	         showIds:showIds});
	}
	//显示树节点(该节点及其下级节点)
	function showAllTreeNodesForPact(updateNode,treeObjId,showDisabledDept,showDisabledPerson){
		var treeObj = $.fn.zTree.getZTreeObj(treeObjId);
		if(!showDisabledPerson&&updateNode.subSysTem=="PERSON" && updateNode.actionUrl == '1'){
			treeObj.hideNode(updateNode);
		}else if(!showDisabledDept && updateNode.subSysTem=="DEPT" && updateNode.actionUrl == '1'){
			treeObj.hideNode(updateNode);
		}else{
			treeObj.showNode(updateNode);
		}
		if(updateNode.isParent){
			var childNodes=updateNode.children;
			if(childNodes){
				jQuery.each(childNodes, function() {
					showAllTreeNodesForPact(this,treeObjId,showDisabledDept,showDisabledPerson)
	    		 });
			}
		}
	}
	/*--------------------------------------search end---------------------------*/
</script>
<div class="page" id="pact_page">
	<div class="pageHeader" id="pact_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="pact_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.code'/>:
      					<input type="text" name="filter_LIKES_code"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.hrPerson'/>:
      					<input type="text" name="filter_LIKES_hrPerson.name"/>
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.orgCode'/>:
      					<input type="hidden" name="filter_EQS_dept.orgCode" id="pact_search_form_orgCode_id"/>
      					<input type="text" id="pact_search_form_orgCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.dept'/>:
      					<input type="hidden" name="filter_EQS_dept.departmentId" id="pact_search_form_dept_id"/>
      					<input type="text" id="pact_search_form_dept"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.post'/>:
      					<input type="hidden" name="filter_EQS_post.id" id="pact_search_form_post_id"/>
      					<input type="text" id="pact_search_form_post"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.compSignDate'/>:
      					<input type="text" id="pact_search_form_compSignDate_from" name="filter_GED_compSignDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'pact_search_form_compSignDate_to\')}'})">
						<s:text name='至'/>
					 	<input type="text" id="pact_search_form_compSignDate_to" name="filter_LED_compSignDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'pact_search_form_compSignDate_from\')}'})">
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.pactState'/>:
						<s:select name="filter_EQI_pactState" list="#{'':'--','1':'初签','2':'续签'}" style="width:70px" ></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.signState'/>:
						<s:select name="filter_EQI_signState" list="#{'':'--','0':'草拟','3':'有效','4':'终止','5':'解除'}" style="width:70px" ></s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="pactSearchFormReaload()"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>		
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="pactSearchFormReaload()"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div id="pact_buttonBar" class="panelBar">
			<%-- <ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:addPact()" ><span><s:text name="button.add"/></span></a>
				</li>
				<li>
					<a class="changebutton"  href="javaScript:editPact()"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a class="delbutton"  href="javaScript:delPact()"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a class="enablebutton"  href="javaScript:effectPact()"><span>生效</span></a>
				</li>
				<li>
					<a class="checkbutton"  href="javaScript:reNewPact()"><span>续签</span></a>
				</li>
				<li>
					<a class="delallbutton"  href="javaScript:breakPact()"><span>终止</span></a>
				</li>
				<li>
					<a class="disablebutton"  href="javaScript:relievePact()"><span>解除</span></a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('pact_gridtable','com.huge.ihos.hr.pact.model.Pact')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			</ul> --%>
		</div>
		
		<div id="pact_container">
			<div id="pact_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				
        <div class="treeTopCheckBox">
          <span>
           显示编码
           <input id="pact_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrPersonTreeInPact',showCode:this,disabledDept:jQuery('#pact_showDisabledDept')[0],disabledPerson:jQuery('#pact_showDisabledPerson')[0],showCount:true,showIds:showIds})"/>
         </span>
         <span>
           显示停用部门
          <input id="pact_showDisabledDept" type="checkbox" onclick="showDisabledDeptInPact(this)"/>
         </span>
         <label id="pact_expandTree" onclick="toggleExpandHrTree(this,'hrPersonTreeInPact')">展开</label>
        </div>
       <div class="treeTopCheckBox">
          <span>
           显示停用人员
          <input id="pact_showDisabledPerson" type="checkbox" onclick="showDisbaledPersonInPact(this)"/>
          </span>
        </div>
        <div class="treeTopCheckBox">
         <span>
           快速查询：
          <input type="text" onKeyUp="searchTree('hrPersonTreeInPact',this)"/>
         </span>
  	  </div>				
				<div id="hrPersonTreeInPact" class="ztree"></div>
			</div>
			<div id="pact_layout-center" class="pane ui-layout-center">
				<div id="pact_gridtable_div" class="grid-wrapdiv" 
					style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="pact_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_pact_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
		 			<table id="pact_gridtable"></table>
				</div>
				<div class="panelBar" id="pact_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="pact_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />.<s:text name="pager.total" /><label id="pact_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="pact_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>