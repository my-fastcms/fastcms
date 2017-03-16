function OpenWW(shopid,snick){
	var options = {
		    snick:snick,
		    itemId :"",
		    shopId :shopid,
		    orderId :""
		};
	try{
		Tida.wangwang(options,function(data){
			if(data.ret.indexOf("SUCCESS")>0){
			}else{
				showAlert("您的淘宝客户端版本不够，您可以前往店铺后与卖家联系");
			}
		});
	}catch (e) {
		showAlert("您的淘宝客户端版本不够，您可以前往店铺后与卖家联系");
	}
}
function opendailog() {
	easyDialog.open({
		container : "tips",
		fixed : true,
		drag : true,
		overlay : true
	});
}
var sending=false;
function getPrizeInfo(id){//获取奖品现在的信息
	if(sending){
		return false;
	}
	sending=true;
	$.post("/admin/user/PrizeInfo.json", {"id":id}, function(json) {
		if(json.code==0){
			$("#gettype").html(json.gettype);
			$("#pondname").html(json.pondname);
			$("#sendinfo").html('<div class="cont">'+json.sendinfo+'</div>');
			$("#pond_info").html(json.pondinfo);
			$("#pond_time").html(json.time);
			opendailog();
		}else{
			showAlert(json.txt);
		}
		sending=false;
	},'json');
}

function getPrize(id,obj){//获取奖品
	if(sending){
		return false;
	}
	sending=true;
	$.post("/admin/user/GetPrize.json", {"id":id}, function(json) {
		if(json.code==0){
			showAlert(json.txt);
			$(obj).removeClass("register_no").addClass("register_yes");
			$(obj).removeAttr("onclick");
		}else{
			showAlert(json.txt);
		}
		sending=false;
	},'json');
	return false;
}
