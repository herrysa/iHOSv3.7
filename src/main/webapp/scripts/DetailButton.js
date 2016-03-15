/*
 * @author zzh
 * @description make detailButton for input or some div
 */
(function($){
	$.fn.detailButton = function(opts){
		
		return this.each(function(){
			var $this = $(this);
			var db = new DetailButton(opts);
			removeTempDiv();
			var mouseleaveTimer = null;
			if(db.opts.type=="click"){
				$this.unbind('blur').bind('blur', function(){
					removeDetailButton(db);
				});
				$this.unbind('click').bind('click', function(){
					addDetailButton($this,db);
					$("#"+db.opts.tempDivId).hover(function(){ 
						$this.unbind('blur');
					},function(){
						$this.unbind('blur').bind('blur', function(){
							removeDetailButton(db);
						});
					});
					$("#"+db.opts.tempDivId).unbind('click').bind('click',function(){
						if(typeof(db.opts.dealFuction)=='function'){
							db.opts.dealFuction($this);
						}
						removeDetailButton(db);
					});
				});
				
				if(db.opts.hostAction=='dblclick'){
					$this.unbind('dblclick').bind('dblclick', function(){
						$this.trigger('click');
					});
				}
				
			}else{
				$this.unbind('mouseleave').bind("mouseleave ",function(){
					mouseleaveTimer = setTimeout(function(){
						removeDetailButton(db);
					},100);
				});
				$this.unbind('mouseenter').bind("mouseenter ",function(){
					addDetailButton($this,db);
					$("#"+db.opts.tempDivId).hover(function(){
						clearTimeout(mouseleaveTimer);
					},function(){
						removeDetailButton(db);
					});
					
					$("#"+db.opts.tempDivId).unbind('click').bind('click',function(){
						if(typeof(db.opts.dealFuction)=='function'){
							db.opts.dealFuction($this);
						}
						removeDetailButton(db);
					});
				});
				
			}
		});
		
	
	function addDetailButton($this,db){
		if($("#"+db.opts.tempDivId).length){
			$("#"+db.opts.tempDivId).remove();
		}
		var addDiv = "<div id='"+db.opts.tempDivId+"'><a href='"+db.opts.url+"' style='display:block;cursor:pointer;width:22px;height:22px;background: url(./dwz/themes/default/images/button/imgX.gif) no-repeat;background-position: -160px 0px;'></a></div>";
		$("body").append( $( addDiv ) );
		var left = $this.offset().left;
		var top = $this.offset().top;
		var textWidth = $this.innerWidth();
		var textHeight = $this.innerHeight();
		$("#"+db.opts.tempDivId).css({position:'absolute',top:(top+textHeight-18)+'px',left:(left+textWidth-20)+'px',width:'20px',height:"20px","z-index":200});
	}
	
	function removeDetailButton(db){
		if($("#"+db.opts.tempDivId).length){
			$("#"+db.opts.tempDivId).remove();
		}
	}
	
	function removeTempDiv(){
		$("div[id^=isbTemp]").each(function(index, doEle){
			if($(doEle).size()){
				$(doEle).remove();
			}
		})
	}
	}
	var DetailButton = function(opts) {
		this.opts = $.extend({
			tempDivId : "isbTemp"+Math.floor(Math.random()*100),
			url:'javaScript:',
			type:'label',
			dealFuction:null,
			hostAction:null
		}, opts);
	}
})(jQuery);
