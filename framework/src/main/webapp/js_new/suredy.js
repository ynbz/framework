;
(function(global, factory) {

	if (typeof define === 'function' && define.amd) {
		// 添加到amd中，并加载依赖
		define([ 'jquery', 'notify' ], function(_$) {
			return factory(global, _$);
		});
	} else if (!global.jQuery || typeof global.jQuery !== 'function') {
		throw new Error("Suredy requires a jQuery");
	} else {
		factory(global, global.jQuery);
	}

})(this, function(window, $, undefined) {

	var _container = 'div.suredy-content';
	var _isFn = $.isFunction;

	/**
	 * Suredy object
	 */
	var Suredy = {
		isSuredy : true
	};

	/**
	 * String preventCacheOfUrl(url)
	 * 
	 * @param String
	 *            url
	 */
	Suredy.preventCacheOfUrl = function(url) {
		if (!url)
			throw new Error('Invalid parameter String[url].');

		var randomStr = '&_=' + Suredy.randomNumberString();

		if (url.indexOf('?') === -1)
			randomStr = '?' + randomStr;

		var nbspIndex = url.indexOf(' ');

		if (nbspIndex != -1) {
			// has css selector
			url = url.substring(0, nbspIndex) + randomStr + url.substring(nbspIndex);
		} else {
			url += randomStr;
		}

		return url;
	};

	/**
	 * String randomNumberString()
	 */
	Suredy.randomNumberString = function() {
		return Math.random().toString().replace(/\D/, '');
	};

	/**
	 * String randomString(len)
	 */
	Suredy.randomString = function(len) {
		len = len || 32;
		var chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
		var maxPos = chars.length;
		var pwd = '';

		for (; len-- > 0;) {
			pwd += chars.charAt(Math.floor(Math.random() * maxPos));
		}

		return pwd;
	};

	/**
	 * void loadContent(url[,args])
	 * 
	 * @param String
	 *            url
	 * @param Object
	 *            args
	 * 
	 * args like:<br>
	 * {String url, String container, Object postData, Function callback}
	 */
	Suredy.loadContent = function(url, args) {
		if ($.type(url) === 'object') {
			args = url;
			url = undefined;
		}

		var p = $.extend({
			url : undefined,
			container : _container,
			postData : undefined,
			callback : undefined
		}, args, {
			url : url
		});

		if (typeof p.url !== 'string' || $.trim(p.url) === '')
			throw new Error('Error! Need a not empty String url.');

		var ele;

		if (p.container instanceof jQuery)
			ele = p.container;
		else if (typeof p.container === 'string' && $.trim(p.container) !== '') {
			ele = $(p.container);
		} else
			throw new Error('Error! Need a css selector or a jQuery object container.');

		Suredy.loading(ele.get(0));

		// 防止缓存
		p.url = Suredy.preventCacheOfUrl(p.url);

		ele.load(p.url, p.postData, function(response, status, request) {
			if ('success' === status) {
				var html = $('<div>').append($.parseHTML(response));
				var meta = html.find('meta[property=type]');

				if (meta.length > 0 && meta.attr('value') === 'login') {
					window.location.href = Suredy.ctxp + '/login';
					return false;
				}

				if (p.callback && _isFn(p.callback))
					p.callback.call();

				return true;
			} else {
				throw new Error('Bad request! Please check it. url:[' + p.url + ']');
				return false;
			}
		});
	};

	/**
	 * String formatMoney(number[,places][,symbol][,thousand][,decimal])
	 * 
	 * @param number
	 * @param places
	 * @param symbol
	 * @param thousand
	 * @param decimal
	 */
	Suredy.formatMoney = function(number, places, symbol, thousand, decimal) {
		number = number || 0;
		places = !isNaN(places = Math.abs(places)) ? places : 2;
		symbol = symbol !== undefined ? symbol : "￥";
		thousand = thousand || ",";
		decimal = decimal || ".";
		var negative = number < 0 ? "-" : "", i = parseInt(number = Math.abs(+number || 0).toFixed(places), 10) + "", j = (j = i.length) > 3 ? j % 3 : 0;
		return symbol + negative + (j ? i.substr(0, j) + thousand : "") + i.substr(j).replace(/(\d{3})(?=\d)/g, "$1" + thousand) + (places ? decimal + Math.abs(number - i).toFixed(places).slice(2) : "");
	};

	/**
	 * Object loadData(url[,params]);
	 * 
	 * @param url
	 * @param params
	 */
	Suredy.loadData = function(url, params) {
		params = params || {};
		var data = undefined;

		// 防止缓存
		url = Suredy.preventCacheOfUrl(url);

		$.ajax({
			url : url,
			data : params,
			dataType : 'json',
			async : false,
			success : function(response, textStatus, jqXHR) {
				data = response;
			}
		});

		return data;
	};

	Suredy.loading = function(dom) {
		if (!dom)
			return false;

		var $ele = $(dom);

		var height = $ele.innerHeight() / 3;
		var width = $ele.innerWidth() / 3;

		var font_size = parseInt(Math.min(height, width));

		font_size = font_size > 100 ? 100 : font_size;
		var id = Suredy.randomNumberString();

		var template = '<div id="' + id + '">\
							<div style="text-align: center; font-size: ' + font_size + 'px; color: #aaa;">\
								<i class="icon-refresh icon-spin"></i>\
							</div>\
						</div>';

		$ele.empty().html(template);
	};

	Suredy.fileNameFromPath = function(path) {
		if (typeof path != 'string' || !path)
			return null;

		var arr = path.split('\\');
		var fileName = arr[arr.length - 1];

		if (!fileName)
			return null;

		var ret = {
			name : '',
			suffix : ''
		};

		var index = fileName.lastIndexOf('.');

		if (index == -1)
			ret.name = fileName;
		else {
			ret.name = fileName.substring(0, index);
			ret.suffix = fileName.substring(index);
		}

		return ret;
	};

	Suredy.pswdChecker = function(selector) {
		var $pswd = $(selector);

		if (!$pswd.length)
			return false;

		$pswd = $pswd.eq(0);

		$pswd.popover({
			container : 'body',
			content : function() {
				var template = '<small class="text-danger">\
									密码长度至少&nbsp;<h3 style="display: inline-block; margin: 0px;">8</h3>&nbsp;位<br>\
									且至少包含一个数字、一个小写字母和一个大写字母\
								<small>';

				return template;
			},
			html : true,
			placement : 'auto right',
			title : '<div class="text-danger"><i class="icon-warning-sign"></i>&nbsp;密码格式错误</div>',
			trigger : 'focus hover',
		});

		var pswd = $pswd.val();

		if (pswd.length < 8) {
			$pswd.parents('.form-group').eq(0).addClass('has-error');
			$pswd.popover('show').focus();
			return false;
		}

		var num = /\d+/;
		var c = /[a-z]+/;
		var C = /[A-Z]+/;

		// must contains number
		if (pswd.match(num) == null) {
			$pswd.parents('.form-group').eq(0).addClass('has-error');
			$pswd.popover('show').focus();
			return false;
		}

		// must contains a-z
		if (pswd.match(c) == null) {
			$pswd.parents('.form-group').eq(0).addClass('has-error');
			$pswd.popover('show').focus();
			return false;
		}

		// must contains A-Z
		if (pswd.match(C) == null) {
			$pswd.parents('.form-group').eq(0).addClass('has-error');
			$pswd.popover('show').focus();
			return false;
		}

		$pswd.parents('.form-group').eq(0).removeClass('has-error');
		$pswd.popover('destroy');

		return true;
	};

	/**
	 * beautify the input checkbox
	 */
	Suredy.event_change_suredy_checkbox = 'change.suredy.checkbox';
	Suredy.beautifyCheckbox = function(dom) {
		if (!dom)
			return;

		var $checkbox = $(dom);

		// not exists
		if (!$checkbox.length)
			return;

		// beautify
		return $checkbox.each(function(i, input) {
			var $this = $(input);

			// hidden, continue
			// if ($this.is(':hidden'))
			// return true;

			var id = $this.attr('id');

			// no id
			if (!id)
				$this.attr('id', id = Suredy.randomNumberString());

			$this.wrap('<label class="suredy-checkbox" for="' + id + '"></label>').hide();

			var style = $checkbox.prop('checked') ? ' checked' : '';

			$this.parent().append('<i class="icon-suredy-checkbox' + style + '"></i>');

			// bind event
			$this.on(Suredy.event_change_suredy_checkbox, function(event) {
				var $me = $(this);

				var icon = $me.parent().find('> i.icon-suredy-checkbox').removeClass('checked');

				if ($me.prop('checked'))
					icon.addClass('checked');

				return true;
			});
		});
	};

	/**
	 * 返回层级数据，使用"."的方式。eg: data['k1.k2.k3']
	 */
	Suredy.data = function(data, key) {
		if (!data || !key || typeof key !== 'string')
			return undefined;

		var ret = data;

		$.each(key.split('.'), function(i, k) {
			if (!key) {
				ret = undefined;
				return false;
			}

			ret = ret[k];

			if (ret === undefined)
				return false;
		});

		return ret;
	};

	/**
	 * bootstrap notify defaults
	 */
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

	window.Suredy = Suredy;

	// 返回Suredy对象
	return Suredy;

});
