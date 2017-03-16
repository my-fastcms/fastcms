var drawResult = {};//保存抽奖结果
var click=false;
var lottery={
	index:-1,	//当前转动到哪个位置，起点位置
	count:0,	//总共有多少个位置
	timer:0,	//setTimeout的ID，用clearTimeout清除
	speed:20,	//初始转动速度
	times:0,	//转动次数
	cycle:50,	//转动基本次数：即至少需要转动多少次再进入抽奖环节
	prize:-1,	//中奖位置，通过服务端返回
	init:function(id){
		if ($("#"+id).find(".prize_item").length>0) {
			$lottery = $("#"+id);
			$units = $lottery.find(".prize_item");
			this.obj = $lottery;
			this.count = $units.length;
		};
	},
	roll:function(){
		var _index = this.index;
		var _count = this.count;
		var lottery = this.obj;
		_index += 1;
		if (_index > _count-1) {
			_index = 0;
		}
		//$(lottery).find(".lottery-unit-" + _index).css("border", "");//.addClass("lott_active");
		$(lottery).find(".prize_item").each(function(){
			var me = $(this);
			me.css({"color" : "", "font-size" : ""});
		});
		$(lottery).find(".lottery-unit-" + _index).css({"color" : "red", "font-size" : "24px"});//.removeClass("lott_active");
		this.index = _index;
		//console.log("=======index:" + this.index);
		return false;
	},
	stop:function(callback){
		this.prize=-1;
		this.times=0;
		this.index=-1;
		clearTimeout(this.timer);
		click=false;
		$(this.obj).find("li.prize_item").each(function(){
			$(this).removeClass("active");
		});
		if(callback) callback();
		return false;
	}
};

function initDrawView(callback){
	maskMsg("正在初始化抽奖组件...");
	$.post('/lottery/initView', {
		lotteryId : $('#lottery_id').val()
	} , function(_json) {
		unmask();
		if(_json.code!=200){
			errorMessage = _json.msg;
			doing = false;
			$.alert(errorMessage);
		}else{
			var awards = _json.data;
			for(var i=0;i<awards.length;i++){
				var award = awards[i];
				var li = $("#award_"+award.award_pos);
				li.find("h2").text(award.award_name.length>4 ? award.award_name.substring(0, 4) : award.award_name);
				li.find("div.prize_type_name").find("p").text(award.type_name);
				li.find("div.prize_item_icon").html(award.award_count+"份");
			}
			
			lottery.init('lottery');
			if(callback) callback();
		}
	});
}

function roll(){
	lottery.times += 1;
	//console.log("========times:" + lottery.times + ",index:" + lottery.index);
	if(lottery.times > 400){
		//转了2000次服务端还是没响应的话，就设置超时，重新再抽
		clearTimeout(lottery.timer);
		lottery.prize=-1;
		lottery.times=0;
		lottery.index=-1;
		click=false;
		initDrawView(function(){
			$.alert("抽奖失败，请重试或联系客服");	
		});
		return false;
	}
	
	if(lottery.times >lottery.cycle + 100
			&& lottery.index == lottery.prize){//转到此处停止
		clearTimeout(lottery.timer);
		lottery.prize=-1;
		lottery.times=0;
		lottery.index=-1;
		click=false;
		initDrawView(function(){
			if(drawResult.name != "感谢参与"){
				$.alert("恭喜您获得[" + drawResult.name + "]", function(){/*location.reload();*/});
			}
		});
		return false;
	}
	lottery.roll();
	if (lottery.times<lottery.cycle) {
		lottery.speed -= 50;
	}
	if (lottery.speed<40) {
		lottery.speed=100;
	}
	lottery.timer = setTimeout(roll,lottery.speed);
	return false;
}

$(document).ready(function(){
	initDrawView(function(){
		$("#lottery-tip").popup();
	});
	$('.lottery_btn').on('click',function(){
		if (click) {
			return false;
		}else{
			lottery.speed=100;
			roll();
			click=true;
			$.post("/lottery/start", {id:$("#lottery_id").val(), snick: $('#seller_nick_input').val()}, function(resp){
				click=false;
				if(resp.code != 200){
					setTimeout(lottery.stop(function(){
						$.alert(resp.msg, function(){});
					}), 1000);
				}else{
					drawResult = resp.data; 
					if(drawResult.luck == true){
						lottery.prize = drawResult.index - 1;	
					}else{
						lottery.prize = drawResult.index;
					}
					//console.log("=======index:" + lottery.prize);
					if(lottery.prize<0 && lottery.times > lottery.cycle){
						lottery.stop();
						$.alert("感谢参与");
					}
				}
			});
		}
	});
	
	//充值
	$("#chongzhi").click(function(){
		showChongzhi();
		return false;
	});
/*	wx.ready(function(res){
		
	});*/
	
});