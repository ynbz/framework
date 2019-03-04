var saveForm;
require([ 'suredyModal', 'validation', 'suredyTreeSelector', 'jqueryForm','suredyDatetimepicker' ],function(Model, validators, TreeSelector) {
	Suredy.Datetimepicker.AutoDelegate(true);
	$('form#formIncDis').bootstrapValidator({
        message: '输入错误',
         feedbackIcons: {
//            valid: 'glyphicon glyphicon-ok',
//            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
		fields : {
			title : {
				validators : {
					notEmpty : {
						message : '请填写发文题名！'
					}
				}
			},
			idUnit : {
				validators : {
					notEmpty : {
						message : '请填写收文单位！'
					}
				}
			},
			rootInUnit : {
				validators : {
					notEmpty : {
						message : '请填写来文单位！'
					}
				}
			},
			originalNumber : {
				validators : {
					notEmpty : {
						message : '请填写原文文号！'
					}
				}
			},
			incDisCode : {
				validators : {
					notEmpty : {
						message : '请填写收文编号！'
					}
				}
			},
			originalDate : {
				validators : {
					notEmpty : {
						message : '请填写原文日期！'
					}
				}
			},
			incDisDate : {
				validators : {
					notEmpty : {
						message : '请填写收文日期！'
					}
				}
			},
			limitDate : {
				validators : {
					notEmpty : {
						message : '请填写限办日期！'
					}
				}
			},
			countCode : {
				validators : {
					notEmpty : {
						message : '请填写总序号！'
					}
				}
			},
			sDepartment : {
				validators : {
					notEmpty : {
						message : '请填写主办部门！'
					}
				}
			},
			coOrganizer : {
				validators : {
					notEmpty : {
						message : '请填写协办部门！'
					}
				}
			},
			readUnit : {
				validators : {
					notEmpty : {
						message : '请填写阅读部门！'
					}
				}
			},
			readDoUnit : {
				validators : {
					notEmpty : {
						message : '请填写阅办部门！'
					}
				}
			},
			fileSecCla : {
				validators : {
					notEmpty : {
						message : '请选择文件密级！'
					}
				}
			},
			secrecyDateLimit : {
				validators : {
					notEmpty : {
						message : '请填写保密期限！'
					}
				}
			},
			degreeOfE : {
				validators : {
					notEmpty : {
						message : '请选择紧急程度！'
					}
				}
			},
			pageNumber : {
				validators : {
					notEmpty : {
						message : '请填写页数！'
					},callback:{
	                   	message: '填写错误，填写内容不是数字类型并且不能小于1。',
	                   	callback: function(value, validator,$field) {		                    		
	                   		var falg = true;
	                   		var numer=Number(value);
	                   		var isNum =  /^[0-9]*[1-9][0-9]*$/;
            		        if(!isNum.test(numer)||numer<1){
            		            falg = false;
            		        }
	                   		return falg;
	                   	}
					}
	                   
				}
			}
		}
	});
	
	$("#originalDate").on('hidden.bs.suredy.datetimepicker', function() {
		$('#formIncDis').data('bootstrapValidator').updateStatus('originalDate', 'NOT_VALIDATED', null).validateField('originalDate');
	})
	
	$("#incDisDate").on('hidden.bs.suredy.datetimepicker', function() {
		$('#formIncDis').data('bootstrapValidator').updateStatus('incDisDate', 'NOT_VALIDATED', null).validateField('incDisDate');
	})
	
	$("#limitDate").on('hidden.bs.suredy.datetimepicker', function() {
		$('#formIncDis').data('bootstrapValidator').updateStatus('limitDate', 'NOT_VALIDATED', null).validateField('limitDate');
	})
	
	saveForm = function() {
		var d1=$('form#formIncDis').data('bootstrapValidator').validate();
		var vaild=$('form#formIncDis').data('bootstrapValidator').isValid();			
		if(!vaild){
			return false;
		}
		
		$('form#formIncDis').ajaxSubmit({
			url : Suredy.ctxp+ '/incdism/saveOreditIncDisM',
			dataType : 'json',
			async:false,
			success : function(success) {
				if (!success) {
					alert('保存失败！');
				} else if (!success.success) {
					alert('保存失败！\n\n' + success.msg);
				} else {
					alert('保存成功！');
		
				}
			},
			error : function(a, b, c) {
				alert('服务器错误! ' + b);
			}
		});
	}
});