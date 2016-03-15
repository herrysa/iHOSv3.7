
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var pactBreakGridIdString="#pactBreak_gridtable";
	jQuery(document).ready(function() {
		jQuery("#pactBreak_pageHeader").find("select").css("font-size","12px");
		var pactBreakFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('pactBreak_container','pactBreak_gridtable',pactBreakFullSize);
		
		hrPersonTreeInPactBreak();
		var initFlag = 0;
		var pactBreakGrid = jQuery(pactBreakGridIdString);
	    pactBreakGrid.jqGrid({
	    	url : "pactBreakGridList?1=1",
	    	editurl:"pactBreakGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="pactBreak.id" />',hidden:true,key:true},				
				{name:'breakNo',index:'breakNo',align:'left',width : '120',label : '<s:text name="pactBreak.breakNo" />',hidden:false,highsearch:true},				
				{name:'pact.code',index:'pact.code',align:'left',label : '<s:text name="pact.code" />',hidden:false,highsearch:true},				
				{name:'pact.hrPerson.name',index:'pact.hrPerson.name',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:false,highsearch:true},				
				{name:'pact.hrPerson.personId',index:'pact.hrPerson.personId',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'pact.personSnapCode',index:'pact.personSnapCode',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'pact.personCurrentSnapCode',index:'pact.personCurrentSnapCode',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'pact.id',index:'pact.id',align:'left',label : '<s:text name="pact.code" />',hidden:true},				
				//{name:'breakDate',index:'breakDate',align:'center',label : '<s:text name="pactBreak.breakDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'breakReason',index:'breakReason',align:'left',label : '<s:text name="pactBreak.breakReason" />',hidden:false,highsearch:true},				
				<c:if test="${pactNeedCheck=='1'}">
				{name:'makePerson.name',index:'makePerson.name',align:'left',width:'60',label : '<s:text name="pactBreak.makePerson" />',hidden:false,highsearch:true},				
				{name:'makeDate',index:'makeDate',align:'center',width:'80',label : '<s:text name="pactBreak.makeDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'checkPerson.name',index:'checkPerson.name',align:'left',width:'60',label : '<s:text name="pactBreak.checkPerson" />',hidden:false,highsearch:true},				
				{name:'checkDate',index:'checkDate',align:'center',width:'80',label : '<s:text name="pactBreak.checkDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'confirmPerson.name',index:'confirmPerson.name',align:'left',width:'60',label : '<s:text name="pactBreak.confirmPerson" />',hidden:false,highsearch:true},				
				{name:'confirmDate',index:'confirmDate',align:'center',width:'80',label : '<s:text name="pactBreak.confirmDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				</c:if>
				{name:'state',index:'state',align:'center',label : '<s:text name="pactBreak.state" />',hidden:false,highsearch:true,formatter:'select',edittype : 'select',editoptions : {value : '1:新建;2:已审核;3:已执行'}},				
				{name:'remark',index:'remark',align:'left',label : '<s:text name="pactBreak.remark" />',hidden:false,highsearch:true}				

	        ],
	        jsonReader : {
				root : "pactBreaks", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'breakNo',
	        viewrecords: true,
	        sortorder: 'desc',
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
				 gridContainerResize('pactBreak','layout');
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
	              	  setCellText(this,id,'breakNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPactBreakRecord(\''+ret[i]['id']+'\');">'+ret[i]["breakNo"]+'</a>');
		              	var snapId = ret[i]['pact.hrPerson.personId'] +'_'+ret[i]['pact.personSnapCode'];
		              	if(ret[i]['pact.personCurrentSnapCode']){
		              		snapId = ret[i]['pact.hrPerson.personId'] +'_'+ret[i]['pact.personCurrentSnapCode'];
		              	}
		   	          	setCellText(this,id,'pact.hrPerson.name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrPersonRecord(\''+snapId+'\');">'+ret[i]["pact.hrPerson.name"]+'</a>');
	              }
	            }else{
	            	var tw = jQuery("#pactBreak_gridtable").outerWidth();
					jQuery("#pactBreak_gridtable").parent().width(tw);
					jQuery("#pactBreak_gridtable").parent().height(1);
	            }
	           var dataTest = {"id":"pactBreak_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	 initFlag = initColumn('pactBreak_gridtable','com.huge.ihos.hr.pact.model.PactBreak',initFlag);
	       	} 
	    });
	    jQuery(pactBreakGrid).jqGrid('bindKeys');
	    
	    /*----------------------------toolBar start---------------------------------------------------*/
	    var pactBreak_menuButtonArrJson = "${menuButtonArrJson}";
    	var pactBreak_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(pactBreak_menuButtonArrJson,false)));
    	var pactBreak_toolButtonBar = new ToolButtonBar({el:$('#pactBreak_buttonBar'),collection:pactBreak_toolButtonCollection,attributes:{
    		tableId : 'pactBreak_gridtable',
    		baseName : 'pactBreak',
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
    	
    	var pactBreak_function = new scriptFunction();
    	pactBreak_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.checkPeriod){
				if('${yearStarted}'!='true'){
					alertMsg.error("本年度人力资源系统未启用。");
	    			return pass;
				}
			}
			if(param.selectRecord){
				var sid = jQuery("#pactBreak_gridtable").jqGrid('getGridParam','selarrrow');
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
						var row = jQuery("#pactBreak_gridtable").jqGrid('getRowData',rowId);
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
		pactBreak_toolButtonBar.addCallBody('1003020301',function(e,$this,param){
			var url = "pactForSelect?random=${random}&directBusiness=break";
			var hrPersonTreeObj = $.fn.zTree.getZTreeObj("hrPersonTreeInPactBreak");
			var nodes = hrPersonTreeObj.getSelectedNodes();
			if(nodes.length==1 && nodes[0].subSysTem!='ALL'){
				url += "&nodeId="+nodes[0].id+"&nodeType="+nodes[0].subSysTem; 			
			}
			var winTitle='<s:text name="pactForSelect.title"/>';
			$.pdialog.open(url,'pactForSelect',winTitle, {mask:true,width : 860,height : 628,resizable:false});
		},{});
		pactBreak_toolButtonBar.addBeforeCall('1003020301',function(e,$this,param){
			return pactBreak_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
		//删除
		pactBreak_toolButtonBar.addCallBody('1003020302',function(e,$this,param){
			var url = "${ctx}/pactBreakGridEdit?oper=del"
			var sid = jQuery("#pactBreak_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactBreak_gridtable";
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		pactBreak_toolButtonBar.addBeforeCall('1003020302',function(e,$this,param){
			return pactBreak_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"删除",status:"新建",checkPeriod:"checkPeriod"});
		// 修改
		pactBreak_toolButtonBar.addCallBody('1003020303',function(e,$this,param){
			var sid = jQuery("#pactBreak_gridtable").jqGrid('getGridParam','selarrrow');
			var winTitle='<s:text name="pactBreakEdit.title"/>';
			var url = "editPactBreak?navTabId=pactBreak_gridtable&random=${random}&id="+sid;
			$.pdialog.open(url,'editPactBreak',winTitle, {mask:true,width : 600,height : 300,maxable:false,resizable:false});
		},{});
		pactBreak_toolButtonBar.addBeforeCall('1003020303',function(e,$this,param){
			return pactBreak_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",singleSelect:"单选",opt:"修改",status:"新建",checkPeriod:"checkPeriod"});
		//审核
		pactBreak_toolButtonBar.addCallBody('1003020304',function(e,$this,param){
			var url = "${ctx}/pactBreakGridEdit?oper=check"
			var sid = jQuery("#pactBreak_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactBreak_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认审核？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		pactBreak_toolButtonBar.addBeforeCall('1003020304',function(e,$this,param){
			return pactBreak_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"审核",status:"新建",checkPeriod:"checkPeriod"});
		//销审
		pactBreak_toolButtonBar.addCallBody('1003020305',function(e,$this,param){
			var url = "${ctx}/pactBreakGridEdit?oper=cancelCheck"
			var sid = jQuery("#pactBreak_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactBreak_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认销审？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		pactBreak_toolButtonBar.addBeforeCall('1003020305',function(e,$this,param){
			return pactBreak_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"销审",status:"已审核",checkPeriod:"checkPeriod"});
		//撤销
		pactBreak_toolButtonBar.addCallBody('1003020306',function(e,$this,param){
			var url = "${ctx}/pactBreakGridEdit?oper=confirm"
			var sid = jQuery("#pactBreak_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactBreak_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认执行？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
						if(data.statusCode==200){
							alertMsg.confirm("是否修改人员状态为离职？", {
								okCall : function() {
									var afterUrl = "cascadeUpdatePersonByBreakPact?idType=break&id="+sid;// 终止单id
									$.post(afterUrl,function(data) {
										formCallBack(data);
										if(data.statusCode==200){
											for(var i = 0;i < sid.length; i++){
												var rowId = sid[i];
												var row = jQuery("#pactBreak_gridtable").jqGrid('getRowData',rowId);
												var personId = row['pact.hrPerson.personId'];
												dealHrTreeNode('hrPersonTreeInPactBreak',{id:personId},'disable','person');
									        }
										}
									});
								}
							});
						}
					});
				}
			});
		},{});
		pactBreak_toolButtonBar.addBeforeCall('1003020306',function(e,$this,param){
			return pactBreak_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"执行",status:"已审核",checkPeriod:"checkPeriod"});
		var pactBreak_setColShowButton = {id:'1003020388',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
	   			callBody:function(){
	   				setColShow('pactBreak_gridtable','com.huge.ihos.hr.pact.model.PactBreak');
	   		}};
		pactBreak_toolButtonBar.addButton(pactBreak_setColShowButton);
    	/*--------------------------------------tooBar end-------------------------------------------*/
  	});
	function viewPactBreakRecord(id){
		var url = "editPactBreak?oper=view&random=${random}&id="+id;
		$.pdialog.open(url,'viewPactBreak','查看终止合同信息', {mask:true,width : 600,height : 300,maxable:false,resizable:false});
	}
	/*------------------------------tree method area-----------------------------------------*/
	function hrPersonTreeInPactBreak() {
		var treeUrl = "makeHrPersonTree?1=1&showPersonType=true";
		//var showDisabledDept = jQuery("#pactBreak_showDisabledDept").attr("checked");
		//var showDisabledPerson = jQuery("#pactBreak_showDisabledPerson").attr("checked");
		/* if(showDisabledDept){
			treeUrl += "&showDisabledDept=show";
		} */
		/* if(showDisabledPerson){
			treeUrl += "&showDisabledPerson=show";	
		} */
		$.get(treeUrl, {"_" : $.now()}, function(data) {
			var hrPersonTreeData = data.hrPersonTreeNodes;
			var hrPersonTree = $.fn.zTree.init($("#hrPersonTreeInPactBreak"),ztreesetting_hrPersonTreeInPactBreak, hrPersonTreeData);
			var nodes = hrPersonTree.getNodes();
			hrPersonTree.expandNode(nodes[0], true, false, true);
			hrPersonTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'hrPersonTreeInPactBreak',
		         showCode:jQuery('#pactBreak_showCode')[0],
		         disabledDept:jQuery("#pactBreak_showDisabledDept")[0],
		         disabledPerson: jQuery("#pactBreak_showDisabledPerson")[0],
		         showCount:true });
		});
		jQuery("#pactBreak_expandTree").text("展开");
	}
	
	var ztreesetting_hrPersonTreeInPactBreak = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				fontCss : setDisabledDeptFontCss
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var urlString = "pactBreakGridList?1=1";
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
				    var showDisabledDept = jQuery("#pactBreak_showDisabledDept").attr("checked");
					var showDisabledPerson = jQuery("#pactBreak_showDisabledPerson").attr("checked");
					if(showDisabledDept){
						urlString += "&showDisabledDept=show";
					}
					if(showDisabledPerson){
						urlString += "&showDisabledPerson=show";	
					}
					urlString=encodeURI(urlString);
					jQuery("#pactBreak_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
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
	
	function showDisabledDeptInPactBreak(obj){
		//var urlString = "pactBreakGridList?1=1";
		var urlString = jQuery("#pactBreak_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledDept=show";
		}
		var showDisabledPerson = jQuery("#pactBreak_showDisabledPerson").attr("checked");
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=show";	
		}
		//hrPersonTreeInPactBreak();
		toggleDisabledOrCount({treeId:'hrPersonTreeInPactBreak',
		         showCode:jQuery('#pactBreak_showCode')[0],
		         disabledDept:jQuery("#pactBreak_showDisabledDept")[0],
		         disabledPerson: jQuery("#pactBreak_showDisabledPerson")[0],
		         showCount:true });
		urlString=encodeURI(urlString);
		jQuery("#pactBreak_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
	}
	
	function showDisbaledPersonInPactBreak(obj){
		//var urlString = "pactBreakGridList?1=1";
		var urlString = jQuery("#pactBreak_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledPerson=show";
		}
		var showDisabledDept = jQuery("#pactBreak_showDisabledDept").attr("checked");
		if(showDisabledDept){
			urlString += "&showDisabledDept=show";
		}
		toggleDisabledOrCount({treeId:'hrPersonTreeInPactBreak',
	         showCode:jQuery('#pactBreak_showCode')[0],
	         disabledDept:jQuery("#pactBreak_showDisabledDept")[0],
	         disabledPerson: jQuery("#pactBreak_showDisabledPerson")[0],
	         showCount:true });
		urlString=encodeURI(urlString);
		jQuery("#pactBreak_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
	}
</script>

<div class="page">
	<div class="pageHeader" id="pactBreak_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="pactBreak_search_form"  style="white-space: break-all;word-wrap:break-word;" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='pactBreak.breakNo'/>:
      					<input type="text" name="filter_LIKES_breakNo"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.code'/>:
      					<input type="text" name="filter_LIKES_pact.code"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.hrPerson'/>:
      					<input type="text" name="personName"/>
					</label>
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='pactBreak.breakDate'/>:
      					<input type="text" id="pactBreak_search_form_breakDate_from" name="filter_GED_validDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'pactBreak_search_form_breakDate_to\')}'})">
						<s:text name='至'/>
					 	<input type="text" id="pactBreak_search_form_breakDate_to" name="filter_LED_validDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'pactBreak_search_form_breakDate_from\')}'})">
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pactBreak.breakReason'/>:
      					<input type="text" name="filter_LIKES_breakReason"/>
					</label>	
					<label style="float:none;white-space:nowrap" >
						<s:text name='pactBreak.state'/>:
						<s:select name="filter_EQI_state" list="#{'':'--','1':'新建','2':'已审核','3':'已执行'}" style="width:70px" ></s:select>
					</label>
						<label style="float:none;white-space:nowrap"></label>
						<s:select id="pactBreak_searchTime"   list="#{'0':'执行日期','1':'填制日期','2':'审核日期'}" style="width:100px;font-size:9pt;" ></s:select>
						<s:text name=" 从"/>:
						<input type="text" id="pactBreakbeginDate"  name="hrPerson.beginDate" onclick="changeSysTimeType('pactBreak','confirmDate','makeDate','checkDate')" onblur="checkQueryDate('pactBreakbeginDate','pactBreakendDate',0,'pactBreak_beginDate','pactBreak_endDate')" style="width:65px"/>
					<label style="float:none;white-space:nowrap" >
						<s:text name="至"/>:
						<input type="text" id="pactBreakendDate" name="hrPerson.endDate" onclick="changeSysTimeType('pactBreak','confirmDate','makeDate','checkDate')"  onblur="checkQueryDate('pactBreakbeginDate','pactBreakendDate',1,'pactBreak_beginDate','pactBreak_endDate')" style="width:65px"/>
						<input type="hidden" id="pactBreak_beginDate" name="hrPerson.endDate"  />
						<input type="hidden" id="pactBreak_endDate" name="hrPerson.endDate"  />
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('pactBreak_search_form','pactBreak_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>	
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive" style="float:right"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('pactBreak_search_form','pactBreak_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div id="pactBreak_buttonBar" class="panelBar">
			<!-- <ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:addPactBreak()" ><span><s:text name="button.add"/></span></a>
				</li>
				<li>
					<a class="delbutton"  href="javaScript:pactBreakListEditOper('del')"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a class="changebutton"  href="javaScript:editPactBreak()"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a class="checkbutton"  href="javaScript:pactBreakListEditOper('check')"><span><s:text name="button.check" /></span></a>
				</li>
				<li>
					<a class="delallbutton"  href="javaScript:pactBreakListEditOper('cancelCheck')"><span><s:text name="button.cancelCheck" /></span></a>
				</li>
				<li>
					<a class="confirmbutton"  href="javaScript:pactBreakListEditOper('confirm')"><span><s:text name="终止" /></span></a>
				</li>
			</ul> -->
		</div>
		<div id="pactBreak_container">
			<div id="pactBreak_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
        <div class="treeTopCheckBox">
          <span>
           	显示编码
           <input id="pactBreak_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrPersonTreeInPactBreak',showCode:this,disabledDept:jQuery('#pactBreak_showDisabledDept')[0],disabledPerson:jQuery('#pactBreak_showDisabledPerson')[0],showCount:true})"/>
         </span>
         <span>
           	显示停用部门
          <input id="pactBreak_showDisabledDept" type="checkbox" onclick="showDisabledDeptInPactBreak(this)"/>
         </span>
         <label id="pactBreak_expandTree" onclick="toggleExpandHrTree(this,'hrPersonTreeInPactBreak')">展开</label>
        </div>
       <div class="treeTopCheckBox">
          <span>
           	显示停用人员
          <input id="pactBreak_showDisabledPerson" type="checkbox" onclick="showDisbaledPersonInPactBreak(this)"/>
          </span>
        </div>
        <div class="treeTopCheckBox">
         <span>
          	 快速查询：
          <input type="text" onKeyUp="searchTree('hrPersonTreeInPactBreak',this)"/>
         </span>
   		 </div>			
				<div id="hrPersonTreeInPactBreak" class="ztree"></div>
			</div>
			<div id="pactBreak_layout-center" class="pane ui-layout-center">
				<div id="pactBreak_gridtable_div" class="grid-wrapdiv"
					style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="pactBreak_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_pactBreak_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
					 <table id="pactBreak_gridtable"></table>
				</div>
				<div class="panelBar" id="pactBreak_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="pactBreak_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="pactBreak_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
			
					<div id="pactBreak_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
