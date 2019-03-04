;
(function(global, factory) {

	if (typeof define === 'function' && define.amd) {
		// 添加到amd中，并加载依赖
		define([ 'jquery', 'suredy', 'suredyTree', 'bootstrap' ], function($, Suredy, Tree) {
			return factory(global, $, Suredy, Tree);
		});
	} else if (!global.jQuery || !global.Suredy || !global.Suredy.Tree) {
		throw new Error("Suredy.TreeSelector requires a jQuery and a Suredy.Tree");
	} else {
		factory(global, global.jQuery, global.Suredy, global.Suredy.Tree);
	}

})(this, function(window, $, Suredy, Tree, undefined) {
	'use strict';

	if (!$.fn.popover)
		throw new Error('Datetimepicker requires Bootstrap\'s popover');

	var TreeSelector = function(element, resource, options) {
		this.popover = null;

		this.init('tree.selector', element, resource, options);

		this.minWidth = this.options.minWidth || 0;

		// 初始化popover
		this.initPopover();
	};

	TreeSelector.DEFAULTS = $.extend({}, Tree.Constructor.DEFAULTS);

	TreeSelector.STYLES = $.extend({}, Tree.Constructor.STYLES);

	// 继承树
	TreeSelector.prototype = $.extend({}, Tree.Constructor.prototype);
	TreeSelector.prototype.constructor = TreeSelector;

	TreeSelector.prototype.getDefaults = function() {
		return TreeSelector.DEFAULTS;
	};

	TreeSelector.prototype.getStylesDefine = function() {
		return TreeSelector.STYLES;
	};

	TreeSelector.prototype.destroy = function() {
		if (this.popover)
			this.popover.destroy();

		this.popover = null;

		$(window).off('resize.suredy.' + this.type);

		this.$tree.removeData('suredy.' + this.type);

		Tree.Constructor.prototype.destroy.call(this);
	};

	TreeSelector.prototype.initPopover = function() {
		if (this.popover) {
			this.$element.popover('hide');
			return;
		}

		var that = this;

		this.$element.popover({
			content : function() {
				return that.$tree;
			},
			html : true,
			placement : 'bottom',
			trigger : 'manual',
			template : '<div class="popover suredy-tree-selector" role="tooltip"><div class="popover-content padding-0"></div></div>',
			//container : 'body',
		});

		this.popover = this.$element.data('bs.popover');

		// 显示、隐藏selector
		this.$element.off('click.suredy.' + this.type);
		this.$element.on('click.suredy.' + this.type, function(event) {
			$(this).popover('toggle');
			return false;
		});

		// 控制selector大小
		this.$element.off('show.bs.popover');
		this.$element.on('show.bs.popover', $.proxy(function(event) {
			var eleWidth = this.$element.outerWidth();
			var width = this.options.minWidth > 0 && eleWidth < this.options.minWidth && this.options.minWidth || eleWidth;
			this.popover.tip().width(width - 4);
			this.$element.trigger(Suredy.TreeSelector.selectorShow); // 触发用户事件
		}, this));

		// 显示完成后执行操作
		this.$element.off('shown.bs.popover');
		this.$element.on('shown.bs.popover', $.proxy(function(event) {
			this.$element.trigger(Suredy.TreeSelector.selectorShown); // 触发用户事件

			var hideEvent = null;

			hideEvent = function(event) {
				if ($(event.target).parents('.popover.suredy-tree-selector:first').length) {
					$(document).one('click', hideEvent);
					return true;
				}

				if (that.$element)
					that.$element.popover('hide');
			};

			$(document).one('click', hideEvent);
		}, this));

		$(window).off('resize.suredy.' + this.type);
		$(window).on('resize.suredy.' + this.type, $.proxy(function() {
			this.popover.tip().width(this.$element.outerWidth() - 4);
		}, this));

		this.popover.tip().css('border', '1px solid #888');
	};

	var _plugin = function(resource, options) {
		return this.each(function(i, dom) {
			var $this = $(this);
			var selector = $this.data('suredy.tree.selector');

			if (!selector)
				selector = new TreeSelector(this, resource, options);
			else
				selector.init(selector.type, this, resource, options);
		});
	};

	Suredy.TreeSelector = function(element, resource, options) {
		return _plugin.call($(element), resource, options);
	};

	Suredy.TreeSelector.Constructor = TreeSelector;

	Suredy.TreeSelector.instance = function(element) {
		var $selector = $(element);

		// 树节点-->获取树
		if ($selector.is('.tree-node'))
			$selector = $selector.parents('.suredy-tree.tree-selector:first');

		if (!$selector.length)
			throw new Error('Invalid Suredy tree selector.');

		return $selector.data('suredy.tree.selector');
	};

	Suredy.TreeSelector.hidden = function(selector) {
		var $selector = $(selector);

		if (!$selector.length)
			throw new Error('Invalid Suredy tree selector.');

		return $selector.triggerHandler('click');
	};

	// selector: 可以使selector自身，也可以是tree的node
	Suredy.TreeSelector.nodes = function(selector) {
		var $selector = $(selector);

		if (!$selector.length)
			throw new Error('Invalid Suredy tree selector.');

		// 树节点，直接获取子节点
		if ($selector.is('.tree-node'))
			return Tree.nodes($selector);

		var comp = Suredy.TreeSelector.instance($selector);

		if (!comp)
			throw new Error('Not a Suredy tree selector.');

		return Tree.nodes(comp.$tree);
	};

	// selector: 可以使selector自身，也可以是tree的node
	Suredy.TreeSelector.data = function(selector, key) {
		var $selector = $(selector);

		if (!$selector.length)
			throw new Error('Invalid Suredy tree selector.');

		var comp = Suredy.TreeSelector.instance($selector);

		if (!comp)
			throw new Error('Not a Suredy tree selector.');

		var $nodes = $selector;

		// 直接获取选中的节点
		if (!$selector.is('.tree-node'))
			$nodes = Tree.checked(comp.$tree);

		if (!$nodes.length)
			return null;

		var data = [];

		$nodes.each(function(i, dom) {
			data[i] = comp.nodeData(dom, key);
		});

		return data.length === 1 ? data[0] : data;
	};

	Suredy.TreeSelector.checked = function(selector) {
		var $selector = $(selector);

		if (!$selector.length)
			throw new Error('Invalid Suredy tree selector.');

		var comp = Suredy.TreeSelector.instance($selector);

		if (!comp)
			throw new Error('Not a Suredy tree selector.');

		return Tree.checked(comp.$tree);
	};

	// selector: 可以使selector自身，也可以是tree的node
	Suredy.TreeSelector.isActive = function(selector) {
		var $selector = $(selector);

		if (!$selector.length)
			throw new Error('Invalid Suredy tree selector.');

		// 树节点，直接获取数据
		if ($selector.is('.tree-node'))
			return Tree.isActive($selector);

		var $nodes = Suredy.TreeSelector.checked($selector);

		return !!$nodes.length;
	};

	Suredy.TreeSelector.setActive = function(node) {
		return Tree.setActive(node);
	};

	Suredy.TreeSelector.toggleActive = function(node) {
		return Tree.toggleActive(node);
	};

	Suredy.TreeSelector.destroy = function(selector) {
		var $selector = $(selector);

		if (!$selector.length)
			throw new Error('Invalid Suredy tree selector.');

		var comp = Suredy.TreeSelector.instance($selector);

		if (!comp)
			throw new Error('Not a Suredy tree selector.');

		comp.destroy();
	};

	Suredy.TreeSelector.active = Suredy.TreeSelector.checked;
	Suredy.TreeSelector.isChecked = Suredy.TreeSelector.isActive;

	Suredy.TreeSelector.nodeClick = 'nodeclick.suredy.tree.selector';
	Suredy.TreeSelector.selectorShow = 'selectorshow.suredy.tree.selector';
	Suredy.TreeSelector.selectorShown = 'selectorshown.suredy.tree.selector';

	return Suredy.TreeSelector;

});
