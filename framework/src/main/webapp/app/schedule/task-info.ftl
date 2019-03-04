<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">
		<form name="form" id="form1">
			<#if task??>
			<input type="hidden" id="id" name="id" value="${task.id}" />
			<div class="col-md-12 col-sm-12">	
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">任务名称</div>
						<input type="text" class="form-control" id="name" name="name" value="${task.name}" placeholder="任务名称" />
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">任务时限</div>
						<input type="text" class="form-control datetimepicker" data-format="yyyy-MM-dd HH:mm:ss" data-view="D Y M T" readonly="readonly" id="startTime" name="startTime" value="${(task.start?string('yyyy-MM-dd HH:mm:ss'))!''}" placeholder="起始时间" />
						<div class="input-group-addon" style="border-left:0px; border-right:0px;"> --- </div>
						<input type="text" class="form-control datetimepicker" data-format="yyyy-MM-dd HH:mm:ss" data-view="D Y M T" readonly="readonly" id="endTime" name="endTime" value="${(task.end?string('yyyy-MM-dd HH:mm:ss'))!''}" placeholder="终止时间" />
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">	
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">主体类型</div>
						<select class="form-control" id="subjectType" name="subjectType">
							<option>---请选择---</option>
							<option value="1" ${((task.subjectType==1)?string('selected="selected"',''))!''}>---个人---</option>
							<option value="2" ${((task.subjectType==2)?string('selected="selected"',''))!''}>---班组---</option>
						</select>
					</div>
				</div>
			</div>			
			<div class="col-md-12 col-sm-12">	
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">任务主体</div>
						<input type="hidden" id="subjectId" name="subjectId" value="${task.subjectId}" />
						<input type="text" class="form-control" readonly="readonly" id="subjectName" name="subjectName" value="${task.subjectName}" placeholder="完成任务的主体" />
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">任务描述</div>
						<textarea class="form-control" rows="3" id="description" name="description">${task.description}</textarea>
					</div>
				</div>
			</div>	
			</#if>

		</form>
		<div class="text-center">
			<div class="btn btn-info" id="btn-save">
				<i class="icon-save"></i>
				保存
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	require([ 'validation', 'suredyModal', 'suredyTreeSelector', 'suredyDatetimepicker' ], function(validators, Modal, TreeSelector, Datetimepicker) {
		Datetimepicker.AutoDelegate(true);
		
		var loadSubjects = function() {
			if ($('#subjectType').val() == 1) {
				TreeSelector('input#subjectName', '${request.contextPath}/config/ou/tree/true/true/true', {
					leafCheckbox:true,
					size : 'lg',
					showTitle : true,
					autoCollapse : false,
					canFolderActive:false,
					toggleActive : false
				});
				// 监听树的点击事件
				$('input#subjectName').on(TreeSelector.nodeClick, function(event, $node) {
					$('#subjectId').val(TreeSelector.data($node,'id'));
					$('#subjectName').val(TreeSelector.data($node,'fullName'));
					$('#form1').data('bootstrapValidator').updateStatus('subjectName', 'NOT_VALIDATED', null).validateField('subjectName');
					TreeSelector.hidden(this);
					return true;
				});
			} else if ($('#subjectType').val() == 2) {
				TreeSelector('input#subjectName', '${request.contextPath}/subject/group-tree', {
					leafCheckbox:true,
					size : 'lg',
					showTitle : true,
					autoCollapse : false,
					toggleActive : false
				});
				// 监听树的点击事件
				$('input#subjectName').on(TreeSelector.nodeClick, function(event, $node) {
					$('#subjectId').val(TreeSelector.data($node,'id'));
					$('#subjectName').val(TreeSelector.data($node,'name'));
					$('#form1').data('bootstrapValidator').updateStatus('subjectName', 'NOT_VALIDATED', null).validateField('subjectName');
					TreeSelector.hidden(this);
					return true;
				});
			}
		};
		loadSubjects();

		
		$('#subjectType').change(function(){
			loadSubjects();
		});
		

			
		$('#form1').bootstrapValidator({
	        message: '输入错误',
	        feedbackIcons: {
	            validating: 'glyphicon glyphicon-refresh'
	        },
			fields : {
				name : {
					validators : {
						notEmpty : {
							message : '请填写任务名称！'
						}
					}
				},
				startTime : {
					validators : {
						notEmpty : {
							message : '请设置开始时间！'
						}
					}
				},
				endTime : {
					validators : {
						notEmpty : {
							message : '请设置终止时间！'
						}
					}
				},
				subjectType : {
					validators : {
						notEmpty : {
							message : '请选择任务主体类型！'
						}
					}
				},
				subjectName : {
					validators : {
						notEmpty : {
							message : '请选择任务主体！'
						}
					}
				}
				
			}
		});	
		
		$('#btn-save').click(function() {
			$('#form1').data('bootstrapValidator').validate();
			var valid=$('#form1').data('bootstrapValidator').isValid();		
			if(!valid){
				return false;
			}
			
			$.ajax({
				url : '${request.contextPath}/schedule/task-save.do',
				dataType : 'json',
				type : 'POST',
				data : $('#form1').serialize(),
				success : function(success) {
					if (!success) {
						alert('保存任务排班失败！');
					} else if (!success.success) {
						alert('保存任务排班失败！\n\n' + success.msg);
					} else {
						$.notify({title:'提示：',message:'任务排班信息已保存!'});
						Suredy.loadContent('${request.contextPath}/schedule/index');
						Suredy.Modal.closeModal();
					}
				},
				error : function(a, b, c) {
					alert('服务器错误! ' + b);
				}
			});
			
		});	
		
		
	});
</script>
