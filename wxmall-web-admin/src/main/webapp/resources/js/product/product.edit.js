var isOnly="0";
function addPicture(){
	isOnly="0";
	listImage();
	$("#img_list_dialog").modal("show");
		}
		
function addPictureOnly(){
	isOnly="1";
	listImage();
	$("#img_list_dialog").modal("show");
}
function nextStepTwo(){
	if(catagory == ''){
		$("#assign_error_msg").html("必须选择一个商品类目。");
		return false;
	}
	//alert(catagory);
	$('#myTab a[href="#js-step-2"]').tab('show')
}	

function nextStepThree(){
	clearError();
	var type = $("input[name='is_unified_spec']:checked").val();
	var hasErr = false;
	var hasError = false;
	var params = {}, error = {};
	
	var productName = $("#product_name").val();
	if($.trim(productName) =="") {error.error_product_name = "商品名不能为空"; } else {error.error_product_name=""; params.product_name = $.trim(productName);}
	if(type =="true"){
		var productPrice = $("#product_price").val();
		if($.trim(productPrice) =="") {error.error_product_price = "商品价格不能为空"; } else {error.error_product_price =""; params.product_price = $.trim(productPrice);}	
	}
	for(var key in error){
		if(error[key]!=""){
			//alert(key);
			if(!hasError) hasError = true;
			$("#"+key).addClass("has-error");
			$("#"+key).find("label").text(error[key]);
		}else{
			$("#"+key).removeClass("has-error");
			$("#"+key).find("label").empty();
		}
	}
	if($(".js-picture-lists li").length<=1){
		if(!hasErr) hasErr=true;
		$("#image_error_msg").html("商品图至少有一张。");
		//return;
	}
	if($(".js-picture-listss li").length<=1){
		if(!hasErr) hasErr=true;
		$("#only_error_msg").html("商品展示图至少有一张。");
		//return;
	}
	if($(".js-picture-listss li").length>2){
		if(!hasErr) hasErr=true;
		$("#only_error_msg").html("商品展示图只能有一张。");
		//return;
	}
	
	if($("#specificationSelect input[type=checkbox]:checked").length>=1){
		if($("ul.Item" + " input[type=checkbox]:checked").length<=0){
			if(!hasErr) hasErr=true;
			$("#specificationValue_error_msg").html("商品规格值至少有一个。");
		}
	}
	 
	if($("#bodyss tr").length>=1){
 	$("#bodyss tr").each(function(i,obj){
 		var price_input = $(obj).find("input[name='price_s']");
 		if(price_input.val()==""){	
 			if(!hasErr) hasErr=true;
 			price_input.css("border-color", "#ff5454");
 			$(obj).find(".price_error_msg").html("价格不能为空");
			//return ;
		}
 		var stock_input = $(obj).find("input[name='stock_s']");		
 		if(stock_input.val()==""){
 			if(!hasErr) hasErr=true;
 			stock_input.css("border-color", "#ff5454");
 			$(obj).find(".stock_error_msg").html("库存不能为空");
			//return ;
		}
 				
	}); 
	}
	
	var p1=$('#delivery_memo').children('option:selected').val();
	var type = $("input[name='is_unified_spec']:checked").val();
	var valuation_value="0";
	 $.post(obz.ctx+"/delivery/getDeliveryMemoName", {id:p1}, function(json) {
		 var dataList = json;
			if(dataList.length>0){
				var valuation_type=dataList[0].valuation_type;
				if(valuation_type=="2"&&type=="false"){
					if($("#bodyWeight tr").length>=1){
					 	$("#bodyWeight tr").each(function(i,obj){
					 		var weight_input = $(obj).find("input[name='weight_s']");
					 		if(weight_input.val()==""){	
					 			valuation_value="1";
					 			weight_input.css("border-color", "#ff5454");
					 			$(obj).find(".weight_error_msg").html("重量不能为空");
							}
					 			
						}); 
						}
				}else if(valuation_type=="2"&&type=="true"){
					var delivery_weight=$("input[name='delivery_weight']");
					if(delivery_weight.val()==""){
						valuation_value="1";
					  delivery_weight.css("border-color", "#ff5454");
					  $(".delivery_weight_error_msg").html("物流重量不能为空");

					}
				}	
			}	
	 });
	
	
	var delivery_type=$("input[name='delivery_type']:checked").val();
	if(delivery_type=="0"){
		var delivery_fees=$("input[name='delivery_fees']");
		if(delivery_fees.val()==""){
		  if(!hasErr) hasErr=true;
		  delivery_fees.css("border-color", "#ff5454");
		  $(".delivery_fees_error_msg").html("邮费不能为空");
		}
	}else{
		
		var delivery_memo=$("#delivery_memo option:selected").val();
		if(delivery_memo==""||delivery_memo=="0"){
			if(!hasErr) hasErr=true;
			$(".delivery_template_error_msg").html("请选择运费模版");
		}
		
	}
	
	if(hasErr){
		return false;
	}
	
	if(hasError){
		return false;
	}
	$('#myTab a[href="#js-step-3"]').tab('show')
}

function lastStepTwo(){
	$('#myTab a[href="#js-step-2"]').tab('show')
}

function lastStepFirst(){
	$('#myTab a[href="#js-step-1"]').tab('show')
}
var listImg="";	
var listImgUrl="";
var listUpload="";
var catagory="";
var selectImg = new Array();
$(document).ready(function() {
	$('#myTab a[href="#js-step-2"]').tab('show')
	catagory="${product.product_category_id}";
	$(".widget-goods-klass .widget-goods-klass-item").click(function(){
		catagory=""
		$("#productCategoryName").val();
		$(".widget-goods-klass .widget-goods-klass-item").each(function(){
			$(this).removeClass("current");
		});
		$(this).addClass("current");
		//alert($(this).attr("category-id"));
		//alert($(this).attr("category-name"));
		
		catagory=$(this).attr("category-id");
		$("#productCategoryId").val(catagory);
		$("#productCategoryName").html($(this).attr("category-name"));
		$("#assign_error_msg").html("");
		return false;
	});
	
	
	$('#img_list_dialog').on('hidden.bs.modal', function () {
		selectImg.length=0;
		listImg="";
		$("ul.selectMonth li").each(function(){					    	
	    	$(this).removeClass("active");
	    });
		$("#saveImg").addClass("disabled");
		$(".selected-count-region").addClass("hidden");
		// return false;
	});

	$('#img_upload_dialog').on('hidden.bs.modal', function () {
		$(".js-picture-list").empty();
		 return false;
	});
	
	$(".add-local-image-button").click(function(){
		 $("#img_list_dialog").modal("hide");
		 $("#img_upload_dialog").modal("show");
	});
	
	
	
	 filer_default_opts = {
	            changeInput2: '<div class="jFiler-input-dragDrop"><div class="jFiler-input-inner"><div class="jFiler-input-icon">\
	            <i class="icon-jfi-cloud-up-o"></i></div>\
	            <a class="jFiler-input-choose-btn blue-light">选 择 文 件</a></div></div>',
	            limit: null,
	            extensions: ['jpg', 'jpeg', 'png', 'gif'],
	            templates: {
	                box: '<ul class="js-picture-list app-image-list clearfix"></ul>',
	                item: '<li class="sort">\
	                                        <div class="jFiler-item-info hide">\
	                                            <span class="jFiler-item-title"><b title="{{fi-name}}">{{fi-name | limitTo: 25}}</b></span>\
	                                            <span class="jFiler-item-others">{{fi-size2}}</span>\
	                                        </div>\
	                                        {{fi-image}}\
	                                        {{fi-progressBar}}\
	                                        <a class="js-delete-picture close-modal small">×</a>\
	                        </li>',
	                itemAppend:'<li class="sort">\
					                        <div class="jFiler-item-info hide">\
					                        <span class="jFiler-item-title"><b title="{{fi-name}}">{{fi-name | limitTo: 25}}</b></span>\
					                        <span class="jFiler-item-others">{{fi-size2}}</span>\
					                    </div>\
					                    {{fi-image}}\
					                    <a class="js-delete-picture close-modal small">×</a>\
                        </li>',
                progressBar: '<div class="bar"></div>',
	                itemAppendToEnd: false,
	                removeConfirmation: false,
	                _selectors: {
	                    list: '.js-picture-list',
	                    item: '.sort',
	                    remove: '.js-delete-picture'
	                }
	            },
	            uploadFile: {
	                url: "${webctx}/product/upload",
	                data: null,
	                type: 'POST',
	                enctype: 'multipart/form-data',
	                beforeSend: function(){},
	                success: function(data, el){
	                 /*    var parent = el.find(".jFiler-jProgressBar").parent();
	                    el.find(".jFiler-jProgressBar").fadeOut("slow", function(){
	                        $("<div class=\"jFiler-item-others text-success\"><i class=\"icon-jfi-check-circle\"></i> Success</div>").hide().appendTo(parent).fadeIn("slow");
	                    }); */
	                    var json=eval(data);  
	                    $.each(json, function(key,item){
	                    	$.each(item, function(key1,item1){
	                    		if(isOnly=="1"){
	                    			 var reValue=$(".js-picture-listss li").find("input[name='image']").val();
		                 				// alert(reValue);
		                 				  if (typeof(reValue)== "undefined"){
		                 					  $(".js-picture-listss").append('<li class="sort"><img src="'+item1.filePath+'" id="imgId"  class="js-img-preview">'
		                 								 +'<input name="image" value="'+item1.fileUrl+'" type="hidden"  class="js-img-preview">'
		                 								 +'<a class="js-delete-picture close-modal small hide" onclick="deleteImg(this);">×</a></li>');
		                 				  }else{
		                 					  $("#imgId").attr('src',item1.filePath);
		                 					  $(".js-picture-listss li").find("input[name='image']").val(item1.fileUrl);
		                 				  }
	                    		}else{
	                    		 $(".js-picture-lists").append('<li class="sort"><img src="'+item1.filePath+'"  class="js-img-preview">'
	                    				 +'<input name="imgList" value="'+item1.fileUrl+'" type="hidden"  class="js-img-preview">'
	                					 +'<a class="js-delete-picture close-modal small hide" onclick="deleteImg(this);">×</a></li>');
	                    		}
	                    	});
	                    });
	                    $(".col-md-5 ul>li").hover(function(){
	        				$(this).find("a").removeClass("hide");
	        			   $(this).find("a").addClass("hover");
	        			},function(){
	        				 $(this).find("a").addClass("hide");  
	        				 $(this).find("a").removeClass("hover");
	        			});	
	                    $("#img_upload_dialog").modal("hide");
	                    console.log(data);
	                },
	                error: function(el){
	                  /*   var parent = el.find(".jFiler-jProgressBar").parent();
	                    el.find(".jFiler-jProgressBar").fadeOut("slow", function(){
	                        $("<div class=\"jFiler-item-others text-error\"><i class=\"icon-jfi-minus-circle\"></i> Error</div>").hide().appendTo(parent).fadeIn("slow");    
	                    }); */
	                },
	                statusCode: true,
	                onProgress: true,
	                onComplete: null
	            },
	            onRemove: function(itemEl, file, id, listEl, boxEl, newInputEl, inputEl){
	                var file = file.name;
	              //  $.post('./php/remove_file.php', {file: file});
	            },
	        };
	 
	 $('#demo-fileInput-6').filer({
	        changeInput: filer_default_opts.changeInput2,
	        showThumbs: true,
	        theme: "dragdropbox",
	        templates: filer_default_opts.templates,
	        uploadFile: filer_default_opts.uploadFile,
	        onRemove: filer_default_opts.onRemove
	    });
	 
	 $("#uploadImage").click(function(){
		 $("#img_list_dialog").modal("hide");
		 $("#img_upload_dialog").modal("show");
	 });
	 
	 $("#selectImage").click(function(){
		 listImage();
		 $("#img_upload_dialog").modal("hide");
		 $("#img_list_dialog").modal("show");	 
	 });
	
	 
});

function prices(){
	$("#js-price").show();
	$(".js-batch-type").hide();
}

function stocks(){
	$("#js-stock").show();
	$("#js-price").hide();
	$(".js-batch-type").hide();
}
function cancel(){
	$(".js-batch-type").show();
	$("#js-price").hide();
	$("#js-stock").hide();
}

function pricesSave(){
	
	var priceId=$("#priceId").val();
	 if(priceId !=''){
		// alert(priceId);
		 $("input[name='price_s']").empty();
		 $("input[name='price_s']").val(priceId);
	$(".js-batch-type").show();
	$("#js-price").hide();
	$("#js-stock").hide();
	 }else{
		 //$.message("warn", "请输入价格值！");
		 alert("请输入价格值！");
	    	return false; 
	 }
}

function stocksSave(){
	var stockId=$("#stockId").val();
    if(stockId !=''){
    	
    $("input[name='stock_s']").empty();
    $("input[name='stock_s']").val(stockId);	
	$(".js-batch-type").show();
	$("#js-price").hide();
	$("#js-stock").hide();
    }else{
    	//$.message("warn", "请输入库存值！");
    	alert("请输入库存值！");
    	return false;
    }
}

function checkNum(obj) {  
    if (isNaN(obj.value)) {  
        obj.value = "";  
    }  
    if (obj != null) {  
        if (obj.value.toString().split(".").length > 1 && obj.value.toString().split(".")[1].length > 2) {  
            obj.value = "";  
        }  
    }  
} 
var step ="";
var items = new Array();
var params = {};
var itemsAll = new Array();
function saveTask(){
	 var trList = $("#bodyss").children("tr");
	 var trListWeight = $("#bodyWeight").children("tr");
	 $.each(items, function (index, item) {
		 var tdArr = trList.eq(index).find("td");
		 var tdArrWeight = trListWeight.eq(index).find("td");
		 var price = tdArr.eq(tdArr.length-2).find("input").val();//价格
	     var stock = tdArr.eq(tdArr.length-1).find("input").val();//库存
	     var weight= tdArrWeight.eq(tdArrWeight.length-1).find("input").val();//重量
		 var entity = new Object();
		    entity.specificationValue=item;
			entity.price = price;
			entity.stock = stock;
			entity.weight= weight;
			itemsAll.push(entity);	 
	 }); 		 
	 params.items =  JSON.stringify(itemsAll);
$("#allStocks").val(params.items);
}
$(function () {	
	listImage();
	
	 $("#saveImg").click(function(){
		 $("#img_list_dialog").modal("hide");		 
		 var newlistImg=listImg.split(",");
		 var newlistImgUrl=listImgUrl.split(",");
		 for (var i = 0; i < newlistImg.length; i++) {
			 if(isOnly=="1"){
				 var reValue=$(".js-picture-listss li").find("input[name='image']").val();
					// alert(reValue);
					  if (typeof(reValue)== "undefined"){
						  $(".js-picture-listss").append('<li class="sort"><img src="'+newlistImg[i]+'" id="imgId"  class="js-img-preview">'
									 +'<input name="image" value="'+newlistImgUrl[i]+'" type="hidden"  class="js-img-preview">'
									 +'<a class="js-delete-picture close-modal small hide" onclick="deleteImg(this);">×</a></li>');
					  }else{
						  $("#imgId").attr('src',newlistImg[i]);
						  $(".js-picture-listss li").find("input[name='image']").val(newlistImgUrl[i]);
					  }
			 }else{
			 $(".js-picture-lists").append('<li class="sort"><img src="'+newlistImg[i]+'"  class="js-img-preview">'
					 +'<input name="imgList" value="'+newlistImgUrl[i]+'" type="hidden"  class="js-img-preview">'
					 +'<a class="js-delete-picture close-modal small hide" onclick="deleteImg(this);">×</a></li>');
		          }
		 }
		 $(".col-md-5 ul>li").hover(function(){
				$(this).find("a").removeClass("hide");
			   $(this).find("a").addClass("hover");
			},function(){
				 $(this).find("a").addClass("hide");  
				 $(this).find("a").removeClass("hover");
			});	
	 });
	 
	 
	 $("#specificationSelect :checkbox").click(function(){		
	     var code_Values =$("#specificationSelect input[type=checkbox]:checked");      
			if(code_Values.length<=3){
			var $this = $(this);
			if ($this.prop("checked")) {
				$("#specificationProductTable").find("tr.specification_" + $this.val()).removeClass("hidden");
				$("#Title_"+$this.val()).addClass("Father_Title");
			} else {
				$("#specificationProductTable").find("tr.specification_" + $this.val()).addClass("hidden");
			 	$("#Item_"+$this.val() +" li").find("label").find("input[type=checkbox]").each(function(i,n){
			 	   $(n).prop('checked',false);
				});	   
			 	if(code_Values.length==0){
			 		step.Creat_Table();	
			 		$("#Title_"+$this.val()).removeClass();
					
			 	}else{
				$("#Title_"+$this.val()).removeClass();
				step.Creat_Table();	
			 	}
			}	
			}else{
				//$.message("warn", "只能选择三个规格");
				alert("只能选择三个规格！");
				$(this).prop('checked',false);
				
				return false;
				
			}	
			
		});
	 
	 
	 $("#specificationProductTable label").bind("change", function () {
	        step.Creat_Table();
	        getPrice();
	        //sumNum();
	    });
     step = {
        Creat_Table: function () {
            step.hebingFunction();
            var SKUObj = $(".Father_Title");
            //var skuCount = SKUObj.length;//
            var arrayTile = new Array();//标题组数
            var arrayInfor = new Array();//盛放每组选中的CheckBox值的对象
            var arrayInforId = new Array();//盛放每组选中的CheckBox id值的对象
            var arrayColumn = new Array();//指定列，用来合并哪些列
            var bCheck = true;//是否全选
            var columnIndex = 0;
            var s="";
            $.each(SKUObj, function (i, item){
            	 arrayColumn.push(columnIndex);
                 columnIndex++;
                 arrayTile.push(SKUObj.find("li").eq(i).html().replace("：", ""));
            	s+=$(item).attr("id")+",";
            });
            var newstr=s.substring(0,s.length-1);
            var td_array = newstr.split(",");
            
            $.each(td_array, function (i, values) {
            	
            	 var itemName =values;
                 //选中的CHeckBox取值
                 var order = new Array();
                 var orderId = new Array();
                 $("." + itemName + " input[type=checkbox]:checked").each(function (){
                 /* 	var orderObj = new Object();
                 	orderObj.id = $(this).val();
                 	orderObj.vaule = $(this).attr("data-val");  */
                     order.push($(this).attr("data-val"));
                     orderId.push($(this).attr("data-value"));
                 	//order.push(orderObj);
                 });
                 arrayInforId.push(orderId);
                 arrayInfor.push(order);
                 if (order.join() == ""){
                     bCheck = false;
                 }
            	//alert(values);
            });
            $.each(SKUObj, function (i, item){
            });
            
            if (arrayInfor.length <=0){
                bCheck = false;
            }
            //开始创建Table表
            if (bCheck == true) {
                var RowsCount = 0;
              //创建重量表
                $("#createdWeight").html("");
                var tableWeight = $("<table id=\"weightTable\" class=\"table table-striped table-bordered\"></table>");
                tableWeight.appendTo($("#createdWeight"));
                var theadWeight = $("<thead align=\"left\"></thead>");
                theadWeight.appendTo(tableWeight);
                var trHeadWeight = $("<tr></tr>");
                trHeadWeight.appendTo(theadWeight);
              //创建表头
                $.each(arrayTile, function (index, item) {
                    var tdWeight = $("<th>" + item + "</th>");
                    tdWeight.appendTo(trHeadWeight);
                });
                var itemColumHeadWeight = $("<th  style=\"width:30%;\">重量(Kg)</th> ");
                itemColumHeadWeight.appendTo(trHeadWeight);
                var tbodyWeight = $("<tbody id=\"bodyWeight\"></tbody>");
                tbodyWeight.appendTo(tableWeight);
                //创建规格表
                $("#createTable").html("");
                var table = $("<table id=\"process\" class=\"table table-striped table-bordered\"></table>");
                table.appendTo($("#createTable"));
                var thead = $("<thead></thead>");
                thead.appendTo(table);
                var trHead = $("<tr></tr>");
                trHead.appendTo(thead);
                //创建表头
                $.each(arrayTile, function (index, item) {
                    var td = $("<th>" + item + "</th>");
                    td.appendTo(trHead);
                });
                var itemColumHead = $("<th  style=\"width:70px;\">价格（￥）</th><th style=\"width:70px;\">库存</th> ");
                itemColumHead.appendTo(trHead);
                var tbody = $("<tbody id=\"bodyss\"></tbody>");
                var tfoot=$("<tfoot></tfoot>");
                var tfootColum=$("<div class=\"batch-opts\"> 批量设置：<span class=\"js-batch-type\" style=\"display: inline;\">"
                        +"<a id=\"js-batch-price\" href=\"javascript:;\" onclick=\"prices();\">价格</a>&nbsp;&nbsp;<a class=\"js-batch-stock\" href=\"javascript:;\" onclick=\"stocks();\">库存</a></span>"
                       +"<span id=\"js-stock\" class=\"js-batch-form\" style=\"display: none;\">"
                       +"<input class=\"js-batch-txt\"  id=\"stockId\" placeholder=\"请输入库存\" maxlength=\"9\" type=\"text\" onkeyup=\"checkNum(this)\">"
                       +"<a class=\"js-batch-save\" href=\"javascript:;\" onclick=\"stocksSave();\">保存</a>"
                       +"<a class=\"js-batch-cancel\" href=\"javascript:;\" onclick=\"cancel();\">取消</a>"
                       +"<p class=\"help-desc\"></p></span>"
                       +"<span id=\"js-price\" class=\"js-batch-form\" style=\"display: none;\">"
                       +"<input class=\"js-batch-txt\" id=\"priceId\" placeholder=\"请输入价格\" maxlength=\"9\" type=\"text\" onkeyup=\"checkNum(this)\">"
                       +"<a class=\"js-batch-save\" href=\"javascript:;\" onclick=\"pricesSave();\">保存</a>"
                       +"<a class=\"js-batch-cancel\" href=\"javascript:;\" onclick=\"cancel();\">取消</a>"
                       +"<p class=\"help-desc\"></p></span>"
                       +"</div>");
                 /*        +"<tr> <th><span class=\"requiredField\">*</span>总库存:</th>"
                        +"  <td><input type=\"text\" id=\"allStock\" name=\"product.stock\" class=\"text\" maxlength=\"9\"  disabled=\"disabled\"/></td>"
                        +"</tr>" */
                tfootColum.appendTo(tfoot);
                tfoot.appendTo(table);
                tbody.appendTo(table);
               
                ////生成组合
                var zuheId=step.doExchange(arrayInforId);
                if (zuheId.length > 0) {
                	items.length=0;
               // alert("组合："+zuheId.length);
                $.each(zuheId, function (index, item) {
                   // var td_array = item.split(",");
                   // alert(td_array);
                    items.push(item);
                });
               // alert(items.length);
                }
                
                var zuheDate = step.doExchange(arrayInfor);
              //物流重量
                if (zuheDate.length > 0) {
                    //创建行
                    $.each(zuheDate, function (index, item) {
                        var td_array = item.split(",");
                        var trWeight = $("<tr></tr>");
                        trWeight.appendTo(tbodyWeight);
                        $.each(td_array, function (i, values) {
                            var tdWeight = $("<td>" + values + "</td>");
                            tdWeight.appendTo(trWeight);
                        });
                        var td1 = $("<td class=\"col-md-2\"><span class=\"requiredField\"></span><input id=\"weightId"+index+"\" name=\"weight_s\" class=\"weights form-control\" type=\"text\" value=\"\" onkeyup=\"checkNum(this)\"><label class=\"weight_error_msg\" style=\"color:red;\"></label></td>");
                        td1.appendTo(trWeight);
                    });
                }
                //规格
                if (zuheDate.length > 0) {
                    //创建行
                    var size;
                    $.each(zuheDate, function (index, item) {
                        var td_array = item.split(",");
                        var tr = $("<tr></tr>");
                        tr.appendTo(tbody);
                        $.each(td_array, function (i, values) {
                            var td = $("<td>" + values + "</td>");
                            td.appendTo(tr);
                        });
                        var td1 = $("<td class=\"col-md-2\"><span  class=\"requiredField\"></span><input id=\"priceId"+index+"\" name=\"price_s\" class=\"price form-control\" type=\"text\" value=\"${stock.price}\" onkeyup=\"checkNum(this)\"><label class=\"price_error_msg\" style=\"color:red;\"></label></td>");
                        td1.appendTo(tr);
                        var td2 = $("<td class=\"col-md-2\"><span class=\"requiredField\"></span><input id=\"stockId"+index+"\" name=\"stock_s\" class=\"stocks form-control\" type=\"text\" value=\"${stock.stock}\" onkeyup=\"sumNum();checkNum(this)\"><label class=\"stock_error_msg\" style=\"color:red;\"></td>");
                        td2.appendTo(tr);
                    });
                }
                //结束创建Table表
                arrayColumn.pop();//删除数组中最后一项
                //合并单元格
                $(table).mergeCell({
                    // 目前只有cols这么一个配置项, 用数组表示列的索引,从0开始
                    cols: arrayColumn
                });
            } else{
                //未全选中,清除表格
                document.getElementById('createTable').innerHTML="";
            }
        },//合并行
        hebingFunction: function () {
            $.fn.mergeCell = function (options) {
                return this.each(function () {
                    var cols = options.cols;
                    for (var i = cols.length - 1; cols[i] != undefined; i--) {
                        // fixbug console调试
                        // console.debug(cols[i]);
                        mergeCell($(this), cols[i]);
                    }
                    dispose($(this));
                });
            };
            function mergeCell($table, colIndex) {
                $table.data('col-content', ''); // 存放单元格内容
                $table.data('col-rowspan', 1); // 存放计算的rowspan值 默认为1
                $table.data('col-td', $()); // 存放发现的第一个与前一行比较结果不同td(jQuery封装过的), 默认一个"空"的jquery对象
                $table.data('trNum', $('tbody tr', $table).length); // 要处理表格的总行数, 用于最后一行做特殊处理时进行判断之用
                // 进行"扫面"处理 关键是定位col-td, 和其对应的rowspan
                $('tbody tr', $table).each(function (index) {
                    // td:eq中的colIndex即列索引
                    var $td = $('td:eq(' + colIndex + ')', this);
                    // 取出单元格的当前内容
                    var currentContent = $td.html();
                    // 第一次时走此分支
                    if ($table.data('col-content') == '') {
                        $table.data('col-content', currentContent);
                        $table.data('col-td', $td);
                    } else {
                        // 上一行与当前行内容相同
                        if ($table.data('col-content') == currentContent) {
                            // 上一行与当前行内容相同则col-rowspan累加, 保存新值
                            var rowspan = $table.data('col-rowspan') + 1;
                            $table.data('col-rowspan', rowspan);
                            // 值得注意的是 如果用了$td.remove()就会对其他列的处理造成影响
                            $td.hide();
                            // 最后一行的情况比较特殊一点
                            // 比如最后2行 td中的内容是一样的, 那么到最后一行就应该把此时的col-td里保存的td设置rowspan
                            if (++index == $table.data('trNum'))
                                $table.data('col-td').attr('rowspan', $table.data('col-rowspan'));
                        } else { // 上一行与当前行内容不同
                            // col-rowspan默认为1, 如果统计出的col-rowspan没有变化, 不处理
                            if ($table.data('col-rowspan') != 1) {
                                $table.data('col-td').attr('rowspan', $table.data('col-rowspan'));
                            }
                            // 保存第一次出现不同内容的td, 和其内容, 重置col-rowspan
                            $table.data('col-td', $td);
                            $table.data('col-content', $td.html());
                            $table.data('col-rowspan', 1);
                        }
                    }
                });
            }
            // 同样是个private函数 清理内存之用
            function dispose($table) {
                $table.removeData();
            }
        },
        //组合数组
        doExchange: function (doubleArrays) {
            var len = doubleArrays.length;
            if (len >= 2) {
                var arr1 = doubleArrays[0];
                var arr2 = doubleArrays[1];
                var len1 = doubleArrays[0].length;
                var len2 = doubleArrays[1].length;
                var newlen = len1 * len2;
                var temp = new Array(newlen);
                var index = 0;
                for (var i = 0; i < len1; i++) {
                    for (var j = 0; j < len2; j++) {
                        temp[index] = arr1[i] + "," + arr2[j];
                        index++;
                    }
                }
                var newArray = new Array(len - 1);
                newArray[0] = temp;
                if (len > 2) {
                    var _count = 1;
                    for (var i = 2; i < len; i++) {
                        newArray[_count] = doubleArrays[i];
                        _count++;
                    }
                }
                //console.log(newArray);
                return step.doExchange(newArray);
            }
            else {
                return doubleArrays[0];
            }
        }
    }
    return step;
})
$().ready(function() {
	var type = $("input[name='is_unified_spec']:checked").val();
	if(type!="true"){
		step.Creat_Table();
		 getPrice();
	}
	 changeType();
});

function listImage(){
	var params = {};
	$("#mainUl").mask("加载中...");
	var url = obz.ctx + "/product/browser";
	obz.ajaxJson(url, params, function(resp){
		var result = eval(resp.data);
		$(".selectMonth").empty();
		var dataList = result;
		if(dataList.length>0){
			for(var i=0;i<dataList.length;i++){
				var _li = dataList[i];
				var liHtml = obz.dataTemplate4obj($("#ul_li_tpl").html(), _li);
				$(".selectMonth").append(liHtml);
			}
			
			$(".add-local-image-button").addClass("hidden");
		}else{
			
			$(".add-local-image-button").removeClass("hidden");
		}
		
		 $("ul.selectMonth li").toggle(
				 function(){
					 
					 if(isOnly=="1"){
							$("ul.selectMonth li").each(function(){	
								 $(this).removeClass("active");
							});
						}
					    $(this).addClass("active");
					    selectImg.length=0;
					    listImg="";
					    listImgUrl="";
					    $("ul.selectMonth li.active").each(function(){
					    	listImg+=$(this).attr("data-file")+",";
					    	listImgUrl+=$(this).attr("data-fileUrl")+",";
					    	selectImg.push($(this).attr("data-file"));
					    });
					    listImg=listImg.substring(0,listImg.length-1);
					    if(selectImg.length>0){
					    	$(".selected-count-region").removeClass("hidden");
					    	$(".js-selected-count").html(selectImg.length);
					    	$("#saveImg").removeClass("disabled");
					    }else{
					    	$(".selected-count-region").addClass("hidden");
					    	$("#saveImg").addClass("disabled");
					    }
					    },
					 function(){
						 $(this).removeClass("active");
						 selectImg.length=0;
						 listImg="";
						 listImgUrl="";
						 $("ul.selectMonth li.active").each(function(){
							 
							 listImg+=$(this).attr("data-file")+",";
							 listImgUrl+=$(this).attr("data-fileUrl")+",";
							 selectImg.push($(this).attr("data-file"));
						    });
						 listImg=listImg.substring(0,listImg.length-1);
						 if(selectImg.length>0){
						    	$(".selected-count-region").removeClass("hidden");
						    	$(".js-selected-count").html(selectImg.length);
						    	$("#saveImg").removeClass("disabled");
						    }else{
						    	$(".selected-count-region").addClass("hidden");
						    	$("#saveImg").addClass("disabled");
						    }
						 }
					  );
	});
	
}

function deleteImg(obj){
	$(obj).parent().remove();
}

function clearError(){
	$("#image_error_msg,.price_error_msg,#only_error_msg,#specificationValue_error_msg,.stock_error_msg,#assign_error_msg,.delivery_template_error_msg,.delivery_fees_error_msg,.delivery_weight_error_msg").empty();
	$("#bodyss tr").each(function(i,obj){
		$(obj).find("input[name='price_s']").css("border-color", "");
		$(obj).find("input[name='stock_s']").css("border-color", "");
	});
	var delivery_fees=$("input[name='delivery_fees']");
	  delivery_fees.css("border-color", "");
	  var delivery_weight=$("input[name='delivery_weight']");
	  delivery_weight.css("border-color", "");
}

function changeType(){
	var type = $("input[name='is_unified_spec']:checked").val();
	if(type=="true"){
		$("#unified").show();
		$("#allSpec").hide();
		}else{
	    $("#unified").hide();
	    $("#allSpec").show();
		}
	
	if(type=="true"){
		$("#unified_weight").show();
		$("#disunified_weight").hide();
		}else{
	    $("#unified_weight").hide();
	    $("#disunified_weight").show();
		}
	}
function saveTaskForm(){
	saveTask();
	clearError();
	$("#inputForm").submit();
}