
<style type="text/css">
.input-lable {
	min-width: 95px;
}
</style>
<div class="container-fluid suredy-form" style="padding-top: 0">
	<form id="form" method="post">
	<#if cmdata??>
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">
							基本信息(<span style="color: red; font-size: 15px;">*&nbsp;</span>必填项)
						</h3>
					</div>
					<input type="hidden" id="id" name="id" value="${cmdata.id}" />
				<div class="panel-body">	
				<div class="row">
							<div class=" col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">
										<span style="color: red; font-size: 15px;">*&nbsp;</span>代码
										</div>
										<input type="text" class="form-control" id="cunsumCode"
											name="cunsumCode" value="${cmdata.cunsumCode}"  maxlength="50"/>
									</div>
								</div>
							</div>
							<div class=" col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">
										<span style="color: red; font-size: 15px;">*&nbsp;</span>型号
										</div>
										<input type="text" class="form-control" id="cunsumModel"
											name="cunsumModel" value="${cmdata.cunsumModel}" maxlength="50"/>
									</div>
								</div>
							</div>						
						</div>
						<div class="row">
							<div class=" col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">
											<span style="color: red; font-size: 15px;">*&nbsp;</span>物资名称
										</div>
										<input type="text" class="form-control" id="consumableName" name="consumableName" value="${cmdata.consumableName}" maxlength="50"/>
									</div>
								</div>
							</div>
							<div class=" col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">
											<span style="color: red; font-size: 15px;">*&nbsp;</span>类型
										</div>
										<input type="hidden" id="tempTypeID" name="tempType" value="${cmdata.type.id}" /> 
										<input type="hidden" id="typeId" name="type.id" value="${cmdata.type.id}" /> 
										<input type="text" class="form-control" id="consumerName" name="consumerName" value="${cmdata.type.consumerName}" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							
							<div class=" col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">品牌</div>
										<input type="text" class="form-control" id="cunsumbrand" name="cunsumbrand"
											value="${cmdata.cunsumbrand}" maxlength="50"/>
									</div>
								</div>
							</div>		
							<div class=" col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">单位</div>
										<input type="text" class="form-control" id="unit" name="unit"
											value="${cmdata.unit}" maxlength="20"/>
									</div>
								</div>
							</div>							
						</div>		
						<div class="row">
							<div class=" col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">单价</div>
										<input type="text" class="form-control" id="unitPrice" name="unitPrice"
											value="${cmdata.unitPrice}" maxlength="30"/>
									</div>
								</div>
							</div>	
							<div class=" col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">
										生产厂商
										</div>
										<input type="text" class="form-control" id="vendor"
											name="vendor" value="${cmdata.vendor}" maxlength="50"/>
									</div>
								</div>
							</div>						
						</div>		
						<div class="row">
							
							<div class=" col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">供应商</div>
										<input type="text" class="form-control" id="supplier" name="supplier"
											value="${cmdata.supplier}" maxlength="50"/>
									</div>
								</div>
							</div>		
							<div class=" col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">
										<span style="color: red; font-size: 15px;">*&nbsp;</span>库存量
										</div>
										<input type="text" class="form-control" id="stock"
											name="stock" value="${cmdata.stock}" min="0" maxlength="6"  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  
                                    		onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}"/>
									</div>
								</div>
							</div>				
						</div>	
						<div class="row">
							
							 <div class=" col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">已用量</div>
										<input type="number" class="form-control" id="hasTheDosage" name="hasTheDosage"
											value="${cmdata.hasTheDosage}" min="0" readonly="readonly" />
									</div>
								</div>
							</div>	
							<div class=" col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">
										占用量
										</div>
										<input type="number" class="form-control" id="footprint"
											name="footprint" value="${cmdata.footprint}" min="0" readonly="readonly"/>
									</div>
								</div>
							</div>						
						</div>	
						<div class="row">
						 <div class=" col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">适用设备类型</div>
										<textarea  class="form-control" id="equipModelApply" name="equipModelApply" rows="3"
											style="resize: none;" maxlength="250">${cmdata.equipModelApply}</textarea>
									</div>
								</div>
							</div>		
							
							<div class=" col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">备注</div>
										<textarea  class="form-control" id="comm" name="comm" rows="3"
											style="resize: none;"	maxlength="250">${cmdata.comm}</textarea>
									</div>
								</div>
							</div>		
												
						</div>																							
						</div>
				</div>
			</div>
		</div>
		<div class="row">		
		<div class="col-md-12">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">配置信息</h3>
					</div>
					<div class="panel-body" id="propertyid">
					<#if datalist??><#list datalist as dl>
						<div class='col-md-6'>
							<div class='form-group'>
								<div class='input-group'>
									<div class='input-lable input-group-addon'>${dl.propertyName}</div>
									<input type='text' class='form-control' id='${dl.field}'
										name='${dl.field}' value='${cmdata.getColvalue(dl.field)}' maxlength="50"/>
								</div>
							</div>
						</div>
						</#list></#if>
					</div>
				</div>
			</div>
		</div>
		</#if>		
	</form>
</div>
<script type="text/javascript">
	require(['suredyTree','suredyList','suredyModal','suredyTreeSelector','notify'], function(Tree, List, Modal,TreeSelector) {

		var validata=function(){
			var cunsumModel=$("#cunsumModel").val();
			var consumableName=$("#consumableName").val();
			var cunsumCode=$("#cunsumCode").val();		
			var typeId=$("#typeId").val();
			var stock=$("#stock").val();
			var stock=$("#stock").val();
			if(cunsumCode==""){
				alert("代码不能为空");
				return false;
			}else if(cunsumModel==""){
				alert("型号不能为空");
				return false;
			}else if(consumableName==""){
				alert("物资名称不能为空");
				return false;
			}else if(typeId==""||typeId==undefined){
				alert("类型不能为空");
				return false;
			}else if(stock==""){
				alert("库存量不能为空");
				return false;
			}
			return true;
		};
		$('.btn-ok').click(function() {
			if(validata()){
				 $.ajax({
						url : '${request.contextPath}/ConsumableCtrl/saveAndUpdatedata.do',
						dataType : 'json',
						type : 'Post',
						data : $('#form').serialize(),
						success : function(data) {	
							if(data.data){
								$.notify({title:'提示：',message:'数据保存成功！'});
								Suredy.loadContent('${request.contextPath}/ConsumableCtrl/getConsumbleCtrl.do?typeId='+$('#tempTypeID').val());
								Modal.closeModal();		
							}else{
								alert('数据保存失败！'+data.msg);
							}
									
						},
						error : function(a, b, c) {
							alert('服务器错误! ' + b);
						}
					});
			}
			
		});
		
		TreeSelector('#consumerName',Suredy.ctxp + '/ConsumableTypeCtrl/Consumabletree', {
					autoCollapse : false,
					style : 'department',
					showTitle : true,
					canFolderActive : false,
					size : 'lg'
			});	
		$('#consumerName').one(TreeSelector.selectorShown, function() {			
			  var id=$('#typeId').val();
				if (id!= '') {
					 TreeSelector.nodes($('#consumerName')).each(function(index) {
						var data = TreeSelector.data(this);										
						if (id== data) {
							$(this).addClass('active');
						}
					});
				}
		});			
		
		
		$('#consumerName').on(TreeSelector.nodeClick, function(event, $node) {
			if (!TreeSelector.isActive($node)) {
				$('#typeId').val("");
				$('#consumerName').val("");
				return false;
			}
		
			var nodeData = TreeSelector.data($node);
			var name = TreeSelector.data($node, 'text');
			$('#typeId').val(nodeData);
			$('#consumerName').val(name);
			var id = $("#id").val();
			if (nodeData!=""&&nodeData!=undefined) {							
					url='${request.contextPath}/ConsumableCtrl/addOrUpdatePage.do';
					if ($("#tempTypeID").val() == $('#typeId').val()&& id != '') {
						$.post(url, {'id' : id}, function(html, textStatus,jqXHR) {
							var $html = $(html);
							$("#propertyid").html($("#propertyid",$html).html());
						}, 'html');
					} else {
						$.post(url, {'typeID' : nodeData}, function(html, textStatus,jqXHR) {
							var $html = $(html);
							$("#propertyid").html($("#propertyid",$html).html());
						}, 'html');
					}
				} else {
					$('#typeId').val('');
				}

			return true;
		
		});
		
	});
</script>