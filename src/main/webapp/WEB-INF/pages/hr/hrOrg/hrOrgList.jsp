
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var hrOrgGridIdString="#hrOrg_gridtable";
	
	jQuery(document).ready(function() { 
		var hrOrgFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('hrOrg_container','hrOrg_gridtable',hrOrgFullSize);
// 		jQuery("#hrOrg_container").css("height",hrOrgFullSize);
// 		$('#hrOrg_container').layout({ 
// 			applyDefaultStyles: false ,
// 			west__size : 250,
// 			spacing_open:5,//边框的间隙  
// 			spacing_closed:5,//关闭时边框的间隙 
// 			resizable :true,
// 			resizerClass :"ui-layout-resizer-blue",
// 			slidable :true,
// 			resizerDragOpacity :1, 
// 			resizerTip:"可调整大小",//鼠标移到边框时，提示语
// 			onresize_end : function(paneName,element,state,options,layoutName){
// 				if("center" == paneName){
// 					gridResize(null,null,"hrOrg_gridtable","single");
// 				}
// 			}
// 		});
		
		loadHrOrgTree();
		
		var initFlag = 0;
		var hrOrgGrid = jQuery(hrOrgGridIdString);
    	hrOrgGrid.jqGrid({
    		url : "hrOrgGridList",  //获取数据地址
    		editurl:"hrOrgGridEdit",
			datatype : "json",//从服务器端返回的数据类型
			mtype : "GET", //ajax提交的方式
        	colModel:[//name列显示
				{name:'orgCode',index:'orgCode',align:'left',width:'90',label : '<s:text name="hrOrgSnap.orgCode" />',hidden:false,key:true},
				{name:'orgname',index:'orgname',align:'left',width:'160',label : '<s:text name="hrOrgSnap.orgname" />',hidden:false,highsearch:true},
				{name:'shortName',index:'shortName',align:'left',width:'120',label : '<s:text name="hrOrgSnap.shortName" />',hidden:false,highsearch:true},
				{name:'ownerOrg',index:'ownerOrg',align:'left',width:'140',label : '<s:text name="hrOrgSnap.ownerOrg" />',hidden:false,highsearch:true},
				{name:'internal',index:'internal',align:'left',width:'70',label : '<s:text name="hrOrgSnap.internal" />',hidden:false,highsearch:true},
				{name:'address',index:'address',align:'left',width:'140',label : '<s:text name="hrOrgSnap.address" />',hidden:false,highsearch:true},
				{name:'postCode',index:'postCode',align:'left',width:'80',label : '<s:text name="hrOrgSnap.postCode" />',hidden:false,highsearch:true},
				{name:'homePage',index:'homePage',align:'left',width:'90',label : '<s:text name="hrOrgSnap.homePage" />',hidden:false,highsearch:true},
				{name:'email',index:'email',align:'left',width:'120',label : '<s:text name="hrOrgSnap.email" />',hidden:false,highsearch:true},
				{name:'phone',index:'phone',align:'left',width:'100',label : '<s:text name="hrOrgSnap.phone" />',hidden:false,highsearch:true},
				{name:'fax',index:'fax',align:'left',width:'100',label : '<s:text name="hrOrgSnap.fax" />',hidden:false,highsearch:true},
				{name:'contact',index:'contact',align:'left',width:'90',label : '<s:text name="hrOrgSnap.contact" />',hidden:false,highsearch:true},
				{name:'contactPhone',index:'contactPhone',align:'left',width:'90',label : '<s:text name="hrOrgSnap.contactPhone" />',hidden:false,highsearch:true},
				{name:'disabled',index:'disabled',align:'center',width:'50',label : '<s:text name="hrOrgSnap.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},
				{name:'invalidDate',index:'invalidDate',align:'center',width:'90',label : '<s:text name="hrOrgSnap.invalidDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
				{name:'snapCode',index:'snapCode',align:'center',width:'21',label : '<s:text name="hrOrgSnap.snapCode" />',hidden:true},
				//{name:'type',index:'type',align:'center',label : '<s:text name="hrOrgSnap.type" />',hidden:false,highsearch:true} ,       	
				{name:'note',index:'note',align:'left',width:'150',label : '<s:text name="hrOrgSnap.note" />',hidden:false,highsearch:true},
				],
        	jsonReader : {
				root : "hrOrgs", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
        	sortname: 'orgCode',
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
		 		gridContainerResize('hrOrg','layout');
		 		var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum<=0){
	            	var tw = jQuery("#hrOrg_gridtable").outerWidth();
					jQuery("#hrOrg_gridtable").parent().width(tw);
					jQuery("#hrOrg_gridtable").parent().height(1);
	            }else{
	            	var rowIds = jQuery(this).getDataIDs();
 	                var ret = jQuery(this).jqGrid('getRowData');
 	                var id='';
 	                for(var i=0;i<rowNum;i++){
 	                	id=rowIds[i];
 	                	var snapId=ret[i]["orgCode"]+'_'+ret[i]["snapCode"];
 		   	        	setCellText(this,id,'orgCode','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrOrgRecord(\''+snapId+'\');">'+ret[i]["orgCode"]+'</a>');
 	                }
 	                var ztree = $.fn.zTree.getZTreeObj("hrOrgTree");
 	                if(ztree){
 	                	var selectNode = ztree.getSelectedNodes();
 						if(selectNode && selectNode.length==1){
 							var selectid = selectNode[0].id;
 	 						jQuery(this).jqGrid('setSelection',selectid);
 						}
 	                }
	            }
           		var dataTest = {"id":"hrOrg_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('hrOrg_gridtable','com.huge.ihos.hr.hrOrg.model.HrOrg',initFlag);
       		} 
    	});
		jQuery(hrOrgGrid).jqGrid('bindKeys');
		
		/*--------------------------------------tooBar start-------------------------------------------*/
    	var hrOrg_menuButtonArrJson = "${menuButtonArrJson}";
    	var hrOrg_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(hrOrg_menuButtonArrJson,false)));
    	var hrOrg_toolButtonBar = new ToolButtonBar({el:$('#hrOrg_buttonBar'),collection:hrOrg_toolButtonCollection,attributes:{
    		tableId : 'hrOrg_gridtable',
    		baseName : 'hrOrg',
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
    	
    	var hrOrg_function = new scriptFunction();
    	
    	hrOrg_function.optBeforeCall = function(e,$this,param){
    		var pass = false;
    		if(param.checkPeriod){
				if('${yearStarted}'!='true'){
					alertMsg.error("本年度人力资源系统未启用。");
	    			return pass;
				}
			}
    		if(param.selectRecord){
    			var sid = jQuery("#hrOrg_gridtable").jqGrid('getGridParam','selarrrow');
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
    	//添加
		hrOrg_toolButtonBar.addCallBody('10010101',function(e,$this,param){
			var url = "editHrOrgSnap?popup=true&navTabId=hrOrg_gridtable";
			var winTitle="<s:text name='hrOrgSnapNew.title'/>";
			var zTree = $.fn.zTree.getZTreeObj("hrOrgTree");  
			var nodes = zTree.getSelectedNodes(); 
			if(nodes.length!=0 && nodes[0].id != '-1'){
				url += "&parentOrgCode="+nodes[0].id
		    }
			url = encodeURI(url);
			$.pdialog.open(url, 'addHrOrgSnap', winTitle, {mask:true,width:650,height:400,resizable:false,maxable:false});
		},{});
		hrOrg_toolButtonBar.addBeforeCall('10010101',function(e,$this,param){
			return hrOrg_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
		//删除
		hrOrg_toolButtonBar.addCallBody('10010102',function(e,$this,param){
			var sid = jQuery("#hrOrg_gridtable").jqGrid('getGridParam','selarrrow');
			var opsid = '';
			for(var i=0;i<sid.length;i++){
				rowData = jQuery("#hrOrg_gridtable").jqGrid('getRowData',sid[i]);
				if(rowData["disabled"]=='No'){
					alertMsg.error("只能删除已停用的单位。");
					return;
				}
				opsid += sid[i]+'_'+rowData['snapCode']+',';
			} 
			var editUrl = "hrOrgSnapGridEdit?oper=del&navTabId=hrOrg_gridtable&id="+opsid;
			editUrl = encodeURI(editUrl);
	        alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post(editUrl, function(data){
						formCallBack(data);
						if(data.statusCode!=200){
							return;
						}
						var hrOrgTreeObj = $.fn.zTree.getZTreeObj("hrOrgTree");
						for(var i=0;i<sid.length;i++){
							var node = hrOrgTreeObj.getNodeByParam("id", sid[i], null);
							hrOrgTreeObj.removeNode(node);
						}
					},'json');
				}
			});
		},{});
		hrOrg_toolButtonBar.addBeforeCall('10010102',function(e,$this,param){
			return hrOrg_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
		// 修改
		hrOrg_toolButtonBar.addCallBody('10010103',function(e,$this,param){
			var sid = jQuery("#hrOrg_gridtable").jqGrid('getGridParam','selarrrow');
			var row = jQuery("#hrOrg_gridtable").jqGrid('getRowData',sid[0]);
    		var opsid = sid+'_'+row['snapCode'];
			var url = "editHrOrgSnap?popup=true&snapId="+opsid+"&navTabId=hrOrg_gridtable";
			var winTitle="<s:text name='hrOrgSnapEdit.title'/>";
			url = encodeURI(url);
			$.pdialog.open(url, 'editHrOrgSnap', winTitle, {mask:true,width:650,height:400,resizable:false,maxable:false});
		},{});
		hrOrg_toolButtonBar.addBeforeCall('10010103',function(e,$this,param){
			return hrOrg_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",singleSelect:"单选",checkPeriod:"checkPeriod"});
		// 启用
		//只有上级单位启用的情况下才可以启用下级单位
		hrOrg_toolButtonBar.addCallBody('10010104',function(e,$this,param){
			var sid = jQuery("#hrOrg_gridtable").jqGrid('getGridParam','selarrrow');
			var opsid = '';
			for(var i=0;i<sid.length;i++){
				rowData = jQuery("#hrOrg_gridtable").jqGrid('getRowData',sid[i]);
				if(rowData["disabled"]=='No'){
					alertMsg.error("你选择的单位已启用。");
					return;
				}
				opsid += sid[i]+'_'+rowData['snapCode']+',';
			} 
			$.ajax({
			    url: "checkEnableHrOrg?orgCodes="+sid,
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
			        	var editUrl = "hrOrgSnapGridEdit?oper=enable&navTabId=hrOrg_gridtable&id="+opsid;
			        	editUrl = encodeURI(editUrl);
						alertMsg.confirm("确认启用？", {
							okCall: function(){
								jQuery.post(editUrl, function(data){
									formCallBack(data);
									if(data.statusCode==200){
										var hrOrgTreeObj = $.fn.zTree.getZTreeObj("hrOrgTree");
										for(var i=0;i<sid.length;i++){
											var node = hrOrgTreeObj.getNodeByParam("id", sid[i], null);
											node.actionUrl = '0';
											//node.font = {'color' : 'black','font-style' : 'normal','text-decoration' : 'none'};
											hrOrgTreeObj.updateNode(node);
											var alabelid = node.tId;
											jQuery("#"+alabelid+"_a").css('font-style','normal').css('text-decoration','none');
										}
									}
									//loadHrOrgTree();
								}, "json");
							}
						});
					}
			    }
			});		
		},{});
		hrOrg_toolButtonBar.addBeforeCall('10010104',function(e,$this,param){
			return hrOrg_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
		// 停用
		//只有下级全部停用的情况下才可以停用上级
		hrOrg_toolButtonBar.addCallBody('10010105',function(e,$this,param){
			var sid = jQuery("#hrOrg_gridtable").jqGrid('getGridParam','selarrrow');
			var opsid = '';
			for(var i=0;i<sid.length;i++){
				rowData = jQuery("#hrOrg_gridtable").jqGrid('getRowData',sid[i]);
				if(rowData["disabled"]=='Yes'){
					alertMsg.error("你选择的单位已停用。");
					return;
				}
				opsid += sid[i]+'_'+rowData['snapCode']+',';
			} 
			$.ajax({
			    url: "checkDisableHrOrg?orgCodes="+sid,
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
			        	var editUrl = "hrOrgSnapGridEdit?oper=disable&navTabId=hrOrg_gridtable&id="+opsid;
			        	editUrl = encodeURI(editUrl);
						alertMsg.confirm("停用后，此单位下部门和人员将无法显示，确认停用？", {
							okCall: function(){
								jQuery.post(editUrl, function(data){
									formCallBack(data);
									if(data.statusCode==200){
										var hrOrgTreeObj = $.fn.zTree.getZTreeObj("hrOrgTree");
										for(var i=0;i<sid.length;i++){
											var node = hrOrgTreeObj.getNodeByParam("id", sid[i], null);
											node.actionUrl = '1';
											//node.font={'color':'black','font-style' : 'italic','text-decoration' : 'line-through'};
											hrOrgTreeObj.updateNode(node);
											var alabelid = node.tId;
											jQuery("#"+alabelid+"_a").css('font-style','italic').css('text-decoration','line-through');
										}
									}
									//loadHrOrgTree();
								}, "json");
							}
						});
					}
			    }
			});			
		},{});
		hrOrg_toolButtonBar.addBeforeCall('10010105',function(e,$this,param){
			return hrOrg_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
		//设置表格列
		var hrOrg_setColShowButton = {id:'10010188',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
   			callBody:function(){
   				setColShow('hrOrg_gridtable','com.huge.ihos.hr.hrOrg.model.HrOrg');
   			}};
		hrOrg_toolButtonBar.addButton(hrOrg_setColShowButton);
  	});
	
	function loadHrOrgTree(){
		$.get("makeHrOrgTree", function(data) {
			var hrOrgTreeData = data.hrOrgTreeNodes;
			var hrOrgTree = $.fn.zTree.init($("#hrOrgTree"),ztreesetting_hrOrgTree, hrOrgTreeData);
			var nodes = hrOrgTree.getNodes();
			hrOrgTree.expandNode(nodes[0], true, false, true);
			hrOrgTree.selectNode(nodes[0]);
		});
		jQuery("#hrOrg_expandTree").text("展开");
	}
	var ztreesetting_hrOrgTree = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false,
			fontCss :setDisabledDeptFontCss
		},
		callback : {
			beforeDrag:function(){return false},
			onClick : function(event, treeId, treeNode, clickFlag){
				var urlString = "hrOrgGridList?1=1";
			    var nodeId = treeNode.id;
			    if(nodeId!="-1"){
				    urlString += "&orgCode="+nodeId;
			    }
				urlString=encodeURI(urlString);
				jQuery("#hrOrg_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
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
	function hrOrgListSearchFormReload(){
		propertyFilterSearch('hrOrg_search_form','hrOrg_gridtable');
		var postData = jQuery("#hrOrg_gridtable").jqGrid('getGridParam',"postData");
		var urlString = 'hrOrgGridList?1=1';
		var isShowAll=true;
		jQuery.each(postData, function(key, val) {
			if(val){
			switch(key){
				case 'filter_LIKES_orgCode':
					urlString+="&filter_LIKES_orgCode="+val;
					isShowAll=false;
					break;
				case 'filter_LIKES_orgname':
					urlString+="&filter_LIKES_orgname="+val;
					isShowAll=false;
					break;
				case 'filter_EQB_disabled':
					urlString+="&filter_EQB_disabled="+val;
					isShowAll=false;
					break;
				case 'filter_LIKES_note':
					urlString+="&filter_LIKES_note="+val;
					isShowAll=false;
					break;
			}  
			}
	 　　});   
		var treeObj = $.fn.zTree.getZTreeObj("hrOrgTree");
		 if(!treeObj){
        	return; 
         }else{
        	 var nodes = treeObj.getNodes();
        	 if(isShowAll){
        		 jQuery.each(nodes[0].children, function() { 
        			 showAllTreeNodes(this,"hrOrgTree",true);
            		 });
        		 return;
        	 }
        	 jQuery.each(nodes[0].children, function() { 
        		 hideZTreeNodes(this,"hrOrgTree");
        		 });
        	treeObj.refresh();
         }
		 urlString=encodeURI(urlString);
		jQuery.ajax({
		       url: urlString,
		       data: {},
		       type: 'post',
		       dataType: 'json',
		       async:false,
		       error: function(data){
		       },
		       success: function(data){
		        if(data.hrOrgAll){
		        	jQuery.each(data.hrOrgAll, function() { 
		        		var updateNode = treeObj.getNodeByParam("id", this, null);
		        		showZTreeNodes(updateNode,"hrOrgTree");
		         		 });
		        	//treeObj.refresh();
		        }
		   }
		  });
	}
</script>

<div class="page" id="hrOrg_page">
	<div id="hrOrg_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="hrOrg_search_form" style="white-space: break-all;word-wrap:break-word;" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrOrgSnap.orgCode'/>:
						<input type="text" name="filter_LIKES_orgCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrOrgSnap.orgname'/>:
						<input type="text" name="filter_LIKES_orgname"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrOrgSnap.disabled'/>:
						<s:select list="#{'':'--','1':'是','0':'否'}" name="filter_EQB_disabled" style="width:60px"></s:select>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrOrgSnap.note'/>:
						<input type="text" name="filter_LIKES_note"/>
					</label>			
					 <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="hrOrgListSearchFormReload()"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>				
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="hrOrgListSearchFormReload()"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div id="hrOrg_buttonBar" class="panelBar">
		</div>
		<div id="hrOrg_container">
			<div id="hrOrg_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
<!-- 				<div style="float:right"> -->
<!-- 					<span id="hrOrg_expandTree" onclick="toggleExpandTree(this,'hrOrgTree')" style="vertical-align:middle;text-decoration:underline;cursor:pointer;">展开</span> -->
<!-- 				</div> -->
					<div class="treeTopCheckBox">
						<span>
							显示机构编码
							<input id="hrOrg_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrOrgTree',showCode:this,disabledDept:false,disabledPerson:false,showCount:false })"/>
						</span>
					</div>
				<div id="hrOrgTree" class="ztree"></div>
			</div>
			<div id="hrOrg_layout-center" class="pane ui-layout-center">
				<div id="hrOrg_gridtable_div" class="grid-wrapdiv">
					<input type="hidden" id="hrOrg_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_hrOrg_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
		 			<table id="hrOrg_gridtable"></table>		 				
				</div>

				<div class="panelBar"  id="hrOrg_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="hrOrg_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>							
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="hrOrg_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="hrOrg_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">  
						
					</div>
				</div>
			</div>
		</div>
	</div>	
</div>