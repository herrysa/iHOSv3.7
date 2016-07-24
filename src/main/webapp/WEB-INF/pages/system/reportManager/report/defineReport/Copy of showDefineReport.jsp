<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var jzStatus${random} = "${jzStatusStr}";
	if(jzStatus${random}){
		jzStatus${random} = jzStatus${random}.replaceAll("&#034;","'");
		jzStatus${random} = eval("(" +jzStatus${random}+ ")");
	}

	var fullSize = jQuery("#container").innerHeight()+17;
	var fullWidth = jQuery("#container").innerWidth()+17;
	var useFooter = "${sumSearchOptionsNum>0}";
	var isOpen = "${!viewDef.opened}";
	var headLeve = "${headLeve}";
	var reportType = "${reportType}";
	//alert(useFooter+","+isOpen+","+headLeve+","+reportType);
	jQuery("#${random}_${searchName}_container").css("height",fullSize);
	function resizeGridTable(paneName,element,state,options,layoutName){
		var gridTableWidth = state.innerWidth;
		var gridTableHeight = state.innerHeight;
		if(jQuery("#${random}_${searchName}_searchDiv").css("display")=="none"){
			gridTableHeight -= 125;
		}else{
			var searchDivHeight = jQuery("#${random}_${searchName}_searchDiv").css('height');
			searchDivHeight = searchDivHeight.replace("px","");
			if(parseInt(searchDivHeight)>40){
				gridTableHeight -= 186;
			}else{
				gridTableHeight -= 162;
			}
			
		}
		if(useFooter=='true'){
			gridTableHeight -= 22;
		}
		if(headLeve=='2'){
			gridTableHeight -= 22;
		}else if(headLeve=='3'){
			gridTableHeight -= 44;
			jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".jqg-second-row-header").css("display","none");  
	        jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".jqg-third-row-header").css("display","none");  
	        jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".ui-th-column-header").css("display","none");  
	        jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".ui-jqgrid-htable").css("height","68px");  
	        jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".ui-jqgrid-htable").find(".jqg-four-row-header").css("height","68px");
		}
		if(reportType=='1'){
			gridTableHeight -= 20;
		}else if(reportType=='2'){
			gridTableHeight -= 36;
		}
		
		setTimeout(function(){
			jQuery("#${random}_${searchName}_gridTable").setGridWidth(gridTableWidth); 
			jQuery("#${random}_${searchName}_gridTable").setGridHeight(gridTableHeight); 
		},200);
		
	}
	var girdTableLayout;
	jQuery(document).ready(function() {
		var initFlag = 0;
		jQuery("#${random}_${searchName}_gridTable").jqGrid({
			<c:choose>
			   <c:when test="${!viewDef.opened}">
			   /* "", */
			   </c:when>
			   <c:otherwise> 
			   url :encodeURI("${ctx}/queryListJson?searchName=${searchName}&actionName=${actionName}&_key=${_key}&_field=${_field}<c:forEach items='${searchItemInit}' var='op'>&${op}</c:forEach>"),
			   </c:otherwise>
			</c:choose>
			editurl:  "${ctx}/pub?searchName=${searchName}",
			
			<c:choose>
			  <c:when test="${!viewDef.opened}">
			  datatype : "local",
			   </c:when>
			   <c:otherwise> 
			   datatype : "json",
			   </c:otherwise>
			   </c:choose>
			mtype : "GET",
			autowidth : true,
			shrinkToFit :false,
			height:"100%",
			colModel : [
			<c:forEach items="${searchOptions}" var="op">
			{
				name : "${op.fieldNameUpperCase }",
				index : "${op.fieldName }",
				label : "${op.realTitle}",
				width : "${op.displayWidth*10}",
				sortable:${op.sortable},
				frozen : ${op.frozen},
			<c:if test="${op.fieldNameUpperCase==viewDef.upperKey}">
					key:true,
					//frozen : false,
			</c:if>
				//<c:if test="${op.editType=='Label'}">
					/* <c:if test="${op.fieldType=='Boolean'}">
							formatter :"checkbox",
							formatoptions: {disabled: true},
					</c:if>
	 */
		 	 	  	<c:if test="${op.fieldType=='String'}">
						formatter:stringFormatter,
					</c:if> 	 
						
					<c:if test="${op.fieldType=='Money'}">
						<c:choose>
						   <c:when test="${op.displayFormat!=null && fn:length(op.displayFormat)>0 && op.displayFormat!='null'}">
								formatter:numberFormatter, formatoptions:{definedFormat:"${op.displayFormat}"},
						   </c:when>
						   <c:otherwise>
						   		formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2, defaultValue: '0.00'},
						   </c:otherwise>
						</c:choose>
					</c:if>	
					<c:if test="${op.fieldType=='Number'}">
						<c:choose>
						   <c:when test="${op.displayFormat!=null && fn:length(op.displayFormat)>0 && op.displayFormat!='null'}">
								formatter:numberFormatter, formatoptions:{definedFormat:"${op.displayFormat}"},
						   </c:when>
						   <c:otherwise>
						   		formatter:'number', formatoptions:{decimalSeparator:".", thousandsSeparator: "", decimalPlaces: 2, defaultValue: '0.00'},
						   </c:otherwise>
						</c:choose>
					</c:if>		
					
					<c:if test="${op.fieldType=='Date'}">
						formatter:'date', formatoptions:{ newformat: 'Y-m-d'},
					</c:if>		
					<c:if test="${op.fieldType=='Integer'}">
						formatter:'integer', formatoptions:{thousandsSeparator: "", defaultValue: ''},
					</c:if>		
						
				//</c:if>
	 		<c:if test="${op.editType=='Input'}">
	 				editable:true,
	 			<c:if test="${op.fieldType=='Money' || op.fieldType=='Number' }">
	 				editrules:{number:true},
	 			</c:if>
	 				<c:if test="${op.fieldType=='Integer'}">
	 				editrules:{integer:true},
	 			</c:if>
			</c:if>
			<c:if test="${op.editType=='stringSelect' || op.editType=='stringSelectR' || op.editType=='sqlSelect' || op.editType=='sqlSelectR' }">
				editable:true,
				edittype:'select', 
				formatter:'select',
				editoptions:{value:'${op.param2}'},
			</c:if>
				
			<c:if test="${op.fieldType=='Boolean'}">
				formatter :"checkbox",
	 			<c:if test="${op.editType=='CheckBox' }">
					/* formatter :"checkbox", */
					editable:true,
					edittype:'checkbox', 
					editoptions: { value:"1:0" },
				</c:if> 
			</c:if>
			
			
			
			
			
			<c:if test="${!op.visible}">
				hidden:true,
			</c:if>
			<c:if test="${op.visible}">
				highsearch : true,
			</c:if>
			
			<c:if test="${op.merge}">
			cellattr: function(rowId, tv, rawObject, cm, rdata) {
				return 'id=\'${op.fieldNameUpperCase}' + rowId + "\'";
            },
            </c:if>
				align:'${op.alignType}'
			},
			
			</c:forEach>
			            
			     ],
			viewrecords : true,
			rowNum : ${viewDef.pageSize},
			rowList : [${viewDef.pageSize},${viewDef.pageSize},${viewDef.pageSize}*2,${viewDef.pageSize}*3,${viewDef.pageSize}*4,${viewDef.pageSize}*5,${viewDef.pageSize}*6,${viewDef.pageSize}*7,${viewDef.pageSize}*8,${viewDef.pageSize}*9,${viewDef.pageSize}*10],
			prmNames : {
				search : "search"
			}, //(1)
			jsonReader : {
				root : "queryList", // (2)
				records : "records", // (3)
				repeatitems : false	// (4)
			},
			hidegrid : false,
			<c:if test="${viewDef.multiSelect}">
				multiselect: true,
				multiboxonly:true,
			</c:if>
			<c:if test="${sumSearchOptionsNum>0}">
				footerrow : true,
				userDataOnFooter : true,
			</c:if>
			altRows: false,
			rownumbers: true,
			loadui: "disable",
			<c:if test="${viewDef.rownumWidth>0}">
			rownumWidth: ${viewDef.rownumWidth},
			</c:if>
			loadComplete: function(){
				//testSetCell(this);
			},
			jqGridInlineAfterSaveRow:function(rowid, resp, tmp, o){
				
				alert("jqGridInlineAfterSaveRow");
			},
			jqGridInlineSuccessSaveRow:function(res, rowid, o){
				
				alert("jqGridInlineAfterSaveRow");
			},
			gridComplete: function() {
				//alert("gridComplete");
			     //urlFieldFormatter(jQuery(this));
			     //reFormatColumnData(this);
			     var grid = this;
			     	 var rowNum = jQuery(grid).getDataIDs().length;
			     	 var ret = jQuery(grid).jqGrid('getRowData');
			          if(rowNum > 0){
			         	 var rowIds = jQuery(grid).getDataIDs();
			         	 var i=0
			         	 for (i=0;i<rowNum;i++){
			         	 <c:forEach items="${searchOptions}" var="op">
			     	    	 <c:if test="${op.url!=null && fn:length(op.url)>0 }">
			     		    	var id = rowIds[i];
			     		    	var data = ret[i]["${op.fieldNameUpperCase}"];
			     		    	var linkField="${op.linkField}";
			     		    	var fields=linkField.split(",");
			     		    	
			     		    	var j=0;
			     		    	var args="";
			     		    	for(j=0;j<fields.length;j=j+2){
			     		    		try{
			     		    		args=args+"&"+fields[j]+"="+ret[i][fields[j+1].toUpperCase()];
			     		    		}
			     					catch(err)
			     					{
			     					}
			     		    	}
			     		    	var hrefUrl = "${ctx}${op.url}"+args;
			     		    	hrefUrl=encodeURI(hrefUrl);
			     				setCellText(grid,id,'${op.fieldNameUpperCase}','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="zhuanqu(\''+hrefUrl+'\')" target="navTab">'+data+"</a>")
			     		    </c:if>
			     			
			     			
			     			 <c:if test="${op.threshold!=null && fn:length(op.threshold)>0 && op.threshold!='null'}">
			     		    	var id = rowIds[i];
			     		    	var data = ret[i]["${op.fieldNameUpperCase}"];
			     		    	//console.log("before is: "+ data);
			     		    	data = data.replace(/,/g,"");
			     		    	//console.log("after is: "+ data);
			     		    	var threshold="${op.threshold}";
			     		    	threshold = unescapeHTML(threshold);
			     		    	var fields=threshold.split(";");
			     		    	var j=0;
			     		    	var args="";
			     		    	var param;
			     		    	if(typeof data == 'string'){
	     		    				data = "'"+data+"'";
	     		    			}
			     		    	 for(j=0;j<fields.length;j++){
			     		    		try{
			     		    			param=fields[j].split(",");
			     		    			
			     		    			if(eval(data+param[0]+" && "+ data+param[1])){
			     		    				//console.log(data+param[0]+' && '+ data+param[1]);
			     		    				//console.log(param[2]);
			     		    			    jQuery(grid).setCell(id,'${op.fieldNameUpperCase}','',{color:param[2]});
			     		    				
			     		    				}
			     		    		}
			     					catch(err)
			     					{
			     						//alert(err);
			     					}
			     		    	} 
			     			</c:if>
			     			<c:if test="${op.thresholdR!=null && fn:length(op.thresholdR)>0 && op.thresholdR!='null'}">
			     				var id = rowIds[i];
		     		    		var data = ret[i]["${op.fieldNameUpperCase}"];
		     		    		data = data.replace(/,/g,"");
			     		    	var thresholdR="${op.thresholdR}";
			     		    	thresholdR = unescapeHTML(thresholdR);
			     		    	var fieldRs=thresholdR.split(";");
	     		    			if(typeof data == 'string'){
	     		    				data = "'"+data+"'";
	     		    			}
			     		    	 for(j=0;j<fieldRs.length;j++){
			     		    		try{
			     		    			var tparam=fieldRs[j].split(",");
			     		    			//alert(data);
			     		    			//alert(eval(data+tparam[0]+" && "+ data+tparam[1]));
			     		    			if(eval(data+tparam[0]+" && "+ data+tparam[1])){
			     		    				
			     		    			    //jQuery(grid).setCell(id,'${op.fieldNameUpperCase}','',{color:param[2]});
			     		    				jQuery("#"+id).find("td").css("background-color", tparam[2]);
			     		    			}
			     		    		}
			     					catch(err){
			     						//alert(err);
			     					}
			     		    	} 
		     				</c:if>
			         	 </c:forEach>
			         	 }
			         	 }else{
			         		var tw = jQuery("#${random}_${searchName}_gridTable").outerWidth();
							 jQuery("#${random}_${searchName}_gridTable").parent().width(tw);
							 jQuery("#${random}_${searchName}_gridTable").parent().height(1);
			         	 }
			     var layoutSettings = {
						applyDefaultStyles : true // basic styling for testing & demo purposes
						,
						onresize_end : function(paneName,element,state,options,layoutName){

							var fullSize = jQuery("#container").innerHeight()+17;
	var fullWidth = jQuery("#container").innerWidth()+17;
	var useFooter = "${sumSearchOptionsNum>0}";
	var isOpen = "${!viewDef.opened}";
	var headLeve = "${headLeve}";
	var reportType = "${reportType}";
	
		var gridTableWidth = state.innerWidth;
		var gridTableHeight = state.innerHeight;
		if(jQuery("#${random}_${searchName}_searchDiv").css("display")=="none"){
			gridTableHeight -= 125;
		}else{
			var searchDivHeight = jQuery("#${random}_${searchName}_searchDiv").css('height');
			searchDivHeight = searchDivHeight.replace("px","");
			if(parseInt(searchDivHeight)>40){
				gridTableHeight -= 186;
			}else{
				gridTableHeight -= 162;
			}
			
		}
		if(useFooter=='true'){
			gridTableHeight -= 22;
		}
		if(headLeve=='2'){
			gridTableHeight -= 22;
		}else if(headLeve=='3'){
			gridTableHeight -= 44;
			jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".jqg-second-row-header").css("display","none");  
	        jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".jqg-third-row-header").css("display","none");  
	        jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".ui-th-column-header").css("display","none");  
	        jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".ui-jqgrid-htable").css("height","68px");  
	        jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".ui-jqgrid-htable").find(".jqg-four-row-header").css("height","68px");
		}
		if(reportType=='1'){
			gridTableHeight -= 20;
		}else if(reportType=='2'){
			gridTableHeight -= 36;
		}
		
		setTimeout(function(){
			jQuery("#${random}_${searchName}_gridTable").setGridWidth(gridTableWidth); 
			jQuery("#${random}_${searchName}_gridTable").setGridHeight(gridTableHeight); 
		},200);
		
	}
				};
			     makepager("${random}_${searchName}_gridTable");
			     girdTableLayout = jQuery("#${random}_${searchName}_container").layout(layoutSettings);
			     jQuery("#${random}_${searchName}_layout-center").css("padding",0);
				 layoutResize(girdTableLayout);
				 jQuery("#gview_${random}_${searchName}_gridTable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
						jQuery(this).find("th").each(function(){
							//jQuery(this).css("vertical-align","middle");
							//jQuery(this).find("div").eq(0).css("margin-top","5px");
							jQuery(this).find("div").eq(0).css("line-height","18px");
						});
					});
				 
				 jQuery("#${random}_${searchName}_gridTable").unbind("keyup").bind("keyup",function(e){
					 chargeKeyPress(jQuery("#${random}_${searchName}_gridTable"),e);
					});
				 var cols = new Array();
				 <c:forEach items="${searchOptions}" var="op">
				 	<c:if test="${op.merge}">
				 		Merger("${random}_${searchName}_gridTable","${op.fieldNameUpperCase}");
				 		cols.push("${op.fieldNameUpperCase}");
				 	</c:if>
				 </c:forEach>
				 MergerRow("${random}_${searchName}_gridTable",cols);
				
				//mergeTitle();

				initFlag = initColumn('${random}_${searchName}_gridTable','${searchName}',initFlag);
				
				/* jQuery('#${random}_${searchName}_gridTable').contextMenu('searchGridCM',{
					bindings:{
					showInfo:function(t,m){
						var url = "#DIA_inline?inlineId=searchGridPropertyDiv";
						$.pdialog.open(url, 'searchGridProperty', "属性", {
							mask : false,
							width : 400,
							height : 200
						});
					}
					}
				}); */
			}
		}
		
		
		);
	
		jQuery("#${random}_${searchName}_gridTable").jqGrid('bindKeys');
		
		jQuery("#${random}_${searchName}_gridTable").jqGrid('setFrozenColumns');
		
		//jQuery( "button" ).button();
		<c:choose>
			<c:when test="${headLeve=='3'}">
				jQuery("#${random}_${searchName}_gridTable").jqGrid('setComplexHeaders',{
					complexHeaders:{
					defaultStyle:true,
					twoLevel:[
						<c:forEach items="${secondHeaders}" var="sh" varStatus="stat">
						
						{startColumnName: '${sh.startColumnName}', numberOfColumns: ${sh.numberOfColumns}, titleText: '${sh.headerTitleText}'} <c:if test="${!stat.last}">,</c:if>  
						</c:forEach>
					],
					threeLevel:[//三级表头，和二级表头用法一样
						<c:forEach items="${thirdHeaders}" var="sh" varStatus="stat">
							{startColumnName: '${sh.startColumnName}', numberOfColumns: ${sh.numberOfColumns}, titleText: '${sh.headerTitleText}'}<c:if test="${!stat.last}">,</c:if>
						</c:forEach>
					]
					}
				});
			</c:when>
				
			<c:otherwise>
				jQuery("#${random}_${searchName}_gridTable").jqGrid('setGroupHeaders',{
						useColSpanStyle :true,
						groupHeaders :[
						<c:forEach items="${secondHeaders}" var="sh" varStatus="stat">
						
						{startColumnName: '${sh.startColumnName}', numberOfColumns: ${sh.numberOfColumns}, titleText: '${sh.headerTitleText}'} <c:if test="${!stat.last}">,</c:if>  
						</c:forEach>
					  ]
				});
			</c:otherwise>
		</c:choose>
		
		var htableHeight = jQuery(".ui-jqgrid-htable","#${random}_${searchName}_gridTable_div").height();
		jQuery(".frozen-div","#${random}_${searchName}_gridTable_div").height(htableHeight);
		jQuery(".frozen-div","#${random}_${searchName}_gridTable_div").find("table>thead>tr").height(htableHeight);
		jQuery(".frozen-bdiv","#${random}_${searchName}_gridTable_div").css("top",htableHeight+1);
		
		//alert(htableHeight);
		
	 
		jQuery("#${random}_${searchName}_searchDivSimple").prepend("<input name='${random}_${searchName}_searchSimpleItem' type='text'/>");
		jQuery("#${random}_${searchName}_searchDivSimple").prepend("<select id='${random}_${searchName}_searchItem' style='width:95px'></select>&nbsp;");
		jQuery("#${random}_${searchName}_searchItem").append("<option value='-1'>--选择条件--</option>");
		jQuery("#${random}_${searchName}_searchDiv").find("label").each(function(){
			
			if(jQuery(this).find(":input").eq(0)!=null&&jQuery(this).find(":input").eq(0).attr("type")=="text"){
				var itemName = jQuery(this).text();
				itemName = itemName.substring(0,itemName.length-1);
				jQuery("#${random}_${searchName}_searchItem").append("<option value='"+jQuery(this).children().eq(0).attr("id")+"'>"+itemName+"</option>");
			}
		});
		jQuery("#${random}_${searchName}_searchItem").change(function(){
			
			jQuery("input[name=${random}_${searchName}_searchSimpleItem]").attr("id",jQuery(this).val());
			jQuery("input[name=${random}_${searchName}_searchSimpleItem]").attr("onClick",jQuery("#"+jQuery(this).val()).attr("onClick"));
			jQuery("input[name=${random}_${searchName}_searchSimpleItem]").attr("value",jQuery("#"+jQuery(this).val()).attr("value"));
		});
		var type = '${viewDef.searchBarType}';
		if(type==null){
			type=1;
		}
		if(type==0){
			jQuery("#${random}_${searchName}_searchDiv").hide();
			jQuery("#${random}_${searchName}_searchDivSimple").hide();
		}else if(type==1){
			jQuery("#${random}_${searchName}_searchDiv").hide();
		}else{
			jQuery("#${random}_${searchName}_higherButton").text("简单");
			jQuery("#${random}_${searchName}_searchDivSimple").hide();
		}
		
		jQuery("#${random}_${searchName}_higherButton").click(function(){
			if(type==1){
				jQuery("#${random}_${searchName}_searchDiv").show();
				jQuery(this).text("简单");
				type=0;
				
			}else{  
				jQuery("#${random}_${searchName}_searchDiv").hide();
				jQuery(this).text("高级");
				type=1;
			}
			layoutResize(girdTableLayout);
		});

		if(isOpen=='true'){
			setTimeout(function(){
				var layoutSettings = {
						applyDefaultStyles : true // basic styling for testing & demo purposes
						,onresize_end : function(paneName,element,state,options,layoutName){
							var fullSize = jQuery("#container").innerHeight()+17;
							var fullWidth = jQuery("#container").innerWidth()+17;
							var useFooter = "${sumSearchOptionsNum>0}";
							var isOpen = "${!viewDef.opened}";
							var headLeve = "${headLeve}";
							var reportType = "${reportType}";

		var gridTableWidth = state.innerWidth;
		var gridTableHeight = state.innerHeight;
		if(jQuery("#${random}_${searchName}_searchDiv").css("display")=="none"){
			gridTableHeight -= 125;
		}else{
			var searchDivHeight = jQuery("#${random}_${searchName}_searchDiv").css('height');
			searchDivHeight = searchDivHeight.replace("px","");
			if(parseInt(searchDivHeight)>40){
				gridTableHeight -= 186;
			}else{
				gridTableHeight -= 162;
			}
			
		}
		if(useFooter=='true'){
			gridTableHeight -= 22;
		}
		if(headLeve=='2'){
			gridTableHeight -= 22;
		}else if(headLeve=='3'){
			gridTableHeight -= 44;
			jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".jqg-second-row-header").css("display","none");  
	        jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".jqg-third-row-header").css("display","none");  
	        jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".ui-th-column-header").css("display","none");  
	        jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".ui-jqgrid-htable").css("height","68px");  
	        jQuery("#${random}_${searchName}_gridTable_div").find(".frozen-div").find(".ui-jqgrid-htable").find(".jqg-four-row-header").css("height","68px");
		}
		if(reportType=='1'){
			gridTableHeight -= 20;
		}else if(reportType=='2'){
			gridTableHeight -= 36;
		}
		
		setTimeout(function(){
			jQuery("#${random}_${searchName}_gridTable").setGridWidth(gridTableWidth); 
			jQuery("#${random}_${searchName}_gridTable").setGridHeight(gridTableHeight); 
		},200);
		
	}
				};
			     makepager("${random}_${searchName}_gridTable");
			     girdTableLayout = jQuery("#${random}_${searchName}_container").layout(layoutSettings);
			     jQuery("#${random}_${searchName}_layout-center").css("padding",0);
				 layoutResize(girdTableLayout);
				 jQuery("#gview_${random}_${searchName}_gridTable").find(".ui-jqgrid-htable").eq(0).find("tr").each(function(){
						jQuery(this).find("th").each(function(){
							//jQuery(this).css("vertical-align","middle");
							//jQuery(this).find("div").eq(0).css("margin-top","5px");
							jQuery(this).find("div").eq(0).css("line-height","18px");
						});
					});
			},'150');
		}
		jQuery("#${random}_${searchName}_reload").unbind("click").bind("click",function(){
			<c:choose>
			   <c:when test="${actionName=='queryEx'}">
					var urlString="${ctx}/query?searchName=${searchName}&actionName=${actionName}&_key=${_key}&_field=${_field}";
			   </c:when>
			   <c:otherwise>
					var urlString = "${ctx}/queryListJson?searchName=${searchName}";
			   </c:otherwise>
			</c:choose>
			
			
			
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
				urlString=urlString<c:forEach items="${searchItems}" var="item">+"&${item.htmlField}="+${item.htmlField}</c:forEach>;
			</c:if>
			//alert(urlString); 
			urlString=encodeURI(urlString);
			<c:choose>
			   <c:when test="${actionName=='queryEx'}">
			   //alert(urlString);
				   navTab.reload(urlString, {
						title : "New Tab",
						fresh : false,
						data : {}
					});
			   </c:when>
			   <c:otherwise>
			   
					jQuery("#${random}_${searchName}_gridTable").jqGrid('setGridParam',{url:urlString,page:1}).jqGrid('setGridParam', { datatype : 'json' }).trigger("reloadGrid");
			   </c:otherwise>
			</c:choose>
		});
		
		jQuery("#${random}_${searchName}_fullGridEdit").click(function(){
			try{
				var scrollTop = jQuery("#${random}_${searchName}_gridTable_div").find(".ui-jqgrid-bdiv").scrollTop();
				var ids = jQuery("#${random}_${searchName}_gridTable").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					var cl = ids[i];
					jQuery("#${random}_${searchName}_gridTable").editRow(cl);
				}	
				jQuery("#${random}_${searchName}_gridTable_div").find(".ui-jqgrid-bdiv").scrollTop(scrollTop)
			}catch(err){
				alert(err);
			}
		});
		jQuery("#${random}_${searchName}_restoreRowGrid").click(function(){
				try{
					var ajaxbg = $("#background,#progressBar");
					$(document).ajaxStart(function(){
						ajaxbg.hide();
					}).ajaxStop(function(){
						ajaxbg.hide();
					});
					
					
					var currentPage = jQuery("#${random}_${searchName}_gridTable").jqGrid('getGridParam', 'page');
					
					jQuery("#${random}_${searchName}_gridTable").trigger('reloadGrid',
						[ {
								page : currentPage
							} ]);
					$(document).ajaxStart(function(){
						ajaxbg.show();
					}).ajaxStop(function(){
						ajaxbg.hide();
					});
					//editQueryRowCount=0;
				}catch(err){
					alert(err);
				}
		});
		jQuery("#${random}_${searchName}_saveRowGrid").click(function(){
			var random = "${random}",searchName = "${searchName}";
				try{
					var gridId = "#"+random+"_"+searchName+"_gridTable"
					var scrollTop = jQuery(gridId+"_div").find(".ui-jqgrid-bdiv").scrollTop();
					var saveUrl = "pub?searchName="+searchName+"&actionName=saveRow";
					var grid=jQuery(gridId);
					var entityArray="";
					var errorMsg;
					var editDataId = grid.jqGrid('getGridParam','selrow');
				/* if(!editDataId){
					editDataId = "new_row";
				} */
					if(editDataId=="new_row"){
						saveUrl+="&editType=add";
						var editUrl = grid.jqGrid('getGridParam', 'editurl');
						 var  saveparameters = {
										    "url" : 'clientArray',
										     "aftersavefunc" : function(rowid,resp,tmp) {
										                          jQuery.each(tmp, function(name, value) {
										                        	  if(name!="oper")
										                        	  entityArray = entityArray + name+":"+value+"|";
										                        	  //alert(name);
										                        	}); 
										                          entityArray+=";";
										                         
										                      }
										}; 
						var returnRes =grid.saveRow('new_row', saveparameters );
						//alert(entityArray);
						//editRowCount=0;
						//editDataId="";
						errorMsg="数据类型错误！";
					}else{
						var ids = grid.jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							var cl = ids[i];
							if($($(gridId).jqGrid("getInd",cl,true)).attr("editable") === "1") {
							 
							var  saveparameters = {
									    "url" : 'clientArray',
									     "aftersavefunc" : function(rowid,resp,tmp) {
									                          jQuery.each(tmp, function(name, value) {
									                        	  if(name!="oper")
									                        	  entityArray = entityArray + name+":"+value+"|";
									                        	//console.log(entityArray);
									                        	}); 
									                          entityArray+=";";
									                         
									                      }
									};
					
							 var returnRes =$(gridId).saveRow(cl, saveparameters );
							if(returnRes){
							continue;
							}
							else{
								break;
							}
							}
						}	
						errorMsg="编辑保存失败,有可能是由于编辑内容过多,请尝试每页条数在100以内！！！";
				}
					  jQuery.ajax({
						dataType : 'json',
						url:saveUrl,
						data:'allParam='+entityArray,
					    type: 'POST',
					    async:'false',
					    error: function(data){
					    	//alert(data);
					    	alertMsg.confirm(errorMsg);
					    },success: function(data){
					    	//editQueryRowCount=0;
					    	jQuery(gridId).trigger("reloadGrid");
					    	setTimeout(function(){
					    		if(editDataId){
						    		jQuery(gridId).jqGrid('setSelection',editDataId);
					    			jQuery(gridId+"_div").find(".ui-jqgrid-bdiv").scrollTop(scrollTop);
						    	}
					    	},500);
					    	
					    }
					}); 
				}catch(err){
					alert(err);
				}
		});
	});
	
	function Merger(gridName, CellName) {
        //得到显示到界面的id集合
        var mya = $("#" + gridName + "").getDataIDs();
        //当前显示多少条
        var length = mya.length;
        for (var i = 0; i < length; i++) {
            //从上到下获取一条信息
            var before = $("#" + gridName + "").jqGrid('getRowData', mya[i]);
            //定义合并行数
            var rowSpanTaxCount = 1;
            for (j = i + 1; j <= length; j++) {
                //和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏
                var end = $("#" + gridName + "").jqGrid('getRowData', mya[j]);
                if (before[CellName] == end[CellName]) {
                    rowSpanTaxCount++;
                    $("#" + gridName + "").setCell(mya[j], CellName, '', { display: 'none' });
                } else {
                    rowSpanTaxCount = 1;
                    break;
                }
                $("#" + CellName + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount);
            } 
        } 
    }
	function MergerRow(gridName,cols) {
		
        //得到显示到界面的id集合
        var mya = $("#" + gridName + "").getDataIDs();
        //当前显示多少条
        var length = mya.length;
        for (var i = 0; i < length; i++) {
            //从上到下获取一条信息
            var rowData = $("#" + gridName + "").jqGrid('getRowData', mya[i]);
            //定义合并行数
            var colSpanTaxCount = 1;
            for(var ci=0;ci<cols.length-1;ci++){
            	if(rowData[cols[ci]]==rowData[cols[ci+1]]){
            		colSpanTaxCount++;
            		$("#" + gridName + "").setCell(mya[i], cols[ci+1], '', { display: 'none' });
            	} else {
                    colSpanTaxCount = 1;
                    break;
                }
    		}
            $("#" + cols[0] + "" + mya[i] + "").attr("colspan", colSpanTaxCount);
            /* var colLenth = ;
            for (j = 0; j <colLenth; j++) {
                var end = $("#" + gridName + "").jqGrid('getRowData', mya[j]);
                
                if (before[CellName] == end[CellName]) {
                    rowSpanTaxCount++;
                    $("#" + gridName + "").setCell(mya[j], CellName, '', { display: 'none' });
                } else {
                    rowSpanTaxCount = 1;
                    break;
                }
                $("#" + CellName + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount);
            }  */
        } 
    }
	
	function mergeTitle(){
		var beforeTh,beforeThText,thColspan=1;
		jQuery(".ui-jqgrid-htable","#${random}_${searchName}_gridTable_div").find("th").each(function(){
			if(beforeThText&&(beforeThText==jQuery(this).text())){
					jQuery(this).hide();
					thColspan++;
			}else{
				thColspan = 1;
				beforeTh = jQuery(this);
				beforeThText = jQuery(this).text();
			}
			beforeTh.attr("colspan", thColspan);
		});
	}
	
	function gridReload(){
		
		
		<c:choose>
		   <c:when test="${actionName=='queryEx'}">
				var urlString="${ctx}/query?searchName=${searchName}&actionName=${actionName}&_key=${_key}&_field=${_field}";
		   </c:when>
		   <c:otherwise>
				var urlString = "${ctx}/queryListJson?searchName=${searchName}";
		   </c:otherwise>
		</c:choose>
		
		
		
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
			</c:forEach>
		</c:if>
		<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
			urlString=urlString<c:forEach items="${searchItems}" var="item">+"&${item.htmlField}="+${item.htmlField}</c:forEach>;
		</c:if>
		//alert(urlString);
		urlString=encodeURI(urlString);
		<c:choose>
		   <c:when test="${actionName=='queryEx'}">
		   //alert(urlString);
			   navTab.reload(urlString, {
					title : "New Tab",
					fresh : false,
					data : {}
				});
		   </c:when>
		   <c:otherwise>
		   
				jQuery("#${random}_${searchName}_gridTable").jqGrid('setGridParam',{url:urlString,page:1}).jqGrid('setGridParam', { datatype : 'json' }).trigger("reloadGrid");
		   </c:otherwise>
		</c:choose>
		
		
	}
	var searchTitle="${searchTitle}";
	function chartGridReload(){
		var urlString="${ctx}/chartQuery?searchName=${searchName}&actionName=queryChartEx&_key=${_key}&_field=${_field}&searchTitle="+searchTitle;
		//urlString = encodeURI(urlString);
		<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
			<c:forEach items="${searchItems}" var="item">
				var ${item.htmlField} = jQuery("#${random}_${searchName}_${item.htmlField}").val();
			</c:forEach>
		</c:if>
		<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
			urlString=urlString<c:forEach items="${searchItems}" var="item">+"&${item.htmlField}="+${item.htmlField}</c:forEach>;
		</c:if>
		urlString=encodeURI(urlString);
		$.pdialog.open(urlString, "txxs", "图形显示",{width:800,height:600});
 		/* navTab.openTab("txxs",urlString, {
			title : "图形显示",
			fresh : false,
			data : {}
		}); */
 	
	}
	
	function gridReloadSimple(){
		
		var urlString = "${ctx}/queryListJson?searchName=${searchName}";
		var itemValue = jQuery("input[name=${random}_${searchName}_searchSimpleItem]").val();
		var itemId = jQuery("input[name=${random}_${searchName}_searchSimpleItem]").attr("id");
		itemId = itemId.replace("${random}_${searchName}_","");
		if(itemValue==null||itemValue==-1){
		}else{
			urlString=urlString+"&"+itemId+"="+itemValue;
		}
		urlString=encodeURI(urlString);
		jQuery("#${random}_${searchName}_gridTable").jqGrid('setGridParam',{url:urlString,page:1}).jqGrid('setGridParam', { datatype : 'json' }).trigger("reloadGrid");
	}
	
 	function outputEXCEL(fileName){
 		if(fileName!=null&&fileName!=""){
 			$.ajax({
 			    url: 'outputExcelFileExit?fileName='+fileName,
 			    type: 'post',
 			    dataType: 'json',
 			    async:false,
 			    error: function(data){
 			    },
 			    success: function(data){
 			        if(data.message=='0'){
 			        	alertMsg.confirm("模板文件 "+fileName+" 不存在，是否继续？", {
 			        		okCall: function(){
 			        			var urlC=jQuery('#${random}_${searchName}_gridTable').jqGrid('getGridParam','url');
 			        			var str = urlC.split("?");
 			        			var url = "${ctx}/outputExcel?fileName=&"+str[1];
 			        			//url=encodeURI(url);
 			        			location.href=url; 
 			        		},
 			        		cancelCall: function(){
 			        		}
 			        	});
 			        }else{
 			        	var urlC=jQuery('#${random}_${searchName}_gridTable').jqGrid('getGridParam','url');
 			     		var str = urlC.split("?");
 			    		var url = "${ctx}/outputExcel?fileName="+fileName+"&"+str[1];
 			    		//url=encodeURI(url);
 			    		location.href=url; 
 			        }
 			    }
 			});
 		}else{
 			var urlC=jQuery('#${random}_${searchName}_gridTable').jqGrid('getGridParam','url');
 			var urlC=jQuery('#${random}_${searchName}_gridTable').jqGrid('getGridParam','url');
 			var str = urlC.split("?");
 			var url = "${ctx}/outputExcel?fileName=&"+str[1];
 			//url=encodeURI(url);
 			location.href=url; 
 		}
	} 
 	
 	function outputEXCELByRow(fileName,sheetName){
 		var sid = jQuery('#${random}_${searchName}_gridTable').jqGrid('getGridParam','selarrrow');
		var dataMap = {};
 		if(sid==null||sid.length==0){
 			alertMsg.error("<fmt:message key='list.selectNone'/>");
 			return ;
 		}else{
 			for(var rowIndex=0;rowIndex<sid.length;rowIndex++){
	 			var ret = jQuery('#${random}_${searchName}_gridTable').jqGrid('getRowData',sid[rowIndex]);
 				for(var colName in ret){
 					var colValue = ret[colName];
 					if(colValue.indexOf("<a ")!=-1){
 						var trueValue = jQuery(colValue).text();
 						ret[colName] = trueValue;
 					}
 				}
 				var sheetKey = "";
 				if(!sheetName){
 					sheetKey = sid[rowIndex];
 				}else{
 					sheetKey = ret[sheetName];
 				}
	 			dataMap[sheetKey] = ret;
 			}
 		}
 		if(!fileName){
 			alertMsg.error("请设置模板文件名！");
 			return ;
 		}
 		var rowData = json2str(dataMap);
 		if(fileName!=null&&fileName!=""){
 			$.ajax({
 			    url: 'outputExcelFileExit?fileName='+fileName,
 			    type: 'post',
 			    dataType: 'json',
 			    async:false,
 			    error: function(data){
 			    	alertMsg.error("系统错误！");
 			    },
 			    success: function(data){
 			        if(data.message=='0'){
 			        	alertMsg.error("模板文件 "+fileName+" 不存在,请检查！");
 			        	return ;
 			        }else{
 			        	var urlC=jQuery('#${random}_${searchName}_gridTable').jqGrid('getGridParam','url');
 			     		var str = urlC.split("?");
 			    		var url = "${ctx}/outputExcelByRow?fileName="+fileName+"&"+str[1];
 			    		//url=encodeURI(url);
 			    		$.ajax({
 						    url: url,
 						    type: 'post',
 						    data: {rowData:rowData},
 						    dataType: 'json',
 						    async:false,
 						    error: function(data){
 								alertMsg.error("系统错误！");
 						    },
 						    success: function(data){
 								var downLoadFileName = data["message"];
 								var filePathAndName = downLoadFileName.split("@@");
 								var url = "${ctx}/downLoadExel?filePath="+filePathAndName[0]+"&fileName="+filePathAndName[1];
 					 			//url=encodeURI(url);
 					 			location.href=url; 
 						    }
 						});
 			        }
 			    }
 			});
 		}
	} 
 	
 	function popSearch(url,type,title,width,height){
		if(!url){return;}
		if(!type){ type = "tab"; }
		if(!title){ title = "查询"; }
		if(!width){ width = 700; }
		if(!height){ height = 600; }
 		if(type=="dialog"){
 			$.pdialog.open(url, 'popsearch', title, {mask:false,width:width,height:height});
 		}else if(type=="tab"){
 			navTab.openTab("popsearch", url, { title:title, fresh:true, data:{} });
 		} 
	}
 	
 	function addDataByForm(colNum,width,height,title){
 		var jzSystem = jzStatus${random}['addDataByForm'];
		if(jzSystem){
			alertMsg.error(jzSystem+"已结账！");
			return ;
		}
		if(!colNum){ colNum = 1;}
		if(!width){ width = 400;}
		if(!height){ height = 600;}
		if(!title){ title = "添加数据";}
		var url = "${ctx}/addDataByForm?searchName=${searchName}&entityIsNew=true&navTabId=${random}_${searchName}_gridTable&colNum="+colNum;
		$.pdialog.open(url, 'popsearch', title, {mask:false,width:width,height:height});
	}
	function editDataByForm(colNum,width,height,title){
		var jzSystem = jzStatus${random}['editDataByForm'];
		if(jzSystem){
			alertMsg.error(jzSystem+"已结账！");
			return ;
		}
		if(!colNum){ colNum = 1;}
		if(!width){ width = 400;}
		if(!height){ height = 600;}
		if(!title){ title = "修改数据";}
		var sid = jQuery("#${random}_${searchName}_gridTable").jqGrid('getGridParam','selarrrow');
		if(sid==null || sid.length ==0){
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return ;
		}else if(sid.length>1){
			alertMsg.error("<fmt:message key='list.selectMore'/>");
			return ;
		}
		var url = "${ctx}/addDataByForm?searchName=${searchName}&entityIsNew=false&navTabId=${random}_${searchName}_gridTable&colNum="+colNum+"&id="+sid;
		$.pdialog.open(url, 'popsearchedit', title, {mask:false,width:width,height:height});
	}
	
	function showHideCol(){
		setColShow('${random}_${searchName}_gridTable','${searchName}');
	}
 	
 	function chartDisplay(){
		alert('sdfa');
	}
	function graDisplay(gdParam,biaoTi,picName,biaoZhu,xyName){
			var grid = jQuery("#${random}_${searchName}_gridTable");
			 var colModel = grid.jqGrid("getGridParam",'colModel');  
			var id = grid.jqGrid('getGridParam','selrow');
			 if (id){
				var ret = grid.jqGrid('getRowData',id);
				var msg;
				for (var i = 0; i < colModel.length; i++){
					if(colModel[i].name!="cb" && colModel[i].name!="rn" )
						msg = msg + "-" + colModel[i].name +":"+ret[colModel[i].name];
				}
			} 
			//alert(msg);
		 var sid = jQuery("#${random}_${searchName}_gridTable").jqGrid('getGridParam','selarrrow');
		 if(sid.length!=1){
			alert("必须且只能选择一条数据，请重新选择!!!");
			return;
		} 
		/*  alert("start:   "+xyName+"   :end");
		 gdParam=escape(xyName);
		 alert("start:   "+xyName+"   :end"); */
		var url = "${ctx}/graphicsDisplayForm?gdParam="+gdParam+"&biaoTi="+biaoTi+"&dataMsg="+msg+"&picName="+picName+"&biaoZhu="+biaoZhu+"&xyName="+xyName;
		url=encodeURI(url);
		//alert('${ctx}');
		navTab.openTab("", url, { title:"图示", fresh:false, data:{} });
		//location.href=url; 
		
	}
    function confirmDelete(){
    	var jzSystem = jzStatus${random}['confirmDelete'];
		if(jzSystem){
			alertMsg.error(jzSystem+"已结账！");
			return ;
		}
        var sid = jQuery("#${random}_${searchName}_gridTable").jqGrid('getGridParam','selarrrow');
        var msgDialog = jQuery('#msgDialog');
        var confirmDialog = jQuery("#confirmDialog");
        if(sid==null || sid.length ==0){
		 
				/* msgDialog.dialog('option', 'buttons', { "<fmt:message key='dialog.ok'/>": function() {
						jQuery( this ).dialog( "close" );
					} });
			jQuery('div.#msgDialog').html("<fmt:message key='list.selectNone'/>");
			msgDialog.dialog('open'); */
			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
			}else{
				alertMsg.confirm("确认删除？", {
					okCall: function(){
					var url = "pub?searchName=${searchName}&id="+sid+"&actionName=delete";
					var jqxhr=jQuery.post( url, {dataType : "json"}).success(function(data){
				      	var status = data['jsonStatus'];
				    	if(status=="error"){
				    		alertMsg.error(data['jsonMessages']);
				    	}else{
				    		jQuery("#${random}_${searchName}_gridTable").trigger("reloadGrid");
				    		//editQueryRowCount=0;
				    		alertMsg.correct(data['jsonMessages']);
				    	}
					});
					}
				});
				
			
		}
	}
    
    function selectConfirmProcess(taskName,args,msg,returnSearch,waittingMsg,callback){
    	var jzSystem = jzStatus${random}['selectConfirmProcess'];
		if(jzSystem){
			alertMsg.error(jzSystem+"已结账！");
			return ;
		}
        var sid = jQuery("#${random}_${searchName}_gridTable").jqGrid('getGridParam','selarrrow');
        var msgDialog = jQuery('#msgDialog');
        var confirmDialog = jQuery("#confirmDialog");
        if(sid==null || sid.length ==0){

			alertMsg.error("<fmt:message key='list.selectNone'/>");
			return;
        }else if(sid.length>200){
			alertMsg.error("您选择的记录数大于最大限制200！");
			return;
		}else{
				
				var searchItemValue = "";
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
					searchItemValue = searchItemValue<c:forEach items="${searchItems}" var="item">+"|${item.htmlField}="+${item.htmlField}</c:forEach>;
				</c:if>
				searchItemValue = "&itemValue="+searchItemValue;
				var url = "${ctx}/pub?searchName=${searchName}&id="+sid+"&actionName=process"+searchItemValue+"&taskName="+taskName+"&snapCode="+timestamp${random}+args+"&callback="+callback;
				var executCallback = function(){
					returnSearchFun(returnSearch);
				};
				url = encodeURI(url);
				executeSp(null,"${random}_${searchName}_gridTable",msg,waittingMsg,url,executCallback);
				/*alertMsg.confirm(msg, {
					okCall: function(){
					var url = "${ctx}/pub?searchName=${searchName}&id="+sid+"&actionName=process&taskName="+taskName+args;
					executeSp(null,"#${random}_${searchName}_gridTable","selectConfirmProcess",url);
					var jqxhr=jQuery.post( url, {dataType : "json"}).success(function(data){
				      	var status = data['jsonStatus'];
				    	if(status=="error"){
				    		alertMsg.error(data['jsonMessages']);
				    	}else{
				    		alertMsg.correct(data['jsonMessages']);
				    		jqxhr.complete(function(){
								jQuery("#${random}_${searchName}_gridTable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
								});
				    		//处理成功后进入指定Search
				    		returnSearchFun(returnSearch);
				    	}
					});
					}
				});			*/	

		}
	}
    function noSelectConfirmProcess(taskName,args,msg,returnSearch,waittingMsg,callback){
    	var jzSystem = jzStatus${random}['noSelectConfirmProcess'];
		if(jzSystem){
			alertMsg.error(jzSystem+"已结账！");
			return ;
		}
    	var searchItemValue = "";
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
			searchItemValue = searchItemValue<c:forEach items="${searchItems}" var="item">+"|${item.htmlField}="+${item.htmlField}</c:forEach>;
		</c:if>
		searchItemValue = "&itemValue="+searchItemValue;
        //var sid = jQuery("#${random}_${searchName}_gridTable").jqGrid('getGridParam','selarrrow');
        var url = "${ctx}/pub?searchName=${searchName}&actionName=process"+searchItemValue+"&taskName="+taskName+"&snapCode="+timestamp${random}+args+"&callback="+callback;
		var executCallback = function(){
					returnSearchFun(returnSearch);
				};
		url = encodeURI(url);
		executeSp(null,"${random}_${searchName}_gridTable",msg,waittingMsg,url,executCallback);
       /* alertMsg.confirm(msg, {
			okCall: function(){
			var url = "${ctx}/pub?searchName=${searchName}&id="+sid+"&actionName=process&taskName="+taskName+args;
			executeSp(null,"#${random}_${searchName}_gridTable","noSelectConfirmProcess",url);
				var jqxhr=jQuery.post( url, {dataType : "json"}).success(function(data){
		      	var status = data['jsonStatus'];
		    	if(status=="error"){
		    		alertMsg.error(data['jsonMessages']);
		    	}else{
	    			alertMsg.correct(data['jsonMessages']);
		    		jqxhr.complete(function(){
						jQuery("#${random}_${searchName}_gridTable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
					});P@
		    		//处理成功后进入指定Search
		    		returnSearchFun(returnSearch);
		    	}
			}); 
			}
		});		*/
        
	}
    
function conditionConfirmProcess(taskName,args,businessTypeCode,msg,returnSearch,waittingMsg,callback){
		var jzSystem = jzStatus${random}['conditionConfirmProcess'];
		if(jzSystem){
			alertMsg.error(jzSystem+"已结账！");
			return ;
		}
    	var searchItemValue = "",searchItemValueUrl = "";
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
			searchItemValue = searchItemValue<c:forEach items="${searchItems}" var="item">+"|${item.htmlField}="+${item.htmlField}</c:forEach>;
			searchItemValueUrl = searchItemValueUrl<c:forEach items="${searchItems}" var="item">+"&${item.htmlField}="+${item.htmlField}</c:forEach>;
		</c:if>
		searchItemValue = "&itemValue="+searchItemValue+searchItemValueUrl;
        //var sid = jQuery("#${random}_${searchName}_gridTable").jqGrid('getGridParam','selarrrow');
        var url = "${ctx}/pub?searchName=${searchName}&actionName=processByCondition"+searchItemValue+"&taskName="+taskName+"&businessTypeCode="+businessTypeCode+"&snapCode="+timestamp${random}+args+"&callback="+callback;
		var executCallback = function(){
					returnSearchFun(returnSearch);
				};
		url = encodeURI(url);
		executeSp(null,"${random}_${searchName}_gridTable",msg,waittingMsg,url,executCallback);
       /* alertMsg.confirm(msg, {
			okCall: function(){
			var url = "${ctx}/pub?searchName=${searchName}&id="+sid+"&actionName=process&taskName="+taskName+args;
			executeSp(null,"#${random}_${searchName}_gridTable","noSelectConfirmProcess",url);
				var jqxhr=jQuery.post( url, {dataType : "json"}).success(function(data){
		      	var status = data['jsonStatus'];
		    	if(status=="error"){
		    		alertMsg.error(data['jsonMessages']);
		    	}else{
	    			alertMsg.correct(data['jsonMessages']);
		    		jqxhr.complete(function(){
						jQuery("#${random}_${searchName}_gridTable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
					});P@
		    		//处理成功后进入指定Search
		    		returnSearchFun(returnSearch);
		    	}
			}); 
			}
		});		*/
        
	}
    
    function returnSearchFun(returnSearch){
		if(returnSearch!=null&&returnSearch!=""&&returnSearch!="undefined"){
			//if(data['jsonMessages']=='处理成功。'){
				var winTitle = '<fmt:message key="searchPreview.title"/>';
		        navTab.openTab("",'query?searchName='+returnSearch, { title:winTitle, fresh:true, data:{} });
			//}
		}
	}
    function zhuanqu(url){
    	var paramsArr = url.split("&");
    	var searchName = "",openType = "";
    	var title = "";
    	for(var i=0;i<paramsArr.length;i++){
    		if(paramsArr[i].indexOf("SearchName")!=-1){
    			searchName = paramsArr[i].split("=")[1];
    		}
    		if(paramsArr[i].indexOf("openType")!=-1){
    			openType = paramsArr[i].split("=")[1];
    		}
    	}
    	if(searchName!=null&&searchName!=""){
    		var entityName = "Search";
    		var searchItem = "searchName";
    		var searchValue = searchName;
    		$.ajax({
			    url: 'searchDictionary?entityName='+entityName+"&searchItem="+searchItem+"&searchValue="+searchValue,
			    type: 'post',
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			    },
			    success: function(data){
			        // do something with xml
			        title = data.message;
			        if(openType=="dialog"){
		    			$.pdialog.open(url, "${random}_${searchName}showQueryAction", title,{width:800,height:600});
			        }else{
			        	navTab.openTab("", url, { title:title, fresh:true, data:{} });
		    		}
			    }
			});
    	}else{
    		if(openType=="dialog"){
    			$.pdialog.open(url, "${random}_${searchName}showQueryAction", "明细",{width:800,height:600});
    		}else{
    			navTab.openTab("${random}_${searchName}showQueryAction", url, { title:"明细", fresh:true, data:{} });
    		}
    	}
    }
    
    function layoutResize(jLayout){
    	jLayout.resizeAll();
    }
    
    function openAction(url,title,type){
    	
    	if(!url){
    		return ;
    	}
    	if(!title){
    		title = "新标签";
    	}
    	var urlArr = url.split("?");
    	var realUrl = urlArr[0];
    	if(urlArr.length==2){
    		var sid = jQuery("#${random}_${searchName}_gridTable").jqGrid('getGridParam','selarrrow');
    		if(sid==null || sid.length ==0){
    			alertMsg.error("<fmt:message key='list.selectNone'/>");
    			return;
    		}else{
    			realUrl += "a=1";
    			var rowData = jQuery("#${random}_${searchName}_gridTable").jqGrid('getRowData',sid);
    			var paramsArr = urlArr[1].split("&");
    			for(var i=0;i<paramsArr.length;i++){
    	    		var paramArr = paramsArr[i].split("=");
    	    		var paramName = paramArr[0];
    	    		var dataFrom = "system";
    	    		var paramValue = "";
    	    		if(paramArr.length==2){
    	    			dataFrom = paramArr[1];
    	    		}
    	    		if(dataFrom=='grid'){
    	    			paramValue = rowData[paramName];
	    			}
    	    		realUrl += "&"+paramName+"="+paramValue;
    	    	}
    		}
    	}
    	
		if(type=='tab'){
    		navTab.openTab("", realUrl, { title:title, fresh:true, data:{} });
    	}else{
    		$.pdialog.open(realUrl, "txxs", title,{width:800,height:600});
    	}
    	
    }
</script>
</head>

<div class="page">
	<div class="pageContent">
		<div id="${random}_${searchName}_container">
			<div id="${random}_${searchName}_layout-center"
				class="pane ui-layout-center" style="padding: 2px">
				<div id="${random}_${searchName}_searchDiv" class="pageHeader">
					<form onsubmit="return navTabSearch(this);" action="" method="post">
						<div class="searchBar">
							<div class="searchContent">
								<c:if test="${searchItems!=null && fn:length(searchItems)>0}">
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
								</c:if>
								<div class="buttonActive" style="float:right">
											<div class="buttonContent">
												<button type="button" id="${random}_${searchName}_reload">
													<fmt:message key='button.search' />
												</button>
											</div>
										</div>
							</div>
							
							<%-- <div class="subBar">
								<ul>
									<li><div class="buttonActive">
											<div class="buttonContent">
												<button type="button" onclick="gridReload()">
													<fmt:message key='button.search' />
												</button>
											</div>
											
										</div>
								</li>
									<!-- <li><div class="buttonActive">
											<div class="buttonContent">
												<button type="button" onclick="chartGridReload()">
													图形显示
												</button>
											</div>
										</div>
								</li> -->
								</ul>
						sty	</div> --%>
						</div>
					</form>
				</div>


				<div style="z-index: 1">
					<sj:dialog id="msgDialog"
						buttons="{'%{getText('dialog.ok')}':function() {jQuery( this ).dialog( 'close' ); }}"
						autoOpen="false" modal="true"
						title="%{getText('messageDialog.title')}" />
					<sj:dialog id="confirmDialog"
						buttons="{'%{getText('dialog.confirm')}':function() {jQuery( this ).dialog( 'close' ); },'%{getText('dialog.cancel')}':function() {jQuery( this ).dialog( 'close' ); }}"
						autoOpen="false" modal="true"
						title="%{getText('messageDialog.title')}" />
					<sj:dialog id="testDialog"
						buttons="{'OK':function() {jQuery( this ).dialog( 'close' ); }}"
						autoOpen="false" modal="true"
						title="%{getText('messageDialog.title')}" />


					<div class="panelBar">
						<ul class="toolBar">
						<c:forEach items="${searchUrls}" var="op">
							<c:if test="${op.visible==true}">
							<li><a id="${op.searchUrlId}"
								href="javaScript:${op.realUrl}"><span style="background:url('${ctx}/styles/themes/rzlt_theme/ihos_images/icon_button/${op.icon}') ;background-repeat: no-repeat;background-position:0 4px;">${op.title} </span> </a>
							</li>
							</c:if>
						</c:forEach>
						<c:if test="${editOptionsNum>0}">
						<li><a id="${random}_${searchName}_fullGridEdit" class="edit" href="javaScript:"><span>编辑</span></a></li>
						<li><a id="${random}_${searchName}_restoreRowGrid" class="canceleditbutton" href="javaScript:"><span>取消编辑</span></a></li>
						<li><a id="${random}_${searchName}_saveRowGrid" class="savebutton" href="javaScript:"><span>保存</span></a></li>
						</c:if>
						</ul>
					</div>
					<div id="${random}_${searchName}_searchDivSimple" align="right"
						style="margin-top: -26px">
						<button onclick="gridReloadSimple()" class="btn btn-info">
							<fmt:message key='button.search' />
						</button>
						<button id="${random}_${searchName}_higherButton"
							class="btn btn-info">
							<fmt:message key='button.higher' />
						</button>
					</div>

					<div id="${random}_${searchName}_gridTable_div"
						style="margin-left: 2px; margin-top: 2px; overflow: hidden;text-align:center""
						buttonBar="width:700;height:400">

						<table id="${random}_${searchName}_gridTable"></table>
						<div id="${random}_${searchName}_gridPager"></div>
					</div>
					<div class="panelBar">
						<div class="pages">
							<span>显示</span> <select
								id="${random}_${searchName}_gridTable_numPerPage">
								<option value="20">20</option>
								<option value="50">50</option>
								<option value="100">100</option>
								<option value="200">200</option>
							</select> <span>条，共<label
								id="${random}_${searchName}_gridTable_totals"></label>条
							</span>
						</div>

						<div id="${random}_${searchName}_gridTable_pagination"
							class="pagination" targetType="navTab" totalCount="200"
							numPerPage="20" pageNumShown="10" currentPage="1"></div>

					</div>
				</div>
			</div>

		</div>
		<div id="searchGridPropertyDiv" style="display:none">
			<div class="page" style="height:100%">
				<div class="pageContent" style="height:100%">
					<br/>
					<span style="margin:20px">searchName : ${searchName}</span>
				</div>
			</div>
		</div>
	</div>
</div>
