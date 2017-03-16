//抽奖
$(document).ready(function(){
	//var _interactId = "";// S.one('#dp_interact_id').val();
	var _interactId = S.one('#dp_interact_id').val();
	//alert("_interactId:" + _interactId);
    Tida.ready({
        interactId : _interactId
    }, function(){
    });
	
    $('.lottery_btn').on('click',function(){
		var lotteryCount = $("#today_used_count").html();
		if(lotteryCount<=0){
			showMessage("亲，您的抽奖机会不够了，去做任务赚取吧");
			return;
		}
		
		Tida.doAuth(function(data){
			if(data.finish){
				//授权成功
				//开始抽奖
				dbumama.win.bg();
				dbumama.win.waiting();
				Tida.draw(function(e){
			       if(window.drawResultList){
			           drawResultList.push(e);
			       }else{
			           window.drawResultList = [e];
			       }
			       
			       Tida.showDrawResult({
			           score : {
			               currPoint : "", // 若需要传单位："1234秒"
			               isBest : false
			           },
			           shareData : {
			               image : "http://img02.taobaocdn.com/tps/i2/T1gdi3Xb8pXXXXXXXX-230-35.png"
			           },
			           awards : window.drawResultList || []
			       }, function() {
			           window.drawResultList=[];
			       }); 
			       
			       dbumama.win.bgc();
	        	   dbumama.win.waitingc();
		           //用户抽一次奖，更新抽奖次数
		           KISSY.IO.post('/mobile/user/save',
		           {
		        	   "lotCount" : "1",
		        	   "snick":$('#db_seller_nick').val()
		           },
		           function(_json){
		        	   if(_json.code != 200){
			   				showMessage(_json.msg,null);
			   			}else {
			   				$("#today_used_count").html(lotteryCount-1);
			   			}
		           });
			       
			    });
			}else{
				showMessage("授权失败",null);
			}
	    });
	});
});