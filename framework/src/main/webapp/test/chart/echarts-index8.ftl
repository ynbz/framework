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
		var idx = 1;
		var option = {
		    timeline : {
		        data : [
		            '2013-01-01', '2013-02-01', '2013-03-01', '2013-04-01', '2013-05-01',
		            { name:'2013-06-01', symbol:'emptyStar6', symbolSize:8 },
		            '2013-07-01', '2013-08-01', '2013-09-01', '2013-10-01', '2013-11-01',
		            { name:'2013-12-01', symbol:'star6', symbolSize:8 }
		        ],
		        label : {
		            formatter : function(s) {
		                return s.slice(0, 7);
		            }
		        }
		    },
		    options : [
		        {
		            title : {
		                text: '浏览器占比变化',
		                subtext: '纯属虚构'
		            },
		            tooltip : {
		                trigger: 'item',
		                formatter: "{a} <br/>{b} : {c} ({d}%)"
		            },
		            legend: {
		                data:['Chrome','Firefox','Safari','IE9+','IE8-']
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
		                                max: 1700
		                            }
		                        }
		                    },
		                    restore : {show: true},
		                    saveAsImage : {show: true}
		                }
		            },
		            series : [
		                {
		                    name:'浏览器（数据纯属虚构）',
		                    type:'pie',
		                    center: ['50%', '45%'],
		                    radius: '50%',
		                    data:[
		                        {value: idx * 128 + 80,  name:'Chrome'},
		                        {value: idx * 64  + 160,  name:'Firefox'},
		                        {value: idx * 32  + 320,  name:'Safari'},
		                        {value: idx * 16  + 640,  name:'IE9+'},
		                        {value: idx++ * 8  + 1280, name:'IE8-'}
		                    ]
		                }
		            ]
		        },
		        {
		            series : [
		                {
		                    name:'浏览器（数据纯属虚构）',
		                    type:'pie',
		                    data:[
		                        {value: idx * 128 + 80,  name:'Chrome'},
		                        {value: idx * 64  + 160,  name:'Firefox'},
		                        {value: idx * 32  + 320,  name:'Safari'},
		                        {value: idx * 16  + 640,  name:'IE9+'},
		                        {value: idx++ * 8  + 1280, name:'IE8-'}
		                    ]
		                }
		            ]
		        },
		        {
		            series : [
		                {
		                    name:'浏览器（数据纯属虚构）',
		                    type:'pie',
		                    data:[
		                        {value: idx * 128 + 80,  name:'Chrome'},
		                        {value: idx * 64  + 160,  name:'Firefox'},
		                        {value: idx * 32  + 320,  name:'Safari'},
		                        {value: idx * 16  + 640,  name:'IE9+'},
		                        {value: idx++ * 8  + 1280, name:'IE8-'}
		                    ]
		                }
		            ]
		        },
		        {
		            series : [
		                {
		                    name:'浏览器（数据纯属虚构）',
		                    type:'pie',
		                    data:[
		                        {value: idx * 128 + 80,  name:'Chrome'},
		                        {value: idx * 64  + 160,  name:'Firefox'},
		                        {value: idx * 32  + 320,  name:'Safari'},
		                        {value: idx * 16  + 640,  name:'IE9+'},
		                        {value: idx++ * 8  + 1280, name:'IE8-'}
		                    ]
		                }
		            ]
		        },
		        {
		            series : [
		                {
		                    name:'浏览器（数据纯属虚构）',
		                    type:'pie',
		                    data:[
		                        {value: idx * 128 + 80,  name:'Chrome'},
		                        {value: idx * 64  + 160,  name:'Firefox'},
		                        {value: idx * 32  + 320,  name:'Safari'},
		                        {value: idx * 16  + 640,  name:'IE9+'},
		                        {value: idx++ * 8  + 1280, name:'IE8-'}
		                    ]
		                }
		            ]
		        },
		        {
		            series : [
		                {
		                    name:'浏览器（数据纯属虚构）',
		                    type:'pie',
		                    data:[
		                        {value: idx * 128 + 80,  name:'Chrome'},
		                        {value: idx * 64  + 160,  name:'Firefox'},
		                        {value: idx * 32  + 320,  name:'Safari'},
		                        {value: idx * 16  + 640,  name:'IE9+'},
		                        {value: idx++ * 8  + 1280, name:'IE8-'}
		                    ]
		                }
		            ]
		        },
		        {
		            series : [
		                {
		                    name:'浏览器（数据纯属虚构）',
		                    type:'pie',
		                    data:[
		                        {value: idx * 128 + 80,  name:'Chrome'},
		                        {value: idx * 64  + 160,  name:'Firefox'},
		                        {value: idx * 32  + 320,  name:'Safari'},
		                        {value: idx * 16  + 640,  name:'IE9+'},
		                        {value: idx++ * 8  + 1280, name:'IE8-'}
		                    ]
		                }
		            ]
		        },
		        {
		            series : [
		                {
		                    name:'浏览器（数据纯属虚构）',
		                    type:'pie',
		                    data:[
		                        {value: idx * 128 + 80,  name:'Chrome'},
		                        {value: idx * 64  + 160,  name:'Firefox'},
		                        {value: idx * 32  + 320,  name:'Safari'},
		                        {value: idx * 16  + 640,  name:'IE9+'},
		                        {value: idx++ * 8  + 1280, name:'IE8-'}
		                    ]
		                }
		            ]
		        },
		        {
		            series : [
		                {
		                    name:'浏览器（数据纯属虚构）',
		                    type:'pie',
		                    data:[
		                        {value: idx * 128 + 80,  name:'Chrome'},
		                        {value: idx * 64  + 160,  name:'Firefox'},
		                        {value: idx * 32  + 320,  name:'Safari'},
		                        {value: idx * 16  + 640,  name:'IE9+'},
		                        {value: idx++ * 8  + 1280, name:'IE8-'}
		                    ]
		                }
		            ]
		        },
		        {
		            series : [
		                {
		                    name:'浏览器（数据纯属虚构）',
		                    type:'pie',
		                    data:[
		                        {value: idx * 128 + 80,  name:'Chrome'},
		                        {value: idx * 64  + 160,  name:'Firefox'},
		                        {value: idx * 32  + 320,  name:'Safari'},
		                        {value: idx * 16  + 640,  name:'IE9+'},
		                        {value: idx++ * 8  + 1280, name:'IE8-'}
		                    ]
		                }
		            ]
		        },
		        {
		            series : [
		                {
		                    name:'浏览器（数据纯属虚构）',
		                    type:'pie',
		                    data:[
		                        {value: idx * 128 + 80,  name:'Chrome'},
		                        {value: idx * 64  + 160,  name:'Firefox'},
		                        {value: idx * 32  + 320,  name:'Safari'},
		                        {value: idx * 16  + 640,  name:'IE9+'},
		                        {value: idx++ * 8  + 1280, name:'IE8-'}
		                    ]
		                }
		            ]
		        },
		        {
		            series : [
		                {
		                    name:'浏览器（数据纯属虚构）',
		                    type:'pie',
		                    data:[
		                        {value: idx * 128 + 80,  name:'Chrome'},
		                        {value: idx * 64  + 160,  name:'Firefox'},
		                        {value: idx * 32  + 320,  name:'Safari'},
		                        {value: idx * 16  + 640,  name:'IE9+'},
		                        {value: idx++ * 8  + 1280, name:'IE8-'}
		                    ]
		                }
		            ]
		        }
		    ]
		};

		line.setOption(option);

		//line.clear();

		//line.dispose();
	});
</script>