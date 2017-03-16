/*$.browser.mozilla = /firefox/.test(navigator.userAgent.toLowerCase());
$.browser.webkit = /webkit/.test(navigator.userAgent.toLowerCase());
$.browser.opera = /opera/.test(navigator.userAgent.toLowerCase());
$.browser.msie = /msie/.test(navigator.userAgent.toLowerCase());*/

//JS全局对象 wangjun
var obz = obz || {
	ctx : '',
	version : '2.0',
	uuid : function(){
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
            return v.toString(16);
        }).toUpperCase();
    },
    createDiv : function(divid){
    	var newDiv = document.getElementById(divid);
    	if(!newDiv){
    		newDiv = document.createElement("DIV");
    		newDiv.setAttribute("id", divid);    		
    	}
    	//每次调用创建div都会清空div的内容
    	$("#"+divid).empty();
		return newDiv;
    },
    //调用jquery ui dialog 封装系统消息提示框 
    //okCallback 点击确认的回调函数
    //cancelCallback 点击取消的回调函数
    showMessage : function(message, okCallback, cancelCallback){
    	var okCallbackFun = okCallback, cancelCallbackFun = cancelCallback;
    	//use bootstrap dialog
    	BootstrapDialog.confirm({title: "提示", message:message, btnCancelLabel: '取消', // <-- Default value is 'Cancel',
            btnOKLabel: '确定', closable: true, 
	            callback : function(result){
	            if(result) {
	            	if (okCallbackFun)
						okCallbackFun();
	            }else {
	            	if(cancelCallbackFun){
						cancelCallback();
					}
	            }
	        }
    	});
    	return false;
    },
    error : function(msg, callback){
    	if(callback) toastr.options.onHidden = callback;
		toastr.error(msg, "错误");
    },
    info : function(msg, callback){
    	if(callback) toastr.options.onHidden = callback;
    	toastr.info(msg, "提示");
    },
    msg : function(msg, callback){
    	if(callback) toastr.options.onHidden = callback;
    	toastr.success(msg, "提示");
    },
    warn : function(msg, callback){
    	if(callback) toastr.options.onHidden = callback;
    	toastr.warning(msg, "警告");    	
    },
    
    showQrcode : function(wirlessUrl){
    	var title = "", content = "";
		if(wirlessUrl == ""){
			title = '<div style="text-align:center; color:red; text-decoration:underline; font-size:14px;">请绑定微信公众号</div>';
			content = '<div id="popOverBox" align="center"><a href="'+obz.ctx+'/weixin/auth2">立即绑定</a></div>';
		}else{
			title = '<div style="text-align:center; color:red; text-decoration:underline; font-size:14px;">微信扫描二维码</div>';
			content = '<div id="popOverBox" align="center"><img style="width:200px; height:200px" src="'+obz.ctx+'/qrcode/genio/?url='+wirlessUrl+'"/><br/>手机端链接<br/><input value="'+wirlessUrl+'" style="width:220px;"></input></div>';
		}
		$(".fa-qrcode").popover({
            trigger:'manual',
            placement : 'right', //placement of the popover. also can use top, bottom, left or right
            title : title,
            html: 'true', //needed to show html of course
            content : content, 
            animation: false
        }).on("mouseenter", function () {
                    var _this = this;
                    $(this).popover("show");
                    $(this).siblings(".popover").on("mouseleave", function () {
                        $(_this).popover('hide');
                    });
                }).on("mouseleave", function () {
                    var _this = this;
                    setTimeout(function () {
                        if (!$(".popover:hover").length) {
                            $(_this).popover("hide");
                        }
                    }, 100);
                });
    }
};

//发送POST AJAX请求,返回JSON数据  wangjun
obz.ajaxJson = function(url, param, callback, failureCallback) {
    $.ajax({
        url: url,
        data: param,
        type: "POST",
        beforeSend: function () {
        },
        success: function (data) {
        	if (data.Success==undefined) {
        		//兼容 IE
            	data = $.parseJSON(data);
        	}
            if (data.Success) {
                if (callback != undefined) callback(data);
            } else {
            	if(failureCallback != undefined){
        			failureCallback();
        		}
            	//验证拦截器使用，把错误信息显示在前台
        		for(var key in data){
        			//$("#"+key).text(data[key]);
        			$("#"+key).addClass("has-error");
        			$("#"+key).find("label").text(data[key]);
        		}
           }
        },
        error: function (request) {
            if (failureCallback != undefined) failureCallback();
            else {
            	if(typeof request != 'undefined' && request){
            		if(request.response){
            			alert(request.response);
            		}else{
            			alert("服务端没有响应,请联系客服......");
            		}
            		location.href = obz.ctx;
            	}
            }
        }
    });
};

function isFloat(n) {
	return ((typeof n==='number')&&(n%1!==0));
}
/**
 * 一个用作js模板替换的代码 template格式和数组格式如下 var template = "<div>
 * <h1>{title}</h1>
 * <p>
 * {content}
 * </p>
 * </div>"; var data =
 * [{title:"a",content:"aaaa"},{title:"b",content:"bbb"},{title:"c",content:"ccc"}];
 * 只需要数据格式对应
 */
obz.dataTemplate = function(template, data, dec) {
	var outPrint = "";
	for ( var i = 0; i < data.length; i++) {
		var matchs = template.match(/\{[a-zA-Z0-9\.\$_-]+\}/gi);
		var temp = "";
		for ( var j = 0; j < matchs.length; j++) {
			if (temp == "")
				temp = template;
			var re_match = matchs[j].replace(/[\{\}]/gi, "");
			if(dec && isFloat(data[i][re_match])){
				temp = temp.replace(matchs[j], data[i][re_match].toFixed(dec));
			}
			else{
				temp = temp.replace(matchs[j], data[i][re_match]);
			}
			
		}
		outPrint += temp;
	}
	return outPrint;
};

obz.dataTemplate4obj = function(template, obj, dec){
	var matchs = template.match(/\{[a-zA-Z0-9\.\$_-]+\}/gi);
	var temp = "";
	for ( var j = 0; j < matchs.length; j++) {
		if (temp == "")
			temp = template;
		var re_match = matchs[j].replace(/[\{\}]/gi, "");
		if(dec && isFloat(obj[re_match])){
			temp = temp.replace(matchs[j], obj[re_match].toFixed(dec));
		}
		else{
			temp = temp.replace(matchs[j], obj[re_match]);
		}
		
	}
	return temp;
}

//弹出对话框组件开始，基于JqueryUi进行封装 默认不使用iframe 传true才使用iframe
//通过src远程加载对话框内容 
//src 远程地址
//div 对话框div id值
//callback 远程load后回调方法
//isIframe 是否使用 Iframe装载
//自动加载load mask遮罩层
obz.PopupDialog = function(src, div, callback, isIframe) {
    this.src = src;
    this.div = div;
    this.callback = callback;
    this.isIframe = isIframe;
}

obz.PopupDialog.prototype = {
	width: '90%',
	height: '100%',
	div: null,
	src: null,
	isIframe : null,
	popupDialog: null,
	callback : null,

	init: function() {
		$(document.body).css("overflow","hidden");
	    this.show();
	},

	show: function() {
		var newisIframe = this.isIframe;
		var newCallback = this.callback;
	    var newSrc = this.src;
	    var newDivid = this.div;
	    
	    if (newSrc.indexOf("?") < 0) {
	        newSrc += "?";
	    }
	    newSrc += "&_=" + new Date().valueOf().toString();
	             
	    var temWidth = $(window).width();
	    var temHeight = $(window).height();
	    if (temWidth >= 768) {
	        this.width = temWidth * 0.85;
	        this.height = temHeight * 0.9;
	    } else {
	        this.width = temWidth - 20;
	        this.height = temHeight - 20;
	    }
	      
	    var thisObject = this;
	    var newDiv = document.getElementById(newDivid);
	    
	    //只第一次创建div的时候才使用div去load页面，如果需要刷新，请手动删除
	    if (!newDiv) {
	        newDiv = document.createElement("DIV");
	        newDiv.setAttribute("id", newDivid);
	        if(this.isIframe && this.isIframe==true){//使用IFrame情况
	        	//创建IFrame
	        	var newFrame = document.getElementById("jqueryDialogFrame");
	        	if (!newFrame) {
		            newFrame = document.createElement("IFRAME");
		            newFrame.setAttribute("id", "jqueryDialogFrame");
		            newFrame.setAttribute("name", "jqueryDialogFrame");
		            newFrame.setAttribute("frameborder", "0");
		            newFrame.setAttribute("width", "100%");
		            newFrame.setAttribute("height", this.height-20);
		            newFrame.setAttribute("scrolling", "no");
		            newDiv.appendChild(newFrame);
		            document.body.appendChild(newDiv);
		        }
	        }else{//div直接Loading
	        	$(newDiv).mask("加载中...");
	        	if(newCallback){
	        		$(newDiv).load(newSrc, newCallback);
	        	}else{
	        		$(newDiv).load(newSrc);
	        	}
	        }
	    }

	    var openDialog = function() {
	    	if(newisIframe && newisIframe==true){
	    		var newFrame = document.getElementById("jqueryDialogFrame");
		        if (newFrame != null) {
		            setTimeout(function() { newFrame.setAttribute("src", newSrc); }, 100);
		        }
	    	}
	        $(".ui-dialog.ui-widget").css("position", "fixed");
	        $(".ui-dialog.ui-widget").css("top", "5%");
	    }
	    
	    var closePopupDialog = function() {
	    	if(newisIframe && newisIframe==true){
	    		var newFrame = document.getElementById("jqueryDialogFrame");
		        if (newFrame != null) {
		            newFrame.setAttribute("src", "");
		        }	    		
	    	}
	    	//关闭后移除div,每次都请求后台
	    	$(document.body).css("overflow","visible");
	    	$(newDiv).remove();
	    }
	      
	    this.popupDialog = $(newDiv).dialog({
	        modal: true,
	        //title: this.title,
	        //show: 'drop',
	        //hide: 'scale',
	        //minWidth: this.width,
	        //minHeight: this.height,
	        width: this.width,
	        height: this.height,
	        position: 'center',
	        draggable: false,
	        autoOpen: true,
	        resizable: false,
	        overlay: {
	            opacity: 0.5,
	            background: "black"
	        },
	        create:function(){
	        	$(this).closest(".ui-dialog").find(".ui-button").css({'height' : '28px'});
	        },
	        open: openDialog,
	        close: closePopupDialog,
	        zIndex: 15001
	     });
	},

	close: function() {
	    var result = false;
        if (this.popupDialog != null) {
            this.popupDialog.dialog("close");
            this.popupDialog = null;
            result = true;
        }
	    return result;
	}
}
//弹出框组件结束


//tab组件开始
//基于JQuery UI 的tab组件进行封装 wangjun
obz.TabView = function(div, orientation, selectCallback){
    this.div = div;
    this.orientation = orientation;
    this.selectFunction = selectCallback;
}

obz.TabView.prototype = {
    div: '',
    orientation: 'top',
    tabView: '',
    selectFunction: null, // callback function onSelect

    init : function(){
        //var thisObject = this;
    	if(this.selectFunction)
	    	this.tabView = $("#" + this.div).tabs({
				heightStyleType : "fill",
				activate : this.selectFunction
			});
    	else {
    		this.tabView = $("#" + this.div).tabs({heightStyleType : "fill"});
    	}
    },

    getTab : function(index){
        //return this.tabView.getTab(index);
    },

    getActiveViewIndex : function () {
    	var tabs = this.tabView.find("li");
    	var activeTab = this.getActiveTab();
    	return tabs.index(activeTab);
    },

    getActiveTab : function () {
    	var activeTab = this.tabView.find("li.ui-tabs-active:first");
    	if (activeTab.size() == 0) {
    		activeTab = undefined;
    	}
    	return activeTab;
    },
    
    select: function(index) {
//        this.tabView.tabs("option", "active", $(index).index());
    	this.tabView.tabs("option", "active", index);
//    	this.tabView.tabs("option", "select" , index);
    },

    disable: function(index) {
        this.tabView.tabs("disable", index);
    }
}
//tab组件结束

//分页组件开始
//基于jquery page 插件进行封装 wangjun
obz.PageView = function(pagediv, params, doDataListCallBack){
	this.pagediv = pagediv;
	this.params = params;
	this.doDataListCallBack = doDataListCallBack;
}

obz.PageView.prototype = {
	pagediv : null,
	params : null,
	doDataListCallBack : null,
	pageResult : null,
	
	pageClick : function(pageNo){
		this.query(pageNo);
	},
	
	init : function (){
		var pagediv = this.pagediv;
		var params = this.params;
		
		this.query();
	},
	
	query : function(currPage){
		if(currPage){
			params.currPage = currPage;
		}
		$("#"+this.pagediv).mask("数据加载中......");
		obz.ajaxJson(params, function(resp){
			var result = resp.data;
			if(currPage){
				$("#"+this.pagediv).pager({ pagenumber:currPage, recordcount:result.totalRecord, pagesize:result.pageSize, recordtext:'共 {0} 页, {1} 条记录', buttonClickCallback: this.pageClick });
			}else{
				$("#"+this.pagediv).pager({ recordcount:result.totalRecord, pagesize:result.pageSize, recordtext:'共 {0} 页, {1} 条记录', buttonClickCallback: this.pageClick });	
			}
			this.doDataListCallBack(result.records);
			$("#"+this.pagediv).unmask();
		});
	}
}
//分页组件结束

//选择奖品组件开始
obz.selectPrizeCallBack;
obz.selectDialog;
obz.doSelectPrzie = function(id, prizeName){
	if(obz.selectPrizeCallBack){
		obz.selectPrizeCallBack(id, prizeName);
	}
	if(selectDialog) selectDialog.close();
}
obz.selectPrize = function(params, callBack){
	selectDialog = BootstrapDialog.show({
		size: BootstrapDialog.SIZE_WIDE,
		title: "选择奖品",
        message: $('<div></div>').load(obz.ctx+'/prize/selectPrize')
    });
	obz.selectPrizeCallBack = callBack;
}
//选择奖品组件结束

//选择图片组件开始
obz.selectImageCallBack;
obz.selectImageDialog;
obz.doSelectImage = function(ImgPath, ImgUrl){
	if(obz.selectImageCallBack){
		obz.selectImageCallBack(ImgPath, ImgUrl);
	}
	if(selectImageDialog) selectImageDialog.close();
}
obz.selectImage = function(params, callBack){
	selectImageDialog = BootstrapDialog.show({
		size: BootstrapDialog.SIZE_WIDE,
		title: "选择图片",
        message: $('<div></div>').load(obz.ctx+'/product/addImage?model='+params.model)
    });
	obz.selectImageCallBack = callBack;
}
//选择图片组件结束
//上传图片
obz.uploadImageCallBack;
obz.uploadImageDialog;
obz.doUploadImage = function(obj){
	if(obz.uploadImageCallBack){
		obz.uploadImageCallBack(obj);
	}
	if(uploadImageDialog) uploadImageDialog.close();
}
obz.uploadImage = function(params, callBack){
	uploadImageDialog = BootstrapDialog.show({
		size: BootstrapDialog.SIZE_NORMAL,
		title: "上传图片",
        message: $('<div></div>').load(obz.ctx+'/product/addUploadImage?groupId='+params.groupId)
    });
	obz.uploadImageCallBack = callBack;
}
