<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery("#postForm").find("select").css({"font-size":"12px"});
		jQuery('#savePostlink').click(function() {
			jQuery("#postForm").attr("action","savePost?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}");
			jQuery("#postForm").submit();
		});
		if('${entityIsNew}'=="true"){
			var sn = jQuery("#post_codeSn").val()+"";
			if(sn.length<2){
				sn="0"+sn;
			}
			jQuery("#post_code").val("GW_"+"${post.hrDept.deptCode}"+"_"+sn);
		}
		if("${oper}"=='view'){
			readOnlyForm("postForm");
		}else{
			jQuery("#post_code").addClass("required");
			jQuery("#post_name").addClass("required");
			jQuery("#post_codeSn").addClass("required");
			var deptIdStr = "${deptIdStr}";
			var checkDeptId = "";
			if(deptIdStr){
				var deptIds = deptIdStr.split(",");
				for(var index in deptIds){
					var deptId = deptIds[index];
					if(checkDeptId){
						checkDeptId += ",'"+deptId+"'";
					}else{
						checkDeptId = "'"+deptId+"'";
					}
				}
			}else{
				checkDeptId =  "''";
			}
			var namevalidatorParam = "SELECT post.id AS checkCode FROM hr_post post WHERE post.deptId IN ("+checkDeptId+") AND post.name = %value%";
			jQuery("#post_name").attr("validatorParam",namevalidatorParam);
		} 
		adjustFormInput("postForm");
	});
	function checkCodeForPost(obj){
 		var value = obj.value;
		if(value>99||value<1){
			alertMsg.error("岗位编码应在0~100之间。");
			obj.value = "${post.codeSn}";
		}
		var sn = obj.value+"";
		if(sn.length<2){
			sn="0"+sn;
		}
		jQuery("#post_code").val("GW_"+"${post.hrDept.deptCode}"+"_"+sn);
	}
	function checkPostName(obj){
		var value = obj.value;
		if(!value){
			return;
		}
		if('${entityIsNew}'!="true"){
			var oldName = "${post.name}";
			if(value==oldName){
				return;
			}
		}
		var deptId = "${post.hrDept.departmentId}";
		jQuery.ajax({
            url: 'checkPostName',
            data: {deptId:deptId,postName:value},
            type: 'post',
            dataType: 'json',
            async:false,
            error: function(data){
            },
            success: function(data){
           		if(data){
           			alertMsg.error("此岗位名称已存在。");
           			obj.value="";
           		}
            }
        });
	}
	// 保存岗位后的回调函数
	function savePostCallback(data){
		formCallBack(data);
		if(data.statusCode!=200){
			return;
		}
		if('${entityIsNew}'=="true"){
			var showPostCount = jQuery("#post_showPostCount").attr("checked");
			var post = data.post;
			var deptId = data.deptId;
			var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrDepartmentPostTreeLeft");
			var deptNodeId = deptId;
			var updateNode = hrDeptTreeObj.getNodeByParam("id", deptNodeId, null);
			updateHrDeptPostCountAddOne("hrDepartmentPostTreeLeft",updateNode,showPostCount);
		}
	}
	//岗位数加一
	function updateHrDeptPostCountAddOne(treeObjId,updateNode,showPostCount){
		var treeObj = $.fn.zTree.getZTreeObj(treeObjId);
		var postCount = parseInt(updateNode.postCount)+1;
		var postCountD = parseInt(updateNode.postCountD)+1;
		updateNode.postCountD = postCountD;
		updateNode.postCount = postCount;
		if(showPostCount){
			updateNode.name = updateNode.nameWithoutPerson+"("+postCount+")";
		}
		treeObj.updateNode(updateNode);
		var parentNode = updateNode.getParentNode();
		if(parentNode&&parentNode.subSysTem!="ALL"){
			updateHrDeptPostCountAddOne(treeObjId,parentNode,showPostCount);
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="postForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,savePostCallback);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
					<s:hidden key="post.id"></s:hidden>
					<s:hidden key="post.hrDept.departmentId"></s:hidden>
					<s:hidden key="post.deptSnapCode"></s:hidden>
					<s:hidden key="post.disabled"></s:hidden>
					<label><s:text name='dept.name'/>:</label>
					<input type="text" value="${post.hrDept.name}" readonly="readonly"/>
					<s:textfield id="post_code" key="post.code" name="post.code"  readonly="true" maxlength="50"/>
				</div>
				<div class="unit">
				<s:if test="%{entityIsNew}">
					<s:textfield id="post_name" key="post.name" name="post.name"  maxlength="50" 
					notrepeat='岗位名称已存在' validatorType="sql"  validatorParam=""/>
				</s:if>
				<s:else>
					<s:textfield id="post_name" key="post.name" name="post.name"  maxlength="50" 
					notrepeat='岗位名称已存在' validatorType="sql"  validatorParam="" oldValue="'${post.name}'"/>
				</s:else>
					<s:textfield id="post_codeSn" key="post.codeSn" name="post.codeSn" cssClass="digits" onblur="checkCodeForPost(this)"/>
				</div>
				<div class="unit">
					<label><s:text name='post.directSupervisor'/>:</label>
					<span class="formspan" style="float: left; width: 139px"> 
						<s:select key="post.directSupervisor" list="%{directSupervisorList}"
							maxlength="20" emptyOption="true" theme="simple">
						</s:select>
					</span>
					<%-- <s:textfield key="post.directSupervisor" name="post.directSupervisor" cssClass="" maxlength="50" /> --%>
					<%-- <s:textfield key="post.postOrder" name="post.postOrder" cssClass="" maxlength="50"/> --%>
					<s:textfield key="post.basicSalary" name="post.basicSalary" cssClass="number"/>
				</div>
				<%-- <div class="unit">
					<s:textfield key="post.dutySeries" name="post.dutySeries" cssClass="" maxlength="50"/>
					<s:textfield key="post.postLevel" name="post.postLevel" cssClass="" maxlength="50"/>
				</div>
				<div class="unit">
					<s:textfield key="post.postSeries" name="post.postSeries" cssClass="" maxlength="50"/>
				</div> --%>
				<div class="unit">
					<s:textfield key="post.maxSalary" name="post.maxSalary" cssClass="number "/>
					<s:textfield key="post.minSalary" name="post.minSalary" cssClass="number"/>
				</div>
				<%-- <div class="unit">
					<label><s:text name='post.disabled' />:</label> 
					<span style="float: left; width: 140px"> 
						<s:checkbox theme="simple" key="post.disabled" name="post.disabled" />
					</span>
				</div> --%>
				<div class="unit">				
				      <s:textarea key="post.remark" required="false" maxlength="200" rows="4" cols="53" cssClass="input-xlarge" />
				</div>	
				<s:if test="post.disabled==true">
     				<div style="position:absolute;left:600px;top:10px;padding:2px;z-index:100;border-style: solid;border-width:1px; border-color:red">
      				<span style='color:red;font-size:12px'>已停用</span>
    				 </div>
    			</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="postFormSaveButton">
							<div class="buttonContent">
								<button type="button" id="savePostlink"><s:text name="button.save" /></button>
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





