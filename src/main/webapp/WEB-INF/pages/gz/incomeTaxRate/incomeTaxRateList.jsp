
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var incomeTaxRateLayout;
	var incomeTaxRateGridIdString="#incomeTaxRate_gridtable";
	jQuery(document).ready(function() {
		var initFlag = 0;
		var incomeTaxRateGrid = jQuery(incomeTaxRateGridIdString);
    	incomeTaxRateGrid.jqGrid({
    		url : "incomeTaxRateGridList",
    		editurl:"incomeTaxRateGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
{name:'rateId',index:'rateId',align:'center',label : '<s:text name="incomeTaxRate.rateId" />',hidden:true,key:true},
{name:'level',index:'level',align:'right',width:'50px',label : '<s:text name="incomeTaxRate.level" />',hidden:false,formatter:'integer',highsearch:true},
{name:'baseNum',index:'baseNum',align:'right',width:'80px',label : '<s:text name="incomeTaxRate.baseNum" />',hidden:false,formatter:'integer',formatoptions: {thousandsSeparator:",", defaulValue:"",decimalPlaces:0},highsearch:true},
{name:'incomeFloor',index:'incomeFloor',align:'right',width:'110px',label : '<s:text name="incomeTaxRate.incomeFloor" />',hidden:false,formatter:'integer',formatoptions: {thousandsSeparator:",", defaulValue:"",decimalPlaces:0},highsearch:true},
{name:'incomeTopLimit',index:'incomeTopLimit',align:'right',width:'110px',label : '<s:text name="incomeTaxRate.incomeTopLimit" />',hidden:false,formatter:'integer',formatoptions: {thousandsSeparator:",", defaulValue:"",decimalPlaces:0},highsearch:true},
{name:'fullTaxCost',index:'fullTaxCost',align:'right',width:'80px',label : '<s:text name="incomeTaxRate.fullTaxCost" />',hidden:false,formatter:'number',formatoptions: {thousandsSeparator:",", defaulValue:"",decimalPlaces:2},highsearch:true},
{name:'extraCost',index:'extraCost',align:'right',width:'80px',label : '<s:text name="incomeTaxRate.extraCost" />',hidden:false,formatter:'number',formatoptions: {thousandsSeparator:",", defaulValue:"",decimalPlaces:2},highsearch:true},
{name:'taxRate',index:'taxRate',align:'right',width:'80px',label : '<s:text name="incomeTaxRate.taxRate" />',hidden:false,formatter:'number',formatoptions: {thousandsSeparator:",", defaulValue:"",decimalPlaces:2},highsearch:true},        	
{name:'disabled',index:'disabled',align:'center',width:'100px',label : '<s:text name="incomeTaxRate.disabled" />',hidden:false,formatter:'checkbox',highsearch:true}
],
        	jsonReader : {
				root : "incomeTaxRates", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'level',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="incomeTaxRateList.title" />',
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
		 		gridContainerResize('incomeTaxRate','div');
		 		var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum<=0){
	            	var tw = jQuery(this).outerWidth();
					jQuery(this).parent().width(tw);
					jQuery(this).parent().height(1);
	            }
           	var dataTest = {"id":"incomeTaxRate_gridtable"};
      	   	jQuery.publish("onLoadSelect",dataTest,null);
      	   	//makepager("incomeTaxRate_gridtable");
      	  	initFlag = initColumn('incomeTaxRate_gridtable','com.huge.ihos.gz.incomeTaxRate.model.IncomeTaxRate',initFlag);
       	  } 

    	});
    jQuery(incomeTaxRateGrid).jqGrid('bindKeys');
    
  	});
	/*----------------------------------tooBar start-----------------------------------------------*/
	var incomeTaxRate_menuButtonArrJson = "${menuButtonArrJson}";
	var incomeTaxRate_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(incomeTaxRate_menuButtonArrJson,false)));
	var incomeTaxRate_toolButtonBar = new ToolButtonBar({el:$('#incomeTaxRate_toolbuttonbar'),collection:incomeTaxRate_toolButtonCollection,attributes:{
		tableId : 'incomeTaxRate_gridtable',
		baseName : 'incomeTaxRate',
		width : 600,
		height : 600,
		base_URL : null,
		optId : null,
		fatherGrid : null,
		extraParam : null,
		selectNone : "请选择记录。",
		selectMore : "只能选择一条记录。",
		addTitle : '<s:text name="incomeTaxRateNew.title"/>',
		editTitle : null
	}}).render();
	
	var incomeTaxRate_function = new scriptFunction();
	incomeTaxRate_function.optBeforeCall = function(e,$this,param){
		var pass = false;
		if(param.selectRecord){
			var sid = jQuery("#incomeTaxRate_gridtable").jqGrid('getGridParam','selarrrow');
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
	incomeTaxRate_toolButtonBar.addCallBody('11010201',function(e,$this,param){
		var url = "editIncomeTaxRate?popup=true&navTabId=incomeTaxRate_gridtable";
		url = encodeURI(url);
		var winTitle = '<s:text name="incomeTaxRateNew.title"/>';
		$.pdialog.open(url,'addIncomeTaxRate',winTitle, {mask:true,width : 650,height : 250,maxable:true,resizable:true});
	},{});
	//删除
	incomeTaxRate_toolButtonBar.addFunctionDel('11010202');
	//修改
	incomeTaxRate_toolButtonBar.addCallBody('11010203',function(e,$this,param){
		var sid = jQuery("#incomeTaxRate_gridtable").jqGrid('getGridParam','selarrrow');
		var url = "editIncomeTaxRate?rateId="+sid+"&popup=true&navTabId=incomeTaxRate_gridtable";
		url = encodeURI(url);
		var winTitle = '<s:text name="incomeTaxRateEdit.title"/>';
		$.pdialog.open(url,'viewIncomeTaxRate_'+sid,winTitle, {mask:true,width : 650,height : 250,maxable:true,resizable:true});
	},{});
	incomeTaxRate_toolButtonBar.addBeforeCall('11010203',function(e,$this,param){
		return incomeTaxRate_function.optBeforeCall(e,$this,param);
	},{selectRecord:"selectRecord",singleSelect:"singleSelect"});

	//设置表格列
	var incomeTaxRate_setColShowButton = {id:'1001020188',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
			callBody:function(){
				setColShow('incomeTaxRate_gridtable','com.huge.ihos.gz.incomeTaxRate.model.IncomeTaxRate');
			}};
	incomeTaxRate_toolButtonBar.addButton(incomeTaxRate_setColShowButton);
	/*----------------------------------tooBar end-----------------------------------------------*/
	
	
    //验证incomeFloor条件
	function yzGzIncomeF(){
		var regex = new RegExp("^[0-9]*$");  
		var incomeFloor = jQuery("#incomeFloor").val();
		if(!regex.test(incomeFloor)){
			alertMsg.error("应纳税所得额的下限为数字型！");
			jQuery("#incomeFloor").val("");
			return ;
		}
		var incomeTopLimit = jQuery("#incomeTopLimit").val();
		if(!regex.test(incomeTopLimit)){
			alertMsg.error("应纳税所得额的上限为数字型！");
			jQuery("#incomeTopLimit").val("");
			return ;
		}
		if(incomeFloor>incomeTopLimit&&incomeTopLimit!=""){
			alertMsg.error("应纳税所得额的下限不能超过应纳税所得额的上限！");
			jQuery("#incomeFloor").val("");
			return ;
		}
	}
	//验证incomeTopLimit条件
	function yzGzIncomeT(){
		var regex = new RegExp("^[0-9]*$");  
		var incomeFloor = jQuery("#incomeFloor").val();
		if(!regex.test(incomeFloor)){
			alertMsg.error("应纳税所得额的下限为数字型！");
			jQuery("#incomeFloor").val("");
			return ;
		}
		var incomeTopLimit = jQuery("#incomeTopLimit").val();
		if(!regex.test(incomeTopLimit)){
			alertMsg.error("应纳税所得额的上限为数字型！");
			jQuery("#incomeTopLimit").val("");
			return ;
		}
		if(incomeTopLimit<incomeFloor&&incomeTopLimit!=""){
			alertMsg.error("应纳税所得额的上限不能低于应纳税所得额的下限！");
			jQuery("#incomeTopLimit").val("");
			return ;
		}
	}
</script>

<div class="page" id="incomeTaxRate_page">
	<div id="incomeTaxRate_pageHeader" class="pageHeader" >
			<div class="searchBar">
				<div class="searchContent">
				<form id="incomeTaxRate_search_form" class="queryarea-form">
					<label class="queryarea-label" >
						<s:text name='incomeTaxRate.incomeFloor'/>:
						<input id="incomeFloor" type="text" name="filter_LEI_incomeFloor" style="width:100px" onblur="yzGzIncomeF()"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='incomeTaxRate.incomeTopLimit'/>:
						<input id="incomeTopLimit" type="text" name="filter_LEI_incomeTopLimit" style="width:100px" onblur="yzGzIncomeT()"/>
					</label>
					<label class="queryarea-label" >
						<s:text name='incomeTaxRate.taxRate'/>:
						<input type="text" name="filter_LEN_taxRate" style="width:80px" />
					</label>
					<label class="queryarea-label" >
			   			<s:text name='incomeTaxRate.disabled'/>:			
						<s:select name="filter_EQB_disabled" headerKey=""   style="font-size:12px"
							list="#{'':'--','true':'是','false':'否'}" listKey="key" listValue="value"
							emptyOption="false"  maxlength="50" width="50px">
						</s:select>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearch('incomeTaxRate_search_form','incomeTaxRate_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
					</form>
				</div>
<!-- 				<div class="subBar"> -->
<!-- 					<ul> -->
<!-- 						<li><div class="buttonActive"> -->
<!-- 								<div class="buttonContent"> -->
<%-- 									<button type="button" onclick="propertyFilterSearch('incomeTaxRate_search_form','incomeTaxRate_gridtable')"><s:text name='button.search'/></button> --%>
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="incomeTaxRate_buttonBar">
			<ul class="toolBar" id="incomeTaxRate_toolbuttonbar">
				<%-- <li><a  class="addbutton" href="javaScript:addGzInfo()" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="incomeTaxRate_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a  class="changebutton" Onclick="viewGzInfo()"><span><s:text name="button.edit" />
					</span>
				</a>
				</li> --%>
			</ul>
		</div>
		<div id="incomeTaxRate_gridtable_div" class="grid-wrapdiv" buttonBar="width:500;height:300">
			<input type="hidden" id="incomeTaxRate_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="incomeTaxRate_gridtable_addTile">
				<s:text name="incomeTaxRateNew.title"/>
			</label> 
			<label style="display: none"
				id="incomeTaxRate_gridtable_editTile">
				<s:text name="incomeTaxRateEdit.title"/>
			</label>
			<div id="load_incomeTaxRate_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="incomeTaxRate_gridtable"></table>
			<!--<div id="incomeTaxRatePager"></div>-->
		</div>
	</div>
	<div class="panelBar" id="incomeTaxRate_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="incomeTaxRate_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="incomeTaxRate_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="incomeTaxRate_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
	</div>
</div>