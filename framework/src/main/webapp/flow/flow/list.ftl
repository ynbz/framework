
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">流程管理</h3>
		</div>
		<div class="panel-body" id="flmList">
			<table class="flow-list" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>" data-count="<#if count??>${count}<#else>0</#if>">
				<tr class="title-row">
					<th style="display:none"></th>
					<th>流程名称</th>
					<th>流程代码</th>
					<th>创建时间</th>
				</tr>
				<#if data??> <#list data as flm>
				<tr data-id="${flm.id}">
					<td style="display:none">${flm.id}</td>
					<td>${flm.name}</td>
					<td>${flm.code}</td>	
					<td>${flm.createtime?string("yyyy-MM-dd HH:mm:ss")}</td>				
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
				var url='${request.contextPath}/config/flow/list.do?page=' + page + '&size=' + size;
				$.get(url, function(html, textStatus, jqXHR) {
					var $html = $(html);
					$("#flmList").html($("#flmList", $html).html());
					List('.flow-list', listConfig);
				}, 'html');
			},
			
			btns : [ {
				text :'创建',
				icon : 'icon-plus',
				style : 'btn-info',
				click : function() {
					Modal.showModal({
						size : 'lg',
						icon : 'icon-plus',
						title : '创建流程',
						showFoot : false,
						uri : '${request.contextPath}/config/flow/save'
					});
				}
			}, {
				text :'修改',
				icon : 'icon-edit',
				style : 'btn-danger',
				click : function() {
					var checked = List.checked($('.flow-list'));
					if (checked.length == 0 ) {
						alert('请选择需要修改的流程!');
						return;
					} else if (checked.length > 1) {
						alert('只能修改一个流程!');
						return;
					} else {
						var flmid = checked.eq(0).data('id');//.find('td').eq(1).html();// $( $( checked[0]).find('td' )[1] ).html();
						var uri = '${request.contextPath}/config/flow/save?flowId=' + flmid;
						Modal.showModal({
							size : 'lg',
							icon : 'icon-edit',
							title : '修改流程',
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
					var checked = List.checked($('.flow-list'));
					if (checked.length == 0 ) {
						alert('请选择需要删除的流程!');
						return;
					} else if (checked.length > 1) {
						alert('只能删除一个流程!');
						return;
					} else {
						//TODO: 
						var flmid =checked.eq(0).data('id') ;// $( $( $.suredy.list.checked($('.flow-list'))[0]).find('td' )[1] ).html();
						var uri = '${request.contextPath}/config/flow/delete?flowId=' + flmid;
						var msg = '是否确认删除申报？提示：请谨慎操作！';
				
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
									alert("删除流程失败！\n\n" + msg.msg);
								} else {
									alert("删除流程成功！");
									Suredy.loadContent('${request.contextPath}/config/flow/list');
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
		List('.flow-list', listConfig);
	});
</script>	