<@t.header> </@t.header> <@t.body>


<div class="container suredy-form">
	<div class="row">
	
		<div class="col-md-12 col-sm-12 text-center">
			<h3>这里是首页啊</h3>
		</div>
	
	
		<div class="col-md-12 col-sm-12 text-center">
			<div class="btn btn-primary" id="refresh">Refresh</div>
		</div>
		<div class="col-md-12 col-sm-12"></div>
		<div class="col-md-12 col-sm-12 text-center">
			<div class="btn btn-info" id="logout">SignOut</div>
		</div>
	</div>
</div>
</@t.body> <@t.foot>
<script type="text/javascript">
	require(['suredyModal'], function(Modal) {
		$('#logout').click(function() {
			alert('logouted');
			/*
			$.ajax({
				url : 'ou/org/save',
				dataType : 'json',
				type : 'POST',
				data : $('#form').serialize(),
				success : function(success) {
					if (!success) {
						alert('保存单位信息失败！');
					} else if (!success.success) {
						alert('保存单位信息失败！\n\n' + success.msg);
					} else {
						alert('保存单位成功！');
						Suredy.loadContent('user/list');
						Modal.closeModal();
					}
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + b);
				}
			});
			*/
		});
		$('#refresh').click(function() {
			alert('refreshed');
			/*
			$.ajax({
				url : 'ou/org/save',
				dataType : 'json',
				type : 'POST',
				data : $('#form').serialize(),
				success : function(success) {
					if (!success) {
						alert('保存单位信息失败！');
					} else if (!success.success) {
						alert('保存单位信息失败！\n\n' + success.msg);
					} else {
						alert('保存单位成功！');
						Suredy.loadContent('user/list');
						Modal.closeModal();
					}
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + b);
				}
			});
			*/
		});
	});
</script>
</@t.foot>
