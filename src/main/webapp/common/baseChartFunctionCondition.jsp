<%@ page language="java" pageEncoding="UTF-8"%>
<script language="JavaScript" src="${ctx}/fusionCharts/JSClass/FusionCharts.js"></script>
<script language="javascript" type="text/javascript" src="${ctx}/scripts/DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript" src="${ctx}/dwz/js/jquery.validate.js"></script>
<script type="text/javascript">
//var meaSure=false;
var choiceId='0';
var chartTitleS = new Array();
function fn(){
	for(var i=0;i<chartTitleS.length;i++){ 
		clearInterval(chartTitleS[i]); 
	}
} 
	//var color1 = "white"; //文字的颜色
	var color2 = "red"; //转换的颜色
	var speed = 1000; //转换速度 (1000 = 1 秒)
	var zhuanDataI = 0;
function changeCharColor(maxValue,currentValue,chartDivId,text) {
	//alert("id:"+sss+"   text:"+text);
	var str ="";
	var fontColor = jQuery("#"+chartDivId+"_color").attr("color");
	if(fontColor!=null&&fontColor!=""&&fontColor!="undefined"){
		/* if(fontColor==color1) fontColor = color2;
		else if(fontColor==color2)  fontColor=color1; */
		str="";
	}else{
		fontColor = color2;
		str = "<b><center><span><font id='"+chartDivId+"_color' face=arial color='" + fontColor + "'>"+text+"</font></span></center><b>";
	}
	//str = "<center><span><font id='"+chartDivId+"_color' face=arial color='" + fontColor + "'>"+text+"</font></span></center>";
	jQuery("#"+chartDivId).html(str);
	(zhuanDataI == text.length) ? zhuanDataI=0 : zhuanDataI++;
}
jQuery(document).ready(function(){
	jQuery("#${random}_QueryLink").click(function(){
		var url = "";
		var action = jQuery("#${random}_ChartForm").attr("action");
		var type = jQuery("#${random}_type").val();
		var ckey = jQuery("#${random}_ckey").val();
		var conditionO = jQuery("#${random}_conditionO").val();
		var conditionT = jQuery("#${random}_conditionT").val();
		var deptId = jQuery("#${random}_deptId").val();
		var deptKey = jQuery("#${random}_deptKey").val();
		
		var orgCode = jQuery("#${random}_chartOrg_id").val();
		var deptName = jQuery("#${random}_deptName").val();
		var deptKeyName = jQuery("#${random}_deptKeyName").val();
		var resultPage = jQuery("#${random}_resultPage").val();
		var chaXun = jQuery("#${random}_chaXunS").val();
		var orgDisplay = jQuery("#${random}_orgDisplay").val();
		var branchDisplay = jQuery("#${random}_branchDisplay").val();
		var branchCode = jQuery("#${random}_chartBranch_id").val();
		chaXun = chaXun.substring(2); 
		url = action+"?popup=true&resultPage="+resultPage+"&chaXun="+chaXun+"&type="+type+"&ckey="+ckey+"&conditionO="+conditionO+"&conditionT="+conditionT+"&deptId="+deptId+"&deptKey="+deptKey+"&deptKeyName="+deptKeyName+"&deptName="+deptName;
		url += "&orgCode="+orgCode+"&orgDisplay="+orgDisplay;
		url += "&branchCode="+branchCode+"&branchDisplay="+branchDisplay;
		url = encodeURI(url);
		//jQuery("#${random}_page").load(url);
		navTab.reload(url, {title : "New Tab",fresh : false,data : {}});
	});
	
	 $('.${random}_chartTitle').click(function(){
		 //var chartContent=$(this).find(".${random}_chartContent");
		 var chartContent=$(this).next();
			if( chartContent.is(':hidden') ) {
				chartContent.slideDown();
			}else{
				chartContent.slideUp();
		        }
			return false;
	    });
	
	$('.${random}_allOpen').click(function(){
    	$('.${random}_chartContent').each(function(){
    		 $('.${random}_chartContent').slideDown();
    	});
    });
    $('.${random}_allClose').click(function(){
		var allChartText=$('.${random}_allClose').eq(0).text();
		if(allChartText=="全部折叠"){
	    	$('.${random}_chartContent').each(function(){
	    		 $('.${random}_chartContent').slideUp();
	    	});
	    	$('.${random}_allClose').html("<a href='#' ><b><font color='#15428B' class='${random}_allClose'>全部展开</font></b></a>");
    	}else{
    		$('.${random}_chartContent').each(function(){
       		 $('.${random}_chartContent').slideDown();
       	});
	    	$('.${random}_allClose').html("<a href='#' ><b><font color='#15428B' class='${random}_allClose'>全部折叠</font></b></a>");
    	}
    });
    var herpType = "${fns:getHerpType()}";
    if(herpType == "M"){
    	var org = "";
    	//单位选择
        jQuery("#${random}_chartOrg").treeselect({
        	dataType:"sql",
        	optType:"multi",
        	sql:"SELECT orgCode id,ISNULL(shortName, orgname) name,parentOrgCode parent FROM T_Org where orgCode <> 'XT'",
        	exceptnullparent:true,
        	selectParent:true,
        	initSelect:"${orgCode}",
        	lazy:true
        });
    } else {
    	jQuery("#${random}_chartOrg_label").hide();
    }
    jQuery("#${random}_chartBranch").treeselect({
    	dataType:"sql",
    	optType:"multi",
    	sql:"SELECT branchCode id,branchName name,'-1' parent FROM t_branch where branchCode <> 'XT'",
    	exceptnullparent:true,
    	selectParent:true,
    	//initSelect:"${orgCode}",
    	lazy:true
    });	
  
});
/* function changeTABLE(id) {
	var contentTABLE = document.getElementById("${random}_content_"+id);
	contentTABLE.style.display = (contentTABLE.style.display == "none") ? "block": "none";
	//document.getElementById("${random}_link"+id).innerText = (contentTABLE.style.display == "none") ? content: content;
}
function changeALLTABLE(id) {
	var arrId =id.split(",");
	var content=document.getElementById("${random}_btQB").innerText;
	for (var i=0;i<arrId.length;i++){
		if(content=="全部展开"){
			var contentTABLE = document.getElementById("${random}_content_"+arrId[i]);
			contentTABLE.style.display = "block";
			jQuery("#${random}_btQB").html( "<b><font color='#15428B'>全部折叠</font></b>");
		}else if(content=="全部折叠"){
			var contentTABLE = document.getElementById("${random}_content_"+arrId[i]);
			contentTABLE.style.display = "none";
			jQuery("#${random}_btQB").html( "<b><font color='#15428B'>全部展开</font></b>");
		}
	}
} */
function dispalySearchName(urlString){
	$.pdialog.open(urlString, "txxs", "报表显示",{width:800,height:600});
}
</script>
	<!-- -----------------------------------弹出框START----------------------------------- -->


<%@ include file="/common/choiceDept.jsp"%>
<%@ include file="/common/choiceDeptKey.jsp"%>


	<!-- -----------------------------------弹出框END----------------------------------- -->


	


	
	
	<!-- -----------------快速搜索指标(START)----------------- -->
	
	<!-- -----------------快速搜索指标(END)----------------- -->
	<!-- -----------------------------------弹出框END----------------------------------- -->
<div class="pageHeader">
<form id="${random}_ChartForm" action="xuanTu">
<input type="hidden" id="${random}_resultPage" value="${resultPage}">
<input type="hidden" id="${random}_chaXunS">

<!-- -----------------------------------隐藏域START-------------------------------------- -->
<!-- -----------------显示的图形类型(START)----------------- -->
<input type="hidden" id="${random}_type" value="${type}">
<!-- -----------------显示的图形类型(END)----------------- -->

<!-- -----------------显示的图形Ckey值(START)----------------- -->
<input type="hidden" id="${random}_ckey" value="${ckey}">
<!-- -----------------显示的图形Ckey值(END)----------------- -->

<!-- -----------------科室ID(START)----------------- -->
<input type="hidden" id="${random}_deptId" value="${deptId}">
<!-- -----------------科室ID(END)----------------- -->

<!-- -----------------指标(START)----------------- -->
<input type="hidden" id="${random}_deptKey" value="${deptKey}">
<!-- -----------------指标(END)----------------- -->
<input type="hidden" id="${random}_orgDisplay" value="${orgDisplay}">
<input type="hidden" id="${random}_branchDisplay" value="${branchDisplay}">
<!-- -----------------------------------隐藏域END-------------------------------------- -->


<div class="searchBar"><table width="100%" border="0" cellspacing="0" cellpadding="3" ><tr><td  height="40"><table class="searchContent"><tr><td>
		
		
		<!-- -----------------查询条件START(注:复制该部分时应将-弹出框部分同时复制)------------------ -->
		<!-- -----------------核算期间(条件START)------------------ -->
		<script>
			var chaXunStr="0,";
		</script>
		<c:forEach var="chaXun" items="${chaXunS}"> 
		<script>
			var chaXunStr = chaXunStr+'${chaXun},';
		</script>
			<c:if test="${chaXun=='1'}">
				<font color="#15428B" size="16" face="宋体"><b>核算期间：</b>
				<input type="text" id="${random}_conditionO"  onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})" class="required" value="${currentPeriod}"/>
				</font>
			</c:if>
			<c:if test="${chaXun=='2'}">
				<font color="#15428B" size="16" face="宋体"><b>核算期间：</b>
				从<input type="text" id="${random}_conditionO"  onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})" class="required" value="${currentPeriod}"/>
				到<input type="text" id="${random}_conditionT"  onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})" value="${currentPeriod1}"/>
				</font>
			</c:if>
			<!-- -----------------核算期间(条件END)------------------ -->
			<!-- -----------------单位(条件START)------------------ -->
			<c:if test="${orgDisplay=='1'}">
					<label id="${random}_chartOrg_label">
						<font color="#15428B" size="16" face="宋体"><b><fmt:message key='hisOrg.orgName' />：</b>
							<input type="text" id="${random}_chartOrg">
							<input type="hidden" id="${random}_chartOrg_id" value="${orgCode}">
						</font>
					</label>
			</c:if>
			<!-- -----------------单位(条件END)------------------ -->
			<!-- -----------------院区(条件START)------------------ -->
			<c:if test="${branchDisplay=='1'}">
					<label id="${random}_chartBranch_label">
						<font color="#15428B" size="16" face="宋体"><b>院区：</b>
							<input type="text" id="${random}_chartBranch">
							<input type="hidden" id="${random}_chartBranch_id" value="${branchCode}">
						</font>
					</label>
			</c:if>
			<!-- -----------------院区(条件END)------------------ -->
			<!-- -----------------科室(条件START)------------------ -->
			<c:if test="${chaXun=='3'}">
				<c:forEach var="chaXun" items="${chaXunS}"> 
					<c:if test="${chaXun=='2'}">
						<!-- 3，4必须有一个单选 -->
						<font color="#15428B" size="16" face="宋体"><b>科室：</b></font><input type="text" id="${random}_deptName"  onClick="choiceDept()" size="30" readonly="readonly" value="${deptName}"/>
					</c:if>
					<c:if test="${chaXun=='1'}">
						<!-- 3，4必须有一个单选 -->
						<font color="#15428B" size="16" face="宋体"><b>科室：</b></font><input type="text" id="${random}_deptName"  onClick="choiceDept()" size="30" readonly="readonly" value="${deptName}"/>
					</c:if>
				</c:forEach>
			</c:if>
			<!-- -----------------科室(条件END)------------------ -->
			<!-- -----------------指标(条件START)------------------ -->
			<c:if test="${chaXun=='4'}">
				<c:forEach var="chaXun" items="${chaXunS}"> 
					<c:if test="${chaXun=='2'}">
						<!-- 3，4必须有一个单选 -->
						<font color="#15428B" size="16" face="宋体"><b>指标：</b></font><input type="text" id="${random}_deptKeyName" onClick="choiceDeptKey('2')"  size="40" readonly="readonly" value="${deptKeyName}" />
					</c:if>
					<c:if test="${chaXun=='1'}">
						<!-- 3，4必须有一个单选 -->
						<font color="#15428B" size="16" face="宋体"><b>指标：</b></font><input type="text" id="${random}_deptKeyName" onClick="choiceDeptKey('1')"  size="40" readonly="readonly" value="${deptKeyName}" />
					</c:if>
				</c:forEach>
			</c:if> 
			<!-- -----------------指标(条件END)------------------ -->
		</c:forEach>
		<script>
			jQuery("#${random}_chaXunS").attr("value",chaXunStr);
			
		</script>
		<!-- -----------------------------------查询条件END-------------------------------------- -->
		
		
</td></tr></table></td><td><ul><li><div class="buttonActive"><div class="buttonContent">

		<button type="button" id="${random}_QueryLink" >&nbsp;&nbsp;<fmt:message key='button.search' />&nbsp;&nbsp;</button>
		
</div></div></li></ul></td></tr></table></div>
</form>
</div>