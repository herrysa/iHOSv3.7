
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function hrPersonTypeGridReload(){
		var urlString = "hrPersonTypeGridList";
		var code = jQuery("#search_hrPersonType_code").val();
		var name = jQuery("#search_hrPersonType_name").val();
		var remark = jQuery("#search_hrPersonType_remark").val();
		var leaf = jQuery("#search_hrPersonType_leaf").val();
		var disabled = jQuery("#search_hrPersonType_disabled").val();
		var sysFiled = jQuery("#search_hrPersonType_sysFiled").val();
		
		urlString=urlString+"?filter_LIKES_code="+code+"&filter_LIKES_name="+name;
		urlString=urlString+"&filter_LIKES_remark="+remark+"&filter_EQB_sysFiled="+sysFiled;
		urlString=urlString+"&filter_EQB_leaf="+leaf+"&filter_EQB_disabled="+disabled;
		urlString=encodeURI(urlString);
		jQuery("#hrPersonType_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var hrPersonTypeLayout;
			  var hrPersonTypeGridIdString="#hrPersonType_gridtable";
	
	jQuery(document).ready(function() {
		var hrPersonTypeFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#hrPersonType_container").css("height",hrPersonTypeFullSize);
		$('#hrPersonType_container').layout({ 
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
					gridResize(null,null,"hrPersonType_gridtable","single");
				}
			}
		});
		
	var hrPersonTypeGrid = jQuery(hrPersonTypeGridIdString);
    hrPersonTypeGrid.jqGrid({
    	url : "hrPersonTypeGridList",
    	editurl:"hrPersonTypeGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'id',index:'id',align:'center',label : '<s:text name="hrPersonType.id" />',hidden:true,key:true},				
{name:'code',index:'code',align:'left',width : 100,label : '<s:text name="hrPersonType.code" />',hidden:false,highsearch:true},
{name:'name',index:'name',align:'left',width : 100,label : '<s:text name="hrPersonType.name" />',hidden:false,highsearch:true},	
{name:'parentType.name',index:'parentType.name',align:'left',width : 100,label : '<s:text name="hrPersonType.parentType" />',hidden:false,highsearch:true},
// {name:'makeDate',index:'makeDate',align:'center',width : 80,label : '<s:text name="hrPersonType.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
// {name:'maker.name',index:'maker.name',align:'left',width : 80,label : '<s:text name="hrPersonType.maker" />',hidden:false},
// {name:'sn',index:'sn',align:'right',width : 50,label : '<s:text name="hrPersonType.sn" />',hidden:false,formatter:'integer',highsearch:true},
{name:'leaf',index:'leaf',align:'center',width : 50,label : '<s:text name="hrPersonType.leaf" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'sysFiled',index:'sysFiled',align:'center',width : 50,label : '<s:text name="hrPersonType.sysFiled" />',hidden:false,formatter:'checkbox',highsearch:true},
{name:'disabled',index:'disabled',align:'center',width : 50,label : '<s:text name="hrPersonType.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},				
{name:'remark',index:'remark',align:'left',width : 250,label : '<s:text name="hrPersonType.remark" />',hidden:false,highsearch:true}		
				

        ],
        jsonReader : {
			root : "hrPersonTypes", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'id',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="hrPersonTypeList.title" />',
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
                   setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrPersonTypeRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
                 }
               }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
           var dataTest = {"id":"hrPersonType_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
       	} 

    });
    jQuery(hrPersonTypeGrid).jqGrid('bindKeys');
    
	
	
    jQuery("#hrPersonType_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
    	  var zTree = $.fn.zTree.getZTreeObj("hrPersonTypeTreeLeft"); 
          var nodes = zTree.getSelectedNodes();
          if(nodes.length!=1){
        	alertMsg.error("请选择人员类别。");
          	return;
       	  }
          jQuery.ajax({
              url: 'hrPersonCurrentGridList?filter_EQS_status.id='+nodes[0].id,
              data: {},
              type: 'post',
              dataType: 'json',
              async:false,
              error: function(data){
              },
              success: function(data){
            	  if(data.hrPersonCurrents&&data.hrPersonCurrents.length>0){
            		  alertMsg.error("请选择不包含人员的人员类别。");
                      return;
            	  }
            	  var url = "editHrPersonType?navTabId=hrPersonType_gridtable&parentId="+nodes[0].id;
          		  var winTitle='<s:text name="hrPersonTypeNew.title"/>';
          		  $.pdialog.open(url,'addHrPersonType',winTitle, {mask:true,width : 700,height : 250});
              }
          });
	}); 
     jQuery("#hrPersonType_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
        var sid = jQuery("#hrPersonType_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null|| sid.length != 1){       	
			alertMsg.error("请选择一条记录。");
			return;
			}
		var winTitle='<s:text name="hrPersonTypeEdit.title"/>';
		var url = "editHrPersonType?popup=true&id="+sid+"&navTabId=hrPersonType_gridtable";
		$.pdialog.open(url,'editHrPersonType',winTitle, {mask:true,width : 700,height : 270});
	}); 
     jQuery("#hrPersonType_gridtable_del_custom").unbind( 'click' ).bind("click",function(){  
       	 var url = "${ctx}/hrPersonTypeGridEdit?oper=del";
            var sid = jQuery("#hrPersonType_gridtable").jqGrid('getGridParam','selarrrow');
            if(sid==null|| sid.length == 0){       	
    			alertMsg.error("请选择记录。");
    			return;
    			}else {
    				for(var i = 0;i < sid.length; i++){
    					var rowId = sid[i];
    					var row = jQuery("#hrPersonType_gridtable").jqGrid('getRowData',rowId);
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
    		            	  url = url+"&id="+sid+"&navTabId=hrPersonType_gridtable";
    		    				alertMsg.confirm("确认删除？", {
    		    					okCall : function() {
    		    						$.post(url,function(data) {
    		    							formCallBack(data);
    		    							// delete 
    		    							if(data.statusCode!=200){
    		    							   return;
    		    							  }
    		    							 var hrPersonTypeTreeObj = $.fn.zTree.getZTreeObj("hrPersonTypeTreeLeft");
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
     hrPersonTypeLeftTree();
  	});
	function hrPersonTypeLeftTree() {
		$.get("makeHrPersonTypeTree", {
			"_" : $.now()
		}, function(data) {
			var hrPersonTypeTreeData = data.hrPersonTypeTreeNodes;
			var hrPersonTypeTree = $.fn.zTree.init($("#hrPersonTypeTreeLeft"),
					ztreesetting_hrPersonTypeLeft, hrPersonTypeTreeData);
			var nodes = hrPersonTypeTree.getNodes();
			hrPersonTypeTree.expandNode(nodes[0], true, false, true);
		});
	}
	var ztreesetting_hrPersonTypeLeft = {
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
	     var urlString = "hrPersonTypeGridList";
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
		jQuery("#hrPersonType_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
	}
	function viewHrPersonTypeRecord(sid){
		var winTitle='<s:text name="hrPersonTypeView.title"/>';
		var url = "editHrPersonType?popup=true&id="+sid+"&navTabId=hrPersonType_gridtable";
		 url+="&oper=view";
		$.pdialog.open(url,'viewHrPersonType',winTitle, {mask:true,width : 700,height : 250});
	}
</script>

<div class="page">
	<div class="pageHeader" id="hrPersonType_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
					<form id="hrPersonType_search_form" >
					<label style="float:none;white-space:nowrap" >
							<s:text name='hrPersonType.code'/>:
						 	<input type="text"  id="search_hrPersonType_code" />
						</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
							<s:text name='hrPersonType.name'/>:
						 	<input type="text"  id="search_hrPersonType_name" />
						</label>&nbsp;&nbsp;
						<label style="float:none;white-space:nowrap" >
							<s:text name='hrPersonType.remark'/>:
						 	<input type="text"  id="search_hrPersonType_remark" />
						</label>&nbsp;&nbsp;
						 <label style="float:none;white-space:nowrap" >
						<s:text name='hrPersonType.disabled'/>:
					 	<s:select id="search_hrPersonType_disabled" headerKey="" headerValue="--" 
							list="#{'1':'是','0':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
						</label>&nbsp;&nbsp;	
						<div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="hrPersonTypeGridReload()"><s:text name='button.search'/></button>
							</div>
						</div>
				</form>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="hrPersonTypeGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a id="hrPersonType_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a>
				</li>
				<li>
					<a id="hrPersonType_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a id="hrPersonType_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a>
				</li>
			</ul>
		</div>
		<div id="hrPersonType_container">
		<div id="hrPersonType_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div style="margin-left:20px;margin-bottom:2px;">
				<span style="vertical-align:middle;text-decoration:underline;cursor:pointer;float: right;" onclick="toggleExpandTree(this,'hrPersonTypeTreeLeft')">展开</span>
				</div>
				<div id="hrPersonTypeTreeLeft" class="ztree"></div>
			</div>
			<div id="hrPersonType_layout-center" class="pane ui-layout-center">
		<div id="hrPersonType_gridtable_div" layoutH="122" class="grid-wrapdiv" class="unitBox" 
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:id;width:900;height:550">
			<input type="hidden" id="hrPersonType_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="hrPersonType_gridtable_addTile">
				<s:text name="hrPersonTypeNew.title"/>
			</label> 
			<label style="display: none"
				id="hrPersonType_gridtable_editTile">
				<s:text name="hrPersonTypeEdit.title"/>
			</label>
			<label style="display: none"
				id="hrPersonType_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="hrPersonType_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_hrPersonType_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="hrPersonType_gridtable"></table>
		<div id="hrPersonTypePager"></div>
</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="hrPersonType_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="hrPersonType_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="hrPersonType_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>
</div>