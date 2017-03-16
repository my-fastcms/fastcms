function bindtap(iden){
	jQuery("."+iden).bind('tap', function() {
	  //打开关于页面
		window.location.href=jQuery(this).attr('href')
	});
}