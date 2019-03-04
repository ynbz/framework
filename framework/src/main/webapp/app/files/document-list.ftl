<div class="row">
	<form>
	  	<div class="col-md-12 col-sm-12">	
			<div class="form-group margin-0">
				<div class="input-group">
					<div class="input-group-addon">全文检索</div>
					<input type="text" class="form-control" id="keyword" name="keyword" placeholder="&lt;按关键字搜索,多关键字可用空格分隔&gt;" autocomplete="off" />
					<div class="btn btn-info input-group-addon" id="btn-search"><i class="icon-search"></i> 搜索</div>
				</div>
			</div>
	  	</div>		
	</form>	
</div>
<div class="row">&nbsp;</div>
<div class="row" style="overflow-x: auto;">
    <input type="hidden" name="typeId" id="typeId" value="${typeId}" />
	<div id="fileList" class="col-md-12 col-sm-12">
		<table class="file-list table table-hover" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>10</#if>" data-count="<#if count??>${count}<#else>0</#if>" style="width: 100%;">
			<tr class="title-row">
				<th>&lt;文件列表&gt; 共有 <font color="#e11">${count}</font> 组文件</th>
			</tr>
			<#if data??> <#list data as info>
			<tr>
				<td>
					<div class="text-info" style="font-size:16px;">[${info.folder.fullName}] - ${info.fileTitel}&nbsp;&nbsp;&nbsp;上传人：${info.uploader}</div>
					<blockquote>
						<#if info.files??> <#list info.files as file>
							<div style="font-size:14px; padding-left:12px;"><a class="download-avaiable" href="#" data-url="${request.contextPath}/file/download/?fileId=${file.id}"><i class="icon-file"></i> ${file.name}${file.suffix}</a>&nbsp;&nbsp;&nbsp;上传时间:${(file.uploadTime?string('yyyy-MM-dd'))!''}</div>
						</#list> </#if>	
					</blockquote>				
				</td>
			</tr>
			</#list> </#if>		
		</table>
	</div>
</div>

<script type="text/javascript">
var listConfig;
require(['suredyList'], function(List) {
	var listConfig = ({
		header : false,
		footer : true,
		search : false,
		checkbox : false,			
		paginate : function(page, size) {
			var typeId=$('#typeId').val();
			var url='${request.contextPath}/files/document/list?page=' + page + '&size=' + size+'&typeId='+typeId;
			
			$.post(url, $('#form').serialize() , function(html, textStatus, jqXHR) {
				var $html = $(html);
					$("#fileList").html($("#fileList", $html).html());
					List('.file-list',listConfig);
			}, 'html');
		}
	});
    List('.file-list',listConfig);	

	$('.download-avaiable').on('click', function(){
		$(this).attr('href', $(this).data("url"));
	});
	
	$('#btn-search').on('click', function(){
		var key = $('#keyword').val();
		if (key != '') {
			var data = {keyword : key};
			Suredy.loadContent('${request.contextPath}/files/search', {postData:data});
		}
	});
	
	$(document).keydown(function(event){  
		 if(event.keyCode == 13){  
			 $('#btn-search').click();   
		 }   
	}); 
});


</script>