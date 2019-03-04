<#if invalid?? && invalid>
<div class="alert alert-danger" role="alert">资产信息无效！</div>
<#else>
<table class="eq-log" data-page="${page!'1'}" data-page-size="${pageSize!'30'}" data-count="${count!'0'}">
	<tr>
		<th > 操作人</th>
		<th >操作类型</th>
		<th >操作时间</th>
		<th >内容</th>
	</tr>
	<#if data??> <#list data as d>
	<tr >
		<td >${(d.name)!'-'}</td>
		<td >${(d.updateType==0)?string('PC端',(d.updateType==1)?string('桌面端',(d.updateType==2)?string('移动端','')))}</td>
		<td >${(d.updateEq?string('yyyy-MM-dd HH:mm:ss'))!'-'}</td>
		<td >${(d.laterContent)!'-'}</td>
	</tr>
	</#list> </#if>
</table>
</#if>
