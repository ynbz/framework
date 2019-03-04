<@t.header></@t.header> <@t.body>
<div class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<div class=" btn navbar-toggle collapsed" data-toggle="collapse"
				data-target="#left-menu-collapse" aria-expanded="false">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</div>
			<div class="navbar-brand">文件资源管理</div>
		</div>

		<div class="collapse navbar-collapse" id="left-menu-collapse">
			<p class="navbar-text navbar-right">
				<i class="icon-signout"></i>
				<a href="${request.contextPath}/logout" class="navbar-link"> 退出 </a>
				&nbsp;
			</p>			
			<p class="navbar-text navbar-right">
				<i class="icon-leaf"></i> <a href="${request.contextPath}/config/index" class="navbar-link">系统配置 </a>
			</p>
			<div class="left-menu">
				<div class="user-info">
					<i class="icon-user"></i>
					${Session['login_user'].name}
					<i class="icon-cog text-primary user-config" style="cursor: pointer;"></i>
				</div>
				<div class="menu-info">
					<div class="left-tree-menu"></div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="container-fluid index-container suredy-content">
	<div class="row">
		<form>
		  	<div class="col-md-12 col-sm-12">	
				<div class="form-group margin-0">
					<div class="input-group">
						<input type="text" class="form-control" id="keyword" name="keyword" placeholder="&lt;按关键字搜索,多关键字可用空格分隔&gt;" autocomplete="off"/>
						<div class="btn btn-info input-group-addon" id="btn-search"><i class="icon-search"></i> 搜索</div>
					</div>
				</div>
		  	</div>		
		</form>	
	</div>
	<div class="row text-center">
		<div class="col-md-12 col-sm-12">	
			<p>&nbsp;</p>
			<p>请在左侧选择分类浏览,或使用上方搜索功能...</p>
			<p>&nbsp;</p>
		</div>	
	</div>	
</div>
</@t.body> <@t.foot>
<!-- <script src="${request.contextPath}/js/suredy-tree-menu.js"></script>
<script src="${request.contextPath}/js/suredy-modal.js"></script> -->
<script type="text/javascript">
	require([ 'suredyModal', 'suredyTreeMenu' ], function(Modal, TreeMenu) {
		$('i.user-config').on('click', function() {
			Modal.showModal({
				title : '修改密码',
				showFoot : false,
				icon : 'icon-cog',
				uri : '${request.contextPath}/user-config'
			});
		});		
		
		TreeMenu('.left-tree-menu', '${request.contextPath}/files/folder-tree/user', {
			size : 'lg',
			style : 'file',
			autoCollapse : true
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
</@t.foot>
