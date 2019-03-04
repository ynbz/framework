<@t.header>
<!-- <link rel="stylesheet" href="${request.contextPath}/css/suredy-tree.css"> -->
<style type="text/css">
.bottom-input {
	padding-bottom: 5px;
}

.input-lable {
	min-width: 95px;
}

   
.user-list {
	padding:10px 20px 0px 10px;
	border-bottom:1px solid #ddd;
}  
.user-highlight {
	background:#ddd;
}
</style>
</@t.header> <@t.body>
<div class="navbar navbar-default navbar-fixed-top padding-0 margin-0">
	<div class="container-fluid">
		<div class="navbar-header">
			<p class="navbar-text navbar-left hidden-xs" id="updatedata"
				style="cursor: pointer;">
				<i class="icon-save"></i> 保存
			</p>
			
			<p class="navbar-text navbar-left hidden-xs" id="checkupSave"
				style="cursor: pointer;">
				<i class="icon-save"></i> 完成巡检
			</p>
			
			<p class="navbar-text navbar-left hidden-xs" id="updatedata"
				style="cursor: pointer;">
				<i class="icon-remove" onclick="window.history.back()">返回</i>
			</p>
		</div>
	</div>
</div>
<div class="container-fluid suredy-form" >
	<input type="hidden" id="thisnodeid" name="thisnodeid" value="${thisnodeid}" />
	<form id="form" method="post">
		<#if equipdata??><#list equipdata as ea>
		<div class="row" style="margin-top: 60px;">
			<div class="col-md-12">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">
							基本信息(<span style="color: red; font-size: 15px;">*&nbsp;</span>必填项)
						</h3>
					</div>
					<input type="hidden" id="id" name="id" value="${ea.id}" />
					<div class="panel-body">
						<div class="row">
							<div class=" col-md-3">

								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">
											<span style="color: red; font-size: 15px;">*&nbsp;</span>设备型号
										</div>
										<input type="text" class="form-control" id="equipModel" name="equipModel" value="${ea.equipModel}" maxlength="50" />
									</div>
								</div>
							</div>
							
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">
											<span style="color: red; font-size: 15px;">*&nbsp;</span>设备类型
										</div>
										<input type="hidden" id="tempTypeID" name="tempType" value="<#if ea.type.id??>${ea.type.id}</#if>" /> 
										<input type="hidden" id="typeId" name="type.id" value="<#if ea.type.id??>${ea.type.id}</#if>" /> 
										<#if ea.type.id??><input class="form-control"  value="${ea.type.typeName}" readonly="readonly"/><#else><input type="text" class="form-control" readonly="readonly" id="typeName" name="typeName" value="" /></#if>
									</div>
								</div>
							</div>
							<div class=" col-md-3">
								<div class="form-group">
									<div class="input-group" >
										<div class="input-lable input-group-addon">
											<span style="color: red; font-size: 15px;">*&nbsp;</span>状态
										</div>
										<select class="form-control" id="status" name="status" onchange="statusChange(this.value)"> 
										<#if equipStatus??>
											<#list equipStatus as es>
											<option value="${es.index}" ${(ea.status==es.index)?string('selected','')}>${es.statusName}</option>
											</#list>
										</#if>
										</select>
									</div>
									<#if ea.statused??> <span style="color: red;">导入数据：${ea.statused}</span>
									</#if>
								</div>
							</div>
						</div>

						<div class="row">
							<div class=" col-md-3">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">
											<span style="color: red; font-size: 15px;">*&nbsp;</span>资产ID
										</div>
										<input type="text" class="form-control" id="assetId" name="assetId" value="${ea.assetId}" readonly="readonly" maxlength="50" />
									</div>
								</div>
							</div>
							<div class=" col-md-3">
								<div class="form-group">
									<div class="input-group" >
										<div class="input-lable input-group-addon"
											style="width: 85px;">序列号</div>
										<input type="text" class="form-control" id="serialNum" name="serialNum" value="${ea.serialNum}" maxlength="50" />
									</div>
								</div>
							</div>
							<div class=" col-md-3">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">
											<span style="color: red; font-size: 15px;">*&nbsp;</span>RFID
										</div>
										<input type="text" class="form-control" id="rfid" name="rfid" value="${ea.rfid}" maxlength="50" />
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">
											<span style="color: red; font-size: 15px;">*&nbsp;</span>购买日期
										</div>
										<input type="text" class="form-control datetimepicker" id="buyDate" name="buyDate" value="${ea.buyDate}" data-format="yyyy-MM-dd" data-foot="false" readonly />
									</div>
								</div>
							</div>

						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">合同号</div>
										<input type="text" class="form-control" id="contractNumber" name="contractNumber" value="${ea.contractNumber}" maxlength="50" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">项目名称</div>
										<input type="text" class="form-control" id="projectName" name="projectName" value="${ea.projectName}" maxlength="50" />
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">费用来源</div>
										<input type="text" class="form-control" id="costSource" name="costSource" value="${ea.costSource}" maxlength="50" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">财务资产编号</div>
										<input type="text" class="form-control" id="financeAssetNumber" name="financeAssetNumber" value="${ea.financeAssetNumber}" maxlength="50" />
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">供应商</div>
										<input type="text" class="form-control" id="supplier" name="supplier" value="${ea.supplier}" maxlength="50" />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">生产厂商</div>
										<input type="text" class="form-control" id="vendor" name="vendor" value="${ea.vendor}" maxlength="50" />
									</div>
								</div>
							</div>
						</div>

						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">备注</div>
										<textarea class="form-control" id="comm" name="comm" rows="3" cols="20" maxlength="250">${ea.comm}</textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">状态选项（必填项）</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-12">
								<div class="form-group newequipdiv" style="display: none">
									<div class="input-group">
										<div class="input-lable input-group-addon">新旧设备</div>
										<select class="form-control" id="isNewEquip" name="isNewEquip">
											<option value="1" ${(ea.isNewEquip==1)?string('selected','')}>新设备</option>
											<option value="0" ${(ea.isNewEquip==0)?string('selected','')}>旧设备</option>
										</select>
									</div>
								</div>

							</div>
						</div>
						
						<div class="row  userplacediv" style="display: none">
							<div class="col-md-6">
								<div class="form-group receivediv" style="display: none">
									<div class="input-group">
										<div class="input-lable input-group-addon">领用日期</div>
										<input type="text" class="form-control datetimepicker" id="receiveDate" name="receiveDate" value="${ea.receiveDate}" data-format="yyyy-MM-dd" data-foot="false" readonly />
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">使用地点</div>
										<input type="text" class="form-control" id="userPlace" name="userPlace" value="${ea.userPlace}" maxlength="50" />
									</div>
								</div>
							</div>
						</div>

						<div class="row  userdiv" style="display: none">
			
							<div class="col-md-6">
								<div class="form-group" >
									<div class="input-group">
										<div class="input-lable input-group-addon">使用范围</div>
										<select class="form-control" id="isPublic" name="isPublic">
											<option value="0" ${(ea.isPublic==0)?string('selected','')}>合同制员工</option>
											<option value="1" ${(ea.isPublic==1)?string('selected','')}>部门公用</option>
											<option value="2" ${(ea.isPublic==2)?string('selected','')}>其他用工人员</option>
										</select>
									</div>
								</div>

							</div>
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">责任部门</div>
										<input type="hidden" class="form-control" id="userUnitId" name="userUnitId" value="${ea.userUnitId}" /> 
										<input type="text" class="form-control" readonly="readonly" id="userUnit" name="userUnit" value="${ea.userUnit}" maxlength="250" />
									</div>

								</div>
							</div>
						</div>
						<div class="row responsdiv">
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">责任人</div>
										<input type="hidden" class="form-control" id="responsibleId" name="responsibleId" value="${ea.responsibleId}" /> 
										<input type="text" class="form-control" autocomplete="off" id="responsible" name="responsible" value="${ea.responsible}" />
										<div class="input-group-addon btn btn-default" id="responsibleSelector">选择...</div>									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">责任人电话</div>
										<input type="text" class="form-control" id="responsiblePhone" name="responsiblePhone" value="${ea.responsiblePhone}"readonly="readonly" maxlength="250" />
									</div>
								</div>
							</div>
						</div>

						<div class="row userdiv">
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">使用人</div>
										<input type="hidden" class="form-control" id="userID" name="userID" value="${ea.userID}" /> 
										<input type="text" class="form-control" readonly="readonly" id="userName" name="userName" value="${ea.userName}"/>
									</div>
									<#if ea.userNameeed??> <span style="color: red;">导入数据：${ea.userNameeed}</span>
									</#if>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">使用人电话</div>
										<input type="text" class="form-control" id="userPhone" name="userPhone" value="${ea.userPhone}" readonly="readonly" maxlength="250" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="form-inline col-md-12">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">配置信息</h3>
					</div>
					<div class="panel-body" id="propertyid">
						<#if csdata??><#list csdata as cs>
							<div class="form-group bottom-input">
								<div class="input-group" style="width: 295px;">
									<div class="input-group-addon input-lable">${cs.propertyName}<#if cs.propertyUnit??>(${cs.propertyUnit})</#if></div>
									<input type="text" class="form-control" id="${cs.field}" name="${cs.field}" value="${ea.getColvalue(cs.field)}"/>
								</div>
							</div>
						</#list></#if>
					</div>
				</div>

			</div>
		</div>
		</#list></#if>
	</form>
</div>
<script type="text/javascript">
	var userList=[];
	<#if users??><#list users as user>
	userList.push({pinyin:'${user.shortPinyin}', name:'${user.fullName}', id:'${user.id}', phone:'${user.userphone}', unit:'${user.fullUnitName}', unitId:'${user.unitId}'});
	</#list></#if>	
</script>
</@t.body> <@t.foot>
<script type="text/javascript"
	src="${request.contextPath}/app/equipasset/eqm-checkup-update.js"></script>
<script type="text/javascript">
	function statusChange(v){	
		 if(v==5){
			
			$('.responsdiv').show();
			$('.newequipdiv').show();
			$('.userdiv').hide();
			$('.receivediv').hide();
			$('.userplacediv').hide();
		}else if(v==1){
			
			$('.userdiv').hide();
			$('.responsdiv').hide();
			$('.userplacediv').hide();
			$('.newequipdiv').show();
			$('.receivediv').hide();
		}else if(v==0){
			
			$('.userdiv').hide();
			$('.responsdiv').hide();
			$('.userplacediv').hide();
			$('.newequipdiv').hide();
			$('.receivediv').hide();
		}else{
			
			$('.userdiv').show();
			$('.responsdiv').show();
			$('.userplacediv').show();
			$('.newequipdiv').show();
			$('.receivediv').show();
		} 
	}
	$(document).ready(function(){
			var v=$('#status').val();
			statusChange(v);
		});
</script>
</@t.foot>

