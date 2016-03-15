
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	var showIds = null;
	var hrPersonCurrentGridIdString="#hrPersonCurrent_gridtable";
	jQuery(document).ready(function() {
		jQuery("#hrPersonCurrent_pageHeader").find("select").css("font-size","12px");
		var hrPersonCurrentFullSize = jQuery("#container").innerHeight()-116;
		setLeftTreeLayout('hrPersonCurrent_container','hrPersonCurrent_gridtable',hrPersonCurrentFullSize);
		
		hrPersonCurrentLeftTree();
		var initFlag = 0;
		var hrPersonCurrentGrid = jQuery(hrPersonCurrentGridIdString);
   		hrPersonCurrentGrid.jqGrid({
	    	url : "hrPersonCurrentGridList?1=1",
	    	editurl:"hrPersonCurrentGridEdit",
			datatype : "json",
			mtype : "GET",
	        colModel:[
				{name:'personId',index:'personId',align:'center',label : '<s:text name="hrPersonSnap.personId" />',hidden:true,key:true,highsearch:false},				
				{name:'snapCode',index:'snapCode',align:'center',label : '<s:text name="hrPersonSnap.snapCode" />',hidden:true,highsearch:false},				
				{name:'personCode',index:'personCode',align:'left',width:70,label : '<s:text name="hrPersonSnap.personCode" />',hidden:false,highsearch:true},				
				{name:'name',index:'name',align:'left',width:80,label : '<s:text name="hrPersonSnap.name" />',hidden:false,highsearch:true},
				{name:'hrOrg.orgname',index:'hrOrg.orgname',align:'left',width:130,label : '<s:text name="hrPersonSnap.orgCode" />',hidden:false,highsearch:true},		
				{name:'hrOrg.orgCode',index:'hrOrg.orgCode',align:'left',width:130,label : '<s:text name="hrPersonSnap.orgCode" />',hidden:true},		
				{name:'department.departmentId',index:'department.departmentId',width:120,align:'left',label : '<s:text name="hrPersonSnap.departmentId" />',hidden:true},				
				{name:'department.name',index:'department.name',width:120,align:'left',label : '<s:text name="hrPersonSnap.hrDept" />',hidden:false,highsearch:true},				
				{name:'sex',index:'sex',align:'center',width:40,label : '<s:text name="hrPersonSnap.sex" />',hidden:false,highsearch:true},				
				{name:'status.name',index:'status.name',align:'left',width:60,label : '<s:text name="hrPersonSnap.empType" />',hidden:false,highsearch:true},	
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
				{name:'profession',index:'profession',align:'left',width:80,label : '<s:text name="hrPersonSnap.profession" />',hidden:false,highsearch:true},	
				{name:'graduateDate',index:'graduateDate',align:'center',width:70,label : '<s:text name="hrPersonSnap.graduateDate" />',hidden:false,formatter:"date",formatoptions:{newformat : 'Y-m-d'},highsearch:true},
				{name:'ratio',index:'ratio',align:'right',width:60,label : '<s:text name="hrPersonSnap.ratio" />',hidden:false,highsearch:true,formatter:'currency',
					formatoptions:{decimalSeparator:'.', thousandsSeparator: ',', decimalPlaces: 4, prefix: '', suffix:'', defaultValue: '0.0000'}},				
				{name:'disable',index:'disable',align:'center',width:40,label : '<s:text name="hrPersonSnap.disabled" />',hidden:false,formatter:'checkbox',highsearch:true},				
				{name:'jjmark',index:'jjmark',align:'center',width:40,label : '<s:text name="hrPersonSnap.jjmark" />',hidden:false,formatter:'checkbox',highsearch:true},				
				{name:'imagePath',index:'imagePath',align:'center',label : '<s:text name="hrPerson.imagePath" />',hidden:true}
	        ],
	        jsonReader : {
				root : "hrPersonCurrents", // (2)
				page : "page",
				total : "total",
				records : "records", // (3)
				repeatitems : false
			// (4)
			},
	        sortname: 'personCode',
	        viewrecords: true,
	        sortorder: 'asc',
	        //caption:'<s:text name="hrPersonCurrentList.title" />',
	        height:300,
	        gridview:true,
	        rownumbers:true,
	        postData:{'filter_EQB_disable':false,'filter_EQB_department.disabled':false},
	        loadui: "disable",
	        multiselect: true,
			multiboxonly:true,
			shrinkToFit:false,
			autowidth:false,
	        onSelectRow: function(rowid) {
	       
	       	},
			gridComplete:function(){
	            var dataTest = {"id":"hrPersonCurrent_gridtable"};
	      	    jQuery.publish("onLoadSelect",dataTest,null);
	      	    initFlag = initColumn('hrPersonCurrent_gridtable','com.huge.ihos.hr.hrPerson.model.HrPersonSnap',initFlag);
	      	  	
	      	    //照片墙
// 	      	    var defaultPersonPhoto = "${ctx}/styles/themes/rzlt_theme/ihos_images/defaultPerson.jpg";
				 var defaultPersonPhoto ="";
	      	    var defaultPersonCard = "${ctx}/styles/themes/rzlt_theme/ihos_images/defaultPersonCard.jpg";
				var rowNum = jQuery(this).getDataIDs().length;
	       	   	jQuery("#hrPersonCurrent_photo_list")[0].innerHTML="";
              	var photoHtml = jQuery("#hrPersonCurrent_photo_list")[0].innerHTML;
	       	   	if(rowNum>0){           
	              	photoHtml +='<table><tr>';     
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
	 	               	var snapId=ret[i]["personId"]+'_'+ret[i]["snapCode"];
	 	               	setCellText(this,id,'name','<a style="color:blue;text-decoration:underline;cursor:pointer;" onclick="javascript:viewHrPersonRecord(\''+snapId+'\');">'+ret[i]["name"]+'</a>');
	 	                var src;
	 	               	if(ret[i]["imagePath"]){
	 	               		src ="${ctx}/home/hrPerson/"+ret[i]["hrOrg.orgCode"]+"/"+ret[i]["imagePath"];
	 	               	}else{
	 	                    src=defaultPersonPhoto;
	 	               	}
 	                	var personId=ret[i]["personId"];
 	                	var divHtml ='<td>' + '<div class="left" onclick="javascript:selectRecord(\''+personId+'\',this);"><a class="photos-max-img" ><img src='+ src +' width="71px" height="99px" alt="图片描述"/></a><div class="personName"><a onclick="javascript:viewHrPersonRecord(\''+snapId+'\');" style="color:blue;cursor:pointer;">'+ret[i]["name"]+'</a>';	                	
	 	                	divHtml +='</div><div class="departmentName"><label>'+ret[i]["department.name"]+'</label></div><div class="postType"><label>'+ret[i]["duty.name"]+'</label></div></div>';
	 	                	divHtml +='</td>';
  	                	if((i+1)%5==0){
  	                		divHtml += '</tr><tr>';	                		
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
 	            jQuery("#hrPersonCurrent_photo_list")[0].innerHTML=photoHtml;
 	            jQuery(".left").css({"background-image":"url("+defaultPersonCard+")","background-size":"100% 100%"});	
//  	            var hightMax= jQuery("#hrPersonCurrent_photo_list").height()+jQuery("#hrPersonCurrent_photo_list").offset().top;
//  	            var widthMax= jQuery("#hrPersonCurrent_photo_list").width()+jQuery("#hrPersonCurrent_photo_list").offset().left;
 	            jQuery(".photos-max-img").mousemove(function(event){ 	            	
 	            	var imgsrc= jQuery(this).find("img").attr("src");
 	            	jQuery("#imgphoto").attr("src",imgsrc); 	            	            
 	            	var x = event.clientX;
 	            	var y = event.clientY;
 	            	 var hightMax=jQuery("#hrPersonCurrent_layout-center").offset().top+jQuery("#hrPersonCurrent_photo_list").height();
 	            	var widthMax=jQuery("#hrPersonCurrent_layout-center").offset().left+jQuery("#hrPersonCurrent_photo_list").width();
 	            	var divx=jQuery("#hrPersonCurrent_layout-center").offset().left;
 	            	var divy=jQuery("#hrPersonCurrent_layout-center").offset().top;	
 	            	if(y+200>hightMax){
	            			jQuery(".preview-box").css("top",(y-divy-199-15));
	            			if(y-jQuery("#hrPersonCurrent_layout-center").offset().top<hightMax-y){
	            				jQuery(".preview-box").css("top",(y-divy+10));
	            			}
	            		}else{
	            			jQuery(".preview-box").css("top",(y-divy+10));
	            		} 
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
				   var ztree = $.fn.zTree.getZTreeObj("hrPersonCurrentLeftTree");
	                if(ztree){
	                	var selectNode = ztree.getSelectedNodes();
						if(selectNode && selectNode.length==1 && selectNode[0].subSysTem =='PERSON'){
							var selectid = selectNode[0].id;
							jQuery(this).jqGrid('setSelection',selectid);
						}
	                }
	                
	                /* var userData = jQuery("#hrPersonCurrent_gridtable").getGridParam('userData'); 
	          	    if(userData){
		          	    var addFilters = userData.addFilters;
		    			var queryedPersonIds = userData.queryedPersonIds;
		          	  	var queryedPersonArr ;
		          	  	
		          	  	if(queryedPersonIds.indexOf(",")){
		          	  		queryedPersonArr = queryedPersonIds.split(",");
		          	  	}else if(queryedPersonIds){
		          	  		queryedPersonArr = [queryedPersonIds];
		          	  	}
		    	      	if(addFilters==true){
		          	  		if(!queryedPersonIds){
		          	  			queryedPersonArr = 'null';
		          	  		}
		      	  		}else{
		      	  			queryedPersonArr = 'all';
		      	  		}
		    	      	showIds = queryedPersonArr;  
		          	  	toggleDisabledOrCount({treeId:'hrPersonCurrentLeftTree',
		    	  			showCode:jQuery('#hrPersonCurrent_showCode')[0],
		    	  			disabledDept:jQuery("#hrPersonCurrent_showDisabled")[0],
		    	  			disabledPerson: jQuery("#hrPersonCurrent_showDisabledPerson")[0],
		    	  			showCount:jQuery("#hrPersonCurrent_showPersonCount")[0],
		    	  			showIds:queryedPersonArr
		    	  		});
	          	    } */
	                
	       	} 
	       	
    	});
   	 	
	   	jQuery("#hrPersonCurrent_queryCommon").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT id, name FROM sy_query_common WHERE disabled = 0 order by sn asc",
			exceptnullparent : false,
			lazy : false
		});
	   	
    	jQuery("#hrPersonCurrent_empType").treeselect({
			dataType:"sql",
			optType:"multi",
			sql:"SELECT id,name,parentType parent FROM t_personType where disabled=0  ORDER BY code",
			exceptnullparent:false,
			selectParent:false,
			lazy:false
		});
		
		jQuery("#hrPersonCurrent_postType").treeselect({
			dataType:"sql",
			optType:"multi",
			sql:"SELECT value id ,displayContent name , '1' parent FROM t_dictionary_items WHERE dictionary_id = ( SELECT dictionaryId FROM t_dictionary WHERE code='postType' )",
			exceptnullparent:false,
			selectParent:false,
			lazy:false,
			minWidth :'130px'
		});
    	
    	//实例化ToolButtonBar
		var hrPersonCurrent_menuButtonArrJson = "${menuButtonArrJson}";
    	var hrPersonCurrent_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(hrPersonCurrent_menuButtonArrJson,false)));
    	var hrPersonCurrent_toolButtonBar = new ToolButtonBar({el:$('#hrPersonCurrent_panelBar'),collection:hrPersonCurrent_toolButtonCollection,attributes:{
    	      tableId : 'hrPersonCurrent_gridtable',
    	      baseName : 'hrPersonCurrent',
    	      width : 600,
    	      height : 600,
    	      base_URL : null,
    	      optId : null,
    	      fatherGrid : null,
    	      extraParam : null,
    	      selectNone : "请选择记录。",
    	      selectMore : "只能选择一条记录。",
    	      addTitle : '<s:text name="hrPersonSnapNew.title"/>',
    	      editTitle : null
		}}).render();
    	var hrPersonCurrent_function = new scriptFunction();
    	hrPersonCurrent_function.optBeforeCall = function(e,$this,param){
			var pass = false;
			if(param.checkPeriod){
				if('${yearStarted}'!='true'){
					alertMsg.error("本年度人力资源系统未启用!");
	    			return pass;
				}
			}
	        return true;
		};
    	//add hrPerson
    	hrPersonCurrent_toolButtonBar.addCallBody('1002010101',function(e,$this,param){
    		var url = "editHrPersonSnap?navTabId=hrPersonCurrent_gridtable";
    		var zTree = $.fn.zTree.getZTreeObj("hrPersonCurrentLeftTree");  
    		var nodes = zTree.getSelectedNodes(); 
		    if(nodes.length!=1 || nodes[0].subSysTem =='ALL' || nodes[0].subSysTem =='ORG'){
		    	alertMsg.error("请选择一个部门。");
	      		return;
		    }
		    if(nodes[0].actionUrl == '1'){
		    	alertMsg.error("已停用部门不能添加人员。");
	      		return;
		    }
		    if(nodes[0].subSysTem =='DEPT' && nodes[0].state == 'PARENT'){
		    	alertMsg.error("父级部门不能添加人员。");
	      		return;
		    }
		    if(nodes[0].subSysTem =='PERSON' && nodes[0].getParentNode()){
		    	nodes[0] = nodes[0].getParentNode();
		    }
		    url= url + "&deptId="+nodes[0].id;
			url=encodeURI(url);
			var winTitle='<s:text name="hrPersonSnapNew.title"/>';
			$.pdialog.open(url,'addHrPersonSnap',winTitle, {mask:true,resizable:false,maxable:false,width : 800,height : 600});
		},{});
    	hrPersonCurrent_toolButtonBar.addBeforeCall('1002010101',function(e,$this,param){
			return hrPersonCurrent_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
    	//del hrPerson
    	hrPersonCurrent_toolButtonBar.addCallBody('1002010102',function(e,$this,param){
    	    var url = "${ctx}/hrPersonSnapGridEdit?oper=del"
    	 	var sid = jQuery("#hrPersonCurrent_gridtable").jqGrid('getGridParam','selarrrow');
    	    var opsid = '';
 			if (sid == null || sid.length == 0) {
 				alertMsg.error("请选择记录。");
 				return;
 			} else {
 				$.ajax({
 				    url: "checkDelPerson?personIds="+sid,
 				    type: 'post',
 				    dataType: 'json',
 				    aysnc:false,
 				    error: function(data){
 				    },
 				    success: function(data){
 				        if(data!=null){
 				        	alertMsg.error(data.message);
 							return;
 				        }else{
 				var rowData;
 				var deptIdArray=new Array();
 				for(var i=0;i<sid.length;i++){
 					rowData = jQuery("#hrPersonCurrent_gridtable").jqGrid('getRowData',sid[i]);
 					if(rowData["disable"]=='No'){
 						alertMsg.error("只能删除已停用的人员！");
 						return;
 					}
 					opsid = sid[i] + '_'+rowData['snapCode']+",";
 					var deptId=rowData['department.departmentId'];
 					deptIdArray[i]=deptId;
 				} 
 				url = url+"&id="+opsid+"&navTabId=hrPersonCurrent_gridtable";
 				alertMsg.confirm("确认删除？", {
 					okCall : function() {
 						$.post(url,function(data) {
 							formCallBack(data);
 							if(data.statusCode!=200){
 						       return;
 						      }
    						reloadHrPersonCurrentGrid();
    						for(var i=0;i<sid.length;i++){
    							dealHrTreeNode('hrPersonCurrentLeftTree',{id:sid[i]},'del','person');
							}
 							/* if(data.statusCode==200){
 								var showPersonCount = jQuery("#hrPersonCurrent_showPersonCount").attr("checked");
 								var hrDeptTreeObj = $.fn.zTree.getZTreeObj("hrPersonCurrentLeftTree");
 								for (deptIdArrayIndex in deptIdArray)
 								{
 									var updateNode = hrDeptTreeObj.getNodeByParam("id", deptIdArray[deptIdArrayIndex], null);
 									updateHrDeptPersonCountSubOne("hrPersonCurrentLeftTree",updateNode,showPersonCount);
 								}
 							} */
 						});
 					}
 				});
 				        }}});
 			}
		},{});
    	hrPersonCurrent_toolButtonBar.addBeforeCall('1002010102',function(e,$this,param){
			return hrPersonCurrent_function.optBeforeCall(e,$this,param);
    	},{checkPeriod:"checkPeriod"});
   	    //edit hrPerson
   	    hrPersonCurrent_toolButtonBar.addCallBody('1002010103',function(e,$this,param){
    		var sid = jQuery("#hrPersonCurrent_gridtable").jqGrid('getGridParam','selarrrow');
	   	 	if(sid==null|| sid.length != 1){       	
				alertMsg.error("请选择一条记录。");
				return;
			}else{
    			var row = jQuery("#hrPersonCurrent_gridtable").jqGrid('getRowData',sid[0]);
    			sid += '_'+row['snapCode']; 
    		}
	 		var winTitle='<s:text name="hrPersonSnapEdit.title"/>';
 			var url = "editHrPersonSnap?snapId="+sid+"&navTabId=hrPersonCurrent_gridtable";
 			$.pdialog.open(url,'editHrPersonSnap',winTitle, {mask:true,resizable:false,maxable:false,width : 800,height : 600});
   	    },{});
	   	 hrPersonCurrent_toolButtonBar.addBeforeCall('1002010103',function(e,$this,param){
				return hrPersonCurrent_function.optBeforeCall(e,$this,param);
	 	},{checkPeriod:"checkPeriod"});
   	     //batchEdit hrPerson
   	    hrPersonCurrent_toolButtonBar.addCallBody('1002010104',function(e,$this,param){
   	    	var sid = jQuery("#hrPersonCurrent_gridtable").jqGrid('getGridParam','selarrrow');
   	    	var snapId="";
   	        if(sid==null|| sid.length ==0){
   	        	alertMsg.error("请选择记录。");
   	 			return;
 			}else {	
 				var rowData;
 				for(var i=0;i<sid.length;i++){
 					rowData = jQuery("#hrPersonCurrent_gridtable").jqGrid('getRowData',sid[i]);
 					if(i==0){
 						snapId=sid[i]+rowData['snapCode'];
 					}else{
 						snapId+=","+sid[i]+rowData['snapCode'];
 					}
 				} 
 			}
   	 		var winTitle='<s:text name="hrPersonBatchEdit.title"/>';
   	 		var url = "hrPersonBatchEdit?id="+sid+"&navTabId=hrPersonCurrent_gridtable";
   	 		$.pdialog.open(url,'batchEditHrPersonSnap',winTitle, {mask:true,width : 800,height : 600});
   	    },{});
   	 	hrPersonCurrent_toolButtonBar.addBeforeCall('1002010104',function(e,$this,param){
			return hrPersonCurrent_function.optBeforeCall(e,$this,param);
		},{checkPeriod:"checkPeriod"});
   	    //照片 、列表
    	hrPersonCurrent_toolButtonBar.addCallBody('1002010105',function(e,$this,param){
    		if(jQuery("#hrPersonCurrent_gridtable_div :visible").length>0){
	    		jQuery("#hrPersonCurrent_gridtable_div").hide();
		    	jQuery("#hrPersonCurrent_photo_list").show();
    		}else{
	    		jQuery("#hrPersonCurrent_gridtable_div").show();
	    		jQuery("#hrPersonCurrent_photo_list").hide();
    		}
    	},{}); 
		//人事异动
    	hrPersonCurrent_toolButtonBar.addCallBody('1002010106',function(e,$this,param){
    		var buttonId = "button_1002010106";
       	    var containerId = 'hrPersonCurrent_page';
       	    addSelectButton(buttonId,containerId,[{id:'hrPersonCurrentEntry',name:'人员入职',className:'edit',callBody:function(e,$$this){
       	    	var zTree = $.fn.zTree.getZTreeObj("hrPersonCurrentLeftTree");  
 			    var nodes = zTree.getSelectedNodes(); 
 			   if(nodes.length!=1 || nodes[0].subSysTem =='ALL' || nodes[0].subSysTem =='ORG'){
 			    	alertMsg.error("请选择一个部门。");
 		      		return;
 			    }
 			    if(nodes[0].actionUrl == '1'){
 			    	alertMsg.error("已停用部门不能添加人员。");
 		      		return;
 			    }
 			    if(nodes[0].subSysTem =='DEPT' && nodes[0].state == 'PARENT'){
 			    	alertMsg.error("父级部门不能添加人员。");
 		      		return;
 			    }
 			    if(nodes[0].subSysTem =='PERSON' && nodes[0].getParentNode()){
 			    	nodes[0] = nodes[0].getParentNode();
 			    }
       	    	var url = "editPersonEntry?navTabId=hrPersonCurrent_gridtable&deptId="+nodes[0].id;
       	 	    url+="&oper=done";
       			var winTitle='<s:text name="personEntryNew.title"/>';
       			$.pdialog.open(url,'addPersonEntry',winTitle, {mask:true,width : 700,height : 600});
       	      }},{id:'hrPersonCurrentDeptMove',name:'人员调动',className:'edit',callBody:function(e,$$this){
       	    	var sid = jQuery("#hrPersonCurrent_gridtable").jqGrid('getGridParam','selarrrow');
		   	 	if(sid==null|| sid.length != 1){       	
					alertMsg.error("请选择一条记录。");
					return;
				} 
       	   		var url = "editSysPersonMove?navTabId=hrPersonCurrent_gridtable&personId="+sid;
       	 	    url+="&oper=done";
				var winTitle='<s:text name="sysPersonMoveNew.title"/>';
				$.pdialog.open(url,'addSysPersonMove',winTitle, {mask:true,width : 666,height : 400,maxable:false,resizable:false});
       	      }},{id:'hrPersonCurrentPostMove',name:'人员调岗',className:'edit',callBody:function(e,$$this){
       	    	var sid = jQuery("#hrPersonCurrent_gridtable").jqGrid('getGridParam','selarrrow');
		   	 	if(sid==null|| sid.length != 1){       	
					alertMsg.error("请选择一条记录。");
					return;
				} 
		   	 	var url = "editSysPostMove?navTabId=hrPersonCurrent_gridtable&personId="+sid;
       	 	    url+="&oper=done";
				var winTitle='<s:text name="sysPostMoveNew.title"/>';
				$.pdialog.open(url,'addSysPostMove',winTitle, {mask:true,width : 666,height : 382,maxable:false,resizable:false});
       	      }},{id:'hrPersonCurrentLeave',name:'人员离职',className:'edit',callBody:function(e,$$this){
       	    	var sid = jQuery("#hrPersonCurrent_gridtable").jqGrid('getGridParam','selarrrow');
		   	 	if(sid==null|| sid.length != 1){       	
					alertMsg.error("请选择一条记录。");
					return;
				} else {	
	 				var rowData;
	 				for(var i=0;i<sid.length;i++){
	 					rowData = jQuery("#hrPersonCurrent_gridtable").jqGrid('getRowData',sid[i]);
	 					if(rowData["status.name"]=='离职'){
	 						alertMsg.error("请选择处于非离职状态的记录");
	 						return;
	 					}
	 				} 
				}
		   	 	var url = "editSysPersonLeave?navTabId=hrPersonCurrent_gridtable&personId="+sid;
       	 	    url+="&oper=done";
				var winTitle='<s:text name="sysPersonLeaveNew.title"/>';
				$.pdialog.open(url,'addSysPersonLeave',winTitle, {mask:true,width : 666,height : 352,maxable:false,resizable:false});
       	      }}]); 
    	     },{});
    	   	 //停用/启用
    	     hrPersonCurrent_toolButtonBar.addCallBody('1002010107',function(e,$this,param){
    	    	 var buttonId = "button_1002010107";
    	    	 var containerId = 'hrPersonCurrent_page';
          	      addSelectButton(buttonId,containerId,[{id:'hrPersonCurrentEnable',name:'启用',className:'edit',callBody:function(e,$$this){
          	    	var sid = jQuery("#hrPersonCurrent_gridtable").jqGrid('getGridParam','selarrrow');
          	    	var opsid = '';
          	  	    var rowData;
          	  		if(sid==null|| sid.length == 0){       	
						alertMsg.error("请选择记录。");
						return;
					}
          	    	for(var i=0;i<sid.length;i++){
        				rowData = jQuery("#hrPersonCurrent_gridtable").jqGrid('getRowData',sid[i]);
        				if(rowData["disable"]=='No'){
        					alertMsg.error("你选择的人员已启用！");
        					return;
        				}
        				opsid += sid[i] + '_'+rowData['snapCode']+',';
          	    	}
          	    var url = "${ctx}/hrPersonSnapGridEdit?oper=enable";
          	  	url = url+"&id="+opsid+"&navTabId=hrPersonCurrent_gridtable";
    			alertMsg.confirm("确认启用？", {
    				okCall : function() {
    					$.post(url,function(data) {
    						formCallBack(data);
    						if(data.statusCode!=200){
  						       return;
  						      }
    						reloadHrPersonCurrentGrid();
    						for(var i=0;i<sid.length;i++){
    							dealHrTreeNode('hrPersonCurrentLeftTree',{id:sid[i]},'enable','person');
							}
    						//hrPersonCurrentLeftTree();
    					});
    				}
    			});
          	      }},
          	    {id:'hrPersonCurrentDisable',name:'停用',className:'edit',callBody:function(e,$$this){
          	    	var sid = jQuery("#hrPersonCurrent_gridtable").jqGrid('getGridParam','selarrrow');
          	    	var opsid = '';
          	  		var rowData;
          	  		if(sid==null|| sid.length == 0){       	
						alertMsg.error("请选择记录。");
						return;
					}
          	    	for(var i=0;i<sid.length;i++){
        				rowData = jQuery("#hrPersonCurrent_gridtable").jqGrid('getRowData',sid[i]);
        				if(rowData["disable"]=='Yes'){
        					alertMsg.error("你选择的人员已停用！");
        					return;
        				}
        				opsid += sid[i] + '_'+rowData['snapCode']+',';
          	    	}
          	    var url = "${ctx}/hrPersonSnapGridEdit?oper=disable";
          	  	url = url+"&id="+opsid+"&navTabId=hrPersonCurrent_gridtable";
    			alertMsg.confirm("确认停用？", {
    				okCall : function() {
    					$.post(url,function(data) {
    						formCallBack(data);
    						if(data.statusCode!=200){
  						       return;
  						      }
    						reloadHrPersonCurrentGrid();
    						for(var i=0;i<sid.length;i++){
    							dealHrTreeNode('hrPersonCurrentLeftTree',{id:sid[i]},'disable','person');
							}
    						//hrPersonCurrentLeftTree();
    					});
    				}
    			});
          	    }}]); 
  			 },{});
    	     hrPersonCurrent_toolButtonBar.addBeforeCall('1002010107',function(e,$this,param){
    				return hrPersonCurrent_function.optBeforeCall(e,$this,param);
    			},{checkPeriod:"checkPeriod"});
    	     //导入
    	     hrPersonCurrent_toolButtonBar.addCallBody('1002010108',function(e,$this,param){
    	    	 var winTitle='<s:text name="hrPersonExcelImport.title"/>';
       	 		 var url = "hrPersonExcelImport?navTabId=hrPersonCurrent_gridtable&oper=hrPerson";
       	 		 $.pdialog.open(url,'importHrPersonSnap',winTitle, {mask:true,width : 600,height : 150});
		     },{});
    	   //设置表格列
    	     var hrPersonCurrent_setColShowButton = {id:'1002010109',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
    	       callBody:function(){
    	        setColShow('hrPersonCurrent_gridtable','com.huge.ihos.hr.hrPerson.model.HrPersonSnap');
    	       }};
    	     hrPersonCurrent_toolButtonBar.addButton(hrPersonCurrent_setColShowButton);//实例化ToolButtonBar
  		
		/* jQuery("#hrPersonCurrent_layout-west").scroll(function(e){
			
		}); */
		var windowHeight = jQuery("#hrPersonCurrent_layout-west").height();
		var extraHeight = 2*windowHeight;
		var upExtraHeight = 150 - extraHeight , downExtraHeight = extraHeight , remainHeight = extraHeight;
		var widnowTopCurrent = 0;
		var scrollTimer ;
		jQuery("#hrPersonCurrent_layout-west").scroll(function(e){
			//var treeObj = $.fn.zTree.getZTreeObj("hrPersonCurrentLeftTree");
			var widnowTop = jQuery("#hrPersonCurrent_layout-west").scrollTop();
			//var rootNode = treeObj.getNodes()[0];
			if(widnowTop>widnowTopCurrent){
				clearTimeout(scrollTimer);
				scrollTimer = setTimeout(function(){
					downScroll();
				},100);
			}else{
				clearTimeout(scrollTimer);
				scrollTimer = setTimeout(function(){
					upScroll();
				},100);
			}
			widnowTopCurrent = widnowTop;
			/* var expandArr = new Array();
			jQuery("#hrPersonCurrentLeftTree").find("li[opened!=true]").each(function(i){
				var nodeLi = jQuery(this);
				var nodeTop = nodeLi.offset().top;
				if(nodeTop<downExtraHeight){
					var nodeId = nodeLi.attr("id");
					var expendNode = treeObj.getNodeByTId(nodeId);
					expandNode(expendNode,expandArr,remainHeight);
				}else{
					return false;
				}
			}); */
			/* jQuery("#hrPersonCurrentLeftTree").find("li").each(function(i){
				var nodeLi = jQuery(this);
				var nodeTop = nodeLi.offset().top;
				var nodeId = nodeLi.attr("id");
				var expendNode = treeObj.getNodeByTId(nodeId);
				if(nodeTop<downExtraHeight){
					expandNode(expendNode,expandArr,remainHeight);
				}else{
					return false;
				}
				/* else if(nodeTop>(downExtraHeight+extraHeight)&&expendNode.open){
					treeObj.expandNode(expendNode,false,true,false,false);
					nodeLi.find('ul').remove();
				} */
			/*}); */
			/* for(var nodeIdnex=0;nodeIdnex<expandArr.length;nodeIdnex++){
				jQuery("#"+expandArr[nodeIdnex].tId).attr("opened",true);
				treeObj.expandNode(expandArr[nodeIdnex],true,false,false,false);
			} */
			/* var expandArr = new Array();
			var windowHeight = jQuery("#hrPersonCurrent_layout-west").height();
			remainHeight = 2*windowHeight;
			expandNode(rootNode,expandArr,remainHeight);
			for(var nodeIdnex=0;nodeIdnex<expandArr.length;nodeIdnex++){
				treeObj.expandNode(expandArr[nodeIdnex],true,false,false,false);
			} */
		});
		
		/* function downScroll(){
			console.log("down");
			var treeObj = $.fn.zTree.getZTreeObj("hrPersonCurrentLeftTree");
			var expandArr = new Array();
			jQuery("#hrPersonCurrentLeftTree").find("li[opened!=true]").each(function(i){
				var nodeLi = jQuery(this);
				var nodeTop = nodeLi.offset().top;
				if(nodeTop<=downExtraHeight){
					var nodeId = nodeLi.attr("id");
					var expendNode = treeObj.getNodeByTId(nodeId);
					expandNode(expendNode,expandArr,remainHeight);
				}else{
					return false;
				}
			});
			//alert(expandArr.length);
			for(var nodeIdnex=0;nodeIdnex<expandArr.length;nodeIdnex++){
				jQuery("#"+expandArr[nodeIdnex].tId).attr("opened",true);
				treeObj.expandNode(expandArr[nodeIdnex],true,false,false,false);
			}
		} */
		
		/* function upScroll(){
			console.log("up");
			var treeObj = $.fn.zTree.getZTreeObj("hrPersonCurrentLeftTree");
			var openedLis = jQuery("#hrPersonCurrentLeftTree").find("li[opened='true']");
			jQuery("#hrPersonCurrentLeftTree").find("li[opened=true]").each(function(i){
				var nodeLi = openedLis.eq(openedLis.length-1-i);
				var nodeTop = nodeLi.offset().top;
				if(nodeTop>downExtraHeight){
					var nodeId = nodeLi.attr("id");
					var expendNode = treeObj.getNodeByTId(nodeId);
					nodeLi.attr("opened",false);
					treeObj.expandNode(expendNode,false,true,false,false);
					nodeLi.find('ul').remove();
				}else{
					return false;
				}
			});
		} */
		
		var $scrollbar  = $('#scrollbar1')
        ,   $overview   = $scrollbar.find(".overview")
        ,   loadingData = false
        ;
		var layoutWidth = jQuery("#hrPersonCurrent_layout-west").width();
		var layoutHeight = jQuery("#hrPersonCurrent_layout-west").height();
		$scrollbar.width(layoutWidth-10);
		$scrollbar.find(".viewport").width(layoutWidth-30);
		$scrollbar.find(".viewport").height(layoutHeight-85);
		$scrollbar.tinyscrollbar({thumbSize : 80});
		var scrollbarData = $scrollbar.data("plugin_tinyscrollbar");

		var numLiView = Math.floor(scrollbarData.viewportSize/20);
		var scrollTimer ,scrollBarPosition = 0;
        $scrollbar.bind("move", function(){
        	var contentPosition = scrollbarData.contentPosition;
        		clearTimeout(scrollTimer);
        		scrollTimer = setTimeout(function(){downScroll(scrollbarData);},20);
        	if(contentPosition>scrollBarPosition){
        	}else{
        		upScroll(scrollbarData);
        	}
        	scrollBarPosition = contentPosition;
        	/* clearTimeout(scrollTimer);
			scrollTimer = setTimeout(function(){
				doScroll(scrollbarData);
			},100); */
        	
        	//console.log(scrollbarData.contentPosition+":"+scrollbarData.viewportSize);
        });
	});
	function upScroll(scrollbarData){
		
	}
	function findExistLi(num,direct,total){
		var nodeLi = jQuery("#hrPersonCurrentLeftTree_"+num);
		if(nodeLi.length>0){
			return nodeLi;
		}else if(num<0){
			num = 1;
			nodeLi = findExistLi(num,direct,total);
		}else if(num>total){
			num = total;
			nodeLi = findExistLi(num,direct,total);
		}else{
			if(direct=='up'){
				num--;
			}else{
				num++;
			}
			nodeLi = findExistLi(num,direct,total);
		}
		return nodeLi;
	}
	function downScroll(scrollbarData){
		//debugger;
		//	console.log("downScroll");
		var treeObj = $.fn.zTree.getZTreeObj("hrPersonCurrentLeftTree");
    	var updatePosition = scrollbarData.contentPosition;
    	//console.log("contentPosition"+scrollbarData.contentPosition);
    	var positionStart = -scrollbarData.viewportSize + scrollbarData.contentPosition;
    	var positionEnd = scrollbarData.contentPosition + 2*scrollbarData.viewportSize;
    	var totalNum = jQuery("#hrPersonCurrentLeftTree").data("totalNum");
    	var startLiNum = Math.floor(positionStart/20);
    	var endLiNum = Math.floor(positionEnd/20);
    	var hideAreaLiNum = startLiNum;
    	var unFoldAreaLiNum = endLiNum;
    	var unFoldAreaHeight = (positionEnd - positionStart);
    	
    	var removeUlArr = new Array();
    	var expandArr = new Array();
    	var allNodeLi = jQuery("#hrPersonCurrentLeftTree").find("li");
    	var liIndex = 0 , allNodeLiLength = allNodeLi.length , liPointer = 0;
    	if(!totalNum){
    		totalNum = allNodeLiLength;
    	}else{
    		totalNum = parseInt(totalNum);
    	}
    	/* debugger;
    	var startNodeLi = findExistLi(startLiNum,'up',allNodeLi.length);
    	var endNodeLi = findExistLi(endLiNum,'down',allNodeLi.length);
    	
    	var startLiIndex = allNodeLi.index(startLiIndex);
    	var endLiIndex = allNodeLi.index(endLiIndex);
    	
    	console.log("startLiIndex:"+startLiIndex+";endLiIndex:"+endLiIndex);
    	return ; */
    	while(liIndex<allNodeLiLength){
   			var nodeLi = allNodeLi.eq(liIndex);
    		if(liPointer<startLiNum){
    			hideAreaLiNum -= 1;
				var ulRemoved = nodeLi.attr("ulRemoved");
				if(ulRemoved){
					//liIndex += parseInt(ulRemoved);
					liIndex++;
					liPointer++;
					var parentUlRemoved = nodeLi.parent().parent().attr("ulRemoved");
					if(!parentUlRemoved){
						liPointer += parseInt(ulRemoved);
					}
					hideAreaLiNum -= parseInt(ulRemoved);
					if(startLiNum<liPointer){
						liIndex--;
						liPointer--;
						liPointer -= parseInt(ulRemoved);
						startLiNum -= parseInt(ulRemoved)+1;
						unFoldAreaHeight = (endLiNum - startLiNum)*20;
					}
					continue;
				}
				var nodeId = nodeLi.attr("id");
				var expendNode = treeObj.getNodeByTId(nodeId);
				var childrenNum = 0;
				if(expendNode.subSysTem!='ALL'&&expendNode.subSysTem!='ORG'&&expendNode.isParent){
					childrenNum = getNodeChildrenNum(expendNode);
					if(childrenNum <= hideAreaLiNum){
    					//console.log("fold:"+expendNode.name+nodeId+";childrenNum:"+childrenNum+":liPointer:"+liPointer);
    					removeUlArr.push(nodeId);
	    				hideAreaLiNum -= childrenNum;
	    				if(expendNode.open){
		   					liIndex += childrenNum;
	    				}
	   					liPointer += childrenNum;
    					nodeLi.attr("ulRemoved",childrenNum);
    					nodeLi.height(childrenNum*20+20);
    					//treeObj.expandNode(expendNode,false,true,false,false);
    					//updatePosition -= childrenNum*20;
    				}
    			}
    		}else if(startLiNum<=liPointer&&liPointer<=unFoldAreaLiNum){
    			//console.log("startUnfold:"+nodeLi.find("a").attr("title"));
    			mustUnFoldAreaHeight = Math.floor(unFoldAreaHeight/2);
    			findExpendNode(nodeLi,treeObj,expandArr,unFoldAreaHeight,mustUnFoldAreaHeight);
    			liPointer = unFoldAreaLiNum;
    			
    		}else{
    			//
    			//debugger;
    			var nodeId = nodeLi.attr("id");
    			var nodeNum = nodeId.split("_")[1];
    			nodeNum = parseInt(nodeNum);
    			if(nodeNum<liPointer){
    				liIndex++;
    				continue;
    			}
    			var ulRemoved = nodeLi.attr("ulRemoved");
    			if(ulRemoved){
    				liIndex++;
					continue;
				}
				var expendNode = treeObj.getNodeByTId(nodeId);
				var childrenNum = 0;
				if(expendNode.subSysTem!='ALL'&&expendNode.subSysTem!='ORG'&&expendNode.isParent){
					childrenNum = getNodeChildrenNum(expendNode);
   					removeUlArr.push(nodeId);
   					if(expendNode.open){
	   					liIndex += childrenNum;
    				}
					nodeLi.attr("ulRemoved",childrenNum);
					nodeLi.height(childrenNum*20+20);
    			}
    		}
    		liIndex++;
    		liPointer++;
    	}
    	//trimExpendArr(expandArr);
			//alert(expandArr.length);
    	for(var nodeIdnex=0;nodeIdnex<expandArr.length;nodeIdnex++){
    		var liId = expandArr[nodeIdnex].tId;
    		dealNodeDom(expandArr[nodeIdnex]);
    		dealParentNodeDom(expandArr[nodeIdnex]);
    		/* if(expandArr[nodeIdnex].open&&!ulRemoved){
				continue;
			} */
			//console.log("unfold:"+expandArr[nodeIdnex].name);
			treeObj.expandNode(expandArr[nodeIdnex],true,true,false,false);
		}
    	for(var ulIndex in removeUlArr){
			var romveUlNodeId = removeUlArr[ulIndex];
			var romveUlNode = jQuery("#"+romveUlNodeId);
    		//console.log("fold:"+romveUlNode.find("a").attr("title"));
			romveUlNode.find('ul').remove();
		}
		/* for(var liIndex=0;liIndex<allNodeLi.length;liIndex++){
			
		} */
    	
    	//startLiNum = -1;
    	//var lastCloseIndex = scrollbarData.lastCloseIndex;
    	//var remainHeight = ( startLiNum - lastCloseIndex )*20;
    	/* if(remainHeight>0){
    		var removeUlArr = new Array();
    		jQuery("#hrPersonCurrentLeftTree").find("li").each(function(i){
    			if(i>=startLiNum){
    				return false;
    			}else{
    				var nodeLi = jQuery(this);
    				var ulRemoved = nodeLi.attr("ulRemoved");
    				if(ulRemoved){
    					startLiNum -= parseInt(ulRemoved);
    					remainliNum -= parseInt(ulRemoved);
    					return true;
    				}
    				var nodeId = nodeLi.attr("id");
    				var expendNode = treeObj.getNodeByTId(nodeId);
    				var childrenNum = 1;
    				if(expendNode.subSysTem!='ALL'&&expendNode.subSysTem!='ORG'&&expendNode.isParent){
    				childrenNum = getNodeChildrenNum(treeObj,expendNode,childrenNum);
    				//console.log(childrenNum+":"+remainliNum);
    				if(childrenNum <= remainliNum){
    					remainliNum -= childrenNum;
    					//console.log("fold:"+expendNode.name);
    					removeUlArr.push(nodeLi.find("ul"));
    					nodeLi.attr("ulRemoved",childrenNum);
    					nodeLi.height(childrenNum*20+20);
    					//treeObj.expandNode(expendNode,false,true,false,false);
    					updatePosition -= childrenNum*20;
    				}else{
    					remainliNum -= childrenNum;
    				}
    				}
    			}
    		});
    		for(var ulIndex in removeUlArr){
    			removeUlArr[ulIndex].remove();
    		}
    	}
    	startLiNum = Math.floor(positionStart/20);
    	var endLiNum = Math.floor(positionEnd/20);
    	//scrollbarData.lastOpenLi = expandArr[nodeIdnex].tId;
		var lastOpenIndex = scrollbarData.lastOpenIndex;
		var remainHeight = (positionEnd - positionStart);
		//remainHeight =-1;
		if(remainHeight>0){
			var lastNodeLi ,liPointer=0;
			jQuery("#hrPersonCurrentLeftTree").find("li").each(function(i){
				var nodeLi = jQuery(this);
				console.log(liPointer+":"+startLiNum);
				if(liPointer==startLiNum){
					lastNodeLi = nodeLi;
					return false;
				}
				var ulRemoved = nodeLi.attr("ulRemoved");
				if(ulRemoved){
					liPointer += parseInt(ulRemoved)+1;
					return true;
				}else{
					liPointer += 1;
				}
			});
			//var nodeId = scrollbarData.lastOpenLi;
			var expandArr = new Array();
			//debugger;
			findExpendNode(lastNodeLi,treeObj,expandArr,remainHeight);
			for(var nodeIdnex=0;nodeIdnex<expandArr.length;nodeIdnex++){
				if(expandArr[nodeIdnex].open){
					continue;
				}
				treeObj.expandNode(expandArr[nodeIdnex],true,false,false,false);
				scrollbarData.lastOpenLi = expandArr[nodeIdnex].tId;
				scrollbarData.lastOpenIndex = 0;
			}
		} */
    	/* var remainliNum = 0;
    	if(endLiNum>0){
    		jQuery("#hrPersonCurrentLeftTree").find("li").each(function(i){
    			if(i>=endLiNum){
    				return false;
    			}else{
    				var nodeLi = jQuery(this);
    				var nodeId = nodeLi.attr("id");
    				var expendNode = treeObj.getNodeByTId(nodeId);
    				var childrenNum = 1;
    				if(expendNode.subSysTem!='ALL'&&expendNode.subSysTem!='ORG'&&!expendNode.open&&expendNode.isParent){
    					childrenNum = getNodeChildrenNum(treeObj,expendNode,childrenNum);
    					/* if(remainliNum<){
    						remainliNum += childrenNum;
    					} 
    					updatePosition += childrenNum*20;
    					console.log("unfold:"+expendNode.name+":childrenNum:"+childrenNum);
    					treeObj.expandNode(expendNode,true,true,false,false);
    				}
    			}
    		});
    	} */
    	//downScroll();
		//scrollbarData.update("relative");
    	/* setTimeout(function(){
			var scrollbarData = jQuery('#scrollbar1').data("plugin_tinyscrollbar");
			console.log("updatePosition"+updatePosition);
		},100); */
		//console.log("end_downScroll");
	}
	
/* 	function findChildrenLi(nodeLi){
		var liNum = 0;
		if(nodeLi){
			var childrenUl = nodeLi.find('ul');
			var childrenLi = childrenUl.find("li");
			if(){
				
			}
		}
	} */
	
	function findExpendNode(lastNodeLi,treeObj,expandArr,remainHeight,mustUnFoldAreaHeight){
		//debugger;
		var nodeId = lastNodeLi.attr("id");
		if(nodeId){
			var expendNode = treeObj.getNodeByTId(nodeId);
			if(expendNode.subSysTem=="PERSON"){
				var pNode = expendNode.getParentNode();
				pNode.mustShow = true;
				lastNodeLi = lastNodeLi.parent().parent();
				var pId = pNode.tId;
				var cId = expendNode.tId;
				var pNum = parseInt(pId.split("_")[1]);
				var cNum = parseInt(cId.split("_")[1]);
				var etraHeight = (cNum - pNum)*20;
				remainHeight += etraHeight;
				remainHeight = expandNode(pNode,expandArr,remainHeight,mustUnFoldAreaHeight);
			}else{
				remainHeight = expandNode(expendNode,expandArr,remainHeight,mustUnFoldAreaHeight);
			}
			if(remainHeight>0){
				var nextNodeLi = lastNodeLi.next('li');
				var nextNodeLiId = nextNodeLi.attr("id");
				if(nextNodeLiId){
					findExpendNode(nextNodeLi,treeObj,expandArr,remainHeight,mustUnFoldAreaHeight);
				}else{
					var lastNodeLiParent = lastNodeLi.parent().parent().next('li');
					if(lastNodeLiParent.is('li')){
						findExpendNode(lastNodeLiParent,treeObj,expandArr,remainHeight,mustUnFoldAreaHeight)
					}
				}
			}else{
				return lastNodeLi;
			}
		}
		/* function trimExpendArr(expandArr){
			var trimedExpendArr = new Array();
			for(var i=0;i<expandArr.length;i++){
				var node = expandArr[i];
				if(node&&node.subSysTem=='DEPT'){
					trimedExpendArr.push(node);
					var nodelChildren = node.children
					
				}
			}
		} */
		/* var nextNodeLi = lastNodeLi.next('li');
		var nextNodeLiId = nextNodeLi.attr("id");
		if(nextNodeLiId){
			var expendNode = treeObj.getNodeByTId(nextNodeLiId);
			remainHeight = expandNode(expendNode,expandArr,remainHeight);
			if(remainHeight>0){
				findExpendNode(nextNodeLi,treeObj,expandArr,remainHeight)
			}
			
		}else if(remainHeight>0){
			var lastNodeLiParent = lastNodeLi.parent().parent();
			if(lastNodeLiParent.is('li')){
				findExpendNode(lastNodeLiParent,treeObj,expandArr,remainHeight)
			}
		} */
		
	}
	function dealNodeDom(nodeDom){
		var liId = nodeDom.tId;
		var liHeight = jQuery("#"+liId).height();
		if(liHeight>20){
			nodeDom.open = false;
		}
		jQuery("#"+liId).removeAttr("ulRemoved");
		jQuery("#"+liId).attr("childrenDeal");
		if(nodeDom.isParent){
			var childenNodes = nodeDom.children;
			if(!childenNodes){
				return ;
			}
			if(childenNodes[0].subSystem=='PERSON'){
				return ;
			}
			for(var i=0;i<childenNodes.length;i++){
				dealNodeDom(childenNodes[i])
			}
		}
	}
	function dealParentNodeDom(nodeDom){
		var pNode = nodeDom.getParentNode();
		if(!pNode){
			return ;
		}
		var liId = pNode.tId;
		var liHeight = jQuery("#"+liId).height();
		if(liHeight>20){
			pNode.open = false;
		}
		jQuery("#"+liId).removeAttr("ulRemoved");
		jQuery("#"+liId).attr("parentDeal");
		dealParentNodeDom(pNode);
	}
	function hrPersonCurrentLeftTree() {
		//var url = "makeHrDeptTree";
		var url = "makeHrPersonTree?";
		$.get(url, {"_" : $.now()}, function(data) {
			var hrDeptTreeData = data.hrPersonTreeNodes;
			var hrDeptTree = $.fn.zTree.init($("#hrPersonCurrentLeftTree"),ztreesetting_hrPersonTree, hrDeptTreeData);
			var nodes = hrDeptTree.getNodes();
			hrDeptTree.expandNode(nodes[0], true, false, true);
			hrDeptTree.selectNode(nodes[0]);
			var totalNum = 0;
			totalNum = getNodeChildrenNum(nodes[0]);
			jQuery("#hrPersonCurrentLeftTree").data("totalNum",totalNum);
			jQuery("#hrPersonCurrentLeftTree").height(totalNum*20+20);
			/* toggleDisabledOrCount({treeId:'hrPersonCurrentLeftTree',
		         showCode:jQuery('#hrPersonCurrent_showCode')[0],
		         disabledDept:jQuery("#hrPersonCurrent_showDisabled")[0],
		         disabledPerson:jQuery("#hrPersonCurrent_showDisabledPerson")[0],
		         showCount:jQuery("#hrPersonCurrent_showPersonCount")[0] }); */
		});
		jQuery("#hrPersonCurrent_expandTree").text("展开");
	}
	var ztreesetting_hrPersonTree = {
		view : {
			dblClickExpand : false,
			showLine : true,
			selectedMulti : false,
			fontCss : setDisabledDeptFontCss
		},
		callback : {
			beforeDrag:function(){return false},
			onClick : function(event, treeId, treeNode, clickFlag){
				var urlString = "hrPersonCurrentGridList?1=1";
			    var nodeId = treeNode.id;
			    if(nodeId!="-1"){
			    	if(treeNode.subSysTem==='ORG'){
				    	urlString += "&orgCode="+nodeId;
			    	}else if(treeNode.subSysTem==='DEPT'){
				    	urlString += "&departmentId="+nodeId;
			    	}else{
			    		urlString += "&personId="+nodeId;
			    	}
			    }
			    var showDisabled = jQuery("#hrPersonCurrent_showDisabled").attr("checked");
			    if(showDisabled){
			    	urlString += "&showDisabled=true";
			    }
				urlString=encodeURI(urlString);
				jQuery("#hrPersonCurrent_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
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
	function showDisabledDeptInPerson(obj){
		var postData = jQuery("#hrPersonCurrent_gridtable").jqGrid('getGridParam',"postData");
		if(!obj.checked){
			postData['filter_EQB_department.disabled'] = false;
		}else{
			delete postData['filter_EQB_department.disabled'];
		}
		reloadHrPersonCurrentGrid();
	}
	function showDisabledPerson(obj){
		var postData = jQuery("#hrPersonCurrent_gridtable").jqGrid('getGridParam',"postData");
		if(!obj.checked){
			postData['filter_EQB_disable'] = false;
		}else{
			delete postData['filter_EQB_disable'];
		}
		reloadHrPersonCurrentGrid();
	}
/* 	function showDisabledDeptInPerson(){
		var urlString = jQuery("#hrPersonCurrent_gridtable").jqGrid('getGridParam',"url");
		urlString = urlString.replace('showDisabled','');
		urlString = urlString.replace('showDisabledPerson','');
		var showDisabledDept = jQuery("#hrPersonCurrent_showDisabled").attr("checked");
		var showDisabledPerson = jQuery("#hrPersonCurrent_showDisabledPerson").attr("checked");
		if(showDisabledDept){
			urlString += "&showDisabled=true";
		}
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=true";
		}
		 urlString=encodeURI(urlString);
		jQuery("#hrPersonCurrent_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		hpcTreeReShow();
	} */
	//photo list person selected
	function selectRecord(rowid,obj){
		var sid = jQuery("#hrPersonCurrent_gridtable").jqGrid('getGridParam','selarrrow');
		var rowIds = sid;
	    if(sid==null){
		}else{
			jQuery('.photoSelected').removeClass('photoSelected');
			jQuery(obj).addClass('photoSelected');
			var sumLength = rowIds.length;
			for(var num=0;num<sumLength;num++){
				jQuery("#hrPersonCurrent_gridtable").jqGrid('setSelection',rowIds[0]);
			}
		}
	    jQuery("#hrPersonCurrent_gridtable").jqGrid('setSelection',rowid);
	}
	
	function reloadHrPersonCurrentGrid(){
		jQuery("#hrPersonCurrent_gridtable").jqGrid('setGridParam',{page:1}).trigger("reloadGrid");
	}
	
	//树隐藏与显示
	function hrPersonCurrentSearchFormReaload(){
		var urlString = "hrPersonCurrentGridList?1=1";
		propertyFilterSearch('hrPersonCurrent_search_form','hrPersonCurrent_gridtable',true);
		var postData = $("#hrPersonCurrent_gridtable").jqGrid("getGridParam", "postData");
		var addFilters = postData['addFilters'];
		var treeObj = $.fn.zTree.getZTreeObj("hrPersonCurrentLeftTree");
		if(addFilters!=true){
			var selectedNode = treeObj.getNodeByParam('id','-1',null);
			treeObj.selectNode(selectedNode,false);
		}
		var selectedNodes = treeObj.getSelectedNodes();
	    var selectedNode ,nodeId ;
	    if(selectedNodes){
	    	selectedNode = selectedNodes[0];
	    	if(selectedNode){
				nodeId = selectedNode.id;
	    	}
	    }
	    
	    if(nodeId&&nodeId!="-1"){
	    	if(selectedNode.subSysTem==='ORG'){
		    	urlString += "&orgCode="+nodeId;
	    	}else if(selectedNode.subSysTem==='DEPT'){
		    	urlString += "&departmentId="+nodeId;
	    	}else{
	    		urlString += "&personId="+nodeId;
	    	}
	    }
		urlString=encodeURI(urlString);
		jQuery("#hrPersonCurrent_gridtable").jqGrid('setGridParam',{url:urlString,page:1}).trigger("reloadGrid");
		//propertyFilterSearch('hrPersonCurrent_search_form','hrPersonCurrent_gridtable');
		//hpcTreeReShow();
	}
	/*
	function hpcTreeReShow(){
		var postData = jQuery("#hrPersonCurrent_gridtable").jqGrid('getGridParam',"postData");
		var urlString = 'hrPersonCurrentGridList?1=1';
		var isShowAll=true;
		jQuery.each(postData, function(key, val) {
			if(val){
			switch(key){
				case 'filter_LIKES_personCode':
					urlString+="&filter_LIKES_personCode="+val;
					isShowAll=false;
					break;
				case 'filter_LIKES_name':
					urlString+="&filter_LIKES_name="+val;
					isShowAll=false;
					break;
				case 'filter_INS_status.id':
					urlString+="&filter_INS_status.id="+val;
					isShowAll=false;
					break;
				case 'filter_INS_postType':
					urlString+="&filter_INS_postType="+val;
					isShowAll=false;
					break;
				case 'filter_LIKES_duty.name':
					urlString+="&filter_LIKES_duty.name="+val;
					isShowAll=false;
					break;
				case 'filter_EQS_sex':
					urlString+="&filter_EQS_sex="+val;
					isShowAll=false;
					break;
				case 'filter_EQS_educationalLevel':
					urlString+="&filter_EQS_educationalLevel="+val;
					isShowAll=false;
					break;
				case 'filter_EQS_nation':
					urlString+="&filter_EQS_nation="+val;
					isShowAll=false;
					break;
				case 'filter_EQB_disable':
					urlString+="&filter_EQB_disable="+val;
					isShowAll=false;
					break;
				case 'queryCommonId':
					urlString+="&queryCommonId="+val;
					isShowAll=false;
					break;
			}    
			}
	 　　});   
		var showDisabledDept = jQuery("#hrPersonCurrent_showDisabled").attr("checked");
		var showDisabledPerson = jQuery("#hrPersonCurrent_showDisabledPerson").attr("checked");
		if(showDisabledDept){
			urlString += "&showDisabled=true";
		}
		if(showDisabledPerson){
			urlString += "&showDisabledPerson=true";
		}
		var treeObj = $.fn.zTree.getZTreeObj("hrPersonCurrentLeftTree");
		 if(!treeObj){
        	return; 
         }
		 if(isShowAll){
			 showIds = null; 
		 }else{
			 showIds = new Array(); 
			 urlString=encodeURI(urlString);
				jQuery.ajax({
				       url: urlString,
				       data: {},
				       type: 'post',
				       dataType: 'json',
				       async:false,
				       error: function(data){
				       },
				       success: function(data){
				        if(data.hrPersonCurrentAll){
				        	jQuery.each(data.hrPersonCurrentAll, function(i,val) { 
				        		showIds[i] = val.personId;
				         		});
				        }
				   }
				  });
		 }
		/* toggleDisabledOrCount({treeId:'hrPersonCurrentLeftTree',
	         showCode:jQuery('#hrPersonCurrent_showCode')[0],
	         disabledDept:jQuery("#hrPersonCurrent_showDisabled")[0],
	         disabledPerson: jQuery("#hrPersonCurrent_showDisabledPerson")[0],
	         showCount:jQuery("#hrPersonCurrent_showPersonCount")[0],
	         showIds:showIds}); */
	//}
	//人员数减一
	function updateHrDeptPersonCountSubOne(treeObjId,updateNode,showPersonCount){
		var treeObj = $.fn.zTree.getZTreeObj(treeObjId);
		var personCount = parseInt(updateNode.personCount);
		if(personCount>0){
			personCount=personCount-1;
		}
		updateNode.personCount=personCount;
		if(showPersonCount){
			updateNode.name = updateNode.nameWithoutPerson+"("+personCount+")";
		}
		treeObj.updateNode(updateNode);
		var parentNode = updateNode.getParentNode();
		if(parentNode&&parentNode.subSysTem!="ALL"){
			updateHrDeptPersonCountSubOne(treeObjId,parentNode,showPersonCount);
		}
	}

	function toggleExpandTreeDefine(ex,treeId){
		var scrollbarData = jQuery('#scrollbar1').data("plugin_tinyscrollbar");
		var updatePosition = scrollbarData.contentPosition;
    	var positionStart = -scrollbarData.viewportSize + scrollbarData.contentPosition;
    	var positionEnd = scrollbarData.contentPosition + 2*scrollbarData.viewportSize;
    	
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var rootNode = treeObj.getNodes()[0];
		
		var expandArr = new Array();
		
		remainHeight = positionEnd;
		expandNode(rootNode,expandArr,remainHeight);
		//debugger;
		scrollbarData.lastCloseLi = rootNode.tId;
		for(var nodeIdnex=0;nodeIdnex<expandArr.length;nodeIdnex++){
			if(expandArr[nodeIdnex].fullExpend){
				treeObj.expandNode(expandArr[nodeIdnex],true,true,false,false);
			}else{
				treeObj.expandNode(expandArr[nodeIdnex],true,false,false,false);
			}
			scrollbarData.lastOpenLi = expandArr[nodeIdnex].tId;
			//console.log(treeObj.getNodeIndex(expandArr[nodeIdnex]));
			scrollbarData.lastOpenIndex = 0;
			scrollbarData.lastCloseIndex = 0;
		}
		setTimeout(function(){
			scrollbarData.update();
		},100);
	}
	function nodeExitInArr(arr,node){
		if(!arr){
			return false;
		}
		var arrlength = arr.length;
		for(var i=0;i<arrlength;i++){
			if(arr[i].tId==node.tId){
				return true;
			}
		}
	}
	function expandNode(node,expandArr,remainHeight,mustUnFoldAreaHeight){
		//debugger;
		if(!node){
			return remainHeight;
		}
		if(remainHeight<=20){
			return remainHeight;
		}else{
			remainHeight -= 20;
		}
		if(node.isParent||node.subSysTem=='DEPT'||node.subSysTem=='ORG'){
			/* var widnowTop = jQuery("#hrPersonCurrent_layout-west").scrollTop();
			var treeTop = jQuery('#hrPersonCurrentLeftTree').offset().top;
			var nodeTId = node.tId;
			var nodeLi = jQuery("#"+nodeTId); */
			if(node.subSysTem=='DEPT'){
				var childrenNum = getNodeChildrenNum(node);
				var fullExpendHeight = childrenNum*20;
				if(fullExpendHeight<=remainHeight||node.mustShow){
					if(node.mustShow){
						node.mustShow = false;
						
					}
					node.fullExpend = true;
					expandArr.push(node);
					remainHeight -= fullExpendHeight;
				}else{
					var childrenNodes = node.children;
					if(!childrenNodes){
						return remainHeight;
					}
					if(childrenNodes[0].subSysTem=='PERSON'){
						node.fullExpend = true;
						expandArr.push(node);
						remainHeight -= childrenNodes.length*20;
					}else{
						for(var childIndex in childrenNodes){
							if(remainHeight<=20){
								break;
							}
							var childrenNode = childrenNodes[childIndex];
							remainHeight = expandNode(childrenNode,expandArr,remainHeight);
						}
					}
				}
			}else{
				var childrenNodes = node.children;
				if(!childrenNodes){
					return remainHeight;
				}
				for(var childIndex in childrenNodes){
					if(remainHeight<=20){
						break;
					}
					var childrenNode = childrenNodes[childIndex];
					remainHeight = expandNode(childrenNode,expandArr,remainHeight);
				}
				/* var nodeNum = childrenNodes.length;
				var childrenHeight = nodeNum*20;
				if(!nodeExitInArr(expandArr,node)){
					expandArr.push(node);
				}
				if(childrenNodes[0].isParent==true||childrenNodes[0].subSysTem=='DEPT'||childrenNodes[0].subSysTem=='ORG'){
					remainHeight -= 20;
					for(var childIndex in childrenNodes){
						var childrenNode = childrenNodes[childIndex];
						remainHeight = expandNode(childrenNode,expandArr,remainHeight);
					}
				}else{
					remainHeight -= childrenHeight + 20;
				} */
			}
			/* if(remainHeight>0){
				
			}else{
				return 0;
			} */
			
			/* if(nodeLi.offset().top>widnowTop){
				//var expendR = treeObj.expandNode(node,true,false,false,false);
				/* if(node.id=='-1'){
					expendR = true;
				}
				if(expendR==true){ 
					var childrenNodes = node.children;
					if(childrenNodes==null||childrenNodes.length==0){
						return ;
					}else{
						var nodeNum = childrenNodes.length;
						var childrenHeight = nodeNum*20;
						if(childrenHeight+treeTop <= 2*windowHeight){
							expandArr.push();
						}
					}
					
					for(var childIndex in childrenNodes){
						var childrenNode = childrenNodes[childIndex];
							expandNode(childrenNode,treeObj);
						
					}
				//}
				
			} */
		}
		return remainHeight;
	}
	
	function getNodeChildrenNum(parentNode){
		var childrenNum = 0;
		if(!parentNode||!parentNode.isParent){
			return 0;
		}else{
			var childrenNodes = parentNode.children;
			if(!childrenNodes||childrenNodes.length==0){
				return 0;
			}else{
				childrenNum += childrenNodes.length;
				for(var nondeIndex in childrenNodes){
					var childNode = childrenNodes[nondeIndex];
					childrenNum += getNodeChildrenNum(childNode);
				}
				//console.log(parentNode.name+":"+childrenNum);
				return childrenNum;
			}
		}
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
	.photoSelected{
		border :2px solid #FFA300;
		margin-top : 36px;
		margin-left:26px
	}
</style>
<div class="page" id="hrPersonCurrent_page">
	<div id="hrPersonCurrent_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
				<form id="hrPersonCurrent_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrPersonSnap.personCode'/>:
					 	<input type="text" style="width:80px" name="filter_LIKES_personCode" />
					</label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrPersonSnap.name'/>:
					 	<input type="text" style="width:80px" name="filter_LIKES_name" />
					</label>&nbsp;&nbsp;
					<%-- <label style="float:none;white-space:nowrap" >
						<s:text name='hrPersonSnap.hrDept'/>:
						<input type="text" id="hrPersonCurrent_hrDept"/>
					 	<input type="hidden" id="hrPersonCurrent_hrDept_id" name="deptIds" />
					</label>&nbsp;&nbsp; --%>
					<label style="float:none;white-space:nowrap" >
       					<s:text name='hrPersonSnap.empType'/>:
      					<input type="text" id="hrPersonCurrent_empType" style="width:100px"/>
					 	<input type="hidden" id="hrPersonCurrent_empType_id" name="filter_INS_status.id" />
					 </label>&nbsp;&nbsp;
					 <label style="float:none;white-space:nowrap" >
       					<s:text name='hrPersonSnap.postType'/>:
      					<input type="text" id="hrPersonCurrent_postType" style="width:100px"/>
					 	<input type="hidden" id="hrPersonCurrent_postType_id" name="filter_INS_postType" />
					 </label>&nbsp;&nbsp;
					 <label style="float:none;white-space:nowrap" >
       					<s:text name='hrPersonSnap.duty'/>:
      					<input type="text" id="hrPersonCurrent_duty" style="width:100px" name="filter_LIKES_duty.name"/>
					 </label>&nbsp;&nbsp;
					<label style="float:none;white-space:nowrap" >
       					<s:text name='hrPersonSnap.sex'/>:
      					 <s:select
           				  key="hrPersonSnap.sex" name="filter_EQS_sex" 
            			  maxlength="50" list="sexList" listKey="value" headerKey="" headerValue="--" 
            			  listValue="content" emptyOption="false" theme="simple"></s:select>
     				 </label>&nbsp;&nbsp;
     				 <label style="float:none;white-space:nowrap" >
       					<s:text name='hrPersonSnap.educationalLevel'/>:
      					 <s:select
           				  key="hrPersonSnap.educationalLevel" name="filter_EQS_educationalLevel" 
            			  maxlength="50" list="educationalLevelList" listKey="value" headerKey="" headerValue="--" 
            			  listValue="content" emptyOption="false" theme="simple"></s:select>
     				 </label>&nbsp;&nbsp;
     				 <label style="float:none;white-space:nowrap" >
       					<s:text name='hrPersonSnap.nation'/>:
      					 <s:select
           				  key="hrPersonSnap.nation" name="filter_EQS_nation" 
            			  maxlength="50" list="nationList" listKey="value" headerKey="" headerValue="--" 
            			  listValue="content" emptyOption="false" theme="simple"></s:select>
     				 </label>&nbsp;&nbsp;
     				<%--  <label style="float:none;white-space:nowrap" >
						<s:text name='hrPersonSnap.disabled'/>:
					 	<s:select name="filter_EQB_disable" headerKey="" headerValue="--" 
							list="#{'1':'是','0':'否' }" listKey="key" listValue="value"
							emptyOption="false"  maxlength="10" theme="simple" >
						</s:select>
					</label>&nbsp;&nbsp; --%>
					<label style="float:none;white-space:nowrap" >
						<s:text name='hrPersonSnap.queryCommon'/>:
					 	<input type="text" id="hrPersonCurrent_queryCommon" style="width:120px"/>
					 	<input type="hidden" id="hrPersonCurrent_queryCommon_id" name="queryCommonId"/>
					</label>&nbsp;&nbsp;
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="hrPersonCurrentSearchFormReaload()"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="hrPersonCurrentSearchFormReaload()"><s:text name='button.search'/></button>
							</div>
						</div>
					</li>
					<%-- <li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="highSearch('hrPersonCurrent_gridtable','com.huge.ihos.hr.hrPerson.model.HrPersonCurrent')"><s:text name='button.higher'/></button>
								</div>
							</div>
					</li> --%>
				</ul>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar" id="hrPersonCurrent_panelBar">
<!-- 			<ul class="toolBar"> -->
<!-- 				<li> -->
<%-- 					<a id="hrPersonCurrent_gridtable_add_custom" class="addbutton" href="javaScript:" ><span><fmt:message key="button.add" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="hrPersonCurrent_gridtable_del_custom" class="delbutton"  href="javaScript:"><span><s:text name="button.delete" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a id="hrPersonCurrent_gridtable_edit_custom" class="changebutton"  href="javaScript:"><span><s:text name="button.edit" /></span></a> --%>
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 				    <a id="hrPersonCurrent_gridtable_batchEdit_custom" class="getdatabutton"  href="javaScript:"><span>批量修改</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<a id="hrPersonCurrent_gridtable_photo_custom" class="zbcomputebutton"  href="javaScript:"><span id="hrPersonCurrent_gridtable_photoSpan">显示照片</span></a> -->
<!-- 				</li> -->
<!-- 				<li> -->
<!-- 					<select> -->
<!-- 						<option value="入职申请">入职申请</option> -->
<!-- 						<option value="调动申请">调动申请</option> -->
<!-- 						<option value="调岗申请">调岗申请</option> -->
<!-- 						<option value="离职申请">离职申请</option> -->
<!-- 					</select> -->
<!-- 				</li> -->
<!-- 				<li> -->
<%-- 					<a class="settlebutton"  href="javaScript:setColShow('hrPersonCurrent_gridtable','com.huge.ihos.hr.hrPerson.model.HrPersonSnap')"><span><s:text name="button.setColShow" /></span></a> --%>
<!-- 				</li> -->
<!-- 			</ul> -->
		</div>
		<div id="hrPersonCurrent_container">
					
			<div id="hrPersonCurrent_layout-west" class="pane ui-layout-west" style="float: left; display: block;padding:0; overflow: hidden;">
				<div class="treeTopCheckBox" style="margin-top:15px">
					<span>
						显示机构编码
						<input id="hrPersonCurrent_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrPersonCurrentLeftTree',showCode:this,disabledDept:jQuery('#hrPersonCurrent_showDisabled')[0],disabledPerson:jQuery('#hrPersonCurrent_showDisabledPerson')[0],showCount:jQuery('#hrPersonCurrent_showPersonCount')[0],showIds:showIds})"/>
					</span>
					<span>
						显示人员数
						<input id="hrPersonCurrent_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrPersonCurrentLeftTree',showCode:jQuery('#hrPersonCurrent_showCode')[0],disabledDept:jQuery('#hrPersonCurrent_showDisabled')[0],disabledPerson:jQuery('#hrPersonCurrent_showDisabledPerson')[0],showCount:this,showIds:showIds})"/>
					</span>
					<label id="hrPersonCurrent_expandTree" onclick="toggleExpandTreeDefine(this,'hrPersonCurrentLeftTree')">展开</label>
				</div>
				<div class="treeTopCheckBox">
					<span>
						显示停用部门
						<input id="hrPersonCurrent_showDisabled" type="checkbox" onclick="showDisabledDeptInPerson(this)"/>
					</span>
					 <span>
          			 	显示停用人员
          			<input id="hrPersonCurrent_showDisabledPerson" type="checkbox" onclick="showDisabledPerson(this)"/>
          			</span>
				</div>
				<div class="treeTopCheckBox">
					<span>
						快速查询：
						<input type="text" onKeyUp="searchTree('hrPersonCurrentLeftTree',this)"/>
					</span>
				</div>
				<div id="scrollbar1">
				<div class="scrollbar"><div class="track"><div class="thumb"><div class="end"></div></div></div></div>
					<div class="viewport">
						<div class="overview">
							<div id="hrPersonCurrentLeftTree" class="ztree"></div>
						</div>
					</div>
				</div>
			</div>
			<div id="hrPersonCurrent_layout-center" class="pane ui-layout-center">
				<div id="hrPersonCurrent_gridtable_div" layoutH="119"  class="grid-wrapdiv" class="unitBox" style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="hrPersonCurrent_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_hrPersonCurrent_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
		 			<table id="hrPersonCurrent_gridtable"></table>
				</div>
		 		<div id="hrPersonCurrent_photo_list" layoutH="119" style="background:#eeeeee;display:none;overflow:auto"></div>
				<div class="panelBar">
					<div class="pages">
						<span><s:text name="pager.perPage" /></span> <select id="hrPersonCurrent_gridtable_numPerPage">
							<option value="20">20</option>
							<option value="50">50</option>
							<option value="100">100</option>
							<option value="200">200</option>
						</select> <span><s:text name="pager.items" />. <s:text name="pager.total" /><label id="hrPersonCurrent_gridtable_totals"></label><s:text name="pager.items" /></span>
					</div>
					<div id="hrPersonCurrent_gridtable_pagination" class="pagination"
						targetType="navTab" totalCount="200" numPerPage="20"
						pageNumShown="10" currentPage="1">
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
