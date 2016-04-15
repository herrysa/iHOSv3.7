<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script language="javascript" type="text/javascript" src="${ctx}/scripts/DatePicker/WdatePicker.js"></script>
<head>
    <title><fmt:message key="sourcecostList.title"/></title>
    <meta name="heading" content="<fmt:message key='sourcecostList.heading'/>"/>
    <meta name="menu" content="SourcecostMenu"/>
    <script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
		
	<script type="text/javascript">
	 jQuery(function(){
		 var orgPriv = "${fns:u_readDPSql('org_dp')}";
		 var branchPriv = "${fns:u_readDPSql('branch_dp')}";
		 var orgPrivSql = "and orgCode in "+orgPriv;
			if(orgPriv.indexOf("select") != -1 || orgPriv.indexOf("SELECT") != -1){
				orgPrivSql = "";
			}
			var branchPrivSql = " and branchCode in "+branchPriv;
			if(${herpType=="S1"}||branchPriv.indexOf("select") != -1 || branchPriv.indexOf("SELECT") != -1){
				branchPrivSql = "";
			}
			var extra1Str = " leaf=true and departmentId !='XT' "+orgPrivSql+branchPrivSql+" and ( ";
	jQuery("#departmentIdC").autocomplete("autocomplete", {
			width: 300,
			multiple: false,
			autoFill: false,
			matchContains: true,
			matchCase: true,
			dataType: 'json',
			parse: function(test) {
				//alert(test.dicList.length)
				var data = test.autocompleteList;
			    if (data == null || data=="") {
			    	var rows = [];
	                rows[0] = {
	                     data:"没有结果",
	                     value:"",
	                     result:""
	                   };
	                return rows;
			    } else {
	         		var rows = [];
	         		for(var i=0; i<data.length; i++){
	         			rows[rows.length] = {
	            			data:data[i].departmentId+","
	            			<c:if test="${herpType == 'M'}" >
								+ data[i].org.orgname + ","
							</c:if>
							<c:if test="${herpType == 'S2'}" >
								+ data[i].branchName + ","
							</c:if>
		            			+data[i].deptCode+","
		            			+data[i].cnCode+","
		            			+data[i].name+":"
		            			+data[i].departmentId,
	           		  		value:data[i].name,
	              			result:data[i].name
	            		};
	        		}
	        		return rows;
	        	}
	    	},extraParams: {
	    		flag:2,
    		  	entity:"Department",
    		  	cloumnsStr:"departmentId,deptCode,name,cnCode",
    		  	extra1:extra1Str,
   				extra2:")"
    		},formatItem: function(row){
			    return dropId(row);
			},formatResult: function(row){
			    return dropId(row);
			}
		});
	jQuery("#departmentIdC").result(function(event, row, formatted) {
	        if (row == "没有结果") {
	            return;
	        }
	        jQuery("#deptId").attr("value", getId(row));
	        //alert(jQuery("#zxDeptId").attr("value"));
		});
    }) 
	 jQuery(function(){
	jQuery("#departmentTypeIdC").autocomplete("autocomplete", {
			width: 300,
			multiple: false,
			autoFill: false,
			matchContains: true,
			matchCase: true,
			dataType: 'json',
			parse: function(test) {
				//alert(test.dicList.length)
				var data = test.autocompleteList;
			    if (data == null || data=="") {
			    	var rows = [];
	                rows[0] = {
	                     data:"没有结果",
	                     value:"",
	                     result:""
	                   };
	                return rows;
			    } else {
	         		var rows = [];
	         		for(var i=0; i<data.length; i++){
	         			rows[rows.length] = {
	            			data:data[i].deptTypeId+","+data[i].deptTypeName+":"+data[i].deptTypeId,
	           		  		value:data[i].deptTypeName,
	              			result:data[i].deptTypeName
	            		};
	        		}
	        		return rows;
	        	}
	    	},extraParams: {
	    		flag:2,
    		  	entity:"DeptType",
    		  	cloumnsStr:"deptTypeId,deptTypeName",
    		  	extra1:" disabled=false and (",
   				extra2:")"
    		},formatItem: function(row){
			    return dropId(row);
			},formatResult: function(row){
			    return dropId(row);
			}
		});
	jQuery("#departmentTypeIdC").result(function(event, row, formatted) {
	        if (row == "没有结果") {
	            return;
	        }
	        jQuery("#deptTypeId").attr("value", getId(row));
	        //alert(jQuery("#zxDeptId").attr("value"));
		});
    }) 
jQuery(function(){
	jQuery("#costItemIdC").autocomplete("autocomplete", {
			width: 300,
			multiple: false,
			autoFill: false,
			matchContains: true,
			matchCase: true,
			dataType: 'json',
			parse: function(test) {
				//alert(test.dicList.length)
				var data = test.autocompleteList;
			    if (data == null || data=="") {
			    	var rows = [];
	                rows[0] = {
	                     data:"没有结果",
	                     value:"",
	                     result:""
	                   };
	                return rows;
			    } else {
	         		var rows = [];
	         		for(var i=0; i<data.length; i++){
	         			rows[rows.length] = {
	            			data:data[i].costItemId+","+data[i].costItemName+","+data[i].cnCode+":"+data[i].costItemId,
	           		  		value:data[i].costItemName,
	              			result:data[i].costItemName
	            		};
	        		}
	        		return rows;
	        	}
	    	},extraParams: {
	    		flag:2,
    		  	entity:"CostItem",
    		  	cloumnsStr:"costItemId,costItemName,cnCode",
    		  	extra1:" disabled=false and (",
   				extra2:")"
    		},formatItem: function(row){
			    return dropId(row);
			},formatResult: function(row){
			    return dropId(row);
			}
		});
	jQuery("#costItemIdC").result(function(event, row, formatted) {
	        if (row == "没有结果") {
	            return;
	        }
	        jQuery("#costItemId").attr("value", getId(row));
	        //alert(jQuery("#costItemId").attr("value"));
		}); 
    })
    jQuery(function() {
    	/*----------------------------------tooBar start-----------------------------------------------*/
     	var sourcecost_menuButtonArrJson = "${menuButtonArrJson}";
		var sourcecost_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(sourcecost_menuButtonArrJson,false)));
		var sourcecost_toolButtonBar = new ToolButtonBar({el:$('#sourcecost_buttonBar'),collection:sourcecost_toolButtonCollection,attributes:{
			tableId : 'sourcecost_gridtable',
			baseName : 'sourcecost',
			width : 800,
			height : 550,
			base_URL : null,
			optId : 'sourceCostId',
			fatherGrid : null,
			extraParam : null,
			selectNone : "请选择记录。",
			selectMore : "只能选择一条记录。",
			addTitle : '<s:text name="sourcecostNew.title"/>',
			editTitle : '<s:text name="sourcecostEdit.title"/>'
		}}).render();
		
		var sourcecost_function = new scriptFunction();
		sourcecost_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.selectRecord){
				var sid = jQuery("#sourcecost_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
		        	alertMsg.error("请选择记录！");
					return pass;
				}
		        if(param.singleSelect){
		        	if(sid.length != 1){
			        	alertMsg.error("只能选择一条记录！");
						return pass;
					}
		        }
			}
	        return true;
		};
		//添加
		sourcecost_toolButtonBar.addFunctionAdd('01020201');
		//删除
		sourcecost_toolButtonBar.addCallBody('01020202',function(){
			var sid = jQuery("#sourcecost_gridtable").jqGrid('getGridParam',
			'selarrrow');
			var editUrl = jQuery("#sourcecost_gridtable").jqGrid('getGridParam', 'editurl');
			if (sid == null || sid.length == 0) {
				//alert("<fmt:message key='list.selectNone'/>");
				alertMsg.error("<fmt:message key='list.selectNone'/>");
				return;
			}else {
				for(var s=0;s<sid.length;s++){
					var row = jQuery("#sourcecost_gridtable").jqGrid('getRowData',sid[s]);
					var selectOper = row['operator.personId'];
					var currentPerson = '${currentUser.person.personId}';
					if(selectOper===currentPerson||selectOper===""){
						alertMsg.confirm("确认删除？", {
							okCall: function(){
								jQuery.post(editUrl+"?id=" + sid+"&navTabId=sourcecost_gridtable&oper=del", {
								}, formCallBack, "json");
							}
						});
					}else{
						alertMsg.error("您没有操作权限！");
						return;
					}
				}
			}
		},{});
		sourcecost_toolButtonBar.addBeforeCall('01020202',function(e,$this,param){
			return sourcecost_function.optBeforeCall(e,$this,param);
		},{selectRecord:'selectRecord'});
		//修改
		sourcecost_toolButtonBar.addCallBody('01020203',function() {
			var sid = jQuery("#sourcecost_gridtable").jqGrid('getGridParam',
			'selarrrow');
			if (sid == null || sid.length == 0) {
				//alert("<fmt:message key='list.selectNone'/>");
				alertMsg.error("<fmt:message key='list.selectNone'/>");
				return;
			}
			if (sid.length > 1) {
				//alert("<fmt:message key='list.selectMore'/>");
				alertMsg.error("<fmt:message key='list.selectMore'/>");
				return;
			} else {
				var row = jQuery("#sourcecost_gridtable").jqGrid('getRowData',sid);
				var selectOper = row['operator.personId'];
				var currentPerson = '${currentUser.person.personId}';
				if(selectOper===currentPerson||selectOper===""){
					var url = "editSourcecost?popup=true&sourceCostId=" + sid+"&navTabId=sourcecost_gridtable";
					var winTitle = '<fmt:message key="sourcecostEdit.title"/>';
					$.pdialog.open(url, 'editSourcecost', winTitle, {maxable:false,mask:false,width:800,height:550});　
				}else{
					alertMsg.error("您没有操作权限！");
				}
				
			}
		},{});
		sourcecost_toolButtonBar.addBeforeCall('01020203',function(e,$this,param){
			return sourcecost_function.optBeforeCall(e,$this,param);
		},{selectRecord:'selectRecord',singleSelect:'singleSelect'});
		//导出本页数据
		sourcecost_toolButtonBar.addCallBody('01020204',function() {
			//exportToExcel('sourcecost_gridtable','Sourcecost','成本数据','page');
			exportToExcelByAction('sourcecost_gridtable','Sourcecost','成本数据','page');
		},{});
		//导出当前全部数据
		sourcecost_toolButtonBar.addCallBody('01020205',function() {
			//exportToExcel('sourcecost_gridtable','Sourcecost','成本数据','all');
			exportToExcelByAction('sourcecost_gridtable','Sourcecost','成本数据','all');
		},{});
		//设置表格列
		var sourcecost_setColShowButton = {id:'01020220',buttonLabel:'设置表格列',className:"settlebutton",show:true,enable:true,
				callBody : function() {
					setColShow('sourcecost_gridtable','com.huge.ihos.inout.model.Sourcecost');
				}};
		sourcecost_toolButtonBar.addButton(sourcecost_setColShowButton);
    	/*----------------------------------tooBar end -----------------------------------------------*/
    	var herpType = "${fns:getHerpType()}";
    	if(herpType == "M") {
    		//单位树
    		var orgNumber = "${orgNumber}";
    		if(orgNumber == 1) {
    			jQuery("#sourcecost_costOrg_label").hide();
    		}
        	var orgPriv = "${fns:u_readDP('org_dp')}";
			//var orgPrivArr = orgPriv.split(",");
			var sql = "select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT'";
			 if(orgPriv) {
				sql += " and orgCode in ${fns:u_readDPSql('org_dp')} ";
			} else {
				sql += " and 1=2";
			}
			sql += " ORDER BY orderCol"; 
        	jQuery("#sourcecost_costOrg").treeselect({
        		optType : "multi",
    			dataType : 'sql',
    			sql : sql,
    			exceptnullparent : true,
    			lazy : false,
    			minWidth : '180px',
    			selectParent : false
        	});
        	/* if(orgPrivArr.length == 1 && orgPrivArr[0] != "") {
				jQuery("#sourcecost_costOrg").attr("readonly","readonly");
				jQuery("#sourcecost_costOrg").unbind('click');
			} */
    	} else {
    		jQuery("#sourcecost_costOrg_label").hide();
    	}
    	function exportToExcelByAction(gridId,entityName,title,outPutType){
    		var url = jQuery("#"+gridId).jqGrid('getGridParam','url');
    		var formData = jQuery("#"+gridId).jqGrid('getGridParam','formData');
    		//var param = url.split("?")[1];
    		//alert(json2str(jQuery("#sourcepayin_gridtable")[0].p.colModel));
    		//return ;
    		var colModel = jQuery("#"+gridId).jqGrid('getGridParam','colModel');
    		var colDefine = new Array();
    		var colDefineIndex = 0;
    		for(var mi=0;mi<colModel.length;mi++){
    			var col = colModel[mi];
    			if(col.name!="rn"&&col.name!="cb"&&!col.hidden){
    				var label = col.label?col.label:col.name;
    				var type = col.formatter?col.formatter:1;
    				var align = col.align?col.align:"left";
    				var width = col.width?parseInt(col.width)*20:50*20;
    				colDefine[colDefineIndex] = {name:col.name,type:type,align:align,width:width,label:label};
    				colDefineIndex++;
    			}
    		}
    		var colDefineStr = json2str(colDefine);
    		var page=1,pageSize=20,sortname,sortorder;
    		page = jQuery("#"+gridId).jqGrid('getGridParam','page');
    		pageSize = jQuery("#"+gridId).jqGrid('getGridParam','rowNum');
    		if(outPutType=='all'){
    			pageSize = 100000;
    			page = 1;
    		}
    		sortname = jQuery("#"+gridId).jqGrid('getGridParam','sortname');
    		sortorder = jQuery("#"+gridId).jqGrid('getGridParam','sortorder');
    		var searchParam = "&rows="+pageSize+"&page="+page+"&sidx="+sortname+"&sord="+sortorder;
    		url += searchParam;
    		//var u =  'outPutExcel?'+param+"&entityName="+entityName;
    		$.ajax({
    			url: url,
    			type: 'post',
    			data:{outputExcel:true,title:title,colDefineStr:colDefineStr},
    			dataType: 'json',
    			async:false,
    			error: function(data){
    				alertMsg.error("系统错误！");
    			},
    			success: function(data){
    				var downLoadFileName = data["userdata"]["excelFullPath"];
    				var filePathAndName = downLoadFileName.split("@@");
    				var url = "${ctx}/downLoadExel?filePath="+filePathAndName[0]+"&fileName="+filePathAndName[1];
    		 		//url=encodeURI(url);
    		 		location.href=url;
    			}
    		}); 
    	}
    });
    
</script>
    <script type="text/javascript">
    function refreshGridCurrentPage(){
    	var jq = jQuery('#sourcecost_gridtable');
        var currentPage = jq.jqGrid('getGridParam', 'page');  
    	jQuery('#sourcecost_gridtable').trigger('reloadGrid', [{page: currentPage }]);
    }
    	function addRecord(){
			var url = "editSourcecost?popup=true";
			var winTitle='<fmt:message key="sourcecostNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#sourcecost_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
				//alert("<fmt:message key='list.selectNone'/>");
				jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectNone'/>");
				jQuery('#mybuttondialog').dialog('open');
				return;
				}
	        if(sid.length>1){
				  //alert("<fmt:message key='list.selectMore'/>");
			  jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectMore'/>");
			  jQuery('#mybuttondialog').dialog('open');
				return;
			  }else{
	         jQuery("#gridinfo").html('<p>Loading..... ID : '+sid+'</p>');
				var url = "editSourcecost?popup=true&sourceCostId=" + sid;
				var winTitle='<fmt:message key="sourcecostNew.title"/>';
				popUpWindow(url, winTitle, "width=500");
			}
		}
	    function okButton(){
	    	 jQuery('#mybuttondialog').dialog('close');
	    };		
		datePick = function(elem){
		        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
		        jQuery('#ui-datepicker-div').css("z-index", 2000);
		};
		function sourcecostGridReload(){
			var urlString = "sourcecostGridList?1=1";
			var herpType = "${fns:getHerpType()}";
			if(herpType == "S2") {
				var cbBranchDP = "${fns:u_readDPSql('cbdata_branch')}";
				var operatorId = "${fns:userContextParam('loginPersonId')}";
				var sqlString = "sourceCostId IN (SELECT t.sourceCostId FROM t_sourcecost t WHERE t.operatorId = '" + operatorId +"'";
					sqlString += " OR t.cbBranchCode in " + cbBranchDP;
					sqlString += " )";
				urlString += "&filter_SQS_sourceCostId="+sqlString;
			}
			var fdc1 = jQuery("#fdc1").val();
			var fdc2 = jQuery("#fdc2").val();
			var costItemId = jQuery("#costItemId").val();
			var deptId = jQuery("#deptId").val();
			var amountC = jQuery("#amountC").val();
			var amountC2 = jQuery("#amountC2").val();
			var freemarkC = jQuery("#freemarkC").val();
			var paytypeId = jQuery("#paytypeId").val();
			var deptTypeId = jQuery("#deptTypeId").val();
			var remarksC = jQuery("#remarksC").val();
			var operatorNameC = jQuery("#operatorNameC").val();
			var SourceCostIsManual = jQuery("#SourceCostIsManual").val();
			var costOrg = jQuery("#sourcecost_costOrg_id").val();
			var cbBranch = jQuery("#sourcecost_cbBranch").val();
			urlString=urlString+"&filter_GES_checkPeriod="+fdc1+"&filter_LES_checkPeriod="+fdc2+"&filter_EQS_costItemId.costItemId="+costItemId+"&filter_EQS_deptartment.departmentId="+deptId+"&filter_INS_costOrg.orgCode=" + costOrg+"&filter_EQS_cbBranch.branchCode="+cbBranch+"&filter_GEG_amount="+amountC+"&filter_LEG_amount="+amountC2+"&filter_LIKES_freemark="+freemarkC+"&filter_LIKES_remarks="+remarksC+"&filter_EQS_operator.name="+operatorNameC+"&filter_EQB_manual="+SourceCostIsManual+"&paytypeId="+paytypeId;
			//alert(urlString);
			urlString=encodeURI(urlString);
			jQuery("#sourcecost_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		}
		function selectMonthToInput(yearID, monthID, year_monthID) {

			  if (year_monthID.value!='' && (yearID.options[yearID.selectedIndex].value=='' || monthID.options[monthID.selectedIndex].value=='')) {
			    yearID.options[0].selected=true;
			    monthID.options[0].selected=true;
			    year_monthID.value='';
			    return false;
			  }

			  if (year_monthID.value=='' && yearID.options[yearID.selectedIndex].value=='') {
			    yearID.options[1].selected=true;
			  }

			  if (year_monthID.value=='' && monthID.options[monthID.selectedIndex].value=='') {
			    monthID.options[1].selected=true;
			  }

			  year_monthID.value=yearID.value+monthID.value;
			 // alert(year_monthID.value);
			}
		jQuery(document).ready(function() { 
			
		/* 	var s2=jQuery("#departmentIdC");
			 if(s2.attr("value")==null||s2.attr("value")=="")
				 s2.attr("value","拼音/汉字/编码");
			  */
    	});
		/* function f(s){
			if(s==null||s==""){
				jQuery("#costItemId").attr("");
			}
		} */
		
		//var sourcecostLayout;
		jQuery(document).ready(function() { 
			/* sourcecostLayout = makeLayout({
				baseName: 'sourcecost', 
				tableIds: 'sourcecost_gridtable'
			}, null);
			sourcecostLayout.resizeAll(); */
			
			initSourceCostGrid();
			var amountSum = "${amountSum}";
			jQuery("#sourcecostAllSum").text(parseFloat(amountSum).toFixed(2));
			/* jQuery.subscribe('onGridComplete', function(event, data) {
				
				
			});
			 */
			/* jQuery.subscribe('chargeValidate', function(event, data) {
				var s = jQuery("#sourcecost_gridtable").jqGrid('getGridParam','selarrrow');
				for(var si=0;si<s.length;si++){
					var row = jQuery("#sourcecost_gridtable").jqGrid('getRowData',s[si]);
					var selectOper = row['operator.personId'];
					var checkPeriod = row['checkPeriod'];
					
					var priviliage = checkPriviliage(checkPeriod,selectOper);
					if(!priviliage){
						jQuery("#sourcecost_gridtable").jqGrid('setSelection',s[si]);
					}
				}
				
			}); */
    	});
		
		
		/* var currentPerson = '${currentUser.person.personId}';
		var currentCheckPeriod = '${currentPeriod}';
		var ii=0;
		function checkPriviliage(checkPeriod,person){
			if((currentCheckPeriod === checkPeriod)&&(person===currentPerson||person==="")){
				return true;
			}else{
				return false;
			}
		} */
		function onSourceCostGridComplete(){
			var sumDataJson = jQuery("#sourcecost_gridtable").getGridParam('userData'); 
			var sumDataStr = sumDataJson.amount;
			jQuery("#sourcecostAllSum").text(formatNum(parseFloat(sumDataStr).toFixed(2)));
			var amount = jQuery("#sourcecost_gridtable").getCol('amount',false,'sum');
			jQuery("#sourcecostPageSum").text(formatNum(amount.toFixed(2)));
			jQuery("#sourcecost_gridtable").find("tr").each(function(){
				var checkPeriod= jQuery(this).find("td").eq(3).text();
				var person= jQuery(this).find("td").eq(jQuery(this).find("td").length-5).html();
				if(person==""){
					person = "";
				}
				var priviliage = checkPriviliage(checkPeriod,person);
				if(!priviliage){
					jQuery(this).find("td").each(function(){
						if(jQuery(this).children().eq(0).attr("type")=="checkbox"){
							jQuery(this).children().eq(0).attr("disabled","true");
							/* jQuery(this).parent().bind("click",function(){
								alert(jQuery(this).html());
								jQuery(this).removeClass("ui-widget-content jqgrow ui-row-ltr ui-state-highlight");
								jQuery(this).addClass("ui-widget-content jqgrow ui-row-ltr");
							}); */
						}
				});
				}
			});
		}
		function initSourceCostGrid(){
			var initSourcecostFlag = 0;
			var sourceCostGrid = jQuery("#sourcecost_gridtable");
			var urlString = "sourcecostGridList?filter_GES_checkPeriod=${currentPeriod}&filter_LES_checkPeriod=${currentPeriod}";
			var herpType = "${fns:getHerpType()}";
			if(herpType == "S2") {
				var cbBranchDP = "${fns:u_readDPSql('cbdata_branch')}";
				var operatorId = "${fns:userContextParam('loginPersonId')}";
				var sqlString = "sourceCostId IN (SELECT t.sourceCostId FROM t_sourcecost t WHERE t.operatorId = '" + operatorId +"'";
					sqlString += " OR t.cbBranchCode in " + cbBranchDP;
					sqlString += " )";
				urlString += "&filter_SQS_sourceCostId="+sqlString;
			}
			sourceCostGrid.jqGrid({
		    	url : urlString,
		    	editurl:"sourcecostGridEdit",
				datatype : "json",
				mtype : "GET",
		        colModel:[
					{name:'sourceCostId',index:'sourceCostId',align:'center',label : '<s:text name="sourcecost.sourceCostId" />',hidden:true,key:true},				
					{name:'checkPeriod',index:'checkPeriod',align:'left',width:"100",label : '<s:text name="sourcecost.checkPeriod" />',hidden:false,highsearch:true},			
					{name:'remarks',index:'remarks',align:'left',width:"200",label : '<s:text name="sourcecost.remarks" />',hidden:false,highsearch:true},				
					{name:'deptartment.name',index:'deptartment.name',align:'left',width:"120",label : '<s:text name="department.name" />',hidden:false,highsearch:true},				
					<c:if test="${herpType == 'M'}">
					{name:'costOrg.shortName',index:'costOrg.shortName',align:'left',width:"130",label : '<s:text name="sourcecost.costOrg" /><s:text name="hisOrg.orgName" />',hidden:false,highsearch:true},				
					</c:if>
					<c:if test="${herpType == 'S2'}">
					{name:'cbBranch.branchName',index:'cbBranch.branchName',align:'left',width:"130",label : '<s:text name="sourcecost.costOrg" /><s:text name="hisOrg.branchName" />',hidden:false,highsearch:true},				
					</c:if>
					{name:'costItemId.costItemName',index:'costItemId.costItemName',align:'left',width:"120",label : '<s:text name="costItem.costItemName" />',hidden:false,highsearch:true},			
					{name:'amount',index:'amount',align:'right',width:"120",label : '<s:text name="sourcecost.amount" />',hidden:false,highsearch:true,formatter:"currency",formatoptions:{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 2, prefix: '', suffix:'', defaultValue: '0.00'}},			
					{name:'processDate',index:'processDate',align:'center',width:"120",label : '<s:text name="sourcecost.processDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat: 'Y-m-d'}},			
					{name:'operator.name',index:'operator.name',align:'left',width:"60",label : '<s:text name="sourcecost.operator.name" />',hidden:false,highsearch:true},			
					{name:'operator.personId',index:'operator.personId',align:'left',width:"12",label : '<s:text name="sourcecost.operator.personId" />',hidden:true},			
					{name:'manual',index:'manual',align:'center',width:"50",label : '<s:text name="sourcecost.manual" />',hidden:false,highsearch:true,formatter:"checkbox",edittype:"checkbox",editrules:{required: true}},			
					{name:'costItemId.costItemId',index:'costItemId.costItemId',align:'left',width:"120",label : '<s:text name="costItem.costItemId" />',hidden:false,highsearch:true},			
					{name:'status',index:'status',align:'left',width:"60",label : '<s:text name="sourcepayin.status" />',formatter:'select',editoptions:{value:"0:新建;1:已结帐"},hidden:true,highsearch:true},		
					{name:'freemark',index:'freemark',align:'left',width:"100",label : '<s:text name="sourcecost.freemark" />',hidden:false,highsearch:true}
					],
		        jsonReader : {
					root : "sourcecosts", // (2)
					page : "page",
					total : "total",
					records : "records", // (3)
					repeatitems : false
				},
		        sortname: 'sourceCostId',
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
					gridContainerResize("sourcecost","div");
					var rowNum = jQuery(this).getDataIDs().length;
					 if(rowNum <= 0) {
						 var tw = jQuery("#sourcecost_gridtable").outerWidth();
						 jQuery("#sourcecost_gridtable").parent().width(tw);
						 jQuery("#sourcecost_gridtable").parent().height(1);

					 }
					onSourceCostGridComplete();
		           var dataTest = {"id":"sourcecost_gridtable"};
		      	   jQuery.publish("onLoadSelect",dataTest,null);
		      	   makepager("sourcecost_gridtable");
		      	 initSourcecostFlag = initColumn('sourcecost_gridtable','com.huge.ihos.inout.model.Sourcecost',initSourcecostFlag);
		       	} 
		    });
		    jQuery(sourceCostGrid).jqGrid('bindKeys');
		}
	</script>
</head>

<div class="page" id="sourcecost_page">
<!-- <div id="sourcecost_container">
			<div id="sourcecost_layout-center"
				class="pane ui-layout-center"> -->

	<div id="sourcecost_pageHeader" class="pageHeader">
		<form onsubmit="return navTabSearch(this);" action="userGridList"
			method="post" class="queryarea-form">
			<div class="searchBar">
				<div class="searchContent">
						<label class="queryarea-label"><fmt:message key='sourcecost.checkPeriod'/>：从<input size="8" class="input-mini" type="text" id="fdc1" onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})" value="${currentPeriod}"/>  
	到<input  class="input-mini" type="text" id="fdc2" size="8" onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})" value="${currentPeriod}"/>
	
	<s:hidden id="deptId"/>
						</label>
						
						<label class="queryarea-label"><fmt:message key="sourcecost.deptartmentName"></fmt:message>：
							<input type="text" class="defaultValueStyle"  id="departmentIdC" maxlength="30" value="拼音/汉字/编码" size=15 onfocus="clearInput(this,jQuery('#deptId'))"  onblur="setDefaultValue(this,jQuery('#deptId'))" onkeydown="setTextColor(this)"/>
							<s:hidden key="sourcecost.costItemId.costItemId" id="costItemId"/>
						</label>
						
						<label class="queryarea-label"><fmt:message key="costItem.costItemName"></fmt:message>：
							<input type="text" class="defaultValueStyle" id="costItemIdC" maxlength="30"   value="拼音/汉字/编码" size=15 onfocus="clearInput(this,jQuery('#costItemId'))"  onblur="setDefaultValue(this,jQuery('#costItemId'))" onkeydown="setTextColor(this)"/>
						</label>
						
						<label class="queryarea-label"><fmt:message key='sourcecost.paytype'/>：
							<appfuse:dictionary code="paytype" name="paytypeId" cssClass="input-small"></appfuse:dictionary>
						</label>
						
						<label class="queryarea-label">
						<fmt:message key="sourcecost.remarks"></fmt:message>：
							<input type="text" id="remarksC" class="input-medium" size=15/>
						</label>
						
						<label class="queryarea-label"><fmt:message key="sourcecost.amount"></fmt:message>：
							<input type="text" id="amountC" class="input-mini" size="8"/>-<input type="text" size="8" id="amountC2" class="input-mini" />
						</label>
						
						<label class="queryarea-label"><fmt:message key="sourcecost.freemark"></fmt:message>：
							<input type="text" id="freemarkC" size=15 class="input-medium" />
						</label>
						
						<label class="queryarea-label"><fmt:message key="sourcecost.operator.name"></fmt:message>：
							<input type="text" id="operatorNameC" class="input-medium" size="10"/>
						</label>
						
						<label class="queryarea-label">	
							<fmt:message key="sourcecost.manual"></fmt:message>:
							<s:select id="SourceCostIsManual" style="width:50px;" list='#{"true":"是","false":"否"}' emptyOption="true"/>
						</label>
						<label class="queryarea-label" id="sourcecost_costOrg_label">
							<fmt:message key="sourcecost.costOrg"></fmt:message><fmt:message key="hisOrg.orgName"></fmt:message>:
							<input type="text" id="sourcecost_costOrg" />
							<input type="hidden" id="sourcecost_costOrg_id"/>
						</label>
						<label class="queryarea-label" style="${herpType=='S2'?'':'display:none'}">
							<fmt:message key="sourcecost.costOrg"></fmt:message><fmt:message key="hisOrg.branchName"></fmt:message>:
							<s:select list="branches" headerKey="" headerValue="--" listKey="branchCode" listValue="branchName" id="sourcecost_cbBranch" ></s:select>
							<!-- <input type="text" id="sourcecost_cbBranch" />
							<input type="hidden" id="sourcecost_cbBranch_id"/> -->
						</label>
						<div class="buttonActive" style="float:right">
								<div class="buttonContent">
									<button type="button" onclick="sourcecostGridReload()"><s:text name='button.search'/></button>
								</div>
						
						</div>
				</div>
				<%-- <div class="subBar">
								<ul>
									<li>
									<div class="buttonActive">
									<div class="buttonContent">
									<button type="button" onclick="sourcecostGridReload()"><s:text name='button.search'/></button>
									</div>
									</div>
									</li>
								</ul>
				</div> --%>
				<%-- </td><td valign="bottom"><ul>
										<li><div class="buttonActive">
												<div class="buttonContent">
													<button type="button" onclick="sourcecostGridReload()">
														<fmt:message key='button.search' />
													</button>
												</div>
											</div></li>
									</ul></td></tr></table> --%>
				<!-- <div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="gridReload()">检索</button>
								</div>
							</div></li>
						<li><a class="button" href="demo_page6.html" target="dialog"
							rel="dlg_page1" title="查询框"><span>高级检索</span>
						</a>
						</li>
					</ul>
				</div> -->
			</div>
		</form>
	</div>
	<div class="pageContent">


 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="sourcecostGridEdit"/> 
<s:url id="remoteurl" action="sourcecostGridList" escapeAmp="false">
	<s:param name="filter_GES_checkPeriod" value="%{currentPeriod}"></s:param>
	<s:param name="filter_LES_checkPeriod" value="%{currentPeriod}"></s:param>
</s:url> 

<div class="panelBar" id="sourcecost_buttonBar">
			<%-- <ul class="toolBar">
				<li><a id="sourcecost_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="sourcecost_gridtable_del" class="delbutton"  href="javaScript:"><span>删除</span>
				</a>
				</li>
				<li><a id="sourcecost_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><fmt:message key="button.edit" />
					</span>
				</a>
				</li>
				<li><a class="excelbutton" href="javaScript:exportToExcel('sourcecost_gridtable','Sourcecost','成本数据','page')"><span>导出本页数据 </span> </a>
				</li>
				<li><a class="excelbutton" href="javaScript:exportToExcel('sourcecost_gridtable','Sourcecost','成本数据','all')"><span>导出当前全部数据 </span> </a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('sourcecost_gridtable','ccom.huge.ihos.inout.model.Sourcecost')"><span><s:text name="button.setColShow" /></span></a>
				</li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul> --%>
		</div>
		<div align="right"  style="margin-top: -20px;margin-right: 5px">
				本页金额合计：<label id="sourcecostPageSum"></label>总计：<label id="sourcecostAllSum" ></label>
				</div>
		<div id="sourcecost_gridtable_div"
			style="margin-left: 2px; margin-top: 10px" class="grid-wrapdiv"
			buttonBar="base_URL:editSourcecost;optId:sourceCostId;width:800;height:550">
			<input type="hidden" id="sourcecost_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="sourcecost_gridtable_addTile">
				<fmt:message key="sourcecostNew.title"/>
			</label> 
			<label style="display: none"
				id="sourcecost_gridtable_editTile">
				<fmt:message key="sourcecostEdit.title"/>
			</label>
			<label style="display: none"
				id="sourcecost_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="sourcecost_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
			<div id="load_sourcecost_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
			<table id="sourcecost_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="sourcecost_pageBar">
		<div class="pages">
			<span>显示</span> <select id="sourcecost_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="sourcecost_gridtable_totals"></label>条</span>
		</div>

		<div id="sourcecost_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>