<table class="form-define-data-list" data-page="${page!'1'}" data-page-size="${pageSize!'30'}" data-count="${count!'0'}">
	<tr class="title-row">
		<th class="text-center" width="80" field="enable" order="${(order.isAsc('enable')?string('asc','desc'))!''}">启用状态</th>
		<th class="" field="name" order="${(order.isAsc('name')?string('asc','desc'))!''}">名称</th>
		<th class="text-center" width="80" field="version" order="${(order.isAsc('version')?string('asc','desc'))!''}">版本</th>
		<th class="text-center" width="170" field="createTime" order="${(order.isAsc('createTime')?string('asc','desc'))!''}">创建日期</th>
		<th class="text-center" width="170" field="lastEditTime" order="${(order.isAsc('lastEditTime')?string('asc','desc'))!''}">最后修改日期</th>
	</tr>
	<#if data??> <#list data as d>
	<tr data-id="${d.id}">
		<td class="text-center">${(d.enable?string('启用','停用'))!'未知'}</td>
		<td><a href="${request.contextPath}/formbuilder/open/0${d.id}" target="_blank">${d.name}</a></td>
		<td class="text-center">${d.version}</td>
		<td class="text-center">${d.createTime}</td>
		<td class="text-center">${d.lastEditTime}</td>
	</tr>
	</#list> </#if>
</table>

<script type="text/javascript">
	require([ 'suredyList' ], function(List) {
		List('.form-define-data-list', {
			header : false,
			checkbox : true,
			paginate : function(page, pageSize, key, order) {
				loadDatalist(page, pageSize, order);
			}
		});
	});
</script>