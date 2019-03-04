var upLoaddataSave;
require(['suredyModal','suredyTreeSelector','suredyDatetimepicker', 'validation'],function(Modal,TreeSelector) {
	$('#form').bootstrapValidator({
		message : '输入错误',
		feedbackIcons : {
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			equipModel : {
				validators : {
					notEmpty : {
						message : '设备型号不为空'
					}
				}
			},
			assetId : {
				validators : {
					notEmpty : {
						message : '资产ID不为空'
					},
					callback : {
						message : '资产ID已经存在',
						callback : function(value,validator) {
							var flg;
							if (value.trim() == "") {
								flg = true
							} else {
								var url = Suredy.ctxp+'/EquipAssetCtrl/judgeAssetId.do';
								var equipId = $('#id').val();
								$.ajax({
									type : "post",
									url : url,
									data : {
										"assetId" : value,
										"equipId" : equipId
									},
									async : false,
									success : function(data) {
										flg = data.data;
									}
								});
							}
							return flg;
						}
					}
				}
			},
			status : {
				validators : {
					notEmpty : {
						message : '状态不为空'
					}
				}
			},
			typeName : {
				validators : {
					notEmpty : {
						message : '设备类型不为空'
					}
				}
			},
			rfid : {
				validators : {
					notEmpty : {
						message : 'rfid号不为空'
					}
				}
			},
			buyDate : {
				validators : {
					notEmpty : {
						message : '购买日期不能为空！'
					}
				}
			},
			userPlace : {
				validators : {
					notEmpty : {
						message : '使用地点不能为空！'
					}
				}
			},
			responsible : {
				validators : {
					notEmpty : {
						message : '责任人不能为空！'
					}
				}
			},
			userName : {
				validators : {
					notEmpty : {
						message : '使用人不能为空！'
					}
				}
			},
			receiveDate : {
				validators : {
					notEmpty : {
						message : '领用日期不能为空！'
					}
				}
			}
		}
	});
	
	$("#buyDate").on('hidden.bs.suredy.datetimepicker', function() {
		$('#form').data('bootstrapValidator').updateStatus('buyDate', 'NOT_VALIDATED', null).validateField('buyDate');
	})
	
	$("#receiveDate").on('hidden.bs.suredy.datetimepicker', function() {
		$('#form').data('bootstrapValidator').updateStatus('receiveDate', 'NOT_VALIDATED', null).validateField('receiveDate');
	})
	//通过设备判断数据是否有效
	var validataStatru=function(){
		var status = $('#status').val();
		var responsibleId = $('#responsibleId')
				.val();
		var userID = $('#userID').val();
		var receiveDate= $('#receiveDate').val();
		var userPlace= $('#userPlace').val().trim();
		var buyDate= $('#buyDate').val();	
		if(status == 1 || status == 0){
			if(responsibleId!=''||userID!=''||receiveDate!=''||userPlace!=''){
			//	var msg="待分配状态或者已报废状态\n\n\提示：\n\使用人、责任人、领用时间和使用地点都必须为空\n\是否改为空,并且入库！";
				//if (window.confirm(msg)) {
					 $('#responsibleId').val(""); 
					 $('#responsible').val("");
					 $('#userName').val("");
					 $('#userID').val("");
					 $('#receiveDate').val("");
					 $('#userPlace').val("");
					 $('#responsiblePhone').val("");
					 $('#userPhone').val("");
					 $('#userUnitId').val("");
					 $('#userUnit').val("");
					return true;
					//}
				//return false;
			}
		}
//		}else if(status == 4 && userPlace == ''){
//			
//			alert("使用地点不能为空！")
//			return false;
//		}else if (status == 4 && responsibleId == '') {
//			alert("责任人不能为空！")
//			return false;
//		}else if (status == 4 && userID == '') {
//			alert("使用人不能为空！")
//			return false;
//		}else if(status == 4 && receiveDate == ''){
//			alert("领用日期不能为空！")
//			return false;
//		}else if(status == 5 && responsibleId == '') {
//			 	
//			alert("责任人不能为空！")
//			return false;
//		}
	
		return true;
	};
	
	upLoaddataSave=function(identifying) {
		
		$('#form').data('bootstrapValidator').validate();
		var vaild = $('#form').data('bootstrapValidator').isValid();
		if (!vaild) {
			return false;
		}
		
		var status = $('#status').val();
		
		var buyDate= $('#buyDate').val();	
		if(buyDate==''){
			alert("购买日期不能为空！");
			return false;
		}
		
		var receiveDate= $('#receiveDate').val();	
		if(receiveDate==''&&status==4){
			alert("领用日期不能为空！");
			return false;
		}
		
		if(!validataStatru()) return false;	
		if(status == 0){
			var msg="已报废的设备将不能再操作\n\n\提示：\n\请谨慎操作！";
			if (!window.confirm(msg)) return false;
		}
		$.ajax({
			dataType : 'json',
			type : 'Post',
			data : $('#form').serialize(),
			url : Suredy.ctxp+'/EquipAssetCtrl/saveAndApdatedata.do?identifying='+identifying,
			success : function(data) {
			var ms = data.data;
			console.log($("#thisnodeid").val());
				if (ms&&identifying=='1') {
					alert("数据完成入库！");
					var url =Suredy.ctxp+'/ExcelUpLoadCtrl/getEquipStandbyData.do?typeId='+ $("#thisnodeid").val();
					$.ajax({
						type : 'Post',
						url : url,
						datatype : 'json',
						success : function() {
							var wopen = window.opener;
							wopen.Suredy.loadContent(url+ " #excelList",{
								callback : function() {
									wopen.readdata();
									window.close();
								},
								container : '#excelList'
							});
						},
						error : function() {

						}
					});

				} else if(ms&&identifying=='0'){
					alert("数据修改成功！");
				}else{
					alert("数据修改失败！");
				}
			},
			error : function() {
			}
		});

	};
	$("#updatedata").click(function() {
		
		
		$('#form').data('bootstrapValidator').validate();
		var vaild = $('#form').data('bootstrapValidator').isValid();
		if (!vaild) {
			return false;
		}
		
		var status = $('#status').val();
		var buyDate= $('#buyDate').val();	
		if(buyDate==''){
			alert("购买日期不能为空！");
			return false;
		}
		
		var receiveDate= $('#receiveDate').val();	
		if(receiveDate==''&&status==4){
			alert("领用日期不能为空！");
			return false;
		}
		
		if(!validataStatru()) return false;	
		if(status == 0){
			var msg="已报废的设备将不能再操作\n\n\提示：\n\请谨慎操作！";
			if (!window.confirm(msg)) return false;
		}
		$.ajax({
			dataType : 'json',
			type : 'Post',
			data : $('#form').serialize(),
			url : Suredy.ctxp+'/EquipAssetCtrl/saveAndApdatedata.do',
			success : function(data) {
				var ms = data.data;
				if (ms) {
					alert("数据保存成功！");
					var url = Suredy.ctxp+'/EquipAssetCtrl/getEquipData.do?typeId='
							+ $("#thisnodeid").val();
					$.ajax({
						type : 'Post',
						url : url,
						datatype : 'json',
						success : function() {
						var wopen = window.opener;
						wopen.Suredy.loadContent(url+ " #equipmentList",{
							callback : function() {
								wopen.readdata();
								window.close();
							},
							container : '#equipmentList'
						});

						},
						error : function() {

						}
					});

					} else {
						alert("数据修改失败");
					}
				},
			error : function() {
				
			}
		});
	});
	
	if($("#typeName").val()=="undefined"||$("#typeName").val()==''){
		$.ajax({
			url : Suredy.ctxp + '/type/tree',
			type : 'get',
			dataType : 'json',
			success : function(data) {
				TreeSelector('input#typeName',data, {
					autoCollapse : false,
					style : 'department',
					showTitle : true,
					canFolderActive : false,
					size : 'lg'
				});	
			}
		});
		$('#typeName').one(TreeSelector.selectorShown, function() {
			var id=$('#typeId').val();
			if (id!= '') {
				 TreeSelector.nodes($('input#typeName')).each(function(index) {
					var data = TreeSelector.data(this);										
					if (id== data) {
						$(this).addClass('active');
					}
				});
			}
		});			
		$('#typeName').on(TreeSelector.nodeClick, function(event, $node) {
			if (!TreeSelector.isActive($node)) {
				$('#typeId').val("");
				$('#typeName').val("");
				$('#form').data('bootstrapValidator').updateStatus('typeName','NOT_VALIDATED',null).validateField('typeName');
				return false;
			}
		
			var nodeData = TreeSelector.data($node);
			var name = TreeSelector.data($node, 'text');
			$('#typeId').val(nodeData);
			$('#typeName').val(name);	
			var id = $("#id").val();
			if (nodeData!=""&&nodeData!=undefined) {							
				$('#form').data('bootstrapValidator').updateStatus('typeName','NOT_VALIDATED',null).validateField('typeName');
				
				url =Suredy.ctxp + '/ExcelUpLoadCtrl/getSurePage.do';
				if ($("#tempTypeID").val() == $('#typeId').val()&& id != '') {
					$.post(url, {'equipid' : id}, function(html, textStatus,jqXHR) {
						var $html = $(html);
						$("#propertyid").html($("#propertyid",$html).html());
					}, 'html');
				} else {
					$.post(url, {'nodeid' : nodeData}, function(html, textStatus,jqXHR) {
						var $html = $(html);
						$("#propertyid").html($("#propertyid",$html).html());
					}, 'html');
				}
			} else {
				$('#typeId').val('');
			}
			return true;
		});
	}
	
	
//	$.ajax({
//		url : Suredy.ctxp + '/config/ou/tree/true/true/true',
//		type : 'get',
//		dataType : 'json',
//		success : function(data) {
//			TreeSelector('input#responsible',data, {
//				autoCollapse : false,
//				style : 'department',
//				showTitle : true,
//				canFolderActive : false,
//				size : 'lg'
//			});
//			TreeSelector('input#userName',data, {
//				autoCollapse : false,
//				style : 'department',
//				showTitle : true,
//				canFolderActive : false,
//				size : 'lg'
//			});
//		}
//	});
//		
//	$('input#responsible').one(TreeSelector.selectorShown, function() {
//		var id=$('input[name="responsibleId"]').val();
//		if (id!= '') {
//			 TreeSelector.nodes($('input#responsible')).each(function(index) {
//				var data = TreeSelector.data(this,'id');
//				if (id== data) {
//					$(this).addClass('active');
//				}
//			});
//		}
//	});			
//	$('input#responsible').on(TreeSelector.nodeClick, function() {
//		if (!TreeSelector.isActive(this)) {
//			$('input[name="responsibleId"]').val('');
//			$('input#responsible').val('');
//			$('#responsiblePhone').val('');
//			return false;
//		}
//			
//		var userid = TreeSelector.data(this, 'id');
//		var name = TreeSelector.data(this, 'fullName');
//		var phone = TreeSelector.data(this, 'userphone');
//		$('input[name="responsibleId"]').val(userid);
//		$('input#responsible').val(name);
//		$('#responsiblePhone').val(phone);
//		return true;
//
//	});
//	
//	$('input#userName').one(TreeSelector.selectorShown, function() {
//		var id=$('input[name="userID"]').val();
//		if (id!= '') {
//			TreeSelector.nodes($('input#userName')).each(function(index) {
//				var data = TreeSelector.data(this,'id');
//				if (id== data) {
//					$(this).addClass('active');
//				}
//			});
//		}
//	});	
//	$('input#userName').on(TreeSelector.nodeClick, function() {
//		
//		if (!TreeSelector.isActive(this)) {
//			$('input[name="userID"]').val('');
//			$('input#userName').val('');
//			$('#userPhone').val('');
//			$('#userUnitId').val('');
//			$('#userUnit').val('');
//			
//			return false;
//		}	
//		
//		var userid = TreeSelector.data(this, 'id');
//		var name = TreeSelector.data(this, 'fullName');
//		var phone = TreeSelector.data(this, 'userphone');
//		var unitnames=name.split('/');
//		var unitname="";
//		for(var i=1;i<unitnames.length;i++){
//			if(i==unitnames.length-1){
//				unitname+=unitnames[i];
//			}else{
//				unitname+=unitnames[i]+"/";
//			}
//		}
//		$('input[name="userID"]').val(userid);
//		$('input#userName').val(name);
//		$('#userPhone').val(phone);
//		$('#userUnitId').val(TreeSelector.data(this, 'unitUniqueCode'));
//		$('#userUnit').val(unitname);
//		return true;
//
//	});
//	
	//停止使用此项，用下面的方法替代
	
	$("#responsibleSelector").click(function(){
		Modal.showModal({
			size : 'lg',
			icon : 'icon-plus',
			title : '选择人员',
			showFoot : false,
			uri :Suredy.ctxp + '/EquipAssetCtrl/getSelectUser'
		})
	});
	
	
	var setUsers = function(){
		var indexUsers = [];
		var code = $('#responsible').val();
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
		if ($('#responsible').attr('aria-describedby')) {
			var popId  = $('#responsible').attr('aria-describedby');
			var popover = $($('#' + popId + ' > .popover-content')[0]);	
			popover.html('');
			popover.html(setUsers());
			
			$('.users-Selector').click(function(){
				return false;
			});
			
			var isPublic = $('#isPublic').val();
	 		$('.selectedUser').click(function() {
				$('#responsibleId').val($(this).data('id'));
				$('#userUnitId').val($(this).data('unitId'));
				$('#userUnit').val($(this).data('unit'));
				$('#responsiblePhone').val($(this).data('phone'));
				$('#responsible').val($(this).html());
				if(isPublic!='2') {
					$('#userID').val($(this).data('id'));
					$('#userName').val($(this).html());
					$('#userPhone').val($(this).data('phone'));				
				}
				
				$('#responsible').popover('destroy');
			});
		}				
	};
	
	var arrowPress = function(keyCode){
		if ($('#responsible').attr('aria-describedby')) {
			var popId  = $('#responsible').attr('aria-describedby');
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
		if ($('#responsible').attr('aria-describedby')) {
			var popId  = $('#responsible').attr('aria-describedby');
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
			var isPublic = $('#isPublic').val();
			$('#responsibleId').val($(user).data('id'));
			$('#userUnitId').val($(user).data('unitId'));
			$('#userUnit').val($(user).data('unit'));
			$('#responsiblePhone').val($(user).data('phone'));
			$('#responsible').val($(user).html());
			if(isPublic!='2') {
				$('#userID').val($(user).data('id'));
				$('#userName').val($(user).html());
				$('#userPhone').val($(user).data('phone'));				
			}			
			$('#responsible').popover('destroy');
		}	
	};
	
	
	$('#responsible').on({
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
	    	var width = $('#responsible').width() + 25;
			$('#responsible').popover({
				animation : true,
				html : true,
				placement:'top',
				content:setUsers(),
				title:'输入用户拼音首字检索,点选结果',
				trigger : 'manual',
				template:'<div class="popover users-Selector" style="width:'+ width +'px;" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'
			});	
			$('#responsible').popover('show');
			$('.users-Selector').click(function(){
				return false;
			});
			
			var isPublic = $('#isPublic').val();
	 		$('.selectedUser').click(function() {				
				$('#responsibleId').val($(this).data('id'));
				$('#userUnitId').val($(this).data('unitId'));
				$('#userUnit').val($(this).data('unit'));
				$('#responsiblePhone').val($(this).data('phone'));
				$('#responsible').val($(this).html());
				if(isPublic!='2') {
					$('#userID').val($(this).data('id'));
					$('#userName').val($(this).html());
					$('#userPhone').val($(this).data('phone'));				
				}			
				$('#responsible').popover('destroy');
			});
	 		
			$('body').one('click', function(e) {
	 			$('#responsible').popover('destroy');	
	 		});		
			return false;	
	    }

	  });		
	
	//使用范围的值改变而改变使用人
	$('[name="isPublic"]').on('change.is.public', function() {
		var isPublic=$('#isPublic').val();
		var responsibleId=$('#responsibleId');
		var responsible=$('#responsible');
		var responsiblePhone=$('#responsiblePhone');
		var userID=$('#userID');
		var userName=$('#userName');
		var userPhone=$('#userPhone');
		if(isPublic=='2'){
			userName.attr("readonly",false);
			userPhone.attr("readonly",false);
			userID.val("")
			userName.val("");
			userPhone.val("");
		}else{
			userName.attr("readonly",true);
			userPhone.attr("readonly",true);
			userID.val(responsibleId.val());
			userName.val(responsible.val());
			userPhone.val(responsiblePhone.val());
		}
	});
});