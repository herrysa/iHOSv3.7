<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/links.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<head>
<title>pinYinCodeSeclectTest</title>
    <script type="text/javascript" src="${ctx}/scripts/fancyBox/source/jquery.fancybox.js?v=2.0.6"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/scripts/fancyBox/source/jquery.fancybox.css?v=2.0.6" media="screen" />
<style type="text/css">
#htmlcon {
	width: 800px;
	height: 600px;
	display: none;
}
</style>
<script type="text/javascript">
jQuery("#various2").fancybox({
	'modal' : true
});

jQuery("#various1").fancybox({
	'modal' : true
});
function getSelectRowData(sId,dId,varId,disId){
	var grid = jQuery("#gridTable");
	var colModel = grid.jqGrid("getGridParam",'colModel');  
	var id = jQuery("#gridTable").jqGrid('getGridParam','selrow');
	if (id)	{
		var ret = jQuery("#gridTable").jqGrid('getRowData',id);
		var value = ret[varId];
		var display = ret[disId];
		jQuery('#'+sId).attr("value",value);
		jQuery('#'+dId).attr("value",display);
		jQuery.fancybox.close();
	} else { alert("Please select row");}
}
function getHtmlCon(){
	jQuery.get("${ctx}/query?searchName=TestSearchSample&popup=true", function(result){
		alert(result);
		jQuery("#htmlcon").html(result);
		//jQuery("#htmlcon").attr(result);
		  });
}
</script>
</head>


<s:form id="pinYinCodeSeclectTestForm" theme="xhtml">

	<s:url id="dictionAuto" action="dictionaryPinYinJson"
		namespace="/pinyin" />


	<sj:autocompleter id="mapTest" name="mapTest" label="HandleaMap"
		href="%{dictionAuto}" list="dicList" size="20" delay="50"
		loadMinimumCount="1" forceValidOption="false" />
	<sj:autocompleter id="fdc" name="fdc" label="fdc" href="%{dictionAuto}"
		list="fdcMap" delay="50" loadMinimumCount="2" forceValidOption="false"
		title="PinYin" loadingText="sssssss" tooltip="gggggggggggggg" />
	<s:url id="deptAuto" action="deptPinYinJson" namespace="/pinyin" />
	<sj:autocompleter id="deptTest" name="deptTest" label="deptTest"
		href="%{deptAuto}" list="deptList" size="20" delay="50"
		loadMinimumCount="1" forceValidOption="false" />

	<s:url id="personAuto" action="personPinYinJson" namespace="/pinyin" />
	<sj:autocompleter id="personTest" name="personTest" label="personTest"
		href="%{personAuto}" list="personList" size="20" delay="50"
		loadMinimumCount="1" forceValidOption="false" />


	<s:url id="chargeTypeAuto" action="chargeTypePinYinJson"
		namespace="/pinyin" />
	<sj:autocompleter id="chargeTypeTest" name="chargeTypeTest"
		label="chargeTypeTest" href="%{chargeTypeAuto}" list="chargeTypeList"
		size="20" delay="50" loadMinimumCount="1" forceValidOption="false" />

	<s:url id="chargeItemAuto" action="chargeItemPinYinJson"
		namespace="/pinyin" />
	<sj:autocompleter id="chargeItemTest" name="chargeItemTest"
		label="chargeItemTest" href="%{chargeItemAuto}" list="chargeItemList"
		size="20" delay="50" loadMinimumCount="3" forceValidOption="false" />


	<s:url id="costItemAuto" action="costItemPinYinJson"
		namespace="/pinyin" />
	<sj:autocompleter id="costItemeTest" name="costItemTest"
		label="costItemTest" href="%{costItemAuto}" list="costItemList"
		size="20" delay="50" loadMinimumCount="1" forceValidOption="false" />
	<appfuse:yearMonth htmlField="fdctestfield" initValue="201205"></appfuse:yearMonth>
	<appfuse:stringSelect htmlFieldName="stringSelect" paraString="1;2;3;4"></appfuse:stringSelect>
	
	 <appfuse:dictionary code="yesno" name="expertFlag" required="false" />
	 <appfuse:singleSelect htmlFieldName="fdctestfield1" paraDisString="a;b;c;d" paraValueString="1;2;3;4"></appfuse:singleSelect>
	 
	 
	 
	 <s:select list="#{1:'aa',2:'bb',3:'cc'}"  label="abc" listKey="key" listValue="value"  headerKey="0" headerValue="aabb"/>
</s:form>


     <div id="htmlcon">
   </div>
		<a id="various1" href="#htmlcon" onclick="getHtmlCon();">选择</a>
	

