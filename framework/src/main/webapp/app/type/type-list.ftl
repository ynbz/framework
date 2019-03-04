<div class="panel panel-default" id="equipTypePanel" >
		<div class="panel-heading">
			<input type="hidden" value="" id="nodeId" />
			<#if childid??>
			<input type="hidden" value="${childid}" id="childid" />
			</#if>
			<i class="icon-sitemap"></i> 设备分类管理 （<i class="bg-danger select-node-info" id="selected-info">请点击选择</i>）
		</div>
		
		<table  width="100%">
			 <td width="200px" style="vertical-align: top;padding:5px;">
			 <div class="row" style="padding-bottom: 5px;" id="addEditID" >												
				<div class="col-md-12 col-sm-12">
					<div class="btn btn-info" id="add">
						<i class="icon-plus"></i> 新增
					</div>										
					<div class="btn btn-info" id="edit">
						<i class="icon-edit "></i> 修改
					</div>
					<div class="btn btn-danger" id="remove">
						<i class="icon-remove"></i> 删除
					</div>				  
				</div>
			</div>
			<div class="panel panel-default" id="treePanel" style="overflow: auto;width: 230px;">		 
				<div class="panel-body menu-tree" id="typetreeid" style="padding: 0;">加载中......</div>
			</div>
			</td>
			<td id="propertyList" style="display: none;vertical-align: top;" >
			<ul class="nav nav-tabs" id="propertyListtab">
				<li class="active"><a href="#propertycon" data-toggle="tab">属性配置</a></li>
				<li><a href="#templatecon" data-toggle="tab" onclick="showTemplate()">模板配置</a></li>
			</ul>
			<div class="tab-content">
				<div class="tab-pane fade in active" id="propertycon"  style="padding: 10px;">
				
				<div class="panel-body" id="propertypanel" >
					<table class="property-list"
						data-page="<#if pageIndex??>${pageIndex}<#else>1</#if>"
						data-page-size="<#if pageSize??>${pageSize}<#else>20</#if>"
						data-count="<#if count??>${count}<#else>0</#if>">
						<tr class="title-row">
							<th style="display: none"></th>
							<th>属性名称</th>
							<th>显示顺序</th>
							<th>表头显示</th>
							<th>查询条件</th>
							<th>主要配置</th>
							<th>需求项</th>
							<th>属性单位</th>
						</tr>
						<#if data??> <#list data as pro>
						<tr>
							<td style="display: none;">${pro.id}</td>
							<td>${pro.propertyName}</td>
							<td>${pro.sort}</td>
							<td>${(pro.isShow==1)?string('是','否')}</td>
							<td>${(pro.isSearch==1)?string('是','否')}</td>
							<td>${(pro.isPrimeAttribute==1)?string('是','否')}</td>
							<td>${(pro.isNeed==1)?string('是','否')}</td>
							<td>${pro.propertyUnit}</td>
						</tr>
						</#list> </#if>
					</table>
					</div>
				</div>
				<div class="tab-pane fade" id="templatecon"  style="padding: 10px;">
					<div class="panel-body" id="templatepanel">
						<div class="row" style="padding: 10px;">
						<input id="templateId" name="templateName" value="" type="hidden" />
							<textarea class="form-control" id="templateinfo"
								name="templateinfo" rows="13" cols="20"></textarea>
						</div>
						<div class="row" align="center">
							<div class="btn btn-info" onclick="saveData()">
								<i class="icon-save"></i> 保存
							</div>
						</div>
					</div>
				</div>
			</div> 
			</td>
		</table>			
		<div class="panel-footer"></div>
	</div>

<script type="text/javascript">
	require(['suredyList','suredyTree','suredyModal','notify'], function(List, Tree, Modal) {
	
			/* $('#treePanel li a').each(function(index){
				  console.log("=======11===="+$('#childid').val());
				  var data = Tree.data(this);				
				 if($('#childid').val()==data){
					
					 $(this).addClass("active");
					
					 List('.property-list',listConfig);
					 $('#childid').val(undefined)
				 } 
				}); */
		
		var treeDistance=function(){
			var a=$(window).height();
			var r=$('#treePanel').offset().top;			
			var height=a-r-90;
		
			$('#typetreeid').css('height',height);
			$('#propertypanel').css('height',height-40);
			
			};
			
			treeDistance();
		var ouTree = function() {
			Tree('.menu-tree', '${request.contextPath}/type/tree',{
				style : 'file',
				size : 'sm'
			});	
			
			$('.menu-tree').on(Suredy.Tree.nodeClick, function(event, $node) {
				var nodeData = Tree.data($node);
				var nodeisActive =Tree.isActive($node);
				if(nodeisActive){
					$('#nodeId').val(nodeData);
					var param={
							nodeid:$('#nodeId').val()
							};
					$.ajax({
						type:'post',
						data:param,
						url:'${request.contextPath}/classifyManageCtrl/getListData.do',
						success:function(data){
							var data=data.data;
							if(data==1){
								$('#propertyList').show();	
								$.post('${request.contextPath}/classifyManageCtrl/getClassifyData.do',param,function(html, textStatus, jqXHR) {
									var $html = $(html);
										$("#propertypanel").html($("#propertypanel", $html).html());
										List('.property-list',listConfig);
										treeDistance();
								}, 'html');		
								showTemplate();
							}else{
								
								$('#propertyList').hide();
							}
						
						},
						error:function(){
							//console.log("服务器连接失败");
						}
					});
				}else{
					$('#nodeId').val("");
					$('#propertyList').hide();
				}
				
			
				
			});
		};
		ouTree();	
		
		if($('#childid').val()!=undefined){
			Tree.nodes($('.menu-tree')).each(function(index) {
				var data = Tree.data(this);
				 if($('#childid').val()==data){
					 $(this).click();
					 $('#childid').val(undefined)
				 } 
			});
		};
		$('#add').on('click', function() {		
			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : '新增',
				showFoot : false,
				uri : '${request.contextPath}/type/info.do'
			});
		
		});
		
		$('#edit').on('click', function() {	
			var nodeId = $('#nodeId').val();
			if (nodeId == '') {
				alert('请选择需要修改的节点！');
				return;
			}
			Modal.showModal({
				size : 'lg',
				icon : 'icon-edit',
				title :'修改',
				showFoot : false,
				uri : '${request.contextPath}/type/info.do?id='+nodeId
			});
		
		});
	
		
		$('#remove').on('click', function() {
			var nodeId = $('#nodeId').val();
			if (nodeId == '') {
				alert('请选择需要删除的节点！');
				return;
			}
	
			var msg = '数据删除\n\n\提示：\n\该类型下面的设备台账数据也会删除\n\是否确认删除【选中的节点】？';
	
			if (!window.confirm(msg)) {
				return;
			}
			$.ajax({
				url : '${request.contextPath}/type/delete.do?id='+nodeId,
				type : 'get',
				success : function(msg) {
					if (!msg) {
						alert('Unknown exception!');
					} else if (!msg.success) {
						alert("删除失败！\n\n" + msg.msg);
					} else {
						//alert("删除成功！");
						$.notify({title:'提示：',message:'数据删除成功！'});
						$("#templateId").val("");	
						
						$('#propertyList').hide();
						ouTree();
					}
				},
				error : function(a, b, c) {
					alert('Server error! ' + b);
				}
			});
		});	
		
		var listConfig = ({
			header : true,
			footer : true,
			search : false,
			checkbox : true,			
			paginate : function(page, size) {
				
				 $.post('${request.contextPath}/classifyManageCtrl/getClassifyData.do',{'nodeid':$('#nodeId').val(),'page':page,'size':size},function(html, textStatus, jqXHR) {
					var $html = $(html);
						$("#propertyList").html($("#propertyList", $html).html());
						List('.property-list',listConfig);	
				}, 'html');
			},
			
			 btns : [ {
				text :'新增',
				icon : 'icon-plus',
				style : 'btn-info',
				click : function() {
					Modal.showModal({
						size : 'lg',
						icon : 'icon-plus',
						title : '新增',
						showFoot : false,
						uri : '${request.contextPath}/classifyManageCtrl/addDatapage.do?nodeid='+$('#nodeId').val()
					});
				}
			}, {
				text :'修改',
				icon : 'icon-edit',
				style : 'btn-danger',
				click : function() {
					var checked = List.checked($('.property-list'));
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
							uri : '${request.contextPath}/classifyManageCtrl/addDatapage.do?nodeid='+$('#nodeId').val()+"&dataId="+dataId
						});
					}
					
				}
			},  {
				text : '删除',
				icon : 'icon-remove',
				style : 'btn-danger',
				click : function() {
					var checked = List.checked($('.property-list'));
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
							url:'${request.contextPath}/classifyManageCtrl/deletedata.do',
							success:function(data){
								var m=data.data;
								if(m){	
								//	Suredy.loadContent('${request.contextPath}/classifyManageCtrl/getClassifyData.do?nodeid='+$('#nodeId').val());
									$.notify({title:'提示：',message:'数据删除成功！'});
									 $.post('${request.contextPath}/classifyManageCtrl/getClassifyData.do',{'nodeid':$('#nodeId').val()},function(html, textStatus, jqXHR) {
											var $html = $(html);
												$("#propertyList").html($("#propertyList", $html).html());
												List('.property-list',listConfig);	
										}, 'html');
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
		List('.property-list',listConfig);
		//模板数据保存
		
	});
	function saveData(){
		var templateinfo=$("#templateinfo").val();	
		var id=$("#templateId").val();	
		$.ajax({
			dataType:'json',
			type:'post',
			data:{'templateinfo':templateinfo,'typeId':$('#nodeId').val(),'id':id},
			url:'${request.contextPath}/TypeTemplateInfoCtrl/savetemplateinfo.do',
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
				url:'${request.contextPath}/TypeTemplateInfoCtrl/gettemplateinfo.do',
				success:function(data){
					var id=data.id;
					var t=data.templateinfo;
					$("#templateinfo").val(t);
					$("#templateId").val(id)	
				},
				error:function(){
					alert("服务器出现问题了！");
				}
			});
	};
</script>	
