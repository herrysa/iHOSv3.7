
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	var periodSubjectLayout;
	var periodSubjectGridIdString="#periodSubject_gridtable";
	
	jQuery(document).ready(function() { 
		var periodSubFullSize = jQuery("#container").innerHeight()-60;
		jQuery("#periodSubject_container").css("height",periodSubFullSize);
		$('#periodSubject_container').layout({ 
			applyDefaultStyles: false ,
			west:{
				size:200,
				resizable :true,
				closable:false
			},
			spacing_open:5,//边框的间隙  
			spacing_closed:5,//关闭时边框的间隙 
			resizable :true,
			resizerClass :"ui-layout-resizer-blue",
			slidable :true,
			resizerDragOpacity :1, 
			resizerTip:"可调整大小",//鼠标移到边框时，提示语
			onresize_end : function(paneName,element,state,options,layoutName){
				//zzhJsTest.debug("resize:"+paneName);
				if("center" == paneName){
					gridResize(null,null,"periodSubject_gridtable","single");
				}
			}
		});
    refreshPeriodPlanTree();
    jQuery('#periodSubject_gridtable_save').unbind('click');
	jQuery("#periodMonthDetails").load("periodMonthList");
	jQuery("#periodMonthDetails").css('visibility','hidden');
	$('#periodSubject_periodYear').attr('disabled','true').attr('readOnly','readOnly');
    $('#periodSubject_periodNum').attr('disabled','true').attr('readOnly','readOnly');
    $('#periodSubject_beginDate').attr('disabled','true').attr('readOnly','readOnly');
    $('#periodSubject_endDate').attr('disabled','true').attr('readOnly','readOnly');
    $('#periodSubject_periodNum').blur(calculatePeriodMonth);
    $('#periodSubject_beginDate').blur(calculatePeriodMonth);
    $('#periodSubject_endDate').blur(calculatePeriodMonth);
    $('#periodSubject_periodYear').blur(function(){
    	var periodYearCode = $('#periodSubject_periodYear').val();
    	if(periodYearCode.length!=4){
    		alertMsg.error("请输入四位有效的年份！");
    		return;
    	}
		var periodPlanId = $('#periodSubject_planId').val();
		$.ajax({
		    url:"${ctx}/checkPeriodYearCode",
		    type: 'post',
		    data:{periodYearCode:periodYearCode,periodPlanId:periodPlanId},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		        
		    },
		    success: function(data){
		        if(data.exists){
		        	 alertMsg.error("该方案下已存在该会计年！");
		        	 $('#periodSubject_periodYear').val("");
		        }
		    }
		});
    });
  	});
	
	var selectId = "";
    var tableId = "periodSubject_gridtable";
    var saveUrl = "editPeriodSubject";
    function addPeriodSubButton(){
    	jQuery('#periodSubject_gridtable_save').unbind('click').bind('click',savePeriodSubject);
    	$('#periodSubject_periodYear').removeAttr('disabled').removeAttr('readOnly').focus();
        $('#periodSubject_periodNum').removeAttr('disabled').removeAttr('readOnly');
        $('#periodSubject_beginDate').removeAttr('disabled').removeAttr('readOnly');
        $('#periodSubject_endDate').removeAttr('disabled').removeAttr('readOnly');
        $('#periodSubject_status').val('add');
        jQuery("#periodMonthDetails").css('visibility','visible');
   		if(selectId==-1 || selectId == ""){
   			selectId="";
   			alertMsg.error("请选择一个期间方案");
   			return;
   		}
   		var url = saveUrl+"?popup=true&planId="+selectId+"&navTabId="+tableId+"&periodSubject_status=add";
   		jQuery.ajax({
		    url: url,
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    },
		    success: function(data){
		        $('#periodSubject_periodYear').val(data.periodYear);
		        $('#periodSubject_periodId').val(data.periodId);
		        $('#periodSubject_periodNum').val(data.periodNum);
		        $('#periodSubject_beginDate').val(data.beginDate);
		        $('#periodSubject_endDate').val(data.endDate);
		        $('#periodSubject_planId').val(data.planId);
		        calculatePeriodMonth();
		    }
		});
    }
    
    function editPeriodSubButton(){
    	jQuery('#periodSubject_gridtable_save').unbind('click').bind('click',savePeriodSubject);
    	$('#periodSubject_periodYear').removeAttr('disabled').removeAttr('readOnly').focus();
        $('#periodSubject_periodNum').removeAttr('disabled').removeAttr('readOnly');
        $('#periodSubject_beginDate').removeAttr('disabled').removeAttr('readOnly');
        $('#periodSubject_endDate').removeAttr('disabled').removeAttr('readOnly');
        $('#periodSubject_status').val('edit');
        monthEditable();
    }
    
    function calculatePeriodMonth(){
    	  var num = $('#periodSubject_periodNum').val();
	      var beginDate = $('#periodSubject_beginDate').val();
	      var endDate = $('#periodSubject_endDate').val();
	      var periodYear = $('#periodSubject_periodYear').val();
	      if(beginDate == ''||num==''||endDate == ''){
	    	  return ;
	      }
	      var start=new Date(beginDate.replace("-", "/").replace("-", "/"));  
	      var end=new Date(endDate.replace("-", "/").replace("-", "/"));  
	      var diffMonth = (end.getFullYear()-start.getFullYear())*12+end.getMonth()-start.getMonth();
	      
	      if(diffMonth < parseInt(num) ){
	    	  if(parseInt(num) - diffMonth > 1){
	    		  if($(this).is($('#periodSubject_periodNum'))){
	    			  $(this).val(diffMonth);
	    			  calculatePeriodMonth();
	    		  } else {
		    		  $(this).val('');
	    		  }
	    		  alertMsg.error("您输入的期间月个数与输入日期不符,请重新输入");
	    		  return;
	    	  } else {
	    		  var diffDay = end.getDate()-start.getDate();
	    		  if(diffDay < 0){
	    			  if($(this)==$('#periodSubject_periodNum')){
		    			  $(this).val(diffMonth);
		    			  calculatePeriodMonth();
		    		  } else {
			    		  $(this).val('');
		    		  }
	    			  alertMsg.error("您输入的期间月个数与输入日期不符,请重新输入");
		    		  return;
	    		  }
	    	  }
	      }
	      if(end<start){  
	    	 alertMsg.error("您输入的结束日期不能小于起始日期");
	         return false;  
	      } 
	      var urlString = "calculatePeriodMonth?num="+num+"&beginDate="+beginDate+"&endDate="+endDate+"&periodYear="+periodYear;
	      jQuery("#periodMonth_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
    }
    
    function deletePreiodSubButton(){
    	alertMsg.confirm("确认删除？", {
			okCall: function(){
				var periodId =  $('#periodSubject_periodId').val();
		    	jQuery.ajax({
				    url: 'delPeriodSubject?periodSubject_periodId='+periodId,
				    type: 'post',
				    dataType: 'json',
				    async:false,
				    error: function(data){
				    	alertMsg.error(data.message);
				    },
				    success: function(data){
				    	alertMsg.correct(data.message);
				    	$('#periodSubject_periodYear').val('');
				        $('#periodSubject_periodId').val('');
				        $('#periodSubject_periodNum').val('');
				        $('#periodSubject_beginDate').val('');
				        $('#periodSubject_endDate').val('');
				        $('#periodSubject_planId').val('');
				        $('#periodSubject_periodYear').attr('disabled','true').attr('readOnly','readOnly');
				        $('#periodSubject_periodNum').attr('disabled','true').attr('readOnly','readOnly');
				        $('#periodSubject_beginDate').attr('disabled','true').attr('readOnly','readOnly');
				        $('#periodSubject_endDate').attr('disabled','true').attr('readOnly','readOnly');
				        //jQuery("#periodMonthDetails").load("periodMonthList");
				    	jQuery("#periodMonthDetails").css('visibility','hidden');
				    	refreshPeriodPlanTree();
				    }
				});
			}
		});
    	
    }
    
	
	function planTreeReload(e,treeId, treeNode){
		var treeId = treeNode.id;
		if(treeId && treeId != "-1"){
			var id = treeId;
			var status = treeNode.subSysTem;
			if(status =='plan'){
				selectId = id;
				var periodId =  $('#periodSubject_periodId').val('');
				jQuery("#periodSubject_gridtable_addlocal").unbind('click').bind('click',addPeriodSubButton);
				jQuery("#periodSubject_gridtable_editlocal").unbind('click');
				jQuery("#periodSubject_gridtable_deletelocal").unbind('click');
			} else {
				selectId = "";
				jQuery("#periodSubject_gridtable_addlocal").unbind('click');
				jQuery("#periodSubject_gridtable_editlocal").unbind('click').bind('click',editPeriodSubButton);
				jQuery("#periodSubject_gridtable_deletelocal").unbind('click').bind('click',deletePreiodSubButton);
				$('#periodSubject_planId').val(treeNode.getParentNode().id);
				jQuery.ajax({
				    url: 'getPeriodSub?periodId='+id,
				    type: 'post',
				    dataType: 'json',
				    async:false,
				    error: function(data){
				    },
				    success: function(data){
				       $('#periodSubject_periodYear').attr('disabled','true').attr('readOnly','readOnly');
				       $('#periodSubject_periodNum').attr('disabled','true').attr('readOnly','readOnly');
				       $('#periodSubject_beginDate').attr('disabled','true').attr('readOnly','readOnly');
				       $('#periodSubject_endDate').attr('disabled','true').attr('readOnly','readOnly');
				       $('#periodSubject_periodId').val(data.periodId);
				       $('#periodSubject_periodYear').val(data.periodYear);
				       $('#periodSubject_periodNum').val(data.periodNum);
				       $('#periodSubject_beginDate').val(data.beginDate);
				       $('#periodSubject_endDate').val(data.endDate);
				       $('#periodSubject_status').val('select');
				       jQuery("#periodMonthDetails").css('visibility','visible');
					   jQuery("#periodMonth_gridtable").jqGrid('setGridParam',{url:"periodMonthGridList?filter_EQS_periodYear.periodYearId="+id,page:1}).trigger("reloadGrid");
				    }
				});
			}
		} 
	}
	
	var ztreesetting_periodPlan = {
			view : {
				showLine : true,
				selectedMulti : false
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback : {
				onClick: planTreeReload
			}
		};
	
	function refreshPeriodPlanTree(){
		jQuery.ajax({
		    url: 'makePeriodPlanTree',
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    },
		    success: function(data){
		        setTimeout(function(){
		        	periodTree = jQuery.fn.zTree.init(jQuery("#periodPlanTree"), ztreesetting_periodPlan,data.ztreeList);
		        	var rootnode = periodTree.getNodeByParam("id","-1",null);
		        	periodTree.selectNode(rootnode);
		        },100);
		    }
		});
	}
			
	function savePeriodSubject(){
		$('#periodMonth_gridtable').click();
		 $('td[aria-describedby=periodMonth_gridtable_endDate]').each(function(){
	        	$(this).unbind('dblclick');
	       });
		var periodSubject_periodId = $('#periodSubject_periodId').val();
		var periodSubject_planId = $('#periodSubject_planId').val();
		var periodSubject_periodYear = $('#periodSubject_periodYear').val();
		var periodSubject_periodNum = $('#periodSubject_periodNum').val();
		var periodSubject_beginDate = $('#periodSubject_beginDate').val();
		var periodSubject_endDate = $('#periodSubject_endDate').val();
		var periodSubject_status = $('#periodSubject_status').val();
		if(periodSubject_beginDate == ''||periodSubject_periodNum==''||periodSubject_endDate == '' || periodSubject_periodYear==''){
			alertMsg.error("表单项有空值"); 
			return ;
	      }
	    var start=new Date(periodSubject_beginDate.replace("-", "/").replace("-", "/"));  
	    var end=new Date(periodSubject_endDate.replace("-", "/").replace("-", "/"));  
	    if(end<start){  
	    	alertMsg.error("您输入的结束日期不能小于起始日期");
	        return false;  
	    }  
		jQuery('#periodSubject_gridtable_save').unbind('click');
		var ids = jQuery('#periodMonth_gridtable').getDataIDs();
		var months={};
		for(var i in ids){
			var rowId = ids[i];
			var row = jQuery('#periodMonth_gridtable').getRowData(rowId);
			var month={};
			month['beginDate'] = row['beginDate'];
			month['endDate'] = row['endDate'];
			month['periodMonthCode'] = row['periodMonthCode'];
			months['month'+i] = month;
		}
		jQuery.ajax({
		    url: 'savePeriodSubject',
		    data:{
		    	'monthsJson':json2str(months),
		    	"periodSubject_periodId":periodSubject_periodId,
		    	"periodSubject_planId":periodSubject_planId,
		    	"periodSubject_periodYear":periodSubject_periodYear,
		    	"periodSubject_periodNum":periodSubject_periodNum,
		    	"periodSubject_beginDate":periodSubject_beginDate,
		    	"periodSubject_endDate":periodSubject_endDate,
		    	"periodSubject_status":periodSubject_status
		    	},
		    type: 'post',
		    dataType: 'json',
		    async:false,
		    error: function(data){
		    	alertMsg.error(data.message);
		    },
		    success: function(data){
		    	alertMsg.correct(data.message);
		    	$('#periodSubject_periodYear').attr('disabled','true').attr('readOnly','readOnly');
		        $('#periodSubject_periodNum').attr('disabled','true').attr('readOnly','readOnly');
		        $('#periodSubject_beginDate').attr('disabled','true').attr('readOnly','readOnly');
		        $('#periodSubject_endDate').attr('disabled','true').attr('readOnly','readOnly');
		    	refreshPeriodPlanTree();
		    }
		});
	}
</script>

<div class="page">
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a id="periodSubject_gridtable_addlocal" class="addbutton" href="javaScript:" ><span><fmt:message
								key="button.add" />
					</span>
				</a>
				</li>
				<li><a id="periodSubject_gridtable_editlocal" class="changebutton"  href="javaScript:"
					><span><s:text name="button.edit" />
					</span>
				</a>
				</li>
				<li><a id="periodSubject_gridtable_save" class="savebutton" href="javaScript:" ><span><fmt:message
								key="button.save" />
					</span>
				</a>
				</li>
				<li><a id="periodSubject_gridtable_deletelocal" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span>
				</a>
				</li>
			</ul>
		</div>
		<div id="periodSubject_container">
			<div id="periodSubject_layout-west" class="pane ui-layout-west" 
				style="float: left; display: block; overflow: auto;">
				<DIV id="periodPlanTree" class="ztree"></DIV>
			</div>
			<div id="periodSubject_layout-center" class="pane ui-layout-center">
				<div id="periodSubject_layout-south" class="pane ui-layout-south" style="display: block; overflow: auto; height:170px">
					<div class="pageFormContent">
						<div class="unit">
						<input type="hidden" id="periodSubject_periodId"/>
						<input type="hidden" id="periodSubject_planId"/>
						<input type="hidden" id="periodSubject_status"/>
						</div>
						<div class="unit">
						<span>
							<label><s:text name="periodSubject.periodYear"/>:</label>
							<input type="text" readonly="readonly" id="periodSubject_periodYear" value="" maxlength="4">
						</span>
						</div>
						<div class="unit">
						<span>
							<label><s:text name="periodSubject.periodNum"/>:</label>
							<input type="text" readonly="readonly" id="periodSubject_periodNum" value="">
						</span>
						</div>
						<div class="unit">
						<span>
							<label><s:text name="periodSubject.beginDate"/>:</label>
							<input type="text" readonly="readonly" id="periodSubject_beginDate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" value="">
						</span>
						</div>
						<div class="unit">
						<span>
							<label><s:text name="periodSubject.endDate"/>:</label>
							<input type="text" readonly="readonly" id="periodSubject_endDate" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'})" value="">
						</span>
						</div>
					</div>
				</div>
				<div id="periodMonth_layout-south" class="pane ui-layout-south"
							style="padding: 2px">
					<div id="periodMonthDetails">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>