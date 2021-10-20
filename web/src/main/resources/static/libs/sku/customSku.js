var customPropId = -1;//自定义属性类型ID
var customPropValId = -1;//自定义属性值id
$(function(){
	//克隆SKU模板生成自定义sku
	$(document).on("click" , ".cloneSku" , function(){
		var cloneSource = $("#skuCloneModel");//要克隆的sku模板
		var cloneNode = cloneSource.clone(true);//克隆出来的节点
		cloneNode.css("display","block");//显示元素
		//移除id
		cloneNode.removeAttr("id");
		customPropId -- ;
		//设置自定义属性类型主键
		$(cloneNode).find(".cusSkuTypeInput").parent().attr("propid",customPropId);
		//设置SKU类型主键 propid
		$(cloneNode).find(".cusSkuValInput").prev().attr("propid",customPropId);
		customPropValId -- ;
		//设置自定义属性类型值主键
		$(cloneNode).find(".cusSkuValInput").prev().attr("propvalid",customPropValId);
		$(this).before(cloneNode);//添加到该按钮的前面
	});
	
	//添加自定义sku值
	$(document).on("click",".cloneSkuVal",function(){
		//要克隆的SKU值模板
		var cloneSource = $("#onlySkuValCloneModel");
		//克隆出来的节点
		var cloneNode = cloneSource.clone(true);
		//移除id
		cloneNode.removeAttr("id");
		//获取并设置SKU类型主键
		var propid = $(this).parents("ul").prev().find("li").attr("propid");
		$(cloneNode).find(".cusSkuValInput").prev().attr("propid",propid);
		customPropValId -- ;
		//设置自定义属性类型值主键
		$(cloneNode).find(".cusSkuValInput").prev().attr("propvalid",customPropValId);
		//显示元素
		cloneNode.css("display","block");
		//添加到该按钮的前面
		$(this).before(cloneNode);
	});
	
	//SKU类型改变
	$(document).on("change", ".cusSkuTypeInput", function(){
		//判断当前的SKU类型是否已经存在
		var isHaveThisSkuType = false;
		var customSkuTypeName = $(this).val();
		if(customSkuTypeName){
			$("ul[class*='SKU_TYPE']").find("li").each(function(){
				var currSkuTypeName = $(this).attr("sku-type-name");//当前SKU类型名称
				if(currSkuTypeName == customSkuTypeName){//该SKU类型已经存在
					isHaveThisSkuType = true;
				}
			});
		}
		if(isHaveThisSkuType){
			layer.alert("该SKU类型已经存在!");
			$(this).val("");
		}
		//赋值类型属性
		$(this).parent().attr("sku-type-name",$(this).val());
		if(!$(this).val() || isHaveThisSkuType){
			$(this).parent().parent().next().find("input[type='checkbox'][class*='sku_value']").each(function(){
				//取消选中
				$(this).attr("checked",false)
				//移除class
				$(this).removeClass("sku_value");
			});
		}
		//触发change事件,重绘表格
		$(".model_sku_val").trigger("change");
	});
	
	//SKU值改变
	$(document).on("change", ".cusSkuValInput", function(){
		var isHaveSkuVal = false;//是否已经存在当前的SKU值
		var thisSkuVal = $(this).val();
		//判断SKU值是否已经重复了
		$(".model_sku_val,.sku_value").each(function(){
			var customSkuVal = $(this).val();//当前SKU值
			if(thisSkuVal == customSkuVal){
				isHaveSkuVal = true;
				return;
			}
		});
		
		if(isHaveSkuVal){
			layer.alert("该SKU类型已经存在!");
			$(this).val("");
		}
		$("input[type*='checkbox'][class*='']")
		var cusSkuVal = $(this).val();
		if(!cusSkuVal || isHaveSkuVal){
			//移除class
			$(this).prev().removeClass("sku_value");
			if($(this).prev().is(":checked")){
				//移除选中
				$(this).prev().attr("checked",false);
			}
		}
		//赋值类型属性
		$(this).prev().val(cusSkuVal);
		//触发change事件
		$(".model_sku_val").trigger("change");
	});
	
	//未设置sku值和属性的sku选择框改变事件
	$(document).on("change",".model_sku_val",function(){
		//SKU类型
		var skuTypeVal = $(this).parent().parent().prev().find("li").attr("sku-type-name");
		//是否设置了sku类型及sku值
		if(skuTypeVal && $(this).val()){
			//添加class
			$(this).addClass("sku_value");
		}
		//触发change事件,重绘表格
		$("input[type='checkbox']").first().trigger("change");
	});
	
	//删除自定义sku类型模块
	$(document).on("click",".delCusSkuType",function(){
		$(this).parent().parent().parent().remove();
		//触发change事件,重绘表格
		$("input[type='checkbox']").first().trigger("change");
	});
	
	//删除自定义sku值
	$(document).on("click",".delCusSkuVal",function(){
		$(this).parent().remove();
		//触发change事件,重绘表格
		$("input[type='checkbox']").first().trigger("change");
	});
})
