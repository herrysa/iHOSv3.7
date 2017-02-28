
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var invUseGridIdString="#invUse_gridtable";
	
	jQuery(document).ready(function() { 
	var invUseGrid = jQuery(invUseGridIdString);
    invUseGrid.jqGrid({
    	url : "invUseGridList",
    	editurl:"invUseGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'invUseId',index:'invUseId',align:'center',label : '<s:text name="invUse.invUseId" />',hidden:true,key:true,editable:true},				
{name:'orgCode',index:'orgCode',align:'center',label : '<s:text name="invUse.orgCode" />',hidden:true},			
{name:'copyCode',index:'copyCode',align:'center',label : '<s:text name="invUse.copyCode" />',hidden:true},				
{name:'invUseCode',index:'invUseCode',align:'left',width:300,label : '<s:text name="invUse.invUseCode" />',hidden:false,edittype:"text",editable:true,editrules:{required:true},editoptions:{dataEvents:[{type:'blur',fn: function(e) { checkInvUseCode(this); }}]}},				
{name:'invUseName',index:'invUseName',align:'left',width:400,label : '<s:text name="invUse.invUseName" />',hidden:false,edittype:"text",editable:true,editrules:{required:true},editoptions:{dataEvents:[{type:'blur',fn: function(e) { isNullValidate(this); }}]}}				

        ],
        jsonReader : {
			root : "invUses", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'invUseId',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="invUseList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:false,
		autowidth:true,
        onSelectRow: function(rowid) {
       
       	},
		 gridComplete:function(){
           if(jQuery(this).getDataIDs().length>0){
             // jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"invUse_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("invUse_gridtable");
       	} 

    });
    jQuery(invUseGrid).jqGrid('bindKeys');
	
  	});
	
	function isNullValidate(obj){
		if(""==obj.value.trim()){
			alertMsg.error("该项不能为空");
			return;
		}
	}
	
	function checkInvUseCode(obj){
		var org = "${fns:userContextParam('orgCode')}";
		var copy = "${fns:userContextParam('copyCode')}";
		var invUseIdTemp = org+"_"+copy+"_"+obj.value;
		var url = 'checkId';
		url = encodeURI(url);
		$.ajax({
		    url: url,
		    type: 'post',
		    data:{entityName:'InvUse',searchItem:'invUseId',searchValue:invUseIdTemp,returnMessage:'材料用途编码已存在'},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		        if(data!=null){
		        	 alertMsg.error(data.message);
				     obj.value="";
		        }
		    }
		});
	}
	
	function invUseEdit(obj){
		obj.jqGrid("setColProp","invUseCode",{editable:false});
		gridEditRow(obj);
	}
	function invUseAdd(obj){
		obj.jqGrid("setColProp","invUseCode",{editable:true});
		gridAddRow(obj);
	}
	
</script>

<div class="page">
<div id="invUse_container">
			<div id="invUse_layout-center"
				class="pane ui-layout-center">
	<div class="pageContent">

<div class="panelBar">
			<ul class="toolBar">
				<li><a class="addbutton" onclick="invUseAdd(jQuery('#invUse_gridtable'))" ><span><fmt:message key="button.addRow" /></span></a></li>
				<li><a class="editbutton" onclick="invUseEdit(jQuery('#invUse_gridtable'))" ><span><fmt:message key="button.editRow" /></span></a></li>
				<li><a class="savebutton" onclick="gridSaveRow(jQuery('#invUse_gridtable'))" ><span><fmt:message key="button.saveRow" /></span></a></li>
				<li><a class="canceleditbutton" onclick="gridRestore(jQuery('#invUse_gridtable'))" ><span><fmt:message key="button.restoreRow" /></span></a></li>
				<li><a id="invUse_gridtable_del" class="delbutton" href="javaScript:"><span>删除</span></a></li>
			
			</ul>
		</div>
		<div id="invUse_gridtable_div" layoutH="58"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:invUseId;width:500;height:300">
			<input type="hidden" id="invUse_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="invUse_gridtable_addTile">
				<s:text name="invUseNew.title"/>
			</label> 
			<label style="display: none"
				id="invUse_gridtable_editTile">
				<s:text name="invUseEdit.title"/>
			</label>
			<label style="display: none"
				id="invUse_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="invUse_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_invUse_gridtable" class='loading ui-state-default ui-state-active' style="display: none"></div>
 <table id="invUse_gridtable"></table>
		<div id="invUsePager"></div>
</div>
	</div>
	<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="invUse_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="invUse_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="invUse_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>