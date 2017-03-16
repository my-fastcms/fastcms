//点步科技
//封装JQ table 相关JS API  wangjun
var TBatch = {
	tableId : "mainTable",	
	checkCheckbox : function (callBack){
		if($("body").data("count")==undefined){
			$("body").data("count",0);
		}
		var length = $("#"+TBatch.tableId+" input:checkbox[class='commchk']").length;
		//全选功能
		var $all = $("#"+TBatch.tableId+" thead tr th:eq(0)").find(":checkbox");
		$all.mouseup(function(evt){
			var checked = this.checked;
			evt = evt ? evt : window.event;
			if(checked == true){
				$("body").data("count",0);
			}else{
				$("body").data("count",length);
			}
			$("#"+TBatch.tableId+" input:checkbox.commchk").each(function(){
				if(checked == true){
					this.checked = false;
					$(this).parent().parent().css({'background-color':'#f9f9f9'});					
					$all[0].checked = true;
				} else{
					$(this).parent().parent().css({'background-color':'#E6FAB6'});
					this.checked = true;
				}
			});
			TBatch.getCheckedCount();
			
			if(callBack) callBack(this);
			evt.stopPropagation();
			return false;
		});
	},
	
	getCheckedCount : function (){
		var count = $("#"+TBatch.tableId+" input:checkbox.commchk:checked").length;
		$("body").data("count", count);
		return count;
	},
	
	getChecked : function(){
		var idArray = new Array();
		$("#"+TBatch.tableId+" input:checkbox.commchk:checked").each(function(){
			idArray.push($(this).attr("id").split("_")[1]);
		});
		var ids = "";
		for(var i=0;i<idArray.length;i++){
			ids += idArray[i] + "-";
		}
		return ids;
	},
	
	initCheckboxClick : function (checkedCallBack, cancelCallBack){
		if($("body").data("count")==undefined){
			$("body").data("count",0);
		}
		var length = $(":checkbox[class='commchk']").length;
		$(":checkbox.commchk").each(function(){
			$(this).click(function(){
				if(this.checked){
					$(this).parent().parent().css({'background-color':'#E6FAB6'});
					if(checkedCallBack) checkedCallBack(this);
				} else{
					$(this).parent().parent().css({'background-color':'#f9f9f9'});
					if(checkedCallBack) cancelCallBack(this);
				}
				
				TBatch.getCheckedCount();
				if($(":checkbox.commchk:not(:checked)").length == 0){
					$("#all")[0].checked = true;
				}
				else{
					$("#all")[0].checked = false;
				}
			});
		});
	},
	
	//初始化表格的操作事件
	//callback 为 array类型
	initOptEvent : function(callback){
		$("#"+TBatch.tableId+" tbody tr.package").each(function(){
			$(this).find("td:last").find("a").each(function(i){
				$(this).click(function(){
					if(callback) {
						var params = $(this).attr("id").split("_");
						callback[i](params);
					}
				});
			});
		});
		
//		$("#"+tableId+" tbody tr.package").find("td:last").find("a").each(function(i){
//			$(this).click(function(){
//				if(callback) {
//					var params = $(this).attr("id").split("_");
//					callback[i](params);
//				}
//			});
//		});
	}
	
};