<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">
	<#if type??>
		<form name="form" id="form">
			<input type="hidden" id="id" name="id" value="${type.id}" /> 
			<input type="hidden" id="parent" name="parent" value="${type.parent.id!''}" />
			<input type="hidden" id="resource" name="resource" value="${type.resource.id}" />
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon" style="width: 81px;">父节点</div>
					<input type="text" class="form-control" readonly="readonly" id="parentName" value="${type.parent.name}" />
					<div class="input-group-addon btn btn-default btn-load-tree-tree" data-toggle="collapse" href="#type-tree" aria-expanded="false" aria-controls="type-tree">
						<i class="icon-sitemap"></i>
					</div>
				</div>
			</div>
			<div class="collapse" id="type-tree" style="border-top: 1px solid #ddd; max-height: 300px; overflow: auto;">
				<div class="type-tree-for-parent">加载中……</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">分类名称</div>
					<input type="text" class="form-control" id="name" name="name" value="${type.name}" />
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">显示顺序</div>
					<input type="text" class="form-control" id="sort" name="sort" value="${type.sort}" placeholder="显示顺序,默认0" />
				</div>
			</div>
		</form>
		</#if>
		<div class="text-center">
			<div class="btn btn-info" id="btn-save">
				<i class="icon-save"></i> 保存
			</div>
		</div>		
	</div>
</div>
<script type="text/javascript">
require(['suredyTree','suredyModal','notify'], function(Tree,Modal) {
	
	 var parentTree = function() {
		Tree('.type-tree-for-parent', '${request.contextPath}/config/form/type-tree');	
		$('.type-tree-for-parent').on(Suredy.Tree.nodeClick,function(event, $node) {		
			$('#parentName').val(Tree.data($node,'text'));
			var nodeData = Tree.data($node);
			if (nodeData) {
				$('#parent').val(nodeData.id);
			} else {
				$('#parent').val('');
			}
		}); 
		
	};
	parentTree();
	
	var setActive = function(){
		var parent = '${parent}';
		
		// 取消active状态
		$.each(Tree.checked('.type-tree-for-parent'),function(i,v) {
			$(v).trigger('click');
		});

		if (parent) {
			
			Tree.nodes('.type-tree-for-parent').each(function(index){
				var node = $(this);
				
				var id = Tree.data(this);
				if (parent == id) {
					node.click();
					return false; //跳出当前each循环
				} 
			});
		}
	}
	
	setActive();
	
	$('#btn-save').click(function() {
		var name = $('#name').val();
		if (name == '') {
			alert('请填写分类名称！');
			return;
		}
		
		 $.ajax({
			url : '${request.contextPath}/config/form/type-save',
			dataType : 'json',
			type : 'Post',
			data : $('#form').serialize(),
			success : function(success) {
				if (!success) {
					alert('保存表单分类失败！');
				} else if (!success.success) {
					alert('保存表单分类失败！\n\n' + success.msg);
				} else {
					$.notify({title:'提示：',message:'数据保存成功！'});
					Suredy.loadContent('${request.contextPath}/config/form/list.do');
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
