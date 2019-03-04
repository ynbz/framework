<@t.header>
<!-- <link rel="stylesheet" href="${request.contextPath}/css/suredy-tree.css"> -->
<style type="text/css">
.bottom-input {
	padding-bottom: 5px;
}

.input-lable {
	min-width: 85px;
}

</style>
</@t.header> <@t.body>
<div class="container-fluid suredy-form" align="center">
	<ul class="nav nav-pills" role="tablist" style="width: 1260px;"  >
		<li role="presentation" class="active">
			<a href="#info" aria-controls="info" role="tab" data-toggle="tab">设备信息</a>
		</li>
		<li role="presentation">
			<a href="#xj-list" aria-controls="xj-list" role="tab" data-toggle="tab" id="xj-list-tab">巡检记录</a>
		</li>
		<li role="presentation">
			<a href="#xg-list" aria-controls="xg-list" role="tab" data-toggle="tab" id="xg-list-tab">日志记录</a>
		</li>
	</ul>

	<div class="tab-content" style="width: 1260px;" align="left" >
		<div role="tabpanel" class="tab-pane fade in active" id="info" style="padding-top: 5px;">
			<input type="hidden" id="id" name="id" value="${equipdata.id}" />
			<#if equipdata??><#list equipdata as ea>
			<div class="row">
				<div class="col-md-12">
					<div class="panel panel-info">
						<div class="panel-heading">
							<h3 class="panel-title">基本信息</h3>
						</div>
						
						<div class="panel-body">
							<div class="row">
								<div class=" col-md-3">
									<div class="form-group">
										<div class="input-group">
											<div class="input-lable input-group-addon">设备型号</div>
											<input type="text" class="form-control" id="equipModel" name="equipModel" value="${ea.equipModel}" readonly="readonly" />
										</div>
									</div>
								</div>
								<div class=" col-md-3">
									<div class="form-group">
										<div class="input-group" >
											<div class="input-lable input-group-addon" >序列号</div>
											<input type="text" class="form-control" id="serialNum" name="serialNum" value="${ea.serialNum}" readonly="readonly" />
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<div class="input-group">
											<div class="input-lable input-group-addon">设备类型</div>
											<input type="hidden" id="typeId" name="type.id" value="<#if ea.type.id??>${ea.type.id}</#if>" /> <input type="text" class="form-control" readonly="readonly" id="typeName" name="typeName" value="<#if ea.type.typeName??>${ea.type.typeName}</#if>" readonly="readonly" />
										</div>
									</div>
								</div>
								<div class=" col-md-3">
									<div class="form-group">
										<div class="input-group">
											<div class="input-lable input-group-addon" >状态</div>
											<input type="hidden" class="form-control" id="mystatus" name="mystatus" value="${ea.status}" /> <input type="text" class="form-control" readonly="readonly" id="status" name="status" value="${ea.statusName}" />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">合同号</div>
										<input type="text" class="form-control" id="contractNumber" name="contractNumber" value="${ea.contractNumber}" maxlength="50" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">项目名称</div>
										<input type="text" class="form-control" id="projectName" name="projectName" value="${ea.projectName}" maxlength="50" readonly="readonly"/>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">费用来源</div>
										<input type="text" class="form-control" id="costSource" name="costSource" value="${ea.costSource}" maxlength="50" readonly="readonly"/>
									</div>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<div class="input-group">
										<div class="input-lable input-group-addon">财务资产编号</div>
										<input type="text" class="form-control" id="financeAssetNumber" name="financeAssetNumber" value="${ea.financeAssetNumber}" maxlength="50" readonly="readonly" />
									</div>
								</div>
							</div>
						</div>
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group">
											<div class="input-lable input-group-addon">供应商</div>
											<input type="text" class="form-control" id="supplier" name="supplier" value="${ea.supplier}" readonly="readonly" />
										</div>
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<div class="input-group">
											<div class="input-lable input-group-addon">生产厂商</div>
											<input type="text" class="form-control" id="vendor" name="vendor" value="${ea.vendor}" readonly="readonly" />
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class=" col-md-6">
									<div class="form-group">
										<div class="input-group">
											<div class="input-lable input-group-addon">资产ID</div>
											<input type="text" class="form-control" id="assetId" name="assetId" value="${ea.assetId}" readonly="readonly" />
										</div>
									</div>
								</div>
								<div class=" col-md-3">
									<div class="form-group">
										<div class="input-group">
											<div class="input-lable input-group-addon">RFID</div>
											<input type="text" class="form-control" id="rfid" name="rfid" value="${ea.rfid}" readonly="readonly" />
										</div>
									</div>
								</div>
								<div class="col-md-3">
									<div class="form-group">
										<div class="input-group">
											<div class="input-lable input-group-addon">购买日期</div>
											<input type="text" class="form-control datetimepicker" id="buyDate" name="buyDate" value="${ea.buyDate}" data-format="yyyy-MM-dd" data-foot="false" readonly />
										</div>
									</div>
								</div>
							</div>

							<div class="row">
								<div class="col-md-12">
									<div class="form-group">
										<div class="input-group">
											<div class="input-lable input-group-addon">备注</div>
											<textarea class="form-control" id="comm" name="comm" rows="3" cols="20" readonly="readonly">${ea.comm}</textarea>
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
										<input type="text" class="form-control" readonly="readonly"  value="${(ea.isNewEquip==1)?string('新设备','旧设备')}"  />
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
										<input type="text" class="form-control" id="userPlace" readonly="readonly" name="userPlace" value="${ea.userPlace}" maxlength="50" />
									</div>
								</div>
							</div>
						</div>

						<div class="row  userdiv" style="display: none">
			
							<div class="col-md-6">
								<div class="form-group" >
									<div class="input-group">
										<div class="input-lable input-group-addon">使用范围</div>
										
										<input type="text" class="form-control" readonly="readonly"  value="${(ea.isPublic==0)?string('合同制员工',(ea.isPublic==1)?string('部门公用',(ea.isPublic==2)?string('其他用工人员','')))}"  />
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
										<input type="text" class="form-control" readonly="readonly" id="responsible" name="responsible" value="${ea.responsible}" />

									</div>
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
								<div class="input-group" style="width: 300px;">
									<div class="input-group-addon">${cs.propertyName}<#if cs.propertyUnit??>(${cs.propertyUnit})</#if></div>
									<input type="text" class="form-control" id="${cs.field}" name="${cs.field}" value="${ea.getColvalue(cs.field)}" readonly="readonly" />
								</div>
							</div>
							</#list></#if>
						</div>
					</div>
				</div>
			</div>
			</#list></#if>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="xj-list" style="min-height: 200px; padding-top: 5px;"></div>
		
		<div role="tabpanel" class="tab-pane fade" id="xg-list" style="min-height: 200px; padding-top: 5px;"></div>
	</div>
</div>
</@t.body> <@t.foot>
<script type="text/javascript">
	var checkupLogList;
	function statusChange(v) {
		if (v == 5) {
			$('.responsdiv').show();
			$('.newequipdiv').show();
			$('.userdiv').hide();
			$('.receivediv').hide();
			$('.userplacediv').hide();
		} else if (v == 1) {
			$('.userdiv').hide();
			$('.responsdiv').hide();
			$('.userplacediv').hide();
			$('.newequipdiv').show();
			$('.receivediv').hide();
		} else if (v == 0) {
			$('.userdiv').hide();
			$('.responsdiv').hide();
			$('.userplacediv').hide();
			$('.newequipdiv').hide();
			$('.receivediv').hide();
		} else {
			$('.userdiv').show();
			$('.responsdiv').show();
			$('.userplacediv').show();
			$('.newequipdiv').show();
			$('.receivediv').show();
		}
	}

	require([ 'suredyList','suredyModal', 'suredy', 'bootstrap' ], function(List,Modal) {
		var v = $('#mystatus').val();
		statusChange(v);

		// 更新巡检记录
		checkupLogList = function(page, pageSize) {
			$.ajax({
				url : '${request.contextPath}/EquipAssetCtrl/checkup-log/${equipdata.assetId}',
				data : {
					page : page,
					pageSize : pageSize
				},
				type : 'get',
				dataType : 'html',
				success : function(html, textStatus, jqXHR) {
					if (html) {
						$('#xj-list').html(html);

						List('#xj-list table.checkup-log', {
							header : true,
							paginate : checkupLogList,
							checkbox : false,
							search : false,
							btns : [ {
								text :'新建巡检',
								icon : 'icon-plus',
								style : 'btn-info',
								click : function() {
									Modal.showModal({
										size : 'lg',
										icon : 'icon-plus',
										title : '新增巡检',
										showFoot : false,
										uri : '${request.contextPath}/EquipAssetCtrl/eqm-checkup-update/checkupSave?assetId=${equipdata.assetId}'
									});
								}
							}]
						});
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
				}
			});
		};
		
		// 更新修改记录
		var eqLogList = function(page, pageSize) {
			$.ajax({
				url : '${request.contextPath}/EquipAssetCtrl/eq-log/${equipdata.id}',
				data : {
					page : page,
					pageSize : pageSize
				},
				type : 'get',
				dataType : 'html',
				success : function(html, textStatus, jqXHR) {
					if (html) {
						$('#xg-list').html(html);

						List('#xg-list table.eq-log', {
							header : false,
							paginate : checkupLogList
						});
					}
				},
				error : function(jqXHR, textStatus, errorThrown) {
				}
			});
		};

		checkupLogList(1, 25);
		eqLogList(1, 25);
	});
</script>
</@t.foot>

