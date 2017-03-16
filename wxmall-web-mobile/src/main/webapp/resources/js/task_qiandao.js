var sending = false;
function DoSign(){
	if(sending==true){
		return false;
	}
	var id = $("#task_id").val();
	sending=true;
	$.post("/admin/task/DoSign.json", {"id":id}, function(json) {
		if(json.code==0){
			$("#gift_count").html(json.gift_draw_count);
			$("#next_gift_count").html(json.next_sign_draw_count);
			$("#all_draw_count").html(json.all_draw_count);
			opendailog();
		}else if(json.code==1){
			showAlert("今日已签到，明日再来吧");
		}else if(json.code==2){
			showAlert("系统数据异常");
		}else if(json.code==-10){
			showAlert("任务无效");
		}else{
			showAlert("签到失败");
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

function reShowTable(){
	var mc=$("#max_chance_count").val();
	mc= Number(mc);
	$("#resettr td").each(function(index,obj){
		if(index==0)
			return;
		if(index<=mc){
			$(obj).html(1+"+"+(index-1));
		}else{
			$(obj).html(1+"+"+mc);
		}
	});
}

var now = new Date();
function set_cal() {
	$("#daydate").html(now.getFullYear()+"年"+(now.getMonth()+1)+"月");
	var thtime = new Date();
	thtime.setDate(1);
	var begin_day = thtime.getTime();
	begin_day_date = thtime.getDay();
	thtime.setMonth(thtime.getMonth() + 1);
	end_day = thtime.getTime();
	count_day = (end_day - begin_day) / 1000 / 60 / 60 / 24;
	input_table(begin_day_date, count_day);
}
function input_table(begin, count) {
	begin = begin==0?6:begin - 1;
	var row = Math.floor((count + begin) / 7);
	if ((count + begin) % 7 > 0) {
		row++;
	}
	init(row);
	var j = 0;
	var signs =$("#signs").val();
	for (var c = 1; c < count + 1; c++) {
		colum_name = eval("d" + begin + "r" + j);
		if (signs.indexOf(now.getMonth()+","+c)>=0) {
			colum_name.className = "arrive";
		}
		colum_name.innerText = c;
		begin++;
		if (begin == 7) {
			begin = 0;
			j++;
		}
	}
}

function init(row) {
	for (var j = 0; j < 6; j++) {
		if (j >= row) {
			colum_name = eval("r" + j).style.display = "none";
			continue;
		}
		for (var i = 0; i < 7; i++) {
			colum_name = eval("d" + i + "r" + j);
			colum_name.innerText = "";
			colum_name.style.backgroundColor = "";
			colum_name.style.color = "";
		}
	}
}
set_cal();
reShowTable();
