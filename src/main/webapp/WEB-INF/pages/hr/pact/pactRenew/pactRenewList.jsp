
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var pactRenewGridIdString="#pactRenew_gridtable";
	jQuery(document).ready(function() { 
		jQuery("#pactRenew_pageHeader").find("select").css("font-size","12px");
		var pactRenewFullSize = jQuery("#container").innerHeight()-144;
		setLeftTreeLayout('pactRenew_container','pactRenew_gridtable',pactRenewFullSize);
		
		hrPersonTreeInPactRenew();
		var initFlag = 0;
		var pactRenewGrid = jQuery(pactRenewGridIdString);
    	pactRenewGrid.jqGrid({
	    	url : "pactRenewGridList?1=1",
	    	editurl:"pactRenewGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="pactRenew.id" />',hidden:true,key:true},				
				{name:'renewNo',index:'renewNo',align:'left',width : '120',label : '<s:text name="pactRenew.renewNo" />',hidden:false,highsearch:true},				
				{name:'pact.code',index:'pact.code',align:'left',label : '<s:text name="pact.code" />',hidden:false,highsearch:true},				
				{name:'pact.hrPerson.name',index:'pact.hrPerson.name',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:false,highsearch:true},				
				{name:'pact.hrPerson.personId',index:'pact.hrPerson.personId',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'pact.personSnapCode',index:'pact.personSnapCode',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'pact.personCurrentSnapCode',index:'pact.personCurrentSnapCode',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'pact.id',index:'pact.id',align:'left',label : '<s:text name="pact.code" />',hidden:true},				
				{name:'renewYear',index:'renewYear',align:'right',label : '<s:text name="pactRenew.renewYear" />',hidden:false,formatter:'integer',highsearch:true},				
				//{name:'validDate',index:'validDate',align:'center',label : '<s:text name="pactRenew.validDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				<c:if test="${pactNeedCheck=='1'}">
				{name:'makePerson.name',index:'makePerson.name',align:'left',width:'60',label : '<s:text name="pactRenew.makePerson" />',hidden:false,highsearch:true},				
				{name:'makeDate',index:'makeDate',align:'center',width:'80',label : '<s:text name="pactRenew.makeDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'checkPerson.name',index:'checkPerson.name',align:'left',width:'60',label : '<s:text name="pactRenew.checkPerson" />',hidden:false,highsearch:true},				
				{name:'checkDate',index:'checkDate',align:'center',width:'80',label : '<s:text name="pactRenew.checkDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'confirmPerson.name',index:'confirmPerson.name',align:'left',width:'60',label : '<s:text name="pactRenew.confirmPerson" />',hidden:false,highsearch:true},				
				{name:'confirmDate',index:'confirmDate',align:'center',width:'80',label : '<s:text name="pactRenew.confirmDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				</c:if>
				{name:'state',index:'state',align:'center',label : '<s:text name="pactRenew.state" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '1:新建;2:已审核;3:已执行'}},				
				{name:'remark',index:'remark',align:'left',width:'150',label : '<s:text name="pactRenew.remark" />',hidden:false,highsearch:true}			
	
	        ],
	        jsonReader : {
				root : "pactRenews", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'renewNo',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="pactRenewList.title" />',
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
				gridContainerResize('pactRenew','layout',0,30);
				 var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  id=rowIds[i];
	              	  if(ret[i]['state']=="1"){
	              		  setCellText(this,id,'state','<span >新建</span>');
	              	  }else if(ret[i]['state']=="2"){
	              		  setCellText(this,id,'state','<span style="color:green" >已审核</span>');
	              	  }else if(ret[i]['state']=="3"){
	              		  setCellText(this,id,'state','<span style="color:blue" >已执行</span>');
	              	  }
	              	  setCellText(this,id,'pact.code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPactRecord(\''+ret[i]['pact.id']+'\');">'+ret[i]["pact.code"]+'</a>');
	              	  setCellText(this,id,'renewNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPactRenewRecord(\''+ret[i]['id']+'\');">'+ret[i]["renewNo"]+'</a>');
		              	var snapId = ret[i]['pact.hrPerson.personId'] +'_'+ret[i]['pact.personSnapCode'];
		              	if(ret[i]['pact.personCurrentSnapCode']){
		              		snapId = ret[i]['pact.hrPerson.personId'] +'_'+ret[i]['pact.personCurrentSnapCode'];
		              	}
		              	setCellText(this,id,'pact.hrPerson.name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrPersonRecord(\''+snapId+'\');">'+ret[i]["pact.hrPerson.name"]+'</a>');
	              }
	            }else{
	            	var tw = jQuery("#pactRenew_gridtable").outerWidth();
					jQuery("#pactRenew_gridtable").parent().width(tw);
					jQuery("#pactRenew_gridtable").parent().height(1);
	            }
	           var dataTest = {"id":"pactRenew_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	 	initFlag = initColumn('pactRenew_gridtable','com.huge.ihos.hr.pact.model.PactRenew',initFlag);
	       	} 
    	});
   	 	jQuery(pactRenewGrid).jqGrid('bindKeys');
   	 	
   	 /*----------------------------toolBar start---------------------------------------------------*/
	    var pactRenew_menuButtonArrJson = "${menuButtonArrJson}";
    	var pactRenew_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(pactRenew_menuButtonArrJson,false)));
    	var pactRenew_toolButtonBar = new ToolButtonBar({el:$('#pactRenew_buttonBar'),collection:pactRenew_toolButtonCollection,attributes:{
    		tableId : 'pactRenew_gridtable',
    		baseName : 'pactRenew',
    		width : 600,
    		height : 600,
    		base_URL : null,
    		optId : null,
    		fatherGrid : null,
    		extraParam : null,
    		selectNone : "请选择记录。",
    		selectMore : "只能选择一条记录。",
    		addTitle : null,
    		editTitle : null
    	}}).render();
    	
    	var pactRenew_function = new scriptFunction();
    	pactRenew_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.checkPeriod){
				if('${yearStarted}'!='true'){
					alertMsg.error("本年度人力资源系统未启用。");
	    			return pass;
				}
			}
			if(param.selectRecord){
				var sid = jQuery("#pactRenew_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
		        	alertMsg.error("请选择记录。");
					return pass;
				}
		        if(param.singleSelect){
		        	if(sid.length != 1){
			        	alertMsg.error("只能选择一条记录。");
						return pass;
					}
		        }
		        if(param.opt){
		        	for(var i = 0;i < sid.length; i++){
						var rowId = sid[i];
						var row = jQuery("#pactRenew_gridtable").jqGrid('getRowData',rowId);
						if(param.status=='新建'){
							if(row['state']!='1'){
								alertMsg.error("只能"+param.opt+"处于  ["+param.status+"] 状态的记录。");
								return pass;
							}
						}else{
							if(row['state']!='2'){
								alertMsg.error("只能"+param.opt+"处于  ["+param.status+"] 状态的记录。");
								return pass;
							}
						}
			        }
		        }
			}
	        return true;
		};
		//添加
		pactRenew_toolButtonBar.addCallBody('1003020202',function(e,$this,param){
			var winTitle='<s:text name="pactForSelect.title"/>';
			var url = "pactForSelect?random=${random}&directBusiness=renew";
			var hrPersonTreeObj = $.fn.zTree.getZTreeObj("hrPersonTreeInPactRenew");
			var nodes = hrPersonTreeObj.getSelectedNodes();
			if(nodes.length==1 && nodes[0].subSysTem!='ALL'){
				url += "&nodeId="+nodes[0].id+"&nodeType="+nodes[0].subSysTem; 			
			}
			$.pdialog.open(url,'pactForSelect',winTitle, {mask:true,width : 860,height : 628,resizable:false});
		},{});
		pactRenew_toolButtonBar.addBeforeCall('1003020202',function(e,$this,param){
			return pactRenew_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
		//删除
		pactRenew_toolButtonBar.addCallBody('1003020203',function(e,$this,param){
			var url = "${ctx}/pactRenewGridEdit?oper=del"
			var sid = jQuery("#pactRenew_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactRenew_gridtable";
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		pactRenew_toolButtonBar.addBeforeCall('1003020203',function(e,$this,param){
			return pactRenew_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"删除",status:"新建",checkPeriod:"checkPeriod"});
		// 修改
		pactRenew_toolButtonBar.addCallBody('1003020204',function(e,$this,param){
			var sid = jQuery("#pactRenew_gridtable").jqGrid('getGridParam','selarrrow');
			var winTitle='<s:text name="pactRenewEdit.title"/>';
			var url = "editPactRenew?navTabId=pactRenew_gridtable&random=${random}&id="+sid;
			$.pdialog.open(url,'editPactRenew',winTitle, {mask:true,width : 600,height : 300,maxable:false,resizable:false});
		},{});
		pactRenew_toolButtonBar.addBeforeCall('1003020204',function(e,$this,param){
			return pactRenew_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",singleSelect:"单选",opt:"修改",status:"新建",checkPeriod:"checkPeriod"});
		//审核
		pactRenew_toolButtonBar.addCallBody('1003020205',function(e,$this,param){
			var url = "${ctx}/pactRenewGridEdit?oper=check"
			var sid = jQuery("#pactRenew_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactRenew_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认审核？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		pactRenew_toolButtonBar.addBeforeCall('1003020205',function(e,$this,param){
			return pactRenew_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"审核",status:"新建",checkPeriod:"checkPeriod"});
		//销审
		pactRenew_toolButtonBar.addCallBody('1003020206',function(e,$this,param){
			var url = "${ctx}/pactRenewGridEdit?oper=cancelCheck"
			var sid = jQuery("#pactRenew_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactRenew_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认销审？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		pactRenew_toolButtonBar.addBeforeCall('1003020206',function(e,$this,param){
			return pactRenew_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"销审",status:"已审核",checkPeriod:"checkPeriod"});
		//撤销
		pactRenew_toolButtonBar.addCallBody('1003020207',function(e,$this,param){
			var url = "${ctx}/pactRenewGridEdit?oper=confirm"
			var sid = jQuery("#pactRenew_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactRenew_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认执行？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		pactRenew_toolButtonBar.addBeforeCall('1003020207',function(e,$this,param){
			return pactRenew_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"执行",status:"已审核",checkPeriod:"checkPeriod"});
		var pactRenew_setColShowButton = {id:'1003020288',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
	   			callBody:function(){
	   				setColShow('pactRenew_gridtable','com.huge.ihos.hr.pact.model.PactRenew');
	   		}};
		pactRenew_toolButtonBar.addButton(pactRenew_setColShowButton);
    	/*--------------------------------------tooBar end-------------------------------------------*/
  	});
	function viewPactRenewRecord(id){
		var url = "editPactRenew?oper=view&random=${random}&id="+id;
		$.pdialog.open(url,'viewPactRenew','查看续签合同信息', {mask:true,width : 600,height : 300,maxable:false,resizable:false});
	}
	/*------------------------------tree method area-----------------------------------------*/
	function hrPersonTreeInPactRenew() {
		var treeUrl = "makeHrPersonTree?1=1&showPersonType=true";
		//var showDisabledDept = jQuery("#pactRenew_showDisabledDept").attr("checked");
		//var showDisabledPerson = jQuery("#pactRenew_showDisabledPerson").attr("checked");
		/* if(showDisabledDept){
			treeUrl += "&showDisabledDept=show";
		} */
		/* if(showDisabledPerson){
			treeUrl += "&showDisabledPerson=show";	
		} */
		$.get(treeUrl, {"_" : $.now()}, function(data) {
			var hrPersonTreeData = data.hrPersonTreeNodes;
			var hrPersonTree = $.fn.zTree.init($("#hrPersonTreeInPactRenew"),ztreesetting_hrPersonTreeInPactRenew, hrPersonTreeData);
			var nodes = hrPersonTree.getNodes();
			hrPersonTree.expandNode(nodes[0], true, false, true);
			hrPersonTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'hrPersonTreeInPactRenew',
		         showCode:jQuery('#pactRenew_showCode')[0],
		         disabledDept:jQuery("#pactRenew_showDisabledDept")[0],
		         disabledPerson: jQuery("#pactRenew_showDisabledPerson")[0],
		         showCount:true });
		});
		jQuery("#pactRenew_expandTree").text("展开");
	}
	var ztreesetting_hrPersonTreeInPactRenew = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				fontCss : setDisabledDeptFontCss
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var urlString = "pactRenewGridList?1=1";
					var nodeId = treeNode.id;
				    if(nodeId!="-1"){
				    	if(treeNode.subSysTem==='ORG'){
					    	urlString += "&orgCode="+nodeId;
				    	}else if(treeNode.subSysTem==='DEPT'){
					    	urlString += "&deptId="+nodeId;
				    	}else{
				    		urlString += "&personId="+nodeId;
				    	}
				    }
				    var showDisabledDept = jQuery("#pactRenew_showDisabledDept").attr("checked");
					var showDisabledPerson = jQuery("#pactRenew_showDisabledPerson").attr("checked");
					if(showDisabledDept){
						urlString += "&showDisabledDept=show";
					}
					if(showDisabledPerson){
						urlString += "&showDisabledPerson=show";	
					}
					urlString=encodeURI(urlString);
					jQuery("#pactRenew_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
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
	
	function showDisabledDeptInPactRenew(obj){
		//var urlString = "pactRenewGridList?1=1";
		var urlString = jQuery("#pactRenew_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledDept=show";
		}
		var showDisabledPerson = jQuery("#pactRenew_showDisabledPerson").attr("checked");
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=show";	
		}
		//hrPersonTreeInPactRenew();
		toggleDisabledOrCount({treeId:'hrPersonTreeInPactRenew',
		         showCode:jQuery('#pactRenew_showCode')[0],
		         disabledDept:jQuery("#pactRenew_showDisabledDept")[0],
		         disabledPerson: jQuery("#pactRenew_showDisabledPerson")[0],
		         showCount:true });
		urlString=encodeURI(urlString);
		jQuery("#pactRenew_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	function showDisbaledPersonInPactRenew(obj){
		//var urlString = "pactRenewGridList?1=1";
		var urlString = jQuery("#pactRenew_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledPerson=show";
		}
		var showDisabledDept = jQuery("#pactRenew_showDisabledDept").attr("checked");
		if(showDisabledDept){
			urlString += "&showDisabledDept=show";
		}
		toggleDisabledOrCount({treeId:'hrPersonTreeInPactRenew',
	         showCode:jQuery('#pactRenew_showCode')[0],
	         disabledDept:jQuery("#pactRenew_showDisabledDept")[0],
	         disabledPerson: jQuery("#pactRenew_showDisabledPerson")[0],
	         showCount:true });
		urlString=encodeURI(urlString);
		jQuery("#pactRenew_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
	}
</script>

<div class="page" id="pactRenew_page">
	<div class="pageHeader" id="pactRenew_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="pactRenew_search_form" class="queryarea-form">	
					<label class="queryarea-label" >
						<s:text name='pactRenew.renewNo'/>:
      					<input type="text" name="filter_LIKES_renewNo"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='pact.code'/>:
      					<input type="text" name="filter_LIKES_pact.code"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='pact.hrPerson'/>:
      					<input type="text" name="personName"/>
					</label>
					<label class="queryarea-label"></label>
						<s:select id="pactRenew_searchTime"   list="#{'0':'执行日期','1':'填制日期','2':'审核日期'}" style="width:100px;font-size:9pt;" ></s:select>
						<s:text name=" 从"/>:
						<input type="text" id="pactRenewbeginDate"  name="hrPerson.beginDate" onclick="changeSysTimeType('pactRenew','confirmDate','makeDate','checkDate')" onblur="checkQueryDate('pactRenewbeginDate','pactRenewendDate',0,'pactRenew_beginDate','pactRenew_endDate')" style="width:65px"/>
					<label class="queryarea-label" >
						<s:text name="至"/>:
						<input type="text" id="pactRenewendDate" name="hrPerson.endDate" onclick="changeSysTimeType('pactRenew','confirmDate','makeDate','checkDate')"  onblur="checkQueryDate('pactRenewbeginDate','pactRenewendDate',1,'pactRenew_beginDate','pactRenew_endDate')" style="width:65px"/>
						<input type="hidden" id="pactRenew_beginDate" name="hrPerson.endDate"  />
						<input type="hidden" id="pactRenew_endDate" name="hrPerson.endDate"  />
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='pactRenew.validDate'/>:
      					<input type="text" id="pactRenew_search_form_validDate_from" name="filter_GED_validDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'pactRenew_search_form_validDate_to\')}'})">
						<s:text name='至'/>
					 	<input type="text" id="pactRenew_search_form_validDate_to" name="filter_LED_validDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'pactRenew_search_form_validDate_from\')}'})">
					</label> --%>
					<label class="queryarea-label">
						<s:text name='pactRenew.state'/>:
						<s:select name="filter_EQI_state" list="#{'':'--','1':'新建','2':'已审核','3':'已执行'}" style="width:70px" ></s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('pactRenew_search_form','pactRenew_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>	
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive" style="float:right"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('pactRenew_search_form','pactRenew_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div id="pactRenew_buttonBar" class="panelBar">
			<!-- <ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:addPactRenew()" ><span><s:text name="button.add"/></span></a>
				</li>
				<li>
					<a class="delbutton"  href="javaScript:pactRenewListEditOper('del')"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a class="changebutton"  href="javaScript:editPactRenew()"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a class="checkbutton"  href="javaScript:pactRenewListEditOper('check')"><span><s:text name="button.check" /></span></a>
				</li>
				<li>
					<a class="delallbutton"  href="javaScript:pactRenewListEditOper('cancelCheck')"><span><s:text name="button.cancelCheck" /></span></a>
				</li>
				<li>
					<a class="confirmbutton"  href="javaScript:pactRenewListEditOper('confirm')"><span><s:text name="续签" /></span></a>
				</li>
			</ul> -->
		</div>
		<div id="pactRenew_container">
			<div id="pactRenew_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
        <div class="treeTopCheckBox">
          <span>
          	 显示编码
           <input id="pactRenew_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrPersonTreeInPactRenew',showCode:this,disabledDept:jQuery('#pactRenew_showDisabledDept')[0],disabledPerson:jQuery('#pactRenew_showDisabledPerson')[0],showCount:true})"/>
         </span>
         <span>
          	 显示停用部门
          <input id="pactRenew_showDisabledDept" type="checkbox" onclick="showDisabledDeptInPactRenew(this)"/>
         </span>
         <label id="pactRenew_expandTree" onclick="toggleExpandHrTree(this,'hrPersonTreeInPactRenew')">展开</label>
        </div>
       <div class="treeTopCheckBox">
          <span>
           	显示停用人员
          <input id="pactRenew_showDisabledPerson" type="checkbox" onclick="showDisbaledPersonInPactRenew(this)"/>
          </span>
        </div>
        <div class="treeTopCheckBox">
         <span>
         	  快速查询：
          <input type="text" onKeyUp="searchTree('hrPersonTreeInPactRenew',this)"/>
         </span>
   		</div>
				<div id="hrPersonTreeInPactRenew" class="ztree"></div>
			</div>
			<div id="pactRenew_layout-center" class="pane ui-layout-center">
				<div id="pactRenew_gridtable_div" style="margin-left: 2px; margin-top: 2px; overflow: hidden"
					class="grid-wrapdiv" class="unitBox">
					<input type="hidden" id="pactRenew_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_pactRenew_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
		 			<table id="pactRenew_gridtable"></table>
				</div>
				<div class="panelBar" id="pactRenew_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="pactRenew_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="pactRenew_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="pactRenew_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>