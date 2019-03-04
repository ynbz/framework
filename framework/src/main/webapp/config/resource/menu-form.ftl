<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">	
		<form name="form" id="form">
		<#if data??>
			<input type="hidden" id="id" name="id" value="${data.id}" />
			<input type="hidden" id="parent" name="parent" value="${data.parent.id!''}" />
			<input type="hidden" id="resource" name="resource" value="${data.resource.id}" />
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">父节点</div>
					<input type="text" class="form-control" readonly="readonly" id="parentName" value="${data.parent.text}" />
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">菜单文本</div>
					<input type="text" class="form-control" id="text" name="text" value="${data.text}" placeholder="菜单文本" />
				</div>
			</div>
		
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">链接地址</div>
					<input type="text" class="form-control" id="url" name="url" value="${data.url}" placeholder="链接地址" />
				</div>
			</div>	
			
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">显示顺序</div>
					<input type="text" class="form-control" id="sort" name="sort" value="${data.sort}" placeholder="显示顺序,默认0"/>
				</div>
			</div>	
			
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">菜单图标</div>
					<input type="text" class="form-control" id="icon" name="icon" value="${data.icon}" placeholder="菜单图标"/>
				</div>
			</div>	
			
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">是否活动</div>
					<select class="form-control" id="active" name="active">
						<option value="true" ${(data.active) ? string('selected="selected"', '')}>是</option>
						<option value="false" ${(!data.active) ? string('selected="selected"', '')}>否</option>
					</select>
				</div>
			</div>			
	
			<div class="form-group">
				<div class="input-group">
					<div class="input-group-addon">是否收拢</div>
					<select class="form-control" id="collapse" name="collapse">
						<option value="true" ${(data.collapse) ? string('selected="selected"', '')}>是</option>
						<option value="false" ${(!data.collapse) ? string('selected="selected"', '')}>否</option>			
					</select>
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
	require(['suredyTreeSelector','suredyModal'], function(Selector, Modal) {
		Selector('#parentName','menu/tree',{
			autoCollapse : false,
			size : 'sm'
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
			var text = $('#text').val();
			if (text == '') {
				alert('请填写名称！');
				return;
			}
			$.ajax({
				url : 'menu/save',
				dataType : 'json',
				type : 'post',
				data : $('#form').serialize(),
				success : function(data) {
					if (!data || !data.success) {
						alert('保存菜单失败！\n\n' + ((data && data.msg)||''));
						return;
					}
					
					$('.menu-tree').trigger('reloadtree', data.data);

					alert('保存菜单成功！');
					Modal.closeModal();
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + c);
				}
			});
		});
	});
</script>	
