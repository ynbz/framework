/**
 * This is list plugin
 * 
 * @author VIVID.G
 * @since 2015-6-30
 * @version v0.1
 */

// var btns = [ {
// text : 'button1',
// icon : 'icon-user',
// style : 'btn-default',
// click : function(page, pageSize, key) {
// }
// }, {
// text : '按钮组2',
// icon : 'icon-key',
// style : 'btn-primary',
// click : function(page, pageSize, key) {
// },
// children : [ {
// text : '子按钮1',
// icon : 'icon-user',
// click : function(page, pageSize, key) {
// }
// }, {
// split : true
// }, {
// text : '子按钮2',
// // icon : 'icon-key',
// click : function(page, pageSize, key) {
// }
// } ]
// } ];
(function($, window, document, undefined) {
	$.suredy = $.extend({}, $.suredy);

	var SuredyList = function($list, options) {
		var me = this;
		this.list = $list;
		this.options = $.extend({
			header : true,
			footer : true,
			search : true,
			checkbox : false,
			title : false,
			searchDefaultText : '输入查询关键字',
			searchValue : false,
			searchFieldName : 'search',
			pageFieldName : 'page',
			pageSizeFieldName : 'pageSize',
			paginate : function(page, pageSize, key) {
			},
			btns : []
		}, options);
		this.page = function() {
			var page = me.list.data('page');

			return page ? Number(page) : 1;
		}();
		this.count = function() {
			var count = me.list.data('count');

			return count ? Number(count) : 0;
		}();
		this.pageSize = function() {
			var pageSize = me.list.data('page-size');

			return pageSize ? Number(pageSize) : 25;
		}();
		this.pages = function() {
			return Math.ceil(me.count / me.pageSize);
		}();
		this.clickFunction = {};
	};

	SuredyList.prototype = {
		make : function() {
			var classes = this.list.attr('class');
			classes = classes.replace(/suredy\-list/g, '');

			// remove all classes
			this.list.removeClass();

			// add classes
			this.list.addClass('suredy-list');

			// wrap all column
			$('>tbody', this.list).children().wrapAll('<tr><td class="list-content"><table></table></td></tr>');

			// add classes
			$('table', this.list).first().addClass(classes + ' table table-bordered table-striped table-hover margin-0 padding-0');

			// make head
			this.makeHead();

			// make footer
			this.makeFooter();

			// make checkbox
			this.makeCheckbox();

			var pagination = $('ul.pagination li.page-btn', this.list);

			// previous btn style
			if (pagination.first().data('page') === 1)
				$('ul.pagination li.previous', this.list).addClass('disabled');

			// next btn style
			if (pagination.last().data('page') === this.pages)
				$('ul.pagination li.next', this.list).addClass('disabled');

			// marke content row
			$('.list-content>table>tbody>tr', this.list).not('.title-row').not('.not-content-row').addClass('content-row');
		},
		makeHead : function() {
			if (!this.options.header)
				return;

			var header = '<tr><td height="51"><div class="row"><div class="col-md-9 col-sm-9 col-xs-12">{title}{btns}</div><div class="col-md-3 col-sm-3 col-xs-12 text-right">{search}</div></div></td></tr>';

			// title
			header = header.replace(/\{title\}/g, this.options.title ? '<div class="btn btn-link"><h4 style="display: inline; font-weight: bold;">' + this.options.title + '</h4></div>' : '');

			// btns
			var btns = this.makeButtons();
			header = header.replace(/\{btns\}/g, btns || '');

			// search
			var search = this.makeSearch();
			header = header.replace(/\{search\}/g, search || '');

			$('>tbody', this.list).prepend(header);
		},
		makeFooter : function() {
			if (!this.options.footer)
				return;

			var footer = '<tr><td height="51"><div class="row"><div class="col-md-9 col-sm-9 col-xs-12">{paginal}</div><div class="col-md-3 col-sm-3 col-xs-12 text-right">{pageInfo}</div></div></td></tr>';

			// paginal
			var paginal = this.makePaginal();
			footer = footer.replace(/\{paginal\}/g, paginal || '');

			// pageInfo
			var pageInfo = this.makePageInfo();
			footer = footer.replace(/\{pageInfo\}/g, pageInfo || '');

			$('>tbody', this.list).append(footer);
		},
		makeCheckbox : function() {
			var trs = $('.list-content>table>tbody>tr', this.list);

			if (!this.options.checkbox) {
				trs.removeClass('checked');
				return;
			}

			trs.each(function(i) {
				var tr = $(this);
				var tds = tr.children();

				if (tds.length <= 0)
					return false;

				var checkbox = '<{tagName} class="text-center" width="30"><div><i class="icon-check-empty row-checkbox"></i><div></{tagName}>';

				if (tr.hasClass('no-checkbox')) {
					checkbox = '<{tagName} class="text-center" width="30"></{tagName}>';
				}

				var tagName = tds.get(0).tagName;

				// tagName
				checkbox = checkbox.replace(/\{tagName\}/g, tagName);

				tr.prepend(checkbox);
			});

			// title row no need class: checked
			var titleRow = $('.list-content>table>tbody>tr.title-row', this.list);
			titleRow.removeClass('checked');

			// relation checked
			if (trs.not('.title-row').length === $('.list-content>table>tbody>tr.checked', this.list).length) {
				$('i.row-checkbox', titleRow).removeClass('icon-check-empty').addClass('icon-check');
			} else {
				$('i.row-checkbo', titleRow).removeClass('icon-check').addClass('icon-check-empty');
			}
		},
		makeButtons : function() {
			var btns = this.options.btns || [];

			if (!$.isArray(btns))
				btns = [ btns ];

			if (btns.length <= 0)
				return '';

			var me = this;

			var html = '';

			$(btns).each(function(i) {
				var data = this;

				if (!data)
					return true;

				if (data.children)
					html += me.makeButtonGroup(data);
				else
					html += me.makeButton(data);
			});

			return html;
		},
		makeSearch : function() {
			if (!this.options.search)
				return '';

			var html = '<div class="input-group"><input type="text" class="form-control" name="{searchFieldName}" placeholder="{searchDefaultText}"{searchValue}><div class="input-group-addon btn search-btn"><i class="icon-search"></i></div></div>';

			// searchFieldName
			html = html.replace(/\{searchFieldName\}/g, this.options.searchFieldName || '');

			// searchDefaultText
			html = html.replace(/\{searchDefaultText\}/g, this.options.searchDefaultText || '');

			// searchValue
			html = html.replace(/\{searchValue\}/g, this.options.searchValue ? 'value="' + this.options.searchValue + '"' : '');

			return html;
		},
		makePaginal : function() {
			if (this.pages <= 0)
				return '';

			var html = '<ul class="pagination margin-0">{previous}{btns}{next}</ul>';

			// previous
			html = html.replace(/\{previous\}/g, '<li class="previous"><a class="icon-arrow-left" href="#"></a></li>');

			// btns
			var btns = '';
			var page = Math.floor((this.page - 1) / 10) * 10 + 1;
			for ( var i = 0; i < 10 && page <= this.pages; i++, page++) {
				btns += '<li class="page-btn' + (page === this.page ? ' active' : '') + '" data-page="' + page + '"><a href="#">' + page + '</a></li>';
			}
			html = html.replace(/\{btns\}/g, btns || '');

			// next
			html = html.replace(/\{next\}/g, '<li class="next"><a class="icon-arrow-right" href="#"></a></li>');

			return html;
		},
		makePageInfo : function() {
			var html = '<div class="text-nowrap" style="margin: 10px 0;" title="共{pages}页（{pageSize}条/页 - 共{count}条）">共{pages}页（{pageSize}条/页 - 共{count}条）</div>';

			// pages
			html = html.replace(/\{pages\}/g, this.pages);

			// pageSize
			html = html.replace(/\{pageSize\}/g, this.pageSize);

			// count
			html = html.replace(/\{count\}/g, this.count);

			return html;
		},
		makeButton : function(data) {
			var html = '<div class="btn {style}" id="{id}">{icon}{text}</div>&nbsp;';

			var id = ('' + Math.random()).replace('0.', '');

			// click function
			if (data.click)
				this.clickFunction[id] = data.click;

			// style
			html = html.replace(/\{style\}/g, data.style || 'btn-default');

			// id
			html = html.replace(/\{id\}/g, id);

			// icon
			html = html.replace(/\{icon\}/g, data.icon ? '<i class="' + data.icon + '">&nbsp;</i>&nbsp;' : '');

			// text
			html = html.replace(/\{text\}/g, data.text || '自定义按钮');

			return html;
		},
		makeButtonGroup : function(data) {
			var me = this;

			var html = '<div class="btn-group"><div class="btn dropdown-toggle {style}" id="{id}" data-toggle="dropdown">{icon}{text}{caret}</div>{children}</div>&nbsp;';

			var id = ('' + Math.random()).replace('0.', '');

			// click function
			if (data.click)
				this.clickFunction[id] = data.click;

			// style
			html = html.replace(/\{style\}/g, data.style || 'btn-default');

			// id
			html = html.replace(/\{id\}/g, id);

			// icon
			html = html.replace(/\{icon\}/g, data.icon ? '<i class="' + data.icon + '">&nbsp;</i>&nbsp;' : '');

			// text
			html = html.replace(/\{text\}/g, data.text || '自定义按钮');

			// caret
			html = html.replace(/\{caret\}/g, '&nbsp;&nbsp;<i class="icon-caret-down"></i>');

			// children
			var children = '<ul class="dropdown-menu">{child}</ul>';
			var child = '';
			$(data.children).each(function(i) {
				if (this.split) {
					child += '<li class="divider"></li>';
					return true;
				}

				var cid = ('' + Math.random()).replace('0.', '');

				// click function
				if (this.click)
					me.clickFunction[cid] = this.click;

				var html = '<li id="{id}"><a href="#">{icon}{text}</a></li>';

				// id
				html = html.replace(/\{id\}/g, cid);

				// icon
				html = html.replace(/\{icon\}/g, this.icon ? '<i class="' + this.icon + '">&nbsp;</i>&nbsp;' : '');

				// text
				html = html.replace(/\{text\}/g, this.text || '自定义按钮');

				child += html;
			});
			children = children.replace(/\{child\}/g, child);
			html = html.replace(/\{children\}/g, children);

			return html;
		}
	};

	var changePageBar = function($list, pre, currentPage, pages) {
		var pageBtns = $('ul.pagination li.page-btn', $list);
		var page = pre ? pageBtns.first().data('page') - 10 : pageBtns.last().data('page') + 1;

		pageBtns.addClass('animated flipOutY');

		pageBtns.last().one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function() {
			var $this = $(this);

			var newPageBtns = '';
			for ( var i = 0; i < 10 && page <= pages; i++, page++) {
				newPageBtns += '<li class="page-btn' + (page === currentPage ? ' active' : '') + '" data-page="' + page + '"><a href="#">' + page + '</a></li>';
			}

			$this.after(newPageBtns);
			pageBtns.remove();

			$('ul.pagination li.page-btn', $list).addClass('animated flipInY');

			if ($('ul.pagination li.page-btn', $list).first().data('page') === 1)
				$('ul.pagination li.previous', $list).addClass('disabled');
			else
				$('ul.pagination li.previous', $list).removeClass('disabled');

			if ($('ul.pagination li.page-btn', $list).last().data('page') === pages)
				$('ul.pagination li.next', $list).addClass('disabled');
			else
				$('ul.pagination li.next', $list).removeClass('disabled');
		});

		return true;
	};

	$.fn.list = function(options) {
		return this.each(function(i) {
			var $list = $(this);

			var list = new SuredyList($list, options);

			list.make();

			// button click
			$('.btn', $list).on('click', function(event) {
				var func = list.clickFunction[$(this).attr('id')];

				if (!func)
					return true;

				var searchText = $('.suredy-list input[name="' + list.options.searchFieldName + '"]').val();

				func.call(this, list.page, list.pageSize, searchText);
			});

			// button group's child click
			$('.btn-group li', $list).on('click', function(event) {
				var func = list.clickFunction[$(this).attr('id')];

				if (!func)
					return true;

				var searchText = $('.suredy-list input[name="' + list.options.searchFieldName + '"]').val();

				func.call(this, list.page, list.pageSize, searchText);
			});

			// pagination btn click
			$('ul.pagination li.previous', $list).on('click', function(event) {
				var $this = $(this);

				if ($this.hasClass('disabled'))
					return true;

				changePageBar($list, true, list.page, list.pages);

				return true;
			});

			// next btn click
			$('ul.pagination li.next', $list).on('click', function(event) {
				var $this = $(this);

				if ($this.hasClass('disabled'))
					return true;

				changePageBar($list, false, list.page, list.pages);

				return true;
			});

			// page btn click
			$('ul.pagination', $list).delegate('li.page-btn', 'click', function(event) {
				var $this = $(this);

				if ($this.hasClass('active'))
					return true;

				var func = list.options.paginate;

				if (!func)
					return true;

				var searchText = $('.suredy-list input[name="' + list.options.searchFieldName + '"]').val();

				func.call(this, $this.data('page'), list.pageSize, searchText);

				return true;
			});

			// search box
			$('.suredy-list input[name="' + list.options.searchFieldName + '"]').on('keypress', function(event) {
				if (event.keyCode !== 13)
					return true;

				var func = list.options.paginate;

				if (!func)
					return true;

				var searchText = $(this).val();

				func.call(this, list.page, list.pageSize, searchText);

				return true;
			});

			// search btn
			$('.suredy-list .search-btn').on('click', function(event) {
				var func = list.options.paginate;

				if (!func)
					return true;

				var searchText = $('.suredy-list input[name="' + list.options.searchFieldName + '"]').val();

				func.call(this, list.page, list.pageSize, searchText);

				return true;
			});

			// checkbox all click
			$('.list-content>table>tbody>tr.title-row', $list).delegate('i.row-checkbox', 'click', function(event) {
				var $icon = $(this);
				var checked = $icon.hasClass('icon-check');

				if (checked) {
					// make style to unchecked
					$('.list-content>table>tbody>tr.title-row i.row-checkbox', $list).removeClass('icon-check').addClass('icon-check-empty');
					$('.list-content>table>tbody>tr.content-row.checked', $list).removeClass('checked');
				} else {
					// make style to checked
					$('.list-content>table>tbody>tr.title-row i.row-checkbox', $list).removeClass('icon-check-empty').addClass('icon-check');
					$('.list-content>table>tbody>tr.content-row', $list).addClass('checked');
				}

				return false;
			});

			// checkbox click
			$('.list-content>table>tbody>tr.content-row', $list).delegate('i.row-checkbox', 'click', function(event) {
				var $icon = $(this);
				var parentTr = $icon.parents('tr.content-row');
				var checked = parentTr.hasClass('checked');

				if (checked) {
					// make style to unchecked
					parentTr.removeClass('checked');
				} else {
					// make style to checked
					parentTr.addClass('checked');
				}

				if ($('.list-content>table>tbody>tr.content-row', $list).not('.checked').length > 0) {
					$('.list-content>table>tbody>tr.title-row i.row-checkbox', $list).removeClass('icon-check').addClass('icon-check-empty');
				} else {
					$('.list-content>table>tbody>tr.title-row i.row-checkbox', $list).removeClass('icon-check-empty').addClass('icon-check');
				}

				return false;
			});
		});
	};

	$.suredy.list = {
		checked : function($list) {
			return $('tr.content-row.checked .row-checkbox', $list).parents('tr.content-row');
		}
	};
})(jQuery, window, document);
