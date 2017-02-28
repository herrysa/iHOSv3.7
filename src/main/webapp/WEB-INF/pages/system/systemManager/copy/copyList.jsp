
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function copyGridReload(){
		var urlString = "copyGridList";
		var copycode = jQuery("#copy_copycode").val();
		var copyname = jQuery("#copy_copyname").val();
		var copyshort = jQuery("#copy_copyshort").val();
		var cwmanager = jQuery("#copy_cwmanager").val();
		var orgCode = jQuery("#copy_orgCode").val();
	
		urlString=urlString+"?filter_LIKES_copycode="+copycode+"&filter_LIKES_copyname="+copyname+"&filter_LIKES_copyshort="+copyshort+"&filter_LIKES_cwmanager="+cwmanager+"&filter_LIKES_org.orgCode="+orgCode;
		urlString=encodeURI(urlString);
		jQuery("#copy_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var copyLayout;
			  var copyGridIdString="#copy_gridtable";
	
	jQuery(document).ready(function() {
		var copyFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#copy_container").css("height",copyFullSize);
		$('#copy_container').layout({ 
			applyDefaultStyles: false ,
			west__size : 280,
			spacing_open:5,//边框的间隙  
			spacing_closed:5,//关闭时边框的间隙 
			resizable :true,
			resizerClass :"ui-layout-resizer-blue",
			slidable :true,
			resizerDragOpacity :1, 
			resizerTip:"可调整大小",//鼠标移到边框时，提示语
			onresize_end : function(paneName,element,state,options,layoutName){
				//zzhJsTest.debug("resize:"+paneName);
				if("center" == paneName){
					gridResize(null,null,"copy_gridtable","single");
				}
			}
			
		});
		
var copyGrid = jQuery(copyGridIdString);
    copyGrid.jqGrid({
    	url : "copyGridList",
    	editurl:"copyGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'copycode',index:'copycode',align:'center',width:70,label : '<s:text name="copy.copycode" />',hidden:false,key:true},				
{name:'copyname',index:'copyname',align:'left',label : '<s:text name="copy.copyname" />',hidden:false},				
{name:'copyshort',index:'copyshort',align:'left',label : '<s:text name="copy.copyshort" />',hidden:false},				
{name:'cwmanager',index:'cwmanager',align:'left',width:50,label : '<s:text name="copy.cwmanager" />',hidden:false},				
{name:'periodPlan.planName',index:'periodPlan.planName',align:'left',width:50,label : '<s:text name="copy.periodPlan.planName" />',hidden:false},				
        ],
        jsonReader : {
			root : "copies", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'copycode',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="copyList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:true,
        onSelectRow: function(rowid) {
       
       	},
		 gridComplete:function(){
           if(jQuery(this).getDataIDs().length>0){
              //jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"copy_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("copy_gridtable");
       	} 

    });
    jQuery(copyGrid).jqGrid('bindKeys');
    refreshCopyOrgTree();
	//copyLayout.resizeAll();
  	});
	
		var selectId = "";
	    var tableId = "copy_gridtable"; var addCopyUrl = "editCopy";
	    jQuery("#copy_gridtable_addlocal").bind('click',function(){
			if(selectId==-1 || selectId == ""){
				selectId="";
				alert("请选择一个单位");
				return;
			}
			var url = addCopyUrl+"?popup=true&orgCode="+selectId+"&navTabId="+tableId+"&editType=new";
			var winTitle="<s:text name='copyNew.title'/>";
			//alert(url);
			url = encodeURI(url);
			$.pdialog.open(url, 'addCopy', winTitle, {mask:false,width:400,height:300,resizable:false,maxable:false});　
		});
	
	
		function reloadCopyGrid(e,treeId, treeNode){
			var treeId = treeNode.id;
			selectId = treeId;
			urlString = "copyGridList";
			if(treeId!='-1' && treeId!=""){
				jQuery("#copy_orgCode").val(selectId);
				urlString += "?filter_LIKES_org.orgCode="+treeId+"*";
			} else {
				jQuery("#copy_orgCode").val("");
			}
			urlString=encodeURI(urlString);
			jQuery("#copy_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		}
		var ztreesetting_org_copy = {
			view : {
				showLine : true,
				selectedMulti : false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onClick: reloadCopyGrid
			}
		};
	
	function refreshCopyOrgTree(){
		jQuery.ajax({
		    url: 'makeOrgTree',
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		        alert(data);
		    },
		    success: function(data){
		        //alert(data.ztreeList);
		        setTimeout(function(){
		        	orgTree = jQuery.fn.zTree.init(jQuery("#copyOrgTree"), ztreesetting_org_copy,data.ztreeList);
		        	var rootnode = orgTree.getNodeByParam("id","-1",null);
		        	orgTree.selectNode(rootnode);
		        },100);
		    }
		});
	}
</script>

<div class="page">

	<div class="pageHeader">
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td><s:text name='copy.copycode'/>:
							<input type="text"	id="copy_copycode" >
						</td>
						<td><s:text name='copy.copyname'/>:
						 	<input type="text"	id="copy_copyname" >
						 </td>
						<td><s:text name='copy.copyshort'/>:
						 	<input type="text"	id="copy_copyshort" >
						 </td>
						<td><s:text name='copy.cwmanager'/>:
						 	<input type="text"	id="copy_cwmanager" >
						 </td>
						 <td>
						 	<input type="hidden"	id="copy_orgCode" >
						 </td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="copyGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar">
			<ul class="toolBar">
				<li><a id="copy_gridtable_addlocal" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="copy_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="copy_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
			
			</ul>
		</div>
		<div id="copy_container">
			<div id="copy_layout-west" class="pane ui-layout-west" 
				style="float: left; display: block; overflow: auto;">
				<DIV id="copyOrgTree" class="ztree"></DIV>
			</div>
			<div id="copy_layout-center"
				class="pane ui-layout-center">
		<div id="copy_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:copycode;width:400;height:300">
			<input type="hidden" id="copy_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="copy_gridtable_addTile">
				<s:text name="copyNew.title"/>
			</label> 
			<label style="display: none"
				id="copy_gridtable_editTile">
				<s:text name="copyEdit.title"/>
			</label>
			<label style="display: none"
				id="copy_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="copy_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_copy_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="copy_gridtable"></table>
		<div id="copyPager"></div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="copy_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="copy_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="copy_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>
</div>