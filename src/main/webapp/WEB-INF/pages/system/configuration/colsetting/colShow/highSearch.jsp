
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
 var seOrSo = "so";
 var colSearchGridString="#colSearch_gridtable";
 jQuery(document).ready(function(){
	 
	 var colSearchGrid = jQuery(colSearchGridString);
	 colSearchGrid.jqGrid({
	    	url : "colSearchGridList",
	    	editurl:"colSearchGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
	{name:'inspectModelId',index:'inspectModelId',align:'left',label : '<s:text name="inspectTempl.inspectModelId" />',hidden:false,key:true,width:70},				
	{name:'inspectModelNo',index:'inspectModelNo',align:'left',label : '<s:text name="inspectTempl.inspectModelNo" />',hidden:false,width:80},				
	{name:'inspectModelName',index:'inspectModelName',align:'left',label : '<s:text name="inspectTempl.inspectModelName" />',hidden:false,width:150},				
	{name:'inspecttype',index:'inspecttype',align:'center',label : '<s:text name="inspectTempl.inspecttype" />',hidden:false,width:70},				
	{name:'jjdepttype.khDeptTypeName',index:'jjdepttype.khDeptTypeName',align:'center',label : '<s:text name="inspectTempl.jjdepttype" />',hidden:false,width:80},				
	{name:'periodType',index:'periodType',align:'center',label : '<s:text name="inspectTempl.periodType" />',hidden:false,width:70},				
	{name:'departmentNames',index:'departmentNames',align:'left',label : '<s:text name="inspectTempl.departmentNames" />',hidden:false},				
	/* {name:'personNames',index:'personNames',align:'center',label : '<s:text name="inspectTempl.personNames" />',hidden:false},				 */
	{name:'disabled',index:'disabled',align:'center',label : '<s:text name="inspectTempl.disabled" />',hidden:false,formatter:'checkbox',width:60},				
	{name:'remark',index:'remark',align:'center',label : '<s:text name="inspectTempl.remark" />',hidden:false,width:80}				

	        ],
	        jsonReader : {
				root : "inspectTempls", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        gridview:true,
	        rownumbers:true,
	        sortname: 'inspectModelId',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="inspectTemplList.title" />',
	        height:300,
	        gridview:true,
	        rownumbers:true,
	        loadui: "disable",
	        multiselect: true,
			multiboxonly:true,
			shrinkToFit:true,
			autowidth:true,
			ondblClickRow:function(){
				inspectTemplLayout.optDblclick();
			},
			onSelectRow: function(rowid) {
	        	setTimeout(function(){
	        		inspectTemplLayout.optClick();
	        	},100);
	       	},
			 gridComplete:function(){
	          /*  if(jQuery(this).getDataIDs().length>0){
	              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
	            } */
	           var dataTest = {"id":"inspectTempl_gridtable"};
	      	   jQuery.publish("onLoadSelect",dataTest,null);
	      	   makepager("inspectTempl_gridtable");
	       	} 

	    });
	 
	 jQuery("#saveColumnSet").click(function(){
		 var inspectTemplId = jQuery("#inspectTempl_gridtable").jqGrid('getGridParam','selarrrow');
		 var columnName = "",status = "",columnWidth="";
		 jQuery("input[name=columnCheckBox]").each(function(){
			 columnName += jQuery(this).attr("id")+",";
			 if(jQuery(this).attr("checked")=="checked"){
				 status += "true"+",";
			 }else{
				 status += "false"+",";
			 }
		 });
		 jQuery("input[name=columnWidth]").each(function(){
			 columnWidth +=  jQuery(this).val()+",";
		 });
		 $.ajax({
			    url: 'saveColumnSet?inspectTemplId='+inspectTemplId+'&columnNames='+columnName+'&statuss='+status+'&columnWidths='+columnWidth,
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			        jQuery('#name').attr("value",data.responseText);
			    },
			    success: function(data){
			        // do something with xml
			        $.pdialog.closeCurrent();
			        reloadInspectBSC('reload');
			        alert(data.message);
			    }
			});
	 });
}); 
function selectStatus(){
	if(seOrSo=="so"){
		$("div.sortDrag").find("div").unbind("mousedown");
		seOrSo = "se";
	}
}
function sortStatus(){
	if(seOrSo=="se"){
		$("div.sortDrag").sortDrag();
		seOrSo = "so";
	}
}

</script>
<div id="bscColumnDiv" class="page">
			<div class="pageContent">
			<!-- <div class="panelBar">
			<ul class="toolBar">
				<li><a  class="addbutton" href="javaScript:selectStatus()" ><span>输入
					</span>
				</a>
				</li>
				<li><a class="delbutton"  href="javaScript:sortStatus()"><span>排序</span>
				</a>
				</li>
			
			</ul>
			</div> -->
			
			
			
			<div>
				查询条件
				
				<s:iterator>
					
				</s:iterator>
			</div>
				<form id="inspectTemplForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
					<div class="pageFormContent sortDrag" layoutH="80">
					<s:iterator value="columnMap">
						<div style="border:1px solid #B8D0D6;padding:5px;margin:5px">
						<input id="${key}" onclick="checkboxSelect()" type="checkbox" name="columnCheckBox" <s:if test="value.status==true">checked</s:if>/>
						<s:text name="%{key}"></s:text>
						 <span style="margin-right:20px;float:right">列宽：<input style="float:none" name="columnWidth" type="text" size="3" value="${value.width }"/>%</span>
						</div>
					</s:iterator>
				</div>
				</form>
				<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="saveColumnSet"><s:text name="button.save" /></button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();"><s:text name="button.cancel" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
			</div>
</div>