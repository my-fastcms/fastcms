//封装批量打印中相关JS API  wangjun
var TBatch = {
	
	//表格上显示订单详情
	toggleTradeDetail : function(){
		$("#mainTable tbody tr.package").each(function(){
			var me = $(this), nextTr = me.next(), lastTd = me.find("td:last");
			if(lastTd.html()!=''){
				lastTd.click(function(event){
					var event = event ? event : window.event;
					nextTr.toggle();
					event.stopPropagation();
					return false;
				});
			}
		});
	},	
		
	checkCheckbox : function (callback){
		if($("body").data("count")==undefined){
			$("body").data("count",0);
		}
		var length = $(":checkbox[class='nickName']").length;
		//全选功能
		var $all = $("#mainTable thead td:eq(1)").find(":checkbox");
		$all.mouseup(function(evt){
			var checked = this.checked;
			evt = evt ? evt : window.event;
			if(checked == true){
				$("body").data("count",0);
			}else{
				$("body").data("count",length);
			}
			$(":checkbox.nickName").each(function(){
				if(checked == true){
					this.checked = false;
					$(this).next().parent().parent().css({'background-color':'#E6FAB6'});
					$all[0].checked = true;
				}
				else{
					$(this).next().parent().parent().css({'background-color':'#D3E8FE'});
					this.checked = true;
				}
			});
			//计算并提示选择多少条订单
			TBatch.getCheckedCount();
			
			if(callback) callback();
			
			evt.stopPropagation();
			return false;
		});
	},
	
	getCheckedCount : function (){
		var count = 0;
		var length = $("#mainTable input:checkbox.nickName:checked").length;
		count = length;
		$("#checkedLines").html("您选择了<label style='font-size:16px;color:#f00;font-weight:bold;margin:0 3px;'>"+count+"</label>单").css({'margin-left':'20px'});
	},
	
	initCheckboxClick : function (){
		if($("body").data("count")==undefined){
			$("body").data("count",0);
		}
		var length = $(":checkbox[class='nickName']").length;
		$(":checkbox.nickName").each(function(){
			$(this).click(function(){
				if(this.checked){
					$checkbox.next().parent().parent().css({'background-color':'#D3E8FE'});
				}
				else{
					$checkbox.next().parent().parent().css({'background-color':'#E6FAB6'});
				}
				TBatch.getCheckedCount();
				if($(":checkbox.nickName:not(:checked)").length == 0){
					$("#all")[0].checked = true;
				}
				else{
					$("#all")[0].checked = false;
				}
			});
			var $checkbox = $(this);
			var $label = $(this).next();
			/*
			$label.hover(function(){
				$(this).css({'text-decoration':'underline',cursor:'pointer'});
			},function(){
				$(this).css({'text-decoration':'none',cursor:'pointer'});
			});
			*/
			$label.click(function(evt){
				evt = evt ? evt : window.event;
				//this.checked = !this.checked;
				if($checkbox[0].checked){
					$checkbox[0].checked = false;
					$checkbox.next().parent().parent().css({'background-color':'#E6FAB6'});
					
					var count = $("body").data("count");
					count = count -1;
					$("body").data("count",count);
					if(count < length){
						$("#all")[0].checked = false;
					}
				}
				else{
					$checkbox[0].checked = true;
					$checkbox.next().parent().parent().css({'background-color':'#D3E8FE'});
					var count = $("body").data("count");
					count = count+1;
					$("body").data("count",count);
					if(count == length){
						$("#all")[0].checked = true;
					}
				}
				
				TBatch.getCheckedCount();
				evt.stopPropagation();
				return false;
			});
		});
	}
	
};