
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">设备领取</h3>
	</div>
	
		<div class="panel-body" id="getEquipment">
			<!-- <div class="row pull-right"  >经典托管实例  当页面刷新时id='personSearchbtn'的click时间会失效，失效原因是，刷新后会重新创建新对象id='personSearchbtn'
				<div class="form-inline">	
					<form id="person_form" >
						<div class="col-md-9 col-xs-12" >
							<div class="form-group">
								<div class="input-group" >
									<div class="input-group-addon">责任人</div>
									<input type="text" class="form-control"  name="person" value="${person}" />
									<input type="hidden" class="form-control" id="person" value="${person}" />
									<input type="hidden" class="form-control"  name="pageIndex" value="${pageIndex}" />
									<input type="hidden" class="form-control" name="pageSize" value="${pageSize}" />
								</div>
							</div>
						</div>
						
						<div class="btn btn-info pull-right"  id="personSearchbtn" >
							<i class="icon-search"></i> 查询
						</div>
					</form>
				</div>
			</div> -->
			<input type="hidden" class="form-control" id="person" value="${person}" />
			<table class="get_equipment" data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>" data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>" data-count="<#if count??>${count}<#else>0</#if>">
				<tr class="title-row">
					<th style="display:none"></th>
					<th>设备资产id</th>
					<th>责任人</th>
					<th>使用人</th>
					<th>设备类型</th>
					<th>设备型号</th>
					<th>申请类型</th>
				</tr>
				<#if data??> <#list data as eq>
				<tr data-id="${eq.assetId}">
					<td style="display:none">${eq.assetId}</td>
					<td>${eq.assetId}</td>
					<td>${eq.responsible}</td>
					<td>${eq.userName}</td>
					<td>${eq.type.typeName}</td>	
					<td>${eq.equipModel}</td>
					<td>
						<#if eq.isBorrow==0>领用申请<#else>借用申请</#if>
					</td>			
			
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
			search : true,
			searchDefaultText : '请输入责任人',
			searchValue : '',
			checkbox : true,
			paginate : function(page,size,key) {
				var url='EquipAssetCtrl/get_equipment';
				$.post(url,{person:key,page:page,size:size},function(html, textStatus, jqXHR) {
					var $html = $(html);
					$("#getEquipment").html($("#getEquipment", $html).html());
					listConfig.searchValue = $("#person", $html).val();
					List('.get_equipment', listConfig);
				}, 'html');
			},
			
			btns : [{
				text : '确认领用',
				icon : 'icon-plus',
				style : 'btn-info',
				click : function() {
					var checked = List.checked($('.get_equipment'));
					if (checked.length == 0 ) {
						alert('请选择确认领用设备项!');
						return;
					} else {
						var eqids = new Array();
						
						//TODO: 
						for(var i = 0;i<checked.length;i++){
							eqids[i]=checked.eq(i).data('id');
						}
						
						var uri = 'EquipAssetCtrl/getByEquipment?eqids=' + eqids;
						var msg = '是否确认领用设备？';
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
									alert("领用设备失败！\n\n设备资产id为：" + msg.msg);
								} else {
									alert("领用设备成功！");
									Suredy.loadContent('EquipAssetCtrl/get_equipment');
								}
							},
							error : function(a, b, c) {
								alert('Server error! ' + b);
							}
						});

					} //end else
				} // end click function
			}]
		});	
		/** 
		$(".notice-list").on('click','#personSearchbtn',function(){经典托管实例 
			alert("----")
			var url='EquipAssetCtrl/get_equipment';
			$.post(url, $('#person_form').serialize() , function(html, textStatus, jqXHR) {
				var $html = $(html);
				$("#getEquipment").html($("#getEquipment", $html).html());
				List('.get_equipment', listConfig);
			}, 'html');
		});*/
		List('.get_equipment', listConfig);
	});
</script>	