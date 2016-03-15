
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
 
	
	jQuery(document).ready(function() { 
var periodMonthGrid = jQuery("#periodMonth_gridtable");
    periodMonthGrid.jqGrid({
    	url : "",
    	editurl:"periodMonthGridEdit",
		datatype : "json",
		mtype : "GET",
        colModel:[
/* {name:'monthId',index:'monthId',align:'center',label : '<s:text name="periodMonth.monthId" />',hidden:true}, */				
{name:'periodMonthCode',index:'month',align:'center',label : '<s:text name="periodMonth.month" />',hidden:false,width:'60px',key:true},				
{name:'beginDate',index:'beginDate',align:'center',label : '<s:text name="periodMonth.beginDate" />',width:'150px',hidden:false,sortable:false,formatter:"date",formatoptions: {srcformat:'Y-m-d',newformat:'Y-m-d'}},				
{name:'endDate',index:'endDate',align:'center',label : '<s:text name="periodMonth.endDate" />',width:'150px',hidden:false,sortable:false,formatter:"date",formatoptions: {srcformat:'Y-m-d',newformat:'Y-m-d'}},				
        ],
        jsonReader : {
			root : "periodMonths", // (2)
			page : "page",
			total : "total",
			records : "records", // (3)
			repeatitems : false
		// (4)
		},
        sortname: 'periodMonthCode',
        viewrecords: true,
        sortorder: 'asc',
        //caption:'<s:text name="periodMonthList.title" />',
        height:300,
        gridview:true,
        rownumbers:true,
        loadui: "disable",
        multiselect: false,
		multiboxonly:false,
		shrinkToFit:false,
		autowidth:false,
        onSelectRow: function(rowid) {
       
       	},
		 gridComplete:function(){
           if(jQuery(this).getDataIDs().length>0){
              //jQuery(this).jqGrid('setSelection',jQuery(this).getDataIDs()[0]);
            }
           var dataTest = {"id":"periodMonth_gridtable"};
      	   jQuery.publish("onLoadSelect",dataTest,null);
      	   makepager("periodMonth_gridtable");
      	   
      	   var status = $('#periodSubject_status').val();
      	   if(status =='add'|| status =='edit'){
				monthEditable();      		   
      	   }
       	} 

    });
    jQuery(periodMonthGrid).jqGrid('bindKeys');
    
	
	
	
	//periodMonthLayout.resizeAll();
  	});
	
	function monthEditable(){
		$('td[aria-describedby=periodMonth_gridtable_endDate]').each(function(){
			$(this).unbind('dblclick').bind('dblclick', function(){
				//alert($(this).html());
				var inputStr = "<input type='text' value='"+$(this).text()+"' id='monthBeginDateInput' onclick=WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd'}) />";
				$(this).html(inputStr);
				var monthInput = $(this);
				
				$('#periodMonth_gridtable').unbind('click').bind('click',function(){
					
					var dateStr = $("#monthBeginDateInput").val();
					//如果文本框不存在, 则不执行清空代码
					if(!dateStr){
						return;
					}
					var start=new Date($("#monthBeginDateInput").parent().prev().text().replace("-", "/").replace("-", "/"));  
				    var end=new Date(dateStr.replace("-", "/").replace("-", "/"));  
				    if(end<start){  
				    	alertMsg.error("您输入的结束日期不能小于起始时间");
				        return false;  
				    }
					var year = dateStr.split('-')[0];
					var month = dateStr.split('-')[1];
					var day = dateStr.split('-')[2];
					
					var date1 = new Date(year, (parseInt(month)-1), day);
				
					date1 = date1.valueOf();
					date1=date1 +1 * 24 * 60 * 60 * 1000;
					var myDate = new Date(date1);
					var endYear = myDate.getFullYear();
					var endMonth = myDate.getMonth()+1;
					var endDay = myDate.getDate();
					endMonth = endMonth> 9? endMonth:"0"+endMonth;
					endDay = endDay> 9? endDay:"0"+endDay;
					monthInput.parent().next().find('td[aria-describedby=periodMonth_gridtable_beginDate]').html(endYear+"-"+endMonth+"-"+endDay);
					if(!monthInput.parent().next().html()){
						$('#periodSubject_endDate').val(dateStr);
					}
					monthInput.html($("#monthBeginDateInput").val());
				});
			});
		 });
	}
</script>

<div class="page">
		<div id="periodMonth_gridtable_div" style="height:540px">
<div id="periodMonth_container">
			
 <table id="periodMonth_gridtable"></table>
		<div id="periodMonthPager"></div>
</div>
	
</div>
</div>
