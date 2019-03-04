;
/**
 * support bootstrap 3.3.0
 */
(function(global, factory) {

	if (typeof define === 'function' && define.amd) {
		// 添加到amd中，并加载依赖
		define([ 'moment', 'suredy', 'bootstrap' ], function(moment, Suredy) {
			return factory(global, moment, global.jQuery, Suredy);
		});
	} else if (!global.moment || !global.Suredy) {
		throw new Error("Suredy.Datetimepicker requires moment.js and suredy.js");
	} else {
		factory(global, global.moment, global.jQuery, global.Suredy);
	}

})(window, function(window, moment, $, Suredy) {
	'use strict';

	// bootstrap's popover first
	if (!$.fn.popover)
		throw new Error('Datetimepicker requires Bootstrap\'s popover');

	var fixNumberWithZero = function(num, len) {
		if (!len)
			return num;

		var text = '' + num;

		if (text.length >= len)
			return text;

		var index = 0;

		for (; index < len - text.length; index++) {
			text = '0' + text;
		}

		return text;
	};

	// year view events
	var _EVENT_YEAR_ = function() {
		var that = this;
		var $picker = this.tip().find('>.content>table.year>tbody');

		// change year group event
		$picker.find('>tr.title>td>div.btn').filter('.pre,.next').on('click', function(event) {
			that.yearView($(this).data('start'));
		});

		// year click event
		$picker.find('>tr.year>td').on('click', function(event) {
			var $this = $(this);

			that.setYear($this.data('year'));

			// year view is not the default view, then return to the default view
			if (that.defaultView() != 'Y') {
				that.gotoPreView();
				return;
			}

			// manual close, only change view
			if (that.isManualClose()) {
				$picker.find('>tr.year>td').removeClass('active');
				$this.addClass('active');
				return;
			}

			that.hide();
		});

		// toyear event
		$picker.find('>tr.foot>td>div.btn.toyear').on('click', function() {
			that.setYear(moment().year());

			// year view is not the default view, then return to the default view
			if (that.defaultView() != 'Y') {
				that.gotoPreView();
				return;
			}

			// manual close, change to now year view
			if (that.isManualClose()) {
				that.yearView();
				return;
			}

			that.hide();
		});

		// clean btn event
		$picker.find('>tr.foot>td>div.btn.clean-data').on('click', function() {
			that.time = null;
			that.$element.val('');
			that.yearView();
		});

		// close event
		$picker.find('>tr.foot>td>div.btn.manual-close').on('click', function() {
			// year view is not the default view, then return to the default view
			if (that.defaultView() != 'Y') {
				that.gotoPreView();
				return;
			}

			that.hide();
		});
	};

	// month view events
	var _EVENT_MONTH_ = function() {
		var that = this;
		var $picker = this.tip().find('>.content>table.month>tbody');

		// change year group event
		$picker.find('>tr.title>td>div.btn').filter('.pre,.next').on('click', function(event) {
			var $this = $(this);

			that.addYear($this.data('step'));

			that.monthView();
		});

		// year btn click event
		$picker.find('>tr.title>td>div.btn.year').on('click', function(event) {
			if ($(this).hasClass('disabled'))
				return;

			that.addPreView('M');

			that.yearView();
		});

		// month click event
		$picker.find('>tr.month>td').on('click', function(event) {
			var $this = $(this);

			that.setMonth($this.data('month') - 1);

			// month view is not the default view, then return to the default view
			if (that.defaultView() != 'M') {
				that.gotoPreView();
				return;
			}

			// manual close, only change view
			if (that.isManualClose()) {
				that.monthView();
				return;
			}

			that.hide();
		});

		// tomonth event
		$picker.find('>tr.foot>td>div.btn.tomonth').on('click', function() {
			var time = moment();

			that.setYear(time.year());
			that.setMonth(time.month());

			// month view is not the default view, then return to the default view
			if (that.defaultView() != 'M') {
				that.gotoPreView();
				return;
			}

			// manual close, change to now year view
			if (that.isManualClose()) {
				that.monthView();
				return;
			}

			that.hide();
		});

		// clean btn event
		$picker.find('>tr.foot>td>div.btn.clean-data').on('click', function() {
			that.time = null;
			that.$element.val('');
			that.monthView();
		});

		// close event
		$picker.find('>tr.foot>td>div.btn.manual-close').on('click', function() {
			// month view is not the default view, then return to the default view
			if (that.defaultView() != 'M') {
				that.gotoPreView();
				return;
			}

			that.hide();
		});
	};

	// date view event
	var _EVENT_DATE_ = function() {
		var that = this;
		var $picker = this.tip().find('>.content>table.date>tbody');

		// change month group event
		$picker.find('>tr.title>td>div.btn').filter('.pre,.next').on('click', function(event) {
			var $this = $(this);

			if ($this.hasClass('disabled'))
				return;

			that.addMonth($this.data('step'));

			that.dateView();
		});

		// year btn click event
		$picker.find('>tr.title>td>div.btn.year').on('click', function(event) {
			if ($(this).hasClass('disabled'))
				return;

			that.addPreView('D');

			that.yearView();
		});

		// month btn click event
		$picker.find('>tr.title>td>div.btn.month').on('click', function(event) {
			if ($(this).hasClass('disabled'))
				return;

			that.addPreView('D');

			that.monthView();
		});

		// date click event
		$picker.find('>tr.date>td').on('click', function(event) {
			var $this = $(this);
			var time = moment($this.data('date'), 'YYYY-MM-DD');

			that.setYear(time.year());
			that.setMonth(time.month());
			that.setDate(time.date());

			// month view is not the default view, then return to the default view
			if (that.defaultView() != 'D') {
				that.gotoPreView();
				return;
			}

			// manual close, only change view
			if (that.isManualClose()) {
				that.dateView();
				return;
			}

			that.hide();
		});

		// today event
		$picker.find('>tr.foot>td>div.btn.today').on('click', function() {
			var time = moment();

			that.setYear(time.year());
			that.setMonth(time.month());
			that.setDate(time.date());

			// month view is not the default view, then return to the default view
			if (that.defaultView() != 'D') {
				that.gotoPreView();
				return;
			}

			// manual close, change to now year view
			if (that.isManualClose()) {
				that.dateView();
				return;
			}

			that.hide();
		});

		// time btn event
		$picker.find('>tr.foot>td>div.btn.time').on('click', function() {
			that.addPreView('D');
			that.timeView();
		});

		// clean btn event
		$picker.find('>tr.foot>td>div.btn.clean-data').on('click', function() {
			that.time = null;
			that.$element.val('');
			that.dateView();
		});

		// close event
		$picker.find('>tr.foot>td>div.btn.manual-close').on('click', function() {
			// month view is not the default view, then return to the default view
			if (that.defaultView() != 'D') {
				that.gotoPreView();
				return;
			}

			that.hide();
		});
	};

	// time view event
	var _EVENT_TIME_ = function() {
		var that = this;
		var $picker = this.tip().find('>.content>table.time>tbody');
		var $hour = $picker.find('>tr.time>td.hour>div.btn-group.hour');
		var $minute = $picker.find('>tr.time>td.minute>div.btn-group.minute');

		// hour btn click
		$hour.find('>div.btn.hour').on('click', function(event) {
			$hour.find('>ul.dropdown-menu.hour').dropdown('toggle');
		});

		// hour selector
		$hour.find('>ul.dropdown-menu.hour>li').on('click', function(event) {
			var hour = $(this).data('hour');

			that.setHour(hour);

			that.timeView();
		});

		// minute btn click
		$minute.find('>div.btn.minute').on('click', function(event) {
			$minute.find('>ul.dropdown-menu.minute').dropdown('toggle');
		});

		// minute selector
		$minute.find('>ul.dropdown-menu.minute>li').on('click', function(event) {
			var minute = $(this).data('minute');

			that.setMinute(minute);

			that.timeView();
		});

		// clean btn event
		$picker.find('>tr.time>td.clean-data>div.btn.clean-data').on('click', function() {
			that.time = null;
			that.$element.val('');
			that.timeView();
		});

		// close event
		$picker.find('>tr.time>td.ok>div.btn.manual-close').on('click', function() {
			// month view is not the default view, then return to the default view
			if (that.defaultView() != 'T') {
				that.gotoPreView();
				return;
			}

			that.hide();
		});
	};

	var Datetimepicker = function(element, options) {
		this.init('suredy.datetimepicker', element, options);

		var that = this;

		// bind click event
		this.$element.on('click.suredy.datetimepicker.toggle', $.proxy(function(event) {
			event.stopPropagation();

			this.toggle();
		}, this));

		// reset position and bind body click
		this.$element.on('shown.bs.' + this.type, function() {
			var component = $(this).data('bs.' + that.type);

			var $tip = component.tip();

			//$tip.css('left', component.$element.position().left + 'px');

			// close for click body
			if (!that.isManualClose())
				$(document).one('click.suredy.datetimepicker.hidden', function(event) {
					that.hide();
				});

			// stop pop event
			that.tip().off('click.suredy.datetimepicker.stoppropagation');
			that.tip().on('click.suredy.datetimepicker.stoppropagation', function(event) {
				event.stopPropagation();
			});
		});
	};

	// extends popover
	Datetimepicker.prototype = $.extend({}, $.fn.popover.Constructor.prototype);

	Datetimepicker.prototype.constructor = Datetimepicker;

	Datetimepicker.DEFAULTS = $.extend({}, $.fn.popover.DEFAULTS, {
		// picker's default options
		weekstart : 1, // start of a week. 0=sundy...6=Saturday
		format : 'yyyy-MM-dd', // datetime formate, eg: yyyy-MM-dd HH:mm:ss
		time : false, // selected time, milliseconds, default is now
		view : 'D Y M', // views, splic by space, the first is default. eg: Y M D T
		manualClose : false, // manual close the datetime picker

		// popover's default options
		container : false,
		animation : true,
		placement : 'bottom',
		trigger : 'manual',
		template : '<div class="suredy-datetimepicker" role="tooltip" style="width: 225px;"><div class="content"></div></div>',
	});

	// week definition
	Datetimepicker.prototype.WEEK_DEFINE = [ '日', '一', '二', '三', '四', '五', '六' ];

	// pre view link
	Datetimepicker.prototype.preViews = [];

	// view definition
	Datetimepicker.prototype.VIEW_DEFINE = {
		Y : 'yearView',
		M : 'monthView',
		D : 'dateView',
		T : 'timeView',
	};

	Datetimepicker.prototype.getDefaults = function() {
		return Datetimepicker.DEFAULTS;
	};

	Datetimepicker.prototype.setContent = function() {
		// reset pre views
		this.preViews = [];

		// show default view
		this.showDefaultView();
	};

	Datetimepicker.prototype.hasContent = function() {
		return true;
	};

	Datetimepicker.prototype.getTitle = function() {
		return false;
	};

	Datetimepicker.prototype.isManualClose = function() {
		return (this.manulClose = this.manulClose || !!this.options.manualClose);
	};

	Datetimepicker.prototype.showDefaultView = function() {
		var view = this[this.VIEW_DEFINE[this.defaultView()]];

		// show view
		if (view)
			view.call(this);

		// default show date view
		else
			this.dateView();
	};

	Datetimepicker.prototype.yearView = function(start) {
		var $tip = this.tip();
		var $content = $tip.find('>.content').empty();

		var time = this.getTime();
		var index = 0;

		// current
		var year = time.year();

		// toyear
		var toyear = moment().year();

		if (!start || !$.isNumeric(start))
			start = year - 4;
		else
			start = Math.floor(start);

		var $picker = $('<table class="picker year"><tbody></tbody></table>').appendTo($content).find('>tbody');

		// add title
		var $title = $('<tr class="title"><td colspan="3"></td></tr>').appendTo($picker).find('>td');
		$('<div class="btn btn-info btn-sm pull-left pre"><i class="glyphicon glyphicon-chevron-left"></i></div>').appendTo($title).data('start', start - 9);
		$('<div class="btn btn-info btn-sm year disabled">' + start + '年~' + (start + 8) + '年</div>').appendTo($title);
		$('<div class="btn btn-info btn-sm pull-right next"><i class="glyphicon glyphicon-chevron-right"></i></div>').appendTo($title).data('start', start + 9);

		// add year
		var $year = false;
		var y = start;
		for (index = 0; index < 3; index++) {
			// add a date row
			if (index == 0)
				$year = $('<tr class="year"></tr>').appendTo($picker);

			var $y = $('<td>' + y + '年</td>').appendTo($year);
			$y.data('year', y);

			// this year
			if (y == toyear)
				$y.addClass('toyear');

			// selected year
			if (y == year)
				$y.addClass('active');

			y += 1;

			if (y > start + 8)
				break;

			// the last date
			if (index == 2)
				index = -1;
		}

		// add footer
		var $foot = $('<tr class="foot"><td colspan="7"></td></tr>').appendTo($picker).find('>td');
		$('<div class="btn btn-warning btn-xs pull-left toyear">今年</div>').appendTo($foot);

		// clean btn
		if (this.defaultView() == 'Y')
			$('<div class="btn btn-info btn-xs pull-left clean-data" title="清除"><i class="glyphicon glyphicon-trash"></i></div>').appendTo($foot);

		// close btn
		if (this.defaultView() != 'Y' || !!this.isManualClose())
			$('<div class="btn btn-danger btn-xs pull-right manual-close" title="完成"><i class="glyphicon glyphicon-ok"></i></div>').appendTo($foot);

		// bind events
		_EVENT_YEAR_.call(this);
	};

	Datetimepicker.prototype.monthView = function() {
		var $tip = this.tip();
		var $content = $tip.find('>.content').empty();

		var time = this.getTime();
		var index = 0;

		// current
		var year = time.year();
		var month = time.month() + 1;

		// tomonth
		var now = moment();
		var toyear = now.year();
		var tomonth = now.month() + 1;

		var $picker = $('<table class="picker month"><tbody></tbody></table>').appendTo($content).find('>tbody');

		// add title
		var $title = $('<tr class="title"><td colspan="3"></td></tr>').appendTo($picker).find('>td');
		$('<div class="btn btn-info btn-sm pull-left pre"><i class="glyphicon glyphicon-chevron-left"></i></div>').appendTo($title).data('step', -1);
		$('<div class="btn btn-info btn-sm year">' + year + '年</div>').appendTo($title);
		$('<div class="btn btn-info btn-sm pull-right next"><i class="glyphicon glyphicon-chevron-right"></i></div>').appendTo($title).data('step', 1);

		// add year
		var $month = false;
		var m = 1;
		for (index = 0; index < 3; index++) {
			// add a date row
			if (index == 0)
				$month = $('<tr class="month"></tr>').appendTo($picker);

			var $m = $('<td>' + m + '月</td>').appendTo($month);
			$m.data('month', m);

			if (year == toyear && m == tomonth)
				$m.addClass('tomonth');

			if (m == month)
				$m.addClass('active');

			m += 1;

			if (m > 12)
				break;

			// the last date
			if (index == 2)
				index = -1;
		}

		// add footer
		var $foot = $('<tr class="foot"><td colspan="7"></td></tr>').appendTo($picker).find('>td');
		$('<div class="btn btn-warning btn-xs pull-left tomonth">本月</div>').appendTo($foot);

		// clean btn
		if (this.defaultView() == 'M')
			$('<div class="btn btn-info btn-xs pull-left clean-data" title="清除"><i class="glyphicon glyphicon-trash"></i></div>').appendTo($foot);

		// close btn
		if (this.defaultView() != 'M' || !!this.isManualClose())
			$('<div class="btn btn-danger btn-xs pull-right manual-close" title="完成"><i class="glyphicon glyphicon-ok"></i></div>').appendTo($foot);

		// no year view
		if ($.inArray('Y', this.getViews()) == -1) {
			$title.find('div.btn.pre,div.btn.next').remove();
			$title.find('div.btn.year').addClass('disabled');
		}

		// bind events
		_EVENT_MONTH_.call(this);
	};

	Datetimepicker.prototype.dateView = function() {
		var $tip = this.tip();
		var $content = $tip.find('>.content').empty();

		var time = this.getTime();
		var weekstart = this.getWeekStart();
		var index = 0;

		// current
		var year = time.year();
		var month = time.month() + 1;
		var date = time.date();
		var hour = time.hour();
		var minute = time.minute();

		// toyear
		var now = moment();
		var toyear = now.year();
		var tomonth = now.month() + 1;
		var today = now.date();

		// reset to the first day of month
		time.date(1);

		// reset to the first date of week
		while (weekstart != time.weekday()) {
			time.subtract(1, 'days');
		}

		var $picker = $('<table class="picker date"><tbody></tbody></table>').appendTo($content).find('>tbody');

		// add title
		var $title = $('<tr class="title"><td colspan="7"></td></tr>').appendTo($picker).find('>td');
		$('<div class="btn btn-info btn-sm pull-left pre"><i class="glyphicon glyphicon-chevron-left"></i></div>').appendTo($title).data('step', -1);
		$('<div class="btn btn-info btn-sm year">' + year + '年</div>').appendTo($title);
		$('<div class="btn btn-info btn-sm month">' + month + '月</div>').appendTo($title);
		$('<div class="btn btn-info btn-sm pull-right next"><i class="glyphicon glyphicon-chevron-right"></i></div>').appendTo($title).data('step', 1);

		// add week
		var $week = $('<tr class="week"></tr>').appendTo($picker);
		for (index = 0; index < 7; index++) {
			if (weekstart > 6)
				weekstart = 0;

			$('<td>' + this.WEEK_DEFINE[weekstart++] + '</td>').appendTo($week);
		}

		// add date
		var $date = false;
		for (index = 0; index < 7; index++) {
			// add a date row
			if (index == 0)
				$date = $('<tr class="date"></tr>').appendTo($picker);

			var y = time.year();
			var m = time.month() + 1;
			var d = time.date();

			var $d = $('<td>' + d + '</td>').appendTo($date);
			$d.data('date', y + '-' + m + '-' + d);

			if (y < year || (y == year && m < month)) // pre month
				$d.addClass('pre');
			else if (y > year || (y == year && m > month)) // next month
				$d.addClass('next');

			if (y == toyear && m == tomonth && d == today)
				$d.addClass('today');

			if (y == year && m == month && d == date)
				$d.addClass('active');

			time.add(1, 'days');

			// the last date
			if (index == 6) {
				index = -1;

				if (time.year() > year || time.month() + 1 > month)
					break;
			}
		}

		// add time
		var $foot = $('<tr class="foot"><td colspan="7"></td></tr>').appendTo($picker).find('>td');
		if ($.inArray('T', this.getViews()) != -1) {
			$('<div class="btn btn-info btn-xs pull-left time">' + fixNumberWithZero(hour, 2) + ':' + fixNumberWithZero(minute, 2) + '</div>').appendTo($foot);
		}
		$('<div class="btn btn-warning btn-xs pull-left today">今天</div>').appendTo($foot);

		// clean btn
		if (this.defaultView() == 'D')
			$('<div class="btn btn-info btn-xs pull-left clean-data" title="清除"><i class="glyphicon glyphicon-trash"></i></div>').appendTo($foot);

		// close btn
		if (this.defaultView() != 'D' || !!this.isManualClose())
			$('<div class="btn btn-danger btn-xs pull-right manual-close" title="完成"><i class="glyphicon glyphicon-ok"></i></div>').appendTo($foot);

		// no year
		if ($.inArray('Y', this.getViews()) == -1)
			$title.find('>div.btn.year').addClass('disabled');

		// no month
		if ($.inArray('M', this.getViews()) == -1) {
			$title.find('>div.btn.pre,>div.btn.next').remove();
			$title.find('>div.btn.month').addClass('disabled');
		}

		// bind events
		_EVENT_DATE_.call(this);
	};

	Datetimepicker.prototype.timeView = function() {
		var $tip = this.tip();
		var $content = $tip.find('>.content').empty();

		var time = this.getTime();
		var index = 0;

		// current
		var hour = time.hour();
		var minute = time.minute();

		var $picker = $('<table class="picker time"><tbody></tbody></table>').appendTo($content).find('>tbody');

		// time
		var $time = $('<tr class="time"></tr>').appendTo($picker);

		// hour
		var $hour = $('<td class="hour"><div class="btn-group btn-block hour"></div></td>').appendTo($time).find('>div.hour');
		$('<div class="btn btn-info btn-block hour" data-toggle="dropdown">' + fixNumberWithZero(hour, 2) + '时&nbsp;<span class="caret"></span></div>').appendTo($hour);
		var $hourGroup = $('<ul class="dropdown-menu hour" role="menu"></ul>').appendTo($hour);
		for (index = 0; index < 24; index++) {
			$('<li><a href="javascript:;">' + fixNumberWithZero(index, 2) + '时</a></li>').appendTo($hourGroup).data('hour', index);
		}

		// minute
		var $minute = $('<td class="minute"><div class="btn-group btn-block minute"></div></td>').appendTo($time).find('>div.minute');
		$('<div class="btn btn-info btn-block minute" data-toggle="dropdown">' + fixNumberWithZero(minute, 2) + '分&nbsp;<span class="caret"></span></div>').appendTo($minute);
		var $minuteGroup = $('<ul class="dropdown-menu minute" role="menu"></ul>').appendTo($minute);
		for (index = 0; index < 59; index++) {
			$('<li><a href="javascript:;">' + fixNumberWithZero(index, 2) + '分</a></li>').appendTo($minuteGroup).data('minute', index);
		}

		// clean btn
		if (this.defaultView() == 'T')
			$('<td class="clean-data"><div class="btn btn-info btn-block clean-data" title="清除"><i class="glyphicon glyphicon-trash"></i></div></td>').appendTo($time);

		// ok
		$('<td class="ok"><div class="btn btn-danger btn-block manual-close" title="完成"><i class="glyphicon glyphicon-ok"></i></div></td>').appendTo($time);

		// bind events
		_EVENT_TIME_.call(this);
	};

	Datetimepicker.prototype.getViews = function() {
		if (this.views)
			return this.views;

		// *******************************************
		// compatibility mode. for old version fix
		// default use old version properties
		this._fromOldVersionViews();
		// *******************************************

		var that = this;
		var views = this.options.view.replace(/(^\s*)|(\s*$)/g, '').toUpperCase().split(' ');

		// clean
		$.each(views, function(i, v) {
			// empty value
			if (!v)
				return;

			// invalid
			if (!that.VIEW_DEFINE[v])
				return;

			that.views = that.views || [];

			that.views.push(v);
		});

		if (!this.views) {
			// reset view
			this.options.view = Datetimepicker.DEFAULTS.view;
			return this.getViews();
		}

		return this.views;
	};

	Datetimepicker.prototype.defaultView = function() {
		return this.getViews()[0];
	};

	Datetimepicker.prototype.addPreView = function(view) {
		this.preViews = this.preViews || [];

		if (!view)
			return;

		if ($.inArray(view, this.getViews()) == -1)
			throw new Error('Invalid view flag. It is: ' + view);

		this.preViews.push(view);
	};

	Datetimepicker.prototype.gotoPreView = function() {
		this.preViews = this.preViews || [];

		var pre = this.preViews.pop() || this.defaultView();

		// call show view method
		this[this.VIEW_DEFINE[pre]].call(this);
	};

	Datetimepicker.prototype.getTime = function() {
		this.time = this.time || this.parse(this.$element.val() || this.options.time);

		if (!this.time) {
			this.time = moment();
			this.time.second(0);
			this.time.millisecond(0);
		}

		// clone a moment
		return moment(this.time);
	};

	Datetimepicker.prototype.fs = function() {
		return this.formatStr = this.formatStr || this.options.format.replace(/y/g, 'Y').replace(/d/g, 'D');
	};

	Datetimepicker.prototype.parse = function(time) {
		time = time || this.$element.val() || this.options.time;

		if (!time)
			return null;

		return moment(time, this.fs());
	};

	Datetimepicker.prototype.format = function(time) {
		time = time || this.getTime();

		return time.format(this.fs());
	};

	Datetimepicker.prototype.addYear = function(year) {
		this.time = this.getTime().add(year, 'years');

		this.$element.val(this.format());
	};

	Datetimepicker.prototype.setYear = function(year) {
		var oldTime = this.getTime();
		var newTime = this.getTime().year(year);

		// fix month
		while (newTime.month() > oldTime.month()) {
			newTime.date(1);
			newTime.subtract(1, 'days');
		}

		// reset time
		this.time = newTime;

		this.$element.val(this.format());
	};

	Datetimepicker.prototype.addMonth = function(month) {
		this.time = this.getTime().add(month, 'months');

		this.$element.val(this.format());
	};

	Datetimepicker.prototype.setMonth = function(month) {
		var newTime = this.getTime().month(month);

		// fix month
		while (newTime.month() > month) {
			newTime.date(1);
			newTime.subtract(1, 'days');
		}

		// reset time
		this.time = newTime;

		this.$element.val(this.format());
	};

	Datetimepicker.prototype.addDate = function(date) {
		this.time = this.getTime().add(date, 'days');

		this.$element.val(this.format());
	};

	Datetimepicker.prototype.setDate = function(date) {
		this.time = this.getTime().date(date);

		this.$element.val(this.format());
	};

	Datetimepicker.prototype.addHour = function(hour) {
		this.time = this.getTime().add(hour, 'hours');

		this.$element.val(this.format());
	};

	Datetimepicker.prototype.setHour = function(hour) {
		if (hour < 0 || hour > 23)
			throw new Error('Invalid hours. 0 <= hour >= 23');

		this.time = this.getTime().hour(hour);

		this.$element.val(this.format());
	};

	Datetimepicker.prototype.addMinute = function(minute) {
		this.time = this.getTime().add(minute, 'minutes');

		this.$element.val(this.format());
	};

	Datetimepicker.prototype.setMinute = function(minute) {
		if (minute < 0 || minute > 59)
			throw new Error('Invalid minute. 0 <= minute >= 59');

		this.time = this.getTime().minute(minute);

		this.$element.val(this.format());
	};

	Datetimepicker.prototype.getWeekStart = function() {
		this.weekstart = this.weekstart || this.options.weekstart;

		if (!$.isNumeric(this.weekstart))
			return (this.weekstart = 1);

		this.weekstart = Math.floor(this.weekstart);

		if (this.weekstart < 0)
			this.weekstart = 0;

		if (this.weekstart > 6)
			this.weekstart = 6;

		return this.weekstart;
	};

	Datetimepicker.prototype.getWeekEnd = function() {
		this.weekend = this.weekend || this.getWeekStart();

		if (this.weekend != this.weekstart)
			return this.weekend;

		var index = 0;

		for (; index < 6; index++) {
			this.weekend += 1;

			if (this.weekend > 6)
				this.weekend = 0;
		}

		return this.weekend;
	};

	Datetimepicker.prototype._fromOldVersionViews = function() {
		var options = this.options;

		var hasOldProperties = 'head' in options || 'date' in options || 'foot' in options;

		// no old version config
		if (!hasOldProperties)
			return;

		// get properties
		var head = !!options.head;
		var date = !!options.date;
		var foot = !!options.foot;

		// clean options
		delete options.head;
		delete options.date;
		delete options.foot;

		var views = '';

		// views
		if (head)
			views += ' Y M';
		if (date)
			views += ' D';
		if (foot)
			views += ' T';

		// default views
		views = (date && 'D' || head && 'Y' || foot && 'T') + views;

		this.options.view = views;
	};

	var _plugin = function(option) {
		return this.each(function(i, dom) {
			var $this = $(dom);
			var component = $this.data('bs.suredy.datetimepicker');

			if (!component)
				$this.data('bs.suredy.datetimepicker', (new Datetimepicker(dom, option)));
		});
	};

	Suredy.Datetimepicker = function(selecotr, options) {
		return _plugin.call($(selecotr), options);
	};

	Suredy.Datetimepicker.Constructor = Datetimepicker;

	Suredy.Datetimepicker.AutoDelegate = function(delegate) {
		// off bind "suredy.datetimepicker" event on document
		$(document).off('.suredy.datetimepicker');

		// destroy all ".datetimepicker"'s datetimepicker component
		$('.datetimepicker').each(function() {
			var picker = $(this).data('bs.suredy.datetimepicker');

			if (picker)
				picker.destroy();
		});

		// auto delegate
		if (!!delegate) {
			// init exists
			Suredy.Datetimepicker('.datetimepicker');

			// delegate future
			// $(document).one('click.suredy.datetimepicker.delegate', '.datetimepicker', function(event) {
			// _plugin.call($(this));
			// });
		}
	};

	Suredy.Datetimepicker.AutoDelegate(true);

	return Suredy.Datetimepicker;
});