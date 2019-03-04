<table class="segment-list" data-page="${(pageIndex)!'1'}" data-page-size="${(pageSize)!'20'}" data-count="${(count)!'0'}">
	<tr class="title-row">
		<th style="display:none"></th>
		<th>名称</th>
		<th>资源标识符</th>
		<th>顺序</th>			
	</tr>
	<#if data??> <#list data as segment>
	<tr>
		<td style="display:none">${segment.id}</td>
		<td>${segment.name}</td>
		<td>${segment.uri}</td>
		<td>${segment.sort}</td>									
	</tr>
	</#list></#if>				
</table>


<script type="text/javascript">
	require(['suredyList','suredyModal'], function(List, Modal) {
		var listConfig = ({
			header : true,
			footer : false,
			search : false,
			checkbox : true,			
			paginate : function(page, size) {
				Suredy.loadContent('resource/segment-manager?page=' + page + '&size=' + size);
			},
			
			btns : [ {
				text :'新建',
				icon : 'icon-plus',
				style : 'btn-info',
				click : function() {
					Modal.showModal({
						size : 'lg',
						icon : 'icon-plus',
						title : '创建单点控制',
						showFoot : false,
						uri : 'segment/form'
					});
				}
			}, {
				text :'修改',
				icon : 'icon-edit',
				style : 'btn-danger',
				click : function() {
					var checked = List.checked($('.segment-list'));
					if (checked.length == 0 ) {
						alert('请选择需要修改的控制点!');
						return;
					} else if (checked.length > 1) {
						alert('只能修改单个控制点!');
						return;
					} else {
						var id = $( $( checked[0]).find('td' )[1] ).html();
						var uri = 'segment/form?id=' + id;
						Modal.showModal({
							size : 'lg',
							icon : 'icon-edit',
							title : '修改单点控制',
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
					var checked = List.checked($('.segment-list'));
					if (checked.length == 0 ) {
						alert('请选择需要删除的控制点!');
						return;
					} else if (checked.length > 1) {
						alert('只能删除一个控制点!');
						return;
					} else {
						var segmentId = $( $( checked[0]).find('td' )[1] ).html();
						var uri = 'segment/delete.do?id=' + segmentId;
						var msg = '是否确认删除控制点？\n\n\提示：\n\该操作将会删除该控制点及对应的权限映射关系！\n\请谨慎操作！';
	
						if (!window.confirm(msg)) {
							return;
						}
						$.ajax({
							url : uri,
							type : 'POST',
							success : function(msg) {
								if (!msg) {
									alert('Unknown exception!');
								} else if (!msg.success) {
									alert("删除控制点失败！\n\n" + msg.msg);
								} else {
									alert("删除控制点成功！");
									Suredy.loadContent('segment/manager');
								}
							},
							error : function(a, b, c) {
								alert('Server error! ' + b);
							}
						});

					} 
				}
			}]
		});	
		
		List('.segment-list', listConfig);
	});
</script>	