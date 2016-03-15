/**
 * @author Roger Wu
 * reference:dwz.drag.js, dwz.dialogDrag.js, dwz.resize.js, dwz.taskBar.js
 */
(function($){
	$.pdialog = {
		_op:{height:300, width:580, minH:40, minW:50, total:20, max:false, mask:false, resizable:true, drawable:true, maxable:true,minable:true,fresh:true,ifr:false},
		_current:null,
		_zIndex:42,
		_lastzIndex:42,
		_supcanIds:[],
		getCurrent:function(){
			return this._current;
		},
		reload:function(url, options){
			var op = $.extend({data:{}, dialogId:"", callback:null}, options);
			var dialog = (op.dialogId && $("body").data(op.dialogId)) || this._current;
			if (dialog){
				var jDContent = dialog.find(".dialogContent");
				jDContent.ajaxUrl({
					type:"POST", url:url, data:op.data, callback:function(response){
						jDContent.find("[layoutH]").layoutH(jDContent);
						$(".pageContent", dialog).width($(dialog).width()-14);
						$(":button.close", dialog).click(function(){
							$.pdialog.close(dialog);
							return false;
						});
						if ($.isFunction(op.callback)) op.callback(response);
					}
				});
			}
		},
		//打开一个层
		open:function(url, dlgid, title, options) {
			var op = $.extend({},$.pdialog._op, options);
			var dialog = $("body").data(dlgid);
			//重复打开一个层
			if(dialog) {
				if(dialog.is(":hidden")) {
					dialog.show();
				}
				if(op.fresh || url != $(dialog).data("url")){
					dialog.data("url",url);
					dialog.find(".dialogHeader").find("h1").html(title);
					this.switchDialog(dialog);
					var jDContent = dialog.find(".dialogContent");
					jDContent.loadUrl(url, {}, function(){
						jDContent.find("[layoutH]").layoutH(jDContent);
						$(".pageContent", dialog).width($(dialog).width()-14);
						$("button.close").click(function(){
							$.pdialog.close(dialog);
							return false;
						});
					});
				}
			
			} else { //打开一个全新的层
			
				$("body").append(DWZ.frag["dialogFrag"]);
				dialog = $(">.dialog:last-child", "body");
				dialog.data("id",dlgid);
				dialog.data("url",url);
				if(options.close) dialog.data("close",options.close);
				if(options.param) dialog.data("param",options.param);
				if(op.ifr) dialog.data("ifr",op.ifr);
				($.fn.bgiframe&&dialog.bgiframe());
				
				dialog.find(".dialogHeader").find("h1").html(title);
				$(dialog).css("zIndex", ($.pdialog._zIndex+=2));
				$("div.shadow").css("zIndex", $.pdialog._zIndex - 3).show();
				if(op.ifr==true){
					$(dialog).resize(function(e,w,h,t,l,hide){
						bgiframe(dialog,e,w,h,t,l,hide);
					});
				}
				$.pdialog._init(dialog, options);
				$(dialog).click(function(){
					$.pdialog.switchDialog(dialog);
				});
				
				if(op.resizable)
					dialog.jresize();
				if(op.drawable)
				 	dialog.dialogDrag();
				$("a.close", dialog).click(function(event){ 
					$.pdialog.close(dialog);
					return false;
				});
				if (op.maxable) {
					$("a.maximize", dialog).show().click(function(event){
						$.pdialog.switchDialog(dialog);
						$.pdialog.maxsize(dialog);
						dialog.jresize("destroy").dialogDrag("destroy");
						return false;
					});
				} else {
					$("a.maximize", dialog).hide();
				}
				$("a.restore", dialog).click(function(event){
					$.pdialog.restore(dialog);
					dialog.jresize().dialogDrag();
					return false;
				});
				if (op.minable) {
					$("a.minimize", dialog).show().click(function(event){
						$.pdialog.minimize(dialog);
						return false;
					});
				} else {
					$("a.minimize", dialog).hide();
				}
				$("div.dialogHeader a", dialog).mousedown(function(){
					return false;
				});
				$("div.dialogHeader", dialog).dblclick(function(){
					if($("a.restore",dialog).is(":hidden"))
						$("a.maximize",dialog).trigger("click");
					else
						$("a.restore",dialog).trigger("click");
				});
				if(op.max) {
//					$.pdialog.switchDialog(dialog);
					$.pdialog.maxsize(dialog);
					dialog.jresize("destroy").dialogDrag("destroy");
				}
				$("body").data(dlgid, dialog);
				$.pdialog._current = dialog;
				$.pdialog.attachShadow(dialog);
				//load data
				var jDContent = $(".dialogContent",dialog);
				
				//url 指向本页的一个div时
				if(url.indexOf('DIA_inline') != -1)
				{
					var queryString = url.replace(/^[^\?]+\??/,'');
					var params = $.pdialog.parseQuery( queryString );
					var inlineId = '#'+params['inlineId'];
					var callback = params['callback'];
					var callback = eval(callback);
					//移动数据到对话框中
					jDContent.append($(inlineId ).children());
					
					
					jDContent.find("[layoutH]").layoutH(jDContent);
					$(".pageContent", dialog).width($(dialog).width()-14);
					
					//重新指定关闭方法
					$("button.close",dialog).unbind( "click" );
					$("button.close",dialog).click(function(){
						$.pdialog.divclose(dialog,inlineId,callback);
						return false;
					});	
					//重新指定关闭方法
					$("a.close", dialog).unbind( "click" );
					$("a.close", dialog).click(function(event){ 
						$.pdialog.divclose(dialog,inlineId);//只是关闭对话框，不用执行回调函数	
						return false;
					});					
				}
				else
				{					
				jDContent.loadUrl(url, {}, function(){
					jDContent.find("[layoutH]").layoutH(jDContent);
					$(".pageContent", dialog).width($(dialog).width()-14);
					$("button.close").click(function(){
						$.pdialog.close(dialog);
						return false;
					});
				});
				}
			}
			if (op.mask) {
				var lastzIndex = $.pdialog._lastzIndex;
				if(lastzIndex<1000){
					lastzIndex = 1000;
				}else{
					lastzIndex += 2;
				}
				$.pdialog._lastzIndex = lastzIndex;
				$(dialog).css("zIndex", lastzIndex);
				$("a.minimize",dialog).hide();
				$(dialog).data("mask", true);
				var dlId = $(dialog).data("id");
				var thisBackGround = $("#dialogBackground").clone(true);
				thisBackGround.attr("id",dlId+"_dialogBackground");
				thisBackGround.css("zIndex",(lastzIndex-1));
				$("#dialogBackground").after(thisBackGround);
				thisBackGround.show();
				if(op.hasSupcan){
					$(dialog).data("hasSupcan", op.hasSupcan);
					if(op.hasSupcan=='all'){
						var supcanIdArr = supcanGridMap.keys();
						for(var s in supcanIdArr){
							var sId = supcanIdArr[s];
							try{
								eval('('+sId+')').func("GrayWindow", '1 \r\n 255');
							}catch(e){
								//alert(e);
							}
						}
					}else{
						var supcanIds = op.hasSupcan.split(",");
						for(var s in supcanIds){
							var sId = supcanIds[s];
							try{
								eval('('+sId+')').func("GrayWindow", '1 \r\n 255');
							}catch(e){
								//alert(e);
							}
						}
					}
					/*jQuery('object').each(function(){
						var s = jQuery(this);
						var sId = s.attr('id');
						var sType = s.attr('type');
						if(sType=='application/supcan-plugin'&&eval('('+sId+')')){
							if(typeof(eval('('+sId+')').func)=='function'){
								eval('('+sId+')').func("GrayWindow", '1 \r\n 255');
							}
						}
					});*/
				}
			}else {
				//add a task to task bar
				if(op.minable) $.taskBar.addDialog(dlgid,title);
			}
		},
		
		
		//添加的新方法
		divclose:function(dialog,inlineId,callback) {
			// 从对话框中移回数据  move elements back when you're finished		    
			$(inlineId).append($(".dialogContent",dialog).children());
			if(callback !=null)
				callback();
			$.pdialog.close(dialog);
		},
		parseQuery:function(query) {
			var Params = {};
		   if ( ! query ) {return Params;}// return empty object
		   var Pairs = query.split(/[;&]/);
		   for ( var i = 0; i < Pairs.length; i++ ) {
		      var KeyVal = Pairs[i].split('=');
		      if ( ! KeyVal || KeyVal.length != 2 ) {continue;}
		      var key = unescape( KeyVal[0] );
		      var val = unescape( KeyVal[1] );
		      val = val.replace(/\+/g, ' ');
		      Params[key] = val;
		   }
		   return Params;
		},
		closeCurrentDiv:function(inlineId,callback){
			this.divclose($.pdialog._current,"#"+inlineId,callback);
		},
		/**
		 * 切换当前层
		 * @param {Object} dialog
		 */
		switchDialog:function(dialog) {
			var index = $(dialog).css("zIndex");
			$.pdialog.attachShadow(dialog);
			if($.pdialog._current) {
				var cindex = $($.pdialog._current).css("zIndex");
				$($.pdialog._current).css("zIndex", index);
				$(dialog).css("zIndex", cindex);
				$("div.shadow").css("zIndex", cindex - 1);
				$.pdialog._current = dialog;
			}
			$.taskBar.switchTask(dialog.data("id"));
		},
		/**
		 * 给当前层附上阴隐层
		 * @param {Object} dialog
		 */
		attachShadow:function(dialog) {
			var shadow = $("div.shadow");
			if(shadow.is(":hidden")) shadow.show();
			shadow.css({
				top: parseInt($(dialog)[0].style.top) - 2,
				left: parseInt($(dialog)[0].style.left) - 4,
				height: parseInt($(dialog).height()) + 8,
				width: parseInt($(dialog).width()) + 8,
				zIndex:parseInt($(dialog).css("zIndex")) - 1
			});
			$(".shadow_c", shadow).children().andSelf().each(function(){
				$(this).css("height", $(dialog).outerHeight() - 4);
			});
		},
		_init:function(dialog, options) {
			var op = $.extend({}, this._op, options);
			var height = op.height>op.minH?op.height:op.minH;
			var width = op.width>op.minW?op.width:op.minW;
			if(isNaN(dialog.height()) || dialog.height() < height){
				$(dialog).height(height+"px");
				$(".dialogContent",dialog).height(height - $(".dialogHeader", dialog).outerHeight() - $(".dialogFooter", dialog).outerHeight() - 6);
			}
			if(isNaN(dialog.css("width")) || dialog.width() < width) {
				$(dialog).width(width+"px");
			}
			
			var iTop = ($(window).height()-dialog.height())/2;
			dialog.css({
				left: ($(window).width()-dialog.width())/2,
				top: iTop > 0 ? iTop : 0
			});
		},
		/**
		 * 初始化半透明层
		 * @param {Object} resizable
		 * @param {Object} dialog
		 * @param {Object} target
		 */
		initResize:function(resizable, dialog,target) {
			$("body").css("cursor", target + "-resize");
			resizable.css({
				top: $(dialog).css("top"),
				left: $(dialog).css("left"),
				height:$(dialog).css("height"),
				width:$(dialog).css("width")
			});
			resizable.show();
		},
		/**
		 * 改变阴隐层
		 * @param {Object} target
		 * @param {Object} options
		 */
		repaint:function(target,options){
			var shadow = $("div.shadow");
			if(target != "w" && target != "e") {
				shadow.css("height", shadow.outerHeight() + options.tmove);
				$(".shadow_c", shadow).children().andSelf().each(function(){
					$(this).css("height", $(this).outerHeight() + options.tmove);
				});
			}
			if(target == "n" || target =="nw" || target == "ne") {
				shadow.css("top", options.otop - 2);
			}
			if(options.owidth && (target != "n" || target != "s")) {
				shadow.css("width", options.owidth + 8);
			}
			if(target.indexOf("w") >= 0) {
				shadow.css("left", options.oleft - 4);
			}
		},
		/**
		 * 改变左右拖动层的高度
		 * @param {Object} target
		 * @param {Object} tmove
		 * @param {Object} dialog
		 */
		resizeTool:function(target, tmove, dialog) {
			$("div[class^='resizable']", dialog).filter(function(){
				return $(this).attr("tar") == 'w' || $(this).attr("tar") == 'e';
			}).each(function(){
				$(this).css("height", $(this).outerHeight() + tmove);
			});
		},
		/**
		 * 改变原始层的大小
		 * @param {Object} obj
		 * @param {Object} dialog
		 * @param {Object} target
		 */
		resizeDialog:function(obj, dialog, target) {
			var oleft = parseInt(obj.style.left);
			var otop = parseInt(obj.style.top);
			var height = parseInt(obj.style.height);
			var width = parseInt(obj.style.width);
			if(target == "n" || target == "nw") {
				tmove = parseInt($(dialog).css("top")) - otop;
			} else {
				tmove = height - parseInt($(dialog).css("height"));
			}
			$(dialog).css({left:oleft,width:width,top:otop,height:height});
			$(".dialogContent", dialog).css("width", (width-12) + "px");
			$(".pageContent", dialog).css("width", (width-14) + "px");
			if (target != "w" && target != "e") {
				var content = $(".dialogContent", dialog);
				content.css({height:height - $(".dialogHeader", dialog).outerHeight() - $(".dialogFooter", dialog).outerHeight() - 6});
				content.find("[layoutH]").layoutH(content);
				$.pdialog.resizeTool(target, tmove, dialog);
			}
			$.pdialog.repaint(target, {oleft:oleft,otop: otop,tmove: tmove,owidth:width});
			
			$(window).trigger("resizeGrid");
		},
		close:function(dialog) {
			if(typeof dialog == 'string') dialog = $("body").data(dialog);
			var close = dialog.data("close");
			var go = true;
			if(close && $.isFunction(close)) {
				var param = dialog.data("param");
				if(param && param != ""){
					param = DWZ.jsonEval(param);
					go = close(param);
				} else {
					go = close();
				}
				if(!go) return;
			}
			if ($.fn.xheditor) {
				$("textarea.editor", dialog).xheditor(false);
			}
			$(dialog).unbind("click").hide();
			$("div.dialogContent", dialog).html("");
			$("div.shadow").hide();
			if($(dialog).data("mask")){
				var dlId = $(dialog).data("id");
				$("#"+dlId+"_dialogBackground").remove();
				//$("#dialogBackground").hide();
			} else{
				if ($(dialog).data("id")) $.taskBar.closeDialog($(dialog).data("id"));
			}
			$("body").removeData($(dialog).data("id"));
			var hasSupcan = $(dialog).data("hasSupcan");
			if(hasSupcan){
				if(hasSupcan=='all'){
					var supcanIdArr = supcanGridMap.keys();
					for(var s in supcanIdArr){
						var sId = supcanIdArr[s];
						try{
							eval('('+sId+')').func("GrayWindow", '0');
						}catch(e){
							//alert(e);
						}
					}
				}else{
					var supcanIds = hasSupcan.split(",");
					for(var s in supcanIds){
						var sId = supcanIds[s];
						try{
							eval('('+sId+')').func("GrayWindow", '0');
						}catch(e){
							//alert(e);
						}
						/*if(typeof(eval('('+sId+')'))!='undefined'&&typeof(eval('('+sId+')').func)=='function'){
							eval('('+sId+')').func("GrayWindow", '0');
						}*/
					}
				}
			}
			$(dialog).remove();
			$.pdialog._lastzIndex -= 2;
			/*jQuery('object').each(function(){
				var s = jQuery(this);
				var sId = s.attr('id');
				var sType = s.attr('type');
				if(sType=='application/supcan-plugin'){
					if(typeof(eval('('+sId+')').func)=='function'){
						eval('('+sId+')').func("GrayWindow", '0');
					}
				}
			});*/
		},
		closeCurrent:function(){
			this.close($.pdialog._current);
		},
		checkTimeout:function(){
			var $conetnt = $(".dialogContent", $.pdialog._current);
			var json = DWZ.jsonEval($conetnt.html());
			if (json && json.statusCode == DWZ.statusCode.timeout) this.closeCurrent();
		},
		maxsize:function(dialog) {
			$(dialog).data("original",{
				top:$(dialog).css("top"),
				left:$(dialog).css("left"),
				width:$(dialog).css("width"),
				height:$(dialog).css("height")
			});
			$("a.maximize",dialog).hide();
			$("a.restore",dialog).show();
			var iContentW = $(window).width();
			var iContentH = $(window).height() - 34;
			$(dialog).css({top:"0px",left:"0px",width:iContentW+"px",height:iContentH+"px"});
			$.pdialog._resizeContent(dialog,iContentW,iContentH);
			$(window).trigger("resize");
		},
		restore:function(dialog) {
			var original = $(dialog).data("original");
			var dwidth = parseInt(original.width);
			var dheight = parseInt(original.height);
			$(dialog).css({
				top:original.top,
				left:original.left,
				width:dwidth,
				height:dheight
			});
			$.pdialog._resizeContent(dialog,dwidth,dheight);
			$("a.maximize",dialog).show();
			$("a.restore",dialog).hide();
			$.pdialog.attachShadow(dialog);
			$(window).trigger("resize");
		},
		minimize:function(dialog){
			$(dialog).hide();
			$("div.shadow").hide();
			var task = $.taskBar.getTask($(dialog).data("id"));
			$(".resizable").css({
				top: $(dialog).css("top"),
				left: $(dialog).css("left"),
				height:$(dialog).css("height"),
				width:$(dialog).css("width")
			}).show().animate({top:$(window).height()-60,left:task.position().left,width:task.outerWidth(),height:task.outerHeight()},250,function(){
				$(this).hide();
				$.taskBar.inactive($(dialog).data("id"));
			});
		},
		_resizeContent:function(dialog,width,height) {
			var content = $(".dialogContent", dialog);
			content.css({width:(width-12) + "px",height:height - $(".dialogHeader", dialog).outerHeight() - $(".dialogFooter", dialog).outerHeight() - 6});
			content.find("[layoutH]").layoutH(content);
			$(".pageContent", dialog).css("width", (width-14) + "px");
			
			$(window).trigger("resizeGrid");
		}
	};
})(jQuery);