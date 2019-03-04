
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">班组管理</h3>
		</div>
		<div class="panel-body" id="groupList">
			<table class="group-list" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>" data-count="<#if count??>${count}<#else>0</#if>">
				<tr class="title-row">
					<th style="display:none"></th>
					<th>班组名</th>
					<th>成员</th>
					<th>班组长</th>
					<th>创建时间</th>
				</tr>
				<#if data??> <#list data as group>
				<tr data-id="${group.id}">
					<td style="display:none">${group.id}</td>
					<td>${group.name}</td>
					<td>${group.memberName}</td>
					<td>${group.leaderName}</td>			
					<td>${group.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>				
				</tr>
				</#list> </#if>				
			</table>
		</div>
		<div class="panel-footer">
		</div>
	</div>


<script type="text/javascript">
	require(['suredyList', 'suredyModal'], function(List, Modal) {
		var listConfig = ({
			header : true,
			footer : true,
			search : false,
			checkbox : true,			
			paginate : function(page, size) {
				var url='${request.contextPath}/subject/group-list.do?page=' + page + '&size=' + size;
				$.get(url, function(html, textStatus, jqXHR) {
					var $html = $(html);
					$("#groupList").html($("#groupList", $html).html());
					List('.group-list', listConfig);
				}, 'html');
			},
			
			btns : [ {
				text :'新增',
				icon : 'icon-plus',
				style : 'btn-info',
				click : function() {
					Modal.showModal({
						size : 'lg',
						icon : 'icon-plus',
						title : '新增班组',
						showFoot : false,
						uri : '${request.contextPath}/subject/group-info'
					});
				}
			}, {
				text :'修改',
				icon : 'icon-edit',
				style : 'btn-danger',
				click : function() {
					var checked = List.checked($('.group-list'));
					if (checked.length == 0 ) {
						alert('请选择需要修改的班组!');
						return;
					} else if (checked.length > 1) {
						alert('只能修改一个班组!');
						return;
					} else {
						var id = checked.eq(0).data('id');
						var uri = '${request.contextPath}/subject/group-info?id=' + id;
						Modal.showModal({
							size : 'lg',
							icon : 'icon-edit',
							title : '修改班组',
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
					var checked = List.checked($('.group-list'));
					if (checked.length == 0 ) {
						alert('请选择需要删除的班组!');
						return;
					} else {
						//TODO: 
						var ids='';
						for(var i = 0;i<checked.length;i++){
							ids =ids+checked.eq(i).data('id')+",";
						}
						ids = ids.substring(0, ids.length-1);//去掉最后的逗号
						var uri = '${request.contextPath}/subject/group-delete?ids=' + ids;
						var msg = '是否确认删除班组？提示：请谨慎操作！';
				
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
									alert("删除班组失败！\n\n" + msg.msg);
								} else {
									alert("删除班组成功！");
									Suredy.loadContent('${request.contextPath}/subject/group-list.do');
								}
							},
							error : function(a, b, c) {
								alert('Server error! ' + b);
							}
						});

					} //end else
				} // end click function
			} ]
		});	
		List('.group-list', listConfig);
	});
</script>	