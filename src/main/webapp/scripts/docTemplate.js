function docTemplate(listMap,title,inputSize,footSize){
	var result = {
		containColumn:function(str){
			return contains(str,listMap);
		},
		getListName:function(key){
			return getDocName(key,listMap);
		},
		setDocLayOut:function(formId,listDiv,foorDiv){
			jQuery("#"+formId).find("div:first").width(getMixStrlen(title.trim())*13);
			var inputRow = Math.ceil(inputSize/5);
			var tableLayoutH = (inputRow*2 + 1)*10 + 120;
			jQuery("#"+listDiv).attr("layoutH",tableLayoutH);
			var footWidth = parseInt(100/footSize);
			jQuery("#"+foorDiv+" li").css("width",footWidth+"%");
		},
		disableButton:function(toolBarId){
			jQuery("#"+toolBarId+" li a").each(function(){
				disableLink([this.id]);
			});
		},
		clearData:function(cardId){
			jQuery("#"+cardId+" input").val("");
			jQuery("#"+cardId+" select").empty();
		}
	};
	return result;
}

function parseMap(map){
	var result = new Map();
	map  = map.substring(1,map.length-1);//去掉{}
	var arr = map.split(",");
	var key = '',value = '';
	for(var i=0;i<arr.length;i++){
		var item = arr[i];
		key = item.substring(0,item.indexOf("=")).trim();
		value = item.substring(item.indexOf("=")+1,item.length).trim();
		result.put(key,value);
	}
	return result;
}
	
function getDocName(key,map){
	var realMap = parseMap(map);
	return realMap.get(key);
}

function contains(item,map){
	var realMap = parseMap(map);
	return realMap.containsKey(item);
}