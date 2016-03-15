
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var postGridIdString="#post_gridtable";
	
	jQuery(document).ready(function() { 
		jQuery("#post_pageHeader").find("select").css({"font-size":"12px"});
		/*------------------------------set layout-----------------------------------------*/
		var postFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('post_container','post_gridtable',postFullSize);
		/*------------------------------load leftTree-----------------------------------------*/
		hrDeptTreeLeftInPost();
		/*------------------------------load rightGrid-----------------------------------------*/
		var initFlag = 0;
		var postGrid = jQuery(postGridIdString);
    	postGrid.jqGrid({
	    	url : "postGridList?1=1",
	    	editurl:"postGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="post.id" />',hidden:true,key:true},
				{name:'code',index:'code',align:'left',width : '120',label : '<s:text name="post.code" />',hidden:false,highsearch:true},	
				{name:'name',index:'name',align:'left',width : '100',label : '<s:text name="post.name" />',hidden:false,highsearch:true},	
				{name:'hrDept.name',index:'hrDept.name',align:'left',width : '100',label : '<s:text name="dept.name" />',hidden:false,highsearch:true},
				{name:'hrDept.departmentId',index:'hrDept.departmentId',align:'left',width : '100',label : '<s:text name="dept.departmentId" />',hidden:true},
				{name:'hrDept.hrOrg.orgname',index:'hrDept.hrOrg.orgname',align:'left',width : '150',label : '<s:text name="post.orgCode" />',hidden:false,highsearch:true},
				/* {name:'postLevel',index:'postLevel',align:'left',width : '100',label : '<s:text name="post.postLevel" />',hidden:false,highsearch:true},				
				{name:'postOrder',index:'postOrder',align:'left',width : '100',label : '<s:text name="post.postOrder" />',hidden:false,highsearch:true},				
				{name:'postSeries',index:'postSeries',align:'left',width : '100',label : '<s:text name="post.postSeries" />',hidden:false,highsearch:true},
				 */{name:'directSupervisor',index:'directSupervisor',align:'left',width : '80',label : '<s:text name="post.directSupervisor" />',hidden:false,highsearch:true},				
				/* {name:'dutySeries',index:'dutySeries',align:'left',width : '100',label : '<s:text name="post.dutySeries" />',hidden:false,highsearch:true},		
				 */{name:'basicSalary',index:'basicSalary',align:'right',width : '80',label : '<s:text name="post.basicSalary" />',hidden:false,formatter:'number',formatoptions: {thousandsSeparator:",", defaulValue:"",decimalPlaces:2},highsearch:true,},				
				{name:'maxSalary',index:'maxSalary',align:'right',width : '80',label : '<s:text name="post.maxSalary" />',hidden:false,formatter:'number',formatoptions: {thousandsSeparator:",", defaulValue:"",decimalPlaces:2},highsearch:true},				
				{name:'minSalary',index:'minSalary',align:'right',width : '80',label : '<s:text name="post.minSalary" />',hidden:false,formatter:'number',formatoptions: {thousandsSeparator:",", defaulValue:"",decimalPlaces:2},highsearch:true},				
				//{name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="post.orgCode" />',hidden:true},				
				{name:'remark',index:'remark',align:'left',width : '150',label : '<s:text name="post.remark" />',hidden:false,highsearch:true},
				{name:'lockState',index:'lockState',align:'left',width : '100',label : '<s:text name="岗位引用" />',hidden:true},
				{name:'disabled',index:'disabled',align:'center',width : '80',label : '<s:text name="post.disabled" />',hidden:false,formatter:'checkbox',highsearch:true}
	        ],
	        jsonReader : {
				root : "posts", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'hrDept.orgCode,hrDept.deptCode,code',
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
			 	gridContainerResize('post','layout');
				var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  	id=rowIds[i];
	              	  	setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPostRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
	              	}
	            }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
				
	           var dataTest = {"id":"post_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   initFlag = initColumn('post_gridtable','com.huge.ihos.hr.hrDeptment.model.Post',initFlag);
	       	} 
    	});
   		jQuery(postGrid).jqGrid('bindKeys');
   		
   		/*--------------------------------------tooBar start-------------------------------------------*/
    	var post_menuButtonArrJson = "${menuButtonArrJson}";
    	var post_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(post_menuButtonArrJson,false)));
    	var post_toolButtonBar = new ToolButtonBar({el:$('#post_buttonBar'),collection:post_toolButtonCollection,attributes:{
    		tableId : 'post_gridtable',
    		baseName : 'post',
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
    	
    	var post_function = new scriptFunction();
    	post_function.optBeforeCall = function(e,$this,param){
    		var pass = false;
    		if(param.checkPeriod){
				if('${yearStarted}'!='true'){
					alertMsg.error("本年度人力资源系统未启用。");
	    			return pass;
				}
			}
    		if(param.selectRecord){
    			var sid = jQuery("#post_gridtable").jqGrid('getGridParam','selarrrow');
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
		post_toolButtonBar.addCallBody('10010301',function(e,$this,param){
			var zTree = $.fn.zTree.getZTreeObj("hrDepartmentPostTreeLeft");  
		    var nodes = zTree.getSelectedNodes(); 
		    if(nodes.length!=1 || nodes[0].subSysTem!='DEPT'){
		    	alertMsg.error("请选择一个部门。");
	      		return;
		    }
		    if(nodes[0].actionUrl == '1'){
		    	alertMsg.error("已停用部门不能添加岗位。");
	      		return;
		    }
			var url = "editPost?navTabId=post_gridtable&deptId="+nodes[0].id;
			var winTitle='<s:text name="postNew.title"/>';
			$.pdialog.open(url,'addPost',winTitle, {mask:true,width : 680,height : 300,maxable:false,resizable:false});
		},{});
		post_toolButtonBar.addBeforeCall('10010301',function(e,$this,param){
			return post_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
		//删除
		post_toolButtonBar.addCallBody('10010302',function(e,$this,param){
			var url = "${ctx}/postGridEdit?oper=del";
			var sid = jQuery("#post_gridtable").jqGrid('getGridParam','selarrrow');
			var rowData;
			var deptIdArray=new Array();
			for(var i=0;i<sid.length;i++){
				rowData = jQuery("#post_gridtable").jqGrid('getRowData',sid[i]);
				if(rowData["disabled"]=='No'){
					alertMsg.error("只能删除已停用的岗位！");
					return;
				}
				var deptId=rowData['hrDept.departmentId'];
				deptIdArray[i]=deptId;
			} 
			url = url+"&id="+sid+"&navTabId=post_gridtable";
			alertMsg.confirm("确认删除?", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
						if(data.statusCode==200){
							var showPostCount = jQuery("#post_showPostCount").attr("checked");
							var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDepartmentPostTreeLeft");
							for (deptIdArrayIndex in deptIdArray)
							{
								var updateNode = hrDeptTreeObj.getNodeByParam("id", deptIdArray[deptIdArrayIndex], null);
								updateHrDeptPostCountSubOne("hrDepartmentPostTreeLeft",updateNode,showPostCount);
							}
						}
					});
				}
			});
		},{});
		post_toolButtonBar.addBeforeCall('10010302',function(e,$this,param){
			return post_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
		// 修改
		post_toolButtonBar.addCallBody('10010303',function(e,$this,param){
			var sid = jQuery("#post_gridtable").jqGrid('getGridParam','selarrrow');
			var winTitle='<s:text name="postEdit.title"/>';
			var url = "editPost?popup=true&id="+sid+"&navTabId=post_gridtable";
			$.pdialog.open(url,'editPost',winTitle, {mask:true,width : 680,height : 300,maxable:false,resizable:false});
		},{});
		post_toolButtonBar.addBeforeCall('10010303',function(e,$this,param){
			return post_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",singleSelect:"单选",checkPeriod:"checkPeriod"});
		
		// 启用
		post_toolButtonBar.addCallBody('10010304',function(e,$this,param){
			var sid = jQuery("#post_gridtable").jqGrid('getGridParam','selarrrow');
			var editUrl = "postGridEdit?oper=enable&navTabId=post_gridtable"
			for(var i=0;i<sid.length;i++){
				rowData = jQuery("#post_gridtable").jqGrid('getRowData',sid[i]);
				if(rowData["disabled"]=='No'){
					alertMsg.error("你选择的岗位已启用！");
					return;
				}
			} 
			editUrl += "&id="+sid;
			alertMsg.confirm("确认启用？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, formCallBack, "json");
				}
			});
		},{});
		post_toolButtonBar.addBeforeCall('10010304',function(e,$this,param){
			return post_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
		// 停用
		post_toolButtonBar.addCallBody('10010305',function(e,$this,param){
			var sid = jQuery("#post_gridtable").jqGrid('getGridParam','selarrrow');
			var editUrl = "postGridEdit?oper=disable&navTabId=post_gridtable"
			for(var i=0;i<sid.length;i++){
				rowData = jQuery("#post_gridtable").jqGrid('getRowData',sid[i]);
				if(rowData["disabled"]=='Yes'){
					alertMsg.error("你选择的岗位已停用！");
					return;
				}
			}
				jQuery.ajax({
		            url: 'checkPostRemovable',
		            data: {id:sid},
		            type: 'post',
		            dataType: 'json',
		            async:false,
		            error: function(data){
		            },
		            success: function(data){
		           		if(data){
		           			alertMsg.error("此岗位已被使用，不能停用!");
		           			return;
		           		}else{
		           			editUrl += "&id="+sid;
		        			alertMsg.confirm("确认停用？", {
		        				okCall: function(){
		        					jQuery.post(editUrl, {
		        					}, formCallBack, "json");
		        				}
		        			});
		           		}
		            }
		        });
// 				$.ajax({
// 				    url: "checkPostIsUsing?postId="+sid[i],
// 				    type: 'post',
// 				    dataType: 'json',
// 				    aysnc:false,
// 				    error: function(data){
// 				    },
// 				    success: function(data){
// 				        if(data!=null){
// 				        	alertMsg.error("你选择的岗位正在被人员使用，不能停用！");
// 							return;
// 				        }
// 				    }
// 				});
			 
			
		},{});
		post_toolButtonBar.addBeforeCall('10010305',function(e,$this,param){
			return post_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",checkPeriod:"checkPeriod"});
		//设置表格列
		var post_setColShowButton = {id:'10010306',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
   			callBody:function(){
   				setColShow('post_gridtable','com.huge.ihos.hr.hrDeptment.model.Post');
   			}};
		post_toolButtonBar.addButton(post_setColShowButton);
    	
   		/*------------------------------add Post-----------------------------------------*/
	   /* jQuery("#post_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
	    	var zTree = $.fn.zTree.getZTreeObj("hrDepartmentPostTreeLeft");  
		    var nodes = zTree.getSelectedNodes(); 
		    if(nodes.length!=1 || nodes[0].subSysTem!='DEPT'){
		    	alertMsg.error("请选择一个部门。");
	      		return;
		    }
		    if(nodes[0].actionUrl == '1'){
		    	alertMsg.error("已停用部门不能添加岗位。");
	      		return;
		    }
			var url = "editPost?navTabId=post_gridtable&deptId="+nodes[0].id;
			var winTitle='<s:text name="postNew.title"/>';
			$.pdialog.open(url,'addPost',winTitle, {mask:true,width : 680,height : 300,maxable:false,resizable:false});
		}); */
	    /*------------------------------edit Post-----------------------------------------*/
	    /*jQuery("#post_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
	        var sid = jQuery("#post_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
				}
			var winTitle='<s:text name="postEdit.title"/>';
			var url = "editPost?popup=true&id="+sid+"&navTabId=post_gridtable";
			$.pdialog.open(url,'editPost',winTitle, {mask:true,width : 680,height : 300,maxable:false,resizable:false});
		}); */
  	});
	function hrDeptTreeLeftInPost() {
		var url = "makeHrDeptTree";
		/* var showDisabled = jQuery("#post_showDisabled").attr("checked");
		if(showDisabled){
			url += "?showDisabled=true"; 
		} */
		$.get(url, {"_" : $.now()}, function(data) {
			var hrDeptTreeData = data.hrDeptTreeNodes;
			var hrDeptTree = $.fn.zTree.init($("#hrDepartmentPostTreeLeft"),ztreesetting_hrDeptTreeInPost, hrDeptTreeData);
			var nodes = hrDeptTree.getNodes();
			hrDeptTree.expandNode(nodes[0], true, false, true);
			hrDeptTree.selectNode(nodes[0]);
			toogleShowDisabledDept1(jQuery("#post_showDisabled")[0],jQuery("#post_showPostCount")[0],'hrDepartmentPostTreeLeft');
			showPostCount(jQuery("#post_showPostCount")[0]);
		});
		jQuery("#post_expandTree").text("展开");
	}
	
	var ztreesetting_hrDeptTreeInPost = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				fontCss : setDisabledDeptFontCss
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var urlString = "postGridList?1=1";
				    var nodeId = treeNode.id;
				    if(nodeId!="-1"){
				    	if(treeNode.subSysTem==='ORG'){
					    	urlString += "&orgCode="+nodeId;
				    	}else{
					    	urlString += "&deptId="+nodeId;
				    	}
				    }
				    var showDisabled = jQuery("#post_showDisabled").attr("checked");
				    if(showDisabled){
				    	urlString += "&showDisabled=true"
				    }
					urlString=encodeURI(urlString);
					jQuery("#post_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
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
	
	function showDisabledDeptInPost(obj){
		var urlString = jQuery("#post_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabled','');
		if(obj.checked){
			urlString += "&showDisabled=true";
		}
		toogleShowDisabledDept1(obj,jQuery("#post_showPostCount")[0],'hrDepartmentPostTreeLeft');
		jQuery("#post_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	function toogleShowDisabledDept1(dObj,pObj,treeId){
		var hrDeptTreeObj = $.fn.zTree.getZTreeObj(treeId);
		var nodes = hrDeptTreeObj.transformToArray(hrDeptTreeObj.getNodes());
		var node;
		if(dObj.checked){
			for(index in nodes){
				node = nodes[index];
				if(node.subSysTem!="ALL"){
					if(pObj.checked){
						node.name = node.nameWithoutPerson+"("+node.postCount+")";
					}else{
						node.name = node.nameWithoutPerson;
					}
					hrDeptTreeObj.updateNode(node);
				}
				if(node.subSysTem=="DEPT" && node.actionUrl == '1' ){
					hrDeptTreeObj.showNode(node);
				}
			}
		}else{
			for(index in nodes){
				node = nodes[index];
				if(node.subSysTem!="ALL"){
					if(pObj.checked){
						node.name = node.nameWithoutPerson+"("+node.postCountD+")";
					}else{
						node.name = node.nameWithoutPerson;
					}
					hrDeptTreeObj.updateNode(node);
				}
				if(node.subSysTem=="DEPT" && node.actionUrl == '1'){
					node.name = node.nameWithoutPerson;
					hrDeptTreeObj.hideNode(node);
				}
			}
		}
	}
	function showPostCount(obj){
		var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDepartmentPostTreeLeft");
		var nodes = hrDeptTreeObj.transformToArray(hrDeptTreeObj.getNodes());
		var node;
		var dObj = jQuery("#post_showDisabled")[0];
		if(obj.checked){
			for(index in nodes){
				node = nodes[index];
				if(node.subSysTem!="ALL"){
					if(dObj.checked){
						node.name = node.nameWithoutPerson+"("+node.postCount+")";
					}else{
						node.name = node.nameWithoutPerson+"("+node.postCountD+")";
					}
					hrDeptTreeObj.updateNode(node);
				}
			}
		}else{
			for(index in nodes){
				node = nodes[index];
				if(node.subSysTem!="ALL"){
					node.name = node.nameWithoutPerson;
					hrDeptTreeObj.updateNode(node);
				}
			}
		}
	}
	
	/*function enableOrDisablePost(type){
		var url = "${ctx}/postGridEdit?oper="+type
		var sid = jQuery("#post_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			var rowData;
			if(type=="enable"){
				for(var i=0;i<sid.length;i++){
					rowData = jQuery("#post_gridtable").jqGrid('getRowData',sid[i]);
					if(rowData["disabled"]=='No'){
						alertMsg.error("你选择的岗位已启用！");
						return;
					}
				} 
			}else if(type=="disable"){
				// 停用岗位：没有人员引用
				for(var i=0;i<sid.length;i++){
					rowData = jQuery("#post_gridtable").jqGrid('getRowData',sid[i]);
					if(rowData["disabled"]=='Yes'){
						alertMsg.error("你选择的岗位已停用！");
						return;
					}
					$.ajax({
					    url: "checkPostIsUsing?postId="+sid[i],
					    type: 'post',
					    dataType: 'json',
					    aysnc:false,
					    error: function(data){
					    },
					    success: function(data){
					        if(data!=null){
					        	alertMsg.error("你选择的岗位正在被人员使用，不能停用！");
								return;
					        }
					    }
					});
				} 
				
			}
			url = url+"&id="+sid+"&navTabId=post_gridtable";
			alertMsg.confirm("确认"+(type=='enable'?'启用':'停用'+"？"), {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
						hrDepartmentCurrentLeftTree();
					});
				}
			});
		}
	}
	
	function delPost(){
		var url = "${ctx}/postGridEdit?oper=del";
		var sid = jQuery("#post_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			var rowData;
			for(var i=0;i<sid.length;i++){
				rowData = jQuery("#post_gridtable").jqGrid('getRowData',sid[i]);
				if(rowData["disabled"]=='No'){
					alertMsg.error("只能删除已停用的岗位！");
					return;
				}
			} 
			url = url+"&id="+sid+"&navTabId=post_gridtable";
			alertMsg.confirm("确认删除?", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		}
	}*/
	function viewPostRecord(id){
		var url = "editPost?oper=view&id="+id;
		$.pdialog.open(url,'viewPost','岗位信息', {mask:true,width : 680,height : 300,maxable:false,resizable:false});
	}
	//岗位数减一
	function updateHrDeptPostCountSubOne(treeObjId,updateNode,showPostCount){
		var treeObj = $.fn.zTree.getZTreeObj(treeObjId);
		var postCount = parseInt(updateNode.postCount);
		var postCountD = parseInt(updateNode.postCountD);
		if(postCount>0){
			postCount=postCount-1;
		}
		if(postCountD>0){
			postCountD=postCountD-1;
		}
		updateNode.postCount=postCount;
		updateNode.postCountD=postCountD;
		if(showPostCount){
			updateNode.name = updateNode.nameWithoutPerson+"("+postCount+")";
		}
		treeObj.updateNode(updateNode);
		var parentNode = updateNode.getParentNode();
		if(parentNode&&parentNode.subSysTem!="ALL"){
			updateHrDeptPostCountSubOne(treeObjId,parentNode,showPostCount);
		}
	}
</script>
<div class="page">
	<div id="post_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="post_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='post.code'/>:
      					<input type="text" name="filter_LIKES_code"/>
					</label>
					<label class="queryarea-label">
						<s:text name='post.name'/>:
      					<input type="text" name="filter_LIKES_name"/>
					</label>
					<label class="queryarea-label">
						<s:text name='post.directSupervisor'/>:
      					<input type="text" name="filter_LIKES_directSupervisor"/>
					</label>
		   			<label class="queryarea-label">
						<s:text name='post.disabled'/>:
					 	<s:select name="filter_EQB_disabled" headerKey="" headerValue="--" 
							list="#{'1':'是','2':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
					</label>
					<label class="queryarea-label">
						<s:text name='post.remark'/>:
      					<input type="text" name="filter_LIKES_remark"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('post_search_form','post_gridtable');"><s:text name='button.search'/></button>
						</div>
					</div>
			</form>
			</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li> -->
<!-- 							<div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="propertyFilterSearch('post_search_form','post_gridtable');"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
<div class="panelBar" id="post_buttonBar">

			<!--<ul class="toolBar">
				<li>
					<a id="post_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a class="delbutton"  href="javaScript:delPost()"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="post_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a class="enablebutton" href="javaScript:enableOrDisablePost('enable')"><span><s:text name="button.enable" /></span> </a></li>
				<li>
					<a class="disablebutton" href="javaScript:enableOrDisablePost('disable')"><span><s:text name="button.disable" /></span> </a></li>
				<li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('post_gridtable','com.huge.ihos.hr.hrDeptment.model.Post')"><span><s:text name="button.setColShow" /></span></a>
				</li>-->
  
		</div>
		<div id="post_container">
			<div id="post_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
					<span>
						显示停用部门
						<input id="post_showDisabled" type="checkbox" onclick="showDisabledDeptInPost(this)"/>
					</span>
					<span>
						显示岗位数
						<input id="post_showPostCount" type="checkbox" onclick="showPostCount(this)"/>
					</span>
					<label id="post_expandTree" onclick="toggleExpandTree(this,'hrDepartmentPostTreeLeft')">展开</label>
				</div>
				<div id="hrDepartmentPostTreeLeft" class="ztree"></div>
			</div>
			<div id="post_layout-center" class="pane ui-layout-center">
				<div id="post_gridtable_div" class="grid-wrapdiv" 
					style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="post_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_post_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 					<table id="post_gridtable"></table>
				</div>
				<div class="panelBar"  id="post_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="post_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="post_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>

					<div id="post_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>