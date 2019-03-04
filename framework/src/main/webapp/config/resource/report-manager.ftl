<div class="row">
	<div class="col-md-4 col-sm-4">
		<input type="hidden" value="" id="typeId" /> 
		<div class="row" style="height: 51px; padding: 8px 0px 8px 15px">
			<div class="btn btn-primary" id="newType">
				<i class="icon-plus"></i> 新建
			</div>
			<div class="btn btn-primary hidden" id="editType">
				<i class="icon-edit "></i> 修改
			</div>
			<div class="btn btn-danger hidden" id="removeType">
				<i class="icon-remove"></i> 删除
			</div>
		</div>
		<div class="report-type-tree" style="border-top: 1px solid #ddd; max-height: 800px; overflow: auto;">加载中......</div>
	</div>
	<div class="col-md-8 col-sm-8">
		<div id="typeList">
			<table class="report-list"
				data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>"
				data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>"
				data-count="<#if count??>${count}<#else>0</#if>">
				<tr class="title-row">
					<th style="display: none"></th>
					<th>报表名称</th>
					<th>报表分类</th>
					<th>存储标识</th>
					<th>创建时间</th>
				</tr>
				<#if data??> <#list data as report>
				<tr>
					<td style="display: none">${report.id}</td>
					<td>${report.name}</td>
					<td>${report.typeName}</td>
					<td>${report.fileId}</td>
					<td>${(report.createTime ? string('yyyy-MM-dd')) ! ''}</td>
				</tr>
				</#list> </#if>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
	require(['suredyTree','suredyList','suredyModal'], function(Tree, List, Modal) {
		var listConfig = ({
			header : true,
			footer : true,
			search : false,
			checkbox : true,			
			paginate : function(page, size) {
				var url='report/manager?page=' + page + '&size=' + size+'&typeId='+$("#typeId").val();
				$.get(url, function(html, textStatus, jqXHR) {
					var $html = $(html);
						$("#typeList").html($("#typeList", $html).html());
						List('.report-list', listConfig);
				}, 'html');
			},
			
			btns : [ {
				text :'上传',
				icon : 'icon-plus',
				style : 'btn-info',
				click : function() {
					Modal.showModal({
						size : 'lg',
						icon : 'icon-plus',
						title : '上传报表',
						showFoot : false,
						uri : 'report/file/edit'
					});
				}
			}, {
				text :'修改',
				icon : 'icon-edit',
				style : 'btn-info',
				click : function() {
					var checked = List.checked($('.report-list'));
					if (checked.length == 0 ) {
						alert('请选择需要修改的报表!');
						return;
					} else if (checked.length > 1) {
						alert('只能修改单个报表!');
						return;
					} else {
						var reportId = $( $( checked[0]).find('td' )[1] ).html();
						var uri = 'report/file/edit?reportId=' + reportId;
						Modal.showModal({
							size : 'lg',
							icon : 'icon-edit',
							title : '修改报表',
							showFoot : false,
							uri : uri
						});
					}
				}
			},  {
				text : '删除',
				icon : 'icon-remove',
				style : 'btn-danger',
				click : function() {
					var checked = List.checked($('.report-list'));
					if (checked.length == 0 ) {
						alert('请选择需要删除报表!');
						return;
					} else if (checked.length > 1) {
						alert('只能删除单个报表!');
						return;
					} else {
						if(confirm('是否确认删除该类型，删除后将不能进行恢复！')){
							var reportId = $( $( checked[0]).find('td' )[1] ).html();
							$.ajax({
									url : 'report/file/delete?reportId=' + reportId,
									dataType : 'json',
									type : 'POST',
									success : function(success) {
										if (!success) {
											alert('报表删除失败！');
										} else if (!success.success) {
											alert('报表删除失败！\n\n' + success.msg);
										} else {
											alert('报表删除成功！');
											Suredy.loadContent('report/manager');
											Modal.closeModal();
										}
									},
									error : function(a, b, c) {
										alert('服务器错误! ' + b);
									}
								});
						}
					} 
				} 
			} ]
		});	
		List('.report-list', listConfig);
		
		var typeTree = function() {
			Tree('.report-type-tree', 'report/type/tree', {
				autoCollapse : false,
				leafCheckbox : false,
				folderCheckbox : false,
				inContainer : false,
				style : 'file'
			});
			
			$('.report-type-tree').on(Suredy.Tree.nodeClick, function(event, $node) {
				var nodeData = Tree.data($node);				
				if (nodeData.id){
					$("#typeId").val(nodeData.id);
					$('#editType').removeClass('hidden');
					$('#removeType').removeClass('hidden');
				} else {
					$('#typeId').val('');
					$('#editType').addClass('hidden');
					$('#removeType').addClass('hidden');
				} 
				
				
				var url='report/manager?typeId='+nodeData.id;
				
				$.get(url, function(html, textStatus, jqXHR) {
					var $html = $(html);
						$("#typeList").html($("#typeList", $html).html());
						List('.report-list', listConfig);
				}, 'html');	
				
			});
		};
		typeTree();	

		
		$('#newType').on('click', function() {	
			var parentId = $("#typeId").val();
			
			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : '创建报表分类',
				showFoot : false,
				uri : 'report/type/edit?parentId=' + parentId
			});
		});
		

		// edit menu btn click
		$('#editType').on('click', function() {	
			var typeId = $('#typeId').val();
			if (typeId == '') {
				alert('请选择需要修改的类型！');
				return;
			}
			Modal.showModal({
				size : 'lg',
				icon : 'icon-edit',
				title : '修改报表分类',
				showFoot : false,
				uri : 'report/type/edit?id=' + typeId
			});
		
		});
	
		// remove btn click
		$('#removeType').on('click', function() {
			var typeId = $('#typeId').val();
			
	
			if (typeId == '') {
				alert('请选择需要删除的报表分类！');
				return;
			}
	
			var msg = '是否确认删除【选中的报表分类】？\n\n\提示：\n\该操作将会删除该分类及下的所有子节点！\n\请谨慎操作！';
	
			if (!window.confirm(msg)) {
				return;
			}
	
			$.ajax({
				url : 'report/type/delete',
				type : 'POST',
				data : {
					typeId : typeId
				},
				success : function(msg) {
					if (!msg) {
						alert('Unknown exception!');
					} else if (!msg.success) {
						alert("删除报表分类失败！\n\n" + msg.msg);
					} else {
						alert("删除报表分类成功！");
						typeTree();
	
						$('#editType').addClass('hidden');
						$('#removeType').addClass('hidden');
					}
				},
				error : function(a, b, c) {
					alert('Server error! ' + b);
				}
			});
		});	
		

	});
</script>	