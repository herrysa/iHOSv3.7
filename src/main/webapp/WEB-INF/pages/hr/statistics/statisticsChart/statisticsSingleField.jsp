
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var countValue="";
	var sumValue="";
	var avgValue="";
	var maxValue="";
	var minValue="";
	jQuery(document).ready(function() { 
		if("${statisticsDetail.shijianName}"){
   			jQuery("#statisticsSingleField_date")[0].innerHTML="${statisticsDetail.shijianName}";
   		}else{
   			jQuery("#statisticsSingleField_datelabel").hide();
   		}
   		if(!"${statisticsDetail.deptFK}"){
   			jQuery("#statisticsSingleField_deptlabel").hide();
   		}
		jQuery.ajax({
	           url: 'getSingleFieldTableList',
	           data: {statisticsCode:"${statisticsCode}"},
	           type: 'post',
	           dataType: 'json',
	           async:false,
	           error: function(data){
	           },
	           success: function(data){
	        	   var bdInfos = data.bdInfos;
	        	   if(bdInfos){
	        		   for(var num = 0;num< bdInfos.length;num++){
	        			   var bdInfo = bdInfos[num];
	   	        			var tableCnName = bdInfo.bdInfoName;
	   	        			var tableId = bdInfo.bdInfoId;
	   	        			var subset = bdInfo.tableAlias;
	   	        			var deptFK = bdInfo.remark;
	   	        			var mainTableId = bdInfo.entityName;
	   	        			jQuery("#statisticsSingleFieldTableList").append("<option title='"+mainTableId+"' value='"+tableId+"' class='"+subset+"' name='"+deptFK+"'>"+tableCnName+"</option>");
	   	        	 	} 
	        	   }
	           }
	       });
		jQuery("#statisticsSingleField_filter").unbind( 'click' ).bind("click",function(){
			var tableId=jQuery("#statisticsSingleFieldTableList").find("option:selected").val();
			if(!tableId){
				alertMsg.error("请选择一个统计表。");
 	      		return;
			}
			var fieldId=jQuery("#statisticsSingleFieldLeftsel").find("option:selected").val();
			if(!fieldId){
				alertMsg.error("请选择一个统计字段。");
		      		return;
			}
			var subset=jQuery("#statisticsSingleFieldTableList").find("option:selected").attr('class');
			var deptFK=jQuery("#statisticsSingleFieldTableList").find("option:selected").attr('name');
			var shijianFK="${statisticsDetail.shijianFK}";
		    var url = "editStatisticsSingleField?id="+tableId;
		    url=url+"&statisticsCode="+"${statisticsCode}";
		    url=url+"&filterFieldName="+"deptFK";
		    url=url+"&subset="+subset;
		    url=url+"&shijianFK="+shijianFK;
		    var winTitle='<s:text name="singleField.filterEdit"/>';
			$.pdialog.open(url,'editSingleFieldFilter',winTitle, {mask:true,width : 700,height : 600});
	   	});
		jQuery("#statisticsSingleField_calculate").unbind( 'click' ).bind("click",function(){
			singleFieldDataLoad();
		});
  	});
	function statisticsSingleFieldTableChange(value){
		jQuery("#statisticsSingleField_resultSumValue").val("");
		jQuery("#statisticsSingleField_resultAvgValue").val("");
		jQuery("#statisticsSingleField_resultMinValue").val("");
		jQuery("#statisticsSingleField_resultMaxValue").val("");
		jQuery("#statisticsSingleField_countValue").val("");
		jQuery("#statisticsSingleField_deptIds").val("");
		jQuery("#statisticsSingleField_expression").val("");
		jQuery("#statisticsSingleField_gridAllDatas").val("");
		jQuery("#statisticsSingleFieldLeftsel option").remove();
		if(value=="--"){
			return;
		}
		var subSetType="";
		var subset=jQuery("#statisticsSingleFieldTableList").find("option:selected").attr('class');
		if(subset=="1"){
			subSetType="subSet";
		}
		   jQuery.ajax({
	           url: 'getStatisticsTargetFieldInfoList',
	           data: {subSetType:subSetType,bdInfoId:value,oper:"singleFiled"},
	           type: 'post',
	           dataType: 'json',
	           async:false,
	           error: function(data){
	           },
	           success: function(data){
	        	 for(var num = 0;num< data.fieldInfoes.length;num++){
	        			jQuery("#statisticsSingleFieldLeftsel").append("<option value='"+data.fieldInfoes[num].fieldId+"' name='"+data.fieldInfoes[num].dataType+"'>"+data.fieldInfoes[num].fieldName+"</option>");
	        	 	} 
	           }
	       });
	}
	function statisticsSingleFieldLeftselChange(value){
		jQuery("#statisticsSingleField_resultSumValue").val("");
		jQuery("#statisticsSingleField_resultAvgValue").val("");
		jQuery("#statisticsSingleField_resultMinValue").val("");
		jQuery("#statisticsSingleField_resultMaxValue").val("");
		jQuery("#statisticsSingleField_countValue").val("");
		var dataType= jQuery("#statisticsSingleFieldLeftsel").find("option:selected").attr('name');
		if(dataType=='4'){
			jQuery("#statisticsSingleField_resultSumSpan").hide();
			jQuery("#statisticsSingleField_resultAvgSpan").hide();
		}else{
			jQuery("#statisticsSingleField_resultSumSpan").show();
			jQuery("#statisticsSingleField_resultAvgSpan").show();
		}
		singleFieldDataLoad();
	}
	function singleFieldDataLoad(){
		var tableId=jQuery("#statisticsSingleFieldTableList").find("option:selected").val();
		if(!tableId){
			alertMsg.error("请选择一个统计表。");
	      		return;
		}
		var mainTableId=jQuery("#statisticsSingleFieldTableList").find("option:selected").attr('title');
		var subset=jQuery("#statisticsSingleFieldTableList").find("option:selected").attr('class');
		var deptFK=jQuery("#statisticsSingleFieldTableList").find("option:selected").attr('name');
		var fieldId=jQuery("#statisticsSingleFieldLeftsel").find("option:selected").val();
		if(!fieldId){
			alertMsg.error("请选择一个统计字段。");
	      		return;
		}
		countValue="";
		sumValue="";
		avgValue="";
		maxValue="";
		minValue="";
		expression="";
		gridAllDatas="";
		var hisTime =jQuery("#statisticsSingleField_search_snapTime").val();
		var deptIds= jQuery("#statisticsSingleField_search_dept_id").val();
		var searchDateFrom= jQuery("#statisticsSingleField_search_date_from").val();
		var searchDateTo= jQuery("#statisticsSingleField_search_date_to").val(); 
		var shijianFK="${statisticsDetail.shijianFK}";
		var expression="";
		var gridAllDatas="";
		
		jQuery.ajax({
	           url: 'statisticsTargetGridEdit',
	           data: {oper:"singleField",deptIds:deptIds,expression:expression,gridAllDatas:gridAllDatas,bdInfoId:mainTableId,id:fieldId,deptFK:deptFK,searchDateFrom:searchDateFrom,searchDateTo:searchDateTo,shijianFK:shijianFK,hisTime:hisTime},
	           type: 'post',
	           dataType: 'json',
	           async:false,
	           error: function(data){
	           },
	           success: function(data){
	        	   if(data.message!="查询成功"){
	               		alertMsg.error(data.message);
	                	return;
	               }
	        	   countValue=data.chartXMLMap["count"];
	        	   sumValue=data.chartXMLMap["sum"];
	        	   avgValue=data.chartXMLMap["avg"];
	        	   maxValue=data.chartXMLMap["max"];
	        	   minValue=data.chartXMLMap["min"];		        	
	           }
	       });
		jQuery("#statisticsSingleField_resultSumValue").val(sumValue);
		jQuery("#statisticsSingleField_resultAvgValue").val(avgValue);
		jQuery("#statisticsSingleField_resultMinValue").val(minValue);
		jQuery("#statisticsSingleField_resultMaxValue").val(maxValue);
		jQuery("#statisticsSingleField_countValue").val(countValue);
	}
	function getSingleFieldFilterDeptHisNode(){
		var hisTime =jQuery("#statisticsSingleField_search_snapTime").val();
		jQuery("#statisticsSingleField_search_dept").treeselect({
			dataType:"url",
			optType:"multi",
			url:'getHrDeptHisNode?hisTime='+hisTime,
			exceptnullparent:false,
			selectParent:false,
			minWidth:'230px',
			lazy:false
		});
	}
</script>
<div class="page" id="statisticsSingleField_page">
	<div class="pageHeader" id="statisticsSingleField_pageHeader">
			<div class="searchBar">
			<div class="searchContent">
			<form id="statisticsSingleField_search_form" class="queryarea-form" >
					<label class="queryarea-label" id="statisticsSingleField_snapTimelabel">
						<s:text name='历史时间'/>:
					 	<input type="text"  id="statisticsSingleField_search_snapTime"  class="Wdate" style="height:15px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					</label>
					<label class="queryarea-label" id="statisticsSingleField_deptlabel">
						<s:text name='statisticsSingleField.dept'/>:
						<input type="hidden" id="statisticsSingleField_search_dept_id">
					 	<input type="text" id="statisticsSingleField_search_dept" onfocus="getSingleFieldFilterDeptHisNode()"/>
					</label>
					<label class="queryarea-label" id="statisticsSingleField_datelabel">
						<span id="statisticsSingleField_date"></span>:
						<input type="text"	id="statisticsSingleField_search_date_from" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'statisticsSingleField_search_date_to\')}'})">
						<s:text name='至'/>
					 	<input type="text"	id="statisticsSingleField_search_date_to" style="width:80px;height:15px" class="Wdate"  onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'statisticsSingleField_search_date_from\')}'})">
					</label>
						<%-- <div class="buttonActive" style="float:right">
							<div class="buttonContent">
								<button type="button" onclick="singleFieldDataLoad()"><s:text name='button.search'/></button>
							</div>
						</div> --%>
							<div class="buttonActive" style="float:right;">
								<div class="buttonContent">
									<button type="button" onclick="singleFieldDataLoad()"><s:text name='button.search'/></button>
								</div>
							</div>
				</form>
				</div>
			</div>
	</div>
	<div class="pageContent">





	<div class="panelBar" id="statisticsSingleField_toolbuttonbar">
		<ul class="toolBar">
<!-- 			<li>  -->
<!-- 				<a id="statisticsSingleField_calculate" class="changebutton"  href="javaScript:"><span>统计</span></a> -->
<!-- 			</li> -->
			<li> 
				<a id="statisticsSingleField_filter" class="changebutton"  href="javaScript:"><span><s:text name="statisticsSingleField.addFilter" /></span></a>
			</li> 
		</ul> 
	</div>
	<div style="margin: 0 auto;width:400px">
	<br><br><br><br>
		<table>
		<tr>
		<td>
		<span>
		<label>统计表</label>
		<select id="statisticsSingleFieldTableList" onchange="statisticsSingleFieldTableChange(this.value)" style="font-size:9pt;">
		<option value ="">--</option>
		</select>
		</span>
		</td>
		<td>
		<label>&nbsp;&nbsp;&nbsp;统计类型</label>
		</td>
		</tr>
		<tr>
			<td rowspan="6">
		<select multiple="multiple"  style="height: 300px; width: 150px;font-size:9pt;" id="statisticsSingleFieldLeftsel" onchange="statisticsSingleFieldLeftselChange(this.value)">
        </select>		
        	</td>
        	<td >
        	<span>
        	<label>&nbsp;&nbsp;&nbsp;数&nbsp;&nbsp;&nbsp;量</label>
        	<input id="statisticsSingleField_countValue"/>
        	</span>
        	</td>
        	</tr>
        	<tr>
        	<td >
        	<span>
        	<label>&nbsp;&nbsp;&nbsp;最大值</label>
        	<input id="statisticsSingleField_resultMaxValue"/>
        	</span>
        	</td>
        	</tr>
        	<tr>
        	<td>
        	<span>
        	<label>&nbsp;&nbsp;&nbsp;最小值</label>
        	<input id="statisticsSingleField_resultMinValue"/>
        	</span>
        	</td>
        	</tr>
        	<tr>
        	<td>
        	<span id="statisticsSingleField_resultAvgSpan">
        	<label>&nbsp;&nbsp;&nbsp;平均值</label>
        	<input id="statisticsSingleField_resultAvgValue"/>
        	</span>
        	</td>
        	</tr>
        	<tr>
        	<td>
        	<span id="statisticsSingleField_resultSumSpan">
        	<label>&nbsp;&nbsp;&nbsp;总&nbsp;&nbsp;&nbsp;值</label>
        	<input id="statisticsSingleField_resultSumValue"/>
        	</span>
        	</td>
        	</tr>
        </table>	
	</div>
	</div>
</div>
