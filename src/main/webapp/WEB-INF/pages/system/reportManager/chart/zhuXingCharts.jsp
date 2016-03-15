<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="commandScript.title" /></title>
<meta name="heading" content="<fmt:message key='commandScript.title'/>" />
<link rel="stylesheet" href="${ctx}/fusionCharts/css/Style.css" type="text/css" />


<!-- -----------------------------------导入脚本START----------------------------------- -->
<script language="JavaScript" src="${ctx}/fusionCharts/JSClass/FusionCharts.js"></script>
<script language="javascript" type="text/javascript" src="${ctx}/scripts/DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript" src="${ctx}/dwz/js/jquery.validate.js"></script>
<script type="text/javascript">
var meaSure=false;
jQuery(document).ready(function(){
	if('${chartStr[0]}'==null||'${chartStr[0]}'==''){
		jQuery("span[name=wu]").each(function(){ 
			jQuery(this).html("没有可显示数据!");
		});
	}else{
		jQuery("span[name=wu]").each(function(){ 
			jQuery(this).html("");
		});
	}
	jQuery("#zhuXingQueryLink").click(function(){
		var url = "";
		var action = jQuery("#zhuXingChartForm").attr("action");
		var type = jQuery("#type").val();
		var ckey = jQuery("#ckey").val();
		var conditionO = jQuery("#conditionO").val();
		var conditionT = jQuery("#conditionT").val();
		var deptId = jQuery("#deptId").val();
		var deptKey = jQuery("#deptKey").val();
		url = action+"&type="+type+"&ckey="+ckey+"&conditionO="+conditionO+"&conditionT="+conditionT+"&deptId="+deptId+"&deptKey="+deptKey;
		navTab.reload(url, {title : "New Tab",fresh : false,data : {}});
	});
});

</script>
<!-- -----------------------------------导入脚本END  ----------------------------------- -->


</head>
<div class="pageHeader">
<form id="zhuXingChartForm" action="zhuXingCharts?popup=true" class="pageForm required-validate" onsubmit="return validateCallback(this,formCallBack);">

<!-- -----------------------------------隐藏域START-------------------------------------- -->
<!-- -----------------显示的图形类型(START)----------------- -->
<input type="hidden" id="type" value="zhuXing">
<!-- -----------------显示的图形类型(END)----------------- -->

<!-- -----------------显示的图形Ckey值(START)----------------- -->
<input type="hidden" id="ckey" value="B8">
<!-- -----------------显示的图形Ckey值(END)----------------- -->

<!-- -----------------科室ID(START)----------------- -->
<input type="hidden" id="deptId" name="deptId">
<!-- -----------------科室ID(END)----------------- -->

<!-- -----------------指标(START)----------------- -->
<input type="hidden" id="deptKey" name="deptKey">
<!-- -----------------指标(END)----------------- -->

<!-- -----------------------------------隐藏域END-------------------------------------- -->


<div class="searchBar"><table width="100%" border="0" cellspacing="0" cellpadding="3" ><tr><td  height="40"><table class="searchContent"><tr><td>
		
		
		<!-- -----------------查询条件START(注:复制该部分时应将-弹出框部分同时复制)------------------ -->
		<!-- -----------------核算期间(条件START)------------------ -->
			<font color="#15428B" size="16" face="宋体"><b>核算期间：</b>
			从<input type="text" id="conditionO"  onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})" class="required"  value="${currentPeriod}"/>
			到<input type="text" id="conditionT"  onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"  value="${currentPeriod1}"/>
			</font>
		<!-- -----------------核算期间(条件END)------------------ -->
		
		<!-- -----------------科室(条件START)------------------ -->
		<font color="#15428B" size="16" face="宋体"><b>科室：</b></font><input type="text" id="deptName" onClick="choiceDept()" size="30" readonly="readonly"/>
		<!-- -----------------科室(条件END)------------------ -->
		
		<!-- -----------------指标(条件START)------------------ -->
		<font color="#15428B" size="16" face="宋体"><b>指标：</b></font><input type="text" id="deptKeyName" onClick="choiceDeptKey()"  size="40" readonly="readonly" />
		<!-- -----------------指标(条件END)------------------ -->
		
		<!-- -----------------------------------查询条件END-------------------------------------- -->
		
</td></tr></table></td><td><ul><li><div class="buttonActive"><div class="buttonContent">

		<button type="button" id="zhuXingQueryLink" >&nbsp;&nbsp;<fmt:message key='button.search' />&nbsp;&nbsp;</button>
		
</div></div></li></ul></td></tr></table></div></form></div>
	
	
	
	
	
	<!-- -----------------------------------图表START----------------------------------- -->
		
	<table width="100%" border="0" cellspacing="0" cellpadding="3">
	    <tr> 
	    <td valign="top" class="text" align="center"> <div id="chartdiv3" align="center"> </div>
		    <c:if  test="${chartStr[0]!=null&&chartStr[0]!=''}">
		      <script type="text/javascript">
		      		var chart = new FusionCharts("${ctx}/fusionCharts/Charts/MSColumn${c3dS[0]}D.swf", "ChartId", "800", "350", "0", "0");
				   chart.setDataXML("${chartStr[0]}");
				   chart.render("chartdiv3");
				</script>
			</c:if>
			<span name="wu"></span>
		</td>
	  </tr>
	</table>
	
	<!-- -----------------------------------图表END----------------------------------- -->




	<!-- -----------------------------------弹出框START----------------------------------- -->

	<!-- -----------------选择科室(弹出框START)----------------- -->
	
	<sj:dialog id="choiceDeptDialog" width="360" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}" >
		<table>
		<tr>
		<td>
		<input type="text" id="quickDeptSelect" name="quickDeptSelect" size="16"/><input type="button" onclick="quickDeptSelect()" value="搜"/>
		选择科室：
		<select id="dept" ondblclick="add()" name="selectAll" multiple="true" style="width: 150px; height: auto" size="14">
			<c:forEach items="${departments}" var="dept">
				<c:if test="${dept.name!=null }"><option value="${dept.departmentId}">${dept.name}</option></c:if>
			</c:forEach>
		</select></td>
		<td valign="middle" >
		<input id="addBut" type="button"  class="btn"  value=">" onclick="add()"/>
		<input id="delBut" type="button" value="&lt;" onclick="del()"/>
		<input id="allAddBut" type="button" value=">>" onclick="addAll()"/>
		<input id="allDelBut" type="button" value="&lt;&lt;" onclick="delAll()"/>
		</td>
		<td>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		已选科室：
		<select id="deptd" ondblclick="del()" multiple="true" style="width:150px;" name="selectedAll" size="14">
		
		</select>
		</td>
		</tr>
		</table>
	</sj:dialog>
<script type="text/javascript">
	/* 选择科室 */
function choiceDept(){
	var importDialog = jQuery("#choiceDeptDialog");
	importDialog.dialog('option', 'buttons', { "<fmt:message key='dialog.ok'/>": function() {
		okDeptButton();
		jQuery( this ).dialog( "close" );
		importForm.target = "check_file_frame";
		importForm.submit();
	},
	"<fmt:message key='dialog.cancel'/>": function() {
		jQuery( this ).dialog( "close" );
	}
	}
	);
		importDialog.dialog("open");
} 
function okDeptButton(){
 	var alreadyValue=new Array();
	var alreadyText =new Array();
	jQuery("#deptd").find("option").each(function(){
		alreadyValue.push(jQuery(this).attr("value"));
		alreadyText.push(jQuery(this).text());
	});
	//alert(alreadyText);
	jQuery("#deptName").attr("value",alreadyText);
	jQuery("#deptId").attr("value",alreadyValue);
	//alert(jQuery("#deptId").attr("value"));
	
	
	var deptNameArr=jQuery("#deptKeyName").attr("value").split(",");
	var deptIdArr=jQuery("#deptKey").attr("value").split(",");
	if((alreadyValue.length>1)&&(deptIdArr.length>1)){
		jQuery("#deptKeyName").attr("value","");
		jQuery("#deptKey").attr("value","");
		alert("科室和指标不能同时选择多个,科室选择多个时,指标只能选一个,反之相同(请重新选择指标)!");
	}
	
	
	jQuery('#mybuttondialog').dialog('close'); 
};
		
		//指定添加
function add()  {
		var leftValue = new Array();
		var leftText = new Array();
		jQuery("#dept").find("option").each(function(){
			if(jQuery(this).attr("selected")){
				leftValue.push(jQuery(this).attr("value"));
				leftText.push(jQuery(this).text());
			}
		});
		for ( var i = 0; i < leftValue.length; i++) {
			var item = "<option value="+leftValue[i]+">"+ leftText[i]+ "</option>";
			jQuery("#deptd").append(item);
			jQuery("#dept").find("option").each(function() {
					if (jQuery(this).attr("value") == leftValue[i]) {
						jQuery(this).remove();
					}
			});
		}
}
			
			
		//指定删除
function del() {
		var rightValue = new Array();
		var rightText = new Array();
		jQuery("#deptd").find("option").each(function(){
			if(jQuery(this).attr("selected")){
				rightValue.push(jQuery(this).attr("value"));
				rightText.push(jQuery(this).text());
			}
		});
		for ( var i = 0; i < rightValue.length; i++) {
			jQuery("#dept").append("<option value="+rightValue[i]+">"+ rightText[i]+ "</option>");
			jQuery("#deptd").find("option").each(function() {
					if (jQuery(this).attr("value") == rightValue[i]) {
							jQuery(this).remove();
					}
				});
				}
}
		
		
		//全部添加
function addAll() {
		jQuery("#dept").find("option").each(function() {
			jQuery("#deptd").append("<option value="+ jQuery(this).attr("value")+ ">"+ jQuery(this).text()+ "</option>");
			jQuery(this).remove();
		});
	}
		
		
		//全部删除
function delAll() {
		jQuery("#deptd").find("option").each(function() {
			jQuery("#dept").append("<option value="+ jQuery(this).attr("value")+ ">"+ jQuery(this).text()+ "</option>");
			jQuery(this).remove();
		});
	}
		
</script>
	
	<!-- -----------------选择科室(弹出框END)----------------- -->


	<!-- -----------------选择指标(弹出框START)----------------- -->
	
	<sj:dialog id="choiceDeptKeyDialog" width="360" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}" >
		<table>
		<tr>
		<td id="deptN">
		
		</td>
		<td valign="middle" >
		<input id="addKeyBut" type="button"  class="btn"  value=">" onclick="addKey()"/>
		<input id="delKeyBut" type="button" value="&lt;" onclick="delKey()"/>
		<input id="allAddKeyBut" type="button" value=">>" onclick="addKeyAll()"/>
		<input id="allDelKeyBut" type="button" value="&lt;&lt;" onclick="delKeyAll()"/>
		</td>
		<td>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		已选指标：
		<select id="deptKeyAld" ondblclick="delKey()" multiple="true" style="width:150px;" name="selectedall" size="14">
		
		</select>
		</td>
		</tr>
		</table>
	</sj:dialog>
	<script type="text/javascript">
	/* 选择指标*/
	function choiceDeptKey(){
		var importDialog = jQuery("#choiceDeptKeyDialog");
		importDialog.dialog('option', 'buttons', { "<fmt:message key='dialog.ok'/>": function() {
			//alert("fdc");
			okDeptKeyButton();
			jQuery( this ).dialog( "close" );
			importForm.target = "check_file_frame";
			importForm.submit();
		},
		"<fmt:message key='dialog.cancel'/>": function() {
			jQuery( this ).dialog( "close" );
		}
		}
		);
		importDialog.dialog("open");
		var deptN=jQuery("#deptName").val();
		if(deptN==null||deptN==""){
			jQuery("#deptN").html('<input type="text" id="quickKeySelect" name="quickDeptSelect" size="16"/><input type="button" onclick="quickKeySelect()" value="搜"/>选择指标：<select id="deptKeyAl" ondblclick="addKey()" name="selectableall" multiple="true" style="width: 150px; height: auto" size="14"><c:forEach items="${orgKeys}" var="allKey"><c:if test="${allKey.fieldTitle!=null }"><option value="${allKey.fieldName}">${allKey.formulaFieldId}&nbsp;&nbsp;${allKey.fieldTitle}</option></c:if></c:forEach></select>');
			meaSure=true;
		}else{
			jQuery("#deptN").html('<input type="text" id="quickKeySelect" name="quickDeptSelect" size="16"/><input type="button" onclick="quickKeySelect()" value="搜"/>选择指标：<select id="deptKeyAl" ondblclick="addKey()" name="selectableall" multiple="true" style="width: 150px; height: auto" size="14"><c:forEach items="${deptKeys}" var="allKey"><c:if test="${allKey.fieldTitle!=null }"><option value="${allKey.fieldName}">${allKey.formulaFieldId}&nbsp;&nbsp;${allKey.fieldTitle}</option></c:if></c:forEach></select>');
			meaSure=false;
		}
		
	} 
	
	
	function okDeptKeyButton(){
		//alert();
	 	var alreadyValue=new Array();
		var alreadyText =new Array();
		jQuery("#deptKeyAld").find("option").each(function(){
			alreadyValue.push(jQuery(this).attr("value"));
			alreadyText.push(jQuery(this).text());
		});
		jQuery("#deptKeyName").attr("value",alreadyText);
		jQuery("#deptKey").attr("value",alreadyValue); 
		
		var deptNameArr=jQuery("#deptName").attr("value").split(",");
		var deptIdArr=jQuery("#deptId").attr("value").split(",");
		if((alreadyValue.length>1)&&(deptIdArr.length>1)){
			jQuery("#deptName").attr("value","");
			jQuery("#deptId").attr("value","");
			alert("科室和指标不能同时选择多个,指标选择多个时,科室只能选一个,反之相同(请重新选择科室)!");
		}
		
		jQuery('#mybuttondialog').dialog('close'); 
	};
	//指定添加
	function addKey() {
		var leftValue = new Array();
		var leftText = new Array();
		jQuery("#deptKeyAl").find("option").each(function(){
			if(jQuery(this).attr("selected")){
				leftValue.push(jQuery(this).attr("value"));
				leftText.push(jQuery(this).text());
			}
		});
		for ( var i = 0; i < leftValue.length; i++) {
			var item = "<option value="+leftValue[i]+">"+ leftText[i]+ "</option>";
			jQuery("#deptKeyAld").append(item);
			jQuery("#deptKeyAl").find("option").each(function() {
					if (jQuery(this).attr("value") == leftValue[i]) {
						jQuery(this).remove();
					}
			});
		}
	}
		
		
	//指定删除
	function delKey() {
		var rightValue = new Array();
		var rightText = new Array();
		jQuery("#deptKeyAld").find("option").each(function(){
			if(jQuery(this).attr("selected")){
				rightValue.push(jQuery(this).attr("value"));
				rightText.push(jQuery(this).text());
			}
		});
		for ( var i = 0; i < rightValue.length; i++) {
			jQuery("#deptKeyAl").append("<option value="+rightValue[i]+">"+ rightText[i]+ "</option>");
			jQuery("#deptKeyAld").find("option").each(function() {
					if (jQuery(this).attr("value") == rightValue[i]) {
							jQuery(this).remove();
					}
				});
				}
	}
	
	
	//全部添加
	function addKeyAll() {
				jQuery("#deptKeyAl").find("option").each(function() {
					jQuery("#deptKeyAld").append("<option value="+ jQuery(this).attr("value")+ ">"+ jQuery(this).text()+ "</option>");
					jQuery(this).remove();
				});
		}
	
	
	//全部删除
	function delKeyAll() {
				jQuery("#deptKeyAld").find("option").each(function() {
					jQuery("#deptKeyAl").append("<option value="+ jQuery(this).attr("value")+ ">"+ jQuery(this).text()+ "</option>");
					jQuery(this).remove();
				});
		}
	</script>
	<!-- -----------------选择指标(弹出框END)----------------- -->


	<!-- -----------------快速搜索科室(START)----------------- -->
	<script type="text/javascript">
		function quickDeptSelect(){
			var inputDept=jQuery("#quickDeptSelect").attr("value");
			jQuery.ajax({
				dataType : 'json',
				url:'quickDeptSelect?quickDeptStr='+inputDept,
			    type: 'POST',
			    error: function(data){
			    	alert(":error");
			    },success: function(data){
			    	var url = data["deptString"];
					$('#dept').html(url);
			    }
			});
		}
	</script>
	<!-- -----------------快速搜索科室(END)----------------- -->
	
	<!-- -----------------快速搜索指标(START)----------------- -->
	<script type="text/javascript">
		function quickKeySelect(){
			var inputKey=jQuery("#quickKeySelect").attr("value");
			jQuery.ajax({
				dataType : 'json',
				url:'quickKeySelect?quickKeyStr='+inputKey+'&meaSure='+meaSure,
			    type: 'POST',
			    error: function(data){
			    	alert(":error");
			    },success: function(data){
			    	var url = data["deptKeyStr"];
			    	jQuery('#deptKeyAl').html(url);
			    	//alert(url);
			    }
			});
		}
	</script>
	<!-- -----------------快速搜索指标(END)----------------- -->
	<!-- -----------------------------------弹出框END----------------------------------- -->
