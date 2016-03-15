<!DOCTYPE html>
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
<script>
	jQuery(document).ready(function() {
		jQuery('#trainRecord_savelink').click(function() {
			var curInputObj;
			var jsonStr="";
			var obj = jQuery("#trainRecordDetail_gridtable").jqGrid("getRowData");
// 			var obj = jQuery("#statisticsTarget_gridtable>tbody>tr");
			    jQuery(obj).each(function(){
			    	jsonStr=jsonStr+"{";
			    	if(this.id.indexOf("recordDetail") >=0){
			    		jsonStr=jsonStr+'"id":"",'; 
			    	}else{
			    		jsonStr=jsonStr+'"id":"'+this.id+'",';
			    	}
			    	var row = jQuery("#trainRecordDetail_gridtable").jqGrid('getRowData',this.id);
			    	jsonStr=jsonStr+'"trainCourse":"'+row['trainCourse.id']+'",';
			    	curInputObj=jQuery("#"+this.id).find("input[name=trainDate]");
			    	jsonStr+='"trainDate"'+":"+'"'+curInputObj.val()+'",';
			    	curInputObj=jQuery("#"+this.id).find("input[name=trainHour]");
			    	jsonStr+='"trainHour"'+":"+'"'+curInputObj.val()+'",';
			    	curInputObj=jQuery("#"+this.id).find("input[name=personIds]");
			    	jsonStr+='"personIds"'+":"+'"'+curInputObj.val()+'",';
			    	curInputObj=jQuery("#"+this.id).find("input[name=personNames]");
			    	jsonStr+='"personNames"'+":"+'"'+curInputObj.val()+'"},';
			    });
			    if(jsonStr.length>1){
			    	 jsonStr=jsonStr.substring(0,jsonStr.length-1);
			    }
			jsonStr="{"+'"'+"edit"+'"'+":[" +jsonStr+"]}";
			jQuery("#trainRecord_gridAllDatas").val(jsonStr);			
			jQuery("#trainRecordForm").attr("action","saveTrainRecord?popup=true&navTabId="+'${navTabId}&entityIsNew=${entityIsNew}');
			jQuery("#trainRecordForm").submit();
		});
		if('${entityIsNew}'=="true"){
		jQuery("#trainRecord_trainNeed").treeselect({
		   	dataType:"sql",
		   	optType:"single",
		   	sql:"SELECT id,name FROM hr_train_class where state='2' ORDER BY code",
		   	exceptnullparent:false,
		   	lazy:false,
		   	callback : {
		   		afterClick : function() { 
		   			var className=jQuery("#trainRecord_trainNeed").val();
		   			var classId=jQuery("#trainRecord_trainNeed_id").val();
		   		 	jQuery.ajax({
				        url: 'trainNeedGridList?filter_EQS_id='+classId,
				        data: {},
				        type: 'post',
				        dataType: 'json',
				        async:false,
				        error: function(data){
				        },
				        success: function(data){ 
				     	   if(data.trainNeeds){
				     		   jQuery("#trainRecord_goal").val(data.trainNeeds[0]["goal"]);
				     		   jQuery("#trainRecord_content").val(data.trainNeeds[0]["content"]);
				     	   }
				     	 }
				    });
		   		 
		   			var courseName=className+"记录";
		   			jQuery("#trainRecord_name").val(courseName);
		   			var obj = jQuery("#trainRecordDetail_gridtable").jqGrid("getRowData");
					jQuery(obj).each(function(){
						jQuery("#trainRecordDetail_gridtable").delRowData(this.id);	
					});
			 }
		   	}
		});
		}
		var url="";
		if("${trainRecord.id}"){
			url="trainRecordDetailGridList?filter_EQS_trainRecord.id="+"${trainRecord.id}";
		}else{
			url="trainRecordDetailGridList?filter_EQS_trainRecord.id="+"1";
		}
		 var trainRecordDetailGridIdString="#trainRecordDetail_gridtable";
		 var trainRecordDetailGrid = jQuery(trainRecordDetailGridIdString);
		 trainRecordDetailGrid.jqGrid({
		    	url : url,
		    	editurl:"trainRecordDetailGridEdit",
				datatype : "json",
				mtype : "GET",
		        colModel:[
		{name:'id',index:'id',align:'center',label : '<s:text name="trainRecordDetail.id" />',hidden:true,key:true},
		{name:'trainCourse.id',index:'trainCourse.id',width:100,align:'left',label : '<s:text name="trainRecordDetail.id" />',hidden:true},
		{name:'trainCourse.name',index:'trainCourse.name',width:80,align:'left',label : '<s:text name="trainRecordDetail.courseName" />',hidden:false,sortable:false},
		{name:'trainDate',index:'trainDate',align:'center',width:70,label : '<s:text name="trainRecordDetail.trainDate" />',hidden:false,sortable:false,
			formatter:function(cellvalue, options, rowObject) {
				if("${oper}"=="view"){
					if(cellvalue.length > 10){
						cellvalue=cellvalue.substring(0, 10);
					}
					return cellvalue;
				}
				var editvalue="";
				if(cellvalue){
					if(cellvalue.length > 10){
						cellvalue=cellvalue.substring(0, 10);
					}
					editvalue = '<input name="trainDate" onclick="WdatePicker({skin:\'ext\',dateFmt:\'yyyy-MM-dd\'})" type="text" value="'+cellvalue+'"></input>';
				}else{
					editvalue = '<input name="trainDate" onclick="WdatePicker({skin:\'ext\',dateFmt:\'yyyy-MM-dd\'})" type="text"></input>';
				}
				return editvalue;
			}
		},
		{name:'trainHour',index:'trainHour',align:'center',width:60,label : '<s:text name="trainRecordDetail.trainHour" />',hidden:false,sortable:false,
			formatter:function(cellvalue, options, rowObject) {
				if("${oper}"=="view"){
					return cellvalue;
				}
				var editvalue="";
				var mydate = new Date();
				var inputId="trainRecord"+mydate.getTime();
				
				if(cellvalue){
						editvalue = '<input id="'+inputId+'" name="trainHour" type="text" value="'+cellvalue +'" onblur="digitCheckForRecord(this)"></input>';
					}else{
						editvalue = '<input id="'+inputId+'" name="trainHour" type="text" onblur="digitCheckForRecord(this)"></input>';
					}
				return editvalue;
			}
		},
		{name:'personIds',index:'personIds',align:'center',label : '<s:text name="trainRecordDetail.personIds" />',hidden:true},
		{name:'personNames',index:'personNames',align:'center',width:220,label : '<s:text name="trainRecordDetail.personNames" />',hidden:false,sortable:false,
			formatter:function(cellvalue, options, rowObject) {
				if("${oper}"=="view"){
					return cellvalue;
				}
				var editvalue="";
				var personIds=rowObject['personIds'];
				var id=rowObject['id'];
				var textId=id+"_person";
				var hiddenId=id+"_person_id";
// 				alert(rowObject['personIds']);
				if(cellvalue){
					editvalue='<input  name="personNames"  maxlength="8000" style="width:210px" type="text" id="'+textId+'" value="'+cellvalue+'" readonly="readonly" onfocus="trainRecordDetailHrPersonTreeLoad(this)">';
					editvalue+='<input  name="personIds"  maxlength="8000" type="hidden" id="'+hiddenId+'" value="'+personIds+'">';
				}else{
					editvalue='<input  name="personNames"  maxlength="8000" style="width:210px" type="text" id="'+textId+'"  readonly="readonly" onfocus="trainRecordDetailHrPersonTreeLoad(this)">';
					editvalue+='<input  name="personIds"  maxlength="8000" type="hidden" id="'+hiddenId+'">';
				}
				return editvalue;
			}} 
		],
        jsonReader : {
			root : "trainRecordDetails", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'id',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="statisticsTargetList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: true,
		multiboxonly:true,
		shrinkToFit:false,
		autowidth:false,
        onSelectRow: function(rowid) {
       
       	},
		 gridComplete:function(){
           var dataTest = {"id":"trainRecordDetail_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
//       	   makepager("trainRecordDetail_gridtable");
       	} 
    });
    jQuery(trainRecordDetailGrid).jqGrid('bindKeys');
    
    jQuery("#trainRecordDetail_gridtable_add_custom").unbind( 'click' ).bind("click",function(){
    	var needId=jQuery("#trainRecord_trainNeed_id").val();
    	if(!needId){
    		alertMsg.error("请先选择班。");
			return;
    	}
    	
    	//GRID  课程id
    	var id="";
    	var obj = jQuery("#trainRecordDetail_gridtable").jqGrid("getRowData");
// 			var obj = jQuery("#statisticsTarget_gridtable>tbody>tr");
			    jQuery(obj).each(function(){
			    	var row = jQuery("#trainRecordDetail_gridtable").jqGrid('getRowData',this.id);
			    	id+=row['trainCourse.id']+",";
			    });
		 if(id.Length>2){
			 id=id.Substring(0,id.Length-1); 
		 }
    	//GRID kecheng
    	
		var url = "trainCourseCheckList?popup=true&navTabId=trainRecordDetail_gridtable";
		url=url+ "&needId="+needId+"&id="+id;
		var winTitle='<s:text name="trainCourseList.title"/>';
		$.pdialog.open(url,'addtrainCourse',winTitle, {mask:true,width : 500,height : 450});
		stopPropagation();
	}); 
    jQuery("#trainRecordDetail_gridtable_del_custom").unbind( 'click' ).bind("click",function(){
		var sid = jQuery("#trainRecordDetail_gridtable").jqGrid('getGridParam','selarrrow');
        if(sid==null || sid.length ==0){
        	alertMsg.error("请选择记录。");
			return;
			}else {
				alertMsg.confirm("确认删除？", {
					okCall : function() {
				for(var i = sid.length;i > 0; i--){
					var rowId = sid[i-1];
					jQuery("#trainRecordDetail_gridtable").delRowData(rowId);
					}
				}
				});
			}
		});
    if("${oper}"=="view"){
		readOnlyForm("trainRecordForm");
		jQuery("#trainRecord_buttonActive").css("display","none");
		jQuery("#trainRecordDetail_gridtable_add_custom").css("display","none");
		jQuery("#trainRecordDetail_gridtable_del_custom").css("display","none");
		jQuery("#trainRecord_add_buttonActive").css("display","none");
		jQuery("#trainRecord_del_buttonActive").css("display","none");
// 		var obj = jQuery("#trainRecordDetail_gridtable").jqGrid("getRowData");
// 		    jQuery(obj).each(function(){
// 		    	jQuery("#"+this.id).find("[type='text']").css("readonly","readonly");
// 		    });
	}else{
		jQuery("#trainRecord_trainNeed").addClass("required");
		jQuery("#trainRecord_name").addClass("required");
	}
	});
	function trainRecordDetailHrPersonTreeLoad(obj){
		var id=obj.id;
		var sql="select personId id,name,dept_id parent,0 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/person.png' icon,personCode orderCol from v_hr_person_current where disabled=0 and deleted=0 ";
		sql=sql+" union select deptId id,name,ISNULL(parentDept_id,orgCode) parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/dept.gif' icon,deptCode orderCol  from v_hr_department_current where disabled=0 and deleted=0 "
		sql=sql+" union select orgCode id,orgname name,ISNULL(parentOrgCode,'') parent,1 isParent,'/scripts/zTree/css/zTreeStyle/img/diy/1_close.png' icon,orgCode orderCol from v_hr_org_current where disabled=0 ";
		sql += " ORDER BY orderCol ";
		jQuery("#"+id).treeselect({
			optType:"multi",
			dataType:'sql',
			sql:sql,
			exceptnullparent:true,
			lazy:false,
			selectParent:false
		});
	}
	
	function digitCheckForRecord(obj){
		var value=obj.value;
		var id=obj.id;
		if(value&&isNaN(value)){
			jQuery("#"+id).val("0");
			alertMsg.error("请输入数字");
		}
	}
</script>
</head>

<div class="page">
	<div class="pageContent">
		<form id="trainRecordForm" method="post"	action="saveTrainRecord?popup=true&navTabId=${navTabId}&entityIsNew=${entityIsNew}" class="pageForm required-validate"	onsubmit="return validateCallback(this,formCallBack);">
			<div class="pageFormContent" layoutH="56">
				<div>
				<s:hidden key="trainRecord.id"/>
				<s:hidden key="trainRecord.checker.personId"/>
				<s:hidden key="trainRecord.checkDate"/>
				<s:hidden key="trainRecord.doneDate"/>
				<s:hidden key="trainRecord.doner.personId"/>
				<s:hidden key="trainRecord.makeDate"/>
				<s:hidden key="trainRecord.maker.personId"/>
				<s:hidden key="trainRecord.state"/>
				<s:hidden key="trainRecord.code"/>
				<s:hidden name="gridAllDatas" id="trainRecord_gridAllDatas"/>
				<s:hidden name="trainRecord.yearMonth"/>
				</div>
				<div class="unit">
				<s:if test="%{!entityIsNew}">
				<label><s:text name='trainRecord.className' />:</label>
				<input type="text" id="trainRecord_trainNeed"  value="${trainRecord.trainNeed.name}" readonly="readonly"/>
				<input type="hidden" id="trainRecord_trainNeed_id" name="trainRecord.trainNeed.id" value="${trainRecord.trainNeed.id}"/>
				</s:if>
				<s:else>
				<label><s:text name='trainRecord.className' />:</label>
				<input type="text" id="trainRecord_trainNeed"  value="${trainRecord.trainNeed.name}" />
				<input type="hidden" id="trainRecord_trainNeed_id" name="trainRecord.trainNeed.id" value="${trainRecord.trainNeed.id}"/>
				</s:else>
				<s:textfield id="trainRecord_name" key="trainRecord.name" name="trainRecord.name"  maxlength="20"/>
				</div>
				<div class="unit">
				<s:textfield id="trainRecord_goal" key="trainRecord.goal" name="trainRecord.goal" cssClass="" maxlength="30"/>
				</div>
				<div class="unit">
					<s:textarea id="trainRecord_content" key="trainRecord.content" maxlength="200" rows="4" cols="54" cssClass="input-xlarge " />
				</div>
					<s:if test="%{oper=='view'}">
						<div class="hrDocFoot">
							<span>
								<label><s:text name="trainRecord.maker"></s:text>:</label>
								<input type="text" value="${trainRecord.maker.name}"></input>
							</span>
							<span>
								<label><s:text name="trainRecord.checker"></s:text>:</label>
								<input type="text" value="${trainRecord.checker.name}"></input>
							</span>
							<span>
								<label><s:text name="trainRecord.doner"></s:text>:</label>
								<input type="text" value="${trainRecord.doner.name}"></input>
							</span>
						</div>
				</s:if>
				<div class="unit">
				<div id="trainRecordDetail_gridtable_div" layoutH="190"
					  class="grid-wrapdiv" class="unitBox" style="position:absolute;left:75px;width:510px"
						buttonBar="optId:id;width:500;height:400">
					<div id="load_trainRecordDetail_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
 						<table id="trainRecordDetail_gridtable"></table>
				</div>
<!-- 				<div style="position:absolute;left:580px"> -->
<!-- 					<br><br> -->
<%-- 					<button type="button" id="trainRecordDetail_gridtable_add_custom"><s:text name="button.add" /></button> --%>
<!-- 					<br><br> -->
<%-- 					<button type="button" id="trainRecordDetail_gridtable_del_custom"><s:text name="button.delete" /></button> --%>
<!-- 				</div>							 -->
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive" id="trainRecord_add_buttonActive">
							<div class="buttonContent">
									<button type="button" id="trainRecordDetail_gridtable_add_custom">选择课程</button>
							</div>
						</div>
					</li>
					<li><div class="buttonActive" id="trainRecord_del_buttonActive">
							<div class="buttonContent">
									<button type="button" id="trainRecordDetail_gridtable_del_custom">删除记录</button>
							</div>
						</div>
					</li>
					<li><div class="buttonActive" id="trainRecord_buttonActive">
							<div class="buttonContent">
								<button type="Button" id="trainRecord_savelink"><s:text name="button.save" /></button>
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





