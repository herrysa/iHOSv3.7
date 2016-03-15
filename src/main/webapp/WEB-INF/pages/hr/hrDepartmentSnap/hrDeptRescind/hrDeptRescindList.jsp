
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var hrDeptRescindGridIdString="#hrDeptRescind_gridtable";
	jQuery(document).ready(function() { 
		jQuery("#hrDeptRescind_pageHeader").find("select").css({"font-size":"12px"});
		/*------------------------------set layout-----------------------------------------*/
		var hrDeptRescindFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('hrDeptRescind_container','hrDeptRescind_gridtable',hrDeptRescindFullSize);
		/*------------------------------load leftTree-----------------------------------------*/
		hrDeptTreeInDeptRescind();
		/*------------------------------load rightGrid-----------------------------------------*/
		var initFlag = 0;
		var hrDeptRescindGrid = jQuery(hrDeptRescindGridIdString);
    	hrDeptRescindGrid.jqGrid({
	    	url : "hrDeptRescindGridList?1=1&showDisabled=true",
	    	editurl:"hrDeptRescindGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="hrDeptRescind.id" />',hidden:true,key:true},				
				{name:'rescindNo',index:'rescindNo',align:'left',width:'120',label : '<s:text name="hrDeptRescind.rescindNo" />',hidden:false,highsearch:true},				
				{name:'hrDeptHis.name',index:'hrDeptHis.name',align:'left',width:'120',label : '<s:text name="hrDeptRescind.hrDept" />',hidden:false,highsearch:true},				
				{name:'moveToDeptHis.name',index:'moveToDeptHis.name',align:'left',width:'120',label : '<s:text name="hrDeptRescind.moveToDept" />',hidden:false,highsearch:true},				
				// {name:'rescindDate',index:'rescindDate',align:'center',width:'80',label : '<s:text name="hrDeptRescind.rescindDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'rescindReason',index:'rescindReason',align:'left',width:'250',label : '<s:text name="hrDeptRescind.rescindReason" />',hidden:false,highsearch:true},				
				<c:if test="${deptNeedCheck=='1'}">
				{name:'rescindPerson.name',index:'rescindPerson.name',align:'left',width:'60',label : '<s:text name="hrDeptRescind.rescindPerson" />',hidden:false,highsearch:true},				
				{name:'makeDate',index:'makeDate',align:'center',width:'80',label : '<s:text name="hrDeptRescind.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'checkPerson.name',index:'checkPerson.name',align:'left',width:'60',label : '<s:text name="hrDeptRescind.checkPerson" />',hidden:false,highsearch:true},				
				{name:'checkDate',index:'checkDate',align:'center',width:'80',label : '<s:text name="hrDeptRescind.checkDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				{name:'confirmPerson.name',index:'confirmPerson.name',align:'left',width:'60',label : '<s:text name="hrDeptRescind.confirmPerson" />',hidden:false,highsearch:true},				
				{name:'confirmDate',index:'confirmDate',align:'center',width:'80',label : '<s:text name="hrDeptRescind.confirmDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},				
				</c:if>
				{name:'snapCode',index:'snapCode',align:'center',label : '<s:text name="hrDeptRescind.snapCode" />',hidden:true},				
				{name:'moveToSnapCode',index:'moveToSnapCode',align:'center',label : '<s:text name="hrDeptRescind.moveToSnapCode" />',hidden:true},				
				{name:'moveToDeptHis.hrDeptPk.deptId',index:'moveToDeptHis.hrDeptPk.deptId',align:'center',label : '<s:text name="hrDeptRescind.snapCode" />',hidden:true},				
				{name:'hrDeptHis.hrDeptPk.deptId',index:'hrDeptHis.hrDeptPk.deptId',align:'center',label : '<s:text name="hrDeptRescind.snapCode" />',hidden:true},				
				{name:'state',index:'state',align:'center',width:'60',label : '<s:text name="hrDeptRescind.state" />',hidden:false,formatter:'select',editoptions : {value : '1:新建;2:已审核;3:已撤销'},highsearch:true}				
	        ],
	        jsonReader : {
				root : "hrDeptRescinds", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'rescindNo',
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
			 	gridContainerResize('hrDeptRescind','layout');
				var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  id=rowIds[i];
	              	  if(ret[i]['state']=="1"){
	              		  setCellText(this,id,'state','<span >新建</span>');
	              	  }else if(ret[i]['state']=="2"){
	              		  setCellText(this,id,'state','<span style="color:green" >已审核</span>');
	              	  }else if(ret[i]['state']=="3"){
	              		  setCellText(this,id,'state','<span style="color:blue" >已执行</span>');
	              	  }
	              	  var snapId=ret[i]["hrDeptHis.hrDeptPk.deptId"]+'_'+ret[i]["snapCode"];
		   	          setCellText(this,id,'hrDeptHis.name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrDeptRecord(\''+snapId+'\',${random});">'+ret[i]["hrDeptHis.name"]+'</a>');
	              	  snapId=ret[i]["moveToDeptHis.hrDeptPk.deptId"]+'_'+ret[i]["moveToSnapCode"];
		   	          setCellText(this,id,'moveToDeptHis.name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrDeptRecord(\''+snapId+'\',${random});">'+ret[i]["moveToDeptHis.name"]+'</a>');
		   	       	  setCellText(this,id,'rescindNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrDeptRescind(\''+id+'\',${random});">'+ret[i]["rescindNo"]+'</a>');
	                }
	            }else{
	 	               var tw = jQuery(this).outerWidth();
	 	               jQuery(this).parent().width(tw);
	 	               jQuery(this).parent().height(1);
	 	            }
	           var dataTest = {"id":"hrDeptRescind_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	 	initFlag = initColumn('hrDeptRescind_gridtable','com.huge.ihos.hr.hrDeptment.model.HrDeptRescind',initFlag);
	       	} 
	    });
	    jQuery(hrDeptRescindGrid).jqGrid('bindKeys');
	    
	    /*--------------------------------------tooBar start-------------------------------------------*/
    	var hrDeptRescind_menuButtonArrJson = "${menuButtonArrJson}";
    	var hrDeptRescind_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(hrDeptRescind_menuButtonArrJson,false)));
    	var hrDeptRescind_toolButtonBar = new ToolButtonBar({el:$('#hrDeptRescind_buttonBar'),collection:hrDeptRescind_toolButtonCollection,attributes:{
    		tableId : 'hrDeptRescind_gridtable',
    		baseName : 'hrDeptRescind',
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
    	
    	var hrDeptRescind_function = new scriptFunction();
    	hrDeptRescind_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.checkPeriod){
				if('${yearStarted}'!='true'){
					alertMsg.error("本年度人力资源系统未启用。");
	    			return pass;
				}
			}
			
			if(param.selectRecord){
				var sid = jQuery("#hrDeptRescind_gridtable").jqGrid('getGridParam','selarrrow');
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
						var row = jQuery("#hrDeptRescind_gridtable").jqGrid('getRowData',rowId);
						if(param.status=='新建'){
							if(row['state']!='1'){
								alertMsg.error("只能"+param.opt+"处于  ["+param.status+"] 状态的记录。");
								return pass;
							}
						}else{
							if(row['state']!='2'){
								alertMsg.error("只能"+param.opt+"处于  ["+param.status+"] 状态的记录。");
								return pass;
							}
						}
			        }
		        }
			}
	        return true;
		};
		//添加
		hrDeptRescind_toolButtonBar.addCallBody('100102030201',function(e,$this,param){
			var url = "editHrDeptRescind?navTabId=hrDeptRescind_gridtable";
			var zTree = $.fn.zTree.getZTreeObj("hrDeptTreeInDeptRescind"); 
			var nodes = zTree.getSelectedNodes();
			if(nodes.length!=1 || nodes[0].subSysTem !='DEPT'){
				alertMsg.error("请选择要撤销的部门。");
				return;
			}
			var node = nodes[0];
			if(node.state == '4'){
				alertMsg.error("你选择的部门已撤销。");
				return;
			}
			if(node.actionUrl == '1'){
				alertMsg.error("你选择的部门已停用。");
				return;
			}
			if(node.isParent){
				var childNodes = node.children;
				if(childNodes){
					for(var index in childNodes){
						node = childNodes[index];
						if(node.state != '4'){
							alertMsg.error("你选择的部门存在未撤销的下级部门，不能撤销。");
							return;
						}
					}
				}
			}
			$.ajax({
			    url: "checkHrDeptCanBeEdit?deptId="+node.id,
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			    },
			    success: function(data){
			        if(data!=null){
			        	alertMsg.error("你选择的"+data.message);
						return;
			        }else{
			        	url += "&deptId="+node.id;
						url=encodeURI(url);
						var winTitle='<s:text name="hrDeptRescindNew.title"/>';
						$.pdialog.open(url,'addHrDeptRescind',winTitle, {mask:true,width : 670,height : 260,maxable:false,resizable:false});
			        }
			    }
			});
		},{});
		hrDeptRescind_toolButtonBar.addBeforeCall('100102030201',function(e,$this,param){
			return hrDeptRescind_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
		//删除
		hrDeptRescind_toolButtonBar.addCallBody('100102030202',function(e,$this,param){
			var url = "${ctx}/hrDeptRescindGridEdit?oper=del"
			var sid = jQuery("#hrDeptRescind_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=hrDeptRescind_gridtable";
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		hrDeptRescind_toolButtonBar.addBeforeCall('100102030202',function(e,$this,param){
			return hrDeptRescind_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"删除",status:"新建",checkPeriod:"checkPeriod"});
		// 修改
		hrDeptRescind_toolButtonBar.addCallBody('100102030203',function(e,$this,param){
			var sid = jQuery("#hrDeptRescind_gridtable").jqGrid('getGridParam','selarrrow');
			var winTitle='<s:text name="hrDeptRescindEdit.title"/>';
			var url = "editHrDeptRescind?id="+sid+"&navTabId=hrDeptRescind_gridtable";
			$.pdialog.open(url,'editHrDeptRescind',winTitle, {mask:true,width : 670,height : 260,maxable:false,resizable:false});
		},{});
		hrDeptRescind_toolButtonBar.addBeforeCall('100102030203',function(e,$this,param){
			return hrDeptRescind_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",singleSelect:"单选",opt:"修改",status:"新建",checkPeriod:"checkPeriod"});
		//审核
		hrDeptRescind_toolButtonBar.addCallBody('100102030204',function(e,$this,param){
			var url = "${ctx}/hrDeptRescindGridEdit?oper=check"
			var sid = jQuery("#hrDeptRescind_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=hrDeptRescind_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认审核？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		hrDeptRescind_toolButtonBar.addBeforeCall('100102030204',function(e,$this,param){
			return hrDeptRescind_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"审核",status:"新建",checkPeriod:"checkPeriod"});
		//销审
		hrDeptRescind_toolButtonBar.addCallBody('100102030205',function(e,$this,param){
			var url = "${ctx}/hrDeptRescindGridEdit?oper=cancelCheck"
			var sid = jQuery("#hrDeptRescind_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=hrDeptRescind_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认销审？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		hrDeptRescind_toolButtonBar.addBeforeCall('100102030205',function(e,$this,param){
			return hrDeptRescind_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"销审",status:"已审核",checkPeriod:"checkPeriod"});
		//撤销
		hrDeptRescind_toolButtonBar.addCallBody('100102030206',function(e,$this,param){
			var url = "${ctx}/hrDeptRescindGridEdit?oper=confirm"
			var sid = jQuery("#hrDeptRescind_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=hrDeptRescind_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认执行？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
						if(data.statusCode!=200){
							return;
						}
						// 处理树节点
						//hrDeptTreeInDeptRescind();
						var deptNodes = data.deptTreeNodes;
						var deptEditNodes = data.deptEditTreeNodes;
						if(deptNodes){
							for(var deptIndex in deptNodes){
								dealHrTreeNode('hrDeptTreeInDeptRescind',deptNodes[deptIndex],'rescind','dept');
							}
						}
						if(deptEditNodes){
							for(var deptIndex in deptEditNodes){
								dealHrTreeNode('hrDeptTreeInDeptRescind',deptEditNodes[deptIndex],'editPC','dept');
							}
						}
						
						/* var rowData;
						var nodes = new Array();
						var node;
						var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDeptTreeInDeptRescind");
						for(var i=0;i<sid.length;i++){
							var rowId = sid[i];
							rowData = jQuery("#hrDeptRescind_gridtable").jqGrid('getRowData',rowId);
							var deptId = rowData['hrDeptHis.hrDeptPk.deptId'];
							node = hrDeptTreeObj.getNodeByParam("id", deptId, null);
							nodes.push(node);
						}
						var showDisabled = jQuery("#hrDeptRescind_showDisabled").attr("checked");
						for(var i=0;i<nodes.length;i++){
							node = nodes[i];
							alert(node.name);
							// 更新样式,如果当前不显示停用部门，则隐藏节点
							if(showDisabled){
															
							}else{
								hrDeptTreeObj.hideNode(node);
							}
						} */
					});
				}
			});
		},{});
		hrDeptRescind_toolButtonBar.addBeforeCall('100102030206',function(e,$this,param){
			return hrDeptRescind_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"执行",status:"已审核",checkPeriod:"checkPeriod"});
		var hrDeptRescind_setColShowButton = {id:'100102030288',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
	   			callBody:function(){
	   				setColShow('hrDeptRescind_gridtable','com.huge.ihos.hr.hrDeptment.model.HrDeptRescind');
	   			}};
		hrDeptRescind_toolButtonBar.addButton(hrDeptRescind_setColShowButton);
    	/*--------------------------------------tooBar end-------------------------------------------*/
  	});
	function viewHrDeptRescind(id){
		var winTitle='<s:text name="查看部门撤销信息"/>';
		var url = "editHrDeptRescind?oper=view&id="+id;
		$.pdialog.open(url,'viewHrDeptRescind',winTitle, {mask:true,width : 670,height : 260,maxable:false,resizable:false});
	}
	/*------------------------------tree method area-----------------------------------------*/
	function hrDeptTreeInDeptRescind() {
		var url = "makeHrDeptTree";
		$.get(url, {"_" : $.now()}, function(data) {
			var hrDeptTreeData = data.hrDeptTreeNodes;
			var hrDeptTree = $.fn.zTree.init($("#hrDeptTreeInDeptRescind"),ztreesetting_hrDeptTreeInDeptRescind, hrDeptTreeData);
			var nodes = hrDeptTree.getNodes();
			hrDeptTree.expandNode(nodes[0], true, false, true);
			hrDeptTree.selectNode(nodes[0]);
			 toggleDisabledOrCount({treeId:'hrDeptTreeInDeptRescind',
				    showCode:jQuery('#hrDeptRescind_showCode')[0],
				    disabledDept:jQuery("#hrDeptRescind_showDisabled")[0],
				    disabledPerson:false,
				    showCount:jQuery("#hrDeptRescind_showPersonCount")[0] });
// 			toogleShowDisabledDept(jQuery("#hrDeptRescind_showDisabled")[0],jQuery("#hrDeptRescind_showPersonCount")[0],'hrDeptTreeInDeptRescind');
// 			toogleShowPersonCount(jQuery("#hrDeptRescind_showDisabled")[0],jQuery("#hrDeptRescind_showPersonCount")[0],'hrDeptTreeInDeptRescind');
		});
		jQuery("#hrDeptRescind_expandTree").text("展开");
	}
	
	var ztreesetting_hrDeptTreeInDeptRescind = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : true,
			fontCss : setDisabledDeptFontCss
		},
		callback : {
			beforeDrag:function(){return false},
			onClick : function(event, treeId, treeNode, clickFlag){
				var urlString = "hrDeptRescindGridList?1=1";
			    var nodeId = treeNode.id;
			    if(nodeId!="-1"){
			    	if(treeNode.subSysTem==='ORG'){
				    	urlString += "&orgCode="+nodeId;
			    	}else{
				    	urlString += "&departmentId="+nodeId;
			    	}
			    }
			    urlString = urlString.replace('showDisabled','');
			    var showDisabled = jQuery("#hrDeptRescind_showDisabled").attr("checked");
			    if(showDisabled){
			    	urlString += "&showDisabled=true";
			    }
			    urlString=encodeURI(urlString);
			    jQuery("#hrDeptRescind_gridtable").jqGrid('setGridParam',{url:urlString}).trigger("reloadGrid");
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
	
	function showDisabledDeptInRescind(obj){
		var urlString = jQuery("#hrDeptRescind_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabled','');
		if(obj.checked){
			urlString += "&showDisabled=true";
		}
		 toggleDisabledOrCount({treeId:'hrDeptTreeInDeptRescind',
			    showCode:jQuery('#hrDeptRescind_showCode')[0],
			    disabledDept:jQuery("#hrDeptRescind_showDisabled")[0],
			    disabledPerson:false,
			    showCount:jQuery("#hrDeptRescind_showPersonCount")[0] });
		jQuery("#hrDeptRescind_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
</script>

<div class="page" id="hrDeptRescind_page">
	<div class="pageHeader" id="hrDeptRescind_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="hrDeptRescind_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='hrDeptRescind.rescindNo'/>:
      					<input type="text" name="filter_LIKES_rescindNo"/>
					</label>
					<label class="queryarea-label">
						<s:text name='hrDeptRescind.hrDept'/>:
      					<input type="text" name="filter_LIKES_hrDept.name"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='hrDeptRescind.rescindDate'/>:
      					<input type="text" id="hrDeptRescind_search_form_rescindDate_from" name="filter_GED_rescindDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'hrDeptRescind_search_form_rescindDate_to\')}'})">
						<s:text name='至'/>
					 	<input type="text" id="hrDeptRescind_search_form_rescindDate_to" name="filter_LED_rescindDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'hrDeptRescind_search_form_rescindDate_from\')}'})">
					</label> --%>
					<label class="queryarea-label">
						<s:text name='hrDeptRescind.rescindReason'/>:
      					<input type="text" name="filter_LIKES_rescindReason"/>
					</label>	
					<label class="queryarea-label">
						<s:text name='hrDeptRescind.state'/>:
						<s:select name="filter_EQI_state" list="#{'':'--','1':'新建','2':'已审核','3':'已执行'}" style="width:70px;" ></s:select>
					</label>
					<label class="queryarea-label">
						<s:select id="hrDeptRescind_searchTime"   list="#{'0':'执行日期','1':'填制日期','2':'审核日期'}" style="width:100px;font-size:9pt;" ></s:select>
						<s:text name=" 从"/>:
						<input type="text" id="hrDeptRescindbeginDate"  name="hrPerson.beginDate" onclick="changeSysTimeType('hrDeptRescind','confirmDate','makeDate','checkDate')" onblur="checkQueryDate('hrDeptRescindbeginDate','hrDeptRescindendDate',0,'hrDeptRescind_beginDate','hrDeptRescind_endDate')" style="width:65px"/>
					</label><label class="queryarea-label">
						<s:text name="至"/>:
						<input type="text" id="hrDeptRescindendDate" name="hrPerson.endDate" onclick="changeSysTimeType('hrDeptRescind','confirmDate','makeDate','checkDate')"  onblur="checkQueryDate('hrDeptRescindbeginDate','hrDeptRescindendDate',1,'hrDeptRescind_beginDate','hrDeptRescind_endDate')" style="width:65px"/>
						<input type="hidden" id="hrDeptRescind_beginDate" name="hrPerson.endDate"  />
						<input type="hidden" id="hrDeptRescind_endDate" name="hrPerson.endDate"  />
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('hrDeptRescind_search_form','hrDeptRescind_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('hrDeptRescind_search_form','hrDeptRescind_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div id="hrDeptRescind_buttonBar" class="panelBar">
			<!--<ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:addDeptRescind()" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a class="delbutton"  href="javaScript:deptRescindListEditOper('del')"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="hrDeptRescind_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a class="checkbutton"  href="javaScript:deptRescindListEditOper('check')"><span><s:text name="button.check" /></span></a>
				</li>
				<li>
					<a class="delallbutton"  href="javaScript:deptRescindListEditOper('cancelCheck')"><span><s:text name="button.cancelCheck" /></span></a>
				</li>
				<li>
					<a class="confirmbutton"  href="javaScript:deptRescindListEditOper('confirm')"><span><s:text name="撤销" /></span></a>
				</li>
			</ul>  -->
		</div>
		<div id="hrDeptRescind_container">
			<div id="hrDeptRescind_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
			
			 <div class="treeTopCheckBox">
     			<span>
      				显示机构编码
      		   <input id="hrDeptRescind_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrDeptTreeInDeptRescind',showCode:this,disabledDept:jQuery('#hrDeptRescind_showDisabled')[0],disabledPerson:false,showCount:jQuery('#hrDeptRescind_showPersonCount')[0] })"/>
     		   </span>
     		<span>
      				显示人员数
      			<input id="hrDeptRescind_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrDeptTreeInDeptRescind',showCode:jQuery('#hrDeptRescind_showCode')[0],disabledDept:jQuery('#hrDeptRescind_showDisabled')[0],disabledPerson:false,showCount:this })"/>
     		</span>
     		<label id="hrDeptRescind_expandTree" onclick="toggleExpandTree(this,'hrDeptTreeInDeptRescind')">展开</label>
    		</div>
    		<div class="treeTopCheckBox">
     		<span>
      			显示停用部门
     		 <input id="hrDeptRescind_showDisabled" type="checkbox" checked='checked' onclick="showDisabledDeptInRescind(this)"/>
    		 </span>
    		 </div>
    		 <div class="treeTopCheckBox">
    			 <span>
     			 快速查询：
     			 <input type="text" onKeyUp="searchTree('hrDeptTreeInDeptRescind',this)"/>
     			</span>
    		</div>
				<div id="hrDeptTreeInDeptRescind" class="ztree"></div>
			</div>
			<div id="hrDeptRescind_layout-center" class="pane ui-layout-center">
				<div id="hrDeptRescind_gridtable_div" class="grid-wrapdiv"
					style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="hrDeptRescind_gridtable_navTabId" value="${sessionScope.navTabId}">
					<label style="display: none" id="hrDeptRescind_gridtable_addTile"> 
						<s:text name="hrDeptRescindNew.title" />
					</label> 
					<label style="display: none" id="hrDeptRescind_gridtable_editTile"> 
						<s:text name="hrDeptRescindEdit.title" />
					</label> 
					<div id="load_hrDeptRescind_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
		 			<table id="hrDeptRescind_gridtable"></table>
				</div>
				<div class="panelBar"  id="hrDeptRescind_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="hrDeptRescind_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="hrDeptRescind_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="hrDeptRescind_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>