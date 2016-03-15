
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var pactForSelectGridIdString="#${random}pactForSelect_gridtable";
	jQuery(document).ready(function() { 
		var pactForSelectFullSize = jQuery("#container").innerHeight()-70;
		setLeftTreeLayout('${random}pactForSelect_container','${random}pactForSelect_gridtable',502);
		$(window).resize(function(){
			var height = jQuery("#${random}pactForSelect_gridtable_div").height();
			jQuery("#${random}pactForSelect_container").css("height",height+33);
		});
		hrPersonTreeInPactForSelect();
		var initFlag = 0;
		var pactForSelectGrid = jQuery(pactForSelectGridIdString);
   	 	pactForSelectGrid.jqGrid({
	    	url : getPactForSelectGridUrl(),
	    	editurl:"pactForSelectGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="pact.id" />',hidden:true,key:true},	
				{name:'code',index:'code',width : '120',align:'left',label : '<s:text name="pact.code" />',hidden:false,highsearch:true},	
				{name:'hrPerson.name',index:'hrPerson.name',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:false,highsearch:true},	
				{name:'hrPerson.personId',index:'hrPerson.personId',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'personSnapCode',index:'personSnapCode',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'dept.name',index:'dept.name',width : '100',align:'left',label : '<s:text name="pact.dept" />',hidden:false,highsearch:true},	
				{name:'post.name',index:'post.name',width : '100',align:'left',label : '<s:text name="pact.post" />',hidden:false,highsearch:true},	
			 	{name:'workContent',index:'workContent',width : '150',align:'left',label : '<s:text name="pact.workContent" />',hidden:false,highsearch:true},		
				{name:'signYear',index:'signYear',width : '56',align:'right',label : '<s:text name="pact.signYear" />',hidden:false,highsearch:true,formatter:'integer'},
				{name:'compSignDate',index:'compSignDate',width : '100',align:'center',label : '<s:text name="pact.compSignDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'beginDate',index:'beginDate',width : '100',align:'center',label : '<s:text name="pact.beginDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
				{name:'endDate',index:'endDate',width : '100',align:'center',label : '<s:text name="pact.endDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
				{name:'path',index:'path',align:'center',label : '<s:text name="pact.path" />',hidden:true},				
				{name:'remark',index:'remark',width : '100',align:'left',label : '<s:text name="pact.remark" />',hidden:false,highsearch:true},	
				{name:'pactState',index:'pactState',width : '80',align:'center',label : '<s:text name="pact.pactState" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '1:初签;2:续签'}},			
				{name:'signState',index:'signState',width : '80',align:'center',label : '<s:text name="pact.signState" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '3:有效;4:终止'}}			
	
	        ],
	        jsonReader : {
				root : "pacts", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'code',
	        viewrecords: true,
	        sortorder: 'asc',
	        //caption:'<s:text name="pactList.title" />',
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
		 		gridContainerResize("${random}pactForSelect","layout",0,295);
		 		var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  id=rowIds[i];
	              	  if(ret[i]['signState']=="3"){
	              		  setCellText(this,id,'signState','<span >有效</span>');
	              	  }else if(ret[i]['signState']=="4"){
	              		  setCellText(this,id,'signState','<span style="color:red" >终止</span>');
	              	  }
	              	  setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPactRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
		              	var snapId = ret[i]['hrPerson.personId'] +'_'+ret[i]['personSnapCode'];
		   	          	setCellText(this,id,'hrPerson.name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrPersonRecord(\''+snapId+'\');">'+ret[i]["hrPerson.name"]+'</a>');
	              }
	            }else{
	            	var tw = jQuery("#${random}pactForSelect_gridtable").outerWidth();
					jQuery("#${random}pactForSelect_gridtable").parent().width(tw);
					jQuery("#${random}pactForSelect_gridtable").parent().height(1);
	            }
           		var dataTest = {"id":"${random}pactForSelect_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('${random}pactForSelect_gridtable','com.huge.ihos.hr.pact.model.PactForSelect${directBusiness}',initFlag);
       		} 
    	});
    	jQuery(pactForSelectGrid).jqGrid('bindKeys');
    	/* jQuery("#${random}pactForSelect_search_form_orgCode").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT orgCode id,orgname name,parentOrgCode parent FROM T_Org WHERE disabled = 0",
			exceptnullparent : false,
			lazy : false,
			selectParent:true,
			callback : {
				afterClick : function() {
					jQuery("#${random}pactForSelect_search_form_dept").val("");
					jQuery("#${random}pactForSelect_search_form_dept_id").val("");
					jQuery("#${random}pactForSelect_search_form_post").val("");
					jQuery("#${random}pactForSelect_search_form_post_id").val("");
					jQuery("#${random}pactForSelect_search_form_dept").treeselect({
						dataType : "sql",
						optType : "single",
						sql : "SELECT  deptId id,name,parentDept_id parent FROM v_hr_department_current where disabled = 0 and deleted = 0 and orgCode='"+jQuery("#${random}pactForSelect_search_form_orgCode_id").val()+"'",
						exceptnullparent : false,
						selectParent:true,
						lazy : false,
						callback:{
							afterClick:function(){
								jQuery("#${random}pactForSelect_search_form_post").val("");
								jQuery("#${random}pactForSelect_search_form_post_id").val("");
								jQuery("#${random}pactForSelect_search_form_post").treeselect({
									dataType : "sql",
									optType : "single",
									sql : "SELECT  id,name FROM hr_post where disabled = 0 and deptId='"+jQuery("#${random}pactForSelect_search_form_dept_id").val()+"'",
									lazy : false
								});
							}
						}
					});
				}
			}
		}); */
  	});
	
	function getPactForSelectGridUrl(){
		var directBusiness = "${directBusiness}";
		var url = "pactForSelectGridList?nodeId=${nodeId}&nodeType=${nodeType}&filter_EQI_lockType=0";
		if(directBusiness=='relieve'){
			url += "&filter_INI_signState=3,4";
		}else{
			url += "&filter_EQI_signState=3";
		}
		return url;
	}
	
	function hrPersonTreeInPactForSelect() {
		var treeUrl = "makeHrPersonTree?showPersonType=true";
		$.get(treeUrl, {"_" : $.now()}, function(data) {
			var hrPersonTreeData = data.hrPersonTreeNodes;
			var hrPersonTree = $.fn.zTree.init($("#${random}hrPersonTreeInPactForSelect"),ztreesetting_hrPersonTreeInPactForSelect, hrPersonTreeData);
			var nodes = hrPersonTree.getNodes();
			toggleDisabledOrCount({treeId:'${random}hrPersonTreeInPactForSelect',
		         showCode:jQuery('#${random}pactForSelect_showCode')[0],
		         disabledDept:jQuery("#${random}pactForSelect_showDisabledDept")[0],
		         disabledPerson: jQuery("#${random}pactForSelect_showDisabledPerson")[0],
		         showCount:true });
			//var obj = new Object();
			//obj.checked = false;
			//toogleShowDisabledDeptWithPerson(obj,obj,'${random}hrPersonTreeInPactForSelect');
			//toogleShowDisabledPerson(obj,obj,'${random}hrPersonTreeInPactForSelect');
			var nodeId = "${nodeId}";
			if(nodeId){
				var node = hrPersonTree.getNodeByParam("id", nodeId, null);
				if(node){
					if(!node.isParent){
						node = node.getParentNode();						
					}
					hrPersonTree.expandNode(node,true,false,false);
					hrPersonTree.selectNode(node);
				}
			}else{
				var node = nodes[0];
				if(!node.isParent){
					node = node.getParentNode();						
				}
				hrPersonTree.expandNode(nodes[0], true, false, true);
				hrPersonTree.selectNode(nodes[0]);
			}
		});
	}
	var ztreesetting_hrPersonTreeInPactForSelect = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				fontCss : setDisabledDeptFontCss
			},
			callback : {
				beforeDrag:function(){return false},
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
	function onModuleClick(event, treeId, treeNode, clickFlag) {
		var directBusiness = "${directBusiness}";
		var urlString = "pactForSelectGridList?filter_EQI_lockType=0";
		if(directBusiness=='relieve'){
			urlString += "&filter_INI_signState=3,4";
		}else{
			urlString += "&filter_EQI_signState=3";
		}
		var nodeId = treeNode.id;
	    if(nodeId!="-1"){
	    	urlString += "&nodeType="+treeNode.subSysTem;
	    	urlString += "&nodeId="+nodeId;
	    	/* if(treeNode.subSysTem==='ORG'){
		    	urlString += "&orgCode="+nodeId;
	    	}else if(treeNode.subSysTem==='DEPT'){
		    	urlString += "&departmentId="+nodeId;
	    	}else{
	    		urlString += "&personId="+nodeId;
	    	} */
	    }
	    var showDisabledDept = jQuery("#${random}pactForSelect_showDisabledDept").attr("checked");
		var showDisabledPerson = jQuery("#${random}pactForSelect_showDisabledPerson").attr("checked");
		if(showDisabledDept){
			urlString += "&showDisabledDept=show";
		}
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=show";	
		}
		urlString=encodeURI(urlString);
		jQuery("#${random}pactForSelect_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
	}
	/*--------------------------------------search end---------------------------*/
	function selectPact(){
		var sid = jQuery("#${random}pactForSelect_gridtable").jqGrid('getGridParam','selarrrow');
		if (sid == null || sid.length == 0) {
			alertMsg.error("请选择记录。");
			return;
		} 
		$.pdialog.closeCurrent();
		var directBusiness = "${directBusiness}";
		if(directBusiness=='break'){
			var winTitle='<s:text name="pactBreakNew.title"/>';
			var url = "editPactBreak?navTabId=pactBreak_gridtable&random=${random}&pactIds="+sid;
			$.pdialog.open(url,'addPactBreak',winTitle, {mask:true,width : 600,height : 300,maxable:false,resizable:false});
		}else if(directBusiness=='relieve'){
			var winTitle='<s:text name="pactRelieveNew.title"/>';
			var url = "editPactRelieve?navTabId=pactRelieve_gridtable&random=${random}&pactIds="+sid;
			$.pdialog.open(url,'addPactRelieve',winTitle, {mask:true,width : 600,height : 300,maxable:false,resizable:false});
		}else if(directBusiness=='renew'){
			var winTitle='<s:text name="pactRenew.title"/>';
			var url = "editPactRenew?navTabId=pactRenew_gridtable&random=${random}&pactIds="+sid;
			$.pdialog.open(url,'addPactRenew',winTitle, {mask:true,width : 600,height : 300,maxable:false,resizable:false});
		}
		
	}
	function showDisabledInPactForSelect(){
		toggleDisabledOrCount({treeId:'${random}hrPersonTreeInPactForSelect',
	         showCode:jQuery('#${random}pactForSelect_showCode')[0],
	         disabledDept:jQuery("#${random}pactForSelect_showDisabledDept")[0],
	         disabledPerson: jQuery("#${random}pactForSelect_showDisabledPerson")[0],
	         showCount:true });
		var urlString = jQuery("#${random}pactForSelect_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		var showDisabledDept = jQuery("#${random}pactForSelect_showDisabledPerson").attr("checked");
		if(obj.checked){
			urlString += "&showDisabledDept=show";
		}
		var showDisabledPerson = jQuery("#${random}pactForSelect_showDisabledPerson").attr("checked");
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=show";	
		}
		urlString=encodeURI(urlString);
		jQuery("#${random}pactForSelect_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
</script>

<div class="page" id="${random}pactForSelect_page">
	<div class="pageHeader" id="${random}pactForSelect_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="${random}pactForSelect_search_form" class="queryarea-form" >	
					<label class="queryarea-label" >
						<s:text name='pact.code'/>:
      					<input type="text" name="filter_LIKES_code"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='pact.hrPerson'/>:
      					<input type="text" name="filter_LIKES_hrPerson.name"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='pact.compSignDate'/>:
      					<input type="text" id="${random}pactForSelect_search_form_compSignDate_from" name="filter_GED_compSignDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'${random}pactForSelect_search_form_compSignDate_to\')}'})">
						<s:text name='至'/>
					 	<input type="text" id="${random}pactForSelect_search_form_compSignDate_to" name="filter_LED_compSignDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'${random}pactForSelect_search_form_compSignDate_from\')}'})">
					</label>
					<label class="queryarea-label" >
						<s:text name='pact.pactState'/>:
						<s:select name="filter_EQI_pactState" list="#{'':'--','1':'初签','2':'续签'}" style="width:70px;font-size:12px" ></s:select>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='pact.orgCode'/>:
      					<input type="hidden" name="filter_EQS_orgCode" id="${random}pactForSelect_search_form_orgCode_id"/>
      					<input type="text" id="${random}pactForSelect_search_form_orgCode"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.dept'/>:
      					<input type="hidden" name="filter_EQS_dept.departmentId" id="${random}pactForSelect_search_form_dept_id"/>
      					<input type="text" id="${random}pactForSelect_search_form_dept"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.post'/>:
      					<input type="hidden" name="filter_EQS_post.id" id="${random}pactForSelect_search_form_post_id"/>
      					<input type="text" id="${random}pactForSelect_search_form_post"/>
					</label> --%>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('${random}pactForSelect_search_form','${random}pactForSelect_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>		
			<%-- <div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="propertyFilterSearch('${random}pactForSelect_search_form','${random}pactForSelect_gridtable')"><s:text name='button.search'/></button>
							</div>
						</div>
					</li>
				</ul>
			</div> --%>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="${random}pactForSelect_buttonBar">
			<ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:selectPact()" ><span><s:text name="确定"/></span></a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('${random}pactForSelect_gridtable','com.huge.ihos.hr.pact.model.PactForSelect${directBusiness}')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			</ul>
		</div>
		
		<div id="${random}pactForSelect_container">
			<div id="${random}pactForSelect_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				       <div class="treeTopCheckBox">
         	 <span>
          	 显示编码
           <input id="${random}pactForSelect_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'${random}hrPersonTreeInPactForSelect',showCode:this,disabledDept:jQuery('#${random}pactForSelect_showDisabledDept')[0],disabledPerson:jQuery('#${random}pactForSelect_showDisabledDept')[0],showCount:true})"/>
         </span>
         <span>
           显示停用部门
          <input id="${random}pactForSelect_showDisabledDept" type="checkbox" onclick="showDisabledInPactForSelect()"/>
         </span>
         <label id="${random}pactForSelect_expandTree" onclick="toggleExpandHrTree(this,'${random}hrPersonTreeInPactForSelect')">展开</label>
        </div>
       <div class="treeTopCheckBox">
          <span>
           显示停用人员
          <input id="${random}pactForSelect_showDisabledPerson" type="checkbox" onclick="showDisabledInPactForSelect()"/>
          </span>
        </div>
        <div class="treeTopCheckBox">
         <span>
           快速查询：
          <input type="text" onKeyUp="searchTree('${random}hrPersonTreeInPactForSelect',this)"/>
         </span>
  	  </div>		
				<div id="${random}hrPersonTreeInPactForSelect" class="ztree"></div>
			</div>
			<div id="${random}pactForSelect_layout-center" class="pane ui-layout-center">
				<div id="${random}pactForSelect_gridtable_div" layoutH="118" class="grid-wrapdiv" class="unitBox"
					style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="${random}pactForSelect_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_${random}pactForSelect_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
		 			<table id="${random}pactForSelect_gridtable"></table>
				</div>
				<div class="panelBar" id="${random}pactForSelect_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="${random}pactForSelect_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />.<s:text name="pager.total" /><label id="${random}pactForSelect_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="${random}pactForSelect_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>