<style>
.input-lable {
	min-width: 95px;
}

</style>
<div class="container-fluid suredy-form" style="padding-top: 0">
	<div class="row">	
	<form name="schform" id="schform">
		<input type="hidden" id="proid" name="proid" value="${proid}" />
		<input type="hidden" id="schid" name="schid" value="${sch.schid}" />
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<div class="form-group" >
					<div class="input-group">
						<div class="input-lable input-group-addon input-lable">巡检状态</div>
						<input type="text" class="form-control datetimepicker" id="periodStart" name="periodStart" value="${(sch.periodStart?string('yyyy-MM-dd'))!''}" data-format="yyyy-MM-dd" data-foot="false" readonly />
						<div class="input-group-addon">~</div>
						<input type="text" class="form-control datetimepicker" id="periodEnd" name="periodEnd" value="${(sch.periodEnd?string('yyyy-MM-dd'))!''}" data-format="yyyy-MM-dd" data-foot="false" readonly />
					</div>
				</div>	
			</div>
		</div>				
	
		<div class="row">
			<div class="col-md-6 col-xs-6">
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon input-lable">产量</div>
						<input type="text" class="form-control"  id="number" name="number" value="${sch.number}"/>	
					</div>
				</div>
			</div>
			<div class="col-md-6 col-xs-6">
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon input-lable">单位</div>
						<select class="form-control" id="unit" name="unit">
							<option value="1" ${((sch.unit==1)?string('selected',''))!''}>立方</option>
							<option value="2" ${((sch.unit==2)?string('selected',''))!''}>吨</option>
							<option value="3" ${((sch.unit==3)?string('selected',''))!''}>个</option>
							<option value="4" ${((sch.unit==4)?string('selected',''))!''}>件</option>
							<option value="5" ${((sch.unit==5)?string('selected',''))!''}>千克</option>
						</select>
					</div>
				</div>
			</div>
		</div>				
		<div class="text-center">
			<div class="btn btn-info" id="btn-save" >
				<i class="icon-save"></i> 保存
			</div>
		</div>	
	</form>	
	</div>
</div>
<script type="text/javascript">
require(['validation','suredyModal','suredyDatetimepicker'],function(validators,Modal) {
	Suredy.Datetimepicker.AutoDelegate(true);
	$('form#schform').bootstrapValidator({
        message: '输入错误',
         feedbackIcons: {
            validating: 'glyphicon glyphicon-refresh'
        },
		fields : {
			periodStart : {
				validators : {
					notEmpty : {
						message : '请填写周期的开始时间！'
					}
				}
			},
			periodEnd : {
				validators : {
					notEmpty : {
						message : '请填写周期的结束时间！'
					}
				}
			},
			number : {
				validators : {
					notEmpty : {
						message : '请填写产量！',
					},
					callback:{
	                   	message: '产量填写错误，请填写正确的产量！',
	                   	callback: function(value, validator,$field) {		                    		
	                   		var falg = true;
	                   		var isNum =  /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
            		        if(!isNum.test(value)){
            		            falg = false;
            		        }               			                    	                    		
	                   		return falg;
	                   }
                   }
				}
			}
		}
	});
	
	$("#periodStart").on('hidden.bs.suredy.datetimepicker', function() {
		$('#schform').data('bootstrapValidator').updateStatus('periodStart', 'NOT_VALIDATED', null).validateField('periodStart');
	})
	
	$("#periodEnd").on('hidden.bs.suredy.datetimepicker', function() {
		$('#schform').data('bootstrapValidator').updateStatus('periodEnd', 'NOT_VALIDATED', null).validateField('periodEnd');
	})
	
	
	$('#btn-save').click(function() {
		var d1=$('form#schform').data('bootstrapValidator').validate();
		var vaild=$('form#schform').data('bootstrapValidator').isValid();			
		if(!vaild){
			return false;
		}
		
		
		$.ajax({
			url :'${request.contextPath}/pro-schedule/sch-save',
			dataType : 'json',
			type : 'POST',
			data : $('#schform').serialize(),
			success : function(success) {
				if (!success) {
					$.notify({title:'提示：',message:'保存失败！'});
				} else if (!success.success) {
					$.notify({title:'提示：',message:'保存失败！\n\n' + success.msg});
				} else {
					$.notify({title:'提示：',message:'保存成功！'});
					var wopen = window.parent;
					wopen.schList(1, 25);
					Suredy.Modal.closeModal();
				}
			},
			error : function(a, b, c) {
				$.notify({title:'提示：',message:'服务器错误！'+b});
			}
		});
	});
});	
</script>	
