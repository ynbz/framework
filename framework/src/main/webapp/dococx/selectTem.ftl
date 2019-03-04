<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">	
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">选择模板</div>
						<input type="hidden" id="temFileid" name=temFileid />
						<input type="text" class="form-control" readonly="readonly" id="temName" name="temName"  />		
					</div>
				</div>
			</div>
		</div>	
		<div class="text-center">
			<div class="btn btn-info" id="btn-save" >
				<i class="icon-save"></i> 确定
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		require([ 'suredyModal','suredyTreeSelector' ],function(Model,TreeSelector) {
			// 表单类型tree
			TreeSelector('input#temName','${request.contextPath}/config//templatemanage/tree/${temTypeId}', {
				leafCheckbox:true,
				size : 'lg',
				showTitle : true,
				autoCollapse : false,
				canFolderActive:true,
				toggleActive : false
			});
			
			// 监听树的点击事件
			$('input#temName').on(TreeSelector.nodeClick, function(event, $node) {
				if (!TreeSelector.isActive($node)) {
					$('#temFileid').val('');
					$('#temName').val('');
					return false;
				}
				$('#temFileid').val(TreeSelector.data($node));
				$('#temName').val(TreeSelector.data($node,'text'));
				TreeSelector.hidden(this);
				return true;
			});
			$('#btn-save').click(function() {
				if($('#temFileid').val()==''){
					alert("请选择模板！")
					return false;
				}
				location.href = Suredy.ctxp+'/dococx/openwindow?url=create?id='+$('#id').val()+'&temFileid='+$('#temFileid').val();
				Suredy.Modal.closeModal();
			});	
		})
	});
</script>	
