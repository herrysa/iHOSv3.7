<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/miniNavBar.jsp"%>
<head>
    <title><fmt:message key="singleEntitySampleList.title"/></title>
    <meta name="heading" content="<fmt:message key='singleEntitySampleList.heading'/>"/>
    <meta name="menu" content="SingleEntitySampleMenu"/>
    
    <script type="text/javascript">
    	function addRecord(){
			var url = "editSingleEntitySample?popup=true";
			var winTitle='<fmt:message key="singleEntitySampleNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#singleEntitySample_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
				//alert("<fmt:message key='list.selectNone'/>");
				jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectNone'/>");
				jQuery('#mybuttondialog').dialog('open');
				return;
				}
	        if(sid.length>1){
				  //alert("<fmt:message key='list.selectMore'/>");
			  jQuery('div.#mybuttondialog').html("<fmt:message key='list.selectMore'/>");
			  jQuery('#mybuttondialog').dialog('open');
				return;
			  }else{
	         jQuery("#gridinfo").html('<p>Loading..... ID : '+sid+'</p>');
				var url = "editSingleEntitySample?popup=true&pkid=" + sid;
				var winTitle='<fmt:message key="singleEntitySampleNew.title"/>';
				popUpWindow(url, winTitle, "width=500");
			}
		}
	    function okButton(){
	    	 jQuery('#mybuttondialog').dialog('close');
	    };		
		datePick = function(elem){
		        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
		        jQuery('#ui-datepicker-div').css("z-index", 2000);
		};
		jQuery(document).ready(function() { 
    	});
	</script>
</head>


 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="singleEntitySampleGridEdit"/> 
<s:url id="remoteurl" action="singleEntitySampleGridList"/> 
<div align="left">
	<sj:submit id="add_button" value="%{getText('button.add')}" onClickTopics="addRowRecord" button="true" onclick="addRecord();"/>
	<sj:submit id="editSelectRow_button" value="%{getText('button.edit')}"  button="true" onclick="editRecord();"/>
</div>
 <sjg:grid 
    	id="singleEntitySample_gridtable" 
    	caption="%{getText('singleEntitySampleList.title')}" 
    	dataType="json" 
    	gridModel="singleEntitySamples"
    	href="%{remoteurl}"    	
    	editurl="%{editurl}"
    	rowList="10,15,20,30,40,50,60,70,80,90,100"
    	rowNum="20"
    	rownumbers="true"
    	pager="true" 
    	page="1"
    	pagerButtons="true"
    	pagerInput="true"
    	pagerPosition="right"
		navigator="true"
		navigatorAdd="false"
        navigatorEdit="false"
		navigatorDelete="true"
		navigatorDeleteOptions="{reloadAfterSubmit:true}"   
        navigatorSearch="true"
        navigatorSearchOptions="{multipleSearch:true,  showQuery: true}"
 		navigatorRefresh="true"
    	multiselect="true"
  		multiboxonly="true"
  		resizable="true"
  		autowidth="true"
	onCompleteTopics="onLoadSelect"
  		draggable="true"
    >
    <sjg:gridColumn 
    	    name="pkid" 
    	    search="false" 
    	    index="pkid" 
    	    title="%{getText('singleEntitySample.pkid')}" 
    	    hidden="true"
    	    formatter="integer" 
    	    key="true"
    	    />
    
   <sjg:gridColumn 
 	    name="bigDecimalField" 
 	    index="bigDecimalField" 
 	    title="%{getText('singleEntitySample.bigDecimalField')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
    <sjg:gridColumn 
    	name="booleanField" 
	    index="booleanField" 
	    title="%{getText('singleEntitySample.booleanField')}" 
	    sortable="true"
      edittype="checkbox"
      formatter="checkbox"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"/> 
    <sjg:gridColumn 
    	name="dateField" 
	    index="dateField" 
	    title="%{getText('singleEntitySample.dateField')}" 
	    sortable="true"
      formatter="date"
      formatoptions="{newformat : '%{datePattern}'}"
      search="true"
      searchoptions="{sopt:['eq','ne','lt','gt'], dataInit:datePick}"
      /> 
   <sjg:gridColumn 
 	    name="doubleField" 
 	    index="doubleField" 
 	    title="%{getText('singleEntitySample.doubleField')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="floatField" 
 	    index="floatField" 
 	    title="%{getText('singleEntitySample.floatField')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="intField" 
 	    index="intField" 
 	    title="%{getText('singleEntitySample.intField')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="stringField" 
 	    index="stringField" 
 	    title="%{getText('singleEntitySample.stringField')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
  </sjg:grid>
