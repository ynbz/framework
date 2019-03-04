<div class="row">
	<div class="col-md-3 col-sm-3">
		<input type="hidden" value="" id="nodeId" />
		<div class="row" style="padding: 3px 15px"><h5>栏目分类(此处不能管理栏目)</h5></div>
		<div class="channel-tree" style="max-height:800px; overflow: auto;"  >加载中......</div>
	</div>

	<div class="col-md-9 col-sm-9">
		<div class="row" style="padding: 6px 15px">
			<div class="col-md-4 col-sm-4" style="padding: 0px;">
				<div class="btn btn-primary btn-sm" id="newArticle"><i class="icon-plus"></i> 新建</div>
				<div class="btn btn-primary btn-sm" id="editArticle"><i class="icon-edit"></i> 修改</div>	
				<div class="btn btn-danger btn-sm" id="removeArticle"><i class="icon-remove"></i> 删除</div>	
				<a id="editor" style="display:none" href="#" target="_blank"><label id="editorClick">新窗口<label></a>		
			</div>	
			<div class="col-md-8 col-sm-8" style="padding: 0px;">
				<div class="input-group input-group-sm">
					<input type="text" class="form-control" name="keyword" id="keyword" placeholder="&lt; 标题或内容中按关键字搜索' &gt;" />
					<div class="input-group-btn"><button class="btn btn-primary" id="btn-search"><i class="icon-search"></i> 检索</button></div>
				</div>		
			</div>																									  			
		</div>	
		<div id="articleList">
			<table class="article-list" data-page="${pageIndex ! '1'}" data-page-size="${pageSize ! '20'}" data-count="${count ! '0'}">
				<tr class="title-row">
					<th style="display: none"></th>
					<th>标题</th>
					<th>所属栏目</th>
					<th>发布时间</th>
					<th>发布人</th>
				</tr>
				<#if data??> <#list data as atricle>
				<tr>
					<td style="display: none">${atricle.id}</td>
					<td>${atricle.title}</td>
					<td>${atricle.channel.fullName}</td>
					<td>${atricle.publishedTime}</td>
					<td>${user.publisher.fullName}</td>
				</tr>
				</#list> </#if>
			</table>
		</div>	
	</div>	
</div>
<script type="text/javascript">
	require(['suredyTree','suredyList','suredyModal', 'notify'], function(Tree, List, Modal) {
		var listConfig = ({
			header : false,
			footer : true,
			search : false,
			checkbox : true,			
			paginate : function(page, size) {
				var url='${request.contextPath}/app/cms/manager?page=' + page + '&size=' + size+'&channelId='+$("#nodeId").val() + '&keyword=' + $("#keyword").val();
				$.get(url, function(html, textStatus, jqXHR) {
					var $html = $(html);
						$("#articleList").html($("#articleList", $html).html());
						List('.article-list', listConfig);
				}, 'html');
			}
		});	
		List('.article-list', listConfig);
		
		Tree('.channel-tree', '${request.contextPath}/app/cms/channel/tree', {
			autoCollapse : false,
			leafCheckbox : false,
			folderCheckbox : false,
			inContainer : false,
			style : 'file'
		});
			
		$('.channel-tree').on(Suredy.Tree.nodeClick, function(event, $node) {
			var nodeData = Tree.data($node);				
			if (nodeData.id){
				$('#nodeId').val(nodeData.id);
			} else {
				$('#nodeId').val('');
			} 
			var url='${request.contextPath}/app/cms/manager?channel='+nodeData.id;
			$.get(url, function(html, textStatus, jqXHR) {
				var $html = $(html);
				$("#articleList").html($("#articleList", $html).html());
				List('.article-list', listConfig);
			}, 'html');	
		});
	
		$('#btn-search').on('click', function() {			
			var url='${request.contextPath}/app/cms/manager?keyword=' + $("#keyword").val();
			$.get(url, function(data, textStatus, jqXHR) {
				var $html = $(data);
				$("#articleList").html($("#articleList", $html).html());
				List('.article-list', listConfig);
			}, 'html');
		});	
		


		$('#newArticle').on('click', function() {				
			$('#editor').attr('href', '${request.contextPath}/app/cms/article/form?channelId=' + $('#nodeId').val());
			$('#editorClick').click();		
		});
 
		$('#editArticle').on('click', function() {	
			var checked = List.checked($('.article-list'));
			if (checked.length == 0 ) {
				alert('请选择需要修改的文章!');
				return;
			} else if (checked.length > 1) {
				alert('只能修改一个文章!');
				return;
			} else {
				var articleId = $( $( checked[0]).find('td' )[1] ).html();
				var uri = '${request.contextPath}/app/cms/article/form?articleId=' + articleId;
				Modal.showModal({
					size : 'lg',
					icon : 'icon-edit',
					title : '修改文章',
					showFoot : false,
					uri : uri
				});
			}
			
		});

		$('#removeArticle').on('click', function() {	
			var checked = List.checked($('.article-list'));
			if (checked.length == 0 ) {
				alert('请选择需要删除的文章!');
				return;
			} else if (checked.length > 1) {
				alert('只能删除单份文章!');
				return;
			} else {
				if(confirm('是否确认删除该文章，删除后将不能进行恢复！')){
					var articleId = $( $( checked[0]).find('td' )[1] ).html();
					$.ajax({
							url : '${request.contextPath}/app/cms/article/delete?articleId=' + articleId,
							dataType : 'json',
							type : 'POST',
							success : function(success) {
								if (!success) {
									alert('文章删除失败！');
								} else if (!success.success) {
									alert('文章删除失败！\n\n' + success.msg);
								} else {
									$.notify({title:'提示：',message:'删除文章成功！'});
									Suredy.loadContent('${request.contextPath}/app/cms/manager');
								}
							},
							error : function(a, b, c) {
								alert('服务器错误! ' + b);
							}
						});
				}
			} 
			
		});


	});
</script>	