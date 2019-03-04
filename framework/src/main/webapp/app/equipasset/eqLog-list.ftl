<div class="panel panel-info eqLog-list">
	 <div class="panel-heading">
		<h3 class="panel-title">设备日志</h3>
	</div>
	<div class="panel-body" id="eqLogList">
		<div class="row pull-right" style="padding-right: 31px;">
			<div class="form-inline">	
				<form id="time_form" >
						<div class="row" style="margin-bottom: 5px;">
							<div class="col-md-5 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">开始时间</div>
										<input type="text" class="form-control datetimepicker" id="startTime" name="startTime" value="${startTime}"  data-format="yyyy-MM-dd" data-foot="false" readonly/>
									</div>
								</div>
							</div>
							<div class="col-md-5 col-xs-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">结束时间</div>
										<input type="text" class="form-control datetimepicker" id="endTime" name="endTime" value="${endTime}"  data-format="yyyy-MM-dd" data-foot="false" readonly/>
									</div>
								</div>
							</div>
							<div class="btn btn-info pull-right"  id="timeSearchbtn" >
								<i class="icon-search"></i> 查询
							</div>
						</div>
					</form>
			</div>
		</div>	
		<table class="eqLogTab-list" 
			data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>25</#if>" data-count="<#if count??>${count}<#else>0</#if>">
			<tr class="title-row">
				<th > 操作人</th>
				<th >操作类型</th>
				<th >操作时间</th>
				<th >日志类型</th>
			</tr>
			<#if data??> <#list data as d>
				<tr style="cursor: pointer;" data-uri="${request.contextPath}/EquipAssetCtrl/getLookPage.do?equipid=${d.eqId}">
					<td >${(d.name)}</td>
					<td >${(d.updateType==0)?string('PC端',(d.updateType==1)?string('桌面端',(d.updateType==2)?string('移动端','')))}</td>
					<td >${(d.updateEq?string('yyyy-MM-dd HH:mm:ss'))}</td>		
					<td ><#if d.laterContent?index_of('新增设备')=-1>
						<span style="color:#f0ad4e;">修改设备</span>
						<#else>
						<span style="color:#5bc0de;">新增设备</span>
						</#if>
					</td>					
				</tr>
				</#list>
			</#if>
		</table>	
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$('.eqLogTab-list').delegate('tr', 'click', function(event) {
			var $this = $(this);

			var uri = $this.data('uri');

			if (!uri)
				return;

			window.open(uri, '_blank');
		});
	});
</script>
<script type="text/javascript">
	require(['suredyList', 'suredyModal','suredyDatetimepicker'], function(List, Modal) {
		var listConfig = ({
			header : false,
			footer : true,
			search : false,
			checkbox : true,
			paginate : function(page,size,key) {
				var url='eqUpdateLog/eqLog-list';
				var startTime = $('#startTime').val();
				var endTime = $('#endTime').val();
				
				$.post(url,{startTime:startTime,endTime:endTime,page:page,size:size},function(html, textStatus, jqXHR) {
					var $html = $(html);
					$("#eqLogList").html($("#eqLogList", $html).html());
					List('.eqLogTab-list', listConfig);
				}, 'html');
			},
		
		});
 
		$(".eqLog-list").on('click','#timeSearchbtn',function(){
			var url='eqUpdateLog/eqLog-list';
			$.post(url, $('#time_form').serialize() , function(html, textStatus, jqXHR) {
				var $html = $(html);
				$("#eqLogList").html($("#eqLogList", $html).html());
				List('.eqLogTab-list', listConfig);
			}, 'html');
		});
		List('.eqLogTab-list', listConfig);
	});
</script>	