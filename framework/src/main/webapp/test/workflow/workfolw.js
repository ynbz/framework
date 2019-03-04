
var saveForm;
require([ 'suredyModal','validation','suredyDatetimepicker'],function(Modal,validators) {
	$('#form').bootstrapValidator({
        message: '输入错误',
         feedbackIcons: {
//	            valid: 'glyphicon glyphicon-ok',
//	            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
		fields : {
			executeName : {
				validators : {
					notEmpty : {
						message : '请填写执行人姓名！'
					}
				}
			},
			startTime : {
				validators : {
					notEmpty : {
						message : '请选择开始时间！'
					}
				}
			},
			endTime : {
				validators : {
					notEmpty : {
						message : '请选择完成时间！'
					}
				}
			},
			content : {
				validators : {
					notEmpty : {
						message : '请填写工作内容！'
					}
				}
			}
		}
	});
	
	$("#startTime").mouseout(function() {
		var value = $('#startTime').val();
		if(value.trim()!=""){
			$('#form').data('bootstrapValidator')
	        .updateStatus('startTime', 'NOT_VALIDATED', null)          
	        .validateField('startTime');
		}
	});
	$("#endTime").mouseout(function() {
		var value = $('#endTime').val();
		if(value.trim()!=""){
			$('#form').data('bootstrapValidator')
	        .updateStatus('endTime', 'NOT_VALIDATED', null)          
	        .validateField('endTime');
		}
	});
	
	saveForm = function() {
		var d1=$('#form').data('bootstrapValidator').validate();
		var vaild=$('#form').data('bootstrapValidator').isValid();			
		if(!vaild){
			return false;
		}
		var id = $('#id').val();
		$.ajax({
			url :Suredy.ctxp+'/workflow/save-edit.do',
			dataType : 'json',
			type : 'POST',
			async:false,
			data : $('#form').serialize(),
			success : function(success) {
				if (!success) {
					alert('保存失败！');
				} else if (!success.success) {
					alert('保存失败！\n\n' + success.msg);
				} else {
					alert('保存成功！');
					Suredy.loadContent({
						url :Suredy.ctxp + '/workflow/save/' + id 
					});
				}
			},
			error : function(a, b, c) {
				alert('服务器错误! ' + b);
			}
		});
	}
	
	//停止使用此项，用下面的方法替代
	$("#selectorExecute").click(function(){
		
		Modal.showModal({
			size : 'lg',
			icon : 'icon-plus',
			title : '选择人员',
			showFoot : false,
			uri :Suredy.ctxp + '/workflow/getSelectUser'
		})
	});
	
	
	var setUsers = function(){
		var indexUsers = [];
		var code = $('#executeName').val();
		if (code == ''){
			indexUsers = [];
		} else {
			for(var i=0;i<userList.length;i++){
				if( userList[i].pinyin.startsWith(code)){
					indexUsers.push(userList[i]);
				}
			}
		}
		var content = '';
		for(var i=0;i<indexUsers.length;i++) {
			var user= indexUsers[i];
			content+='<div class="user-list"><a data-id="'+user.id+'" data-phone="'+user.phone+'" data-unit="'+user.unit+'" data-unitId="'+user.unitId+'" class="selectedUser text-nowrap" href="#">'+user.name+'</a></div>';
		}				
		

		return content;				
	};
	
	var filteUsers = function(){
		if ($('#executeName').attr('aria-describedby')) {
			var popId  = $('#executeName').attr('aria-describedby');
			var popover = $($('#' + popId + ' > .popover-content')[0]);	
			popover.html('');
			popover.html(setUsers());
			
			$('.users-Selector').click(function(){
				return false;
			});
		
	 		$('.selectedUser').click(function() {
				$('#executeNameId').val($(this).data('id'));
				$('#executeName').val($(this).html());
				$('#executeName').popover('destroy');
			});
		}				
	};
	
	var arrowPress = function(keyCode){
		if ($('#executeName').attr('aria-describedby')) {
			var popId  = $('#executeName').attr('aria-describedby');
			var popover = $($('#' + popId + ' > .popover-content')[0]);	
			var list = popover.children();
			var line = 0;
			for (var i=0; i<list.length;i++) {
				if ($(list[i]).hasClass('user-highlight')){
					if (keyCode == 38) {
						line = i - 1; //up
					} else if (keyCode == 40){
						line = i + 1; //down
					}
					$(list[i]).removeClass('user-highlight');
					break;
				} 
			}
			if (line < 0) {
				line = 0;
			}

			if (line == list.length) {
				line = list.length - 1;
			}
			$(list[line]).addClass('user-highlight');
		}				
	};
	
	var enterPress = function(){
		if ($('#executeName').attr('aria-describedby')) {
			var popId  = $('#executeName').attr('aria-describedby');
			var popover = $($('#' + popId + ' > .popover-content')[0]);	
			var list = popover.children();
			var line = 0;
			for (var i=0; i<list.length;i++) {
				if ($(list[i]).hasClass('user-highlight')){
					line = i;
					break;
				} 
			}
			var user = $(list[line]).children()[0];			
			$('#executeNameId').val($(user).data('id'));
			$('#executeName').val($(user).html());
			$('#executeName').popover('destroy');
		}	
	};
	
	
	$('#executeName').on({
	    focus:function () {
	          this.style.imeMode = 'disabled';
	    },
	    keydown:function(event){
	    	var eventObj = event || e;
	        var keyCode = eventObj.keyCode || eventObj.which;
	        if ((keyCode >= 65 && keyCode <= 90) || 
	        	(keyCode >= 112 && keyCode <= 122) ||
	        	keyCode === 8 || keyCode === 13 || keyCode === 38 || keyCode === 40 || keyCode === 46) {
	        	return true;
	        } else {
	            return false;
	        }
	    },
	    keyup:function(event) {
	    	var eventObj = event || e;
	        var keyCode = eventObj.keyCode || eventObj.which;
	        switch (keyCode) {
	        // 1. 回车键
	        case 13:
	        	enterPress();
	            break;
	        // 2. 删除键，在keyup后再计算字数
	        case 8:   // backspace
	        case 46:  // delete
	        	filteUsers();
	            break;
	        // 3. 上下键    
	        case 38: //up
	        case 40: //down
	        	arrowPress(keyCode);
	        	break;
	        // 4. 默认	
	        default:
	        	filteUsers();
	            break;
	        }			    	
	    },
	    click:function(){
	    	var width = $('#executeName').width() + 25;
			$('#executeName').popover({
				animation : true,
				html : true,
				placement:'top',
				content:setUsers(),
				title:'输入用户拼音首字检索,点选结果',
				trigger : 'manual',
				template:'<div class="popover users-Selector" style="width:'+ width +'px;" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
			});	
			$('#executeName').popover('show');
			$('.users-Selector').click(function(){
				return false;
			});
			
			var isPublic = $('#isPublic').val();
	 		$('.selectedUser').click(function() {				
				$('#executeNameId').val($(this).data('id'));
				$('#executeName').val($(this).html());	
				$('#executeName').popover('destroy');
			});
	 		
			$('body').one('click', function(e) {
	 			$('#executeName').popover('destroy');	
	 		});		
			return false;	
	    }

	  });
});

var send = function() {
	var d1=$('#form').data('bootstrapValidator').validate();
	var vaild=$('#form').data('bootstrapValidator').isValid();			
	if(!vaild){
		return false;
	}
	var falg = true;
	$.ajax({
		url :Suredy.ctxp+'/workflow/save-edit.do',
		dataType : 'json',
		type : 'POST',
		async:false,
		data : $('#form').serialize(),
		success : function(success) {
			if (!success) {
				falg = false;
			} else if (!success.success) {
				falg = false;
			} 
		},
		error : function(a, b, c) {
			falg = false;
		}
	});
	if(!falg){
		alert('表单保存失败，不能发送！！');
	}
	return falg;
}


