
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
 
var defColumnFormula_list = new Array();
var defColumnFormulaColumnType_list = new Array();
var defColumnFormulaType_list = new Array();
var defColumnFormulaTemple_list = new Array();
var defColumnValue_list = new Array();
<c:forEach items="${jjUpdataDefColumns}" var="dc">
	<c:if test="${dc.type=='计算'}">
		defColumnFormula_list.push("${dc.columnName}="+"${dc.formula}");
		defColumnFormulaTemple_list.push("${dc.columnName}="+"${dc.formula}");
		defColumnFormulaColumnType_list.push("${dc.type}");
		
	</c:if>
	<c:if test="${dc.type=='其他'}">
		defColumnFormula_list.push("${dc.columnName}=-2");
		defColumnFormulaTemple_list.push("${dc.columnName}=-2");
		defColumnFormulaColumnType_list.push("${dc.type}");
	</c:if>
	<c:if test="${dc.type=='手工'}">
		defColumnFormula_list.push("${dc.columnName}=-1");
		defColumnFormulaTemple_list.push("${dc.columnName}=-1");
		defColumnFormulaColumnType_list.push("${dc.type}");
	</c:if>
	defColumnFormulaType_list.push("${dc.columnType}");
	//defColumnValue.push("${dc.columnName}=");
</c:forEach>
 
	function jjUpdataGridReload(){
		var urlString = "jjUpdataGridList";
		var paramKeyTxt = jQuery("#paramKeyTxt").val();
		var paramValueTxt = jQuery("#paramValueTxt").val();
		var descriptionTxt = jQuery("#descriptionTxt").val();
		var subSystemTxt = jQuery("#subSystemTxt").val();
	
		urlString=urlString+"?filter_LIKES_paramKey="+paramKeyTxt+"&filter_LIKES_paramValue="+paramValueTxt+"&filter_LIKES_description="+descriptionTxt+"&filter_LIKES_subSystemId="+subSystemTxt;
		urlString=encodeURI(urlString);
		jQuery("#jjUpdata_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var jjUpdataLayout;
			  var jjUpdataGridIdString="#jjUpdata_gridtable";
	
	jQuery(document).ready(function() { 
		/* jjUpdataLayout = makeLayout({
			baseName: 'jjUpdata', 
			tableIds: 'jjUpdata_gridtable'
		}, null); */
var jjUpdataGrid = jQuery(jjUpdataGridIdString);
    jjUpdataGrid.jqGrid({
    	url : "jjUpdataGridList",
    	editurl:"jjUpdataGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'updataId',index:'updataId',align:'center',label : '<s:text name="jjUpdata.updataId" />',hidden:true,key:true,formatter:'integer',editable:true},				
{name:'checkperiod',index:'checkperiod',align:'left',label : '<s:text name="jjUpdata.checkperiod" />',hidden:false,width:60},				
{name:'department.name',index:'department.name',align:'left',label : '<s:text name="jjUpdata.dept" />',hidden:false,width:100},
//{name:'zjj',index:'zjj',align:'right',label : '<s:text name="jjUpdata.zjj" />',hidden:false,formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", //decimalPlaces: 2, defaultValue: '0.00'},width:80},

{name:'zjj',index:'zjj',align:'center',label : '<s:text name="总绩效" />',hidden:false,formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},width:80},



{name:'ownerdept.name',index:'ownerdept.name',align:'left',label : '<s:text name="jjUpdata.ownerdept" />',hidden:false,width:100},
{name:'person.personCode',index:'person.personCode',align:'left',label : '<s:text name="person.personCode" />',hidden:false,width:80},
{name:'person.name',index:'person.name',align:'left',label : '<s:text name="person.name" />',hidden:false,width:80},
<c:forEach items="${jjUpdataDefColumns}" var="dc">
{
	name : "sqlDatamap.${dc.columnName }",
	index : "sqlDatamap.${dc.columnName }",
	label : "${dc.title }",
	width : 80,
	sortable:false,
	/* <c:if test="${dc.columnType=='varchar'}">
		formatter:stringFormatter,
	</c:if> */
	<c:if test="${dc.columnType=='numeric'||dc.columnType=='money'}">
		align:"right",
		formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},
	</c:if>
	<c:if test="${dc.type=='手工'}">
		editable:true
		//editoptions:{dataEvents:[{type:'blur',fn: function(e) { computDefColumnForList(this); }}]}
	</c:if>
},
</c:forEach>				
/*{name:'itemName',index:'itemName',align:'center',label : '<s:text name="jjUpdata.itemName" />',hidden:false},				
{name:'operateDate',index:'operateDate',align:'center',label : '<s:text name="jjUpdata.operateDate" />',hidden:false,formatter:'date'},				
{name:'operateDate1',index:'operateDate1',align:'center',label : '<s:text name="jjUpdata.operateDate1" />',hidden:false,formatter:'date'},				
{name:'operateDate2',index:'operateDate2',align:'center',label : '<s:text name="jjUpdata.operateDate2" />',hidden:false,formatter:'date'},				
{name:'state',index:'state',align:'center',label : '<s:text name="jjUpdata.state" />',hidden:false,formatter:'integer'}				
*/
{name:'amount',index:'amount',align:'right',label : '<s:text name="jjUpdata.amount" />',formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},width:80},				
//{name:'operator.name',index:'operator.name',align:'center',label : '<s:text name="jjUpdata.operator" />',hidden:false,width:80},				
{name:'remark',index:'remark',align:'center',label : '<s:text name="jjUpdata.remark" />',hidden:false,edittype:"textarea",editoptions: {rows:"1"},editable:true,width:200},				

        ],
        jsonReader : {
			root : "jjUpdatas", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'person.personCode',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="jjUpdataList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:false,
		autowidth:false,
		footerrow: true,
        onSelectRow: function(rowid) {
       
       	},
		 gridComplete:function(){
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"jjUpdata_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("jjUpdata_gridtable");
      	 
      	var jqgHeight = jQuery("#jjUpdata_gridtable_div").height();;
      	//var jqgWeigth = jQuery(this).jqGrid('getGridWeight');
      	//jQuery(this).jqGrid('setGridHeight',jqgHeight-53);
         var rowNum = parseInt($(this).getGridParam('records'),10);
         if(rowNum > 0){
          $(".ui-jqgrid-sdiv").show();
             //var id = jQuery(this).getCol('updataId',false);
             var footerData = '{"checkperiod":"合计",';
             for(var i=0;i<defColumnFormula_list.length;i++){
            	 
            	 if(defColumnFormulaType_list[i]=="numeric"||defColumnFormulaType_list[i]=="money"){
            		 var muValue = 0;
        			 var sumValue = 0;
            		/*  if(defColumnFormulaColumnType_list[i]=="手工"){
            			 //alert(jQuery("input[name='sqlDatamap.def_b']").length);
            			 jQuery("input[name='sqlDatamap."+defColumnFormula_list[i].split("=")[0]+"']").each(function(){
            				 muValue += parseInt(jQuery(this).val());
            			 });
            			 sumValue = muValue;
            		 }else{
	            	 	sumValue = jQuery(this).getCol("sqlDatamap."+defColumnFormula_list[i].split("=")[0],false,'sum');
            		 } */
            	 	 sumValue = jQuery(this).getCol("sqlDatamap."+defColumnFormula_list[i].split("=")[0],false,'sum');
	            	 footerData += '\"sqlDatamap.'+defColumnFormula_list[i].split("=")[0]+'\":'+sumValue+',';
            	 }
             }
             var sumValue = jQuery(this).getCol("amount",false,'sum');
             footerData += '"amount":'+sumValue;
             //footerData = footerData.substring(0,footerData.length-1);
             footerData += '}';
             //alert(footerData);
             $(this).footerData("set",  eval('(' + footerData + ')'));
             //alert(footerData);
         }else{
        	 var tw = jQuery(this).outerWidth();
			 jQuery(this).parent().width(tw);
			 jQuery(this).parent().height(1);
         }
         /* else{
            $(".ui-jqgrid-sdiv").hide();
         } */
         jQuery(jjUpdataGrid).unbind("keyup").bind("keyup",function(e){
			 chargeKeyPress(jQuery(jjUpdataGrid),e);
			});
        },
        userDataOnFooter: true

    });
    jQuery(jjUpdataGrid).jqGrid('bindKeys');
    
	
	
	
	//jjUpdataLayout.resizeAll();
	
    /*===================================按钮权限begin============================================*/
	//实例化toolButtonBar
	var jjUpdata_menuButtonArrJson = "${menuButtonArrJson}";
	var jjUpdata_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(jjUpdata_menuButtonArrJson,false)));
	var jjUpdata_toolButtonBar = new ToolButtonBar({el:$('#jjUpdata_buttonBar'),collection:jjUpdata_toolButtonCollection,attributes:{
		tableId : 'jjUpdata_gridtable',
		baseName : 'jjUpdata',
		width : 600,
		height : 600,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : '<s:text name="jjUpdataNew.title"/>',
		editTitle : null
		}}).render();
	var jjUpdata_function = new scriptFunction();
	jjUpdata_function.optBeforeCall = function(e,$this,param){
		var pass = false;
		if(param.selectRecord){
			var sid = jQuery("#jjUpdata_gridtable").jqGrid('getGridParam','selarrrow');
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
	//初始化
	jjUpdata_toolButtonBar.addCallBody('06030301',function() {
		executeSpU('sp_jj_updataInit','jjUpdata_gridtable');
	},{});
	//继承
	jjUpdata_toolButtonBar.addCallBody('06030302',function() {
		inheritJiUpdata();
	},{});
	//添加
	jjUpdata_toolButtonBar.addCallBody('06030303',function() {
		addJjUpdate();
	},{});
	//删除
	jjUpdata_toolButtonBar.addFunctionDel('06030304');
	jjUpdata_toolButtonBar.addBeforeCall('06030304',function(e,$this,param) {
		return jjUpdata_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord"});
	//编辑
	jjUpdata_toolButtonBar.addCallBody('06030305',function() {
		editRowJjUpdata();
	},{});
	//取消编辑
	jjUpdata_toolButtonBar.addCallBody('06030306',function() {
		unfullGridEdit(jQuery('#jjUpdata_gridtable'));
	},{});
	//保存计算
	jjUpdata_toolButtonBar.addCallBody('06030307',function() {
		saveDefColumnData();
	},{});
	//全部确认
	jjUpdata_toolButtonBar.addCallBody('06030308',function() {
		executeSp('sp_jj_updataCheck','jjUpdata_gridtable');
	},{});
	
	 /*===================================按钮权限end============================================*/
  	});
	var updateColumn = new Array();
	var updateDataValue = "defData_";
	var updateColumnInfo = new Array();
	updateColumnInfo.push("updataId");
	updateColumnInfo.push("手工");
	updateColumn.push(updateColumnInfo);
	<c:forEach items="${jjUpdataDefColumns}" var="dc">
		updateColumnInfo = new Array();
		updateColumnInfo.push("sqlDatamap.${dc.columnName}");
		updateColumnInfo.push("${dc.type}");
		updateColumn.push(updateColumnInfo);
	</c:forEach>
	updateColumnInfo = new Array();
	updateColumnInfo.push("remark");
	updateColumnInfo.push("手工");
	updateColumn.push(updateColumnInfo);
	function saveDefColumnData(){
		updateDataValue = "";
		for(var i=0;i<updateColumn.length;i++){
			updateDataValue += updateColumn[i][0]+"=";
			if(updateColumn[i][1]=="手工"){
				if(updateColumn[i][0]=='remark'){
					jQuery("textarea[name='"+updateColumn[i][0]+"']").each(function(){
						updateDataValue += jQuery(this).val()+",";
			  		});
				}else{
					jQuery("input[name='"+updateColumn[i][0]+"']").each(function(){
						updateDataValue += jQuery(this).val()+",";
			  		});
				}
				
			}else{
				var ids = jQuery(jjUpdataGridIdString).jqGrid('getDataIDs');
				for(var idindex=0;idindex < ids.length;idindex++){
					var cl = ids[idindex];
					var row = jQuery(jjUpdataGridIdString).jqGrid('getRowData',cl);
					updateDataValue += row[updateColumn[i][0]]+",";
					//jQuery(jjUpdataGridIdString).editRow(cl);
				}	
			}
			updateDataValue += "end@";
		}
		//alert(updateDataValue);
  		var url="saveDefColumnData";
  		url=encodeURI(url);
  		
  		var taskName ="sp_jj_updataCal";
		var proArgsStr ="${checkPeriod},${personId}";
  		
  		$.ajax({
		    url: url,
		    type: 'post',
		    data:{defData_defData:updateDataValue,taskName:taskName,proArgsStr:proArgsStr,navTabId:'jjUpdata_gridtable'},
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
	function quchuColumnForList(formula){
		var rsformula = formula;
		//alert("be::"+rsformula);
		if(rsformula.indexOf("[")!=-1){
			for(var i=0;i<defColumnFormula_list.length;i++){
				var columnName = rsformula.substring(rsformula.indexOf("[")+1,rsformula.indexOf("]"));
				if(defColumnFormula_list[i].split("=")[0]==columnName){
					rsformula = rsformula.replace("["+columnName+"]",defColumnFormula_list[i].split("=")[1]);
					//alert(rsformula);
					quchuColumnForList(rsformula);
				}
			}
		}else{
			defColumnValue_list[columnIndex_list] = eval(rsformula);
			//return eval(rsformula);
		}
	}
	var columnIndex_List = 0;
	function computDefColumnForList(obj){
		//var objValue = jQuery(obj).val();
		//var objName = jQuery(obj).attr("name");
		for(var i=0;i<defColumnFormula_list.length;i++){
			if(defColumnFormula_list[i].split("=")[1]=='-1'){
				var muValue = jQuery(obj).parent().parent().find("input[name='sqlDatamap."+defColumnFormula_list[i].split("=")[0]+"']").val();
				defColumnFormula_list[i] = defColumnFormula_list[i].replace('-1',muValue);
			}
		}
		for(var i=0;i<defColumnFormula_list.length;i++){
			columnIndex_list = i;
			var nameAndFormula = defColumnFormula_list[i].split("=");
			quchuColumnForList(nameAndFormula[1]);
			//defColumnValue[columnIndex_list]
			/* jQuery(obj).parent().parent().find("td").each(function(){
				alert(jQuery(this).attr('aria-describedby'));
			}); */
			jQuery(obj).parent().parent().find("td[aria-describedby='jjUpdata_gridtable_sqlDatamap."+nameAndFormula[0]+"']").text(defColumnValue_list[columnIndex_list]);
			//var gridId = jQuery(obj).parent().find("input[name='updataId']").val();
			//var row = jQuery(jjUpdataGridIdString).jqGrid('getRowData',gridId);
			//row['sqlDatamap.'+nameAndFormula[0]] = defColumnValue_list[columnIndex_list];
			//jQuery(jjUpdataGridIdString).jqGrid('saveRowData',row);
			
		}
		for(var i=0;i<defColumnFormula_list.length;i++){
			defColumnFormula_list[i] = defColumnFormulaTemple_list[i];
		}
	}
	
	function confirmAll(){
		/* $.ajax({
		    url: 'confirmAll',
		    type: 'post',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        //jQuery('#name').attr("value",data.responseText);
		    },
		    success: function(data){
		        // do something with xml
		        alert(data.message);
		    }
		}); */
		var taskName ="sp_jj_updataCheck";
		var proArgsStr ="${checkPeriod},${personId}";
		var url = 'executeSp';
		alertMsg.confirm("是否全部确认？", {
			okCall: function(){
				$.ajax({
				    url: url,
				    type: 'post',
				    data:{taskName:taskName,proArgsStr:proArgsStr},
				    dataType: 'json',
				    aysnc:false,
				    error: function(data){
				        
				    },
				    success: function(data){
				    	alertMsg.info(data.message);
				    	jQuery('#jjUpdata_gridtable').trigger("reloadGrid");
				    }
				});
			}
		});
	}
	function initJjUpdata(){
		
		var taskName ="sp_jj_updataInit";
		var proArgsStr ="${checkPeriod},${personId}";
		var url = 'executeSp';
		alertMsg.confirm("确认初始化？", {
			okCall: function(){
				$.ajax({
				    url: url,
				    type: 'post',
				    data:{taskName:taskName,proArgsStr:proArgsStr},
				    dataType: 'json',
				    aysnc:false,
				    error: function(data){
				        
				    },
				    success: function(data){
				    	alertMsg.info(data.message);
				    }
				});
			}
		});
	}
	var haveAddRight = "${haveAddRight}";
	function addJjUpdate(){
		if(haveAddRight=='0'){
			alertMsg.error("您没有上报权限！");
			return;
		}
		$.ajax({
		    url: 'beforeAddJjUpdata',
		    type: 'get',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		    	if(data.statusCode==200){
		    		var url = "editJjUpdata?popup=true&navTabId=jjUpdata_gridtable";
		    		var winTitle="添加信息";
		    		url = encodeURI(url);
		    		$.pdialog.open(url, 'addJjUpdata', winTitle, {mask:false,width:600,height:400});
		    	}else{
		    		alertMsg.error(data.message);
		    	}
		    }
		});
		
	}
	
	function unfullGridEdit(grid){
		var currentPage = grid.jqGrid('getGridParam', 'page');
		grid.trigger('reloadGrid',[ {page : currentPage} ]);
	}
	
	function inheritJiUpdata(){
		/*
			1:判断是否初始化
			2：已经初始化，弹出选择继承条件的dialog
		*/
		if(haveAddRight=='0'){
			alertMsg.error("您没有上报权限！");
			return;
		}
		$.ajax({
		    url: 'beforeInheritJjUpdata',
		    type: 'get',
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		    	if(data.statusCode==200){
			    	$.pdialog.open('jjUpdataInheritance', 'jjUpdataInheritance', '选择历史上报数据', {mask:false,width:500,height:340,resizable:false});
		    	}else{
		    		alertMsg.error(data.message);
		    	}
		    }
		});
	}
	
	function editRowJjUpdata(){
		<c:if test="${empty jjUpdataDefColumns}">
			jQuery('#jjUpdata_gridtable').jqGrid("setColProp","amount",{editable:true});
		</c:if>
		fullGridEdit(jQuery('#jjUpdata_gridtable'));
	}
</script>

<div class="page">
	<div id="jjUpdata_container">
		<div id="jjUpdata_layout-center" class="pane ui-layout-center">
			<%-- <div id="jjUpdata_pageHeader" class="pageHeader">
			<div class="searchBar">
			<div class="searchContent">
					<label style="float:none;white-space:nowrap" >
						<s:text name='inspectTempl.inspectModelId'/>:
						 	<input type="text"	id="inspectTempl_inspectModelId_search" >
					</label>&nbsp;&nbsp;
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="jjUpdataGridReload()"><s:text name='button.search'/></button>
						</div>
					</div>
			</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="jjUpdataGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div>
			</div>
	</div> --%>
			<div class="pageContent">





				<div class="panelBar" id="jjUpdata_buttonBar">
					<%-- <ul class="toolBar">
						<li><a class="addbutton"
							href="javaScript:executeSp('sp_jj_updataInit','jjUpdata_gridtable')"><span>初始化
							</span> </a></li>
						<li><a class="inheritbutton"
							href="javaScript:inheritJiUpdata()"><span>继承</span></a></li>
						<li><a class="addbutton" href="javaScript:addJjUpdate()"><span><fmt:message
										key="button.add" /> </span> </a></li>
						<li><a id="jjUpdata_gridtable_del" class="delbutton"
							href="javaScript:"><span><s:text name="button.delete" /></span>
						</a></li>
						<li><a class="edit"
							href="javaScript:editRowJjUpdata()"><span>编辑
							</span> </a></li>
						<li><a class="canceleditbutton"
							href="javaScript:unfullGridEdit(jQuery('#jjUpdata_gridtable'))"><span>取消编辑
							</span> </a></li>
						<li><a class="changebutton"
							href="javaScript:saveDefColumnData()"><span>保存/计算
							</span> </a></li>
						<li><a class="changebutton"
							href="javaScript:executeSp('sp_jj_updataCheck','jjUpdata_gridtable')"><span>全部确认
							</span> </a></li>
 --%>
						<%--		
				<li><a class="changebutton"  href="javaScript:executeSp('sp_xxx','jjUpdata_gridtable')"
					><span>获取岗位津贴
					</span>
				</a>
				</li>

				--%>

					<!-- </ul> -->
				</div>
				<div id="jjUpdata_gridtable_div" layoutH="56" extraHeight=53
					class="grid-wrapdiv"
					buttonBar="optId:updataId;width:600;height:400">
					<input type="hidden" id="jjUpdata_gridtable_navTabId"
						value="${sessionScope.navTabId}"> <label
						style="display: none" id="jjUpdata_gridtable_addTile"> <s:text
							name="jjUpdataNew.title" />
					</label> <label style="display: none" id="jjUpdata_gridtable_editTile">
						<s:text name="jjUpdataEdit.title" />
					</label> <label style="display: none" id="jjUpdata_gridtable_selectNone">
						<s:text name='list.selectNone' />
					</label> <label style="display: none" id="jjUpdata_gridtable_selectMore">
						<s:text name='list.selectMore' />
					</label>
					<div id="load_jjUpdata_gridtable"
						class='loading ui-state-default ui-state-active'
						style='display: none'></div>
					<table id="jjUpdata_gridtable"></table>
					<!-- <div id="jjUpdataPager"></div> -->
				</div>
			</div>
			<div class="panelBar">
				<div class="pages">
					<span><s:text name="pager.perPage" /></span> <select
						id="jjUpdata_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span><s:text name="pager.items" />. <s:text
							name="pager.total" /><label id="jjUpdata_gridtable_totals"></label>
					<s:text name="pager.items" /></span>
				</div>

				<div id="jjUpdata_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

			</div>
		</div>
	</div>
</div>