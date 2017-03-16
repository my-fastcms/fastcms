KISSY.use("node,dom",function(S,N,D){
	var $ = KISSY.all;
	var d = document;
	var dbumama = {};
	
	dbumama.pb = {
		dateString:function(date){
			var year = date.getFullYear();
			var month = date.getMonth()+1;
			var day =  date.getDate();
			var hour = date.getHours();
			var minute = date.getMinutes();
			var second = date.getSeconds();
			if(month<10){
				month = '0'+month;
			}
			if(day<10){
				day = '0'+day;
			}
			if(hour<10){
				hour = '0'+hour;
			}
			if(minute<10){
				minute = '0'+minute;
			}
			if(second<10){
				second = '0'+second;
			}
			return year+'-'+month+'-'+day+' '+hour+':'+minute+':'+second;
		}
	};
	
	dbumama.win = {
		boxc : function(config) {
			config = config||{};
			var id = config.id||'';
			$('#pub_box'+id).hide();
		},
		box : function(config) {
			var content = config.html || '';// 内容的html内容
			var exid = config.id||'';
			var id = 'pub_box'+exid;
			var box = D.get('#'+id);
			if(config.show){
				box.style.display='block';
				return;
			}
			if (!box) {
				box = d.createElement('div');
				box.id=id;
				box.className='pub_box';
				d.body.appendChild(box);
			}
			var _html ='';
			_html +='<div id="pub_box_container">';
			_html +='<div id="pub_box_content">'+content+'</div>';
			_html +='</div>';
			box.innerHTML = _html;
			//计算位置
			box.style.top = (d.documentElement.clientHeight/3 - $('#'+id).height()/2)+'px';
			box.style.display='block';
			return box;
		},
		alert:function(content,callback){
			var html =
				'<div class="db_alert">'+
					'<div class="content">'+content+'</div>'+
					'<div class="btns"><a class="btn">确定</a></div>'+
				'</div>';
			dbumama.win.bg();
			var win = dbumama.win.box({id:'_alert',html:html});
			$('.btn',win).on('click',function(){
				dbumama.win.boxc({id:'_alert'});
				dbumama.win.bgc();
				if(callback){
					callback();
				}
			});
		},
		bg:function(){
			var id = 'pub_bg';
			var bg = D.get('#'+id);
			if (!bg) {
				bg = d.createElement('div');
				bg.id=id;
				d.body.appendChild(bg);
			}
			bg.style.width = d.documentElement.clientWidth+'px';
			bg.style.height = d.documentElement.clientHeight+'px';
			bg.style.display='block';
			return bg;
		},
		bgc:function(){
			$('#pub_bg').hide();
		},
		waiting:function(){
			var id = 'pub_waiting';
			var win = D.get('#'+id);
			if (!win) {
				win = d.createElement('div');
				win.id=id;
				d.body.appendChild(win);
			}
			win.style.display='block';
			win.innerHTML = '<div class="waiting_box"><img src="/resources/images/loading.gif"/></div>';
			return win;
		},
		waitingc:function(){
			$('#pub_waiting').hide();
		},
		pageHeight:function(){
			return Math.max(d.body.clientHeight,d.documentElement.clientHeight);
		}
	};
	window.dbumama=dbumama;
	
	
	function showMessage(content,onclick){
		dbumama.win.bg();
		var _html = '';
		_html+='<div class="task_complete_box">';
		_html+='<div class="tips">'+content+'</div>';
		_html+='<div class="btns"><a class="btn btn_ok">确定</a></div>';
		_html+='</div>';
		var box = dbumama.win.box({html:_html});
		$('.btn_ok',box).on('click',function(){
			if(onclick){
				onclick();
			}else{
				dbumama.win.boxc();
				dbumama.win.bgc();
			}
		});
	}
	
	window.showMessage=showMessage;
});