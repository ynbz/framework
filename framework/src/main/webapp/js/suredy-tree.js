/**
 * This is tree plugin
 * 
 * @author VIVID.G
 * @since 2015-6-30
 * @version v0.1
 */
(function($, window, document, undefined) {
	$.suredy = $.extend({}, $.suredy);

	var SuredyTree = function($tree, options) {
		this.tree = $tree;
		this.data = {};
		this.options = $.extend({
			allowEmpty : true,
			autoCollapse : false,
			checkbox : false,
			onlyLeafCheckbox : false,
			onlyFolderCheckbox : false,
			collapseAll : false,
			click : function() {
			},
			style : 'normal', // normal|file|department
			showTitle : false
		}, options);
	};

	SuredyTree.prototype = {
		make : function(data) {
			if (!this.options.allowEmpty && data.length <= 0) {
				alert('Invalid tree data. Please check it.');
				return;
			}

			var html = this.makeUl(data, false, false);

			this.tree.html(html);

			$('>ul', this.tree).addClass('suredy-tree ' + this.options.style);

			var treeData = this.data;

			// bind
			$('li>a', this.tree).each(function(i) {
				var $this = $(this);

				// bind data
				$this.data('tree-data', treeData[$this.attr('id')]);
			});
		},
		makeUl : function(data, folder, collapse, id, level) {
			if (!data)
				return '';

			var ul = '<ul class="list-group{collapse}{in}"{id}>{li}</ul>';

			// folder
			ul = ul.replace(/\{collapse\}/g, folder ? ' collapse' : '');

			// collapse
			ul = ul.replace(/\{in\}/g, (this.options.collapseAll || collapse) ? '' : ' in');

			// id
			ul = ul.replace(/\{id\}/g, id ? ' id="' + id + '"' : '');

			// li
			var li = '';
			for ( var i = 0; data && i < data.length; i++) {
				li += this.makeLi(data[i], i, id, level);
			}

			return ul.replace(/\{li\}/g, li);
		},
		makeLi : function(data, index, parentId, level) {
			if (!data)
				return '';

			level = level || 1;

			var li = '<li class="list-group-item">{a}{ul}</li>';

			var id = (parentId || ('node' + Math.random()).replace(/0\./g, '')) + index;

			// a
			li = li.replace(/\{a\}/g, this.makeA(data, id, level));

			// children
			li = li.replace(/\{ul\}/g, this.makeUl(data.children, true, data.collapse, id, level + 1));

			return li;
		},
		makeA : function(data, id, level) {
			if (!data)
				return '';

			var a = '<a id="{id}" class="text-nowrap{active}{collapsed}" href="javascript:void(0)"{toggle}{target}{title}>{text}</a>';
			var checkbox = '<td class="{hide}"><i class="tree-checkbox icon-check-empty{checked}"></i></td>';
			var icon = '<td><i class="tree-icon icon-angle-down"></i></td>';
			var text = '<table class="pull-left tree-node-pre" style="{level}"><tr>{icon}{checkbox}</tr></table>{text}';

			// checked
			checkbox = checkbox.replace(/\{checked\}/g, data.checked ? ' node-checked' : '');

			// hide checkbox
			var show = true;
			if (this.options.onlyLeafCheckbox || this.options.onlyFolderCheckbox)
				show = (this.options.onlyLeafCheckbox && !data.children) || (this.options.onlyFolderCheckbox && data.children);
			checkbox = checkbox.replace(/\{hide\}/g, show ? '' : 'hide');

			// level
			text = text.replace(/\{level\}/g, level && level - 1 > 0 ? 'margin-left: ' + ((level - 1) * 1.3) + 'em; ' : '');

			// icon
			text = text.replace(/\{icon\}/g, icon);

			// checkbox
			text = text.replace(/\{checkbox\}/g, this.options.checkbox ? checkbox : '');

			// text
			text = text.replace(/\{text\}/g, data.text ? data.text : 'tree-' + id);

			// id
			var idOfA = ('a' + Math.random()).replace(/0\./g, '');
			this.data[idOfA] = data.data || {}; // bind data
			a = a.replace(/\{id\}/g, idOfA);

			// active
			a = a.replace(/\{active\}/g, this.options.checkbox ? ((data.checked) ? ' active' : '') : (data.active ? ' active' : ''));

			// collapsed
			a = a.replace(/\{collapsed\}/g, (this.options.collapseAll || data.collapse) ? ' collapsed' : '');

			// toggle
			a = a.replace(/\{toggle\}/g, data.children ? ' data-toggle="collapse"' : '');

			// target
			a = a.replace(/\{target\}/g, data.children && id ? ' data-target="#' + id + '"' : '');

			// title
			a = a.replace(/\{title\}/g, this.options.showTitle ? ' title="' + data.text + '"' : '');

			// text
			a = a.replace(/\{text\}/g, text);

			return a;
		}
	};

	$.fn.tree = function(resource, options) {
		if ($.type(resource) === 'string') {
			resource = $.suredy.loadData(resource);

			if (!resource) {
				return;
			}
		}

		if (resource && !$.isArray(resource))
			resource = [ resource ];

		return this.each(function(i) {
			var $tree = $(this);

			var tree = new SuredyTree($tree, options);

			// draw tree
			tree.make(resource);

			// bind tree click event
			$('li>a', $tree).on('click', function(event) {
				var $this = $(this);

				// auto collapse
				if (tree.options.autoCollapse && $('+ul', $this).length > 0) {
					var parent = $this.parent('li').parent('ul');
					$('>li>ul.collapse.in', parent).collapse('toggle');
				}

				// call click function
				var result = tree.options.click.call($this.get(0));

				if (result === undefined || result === true) {
					// change active style
					var $checkbox = $('.tree-checkbox', $this);
					if ($checkbox.length === 0) {
						$('li>a.active', $tree).removeClass('active');
						$this.addClass('active');
					}
				}

				return true;
			});

			// bind tree checkbox click event
			$('li>a .tree-checkbox', $tree).on('click', function(event) {
				var $this = $(this);

				if ($this.hasClass('node-checked')) {
					$this.removeClass('node-checked');
					$this.parents('a').removeClass('active');
				} else {
					$this.addClass('node-checked');
					$this.parents('a').addClass('active');
				}

				return true;
			});

			// click tree
			$('li a.active', $tree).trigger('click');
		});
	};

	$.suredy.tree = {
		data : function($node) {
			if (!$node || !($node instanceof jQuery)) {
				alert('Invalid tree node.');
				return;
			}

			var data = [];

			$node.each(function(i) {
				data[i] = $(this).data('tree-data');
			});

			if (data.length === 0)
				return undefined;

			if (data.length === 1)
				return data[0];

			return data;
		},
		checked : function($tree) {
			if (!$tree || !($tree instanceof jQuery)) {
				alert('It\'s not a tree.');
				return;
			}

			return $('li>a.active', $tree);
		}
	};
})(jQuery, window, document);
