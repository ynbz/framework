
	<div class="panel panel-info">
		<div class="panel-heading">
			<h3 class="panel-title">${consumname} 备品备件领用记录</h3>
			<#if consumableid??> <input type="hidden" value="${consumableid}" id="consumableEquipid"/>
				</#if>
				<#if consumname??> <input type="hidden" value="${consumname}" id="consumableEquipname" />
				</#if>
		</div>
		<div class="panel-body">
			<div id="outConsumList">
				<table class="outConsum-list"
					data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>"
					data-page-size="<#if pageSize??>${pageSize}<#else>5</#if>"
					data-count="<#if count??>${count}<#else>0</#if>"
					style="width: 100%;">
					<tr class="title-row">
						<th style="display: none"></th>
						<th>资产ID</th>
						<th>设备型号</th>
						<th>设备类型</th>						
						<th>领用人</th>
						<th>领用部门</th>
						<th>领用数量</th>
						<th>领用时间</th>							
					</tr>
					<#if data??> <#list data as co>
					<tr>
					<td style="display: none">${co.id}</td>
					<td>${co.assetId}</td>
					<td>${co.equipModel}</td>
					<td>${co.typeName}</td>
					<td>${co.userName}</td>
					<td>${co.departName}</td>
					<td>${co.stockNum}</td>
					<td>${(co.outStocktime?string('yyyy-MM-dd'))!''}</td>				
					</tr>
					</#list></#if>
				</table>
			</div>
		</div>
	</div>
		
<script type="text/javascript">
require(['suredyList'], function(List) {	
	listConfig = ({
		header : false,
		footer : true,
		search : false,
		checkbox : false,			
		paginate : function(page, size) {
			size=8;
			var consumableEquipid=$("#consumableEquipid").val();
			var consumableEquipname=$("#consumableEquipname").val();
			var url='${request.contextPath}/ConsumableCtrl/lookConsumableAnddEquip.do';
			$.post(url,{'page':page,'size':size,'consumableid':consumableEquipid,'consumname':consumableEquipname},
					function(html, textStatus, jqXHR) {
						var $html = $(html);
						$("#outConsumList").html($("#outConsumList", $html).html());
						List('.outConsum-list',listConfig);
			}, 'html');
		}		
	});
	List('.outConsum-list',listConfig);	
	});
		
	</script>