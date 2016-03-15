<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	var defColumnFormula = new Array();
	var defColumnFormulaTemple = new Array();
	var defColumnValue = new Array();
	<c:forEach items="${jjUpdataDefColumns}" var="dc">
		<c:if test="${dc.type=='计算'}">
			defColumnFormula.push("${dc.columnName}="+"${dc.formula}");
			defColumnFormulaTemple.push("${dc.columnName}="+"${dc.formula}");
		</c:if>
		<c:if test="${dc.type=='其他'}">
			defColumnFormula.push("${dc.columnName}=q");
			defColumnFormulaTemple.push("${dc.columnName}=q");
		</c:if>
		<c:if test="${dc.type=='手工'}">
			defColumnFormula.push("${dc.columnName}=sg");
			defColumnFormulaTemple.push("${dc.columnName}=sg");
		</c:if>
		//defColumnValue.push("${dc.columnName}=");
	</c:forEach>
	
	/* for(var i=0;i<defColumnFormula.length;i++){
		if(defColumnFormula[i].=='-1'){
			for(var j=0;j<defColumnFormula.length;j++){
				defColumnFormula[j]=defColumnFormula[i].replace("${dc.columnName}","(${dc.formula})");
			}
		}
		if(defColumnFormula[i].indexOf("${dc.columnName}")!=-1){
			alert("${dc.columnName}");
			alert("(${dc.formula})");
			defColumnFormula[i]=defColumnFormula[i].replace("${dc.columnName}","(${dc.formula})");
		}
	} */
	var selectedPersons = "";
	//selectedPersons = jQuery.parseJSON(selectedPersons);
	jQuery(document).ready(function() {
		jQuery('#${random}savelink').click(function() {
			//alert(jQuery("#updataDepts").val());
			if(jQuery("#updataDepts").val()=="-1"){
				alertMsg.error("请选择上报科室！");
				return;
			}
			jQuery("#jjUpdataForm").attr("action","saveJjUpdata?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#jjUpdataForm").submit();
		});
		selectedPersons = jQuery("#deptSp").val();
		//selectedPersons = selectedPersons.replace("[","");
		//selectedPersons = selectedPersons.replace("]","");
		selectedPersons = jQuery.parseJSON(selectedPersons);
		selectedPersons = selectedPersons[0];
		jQuery("#updataDepts").change(function(){
			var selectdept = jQuery(this).val();
			//alert(selectedPersons[selectdept]);
			//jQuery("#jjUpdate_person_name_2").treeselectHide(selectedPersons[selectdept]);
			//addTreeSelect("class","sync","jjUpdate_person_name","jjUpdate_personId","multi",{tableName:"t_person",treeId:"personId",treeName:"name",parentId:"dept_id",filter:"disabled=0 and personId not in "+selectedPersons[selectdept],classTable:"t_department",classTreeId:"deptId",classTreeName:"name"});
			jQuery("#jjUpdate_person_name_2").val("");
			jQuery("#jjUpdate_person_name_2_id").val("");
			jQuery("#jjUpdate_person_name_2").treeselect({
				dataType:"url",
				url:"personJjAndDeptTree",
				params: selectedPersons[selectdept],
				optType:"multi",
				exceptnullparent:true,
				lazy:false
			});
		});
	});
	
	
	function quchuColumn(formula){
		var rsformula = formula;
		//alert("be::"+rsformula);
		if(rsformula.indexOf("\{")!=-1){
			for(var i=0;i<defColumnFormula.length;i++){
				var columnName = rsformula.substring(rsformula.indexOf("\{")+1,rsformula.indexOf("\}"));
				if(defColumnFormula[i].split("=")[0]==columnName){
					//var v = defColumnFormula[i].split("=")[1];
					//v = parseFloat(v);
					rsformula = rsformula.replace("\{"+columnName+"\}",defColumnFormula[i].split("=")[1]);
					//alert(rsformula);
					quchuColumn(rsformula);
				}
			}
		}else{
			//alert(rsformula);
			var rs = eval(rsformula);
			if(rs){
				rs = rs.toFixed(2);
			}
			defColumnValue[columnIndex] = rs;
			//return eval(rsformula);
		}
	}
	var columnIndex = 0;
	function computDefColumn(){
		//var objValue = jQuery(obj).val();
		//var objName = jQuery(obj).attr("name");
		for(var i=0;i<defColumnFormula.length;i++){
			if(defColumnFormula[i].split("=")[1]=='sg'){
				var muValue = jQuery("input[name="+defColumnFormula[i].split("=")[0]+"]").val();
				if(isNaN(muValue)){
					jQuery("input[name="+defColumnFormula[i].split("=")[0]+"]").val("");
					alertMsg.error("请输入数字！");
					return ;
				}
				defColumnFormula[i] = defColumnFormula[i].replace('sg',muValue);
			}
		}
		for(var i=0;i<defColumnFormula.length;i++){
			columnIndex = i;
			var nameAndFormula = defColumnFormula[i].split("=");
			if(nameAndFormula[1]!='q'){
				quchuColumn(nameAndFormula[1]);
				jQuery("input[name="+nameAndFormula[0]+"]").val(defColumnValue[columnIndex]);
			}
			
			//defColumnValue[columnIndex]
			
			/* if(nameAndFormula[1].indexOf(objName)!=-1){
				var tempValue = nameAndFormula[1].replace(objName,objValue);
				tempValue = eval(tempValue);
				jQuery("input[name="+nameAndFormula[0]+"]").val(tempValue);
				/* $.ajax({
				    url: 'computeFormula',
				    data:{formula:tempValue},
				    type: 'post',
				    dataType: 'json',
				    aysnc:false,
				    error: function(data){
				        //jQuery('#name').attr("value",data.responseText);
				    },
				    success: function(data){
				        // do something with xml
				    	jQuery("input[name="+nameAndFormula[0]+"]").val(data.message);
				    }
				}); 
				
			} */
		}
		for(var i=0;i<defColumnFormula.length;i++){
			defColumnFormula[i] = defColumnFormulaTemple[i];
		}
	}
</script>
</head>
<s:hidden id="deptSp" value="%{deptSp}"/>
<div class="page">
	<div class="pageContent">
		<form id="jjUpdataForm" method="post"	action="" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div class="unit">
				<s:hidden key="jjUpdata.updataId"/>
				</div>
				<div class="unit">
					<label><s:text name="jjUpdata.checkperiod"></s:text>:</label>
					<span
						style="float: left; width: 140px;line-height:21px"> <s:property value="checkPeriod"/>
						<input type="hidden" name="jjUpdata.checkperiod" value="<s:property value='checkPeriod'/>"/>
					</span>
					
					<label><s:text name="jjUpdata.itemName"></s:text>:</label>
					<span
						style="float: left; width: 140px;line-height:21px"> <s:property value="itemName"/>
						<input type="hidden" name="jjUpdata.itemName" value="<s:property value='itemName'/>"/>
						</span>
				</div>
				<div class="unit">
				<label><s:text name="jjUpdata.dept"></s:text>:</label>
				<span style="float: left; width: 140px;line-height:21px"> 
				<s:select id="updataDepts" list="updataDepts" listKey="departmentId" listValue="name" key="jjUpdata.department.departmentId" headerKey="-1" headerValue="--" theme="simple"></s:select>
				</span>
				<label>状态标记:</label><span style="float: left; width: 140px;line-height:21px">新建</span>
				</div>
				<!-- <div class="unit">
				<s:hidden id="jjUpdate_personId" name="jjUpdata.person.personId" value="%{jjUpdata.person.personId}"/>
					<label><s:text name="jjUpdata.person"></s:text>:</label>
					<span style="float:left;width:140px">
						<s:textfield id="jjUpdate_person_name" cssClass="required" value="%{jjUpdata.person.name}"   readonly="false" style="width:400px"></s:textfield>
					</span>
					
					<script>																			   //{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"jjdepttype",filter:"",initSelect:"${jjDeptMap.deptIds}",disabledSelect:"${selected_dept_id}",classTable:"KH_Dict_JjDeptType",classTreeId:"jjDeptTypeId",classTreeName:"jjDeptTypeName",classFilter:""}
					addTreeSelect("class","sync","jjUpdate_person_name","jjUpdate_personId","multi",{tableName:"t_person",treeId:"personId",treeName:"name",parentId:"dept_id",filter:"disabled=0 ",classTable:"t_department",classTreeId:"deptId",classTreeName:"name"});
						/* jQuery("#jjUpdate_person_name").treeselect({
							nameField:"jjUpdate_person_name",
							//sql:"select personId as id,name,dept_id as parentId from t_person ",
							tableName:"t_person",
							treeId:"personId",treeName:"name",parentId:"dept_id",filter:"disabled=0 ",order:"",classTable:"t_department",classTreeId:"deptId",classTreeName:"name",classOrder:""
						}); */
					</script>
				
				</div> -->
				<div class="unit">
				<s:hidden id="jjUpdate_person_name_2_id" name="jjUpdata.person.personId" value="%{jjUpdata.person.personId}"/>
					<label><s:text name="jjUpdata.person"></s:text>:</label>
					<span style="float:left;width:140px"><!-- onblur="checkId(document.getElementById('jjDeptMap_personId'),'JjDeptMap','personId','该人员已在列表中存在,请重新选择！')" -->
						<s:textfield id="jjUpdate_person_name_2" cssClass="required" value="%{jjUpdata.person.name}"   readonly="false" style="width:400px"></s:textfield>
					</span>
					
					<script>		
						jQuery("#jjUpdate_person_name_2").treeselect({
							dataType:"url",
							url:"personJjAndDeptTree",
							optType:"multi",
							//sql:"SELECT personId id,name,dept_id parent, '-1' internalCode, '0' isparent FROM v_jj_person where personId<>'XT' and disabled=0  UNION SELECT deptId id,name,'1' parentId ,internalCode, '1' isparent FROM t_department where deptId<>'XT' and disabled=0 ORDER BY internalCode ",
							exceptnullparent:true,
							lazy:false
							/*tableName:"t_person",
							treeId:"personId",treeName:"name",parentId:"dept_id",filter:"disabled=0 ",order:"",classTable:"t_department",classTreeId:"deptId",classTreeName:"name",classOrder:""*/
						});
					</script>
				
				</div>
				<c:if test="${empty jjUpdataDefColumns}">
					<div class="unit">
						<s:textfield id="jjUpdata_amount" key="jjUpdata.amount" name="jjUpdata.amount" cssClass=""/>
					</div>
				</c:if>
				<c:if test="${!empty jjUpdataDefColumns}">
					<s:iterator value="jjUpdataDefColumns" status="it">
						<s:if test="#it.odd==true">
							<div class="unit">
								<label><s:property value="title"/>:</label>
								<span style="float: left; width: 140px;line-height:21px"> 
									<s:if test="type=='手工'">
									<input name="<s:property value='columnName'/>" value="0" onblur="computDefColumn()"/>
									</s:if>
									<s:else><input name="<s:property value='columnName' />" readonly="true" value="0"/>
									</s:else>
								</span>
						</s:if>
						<s:else>
							<label><s:property value="title"/>:</label>
								<span style="float: left; width: 140px;line-height:21px"> 
									<s:if test="type=='手工'">
									<input name="<s:property value='columnName'/>" value="0" onblur="computDefColumn()"/>
									</s:if>
									<s:else><input name='<s:property value="columnName" />' readonly="true" value="0"/>
									</s:else>
								</span>
							</div>
						</s:else>
					</s:iterator>
					<s:if test="jjUpdataDefColumns.size%2!=0">
						</div>
					</s:if>
				</c:if>
				
				<div class="unit">
					<s:textarea id="jjUpdata_remark" key="jjUpdata.remark" name="jjUpdata.remark" cssClass="" cols="50"/>
				</div>
				
			
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="${random}savelink"><s:text name="button.save" /></button>
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





