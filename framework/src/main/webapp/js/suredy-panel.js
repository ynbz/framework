(function($, window, document, undefined) {
	var panel = function(panel) {
		this.$ele = panel;
	};

	panel.prototype = {
		count : function() {
			if (this.$ele.length <= 0)
				return;

			var content = $('.suredy-widget-panel-datacount', this.$ele);
			var count = content.data('count');

			if (count) {
				$('.badge.suredy-widget-panel-count', this.$ele).html(count);
			}
		},
		load : function() {
			if (this.$ele.length <= 0)
				return;

			$('.badge.suredy-widget-panel-count', this.$ele).html(0);

			var uri = this.$ele.data('uri');

			if (uri) {
				var that = this;

				$.suredy.loadContent(uri, $('.suredy-widget-panel-body', this.$ele), function() {
					that.count();
				});
			}
		}
	};

	$.fn.panel = function() {
		return this.each(function() {
			(new panel($(this))).load();
		});
	};
})(jQuery);

$(function() {
	$('.suredy-widget-panel').panel();

	$('.suredy-widget-panel').delegate('.suredy-widget-panel-refresh', 'click', function(event) {
		$(this).parent().parent().panel();
	});
});