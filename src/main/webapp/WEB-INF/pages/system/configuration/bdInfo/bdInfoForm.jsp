<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#bdInfoFormSaveLink').click(function() {
			var urlString = "saveBdInfo?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}';
			var checked = jQuery("#bdInfo_dbCheck").attr("checked");
			if(checked == "checked"){
				urlString += "&oper=dbInit";
			}
			urlString = encodeURI(urlString);
			jQuery("#bdInfoForm").attr("action",urlString);
			jQuery("#bdInfoForm").submit();
		});
        adjustFormInput("bdInfoForm");
        jQuery("#bdInfo_dbCheck").click(function(){
        	var checked = jQuery(this).attr("checked");
        	if(checked == "checked"){
        		jQuery("#bdInfo_tableName").val("").combogrid({        			
        	        url : '${ctx}/comboGridSqlList',
        	        queryParams : {
        	            sql : "SELECT name,CONVERT(varchar(100), crdate, 20) crdate from sysobjects WHERE type = 'U' " 
        	            	  + " AND name NOT IN (SELECT tableName FROM t_bdinfo) ",
        	            cloumns : 'name,crdate'//搜索字段
        	        },
        	        autoFocus : false,
        	        showOn : false, 
        	        rows:10,
        	        width:540,
        	        sidx:"NAME",
        	        colModel : [ {//columnName必须大写字母
        	            'columnName' : 'NAME',
        	            'width' : '30',
        	            'align' : 'left',
        	            'label' : '表名',
        	            hidden : false
        	        }, {
        	            'columnName' : 'CRDATE',
        	            'width' : '50',
        	            'align' : 'left',
        	            'label' : '创建日期'
        	        }
        	        ],
        	        _create: function( event, item ) {
        	        },
        	        focus: function( event, ui ) {
        	        },
        	        select : function(event, ui) {
        	        	jQuery("#bdInfo_tableName").val(ui.item.NAME);
        	            return false;
        	        }
        	    });
        	}else{
        		jQuery("#bdInfo_tableName").val("");
        	}
        });
	});
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="bdInfoForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="bdInfo.sysField"/>
				<s:hidden key="bdInfo.statistics"/>
				<s:hidden key="bdInfo.changer.personId"/>
				<s:hidden key="bdInfo.changeDate"/>
				<s:hidden key="bdInfo.tableType"/>
				<s:hidden key="bdInfo.mainTable"/>
				<s:hidden key="bdInfo.statisticsSingle"/>
				<s:hidden key="bdInfo.orderByField"/>
				<s:hidden key="bdInfo.orderByFieldAsc"/>
				</div>
				<div class="unit">
					<s:if test="entityIsNew">
					<s:textfield key="bdInfo.bdInfoId" cssClass="required" maxlength="50" notrepeat='id已存在 ' validatorParam="from BdInfo bdInfo where bdInfo.bdInfoId=%value%"/>
					</s:if>
					<s:else>
					<s:textfield key="bdInfo.bdInfoId" maxlength="50" readonly="true"/>
					</s:else>
					<s:textfield id="bdInfo_tableName" key="bdInfo.tableName" cssClass="required" maxlength="50"/>
				</div>
				<div class="unit">
					<s:textfield key="bdInfo.bdInfoName" cssClass="required" maxlength="50"/>
					<s:textfield key="bdInfo.entityName" maxlength="100"/>
				</div>
				<div class="unit">
					<s:textfield key="bdInfo.tableAlias" maxlength="20"/>
					<s:textfield key="bdInfo.subSystem" maxlength="50"/>
				</div>
				<div class="unit">
					<s:textfield key="bdInfo.parentTable" maxlength="20"/>
					<s:textfield key="bdInfo.typeTable" maxlength="20"/>
				</div>
				<div class="unit">
					<s:textfield key="bdInfo.sn" cssClass="digits"/>
					<label><s:text name='bdInfo.treeStructure'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="bdInfo.treeStructure" theme="simple" />
					</span>
				</div>
				<div class="unit">
					<label><s:text name='bdInfo.disabled'/>:</label>
					<span class="formspan" style="float: left; width: 140px">
					<s:checkbox key="bdInfo.disabled" theme="simple" />
					</span>
				</div>
				<div class="unit">
					<s:textarea key="bdInfo.remark" name="bdInfo.remark" cssStyle="width:350px;height:60px;" />
				</div>
				<s:if test="entityIsNew">
					<div class="unit">
						<label>数据库引入:</label>
						<span class="formspan" style="float: left; width: 140px">
							<input id="bdInfo_dbCheck" type="checkbox">
						</span>
					</div>
				</s:if>
			</div>
			<div class="formBar">
				<ul>
					<li id="bdInfoFormSaveLi"><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="bdInfoFormSaveLink"><s:text name="button.save" /></button>
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





