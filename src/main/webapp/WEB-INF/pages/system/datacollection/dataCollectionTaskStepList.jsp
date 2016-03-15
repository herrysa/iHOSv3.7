<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="dataCollectionTaskStepList.title"/></title>
    <meta name="heading" content="<fmt:message key='dataCollectionTaskStepList.heading'/>"/>
    <meta name="menu" content="DataCollectionTaskStepMenu"/>
    
    <script type="text/javascript">
    	function addStepRecord(){
			var url = "editDataCollectionTaskStep?popup=true";
			var winTitle='<fmt:message key="dataCollectionTaskStepNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editStepRecord(){
	        var sid = jQuery("#dataCollectionTaskStep_gridtable").jqGrid('getGridParam','selarrrow');
	        if(sid==null || sid.length ==0){
				//alert("<fmt:message key='list.selectNone'/>");
				jQuery('div.#stepMsgDialog').html("<fmt:message key='list.selectNone'/>");
				jQuery('#stepMsgDialog').dialog('open');
				return;
				}
	        if(sid.length>1){
				  //alert("<fmt:message key='list.selectMore'/>");
			  jQuery('div.#stepMsgDialog').html("<fmt:message key='list.selectMore'/>");
			  jQuery('#stepMsgDialog').dialog('open');
				return;
			  }else{
	         jQuery("#gridinfo").html('<p>Loading..... ID : '+sid+'</p>');
				var url = "editDataCollectionTaskStep?popup=true&stepId=" + sid;
				var winTitle='<fmt:message key="dataCollectionTaskStepNew.title"/>';
				popUpWindow(url, winTitle, "width=500");
			}
		}
	    function okStepButton(){
	    	 jQuery('#stepMsgDialog').dialog('close');
	    };		
	    function setDataCollectionTaskStepModel(postdata, formid){
	  	  //alert("beforesubmit");
	  	   postdata['model.stepId'] = postdata['stepId'];
	  	   postdata['model.dataCollectionTaskDefine'] = postdata['dataCollectionTaskDefine'];
	  	   postdata['model.execOrder'] = postdata['execOrder'];
	  	   postdata['model.execSql'] = postdata['execSql'];
	  	   postdata['model.isUsed'] = postdata['isUsed'];
	  	   postdata['model.note'] = postdata['note'];
	  	   postdata['model.stepName'] = postdata['stepName'];
	  	   postdata['model.stepType'] = postdata['stepType'];
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


 <sj:dialog id="stepMsgDialog" buttons="{'OK':function() { okStepButton(); }}" autoOpen="false" modal="true" title="%{getText('messageDialog.title')}"/>

<s:url id="stepEditurl" action="dataCollectionTaskStepGridEdit"/> 
<s:url id="stepRemoteurl" action="dataCollectionTaskStepGridList"/> 

<sj:submit id="step_add_button" value="%{getText('button.add')}" onClickTopics="addRowRecord" button="true" onclick="addStepRecord();"/>
<sj:submit id="step_editSelectRow_button" value="%{getText('button.edit')}"  button="true" onclick="editStepRecord();"/>
 <sjg:grid 
    	id="dataCollectionTaskStep_gridtable" 
    	caption="%{getText('dataCollectionTaskStepList.title')}" 
    	dataType="json" 
    	gridModel="dataCollectionTaskSteps"

    	href="%{stepRemoteurl}"    	
    	editurl="%{stepEditurl}"
   	
    	rowList="10,15,20,30,40,50,60,70,80,90,100"
    	rowNum="20"
    	rownumbers="true"
    	
    	pager="true" 
    	page="1"
    	pagerButtons="true"
    	pagerInput="true"
    	pagerPosition="right"
    	
		navigator="true"
		navigatorAdd="true"
		navigatorAddOptions="{reloadAfterSubmit:true,beforeSubmit:setDataCollectionTaskStepModel,afterSubmit:checkGridOperation,left:screen.availWidth/4, top:screen.availHeight/4}"
        navigatorEdit="true"
		navigatorEditOptions="{reloadAfterSubmit:true,beforeSubmit:setDataCollectionTaskStepModel,afterSubmit:checkGridOperation,left:screen.availWidth/4, top:screen.availHeight/4}"
		navigatorDelete="true"
		navigatorDeleteOptions="{reloadAfterSubmit:true,afterSubmit:checkGridOperation}"   
        navigatorSearch="true"
        navigatorSearchOptions="{sopt:['cn','bw','eq'],multipleSearch:true,  showQuery: true}"
        navigatorView="true"
 		navigatorRefresh="true"

    	multiselect="true"
  		multiselectWidth="20"
  		multiboxonly="true"
  		
  		resizable="true"
  		
  		onSelectRowTopics="rowselect"
  		
  		draggable="true"
  		autowidth="false"
		onCompleteTopics="onLoadSelect"
    >
    <sjg:gridColumn 
    	    name="stepId" 
    	    search="false" 
    	    index="stepId" 
    	    title="%{getText('dataCollectionTaskStep.stepId')}" 
    	    hidden="true"
    	    formatter="integer" 
    	    sortable="false"
    	    editable="true" 
    	    key="true"
    	    />
    
   <sjg:gridColumn 
 	    name="execOrder" 
 	    index="execOrder" 
 	    title="%{getText('dataCollectionTaskStep.execOrder')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />
   <sjg:gridColumn 
 	    name="execSql" 
 	    index="execSql" 
 	    title="%{getText('dataCollectionTaskStep.execSql')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />
    <sjg:gridColumn 
    	name="isUsed" 
	    index="isUsed" 
	    title="%{getText('dataCollectionTaskStep.isUsed')}" 
	    sortable="true"
	    editable="true" 
      edittype="checkbox"
      formatter="checkbox"
      width="90"
      search="true"
      searchoptions="{sopt:['eq','ne']}"
      editrules="{required: true}"/> 
   <sjg:gridColumn 
 	    name="note" 
 	    index="note" 
 	    title="%{getText('dataCollectionTaskStep.note')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />
   <sjg:gridColumn 
 	    name="stepName" 
 	    index="stepName" 
 	    title="%{getText('dataCollectionTaskStep.stepName')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />
   <sjg:gridColumn 
 	    name="stepType" 
 	    index="stepType" 
 	    title="%{getText('dataCollectionTaskStep.stepType')}" 
 	    sortable="true"
 	    editable="true" 
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    width="100"
      editrules="{required: true}" 
 	    />
  </sjg:grid>
  <br/>
<div id="gridinfo" class="ui-widget-content ui-corner-all"><p>Edit Mode for Row :</p></div>