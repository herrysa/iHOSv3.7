(function($) {

	$.zgrid = $.zgrid || {};
	$.extend($.zgrid, {
		version: "1.0",
		htmlDecode: function(value) {
			if (value && (value == '&nbsp;' || value == '&#160;' || (value.length === 1 && value.charCodeAt(0) === 160))) {
				return "";
			}
			return !value ? value : String(value).replace(/&gt;/g, ">").replace(/&lt;/g, "<").replace(/&quot;/g, '"').replace(/&amp;/g, "&");
		},
		htmlEncode: function(value) {
			return !value ? value : String(value).replace(/&/g, "&amp;").replace(/\"/g, "&quot;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
		},
		format: function(format) { //jqgformat
			var args = $.makeArray(arguments).slice(1);
			if (format === undefined) {
				format = "";
			}
			return format.replace(/\{(\d+)\}/g, function(m, i) {
				return args[i];
			});
		},
		getCellIndex: function(cell) {
			var c = $(cell);
			if (c.is('tr')) {
				return -1;
			}
			c = (!c.is('td') && !c.is('th') ? c.closest("td,th") : c)[0];
			if ($.browser.msie) {
				return $.inArray(c, c.parentNode.cells);
			}
			return c.cellIndex;
		},
		stripHtml: function(v) {
			v = v + "";
			var regexp = /<("[^"]*"|'[^']*'|[^'">])*>/gi;
			if (v) {
				v = v.replace(regexp, "");
				return (v && v !== '&nbsp;' && v !== '&#160;') ? v.replace(/\"/g, "'") : "";
			} else {
				return v;
			}
		},
		stripPref: function(pref, id) {
			var obj = $.type(pref);
			if (obj == "string" || obj == "number") {
				pref = String(pref);
				id = pref !== "" ? String(id).replace(String(pref), "") : id;
			}
			return id;
		},
		stringToDoc: function(xmlString) {
			var xmlDoc;
			if (typeof xmlString !== 'string') {
				return xmlString;
			}
			try {
				var parser = new DOMParser();
				xmlDoc = parser.parseFromString(xmlString, "text/xml");
			} catch (e) {
				xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
				xmlDoc.async = false;
				xmlDoc.loadXML(xmlString);
			}
			return (xmlDoc && xmlDoc.documentElement && xmlDoc.documentElement.tagName != 'parsererror') ? xmlDoc : null;
		},
		parse: function(jsonString) {
			var js = jsonString;
			if (js.substr(0, 9) == "while(1);") {
				js = js.substr(9);
			}
			if (js.substr(0, 2) == "/*") {
				js = js.substr(2, js.length - 4);
			}
			if (!js) {
				js = "{}";
			}
			return ($.zgrid.useJSON === true && typeof(JSON) === 'object' && typeof(JSON.parse) === 'function') ?
				JSON.parse(js) :
				eval('(' + js + ')');
		},
		parseDate: function(format, date) {
			var tsp = {
					m: 1,
					d: 1,
					y: 1970,
					h: 0,
					i: 0,
					s: 0,
					u: 0
				},
				k, hl, dM, regdate = /[\\\/:_;.,\t\T\s-]/;
			if (date && date !== null && date !== undefined) {
				date = $.trim(date);
				date = date.split(regdate);
				if ($.zgrid.formatter.date.masks[format] !== undefined) {
					format = $.zgrid.formatter.date.masks[format];
				}
				format = format.split(regdate);
				var dfmt = $.zgrid.formatter.date.monthNames;
				var afmt = $.zgrid.formatter.date.AmPm;
				var h12to24 = function(ampm, h) {
					if (ampm === 0) {
						if (h === 12) {
							h = 0;
						}
					} else {
						if (h !== 12) {
							h += 12;
						}
					}
					return h;
				};
				for (k = 0, hl = format.length; k < hl; k++) {
					if (format[k] == 'M') {
						dM = $.inArray(date[k], dfmt);
						if (dM !== -1 && dM < 12) {
							date[k] = dM + 1;
							tsp.m = date[k];
						}
					}
					if (format[k] == 'F') {
						dM = $.inArray(date[k], dfmt);
						if (dM !== -1 && dM > 11) {
							date[k] = dM + 1 - 12;
							tsp.m = date[k];
						}
					}
					if (format[k] == 'a') {
						dM = $.inArray(date[k], afmt);
						if (dM !== -1 && dM < 2 && date[k] == afmt[dM]) {
							date[k] = dM;
							tsp.h = h12to24(date[k], tsp.h);
						}
					}
					if (format[k] == 'A') {
						dM = $.inArray(date[k], afmt);
						if (dM !== -1 && dM > 1 && date[k] == afmt[dM]) {
							date[k] = dM - 2;
							tsp.h = h12to24(date[k], tsp.h);
						}
					}
					if (date[k] !== undefined) {
						tsp[format[k].toLowerCase()] = parseInt(date[k], 10);
					}
				}
				tsp.m = parseInt(tsp.m, 10) - 1;
				var ty = tsp.y;
				if (ty >= 70 && ty <= 99) {
					tsp.y = 1900 + tsp.y;
				} else if (ty >= 0 && ty <= 69) {
					tsp.y = 2000 + tsp.y;
				}
				if (tsp.j !== undefined) {
					tsp.d = tsp.j;
				}
				if (tsp.n !== undefined) {
					tsp.m = parseInt(tsp.n, 10) - 1;
				}
			}
			return new Date(tsp.y, tsp.m, tsp.d, tsp.h, tsp.i, tsp.s, tsp.u);
		},
		jqID: function(sid) {
			return String(sid).replace(/[!"#$%&'()*+,.\/:;<=>?@\[\\\]\^`{|}~]/g, "\\$&");
		},
		guid: 1,
		uidPref: 'jqg',
		randId: function(prefix) {
			return (prefix ? prefix : $.zgrid.uidPref) + ($.zgrid.guid++);
		},
		getAccessor: function(obj, expr) {
			var ret, p, prm = [],
				i;
			if (typeof expr === 'function') {
				return expr(obj);
			}
			ret = obj[expr];
			if (ret === undefined) {
				try {
					if (typeof expr === 'string') {
						prm = expr.split('.');
					}
					i = prm.length;
					if (i) {
						ret = obj;
						while (ret && i--) {
							p = prm.shift();
							ret = ret[p];
						}
					}
				} catch (e) {}
			}
			return ret;
		},
		getXmlData: function(obj, expr, returnObj) {
			var ret, m = typeof(expr) === 'string' ? expr.match(/^(.*)\[(\w+)\]$/) : null;
			if (typeof(expr) === 'function') {
				return expr(obj);
			}
			if (m && m[2]) {
				// m[2] is the attribute selector
				// m[1] is an optional element selector
				// examples: "[id]", "rows[page]"
				return m[1] ? $(m[1], obj).attr(m[2]) : $(obj).attr(m[2]);
			} else {
				ret = $(expr, obj);
				if (returnObj) {
					return ret;
				}
				//$(expr, obj).filter(':last'); // we use ':last' to be more compatible with old version of jqGrid
				return ret.length > 0 ? $(ret).text() : undefined;
			}
		},
		ajaxOptions: {},
		from: function(source) {
			// Original Author Hugo Bonacci
			// License MIT http://jlinq.codeplex.com/license
			var QueryObject = function(d, q) {
				if (typeof(d) == "string") {
					d = $.data(d);
				}
				var self = this,
					_data = d,
					_usecase = true,
					_trim = false,
					_query = q,
					_stripNum = /[\$,%]/g,
					_lastCommand = null,
					_lastField = null,
					_orDepth = 0,
					_negate = false,
					_queuedOperator = "",
					_sorting = [],
					_useProperties = true;
				if (typeof(d) == "object" && d.push) {
					if (d.length > 0) {
						if (typeof(d[0]) != "object") {
							_useProperties = false;
						} else {
							_useProperties = true;
						}
					}
				} else {
					throw "data provides is not an array";
				}
				this._hasData = function() {
					return _data === null ? false : _data.length === 0 ? false : true;
				};
				this._getStr = function(s) {
					var phrase = [];
					if (_trim) {
						phrase.push("jQuery.trim(");
					}
					phrase.push("String(" + s + ")");
					if (_trim) {
						phrase.push(")");
					}
					if (!_usecase) {
						phrase.push(".toLowerCase()");
					}
					return phrase.join("");
				};
				this._strComp = function(val) {
					if (typeof(val) == "string") {
						return ".toString()";
					} else {
						return "";
					}
				};
				this._group = function(f, u) {
					return ({
						field: f.toString(),
						unique: u,
						items: []
					});
				};
				this._toStr = function(phrase) {
					if (_trim) {
						phrase = $.trim(phrase);
					}
					if (!_usecase) {
						phrase = phrase.toLowerCase();
					}
					phrase = phrase.toString().replace(/\\/g, '\\\\').replace(/\"/g, '\\"');
					return phrase;
				};
				this._funcLoop = function(func) {
					var results = [];
					$.each(_data, function(i, v) {
						results.push(func(v));
					});
					return results;
				};
				this._append = function(s) {
					var i;
					if (_query === null) {
						_query = "";
					} else {
						_query += _queuedOperator === "" ? " && " : _queuedOperator;
					}
					for (i = 0; i < _orDepth; i++) {
						_query += "(";
					}
					if (_negate) {
						_query += "!";
					}
					_query += "(" + s + ")";
					_negate = false;
					_queuedOperator = "";
					_orDepth = 0;
				};
				this._setCommand = function(f, c) {
					_lastCommand = f;
					_lastField = c;
				};
				this._resetNegate = function() {
					_negate = false;
				};
				this._repeatCommand = function(f, v) {
					if (_lastCommand === null) {
						return self;
					}
					if (f !== null && v !== null) {
						return _lastCommand(f, v);
					}
					if (_lastField === null) {
						return _lastCommand(f);
					}
					if (!_useProperties) {
						return _lastCommand(f);
					}
					return _lastCommand(_lastField, f);
				};
				this._equals = function(a, b) {
					return (self._compare(a, b, 1) === 0);
				};
				this._compare = function(a, b, d) {
					var toString = Object.prototype.toString;
					if (d === undefined) {
						d = 1;
					}
					if (a === undefined) {
						a = null;
					}
					if (b === undefined) {
						b = null;
					}
					if (a === null && b === null) {
						return 0;
					}
					if (a === null && b !== null) {
						return 1;
					}
					if (a !== null && b === null) {
						return -1;
					}
					if (toString.call(a) === '[object Date]' && toString.call(b) === '[object Date]') {
						if (a < b) {
							return -d;
						}
						if (a > b) {
							return d;
						}
						return 0;
					}
					if (!_usecase && typeof(a) !== "number" && typeof(b) !== "number") {
						a = String(a).toLowerCase();
						b = String(b).toLowerCase();
					}
					if (a < b) {
						return -d;
					}
					if (a > b) {
						return d;
					}
					return 0;
				};
				this._performSort = function() {
					if (_sorting.length === 0) {
						return;
					}
					_data = self._doSort(_data, 0);
				};
				this._doSort = function(d, q) {
					var by = _sorting[q].by,
						dir = _sorting[q].dir,
						type = _sorting[q].type,
						dfmt = _sorting[q].datefmt;
					if (q == _sorting.length - 1) {
						return self._getOrder(d, by, dir, type, dfmt);
					}
					q++;
					var values = self._getGroup(d, by, dir, type, dfmt);
					var results = [];
					for (var i = 0; i < values.length; i++) {
						var sorted = self._doSort(values[i].items, q);
						for (var j = 0; j < sorted.length; j++) {
							results.push(sorted[j]);
						}
					}
					return results;
				};
				this._getOrder = function(data, by, dir, type, dfmt) {
					var sortData = [],
						_sortData = [],
						newDir = dir == "a" ? 1 : -1,
						i, ab, j,
						findSortKey;

					if (type === undefined) {
						type = "text";
					}
					if (type == 'float' || type == 'number' || type == 'currency' || type == 'numeric') {
						findSortKey = function($cell) {
							var key = parseFloat(String($cell).replace(_stripNum, ''));
							return isNaN(key) ? 0.00 : key;
						};
					} else if (type == 'int' || type == 'integer') {
						findSortKey = function($cell) {
							return $cell ? parseFloat(String($cell).replace(_stripNum, '')) : 0;
						};
					} else if (type == 'date' || type == 'datetime') {
						findSortKey = function($cell) {
							return $.zgrid.parseDate(dfmt, $cell).getTime();
						};
					} else if ($.isFunction(type)) {
						findSortKey = type;
					} else {
						findSortKey = function($cell) {
							if (!$cell) {
								$cell = "";
							}
							return $.trim(String($cell).toUpperCase());
						};
					}
					$.each(data, function(i, v) {
						ab = by !== "" ? $.zgrid.getAccessor(v, by) : v;
						if (ab === undefined) {
							ab = "";
						}
						ab = findSortKey(ab, v);
						_sortData.push({
							'vSort': ab,
							'index': i
						});
					});

					_sortData.sort(function(a, b) {
						a = a.vSort;
						b = b.vSort;
						return self._compare(a, b, newDir);
					});
					j = 0;
					var nrec = data.length;
					// overhead, but we do not change the original data.
					while (j < nrec) {
						i = _sortData[j].index;
						sortData.push(data[i]);
						j++;
					}
					return sortData;
				};
				this._getGroup = function(data, by, dir, type, dfmt) {
					var results = [],
						group = null,
						last = null,
						val;
					$.each(self._getOrder(data, by, dir, type, dfmt), function(i, v) {
						val = $.zgrid.getAccessor(v, by);
						if (val === undefined) {
							val = "";
						}
						if (!self._equals(last, val)) {
							last = val;
							if (group !== null) {
								results.push(group);
							}
							group = self._group(by, val);
						}
						group.items.push(v);
					});
					if (group !== null) {
						results.push(group);
					}
					return results;
				};
				this.ignoreCase = function() {
					_usecase = false;
					return self;
				};
				this.useCase = function() {
					_usecase = true;
					return self;
				};
				this.trim = function() {
					_trim = true;
					return self;
				};
				this.noTrim = function() {
					_trim = false;
					return self;
				};
				this.execute = function() {
					var match = _query,
						results = [];
					if (match === null) {
						return self;
					}
					$.each(_data, function() {
						if (eval(match)) {
							results.push(this);
						}
					});
					_data = results;
					return self;
				};
				this.data = function() {
					return _data;
				};
				this.select = function(f) {
					self._performSort();
					if (!self._hasData()) {
						return [];
					}
					self.execute();
					if ($.isFunction(f)) {
						var results = [];
						$.each(_data, function(i, v) {
							results.push(f(v));
						});
						return results;
					}
					return _data;
				};
				this.hasMatch = function() {
					if (!self._hasData()) {
						return false;
					}
					self.execute();
					return _data.length > 0;
				};
				this.andNot = function(f, v, x) {
					_negate = !_negate;
					return self.and(f, v, x);
				};
				this.orNot = function(f, v, x) {
					_negate = !_negate;
					return self.or(f, v, x);
				};
				this.not = function(f, v, x) {
					return self.andNot(f, v, x);
				};
				this.and = function(f, v, x) {
					_queuedOperator = " && ";
					if (f === undefined) {
						return self;
					}
					return self._repeatCommand(f, v, x);
				};
				this.or = function(f, v, x) {
					_queuedOperator = " || ";
					if (f === undefined) {
						return self;
					}
					return self._repeatCommand(f, v, x);
				};
				this.orBegin = function() {
					_orDepth++;
					return self;
				};
				this.orEnd = function() {
					if (_query !== null) {
						_query += ")";
					}
					return self;
				};
				this.isNot = function(f) {
					_negate = !_negate;
					return self.is(f);
				};
				this.is = function(f) {
					self._append('this.' + f);
					self._resetNegate();
					return self;
				};
				this._compareValues = function(func, f, v, how, t) {
					var fld;
					if (_useProperties) {
						fld = 'jQuery.zgrid.getAccessor(this,\'' + f + '\')';
					} else {
						fld = 'this';
					}
					if (v === undefined) {
						v = null;
					}
					//var val=v===null?f:v,
					var val = v,
						swst = t.stype === undefined ? "text" : t.stype;
					if (v !== null) {
						switch (swst) {
							case 'int':
							case 'integer':
								val = (isNaN(Number(val)) || val === "") ? '0' : val; // To be fixed with more inteligent code
								fld = 'parseInt(' + fld + ',10)';
								val = 'parseInt(' + val + ',10)';
								break;
							case 'float':
							case 'number':
							case 'numeric':
								val = String(val).replace(_stripNum, '');
								val = (isNaN(Number(val)) || val === "") ? '0' : val; // To be fixed with more inteligent code
								fld = 'parseFloat(' + fld + ')';
								val = 'parseFloat(' + val + ')';
								break;
							case 'date':
							case 'datetime':
								val = String($.zgrid.parseDate(t.newfmt || 'Y-m-d', val).getTime());
								fld = 'jQuery.zgrid.parseDate("' + t.srcfmt + '",' + fld + ').getTime()';
								break;
							default:
								fld = self._getStr(fld);
								val = self._getStr('"' + self._toStr(val) + '"');
						}
					}
					self._append(fld + ' ' + how + ' ' + val);
					self._setCommand(func, f);
					self._resetNegate();
					return self;
				};
				this.equals = function(f, v, t) {
					return self._compareValues(self.equals, f, v, "==", t);
				};
				this.notEquals = function(f, v, t) {
					return self._compareValues(self.equals, f, v, "!==", t);
				};
				this.isNull = function(f, v, t) {
					return self._compareValues(self.equals, f, null, "===", t);
				};
				this.greater = function(f, v, t) {
					return self._compareValues(self.greater, f, v, ">", t);
				};
				this.less = function(f, v, t) {
					return self._compareValues(self.less, f, v, "<", t);
				};
				this.greaterOrEquals = function(f, v, t) {
					return self._compareValues(self.greaterOrEquals, f, v, ">=", t);
				};
				this.lessOrEquals = function(f, v, t) {
					return self._compareValues(self.lessOrEquals, f, v, "<=", t);
				};
				this.startsWith = function(f, v) {
					var val = (v === undefined || v === null) ? f : v,
						length = _trim ? $.trim(val.toString()).length : val.toString().length;
					if (_useProperties) {
						self._append(self._getStr('jQuery.zgrid.getAccessor(this,\'' + f + '\')') + '.substr(0,' + length + ') == ' + self._getStr('"' + self._toStr(v) + '"'));
					} else {
						length = _trim ? $.trim(v.toString()).length : v.toString().length;
						self._append(self._getStr('this') + '.substr(0,' + length + ') == ' + self._getStr('"' + self._toStr(f) + '"'));
					}
					self._setCommand(self.startsWith, f);
					self._resetNegate();
					return self;
				};
				this.endsWith = function(f, v) {
					var val = (v === undefined || v === null) ? f : v,
						length = _trim ? $.trim(val.toString()).length : val.toString().length;
					if (_useProperties) {
						self._append(self._getStr('jQuery.zgrid.getAccessor(this,\'' + f + '\')') + '.substr(' + self._getStr('jQuery.zgrid.getAccessor(this,\'' + f + '\')') + '.length-' + length + ',' + length + ') == "' + self._toStr(v) + '"');
					} else {
						self._append(self._getStr('this') + '.substr(' + self._getStr('this') + '.length-"' + self._toStr(f) + '".length,"' + self._toStr(f) + '".length) == "' + self._toStr(f) + '"');
					}
					self._setCommand(self.endsWith, f);
					self._resetNegate();
					return self;
				};
				this.contains = function(f, v) {
					if (_useProperties) {
						self._append(self._getStr('jQuery.zgrid.getAccessor(this,\'' + f + '\')') + '.indexOf("' + self._toStr(v) + '",0) > -1');
					} else {
						self._append(self._getStr('this') + '.indexOf("' + self._toStr(f) + '",0) > -1');
					}
					self._setCommand(self.contains, f);
					self._resetNegate();
					return self;
				};
				this.groupBy = function(by, dir, type, datefmt) {
					if (!self._hasData()) {
						return null;
					}
					return self._getGroup(_data, by, dir, type, datefmt);
				};
				this.orderBy = function(by, dir, stype, dfmt) {
					dir = dir === undefined || dir === null ? "a" : $.trim(dir.toString().toLowerCase());
					if (stype === null || stype === undefined) {
						stype = "text";
					}
					if (dfmt === null || dfmt === undefined) {
						dfmt = "Y-m-d";
					}
					if (dir == "desc" || dir == "descending") {
						dir = "d";
					}
					if (dir == "asc" || dir == "ascending") {
						dir = "a";
					}
					_sorting.push({
						by: by,
						dir: dir,
						type: stype,
						datefmt: dfmt
					});
					return self;
				};
				return self;
			};
			return new QueryObject(source, null);
		},
		extend: function(methods) {
			$.extend($.fn.jqGrid, methods);
			if (!this.no_legacy_api) {
				$.fn.extend(methods);
			}
		}
	});

	$.fn.zhGrid = function(pin) {
		if (typeof pin == 'string') {
			//var fn = $.fn.jqGrid[pin];
			var fn = $.zgrid.getAccessor($.fn.zhGrid, pin);
			if (!fn) {
				throw ("zhGrid - No such method: " + pin);
			}
			var args = $.makeArray(arguments).slice(1);
			return fn.apply(this, args);
		}
		return this.each(function() {
			if (this.grid) {
				return;
			}
			var p = $.extend(true, {
				url: "",
				height: 150,
				trHeight: 25,
				page: 1,
				rowNum: 20,
				rowTotal: null,
				records: 0,
				pager: "",
				pgbuttons: true,
				pginput: true,
				colModel: [],
				rowList: [],
				colNames: [],
				sortorder: "asc",
				sortname: "",
				datatype: "xml",
				mtype: "GET",
				altRows: false,
				selarrrow: [],
				savedRow: [],
				shrinkToFit: true,
				xmlReader: {},
				jsonReader: {},
				subGrid: false,
				subGridModel: [],
				reccount: 0,
				lastpage: 0,
				lastsort: 0,
				selrow: null,
				beforeSelectRow: null,
				onSelectRow: null,
				onSortCol: null,
				ondblClickRow: null,
				onRightClickRow: null,
				onPaging: null,
				onSelectAll: null,
				loadComplete: null,
				gridComplete: null,
				loadError: null,
				loadBeforeSend: null,
				afterInsertRow: null,
				beforeRequest: null,
				beforeProcessing: null,
				onHeaderClick: null,
				viewrecords: false,
				loadonce: false,
				multiselect: false,
				multikey: false,
				editurl: null,
				search: false,
				caption: "",
				hidegrid: true,
				hiddengrid: false,
				postData: {},
				userData: {},
				treeGrid: false,
				treeGridModel: 'nested',
				treeReader: {},
				treeANode: -1,
				ExpandColumn: null,
				tree_root_level: 0,
				prmNames: {
					page: "page",
					rows: "rows",
					sort: "sidx",
					order: "sord",
					search: "_search",
					nd: "nd",
					id: "id",
					oper: "oper",
					editoper: "edit",
					addoper: "add",
					deloper: "del",
					subgridid: "id",
					npage: null,
					totalrows: "totalrows"
				},
				forceFit: false,
				gridstate: "visible",
				cellEdit: false,
				cellsubmit: "remote",
				nv: 0,
				loadui: "enable",
				toolbar: [false, ""],
				scroll: false,
				multiboxonly: false,
				deselectAfterSort: true,
				scrollrows: false,
				autowidth: false,
				scrollOffset: 18,
				cellLayout: 5,
				subGridWidth: 20,
				multiselectWidth: 20,
				gridview: false,
				rownumWidth: 25,
				rownumbers: false,
				pagerpos: 'center',
				recordpos: 'right',
				footerrow: false,
				userDataOnFooter: false,
				hoverrows: true,
				altclass: 'ui-priority-secondary',
				viewsortcols: [false, 'vertical', true],
				resizeclass: '',
				autoencode: false,
				remapColumns: [],
				ajaxGridOptions: {},
				direction: "ltr",
				toppager: false,
				headertitles: false,
				scrollTimeout: 40,
				data: [],
				_index: {},
				grouping: false,
				groupingView: {
					groupField: [],
					groupOrder: [],
					groupText: [],
					groupColumnShow: [],
					groupSummary: [],
					showSummaryOnHide: false,
					sortitems: [],
					sortnames: [],
					groupDataSorted: false,
					summary: [],
					summaryval: [],
					plusicon: 'ui-icon-circlesmall-plus',
					minusicon: 'ui-icon-circlesmall-minus'
				},
				ignoreCase: false,
				cmTemplate: {},
				idPrefix: "",
				listen: {
					left: true,
					up: true,
					right: true,
					down: true,
					enter: true,
					esc: true
				}
			}, $.zgrid.defaults, pin || {});
			var ts = this,
				grid = {
					headers: [],
					cols: [],
					footers: []
				};
			var sortkeys = ["shiftKey", "altKey", "ctrlKey"],
				gridEvent = {
					left: 37,
					up: 38,
					right: 39,
					down: 40,
					enter: 13,
					esc: 27
				},

				addUrlData = function() {
					var datas = ts.p.jsonReader.root;
					var initTableColModels = ts.p.colModel;
					jQuery.ajax({
						dataType: 'json',
						url: ts.p.url,
						//data : formData,
						type: 'POST',
						async: 'false',
						error: function(data) {
							alert(data);
						},
						success: function(data) {
							var gridDatas = data[datas];
							ts.grid.dataSource = gridDatas;
							jQuery(ts).fillData();
							/*for (var gi = 0; gi < gridDatas.length; gi++) {
								jQuery("#" + ts.id).find("tr").eq(gi + 1).attr("used", 'true');
								for (var mi in initTableColModels) {
									var colName = initTableColModels[mi].name;
									var colValue = eval("gridDatas[gi]." + initTableColModels[mi].name);
									if (colValue != 0 && (!colValue || colName == 'null')) {
										colValue = "";
									}
									var thisTd = jQuery("#" + ts.id).find("tr").eq(gi + 1).find("td[name='" + colName + "']");
									var formatter = initTableColModels[mi].formatter;
									var thisEditInput = thisTd.find("span").eq(1).find("input:first");
									thisEditInput.val(colValue);
									//thisTd.attr('editing','true');
									jQuery(ts).cellChange(thisEditInput, 'noaction');*/
									/*if(formatter){
									if(formatter=='checkbox'){
										//formatter = "<input type='checkbox' disabled=true style='margin:2px 2px 2px 2px' value='"+colValue+"'/ >";
										if(colValue==true){
											thisTd.find("span").eq(0).find("input[type=checkbox]").attr("checked","checked");
											thisTd.find("span").eq(0).find("input[type=checkbox]").attr("value",colValue);
											thisTd.find("span").eq(1).find("input[type=checkbox]").attr("checked","checked");
											thisTd.find("span").eq(1).find("input[type=checkbox]").val("value",colValue);
										}else{
											thisTd.find("span").eq(0).find("input[type=checkbox]").removeAttr("checked");
											thisTd.find("span").eq(0).find("input[type=checkbox]").attr("value",colValue);
											thisTd.find("span").eq(1).find("input[type=checkbox]").removeAttr("checked","checked");
											thisTd.find("span").eq(1).find("input[type=checkbox]").val("value",colValue);
										}
										
									}else{
										thisTd.find("span").eq(0).text(colValue);
										thisTd.find("span").eq(1).find("input").val(colValue);
									}
								}else{
									thisTd.find("span").eq(0).text(colValue);
									thisTd.find("span").eq(1).find("input").val(colValue);
								}*/

								//}
							//}
							//jQuery("#invMainInitTable").find("tr").not(".firstTr").
						}
					});
				},
				/*addDataInit = function() {
					var initTableColModels = ts.p.colModel;
					for (var mi in initTableColModels) {
						if (initTableColModels[mi].editable == true && initTableColModels[mi].editoptions && initTableColModels[mi].editoptions.dataInit) {
							//alert("#"+tableName+">input[name='"+initTableColModels[mi].name+"']");
							jQuery("#" + ts.id).find("input[name='" + initTableColModels[mi].name + "']").each(function() {
								var dataInit = initTableColModels[mi].editoptions.dataInit;
								dataInit(jQuery(this));
							});
						}
					}
				},*/
				rightClick = function() {
					jQuery("#" + ts.id + '>tbody>tr').contextMenu('zhGridRightMenu', {
						bindings: {
							'addRow': function(t) {
								$('#' + ts.id).addGridRow(t.id);
							},
							'delRow': function(t) {
								$('#' + ts.id).delRow(t.id);
							},
							'emptyRow': function(t) {
								$('#' + ts.id).emptyRow(t.id);
							},

							'delete': function(t) {
								alert('Trigger was ' + t.id + '\nAction was Delete');
							}
						},
						onContextMenu: function(e) {
							return true;

						},
						onShowMenu: function(e, trigger, menu) {
							jQuery("#" + trigger.id).trigger('click');
							return menu;
						}
					});
				},
				bindKeys = function() {
					//var gridEvent = { left:37 , up:38 , right:39 , down:40 , enter:13 , esc:27 };
					var colModels = ts.p.colModel;
					var editAbleModels = new Array();
					//查找可编辑的列
					for (var mi in colModels) {
						if (colModels[mi].editable == true && colModels[mi].hidden == false) {
							editAbleModels.push({
								name: colModels[mi].name,
								index: mi
							});
						}
					}
					ts.p.editAbleModels = editAbleModels;
					/*jQuery("#" + ts.id).on('keydown', "input" , function(event) {
						alert();
						event.stopPropagation();
					});*/
					jQuery("#" + ts.id).on('keydown', function(event) {
						var listen = jQuery(this)[0].p.listen;
						var keyCode = event.keyCode;
						switch (keyCode) {
							case 37:
								listen.left ? moveToLeft(this) : null;
								break;
							case 38:
								listen.up ? moveToUp(this) : null;
								break;
							case 39:
								listen.right ? moveToRight(this) : null;
								break;
							case 40:
								//listen.down ? moveToDown(this) : null;
								break;
							case 13:
								listen.enter ? dealEnter(this) : null;
								break;
							case 27:
								listen.esc ? dealEsc(this) : null;
								break;
						}
						
						/*if(listen){
					jQuery(this).trigger('keyMove',[event.keyCode]);
				}*/
					});
				},
				moveToLeft = function(grid) {
					var activeCell = jQuery(grid)[0].p.activeCell;
					var editAbleModels = jQuery(grid)[0].p.editAbleModels;
					if (activeCell&&editAbleModels) {
						var emLength = editAbleModels.length,
							tdName = activeCell.name,
							tdRow = activeCell.row,
							tdCol = activeCell.col,
							emIndex = parseInt(activeCell.emIndex),
						    nextTd, nextRow, nextCol;
						if (emIndex || emIndex == 0) {
							if (emIndex > 0) {
								var nextTdIndex = parseInt(editAbleModels[emIndex - 1].index) + 1;
								nextRow = tdRow;
								nextCol = nextTdIndex;
								emIndex -= 1;
							} else {
								var nextTdIndex = parseInt(editAbleModels[editAbleModels.length - 1].index) + 1;
								nextRow = tdRow - 1;
								nextCol = nextTdIndex;
								emIndex = editAbleModels.length - 1;
							}
							var currentTd = jQuery(grid).getCell(tdRow, tdCol);
							jQuery(grid).afterEditCell(jQuery(currentTd).find("input:first"));
							nextTd = jQuery(grid).getCell(nextRow, nextCol);
							jQuery(grid).cellEdit(nextTd);
							/*nextTd = jQuery(nextTd);
							if (nextTd) {
								jQuery(jQuery(grid).getCell(tdRow, tdCol)).find("input:first").trigger('blur');
								setTimeout(function() {
									nextTd.trigger('click');
									var toFocusInput = nextTd.find("input");
									var sIdContent = toFocusInput.val();
									jQuery(toFocusInput).setSelectionForInput(0, sIdContent.length);
									var mainTableDivScrollLeft = jQuery("#" + ts.id + "Div").scrollLeft();
									jQuery("#" + ts.id + "Div").scrollLeft(0);
									jQuery("#" + ts.id + "_div").scrollLeft(mainTableDivScrollLeft);

								}, 100);
							}*/
						}
					}
				},
				moveToRight = function(grid) {
					var activeCell = jQuery(grid)[0].p.activeCell;
					if (activeCell) {
						var editAbleModels = jQuery(grid)[0].p.editAbleModels;
						var emLength = editAbleModels.length;
						var tdName = activeCell.name,
							tdRow = activeCell.row,
							tdCol = activeCell.col,
							emIndex = activeCell.emIndex;
						var nextTd, nextRow, nextCol;
						emIndex = parseInt(emIndex);
						if (emIndex || emIndex == 0) {
							if (emIndex < emLength-1) {
								var nextTdIndex = parseInt(editAbleModels[emIndex + 1].index) + 1;
								nextRow = tdRow;
								nextCol = nextTdIndex;
							} else {
								var nextTdIndex = parseInt(editAbleModels[0].index) + 1;
								nextRow = tdRow + 1;
								nextCol = nextTdIndex;
							}
							var currentTd = jQuery(grid).getCell(tdRow, tdCol);
							jQuery(grid).afterEditCell(jQuery(currentTd).find("input:first"));
							nextTd = jQuery(grid).getCell(nextRow, nextCol);
							jQuery(grid).cellEdit(nextTd);
						}
					}
				},
				moveToUp = function(grid) {
					var activeCell = jQuery(grid)[0].p.activeCell;
					if (activeCell) {
						var editAbleModels = jQuery(grid)[0].p.editAbleModels;
						var emLength = editAbleModels.length;
						var tdName = activeCell.name,
							tdRow = activeCell.row,
							tdCol = activeCell.col,
							emIndex = activeCell.emIndex,
							maxRow = jQuery(grid)[0].p.maxRow;
						var nextTd, nextRow, nextCol;
						emIndex = parseInt(emIndex);
						if (emIndex || emIndex == 0) {
							if (tdRow > 0) {
								nextRow = tdRow - 1;
								nextCol = tdCol;
							} else {
								nextRow = maxRow - 2;
								nextCol = tdCol;
							}
							var currentTd = jQuery(grid).getCell(tdRow, tdCol);
							jQuery(grid).afterEditCell(jQuery(currentTd).find("input:first"));
							nextTd = jQuery(grid).getCell(nextRow, nextCol);
							jQuery(grid).cellEdit(nextTd);
						}
					}
				},
				moveToDown = function(grid) {
					var activeCell = jQuery(grid)[0].p.activeCell;
					if (activeCell) {
						var editAbleModels = jQuery(grid)[0].p.editAbleModels;
						var emLength = editAbleModels.length;
						var tdName = activeCell.name,
							tdRow = activeCell.row,
							tdCol = activeCell.col,
							emIndex = activeCell.emIndex,
							maxRow = jQuery(grid)[0].p.maxRow;
						var nextTd, nextRow, nextCol;
						emIndex = parseInt(emIndex);
						if (emIndex || emIndex == 0) {
							if (tdRow < maxRow - 2) {
								nextRow = tdRow + 1;
								nextCol = tdCol;
							} else {
								nextRow = 0;
								nextCol = tdCol;
							}
							var currentTd = jQuery(grid).getCell(tdRow, tdCol);
							jQuery(grid).afterEditCell(jQuery(currentTd).find("input:first"));
							nextTd = jQuery(grid).getCell(nextRow, nextCol);
							jQuery(grid).cellEdit(nextTd);
						}
					}
				},
				dealEnter = function(grid) {

				},
				dealEsc = function(grid) {
					
				};
			this.p = p;
			this.grid = grid;
			//this.p.listen = true;
			var initTableColModels = this.p.colModel;
			var gridId = this.id;
			jQuery("#" + this.id).append("<colgroup></colgroup>");
			jQuery("#" + this.id).append("<tbody><tr id='tr_0' class='firstTr'></tr></tbody>");
			jQuery("#" + this.id).addClass("zhGrid");
			jQuery("#" + this.id).addClass("list");
			jQuery("#" + this.id).wrap("<div id='" + this.id + "Div' style='overflow-x:hidden;'><form id='" + this.id + "Form'></form></div>");
			jQuery("#" + this.id + "Div").before("<table id='" + this.id + "_head' class='list zhGrid_head'><colgroup></colgroup><thead><tr></tr></thead></table>");
			jQuery("#" + this.id + "Div").after("<table id='" + this.id + "_foot' class='list zhGrid_foot'><colgroup></colgroup><tfoot><tr></tr></tfoot></table>");
			var tableWidth = 0,
				minWidth = 0,
				showColNum = 0;
			for (var mi in initTableColModels) {
				var initTableColModel = initTableColModels[mi];
				if(!initTableColModel.width){
					initTableColModel.width = 50;
				}
				if(!initTableColModel.align){
					initTableColModel.align = "center";
				}
				if(!initTableColModel.hidden){
					initTableColModel.hidden = false;
					showColNum += 1;
					tableWidth += parseInt(initTableColModel.width);
				}

			}
			ts.p.width = tableWidth + 25 +1;
			ts.p.minWidth = tableWidth + 25 +1;
			ts.p.showColNum = showColNum;
			
			//jQuery("#" + this.id + "Div").append("<div class='zhGridInputHolder'><textarea class='zhGridInput'></textarea></div>");

			/*var firstShow = true,
				headStr = "",
				bodyFirstTrStr = "",
				bodyStr = "",
				footStr = "",
				tableWidth = 0,
				showColNum = 0;
			headStr = "<th style='width:24px;'><div style='width:20px'></div></th>";
			bodyFirstTrStr = "<th style='width:24px;'><div style='width:20px'></div></th>";
			bodyStr = "<td align='center' name='sn' style='width:24px;'><div style='width:20px'>1</div></td>";
			footStr = "<th style='width:24px;'><div style='width:20px'></div></th>";

			if (this.p.initColumn) {
				var entityName = this.p.initColumn;
				$.ajax({
					url: 'getColShowByEntity',
					type: 'post',
					data: {
						entityName: entityName
					},
					dataType: 'json',
					async: false,
					error: function(data) {},
					success: function(data) {
						var colShows = data.colShows;
						var gridColModel = initTableColModels;
						var newColModel = new Array();
						var ctrlColModel = new Array();
						var colArray = new Map();
						var colInCtrl = new Map();
						for (var gci = 0; gci < gridColModel.length; gci++) {
							colArray.put(gridColModel[gci].name, gridColModel[gci]);
						}
						for (var ci in colShows) {
							var gcmodel = colArray.get(colShows[ci].col);
							if (!gcmodel) {
								continue;
							}
							if (colShows[ci].show) {
								gcmodel.hidden = false;
							} else {
								gcmodel.hidden = true;
							}
							if (colShows[ci].colWidth) {
								gcmodel.width = parseInt(colShows[ci].colWidth);
							}
							ctrlColModel.push(gcmodel);
							colInCtrl.put(colShows[ci].col, true);
						}
						var colCBI = 0;
						for (var gci = 0; gci < gridColModel.length; gci++) {
							var inCtrl = colInCtrl.get(gridColModel[gci].name);
							if (!inCtrl) {
								newColModel.push(gridColModel[gci]);
							} else {
								newColModel.push(ctrlColModel[colCBI]);
								colCBI++;
							}
						}
						initTableColModels = newColModel;
						jQuery("#" + gridId)[0].p.colModel = newColModel;
					}
				});
			}*/

			/*for (var mi in initTableColModels) {
				var hide = "",
					align = "",
					formatter = "",
					formatoptions = "",
					color = "",
					editable = "",
					width = "",
					cellStr = "";
				if (initTableColModels[mi].hidden) {
					hide = 'display:none;';
				}
				var width = initTableColModels[mi].width;
				if (!initTableColModels[mi].width) {
					width = 50;
					if (!initTableColModels[mi].hidden) {
						tableWidth += 50;
						showColNum += 1;
					}
				} else {
					if (!initTableColModels[mi].hidden) {
						tableWidth += parseInt(initTableColModels[mi].width);
						showColNum += 1;
					}
				}
				if (!ts.p.colWidth) {
					ts.p.colWidth = {};
				}
				ts.p.colWidth[initTableColModels[mi].name] = width;
				//console.log(initTableColModels[mi].name+","+tableWidth);
				align = initTableColModels[mi].align;
				if (!align) {
					align = 'left';
				}
				headStr += "<th name='" + initTableColModels[mi].name + "' style='" + hide + "width:" + (width - 1) + "px;'><span class='ui-zhgrid-resize' style='cursor: col-resize;'>&nbsp;</span><div style='width:" + (width - 5 - 3) + "px'>" + initTableColModels[mi].label + "</div></th>";
				formatoptions = initTableColModels[mi].formatoptions;
				if (formatoptions) {
					color = formatoptions.color;
					if (color) {
						color = "color:" + color + ";";
					}
				}*/
				/*formatter = initTableColModels[mi].formatter;
			
			if(formatter){
				if(formatter=='checkbox'){
					formatter = "<input type='checkbox' disabled=true style='margin:2px 2px 2px 2px'/ >";
				}else{
					formatter = "";
				}
			}else{
				formatter = "";
			}
			if(initTableColModels[mi].editable==true){
				editable = "true";
			}else{
				editable = "false";
			}*/
				//if(initTableColModels[mi].editable==true){
			/*	var edittype = initTableColModels[mi].edittype;
				if (!edittype) {
					edittype = "text";
				}
				if (edittype == 'text') {
					cellStr = "<span name='toShow' class=''></span><span name='toEdit' style='display:none'><input id='" + initTableColModels[mi].name + "_1' name='" + initTableColModels[mi].name + "' type='text' class=''/></span>";
				}
				if (edittype == 'checkbox') {
					cellStr = "<span name='toShow' class=''></span><span name='toEdit' style='display:none'><input id='" + initTableColModels[mi].name + "_1' name='" + initTableColModels[mi].name + "' type='checkbox' class='' value='false'/></span>";
				}
				//}
				bodyFirstTrStr += "<th name='" + initTableColModels[mi].name + "' style='" + hide + "width:" + (width - 1) + "px;'><div style='width:" + (width - 5) + "px'></div></th>";
				bodyStr += "<td align='" + align + "' editable='" + initTableColModels[mi].editable + "' name='" + initTableColModels[mi].name + "' style='" + hide + "width:" + (width - 1) + "px;'><div style='width:" + (width - 5) + "px;" + color + "'>" + cellStr + "</div></td>";
				if (firstShow == true && hide == false) {
					footStr += "<th name='" + initTableColModels[mi].name + "' style='" + hide + "width:" + (width - 1) + "px;'><div style='width:" + (width - 5) + "px'></div></th>";
					firstShow = false;
				} else {
					footStr += "<th align='" + align + "' name='" + initTableColModels[mi].name + "' style='" + hide + "width:" + (width - 1) + "px;'><div style='width:" + (width - 5) + "px'></div></th>";
				}
			}


			headStr += "<th  name='scrollbar' style='display:none'><div></div></th>";
			footStr += "<th  name='scrollbar' style='display:none'><div></div></th>";

			ts.p.width = tableWidth + 25 + 1;
			ts.p.minWidth = tableWidth + 25 + 1;
			ts.p.showColNum = showColNum;*/
			//jQuery("#"+this.id+"_div").css('width',tableWidth+4+'px');

			//var widthPercent = (gridWidth-4)/gridWidth*100;
			//console.log("gridWidth:"+gridWidth);
			//console.log("tableWidth:"+tableWidth);

			/*if(gridWidth>tableWidth){
			
			//jQuery("#"+this.id+"_head").css('width',gridWidth+'px');
			//jQuery("#"+this.id).css('width',gridWidth+'px');
			//jQuery("#"+this.id+"_foot").css('width',gridWidth+'px');
			this.p.width = gridWidth;
		}else{
			//jQuery("#"+this.id+"_head").css('width',tableWidth+'px');
			//jQuery("#"+this.id).css('width',tableWidth+'px');
			//jQuery("#"+this.id+"_foot").css('width',tableWidth+'px');
			this.p.width = tableWidth;
		}*/
			//jQuery("#"+this.id+"_div").css('heigth',tableWidth+4+'px');

			/*jQuery("#" + this.id + "_head>thead>tr:first").append(headStr);
			jQuery("#" + this.id + ">tbody>tr:first").append(bodyFirstTrStr);
			jQuery("#" + this.id + ">tbody>tr:first").after("<tr id='tr_1'>" + bodyStr + "</tr>");
			jQuery("#" + this.id + "_foot>tfoot>tr:first").append(footStr);

			jQuery("#" + this.id + "_head>thead>tr>th:visible:last").addClass("lastTh");
			jQuery("#" + this.id + ">tbody>tr>th:visible:last").addClass("lastTh");
			jQuery("#" + this.id + ">tbody>tr>td:visible:last").addClass("lastTh");
			jQuery("#" + this.id + "_foot>tfoot>tr>th:visible:last").addClass("lastTh");*/

			var $grid = jQuery("#" + this.id + "_div");
			var thead = jQuery("#" + this.id + "_head>thead");
			$grid.append("<div class='resizeMarker' style='height:300px; left:57px;display:none;'></div><div class='resizeProxy' style='height:300px; left:377px;display:none;'></div>");
			$(">tr", thead).each(function() {
				$(">th", this).each(function(i) {
					var th = this,
						$th = $(this),
						thisName = $th.attr("name");
					$th.mousedown(function(event) {
						if ($(event.target).closest("th>span.ui-zhgrid-resize").length != 1) {
							return;
						}
						var l = 0,
							r = 0,
							w = 0,
							idx = 0;
						$th.prevAll(":visible").andSelf().each(function() {
							l += $(this).outerWidth();
							r += $(this).outerWidth();
							//console.log($(this).parent().html()+":::"+$(this).outerWidth());
						});
						l -= $th.outerWidth();
						l += 1;
						//console.log(l);
						r += 1;
						//r -= $grid.scrollLeft();
						//l -= $grid.scrollLeft();
						$(".resizeProxy", $grid).show().css({
							left: r,
							top: $.jTableTool.getTop(th) + 3,
							height: $grid.height() - 4,
							cursor: "col-resize"
						});
						$(".resizeMarker", $grid).show().css({
							left: l,
							top: $.jTableTool.getTop(th) + 3,
							height: $grid.height() - 4
						});
						$(".resizeProxy", $grid).jDrag($.extend(pin, {
							scop: true,
							cellMinW: 20,
							relObj: $(".resizeMarker", $grid)[0],
							move: "horizontal",
							event: event,
							stop: function() {
								var pleft = $(".resizeProxy", $grid).position().left;
								var mleft = $(".resizeMarker", $grid).position().left;
								var move = pleft - mleft - $th.outerWidth() - 9;

								/*var cols = $.jTableTool.getColspan($th);
								var cellNum = $.jTableTool.getCellNum($th);
								var oldW = $th.width(), newW = $th.width() + move;
								var $dcell = $(">td", ftr).eq(cellNum - 1);
								
								$th.width(newW + "px");
								$dcell.width(newW+"px");
								
								var $table1 = $(thead).parent();
								$table1.width(($table1.width() - oldW + newW)+"px");
								var $table2 = $(tbody).parent();
								$table2.width(($table2.width() - oldW + newW)+"px");*/
								jQuery(ts).resizeGridWidth(null, thisName, move);
								$(".resizeMarker,.resizeProxy", $grid).hide();
							}
						}));
						return false;
					});
					/*$th.mouseover(function(event){
					var offset = $.jTableTool.getOffset(th, event).offsetX;
					if($th.outerWidth() - offset < 5) {
						var l=0,r=0,w=0,idx=0;
						$(th).prevAll().andSelf().find(":visible").each(function(){
							l += $(this).outerWidth();
							r += $(this).outerWidth();
						});
						l -= $th.outerWidth();
						l -= 1;
						r -= 1;
						
					} else {
						$th.css("cursor", $th.attr("orderField") ? "pointer" : "default");
						$th.unbind("mousedown");
					}
					return false;
				});*/
				});
			});

			/*jQuery("#"+this.id).find("td").each(function(){
			var thisInput = jQuery(this).find("input:first");
			if(thisInput){
				jQuery("#"+ts.id).cellChange(thisInput);
			}
		});*/
			jQuery("#" + this.id).delegate("tr", "click", function() {
				jQuery("#" + ts.id).find(".selected").removeClass("selected");
				jQuery(this).addClass("selected");
			});
			jQuery("#" + this.id).delegate("tr", "hover", function() {
				jQuery(this).addClass("hover");
			}, function() {
				jQuery(this).removeClass("hover");
			});

			jQuery(window).unbind("resizeZhGrid").bind("resizeZhGrid", resizeZhGrid);

			/*jQuery(window).resize(function(e) {
				if (e.target == window) {
					//resizeZhGrid();
					jQuery(window).trigger("resizeZhGrid");
				}
			});*/

			var tableDivWidth = jQuery("#" + this.id + "Div").width();
			jQuery("#" + gridId + "_div").scroll(function() {
				//jQuery("#"+gridId+"Div").width(tableDivWidth+jQuery("#"+gridId+"_div").scrollLeft());
				//jQuery("#"+gridId+"Div").css("width","auto");
				jQuery("#" + gridId ).resizeWindow();
			});
			//var _divW = jQuery("#"+this.id+"").width();
			//jQuery("#"+this.id+"Div").width(_divW-4);
			/*if(!ts.p.shrinkToFit){
			var tableDivWidth = jQuery("#"+this.id+"Div").width();
			jQuery("#"+gridId+"_div").scroll(function(){
				jQuery("#"+gridId+"Div").width(tableDivWidth+jQuery("#"+gridId+"_div").scrollLeft());
			});
		}*/
			/*var tableDivWidth = jQuery("#"+this.id+"Div").width();
		jQuery("#"+gridId+"_div").scroll(function(){
			jQuery("#"+gridId+"Div").width(tableDivWidth+jQuery("#"+gridId+"_div").scrollLeft());
		});*/


			
			setTimeout(function() {
				jQuery("#" + gridId).renderHead();
				jQuery("#" + gridId).renderBody();
				jQuery("#" + gridId).renderFoot();
				//jQuery(ts).resizeGrid();
				addUrlData();
				//addDataInit();
				bindKeys();
				//rightClick();
				var gridComplete = ts.p.gridComplete;
				if (typeof(ts.p.gridComplete) == 'function') {
					gridComplete(ts);
				}
			}, 100);
		});
	};
	$.zgrid.extend({
		getParentDom: function($o, type) {
			if (!$o) {
				return;
			}
			if ($o.is(type)) {
				return $o;
			}
			var dom = $o,
				i = 0;
			while ((!dom.is(type)) && i < 10) {
				dom = dom.parent();
				i++;
			}
			if (dom.is(type)) {
				return dom;
			} else {
				return null;
			}
		},
		getTr: function($o) {
			if (!$o) {
				return;
			}
			if ($o.is("tr")) {
				return $o;
			}
			var tr = $o,
				i = 0;
			while ((!tr.is("tr")) && i < 10) {
				tr = tr.parent();
				i++;
			}
			if (tr.is("tr")) {
				return tr;
			} else {
				return null;
			}
		},
		getThisTr: function() {
			var $t = this[0];
			var activeCell = $t.p.activeCell;
			var row = activeCell.row;
			if (row) {
				row = parseInt(row) + 1;
				return jQuery(this).find('tr').eq(row);
			}
		},
		getCell: function(row, col) {
			//if (row && col) {
				var tr = jQuery(this).find('tr').eq(row);
				var td = tr.find('td').eq(col);
				return td[0];
			//}
		},
		getColIndex: function(col) {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			var thisColModel = $t.p.colModel;
			for (var i = 0; i < thisColModel.length; i++) {
				if (thisColModel[i].name == col) {
					return i;
				}
			}
		},
		getColModel: function(col) {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			var thisColModel = $t.p.colModel;
			for (var i = 0; i < thisColModel.length; i++) {
				if (thisColModel[i].name == col) {
					return thisColModel[i];
				}
			}
		},
		getCellData: function(rowid, colName) {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			var dataSource = $t.grid.dataSource;
			var sn = rowid.split("_")[1];
			sn = parseInt(sn);
			var cellVal ;
			if(dataSource[sn]){
				cellVal = eval("dataSource[sn]."+colName);
			}else{
				cellVal = "";
			}
			
			//var cellVal = jQuery("#" + rowid, "#" + $t.id).find("input[name='" + colName + "']").val();
			return cellVal;
		},
		setCellData: function(rowid, colName, cellVal, type) {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			var editInput = jQuery("#" + rowid, "#" + $t.id).find("input[name='" + colName + "']");
			var thisTd = this.getParentDom(editInput, 'td');
			editInput.val(cellVal);
			//thisTd.attr('editing','true');
			jQuery($t).cellChange(editInput, type);
		},
		addColData: function(row,col,data){
			var $t = this[0];
			var cell = jQuery(this).getCell(row,col+1);
			var cellEditor = jQuery(cell).find("input:first");
			cellEditor.val(data);
			this.cellChange(cellEditor, null);
			/*var v = "";
			if(data===0||data){
				v = jQuery(this).formatter("tr_"+row, data, col);
			}
			jQuery(cell).find("span").eq(0).text(v);
			jQuery(cell).find("span").eq(1).find("input:first").val(v);*/
		},
		addGridRowData: function(row,rowData){
			var $t = this[0];
			var colModel = $t.p.colModel;
			for(var modelIndex in colModel){
				var colName = colModel[modelIndex].name;
				var nameArr = colName.split(".");
				var objStr = "rowData";
				for(var nameIndex=0; nameIndex<nameArr.length-1; nameIndex++){
					objStr += "['"+nameArr[nameIndex]+"']";
					if(!(nameArr[nameIndex] in rowData)){
						eval(objStr+" = {}");
					}
				/*var obj = eval("("+objStr+")");
				if(!obj){
					obj = {};
				}*/
				}
				var v = eval("rowData."+colName);
				modelIndex = parseInt(modelIndex) ;
				jQuery(this).addColData(row,modelIndex,v);
				//jQuery("#"+rowId,instance).find("td[name='"+rowData[colName]+"']")
			}
		},
		fillData: function(){
			var $t = this[0];
			var dataSource = $t.grid.dataSource;
			for(var rowDataIndex in dataSource){
				var rowData = dataSource[rowDataIndex];
				jQuery(this).addGridRowData(rowDataIndex,rowData);
			}
			this.footData();
		},
		rowChange: function(rowid,rowData){
			var $t = this[0];
			if(rowid){
				var sn = rowid.split("_")[1];
				sn = parseInt(sn);
				var dataSource = $t.grid.dataSource;
				if(!dataSource[sn]){
					dataSource[sn] = {};
				}
				//var row = dataSource[sn];
				jQuery.extend(true,dataSource[sn],rowData);
				//dataSource[sn] = rowData;
				this.render();
			}
			
		},
		cellChange: function(editInput, type) {
			var $t = this[0];
			if (!editInput) {
				return;
			}
			var editSpan = editInput.parent();
			var vl = editInput.val();
			var thistr = null,
				thistd = null;
			thistr = jQuery($t).getParentDom(editInput, 'tr');
			thistd = jQuery($t).getParentDom(editInput, 'td');
			if (!thistr || !thistd) {
				return;
			}
			//var editing = thistd.attr('editing');
			//if(editing!=='true'){ return ;}
			var v = null;
			if ( !vl ) {
				v = "";
				var oldValue = this.getCellData(thistr.attr("id"),thistd.attr("name"));
				if(v==oldValue){
					editSpan.prev().show();
					editSpan.hide();
					return ;
				}
			} else {
				v = jQuery($t).formatter(thistr.attr('id'), vl, thistd.index() - 1);
			}

			$t.p.gridEdited = true ;
			
			editSpan.prev().html(v);
			var editCell ;
			if(!editCell){
				editCell = {name:thistd.attr("name"),row:thistr.index(),col:thistd.index()};
			}
			jQuery(this).changeData(editCell,v);
			if (type != 'noaction') {
				var rowid = thistr.attr("id"),
					cellname = editInput.attr("name"),
					value = v,
					iRow = "",
					iCol = "";
				var afterEditCell = $t.p.afterEditCell;
				if (afterEditCell) {
					afterEditCell(rowid, cellname,
						value, iRow, iCol);
				}
			}
			editSpan.prev().show();
				editSpan.hide();
			/*if (editing != 'true') {
				editSpan.prev().show();
				editSpan.hide();
				//thistd.removeAttr("editing");
			}*/
			
			this.footData();

		},
		cellEditPrepare: function(instance,TD){
			var $t = instance;
			var beforeEditCell = $t.p.beforeEditCell;
			var thisTr = jQuery(TD).parent(),
				thisTd = jQuery(TD),
				trIndex = thisTr.index(),
				tdIndex = thisTd.index(),
				rowid = thisTr.attr("id"),
				cellname = jQuery(TD).attr("name"),
				value = jQuery(TD).val(),
				iRow = "",
				iCol = "";
			if (thisTr && beforeEditCell) {
				var pass = beforeEditCell(rowid, cellname, value, iRow, iCol);
				if (!pass) {
					return false;
				}else{
					return true;
				}
			}
		},
		fillEditor:function(TD){
			var $t = this[0];
			var thisName = jQuery(TD).attr("name"),
				thWidth = jQuery(TD).innerWidth(),
				thHeight = jQuery(TD).innerHeight(),
				chaWidth = 10;

			jQuery(TD).find("span[name=toEdit]").find("input[type=text]").eq(0).css({
				width: thWidth - chaWidth + "px",
				height: thHeight - 10 + "px"
			});
			jQuery(TD).find("span[name=toShow]").hide();
			jQuery(TD).find("span[name=toEdit]").show();
			jQuery(TD).find("span[name=toEdit]").find("input").eq(0).focus();
			var editAbleModels = $t.p.editAbleModels,
				emIndex;
			for (var emi in editAbleModels) {
				if (editAbleModels[emi].name == thisName) {
					emIndex = emi;
					break;
				}
			}
			var row = jQuery(TD).parent().index(),
			    col = jQuery(TD).index(),
			    rowid = jQuery(TD).parent().attr("id");
			$t.p.activeCell = {
				name: thisName,
				row: row,
				col: col,
				emIndex: emIndex,
				state: 1
			};
			jQuery($t).dataInit(rowid, col - 1);
		},
		cellEdit: function(TD){
			var $t = this[0];
			var colName = jQuery(TD).attr("name");
			var colModel = jQuery(this).getColModel(colName);
			var editable = colModel.editable;
			if (editable) {
				var activeCell = $t.p.activeCell;
				if(activeCell&&activeCell.state!=2){
					var checkTime = 0;
					var stateTimer = setInterval(function(){
						activeCell = $t.p.activeCell;
						if(activeCell.state==2){
							clearInterval(stateTimer);
							//this.cellEdit($t,TD);
							this.cellEditPrepare($t,TD);
							this.fillEditor(TD);
						}else{
							if(checkTime==10){
								clearInterval(stateTimer);
							}
							checkTime++;
						}
					},50);
				}else{
					this.cellEditPrepare($t,TD);
					this.fillEditor(TD);
				}
			}
		},
		afterEditCell: function(editor){
			var $t = this[0];
			if (editor) {
				jQuery($t).cellChange(editor);
				$t.p.activeCell.state = 2;
				this.listen();
			}

		},
		changeData: function(cell,data){
			var $t = this[0];
			var dataSource = $t.grid.dataSource;
			var row = cell.row,
				colName = cell.name;
			if(!data){
				if(dataSource[row]){
					var nameArr = colName.split(".");
					var objStr = "dataSource[row]";
					for(var nameIndex=0; nameIndex<nameArr.length-1; nameIndex++){
						objStr += "['"+nameArr[nameIndex]+"']";
						var obj = eval("("+objStr+")");
						if(nameIndex==nameArr.length-2&&obj){
							delete obj["'"+nameArr[nameArr.length-1]+"'"];
						}
					}
				}
			}else{
				if(!dataSource[row]){
					dataSource[row] = {};
				}
				var nameArr = colName.split(".");
				var objStr = "dataSource[row]";
				for(var nameIndex=0; nameIndex<nameArr.length-1; nameIndex++){
					objStr += "['"+nameArr[nameIndex]+"']";
					var obj = eval("("+objStr+")");
					if(!obj){
						eval(objStr+" = {}");
					}
				}
				eval("dataSource["+row+"]."+colName+" = '"+data+"'");
			}
			
			
		},
		formatterRow: function(rowid) {
			var ts = this[0];
			if (!rowid) {
				return;
			}
			jQuery("#" + rowid).find("td").each(function() {
				var thisInput = jQuery(this).find("input:first");
				//jQuery(this).attr('editing','true');
				jQuery(ts).cellChange(thisInput);
			});
		},
		cellVal: function(val) {
			var ts = this[0];
			return val === undefined || val === null || val === "" ? "&#160;" : (ts.p.autoencode ? $.zgrid.htmlEncode(val) : val + "");
		},
		formatter: function(rowId, cellval, colpos, rwdat, _act) {
			var ts = this[0];
			if (!ts) {
				return;
			}
			var cm = ts.p.colModel[colpos],
				v;
			/*if(typeof cm.formatter === 'undefined'){
			
		}*/
			if (!cm) {
				return;
			}
			if (typeof cm.formatter !== 'undefined') {
				var opts = {
					rowId: rowId,
					colModel: cm,
					gid: ts.p.id,
					pos: colpos
				};
				if ($.isFunction(cm.formatter)) {
					v = cm.formatter.call(ts, cellval, opts, rwdat, _act);
				} else if ($.fmatter) {
					v = $.fn.fmatter.call(ts, cm.formatter, cellval, opts, rwdat, _act);
				} else {
					v = jQuery(ts).cellVal(cellval);
				}
			} else {
				v = jQuery(ts).cellVal(cellval);
			}
			return v;
		},
		addGridRowData11: function(rowid, rowdata) {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			if (!rowdata) {
				return;
			}
			for (var rd in rowdata) {
				jQuery($t).setCellData(rowid, rowdata[rd].name, rowdata[rd].value);
			}
			/*var thisTr = jQuery("#"+rowid);
		if(thisTr){
			
			}
		}*/
		},
		dataInit: function(rowid, i) {
			var $t = this[0];
			if (!$t) {
				return;
			}
			var initTableColModels = $t.p.colModel;
			if (!initTableColModels[i]) {
				return;
			}
			var editoptions = initTableColModels[i].editoptions;
			if (!editoptions) {
				return;
			}
			var datainit = editoptions.dataInit;
			if (datainit) {
				var thisInit = jQuery("#" + rowid, "#" + $t.id).find("input[name='" + initTableColModels[i].name + "']");
				datainit(thisInit);
			}
			/*for(var mi in initTableColModels){
			if(initTableColModels[mi].editable==true&&initTableColModels[mi].editoptions&&initTableColModels[mi].editoptions.dataInit){
				//alert("#"+tableName+">input[name='"+initTableColModels[mi].name+"']");
				jQuery("#"+ts.id).find("input[name='"+initTableColModels[mi].name+"']").each(function(){
					var dataInit = initTableColModels[mi].editoptions.dataInit;
					dataInit(jQuery(this));
				});
			}
		}*/
		},
		saveGrid: function() {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			$t.p.gridEdited = false;
			var initTableColModels = $t.p.colModel;
			var dataSource = $t.grid.dataSource;
			// gzy validate inputArea start
			var hasNull = false;
			var errorMsg = "";
			jQuery("#" + $t.p.formId + " .required").each(function() {
				if (!$(this).val()) {
					errorMsg += $(this).prev().text();
					hasNull = true;
				}
			});
			errorMsg = errorMsg.replaceAll(":", ",");
			if (hasNull) {
				alertMsg.error(errorMsg + "为必填项！");
				return;
			}
			// gzy validate inputArea end
			/*var tableData = new Array();
			jQuery("#" + $t.id).find("tr[used='true']").not("tr[remove='true']").each(function() {
				var rowData = {};
				var sn = jQuery(this).find("td[name=sn]").text();
				for (var mi in initTableColModels) {
					rowData[initTableColModels[mi].name] = jQuery(this).find("input[name='" + initTableColModels[mi].name + "']").val();
				}
				tableData.push(rowData);
			});*/
			if (dataSource.length == 0) {
				alertMsg.error("无保存数据，请检查！");
				return;
			}
			var trimDataSource = new Array();
			for(var rowIndex in dataSource){
				trimDataSource.push(dataSource[rowIndex]);
			}
			dataSource = trimDataSource;
			for(var rowIndex in dataSource){
				var row = dataSource[rowIndex];
				for (var mi in initTableColModels) {
					var colMode = initTableColModels[mi];
					if(colMode.editrules&&colMode.editrules.required){
						var colName = colMode.name;
						var nameArr = colName.split(".");
						var objStr = "row";
						for(var nameIndex in nameArr){
							objStr += "['"+nameArr[nameIndex]+"']";
							var obj = eval(objStr);
							if(!obj){
								alertMsg.error("第"+(parseInt(rowIndex)+1)+"条记录的 '"+colMode.label+"' 为必填项！");
								return ;
							}
							/*if(!(nameArr[nameIndex] in row)){
								alertMsg.error("第"+(parseInt(rowIndex)+1)+"条记录的 '"+colMode.label+"' 为必填项！");
								return ;
							}*/
						}
						var data = eval(objStr);
						if(!data){
							alertMsg.error("第"+(parseInt(rowIndex)+1)+"条记录的 '"+colMode.label+"' 为必填项！");
							return ;
						}
					}
				}
			}
			var jsonArrays = arrayToJson(dataSource);
			var retJsonString = JSON.stringify(jsonArrays);
			var formData = jQuery("#" + $t.p.formId).serializeArray();
			formData.push({
				name: $t.p.paramName,
				value: retJsonString
			});
			jQuery.ajax({
				dataType: 'json',
				url: $t.p.saveUrl,
				data: formData,
				type: 'POST',
				async: 'false',
				error: function(data) {
					//console.log(data);
				},
				success: function(data) {
					formCallBack(data);
					if (typeof($t.p.afterSaveFun) == 'function') {
						$t.p.afterSaveFun(data);
					}
				}
			});
		},
		addGridRow: function(rowId) {
			var $t = this[0];
			if (!$t) {
				return;
			}
			var sn = rowId.split("_")[1];
			var dataSource = $t.grid.dataSource;
			sn = parseInt(sn);
			if(sn<=dataSource.length-1){
				for(var i=dataSource.length-1 ; i>=sn ; i--){
					dataSource[i+1] = dataSource[i];
				}
				dataSource[sn] = {};
			}
			this.render();
			
			/*var rowid = rowId
			if (!rowid) {
				var row = jQuery("#" + $t.id).find("tr:visible:last");
				rowid = row.attr('id');
				if (!rowid) {
					return;
				}
				var sn = rowid.split("_")[1];
				if (sn) {
					sn = parseInt(sn);
					sn += 1;
					rowid = "tr_" + sn;
					var rowTemp = row.clone(true);
					rowTemp.attr("id", rowid);
					rowNext.removeClass("selected");
					row.after(rowTemp);
					jQuery($t).emptyRow(rowid);
				}
			} else {
				var row = jQuery("#" + rowid, "#" + $t.id);
				if (row.length > 0) {
					var rowNext = row.clone(true);
					rowNext.removeClass("selected");
					row.after(rowNext);
					jQuery($t).emptyRow(rowNext);
				}
			}
			//var optTr = jQuery("#"+rowId,"#"+$t.id);
			//optTr.attr("remove","true");
			//optTr.hide();
			this.trimGridRow();
			return rowid;*/
		},
		delRow: function(rowId) {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			var sn = rowId.split("_")[1];
			var dataSource = $t.grid.dataSource;
			sn = parseInt(sn);
			if(sn<=dataSource.length-1){
				for(var i=sn ; i<dataSource.length-1 ; i++){
					dataSource[i] = dataSource[i+1];
				}
				delete dataSource[dataSource.length-1];
			}
			this.render();
		},
		emptyRow: function(rowId) {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			if (!rowId) {
				return;
			}
			var sn = rowId.split("_")[1];
			var dataSource = $t.grid.dataSource;
			sn = parseInt(sn);
			delete dataSource[sn];
			this.render();
		},
		getGridData: function() {
			var $t = this[0];
			return $t.grid.dataSource;
			/*var colModel = $t.p.colModel;
			var rowData = new Array();
			var ii = 0;

			jQuery(this).find("tr[used='true").each(function() {
				var thisId = jQuery(this).attr('id');
				var ret = {};
				ret['rowid'] = thisId;
				for (var c in colModel) {
					var v = jQuery($t).getCellData(thisId, colModel[c].name);
					ret[colModel[c].name] = v;
				}
				rowData[ii] = ret;
				ii++;
			});
			return rowData;*/
		},
		trimGridRow: function() {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			var trHeight = $t.p.trHeight;
			if (!trHeight) {
				trHeight = 25;
			}
			//jQuery("#"+$t.id).find('tbody>tr').unbind("click");
			//jQuery("#"+ts.id+"foot").find('tbody>tr').unbind("click");
			var cardContainerHeight = jQuery("#" + $t.id + "_div").innerHeight();
			//var cardContainerWidth = jQuery("#"+ts.id+"_div").innerWidth();
			//var cardWidthPer = (cardContainerWidth-4)/cardContainerWidth*100;
			var cordNum = (cardContainerHeight - 50) / trHeight;
			cordNum = Math.floor(cordNum);
			/*var mainTableDivHeight = cordNum*trHeight+2;
		if(jQuery.browser.msie){
			mainTableDivHeight = cordNum*trHeight+1;
		}*/
			var widthScroll = $t.p.widthScroll;
			var heightScroll = $t.p.heightScroll;
			var tableWidth = $t.p.width;
			var trLength = jQuery("#" + $t.id + ">tbody>tr:visible").length;
			var scrollBarWidth = getScrollWidth();
			//console.log(scrollBarWidth);
			if (scrollBarWidth < 10) {
				scrollBarWidth = 20;
			}
			if (cordNum > trLength) {
				var chaNum = cordNum - trLength;
				for (var cn = 0; cn < chaNum; cn++) {
					var lastTr = jQuery("#" + $t.id + ">tbody>tr:last").clone(true);
					var id = lastTr.attr("id");
					var sn = id.split("_")[1];
					sn = parseInt(sn) + 1;
					id = id.split("_")[0] + "_" + sn;
					lastTr.attr("id", id);
					jQuery("#" + $t.id + ">tbody").append(lastTr);
					this.emptyRow(id);

				}
				var hs = $t.p.heightScroll;
				if (hs == true) {
					$t.p.heightScroll = false;

					if (!$t.p.shrinkToFit) {
						jQuery($t).resizeGridWidth(tableWidth + scrollBarWidth);
					} else {
						$t.p.width = tableWidth - scrollBarWidth;
						jQuery($t).resizeGridWidth();
					}
					//jQuery($t).resizeGridWidth();
					jQuery("#" + $t.id + "_foot").find("th[name=scrollbar]").hide();
					jQuery("#" + $t.id + "_foot").find("th:visible:last").addClass('lastTh');
					jQuery("#" + $t.id + "_head").find("th[name=scrollbar]").hide();
					jQuery("#" + $t.id + "_head").find("th:visible:last").addClass('lastTh');
				}
			} else if (cordNum < trLength) {
				//jQuery("#"+$t.id+"Div").css("overflow","auto");
				//jQuery("#"+$t.id+"Div").css("width","auto");
				//jQuery("#"+$t.id+"Div").css("height","auto");
				var hs = $t.p.heightScroll;
				if (hs != true) {
					$t.p.heightScroll = true;
					if (!$t.p.shrinkToFit) {
						jQuery($t).resizeGridWidth(tableWidth - scrollBarWidth);
					} else {
						$t.p.width = tableWidth + scrollBarWidth;
						jQuery($t).resizeGridWidth();
					}
					//jQuery($t).resizeGridWidth();
					jQuery("#" + $t.id + "_foot").find(".lastTh").removeClass('lastTh');
					jQuery("#" + $t.id + "_foot").find("th[name=scrollbar]").addClass("lastTh").show();
					jQuery("#" + $t.id + "_foot").find("th[name=scrollbar]>div").outerWidth(scrollBarWidth);
					jQuery("#" + $t.id + "_head").find(".lastTh").removeClass('lastTh');
					jQuery("#" + $t.id + "_head").find("th[name=scrollbar]").addClass("lastTh").show();
					jQuery("#" + $t.id + "_head").find("th[name=scrollbar]>div").outerWidth(scrollBarWidth).addClass("lastTh");
				}

			} else if (cordNum == trLength) {
				var hs = $t.p.heightScroll;
				if (hs == true) {
					$t.p.heightScroll = false;
					if (!$t.p.shrinkToFit) {
						jQuery($t).resizeGridWidth(tableWidth + scrollBarWidth);
					} else {
						$t.p.width = tableWidth - scrollBarWidth;
						jQuery($t).resizeGridWidth();
					}
					//jQuery($t).resizeGridWidth();
					jQuery("#" + $t.id + "_foot").find("th[name=scrollbar]").hide();
					jQuery("#" + $t.id + "_foot").find("th:visible:last").addClass('lastTh');
					jQuery("#" + $t.id + "_head").find("th[name=scrollbar]").hide();
					jQuery("#" + $t.id + "_head").find("th:visible:last").addClass('lastTh');
				}
			}
			this.adjustSn();

		},
		adjustSn: function() {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			var tri = 0;
			jQuery("#" + $t.id + ">tbody>tr:visible").each(function() {
				var id = jQuery(this).attr("id");
				if (tri % 2 == 0) {
					jQuery(this).addClass('trbg');
				} else {
					jQuery(this).removeClass('trbg');
				}
				var sn = id.split("_")[1];
				sn = parseInt(sn);
				if (tri != sn) {
					id = id.split("_")[0] + "_" + tri;
					jQuery(this).attr("id", id);
					var thisTd = jQuery(this).find("td[name=sn]").text(tri);;
					jQuery(this).find("input").each(function() {
						var inputId = jQuery(this).attr("id");
						if (inputId) {
							inputId = inputId.split("_")[0] + "_" + tri;
							jQuery(this).attr("id", inputId);
						}
					});
				}
				tri++;
			});
		},
		fullEdit: function() {
			var $t = this[0];
			if ($t.p.cellEdit == true) {
				jQuery("#" + $t.id).on("click","tbody>tr>td",function(){
					jQuery($t).cellEdit(this);
				});
				jQuery("#" + $t.id).on("click","input[type=text]",function(event){
					event.stopPropagation();
				});
				/*jQuery("#" + $t.id + ">tbody>tr>td").click(function() {
					jQuery($t).cellEdit($t,this);
				});*/
				jQuery("#" + $t.id).delegate("input[type=text]", "blur", function() {
					jQuery($t).afterEditCell(jQuery(this));
				});
				jQuery("#" + $t.id).delegate("input[type=checkbox]", "click", function() {
					//var editSpan = jQuery(this).parent();
					var checked = jQuery(this).attr("checked");
					if (checked) {
						jQuery(this).attr("checked", "checked").attr("value", "true");
						//editSpan.prev().find("input[type=checkbox]").attr("checked","checked").attr("value","true");
					} else {
						jQuery(this).removeAttr("checked").attr("value", "false");
						//editSpan.prev().find("input[type=checkbox]").removeAttr("checked").attr("value","false");
					}
					//jQuery($t).cellChange(jQuery(this));
					//editSpan.prev().show();
					//editSpan.hide();
				});
				jQuery("#" + $t.id).find("input[type=text]").unbind('click').bind('click', function(e) {
					e.stopPropagation();
				});
			}
		},
		footData: function(cols) {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			var colNames = null;
			if (!cols) {
				colNames = $t.p.footCols;
			} else {
				$t.p.footCols = cols;
				colNames = $t.p.footCols;
			}
			if (!colNames) return;
			var colModel = $t.p.colModel;
			var dataSource = $t.grid.dataSource;
			if(!dataSource){
				return ;
			}
			for (var ci in colNames) {
				var sum = 0;
				if (colNames[ci].name && colNames[ci].value) {
					if (colNames[ci].value == 'sum') {
						for(var rowIndex in dataSource){
							var rowData = dataSource[rowIndex];
							var data = rowData[colNames[ci].name];
							sum += isNaN(parseFloat(data)) ? 0 : parseFloat(data);
						}
					/*	jQuery("#" + $t.id).find("input[name='" + colNames[ci].name + "']").each(function() {
							sum += isNaN(parseFloat(jQuery(this).val())) ? 0 : parseFloat(jQuery(this).val());
						});*/
						//sum = sum.toFixed(2);
						var thIndex = jQuery("#" + $t.id + "_foot").find("th[name='" + colNames[ci].name + "']").index();
						v = this.formatter("", sum, thIndex - 1);
						jQuery("#" + $t.id + "_foot").find("th[name='" + colNames[ci].name + "']").html(v);
					} else {
						jQuery("#" + $t.id + "_foot").find("th[name='" + colNames[ci].name + "']").html(colNames[ci].value);
					}
				}


			}
		},
		maxRow: function(){
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			var trHeight = $t.p.trHeight,
				tableId = $t.id,
				dataLength =  $t.grid.dataSource? $t.grid.dataSource.length:0,
				cardContainerHeight = jQuery("#" + tableId + "_div").innerHeight(),
				cordNum = Math.floor((cardContainerHeight - 50) / trHeight);
			return cordNum>dataLength?cordNum:dataLength;
		},
		windowRow: function(){
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			var trHeight = $t.p.trHeight,
				tableId = $t.id,
				cardContainerHeight = jQuery("#" + tableId + "_div").innerHeight(),
				cordNum = Math.floor((cardContainerHeight - 50) / trHeight);
			return cordNum;
		},
		resizeGridWidth: function(resizeWidth, changeName, changeWidth) {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			var gridWidth = jQuery("#" + $t.id + "_div").innerWidth() - 4;
			var tableWidth = $t.p.width;
			var minWidth = $t.p.minWidth;
			//console.log("resizeGridWidth{gridWidth:"+gridWidth+",tableWidth:"+tableWidth+",minWidth:"+minWidth+"}");
			if (resizeWidth) {
				gridWidth = resizeWidth;
			} else {
				if (gridWidth < minWidth) {
					if (!$t.p.shrinkToFit) {
						gridWidth = minWidth;
					}
				}
				/*if(gridWidth<tableWidth){
				
				if(gridWidth<minWidth){
					gridWidth = minWidth;
				}else{
					if(!$t.p.shrinkToFit){
						gridWidth = tableWidth;
					}
				}
				
			}*/
			}

			//if($t.p.shrinkToFit){
			//	var gridWidth = jQuery("#"+$t.id+"_div").innerWidth()-4;
			var wRow = this.windowRow();
			var mRow = this.maxRow();
			if(mRow>wRow){
				gridWidth -= 20;
			}
			var colModels = $t.p.colModel;
			var showColNum = $t.p.showColNum;
			var widthCha = gridWidth - tableWidth;
			var eachCha = widthCha / showColNum;
			eachCha = Math.floor(eachCha);
			if (eachCha == 0 && !changeName) {
				var tableW = jQuery("#" + $t.id + "").outerWidth(true);
				jQuery("#" + $t.id + "Div").width(tableW);
				jQuery("#" + $t.id + "_head").width(tableW - 4);
				return;
			}
			var tdpointer = 0;
			var showModelPointer = 0;
			var mendWidth = widthCha - eachCha * showColNum,
				y = 0;
			var widthArr = new Array();
			/*jQuery("#"+$t.id+"_head>thead>tr:first").find("th:visible").css('width',"1px");
			jQuery("#"+$t.id+"_head>thead>tr:first").find("th>div:visible").css('width',"1px");
			jQuery("#"+$t.id+">tbody>tr:first").find("th:visible").css('width',"1px");
			jQuery("#"+$t.id+">tbody>tr:first").find("th>div:visible").css('width',"1px");
			jQuery("#"+$t.id+">tbody>tr>td").css('width',"1px");
			jQuery("#"+$t.id+">tbody>tr>td>div").css('width',"1px");
			jQuery("#"+$t.id+"_foot>tfoot>tr:first").find("th:visible").css('width',"1px");
			jQuery("#"+$t.id+"_foot>tfoot>tr:first").find("th>div:visible").css('width',"1px");*/
			var xxWidth = 0;
			for(var modelIndex in colModels){
				var colModel = colModels[modelIndex];
				if(!colModel.hidden){
					showModelPointer++;
					if(showModelPointer==showColNum){
						colModel.width +=  (eachCha + mendWidth);
					}else{
						colModel.width +=  eachCha;
					}
					xxWidth += colModel.width;
				}
			}
			$t.p.width = xxWidth + 25 +1;
/*
			jQuery("#" + $t.id + "_head>thead>tr:first").find("th:visible").each(function() {
				var tdWidth = jQuery(this).css('width');
				var tdName = jQuery(this).attr('name');
				tdWidth = $t.p.colWidth[tdName];
				tdWidth = parseInt(tdWidth);
				if (changeName && changeName == tdName) {
					if (!changeWidth) {
						changeWidth = 0;
					}
					tdWidth += changeWidth;
					gridWidth += changeWidth;
				}
				if (tdWidth) {
					tdWidth -= 1;
					var tw = tdWidth + eachCha;
					if (y > 0) {
						if (eachCha < 0) {
							tw -= y;
						} else {
							tw += y;
						}

					}
					if (tw < 4) {
						y = 4 - tw;
						tw = 4;
					} else {
						y = 0;
					}
					if (tdpointer == showColNum) {
						jQuery(this).find('div').css('width', tw + mendWidth - 4 - 3 + "px");
						jQuery(this).css('width', tw + mendWidth + "px");
						widthArr[tdpointer] = tw + mendWidth;
						$t.p.colWidth[tdName] = tw + mendWidth + 1;
					} else if (tdpointer != 0) {
						jQuery(this).find('div').css('width', tw - 4 - 3 + "px");
						jQuery(this).css('width', tw + "px");
						widthArr[tdpointer] = tw;
						$t.p.colWidth[tdName] = tw + 1;
					}
				}
				tdpointer++;
			});
			tdpointer = 0;
			jQuery("#" + $t.id + ">tbody>tr:first").find("th:visible").each(function() {
				//var tdWidth = jQuery(this).css('width');
				//tdWidth = parseInt(tdWidth);
				//if(tdpointer==showColNum-1){
				jQuery(this).css('width', widthArr[tdpointer] + "px");
				jQuery(this).find('div').css('width', widthArr[tdpointer] - 4 + "px");
				//}else if(tdpointer!=0){
				//jQuery(this).css('width',tdWidth+eachCha+"px");
				//jQuery(this).find('div').css('width',tdWidth+eachCha-4+"px");
				//}
				tdpointer++;
			});
			jQuery("#" + $t.id + ">tbody>tr").each(function() {
				tdpointer = 0;
				jQuery(this).find("td:visible").each(function() {
					//var tdWidth = jQuery("#"+$t.id+">tbody>tr:first").find("th:visible").eq(tdpointer).css('width');
					//tdWidth = parseInt(tdWidth);
					jQuery(this).css('width', widthArr[tdpointer] + "px");
					jQuery(this).find('div').css('width', widthArr[tdpointer] - 4 + "px");
					tdpointer++;
				});
			});
			tdpointer = 0;
			jQuery("#" + $t.id + "_foot>tfoot>tr:first").find("th:visible").each(function() {
				jQuery(this).css('width', widthArr[tdpointer] + "px");
				jQuery(this).find('div').css('width', widthArr[tdpointer] - 4 + "px");
				tdpointer++;
			});
			var tableW = jQuery("#" + $t.id + "").outerWidth(true);
			jQuery("#" + $t.id + "Div").width(tableW);
			jQuery("#" + $t.id + "_head").width(tableW - 4);
			$t.p.width = gridWidth;*/
			//}
		},
		renderHead: function(){
			var $t = this[0];
			var trHeight = $t.p.trHeight,
				colModel = $t.p.colModel,
				tableId = $t.id;
			jQuery("#" + tableId + "_head>colgroup>col").remove();
			jQuery("#" + tableId + "_head>thead>tr>th").remove();
			var headTr = jQuery("#" + tableId + "_head>thead>tr");
			var colgroup = jQuery("#"+tableId + "_head").find("colgroup");
			colgroup.append("<col align='center' style='width:25px'>");
			headTr.append("<th></th>");
			for(var col in colModel){
				var colWidth = colModel[col].width ; 
				/*if (!$t.p.colWidth) {
				$t.p.colWidth = {};
			}
				$t.p.colWidth[colModel[col].name] = colWidth;*/
				var colset = jQuery("<col align='center' style='width:"+colWidth+"px'>");
				//"<td align='" + align + "' editable='" + initTableColModels[mi].editable + "' name='" + initTableColModels[mi].name + "' style='" + hide + "width:" + (width - 1) + "px;'><div style='width:" + (width - 5) + "px;" + color + "'>" + cellStr + "</div></td>";
				//headStr += "<th name='" + initTableColModels[mi].name + "' style='" + hide + "width:" + (width - 1) + "px;'><span class='ui-zhgrid-resize' style='cursor: col-resize;'>&nbsp;</span><div style='width:" + (width - 5 - 3) + "px'>" + initTableColModels[mi].label + "</div></th>";
				var hidden = colModel[col].hidden ; 
				var TH = jQuery("<th name='" + colModel[col].name + "'> "+colModel[col].label +"</th>");
				if(hidden){
					colset.hide();
					TH.hide();
				}
				colgroup.append(colset);
				headTr.append(TH);
			}
			headTr.append("<th  name='scrollbar' style='display:none'></th>");
			headTr.css("height", trHeight + "px");
			jQuery("#" + tableId + "_head").css("width",$t.p.width);
		},
		renderBody: function(){
			var $t = this[0];
			var maxRow = this.maxRow();
			$t.p.maxRow = maxRow;
			var trHeight = $t.p.trHeight,
				colModel = $t.p.colModel,
				tableId = $t.id;
			jQuery("#"+tableId).find("colgroup>col").remove();
			jQuery("#"+tableId).find("tbody>tr").remove();
			var trEx = jQuery("<tr></tr>");
			trEx.css("height", trHeight + "px");
			var colgroup = jQuery("#"+tableId).find("colgroup");
			colgroup.append("<col align='center' style='width:25px'>");
			trEx.append("<td align='center' name='sn'></td>");
			for(var col in colModel){
				var colWidth = colModel[col].width ; 
				var colAlign = colModel[col].align ; 
				var hidden = colModel[col].hidden ; 
				var colset = jQuery("<col align='center' style='width:"+colWidth+"px'>");
				//"<td align='" + align + "' editable='" + initTableColModels[mi].editable + "' name='" + initTableColModels[mi].name + "' style='" + hide + "width:" + (width - 1) + "px;'><div style='width:" + (width - 5) + "px;" + color + "'>" + cellStr + "</div></td>";
				var cellStr = "<span name='toShow' class=''></span><span name='toEdit' style='display:none'><input id='" + colModel[col].name + "_1' name='" + colModel[col].name + "' type='text' class=''/></span>";
				var TD = jQuery("<td name='" + colModel[col].name + "'><div>" + cellStr + "</div></td>");
				if(hidden){
					TD.hide();
					colset.hide();
				}
				colgroup.append(colset);
				trEx.append(TD);
			}
			var tbody = jQuery("#"+tableId).find("tbody");
			for(var rowIndex=0;rowIndex<maxRow;rowIndex++){
				var rowDom = trEx.clone(true);
				rowDom.attr("id","tr_"+rowIndex);
				rowDom.find("td[name=sn]").text(rowIndex+1);
				tbody.append(rowDom);
			}
			this.rightClick();
			jQuery("#" + tableId ).css("width",$t.p.width);
			var windowRow = this.windowRow();
			var windowHeight = windowRow*trHeight + 1;
			var windowWIdth = jQuery("#" + tableId +"_div").width();
			jQuery("#" + tableId +"Div").outerHeight(windowHeight);
			jQuery("#" + tableId +"Div").outerWidth(windowWIdth);

		},
		renderFoot: function(){
			var $t = this[0];
			var trHeight = $t.p.trHeight,
				colModel = $t.p.colModel,
				tableId = $t.id;
			jQuery("#" + tableId + "_foot>colgroup>col").remove();
			jQuery("#" + tableId + "_foot>tfoot>tr>th").remove();
			var footTr = jQuery("#" + tableId + "_foot>tfoot>tr");
			var colgroup = jQuery("#"+tableId+ "_foot").find("colgroup");
			colgroup.append("<col align='center' style='width:25px'>");
			footTr.append("<th></th>");
			for(var col in colModel){
				var colWidth = colModel[col].width ; 
				var colAlign = colModel[col].align ; 
				var colset = jQuery("<col align='"+colAlign+"' style='width:"+colWidth+"px'>");
				//"<td align='" + align + "' editable='" + initTableColModels[mi].editable + "' name='" + initTableColModels[mi].name + "' style='" + hide + "width:" + (width - 1) + "px;'><div style='width:" + (width - 5) + "px;" + color + "'>" + cellStr + "</div></td>";
				//headStr += "<th name='" + initTableColModels[mi].name + "' style='" + hide + "width:" + (width - 1) + "px;'><span class='ui-zhgrid-resize' style='cursor: col-resize;'>&nbsp;</span><div style='width:" + (width - 5 - 3) + "px'>" + initTableColModels[mi].label + "</div></th>";
				var hidden = colModel[col].hidden ; 
				var TH = jQuery("<th name='" + colModel[col].name + "'> </th>");
				if(hidden){
					TH.hide();
					colset.hide();
				}
				colgroup.append(colset);
				footTr.append(TH);
			}
			footTr.append("<th  name='scrollbar' style='display:none'></th>");
			footTr.css("height", trHeight + "px");
			jQuery("#" + tableId + "_foot").css("width",$t.p.width);
		},
		resizeWindow: function(){
			var $t = this[0];
			var tableId = $t.id;
			var windowWIdth = jQuery("#" + tableId +"_div").width();
			jQuery("#" + tableId +"Div").outerWidth(windowWIdth+jQuery("#"+tableId+"_div").scrollLeft());
		},
		resizeGridHeight: function() {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			var tableId = $t.id;
			//adjust card width and row count
			var trHeight = $t.p.trHeight;
			if (!trHeight) {
				trHeight = 25;
			}
			//jQuery("#"+tableId).find('tbody>tr').unbind("click");
			jQuery("#" + tableId + "foot").find('tbody>tr').unbind("click");
			var cardContainerHeight = jQuery("#" + tableId + "_div").innerHeight();
			if ($t.p.height == cardContainerHeight) {
				return;
			}
			var cardContainerWidth = jQuery("#" + tableId + "_div").innerWidth();
			var cardWidthPer = (cardContainerWidth - 4) / cardContainerWidth * 100;
			var cordNum = (cardContainerHeight - 50) / trHeight;
			//console.log(cardContainerHeight);
			//console.log(cordNum);
			var realNum = Math.floor(cordNum);
			var mendHeight = (realNum - cordNum) * trHeight;
			if ($t.p.width > (cardContainerWidth - 4)) {
				var scrollBarWidth = getScrollWidth();
				if (scrollBarWidth < 10) {
					cardContainerHeight += 20;
				} else {
					cardContainerHeight += scrollBarWidth;
				}
			} else {
				jQuery("#" + tableId + "Div").css("width", "auto");
			}
			$t.p.height = cardContainerHeight + mendHeight + 9;
			//$t.p.mendHeight = mendHeight;
			jQuery("#" + tableId + "_div").innerHeight(cardContainerHeight + mendHeight + 9);
			cordNum = realNum;
			//console.log(cordNum);
			var mainTableDivHeight = cordNum * trHeight + 1;
			if (jQuery.browser.msie) {
				mainTableDivHeight = cordNum * trHeight + 1;
			}

			jQuery("#" + tableId + "_head>thead>tr").css("height", trHeight + "px");
			jQuery("#" + tableId + "_foot>tfoot>tr").css("height", trHeight + "px");
			jQuery("#" + tableId + "Div").outerHeight(mainTableDivHeight);
			//jQuery("#"+tableName).css("width",cardWidthPer+"%");
			//jQuery("#"+tableName+"_foot").css("width",cardWidthPer+"%");
			//jQuery("#"+tableName+"_head").css("width",cardWidthPer+"%");

			var countPointer = 0;

			var cardBody = jQuery("#" + tableId + ">tbody:first");
			var firstTr = jQuery("#" + tableId).find("tbody>tr").eq(1).removeClass("trbg").clone(true);
			jQuery("#" + tableId).find("tbody>tr").eq(1).css("height", trHeight + "px");

			var hasTr = jQuery("#" + tableId).find("tbody>tr:visible").length - 1;

			firstTr.removeClass("hover");
			firstTr.removeClass("firstTr");
			if (hasTr >= cordNum) {
				//jQuery($t)
				for (var i = 0; i < hasTr - cordNum; i++) {
					var trT = jQuery("#tr_" + (cordNum + i + 1), "#" + $t.id);
					if (trT.attr("used") != 'true') {
						trT.remove();
					}
				}
			}
			for (var i = hasTr; i < cordNum; i++) {
				var cloneTr = firstTr.clone(true);
				//var sn = cloneTr.find("td[name=sn]").text();
				//sn = parseInt(sn)+1;
				cloneTr.find("td[name=sn]").text(i + 1);
				cloneTr.find("td").find("input").each(function() {
					var thisName = jQuery(this).attr("name");
					if (thisName != 'sn') {
						jQuery(this).attr("id", thisName + "_" + i + 1);
					}
				});
				cloneTr.css("height", trHeight + "px");
				//cloneTr.attr("rel",""+(i+2));
				cloneTr.attr("id", "tr_" + (i + 1));
				if ((i + 1) % 2 == 0) cloneTr.addClass("trbg");
				this.emptyRow(cloneTr);
				cardBody.append(cloneTr);
			}
			jQuery($t).adjustTableByScrollBar();
		},
		adjustTableByScrollBar: function() {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			var tableId = $t.id;
			var wRow = this.windowRow();
			var mRow = this.maxRow();
			//var scrollBarWidth = jQuery("#" + tableId + "Div").outerWidth() - jQuery("#" + tableId).outerWidth();
			if (mRow > wRow) {
				jQuery("#" + tableId + "_foot").find("th[name=scrollbar]").outerWidth(22 - 4).show();
				jQuery("#" + tableId + "_head").find("th[name=scrollbar]").outerWidth(22 - 4).show();
			}
		},
		render: function(){
			this.resizeGridWidth();
			this.renderHead();
			this.renderBody();
			this.renderFoot();
			this.adjustTableByScrollBar();
			this.fillData();
		},
		resizeGrid: function() {
			var $t = this[0];
			if (!$t || !$t.grid) {
				return;
			}
			jQuery($t).resizeGridWidth();
			jQuery($t).renderHead();
			jQuery($t).renderBody();
			jQuery($t).renderFoot();
			jQuery($t).adjustTableByScrollBar();
			jQuery($t).fillData();
			//jQuery($t).resizeGridHeight();
		},
		setGridParam: function(newParams) {
			var $t = this[0];
			$.extend(true, $t.p, newParams);
			return this;
		},
		reload: function() {
			var $t = this[0];
			var datas = $t.p.jsonReader.root;
			var initTableColModels = $t.p.colModel;
			jQuery.ajax({
				dataType: 'json',
				url: $t.p.url,
				//data : formData,
				type: 'POST',
				async: 'false',
				error: function(data) {
					//console.log(data);
				},
				success: function(data) {
					var gridDatas = data[datas];
					$t.grid.dataSource = gridDatas;
					jQuery($t).render();
				}
			});
		},
		addLocalData: function(rowDatas) {
			var $t = this[0];
			if (!rowDatas || (rowDatas.length == 0)) {
				return;
			}
			//var gridDatas = data[datas];
			$t.grid.dataSource = rowDatas;
			this.render();
		},
		addZhLocalData: function(rowDatas) {
			var $t = this[0];
			if (!rowDatas || (rowDatas.length == 0)) {
				return;
			}
			//var gridDatas = data[datas];
			$t.grid.dataSource = rowDatas;
			this.render();
		},
		showHideGridCol: function(col, showOrHide) {
			var $t = this[0];
			if (showOrHide == 'block') {
				var colIsShow = jQuery("th[name='" + col + "']:first", "#" + $t.id + "_head").css("display");
				if (colIsShow != 'none') {
					return;
				}
				jQuery("th[name='" + col + "']", "#" + $t.id + "_head").show();
				jQuery("th[name='" + col + "']", "#" + $t.id).show();
				jQuery("th[name='" + col + "']", "#" + $t.id + "_foot").show();
				jQuery("td[name='" + col + "']", "#" + $t.id).show();
				var i = jQuery(this).getColIndex(col);
				$t.p.colModel[i].hidden = false;
				$t.p.showColNum += 1;
				//var tableW = jQuery("#"+$t.id+"").outerWidth(true);
				$t.p.width += parseInt($t.p.colModel[i].width);
			} else {
				var colIsShow = jQuery("th[name='" + col + "']:first", "#" + $t.id + "_head").css("display");
				if (colIsShow == 'none') {
					return;
				}
				jQuery("td[name='" + col + "']", "#" + $t.id).hide();
				jQuery("th[name='" + col + "']", "#" + $t.id + "_head").hide();
				jQuery("th[name='" + col + "']", "#" + $t.id).hide();
				jQuery("th[name='" + col + "']", "#" + $t.id + "_foot").hide();
				var i = jQuery(this).getColIndex(col);
				$t.p.colModel[i].hidden = true;
				$t.p.showColNum -= 1;
				//var tableW = jQuery("#"+$t.id+"").outerWidth(true);
				$t.p.width -= parseInt($t.p.colModel[i].width);
			}
			jQuery(this).resizeGrid();
		},
		setGridColWidth: function(i, width) {
			var $t = this[0];
			if (!width) {
				return;
			}
			var oldWidth = $t.p.colModel[i].width;
			var tableW = $t.p.width;
			var diff = width - oldWidth;
			if (diff != 0) {
				$t.p.colModel[i].width = width;
				jQuery(this).resizeGridWidth(null, $t.p.colModel[i].name, diff);
			}
		},
		remapCols: function(newOrder) {
			var $t = this[0];
			var oArr = new Array();
			var colModels = $t.p.colModel;
			var newColModel = new Array();
			for (var oi = 0; oi < newOrder.length + 2; oi++) {
				var n = 0;
				if (oi == 0) {
					n = 0;
				} else if (oi == (newOrder.length + 1)) {
					n = newOrder.length + 1;
				} else {
					n = newOrder[oi - 1] + 1;
					newColModel.push(colModels[newOrder[oi - 1]]);
				}
				oArr.push(n);
			}
			$t.p.colModel = newColModel;
			var headTrs = jQuery("#" + $t.id + "_head").find("tr").clone(true);
			var bodyTrFirst = jQuery("#" + $t.id).find("tr:first").clone(true);
			var bodyTrs = jQuery("#" + $t.id).find("tr").clone(true);
			var tbody = jQuery("#" + $t.id + ">tbody").clone(true);
			tbody.empty();
			var footTrs = jQuery("#" + $t.id + "_foot").find("tr").clone(true);
			headTrs.each(function(tri) {
				var thisTr = jQuery(this);
				var thisTrClone = thisTr.clone(true);
				thisTrClone.empty();
				thisTr.find("th").each(function(tdi) {
					thisTrClone.append(thisTr.find('th').eq(oArr[tdi]).clone(true));
				});
				jQuery("#" + $t.id + "_head").find("tr").eq(tri).remove();
				jQuery("#" + $t.id + "_head>thead").append(thisTrClone);
			});
			bodyTrFirst.each(function(tri) {
				var thisTr = jQuery(this);
				var thisTrClone = thisTr.clone(true);
				thisTrClone.empty();
				thisTr.find("th").each(function(tdi) {
					thisTrClone.append(thisTr.find('th').eq(oArr[tdi]).clone(true));
				});
				//jQuery("#"+$t.id).find("tr").eq(tri).remove();
				tbody.append(thisTrClone);
			});
			bodyTrs.each(function(tri) {
				if (tri == 0) {
					return true;
				}
				var thisTr = jQuery(this);
				var thisTrClone = thisTr.clone(true);
				thisTrClone.empty();
				thisTr.find("td").each(function(tdi) {
					thisTrClone.append(thisTr.find('td').eq(oArr[tdi]).clone(true));
				});
				//jQuery("#"+$t.id).find("tr").eq(tri).remove();
				tbody.append(thisTrClone);
			});
			jQuery("#" + $t.id).empty();
			jQuery("#" + $t.id).append(tbody);
			footTrs.each(function(tri) {
				var thisTr = jQuery(this);
				var thisTrClone = thisTr.clone(true);
				thisTrClone.empty();
				thisTr.find("th").each(function(tdi) {
					thisTrClone.append(thisTr.find('th').eq(oArr[tdi]).clone(true));
				});
				jQuery("#" + $t.id + "_foot").find("tr").eq(tri).remove();
				jQuery("#" + $t.id + "_foot>tfoot").append(thisTrClone);
			});
		},
		rightClick: function() {
			var $t = this[0];
			var tableId = $t.id;
			jQuery("#" + tableId + ">tbody>tr").each(function() {
				jQuery(this).contextMenu('tableCM', {
					bindings: {
						'addRow': function(t) {
							var rowid = t.attr('id');
							$('#' + tableId).addGridRow(rowid);
						},
						'delRow': function(t) {
							var rowid = t.attr('id');
							$('#' + tableId).delRow(rowid);
						},
						'emptyRow': function(t) {
							var rowid = t.attr('id')
							$('#' + tableId).emptyRow(rowid);
						}
					},
					onContextMenu: function(e) {
						return true;

					},
					onShowMenu: function(e, trigger, menu) {
						jQuery("#" + trigger.id).trigger('click');
						return menu;
					}
				});
			});
		},
		listen: function() {
			var $t = this[0];
			var listen = $t.p.listen;
			for (var event in listen) {
				listen[event] = true;
			}
		},
		unlisten: function() {
			var $t = this[0];
			var listen = $t.p.listen;
			for (var event in listen) {
				listen[event] = false;
			}
		},
		subListen: function(listenState) {
			var $t = this[0];
			var listen = $t.p.listen;
			jQuery.extend(listen, listenState);
		}
	});
})(jQuery);

function resizeZhGrid() {
	jQuery(".zhGrid").resizeGrid();
}

function reMapData(row,colMap){
	var newRow ; 
	for (var name in row) {
		var newName = colMap.get(name);
		if (!newName) continue;
		var nameArr = newName.split(".");
		var objStr = "row";
		for(var nameIndex=0; nameIndex<nameArr.length-1; nameIndex++){
			objStr += "['"+nameArr[nameIndex]+"']";
			var obj = eval("("+objStr+")");
			if(!obj){
				eval("("+objStr+" = {})");
			}
		}
		var data = row[name];
		//alert(data);
		if(!data){
			data = "";
		}
		eval("row."+newName+" = '"+data+"'");
		//row["'"+newName+"'"] = row["'"+name+"'"];
		//delete  row["'"=name+"'"] ;
	}
	newRow = row ;
	return newRow;
}