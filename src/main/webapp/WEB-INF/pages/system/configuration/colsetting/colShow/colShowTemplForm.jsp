<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		var supcan = "${oper}";
		var navTabId = "${navTabId}";
		jQuery("#colShowTemlSelect").dblclick(function(){
			var templName = jQuery(this).val();
			jQuery.ajax({
				url: 'getColShowByTempl',
				data: {templName:templName,entityName:"${entityName}",colshowType:"${colshowType}"},
				type: 'post',
				dataType: 'json',
				async:true,
				error: function(data){
					alertMsg.error("系统错误!");
				},
				success: function(data){
					var colShows = data.colShows;
					if(colShows.length<=0){
						return ;
					}
					var customLayout = colShows[0].customLayout;
					if(supcan == "supcan"&&customLayout){
						var grid = eval("("+navTabId+")");
						if("${entityName}" == "com.huge.ihos.kq.kqUpData.model.KqDayData"){
							customLayout = kqDayDataColSetting(grid,customLayout);
						}
						grid.func("setCustom", customLayout);
					}
					$.pdialog.closeCurrent();
				}
			});
		});
		jQuery("#saveToNewTempl").click(function(){
			if(jQuery("#saveToNewTemplDiv").is(":visible")){
				jQuery("#saveToNewTemplDiv").hide();
			}else{
				jQuery("#saveToNewTemplDiv").show();
			}
		});
		jQuery("#delColShowTempl").click(function(){
			var templName = jQuery("#colShowTemlSelect").find("option:selected").text();
			if(!templName){
				alertMsg.error("请选择需要删除的格式！");
			}else{
				alertMsg.confirm("确认删除？", {
					okCall : function() {
						jQuery.ajax({
							url: 'delColShowTemplForGz',
							data: {templName:templName,entityName:"${entityName}",colshowType:"${colshowType}"},
							type: 'post',
							dataType: 'json',
							async:true,
							error: function(data){
								alertMsg.error("系统错误!");
							},
							success: function(data){
								formCallBack(data);
								if(data.statusCode == 200){
									jQuery("#colShowTemlSelect").find("option:selected").remove();
								}
							}
						});
					}
				});
			}
		});
		jQuery("#submitColShowTempl").click(function(){
			var isSave = jQuery("#saveToNewTempl").attr("checked")?true:false;
			if(isSave){
				var templName = jQuery("#colShowTemplName").val();
				var colShowTemplToDept = jQuery("#colShowTemplToDept").attr("checked")?true:false;
				var colShowTemplToRole = jQuery("#colShowTemplToRole").attr("checked")?true:false;
				var colShowTemplToPublic = jQuery("#colShowTemplToPublic").attr("checked")?true:false;
				var customLayout = "";
				if(supcan == "supcan"){
					var grid = eval("("+navTabId+")");
					customLayout = grid.func("getCustom","");
				}
				if(!templName){
					alertMsg.error("请输入格式名称！");
				}else{
					jQuery.ajax({
						url: 'saveColShowTemplForGz',
						data: {templName:templName,entityName:"${entityName}",templetToDept:colShowTemplToDept,
							templetToRole:colShowTemplToRole,templetToPublic:colShowTemplToPublic,
							customLayout:customLayout,colshowType:"${colshowType}"},
						type: 'post',
						dataType: 'json',
						async:true,
						error: function(data){
							alertMsg.error("系统错误!");
						},
						success: function(data){
							formCallBack(data);
							$.pdialog.closeCurrent();
						}
					});
				}
			}else{
				var templName = jQuery("#colShowTemlSelect").val();
				if(!templName) {
					$.pdialog.closeCurrent();
					return;
				}else {
					jQuery.ajax({
						url: 'getColShowByTempl',
						data: {templName:templName,entityName:"${entityName}",colshowType:"${colshowType}"},
						type: 'post',
						dataType: 'json',
						async:true,
						error: function(data){
							alertMsg.error("系统错误!");
						},
						success: function(data){
							var colShows = data.colShows;
							if(colShows.length<=0){
								return ;
							}
							var customLayout = colShows[0].customLayout;
							if(supcan == "supcan"){
								var grid = eval("("+navTabId+")");
								if("${entityName}" == "com.huge.ihos.kq.kqUpData.model.KqDayData"){
									customLayout = kqDayDataColSetting(grid,customLayout);
								}
								customLayout = grid.func("setCustom", customLayout);
							}
							$.pdialog.closeCurrent();
						}
					});
				}
			}
		});
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="colShowTemplForm" method="post"	action="saveColShow?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div style="float:left;width:250px">
				<label>格式列表:</label>
				<select id="colShowTemlSelect" size="10" style="width:110px">
					<c:forEach var="templtShow" items="${templtShowList}">
                    	<option value="${templtShow.templetNameV}">${templtShow.templetName}</option>
                    </c:forEach>
				</select>
				</div>
				<div style="margin-left:255px">
					<div class="buttonActive" style="float: none;">
						<div class="buttonContent">
							<button type="Button" id="delColShowTempl">删除此格式</button>
						</div>
					</div>
					<div>
						<input type="checkbox" id="saveToNewTempl"/>另存为新格式
					</div>
					<div id="saveToNewTemplDiv" style="display:none">
					格式名称：<input id="colShowTemplName" size=10/> <br/>
					本部门可见<input id="colShowTemplToDept" name="templetToDept" type="checkbox"/>
					角色可见<input id="colShowTemplToRole" name="templetToRole" type="checkbox"/>
					公共<input id="colShowTemplToPublic" name="templetToPublic" type="checkbox"/>
					</div>
				</div>
			</div>
		<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="Button" id="submitColShowTempl">确定</button>
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
		</form>
	</div>
</div>