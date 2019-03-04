<div class="row margin-0">
	<div class="col-md-10 col-sm-10 col-xs-12 col-md-offset-1 col-sm-offset-1">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Line Chart</h3>
			</div>
			<div class="panel-body padding-0">
				<div id="line" style="width: 100%; height: 500px;"></div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
<!--
	require(['echarts'], function(echarts) {
		var line = echarts.init($('#line').get(0), 'macarons');
		line.showLoading({
			text : '正在努力的读取数据中...'
		});
		line.hideLoading();
		var option = {
			title : {
				text : '未来一周气温变化',
				subtext : '纯属虚构'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '最高气温', '最低气温' ]
			},
			toolbox : {
				show : true,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
			} ],
			yAxis : [ {
				type : 'value',
				axisLabel : {
					formatter : '{value} °C'
				}
			} ],
			series : [ {
				name : '最高气温',
				type : 'line',
				data : [ 11, 11, 15, 13, 12, 13, 10 ],
				markPoint : {
					data : [ {
						type : 'max',
						name : '最大值'
					}, {
						type : 'min',
						name : '最小值'
					} ]
				},
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			}, {
				name : '最低气温',
				type : 'line',
				data : [ 1, -2, 2, 5, 3, 2, 0 ],
				markPoint : {
					data : [ {
						name : '周最低',
						value : -2,
						xAxis : 1,
						yAxis : -1.5
					} ]
				},
				markLine : {
					data : [ {
						type : 'average',
						name : '平均值'
					} ]
				}
			} ]
		};

		line.setOption(option);

		//line.clear();

		//line.dispose();
	});
</script>