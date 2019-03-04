(function($, window, document, undefined) {
	$.suredy = $.extend({}, $.suredy);

	$.suredy.pswdChecker = function($pswd) {
		if (!($pswd instanceof jQuery) || $pswd.length <= 0)
			return false;

		var pswd = $pswd.val();

		if (pswd.length < 8) {
			$pswd.css('border', '2px solid #ff0000').attr('title', '密码格式错误，必须包含至少，必须包含至少一个数字、一个小写字母和一个大写字母');
			return false;
		}

		var num = /\d+/;
		var c = /[a-z]+/;
		var C = /[A-Z]+/;

		// must contains number
		if (pswd.match(num) == null) {
			$pswd.css('border', '2px solid #ff0000').attr('title', '密码格式错误，必须包含至少，必须包含至少一个数字、一个小写字母和一个大写字母');
			return false;
		}

		// must contains a-z
		if (pswd.match(c) == null) {
			$pswd.css('border', '2px solid #ff0000').attr('title', '密码格式错误，必须包含至少，必须包含至少一个数字、一个小写字母和一个大写字母');
			return false;
		}

		// must contains A-Z
		if (pswd.match(C) == null) {
			$pswd.css('border', '2px solid #ff0000').attr('title', '密码格式错误，必须包含至少，必须包含至少一个数字、一个小写字母和一个大写字母');
			return false;
		}

		$pswd.removeAttr('style');
		$pswd.removeAttr('title');

		return true;
	}
})(jQuery, window, document);
