<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">	
		<form name="formtype" id="formtype">
		<#if data??>
			<input type="hidden" id="id" name="id" value="${data.id}" />
			<input type="hidden" id="parent" name="parent" value="${data.parent.id!''}" />
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">父分类</div>
					<input type="text" class="form-control" readonly="readonly" id="parentName" value="${data.parent.name}" data-toggle="collapse" href="#menu-tree" aria-expanded="false" aria-controls="menu-tree" />
					<div class="input-group-addon btn btn-default btn-load-menu-tree" data-toggle="collapse" href="#menu-tree" aria-expanded="false" aria-controls="menu-tree">
						<i class="icon-sitemap"></i>
					</div>
				</div>
			</div>
			<div class="collapse" id="menu-tree" style="border-top:1px solid #ddd; max-height:300px; overflow:auto;">
				<div class="filetype-tree-for-parent" data-uri="menu-tree.do">加载中……</div>
			</div>
			<div class="row">&nbsp;</div>	
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">分类名称</div>
					<input type="text" class="form-control" id="name" name="name" value="${data.name}" />
				</div>
			</div>		
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">权限分类</div>
					<select class="form-control" name="isPublic" id="isPublic">
						<option value="">---请选择---</option>
						<option value="1" ${(data.isPublic==1) ? string('selected=\"selected\"', '')}>公共类-全部用户均可访问</option>
						<option value="0" ${(data.isPublic==0) ? string('selected=\"selected\"', '')}>私密类-允许授权用户访问</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">显示顺序</div>
					<input type="text" class="form-control" id="sort" name="sort" value="${data.sort}" />
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
	require(['suredyTree','suredyModal', 'notify'], function(Tree, Modal) {
		var setTree = function() {
			Tree('.filetype-tree-for-parent','${request.contextPath}/files/type-tree',{
				autoCollapse : true,
				inContainer : false,
				style : 'file',
				size : 'sm'
			});
			
			$('.filetype-tree-for-parent .suredy-tree li>span+ul').collapse('show');
			
			$('.filetype-tree-for-parent').on(Suredy.Tree.nodeClick, function(event, $node) {
				if (Tree.isActive($node)) {
					if (Tree.data($node)) {
						var isPublic = Tree.data($node, 'isPublic');
						$('#parentName').val(Tree.data($node,'text'));
						$('#parent').val(Tree.data($node, 'id'));
						$('#isPublic').val(Tree.data($node, 'isPublic'));
						$('#isPublic').prop('disabled', 'true');
					} 
				} else {
					$('#parentName').val('');
					$('#parent').val('');
					$('#isPublic').val('');
					$('#isPublic').prop('disabled', 'false');
				}
			});
		};
		var setActive = function(){
			var parent = $('#parent').val();
		
			// 取消active状态
			$.each(Tree.checked('.filetype-tree-for-parent'),function(i,v) {
				$(v).trigger('click');
			});
			if (parent) {
				Tree.nodes('.filetype-tree-for-parent').each(function(index){
					var node = $(this);
					
					var id = Tree.data($(this), 'id');

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
			var name = $('#name').val();
			if (name == '') {
				alert('请填写文件分类名称！');
				return;
			}
			var isPublic = $('#isPublic').val();
			if (isPublic == '') {
				alert('请选择权限分类！');
				return;
			}
			$.ajax({
				url : '${request.contextPath}/files/type-save',
				dataType : 'json',
				type : 'POST',
				data : $('#formtype').serialize(),
				success : function(success) {
					if (!success) {
						alert('保存文件分类失败！');
					} else if (!success.success) {
						alert('保存文件分类失败！\n\n' + success.msg);
					} else {
						Suredy.loadContent('${request.contextPath}/files/manager');
						Modal.closeModal();
						$.notify({title:'提示：',message:'分类数据已保存！'});
					}
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + c);
				}
			});
		});
	});
</script>	
