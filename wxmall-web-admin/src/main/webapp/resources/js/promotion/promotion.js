//取消批量打折
$("#quxiao_dazhe_a").click(function(){
	$("#bc-dazhe, #bc-jianjia").val("");
	$("#batch-jianjia,#batch-dazhe").hide();
});
//取消批量减价
$("#quxiao_jianjia_a").click(function(){
	$("#bc-dazhe, #bc-jianjia").val("");
	$("#batch-jianjia,#batch-dazhe").hide();
});
//批量打折
function confirmDazhe(){
	var zhekou = $("#bc-dazhe").val();
	$(".productSetLotGrid tr").each(function(){
		$(this).find("div.dazhe").find("input").val(zhekou);
		var price = $(this).attr("pp");
		setZhekou($(this), zhekou, price);
	});
}
//批量减价
function confirmJiage(){
	var jianjia = $("#bc-jianjia").val();
	$(".productSetLotGrid tr").each(function(){
		$(this).find("div.jianjia").find("input").val(jianjia);
		var price = $(this).attr("pp");
		setJianjia($(this), jianjia, price);
	});
}
function batchDazhe(){
	$("#batch-dazhe").show();
	$("#batch-jianjia").hide();
}
function batchJianjia(){
	$("#batch-dazhe").hide();
	$("#batch-jianjia").show();
}
function bindSetTableAEvent(){
	//注册取消打折商品事件
	$(".productSetLotGrid").find("tr a").each(function(){
		$(this).unbind("click");
		$(this).click(function(){
			var me = $(this);
			//取消设置不能直接删除该行，需要隐藏
			me.parent().parent().attr("opt", "del");
			me.parent().parent().hide();
			$("#checkbox_"+me.attr("id")).attr("checked", false);
			//$("#_sel_total_item").html(parseInt(TBatch.getCheckedCount()) + parseInt($("#_sel_total_item").text()));
			//添加到可选商品列表
			return false;
		});
	});
	//注册打折文本框的失去焦点事件
	$(".productSetLotGrid").find("div.dazhe").each(function(){
		$(this).find("input").unbind("blur");//先取消事件，再绑定，防止重复绑定事件
		$(this).find("input").blur(function(){
			var dazheValue = $(this).val();//设置的打折值
			if(dazheValue != ""){
				var trObj = $(this).parent().parent().parent();
				var price = trObj.attr("pp"); 
				setZhekou(trObj, dazheValue, price);
			}
			return false;
		});
	});
	//注册减价文本框失去焦点事件
	$(".productSetLotGrid").find("div.jianjia").each(function(){
		$(this).find("input").unbind("blur");//先取消事件，再绑定，防止重复绑定事件
		$(this).find("input").blur(function(){
			var jianjiaValue = $(this).val();
			if(jianjiaValue != ""){
				var trObj = $(this).parent().parent().parent();
				var price = trObj.attr("pp");
				setJianjia(trObj, jianjiaValue, price);
			}
			return false;
		});
	});
}
//设置折扣
function setZhekou(trObj, zhekou, price){
	dazheValue = zhekou / 10;
	var price = trObj.attr("pp"); 
	if(price.indexOf(" ~ ")>=0){
		price = price.split(" ~ ")[0]; //最小价格
	}
	trObj.attr("ptype", 1);
	trObj.find("div.jianjia").find("input").val((price - price * dazheValue).toFixed(2));
	trObj.find("div.set_result").find("input").val((price * dazheValue).toFixed(2));
	if(trObj.attr("ps_id") != null && trObj.attr("ps_id") != ""){
		trObj.attr("opt", "updated");
	}
}
//设置减价
function setJianjia(trObj, jianjiaValue, price){
	if(price.indexOf(" ~ ")>=0){
		price = price.split(" ~ ")[0]; //最小价格
	}
	trObj.attr("ptype", 2);
	trObj.find("div.dazhe").find("input").val(((price - jianjiaValue) / price * 10).toFixed(2));
	trObj.find("div.set_result").find("input").val((price - jianjiaValue).toFixed(2));
	if(trObj.attr("ps_id") != null && trObj.attr("ps_id") != ""){
		trObj.attr("opt", "updated");
	}
}
//选中商品
function selectItem(obj){
	$("#_sel_total_item").html(parseInt(TBatch.getCheckedCount()) + parseInt($("#_sel_total_item").text()));
	var productId = $(obj).attr("product_id");
	//check
	var hasItem = false;
	$(".productSetLotGrid tr").each(function(){
		if($(this).attr("prodId") == productId){
			hasItem = true;
			$(this).attr("opt", "");
			$(this).show();
			return;
		}
	});
	//如果设置列表中有了该商品就不再添加，
	//由于取消设置是隐藏该行，
	//并把该行数据的opt标志为“del”，
	//所以此处如果找到设置列表存在的话，就移除删除标志，并显示该行
	if(hasItem) {
		return;
	}
	var productName = $(obj).attr("product_name");
	var productImage = $(obj).attr("product_img");
	var productPrice = $(obj).attr("product_price");
	var product = {id:productId, name:productName, price:productPrice, img:productImage};
	var trHtml = obz.dataTemplate4obj($("#promotion_set_tr_tpl").html(), product);
	$(".productSetLotGrid").append(trHtml);
	bindSetTableAEvent();
}
//取消选中
function unSelectItem(obj){
	$("#_sel_total_item").html(parseInt(TBatch.getCheckedCount()) + parseInt($("#_sel_total_item").text()));
	$(".productSetLotGrid").find("tr.trcls_"+$(obj).attr("product_id")).hide();
}

//tab 切换事件
$("#select_item_tab li").find("a").click(function(){
	$("#select_item_tab li").each(function(){
		$(this).removeClass("active");
	});
	$("#select_items_div").removeClass("active");
	$("#promotion_set_div").removeClass("active");
	var me = $(this);
	me.parent().addClass("active");
	if(me.attr("id")=="select_item_a"){
		//获取商品列表
		$("#select_items_div").addClass("active");
	}else {
		//展示设置列表
		$("#promotion_set_div").addClass("active");
	}
});

$(document).ready(function(){
	bindSetTableAEvent();
});

var pageClick = function(pageNo) {
	searchProducts(pageNo);
}
var searchProducts = function(currPage){
	var params = {};
	//其他查询条件
	if(currPage){
		params.page = currPage;
	}
	//params.qname = qname;
	//params.type = $("#a_type_sel").val();
	//params.status = $("#a_status_sel").val();
	
	$("#mainTable").mask("加载中...");
	var url = obz.ctx + "/promotion/listProducts";
	obz.ajaxJson(url, params, function(resp){
		$("#mainTable").unmask();
		var result = resp.data;
		$(".productLotGrid").empty();
		if(currPage){
			$("#pager").pager({ pagenumber:currPage, recordcount:result.totalRow, pagesize:result.pageSize, recordtext:'共 {0} 页, {1} 条记录', buttonClickCallback: pageClick });
		}else{
			$("#pager").pager({ recordcount:result.totalRow, pagesize:result.pageSize, recordtext:'共 {0} 页, {1} 条记录', buttonClickCallback: pageClick });	
		}
		var dataList = result.list;
		if(dataList.length>0){
			for(var i=0;i<dataList.length;i++){
				var _row = dataList[i];
				var trHtml = obz.dataTemplate4obj($("#product_table_tr_tpl").html(), _row);
				$(".productLotGrid").append(trHtml);
			}
			TBatch.checkCheckbox(function (chkAll){
				$("#_sel_total_item").html(parseInt(TBatch.getCheckedCount()) + parseInt($("#_sel_total_item").text()));
				$("#mainTable input:checkbox.commchk").each(function(){
					if($(this).is(":checked") == true){
						selectItem($(this));
					}else{
						unSelectItem($(this));
					}
				});
			});
			TBatch.initCheckboxClick(function(resp){
				selectItem($(resp));
			}, function(resp){
				unSelectItem($(resp));
			});
		}else{
			$(".productLotGrid").append($("#table_noresult_tr_tpl").html());
		}
	});
}
searchProducts();
function savePromotion(){
	var params = {}, error = {};	
	var promotionId = $("#id").val(), 
	promotionName = $("#promotion_name").val(), promotionTag = $("#promotion_tag").val(),
		startDate = $("#start_date").val(), endDate = $("#end_date").val();
	if(promotionId!=null && promotionId!="") params.id = promotionId;
	if($.trim(promotionName) =="") {error.error_promotion_name = "折扣活动名称不能为空"; } else {error.error_promotion_name=""; params.promotion_name = $.trim(promotionName);}
	if($.trim(startDate) =="") {error.error_start_date = "开始时间不能为空"; } else {error.error_start_date =""; params.start_date = $.trim(startDate);}
	if($.trim(endDate) =="") {error.error_end_date = "结束时间不能为空"; } else { error.error_end_date=""; params.end_date = $.trim(endDate);}
	if($.trim(promotionTag) =="") {error.error_promotion_tag = "活动标签不能为空"; } else { error.error_promotion_tag=""; params.promotion_tag = $.trim(promotionTag);}
	
	var hasError = false;
	for(var key in error){
		if(error[key]!=""){
			if(!hasError) hasError = true;
			$("#"+key).addClass("has-error");
			$("#"+key).find("label").text(error[key]);
		}else{
			$("#"+key).removeClass("has-error");
			$("#"+key).find("label").empty();
		}
	}
	//主表信息不完整，返回
	if(hasError) return false;
	var promotionArray = new Array();
	$(".productSetLotGrid tr").each(function(){
		var entity = new Object();
		entity.productId = $(this).attr("prodId");
		entity.zhekou = $(this).find("div.dazhe").find("input").val();
		entity.jianjia = $(this).find("div.jianjia").find("input").val();
		entity.promotion = $(this).find("div.set_result").find("input").val();
		entity.ptype = $(this).attr("ptype");
		entity.opt = $(this).attr("opt");
		entity.psetId = $(this).attr("ps_id");
		promotionArray.push(entity);
	});
	
	if(promotionArray.length <=0){
		$("#zhek_err_msg").show();
		return false;
	}
	$("#zhek_err_msg").hide();
	params.zhekouItems = JSON.stringify(promotionArray);
	$(".main").mask();
	$.post(obz.ctx+"/promotion/save", params, function(resp) {
		$(".main").unmask();
		if(resp.code != 200){
			obz.showMessage(resp.msg);
			return;
		}
		
		obz.showMessage("保存成功", function(){
			location.href = obz.ctx + "/promotion";
		});
	});
}