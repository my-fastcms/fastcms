//签到
$(document).ready(function(){
	var qiandaoDayList = [];
	function submit(){
		mask();
		$.post('/qiandao/submit',  {
					qiandaoId : $('#qiandao_id').val()
				}, 
		function(_json){
			unmask();
			if(_json.code != 200){
				$.alert(_json.msg);
				return;
			}
			var json = _json.data;
			if(json.hasPrize){
				$.alert("恭喜获得奖品：" + json.msg, function(){location.reload();});
			}else {
				showMsg("签到成功", function(){
					location.reload();
				});
			}
		},"json");
	}
	
	function getCalendarHtml(showDate){
		var year = showDate.getFullYear();
		var month = showDate.getMonth();
		var html = '';
		html += '<div class="header_info">';
		html += '<a class="pre_month" href="javascript:void(0)">'+year+'年'+(month+1)+'月</a>';
		html += '签到日历';
		html += '<a class="next_month" href="javascript:void(0)"></a>';
		html += '</div>';
		html += '<table cellspacing="1" cellpading="0">';
		html += '<tr><td>日</td><td>一</td><td>二</td><td>三</td><td>四</td><td>五</td><td>六</td></tr>';
		//这个月总共有多少天
		var totalDays = new Date(year,month+1,0).getDate();
		var count = 1;
		for(var i=1;i<=totalDays;i++){
			var date = new Date(year,month,i);
			if(count%7==1){
				html += '<tr>';
			}
			if(i==1){
				//第一天
				var day = date.getDay();
				if(day>0){
					for(var j=0;j<day;j++){
						count++;
						html += '<td>&nbsp;</td>';
					}
				}
			}
			if(isQiandao(i)){
				html += '<td class="checked">'+i+'</td>';
			}else{
				html += '<td>'+i+'</td>';
			}
			if(i==totalDays){
				//最后一天
				var day = date.getDay();
				if(day<6){
					for(var j=day;j<6;j++){
						html += '<td>&nbsp;</td>';
					}
				}
				if(count%7!=0){
					html += '</tr>';
				}
			}
			if(count%7==0){
				html += '</tr>';
			}
			count++;
		}
		html += '</table>';
		return html;
	}
	
	function isQiandao(day){
		for(var i=0;i<qiandaoDayList.length;i++){
			if(day==qiandaoDayList[i]){
				return true;
			}
		}
		return false;
	}
	
	function showCalendar(){
		$(".qiandao_time").each(function(){
			qiandaoDayList.push(parseInt($(this).val()));
		})
		/*var currentYear = parseInt($('#current_year').val());
		var currentMonth = parseInt($('#current_month').val())-1;
		var currentDay = parseInt($('#current_day').val());*/
		var currentDate = new Date();//new Date(currentYear,currentMonth,currentDay);
		$('.qiandao_calendar').html(getCalendarHtml(currentDate));
	}
	
	$('#submit_qd').on('click',function(){
		submit();
	});
	showCalendar();
	$(".next_month").on("click", function(){
		easyDialog.open({
			container : "rule",
			fixed : true,
			drag : true,
			overlay : true
		});
	});
});
