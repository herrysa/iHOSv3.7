<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
var ztreeSetting_updateMonthPersonForm = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false
		},
		callback : {
			beforeDrag:function(){return false},
			onClick : function(event, treeId, treeNode, clickFlag){
				var treeObj = $.fn.zTree.getZTreeObj(treeId);
				treeObj.checkNode(treeNode, true, false);
			}
		},
		data : {
			key : {
				name : "name"
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pId"
			}
		},
		check: {
			enable: true,
			chkStyle: "checkbox",
			chkboxType: {"Y":"ps", "N":"ps"}
		}
	};
	jQuery(document).ready(function() {
// 		jQuery('#savelink').click(function() {
// 			jQuery("#orgForm").attr("action","saveOrg?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
// 			jQuery("#orgForm").submit();
// 		});
		var personIdLength = 0;
		if("${id}"){
			var personIdStr = "${id}";
			personIdLength = personIdStr.split(",").length;
		}
		jQuery("#updateMonthPeronCount").text("待更新人数:" + personIdLength)
		var periodNodes = [];
		if("${params}"){
			var periodStr = "${params}";
			var periodArr = periodStr.split(",");
			for(var index in periodArr){
				var nodeTemp = {id:periodArr[index],name:periodArr[index]};
				periodNodes.push(nodeTemp);
			}
		}
		$.fn.zTree.init($("#monthPeronPeriodListTree"),ztreeSetting_updateMonthPersonForm, periodNodes);
		var fieldJsonStr = jQuery("#updateMonthPeron_fieldJsonStr").val();
		var fieldNodes = [];
		if(fieldJsonStr){
			fieldNodes = JSON.parse(fieldJsonStr);
		}
		$.fn.zTree.init($("#monthPeronFieldListTree"),ztreeSetting_updateMonthPersonForm, fieldNodes);
	});
	//更新月度职工表数据
	function updateMonthPerson(){
		var periodTreeObj = $.fn.zTree.getZTreeObj("monthPeronPeriodListTree");
		var periodNodes = periodTreeObj.transformToArray(periodTreeObj.getNodes());
		var periodStr = "";
		for(index in periodNodes){
			var node = periodNodes[index];
			if(node.checked){
				periodStr += node.id + ",";
			}
		}
		if(!periodStr){
			alertMsg.error("请选择需要更新的期间！");
			return;
		}
		var fieldTreeObj = $.fn.zTree.getZTreeObj("monthPeronFieldListTree");
		var fieldNodes = fieldTreeObj.transformToArray(fieldTreeObj.getNodes());
		var fieldStr = "";
		for(index in fieldNodes){
			var node = fieldNodes[index];
			if(node.checked){
				fieldStr += node.id + ",";
			}
		}
		if(!fieldStr){
			alertMsg.error("请选择需要更新的字段！");
			return;
		}
		alertMsg.confirm("确认更新？",{
              okCall:function(){
                  var url = "updateMonthPerson?";  
                  $.post(url,{periodStr:periodStr,fieldStr:fieldStr,id:"${id}"},function(data){
                      formCallBack(data);
                      if(data.statusCode == 200){
                    	  $.pdialog.closeCurrent();
                      }
                  });
              }
          });
	}
</script>
</head>
<div class="page">
	<div class="pageContent">
		<form method="post" action="" class="pageForm required-validate" onsubmit="">
			<div class="pageFormContent" layoutH="56">
				<div style="width: 500px;overflow: hidden;margin: 0 auto;margin-top: 10px;">
					<input id="updateMonthPeron_fieldJsonStr" type="hidden" value='<s:property value="fieldJsonStr" escapeHtml="false"/>'>
					<span id="updateMonthPeronCount" style="color: red;display: block;height: 20px;"></span>
					<fieldset style="border:1px solid #5bc0de;width:210px;height:330px;float: left;">
						<legend>期间</legend>
						<div  style="float:left;margin-left:6px;margin-top:6px">
							检索
							<input type="text" style="float:none;width: 100px;" onKeyUp="searchTree('monthPeronPeriodListTree',this)"/>
						</div>
						<div style="float:left;margin-top:6px;overflow:auto;height:266px;width:205px;">
							<div id="monthPeronPeriodListTree" style="float:left;margin-top:6px;" class="ztree"></div>
						</div>
					</fieldset>
					<fieldset style="border:1px solid #5bc0de;width:210px;height:330px;float: right;">
						<legend>字段</legend>
						<div  style="float:left;margin-left:6px;margin-top:6px;">
							检索
							<input type="text" style="float:none;width: 100px;" onKeyUp="searchTree('monthPeronFieldListTree',this)"/>
						</div>
						<div style="float:left;margin-top:6px;overflow:auto;height:266px;width:205px;">
							<div id="monthPeronFieldListTree" style="float:left;margin-top:6px;" class="ztree"></div>
						</div>
					</fieldset>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="updateMonthPerson()">更新</button>
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





