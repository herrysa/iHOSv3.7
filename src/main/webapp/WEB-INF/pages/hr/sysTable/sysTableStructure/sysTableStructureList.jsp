
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function sysTableStructureGridReload(){
		var urlString = "sysTableStructureGridList";
		var name = jQuery("#sysTableStructure_search_name").val();
		var code = jQuery("#sysTableStructure_search_code").val();
		var sysFiled = jQuery("#sysTableStructure_search_sysFiled").val();
		var disabled = jQuery("#sysTableStructure_search_disabled").val();
		
		
		urlString=urlString+"?filter_LIKES_name="+name+"&filter_LIKES_fieldInfo.fieldCode="+code;
		urlString=urlString+"&filter_EQB_fieldInfo.sysFiled="+sysFiled+"&filter_EQB_disabled="+disabled;
		urlString=encodeURI(urlString);
		jQuery("#sysTableStructure_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var sysTableStructureLayout;
			  var sysTableStructureGridIdString="#sysTableStructure_gridtable";
	
	jQuery(document).ready(function() { 
		var hrDepartmentCurrentFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#sysTableStructure_container").css("height",hrDepartmentCurrentFullSize);
		$('#sysTableStructure_container').layout({ 
			applyDefaultStyles: false ,
			west__size : 250,
			spacing_open:5,//边框的间隙  
			spacing_closed:5,//关闭时边框的间隙 
			resizable :true,
			resizerClass :"ui-layout-resizer-blue",
			slidable :true,
			resizerDragOpacity :1, 
			resizerTip:"可调整大小",
			onresize_end : function(paneName,element,state,options,layoutName){
				if("center" == paneName){
					gridResize(null,null,"sysTableStructure_gridtable","single");
				}
			}
		});
		var initFlag = 0;
var sysTableStructureGrid = jQuery(sysTableStructureGridIdString);
    sysTableStructureGrid.jqGrid({
    	url : "sysTableStructureGridList",
    	editurl:"sysTableStructureGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',width : 100,align:'center',label : '<s:text name="sysTableStructure.id" />',hidden:true,key:true},
{name:'fieldInfo.fieldCode',index:'fieldInfo.fieldCode',width : 100,align:'left',label : '<s:text name="sysTableStructure.code" />',hidden:false,highsearch:true},
{name:'name',index:'name',width : 100,align:'left',label : '<s:text name="sysTableStructure.name" />',hidden:false,highsearch:true},
{name:'tableContent.name',index:'tableContent.name',width : 100,align:'left',label : '<s:text name="sysTableContent.bdinfo.tableName" />',hidden:false,highsearch:true},
{name:'sn',index:'sn',width : 60,align:'right',label : '<s:text name="sysTableStructure.sn" />',hidden:false,formatter:'integer',highsearch:true},
{name:'fieldInfo.dataType',index:'fieldInfo.dataType',width : 100,align:'center',label : '<s:text name="sysTableStructure.dataType" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '1:字符型;2:浮点型;3:布尔型;4:日期型;5:整数型;6:货币型;7:图片型'},highsearch:true},
{name:'fieldInfo.dataLength',index:'fieldInfo.dataLength',width : 80,align:'right',label : '<s:text name="sysTableStructure.dataLength" />',hidden:false,formatter:'integer',highsearch:true},				
{name:'fieldInfo.dataDecimal',index:'fieldInfo.dataDecimal',width : 80,align:'right',label : '<s:text name="sysTableStructure.dataDecimal" />',hidden:false,formatter:'integer',highsearch:true},
{name:'gridShowLength',index:'gridShowLength',width : 80,align:'right',label : '<s:text name="sysTableStructure.gridShowLength" />',hidden:false,formatter:'integer',highsearch:true},				
{name:'fieldInfo.fieldDefault',index:'fieldInfo.fieldDefault',width : 100,align:'left',label : '<s:text name="sysTableStructure.fieldDefault" />',hidden:false,highsearch:true},
{name:'fieldInfo.userTag',index:'fieldInfo.userTag',align:'center',width : 80,label : '<s:text name="sysTableStructure.userTag" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : 'input:文本框;select:下拉框;treeSelect:TreeSelect'},highsearch:true},				
{name:'fieldInfo.parameter1',index:'fieldInfo.parameter1',width : 150,align:'left',label : '<s:text name="sysTableStructure.parameter" />',hidden:false,highsearch:true},					
{name:'fieldInfo.read',index:'fieldInfo.read',width : 60,align:'center',label : '<s:text name="sysTableStructure.read" />',hidden:true,formatter:'checkbox',highsearch:true},				
{name:'fieldInfo.notNull',index:'fieldInfo.notNull',width : 60,align:'center',label : '<s:text name="sysTableStructure.notNull" />',hidden:true,formatter:'checkbox',highsearch:true},				
{name:'batchEdit',index:'batchEdit',width : 60,align:'center',label : '<s:text name="sysTableStructure.batchEdit" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'fieldInfo.statistics',index:'fieldInfo.statistics',width : 60,align:'center',label : '<s:text name="sysTableStructure.statistics" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'fieldInfo.sysField',index:'fieldInfo.sysField',width : 60,align:'center',label : '<s:text name="sysTableStructure.sysFiled" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'disabled',index:'disabled',width : 60,align:'center',label : '<s:text name="sysTableStructure.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="sysTableStructure.remark" />',hidden:false,highsearch:true},
{name:'changeUser.name',index:'changeUser.name',width : 100,align:'left',label : '<s:text name="sysTableStructure.changeUser" />',hidden:true,highsearch:true},
{name:'changeDate',index:'changeDate',width : 100,align:'center',label : '<s:text name="sysTableStructure.changeDate" />',hidden:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true}
        ],
        jsonReader : {
			root : "sysTableStructures", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'sn',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="sysTableStructureList.title" />',
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
			 gridContainerResize('sysTableStructure','layout');
           /* if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            } */
           var dataTest = {"id":"sysTableStructure_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	 initFlag = initColumn('sysTableStructure_gridtable','com.huge.ihos.hr.sysTables.model.SysTableStructure',initFlag);
       	} 

    });
    jQuery(sysTableStructureGrid).jqGrid('bindKeys');
    jQuery("#sysTableStructure_gridtable_add_custom").unbind( 'click' ).bind("click",function(){    	
    	var zTree = $.fn.zTree.getZTreeObj("sysTableStructureTreeLeft");  
        checkCount = zTree.getSelectedNodes(); 
        var classpurview = ""; 
        if(checkCount.length ==1){
            for(var i=0;i<checkCount.length;i++) { 
           	 if(checkCount[i].dataType=="tableContent"){
           		classpurview += checkCount[i].id;
           	 }
            }  
            
        }    	
		var url = "editSysTableStructure?navTabId=sysTableStructure_gridtable";
		if(classpurview){
			url+="&code="+classpurview;
		}
		var winTitle='<s:text name="sysTableStructureNew.title"/>';
		$.pdialog.open(url,'addSysTableStructure',winTitle, {mask:true,width : 700,height : 400});
	}); 
    jQuery("#sysTableStructure_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
        var sid = jQuery("#sysTableStructure_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null || sid.length !=1){
			return;
			}
		var winTitle='<s:text name="sysTableStructureEdit.title"/>';
		var url = "editSysTableStructure?navTabId=sysTableStructure_gridtable&id="+sid;
		$.pdialog.open(url,'editSysTableStructure',winTitle, {mask:true,width : 700,height : 400});
	}); 	
    jQuery("#sysTableStructure_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
		var url = "${ctx}/sysTableStructureGridEdit?oper=del"
		var sid = jQuery("#sysTableStructure_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#sysTableStructure_gridtable").jqGrid('getRowData',rowId);
				if(row['fieldInfo.sysFiled']=='Yes'){
					alertMsg.error("系统记录不能删除!");
					return;
				}
			}
			url = url+"&id="+sid+"&navTabId=sysTableStructure_gridtable";
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		}
	});
    sysTableStructureLeftTree();			
  	});
	function sysTableStructureLeftTree() {
		$.get("makeSysTableContentTree", {
			"_" : $.now()
		}, function(data) {
			var sysTableStructureTreeData = data.treeNodesList;
			var sysTableStructureTree = $.fn.zTree.init($("#sysTableStructureTreeLeft"),
					ztreesetting_sysTableStructureLeft, sysTableStructureTreeData);
			var nodes = sysTableStructureTree.getNodes();
			sysTableStructureTree.expandNode(nodes[0], true, false, true);
		});
	}
	var ztreesetting_sysTableStructureLeft = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false
			},
			callback : {
				beforeDrag:zTreeBeforeDrag,
				onClick : onModuleClick
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
	function zTreeBeforeDrag(treeId, treeNodes) {
	    return false;
	};
	function onModuleClick(event, treeId, treeNode, clickFlag) { 
	     var urlString = "sysTableStructureGridList";
 	    if(treeNode.children){
 	    	 var ids="0";
	    	 $.each(treeNode.children,function(n,value) {  
	    		 ids+=","+treeNode.children[n].id;
	    	 });
	    	urlString=urlString+"?filter_INS_tableContent.id="+ids;
	    }else{
	    	urlString=urlString+"?filter_EQS_tableContent.id="+treeNode.id;	
	    }		    	
		urlString=encodeURI(urlString);
		jQuery("#sysTableStructure_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
	}
</script>

<div class="page">
	<div class="pageHeader" id="sysTableStructure_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
			<form id="sysTableStructure_search_form" style="white-space: break-all;word-wrap:break-word;">				
				<label style="float:none;white-space:nowrap" >
						<s:text name='sysTableStructure.code'/>:
      						 <input type="text" id="sysTableStructure_search_code"/>
						</label>
				<label style="float:none;white-space:nowrap" >
						<s:text name='sysTableStructure.name'/>:
      						 <input type="text" id="sysTableStructure_search_name"/>
						</label>
				 <label style="float:none;white-space:nowrap" >
						<s:text name='sysTableStructure.sysFiled'/>:
					 	<s:select id="sysTableStructure_search_sysFiled" headerKey="" headerValue="--" 
							list="#{'1':'是','2':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
					</label>
				 <label style="float:none;white-space:nowrap" >
						<s:text name='sysTableStructure.disabled'/>:
					 	<s:select id="sysTableStructure_search_disabled" headerKey="" headerValue="--" 
							list="#{'1':'是','2':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
					</label>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="sysTableStructureGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="sysTableStructure_buttonBar">
			<ul class="toolBar">
				<li>
					<a id="sysTableStructure_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a id="sysTableStructure_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="sysTableStructure_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
     				<a class="settlebutton"  href="javaScript:setColShow('sysTableStructure_gridtable','com.huge.ihos.hr.sysTables.model.SysTableStructure')"><span><s:text name="button.setColShow" /></span></a>
   				 </li>
			</ul>
		</div>
		<div id="sysTableStructure_container">
			<div id="sysTableStructure_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div style="margin-left:20px;margin-bottom:2px;">
					<span style="vertical-align:middle;text-decoration:underline;cursor:pointer;float: right;" onclick="toggleExpandTreeWithShowFlag(this,'sysTableStructureTreeLeft')">展开</span>
				</div>
				<div id="sysTableStructureTreeLeft" class="ztree"></div>
			</div>
			<div id="sysTableStructure_layout-center" class="pane ui-layout-center">
		<div id="sysTableStructure_gridtable_div" class="grid-wrapdiv"  
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:960;height:628">
			<input type="hidden" id="sysTableStructure_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="sysTableStructure_gridtable_addTile">
				<s:text name="sysTableStructureNew.title"/>
			</label> 
			<label style="display: none"
				id="sysTableStructure_gridtable_editTile">
				<s:text name="sysTableStructureEdit.title"/>
			</label>
			<label style="display: none"
				id="sysTableStructure_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="sysTableStructure_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_sysTableStructure_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="sysTableStructure_gridtable" ></table>
		<div id="sysTableStructurePager"></div>
</div>
	<div class="panelBar" id="sysTableStructure_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="sysTableStructure_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="sysTableStructure_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="sysTableStructure_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	</div>
</div>
</div>
</div>