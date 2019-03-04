;
(function(global, factory) {

	if (typeof define === 'function' && define.amd) {
		// 添加到amd中，并加载依赖
		define([ 'jquery', 'suredy', 'bootstrap' ], function($, Suredy) {
			return factory(global, $, Suredy);
		});
	} else if (!global.jQuery || !global.Suredy) {
		throw new Error("Suredy.Tree requires a jQuery and a Suredy");
	} else {
		factory(global, global.jQuery, global.Suredy);
	}

})(this, function(window, $, Suredy) {
	'use strict';

	// resource可以为RUI可以为function
	var Tree = function(element, resource, options) {
		this.$element = null;
		this.$tree = null;
		this.type = null;
		this.resource = null;
		this.options = null;

		this.size = null;
		this.style = null;

		this.init('tree', element, resource, options);
	};

	// 默认参数
	Tree.DEFAULTS = {
		allowEmpty : true,
		autoCollapse : true, // 自动折叠
		multiselect : false, // 允许多选
		leafCheckbox : false, // 为子节点添加checkbox
		folderCheckbox : false, // 为父节点添加checkbox
		canLeafActive : true, // 子节点可选中
		canFolderActive : true, // 父节点可选中
		collapseAll : false, // 收起所有节点
		inContainer : true, // 在一个特定容器中，此时，只显示树节点下边框
		size : 'normal', // 尺寸，xl > lg > normal > sm. default is normal
		style : 'normal', // 样式，'normal'|'file'|'department'|{}或者是一个js对象（参看默认样式定义部分）
		showTitle : false, // 为树节点附加title属性
		toggleActive : true, // 轮换选中与未选中状态，使用此属性可以实现必选功能

		asynLoadable : false, // 是否启用异步加载
		asynLoadUri : '', // 请求数据的URI。推荐使用rest风格URI。格式：uri/{id}/{name}或者uri?id={id}&name={name}。
	// 其中{key}为父节点数据的占位符，key为父节点数据的key，{.}表示使用直接使用节点数据（当节点数据只是一个字符串或者数字或者boolean的时候）
	};

	// 默认样式
	Tree.STYLES = {
		normal : {
			folder : {
				icon : 'icon-envelope-alt',
			},
			leaf : {
				icon : 'icon-paper-clip',
			},
		},
		file : {
			folder : {
				icon : 'icon-suredy-tree-folder',
			},
			leaf : {
				icon : 'icon-suredy-tree-file',
			},
		},
		department : {
			folder : {
				icon : 'icon-suredy-tree-dep',
			},
			leaf : {
				icon : 'icon-suredy-tree-user',
			},
		},
	};

	Tree.prototype.getDefaults = function() {
		return Tree.DEFAULTS;
	};

	Tree.prototype.getStylesDefine = function() {
		return Tree.STYLES;
	};

	Tree.prototype.init = function(type, element, resource, options) {
		this.$element = $(element);

		if (!this.$element.length)
			throw new Error('Invalid Tree container.');

		this.$element.data('suredy.' + type, this);

		this.type = type;
		this.resource = resource;
		this.options = this.getOptions(options);

		this.size = this.getSize(this.options.size);
		this.style = this.getStyle(this.options.style);

		this.$tree = $('<ul class="suredy-tree ' + this.type.replace(/\./g, '-') + '"/>');

		// 为树绑定控件对象
		this.$tree.data('suredy.' + this.type, this);

		this.setTreeClasses();

		this.prepareTree();
	};

	Tree.prototype.destroy = function() {
		this.$element.off('.suredy.' + this.type).removeData('suredy.' + this.type).empty();

		if (this.$tree)
			this.$tree.removeData('suredy.' + this.type).remove();

		this.$element = null;
		this.$tree = null;
		this.type = null;
		this.resource = null;
		this.options = null;
		this.size = null;
		this.style = null;
	};

	Tree.prototype.draw = function() {
		this.$element.empty().append(this.$tree);
	};

	Tree.prototype.getOptions = function(options) {
		return $.extend({}, this.getDefaults(), options, this.$element.data());
	};

	Tree.prototype.getSize = function(size) {
		if ($.inArray(size, [ 'xl', 'lg', 'sm' ]) === -1)
			return 'tree-normal';

		return 'tree-' + size;
	};

	Tree.prototype.getStyle = function(style) {
		if ($.type(style) === 'string')
			style = this.getStylesDefine()[style];

		if ($.isEmptyObject(style))
			style = this.getStylesDefine()['normal'];

		return style;
	};

	Tree.prototype.getTreeData = function(resource) {
		if (!resource)
			throw new Error('Invalid tree data.');

		var data = null;

		if ($.type(resource) === 'string') {
			data = Suredy.loadData(resource);
		} else if ($.type(resource) === 'function') {
			data = resource();
		} else {
			data = resource;
		}

		if (!$.isArray(data) && $.type(data) === 'object')
			data = [ data ];

		if ($.isEmptyObject(data))
			data = [];

		if (!!!this.options.allowEmpty && $.isEmptyObject(data))
			throw new Error('Empty tree data.');

		if (!$.isArray(data))
			throw new Error('Tree data must be an array.');

		return data;
	};

	Tree.prototype.setTreeClasses = function() {
		if (!!this.options.inContainer)
			this.$tree.addClass('in-container');

		this.$tree.addClass(this.size);
	};

	Tree.prototype.prepareTree = function() {
		var data = this.getTreeData(this.resource);

		// 绑定节点点击事件
		this.$tree.on('click.suredy.' + this.type, '.tree-node>.node-info>.node-info-item', $.proxy(this.EventNodeInfoItemClick, this));

		// 绑定子节点加载事件
		this.$tree.on('add.nodes.suredy.' + this.type, $.proxy(this.EventAddNodes, this));

		// 加载直属节点
		this.$tree.trigger('add.nodes.suredy.' + this.type, [ this.$tree, data, 0 ]);

		return this.$tree;
	};

	Tree.prototype.createOneNode = function(nodeData, level, pid) {
		if (!nodeData)
			return $();

		var id = Suredy.randomNumberString();
		var isFolder = !!nodeData.children;
		var isActive = (isFolder ? !!this.options.canFolderActive : !!this.options.canLeafActive) && (!!nodeData.active || !!nodeData.checked);
		var isCollapse = isFolder && (!!this.options.collapseAll || !!nodeData.collapse);
		var nodeIcon = nodeData.icon || (isFolder ? this.style['folder'] : this.style['leaf'])['icon'];
		var text = nodeData.text || 'Tree Node';
		var showCheckbox = isFolder ? !!this.options.folderCheckbox : !!this.options.leafCheckbox;

		var $node = $('<li class="tree-node"/>').addClass(isActive ? 'active' : '');
		var $nodeInfo = $('<div class="node-info"/>').appendTo($node);

		level = parseInt(level) || 0;

		$node.data('node.level', level);

		if (isCollapse)
			$nodeInfo.addClass('collapsed');

		// 绑定数据
		$node.data('suredy.' + this.type + '.node.data', this.mixNodeData(nodeData));

		if (isFolder)
			$node.addClass('folder');
		else
			$node.addClass('leaf');

		// title
		if (!!this.options.showTitle)
			$nodeInfo.attr('title', text);

		// 添加缩进
		$nodeInfo.append(this.createIndentationItems(level));

		// 绘制节点
		$nodeInfo.append(this.createNodInfo(isFolder, showCheckbox, nodeIcon, text));

		// 处理子节点
		if (isFolder) {
			// 增加折叠控制
			$nodeInfo.attr('role', 'button');
			$nodeInfo.attr('data-toggle', 'collapse');
			$nodeInfo.attr('data-target', '#' + id);
			$nodeInfo.attr('aria-expanded', !isCollapse);
			$nodeInfo.attr('aria-controls', id);
			if (!!this.options.autoCollapse) {
				$node.addClass('panel');
				$nodeInfo.attr('data-parent', '#' + pid);
			}

			var $children = $('<ul class="collapse"/>').appendTo($node).attr('id', id).addClass(!isCollapse ? 'in' : '');
			// $children.append('<li class="tree-node loading"><div
			// class="node-info"></div></li>');

			// 绘制“加载中”
			var $childrenNodeInfo = $children.find('.node-info');
			$childrenNodeInfo.append(this.createIndentationItems(level + 2));
			$childrenNodeInfo.append(this.createEmptyNodeInfoItem().append('<i class="icon-spinner icon-spin"/>'));
			$childrenNodeInfo.append(this.createEmptyNodeInfoItem().addClass('text').append('加载中……'));

			if (!isCollapse || !!!this.options.asynLoadable) {
				// 展开、同步加载数据时
				this.$tree.trigger('add.nodes.suredy.' + this.type, [ $children, nodeData.children, level + 1 ]);
			} else {
				// 异步加载数据时，绑定数据加载事件
				$node.one('click.add.nodes.suredy.' + this.type, '>.node-info>.node-info-item', [ $children, nodeData.children, level + 1 ], $.proxy(function(event) {
					this.$tree.trigger('add.nodes.suredy.' + this.type, event.data);
				}, this));
			}
		}

		return $node;
	};

	Tree.prototype.createNodInfo = function(isFolder, showCheckbox, nodeIcon, text) {
		var infos = [];

		// 三角符
		infos[infos.length] = isFolder ? this.createAngleItem() : this.createEmptyNodeInfoItem();

		// checkbox
		infos[infos.length] = this.createCheckboxItem(showCheckbox);

		// icon
		infos[infos.length] = this.createNodeIconItem(nodeIcon);

		// text
		infos[infos.length] = this.createNodeTextItem(text);

		return infos;
	};

	Tree.prototype.mixNodeData = function(nodeData) {
		var data = $.extend({}, nodeData);

		delete data.children;

		return data;
	};

	Tree.prototype.reload = function() {
		this.init('tree', this.$element, this.resource, this.options);
		this.draw();
	};

	Tree.prototype.refresh = function($node, data) {
		$node = $($node);

		if (!$node.is('.tree-node'))
			throw new Error('Invalid Suredy tree node.');

		// 无数据直接返回
		if (!data)
			return;

		if ($.type(data) !== 'object')
			throw new Error('Invalid Suredy tree data.');

		var that = this;

		$node.each(function(i, dom) {
			var $this = $(dom);
			var text = data.text || 'Tree Node';
			var $nodeInfo = $this.find('>.node-info');

			$this.data('suredy.' + that.type + '.node.data', that.mixNodeData(data));

			// title
			if (!!that.options.showTitle)
				$nodeInfo.attr('title', text);

			$nodeInfo.find('>.node-info-item.text').text(text);
		});
	};

	Tree.prototype.refreshChildren = function($node, resource) {
		$node = $($node);

		if (!$node.is('.folder'))
			throw new Error('Need a Suredy tree folder node.');

		var that = this;

		$node.each(function(i, dom) {
			var $this = $(dom);
			var level = $this.data('node.level');

			that.$tree.trigger('add.nodes.suredy.' + that.type, [ $this.find('>ul'), resource, level + 1 ]);
		});
	};

	Tree.prototype.createEmptyNodeInfoItem = function() {
		return $('<div class="node-info-item"/>');
	};

	Tree.prototype.createIndentationItems = function(level) {
		var i = 0, emptys = [];

		for (i = 0; i < level; i++) {
			emptys[emptys.length] = this.createEmptyNodeInfoItem().addClass('empty');
		}

		return emptys;
	};

	Tree.prototype.createAngleItem = function() {
		return this.createEmptyNodeInfoItem().append($('<i class="angle-icon icon-angle-down"/>')).addClass('angle');
	};

	Tree.prototype.createCheckboxItem = function(showCheckbox) {
		if (!showCheckbox)
			return $();

		return this.createEmptyNodeInfoItem().append($('<i class="checkbox-icon icon-check-empty"/>')).addClass('checkbox');
	};

	Tree.prototype.createNodeIconItem = function(icon) {
		return this.createEmptyNodeInfoItem().append($('<i class="node-icon"/>').addClass(icon)).addClass('node-icon');
	};

	Tree.prototype.createNodeTextItem = function(text) {
		return this.createEmptyNodeInfoItem().text(text).addClass('text');
	};

	Tree.prototype.getLoadDataUri = function(uri, pData) {
		if (!uri || typeof uri !== 'string')
			throw new Error('Invalid URI from load data. It is: ' + uri);

		if (!pData)
			pData = {};

		var fixUri = uri;

		$.each(uri.match(/\{.*?}/g), function(i, str) {
			var pd;

			if ('{.}' === str)
				pd = pData;
			else
				pd = Suredy.data(pData, str.replace(/\{/g, '').replace(/\}/g, ''));

			fixUri = fixUri.replace(new RegExp(str, 'g'), pd || '');
		});

		return fixUri;
	};

	Tree.prototype.EventAddNodes = function(event, $ul, nodeData, level) {
		$ul = $($ul);

		var that = this;
		var id = $ul.attr('id') || Suredy.randomNumberString();

		level = level || 0;
		$ul.attr('id', id);

		// 切换为“加载中”状态
		var $nodeIcon = $ul.parent().find('>.node-info>.node-info-item.node-icon>.node-icon');
		var oldClass = $nodeIcon.attr('class');
		$nodeIcon.attr('class', 'node-icon icon-spinner icon-spin');

		// 加载数据
		if (level > 0 && !!this.options.asynLoadable && $.isEmptyObject(nodeData)) {
			var uri = this.nodeData($ul.parent(), 'loadDataFrom') || this.options.asynLoadUri;
			nodeData = this.getTreeData(this.getLoadDataUri(uri, this.nodeData($ul.parent())));

			if ($.isEmptyObject(nodeData))
				nodeData = [];
			else if ($.type(nodeData) === 'object')
				nodeData = [ nodeData ];
			else if ($.type(nodeData) !== 'array')
				nodeData = [];
		}

		$ul.empty();

		if (nodeData) {
			$.each(nodeData, function(i, data) {
				$ul.append(that.createOneNode(data, level, id));
			});
		}

		// 从“加载中”状态还原
		$nodeIcon.attr('class', oldClass);
	};

	Tree.prototype.EventNodeInfoItemClick = function(event) {
		var $this = $(event.currentTarget);
		var $node = $this.parent().parent();

		if ($node.is('.loading'))
			return false;

		var isFolder = this.isFolder($node);
		var canToogleActive = !!this.options.toggleActive || !!$node.find('>.node-info>.node-info-item.checkbox').length;
		var canMultiselect = !!this.options.multiselect;

		// 不可激活节点，直接返回，不触发任何自定义事件
		if (!this.canActive($node))
			return true;

		if (!isFolder || ($this.is('.checkbox') || $this.is('.text'))) {
			if (!canMultiselect)
				this.$tree.find('.tree-node.active').not($node).removeClass('active');

			if (canToogleActive)
				$node.toggleClass('active');
			else
				$node.addClass('active');

			// 触发用户事件（点击后事件）
			this.$element.trigger('nodeclick.suredy.' + this.type, $node);
		}

		// 点击文件夹复选框和文本的时候，终止折叠事件
		return isFolder ? !($this.is('.checkbox') || $this.is('.text')) : true;
	};

	Tree.prototype.isFolder = function($node) {
		return $($node).is('.folder');
	};

	Tree.prototype.canActive = function($node) {
		return this.isFolder($node) ? !!this.options.canFolderActive : !!this.options.canLeafActive;
	};

	Tree.prototype.nodeData = function($node, key) {
		$node = $($node);

		if (!$node.is('.tree-node'))
			throw new Error('Invalid Suredy tree node.');

		var data = $node.data('suredy.' + this.type + '.node.data');

		if (!data)
			return null;

		// 直接返回用户数据
		if (!key)
			return data['data'];

		// 用户数据
		var d = Suredy.data(data['data'], key);

		if (d !== undefined)
			return d;

		// 用户数据中没有相应的数据，则返回节点数据
		return Suredy.data(data, key);
	};

	var _plugin = function(resource, options) {
		return this.each(function(i, dom) {
			var $this = $(this);
			var tree = $this.data('suredy.tree');

			if (!tree)
				tree = new Tree(this, resource, options);
			else
				tree.init(tree.type, this, resource, options);

			tree.draw();
		});
	};

	Suredy.Tree = function(element, resource, options) {
		return _plugin.call($(element), resource, options);
	};

	Suredy.Tree.Constructor = Tree;

	Suredy.Tree.instance = function(element) {
		var $tree = $(element);

		// 树节点-->获取树
		if ($tree.is('.tree-node'))
			$tree = $tree.parents('.suredy-tree.tree:first');

		// 树-->获取树的容器
		if ($tree.is('.suredy-tree.tree'))
			$tree = $tree.parent();

		if (!$tree.length)
			throw new Error('Invalid Suredy tree.');

		return $tree.data('suredy.tree');
	};

	Suredy.Tree.nodes = function(tree) {
		var $tree = $(tree);

		if (!$tree.length)
			throw new Error('Invalid Suredy tree.');

		return $('.tree-node', $tree);
	};

	Suredy.Tree.data = function(node, key) {
		var $node = $(node);

		if (!$node.is('.tree-node'))
			throw new Error('Invalid Suredy tree node.');

		var comp = Suredy.Tree.instance($node);

		if (!comp)
			throw new Error('Not a Suredy tree.');

		return comp.nodeData($node, key);
	};

	Suredy.Tree.checked = function(tree) {
		var $tree = $(tree);

		if (!$tree.length)
			throw new Error('Invalid Suredy tree.');

		return $('.tree-node.active', $tree);
	};

	Suredy.Tree.isActive = function(node) {
		var $node = $(node);

		if (!$node.is('.tree-node'))
			throw new Error('Invalid Suredy tree node.');

		return $node.is('.active');
	};

	Suredy.Tree.setActive = function(node) {
		var $node = $(node);

		if (!$node.is('.tree-node'))
			throw new Error('Invalid Suredy tree node.');

		$node.addClass('active');
	};

	Suredy.Tree.toggleActive = function(node) {
		var $node = $(node);

		if (!$node.is('.tree-node'))
			throw new Error('Invalid Suredy tree node.');

		$node.toggleClass('active');
	};

	Suredy.Tree.reload = function(tree) {
		var comp = Suredy.Tree.instance(tree);

		if (!comp)
			throw new Error('Not a Suredy tree.');

		comp.reload();
	};

	Suredy.Tree.refresh = function(node, data) {
		var $node = $(node);

		var comp = Suredy.Tree.instance($node);

		if (!comp)
			throw new Error('Not a Suredy tree.');

		comp.refresh($node, data);
	};

	Suredy.Tree.refreshChildren = function(node, data) {
		var $node = $(node);

		if (!$node.length || !$node.is('.tree-node'))
			throw new Error('Invalid Suredy tree node.');

		var comp = Suredy.Tree.instance($node);

		if (!comp)
			throw new Error('Not a Suredy tree.');

		comp.refreshChildren($node, data);
	};

	Suredy.Tree.destroy = function(tree) {
		var $tree = $(tree);

		if (!$tree.length)
			throw new Error('Invalid Suredy tree.');

		var comp = Suredy.Tree.instance($tree);

		if (!comp)
			throw new Error('Not a Suredy tree.');

		comp.destroy();
	};

	Suredy.Tree.active = Suredy.Tree.checked;
	Suredy.Tree.isChecked = Suredy.Tree.isActive;

	Suredy.Tree.nodeClick = 'nodeclick.suredy.tree';

	return Suredy.Tree;

});