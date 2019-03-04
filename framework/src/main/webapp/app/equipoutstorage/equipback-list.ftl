	<div class="panel panel-info">
			 <div class="panel-heading">
				<h3 class="panel-title">应归还设备</h3>
			</div>
		<div class="panel-body">
		<div class="row" style="margin-top: -15px;padding: 10px;">
		<table class="equipBack-list" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>" data-count="<#if count??>${count}<#else>0</#if>">
				<tr class="title-row">
					<th>设备型号</th>
					<th>资产id</th>
					<th>设备类型</th>
					<th>使用人</th>	
					<th>领用日期</th>
					<th>应归还日期</th>	
				</tr>
				<#if data??> <#list data as eos>
				<tr>
					<td >${eos.equipModel}</td>
					<td>${eos.assetId}</td>
					<td>${eos.typeName}</td>
					<td>${eos.userName}</td>
					 <td>${(eos.receiveDate?string('yyyy-MM-dd'))!''}</td>		
					<td>${(eos.promiseTime?string('yyyy-MM-dd'))!''}</td>								
					</tr>
						</#list></#if>						
			</table>
		</div>			
		</div>
		<div class="panel-footer">
		</div>
	</div>

<script type="text/javascript">
require(['suredyList'], function(List) {
var listConfig = ({
	header : true,
	footer : true,
	search : true,
	//title : "设备到期提醒",
	searchDefaultText : '请输入资产id',
	searchFieldName : 'search',	
	checkbox : false,			
	paginate : function(page, size,value) {
		Suredy.loadContent('EquipOutStoraneCtrl/getEquipOutStorageList.do?page=' + page + '&size=' + size+'&assetId='+value);
	}
});	
List('.equipBack-list',listConfig);	
});
	
</script>
