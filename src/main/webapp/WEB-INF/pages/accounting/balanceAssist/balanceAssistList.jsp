
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 	var balanceAssistGridRow,balanceAssistGridCol,balanceAssistRowId;
	var balanceAssistLayout;
			  var balanceAssistGridIdString="#balanceAssist_gridtable";
	jQuery(document).ready(function() { 
var balanceAssistGrid = jQuery(balanceAssistGridIdString);
    balanceAssistGrid.jqGrid({
    	url : "balanceAssistGridList?balanceId=${balanceId}",
    	editurl:"balanceAssistGridEdit?balanceId=${balanceId}",
		datatype : "json",
		mtype : "GET",
		<c:if test="${copyBalanceFlag!='true'}">
		cellEdit:true,
		cellsubmit:'clientArray',
		</c:if>
        colModel:[
<c:forEach items="${assistTypeList}" var="asst">
	{name:'assist.assist_typeValue_${asst.typeCode}',index:'assistValue',align:'center',label : 'typeValue',hidden:true,sortable:false,editable:false},
	{name:'assist.assist_typeCode_${asst.typeCode}',index:'assistValue',align:'center',label : '辅助核算编码',hidden:true,sortable:false,editable:true},
	{name:'assist.assist_typeName_${asst.typeCode}',index:'assistValue',align:'center',label : '${asst.typeName}',hidden:false,sortable:false,editable:true,editoptions : {
		dataInit : invBalanceAssistComboGrid
	}},
	{name:'assist.assist_assId_${asst.typeCode}',index:'assistValue',align:'center',sortable:false,label : '<s:text name="balanceAssist.balanceAssistId" />',hidden:true,editable:true},
</c:forEach>
{name:'beginJie',index:'beginJie',align:'center',label : '<s:text name="balanceAssist.beginJie" />',sortable:false,hidden:false,formatter:'number',editable:true},				
{name:'beginDai',index:'beginDai',align:'center',label : '<s:text name="balanceAssist.beginDai" />',sortable:false,hidden:false,formatter:'number',editable:true},				
{name:'yearBalance',index:'yearBalance',align:'center',label : '<s:text name="balanceAssist.yearBalance" />',sortable:false,hidden:false,formatter:'number',editable:true},				
{name:'initBalance',index:'initBalance',align:'center',label : '<s:text name="balanceAssist.initBalance" />',sortable:false,hidden:false,formatter:'number',editable:true},				
{name:'num',index:'num',align:'center',label : '<s:text name="balanceAssist.num" />',sortable:false,hidden:true,key:true,formatter:'integer',editable:true},				

        ],
        jsonReader : {
			root : "balanceAssistList", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'balanceAssistId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="balanceAssistList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:true,
		afterEditCell:function(rowid, cellname, value, iRow, iCol){
			//gridEditBalanceAssit(rowid);
			balanceAssistGridRow = iRow;
			balanceAssistGridCol = iCol;
			balanceAssistRowId = rowid;
		},
		afterSaveCell:function(rowid, cellname, value, iRow, iCol){
			if('beginJie'==cellname||'beginDai'==cellname||'initBalance'==cellname||'yearBalance'==cellname){
				var row = jQuery(this).getRowData(rowid);
				var beginJie = row['beginJie']*1;
				var beginDai = row['beginDai']*1;
				var initBalance = row['initBalance']*1;
				var yearBalance = row['yearBalance']*1;
				var rowdata={};
				if('beginJie'==cellname||'beginDai'==cellname||'yearBalance'==cellname){
					initBalance = beginJie-beginDai+yearBalance
				} else {
					yearBalance = initBalance-beginJie+beginDai;
				}
				rowdata['beginJie'] = beginJie;
				rowdata['beginDai'] = beginDai;
				rowdata['initBalance'] = initBalance;
				rowdata['yearBalance'] = yearBalance;
				jQuery(this).setRowData(rowid,rowdata);
			}
		},
		 gridComplete:function(){
           if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"balanceAssist_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("balanceAssist_gridtable");
       	} 
    });
    jQuery(balanceAssistGrid).jqGrid('bindKeys');
	//balanceAssistLayout.resizeAll();
    
    var assistArr = new Array();
	var assistsData = new Array();
	var assistsMap = new Map();
	var assists = new Array();
	assists.push('assistsAbstract');
	<c:if test="${copyBalanceFlag=='true'}">
		$('input[id^=balanceAssistTypeTreeSelect_]').each(function(){
			$(this).attr('disabled','true');
		});	
	</c:if>
	
	
  	<c:forEach items="${assistTypeList}" var="asst" varStatus="vs">
	var assistData,thisIndex = ${vs.count};
	assistsMap.put('${asst.typeCode}',${vs.count}-1);
	assists.push('${asst.typeCode}');
	
  	jQuery("#balanceAssistTypeTreeSelect_${asst.typeCode}").treeselect({
		dataType:"sql",
		optType:"multi",
		sql:"select rtrim(ltrim(${asst.bdInfo.tablePkName})) as id, rtrim(ltrim(${asst.bdInfo.tableDisplayName})) as name from ${asst.bdInfo.tableName} where 1=1 and orgId='"+"${fns:userContextParam('orgCode')}"+"' and kjYear='"+"${fns:userContextParam('periodYear')}"+"'",
		exceptnullparent:false,
		lazy:false,
		callback : {
			afterCheck :function(treeId,ids,names){
				$('#balanceAssist_gridtable').clearGridData();
				var assistCode = '${asst.typeCode}';
				//alert(assistCode);
				var idArr = ids.split(",");
				var nameArr = names.split(",");
				assistData = new Array();
				for(var idIndex=0;idIndex<idArr.length;idIndex++){
					assistData.push(nameArr[idIndex]+","+idArr[idIndex]); 
				}
				//alert(assistData);
				assistArr[assistsMap.get(assistCode)] = assistData;
				//alert(assistArr);
				assistsData = new Array();
				//alert(assistsMap.get(assistCode));
				
				descartesResult(0,null,assistArr,assistsData);
				var assistKeys = assistsMap.keys();
				for(var tt=0;tt<assistsData.length;tt++){
					var rowIndex = addAssitRowGrid('');
					var rowdata = {};
					for(var tdi=0;tdi<assistKeys.length;tdi++){
						var thisAssistValue = tdi<assistsData[tt].length?assistsData[tt][tdi]:"";
						var thisAssistValueName = thisAssistValue.split(",")[0];
						var thisAssistValueId = thisAssistValue.split(",")[1];
						rowdata['assist.assist_typeValue_'+assistKeys[tdi]]=thisAssistValueId;
						rowdata['assist.assist_typeName_'+assistKeys[tdi]]=thisAssistValueName;
					}
					$('#balanceAssist_gridtable').setRowData(rowIndex,rowdata);
				}
			}
		}
	});
  	</c:forEach>
  	});
	
	function descartesResult(arrIndex,aresult,arrayContainer,dataContainer)
	{
	  if(arrIndex>=arrayContainer.length) {
		  dataContainer.push(aresult);
		  return;
	  };
	  var aArr=arrayContainer[arrIndex];
	  if(!aresult) {
		  aresult=new Array();
	  }
	  for(var i=0;i<aArr.length;i++){
	    var theResult=aresult.slice(0,aresult.length);
	    
	    theResult.push(aArr[i]);
	    descartesResult(arrIndex+1,theResult,arrayContainer,dataContainer);
	  }
	}
  	
	
  	function addAssitRowGrid(rowIndex){
  		var locationIndex = '';
  		if(rowIndex == ''){
  			var rowIds = $("#balanceAssist_gridtable").getDataIDs();
  			if(rowIds.length == 0){
  				rowIndex=1;
  			} else {
  				rowIndex = parseInt(rowIds[rowIds.length-1])+1;
  			}
  			locationIndex = 'last';
  		}
  		jQuery('#balanceAssist_gridtable').jqGrid('addRowData', rowIndex, {lend:0,loan:0}, locationIndex);
  		<c:forEach items="${assistTypeList}" var="asst">
  		jQuery('#balanceAssist_gridtable').find('#'+rowIndex).find("td[aria-describedby='balanceAssist_gridtable_assist.assist_typeCode_${asst.typeCode}']").text('${asst.typeCode}');
  	    </c:forEach>
  	    jQuery('#balanceAssist_gridtable').find('#'+rowIndex).find("td[aria-describedby=balanceAssist_gridtable_num]").text(rowIndex);
  	    resetRowNum();
  	    return rowIndex;
  	}
  	function delAssitRowGrid(){
		var selectedRowIds = $("#balanceAssist_gridtable").jqGrid("getGridParam","selarrrow");  
		var len = selectedRowIds.length;  
		for(var i = 0;i < len ;i ++) {  
			$("#balanceAssist_gridtable").jqGrid("delRowData", selectedRowIds[0]);  
		}
		resetRowNum();
  	}
  	function resetRowNum(){
  		var rowIds = $("#balanceAssist_gridtable").getDataIDs();
  		for(var i = 0 ; i < rowIds.length; i++){
  			$('#balanceAssist_gridtable').setCell(rowIds[i],'num',i+1);
  		}
  	}
  	function saveBalanceAssist(){
  		$("#balanceAssist_gridtable").saveCell(balanceAssistGridRow,balanceAssistGridCol);
  		 var rowIds = $("#balanceAssist_gridtable").getDataIDs();
	     var assistBalances={};
  		 for(var i = 0 ; i < rowIds.length; i++){
  			var assistBalance={};
  			var rowData = $('#balanceAssist_gridtable').jqGrid('getRowData',rowIds[i]);
  			assistBalance['beginJie'] = rowData['beginJie'].trim();
  			assistBalance['beginDai'] = rowData['beginDai'].trim();
  			assistBalance['initBalance'] = rowData['initBalance'].trim();
  			assistBalance['yearBalance'] = rowData['yearBalance'].trim();
  			assistBalance['num'] = rowData['num'].trim();
  			<c:forEach items="${assistTypeList}" var="asst">
	  			assistBalance['assist_typeValue_${asst.typeCode}'] = rowData['assist.assist_typeValue_${asst.typeCode}'].trim();
	  			assistBalance['assist_typeCode_${asst.typeCode}'] = rowData['assist.assist_typeCode_${asst.typeCode}'].trim();
	  			assistBalance['assist_typeName_${asst.typeCode}'] = rowData['assist.assist_typeName_${asst.typeCode}'].trim();
	  			assistBalance['assist_assId_${asst.typeCode}'] = rowData['assist.assist_assId_${asst.typeCode}'].trim();
  			</c:forEach>
  			assistBalances['assistBalance'+i] = assistBalance;
  		 }  
  		//alert(json2str(assistBalances));
  		jQuery.ajax({
		    url: 'saveBalanceAssist?balanceId=${balanceId}',
		    data:{
		    	'assistBalanceJson':json2str(assistBalances)
		    	},
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    	alertMsg.error(data.message);
		    },
		    success: function(data){
		    	alertMsg.correct(data.message);
		    	$.pdialog.close('editBalance');
		    	var checkBalanceDialog = $("body").data('checkBalanceList');  
		        if(checkBalanceDialog){  
		        	checkBalanceGridReload();  
		        }
		    	balanceGridReload();
		    }
		});
  	}
  	
	function gridEditBalanceAssit(rowIndex){
		/* jQuery('#balanceAssist_gridtable').find('#'+rowIndex).find("input[name='beginJie']:visible").unbind('blur').bind('blur',function(){
			var value = $(this).val();
			var rowdata = {};
			var rowDataTemp = $('#balanceAssist_gridtable').jqGrid('getRowData',rowIndex);
			rowdata['beginDai'] = value;
			$('#balanceAssist_gridtable').setRowData(rowIndex,rowdata);
		}); */
		
		<c:forEach items="${assistTypeList}" var="asst">
		jQuery('#balanceAssist_gridtable').find('#'+rowIndex).find("input[name='assist.assist_typeName_${asst.typeCode}']:visible").autocomplete("autocompleteBySql",{
	  		width : 270,
	  		multiple : false,
	  		multipleSeparator: "", 
	  		autoFill : false,
	  		matchContains : true,
	  		matchCase : true,
	  		dataType : 'json',
	  		parse : function(json) {
	  			var data = json.autocompleteResult;
	  			if (data == null || data == "") {
	  				var rows = [];
	  				rows[0] = {
	  					data : "没有结果",
	  					value : "",
	  					result : ""
	  				};
	  				return rows;
	  			} else {
	  				var rows = [];
	  				for ( var i = 0; i < data.length; i++) {
	  					rows[rows.length] = {
	  						data : data[i].id+','+data[i].name,
	  						value : data[i].id,
	  						result : data[i].name
	  					};
	  				}
	  				return rows;
	  			}
	  		},
	  		extraParams : {
	  			cloumns : "${asst.bdInfo.tablePkName},${asst.bdInfo.tableDisplayName}",
	  			sql:"select rtrim(ltrim(${asst.bdInfo.tablePkName})) as id, rtrim(ltrim(${asst.bdInfo.tableDisplayName})) as name from ${asst.bdInfo.tableName} where 1=1 and orgId='"+"${fns:userContextParam('orgCode')}"+"' and kjYear='"+"${fns:userContextParam('periodYear')}"+"'"
	  		},
	  		formatItem : function(row) {
	  			return row;
	  		},
	  		formatResult : function(row) {
	  			return row;
	  		}
	  	});
		jQuery('#balanceAssist_gridtable').find('#'+rowIndex).find("input[name='assist.assist_typeName_${asst.typeCode}']:visible").result(function(event, row, formatted) {
	  		if (row == "没有结果") {
	  			return;
	  		}
	  		jQuery('#balanceAssist_gridtable').find('#'+rowIndex).find("td[aria-describedby='balanceAssist_gridtable_assist.assist_typeValue_${asst.typeCode}']").html(row.split(',')[0]); 
	  	});
	  	</c:forEach>
  	}
	function invBalanceAssistDetailFun(typeCode,elem){
	<c:forEach items="${assistTypeList}" var="asst">
		if(typeCode == '${asst.typeCode}' ){
		var cloumns = "${asst.bdInfo.tablePkName},${asst.bdInfo.tableDisplayName}";
		var sql="select rtrim(ltrim(${asst.bdInfo.tablePkName})) as id, rtrim(ltrim(${asst.bdInfo.tableDisplayName})) as name from ${asst.bdInfo.tableName} where 1=1 and orgId='"+"${fns:userContextParam('orgCode')}"+"' and kjYear='"+"${fns:userContextParam('periodYear')}"+"'";
	/* 	alert(sql${asst.typeCode});
		alert(cloumns${asst.typeCode}); */
		var thisId = jQuery(elem).attr('id');
		jQuery(elem).removeAttr('id');
		$(elem).combogrid({
			url : '${ctx}/comboGridSqlList',
			queryParams:{
				sql : sql,
				cloumns : cloumns
			},
			colModel : [{
				'columnName' : 'ID',
				'width' : '30',
				'label' : 'Id'
			},
			{
				'columnName' : 'NAME',
				'width' : '70',
				'label' : '名称'
			}
			],
			select : function(event, ui) {
				$(this).val(ui.item['NAME']);
				$(this).parent().parent().find("td[aria-describedby='balanceAssist_gridtable_assist.assist_typeValue_${asst.typeCode}']").html(ui.item['ID']);
			//	$this${asst.typeCode}.val(ui.item[cloumns]);
				$(this).attr("id",thisId);
				return false;
			}
		});
		}
	</c:forEach>
	}
	function invBalanceAssistComboGrid(elem){
		var name = $(elem).attr('name');
		var mm = name.split('assist.assist_typeName_')[1];
		//alert(mm);
		switch(mm){
		<c:forEach items="${assistTypeList}" var="asst">
		case '${asst.typeCode}':
			invBalanceAssistDetailFun(mm,elem);
			break;
		</c:forEach>
		}
	}
	
	
</script>

<div class="page">
	<div class="pageContent">
		<form id="balanceAssistForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="385">
				<div class="unit">
				<s:textfield key='account.acctCode' name="balance.account.acctCode" disabled="true"/>
				<s:textfield key='account.acctname' name="balance.account.acctname" disabled="true"/>
				</div>
				<div class="unit">
				<s:textfield key='balance.beginJie' name="balance.beginJie" disabled="true"/>
				<s:textfield key='balance.beginDai' name="balance.beginDai" disabled="true"/>
				</div>
				<div class="unit">
				<s:textfield key='balance.yearBalance' name="balance.yearBalance" disabled="true"/>
				<s:textfield key='balance.initBalance' name="balance.initBalance" disabled="true"/>
				</div>
				<div class="unit" style="">
				<hr>
				</div>
				<div class="unit">
				<span>
					<label>辅助核算:</label>
				</span>
				</div>
				
				
				<c:forEach items="${assistTypeValueList}" var="asst" varStatus="asstVar">
				<c:if test="${asstVar.count%2==1 }">
				<div class="unit" style="">
				</c:if>
				<span>
					<label>${asst.typeName }:</label>
				</span>
				<span>
					<label>
						<input type="text" value="${asst.assistName }" id="balanceAssistTypeTreeSelect_${asst.typeCode }"/>
						<input type="hidden" value="${asst.assistValue }" id="balanceAssistTypeTreeSelect_${asst.typeCode }_id"/>
					</label>
				</span>
				<c:if test="${asstVar.last ||asstVar.count%2==0}">
				</div>
				</c:if>
				</c:forEach>
				
			</div>
				 
			<table id="balanceAssist_gridtable"></table>
			<div class="formBar">
			<c:if test="${copyBalanceFlag!='true'}">
				<div class="buttonActive" style="float:left">
					<div class="buttonContent">
						<button type="Button" onclick="addAssitRowGrid('')"><fmt:message key="button.add" /></button>
					</div>
				</div>
				<div class="buttonActive" style="float:left;margin-left:5px;">
					<div class="buttonContent">
						<button type="Button" onclick="delAssitRowGrid()"><fmt:message key="button.delete" /></button>
					</div>
				</div>
				
			</c:if>
				<div class="buttonActive" style="float:right;">
					<div class="buttonContent">
						<button type="Button" onclick="$.pdialog.closeCurrent();"><s:text name="button.cancel" /></button>
					</div>
				</div>
				<c:if test="${copyBalanceFlag!='true'}">
				<div class="buttonActive" style="float:right;margin-right:5px;">
					<div class="buttonContent">
						<button type="button" id="savelink" onclick="saveBalanceAssist()"><s:text name="button.save" /></button>
					</div>
				</div>
				</c:if>
			</div>
		</form>
	</div>
	
</div>

