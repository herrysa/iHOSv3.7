<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
var reportPlanDefine = {
		key:"${random}_reportPlan_gridtable",
		main:{
			SetSource : 'getReportDataSourceXml?code=${code}',
			Build : 'getDefineReportXml?code=${code}',
			Load :''
		},
		event:{
			"Load":function( id,p1, p2, p3, p4){
			},
			"Opened":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				grid.func("AddUserFunctions", "getReportFunctionXml?code=${code}");
				grid.func("SetBatchFunctionURL","batchFunc \r\n functions=10000;timeout=9999 \r\n user=normal");
				grid.func("Swkrntpomzqa", "1, 2,4,8"); 
				grid.func("SetAutoCalc","10000");
				setTimeout(function(){
					grid.func("CallFunc","64");
					grid.func("CallFunc","163");
				},300);
				
			},
			"Toolbar":function( id,p1, p2, p3, p4){
				var grid = eval("("+id+")");
				if(p1=="104"){
					grid.func("CancelEvent", "");
				}
			}
		},
		callback:{
		}
	}; 
	
    supcanGridMap.put('reportPlan_gridtable_${random}',reportPlanDefine); 
    var sgridsKeys = supcanGridMap.keys();
    for(var s in  sgridsKeys){
    	var key = sgridsKeys[s];
    	if(key=="reportPlan_gridtable_${random}"){
    		continue;
    	}
    	var sgridObj = jQuery("#"+key).length;
    	if(sgridObj==0){
    		try{
				eval('('+key+')') = null;
			}catch(e){
			}
    		supcanGridMap.remove(key);
    	}
    	console.log(key+":"+sgridObj);
    }
 	jQuery(document).ready(function(){
 		jQuery("#${random}_editReport").click(function(){
	 		var grid = eval("(reportPlan_gridtable_${random})");
 			grid.func("SetAutoCalc","0");
			grid.func("CallFunc","2");
			grid.func("CallFunc","143");
 		});
 		jQuery("#${random}_showReport").click(function(){
	 		var grid = eval("(reportPlan_gridtable_${random})");
 			grid.func("SetAutoCalc","10000");
			grid.func("CallFunc","64");
			grid.func("CallFunc","163");
 		});
 		jQuery("#${random}_saveReport").click(function(){
	 		var grid = eval("(reportPlan_gridtable_${random})");
 			var reportXml = grid.func("GetFileXML", "");
			$.ajax({
	            url: 'saveDefineReportXml',
	            type: 'post',
	            dataType: 'json',
	            data :{code:"${code}",reportXml:reportXml},
	            async:false,
	            error: function(data){
	            	alertMsg.error("系统错误！");
	            },
	            success: function(data){
	            	formCallBack(data);
	            }
	        });
 		});
 		jQuery("#${random}_${searchName}_reload").unbind("click").bind("click",function(){
	 		var grid = eval("(reportPlan_gridtable_${random})");
			<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
				<c:forEach items="${searchItems}" var="item">
					<c:if test="${item.userTag=='checkbox'}">
					var cvs = jQuery("#${random}_${searchName}_${item.htmlField}").attr("values");
					var checked = jQuery("#${random}_${searchName}_${item.htmlField}").attr("checked");
					var cvsArr = "";
					if(cvs){
						cvsArr = cvs.split(";");
					}
					if(checked=='checked'){
						if(cvsArr.length>0){
							jQuery("#${random}_${searchName}_${item.htmlField}").val(cvsArr[0]);
						}else{
							jQuery("#${random}_${searchName}_${item.htmlField}").val(true);
						}
					}else{
						if(cvsArr.length>1){
							jQuery("#${random}_${searchName}_${item.htmlField}").val(cvsArr[1]);
						}else{
							jQuery("#${random}_${searchName}_${item.htmlField}").val(false);
						}
					}
					</c:if>
					var ${item.htmlField} = jQuery("#${random}_${searchName}_${item.htmlField}").val();
					//alert(jQuery("#${random}_${searchName}_${item.htmlField}").attr("requiredd")=="true");
					if(jQuery("#${random}_${searchName}_${item.htmlField}").attr("requiredd")=="true"&&!${item.htmlField}){
						alertMsg.error("'${item.caption}'为必填项！");
						return ;
					}
				</c:forEach>
			</c:if>
			<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
				<c:forEach items="${searchItems}" var="item">
					var cell${item.htmlField} = grid.func("FindCell", "alias='${item.htmlField}'");
					if(${item.htmlField}){
						grid.func("SetCellData",cell${item.htmlField}+"\r\n"+${item.htmlField});
					}
				</c:forEach>;
			</c:if>
			grid.func("SetAutoCalc","10000");
			grid.func("CallFunc","64");
			grid.func("CallFunc","163");
		});
 		jQuery("#${random}_${searchName}_refresh").unbind("click").bind("click",function(){
	 		var grid = eval("(reportPlan_gridtable_${random})");
 			grid.func("Build",'getDefineReportXml?code=${code}&isTemplate=1');
 			grid.func("SetAutoCalc","10000");
			grid.func("CallFunc","64");
			grid.func("CallFunc","163");
 		});
 		jQuery("#${random}_${searchName}_save").unbind("click").bind("click",function(){
	 		var grid = eval("(reportPlan_gridtable_${random})");
 			var cells = grid.func("FindCell", "left(formula,1)='='"); 
 			var cellArr = cells.split(",");
 			for(var i in cellArr){
 				var cell = cellArr[i];
 				var cellTxt = grid.func("GetCellText", cell+"");
 				grid.func("SetCellData", cell+"\r\n");
 				grid.func("SetCellData", cell+"\r\n"+cellTxt);
 			}
 			var rows = grid.func("GetRows","");
 			for(var row=0;row<rows;row++){
 				var isds = grid.func("GetRowProp",row+"\r\nds");
 				if(isds==1){
 					grid.func("SetRowProp",row+"\r\nds\r\n0");
 				}
 			}
 			var fileXml = grid.func("GetFileXML", "isSaveCalculateResult=true"); 
 			$.ajax({
	            url: 'saveReportXml',
	            type: 'post',
	            dataType: 'json',
	            data :{code:'${code}',reportXml:fileXml},
	            async:false,
	            error: function(data){
	            alertMsg.error("系统错误！");
	            },
	            success: function(data){
	            	formCallBack(data);
	            }
	        });
 		});
 		var reportTimer = setInterval(function(){
	 		var containerHeight = jQuery("#${random}_reportPlan_gridtable_container").height();
	 		if(containerHeight>0){
		 		containerHeight = containerHeight-3;
		 		insertReportToDiv("${random}_reportPlan_gridtable_container","reportPlan_gridtable_${random}","Rebar=Main,Print; SeperateBar=none",containerHeight+"px");
		 		clearInterval(reportTimer);
	 		}
 		},100);
 	});

 	function sv(str){
 		var sysVarStr = '${fns:getAllVariableStr()}';
 		var sysVarJson = eval("("+sysVarStr+")");
 		var varIndex = 0;
 		for(var vName in sysVarJson){
 			var vValue = sysVarJson[vName];
 			str = str.replace(vName,vValue);
 		}
 		return str;
 	}
</script>
<div class="page">
				<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
	<div id="${random}_${searchName}_searchDiv" class="pageHeader">
		<form onsubmit="return navTabSearch(this);" action="" method="post">
			<div class="searchBar">
				<div class="searchContent">
									<c:forEach items="${searchItems}" var="item" varStatus="it">
										<label id="${random}_${searchName}label<c:out value="${it.index}"/>" style="float: none">${item.caption}： <c:choose>
												<c:when test="${item.userTag=='yearMonth'}">
													<input class="input-small" type="text"
														id="${random}_${searchName}_${item.htmlField}"
														value="${item.initValueString}" 
														<c:choose>
															<c:when test="${item.length==null}">
																size="10"
															</c:when>
															<c:otherwise>
																size="${item.length}"
															</c:otherwise>
														</c:choose>
														<c:if test="${item.readOnly==true}">readOnly</c:if> 
														<c:if test="${item.required==true}">requiredd='true'</c:if> 
														<c:if test="${item.readOnly!=true}">onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})"</c:if> 
														/><c:if test="${item.required==true}"><font color="#FF0000">*</font></c:if> 
													<%-- <appfuse:yearMonth htmlField="${item.htmlField}" cssClass="input-small"></appfuse:yearMonth> --%>
												</c:when>
												<c:when test="${item.userTag=='checkbox'}">
													<input type="checkbox"
														id="${random}_${searchName}_${item.htmlField}"
														<c:if test="${item.readOnly==true}">disabled='true'</c:if> 
														<c:if test="${item.required==true}">requiredd='true'</c:if> 
														class="input-small" values="${item.param1}"/>
														<c:if test="${item.required==true}"><font color="#FF0000">*</font></c:if> 
												</c:when>
												<c:when test="${item.userTag=='stringSelect'}">
													<appfuse:stringSelect
														htmlFieldName="${random}_${searchName}_${item.htmlField}"
														paraString="${item.param1}"
														initValue="${item.initValueString}" required="false"></appfuse:stringSelect>
													<script>
														var rd = "${item.readOnly==true}"; 
														if(rd=='true'){
															jQuery("#${random}_${searchName}_${item.htmlField}").attr("disabled","true");
														}
													</script>
												</c:when>
												<c:when test="${item.userTag=='stringSelectR'}">
													<appfuse:stringSelect
														htmlFieldName="${random}_${searchName}_${item.htmlField}"
														paraString="${item.param1}"
														initValue="${item.initValueString}" required="true"></appfuse:stringSelect>
														<script>
														var rd = "${item.readOnly==true}"; 
														if(rd=='true'){
															jQuery("#${random}_${searchName}_${item.htmlField}").attr("disabled","true");
														}
													</script>
												</c:when>
												<c:when test="${item.userTag=='sqlSelect'}">
													<appfuse:sqlSelect
														htmlFieldName="${random}_${searchName}_${item.htmlField}"
														paraString="${item.param1}"
														initValue="${item.initValueString}"
														required="false"
														></appfuse:sqlSelect>
														<script>
														var rd = "${item.readOnly==true}"; 
														if(rd=='true'){
															jQuery("#${random}_${searchName}_${item.htmlField}").attr("disabled","true");
														}
													</script>
												</c:when>
												<c:when test="${item.userTag=='sqlSelectR'}">
													<appfuse:sqlSelect
														htmlFieldName="${random}_${searchName}_${item.htmlField}"
														paraString="${item.param1}"
														initValue="${item.initValueString}"
														required="true"
														></appfuse:sqlSelect>
														<script>
														var rd = "${item.readOnly==true}"; 
														if(rd=='true'){
															jQuery("#${random}_${searchName}_${item.htmlField}").attr("disabled","true");
														}
													</script>
												</c:when>
												<c:when test="${item.userTag=='dicSelect'}">
													<appfuse:dictionary code="${item.param1}"
														name="${random}_${searchName}_${item.htmlField}"
														required="false" cssClass="input-small"
														value="${item.initValueString}" />
														<script>
														var rd = "${item.readOnly==true}"; 
														if(rd=='true'){
															jQuery("#${random}_${searchName}_${item.htmlField}").attr("disabled","true");
														}
													</script>
												</c:when>
												<c:when test="${item.userTag=='deptTreeS'}">
													<input id="${random}_${searchName}_${item.htmlField}" name="${random}_${searchName}_${item.htmlField}" type="hidden" <c:if test="${item.required==true}">requiredd='true'</c:if> value="${item.initValueString}"/>
						 							<input id="${random}_${searchName}_${item.htmlField}_name" <c:if test="${item.readOnly==true}">readOnly</c:if> <c:if test="${item.required==true}">requiredd='true'</c:if>  size="${item.length}" />
													<c:if test="${item.required==true}"><font color="#FF0000">*</font></c:if> 
													<script>
														//addTreeSelect("tree","sync","${random}_${searchName}_${item.htmlField}Name","${random}_${searchName}_${item.htmlField}","single",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"parentDept_id",filter:""});
														var rd = "${item.readOnly==true}"; 
														if(rd!='true'){
															jQuery("#${random}_${searchName}_${item.htmlField}_name").treeselect({
																dataType:"sql",
																optType:"single",
																sql:"select deptId id,name,parentDept_id parent from t_department where deptId<>'XT' order by internalCode asc",
																idId:"${random}_${searchName}_${item.htmlField}",
																exceptnullparent:false,
																rebuildByClick:false,
																lazy:false,
																ifr:true,
																callback : {}
															});
														}
														
													
													</script>
												</c:when>
												<c:when test="${item.userTag=='deptTreeM'}">
													<input id="${random}_${searchName}_${item.htmlField}"  type="hidden" <c:if test="${item.required==true}">requiredd='true'</c:if> value="${item.initValueString}"/>
						 							<input id="${random}_${searchName}_${item.htmlField}_name" size="${item.length}"  <c:if test="${item.readOnly==true}">readOnly</c:if> <c:if test="${item.required==true}">requiredd='true'</c:if>  />
													<c:if test="${item.required==true}"><font color="#FF0000">*</font></c:if> 
													<script>
														//addTreeSelect("tree","sync","${random}_${searchName}_${item.htmlField}Namemul","${random}_${searchName}_${item.htmlField}","multi",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"parentDept_id",filter:""});
														var rd = "${item.readOnly==true}"; 
														if(rd!='true'){
														jQuery("#${random}_${searchName}_${item.htmlField}_name").treeselect({
															dataType:"sql",
															optType:"multi",
															sql:"select deptId id,name,parentDept_id parent from t_department where deptId<>'XT' order by internalCode asc",
															idId:"${random}_${searchName}_${item.htmlField}",
															exceptnullparent:false,
															rebuildByClick:false,
															lazy:false,
															ifr:true,
															callback : {}
														});
														}
													</script>
												</c:when>
												<c:when test="${item.userTag=='deptFormulaSelect'}">
													<input id="${random}_${searchName}_${item.htmlField}"  name="${random}_${searchName}_${item.htmlField}" type="hidden" <c:if test="${item.required==true}">requiredd='true'</c:if> value="${item.initValueString}"/>
													<input id="${random}_${searchName}_${item.htmlField}_name" size="${item.length}"  <c:if test="${item.readOnly==true}">readOnly</c:if> <c:if test="${item.required==true}">requiredd='true'</c:if> />
													<c:if test="${item.required==true}"><font color="#FF0000">*</font></c:if> 
													<script>
														//addTreeSelect("list","sync","${random}_${searchName}_${item.htmlField}Namemul","${random}_${searchName}_${item.htmlField}","multi",{tableName:"t_formulaField",treeId:"fieldName",treeName:"fieldTitle",parentId:"fieldTitle",filter:"formulaEntityId=2"});
													var rd = "${item.readOnly==true}"; 
													if(rd!='true'){
														jQuery("#${random}_${searchName}_${item.htmlField}_name").treeselect({
														dataType:"sql",
														optType:"multi",
														sql:"select fieldName id,fieldTitle name,fieldTitle parent from t_formulaField where formulaEntityId=2",
														idId:"${random}_${searchName}_${item.htmlField}",
														exceptnullparent:false,
														rebuildByClick:false,
														lazy:false,
														ifr:true,
														callback : {}
														});
														}
													</script>
												</c:when>
												<c:when test="${item.userTag=='orgFormulaSelect'}">
													<input id="${random}_${searchName}_${item.htmlField}"  type="hidden" <c:if test="${item.required==true}">requiredd='true'</c:if> value="${item.initValueString}"/>
						 							<input id="${random}_${searchName}_${item.htmlField}_name" size="${item.length}"  <c:if test="${item.readOnly==true}">readOnly</c:if> <c:if test="${item.required==true}">requiredd='true'</c:if> />
													<c:if test="${item.required==true}"><font color="#FF0000">*</font></c:if> 
													<script>
														//addTreeSelect("list","sync","${random}_${searchName}_${item.htmlField}Namemul","${random}_${searchName}_${item.htmlField}","multi",{tableName:"t_formulaField",treeId:"fieldName",treeName:"fieldTitle",parentId:"fieldTitle",filter:"formulaEntityId=1"});
														var rd = "${item.readOnly==true}"; 
														if(rd!='true'){
														jQuery("#${random}_${searchName}_${item.htmlField}_name").treeselect({
															dataType:"sql",
															optType:"multi",
															sql:"select fieldName id,fieldTitle name,fieldTitle parent from t_formulaField where formulaEntityId=1",
															idId:"${random}_${searchName}_${item.htmlField}",
															exceptnullparent:false,
															rebuildByClick:false,
															lazy:false,
															ifr:true,
															callback : {}
														});
														}
													</script>
												</c:when>
												<c:when test="${item.userTag=='personTreeS'}">
													<input id="${random}_${searchName}_${item.htmlField}" name="${random}_${searchName}_${item.htmlField}" type="hidden" <c:if test="${item.required==true}">requiredd='true'</c:if> value="${item.initValueString}"/>
						 							<input id="${random}_${searchName}_${item.htmlField}_name" <c:if test="${item.readOnly==true}">readOnly</c:if> size="${item.length}" <c:if test="${item.required==true}">requiredd='true'</c:if> />
													<c:if test="${item.required==true}"><font color="#FF0000">*</font></c:if> 
													<script>
														//addTreeSelect("tree","sync","${random}_${searchName}_${item.htmlField}Name","${random}_${searchName}_${item.htmlField}","single",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"parentDept_id",filter:""});
														var rd = "${item.readOnly==true}"; 
														if(rd!='true'){
															jQuery("#${random}_${searchName}_${item.htmlField}_name").treeselect({
																dataType:"sql",
																optType:"single",
																sql:"select personid id,name name,dept_id parent ,'1' deptCode,0 isParent from t_person where disabled=0 and personid <>'XT' UNION SELECT deptId id , name ,parentDept_id parent , deptCode,1 isParent FROM t_department WHERE disabled=0 AND deptId<>'XT' ORDER BY deptCode",
																idId:"${random}_${searchName}_${item.htmlField}",
																exceptnullparent:true,
																rebuildByClick:false,
																lazy:false,
																ifr:true,
																callback : {}
															});
														}
														
													
													</script>
												</c:when>
												<c:when test="${item.userTag=='personTreeM'}">
													<input id="${random}_${searchName}_${item.htmlField}"  type="hidden" <c:if test="${item.required==true}">requiredd='true'</c:if> value="${item.initValueString}"/>
						 							<input id="${random}_${searchName}_${item.htmlField}_name" size="${item.length}"  <c:if test="${item.readOnly==true}">readOnly</c:if> <c:if test="${item.required==true}">requiredd='true'</c:if> />
													<c:if test="${item.required==true}"><font color="#FF0000">*</font></c:if> 
													<script>
														//addTreeSelect("tree","sync","${random}_${searchName}_${item.htmlField}Namemul","${random}_${searchName}_${item.htmlField}","multi",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"parentDept_id",filter:""});
														var rd = "${item.readOnly==true}"; 
														if(rd!='true'){
														jQuery("#${random}_${searchName}_${item.htmlField}_name").treeselect({
															dataType:"sql",
															optType:"multi",
															sql:"select personid id,name name,dept_id parent ,'1' deptCode,0 isParent from t_person where disabled=0 and personid <>'XT' UNION SELECT deptId id , name ,parentDept_id parent , deptCode,1 isParent FROM t_department WHERE disabled=0 AND deptId<>'XT' ORDER BY deptCode",
															idId:"${random}_${searchName}_${item.htmlField}",
															exceptnullparent:true,
															rebuildByClick:false,
															lazy:false,
															ifr:true,
															callback : {}
														});
														}
													</script>
												</c:when>
												<c:when test="${item.userTag=='costitemTreeS'}">
													<input id="${random}_${searchName}_${item.htmlField}" name="${random}_${searchName}_${item.htmlField}" <c:if test="${item.required==true}">requiredd='true'</c:if> type="hidden" value="${item.initValueString}"/>
						 							<input id="${random}_${searchName}_${item.htmlField}_name" <c:if test="${item.readOnly==true}">readOnly</c:if> size="${item.length}" <c:if test="${item.required==true}">requiredd='true'</c:if> />
													<c:if test="${item.required==true}"><font color="#FF0000">*</font></c:if> 
													<script>
														//addTreeSelect("tree","sync","${random}_${searchName}_${item.htmlField}Name","${random}_${searchName}_${item.htmlField}","single",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"parentDept_id",filter:""});
														var rd = "${item.readOnly==true}"; 
														if(rd!='true'){
															jQuery("#${random}_${searchName}_${item.htmlField}_name").treeselect({
																dataType:"sql",
																optType:"single",
																sql:"select costitemid id,costitem name,isnull(parentid,'1') parent from t_costitem where disabled=0 order by costitemid",
																idId:"${random}_${searchName}_${item.htmlField}",
																exceptnullparent:false,
																rebuildByClick:false,
																lazy:false,
																ifr:true,
																callback : {}
															});
														}
														
													
													</script>
												</c:when>
												<c:when test="${item.userTag=='costitemTreeM'}">
													<input id="${random}_${searchName}_${item.htmlField}"  type="hidden" <c:if test="${item.required==true}">requiredd='true'</c:if> value="${item.initValueString}"/>
						 							<input id="${random}_${searchName}_${item.htmlField}_name" size="${item.length}"  <c:if test="${item.readOnly==true}">readOnly</c:if> <c:if test="${item.required==true}">requiredd='true'</c:if> />
													<c:if test="${item.required==true}"><font color="#FF0000">*</font></c:if> 
													<script>
														//addTreeSelect("tree","sync","${random}_${searchName}_${item.htmlField}Namemul","${random}_${searchName}_${item.htmlField}","multi",{tableName:"t_department",treeId:"deptId",treeName:"name",parentId:"parentDept_id",filter:""});
														var rd = "${item.readOnly==true}"; 
														if(rd!='true'){
														jQuery("#${random}_${searchName}_${item.htmlField}_name").treeselect({
															dataType:"sql",
															optType:"multi",
															sql:"select costitemid id,costitem name,isnull(parentid,'1') parent from t_costitem where disabled=0 order by costitemid",
															idId:"${random}_${searchName}_${item.htmlField}",
															exceptnullparent:false,
															rebuildByClick:false,
															lazy:false,
															ifr:true,
															callback : {}
														});
														}
													</script>
												</c:when>
												<c:when test="${item.userTag=='treeSelectS'}">
													<input type="hidden" id="${random}_${searchName}_${item.htmlField}_param1" value="${item.param1}"/>
													<input id="${random}_${searchName}_${item.htmlField}"  type="hidden" <c:if test="${item.required==true}">requiredd='true'</c:if> value="${item.initValueString}"/>
						 							<input id="${random}_${searchName}_${item.htmlField}_name" size="${item.length}" <c:if test="${item.readOnly==true}">readOnly</c:if>  <c:if test="${item.required==true}">requiredd='true'</c:if> />
													<c:if test="${item.required==true}"><font color="#FF0000">*</font></c:if> 
													<script>
													var rd = "${item.readOnly==true}"; 
													if(rd!='true'){
													var param1 = jQuery("#${random}_${searchName}_${item.htmlField}_param1").val();
													jQuery("#${random}_${searchName}_${item.htmlField}_name").treeselect({
														dataType:"sql",
														optType:"single",
														sql:param1,
														idId:"${random}_${searchName}_${item.htmlField}",
														relateParam:"${item.param2}",
														relatePrefix:"${random}_${searchName}_",
														exceptnullparent:false,
														rebuildByClick:true,
														lazy:true,
														ifr:true,
														callback : {}
													});
													}
													</script>
												</c:when>
												<c:when test="${item.userTag=='treeSelectM'}">
													<input type="hidden" id="${random}_${searchName}_${item.htmlField}_param1" value="${item.param1}"/>
													<input id="${random}_${searchName}_${item.htmlField}"  type="hidden" <c:if test="${item.required==true}">requiredd='true'</c:if> value="${item.initValueString}"/>
						 							<input id="${random}_${searchName}_${item.htmlField}_name" size="${item.length}"  <c:if test="${item.readOnly==true}">readOnly</c:if> <c:if test="${item.required==true}">requiredd='true'</c:if> />
													<c:if test="${item.required==true}"><font color="#FF0000">*</font></c:if> 
													<script>
													var rd = "${item.readOnly==true}"; 
													if(rd!='true'){
														var param1 = jQuery("#${random}_${searchName}_${item.htmlField}_param1").val();
													jQuery("#${random}_${searchName}_${item.htmlField}_name").treeselect({
														dataType:"sql",
														optType:"multi",
														sql:param1,
														idId:"${random}_${searchName}_${item.htmlField}",
														relateParam:null,
														relatePrefix:null,
														exceptnullparent:false,
														rebuildByClick:false,
														lazy:false,
														ifr:true,
														callback : {}
													});
													}
													</script>
												</c:when>
												<c:when test="${item.userTag=='autocomplete'}">
													<input id="${random}_${searchName}_${item.htmlField}" type='hidden' value="${item.initValueString}"/>
													<input id="${random}_${searchName}_${item.htmlField}_name" <c:if test="${item.readOnly==true}">readOnly</c:if> class="defaultValueStyle textInput" <c:if test="${item.required==true}">requiredd='true'</c:if> size="${item.length}" value='拼音/汉字/编码' onfocus="clearInput(this,jQuery('#chargeTypeIdC3'))" onblur="setDefaultValue(this,jQuery('#chargeTypeIdC3'))" onkeydown="setTextColor(this)"/>
												<script>
													jQuery("#${random}_${searchName}_${item.htmlField}_name").autocomplete("autocompleteBySql",
															{
																width : 300,
																multiple : false,
																autoFill : false,
																matchContains : true,
																matchCase : true,
																dataType : 'json',
																parse : function(test) {
																	var data = test.result;
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
																				data : {
																					showValue : data[i].showValue,
																					id : data[i].id,
																					name : data[i].name
																				},
																				value : data[i].id,
																				result : data[i].name
																			};
																		}
																		return rows;
																	}
																},
																extraParams : {
																	sql : "${item.param2}"
																},
																formatItem : function(row) {
																	if(typeof(row)=='string'){
																		return row
																	}else{
																		return row.showValue;
																	}
																	//return dropId(row);
																},
																formatResult : function(row) {
																	return row.showValue;
																	//return dropId(row);
																}
															});
													jQuery("#${random}_${searchName}_${item.htmlField}_name").result(function(event, row, formatted) {
														if (row == "没有结果") {
															return;
														}
														jQuery("#${random}_${searchName}_${item.htmlField}").attr("value", row.id);
													});
												</script>
		
												</c:when>
												<c:when test="${item.userTag=='hidden'}">
													<input id="${random}_${searchName}_${item.htmlField}"  type="hidden" value="${item.initValueString}"/>
												</c:when>
												<c:otherwise>
													<input type="text"
														id="${random}_${searchName}_${item.htmlField}"
														class="input-small" value="${item.initValueString}"
														<c:if test="${item.readOnly==true}">readOnly</c:if> 
														<c:if test="${item.required==true}">requiredd='true'</c:if> 
														 size="${item.length}"/>
													<c:if test="${item.required==true}"><font color="#FF0000">*</font></c:if>
												</c:otherwise>
											</c:choose>
											<label style="float:none;line-height:15px">${item.suffix}</label>
										</label>
										<script>
											var hidden = "${item.hidden==true}"; 
											var itIndex = "${it.index}";
											if(hidden=='true'){
												jQuery("#${random}_${searchName}label"+itIndex).hide();
											}
										</script>
										
										<c:if test="${it.index==4}">
										<br/></c:if>
									</c:forEach>
								
				<div class="buttonActive" style="float:right">
					<div class="buttonContent">
						<button type="button" id="${random}_${searchName}_reload">
							查询
						</button>
					</div>
				</div>
				</div>
			</div>
		</form>
	</div>
	</c:if>
	<div class="panelBar">
		<ul class="toolBar">
			<li><a id="${random}_${searchName}_save" class="savebutton" href="javaScript:"><span>保存</span></a></li>
			<li><a id="${random}_${searchName}_refresh" class="zbcomputebutton" href="javaScript:"><span>重新计算</span></a></li>
		</ul>
	</div>
	<c:choose>
		<c:when test="${searchItems!=null && fn:length(searchItems)>0}">
			<div id="defineReportContent" class="pageContent" layoutH=63>
				<div id="${random}_reportPlan_gridtable_container" style="height:100%"></div>
			</div> 
		</c:when>
		<c:otherwise>
			<div id="defineReportContent" class="pageContent" layoutH=25>
				<div id="${random}_reportPlan_gridtable_container" style="height:100%"></div>
			</div> 
		</c:otherwise>
	</c:choose>
 </div>