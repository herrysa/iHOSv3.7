
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java"   pageEncoding="UTF-8"%>
<script type="text/javascript">
	/*
		以下脚本为指标分析的画图函数，有需要可以提出公共脚本文件
		一、showAnalysisGraph 通过查询条件获取指标数据，形成画图的参数
		二、drawAnalysisGraph 画图功能入口，需要参数  canvas:画布ID，graphParams：画图参数
			对于图形的参数修改在这个函数里修改
		三、canvasSize 计算画布尺寸
		四、drawIndicator 画指标
		五、drawArrow 画数据连线
		六、animateZb 动画显示指标
		七、showZb 直接显示指标
		八、animateArrow 动画显示数据连线
		九、showArrow 直接显示数据连线
		十、drawOper 画操作符
	*/
	//获取指标数据，和画图的参数
	function showAnalysisGraph(checkPeriod,indicatorId,indicatorLevel,indicatorTypeCode,condition,random){
		$.ajax({
		    url: 'showAnalysisGraph?random='+random,
		    type: 'post',
		    dataType: 'json',
		    data:{checkPeriod:checkPeriod,indicatorId:indicatorId,indicatorLevel:indicatorLevel,indicatorTypeCode:indicatorTypeCode,condition:condition},
		    async:false,
		    error: function(data){
				alertMsg.error("系统错误！");
		    },
		    success: function(data){
		        var indicators = data.indicatorAnalysisList;
		        var levelMap = data.levelMap;
		        var proportion = jQuery("#graphProportion").val();
		        var animateType = jQuery("#graphAnimateType").val();
		        var graphParams = {indicators:indicators,indicatorMap:{},levelMap:levelMap,proportion:proportion,animateType:animateType};
		    	jQuery("#duBangCanvas").data("graphParams",graphParams);
		        drawAnalysisGraph("duBangCanvas",graphParams);
		    }
		});
	}
	//drawAnalysisGraph 画图功能入口函数
	function drawAnalysisGraph(canvas,graphParams){
		//清空画布
		jQuery('#'+canvas).clearCanvas();
        jQuery('#'+canvas).removeLayers();
        //画图参数
        //1.画布参数
        //indicators指标数据，proportion图形比例，animateType，动画类型
		var indicators = graphParams.indicators,levelMap = graphParams.levelMap,indicatorMap = graphParams.indicatorMap
			,proportion=parseFloat(graphParams.proportion),animateType=graphParams.animateType;
		//2.图形参数
		/*
			canvasWidth：画布宽度，800为初始值，后面会根据具体图形大小调整
			canvasHeight：换土高度
			margin_left、margin_top、margin_right、margin_bottom:画布边距
			indicatorWidth：指标方块宽度
			indicatorHeight：指标方块高度
			xjg：X方向间隔
			yjg：Y方向间隔
			operjg：操作符间隔
			fontSize：字体大小
			operSize：操作符大小
			nameAreaColor：指标名称背景颜色
			valueAreaColor：指标数值背景颜色
			nameTextColor：指标名称文字颜色
			valueTextColor：指标数字文字颜色
		*/
        var canvasWidth = 800 , canvasHeight = 600 ,margin_left = 30 ,margin_top = 30 ,margin_right = 30 ,margin_bottom = 30
			,indicatorWidth = Math.floor(130*proportion),indicatorHeight = Math.floor(60*proportion)
			,xjg = Math.floor(30*proportion) ,yjg=Math.floor(50*proportion),operjg = Math.floor(15*proportion)
			,fontSize = Math.floor(13*proportion),operSize = Math.floor(20*proportion)
			,nameAreaColor = "#40afde" ,valueAreaColor = "#D6D6D6" ,nameTextColor = "#FFFFFF" , valueTextColor = "#000000";
		//3.数据参数
		//levelMap存储每个level含指标数，indicatorMap是指标map，minSeqMap每个level第一个指标的序号
        levelMap = new Map(),indicatorMap = new Map(),minSeqMap = new Map();
		var minLevel = 10 , maxlevel = 0 , maxLeftOperLength = 0 ,maxRightOperLength = 0;
		//循环指标，组织上述结构
		for(var indicatorIndex in indicators){
			var indicator = indicators[indicatorIndex];
			indicatorMap.put(indicator.id,indicator);
			var level = indicator.level;
			var seq = indicator.seq;
			/* var leftOper = indicator.left_oper;
			var rightOper = indicator.right_oper;
			var leftoperc , rightoperc;
			if(leftOper&&leftOper!='null'&&maxLeftOperLength<leftOper.length){
				maxLeftOperLength = leftOper.length;
			}
			if(rightOper&&rightOper!='null'&&maxRightOperLength<rightOper.length){
				maxRightOperLength = rightOper.length;
			} */
			level = parseInt(level);
			seq = parseInt(seq);
			if(level<minLevel){
				minLevel = level;
			}
			if(level>maxlevel){
				maxlevel = level;
			}
			var levelNum = levelMap.get("level_"+level);
			
			if(!levelNum){
				levelMap.put("level_"+level,1);
			}else{
				levelNum++;
				levelMap.put("level_"+level,levelNum);
			}
			var minseq = minSeqMap.get("minSeq_"+level);
			if(!minseq||seq<minseq){
				minSeqMap.put("minSeq_"+level,seq);
			}
		}
		graphParams.levelMap = levelMap;
		graphParams.indicatorMap = indicatorMap;
		
		//组织层级遍历，为自下而上显示提供数据
		var levelTraverse = new Array();
		var levelPointer = maxlevel,seqPointer = 0,indicatorIndex = 0 ,indicatorNum = 0 ,zbIndex = 0, oldZbIndex = -1;
		while(indicatorNum!=indicators.length){
			var zb = indicators[indicatorIndex];
			var level = zb.level
				,seq = zb.seq;
			var maxSeq = levelMap.get("level_"+level);
			if(levelPointer==level){
				levelTraverse.push(zb);
				seqPointer++;
				indicatorNum++;
				if(seqPointer==maxSeq){
					levelPointer -= 1;
					seqPointer = 0;
				}
			}
			indicatorIndex++;
			if(indicatorIndex==indicators.length){
				indicatorIndex = 0;
			}
		}
		jQuery("#"+canvas).data("graphParams",graphParams);
		//margin_left += maxLeftOperLength * operjg ;
		//margin_top += maxLeftOperLength * operjg ;
		
		//计算画图尺寸
		var canvSize = canvasSize(levelMap,indicatorWidth,indicatorHeight,xjg,yjg,margin_left,margin_top,margin_right,margin_bottom);
		canvasWidth = canvSize.tWidth;
		canvasHeight = canvSize.tHeight;
		var maxXNum = canvSize.maxXNum;
		$('#'+canvas).attr('width',canvasWidth);
		$('#'+canvas).attr('height',canvasHeight);
		
		var instance = {id:canvas,indicators:indicators,levelMap:levelMap,animateType:animateType
						,width:canvasWidth,height:canvasHeight};
		var indicatorMap = new Map();
		instance.indicatorMap = indicatorMap;
		//循环指标数据，计算相应的坐标值，并画出起始位置，大小为0
		for(var indicatorIndex in indicators){
			var indicator = indicators[indicatorIndex];
			var id = indicator.id
				,level = indicator.level
				,seq = indicator.seq
				,parent = indicator.parent
				,value = indicator.value;
			var levelNum = levelMap.get("level_"+level);
			var minSeq = minSeqMap.get("minSeq_"+level);
			var xjgc = (maxXNum - levelNum)*(indicatorWidth+xjg)/(levelNum+1);
			seq = parseInt(seq);
			level = parseInt(level);
			var thisX = margin_left+indicatorWidth/2+xjgc;
			thisX += (seq-1-(minSeq-1))*(indicatorWidth+xjg+xjgc);
			indicator.x = thisX;
			
			var thisY = margin_top+indicatorHeight/4;
			thisY += (level-1-(minLevel-1))*(indicatorHeight+yjg);
			indicator.y = thisY;
			
			indicator.width = indicatorWidth;
			indicator.height = indicatorHeight;
			indicator.nameAreaColor = nameAreaColor;
			indicator.valueAreaColor = valueAreaColor;
			indicator.nameTextColor = nameTextColor;
			indicator.valueTextColor = valueTextColor;
			indicator.xjg = xjg;
			indicator.yjg = yjg;
			indicator.xjgc = xjgc;
			indicator.yjgc = 0;
			indicator.fontSize = fontSize;
			
			indicatorMap.put(id,indicator);
			drawIndicator(instance,indicator);
			
			var childrenIndicator = findChildren(instance,indicator);
			if(childrenIndicator.length>0){
				indicator.isParent = true;
				indicator.children = childrenIndicator;
			}
		}
		//循环指标数据，画出纸币连线起始位置
		for(var indicatorIndex in indicators){
			var indicator = indicators[indicatorIndex];
			var childrenIndicator = indicator.children;
			if(childrenIndicator){
				for(var cz=0;cz<childrenIndicator.length;cz++){
					var cId =childrenIndicator[cz];
					var toIndicator = indicatorMap.get(cId);
					drawArrow(instance,indicator,toIndicator);
				}
			}
		}
		//动画效果 animateType为1，自上而下显示，也就是根据指标数据顺序显示
		if(animateType==1){
			var zbIndex = 0 , oldZbIndex = -1;
			var drawTimer = setInterval(function(){
				if(zbIndex==indicators.length){
					clearInterval(drawTimer);
				}else if(zbIndex!=oldZbIndex){
					var zb = indicators[zbIndex];
					oldZbIndex = zbIndex;
					zbIndex = animateZb(zbIndex,instance,zb);
					
				}
			},1000);
		}else if(animateType==2){ //animateType为2，自下而上显示，使用前面整理的层级数组
			var zbIndex = 0, oldZbIndex = -1;
			var drawTimer = setInterval(function(){
				if(zbIndex==indicators.length){
					clearInterval(drawTimer);
				}else if(zbIndex!=oldZbIndex){
					var zb = levelTraverse[zbIndex];
					oldZbIndex = zbIndex;
					zbIndex = animateZb(zbIndex,instance,zb);
				}
			},1000);
		}else{//无动画效果，直接显示
			for(var indicatorIndex in indicators){
				var zb = indicators[indicatorIndex];
				showZb(instance,zb);
				
			}
		}
		
	}
	//计算画图尺寸
	function canvasSize(levelMap,zbWidth,zbHeight,xjg,yjg,margin_left,margin_top,margin_right,margin_bottom){
		var totalHeight = levelMap.size()*zbHeight+(levelMap.size()-1)*yjg; //总长度
		var maxXNum = findMaxNumInLevel(levelMap); //x方向最大个数
		var totalWidth = maxXNum*zbWidth+(maxXNum-1)*xjg; //总宽度
		return {tHeight:totalHeight+(margin_left+margin_right),tWidth:totalWidth+(margin_top+margin_bottom),maxXNum:maxXNum};
	}
	//计算指标最多的一层
	function findMaxNumInLevel(levelMap){
		var numArr = levelMap.values();
		return Math.max.apply(null, numArr);
	}
	//画指标
	function drawIndicator(instance,indicator){
		var canvas = instance.id;
		var id = indicator.id;
		var name = indicator.name;
		var	value = indicator.value;
		var	precision = indicator.precision;
		var fontSize = indicator.fontSize;
		var indicatorWidth = indicator.width;
		var parent = indicator.parent;
		var toPercent = indicator.toPercent;
		var unit = indicator.unit;
		var needSeparator = indicator.needSeparator;
		value = parseFloat(value);
		if(toPercent=='true'){
			value = value*100;
		}
		value = value.toFixed(precision);
		if(needSeparator=='true'){
			value = renderMoney(value);
		}
		value = ""+value+unit;
		
		var nameAreaColor = indicator.nameAreaColor;
		var valueAreaColor = indicator.valueAreaColor;
		var nameTextColor = indicator.nameTextColor;
		var valueTextColor = indicator.valueTextColor;
		
		$('#'+canvas).drawRect({
			  fillStyle: nameAreaColor,
			  layer: true,
			  groups: ['zb_'+id,parent],
			  name: id+'_n',
			  x: 60, y: 25,
			  width: 0,
			  height: 0,
			  mouseover: function(layer) {
				var canVasOffset = $('#'+canvas).position();
				$('#zdButton').css('left',layer.x+canVasOffset.left-indicatorWidth/2-10+'px');
				$('#zdButton').css('top',layer.y+canVasOffset.top-indicatorWidth/2+38+'px');
				$('#zdButton').attr("layerName",layer.name);
				$('#zdButton').show();
			  }
			})
			.drawRect({
			  fillStyle: valueAreaColor,
			  layer: true,
			  groups: ['zb_'+id,parent],
			  name: id+'_v',
			  x: 60, y: 50,
			  width: 0,
			  height: 0
			}).drawText({
			  fillStyle: nameTextColor,
			  layer: true,
			  groups: ['zb_'+id,parent],
			  name: id+'_nt',
			  strokeStyle: nameTextColor,
			  strokeWidth: 0.5,
			  x: 60, y: 25,
			  fontSize: 0,
			  fontFamily: '微软雅黑, sans-serif',
			  text: name
			}).drawText({
			  fillStyle: valueTextColor,
			  layer: true,
			  groups: ['zb_'+id,parent],
			  name: id+'_vt',
			  strokeStyle: valueTextColor,
			  strokeWidth: 0.5,
			  x: 60, y: 25,
			  fontSize: 0,
			  fontFamily: '微软雅黑, sans-serif',
			  text: ''+value
			});
	}
	//查找直接孩子节点
	function findChildren(instance,indicator){
		var id = indicator.id;
		var childrenZb = new Array();
		var indicators = instance.indicators;
		for(var indicatorIndex in indicators){
			var indicatorTemp = indicators[indicatorIndex];
			if(id==indicatorTemp.parent){
				childrenZb.push(indicatorTemp.id);
			}
		}
		return childrenZb;
	}
	//画数据连线
	function drawArrow(instance,fromZb,toZb){
		var canvas = instance.id;
		var groupName = fromZb.id;
		var fromId = fromZb.id,fromHeight = fromZb.height,fromYjg = fromZb.yjg,fromX = fromZb.x,fromY = fromZb.y ,fromXjgc = fromZb.xjgc
			,toId = toZb.id,toX = toZb.x,toY = toZb.y ,toXjgc = toZb.xjgc;
		var x1,x2,x3,x4,y1,y2,y3,y4;
		var dx = fromX - toX , dy = fromY - toY;
		//if(d<0){
			//alert(fromZb.name+':'+fromX+':'+fromXjgc);
			x1 = fromX
			,x2 = x1
			,x3 = toX
			,x4 = x3;
		//}
		if(dy<0){
			y1 = fromY + (3*fromHeight/4)
			,y2 = y1 + fromYjg/2
			,y3 = y2
			,y4 = y3 + fromYjg/2;
		}else{
			y1 = fromY - (fromHeight/4)
			,y2 = y1 - fromYjg/2
			,y3 = y2
			,y4 = y3 - fromYjg/2;
		}
		toZb.arrow = {x1:x1,x2:x2,x3:x3,x4:x4,y1:y1,y2:y2,y3:y3,y4:y4};
		$('#'+canvas).drawLine({
			  strokeStyle: '#000',
			  layer: true,
			  groups: [groupName],
			  name:'line_'+toId,
			  strokeWidth: 1.5,
			  rounded: true,
			  startArrow: false,
			  endArrow: false,
			  arrowRadius: 10,
			  arrowAngle: 65,
			  x1: x1, y1: y1,
			  x2: x1, y2: y1,
			  x3: x1, y3: y1,
			  x4: x1, y4: y1
			});
			
	}
	//动画显示指标
	function animateZb(zbIndex,instance,zb){
		var canvas = instance.id;
		var id = zb.id
			,name = zb.name
			,parent = zb.parent
			,thisX = zb.x
			,thisY = zb.y
			,zbWidth = zb.width
			,zbHeight = zb.height
			,xjgc = zb.xjgc
			,fontSize = zb.fontSize
			,parent = zb.parent;
		$('#'+canvas).animateLayer(id+'_n', {
			width: zbWidth, height: zbHeight/2,
			x:thisX,y: thisY

			});
		$('#'+canvas).animateLayer(id+'_v', {
			width: zbWidth, height: zbHeight/2,
			x:thisX,y: thisY+zbHeight/2

			});
		$('#'+canvas).animateLayer(id+'_nt', {
			width: zbWidth, height: zbHeight/2, fontSize:fontSize,
			x:thisX,y: thisY

			});
		$('#'+canvas).animateLayer(id+'_vt', {
			width: zbWidth, height: zbHeight/2,fontSize:fontSize,
			x:thisX,y: thisY+zbHeight/2

			});
		drawOper(canvas,zb);
		if(zb.isParent){
			var childrenZb = zb.children;
			var indicatorMap = instance.indicatorMap;
			for(var cz=0;cz<childrenZb.length;cz++){
				var childZb = indicatorMap.get(childrenZb[cz]);
				var arrow = childZb.arrow;
				//console.log(childZb.id+':'+arrow.x1+','+arrow.y1+':'+arrow.x2+','+arrow.y2+':'+arrow.x3+','+arrow.y3);
				animateArrow(canvas,childZb.id,arrow.x2,arrow.y2,arrow.x3,arrow.y3,arrow.x4,arrow.y4);
			}
		}

	/*			$('#myCanvas').animateLayerGroup('zb_'+id, {
			width: zbWidth, height: zbHeight/2,
			step: function( now, fx ){
				console.log(now.name+'------'+id);
				if(now.name==id+'_v' ){
					now.x = thisx;
					now.y = thisy+30;
				}if(now.name==id+'_vt' ){
					now.x = thisx;
					now.y = thisy+30;
				}else{
					now.x = thisx;
					now.y = thisy;
				}
			}
		}, 1000, function(layer) {
			/*if(layer.name==id+'_v'){
				$(this).animateLayer(layer, {
					x: x, y: y,
					rotate: 360
				}, 'slow', 'swing');
			}if(layer.name==id+'_vt'){
				$(this).animateLayer(layer, {
					x: x, y: y,
					rotate: 360
				}, 'slow', 'swing');
			}else{
				$(this).animateLayer(layer, {
					x: x, y: y+30,
					rotate: 360
				}, 'slow', 'swing');
			}
		});*/
		return (zbIndex+1);
	}
	//直接显示指标
	function showZb(instance,zb){
		var canvas = instance.id;
		var id = zb.id
			,name = zb.name
			,parent = zb.parent
			,thisX = zb.x
			,thisY = zb.y
			,xjgc = zb.xjgc
			,parent = zb.parent
			,zbWidth = zb.width
			,zbHeight = zb.height
			,fontSize = zb.fontSize;
		$('#'+canvas).setLayer(id+'_n', {
			width: zbWidth, height: zbHeight/2,
			x:thisX,y: thisY

			}).drawLayers();;
		$('#'+canvas).setLayer(id+'_v', {
			width: zbWidth, height: zbHeight/2,
			x:thisX,y: thisY+zbHeight/2

			}).drawLayers();;
		$('#'+canvas).setLayer(id+'_nt', {
			width: zbWidth, height: zbHeight/2, fontSize:fontSize,
			x:thisX,y: thisY

			}).drawLayers();;
		$('#'+canvas).setLayer(id+'_vt', {
			width: zbWidth, height: zbHeight/2, fontSize:fontSize,
			x:thisX,y: thisY+zbHeight/2

			}).drawLayers();;
			drawOper(canvas,zb);
		if(zb.isParent){
			var childrenZb = zb.children;
			var indicatorMap = instance.indicatorMap;
			for(var cz=0;cz<childrenZb.length;cz++){
				var childZb = indicatorMap.get(childrenZb[cz]);
				var arrow = childZb.arrow;
				showArrow(canvas,childZb.id,arrow.x2,arrow.y2,arrow.x3,arrow.y3,arrow.x4,arrow.y4);
			}
		}
	}

	//动画显示数据连线
	function animateArrow(canvas,zbId,x2,y2,x3,y3,x4,y4){
		//console.log(zbId+':'+x2+':'+y2+':'+x3+y3);
		$('#'+canvas).animateLayer('line_'+zbId,{
			endArrow: true,
					x2: x2, y2: y2,
					x3: x2, y3: y2,
					x4: x2, y4: y2
		});
		$('#'+canvas).animateLayer('line_'+zbId,{
					x3: x3, y3: y3,
					x4: x3, y4: y3
		});
		$('#'+canvas).animateLayer('line_'+zbId,{
					x4: x4, y4: y4
		});
	}
	//直接显示数据连线
	function showArrow(canvas,zbId,x2,y2,x3,y3,x4,y4){
		//console.log(zbId+':'+x2+':'+y2+':'+x3+y3);
		$('#'+canvas).setLayer('line_'+zbId,{
			endArrow: true,
					x2: x2, y2: y2,
					x3: x3, y3: y3,
					x4: x4, y4: y4
		}).drawLayers();
	}
	
	//画操作符
	function drawOper(canvas,indicator){
		var leftOper = indicator.left_oper
			,rightOper = indicator.right_oper
			,id = indicator.id
			,xjg = indicator.xjg
			,indicatorX = indicator.x
			,indicatorY = indicator.y
			,indicatorWidth = indicator.width
			,indicatorHeight = indicator.height
			,indicatorXjgc = indicator.xjgc;
		var groupName = indicator.parent;
		var parentIndicator = findIndicator(canvas,groupName);
		if(!parentIndicator){
			return ;
		}
		if(leftOper&&leftOper!='null'){
			var leftLength = leftOper.length;
			for(var l=0;l<leftLength;l++){
				var oper = leftOper.substring(l,l+1);
				thisX = indicatorX-(indicatorWidth+indicatorXjgc)/2-(Math.floor(leftLength/2)-l+1)*15;
				$('#'+canvas).drawText({
					  fillStyle: '#000000',
					  layer: true,
					  groups: [groupName],
					  name: 'leftOper_'+id+'_'+l,
					  strokeStyle: '#000',
					  strokeWidth: 0.8,
					  x: thisX, y: indicatorY+indicatorHeight/4,
					  fontSize: 18,
					  fontFamily: '微软雅黑, sans-serif',
					  text: oper
					});
			}
		}
		
		if(rightOper&&rightOper!='null'){
			var rightLength = rightOper.length;
			for(var r=0;r<rightLength;r++){
				var oper = rightOper.substring(r,r+1);
				thisX = indicatorX+(indicatorWidth+indicatorXjgc)/2+(Math.floor(r/2)+1)*15;
				$('#'+canvas).drawText({
					  fillStyle: '#000000',
					  layer: true,
					  groups: [groupName],
					  name: 'rightOper_'+id+'_'+r,
					  strokeStyle: '#000',
					  strokeWidth: 0.8,
					  x: thisX, y: indicatorY+indicatorHeight/4,
					  fontSize: 18,
					  fontFamily: '微软雅黑, sans-serif',
					  text: oper
					});
			}
		}
	}
	//根据ID查找指标
	function findIndicator(canvas,id){
		var graphParams = jQuery("#"+canvas).data("graphParams");
		var indicatorMap = graphParams.indicatorMap;
		var indicator;
		if(indicatorMap){
			indicator = indicatorMap.get(id);
		}
		return indicator;
	}
	
	function hideOrShowGroup(indicatorMap,groupName,hide){
		$('#duBangCanvas').setLayerGroup(groupName, {
			  visible:!hide
		}).drawLayers();
		var indicator = indicatorMap.get(groupName);
		var children = indicator.children;
		if(children){
			for(var c=0;c<children.length;c++){
				var id = children[c];
				hideOrShowGroup(indicatorMap,id,hide);
			}
		}
	}
	
	/*数字千分符*/  
	function renderMoney(v) {  
	    if(isNaN(v)){  
	        return v;  
	    }  
	    //v = (Math.round((v - 0) * 100)) / 100;  
	    v = (v == Math.floor(v)) ? v + ".00" : ((v * 10 == Math.floor(v * 10)) ? v  
	            + "0" : v);  
	    v = String(v);  
	    var ps = v.split('.');  
	    var whole = ps[0];  
	    var sub = ps[1] ? '.' + ps[1] : '.00';  
	    var r = /(\d+)(\d{3})/;  
	    while (r.test(whole)) {  
	        whole = whole.replace(r, '$1' + ',' + '$2');  
	    }  
	    v = whole + sub;  
	      
	    return v;  
	}
	// 画图函数结束
	
	
	function initGraphSearchFormValue(){
		var indicatorId = "${indicatorId}";
		var checkPeriod = "${checkPeriod}";
		var indicatorLevel = "${indicatorLevel}";
		if(checkPeriod){
			jQuery("#indicatorAnalysisGraph_search_checkPeriod").val(checkPeriod);
		}
		if(indicatorId){
			jQuery("#indicatorAnalysisGraph_search_indicator_radio").trigger("click");
			jQuery("#indicatorAnalysisGraph_search_indicator").val("${indicatorName}");
			jQuery("#indicatorAnalysisGraph_search_indicator_id").val(indicatorId);
		}
		if(indicatorLevel){
			jQuery("#indicatorAnalysisGraph_search_indicator_level_radio").trigger("click");
			jQuery("#indicatorAnalysisGraph_search_indicator_level").val(indicatorLevel);
		}
		reloadIndicatorAnalysisGraph();
	}
	function initIndicatorValue(){
		var checkPeriod = jQuery("#indicatorAnalysisGraph_search_checkPeriod").val();
		if(!checkPeriod){
			alertMsg.error("请选择期间!");
			return;
		}
		$.ajax({
		    url: 'initIndicatorValue',
		    type: 'post',
		    data:{indicatorTypeCode:"${indicatorTypeCode}",checkPeriod:checkPeriod},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		        if(data.statusCode==200){
		        	alertMsg.correct(data.message);
		        	reloadIndicatorAnalysisGraph();
		        }else{
		        	alertMsg.error(data.message);
		        }
		    }
		});
	}
	function calculateIndicatorValue(){
		var checkPeriod = jQuery("#indicatorAnalysisGraph_search_checkPeriod").val();
		if(!checkPeriod){
			alertMsg.error("请选择期间!");
			return;
		}
		$.ajax({
		    url: 'executeIndicatorSp',
		    type: 'post',
		    data:{indicatorTypeCode:"${indicatorTypeCode}",checkPeriod:checkPeriod,spName:"sp_GL_keyCal"},
		    dataType: 'json',
		    aysnc:false,
		    error: function(data){
		    },
		    success: function(data){
		        if(data.statusCode==200){
		        	$.ajax({
		    		    url: 'calculateIndicatorValue',
		    		    type: 'post',
		    		    data:{indicatorTypeCode:"${indicatorTypeCode}",checkPeriod:checkPeriod},
		    		    dataType: 'json',
		    		    aysnc:false,
		    		    error: function(data){
		    		    },
		    		    success: function(data){
		    		        if(data.statusCode==200){
		    		        	alertMsg.correct(data.message);
		    		        	reloadIndicatorAnalysisGraph();
		    		        }else{
		    		        	alertMsg.error(data.message);
		    		        }
		    		    }
		    		});
		        }else{
		        	alertMsg.error(data.message);
		        }
		    }
		});
	}
	
	function singleSelectCon(obj){
		var val = obj.value;
		jQuery("#indicatorAnalysisGraph_search_indicator_"+obj.value).removeAttr("disabled","disabled").removeAttr("readonly").removeClass("readonly");
		if("id"===val){
			jQuery("#indicatorAnalysisGraph_search_indicator").removeAttr("disabled","disabled").removeAttr("readonly").removeClass("readonly");
			jQuery("#indicatorAnalysisGraph_search_indicator_level").attr("disabled","disabled").attr("readonly","readonly").addClass("readonly");
		}else{
			jQuery("#indicatorAnalysisGraph_search_indicator_id").attr("disabled","disabled").attr("readonly","readonly");
			jQuery("#indicatorAnalysisGraph_search_indicator").attr("disabled","disabled").attr("readonly","readonly").addClass("readonly");
		}
	}
	
	function checkInputLevel(obj){
		if(obj.value){
			var reg = /^\d+$/ ;
			if(!reg.test(obj.value)){
				alertMsg.error("层级必须为正整数!");
				obj.value="";
				return;
			}
		}
	}
	
	function switchGraphAndGrid(redirectType){
		var url = "switchGraphAndGrid?indicatorTypeCode=${indicatorTypeCode}&redirectType="+redirectType;
		url += "&checkPeriod="+jQuery("#indicatorAnalysisGraph_search_checkPeriod").val();
		var indicatorIdRadio = jQuery("#indicatorAnalysisGraph_search_indicator_radio").attr("checked");
		if(indicatorIdRadio){
			url += "&indicatorId="+jQuery("#indicatorAnalysisGraph_search_indicator_id").val();
		}else{
			url += "&indicatorLevel="+jQuery("#indicatorAnalysisGraph_search_indicator_level").val();
		}
		navTab.reload(url, {
			title : "New Tab",
			fresh : false,
			data : {}
		});
	}
	
	function reloadIndicatorAnalysisGraph(){
		var indicatorTypeCode = "${indicatorTypeCode}";
		var checkPeriod = jQuery("#indicatorAnalysisGraph_search_checkPeriod").val();
		var indicatorId = jQuery("#indicatorAnalysisGraph_search_indicator_id").val();
		var indicatorLevel = jQuery("#indicatorAnalysisGraph_search_indicator_level").val();
		var condition = jQuery("input[name=indicatorAnalysisGraphCondition]:checked").val();
		showAnalysisGraph(checkPeriod,indicatorId,indicatorLevel,indicatorTypeCode,condition,Math.random());
	}
	
	//document ready
	jQuery(document).ready(function() { 
		var checkPeriod = "${checkPeriod}" ,indicatorId = "${indicatorId}", indicatorLevel = "${indicatorLevel}" ,condition = "${condition}" , indicatorTypeCode = "${indicatorTypeCode}" ;
		jQuery("input[name=indicatorAnalysisGraphCondition][value="+condition+"]").attr("checked","true");
		jQuery("#indicatorAnalysisGraph_search_indicator_level").val(indicatorLevel);
		
		showAnalysisGraph(checkPeriod,indicatorId,indicatorLevel,indicatorTypeCode,condition);
		
		jQuery("#indicatorAnalysisGraph_search_indicator").treeselect({
			dataType : "sql",
			optType : "single",
			sql : "SELECT  id id,name name,parentId parent FROM t_indicator where indicatorTypeId = '${indicatorTypeCode}'",
			initSelect:indicatorId,
			selectParent:true,
			exceptnullparent : false,
			lazy : false
		});

		//jQuery("#indicatorAnalysisGraph_div").load("showAnalysisGraph");
		jQuery("#graphProportion").blur(function(){
			var v = jQuery(this).val();
			if(isNaN(v)||v>3){
				alertMsg.error("请输入小于3的数字！");
				jQuery(this).val("1");
				jQuery(this).focus();
			}else if(v<=0){
				alertMsg.error("请输入大于0的数字！");
				jQuery(this).val("1");
				jQuery(this).focus();
			}
		});
		jQuery("#reGraphAnalysisGraph").click(function(){
			var proportion = jQuery("#graphProportion").val();
	        var animateType = jQuery("#graphAnimateType").val();
	        if(!proportion){
	        	proportion = 1;
	        	jQuery("#graphProportion").val(1);
	        }
	        var graphParams = jQuery("#duBangCanvas").data("graphParams");
	        graphParams.proportion = proportion;
	        graphParams.animateType = animateType;
	        //var graphParams = {indicators:indicators,levelMap:levelMap,proportion:proportion,animateType:animateType};
	        drawAnalysisGraph("duBangCanvas",graphParams);
		});
		jQuery("#exportGraph").click(function(){
			var graphStr = jQuery('#duBangCanvas').getCanvasImage('png');
			graphStr = graphStr.replace("data:image/png;base64,","");
			$.ajax({
			    url: 'downloadAnalysisGraph?indicatorTypeCode=${indicatorTypeCode}',
			    type: 'post',
			    dataType: 'json',
			    data:{'graphStr':graphStr},
			    async:false,
			    error: function(data){
					alertMsg.error("系统错误！");
			    },
			    success: function(data){
			    	var downLoadFileName = data["message"];
			    	var filePathAndName = downLoadFileName.split("@@");
					var url = "${ctx}/downLoadFile?filePath="+filePathAndName[0]+"&fileName="+filePathAndName[1];
			 		//url=encodeURI(url);
			 		location.href=url; 
			    }
			});
		});

		initGraphSearchFormValue();
		
		jQuery("#zdButton").click(function(){
			var layerName = jQuery(this).attr("layerName");
			var hideOrShow = jQuery(this).attr("hideOrShow");
			var groupName = layerName.split("_")[0];
			
			 var graphParams = jQuery("#duBangCanvas").data("graphParams");
			 var indicatorMap = graphParams.indicatorMap;
			if(!hideOrShow){
				jQuery(this).attr("hideOrShow" , "hide");
			 	jQuery(this).find("img").attr("src",'${ctx}/styles/themes/${themeName}/ihos_images/fold.png');
			 	hideOrShowGroup(indicatorMap,groupName,true);
			}else if(hideOrShow=="show"){
				jQuery(this).attr("hideOrShow" , "hide");
				 jQuery(this).find("img").attr("src",'${ctx}/styles/themes/${themeName}/ihos_images/fold.png');
				 hideOrShowGroup(indicatorMap,groupName,true);
			}else if(hideOrShow=="hide"){
				jQuery(this).attr("hideOrShow" , "show");
				 jQuery(this).find("img").attr("src",'${ctx}/styles/themes/${themeName}/ihos_images/unfold.png');
				 hideOrShowGroup(indicatorMap,groupName,false);
			}
			//$('#duBangCanvas').removeLayerGroup(groupName);
		});
		/* jQuery("#zdButton").mouseover(function(e) {
			jQuery("#duBangCanvas").unbind('mouseout');
		}).mouseout(function(e) {
			jQuery("#duBangCanvas").unbind('mouseout').bind('mouseout',function(){
				$('#zdButton').hide();
			});
		}); */
		/* jQuery("#duBangCanvas").mouseout(function(e) {
			$('#zdButton').hide();
		}); */
	});
</script>

<div class="page">
	<div id="indicatorAnalysisGraph_pageHeader" class="pageHeader">
		<div class="searchBar">
			<div class="searchContent">
			<%-- <s:hidden id="indicatorTypeCode" name="indicatorTypeCode"></s:hidden> --%>
				<form id="indicatorAnalysisGraph_search_form" >
					<label style="float:none;white-space:nowrap" >
						<s:text name='indicatorAnalysis.checkPeriod'/>:
						<input id="indicatorAnalysisGraph_search_checkPeriod" type="text" name="filter_EQS_checkPeriod" onClick="WdatePicker({skin:'ext',dateFmt:'yyyyMM'})" size="10" value="${checkPeriod}"/>
					</label>
					<input type="radio" name="indicatorAnalysisGraphCondition" id="indicatorAnalysisGraph_search_indicator_radio" onclick="singleSelectCon(this)" value="id" checked="checked"/>
					<s:text name='indicatorAnalysis.indicator'/>:
					<label style="float:none;white-space:nowrap" >
						<input type="text" id="indicatorAnalysisGraph_search_indicator" size="40"/>
						<input type="hidden" id="indicatorAnalysisGraph_search_indicator_id" name="filter_EQS_indicator.id"/>
					</label>
					<input type="radio" name="indicatorAnalysisGraphCondition" onclick="singleSelectCon(this)" value="level" id="indicatorAnalysisGraph_search_indicator_level_radio"/>
					<label style="float:none;white-space:nowrap" >
						<s:text name='层级'/>:
						<input type="text" name="filter_LEI_indicator.level" disabled="disabled" size="10" id="indicatorAnalysisGraph_search_indicator_level" onblur="checkInputLevel(this)"class="readonly"/>
					</label>
				</form>
			</div>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="reloadIndicatorAnalysisGraph()"><s:text name='button.search'/></button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="pageContent">
		<div class="panelBar">
			<ul class="toolBar">
				<li>
					<a class="zbcomputebutton" href="javaScript:initIndicatorValue()" ><span><s:text name="初始化" /></span></a>
				</li>
				<li>
					<a class="zbcomputebutton" href="javaScript:calculateIndicatorValue()" ><span><s:text name="计算" /></span></a>
				</li>
				<li>
					<a class="getdatabutton"  href="javaScript:switchGraphAndGrid('grid')"><span><s:text name="列表显示" /></span></a>
				</li>
			</ul>
		</div>
		<div id="indicatorAnalysisGraph_div" layoutH=90>
			<div id="" style="margin:10px">
				图形比例：<s:textfield id="graphProportion" value="1" size="5"></s:textfield>
				动画类型：<s:select id="graphAnimateType" list="#{0:'无',1:'自上而下',2:'自下而上'}"/>
				<button id="reGraphAnalysisGraph">重新显示</button>
				<button id="exportGraph">导出图片</button>
				<!-- <div class="button"><div class="buttonContent"><button>重新显示</button></div></div>
				<div class="button"><div class="buttonContent"><button>导出图片</button></div></div> -->
		</div>
			<div>
			<canvas id='duBangCanvas' width='800' height='600'></canvas>
			<div id='zdButton' style='position:absolute;left:0;top:0;color:red;display:none'><img src="${ctx}/styles/themes/${themeName}/ihos_images/unfold.png" width="25px" height="20px"/></div>
			</div>
		</div>
	</div>
</div>