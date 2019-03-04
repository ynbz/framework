<link rel="stylesheet" href="${request.contextPath}/component/flow/css/flowComponent.css">
<input type="hidden" value="" id="sendId"/>
<div class="row panal-row">
	<div class="col-md-4 panal-col">
		<div class="title"><i class='icon-exchange'></i> 人员范围</div>
		<div style="overflow: auto; height: 280px;padding-top:1px;">
			<div class="unit-user-tree department"></div>
		</div>
	</div>

	<div class="col-md-8 panal-col">
		<div class="title"><i class='icon-reorder'></i> 选择结果</div>
		<div style="overflow: auto; height: 280px;">
			<table class="table table-bordered table-hover table-striped table-condensed">
				<thead>
				<tr>
					<th class="text-center" width="100">人员</th>
					<th width="200" class="text-center">部门</th>
				</tr>
				</thead>
				<tbody  id="selUser" ></tbody>
			</table>
		</div>
	</div>
</div>
<!-- script src="${request.contextPath}/js/suredy-tree.js"></script -->
<script>
	require(['suredyTree'], function(Tree) {
		var s_style = "${sendData.style}";
		var s_id = "${sendData.id}";
		var s_obj = "${sendData.obj}";
		var treeData = ${treeData};
		var isMulti = true;
		if (s_style == "Multi"){
			isMulti = true;
		}
		else if (s_style == "Single"){
			isMulti = false;
		}
		$('#sendId').val(s_id);
		Tree('.unit-user-tree',treeData,
		{
			multiselect : isMulti,
			leafCheckbox : isMulti,
			style:'department',
			canFolderActive : false
		});
		$('.unit-user-tree').on(Suredy.Tree.nodeClick, function(event, $node) {
			if (isMulti) {
				$('#selUser').html('');
				Tree.checked($('.unit-user-tree')).each(function(index) {
					var text = Tree.data(this, 'text');
					var u=Tree.data(this);
					if( u.nodeType=="user"){
						$('#selUser').append('<tr class="text-center userRow" data-id="'+u.id+'"><td>' + text + '</td><td>'+u.unitName+'</td></tr>');
					}
				});
			} else {
				var text = Tree.data($node, 'text');
				var u=Tree.data($($node));
				if( u.nodeType=="user"){
					$('#selUser').html('');
					$('#selUser').append('<tr class="text-center userRow" data-id="'+u.id+'"><td>' + text + '</td><td>'+u.unitName+'</td></tr>');
				}
			}
		});
	});
</script>