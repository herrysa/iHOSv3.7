
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var deptAppDistributeDetailGrid = jQuery("#deptAppDistributeDetail_gridtable");
	jQuery(document).ready(function() {
		deptAppDistributeDetailGrid.jqGrid({
	    	url : "invOutStoreGridList?filter_EQS_storeId=${storeId}&filter_EQS_invId=${invDictId}",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="invOutStore.id" />',hidden:true,key:true},				
				{name:'invId',index:'invId',align:'left',width : 120,label : '<s:text name="invOutStore.invId" />',hidden:true},				
				{name:'invName',index:'invName',align:'left',width : 120,label : '<s:text name="材料名称" />',hidden:false},				
				{name:'batchNo',index:'batchNo',align:'left',width : 120,label : '<s:text name="批号" />',hidden:false},				
				{name:'price',index:'price',align:'right',width : 120,label : '<s:text name="单价" />',hidden:false,formatter : 'currency'},				
				{name:'curAmount',index:'curAmount',align:'right',width : 120,label : '<s:text name="库存量" />',hidden:false,formatter : 'number'},			
				{name:'disAmount',index:'disAmount',align:'right',width : 120,label : '<s:text name="发放量" />',hidden:false,formatter : 'number',edittype:"text",editable : true,editrules:{number:true},editoptions:{dataEvents:[{type:'blur',fn: function(e) { validateDeptAppDistributeDetailPassAmount(this); }}]}},			
				{name:'batchId',index:'batchId',align:'left',width : 120,label : '<s:text name="invOutStore.batchId" />',hidden:true},				
				{name:'validityDate',index:'validityDate',align:'center',width : 120,label : '<s:text name="有效期" />',hidden:false,formatter: 'date',formatoptions: {srcformat: 'Y-m-d H:i:s',newformat: 'Y-m-d'}},				
				{name:'invCode',index:'invCode',align:'left',width : 120,label : '<s:text name="材料编码" />',hidden:false},				
				{name:'invModel',index:'invModel',align:'left',width : 120,label : '<s:text name="型号规格" />',hidden:false},				
				{name:'firstUnit',index:'firstUnit',align:'center',width : 120,label : '<s:text name="计量单位" />',hidden:false},				
			],
	        jsonReader : {
				root : "invOutStores", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			},
	        sortname: 'guarantee,batchNo',
	        viewrecords: true,
	        gridview:true,
	        rownumbers:true,
	        emptyrecords:'该材料当前仓库无库存',
	        sortorder: 'desc',
	        height:300,
	        loadui: "disable",
	       	multiselect: false,
			multiboxonly:false,
			shrinkToFit:true,
			autowidth:true,
			onSelectRow : function(rowid) {
				
			},
			ondblClickRow:function(rowid){
				jQuery(this).jqGrid("editRow",rowid,{
           			"keys" : false,
           			"url":"clientArray"
				});
			},
			gridComplete:function(){
	           var dataTest = {"id":"deptAppDistributeDetail_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   //var needAmount = jQuery("#${subGridId}").jqGrid("getCell","${subRowId}","throughAmount");
	      	   //needAmount = convertStringToFloat(needAmount);
	      	   //distributeAmount(needAmount);
			   var deptAppId = getMainRowIdBySubGridId("${subGridId}");
	      	   initDisAndCurAmount(deptAppId,"${subRowId}");
			} 
		});
		jQuery(deptAppDistributeDetailGrid).jqGrid('bindKeys');
	});
	/*页面初始根据输入历史渲染disAmount*/
	function initDisAndCurAmount(deptAppId,deptAppDetailId){
		if(disResultMap.size==0) {
			return;
		}
		if(disResultMap.get(deptAppId)==null){
			return;
		}
		var disDetailArray = disResultMap.get(deptAppId);
		var disBatchData = null;
		for(var i=0;i<disDetailArray.length;i++){
			var disDetailMap = disDetailArray[i];
			disBatchData = disDetailMap.get(deptAppDetailId);
			if(disBatchData!=null){
				break;
			}
		}
		if(!disBatchData || disBatchData==null){
			return;
		}
		var disBatchArray = disBatchData['batchDetail'];
		var disBatchIds = jQuery("#deptAppDistributeDetail_gridtable").getDataIDs();
		for(var i=0;i<disBatchArray.length;i++){
			var disBatchMap = disBatchArray[i];
			var disBatchId = jQuery("#deptAppDistributeDetail_gridtable").jqGrid("getCell",disBatchIds[i],"batchId");
			var disInfo = disBatchMap.get(disBatchId);
			var disAmount = disInfo['disAmount'];
			disAmount = convertStringToFloat(disAmount);
			jQuery("#deptAppDistributeDetail_gridtable").jqGrid("setCell",disBatchIds[i],"disAmount",disAmount); 
			
			/* for(var j=0;j<disBatchIds.length;j++){
				if(disAmount==null){
					continue;
				}
			} */
		}
	}
	function validateDeptAppDistributeDetailPassAmount(obj){
		if(!numberValidate(obj)){
  			return;
  		}
		//不能超过库存量
		var tdObj = jQuery(obj).parent();//td
		var trObj = tdObj.parent();//tr
		var trId = trObj.children().eq(1).text();//trId
		var curAmount = tdObj.prev().html();
		var disAmount = jQuery(obj).val();
		curAmount = isNaN(parseFloat(curAmount))?0:parseFloat(curAmount);
		disAmount = isNaN(parseFloat(disAmount))?0:parseFloat(disAmount);
		if(disAmount>curAmount){
			jQuery(obj).val("").focus();
			alertMsg.error("发放数量超过库存量！");
			return;
		}
		//综合该列表所有发放值赋值到明细表的本次通过数量
		var allDisAmount = disAmount;
		var ids = deptAppDistributeDetailGrid.getDataIDs();
		for(var i=0;i<ids.length;i++){
			if(ids[i]==trId){
				continue;
			}
			var rdisAmount = deptAppDistributeDetailGrid.jqGrid("getCell",ids[i],"disAmount");
			rdisAmount = isNaN(parseFloat(rdisAmount))?0:parseFloat(rdisAmount);
			allDisAmount += rdisAmount;
		}
		//判断发放数量不能超过申领数量
		var appAmount = jQuery("#${subGridId}").jqGrid("getCell","${subRowId}","waitingAmount");
		appAmount = isNaN(parseFloat(appAmount))?0:parseFloat(appAmount);
		if(allDisAmount>appAmount){
			jQuery(obj).val("");
			alertMsg.error("发放数量超过等待通过数量！");
			return;
		}
		jQuery("#${subGridId}").jqGrid("setCell","${subRowId}","throughAmount",allDisAmount);
		deptAppDistributeDetailGrid.jqGrid("saveRow",trId,{url:'clientArray'});
		//将该条明细的数据暂时存放
		updateRealDisData("${subGridId}","${subRowId}");
	}
</script>

<div class="page">
	<div class="pageContent">
		<div id="deptAppDistributeDetail_gridtable_div"  extraHeight=60 tablecontainer="deptAppDistribute_layout-south" class="grid-wrapdiv">
			<div id="load_deptAppDistributeDetail_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
	 		<table id="deptAppDistributeDetail_gridtable"></table>
		</div>
	</div>
</div>