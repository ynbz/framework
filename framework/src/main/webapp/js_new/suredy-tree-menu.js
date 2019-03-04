;
(function(global, factory) {

	if (typeof define === 'function' && define.amd) {
		// 添加到amd中，并加载依赖
		define([ 'jquery', 'suredy', 'suredyTree', 'bootstrap' ], function($, Suredy, Tree) {
			return factory(global, $, Suredy, Tree);
		});
	} else if (!global.jQuery || !global.Suredy || !global.Suredy.Tree) {
		throw new Error("Suredy.TreeMenu requires a jQuery and a Suredy.Tree");
	} else {
		factory(global, global.jQuery, global.Suredy, global.Suredy.Tree);
	}

})(this, function(window, $, Suredy, Tree, undefined) {
	'use strict';

	var TreeMenu = function(element, resource, options) {
		this.init('tree.menu', element, resource, options);

		// 绑定点击事件
		this.$element.on('nodeclick.suredy.' + this.type, $.proxy(function(event, $node) {
			if (!Suredy.TreeMenu.isActive($node))
				return true;

			var uri = Suredy.TreeMenu.data($node, 'uri');

			if (!!this.options.triggerUrl && !!uri)
				Suredy.loadContent(uri);

			return true;
		}, this));
	};

	TreeMenu.DEFAULTS = $.extend({}, Tree.Constructor.DEFAULTS, {
		triggerUrl : true,
		alwaysActive : false,

		allowEmpty : false,
		toggleActive : false,
	});

	TreeMenu.STYLES = $.extend({}, Tree.Constructor.STYLES);

	// 继承树
	TreeMenu.prototype = $.extend({}, Tree.Constructor.prototype);
	TreeMenu.prototype.constructor = TreeMenu;

	TreeMenu.prototype.getDefaults = function() {
		return TreeMenu.DEFAULTS;
	};

	TreeMenu.prototype.getStylesDefine = function() {
		return TreeMenu.STYLES;
	};

	TreeMenu.prototype.draw = function() {
		this.$element.empty().append(this.$tree);
		Suredy.TreeMenu.checked(this.$element).eq(0).find('>.node-info>.node-info-item.text').trigger('click');
	};

	TreeMenu.prototype.createNodInfo = function(isFolder, showCheckbox, nodeIcon, text) {
		var infos = [];

		// checkbox
		infos[infos.length] = this.createCheckboxItem(showCheckbox);

		// icon
		infos[infos.length] = this.createNodeIconItem(nodeIcon);

		// text
		infos[infos.length] = this.createNodeTextItem(text);

		// 三角符
		infos[infos.length] = isFolder ? this.createAngleItem() : this.createEmptyNodeInfoItem();

		return infos;
	};

	TreeMenu.prototype.canActive = function($node) {
		return !!this.options.alwaysActive || !!Suredy.TreeMenu.data($node, 'uri');
	};

	var _plugin = function(resource, options) {
		return this.each(function(i, dom) {
			var $this = $(this);
			var menu = $this.data('suredy.tree.menu');

			if (!menu)
				menu = new TreeMenu(this, resource, options);
			else
				menu.init(menu.type, this, resource, options);

			menu.draw();
		});
	};

	Suredy.TreeMenu = function(element, resource, options) {
		return _plugin.call($(element), resource, options);
	};

	Suredy.TreeMenu.Constructor = TreeMenu;

	Suredy.TreeMenu.instance = function(element) {
		var $tree = $(element);

		// 树节点-->获取树
		if ($tree.is('.tree-node'))
			$tree = $tree.parents('.suredy-tree.tree-menu:first');

		// 树-->获取树的容器
		if ($tree.is('.suredy-tree.tree-menu'))
			$tree = $tree.parent();

		if (!$tree.length)
			throw new Error('Invalid Suredy tree menu.');

		return $tree.data('suredy.tree.menu');
	};

	Suredy.TreeMenu.nodes = function(menu) {
		return Tree.nodes(menu);
	};

	Suredy.TreeMenu.data = function(node, key) {
		var $node = $(node);

		if (!$node.is('.tree-node'))
			throw new Error('Invalid Suredy tree menu node.');

		var comp = Suredy.TreeMenu.instance($node);

		if (!comp)
			throw new Error('Not a Suredy tree menu.');

		return comp.nodeData($node, key);
	};

	Suredy.TreeMenu.checked = function(menu) {
		return Tree.checked(menu);
	};

	Suredy.TreeMenu.isActive = function(node) {
		return Tree.isActive(node);
	};

	Suredy.TreeMenu.toggleActive = function(node) {
		return Tree.toggleActive(node);
	};

	Suredy.TreeMenu.nodeClick = 'nodeclick.suredy.tree.menu';

	return Suredy.TreeMenu;
});