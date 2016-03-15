
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var accountLayout;
	var accountGridIdString="#account_gridtable";
	var accountTreeStr="";
	
	jQuery(document).ready(function() { 
		var initFlag = 0;
		var accountGrid = jQuery(accountGridIdString);
    	accountGrid.jqGrid({
    		url : "accountGridList",
    		editurl:"accountGridEdit",
			datatype : "json",
			mtype : "GET",
        	colModel:[
			{name:'acctId',index:'acctId',align:'center',label : '<s:text name="account.acctId" />',hidden:true,key:true},
			{name:'acctCode',index:'acctCode',align:'left',width:90,label : '<s:text name="account.acctCode" />',hidden:false,highsearch:true},
			{name:'acctname',index:'acctname',align:'left',width:180,label : '<s:text name="account.acctname" />',hidden:false,highsearch:true},
			{name:'cnCode',index:'cnCode',align:'center',label : '<s:text name="account.cnCode" />',hidden:true,highsearch:true},
			{name:'isUsed',index:'isUsed',align:'center',width:60,label : '<s:text name="account.used" />',hidden:false,formatter:'checkbox',highsearch:true},
			{name:'assistTypes',index:'assistTypes',align:'left',width:120,label : '<s:text name="account.assistTypes" />',hidden:false,highsearch:true},
			{name:'acctNature',index:'acctNature',align:'center',width:80,label : '<s:text name="account.acctNature" />',hidden:false,highsearch:true},
			{name:'accttype.accounttype',index:'accttype.accounttype',width:120,align:'center',label : '<s:text name="account.accttypecode" />',hidden:false,highsearch:true},
			{name:'direction',index:'direction',align:'center',width:80,label : '<s:text name="account.direction" />',hidden:false,highsearch:true},
			{name:'build_date',index:'build_date',align:'center',width:100,label : '<s:text name="account.build_date" />',hidden:false,highsearch:true},
			{name:'modi_date',index:'modi_date',align:'center',width:100,label : '<s:text name="account.modi_date" />',hidden:false,highsearch:true}
			],
        	jsonReader : {
				root : "accounts", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
				// (4)
			},
        	sortname: 'acctCode',
        	viewrecords: true,
        	sortorder: 'asc',
        	//caption:'<s:text name="accountList.title" />',
        	height:300,
        	gridview:true,
        	rownumbers:true,
        	loadui: "disable",
        	multiselect: true,
			multiboxonly:true,
			shrinkToFit:true,
			autowidth:true,
        	onSelectRow: function(rowid) {
       		},
		 	gridComplete:function(){
		 		
		 		initFlag = initColumn("account_gridtable","com.huge.ihos.accounting.account.model.Account",initFlag);
	           	if(jQuery(this).getDataIDs().length>0){
	           	  jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
	           	 }
	           	var dataTest = {"id":"account_gridtable"};
	      	   	jQuery.publish("onLoadSelect",dataTest,null);
	      	   	makepager("account_gridtable");
       		} 
    	});
    jQuery(accountGrid).jqGrid('bindKeys');
    
    /* setTimeout(function(){
    	initColumn("account_gridtable","com.huge.ihos.accounting.account.model.Account");
    },200); */
    
    
    
    jQuery("#account_assistTypes").treeselect({
			dataType:"sql",
			optType:"multi",
			sql:"SELECT assistTypeCode id,assistTypeName name,1 parentId FROM GL_assistType",
			exceptnullparent:false,
			lazy:false
		});
    
	jQuery("#account_cnCode").autocomplete(
			"autocomplete",
			{
				width : 300,
				multiple : false,
				autoFill : false,
				matchContains : true,
				matchCase : true,
				dataType : 'json',
				parse : function(test) {
					var data = test.autocompleteList;
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
								data : data[i].acctCode + ","
										+ data[i].cnCode + ","
										+ data[i].acctname,
								value : data[i].cnCode,
								result : data[i].acctname
							};
						}
						return rows;
					}
				},
				extraParams : {
					flag : 2,
					entity : "Account",
					cloumnsStr : "acctCode,cnCode,acctname",
				},
				formatItem : function(row) {
					return dropId(row);
				},
				formatResult : function(row) {
					return dropId(row);
				}
			});
	jQuery("#account_cnCode").result(function(event, row, formatted) {
		if (row == "没有结果") {
			return;
		}
		jQuery("#account_cnCodeToSend").attr("value", getId(row).split(",")[1]);
	});
	refreshAccountTree();
    var accountFullSize = jQuery("#container").innerHeight()-118;
	jQuery("#account_container").css("height",accountFullSize);
	$('#account_container').layout({ 
		applyDefaultStyles: false ,
		west__size : 250,
		spacing_open:5,//边框的间隙  
		spacing_closed:5,//关闭时边框的间隙 
		resizable :true,
		resizerClass :"ui-layout-resizer-blue",
		slidable :true,
		resizerDragOpacity :1, 
		resizerTip:"可调整大小",//鼠标移到边框时，提示语
		onresize_end : function(paneName,element,state,options,layoutName){
			//zzhJsTest.debug("resize:"+paneName);
			if("center" == paneName){
				gridResize(null,null,"account_gridtable","single");
			}
		}
		
	});
	var edit_URL = "editAccount" , tableId = "account_gridtable" , width=660 , height = 470;
	jQuery("#"+tableId+"_addlocal").unbind( 'click' ).bind("click",function(){
		if(selectId==-1){
			selectId="";
		}
		var url = edit_URL+"?popup=true&acountTypeId="+acountTypeId+"&accountCode="+selectId+"&navTabId="+tableId;
		var winTitle="<s:text name='accountNew.title'/>";
		// alert(url);
		url = encodeURI(url);
		$.pdialog.open(url, 'addAccount', winTitle, {mask:false,width:width,height:height,resizable:false,maxable:false});　
	});
  	});
	function refreshAccountTree(){
		jQuery.ajax({
		    url: 'makeAccountTree',
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		        alert(data);
		    },
		    success: function(data){
		        //alert(data.ztreeList);
		        setTimeout(function(){
		        	accountTree = jQuery.fn.zTree.init(jQuery("#accountTree"), ztreesetting_account,data.ztreeList);
		        	var rootnode = accountTree.getNodeByParam("id","-1",null);
		        	accountTree.selectNode(rootnode);
		        },100);
		    }
		});
	}
	var ztreesetting_account = {
			view : {
				showLine : true,
				selectedMulti : false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onClick: reloadAccountGrid
			}
			
		};
	var selectId = "" , acountTypeId = "";
	function reloadAccountGrid(e, treeId, treeNode){
		var treeId = treeNode.id;
		if(treeId.indexOf("_")!=-1){
			var tpArr = treeId.split("_");
			acountTypeId = treeId;
			treeId = tpArr[tpArr.length-1];
		}else{
			acountTypeId = "";
		}
		selectId = treeId;
		urlString = "accountGridList";
		if(treeId!='-1'){
			urlString += "?filter_LIKES_acctCode="+treeId+"*";
		}
		//alert(urlString);
		urlString=encodeURI(urlString);
		jQuery("#account_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}

	function remapColumns(){
		jQuery("#account_gridtable").jqGrid('remapColumns',[0,1,3,2,5,4,6,7,8,9,10,12,11],true);
	}
	
	function propertyFilterSearchInAccountList(searchAreaId,gridId){
		var originalStr = jQuery("#account_assistTypes_id").val();
		var newStr = originalStr.trim();
		if(originalStr!='' && originalStr.charAt(0)!='*'){
			newStr = "*,"+originalStr+",*";
		}
		jQuery("#account_assistTypes_id").val(newStr);
		propertyFilterSearch(searchAreaId,gridId);
	}

	function refreshTree(data){
		refreshAccountTree();
		formCallBack(data);
	}
	
	function accountDel(tableId){
		var buttonBar ;
		if(typeof(jQuery("#"+tableId+"_div").attr("buttonBar"))!="undefined"){
			buttonBar = jQuery("#"+tableId+"_div").attr("buttonBar");
		}
		var fatherGrid = getParam('fatherGrid',buttonBar);
		var extraParam = getParam('extraParam',buttonBar);
		var selectNone = jQuery("#"+tableId+"_selectNone").text();
		var sid = jQuery("#"+tableId).jqGrid('getGridParam','selarrrow');
		var editUrl = jQuery("#"+tableId).jqGrid('getGridParam', 'editurl');
		editUrl+="?id=" + sid+"&navTabId="+tableId+"&oper=del";
		if(fatherGrid!=null&&fatherGrid!=""&&extraParam!=null&&extraParam!=""){
			var fatherGridId = jQuery("#"+fatherGrid).jqGrid('getGridParam','selarrrow');
			editUrl += "&"+extraParam+"="+fatherGridId;
		}
		editUrl = encodeURI(editUrl);
	    if(sid==null || sid.length ==0){
				alertMsg.error(selectNone);
				return;
		}else{
			// jQuery("#"+tableId).jqGrid('delGridRow',sid,{reloadAfterSubmit:false,left:300,top:150});
			alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, refreshTree, "json");
					
				}
			});
		}
	}
	
	function initAccount(){
		jQuery.ajax({
		    url: 'initAccount',
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    	alertMsg.error(data.message);
		    },
		    success: function(data){
		    	alertMsg.correct(data.message);
		    	navTab.reload("accountList", {
					title : "",
					fresh : false,
					data : {}
				});
		    }
		});
	}
	
</script>
<style>
.divborder {
    padding:2px 2px 2px 2px;
    margin:2px 2px 2px 2px;
    height:100%;
    color:#333; 
    border:#c5dbec solid 1px;
} 
</style>
<div class="page">
	<div id="account_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
				<form id="account_search_form" >
					
					<label style="float:none;white-space:nowrap" >
						<s:text name='account.acctCode'/>:
						<input type="text" name="filter_EQS_acctCode"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='account.acctname'/>:
						<input type="text" name="filter_LIKES_acctname"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='account.assistTypes'/>:
						<input id="account_assistTypes_id" type="hidden" name="filter_LIKES_assistTypes"/>
						<input id="account_assistTypes" type="text" name="filter_exclude_assistTypes" style="width:150px"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='account.acctNature'/>:
						<input type="text" name="filter_EQS_acctNature"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='account.cash'/>:
						<s:select list="#{'':'全部','true':'是','false':'否'}" name="filter_EQB_cash" style="width:60px"></s:select>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='account.cnCode'/>:
						<input id="account_cnCode" type="text"  value="拼音/汉字/编码" size="14"
						class="defaultValueStyle" onblur="setDefaultValue(this,jQuery('#account_cnCodeToSend'))" 
						onfocus="clearInput(this,jQuery('#account_cnCodeToSend'))" onkeyDown="setTextColor(this)"/>
						<s:hidden id="account_cnCodeToSend" name="filter_EQS_cnCode"/>
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='account.isProfitLoss'/>:
						<s:select list="#{'':'全部','true':'是','false':'否'}" name="filter_EQB_isProfitLoss" style="width:60px"></s:select>
					</label>&nbsp;&nbsp;
					
					<label style="float:none;white-space:nowrap" >
						<s:text name='account.used'/>:
						<s:select list="#{'':'全部','true':'使用','false':'停用'}" name="filter_EQB_isUsed" style="width:60px"></s:select>
					</label>
					<div class="buttonActive" style="float:right">
								<div class="buttonContent">
									<button type="button" onclick="highSearch('account_gridtable','com.huge.ihos.accounting.account.model.Account')"><s:text name='button.higher'/></button>
								</div>
							</div>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="propertyFilterSearchInAccountList('account_search_form','account_gridtable')"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
				</div>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="propertyFilterSearchInAccountList('account_search_form','account_gridtable')"><s:text name='button.search'/></button>
								</div>
							</div>
						</li>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="highSearch('account_gridtable','com.huge.ihos.accounting.account.model.Account')"><s:text name='button.higher'/></button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="account_gridtable_addlocal" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a class="delbutton" onclick="accountDel('account_gridtable')" ><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="account_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<c:if test="${initAccountStatus=='init'}">
				<li><a id="initAccount" class="changebutton"  href="javaScript:initAccount()"
					><span><s:text name="account.initAccount" />
					</span>
				</a>
				</li>
				</c:if>
				<%-- <li><a class="changebutton"  href="javaScript:remapColumns()"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li> --%>
			
			</ul>
		</div>
		<div id="account_container">
		<div id="accout_layout-west" class="pane ui-layout-west" 
			style="float: left; display: block; overflow: auto;">

			<DIV id="accountTree" class="ztree"></DIV>

		</div>
		<div id="account_layout-center" class="pane ui-layout-center">
		<div id="account_gridtable_div" layoutH="120" class="grid-wrapdiv" class="unitBox"  buttonBar="width:660;height:470">
			<input type="hidden" id="account_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="account_gridtable_addTile">
				<s:text name="accountNew.title"/>
			</label> 
			<label style="display: none"
				id="account_gridtable_editTile">
				<s:text name="accountEdit.title"/>
			</label>
			<div id="load_account_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 			<table id="account_gridtable"></table>
			<!--<div id="accountPager"></div>-->
		</div>
		<div class="panelBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="account_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="account_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>
		<div id="account_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1">
		</div>
		</div>
		</div>
		</div>
	</div>
</div>