
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
			 	/*2015.08.27 form search change*/
			 	gridContainerResize('hrPersonCurrent','layout');
			 	jQuery("#hrPersonCurrent_photo_list").height(jQuery("#hrPersonCurrent_gridtable_div").height());
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
	                
	                var userData = jQuery("#hrPersonCurrent_gridtable").getGridParam('userData'); 
	          	    if(userData){
		          	    var addFilters = userData.addFilters;
		    			var queryedPersonIds = userData.queryedPersonIds;
		          	  	var queryedPersonArr ;
		          	  	
		          	  	if(queryedPersonIds){
		          	  		queryedPersonArr = queryedPersonIds.split("");
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
		    	  			showIds:queryedPersonArr,
		    	  			addFilters:addFilters
		    	  		});
	          	    }
	                
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
// 		jQuery("#hrPersonCurrent_hrDept").treeselect({
//     			dataType:"url",
//     			optType:"multi",
//     			url:'getHrDeptHisNode',
//     			exceptnullparent:false,
//     			selectParent:false,
//     			minWidth:'230px',
//     			lazy:false
//     		});

    	jQuery("#hrPersonCurrent_form_branch").treeselect({
    		dataType:"sql",
        	optType:"multi",
        	sql:"select branchCode id,branchName name,'-1' pId from t_branch where branchCode <> 'XT' and disabled = '0'",
        	exceptnullparent:true,
        	minWidth : '175px',
        	selectParent:false,
        	lazy:true
    	});
    	
    	jQuery("#hrPersonCurrent_form_dgroup").treeselect({
    		dataType:"sql",
        	optType:"multi",
        	sql:"select i.value id,i.displayContent name,'-1' pId from t_dictionary_items i,t_dictionary d where i.dictionary_id = d.dictionaryId and d.code = 'dgroup'",
        	exceptnullparent:true,
        	minWidth : '150px',
        	selectParent:false,
        	lazy:true
    	});
    	//实例化ToolButtonBar
		var hrPersonCurrent_menuButtonArrJson = "${menuButtonArrJson}";
    	var hrPersonCurrent_toolButtonCollection = new ToolButtonCollection(eval(zzhUtil.DecodeURI(hrPersonCurrent_menuButtonArrJson,false)));
    	var hrPersonCurrent_toolButtonBar = new ToolButtonBar({el:$('#hrPersonCurrent_buttonBar'),collection:hrPersonCurrent_toolButtonCollection,attributes:{
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
	   	 $.ajax({
			    url: 'checkHrPersonCanBeEdited',
			    type: 'post',
			    data:{snapId:sid},
			    dataType: 'json',
			    aysnc:false,
			    error: function(data){
			    },
			    success: function(data){
			        if(!data.canBeEdited){
			        	 alertMsg.error(data.message);
			        }else{
			        	var winTitle='<s:text name="hrPersonSnapEdit.title"/>';
			 			var url = "editHrPersonSnap?snapId="+sid+"&navTabId=hrPersonCurrent_gridtable";
			 			$.pdialog.open(url,'editHrPersonSnap',winTitle, {mask:true,resizable:false,maxable:false,width : 800,height : 600});
			        }
			    }
			});
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
    	hrPersonCurrent_toolButtonBar.addBeforeCall('1002010105',function(e,$this,param){
			return hrPersonCurrent_function.optBeforeCall(e,$this,param);
		},{checkPeriod:"checkPeriod"});
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
    	hrPersonCurrent_toolButtonBar.addBeforeCall('1002010106',function(e,$this,param){
		return hrPersonCurrent_function.optBeforeCall(e,$this,param);
		},{checkPeriod:"checkPeriod"});
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
       	 		 $.pdialog.open(url,'importHrPersonSnap',winTitle, {mask:true,width : 600,height : 200});
		     },{});
    	     hrPersonCurrent_toolButtonBar.addBeforeCall('1002010108',function(e,$this,param){
 				return hrPersonCurrent_function.optBeforeCall(e,$this,param);
 			},{checkPeriod:"checkPeriod"});
    	   //设置表格列
    	     var hrPersonCurrent_setColShowButton = {id:'1002010109',buttonLabel:'<s:text name="button.setColShow" />',className:"settlebutton",show:true,enable:true,
    	       callBody:function(){
    	        setColShow('hrPersonCurrent_gridtable','com.huge.ihos.hr.hrPerson.model.HrPersonSnap');
    	       }};
    	     hrPersonCurrent_toolButtonBar.addButton(hrPersonCurrent_setColShowButton);//实例化ToolButtonBar
    	     
    	     jQuery("select[name=filter_EQB_disable_notInFilter]","#hrPersonCurrent_search_form").change(function(){
    	    	 var personDisable = jQuery(this).val();
    	    	 var postData = jQuery("#hrPersonCurrent_gridtable").jqGrid('getGridParam',"postData");
    	    	 if(personDisable=='0'){
    	    		 jQuery("#hrPersonCurrent_showDisabledPerson").removeAttr('checked');
    	    		 postData['filter_EQB_disable'] = false;
    	    	 }else{
    	    		 jQuery("#hrPersonCurrent_showDisabledPerson").attr('checked','checked');
    	    		 delete postData['filter_EQB_disable'];
    	    	 }
    	     });
  	});
	
	function hrPersonCurrentLeftTree() {
		//var url = "makeHrDeptTree";
		var url = "makeHrPersonTree?";
		$.get(url, {"_" : $.now()}, function(data) {
			var hrDeptTreeData = data.hrPersonTreeNodes;
			var hrDeptTree = $.fn.zTree.init($("#hrPersonCurrentLeftTree"),ztreesetting_hrPersonTree, hrDeptTreeData);
			var nodes = hrDeptTree.getNodes();
			hrDeptTree.expandNode(nodes[0], true, false, true);
			hrDeptTree.selectNode(nodes[0]);
			toggleDisabledOrCount({treeId:'hrPersonCurrentLeftTree',
		         showCode:jQuery('#hrPersonCurrent_showCode')[0],
		         disabledDept:jQuery("#hrPersonCurrent_showDisabled")[0],
		         disabledPerson:jQuery("#hrPersonCurrent_showDisabledPerson")[0],
		         showCount:jQuery("#hrPersonCurrent_showPersonCount")[0] ,
		         showIds:'init'
			});
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
			jQuery("select[name=filter_EQB_disable_notInFilter]","#hrPersonCurrent_search_form").val('0');
		}else{
			delete postData['filter_EQB_disable'];
			jQuery("select[name=filter_EQB_disable_notInFilter]","#hrPersonCurrent_search_form").val('');
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
				<form id="hrPersonCurrent_search_form"  class="queryarea-form">
					<label class="queryarea-label" style="${herpType=='S2'?'':'display:none'}">
						<s:text name='hrPersonSnap.branch'/>:
					 	<input type="hidden" name="filter_INS_branch.branchCode" id="hrPersonCurrent_form_branch_id" />
					 	<input type="text" style="width:80px" id="hrPersonCurrent_form_branch"/>
					</label>
					<label class="queryarea-label">
						<s:text name='hrPersonSnap.dgroup'/>:
						<input type="hidden" name="filter_INS_department.dgroup" id="hrPersonCurrent_form_dgroup_id" />
						<input type="text" style="width:80px" id="hrPersonCurrent_form_dgroup" >
					</label>
					<label class="queryarea-label">
						<s:text name='hrPersonSnap.personCode'/>:
					 	<input type="text" style="width:80px" name="filter_LIKES_personCode" />
					</label>
					<label class="queryarea-label">
						<s:text name='hrPersonSnap.name'/>:
					 	<input type="text" style="width:80px" name="filter_LIKES_name" />
					</label>
					<label class="queryarea-label">
						<s:text name='hrPersonSnap.hrDept'/>:
						<input type="text" style="width:80px" id="hrPersonCurrent_hrDept" name="filter_LIKES_department.name"/>
					</label>
					<label class="queryarea-label">
       					<s:text name='hrPersonSnap.empType'/>:
      					<input type="text" id="hrPersonCurrent_empType" style="width:80px"/>
					 	<input type="hidden" id="hrPersonCurrent_empType_id" name="filter_SQS_{empType}" />
					 </label>
					 <label class="queryarea-label">
       					<s:text name='hrPersonSnap.postType'/>:
      					<input type="text" id="hrPersonCurrent_postType" style="width:80px"/>
					 	<input type="hidden" id="hrPersonCurrent_postType_id" name="filter_EQS_postType"/>
					 </label>
					 <label class="queryarea-label">
       					<s:text name='hrPersonSnap.duty'/>:
      					<input type="text" id="hrPersonCurrent_duty" style="width:80px" name="filter_LIKES_duty.name"/>
					 </label>
					<label class="queryarea-label">
       					<s:text name='hrPersonSnap.sex'/>:
      					 <s:select
           				  key="hrPersonSnap.sex" name="filter_EQS_sex" 
            			  maxlength="50" list="sexList" listKey="value" headerKey="" headerValue="--" 
            			  listValue="content" emptyOption="false" theme="simple"></s:select>
     				 </label>
     				 <label class="queryarea-label">
       					<s:text name='hrPersonSnap.educationalLevel'/>:
      					 <s:select
           				  key="hrPersonSnap.educationalLevel" name="filter_EQS_educationalLevel" 
            			  maxlength="50" list="educationalLevelList" listKey="value" headerKey="" headerValue="--" 
            			  listValue="content" emptyOption="false" theme="simple"></s:select>
     				 </label>
     				 <label class="queryarea-label">
       					<s:text name='hrPersonSnap.nation'/>:
      					 <s:select
           				  key="hrPersonSnap.nation" name="filter_EQS_nation" 
            			  maxlength="50" list="nationList" listKey="value" headerKey="" headerValue="--" 
            			  listValue="content" emptyOption="false" theme="simple"></s:select>
     				</label>
     				<label class="queryarea-label">
						<s:text name='hrPersonSnap.disabled'/>:
						<s:select name="filter_EQB_disable_notInFilter" headerKey="" headerValue="--" 
							list="#{'1':'是','0':'否' }" listKey="key" listValue="value" value="0"
							emptyOption="false"  maxlength="10"  theme="simple" >
						</s:select>
					</label>
					<label class="queryarea-label">
						<s:text name='hrPersonSnap.queryCommon'/>:
					 	<input type="text" id="hrPersonCurrent_queryCommon" style="width:120px"/>
					 	<input type="hidden" id="hrPersonCurrent_queryCommon_id" name="queryCommonId"/>
					</label>
					<div class="buttonActive" style="float:right">
						<div class="buttonContent">
							<button type="button" onclick="hrPersonCurrentSearchFormReaload()"><s:text name='button.search'/></button>
						</div>
					</div>
				</form>
			</div>
<!-- 			<div class="subBar"> -->
<!-- 				<ul> -->
<!-- 					<li><div class="buttonActive"> -->
<!-- 							<div class="buttonContent"> -->
<%-- 								<button type="button" onclick="hrPersonCurrentSearchFormReaload()"><s:text name='button.search'/></button> --%>
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="pageContent">
<div class="panelBar" id="hrPersonCurrent_buttonBar">

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
			<div id="hrPersonCurrent_layout-west" class="pane ui-layout-west" style="float: left; display: block; overflow: auto;">
				<div class="treeTopCheckBox">
					<span>
						显示机构编码
						<input id="hrPersonCurrent_showCode" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrPersonCurrentLeftTree',showCode:this,disabledDept:jQuery('#hrPersonCurrent_showDisabled')[0],disabledPerson:jQuery('#hrPersonCurrent_showDisabledPerson')[0],showCount:jQuery('#hrPersonCurrent_showPersonCount')[0],showIds:showIds})"/>
					</span>
					<span>
						显示人员数
						<input id="hrPersonCurrent_showPersonCount" type="checkbox" onclick="toggleDisabledOrCount({treeId:'hrPersonCurrentLeftTree',showCode:jQuery('#hrPersonCurrent_showCode')[0],disabledDept:jQuery('#hrPersonCurrent_showDisabled')[0],disabledPerson:jQuery('#hrPersonCurrent_showDisabledPerson')[0],showCount:this,showIds:showIds})"/>
					</span>
					<label id="hrPersonCurrent_expandTree" onclick="toggleExpandHrTree(this,'hrPersonCurrentLeftTree')">展开</label>
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
				<div id="hrPersonCurrentLeftTree" class="ztree"></div>
			</div>
			<div id="hrPersonCurrent_layout-center" class="pane ui-layout-center">
				<div id="hrPersonCurrent_gridtable_div" class="grid-wrapdiv" style="margin-left: 2px; margin-top: 2px; overflow: hidden">
					<input type="hidden" id="hrPersonCurrent_gridtable_navTabId" value="${sessionScope.navTabId}">
					<div id="load_hrPersonCurrent_gridtable" class='loading ui-state-default ui-state-active' style="display:none"></div>
		 			<table id="hrPersonCurrent_gridtable"></table>
				</div>
		 		<div id="hrPersonCurrent_photo_list"  style="background:#eeeeee;display:none;overflow:auto"></div>
				<div class="panelBar"  id="hrPersonCurrent_pageBar">
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
