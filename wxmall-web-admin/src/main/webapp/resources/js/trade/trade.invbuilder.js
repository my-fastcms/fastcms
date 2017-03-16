//封装发货单模板设计器 wangjun
var InvBuilder = {
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
        
		InvBuilder.initPallet();
		InvBuilder.initCanvas();
		
		//让表格可以调整列宽 table table-bordered
		//$("#orderlist_table").addClass("table").addClass("table-bordered");
		//$("#orderlist_table").resizableColumns();
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
	
	initCanvas : function(){
		//初始化清空画布
		$("#form-container-div").empty();
		var width = Trade.useInvtpl && Trade.useInvtpl.pagewidth!=0 ? Trade.useInvtpl.pagewidth : 210;
		var height = Trade.useInvtpl && Trade.useInvtpl.pageheight!=0 ? Trade.useInvtpl.pageheight : 127;
		$("#form-container-div").css("width", width +"mm").css("height", height+"mm");
		
		if(Trade.useInvtpl !=null){
			$("#text_pagewidth").val(Trade.useInvtpl.pagewidth);
			$("#text_pagelength").val(Trade.useInvtpl.pageheight);
			var tplcontent = Trade.useInvtpl.inv_tplcontent;
			$("#form-container-div").append($(tplcontent));
		}
		
		$('#form-container-div').children().each(function(){
			InvBuilder.initElementObj($(this));
			//对二维码元素进行特殊处理
			if($(this).is("div")){
				var e = $(this);
				var key = e.attr("key");
				$("div.form-palette-element").each(function(){
					var me = $(this);
					if(key == me.attr("elementkey")){
						e.attr("data-url", me.attr("elementsrc"));
						e.css("background-size", "100% 100%").css("background-image", "url('"+me.attr("elementsrc")+"')");
					}
				});
			}
		});
		
		//在画布上加载新的内容
		$(".form-container-div").droppable({
            drop: function(event, ui) {
                var obj = $(ui.draggable);
                if (obj.hasClass("form-palette-element")) {
                	var elementObj = InvBuilder.createElementObj(obj);
                	elementObj.css('top', parseInt(ui.position.top) - 60);
                	elementObj.css('left', parseInt(ui.position.left) - 100);
                	InvBuilder.initElementObj(elementObj);
                }
            },
            //activeClass: "form-cell-highlight",
            accept: ".form-palette-element",
            greedy: true,
            scroll: false,
            tolerance: "touch"
        });
		
	},
	
	//根据拖拽的对象创建一个新的元素
	createElementObj : function (dragObj){
		var elementObj = null;
		if(dragObj){
			var type = $(dragObj).attr("elementType");
			if(type=='table'){
				elementObj = $("#div_invoice_table_tpl").children("table").clone(true);
			}else if(type=="img"){
				//比如二维码等图片
				elementObj = $("#div_img_dataItemtpl").children("div").clone(true);
			}else{
				elementObj = $("#div_std_dataItemtpl").children("span").clone(true);
			}
			
			//设置相关属性
			if(elementObj != null){
				if(type != 'img'){
					elementObj.text($.trim(dragObj.text()));	
				}else{
					//图片
					elementObj.css("background-size", "100% 100%").css("background-image", "url('"+$(dragObj).attr("elementsrc")+"')");
					elementObj.attr("data-url", $(dragObj).attr("elementsrc"));
				}
				var elementKey = $(dragObj).attr("elementKey");
				if(elementKey != null){
					elementObj.attr("key", $.trim(elementKey));	
				}
				var elementText = $(dragObj).attr("elementText");
				if(elementText != null){
					elementObj.attr("etext", $.trim(elementText));					
				}
				elementObj.append('<a class="delX" href="javascript:void(0);" title="删除" onclick="InvBuilder.delElement(this)"></a>');
			}
			
			$("#form-container-div").append(elementObj);
		}
		return elementObj;
	},
	
	//创建一个新的拖拽元素对象后,对该对象进行属性初始化
	//设置样式,绑定相关事件 
	initElementObj : function(element){
		//初始化css为选中,取消其他element选择状态
		InvBuilder.clear();
    	element.removeClass('unseled').addClass('seled');
		//可拖拽事件
		element.draggable({
		    containment: '#form-container-div',
		    scroll: false,
		    start: function () {
		    	InvBuilder.clear();
		    	element.removeClass('unseled').addClass('seled');
		    },
		    stop: function(){
		    }
		});
		
		//if(element.is("table") || element.is("span"))
			element.resizable({containment:'parent'}).resizable('destroy').resizable({containment:'parent'});
		
		//绑定点击事件
		element.bind('click', function () {
			InvBuilder.clear();
		    element.removeClass('unseled').addClass('seled');
		    $("#sel_FontNameList,#sel_FontSizeList,#chk_PrintBorderLine_Lab,#chk_PrintBorderCuti_Lab,#chkBox_SelAll").attr("disabled", false);
		});
		
		//绑定键盘事件 TODO
	},
	
	//调整表格显示的列,以及列宽
	settingTable : function(){
	},
	
	delElement : function(element){
		obz.showMessage("确认删除吗?", function(){
			var a = $(element).parent();
			if(a) a.remove();
			return false;
		});
		return false;
	},
	
	//保存
	saveDesignHtml : function(){
		InvBuilder.clear();
		//tplcontent, designhtml, pagewidth, pageheight, offsetx, offsety
		//此处先移除可改变表格大小产生的相关控件元素;在初始化方法中初始化表格可调整大小
		$("#orderlist_table").find("div").each(function(){
			$(this).remove();
		});
		
		//tplcontent如果有二维码图片，需要排除base64格式的数据；此处必须移除掉，不能把base64的数据存到数据库
		$("#form-container-div").children("div").each(function(){
			$(this).attr("data-url", "");
			$(this).css("background-image", "");
		});
		var tplcontent=$("#form-container-div").html();//只取拖拽后生成的原始模板内容
		//设计后的普通文本html
		var html = $('<div></div>');
		$("#form-container-div span").each(function(){
			var me = $(this);
			var divElement = $('<div style="position: absolute;"></div>');
			var newDivElement = InvBuilder.convert2DivElement(me, divElement);
			html.append(newDivElement);
		});
		var designhtml = html.html();//只取拖拽后生成的内容
		
		//设计后的图片html
		var imgHtml = $('<div></div>');
		$("#form-container-div").children("div").each(function(){
			var divElement = $('<div style="position: absolute;"></div>');
			divElement.css("width", $(this).css("width")).css("height", $(this).css("height"))
			.css("top", $(this).css("top")).css("left", $(this).css("left"));
			divElement.attr("key", $(this).attr("key"));
			imgHtml.append(divElement);
		});
		var imgDesignHtml = imgHtml.html();
		
		//设计后的表格html
		var tablehtml = "";
		$("#form-container-div table").each(function(){
			var me = $(this);
			var tableElement = $('<table><tbody><tr><td>序号</td><td>宝贝名称</td><td>规格</td><td>单价</td><td>数量</td><td>金额</td></tr></tbody></table>');
			tableElement.attr("style", me.attr("style"));
			tablehtml = tableElement.prop("outerHTML");
		});
		
		var pagewidth = $("#text_pagewidth").val(), pageheight = $("#text_pagelength").val(), 
			offsetx = $("#text_offsetx").val(), offsety = $("#text_offsety").val();
		
		var params = {};
		params.tplcontent = $.trim(tplcontent);
		params.designhtml = $.trim(designhtml);
		params.tablehtml = $.trim(tablehtml);
		params.imghtml = $.trim(imgDesignHtml);
		params.pagewidth = pagewidth;
		params.pageheight = pageheight;
		
		//alert("tplcontent:" + tplcontent + ",designhtml:" + designhtml + ",tablehtml:" + tablehtml + ",imgDesignHtml:" + imgDesignHtml);
		//return false;
		params.offsetx = offsetx;
		params.offsety = offsety;
		obz.ajaxJson(obz.ctx+"/invoice/save", params, function(resp){
			var result = resp.data;
			if(resp.code != 200){
				obz.showMessage(resp.msg);
				return false;
			}
			Trade.useInvtpl = result;
			obz.showMessage("保存成功", function(){
				InvBuilder.init();
				return false;
			});
		});
	},
	
	convert2DivElement : function(source, target){
		var key = source.attr("key"), label = source.attr("etext"), style = source.attr("style");
		$(target).attr("style", style);
		var span = $("<span></span>");
		span.text(label + " {"+key+"}");
		target.append(span);
		return target;
	},
	
	clear : function(){
		$('#form-container-div>*').removeClass('seled').addClass('unseled');
		//$("#orderlist_table .settingTable").remove();
	}
};