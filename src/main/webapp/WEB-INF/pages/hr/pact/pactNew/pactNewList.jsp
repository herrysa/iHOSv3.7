
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var pactNewGridIdString="#pactNew_gridtable";
	jQuery(document).ready(function() {
		jQuery("#actNew_pageHeader").find("select").css("font-size","12px");
		var pactNewFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('pactNew_container','pactNew_gridtable',pactNewFullSize);
		
		hrPersonTreeInpactNew();
		var initFlag = 0;
		var pactNewGrid = jQuery(pactNewGridIdString);
	    pactNewGrid.jqGrid({
	    	url : "pactGridList?filter_EQI_pactState=1&filter_INI_signState=1,2,3",
	    	editurl:"pactGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="pact.id" />',hidden:true,key:true},	
				{name:'code',index:'code',width : '120',align:'left',label : '<s:text name="pact.code" />',hidden:false,highsearch:true},	
				{name:'hrPerson.name',index:'hrPerson.name',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:false,highsearch:true},	
				{name:'hrPerson.personId',index:'hrPerson.personId',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'personSnapCode',index:'personSnapCode',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},	
				{name:'personCurrentSnapCode',index:'personCurrentSnapCode',width : '60',align:'left',label : '<s:text name="pact.hrPerson" />',hidden:true},
				{name:'dept.hrOrg.orgname',index:'dept.hrOrg.orgname',width : '150',align:'left',label : '<s:text name="pact.orgCode" />',hidden:false,highsearch:true},	
				{name:'dept.name',index:'dept.name',width : '100',align:'left',label : '<s:text name="pact.dept" />',hidden:false,highsearch:true},	
				{name:'post.name',index:'post.name',width : '100',align:'left',label : '<s:text name="pact.post" />',hidden:false,highsearch:true},	
				{name:'workContent',index:'workContent',width : '150',align:'left',label : '<s:text name="pact.workContent" />',hidden:false,highsearch:true},		
				{name:'signYear',index:'signYear',width : '60',align:'right',label : '<s:text name="pact.signYear" />',hidden:false,highsearch:true,formatter:'integer'},
				{name:'compSignDate',index:'compSignDate',width : '100',align:'center',label : '<s:text name="pact.compSignDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'beginDate',index:'beginDate',width : '100',align:'center',label : '<s:text name="pact.beginDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
				{name:'endDate',index:'endDate',width : '100',align:'center',label : '<s:text name="pact.endDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
				{name:'probationMonth',index:'probationMonth',width : '80',align:'right',label : '<s:text name="pact.probationMonth" />',hidden:false,highsearch:true,formatter:'integer'},
				{name:'probationBeginDate',index:'probationBeginDate',width : '100',align:'center',label : '<s:text name="pact.probationBeginDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'probationEndDate',index:'probationEndDate',width : '100',align:'center',label : '<s:text name="pact.probationEndDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'path',index:'path',align:'center',label : '<s:text name="pact.path" />',hidden:true},				
				<c:if test="${pactNeedCheck=='1'}">
				{name:'operator.name',index:'operator.name',align:'left',width:'60',label : '<s:text name="pact.operator" />',hidden:false,highsearch:true,},				
				{name:'operateDate',index:'operateDate',align:'center',width:'80',label : '<s:text name="pact.operateDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'checkPerson.name',index:'checkPerson.name',align:'left',width:'60',label : '<s:text name="pact.checkPerson" />',hidden:false,highsearch:true,},				
				{name:'checkDate',index:'checkDate',align:'center',width:'80',label : '<s:text name="pact.checkDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'confirmPerson.name',index:'confirmPerson.name',align:'left',width:'60',label : '<s:text name="pact.confirmPerson" />',hidden:false,highsearch:true,},				
				{name:'confirmDate',index:'confirmDate',align:'center',width:'80',label : '<s:text name="pact.confirmDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				</c:if>
				{name:'signState',index:'signState',width : '60',align:'center',label : '<s:text name="pact.signState" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '1:新建;2:已审核;3:已执行'}},				
				{name:'remark',index:'remark',width : '120',align:'left',label : '<s:text name="pact.remark" />',hidden:false,highsearch:true}
			   ],
	        jsonReader : {
				root : "pacts", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'code',
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
				 gridContainerResize('pactNew','layout');
			 	var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  id=rowIds[i];
	              	  if(ret[i]['signState']=="1"){
	              		  setCellText(this,id,'signState','<span >新建</span>');
	              	  }else if(ret[i]['signState']=="2"){
	              		  setCellText(this,id,'signState','<span style="color:green" >已审核</span>');
	              	  }else if(ret[i]['signState']=="3"){
	              		  setCellText(this,id,'signState','<span style="color:blue" >已执行</span>');
	              	  }
	              	  setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPactRecord(\''+id+'\',\'pact\');">'+ret[i]["code"]+'</a>');
		              	var snapId = ret[i]['hrPerson.personId'] +'_'+ret[i]['personSnapCode'];
		              	if(ret[i]['personCurrentSnapCode']){
		   	          		snapId = ret[i]['hrPerson.personId'] +'_'+ret[i]['personCurrentSnapCode'];
		   	          	}
		              	setCellText(this,id,'hrPerson.name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrPersonRecord(\''+snapId+'\');">'+ret[i]["hrPerson.name"]+'</a>');
	              }
	            }else{
	            	var tw = jQuery("#pactNew_gridtable").outerWidth();
					jQuery("#pactNew_gridtable").parent().width(tw);
					jQuery("#pactNew_gridtable").parent().height(1);
	            }
	           var dataTest = {"id":"pactNew_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   initFlag = initColumn('pactNew_gridtable','com.huge.ihos.hr.pact.model.PactNew',initFlag);
	       	} 
	
	    });
	    jQuery(pactNewGrid).jqGrid('bindKeys');
	    
	    jQuery("#pactNew_search_form_orgCode").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT orgCode id,orgname name,parentOrgCode parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon  FROM v_hr_org_current WHERE disabled = 0 order by orgCode",
			exceptnullparent : false,
			lazy : false,
			minWidth:'200px',
			selectParent:true,
			callback : {
				afterClick : function() {
					jQuery("#pactNew_search_form_dept").val("");
					jQuery("#pactNew_search_form_dept_id").val("");
					jQuery("#pactNew_search_form_post").val("");
					jQuery("#pactNew_search_form_post_id").val("");
					jQuery("#pactNew_search_form_dept").treeselect({
						dataType : "sql",
						optType : "single",
						sql : "SELECT  deptId id,name,parentDept_id parent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon FROM v_hr_department_current where disabled = 0 and orgCode='"+jQuery("#pactNew_search_form_orgCode_id").val()+"' order by deptCode",
						exceptnullparent : false,
						selectParent:true,
						lazy : false,
						minWidth:'200px',
						callback:{
							afterClick:function(){
								jQuery("#pactNew_search_form_post").val("");
								jQuery("#pactNew_search_form_post_id").val("");
								jQuery("#pactNew_search_form_post").treeselect({
									dataType : "sql",
									optType : "single",
									sql : "SELECT  id,name FROM hr_post where disabled = 0 and deptId='"+jQuery("#pactNew_search_form_dept_id").val()+"'",
									lazy : false
								});
							}
						}
					});
				}
			}
		});
	    /*----------------------------toolBar start---------------------------------------------------*/
	    var pactNew_menuButtonArrJson = "${menuButtonArrJson}";
    	var pactNew_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(pactNew_menuButtonArrJson,false)));
    	var pactNew_toolButtonBar = new ToolButtonBar({el:$('#pactNew_buttonBar'),collection:pactNew_toolButtonCollection,attributes:{
    		tableId : 'pactNew_gridtable',
    		baseName : 'pactNew',
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
    	
    	var pactNew_function = new scriptFunction();
    	pactNew_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.checkPeriod){
				if('${yearStarted}'!='true'){
					alertMsg.error("本年度人力资源系统未启用。");
	    			return pass;
				}
			}
			if(param.selectRecord){
				var sid = jQuery("#pactNew_gridtable").jqGrid('getGridParam','selarrrow');
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
						var row = jQuery("#pactNew_gridtable").jqGrid('getRowData',rowId);
						if(param.status=='新建'){
							if(row['signState']!='1'){
								alertMsg.error("只能"+param.opt+"处于  ["+param.status+"] 状态的记录。");
								return pass;
							}
						}else{
							if(row['signState']!='2'){
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
		pactNew_toolButtonBar.addCallBody('1003020101',function(e,$this,param){
			var zTree = $.fn.zTree.getZTreeObj("hrPersonTreeInpactNew");  
		    var nodes = zTree.getSelectedNodes(); 
		    if(nodes.length!=1 || nodes[0].subSysTem!='PERSON'){
		    	alertMsg.error("请选择人员。");
	      		return;
		    }
		    if(nodes[0].actionUrl == '1'){
		    	alertMsg.error("已停用人员不能签订合同。");
	      		return;
		    }
		    $.ajax({
			    url: "checkAddPactForPerson?personId="+nodes[0].id,
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			    },
			    success: function(data){
			        if(data!=null){
			        	alertMsg.error(data.message);
						return;
			        }else{
			        	var url = "editPact?navTabId=pactNew_gridtable&personId="+nodes[0].id;
			    		var winTitle='<s:text name="pactNew.title"/>';
			    		$.pdialog.open(url,'addPactNew',winTitle, {mask:true,width : 680,height : 410,maxable:false,resizable:false});
					}
			    }
			});
		},{});
		pactNew_toolButtonBar.addBeforeCall('1003020101',function(e,$this,param){
			return pactNew_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
		//删除
		pactNew_toolButtonBar.addCallBody('1003020102',function(e,$this,param){
			var url = "${ctx}/pactGridEdit?oper=del"
			var sid = jQuery("#pactNew_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactNew_gridtable";
			alertMsg.confirm("确认删除？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		pactNew_toolButtonBar.addBeforeCall('1003020102',function(e,$this,param){
			return pactNew_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"删除",status:"新建",checkPeriod:"checkPeriod"});
		// 修改
		pactNew_toolButtonBar.addCallBody('1003020103',function(e,$this,param){
			var sid = jQuery("#pactNew_gridtable").jqGrid('getGridParam','selarrrow');
			var winTitle='<s:text name="pactEdit.title"/>';
			var url = "editPact?navTabId=pactNew_gridtable&id="+sid;
			$.pdialog.open(url,'editPactNew',winTitle, {mask:true,width : 620,height : 410,maxable:false,resizable:false});
		},{});
		pactNew_toolButtonBar.addBeforeCall('1003020103',function(e,$this,param){
			return pactNew_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",singleSelect:"单选",opt:"修改",status:"新建",checkPeriod:"checkPeriod"});
		//审核
		pactNew_toolButtonBar.addCallBody('1003020104',function(e,$this,param){
			var url = "${ctx}/pactGridEdit?oper=check"
			var sid = jQuery("#pactNew_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactNew_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认审核？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		pactNew_toolButtonBar.addBeforeCall('1003020104',function(e,$this,param){
			return pactNew_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"审核",status:"新建",checkPeriod:"checkPeriod"});
		//销审
		pactNew_toolButtonBar.addCallBody('1003020105',function(e,$this,param){
			var url = "${ctx}/pactGridEdit?oper=cancelCheck"
			var sid = jQuery("#pactNew_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactNew_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认销审？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		pactNew_toolButtonBar.addBeforeCall('1003020105',function(e,$this,param){
			return pactNew_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"销审",status:"已审核",checkPeriod:"checkPeriod"});
		//撤销
		pactNew_toolButtonBar.addCallBody('1003020106',function(e,$this,param){
			var url = "${ctx}/pactGridEdit?oper=confirm"
			var sid = jQuery("#pactNew_gridtable").jqGrid('getGridParam','selarrrow');
			url = url+"&id="+sid+"&navTabId=pactNew_gridtable";
			url=encodeURI(url);
			alertMsg.confirm("确认执行？", {
				okCall : function() {
					$.post(url,function(data) {
						formCallBack(data);
					});
				}
			});
		},{});
		pactNew_toolButtonBar.addBeforeCall('1003020106',function(e,$this,param){
			return pactNew_function.optBeforeCall(e,$this,param);
    	},{selectRecord:"selectRecord",opt:"执行",status:"已审核",checkPeriod:"checkPeriod"});
		var pactNew_setColShowButton = {id:'1003020188',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
   			callBody:function(){
   				setColShow('pactNew_gridtable','com.huge.ihos.hr.pact.model.PactNew');
   		}};
		pactNew_toolButtonBar.addButton(pactNew_setColShowButton);
    	/*--------------------------------------tooBar end-------------------------------------------*/
  	});
	/*------------------------------tree method area-----------------------------------------*/
	function hrPersonTreeInpactNew() {
		var treeUrl = "makeHrPersonTree?1=1&showPersonType=true";
		//var showDisabledDept = jQuery("#pactNew_showDisabledDept").attr("checked");
		//var showDisabledPerson = jQuery("#pactNew_showDisabledPerson").attr("checked");
		/* if(showDisabledDept){
			treeUrl += "&showDisabledDept=show";
		} */
		/* if(showDisabledPerson){
			treeUrl += "&showDisabledPerson=show";	
		} */
		$.get(treeUrl, {"_" : $.now()}, function(data) {
			var hrPersonTreeData = data.hrPersonTreeNodes;
			var hrPersonTree = $.fn.zTree.init($("#hrPersonTreeInpactNew"),ztreesetting_hrPersonTreeInpactNew, hrPersonTreeData);
			var nodes = hrPersonTree.getNodes();
			hrPersonTree.expandNode(nodes[0], true, false, true);
			hrPersonTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'hrPersonTreeInpactNew',
		         showCode:jQuery('#pactNew_showCode')[0],
		         disabledDept:jQuery("#pactNew_showDisabledDept")[0],
		         disabledPerson: jQuery("#pactNew_showDisabledPerson")[0],
		         showCount:true });
		});
		jQuery("#pactNew_expandTree").text("展开");
	}
	var ztreesetting_hrPersonTreeInpactNew = {
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : false,
				fontCss : setDisabledDeptFontCss
			},
			callback : {
				beforeDrag:function(){return false},
				onClick : function(event, treeId, treeNode, clickFlag){
					var urlString = "pactGridList?filter_EQI_pactState=1&filter_INI_signState=1,2,3";
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
				    var showDisabledDept = jQuery("#pactNew_showDisabledDept").attr("checked");
					var showDisabledPerson = jQuery("#pactNew_showDisabledPerson").attr("checked");
					if(showDisabledDept){
						urlString += "&showDisabledDept=show";
					}
					if(showDisabledPerson){
						urlString += "&showDisabledPerson=show";	
					}
					urlString=encodeURI(urlString);
					jQuery("#pactNew_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
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
	
	function showDisabledDeptInPactNew(obj){
		//var urlString = "pactGridList?filter_EQI_pactState=1&filter_INI_signState=1,2,3";
		var urlString = jQuery("#pactNew_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledDept=show";
		}
		var showDisabledPerson = jQuery("#pactNew_showDisabledPerson").attr("checked");
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=show";	
		}
		//hrPersonTreeInpactNew();
		toggleDisabledOrCount({treeId:'hrPersonTreeInpactNew',
		         showCode:jQuery('#pactNew_showCode')[0],
		         disabledDept:jQuery("#pactNew_showDisabledDept")[0],
		         disabledPerson: jQuery("#pactNew_showDisabledPerson")[0],
		         showCount:true });
		urlString=encodeURI(urlString);
		jQuery("#pactNew_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	function showDisbaledPersonInPactNew(obj){
		//var urlString = "pactGridList?filter_EQI_pactState=1&filter_INI_signState=1,2,3";
		var urlString = jQuery("#pactNew_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		if(obj.checked){
			urlString += "&showDisabledPerson=show";
		}
		var showDisabledDept = jQuery("#pactNew_showDisabledDept").attr("checked");
		if(showDisabledDept){
			urlString += "&showDisabledDept=show";
		}
		//hrPersonTreeInpactNew();
		toggleDisabledOrCount({treeId:'hrPersonTreeInpactNew',
		         showCode:jQuery('#pactNew_showCode')[0],
		         disabledDept:jQuery("#pactNew_showDisabledDept")[0],
		         disabledPerson: jQuery("#pactNew_showDisabledPerson")[0],
		         showCount:true });
		urlString=encodeURI(urlString);
		jQuery("#pactNew_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid"); 
	}
</script>

<div class="page">
	<div class="pageHeader" id="pactNew_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="pactNew_search_form"  style="white-space: break-all;word-wrap:break-word;" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.code'/>:
      					<input type="text" name="filter_LIKES_code"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.hrPerson'/>:
      					<input type="text" name="filter_LIKES_hrPerson.name"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.orgCode'/>:
      					<input type="hidden" name="filter_EQS_dept.orgCode" id="pactNew_search_form_orgCode_id"/>
      					<input type="text" id="pactNew_search_form_orgCode"/>
					</label>
				   <label style="float:none;white-space:nowrap" >
						<s:text name='pact.dept'/>:
      					<input type="hidden" name="filter_EQS_dept.departmentId" id="pactNew_search_form_dept_id"/>
      					<input type="text" id="pactNew_search_form_dept"/>
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.post'/>:
      					<input type="hidden" name="filter_EQS_post.id" id="pactNew_search_form_post_id"/>
      					<input type="text" id="pactNew_search_form_post"/>
					</label>
					<label style="float:none;white-space:nowrap"></label>
						<s:select id="pactNew_searchTime"   list="#{'0':'执行日期','1':'填制日期','2':'审核日期'}" style="width:100px;font-size:9pt;" ></s:select>
						<s:text name=" 从"/>:
						<input type="text" id="pactNewbeginDate"  name="hrPerson.beginDate" onclick="changeSysTimeType('pactNew','confirmDate','operateDate','checkDate')" onblur="checkQueryDate('pactNewbeginDate','pactNewendDate',0,'pactNew_beginDate','pactNew_endDate')" style="width:65px"/>
					<label style="float:none;white-space:nowrap" >
						<s:text name="至"/>:
						<input type="text" id="pactNewendDate" name="hrPerson.endDate" onclick="changeSysTimeType('pactNew','confirmDate','operateDate','checkDate')"  onblur="checkQueryDate('pactNewbeginDate','pactNewendDate',1,'pactNew_beginDate','pactNew_endDate')" style="width:65px"/>
						<input type="hidden" id="pactNew_beginDate" name="hrPerson.endDate"  />
						<input type="hidden" id="pactNew_endDate" name="hrPerson.endDate"  />
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pact.compSignDate'/>:
      					<input type="text" id="pactNew_search_form_compSignDate_from" name="filter_GED_compSignDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'pactNew_search_form_compSignDate_to\')}'})">
						<s:text name='至'/>
					 	<input type="text" id="pactNew_search_form_compSignDate_to" name="filter_LED_compSignDate" style="width:80px;height:15px" class="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'pactNew_search_form_compSignDate_from\')}'})">
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='pactNew.state'/>:
						<s:select name="filter_EQI_signState" list="#{'':'--','1':'新建','2':'已审核','3':'已执行'}" style="width:70px" ></s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('pactNew_search_form','pactNew_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>	
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive" style="float:right"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('pactNew_search_form','pactNew_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div id="pactNew_buttonBar" class="panelBar">
			<!-- <ul class="toolBar">
				<li>
					<a class="addbutton" href="javaScript:addPactNew()" ><span><s:text name="button.add"/></span></a>
				</li>
				<li>
					<a class="delbutton"  href="javaScript:pactNewListEditOper('del')"><span><s:text name="button.delete" /></span></a>
				</li>
				<li>
					<a class="changebutton"  href="javaScript:editPactNew()"><span><s:text name="button.edit" /></span></a>
				</li>
				<li>
					<a class="checkbutton"  href="javaScript:pactNewListEditOper('check')"><span><s:text name="button.check" /></span></a>
				</li>
				<li>
					<a class="delallbutton"  href="javaScript:pactNewListEditOper('cancelCheck')"><span><s:text name="button.cancelCheck" /></span></a>
				</li>
				<li>
					<a class="confirmbutton"  href="javaScript:pactNewListEditOper('confirm')"><span><s:text name="签订" /></span></a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('pactNew_gridtable','com.huge.ihos.hr.pact.model.PactNew')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			</ul> -->
		</div>
		<div id="pactNew_container">
			<div id="pactNew_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
        <div class="treeTopCheckBox">
          <span>
           显示编码
           <input id="pactNew_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrPersonTreeInpactNew',showCode:this,disabledDept:jQuery('#pactNew_showDisabledDept')[0],disabledPerson:jQuery('#pactNew_showDisabledPerson')[0],showCount:true})"/>
         </span>
         <span>
           显示停用部门
          <input id="pactNew_showDisabledDept" type="checkbox" onclick="showDisabledDeptInPactNew(this)"/>
         </span>
         <label id="pactNew_expandTree" onclick="toggleExpandHrTree(this,'hrPersonTreeInpactNew')">展开</label>
        </div>
       <div class="treeTopCheckBox">
          <span>
           显示停用人员
          <input id="pactNew_showDisabledPerson" type="checkbox" onclick="showDisbaledPersonInPactNew(this)"/>
          </span>
        </div>
        <div class="treeTopCheckBox">
         <span>
           快速查询：
          <input type="text" onKeyUp="searchTree('hrPersonTreeInpactNew',this)"/>
         </span>
 	  </div>				
				<div id="hrPersonTreeInpactNew" class="ztree"></div>
			</div>
			<div id="pactNew_layout-center" class="pane ui-layout-center">
				<div id="pactNew_gridtable_div" class="grid-wrapdiv" 
					style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="pactNew_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_pactNew_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
					 <table id="pactNew_gridtable"></table>
				</div>
				<div class="panelBar" id="pactNew_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="pactNew_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="pactNew_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
			
					<div id="pactNew_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
