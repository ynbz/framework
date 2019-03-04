<style type="text/css">
.hr-line {
	margin-top: 0px;
	margin-bottom: 0px;
	border: 0;
	border-top: 1px solid #eee;
}
.bottom-input {
	padding-bottom: 5px;
}

</style>
<div class="row">
	<div class="col-md-3">
			<input type="hidden" value="" id="nodeId" name="type.id" />
			<div class="panel panel-default">
			<div class="panel-heading">
				<i class="icon-user"></i>
				设备分类
			</div>
			<div class="panel-body type-tree" id="treeid" style="padding: 0; max-height: 850px; overflow: auto;">加载中......</div>
			</div>
			</div>
	<div class="col-md-9" valign="top" style="padding-left: 5px;">
		
			<div class="row" style="padding-top: 10px;">
				<div class="form-inline">
					<#if permissions?? && permissions?seq_contains('dd#sbczgl')>
						<div class="btn btn-danger" id="deletebtn">
							<i class="icon-remove"></i> 报废
						</div>	
					</#if>
							
					<div class="btn btn-info pull-right" id="properSearchbtn" style="display: none;margin-left:5px;">
						<i class="icon-search"></i> 查询
					</div>
					
					<div class="btn btn-info pull-right" id="conditionbtn" style=" ">
						<i class="icon-chevron-down"></i> 筛选
					</div>

					
				</div>

			</div>
			<div class="row">
				<div id="searchid" style="display: none; padding-left: 0px;">
					<form id="form" name="form">
						<div class="row" style="padding-top: 8px;">
					
							<div class="form-inline col-md-12" >
								<div class="form-group bottom-input">

									<div class="input-group" style="width: 200px;">
										<div class="input-group-addon" style="width: 85px;">序列号</div>
										<input type="text" class="form-control" id="serialNum"
											name="serialNum" value="" />
									</div>

								</div>

								<div class="form-group bottom-input">

									<div class="input-group" style="width: 200px;">
										<div class="input-group-addon" style="width: 85px;">资产ID</div>
										<input type="text" class="form-control" id="assetId"
											name="assetId" value="" />
									</div>
								</div>

								<div class="form-group bottom-input">
									<div class="input-group" style="width: 200px;">
										<div class="input-group-addon" style="width: 85px;">RFID</div>
										<input type="text" class="form-control" id="rfid" name="rfid"
											value="" />
									</div>
								</div>
								
								<div class="form-group bottom-input">
									<div class="input-group" style="width: 200px;">
										<div class="input-group-addon" style="width: 85px;">责任人</div>
										<input type="text" class="form-control" id="responsible"
											name="responsible" value="" />
									</div>
								</div>
								
								<div class="form-group bottom-input">
									<div class="input-group" style="width: 400px;">
										<div class="input-group-addon" style="width: 85px;">使用部门</div>
										<input type="hidden" class="form-control" id="userUnitId" name="userUnitId" value="" />
										<input type="text" class="form-control" id="userUnit" readonly="readonly" name="userUnit"
											value="" />
									</div>
								</div>
								
								<div class="form-group bottom-input">
									<div class="input-group" style="width: 200px;">
										<div class="input-group-addon" style="width: 85px;">项目名称</div>
										<input type="text" class="form-control" id="projectName" name="projectName"
											value="" />
									</div>
								</div>
								
								<div class="form-group bottom-input">
									<div class="input-group" style="width: 200px;">
										<div class="input-group-addon" style="width: 85px;">合同号</div>
										<input type="text" class="form-control" id="contractNumber" name="contractNumber"
											value="" />
									</div>
								</div>
							</div>
						</div>

						<hr class="hr-line" />
						<div class="row" style="padding-top: 5px;"  id="properSearchid">						
							<div class="form-inline col-md-12">
								<#if searchdata??> <#list searchdata as sd>
								<div class="form-group bottom-input">
									<div class="input-group" style="width: 200px;">
										<div class="input-group-addon" style="width: 85px;">${sd.propertyName}</div>
										<input type="text" class="form-control" id="${sd.field}"
											name="${sd.field}" value="" />
									</div>
								</div>
							
								</#list></#if>
								<#if searchsize!=0><hr class="hr-line" /></#if>		
							</div>
									
						</div>	
							
					</form>
				</div>
			</div>

		
		<div class="row" style="overflow-x: auto;padding-top: 5px;">
			<div id="equipmentList" style="width: 3500px;">

				<#if childid??> <input type="hidden" value="${childid}" id="childid" />
				</#if>
				<table class="equipment-list"
					data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>"
					data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>"
					data-count="<#if count??>${count}<#else>0</#if>"
					style="width: 100%;">

					<tr class="title-row">
						<th style="display: none"></th>
						<th>资产ID</th>
						<th field="buyDate" order="${((sortField??)&&sortField=='buyDate')?string(sort,'')}">购买日期</th>
						<th field="updateEq" order="${((sortField??)&&sortField=='updateEq')?string(sort,'')}">修改时间</th>
						<th field="type" order="${((sortField??)&&sortField=='type')?string(sort,'')}">资产类型</th>
						<th field="contractNumber" order="${((sortField??)&&sortField=='contractNumber')?string(sort,'')}">合同号</th>
						<th field="projectName" order="${((sortField??)&&sortField=='projectName')?string(sort,'')}">项目名称</th>
						<th>序列号</th>
						<th>RFID</th>
						<th field="responsible" order="${((sortField??)&&sortField=='responsible')?string(sort,'')}">责任人</th>
						<th field="userName" order="${((sortField??)&&sortField=='userName')?string(sort,'')}">使用人</th>
						<th>状态</th>
						<th>设备型号</th>
						<th>生产厂商</th>
						<th>供应商</th>
						<th>备注</th>
					</tr>
					<#if data??> <#list data as ea>
					<tr>
						<td style="display: none">${ea.id}</td>
						<td><a href='javascript:void(0)'onclick="assetIdOnclick('${ea.id}')">${ea.assetId}</a></td>
						<td>${(ea.buyDate?string('yyyy-MM-dd'))!''}</td>
						<td>${(ea.updateEq?string('yyyy-MM-dd'))!''}</td>
						<td>${ea.type.typeName}</td>
						<td>${ea.contractNumber}</td>
						<td>${ea.projectName}</td>
						<td>${ea.serialNum}</td>
						<td>${ea.rfid}</td>
						<td>${ea.responsible}</td>
						<td>${ea.userName}</td>
						<td id='${ea.status}'>${ea.statusName}</td>
						<td>${ea.equipModel}</td>
						<td>${ea.vendor}</td>
						<td>${ea.supplier}</td>
						<td>${ea.comm}</td> 
					</tr>
					</#list> </#if>

				</table>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
var listConfig;
var tempTree;
var tempList;
require(['suredyList','suredyTree','jqueryForm','suredyModal','suredyTreeSelector'], function(List, Tree,UpLoad,Modal,TreeSelector) {
	

	$.ajax({
		url : Suredy.ctxp + '/config/ou/tree/true/false/true',
		type : 'get',
		dataType : 'json',
		success : function(data) {
			TreeSelector('input#userUnit',data, {
				autoCollapse : false,
				style : 'department',
				showTitle : true,
				canFolderActive : true,
				size : 'lg'
			});
		}
	});		
	$('input#userUnit').on(TreeSelector.nodeClick, function(event, $node) {
		if (!TreeSelector.isActive($node)) {
			$('input[name="userUnitId"]').val('');
			$('input#userUnit').val('');
			return false;
		}
			
		var name = TreeSelector.data($node, 'fullName');
		$('input#userUnit').val(name);
		TreeSelector.hidden(this);
		return true;
	});
	
	tempTree=Tree;
	tempList=List;
	 var ouTree = function() {
		Tree('.type-tree','type/tree',{
			style : 'file',
			size : 'sm'
		});
		$('.type-tree').on(Suredy.Tree.nodeClick, function(event, $node) {			
			var nodeData =Tree.data($node);	
			var nodeisActive =Tree.isActive($node);
			if(nodeisActive){
				$('#nodeId').val(nodeData);
			}else{
				$('#nodeId').val("");
			}
			var url='${request.contextPath}/EquipAssetCtrl/getObsEquipData.do';
			$.post(url,{'typeId':$('#nodeId').val()},function(html, textStatus, jqXHR) {	
				var $html = $(html);
				$("#equipmentList").html($("#equipmentList", $html).html());
				$("#searchid").html($("#searchid", $html).html());
				List('.equipment-list',listConfig);
			}, 'html');
		})
	};
	ouTree();
	listConfig = ({
		header : false,
		footer : true,
		search : false,
		checkbox : true,			
		paginate : function(page, size, key, sort) {
			var typeId=$('#nodeId').val();
			var url='${request.contextPath}/EquipAssetCtrl/getObsEquipData.do?page=' + page + '&size=' + size+'&typeId='+typeId;
			
			
			$.each(sort, function(key,value) {
				url += '&sortField=' + key;
				url += '&sort='+value;
			});
			
			$.post(url, $('#form').serialize() , function(html, textStatus, jqXHR) {
				var $html = $(html);
					$("#equipmentList").html($("#equipmentList", $html).html());
					List('.equipment-list',listConfig);
			}, 'html');
		}
	});
	    List('.equipment-list',listConfig);	
		$("#conditionbtn").click(function(){
			var btn = $('i', $(this));
			if (btn.hasClass('icon-chevron-down')) {
				btn.removeClass('icon-chevron-down');
				btn.addClass('icon-chevron-up');
				$('#properSearchbtn').show();
				$('#searchid').show();
				
				
			} else if (btn.hasClass('icon-chevron-up')) {
				btn.removeClass('icon-chevron-up');
				btn.addClass('icon-chevron-down');
				$('#properSearchbtn').hide();
				$('#searchid').hide();
			}
		});	
		$("#properSearchbtn").click(function(){
			var url='${request.contextPath}/EquipAssetCtrl/getObsEquipData.do?typeId='+$('#nodeId').val();
			$.post(url, $('#form').serialize() , function(html, textStatus, jqXHR) {
				var $html = $(html);
				$("#equipmentList").html($("#equipmentList", $html).html());
				List('.equipment-list',listConfig);
			}, 'html');
		});
		
		$("#deletebtn").click(function(){
			var chechedids="";
			var checked =List.checked($('.equipment-list'));
			if (checked.length == 0 ) {
				alert('请选择需要报废的物品!');
				return;
			}else {
				var msg="数据报废\n\n\提示：\n\确定报废一下设备吗！";
				if (!window.confirm(msg)) return false;
				for(var i=0;i<checked.length;i++){
					//checked.eq(i).find('td').eq(1).html();
					chechedids=chechedids+","+checked.eq(i).find('td').eq(1).html();
				}	
				var prame={
						typeId:$('#nodeId').val()
				}
				var url='${request.contextPath}/EquipAssetCtrl/scrapData.do';
				$.ajax({
					type:'post',
					data:{'chechedids':chechedids},
					url:url,
					success:function(data){
						var m=data.data;
						if(m){	
							var url='${request.contextPath}/EquipAssetCtrl/getObsEquipData.do';
							$.post(url,prame,function(html, textStatus, jqXHR) {	
								var $html = $(html);
								$("#equipmentList").html($("#equipmentList", $html).html());
								$("#searchid").html($("#searchid", $html).html());
								List('.equipment-list',listConfig);
							}, 'html');
						}
					},
					error:function(){
						//alert("服务器连接失败");
					}
				});
				
			} 	
		});	
		
});
function assetIdOnclick(dataId){	
		window.open('${request.contextPath}/EquipAssetCtrl/getLookPage.do?equipid='+dataId)
};		
function readdata() {
	tempList('.equipment-list',listConfig);
};


</script>
