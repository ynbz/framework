var saveForm;
require([ 'suredyModal', 'validation', 'suredyTreeSelector', 'jqueryForm','suredyDatetimepicker' ],function(Model, validators, TreeSelector) {
	Suredy.Datetimepicker.AutoDelegate(true);
	$('form#formDis').bootstrapValidator({
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
			asUnit : {
				validators : {
					notEmpty : {
						message : '请填写主送单位！'
					}
				}
			},
			ctUnit : {
				validators : {
					notEmpty : {
						message : '请填写抄送单位！'
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
			disUnit : {
				validators : {
					notEmpty : {
						message : '请填写发文单位！'
					}
				}
			},
			issuePerson : {
				validators : {
					notEmpty : {
						message : '请填写签发人！'
					}
				}
			},
			issueDate : {
				validators : {
					notEmpty : {
						message : '请填写签发时间！'
					}
				}
			},
			drafter : {
				validators : {
					notEmpty : {
						message : '请填写拟稿人！'
					}
				}
			},
			draftDate : {
				validators : {
					notEmpty : {
						message : '请填写拟稿时间！'
					}
				}
			},
			phone : {
				validators : {
					notEmpty : {
						message : '请填写联系电话！'
					},
					callback:{
	                   	message: '请输入有效的手机号码！',
	                   	callback: function(value, validator,$field) {		                    		
	                   		var falg = true;
	                   		var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
	                   		if(!myreg.test(value)) {
            		            falg = false;
            		        }               			                    	                    		
	                   		return falg;
	                   }
                   }
				}
			},
			disType : {
				validators : {
					notEmpty : {
						message : '请填写发文类型！'
					}
				}
			},
			disWrodSize : {
				validators : {
					notEmpty : {
						message : '请填写发文字号！'
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
			printNumber : {
				validators : {
					notEmpty : {
						message : '请填写印发数量！'
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
			},
			subjectTerm : {
				validators : {
					notEmpty : {
						message : '请填写主题词！'
					}
				}
			}
		}
	});
	
	$("#issueDate").on('hidden.bs.suredy.datetimepicker', function() {
		$('#formDis').data('bootstrapValidator').updateStatus('issueDate', 'NOT_VALIDATED', null).validateField('issueDate');
	})
	
	$("#draftDate").on('hidden.bs.suredy.datetimepicker', function() {
		$('#formDis').data('bootstrapValidator').updateStatus('draftDate', 'NOT_VALIDATED', null).validateField('draftDate');
	})
	
	saveForm = function() {
		var d1=$('form#formDis').data('bootstrapValidator').validate();
		var vaild=$('form#formDis').data('bootstrapValidator').isValid();			
		if(!vaild){
			return false;
		}
		
		$('form#formDis').ajaxSubmit({
			url : Suredy.ctxp+ '/dism/saveOreditDisM',
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
