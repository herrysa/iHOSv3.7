
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function orgGridReload(){
		propertyFilterSearch('org_search_form','org_gridtable');
		var postData = jQuery("#org_gridtable").jqGrid('getGridParam',"postData");
		var urlString = 'orgGridList?1=1';
		jQuery.each(postData, function(key, val) {
			if(val){
			switch(key){
				case 'filter_LIKES_orgCode':
					urlString+="&filter_LIKES_orgCode="+val;
					break;
				case 'filter_LIKES_orgname':
					urlString+="&filter_LIKES_orgname="+val;
					break;
				case 'filter_LIKES_type':
					urlString+="&filter_LIKES_type="+val;
					break;
				case 'filter_EQB_disabled':
					urlString+="&filter_EQB_disabled="+val;
					break;
			}    
			}
	 　　});   
		var treeObj = $.fn.zTree.getZTreeObj("orgTree");
		 if(!treeObj){
        	return; 
         }else{
        	 var nodes = treeObj.getNodes();
        	 jQuery.each(nodes[0].children, function() { 
        		 hideZTreeNodes(this,"orgTree");
        		 });
        	treeObj.refresh();
         }
		jQuery.ajax({
		       url: urlString,
		       data: {},
		       type: 'post',
		       dataType: 'json',
		       async:false,
		       error: function(data){
		       },
		       success: function(data){
		        if(data.orgAll){
		        	jQuery.each(data.orgAll, function() { 
		        		var updateNode = treeObj.getNodeByParam("id", this.orgCode, null);
		        		showZTreeNodes(updateNode,"orgTree");
		         		 });
		        	//treeObj.refresh();
		        }
		   }
		  });
	}
	var orgLayout;
			  var orgGridIdString="#org_gridtable";
	
	jQuery(document).ready(function() { 
		var orgFullSize = jQuery("#container").innerHeight()-118;
		jQuery("#org_container").css("height",orgFullSize);
		$('#org_container').layout({ 
			applyDefaultStyles: false ,
			west__size : 250,
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
					gridResize(null,null,"org_gridtable","single");
				}
			}
			
		});
		var initFlag = 0;
var orgGrid = jQuery(orgGridIdString);
    orgGrid.jqGrid({
    	url : "orgGridList",
    	editurl:"orgGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'orgCode',index:'orgCode',align:'left',width:90,label : '<s:text name="org.orgCode" />',hidden:false,key:true},				
{name:'orgname',index:'orgname',align:'left',width:160,label : '<s:text name="org.orgname" />',hidden:false,highsearch:true},				
{name:'shortName',index:'shortName',align:'left',width:120,label : '<s:text name="org.shortName" />',hidden:false,highsearch:true},				
{name:'internal',index:'internal',align:'left',width:70,label : '<s:text name="org.internal" />',hidden:false,highsearch:true},				
{name:'address',index:'address',align:'left',width:140,label : '<s:text name="org.address" />',hidden:true,highsearch:true},				
{name:'postCode',index:'postCode',align:'left',width:70,label : '<s:text name="org.postCode" />',hidden:false,highsearch:true},				
{name:'homePage',index:'homePage',align:'left',width:90,label : '<s:text name="org.homePage" />',hidden:true,highsearch:true},				
{name:'email',index:'email',align:'left',width:120,label : '<s:text name="org.email" />',hidden:false,highsearch:true},				
{name:'phone',index:'phone',align:'left',width:100,label : '<s:text name="org.phone" />',hidden:false,highsearch:true},				
{name:'fax',index:'fax',align:'left',width:100,label : '<s:text name="org.fax" />',hidden:false,highsearch:true},				
{name:'contact',index:'contact',align:'left',width:90,label : '<s:text name="org.contact" />',hidden:false,highsearch:true},				
{name:'contactPhone',index:'contactPhone',align:'left',width:90,label : '<s:text name="org.contactPhone" />',hidden:false,highsearch:true},				
{name:'disabled',index:'disabled',align:'center',width:50,label : '<s:text name="org.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},				
{name:'invalidDate',index:'invalidDate',align:'left',width:90,label : '<s:text name="org.invalidDate" />',hidden:false,highsearch:true},				
{name:'parentOrgCode.orgname',index:'ownerOrg',align:'left',width:90,label : '<s:text name="org.ownerOrg" />',hidden:true},				
{name:'type',index:'type',align:'left',width:90,label : '<s:text name="org.type" />',hidden:false,highsearch:true},		
{name:'note',index:'note',align:'left',width:90,label : '<s:text name="org.note" />',hidden:false,highsearch:true}	
        ],
        jsonReader : {
			root : "orgs", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'orgCode',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="orgList.title" />',
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
	 		gridContainerResize('org','layout');
//            if(jQuery(this).getDataIDs().length>0){
              //jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
//             }
           var rowNum = jQuery(this).getDataIDs().length;
           if(rowNum>0){
			var ztree = $.fn.zTree.getZTreeObj("orgTree");
                if(ztree){
                	var selectNode = ztree.getSelectedNodes();
					if(selectNode && selectNode.length==1){
						var selectid = selectNode[0].id;
 						jQuery(this).jqGrid('setSelection',selectid);
					}
                }
			}else{
				var tw = jQuery(this).outerWidth();
	        	jQuery(this).parent().width(tw);
	        	jQuery(this).parent().height(1);
			}
           var dataTest = {"id":"org_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("org_gridtable");
      	 initFlag = initColumn('org_gridtable','com.huge.ihos.system.systemManager.organization.model.Org',initFlag);
       	} 
       	

    });
    jQuery(orgGrid).jqGrid('bindKeys');
    refreshOrgTree();
    
    /*--------------------------------------tooBar start-------------------------------------------*/
	var org_menuButtonArrJson = "${menuButtonArrJson}";
	var org_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(org_menuButtonArrJson,false)));
	var org_toolButtonBar = new ToolButtonBar({el:$('#org_buttonBar'),collection:org_toolButtonCollection,attributes:{
		tableId : 'org_gridtable',
		baseName : 'org',
		width : 580,
		height : 450,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : "添加单位",
		editTitle : "修改单位"
	}}).render();
	
	var org_function = new scriptFunction();
	org_function.optBeforeCall = function(e,$this,param){
		var pass = false;
		if('${yearStarted}' == 'true'){
			alertMsg.error("本年度人力资源系统已启用，请到人力资源系统维护!");
   			return pass;
		}
        return true;
	};
	//添加
	org_toolButtonBar.addCallBody('50000801',function(e,$this,param){
		if(selectId==-1){
			selectId="";
		}
		$.ajax({
		    url: 'checkOrgCanBeAdded',
		    type: 'post',
		    data:{orgCode:selectId},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		        if(!data.canBeAdded){
		        	 alertMsg.error("当前版本可添加的单位数已经是最大。");
		        }else{
		        	var url = addOrgUrl+"?popup=true&orgCode="+selectId+"&navTabId="+tableId+"&editType=new";
		    		var winTitle="<s:text name='orgNew.title'/>";
		    		//alert(url);
		    		url = encodeURI(url);
		    		$.pdialog.open(url, 'addOrg', winTitle, {mask:true,width:650,height:450,resizable:false,maxable:false});　
		        }
		    }
		});
	},{});
	org_toolButtonBar.addBeforeCall('50000801',function(e,$this,param){
		return org_function.optBeforeCall(e,$this,param);
	},{});
	//删除
	org_toolButtonBar.addCallBody('50000802',function(e,$this,param){
		orgDel('org_gridtable');
	},{});
	org_toolButtonBar.addBeforeCall('50000802',function(e,$this,param){
		return org_function.optBeforeCall(e,$this,param);
	},{});
	// 修改
	//org_toolButtonBar.addFunctionEdit('50000803');
	org_toolButtonBar.addCallBody('50000803',function(e,$this,param){
			var sid = jQuery("#org_gridtable").jqGrid('getGridParam','selarrrow');
		    if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
			}
			var url = addOrgUrl+"?popup=true&orgCode="+sid+"&navTabId=org_gridtable";
    		var winTitle="<s:text name='orgEdit.title'/>";
    		//alert(url);
    		url = encodeURI(url);
    		$.pdialog.open(url, 'editOrg', winTitle, {mask:true,width:650,height:450,resizable:false,maxable:false});　
	},{});
	org_toolButtonBar.addBeforeCall('50000803',function(e,$this,param){
		return org_function.optBeforeCall(e,$this,param);
	},{});
	//设置表格列
	var org_setColShowButton = {id:'50010388',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
			callBody:function(){
				setColShow('org_gridtable','com.huge.ihos.system.systemManager.organization.model.Org');
			}};
	org_toolButtonBar.addButton(org_setColShowButton);
    
	//orgLayout.resizeAll();
  	});
	
	
	var selectId = "";
    var tableId = "org_gridtable"; var addOrgUrl = "editOrg";
    jQuery("#org_gridtable_addlocal").bind('click',function(){
		if(selectId==-1){
			selectId="";
		}
		$.ajax({
		    url: 'checkOrgCanBeAdded',
		    type: 'post',
		    data:{orgCode:selectId},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		        if(!data.canBeAdded){
		        	 alertMsg.error("对不起，该单位为末级， 不能再继续添加");
		        }else{
		        	var url = addOrgUrl+"?popup=true&orgCode="+selectId+"&navTabId="+tableId+"&editType=new";
		    		var winTitle="<s:text name='orgNew.title'/>";
		    		//alert(url);
		    		url = encodeURI(url);
		    		$.pdialog.open(url, 'addOrg', winTitle, {mask:false,width:580,height:450,resizable:false,maxable:false});　
		        }
		    }
		});
	});
	
	
	function reloadOrgGrid(e,treeId, treeNode){
		var treeId = treeNode.id;
		selectId = treeId;
		urlString = "orgGridList";
		if(treeId!='-1' && treeId !==""){
			//jQuery("#orgCode").val(treeId);
			urlString += "?orgCode="+treeId;
		}
		urlString=encodeURI(urlString);
		jQuery("#org_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var ztreesetting_org = {
			view : {
				showLine : true,
				selectedMulti : false,
				fontCss : function(treeId, treeNode){
					var color;
					if(treeNode.actionUrl == '1') {
						color = {
							color : "black",
							'font-style' : 'italic',
							'text-decoration' : 'line-through'
						};
					}else{
						color = {
							color : "black"
						};
					}
					return color;
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
			},
			callback : {
				onClick: reloadOrgGrid
			}
		};
	function refreshOrgTreeBack(data){
		refreshOrgTree();
		formCallBack(data);
	}
	function refreshOrgTree(){
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
		        	orgTree = jQuery.fn.zTree.init(jQuery("#orgTree"), ztreesetting_org,data.ztreeList);
		        	var rootnode = orgTree.getNodeByParam("id","-1",null);
		        	orgTree.selectNode(rootnode);
		        	toggleDisabledOrCount({treeId:'orgTree',
						showCode:jQuery('#org_showCode')[0],
						disabledDept:false,
						disabledPerson: false,
						showCount:false });
		        },100);
		    }
		});
	}
	
	function orgDel(tableId){
		var buttonBar ;
		if(typeof(jQuery("#"+tableId+"_div").attr("buttonBar"))!="undefined"){
			buttonBar = jQuery("#"+tableId+"_div").attr("buttonBar");
		}
		var fatherGrid = getParam('fatherGrid',buttonBar);
		var extraParam = getParam('extraParam',buttonBar);
		var selectNone = jQuery("#"+tableId+"_selectNone").text();
		var sid = jQuery("#"+tableId).jqGrid('getGridParam','selarrrow');
		var editUrl = jQuery("#"+tableId).jqGrid('getGridParam', 'editurl');
		editUrl+="?id=" + sid+"&navTabId="+tableId+"&oper=del";
		if(fatherGrid!=null&&fatherGrid!=""&&extraParam!=null&&extraParam!=""){
			var fatherGridId = jQuery("#"+fatherGrid).jqGrid('getGridParam','selarrrow');
			editUrl += "&"+extraParam+"="+fatherGridId;
		}
		editUrl = encodeURI(editUrl);
	    if(sid==null || sid.length ==0){
				alertMsg.error(selectNone);
				return;
		}else{
			// jQuery("#"+tableId).jqGrid('delGridRow',sid,{reloadAfterSubmit:false,left:300,top:150});
			alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, refreshOrgTreeBack, "json");
					
				}
			});
			selectId="";
		}
	}
	
	
	
</script>

<div class="page">
	<div class="pageHeader" id="org_pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<form id="org_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='org.orgCode'/>:
						<input type="text" name="filter_LIKES_orgCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='org.orgname'/>:
						<input type="text" name="filter_LIKES_orgname"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='org.type'/>:
						<input type="text" name="filter_LIKES_type"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='org.disabled'/>:
						<s:select list="#{'':'--','true':'是','false':'否'}" id="disabled"  name="filter_EQB_disabled"></s:select>
					</label>
					 <div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="orgGridReload()"><s:text name='button.search'/></button>
						</div>
					</div>	
					</form>			
			</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="orgGridReload()"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
		<div id="org_buttonBar" class="panelBar">
			<%-- <ul class="toolBar">
				<li>
				<a id="org_gridtable_addlocal" class="addbutton" href="javaScript:" >
					<span>
						<fmt:message key="button.add" />
					</span>
				</a>
				</li>
				<li>
					<a class="delbutton" onclick="orgDel('org_gridtable')" >
						<span><s:text name="button.delete" /></span>
					</a>
				</li>
				<li><a id="org_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('org_gridtable','com.huge.ihos.system.systemManager.organization.model.Org')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			
			</ul> --%>
		</div>
		<div id="org_container">
			<div id="org_layout-west" class="pane ui-layout-west" 
				style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
						<span>
							显示机构编码
							<input id="org_showCode" type="checkbox" checked="checked" onclick="toggleDisabledOrCount({treeId:'orgTree',showCode:this,disabledDept:false,disabledPerson:false,showCount:false })"/>
						</span>
					</div>
<!-- 				<div style="float:right"> -->
<!-- 					<span id="org_expandTree" onclick="toggleExpandTree(this,'orgTree')" style="vertical-align:middle;text-decoration:underline;cursor:pointer;">展开</span> -->
<!-- 				</div> -->
				<div id="orgTree" class="ztree"></div>
			</div>
			<div id="org_layout-center" class="pane ui-layout-center">
				<div id="org_gridtable_div" class="grid-wrapdiv" style="margin-left: 2px; margin-top: 2px; overflow: hidden" buttonBar="optId:orgCode;width:580;height:450">
					<input type="hidden" id="org_gridtable_navTabId" value="${sessionScope.navTabId}">
					<label style="display: none" id="org_gridtable_addTile">
						<s:text name="orgNew.title"/>
					</label> 
					<label style="display: none"
						id="org_gridtable_editTile">
						<s:text name="orgEdit.title"/>
					</label>
					<label style="display: none"
						id="org_gridtable_selectNone">
						<s:text name='list.selectNone'/>
					</label>
					<label style="display: none"
						id="org_gridtable_selectMore">
						<s:text name='list.selectMore'/>
					</label>
					<div id="load_org_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
			 			<table id="org_gridtable"></table>
					<div id="orgPager"></div>
				</div>
			<div class="panelBar" id="org_pageBar">
				<div class="pages">
					<span><s:text name="pager.perPage" /></span> <select id="org_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="org_gridtable_totals"></label><s:text name="pager.items" /></span>
				</div>
	
				<div id="org_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>
	
			</div>
			</div>
		</div>
	</div>
</div>