
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="${ctx}/scripts/autocomplete/jquery.autocomplete3.js"></script>
<link rel="stylesheet"
	href="${ctx}/scripts/autocomplete/jquery.autocomplete.css" />
<script type="text/javascript">
 
	function specialSourceGridReload(){
		var urlString = "specialSourceGridList";
		var specialStartId = jQuery("#specialStartId").val();
		var specialEndId = jQuery("#specialEndId").val();
		var departmentIdCXS = jQuery("#departmentIdCXS").val();
		var specialSourceCX = jQuery("#specialSourceCX").val();
		var costOrg = jQuery("#specialSource_costOrg_id").val();
		var cbBranch = jQuery("#specialSource_cbBranch").val();
		urlString=urlString+"?filter_GES_checkPeriod="+specialStartId+"&filter_LES_checkPeriod="+specialEndId+"&filter_EQS_deptId.departmentId="+departmentIdCXS+"&filter_INS_costOrg.orgCode="+costOrg+"&filter_EQS_cbBranch.branchCode="+cbBranch+"&filter_EQS_itemId.itemId="+specialSourceCX;
		var herpType = "${fns:getHerpType()}";
		if(herpType == "S2") {
			var cbBranchDP = "${fns:u_readDPSql('cbdata_branch')}";
			var operatorId = "${fns:userContextParam('loginPersonId')}";
			var sqlString = "SpecialSourceId in (SELECT t.SpecialSourceId from T_SpecialSource t where t.operatorId ='" +operatorId+ "'";
			sqlString += " OR t.cbBranchCode in " + cbBranchDP;
			sqlString += " )";
			urlString += "&filter_SQS_SpecialSourceId=" + sqlString;
		}
		urlString=encodeURI(urlString);
		jQuery("#specialSource_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	var specialSourceLayout;
			  var specialSourceGridIdString="#specialSource_gridtable";
	
	jQuery(document).ready(function() {
		var initFlag = 0;
		specialSourceLayout = makeLayout({
			baseName: 'specialSource', 
			tableIds: 'specialSource_gridtable'
		}, null);
var specialSourceGrid = jQuery(specialSourceGridIdString);
	var urlString = "specialSourceGridList?filter_GES_checkPeriod=${currentPeriod}&filter_LES_checkPeriod=${currentPeriod}";
	var herpType = "${fns:getHerpType()}";
	if(herpType == "S2") {
		var cbBranchDP = "${fns:u_readDPSql('cbdata_branch')}";
		var operatorId = "${fns:userContextParam('loginPersonId')}";
		var sqlString = "SpecialSourceId in (SELECT t.SpecialSourceId from T_SpecialSource t where t.operatorId ='" +operatorId+ "'";
		sqlString += " OR t.cbBranchCode in " + cbBranchDP;
		sqlString += " )";
		urlString += "&filter_SQS_SpecialSourceId=" + sqlString;
	}
    specialSourceGrid.jqGrid({
    	url : urlString,
    	editurl:"specialSourceGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
{name:'specialSourceId',index:'specialSourceId',align:'center',label : '<s:text name="specialSource.specialSourceId" />',hidden:true,key:true,formatter:'integer',highsearch:false},				
{name:'operatorId',index:'operatorId',align:'center',label : '<s:text name="specialSource.operatorId" />',hidden:true,highsearch:false},				
{name:'checkPeriod',index:'checkPeriod',align:'center',label : '<s:text name="specialSource.checkPeriod" />',hidden:false,width:'80',highsearch:true},				
{name:'deptId.name',index:'deptid1',align:'left',label : '<s:text name="specialSource.deptId" />',hidden:false,highsearch:true},				
<c:if test="${herpType == 'M'}">
{name:'costOrg.shortName',index:'costOrg.shortName',align:'left',width:"130",label : '<s:text name="sourcecost.costOrg" /><s:text name="hisOrg.orgName" />',hidden:false,highsearch:true},				
</c:if>
<c:if test="${herpType == 'S2'}">
{name:'cbBranch.branchName',index:'cbBranch.branchName',align:'left',width:"130",label : '<s:text name="sourcecost.costOrg" /><s:text name="hisOrg.branchName" />',hidden:false,highsearch:true},				
</c:if>
{name:'itemId.itemType',index:'itemId.itemType',align:'center',width:70,label : '<s:text name="specialItem.itemType" />',hidden:false,highsearch:true},				
{name:'itemId.itemName',index:'itemId.itemName',align:'left',width:130,label : '<s:text name="specialItem.itemName" />',hidden:false,highsearch:true},				
{name:'amount',index:'amount',align:'right',width:'75',label : '<s:text name="specialSource.amount" />',hidden:false,formatter:'number',formatoptions:{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 2, prefix: '', suffix:'', defaultValue: '0.00'},highsearch:true},				
{name:'operatorName',index:'operatorName',align:'center',width:'70',label : '<s:text name="specialSource.operatorName" />',hidden:false,highsearch:true},				
{name:'processDate',index:'processDate',align:'center',label : '<s:text name="specialSource.processDate" />',hidden:false,formatter:'date',formatoptions:{ newformat: 'Y-m-d'},width:120,highsearch:true},				
{name:'deptid1.name',index:'deptid1',align:'left',label : '<s:text name="specialSource.deptid1" />',hidden:true,highsearch:true},				
{name:'deptid2.name',index:'deptid2',align:'left',label : '<s:text name="specialSource.deptid2" />',hidden:true,highsearch:true},				
{name:'deptid3.name',index:'deptid3',align:'left',label : '<s:text name="specialSource.deptid3" />',hidden:true,highsearch:true},				
{name:'status',index:'status',align:'left',width:"60",label : '<s:text name="sourcepayin.status" />',formatter:'select',editoptions:{value:"0:新建;1:已结帐"},hidden:true,highsearch:true},
{name:'remark',index:'remark',align:'left',label : '<s:text name="specialSource.remark" />',hidden:false,highsearch:true}				

        ],
        jsonReader : {
			root : "specialSources", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        gridview:true,
        rownumbers:true,
        sortname: 'specialSourceId',
        viewrecords: true,
        sortorder: 'desc',
        //caption:'<s:text name="specialSourceList.title" />',
        height:300,
        loadui: "disable",
        multiselect: true,
		multiboxonly:false,
		shrinkToFit:false,
		autowidth:false,
		onSelectAll:function() {
           var dataTest = {"id":"specialSource_gridtable"};
       	   jQuery.publish("chargeValidateStatu",dataTest,null);
       	},
        onSelectRow: function() {
        	var dataTest = {"id":"specialSource_gridtable"};
       	   jQuery.publish("chargeValidateStatu",dataTest,null);
       	},
		 gridComplete:function(){
			 gridContainerResize("specialSource","div");
			 var rowNum = jQuery(this).getDataIDs().length;
			 if(rowNum <= 0) {
				 var tw = jQuery("#specialSource_gridtable").outerWidth();
				 jQuery("#specialSource_gridtable").parent().width(tw);
				 jQuery("#specialSource_gridtable").parent().height(1);

			 }
/*            if(jQuery(this).getDataIDs().length>0){
              jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
 */           var dataTest = {"id":"specialSource_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("specialSource_gridtable");
      	 initFlag = initColumn('specialSource_gridtable','com.huge.ihos.inout.model.SpecialSource',initFlag);
      	 var sumDataJson = jQuery("#specialSource_gridtable").getGridParam('userData');
      	var sumDataStr = sumDataJson.amount;
			jQuery("#allSumSpecialSource").text(formatNum(parseFloat(sumDataStr).toFixed(2)));
			var amount = jQuery("#specialSource_gridtable").getCol('amount',false,'sum');
			//alert(amount);
			jQuery("#pageSumSpecialSource").text(formatNum(amount.toFixed(2)));
			
			jQuery("#specialSource_gridtable").find("tr").each(function(){
				//alert("S:"+checkPeriod);
				var checkPeriod= jQuery(this).find("td").eq(4).text();
				//alert("Q:"+checkPeriod);
				var priviliage = checkPriviliaged(checkPeriod);
				if(priviliage){
					jQuery(this).find("td").each(function(){
						if(jQuery(this).children().eq(0).attr("type")=="checkbox"){
							jQuery(this).children().eq(0).attr("disabled","true");
						}
				});
				}
			});
      	   
       	}

    });
	    jQuery(specialSourceGrid).jqGrid('bindKeys');
	    var amountSum = "${amountSum}";
		jQuery("#allSumSpecialSource").text(parseFloat(amountSum).toFixed(2));
		
  	});
	
	jQuery(function() {
		var orgPriv = "${fns:u_readDPSql('org_dp')}";
		var branchPriv = "${fns:u_readDPSql('branch_dp')}";
		var orgPrivSql = "and orgCode in "+orgPriv;
		if(orgPriv.indexOf("select") != -1 || orgPriv.indexOf("SELECT") != -1){
			orgPrivSql = "";
		}
		var branchPrivSql = " and branchCode in "+branchPriv;
		if(${herpType=="S1"}||branchPriv.indexOf("select") != -1 || branchPriv.indexOf("SELECT") != -1){
			branchPrivSql = "";
		}
		var extra1Str = " leaf=true and disabled=false and departmentId !='XT' "+orgPrivSql+branchPrivSql+" and ( ";
		jQuery("#departmentIdCXSC").autocomplete(
				"autocomplete",
				{
					width : 300,
					multiple : false,
					autoFill : false,
					matchContains : true,
					matchCase : true,
					dataType : 'json',
					parse : function(test) {
						//alert(test.dicList.length)
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
									data : data[i].departmentId + ","
									<c:if test="${herpType == 'M'}" >
										+ data[i].org.orgname + ","
									</c:if>
									<c:if test="${herpType == 'S2'}" >
										+ data[i].branchName + ","
									</c:if>
											+ data[i].deptCode + ","
											+ data[i].cnCode + ","
											+ data[i].name + ":"
											+ data[i].departmentId,
									value : data[i].name,
									result : data[i].name
								};
							}
							return rows;
						}
					},
					extraParams : {
						flag : 2,
						entity : "Department",
						cloumnsStr : "departmentId,deptCode,name,cnCode",
						extra1 : extra1Str,
						extra2 : ")"
					},
					formatItem : function(row) {
						return dropId(row);
					},
					formatResult : function(row) {
						return dropId(row);
					}
				});
		jQuery("#departmentIdCXSC").result(function(event, row, formatted) {
			if (row == "没有结果") {
				return;
			}
			jQuery("#departmentIdCXS").attr("value", getId(row));
			//alert(jQuery("#zxDeptId").attr("value"));
		});
		
	});
	jQuery(function() {
		/*----------------------------------tooBar start -----------------------------------------------*/
     	var specialSource_menuButtonArrJson = "${menuButtonArrJson}";
		var specialSource_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(specialSource_menuButtonArrJson,false)));
		var specialSource_toolButtonBar = new ToolButtonBar({el:$('#specialSource_buttonBar'),collection:specialSource_toolButtonCollection,attributes:{
			tableId : 'specialSource_gridtable',
			baseName : 'specialSource',
			width : 550,
			height : 400,
			base_URL : null,
			optId : null,
			fatherGrid : null,
			extraParam : null,
			selectNone : "请选择记录。",
			selectMore : "只能选择一条记录。",
			addTitle : '<s:text name="specialSourceNew.title"/>',
			editTitle : '<s:text name="specialSourceEdit.title"/>'
		}}).render();
		
		var specialSource_function = new scriptFunction();
		specialSource_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.selectRecord){
				var sid = jQuery("#specialSource_gridtable").jqGrid('getGridParam','selarrrow');
		        if(sid==null || sid.length ==0){
		        	alertMsg.error("请选择记录！");
					return pass;
				}
		        if(param.singleSelect){
		        	if(sid.length != 1){
			        	alertMsg.error("只能选择一条记录！");
						return pass;
					}
		        }
			}
	        return true;
		};
		//添加
		specialSource_toolButtonBar.addFunctionAdd('01022001');
		//删除
		specialSource_toolButtonBar.addFunctionDel('01022002');
		specialSource_toolButtonBar.addBeforeCall('01022002',function(e,$this,param){
			return specialSource_function.optBeforeCall(e,$this,param);
		},{selectRecord:'selectRecord'});
		//修改
		specialSource_toolButtonBar.addFunctionEdit('01022003');
		specialSource_toolButtonBar.addBeforeCall('01022003',function(e,$this,param){
			return specialSource_function.optBeforeCall(e,$this,param);
		},{selectRecord:'selectRecord',singleSelect:'singleSelect'});
		//设置表格列
		var specialSource_setColShowButton = {id:'01022020',buttonLabel:'设置表格列',className:"settlebutton",show:true,enable:true,
				callBody : function() {
					setColShow('specialSource_gridtable','com.huge.ihos.inout.model.SpecialSource');
				}};
		specialSource_toolButtonBar.addButton(specialSource_setColShowButton);
		
		/*----------------------------------tooBar end -----------------------------------------------*/
		var herpType = "${fns:getHerpType()}";
    	if(herpType == "M") {
    		//单位树
    		var orgNumber = "${orgNumber}";
    		if(orgNumber == 1) {
    			jQuery("#specialSource_costOrg_label").hide();
    		}
        	var orgPriv = "${fns:u_readDP('org_dp')}"; //cbdata_org
			//var orgPrivArr = orgPriv.split(",");
			var sql = "select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from T_Org where disabled=0 AND orgCode<>'XT'";
			if(orgPriv) {
				sql += " and orgCode in ${fns:u_readDPSql('org_dp')} ";
			} else {
				sql += " and 1=2 ";
			}
			sql += " ORDER BY orderCol"; 
	    	jQuery("#specialSource_costOrg").treeselect({
	    		optType : "multi",
				dataType : 'sql',
				sql : sql,
				exceptnullparent : true,
				lazy : false,
				minWidth : '180px',
				selectParent : false
	    	});
	    	/* if(orgPrivArr.length == 1 && orgPrivArr[0] != "") {
				jQuery("#specialSource_costOrg").attr("readonly","readonly");
				jQuery("#specialSource_costOrg").unbind('click');
			} */
    	} else {
    		jQuery("#specialSource_costOrg_label").hide();
    	}
    	
    });
function changeSpecialItemList(value){
	jQuery.ajax({
		dataType : 'json',
		url:encodeURI('changeSpecialItemList?itemTypeName='+value),
	    type: 'POST',
	    error: function(data){
	    	alertMsg.confirm("");
	    },success: function(data){
	    	$("#specialSourceCX").html(data["specialItemStr"]);
	    	//alert(data["specialItemStr"]);
	    }
	});  
}
  
    


</script>

<div class="page">
<div id="specialSource_container">
			<div id="specialSource_layout-center"
				class="pane ui-layout-center">
	<div id="specialSource_pageHeader" class="pageHeader">
			<div class="searchBar">
				<div class="searchContent">
					<label class="queryarea-label"><s:text name='specialSource.checkPeriod' />：从 <input
									class="input-mini" type="text" id="specialStartId"
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"
									value="${currentPeriod}" size="10" />到<input
									class="input-mini" type="text" id="specialEndId"
									onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"
									value="${currentPeriod}" size="10" /><s:hidden id="departmentIdCXS" />
					</label>
					
					<label class="queryarea-label"><s:text name='specialSource.deptId'/>： <input
									id="departmentIdCXSC" value="拼音/汉字/编码"
									onfocus="clearInput(this,jQuery('#departmentIdCXS'))"
									class="input-medium defaultValueStyle" onblur="setDefaultValue(this,jQuery('#departmentIdCXS'))" onkeydown="setTextColor(this)"/>
					</label>
					
					<label class="queryarea-label">
						 <s:text name='specialItem.itemType'/>:
						 <s:select id="specialItem_itemType_Source"  style="width:133px;" 	list="dicSpecialItemList" listKey="value" listValue="content" emptyOption="true" onchange="changeSpecialItemList(this.value)"></s:select>
					</label>
					
					<label class="queryarea-label">
						 <s:text name='specialSource.itemId'/>:
						 	<select id="specialSourceCX" name="specialSourceCX" style="width:140px;">
						 		<option value=""></option>
							 	<c:forEach items="${changeSpecialItemList}" var="csi">
											<option value="${csi.itemId}">${csi.itemName}</option>
								</c:forEach>
						 	</select>
					</label>
					<label class="queryarea-label" id="specialSource_costOrg_label">
						<fmt:message key="sourcecost.costOrg"></fmt:message><fmt:message key="hisOrg.orgName"></fmt:message>:
							<input type="text" id="specialSource_costOrg" />
							<input type="hidden" id="specialSource_costOrg_id"/>
					</label>
					<label class="queryarea-label" style="${herpType=='S2'?'':'display:none'}">
						<fmt:message key="sourcecost.costOrg"></fmt:message><fmt:message key="hisOrg.branchName"></fmt:message>:
							<s:select list="branches" headerKey="" headerValue="--" listKey="branchCode" listValue="branchName" id="specialSource_cbBranch" ></s:select>
							<!-- <input type="text" id="specialSource_cbBranch" />
							<input type="hidden" id="specialSource_cbBranch_id"/> -->
					</label>
					<div class="buttonActive" style="float:right">
								<div class="buttonContent">
									<button type="button" onclick="specialSourceGridReload()"><s:text name='button.search'/></button>
								</div>
							</div>
				</div>
				<%-- <div class="subBar">
					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="specialSourceGridReload()"><s:text name='button.search'/></button>
								</div>
							</div></li>
					
					</ul>
				</div> --%>
			</div>
	</div>
	<div class="pageContent">





<div class="panelBar" id="specialSource_buttonBar">
			<%-- <ul class="toolBar">
				<li><a id="specialSource_gridtable_add" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="specialSource_gridtable_del" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
				<li><a id="specialSource_gridtable_edit" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<li>
					<a class="settlebutton"  href="javaScript:setColShow('specialSource_gridtable','com.huge.ihos.inout.model.SpecialSource')"><span><s:text name="button.setColShow" /></span></a>
				</li>
			
			</ul> --%>
		</div>
		<div align="right"  style="margin-top: -13px;margin-right:5px">
				本页金额合计：<label id="pageSumSpecialSource"></label>总计：<label id="allSumSpecialSource" ></label>
				<br/>
		</div>
		<div id="specialSource_gridtable_div"
			class="grid-wrapdiv"
			buttonBar="optId:specialSourceId;width:550;height:400">
			<input type="hidden" id="specialSource_gridtable_navTabId" value="${sessionScope.navTabId}">
			<label style="display: none" id="specialSource_gridtable_addTile">
				<s:text name="specialSourceNew.title"/>
			</label> 
			<label style="display: none"
				id="specialSource_gridtable_editTile">
				<s:text name="specialSourceEdit.title"/>
			</label>
			<label style="display: none"
				id="specialSource_gridtable_selectNone">
				<s:text name='list.selectNone'/>
			</label>
			<label style="display: none"
				id="specialSource_gridtable_selectMore">
				<s:text name='list.selectMore'/>
			</label>
<div id="load_specialSource_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 <table id="specialSource_gridtable"></table>
		<div id="specialSourcePager"></div>
</div>
	</div>
	<div class="panelBar" id="specialSource_pageBar">
		<div class="pages">
			<span><s:text name="pager.perPage" /></span> <select id="specialSource_gridtable_numPerPage">
				<option value="20">20</option>
				<option value="50">50</option>
				<option value="100">100</option>
				<option value="200">200</option>
			</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="specialSource_gridtable_totals"></label><s:text name="pager.items" /></span>
		</div>

		<div id="specialSource_gridtable_pagination" class="pagination"
			targetType="navTab" totalCount="200" numPerPage="20"
			pageNumShown="10" currentPage="1"></div>

	</div>
</div>
</div>
</div>