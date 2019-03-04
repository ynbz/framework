;
(function(global, factory) {

	if (typeof define == 'function' && define.amd) {
		define([ 'suredy', 'jqueryForm' ], function(Suredy) {
			return factory(global, global.jQuery, Suredy);
		});
	} else if (!global.jQuery || !global.Suredy) {
		throw new Error("Suredy.FileManager requires a jQuery, a Suredy and a jquery.form plugin.");
	} else if (!global.jQuery.isFunction(global.jQuery.fn.ajaxSubmit)) {
		throw new Error("Suredy.FileManager requires a jquery.form plugin.");
	} else {
		factory(global, global.jQuery, global.Suredy);
	}

})(window, function(window, $, Suredy, undefined) {
	'use strict';

	var FileManager = function(element, options) {
		this.type = null;
		this.options = null;
		this.$element = null;

		this.init('filemanager', element, options);

		// bind file item added event
		this.$element.on('added.suredy.' + this.type, $.proxy(function(event, params) {
			if (!params || !params.$item)
				throw Error('Need a object parameter with key: [$item].');

			var that = this;
			var $items = params.$item;

			$items.each(function(i, dom) {
				var $item = $(dom);
				var $file = $item.find('>input[type="file"]');
				// no file for upload
				if (!$file.length) {
					that.toUploadedStyle($item);
					return true;
				}
				
				

				// change style
				that.toUploadStyle($item);
			});

			// trigger upload event
			if (this.autoUpload())
				this.$element.trigger('upload.suredy.' + that.type, {
					$item : $items
				});

			return true;
		}, this));

		// bind file upload event
		this.$element.on('upload.suredy.' + this.type, $.proxy(function(event, params) {
			if (!params || !params.$item)
				throw Error('Need a object parameter with key: [$item].');

			var that = this;
			var $items = params.$item;

			$items.each(function(i, dom) {
				var $item = $(dom);

				// uploading
				if ($item.hasClass('uploading'))
					return true;

				var $file = $item.find('>input[type="file"]').attr('name', that.fieldName());

				if (!$file.length)
					return true;

				// change style
				that.toUploadingStyle($item);

				$file.wrap('<form enctype="multipart/form-data" method="post"></form>');

				var $form = $file.parent();

				$form.attr('action', that.uploadUrl());

				// 异步提交文件
				$form.ajaxSubmit({
					data : {
						extendData : that.extendData(),
					},
					dataType : 'json',
					clearForm : false, // 完毕之后禁止清空表单
					// iframe : false, 组件自适应浏览器
					success : function(data, textStatus, jqXHR) {
						// recover
						$file.unwrap().removeAttr('name');

						var success = data && !!data.success;
						var itemData = {};

						if (!success) {
							that.toUploadStyle($item); // change style
							that.ERR_MESSAGE((data && data.msg) || '文件上传失败，请重试！'); // alert
						} else {
							that.toUploadedStyle($item); // change style
							$file.remove(); // remove dom
							that.bindDataToItem($item, itemData = data.data[that.fieldName()]); // bind data
						}

						$item.trigger('uploaded.suredy.' + that.type, {
							success : success,
							data : itemData
						});
					},
					error : function(jqXHR, textStatus, errorThrown) {
						// trigger event
						$item.trigger('uploaded.suredy.' + that.type, {
							success : false
						});

						// recover
						$file.unwrap().removeAttr('name');

						that.toUploadStyle($item); // change style

						if (jqXHR.status == 400)
							that.ERR_MESSAGE('提交的数据错误，请重试！');
						else if (jqXHR.status == 404)
							that.ERR_MESSAGE('未能找到文件上传服务，请重试！');
						else if (jqXHR.status == 500)
							that.ERR_MESSAGE('服务器异常，无法上传文件，请重试！');
						else if (textStatus == 'timeout')
							that.ERR_MESSAGE('向服务器发送文件超时，请重试！');
						else if (textStatus == 'error')
							that.ERR_MESSAGE('服务器异常，请重试！');
						else if (textStatus == 'abort')
							that.ERR_MESSAGE('上传文件被中止，请重试！');
						else if (textStatus == 'parsererror')
							that.ERR_MESSAGE('服务器无法响应文件上传请求，请重试！');
						else
							that.ERR_MESSAGE('文件上传失败，错误未知，请重试！');
					},
					beforeSend : function(jqXHR, settings) {
						$item.trigger('preupload.suredy.' + that.type);
					}
				});
			});

			return true;
		}, this));

		// bind remove item btn click event
		this.$element.on('delete.suredy.' + this.type, $.proxy(function(event, params) {
			if (!params || !params.$item)
				throw Error('Need a object parameter with key: [$item].');

			var that = this;
			var $items = params.$item;

			$items.each(function(i, dom) {
				var $item = $(dom);
				var $removeBtn = $item.find('>.remove');

				if (!$removeBtn.length || $removeBtn.hasClass('disabled'))
					return true;

				if ($item.hasClass('uploaded') && !that.editable())
					return true;

				$removeBtn.addClass('disabled');

				var itemData = $item.data('suredy.' + that.type + '.data.item');

				// not upload file to server
				if (!itemData || !itemData.id) {
					$item.remove();
					return true;
				}

				// delete file on server then remove file item
				var fileId = itemData.id;

				$.ajax({
					url : Suredy.ctxp + '/file/remove',
					data : {
						fileId : fileId
					},
					type : 'post',
					dataType : 'json',
					success : function(data, textStatus, jqXHR) {
						var success = data && !!data.success;

						if (!success) {
							that.ERR_MESSAGE((data && data.msg) || '无法移除文件，请重试！');
							$removeBtn.removeClass('disabled');
						} else {
							$item.remove(); // remove the file item
						}

						that.$element.trigger('deleted.suredy.' + that.type, {
							success : success,
							data : itemData,
							$item : success ? $() : $item
						});
					},
					error : function(jqXHR, textStatus, errorThrown) {
						that.$element.trigger('deleted.suredy.' + that.type, {
							success : false,
							data : itemData,
							$item : $item
						});

						that.ERR_MESSAGE('无法移除文件，请重试！' + errorThrown);
						$removeBtn.removeClass('disabled');
					},
					beforeSend : function(jqXHR, settings) {
						that.$element.trigger('predelete.suredy.' + that.type, {
							data : itemData,
							$item : $item
						});
					}
				});
			});

			return true;
		}, this));

		// download event
		this.$element.on('download.suredy.' + this.type, $.proxy(function(event, params) {
			if (!params || !params.$item)
				throw Error('Need a object parameter with key: [$item].');

			if (!this.downloadable())
				return true;

			var that = this;
			var $items = params.$item;

			$items.each(function(i, dom) {
				var $item = $(dom);
				var data = $item.data('suredy.' + that.type + '.data.item');

				// invalid item data
				if (!data || !data.id)
					return true;

				var url = Suredy.ctxp + '/file/download?fileId=' + data.id;

				window.open(url);
			});
		}, this));
	};

	FileManager.DEFAULTS = {
		// 基本参数
		fileCount : -1, // [data-file-count]. <=0 means not limit.
		fileType : '', // [data-file-type]. limit the file types(split by ','). empty means not limit.
		view : 'list', // [data-view]. current view. [tiled, icon, list, detail]
		changeView : true, // [data-change-view]. show change view btn
		addFile : true, // [data-add-file]. show add file btn
		editable : true,// [data-editable]. can remove uploaded file
		download : true, // [data-download]. can download file

		// 组件上传参数
		autoUpload : true, // [data-auto-upload]. auto upload.
		fieldName : 'file', // [data-field-name]. input[type="file"]'s attr 'name'.
		extendData : '', // [data-extend-data]. append this data inon DB.
	};

	FileManager.FileModel = function() {
		return {
			id : '',
			fileName : '',
			fileSize : '',
			fileTime : '',
			filePath : '',
			fileType : '',
		};
	};

	// 文件图标映射
	FileManager.FILE_ICON_DEFINE = {
		'suredy' : 'suredy-file-icon'
	};

	FileManager.prototype.init = function(type, element, options) {
		this.type = type;
		this.$element = $(element);
		this.options = this.getOptions(options);

		// clean
		this.$element.empty();

		// fix class
		this.$element.addClass('suredy-file');

		// fix view
		this.$element.addClass(this.view());

		// bind data
		this.$element.data('suredy.' + this.type, this);

		// off event
		this.$element.off('.suredy' + this.type);

		// draw component
		this.draw();
	};

	FileManager.prototype.fileCount = function() {
		var count = this.options.fileCount;

		// set default val
		if (!$.isNumeric(count) || count < 1)
			count = this.options.fileCount = -1;

		return Math.floor(count);
	};

	FileManager.prototype.fileType = function() {
		var type = this.options.fileType;

		// set default val
		if (typeof type != 'string' || !(type = $.trim(type.replace(/\s|\./g, ''))))
			type = this.options.fileType = '';

		return type.toUpperCase();
	};

	FileManager.prototype.view = function() {
		var view = this.options.view;

		// set default val
		if (typeof view != 'string' || !(view = $.trim(view)))
			view = this.options.view = 'list';

		// must in array: [tiled, icon, list, detail]
		if ($.inArray(view, [ 'tiled', 'icon', 'list', 'detail' ]) == -1)
			view = this.options.view = 'list';

		return view;
	};

	FileManager.prototype.canChangeView = function() {
		return (this.options.changeView = !!this.options.changeView);
	};

	FileManager.prototype.canAddFile = function() {
		return (this.options.addFile = !!this.options.addFile);
	};

	FileManager.prototype.editable = function() {
		return (this.options.editable = !!this.options.editable);
	};

	FileManager.prototype.downloadable = function() {
		return (this.options.download = !!this.options.download);
	};

	FileManager.prototype.autoUpload = function() {
		return (this.options.autoUpload = !!this.options.autoUpload);
	};

	FileManager.prototype.fieldName = function() {
		var field = this.options.fieldName;

		if (typeof field != 'string' || !(field = $.trim(field)))
			field = this.options.fieldName = 'file';

		return field;
	};

	FileManager.prototype.extendData = function() {
		var data = this.options.extendData;

		// set default val
		if (typeof data != 'string' || !(data = $.trim(data)))
			data = this.options.extendData = '';

		return data;
	};

	FileManager.prototype.uploadUrl = function() {
		return Suredy.ctxp + '/file/upload';
	};

	FileManager.prototype.getDefaults = function() {
		return FileManager.DEFAULTS;
	};

	FileManager.prototype.getOptions = function(options) {
		options = $.extend({}, this.getDefaults(), options, this.$element.data());

		return options;
	};

	FileManager.prototype.draw = function() {
		// draw change view button
		if (this.canChangeView())
			this.drawChangeViewBtn();

		// draw add file button
		if (this.canAddFile())
			this.drawAddFileBtn();
	};

	FileManager.prototype.drawChangeViewBtn = function() {
		var $btnGroup = $('<div class="btn-group change-view"></div>').appendTo(this.$element);

		// change btn
		var $changeBtn = $('<div class="btn btn-info btn-xs dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></div>');
		$changeBtn.appendTo($btnGroup).append('<div class="glyphicon glyphicon-option-vertical"></div>');

		// menu
		var $menu = $('<ul class="dropdown-menu"></ul>');
		$btnGroup.append($menu);

		// tiled
		var $tiled = $('<li><a href="javascript:;"><i class="glyphicon glyphicon-th" style="color: #c9302c;"></i>&nbsp;&nbsp;平铺</a></li>');
		// icon
		var $icon = $('<li><a href="javascript:;"><i class="glyphicon glyphicon-picture" style="color: #c9302c;"></i>&nbsp;&nbsp;图标</a></li>');
		// list
		var $list = $('<li><a href="javascript:;"><i class="glyphicon glyphicon-list" style="color: #c9302c;"></i>&nbsp;&nbsp;列表</a></li>');
		// detail
		var $detail = $('<li><a href="javascript:;"><i class="glyphicon glyphicon-stats" style="color: #c9302c;"></i>&nbsp;&nbsp;详细</a></li>');

		// change btn click event
		var CLICK_EVENT = function(view, event) {
			this.$element.removeClass(this.view());
			this.$element.addClass(view);
			this.options.view = view;
		};

		// bind event
		$tiled.appendTo($menu).on('click', $.proxy(CLICK_EVENT, this, 'tiled'));
		$icon.appendTo($menu).on('click', $.proxy(CLICK_EVENT, this, 'icon'));
		$list.appendTo($menu).on('click', $.proxy(CLICK_EVENT, this, 'list'));
		$detail.appendTo($menu).on('click', $.proxy(CLICK_EVENT, this, 'detail'));
	};

	FileManager.prototype.drawAddFileBtn = function() {
		var $addBtn = $('<label class="file-item add-file"><input type="file"></label>');

		$addBtn.appendTo(this.$element);

		// add icon
		$addBtn.append('<div class="file-icon" title="上传新文件"><div class="icon-plus-sign-alt"></div></div>');

		// add text
		$addBtn.append('<div class="file-info"><div class="file-name">上传新文件…</div></div>');

		// bind btn click event
		$addBtn.on('click', $.proxy(function(event) {
			var count = this.fileCount();

			// not limit
			if (count < 0)
				return true;

			var rest = count - this.$element.find('.file-item.file').length;

			// can add file
			if (rest > 0)
				return true;

			this.ERR_MESSAGE('最多只能上传【' + count + '】个文件！<br>目前已上传' + (count - rest) + '个文件', '『操作失败』');

			return false;
		}, this));

		// bind file selected event
		$addBtn.on('change', '>input[type="file"]', $.proxy(function($btn, event) {
			var $file = $btn.find('>input[type="file"]');

			$file.remove(); // 移除原有的file域
			$btn.prepend('<input type="file">'); // 添加新的file域

			if (!$file.val())
				return false;

			var fileName = Suredy.fileNameFromPath($file.val());

			if (!fileName || !this.isTypeAllowed(fileName.suffix)) {
				this.ERR_MESSAGE('文件类型无效，只能上传类型为【' + this.fileType().toLowerCase().replace(/,/g, '，') + '】的文件', '『操作失败』');
				return false;
			}

			var $item = this.appendFileItemAndBindData({
				id : '',
				fileName : fileName.name + fileName.suffix,
				fileSize : '处理中…',
				fileTime : '处理中…',
				filePath : '处理中…',
				fileType : fileName.suffix,
			});

			if (!$item)
				throw new Error('Failed to append a new file item.');

			// add file dom into item dom
			$item.prepend($file);

			// event[suredy.filemanager.event.item.added]
			this.$element.trigger('added.suredy.' + this.type, {
				$item : $item,
			});
		}, this, $addBtn));
	};

	FileManager.prototype.appendFileItemAndBindData = function(data) {
		// append file item
		var $item = this.appendFileItem();

		if (!$item)
			throw new Error('Failed to append a new file item.');

		// bind data to item
		return this.bindDataToItem($item, data);
	};

	FileManager.prototype.bindDataToItem = function($item, data) {
		$item.data('suredy.' + this.type + '.data.item', data);

		var $info = $item.find('>.file-info');

		$info.find('>.file-name').text(data.fileName || '未知');
		$info.find('>.file-time').text(data.fileTime || '未知');
		$info.find('>.file-type').text((data.fileType || '未知').toUpperCase());
		$info.find('>.file-size').text(data.fileSize || '未知');

		return $item;
	};

	FileManager.prototype.appendFileItem = function() {
		var that = this;
		var $item = $('<div class="file-item file"></div>');
		var $addBtn = this.$element.find('>.file-item.add-file');

		// append item
		if ($addBtn.length)
			$addBtn.before($item);
		else
			this.$element.append($item);

		// icon
		$item.append('<div class="file-icon"><div class="icon-file-text-alt"></div></div>');

		// info
		var $info = $('<div class="file-info"></div>').appendTo($item);
		$info.append('<div class="file-name"></div>');
		$info.append('<div class="file-time"></div>');
		$info.append('<div class="file-type"></div>');
		$info.append('<div class="file-size"></div>');

		// process
		var $process = $('<div class="process-bar"></div>').appendTo($item);
		$process.append('<div class="process-icon"><i class=""></i></div>');
		$process.append('<div class="process-info"></div>');

		// remove btn
		var $remove = $('<div class="remove btn btn-danger btn-xs"></div>').appendTo($item);
		$remove.append('<i class="glyphicon glyphicon-remove"></i>');

		// bind manual upload click
		$process.on('click', function(event) {
			// only .upload can be click for manual upload
			if (!$item.hasClass('upload'))
				return true;

			that.$element.trigger('upload.suredy.' + that.type, {
				$item : $item
			});

			return true;
		});

		// bind remove btn click
		$remove.on('click', function(event) {
			if ($remove.hasClass('disabled'))
				return true;

			that.$element.trigger('delete.suredy.' + that.type, {
				$item : $item
			});
		});

		// bind download click
		if (this.downloadable())
			$info.find('>.file-name').addClass('download').on('click', function(event) {
				that.$element.trigger('download.suredy.' + that.type, {
					$item : $item
				});
			});

		return $item;
	};

	FileManager.prototype.isTypeAllowed = function(type) {
		if (typeof type != 'string')
			return false;

		var typeLimit = this.fileType();

		// not limit
		if (!typeLimit)
			return true;

		type = $.trim(type).toUpperCase().replace(/^[\.]*|[\.]*$/g, '');

		var tls = typeLimit.split(',');

		return $.inArray(type, tls) != -1;
	};

	FileManager.prototype.toUploadStyle = function($item) {
		var $process = $item.find('>.process-bar');

		$item.removeClass('upload').removeClass('uploading').removeClass('uploaded');

		$item.addClass('upload');

		$process.find('>.process-icon>i').removeClass().addClass('icon-upload');
		$process.find('>.process-info').text('点击上传');
	};

	FileManager.prototype.toUploadingStyle = function($item) {
		var $process = $item.find('>.process-bar');

		$item.removeClass('upload').removeClass('uploading').removeClass('uploaded');

		$item.addClass('uploading');

		$process.find('>.process-icon>i').removeClass().addClass('icon-spinner icon-spin');
		$process.find('>.process-info').text('上传中…');
	};

	FileManager.prototype.toUploadedStyle = function($item) {
		var $process = $item.find('>.process-bar');

		$item.removeClass('upload').removeClass('uploading').removeClass('uploaded');

		$item.addClass('uploaded');

		$process.find('>.process-icon>i').removeClass();
		$process.find('>.process-info').text('');

		if (!this.editable())
			$item.find('>.remove').remove();
	};

	FileManager.prototype.items = function(select) {
		if (typeof select != 'string')
			select = '>.file-item.file';

		return this.$element.find(select);
	};

	FileManager.prototype.uploadItems = function() {
		return this.items('>.file-item.file.upload');
	};

	FileManager.prototype.uploadingItems = function() {
		return this.items('>.file-item.file.uploading');
	};

	FileManager.prototype.uploadedItems = function() {
		return this.items('>.file-item.file.uploaded');
	};

	FileManager.prototype.ERR_MESSAGE = function(messag, title) {
		if (typeof messag != 'string')
			messag = '文件管理出错，请联系管理员！';

		title = (!title || typeof title != 'string') ? '错误' : title;

		$.notify({
			title : '<b>' + title + '</b><br>',
			message : messag
		}, {
			type : 'danger'
		});
	};

	var _plugin = function(options) {
		return this.each(function(i, dom) {
			var $this = $(dom);
			var component = $this.data('suredy.filemanager');

			if (!component)
				new FileManager($this, options);
		});
	};

	// =====================================================================

	// 定义Suredy.File对象
	var File = Suredy.File = function(element, options) {
		return _plugin.call($(element), options);
	};

	File.plugin = function(element) {
		var $element = $(element);

		// no file manage dom be founded.
		if (!$element)
			return $(); // return an empty jQuery obj

		return $element.data('suredy.filemanager');
	};

	File.itemData = function(item) {
		var $item = $(item);

		if (!$item.length)
			return null;

		return $item.eq(0).data('suredy.filemanager.data.item');
	};

	File.items = function(element) {
		var plugin = File.plugin(element);

		if (!plugin)
			return $(); // return an empty jQuery obj

		return plugin.items();
	};

	File.uploadItems = function(element) {
		var plugin = File.plugin(element);

		if (!plugin)
			return $(); // return an empty jQuery obj

		return plugin.uploadItems();
	};

	File.uploadingItems = function(element) {
		var plugin = File.plugin(element);

		if (!plugin)
			return $(); // return an empty jQuery obj

		return plugin.uploadingItems();
	};

	File.uploadedItems = function(element) {
		var plugin = File.plugin(element);

		if (!plugin)
			return $(); // return an empty jQuery obj

		return plugin.uploadedItems();
	};

	File.addFile = function(element, fileModel) {
		var plugin = File.plugin(element);

		if (!plugin)
			return;

		var $item = plugin.appendFileItemAndBindData(fileModel);

		plugin.toUploadedStyle($item);

		return $item;
	};

	File.uploadAll = function(element, callback) {
		var plugin = File.plugin(element);

		if (!plugin)
			return;

		var $items = File.uploadItems(element);
		var len = $items.length;

		if (!len)
			return;

		// do upload
		plugin.$element.trigger('upload.suredy.filemanager', {
			$item : $items
		});

		if (!$.isFunction(callback))
			return;

		var data = {
			ok : [],
			failed : []
		};

		// run callback
		$items.each(function(i, dom) {
			var $this = $(this);

			$this.one('uploaded.suredy.filemanager', function(event, result) {
				if (len-- <= 0)
					return;

				var d = result.success ? data.ok : data.failed;

				d[d.length] = {
					$item : $this,
					data : result.data
				};

				if (len <= 0)
					callback.call(null, data);
			});
		});
	};

	File.removeAll = function(element, callback) {
		var plugin = File.plugin(element);

		if (!plugin)
			return;

		var $items = File.uploadedItems(element);
		var len = $items.length;

		if (!len)
			return;

		// do remove
		plugin.$element.trigger('delete.suredy.filemanager', {
			$item : $items
		});
	};

	File.Constructor = FileManager;

	return File;
});
