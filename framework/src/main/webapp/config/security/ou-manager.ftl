
	<div class="panel panel-default">
		<div class="panel-heading">
			<input type="hidden" value="" id="nodeType" />
			<input type="hidden" value="" id="nodeId" />
			<i class="icon-sitemap"></i> 组织机构管理 （<i class="bg-danger select-node-info" id="selected-info">请点击选择</i>）
		</div>
		
		<div class="panel-body">
			<div class="row">												
				<div class="col-md-12 col-sm-12">
					<div class="btn btn-info" id="newOrg">
						<i class="icon-plus"></i> 创建单位
					</div>
					<div class="btn btn-info" id="newUnit">
						<i class="icon-plus-sign"></i> 创建部门
					</div>												
					<div class="btn btn-info hidden" id="edit">
						<i class="icon-edit "></i> 修改
					</div>
					<div class="btn btn-danger hidden" id="remove">
						<i class="icon-remove"></i> 删除
					</div>				  
				</div>
			</div>
			<div class="row">&nbsp;</div>			
			<div class="row">
				<div class="list-group col-md-12 col-sm-12">
					<div class="suredy-tree normal menu-tree" style="margin-left: 15px; border-top: 1px solid #ddd; max-height:600px; overflow:auto;"  >加载中......</div>
				</div>
			</div>
		</div>	
		
		<div class="panel-footer"></div>
	</div>

<!-- <script src="${request.contextPath}/js/suredy-tree.js"></script> -->
<script type="text/javascript">
	require(['suredyTree','suredyModal'], function(Tree,Modal){
		var ouTree = function() {
			Tree('.menu-tree','ou/tree', {
			});	
			
			$('.menu-tree').on(Suredy.Tree.nodeClick, function(event, $node){
				var nodeData = Tree.data($($node));
				var nodeType = Tree.data($node, 'text');
				if (nodeType.indexOf('[单位]') == -1 ) {
					$('#nodeType').val("unit");
				}  else {
					$('#nodeType').val("org");
				}
				$('#selected-info').html(nodeType);
				
				if (nodeData.id){
					$('#nodeId').val(nodeData.id);
					$('#edit').removeClass('hidden');
					$('#remove').removeClass('hidden');
				} else {
					$('#nodeId').val('');
					$('#edit').addClass('hidden');
					$('#remove').addClass('hidden');
				} 
			});
		};
		ouTree();	
		
		$('#newOrg').on('click', function() {		
			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : '创建单位',
				showFoot : false,
				uri : 'ou/org/create.do'
			});
		
		});
		
		$('#newUnit').on('click', function() {		
			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : '创建部门',
				showFoot : false,
				uri : 'ou/unit/create.do'
			});
		
		});

		// edit menu btn click
		$('#edit').on('click', function() {	
			var nodeId = $('#nodeId').val();
			var nodeType = $('#nodeType').val();
			if (nodeId == '') {
				alert('请选择需要修改的节点！');
				return;
			}
			Modal.showModal({
				size : 'lg',
				icon : 'icon-edit',
				title : nodeType == 'org' ? '修改单位信息' : '修改部门信息',
				showFoot : false,
				uri : nodeType == 'org' ? 'ou/org/edit.do?orgId=' + nodeId : 'ou/unit/edit.do?unitId=' + nodeId
			});
		
		});
	
		// remove btn click
		$('#remove').on('click', function() {
			var nodeId = $('#nodeId').val();
			var nodeType = $('#nodeType').val();
	
			if (nodeId == '') {
				alert('请选择需要删除的节点！');
				return;
			}
	
			var msg = '是否确认删除【选中的节点】？\n\n\
						提示：\n\
						该操作将会删除该节点下的所有子节点！\n\
						请谨慎操作！';
	
			if (!window.confirm(msg)) {
				return;
			}
	
			$.ajax({
				url : nodeType == 'org' ? 'ou/org/delete.do' : 'ou/unit/delete.do',
				type : 'POST',
				data : {
					nodeId : nodeId
				},
				success : function(msg) {
					if (!msg) {
						alert('Unknown exception!');
					} else if (!msg.success) {
						alert("删除单位/部门失败！\n\n" + msg.msg);
					} else {
						alert("删除单位/部门成功！");
	
						$('#selected-info').html('请点击选择');
	
						ouTree();
	
						$('#edit').addClass('hidden');
						$('#remove').addClass('hidden');
					}
				},
				error : function(a, b, c) {
					alert('Server error! ' + b);
				}
			});
		});
	});
</script>	
