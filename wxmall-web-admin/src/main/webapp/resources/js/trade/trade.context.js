//缓存trade js 客户端上下文对象 wangjun
var TContext = {
	recerInfo : null		//收件人信息
	,senderInfo : null		//发件人信息
	,tradeData : null		//交易数据包括明细数据  从后台返回的json对象
	,printData : null		//需要打印的数据 (mapkey获取)
	
	//设置需要打印的数据
	,setPrintData : function(index){
		//TODO 需要获取Checkbox选中的Order id 进行order过滤
		var contextData = TContext.tradeData;
		var addrMapkey = $("#mapkey_span_"+index).text();
		var mydata = contextData.mergeOrders[$.trim(addrMapkey)];
		TContext.printData = mydata;
	}
};