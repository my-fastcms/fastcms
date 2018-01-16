var productAddImg={
		productAddDailogURL : obz.ctx + "/product/browser",//获取图片请求
		productSettingDailogDivURL : obz.ctx + "/product/addImage",//跳到图片列表界面
		isOnlySelect : null,//图片是否单选
		
		//添加图片界面
		addImageHtml : function(){
			var dialog = BootstrapDialog.show({
				size: BootstrapDialog.SIZE_WIDE,
				title: "添加图片",
		        message: $('<div></div>').load(productAddImg.productSettingDailogDivURL)
		    });
			return dialog;
		},
		pageClick : function(pageNo) {
			productAddImg.getImageToHtml(pageNo);
		},
		//添加图片至页面
		getImageToHtml : function(single, currPage){
			
			var params = {};
			if(currPage){
				params.page = currPage;
			}
			$("#image-list").mask("加载中...");
			obz.ajaxJson(productAddImg.productAddDailogURL, params, function(resp){
				var result =resp.data;
				$("#image-list").empty();
				if(currPage){
					$("#pagerImage").pager({ pagenumber:currPage, recordcount:result.totalRow, pagesize:result.pageSize, recordtext:'共 {0} 页, {1} 条记录', buttonClickCallback: productAddImg.pageClick });
				}else{
					$("#pagerImage").pager({ recordcount:result.totalRow, pagesize:result.pageSize, recordtext:'共 {0} 页, {1} 条记录', buttonClickCallback: productAddImg.pageClick });	
				}
				var dataList = result.list;
				if(dataList.length>0){
					$(".add-local-image-button").addClass("hidden");
					for(var i=0;i<dataList.length;i++){
						var _li = dataList[i];
						 $("#image-list").append('<li data-file="'+_li.imgPath+'" data-fileUrl="'+_li.imgUrl+'">'
		          				 +' <img alt="" src="'+_li.imgPath+'">'
		      					 +' <div class="image-title">'+_li.imgUrl+'</div></li>');
						//var liHtml = obz.dataTemplate4obj($("#ul_li_tpl").html(), _li);
						//$("#mainUl").append(liHtml);
				        }	
				}else{
					$(".add-local-image-button").removeClass("hidden");
				}
				var i=0;
				 $("ul.selectMonth li").click(function() {

					 if(!$(this).hasClass("active")){
					 		i = 0;
					 	}
							 if(i==0){
								 i=1;
								 if(single){
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
								    	$("#saveImages").removeClass("disabled");
								    }else{
								    	$(".selected-count-region").addClass("hidden");
								    	$("#saveImages").addClass("disabled");
								    }
								    
							    }else{
							     i=0;
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
								    	$("#saveImages").removeClass("disabled");
								    }else{
								    	$(".selected-count-region").addClass("hidden");
								    	$("#saveImages").addClass("disabled");
								    }
								 }
				 });
			});
		}
};