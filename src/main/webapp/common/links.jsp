<%@ include file="/common/taglibs.jsp"%>

    <s:url id="struts_ctx" value="/"/>
    <s:set name="themeName" value="'rzlt_theme'"  scope="session"></s:set>
 
    <sj:head jqueryui="true" loadAtOnce="false" debug="false" compressed="false" jquerytheme="%{#session.themeName}" customBasepath="%{struts_ctx}styles/themes" locale="zh_CN" scriptPath="%{struts_ctx}struts2/"/>

	<script>
		//jQuery.noConflict();
	</script>
 	<!--<script type="text/javascript"	src="${ctx}/scripts/jquery/jquery-ui.min.js"></script>-->
 	
 	<script type="text/javascript" src="${ctx}/scripts/json-js/json2.js"></script>
 	
	 <script type="text/javascript"	src="${ctx}/scripts/layouts/jquery.layout-latest.js"></script>
	<link rel="stylesheet" type="text/css" media="all"	href="<c:url value='scripts/layouts/layout-default-latest.css'/>" />
	
	<%-- <script type="text/javascript" src="${ctx}/scripts/jqgrid/js/jquery.jqGrid.src.js"></script> --%>
	<script type="text/javascript" src="${ctx}/struts2/js/plugins/jquery.jqGrid.js"></script>
	<script type="text/javascript" src="${ctx}/struts2/js/plugins/grid.grouping.js"></script> 
	<script type="text/javascript" src="${ctx}/struts2/js/plugins/grid.inlinedit.js"></script>
	<script type="text/javascript"	src="${ctx}/struts2/i18n/grid.locale-cn.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/jqgrid/js/jquery.jqGrid.groupHeader-0.2.js"></script>
	<script type="text/javascript"	src="${ctx}/scripts/formater/format.20110630-1100.min.js"></script>

	<link rel="stylesheet" type="text/css" media="all"	href="${ctx}/scripts/jqgrid/css/ui.jqgrid.css" />

	<link rel="stylesheet" type="text/css" media="all"	href="${ctx}/struts2/themes/redmond/jquery-ui.css" />


	<script language="javascript" type="text/javascript"
	src="${ctx}/scripts/DatePicker/WdatePicker.js"></script>
	
	<script type="text/javascript"
	src="${ctx}/scripts/zTree/js/jquery.ztree.all-3.5.js"></script>
	<script type="text/javascript"
	src="${ctx}/scripts/zTree/js/jquery.ztree.exhide-3.5.min.js"></script>
	
	<link rel="stylesheet" href="${ctx}/scripts/tinyscrollbar/tinyscrollbar.css" type="text/css" media="screen"/>
	<script src="${ctx}/scripts/tinyscrollbar/jquery.tinyscrollbar.js"></script>

	<script type="text/javascript" src="<c:url value='/scripts/script.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='/scripts/zzhJsTest.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='/scripts/treeSelect.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='/scripts/treeSelect-2.0.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/scripts/DetailButton.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/scripts/voucher.js'/>"></script>

	<link href="${ctx}/scripts/wdScrollTab/css/TabPanel.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="${ctx}/scripts/wdScrollTab/plugins/Fader.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/wdScrollTab/plugins/TabPanel.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/wdScrollTab/plugins/Math.uuid.js"></script>
	
	<script src="${ctx}/scripts/backbone/underscore.js"></script>
	<script src="${ctx}/scripts/backbone/backbone.js"></script>
	<script src="${ctx}/scripts/ToolButtonBar.js"></script>
	
	<script type="text/javascript" src="${ctx}/scripts/hr.js"></script>
	
	<script type="text/javascript" src="${ctx}/scripts/func.js"></script>
	
	<script type="text/javascript" src="${ctx}/scripts/date.js"></script>

	<script type="text/javascript" src="<c:url value='/scripts/jsValidate.js'/>"></script> 
    <%-- <link rel="stylesheet" type="text/css" media="all" href="${struts_ctx}styles/themes/${themeName}/style.css" /> --%>

    
	<script type="text/javascript" src="<c:url value='/scripts/jcanvas/jcanvas.min.js'/>"></script>
    
    <link rel="stylesheet" type="text/css" media="all"	href="${ctx}/scripts/zTree/css/zTreeStyle/zTreeStyle.css" />
    
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/styles/frameStyle.css" />
    <link rel="stylesheet" type="text/css" media="all" href="${ctx}/styles/hr.css" />
    
    	<%-- <script type="text/javascript" src="${ctx}/scripts/zhgrid/zhgrid.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/scripts/zhgrid/zhgrid.css" /> --%>
	
	<script type="text/javascript" src="${ctx}/supcan/binary/dynaload.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/supcan.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/cursorOper.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/colorpicker.js"></script>
	<%-- <script type="text/javascript" src="${ctx}/supcan/lhgdialog/lhgcore.min.js"></script>
	<script type="text/javascript" src="${ctx}/supcan/lhgdialog/_lhgdialog.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/supcan/lhgdialog/lhgdialog.css" /> --%>
	
	<script type="text/javascript" src="${ctx}/scripts/jquery-resize/jquery.ba-resize.js"></script>
	
	<script type="text/javascript" src="${ctx}/struts2/js/base/jquery.ui.widget.min.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/combogrid/resources/plugin/jquery.ui.combogrid-1.6.2.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/scripts/combogrid/resources/css/smoothness/jquery.ui.combogrid.css" />
	
	<script type="text/javascript" src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
	<link rel="stylesheet" href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
