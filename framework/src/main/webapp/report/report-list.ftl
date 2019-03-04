<div class="row">
	<#if data??> 
		<#list data as report>
			<div class="col-md-2 col-sm-2 text-center">
				<a href="javascript:void(0);" class="run-report" data-report-id="${report.id}" data-file-id="${report.fileId}"><i class="icon-file icon-4x"></i><p>${report.name}</p></a>
			</div>
		</#list>
	 <#else>	
			<div class="col-md-12 col-sm-12 text-center">当前分类下无报表资源,或者您无权访问当前分类下的资源</div>	 
	 </#if>
</div>
<script type="text/javascript">
require(['suredyModal'], function(Modal) {
	$('.run-report').on('click', function() {		
		var data = {reportId:$(this).data('report-id'), fileId:$(this).data('file-id')};
		Suredy.loadContent('${request.contextPath}/report/run/default', {postData:data});
	});
});	
</script>
