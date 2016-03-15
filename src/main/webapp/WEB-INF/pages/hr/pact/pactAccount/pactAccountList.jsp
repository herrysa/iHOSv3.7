
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	var hrPersonInPactAccountGridIdString="#pactAccount_gridtable";
	var hrPersonInPactAccountLayout;
	jQuery(document).ready(function() { 
		jQuery("#pactAccount_pageHeader").find("select").css({"font-size":"12px"});
		var pactAccountFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('pactAccount_container','pactAccount_gridtable',pactAccountFullSize);
		
		hrDeptTreeInPactAccount(); 
		
		var pactAccountChangeData = function(selectedSearchId){
			if(selectedSearchId.length==0){
				hrPersonInPactAccountLayout.closeSouth();
				return;
			}
    		jQuery("#pactAccountDetail").load("pactAccountDetailList?personId="+selectedSearchId);
    		$("#background,#progressBar").hide();
    	};
    	hrPersonInPactAccountLayout = makeLayout({'baseName':'pactAccountP','tableIds':'pactAccount_gridtable;pactAccountPDetail_gridtable','activeGridTable':'pactAccount_gridtable','fullSize':96,'proportion':2,'key':'personId'},pactAccountChangeData);
    	var initFlag = 0;
		var hrPersonInPactAccountGrid = jQuery(hrPersonInPactAccountGridIdString);
		hrPersonInPactAccountGrid.jqGrid({
	    	url : "hrPersonCurrentGridList?1=1",
	    	editurl:"hrPersonCurrentGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'personId',index:'personId',align:'center',label : '<s:text name="hrPersonSnap.personId" />',hidden:true,key:true,highsearch:false},				
				{name:'snapCode',index:'snapCode',align:'center',label : '<s:text name="hrPersonSnap.snapCode" />',hidden:true,highsearch:false},				
				{name:'personCode',index:'personCode',align:'left',width:'70',label : '<s:text name="hrPersonSnap.personCode" />',hidden:false,highsearch:true},				
				{name:'name',index:'name',align:'left',width:'80',label : '<s:text name="hrPersonSnap.name" />',hidden:false,highsearch:true},				
				{name:'hrOrg.orgname',index:'hrOrg.orgname',align:'left',width:'150',label : '<s:text name="hrPersonSnap.orgCode" />',hidden:false,highsearch:true},				
				{name:'department.name',index:'department.name',width:'120',align:'left',label : '<s:text name="hrPersonSnap.hrDept" />',hidden:false,highsearch:true},				
				{name:'sex',index:'sex',align:'center',width:'50',label : '<s:text name="hrPersonSnap.sex" />',hidden:false,highsearch:true},				
				{name:'status.name',index:'status.name',align:'left',width:'100',label : '<s:text name="hrPersonSnap.empType" />',hidden:false,highsearch:true},	
				{name:'postType',index:'postType',align:'left',width:60,label : '<s:text name="hrPersonSnap.postType" />',hidden:false,highsearch:true},				
				{name:'duty.name',index:'duty.name',align:'left',width:60,label : '<s:text name="hrPersonSnap.duty" />',hidden:false,highsearch:true},
				{name:'jobTitle',index:'jobTitle',align:'left',width:'80',label : '<s:text name="hrPersonSnap.jobTitle" />',hidden:false,highsearch:true},				
				{name:'duty',index:'duty',align:'left',width:80,label : '<s:text name="hrPersonSnap.duty" />',hidden:false,highsearch:true},
				{name:'nation',index:'nation',align:'left',width:80,label : '<s:text name="hrPersonSnap.nation" />',hidden:false,highsearch:true},	
				{name:'workPhone',index:'workPhone',align:'left',width:100,label : '<s:text name="hrPersonSnap.workPhone" />',hidden:false,highsearch:true},
				{name:'mobilePhone',index:'mobilePhone',align:'left',width:100,label : '<s:text name="hrPersonSnap.mobilePhone" />',hidden:false,highsearch:true},	
				{name:'entryDate',index:'entryDate',align:'center',width:80,label : '<s:text name="hrPersonSnap.entryDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'ratio',index:'ratio',align:'right',width:'100',label : '<s:text name="hrPersonSnap.ratio" />',hidden:false,highsearch:true,formatter:'currency',
					formatoptions:{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 4, prefix: '', suffix:'', defaultValue: '0.00'}},				
				{name:'disable',index:'disable',align:'center',width:'50',label : '<s:text name="hrPersonSnap.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},				
				{name:'jjmark',index:'jjmark',align:'center',width:'50',label : '<s:text name="hrPersonSnap.jjmark" />',hidden:false,formatter:'checkbox',highsearch:true}				
	        ],
			jsonReader : {
				root : "hrPersonCurrents", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
			sortname: 'orgCode,department.deptCode,personCode',
			viewrecords: true,
			sortorder: 'asc',
			height:'100%',
			gridview:true,
			rownumbers:true,
			loadui: "disable",
			multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
			ondblClickRow:function(){
				hrPersonInPactAccountLayout.optDblclick();
			},
			onSelectRow: function(rowid) {
	        	setTimeout(function(){
	        		hrPersonInPactAccountLayout.optClick();
	        	},100);
	       	},
			gridComplete:function(){
				gridContainerResize('pactAccount','layout');
				var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  id=rowIds[i];
	              	  var snapId=ret[i]["personId"]+'_'+ret[i]["snapCode"];
		   	          setCellText(this,id,'name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrPersonRecord(\''+snapId+'\');">'+ret[i]["name"]+'</a>');
	              }
	            }else{
	            	var tw = jQuery("#pactAccount_gridtable").outerWidth();
					jQuery("#pactAccount_gridtable").parent().width(tw);
					jQuery("#pactAccount_gridtable").parent().height(1);
	            }
	            var dataTest = {"id":"pactAccount_gridtable"};
	      	    jQuery.publish("onLoadSelect",dataTest,null);
	      	    initFlag = initColumn('pactAccount_gridtable','com.huge.ihos.hr.hrPerson.model.HrPersonInPactAccount',initFlag);
			}
		});
		jQuery(hrPersonInPactAccountGrid).jqGrid('bindKeys');
		/* jQuery("#pactAccount_search_form_orgCode").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT orgCode id,orgname name,parentOrgCode parent FROM T_Org WHERE disabled = 0",
			exceptnullparent : false,
			lazy : false,
			selectParent:true,
			callback : {
				afterClick : function() {
					jQuery("#pactAccount_search_form_hrDept").val("");
					jQuery("#pactAccount_search_form_hrDept_id").val("");
					jQuery("#pactAccount_search_form_hrDept").treeselect({
						dataType : "sql",
						optType : "single",
						sql : "SELECT  deptId id,name,parentDept_id parent FROM v_hr_department_current where disabled = 0 and orgCode='"+jQuery("#pactAccount_search_form_orgCode_id").val()+"' ORDER BY deptCode",
						exceptnullparent : false,
						lazy : false
					});
				}
			}
		}); */
		jQuery("#pactAccount_search_form_empType").treeselect({
			dataType:"sql",
			optType:"single",
			sql:"SELECT id,name,parentType parent FROM t_personType where disabled=0  ORDER BY code",
			exceptnullparent:false,
			selectParent:false,
			lazy:false
		});
  	});
	function hrDeptTreeInPactAccount() {
		var url = "makeHrDeptTree";
		/* var showDisabledDept = jQuery("#pactAccount_showDisabledDept").attr("checked");
		if(showDisabledDept){
			url += "?showDisabled=show";
		} */
		$.get(url, {"_" : $.now()}, function(data) {
			var hrDeptTreeData = data.hrDeptTreeNodes;
			var hrDeptTree = $.fn.zTree.init($("#pactAccountTreeLeft"),ztreesetting_hrDeptTreeInPactAccount, hrDeptTreeData);
			var nodes = hrDeptTree.getNodes();
			hrDeptTree.expandNode(nodes[0], true, false, true);
			hrDeptTree.selectNode(nodes[0]);
			nodes = hrDeptTree.transformToArray(nodes);
			var node;
			for(index in nodes){
				node = nodes[index];
				if(node.subSysTem!="ALL"){
					if(node.subSysTem=="ORG"){
						node.name = node.name+"("+node.personCountD+")";
					}else{
						node.name = node.name+"("+node.personCount+")";
					}
					hrDeptTree.updateNode(node);
				}
			}
			var obj = new Object();
			obj.checked = true;
			toggleDisabledOrCount({treeId:'pactAccountTreeLeft',
    		showCode:jQuery('#pactAccount_showCode')[0],
    		disabledDept:jQuery("#pactAccount_showDisabledDept")[0],
    		disabledPerson: true,
    		showCount:true });
			//toogleShowDisabledDept(jQuery("#pactAccount_showDisabledDept")[0],obj,'pactAccountTreeLeft');
		});
		jQuery("#pactAccount_expandTree").text("展开");
	}
	
	var ztreesetting_hrDeptTreeInPactAccount = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				fontCss : setDisabledDeptFontCss
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var urlString = "hrPersonCurrentGridList";
					var nodeId = treeNode.id;
					if(nodeId!="-1"){
				    	if(treeNode.subSysTem==='ORG'){
					    	urlString += "?orgCode="+nodeId;
				    	}else{
					    	urlString += "?departmentId="+nodeId;
				    	}
				    }
					var showDisabled = jQuery("#pactAccount_showDisabledDept").attr("checked");
				    if(showDisabled){
				    	urlString += "&showDisabled=true"
				    }
					urlString=encodeURI(urlString);
					jQuery("#pactAccount_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
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
			}
	};
	
	function showDisabledDeptInPactAccount(obj){
		var urlString = jQuery("#pactAccount_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabled','');
		if(obj.checked){
			urlString += "&showDisabled=true";
		}
		//hrDeptTreeInPactAccount();
		var pobj = new Object();
		pobj.checked = true;
		toggleDisabledOrCount({treeId:'pactAccountTreeLeft',
    		showCode:jQuery('#pactAccount_showCode')[0],
    		disabledDept:jQuery("#pactAccount_showDisabledDept")[0],
    		disabledPerson: true,
    		showCount:true });
		//toogleShowDisabledDept(jQuery("#pactAccount_showDisabledDept")[0],pobj,'pactAccountTreeLeft');
		jQuery("#pactAccount_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
</script>
<div class="page" id="pactAccount_page">
	<div id="pactAccount_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="pactAccount_search_form" class="queryarea-form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrPersonSnap.personCode'/>:
					 	<input type="text" name="filter_LIKES_personCode" />
					</label>
					<label class="queryarea-label" >
						<s:text name='hrPersonSnap.name'/>:
					 	<input type="text" name="filter_LIKES_name" />
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='hrPersonSnap.orgCode'/>:
					 	<input type="text" id="pactAccount_search_form_orgCode" />
					 	<input type="hidden" id="pactAccount_search_form_orgCode_id" name="filter_EQS_orgCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrPersonSnap.hrDept'/>:
						<input type="text" id="pactAccount_search_form_hrDept"/>
					 	<input type="hidden" id="pactAccount_search_form_hrDept_id" name="filter_EQS_department.departmentId" />
					</label> --%>
					<%-- <label style="float:none;white-space:nowrap" >
       					<s:text name='hrPersonSnap.empType'/>:
      					<s:select key="hrPersonSnap.status" name="filter_EQS_status"
            			  maxlength="50" list="empTypeList" listKey="value" headerKey="" headerValue="--"
            			  listValue="content" emptyOption="false" theme="simple"></s:select>
     				</label> --%>
     				<label class="queryarea-label" >
       					<s:text name='hrPersonSnap.empType'/>:
      					<input type="text" id="pactAccount_search_form_empType"/>
					 	<input type="hidden" id="pactAccount_search_form_empType_id" name="filter_EQS_status.id" />
					 </label>
					<label class="queryarea-label" >
       					<s:text name='hrPersonSnap.sex'/>:
      					<s:select key="hrPersonSnap.sex" name="filter_EQS_sex"  style="font-size:9pt;"
            			  maxlength="50" list="sexList" listKey="value" headerKey="" headerValue="--"
            			  listValue="content" emptyOption="false" theme="simple"></s:select>
     				 </label>
     				 <label class="queryarea-label" >
       					<s:text name='hrPersonSnap.nation'/>:
      					<s:select key="hrPersonSnap.nation" name="filter_EQS_nation"  style="font-size:9pt;"
            			  maxlength="50" list="nationList" listKey="value" headerKey="" headerValue="--"
            			  listValue="content" emptyOption="false" theme="simple"></s:select>
     				 </label>
     				 <label class="queryarea-label" >
       					<s:text name='hrPersonSnap.disabled'/>:
      					<s:select key="hrPersonSnap.disabled" name="filter_EQB_disable"  style="font-size:9pt;"
            			  maxlength="50" list="#{'1':'是','0':'否'}"  headerKey="" headerValue="--" 
            			  emptyOption="false" theme="simple"></s:select>
     				 </label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('pactAccount_search_form','pactAccount_gridtable');"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('pactAccount_search_form','pactAccount_gridtable');"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="pactAccount_buttonBar">
			<ul class="toolBar">
				<li>
					<a class="particularbutton" href="javaScript:hrPersonInPactAccountLayout.optDblclick();"><span>明细</span> </a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('pactAccount_gridtable','com.huge.ihos.hr.hrPerson.model.HrPersonInPactAccount')"><span><s:text name="button.setColShow" /></span></a>
				</li>
				
			</ul>
		</div>
		<div id="pactAccount_container">
			<div id="pactAccount_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
     			<span>
      			显示机构编码
      			<input id="pactAccount_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'pactAccountTreeLeft',showCode:this,disabledDept:jQuery('#hrPersonHistory_showDisabled')[0],disabledPerson: true,showCount:true });")"/>
     			</span>
     			<span>
      			显示停用部门
      			<input id="pactAccount_showDisabledDept" type="checkbox" onclick="showDisabledDeptInPactAccount(this)"/>
     			</span>
     			<label id="pactAccount_expandTree" onclick="toggleExpandTree(this,'pactAccountTreeLeft')">展开</label>
    			</div>
    			<div class="treeTopCheckBox">
     			<span>
      			按部门检索：
      			<input type="text" onKeyUp="searchTree('pactAccountTreeLeft',this)"/>
     			</span>
    			</div>						
				<div id="pactAccountTreeLeft" class="ztree"></div>
			</div>
			<div id="pactAccount_layout-center" class="pane ui-layout-center">
				<div id="pactAccountP_container">
					<div id="pactAccountP_layout-center" class="pane ui-layout-center">
						<div id="pactAccount_gridtable_div" class="grid-wrapdiv unitBox" 
							style="margin-left: 2px; margin-top: 2px; overflow: hidden">
							<input type="hidden" id="pactAccount_gridtable_navTabId" value="${sessionScope.navTabId}">
							<div id="load_pactAccount_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
							<div id="pactAccount_table_list" >
				 			<table id="pactAccount_gridtable"></table></div>
						</div>
						<div class="panelBar" id="pactAccount_pageBar">
							<div class="pages">
								<span><s:text name="pager.perPage" /></span> <select id="pactAccount_gridtable_numPerPage">
									<option value="20">20</option>
									<option value="50">50</option>
									<option value="100">100</option>
									<option value="200">200</option>
								</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="pactAccount_gridtable_totals"></label><s:text name="pager.items" /></span>
							</div>
							<div id="pactAccount_gridtable_pagination" class="pagination"
								targetType="navTab" totalCount="200" numPerPage="20"
								pageNumShown="10" currentPage="1">
							</div>
						</div>
					</div>
					<div id="pactAccountP_layout-south" class="pane ui-layout-south" style="padding: 2px">
						<div class="panelBar">
							<ul class="toolBar">
								<li>
									<a class="settlebutton"  href="javaScript:setColShow('pactAccountPDetail_gridtable','com.huge.ihos.hr.pact.model.PactAccountDetail')"><span><s:text name="button.setColShow" /></span></a>
								</li>
								<li style="float: right;">
									<a id="pactAccountP_close" class="closebutton" href="javaScript:"><span><fmt:message key="button.close" /></span></a>
								</li>
								<li class="line" style="float: right">line</li>
								<li style="float: right;">
									<a id="pactAccountP_fold" class="foldbutton" href="javaScript:"><span><fmt:message key="button.fold" /></span></a>
								</li>
								<li class="line" style="float: right">line</li>
								<li style="float: right">
									<a id="pactAccountP_unfold" class="unfoldbutton" href="javaScript:"><span><fmt:message key="button.unfold" /></span></a>
								</li>
							</ul>
						</div>
						<div id="pactAccountDetail"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
