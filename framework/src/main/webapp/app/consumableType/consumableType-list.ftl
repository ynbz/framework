<div class="row">
<input type="hidden" value="" id="nodeId" />
			<#if childid??>
			<input type="hidden" value="${childid}" id="childid" />
			</#if>
	<div class="col-md-3 col-sm-3">
		<div class="row" style="height: 51px; padding: 8px 0px 8px 15px">
			<div class="btn btn-info" id="newTree">
				<i class="icon-plus"></i> 新建
			</div>
			<div class="btn btn-info" " id="editTree">
				<i class="icon-edit "></i> 修改
			</div>
			<div class="btn btn-danger" id="removeTree">
				<i class="icon-remove"></i> 删除
			</div>
		</div>
		<div class="panel panel-default" id="consumablepanel"
			style="overflow: auto; width: 280px;">
			<div class="panel-body consumable-tree" id="consumableTreeId"
				style="padding: 0;">加载中......</div>
		</div>
	</div>
	<div class="col-md-9 col-sm-9" id="consumproperty"  style="display: none;">
	<ul class="nav nav-tabs" id="propertyListtab">
				<li class="active"><a href="#consumpropertycon" data-toggle="tab">属性配置</a></li>
				<li><a href="#ctemplatecon" data-toggle="tab" onclick="showTemplate()">模板配置</a></li>
	</ul>
	<div class="tab-content">
	<div class="tab-pane fade in active" id="consumpropertycon" style="padding: 10px;">
		<div id="consumpropertyList" style="vertical-align: top;">
			<table class="consumproperty-list"
				data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>"
				data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>"
				data-count="<#if count??>${count}<#else>0</#if>">
				<tr class="title-row">
					<th style="display: none"></th>
					<th>属性名称</th>
					<th>显示顺序</th>
					<th>表头显示</th>
				</tr>
				<#if data??> <#list data as pro>
				<tr>
					<td style="display: none;">${pro.id}</td>
					<td>${pro.propertyName}</td>
					<td>${pro.sort}</td>
					<td>${(pro.isShow==1)?string('是','否')}</td>
				</tr>
				</#list> </#if>
			</table>
		</div>
		</div>
		<div class="tab-pane fade" id="ctemplatecon"  style="padding: 10px;">
			<div class="panel-body" id="templatepanel">
				<div class="row" style="padding: 10px;">
				<input id="contemplateId" name="contemplateId" value="" type="hidden" />
					<textarea class="form-control" id="contemplateinfo"
						name="contemplateinfo" rows="13" cols="20"></textarea>
				</div>
				<div class="row" align="center">
					<div class="btn btn-info" onclick="saveData()">
						<i class="icon-save"></i> 保存
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<script type="text/javascript">
	require(['suredyTree','suredyList','suredyModal','notify'], function(Tree, List, Modal) {
		
		var treeDistance=function(){
			var a=$(window).height();
			var r=$('#consumablepanel').offset().top;			
			var height=a-r-90;
		
			$('#consumableTreeId').css('height',height);
			//$('#consumproperty-list').css('height',height-40);
			
			};
			
			treeDistance();
		
		var listConfig = ({
			header : true,
			footer : true,
			search : false,
			checkbox : true,			
			paginate : function(page, size) {
			},
			
			btns : [ {
				text :'新建',
				icon : 'icon-plus',
				style : 'btn-info',
				click : function() {
					Modal.showModal({
						size : 'lg',
						icon : 'icon-plus',
						title : '新增',
						showFoot : false,
						uri : '${request.contextPath}/ConsumPropertyCtrl/addDatapage.do?nodeid='+$('#nodeId').val()
					});
				}
			}, {
				text :'修改',
				icon : 'icon-edit',
				style : 'btn-info',
				click : function() {
					var checked = List.checked($('.consumproperty-list'));
					if(checked.length==0){
						alert("请选择需要修改的数据");
					}else if(checked.length>1){
						alert("修改数据只能选择一条");
					}else{
						var dataId = $(checked[0]).find('td').eq(1).html();
						Modal.showModal({
							size : 'lg',
							icon : 'icon-plus',
							title : '修改',
							showFoot : false,
							uri : '${request.contextPath}/ConsumPropertyCtrl/addDatapage.do?nodeid='+$('#nodeId').val()+"&dataId="+dataId
						});
					}
					
				}
			},  {
				text : '删除',
				icon : 'icon-remove',
				style : 'btn-danger',
				click : function() {
					var checked = List.checked($('.consumproperty-list'));
					var chechedids="chechedids";
					if(checked.length==0){
						alert("请选择需要删除的数据");
					}else{
						var msg = '是否确认删除数据？';						
						if (!window.confirm(msg)) {
							return;
						}
						for(var i=0;i<checked.length;i++){							
							chechedids=chechedids+","+checked.eq(i).find('td').eq(1).html();							
						}	
						
						$.ajax({
							type:'post',
							data:{'chechedids':chechedids},
							url:'${request.contextPath}/ConsumPropertyCtrl/deletedata.do',
							success:function(data){
								var m=data.data;
								if(m){	
									$.notify({title:'提示：',message:'数据删除成功！'});
									 $.post('${request.contextPath}/ConsumPropertyCtrl/getConsumProperData.do',{'nodeid':$('#nodeId').val()},function(html, textStatus, jqXHR) {
											var $html = $(html);
												$("#consumpropertyList").html($("#consumpropertyList", $html).html());
												List('.consumproperty-list',listConfig);	
										}, 'html');
								}else{
									$.notify({title:'提示：',message:'数据删除失败成功！'});
								}
							},
							error:function(){
								alert("服务器连接失败");
							}
						});
					}
				} 
			}]
		});	
		List('.user-list', listConfig);
		$("#newTree").click(function(){
			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : '新增',
				showFoot : false,
				uri : '${request.contextPath}/ConsumableTypeCtrl/info.do'
			});
		});
		
		$("#editTree").click(function(){
			var nodeId = $('#nodeId').val();
			if (nodeId == '') {
				alert('请选择需要修改的节点！');
				return;
			}
			Modal.showModal({
				size : 'lg',
				icon : 'icon-edit',
				title : '修改',
				showFoot : false,
				uri : '${request.contextPath}/ConsumableTypeCtrl/info.do?id='+nodeId
			});
		});
		$("#removeTree").click(function(){
			var nodeId = $('#nodeId').val();
			if (nodeId == '') {
				alert('请选择需要删除的节点！');
				return;
			}
	
			var msg = '数据删除\n\n\提示：\n\该类型下面的备品备件管理数据也会删除\n\是否确认删除【选中的节点】？';
	
			if (!window.confirm(msg)) {
				return;
			}
			$.ajax({
				url : '${request.contextPath}/ConsumableTypeCtrl/delete.do?id='+nodeId,
				type : 'get',
				success : function(msg) {
					if (!msg) {
						alert('Unknown exception!');
					} else if (!msg.success) {
						$.notify({title:'提示：',message:'数据删除失败！'+msg.msg});
					} else {
					//	alert("删除成功！");	
						$.notify({title:'提示：',message:'数据删除成功！'});
						$("#contemplateId").val("");							
						$('#consumproperty').hide();
						ouTree();
					}
				},
				error : function(a, b, c) {
					alert('Server error! ' + b);
				}
			});
		});
		
		var ouTree = function() {
			Tree('.consumable-tree', '${request.contextPath}/ConsumableTypeCtrl/Consumabletree',{
				style : 'file',
				size : 'sm'
			});	
			
			$('.consumable-tree').on(Suredy.Tree.nodeClick, function(event, $node) {
				var nodeData = Tree.data($node);
				var nodeisActive =Tree.isActive($node);
				if(nodeisActive){
					$('#nodeId').val(nodeData);
					var param={
							nodeid:nodeData
							};

					 $.ajax({
						type:'post',
						data:param,
						url:'${request.contextPath}/ConsumPropertyCtrl/getListData.do',
						success:function(data){
							var data=data.data;
							if(data==1){
								$('#consumproperty').show();	
								$.post('${request.contextPath}/ConsumPropertyCtrl/getConsumProperData.do',param,function(html, textStatus, jqXHR) {
									var $html = $(html);
										$("#consumpropertyList").html($("#consumpropertyList", $html).html());
										List('.consumproperty-list',listConfig);
										treeDistance();
								}, 'html');		
								showTemplate();
							}else{
								
								$('#consumproperty').hide();
							}
						
						},
						error:function(){
							//console.log("服务器连接失败");
						}
					});
					}else{
						$('#nodeId').val("");
						$('#consumproperty').hide();
					}			
			});
		};
		ouTree();
		if($('#childid').val()!=undefined){
			Tree.nodes($('.consumable-tree')).each(function(index) {
				var data = Tree.data(this);
				 if($('#childid').val()==data){
					 $(this).click();
					 $('#childid').val(undefined)
				 } 
			});
		};
	});
	
	function saveData(){
		var contemplateinfo=$("#contemplateinfo").val();	
		var id=$("#contemplateId").val();	
		
		$.ajax({
			dataType:'json',
			type:'post',
			data:{'templateinfo':contemplateinfo,'typeId':$('#nodeId').val(),'id':id},
			url:'${request.contextPath}/ConsumTemplateInfoCtrl/savetemplateinfo.do',
			success:function(data){
				if(data.data){
					alert("保存成功");
				}else{
					alert("数据保存失败");
				}
				
			},
			error:function(){
				alert("服务器出现问题了！");
			}
		});
	};
	
	function showTemplate(){
		  $.ajax({
				dataType:'json',
				type:'post',
				data:{'typeId':$('#nodeId').val()},
				async: false,
				url:'${request.contextPath}/ConsumTemplateInfoCtrl/gettemplateinfo.do',
				success:function(data){
					var id=data.contemplateId;
					var t=data.contemplateinfo;
					$("#contemplateinfo").val(t);
					$("#contemplateId").val(id)	
				},
				error:function(){
					alert("服务器出现问题了！");
				}
			});
	};
</script>	
