var sending = false;
function joinGift(){
	if(sending==true){
		return false;
	}
	sending=true;
	$.post("/mobile/JoinGift.json", {}, function(json) {
		if(json.code==0){
			$("#join_button").hide().after('<div class="been_involved"> 您已参与成功！<br>奖号：<font>'+json.activeNumber+'</font>，坐等中奖！</div>');
			$("#tips_number").text(json.activeNumber);
			opendailog();
		}else{
			showAlert(json.txt);
		}
		sending=false;
	},'json');
	
}
function opendailog() {
	easyDialog.open({
		container : "tips",
		fixed : true,
		drag : true,
		overlay : true
	});
}
function toDraw() {
	location.href="/mobile/draw/Index.h4";
}