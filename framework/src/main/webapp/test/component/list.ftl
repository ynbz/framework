<div class="row margin-0">
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">列表</h3>
			</div>
			<div class="panel-body list-container">
				<table style="width: 1100px;" class="my-list" data-page="15" data-count="999" data-page-size="30">
					<thead>
						<tr class="checked title-row">
							<th width="100" field="f1" order="asc">标题标题标题标题标题标题</th>
							<th width="100" field="f2" order="desc">标题2</th>
							<th field="f3">标题3</th>
						</tr>
						<tr class="checked title-row">
							<th width="100">标题11</th>
							<th width="100">标题22</th>
							<th>标题33</th>
						</tr>
					</thead>
					<tr class="checked title-row">
						<td width="100">这一行是全选</td>
						<td width="">b1</td>
						<td>c1</td>
					</tr>
					<tr class="checked">
						<td rowspan="2">a2</td>
						<td colspan="2">b2</td>
					</tr>
					<tr class="checked">
						<td>b3</td>
						<td>c3</td>
					</tr>
					<tr class="checked">
						<td colspan="2">a4</td>
						<td rowspan="2">b4</td>
					</tr>
					<tr class="">
						<td>a5</td>
						<td>b5</td>
					</tr>
					<tr class="checked">
						<td>a6</td>
						<td>b6</td>
						<td>c6</td>
					</tr>
					<tfoot>
						<tr class="checked">
							<th><input type="checkbox" class="user" />自定义</th>
							<th>foot2</th>
							<th>foot3</th>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
	</div>
	<div class="col-md-12 col-sm-12 col-xs-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					列表数据
					<div class="btn btn-xs btn-danger pull-right redo">重绘</div>
				</h3>
			</div>
			<div class="panel-body list-data padding-0" contenteditable="true" style="height: 550px; overflow: none;">
				<textarea class="compnent-data" style="width: 100%; height: 100%; resize: none; border: none;">
{
	header : true,
	footer : true,
	search : true,
	checkbox : true,
	title : '测试列表',
	searchDefaultText : '输入查询关键字',
	searchFieldName : 'search',
	pageFieldName : 'page',
	pageSizeFieldName : 'pageSize',
	paginate : function(page, pageSize, key, order) {
		console.dir(this);
		var msg = 'page: ' + page + ' -- pageSize: ' + pageSize + ' -- searchKey: ' + key + '\n';
		
		if (order)
			$.each(order, function(key, val) {
				msg += key + ' : ' + val + '\n';
			});
		
		console.dir(order);
		alert(msg);
	},
	fixTableStyle : true,
	btns : [ {
		text : '按钮1',
		icon : 'icon-user',
		style : 'btn-default',
		click : function(page, pageSize, key) {
			alert('this is button 1');
		}
	}, {
		text : '按钮组2',
		icon : 'icon-key',
		style : 'btn-primary',
		click : function(page, pageSize, key) {
			alert('this is button 2.');
		},
		children : [ {
			text : '子按钮1',
			icon : 'icon-user',
			click : function(page, pageSize, key) {
				alert('this is a child button 1');
			}
		}, {
			split : true
		}, {
			text : '子按钮2',
			//icon : 'icon-key',
			click : function(page, pageSize, key) {
				alert('this is a child button 2');
			}
		} ]
	}, {
		text : '按钮3',
		style : 'btn-danger',
		click : function(page, pageSize, key) {
			alert('this is button 3.');
		}
	} ]
}
				</textarea>
			</div>
		</div>
	</div>
</div>
<!-- <script src="${request.contextPath}/js/suredy-list.js"></script> -->
<script type="text/javascript">
	require([ 'suredyList' ], function() {
		var table = $('.list-container').html();

		$('div.btn.redo').on('click', function() {
			$('.list-container').html(table);

			var data = eval('(' + $('.compnent-data').val() + ')');

			Suredy.List('.my-list', data);
			//$('.my-list').list(data);

			console.log(Suredy.List.checked($('.my-list')));
		});

		$('div.btn.redo').trigger('click');
	});
</script>