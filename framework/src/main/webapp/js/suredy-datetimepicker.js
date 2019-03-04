/**
 * This is datetime picker plugin
 * 
 * @author VIVID.G
 * @since 2015-6-30
 * @version v0.1
 */
/*
 * 日期格式:
 * 
 * yyyy/yy - 2015/15
 * 
 * MM/M - 01/12
 * 
 * dd/d - 01/30
 * 
 * hh/h - 01/12
 * 
 * HH/H - 01/24
 * 
 * mm/m - 01/59
 * 
 * ss/s - 01/59
 * 
 * a - am/pm
 */
(function($, window, document, undefined) {
	$.suredy = $.extend({}, $.suredy);

	var hideAll = function() {
		$('.datetimepicker').popover('destroy');
	};

	var week = [ '日', '一', '二', '三', '四', '五', '六' ];

	var SuredyDatetimePicker = function($ele, options) {
		this.ele = $ele;
		this.options = $.extend({
			head : true,
			content : true,
			foot : true,
			current : true,
			weekstart : 1, // start of week. 0=sundy...6=Saturday
			format : 'y-M-d H:m',
			time : new Date()
		}, options);

		this.time = function(time) {
			if (!time)
				return new Date();

			if (time instanceof Date)
				return time;

			if (typeof time === 'string') {
				time = time.replace(/\-/g, '\/');

				var tmp = new Date(time) || new Date();

				return tmp;
			}

			return new Date();
		}(this.options.time);
	};

	SuredyDatetimePicker.prototype = {
		id : function() {
			return this.ele.attr('aria-describedby');
		},
		make : function() {
			this.ele.popover({
				animation : true,
				// container : 'body',
				content : this.makeHtml(this.time),
				html : true,
				placement : 'bottom',
				trigger : 'manual',
				template : '<div class="popover suredy-datetimepicker" role="tooltip"><div class="arrow"></div><div class="popover-content padding-0"></div></div>'
			});

			return this;
		},
		makeHtml : function() {
			var html = '<table class="content">{head}{content}{foot}</table>';

			// head
			html = html.replace(/\{head\}/g, this.makeHead());

			// content
			html = html.replace(/\{content\}/g, this.makeContent());

			// foot
			html = html.replace(/\{foot\}/g, this.makeFoot());

			return html;
		},
		makeHead : function() {
			if (!this.options.head)
				return '';

			var head = '<tr class="head"><td class="text-center" colspan="7">{icon}{year}{month}</td></tr>';

			// icon
			head = head.replace(/\{icon\}/g, '<i class="icon-calendar">&nbsp;</i>');

			// year
			head = head.replace(/\{year\}/g, this.makeYear());

			// month
			head = head.replace(/\{month\}/g, this.makeMonth());

			return head;
		},
		makeContent : function() {
			if (!this.options.content)
				return '';

			var content = this.makeContentTitle();

			content += this.makeContentDate();

			return content;
		},
		makeFoot : function() {
			var foot = '<tr class="foot"><td class="text-center" colspan="7">{icon}{hour}{minute}{tody}{clean}</td></tr>';

			// icon
			foot = foot.replace(/\{icon\}/g, this.options.foot ? '&nbsp;<i class="icon-time"></i>' : '');

			// hour
			foot = foot.replace(/\{hour\}/g, this.options.foot ? this.makeHour() : '');

			// minute
			foot = foot.replace(/\{minute\}/g, this.options.foot ? this.makeMinute() : '');

			// tody
			foot = foot.replace(/\{tody\}/g, '<div class="btn btn-link btn-xs today">今天</div>');

			// clean
			foot = foot.replace(/\{clean\}/g, '<div class="btn btn-danger btn-xs clean">清除</div>');

			return foot;
		},
		makeYear : function() {
			var year = '<select class="year border-0">{options}</select>';
			var time = this.time || new Date();

			var options = '';
			for ( var y = time.getFullYear() + 3; y >= time.getFullYear() - 10; y--) {
				var opt = '<option value="{y}"{selected}>{y}年</option>';

				// y
				opt = opt.replace(/\{y\}/g, y);

				// selected
				opt = opt.replace(/\{selected\}/g, y === time.getFullYear() ? ' selected' : '');

				options += opt;
			}

			// options
			year = year.replace(/\{options\}/g, options);

			return year;
		},
		makeMonth : function() {
			var month = '<select class="month border-0">{options}</select>';
			var time = this.time || new Date();

			var options = '';
			for ( var m = 0; m < 12; m++) {
				var opt = '<option value="{m}"{selected}>{m}月</option>';

				// o
				opt = opt.replace(/\{m\}/g, m + 1);

				// selected
				opt = opt.replace(/\{selected\}/g, m === time.getMonth() ? ' selected' : '');

				options += opt;
			}

			// options
			month = month.replace(/\{options\}/g, options);

			return month;
		},
		makeHour : function(time) {
			var hour = '<select class="hour border-0">{options}</select>';
			var time = this.time || new Date();

			var options = '';
			for ( var h = 0; h < 24; h++) {
				var opt = '<option value="{h}"{selected}>{h}时</option>';

				// h
				opt = opt.replace(/\{h\}/g, h);

				// selected
				opt = opt.replace(/\{selected\}/g, h === time.getHours() ? ' selected' : '');

				options += opt;
			}

			// options
			hour = hour.replace(/\{options\}/g, options);

			return hour;
		},
		makeMinute : function(time) {
			var minute = '<select class="minute border-0">{options}</select>';
			var time = this.time || new Date();

			var options = '';
			for ( var m = 0; m < 60; m++) {
				var opt = '<option value="{m}"{selected}>{m}分</option>';

				// m
				opt = opt.replace(/\{m\}/g, m);

				// selected
				opt = opt.replace(/\{selected\}/g, m === time.getMinutes() ? ' selected' : '');

				options += opt;
			}

			// options
			minute = minute.replace(/\{options\}/g, options);

			return minute;
		},
		makeContentTitle : function() {
			var title = '<tr class="bg-info content-title">{weeks}</tr>';

			var weeks = '';
			var html = '<th class="text-center">{week}</th>';
			for ( var w = this.options.weekstart; w >= 0 && w < 7; w++) {
				var tmp = html;
				// week
				tmp = tmp.replace(/\{week\}/g, week[w]);

				weeks += tmp;
			}
			for ( var w = 0; w < this.options.weekstart && w >= 0 && w < 7; w++) {
				var tmp = '<th class="text-center">{week}</th>';

				// week
				tmp = tmp.replace(/\{week\}/g, week[w]);

				weeks += tmp;
			}

			// weeks
			title = title.replace(/\{weeks\}/g, weeks);

			return title;
		},
		makeContentDate : function() {
			var now = new Date();
			var time = this.time || new Date();

			var year = time.getFullYear();
			var month = time.getMonth();
			var date = time.getDate();

			var nowYear = now.getFullYear();
			var nowMonth = now.getMonth();
			var nowDate = now.getDate();

			var start = new Date(time.getTime());
			start.setDate(1); // 设置为1号

			// 获取日历开始的天
			while (start.getDay() != this.options.weekstart)
				start.setDate(start.getDate() - 1);

			var day = '';
			do {
				var tds = '';
				for ( var i = 0; i < 7; i++) {
					var y = start.getFullYear();
					var m = start.getMonth();
					var d = start.getDate();

					td = '<td class="text-center date{other}{current}{selected}" data-date="{data}">{date}</td>';

					// other
					td = td.replace(/\{other\}/g, m !== month ? ' other' : '');

					// current
					td = td.replace(/\{current\}/g, function() {
						if (nowYear === y && nowMonth === m && nowDate === d)
							return ' current';

						return '';
					});

					// selected
					td = td.replace(/\{selected\}/g, function() {
						if (year === y && month === m && date === d)
							return ' selected';

						return '';
					});

					// data
					td = td.replace(/\{data\}/g, y + '/' + (m + 1) + '/' + d);

					// date
					td = td.replace(/\{date\}/g, function() {
						if (nowYear === y && nowMonth === m && nowDate === d)
							return '今';

						return d;
					});

					tds += td;

					start.setDate(start.getDate() + 1);
				}

				if (tds)
					day += '<tr>' + tds + '</tr>';
			} while (start.getMonth() === month);

			return day;
		},
		show : function() {
			this.make();
			this.ele.popover('toggle');
		},
		hide : function() {
			this.ele.popover('destroy');
		},
		reDraw : function() {
			var html = this.makeHtml();

			$('div.popover.suredy-datetimepicker#' + this.id() + ' div.popover-content').html(html);
		},
		write : function() {
			this.ele.val($.suredy.formatDatetime(this.time, this.options.format)).trigger('change');
		},
		clean : function() {
			this.ele.val('');
		}
	};

	$('body').delegate('.datetimepicker', 'click', function() {
		var $this = $(this);

		var picker = $this.data().SuredyDatetimePicker;

		// 如果点击对象已经弹出日期控件，则不做任何操作
		if (picker.id())
			return false;

		hideAll();

		picker.show();

		// cancel
		$('body').one('click', function() {
			picker.hide();
		});

		// select change
		$('div.popover.suredy-datetimepicker#' + picker.id()).on('change', 'select', function() {
			var $this = $(this);
			var $picker = $('div.popover.suredy-datetimepicker#' + picker.id());

			picker.time.setFullYear(Number($('select.year', $picker).val() || '1970'));
			picker.time.setMonth(Number($('select.month', $picker).val() || '1') - 1);
			picker.time.setHours(Number($('select.hour', $picker).val() || '0'));
			picker.time.setMinutes(Number($('select.minute', $picker).val() || '0'));

			if ($this.hasClass('year') || $this.hasClass('month'))
				picker.reDraw();

			picker.write();
		});

		// date click
		$('div.popover.suredy-datetimepicker#' + picker.id()).on('click', 'td.date', function() {
			var $this = $(this);

			var date = new Date($this.data('date'));

			picker.time.setFullYear(date.getFullYear(), date.getMonth(), date.getDate());

			picker.reDraw();

			picker.write();
		});

		// today btn click
		$('div.popover.suredy-datetimepicker#' + picker.id()).on('click', 'div.btn.today', function() {
			var date = new Date();

			picker.time.setFullYear(date.getFullYear(), date.getMonth(), date.getDate());
			picker.time.setHours(date.getHours(), date.getMinutes(), date.getSeconds(), 0);

			picker.reDraw();

			picker.write();
		});

		// clean btn click
		$('div.popover.suredy-datetimepicker#' + picker.id()).on('click', 'div.btn.clean', function() {
			picker.clean();
		});

		// ignore popover click
		$('div.popover.suredy-datetimepicker#' + picker.id()).on('click', function() {
			return false;
		});

		return false;
	});

	// init
	$.suredy.datetimepicker = {
		init : function() {
			$('.datetimepicker').each(function(i) {
				var $this = $(this);

				// 已经初始化过的，不再进行初始化
				if ($this.data().SuredyDatetimePicker)
					return true;

				var picker = new SuredyDatetimePicker($this, {
					head : $this.data('head'),
					foot : $this.data('foot'),
					current : $this.data('current'),
					weekstart : $this.data('weekstart'),
					format : $this.data('format'),
					time : $this.data('time')
				});

				$this.data().SuredyDatetimePicker = picker;

				if ($this.data('time'))
					picker.write();
			});
		}
	};
})(jQuery, window, document);
