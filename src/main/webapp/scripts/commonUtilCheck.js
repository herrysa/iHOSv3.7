	//公用方法用来处理日期的前后判断问题  @param : text1 ,text2(两个比较text的 id),Posation（用来确定是前后text:0是前 1是后 ）,hide1,hide2(hide1,hide2为隐藏域id)
function checkQueryDate(item1,item2,posation,hide1,hide2){
		debugger;
		if(posation==0){
		//先获取连个text的value
		var value1 = $("#"+item1).val();
		var value2 = $("#"+item2).val();
		if(value2==""||value1==""){  //若存在一个text无值的情况不进行判断 
			if(value2==""){
				$("#"+hide1).attr("value",value1 + " 00:00:00");
				$("#"+hide2).attr("value","");
			}
		    if(value1==""){
		    	$("#"+hide1).attr("value","");
		    }
			return;      
		}else{
		//将两个字符串转换为日期比较
	      var date1 = new Date(value1.replace(/-/g,"/"));
	      $("#"+hide1).attr("value",value1 + " 00:00:00");
	      var date2 = new Date(value2.replace(/-/g,"/"));
	      $("#"+hide2).attr("value",value2 + " 23:59:59");
		}
		if(date1>date2){ //存在异常情况
			//清除掉当前值
			$("#"+item1).attr("value","");
			$("#"+hide1).attr("value","");
			alertMsg.error("开始时间不得大于结束时间!");
		}
	 }else{
		//先获取连个text的value
		   var value1 = $("#"+item1).val();
		   var value2 = $("#"+item2).val();
			if(value2==""||value1==""){ //若存在一个text无值的情况不进行判断
				if(value1==""){
					$("#"+hide2).attr("value",value2 + " 23:59:59");
					$("#"+hide1).attr("value","");
				}
			    if(value2==""){
			    	$("#"+hide2).attr("value","");
			    }
				return;
			}else{
			//将两个字符串转换为日期比较
		       var date1 = new Date(value1.replace(/-/g,"/"));
		       $("#"+hide1).attr("value",value1 + " 00:00:00");
	           var date2 = new Date(value2.replace(/-/g,"/"));
	           $("#"+hide2).attr("value",value2 + " 23:59:59");
			}
			if(date1>date2){ //存在异常情况
				$("#"+item2).attr("value","");
				$("#"+hide2).attr("value","");
				alertMsg.error("结束时间不得小于开始时间!");
			}
	    }
 }