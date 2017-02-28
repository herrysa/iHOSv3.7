<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<%-- <script type="text/javascript" src="${ctx}/scripts/combogrid/resources/plugin/jquery.ui.combogrid-1.6.2.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="${ctx}/scripts/combogrid/resources/css/smoothness/jquery-ui-1.8.9.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${ctx}/scripts/combogrid/resources/css/smoothness/jquery.ui.combogrid.css" />
<link href="${ctx}/dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<script src="${ctx}/dwz/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script> --%>

<script type="text/javascript">
	$(function() {
		jQuery('#savelink')
		.click(
				function() {
					jQuery("#inventoryDictBatchForm")
							.attr(
									"action",
									"inventoryDictBatchSave?popup=true&navTabId="
											+ '${navTabId}&entityIsNew=${entityIsNew}');
					jQuery("#inventoryDictBatchForm")
							.submit();
				});
		
		tableToGrid("#inv_batch_edit", {

			colModel : [
			            {
							name : '属性',
							sortable : false,
							width : 20,
							align:'center'
						},
			            {
				name : '选用',
				sortable : false,
				width : 150,
				align:'center'
			} ],
			height:580,
			width:480

		});
		$("#inventoryDict_vendor")
		.combogrid(
				{
					url : '${ctx}/comboGridSqlList',
					queryParams : {
						sql : "select VENDORID,VENDORCODE,VENDORNAME from sy_vendor where orgCode='"+"${fns:userContextParam('orgCode')}"+"' and disabled = 0 and isMate = 1",
						cloumns : 'VENDORNAME,VENDORCODE,cncode'
					},
					 autoFocus : false,
					autoChoose : false,
					debug : true,
					resetButton : false,
					resetFields : null,
					searchButton : false,
					searchIcon : true,
					okIcon : false,
					showOn : true, 
					rows:10,
					width:380,
					sidx:"VENDORCODE",
					colModel : [ {
						'columnName' : 'VENDORID',
						'width' : '0',
						'label' : 'VENDORID',
						hidden : true,
					}, {
						'columnName' : 'VENDORCODE',
						'width' : '30',
						'align' : 'left',
						'label' : '供货商编码'
					},{
						'columnName' : 'VENDORNAME',
						'width' : '60',
						'align' : 'left',
						'label' : '供货商名称'
					}
					],
					select : function(event, ui) {
						$("#inventoryDict_vendor_id").val(ui.item.VENDORID);
						$(event.target).val(ui.item.VENDORNAME);
						
						return false;
					}
				});
		/* jQuery("#inventoryDict_vendor").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT v.vendorId id,v.vendorName name   from sy_vendor v ",
			exceptnullparent : false,
			lazy : false
		}); */
		// $("#inv_batch_edit").jqGrid
		//console.log(invGrid);
		//var cm=jQuery('#inv_batch_edit').jqGrid('getGridParam','colModel');
		//var cm = invGrid.getGridParam("colModel");
		//console.log(cm);
		/* for (var i = 0, l=cm.length; i < l; i += 1) {
		    var colModelColumn = cm[i];
		    // search for the clolumn with the name colModelColumnName.
		    // variables colModelColumnName and selectedOptions are defined
		    // in another code fragment above this one
		    if (colModelColumn.name === colModelColumnName) {
		        jQuery.extend(colModelColumn, { sortable: false
		                                        });
		        break;
		    }
		} */
		
		

	});
	function invBatchCheckBoxClick(obj) {
		//console.log(obj.id);
		var chId = obj.id;
		var inpId = chId.substr(0, chId.length - 6);
		// console.log(chId);
		// console.log(inpId);
		if ($("#" + chId).prop("checked")) {
			$("#" + inpId).attr("disabled", false);
			if(inpId=="invDict_invType_batchEdit"){
				$("#" + inpId).treeselect({
					dataType:"sql",
					optType:"single",
					sql:"SELECT  InvtypeId id,Invtype name,parent_id parent FROM mm_invType where parent_id is not null and disabled = 0 and orgCode='"+"${fns:userContextParam('orgCode')}"+"' and copyCode='"+"${fns:userContextParam('copyCode')}"+"'",
					exceptnullparent:false,
					lazy:false
				});
			}
		} else {
			$("#" + inpId).attr("disabled", true);
			$("#" + inpId).val("");

		}

	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="inventoryDictBatchForm" method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this,formCallBack);">
		
		<s:hidden name="batchIds"></s:hidden>
		
			<div class="pageFormContent" layoutH="40" style="padding: 0px;">
				<div class="tabsContent" layoutH="50">
					<table id="inv_batch_edit">
						<thead>
							<tr>
								<th>选用</th>
								<th>属性</th>
							</tr>
						</thead>
						<tr>
							<td><s:textfield id="inventoryDict_agent" key="inventoryDict.agent" name="inventoryDict.agent" disabled="true" /></td>
							<td><input type="checkbox" id="inventoryDict_agent_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						<tr>
							<td><s:textfield id="inventoryDict_planPrice" key="inventoryDict.planPrice" name="inventoryDict.planPrice" cssClass="number" disabled="true" /></td>
							<td><input type="checkbox" id="inventoryDict_planPrice_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						<tr>
							<td><s:textfield id="inventoryDict_invName" key="inventoryDict.invName" name="inventoryDict.invName" disabled="true" /></td>
							<td><input type="checkbox" id="inventoryDict_invName_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						<tr>
							<td><s:hidden id="invDict_invType_batchEdit_id" key="inventoryDict.inventoryType" name="inventoryDict.inventoryType.id"  /> <s:textfield id="invDict_invType_batchEdit" key="inventoryDict.inventoryType" name="inventoryDict.inventoryType.invTypeName" disabled="true" /></td>
							<td><input type="checkbox" id="invDict_invType_batchEdit_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						<tr>
							<td><s:textfield id="inventoryDict_refCost" key="inventoryDict.refCost" name="inventoryDict.refCost" cssClass="number" disabled="true" /></td>
							<td><input type="checkbox" id="inventoryDict_refCost_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						<tr>
							<td>	<s:textfield id="inventoryDict_ecoBat" key="inventoryDict.ecoBat" name="inventoryDict.ecoBat" cssClass="number" disabled="true" /></td>
							<td><input type="checkbox" id="inventoryDict_ecoBat_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						<tr>
							<td>		<s:select id="inventoryDict_abc" key="inventoryDict.abc" name="inventoryDict.abc" listKey="key" listValue="value"
									list="#{'A':'特别重要的库存（A类）','B':'一般重要的库存（B类）','C':'不重要的库存（C类）'}" cssStyle="float: left; width: 134px"  disabled="true" /></td>
							<td><input type="checkbox" id="inventoryDict_abc_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						<tr>
							<td><s:hidden id="inventoryDict_vendor_id" key="inventoryDict.vendor" name="inventoryDict.vendor.vendorId" /> <s:textfield id="inventoryDict_vendor" key="inventoryDict.vendor"
									name="inventoryDict.vendor.vendorName" disabled="true"/></td>
							<td><input type="checkbox" id="inventoryDict_vendor_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						<tr>
							<td>	<s:textfield id="inventoryDict_safeStock" key="inventoryDict.safeStock" name="inventoryDict.safeStock" cssClass="digits" disabled="true" /></td>
							<td><input type="checkbox" id="inventoryDict_safeStock_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						<tr>
							<td>	<s:textfield id="inventoryDict_lowLimit" key="inventoryDict.lowLimit" name="inventoryDict.lowLimit" cssClass="digits" disabled="true" /></td>
							<td><input type="checkbox" id="inventoryDict_lowLimit_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						<tr>
							<td>	<s:textfield id="inventoryDict_highLimit" key="inventoryDict.highLimit" name="inventoryDict.highLimit" cssClass="digits" disabled="true" /></td>
							<td><input type="checkbox" id="inventoryDict_highLimit_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						<tr>
							<td>	<s:textfield id="inventoryDict_refPrice" key="inventoryDict.refPrice" name="inventoryDict.refPrice" cssClass="number"  disabled="true" /></td>
							<td><input type="checkbox" id="inventoryDict_refPrice_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						<tr>
							<td>	<s:textfield id="inventoryDict_brandName" key="inventoryDict.brandName" name="inventoryDict.brandName"   disabled="true" /></td>
							<td><input type="checkbox" id="inventoryDict_brandName_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						<tr>
							<td>	<s:textfield id="inventoryDict_startDate" key="inventoryDict.startDate" name="inventoryDict.startDate" cssClass="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})"  disabled="true" /></td>
							<td><input type="checkbox" id="inventoryDict_startDate_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						<tr>
							<td>	<s:textfield id="inventoryDict_endDate" key="inventoryDict.endDate" name="inventoryDict.endDate" cssClass="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" disabled="true" /></td>
							<td><input type="checkbox" id="inventoryDict_endDate_check" onclick="javascript:invBatchCheckBoxClick(this);" /></td>
						</tr>
						
					
						
					</table>

				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="savelink">

									<s:text name="button.save" />
								</button>
							</div>
						</div></li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="Button" onclick="$.pdialog.closeCurrent();">
									<s:text name="button.cancel" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>

	</div>
</div>





