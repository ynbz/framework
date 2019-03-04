<div class="panel panel-default">
	<div class="panel-heading">
		<input type="hidden" value="" id="menuId" />
		<i class="icon-magnet"></i>基础权限配置
	</div>
	<div class="panel-body">
		<div class="col-md-12 col-sm-12">	
			<form name="form" id="form">
				<input type="hidden" name="resources" id="resources" value="" />
				<div role="tabpanel" class="suredy-tabs">
					<!-- tab pages -->
					<ul class="nav nav-tabs" role="tablist">
						<li role="presentation" class="active">
							<a href="#menuTab" aria-controls="menuTab" role="tab" data-toggle="tab"><strong>菜单资源</strong></a>
						</li>
						<li role="presentation">
							<a href="#segmentTab" aria-controls="segmentTab" role="tab" data-toggle="tab"><strong>单点控制</strong></a>
						</li>
						<li role="presentation">
							<a href="#reportTab" aria-controls="reportTab" role="tab" data-toggle="tab"><strong>报表资源</strong></a>
						</li>
						<li role="presentation">
							<a href="#formTab" aria-controls="formTab" role="tab" data-toggle="tab"><strong>表单资源</strong></a>
						</li>
						<li role="presentation">
							<a href="#fileTab" aria-controls="fileTab" role="tab" data-toggle="tab"><strong>文件资源</strong></a>
						</li>																	
					</ul>
					<!-- Tab panes -->
					<div class="tab-content">
						<!-- 菜单资源Tab -->
						<div role="tabpanel" class="tab-pane active" id="menuTab">
							<div style="max-height:500px; height:500px; overflow:auto;">
								<div id="menu-tree">加载中......</div>
							</div>
						</div>
						<!-- 菜单资源Tab End-->
						
						<!-- 单点操作Tab -->
						<div role="tabpanel" class="tab-pane" id="segmentTab">
							<div style="max-height:500px; height:500px; overflow:auto;">
								<div id="segment-tree">加载中......</div>
							</div>
						</div>
						<!-- 单点操作Tab End-->
						
						<!-- 报表资源Tab -->
						<div role="tabpanel" class="tab-pane" id="reportTab">
							<div style="max-height:500px; height:500px; overflow:auto;">
								<div id="report-type-tree">加载中......</div>
							</div>
						</div>	
						<!-- 报表资源Tab End-->	
						
						<!-- 表单资源Tab -->
						<div role="tabpanel" class="tab-pane" id="formTab">
							<div style="max-height:500px; height:500px; overflow:auto;">
								<div id="form-type-tree">加载中......</div>
							</div>
						</div>	
						<!-- 表单资源Tab End-->
						<!-- 文件资源Tab -->
						<div role="tabpanel" class="tab-pane" id="fileTab">
							<div style="max-height:500px; height:500px; overflow:auto;">
								<div id="file-type-tree">加载中......</div>
							</div>
						</div>	
						<!-- 文件资源Tab End-->											
					</div>
				</div>
			</form>	
		</div>
	</div>
</div>
	<div class="row text-center">
		<button class="btn btn-info" id="btn-save" type="button">
			<i class="icon-save"></i>保存
		</button>
	</div>	
<!-- <script src="${request.contextPath}/js/suredy-tree.js"></script> -->
<script type="text/javascript">
	require(['suredyTree','suredyModal'], function(Tree,Modal) {
		var loadTree = function() {
			Tree('#menu-tree', '${request.contextPath}/config/resource/basic?type=1', {
				autoCollapse : false,
				multiselect : true,
				leafCheckbox : true,
				folderCheckbox : true,
				inContainer:false,
				size:'sm',
				style : 'file'
			});	
			Tree('#segment-tree', '${request.contextPath}/config/resource/basic?type=2', {
				autoCollapse : false,
				multiselect : true,
				leafCheckbox : true,
				folderCheckbox : true,
				inContainer:false,
				size:'sm',
				style : 'file'
			});	
			Tree('#report-type-tree', '${request.contextPath}/config/resource/basic?type=3', {
				autoCollapse : false,
				multiselect : true,
				leafCheckbox : true,
				folderCheckbox : true,
				inContainer:false,
				size:'sm',
				style : 'file'
			});	
			Tree('#form-type-tree', '${request.contextPath}/config/resource/basic?type=4', {
				autoCollapse : false,
				multiselect : true,
				leafCheckbox : true,
				folderCheckbox : true,
				inContainer:false,
				size:'sm',
				style : 'file'
			});	
			Tree('#file-type-tree', '${request.contextPath}/config/resource/basic?type=5', {
				autoCollapse : false,
				multiselect : true,
				leafCheckbox : true,
				folderCheckbox : true,
				inContainer:false,
				size:'sm',
				style : 'file'
			});	
		};
		loadTree();	
	
		$('#btn-save').click(function() {
			var checked = [];

			$.each(Tree.checked($('#menu-tree>.suredy-tree')), function() {
				checked[checked.length] = Tree.data(this);
			});
			$.each(Tree.checked($('#segment-tree>.suredy-tree')), function() {
				checked[checked.length] = Tree.data(this);
			});			
			$.each(Tree.checked($('#report-type-tree>.suredy-tree')), function() {
				checked[checked.length] = Tree.data(this);
			});	
			$.each(Tree.checked($('#form-type-tree>.suredy-tree')), function() {
				checked[checked.length] = Tree.data(this);
			});	
			$.each(Tree.checked($('#file-type-tree>.suredy-tree')), function() {
				checked[checked.length] = Tree.data(this);
			});				

			var data = "";
			
			if (checked) {
				if (jQuery.type(checked) === "array") {
					var temp = new Array();
					$.each(checked, function(){
						if (this.resourceId) {	
							temp[temp.length] = this.resourceId;
						}
					});
					data = temp.join('-');
				} else  if (jQuery.type(checked) === "object") {
					if (checked.resourceId) {
						data = checked.resourceId;
					}
				}
			}
			$('#resources').val(data);
			
			$.ajax({
				type : 'POST',//发送请求的方式
				url : 'basic/permission-save.do',
				data : $('#form').serialize(),
				dataType : "json",//文件类型
				cache : false,
				timeout : 60000,//超时设置，单位为毫秒
				success : function(success) {
					if (!success) {
						alert('修改基本权限失败！');
					} else if (!success.success) {
						alert('修改基本权限失败！\n\n' + success.msg);
					} else {
						alert('修改基本权限成功！');
						Suredy.loadContent('basic/permission.do');
						Modal.closeModal();
					}
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + b);
				}
			});		
		});	
	});
</script>	
