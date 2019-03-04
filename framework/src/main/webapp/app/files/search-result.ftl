<div class="row">
	<form>
	  	<div class="col-md-12 col-sm-12">	
			<div class="form-group margin-0">
				<div class="input-group">
					<div class="input-group-addon">全文检索</div>
					<input type="text" class="form-control" id="keyword" name="keyword" placeholder="&lt;按关键字搜索,多关键字可用空格分隔&gt;" autocomplete="off" value="${keyword}" />
					<div class="btn btn-info input-group-addon" id="btn-search"><i class="icon-search"></i> 搜索</div>
				</div>
			</div>
	  	</div>		
	</form>	
</div>
<div class="row">&nbsp;</div>
<div class="row" style="overflow-x: auto;">
	<div id="fileList" class="col-md-12 col-sm-12">
		<table class="file-list table table-hover" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>10</#if>" data-count="<#if count??>${count}<#else>0</#if>"style="width: 100%;">
			<tr class="title-row">
				<th>共有 <font color="#e11">${count}</font> 份文件匹配</th>
			</tr>
			<#if data??> <#list data as doc>
			<tr>
				<td>
					<blockquote>
						<div class="text-info" style="font-size:16px;"><a class="download-avaiable" href="#" data-url="${request.contextPath}/file/download/?fileId=${doc.id}">${doc['name']}</a></div>
						<div style="font-size:14px;"><#if doc['summary']??>${doc['summary']}<#else>${doc['text']?substring(0,512)}</#if></div>
							
						<div class="text-success" style="font-size:14px;">作者：<#if doc['author']??>${doc['author']}<#else>未知</#if>&nbsp;&nbsp;&nbsp;时间:${(doc['lastModified']?string('yyyy-MM-dd'))!''}</div>
					</blockquote>		
				</td>
			</tr>
			</#list></#if>
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
			var data = {keyword : $('#keyword').val(), page:page, size:size};
			if (data != '') {
				Suredy.loadContent('${request.contextPath}/files/search', {postData:data});
			}
		}
	});
	
    List('.file-list',listConfig);	

	$('#btn-search').on('click', function(){
		var key = $('#keyword').val();
		if (key != '') {
			var data = {keyword : key};
			Suredy.loadContent('${request.contextPath}/files/search', {postData:data});
		}
	});
	
	$('.download-avaiable').on('click', function(){
		$(this).attr('href', $(this).data("url"));
	});
	
	$(document).keydown(function(event){  
		 if(event.keyCode == 13){  
			 $('#btn-search').click();   
		 }   
	}); 
});


</script>