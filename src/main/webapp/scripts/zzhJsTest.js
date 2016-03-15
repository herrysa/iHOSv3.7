var debugMap = new Map();
var zzhJsTest = {

	init: function(op) {
		this._op = op;
	},
	debug: function(msg) {
		//if(this._set.debug){
		if (true) {
			if (typeof(console) != "undefined") console.log(msg);
			else alert(msg);
		}
	},
	testTimeEffect: function(executeMethod, testName) {
		var testName = testName ? testName : "";
		var g_beginTime = new Date();
		if (typeof(executeMethod) == 'function') {
			executeMethod();
		}
		var endT = new Date();
		var endTime = endT.getTime();
		var spTime = (endTime - g_beginTime);
		this.debug(testName + '执行时间:' + spTime + '毫秒.');
	}

	/*combineDebug = {
		push : function(){
			return 
		},


	}*/
}

	function Map() {
		this.elements = new Array();
		this.size = function() {
			return this.elements.length;
		}
		this.isEmpty = function() {
			return (this.elements.length < 1);
		}
		this.clear = function() {
			this.elements = new Array();
		}
		this.put = function(_key, _value) {
			this.remove(_key);
			this.elements.push({
				key: _key,
				value: _value
			});
		}
		this.remove = function(_key) {
			try {
				for (i = 0; i < this.elements.length; i++) {
					if (this.elements[i].key == _key) {
						this.elements.splice(i, 1);
						return true;
					}
				}
			} catch (e) {
				return false;
			}
			return false;
		}
		this.get = function(_key) {
			try {
				for (i = 0; i < this.elements.length; i++) {
					if (this.elements[i].key == _key) {
						return this.elements[i].value;
					}
				}
			} catch (e) {
				return null;
			}
		}
		this.element = function(_index) {
			if (_index < 0 || _index >= this.elements.length) {
				return null;
			}
			return this.elements[_index];
		}
		this.containsKey = function(_key) {
			try {
				for (i = 0; i < this.elements.length; i++) {
					if (this.elements[i].key == _key) {
						return true;
					}
				}
			} catch (e) {
				return false;
			}
			return false;
		}
		this.values = function() {
			var arr = new Array();
			for (i = 0; i < this.elements.length; i++) {
				arr.push(this.elements[i].value);
			}
			return arr;
		}
		this.keys = function() {
			var arr = new Array();
			for (i = 0; i < this.elements.length; i++) {
				arr.push(this.elements[i].key);
			}
			return arr;
		}
	}