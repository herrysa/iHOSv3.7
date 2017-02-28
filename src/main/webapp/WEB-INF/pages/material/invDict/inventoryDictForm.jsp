<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%-- <script type="text/javascript" src="${ctx}/scripts/combogrid/resources/plugin/jquery.ui.combogrid-1.6.2.js"></script>
<script type="text/javascript" src="${ctx}/scripts/combogrid/resources/plugin/jquery.i18n.properties-1.0.9.js"></script>
<link rel="stylesheet" type="text/css" media="screen" href="${ctx}/scripts/combogrid/resources/css/smoothness/jquery-ui-1.8.9.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="${ctx}/scripts/combogrid/resources/css/smoothness/jquery.ui.combogrid.css" /> --%>
<script>
	jQuery(document).ready(function(){
			jQuery('#inventoryDictSaveLink').click(function() {
				jQuery("#inventoryDictForm").attr("action","saveInventoryDict?popup=true&navTabId="
																	+ '${navTabId}&entityIsNew=${entityIsNew}');
				jQuery("#inventoryDictForm").submit();
			});
			var orgForInvDict = "${fns:userContextParam('orgCode')}";
			var copyForInvDict = "${fns:userContextParam('copyCode')}";
			jQuery("#inventoryDict_invType").treeselect({
				dataType:"sql",
				optType:"single",
				sql:"SELECT  InvtypeId id,Invtype name,parent_id parent FROM mm_invType where parent_id is not null and disabled = 0 and orgCode='"+orgForInvDict+"' and copyCode='"+copyForInvDict+"'",
				exceptnullparent:false,
				lazy:false
			}); 
		 	jQuery("#inventoryDict_vendor").combogrid({
		 		url : '${ctx}/comboGridSqlList',
		 		queryParams : {
		 			sql : "SELECT ltrim(v.vendorId) vid,v.vendorName vname,v.shortName sname,v.vendorCode vcode from sy_vendor v where orgCode='"+"${fns:userContextParam('orgCode')}"+"' and disabled = 0 and isMate = 1",
		 			cloumns : 'vendorName,cncode,vendorCode'
		 		},
		 		rows:10,
		 		sidx:"VID",
		 		showOn:true,
		 		colModel : [ {
		 			'columnName' : 'VID',
		 			'width' : '10',
		 			'label' : 'VID',
		 			hidden : true,
		 		}, {
		 			'columnName' : 'VCODE',
		 			'width' : '30',
		 			'align' : 'left',
		 			'label' : '供应商编码'
		 		},{
		 			'columnName' : 'VNAME',
		 			'width' : '60',
		 			'align' : 'left',
		 			'label' : '供应商名称'
		 		}
		 		],
		 		select : function(event, ui) {
		 			 $(event.target).val(ui.item.VNAME);
		 			$("#inventoryDict_vendor_id").val(ui.item.VID);
		 			return false; 
		 		}
		 	});						 	
						/* jQuery("#inventoryDict_vendor")
								.treeselect(
										{
											dataType : "sql",
											optType : "single",
											sql : "SELECT ltrim(vendorId) id,vendorName name from sy_vendor where orgCode='"+jQuery("#orgCode").html().trim()+"'",
											exceptnullparent : false,
											lazy : false
										}); */
										
			jQuery("#inventoryDict_secondUnit").change(function(){
				if(jQuery("#inventoryDict_secondUnit").val()){
					
					$("#inventoryDict_unitRate").addClass("required");
				}else
				
				$("#inventoryDict_unitRate").removeClass("required");
				
			});
						
			$('#testFileInput').uploadify({
				debug:true,
				swf:'${ctx}/dwz/uploadify/scripts/uploadify.swf',
				uploader:'${ctx}/uploadInvImageFile',
				formData:{PHPSESSID:'xxx', ajax:1},
				buttonText:'请选择文件',
				fileObjName: 'imageFile',  
				fileSizeLimit:'200KB',
				fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;',
				fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;',
				queueSizeLimit : 1,
				auto:true,
				multi:false,
				wmode: 'transparent' ,  
				rollover: false,
				queueID:'fileQueue',
			      'onInit': function () {                        //载入时触发，将flash设置到最小
               $('#fileQueue').hide();
              // $('#testFileInput').hide();
                      },
				onUploadSuccess: function(file, data, response) {
				//console.log(data);
	                                var retdata = eval('(' + data + ')');
	                                $('#inventoryDict_fileName').attr('value',retdata.message);
	                                $('#imageShow').attr('src','${ctx}/home/inventory/'+retdata.message); 
									$('#imageShow').show();
	                            }
				
			}); 
			jQuery("#inventoryDictForm").find("input").keydown(function(e){
				if(e.keyCode==13){
					e.stopPropagation();
					return false;
				}
			});	
	});
	function selectInvImg(){
		//alert("here");
		//console.log($('object#SWFUpload_0.swfupload'));
		$('#testFileInput-button').click();
		//$('#testFileInput').trigger("fileDialogStart");
		//$('div#testFileInput-button.uploadify-button').queueEvent("file_dialog_start_handler");
		//$('object#SWFUpload_0.swfupload').trigger("fileDialogStart");
		// $('#testFileInput').triggerHandler('fileDialogStart');
		//$('#testFileInput').fadeOut(300).delay(100).fadeIn(300, function(){$(this).focus().trigger("click")});
		//var ie=navigator.appName=="Microsoft Internet Explorer" ? true : false; 
		//if(ie){ 
		//document.getElementById("testFileInput").click(); 
		//document.getElementById("filename").value=document.getElementById("file").value;
		//}else{
		//var a=document.createEvent("MouseEvents");//FF的处理 
		//a.initEvent("click", true, true);  
		//document.getElementById("testFileInput").dispatchEvent(a);
		//}
	}
	function deleteInvImg(){
		  $('#imageShow').attr('src','${ctx}/home/inventory/default.jpg'); 
			$('#imageShow').show();
	url="${ctx}/deleteInvImageFile?Filename="+$('#inventoryDict_fileName').attr('value');
	url = encodeURI(url);
	jQuery.post(url, {}, function(json) {
		$('#inventoryDict_fileName').attr('value','');
			DWZ.ajaxDone(json);
			console.log(json);
			//DWZ.ajaxDone(json.ajaxReturn);
		});
	
}
	function checkInvDictCode(obj){
		var invDictIdTemp = "${fns:userContextParam('orgCode')}_${fns:userContextParam('copyCode')}_"+obj.value;
   		var url = 'checkId';
   		url = encodeURI(url);
   		$.ajax({
   		    url: url,
   		    type: 'post',
   		    data:{entityName:'InventoryDict',searchItem:'invId',searchValue:invDictIdTemp,returnMessage:'材料编码已存在，请重新输入'},
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
</script>
<style type="text/css" media="screen">
.my-uploadify-button {
	background:none;
	border: none;
	text-shadow: none;
	border-radius:0;
}

.uploadify:hover .my-uploadify-button {
	background:none;
	border: none;
}

.fileQueue {
	width: 400px;
	height: 150px;
	overflow: auto;
	border: 1px solid #E5E5E5;
	margin-bottom: 10px;
}
</style>

<div class="page">
	<div class="pageContent">
		<form id="inventoryDictForm" method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56" style="padding: 1px;">
				<div class="tabs" currentIndex="0" eventType="click" style="overflow:hidden">
					<div class="tabsHeader">
						<div class="tabsHeaderContent">
							<ul>
								<li><a href="javascript:;"><span>主信息</span></a></li>
								<li><a href="javascript:;"><span>辅助信息</span></a></li>
<!-- 								<li><a href="javascript:;"><span>经济信息</span></a></li> -->
								<li><a href="javascript:;"><span>预警信息</span></a></li>
								<li><a href="javascript:;"><span>维护记录</span></a></li>
							</ul>
						</div>
					</div>
					<div class="tabsContent pageContent" layoutH="95">
						<div>
							<div class="unit">
							<s:hidden name="entityIsNew"></s:hidden>
								<s:hidden key="inventoryDict.invId" />
								<s:hidden id="inventoryDict_copyCode" key="inventoryDict.copyCode" name="inventoryDict.copyCode" />
								<s:hidden id="inventoryDict_orgCode" key="inventoryDict.orgCode" name="inventoryDict.orgCode" />
							</div>
							<div class="unit">
								<s:if test="%{entityIsNew}">
									<s:textfield id="inventoryDict_invCode" key="inventoryDict.invCode" name="inventoryDict.invCode" cssClass="required" onblur="checkInvDictCode(this)"/>
								</s:if>
								<s:else>
									<s:textfield id="inventoryDict_invCode" key="inventoryDict.invCode" name="inventoryDict.invCode" cssClass="required readonly" readonly="true"/>
								</s:else>
								<s:select id="inventoryDict_mLevel" key="inventoryDict.mLevel" name="inventoryDict.mLevel" cssStyle="float: left; width: 50px"  cssClass="required digits" list="{'0','1','2'}" />
							</div>
							<div class="unit">
								<s:textfield id="inventoryDict_invName" key="inventoryDict.invName" name="inventoryDict.invName" cssClass="required" />
								<s:textfield id="inventoryDict_cnCode" key="inventoryDict.cnCode" name="inventoryDict.cnCode" readonly="true" cssClass="textInput readonly"/>
							</div>
							<div class="unit">
								<s:textfield id="inventoryDict_ivAlias" key="inventoryDict.ivAlias" name="inventoryDict.ivAlias"/>
								<s:textfield id="inventoryDict_invModel" key="inventoryDict.invModel" name="inventoryDict.invModel" />
							</div>
							
							<div class="unit">
								<s:hidden id="inventoryDict_invType_id" key="inventoryDict.inventoryType" name="inventoryDict.inventoryType.id"  />
								<s:textfield id="inventoryDict_invType" key="inventoryDict.inventoryType" name="inventoryDict.inventoryType.invTypeName"  cssClass="required" readonly="true"/>
								<%-- <s:textfield id="inventoryDict_invType_combo"  cssClass="required digits" label="fdcfdc"/> --%>
								<s:select id="inventoryDict_invAttrCode" key="inventoryDict.invAttrCode" name="inventoryDict.invAttrCode"  cssStyle="float: left; width: 60px" cssClass="required digits" listKey="key" listValue="value"
									list="#{0:'常规',1:'植入',2:'接触'}" />
							</div>
							
							<div class="unit">
								<s:hidden id="inventoryDict_vendor_id" name="inventoryDict.vendor.vendorId" />
								<s:textfield id="inventoryDict_vendor" key="inventoryDict.vendor" name="inventoryDict.vendor.vendorName" />
								<s:textfield id="inventoryDict_factory" key="inventoryDict.factory" name="inventoryDict.factory" />
							</div>
							<div class="unit">
								<%-- <s:hidden id="inventoryDict_firstUnit_id" key="inventoryDict.firstUnit" name="inventoryDict.firstUnit"  />
								<s:textfield id="inventoryDict_firstUnit" key="inventoryDict.firstUnit" name="inventoryDict_firstUnit"  cssClass="required"/> --%>
								
								<label><s:text name="inventoryDict.firstUnit"></s:text>:</label><span style="float: left; width: 134px"><appfuse:sqlSelect
														htmlFieldName="inventoryDict.firstUnit"
														paraString="SELECT unitName id,unitName name   from mm_inv_unit "
														initValue="${inventoryDict.firstUnit}"
														required="true" 
														></appfuse:sqlSelect></span>
								<label><s:text name="inventoryDict.secondUnit"></s:text>:</label><span style="float: left; width: 134px"><appfuse:sqlSelect
														htmlFieldName="inventoryDict.secondUnit"
														paraString="SELECT unitName id,unitName name   from mm_inv_unit "
														initValue="${inventoryDict.secondUnit}"
														required="false"
														></appfuse:sqlSelect></span>
								
							<%-- 	<s:hidden id="inventoryDict_secondUnit_id" key="inventoryDict.secondUnit" name="inventoryDict.secondUnit"  />
								<s:textfield id="inventoryDict_secondUnit" key="inventoryDict.secondUnit" name="inventoryDict_secondUnit"  /> --%>
								<s:textfield id="inventoryDict_unitRate" key="inventoryDict.unitRate" name="inventoryDict.unitRate" cssClass="digits" />
							</div>

							<div class="unit">
										 <label><s:text name="inventoryDict.isBatch"></s:text>:</label> <span style="float: left; width: 134px"> <s:checkbox	id="inventoryDict_isBatch" key="inventoryDict.isBatch" name="inventoryDict.isBatch" theme="simple"   onclick="return false;"/></span> 
										 <label><s:text name="inventoryDict.isPublic"></s:text>:</label> <span style="float: left; width: 134px"> <s:checkbox	id="inventoryDict_isPublic" key="inventoryDict.isPublic" name="inventoryDict.isPublic" theme="simple" /></span> 
								<s:select id="inventoryDict_priceType" key="inventoryDict.priceType" name="inventoryDict.priceType" cssStyle="float: left; width: 134px"  cssClass="digits" headerKey="" headerValue="---" listKey="key" listValue="value"
									list="#{0:'先进先出'}" />
							</div>
							<div class="unit">
								<label><s:text name="inventoryDict.isCharge"></s:text>:</label> <span style="float: left; width: 134px"> <s:checkbox id="inventoryDict_isCharge" key="inventoryDict.isCharge"
										name="inventoryDict.isCharge" theme="simple" /></span>
								<label><s:text name="inventoryDict.isAddSale"></s:text>:</label> <span style="float: left; width: 134px"> <s:checkbox id="inventoryDict_isAddSale" key="inventoryDict.isAddSale"
										name="inventoryDict.isAddSale" theme="simple" /></span>
								<s:textfield id="inventoryDict_addRate" key="inventoryDict.addRate" name="inventoryDict.addRate" cssClass="number" />
							</div>
							<div class="unit">
								<s:textfield id="inventoryDict_planPrice" key="inventoryDict.planPrice" name="inventoryDict.planPrice" cssClass="number" />
								<s:textfield id="inventoryDict_refCost" key="inventoryDict.refCost" name="inventoryDict.refCost" cssClass="number" />
								<s:textfield id="inventoryDict_refPrice" key="inventoryDict.refPrice" name="inventoryDict.refPrice" cssClass="number" />
							</div>
							<div class="unit">
								<label><s:text name="inventoryDict.isSelfMake"></s:text>:</label> <span style="float: left; width: 134px">								 <s:checkbox id="inventoryDict_isSelfMake" key="inventoryDict.isSelfMake"										name="inventoryDict.isSelfMake" theme="simple" /></span>
								<label><s:text name="inventoryDict.isDurable"></s:text>:</label> <span style="float: left; width: 134px"> <s:checkbox										id="inventoryDict_isDurable" key="inventoryDict.isDurable" name="inventoryDict.isDurable" theme="simple" /></span>
										 <label><s:text name="inventoryDict.isHighValue"></s:text>:</label> <span									style="float: left; width: 134px"> <s:checkbox id="inventoryDict_isHighValue" key="inventoryDict.isHighValue" name="inventoryDict.isHighValue" theme="simple" /></span>
							</div>
							<div class="unit">
								<label><s:text name="inventoryDict.isApply"></s:text>:</label> <span style="float: left; width: 134px"> <s:checkbox id="inventoryDict_isApply" key="inventoryDict.isApply"
										name="inventoryDict.isApply" theme="simple" /></span>
										
								<s:select id="inventoryDict_deptWhType" key="inventoryDict.deptWhType" name="inventoryDict.deptWhType" cssClass="digits" headerKey="" headerValue="---" listKey="key" listValue="value"
									list="#{0:'常规',1:'盘点',2:'台账'}" cssStyle="float: left; width: 134px" />
										
										<label><s:text name="inventoryDict.isPrepare"></s:text>:</label> <span
									style="float: left; width: 134px"> <s:checkbox id="inventoryDict_isPrepare" key="inventoryDict.isPrepare" name="inventoryDict.isPrepare" theme="simple" /></span>
							</div>
							<div class="unit">
								<label><s:text name="inventoryDict.isBar"></s:text>:</label> <span style="float: left; width: 134px"> <s:checkbox id="inventoryDict_isBar" key="inventoryDict.isBar"
										name="inventoryDict.isBar" theme="simple" /></span>
								<s:textfield id="inventoryDict_barCode" key="inventoryDict.barCode" name="inventoryDict.barCode" />
								<s:textfield id="inventoryDict_barCode2" key="inventoryDict.barCode2" name="inventoryDict.barCode2"  />
							</div>
							<div class="unit">
								<s:textfield id="inventoryDict_startDate" key="inventoryDict.startDate" name="inventoryDict.startDate" style="height:15px" cssClass="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" />
								<s:textfield id="inventoryDict_endDate" key="inventoryDict.endDate" name="inventoryDict.endDate" style="height:15px" cssClass="Wdate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" />
								<label><s:text name="inventoryDict.disabled"></s:text>:</label> <span style="float: left; width: 134px"><s:checkbox id="inventoryDict_disabled" key="inventoryDict.disabled"
										name="inventoryDict.disabled" theme="simple" /></span>

							</div>
							<div>

								<s:hidden id="inventoryDict_fileName" key="inventoryDict.fileName" name="inventoryDict.fileName" />
								
								<div id="fileQueue" class="fileQueue"></div>
									
							</div>
							<div style="position:absolute;top:0;right:0;">
							<table>
							<tbody>
							<tr><td colspan="2">
								<s:if test="%{inventoryDict.fileName==''}">
									<input type="image" id="imageShow" src="${ctx}/home/inventory/default.jpg" height="100" width="200" />
								</s:if>
								<s:else>
									<input type="image" id="imageShow" src="${ctx}/home/inventory/${inventoryDict.fileName}" height="100" width="200" />
								</s:else>
							</td></tr>
							<tr><td><input id="testFileInput" type="file" /></td><td> <input type="button" onclick="javascript:deleteInvImg()" value="Del" /></td></tr>
							</tbody>
							
							</table>
							</div>
						</div>
						<div>
							<div class="unit">
								<s:textfield id="inventoryDict_origin" key="inventoryDict.origin" name="inventoryDict.origin" />
								<s:textfield id="inventoryDict_brandName" key="inventoryDict.brandName" name="inventoryDict.brandName" />
								<s:textfield id="inventoryDict_agent" key="inventoryDict.agent" name="inventoryDict.agent" />
							</div>
							<div class="unit">
								<s:textfield id="inventoryDict_serveOrg" key="inventoryDict.serveOrg" name="inventoryDict.serveOrg" />
								<s:textfield id="inventoryDict_perValume" key="inventoryDict.perValume" name="inventoryDict.perValume" cssClass="number" />
								<s:textfield id="inventoryDict_perWeight" key="inventoryDict.perWeight" name="inventoryDict.perWeight" cssClass="number" />
								
								
							</div>
							<div class="unit">
								<s:select list="invUsedList"  listValue="invUseName" listKey="invUseId"   id="inventoryDict_invUse" key="inventoryDict.invUse" name="inventoryDict.invUse.invUseId" headerKey="" headerValue="---" cssStyle="float: left; width: 134px" ></s:select>
								<label><s:text name="inventoryDict.isImport"></s:text>:</label> <span style="float: left; width: 134px"><s:checkbox id="inventoryDict_isImport" key="inventoryDict.isImport" name="inventoryDict.isImport" theme="simple" /></span>
								<s:textfield id="inventoryDict_refNO" key="inventoryDict.refNO" name="inventoryDict.refNO"  />
							</div>

							<div class="unit">
								<label><s:text name="inventoryDict.isTender"></s:text>:</label> <span style="float: left; width: 134px"><s:checkbox id="inventoryDict_isTender" key="inventoryDict.isTender"
										name="inventoryDict.isTender" theme="simple" /></span>
								<label><s:text name="inventoryDict.isCert"></s:text>:</label> <span style="float: left; width: 134px"><s:checkbox id="inventoryDict_isCert" key="inventoryDict.isCert"										name="inventoryDict.isCert" theme="simple" /></span>
										
								<s:textfield id="inventoryDict_certCode" key="inventoryDict.certCode" name="inventoryDict.certCode" />
						
							</div>
							<div class="unit">
								<s:textfield id="inventoryDict_ecoBat" key="inventoryDict.ecoBat" name="inventoryDict.ecoBat" cssClass="number" />
								<s:select id="inventoryDict_abc" key="inventoryDict.abc" name="inventoryDict.abc" cssClass=" " headerKey="" headerValue="---" listKey="key" listValue="value"
									list="#{'A':'特别重要的库存（A类）','B':'一般重要的库存（B类）','C':'不重要的库存（C类）'}" cssStyle="float: left; width: 134px" />
								<s:select id="inventoryDict_amortizeType" key="inventoryDict.amortizeType" name="inventoryDict.amortizeType" cssClass="digits" headerKey="" headerValue="---" listKey="key"
									listValue="value" list="#{0:'一次性 ',1:'五五摊销'}" cssStyle="float: left; width: 134px" />
							</div>
							<div class="unit">
								<s:textfield id="inventoryDict_chargeItemId" key="inventoryDict.chargeItemId" name="inventoryDict.chargeItemId" />
								<s:textfield id="inventoryDict_chargeType" key="inventoryDict.chargeType" name="inventoryDict.chargeType" />
								<s:textfield id="inventoryDict_ybCode" key="inventoryDict.ybCode" name="inventoryDict.ybCode" />
							</div>

							<div class="unit">
  								<label><s:text name="inventoryDict.isSterile"></s:text>:</label> <span	style="float: left; width: 134px"><s:checkbox id="inventoryDict_isSterile" key="inventoryDict.isSterile" name="inventoryDict.isSterile" theme="simple" /></span>
								<s:textfield id="inventoryDict_stockCondition" key="inventoryDict.stockCondition" name="inventoryDict.stockCondition" cssClass="" />
							</div>



							<div class="unit">
								<s:textfield id="inventoryDict_invStructure" key="inventoryDict.invStructure" name="inventoryDict.invStructure"  />
								<s:select id="inventoryDict_planAttr" key="inventoryDict.planAttr" name="inventoryDict.planAttr" cssClass="digits" headerKey="" headerValue="---" listKey="key" listValue="value"
									list="#{0:'库存基数',1:'科室需求'}" cssStyle="float: left; width: 134px" />
							</div>






							<div class="unit">
								<s:textarea id="inventoryDict_remark" key="inventoryDict.remark" name="inventoryDict.remark" cols="100" rows="5" />
							</div>

							<div class="unit">
								<s:hidden id="inventoryDict_currentPrice" key="inventoryDict.currentPrice" name="inventoryDict.currentPrice" cssClass="number" />
								<s:hidden id="inventoryDict_moveAveragePrice" key="inventoryDict.moveAveragePrice" name="inventoryDict.moveAveragePrice" cssClass="number" />
							</div>
							
						</div>
						<div>
							<div class="unit">
								<label><s:text name="inventoryDict.isQuality"></s:text>:</label> <span style="float: left; width: 134px"><s:checkbox id="inventoryDict_isQuality" key="inventoryDict.isQuality"
										name="inventoryDict.isQuality" theme="simple" /></span>
							</div>
							<div class="unit">
								<s:textfield id="inventoryDict_guarantee" key="inventoryDict.guarantee" name="inventoryDict.guarantee" cssClass="digits" />
							</div>
							<div class="unit">
								<s:textfield id="inventoryDict_safeStock" key="inventoryDict.safeStock" name="inventoryDict.safeStock" cssClass="digits" />
							</div>
							<div class="unit">
								<s:textfield id="inventoryDict_lowLimit" key="inventoryDict.lowLimit" name="inventoryDict.lowLimit" cssClass="digits" />
							</div>
							<div class="unit">
								<s:textfield id="inventoryDict_highLimit" key="inventoryDict.highLimit" name="inventoryDict.highLimit" cssClass="digits" />
							</div>
							<div class="unit">
								<s:hidden id="inventoryDict_currentStock" key="inventoryDict.currentStock" name="inventoryDict.currentStock" cssClass="digits" />
							</div>
							<div class="unit">
								<s:textfield id="inventoryDict_buyAhead" key="inventoryDict.buyAhead" name="inventoryDict.buyAhead" cssClass="digits" />
							</div>
							<div class="unit">
								<label><s:text name="inventoryDict.isOverStock"></s:text>:</label> <span style="float: left; width: 134px"><s:checkbox id="inventoryDict_isOverStock" key="inventoryDict.isOverStock"
										name="inventoryDict.isOverStock" theme="simple" /></span>
							</div>

							<div class="unit">
								<s:textfield id="inventoryDict_stayTime" key="inventoryDict.stayTime" name="inventoryDict.stayTime" cssClass="digits" />
							</div>
						</div>
					</div>
					<div class="tabsFooter">
						<div class="tabsFooterContent"></div>
					</div>
				</div>



			</div>
			
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="inventoryDictSaveLink">

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





