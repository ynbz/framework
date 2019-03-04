;
(function(global, factory) {

	if (typeof define === 'function' && define.amd) {
		// 添加到amd中，并加载依赖
		define([ 'jquery', 'suredy' ], function(_$, _$d) {
			return factory(global, _$, _$d);
		});
	} else if (!global.jQuery || !global.Suredy) {
		throw new Error("Suredy.Modal requires a jQuery and a Suredy");
	} else {
		factory(global, global.jQuery, global.Suredy);
	}

})(this, function(window, $, $d, undefined) {

	var template = '<div class="modal fade suredy-modal" tabindex="-1">\
		  <div class="modal-dialog">\
		    <div class="modal-content">\
		      <div class="modal-header bg-primary">\
		        <div class="btn btn-danger btn-sm pull-right icon-remove" data-dismiss="modal" aria-label="Close"></div>\
		        <h4 class="modal-title"></h4>\
		      </div>\
		      <div class="modal-body"></div>\
		      <div class="modal-footer">\
				<button type="button" class="btn btn-primary btn-sm btn-ok"></button>\
				<button type="button" class="btn btn-default btn-sm btn-cancel" data-dismiss="modal"></button>\
		      </div>\
		    </div>\
		  </div>\
		</div>';

	var Modal = function(options) {
		this.options = $.extend({
			size : 'normal',// lg sm normal
			showHead : true,
			showFoot : true,
			showBody : true,
			showOkBtn : true,
			showCancelBtn : true,
			ignoreBackgroundClick : true,
			backdrop : true,
			keyboard : true,
			icon : 'icon-exclamation-sign',
			title : '系统消息',
			okText : '确定',
			cancelText : '关闭',
			text : '系统消息',
			uri : '',
			data : undefined,
			okClick : function() {
			}
		}, options);

		var me = this;

		// remove old
		$('body>div.modal.suredy-modal').remove();

		// new one
		$('body').append(template);

		this.modal = $('body>div.modal.suredy-modal');

		// bind ok btn click
		$('div.modal-footer .btn.btn-ok', this.modal).on('click', function(event) {
			me.options.okClick.call(this);
		});

		// bind hidden event
		this.modal.on('hidden.bs.modal', function(event) {
			$(this).remove();
		});

		// bind show event
		this.modal.on('shown.bs.modal', function(event) {
			me.modal.height(me.modal.height());
		});
	};

	Modal.prototype = {
		show : function() {
			var me = this;

			this.create().modal.modal({
				backdrop : me.options.backdrop,
				keyboard : me.options.keyboard,
				remote : false
			});

			if (me.options.ignoreBackgroundClick) {
				$('.modal').unbind('click');

				// bind close btn click event
				$('.modal .modal-header .btn.icon-remove').on('click', function() {
					$d.Modal.closeModal();
				});

				// bind close btn click event
				$('.modal .modal-footer .btn.btn-cancel').on('click', function() {
					$d.Modal.closeModal();
				});
			}

			return this.modal;
		},
		create : function() {
			var $dialog = $('div.modal-dialog', this.modal);

			// size
			$dialog.removeClass('modal-lg').removeClass('modal-sm');
			if (this.options.size === 'lg')
				$dialog.addClass('modal-lg');
			else if (this.options.size === 'sm')
				$dialog.addClass('modal-sm');

			// head
			this.makeHead();

			// foot
			this.makeFoot();

			// content
			this.makeBody();

			// only mask
			if (!this.options.showHead && !this.options.showBody && !this.options.showFoot) {
				$('div.modal-content', $dialog).addClass('hidden');
			}

			return this;
		},
		makeHead : function() {
			var $head = $('div.modal-header', this.modal);
			if (!this.options.showHead) {
				$head.addClass('hidden');
				return;
			}

			// icon and title
			var title = '<i class="' + this.options.icon + '">&nbsp;</i>' + this.options.title;
			$('h4.modal-title', $head).html(title);
		},
		makeFoot : function() {
			var $foot = $('div.modal-footer', this.modal);

			if (!this.options.showFoot) {
				$foot.addClass('hidden');
				return;
			}

			// ok btn
			if (!this.options.showOkBtn)
				$('.btn.btn-ok', $foot).addClass('hidden');
			else
				$('.btn.btn-ok', $foot).text(this.options.okText);

			// cancel btn
			if (!this.options.showCancelBtn)
				$('.btn.btn-cancel', $foot).addClass('hidden');
			else
				$('.btn.btn-cancel', $foot).text(this.options.cancelText);

		},
		makeBody : function() {
			var $body = $('div.modal-body', this.modal);

			if (!this.options.showBody) {
				$body.addClass('hidden');
				return;
			}

			// text
			$body.html(this.options.text);

			// load uri content
			if (this.options.uri)
				$d.loadContent({
					url : this.options.uri,
					container : $body,
					postData : this.options.data
				});
		}
	};

	$d.Modal = {
		showModal : function(options) {
			return new Modal(options).show();
		},

		closeModal : function() {
			$('body>div.modal.suredy-modal').modal('hide');
		},

		reload : function(uri, params) {
			$d.loadContent({
				url : uri,
				container : $('body>div.modal.suredy-modal div.modal-body'),
				postData : params
			});
		},

		showProcessDialog : function(options) {
			$d.Modal.showModal($.extend({
				ignoreBackgroundClick : false
			}, options, {
				showHead : false,
				showFoot : false,
				showOkBtn : false,
				showCancelBtn : false,
				backdrop : true,
				text : (options && options.text) || '<div class="text-danger"><i class="icon-spinner icon-spin"></i>&nbsp;系统处理中，请稍候……</div>'
			}));
		},

		closeProcessDialog : function() {
			$d.Modal.closeModal();
		},

		showMask : function(options) {
			$d.Modal.showModal($.extend({
				ignoreBackgroundClick : false
			}, options, {
				showHead : false,
				showFoot : false,
				showBody : false,
				backdrop : true
			}));
		},

		closeMask : function() {
			$d.Modal.closeModal();
		}
	};

	// 返回Suredy对象
	return $d.Modal;

});