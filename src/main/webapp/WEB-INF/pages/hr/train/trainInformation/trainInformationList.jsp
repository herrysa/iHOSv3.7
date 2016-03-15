
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var width = 960, height = 638;
	function trainInformationGridReload() {
		var urlString = "trainInformationGridList";
		var code = jQuery("#search_trainInformation_code").val();
		var name = jQuery("#search_trainInformation_name").val();
		var author = jQuery("#search_trainInformation_author").val();
		var informationClass = jQuery("#search_trainInformation_informationClass").val();
		var type = jQuery("#search_trainInformation_type").val();
		var remark=jQuery("#search_trainInformation_remark").val();
		var disabled=jQuery("#search_trainInformation_disabled").val();
		  
		  
		urlString = urlString + "?filter_LIKES_code=" + code
				+ "&filter_LIKES_name=" + name;
		urlString = urlString + "&filter_LIKES_author=" + author
				+ "&filter_EQS_informationClass=" + informationClass;
		urlString = urlString + "&filter_EQS_type=" + type;
		urlString=urlString+"&filter_LIKES_remark="+remark+"&filter_EQB_disabled="+disabled;

		urlString = encodeURI(urlString);
		jQuery("#trainInformation_gridtable").jqGrid('setGridParam', {
			url : urlString,
			page : 1
		}).trigger("reloadGrid");
	}
	var trainInformationGridIdString = "#trainInformation_gridtable";

	jQuery(document)
			.ready(
					function() {
						var trainInformationGrid = jQuery(trainInformationGridIdString);
						trainInformationGrid
								.jqGrid({
									url : "trainInformationGridList",
									editurl : "trainInformationGridEdit",
									datatype : "json",
									mtype : "GET",
									colModel : [
											{name : 'id',index : 'id',align : 'center',label : '<s:text name="trainInformation.id" />',hidden : true,key : true},
											{name : 'code',index : 'code',width : 100,align : 'left',label : '<s:text name="trainInformation.code" />',hidden : false,highsearch:true},
											{name : 'name',index : 'name',width : 150,align : 'left',label : '<s:text name="trainInformation.name" />',hidden : false,highsearch:true},
											{name : 'author',index : 'author',width : 80,align : 'left',label : '<s:text name="trainInformation.author" />',hidden : false,highsearch:true},
											{name : 'expense',index : 'expense',width : 60,align : 'right',label : '<s:text name="trainInformation.expense" />',hidden : false,formatter : 'number',highsearch:true},
											{name : 'informationClass',index : 'informationClass',width : 100,align : 'left',label : '<s:text name="trainInformation.informationClass" />',hidden : false,highsearch:true},
											{name : 'press',index : 'press',width : 100,align : 'left',label : '<s:text name="trainInformation.press" />',hidden : false,highsearch:true},
											{name : 'type',index : 'type',width : 100,align : 'left',label : '<s:text name="trainInformation.type" />',hidden : false,highsearch:true},
											{name : 'attachment',index : 'attachment',width : 50,align : 'center',label : '附件',hidden : false,highsearch:true},
											{name : 'disabled',index : 'disabled',align : 'center',width : 40,label : '<s:text name="trainInformation.disabled" />',hidden : false,sortable : true,formatter : 'checkbox',highsearch : true},
											{name : 'remark',index : 'remark',width : 250,align : 'left',label : '<s:text name="trainInformation.remark" />',hidden : false,formatter:stringFormatter} ],
									jsonReader : {
										root : "trainInformations", // (2)
										page : "page",
										total : "total",
										records : "records", // (3)
										repeatitems : false
									// (4)
									},
									sortname : 'code',
									viewrecords : true,
									sortorder : 'desc',
									//caption:'<s:text name="trainInformationList.title" />',
									height : 300,
									gridview : true,
									rownumbers : true,
									loadui : "disable",
									multiselect : true,
									multiboxonly : true,
									shrinkToFit : false,
									autowidth : false,
									onSelectRow : function(rowid) {

									},
									gridComplete : function() {
										 /*2015.08.27 form search change*/
										 gridContainerResize('trainInformation','div');
										var rowNum = jQuery(this).getDataIDs().length;
										if (rowNum > 0) {
											var rowIds = jQuery(this)
													.getDataIDs();
											var ret = jQuery(this).jqGrid(
													'getRowData');
											var id = '';
											for ( var i = 0; i < rowNum; i++) {
												id = rowIds[i];
												editUrl = "'${ctx}/showAttachment?type=trainInformation&foreignKey="+ ret[i]["id"] + "'";
												setCellText(this,id,'attachment','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="showAttachment('+ editUrl+ ');">附件</a>');
												setCellText(this,id,'code','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewTrainInformationRecord(\''+id+'\');">'+ret[i]["code"]+'</a>');
											}
										}else{
								        	var tw = jQuery(this).outerWidth();
								        	jQuery(this).parent().width(tw);
								        	jQuery(this).parent().height(1);
								        }

										var dataTest = {
											"id" : "trainInformation_gridtable"
										};
										jQuery.publish("onLoadSelect",
												dataTest, null);
									}

								});
						jQuery(trainInformationGrid).jqGrid('bindKeys');

						jQuery("#trainInformation_gridtable_add_custom")
								.unbind('click')
								.bind(
										"click",
										function() {
											var url = "editTrainInformation?popup=true&navTabId=trainInformation_gridtable";
											var winTitle = '<s:text name="trainInformationNew.title"/>';
											$.pdialog.open(url,
													'addTrainInformation',
													winTitle, {
														mask : true,
														width : 700,
														height : 300
													});
										});
						jQuery("#trainInformation_gridtable_edit_custom")
								.unbind('click')
								.bind(
										"click",
										function() {
											var sid = jQuery(
													"#trainInformation_gridtable")
													.jqGrid('getGridParam',
															'selarrrow');
											if (sid == null || sid.length != 1) {
												alertMsg.error("请选择一条记录。");
												return;
											}
											var winTitle = '<s:text name="trainInformationEdit.title"/>';
											var url = "editTrainInformation?popup=true&id="
													+ sid
													+ "&navTabId=trainInformation_gridtable";
											$.pdialog.open(url,
													'editTrainInformation',
													winTitle, {
														mask : true,
														width : 700,
														height : 300
													});
										});
						//trainInformationLayout.resizeAll();
						 jQuery("#trainInformation_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
								var url = "${ctx}/trainInformationGridEdit?oper=del";
								var sid = jQuery("#trainInformation_gridtable").jqGrid('getGridParam','selarrrow');
								if (sid == null || sid.length == 0) {
									alertMsg.error("请选择记录。");
									return;
								} else {
									jQuery.ajax({
							             url: 'attachmentGridList?filter_INS_foreignKey='+sid+'&filter_EQS_type=trainInformation',
							             data: {},
							             type: 'post',
							             dataType: 'json',
							             async:false,
							             error: function(data){
							             },
							             success: function(data){
							             	if(data.attachments&&data.attachments.length>0){
							             		alertMsg.error("包含附件的培训资料不能删除!");
												return;
							             	}
							             	url = url+"&id="+sid+"&navTabId=trainInformation_gridtable";
											alertMsg.confirm("确认删除？", {
												okCall : function() {
													$.post(url,function(data) {
														formCallBack(data);
													});
												}
											});
							             }
							         });
								}
							});
					});

	function showAttachment(url) {
		$.pdialog.open(url, 'showAttachment', '附件列表', {
			mask : true,
			width : 700,
			height : 500
		});
	}
	function viewTrainInformationRecord(id){
		var url = "editTrainInformation?oper=view&id="+id;
		$.pdialog.open(url,'viewTrainInformation','查看培训资料信息', {mask:true,width : 700,height : 350});
	}
</script>

<div class="page">
			<div class="pageHeader" id="trainInformation_pageHeader">
				<div class="searchBar">
					<div class="searchContent">
						<form id="trainCategory_search_form" style="white-space: break-all;word-wrap:break-word;">
							<label style="float: none; white-space: nowrap"> <s:text
									name='trainInformation.code' />: <input type="text"
								id="search_trainInformation_code" style="width:80px"/>
							</label> <label style="float: none; white-space: nowrap">
								<s:text name='trainInformation.name' />: <input type="text"
								id="search_trainInformation_name" style="width:80px"/>
							</label> <label style="float: none; white-space: nowrap">
								<s:text name='trainInformation.author' />: <input type="text"
								id="search_trainInformation_author" style="width:80px"/>
							</label> 
							<label style="float: none; white-space: nowrap">
								<s:text name='trainInformation.informationClass' />: <s:select
									key="trainInformation.informationClass" id="search_trainInformation_informationClass"
									style="font-size:9pt;" maxlength="50" list="informationClassList" headerKey="" headerValue="--"
									listKey="value" listValue="content" emptyOption="false"
									theme="simple"></s:select>
							</label>
							<label style="float: none; white-space: nowrap">
								<s:text name='trainInformation.type' />: <s:select
									key="trainInformation.type" id="search_trainInformation_type"
									style="font-size:9pt;" maxlength="50" list="informationTypeList" headerKey="" headerValue="--"
									listKey="value" listValue="content" emptyOption="false"
									theme="simple"></s:select>
							</label>
							<label style="float: none; white-space: nowrap">
								<s:text name='trainInformation.remark' />: <input type="text"
								id="search_trainInformation_remark" style="width:80px"/>
							</label>
							<label style="float:none;white-space:nowrap" >
						<s:text name='trainInformation.disabled'/>:
					 	<s:select id="search_trainInformation_disabled" headerKey="" headerValue="--" 
							list="#{'1':'是','0':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
						</label>
						
							<div class="buttonActive" style="float: right">
								<div class="buttonContent">
									<button type="button" onclick="trainInformationGridReload()">
										<s:text name='button.search' />
									</button>
								</div>
							</div>
						</form>
					</div>
<!-- 					<div class="subBar"> -->
<!-- 						<ul> -->
<!-- 							<li><div class="buttonActive"> -->
<!-- 									<div class="buttonContent"> -->
<!-- 										<button type="button" onclick="trainInformationGridReload()"> -->
<%-- 											<s:text name='button.search' /> --%>
<!-- 										</button> -->
<!-- 									</div> -->
<!-- 								</div></li> -->

<!-- 						</ul> -->
<!-- 					</div> -->
				</div>
			</div>
			<div class="pageContent">





				<div class="panelBar" id="trainInformation_buttonBar">
					<ul class="toolBar">
						<li><a id="trainInformation_gridtable_add_custom"
							class="addbutton" href="javaScript:"><span><fmt:message
										key="button.add" /> </span> </a></li>
						<li><a id="trainInformation_gridtable_del_custom" class="delbutton"
							href="javaScript:"><span><s:text name="button.delete" /></span>
						</a></li>
						<li><a id="trainInformation_gridtable_edit_custom"
							class="changebutton" href="javaScript:"><span><s:text
										name="button.edit" /> </span> </a></li>
					<%-- <li>
     					<a class="settlebutton"  href="javaScript:setColShow('trainInformation_gridtable','com.huge.ihos.hr.trainInformation.model.TrainInformation')"><span><s:text name="button.setColShow" /></span></a>
   					</li> --%>
					</ul>
				</div>
				<div id="trainInformation_gridtable_div" 
					style="margin-left: 2px; margin-top: 2px; overflow: hidden" class="grid-wrapdiv" class="unitBox" 
					buttonBar="optId:id;width:500;height:300">
					<input type="hidden" id="trainInformation_gridtable_navTabId"
						value="${sessionScope.navTabId}"> <label
						style="display: none" id="trainInformation_gridtable_addTile">
						<s:text name="trainInformationNew.title" />
					</label> <label style="display: none"
						id="trainInformation_gridtable_editTile"> <s:text
							name="trainInformationEdit.title" />
					</label> <label style="display: none"
						id="trainInformation_gridtable_selectNone"> <s:text
							name='list.selectNone' />
					</label> <label style="display: none"
						id="trainInformation_gridtable_selectMore"> <s:text
							name='list.selectMore' />
					</label>
					<div id="load_trainInformation_gridtable"
						class='loading ui-state-default ui-state-active'
						style="display: none"></div>
					<table id="trainInformation_gridtable"></table>
				</div>
			</div>
			<div class="panelBar" id="trainInformation_pageBar">
				<div class="pages">
					<span><s:text name="pager.perPage" /></span> <select
						id="trainInformation_gridtable_numPerPage">
						<option value="20">20</option>
						<option value="50">50</option>
						<option value="100">100</option>
						<option value="200">200</option>
					</select> <span><s:text name="pager.items" />. <s:text
							name="pager.total" /><label
						id="trainInformation_gridtable_totals"></label>
					<s:text name="pager.items" /></span>
				</div>

				<div id="trainInformation_gridtable_pagination" class="pagination"
					targetType="navTab" totalCount="200" numPerPage="20"
					pageNumShown="10" currentPage="1"></div>

			</div>
</div>