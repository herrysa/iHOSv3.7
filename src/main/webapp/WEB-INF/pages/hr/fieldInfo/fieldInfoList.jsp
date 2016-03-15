
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function fieldInfoGridReload(){
		var urlString = "fieldInfoGridList";
		var name = jQuery("#fieldInfo_search_name").val();
		var code = jQuery("#fieldInfo_search_code").val();
		var sysFiled = jQuery("#fieldInfo_search_sysFiled").val();
		var disabled = jQuery("#fieldInfo_search_disabled").val();
		
		
		urlString=urlString+"?filter_LIKES_name="+name+"&filter_LIKES_code="+code;
		urlString=urlString+"&filter_EQB_sysFiled="+sysFiled+"&filter_EQB_disabled="+disabled;
		urlString=encodeURI(urlString);
		jQuery("#fieldInfo_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var fieldInfoLayout;
			  var fieldInfoGridIdString="#fieldInfo_gridtable";
	
	jQuery(document).ready(function() { 
		var hrDepartmentCurrentFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#fieldInfo_container").css("height",hrDepartmentCurrentFullSize);
		$('#fieldInfo_container').layout({ 
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
					gridResize(null,null,"fieldInfo_gridtable","single");
				}
			}
		});
var fieldInfoGrid = jQuery(fieldInfoGridIdString);
    fieldInfoGrid.jqGrid({
    	url : "fieldInfoGridList",
    	editurl:"fieldInfoGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',width : 100,align:'center',label : '<s:text name="fieldInfo.id" />',hidden:true,key:true},
{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="fieldInfo.code" />',hidden:false,highsearch:true},
{name:'name',index:'name',width : 100,align:'left',label : '<s:text name="fieldInfo.name" />',hidden:false,highsearch:true},
{name:'bdinfo.bdInfoName',index:'bdinfo.bdInfoName',width : 100,align:'left',label : '<s:text name="fieldInfo.bdinfo" />',hidden:false,highsearch:true},
{name:'sn',index:'sn',width : 60,align:'right',label : '<s:text name="fieldInfo.sn" />',hidden:false,formatter:'integer',highsearch:true},
{name:'dataType',index:'dataType',width : 100,align:'center',label : '<s:text name="fieldInfo.dataType" />',hidden:false,formatter : 'select',edittype : 'select',editoptions : {value : '1:字符型;2:浮点型;3:布尔型;4:日期型;5:整数型;6:货币型;7:图片型'},highsearch:true},
{name:'dataLength',index:'dataLength',width : 80,align:'right',label : '<s:text name="fieldInfo.dataLength" />',hidden:false,formatter:'integer',highsearch:true},				
{name:'dataDecimal',index:'dataDecimal',width : 80,align:'right',label : '<s:text name="fieldInfo.dataDecimal" />',hidden:false,formatter:'integer',highsearch:true},				
{name:'fieldDefault',index:'fieldDefault',width : 100,align:'left',label : '<s:text name="fieldInfo.fieldDefault" />',hidden:false,highsearch:true},
{name:'codeId',index:'codeId',align:'left',width : 150,label : '<s:text name="fieldInfo.codeId" />',hidden:false,highsearch:true},				
{name:'codeName',index:'codeName',width : 100,align:'left',label : '<s:text name="fieldInfo.codeName" />',hidden:false,highsearch:true},					
{name:'read',index:'read',width : 60,align:'center',label : '<s:text name="fieldInfo.read" />',hidden:false,formatter:'checkbox',highsearch:true},				
{name:'notNull',index:'fieldNotNullFlag',width : 60,align:'center',label : '<s:text name="fieldInfo.notNull" />',hidden:false,formatter:'checkbox',highsearch:true},				
{name:'batchEdit',index:'batchEdit',width : 60,align:'center',label : '<s:text name="fieldInfo.batchEdit" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'sysFiled',index:'sysFiled',width : 60,align:'center',label : '<s:text name="fieldInfo.sysFiled" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'statistics',index:'statistics',width : 60,align:'center',label : '<s:text name="fieldInfo.statistics" />',hidden:false,formatter:'checkbox'},				
{name:'disabled',index:'disabled',width : 60,align:'center',label : '<s:text name="fieldInfo.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="fieldInfo.remark" />',hidden:false,highsearch:true},
{name:'changeUser.name',index:'changeUser.name',width : 100,align:'left',label : '<s:text name="fieldInfo.changeUser" />',hidden:false,highsearch:true},
{name:'changeDate',index:'changeDate',width : 100,align:'center',label : '<s:text name="fieldInfo.changeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true}                  
        ],
        jsonReader : {
			root : "fieldInfoes", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'id',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="fieldInfoList.title" />',
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
			 gridContainerResize('fieldInfo','layout');
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"fieldInfo_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	  // makepager("fieldInfo_gridtable");
       	} 

    });
    jQuery(fieldInfoGrid).jqGrid('bindKeys');
    
    jQuery("#fieldInfo_gridtable_add_custom").unbind( 'click' ).bind("click",function(){    	
    	var zTree = $.fn.zTree.getZTreeObj("fieldInfoTreeLeft");  
        checkCount = zTree.getSelectedNodes(); 
        var classpurview = ""; 
        if(checkCount.length ==1){
            for(var i=0;i<checkCount.length;i++) { 
           	 if(checkCount[i].dataType=="tableContent"){
           		classpurview += checkCount[i].id;
           	 }
            }  
            
        }    	
		var url = "editFieldInfo?navTabId=fieldInfo_gridtable";
		if(classpurview){
			url+="&code="+classpurview;
		}
		var winTitle='<s:text name="fieldInfoNew.title"/>';
		$.pdialog.open(url,'addFieldInfo',winTitle, {mask:true,width : 700,height : 400});
	}); 
    jQuery("#fieldInfo_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
        var sid = jQuery("#fieldInfo_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null || sid.length !=1){
			return;
			}
		var winTitle='<s:text name="fieldInfoEdit.title"/>';
		var url = "editFieldInfo?navTabId=fieldInfo_gridtable&id="+sid;
		$.pdialog.open(url,'editFieldInfo',winTitle, {mask:true,width : 700,height : 400});
	}); 	
    jQuery("#fieldInfo_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
		var url = "${ctx}/fieldInfoGridEdit?oper=del"
		var sid = jQuery("#fieldInfo_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} else {
			for(var i = 0;i < sid.length; i++){
				var rowId = sid[i];
				var row = jQuery("#fieldInfo_gridtable").jqGrid('getRowData',rowId);
				
				if(row['sysFiled']=='Yes'){
					alertMsg.error("系统记录不能删除!");
					return;
				}
			}
			url = url+"&id="+sid+"&navTabId=fieldInfo_gridtable";
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		}
	});
    fieldInfoLeftTree();			
  	});
	function fieldInfoLeftTree() {
		$.get("makeSysTableContentTree", {
			"_" : $.now()
		}, function(data) {
			var fieldInfoTreeData = data.treeNodesList;
			var fieldInfoTree = $.fn.zTree.init($("#fieldInfoTreeLeft"),
					ztreesetting_fieldInfoLeft, fieldInfoTreeData);
			var nodes = fieldInfoTree.getNodes();
			fieldInfoTree.expandNode(nodes[0], true, false, true);
		});
	}
	var ztreesetting_fieldInfoLeft = {
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
	     var urlString = "fieldInfoGridList";
	    
//  	    if(treeNode.children){
// 	    	urlString=urlString+"?filter_EQS_tableKind.tableKindId="+treeNode.id;
// 	    }else{
	    //	urlString=urlString+"?filter_EQS_tableContent.id="+treeNode.id;	
// 	    }		    	
		urlString=encodeURI(urlString);
		jQuery("#fieldInfo_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
	}
</script>

<div class="page">
	<div class="pageHeader" id="fieldInfo_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
			<form id="fieldInfo_search_form"  style="white-space: break-all;word-wrap:break-word;" >				
				<label style="float:none;white-space:nowrap" >
						<s:text name='fieldInfo.code'/>:
      						 <input type="text" id="fieldInfo_search_code"/>
						</label>
				<label style="float:none;white-space:nowrap" >
						<s:text name='fieldInfo.name'/>:
      						 <input type="text" id="fieldInfo_search_name"/>
						</label>
				 <label style="float:none;white-space:nowrap" >
						<s:text name='fieldInfo.sysFiled'/>:
					 	<s:select id="fieldInfo_search_sysFiled" headerKey="" headerValue="--" 
							list="#{'1':'是','2':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
					</label>
				 <label style="float:none;white-space:nowrap" >
						<s:text name='fieldInfo.disabled'/>:
					 	<s:select id="fieldInfo_search_disabled" headerKey="" headerValue="--" 
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
<%-- 									<button type="button" onclick="fieldInfoGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="fieldInfo_buttonBar">
			<ul class="toolBar">
				<li>
					<a id="fieldInfo_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span>
				</a>
				</li>
				<li>
					<a id="fieldInfo_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li>
					<a id="fieldInfo_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span>
				</a>
				</li>
				<li>
     				<a class="settlebutton"  href="javaScript:setColShow('fieldInfo_gridtable','com.huge.ihos.accounting.bdinfo.model.FieldInfo')"><span><s:text name="button.setColShow" /></span></a>
   				 </li>
			</ul>
		</div>
		<div id="fieldInfo_container">
			<div id="fieldInfo_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div id="fieldInfoTreeLeft" class="ztree"></div>
			</div>
			<div id="fieldInfo_layout-center" class="pane ui-layout-center">
		<div id="fieldInfo_gridtable_div" class="grid-wrapdiv" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:fieldId;width:960;height:628">
			<input type="hidden" id="fieldInfo_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="fieldInfo_gridtable_addTile">
				<s:text name="fieldInfoNew.title"/>
			</label> 
			<label style="display: none"
				id="fieldInfo_gridtable_editTile">
				<s:text name="fieldInfoEdit.title"/>
			</label>
			<label style="display: none"
				id="fieldInfo_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="fieldInfo_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_fieldInfo_gridtable" class='loading ui-state-default ui-state-active'></div>
 <table id="fieldInfo_gridtable"></table>
		<div id="fieldInfoPager"></div>
</div>
	<div class="panelBar" id="fieldInfo_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="fieldInfo_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="fieldInfo_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="fieldInfo_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
	</div>
</div>
</div>
</div>