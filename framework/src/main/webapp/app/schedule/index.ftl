<link rel="stylesheet" type="text/css" href="${request.contextPath}/app/schedule/css/schedule.css" />
<div class="page-header">
	<div class="pull-right form-inline">
		<div class="btn-group">
			<button class="btn btn-warning" data-calendar-view="year">年视图</button>
			<button class="btn btn-warning active" data-calendar-view="month">月视图</button>
			<button class="btn btn-warning" data-calendar-view="week">周视图</button>
			<button class="btn btn-warning" data-calendar-view="day">日视图</button>
		</div>		
		<div class="btn-group">
			<button class="btn btn-primary" data-calendar-nav="prev">上一个</button>
			<button class="btn btn-default" data-calendar-nav="today">今日</button>
			<button class="btn btn-primary" data-calendar-nav="next">下一个</button>
		</div>
		<div class="btn-group">
			<div class="btn btn-danger" id="btn-create-task">
				<i class="icon-plus"></i>创建任务
			</div>
		</div>		
	</div>
	<h3></h3>
</div>

<div class="row">
	<div class="col-md-9">
		<div id="calendar"></div>
	</div>
	<div class="col-md-3">
		<h4>排班信息 <small>最新10项</small></h4>
		<ul id="eventlist" class="nav nav-list"></ul>
	</div>
</div>
<script type="text/javascript" src="${request.contextPath}/app/schedule/js/underscore-min.js"></script>
<script type="text/javascript" src="${request.contextPath}/app/schedule/js/timezone.min.js"></script>
<script type="text/javascript" src="${request.contextPath}/app/schedule/js/schedule.js"></script>

<script type="text/javascript">
require([ 'suredyModal' ], function(Modal) {
	var options = {
		events_source: './schedule/tasks',
		view: 'month',
		tmpl_path: './app/schedule/templates/',
		tmpl_cache: false,
		day: 'now', //'now' //'2013-03-23'
		display_week_numbers: true,
		weekbox: true,
		first_day : 1, //0--周日, 1--周一
		onAfterEventsLoad: function(events) {
			if(!events) {
				return;
			}
			var list = $('#eventlist');
			list.html('');

			$.each(events, function(key, val) {
				
				if (key < 10) {
					var text = '[' + (val.subjectType == 2 ? '班组' : '个人') + '] ' + val.name; 
					$(document.createElement('li'))
						.html('<a href="javascript:void(0);" data-event-id="'+ val.id +'" onclick="showTask(this);">' + text + '</a>')
						.appendTo(list);
				}
			});
		},
		onAfterViewLoad: function(view) {
			$('.page-header h3').html(this.getTitle() + " <small>任务排班</small>");
			$('.btn-group button').removeClass('active');
			$('button[data-calendar-view="' + view + '"]').addClass('active');
		},
		classes: {
			months: {
				general: 'label'
			}
		}
	};

	var calendar = $('#calendar').calendar(options);
	
	$('.btn-group button[data-calendar-nav]').each(function() {
		var $this = $(this);
		$this.click(function() {
			calendar.navigate($this.data('calendar-nav'));
		});
	});

	$('.btn-group button[data-calendar-view]').each(function() {
		var $this = $(this);
		$this.click(function() {
			calendar.view($this.data('calendar-view'));
		});
	});
	


	$('#btn-create-task').click(function(e){
		Modal.showModal({
			size : 'lg',
			icon : 'icon-plus',
			title : '创建任务',
			showFoot : false,
			uri : '${request.contextPath}/schedule/task-info'
		});
	});
});

var showTask = function(e){
	var taskId = $(e).data('event-id');
	require([ 'suredyModal' ], function(Modal) {
		Modal.showModal({
			size : 'lg',
			icon : 'icon-edit',
			title : '查看/修改任务',
			showFoot : false,
			uri : '${request.contextPath}/schedule/task-info?id=' + taskId
		});
	});	
};
</script>
