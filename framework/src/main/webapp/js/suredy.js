(function($, window, document, undefined) {
	$.suredy = $.extend({}, $.suredy);

	// load content
	$.suredy.loadContent = function(uri, selector, params, callback) {
		if (!uri || uri == '') {
			alert('Invalid uri, please check the [data-uri] on element.');
			return;
		}

		selector = selector || 'div.suredy-content';

		var ele;

		if (selector instanceof jQuery)
			ele = selector;
		else if (typeof selector === 'string') {
			ele = $(selector);
		} else
			return;

		ele.html('<i class="icon-spinner icon-spin icon-2x suredy-icon-loading"></i>');

		if ($.isFunction(params)) {
			callback = params;
			params = undefined;
		}

		// 防止缓存
		var randomStr = '&_=' + ('' + Math.random()).replace(/0\./, '');
		if (uri.indexOf('?') === -1)
			randomStr = '?' + randomStr;
		var nbspIndex = uri.indexOf(' ');
		if (nbspIndex != -1) { // 存在选择其的情况
			uri = uri.substring(0, nbspIndex) + randomStr + uri.substring(nbspIndex);
		} else {
			uri += randomStr;
		}

		ele.load(uri, params, function(response, status, request) {
			if ('success' === status) {
				var html = $('<div>').append($.parseHTML(response));
				var meta = html.find('meta[property=type]');

				if (meta.length > 0 && meta.attr('value') === 'login') {
					window.location.href = $.suredy.contextPath + '/login';
					return false;
				}

				if (callback) {
					callback.call();
				}

				return true;
			} else {
				alert('Bad request! Please check it.');
				return false;
			}
		});
	};

	// format datetime
	$.suredy.formatDatetime = function(time, format) {
		if (!time)
			return '';

		format = format || 'y-M-d H:m';

		var o = {
			"M+" : time.getMonth() + 1,
			"d+" : time.getDate(),
			"h+" : time.getHours() % 12 == 0 ? 12 : time.getHours() % 12,
			"H+" : time.getHours(),
			"m+" : time.getMinutes(),
			"s+" : time.getSeconds(),
			"S+" : time.getMilliseconds()
		};

		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (time.getFullYear() + "").substr(4 - (RegExp.$1.length === 2 ? 2 : 4)));
		}

		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			}
		}

		return format;
	};

	// format money
	$.suredy.formatMoney = function(number, places, symbol, thousand, decimal) {
		number = number || 0;
		places = !isNaN(places = Math.abs(places)) ? places : 2;
		symbol = symbol !== undefined ? symbol : "￥";
		thousand = thousand || ",";
		decimal = decimal || ".";
		var negative = number < 0 ? "-" : "", i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "", j = (j = i.length) > 3 ? j % 3 : 0;
		return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
	};

	// load data
	$.suredy.loadData = function(uri, params) {
		params = params || {};
		var data = undefined;

		// 防止缓存
		var randomStr = '&_=' + ('' + Math.random()).replace(/0\./, '');
		if (uri.indexOf('?') === -1)
			randomStr = '?' + randomStr;
		var nbspIndex = uri.indexOf(' ');
		if (nbspIndex != -1) { // 存在选择其的情况
			uri = uri.substring(0, nbspIndex) + randomStr + uri.substring(nbspIndex);
		} else {
			uri += randomStr;
		}

		$.ajax({
			url : uri,
			data : params,
			dataType : 'json',
			async : false,
			success : function(response, textStatus, jqXHR) {
				data = response;
			}
		});

		return data;
	};

	// notify config
	if ($.notifyDefaults) {
		$.notifyDefaults({
			allow_dismiss : false,
			placement : {
				align : 'center'
			},
			delay : 3000,
			mouse_over : 'pause',
			animate : {
				enter : 'animated flipInX',
				exit : 'animated flipOutX'
			}
		});
	}
})(jQuery, window, document);
