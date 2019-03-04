<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">	
		<form name="form" id="form">
		<#if data??>
			<input type="hidden" id="id" name="id" value="${data.id}" />
			<input type="hidden" id="parent" name="parent" value="${data.parent.id!''}" />
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">父节点</div>
					<input type="text" class="form-control" readonly="readonly" id="parentName" value="${data.parent.typeName}" data-toggle="collapse" href="#menu-tree" aria-expanded="false" aria-controls="menu-tree" />
					<div class="input-group-addon btn btn-default btn-load-menu-tree" data-toggle="collapse" href="#menu-tree" aria-expanded="false" aria-controls="menu-tree">
						<i class="icon-sitemap"></i>
					</div>
				</div>
			</div>
			<div class="collapse" id="menu-tree" style="border-top:1px solid #ddd; max-height:300px; overflow:auto;">
				<div class="templatetype-tree-for-parent" data-uri="menu-tree.do">加载中……</div>
			</div>
			<div class="row">&nbsp;</div>	
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">分类名称</div>
					<input type="text" class="form-control" id="typeName" name="typeName" value="${data.typeName}" />
				</div>
			</div>		
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">显示顺序</div>
					<input type="text" class="form-control" id="sort" name="sort" value="${data.sort}" />
				</div>
			</div>
			
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">模板代码</div>
					<input type="text" class="form-control" id="code" name="code" value="${data.code}" />
				</div>
			</div>
		</#if>	
		</form>	
	</div>
	<div class="text-center">
		<div class="btn btn-info" id="btn-save" >
			<i class="icon-save"></i> 保存
		</div>
	</div>	
</div>
<script type="text/javascript">
	require(['suredyTree','suredyModal'], function(Tree, Modal) {
		var setTree = function() {
			Tree('.templatetype-tree-for-parent','${request.contextPath}/config/templatetype/tree',{
				autoCollapse : false,
				inContainer : false,
				style : 'template',
				size : 'sm'
			});
			
			$('.templatetype-tree-for-parent .suredy-tree li>span+ul').collapse('show');
			
			$('.templatetype-tree-for-parent').on(Suredy.Tree.nodeClick, function(event, $node) {
				if (Tree.isActive($node)) {
					$('#parentName').val(Tree.data($node,'text'));
								
					if (Tree.data(this)) {
						$('#parent').val(Tree.data($node));
					} 
				} else {
					$('#parentName').val('');
					$('#parent').val('');
				}
			});
		};
		var setActive = function(){
			var parent = $('#parent').val();
			
			// 取消active状态
			$.each(Tree.checked('.templatetype-tree-for-parent'),function(i,v) {
				$(v).trigger('click');
			});
			if (parent) {
				Tree.nodes('.templatetype-tree-for-parent').each(function(index){
					var node = $(this);
					var id = Tree.data(node);
					if (parent == id) {
						node.click();
						return false; //跳出当前each循环
					} 
				});
			}
		}
		
		setTree();
		setActive();
	
		
		$('#btn-save').click(function() {
			var typeName = $('#typeName').val();
			if (typeName == '') {
				alert('请填写文件类型名称！');
				return;
			}
			$.ajax({
				url : '${request.contextPath}/config/templatetype/save',
				dataType : 'json',
				type : 'POST',
				data : $('#form').serialize(),
				success : function(success) {
					if (!success) {
						alert('保存文件类型失败！');
					} else if (!success.success) {
						alert('保存文件类型失败！\n\n' + success.msg);
					} else {
						alert('保存文件类型成功！');
						Suredy.loadContent('${request.contextPath}/config/templatemanage/templateList');
						Modal.closeModal();
					}
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + c);
				}
			});
		});
	});
</script>	
