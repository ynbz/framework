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
				<div class="form-inline">
					<div class="btn btn-info" id="newIncDism">
						<i class="icon-plus"></i> 创建收文
					</div>
				</div>
			</div>
			<div class="row">
				<div id="searchid" style="display: none; padding-left: 0px;">
					<form id="form" name="form">
						<input type="hidden" name="fileTypeCode" value="${fileTypeCode}" />
						<div class="row" style="padding-top: 8px;">
							<div class="form-inline col-md-12" >
								<div class="form-group bottom-input">
									<div class="input-group" style="width: 400px;">
										<div class="input-group-addon" style="width: 85px;">开始时间</div>
										<input type="text" class="form-control datetimepicker"  id="startTime"  data-format="yyyy-MM-dd hh:MM:ss"  name="startTime" value="" />
									</div>
								</div>

								<div class="form-group bottom-input">
									<div class="input-group" style="width: 400px;">
										<div class="input-group-addon" style="width: 85px;">结束时间</div>
										<input type="text" class="form-control datetimepicker"  id="endTime" name="endTime" value="" data-format="yyyy-MM-dd hh:MM:ss" />
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		<div class="row" style="overflow-x: auto;padding-top: 5px;">
			<div id="incDismList">
				<table class="incdism-list" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>" data-count="<#if count??>${count}<#else>0</#if>"style="width: 100%;">
					<tr class="title-row">
						<th style="display: none"></th>
						<th>发文题名</th>
						<th>创建人</th>
						<th>创建时间</th>
					</tr>
					<#if data??> <#list data as dism>
					<tr  style="cursor: pointer;" data-uri="${request.contextPath}/incdism/view/${dism.id}">
						<td style="display: none" >${dism.id}</td>
						<td>${dism.title}</td>
						<td>${dism.creatorFullName}</td>
						<td>${(dism.createTime?string('yyyy-MM-dd hh:MM:ss'))!''}</td>
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
require(['suredyList','jqueryForm','suredyDatetimepicker'], function(List) {
	var listConfig = ({
		header : false,
		footer : true,
		search : false,
		checkbox : true,			
		paginate : function(page, size) {
			var url='${request.contextPath}/incdism/list.do?page=' + page + '&size=' + size;
			$.post(url, $('#form').serialize() , function(html, textStatus, jqXHR) {
				var $html = $(html);
					$("#incDismList").html($("#incDismList", $html).html());
					List('.incdism-list',listConfig);
			}, 'html');
		}
	});
    List('.incdism-list',listConfig);	
    
	$("#conditionbtn").click(function(){
		var btn = $('i', $(this));
		if (btn.hasClass('icon-chevron-down')) {
			btn.removeClass('icon-chevron-down');
			btn.addClass('icon-chevron-up');
			$('#properSearchbtn').show();
			$('#searchid').show();
			
		} else if (btn.hasClass('icon-chevron-up')) {
			btn.removeClass('icon-chevron-up');
			btn.addClass('icon-chevron-down');
			$('#properSearchbtn').hide();
			$('#searchid').hide();
		}
	});	
	$("#properSearchbtn").click(function(){
		var url='${request.contextPath}/incdism/list.do';
		$.post(url, $('#form').serialize() , function(html, textStatus, jqXHR) {
			var $html = $(html);
			$("#incDismList").html($("#incDismList", $html).html());
			List('.incdism-list',listConfig);
		}, 'html');
	});

	$("#newIncDism").click(function(){
		window.open('${request.contextPath}/incdism/newIncDis/${fileTypeCode}');
	});
});

</script>
<script type="text/javascript">
	$(function() {
		$('.incdism-list').delegate('tr', 'click', function(event) {
			var $this = $(this);

			var uri = $this.data('uri');

			if (!uri)
				return;

			window.open(uri, '_blank');
		});
	});
</script>
