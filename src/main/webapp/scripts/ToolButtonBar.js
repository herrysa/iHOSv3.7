var ToolButton = Backbone.Model.extend({
	defaults :{id:null,
	name: null,
	param:{
		callBodyParam:null,
		beforeCallParam:null
	},
	callBody:null,
	className:null,
	callback:{
		beforeCall :null
	}
	}
});

var ToolButtonCollection = Backbone.Collection.extend({
	model: ToolButton
});

var ToolButtonView = Backbone.View.extend({
	tagName:'li',
	events: {
		"click a":  "callButton"
	},
	callButton: function (e) {
		//var jt = jQuery(this).index();
		var callBody = this.model.get('callBody');
		var enable = this.model.get('enable');
		if(enable==true&&callBody&&typeof(callBody)=='function'){
			var param = this.model.get('param');
			var beforeCall = this.model.get('beforeCall');
			if(beforeCall&&typeof(beforeCall)=='function'){
				var beforeCallParam = param.beforeCallParam;
				var pass = beforeCall(e,this.model,beforeCallParam);
				if(pass){
					var callBodyParam = param.callBodyParam;
					callBody(e,this.model,callBodyParam);
				}
			}else{
				var callBodyParam = param.callBodyParam;
				callBody(e,this.model,callBodyParam);
			}
		}
		
	},
	render:function(){
		var modelId = this.model.get('id');
		var show = this.model.get('show');
		var enable = this.model.get('enable');
		var buttonType = this.model.get('buttonType');
		var random = this.model.get('random');
		if(random){
			modelId += "_"+random;
		}
		if(show==true){
			if(buttonType=='select'){
				if(enable==true){
					$(this.el).append("<select id='button_"+modelId+"'></select>");
				}else{
					$(this.el).append("<select id='button_"+modelId+"' disabled='true'></select>");
				}
				var modelId = this.model.get('id');
				setTimeout(function(){
					jQuery("#button_"+modelId).parent().unbind("hover");
					jQuery("#button_"+modelId).hover(function(e){
			    		e.stopPropagation();
			    	});
				},200);
			}else{
				if(enable==true){
					$(this.el).append("<a id='button_"+modelId+"' class='"+this.model.get('className')+"'  href='javaScript:'><span id='span_button_"+modelId+"'>"+this.model.get('buttonLabel')+"</span></a>");
				}else{
					$(this.el).append("<a id='button_"+modelId+"' class='"+this.model.get('className')+"' style='color:#808080;'  href='javaScript:'><span id='span_button_"+modelId+"'>"+this.model.get('buttonLabel')+"</span></a>");
					var modelId = this.model.get('id');
					setTimeout(function(){
						jQuery("#button_"+modelId).hover(function(e){
				    		e.stopPropagation();
				    	});
					},200);
					
				}
			}
			
		}
		return this;
	}
});

var ToolButtonBar = Backbone.View.extend({
    initialize: function () {
		$(this.el).append("<ul class='toolBar'></ul>");
		var buttonUtil = new ButtonUtil();
		$.extend(buttonUtil,this.attributes);
		buttonUtil.prepare();
		this.buttonUtil = buttonUtil;
    },
	render :function(){
		$(this.el).find('ul:first').empty();
		$(this.el).find('ul:first').append(
			_.map(this.collection.models,function(model,key){
				return new ToolButtonView({
					model:model
				}).render().el;
			})
		);
		return this;
	},
    addButton: function(model) {
    	this.collection.add(model);
    	this.render();
        //$(this.el).find('ul:first').append("<li><a id='"+model.get('id')+"' class='"+model.get('className')+"'  href='javaScript:'><span>"+model.get('buttonLabel')+"</span></a></li>");
    },
    addCallBody : function(id,fun,param){
    	var model = this.collection.get(id);
    	if(model){
    		model.set("callBody",fun);
    		var paramAll = model.get('param');
    		if(paramAll){
    			model.set("param",{callBodyParam:param,beforeCallParam:paramAll.beforeCallParam});
    		}
    	}
    },
    addBeforeCall : function(id,fun,param){
    	var model = this.collection.get(id);
    	if(model){
    		model.set("beforeCall",fun);
    		var paramAll = model.get('param');
    		if(paramAll){
    			model.set("param",{callBodyParam:paramAll.callBodyParam,beforeCallParam:param});
    		}
    	}
    },
    addFunctionAdd : function(id){
    	var model = this.collection.get(id);
    	if(model){
    		var bu = this.buttonUtil;
    		model.set("callBody",function(){
    			bu.addEntity();
    		});
    	}
    },
    addFunctionEdit : function(id){
    	var model = this.collection.get(id);
    	if(model){
    		var bu = this.buttonUtil;
    		model.set("callBody",function(){
    			bu.editEntity();
    		});
    	}
    },
    addFunctionDel : function(id){
    	var model = this.collection.get(id);
    	if(model){
    		var bu = this.buttonUtil;
    		model.set("callBody",function(){
    			bu.deleteEntity();
    		});
    	}
    },
    disabledAll :function(){
		_.map(this.collection.models,function(model,key){
			model.set('enable',false);
		});
		return this;
	},
	enabledAll :function(){
		_.map(this.collection.models,function(model,key){
			model.set('enable',true);
		});
		return this;
	}
});

var ButtonUtil = function(){
	this.tableId = null,
	this.baseName = null,
	this.upperBaseName = null,
	this.width = 600,
	this.height = 600,
	this.base_URL = null,
	this.optId = null,
	this.fatherGrid = null,
	this.extraParam = null,
	this.selectNone = "请选择记录。",
	this.selectMore = "只能选择一条记录。",
	this.addTitle = null,
	this.editTitle = null,
	this.edit_URL = null,
	this.del_URL = null,
	this.userDefineParam = null,
	
	this.prepare = function(){
		if(typeof(jQuery("#"+this.tableId+"_div").attr("buttonBar"))!="undefined"){
			var buttonBar = jQuery("#"+this.tableId+"_div").attr("buttonBar");
			var widthTemp = getParam('width',buttonBar);
			var heightTemp = getParam('height',buttonBar);
			if(widthTemp){
				this.width = widthTemp;
			}
			if(heightTemp){
				this.height = heightTemp;
			}
		}
		if(!this.baseName&&this.tableId){
			this.baseName = this.tableId.substring(0,tableId.indexOf("_"));
		}
		if(this.baseName){
			this.upperBaseName = this.baseName.substring(0,1).toUpperCase()+this.baseName.substring(1);
		}
		if(!this.optId){
			this.optId = this.baseName+"Id";
		}
		if(this.base_URL!=null&&this.base_URL!=""){
			if(this.base_URL.indexOf("||")!=-1){
				this.edit_URL = this.base_URL.split("||")[0];
				this.del_URL = this.base_URL.split("||")[1];
			}else{
				this.edit_URL = this.base_URL
			}
			// edit_URL = "edit"+upperBaseName+"?popup=true";
		}else{
			this.edit_URL = "edit"+this.upperBaseName;
			this.del_URL = "del"+this.upperBaseName;
		}
		if(!this.addTitle){
			this.addTitle = "New "+this.upperBaseName;
		}
		if(!this.editTitle){
			this.editTitle = "Edit "+this.upperBaseName;
		}
	},
	this.selectOneCheck = function(){
		var sid = jQuery("#"+this.tableId).jqGrid('getGridParam','selarrrow');
	    if(sid==null || sid.length ==0){
			alertMsg.error(this.selectNone);
			return false;
		}
	    if(sid.length>1){
			alertMsg.error(this.selectMore);
			return false;
		}
	    return true;
	},
	this.selectedCheck = function(){
		var sid = jQuery("#"+this.tableId).jqGrid('getGridParam','selarrrow');
		if(sid==null || sid.length ==0){
			alertMsg.error(this.selectNone);
			return false;
		}else{
			return true;
		}
	},
	this.fatherSelectCheck = function(){
		var fatherGridId = jQuery("#"+this.fatherGrid).jqGrid('getGridParam','selarrrow');
		if(fatherGridId==null || fatherGridId.length ==0){
			alertMsg.error(this.selectNone);
			return false;
		}
	    if(fatherGridId.length>1){
			alertMsg.error(this.selectMore);
			return false;
		}
	    return true;
	},
	this.addEntity = function(){
		var url = this.edit_URL+"?popup=true&navTabId="+this.tableId;
		if(this.fatherGrid!=null&&this.fatherGrid!=""&&this.extraParam!=null&&this.extraParam!=""){
			var fatherSelected = this.fatherSelectCheck();
		    if(fatherSelected){
		    	url += "&"+this.extraParam+"="+fatherGridId;
			}else{
				return ;
			}
		}
		if(this.userDefineParam!=null&&this.userDefineParam!=""){
			url += this.userDefineParam;
		}
		var winTitle = this.addTitle;
		// alert(url);
		url = encodeURI(url);
		$.pdialog.open(url, 'add'+this.upperBaseName, winTitle, {mask:false,width:this.width,height:this.height});　
	},
	this.editEntity = function(){
		var selectOne = this.selectOneCheck();
		if(selectOne){
			var sid = jQuery("#"+this.tableId).jqGrid('getGridParam','selarrrow');
			var url = this.edit_URL +"?popup=true&"+this.optId+"="+ sid+"&navTabId="+this.tableId;
			if(this.fatherGrid!=null&&this.fatherGrid!=""&&this.extraParam!=null&&this.extraParam!=""){
				var fatherSelected = this.fatherSelectCheck();
			    if(fatherSelected){
			    	url += "&"+this.extraParam+"="+fatherGridId;
				}else{
					return ;
				}
			}
			if(this.userDefineParam!=null&&this.userDefineParam!=""){
				url += this.userDefineParam;
			}
			var winTitle = this.editTitle;
			// alert(url);
			url = encodeURI(url);
			$.pdialog.open(url, 'edit'+this.upperBaseName, winTitle, {mask:false,width:this.width,height:this.height});　
		}
	},
	this.deleteEntity = function(){
		var sid = jQuery("#"+this.tableId).jqGrid('getGridParam','selarrrow');
		var editUrl = jQuery("#"+this.tableId).jqGrid('getGridParam', 'editurl');
		editUrl+="?id=" + sid+"&navTabId="+this.tableId+"&oper=del";
		if(this.fatherGrid!=null&&this.fatherGrid!=""&&this.extraParam!=null&&this.extraParam!=""){
			var fatherGridId = jQuery("#"+this.fatherGrid).jqGrid('getGridParam','selarrrow');
			editUrl += "&"+this.extraParam+"="+fatherGridId;
		}
		if(this.userDefineParam!=null&&this.userDefineParam!=""){
			editUrl += this.userDefineParam;
		}
		editUrl = encodeURI(editUrl);
		var selected = this.selectedCheck();
		if(selected){
			alertMsg.confirm("确认删除？", {
				okCall: function(){
					jQuery.post(editUrl, {
					}, formCallBack, "json");
					
				}
			});
		}
	};
	
}

var scriptFunction = function(opts){ 
	if(opts){
		//opts = unescapeHTML(opts);
		opts = eval("("+opts+")");
		this.opts = opts;
	}
}

