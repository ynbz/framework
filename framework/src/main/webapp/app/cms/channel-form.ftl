<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">	
		<form name="form" id="form">
		<#if data??>
			<input type="hidden" id="id" name="id" value="${data.id}" />
			<input type="hidden" id="parent" name="parent" value="${data.parent.id!''}" />
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">父栏目</div>
					<input type="text" class="form-control" readonly="readonly" id="parentName" value="${data.parent.text}" />
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">栏目名称</div>
					<input type="text" class="form-control" id="name" name="name" value="${data.name}" placeholder="栏目名称" />
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">显示顺序</div>
					<input type="text" class="form-control" id="sort" name="sort" value="${data.sort}" placeholder="显示顺序,默认0"/>
				</div>
			</div>	
		</#if>	
		</form>	
	</div>
	<div class="text-center">
		<div class="btn btn-primary" id="btn-save" >
			<i class="icon-save"></i> 保存
		</div>
	</div>	
</div>
<script type="text/javascript">
	require(['suredyTreeSelector','suredyModal'], function(Selector, Modal) {
		Selector('#parentName','${request.contextPath}/app/cms/channel/tree',{
			autoCollapse : false,
			size : 'lg',
			style : 'file'
		});
		
		$('#parentName').on(Suredy.TreeSelector.nodeClick, function(event, $node) {
			if (Selector.isActive($node)) {
				$('#parentName').val(Selector.data($node, 'text'));
				$('#parent').val(Selector.data($node, 'id'));
				Selector.hidden('#parentName');
			} else {
				$('#parentName').val('');
				$('#parent').val('');
			}
		});
		
		var setActive = function(){
			var $checked = Selector.checked('#parentName');

			if ($checked.length)
				Selector.toggleActive($checked);

			var parent = $('#parent').val();
			
			if (parent) {
				Selector.nodes('#parentName').each(function(index){
					var $node = $(this);
					var id = Selector.data($node, 'id');
					
					if (parent == id) {
						Selector.setActive($node);
						$('#parentName').val(Selector.data($node, 'text'));
						return false; //跳出当前each循环
					} 
				});
			}
		}
		setActive();
	
		
		$('#btn-save').click(function() {
			var name = $('#name').val();
			if (name == '') {
				alert('请填写名称！');
				return;
			}
			var sort = $('#sort').val();
			if (sort == '' || isNaN(sort)) {
				alert('请填写显示顺序！');
				return;
			}			
			$.ajax({
				url : '${request.contextPath}/app/cms/channel/save',
				dataType : 'json',
				type : 'post',
				data : $('#form').serialize(),
				success : function(data) {
					if (!data || !data.success) {
						alert('保存栏目失败！\n\n' + ((data && data.msg)||''));
						return;
					}
					Suredy.loadContent('${request.contextPath}/app/cms/channel/manager');
					Modal.closeModal();
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + c);
				}
			});
		});
	});
</script>	
