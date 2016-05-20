<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
    <title><fmt:message key="personList.title"/></title>
    <meta name="heading" content="<fmt:message key='personList.heading'/>"/>
    <meta name="menu" content="PersonMenu"/>
    
    <script type="text/javascript">
    	var showIds = null;
		function personGridReload(){
			propertyFilterSearch('person_search_form','person_gridtable');
			personTreeReShow();
		}
		function personTreeReShow(){
			var postData = jQuery("#person_gridtable").jqGrid('getGridParam',"postData");
			var urlString = 'personGridList?1=1';
			var isShowAll=true;
			jQuery.each(postData, function(key, val) {
				if(val){
				switch(key){
					case 'filter_LIKES_name':
						urlString+="&filter_LIKES_name="+val;
						isShowAll=false;
						break;
					case 'filter_EQS_postType':
						urlString+="&filter_EQS_postType="+val;
						isShowAll=false;
						break;
					case 'filter_EQS_status':
						urlString+="&filter_EQS_status="+val;
						isShowAll=false;
						break;
					case 'filter_EQS_department.departmentId':
						urlString+="&filter_EQS_department.departmentId="+val;
						isShowAll=false;
						break;
					case 'filter_LIKES_personId':
						urlString+="&filter_LIKES_personId="+val;
						isShowAll=false;
						break;
					case 'filter_LIKES_personCode':
						urlString+="&filter_LIKES_personCode="+val;
						isShowAll=false;
						break;
					case 'filter_EQB_disable':
						urlString+="&filter_EQB_disable="+val;
						isShowAll=false;
						break;
					case 'filter_EQB_jjmark':
						urlString+="&filter_EQB_jjmark="+val;
						isShowAll=false;
						break;
				}    
				}
		 　　}); 
			urlString = urlString.replace('showDisabledDept','');
			urlString = urlString.replace('showDisabledPerson','');
			var showDisabledDept = jQuery("#person_showDisabled").attr("checked");
			var showDisabledPerson = jQuery("#person_showDisabledPerson").attr("checked");
			if(showDisabledDept){
				urlString += "&showDisabledDept=true";
			}
			if(showDisabledPerson){
				urlString += "&showDisabledPerson=true";
			}
			var treeObj = $.fn.zTree.getZTreeObj("personLeftTree");
			 if(!treeObj){
	        	return; 
	         }
			 if(isShowAll){
				 showIds = null;
			 }else{
				 showIds = new Array();
				 jQuery.ajax({
				       url: urlString,
				       data: {},
				       type: 'post',
				       dataType: 'json',
				       async:false,
				       error: function(data){
				       },
				       success: function(data){
				    	   showIds = data.personAll;
// 				        if(data.personAll){
// 				        	showIds = data.personAll;
// 				        }
				   }
				  });
			 }
			 toggleDisabledOrCount({treeId:'personLeftTree',
		         showCode:jQuery('#person_showCode')[0],
		         disabledDept:jQuery("#person_showDisabled")[0],
		         disabledPerson:jQuery('#person_showDisabledPerson')[0],
		         showCount:jQuery("#person_showPersonCount")[0],
		         showIds:showIds}); 
		}
    	function addRecord(){
			var url = "editPerson?popup=true";
			var winTitle='<fmt:message key="personNew.title"/>';
			openWindow(url, winTitle, " width=1000");
		}
		function editRecord(){
	        var sid = jQuery("#person_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editPerson?popup=true&personId=" + sid;
				var winTitle='<fmt:message key="personNew.title"/>';
				openWindow(url, winTitle, " width=1000");
			}
		}
		function checkGridDeleteOperation(response,postdata){
		    var gridresponse = gridresponse || {};
		    gridresponse = jQuery.parseJSON(response.responseText);
		    var msg = gridresponse["gridOperationMessage"];
		   // alert(msg);
		   //jQuery("#gridinfo").html(msg);
		   jQuery('div.#mybuttondialog').html(msg);
			  jQuery('#mybuttondialog').dialog('open');
	        return [true,""];   
		}
		
		function dealTreeNodeC(treeId,node,opt) {
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			var nodeId = node.id;
			var oldNode = treeObj.getNodeByParam("id", nodeId, null);
			if(node) {
				switch(opt) {
				case 'add' :
					var parentNode = treeObj.getNodeByParam("id", node.pId, null);
					node = treeObj.addNodes(parentNode,node);
					break;
				case 'change' :
					if(oldNode) {
						oldNode.name = node.name;
						treeObj.updateNode(oldNode);
					}
					break;
				case 'del' :
					treeObj.removeNode(oldNode);
					break;
				}
			}
		}
		
	    function okButton(){
	    	 jQuery('#mybuttondialog').dialog('close');
	    };		
		datePick = function(elem){
		        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
		        jQuery('#ui-datepicker-div').css("z-index", 2000);
		};
		/* var personLayout;
		jQuery(document).ready(function() { 
			personLayout = makeLayout({
				baseName: 'person', 
				tableIds: 'person_gridtable'
			}, null);
			personLayout.resizeAll();
    	});
		 */
		 var personGridIdString="#person_gridtable";
			jQuery(document).ready(function() { 
				var personFullSize = jQuery("#container").innerHeight()-116;
				setLeftTreeLayout('person_container','person_gridtable',personFullSize);
				personLeftTree();
				//GzType
				var gzTypeJsonStr = jQuery("#person_gzTypeJsonStr").val();
				var gzTypes = JSON.parse(gzTypeJsonStr);
				var gzTypeHtml = "<option value=''>--</option>";
				var gridGzTypeSelect = "";
				if(gzTypes){
					jQuery.each(gzTypes,function(index,gzType){
						var gzTypeId = gzType.gzTypeId;
						var gzTypeName = gzType.gzTypeName;
						gzTypeHtml += "<option value='"+gzTypeId+"'>"+gzTypeName+"</option>";
						gridGzTypeSelect += gzTypeId + ":"+ gzTypeName+";";
					});
				}
				if(gridGzTypeSelect){
					gridGzTypeSelect = gridGzTypeSelect.substring(0,gridGzTypeSelect.length-1);
				}
				//KqType
				var kqTypeJsonStr = jQuery("#person_kqTypeJsonStr").val();
				var kqTypes = JSON.parse(kqTypeJsonStr);
				var kqTypeHtml = "<option value=''>--</option>";
				var gridKqTypeSelect = "";
				if(kqTypes){
					jQuery.each(kqTypes,function(index,kqType){
						var kqTypeId = kqType.kqTypeId;
						var kqTypeName = kqType.kqTypeName;
						kqTypeHtml += "<option value='"+kqTypeId+"'>"+kqTypeName+"</option>";
						gridKqTypeSelect += kqTypeId + ":"+ kqTypeName+";";
					});
				}
				if(gridKqTypeSelect){
					gridKqTypeSelect = gridKqTypeSelect.substring(0,gridKqTypeSelect.length-1);
				}
// 				jQuery("#gzPerson_gzTypeSelect").html(gzTypeHtml);
				var initFlag_person = 0;
				var personGrid = jQuery(personGridIdString);
				personGrid.jqGrid({
					url : "personGridList?1=1",
					editurl : "personGridEdit",
					datatype : "json",
					mtype : "GET",
					colModel : [
						{name : 'personId',index : 'personId',align : 'left',width:100,label : '<s:text name="person.personId" />',hidden : false,key : true,highsearch:true},
						{name : 'name',index : 'name',align : 'left',width:150,label : '<s:text name="person.name" />',hidden : false, sortable:true,highsearch:true},
						{name : 'personCode',index : 'personCode',align : 'center',width:70,label : '<s:text name="person.personCode" />',hidden : false, sortable:true,highsearch:true},
						{name : 'department.name',index : 'department.name',align : 'left',width:70,label : '<s:text name="person.departmentName" />',hidden : false, sortable:true,highsearch:true},
						{name : 'org.orgname',index : 'org.orgname',align : 'left',width:70,label : '<fmt:message key="hisOrg.orgName" />',hidden : false, sortable:false,highsearch:true},
						{name : 'branch.branchName',index : 'branch.branchName',align : 'left',width:70,label : '<fmt:message key="hisOrg.branchName" />',hidden : false, sortable:false,highsearch:true},
						{name : 'sex',index : 'sex',align : 'center',width:50,label : '<s:text name="person.sex" />',hidden : false, sortable:true,highsearch:true},
						{name : 'status',index : 'status',align : 'left',width:100,label : '<s:text name="person.status" />',hidden : false, sortable:true,highsearch:true},
						{name : 'postType',index : 'postType',align : 'center',width:80,label : '<s:text name="person.postType" />',hidden : false, sortable:true,highsearch:true},
						{name : 'jobTitle',index : 'jobTitle',align : 'center',width:50,label : '<s:text name="person.jobTitle" />',hidden : false, sortable:true,highsearch:true},
						{name : 'ratio',index : 'ratio',align : 'center',width:100,label : '<s:text name="person.ratio" />',hidden : false, sortable:true,formatter:'currency',
								formatoptions:{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 4, prefix: '', suffix:'', defaultValue: '0.00'},highsearch:true},
						{name : 'idNumber',index : 'idNumber',align : 'left',width:80,label : '<s:text name="person.idNumber" />',hidden : false, sortable:true,highsearch:true},
						{name : 'disable',index : 'disable',align : 'center',width:50,label : '<s:text name="person.disable" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
						{name : 'gzType',index:'gzType',align:'left',width:'70px',label : '<s:text name="person.gzType" />',hidden:false,formatter: "select", editoptions:{value:gridGzTypeSelect}},
						{name : 'gzType2',index:'gzType2',align:'left',width:'70px',label : '工资类别2',hidden:false,formatter: "select", editoptions:{value:gridGzTypeSelect}},
						{name : 'stopSalary',index : 'stopSalary',align : 'center',width:50,label : '<s:text name="person.stopSalary" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
						{name : 'jjmark',index : 'jjmark',align : 'center',width:50,label : '<s:text name="person.jjmark" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
						{name : 'bank1',index : 'bank1',align : 'left',width:80,label : '<s:text name="person.bank1" />',hidden : false, sortable:true,highsearch:true},
						{name : 'salaryNumber',index : 'salaryNumber',align : 'left',width:80,label : '<s:text name="person.salaryNumber" />',hidden : false, sortable:true,highsearch:true},
						{name : 'bank2',index : 'bank2',align : 'left',width:80,label : '<s:text name="person.bank2" />',hidden : false, sortable:true,highsearch:true},
						{name : 'salaryNumber2',index : 'salaryNumber2',align : 'left',width:80,label : '<s:text name="person.salaryNumber2" />',hidden : false, sortable:true,highsearch:true},
						{name : 'taxType',index : 'taxType',align : 'left',width:80,label : '<s:text name="person.taxType" />',hidden : false, sortable:true,highsearch:true},
						{name : 'stopReason',index : 'stopReason',align : 'left',width:200,label : '<s:text name="person.stopReason" />',hidden : false, sortable:true,highsearch:true},
						{name : 'kqType',index:'kqType',align:'center',width:'70px',label : '<s:text name="person.kqType" />',hidden:false,formatter: "select", editoptions:{value:gridKqTypeSelect}},
						{name : 'stopKq',index : 'stopKq',align : 'center',width:50,label : '<s:text name="person.stopKq" />',hidden : false, sortable:true,formatter:'checkbox',highsearch:true},
						{name : 'stopKqReason',index : 'stopKqReason',align : 'left',width:200,label : '<s:text name="person.stopKqReason" />',hidden : false, sortable:true,highsearch:true}
					],
					jsonReader : {
						root : "persons", // (2)
						page : "page",
						total : "total",
						records : "records", // (3)
						repeatitems : false
					},
					sortname : 'personCode',
					viewrecords : true,
					sortorder : 'asc',
					height : 300,
					gridview : true,
					rownumbers : true,
					loadui : "disable",
					multiselect : true,
					multiboxonly : true,
					shrinkToFit : false,
					autowidth : false,
					onSelectRow : function(rowid) {

					},
					gridComplete : function() {
				 		/*2015.08.27 form search change*/
				 		gridContainerResize('person','layout');
						 var rowNum = jQuery(this).getDataIDs().length;
				           if(rowNum<=0){
								var tw = jQuery(this).outerWidth();
					        	jQuery(this).parent().width(tw);
					        	jQuery(this).parent().height(1);
							}else{
								var ztree = $.fn.zTree.getZTreeObj("personLeftTree");
				                if(ztree){
				                	var selectNode = ztree.getSelectedNodes();
									if(selectNode && selectNode.length==1&&selectNode[0].subSysTem=='PERSON'){
										var selectid = selectNode[0].id;
				 						jQuery(this).jqGrid('setSelection',selectid);
									}
				                }
							}
						var dataTest = {
							"id" : "person_gridtable"
						};
						jQuery.publish("onLoadSelect",
								dataTest, null);
						initFlag_person = initColumn('person_gridtable','com.huge.ihos.system.systemManager.organization.model.Person',initFlag_person);
					}
	    		}); 
				jQuery(personGrid).jqGrid('bindKeys');
				
				 /*--------------------------------------tooBar start-------------------------------------------*/
		    	var person_menuButtonArrJson = "${menuButtonArrJson}";
		    	var person_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(person_menuButtonArrJson,false)));
		    	var person_toolButtonBar = new ToolButtonBar({el:$('#person_buttonBar'),collection:person_toolButtonCollection,attributes:{
		    		tableId : 'person_gridtable',
		    		baseName : 'person',
		    		width : 700,
		    		height : 400,
		    		base_URL : null,
		    		optId : null,
		    		fatherGrid : null,
		    		extraParam : null,
		    		selectNone : "请选择记录。",
		    		selectMore : "只能选择一条记录。",
		    		addTitle : "添加人员",
		    		editTitle : "修改人员"
		    	}}).render();
		    	
		    	var person_function = new scriptFunction();
		    	person_function.optBeforeCall = function(e,$this,param){
		    		var pass = false;
					if('${yearStarted}' == 'true'){
						alertMsg.error("本年度人力资源系统已启用，请到人力资源系统维护!");
		    			return pass;
					}
			        return true;
				};
				person_toolButtonBar.addFunctionAdd('50010301');
				person_toolButtonBar.addBeforeCall('50010301',function(e,$this,param){
					var zTree = $.fn.zTree.getZTreeObj('personLeftTree');
		    		var nodes = zTree.getSelectedNodes(); 
				    if(nodes.length!=1 || nodes[0].subSysTem =='ALL' || nodes[0].subSysTem =='ORG'){
				    	alertMsg.error("请选择一个部门。");
			      		return;
				    }
				    if(nodes[0].actionUrl == '1'){
				    	alertMsg.error("已停用部门不能添加人员。");
			      		return;
				    }
				    if(nodes[0].subSysTem =='DEPT'&& nodes[0].state == 'PARENT'){ // 
				    	console.log(nodes[0]);
				    	alertMsg.error("父级部门不能添加人员。");
			      		return;
				    }
				    if(nodes[0].subSysTem =='PERSON' && nodes[0].getParentNode()){
				    	nodes[0] = nodes[0].getParentNode();
				    }
				    person_toolButtonBar.buttonUtil.userDefineParam = "&deptId="+nodes[0].id;
					return person_function.optBeforeCall(e,$this,param);
		    	},{});
				
				person_toolButtonBar.addCallBody('50010302',function(e,$this,param) {
					var sid = jQuery(personGridIdString).jqGrid("getGridParam","selarrrow");
					var urlString = "personGridEdit?id="+sid+"&navTabId=person_gridtable&oper=del";
					alertMsg.confirm("确认删除？",{
						okCall:function() {
							jQuery.post(urlString,function(data) {
								formCallBack(data);
								if(data.statusCode != 200) {
									return;
								}
								for(var i = 0;i < sid.length; i++) {
									dealTreeNodeC("personLeftTree",{id:sid[i]},"del");
								}
							});
						}
					});
				});//addFunctionDel('50010302');
				person_toolButtonBar.addBeforeCall('50010302',function(e,$this,param){
					return person_function.optBeforeCall(e,$this,param);
		    	},{});
				
				person_toolButtonBar.addFunctionEdit('50010303');
// 				person_toolButtonBar.addBeforeCall('50010303',function(e,$this,param){
// 					return person_function.optBeforeCall(e,$this,param);
// 		    	},{});
				// 批量修改
				person_toolButtonBar.addCallBody('50010304',function(e,$this,param){
					 var sid = jQuery("#person_gridtable").jqGrid('getGridParam','selarrrow');
					 if(sid==null || sid.length ==0){
						   alertMsg.error("请选择记录。");
						   return;
					  }
					  var url = "batchEditList?navTabId=person_gridtable&tableCode=t_person&tableKey=personId";
					  if('${yearStarted}' == 'true'){//人力资源启用后去除不可修改的字段
						  url += "&filterStr=filter_NIS_fieldCode=sex,dept_id,empType,postType,jobTitle,ratio,educationalLevel"; 
					  }
					  url = encodeURI(url);
					  var winTitle = '<s:text name="person.batchEdit"/>';
					  $.pdialog.open(url,'batchEditPerson',winTitle, {mask:true,width : 650,height : 480,maxable:true,resizable:true});  
				},{});
				// 备份
				person_toolButtonBar.addCallBody('50010305',function(e,$this,param){
					var taskName ="sp_personbakup" ;
					var proArgsStr ="${currentPeriod}";
					var url = 'backUpPerson?taskName='+taskName+'&proArgsStr='+proArgsStr;
					url = encodeURI(url);
					alertMsg.confirm("确认备份？", {
						okCall: function(){
							$.ajax({
							    url: url,
							    type: 'post',
							    dataType: 'json',
							    aysnc:false,
							    error: function(data){
							        
							    },
							    success: function(data){
							    	/* if(tableId){
							    		data.navTabId = tableId
							    	} */
							    	formCallBack(data);
							    	//alertMsg.info(data.message);
							    }
							});
						}
					});
				},{});
				//更新月度职工表
				person_toolButtonBar.addCallBody('50010306',function(e,$this,param){
					 var sid = jQuery("#person_gridtable").jqGrid('getGridParam','selarrrow');
					 if(sid==null || sid.length ==0){
						   alertMsg.error("请选择记录。");
						   return;
					  }
					 var winTitle = '更新月度职工表';
					 var url = "updateMonthPersonList?id="+sid+"&navTabId=person_gridtable&tableCode=t_person";
					 if('${yearStarted}' == 'true'){//人力资源启用后去除不可修改的字段
						  url += "&filter_NIS_fieldCode=sex,dept_id,empType,postType,jobTitle,ratio,educationalLevel"; 
					  }
					  url = encodeURI(url);
					 $.pdialog.open(url,'updateMonthPersonList',winTitle, {mask:true,width : 650,height : 480,maxable:true,resizable:true});
				},{});
				/* person_toolButtonBar.addBeforeCall('50010304',function(e,$this,param){
					return person_function.optBeforeCall(e,$this,param);
		    	},{}); */
				
				//设置表格列
				var person_setColShowButton = {id:'50010307',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
		   			callBody:function(){
		   				setColShow('person_gridtable','com.huge.ihos.system.systemManager.organization.model.Person');
		   			}};
				person_toolButtonBar.addButton(person_setColShowButton);
				
				
				/*人员类别树*/
				jQuery("#person_empTypes").treeselect({
					dataType : "sql",
					optType : "multi",
					sql : "SELECT id,name,parentType parent FROM t_personType where disabled=0  ORDER BY code",
					exceptnullparent : false,
					selectParent : false,
					lazy : false,
					minWidth : '120px'
				});
				/*部门树*/
				var sql="select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1-leaf isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from t_department where disabled=0 and deptId <> 'XT'"
					sql = sql+" union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT' ";
					sql += " ORDER BY orderCol ";
				jQuery("#person_depts").treeselect({
					optType : "multi",
					dataType : 'sql',
					sql : sql,
					exceptnullparent : true,
					lazy : false,
					minWidth : '140px',
					selectParent : false
				});
			});
			function personLeftTree(){
				var url = "makeDepartmentPersonTree";
				$.get(url, {"_" : $.now()}, function(data) {
					var departmentTreeData = data.personTreeNodes;
					var departmentTree = $.fn.zTree.init($("#personLeftTree"),ztreesetting_personTree, departmentTreeData);
					var nodes = departmentTree.getNodes();
					departmentTree.expandNode(nodes[0], true, false, true);
					departmentTree.selectNode(nodes[0]);
					toggleDisabledOrCount({treeId:'personLeftTree',
				         showCode:jQuery('#person_showCode')[0],
				         disabledDept:jQuery("#person_showDisabled")[0],
				         disabledPerson:jQuery('#person_showDisabledPerson')[0],
				         showCount:jQuery("#person_showPersonCount")[0] });
				});
				jQuery("#person_expandTree").text("展开");
			}
			var ztreesetting_personTree = {
					view : {
						dblClickExpand : false,
						showLine : true,
						selectedMulti : false,
						fontCss : setDisabledDeptFontCss
					},
					callback : {
						beforeDrag:function(){return false},
						onClick : function(event, treeId, treeNode, clickFlag){
							var urlString = "personGridList?1=1";
						    var nodeId = treeNode.id;
						    if(nodeId!="-1"){
						    	if(treeNode.subSysTem==='ORG'){
							    	urlString += "&orgCode="+nodeId;
						    	}else if(treeNode.subSysTem==='DEPT'){
							    	urlString += "&departmentId="+nodeId;
						    	}else{
						    		urlString += "&personId="+nodeId;
						    	}
						    }
						    urlString = urlString.replace('showDisabledDept','');
							urlString = urlString.replace('showDisabledPerson','');
							var showDisabledDept = jQuery("#person_showDisabled").attr("checked");
							var showDisabledPerson = jQuery("#person_showDisabledPerson").attr("checked");
							if(showDisabledDept){
								urlString += "&showDisabledDept=true";
							}
							if(showDisabledPerson){
								urlString += "&showDisabledPerson=true";
							}
							urlString=encodeURI(urlString);
							jQuery("#person_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
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
		function showDisabledForPerson(){
			var urlString = jQuery("#person_gridtable").jqGrid('getGridParam',"url");
			urlString = urlString.replace('showDisabledDept','');
			urlString = urlString.replace('showDisabledPerson','');
			var showDisabledDept = jQuery("#person_showDisabled").attr("checked");
			var showDisabledPerson = jQuery("#person_showDisabledPerson").attr("checked");
			if(showDisabledDept){
				urlString += "&showDisabledDept=true";
			}
			if(showDisabledPerson){
				urlString += "&showDisabledPerson=true";
			}
			 urlString=encodeURI(urlString);
			jQuery("#person_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
			personTreeReShow();
		} 
	</script>
</head>

<div class="page">
	<div id="person_pageHeader" class="pageHeader">
		<form  id="person_search_form" class="queryarea-form">
		<input id="person_gzTypeJsonStr" type="hidden" value='<s:property value="gzTypeJsonStr" escapeHtml="false"/>'>
		<input id="person_kqTypeJsonStr" type="hidden" value='<s:property value="kqTypeJsonStr" escapeHtml="false"/>'>
			<div class="searchBar">
				<div class="searchContent">
					<label class="queryarea-label">
						<s:text name="department.branchCode"></s:text>
						<s:select list="branches" headerKey="" headerValue="--" listKey="branchCode" listValue="branchName" name="filter_LIKES_branch.branchCode"></s:select>
					</label>
					<label class="queryarea-label"><fmt:message key='person.personId'/>：
							<input type="text" class="input-small"  size="15" name="filter_LIKES_personId"/>
					</label>
					<label class="queryarea-label">
						<fmt:message key='person.name'/>：
							<input type="text" class="input-small"  size="15" name="filter_LIKES_name"/>
					</label>
					<label class="queryarea-label">
						<fmt:message key='person.personCode'/>：
							<input type="text" class="input-small" size="15" name="filter_LIKES_personCode"/>
					</label>
					<label class="queryarea-label">
						<fmt:message key="person.departmentName"/>：
						<input type="text" id="person_depts" style="width: 100px;">
						<input type="hidden" id="person_depts_id" name="filter_INS_department.departmentId">
<%-- 						<s:select  theme="simple"  name="filter_EQS_department.departmentId" lable=""  maxlength="20" list="deptList"   listKey="departmentId" listValue="name" emptyOption="true" cssClass="form-horizontal"></s:select> --%>
					</label>
					<label class="queryarea-label">
						<fmt:message key='person.postType'/>：
						<s:select 
							list="postTypeList" cssClass="required" listKey="value"
							listValue="content" emptyOption="true" theme="simple" name="filter_EQS_postType"></s:select>
					  </label>
					<label class="queryarea-label">
						<fmt:message key='person.status'/>：
						<input id="person_empTypes" type="text" name="filter_INS_status" style="width: 100px;">
						<input id="person_empTypes_id" type="hidden">
<%-- 						<s:select  --%>
<%-- 							list="statusList" cssClass="required" listKey="value" --%>
<%-- 							listValue="content" emptyOption="true" theme="simple" name="filter_EQS_status"></s:select> --%>
					  </label>
					<label class="queryarea-label">
						<fmt:message key='person.disable'/>：
						<s:select list="#{'':'--','true':'是','false':'否'}"  name="filter_EQB_disable"></s:select>
					</label>
					<label class="queryarea-label">
						<fmt:message key='person.jjmark'/>：
					<s:select list="#{'':'--','true':'是','false':'否'}" name="filter_EQB_jjmark"></s:select>
					</label>
					<div class="buttonActive" style="float:right">
								<div class="buttonContent">
									<button type="button" onclick="personGridReload()"><s:text name='button.search'/></button>
								</div>
					</div>
					
				<%-- </td><td valign="bottom"><ul style="float:right">
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="personGridReload()"><fmt:message key='button.search'/></button>
								</div>
							</div></li>
						<!-- <li><a class="button" href="demo_page6.html" target="dialog"
							rel="dlg_page1" title="查询框"><span>高级检索</span>
						</a>
						</li> -->
					</ul></td></tr></table> --%>
				</div>
				
<!-- 				<div class="subBar"> -->
<!-- 							<ul> -->
<!-- 								<li><div class="buttonActive"> -->
<!-- 										<div class="buttonContent"> -->
<!-- 											<button type="button" onclick="personGridReload()"> -->
<%-- 												<fmt:message key='button.search' /> --%>
<!-- 											</button> -->
<!-- 										</div> -->
<!-- 									</div> -->
<!-- 								</li> -->
<!-- 							</ul> -->
<!-- 				</div> -->
				<%-- <div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="gridReload()"><fmt:message key='button.search'/></button>
								</div>
							</div></li>
						<!-- <li><a class="button" href="demo_page6.html" target="dialog"
							rel="dlg_page1" title="查询框"><span>高级检索</span>
						</a>
						</li> -->
					</ul>
				</div> --%>
			</div>
		</form>
	</div>
	<div class="pageContent">

 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="personGridEdit"/> 
<s:url id="remoteurl" action="personGridList"/> 

	<div id="person_buttonBar" class="panelBar">
			<%-- <ul class="toolBar">
				<li><a id="person_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="person_gridtable_del" class="delbutton"  href="javaScript:"><span><fmt:message key="button.delete" /></span>
				</a>
				</li>
				<li><a id="person_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><fmt:message key="button.edit" />
					</span>
				</a>
				</li>
				<li><a class="backupbutton"  href="javaScript:backUpPerson()"
					><span><fmt:message key="button.backup" />
					</span>
				</a>
				</li>
				<li>
					<a  class="delbutton"  href="javaScript:setColShow('person_gridtable','com.huge.ihos.system.systemManager.organization.model.Person')"><span><s:text name="button.setColShow" /></span></a>
				</li>
				<!-- <li class="line">line</li>
				<li><a class="icon" href="javascript:void(0);"><span>导入EXCEL</span>
				</a>
				</li> -->
			</ul> --%>
		</div>
		
			<div id="person_container">
			<div id="person_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
					<span>
						显示机构编码
						<input id="person_showCode" type="checkbox" checked="checked" onclick="toggleDisabledOrCount({treeId:'personLeftTree',showCode:this,disabledDept:jQuery('#person_showDisabled')[0],disabledPerson:jQuery('#person_showDisabledPerson')[0],showCount:jQuery('#person_showPersonCount')[0],showIds:showIds})"/>
					</span>
					<span>
						显示人员数
						<input id="person_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'personLeftTree',showCode:jQuery('#person_showCode')[0],disabledDept:jQuery('#person_showDisabled')[0],disabledPerson:jQuery('#person_showDisabledPerson')[0],showCount:this,showIds:showIds})"/>
					</span>
					<label id="person_expandTree" onclick="toggleExpandHrTree(this,'personLeftTree')">展开</label>
				</div>
				<div class="treeTopCheckBox">
					<span>
						显示停用部门
						<input id="person_showDisabled" type="checkbox" onclick="showDisabledForPerson()"/>
					</span>
					 <span>
          			 	显示停用人员
          			<input id="person_showDisabledPerson" type="checkbox" onclick="showDisabledForPerson()"/>
          			</span>
				</div>
				<div class="treeTopCheckBox">
					<span>
						快速查询：
						<input type="text" onKeyUp="searchTree('personLeftTree',this)"/>
					</span>
				</div>
				<div id="personLeftTree" class="ztree"></div>
			</div>
			<div id="personLeftTree_layout-center" class="pane ui-layout-center">
		
		<div id="person_gridtable_div"
			class="grid-wrapdiv"
			buttonBar="width:700;height:400">
			<input type="hidden" id="person_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="person_gridtable_addTile">
				<fmt:message key="personNew.title"/>
			</label> 
			<label style="display: none"
				id="person_gridtable_editTile">
				<fmt:message key="personEdit.title"/>
			</label>
			<label style="display: none"
				id="person_gridtable_selectNone">
				<fmt:message key='list.selectNone'/>
			</label>
			<label style="display: none"
				id="person_gridtable_selectMore">
				<fmt:message key='list.selectMore'/>
			</label>
			<div id="load_person_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
			 <table id="person_gridtable"></table>
		</div>
	<div class="panelBar" id="person_pageBar">
		<div class="pages">
			<span>显示</span> <select id="person_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span>条，共<label id="person_gridtable_totals"></label>条</span>
		</div>

		<div id="person_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

			</div>
			</div><!-- center -->
		</div><!-- layout -->
</div>
</div>