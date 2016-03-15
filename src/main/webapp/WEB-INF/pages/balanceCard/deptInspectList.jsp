
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	function deptInspectGridReload(){
		var urlString = "deptInspectGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#deptInspectMain_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var deptInspectLayout;
			  var deptInspectGridIdString="#deptInspectMain_gridtable";
	
	jQuery(document).ready(function() {
		var deptInspectChangeData = function(selectedSearchId){
			if(selectedSearchId.length==0){
				deptInspectLayout.closeSouth();
				return;
			}
			var newSelectedSearchId = selectedSearchId[selectedSearchId.length-1];
    		jQuery("#deptInspectDetail").loadUrl("deptInspectDetailList?inspectTemplId="+newSelectedSearchId);
    		$("#background,#progressBar").hide();
    	};
    	deptInspectLayout = makeLayout(
				{
					'baseName' : 'deptInspectMain',
					'tableIds' : 'deptInspectMain_gridtable;deptInspectDetail_gridtable',
					'activeGridTable':'deptInspectMain_gridtable',
					'proportion' : 2,
					'key' : 'inspectModelId'
				}, deptInspectChangeData);
var deptInspectGrid = jQuery(deptInspectGridIdString);
    deptInspectGrid.jqGrid({
    	url : "deptInspectMainGridList",
    	editurl:"deptInspectGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
                  
{name:'inspectModelId',index:'inspectModelId',align:'left',label : '<s:text name="inspectTempl.inspectModelId" />',hidden:true,key:true},				
{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="deptInspectMain.checkperiod" />',hidden:false,width:80},				
{name:'periodType',index:'periodType',align:'center',label : '<s:text name="inspectTempl.periodType" />',hidden:false,width:80},				
{name:'jjdepttype.khDeptTypeName',index:'jjdepttype.khDeptTypeName',align:'left',label : '<s:text name="inspectTempl.jjdepttype" />',hidden:false,width:100},				
{name:'inspectModelName',index:'inspectModelName',align:'left',label : '<s:text name="inspectTempl.inspectModelName" />',hidden:false,width:120},				
{name:'departmentNames',index:'departmentNames',align:'left',label : '<s:text name="inspectTempl.departmentNames" />',hidden:false,width:300},				
        
/* {name:'deptinspectId',index:'deptinspectId',align:'center',label : '<s:text name="deptInspect.deptinspectId" />',hidden:false,key:true},				
{name:'aValue',index:'aValue',align:'center',label : '<s:text name="deptInspect.aValue" />',hidden:false,formatter:'number'},				
{name:'checkperiod',index:'checkperiod',align:'center',label : '<s:text name="deptInspect.checkperiod" />',hidden:false},				
{name:'dscore',index:'dscore',align:'center',label : '<s:text name="deptInspect.dscore" />',hidden:false,formatter:'number'},				
{name:'jjdepttype',index:'jjdepttype',align:'center',label : '<s:text name="deptInspect.jjdepttype" />',hidden:false},				
{name:'money1',index:'money1',align:'center',label : '<s:text name="deptInspect.money1" />',hidden:false,formatter:'number'},				
{name:'money2',index:'money2',align:'center',label : '<s:text name="deptInspect.money2" />',hidden:false,formatter:'number'},				
{name:'operateDate',index:'operateDate',align:'center',label : '<s:text name="deptInspect.operateDate" />',hidden:false},				
{name:'operateDate1',index:'operateDate1',align:'center',label : '<s:text name="deptInspect.operateDate1" />',hidden:false},				
{name:'operateDate2',index:'operateDate2',align:'center',label : '<s:text name="deptInspect.operateDate2" />',hidden:false},				
{name:'periodType',index:'periodType',align:'center',label : '<s:text name="deptInspect.periodType" />',hidden:false},				
{name:'remark',index:'remark',align:'center',label : '<s:text name="deptInspect.remark" />',hidden:false},				
{name:'remark2',index:'remark2',align:'center',label : '<s:text name="deptInspect.remark2" />',hidden:false},				
{name:'remark3',index:'remark3',align:'center',label : '<s:text name="deptInspect.remark3" />',hidden:false},				
{name:'score',index:'score',align:'center',label : '<s:text name="deptInspect.score" />',hidden:false,formatter:'number'},				
{name:'state',index:'state',align:'center',label : '<s:text name="deptInspect.state" />',hidden:false},				
{name:'tValue',index:'tValue',align:'center',label : '<s:text name="deptInspect.tValue" />',hidden:false,formatter:'number'},				
{name:'weight',index:'weight',align:'center',label : '<s:text name="deptInspect.weight" />',hidden:false,formatter:'number'}				
 */
        ],
        jsonReader : {
			root : "inspectTempls", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'inspectModelId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="deptInspectList.title" />',
        height:'100%',
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:true,
		ondblClickRow:function(){
			deptInspectLayout.optDblclick();
		},
		onSelectRow: function(rowid) {
        	setTimeout(function(){
        		deptInspectLayout.optClick();
        	},100);
       	},
		 gridComplete:function(){
			 gridContainerResize("deptInspectMain","div");
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"deptInspectMain_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   //makepager("deptInspectMain_gridtable");
       	} 

    });
    jQuery(deptInspectGrid).jqGrid('bindKeys');
    /*===================================按钮权限begin============================================*/
    //实例化ToolButtonBar
     var deptInspectMain_menuButtonArrJson = "${menuButtonArrJson}";
     var deptInspectMain_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(deptInspectMain_menuButtonArrJson,false)));
     var deptInspectMain_toolButtonBar = new ToolButtonBar({el:$('#deptInspectMain_toolbuttonbar'),collection:deptInspectMain_toolButtonCollection,attributes:{
      tableId : 'deptInspectMain_gridtable',
      baseName : 'deptInspectMain',
      width : 600,
      height : 600,
      base_URL : null,
      optId : null,
      fatherGrid : null,
      extraParam : null,
      selectNone : "请选择记录。",
      selectMore : "只能选择一条记录。",
      addTitle : '<s:text name="deptInspectMainNew.title"/>',
      editTitle : null
     }}).render();
     //生成
     deptInspectMain_toolButtonBar.addCallBody('0601030201',function(e,$this,param){
    	 alertMsg.confirm("确认生成本核算期间的评分表？", {
 			okCall: function(){
 				$.ajax({
 				    url: 'creatInspect?navTabId=deptInspectMain_gridtable',
 				    type: 'post',
 				    dataType: 'json',
 				    aysnc:false,
 				    error: function(data){
 				        //jQuery('#name').attr("value",data.responseText);
 				    },
 				    success: function(data){
 				        // do something with xml
 				    	formCallBack(data);
 				    }
 				});
 			}
 		});
     },{});
     //删除
     deptInspectMain_toolButtonBar.addCallBody('0601030202',function(e,$this,param){
    		var tableId = "deptInspectMain_gridtable";
    		var editUrl = "deptInspectGridEdit";
    		var sid = jQuery("#"+tableId).jqGrid('getGridParam','selarrrow');
    		var editUrl = jQuery("#"+tableId).jqGrid('getGridParam', 'editurl');
    		editUrl+="?id=" + sid+"&navTabId="+tableId+"&oper=del";
    		editUrl = encodeURI(editUrl);
    	    if(sid==null || sid.length ==0){
    				alertMsg.error(selectNone);
    				return;
    		}else{
    			//jQuery("#"+tableId).jqGrid('delGridRow',sid,{reloadAfterSubmit:false,left:300,top:150});
    			alertMsg.confirm("确认删除评分表？", {
    				okCall: function(){
    					$.ajax({
    					    url: editUrl,
    					    type: 'post',
    					    dataType: 'json',
    					    aysnc:false,
    					    error: function(data){
    					        //jQuery('#name').attr("value",data.responseText);
    					    },
    					    success: function(json){
    					        // do something with xml
    					    	if (json.statusCode == DWZ.statusCode.ok){
    					    		if (json.navTabId){
    					    			//navTab.reload(json.forwardUrl, {navTabId: json.navTabId});
    					    			jQuery('#'+json.navTabId).trigger("reloadGrid");
    					    		} else if (json.rel) {
    					    			navTabPageBreak({}, json.rel);
    					    		}
    					    		if ("closeCurrent" == json.callbackType) {
    					    			$.pdialog.closeCurrent();
    					    		}
    					    	}else{
    					    		alertMsg.confirm("评分表正在使用中，确认删除？", {
    									okCall: function(){
    										$.ajax({
    										    url: editUrl+"&delByPower=true",
    										    type: 'post',
    										    dataType: 'json',
    										    aysnc:false,
    										    error: function(data){
    										        //jQuery('#name').attr("value",data.responseText);
    										    },
    										    success: function(json){
    										    	formCallBack(json);
    										    }
    										});
    									}
    					    		});
    					    		
    					    	}
    					    }
    					});
    				}
    			});
    		}
     },{});
     //自动得分
     deptInspectMain_toolButtonBar.addCallBody('0601030203',function(e,$this,param){
    	 executeSp('sp_kh_ScoreCalc','deptInspectMain_gridtable');
     },{});
     //明细
     deptInspectMain_toolButtonBar.addCallBody('0601030204',function(e,$this,param){
    	 deptInspectLayout.optDblclick();
     },{});
    /*===================================按钮权限end============================================*/
	
	
	
	//deptInspectLayout.resizeAll();
  	});
	function autoComputeScore(){
		var taskName ="sp_kh_ScoreCalc";
		var proArgsStr ="${checkPeriod}";
		var url = 'backUpPerson?taskName='+taskName+'&proArgsStr='+proArgsStr;
		alertMsg.confirm("确认计算？", {
			okCall: function(){
				$.ajax({
				    url: url,
				    type: 'post',
				    dataType: 'json',
				    aysnc:false,
				    error: function(data){
				        
				    },
				    success: function(data){
				    	alert(data.statusCode);
				    	formCallBack(data.message);
				    }
				});
			}
		});
	}
</script>

<div class="page" id="deptInspectMain_page">
	<div class="pageHeader" id="deptInspectMain_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="deptInspectMain_form" class="queryarea-form">
					<label class="queryarea-label"> <s:textfield
							key='inspectTempl.inspectModelName' name="inspectTemplName" />
					</label> <label class="queryarea-label"> <s:text
							name='inspectTempl.jjdepttype' />: <s:select list="khDeptTypes"
							listKey="khDeptTypeName" listValue="khDeptTypeName"
							headerKey="-1" headerValue="--" name="jjdepttype"></s:select>
					</label> <label class="queryarea-label"> <s:text
							name='inspectTempl.periodType' />: <select name="periodType">
							<option value=''>--</option>
							<option value='月'>月</option>
							<option value='季'>季</option>
							<option value='半年'>半年</option>
							<option value='年'>年</option>
					</select>
					</label> <label class="queryarea-label"> <s:text
							name='deptInspect.department' />: <input type="hidden"
						name="targetDept" id="deptNameTreeSelectCreat_id"> <input
						type="text" id="deptNameTreeSelectCreat" /> <script>
							addTreeSelect("tree", "sync",
									"deptNameTreeSelectCreat",
									"deptNameTreeSelectCreat_id", "single", {
										tableName : "t_department",
										treeId : "deptId",
										treeName : "name",
										parentId : "jjdepttype",
										order : "internalCode asc",
										filter : " jjleaf='1'",
										classTable : "KH_Dict_JjDeptType",
										classTreeId : "jjDeptTypeId",
										classTreeName : "jjDeptTypeName",
										classFilter : ""
									});
						</script>
					</label>
					<div class="buttonActive" style="float: right">
						<div class="buttonContent">
							<button type="button"
								onclick="propertyFilterSearch('deptInspectMain_form','deptInspectMain_gridtable')">
								<s:text name='button.search' />
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="deptInspectMain_buttonBar">
			<ul class="toolBar" id="deptInspectMain_toolbuttonbar">
<!-- 				<li><a class="addbutton" href="javaScript:creatInspect()" ><span> -->
<!-- 						生成 -->
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<%-- 				<li><a class="delbutton"  href="javaScript:deleteInspect()"><span><s:text name="button.delete" /></span> --%>
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li><a class="changebutton"  href="javaScript:executeSp('sp_kh_ScoreCalc','deptInspectMain_gridtable')" -->
<!-- 					><span>自动得分 -->
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li><a class="particularbutton" href="javaScript:deptInspectLayout.optDblclick();"><span>明细</span> </a></li> -->
			</ul>
		</div>
		<div id="deptInspectMain_container">
			<div id="deptInspectMain_layout-center" class="pane ui-layout-center">
				<div id="deptInspectMain_gridtable_div" class="grid-wrapdiv"
					buttonBar="width:500;height:300">
					<input type="hidden" id="deptInspectMain_gridtable_navTabId"
						value="${sessionScope.navTabId}"> <label
						style="display: none" id="deptInspectMain_gridtable_addTile">
						<s:text name="deptInspectNew.title" />
					</label> <label style="display: none"
						id="deptInspectMain_gridtable_editTile"> <s:text
							name="deptInspectEdit.title" />
					</label> <label style="display: none"
						id="deptInspectMain_gridtable_selectNone"> <s:text
							name='list.selectNone' />
					</label> <label style="display: none"
						id="deptInspectMain_gridtable_selectMore"> <s:text
							name='list.selectMore' />
					</label>
					<div id="load_deptInspectMain_gridtable"
						class='loading ui-state-default ui-state-active'
						style="display: none"></div>
					<table id="deptInspectMain_gridtable"></table>
					<div id="deptInspectPager"></div>
				</div>
				<div class="panelBar" id="deptInspectMain_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select
							id="deptInspectMain_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text
								name="pager.total" /><label
							id="deptInspectMain_gridtable_totals"></label>
						<s:text name="pager.items" /></span>
					</div>

					<div id="deptInspectMain_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1"></div>

				</div>
			</div>
			<div id="deptInspectMain_layout-south" class="pane ui-layout-south" style="padding: 2px">
				<div class="panelBar">
					<ul class="toolBar">
					
						<li style="float: right;"><a id="deptInspectMain_close"
							class="closebutton" href="javaScript:"><span><fmt:message
										key="button.close" /> </span> </a></li>
						<li class="line" style="float: right">line</li>
						<li style="float: right;"><a id="deptInspectMain_fold"
							class="foldbutton" href="javaScript:"><span><fmt:message
										key="button.fold" /> </span> </a></li>
						<li class="line" style="float: right">line</li>
						<li style="float: right"><a id="deptInspectMain_unfold"
							class="unfoldbutton" href="javaScript:"><span><fmt:message
										key="button.unfold" /> </span> </a></li>
					</ul>
				</div>
				<div id="deptInspectDetail"></div>
			</div>
		</div>
	</div>
</div>