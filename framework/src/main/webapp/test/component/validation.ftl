	<div class="container">		
		<div class="row">
            <section>
                <div class="page-header">
                    <h3>数据校验</h3>
                </div>

                <div class="col-lg-8 col-lg-offset-2">
                    <form id="form1" method="post" class="form-horizontal">
                        <fieldset>
                            <legend>非空校验</legend>
                            <div class="form-group">
                                <label class="col-lg-3 control-label">用户名称</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" name="username" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-lg-3 control-label">国家</label>
                                <div class="col-lg-5">
                                    <select class="form-control" name="country">
                                        <option value="">-- Select a country --</option>
                                        <option value="fr">France</option>
                                        <option value="de">Germany</option>
                                        <option value="it">Italy</option>
                                        <option value="jp">Japan</option>
                                        <option value="ru">Russia</option>
                                        <option value="gb">United Kingdom</option>
                                        <option value="us">United State</option>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-lg-5 col-lg-offset-3">
                                    <div class="checkbox">
                                        <input type="checkbox" name="acceptTerms" /> 同意许可协议
                                    </div>
                                </div>
                            </div>
                        </fieldset>

                        <fieldset>
                            <legend>基于正则表达式方式的验证</legend>

                            <div class="form-group">
                                <label class="col-lg-3 control-label">邮件地址</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" name="email" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-lg-3 control-label">网址</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" name="website" placeholder="http://" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-lg-3 control-label">电话号码</label>
                                <div class="col-lg-5">
                                    <input type="text" class="form-control" name="phoneNumber" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-lg-3 control-label">RGB颜色值</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" name="color" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-lg-3 control-label">美国邮编</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" name="zipCode" />
                                </div>
                            </div>
                        </fieldset>

                        <fieldset>
                            <legend>身份验证</legend>

                            <div class="form-group">
                                <label class="col-lg-3 control-label">密码</label>
                                <div class="col-lg-5">
                                    <input type="password" class="form-control" name="password" />
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-lg-3 control-label">重复密码</label>
                                <div class="col-lg-5">
                                    <input type="password" class="form-control" name="confirmPassword" />
                                </div>
                            </div>
                        </fieldset>

                        <fieldset>
                            <legend>其他验证</legend>

                            <div class="form-group">
                                <label class="col-lg-3 control-label">年龄</label>
                                <div class="col-lg-3">
                                    <input type="text" class="form-control" name="ages" />
                                </div>
                            </div>
                        </fieldset>

                        <div class="form-group">
                            <div class="col-lg-9 col-lg-offset-3">
                                <button type="submit" class="btn btn-primary">提交</button>
                            </div>
                        </div>
                    </form>
                </div>
            </section>
		</div> 
	</div>	
<script type="text/javascript">
	require([ 'validation' ], function() {
		$('#form1').bootstrapValidator({
	        message: '输入错误',
	        feedbackIcons: {
	            valid: 'glyphicon glyphicon-ok',
	            invalid: 'glyphicon glyphicon-remove',
	            validating: 'glyphicon glyphicon-refresh'
	        },
	        fields: {
	            username: {
	                message: '用户名错误',
	                validators: {
	                    notEmpty: {
	                        message: '非空'
	                    },
	                    stringLength: {
	                        min: 6,
	                        max: 30,
	                        message: '用户名在6~30个字'
	                    },
	                    regexp: {
	                        regexp: /^[a-zA-Z0-9_\.]+$/,
	                        message: '只能输入"a-zA-Z0-9_."'
	                    }
	                }
	            },
	            country: {
	                validators: {
	                    notEmpty: {
	                        message: '请选择国家'
	                    }
	                }
	            },
	            acceptTerms: {
	                validators: {
	                    notEmpty: {
	                        message: '必须同意许可协议'
	                    }
	                }
	            },
	            email: {
	                validators: {
	                    notEmpty: {
	                        message: '邮件地址不能为空'
	                    },
	                    emailAddress: {
	                        message: '邮件格式错误'
	                    }
	                }
	            },
	            website: {
	                validators: {
	                    uri: {
	                        message: 'URL错误'
	                    }
	                }
	            },
	            phoneNumber: {
	                validators: {
	                    digits: {
	                        message: '电话号码必须为数字'
	                    }
	                }
	            },
	            color: {
	                validators: {
	                    hexColor: {
	                        message: '错误的颜色表示法'
	                    }
	                }
	            },
	            zipCode: {
	                validators: {
	                    zipCode: {
	                        country: 'US',
	                        message: 'The input is not a valid US zip code'
	                    }
	                }
	            },
	            password: {
	                validators: {
	                    notEmpty: {
	                        message: '密码必填'
	                    },
	                    identical: {
	                        field: 'confirmPassword',
	                        message: '两次输入的密码不一致'
	                    }
	                }
	            },
	            confirmPassword: {
	                validators: {
	                    notEmpty: {
	                        message: '重复输入密码'
	                    },
	                    identical: {
	                        field: 'password',
	                        message: '两次输入的密码不符'
	                    }
	                }
	            },
	            ages: {
	                validators: {
	                    lessThan: {
	                        value: 150,
	                        inclusive: true,
	                        message: '年龄不能超过150'
	                    },
	                    greaterThan: {
	                        value: 20,
	                        inclusive: false,
	                        message: '年龄至少20'
	                    }
	                }
	            }
	        }
	      
		});
	});
</script>