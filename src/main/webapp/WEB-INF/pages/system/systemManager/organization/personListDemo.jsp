<%@ include file="/common/taglibs.jsp"%>


<head>

    <title><fmt:message key="mainMenu.title"/></title>
    <meta name="heading" content=""/>
    <meta name="menu" content="MainMenu"/>
    
    <script>
   
	    
    jQuery(document).ready(function() { 
       //alert("onready");

    });
   // jQuery.subscribe('rowselect', function(event, data) {
   //     alert('Selected Row : ' + event.originalEvent.id);
//	});
  //  jQuery.subscribe('rowselectall', function(event, data) {
  //      alert('Selected Row : ' + event.originalEvent.ids);
//	});
	
    function setPersonModel(postdata, formid)
    {
  	  //alert("beforesubmit");
  	  postdata['person.firstName'] = postdata['firstName'];
  	  postdata['person.lastName'] = postdata['lastName'];
  	  postdata['person.email'] = postdata['email'];
      return [true,""];
  	  
    }
    
	
	function checkGridOperation(response,postdata)
	{
	    var gridresponse = gridresponse || {};
	    gridresponse = jQuery.parseJSON(response.responseText);
	    var msg = gridresponse["gridOperationMessage"];
	    //alert(msg);
        if (msg == 'Person Saved')
        {
            return [true,""];
        }
        else
	    {
	        return [false,msg];   
	    }
        
	}
    </script>
    
</head>


<s:url id="editurl" action="gridEditPerson" namespace="/person"/> 
<s:url id="remoteurl" action="personListDemoJson" namespace="/person"/> 
<center>
    <sjg:grid 
    	id="personListDemoTable" 
    	href="%{remoteurl}"
     	gridModel="persons"
     
     	dataType="json" 
     	
     	pager="true" 
		page="1"
		pagerButtons="true"
		pagerInput="true"
		pagerPosition="right"
     	
		rowList="5,10,15,20,25,30,35,40,45,50"
		rowNum="5"
		rownumbers="true"
		
		multiselect="true"
		multiselectWidth="40"
		
		navigator="true"
		navigatorAddOptions="{reloadAfterSubmit:true,beforeSubmit:setPersonModel,afterSubmit:checkGridOperation}"
		navigatorEditOptions="{reloadAfterSubmit:true,beforeSubmit:setPersonModel,afterSubmit:checkGridOperation}"
		
    	navigatorEdit="true"
		editurl="%{editurl}"
		editinline="flase"
		

		navigatorView="true"
		onSelectRowTopics="rowselect" 
		onSelectAllTopics="rowselectall"
  
    >
    	<sjg:gridColumn 
    	    name="id" 
    	    search="false" 
    	    index="id" 
    	    title="ID" 
    	    hidden="true"
    	    formatter="integer" 
    	    sortable="false"/>
    	<sjg:gridColumn 
    	    name="firstName" 
    	    index="firstName" 
    	    title="FirstName" 
    	    sortable="true"
    	    editable="true" 
    	    width="275"
            editrules="{required: true}" 
    	    />
    	<sjg:gridColumn 
    	    name="lastName" 
    	    index="lastName" 
    	    title="LastName" 
    	    sortable="true"
    	    editable="true" 
    	    width="300"
    	    hidden="false"
            editrules="{required: true}" 
    	    />
    	 <sjg:gridColumn 
    	    name="email" 
    	    index="email" 
    	    title="Email" 
    	    sortable="true"
    	    editable="true" 
    	    width="300"
    	    hidden="false"
            editrules="{required: true}" 
    	    />
    	
    	
    </sjg:grid>
    <div id="gridinfo" class="ui-widget-content ui-corner-all"><p>Edit Mode for Row :</p></div>
    </center>