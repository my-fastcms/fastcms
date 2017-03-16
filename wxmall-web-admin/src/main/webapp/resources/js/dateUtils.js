/**
 * 时间工具类
 */
function dateTag(day) {
    if ($("#start_date").val() == "") {
        $("#start_date").val(new Date().format_dbu("yyyy-MM-dd"));
    }
    var da = $("#start_date").val().split("-");
    var begtime_ms = new Date(da[0], da[1] - 1, da[2]);
    $("#end_date").val(begtime_ms.addDay_dbu(day).format_dbu("yyyy-MM-dd"));
    //startTimeChange($("#start_date").val());
    //endTimeChange($("#end_date").val());
}

function dateTagBf(day) {
    if ($("#end_date").val() == "") {
        $("#end_date").val(new Date().format_dbu("yyyy-MM-dd"));
    }
    var da = $("#end_date").val().split("-");
    var begtime_ms = new Date(da[0], da[1] - 1, da[2]);
    $("#start_date").val(begtime_ms.addDay_dbu(day).format_dbu("yyyy-MM-dd"));
    //startTimeChange($("#start_date").val());
    //endTimeChange($("#end_date").val());
}

function dateTimeTag(day) {
    if ($("#start_date").val() == "") {
        $("#start_date").val(new Date().format_dbu("yyyy-MM-dd hh:mm:ss"));
    }
    var daArr = $("#start_date").val().split(" ");
    var da = daArr[0].split("-"); 
    var ta = daArr[1].split(":");
    var begtime_ms = new Date(da[0], da[1] - 1, da[2], ta[0], ta[1], ta[2]);
    $("#end_date").val(begtime_ms.addDay_dbu(day).format_dbu("yyyy-MM-dd hh:mm:ss"));
    //startTimeChange($("#start_date").val());
    //endTimeChange($("#end_date").val());
}

//日期改变
function startTimeChange(startTime) {
	if(startTime !=""){
		var da_1 = new Date().format_dbu("yyyy-MM-dd").split("-");
		var da_2 = startTime.split("-");
		var curenttime_ms = new Date(da_1[0], da_1[1] - 1, da_1[2]);
		var begtime_ms = new Date(da_2[0], da_2[1] - 1, da_2[2]);
	    var strDatadiff = "";
	    if (begtime_ms <= curenttime_ms) {
	        $("#spanstart").html("开始时间小于当前时间，活动立即开始");
	    } else {
	        strDatadiff = getdateDiff(curenttime_ms, begtime_ms);
	        $("#spanstart").html("距离活动开始还有  <span style='color:#ff7300'>" + strDatadiff + '</span>');
	    }
	    var da_3 = $("#end_date").val().split("-");
	    var endtime_ms = new Date(da_3[0], da_3[1] - 1, da_3[2]);
	    strDatadiff2 = getdateDiff(begtime_ms, endtime_ms);
	    if(strDatadiff2 != ""){
		    $("#spanend").html("活动持续 <span style='color:#ff7300'>" + strDatadiff2 + '</span>');
	    }
	} else {
        $("#spanstart").html("自定义开始时间");
        $("#spanend").html("自定义结束时间");
	}
}
//日期改变
function endTimeChange(endTime) {
	if(endTime != ""){
		var da = new Date().format_dbu("yyyy-MM-dd").split("-");
		var curenttime_ms = new Date(da[0], da[1] - 1, da[2], da[3], da[4], "00");
		var da_1 = $("#start_date").val().split("-");
		var da_2 = endTime.split("-");
		var begtime_ms = new Date(da_1[0], da_1[1] - 1, da_1[2]);
		var endtime_ms = new Date(da_2[0], da_2[1] - 1, da_2[2]);
	    if (endtime_ms <= curenttime_ms) {
	        $("#spanstart").html("活动已经结束");
	        $("#spanend").html("活动已经结束");
	        return;
	    }
	    strDatadiff = getdateDiff(begtime_ms, endtime_ms);
	    if(strDatadiff != ""){
		    $("#spanend").html("活动持续  <span style='color:#ff7300'>" + strDatadiff + '</span>');
	    }
	} else {
        $("#spanend").html("自定义结束时间");
	}
}

//计算日期差
function getdateDiff(startdate, enddate) {
    var strDatadiff = "";
    var datediff = enddate.getTime() - startdate.getTime();
    //计算出相差天数  
    var days = Math.floor(datediff / (24 * 3600 * 1000));
    //计算出小时数  
    var leave1 = datediff % (24 * 3600 * 1000);    //计算天数后剩余的毫秒数  
    var hours = Math.floor(leave1 / (3600 * 1000));
    //计算相差分钟数  
    var leave2 = leave1 % (3600 * 1000);        //计算小时数后剩余的毫秒数  
    var minutes = Math.floor(leave2 / (60 * 1000));
    if (days > 0) {
        strDatadiff += days + "天";
    }
    if (hours > 0) {
        strDatadiff += hours + "小时";
    }
    if (minutes > 0) {
        strDatadiff += minutes + "分";
    }
    return strDatadiff;
}
Date.prototype.format_dbu = function(format){ 
	var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"h+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond 
	}; 
	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 
	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	} 
	return format; 
};

Date.prototype.addDay_dbu = function(day){
	return new Date(Date.parse(this) + (86400000 * day));
};
