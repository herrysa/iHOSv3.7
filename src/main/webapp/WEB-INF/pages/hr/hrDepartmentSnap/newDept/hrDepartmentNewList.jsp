<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var hrDepartmentNewGridIdString="#hrDepartmentNew_gridtable";
	
	jQuery(document).ready(function() {
		jQuery("#hrDepartmentNew_pageHeader").find("select").css("font-size","12px");
		/*------------------------------set layout-----------------------------------------*/
		var hrDepartmentNewFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('hrDepartmentNew_container','hrDepartmentNew_gridtable',hrDepartmentNewFullSize);
		/*------------------------------load leftTree-----------------------------------------*/
		hrDepartmentNewLeftTree();
		/*------------------------------load rightGrid-----------------------------------------*/
		var initFlag = 0;
		var hrDepartmentNewGrid = jQuery(hrDepartmentNewGridIdString);
    	hrDepartmentNewGrid.jqGrid({
	    	url : "hrDepartmentSnapGridList?1=1",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'snapId',index:'snapId',align:'left',width:'2',label : '<s:text name="hrDepartmentSnap.snapId" />',hidden:true,key:true,highsearch:false},				
				{name:'deptCode',index:'deptCode',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.deptCode" />',hidden:false,highsearch:true},				
				{name:'name',index:'name',align:'left',width:'120',label : '<s:text name="hrDepartmentSnap.name" />',hidden:false,highsearch:true},				
				{name:'hrOrg.orgname',index:'hrOrg.orgname',align:'left',width:'150',label : '<s:text name="hrDepartmentSnap.orgName" />',hidden:false,highsearch:true},				
				{name:'shortName',index:'shortName',align:'left',width:'120',label : '<s:text name="hrDepartmentSnap.shortName" />',hidden:false,highsearch:true},				
				{name:'deptType',index:'deptType',align:'center',width:'70',label : '<s:text name="hrDepartmentSnap.deptType" />',hidden:false,highsearch:true},				
				{name:'outin',index:'outin',align:'center',width:'70',label : '<s:text name="hrDepartmentSnap.outin" />',hidden:false,highsearch:true},				
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
				{name:'disabled',index:'disabled',align:'center',width:'50',label : '<s:text name="hrDepartmentSnap.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},				
				{name:'state',index:'state',align:'center',width:'60',label : '<s:text name="hrDepartmentSnap.state" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '1:新建;2:已审核;3:新增成功'}}				
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
 		              		  setCellText(this,id,'state','<span style="color:blue" >新增成功</span>');
 		              	}
 		   	        	setCellText(this,id,'name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrDeptRecord(\''+id+'\',${random});">'+ret[i]["name"]+'</a>');
 	                }
 	               	var ztree = $.fn.zTree.getZTreeObj("hrDepartmentNewLeftTree");
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
	            var dataTest = {"id":"hrDepartmentNew_gridtable"};
	      	    jQuery.publish("onLoadSelect",dataTest,null);
	      	    initFlag = initColumn('hrDepartmentNew_gridtable','com.huge.ihos.hr.hrDeptment.model.HrDepartmentNew',initFlag);
	       	} 
    	});
    	jQuery(hrDepartmentNewGrid).jqGrid('bindKeys');
    	/*--------------------------------------tooBar start-------------------------------------------*/
    	var hrDepartmentNew_menuButtonArrJson = "${menuButtonArrJson}";
    	var hrDepartmentNew_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(hrDepartmentNew_menuButtonArrJson,false)));
    	var hrDepartmentNew_toolButtonBar = new ToolButtonBar({el:$('#hrDepartmentNew_toolbuttonbar'),collection:hrDepartmentNew_toolButtonCollection,attributes:{
    		tableId : 'hrDepartmentNew_gridtable',
    		baseName : 'hrDepartmentNew',
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
    	
    	var hrDepartmentNew_function = new scriptFunction();
    	hrDepartmentNew_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			var sid = jQuery("#hrDepartmentNew_gridtable").jqGrid('getGridParam','selarrrow');
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
					var row = jQuery("#hrDepartmentNew_gridtable").jqGrid('getRowData',rowId);
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
	        return true;
		};
		//添加
		hrDepartmentNew_toolButtonBar.addCallBody('100102030101',function(e,$this,param){
			var url = "editHrDepartmentSnap?popup=true&navTabId=hrDepartmentNew_gridtable&random=${random}&addFrom=new";
    		var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDepartmentNewLeftTree");
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
    		$.pdialog.open(url,'addHrDepartmentNew',winTitle, {mask:true,width : 680,height : 518,maxable:false,resizable:false});
		},{});
		//删除
		hrDepartmentNew_toolButtonBar.addCallBody('100102030102',function(e,$this,param){
			var url = "${ctx}/hrDepartmentSnapGridEdit?oper=del"
			var sid = jQuery("#hrDepartmentNew_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=hrDepartmentNew_gridtable";
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
						var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDepartmentNewLeftTree");
						for(var i=0;i<sid.length;i++){
							var delId = sid[i].substring(0,sid[i].lastIndexOf('_'));
							var node = hrDeptTreeObj.getNodeByParam("id", delId, null);
							hrDeptTreeObj.removeNode(node);
						}
					});
				}
			});
		},{});
		hrDepartmentNew_toolButtonBar.addBeforeCall('100102030102',function(e,$this,param){
			return hrDepartmentNew_function.optBeforeCall(e,$this,param);
    	},{opt:"删除",status:"新建"});
		// 修改
		hrDepartmentNew_toolButtonBar.addCallBody('100102030103',function(e,$this,param){
			var sid = jQuery("#hrDepartmentNew_gridtable").jqGrid('getGridParam','selarrrow');
    		var winTitle='<s:text name="hrDepartmentSnapEdit.title"/>';
    		var url = "editHrDepartmentSnap?popup=true&snapId="+sid+"&navTabId=hrDepartmentNew_gridtable&random=${random}";
    		$.pdialog.open(url,'editHrDepartmentNew',winTitle, {mask:true,width : 680,height : 518,maxable:false,resizable:false});
   
		},{});
		hrDepartmentNew_toolButtonBar.addBeforeCall('100102030103',function(e,$this,param){
			return hrDepartmentNew_function.optBeforeCall(e,$this,param);
    	},{singleSelect:"单选",opt:"修改",status:"新建"});
		//审核
		hrDepartmentNew_toolButtonBar.addCallBody('100102030104',function(e,$this,param){
			var url = "${ctx}/hrDepartmentSnapGridEdit?oper=check"
			var sid = jQuery("#hrDepartmentNew_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=hrDepartmentNew_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认审核？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		hrDepartmentNew_toolButtonBar.addBeforeCall('100102030104',function(e,$this,param){
			return hrDepartmentNew_function.optBeforeCall(e,$this,param);
    	},{opt:"审核",status:"新建"});
		//销审
		hrDepartmentNew_toolButtonBar.addCallBody('100102030105',function(e,$this,param){
			var url = "${ctx}/hrDepartmentSnapGridEdit?oper=cancelCheck"
			var sid = jQuery("#hrDepartmentNew_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=hrDepartmentNew_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认销审？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		hrDepartmentNew_toolButtonBar.addBeforeCall('100102030105',function(e,$this,param){
			return hrDepartmentNew_function.optBeforeCall(e,$this,param);
    	},{opt:"销审",status:"已审核"});
		//确认
		hrDepartmentNew_toolButtonBar.addCallBody('100102030106',function(e,$this,param){
			var url = "${ctx}/hrDepartmentSnapGridEdit?oper=confirm"
			var sid = jQuery("#hrDepartmentNew_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=hrDepartmentNew_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认执行？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		hrDepartmentNew_toolButtonBar.addBeforeCall('100102030106',function(e,$this,param){
			return hrDepartmentNew_function.optBeforeCall(e,$this,param);
    	},{opt:"执行",status:"已审核"});
		//设置表格列
		var hrDepartmentNew_setColShowButton = {id:'100102030188',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
   			callBody:function(){
   				setColShow('hrDepartmentNew_gridtable','com.huge.ihos.hr.hrDeptment.model.HrDepartmentNew');
   			}};
		hrDepartmentNew_toolButtonBar.addButton(hrDepartmentNew_setColShowButton);
    	/*--------------------------------------tooBar end-------------------------------------------*/
  	});
	
	function hrDepartmentNewLeftTree() {
		var url = "makeHrDeptSnapTree";
		/* var showDisabled = jQuery("#hrDepartmentNew_showDisabled").attr("checked");
	    if(showDisabled){
		   url += "?showDisabled=true"
	    } */
		$.get(url, {"_" : $.now()}, function(data) {
			var hrDeptTreeData = data.hrDeptHisTreeNodes;
			var hrDeptTree = $.fn.zTree.init($("#hrDepartmentNewLeftTree"),ztreesetting_hrDeptTreeNew, hrDeptTreeData);
			var nodes = hrDeptTree.getNodes();
			hrDeptTree.expandNode(nodes[0], true, false, true);
			hrDeptTree.selectNode(nodes[0]);
			toogleShowDisabledDept(jQuery("#hrDepartmentNew_showDisabled")[0],jQuery("#hrDepartmentNew_showPersonCount")[0],'hrDepartmentNewLeftTree');
			toogleShowPersonCount(jQuery("#hrDepartmentNew_showDisabled")[0],jQuery("#hrDepartmentNew_showPersonCount")[0],'hrDepartmentNewLeftTree');
		});
		jQuery("#hrDepartmentNew_expandTree").text("展开");
	}
	var ztreesetting_hrDeptTreeNew = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				fontCss : setDisabledDeptFontCss
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var urlString = "hrDepartmentSnapGridList?1=1";
				    var nodeId = treeNode.id;
				    if(nodeId!="-1"){
				    	if(treeNode.subSysTem==='ORG'){
					    	urlString += "&orgCode="+nodeId;
				    	}else{
					    	urlString += "&snapId="+(nodeId+'_'+treeNode.snapCode);
				    	}
				    }
				    var showDisabled = jQuery("#hrDepartmentNew_showDisabled").attr("checked");
				    if(showDisabled){
				    	urlString += "&showDisabled=true"
				    }
					urlString=encodeURI(urlString);
					jQuery("#hrDepartmentNew_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
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
	
	function showDisabledDeptNew(obj){
		var urlString = jQuery("#hrDepartmentNew_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabled','');
		if(obj.checked){
			urlString += "&showDisabled=true";
		}
		toogleShowDisabledDept(obj,jQuery("#hrDepartmentNew_showPersonCount")[0],'hrDepartmentNewLeftTree');
		jQuery("#hrDepartmentNew_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
</script>

<div class="page" id="hrDepartmentNew_page">
	<div id="hrDepartmentNew_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="hrDepartmentNew_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrDepartmentSnap.deptCode'/>:
					 	<input type="text"  name="filter_LIKES_deptCode"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrDepartmentSnap.name'/>:
					 	<input type="text"  name="filter_LIKES_name" />
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrDepartmentSnap.deptType'/>:
					 	<s:select name="filter_EQS_deptType" headerKey="" headerValue="--" 
							list="deptTypeList" listKey="deptTypeName" listValue="deptTypeName"
							maxlength="20" emptyOption="false" theme="simple">
						</s:select>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrDepartmentSnap.subClass'/>:
					 	<s:select name="filter_EQS_subClass"  headerKey="" headerValue="--" 
							list="deptSubClassList"  listKey="value" listValue="content" 
							emptyOption="false" theme="simple" maxlength="20">
						</s:select> 
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrDepartmentSnap.jjDeptType'/>:
					 	<s:select name="filter_EQS_jjDeptType.khDeptTypeId" headerKey="" headerValue="--" 
							list="jjDeptTypeList" listKey="khDeptTypeId" listValue="khDeptTypeName"
							emptyOption="false"  maxlength="10" theme="simple">
						</s:select>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrDepartmentSnap.disabled'/>:
					 	<s:select name="filter_EQB_disabled" headerKey="" headerValue="--" 
							list="#{'1':'是','2':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple">
						</s:select>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrDepartmentSnap.state'/>:
					 	<s:select name="filter_EQI_state" headerKey="" headerValue="--" 
							list="#{1:'新建',2:'已审核',3:'新增成功' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple">
						</s:select>
					</label>&nbsp;&nbsp;
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('hrDepartmentNew_search_form','hrDepartmentNew_gridtable');"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="propertyFilterSearch('hrDepartmentNew_search_form','hrDepartmentNew_gridtable');"><s:text name='button.search'/></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div id="hrDepartmentNew_toolbuttonbar" class="panelBar">
			<!-- <ul class="toolBar">
				<li>
					<a id="hrDepartmentNew_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a id="hrDepartmentNew_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="hrDepartmentNew_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a class="checkbutton"  href="javaScript:hrDeptNewListEditOper('check')"><span><s:text name="button.check" /></span></a>
				</li>
				<li>
					<a class="delallbutton"  href="javaScript:hrDeptNewListEditOper('cancelCheck')"><span><s:text name="button.cancelCheck" /></span></a>
				</li>
				<li>
					<a class="confirmbutton"  href="javaScript:hrDeptNewListEditOper('confirm')"><span><s:text name="确认" /></span></a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('hrDepartmentNew_gridtable','com.huge.ihos.hr.hrDeptment.model.HrDepartmentNew')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			</ul> -->
		</div>
		<div id="hrDepartmentNew_container">
			<div id="hrDepartmentNew_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
					<span>
						显示停用部门
						<input id="hrDepartmentNew_showDisabled" type="checkbox" onclick="showDisabledDeptNew(this)"/>
					</span>
					<span>
						显示人员数
						<input id="hrDepartmentNew_showPersonCount" type="checkbox" onclick="toogleShowPersonCount(jQuery('#hrDepartmentNew_showDisabled')[0],this,'hrDepartmentNewLeftTree')"/>
					</span>
					<label id="hrDepartmentNew_expandTree" onclick="toggleExpandTree(this,'hrDepartmentNewLeftTree')">展开</label>
				</div>
				<div id="hrDepartmentNewLeftTree" class="ztree"></div>
			</div>
			<div id="hrDepartmentNew_layout-center" class="pane ui-layout-center">
				<div id="hrDepartmentNew_gridtable_div" layoutH="118" class="grid-wrapdiv" class="unitBox" style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="hrDepartmentNew_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_hrDepartmentNew_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
		 			<table id="hrDepartmentNew_gridtable"></table>
				</div>
				<div class="panelBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="hrDepartmentNew_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="hrDepartmentNew_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="hrDepartmentNew_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div><!-- center -->
		</div><!-- layout -->
	</div>
</div>
