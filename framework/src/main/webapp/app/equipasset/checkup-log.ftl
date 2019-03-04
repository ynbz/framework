<#if invalid?? && invalid>
<div class="alert alert-danger" role="alert">资产信息无效！</div>
<#else>
<table class="checkup-log" data-page="${page!'1'}" data-page-size="${pageSize!'30'}" data-count="${count!'0'}">
	<tr>
		<th  width="155">时间</th>
		<th  width="45">状态</th>
		<th >人员</th>
		<th >备注</th>
	</tr>
	<#if data??> <#list data as d>
	<tr class="data-row${((0==d.status)?string(' text-danger bg-danger',''))!''}${((2==d.status)?string(' bg-warning text-warning',''))!''}">
		<td>${(d.time?string('yyyy-MM-dd HH:mm:ss'))!'-'}</td>
		<td>${(d.statusString)!'-'}</td>
		<td>${(d.user)!'-'}</td>
		<td>${(d.remark)!'-'}</td>
	</tr>
	</#list> </#if>
</table>
</#if>
