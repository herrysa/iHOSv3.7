<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"%>

<script type="text/javascript">
	/*var layoutSettings_dictionary = {
		applyDefaultStyles : true // basic styling for testing & demo purposes
		//,	east__size:				600
		//,	east__minWidth:			600
		//,	center__minWidth:   	400
		//,   center__size	400
		,
		east__closable : false

	};*/
	jQuery(document).ready(function() {
		jQuery("#formulaField_gridtable").jqGrid({
			url : "formulaFieldGridList",
			editurl : "formulaFieldGridEdit",
			datatype : "json",
			mtype : "GET",
			height : "100%",
			autowidth : true,
			shrinkToFit :false,
			colModel : [ {
				name : "formulaEntity.formulaEntityId",
				index : "formulaEntity.formulaEntityId",
				label : "<fmt:message key='formulaEntity.formulaEntityId'/>",
				align : 'center',
				width : '50',
				hidden :true
			}, {
				name : "formulaFieldId",
				index : "formulaFieldId",
				label : "<fmt:message key='formulaField.formulaFieldId'/>",
				key : true,
				align : 'left',
				width : '50'
			}, {
				name : "formulaEntity.tableName",
				index : "formulaEntity.tableName",
				label : "<fmt:message key='formulaEntity.tableName'/>",
				align : 'left',
				width : '80'
			}, {
				name : "fieldName",
				index : "fieldName",
				label : "<fmt:message key='formulaField.fieldName'/>",
				align : 'left',
				width : '80'
			}, {
				name : "fieldTitle",
				index : "fieldTitle",
				label : "<fmt:message key='formulaField.fieldTitle'/>",
				align : 'left',
				width : '150'
			}, {
				name : "keyClass",
				index : "keyClass",
				label : "<fmt:message key='formulaField.keyClass'/>",
				align : 'center',
				width : '80'
			}, {
				name : "calcType",
				index : "calcType",
				label : "<fmt:message key='formulaField.calcType'/>",
				align : 'center',
				width : '80'
			}, {
				name : "execOrder",
				index : "execOrder",
				label : "<fmt:message key='formulaField.execOrder'/>",
				width : "80",
				align : 'center'
			}, {
				name : "inherited",
				index : "inherited",
				label : "<fmt:message key='formulaField.inherited'/>",
				width : "60",
				formatter : "checkbox",
				align : 'center'
			}, {
				name : "disabled",
				index : "disabled",
				label : "<fmt:message key='formulaField.disabled'/>",
				width : "30",
				formatter : "checkbox",
				align : 'center'
			}, {
				name : "description",
				index : "description",
				label : "<fmt:message key='formulaField.description'/>",
				width : "130",
				align : 'center'
			}, {
				name : "direction",
				index : "direction",
				label : "<fmt:message key='formulaField.direction'/>",
				width : "55",
				align : 'center'
			}

			/* 				,{name : "helperClass",index : "helperClass",label : "<fmt:message key='formulaField.helperClass'/>",	width : "100",align:'center'}
			,{name : "param1",index : "param1",label : "<fmt:message key='formulaField.param1'/>",	width : "100",align:'center'}
			,{name : "param2",index : "param2",label : "<fmt:message key='formulaField.param2'/>",	width : "100",align:'center'} */

			],
			viewrecords : true,
			rowNum : 20,
			rowList : [ 10, 20, 40 ],
			prmNames : {
				search : "search"
			}, //(1)
			jsonReader : {
				root : "formulaFields", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},

			//pager : "#gridPager",
			//caption : "<fmt:message key='formulaFieldList.title'/>",
			hidegrid : false,

			multiselect : true,
			multiboxonly : true,
			/* 		 afterInsertRow: function(rowid, aData){
					    	switch (aData.fieldName) {
					    		case 'field10':
					    			jQuery("#gridTable").jqGrid('setCell',rowid,'fieldName','',{color:'red'});
					    		break;
					    		
					    		
					    	}
					    }, */
			altRows : false,
			rownumbers : true,
			shrinkToFit:true,
			autowidth : true,
			loadui: "disable",
			gridComplete : function(){
				gridContainerResize("formulaField","div");
			     var dataTest = {"id":"formulaField_gridtable"};
				 jQuery.publish("onLoadSelect",dataTest,null);
				 makepager("formulaField_gridtable");
				/*var height = jQuery("#formulaField_gridtable_div").height();
				var width = jQuery("#formulaField_gridtable_div").width();
			     jQuery("#formulaField_gridtable").setGridHeight(height-30); 
			     jQuery("#formulaField_gridtable").setGridHeight(height-30); 
			     makepager('formulaField_gridtable');
			     var header = jQuery("#formulaField_pageHeader").outerHeight(true);
					if(header&&header!=0){
						var valve = 63;
						if(header>valve){
							jQuery("#formulaField_pageHeader").find(".subBar").hide();
						}else{
							jQuery("#formulaField_pageHeader").find(".searchContent").find(".buttonActive").hide();
						}
					}
			     jQuery("#gview_formulaField_gridtable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
						jQuery(this).find("th").each(function(){
							//jQuery(this).css("vertical-align","middle");
							//jQuery(this).find("div").eq(0).css("margin-top","5px");
							jQuery(this).find("div").eq(0).css("line-height","18px");
						});
					});*/
			}
		});
		/* jQuery("#formulaField_gridtable").jqGrid('navGrid', '#gridPager', {
			edit : false,
			add : false,
			del : false,
			search : false
		}, {}, {}, {}, {
			multipleSearch : false,
			multipleGroup : false,
			showQuery : true
		}); */
		/* jQuery('#formulaFied_container').layout(layoutSettings_dictionary); */
		/*var formulaFieldLayout;
		var formulaFieldFullSize = jQuery("#container").innerHeight()+7;
		var formulaFieldLayoutSettings = {
				applyDefaultStyles : true // basic styling for testing & demo purposes
				,
				onresize_end : function(paneName,element,state,options,layoutName){
					var w = state.innerWidth;
					var h = state.innerHeight;
					jQuery("#formulaField_gridtable").setGridWidth(w-10); 
					//jQuery("#formulaField_gridtable").setGridHeight(height); 
				}	
			};
		jQuery("#formulaField_container").css("height",formulaFieldFullSize);
		formulaFieldLayout = jQuery("#formulaField_container").layout(formulaFieldLayoutSettings);
		jQuery("#formulaField_layout-center").css("padding",0);
		formulaFieldLayout.resizeAll();*/
	});

	function formulaFieldgridReload() {
		var urlString = "formulaFieldGridList";

		var tableName = jQuery('input[name="searchFormulaEntity"]').filter(
				':checked').val();
		var fieldName = jQuery("#searchFieldName").val();
		var fieldTitle = jQuery("#searchFieldTitle").val();
		var disabled = jQuery("#searchDisabled").val();
		var keyClassTxt = jQuery("#keyClassTxt").val();
		//alert(tableName);

		urlString = urlString + "?filter_EQS_formulaEntity.tableName="
				+ tableName + "&filter_LIKES_fieldName=" + fieldName
				+ "&filter_LIKES_fieldTitle=" + fieldTitle
				+ "&filter_EQB_disabled=" + disabled + "&filter_EQS_keyClass="
				+ keyClassTxt;
		urlString = encodeURI(urlString);
		jQuery("#formulaField_gridtable").jqGrid('setGridParam', {
			url : urlString,
			page : 1
		}).trigger("reloadGrid");
	}

	function initFormulaField() {
		var url = "initFormulaField";
		var jqxhr = jQuery.post(url, {
			dataType : "json"
		}).success(
				function(data) {
					var status = data['jsonStatus'];
					if (status == "error") {
						jQuery('div.#msgDialog').html(
								"<p class='ui-state-error'>"
										+ data['jsonMessages'] + "</p>");
					} else {
						jQuery('div.#msgDialog').html(
								"<p class='ui-state-hover'>"
										+ data['jsonMessages'] + "</p>");
						refreshGridCurrentPage();
					}
					jQuery('#msgDialog').dialog('open');
				}).error(function() {
			alert(data['jsonMessages']);
		});
	}
	function refreshGridCurrentPage() {
		var jq = jQuery('#formulaField_gridtable');
		var currentPage = jq.jqGrid('getGridParam', 'page');
		jQuery('#formulaField_gridtable').trigger('reloadGrid', [ {
			page : currentPage
		} ]);
	}
	function editFormulaFieldRecord() {
		var sid = jQuery("#formulaField_gridtable").jqGrid('getGridParam', 'selarrrow');
		if (sid == null || sid.length == 0) {
			//alert("<fmt:message key='list.selectNone'/>");
			/* jQuery('div.#msgDialog').html(
					"<fmt:message key='list.selectNone'/>");
			jQuery('#msgDialog').dialog('open'); */
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
		}
		if (sid.length > 1) {
			//alert("<fmt:message key='list.selectMore'/>");
			/* jQuery('div.#msgDialog').html(
					"<fmt:message key='list.selectMore'/>");
			jQuery('#msgDialog').dialog('open'); */
			alertMsg.error("<fmt:message key='list.selectMore'/>");
			return;
		} else {
			jQuery("#gridinfo").html('<p>Loading..... ID : ' + sid + '</p>');
			var url = "editFormulaField?popup=true&formulaFieldId=" + sid+"&navTabId=formulaField_gridtable";
			var winTitle = '<fmt:message key="formulaFieldEdit.title"/>';
			$.pdialog.open(url, 'editFormulaField', winTitle, {mask:false,width:670,height:650});
			//popUpWindow(url, winTitle, "width=600");
			//openWindow(url, winTitle, " width=1200");
		}
	}
	function addFormulaFieldRecord() {
		var url = "editFormulaField?popup=true&navTabId=formulaField_gridtable";
		var winTitle = '<fmt:message key="formulaFieldNew.title"/>';
		$.pdialog.open(url, 'editFormulaField', winTitle, {mask:false,width:670,height:650});
	}
	/*  function updateDisableFormulaField(disabled){

	     var sid = jQuery("#gridTable").jqGrid('getGridParam','selarrrow');
	     var msgDialog = jQuery('#msgDialog');
	     var confirmDialog = jQuery("#confirmDialog");
	     if(sid==null || sid.length ==0){
		
				msgDialog.dialog('option', 'buttons', {
					"<fmt:message key='dialog.ok'/>": function() {
						jQuery( this ).dialog( "close" );
					}
		    	});
			jQuery('div.#msgDialog').html("<fmt:message key='list.selectNone'/>");
			msgDialog.dialog('open');
			return;
			}else{
				confirmDialog.dialog('option', 'buttons', {
					"<fmt:message key='dialog.confirm'/>": function() {
						jQuery( this ).dialog( "close" );
						
						var url = "pub?searchName=${searchName}&id="+sid+"&actionName=delete";
						if(disabled=='disable')
							url = "disabledFormulaField";
						if(disabled=='enable')
							url = "enabledFormulaField";
						var jqxhr=jQuery.post( url, {dataType : "json"})
					    .success(function(data)
					    		{
					      	var status = data['jsonStatus'];
					    	if(status=="error"){
						jQuery('div.#msgDialog').html("<p class='ui-state-error'>"+data['jsonMessages']+"</p>");
					    	}else{
					    		jQuery('div.#msgDialog').html("<p class='ui-state-hover'>"+data['jsonMessages']+"</p>");
					    	}
						msgDialog.dialog('open');
		 				})
					    .error(function() {  alert(data['jsonMessages']); });
					   
					    jqxhr.complete(function(){
							jQuery("#gridTable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
							});
					},
					"<fmt:message key='dialog.cancel'/>": function() {
						jQuery( this ).dialog( "close" );
					}
				}
				
			
				);
				jQuery('div.#confirmDialog').html("<fmt:message key='dialog.confirmDelete'/>");
				confirmDialog.dialog('open');
		}
	} */

	function updateDisableFormulaField(disabled) {

		var sid = jQuery("#formulaField_gridtable").jqGrid('getGridParam', 'selarrrow');
		var msgDialog = jQuery('#msgDialog');
		var confirmDialog = jQuery("#confirmDialog");
		if (sid == null || sid.length == 0) {

			/* msgDialog.dialog('option', 'buttons', {
				"<fmt:message key='dialog.ok'/>" : function() {
					jQuery(this).dialog("close");
				}
			});
			jQuery('div.#msgDialog').html(
					"<fmt:message key='list.selectNone'/>");
			msgDialog.dialog('open'); */
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
		} else {
			var msgStr = "";
			if (disabled == 'disable')
				msgStr = "是否将所选行改为<font color='red'>停用</font>状态";
			if (disabled == 'enable')
				msgStr = "是否将所选行改为<font color='green'>启用</font>状态";
			alertMsg.confirm(msgStr, {
				okCall: function(){
					var url = "";
					if (disabled == 'disable')
						url = "disabledFormulaField?id=" + sid;
					if (disabled == 'enable')
						url = "enabledFormulaField?id=" + sid;
					var jqxhr = jQuery.post(url, {
						dataType : "json"
					}).success(
							function(data) {
								var status = data['jsonStatus'];
								if (status == "error") {
									/* jQuery('div.#msgDialog').html(
											"<p class='ui-state-error'>"
													+ data['jsonMessages']
													+ "</p>"); */
									alertMsg.error(data['jsonMessages']);
								} else {
									/* jQuery('div.#msgDialog').html(
											"<p class='ui-state-hover'>"
													+ data['jsonMessages']
													+ "</p>"); */
									alertMsg.correct(data['jsonMessages']);
								}
								//msgDialog.dialog('open');
							}).error(function() {
								alertMsg.error(data['jsonMessages']);
					});

					jqxhr.complete(function() {
						jQuery("#formulaField_gridtable").jqGrid('setGridParam', {
							page : 1
						}).trigger("reloadGrid");
					});
					
				}
			});
			
			
			/* confirmDialog.dialog('option', 'buttons', {
				"<fmt:message key='dialog.confirm'/>" : function() {
					jQuery(this).dialog("close");
					var url = "";
					if (disabled == 'disable')
						url = "disabledFormulaField?id=" + sid;
					if (disabled == 'enable')
						url = "enabledFormulaField?id=" + sid;
					var jqxhr = jQuery.post(url, {
						dataType : "json"
					}).success(
							function(data) {
								var status = data['jsonStatus'];
								if (status == "error") {
									jQuery('div.#msgDialog').html(
											"<p class='ui-state-error'>"
													+ data['jsonMessages']
													+ "</p>");
								} else {
									jQuery('div.#msgDialog').html(
											"<p class='ui-state-hover'>"
													+ data['jsonMessages']
													+ "</p>");
								}
								msgDialog.dialog('open');
							}).error(function() {
						alert(data['jsonMessages']);
					});

					jqxhr.complete(function() {
						jQuery("#formulaField_gridtable").jqGrid('setGridParam', {
							page : 1
						}).trigger("reloadGrid");
					});
				},
				"<fmt:message key='dialog.cancel'/>" : function() {
					jQuery(this).dialog("close");
				}
			}

			);
			jQuery('div.#confirmDialog').html("确认更新所选记录的disabled状态的？");
			confirmDialog.dialog('open'); */
		}
	}
</script>

<div class="page" id="formulaField_page">
	<div class="pageContent">
	<div id="formulaField_container">
			<div id="formulaField_layout-center"
				class="pane ui-layout-center">
		<div id="formulaField_pageHeader" class="pageHeader">
			<sj:dialog id="msgDialog"
				buttons="{'%{getText('dialog.ok')}':function() {jQuery( this ).dialog( 'close' ); }}"
				autoOpen="false" modal="true"
				title="%{getText('messageDialog.title')}" />
			<sj:dialog id="confirmDialog"
				buttons="{'%{getText('dialog.confirm')}':function() {jQuery( this ).dialog( 'close' ); },'%{getText('dialog.cancel')}':function() {jQuery( this ).dialog( 'close' ); }}"
				autoOpen="false" modal="true"
				title="%{getText('messageDialog.title')}" />


			<%-- 		<div class="row-fluid">
			<s:label><fmt:message key='formulaEntity.tableName'/></s:label>
			<input type="radio" name="searchFormulaEntity"
				id="searchFormulaEntity" value="all"  checked="checked"/>all
			<c:forEach items="${formulaEntities}" var="op">
				<input type="radio" name="searchFormulaEntity"
					id="searchFormulaEntity" value="${op.tableName}" />${op.tableName}
			</c:forEach>
			<s:label><fmt:message key='formulaField.fieldName'/></s:label>
			<input type="text" name="searchFieldName" id="searchFieldName">
			<s:label><fmt:message key='formulaField.fieldTitle'/></s:label>
			<input type="text" name="searchFieldTitle" id="searchFieldTitle">
			<s:label required="true"><fmt:message key='formulaField.disabled'/></s:label>
			<appfuse:singleSelect htmlFieldName="searchDisabled" paraDisString="使用中;停用中" paraValueString="false;true"></appfuse:singleSelect>
			<button onclick="gridReload()" id="submitButton" style="margin-left: 30px;"><fmt:message key='button.search'/></button>
		</div> --%>
			<%-- 		<div class="container-fluid">
			<div class="row-fluid">
				<div class="span12 well  form-horizontal">
					<div class="span6  row-fluid">
						<div class="span2 ">
							<s:label cssClass="right">
								<fmt:message key='formulaEntity.tableName' />
							</s:label>
							<input type="radio" name="searchFormulaEntity"
								id="searchFormulaEntity" value="all" checked="checked" />全部
							<c:forEach items="${formulaEntities}" var="op">
								<input type="radio" name="searchFormulaEntity"
									id="searchFormulaEntity" value="${op.tableName}" />${op.entityName}
							</c:forEach>
						</div>
						<div class="span2"><s:label><fmt:message key='formulaField.fieldName'/></s:label><input type="text" name="searchFieldName" id="searchFieldName">
						<s:textfield key="formulaField.fieldName" name="searchFieldName" id="searchFieldName" cssClass="input-small"></s:textfield>
						</div>
						<div class="span3"><s:textfield key="formulaField.fieldTitle" name="searchFieldTitle" id="searchFieldTitle" cssClass="input-small"/></div>
					
						<div class="span2"><appfuse:singleSelect htmlFieldName="searchDisabled" paraDisString="使用中;停用中" paraValueString="false;true"></appfuse:singleSelect></div>
						<div class="span1"><button onclick="gridReload()" id="submitButton" style="margin-left: 30px;"><fmt:message key='button.search'/></button></div>
					</div>



				</div>
			</div>
		</div> --%>
			<form onsubmit="return navTabSearch(this);" action="" method="post" class="queryarea-form">
				<div class="searchBar">
					<div class="searchContent">

						<label class="queryarea-label"><fmt:message
								key='formulaEntity.tableName' />： <input type="radio"
							name="searchFormulaEntity" id="searchFormulaEntity" value=""
							checked="checked" />全部 <c:forEach
								items="${formulaEntities}" var="op">
								<input type="radio" name="searchFormulaEntity"
									id="searchFormulaEntity" value="${op.tableName}" />${op.entityName}
							</c:forEach> </label>
						<label class="queryarea-label"><fmt:message
								key='formulaField.fieldName' />：<input type="text"
							name="searchFieldName" id="searchFieldName" class="input-small" />
						</label>
						<label class="queryarea-label"><fmt:message
								key="formulaField.fieldTitle" />：<input type="text"
							name="searchFieldTitle" id="searchFieldTitle" class="input-small" />
						</label> 
						<label class="queryarea-label"><fmt:message
								key="formulaField.keyClass" />：<s:select theme="simple"
								list="distinctFiledList" cssClass="input-small" id="keyClassTxt"
								value="keyClass" emptyOption="true"></s:select>
						</label> 
						<%--  <s:select  id="departmentIdC" lable=""  maxlength="20" list="deptList"   listKey="departmentId" listValue="name" emptyOption="true" cssClass="form-horizontal"></s:select> --%>

						<label class="queryarea-label"><fmt:message
								key="formulaField.disabled" />：<appfuse:singleSelect
								htmlFieldName="searchDisabled" paraDisString="使用中;停用中"
								paraValueString="false;true" cssClass="input-small"></appfuse:singleSelect>
						</label>
						
						<div class="buttonActive" style="float:right">
									<div class="buttonContent">
										<button type="button" onclick="formulaFieldgridReload()">查询</button>
									</div>
						</div>
					</div>
					<!-- <div class="subBar">
						<ul>
							<li><div class="buttonActive">
									<div class="buttonContent">
										<button type="button" onclick="formulaFieldgridReload()">查询</button>
									</div>
								</div></li>
							<li><a class="button" href="demo_page6.html" target="dialog"
							rel="dlg_page1" title="查询框"><span>高级检索</span>
						</a>
						</li>
						</ul>
					</div> -->
				</div>
			</form>
		</div>
		<div class="panelBar" id="formulaField_buttonBar">
			<ul class="toolBar">
			<%-- <li>
			<a class="addbutton" href="javaScript:addFormulaFieldRecord();"><span><fmt:message
						key="button.add" /> </span> </a>
			</li> --%>
			<li>
			<a class="changebutton" href="javaScript:editFormulaFieldRecord();"><span><fmt:message
						key="button.edit" /> </span> </a>
			</li>
			<li> <a class="disablebutton"
				href="javaScript:updateDisableFormulaField('disable');"><span><fmt:message
						key="button.disable" /> </span> </a>
			</li>
			<li> <a class="enablebutton"
				href="javaScript:updateDisableFormulaField('enable');"><span><fmt:message
						key="button.enable" /> </span> </a>
			</li>
			</ul>
		</div>
		<div id="formulaField_gridtable_div" class="grid-wrapdiv">
			<table id="formulaField_gridtable"></table>
			<!-- <div id="gridPager"></div> -->
		</div>

		<div id="formulaField_pageBar" class="panelBar">
				<div class="pages">
					<span>显示</span> <select id="formulaField_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span>条，共<label
						id="formulaField_gridtable_totals"></label>条</span>
				</div>

				<div id="formulaField_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

		</div>
		<!-- 	<DIV class="pane ui-layout-east">east</DIV>
	<DIV class="pane ui-layout-west">west</DIV>
	<DIV class="pane ui-layout-south">South</DIV> -->
	</div>
	</div>
	</div>
</div>







