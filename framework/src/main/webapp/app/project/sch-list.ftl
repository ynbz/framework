<#if invalid?? && invalid>
<div class="alert alert-danger" role="alert">资产信息无效！</div>
<#else>
<table class="sch-list" data-page="${page!'1'}" data-page-size="${pageSize!'25'}" data-count="${count!'0'}">
	<tr class="title-row">
		<th>周期</th>
		<th>产量</th>
		<th>创建时间</th>
	</tr>
	<#if data??> <#list data as d>
	<tr data-id="${d.id}">
		<td>${(d.periodStart?string('yyyy-MM-dd'))!'-'}──${(d.periodEnd?string('yyyy-MM-dd'))!'-'}</td>
		<td>${(d.number)!'-'}${(d.unit==1)?string('立方',(d.unit==2)?string('吨',(d.unit==3)?string('个',(d.unit==4)?string('件',(d.unit==5)?string('千克','')))))}</td>
		<td>${(d.createDate?string('yyyy-MM-dd '))!'-'}</td>
	</tr>
	</#list> </#if>
</table>
</#if>
