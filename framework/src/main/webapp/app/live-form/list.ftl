<style type="text/css">
.hr-line {
	margin-top: 0px;
	margin-bottom: 0px;
	border: 0;
	border-top: 1px solid #eee;
}
.bottom-input {
	padding-bottom: 5px;
}

</style>
<div class="row">
	<div class="col-md-12" style="margin-left: 5px;">
		<div class="row" style="padding-top: 10px;">
			<div class="col-md-8">
				<form id="form" name="form">
					<div class="row" >
						<!-- <div class="col-md-4">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon">开始时间</div>
									<input type="text" class="form-control datetimepicker" id="startTime" name="startTime" value="${startTime}"  data-format="yyyy-MM-dd" data-foot="false" readonly/>
								</div>
							</div>
						</div>
						<div class="col-md-4">
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon">结束时间</div>
									<input type="text" class="form-control datetimepicker" id="endTime" name="endTime" value="" data-format="yyyy-MM-dd" data-foot="false" readonly />
								</div>
							</div>
						</div>
						 -->
						<div class="col-md-4">
							<div class="form-group bottom-input">
								<div class="input-group" >
									<div class="input-group-addon">文件类型编码</div>
									<input type="text" class="form-control"  id="fileTypeCode" name="fileTypeCode" value="" />
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="col-md-4">
				<div class="form-inline">
					<div class="btn btn-info pull-right" id="properSearchbtn">
						<i class="icon-search"></i> 查询
					</div>
				</div>
			</div>
		</div>
		<div class="row" style="overflow-x: auto;padding-top: 5px;">
			<div id="liveFormList">
				<table class="live-form-list" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>" data-count="<#if count??>${count}<#else>0</#if>"style="width: 100%;">
					<tr class="title-row">
						<th style="display: none"></th>
						<th>标题</th>
						<th>文件类型编码</th>
						<th>创建人</th>
						<th>创建时间</th>
					</tr>
					<#if data??> <#list data as lifrom>
					<tr  style="cursor: pointer;" data-uri="${request.contextPath}/live-form/open/${lifrom.id}">
						<td style="display: none" >${lifrom.id}</td>
						<td>${lifrom.title}</td>
						<td>${lifrom.fileTypeCode}</td>
						<td>${lifrom.creatorFullName}</td>
						<td>${(lifrom.createTime?string('yyyy-MM-dd hh:MM:ss'))!''}</td>
					</tr>
					</#list> </#if>
				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var listConfig;
var tempTree;
var tempList;
require(['suredyList','suredyDatetimepicker'], function(List) {
	var listConfig = ({
		header : false,
		footer : true,
		search : false,
		checkbox : true,			
		paginate : function(page, size) {
			var url='${request.contextPath}/live-form/list.do?page=' + page + '&size=' + size;
			$.post(url, $('#form').serialize() , function(html, textStatus, jqXHR) {
				var $html = $(html);
					$("#liveFormList").html($("#liveFormList", $html).html());
					List('.live-form-list',listConfig);
			}, 'html');
		}
	});
    List('.live-form-list',listConfig);	
  
	$("#properSearchbtn").click(function(){
		var url='${request.contextPath}/live-form/list.do';
		$.post(url, $('#form').serialize() , function(html, textStatus, jqXHR) {
			var $html = $(html);
			$("#liveFormList").html($("#liveFormList", $html).html());
			List('.live-form-list',listConfig);
		}, 'html');
	});
});

</script>
<script type="text/javascript">
	$(function() {
		$('.live-form-list').delegate('tr', 'click', function(event) {
			var $this = $(this);

			var uri = $this.data('uri');

			if (!uri)
				return;

			window.open(uri, '_blank');
		});
	});
</script>
