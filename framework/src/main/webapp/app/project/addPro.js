var saveForm;
var schList;
require(['suredyList','suredyModal', 'validation', 'jqueryForm' ],function(List,Modal, validators) {
	$('form#formPro').bootstrapValidator({
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
						message : '请填写计划名称！'
					}
				}
			}
		}
	});
	
	//新建生产计划
	$('#addsch').click(function(){
		Modal.showModal({
			size : 'lg',
			icon : 'icon-plus',
			title : '新增计划',
			showFoot : false,
			uri : Suredy.ctxp+ '/pro-schedule/sch-info?proid='+$('#id').val()
		});
	});
	
	//修改生产计划
	$('#updatesch').click(function(){
		var checked = List.checked($('.sch-list'));
		console.log(checked.length);
		if (checked.length == 0 ) {
			alert('请选择需要修改的生产计划!');
			return;
		} else if (checked.length > 1) {
			alert('只能修改一个生产计划!');
			return;
		} else {
			var id = checked.eq(0).data('id');
			var uri = Suredy.ctxp+ '/pro-schedule/sch-info?proid='+$('#id').val()+'&schid='+id;
			Modal.showModal({
				size : 'lg',
				icon : 'icon-edit',
				title : '修改生产计划',
				showFoot : false,
				uri : uri
			});
		}
	});	
	
	// 删除生产计划
	$('#deletesch').click(function(){
		var checked = List.checked($('.sch-list'));
		
		if (checked.length == 0 ) {
			alert('请选择需要删除的生产计划!');
			return;
		} else {
			//TODO: 
			var ids='';
			for(var i = 0;i<checked.length;i++){
				ids =ids+checked.eq(i).data('id')+",";
			}
			ids = ids.substring(0, ids.length-1);//去掉最后的逗号
			var uri = Suredy.ctxp+ '/pro-schedule/sch-info?ids='+ids
			var msg = '是否确认删除生产计划？提示：请谨慎操作！';
	
			if (!window.confirm(msg)) {
				return;
			}
			$.ajax({
				url : uri,
				type : 'POST',
				success : function(msg) {
					if (!msg) {
						alert('Unknown exception!');
					} else if (!msg.success) {
						alert("删除生产计划失败！\n\n" + msg.msg);
					} else {
						$.notify({title:'提示：',message:'删除生产计划成功！'});
						schList(1, 25);
					}
				},
				error : function(a, b, c) {
					alert('Server error! ' + b);
				}
			});

		} //end else
	});
	
	
	saveForm = function() {
		var d1=$('form#formPro').data('bootstrapValidator').validate();
		var vaild=$('form#formPro').data('bootstrapValidator').isValid();			
		if(!vaild){
			return false;
		}
		
		$('form#formPro').ajaxSubmit({
			url : Suredy.ctxp+ '/project/pro-save',
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
	
	// 更新计划表
	schList = function(page, pageSize) {
		$.ajax({
			url : Suredy.ctxp+'/pro-schedule/sch-list/'+$('#id').val(),
			data : {
				page : page,
				pageSize : pageSize
			},
			type : 'get',
			dataType : 'html',
			success : function(html, textStatus, jqXHR) {
				if (html) {
					$('#schList').html(html);

					List('#schList table.sch-list', {
						header : false,
						paginate : schList,
						checkbox : true,
						search : false
					});
					
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
			}
		});
	};
	schList(1, 25);
});

//申请表单删除
var delproject=function(){
	var id=$('form#formPro #id').val();
	
	var msg = '是否确认删除申请单！？\n\n\提示：删除申请单将会删除申请设备！\n\请谨慎操作！';
	if (!window.confirm(msg)) {
		return;
	}
	$.ajax({
		url : Suredy.ctxp+'/project/pro-delete?id='+id,
		type : 'POST',
		async:false,
		success : function(msg) {
			if (!msg) {
				alert('Unknown exception!');
			} else if (!msg.success) {
				alert("删除申请申请单失败！\n\n" + msg.msg);
			} else {
				alert("删除申请申请单成功！");
				window.open("","_self").close(); 
			}
		},
		error : function(a, b, c) {
			alert('Server error! ' + b);
		}
	});
};

//发布生产计划
var createIssueDate=function(){
	var id=$('form#formPro #id').val();
	
	var msg = '是否确认发布生产计划！';
	if (!window.confirm(msg)) {
		return;
	}
	$.ajax({
		url : Suredy.ctxp+'/project/createIssueDate?id='+id,
		type : 'POST',
		async:false,
		success : function(msg) {
			if (!msg) {
				alert('Unknown exception!');
			} else if (!msg.success) {
				alert("发布生产计划失败！\n\n" + msg.msg);
			} else {
				alert("发布生产计划成功！");
				//Suredy.loadContent(Suredy.ctxp+'/project/view/'+id);
				location.reload();
			}
		},
		error : function(a, b, c) {
			alert('Server error! ' + b);
		}
	});
};
