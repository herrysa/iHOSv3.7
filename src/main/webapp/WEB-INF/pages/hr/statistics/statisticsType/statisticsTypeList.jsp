
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function statisticsTypeGridReload(){
		var urlString = "statisticsTypeGridList";
		var code = jQuery("#search_statisticsType_code").val();
		var name = jQuery("#search_statisticsType_name").val();
		var remark = jQuery("#search_statisticsType_remark").val();
		var disabled = jQuery("#search_statisticsType_disabled").val();
	
		urlString=urlString+"?filter_LIKES_code="+code+"&filter_LIKES_name="+name;
		urlString=urlString	+"&filter_LIKES_remark="+remark+"&filter_EQB_disabled="+disabled;
		urlString=urlString +"&filter_EQS_statisticsCode="+"${statisticsCode}";
		urlString=encodeURI(urlString);
		jQuery("#statisticsType_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var statisticsTypeLayout;
			  var statisticsTypeGridIdString="#statisticsType_gridtable";
	
	jQuery(document).ready(function() { 
		var statisticsTypeFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#statisticsType_container").css("height",statisticsTypeFullSize);
		$('#statisticsType_container').layout({ 
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
					gridResize(null,null,"statisticsType_gridtable","single");
				}
			}
		});
// 		statisticsTypeLayout = makeLayout({
// 			baseName: 'statisticsType', 
// 			tableIds: 'statisticsType_gridtable'
// 		}, null);
var initFlag = 0;
var statisticsTypeGrid = jQuery(statisticsTypeGridIdString);
    statisticsTypeGrid.jqGrid({
    	url : "statisticsTypeGridList"+"?statisticsCode="+"${statisticsCode}",
    	editurl:"statisticsTypeGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="statisticsType.id" />',hidden:true,key:true},
{name:'code',index:'code',align:'left',width : 100,label : '<s:text name="statisticsType.code" />',hidden:false,highsearch:true},	
{name:'name',index:'name',align:'left',width : 100,label : '<s:text name="statisticsType.name" />',hidden:false,highsearch:true},
{name:'parentType.name',index:'parentType.name',align:'left',width : 100,label : '<s:text name="statisticsType.parentType" />',hidden:false,highsearch:true},
// {name:'level',index:'level',align:'right',width : 60,label : '<s:text name="statisticsType.level" />',hidden:false,formatter:'integer',highsearch:true},	
{name:'leaf',index:'leaf',align:'center',width : 60,label : '<s:text name="statisticsType.leaf" />',hidden:false,formatter:'checkbox',highsearch:true},		
{name:'changeUser.name',index:'changeUser.name',align:'left',width : 100,label : '<s:text name="statisticsType.changeUser" />',hidden:true,highsearch:true},	
{name:'changeDate',index:'changeDate',align:'center',width : 80,label : '<s:text name="statisticsType.changeDate" />',hidden:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},								
{name:'remark',index:'remark',align:'center',width : 250,label : '<s:text name="statisticsType.remark" />',hidden:false,highsearch:true}		
	
        ],
        jsonReader : {
			root : "statisticsTypes", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'id',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="statisticsTypeList.title" />',
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
			 gridContainerResize('statisticsType','layout');
//            if(jQuery(this).getDataIDs().length>0){
//               jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
//             }
           var dataTest = {"id":"statisticsType_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   //makepager("statisticsType_gridtable");
      	 	initFlag = initColumn('statisticsType_gridtable','com.huge.ihos.hr.statistics.model.StatisticsType',initFlag);
       	} 

    });
    jQuery(statisticsTypeGrid).jqGrid('bindKeys');
    
  //实例化ToolButtonBar
    var statisticsType_menuButtonArrJson = "${menuButtonArrJson}";
    var statisticsType_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(statisticsType_menuButtonArrJson,false)));
    var statisticsType_toolButtonBar = new ToolButtonBar({el:$('#statisticsType_buttonBar'),collection:statisticsType_toolButtonCollection,attributes:{
     tableId : 'statisticsType_gridtable',
     baseName : 'statisticsType',
     width : 600,
     height : 600,
     base_URL : null,
     optId : null,
     fatherGrid : null,
     extraParam : null,
     selectNone : "请选择记录。",
     selectMore : "只能选择一条记录。",
     addTitle : '<s:text name="statisticsTypeNew.title"/>',
     editTitle : null
    }}).render();
    //实例化结束
     //为button添加方法 (普通点击按钮)
  	statisticsType_toolButtonBar.addCallBody('100206020101',function(e,$this,param){
  	 	  var zTree = $.fn.zTree.getZTreeObj("statisticsTypeTreeLeft"); 
          var nodes = zTree.getSelectedNodes();
          if(nodes.length!=1){
        	alertMsg.error("请选择统计类型。");
          	return;
       	  }
          jQuery.ajax({
              url: 'statisticsItemGridList?filter_EQS_parentType.id='+nodes[0].id,
              data: {},
              type: 'post',
              dataType: 'json',
              async:false,
              error: function(data){
              },
              success: function(data){
            	  if(data.statisticsItems&&data.statisticsItems.length>0){
            		  alertMsg.error("包含统计项的统计类型不能添加。");
                      return;
            	  }
            	  var url = "editStatisticsType?navTabId=statisticsType_gridtable&parentId="+nodes[0].id;
                  url=url+"&statisticsCode="+"${statisticsCode}";
          		  var winTitle='<s:text name="statisticsTypeNew.title"/>';
          		  $.pdialog.open(url,'addStatisticsType',winTitle, {mask:true,width : 700,height : 300});
              }
          });
 	 },{});
  	statisticsType_toolButtonBar.addCallBody('100206020102',function(e,$this,param){
  		 var sid = jQuery("#statisticsType_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
	        	alertMsg.error("请选择记录。");
				return;
			}else{
				var rowData;
 				for(var i=0;i<sid.length;i++){
 					rowData = jQuery("#statisticsType_gridtable").jqGrid('getRowData',sid[i]);
 					if(rowData["leaf"]=="No"){
 						alertMsg.error("只能删除末级记录。");
 						return;
 					}
 				} 
				 jQuery.ajax({
		             url: 'statisticsItemGridList?filter_INS_parentType.id='+sid,
		             data: {},
		             type: 'post',
		             dataType: 'json',
		             async:false,
		             error: function(data){
		             },
		             success: function(data){
		            	 if(data.statisticsItems.length>0){
		            		alertMsg.error("包含统计项的记录不能删除。");
		     				return;
		            	 }else{
		            		  var url = "${ctx}/statisticsTypeGridEdit?oper=del";
		            			  url = url+"&id="+sid+"&navTabId=statisticsType_gridtable";
		      					alertMsg.confirm("确认删除？", {
		      					okCall : function() {
		      						$.post(url,function(data) {
		      							formCallBack(data);
		      						// delete 
		      						if(data.statusCode!=200){
		      						   return;
		      						  }
		      						var statisticsTypeTreeObj = $.fn.zTree.getZTreeObj("statisticsTypeTreeLeft");
		      						for(var i=0;i<sid.length;i++){
		      						    var delId = sid[i];
		      						    var node = statisticsTypeTreeObj.getNodeByParam("id", delId, null);
		      						  statisticsTypeTreeObj.removeNode(node);
		      						 }
		      						});
		      					}
		      				});
		            	 }
		             }
		         });
			}
  	},{});
  	statisticsType_toolButtonBar.addCallBody('100206020103',function(e,$this,param){
  		 var sid = jQuery("#statisticsType_gridtable").jqGrid('getGridParam','selarrrow');
         if(sid==null|| sid.length != 1){       	
 			alertMsg.error("请选择一条记录。");
 			return;
 			}
 		var winTitle='<s:text name="statisticsTypeEdit.title"/>';
 		var url = "editStatisticsType?popup=true&id="+sid+"&navTabId=statisticsType_gridtable";
 		url=url+"&statisticsCode="+"${statisticsCode}";
 		$.pdialog.open(url,'editStatisticsType',winTitle, {mask:true,width : 700,height : 300});
  	},{});
    	
	//statisticsTypeLayout.resizeAll();    
    statisticsTypeLeftTree();
  	});
	function statisticsTypeLeftTree() {
		$.get("makeStatisticsTypeTree"+"?statisticsCode="+"${statisticsCode}", {
			"_" : $.now()
		}, function(data) {
			var statisticsTypeTreeData = data.statisticsTypeTreeNodes;
			var statisticsTypeTree = $.fn.zTree.init($("#statisticsTypeTreeLeft"),
					ztreesetting_statisticsTypeLeft, statisticsTypeTreeData);
			var nodes = statisticsTypeTree.getNodes();
			statisticsTypeTree.expandNode(nodes[0], true, false, true);
		});
	}
	var ztreesetting_statisticsTypeLeft = {
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
	     var urlString = "statisticsTypeGridList";
	     if(treeNode.subSysTem=="ALL"){
	    	 urlString=urlString +"?filter_EQS_statisticsCode="+"${statisticsCode}";
	     }else if(treeNode.children){
	    	 var ids=treeNode.id;
	    	 $.each(treeNode.children,function(n,value) {  
	    		 ids+=","+treeNode.children[n].id;
	    	 });
	    	 urlString=urlString+"?filter_INS_id="+ids;	
		     urlString=urlString +"&filter_EQS_statisticsCode="+"${statisticsCode}";
	     }else{
	    	 urlString=urlString+"?filter_EQS_id="+treeNode.id;	
		     urlString=urlString +"&filter_EQS_statisticsCode="+"${statisticsCode}";
	     }
		urlString=encodeURI(urlString);
		jQuery("#statisticsType_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
	}
</script>

<div class="page">
	<div class="pageHeader" id="statisticsType_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
			<form id="statisticsType_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='statisticsType.code'/>:
						<input type="text"  id="search_statisticsType_code" />
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='statisticsType.name'/>:
						 <input type="text"  id="search_statisticsType_name" />
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='statisticsType.remark'/>:
						 <input type="text"  id="search_statisticsType_remark" />
					</label>
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="statisticsTypeGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="statisticsTypeGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="statisticsType_buttonBar">
<!-- 			<ul class="toolBar"> -->
<!-- 				<li> -->
<%-- 					<a id="statisticsType_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="statisticsType_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="statisticsType_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%--      				<a class="settlebutton"  href="javaScript:setColShow('statisticsType_gridtable','com.huge.ihos.hr.statistics.model.StatisticsType')"><span><s:text name="button.setColShow" /></span></a> --%>
<!--    				</li> -->
<!-- 			</ul> -->
		</div>
		<div id="statisticsType_container">
		<div id="statisticsType_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div style="margin-left:20px;margin-bottom:2px;">
				<span style="vertical-align:middle;text-decoration:underline;cursor:pointer;float: right;" onclick="toggleExpandTreeWithShowFlag(this,'statisticsTypeTreeLeft')">展开</span>
				</div>
				<div id="statisticsTypeTreeLeft" class="ztree"></div>
			</div>
			<div id="statisticsType_layout-center" class="pane ui-layout-center">
		<div id="statisticsType_gridtable_div" class="grid-wrapdiv"  
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:900;height:550">
			<input type="hidden" id="statisticsType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="statisticsType_gridtable_addTile">
				<s:text name="statisticsTypeNew.title"/>
			</label> 
			<label style="display: none"
				id="statisticsType_gridtable_editTile">
				<s:text name="statisticsTypeEdit.title"/>
			</label>
			<label style="display: none"
				id="statisticsType_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="statisticsType_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_statisticsType_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="statisticsType_gridtable"></table>
		<div id="statisticsTypePager"></div>
</div>
	<div class="panelBar" id="statisticsType_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="statisticsType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="statisticsType_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="statisticsType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	</div>
</div>
</div>
</div>
