$(document).ready(function(){
	var needRefresh = false;
	var end;
	function show(){
		if(end<=0){
			$('.timer').style.display='none';
			if(needRefresh){
				location.reload();
			}
			return;
		}
		var current = end;
		var tips = '倒计时';
		var tips_clean = '';
		if(current>86400){
			var days = parseInt(current/86400);
			tips+='<span>'+days+'</span>天';
			tips_clean+=days+'天';
			current = current%86400;
		}
		if(current>3600){
			var hour = parseInt(current/3600);
			tips+='<span>'+hour+'</span>小时';
			tips_clean+=hour+'小时';
			current = current%3600;
		}
		if(current>60){
			var minute = parseInt(current/60);
			tips+='<span>'+minute+'</span>分';
			tips_clean+=minute+'分';
			current = current%60;
		}
		tips+='<span>'+current+'</span>秒';
		tips_clean+=current+'秒';
		//
		$('.timer').html(tips);
		//
		var btn = $('.btn_timer_update');
		if(btn){
			btn.html(tips_clean);
		}
		//
		setTimeout(function(){
			end = end - 1;
			show();
		},1000);
	}
	
	end = parseInt($('#db_end_timestamp').val());
	if(end>0){
		needRefresh = true;
	}
	show();
	
});

