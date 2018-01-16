var Template = { 
    selectMenu: function(menu) {
        if (menu) {
            $(".buttom_list").children("a").removeClass("active");
            $(menu).addClass("active");
        }
    },
    init: function(menu) {
        Template.selectMenu(menu);
    }
}
function mask (){
	$.showLoading();
}
function unmask (){
	$.hideLoading();
}
function maskMsg(msg){
	$.showLoading(msg);
}
function showMsg(msg, callBack){
	if(callBack){
		$.toast(msg, "text", callBack);
	}else{
		$.toast(msg, "text");		
	}
}
function showWarnMsg(msg, callBack){
	if(callBack){
		$.toast(msg, "forbidden", callBack);
	}else{
		$.toast(msg, "forbidden");		
	}
}
function showCancelMsg(msg, callBack){
	if(callBack){
		$.toast(msg, "cancel", callBack);
	}else{
		$.toast(msg, "cancel");		
	}
}

function chongzhi(type){
    $.showLoading("正在连接支付服务...");
    $.post(obz.ctx+"/pay", {type:type}, function(data){
  	  	$.hideLoading();
		var resp = data;
		if(resp.code != 200){
			$.alert(resp.msg);
			return;
		}
		var json = resp.data;
		if(json.returnMsg=='OK'){
			//alert("paySign:" + json.paySign + ",timeStamp:" + json.timeStamp + ",package:"+json.packageValue);
			wx.chooseWXPay({
			    timestamp: json.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
			    nonceStr: json.nonceStr, // 支付签名随机串，不长于 32 位
			    package: json.packageValue, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
			    signType: "MD5", // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
			    paySign: json.paySign, // 支付签名
			    success: function (res) {
			        // 支付成功后的回调函数
			    	if(res.err_msg == "get_brand_wcpay_request:ok" ) {
		            	//使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回ok，但并不保证它绝对可靠。
						initDrawView(function(){
			            	if(type == 'x11') 
			            		$.alert("支付成功，获得20个积分");
			            	else if(type == 'x12')
			            		$.alert("支付成功，获得60个积分");
			            	else if(type == 'x13')
			            		$.alert("支付成功，获得120个积分");
						});	
		            }else if(res.err_msg == "get_brand_wcpay_request:cancel"){
		            	$.alert("您取消了支付");
		            }else {
		            	$.alert("支付失败，请联系管理员，" + res.err_msg);
		            }  
			    },
			    fail: function (res){
			    	for(var key in res){
			    		alert(res[key]);
			    	}
			    }
			});
		}
	}); 
}

function showChongzhi(){
	$.actions({
		  actions: [{
		    text: "充值2元获得20个积分",
		    onClick: function() {
		    	chongzhi("x11");
		    }
		  },{
		    text: "充值5元获得60个积分",
		    onClick: function() {
		    	chongzhi("x12");
		    }
		  },{
		    text: "充值10元获得120个积分",
		    onClick: function() {
		    	chongzhi("x13");
		    }
		  }]
	});
}