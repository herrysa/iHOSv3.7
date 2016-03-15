
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var pactRelieveGridIdString="#pactRelieve_gridtable";
	
	jQuery(document).ready(function() {
		jQuery("#pactRelieve_pageHeader").find("select").css("font-size","12px");
		var pactRelieveFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('pactRelieve_container','pactRelieve_gridtable',pactRelieveFullSize);
		
		hrPersonTreeInPactRelieve();
		var initFlag = 0;
		var pactRelieveGrid = jQuery(pactRelieveGridIdString);
	    pactRelieveGrid.jqGrid({
	    	url : "pactRelieveGridList?1=1",
	    	editurl:"pactRelieveGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="pactRelieve.id" />',hidden:true,key:true},				
				{name:'relieveNo',index:'relieveNo',align:'left',width : '120',label : '<s:text name="pactRelieve.relieveNo" />',hidden:false,highsearch:true},				
				{name:'pact.code',index:'pact.code',align:'left',label : '<s:text name="pact.code" />',hidden:false,highsearch:true},				
				{name:'pact.hrPerson.name',index:'pact.hrPerson.name',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:false,highsearch:true},				
				{name:'pact.hrPerson.personId',index:'pact.hrPerson.personId',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'pact.personSnapCode',index:'pact.personSnapCode',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'pact.personCurrentSnapCode',index:'pact.personCurrentSnapCode',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'pact.id',index:'pact.id',align:'left',label : '<s:text name="pact.code" />',hidden:true},				
				//{name:'relieveDate',index:'relieveDate',align:'center',label : '<s:text name="pactRelieve.relieveDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'relieveReason',index:'relieveReason',align:'left',label : '<s:text name="pactRelieve.relieveReason" />',hidden:false,highsearch:true},				
				<c:if test="${pactNeedCheck=='1'}">
				{name:'makePerson.name',index:'makePerson.name',align:'left',width:'60',label : '<s:text name="pactRelieve.makePerson" />',hidden:false,highsearch:true},				
				{name:'makeDate',index:'makeDate',align:'center',width:'80',label : '<s:text name="pactRelieve.makeDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'checkPerson.name',index:'checkPerson.name',align:'left',width:'60',label : '<s:text name="pactRelieve.checkPerson" />',hidden:false,highsearch:true},				
				{name:'checkDate',index:'checkDate',align:'center',width:'80',label : '<s:text name="pactRelieve.checkDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'confirmPerson.name',index:'confirmPerson.name',align:'left',width:'60',label : '<s:text name="pactRelieve.confirmPerson" />',hidden:false,highsearch:true},				
				{name:'confirmDate',index:'confirmDate',align:'center',width:'80',label : '<s:text name="pactRelieve.confirmDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				</c:if>
				{name:'state',index:'state',align:'center',label : '<s:text name="pactRelieve.state" />',hidden:false,highsearch:true,formatter:'select',editoptions : {value : '1:新建;2:已审核;3:已执行'}},			
				{name:'remark',index:'remark',align:'left',label : '<s:text name="pactRelieve.remark" />',hidden:false,highsearch:true}				
	        ],
	        jsonReader : {
				root : "pactRelieves", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'relieveNo',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="pactRelieveList.title" />',
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
				 gridContainerResize('pactRelieve','layout');
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
	              	  setCellText(this,id,'relieveNo','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPactRelieveRecord(\''+ret[i]['id']+'\');">'+ret[i]["relieveNo"]+'</a>');
		              	var snapId = ret[i]['pact.hrPerson.personId'] +'_'+ret[i]['pact.personSnapCode'];
		            	if(ret[i]['pact.personCurrentSnapCode']){
		              		snapId = ret[i]['pact.hrPerson.personId'] +'_'+ret[i]['pact.personCurrentSnapCode'];
		              	}
		              	setCellText(this,id,'pact.hrPerson.name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrPersonRecord(\''+snapId+'\');">'+ret[i]["pact.hrPerson.name"]+'</a>');
	              }
	            }else{
	            	var tw = jQuery("#pactRelieve_gridtable").outerWidth();
					jQuery("#pactRelieve_gridtable").parent().width(tw);
					jQuery("#pactRelieve_gridtable").parent().height(1);
	            }
	           var dataTest = {"id":"pactRelieve_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	 initFlag = initColumn('pactRelieve_gridtable','com.huge.ihos.hr.pact.model.PactRelieve',initFlag);
	       	} 
	
	    });
	    jQuery(pactRelieveGrid).jqGrid('bindKeys');
	    
	    /*----------------------------toolBar start---------------------------------------------------*/
	    var pactRelieve_menuButtonArrJson = "${menuButtonArrJson}";
    	var pactRelieve_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(pactRelieve_menuButtonArrJson,false)));
    	var pactRelieve_toolButtonBar = new ToolButtonBar({el:$('#pactRelieve_buttonBar'),collection:pactRelieve_toolButtonCollection,attributes:{
    		tableId : 'pactRelieve_gridtable',
    		baseName : 'pactRelieve',
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
    	
    	var pactRelieve_function = new scriptFunction();
    	pactRelieve_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.checkPeriod){
				if('${yearStarted}'!='true'){
					alertMsg.error("本年度人力资源系统未启用。");
	    			return pass;
				}
			}
			if(param.selectRecord){
				var sid = jQuery("#pactRelieve_gridtable").jqGrid('getGridParam','selarrrow');
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
						var row = jQuery("#pactRelieve_gridtable").jqGrid('getRowData',rowId);
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
		pactRelieve_toolButtonBar.addCallBody('1003020401',function(e,$this,param){
			var winTitle='<s:text name="pactForSelect.title"/>';
			var url = "pactForSelect?random=${random}&directBusiness=relieve";
			var hrPersonTreeObj = $.fn.zTree.getZTreeObj("hrPersonTreeInPactRelieve");
			var nodes = hrPersonTreeObj.getSelectedNodes();
			if(nodes.length==1 && nodes[0].subSysTem!='ALL'){
				url += "&nodeId="+nodes[0].id+"&nodeType="+nodes[0].subSysTem; 			
			}
			$.pdialog.open(url,'pactForSelect',winTitle, {mask:true,width : 860,height : 628,resizable:false});
		},{});
		pactRelieve_toolButtonBar.addBeforeCall('1001020101',function(e,$this,param){
			return pactRelieve_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
		//删除
		pactRelieve_toolButtonBar.addCallBody('1003020402',function(e,$this,param){
			var url = "${ctx}/pactRelieveGridEdit?oper=del"
			var sid = jQuery("#pactRelieve_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactRelieve_gridtable";
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		pactRelieve_toolButtonBar.addBeforeCall('1003020402',function(e,$this,param){
			return pactRelieve_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"删除",status:"新建",checkPeriod:"checkPeriod"});
		// 修改
		pactRelieve_toolButtonBar.addCallBody('1003020403',function(e,$this,param){
			var sid = jQuery("#pactRelieve_gridtable").jqGrid('getGridParam','selarrrow');
			var winTitle='<s:text name="pactRelieveEdit.title"/>';
			var url = "editPactRelieve?navTabId=pactRelieve_gridtable&random=${random}&id="+sid;
			$.pdialog.open(url,'editPactRelieve',winTitle, {mask:true,width : 600,height : 300,maxable:false,resizable:false});
		},{});
		pactRelieve_toolButtonBar.addBeforeCall('1003020403',function(e,$this,param){
			return pactRelieve_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",singleSelect:"单选",opt:"修改",status:"新建",checkPeriod:"checkPeriod"});
		//审核
		pactRelieve_toolButtonBar.addCallBody('1003020404',function(e,$this,param){
			var url = "${ctx}/pactRelieveGridEdit?oper=check"
			var sid = jQuery("#pactRelieve_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactRelieve_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认审核？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		pactRelieve_toolButtonBar.addBeforeCall('1003020404',function(e,$this,param){
			return pactRelieve_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"审核",status:"新建",checkPeriod:"checkPeriod"});
		//销审
		pactRelieve_toolButtonBar.addCallBody('1003020405',function(e,$this,param){
			var url = "${ctx}/pactRelieveGridEdit?oper=cancelCheck"
			var sid = jQuery("#pactRelieve_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactRelieve_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认销审？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		pactRelieve_toolButtonBar.addBeforeCall('1003020405',function(e,$this,param){
			return pactRelieve_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"销审",status:"已审核",checkPeriod:"checkPeriod"});
		//撤销
		pactRelieve_toolButtonBar.addCallBody('1003020406',function(e,$this,param){
			var url = "${ctx}/pactRelieveGridEdit?oper=confirm"
			var sid = jQuery("#pactRelieve_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactRelieve_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认执行？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
						if(data.statusCode==200){
							alertMsg.confirm("是否修改人员状态为离职？", {
								okCall : function() {
									var afterUrl = "cascadeUpdatePersonByRelievePact?idType=relieve&id="+sid;// 解除单id
									$.post(afterUrl,function(data) {
										formCallBack(data);
										if(data.statusCode==200){
											for(var i = 0;i < sid.length; i++){
												var rowId = sid[i];
												var row = jQuery("#pactRelieve_gridtable").jqGrid('getRowData',rowId);
												var personId = row['pact.hrPerson.personId'];
												dealHrTreeNode('hrPersonTreeInPactRelieve',{id:personId},'disable','person');
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
		pactRelieve_toolButtonBar.addBeforeCall('1003020406',function(e,$this,param){
			return pactRelieve_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"执行",status:"已审核",checkPeriod:"checkPeriod"});
		var pactRelieve_setColShowButton = {id:'1003020488',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
	   			callBody:function(){
	   				setColShow('pactRelieve_gridtable','com.huge.ihos.hr.pact.model.PactRelieve');
	   		}};
		pactRelieve_toolButtonBar.addButton(pactRelieve_setColShowButton);
    	/*--------------------------------------tooBar end-------------------------------------------*/
  	});
	function viewPactRelieveRecord(id){
		var url = "editPactRelieve?oper=view&random=${random}&id="+id;
		$.pdialog.open(url,'viewPactRelieve','查看解除合同信息', {mask:true,width : 600,height : 300,maxable:false,resizable:false});
	}
	/*------------------------------tree method area-----------------------------------------*/
	function hrPersonTreeInPactRelieve() {
		var treeUrl = "makeHrPersonTree?1=1&showPersonType=true";
		//var showDisabledDept = jQuery("#pactRelieve_showDisabledDept").attr("checked");
		//var showDisabledPerson = jQuery("#pactRelieve_showDisabledPerson").attr("checked");
		/* if(showDisabledDept){
			treeUrl += "&showDisabledDept=show";
		} */
		/* if(showDisabledPerson){
			treeUrl += "&showDisabledPerson=show";	
		} */
		$.get(treeUrl, {"_" : $.now()}, function(data) {
			var hrPersonTreeData = data.hrPersonTreeNodes;
			var hrPersonTree = $.fn.zTree.init($("#hrPersonTreeInPactRelieve"),ztreesetting_hrPersonTreeInPactRelieve, hrPersonTreeData);
			var nodes = hrPersonTree.getNodes();
			hrPersonTree.expandNode(nodes[0], true, false, true);
			hrPersonTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'hrPersonTreeInPactRelieve',
		         showCode:jQuery('#pactRelieve_showCode')[0],
		         disabledDept:jQuery("#pactRelieve_showDisabledDept")[0],
		         disabledPerson: jQuery("#pactRelieve_showDisabledPerson")[0],
		         showCount:true });
		});
		jQuery("#pactRelieve_expandTree").text("展开");
	}
	var ztreesetting_hrPersonTreeInPactRelieve = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				fontCss : setDisabledDeptFontCss
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var urlString = "pactRelieveGridList?1=1";
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
				    var showDisabledDept = jQuery("#pactRelieve_showDisabledDept").attr("checked");
					var showDisabledPerson = jQuery("#pactRelieve_showDisabledPerson").attr("checked");
					if(showDisabledDept){
						urlString += "&showDisabledDept=show";
					}
					if(showDisabledPerson){
						urlString += "&showDisabledPerson=show";	
					}
					urlString=encodeURI(urlString);
					jQuery("#pactRelieve_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
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
	function showDisabledDeptInPactRelieve(obj){
		//var urlString = "pactRelieveGridList?1=1";
		var urlString = jQuery("#pactRelieve_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledDept=show";
		}
		var showDisabledPerson = jQuery("#pactRelieve_showDisabledPerson").attr("checked");
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=show";	
		}
		//hrPersonTreeInPactRelieve();
		toggleDisabledOrCount({treeId:'hrPersonTreeInPactRelieve',
		         showCode:jQuery('#pactRelieve_showCode')[0],
		         disabledDept:jQuery("#pactRelieve_showDisabledDept")[0],
		         disabledPerson: jQuery("#pactRelieve_showDisabledPerson")[0],
		         showCount:true });
		urlString=encodeURI(urlString);
		jQuery("#pactRelieve_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
	}
	
	function showDisbaledPersonInPactRelieve(obj){
		//var urlString = "pactRelieveGridList?1=1";
		var urlString = jQuery("#pactRelieve_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledPerson=show";
		}
		var showDisabledDept = jQuery("#pactRelieve_showDisabledDept").attr("checked");
		if(showDisabledDept){
			urlString += "&showDisabledDept=show";
		}
		toggleDisabledOrCount({treeId:'hrPersonTreeInPactRelieve',
	         showCode:jQuery('#pactRelieve_showCode')[0],
	         disabledDept:jQuery("#pactRelieve_showDisabledDept")[0],
	         disabledPerson: jQuery("#pactRelieve_showDisabledPerson")[0],
	         showCount:true });
		urlString=encodeURI(urlString);
		jQuery("#pactRelieve_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
	}
</script>

<div class="page">
	<div class="pageHeader" id="pactRelieve_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="pactRelieve_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='pactRelieve.relieveNo'/>:
      					<input type="text" name="filter_LIKES_relieveNo"/>
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
						<s:text name='pactRelieve.relieveDate'/>:
      					<input type="text" id="pactRelieve_search_form_relieveDate_from" name="filter_GED_validDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'pactRelieve_search_form_relieveDate_to\')}'})">
						<s:text name='至'/>
					 	<input type="text" id="pactRelieve_search_form_relieveDate_to" name="filter_LED_validDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'pactRelieve_search_form_relieveDate_from\')}'})">
					</label> --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pactRelieve.relieveReason'/>:
      					<input type="text" name="filter_LIKES_relieveReason"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pactRelieve.state'/>:
						<s:select name="filter_EQI_state" list="#{'':'--','1':'新建','2':'已审核','3':'已执行'}" style="width:70px" ></s:select>
					</label>
						<label style="float:none;white-space:nowrap"></label>
						<s:select id="pactRelieve_searchTime"   list="#{'0':'执行日期','1':'填制日期','2':'审核日期'}" style="width:100px;font-size:9pt;" ></s:select>
						<s:text name=" 从"/>:
						<input type="text" id="pactRelievebeginDate"  name="hrPerson.beginDate" onclick="changeSysTimeType('pactRelieve','confirmDate','makeDate','checkDate')" onblur="checkQueryDate('pactRelievebeginDate','pactRelieveendDate',0,'pactRelieve_beginDate','pactRelieve_endDate')" style="width:65px"/>
					<label style="float:none;white-space:nowrap" >
						<s:text name="至"/>:
						<input type="text" id="pactRelieveendDate" name="hrPerson.endDate" onclick="changeSysTimeType('pactRelieve','confirmDate','makeDate','checkDate')"  onblur="checkQueryDate('pactRelievebeginDate','pactRelieveendDate',1,'pactRelieve_beginDate','pactRelieve_endDate')" style="width:65px"/>
						<input type="hidden" id="pactRelieve_beginDate" name="hrPerson.endDate"  />
						<input type="hidden" id="pactRelieve_endDate" name="hrPerson.endDate"  />
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('pactRelieve_search_form','pactRelieve_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>	
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive" style="float:right"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('pactRelieve_search_form','pactRelieve_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div id="pactRelieve_buttonBar" class="panelBar">
			<!-- <ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:addPactRelieve()" ><span><s:text name="button.add"/></span></a>
				</li>
				<li>
					<a class="delbutton"  href="javaScript:pactRelieveListEditOper('del')"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a class="changebutton"  href="javaScript:editPactRelieve()"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a class="checkbutton"  href="javaScript:pactRelieveListEditOper('check')"><span><s:text name="button.check" /></span></a>
				</li>
				<li>
					<a class="delallbutton"  href="javaScript:pactRelieveListEditOper('cancelCheck')"><span><s:text name="button.cancelCheck" /></span></a>
				</li>
				<li>
					<a class="confirmbutton"  href="javaScript:pactRelieveListEditOper('confirm')"><span><s:text name="解除" /></span></a>
				</li>
			</ul> -->
		</div>
		<div id="pactRelieve_container">
			<div id="pactRelieve_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
        <div class="treeTopCheckBox">
          <span>
           	显示编码
           <input id="pactRelieve_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrPersonTreeInPactRelieve',showCode:this,disabledDept:jQuery('#pactRelieve_showDisabledDept')[0],disabledPerson:jQuery('#pactRelieve_showDisabledPerson')[0],showCount:true})"/>
         </span>
         <span>
           	显示停用部门
          <input id="pactRelieve_showDisabledDept" type="checkbox" onclick="showDisabledDeptInPactRelieve(this)"/>
         </span>
         <label id="pactRelieve_expandTree" onclick="toggleExpandHrTree(this,'hrPersonTreeInPactRelieve')">展开</label>
        </div>
       <div class="treeTopCheckBox">
          <span>
           	显示停用人员
          <input id="pactRelieve_showDisabledPerson" type="checkbox" onclick="showDisbaledPersonInPactRelieve(this)"/>
          </span>
        </div>
        <div class="treeTopCheckBox">
         <span>
          	 快速查询：
          <input type="text" onKeyUp="searchTree('hrPersonTreeInPactRelieve',this)"/>
         </span>
   		 </div>			
				<div id="hrPersonTreeInPactRelieve" class="ztree"></div>
			</div>
			<div id="pactRelieve_layout-center" class="pane ui-layout-center">
				<div id="pactRelieve_gridtable_div" class="grid-wrapdiv" 
					style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="pactRelieve_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_pactRelieve_gridtable" class='loading ui-state-default ui-state-active'  style="display:none"></div>
		 			<table id="pactRelieve_gridtable"></table>
				</div>
				<div class="panelBar" id="pactRelieve_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="pactRelieve_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="pactRelieve_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
			
					<div id="pactRelieve_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>