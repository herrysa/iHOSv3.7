
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>

<style type="text/css">
</style>
<script type="text/javascript">
 
	var balanceLayout;
			  var isBalanceGridIdString="#isBalance_gridtable";
	
	jQuery(document).ready(function() { 
		balanceLayout = makeLayout({
			baseName: 'balance', 
			tableIds: 'isBalance_gridtable'
		}, null);
var isBalanceGrid = jQuery(isBalanceGridIdString);
    isBalanceGrid.jqGrid({
    	url : "isBalanceGrid",
		datatype : "json",
		mtype : "GET",
        colModel:[
             
{name:'acctType',index:'balanceId',align:'center',label : '<s:text name="isBalance.acctType" />',hidden:false,formatter:'string'},				
{name:'direction',index:'direction',align:'center',label : '<s:text name="isBalance.direction" />',hidden:false,formatter:'string',width:'50px'},				
{name:'balanceTotal',index:'balanceTotal',align:'right',label : '<s:text name="isBalance.balanceTotal" />',hidden:false,formatter:'number'},				
        ],
        jsonReader : {
			root : "isBalanceCheckList", // (2)
			repeatitems : false
		// (4)
		},
        sortname: 'balanceId',
        //caption:'<s:text name="balanceList.title" />',
        gridview:true,
        rownumbers:true,
        loadui: "disable",
		multiboxonly:true,
		shrinkToFit:true,
		autowidth:true,
		navigatorSearch:false,
		 gridComplete:function(){
			
       	} 
    });
    jQuery(isBalanceGrid).jqGrid('bindKeys');
  	});
</script>


<div class="page">
<div id="balance_container">
			<div id="balance_layout-center"
				>
	
	<div class="pageContent">
			<input type="hidden" id="isBalance_gridtable_navTabId" value="${sessionScope.navTabId}">
 <table id="isBalance_gridtable"></table>
		<div id="balancePager"></div>
		<div class="pageFormContent" layoutH="230">
			<div style="width:60%;float:left">
				<div class="unit">
				<label style="width:200px">${jieStr }</label>
				<span style="line-height:22px;color:red">${jieTotal}</span>
				</div>
				<div class="unit">
				<label style="width:200px">${daiStr }</label>
				<span style="line-height:22px;color:green">${daiTotal}</span>
				</div>
			</div>
			<div  style="width:30%;float:right">
				<div class="unit">
				<c:if test="${isBalance==true }"><font size="12" color="green"><b>平衡</b></font></c:if>
				<c:if test="${isBalance==false }"><font size="12" color="red"><b>不平衡</b></font></c:if>
				</div>
				
			</div>
			
		</div>
	<div>
	</div>
	<div class="formBar">
				
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();"><s:text name="button.confirm" /></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
	</div>
</div>
</div>
</div>