package com.dbumama.market.web.mobile.qrcode.controller;

import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.render.QrcodeRender;

@RouteBind(path="qrcode")
public class QrcodeController extends BaseMobileController{
	
	//生成二维码图片流
	public void genio(){
		final String content = getPara("url");
	    render(new QrcodeRender(content));
	}
	
}
