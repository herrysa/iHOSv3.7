
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function sysTableContentGridReload(){
		var urlString = "sysTableContentGridList";
		var name = jQuery("#sysTableContent_search_name").val();
		var code = jQuery("#sysTableContent_search_code").val();
		var sysFiled = jQuery("#sysTableContent_search_sysFiled").val();
		var disabled = jQuery("#sysTableContent_search_disabled").val();
		
		
		urlString=urlString+"?filter_LIKES_name="+name+"&filter_LIKES_bdinfo.tableName="+code;
		urlString=urlString+"&filter_EQB_bdinfo.sysFiled="+sysFiled+"&filter_EQB_disabled="+disabled;
		urlString=encodeURI(urlString);
		jQuery("#sysTableContent_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var sysTableContentLayout;
			  var sysTableContentGridIdString="#sysTableContent_gridtable";
	
	jQuery(document).ready(function() { 
		var hrDepartmentCurrentFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#sysTableContent_container").css("height",hrDepartmentCurrentFullSize);
		$('#sysTableContent_container').layout({ 
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
					gridResize(null,null,"sysTableContent_gridtable","single");
				}
			}
		});
		var initFlag = 0;
var sysTableContentGrid = jQuery(sysTableContentGridIdString);
    sysTableContentGrid.jqGrid({
    	url : "sysTableContentGridList",
    	editurl:"sysTableContentGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="sysTableContent.id" />',hidden:true,key:true},
{name:'bdinfo.tableName',index:'bdinfo.tableName',width : 160,align:'left',label : '<s:text name="sysTableContent.bdinfo.tableName" />',hidden:false,highsearch:true},
{name:'name',index:'name',width : 100,align:'left',label : '<s:text name="sysTableContent.name" />',hidden:false,highsearch:true},		
{name:'tableKind.id',index:'tableKind.id',width : 100,align:'left',label : '<s:text name="sysTableKind.id" />',hidden:true},
{name:'tableKind.name',index:'tableKind.name',width : 100,align:'left',label : '<s:text name="sysTableContent.tablekind" />',hidden:false,highsearch:true},
{name:'sn',index:'sn',align:'right',width : 60,label : '<s:text name="sysTableContent.sn" />',hidden:false,formatter:'integer',highsearch:true},	
{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="sysTableContent.remark" />',hidden:false,highsearch:true},
{name:'bdinfo.orderByField',index:'bdinfo.orderByField',width : 100,align:'left',label : '<s:text name="sysTableContent.bdinfo.orderByField" />',hidden:false,highsearch:true},
{name:'bdinfo.orderByFieldAsc',index:'bdinfo.orderByFieldAsc',width : 60,align:'center',label : '<s:text name="sysTableContent.orderByFieldAsc" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'bdinfo.sysField',index:'bdinfo.sysField',width : 60,align:'center',label : '<s:text name="sysTableContent.sysFiled" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'sumFlag',index:'sumFlag',width : 60,align:'center',label : '<s:text name="sysTableContent.sumFlag" />',hidden:true,formatter:'checkbox',highsearch:true},
{name:'bdinfo.statistics',index:'bdinfo.statistics',width : 60,align:'center',label : '<s:text name="sysTableContent.statistics" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'disabled',index:'disabled',width : 60,align:'center',label : '<s:text name="sysTableContent.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'changeUser.name',index:'changeUser.name',width : 80,align:'left',label : '<s:text name="sysTableContent.changeUser" />',hidden:true,highsearch:true},
{name:'changeDate',index:'changeDate',width : 100,align:'center',label : '<s:text name="sysTableContent.changeDate" />',hidden:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true}

        ],
        jsonReader : {
			root : "sysTableContents", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'sn',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="sysTableContentList.title" />',
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
			 gridContainerResize('sysTableContent','layout');
           var dataTest = {"id":"sysTableContent_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	  initFlag = initColumn('sysTableContent_gridtable','com.huge.ihos.hr.sysTables.model.SysTableContent',initFlag);
       	} 

    });
    jQuery(sysTableContentGrid).jqGrid('bindKeys');
    jQuery("#sysTableContent_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
    	
    	var zTree = $.fn.zTree.getZTreeObj("sysTableContentTreeLeft");  
        checkCount = zTree.getSelectedNodes(); 
        var classpurview = ""; 
        if(checkCount.length ==1){
            for(var i=0;i<checkCount.length;i++) { 
           	 if(checkCount[i].dataType=="tableContent"){
           		classpurview += checkCount[i].pId;
           	 }else{
                 classpurview += checkCount[i].id;
           	 }
            }  
            
        }else{
       	 alertMsg.error("请选择一个表类型。");
				return;
        }
    	
		var url = "editSysTableContent?navTabId=sysTableContent_gridtable&code="+classpurview;
		var winTitle='<s:text name="sysTableContentNew.title"/>';
		$.pdialog.open(url,'addSysTableContent',winTitle, {mask:true,width : 700,height : 300});
	}); 
	 jQuery("#sysTableContent_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
	        var sid = jQuery("#sysTableContent_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length !=1){
				return;
				}
			var winTitle='<s:text name="sysTableContentEdit.title"/>';
			var url = "editSysTableContent?navTabId=sysTableContent_gridtable&id="+sid;
			$.pdialog.open(url,'editSysTableContent',winTitle, {mask:true,width : 700,height : 300});
		}); 	
	 jQuery("#sysTableContent_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
			var url = "${ctx}/sysTableContentGridEdit?oper=del"
			var sid = jQuery("#sysTableContent_gridtable").jqGrid('getGridParam','selarrrow');
			if (sid == null || sid.length == 0) {
				alertMsg.error("请选择记录。");
				return;
			} else {
				for(var i = 0;i < sid.length; i++){
					var rowId = sid[i];
					var row = jQuery("#sysTableContent_gridtable").jqGrid('getRowData',rowId);
					
					if(row['bdinfo.sysFiled']=='Yes'){
						alertMsg.error("系统记录不能删除!");
						return;
					}
				}
				   jQuery.ajax({
			             url: 'sysTableStructureGridList?filter_INS_tableContent.id='+sid,
			             data: {},
			             type: 'post',
			             dataType: 'json',
			             async:false,
			             error: function(data){
			             },
			             success: function(data){
			             	if(data.sysTableStructures&&data.sysTableStructures.length>0){
			             		alertMsg.error("包含数据项的数据表不能删除!");
								return;
			             	}
			             	url = url+"&id="+sid+"&navTabId=sysTableContent_gridtable";
							alertMsg.confirm("确认删除？", {
								okCall : function() {
									$.post(url,function(data) {
										formCallBack(data);
										sysTableContentLeftTree();
									});
								}
							});
			             }
			         });
			}
		});
    sysTableContentLeftTree();
	//sysTableContentLayout.resizeAll();
  	});
	function sysTableContentLeftTree() {
		$.get("makeSysTableContentTree", {
			"_" : $.now()
		}, function(data) {
			var sysTableContentTreeData = data.treeNodesList;
			var sysTableContentTree = $.fn.zTree.init($("#sysTableContentTreeLeft"),
					ztreesetting_sysTableContentLeft, sysTableContentTreeData);
			var nodes = sysTableContentTree.getNodes();
			sysTableContentTree.expandNode(nodes[0], true, false, true);
		});
	}
	var ztreesetting_sysTableContentLeft = {
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
	     var urlString = "sysTableContentGridList";
	    
 	    if(treeNode.children){
	    	urlString=urlString+"?filter_EQS_tableKind.id="+treeNode.id;
	    }else{
	    	urlString=urlString+"?filter_EQS_id="+treeNode.id;	
	    }		    	
		urlString=encodeURI(urlString);
		jQuery("#sysTableContent_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
	}
</script>

<div class="page">
	<div class="pageHeader" id="sysTableContent_pageHeader">
			<div class="searchBar">	
			<div class="searchContent">
			<form id="sysTableContent_search_form" style="white-space: break-all;word-wrap:break-word;">				
				<label style="float:none;white-space:nowrap" >
						<s:text name='sysTableContent.bdinfo.tableName'/>:
      						 <input type="text" id="sysTableContent_search_code"/>
						</label>
				<label style="float:none;white-space:nowrap" >
						<s:text name='sysTableContent.name'/>:
      						 <input type="text" id="sysTableContent_search_name"/>
						</label>
				 <label style="float:none;white-space:nowrap" >
						<s:text name='sysTableContent.sysFiled'/>:
					 	<s:select id="sysTableContent_search_sysFiled" headerKey="" headerValue="--" 
							list="#{'1':'是','2':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
					</label>
				 <label style="float:none;white-space:nowrap" >
						<s:text name='sysTableContent.disabled'/>:
					 	<s:select id="sysTableContent_search_disabled" headerKey="" headerValue="--" 
							list="#{'1':'是','2':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
					</label>
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="sysTableContentGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>		
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="sysTableContentGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	
	<div class="pageContent">





<div class="panelBar" id="sysTableContent_buttonBar">
			<ul class="toolBar">
				<li><a id="sysTableContent_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="sysTableContent_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="sysTableContent_gridtable_edit_custom" class="changebutton"  href="javaScript:"
				  ><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<li>
     				<a class="settlebutton"  href="javaScript:setColShow('sysTableContent_gridtable','com.huge.ihos.hr.sysTables.model.SysTableContent')"><span><s:text name="button.setColShow" /></span></a>
   				 </li>
			</ul>
		</div>
		<div id="sysTableContent_container">
		<div id="sysTableContent_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div style="margin-left:20px;margin-bottom:2px;">
					<span style="vertical-align:middle;text-decoration:underline;cursor:pointer;float: right;" onclick="toggleExpandTreeWithShowFlag(this,'sysTableContentTreeLeft')">展开</span>
				</div>
				<div id="sysTableContentTreeLeft" class="ztree"></div>
			</div>
			<div id="sysTableContent_layout-center" class="pane ui-layout-center">
		<div id="sysTableContent_gridtable_div" class="grid-wrapdiv"  
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:900;height:550">
			<input type="hidden" id="sysTableContent_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="sysTableContent_gridtable_addTile">
				<s:text name="sysTableContentNew.title"/>
			</label> 
			<label style="display: none"
				id="sysTableContent_gridtable_editTile">
				<s:text name="sysTableContentEdit.title"/>
			</label>
			<label style="display: none"
				id="sysTableContent_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="sysTableContent_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_sysTableContent_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="sysTableContent_gridtable"></table>
		<div id="sysTableContentPager"></div>
</div>
	<div class="panelBar" id="sysTableContent_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="sysTableContent_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="sysTableContent_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="sysTableContent_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	</div>
</div>
</div>
</div>