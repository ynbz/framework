<div class="row">
	<div class="col-md-12 col-sm-12">
		<input type="hidden" value="" id="nodeId" />
		<div class="row" style="padding: 6px 15px">
			<div class="btn btn-primary btn-sm" id="newChannel"><i class="icon-plus"></i> 新建</div>
			<div class="btn btn-primary btn-sm" id="editChannel"><i class="icon-edit"></i> 修改</div>	
			<div class="btn btn-danger btn-sm" id="removeChannel"><i class="icon-remove"></i> 删除</div>																		  			
		</div>
		<div class="channel-tree" style="max-height:800px; overflow: auto;"  >加载中......</div>
	</div>	
</div>
<script type="text/javascript">
	require(['suredyTree','suredyModal', 'notify'], function(Tree,  Modal) {

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
		});

		
		$('#newChannel').on('click', function() {	
			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : '创建栏目',
				showFoot : false,
				uri : '${request.contextPath}/app/cms/channel/form?parentId=' + $('#nodeId').val()
			});
		
		});
	

		// edit menu btn click
		$('#editChannel').on('click', function() {	
			var nodeId = $('#nodeId').val();
			if (nodeId == '') {
				alert('请选择需要修改的栏目！');
				return;
			}
			Modal.showModal({
				size : 'lg',
				icon : 'icon-edit',
				title : '修改栏目',
				showFoot : false,
				uri : '${request.contextPath}/app/cms/channel/form?channelId=' + nodeId
			});
		
		});
		// remove btn click
		$('#removeChannel').on('click', function() {
			var nodeId = $('#nodeId').val();
			
			if (nodeId == '') {
				alert('请选择需要删除的栏目！');
				return;
			}
	
			var msg = '是否确认删除【选中的栏目】？\n\n\提示：\n\该操作将会删除该栏目及全部子栏目！\n\请谨慎操作！';
	
			if (!window.confirm(msg)) {
				return;
			}
	
			$.ajax({
				url : '${request.contextPath}/app/cms/channel/delete?channelId=' + nodeId,
				type : 'POST',
				success : function(msg) {
					if (!msg) {
						alert('Unknown exception!');
					} else if (!msg.success) {
						alert("删除栏目失败！\n\n" + msg.msg);
					} else {
						$.notify({title:'提示：',message:'删除栏目成功！'});
						Suredy.loadContent('${request.contextPath}/app/cms/channel/manager');
					}
				},
				error : function(a, b, c) {
					alert('Server error! ' + b);
				}
			});
		});	
		

	});
</script>	