<%@ page contentType="text/html; charset=GBK"%>
<script language="javascript">
var getById = function (name) {
	return document.getElementById(name);
};
var _ = function (tagName) {
	return document.createElement(tagName);
};

function random() {
	var date = new Date();
	return Date.UTC(date.getFullYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), date.getMilliseconds());
}

var Suggest = {
	scan: false,
	url: "<%=System.getProperty("home_url")%>seldepartment.action?flag=0&key=",
	element: null,
	obj: null,
	obj1:null,
	line: null,
	time: 10,
	inputs: null,
	thread: -1,
	init: function () {
		//this.element = $("suggestLoader");
		this.getElement();
		this.inputs = document.getElementsByName("suggestInputText");
		for (var i = 0; i < this.inputs.length; i++) {
			this.inputs[i].valueOld = this.inputs[i].value; 
		}
	},
	getElement: function () {
		if (this.element != null) {
			document.body.removeChild(this.element);
			this.element = null;
		}
		this.element = _("script");
		this.element.type = "text/javascript";
		this.element.language = "javascript";
		document.body.appendChild(this.element);
		return this.element;
	},
	check: function () {
		if (Suggest.scan == true) {
				if (Suggest.obj.valueOld != Suggest.obj.value && "" != Suggest.obj.value) {
					Suggest.obj.valueOld = Suggest.obj.value;
					Suggest.getData();
				}
				else if (Suggest.obj.value == "" || Suggest.obj.value == "¼òÂë/Ãû³Æ") {
					Suggest.obj.valueOld = Suggest.obj.value;
					Suggest.clean();
				}
			Suggest.thread = window.setTimeout(Suggest.check, Suggest.time);
		}
	},
	getData: function () {
		this.getElement();
		
		this.element.src = "<%=System.getProperty("home_url")%>seldepartment.action?flag=1&key=" + this.obj.value;
		
	},
	focusObj: function (obj,obj1) {
		Suggest.flag=false;
		Suggest.obj = obj;
		Suggest.obj1 = obj1;
		obj.symbol = null;
		if (obj.value == '¼òÂë/Ãû³Æ') {
			obj.style.color = "#000000";
			obj.value = '';
		}
		if (obj.value != '' && obj.value != '¼òÂë/Ãû³Æ' ) {
			Suggest.getData();
		}
		Suggest.scan = true;
		Suggest.check();
	},
	
	blurObj: function (obj, evt) {
		Suggest.clean(obj);
		if (obj.value == '') {
			obj.value = '¼òÂë/Ãû³Æ';
			obj.style.color = "#999999";
		}
		Suggest.scan = false;
		return false;
	},
	keyDown: function (obj, evt) {
		if (this.obj.value != "") {
			switch (evt.keyCode) {
				case 38: //up
					this.scan = false;
					if (this.line == null || this.line.rowIndex == 1) {
						this.setLine(this.obj.suggestBody.rows[this.obj.suggestBody.rows.length - 1]);
					}
					else {
						this.setLine(this.obj.suggestBody.rows[this.line.rowIndex - 1]);
					}
					return false;
					break;
				case 40: //down
					this.scan = false;
					if (this.line == null || this.line.rowIndex == this.obj.suggestBody.rows.length - 1) {
						this.setLine(this.obj.suggestBody.rows[1]);
					}
					else {
						this.setLine(this.obj.suggestBody.rows[this.line.rowIndex + 1]);
					}
					return false;
					break;
				case 13: //Enter
					Suggest.blurObj(obj, evt);

					break;
				case 9: //Tab
					break;
				default:
					this.setLine(null);
					this.scan = true;
					this.check();
					break;
			}
		
		}
		return true;
	},
	setLine: function (line) {
		if (line != null) {
			if (this.line != null) {
				this.discolorLine(this.line);
			}
			this.line = line;
			this.colorLine(line);
			this.obj.value = line.cells[2].innerHTML;
			this.obj1.value = line.cells[0].innerHTML;
			//this.obj.symbol = line.cells[1].innerHTML;
		}
		else {
			this.line = null;
		}
	},
	colorLine: function (line) {
		line.className = "selected";
	},
	discolorLine: function (line) {
		line.className = "";
	},
	clean: function () {
		$(Suggest.obj.id + "_suggest").innerHTML = "";
	}
};
var everydatal = 0;
var everydatalLast = 0;
var everydata = new Array();
function showCnt(length, data) {
	everydatalLast = everydatal;
	Suggest.clean();
	if (length != 0) {
		var result = new String();
		result += '<table class="SuggesTable" border="0" cellpadding="0" cellspacing="0">';
		result += '<tr bgcolor="#cccccc" ><td>Ñ¡Ïî</td><td>´úÂë</td><td>Ãû³Æ</td></tr>';
		for (var i in data) {
			var tempArray1 = data[i].split("	");
			var tempArray2 = tempArray1[1].split("-");
			result += '<tr onmouseover="Suggest.colorLine(this);" onmouseout="Suggest.discolorLine(this);" onmousedown="Suggest.setLine(this);"><td>' + tempArray1[0] + '</td><td>' + tempArray2[1] + '</td><td>' + tempArray2[2] + '</td></tr>';
		}
		result += '</table>';
		$(Suggest.obj.id + "_suggest").innerHTML = result;
		Suggest.obj.suggestBody = getById(Suggest.obj.id + "_suggest").childNodes[0];
		if (length == 1) {
			Suggest.obj.symbol = Suggest.obj.suggestBody.rows[1].cells[2].innerHTML;
			Suggest.obj1.value = Suggest.obj.suggestBody.rows[1].cells[0].innerHTML;
		}
		else {
			Suggest.obj.symbol = null;
			Suggest.setLine(null);
		}
	}
	else {
		Suggest.obj.symbol = null;
		Suggest.obj1.value = "";
	}
	
	everydatal = 0;
	everydata = new Array();
}

</script>

<div class="suggestOuter" />

<style type="text/css">

/* CSS Document */
.clearit{clear:both;}


.Suggest {
	margin-top: 45px;
	margin-left:290px;
	position: absolute;
}

.Suggest .SuggesTable {
	color: #999999;
	width: 300px;
	text-align: center;
	line-height: 18px;
	background-color: #E6F2F2;
	border: 1px dashed #CCCCCC;
	/*border-top: none;*/
}
.Suggest .SuggesTable .selected{
	background-color: #64A3A3;
	color: #ffffff;
}

</style>








