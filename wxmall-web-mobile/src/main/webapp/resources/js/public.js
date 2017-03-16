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