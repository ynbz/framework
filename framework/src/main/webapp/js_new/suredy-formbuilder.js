require([ 'suredy', 'formBuilder', 'notify' ], function() {
	var messages = {
		rows : '行数',
		addOption : '增加新选项',
		allFieldsRemoved : '成功移除所有表单项',
		allowSelect : '允许选择',
		allowMultipleFiles : '允许上传多个文件',
		autocomplete : '自动完成',
		button : '按钮',
		cannotBeEmpty : '此项不能为空',
		checkboxGroup : '复选框组',
		checkbox : '单个复选框',
		checkboxes : '多个复选框',
		className : '样式表',
		clearAllMessage : '你确定移除所有表单项？',
		clearAll : '清除',
		close : '关闭',
		content : '默认内容',
		copy : '复制到剪切板',
		copyButton : '&#43;',
		copyButtonTooltip : '复制',
		dateField : '日期控件',
		description : '提示信息',
		descriptionField : '描述',
		devMode : '开发人员模式',
		editNames : '编辑名称',
		editorTitle : '表单项',
		editXML : '编辑XML',
		enableOther : '允许“其他”项',
		enableOtherMsg : '允许“其他”项',
		fieldDeleteWarning : false,
		fieldVars : '变量',
		fieldNonEditable : '此项禁止修改',
		fieldRemoveWarning : '你确定要删除此项？',
		fileUpload : '文件上传组件',
		formUpdated : '表单已更新',
		getStarted : '从左侧拖拽控件至此',
		header : '标题',
		hide : '编辑',
		hidden : '隐藏域',
		label : '标签',
		labelEmpty : '标签不能为空',
		limitRole : '适用以下多项角色/权限',
		mandatory : '强制',
		maxlength : '最大长度',
		minOptionMessage : '至少需要两个备选项',
		multipleFiles : '文件选择组件（多选）',
		name : '名称',
		no : '否',
		number : '数字控件',
		off : '关闭',
		on : '打开',
		option : '选项',
		optional : '可选的',
		optionLabelPlaceholder : '填写选项名称',
		optionValuePlaceholder : '填写选项值',
		optionEmpty : '必须填写选项值',
		other : '其他',
		paragraph : '段落',
		placeholder : '占位提示文字',
		placeholders : {
			value : '值',
			label : '标签',
			text : '文本',
			textarea : '文本域',
			email : '电子邮件地址',
			placeholder : '占位提示文字',
			className : '使用空格分割的样式表名称',
			password : '输入密码'
		},
		preview : '预览',
		radioGroup : '单选框组',
		radio : '单选框',
		removeMessage : '移除',
		removeOption : '移除选项',
		remove : '&#215;',
		required : '必填',
		richText : '富文本编辑器',
		roles : '接受',
		save : '保存',
		selectOptions : '选项',
		select : '下拉选择控件',
		selectColor : '颜色选择控件',
		selectionsMessage : '允许多选',
		size : '大小',
		sizes : {
			xs : '特小',
			sm : '小',
			m : '默认',
			lg : '大'
		},
		style : 'CSS样式',
		styles : {
			btn : {
				'default' : '默认',
				danger : '错误',
				info : '信息',
				primary : '主要',
				success : '成功',
				warning : '警告'
			}
		},
		subtype : '类型',
		text : '单行文本域',
		textArea : '多行文本域',
		toggle : '切换',
		warning : '警告',
		value : '值',
		viewJSON : '{  }',
		viewXML : '&lt;/&gt;',
		yes : '是',
	};

	var controlOrder = [ 'text', 'textarea', 'select', 'button', 'date', 'file', 'checkbox-group', 'radio-group', 'checkbox', 'radio' ];
	var disableFields = [ 'autocomplete', 'header', 'paragraph', 'number', 'hidden' ];

	var commonAttr = {
		colWidth : {
			label : '列宽',
			value : 'full',
			options : {
				'full' : '1 / 1',
				'half' : '1 / 2',
				'small' : '1 / 3',
				'big-small' : '2 / 3',
			}
		}
	};

	var typeUserAttrs = {
		text : commonAttr,
		textarea : commonAttr,
		select : commonAttr,
		button : commonAttr,
		date : commonAttr,
		file : commonAttr,
		'checkbox-group' : commonAttr,
		'radio-group' : commonAttr,
		checkbox : commonAttr,
		radio : commonAttr,
	};

	typeUserAttrs.file = $.extend({}, typeUserAttrs.file, {
		fileCount : {
			label : '文件个数限制',
			placeholder : '只能是整数，不填或者小于0为不限制',
		},
		fileType : {
			label : '文件类型限制',
			placeholder : '使用文件后缀，并用空格分割'
		},
		view : {
			label : '默认视图',
			value : 'list',
			options : {
				tiled : '平铺',
				icon : '图标',
				list : '列表',
				detail : '详细'
			}
		},
		changeView : {
			label : '是否可以切换视图',
			value : '1',
			options : {
				'1' : '是',
				'0' : '否'
			}
		},
		addFile : {
			label : '是否可以添加文件',
			value : '1',
			options : {
				'1' : '是',
				'0' : '否'
			}
		},
		editable : {
			label : '是否可以删除文件',
			value : '1',
			options : {
				'1' : '是',
				'0' : '否'
			}
		},
		download : {
			label : '是否可以下载文件',
			value : '1',
			options : {
				'1' : '是',
				'0' : '否'
			}
		},
		autoUpload : {
			label : '是否选择文件后自动上传',
			value : '1',
			options : {
				'1' : '是',
				'0' : '否'
			}
		}
	});

	typeUserAttrs.date = $.extend({}, typeUserAttrs.date, {
		weekstart : {
			label : '默认起始天',
			value : '1',
			options : {
				'1' : '星期一',
				'2' : '星期二',
				'3' : '星期三',
				'4' : '星期四',
				'5' : '星期五',
				'6' : '星期六',
				'0' : '星期日',
			}
		},
		format : {
			label : '格式',
			value : 'yyyy-MM-dd',
			options : {
				'yyyy-MM-dd HH:mm' : '1999-01-01 23:01',
				'yyyy-MM-dd' : '1999-01-01',
				'yyyy-MM' : '1999-01',
				'yyyy' : '1999',
				'HH:mm' : '23:01',
			}
		},
		view : {
			label : '视图',
			value : 'D Y M',
			options : {
				'D Y M T' : '年月日时',
				'D Y M' : '年月日',
				'M Y' : '年月',
				'Y' : '年',
				'M' : '月',
				'D' : '日',
				'T' : '时',
			}
		},
		manualClose : {
			label : '选择后自动关闭控件',
			value : '0',
			options : {
				'0' : '是',
				'1' : '否'
			}
		}
	});

	var fb = $('.suredy-formbuilder').formBuilder({
		dataType : 'json',
		messages : messages,
		controlOrder : controlOrder,
		disableFields : disableFields,
		controlPosition : 'left',
		editOnAdd : true,
		stickyControls : true,
		prefix : 'suredy-form-builder-',
		typeUserAttrs : typeUserAttrs,
		formData : $('.form-define-info').find('[name="formData"]').val(),
	}).data('formBuilder');

	$('.form-define-info').find('[name="formData"]').remove();
	fb.actions.closeAllEdit();

	var _EVENT_SAVE = function(event) {
		var $this = $(this);

		if ($this.hasClass('disabled'))
			return;

		var data = fb.formData;
		var $form = $('.form-define-info');

		$this.addClass('disabled');

		$.ajax({
			url : Suredy.ctxp + '/formbuilder/save',
			data : {
				id : $form.find('[name="id"]').val(),
				enable : $form.find('[name="enable"]').val(),
				name : $form.find('[name="name"]').val(),
				version : $form.find('[name="version"]').val(),
				createTime : $form.find('[name="createTime"]').val(),
				formData : data + '',
				formDesc : $form.find('[name="formDesc"]').val(),
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (!data || !data.success) {
					$.notify({
						title : '<b>错误</b><br>',
						message : (data && data.msg) || '保存失败！'
					}, {
						type : 'danger',
						onClosed : function() {
							$this.removeClass('disabled');
						}
					});
					return;
				}

				$.notify({
					title : '<b>提示</b><br>',
					message : '保存成功！'
				}, {
					delay : 1000,
					type : 'info',
					onClosed : function() {
						if (window.opener) {
							console.log(window.opener.loadDatalist);
							window.opener.loadDatalist();
						}

						var url = Suredy.ctxp + '/formbuilder/open/';
						url += $('body').data('e');
						url += data.data.id;

						window.location.href = url;
					}
				});
			},
			error : function() {
				$.notify({
					title : '<b>错误</b><br>',
					message : '保存失败！'
				}, {
					type : 'danger',
					onClosed : function() {
						$this.removeClass('disabled');
					}
				});
			},
		});
	};

	// 保存表单定义数据
	$('.suredy-formbuilder').on('click', '.suredy-form-builder-save', _EVENT_SAVE);
	$('.save-form-data').on('click', _EVENT_SAVE);

	// 切换视图
	$('.btn.change-view').on('click', function() {
		fb.actions.closeAllEdit();
		$('.formediter').toggleClass('editing');

		var $body = $('body');

		if ($body.data('e') === 1) {
			$body.data('e', 0);
		} else {
			$body.data('e', 1);
		}
	});

	// 监听表单名称改变事件
	$('[name="name"]').on('keyup', function() {
		$('title').text($(this).val());
	});
});