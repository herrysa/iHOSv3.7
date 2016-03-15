
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var hrPersonHistoryGridIdString="#hrPersonHistory_gridtable";
	
	jQuery(document).ready(function() { 
		var hrPersonHistoryFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('hrPersonHistory_container','hrPersonHistory_gridtable',hrPersonHistoryFullSize);
		hrPersonHistoryLeftTree();
		var initFlag = 0;
		var hrPersonHistoryGrid = jQuery(hrPersonHistoryGridIdString);
    	hrPersonHistoryGrid.jqGrid({
	    	url : "hrPersonHistoryGridList?1=1",
			datatype : "json",
			mtype : "GET",
	        colModel:[
			{name:'snapId',index:'snapId',align:'center',label : '<s:text name="hrPersonSnap.snapId" />',hidden:true,key:true},				
			{name:'snapCode',index:'snapCode',align:'center',label : '<s:text name="hrPersonSnap.snapCode" />',hidden:true},
			{name:'personCode',index:'personCode',align:'left',width:70,label : '<s:text name="hrPersonSnap.personCode" />',hidden:false,highsearch:true},				
			{name:'name',index:'name',align:'left',width:80,label : '<s:text name="hrPersonSnap.name" />',hidden:false,highsearch:true},
			{name:'hrOrg.orgname',index:'hrOrg.orgname',align:'left',width:130,label : '<s:text name="hrPersonSnap.orgCode" />',hidden:false,highsearch:true},				
			{name:'hrOrg.orgCode',index:'hrOrg.orgCode',align:'left',width:130,label : '<s:text name="hrPersonSnap.orgCode" />',hidden:true},				
			{name:'hrDept.name',index:'hrDept.name',width:120,align:'left',label : '<s:text name="hrPersonSnap.hrDept" />',hidden:false,highsearch:true},				
			{name:'sex',index:'sex',align:'center',width:40,label : '<s:text name="hrPersonSnap.sex" />',hidden:false,highsearch:true},				
			{name:'empType.name',index:'empType.name',align:'left',width:60,label : '<s:text name="hrPersonSnap.empType" />',hidden:false,highsearch:true},	
			{name:'postType',index:'postType',align:'left',width:60,label : '<s:text name="hrPersonSnap.postType" />',hidden:false,highsearch:true},				
			{name:'duty.name',index:'duty.name',align:'left',width:60,label : '<s:text name="hrPersonSnap.duty" />',hidden:false,highsearch:true},				
			{name:'jobTitle',index:'jobTitle',align:'left',width:60,label : '<s:text name="hrPersonSnap.jobTitle" />',hidden:false,highsearch:true},		
			{name:'age',index:'age',width : '40',align:'right',label : '<s:text name="hrPersonSnap.age" />',hidden:false,formatter:'integer',highsearch:true},
			{name:'birthday',index:'birthday',align:'center',width:70,label : '<s:text name="hrPersonSnap.birthday" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},	
			{name:'idNumber',index:'idNumber',align:'left',width:100,label : '<s:text name="hrPersonSnap.idNumber" />',hidden:false,highsearch:true},
			{name:'nation',index:'nation',align:'left',width:60,label : '<s:text name="hrPersonSnap.nation" />',hidden:false,highsearch:true},	
			{name:'politicalCode',index:'politicalCode',align:'left',width:60,label : '<s:text name="hrPersonSnap.politicalCode" />',hidden:false,highsearch:true},
			{name:'workPhone',index:'workPhone',align:'left',width:100,label : '<s:text name="hrPersonSnap.workPhone" />',hidden:false,highsearch:true},
			{name:'mobilePhone',index:'mobilePhone',align:'left',width:100,label : '<s:text name="hrPersonSnap.mobilePhone" />',hidden:false,highsearch:true},	
			{name:'email',index:'email',align:'left',width:100,label : '<s:text name="hrPersonSnap.email" />',hidden:false,highsearch:true},				
			{name:'entryDate',index:'entryDate',align:'center',width:70,label : '<s:text name="hrPersonSnap.entryDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
			{name:'educationalLevel',index:'educationalLevel',align:'left',width:60,label : '<s:text name="hrPersonSnap.educationalLevel" />',hidden:false,highsearch:true},
			{name:'degree',index:'degree',align:'left',width:60,label : '<s:text name="hrPersonSnap.degree" />',hidden:false,highsearch:true},	
			{name:'school',index:'school',align:'left',width:100,label : '<s:text name="hrPersonSnap.school" />',hidden:false,highsearch:true},	
			{name:'profession',index:'profession',align:'left',width:60,label : '<s:text name="hrPersonSnap.profession" />',hidden:false,highsearch:true},	
			{name:'graduateDate',index:'graduateDate',align:'center',width:70,label : '<s:text name="hrPersonSnap.graduateDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
			{name:'ratio',index:'ratio',align:'right',width:60,label : '<s:text name="hrPersonSnap.ratio" />',hidden:false,highsearch:true,formatter:'currency',
				formatoptions:{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 4, prefix: '', suffix:'', defaultValue: '0.00'}},				
			{name:'disabled',index:'disabled',align:'center',width:40,label : '<s:text name="hrPersonSnap.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},				
			{name:'jjmark',index:'jjmark',align:'center',width:40,label : '<s:text name="hrPersonSnap.jjmark" />',hidden:false,formatter:'checkbox',highsearch:true},				
			{name:'imagePath',index:'imagePath',align:'center',label : '<s:text name="hrPerson.imagePath" />',hidden:true}
	
	        ],
	        jsonReader : {
				root : "hrPersonSnaps", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'personCode',
	        viewrecords: true,
	        sortorder: 'desc',
	        //caption:'<s:text name="hrPersonSnapList.title" />',
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
			 	/*2015.08.27 form search change*/
			 	gridContainerResize('hrPersonHistory','layout');
			 	jQuery("#hrPersonHistory_photo_list").height(jQuery("#hrPersonHistory_gridtable_div").height());
				var dataTest = {"id":"hrPersonHistory_gridtable"};
	      	   	jQuery.publish("onLoadSelect",dataTest,null);
	      	   	initFlag = initColumn('hrPersonHistory_gridtable','com.huge.ihos.hr.hrPerson.model.HrPersonHistory',initFlag);
	      	   
	      	  var rowNum = jQuery(this).getDataIDs().length;
	      		var defaultPersonPhoto = "${ctx}/styles/themes/rzlt_theme/ihos_images/defaultPerson.jpg";
      	    	var defaultPersonCard = "${ctx}/styles/themes/rzlt_theme/ihos_images/defaultPersonCard.jpg";
	       	   	jQuery("#hrPersonHistory_photo_list")[0].innerHTML="";
              	var photoHtml = jQuery("#hrPersonHistory_photo_list")[0].innerHTML;
	       	   	if(rowNum>0){           
	              	photoHtml +='<table>' +'<tr>';     
	 	            var rowIds = jQuery(this).getDataIDs();
	 	            var ret = jQuery(this).jqGrid('getRowData');
	 	            var id='';
	 	            for(var i=0;i<rowNum;i++){
	 	              	id=rowIds[i];
	 	           	if(ret[i]['sex']=='女'){
 	              		defaultPersonPhoto = "${ctx}/styles/themes/rzlt_theme/ihos_images/femaleDefalut.jpg";
 	              	}else{
 	              		defaultPersonPhoto = "${ctx}/styles/themes/rzlt_theme/ihos_images/maleDefault.jpg";
 	              	}
	 	               	var snapId=ret[i]["snapId"];
	 		   	        setCellText(this,id,'name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrPersonRecord(\''+snapId+'\');">'+ret[i]["name"]+'</a>');
	 	               	if(ret[i]["imagePath"]){
	 	                	var src="${ctx}/home/hrPerson/"+ret[i]["hrOrg.orgCode"]+"/"+ret[i]["imagePath"];
	 	               	}else{
	 	                    var src=defaultPersonPhoto;
	 	               	}
	 	                var divHtml ='<td>' + '<div class="left" onclick="javascript:selectRecord(\''+snapId+'\');"><a class="photos-max-img" ><img src='+ src +' width="71px" height="99px" alt="图片描述"/></a><div class="personName"><a onclick="javascript:viewHrPersonRecord(\''+snapId+'\');" style="color:blue;cursor:pointer;">'+ret[i]["name"]+'</a>';	                	
	 	                	divHtml +='</div><div class="departmentName"><label>'+ret[i]["hrDept.name"]+'</label></div><div class="postType"><label>'+ret[i]["duty.name"]+'</label></div></div>';
	 	                	divHtml +='</td>';
	  	                if((i+1)%5==0){
	  	                	divHtml += '</tr>' + '<tr>';	                		
	  	                }
	 	                photoHtml += divHtml;                	
	 	            }
	 	            photoHtml +='</tr></table>';
	 	        }else{
		        	var tw = jQuery(this).outerWidth();
		        	jQuery(this).parent().width(tw);
		        	jQuery(this).parent().height(1);
		        }
	       	 	photoHtml +='<div class="preview-box"><div style="position: absolute;width: 142px;height:198px;top:6px;left:6px"> <img id="imgphoto" style="width:100%;height:100%" alt="加载中..." src="'+defaultPersonPhoto+'"> </div> </div>';
	 	        photoHtml +='<div style="clear:both"></div>';
	 	        jQuery("#hrPersonHistory_photo_list")[0].innerHTML=photoHtml;
	 	        jQuery(".left").css({"background-image":"url("+defaultPersonCard+")","background-size":"100% 100%"});	    
// 	 	        var hightMax= jQuery("#hrPersonHistory_photo_list").height()+jQuery("#hrPersonHistory_photo_list").offset().top;
// 	 	        var widthMax= jQuery("#hrPersonHistory_photo_list").width()+jQuery("#hrPersonHistory_photo_list").offset().left;
	 	        
	 	        jQuery(".photos-max-img").mousemove(function(event){ 	            	
	 	        	var imgsrc= jQuery(this).find("img").attr("src");
	 	            jQuery("#imgphoto").attr("src",imgsrc); 	            	            
	 	            var x = event.clientX;
	 	            var y = event.clientY;
	 	            var divx=jQuery("#hrPersonHistory_layout-center").offset().left;
	 	            var divy=jQuery("#hrPersonHistory_layout-center").offset().top;	
	 	           var hightMax=jQuery("#hrPersonHistory_layout-center").offset().top+jQuery("#hrPersonHistory_photo_list").height();
	            	var widthMax=jQuery("#hrPersonHistory_layout-center").offset().left+jQuery("#hrPersonHistory_photo_list").width();
	            	var divx=jQuery("#hrPersonHistory_layout-center").offset().left;
	            	var divy=jQuery("#hrPersonHistory_layout-center").offset().top;	
	            	if(y+200>hightMax){
	            			jQuery(".preview-box").css("top",(y-divy-199-15));
	            			if(y-jQuery("#hrPersonHistory_layout-center").offset().top<hightMax-y){
	            				jQuery(".preview-box").css("top",(y-divy+10));
	            			}
	            		}else{
	            			jQuery(".preview-box").css("top",(y-divy+10));
	            		} 
//  	            	if(y+200>hightMax){
// 	            			jQuery(".preview-box").css("top",(y-divy-199-15));
// 	            		}else{
// 	            			jQuery(".preview-box").css("top",(y-divy+10));
// 	            		} 
 	            	if(x+154>widthMax){
 	            		jQuery(".preview-box").css("left",(x-divx-153-15));
 	            	}else{
 	            		jQuery(".preview-box").css("left",(x-divx+10));
 	            	}   	 
 	            	jQuery(".preview-box").show();	  	            	            	 	            	
 	            }); 
	 	        
	  	        jQuery(".photos-max-img").mouseleave(function(event){
               		var div = jQuery(this);  
                    var x=event.clientX;  
                    var y=event.clientY;  
                    var divxLeft = div.offset().left;  
                    var divyTop = div.offset().top;  
                    var divxRight = divxLeft + 71;  
                    var divyBottom = divyTop + 99;  
                    if( x < divxLeft || x > divxRight || y < divyTop || y > divyBottom){  
                    	jQuery(".preview-box").hide();   
                    }   	            	            		            	
           		});
	  	      var ztree = $.fn.zTree.getZTreeObj("hrPersonHistoryLeftTree");
              if(ztree){
              	var selectNode = ztree.getSelectedNodes();
					if(selectNode && selectNode.length==1 && selectNode[0].subSysTem =='PERSON'){
						var selectid = selectNode[0].id;
						jQuery(this).jqGrid('setSelection',selectid);
					}
              }
	       	} 
	    });
	    jQuery(hrPersonHistoryGrid).jqGrid('bindKeys');
	    
	  //实例化ToolButtonBar
	     var hrPersonHistory_menuButtonArrJson = "${menuButtonArrJson}";
	     var hrPersonHistory_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(hrPersonHistory_menuButtonArrJson,false)));
	     var hrPersonHistory_toolButtonBar = new ToolButtonBar({el:$('#hrPersonHistory_buttonBar'),collection:hrPersonHistory_toolButtonCollection,attributes:{
	      tableId : 'hrPersonHistory_gridtable',
	      baseName : 'hrPersonHistory',
	      width : 600,
	      height : 600,
	      base_URL : null,
	      optId : null,
	      fatherGrid : null,
	      extraParam : null,
	      selectNone : "请选择记录。",
	      selectMore : "只能选择一条记录。",
	      addTitle : '<s:text name="hrPersonHistoryNew.title"/>',
	      editTitle : null
	     }}).render();
	     var hrPersonHistory_function = new scriptFunction();
	     hrPersonHistory_function.optBeforeCall = function(e,$this,param){
				var pass = false;
				if(param.checkPeriod){
					if('${yearStarted}'!='true'){
						alertMsg.error("本年度人力资源系统未启用。");
		    			return pass;
					}
				}
		        return true;
			};
	     //实例化结束
	    //为button添加方法 (普通点击按钮)
  		hrPersonHistory_toolButtonBar.addCallBody('1002010301',function(e,$this,param){
  			//exportToExcel('hrPersonHistory_gridtable','HrPersonSnap','人员数据','page');
  			exportToExcelHrPersonHis('hrPersonHistory_gridtable','HrPersonSnap','人员数据','page');
  		 },{});
  		hrPersonHistory_toolButtonBar.addBeforeCall('1002010301',function(e,$this,param){
			return hrPersonHistory_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		hrPersonHistory_toolButtonBar.addCallBody('1002010302',function(e,$this,param){
//   			exportToExcel('hrPersonHistory_gridtable','HrPersonSnap','人员数据','all');
  			exportToExcelHrPersonHis('hrPersonHistory_gridtable','HrPersonSnap','人员数据','all');
 		 },{});
  		hrPersonHistory_toolButtonBar.addBeforeCall('1002010302',function(e,$this,param){
			return hrPersonHistory_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		hrPersonHistory_toolButtonBar.addCallBody('1002010303',function(e,$this,param){
  			if(jQuery("#hrPersonHistory_gridtable_div :visible").length>0){
	    		jQuery("#hrPersonHistory_gridtable_div").hide();
		    	jQuery("#hrPersonHistory_photo_list").show();
    		}else{
	    		jQuery("#hrPersonHistory_gridtable_div").show();
	    		jQuery("#hrPersonHistory_photo_list").hide();
    		}
 		 },{});
  		hrPersonHistory_toolButtonBar.addBeforeCall('1002010303',function(e,$this,param){
			return hrPersonHistory_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
  		//设置表格列
	     var hrPersonHistory_setColShowButton = {id:'1002010304',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
	       callBody:function(){
	        setColShow('hrPersonHistory_gridtable','com.huge.ihos.hr.hrPerson.model.HrPersonHistory');
	       }};
	     hrPersonHistory_toolButtonBar.addButton(hrPersonHistory_setColShowButton);//实例化ToolButtonBar
// 	   	jQuery("#hrPersonHistory_gridtable_photo_custom").unbind( 'click' ).bind("click",function(){   
// 	   		if(jQuery("#hrPersonHistory_gridtable_div :visible").length>0){
// 	    		jQuery("#hrPersonHistory_gridtable_div").hide();
// 		    	jQuery("#hrPersonHistory_photo_list").show();
//     		}else{
// 	    		jQuery("#hrPersonHistory_gridtable_div").show();
// 	    		jQuery("#hrPersonHistory_photo_list").hide();
//     		}
// 	 	});
	  	
	   	jQuery("#hrPersonHistory_queryCommon").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT id, name FROM sy_query_common WHERE disabled = 0 order by sn asc",
			exceptnullparent : false,
			lazy : false
		});
  	});
	
	function hrPersonHistoryLeftTree() {
		var url = "makeHrPersonSnapTree?1=1&loadFrom=his";
		var hisTime = jQuery("#hrPersonHistory_search_form_snapTime").val();
		var showDisabled = jQuery("#hrPersonHistory_showDisabled").attr("checked");
		if(hisTime){
			url += "&hisTime="+hisTime;
		}
		/* if(showDisabled){
			url += "&showDisabled=true"; 
		} */
		$.get(url, {"_" : $.now()}, function(data) {
			var hrDeptHisTreeData = data.hrPersonHisTreeNodes;
			var hrDeptHisTree = $.fn.zTree.init($("#hrPersonHistoryLeftTree"),ztreesetting_hrPersonHisTree, hrDeptHisTreeData);
			var nodes = hrDeptHisTree.getNodes();
			hrDeptHisTree.expandNode(nodes[0], true, false, true);
			hrDeptHisTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'hrPersonHistoryLeftTree',
    		showCode:jQuery('#hrPersonHistory_showCode')[0],
    		disabledDept:jQuery("#hrPersonHistory_showDisabled")[0],
    		disabledPerson:jQuery("#hrPersonHistory_showDisabledPerson")[0],
    		showCount:jQuery("#hrPersonHistory_showPersonCount")[0] });
		});
		jQuery("#hrPersonHistory_expandTree").text("展开");
	}
	
	var ztreesetting_hrPersonHisTree = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false,
			fontCss : setDisabledDeptFontCss
		},
		callback : {
			beforeDrag:function(){return false},
			onClick : function(event, treeId, treeNode, clickFlag){
				var urlString = "hrPersonHistoryGridList?1=1";
			    var nodeId = treeNode.id;
			    if(nodeId!="-1"){
			    	if(treeNode.subSysTem==='ORG'){
				    	urlString += "&orgCode="+nodeId;
			    	}else if(treeNode.subSysTem==='DEPT'){
				    	urlString += "&deptSnapId="+(nodeId+'_'+treeNode.snapCode);
			    	}else{
			    		urlString += "&personId="+nodeId;
			    	}
			    }
			    urlString=encodeURI(urlString);
			    jQuery("#hrPersonHistory_gridtable").jqGrid('setGridParam',{url:urlString});
			    propertyFilterSearch('hrPersonHistory_search_form','hrPersonHistory_gridtable');
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
		}
	};
	
	//显示停用部门
	function showDisabledDeptInPersonHis(){
		var urlString = jQuery("#hrPersonHistory_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabledDept','');
		urlString = urlString.replace('showDisabledPerson','');
		var showDisabledDept = jQuery("#hrPersonHistory_showDisabled").attr("checked");
		var showDisabledPerson = jQuery("#hrPersonHistory_showDisabledPerson").attr("checked");
		if(showDisabledDept){
			urlString += "&showDisabledDept=true";
		}
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=true";
		}
		toggleDisabledOrCount({treeId:'hrPersonHistoryLeftTree',
    		showCode:jQuery('#hrPersonHistory_showCode')[0],
    		disabledDept:jQuery("#hrPersonHistory_showDisabled")[0],
    		disabledPerson:jQuery("#hrPersonHistory_showDisabledPerson")[0],
    		showCount:jQuery("#hrPersonHistory_showPersonCount")[0] });
		 urlString=encodeURI(urlString);
		 jQuery("#hrPersonHistory_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
	}
	
	//查询
	var hisTime;
	function hrPersonHistoryGridReload(){
		var newTime = jQuery("#hrPersonHistory_search_form_snapTime").val();
		if(newTime && newTime!=hisTime){
			hrPersonHistoryLeftTree();
			hisTime = newTime;
			// 去除url里的orgCode和snapId
			var url = $("#hrPersonHistory_gridtable").jqGrid("getGridParam", "url");
			url = url.replace('orgCode','');
			url = url.replace('snapId','');
			url = url.replace('deptSnapId','');
			url=encodeURI(url);
		    jQuery("#hrPersonHistory_gridtable").jqGrid('setGridParam',{url:url});
		}
		propertyFilterSearch('hrPersonHistory_search_form','hrPersonHistory_gridtable');
	}
	//photo list person selected
	function selectRecord(rowid){
		var sid = jQuery("#hrPersonHistory_gridtable").jqGrid('getGridParam','selarrrow');
		var rowIds = sid;
	    if(sid==null){
		}else{
			var sumLength = rowIds.length;
			for(var num=0;num<sumLength;num++){
				jQuery("#hrPersonHistory_gridtable").jqGrid('setSelection',rowIds[0]);
			}
		}
	    jQuery("#hrPersonHistory_gridtable").jqGrid('setSelection',rowid);
	}

// 	 function getHrDeptHisNode(){
// 		var hisTime =jQuery("#hrPersonHistory_search_form_snapTime").val();
// 		jQuery("#hrPersonHistory_hrDept").treeselect({
// 			dataType:"url",
// 			optType:"multi",
// 			url:'getHrDeptHisNode?hisTime='+hisTime,
// 			exceptnullparent:false,
// 			selectParent:false,
// 			lazy:false
// 		});
// 	} 
	function exportToExcelHrPersonHis(gridId,entityName,title,outPutType){
		 var url = jQuery("#"+gridId).jqGrid('getGridParam','url');
		 var formData = jQuery("#"+gridId).jqGrid('getGridParam','formData');
		 var param = url.split("?")[1];
		 //alert(json2str(jQuery("#sourcepayin_gridtable")[0].p.colModel));
		 var colModel = jQuery("#"+gridId).jqGrid('getGridParam','colModel');
		 var colDefine = new Array();
		 var colDefineIndex = 0;
		 for(var mi=0;mi<colModel.length;mi++){
		  var col = colModel[mi];
		  if(col.name!="rn"&&col.name!="cb"&&!col.hidden){
		   var label = col.label?col.label:col.name;
		   var type = col.formatter?col.formatter:1;
		   var align = col.align?col.align:"left";
		   var width = col.width?parseInt(col.width)*20:50*20;
		   colDefine[colDefineIndex] = {name:col.name,type:type,align:align,width:width,label:label};
		   colDefineIndex++;
		  }
		 }
		 var colDefineStr = json2str(colDefine);
		 var page=1,pageSize=20,sortname,sortorder;
		 page = jQuery("#"+gridId).jqGrid('getGridParam','page');
		 pageSize = jQuery("#"+gridId).jqGrid('getGridParam','rowNum');
		 
		 sortname = jQuery("#"+gridId).jqGrid('getGridParam','sortname');
		 sortorder = jQuery("#"+gridId).jqGrid('getGridParam','sortorder');
		 var u =  'outPutExcelForHrPersonSnap?'+param+"&entityName="+entityName;
		 var postData = cloneObj(jQuery("#"+gridId).jqGrid("getGridParam", "postData"));
		 postData['entityName']=entityName;
		 postData['title']=title;
		 postData['outPutType']=outPutType;
		 postData['page']=page;
		 postData['pageSize']=pageSize;
		 postData['sortname']=sortname;
		 postData['sortorder']=sortorder;
		 postData['colDefineStr']=colDefineStr;
		 var excelSourceDataType="HrPersonHis";
		 postData['excelSourceDataType']=excelSourceDataType;
 		
		 $.ajax({
		  url: u,
		  type: 'post',
		  data:postData,
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
</script>
<style type="text/css" media="screen">
  .left{ 
  		float:left; background:#eeeeee;margin-top:40px;
      	margin-left:30px;position:relative;width:200px;height:107px;
   } 
   .personName{
   		position:absolute; top:30px; left:115px;width:20px;white-space:nowrap;
   }
   .departmentName{
   		position:absolute; top:60px; left:115px;width:20px;white-space:nowrap;
   }
   .postType{
   		position:absolute; top:80px; left:115px;width:20px;white-space:nowrap;
   }
   .preview-box{ 
		position: absolute;  
		top: 0px; 
		width: 155px; 
		height: 211px; 
		background-color: #eeeeee; 
		border: #CCC 1px solid; 
		 left: 0px;  
		display: none; 
		overflow: hidden; 
	} 
	.photos-max-img{ 
		top:3px;
		left:3px;
		width: 71px; 
		height: 99px; 
		display:block; 
		border: #CCC 1px solid; 
		position: relative; 
		cursor: default; 
	} 
</style>
<div class="page">
	<div id="hrPersonHistory_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="hrPersonHistory_search_form" class="queryarea-form">
					<input type="hidden" name="showDisabled" id="hrPersonHistory_search_form_showDisabled" value=""/>			
					<label class="queryarea-label">
						<s:text name='历史时间'/>:
					 	<input type="text"  id="hrPersonHistory_search_form_snapTime" name="hisTime" class="Wdate" style="height:15px;width:110px" onclick="WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					</label>
					<label class="queryarea-label">
						<s:text name='hrPersonSnap.personCode'/>:
					 	<input type="text" id="hrPersonHistory_personCode" style="width:80px" name="filter_LIKES_personCode"/>
					</label>
					<label class="queryarea-label">
						<s:text name='hrPersonSnap.name'/>:
					 	<input type="text" id="hrPersonHistory_name" style="width:80px"  name="filter_LIKES_name"/>
					</label>
<!-- 					<label style="float:none;white-space:nowrap" > -->
<%-- 						<s:text name='hrPersonSnap.orgCode'/>: --%>
<!-- 					 	<input type="text" id="hrPersonHistory_orgCode" /> -->
<!-- 					 	<input type="hidden" id="hrPersonHistory_orgCode_id"/> -->
<!-- 					</label> -->
					<label class="queryarea-label">
						<s:text name='hrPersonSnap.hrDept'/>:
						<input type="text" id="hrPersonHistory_hrDept" style="width:80px" name="filter_LIKES_hrDept.name" />
					</label> 
					<label class="queryarea-label">
       					<s:text name='hrPersonSnap.sex'/>:
      					<s:select key="hrPersonSnap.sex" name="filter_EQS_sex"
            			  maxlength="50" list="sexList" listKey="value" headerKey="" headerValue="--" 
            			  listValue="content" emptyOption="false" theme="simple"></s:select>
     				</label>
     				<label class="queryarea-label">
       					<s:text name='hrPersonSnap.educationalLevel'/>:
      					<s:select key="hrPersonSnap.educationalLevel" name="filter_EQS_educationalLevel"
            			  maxlength="50" list="educationalLevelList" listKey="value" headerKey="" headerValue="--" 
            			  listValue="content" emptyOption="false" theme="simple"></s:select>
     				 </label>
     				 <label class="queryarea-label">
       					<s:text name='hrPersonSnap.nation'/>:
      					<s:select key="hrPersonSnap.nation" name="filter_EQS_nation"
            			  maxlength="50" list="nationList" listKey="value" headerKey="" headerValue="--" 
            			  listValue="content" emptyOption="false" theme="simple"></s:select>
     				 </label>
     				 <label class="queryarea-label">
						<s:text name='hrPersonSnap.disabled'/>:
					 	<s:select name="filter_EQB_disabled" headerKey="" headerValue="--" 
							list="#{'1':'是','0':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple"  style="font-size:9pt;">
						</s:select>
					</label>
					<label class="queryarea-label">
						<s:text name='hrPersonSnap.queryCommon'/>:
					 	<input type="text" id="hrPersonHistory_queryCommon" style="width:120px"/>
					 	<input type="hidden" id="hrPersonHistory_queryCommon_id" name="queryCommonId"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="hrPersonHistoryGridReload();"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="hrPersonHistoryGridReload()"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
	<div class="panelBar" id="hrPersonHistory_buttonBar">

<!-- 			<ul class="toolBar"> -->
<!-- 				<li> -->
<!-- 					<a class="excelbutton" href="javaScript:exportToExcel('hrPersonHistory_gridtable','hrPersonSnap','部门数据','page')"><span>导出本页数据 </span> </a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a class="excelbutton" href="javaScript:exportToExcel('hrPersonHistory_gridtable','hrPersonSnap','部门数据','all')"><span>导出当前全部数据 </span> </a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a id="hrPersonHistory_gridtable_photo_custom" class="zbcomputebutton"  href="javaScript:"><span id="hrPersonHistory_gridtable_photoSpan">照片/列表</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a class="settlebutton"  href="javaScript:setColShow('hrPersonHistory_gridtable','com.huge.ihos.hr.hrPerson.model.hrPersonHistory')"><span><s:text name="button.setColShow" /></span></a> --%>
<!-- 				</li> -->
<!-- 			</ul> -->

		</div>
		<div id="hrPersonHistory_container">
			<div id="hrPersonHistory_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
     			<span>
      				显示机构编码
      				<input id="hrPersonHistory_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrPersonHistoryLeftTree',showCode:this,disabledDept:jQuery('#hrPersonHistory_showDisabled')[0],disabledPerson:jQuery('#hrPersonHistory_showDisabledPerson')[0],showCount:jQuery('#hrPersonHistory_showPersonCount')[0]})"/>
     			</span>
    			<span>
      				显示人员数
      				<input id="hrPersonHistory_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrPersonHistoryLeftTree',showCode:jQuery('#hrPersonHistory_showCode')[0],disabledDept:jQuery('#hrPersonHistory_showDisabled')[0],disabledPerson:jQuery('#hrPersonHistory_showDisabledPerson')[0],showCount:this})"/>
     			</span>
     			<label id="hrPersonHistory_expandTree" onclick="toggleExpandHrTree(this,'hrPersonHistoryLeftTree')">展开</label>
    			</div>
    			<div class="treeTopCheckBox">
     			<span>
      				显示停用部门
      				<input id="hrPersonHistory_showDisabled" type="checkbox" onclick="showDisabledDeptInPersonHis()"/>
     			</span>
     			<span>
          			显示停用人员
          			<input id="hrPersonHistory_showDisabledPerson" type="checkbox" onclick="showDisabledDeptInPersonHis()"/>
          		</span>
    			</div>
    			<div class="treeTopCheckBox">
     			<span>
      			按部门检索：
      			<input type="text" onKeyUp="searchTree('hrPersonHistoryLeftTree',this)"/>
     			</span>
    			</div>				
				<div id="hrPersonHistoryLeftTree" class="ztree"></div>
			</div>
			<div id="hrPersonHistory_layout-center" class="pane ui-layout-center">
				<div id="hrPersonHistory_gridtable_div" class="grid-wrapdiv" style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="hrPersonHistory_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_hrPersonHistory_gridtable" class='loading ui-state-default ui-state-active' style="display:none;"></div>
		 			<table id="hrPersonHistory_gridtable"></table>
				</div>
	 			<div id="hrPersonHistory_photo_list" style="background:#eeeeee;overflow:auto;display:none;"></div>
				<div class="panelBar"  id="hrPersonHistory_pageBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="hrPersonHistory_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="hrPersonHistory_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
			
					<div id="hrPersonHistory_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
