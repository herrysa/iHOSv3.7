<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%-- --%>
<html>
<head>
    <title><fmt:message key="dataCollectionTaskList.title"/></title>
    <meta name="heading" content="<fmt:message key='dataCollectionTaskList.heading'/>"/>
    <meta name="menu" content="DataCollectionTaskMenu"/>
    
    <script type="text/javascript">
    	function addRecord(){
			var url = "editDataCollectionTask?popup=true";
			var winTitle='<fmt:message key="dataCollectionTaskNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#dataCollectionTask_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editDataCollectionTask?popup=true&dataCollectionTaskId=" + sid;
				var winTitle='<fmt:message key="dataCollectionTaskNew.title"/>';
				popUpWindow(url, winTitle, "width=500");
			}
		}
	    function okButton(){
	    	 jQuery('#mybuttondialog').dialog('close');
	    };		
	    function setDataCollectionTaskModel(postdata, formid){
	  	  //alert("beforesubmit");
	  	   postdata['model.dataCollectionTaskId'] = postdata['dataCollectionTaskId'];
	  	   postdata['model.dataCollectionPeriod'] = postdata['dataCollectionPeriod'];
	  	   postdata['model.dataCollectionTaskDefine'] = postdata['dataCollectionTaskDefine'];
	  	   postdata['model.dataFile'] = postdata['dataFile'];
	  	   postdata['model.status'] = postdata['status'];
	      return [true,""];
	    }
		
		function checkGridOperation(response,postdata){
		    var gridresponse = gridresponse || {};
		    gridresponse = jQuery.parseJSON(response.responseText);
		    var msg = gridresponse["gridOperationMessage"];
		   // alert(msg);
		   jQuery("#gridinfo").html(msg);
	        return [true,""];   
		}
    
		datePick = function(elem){
		        jQuery(elem).datepicker({dateFormat:"<fmt:message key='date.format'/>"});
		        jQuery('#ui-datepicker-div').css("z-index", 2000);
		};
		jQuery(document).ready(function() { 
			jQuery.subscribe('rowselect', function(event,data) {
		    	jQuery("#gridinfo").html('<p>Edit Mode for Row : '+event.originalEvent.id+'</p>');
		    });
    	});
	</script>
</head>
<body>
<%@ include file="/common/miniNavBar.jsp"%> 

 <sj:dialog id="mybuttondialog" buttons="{'OK':function() { okButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="editurl" action="dataCollectionTaskGridEdit"/> 
<s:url id="remoteurl" action="dataCollectionTaskGridList"/> 


<div align="left">
<%@ include file="/ommon/miniNavBar.jsp"%>
<sj:submit id="add_button" value="%{getText('button.add')}" onClickTopics="addRowRecord" button="true" onclick="addRecord();"/>
<sj:submit id="editSelectRow_button" value="%{getText('button.edit')}"  button="true" onclick="editRecord();"/>
</div>
 <sjg:grid 
    	id="dataCollectionTask_gridtable" 
    	caption="%{getText('dataCollectionTaskList.title')}" 
    	dataType="json" 
    	gridModel="dataCollectionTasks"
		editurl="%{editurl}"
    	href="%{remoteurl}"    	
 	
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
		navigatorDeleteOptions="{reloadAfterSubmit:true,afterSubmit:checkGridOperation,left:screen.availWidth/4, top:screen.availHeight/4}"   
        navigatorSearch="true"
        navigatorSearchOptions="{multipleSearch:true,  showQuery: true,left:screen.availWidth/4, top:screen.availHeight/4}"
 		navigatorRefresh="true"
    	multiselect="true"
  		multiboxonly="true"
  		resizable="true"
  		
  		onSelectRowTopics="rowselect"
  		
  		draggable="true"
  		autowidth="true"
		onCompleteTopics="onLoadSelect"
    >
    <sjg:gridColumn 
    	    name="dataCollectionTaskId" 
    	    search="false" 
    	    index="dataCollectionTaskId" 
    	    title="%{getText('dataCollectionTask.dataCollectionTaskId')}" 
    	    hidden="true"
    	    formatter="integer" 
    	    sortable="false"
    	    editable="true" 
    	    key="true"
    	    />
   <sjg:gridColumn 
 	    name="dataCollectionPeriod.periodCode" 
 	    index="dataCollectionPeriod.periodCode" 
 	    title="%{getText('period.periodCode')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />
    <sjg:gridColumn 
 	    name="dataCollectionTaskDefine.dataCollectionTaskDefineName" 
 	    index="dataCollectionTaskDefine.dataCollectionTaskDefineName" 
 	    title="%{getText('dataCollectionTaskDefine.dataCollectionTaskDefineName')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />	    
     <sjg:gridColumn 
 	    name="dataCollectionTaskDefine.dataSourceDefine.dataSourceName" 
 	    index="dataCollectionTaskDefine.dataSourceDefine.dataSourceName" 
 	    title="%{getText('dataSourceDefine.dataSourceName')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />	
 	     	 <sjg:gridColumn 
 	    name="dataCollectionTaskDefine.dataSourceDefine.dataSourceType.dataSourceTypeName" 
 	    index="dataCollectionTaskDefine.dataSourceDefine.dataSourceType.dataSourceTypeName" 
 	    title="%{getText('dataSourceType.dataSourceTypeName')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />		
 	 <sjg:gridColumn 
 	    name="dataCollectionTaskDefine.dataSourceDefine.dataSourceType.isNeedFile" 
 	    index="dataCollectionTaskDefine.dataSourceDefine.dataSourceType.isNeedFile" 
 	    title="%{getText('dataSourceType.isNeedFile')}" 
 	    formatter="checkbox" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />	
   <sjg:gridColumn 
 	    name="dataFile" 
 	    index="dataFile" 
 	    title="%{getText('dataCollectionTask.dataFile')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />
   <sjg:gridColumn 
 	    name="status" 
 	    index="status" 
 	    title="%{getText('dataCollectionTask.status')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />
  </sjg:grid>
  </body>
</html>