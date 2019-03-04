;
(function(global, factory) {

	if (typeof define === 'function' && define.amd) {
		// 添加到amd中，并加载依赖
		define([ 'moment', 'jquery', 'bootstrap', 'suredy' ], function(_m, $, _b, $d) {
			return factory(global, _m, $, $d);
		});
	} else if (!global.moment || !global.jQuery || !global.Suredy) {
		throw new Error("Suredy.Datetimepicker requires a Momentjs, a jQuery and a Suredy");
	} else {
		factory(global, global.moment, global.jQuery, global.Suredy);
	}

})(this, function(window, _m, $, $d) {

	var _week = [ '日', '一', '二', '三', '四', '五', '六' ];

	var _options = {
		fontSize : 14, // only support px
		head : true,
		date : true,
		foot : true,
		weekstart : 1, // start of week. 0=sundy...6=Saturday
		format : 'yyyy-MM-dd HH:mm',
		time : undefined, // the time stamp
	};

	// datetimepicker object
	var Datetimepicker = function(dom) {
		if (!dom)
			throw new Error('Error! Invalid dom.');

		var $ele = this.$ele = $(dom);

		// has inited. return datetimepicker obj
		if ($ele.data().datetimepicker)
			return $ele.data().datetimepicker;

		var ops = this.options = $.extend({}, _options, {
			head : $ele.data('head'),
			date : $ele.data('date'),
			foot : $ele.data('foot'),
			weekstart : $ele.data('weekstart'),
			format : $ele.data('format'),
			time : $ele.data('time') || $ele.val()
		});

		// change to momentjs's pattern
		ops.format = ops.format.replace(/y/g, 'Y').replace(/d/g, 'D');

		// parse date
		if (typeof ops.time === 'string')
			ops.time = _m(ops.time, ops.format).valueOf();

		if (ops.time instanceof Date)
			ops.time = ops.time.getTime();

		if (typeof ops.time !== 'number')
			ops.time = new Date().getTime();

		// output val
		$ele.val(_m(ops.time).format(ops.format));

		$ele.trigger('change');

		// set element data
		$ele.data().datetimepicker = this;

		var me = this;
		// init popover
		$ele.popover({
			// container : 'body',
			animation : true,
			content : function() {
				return me.draw();
			},
			html : true,
			placement : 'bottom',
			trigger : 'manual',
			template : '<div style="position: fixed; border: 1px solid #ccc; background-color: #fff;" class="popover suredy-datetimepicker" role="tooltip"><div class="arrow"></div><div class="popover-content padding-0"></div></div>'
		});

		return this;
	};

	Datetimepicker.prototype = {
		constructor : Datetimepicker,
		id : function() {
			return this.$ele.attr('aria-describedby');
		},
		draw : function() {
			return this.makeHtml();
		},
		moment : function() {
			return _m(this.options.time);
		},
		setDatetime : function(datetime) {
			if (typeof datetime === 'number') {
				this.options.time = datetime;
				return true;
			}

			else if (datetime instanceof Date) {
				this.options.time = datetime.getTime();
				return true;
			}

			else if (typeof datetime === 'string') {
				this.options.time = _m(datetime, this.options.format).valueOf();
				return true;
			}

			throw new Error('Invalid param datetime');
		},
		setYear : function(year) {
			if (typeof year !== 'number')
				throw new Error('Invalid param year!');

			this.options.time = this.moment().year(year).valueOf();
		},
		setMonth : function(month) {
			if (typeof month !== 'number')
				throw new Error('Invalid param month!');

			this.options.time = this.moment().month(month).valueOf();
		},
		setDate : function(date) {
			if (typeof date !== 'number')
				throw new Error('Invalid param date!');

			this.options.time = this.moment().date(date).valueOf();
		},
		setHour : function(hour) {
			if (typeof hour !== 'number')
				throw new Error('Invalid param hour!');

			this.options.time = this.moment().hour(hour).valueOf();
		},
		setMinute : function(minute) {
			if (typeof minute !== 'number')
				throw new Error('Invalid param minute!');

			this.options.time = this.moment().minute(minute).valueOf();
		},
		setSeconds : function(seconds) {
			if (typeof seconds !== 'number')
				throw new Error('Invalid param seconds!');

			this.options.time = this.moment().seconds(seconds).valueOf();
		},
		makeHtml : function() {
			var fs = this.options.fontSize;
			var minWidth = fs * 2 * 7;
			minWidth = minWidth < 220 ? 220 : minWidth;

			var style_container = 'width: ' + minWidth + 'px; text-align: center; font-size: ' + fs + 'px;';

			var html = '<div style="' + style_container + '">{head}{content}{foot}</div>';

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

			var fs = this.options.fontSize;
			var style_head = 'width: 100%; border-bottom: 1px solid #ccc; display: table; padding: ' + (fs / 2) + 'px 0;';

			var head = '<div style="' + style_head + '">{icon}{year}{month}</div>';

			// icon
			head = head.replace(/\{icon\}/g, '<i class="icon-calendar">&nbsp;</i>');

			// year
			head = head.replace(/\{year\}/g, this.makeYear());

			// month
			head = head.replace(/\{month\}/g, this.makeMonth());

			return head;
		},
		makeContent : function() {
			if (!this.options.date)
				return '';

			return this.makeContentTitle() + this.makeContentDate();
		},
		makeFoot : function() {
			var fs = this.options.fontSize;
			var style_foot = 'width: 100%; border-top: 1px solid #ccc; display: table; padding: ' + (fs / 2) + 'px 0;';

			var foot = '<div style="' + style_foot + '">{icon}{hour}{minute}{tody}{clean}</div>';

			// icon
			foot = foot.replace(/\{icon\}/g, this.options.foot ? '&nbsp;<i class="icon-time"></i>' : '');

			// hour
			foot = foot.replace(/\{hour\}/g, this.options.foot ? this.makeHour() : '');

			// minute
			foot = foot.replace(/\{minute\}/g, this.options.foot ? this.makeMinute() : '');

			// tody
			foot = foot.replace(/\{tody\}/g, '<div class="btn btn-primary btn-xs today" style="margin-right: 5px;">今天</div>');

			// clean
			foot = foot.replace(/\{clean\}/g, '<div class="btn btn-danger btn-xs clean">清除</div>');

			return foot;
		},
		makeYear : function() {
			var year = '<select class="year" style="border: 0;">{options}</select>';
			var nYear = this.moment().get('year');

			var options = '';
			for ( var y = nYear + 3; y >= nYear - 10; y--) {
				var opt = '<option value="{y}"{selected}>{y}年</option>';

				// y
				opt = opt.replace(/\{y\}/g, y);

				// selected
				opt = opt.replace(/\{selected\}/g, y === nYear ? ' selected' : '');

				options += opt;
			}

			// options
			year = year.replace(/\{options\}/g, options);

			return year;
		},
		makeMonth : function() {
			var month = '<select class="month" style="border: 0;">{options}</select>';
			var nMonth = this.moment().get('month');

			var options = '';
			for ( var m = 0; m < 12; m++) {
				var opt = '<option value="{m}"{selected}>{m}月</option>';

				// o
				opt = opt.replace(/\{m\}/g, m + 1);

				// selected
				opt = opt.replace(/\{selected\}/g, m === nMonth ? ' selected' : '');

				options += opt;
			}

			// options
			month = month.replace(/\{options\}/g, options);

			return month;
		},
		makeHour : function(time) {
			var hour = '<select class="hour" style="border: 0;">{options}</select>';
			var nHour = this.moment().get('hour');

			var options = '';
			for ( var h = 0; h < 24; h++) {
				var opt = '<option value="{h}"{selected}>{h}时</option>';

				// h
				opt = opt.replace(/\{h\}/g, h);

				// selected
				opt = opt.replace(/\{selected\}/g, h === nHour ? ' selected' : '');

				options += opt;
			}

			// options
			hour = hour.replace(/\{options\}/g, options);

			return hour;
		},
		makeMinute : function(time) {
			var minute = '<select class="minute" style="border: 0;">{options}</select>';
			var nMinute = this.moment().get('minute');

			var options = '';
			for ( var m = 0; m < 60; m++) {
				var opt = '<option value="{m}"{selected}>{m}分</option>';

				// m
				opt = opt.replace(/\{m\}/g, m);

				// selected
				opt = opt.replace(/\{selected\}/g, m === nMinute ? ' selected' : '');

				options += opt;
			}

			// options
			minute = minute.replace(/\{options\}/g, options);

			return minute;
		},
		makeContentTitle : function() {
			var style_title_container = 'width: 100%; border-bottom: 1px solid #ccc; display: table;';
			var style_title_item = 'float: left; position: relative; min-height: 1px; width: 14.28571428%; text-align: center;';
			var style_title_week = 'float: left; position: relative; border: 0; width: 100%;';

			var title = '<div class="bg-info" style="' + style_title_container + '">{weeks}</div>';
			var html = '<div class="bg-info" style="' + style_title_item + '"><div style="' + style_title_week + '">{week}</div></div>';

			var weeks = '';

			for ( var w = this.options.weekstart; w >= 0 && w < 7; w++) {
				var tmp = html;
				// week
				tmp = tmp.replace(/\{week\}/g, _week[w]);

				weeks += tmp;
			}

			for ( var w = 0; w < this.options.weekstart && w >= 0 && w < 7; w++) {
				var tmp = html;

				// week
				tmp = tmp.replace(/\{week\}/g, _week[w]);

				weeks += tmp;
			}

			// weeks
			title = title.replace(/\{weeks\}/g, weeks);

			return title;
		},
		makeContentDate : function() {
			var year = this.moment().year();
			var month = this.moment().month();
			var date = this.moment().date();
			var monthEnd = _m(this.moment().valueOf()).endOf('month').valueOf();
			var start = _m(this.moment().valueOf()).startOf('month');

			// get start of datetimepicker
			while (start.weekday() != this.options.weekstart)
				start.subtract(1, 'day');

			var now = _m(new Date());
			var fs = this.options.fontSize;
			var style_item = 'float: left; position: relative; min-height: 1px; width: 14.28571428%;';
			var style_date = 'float: left; position: relative; border: 0; width: 100%; cursor: pointer; padding: ' + (fs / 2 - 2) + 'px 0;';

			var days = '';

			while (start.valueOf() < monthEnd) {
				for ( var i = 1; i <= 7; i++) {
					var tmp = '<div class="date{other}{current}{selected}" style="' + style_item + '" data-year="{year}" data-month="{month}" data-date="{d}"><div style="' + style_date + '">{date}</div></div>';

					// other
					tmp = tmp.replace(/\{other\}/g, start.month() !== month ? ' other' : '');

					// current
					tmp = tmp.replace(/\{current\}/g, function() {
						if (start.year() === now.year() && start.month() === now.month() && start.date() === now.date())
							return ' current';

						return '';
					});

					// selected
					tmp = tmp.replace(/\{selected\}/g, function() {
						if (start.year() === year && start.month() === month && start.date() === date)
							return ' selected';

						return '';
					});

					// year
					tmp = tmp.replace(/\{year\}/g, start.year());

					// month
					tmp = tmp.replace(/\{month\}/g, start.month() + 1);

					// date
					tmp = tmp.replace(/\{d\}/g, start.date());

					// data
					tmp = tmp.replace(/\{date\}/g, function() {
						if (start.year() === now.year() && start.month() === now.month() && start.date() === now.date())
							return '今';

						return start.date();
					});

					days += tmp;

					start.add(1, 'day');
				}
			}

			return days;
		},
		reDraw : function() {
			$('div.popover.suredy-datetimepicker#' + this.id() + ' div.popover-content').html(this.draw());
		},
		write : function() {
			this.$ele.val(this.moment().format(this.options.format)).trigger('change');
		},
		clean : function() {
			this.$ele.val('');
		},
		selectChange : function() {
			var $select = $('div.popover.suredy-datetimepicker#' + this.id() + ' select');
			var me = this;

			$select.each(function(i) {
				var $this = $(this);

				if ($this.hasClass('year')) {
					me.setYear(Number($this.val() || '1970'));
				}

				else if ($this.hasClass('month')) {
					me.setMonth(Number($this.val() || '1') - 1);
				}

				else if ($this.hasClass('hour')) {
					me.setHour(Number($this.val() || '0'));
				}

				else if ($this.hasClass('minute')) {
					me.setMinute(Number($this.val() || '0'));
				}
			});

			return true;
		},
		dateClick : function($date) {
			if (!$date || !($date instanceof $))
				throw new Error('Error! Need a jQuery date object.');

			this.setYear(Number($date.data('year') || '1970'));
			this.setMonth(Number($date.data('month') || '1') - 1);
			this.setDate(Number($date.data('date') || '1'));
		}
	};

	// unbind click event
	$(document).off('.suredy.datetimepicker');

	// bind click event
	$(document).on('click.suredy.datetimepicker.shown', '.datetimepicker', function() {
		var $this = $(this);
		var datetimepicker = $this.data().datetimepicker || new Datetimepicker(this);

		// popover has showed
		if (datetimepicker.id())
			return false;

		// show
		$this.popover('toggle');

		// close for click body
		$(document).one('click.suredy.datetimepicker.hidden', function() {
			$this.popover('hide');
		});

		// select change
		$('div.popover.suredy-datetimepicker#' + datetimepicker.id()).on('change', 'select', function() {
			var $this = $(this);

			datetimepicker.selectChange();

			if ($this.hasClass('year') || $this.hasClass('month')) {
				datetimepicker.reDraw();
			}

			datetimepicker.write();

			return true;
		});

		// date click
		$('div.popover.suredy-datetimepicker#' + datetimepicker.id()).on('click', '.date', function() {
			var $this = $(this);

			datetimepicker.dateClick($this);

			datetimepicker.reDraw();

			datetimepicker.write();

			return true;
		});

		// today btn click
		$('div.popover.suredy-datetimepicker#' + datetimepicker.id()).on('click', 'div.btn.today', function() {
			datetimepicker.setDatetime(new Date());

			datetimepicker.reDraw();

			datetimepicker.write();

			return true;
		});

		// clean btn click
		$('div.popover.suredy-datetimepicker#' + datetimepicker.id()).on('click', 'div.btn.clean', function() {
			datetimepicker.setDatetime(new Date());

			datetimepicker.clean();

			return true;
		});

		// ignore popover click
		$('div.popover.suredy-datetimepicker#' + datetimepicker.id()).on('click', function() {
			return false;
		});

		return true;
	});

	// 返回Suredy对象
	return false;

});