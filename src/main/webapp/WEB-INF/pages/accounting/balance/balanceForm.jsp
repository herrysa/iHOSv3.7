
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var balanceLayout;
	var balanceGridIdString="#balance_assist_gridtable";
	
	jQuery(document).ready(function() { 
		balanceLayout = makeLayout({
			baseName: 'balance', 
			tableIds: 'balance_assist_gridtable'
		}, null);
		
		var balanceGrid = jQuery(balanceGridIdString);
		    balanceGrid.jqGrid({
		    	url : "editBalanceGridList?acctId=${account.acctId}",
		    	editurl:"balanceAssitGridEdit",
		    	editinline:true,
				datatype : "json",
				mtype : "GET",
		        colModel:[
			{name:'balanceId',index:'balanceId',align:'right',label : '<s:text name="balance.balanceId" />',hidden:true,edittype:'text',editable:true,key:true},
		   	<c:forEach items="${assistTypes}" var="asst">
		   	<c:if test="${asst.assisttypeCode == '0'}" >
		   	{name:'assit0.name',index:'assit0.name',align:'center',edittype:'text',label:'${asst.assistType}',hidden:false,key:false,editable:true},
		   	{name:'assit0.departmentId',index:'assit0.departmentId',align:'center',edittype:'text',hidden:true,key:false,editable:true},
		   	</c:if>
			
		   	</c:forEach>
			{name:'beginJie',index:'beginJie',align:'right',label : '<s:text name="balance.beginJie" />',hidden:false,width:50,formatter:'number',edittype:'text',editable:true,editrules:{required:true,number:true}},
			{name:'beginDai',index:'beginDai',align:'right',label : '<s:text name="balance.beginDai" />',hidden:false,width:50,formatter:'number',edittype:'text',editable:true,editrules:{required:true,number:true}},
			{name:'yue',index:'yue',align:'right',label : '<s:text name="balance.ye" />',hidden:false,width:50,formatter:'number',edittype:'text',editable:true,editrules:{required:true,number:true}},
			{name:'total',index:'total',align:'right',label : '<s:text name="balance.total" />',hidden:false,width:50,formatter:'number',editable:false},
			{name:'acctId',index:'tory',align:'right',label : '<s:property value="account.acctId" />',editoptions:{value:'<s:property value="account.acctId"/>'},edittype:'text',hidden:true,editable:true},
		        ],
		        jsonReader : {
					root : "balances", // (2)
					repeatitems : false
				// (4)
				},
		        //caption:'<s:text name="balanceList.title" />',
		        height:500,
		        gridview:true,
		        rownumbers:true,
		        loadui: "disable",
		        multiselect: true,
				multiboxonly:true,
				shrinkToFit:true,
				autowidth:false,
		        onSelectRow: function(rowid) {
		       	},
				 gridComplete:function(){
		           var dataTest = {"id":"balance_assist_gridtable"};
		      	   jQuery.publish("onLoadSelect",dataTest,null);
		      	   makepager("balance_assist_gridtable");
		       	} 
		    });
    	jQuery(balanceGrid).jqGrid('bindKeys');
    	
	//balanceLayout.resizeAll();
  	});
  	function editBalanceAssist(){
		gridEditRow(jQuery('#balance_assist_gridtable'));
		gridEditBalanceAssit();
  	}
  	function addAssitRow(){
  		gridAddRow(jQuery('#balance_assist_gridtable'));
  		gridEditBalanceAssit();
  	}
  	function closeBalanceForm(){
  		$.pdialog.close('editBalance');
  		var checkBalanceDialog = $("body").data('checkBalanceList');  
        if(checkBalanceDialog){  
        	checkBalanceGridReload();  
        }
        balanceGridReload();
  	}
  	
	function gridEditBalanceAssit(){
		$("input[name='assit0.name']:visible").autocomplete("autocompleteBySql",{
	  		width : 270,
	  		multiple : false,
	  		multipleSeparator: "", 
	  		autoFill : false,
	  		matchContains : true,
	  		matchCase : true,
	  		dataType : 'json',
	  		parse : function(json) {
	  			var data = json.autocompleteResult;
	  			if (data == null || data == "") {
	  				var rows = [];
	  				rows[0] = {
	  					data : "没有结果",
	  					value : "",
	  					result : ""
	  				};
	  				return rows;
	  			} else {
	  				var rows = [];
	  				for ( var i = 0; i < data.length; i++) {
	  					rows[rows.length] = {
	  						data : data[i].deptId+','+data[i].deptName,
	  						value : data[i].deptId,
	  						result : data[i].deptName
	  					};
	  				}
	  				return rows;
	  			}
	  		},
	  		extraParams : {
	  			cloumns : "dept.deptId,dept.name",
	  			sql:"select dept.deptId as deptId, dept.name as deptName from t_department dept where disabled = 0 "
	  		},
	  		formatItem : function(row) {
	  			return row;
	  		},
	  		formatResult : function(row) {
	  			return row;
	  		}
	  	});
	  	jQuery("input[name='assit0.name']:visible").result(function(event, row, formatted) {
	  		if (row == "没有结果") {
	  			return;
	  		}
	  		jQuery("input[name='assit0.departmentId']").attr("value", (row.split(','))[0]); 
	  	});
  	}
</script>
<script type="text/javascript" src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
    <link  rel="stylesheet"  href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
<div class="page">
<div id="balance_container">
			<div id="balance_layout-center"
				class="pane ui-layout-center">
	<div class="pageHeader">
			<div class="searchBar">
			<form id="searchAbstractForm">
			<table class="searchContent">
					<tr>
						<td><s:text name='account.acctCode'/>:
							${account.acctCode }
						 </td>
						<td><s:text name='account.acctname'/>:
							${account.acctname }
						 </td>
						 <td><s:text name='account.direction'/>:
							${account.direction }
						 </td>
					</tr>
				</table>
			</form>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
		<ul class="toolBar">
				<li><a class="addbutton" onclick="addAssitRow()" ><span><fmt:message key="button.addRow" /></span></a></li>
				<li><a class="editbutton" onclick="editBalanceAssist()" ><span><fmt:message key="button.editRow" /></span></a></li>
				<li><a class="savebutton" onclick="gridSaveRow(jQuery('#balance_assist_gridtable'))" ><span><fmt:message key="button.saveRow" /></span></a></li>
				<li><a class="canceleditbutton" onclick="gridRestore(jQuery('#balance_assist_gridtable'))" ><span><fmt:message key="button.restoreRow" /></span></a></li>
				<li><a id="balance_assist_gridtable_del" class="delbutton" href="javaScript:"><span>删除</span></a></li>
			</ul>
		</div>
		<div id="balance_assist_gridtable_div" layoutH="120"
			style="margin-left: 2px; margin-top: 2px; overflow: hidden"
			buttonBar="optId:abstractId;width:500;height:500">
			<input type="hidden" id="balance_assist_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="balance_assist_gridtable_addTile">
				<s:text name="balanceNew.title"/>
			</label> 
			<label style="display: none"
				id="balance_assist_gridtable_editTile">
				<s:text name="balanceEdit.title"/>
			</label>
			<label style="display: none"
				id="balance_assist_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="balance_assist_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
	<div id="load_balance_assist_gridtable" class='loading ui-state-default ui-state-active'></div>
	<input type="hidden" name="tory_is_a_good_man"  value="jsdlfjsaldjfkdlsjlk"/>
 	<table id="balance_assist_gridtable"></table>
		<div id="balancePager"></div>
	</div>
	</div>
	</div>
</div>
		<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" onclick="closeBalanceForm()"><s:text name="button.confirm" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
</div>