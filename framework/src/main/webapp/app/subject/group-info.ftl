<form method="post" name="form1" id="form1">
	<input type="hidden" value="${group.id}" name="id" id="id"/>
	<input type="hidden" name="memberId" id="memberId"/>
	<input type="hidden" name="memberName" id="memberName"/>
	<input type="hidden" name="gruopLeaderId" id="gruopLeaderId"/>
	<input type="hidden" name="gruopLeaderName" id="gruopLeaderName"/>
	<!-- Tab panes -->
	<div class="tab-content" style="padding-top: 10px;">
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">班组名</div>
						<input type="text" class="form-control"  id="name" name="name"  value="${group.name}"/>	
					</div>
				</div>
			</div>
		</div>
			
		<div class="row">
			<div class="col-md-4 col-xs-4">
				<div style="overflow:auto;">
					<div class="unit-user-tree" style="margin-top: 1px; height: 500px;"></div>
				</div>
			</div>
			<div class="col-md-8 col-xs-8" style="overflow:auto">
					<table  class="member-list">
						<tr class="title-row" >
							<th  style="text-align: center;width: 200px;">成员名称</th>
							<th  style="text-align: center; width: 200px;">是否组长</th>
							<th  style="text-align: center; width: 200px;">操作</th>
						</tr>
						<tr data-id="" class="hidden"  align="center">
							<td></td>
							<td></td>
							<td></td>					
						</tr>
						<tr data-id="" class="hidden"  align="center">
							<td></td>
							<td></td>
							<td></td>					
						</tr>
						
					</table>
			</div>
		</div>
	
		<div class="text-center" style="margin-top: 10px;">
			<div class="btn btn-info" id="btn-save">
				<i class="icon-save"></i> 保存
			</div>
		</div>
		
	</div>
</form>
<script type="text/javascript">
var validatorfrom;
require([ 'suredyModal', 'validation', 'suredyTree', 'suredyList' ],function(Model,validators,Tree,List) {
	$('#form1').bootstrapValidator({
        message: '输入错误',
        feedbackIcons: {
            validating: 'glyphicon glyphicon-refresh'
        },
		fields : {
			name : {
				validators : {
					notEmpty : {
						message : '请填写班组名！'
					}
				}
			}
		}
	});
	var listConfig = ({
		header : false,
		footer : false,
		search : false,
		checkbox : false
	});	
	List('.member-list', listConfig);
	Tree('.unit-user-tree', '${request.contextPath}/config/ou/tree/true/true/true', {
		autoCollapse : false,
		leafCheckbox : false,
		folderCheckbox : false,
		inContainer : false,
		canFolderActive:false,
		multiselect:true,
		style : 'department'
	});

	$('.unit-user-tree').on(Tree.nodeClick, function(event, $node) {
		var nodeData =Tree.data($node);	
		var nodeisActive =Tree.isActive($node);
		if(nodeisActive){
			var $tr = $('.member-list tr').eq(1).clone();
			$tr.removeClass('hidden');
			$tr.attr("data-id",nodeData.id);
			$tr.find('td').eq(0).html(nodeData.name);
			
			//修改时设置组长
			var gruopLeaderId = '${group.leaderId}';
			if(gruopLeaderId.indexOf(nodeData.id)==-1){
				$tr.find('td').eq(1).html('否');
				$tr.find('td').eq(2).html('<div class="btn btn-sm btn-info" onclick="setAndCancel(this)" ><i class="icon-edit"></i>设置组长</div>');
			}else{
				$tr.find('td').eq(1).html('是');
				$tr.find('td').eq(2).html('<div class="btn btn-sm btn-danger" onclick="setAndCancel(this)" ><i class="icon-edit"></i>取消组长</div>');
			}
			$(".member-list").append($tr);
		}else{
			$('tr', $('.member-list')).each(function(i) {
				var id=$(this).attr("data-id");
				if(id==nodeData.id){
					$(this).remove();
					return false; //跳出当前each循环
				}
			});
		}
	});
	
	//修改时设置成员与组长
	var memberId = '${group.memberId}';
	Tree.nodes('.unit-user-tree').each(function(index){
		var node = $(this);
		var nodeData = Tree.data(this);
		if (memberId.indexOf(nodeData.id)>-1) {
			node.click();
		} 
	});
	
	//保存
	$('#btn-save').click(function() {
		var d1=$('#form1').data('bootstrapValidator').validate();
		var vaild=$('#form1').data('bootstrapValidator').isValid();			
		if(!vaild){
			return false;
		}
		
		//获取成员值和组长值
		var memberId='';
		var memberName='';
		var gruopLeaderId='';
		var gruopLeaderName='';
		$('tr', $('.member-list')).each(function(i) {
			if(i>2){//去掉头和隐藏部分tr
				//获取成员
				if(memberId==''){
					memberId = $(this).attr("data-id");
					memberName = $(this).find('td').eq(0).html();
				}else{
					memberId = memberId+','+$(this).attr("data-id");
					memberName = memberName+','+$(this).find('td').eq(0).html();
				}
			
				//获取组长
				if($(this).find('td').eq(1).html()=='是'){
					if(gruopLeaderId==''){
						gruopLeaderId = $(this).attr("data-id");
						gruopLeaderName = $(this).find('td').eq(0).html();
					}else{
						gruopLeaderId = gruopLeaderId+','+$(this).attr("data-id");
						gruopLeaderName = gruopLeaderName+','+$(this).find('td').eq(0).html();
					}
				}
			}
		});
		
		if(memberId==''){
			alert('请选择成员！')
			return false;
		}
		
		if(gruopLeaderId==''){
			alert('请设置组长！')
			return false;
		}
		
		$('#memberId').val(memberId);
		$('#memberName').val(memberName);
		$('#gruopLeaderId').val(gruopLeaderId);
		$('#gruopLeaderName').val(gruopLeaderName);
		
		$.ajax({
			url : '${request.contextPath}/subject/group-save.do',
			dataType : 'json',
			type : 'POST',
			data : $('#form1').serialize(),
			success : function(success) {
				if (!success) {
					alert('保存班组数据失败！');
				} else if (!success.success) {
					alert('保存班组数据失败！\n\n' + success.msg);
				} else {
					$.notify({title:'提示：',message:'班组数据已保存!'});
					Suredy.loadContent('${request.contextPath}/subject/group-list.do');
					Suredy.Modal.closeModal();
				}
			},
			error : function(a, b, c) {
				alert('服务器错误! ' + b);
			}
		});
	});	
});

//设置组长
var setAndCancel = function(dom){
	var $this=$(dom);
	var $parentTr = $this.parent().parent();
	var $isLeader = $parentTr.find('td').eq(1).html();
	if($isLeader=='是'){
		$parentTr.find('td').eq(1).html('否');
		$parentTr.find('td').eq(2).html('<div class="btn btn-sm btn-info" onclick="setAndCancel(this)" ><i class="icon-edit"></i>设置组长</div>');
	}else{
		$parentTr.find('td').eq(1).html('是');
		$parentTr.find('td').eq(2).html('<div class="btn btn-sm btn-danger" onclick="setAndCancel(this)" ><i class="icon-edit"></i>取消组长</div>');
	}
}
</script>