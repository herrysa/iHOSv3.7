/**
 * @requires jquery.validate.js
 * @author ZhangHuihua@msn.com
 */
(function($){
	if ($.validator) {
		$.validator.addMethod("alphanumeric", function(value, element) {
			return this.optional(element) || /^\w+$/i.test(value);
		}, "Letters, numbers or underscores only please");
		
		$.validator.addMethod("lettersonly", function(value, element) {
			return this.optional(element) || /^[a-z]+$/i.test(value);
		}, "Letters only please"); 
		
		$.validator.addMethod("phone", function(value, element) {
//			return this.optional(element) || /^[0-9 \(\)]{7,30}$/.test(value);
			return this.optional(element) || /^1[34578][0-9]{9}$/.test(value);
		}, "Please specify a valid phone number");
		
		$.validator.addMethod("postcode", function(value, element) {
//			return this.optional(element) || /^[0-9 A-Za-z]{5,20}$/.test(value);
			return this.optional(element) ||  /^[0-9]{6}$/.test(value);
		}, "Please specify a valid postcode");
		
		/*身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  */
		$.validator.addMethod("idcard", function(value, element) {
			return this.optional(element) || /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
		}, "Please specify a valid idcard");
		
		/*固话： 区号+电话号码 的数字，如0901-2100222或010-11111111*/
		$.validator.addMethod("telephone", function(value, element) {
			return this.optional(element) || /(^\d{3}-\d{8}$)|(^\d{4}-\d{7}$)/.test(value);
		}, "Please specify a valid telephone");
		
		$.validator.addMethod("date", function(value, element) {
			value = value.replace(/\s+/g, "");
			if (String.prototype.parseDate){
				var $input = $(element);
				var pattern = $input.attr('format') || 'yyyy-MM-dd';
	
				return !$input.val() || $input.val().parseDate(pattern);
			} else {
				return this.optional(element) || value.match(/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/);
			}
		}, "Please enter a valid date.");
		
		$.validator.addMethod("notrepeat", function(value, element , param) {
			var flag = false;
			var oldValue = $(element).attr("oldValue");
			if(oldValue==value){
				return true;
			}
			var url = 'hasResult';
			url = encodeURI(url);
			var validatorParam = $(element).attr("validatorParam");
			var validatorType = $(element).attr("validatorType");
			var entityName , searchItem , returnMessage;
			if(validatorParam){
				validatorParam = validatorParam.replaceAll("%value%","'"+value+"'");
				//validatorParam = eval("("+validatorParam+")");
				//entityName = validatorParam.entityName;
				//searchItem = validatorParam.searchItem;
			}else{
				return ;
			}
			$.ajax({
			    url: url,
			    type: 'post',
			    data:{hql:validatorParam,validatorType:validatorType},
			    dataType: 'json',
			    async:false,
			    error: function(data){
			    },
			    success: function(data){
			        if(data&&data.message=='1'){
			        	flag = false;
			        }else{
			        	flag = true;
			        }
			    }
			});
			return flag;
		}, "This field must not repeated.");
		
		$.validator.addClassRules({
			date: {date: true},
			alphanumeric: { alphanumeric: true },
			lettersonly: { lettersonly: true },
			phone: { phone: true },
			postcode: {postcode: true},
			idcard: {idcard: true},
			telephone: {telephone: true}
		});
		$.validator.setDefaults({errorElement:"span"});
		$.validator.autoCreateRanges = true;
		
	}

})(jQuery);