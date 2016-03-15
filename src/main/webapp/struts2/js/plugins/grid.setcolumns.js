/**
 * jqGrid extension for manipulating columns properties
 * Piotr Roznicki roznicki@o2.pl
 * http://www.roznicki.prv.pl
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl-2.0.html
**/
(function(a){a.jgrid.extend({setColumns:function(b){b=a.extend({top:0,left:0,width:200,height:"auto",dataheight:"auto",modal:false,drag:true,beforeShowForm:null,afterShowForm:null,afterSubmitForm:null,closeOnEscape:true,ShrinkToFit:false,jqModal:false,saveicon:[true,"left","ui-icon-disk"],closeicon:[true,"left","ui-icon-close"],onClose:null,colnameview:true,closeAfterSubmit:true,updateAfterCheck:false,recreateForm:false},a.jgrid.col,b||{});return this.each(function(){var j=this;if(!j.grid){return}var k=typeof b.beforeShowForm==="function"?true:false;var d=typeof b.afterShowForm==="function"?true:false;var e=typeof b.afterSubmitForm==="function"?true:false;var c=j.p.id,h="ColTbl_"+c,f={themodal:"colmod"+c,modalhead:"colhd"+c,modalcontent:"colcnt"+c,scrollelm:h};if(b.recreateForm===true&&a("#"+f.themodal).html()!=null){a("#"+f.themodal).remove()}if(a("#"+f.themodal).html()!=null){if(k){b.beforeShowForm(a("#"+h))}a.jgrid.viewModal("#"+f.themodal,{gbox:"#gbox_"+c,jqm:b.jqModal,jqM:false,modal:b.modal});if(d){b.afterShowForm(a("#"+h))}}else{var l=isNaN(b.dataheight)?b.dataheight:b.dataheight+"px";var m="<div id='"+h+"' class='formdata' style='width:100%;overflow:auto;position:relative;height:"+l+";'>";m+="<table class='ColTable' cellspacing='1' cellpading='2' border='0'><tbody>";for(i=0;i<this.p.colNames.length;i++){if(!j.p.colModel[i].hidedlg){m+="<tr><td style='white-space: pre;'><input type='checkbox' style='margin-right:5px;' id='col_"+this.p.colModel[i].name+"' class='cbox' value='T' "+((this.p.colModel[i].hidden===false)?"checked":"")+"/><label for='col_"+this.p.colModel[i].name+"'>"+this.p.colNames[i]+((b.colnameview)?" ("+this.p.colModel[i].name+")":"")+"</label></td></tr>"}}m+="</tbody></table></div>";var g=!b.updateAfterCheck?"<a href='javascript:void(0)' id='dData' class='fm-button ui-state-default ui-corner-all'>"+b.bSubmit+"</a>":"",n="<a href='javascript:void(0)' id='eData' class='fm-button ui-state-default ui-corner-all'>"+b.bCancel+"</a>";m+="<table border='0' class='EditTable' id='"+h+"_2'><tbody><tr style='display:block;height:3px;'><td></td></tr><tr><td class='DataTD ui-widget-content'></td></tr><tr><td class='ColButton EditButton'>"+g+"&#160;"+n+"</td></tr></tbody></table>";b.gbox="#gbox_"+c;a.jgrid.createModal(f,m,b,"#gview_"+j.p.id,a("#gview_"+j.p.id)[0]);if(b.saveicon[0]==true){a("#dData","#"+h+"_2").addClass(b.saveicon[1]=="right"?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+b.saveicon[2]+"'></span>")}if(b.closeicon[0]==true){a("#eData","#"+h+"_2").addClass(b.closeicon[1]=="right"?"fm-button-icon-right":"fm-button-icon-left").append("<span class='ui-icon "+b.closeicon[2]+"'></span>")}if(!b.updateAfterCheck){a("#dData","#"+h+"_2").click(function(p){for(i=0;i<j.p.colModel.length;i++){if(!j.p.colModel[i].hidedlg){var o=j.p.colModel[i].name.replace(/\./g,"\\.");if(a("#col_"+o,"#"+h).attr("checked")){a(j).jqGrid("showCol",j.p.colModel[i].name);a("#col_"+o,"#"+h).attr("defaultChecked",true)}else{a(j).jqGrid("hideCol",j.p.colModel[i].name);a("#col_"+o,"#"+h).attr("defaultChecked","")}}}if(b.ShrinkToFit===true){a(j).jqGrid("setGridWidth",j.grid.width-0.001,true)}if(b.closeAfterSubmit){a.jgrid.hideModal("#"+f.themodal,{gb:"#gbox_"+c,jqm:b.jqModal,onClose:b.onClose})}if(e){b.afterSubmitForm(a("#"+h))}return false})}else{a(":input","#"+h).click(function(o){var p=this.id.substr(4);if(p){if(this.checked){a(j).jqGrid("showCol",p)}else{a(j).jqGrid("hideCol",p)}if(b.ShrinkToFit===true){a(j).jqGrid("setGridWidth",j.grid.width-0.001,true)}}return this})}a("#eData","#"+h+"_2").click(function(o){a.jgrid.hideModal("#"+f.themodal,{gb:"#gbox_"+c,jqm:b.jqModal,onClose:b.onClose});return false});a("#dData, #eData","#"+h+"_2").hover(function(){a(this).addClass("ui-state-hover")},function(){a(this).removeClass("ui-state-hover")});if(k){b.beforeShowForm(a("#"+h))}a.jgrid.viewModal("#"+f.themodal,{gbox:"#gbox_"+c,jqm:b.jqModal,jqM:true,modal:b.modal});if(d){b.afterShowForm(a("#"+h))}}})}})})(jQuery);