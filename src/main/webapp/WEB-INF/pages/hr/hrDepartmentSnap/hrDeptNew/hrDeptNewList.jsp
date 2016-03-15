
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var hrDeptNewLayout;
	var hrDeptNewGridIdString="#hrDeptNew_gridtable";
	
	jQuery(document).ready(function() { 
		jQuery("#hrDeptNew_pageHeader").find("select").css("font-size","12px");
		/*------------------------------set layout-----------------------------------------*/
		var hrDeptNewFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('hrDeptNew_container','hrDeptNew_gridtable',hrDeptNewFullSize);
		/*------------------------------load leftTree-----------------------------------------*/
		hrDeptNewLeftTree();
		/*------------------------------load rightGrid-----------------------------------------*/
		var initFlag = 0;
		var hrDeptNewGrid = jQuery(hrDeptNewGridIdString);
    	hrDeptNewGrid.jqGrid({
    		url : "hrDeptNewGridList?1=1",
    		editurl:"hrDeptNewGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="hrDeptNew.id" />',hidden:true,key:true},
{name:'newNo',index:'newNo',align:'left',width:'120',label : '<s:text name="hrDeptNew.newNo" />',hidden:false,highsearch:true},
{name:'deptCode',index:'deptCode',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.deptCode" />',hidden:false,highsearch:true},				
{name:'name',index:'name',align:'left',width:'120',label : '<s:text name="hrDepartmentSnap.name" />',hidden:false,highsearch:true},				
{name:'hrOrg.orgname',index:'hrOrg.orgname',align:'left',width:'150',label : '<s:text name="hrDepartmentSnap.orgName" />',hidden:false,highsearch:true},				
{name:'shortnName',index:'shortnName',align:'left',width:'120',label : '<s:text name="hrDepartmentSnap.shortName" />',hidden:false,highsearch:true},				
{name:'deptClass',index:'deptClass',align:'center',width:'70',label : '<s:text name="hrDepartmentSnap.deptType" />',hidden:false,highsearch:true},				
{name:'outin',index:'outin',align:'center',width:'70',label : '<s:text name="hrDepartmentSnap.outin" />',hidden:false,highsearch:true},				
{name:'clevel',index:'clevel',align:'center',width:'50',label : '<s:text name="hrDepartmentSnap.clevel" />',hidden:false,formatter:'integer',highsearch:true},				
{name:'leaf',index:'leaf',align:'center',width:'50',label : '<s:text name="hrDepartmentSnap.leaf" />',hidden:false,formatter:'checkbox',highsearch:true},				
{name:'parentDeptHis.name',index:'parentDeptHis.name',width:'100',align:'left',label : '<s:text name="hrDepartmentSnap.parentDept" />',hidden:false,highsearch:true},				
{name:'subClass',index:'subClass',align:'center',width:'100',label : '<s:text name="hrDepartmentSnap.subClass" />',hidden:false,highsearch:true},				
{name:'cnCode',index:'cnCode',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.cnCode" />',hidden:false,highsearch:true},				
{name:'jjDeptType.khDeptTypeName',index:'jjDeptType.khDeptTypeName',align:'center',width:'100',label : '<s:text name="hrDepartmentSnap.jjDeptType" />',hidden:false,highsearch:true},				
{name:'internalCode',index:'internalCode',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.internalCode" />',hidden:false,highsearch:true},				
{name:'manager',index:'manager',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.manager" />',hidden:false,highsearch:true},
{name:'orgCode',index:'orgCode',align:'left',width:'100',label : '<s:text name="hrDepartmentSnap.orgCode" />',hidden:true},				
<c:if test="${deptNeedCheck=='1'}">
{name:'makePerson.name',index:'makePerson.name',align:'left',width:'60',label : '<s:text name="hrDeptNew.makePerson" />',hidden:false},				
{name:'makeDate',index:'makeDate',align:'center',width:'80',label : '<s:text name="hrDeptNew.makeDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
{name:'checkPerson.name',index:'checkPerson.name',align:'left',width:'60',label : '<s:text name="hrDeptNew.checkPerson" />',hidden:false,highsearch:true},				
{name:'checkDate',index:'checkDate',align:'center',width:'80',label : '<s:text name="hrDeptNew.checkDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
{name:'confirmPerson.name',index:'confirmPerson.name',align:'left',width:'60',label : '<s:text name="hrDeptNew.confirmPerson" />',hidden:false,highsearch:true},				
{name:'confirmDate',index:'confirmDate',align:'center',width:'80',label : '<s:text name="hrDeptNew.confirmDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
</c:if>
{name:'state',index:'state',align:'center',width:'60',label : '<s:text name="hrDepartmentSnap.state" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '1:新建;2:已审核;3:已执行'}},			
{name:'remark',index:'remark',align:'left',width:'120',label : '<s:text name="hrDepartmentSnap.remark" />',hidden:false,highsearch:true}
],
        	jsonReader : {
				root : "hrDeptNews", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'newNo',
        	viewrecords: true,
        	sortorder: 'desc',
        	//caption:'<s:text name="hrDeptNewList.title" />',
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
		 		gridContainerResize('hrDeptNew','layout');
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
 	                	setCellText(this,id,'newNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewhrDeptNewRecord(\''+id+'\');">'+ret[i]["newNo"]+'</a>');
 	                }
 	            }else{
 	               var tw = jQuery(this).outerWidth();
 	               jQuery(this).parent().width(tw);
 	               jQuery(this).parent().height(1);
 	            }
           	var dataTest = {"id":"hrDeptNew_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	makepager("hrDeptNew_gridtable");
       		} 

    	});
    jQuery(hrDeptNewGrid).jqGrid('bindKeys');
  	});
	/*--------------------------------------tooBar start-------------------------------------------*/
	var hrDeptNew_menuButtonArrJson = "${menuButtonArrJson}";
	var hrDeptNew_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(hrDeptNew_menuButtonArrJson,false)));
	var hrDeptNew_toolButtonBar = new ToolButtonBar({el:$('#hrDeptNew_buttonBar'),collection:hrDeptNew_toolButtonCollection,attributes:{
		tableId : 'hrDeptNew_gridtable',
		baseName : 'hrDeptNew',
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
	
	var hrDeptNew_function = new scriptFunction();
	hrDeptNew_function.optBeforeCall = function(e,$this,param){
		var pass = false;
		if(param.checkPeriod){
			if('${yearStarted}'!='true'){
				alertMsg.error("本年度人力资源系统未启用。");
    			return pass;
			}
		}
		if(param.selectRecord){
			var sid = jQuery("#hrDeptNew_gridtable").jqGrid('getGridParam','selarrrow');
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
					var row = jQuery("#hrDeptNew_gridtable").jqGrid('getRowData',rowId);
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
	function viewhrDeptNewRecord(id){
		var url = "editHrDeptNew?oper=view&id="+id;
		$.pdialog.open(url,'viewHrDeptNew','部门新增信息', {mask:true,width : 680,height : 518,maxable:false,resizable:false});
	}
	//添加
	hrDeptNew_toolButtonBar.addCallBody('100102030101',function(e,$this,param){
		var url = "editHrDeptNew?popup=true&navTabId=hrDeptNew_gridtable";
		var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDeptNewLeftTree");
		var nodes = hrDeptTreeObj.getSelectedNodes();
		if(nodes.length==1 && nodes[0].subSysTem!='ALL'){
			var node = nodes[0];
			if(node.subSysTem==='ORG'){// org
				url += "&orgCode="+node.id;
				url=encodeURI(url);
				var winTitle='<s:text name="新增部门"/>';
				$.pdialog.open(url,'addHrDeptNew',winTitle, {mask:true,width : 680,height : 518,maxable:false,resizable:false});
			}else if(node.subSysTem==='DEPT'){//dept
				// 判断选择的部门能否作为新部门的上级部门
				if(!node.isParent &&　node.personCount!='0'){
						alertMsg.error("您选择的部门下有人员，不能作为上级部门。");
		    			return;
					}
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
				        	url += "&parentDeptId="+node.id;
				        	var winTitle='<s:text name="新增部门"/>';
							$.pdialog.open(url,'addHrDeptNew',winTitle, {mask:true,width : 680,height : 518,maxable:false,resizable:false});
						}
				    }
				});
				
			}
		}else{
			alertMsg.error("请选择单位或部门。");
			return;
		}
	},{});
	hrDeptNew_toolButtonBar.addBeforeCall('100102030101',function(e,$this,param){
		return hrDeptNew_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
	//删除
	hrDeptNew_toolButtonBar.addCallBody('100102030102',function(e,$this,param){
		var url = "${ctx}/hrDeptNewGridEdit?oper=del";
		var sid = jQuery("#hrDeptNew_gridtable").jqGrid('getGridParam','selarrrow');
		url = url+"&id="+sid+"&navTabId=hrDeptNew_gridtable";
		alertMsg.confirm("确认删除？", {
			okCall : function() {
				$.post(url,function(data) {
					formCallBack(data);
				});
			}
		});
	},{});
	hrDeptNew_toolButtonBar.addBeforeCall('100102030102',function(e,$this,param){
		return hrDeptNew_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",opt:"删除",status:"新建",checkPeriod:"checkPeriod"});
	// 修改
	hrDeptNew_toolButtonBar.addCallBody('100102030103',function(e,$this,param){
		var sid = jQuery("#hrDeptNew_gridtable").jqGrid('getGridParam','selarrrow');
	 	if(sid==null || sid.length !=1){
       		alertMsg.error("请选择一条记录。");
			return;
		}
       	var row = jQuery("#hrDeptNew_gridtable").jqGrid('getRowData',sid[0]);
       	if(row['state']!='1'){
			alertMsg.error("只能修改处于新建状态的记录!");
			return;
		}
		var winTitle='<s:text name="hrDeptNewEdit.title"/>';
		var url = "editHrDeptNew?popup=true&id="+sid+"&navTabId=hrDeptNew_gridtable";
		$.pdialog.open(url,'editHrDeptNew',winTitle, {mask:true,width : 680,height : 518,maxable:false,resizable:false});

	},{});
	hrDeptNew_toolButtonBar.addBeforeCall('100102030103',function(e,$this,param){
		return hrDeptNew_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",singleSelect:"单选",opt:"修改",status:"新建",checkPeriod:"checkPeriod"});
	//审核
	hrDeptNew_toolButtonBar.addCallBody('100102030104',function(e,$this,param){
		var url = "${ctx}/hrDeptNewGridEdit?oper=check"
		var sid = jQuery("#hrDeptNew_gridtable").jqGrid('getGridParam','selarrrow');
		url = url+"&id="+sid+"&navTabId=hrDeptNew_gridtable";
		url=encodeURI(url);
		alertMsg.confirm("确认审核？", {
			okCall : function() {
				$.post(url,function(data) {
					formCallBack(data);
				});
			}
		});
	},{});
	hrDeptNew_toolButtonBar.addBeforeCall('100102030104',function(e,$this,param){
		return hrDeptNew_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",opt:"审核",status:"新建",checkPeriod:"checkPeriod"});
	//销审
	hrDeptNew_toolButtonBar.addCallBody('100102030105',function(e,$this,param){
		var url = "${ctx}/hrDeptNewGridEdit?oper=cancelCheck";
		var sid = jQuery("#hrDeptNew_gridtable").jqGrid('getGridParam','selarrrow');
		url = url+"&id="+sid+"&navTabId=hrDeptNew_gridtable";
		url=encodeURI(url);
		alertMsg.confirm("确认销审？", {
			okCall : function() {
				$.post(url,function(data) {
					formCallBack(data);
				});
			}
		});
	},{});
	hrDeptNew_toolButtonBar.addBeforeCall('100102030105',function(e,$this,param){
		return hrDeptNew_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",opt:"销审",status:"已审核",checkPeriod:"checkPeriod"});
	//确认
	hrDeptNew_toolButtonBar.addCallBody('100102030106',function(e,$this,param){
		var url = "${ctx}/hrDeptNewGridEdit?oper=confirm"
		var sid = jQuery("#hrDeptNew_gridtable").jqGrid('getGridParam','selarrrow');
		for(var i = 0;i < sid.length; i++){
			var rowId = sid[i];
			var row = jQuery("#hrDeptNew_gridtable").jqGrid('getRowData',rowId);
			var deptCode = row['deptCode'];
			var name = row['name'];
			var orgCode = row['orgCode'];
			var deptCodeExist = checkHrDeptNewListDuplicateField(deptCode,'deptCode',orgCode);
			var deptNameExist = checkHrDeptNewListDuplicateField(name,'name',orgCode);
			if(deptCodeExist){
				alertMsg.error("部门编码已存在。");
				return;
			}
			if(deptNameExist){
				alertMsg.error("部门名称已存在。");
				return;
			}
        }
		url = url+"&id="+sid+"&navTabId=hrDeptNew_gridtable";
		url=encodeURI(url);
		alertMsg.confirm("确认执行？", {
			okCall : function() {
				$.post(url,function(data) {
					formCallBack(data);
					var deptNodes = data.deptTreeNodes;
					for(var deptIndex in deptNodes){
						dealHrTreeNode('hrDeptNewLeftTree',deptNodes[deptIndex],'add','dept');
					}
				});
			}
		});
	},{});
	hrDeptNew_toolButtonBar.addBeforeCall('100102030106',function(e,$this,param){
		return hrDeptNew_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",opt:"执行",status:"已审核",checkPeriod:"checkPeriod"});
	//设置表格列
	var hrDeptNew_setColShowButton = {id:'100102030188',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
			callBody:function(){
				setColShow('hrDeptNew_gridtable','com.huge.ihos.hr.hrDeptment.model.HrDeptNew');
			}};
	hrDeptNew_toolButtonBar.addButton(hrDeptNew_setColShowButton);
	/*--------------------------------------tooBar end-------------------------------------------*/
	//});

function hrDeptNewLeftTree() {
	var url = "makeHrDeptTree";
	$.get(url, {"_" : $.now()}, function(data) {
		var hrDeptTreeData = data.hrDeptTreeNodes;
		var hrDeptTree = $.fn.zTree.init($("#hrDeptNewLeftTree"),ztreesetting_hrDeptTreeNew, hrDeptTreeData);
		var nodes = hrDeptTree.getNodes();
		hrDeptTree.expandNode(nodes[0], true, false, true);
		hrDeptTree.selectNode(nodes[0]);
		toggleDisabledOrCount({treeId:'hrDeptNewLeftTree',
			showCode:jQuery('#hrDeptNew_showCode')[0],
			disabledDept:jQuery("#hrDeptNew_showDisabled")[0],
			disabledPerson:false,
			showCount:jQuery("#hrDeptNew_showPersonCount")[0] });
	});
	jQuery("#hrDeptNew_expandTree").text("展开");
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
				var urlString = "hrDeptNewGridList?1=1";
				urlString = urlString.replace('showDisabled','');
				var showDisabled = jQuery("#hrDeptNew_showDisabled").attr("checked");
				if(showDisabled){
					urlString += "&showDisabled=true";
				}
			    var nodeId = treeNode.id;
			    if(nodeId!="-1"){
			    	if(treeNode.subSysTem==='ORG'){// org
			    		urlString += "&orgCode="+nodeId;
					}else{
						urlString += "&departmentId="+nodeId;
					}
			    }
				urlString=encodeURI(urlString);
				jQuery("#hrDeptNew_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
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

function showDisabledDeptNew(){
	var urlString = jQuery("#hrDeptNew_gridtable").jqGrid('getGridParam',"url");
	urlString = urlString.replace('showDisabled','');
	var showDisabled = jQuery("#hrDeptNew_showDisabled").attr("checked");
	if(showDisabled){
		urlString += "&showDisabled=true";
	}
	toggleDisabledOrCount({treeId:'hrDeptNewLeftTree',
			showCode:jQuery('#hrDeptNew_showCode')[0],
			disabledDept:jQuery("#hrDeptNew_showDisabled")[0],
			disabledPerson:false,
			showCount:jQuery("#hrDeptNew_showPersonCount")[0] });
	jQuery("#hrDeptNew_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
}

/*检查部门编码和名称*/
function checkHrDeptNewListDuplicateField(fieldValue,fieldName,orgCode){
	if(!fieldValue){
		 alertMsg.error("编码或名称为空。");
	     return true;
	}
	var returnMessage = "该部门";
	if("deptCode"===fieldName){
		returnMessage += "编码:"+fieldValue+",";
	}else if("name"===fieldName){
		returnMessage += "名称:"+fieldValue+",";
	}
	returnMessage += "已存在。";
	$.ajax({
	    url: "checkHrDeptSnapDuplicateField",
	    type: 'post',
	    data:{fieldName:fieldName,fieldValue:fieldValue,orgCode:orgCode,returnMessage:returnMessage},
	    dataType: 'json',
	    aysnc:false,
	    error: function(data){
	        
	    },
	    success: function(data){
	        if(data!=null){
	        	 alertMsg.error(data.message);
			     return true;
	        }
	    }
	});
	return false;
}
</script>

<div class="page" id="hrDeptNew_page">
	<div id="hrDeptNew_pageHeader" class="pageHeader">
	<div class="searchBar">
			<div class="searchContent">
				<form id="hrDeptNew_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.deptCode'/>:
					 	<input type="text"  name="filter_LIKES_deptCode"/>
					</label>
					<label class="queryarea-label">
						<s:text name='hrDepartmentSnap.name'/>:
					 	<input type="text"  name="filter_LIKES_name" />
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
						<s:text name='hrDepartmentSnap.state'/>:
					 	<s:select name="filter_EQI_state" headerKey="" headerValue="--" 
							list="#{1:'新建',2:'已审核',3:'已执行' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple">
						</s:select>
					</label>
					<label class="queryarea-label">
						<s:select id="hrDepartmentSnap_searchTime"   list="#{'0':'执行日期','1':'填制日期','2':'审核日期'}" style="width:100px;font-size:9pt;" ></s:select>
						<s:text name=" 从"/>:
						<input type="text" id="hrDepartmentSnapbeginDate"  name="hrPerson.beginDate" onclick="changeSysTimeType('hrDepartmentSnap','confirmDate','makeDate','checkDate')" onblur="checkQueryDate('hrDepartmentSnapbeginDate','hrDepartmentSnapendDate',0,'hrDepartmentSnap_beginDate','hrDepartmentSnap_endDate')" style="width:65px"/>
					</label><label class="queryarea-label">
						<s:text name="至"/>:
						<input type="text" id="hrDepartmentSnapendDate" name="hrPerson.endDate" onclick="changeSysTimeType('hrDepartmentSnap','confirmDate','makeDate','checkDate')"  onblur="checkQueryDate('hrDepartmentSnapbeginDate','hrDepartmentSnapendDate',1,'hrDepartmentSnap_beginDate','hrDepartmentSnap_endDate')" style="width:65px"/>
						<input type="hidden" id="hrDepartmentSnap_beginDate" name="hrPerson.endDate"  />
						<input type="hidden" id="hrDepartmentSnap_endDate" name="hrPerson.endDate"  />
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('hrDeptNew_search_form','hrDeptNew_gridtable');"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('hrDeptNew_search_form','hrDeptNew_gridtable');"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
<div class="panelBar" id="hrDeptNew_buttonBar">

<!-- 			<ul class="toolBar"> -->
<%-- 				<li><a id="hrDeptNew_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message --%>
<%-- 								key="button.add" /> --%>
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<%-- 				<li><a id="hrDeptNew_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span> --%>
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li><a id="hrDeptNew_gridtable_edit" class="changebutton"  href="javaScript:" -->
<%-- 					><span><s:text name="button.edit" /> --%>
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
			
<!-- 			</ul> -->

		</div>
		<div id="hrDeptNew_container">
		<div id="hrDeptNew_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
				  <span>
      				显示机构编码
      				<input id="hrDeptNew_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrDeptNewLeftTree',showCode:this,disabledDept:jQuery('#hrDeptNew_showDisabled')[0],disabledPerson:false,showCount:jQuery('#hrDeptNew_showPersonCount')[0] })"/>
    			 </span>
					<span>
						显示人员数
						<input id="hrDeptNew_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrDeptNewLeftTree',showCode:jQuery('#hrDeptNew_showCode')[0],disabledDept:jQuery('#hrDeptNew_showDisabled')[0],disabledPerson:false,showCount:this })"/>
					</span>
					<label id="hrDeptNew_expandTree" onclick="toggleExpandTree(this,'hrDeptNewLeftTree')">展开</label>
				</div>
				 <div class="treeTopCheckBox">
    			 <span>
      				显示停用部门
     			 <input id="hrDeptNew_showDisabled" type="checkbox" onclick="showDisabledDeptNew(this)"/>
     				</span>
    			</div>
    			<div class="treeTopCheckBox">
     			<span>
      				快速查询：
      			<input type="text" onKeyUp="searchTree('hrDeptNewLeftTree',this)"/>
     			</span>
   				 </div>
				<div id="hrDeptNewLeftTree" class="ztree"></div>
			</div>
	  <div id="hrDeptNew_layout-center" class="pane ui-layout-center">
		<div id="hrDeptNew_gridtable_div" class="grid-wrapdiv">
			<input type="hidden" id="hrDeptNew_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_hrDeptNew_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
		 	<table id="hrDeptNew_gridtable"></table>
		</div>
	<div class="panelBar" id="hrDeptNew_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="hrDeptNew_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="hrDeptNew_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="hrDeptNew_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
				</div>
			</div><!-- center -->
		</div><!-- layout -->
	</div>
</div>