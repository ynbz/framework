<form method="post" name="form1" id="form1">
	<div role="AfFrom" class="suredy-tabs">
		<input type="hidden" value="${form.id}" id="id" name="id"/>
		<!-- Tab panes -->
		<div class="tab-content" style="padding-top: 10px">
			<div role="AfFrom" class="tab-pane active" id="firstForm">
				<div class="row">
					<div class="col-md-12 col-xs-12">
						<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">文件分类</div>
							<#if type??>
								<input type="hidden" id="formTypeId" name="formTypeId" value="${type.id}" />
								<input type="text" class="form-control" readonly="readonly" id="typeName" name="typeName"  value="${type.name}" />
							<#else>
								<input type="hidden" id="formTypeId" name="formTypeId" value="${form.type.id}" />
								<input type="text" class="form-control" readonly="readonly" id="typeName" name="typeName"  value="${form.type.name}" />
							</#if>		
						</div>
					</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12 col-xs-12">
						<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">关联流程</div>
							<input type="hidden" id="flowId" name=flowId value="${form.flowId}" />
							<input type="text" class="form-control" readonly="readonly" id="flowName" name="flowName"  />		
						</div>
					</div>
					</div>
				</div>
			
				<div class="text-center">
					<a href="#secondForm" aria-controls="secondForm" role="tab"  data-toggle="tab" onclick="validatorfrom(this,'#secondForm')"  class="btn btn-info">下一步</a>
				</div>
			</div>
			<div role="AfFrom" style="height: auto;" class="tab-pane" id="secondForm">				
				<div class="row">
					<div class="col-md-6 col-xs-6">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">文件分类</div>
								<#if type??>
									<input type="text" id="formTypeName" name="formTypeName" readonly="readonly"  value="${type.name}" class="form-control">
								<#else>
									<input type="text" id="formTypeName" name="formTypeName" readonly="readonly"  value="${form.type.name}"   class="form-control">
								</#if>
								
							</div>
						</div>
					</div>
					<div class="col-md-6 col-xs-6">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">关联流程</div>
								<input type="text" id="formFlowName" name="formFlowName" readonly="readonly" class="form-control">
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6 col-xs-6">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">类型名称</div>
								<input type="text" class="form-control" id="name" name="name" value="${form.name}" />
							</div>
						</div>
					</div>
					<div class="col-md-6 col-xs-6">
						<div class="form-group" >
							<div class="input-group">
								<div class="input-group-addon">类型代码</div>
								<input type="text" class="form-control" id="fileType" name="fileType" value="${form.fileType}" />
							</div>
						</div>	
					</div>
				</div>				
				<div class="row">
					<div class="col-md-6 col-xs-6">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">关联菜单</div>
								<input type="hidden" id="menuId" name="menuId" value="${form.menuId}" />
								<input type="text" class="form-control" readonly="readonly" id="menuName" name="menuName" />
							</div>
						</div>
					</div>
					<div class="col-md-6 col-xs-6">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">启动模板</div>
								<select class="form-control" name="isTemplate" id="isTemplate">
									<option value="1" ${((form.isTemplate==1)?string('selected',''))!''}>否</option>
									<option value="2" ${((form.isTemplate==2)?string('selected',''))!''}>是</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				
				
				<div class="row hidden" id="mb">
					<div class="col-md-12 col-xs-12">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">选择模板</div>
								<input type="hidden" id="temTypeId" name="temTypeId" value="${form.temTypeId}" />
								<input type="text" class="form-control" readonly="readonly" id="temTypeName" name="temTypeName" />	
							</div>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12 col-xs-12">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">表单模式</div>
								<select class="form-control" name="formSel" id="formSel">
									<option value="1" ${((form.formSel==1)?string('selected',''))!''}>固定表单</option>
									<option value="2" ${((form.formSel==2)?string('selected',''))!''}>自定义表单</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row" id="zdy">
					<div class="col-md-12 col-xs-12">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">待办路径</div>
								<input type="text" id="doSthPath" name="doSthPath"  value="${form.doSthPath}"   class="form-control" />
							</div>
						</div>
					</div>
					<div class="col-md-12 col-xs-12">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">起草路径</div>
								<input type="text" id="draftPath" name="draftPath"  value="${form.draftPath}"   class="form-control" />
							</div>
						</div>
					</div>
					<div class="col-md-12 col-xs-12">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">列表路径</div>
								<input type="text" id="listPath" name="listPath"  value="${form.listPath}"   class="form-control" />
							</div>
						</div>
					</div>
				</div>
				
				
				<div class="row hidden" id="fdf">
					<div class="col-md-12 col-xs-12">
						<div class="form-group">
							<div class="input-group">
								<div class="input-group-addon">选择表单</div>
								<input type="hidden" id="defineId" name="defineId" value="${form.fdf.id}" />
								<input type="text" class="form-control" readonly="readonly" id="defineName" name="defineName"  value="${form.fdf.name}" />	
							</div>
						</div>
					</div>
				</div>
				
				<div class="text-center" style="margin-top: 10px;">
					<a href="#firstForm" aria-controls="firstForm" role="tab" onclick="validatorfrom(this,'#firstForm')" data-toggle="tab" class="btn btn-info">上一步</a>
					<div class="btn btn-info" id="btn-save">
						<i class="icon-save"></i> 保存
					</div>
				</div>
			</div>
			
		</div>
	</div>
</form>
<script type="text/javascript">
var validatorfrom;
require([ 'suredyModal', 'validation', 'suredyTreeSelector', 'jqueryForm' ],function(Model, validators, TreeSelector) {
	$('#form1').bootstrapValidator({
        message: '输入错误',
        feedbackIcons: {
//            valid: 'glyphicon glyphicon-ok',
//            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
		fields : {
			typeName : {
				validators : {
					notEmpty : {
						message : '请选择文件类型！'
					}
				}
			},
			flowName : {
				validators : {
					notEmpty : {
						message : '请选择关联流程！'
					}
				}
			},
			name : {
				validators : {
					notEmpty : {
						message : '请填写文件名称！'
					}
				}
			},
			doSthPath : {
				validators : {
					notEmpty : {
						message : '请填写代办路径！'
					}
				}
			},
			fileType : {
				validators : {
					notEmpty : {
						message : '请填写文件类型代码！'
					},
					callback:{
	                   	message: '库中已存在此文件类型代码，请重新填写！',
	                   	callback: function(value, validator,$field) {		                    		
	                   		var falg = true;
	                   		$.ajax({
	            				url : '${request.contextPath}/config/form/form-onlyFileType?fileType='+value,
	            				type : 'POST',
	            				async:false,
	            				success : function(msg) {
	            					if (!msg) {
	            						alert('Unknown exception!');
	            					} else if (!msg.success) {
	            						if(msg.data!=$('#id').val()){
	            							falg = false;
	            						}
	            					}
	            				},
	            				error : function(a, b, c) {
	            					alert('Server error! ' + b);
	            				}
	            			});         			                    	                    		
	                   		return falg;
	                   }
                   }
				}
			},
			menuName : {
				validators : {
					notEmpty : {
						message : '请选择关联菜单！'
					}
				}
			},
			draftPath : {
				validators : {
					notEmpty : {
						message : '请填写起草路径！'
					}
				}
			},
			listPath : {
				validators : {
					notEmpty : {
						message : '请填写列表路径！'
					}
				}
			},
			defineName : {
				validators : {
					notEmpty : {
						message : '请选择表单！'
					}
				}
			},
			temTypeName : {
				validators : {
					notEmpty : {
						message : '请选择模板！'
					}
				}
			}
		}
	});
	
	// 表单类型tree
	TreeSelector('input#typeName','${request.contextPath}/config/form/type-tree', {
		leafCheckbox:true,
		size : 'lg',
		showTitle : true,
		autoCollapse : false,
		canFolderActive:true,
		toggleActive : false
	});
	
	// 监听树的点击事件
	$('input#typeName').on(TreeSelector.nodeClick, function(event, $node) {
		if (!TreeSelector.isActive($node)) {
			$('#formTypeId').val('');
			$('#typeName').val('');
			$('#formTypeName').val('');
			$('#form1').data('bootstrapValidator').updateStatus('typeName', 'NOT_VALIDATED', null).validateField('typeName');
			return false;
		}
		$('#formTypeId').val(TreeSelector.data($node,'id'));
		$('#typeName').val(TreeSelector.data($node,'text'));
		$('#formTypeName').val(TreeSelector.data($node,'text'));
		$('#form1').data('bootstrapValidator').updateStatus('typeName', 'NOT_VALIDATED', null).validateField('typeName');
		TreeSelector.hidden(this);
		return true;
	});
	
	// 流程tree
	TreeSelector('input#flowName','${request.contextPath}/config/flow/tree', {
		leafCheckbox:true,
		size : 'lg',
		showTitle : true,
		autoCollapse : false,
		canFolderActive:false
	});
	
	// 监听树的点击事件
	$('input#flowName').on(TreeSelector.nodeClick, function(event, $node) {
		if (!TreeSelector.isActive($node)) {
			$('#flowId').val('');
			$('#flowName').val('');
			$('#formFlowName').val('');
			$('#form1').data('bootstrapValidator').updateStatus('flowName', 'NOT_VALIDATED', null).validateField('flowName');
			return false;
		}
		var nodeData = TreeSelector.data($node);
		$('#flowId').val(nodeData);
		$('#flowName').val(TreeSelector.data($node,'text'));
		$('#formFlowName').val(TreeSelector.data($node,'text'));
		$('#form1').data('bootstrapValidator').updateStatus('flowName', 'NOT_VALIDATED', null).validateField('flowName');
		TreeSelector.hidden(this);
		return true;
	});
	
	// 关联菜单tree
	TreeSelector('input#menuName','${request.contextPath}/config/menu/tree', {
		leafCheckbox:true,
		size : 'lg',
		showTitle : true,
		autoCollapse : false,
		canFolderActive:true
	});
	
	// 监听树的点击事件
	$('input#menuName').on(TreeSelector.nodeClick, function(event, $node) {
		if (!TreeSelector.isActive($node)) {
			$('#menuId').val('');
			$('#menuName').val('');
			$('#form1').data('bootstrapValidator').updateStatus('menuName', 'NOT_VALIDATED', null).validateField('menuName');
			return false;
		}
		var nodeData = TreeSelector.data($node);
		$('#menuId').val(nodeData.id);
		$('#menuName').val(TreeSelector.data($node,'text'));
		$('#form1').data('bootstrapValidator').updateStatus('menuName', 'NOT_VALIDATED', null).validateField('menuName');
		TreeSelector.hidden(this);
		return true;
	});
	
	// 关联起草模板tree
	TreeSelector('input#temTypeName','${request.contextPath}/config/templatetype/tree', {
		leafCheckbox:true,
		size : 'lg',
		showTitle : true,
		autoCollapse : false,
		canFolderActive:true
	});
	
	// 监听树的点击事件
	$('input#temTypeName').on(TreeSelector.nodeClick, function(event, $node) {
		if (!TreeSelector.isActive($node)) {
			$('#temTypeId').val('');
			$('#temTypeName').val('');
			$('#form1').data('bootstrapValidator').updateStatus('temTypeName', 'NOT_VALIDATED', null).validateField('temTypeName');
			return false;
		}
		var nodeData = TreeSelector.data($node);
		$('#temTypeId').val(nodeData);
		$('#temTypeName').val(TreeSelector.data($node,'text'));
		$('#form1').data('bootstrapValidator').updateStatus('temTypeName', 'NOT_VALIDATED', null).validateField('temTypeName');
		TreeSelector.hidden(this);
		return true;
	});
	
	// 自定义表单tree
	TreeSelector('input#defineName','${request.contextPath}/config/form/custom-form-tree', {
		leafCheckbox:true,
		size : 'lg',
		showTitle : true,
		autoCollapse : false,
		canFolderActive:false
	});
	
	// 监听树的点击事件
	$('input#defineName').on(TreeSelector.nodeClick, function(event, $node) {
		if (!TreeSelector.isActive($node)) {
			$('#defineId').val('');
			$('#defineName').val('');
			$('#form1').data('bootstrapValidator').updateStatus('defineName', 'NOT_VALIDATED', null).validateField('defineName');
			return false;
		}
		var nodeData = TreeSelector.data($node);
		$('#defineId').val(nodeData);
		$('#defineName').val(TreeSelector.data($node,'text'));
		$('#form1').data('bootstrapValidator').updateStatus('defineName', 'NOT_VALIDATED', null).validateField('defineName');
		TreeSelector.hidden(this);
		return true;
	});
	
	var setActive = function(){
		var menuId = $('#menuId').val();
		var flowId = $('#flowId').val();
		var temTypeId = $('#temTypeId').val();
		if (menuId) {
			TreeSelector.nodes('#menuName').each(function(index){
				var node = $(this);
				var id = TreeSelector.data(node, 'id');
				if (menuId == id) {
					node.click();
					return false; //跳出当前each循环
				} 
			});
		}
		if (flowId) {
			TreeSelector.nodes('#flowName').each(function(index){
				var node = $(this);
				var id = TreeSelector.data(this);
				if (flowId == id) {
					node.click();
					return false; //跳出当前each循环
				} 
			});
		}
		if (temTypeId) {
			TreeSelector.nodes('#temTypeName').each(function(index){
				var node = $(this);
				var id = TreeSelector.data(this);
				if (temTypeId == id) {
					node.click();
					return false; //跳出当前each循环
				} 
			});
		}
		
	}
	setActive();
	
	validatorfrom = function(dom,value) {
		var $this = $(dom);
		var d1=$('#form1').data('bootstrapValidator').validate();
		var vaild=$('#form1').data('bootstrapValidator').isValid();			
		if(!vaild){
			$this.attr("href","#");
			$this.attr("data-toggle","");
			return false;
		}else{
			$this.attr("href",value);
			$this.attr("data-toggle","tab");
		}
	}
	$('#btn-save').click(function() {
		var d1=$('#form1').data('bootstrapValidator').validate();
		var vaild=$('#form1').data('bootstrapValidator').isValid();			
		if(!vaild){
			return false;
		}
		$.ajax({
			url : '${request.contextPath}/config/form/form-save.do',
			dataType : 'json',
			type : 'POST',
			data : $('#form1').serialize(),
			success : function(success) {
				if (!success) {
					alert('保存表单数据失败！');
				} else if (!success.success) {
					alert('保存表单数据失败！\n\n' + success.msg);
				} else {
					$.notify({title:'提示：',message:'表单数据已保存!'});
					Suredy.loadContent('${request.contextPath}/config/form/list.do');
					Suredy.Modal.closeModal();
				}
			},
			error : function(a, b, c) {
				alert('服务器错误! ' + b);
			}
		});
	});	
});

if($('#formSel').val()==1){
	$('#zdy').removeClass("hidden");
	$('#fdf').addClass('hidden');
}else{
	$('#zdy').addClass('hidden');
	$('#fdf').removeClass("hidden");
}
if($('#isTemplate').val()==1){
	$('#mb').addClass('hidden');
}else{
	$('#mb').removeClass("hidden");
}

$('#formSel').on('click', function() {
	//alert($('#formSel').val());
	if($('#formSel').val()==1){
		$('#zdy').removeClass("hidden");
		$('#fdf').addClass('hidden');
		$('#defineId').val('');
		$('#defineName').val('');	
	}else{
		$('#zdy').addClass('hidden');
		$('#fdf').removeClass("hidden");
		$('#doSthPath').val('');
		$('#draftPath').val('');
		$('#listPath').val('');
	}
});

$('#isTemplate').on('click', function() {
	//alert($('#formSel').val());
	if($('#isTemplate').val()==1){
		$('#mb').addClass('hidden');
		$('#temTypeId').val('')
	}else{
		$('#mb').removeClass("hidden");
	}
});
</script>