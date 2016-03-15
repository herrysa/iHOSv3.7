<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	jQuery(document).ready(function() {
		var dataPrivilegeItemGridIdStr = "#${random}dataPrivilegeItem_gridtable";
		var initShow = "${bdInfoUtil.initShow}";
		var dpGridUrl = "dataPrivilegeItemGridList?dataPrivilegeType=${dataPrivilegeType}&selectedId=${selectedId}&pageType=${pageType}&classCode=${classCode}";
		var dpGridDatatype = "json";
		if(initShow=="false"){
			dpGridUrl = "";
			dpGridDatatype = "local";
		}
		if("${pageType}"=="add"){
			jQuery("#${random}dataPrivilegeItem_gridtable_div").removeAttr("tablecontainer");
			jQuery("#${random}dataPrivilegeItem_gridtable_div").removeAttr("extraHeight");
			var layoutH = 65;
			var headerHeight = jQuery("#${random}dataPrivilegeItem_pageHeader").height();
			layoutH += headerHeight;
			/* if(){
			}else{
			} */
			jQuery("#${random}dataPrivilegeItem_gridtable_div").attr("layoutH",""+layoutH);
		}
		var dataPrivilegeItemGrid = jQuery(dataPrivilegeItemGridIdStr);
		var roleSelect;
		dataPrivilegeItemGrid.jqGrid({
			url : dpGridUrl,
			editurl:"dataPrivilegeItemGridEdit",
			datatype : dpGridDatatype,
			mtype : "GET",
			colModel:[
				{name:'ID',index:'ID',align:'center',label : '<s:text name="dataPrivilegeItemn.id" />',hidden:true,key:true},	
				{name:'CODE',index:'CODE',width : 100,align:'left',label : '${privilegeClass.className}编码',hidden:false},
				{name:'NAME',index:'NAME',width : 100,align:'left',label : '${privilegeClass.className}名称',hidden:false},
				<c:forEach items="${filters}" var="p">
				{name:'${p.fkcol}.${p.fkcolName}',index:'${p.fkcol}.${p.fkcolName}',width : 100,align:'left',label : '${p.label}',hidden:false},
				</c:forEach>
				{name:'PID',index:'PID',width : 100,align:'center',label : '<s:text name="dataPrivilegeItem.parent" />',hidden:true},
				{name:'ORGCODE',index:'ORGCODE',width : 100,align:'center',label : '<s:text name="dataPrivilegeItem.org" />',hidden:true},			
				{name:'COPYCODE',index:'COPYCODE',width : 100,align:'center',label : '<s:text name="dataPrivilegeItem.copy" />',hidden:true},				
				{name:'PERIODYEAR',index:'PERIODYEAR',width : 100,align:'center',label : '<s:text name="dataPrivilegeItem.periodYear" />',hidden:true},				
				{name:'READRIGHT',index:'READRIGHT',width : 100,align:'center',label : '<s:text name="dataPrivilegeItem.read" />',width:55,editable:true,edittype:"checkbox",editoptions:{value:"true:false"},formatter:"checkbox",hidden:false},				
				{name:'dpId',index:'dpId',width : 100,align:'center',label : '<s:text name="dataPrivilegeItem.dpId" />',width:55,hidden:true},				
				{name:'WRITERIGHT',index:'WRITERIGHT',width : 100,align:'center',label : '<s:text name="dataPrivilegeItem.write" />',width:55,editable:true,edittype:"checkbox",editoptions:{value:"true:false",dataEvents:[{type:'click',fn: function(e) {
					var thisTr = jQuery(this).parent().parent();
					var thisChecked = jQuery(this).attr("checked");
					if(thisChecked=="checked"){
						thisTr.find("input[name=READRIGHT]:first").attr("checked","true");
					}
				}}]},formatter:"checkbox",hidden:false},				
				{name:'CONTROLALL',index:'CONTROLALL',width : 100,align:'center',label : '<s:text name="dataPrivilegeItem.controlall" />',width:55,editable:true,edittype:"checkbox",editoptions:{value:"true:false",dataEvents:[{type:'click',fn: function(e) {
					var thisTr = jQuery(this).parent().parent();
					thisTr.find("input[name=READRIGHT]:first").attr("checked","true");
					thisTr.find("input[name=WRITERIGHT]:first").attr("checked","true");
				}}]},formatter:"checkbox",hidden:true}				
	        ],
	        jsonReader : {
				root : "dataPrivilegeItems", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: <c:forEach items="${filters}" var="p">'${p.fkcolCode},'+</c:forEach>'CODE',
	        viewrecords: true,
	        sortorder: 'asc',
	        height:300,
	        gridview:true,
	        rownumbers:true,
	        loadui: "disable",
	        multiselect: true,
			multiboxonly:true,
			shrinkToFit:true,
			autowidth:true,
			beforeSelectRow:function(rowid) {
	        	var disabled = jQuery("#"+rowid).find("input[type=checkbox]").attr("disabled");
	        	if(disabled=="true"){
	        		return false;
	        	}else{
	        		return true;
	        	}
	       	},
	       	onSelectAll : function(aRowids,status) {
	       		if (status) {
	       			var cbs = $("tr.jqgrow > td > input.cbox:disabled", "#${random}dataPrivilegeItem_gridtable");
	                cbs.removeAttr("checked");
	       		}
	       	},
	       	beforeSelectRow: function(rowid) {
	        	for(var ri in roleSelect){
          			var dpData = roleSelect[ri];
          			if(dpData.item==rowid){
			        	return false;
          			}
	        	}
	        	return true;
	       	},
		 	gridComplete:function(){
				//gridContainerResize('${random}dataPrivilegeItem','div');
				var containerWidth = jQuery("#${random}dataPrivilegeItem_page").parent().width();
				var containerHeight = jQuery("#${random}dataPrivilegeItem_page").parent().height();
				
				jQuery("#${random}dataPrivilegeItem_gridtable").find("tr.${random}dataPrivilegeItem_gridtableghead_0").each(function(){
					var orgName = jQuery(this).next().find("td[aria-describedby='${random}dataPrivilegeItem_gridtable_ORGCODE.ORGNAME']").text();
					var oldHtml = jQuery(this).html();
					var oldText = jQuery(this).text();
					jQuery(this).html(oldHtml.replace(oldText,orgName));
				});
           		var dataTest = {"id":"${random}dataPrivilegeItem_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		var userData = jQuery("#${random}dataPrivilegeItem_gridtable").getGridParam('userData'); 
	          	if(userData){
	          		var userSelect = userData.userSelect;
	          		for(var ui in userSelect){
	          			var dpData = userSelect[ui];
	          			var rowData = jQuery("#${random}dataPrivilegeItem_gridtable").jqGrid('getRowData',dpData.item);
  	   					rowData["READRIGHT"]=dpData.read;
  	   					rowData["WRITERIGHT"]=dpData.write;
  	   					//rowData["CONTROLALL"]="";
  	   					rowData["dpId"]=dpData.dpId;
  	   					
  	   					jQuery("#${random}dataPrivilegeItem_gridtable").jqGrid('setRowData',dpData.item,rowData);
	          		}
	          		roleSelect = userData.roleSelect;	
	          		for(var ri in roleSelect){
	          			var dpData = roleSelect[ri];
	          			//jQuery("#"+dpData.item.trim()).find("input[type=checkbox]").attr("checked","checked");
      	   				jQuery("#"+dpData.item.trim()).find("input[type=checkbox]").attr("disabled",true);
      	   				jQuery("#"+dpData.item.trim()).find("td").css("background-color", "#87cefa");
      	   				var rowData = jQuery("#${random}dataPrivilegeItem_gridtable").jqGrid('getRowData',dpData.item);
      	   				rowData["READRIGHT"]=dpData.read;
	   					rowData["WRITERIGHT"]=dpData.write;
	   					//rowData["CONTROLALL"]="";
	   					rowData["dpId"]=dpData.dpId;
      	   				jQuery("#${random}dataPrivilegeItem_gridtable").jqGrid('setRowData',dpData.item,rowData);
	          		}
	          	}
       		} 

    	});
    	//实例化ToolButtonBar
        var dpItem_menuButtonArrJson = "${menuButtonArrJson}";
        var dpItem_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(dpItem_menuButtonArrJson,false)));
        var dpItem_toolButtonBar = new ToolButtonBar({el:$('#trainPlan_buttonBar'),collection:dpItem_toolButtonCollection,attributes:{
         tableId : '${random}dataPrivilegeItem_gridtable',
         baseName : '${random}dataPrivilegeItem',
         width : 600,
         height : 600,
         base_URL : null,
         optId : null,
         fatherGrid : null,
         extraParam : null,
         selectNone : "请选择记录。",
         selectMore : "只能选择一条记录。",
         addTitle : '<s:text name="trainPlanNew.title"/>',
         editTitle : null
        }}).render();
        //实例化结束

        var dpItem_save = {id:'1005030208',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
          callBody:function(){
          }};
        dpItem_toolButtonBar.addButton(dpItem_save);//实例化ToolButtonBar
        setTimeout(function(){
        	if(initShow=="false"){
        		var dataTest = {"id":"${random}dataPrivilegeItem_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
        		jQuery("#${random}dataPrivilegeItem_gridtable").jqGrid("setGridParam",{url:"dataPrivilegeItemGridList?dataPrivilegeType=${dataPrivilegeType}&selectedId=${selectedId}&pageType=${pageType}&classCode=${classCode}",datatype:'json'});
    		}
        },100);
        jQuery("#${random}addDataPrivi").click(function(){
        	var dpItemUrl = "dataPrivilegeItemList?pageType=add&selectedId=${selectedId}&dataPrivilegeType=${dataPrivilegeType}&classCode=${classCode}&&parentRandom=${random}";
    		$.pdialog.open(dpItemUrl, 'chooseDpItem', "选择数据权限", {
    			mask : true,
    			width : 750,
    			height : 450
    		});
        });
        jQuery("#${random}editDataPrivi").click(function(){
        	fullGridEdit("#${random}dataPrivilegeItem_gridtable");
        });
        jQuery("#${random}canceleditDataPrivi").click(function(){
        	fullGridEdit("#${random}dataPrivilegeItem_gridtable");
        });
        jQuery("#${random}saveDataPrivi").click(function(){
        	var dataPrivilegeType = "${dataPrivilegeType}";
    		var sids = jQuery("#${random}dataPrivilegeItem_gridtable").jqGrid('getGridParam','selarrrow');
    		var names="",orgcodes="",copycodes="",periodyears="",readrights="",writerights="",controlalls="";
        	if(sids.length==0){
        		alertMsg.error("请选择数据！");
        		return ;
        	}else{
        		var colModel = jQuery("#${random}dataPrivilegeItem_gridtable").jqGrid("getGridParam",'colModel');  
        	    for(var i=0;i<sids.length;i++){
        	    	if((sids[i]=='all'||sids[i]=='ALL')&&sids.length>1){
        	    		alertMsg.error("您已选择全部数据，不能同时选择其他数据！");
        	    		return ;
        	    	}
        	    	/* else if((sids[i]=='all'||sids[i]=='ALL')&&sids.length==1){
        	    		alertMsg.confirm("确认选择全部数据？选择quanbushuju", {
        					okCall: function(){
        						jQuery.post(editUrl, {}, formCallBack, "json");
        					}
        				});
        	    	} */
    				var ret = jQuery("#${random}dataPrivilegeItem_gridtable").jqGrid('getRowData',sids[i]);
    				if(ret["WRITERIGHT"]=='true'&&ret["READRIGHT"]=='false'){
    					ret["READRIGHT"] = 'true';
    				}
    				names+=ret["NAME"]+",";
    				orgcodes+=ret["ORGCODE"]+",";
    				copycodes+=ret["COPYCODE"]+",";
    				periodyears+=ret["PERIODYEAR"]+",";
    				readrights+=ret["READRIGHT"]+",";
    				writerights+=ret["WRITERIGHT"]+",";
    				controlalls+=ret["CONTROLALL"]+",";
        		}
        	    
        	}
        	var ruId = "";
    	    var saveDataPrivi = "";
    	    if(dataPrivilegeType=='2'){
    	    	saveDataPrivi = "saveUserDataPrivilege";
    	    	ruId = jQuery("#user_gridtable").jqGrid('getGridParam','selarrrow');
    	    }else if(dataPrivilegeType=='1'){
    	    	saveDataPrivi = "saveRoleDataPrivilege";
    	    	ruId = jQuery("#role_gridtable").jqGrid('getGridParam','selarrrow');
        	}
    	    jQuery.ajax({
    		    url: saveDataPrivi,
    		    type: 'post',
    		    data:{privilegeClass:"${classCode}",saveIds:sids,names:names,orgcodes:orgcodes,copycodes:copycodes,periodyears:periodyears,readrights:readrights,writerights:writerights,controlalls:controlalls,ruId:ruId},
    		    dataType: 'json',
    		    aysnc:false,
    		    error: function(data){
    		        
    		    },
    		    success: function(data){
    		    	$.pdialog.closeCurrent();
    		    	data.navTabId = "${parentRandom}dataPrivilegeItem_gridtable";
    		    	//formCallBack(data);
    		    	jQuery('#'+data.navTabId).trigger("reloadGrid");
    		    }
    		});
        });
        jQuery("#${random}delDataPrivi").click(function(){
        	var dataPrivilegeType = "${dataPrivilegeType}";
    		var sids = jQuery("#${random}dataPrivilegeItem_gridtable").jqGrid('getGridParam','selarrrow');
        	if(sids.length==0){
        		alertMsg.error("请选择数据！");
        		return ;
        	}
        	var dpIds = "";
        	for(var i=0;i<sids.length;i++){
        		var ret = jQuery("#${random}dataPrivilegeItem_gridtable").jqGrid('getRowData',sids[i]);
        		dpIds+=ret["dpId"]+",";
        	}
        	var ruId = "";
    	    var delDataPrivi = "";
    	    if(dataPrivilegeType=='2'){
    	    	delDataPrivi = "userDataPrivilegeGridEdit?oper=del";
    	    	ruId = jQuery("#user_gridtable").jqGrid('getGridParam','selarrrow');
    	    }else if(dataPrivilegeType=='1'){
    	    	delDataPrivi = "dataPrivilegeGridEdit?oper=del";
    	    	ruId = jQuery("#role_gridtable").jqGrid('getGridParam','selarrrow');
        	}
    	    jQuery.ajax({
    		    url: delDataPrivi,
    		    type: 'post',
    		    data:{id:dpIds},
    		    dataType: 'json',
    		    aysnc:false,
    		    error: function(data){
    		        
    		    },
    		    success: function(data){
    		    	data.navTabId ="${random}dataPrivilegeItem_gridtable";
    		    	formCallBack(data);
    		    }
    		});
        });
        jQuery("#${random}reloadDataPrivi").click(function(){
			propertyFilterSearch('${random}dataPrivilegeItem_search_form','${random}dataPrivilegeItem_gridtable');
        });
  	});
</script>

<div class="page" id="${random}dataPrivilegeItem_page" style="height:100%">
	<div class="pageHeader" id="${random}dataPrivilegeItem_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="${random}dataPrivilegeItem_search_form" style="white-space: break-all;word-wrap:break-word;">
					<s:iterator value="filters" status="filter">
						<label style="float:none;white-space:nowrap" ><s:property value="label"/>:
							<input type="hidden" id="${random}<s:property value='class'/>filter_id" name="filter_LIKES_<s:property value="fkcol"/>" style="width:80px"/>
					 		<input type="text" id="${random}<s:property value='class'/>filter"  style="width:100px"/>
					 		<script type="text/javascript">
					 			var classId = "<s:property value='class'/>";
						 		jQuery("#${random}"+classId+"filter").treeselect({
						 			url:"makePrivilegeClassTree?classId="+classId+"&selectedId=${selectedId}",
									dataType : "url",
									optType : "multi",
									exceptnullparent : false,
									lazy : false,
									minWidth:'200px',
									selectParent:true,
									lazy:true,
									rebuildByClick:true,
									callback : {
										beforeBuild:function(ts,input){
											var parentValue = jQuery("#${random}<s:property value='pclass'/>filter_id").val();
											if(!ts.opts.urlbackup){
												ts.opts.urlbackup = ts.opts.url;
											}else{
												ts.opts.url = ts.opts.urlbackup;
											}
											ts.opts.url += "&<s:property value='pclass'/>="+parentValue;
											//alert(ts.opts.url);
											return ts;
										}
									}
								});
					 		</script>
						</label>
					</s:iterator>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='dataPrivilegeItem.dept'/>:
					 	<input type="text" name="filter_LIKES_deptId" style="width:80px"/>
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						${privilegeClass.className}编码:
					 	<input type="text" name="filter_LIKES_${bdInfoUtil.pkCol.fieldCode }" style="width:100px"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						${privilegeClass.className}名称:
					 	<input type="text" name="filter_LIKES_${bdInfoUtil.nameCol.fieldCode }" style="width:100px"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button id="${random}reloadDataPrivi" type="button"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="${random}dataPrivilegeItem_buttonBar">
			<ul class="toolBar">
				<s:if test="pageType!='add'">
				<li><a id="${random}addDataPrivi" class="addbutton" href="javaScript:" ><span>添加
					</span>
				</a>
				</li>
				<li><a id="${random}delDataPrivi" class="delbutton" href="javaScript:" ><span>删除
					</span>
				</a>
				</li>
				
				</s:if>
				<s:else>
					<li><a id="${random}saveDataPrivi" class="addbutton" href="javaScript:" ><span>保存
					</span>
				</a>
				</li>
				<li><a id="${random}editDataPrivi" class="editbutton" href="javaScript:" ><span>编辑权限</span>
				</a></li>
				<li><a id="${random}canceleditDataPrivi" class="canceleditbutton" href="javaScript:" ><span>取消编辑</span>
				</a></li>
				</s:else>
			</ul> 
		</div>
		
		<div id="${random}dataPrivilegeItem_gridtable_div" class="grid-wrapdiv"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:900;height:550" tablecontainer="${random}dataPrivilege_layout-center" extraHeight=118>
			<input type="hidden" id="dataPrivilegeItem_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_dataPrivilegeItem_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="${random}dataPrivilegeItem_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="${random}dataPrivilegeItem_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="${random}dataPrivilegeItem_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="${random}dataPrivilegeItem_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="${random}dataPrivilegeItem_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>