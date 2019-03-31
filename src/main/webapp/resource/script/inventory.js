//存放主要交互逻辑js代码
//javascript模块化
var inventoryKill = {
	// 封装秒杀相关ajax的url
	URL : {
		now : function() {
			return '/inventory/time/now';
		},
		exposer : function(inventoryId) {
			return '/inventory/' + inventoryId + '/exposer';
		},
		execution : function(inventoryId, md5) {
			return '/inventory/' + inventoryId + '/' + md5 + '/execution';
		}
	},
	handleInventoryKill : function(inventoryId, node) {
		// 处理秒杀逻辑，控制显示逻辑，执行秒杀
		node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');// 按钮
		$.post(inventoryKill.URL.exposer(inventoryId),{},function(result) {
			// 在回掉函数中执行交互流程
			if (result && result['success']) {
				var exposer = result['data'];
				if (exposer['exposed']) {
					// 开启秒杀
					// 获取秒杀地址
					var md5 = exposer['md5'];
					var killUrl = inventoryKill.URL.execution(inventoryId, md5);
					console.log("killUrl:" + killUrl);
					// 绑定一次点击事件，防止用户连续点击
					$('#killBtn').one('click',function() {
						// 执行秒杀请求
						// 1：先禁用按钮
						$(this).addClass('disabled');
						// 2：发送秒杀请求
						$.post(killUrl,{},function(result) {
							if (result && result['success']) {
								var killResult = result['data'];
								var state = killResult['state'];
								var stateInfo = killResult['stateInfo'];
								// 3：显示秒杀结果
								node.html('<span class="label label-success">' + stateInfo + '</span>');
							}
						});
					});
					node.show();
				} else {
					// 未开始秒杀（用户机器时间与服务器时间不一致）
					var now = exposer['now'];
					var start = exposer['start'];
					var end = exposer['end'];
					// 重新计算计时逻辑
					inventoryKill.countdown(inventoryId, now, start, end);
				}
			} else {
					console.log('result:' + result);
			}
		});
	},
	// 验证手机号
	validataPhone : function(phone) {
		if (phone && phone.length == 11 && !isNaN(phone)) {
			return true;
		} else {
			return false;
		}
	},
	countdown : function(inventoryId, nowTime, startTime, endTime) {
		var inventoryBox = $('#inventory-box');
		// 时间判断
		if (nowTime > endTime) {
			// 秒杀结束
			inventoryBox.html('秒杀结束！');
		} else if (nowTime < startTime) {
			// 秒杀未开始，计时事件绑定
			var killTime = new Date(startTime + 1000);
			inventoryBox.countdown(killTime, function(event) {
				// 时间格式
				var format = event.strftime('秒杀倒计时：%D天  %H时  %M分  %S秒');
				inventoryBox.html(format);
				// 时间完成后回掉事件
			}).on('finish.countdown', function() {
				// 获取秒杀地址，控制显示逻辑，执行秒杀
				inventoryKill.handleInventoryKill(inventoryId, inventoryBox);
			});
		} else {
			// 秒杀开始
			inventoryKill.handleInventoryKill(inventoryId, inventoryBox);
		}
	},
	// 详情页秒杀逻辑
	detail : {
		// 详情页初始化
		init : function(param) {
			// 手机验证和登陆，计时交互
			// 规划交互流程
			// 在cookie中查找手机号
			var killPhone = $.cookie('killPhone');
			// 验证手机号
			if (!inventoryKill.validataPhone(killPhone)) {
				// 没有登陆，绑定phone，控制输出
				var killPhoneModal = $('#killPhoneModal');
				killPhoneModal.modal({
					show : true,// 显示弹出层
					backdrop : 'static',// 禁止位置关闭
					keyboard : false// 关闭键盘事件
				});
				$('#killPhoneBtn').click(function() {
					var inputPhone = $('#killPhoneKey').val();
					if (inventoryKill.validataPhone(inputPhone)) {
						// 电话写入cookie
						$.cookie('killPhone', inputPhone, {expires : 7,path : '/inventory'});
						// 刷新页面
						window.location.reload();
					} else {
						$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误！</label>').show(300);
					}
				});
			}

			// 已经登陆
			// 计时交互
			var startTime = param['startTime'];
			var endTime = param['endTime'];
			var inventoryId = param['inventoryId'];
			$.get(inventoryKill.URL.now(), {}, function(result) {
				if (result && result['success']) {
					var nowTime = result['data'];
					// 时间判断
					inventoryKill.countdown(inventoryId, nowTime, startTime,endTime);
				} else {
					console.log('result' + result);
				}
			});
		}
	}
}