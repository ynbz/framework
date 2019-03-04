//检查是否有创建文件
var confirmIsFile = function() {
	var falg=true;
	$.ajax({
		url : Suredy.ctxp+'/dococx/confirmIsFile?id='+$('#id').val(),
		type : 'POST',
		async:false,
		success : function(msg) {
			if (!msg) {
				alert('Unknown exception!');
			} else if (!msg.success) {
				alert(msg.msg);
				falg = false;
			}
		},
		error : function(a, b, c) {
			alert('Server error! ' + b);
		}
	});
	
	 return falg;
}

//创建word
var createWord = function() {
	//判断是否调用模板
	var temTypeId='';
	$.ajax({
		url : Suredy.ctxp+'/dococx/isForm?fileTypeCode='+$('#fileType').val(),
		type : 'POST',
		async:false,
		success : function(msg) {
			if (!msg) {
				alert('Unknown exception!');
			}else{
				temTypeId = msg.msg
			}
		},
		error : function(a, b, c) {
			alert('Server error! ' + b);
		}
	});
	
	//判断是否创建了文件
	var falg=true;
	$.ajax({
		url : Suredy.ctxp+'/dococx/confirmIsFile?id='+$('#id').val(),
		type : 'POST',
		async:false,
		success : function(msg) {
			if (!msg) {
				alert('Unknown exception!');
			} else if (msg.success) {
				falg = false;
			}
		},
		error : function(a, b, c) {
			alert('Server error! ' + b);
		}
	});
	if(temTypeId!=undefined&&falg){
		require([ 'suredyModal' ],function(Modal) {
			Modal.showModal({
				size : 'lg',
				icon : 'icon-plus',
				title : '创建系统字典',
				showFoot : false,
				uri : Suredy.ctxp+'/dococx/selecttem?temTypeId='+temTypeId
			});
		})
	}else{
		location.href = Suredy.ctxp+'/dococx/openwindow?url=create?id='+$('#id').val();
	}
}

//修改Word
var updateWord = function() {
	location.href = Suredy.ctxp+'/dococx/openwindow?url=update?id='+$('#id').val();
}

//只读word
var readOnlyWord = function() {
	location.href = Suredy.ctxp+'/dococx/openwindow?url=readOnly?id='+$('#id').val();
}

// 领导批阅word
var giveRemarksWord = function() {
	location.href = Suredy.ctxp+'/dococx/openwindow?url=giveRemarks?id='+$('#id').val();
}

//套红word
var taohongWord = function() {
	location.href = Suredy.ctxp+'/dococx/openwindow?url=taohong?id='+$('#id').val();
}

//电子盖章word
var insertSealWord = function() {
	location.href = Suredy.ctxp+'/dococx/openwindow?url=insertSeal?id='+$('#id').val();
}


//修改有痕迹word
var revisionsListWord = function() {
	location.href = Suredy.ctxp+'/dococx/openwindow?url=revisionsList?id='+$('#id').val();
}