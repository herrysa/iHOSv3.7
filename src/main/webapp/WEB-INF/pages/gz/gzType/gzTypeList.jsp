
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var gzTypeLayout;
	var gzTypeGridIdString="#gzType_gridtable";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var gzTypeGrid = jQuery(gzTypeGridIdString);
    	gzTypeGrid.jqGrid({
    		url : "gzTypeGridList",
    		editurl:"gzTypeGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'gzTypeId',index:'gzTypeId',align:'left',width:'80px',label : '<s:text name="gzType.gzTypeId" />',hidden:false,key:true,highsearch:true},
{name:'gzTypeName',index:'gzTypeName',align:'left',width:'100px',label : '<s:text name="gzType.gzTypeName" />',hidden:false,highsearch:true},
{name:'issueType',index:'issueType',align:'center',width:'70px',label : '<s:text name="gzType.issueType" />',hidden:false,formatter: "select", editoptions:{value:"0:月结;1:次结"},highsearch:true},
{name:'issueDate',index:'issueDate',align:'center',width:'80px',label : '<s:text name="gzType.issueDate" />',hidden:false,highsearch:true},
{name:'issueNumber',index:'issueNumber',align:'right',width:'90px',label : '<s:text name="gzType.issueNumber" />',hidden:false,highsearch:true},
{name:'status',index:'status',align:'center',width:'70px',label : '<s:text name="gzType.status" />',hidden:false,formatter:"checkbox",highsearch:true},
{name:'disabled',index:'disabled',align:'center',width:'50px',label : '<s:text name="gzType.disabled" />',hidden:false,formatter:"checkbox",highsearch:true},
{name:'remark',index:'remark',align:'left',width:'250px',label : '<s:text name="gzType.remark" />',hidden:false,highsearch:true}

],
        	jsonReader : {
				root : "gzTypes", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'gzTypeId',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="gzTypeList.title" />',
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
				gridContainerResize('gzType','div');
		 		var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum<=0){
	            	var tw = jQuery(this).outerWidth();
					jQuery(this).parent().width(tw);
					jQuery(this).parent().height(1);
	            }else{
	            	var rowIds = jQuery(this).getDataIDs();
			        var ret = jQuery(this).jqGrid('getRowData');
			        var id='';
			        for(var i=0;i<rowNum;i++){
			        	id = rowIds[i];
			        	var gzTypeId = ret[i]["gzTypeId"];
				   		setCellText(this,id,'gzTypeName','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewGzType(\''+gzTypeId+'\');">'+ret[i]["gzTypeName"]+'</a>');
			        	var status = ret[i]["status"];
			        	if(status == "Yes"){
			        		setCellText(this,id,'gzTypeId','<font style="color:red;">'+ret[i]["gzTypeId"]+'</font>');
			        		setCellText(this,id,'gzTypeName','<a style="color:red;text-decoration:underline;cursor:pointer;" onclick="javascript:viewGzType(\''+gzTypeId+'\');">'+ret[i]["gzTypeName"]+'</a>');
			        		switch(ret[i]["issueType"]){
			        			case '0':
			        				setCellText(this,id,'issueType','<font style="color:red;">月结</font>');
			        				break;
			        			case '1':
			        				setCellText(this,id,'issueType','<font style="color:red;">次结</font>');
			        				break;
			        		}
			        		setCellText(this,id,'issueDate','<font style="color:red;">'+ret[i]["issueDate"]+'</font>');
			        		setCellText(this,id,'issueNumber','<font style="color:red;">'+ret[i]["issueNumber"]+'</font>');
			        		setCellText(this,id,'remark','<font style="color:red;">'+ret[i]["remark"]+'</font>');
			        	}
			        }
	            }
           	var dataTest = {"id":"gzType_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	  initFlag = initColumn('gzType_gridtable','com.huge.ihos.gz.gzType.model.GzType',initFlag);
      	   	//makepager("gzType_gridtable");
       		} 

    	});
    jQuery(gzTypeGrid).jqGrid('bindKeys');
    
  	});
	/*----------------------------------tooBar start-----------------------------------------------*/
	var gzType_menuButtonArrJson = "${menuButtonArrJson}";
	var gzType_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(gzType_menuButtonArrJson,false)));
	var gzType_toolButtonBar = new ToolButtonBar({el:$('#gzType_toolbuttonbar'),collection:gzType_toolButtonCollection,attributes:{
		tableId : 'gzType_gridtable',
		baseName : 'gzType',
		width : 600,
		height : 600,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : '<s:text name="gzTypeNew.title"/>',
		editTitle : null
	}}).render();
	
	var gzType_function = new scriptFunction();
	gzType_function.optBeforeCall = function(e,$this,param){
		var pass = false;
		if(param.selectRecord){
			var sid = jQuery("#gzType_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
	        	alertMsg.error("请选择记录！");
				return pass;
			}
	        if(param.singleSelect){
	        	if(sid.length != 1){
		        	alertMsg.error("只能选择一条记录！");
					return pass;
				}
	        }
		}
        return true;
	};
	//添加
	gzType_toolButtonBar.addCallBody('11010101',function(e,$this,param){
		var url = "editGzType?popup=true&navTabId=gzType_gridtable&oper=update";
		var winTitle = '<s:text name="gzTypeNew.title"/>';
		url = encodeURI(url);
    	$.pdialog.open(url,'editGzType',winTitle, {mask:true,width : 650,height : 300,maxable:true,resizable:true});  
	},{});
	//删除
	gzType_toolButtonBar.addCallBody('11010102',function(e,$this,param){
		 var sid = jQuery("#gzType_gridtable").jqGrid('getGridParam','selarrrow');
 		 for(var i = 0;i < sid.length; i++){
			var rowId = sid[i];
			var row = jQuery("#gzType_gridtable").jqGrid('getRowData',rowId);
			if(row['status'] == "Yes"){
				alertMsg.error("当前类别不能删除！");
				return;
			}
		 }
         jQuery.ajax({
				url: 'checkGzTypeDel',
				data: {id:sid},
				type: 'post',
				dataType: 'json',
				async:true,
				error: function(data){
					alertMsg.error("系统错误！");
				},
				success: function(data){
					if(data.message){
						alertMsg.error(data.message);
						return;
					}
					alertMsg.confirm("确认要删除该类别？",{
			    		okCall:function(){
			    			var url = "gzTypeGridEdit?id="+sid+"&popup=true&navTabId=gzType_gridtable&oper=del";  
		    		    	$.post(url,{},function(data){
		    		    		formCallBack(data);
		    		    	});
			    		}
					});
				}
			});
	},{});
	gzType_toolButtonBar.addBeforeCall('11010102',function(e,$this,param){
		return gzType_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord"});
	//修改
	gzType_toolButtonBar.addCallBody('11010103',function(e,$this,param){
		var sid = jQuery("#gzType_gridtable").jqGrid('getGridParam','selarrrow');
    	var url = "editGzType?gzTypeId="+sid+"&popup=true&navTabId=gzType_gridtable&oper=update";
    	url = encodeURI(url);
    	var winTitle = '<s:text name="gzTypeEdit.title"/>';
    	$.pdialog.open(url,'viewGzItem_'+sid,winTitle, {mask:true,width : 650,height : 300,maxable:true,resizable:true});  
	},{});
	gzType_toolButtonBar.addBeforeCall('11010103',function(e,$this,param){
		return gzType_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",singleSelect:"singleSelect"});
	//停用
	gzType_toolButtonBar.addCallBody('11010104',function(e,$this,param){
		 var sid = jQuery("#gzType_gridtable").jqGrid('getGridParam','selarrrow');
 		 for(var i = 0;i < sid.length; i++){
			var rowId = sid[i];
			var row = jQuery("#gzType_gridtable").jqGrid('getRowData',rowId);
			if(row['status'] == "Yes"){
				alertMsg.error("当前类别不能停用！");
				return;
			}
			if(row['disabled'] == 'Yes'){
				alertMsg.error("该类别已停用！");
				return;
			}
		}
 		var url = "gzTypeGridEdit?id="+sid+"&popup=true&navTabId=gzType_gridtable&oper=disable";  
 		url = encodeURI(url);
		alertMsg.confirm("确认停用？", {
			okCall : function() {
				$.post(url,function(data) {
					formCallBack(data);
				});
			}
		});
	},{});
	gzType_toolButtonBar.addBeforeCall('11010104',function(e,$this,param){
		return gzType_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord"});
	//启用
	gzType_toolButtonBar.addCallBody('11010105',function(e,$this,param){
		 var sid = jQuery("#gzType_gridtable").jqGrid('getGridParam','selarrrow');
 		 for(var i = 0;i < sid.length; i++){
			var rowId = sid[i];
			var row = jQuery("#gzType_gridtable").jqGrid('getRowData',rowId);
			if(row['disabled'] != 'Yes'){
				alertMsg.error("该类别已启用！");
				return;
			}
		}
 		var url = "gzTypeGridEdit?id="+sid+"&popup=true&navTabId=gzType_gridtable&oper=enable";  
 		url = encodeURI(url);
		alertMsg.confirm("确认启用？", {
			okCall : function() {
				$.post(url,function(data) {
					formCallBack(data);
				});
			}
		});
	},{});
	gzType_toolButtonBar.addBeforeCall('11010105',function(e,$this,param){
		return gzType_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord"});
	//当前类别
	gzType_toolButtonBar.addCallBody('11010106',function(e,$this,param){
		 var sid = jQuery("#gzType_gridtable").jqGrid('getGridParam','selarrrow');
 		 for(var i = 0;i < sid.length; i++){
			var rowId = sid[i];
			var row = jQuery("#gzType_gridtable").jqGrid('getRowData',rowId);
			if(row['disabled'] == 'Yes'){
				alertMsg.error("已停用类别不能设为当前类别！");
				return;
			}
			if(row['status'] == "Yes"){
				alertMsg.error("该类别已为当前类别！");
				return;
			}
			var gztypeDp = "${fns:u_writeDP('gztype_dp')}";
			var hasRight = false;
			if(gztypeDp.indexOf('select')!=-1||gztypeDp.indexOf('SELECT')!=-1){
				hasRight = true;
			}else{
				var gzTypeDpArr = gztypeDp.split(",");
				for(var gt in gzTypeDpArr){
					if(gzTypeDpArr[gt]==rowId){
						hasRight = true;
						break;
					}
				}
			}
			if(!hasRight){
				alertMsg.error("您没有该类别的权限！");
				return ;
			}
		}
 		var url = "gzTypeGridEdit?gzTypeId="+sid+"&popup=true&navTabId=gzType_gridtable&oper=changeCur";  
 		url = encodeURI(url);
		alertMsg.confirm("确认切换当前类别？", {
			okCall : function() {
				$.post(url,function(data) {
					formCallBack(data);
					if(data.statusCode == 200){
						setTimeout(function(){
			    			//关闭除了main和当前页的其他页面
			    			var navTabLis= jQuery("ul.navTab-tab li");
			    			  var curTabid = jQuery("ul.navTab-tab li.selected").attr("tabid");
			    			  jQuery.each(navTabLis, function(){
			    			   var tabid = jQuery(this).attr("tabid");
			    			       　     if(tabid != curTabid&&tabid != "main"){
			    			       　  	  navTab.closeTab(tabid);
			    			       　     }  
			    			  　　});  
			    		},100);
					}
				});
			}
		});
	},{});
	gzType_toolButtonBar.addBeforeCall('11010106',function(e,$this,param){
		return gzType_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",singleSelect:"singleSelect"});
	//设置表格列
	var gzType_setColShowButton = {id:'11010107',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
			callBody:function(){
				setColShow('gzType_gridtable','com.huge.ihos.gz.gzType.model.GzType');
			}};
	gzType_toolButtonBar.addButton(gzType_setColShowButton);
	/*----------------------------------tooBar end-----------------------------------------------*/
	/*查看*/
    function viewGzType(id){
    	var url = "editGzType?gzTypeId="+id+"&popup=true&navTabId=gzType_gridtable&oper=view";
    	url = encodeURI(url);
    	var winTitle = '<s:text name="gzTypeView.title"/>';
    	$.pdialog.open(url,'viewGzItem_'+id,winTitle, {mask:true,width : 650,height : 300,maxable:true,resizable:true});  
    }
</script>

<div class="page" id="gzType_page">
	<div id="gzType_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="gzType_search_form" class="queryarea-form">
					<label class="queryarea-label">
						<s:text name='gzType.gzTypeName'/>:
						<input type="text" name="filter_LIKES_gzTypeName" style="width:80px"/>
					</label>
					<label class="queryarea-label">
						<s:text name='gzType.issueDate'/>:
						<input type="text" name="filter_EQS_issueDate" style="width:80px" 
						onclick = "javascript:{WdatePicker({skin:'ext',dateFmt:'yyyyMM'});}" />
					</label>
					<label class="queryarea-label">
					     <s:text name='gzType.issueType'/>:
					   <s:select name='filter_EQS_issueType' headerKey=""   style="font-size:12px"
							list="#{'':'--','0':'月结','1':'次结'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
					<label class="queryarea-label">
						<s:text name='gzType.issueNumber'/>:
						 <s:select name='filter_EQI_issueNumber' headerKey=""   style="font-size:12px"
							list="#{'':'--','1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7'
							,'8':'8','9':'9','10':'10','11':'11','12':'12','13':'13'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
					<label class="queryarea-label">
					     <s:text name='gzType.disabled'/>:
					   <s:select name='filter_EQB_disabled' headerKey=""   style="font-size:12px"
							list="#{'':'--','true':'是','false':'否'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
				       </s:select>
					</label>
					<label class="queryarea-label">
						<s:text name='gzType.remark'/>:
						<input type="text" name="filter_LIKES_remark" style="width:120px"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('gzType_search_form','gzType_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="propertyFilterSearch('gzType_search_form','gzType_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="gzType_buttonBar">
			<ul class="toolBar" id="gzType_toolbuttonbar">
				<%-- <li>
					<a id="gzType_gridtable_add_custom" class="addbutton" href="javaScript:addGzType()" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a  class="delbutton"  href="javaScript:deleteGzType()"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a  class="changebutton"  href="javaScript:editGzType()"><span><s:text name="button.edit" /></span></a>
				</li>
			    <li>
			    	<a id="gzType_gridtable_select" class="changebutton"  href="javaScript:selectNowGzType()"><span>切换类别</span></a>
				</li> --%>
			<!-- 	   <li><a id="gzType_gridtable_select" class="changebutton"  href="javaScript:closeGzType()"
					><span>关闭工资类别
					</span>
				</a>
				</li> -->
			</ul>
		</div>
		<div id="gzType_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="gzType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="gzType_gridtable_addTile">
				<s:text name="gzTypeNew.title"/>
			</label> 
			<label style="display: none"
				id="gzType_gridtable_editTile">
				<s:text name="gzTypeEdit.title"/>
			</label>
			<div id="load_gzType_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="gzType_gridtable"></table>
			<!--<div id="gzTypePager"></div>-->
		</div>
	</div>
	<div class="panelBar" id="gzType_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="gzType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="gzType_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="gzType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>