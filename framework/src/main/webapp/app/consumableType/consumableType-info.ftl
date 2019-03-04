<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">	
	<form name="form" id="form">
	<#if type??>
		<input type="hidden" name="id" id="id" value="${type.id}" />
		<div class="form-group">
			<div class="input-group">
				<div class="input-group-addon" style="width: 81px;">父节点</div>
				<input type="hidden" readonly="readonly" id="parentId" name="parent.id" value="<#if type.parent??>${type.parent.id}</#if>" />
				<input type="text" class="form-control" readonly="readonly" id="parentName"  value="<#if type.parent????>${type.parent.consumerName}</#if>" />
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
				<input type="text" class="form-control" id="consumerName" name="consumerName" value="${type.consumerName}" />
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
				<div class="input-group-addon" >是否关联设备</div>
				 <select class="form-control" id="isChildNode" name="isChildNode"  onchange="isChildNodeOnchange(this.value)">
								<option value=""  ${(type.isChildNode==2)?string('selected','')}> --请选择--</option>
								<option value="1"  ${(type.isChildNode==1)?string('selected','')}>是</option>	
								<option value="0" ${(type.isChildNode==0)?string('selected','')}>否</option>								
							</select> 	
			</div>
		</div>	
		</#if>
		<div class="form-group"  id="userTypeid" style="display: none;">
									<div class="input-group">
										<div class="input-group-addon">
											可用设备类型
										</div>
										<input type="hidden" class="form-control" id="resourceid" name="resourceid" value="${type.resourceid}" />
									    <input type="text"  class="form-control" readonly="readonly" id="resourcename" name="resourcename" value="${type.resourcename}" />
										<div class="input-group-addon btn btn-default btn-load-tree-tree" data-toggle="collapse" href="#equiptypename-tree" aria-expanded="false" aria-controls="equiptypename-tree">
											<i class="icon-sitemap"></i>
										</div>
									</div>
				<div class="collapse" id="equiptypename-tree"
					style="border-top: 1px solid #ddd; max-height: 300px; overflow: auto;">
					<div class="equiptypename-treep">加载中……</div>
				</div>
			</div>		
						
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
	
	/* TreeSelector('input#equiptypename', '${request.contextPath}/type/tree', {
		autoCollapse : false,
		style : 'department',
		showTitle : true,
		canFolderActive : false,
		//size : 'lg'
	}); */
	var isChildNodes = $('#isChildNode').val();
	if(isChildNodes==1){
		$('#userTypeid').show();
	}
	 var equiptypenametree = function() {
			Tree('.equiptypename-treep', '${request.contextPath}/type/tree',{			
				//showTitle : true,
				//canFolderActive : false,
				multiselect:true,
				size : 'normal',
				leafCheckbox:true,
			    //folderCheckbox : true,
			    style : 'file'
			});	
			$('.equiptypename-treep').on(Suredy.Tree.nodeClick,function(event, $node) {	
				var checkedid="";
				var checkedname="";
				$.each(Tree.checked($('.equiptypename-treep')), function() {
					checkedid+= Tree.data(this)+",";
					checkedname+=Tree.data(this,'text')+",";	
				});
				$("#resourcename").val(checkedname);
				$("#resourceid").val(checkedid);
				
			}); 
			var resourceid=$("#resourceid").val();
			if(resourceid!=''){
				var resourceids=resourceid.split(",");
				for(var i=0;i<resourceids.length;i++){
					Tree.nodes($('.equiptypename-treep')).each(function(index) {
						var data = Tree.data(this);
						if (resourceids[i] == data) {
							$(this).addClass('active');
						}
					});
				}
				
			}
		};
		equiptypenametree();
	 var parentTree = function() {
		Tree('.type-tree-for-parent', '${request.contextPath}/ConsumableTypeCtrl/Consumabletree');	
		$('.type-tree-for-parent').on(Suredy.Tree.nodeClick,function(event, $node) {	
			
			$('#parentName').val(Tree.data($node,'text'));
			var nodeData = Tree.data($node);
			if (nodeData) {
				$('#parentId').val(nodeData);
			} else {
				$('#parentId').val('');
			}
		}); 
		
	};
	parentTree();
	$('#btn-save').click(function() {
		var typeName = $('#consumerName').val();
		var isChildNode = $('#isChildNode').val();
		var typeid= $('#id').val();
		var parentId= $('#parentId').val();
		var tree=$('#type-tree-for-parent');
		var  equipname=$("#resourcename").val();
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
		if(equipname==""&&isChildNode==1){
			alert("可用设备类型不能为空！");
			return false;
		}
		 $.ajax({
			url : '${request.contextPath}/ConsumableTypeCtrl/save',
			dataType : 'json',
			type : 'Post',
			data : $('#form').serialize(),
			success : function(success) {
				if (!success) {
					alert('数据保存失败！');
				} else if (!success.success) {
					alert('数据保存失败！'+ success.msg);
				} else {
					if(success.msg=="name"){
						alert(success.data);
					}else if(success.data!=undefined&&!success.data){
						alert("父节点不能是其类型的子节点！");
					}else{
						$.notify({title:'提示：',message:'数据保存成功！'});
						Suredy.loadContent('${request.contextPath}/ConsumableTypeCtrl/list');
						Modal.closeModal();
					}					
				}
			},
			error : function(a, b, c) {	
				alert('服务器错误！'+b);
			}
		});
	});	
		
});
var resourcename=$("#resourcename").val();
var resourceid=$("#resourceid").val();
function isChildNodeOnchange(value){	
	if(value==1){
		$('#userTypeid').show();
		$("#resourcename").val(resourcename);
		$("#resourceid").val(resourceid)
	}else{
		$('#userTypeid').hide();
		$("#resourcename").val("");
		$("#resourceid").val("")
	}
}
</script>	
