/**
 * This is tree menu plugin
 * 
 * @author VIVID.G
 * @since 2015-6-30
 * @version v0.1
 */
(function($, window, document, undefined) {
	$.suredy = $.extend({}, $.suredy);

	var SuredyTreeMenu = function($menu, data, options) {
		this.menu = $menu;
		this.data = data || [];
		this.options = $.extend({
			allowEmpty : true,
			autoCollapse : false
		}, options);
	};

	SuredyTreeMenu.prototype = {
		make : function() {
			if (!this.options.allowEmpty && this.data.length <= 0) {
				alert('Invalid tree menu data. Please check it.');
				return;
			}

			var html = this.makeUl(this.data, false, false);

			this.menu.html(html);

			$('>ul', this.menu).addClass('suredy-tree-menu');
		},
		makeUl : function(data, folder, collapse, id, level) {
			if (!data)
				return '';

			var ul = '<ul class="list-group{collapse}{in}"{id}>{li}</ul>';

			// folder
			ul = ul.replace(/\{collapse\}/g, folder ? ' collapse' : '');

			// collapse
			ul = ul.replace(/\{in\}/g, collapse ? '' : ' in');

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

			var a = '<a class="text-nowrap{active}{collapsed}" href="#"{toggle}{target}{uri}>{text}</a>';
			var text = '<table class="pull-left tree-menu-node-pre" style="{level}"><tr><td><i class="{icon}"></i></td></tr></table>{text}';
			var folderIcon = '<i class="icon-angle-down pull-right icon-large"></i>';
			var badge = '<span class="badge pull-right" style="border-radius: 10px;">{badge}</span>';

			// icon
			text = text.replace(/\{icon\}/g, data.icon ? data.icon : 'icon-th-large');

			// level
			text = text.replace(/\{level\}/g, level && level - 1 > 0 ? 'margin-left: ' + ((level - 1) * 1.3) + 'em; ' : '');

			// text
			text = text.replace(/\{text\}/g, data.text ? data.text : 'menu-' + id);

			// folderIcon
			text = text + (data.children && data.children.length > 0 ? folderIcon : '');

			// badge
			text = text + (data.badge ? badge.replace(/\{badge\}/g, data.badge ? data.badge : '') : '');

			// active
			a = a.replace(/\{active\}/g, data.active ? ' active' : '');

			// collapsed
			a = a.replace(/\{collapsed\}/g, data.collapse ? ' collapsed' : '');

			// toggle
			a = a.replace(/\{toggle\}/g, data.children && data.children.length > 0 ? ' data-toggle="collapse"' : '');

			// target
			a = a.replace(/\{target\}/g, data.children && data.children.length > 0 && id ? ' data-target="#' + id + '"' : '');

			// id
			a = a.replace(/\{uri\}/g, data.uri ? ' data-uri="' + data.uri + '"' : '');

			// text
			a = a.replace(/\{text\}/g, text);

			return a;
		}
	};

	$.fn.treeMenu = function(resource, options) {
		if ($.type(resource) === 'string') {
			resource = $.suredy.loadData(resource);

			if (!resource) {
				return;
			}
		}

		if (resource && !$.isArray(resource))
			resource = [ resource ];

		return this.each(function(i) {
			var $menu = $(this);

			var menu = new SuredyTreeMenu($menu, resource, options);

			// draw tree menu
			menu.make();

			// bind tree menu click event
			$('ul>li>a', $menu).on('click', function(event) {
				var $this = $(this);
				var uri = $this.data('uri');

				// auto collapse
				if (menu.options.autoCollapse && $('+ul', $this).length > 0) {
					$ul = $this.parent('li').parent('ul');
					$('>li>ul.collapse.in', $ul).collapse('toggle');
				}

				if (!uri)
					return true;

				// change active style
				$('li a.active', $menu).removeClass('active');
				$this.addClass('active');

				// load content
				$.suredy.loadContent(uri);

				return true;
			});

			// click left menu
			$('li a.active', $menu).eq(0).trigger('click');
		});
	};
})(jQuery, window, document);
