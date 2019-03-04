<div class="row">
	<div class="col-md-3">
			<input type="hidden" value="" id="nodeId" name="type.id" />
			<#if childid??> <input type="hidden" value="${childid}" id="childid" />
				</#if>
			<div class="panel panel-default">
			<div class="panel-heading">
				<i class="icon-user"></i>
				备品备件分类
			</div>
			<div class="panel-body typelist-tree" id="treeid" style="padding: 0; max-height: 850px; overflow: auto;">加载中......</div>
			</div>
			</div>
	<div class="col-md-9">
		<div class="row" style="padding-top: 10px;margin-right:0px;" >
					<div class="form-inline">
						<div class="btn btn-info addData">
							<i class="icon-plus"></i> 新建
						</div>
						<div class="btn btn-info updateData">
							<i class="icon-edit"></i> 修改
						</div>
						<div class="btn btn-danger deleteData">
							<i class="icon-remove"></i> 删除
						</div>					
						<div class="btn btn-info pull-right searchbtn" style="display: none;margin-left:5px;">
							<i class="icon-search"></i> 查询
						</div>
						
						<div class="btn btn-info pull-right conditionData">
							<i class="icon-chevron-down"></i> 筛选
						</div>						
					</div>
				</div>
		<div class="row"  style="margin-right:0px;">
				<div class="searchDiv" style="display: none;padding-top: 8px;margin-bottom: -15px;">
					<form id="searchform" name="searchform">
					<div class="row" >
						<div class="col-md-6">
							<div class="form-group">
								<div class="input-group">
									<div class="input-lable input-group-addon">
									型号
									</div>
									<input type="text" class="form-control" id="cunsumModelinfo"
										name="cunsumModel" value="" />
								</div>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<div class="input-group">
									<div class="input-lable input-group-addon">
										物资名称
									</div>
									<input type="text" class="form-control" id="consumableNameinfo"
										name="consumableName" value="" />
								</div>
							</div>
						</div>
					</div>
					</form>
				</div>
			</div>
		<div  class="row "  style="padding-top: 8px;margin-right:0px;overflow-x:auto;">
		<div id="consumableList" >
			<table class="consumable-list"
				data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>"
				data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>"
				data-count="<#if count??>${count}<#else>0</#if>" >
				<tr class="title-row">
					<th style="display: none"></th>
					<th>代码</th>
					<th>型号</th>
					<th>物资名称</th>
					<th>类型</th>
				 	<th>适用设备类型</th>
				 	<th>品牌</th>
				 	<th>单位</th>			
					<th>库存量</th>
					<th>已用量</th>
					<th>占用量</th>
					<th>备注</th>
					<#if cpdata??> <#list cpdata as cs>
						<th>${cs.propertyName}</th> </#list></#if>
				</tr>	
				<#if data??> <#list data as cm>
				<tr>
					<td style="display: none">${cm.id}</td>
					<td>${cm.cunsumCode}</td>
					<td>${cm.cunsumModel}</td>
					<td>${cm.consumableName}</td>
					<td>${cm.type.consumerName}</td>
					<td>${cm.equipModelApply}</td>
					<td>${cm.cunsumbrand}</td>
					<td>${cm.unit}</td>					
					<td>${cm.stock}</td>
					<td><!-- <a href='javascript:void(0)' onclick="EquipUserNum('${cm.id}','${cm.consumableName}')" -->${cm.hasTheDosage}<!-- </a> --></td>					
					<td>${cm.footprint}</td>
					<td>${cm.comm}</td>
					<#if cpdata??> <#list cpdata as cs>
					<td>${cm.getColvalue(cs.field)}</td> </#list></#if>
				</tr>
				</#list> </#if>
			</table>
		</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	var tempModel;
	require(['suredyTree','suredyList','suredyModal','notify'], function(Tree, List, Modal) {
		tempModel=Modal;
		 var ouTree = function() {
				Tree('.typelist-tree','${request.contextPath}/ConsumableTypeCtrl/Consumabletree',{
					style : 'file',
					size : 'sm'
				});
				$('.typelist-tree').on(Suredy.Tree.nodeClick, function(event, $node) {			
					 var nodeData =Tree.data($node);	
						var nodeisActive =Tree.isActive($node);
						if(nodeisActive){
							$('#nodeId').val(nodeData);
							}else{
							$('#nodeId').val("");
							}
					
					var url='${request.contextPath}/ConsumableCtrl/getConsumbleCtrl.do';
					$.post(url,{'typeId':$('#nodeId').val()},function(html, textStatus, jqXHR) {	
						var $html = $(html);
						$("#consumableList").html($("#consumableList", $html).html());
						List('.consumable-list',listConfig);
					}, 'html'); 
				});
				 if($('#childid').val()!=undefined){
						Tree.nodes($('.typelist-tree')).each(function(index) {
							var data = Tree.data(this);
							 if($('#childid').val()==data){
								 $(this).click();
								 $('#childid').val(undefined)
							 } 
						});
					};
			};
			ouTree();
		
		var listConfig = ({
			header : false,
			footer : true,
			search : false,
			checkbox : true,			
			paginate : function(page, size) {
				var typeId=$('#nodeId').val();
				var url='${request.contextPath}/ConsumableCtrl/getConsumbleCtrl.do?page=' + page + '&size=' + size+'&typeId='+typeId;
				$.post(url, $('#form').serialize() , function(html, textStatus, jqXHR) {
					var $html = $(html);
						$("#consumableList").html($("#consumableList", $html).html());
						List('.consumable-list',listConfig);
				}, 'html');
			}
		});	
		List('.consumable-list', listConfig);	
		
		
		$(".addData").on("click",function(){
			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : '新增',
				showFoot : true,
				uri : '${request.contextPath}/ConsumableCtrl/addOrUpdatePage.do'
			});
		});
		
		$(".updateData").on("click",function(){
			 var checked = List.checked($('.consumable-list'));					
			 if (checked.length == 0 ) {
				alert('请选择需要修改的数据!');
				return;
			} else if (checked.length > 1) {
				alert('只能修改一个!');
				return;
			}else {
				var dataId = $(checked[0]).find('td').eq(1).html();
				Modal.showModal({
					size : 'lg',
					icon : 'icon-edit',
					title : '修改',
					showFoot : true,
					uri : '${request.contextPath}/ConsumableCtrl/addOrUpdatePage.do?id='+dataId
				});
			}
		});
		
		
		$(".deleteData").on("click",function(){
			var checked = List.checked($('.consumable-list'));	
			if (checked.length == 0 ) {
				alert('请选择需要删除的数据!');
				return;
			}else if(checked.length>1){
				alert('只能删除一条数据!');
				return;
			}else if($(checked[0]).find('td').eq(11).html()>0){
				alert('不能删除此数据，此耗材存在占用量!');
				return;
			} 
			else {
				var dataId ="chechedids";
				for(var i=0;i<checked.length;i++){							
					dataId=dataId+","+ $(checked[i]).find('td').eq(1).html();							
				}	
				var msg="数据删除\n\n\提示：\n\删除的数据将不存在\n\请谨慎操作！";
				if (!window.confirm(msg)) return false;
				var prame={
						typeId:$('#nodeId').val()
				}
				var url='${request.contextPath}/ConsumableCtrl/deletedata.do';
				$.ajax({
					type:'post',
					data:{'chechedid':dataId},
					url:url,
					success:function(data){
						var m=data.data;
						if(m){	
							$.notify({title:'提示：',message:'数据删除成功！'});
							var url='${request.contextPath}/ConsumableCtrl/getConsumbleCtrl.do';
							$.post(url,prame,function(html, textStatus, jqXHR) {	
								var $html = $(html);
								$("#consumableList").html($("#consumableList", $html).html());
								List('.consumable-list',listConfig);
							}, 'html');
						}else{
							$.notify({title:'提示：',message:'数据删除失败！'});
						}
					},
					error:function(){
						//alert("服务器连接失败");
					}
				});
			} 
		});
		
		
		$(".conditionData").on("click",function(){
			var btn = $('i', $(this));
			if (btn.hasClass('icon-chevron-down')) {
				btn.removeClass('icon-chevron-down');
				btn.addClass('icon-chevron-up');
				$('.searchbtn').show();
				$('.searchDiv').show();
				
				
			} else if (btn.hasClass('icon-chevron-up')) {
				btn.removeClass('icon-chevron-up');
				btn.addClass('icon-chevron-down');
				$('.searchDiv').hide();
				$('.searchbtn').hide();
			}
		});	
		
		$(".searchbtn").click(function(){
			var typeId=$('#nodeId').val();
			var url='${request.contextPath}/ConsumableCtrl/getConsumbleCtrl.do?typeId='+typeId;
			$.post(url, $('#searchform').serialize() , function(html, textStatus, jqXHR) {
				var $html = $(html);
				
				$("#consumableList").html($("#consumableList", $html).html());
				
				List('.consumable-list',listConfig);
			}, 'html');
		});
	});
	
	function EquipUserNum(id,name){
		tempModel.showModal({
			size : 'lg',
			icon : 'icon-search',
			title : '列表信息',
			showFoot : false,
			uri : '${request.contextPath}/ConsumableCtrl/lookConsumableAnddEquip.do',
			data:{'consumableid':id,'consumname':name}
		});
		
	}
</script>	
