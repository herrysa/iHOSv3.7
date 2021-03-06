/**
 * jqGrid extension for manipulating Grid Data Tony Tomov tony@trirand.com
 * http://trirand.com/blog/ Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl-2.0.html
 */
(function(a) {
	a.jgrid.inlineEdit = a.jgrid.inlineEdit || {};
	a.jgrid
			.extend({
				editRow : function(c, l, k, f, b, g, e, h, i) {
					var d = {}, j = a.makeArray(arguments).slice(1);
					if (a.type(j[0]) === "object") {
						d = j[0]
					} else {
						if (typeof l !== "undefined") {
							d.keys = l
						}
						if (a.isFunction(k)) {
							d.oneditfunc = k
						}
						if (a.isFunction(f)) {
							d.successfunc = f
						}
						if (typeof b !== "undefined") {
							d.url = b
						}
						if (typeof g !== "undefined") {
							d.extraparam = g
						}
						if (a.isFunction(e)) {
							d.aftersavefunc = e
						}
						if (a.isFunction(h)) {
							d.errorfunc = h
						}
						if (a.isFunction(i)) {
							d.afterrestorefunc = i
						}
					}
					d = a.extend(true, {
						keys : false,
						oneditfunc : null,
						successfunc : null,
						url : null,
						extraparam : {},
						aftersavefunc : null,
						errorfunc : null,
						afterrestorefunc : null,
						restoreAfterError : true,
						mtype : "POST"
					}, a.jgrid.inlineEdit, d);
					return this
							.each(function() {
								var p = this, u, q, n, o = 0, t = null, s = {}, m, r;
								if (!p.grid) {
									return
								}
								m = a(p).jqGrid("getInd", c, true);
								if (m === false) {
									return
								}
								n = a(m).attr("editable") || "0";
								if (n == "0"
										&& !a(m).hasClass("not-editable-row")) {
									r = p.p.colModel;
									a('td[role="gridcell"]', m)
											.each(
													function(y) {
														u = r[y].name;
														var x = p.p.treeGrid === true
																&& u == p.p.ExpandColumn;
														if (x) {
															q = a("span:first",
																	this)
																	.html()
														} else {
															try {
																q = a.unformat
																		.call(
																				p,
																				this,
																				{
																					rowId : c,
																					colModel : r[y]
																				},
																				y)
															} catch (v) {
																q = (r[y].edittype && r[y].edittype == "textarea") ? a(
																		this)
																		.text()
																		: a(
																				this)
																				.html()
															}
														}
														if (u != "cb"
																&& u != "subgrid"
																&& u != "rn") {
															if (p.p.autoencode) {
																q = a.jgrid
																		.htmlDecode(q)
															}
															s[u] = q;
															if (r[y].editable === true) {
																if (t === null) {
																	t = y
																}
																if (x) {
																	a(
																			"span:first",
																			this)
																			.html(
																					"")
																} else {
																	a(this)
																			.html(
																					"")
																}
																var w = a
																		.extend(
																				{},
																				r[y].editoptions
																						|| {},
																				{
																					id : c
																							+ "_"
																							+ u,
																					name : u
																				});
																if (!r[y].edittype) {
																	r[y].edittype = "text"
																}
																if (q == "&nbsp;"
																		|| q == "&#160;"
																		|| (q.length == 1 && q
																				.charCodeAt(0) == 160)) {
																	q = ""
																}
																var z = a.jgrid.createEl
																		.call(
																				p,
																				r[y].edittype,
																				w,
																				q,
																				true,
																				a
																						.extend(
																								{},
																								a.jgrid.ajaxOptions,
																								p.p.ajaxSelectOptions
																										|| {}));
																a(z)
																		.addClass(
																				"editable");
																if (x) {
																	a(
																			"span:first",
																			this)
																			.append(
																					z)
																} else {
																	a(this)
																			.append(
																					z)
																}
																if (r[y].edittype == "select"
																		&& typeof (r[y].editoptions) !== "undefined"
																		&& r[y].editoptions.multiple === true
																		&& typeof (r[y].editoptions.dataUrl) === "undefined"
																		&& a.browser.msie) {
																	a(z)
																			.width(
																					a(
																							z)
																							.width())
																}
																o++
															}
														}
													});
									if (o > 0) {
										s.id = c;
										p.p.savedRow.push(s);
										a(m).attr("editable", "1");
										a("td:eq(" + t + ") input", m).focus();
										if (d.keys === true) {
											a(m)
													.bind(
															"keydown",
															function(w) {
																if (w.keyCode === 27) {
																	a(p)
																			.jqGrid(
																					"restoreRow",
																					c,
																					d.afterrestorefunc);
																	if (p.p._inlinenav) {
																		try {
																			a(p)
																					.jqGrid(
																							"showAddEditButtons")
																		} catch (y) {
																		}
																	}
																	return false
																}
																if (w.keyCode === 13) {
																	var v = w.target;
																	if (v.tagName == "TEXTAREA") {
																		return true
																	}
																	if (a(p)
																			.jqGrid(
																					"saveRow",
																					c,
																					d)) {
																		if (p.p._inlinenav) {
																			try {
																				a(
																						p)
																						.jqGrid(
																								"showAddEditButtons")
																			} catch (x) {
																			}
																		}
																	}
																	return false
																}
															})
										}
										a(p)
												.triggerHandler(
														"jqGridInlineEditRow",
														[ c, d ]);
										if (a.isFunction(d.oneditfunc)) {
											d.oneditfunc.call(p, c)
										}
									}
								}
							})
				},
				saveRow : function(l, i, g, h, q, n, b) {
					var d = a.makeArray(arguments).slice(1), z = {};
					if (a.type(d[0]) === "object") {
						z = d[0]
					} else {
						if (a.isFunction(i)) {
							z.successfunc = i
						}
						if (typeof g !== "undefined") {
							z.url = g
						}
						if (typeof h !== "undefined") {
							z.extraparam = h
						}
						if (a.isFunction(q)) {
							z.aftersavefunc = q
						}
						if (a.isFunction(n)) {
							z.errorfunc = n
						}
						if (a.isFunction(b)) {
							z.afterrestorefunc = b
						}
					}
					z = a.extend(true, {
						successfunc : null,
						url : null,
						extraparam : {},
						aftersavefunc : null,
						errorfunc : null,
						afterrestorefunc : null,
						restoreAfterError : true,
						mtype : "POST"
					}, a.jgrid.inlineEdit, z);
					var m = false;
					var C = this[0], c, E = {}, y = {}, x = {}, A, j, f, t;
					if (!C.grid) {
						return m
					}
					t = a(C).jqGrid("getInd", l, true);
					if (t === false) {
						return m
					}
					A = a(t).attr("editable");
					z.url = z.url ? z.url : C.p.editurl;
					if (A === "1") {
						var r;
						a('td[role="gridcell"]', t)
								.each(
										function(o) {
											r = C.p.colModel[o];
											c = r.name;
											if (c != "cb"
													&& c != "subgrid"
													&& r.editable === true
													&& c != "rn"
													&& !a(this)
															.hasClass(
																	"not-editable-cell")) {
												switch (r.edittype) {
												case "checkbox":
													var k = [ "Yes", "No" ];
													if (r.editoptions) {
														k = r.editoptions.value
																.split(":")
													}
													E[c] = a("input", this).is(
															":checked") ? k[0]
															: k[1];
													break;
												case "text":
												case "password":
												case "textarea":
												case "button":
													E[c] = a("input, textarea",
															this).val();
													break;
												case "select":
													if (!r.editoptions.multiple) {
														E[c] = a(
																"select option:selected",
																this).val();
														y[c] = a(
																"select option:selected",
																this).text()
													} else {
														var F = a("select",
																this), H = [];
														E[c] = a(F).val();
														if (E[c]) {
															E[c] = E[c]
																	.join(",")
														} else {
															E[c] = ""
														}
														a(
																"select option:selected",
																this)
																.each(
																		function(
																				e,
																				I) {
																			H[e] = a(
																					I)
																					.text()
																		});
														y[c] = H.join(",")
													}
													if (r.formatter
															&& r.formatter == "select") {
														y = {}
													}
													break;
												case "custom":
													try {
														if (r.editoptions
																&& a
																		.isFunction(r.editoptions.custom_value)) {
															E[c] = r.editoptions.custom_value
																	.call(
																			C,
																			a(
																					".customelement",
																					this),
																			"get");
															if (E[c] === undefined) {
																throw "e2"
															}
														} else {
															throw "e1"
														}
													} catch (G) {
														if (G == "e1") {
															a.jgrid
																	.info_dialog(
																			a.jgrid.errors.errcap,
																			"function 'custom_value' "
																					+ a.jgrid.edit.msg.nodefined,
																			a.jgrid.edit.bClose)
														}
														if (G == "e2") {
															a.jgrid
																	.info_dialog(
																			a.jgrid.errors.errcap,
																			"function 'custom_value' "
																					+ a.jgrid.edit.msg.novalue,
																			a.jgrid.edit.bClose)
														} else {
															a.jgrid
																	.info_dialog(
																			a.jgrid.errors.errcap,
																			G.message,
																			a.jgrid.edit.bClose)
														}
													}
													break
												}
												f = a.jgrid.checkValues(E[c],
														o, C);
												if (f[0] === false) {
													f[1] = E[c] + " " + f[1];
													return false
												}
												if (C.p.autoencode) {
													E[c] = a.jgrid
															.htmlEncode(E[c])
												}
												if (z.url !== "clientArray"
														&& r.editoptions
														&& r.editoptions.NullIfEmpty === true) {
													if (E[c] === "") {
														x[c] = "null"
													}
												}
											}
										});
						if (f[0] === false) {
							try {
								var p = a.jgrid.findPos(a(
										"#" + a.jgrid.jqID(l), C.grid.bDiv)[0]);
								a.jgrid.info_dialog(a.jgrid.errors.errcap,
										f[1], a.jgrid.edit.bClose, {
											left : p[0],
											top : p[1]
										})
							} catch (D) {
								alert(f[1])
							}
							return m
						}
						var v, s, u;
						s = C.p.prmNames;
						u = s.oper;
						v = s.id;
						if (E) {
							E[u] = s.editoper;
							E[v] = l;
							if (typeof (C.p.inlineData) == "undefined") {
								C.p.inlineData = {}
							}
							E = a.extend({}, E, C.p.inlineData, z.extraparam)
						}
						if (z.url == "clientArray") {
							E = a.extend({}, E, y);
							if (C.p.autoencode) {
								a.each(E, function(k, e) {
									E[k] = a.jgrid.htmlDecode(e)
								})
							}
							var w = a(C).jqGrid("setRowData", l, E);
							a(t).attr("editable", "0");
							for ( var B = 0; B < C.p.savedRow.length; B++) {
								if (C.p.savedRow[B].id == l) {
									j = B;
									break
								}
							}
							if (j >= 0) {
								C.p.savedRow.splice(j, 1)
							}
							a(C).triggerHandler("jqGridInlineAfterSaveRow",
									[ l, w, E, z ]);
							if (a.isFunction(z.aftersavefunc)) {
								z.aftersavefunc.call(C, l, w,E)
							}
							m = true;
							a(t).unbind("keydown")
						} else {
							a("#lui_" + a.jgrid.jqID(C.p.id)).show();
							x = a.extend({}, E, x);
							x[v] = a.jgrid.stripPref(C.p.idPrefix, x[v]);
							a
									.ajax(a
											.extend(
													{
														url : z.url,
														data : a
																.isFunction(C.p.serializeRowData) ? C.p.serializeRowData
																.call(C, x)
																: x,
														type : z.mtype,
														async : false,
														complete : function(F,
																G) {
															a(
																	"#lui_"
																			+ a.jgrid
																					.jqID(C.p.id))
																	.hide();
															if (G === "success") {
																var o = true, H;
																H = a(C)
																		.triggerHandler(
																				"jqGridInlineSuccessSaveRow",
																				[
																						F,
																						l,
																						z ]);
																if (!a
																		.isArray(H)) {
																	H = [ true,
																			E ]
																}
																if (H[0]
																		&& a
																				.isFunction(z.successfunc)) {
																	H = z.successfunc
																			.call(
																					C,
																					F)
																}
																if (a
																		.isArray(H)) {
																	o = H[0];
																	E = H[1] ? H[1]
																			: E
																} else {
																	o = H
																}
																if (o === true) {
																	if (C.p.autoencode) {
																		a
																				.each(
																						E,
																						function(
																								I,
																								k) {
																							E[I] = a.jgrid
																									.htmlDecode(k)
																						})
																	}
																	E = a
																			.extend(
																					{},
																					E,
																					y);
																	a(C)
																			.jqGrid(
																					"setRowData",
																					l,
																					E);
																	a(t)
																			.attr(
																					"editable",
																					"0");
																	for ( var e = 0; e < C.p.savedRow.length; e++) {
																		if (C.p.savedRow[e].id == l) {
																			j = e;
																			break
																		}
																	}
																	if (j >= 0) {
																		C.p.savedRow
																				.splice(
																						j,
																						1)
																	}
																	a(C)
																			.triggerHandler(
																					"jqGridInlineAfterSaveRow",
																					[
																							l,
																							F,
																							E,
																							z ]);
																	if (a
																			.isFunction(z.aftersavefunc)) {
																		z.aftersavefunc
																				.call(
																						C,
																						l,
																						F)
																	}
																	m = true;
																	a(t)
																			.unbind(
																					"keydown")
																} else {
																	a(C)
																			.triggerHandler(
																					"jqGridInlineErrorSaveRow",
																					[
																							l,
																							F,
																							G,
																							null,
																							z ]);
																	if (a
																			.isFunction(z.errorfunc)) {
																		z.errorfunc
																				.call(
																						C,
																						l,
																						F,
																						G,
																						null)
																	}
																	if (z.restoreAfterError === true) {
																		a(C)
																				.jqGrid(
																						"restoreRow",
																						l,
																						z.afterrestorefunc)
																	}
																}
															}
														},
														error : function(k, o,
																F) {
															a(
																	"#lui_"
																			+ a.jgrid
																					.jqID(C.p.id))
																	.hide();
															a(C)
																	.triggerHandler(
																			"jqGridInlineErrorSaveRow",
																			[
																					l,
																					k,
																					o,
																					F,
																					z ]);
															if (a
																	.isFunction(z.errorfunc)) {
																z.errorfunc
																		.call(
																				C,
																				l,
																				k,
																				o,
																				F)
															} else {
																try {
																	a.jgrid
																			.info_dialog(
																					a.jgrid.errors.errcap,
																					'<div class="ui-state-error">'
																							+ k.responseText
																							+ "</div>",
																					a.jgrid.edit.bClose,
																					{
																						buttonalign : "right"
																					})
																} catch (G) {
																	alert(k.responseText)
																}
															}
															if (z.restoreAfterError === true) {
																a(C)
																		.jqGrid(
																				"restoreRow",
																				l,
																				z.afterrestorefunc)
															}
														}
													}, a.jgrid.ajaxOptions,
													C.p.ajaxRowOptions || {}))
						}
					}
					return m
				},
				restoreRow : function(d, b) {
					var c = a.makeArray(arguments).slice(1), e = {};
					if (a.type(c[0]) === "object") {
						e = c[0]
					} else {
						if (a.isFunction(b)) {
							e.afterrestorefunc = b
						}
					}
					e = a.extend(true, a.jgrid.inlineEdit, e);
					return this.each(function() {
						var l = this, f, h, j = {};
						if (!l.grid) {
							return
						}
						h = a(l).jqGrid("getInd", d, true);
						if (h === false) {
							return
						}
						for ( var g = 0; g < l.p.savedRow.length; g++) {
							if (l.p.savedRow[g].id == d) {
								f = g;
								break
							}
						}
						if (f >= 0) {
							if (a.isFunction(a.fn.datepicker)) {
								try {
									a("input.hasDatepicker",
											"#" + a.jgrid.jqID(h.id))
											.datepicker("hide")
								} catch (i) {
								}
							}
							a.each(l.p.colModel, function() {
								if (this.editable === true
										&& this.name in l.p.savedRow[f]) {
									j[this.name] = l.p.savedRow[f][this.name]
								}
							});
							a(l).jqGrid("setRowData", d, j);
							a(h).attr("editable", "0").unbind("keydown");
							l.p.savedRow.splice(f, 1);
							if (a("#" + a.jgrid.jqID(d),
									"#" + a.jgrid.jqID(l.p.id)).hasClass(
									"jqgrid-new-row")) {
								setTimeout(function() {
									a(l).jqGrid("delRowData", d)
								}, 0)
							}
						}
						a(l).triggerHandler("jqGridInlineAfterRestoreRow",
								[ d ]);
						if (a.isFunction(e.afterrestorefunc)) {
							e.afterrestorefunc.call(l, d)
						}
					})
				},
				addRow : function(b) {
					b = a.extend(true, {
						rowID : "new_row",
						initdata : {},
						position : "first",
						useDefValues : true,
						useFormatter : false,
						addRowParams : {
							extraparam : {}
						}
					}, b || {});
					return this
							.each(function() {
								if (!this.grid) {
									return
								}
								var e = this;
								if (b.useDefValues === true) {
									a(e.p.colModel)
											.each(
													function() {
														if (this.editoptions
																&& this.editoptions.defaultValue) {
															var g = this.editoptions.defaultValue, f = a
																	.isFunction(g) ? g
																	.call(e)
																	: g;
															b.initdata[this.name] = f
														}
													})
								}
								a(e).jqGrid("addRowData", b.rowID, b.initdata,
										b.position);
								b.rowID = e.p.idPrefix + b.rowID;
								a("#" + a.jgrid.jqID(b.rowID),
										"#" + a.jgrid.jqID(e.p.id)).addClass(
										"jqgrid-new-row");
								if (b.useFormatter) {
									a(
											"#" + a.jgrid.jqID(b.rowID)
													+ " .ui-inline-edit",
											"#" + a.jgrid.jqID(e.p.id)).click()
								} else {
									var c = e.p.prmNames, d = c.oper;
									b.addRowParams.extraparam[d] = c.addoper;
									a(e).jqGrid("editRow", b.rowID,
											b.addRowParams);
									a(e).jqGrid("setSelection", b.rowID)
								}
							})
				},
				inlineNav : function(b, c) {
					c = a.extend({
						edit : true,
						editicon : "ui-icon-pencil",
						add : true,
						addicon : "ui-icon-plus",
						save : true,
						saveicon : "ui-icon-disk",
						cancel : true,
						cancelicon : "ui-icon-cancel",
						addParams : {
							useFormatter : false,
							rowID : "new_row"
						},
						editParams : {},
						restoreAfterSelect : true
					}, a.jgrid.nav, c || {});
					return this
							.each(function() {
								if (!this.grid) {
									return
								}
								var k = this, e, h = a.jgrid.jqID(k.p.id);
								k.p._inlinenav = true;
								if (c.addParams.useFormatter === true) {
									var d = k.p.colModel, g;
									for (g = 0; g < d.length; g++) {
										if (d[g].formatter
												&& d[g].formatter === "actions") {
											if (d[g].formatoptions) {
												var j = {
													keys : false,
													onEdit : null,
													onSuccess : null,
													afterSave : null,
													onError : null,
													afterRestore : null,
													extraparam : {},
													url : null
												}, f = a.extend(j,
														d[g].formatoptions);
												c.addParams.addRowParams = {
													keys : f.keys,
													oneditfunc : f.onEdit,
													successfunc : f.onSuccess,
													url : f.url,
													extraparam : f.extraparam,
													aftersavefunc : f.afterSavef,
													errorfunc : f.onError,
													afterrestorefunc : f.afterRestore
												}
											}
											break
										}
									}
								}
								if (c.add) {
									a(k)
											.jqGrid(
													"navButtonAdd",
													b,
													{
														caption : c.addtext,
														title : c.addtitle,
														buttonicon : c.addicon,
														id : k.p.id + "_iladd",
														onClickButton : function() {
															a(k)
																	.jqGrid(
																			"addRow",
																			c.addParams);
															if (!c.addParams.useFormatter) {
																a(
																		"#"
																				+ h
																				+ "_ilsave")
																		.removeClass(
																				"ui-state-disabled");
																a(
																		"#"
																				+ h
																				+ "_ilcancel")
																		.removeClass(
																				"ui-state-disabled");
																a(
																		"#"
																				+ h
																				+ "_iladd")
																		.addClass(
																				"ui-state-disabled");
																a(
																		"#"
																				+ h
																				+ "_iledit")
																		.addClass(
																				"ui-state-disabled")
															}
														}
													})
								}
								if (c.edit) {
									a(k)
											.jqGrid(
													"navButtonAdd",
													b,
													{
														caption : c.edittext,
														title : c.edittitle,
														buttonicon : c.editicon,
														id : k.p.id + "_iledit",
														onClickButton : function() {
															var i = a(k)
																	.jqGrid(
																			"getGridParam",
																			"selrow");
															if (i) {
																a(k)
																		.jqGrid(
																				"editRow",
																				i,
																				c.editParams);
																a(
																		"#"
																				+ h
																				+ "_ilsave")
																		.removeClass(
																				"ui-state-disabled");
																a(
																		"#"
																				+ h
																				+ "_ilcancel")
																		.removeClass(
																				"ui-state-disabled");
																a(
																		"#"
																				+ h
																				+ "_iladd")
																		.addClass(
																				"ui-state-disabled");
																a(
																		"#"
																				+ h
																				+ "_iledit")
																		.addClass(
																				"ui-state-disabled")
															} else {
																a.jgrid
																		.viewModal(
																				"#alertmod",
																				{
																					gbox : "#gbox_"
																							+ h,
																					jqm : true
																				});
																a("#jqg_alrt")
																		.focus()
															}
														}
													})
								}
								if (c.save) {
									a(k)
											.jqGrid(
													"navButtonAdd",
													b,
													{
														caption : c.savetext
																|| "",
														title : c.savetitle
																|| "Save row",
														buttonicon : c.saveicon,
														id : k.p.id + "_ilsave",
														onClickButton : function() {
															var l = k.p.savedRow[0].id;
															if (l) {
																var i = k.p.prmNames, m = i.oper;
																if (!c.editParams.extraparam) {
																	c.editParams.extraparam = {}
																}
																if (a(
																		"#"
																				+ a.jgrid
																						.jqID(l),
																		"#" + h)
																		.hasClass(
																				"jqgrid-new-row")) {
																	c.editParams.extraparam[m] = i.addoper
																} else {
																	c.editParams.extraparam[m] = i.editoper
																}
																if (a(k)
																		.jqGrid(
																				"saveRow",
																				l,
																				c.editParams)) {
																	a(k)
																			.jqGrid(
																					"showAddEditButtons")
																}
															} else {
																a.jgrid
																		.viewModal(
																				"#alertmod",
																				{
																					gbox : "#gbox_"
																							+ h,
																					jqm : true
																				});
																a("#jqg_alrt")
																		.focus()
															}
														}
													});
									a("#" + h + "_ilsave").addClass(
											"ui-state-disabled")
								}
								if (c.cancel) {
									a(k)
											.jqGrid(
													"navButtonAdd",
													b,
													{
														caption : c.canceltext
																|| "",
														title : c.canceltitle
																|| "Cancel row editing",
														buttonicon : c.cancelicon,
														id : k.p.id
																+ "_ilcancel",
														onClickButton : function() {
															var i = k.p.savedRow[0].id;
															if (i) {
																a(k)
																		.jqGrid(
																				"restoreRow",
																				i,
																				c.editParams);
																a(k)
																		.jqGrid(
																				"showAddEditButtons")
															} else {
																a.jgrid
																		.viewModal(
																				"#alertmod",
																				{
																					gbox : "#gbox_"
																							+ h,
																					jqm : true
																				});
																a("#jqg_alrt")
																		.focus()
															}
														}
													});
									a("#" + h + "_ilcancel").addClass(
											"ui-state-disabled")
								}
								if (c.restoreAfterSelect === true) {
									if (a.isFunction(k.p.beforeSelectRow)) {
										e = k.p.beforeSelectRow
									} else {
										e = false
									}
									k.p.beforeSelectRow = function(m, l) {
										var i = true;
										if (k.p.savedRow.length > 0
												&& k.p._inlinenav === true
												&& (m !== k.p.selrow && k.p.selrow !== null)) {
											if (k.p.selrow == c.addParams.rowID) {
												a(k).jqGrid("delRowData",
														k.p.selrow)
											} else {
												a(k).jqGrid("restoreRow",
														k.p.selrow,
														c.editParams)
											}
											a(k).jqGrid("showAddEditButtons")
										}
										if (e) {
											i = e.call(k, m, l)
										}
										return i
									}
								}
							})
				},
				showAddEditButtons : function() {
					return this.each(function() {
						if (!this.grid) {
							return
						}
						var b = a.jgrid.jqID(this.p.id);
						a("#" + b + "_ilsave").addClass("ui-state-disabled");
						a("#" + b + "_ilcancel").addClass("ui-state-disabled");
						a("#" + b + "_iladd").removeClass("ui-state-disabled");
						a("#" + b + "_iledit").removeClass("ui-state-disabled")
					})
				}
			})
})(jQuery);