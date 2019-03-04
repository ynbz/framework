;
(function(global, factory) {

	if (typeof define === 'function' && define.amd) {
		// 添加到amd中，并加载依赖
		define([ 'jquery', 'suredy' ], function(jQuery, Suredy) {
			return factory(global, jQuery, Suredy);
		});
	} else if (!global.jQuery || !global.Suredy) {
		throw new Error("Suredy.List requires a jQuery and a Suredy");
	} else {
		factory(global, global.jQuery, global.Suredy);
	}

})(this, function(window, $, Suredy, undefined) {

	/* ****************************** 各种参数 ****************************** */
	var list_plugin_flag = 'suredy.list'; // plugin falg

	var _event_change_suredy_list_checkbox_ = 'change.suredy.list.checkbox';
	var _event_click_suredy_list_header_btn_ = 'click.suredy.list.header.button';

	// default options
	var LIST_DEFAULT = {
		header : true, // show button and search area
		footer : true, // show page info
		search : true, // show search box
		checkbox : false, // show checkbox
		title : false, // table title
		searchDefaultText : '输入查询关键字', // search box's show info
		searchValue : false, // default search value
		searchFieldName : 'search', // search field for request
		pageFieldName : 'page', // page field for request
		pageSizeFieldName : 'pageSize', // page size field for request
		paginate : function(page, pageSize, key, order) { // paginate list
			// user should override this function
		},
		btns : [], // default buttons
		fixTableStyle : true, // use bootstrap's table style
	};

	var ORDER_TEXT = {
		asc : '由小到大排序',
		desc : '由大到小排序',
		def : '点击排序',
	};

	/* ****************************** 各种工具方法 ****************************** */
	var getComponent = function($element) {
		if (!$element)
			return undefined;

		return $element.parents('div.suredy-list').eq(0).data(list_plugin_flag);
	};

	/* ****************************** 各种事件 ****************************** */
	var EVENT_CHECK_ALL_BOX_CHANGE = function(event) {
		var $this = $(this);

		// not check-all checkbox
		if (!$this.hasClass('check-all'))
			return true;

		var checked = $this.prop('checked');
		var component = getComponent($this);

		var checkAllBox = component.checkAllBox();

		var exists;

		if (checked)
			exists = checkAllBox.not(':checked');
		else
			exists = checkAllBox.filter(':checked');

		// more than one check-all checkbox has a different status
		// throw the event to another one
		if (exists.length) {
			exists.eq(0).prop('checked', checked).trigger('change');
			return true;
		}

		var dataCheckbox = component.dataCheckbox();

		if (checked)
			exists = dataCheckbox.not(':checked');
		else
			exists = dataCheckbox.filter(':checked');

		// update data checkbox
		exists.prop('checked', checked).trigger('change');

		return true;
	};

	var EVENT_DATA_CHECKBOX_CHANGE = function(event) {
		var $this = $(this);

		// not data checkbox
		if (!$this.hasClass('check-data'))
			return true;

		var checked = $this.prop('checked');
		var component = getComponent($this);

		var dataCheckbox = component.dataCheckbox();

		var exists;

		if (checked)
			exists = dataCheckbox.not(':checked');
		else
			exists = dataCheckbox.filter(':checked');

		var checkAllBox = component.checkAllBox();

		// update check-all checkbox status to unchecked
		if (exists.length) {
			checkAllBox.prop('checked', false);
			checkAllBox.parent().find('i.icon-suredy-checkbox').removeClass('checked');
			return true;
		}

		if (checked)
			exists = checkAllBox.not(':checked');
		else
			exists = checkAllBox.filter(':checked');

		// update check-all checkbox status
		exists.eq(0).prop('checked', checked).trigger('change');

		return true;
	};

	var EVENT_SEARCH_BOX_DO_SEARCH = function(event) {
		var $this = $(this);

		var component = getComponent($this);

		component.doPaginate(1);

		return true;
	};

	var EVENT_PAGE_BUTTON_CLICK = function(event) {
		var $this = $(this);

		if ($this.hasClass('active'))
			return true;

		var page = $this.data('page') || 1;
		var component = getComponent($this);

		component.doPaginate(page);

		return true;
	};

	var EVENT_PAGE_GROUP_BUTTON_CLICK = function(event) {
		var $this = $(this);

		// can not change page group
		if ($this.hasClass('disabled'))
			return true;

		var pagination = $this.parent('ul');

		var start = pagination.data('start') || 1;

		if ($this.hasClass('pre'))
			start -= 10;
		else if ($this.hasClass('next'))
			start += 10;

		pagination.data('start', start);

		getComponent($this).refreshPagination();

		return true;
	};

	var ORDER_CLICK_TIMEOUT = undefined;
	var EVENT_ORDER_BUTTON_CLICK = function(event) {
		var $this = $(this);
		var oldOrder = $this.attr('order');

		var component = getComponent($this);

		// remove all order
		component.orderCell().removeAttr('order').find('> div.icon-suredy-list-order').attr('title', ORDER_TEXT['def']);

		// change this order status
		if (oldOrder == 'asc') {
			$this.attr('order', 'desc');
			$this.find('> div.icon-suredy-list-order').attr('title', ORDER_TEXT['desc']);
		} else if (oldOrder == 'desc') {
			$this.removeAttr('order');
			$this.find('> div.icon-suredy-list-order').attr('title', ORDER_TEXT['def']);
		} else {
			$this.attr('order', 'asc');
			$this.find('> div.icon-suredy-list-order').attr('title', ORDER_TEXT['asc']);
		}

		if (ORDER_CLICK_TIMEOUT)
			clearTimeout(ORDER_CLICK_TIMEOUT);

		ORDER_CLICK_TIMEOUT = setTimeout(function() {
			component.doPaginate(1);
		}, 800);

		return true;
	};

	/* ****************************** 各种绘制组件 ****************************** */
	var _draw_header_button = function() {
		var btns = this.options.btns || [];

		if (!$.isArray(btns))
			btns = [ btns ];

		if ($.isEmptyObject(btns))
			return;

		var header = this.container.find('> div.list-header');

		// draw single button
		var singleBtn = function(data) {
			if (!data || $.isEmptyObject(data))
				return;

			var id = 'btn' + Suredy.randomNumberString();

			header.append('<div class="btn list-header-btn" id="' + id + '"></div>');

			var btn = header.find('div.btn#' + id);

			// add style
			btn.addClass(data.style || 'btn-default');

			// add icon
			btn.append(data.icon ? '<i class="' + data.icon + '"></i>' : '');

			// add text
			btn.append(data.text || '自定义按钮');

			// bind click event
			if ($.isFunction(data.click))
				btn.on(_event_click_suredy_list_header_btn_, data.click);
		};

		// draw group button
		var groupBtn = function(data) {
			if (!data || $.isEmptyObject(data))
				return;

			var id = 'btn' + Suredy.randomNumberString();

			header.append('<div class="btn-group list-header-btn"><div class="btn dropdown-toggle" data-toggle="dropdown" id="' + id + '"></div><ul class="dropdown-menu" role="menu"></ul></div>');

			var gBtn = header.find('> div.btn-group > div.btn.dropdown-toggle#' + id);

			// add style
			gBtn.addClass(data.style || 'btn-default');

			// add icon
			gBtn.append(data.icon ? '<i class="' + data.icon + '"></i>' : '');

			// add text
			gBtn.append(data.text || '自定义按钮');

			// add caret
			gBtn.append('<i class="icon-caret-down"></i>');

			// bind click event
			if ($.isFunction(data.click))
				gBtn.on(_event_click_suredy_list_header_btn_, data.click);

			var cContainer = gBtn.parent().find('> ul.dropdown-menu');

			$.each(data.children, function(i, cData) {
				// is a divider
				if (this.split) {
					cContainer.append('<li class="divider"></li>');
					return true;
				}

				var cid = 'btn' + Suredy.randomNumberString();

				cContainer.append('<li><a href="javascript:;" id="' + cid + '"></a></li>');

				var cBtn = cContainer.find('> li > a#' + cid);

				// add icon
				cBtn.append(this.icon ? '<i class="' + cData.icon + '"></i>' : '');

				// add text
				cBtn.append(cData.text || '自定义按钮');

				// bind click event
				if ($.isFunction(cData.click))
					cBtn.on(_event_click_suredy_list_header_btn_, cData.click);
			});
		};

		$.each(btns, function(i, btn) {
			if (!btn) // continue
				return true;

			if ($.isEmptyObject(btn.children))
				singleBtn(btn);
			else
				groupBtn(btn);
		});
	};

	var _draw_header_searchbox = function() {
		if (!(!!this.options.search))
			return;

		var header = this.container.find('> div.list-header');

		// add search box
		header.append('<div class="input-group list-search-box"><input type="text" class="form-control input-sm"><div class="input-group-addon btn search-btn"><i class="icon-search"></i></div></div>');

		var input = header.find('> div.list-search-box > input[type="text"]');
		var button = header.find('> div.list-search-box > div.search-btn');

		// add search field name
		input.attr('name', this.options.searchFieldName || '');

		// add search default text
		input.attr('placeholder', this.options.searchDefaultText || '');

		// add search value
		input.val(this.options.searchValue || '');

		// bind search box keypress event
		input.on('keypress', function(event) {
			if (event.keyCode !== 13)
				return true;

			EVENT_SEARCH_BOX_DO_SEARCH.call(this, event);
		});

		// bind search button click event
		button.on('click', EVENT_SEARCH_BOX_DO_SEARCH);
	};

	var _draw_footer_pagination = function() {
		if (!$.isNumeric(this.pages()) || this.pages() <= 0)
			return;

		var footer = this.container.find('> div.list-footer');

		if (!footer.length)
			return;

		// add pagination
		footer.append('<ul class="pagination pagination-sm list-pagination"></ul>');

		var ulPage = footer.find('> ul.list-pagination');

		ulPage.data('start', Math.floor((this.page() - 1) / 10) * 10 + 1);

		this.refreshPagination();
	};

	var _draw_footer_pageinfo = function() {
		var footer = this.container.find('> div.list-footer');

		if (!footer.length)
			return;

		// add page info div
		footer.append('<div class="list-pageinfo"></div>');

		var info = footer.find('> div.list-pageinfo');

		// add info
		var text = '共' + this.pages() + '页（' + this.pageSize() + '条/页 - 共' + this.count() + '条）';
		info.text(text);
		info.attr('title', text);
	};

	var _draw_checkbox_in_table_thead_ = function() {
		var head = this.list.find('> thead');

		if (!head.length)
			return;

		var $tr = head.find('> tr');

		var $row = $tr.eq(0);
		var rowspan = $tr.length;
		var tagName = $row.find('> th:first-child,td:first-child').get(0).tagName;

		$row.prepend('<' + tagName + ' class="text-center list-checkbox-cell" width="30" rowspan="' + rowspan + '"></' + tagName + '>');
		var checkbox = $row.find('> ' + tagName + '.list-checkbox-cell').append('<input class="check-all" type="checkbox" />').find('> input.check-all');

		Suredy.beautifyCheckbox(checkbox);
	};

	var _draw_checkbox_in_table_tfoot_ = function() {
		var foot = this.list.find('> tfoot');

		if (!foot.length)
			return;

		var $tr = foot.find('> tr');

		var tagName = $tr.eq(0).find('> th:first-child,td:first-child').get(0).tagName;

		$tr.prepend('<' + tagName + ' class="text-center" width="30"></' + tagName + '>');
	};

	var _draw_title = function() {
		var title = this.options.title;

		if (!(!!title))
			return;

		this.container.prepend('<div class="list-title text-center">' + title + '</div>');
	};

	var _draw_header = function() {
		if (!(!!this.options.header))
			return;

		// append a header div
		this.container.prepend('<div class="list-header"></div>');

		// draw buttons in header
		_draw_header_button.call(this);

		// draw search box
		_draw_header_searchbox.call(this);
	};

	var _draw_footer = function() {
		if (!(!!this.options.footer))
			return;

		// append a footer div
		this.container.append('<div class="list-footer"></div>');

		// draw paginal
		_draw_footer_pagination.call(this);

		// draw page info
		_draw_footer_pageinfo.call(this);
	};

	var _draw_checkbox = function() {
		if (!this.options.checkbox)
			return;

		// checkbox for title row
		_draw_checkbox_in_table_thead_.call(this);

		// checkbox for data row
		this.list.find('> tbody > tr').each(function(i, dom) {
			var $tr = $(dom);
			var tagName = $tr.find('> th:first-child,td:first-child').get(0).tagName;

			$tr.prepend('<' + tagName + ' class="text-center list-checkbox-cell" width="30"></' + tagName + '>');

			if (!$tr.hasClass('no-checkbox')) {
				var boxStyle = 'check-data';

				if ($tr.hasClass('title-row'))
					boxStyle = 'check-all';

				var checkbox = $tr.find('> ' + tagName + '.list-checkbox-cell').append('<input class="' + boxStyle + '" type="checkbox" />').find('> input[type="checkbox"]');

				// only change data checkbox status
				if (boxStyle == 'check-data' && $tr.hasClass('checked'))
					checkbox.prop('checked', true);

				Suredy.beautifyCheckbox(checkbox);
			}
		});

		// fix tfoot
		_draw_checkbox_in_table_tfoot_.call(this);

		// bind check-all checkbox change event
		this.checkAllBox().on(_event_change_suredy_list_checkbox_, EVENT_CHECK_ALL_BOX_CHANGE);

		// bind data checkbox change event
		this.dataCheckbox().on(_event_change_suredy_list_checkbox_, EVENT_DATA_CHECKBOX_CHANGE);

		// trigger data checkbox change event
		this.dataCheckbox().trigger('change');
	};

	var _draw_order = function() {
		var field = this.list.find('td[field],th[field]');

		field.append('<div class="icon-suredy-list-order"></div>');

		field.each(function(i, dom) {
			var td = $(dom);
			var order = td.find('> div.icon-suredy-list-order');

			if (td.attr('order') == 'asc')
				order.attr('title', ORDER_TEXT['asc']);
			else if (td.attr('order') == 'desc')
				order.attr('title', ORDER_TEXT['desc']);
			else
				order.attr('title', ORDER_TEXT['def']);

			order.parent().on('click', EVENT_ORDER_BUTTON_CLICK);
		});
	};

	var _draw_plugin = function() {
		// wrap with suredy-list div
		this.list.before('<div class="suredy-list"><div class="list-table"></div></div>');

		this.list.appendTo(this.list.prev().find('div.list-table'));
		// this.list.wrap('<div class="suredy-list"></div>').wrap('<div class="list-table"></div>');

		this.list.parents('div.suredy-list').eq(0).data(list_plugin_flag, this);

		// add style
		if (!!this.options.fixTableStyle) {
			this.list.addClass('table table-bordered table-striped table-hover table-condensed');
		}

		this.container = this.list.parents('div.suredy-list').eq(0);

		// draw head
		_draw_header.call(this);

		// draw title
		_draw_title.call(this);

		// draw footer
		_draw_footer.call(this);

		// draw checkbox
		_draw_checkbox.call(this);

		// draw order btn
		_draw_order.call(this);
	};

	/* ****************************** 插件定义 ****************************** */
	var List = function(table, options) {
		this.list = $(table);

		this.options = $.extend({}, LIST_DEFAULT, options);

		// draw list plugin
		_draw_plugin.call(this);

		return this;
	};

	List.prototype = {
		constructor : List,
		page : function(page) {
			// reset current page
			if (page) {
				page = $.isNumeric(page) ? Math.floor(page) : 1;

				if (page < 1)
					page = 1;

				this.list.data('page', page);
			}

			// get current page
			var page = this.list.data('page') || 1; // current page

			return page;
		},
		count : function() {
			var count = this.list.data('count') || 0; // row count
			return count;
		},
		pageSize : function() {
			var pageSize = this.list.data('page-size') || 30; // page size
			return pageSize;
		},
		pages : function() {
			var pages = Math.ceil(this.count() / this.pageSize()); // page count
			return pages;
		},
		checkAllBox : function(checked) {
			return this.list.find('.list-checkbox-cell > label.suredy-checkbox > input.check-all');
		},
		dataCheckbox : function() {
			return this.list.find('.list-checkbox-cell > label.suredy-checkbox > input.check-data');
		},
		searchBox : function() {
			return this.container.find('> div.list-header > div.list-search-box > input[type="text"]');
		},
		orderCell : function() {
			return this.list.find('> thead, > tbody').find('> tr > td[field], > tr > th[field]');
		},
		doPaginate : function(page) {
			var func = this.options.paginate;

			if (!func)
				return;

			// reset current page
			this.page(page);

			var searchText = this.searchBox().val() || '';
			var orderData = {};

			this.orderCell().filter('[order]').each(function(i, dom) {
				var $td = $(dom);

				var field = $td.attr('field');
				var order = $td.attr('order');

				if (!field || !order)
					return true;

				orderData[field] = order;
			});

			func.call(this, page, this.pageSize(), searchText, orderData);

			this.container.find('> div.list-footer > ul.list-pagination').data('start', Math.floor((page - 1) / 10) * 10 + 1);

			this.refreshPagination();
		},
		refreshPagination : function() {
			var footer = this.container.find('> div.list-footer');
			var pagination = footer.find('> ul.list-pagination');

			// destroy old pagination
			pagination.find('> li').remove();

			var start = pagination.data('start') || 1;

			// add pre button
			pagination.append('<li class="change-page-group pre' + (start == 1 ? ' disabled' : '') + '"><a class="icon-arrow-left" href="javascript:;"></a></li>');

			// add page btns
			var i = 0;
			for (; i < 10 && start <= this.pages(); i++, start++) {
				pagination.append('<li class="page-btn' + (start === this.page() ? ' active' : '') + '" data-page="' + start + '"><a href="javascript:;">' + start + '</a></li>');
			}

			// add next button
			pagination.append('<li class="change-page-group next' + (start > this.pages() ? ' disabled' : '') + '"><a class="icon-arrow-right" href="javascript:;"></a></li>');

			// bind page button click
			pagination.find('> li.page-btn').on('click', EVENT_PAGE_BUTTON_CLICK);

			// bind change-page-group button click
			pagination.find('> li.change-page-group').on('click', EVENT_PAGE_GROUP_BUTTON_CLICK);
		},
	};

	var _plugin = function(options) {
		return this.each(function(i, dom) {
			var $this = $(dom);
			var component = getComponent($this);

			if (!component)
				component = new List(dom, options);
		});
	};

	Suredy.List = function(selector, options) {
		return _plugin.call($(selector), options);
	};

	$.extend(Suredy.List, {
		checked : function($list) {
			var component = getComponent($list);

			if (!component)
				return undefined;

			return component.dataCheckbox().filter(':checked').parents('.list-checkbox-cell').parent();
		}
	});

	// 对象属性
	Suredy.List.prototype = {
		constructor : Suredy.List,
	};

	Suredy.List.sortData = function(list) {
		var $list = $(list);

		if (!$list.length)
			return null;

		var $sort = $list.find('> thead, > tbody').find('> tr > td[field][order], > tr > th[field][order]');
		var sort = {};

		$sort.each(function(i, dom) {
			var $td = $(dom);

			var field = $td.attr('field');
			var order = $td.attr('order');

			if (!field || !order)
				return true;

			sort[field] = order;

			return false;
		});

		return sort;
	};

	// 返回Suredy对象
	return Suredy.List;
});