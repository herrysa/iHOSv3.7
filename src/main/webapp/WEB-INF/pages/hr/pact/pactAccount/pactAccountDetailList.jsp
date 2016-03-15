
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	jQuery(document).ready(function() { 
		var initPactAccountFlag = 0;
		var pactAccountDetailGrid = jQuery("#pactAccountPDetail_gridtable");
		pactAccountDetailGrid.jqGrid({
	    	url : "pactGridList?personId=${personId}&filter_GEI_signState=3",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'id',index:'id',align:'center',label : '<s:text name="pact.id" />',hidden:true,key:true,highsearch:false},	
				{name:'code',index:'code',width : '120',align:'left',label : '<s:text name="pact.code" />',hidden:false,highsearch:true},	
				{name:'dept.hrOrg.orgname',index:'dept.hrOrg.orgname',width : '100',align:'left',label : '<s:text name="pact.orgCode" />',hidden:false,highsearch:true},	
				{name:'dept.name',index:'dept.name',width : '100',align:'left',label : '<s:text name="pact.dept" />',hidden:false,highsearch:true},	
				{name:'post.name',index:'post.name',width : '100',align:'left',label : '<s:text name="pact.post" />',hidden:false,highsearch:true},	
				{name:'workContent',index:'workContent',width : '100',align:'left',label : '<s:text name="pact.workContent" />',hidden:false,highsearch:true},				
				{name:'signTimes',index:'signTimes',width : '60',align:'right',label : '<s:text name="pact.signTimes" />',hidden:false,highsearch:true,formatter:'integer'},				
				{name:'signYear',index:'signYear',width : '60',align:'right',label : '<s:text name="pact.signYear" />',hidden:false,highsearch:true,formatter:'integer'},
				{name:'beginDate',index:'beginDate',width : '80',align:'center',label : '<s:text name="pact.beginDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
				{name:'endDate',index:'endDate',width : '80',align:'center',label : '<s:text name="pact.endDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},
				{name:'probationMonth',index:'probationMonth',width : '80',align:'right',label : '<s:text name="pact.probationMonth" />',hidden:false,highsearch:true,formatter:'integer'},
				{name:'probationBeginDate',index:'probationBeginDate',width : '90',align:'center',label : '<s:text name="pact.probationBeginDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'probationEndDate',index:'probationEndDate',width : '90',align:'center',label : '<s:text name="pact.probationEndDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'breakDate',index:'breakDate',width : '80',align:'center',label : '<s:text name="pact.breakDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'breakReason',index:'breakReason',width : '100',align:'left',label : '<s:text name="pact.breakReason" />',hidden:false,highsearch:true},				
				{name:'relieveDate',index:'relieveDate',width : '80',align:'center',label : '<s:text name="pact.relieveDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'relieveReason',index:'relieveReason',width : '100',align:'left',label : '<s:text name="pact.relieveReason" />',hidden:false,highsearch:true},				
				{name:'compSignDate',index:'compSignDate',width : '80',align:'center',label : '<s:text name="pact.compSignDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'operateDate',index:'operateDate',width : '80',align:'center',label : '<s:text name="pact.operateDate" />',hidden:false,highsearch:true,formatter:"date",formatoptions:{newformat : 'Y-m-d'}},				
				{name:'remark',index:'remark',width : '100',align:'left',label : '<s:text name="pact.remark" />',hidden:false,highsearch:true},	
				{name:'pactState',index:'pactState',width : '80',align:'center',label : '<s:text name="pact.pactState" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '1:初签;2:续签'}},				
				{name:'signState',index:'signState',width : '80',align:'center',label : '<s:text name="pact.signState" />',hidden:false,highsearch:true,formatter : 'select',edittype : 'select',editoptions : {value : '3:有效;4:终止;5:解除'}},				
				{name:'path',index:'path',align:'center',label : '<s:text name="pact.path" />',hidden:true,highsearch:false}				
      		],
      		jsonReader : {
      			root : "pacts", // (2)
      			page : "page",
      			total : "total",
      			records : "records", // (3)
      			repeatitems : false
      		// (4)
      		},
      		sortname: 'code',
      	    viewrecords: true,
      	    viewrecords: true,  
      	    height: 100,  
      	    rowNum: 5,
      	    gridview:true,
      	    rownumbers:true,
      	    loadui: "disable",
      	    multiselect: true,
      		multiboxonly:true,
      		shrinkToFit:false,
      		autowidth:false,
      	    onSelectRow: function(rowid) {
      	   
      	   	},
      	  	gridComplete:function(){
				//gridContainerResize('pactAccountPDetail','div');
	      	  	var rowNum = jQuery(this).getDataIDs().length;
	            if(rowNum>0){
	                var rowIds = jQuery(this).getDataIDs();
	                var ret = jQuery(this).jqGrid('getRowData');
	                var id='';
	                for(var i=0;i<rowNum;i++){
	              	  id=rowIds[i];
	              	  if(ret[i]['signState']=="3"){
	              		  setCellText(this,id,'signState','<span >有效</span>');
	              	  }else if(ret[i]['signState']=="4"){
	              		  setCellText(this,id,'signState','<span style="color:red" >终止</span>');
	              	  }else if(ret[i]['signState']=="5"){
	              		  setCellText(this,id,'signState','<span style="color:blue" >解除</span>');
	              	  }
	              	  setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewPactRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
	              }
	            }else{
	            	var tw = jQuery("#pactAccountPDetail_gridtable").outerWidth();
					jQuery("#pactAccountPDetail_gridtable").parent().width(tw);
					jQuery("#pactAccountPDetail_gridtable").parent().height(1);
	            }
            	var dataTest = {"id":"pactAccountPDetail_gridtable"};
       	   		jQuery.publish("onLoadSelect",dataTest,null);
       	   	    initPactAccountFlag = initColumn('pactAccountPDetail_gridtable','com.huge.ihos.hr.pact.model.PactAccountDetail',initPactAccountFlag);
        	} 
		});
		jQuery(pactAccountDetailGrid).jqGrid('bindKeys');
	}); 
</script>

<div class="page">
	<div class="pageContent">
		<div id="pactAccountPDetail_gridtable_div"  extraHeight=56 tablecontainer="pactAccountP_layout-south"
			class="grid-wrapdiv">
		<div id="load_pactAccountPDetail_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 		<table id="pactAccountPDetail_gridtable"></table>
		</div>
	</div>
</div>