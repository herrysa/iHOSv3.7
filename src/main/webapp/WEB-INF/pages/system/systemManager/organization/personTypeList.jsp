
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function personTypeGridReload(){
		var urlString = "personTypeGridList";
		var code = jQuery("#search_personType_code").val();
		var name = jQuery("#search_personType_name").val();
		var remark = jQuery("#search_personType_remark").val();
		//var leaf = jQuery("#search_personType_leaf").val();
		var disabled = jQuery("#search_personType_disabled").val();
		//var sysFiled = jQuery("#search_personType_sysFiled").val();
		
		urlString=urlString+"?filter_LIKES_code="+code+"&filter_LIKES_name="+name;
		urlString=urlString+"&filter_LIKES_remark="+remark; //+"&filter_EQB_sysFiled="+sysFiled;
		urlString=urlString+"&filter_EQB_disabled="+disabled; //"&filter_EQB_leaf="+leaf+
		urlString=encodeURI(urlString);
		jQuery("#personType_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var personTypeLayout;
	var personTypeGridIdString="#personType_gridtable";
	
	jQuery(document).ready(function() {
		var personTypeFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#personType_container").css("height",personTypeFullSize);
		$('#personType_container').layout({ 
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
					gridResize(null,null,"personType_gridtable","single");
				}
			}
		});
		
	var personTypeGrid = jQuery(personTypeGridIdString);
    personTypeGrid.jqGrid({
    	url : "personTypeGridList",
    	editurl:"personTypeGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
		{name:'id',index:'id',align:'center',label : '<s:text name="personType.id" />',hidden:true,key:true},				
		{name:'code',index:'code',align:'left',width : 100,label : '<s:text name="personType.code" />',hidden:false,highsearch:true},
		{name:'name',index:'name',align:'left',width : 100,label : '<s:text name="personType.name" />',hidden:false,highsearch:true},	
		{name:'parentType.name',index:'parentType.name',align:'left',width : 100,label : '<s:text name="personType.parentType" />',hidden:false,highsearch:true},
// {name:'makeDate',index:'makeDate',align:'center',width : 80,label : '<s:text name="personType.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
// {name:'maker.name',index:'maker.name',align:'left',width : 80,label : '<s:text name="personType.maker" />',hidden:false},
// {name:'sn',index:'sn',align:'right',width : 50,label : '<s:text name="personType.sn" />',hidden:false,formatter:'integer',highsearch:true},
		{name:'leaf',index:'leaf',align:'center',width : 50,label : '<s:text name="personType.leaf" />',hidden:false,formatter:'checkbox',highsearch:true},
		{name:'sysFiled',index:'sysFiled',align:'center',width : 50,label : '<s:text name="personType.sysFiled" />',hidden:false,formatter:'checkbox',highsearch:true},
		{name:'disabled',index:'disabled',align:'center',width : 50,label : '<s:text name="personType.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},				
		{name:'remark',index:'remark',align:'left',width : 250,label : '<s:text name="personType.remark" />',hidden:false,highsearch:true}		
        ],
        jsonReader : {
			root : "personTypes", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'code',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="personTypeList.title" />',
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
			 gridContainerResize('personType','layout');
//            if(jQuery(this).getDataIDs().length>0){
//               jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
//             }
			 var rowNum = jQuery(this).getDataIDs().length;
             if(rowNum>0){
                 var rowIds = jQuery(this).getDataIDs();
                 var ret = jQuery(this).jqGrid('getRowData');
                 var id='';
                 for(var i=0;i<rowNum;i++){
                   id=rowIds[i];
                   setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPersonTypeRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
                 }
                 
                  var treeObj = $.fn.zTree.getZTreeObj("personTypeTreeLeft");
					if(treeObj) {
						var selectedNode = treeObj.getSelectedNodes();
						if(selectedNode && selectedNode.length == 1) {
							var sid = selectedNode[0].id;
							if(sid != "-1" && selectedNode[0].pId != "-1") {
								jQuery(this).jqGrid("setSelection",sid);
							}
						}
					} 
               }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
           var dataTest = {"id":"personType_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
       	} 

    });
    jQuery(personTypeGrid).jqGrid('bindKeys');
    
	
	
    jQuery("#personType_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
    	  var zTree = $.fn.zTree.getZTreeObj("personTypeTreeLeft"); 
          var nodes = zTree.getSelectedNodes();
          if(nodes.length!=1){
        	alertMsg.error("请选择人员类别。");
          	return;
       	  }
          var url = "editPersonType?navTabId=personType_gridtable&parentId="+nodes[0].id;
  		  var winTitle='<s:text name="personTypeNew.title"/>';
  		  $.pdialog.open(url,'addPersonType',winTitle, {mask:true,width : 700,height : 250});
          /* jQuery.ajax({
              url: 'personCurrentGridList?filter_EQS_status.id='+nodes[0].id,
              data: {},
              type: 'post',
              dataType: 'json',
              async:false,
              error: function(data){
              },
              success: function(data){
            	  if(data.personCurrents&&data.personCurrents.length>0){
            		  alertMsg.error("请选择不包含人员的人员类别。");
                      return;
            	  }
            	  var url = "editHrPersonType?navTabId=hrPersonType_gridtable&parentId="+nodes[0].id;
          		  var winTitle='<s:text name="hrPersonTypeNew.title"/>';
          		  $.pdialog.open(url,'addHrPersonType',winTitle, {mask:true,width : 700,height : 250});
              }
          }); */
	}); 
     jQuery("#personType_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
        var sid = jQuery("#personType_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
			}
		var winTitle='<s:text name="personTypeEdit.title"/>';
		var url = "editPersonType?popup=true&id="+sid+"&navTabId=personType_gridtable";
		$.pdialog.open(url,'editpersonType',winTitle, {mask:true,width : 700,height : 270});
	}); 
     jQuery("#personType_gridtable_del_custom").unbind( 'click' ).bind("click",function(){  
       	 var url = "${ctx}/personTypeGridEdit?oper=del";
            var sid = jQuery("#personType_gridtable").jqGrid('getGridParam','selarrrow');
            if(sid==null|| sid.length == 0){       	
    			alertMsg.error("请选择记录。");
    			return;
    			}else {
    				for(var i = 0;i < sid.length; i++){
    					var rowId = sid[i];
    					var row = jQuery("#personType_gridtable").jqGrid('getRowData',rowId);
    					if(row['sysFiled']=="Yes"){
    						alertMsg.error("系统记录不能删除!");
    						return;
    					}
    				}
    				 jQuery.ajax({
    		              url: 'hrPersonCurrentGridList?filter_INS_status.id='+sid,
    		              data: {},
    		              type: 'post',
    		              dataType: 'json',
    		              async:false,
    		              error: function(data){
    		              },
    		              success: function(data){
    		            	  if(data.hrPersonCurrents&&data.hrPersonCurrents.length>0){
    		            		  alertMsg.error("包含人员的人员类别记录不能删除。");
    		                      return;
    		            	  }
    		            	  url = url+"&id="+sid+"&navTabId=personType_gridtable";
    		    				alertMsg.confirm("确认删除？", {
    		    					okCall : function() {
    		    						$.post(url,function(data) {
    		    							formCallBack(data);
    		    							// delete 
    		    							if(data.statusCode!=200){
    		    							   return;
    		    							  }
    		    							 var hrPersonTypeTreeObj = $.fn.zTree.getZTreeObj("personTypeTreeLeft");
    		    							 for(var i=0;i<sid.length;i++){
    		    							       var delId = sid[i];
    		    							       var node = hrPersonTypeTreeObj.getNodeByParam("id", delId, null);
    		    							       hrPersonTypeTreeObj.removeNode(node);
    		    							  }
    		    						});
    		    					}
    		    				});
    		              }
    		          });
    				
    				
    				
    				
    			}
    	}); 
	//hrPersonTypeLayout.resizeAll();
     personTypeLeftTree();
  	});
	function personTypeLeftTree() {
		$.get("makePersonTypeTree", {
			"_" : $.now()
		}, function(data) {
			var personTypeTreeData = data.personTypeTreeNodes;
			var personTypeTree = $.fn.zTree.init($("#personTypeTreeLeft"),
					ztreesetting_personTypeLeft, personTypeTreeData);
			var rootnode = personTypeTree.getNodeByParam("id","-1",null);
			personTypeTree.selectNode(rootnode);
			var nodes = personTypeTree.getNodes();
			personTypeTree.expandNode(nodes[0], true, false, true);
		});
	}
	var ztreesetting_personTypeLeft = {
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
	     var urlString = "personTypeGridList";
	     if(treeNode.subSysTem!="ALL"){
	   		if(treeNode.children){
	    	 var ids=treeNode.id;
	    	 $.each(treeNode.children,function(n,value) {  
	    		 ids+=","+treeNode.children[n].id;
	    	 });
	    	 urlString=urlString+"?filter_INS_id="+ids;	
	     }else{
	    	 urlString=urlString+"?filter_EQS_id="+treeNode.id;	
	     }
	     }
		urlString=encodeURI(urlString);
		jQuery("#personType_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
		//点击树节点，jqGrid选中条目
		/* if(treeNode.id != "-1" && (treeNode.pId != "-1" || (treeNode.pId == "-1" && !treeNode.children))) {
			setTimeout(function(){
				jQuery("#personType_gridtable").jqGrid("setSelection",treeNode.id);
			},100);
		} */
	}
	function viewPersonTypeRecord(sid){
		var winTitle='<s:text name="personTypeView.title"/>';
		var url = "editPersonType?popup=true&id="+sid+"&navTabId=personType_gridtable";
		 url+="&oper=view";
		$.pdialog.open(url,'viewPersonType',winTitle, {mask:true,width : 700,height : 250});
	}
	function reloadPersonTypeGrid(nodeId) {
		if(nodeId) {
			var treeObj = $.fn.zTree.getZTreeObj("personTypeTreeLeft");
			var node = treeObj.getNodeByParam("id",nodeId,null);
			var ids=node.id;
			$.each(node.children,function(n,value) {
				ids+=","+node.children[n].id;
				$.each(node.children[n].children,function(m,value1) {
					ids+=","+node.children[n].children[m].id;
				});
			});
			var urlString = "personTypeGridList?filter_INS_id=" + ids;
			urlString=encodeURI(urlString);
			jQuery("#personType_gridtable").jqGrid("setGridParam",{url:urlString,page:1}).trigger("reloadGrid");
		}
	}
</script>

<div class="page">
	<div class="pageHeader" id="personType_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
					<form id="personType_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
							<s:text name='personType.code'/>:
						 	<input type="text"  id="search_personType_code" />
						</label>
					<label style="float:none;white-space:nowrap" >
							<s:text name='personType.name'/>:
						 	<input type="text"  id="search_personType_name" />
						</label>
						<label style="float:none;white-space:nowrap" >
							<s:text name='personType.remark'/>:
						 	<input type="text"  id="search_personType_remark" />
						</label>
						 <label style="float:none;white-space:nowrap" >
						<s:text name='personType.disabled'/>:
					 	<s:select id="search_personType_disabled" headerKey="" headerValue="--" 
							list="#{'1':'是','0':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
						</label>	
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="personTypeGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="personTypeGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div></li> -->
					
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="personType_buttonBar">
			<ul class="toolBar">
				<li>
					<a id="personType_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a id="personType_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="personType_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
				</li>
			</ul>
		</div>
		<div id="personType_container">
		<div id="personType_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div style="margin-left:20px;margin-bottom:2px;">
				<span style="vertical-align:middle;text-decoration:underline;cursor:pointer;float: right;" onclick="toggleExpandTreeWithShowFlag(this,'personTypeTreeLeft',true)">展开</span>
				</div>
				<div id="personTypeTreeLeft" class="ztree"></div>
			</div>
			<div id="personType_layout-center" class="pane ui-layout-center">
		<div id="personType_gridtable_div" class="grid-wrapdiv"  
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:900;height:550">
			<input type="hidden" id="personType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="personType_gridtable_addTile">
				<s:text name="personTypeNew.title"/>
			</label> 
			<label style="display: none"
				id="personType_gridtable_editTile">
				<s:text name="personTypeEdit.title"/>
			</label>
			<label style="display: none"
				id="personType_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="personType_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_personType_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="personType_gridtable"></table>
		<div id="personTypePager"></div>
</div>
	<div class="panelBar" id="personType_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="personType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="personType_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="personType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>
</div>