<div class="row">
	<div class=" col-md-4" >
		<div class="panel panel-default" style="height: 330px;">
			<div class="panel-heading">
				<h3 class="panel-title">通知消息&nbsp; <i class="icon-volume-up"></i></h3>
			</div>
			<div class="panel-body">
			  <ul class="list-group">
		        <li class="list-group-item"><a href="#">您有新邮件<span class="text-danger">【3】</span></a></li>
		        <li class="list-group-item"><a href="#">您关注的文件信息<span class="text-danger">【5】</span></a></li>
		        <li class="list-group-item">.......</li>
		    </ul>
			</div>
		</div>
	</div>
	
	<div class=" col-md-4">
		<div class="panel panel-warning" style="height: 330px;">
			<div class="panel-heading">
				<h3 class="panel-title">待办信息&nbsp; <i class="icon-briefcase"></i></h3>
			</div>
			<div class="panel-body">
				<h4 class="panel-title"><a id="atodo">目前有待办事项<span class="text-danger">【3】</span></a></h4>
				<ul class="list-group" style="margin-top: 25px;">
			        <li class="list-group-item">公文<span class="text-danger">【2】</span></li>
			        <li class="list-group-item">合同<span class="text-danger">【6】</span></li>
			        <li class="list-group-item">发文<span class="text-danger">【3】</span></li>
			    </ul>
			</div>
		</div>
	</div>
	
	
	<div class=" col-md-4">
		<div class="panel panel-success" style="height: 330px;">
			<div class="panel-heading">
				<h3 class="panel-title">日程&nbsp; <i class="icon-calendar"></i></h3>
			</div>
			<div class="panel-body" align="center">
				<div class="form_datetime" ></div>

			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class=" col-md-4" >
		<div class="panel panel-info" style="height: 330px;">
			<div class="panel-heading">
				<h3 class="panel-title">快速操作&nbsp; <i class="icon-wrench"></i></h3>
			</div>
			<div class="panel-body">
				<div class="row">
					<div class=" col-md-12">
						<!-- <div class="btn btn-info" id="adddatabtn" style="margin-bottom:5px">
							<i class="icon-plus"></i> 起草部门工作单
						</div>
						<div class="btn btn-info" id="addgsfw" style="margin-bottom:5px">
							<i class="icon-plus"></i> 起草公司发文
						</div>
						<div class="btn btn-info" id="addgssw" style="margin-bottom:5px">
							<i class="icon-plus"></i> 起草公司收文
						</div> -->
						<#if forms??>
							<#list forms as form>
								<#if form.formSel==2>
									<div class="btn btn-info" onclick="openFlow('live-form/open/${form.id}')" style="margin-bottom:5px">
										<i class="icon-plus"></i>${form.name}
									</div>
								<#else>
									<div class="btn btn-info" onclick="openFlow('${form.draftPath}/${form.fileType}')"  style="margin-bottom:5px">
										 <i class="icon-plus"></i>${form.name}
									</div>
								</#if>
							</#list>
						</#if>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	
	<div class=" col-md-4">
		<div class="panel panel-success" style="height: 330px;">
			<div class="panel-heading">
				<h3 class="panel-title">全文检索&nbsp; <i class="icon-search"></i></h3>
			</div>
			<div class="panel-body">
				<div class="input-group" style="margin-top: 70px;">
					<input type="text" class="form-control" id="keyword" name="keyword" value="" placeholder="&lt;按关键字搜索,多关键字可用空格分隔&gt;" />
					<div class="btn btn-info input-group-addon" id="btn-search"><i class="icon-search"></i> 搜索</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class=" col-md-4">
		<div class="panel panel-danger" style="height: 330px;">
			<div class="panel-heading">
				<h3 class="panel-title">公告&nbsp; <i class="icon-bell-alt"></i></h3>
			</div>
			<div class="panel-body">
				<h4 class="panel-title">【公司公告】</h4>
				<ul class="list-group" style="margin-top: 15px;">
					<#if notes??>
						<#list notes as ns>
							<#if ns.type=='1'>
								<li class="list-group-item"><a href='javascript:void(0)'onclick="noticeView('${ns.id}')">${ns.title}────${ns.createDate?string("yyyy-MM-dd HH:mm:ss")}</a></li>
							</#if>
						</#list>
					</#if>
			    </ul>
			    <h4 class="panel-title">【部门公告】</h4>
				<ul class="list-group" style="margin-top: 15px;">
			       <#if notes??>
						<#list notes as ns>
							<#if ns.type=='2'>
								<li class="list-group-item"><a href='javascript:void(0)'onclick="noticeView('${ns.id}')">${ns.title}────${ns.createDate?string("yyyy-MM-dd HH:mm:ss")}</a></li>
							</#if>
						</#list>
					</#if>
			    </ul>
			</div>
		</div>
	</div>
</div>
	
<script type="text/javascript">
$('#atodo').on('click',function(){
	Suredy.loadContent('${request.contextPath}/todo/todoList.do');
})
	
$("#adddatabtn").click(function(){
	window.open('${request.contextPath}/workflow/new.do');
});

$("#addgsfw").click(function(){
	window.open('${request.contextPath}/dism/newDis.do');
});

$("#addgssw").click(function(){
	window.open('${request.contextPath}/incdism/newIncDis.do');
});

var openFlow = function (url){
	window.open('${request.contextPath}/'+url);
}
function noticeView(id){	
	window.open('${request.contextPath}/notice/notice-view?id='+id)
};	


$(".form_datetime").datetimepicker({
	 format: "yyyy-mm-dd",
	 autoclose: true,
	 todayBtn: true,
	 todayHighlight: true,
	 showMeridian: true,
	 pickerPosition: "bottom-left",
	 language: 'zh-CN',//中文，需要引用zh-CN.js包
	 startView: 2,//月视图
	 minView: 2//日期时间选择器所能够提供的最精确的时间选择视图
});
</script>