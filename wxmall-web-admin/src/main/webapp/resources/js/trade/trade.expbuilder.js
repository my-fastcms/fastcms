//封装快递单模板设计器 wangjun
//Canvas 面板
//pallet 调色板
var ExpBuilder = {
		
	init : function(){
		// workaround for IE draggable bug #4333 http://bugs.jqueryui.com/ticket/4333
        $.extend($.ui.sortable.prototype, (function (orig) {
            return {
                _mouseCapture: function (event) {
                    var result = orig.call(this, event);
                    if (result && $.browser.msie) event.stopPropagation();
                    return result;
                }
            };
        })($.ui.sortable.prototype["_mouseCapture"]));
        
		ExpBuilder.initPallet();
		ExpBuilder.initCanvas();
	},	
	
	initPallet : function(){
		$(".form-palette-element").draggable({
            connectToSortable: ".form-container-div",
            helper:'clone',
            zIndex: 200,
            opacity: 0.7,
            revert: "invalid",
            cursor: "move"
        }).disableSelection();
	},
	
	//width:100%;height:100%;z-Index:-1;
	initCanvas : function(){
		//初始化清空画布
		$("#form-container-div").empty();
		//在画布上加载新的内容
		var width = Trade.useExptpl && Trade.useExptpl.pagewidth !=null && Trade.useExptpl.pagewidth!=0 ? Trade.useExptpl.pagewidth : 230;
		var height = Trade.useExptpl && Trade.useExptpl.pagewidth !=null && Trade.useExptpl.pageheight!=0 ? Trade.useExptpl.pageheight : 127;
		$("#form-container-div").css("width", width +"mm").css("height", height+"mm");
		
		//alert(Trade.useExptpl.exp_bgimg + ",content:" + Trade.useExptpl.exp_tplcontent);
		//if(Trade.useExptpl.exp_designhtml != null && ""!=$.trim(Trade.useExptpl.exp_designhtml)){}
		
		$("#text_pagewidth").val(Trade.useExptpl.pagewidth || 0);
		$("#text_pagelength").val(Trade.useExptpl.pageheight || 0);
		$("#text_offsetx").val(Trade.useExptpl.offsetx || 0);
		$("#text_offsety").val(Trade.useExptpl.offsety || 0);
		//$("#form-container-div").css('background-image', 'url(' + Trade.useExptpl.exp_bgimg + ')');//.css("background", "no-repeat");
		var bgHtml = '<div id="mytemplatediv" style="float: left;" class="unseled">'
		+ '<img id="myexpbgimg" src="'+Trade.useExptpl.exp_bgimg+'"></div>';
		
		$("#form-container-div").append(bgHtml).append(Trade.useExptpl.exp_tplcontent);
		$('#form-container-div').disableSelection();
		$('#form-container-div').find('span').each(function(){
		  	ExpBuilder.initElementObj($(this));
		});
		
		$(".form-container-div").droppable({
            drop: function(event, ui) {
                var obj = $(ui.draggable);
                if (obj.hasClass("form-palette-element")) {
                	var elementObj = ExpBuilder.createElementObj(obj);
                	elementObj.css('top', parseInt(ui.position.top) - 60);
                	elementObj.css('left', parseInt(ui.position.left) - 100);
                	ExpBuilder.initElementObj(elementObj);
                }
            },
            //activeClass: "form-cell-highlight",
            accept: ".form-palette-element",
            greedy: true,
            scroll: false,
            tolerance: "touch"
        });
	},
	
	createElementObj : function (dragObj){
		var elementObj = null;
		if(dragObj){
			var type = $(dragObj).attr("elementType");
			if(type=='img'){
				elementObj = $("#div_img_dataItemtpl").children("div").clone(true);
			}else{
				elementObj = $("#div_std_dataItemtpl").children("span").clone(true);
			}
			//设置属性
			if(elementObj != null){
				if(type != 'img'){
					elementObj.text($.trim(dragObj.text()));	
				}else{
					//图片
					elementObj.css("background-size", "100% 100%").css("background-image", "url('"+$(dragObj).attr("elementsrc")+"')");
					elementObj.attr("data-url", $(dragObj).attr("elementsrc"));
				}
				
				var elementKey = $(dragObj).attr("elementKey");
				elementObj.attr("key", $.trim(elementKey));
				elementObj.append('<a class="delX" href="javascript:void(0);" title="删除" onclick="ExpBuilder.delElement(this)"></a>');
				$("#form-container-div").append(elementObj);	
			}
		}
		return elementObj;
	},
	
	//创建一个新的拖拽元素对象后,对该对象进行属性初始化
	//设置样式,绑定相关事件 
	initElementObj : function(element){
		//初始化css为选中,取消其他element选择状态
		ExpBuilder.clear();
    	element.removeClass('unseled').addClass('seled');
		//可拖拽事件
		element.draggable({
		    containment: '#form-container-div',
		    scroll: false,
		    start: function () {
		    	ExpBuilder.clear();
		    	element.removeClass('unseled').addClass('seled');
		    },
		    stop: function(){
		    	//element.addClass("expressDataItem");
		    }
		});
		
		if(element.is("span"))
			element.resizable({containment:'parent'}).resizable('destroy').resizable({containment:'parent', minWidth: 30, minHeight:10});
		
		//绑定点击事件
		element.bind('click', function () {
			ExpBuilder.clear();
		    element.removeClass('unseled').addClass('seled');
		    $("#sel_FontNameList,#sel_FontSizeList,#chk_PrintBorderLine_Lab,#chk_PrintBorderCuti_Lab").attr("disabled", false);
			$("#chkBox_SelAll").attr("checked", false);
		});
		//绑定键盘事件
		//TODO
	},
	
	delElement : function(element){
		obz.showMessage("确认删除吗?", function(){
			var a = $(element).parent();
			if(a) {
				a.remove();
			}
			return false;
		});
	},
	
	//保存
	//<div id='mytemplatediv' style='float: left;'><img id='myexpbgimg' src='http://img01.taobaocdn.com/imgextra/i1/648670600/T2yQOxXvBXXXXXXXXX_!!648670600.jpg'></img></div>
	//<div style="position: absolute; width: 284px; height: 61px; left: 110px; top: 149px;">
	//<font style="word-break: break-all; font-size: 14px; font-weight: 400">广东广州天河科韵路168号</font>
	//</div>
	saveDesignHtml : function(){
		var tplcontent="";//只取拖拽后生成的原始模板内容，排除div跟背景图
		$("#form-container-div").find("span").each(function(){
			tplcontent += $(this).prop("outerHTML");
		});
		/*alert(tplcontent);
		return false;*/
		var html = $('<div"></div>');
		$("#form-container-div span").each(function(){
			var me = $(this);
			var divElement = $('<div style="position: absolute;"></div>');
			var newDivElement = ExpBuilder.convert2DivElement(me, divElement);
			html.append(newDivElement);
		});
		var designhtml = html.html();//转化为数据模板, 只取拖拽后生成的内容
		
		var params = {};
		//exp_key, tplcontent, designhtml, bgimg, pagewidth, pageheight, offsetx, offsety
		var expkey = Trade.useExptpl.exp_key || $("input[name='myuseexpress']:checked").val();
		var expname = Trade.useExptpl.exp_name || $("input[name='myuseexpress']:checked").attr("expName");//exp_name
		
		var bgimg = Trade.useExptpl.exp_bgimg;
		var pagewidth = $("#text_pagewidth").val(), pageheight = $("#text_pagelength").val(), 
			offsetx = $("#text_offsetx").val(), offsety = $("#text_offsety").val();
		
		//var designhtml = '<div id="__mydesignHtml" style="position: relative;float: left;">'+html.html()+'</div>'
		params.expkey = expkey;
		params.expname = expname;
		params.tplcontent = tplcontent;
		params.designhtml = designhtml;
		params.expbgimg = bgimg;
		params.pagewidth = pagewidth;
		params.pageheight = pageheight;
		params.offsetx = offsetx;
		params.offsety = offsety;
		obz.ajaxJson(obz.ctx + "/express/saveTemplate", params, function(resp){
			var result = resp.data;
			if(resp.code != 200){
				obz.showMessage(resp.msg);
				return false;
			}
			Trade.useExptpl = result;
			//$("#expPrintDialogDiv").load("dailog/expressPrintDailog.action");
			obz.showMessage("设置成功");
		});
	},
	
	convert2DivElement : function(source, target){
		var key = source.attr("key"), style = source.attr("style");
		$(target).attr("style", style);
		var font = $("<font></font>");
		font.text("{"+key+"}");
		target.append(font);
		return target;
	},
	
	//设置快递单背景图
	setExpbgimg : function(){
		//新的背景图
		var mynewexpimg = $("#_myselexpbgimg").val();
		Trade.useExptpl.exp_bgimg = mynewexpimg;
		$("#form-container-div").css('background-image', 'url(' + Trade.useExptpl.exp_bgimg + ')');
		//$("#expbgimgSettingDailogDiv").dialog('close');
	},
	
	clear : function(){
		$('#form-container-div>*').removeClass('seled').addClass('unseled');
	}
		
}; 