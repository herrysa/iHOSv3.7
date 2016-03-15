<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="mccKeyDetailList.title"/></title>
    <meta name="heading" content="<fmt:message key='mccKeyDetailList.heading'/>"/>
    <meta name="menu" content="MccKeyDetailMenu"/>
    
    <script type="text/javascript">
    	function addRecord(){
			var url = "editMccKeyDetail?popup=true";
			var winTitle='<fmt:message key="mccKeyDetailNew.title"/>';
			popUpWindow(url, winTitle, "width=500");
		}
		function editRecord(){
	        var sid = jQuery("#mccKeyDetail_gridtable").jqGrid('getGridParam','selarrrow');
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
				var url = "editMccKeyDetail?popup=true&mccKeyDetailId=" + sid;
				var winTitle='<fmt:message key="mccKeyDetailNew.title"/>';
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

<s:url id="editurl" action="mccKeyDetailGridEdit"/> 
<s:url id="remoteurl" action="mccKeyDetailGridList"/> 
<div align="left">
	<sj:submit id="add_button" value="%{getText('button.add')}" onClickTopics="addRowRecord" button="true" onclick="addRecord();"/>
	<sj:submit id="editSelectRow_button" value="%{getText('button.edit')}"  button="true" onclick="editRecord();"/>
</div>
 <sjg:grid 
    	id="mccKeyDetail_gridtable" 
    	caption="%{getText('mccKeyDetailList.title')}" 
    	dataType="json" 
    	gridModel="mccKeyDetails"
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
 		onCompleteTopics="onLoadSelect"
    	multiselect="true"
  		multiboxonly="true"
  		resizable="true"
  		height="200"
  		autowidth="true"
  		draggable="true"
    >
    <sjg:gridColumn 
    	    name="mccKeyDetailId" 
    	    search="false" 
    	    index="mccKeyDetailId" 
    	    title="%{getText('mccKeyDetail.mccKeyDetailId')}" 
    	    hidden="true"
    	    formatter="integer" 
    	    key="true"
    	    />
    
   <sjg:gridColumn 
 	    name="dispNo" 
 	    index="dispNo" 
 	    title="%{getText('mccKeyDetail.dispNo')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="maxValue" 
 	    index="maxValue" 
 	    title="%{getText('mccKeyDetail.maxValue')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="mccKeyId" 
 	    index="mccKeyId" 
 	    title="%{getText('mccKeyDetail.mccKeyId')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="minValue" 
 	    index="minValue" 
 	    title="%{getText('mccKeyDetail.minValue')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
   <sjg:gridColumn 
 	    name="state" 
 	    index="state" 
 	    title="%{getText('mccKeyDetail.state')}" 
 	    sortable="true"
 	    search="true"
 		searchoptions="{sopt:['eq','ne','cn','bw']}"
 	    />
  </sjg:grid>
