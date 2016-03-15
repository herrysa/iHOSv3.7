<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var hrDepartmentCurrentGridIdString="#hrDepartmentCurrent_gridtable";
	var showIds = null;
	jQuery(document).ready(function() {
		jQuery("#hrDepartmentCurrent_pageHeader").find("select").css("font-size","12px");
		/*------------------------------set layout-----------------------------------------*/
		var hrDepartmentCurrentFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('hrDepartmentCurrent_container','hrDepartmentCurrent_gridtable',hrDepartmentCurrentFullSize);
		
		/*------------------------------load leftTree-----------------------------------------*/
		hrDepartmentCurrentLeftTree();
		/*------------------------------load rightGrid-----------------------------------------*/
		var initFlag = 0;
		var hrDepartmentCurrentGrid = jQuery(hrDepartmentCurrentGridIdString);
    	hrDepartmentCurrentGrid.jqGrid({
	    	url : "hrDepartmentCurrentGridList?1=1",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'departmentId',index:'departmentId',align:'left',width:'20',label : '<s:text name="hrDepartmentSnap.snapId" />',hidden:true,key:true,highsearch:false},				
				{name:'snapCode',index:'snapCode',align:'left',width:'80',label : '<s:text name="hrDepartmentSnap.snapCode" />',hidden:true,highsearch:false},				
				{name:'deptCode',index:'deptCode',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.deptCode" />',hidden:false,highsearch:true},				
				{name:'name',index:'name',align:'left',width:'120',label : '<s:text name="hrDepartmentSnap.name" />',hidden:false,highsearch:true},				
				{name:'hrOrg.orgname',index:'hrOrg.orgname',align:'left',width:'150',label : '<s:text name="hrDepartmentSnap.orgName" />',hidden:false,highsearch:true},				
				<c:if test="${herpType == 'S2'}">
				{name : 'branch.branchName',index : 'branch.branchName',align : 'left',width:70,label : '<s:text name="hrDepartmentSnap.branch" />',hidden : false, sortable:true,highsearch:true},
				</c:if>
				{name:'shortnName',index:'shortnName',align:'left',width:'120',label : '<s:text name="hrDepartmentSnap.shortName" />',hidden:false,highsearch:true},				
				{name:'deptClass',index:'deptClass',align:'center',width:'70',label : '<s:text name="hrDepartmentSnap.deptType" />',hidden:false,highsearch:true},				
				{name:'outin',index:'outin',align:'center',width:'70',label : '<s:text name="hrDepartmentSnap.attrCode" />',hidden:false,highsearch:true},	
				{name : 'dgroup',index : 'dgroup',align : 'left',width:70,label : '<s:text name="hrDepartmentSnap.dgroup" />',hidden : false, sortable:true,highsearch:true},
				{name:'clevel',index:'clevel',align:'center',width:'50',label : '<s:text name="hrDepartmentSnap.clevel" />',hidden:false,formatter:'integer',highsearch:true},				
				{name:'leaf',index:'leaf',align:'center',width:'50',label : '<s:text name="hrDepartmentSnap.leaf" />',hidden:false,formatter:'checkbox',highsearch:true},				
				{name:'parentDept.name',index:'parentDept.name',width:'100',align:'left',label : '<s:text name="hrDepartmentSnap.parentDept" />',hidden:false,highsearch:true},				
				{name:'subClass',index:'subClass',align:'center',width:'100',label : '<s:text name="hrDepartmentSnap.subClass" />',hidden:false,highsearch:true},				
				{name:'cnCode',index:'cnCode',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.cnCode" />',hidden:false,highsearch:true},				
				{name:'jjDeptType.khDeptTypeName',index:'jjDeptType.khDeptTypeName',align:'center',width:'100',label : '<s:text name="hrDepartmentSnap.jjDeptType" />',hidden:false,highsearch:true},				
				{name:'internalCode',index:'internalCode',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.internalCode" />',hidden:false,highsearch:true},				
				{name:'manager',index:'manager',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.manager" />',hidden:false,highsearch:true},				
				{name:'note',index:'note',align:'left',width:'120',label : '<s:text name="hrDepartmentSnap.remark" />',hidden:false,highsearch:true},				
				//{name:'ysPurchasingDepartment',index:'ysPurchasingDepartment',width:'80',align:'center',label : '<s:text name="hrDepartmentSnap.purchaseDept" />',hidden:false,formatter:'checkbox',highsearch:true},				
				{name:'disabled',index:'disabled',align:'center',width:'50',label : '<s:text name="hrDepartmentSnap.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},				
				{name:'state',index:'state',align:'center',width:'50',label : '<s:text name="hrDepartmentSnap.state" />',hidden:true,formatter:'integer'}				
	      	
			],
	        jsonReader : {
				root : "hrDepartmentCurrents", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'orgCode,deptCode',
	        viewrecords: true,
	        sortorder: 'asc',
	        postData:{'filter_EQB_disabled':false},
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
				gridContainerResize('hrDepartmentCurrent','layout');
				var rowNum = jQuery(this).getDataIDs().length;
 	            if(rowNum>0){
 	                var rowIds = jQuery(this).getDataIDs();
 	                var ret = jQuery(this).jqGrid('getRowData');
 	                var id='';
 	                for(var i=0;i<rowNum;i++){
 	                	id=rowIds[i];
 	                	var snapId=ret[i]["departmentId"]+'_'+ret[i]["snapCode"];
 		   	        	setCellText(this,id,'name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrDeptRecord(\''+snapId+'\',${random});">'+ret[i]["name"]+'</a>');
 	                }
 	                var ztree = $.fn.zTree.getZTreeObj("hrDepartmentCurrentLeftTree");
 	                if(ztree){
 	                	var selectNode = ztree.getSelectedNodes();
 						if(selectNode && selectNode.length==1){
 							var selectid = selectNode[0].id;
 							jQuery(this).jqGrid('setSelection',selectid);
 						}
 	                }
	                
 	            }else{
 	               var tw = jQuery(this).outerWidth();
 	               jQuery(this).parent().width(tw);
 	               jQuery(this).parent().height(1);
 	            }
	            var dataTest = {"id":"hrDepartmentCurrent_gridtable"};
	      	    jQuery.publish("onLoadSelect",dataTest,null);
	      	    initFlag = initColumn('hrDepartmentCurrent_gridtable','com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap',initFlag);
	      	    var userData = jQuery("#hrDepartmentCurrent_gridtable").getGridParam('userData'); 
	      	  	if(userData){
		      	    var addFilters = userData.addFilters;
	    			var queryedDeptIds = userData.queryedDeptIds;
		      	  	var queryedDeptIdArr ;
		      	  	
		      	  	if(queryedDeptIds){
		      	  		queryedDeptIdArr = queryedDeptIds.split("");
	          	  	}
			      	if(addFilters==true){
		      	  		if(!queryedDeptIds){
		      	  			queryedDeptIdArr = 'null';
		      	  		}
		  	  		}else{
		  	  			queryedDeptIdArr = 'all';
		  	  		}
			      	showIds = queryedDeptIdArr;
		      	  	toggleDisabledOrCount({treeId:'hrDepartmentCurrentLeftTree',
			  			showCode:jQuery('#hrDepartmentCurrent_showCode')[0],
			  			disabledDept:jQuery("#hrDepartmentCurrent_showDisabled")[0],
			  			disabledPerson:false,
			  			showCount:jQuery("#hrDepartmentCurrent_showPersonCount")[0],
			  			showIds:queryedDeptIdArr,
			  			addFilters:addFilters
			  		});
	      	  	}
			} 
    	});
    	jQuery(hrDepartmentCurrentGrid).jqGrid('bindKeys');
    	
    	/*----------------------------------tooBar start-----------------------------------------------*/
    	var hrDepartmentCurrent_menuButtonArrJson = "${menuButtonArrJson}";
    	var hrDepartmentCurrent_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(hrDepartmentCurrent_menuButtonArrJson,false)));
    	var hrDepartmentCurrent_toolButtonBar = new ToolButtonBar({el:$('#hrDepartmentCurrent_buttonBar'),collection:hrDepartmentCurrent_toolButtonCollection,attributes:{
    		tableId : 'hrDepartmentCurrent_gridtable',
    		baseName : 'hrDepartmentCurrent',
    		width : 600,
    		height : 600,
    		base_URL : null,
    		optId : null,
    		fatherGrid : null,
    		extraParam : null,
    		selectNone : "请选择记录。",
    		selectMore : "只能选择一条记录。",
    		addTitle : '<s:text name="hrDepartmentSnapNew.title"/>',
    		editTitle : null
    	}}).render();
    	
    	var hrDepartmentCurrent_function = new scriptFunction();
    	hrDepartmentCurrent_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.checkPeriod){
				if('${yearStarted}'!='true'){
					alertMsg.error("本年度人力资源系统未启用。");
	    			return pass;
				}
			}
			if(param.selectRecord){
				var sid = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getGridParam','selarrrow');
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
			}
	        return true;
		};
		// 添加
		hrDepartmentCurrent_toolButtonBar.addCallBody('1001020101',function(e,$this,param){
			var url = "editHrDepartmentSnap?popup=true&navTabId=hrDepartmentCurrent_gridtable&random=${random}";
    		var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDepartmentCurrentLeftTree");
    		var nodes = hrDeptTreeObj.getSelectedNodes();
			if(nodes.length==1 && nodes[0].subSysTem!='ALL'){
				var node = nodes[0];
				if(node.subSysTem==='ORG'){// org
					url += "&orgCode="+node.id;
				}else{//dept
					/* if(!node.isParent &&　node.personCount!='0'){
						alertMsg.error("您选择的部门下有人员，不能作为上级部门。");
		    			return;
					} */
					url += "&parentDeptId="+node.id;
				}
				url=encodeURI(url);
	    		var winTitle='<s:text name="hrDepartmentSnapNew.title"/>';
				if(node.subSysTem==='DEPT'){
					// 判断选择的部门能否作为新部门的上级部门
					$.ajax({
					    url: "checkAddDeptForDept?deptId="+node.id,
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
		    					$.pdialog.open(url,'addHrDepartmentSnap',winTitle, {mask:true,width : 680,height : 518,maxable:false,resizable:false});
							}
					    }
					});
				}else{
		    		$.pdialog.open(url,'addHrDepartmentSnap',winTitle, {mask:true,width : 680,height : 518,maxable:false,resizable:false});
				}
			}else{
				alertMsg.error("请选择单位或部门。");
    			return;
			}
		},{});
		hrDepartmentCurrent_toolButtonBar.addBeforeCall('1001020101',function(e,$this,param){
			return hrDepartmentCurrent_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
		// 删除
		hrDepartmentCurrent_toolButtonBar.addCallBody('1001020102',function(e,$this,param){
			var url = "${ctx}/hrDepartmentSnapGridEdit?oper=del"
			var sid = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getGridParam','selarrrow');
			var opsid = '';
			var rowData;
			for(var i=0;i<sid.length;i++){
				rowData = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getRowData',sid[i]);
				/* if(rowData["disabled"]=='No'){
					alertMsg.error("只能删除已停用的部门！");
					return;
				} */
				opsid += sid[i]+'_'+rowData['snapCode']+',';
			} 
			url = url+"&id="+opsid+"&navTabId=hrDepartmentCurrent_gridtable";
			$.ajax({
			    url: "checkDelDept?deptIds="+sid,
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
			        	alertMsg.confirm("确认删除？", {
							okCall : function() {
								$.post(url,function(data) {
									formCallBack(data);
									if(data.statusCode!=200){
										return;
									}
									reloadHrDeptCurrentGrid();
									//reloadHrDeptCurrentGrid(data);
									// delete 
									for(var i=0;i<sid.length;i++){
		    							dealHrTreeNode('hrDepartmentCurrentLeftTree',{id:sid[i]},'del','dept');
									}
									/* if(sid.length==1){
										var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDepartmentCurrentLeftTree");
										var node = hrDeptTreeObj.getNodeByParam("id", sid[0], null);
										hrDeptTreeObj.removeNode(node);
									}else{
										hrDepartmentCurrentLeftTree();
									} */
								});
							}
						});
					}
			    }
			});
			
		},{});
		hrDepartmentCurrent_toolButtonBar.addBeforeCall('1001020102',function(e,$this,param){
			return hrDepartmentCurrent_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
		
		// 修改
		hrDepartmentCurrent_toolButtonBar.addCallBody('1001020103',function(e,$this,param){
			var sid = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getGridParam','selarrrow');
    		var row = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getRowData',sid[0]);
    		var opsid = sid+'_'+row['snapCode'];
    		var winTitle='<s:text name="hrDepartmentSnapEdit.title"/>';
    		var url = "editHrDepartmentSnap?popup=true&snapId="+opsid+"&navTabId=hrDepartmentCurrent_gridtable&random=${random}";
    		$.pdialog.open(url,'editHrDepartmentSnap',winTitle, {mask:true,width : 680,height : 518,maxable:false,resizable:false});
		},{});
		hrDepartmentCurrent_toolButtonBar.addBeforeCall('1001020103',function(e,$this,param){
			return hrDepartmentCurrent_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",singleSelect:"单选",checkPeriod:"checkPeriod"});
		
		// 启用
		hrDepartmentCurrent_toolButtonBar.addCallBody('1001020104',function(e,$this,param){
			var url = "${ctx}/hrDepartmentSnapGridEdit?oper=enable";
			var sid = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getGridParam','selarrrow');
			var opsid = '';
			var rowData;
			for(var i=0;i<sid.length;i++){
				rowData = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getRowData',sid[i]);
				/* if(rowData["disabled"]=='No'){
					alertMsg.error("你选择的部门已启用！");
					return;
				}
				if(rowData["state"]=='4'){
					alertMsg.error("你选择的部门已撤销！");
					return;
				} */
				opsid += sid[i] + '_'+rowData['snapCode']+',';
			} 
			url = url+"&id="+opsid+"&navTabId=hrDepartmentCurrent_gridtable";
			$.ajax({
			    url: "checkEnableDept?deptIds="+sid,
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
			        	alertMsg.confirm("确认启用？", {
							okCall : function() {
								$.post(url,function(data) {
									formCallBack(data);
									if(data.statusCode!=200){
										return;
									}
									reloadHrDeptCurrentGrid();
									// enable
									for(var i=0;i<sid.length;i++){
		    							dealHrTreeNode('hrDepartmentCurrentLeftTree',{id:sid[i]},'enable','dept');
									}
									/* hrDepartmentCurrentLeftTree();
									if(sid.length==1){
										var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDepartmentCurrentLeftTree");
										var node = hrDeptTreeObj.getNodeByParam("id", sid[0], null);
										var pNode = node.getParentNode();
										hrDeptTreeObj.expandNode(pNode, true, false);
										hrDeptTreeObj.selectNode(node);
									} */
								});
							}
						});
					}
			    }
			});
			
		},{});
		hrDepartmentCurrent_toolButtonBar.addBeforeCall('1001020104',function(e,$this,param){
			return hrDepartmentCurrent_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
		
		//停用
		hrDepartmentCurrent_toolButtonBar.addCallBody('1001020105',function(e,$this,param){
			var url = "${ctx}/hrDepartmentSnapGridEdit?oper=disable";
			var sid = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getGridParam','selarrrow');
			var opsid = '';
			var rowData;
			var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDepartmentCurrentLeftTree");
			for(var i=0;i<sid.length;i++){
				rowData = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getRowData',sid[i]);
				if(rowData["disabled"]=='Yes'){
					alertMsg.error("你选择的部门已停用。");
					return;
				}
				var node = hrDeptTreeObj.getNodeByParam("id", sid[i], null);
				if(node.isParent){
					var childNodes = node.children;
					if(childNodes){
						for(var index in childNodes){
							node = childNodes[index];
							if(node.actionUrl != '1'){
								alertMsg.error("你选择的部门存在未停用的下级部门，不能停用。");
								return;
							}
						}
					}
				}else{
					if(node.personCountP!=0){
						alertMsg.error("你选择的部门下有人员，不能停用。");
						return;
					}
					if(node.personCount!=0){
						alertMsg.error("你选择的部门下有停用人员，不能停用。");
						return;
					}
				}
				opsid += sid[i] + '_'+rowData['snapCode']+',';
			} 
			url = url+"&id="+opsid+"&navTabId=hrDepartmentCurrent_gridtable";
			alertMsg.confirm("确认停用？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
						if(data.statusCode!=200){
							return;
						}
						reloadHrDeptCurrentGrid();
						// disable
						for(var i=0;i<sid.length;i++){
							dealHrTreeNode('hrDepartmentCurrentLeftTree',{id:sid[i]},'disable','dept');
						}
						
						/* reloadHrDeptCurrentGrid(data);
						hrDepartmentCurrentLeftTree();
						if(sid.length==1){
							var showDisabled = jQuery("#hrDepartmentCurrent_showDisabled").attr("checked");
							if(showDisabled){
								var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDepartmentCurrentLeftTree");
								var node = hrDeptTreeObj.getNodeByParam("id", sid[0], null);
								var pNode = node.getParentNode();
								hrDeptTreeObj.expandNode(pNode, true, false);
								hrDeptTreeObj.selectNode(node);
							}
						} */
					});
				}
			});
		},{});
		hrDepartmentCurrent_toolButtonBar.addBeforeCall('1001020105',function(e,$this,param){
			return hrDepartmentCurrent_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
		
		//部门调整
    	hrDepartmentCurrent_toolButtonBar.addCallBody('1001020106',function(e,$this,param){
    		var buttonId = "button_1001020106";
       	    var containerId = 'hrDepartmentCurrent_page';
       	    addSelectButton(buttonId,containerId,[ {id:'hrDeptCurrentNew',name:'部门新增',className:'edit',callBody:function(e,$$this){
       	    	var url = "editHrDeptNew?popup=true&navTabId=hrDepartmentCurrent_gridtable&addFrom=noCheck";
       			var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDepartmentCurrentLeftTree");
       			var nodes = hrDeptTreeObj.getSelectedNodes();
       			if(nodes.length==1 && nodes[0].subSysTem!='ALL'){
       				var node = nodes[0];
       				if(node.subSysTem==='ORG'){// org
       					url += "&orgCode="+node.id;
       				}else{//dept
       					if(!node.isParent &&　node.personCount!='0'){
       						alertMsg.error("您选择的部门下有人员，不能作为上级部门。");
       		    			return;
       					}
       					if(node.state=="temp"){
       						alertMsg.error("您选择的部门尚未确认添加，不能作为上级部门。");
       		    			return;
       					}
       					url += "&parentDeptId="+node.id;
       				}
       			}else{
       				alertMsg.error("请选择单位或部门。");
       				return;
       			}
       			url=encodeURI(url);
       			var winTitle='<s:text name="新增部门"/>';
       			$.pdialog.open(url,'addHrDeptNew',winTitle, {mask:true,width : 680,height : 518,maxable:false,resizable:false});
       	    }},{id:'hrDeptCurrentRescind',name:'部门撤销',className:'edit',callBody:function(e,$$this){
       	    	var sid = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
		        	alertMsg.error("请选择记录。");
					return;
				}
		        if(sid.length != 1){
			        alertMsg.error("只能选择一条记录。");
					return;
				}
    			var rowData = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getRowData',sid[0]);
    			if(rowData["state"]=='4'){
    				alertMsg.error("你选择的部门已撤销。");
    				return;
    			}
    			if(rowData["disabled"]=='Yes'){
    				alertMsg.error("你选择的部门已停用。");
    				return;
    			}
    			var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDepartmentCurrentLeftTree");
    			var node = hrDeptTreeObj.getNodeByParam("id", sid[0], null);
    			if(node.isParent){
    				var childNodes = node.children;
    				if(childNodes){
    					for(var index in childNodes){
    						node = childNodes[index];
    						if(node.actionUrl != '1'){
    							alertMsg.error("你选择的部门存在未撤销的下级部门，不能撤销。");
    							return;
    						}
    					}
    				}
    			}
    			var url = "editHrDeptRescind?navTabId=hrDepartmentCurrent_gridtable&addFrom=noCheck&deptId="+node.id;
    			url=encodeURI(url);
    			var winTitle='<s:text name="hrDeptRescindNew.title"/>';
    			$.pdialog.open(url,'addHrDeptRescindNoCheck',winTitle, {mask:true,width : 670,height : 260,maxable:false,resizable:false});
       	      }},{id:'hrDeptCurrentMergeNew',name:'部门合并',className:'edit',callBody:function(e,$$this){
       	    	var winTitle='<s:text name="hrDeptMergeNew.title"/>';
    			var url = "editHrDeptMerge?popup=true&navTabId=hrDepartmentCurrent_gridtable&addFrom=noCheck";
    			$.pdialog.open(url,'addHrDeptMergeNoCheck',winTitle, {mask:true,width : 750,height : 600,maxable:false,resizable:false});
       	      }},{id:'hrDeptCurrentTransfer',name:'部门划转',className:'edit',callBody:function(e,$$this){
       	    	var winTitle='<s:text name="新增划转部门"/>';
    			var url = "editHrDeptTransfer?popup=true&navTabId=hrDepartmentCurrent_gridtable&type=2&addFrom=noCheck";
    			$.pdialog.open(url,'addHrDeptTransferNoCheck',winTitle, {mask:true,width : 750,height : 600,maxable:false,resizable:false});
       	      }}]); 
    	     },{});
    	hrDepartmentCurrent_toolButtonBar.addBeforeCall('1001020106',function(e,$this,param){
			return hrDepartmentCurrent_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
		
		
		//撤销
// 		hrDepartmentCurrent_toolButtonBar.addCallBody('1001020106',function(e,$this,param){
			
// 		},{});
// 		hrDepartmentCurrent_toolButtonBar.addBeforeCall('1001020106',function(e,$this,param){
// 			return hrDepartmentCurrent_function.optBeforeCall(e,$this,param);
//     	},{selectRecord:"selectRecord",singleSelect:"单选"});
		//合并
// 		hrDepartmentCurrent_toolButtonBar.addCallBody('1001020107',function(e,$this,param){
			
// 		},{});
		//划转
// 		hrDepartmentCurrent_toolButtonBar.addCallBody('1001020108',function(e,$this,param){
			
// 		},{});
		  //导入
	     hrDepartmentCurrent_toolButtonBar.addCallBody('1001020109',function(e,$this,param){
	    	 var winTitle='<s:text name="hrDeptExcelImport.title"/>';
  	 		 var url = "hrPersonExcelImport?navTabId=hrDepartmentCurrent_gridtable&oper=hrDept";
  	 		 $.pdialog.open(url,'importHrPersonSnap',winTitle, {mask:true,width : 600,height : 150});
	     },{});
	     hrDepartmentCurrent_toolButtonBar.addBeforeCall('1001020109',function(e,$this,param){
				return hrDepartmentCurrent_function.optBeforeCall(e,$this,param);
	    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
		//设置表格列
		var hrDepartmentCurrent_setColShowButton = {id:'1001020188',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
   			callBody:function(){
   				setColShow('hrDepartmentCurrent_gridtable','com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap');
   			}};
		hrDepartmentCurrent_toolButtonBar.addButton(hrDepartmentCurrent_setColShowButton);
    	/*----------------------------------tooBar end-----------------------------------------------*/
		jQuery("select[name=filter_EQB_disabled_notInFilter]","#hrDepartmentCurrent_search_form").change(function(){
	    	var deptDisabled = jQuery(this).val();
	    	var postData = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getGridParam',"postData");
	    	if(deptDisabled=='0'){
	    		jQuery("#hrDepartmentCurrent_showDisabled").removeAttr('checked');
	    		postData['filter_EQB_disabled'] = false;
	    	}else{
	    		jQuery("#hrDepartmentCurrent_showDisabled").attr('checked','checked');
	    		delete postData['filter_EQB_disabled'];
	    	}
	     });
    	
    	
    	//TreeSelect
    	jQuery("#hrDepartmentCurrent_form_branch").treeselect({
    		dataType:"sql",
        	optType:"multi",
        	sql:"select branchCode id,branchName name,'-1' pId from t_branch where branchCode <> 'XT' and disabled = '0'",
        	exceptnullparent:true,
        	minWidth : '175px',
        	selectParent:false,
        	lazy:true
    	});
    	
    	jQuery("#hrDepartmentCurrent_form_dgroup").treeselect({
    		dataType:"sql",
        	optType:"multi",
        	sql:"select i.value id,i.displayContent name,'-1' pId from t_dictionary_items i,t_dictionary d where i.dictionary_id = d.dictionaryId and d.code = 'dgroup'",
        	exceptnullparent:true,
        	selectParent:false,
        	lazy:true
    	});
    	
  	});
	
	function hrDepartmentCurrentLeftTree() {
		var url = "makeHrDeptTree";
		$.get(url, {"_" : $.now()}, function(data) {
			var hrDeptTreeData = data.hrDeptTreeNodes;
			var hrDeptTree = $.fn.zTree.init($("#hrDepartmentCurrentLeftTree"),ztreesetting_hrDeptTree, hrDeptTreeData);
			var nodes = hrDeptTree.getNodes();
			hrDeptTree.expandNode(nodes[0], true, false, true);
			hrDeptTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'hrDepartmentCurrentLeftTree',
				showCode:jQuery('#hrDepartmentCurrent_showCode')[0],
				disabledDept:jQuery("#hrDepartmentCurrent_showDisabled")[0],
				disabledPerson:false,
				showCount:jQuery("#hrDepartmentCurrent_showPersonCount")[0],
				showIds:'init'
			});
		});
		jQuery("#hrDepartmentCurrent_expandTree").text("展开");
	}
	
	var ztreesetting_hrDeptTree = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				fontCss : setDisabledDeptFontCss
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var urlString = "hrDepartmentCurrentGridList?1=1";
				    var nodeId = treeNode.id;
				    if(nodeId!="-1"){
				    	if(treeNode.subSysTem==='ORG'){
					    	urlString += "&orgCode="+nodeId;
				    	}else{
					    	urlString += "&departmentId="+nodeId;
				    	}
				    }
				    var showDisabled = jQuery("#hrDepartmentCurrent_showDisabled").attr("checked");
				    if(showDisabled){
				    	urlString += "&showDisabled=true"
				    }
					urlString=encodeURI(urlString);
					jQuery("#hrDepartmentCurrent_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
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
	function showDisabledDept(obj){
		var postData = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getGridParam',"postData");
		if(!obj.checked){
			postData['filter_EQB_disabled'] = false;
			jQuery("select[name=filter_EQB_disabled_notInFilter]","#hrDepartmentCurrent_search_form").val('0');
		}else{
			delete postData['filter_EQB_disabled'];
			jQuery("select[name=filter_EQB_disabled_notInFilter]","#hrDepartmentCurrent_search_form").val('');
		}
		reloadHrDeptCurrentGrid();
	}
	/* function showDisabledDept(obj){
		var showDisabledDept;
		var urlString = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabled','');
		if(obj.checked){
			showDisabledDept = true;
			urlString += "&showDisabled=true";
		}
		jQuery("#hrDepartmentCurrent_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		hdcTreeReShow();
	} */
	
	function reloadHrDeptCurrentGrid(){
		jQuery("#hrDepartmentCurrent_gridtable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
	}
	
	/* function reloadHrDeptCurrentGrid(data){
		if(data.statusCode==200){
			alertMsg.correct(data.message);
		}else{
			alertMsg.error(data.message);
			return;
		}
		var urlString = jQuery("#hrDepartmentCurrent_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('orgCode','');
		urlString = urlString.replace('departmentId','');
		alert(urlString);
	    jQuery("#hrDepartmentCurrent_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");

	} */
	function hrDepartmentCurrentSearchFormReaload(){
		var urlString = "hrDepartmentCurrentGridList?1=1";
		propertyFilterSearch('hrDepartmentCurrent_search_form','hrDepartmentCurrent_gridtable',true);
		var postData = $("#hrDepartmentCurrent_gridtable").jqGrid("getGridParam", "postData");
		var addFilters = postData['addFilters'];
		var treeObj = $.fn.zTree.getZTreeObj("hrDepartmentCurrentLeftTree");
		if(addFilters!=true){
			var selectedNode = treeObj.getNodeByParam('id','-1',null);
			treeObj.selectNode(selectedNode,false);
		}
		var selectedNodes = treeObj.getSelectedNodes();
	    var selectedNode ,nodeId ;
	    if(selectedNodes){
	    	selectedNode = selectedNodes[0];
	    	if(selectedNode){
				nodeId = selectedNode.id;
	    	}
	    }
	    
	    if(nodeId&&nodeId!="-1"){
	    	if(selectedNode.subSysTem==='ORG'){
		    	urlString += "&orgCode="+nodeId;
	    	}else{
		    	urlString += "&departmentId="+nodeId;
	    	}
	    }
		urlString=encodeURI(urlString);
		jQuery("#hrDepartmentCurrent_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		
	}
</script>
<div class="page" id="hrDepartmentCurrent_page">
	<div id="hrDepartmentCurrent_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="hrDepartmentCurrent_search_form" class="queryarea-form">
					<label class="queryarea-label" style="${herpType=='S2'?'':'display:none'}">
						<s:text name='hrDepartmentSnap.branch'/>:
						<input type="hidden" name="filter_INS_branch.branchCode" id="hrDepartmentCurrent_form_branch_id" />
					 	<input type="text" style="width:100px" id="hrDepartmentCurrent_form_branch"/>
					</label>
					<label class="queryarea-label">
						<s:text name="hrDepartmentSnap.dgroup"/>:
						<input type="hidden" name="filter_INS_dgroup" id="hrDepartmentCurrent_form_dgroup_id" />
						<input type="text" style="width:100px" id="hrDepartmentCurrent_form_dgroup" >
					</label>
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.deptCode'/>:
					 	<input type="text" style="width:100px" name="filter_LIKES_deptCode"/>
					</label>
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.name'/>:
					 	<input type="text" style="width:100px" name="filter_LIKES_name" />
					</label>
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.deptType'/>:
					 	<s:select name="filter_EQS_deptClass" headerKey="" headerValue="--" 
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
					 	<s:select name="filter_EQB_disabled_notInFilter" headerKey="" headerValue="--" 
							list="#{'1':'是','0':'否' }" listKey="key" listValue="value" value="0"
							emptyOption="false"  maxlength="10" theme="simple">
						</s:select>
					</label>
					
					
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.attrCode'/>:
					 	<s:select name="filter_EQS_outin" headerKey="" headerValue="--" 
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
					 	<input type="text" style="width:100px" name="filter_LIKES_note" />
					</label>
					
					
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="hrDepartmentCurrentSearchFormReaload()"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="hrDepartmentCurrentSearchFormReaload()"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div id="hrDepartmentCurrent_buttonBar" class="panelBar">

			<%--	<li>
					<a id="hrDepartmentCurrent_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a id="hrDepartmentCurrent_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="hrDepartmentCurrent_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a class="enablebutton" href="javaScript:enableOrDisableHrDept('enable')"><span><s:text name="button.enable" /></span> </a></li>
				<li>
					<a class="disablebutton" href="javaScript:enableOrDisableHrDept('disable')"><span><s:text name="button.disable" /></span> </a></li>
				<li>
				<li>
					<a class="delallbutton"  href="javaScript:hrDepartmentRescind()"><span>撤销</span></a>
				</li>
				<li>
					<a class="savebutton"  href="javaScript:hrDepartmentMerge()"><span>合并</span></a>
				</li>
				<li>
					<a class="getdatabutton"  href="javaScript:hrDepartmentTransfer()"><span>划转</span></a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('hrDepartmentCurrent_gridtable','com.huge.ihos.hr.hrDeptment.model.HrDepartmentSnap')"><span><s:text name="button.setColShow" /></span></a>
				</li> --%>

		</div>
		<div id="hrDepartmentCurrent_container">
			<div id="hrDepartmentCurrent_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
					<span>
						显示机构编码
						<input id="hrDepartmentCurrent_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrDepartmentCurrentLeftTree',showCode:this,disabledDept:jQuery('#hrDepartmentCurrent_showDisabled')[0],disabledPerson:false,showCount:jQuery('#hrDepartmentCurrent_showPersonCount')[0],showIds:showIds })"/>
					</span>
					<span>
						显示人员数
						<input id="hrDepartmentCurrent_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrDepartmentCurrentLeftTree',showCode:jQuery('#hrDepartmentCurrent_showCode')[0],disabledDept:jQuery('#hrDepartmentCurrent_showDisabled')[0],disabledPerson:false,showCount:this,showIds:showIds })"/>
					</span>
					<label id="hrDepartmentCurrent_expandTree" onclick="toggleExpandHrTree(this,'hrDepartmentCurrentLeftTree')">展开</label>
				</div>
				<div class="treeTopCheckBox">
					<span>
						显示停用部门
						<input id="hrDepartmentCurrent_showDisabled" type="checkbox" onclick="showDisabledDept(this)"/>
					</span>
				</div>
				<div class="treeTopCheckBox">
					<span>
						按部门检索：
						<input type="text" onKeyUp="searchTree('hrDepartmentCurrentLeftTree',this)"/>
					</span>
				</div>
				<div id="hrDepartmentCurrentLeftTree" class="ztree"></div>
			</div>
			<div id="hrDepartmentCurrent_layout-center" class="pane ui-layout-center">
				<div id="hrDepartmentCurrent_gridtable_div" class="grid-wrapdiv">
					<input type="hidden" id="hrDepartmentCurrent_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_hrDepartmentCurrent_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
		 			<table id="hrDepartmentCurrent_gridtable"></table>		 				
				</div>

				<div class="panelBar"  id="hrDepartmentCurrent_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="hrDepartmentCurrent_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>							
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="hrDepartmentCurrent_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="hrDepartmentCurrent_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">  
						
					</div>
				</div>
			</div>
		</div><!-- layout -->
	</div>
</div>
