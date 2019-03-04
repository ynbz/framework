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

<script src="${request.contextPath}/core/js/echarts-min.js"></script>
<script type="text/javascript">
<!--
	$(function() {
		var line = echarts.init($('#line').get(0), 'macarons');
		line.showLoading({
			text : '正在努力的读取数据中...'
		});
		line.hideLoading();
		var option = {
			    title : {
			        text: '某站点用户访问来源',
			        subtext: '纯属虚构',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient : 'vertical',
			        x : 'left',
			        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {
			                show: true, 
			                type: ['pie', 'funnel'],
			                option: {
			                    funnel: {
			                        x: '25%',
			                        width: '50%',
			                        funnelAlign: 'left',
			                        max: 1548
			                    }
			                }
			            },
			            restore : {show: true},
			            saveAsImage : {show: true}
			        }
			    },
			    calculable : true,
			    series : [
			        {
			            name:'访问来源',
			            type:'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:[
			                {value:335, name:'直接访问'},
			                {value:310, name:'邮件营销'},
			                {value:234, name:'联盟广告'},
			                {value:135, name:'视频广告'},
			                {value:1548, name:'搜索引擎'}
			            ]
			        }
			    ]
			};

		line.setOption(option);

		//line.clear();

		//line.dispose();
	});
</script>