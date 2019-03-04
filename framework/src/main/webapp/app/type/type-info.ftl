<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">	
	<form name="form" id="form">
	<#if type??>
		<input type="hidden" name="id" id="id" value="${type.id}" />
		<div class="form-group">
			<div class="input-group">
				<div class="input-group-addon" style="width: 81px;">父节点</div>
				<input type="hidden" readonly="readonly" id="parentId" name="parent.id" value="<#if type.parent??>${type.parent.id}</#if>" />
				<input type="text" class="form-control" readonly="readonly" id="parentName"  value="<#if type.parent????>${type.parent.typeName}</#if>" />
				<div class="input-group-addon btn btn-default btn-load-tree-tree" data-toggle="collapse" href="#type-tree" aria-expanded="false" aria-controls="type-tree">
					<i class="icon-sitemap"></i>
				</div>
			</div>
		</div>
		<div class="collapse" id="type-tree" style="border-top:1px solid #ddd; max-height:300px; overflow:auto;">
				<div class="type-tree-for-parent" >加载中……</div>
		</div>
	    	
		<div class="form-group">
			<div class="input-group">
				<div class="input-group-addon">分类名称</div>
				<input type="text" class="form-control" id="typeName" name="typeName" value="${type.typeName}" />
			</div>
		</div>		
		<div class="form-group">
			<div class="input-group">
				<div class="input-group-addon">显示顺序</div>
				<input type="text" class="form-control" id="sort" name="sort" value="${type.sort}" />
			</div>
		</div>	
		<div class="form-group">
			<div class="input-group">
				<div class="input-group-addon" >是否有属性</div>
				 <select class="form-control" id="isChildNode" name="isChildNode"  >
								<option value=""  ${(type.isChildNode==2)?string('selected','')}> --请选择--</option>
								<option value="1"  ${(type.isChildNode==1)?string('selected','')}>是</option>	
								<option value="0" ${(type.isChildNode==0)?string('selected','')}>否</option>								
							</select> 	
			</div>
		</div>	
		<div class="form-group">
			<div class="input-group">
				<div class="input-group-addon">提示信息</div>
				<input type="text" class="form-control" id="hint" name="hint" value="${type.hint}" />
			</div>
		</div>	
		</#if>		
							
		<div class="text-center">
			<div class="btn btn-info" id="btn-save" >
				<i class="icon-save"></i> 保存
			</div>
		</div>	
	</form>	
	</div>
</div>
<script type="text/javascript">
require(['suredyTree','suredyModal','notify'], function(Tree,Modal) {
	
	 var parentTree = function() {
		Tree('.type-tree-for-parent', '${request.contextPath}/type/tree');	
		$('.type-tree-for-parent').on(Suredy.Tree.nodeClick,function(event, $node) {		
			$('#parentName').val(Tree.data($node,'text'));
			var nodeData = Tree.data($node);
			if (nodeData) {
				$('#parentId').val(nodeData);
			} else {
				$('#parentId').val('');
			}
		}); 
		/*  if($('#id').val()==""){
			var checkNode = Tree.checked($('.menu-tree'));
			if(checkNode.length===1){				
				$('#parentName').val(Tree.data(checkNode,'text'));
				$('#parentId').val(Tree.data(checkNode));
				Tree.nodes($('#type-tree')).each(function(index){
					  var data = Tree.data(this);
						 if($('#parentId').val()==data){
							 $(this).click();
						 } 
						});				
			};
		} */
	};
	parentTree();
	$('#btn-save').click(function() {
		var typeName = $('#typeName').val();
		var isChildNode = $('#isChildNode').val();
		var typeid= $('#id').val();
		var parentId= $('#parentId').val();
		var tree=$('#type-tree-for-parent');
		if(typeid!=""&&parentId!=""&&typeid==parentId){
			alert("父节点不能是本身！");
			return false;
		}
		if (typeName == '') {
			alert('请填写名称！');
			return;
		}
		if (isChildNode == ''|| isChildNode==undefined) {
			alert('请选择是否有属性！');
			return;
		}
		 $.ajax({
			url : '${request.contextPath}/type/save',
			dataType : 'json',
			type : 'Post',
			data : $('#form').serialize(),
			success : function(success) {
				if (!success) {
					alert('保存失败！');
				} else if (!success.success) {
					alert('保存失败！\n\n' + success.msg);
				} else {
					if(success.msg=="name"){
						alert(success.data);
					}else if(success.data!=undefined&&!success.data){
						alert("父节点不能是其类型的子节点！");
					}else{
						$.notify({title:'提示：',message:'数据保存成功！'});
						Suredy.loadContent('${request.contextPath}/type/list');
						Modal.closeModal();
					}					
				}
			},
			error : function(a, b, c) {
				alert('服务器错误! ' + b);
			}
		});
	});	
		
});
</script>	
