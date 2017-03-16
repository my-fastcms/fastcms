//封装交易模块业务上核心JS方法 wangjun
var Trade = {
	expressAddDailogURL : obz.ctx + "/express/comps",
	exptplSettingDailogDivURL : obz.ctx + "/express/initPallet",//跳到快递单模板设计界面
	
	printData : null,//单个打印的数据(包含发货单,快递单需要的数据)
	//当前操作的快递模板对象,//优先级比默认使用的快递模板高
	//1,在新增快递公司的时候,缓存返回的操作对象
	//2,在保存模板的时候,缓存
	useExptpl : null,//当前用户正在使用的快递单模板
	useInvtpl : null,//当前用户正在使用的发货单模板
	
	//添加快递公司对话框
	addExpCompany : function(){
		var dialog = BootstrapDialog.show({
			size: BootstrapDialog.SIZE_LARGE,
			title: "添加快递",
	        message: $('<div></div>').load(Trade.expressAddDailogURL)
	    });
		return dialog;
	},
	
	//去编辑快递模板 很炫的jquery ui 拖拽功能, 赶快去试试吧
	expTemplateSetting : function(){
		var dialog = BootstrapDialog.show({
			size: BootstrapDialog.SIZE_WIDE,
			title: "快递单设置",
	        message: $('<div></div>').load(Trade.exptplSettingDailogDivURL)
	    });
		return dialog;
	},
	
	//设计器保存选择的快递公司
	getUserExpTemplate : function(){
		var expkey = $("#expressCompy option:selected").val();//key 快递模板相关数据都通过key关联
		if(expkey == null || expkey==""){
			obz.showMessage("亲,请选择快递公司!");
			return false;
		}
		var expname = $('#expressCompy option:selected').text();//快递名称
		var expbgimg = $("#_myselexpbgimg").val();//背景图
		if(expbgimg==null || ""==expbgimg){
			obz.showMessage("亲,请选择一张背景图!");
			return false;
		}
		var params = {};
		params.expKey = expkey;
		//params.expname = expname;
		//params.expbgimg = expbgimg;
		obz.ajaxJson(obz.ctx+"/express/getUserTplByKey", params, function(resp){
			var result = resp.data;
			if(resp.code!=200){
				obz.showMessage(resp.msg);
				return false;
			}
			//保存后返回的快递模板对象
			var myexptpl = result;
			Trade.useExptpl = myexptpl;				//把新增的快递对象缓存起来
			Trade.useExptpl.exp_bgimg = expbgimg; 	//注意：此处用用户临时选择的快递图片进行替换
			Trade.expTemplateSetting();	
		});
	},
	
	//根据快递公司key加载对应的模板图片,一个快递公司有N个模板背景图片
	//添加快递的弹窗口中，选择快递公司的时候，响应该事件
	displayTplImagesByCompkey : function(){
		//添加快递的时候获取下拉列表;更改背景图的时候获取复选框选中的快递类型值
		var expkey = $("#expressCompy option:selected").val();
		if(!expkey || expkey == "" || expkey == null){
			$("#expimglistDiv").empty();
			$("#div_sel_expimginf").empty();
			$("#expimglistDiv").hide();
			$("#_myselexpbgimg").val("");
			return;
		}
		var params = {};
		params.expkey = expkey;
		obz.ajaxJson(obz.ctx + "/express/getTplBgImagesByCompkey", params, function(resp){
			var result = resp.data;
			$("#_myselexpbgimg").val("");
			$('#expimglistDiv').empty();
			$("#div_sel_expimginf").empty();
			$("#expimglistDiv").show();
			
			var expimglistDiv = obz.createDiv("expimglistDiv");
			expimglistDiv.setAttribute("style", "padding-top: 10px;");
			//expimglistDiv.setAttribute("align", "center");
			for(var i=0;i<result.length;i++){
				var s = $('<span style="padding-left: 10px;padding-top: 5px;cursor: pointer;" id="span_'+i+'"></span>').append($('<img width="200px" height="120px" src="'+result[i].exp_img+'">'));
				$(expimglistDiv).append(s);
			}
			$("#expressCompyInfodiv").append($(expimglistDiv));
			$("#expimglistDiv").find("span").click(function(){
				var me = $(this);
				me.children().addClass("imgseled").removeClass("imgnoseled");
				$("#_myselexpbgimg").val(me.children().attr("src"));
				$("#expimglistDiv").find("span").each(function(){
					var otherid = $(this).attr("id");
					if($.trim(me.attr("id"))!=$.trim(otherid)){
						$(this).children().removeClass("imgseled").addClass("imgnoseled");
					}
				});
			});
		});
	},

	setDefaultTpl : function(){
		var expkey = $("input[name='myuseexpress']:checked").val();//key 快递模板相关数据都通过key关联
		var params = {};
		params.expkey = expkey;
		obz.ajaxJson(obz.ctx+"/express/setDefault", params, function(resp){
			var result = resp.data;
			if(result.defWarn){
				obz.showMessage(result.defWarn);
				return false;
			}
			//$("#expPrintDialogDiv").mask("加载中......");
			//$("#expPrintDialogDiv").load("dailog/expressPrintDailog.action");
			var expressname = $("input[name='myuseexpress']:checked").attr("expName");
			obz.showMessage("亲,您已经成功设置[" + expressname+"]为默认模板,下次可以直接使用该模板打印快递单.");
			return false;
		});
	},
	
	//删除自定义上传的快递背景图
	delExpbgImg : function(){
		var expimg = $("#_myselexpbgimg").val();
		if(expimg==null || ""==expimg){
			obz.showMessage("亲,没有底图可以删除哦!");
			return false;
		}
		obz.showMessage("亲,删除后不可恢复,确定要删除这张图片吗?", function(){
			var expkey = $("#expressCompy option:selected").val() || $("input[name='myuseexpress']:checked").val();
			var params = {};
			params.expkey = expkey;
			params.expimg = expimg;
			obz.ajaxJson(params, function(resp){
				var result = resp.data;
				if(resp.code != 200){
					obz.showMessage("亲,底图删除失败！");
					return false;
				}
				$("#_myselexpbgimg").val("");
				Trade.displayExpressTpl();
				return false;
			});				
		});
	},
	
	//打印快递单
	printExpress : function(printData){
		if(Trade.useExptpl==null){
			obz.showMessage("没有选择打印模板，请选择");
			return false;
		}
		try{
			var LODOP = getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
			LODOP.SET_SHOW_MODE("BKIMG_LEFT",0);
			LODOP.SET_SHOW_MODE("BKIMG_TOP",0);
			LODOP.SET_SHOW_MODE("BKIMG_WIDTH",Trade.useExptpl.pagewidth+"mm");
			LODOP.SET_SHOW_MODE("BKIMG_HEIGHT",Trade.useExptpl.pageheight+"mm"); //这句可不加，因宽高比例固定按原图的
			LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);
			LODOP.SET_SHOW_MODE("BKIMG_PRINT",false);
			LODOP.SET_PRINT_STYLEA(0,"Stretch",2)
			
			//LODOP.ADD_PRINT_HTM(0,0,600,1000,printHtml);
			if(printData && printData != null){
				for(var i=0; i<printData.length;i++){
					var newDesignhtml = obz.dataTemplate4obj(Trade.useExptpl.exp_designhtml, printData[i]);
					var mydesignHtml = $("<div id='__mydesignHtml' style='float: left;position: relative'></div>");
					$(mydesignHtml).css("width", Trade.useExptpl.pagewidth+"mm").css("height", Trade.useExptpl.pageheight+"mm");
					//$(mydesignHtml).css('background-image', 'url(' + Trade.useExptpl.exp_bgimg + ')');
					mydesignHtml.append($(newDesignhtml));
					var printHtml = $(mydesignHtml).html();
					LODOP.NewPage();
					//LODOP.ADD_PRINT_IMAGE(0,0,Trade.useExptpl.pagewidth+"mm",Trade.useExptpl.pageheight+"mm",
					//		"<img border='0' " + "src='"+Trade.useExptpl.exp_bgimg+"' />");
					//LODOP.SET_PRINT_STYLEA(0,"Stretch",2)
					
					LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' " + "src='"+Trade.useExptpl.exp_bgimg+"' />");
					LODOP.ADD_PRINT_HTM(0,0,Trade.useExptpl.pagewidth+"mm",Trade.useExptpl.pageheight+"mm",printHtml);
				}
			}else{
				LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' " + "src='"+Trade.useExptpl.exp_bgimg+"' />");
				LODOP.ADD_PRINT_HTM(0,0,Trade.useExptpl.pagewidth+"mm",Trade.useExptpl.pageheight+"mm", Trade.useExptpl.exp_tplcontent);
			}
			LODOP.PREVIEW();	
		}catch(err){
			obz.showMessage(err);
		}
		return false;
	},
	
	//==========================================================================================================================//
	//以上是快递单操作API
	//==========================================================================================================================//
	//以下是发货单操作API
	//==========================================================================================================================//
	
	invtplSettingDailogDivURL : obz.ctx+"/invoice/setting", //跳到发货单模板设计界面
	
	//打印发货单
	printInvoice : function(printData){
		try{
			var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
			if(printData && printData != null){
				for(var i=0;i<printData.length;i++){
					var currTrade = printData[i];
					var mydesignHtml = $("<div id='__myInvdesignHtml' style='float: left;position: relative;'></div>");
					
					//打印常规文本数据
					var newDesignhtml = obz.dataTemplate4obj(Trade.useInvtpl.inv_designhtml, currTrade);
					//获取数据填充到模板中
					$(mydesignHtml).css("width", Trade.useInvtpl.pagewidth+"mm").css("height", Trade.useInvtpl.pageheight+"mm");
					mydesignHtml.append($(newDesignhtml));
					
					//打印图片数据，二维码等 支持多个二维码
					//alert(currTrade.qrcode_url);
					var imgDesignHtml = $(Trade.useInvtpl.inv_img_designhtml);
					$("<div></div>").append(imgDesignHtml).children("div").each(function(){
						var key = $(this).attr("key");
						var strBASE64Code = currTrade[key];
						LODOP.ADD_PRINT_SETUP_BKIMG(strBASE64Code);
						LODOP.SET_SHOW_MODE("BKIMG_LEFT",$(this).css("left"));
						LODOP.SET_SHOW_MODE("BKIMG_TOP",$(this).css("top"));
						LODOP.SET_SHOW_MODE("BKIMG_WIDTH",$(this).css("width"));
						//LODOP.SET_SHOW_MODE("BKIMG_HEIGHT","99mm"); //这句可不加，因宽高比例固定按原图的
						LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);
						LODOP.SET_SHOW_MODE("BKIMG_PRINT",true);
					});
					
					//打印表格数据 	表头 //表尾 //表身
					var count = 0, totalnums = 0, totalpayment = 0;
					var orderList = currTrade.orderItems;
					var tableView = $(Trade.useInvtpl.inv_tabelhtml);
					for(var j=0;j<orderList.length;j++){
						var order = orderList[j];
						order.totalPrice = order.totalPrice.toFixed(2);
						totalnums += order.quantity;
						totalpayment += parseFloat(order.totalPrice);
						order.index=++count;
						var trhtmlTpl = "<tr><td>{index}</td><td>{productName}</td><td>{specificationValueNames}</td><td>{price}</td><td>{quantity}</td><td>{totalPrice}</td></tr>";
						var trhtml = obz.dataTemplate4obj(trhtmlTpl, order);
						tableView.append(trhtml);
					}
					var hejHtml = '<tr><td colspan="4">合计:</td><td>'+totalnums+'(邮费:'+currTrade.postFee+'元)</td><td>'+totalpayment.toFixed(2)+'</td></tr>';
					tableView.append(hejHtml);
					var tableTop = parseInt(tableView.position().top);
					var tableHeight = parseInt(tableView.height());
					mydesignHtml.append(tableView);
					
					mydesignHtml.find("div").each(function(){
						var me = $(this);
						var meTop = parseInt(me.position().top);
						if(meTop>tableTop){//元素在表尾
							var mynewTop = parseInt(meTop + tableHeight - tableTop);
							me.css("top", mynewTop);
						}
					});
					var printHtml = $(mydesignHtml).html();
					LODOP.NewPage();
					LODOP.ADD_PRINT_HTM(0,0,Trade.useInvtpl.pagewidth+"mm",Trade.useInvtpl.pageheight+"mm",printHtml);
				}
				
			}else{
				LODOP.ADD_PRINT_HTM(0,0,Trade.useInvtpl.pagewidth+"mm",Trade.useInvtpl.pageheight+"mm",$("#form-container-div").html());
				$("#form-container-div").children("div").each(function(){
					var strBASE64Code = $(this).attr("data-url");
					LODOP.ADD_PRINT_SETUP_BKIMG(strBASE64Code);
					LODOP.SET_SHOW_MODE("BKIMG_LEFT",$(this).css("left"));
					LODOP.SET_SHOW_MODE("BKIMG_TOP",$(this).css("top"));
					LODOP.SET_SHOW_MODE("BKIMG_WIDTH",$(this).css("width"));
					//LODOP.SET_SHOW_MODE("BKIMG_HEIGHT","99mm"); //这句可不加，因宽高比例固定按原图的
					LODOP.SET_SHOW_MODE("BKIMG_IN_PREVIEW",1);	
				});
			}
			LODOP.PREVIEW();			
		}catch(err){
			obz.showMessage(err);
		}
		return false;
	},
	
	//根据发货单模板数据初始化发货单打印预览界面
	showInvTemplate : function(){
		obz.ajaxJson(obz.ctx+"/invoice/getUserTpl", {}, function(resp){
			var result = resp.data;//该用户使用的发货单模板对象
			Trade.useInvtpl = result;
			InvBuilder.init();
		});
	},
	
	//跳到发货单模板设计器界面, 体验炫酷的 jquery ui 拖拽效果
	invTemplateSetting : function(){
		var dialog = BootstrapDialog.show({
			size: BootstrapDialog.SIZE_WIDE,
			title: "发货单模板设置",
	        message: $('<div></div>').load(Trade.invtplSettingDailogDivURL)
	    });
		return dialog;
	}
	
}