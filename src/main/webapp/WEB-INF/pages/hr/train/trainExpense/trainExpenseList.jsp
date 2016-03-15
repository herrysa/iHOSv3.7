
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	var trainExpenseGridIdString="#trainExpense_gridtable";
	
	jQuery(document).ready(function() {
		var initFlag = 0;
		var trainExpenseGrid = jQuery(trainExpenseGridIdString);
	    trainExpenseGrid.jqGrid({
	    	url : "trainExpenseGridList",
	    	editurl:"trainExpenseGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="trainExpense.id" />',hidden:true,key:true},	
				{name:'code',index:'code',width : 100,align:'left',label : '<s:text name="trainExpense.code" />',hidden:false,highsearch:true},
				{name:'trainNeed.name',index:'trainNeed.name',width : 100,align:'left',label : '<s:text name="trainExpense.trainNeed" />',hidden:false,highsearch:true},
				{name:'makeDate',index:'makeDate',align:'center',width : 70,label : '<s:text name="trainExpense.makeDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'name',index:'name',width : 80,align:'left',label : '<s:text name="trainExpense.name" />',hidden:false,highsearch:true},
// 				{name:'yearMonth',index:'yearMonth',align:'center',width : 50,label : '<s:text name="期间" />',hidden:false,highsearch:true},
				{name:'maker.name',index:'maker.name',width : 80,align:'left',label : '<s:text name="trainExpense.maker" />',hidden:false,highsearch:true},
				{name:'totalExpense',index:'totalExpense',width : 60,align:'right',label : '<s:text name="trainExpense.totalExpense" />',hidden:false,formatter:'number',highsearch:true},	
				{name:'cateringExpense',index:'cateringExpense',width : 100,align:'right',label : '<s:text name="trainExpense.cateringExpense" />',hidden:true,formatter:'number',highsearch:true},				
				{name:'courseExpense',index:'courseExpense',width : 100,align:'right',label : '<s:text name="trainExpense.courseExpense" />',hidden:true,formatter:'number',highsearch:true},				
				{name:'equipmentExpense',index:'equipmentExpense',width : 100,align:'right',label : '<s:text name="trainExpense.equipmentExpense" />',hidden:true,formatter:'number',highsearch:true},				
				{name:'externalTrainExpense',index:'externalTrainExpense',width : 100,align:'right',label : '<s:text name="trainExpense.externalTrainExpense" />',hidden:true,formatter:'number',highsearch:true},				
				{name:'internalTrainExpense',index:'internalTrainExpense',width : 100,align:'right',label : '<s:text name="trainExpense.internalTrainExpense" />',hidden:true,formatter:'number',highsearch:true},				
				{name:'onJobStudyExpense',index:'onJobStudyExpense',width : 100,align:'right',label : '<s:text name="trainExpense.onJobStudyExpense" />',hidden:true,formatter:'number',highsearch:true},				
				{name:'teachingMaterialExpense',width : 100,index:'teachingMaterialExpense',align:'right',label : '<s:text name="trainExpense.teachingMaterialExpense" />',hidden:true,formatter:'number',highsearch:true},				
				{name:'trainSiteExpense',index:'trainSiteExpense',width : 100,align:'right',label : '<s:text name="trainExpense.trainSiteExpense" />',hidden:true,formatter:'number',highsearch:true},				
				{name:'travelExpense',index:'travelExpense',width : 100,align:'right',label : '<s:text name="trainExpense.travelExpense" />',hidden:true,formatter:'number',highsearch:true},				
				{name:'vocationalCertificateExpense',index:'vocationalCertificateExpense',width : 100,align:'right',label : '<s:text name="trainExpense.vocationalCertificateExpense" />',hidden:true,formatter:'number',highsearch:true},				
				{name:'otherExpense',index:'otherExpense',width : 100,align:'right',label : '<s:text name="trainExpense.otherExpense" />',hidden:true,formatter:'number',highsearch:true},	
				{name:'remark',index:'remark',width : 250,align:'left',label : '<s:text name="trainExpense.remark" />',hidden:false,highsearch:true}
	        ],
	        jsonReader : {
				root : "trainExpenses", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'id',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="trainExpenseList.title" />',
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
				 /*2015.08.27 form search change*/
				 gridContainerResize('trainExpense','div');
		 		var rowNum = jQuery(this).getDataIDs().length;
		 		var totalExpense=0;
            	if(rowNum>0){
                	var rowIds = jQuery(this).getDataIDs();
                	var ret = jQuery(this).jqGrid('getRowData');
                	var id='';
                	for(var i=0;i<rowNum;i++){
                		var rowTotalExpense = ret[i]["totalExpense"];
                		totalExpense+=parseFloat(rowTotalExpense);
              	  		id=rowIds[i];
              	  		setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewTrainExpenseRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
              		}
            	}else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
//             	var footerData='{code:"合计",';
//             		footerData+='totalExpense:"'+totalExpense+'"}';
//             	jQuery(this).footerData("set",  eval('(' + footerData + ')'));
            	jQuery(this).footerData("set",{code:"合计",totalExpense:totalExpense});
           		var dataTest = {"id":"trainExpense_gridtable"};
      	   		jQuery.publish("onLoadSelect",dataTest,null);
      	   		initFlag = initColumn('trainExpense_gridtable','com.huge.ihos.hr.trainExpense.model.TrainExpense',initFlag);
       		} 
    	});
    	jQuery(trainExpenseGrid).jqGrid('bindKeys');
    	//实例化ToolButtonBar
        var trainExpense_menuButtonArrJson = "${menuButtonArrJson}";
        var trainExpense_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(trainExpense_menuButtonArrJson,false)));
        var trainExpense_toolButtonBar = new ToolButtonBar({el:$('#trainExpense_buttonBar'),collection:trainExpense_toolButtonCollection,attributes:{
         tableId : 'trainExpense_gridtable',
         baseName : 'trainExpense',
         width : 600,
         height : 600,
         base_URL : null,
         optId : null,
         fatherGrid : null,
         extraParam : null,
         selectNone : "请选择记录。",
         selectMore : "只能选择一条记录。",
         addTitle : '<s:text name="trainExpenseNew.title"/>',
         editTitle : null
        }}).render();
        //实例化结束
        var trainExpense_function = new scriptFunction();
        trainExpense_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.checkPeriod){
				if('${yearStarted}'!='true'){
					alertMsg.error("本年度人力资源系统未启用。");
	    			return pass;
				}
			}
	        return true;
		};
    //为button添加方法 (普通点击按钮)
    //添加
     trainExpense_toolButtonBar.addCallBody('1005040501',function(e,$this,param){
    		var url = "editTrainExpense?popup=true&navTabId=trainExpense_gridtable";
			var winTitle='<s:text name="trainExpenseNew.title"/>';
			$.pdialog.open(url,'addTrainExpense',winTitle, {mask:true,width : 700,height : 400,maxable:false});
   },{});
     trainExpense_toolButtonBar.addBeforeCall('1005040501',function(e,$this,param){
			return trainExpense_function.optBeforeCall(e,$this,param);
 	},{checkPeriod:"checkPeriod"});
   //删除
     trainExpense_toolButtonBar.addCallBody('1005040502',function(e,$this,param){
    	 var url = "${ctx}/trainExpenseGridEdit?oper=del";
        	var sid = jQuery("#trainExpense_gridtable").jqGrid('getGridParam','selarrrow');
        	if(sid==null|| sid.length == 0){       	
  				alertMsg.error("请选择记录。");
  				return;
  			}else {
  				url = url+"&id="+sid+"&navTabId=trainExpense_gridtable";
  				alertMsg.confirm("确认删除？", {
  					okCall : function() {
  						$.post(url,function(data) {
  							formCallBack(data);
  						});
  					}
  				});
  			}	
   },{});
     trainExpense_toolButtonBar.addBeforeCall('1005040502',function(e,$this,param){
			return trainExpense_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
   //修改
     trainExpense_toolButtonBar.addCallBody('1005040503',function(e,$this,param){
    	 var sid = jQuery("#trainExpense_gridtable").jqGrid('getGridParam','selarrrow');
     	if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
			}
			var winTitle='<s:text name="trainExpenseEdit.title"/>';
			var url = "editTrainExpense?popup=true&id="+sid+"&navTabId=trainExpense_gridtable";
			$.pdialog.open(url,'editTrainExpense',winTitle, {mask:true,width : 700,height : 400,maxable:false});
   },{});
     trainExpense_toolButtonBar.addBeforeCall('1005040503',function(e,$this,param){
			return trainExpense_function.optBeforeCall(e,$this,param);
	},{checkPeriod:"checkPeriod"});
   //设置表格列
        var trainExpense_setColShowButton = {id:'1005040504',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
          callBody:function(){
           setColShow('trainExpense_gridtable','com.huge.ihos.hr.trainExpense.model.TrainExpense');
          }};
        trainExpense_toolButtonBar.addButton(trainExpense_setColShowButton);//实例化ToolButtonBar
    	
    	
    	
    	jQuery("#trainExpense_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
		}); 
     	jQuery("#trainExpense_gridtable_edit_custom").unbind( 'click' ).bind("click",function(){   
		}); 
	//trainExpenseLayout.resizeAll();
		jQuery("#search_trainExpense_trainNeed").treeselect({
		   dataType:"sql",
		   optType:"single",
		   sql:"SELECT id,name FROM hr_train_class where state='2' ORDER BY id",
		   exceptnullparent:false,
		   minWidth:"150px",
		   lazy:false
		});
  	});
	function viewTrainExpenseRecord(id){
		var url = "editTrainExpense?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainExpense','查看培训费用信息', {mask:true,width : 700,height : 400,maxable:false});
	}
</script>

<div class="page">
	<div class="pageHeader" id="trainExpense_pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="trainExpense_search_form" style="white-space: break-all;word-wrap:break-word;">
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainExpense.trainNeed'/>:
					 	<input type="hidden" id="search_trainExpense_trainNeed_id" name="filter_EQS_trainNeed.id">
				 		<input type="text" id="search_trainExpense_trainNeed" style="width:80px">
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainExpense.makeDate'/>:
						<input type="text"	id="trainExpense_search_make_date_from"  name="filter_GED_makeDate" style="width:60px;height:15px" class="Wdate"   onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'trainExpense_search_make_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="trainExpense_search_make_date_to" name="filter_LED_makeDate" style="width:60px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'trainExpense_search_make_date_from\')}'})">
					</label>
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainExpense.code'/>:
					 	<input type="text" name="filter_LIKES_code" style="width:80px"/>
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%-- 						<s:text name='trainExpense.name'/>: --%>
<!-- 					 	<input type="text" name="filter_LIKES_name" style="width:60px"/> -->
<!-- 					</label>	 -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainExpense.money'/>:
						<input type="text" name="filter_GEN_totalExpense" style="width:50px;height:15px">
						<s:text name='至'/>
					 	<input type="text"	name="filter_LEN_totalExpense" style="width:50px;height:15px">
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%--      				 <s:text name="期间"/>: --%>
<!--      					 <input type="text"  name="filter_EQS_yearMonth" style="width:50px;" onclick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"/> -->
<!--      				</label> -->
					<label style="float:none;white-space:nowrap" >
						<s:text name='trainExpense.remark'/>:
					 	<input type="text" name="filter_LIKES_remark" style="width:150px;"/>
					</label>	
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('trainExpense_search_form','trainExpense_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="propertyFilterSearch('trainExpense_search_form','trainExpense_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div></li> -->
				
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="trainExpense_buttonBar">
<!-- 			<ul class="toolBar"> -->
<%-- 				<li><a id="trainExpense_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message --%>
<%-- 								key="button.add" /> --%>
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<%-- 				<li><a id="trainExpense_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span> --%>
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li><a id="trainExpense_gridtable_edit_custom" class="changebutton"  href="javaScript:" -->
<%-- 					><span><s:text name="button.edit" /> --%>
<!-- 					</span> -->
<!-- 				</a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%--     				 <a class="settlebutton"  href="javaScript:setColShow('trainExpense_gridtable','com.huge.ihos.hr.trainExpense.model.TrainExpense')"><span><s:text name="button.setColShow" /></span></a> --%>
<!--     			</li> -->
<!-- 			</ul> -->
		</div>
		<div id="trainExpense_gridtable_div" class="grid-wrapdiv" extraHeight="50"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden">
			<input type="hidden" id="trainExpense_gridtable_navTabId" value="${sessionScope.navTabId}">
			<div id="load_trainExpense_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="trainExpense_gridtable"></table>
		</div>
	</div>
	<div class="panelBar" id="trainExpense_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="trainExpense_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="trainExpense_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="trainExpense_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>