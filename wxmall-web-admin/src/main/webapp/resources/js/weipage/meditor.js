
   
   //tempalate
   var tpl={};
   tpl['tpl-title'] = template.compile($('#tpl-title').html());
   tpl['tpl-search'] = template.compile($('#tpl-search').html());
   tpl['tpl-text-href'] = template.compile($('#tpl-text-href').html());
   tpl['tpl-goods-1'] = template.compile($('#tpl-goods-1').html());
   tpl['tpl-goods-2'] = template.compile($('#tpl-goods-2').html());
   tpl['tpl-goods-3'] = template.compile($('#tpl-goods-3').html());
   tpl['tpl-goods-4'] = template.compile($('#tpl-goods-4').html());
   tpl['tpl-goods-list-1'] = template.compile($('#tpl-goods-1').html());
   tpl['tpl-goods-list-2'] = template.compile($('#tpl-goods-2').html());
   tpl['tpl-goods-list-3'] = template.compile($('#tpl-goods-3').html());
   tpl['tpl-goods-list-4'] = template.compile($('#tpl-goods-4').html());
   tpl['tpl-ad'] = template.compile($('#tpl-ad').html());
   tpl['tpl-img-href'] = template.compile($('#tpl-img-href-panel').html());
   tpl['tpl-activity'] = template.compile($('#tpl-activity-panel').html());
   tpl['tpl-line'] = template.compile($('#tpl-line').html());
   
//颜色处理
	function rgb2hex(rgbcolor) {
		if(!rgbcolor){
			return rgbcolor;
		}
		
		rgb = rgbcolor.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
		function hex(x) {
			return ("0" + parseInt(x).toString(16)).slice(-2);
		}
		if(rgb){
		   return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
		}else {
			return rgbcolor;
		}
	}
	
  //default color
	 var def_bgColor="#efeff4";
     var def_hrefColor="#0000e0";
     var whiteColor="#ffffff";
     var blackColor="#000000";
     var lineColor="#e5e5e5";
 
   //goal var
    var g_productMap={}
    var components=[];
    var maxIndex=0;
    var g_deletIndex=null;
    var weiPageId= $("#weipage_id").val();
 //----------
     var $propbox=$('#prop-box');
     var $mobilebox=$('#mobile-design');
     var $actionbox=$('#action-box');
     
     //click event handle
     var editorCompnentClickHandle=function($this){
    	 if(!$this.hasClass("editing")){
	    	 $mobilebox.find(".edit-bar-editing").removeClass("edit-bar-editing");//.hide();
	    	 $this.find(".edit-bar").removeClass("edit-bar-over").addClass("edit-bar-editing");//show();
	    	 var position = $this.position();
	    	 var top=position.top ;
	    	 $actionbox.css("marginTop",top+"px");
	    	 $propbox.css("marginTop",top+"px");
	    	 
	    	 $mobilebox.find(".editing").removeClass("editing");
	    	 $this.addClass("editing");
	    	 
	    	 //prop panel
	    	 $propbox.find(".prop-panpel-show").removeClass("prop-panpel-show").addClass("prop-panpel-hide");
	    	 var proppanel=$this.data('componentType')+"-panel";
	    	 var $proppanel=$("#"+proppanel);
	    	 $proppanel.removeClass("prop-panpel-hide").addClass("prop-panpel-show");
	    	 initPropPanel($this,$proppanel);
    	 }
    	 return false;
     };
     
    //hover event
     var editorCompnentMouseOverHandle=function(){
	   	  $this=$(this);
	 	  if(!$this.hasClass("editing")){
		    	 $this.find(".edit-bar").addClass("edit-bar-over");//show();
	 	 }
     };
     var editorCompnentMouseOutHandle= function(){
    	 $this=$(this);
         $this.find(".edit-bar").removeClass("edit-bar-over");//.hide();
    	 
     };
     
     //delete event -------
     var editorCompnentMouseDeleteHandle=function(){
    	 $this=$(this);
    	 $com=$(this).parent().parent().parent();
    	 
    	//delete data
    	 g_deletIndex= $com.data('index');
    	 //---------
    	 var position = $com.position();
    	 
         pos={};
         pos.left=position.left+75;
         pos.top=position.top+$com.height()-25;
         $('#confirm-del-component').css("left",pos.left).css("top",pos.top).show();
    	
    	 return;
     }
     function editorCompnentMouseDelte(){
    	 if(!g_deletIndex){
    		 return;
    	 }
    	 $com=$("[data-index='"+g_deletIndex+"']");
    	 comlist=[];
    	 find=null;
    	 for(var i=0;i<components.length;i++){
    		 if(("inx_"+components[i].index)==g_deletIndex){
    			 find=components[i]; 
    		 }else {
    			 comlist.push(components[i]);
    		 } 
    	 }
    	 if(!find){
    		 return;
    	 }
    	 components=comlist;//replace
    	 
    	 var focus=null;
    	 if($com.next(".editor-compnent").size()>0){
    		 focus=$com.next(".editor-compnent");
    	 }else if($com.prev(".editor-compnent").size()>0){
    		 focus=$com.prev(".editor-compnent")
    	 }
    	 if(focus){
    		 focus.click();
    	 }else {
    		 $actionbox.css("marginTop","0");
	    	 $propbox.css("marginTop","0");
	    	 $propbox.find(".prop-panpel-show").removeClass("prop-panpel-show").addClass("prop-panpel-hide");
    	 }
    	 $com.remove();
    	 $('#confirm-del-component').hide();
     };
     
     function confirmDetete(){
    	 editorCompnentMouseDelte();
     }
     
     function cancelDetete(){
    	 $('#confirm-del-component').hide();
     }
     
     //添加模块----------
     $(".add-field-btn").click(function(){
    	 var action=$(this).data("action");
    	 var data={};
    	 //check tpl is exist ??
    	 if("goods"==action || "goods-list"==action){

    	 }else if(!tpl['tpl-'+action]){
    		  if("richtext"==action){
        		 data.bgColor=whiteColor;
        		 data.title="";
        	 }else{
    		 return;
        	 }
    	 }
    	 //default value
    	 if("title"==action){
    		 data.bgColor=whiteColor;
    		 data.color=blackColor;
    		 data.align="center";
    		 data.title="";
    		 data.subTitle="";
    	 }else if("search"==action){
    		 data.bgColor=def_bgColor;
    	 }else if("text-href"==action){
    		 data.link="";
    		 data.bgColor=def_bgColor;
    		 data.color=def_hrefColor;
    		 data.title="";
    		 data.outurl="";
    	 }else if("goods"==action){
    		 data.listStyle="1" ; //大图
    		 data.goodsList=[];
    		 data.itemStyle="1";
    		 data.showBuybtn=true;
    		 data.showName=true;
    		 data.showIntroduction=false;
    		 data.showPrice=true;
    		 
    	 }else if("goods-list"==action){
    		 data.listStyle="1" ; //大图
    		 data.listSize="6"
    		 data.goodsGroupName="";
    		 data.goodsGroupId="";
    		 data.goodsList=[];
    		 data.itemStyle="1";
    		 data.showBuybtn=true;
    		 data.showName=true;
    		 data.showIntroduction=false;
    		 data.showPrice=true;
    		 
    	 }else if("ad"==action){
    		 data.imgList=[];
    		 data.size="1" ; //大图
             data.show_method="1"; //折叠轮播 
 
    	 }else if("img-href"==action){
    		 data.imgList=[];
 
    	 }else if("activity"==action){
    		 data.goodsList=[];
    		 data.activityId="";
    		 data.activityName="";
    	 }else if("line"==action){
    		 data.lineColor=lineColor;
    		 data.lineType="solid";
    	 }
    	 componenthtml= getComponentHtml(action,data);
    	 maxIndex++;  //---
    	 //------------
    	 var component={};
    	 component.type=action;
    	 component.index=maxIndex;
    	 component.data=data;
    	 
    	
    	 var html=getEditComponentHtml(component,componenthtml);   
    	 //bind event
    	 $html=$(html).click(function(){
	    	 $this=$(this);
	    	 return editorCompnentClickHandle($this);
	     }).hover(editorCompnentMouseOverHandle,editorCompnentMouseOutHandle);
    	 
    	 $html.find(".delete").click(editorCompnentMouseDeleteHandle);
    	 var $curcom=$mobilebox.find(".editing");
    	 if($curcom.size()==1){
    		 $curcom.after($html);
    	 }else{
    		 $mobilebox.append($html);
    	 }
    	 
    	 components.push(component); //add 
    	 $html.click();
    	
    	 //scroll
    	  var cTop = $html.offset().top;
    	  var cHeight = $html.outerHeight(true);
    	  var windowTop = $(window).scrollTop();
    	  var visibleHeight = $(window).height();
    	  var h=$('#wrapper').scrollTop();
    	  if (cTop < windowTop) {
    		  $('#wrapper').animate({'scrollTop': h+cTop}, 'slow', 'swing');
    	  }else if (cTop + cHeight > windowTop + visibleHeight) {
    		  $('#wrapper').animate({'scrollTop': cTop - visibleHeight + cHeight+h+400}, 'slow', 'swing');
	   	  }else if(cTop+400> windowTop + visibleHeight){
	   		$('#wrapper').animate({'scrollTop': cTop - visibleHeight + cHeight+h+400}, 'slow', 'swing'); 
	   	  }	
     });
     
     function getComponentHtml(action,comData){
    	 if(action=="goods"){
    		 data={};
    		 data.showList=[];
    		 data.listStyle=comData.listStyle ; 
    		 data.itemStyle=comData.itemStyle;
    		 data.showBuybtn=comData.showBuybtn;
    		 data.showName=comData.showName;
    		 data.showIntroduction=comData.showIntroduction;
    		 data.showPrice=comData.showPrice;
    		 for(x=0;x<comData.goodsList.length;x++){
    			 p=g_productMap["p"+comData.goodsList[x]];
    			 if(p){
    				 var item={};
	    			 item.name=p.name;
	    			 item.introduction=p.introduction;
	    			 item.price="￥"+p.price;
	    			 item.image=p.img;
	    			 data.showList.push(item);
    			 }
    		 }
    		 if(data.listStyle=="1"||data.listStyle=="4"){
	    		 if(data.showList.length<1){
	    			 var item={};
	    			 item.name="#商品名称#";
	    			 item.introduction="#商品描述#";
	    			 item.price="￥88.88";
	    			 item.image=basePath+"/resources/css/images/wap/product_img_0.jpg";
	    			 data.showList.push(item);
	    		 }
    		 }else if(data.listStyle=="2"){
	    		 if(data.showList.length<4){
	    			 for(i=data.showList.length;i<4;i++){
		    			 var item={};
		    			 item.name="#商品名称#";
		    			 item.introduction="#商品描述#";
		    			 item.price="￥88.88";
		    			 item.image=basePath+"/resources/css/images/wap/product_img_"+i+".jpg";
		    			 data.showList.push(item);
	    		     }
	    		 }
    		 }else if(data.listStyle=="3"){
	    		 if(data.showList.length<3){
	    			 for(i=data.showList.length;i<3;i++){
		    			 var item={};
		    			 item.name="#商品名称#";
		    			 item.introduction="#商品描述#";
		    			 item.price="￥88.88";
		    			 item.image=basePath+"/resources/css/images/wap/product_img_"+i+".jpg";
		    			 data.showList.push(item);
	    		     }
	    		 }
    		 }
    		 
    		 html= tpl['tpl-'+action+"-"+data.listStyle](data);
    	 }else if(action=="goods-list"){
    		 data={};
    		 data.showList=[];
    		 data.listStyle=comData.listStyle ; 
    		 data.itemStyle=comData.itemStyle;
    		 data.showBuybtn=comData.showBuybtn;
    		 data.showName=comData.showName;
    		 data.showIntroduction=comData.showIntroduction;
    		 data.showPrice=comData.showPrice;
    		 if(data.listStyle=="2"){
	    			 for(var i=0;i<4;i++){
		    			 var item={};
		    			 item.name="#商品名称#";
		    			 item.introduction="#商品描述#";
		    			 item.price="￥88.88";
		    			 item.image=basePath+"/resources/css/images/wap/product_img_"+i+".jpg";
		    			 data.showList.push(item);
	    		     }
    		 }else{
	    			 for(var i=0;i<3;i++){
		    			 var item={};
		    			 item.name="#商品名称#";
		    			 item.introduction="#商品描述#";
		    			 item.price="￥88.88";
		    			 item.image=basePath+"/resources/css/images/wap/product_img_"+i+".jpg";
		    			 data.showList.push(item);
	    		     }

    		 }
    		 html= tpl['tpl-'+action+"-"+data.listStyle](data);
    	 }else if(action=="activity"){
    	
    		 data={};
    		 data.showList=[];
    		 for(x=0;x<comData.goodsList.length;x++){
    			 p=g_productMap["p"+comData.goodsList[x]];
    			 if(p){
    				 var item={};
	    			 item.name=p.name;
	    			 item.id=p.id;
	    			 item.introduction=p.introduction;
	    			 item.price="￥"+p.price;
	    			 item.image=p.img;
	    			 data.showList.push(item);
    			 }
    		 }
    		
    		 html= tpl['tpl-'+action](data);
    	 }else if(action=="richtext"){
             var htmlText='<div class="custom-richtext" style="background-color: '+comData.bgColor+'">';
             htmlText+=""+comData.title+"";
             htmlText+='</div>';
    		 html=htmlText;
    	 }else {
    	     html= tpl['tpl-'+action](comData);
    	 }
    	 return html;
     }
     function getEditComponentHtml(component,componenthtml){
    	 var html='<div class="editor-compnent"  data-index="inx_'+component.index+'" data-component-type="'+component.type+'">';
		         html+='<div class="component">';
		            html+=componenthtml
		         html+='</div>';
		         html+='<div class="edit-bar">';
			         html+='<div class="action-wrap"><span class="delete">删 除</span></div>';
			      html+='</div>';
			      html+='<div class="edit-tip"></div>';
		 html+='</div>';
		 return html;
     }
     
     function getData($com){
    	 var index= $com.data('index');
    	 for(var i=0;i<components.length;i++){
    		 if(("inx_"+components[i].index)==index){
    			 find=components[i];
    			 return find.data;
    		 }
    		 
    	 }
    	 return null;
     }
   
//init propPanel value
     function initPropPanel($com){
    	 var componentType= $com.data('componentType');
    	 var data=getData($com);
    	 if(componentType=='text-href'){
    		 initTextHerfPanel(data);
    	 }else if(componentType=='search'){
    		 initSearchPanel(data);
    	 }else if(componentType=='title'){
    		 initTitlePanel(data);
    	 }else if(componentType=='goods'){
    		 initGoodsPanel(data);
    	 }else if(componentType=='goods-list'){
    		 initGoodsListPanel(data);
    	 }else if(componentType=='ad'){
    		 initAdPanel(data);
    	 }else if(componentType=='img-href'){
    		 initImgHrefPanel(data);
    	 }else if(componentType=='activity'){
    		 initActivityPanel(data);
    	 }else if(componentType=='line'){
    		 initLinePanel(data);
    	 }else if(componentType=='richtext'){
    		 initRichtextPanel(data);
    	 }
    	 
    	 
     }
     
     function changeValueHandle(type,field,value){
    	 var $com=$mobilebox.find(".editing");
    	 if($com && $com.data('componentType')==type){
    		 var data=getData($com);
    		 data[field]=value;
    		 var html= getComponentHtml(type,data);
    		 $com.find('.component').html(html);
    	 }
     }
     
     //title............
     $('#title-main').change(function(){
    	 var value= $(this).val();
    	 changeValueHandle("title","title",value); 
     });
     
     $('#title-sub').change(function(){
    	 var value= $(this).val();
    	 changeValueHandle("title","subTitle",value); 
     });
     $('#title-bgcolor').change(function(){
    	 var value= $(this).val();
    	 changeValueHandle("title","bgColor",value); 
     });
    	 
     $('#title-color').change(function(){
    	 var value= $(this).val();
    	 changeValueHandle("title","color",value); 
     });
        
     $propbox.find("input[name='title-align']").click(function(){
    	 var value= $(this).val();
    	 changeValueHandle("title","align",value);
     });
     
     function initTitlePanel(data){
    	 var $proppanel=$('#title-panel');
    	 $proppanel.find("#title-main").val(data.title);
    	 $proppanel.find("#title-sub").val(data.subTitle);
    	 $proppanel.find("#title-bgcolor").val(rgb2hex(data.bgColor));
    	 $proppanel.find("#title-color").val(rgb2hex(data.color)); 
    	 $propbox.find("input[name='title-align'][value='"+data.align+"']").prop("checked",true);
     }
     
     
     //search............
     $('#search-bgcolor').change(function(){
    	 var value= $(this).val();
    	 changeValueHandle("search","bgColor",value); 
     });
     
     function initSearchPanel(data){
    	 var $proppanel=$('#search-panel');
    	 $proppanel.find("#search-bgcolor").val(rgb2hex(data.bgColor));
     }
     
     //line.........
     $('#line-bgcolor').change(function(){
    	 var value= $(this).val();
    	 changeValueHandle("line","lineColor",value); 
     });
     $propbox.find("input[name='lineType']").click(function(){
    	 var value= $(this).val();
    	 changeValueHandle("line","lineType",value);
     });
     function initLinePanel(data){
    	 var $proppanel=$('#line-panel');
    	 $propbox.find("input[name='lineType'][value='"+data.lineType+"']").prop("checked",true);
    	 $proppanel.find("#line-bgcolor").val(rgb2hex(data.lineColor));
     }
     
     //text-href............
     $('#text-href-title').change(function(){
    	 var value= $(this).val();
    	 changeValueHandle("text-href","title",value);  
     });
     
     $('#text-href-link').change(function(){
    	 var value= $(this).val();
    	 if(value=='9999'){
			 $('#text-href-outurl-div').show();
		 }else {
			 $('#text-href-outurl-div').hide();
		 }
    	 
    	 changeValueHandle("text-href","link",value); 
     });
     
     $('#text-href-outurl').change(function(){
    	 var value= $(this).val();
    	 changeValueHandle("text-href","outurl",value);
     });
     $('#text-href-bgcolor').change(function(){
    	 var value= $(this).val();
    	 changeValueHandle("text-href","bgColor",value);
     });
    	 
     $('#text-href-color').change(function(){
    	 var value= $(this).val();
    	 changeValueHandle("text-href","color",value);
     });
     
     function initTextHerfPanel(data){
    	 var $proppanel=$('#text-href-panel');
    	 $proppanel.find("#text-href-title").val(data.title);
    	 
    	 $proppanel.find("#text-href-link").find("option[value='"+data.link+"']").prop("selected",true);
    	 $proppanel.find("#text-href-bgcolor").val(rgb2hex(data.bgColor));
    	 $proppanel.find("#text-href-color").val(rgb2hex(data.color));
    	 $proppanel.find('#text-href-outurl').val(data.outurl);
    	 
    	 if(data.link=='9999'){
    		 $('#text-href-outurl-div').show();
    	 }else {
    		 $('#text-href-outurl-div').hide();
    	 }
    	 
     }
     
     //goods-----------
     $propbox.find("input[name='goods-list-style']").click(function(){
    	 var value= $(this).val();
    	 var $com=$mobilebox.find(".editing");
    	 if($com && $com.data('componentType')=="goods"){
    		 changeValueHandle("goods","listStyle",value);
    	 }
     });
     
     $propbox.find("input[name='goods-list-styles']").click(function(){
    	 var value= $(this).val();
    	 var $com=$mobilebox.find(".editing");
         if($com && $com.data('componentType')=="goods-list"){
    		 changeValueHandle("goods-list","listStyle",value);
    	 }
     });
     
     $propbox.find("input[name='goods-list-size']").click(function(){
    	 var value= $(this).val();
    	 changeValueHandle("goods-list","listSize",value);
     });
     
     $propbox.find("input[name='goods-item-style']").click(function(){
    	 var value= $(this).val();
    	 changeValueHandle("goods","itemStyle",value);
     });
     
     $propbox.find("input[name='goods-item-styles']").click(function(){
    	 var value= $(this).val();
    	 changeValueHandle("goods-list","itemStyle",value);
     });
     $propbox.find("input[name='goods-show-buybtn']").click(function(){
    	 var value= $(this).prop("checked");
    	 changeValueHandle("goods","showBuybtn",value);
     });
     $propbox.find("input[name='goods-show-name']").click(function(){
    	 var value= $(this).prop("checked");
    	 changeValueHandle("goods","showName",value);
     });
     $propbox.find("input[name='goods-show-introduction']").click(function(){
    	 var value= $(this).prop("checked");
    	 changeValueHandle("goods","showIntroduction",value);
     });
     $propbox.find("input[name='goods-show-price']").click(function(){
    	 var value= $(this).prop("checked");
    	 changeValueHandle("goods","showPrice",value);
     });
     
    
     
     function initGoodsPanel(data){
    	 var $proppanel=$('#goods-panel');
    	 $propbox.find("input[name='goods-list-style'][value='"+data.listStyle+"']").prop("checked",true);
    	 $propbox.find("input[name='goods-item-style'][value='"+data.itemStyle+"']").prop("checked",true);
    	 $propbox.find("input[name='goods-show-buybtn']").prop("checked",data.showBuybtn);
    	 $propbox.find("input[name='goods-show-name']").prop("checked",data.showName);
    	 $propbox.find("input[name='goods-show-introduction']").prop("checked",data.showIntroduction);
    	 $propbox.find("input[name='goods-show-price']").prop("checked",data.showPrice);
    	 
    	 addProductLi(data);//
     }
     

     //select product dialog event
     
     $propbox.find("#prop-goods-add").click(function(){
      	  $('#addGoodsModal').modal('show');
      	  searchProducts();
      })
       
     $("#productall").click(function(){
    		checked=$(this).prop("checked");
    		chkes=$(".productchk");
    		chkes.prop("checked" ,checked);
    		chkes.each(function(){
    			if(checked){
    				$(this).parent().parent().addClass("selectedtr");
    			}
    			else{
    				$(this).parent().parent().removeClass("selectedtr");
    			}
    		});	
    	});
    	var productPageClick = function(pageNo) {
    		searchProducts(pageNo);
    	}
    	
        var pageProductMap=null;//temp save page product  
    	var searchProducts = function(currPage){
    		var params = {};
    		params.rows=5;
    		if(currPage){
    			params.page = currPage;
    		}
    		$("#select-goods-div").mask("加载中...");
    		var url = obz.ctx + "/product/list";
    		var $com=$mobilebox.find(".editing");
    		var goodsId="";
       	    if($com && $com.data('componentType')=="activity"){
       	    	var data=getData($com);
       	    	params.promotionId=data.activityId;
       	    	if(data.activityId!=""){
       	    	url = obz.ctx + "/promotion/listDiscountProducts";
       	    	}
       	    }else if($com && $com.data('componentType')=="goods"){
       	          var data=getData($com);
		       	  for(r=0;r<data.goodsList.length;r++){
		 			 goodsId=goodsId+data.goodsList[r]+",";
		       	  }
		       	 goodsId=goodsId.substring(0,goodsId.length-1)
		       	 if(goodsId!=null&&goodsId!=""){
		       		params.productIds=goodsId;   
		       	 }
       	    }
       	   
       	    
    		obz.ajaxJson(url, params, function(resp){
    			$("#select-goods-div").unmask();
    			pageProductMap={};
    			var result = resp.data;
    			$(".productLotGrid").empty();
    			if(currPage){
    				$("#productpager").pager({ pagenumber:currPage, recordcount:result.totalRow, pagesize:result.pageSize, recordtext:'共 {0} 页, {1} 条记录', buttonClickCallback: productPageClick });
    			}else{
    				$("#productpager").pager({ recordcount:result.totalRow, pagesize:result.pageSize, recordtext:'共 {0} 页, {1} 条记录', buttonClickCallback: productPageClick });	
    			}
    			var dataList = result.list;
    			if(dataList.length>0){
    				for(var i=0;i<dataList.length;i++){
    					var _row = dataList[i];
    					pageProductMap["p"+_row.id]=_row;
    					var trHtml = obz.dataTemplate4obj($("#product_table_tr_tpl").html(), _row);
    					$(".productLotGrid").append(trHtml);
    					
    				}	
    			}
    			
    			$("#productall").prop("checked" ,false);
    			$(".productchk").click(function(){
    				checked=$(this).prop("checked");
    				if(checked){
    					$(this).parent().parent().addClass("selectedtr");
    				}
    				else{
    					$(this).parent().parent().removeClass("selectedtr");
    					$("#productall").prop("checked" ,false);
    				}
    			});
    		});
    	}
    	
    	$('#selectedgoodsbtn').click(function(){
    		var $com=$mobilebox.find(".editing");
       	    if($com && $com.data('componentType')=="goods"){
       	    	var data=getData($com);
	    		$('#select-goods-Table').find(".productchk").each(function(){
	    			if($(this).prop("checked")){
	    				var goodsId=$(this).data("id");	
	    				if(pageProductMap&&pageProductMap["p"+goodsId]){
	    					g_productMap["p"+goodsId]=pageProductMap["p"+goodsId];
	    					data.goodsList.push(goodsId);
	    					
	    				}
	    				
	    			}
	    		});
	    		
	    		 var html= getComponentHtml("goods",data);	    		 
	    		 $com.find('.component').html(html);
	    		 addProductLi(data);
       	    }else if($com && $com.data('componentType')=="activity"){
       	    	var data=getData($com);
       	    	$('#select-goods-Table').find(".productchk").each(function(){
	    			if($(this).prop("checked")){
	    				var goodsId=$(this).data("id");	
	    				if(pageProductMap&&pageProductMap["p"+goodsId]){
	    					g_productMap["p"+goodsId]=pageProductMap["p"+goodsId];
	    					data.goodsList.push(goodsId);
	    					
	    				}
	    				
	    			}
	    		});
       	     var html= getComponentHtml("activity",data);	    		 
    		 $com.find('.component').html(html);
       	    addDiscountProductLi(data);
       	    }
       	    
       	    //---
       	 $('#addGoodsModal').modal('hide');
    	});	
     //营销活动获取商品	
     function addDiscountProductLi(data){
    	 $("#prop-dgoods-list").off('click','.prop-goods-list-del');
    	 $("#prop-dgoods-list").find('.prop-goods-li').remove();
    	//---------
		 for(r=0;r<data.goodsList.length;r++){
			 goodsId=data.goodsList[r];
    		 var li='<li class="prop-goods-li">'
    		  li+='<a href="'+basePath+'/product/edit/'+goodsId+'" target="_blank">';
    		  li+='<img src="'+g_productMap["p"+goodsId].img+'" alt="商品图" width="50" height="50">';
    		  li+='</a>';
    		  li+='<a class="prop-goods-list-del" data-id="'+goodsId+'" title="删除">×</a>'
    		 li+='</li>'
    	     $('#prop-dgoods-add').before(li);	
		 }	
    	 
    	 $("#prop-dgoods-list").on("click",'.prop-goods-list-del',function(){
    	     var $com=$mobilebox.find(".editing");
      	     if($com && $com.data('componentType')=="activity"){
    	       var goodsId= $(this).data("id");
    	       var data=getData($com);
    	       finded=false;
    	       list=[];
    	       for(i=0;i<data.goodsList.length;i++){
    	    	   if((goodsId+"")===(data.goodsList[i]+"")){
    	    		   finded=true;
    	    	   }else {
    	    		   list.push(data.goodsList[i]);
    	    	   }
    	       }
    	       if(finded){
    	    	   data.goodsList=list;
    	    	   $(this).parent().remove();
    	    	   var html= getComponentHtml("activity",data);	    		 
  	    		   $com.find('.component').html(html);
    	       }
      	    }
    	});
    	 
     }	
     
     function addProductLi(data){
    	 $("#prop-goods-list").off('click','.prop-goods-list-del');
    	 $("#prop-goods-list").find('.prop-goods-li').remove();
    	//---------
		 for(r=0;r<data.goodsList.length;r++){
			 goodsId=data.goodsList[r];
    		 var li='<li class="prop-goods-li">'
    		  li+='<a href="'+basePath+'/product/edit/'+goodsId+'" target="_blank">';
    		  li+='<img src="'+g_productMap["p"+goodsId].img+'" alt="商品图" width="50" height="50">';
    		  li+='</a>';
    		  li+='<a class="prop-goods-list-del" data-id="'+goodsId+'" title="删除">×</a>'
    		 li+='</li>'
    	     $('#prop-goods-add').before(li);	
		 }	
    	 
    	 $("#prop-goods-list").on("click",'.prop-goods-list-del',function(){
    	     var $com=$mobilebox.find(".editing");
      	     if($com && $com.data('componentType')=="goods"){
    	       var goodsId= $(this).data("id");
    	       var data=getData($com);
    	       finded=false;
    	       list=[];
    	       for(i=0;i<data.goodsList.length;i++){
    	    	   if((goodsId+"")===(data.goodsList[i]+"")){
    	    		   finded=true;
    	    	   }else {
    	    		   list.push(data.goodsList[i]);
    	    	   }
    	       }
    	       if(finded){
    	    	   data.goodsList=list;
    	    	   $(this).parent().remove();
    	    	   var html= getComponentHtml("goods",data);	    		 
  	    		   $com.find('.component').html(html);
    	       }
      	    }
    	});
    	 
     }	
     
     //goods-list商品列表
     function initGoodsListPanel(data){
    	 var $proppanel=$('#goods-list-panel');
    	 $propbox.find("input[name='goods-list-size'][value='"+data.listSize+"']").prop("checked",true);
    	 $propbox.find("input[name='goods-list-styles'][value='"+data.listStyle+"']").prop("checked",true);
    	 $propbox.find("input[name='goods-item-styles'][value='"+data.itemStyle+"']").prop("checked",true);
    	 $propbox.find("input[name='goods-show-buybtn']").prop("checked",data.showBuybtn);
    	 $propbox.find("input[name='goods-show-name']").prop("checked",data.showName);
    	 $propbox.find("input[name='goods-show-introduction']").prop("checked",data.showIntroduction);
    	 $propbox.find("input[name='goods-show-price']").prop("checked",data.showPrice);
    	 addGroupTitle(data);
     }
     $propbox.find(".js-add-goods").click(function(){
     	  $('#addGroupModal').modal('show');
     	 searchGroup();
     });
     
     //商品分组列表
 	var groupPageClick = function(pageNo) {
 		searchGroup(pageNo);
	}
	var searchGroup = function(currPage){
		var params = {};
		params.rows=5;
		if(currPage){
			params.page = currPage;
		}
		$("#select-group-div").mask("加载中...");
		var url = obz.ctx + "/group/list";
		obz.ajaxJson(url, params, function(resp){
			$("#select-group-div").unmask();
			pageProductMap={};
			var result = resp.data;
			$(".groupLotGrid").empty();
			if(currPage){
				$("#grouppager").pager({ pagenumber:currPage, recordcount:result.totalRow, pagesize:result.pageSize, recordtext:'共 {0} 页, {1} 条记录', buttonClickCallback: groupPageClick });
			}else{
				$("#grouppager").pager({ recordcount:result.totalRow, pagesize:result.pageSize, recordtext:'共 {0} 页, {1} 条记录', buttonClickCallback: groupPageClick });	
			}
			var dataList = result.list;
			if(dataList.length>0){
				for(var i=0;i<dataList.length;i++){
					var _row = dataList[i];
					pageProductMap["p"+_row.id]=_row;
					var trHtml = obz.dataTemplate4obj($("#group_table_tr_tpl").html(), _row);
					$(".groupLotGrid").append(trHtml);
				}
				$('.js-choose').click(function(){
					var groupId=$(this).data("id");
					var groupName=$(this).data("title");
					var $com=$mobilebox.find(".editing");
			   	    if($com && $com.data('componentType')=="goods-list"){
			   	    	var data=getData($com);
			   	    	 data.goodsGroupName=groupName;
			    		 data.goodsGroupId=groupId;
			    		 $("#groupNameSelected").html(groupName);
			    		 $("#selectGroup").hide();
			    		 $("#selectedGroup").show();
			   	    }
			   	 $('#addGroupModal').modal('hide');
				});
			     
			}
		});
	}
	function addGroupTitle(data){
		  var $com=$mobilebox.find(".editing");
 	     if($com && $com.data('componentType')=="goods-list"){
 	    	if(data.goodsGroupName!=""){
 	    		 $("#groupNameSelected").html(data.goodsGroupName);
 	    		 $("#selectGroup").hide();
 	    		 $("#selectedGroup").show(); 
 	    	 }else{
 	    		 $("#selectGroup").show();
 	    		 $("#selectedGroup").hide();  
 	    	 }
 	     }
	
	}
     //添加图片广告  ---ad
     $propbox.find("input[name='show_method']").click(function(){
    	 var value= $(this).prop("value");
    	 changeValueHandle("ad","show_method",value);
     });
     
     $propbox.find("input[name='size']").click(function(){
    	 var value= $(this).prop("value");
    	 changeValueHandle("ad","size",value);
     });
     
     $("#choices").on("change",'#ImgTitle',function(){
    	 var value= $(this).val();
    	 var imgId= $(this).data("id");
    	 changeValueInto("ad",imgId,value); 
     });
     
     $("#choices").on("change",'.ad-href',function(){
    	 var value= $(this).val();
    	 var imgId= $(this).data("id");
    	 if(value=='custom'){
			 $('#text-href-outurl-'+imgId).show();
		 }else {
			 $('#text-href-outurl-'+imgId).hide();
		 }
    	 var $com=$mobilebox.find(".editing");
    	 if($com && $com.data('componentType')=="ad"){
    		 var data=getData($com);
    		 for(i=0;i<data.imgList.length;i++){
    	    	   if((imgId+"")==(data.imgList[i].id+"")){
    	    		 data.imgList[i].link=value;
    	    	   }
    	       }
    		 var html= getComponentHtml(type,data);
    		 $com.find('.component').html(html);
    	 }
     });
     
     $("#choices").on("change",'.herfUrl',function(){
    	 var value= $(this).val();
    	 var imgId= $(this).data("id");
    	 var $com=$mobilebox.find(".editing");
    	 if($com && $com.data('componentType')=="ad"){
    		 var data=getData($com);
    		 for(i=0;i<data.imgList.length;i++){
    	    	   if((imgId+"")==(data.imgList[i].id+"")){
    	    		 data.imgList[i].outhref=value;
    	    	   }
    	       }
    		 var html= getComponentHtml(type,data);
    		 $com.find('.component').html(html);
    	 }
     });
     
     function changeValueInto(type,imgId,value){
    	 var $com=$mobilebox.find(".editing");
    	 if($com && $com.data('componentType')==type){
    		 var data=getData($com);
    		 for(i=0;i<data.imgList.length;i++){
    	    	   if((imgId+"")==(data.imgList[i].id+"")){
    	    		 data.imgList[i].title=value;
    	    	   }
    	       }
    		 var html= getComponentHtml(type,data);
    		 $com.find('.component').html(html);
    	 }
     }
     
     function initAdPanel(data){
    	 var $proppanel=$('#ad-panel'); 
    	 $propbox.find("input[name='show_method'][value='"+data.show_method+"']").prop("checked",true);
    	 $propbox.find("input[name='size']").prop("checked",data.size);
    	 addChoiceImage(data);
     }
     
     $(".js-add-img").click(function(){
    	 var $com=$mobilebox.find(".editing");
		 if($com && $com.data('componentType')=="ad"){
    		 var data=getData($com);
		 
    		var params={};
    		params.model=false;
    		obz.selectImage(params, function (ImgPath, ImgUrl,ImgId) {
    			 var newlistImg=ImgPath.split(",");
    			 var newlistImgUrl=ImgUrl.split(",");
    			 var newImgId=ImgId.split(",");
    			 for (var i = 0; i < newImgId.length; i++) {
    				var entityImg = new Object();
					var entity = new Object();
					entity.id=newImgId[i];
					entity.imgPath=newlistImg[i];
					entity.title="";
					entity.href="";
					entity.link="";
					data.imgList.push(entity);
				}
    			 var html= getComponentHtml("ad",data);	    		 
	    		 $com.find('.component').html(html);
    			 
    			 addChoiceImage(data);
    		});
		 }else if($com && $com.data('componentType')=="img-href"){
			 var data=getData($com);
	    		var params={};
	    		params.model=false;
	    		obz.selectImage(params, function (ImgPath, ImgUrl,ImgId) {
	    			 var newlistImg=ImgPath.split(",");
	    			 var newlistImgUrl=ImgUrl.split(",");
	    			 var newImgId=ImgId.split(",");
	    			 for (var i = 0; i <newImgId.length; i++) {
	    				 if(i<=3){
	    					var entityImg = new Object();
	 						var entity = new Object();
	 						entity.id=newImgId[i];
	 						entity.imgPath=newlistImg[i];
	 						entity.title="";
	 						entity.href="";
	 						entity.link="";
	 						data.imgList.push(entity); 	
	    				 }
					}
	    			 var html= getComponentHtml("img-href",data);	    		 
		    		 $com.find('.component').html(html);
	    			 
		    		 addChoiceImageHref(data);
	    		});
		 }
     });
     
   //重新上传
	 $("#choices").on("click",'.modify-image',function(){
    	 var imgId=$(this).data("id");
    	 //alert(imgId);
    	 var $com=$mobilebox.find(".editing");
		 if($com && $com.data('componentType')=="ad"){
    		var data=getData($com);
    		var params={};
    		params.model=true;
    		obz.selectImage(params, function (ImgPath, ImgUrl,ImgId) {
    			 var newlistImg=ImgPath.split(",");
    			 var newlistImgUrl=ImgUrl.split(",");
    			 var newImgId=ImgId.split(",");
    			 for(i=0;i<data.imgList.length;i++){
      	    	   if((imgId+"")==(data.imgList[i].id+"")){
      	    		 data.imgList[i].id=newImgId[0];
      	    		 data.imgList[i].imgPath=newlistImg[0];
      	    	   }
      	       }
    			 var html= getComponentHtml("ad",data);	    		 
	    		 $com.find('.component').html(html);
    			 
    			 addChoiceImage(data);
    		});
		 }
     });
	 
     
    
     function addChoiceImage(data){
    	 $("#choices").find('.choice').remove();
    	 var $proppanel=$('#ad-panel'); 
    	 for(r=0;r<data.imgList.length;r++){
    		 var LiHtml = obz.dataTemplate4obj($("#tpl-ad-panel").html(), data.imgList[r]);  
    		 $("#choices").append(LiHtml);
    		 if(data.imgList[r].outhref){
    			 $("#text-href-outurl-"+data.imgList[r].id).show();
    		 }
    		 $proppanel.find("#ad-href-url-"+data.imgList[r].id).find("option[value='"+data.imgList[r].link+"']").prop("selected",true);
    	 }
    	 
    	 
    	 $("#choices").on("click",'.deleteImg',function(){
    		 var $com=$mobilebox.find(".editing");
      	     if($com && $com.data('componentType')=="ad"){
    	       var imgId= $(this).data("id");
    	       var data=getData($com);
    	       finded=false;
    	       list=[];
    	       for(i=0;i<data.imgList.length;i++){
    	    	   if((imgId+"")===(data.imgList[i].id+"")){
    	    		   finded=true;
    	    	   }else {
    	    		   list.push(data.imgList[i]);
    	    	   }
    	       }
    	       if(finded){
    	    	   data.imgList=list;
    	    	   $(this).parent().parent().remove();
    	    	   var html= getComponentHtml("ad",data);	    		 
  	    		   $com.find('.component').html(html);
    	       }
      	    }
    	 });
    	
     }
      
     //图片导航
     $("#choicesHref").on("change",'#ImgHrefTitle',function(){
    	 var value= $(this).val();
    	 var imgId= $(this).data("id");
    	 changeValueHrefInto("img-href",imgId,value); 
     });
     
     $("#choicesHref").on("change",'.imgHrefUrl',function(){
    	 var value= $(this).val();
    	 var imgId= $(this).data("id");
    	 var $com=$mobilebox.find(".editing");
    	 if($com && $com.data('componentType')=="img-href"){
    		 var data=getData($com);
    		 for(i=0;i<data.imgList.length;i++){
    	    	   if((imgId+"")==(data.imgList[i].id+"")){
    	    		 data.imgList[i].outhref=value;
    	    	   }
    	       }
    		 var html= getComponentHtml(type,data);
    		 $com.find('.component').html(html);
    	 }
     });
     
     $("#choicesHref").on("change",'.img-href',function(){
    	 var value= $(this).val();
    	 var imgId= $(this).data("id");
    	 if(value=='custom'){
			 $('#img-href-outurl-'+imgId).show();
		 }else {
			 $('#img-href-outurl-'+imgId).hide();
		 }
    	 var $com=$mobilebox.find(".editing");
    	 if($com && $com.data('componentType')=="img-href"){
    		 var data=getData($com);
    		 for(i=0;i<data.imgList.length;i++){
    	    	   if((imgId+"")==(data.imgList[i].id+"")){
    	    		 data.imgList[i].link=value;
    	    	   }
    	       }
    		 var html= getComponentHtml(type,data);
    		 $com.find('.component').html(html);
    	 }
     });
     
     function changeValueHrefInto(type,imgId,value){
    	 var $com=$mobilebox.find(".editing");
    	 if($com && $com.data('componentType')==type){
    		 var data=getData($com);
    		 for(i=0;i<data.imgList.length;i++){
    	    	   if((imgId+"")==(data.imgList[i].id+"")){
    	    		 data.imgList[i].title=value;
    	    	   }
    	       }
    		 var html= getComponentHtml(type,data);
    		 $com.find('.component').html(html);
    	 }
     }
     function initImgHrefPanel(data){
    	 var $proppanel=$('#img-href-panel'); 
    	 addChoiceImageHref(data);
     }
     
     function addChoiceImageHref(data){
    	 var $proppanel=$('#img-href-panel'); 
    	 $("#choicesHref").find('.choice').remove();
    	 for(var r=0;r<data.imgList.length;r++){
    		 if(r<=3){
        		 var LiHtml = obz.dataTemplate4obj($("#tpl-img-href-li-panel").html(), data.imgList[r]);  
        		 $("#choicesHref").append(LiHtml);	
        		 
        		 if(data.imgList[r].outhref){
        			 $("#img-href-outurl-"+data.imgList[r].id).show();
        		 }
        		 $proppanel.find("#img-href-url-"+data.imgList[r].id).find("option[value='"+data.imgList[r].link+"']").prop("selected",true);
    		 }

    	 }
    }
     
   //重新上传
	 $("#choicesHref").on("click",'.modify-image',function(){
    	 var imgId=$(this).data("id");
    	 //alert(imgId);
    	 var $com=$mobilebox.find(".editing");
		 if($com && $com.data('componentType')=="img-href"){
    		var data=getData($com);
    		var params={};
    		params.model=true;
    		obz.selectImage(params, function (ImgPath, ImgUrl,ImgId) {
    			 var newlistImg=ImgPath.split(",");
    			 var newlistImgUrl=ImgUrl.split(",");
    			 var newImgId=ImgId.split(",");
    			 for(i=0;i<data.imgList.length;i++){
      	    	   if((imgId+"")==(data.imgList[i].id+"")){
      	    		 data.imgList[i].id=newImgId[0];
      	    		 data.imgList[i].imgPath=newlistImg[0];
      	    	   }
      	       }
    			 var html= getComponentHtml("img-href",data);	    		 
	    		 $com.find('.component').html(html);
    			 
	    		 addChoiceImageHref(data);
    		});
		 }
     });
	 
	 //营销活动-----------------------
	 function initActivityPanel(data){
		 var $proppanel=$('#activity'); 
		 addDiscountTitle(data);
		 addDiscountProductLi(data);
	 }
	 $(".js-add-limited-discount-activity").click(function(){
		 $('#addDiscountModal').modal('show');
    		 searchDiscount();
	 });
     $("#selectProduct").find("#prop-dgoods-add").click(function(){
     	  $('#addGoodsModal').modal('show');
     	  searchProducts();
     });
 	var discountPageClick = function(pageNo) {
 		searchDiscount(pageNo);
	}
 	var searchDiscount = function(currPage){
		var params = {};
		params.rows=5;
		if(currPage){
			params.page = currPage;
		}
		$("#select-discount-div").mask("加载中...");
		var url = obz.ctx + "/promotion/list";
		obz.ajaxJson(url, params, function(resp){
			$("#select-discount-div").unmask();
			pageProductMap={};
			var result = resp.data;
			$(".discountLotGrid").empty();
			if(currPage){
				$("#discountpager").pager({ pagenumber:currPage, recordcount:result.totalRow, pagesize:result.pageSize, recordtext:'共 {0} 页, {1} 条记录', buttonClickCallback: discountPageClick });
			}else{
				$("#discountpager").pager({ recordcount:result.totalRow, pagesize:result.pageSize, recordtext:'共 {0} 页, {1} 条记录', buttonClickCallback: discountPageClick });	
			}
			var dataList = result.list;
			if(dataList.length>0){
				for(var i=0;i<dataList.length;i++){
					var _row = dataList[i];
					var trHtml = obz.dataTemplate4obj($("#discount_table_tr_tpl").html(), _row);
					$(".discountLotGrid").append(trHtml);
					
				}	
			}
			$(".discountchk").click(function(){
				checked=$(this).prop("checked");
				if(checked){
					$(this).parent().parent().addClass("selectedtr");
				}
				else{
					$(this).parent().parent().removeClass("selectedtr");
				}
			});
		});
	}
 	
	$('#selectedDiscountbtn').click(function(){
		var $com=$mobilebox.find(".editing");
   	    if($com && $com.data('componentType')=="activity"){
   	    	var data=getData($com);
    		$('#select-discount-Table').find(".discountchk").each(function(){
    			if($(this).prop("checked")){
    				var Id=$(this).data("id");
    				var Name=$(this).data("name");
    				 data.activityId=Id;
    	    		 data.activityName=Name;
    			}
    		});
    		 addDiscountTitle(data);
   	    }
   	 $('#addDiscountModal').modal('hide');
	});
	
	function addDiscountTitle(data){
		  var $com=$mobilebox.find(".editing");
   	     if($com && $com.data('componentType')=="activity"){
   	    	 if(data.activityName!=""){
   	    		 $("#addActivity").hide();
   	    		 $("#selectProduct").show();
   	    		 $("#Activitied").show();
   	    		$("#Activitied").empty();
   	    		 var html='<label class="prop-label">已选活动：</label><div style="float: right;"><div class="pull-left"> <span class="label label-success link-to-title" style="padding: 3px 10px;">';
   	    			 html+='<em>'+data.activityName+'</em></span></div><a class="js-change-activity pull-left" data-id="'+data.activityId+'" href="javascript:;">修改</a></div>';
   	    			 $("#Activitied").append(html);
   	    	 }else{
   	    		 $("#Activitied").hide();
   	    		 $("#addActivity").show();
   	    		 $("#selectProduct").hide(); 
   	    	 }
   	    	
   	     }
	}
	
	//富文本
	
	var ue = {};
	var e = {
		    __init: function () {
		        //e.__registerUI();
		        ue = UE.getEditor('editor',{
		toolbars: [
					["bold", "italic", "underline", "strikethrough", "forecolor", "backcolor", "justifyleft", "justifycenter", "justifyright", "|", "insertunorderedlist", "insertorderedlist", "blockquote",
					"simpleupload", "insertimage", "insertvideo", "link", "removeformat", "|", "rowspacingtop", "rowspacingbottom", "lineheight", "paragraph", "fontsize",
					"inserttable", "deletetable", "insertparagraphbeforetable", "insertrow", "deleterow", "insertcol", "deletecol", "mergecells", "mergeright", "mergedown", "splittocells", "splittorows", "splittocols"]
				],
				autoClearinitialContent: false,
				enableAutoSave: false,
				autoFloatEnabled: !0,
				wordCount: !1,
				elementPathEnabled: !1,
				initialFrameHeight: 300, 
				focus: !1,
				pasteplain: !1
	           });
		        this.__addListener();
		    },
		    __addListener: function () {
		        ue.addListener("blur", function () {
		            changeValueHandle("richtext","title",ue.getContent());
		        })
		    }
	}
	 function initRichtextPanel(data){
   	 var $proppanel=$('#richtext-panel');
   	 ue.setContent(data.title, false);
   	 $proppanel.find("#richtext-bgcolor").val(rgb2hex(data.bgColor));
    }
	
	 $('#richtext-bgcolor').change(function(){
    	 var value= $(this).val();
    	 changeValueHandle("richtext","bgColor",value); 
     });
     //-------------------------------
     
   //颜色重置统一处理
     $propbox.find(".resetHerfColorBtn").click(function(){
    	 var refid=$(this).data("refId");
    	 $("#"+refid).val(def_hrefColor).change();
     });
     
     $propbox.find(".resetBgColorBtn").click(function(){
    	 var refid=$(this).data("refId");
    	 $("#"+refid).val(def_bgColor).change();
     });
     
     $propbox.find(".resetRichtextColorBtn").click(function(){
    	 var refid=$(this).data("refId");
    	 $("#"+refid).val(whiteColor).change();
     });
     
     $propbox.find(".resetWhiteBgColorBtn").click(function(){
    	 var refid=$(this).data("refId");
    	 $("#"+refid).val(whiteColor).change();
     });
     $propbox.find(".resetBlackColorBtn").click(function(){
    	 var refid=$(this).data("refId");
    	 $("#"+refid).val(blackColor).change();
     });
     $propbox.find(".resetLineColorBtn").click(function(){
    	 var refid=$(this).data("refId");
    	 $("#"+refid).val(lineColor).change();
     })
     
    //校验方法
    var _showVaildMsg=function(_$el,msg){
		_$el.addClass("error");
		_id=_$el.attr("id");
		if(_id&&_id!=''){
			_$el.parent().find("label[for='"+_id+"']").remove();
			_$el.parent().append('<label id="'+_id+'-error" class="error" for="'+_id+'">'+msg+'</label>');
		}else {
			_$el.attr("title",msg);
		}
	};

	var  _removeVaildMsg= function(_$el){
		_$el.removeClass("error");
		_id=_$el.attr("id");
		_$el.parent().find("label[for='"+_id+"']").remove();	
	};
	
	var _bindChange=function(_$el){
			_$el.change(function(){
				if($(this).hasClass("error")){
					checkEl(this);
				}
			});
			
	}
	var required=function(_$el,msg) {
		_value=$.trim(_$el.val());
		if(_value.length==0){
			_showVaildMsg(_$el,msg||"这是必填字段");
		}else {
			_removeVaildMsg(_$el);
		}
		
		return _value.length > 0;
	};
	var checkEl=function(domEle){
		//1. required
		$el=$(domEle);
		bl=true;
		if($el.attr("required")){
			msg=$el.data("msgRequired");
		    bl=required($el,msg) && bl;
		}
		if(bl){
			if($el.data("refRequired")){
				if($el.data("refId")&&$el.data("refValue")){
					
					if($('#'+$el.data("refId")).val()===$el.data("refValue")){
						msg=$el.data("msgRequired");
					    bl=required($el,msg) && bl;
					}    
				}	    
			}
		}
		
		return bl;
	};
	 //check 保存校验......
	//规则
	var validRules={
			"title":{required:[{field:"title",msg:"标题不能空",id:"title-main"}]
	                },
            "text-href":{required:[{field:"title",msg:"导航文字不能空",id:"text-href-title"},
                                   {field:"link",msg:"必选",id:"text-href-link"}],
                         ref_required:[{field:"outurl",msg:"链接URL不能空",id:"text-href-outurl",refId:"text-href-link",refField:"link",refValue:"9999"}]          
            		}        
	};
	for(rule in validRules){ 
		var binded={}
		if(validRules[rule].required){
			for(i=0;i<validRules[rule].required.length;i++){
				var ref=validRules[rule].required[i];
				$el=$('#'+ref.id);
				if(!binded["R"+ref.id]){
					$el.attr("required",true);
					$el.data("msgRequired",ref.msg);
					_bindChange($el);
					binded["R"+ref.id]=true;
				}	
			}
		}	
		if(validRules[rule].ref_required){
			for(i=0;i<validRules[rule].ref_required.length;i++){
				var ref=validRules[rule].ref_required[i];
				$el=$('#'+ref.id);
				if(!binded["R"+ref.id]){
					$el.data("refRequired",true);
					$el.data("msgRequired",ref.msg);
					$el.data("refId",ref.refId);
					$el.data("refValue",ref.refValue);
					_bindChange($el);
					binded["R"+ref.id]=true;
				}	
			}
		}	
	} 
	
	
    function  checkComponents(){
    	 for(var n=0;n<components.length;n++){
    		 var component=components[n];
    		 var data=component.data;
    		 var bl=true;
    		 if(validRules[component.type]){
    			 if(validRules[component.type].required && validRules[component.type].required.length>0){
    				 
    				 for(i=0;i<validRules[component.type].required.length;i++){
    					 var ref=validRules[component.type].required[i];
    					 if(!data[ref.field] || data[ref.field]==''){
    						 bl=bl&&false;
    						 $el=$('#'+ref.id);
    						 _showVaildMsg($el,ref.msg);
    	    			 }
    					 
    				 }
    				
    				 if(!bl){
						 var index="inx_"+component.index;
	    				 $("[data-index='"+index+"']").click();
	    				 return false;
					 }
    			 }
    			 
    			 //other rule
				if(validRules[component.type].ref_required && validRules[component.type].ref_required.length>0){		 
    				 for(i=0;i<validRules[component.type].ref_required.length;i++){
    					 var ref=validRules[component.type].ref_required[i];
    					 if(data[ref.refField]==ref.refValue){
	    					 if(!data[ref.field] || data[ref.field]==''){
	    						 bl=bl&&false;
	    						 $el=$('#'+ref.id);
	    						 _showVaildMsg($el,ref.msg);
	    	    			 }
    					 }
    				 }
    				
    				 if(!bl){
						 var index="inx_"+component.index;
	    				 $("[data-index='"+index+"']").click();
	    				 return false;
					 }
    			 }
    			 
    		 }
    	 }
    	
    	 return true;
     }
    
    function savePage(){
 	  var bl= checkComponents();
 	  if(bl){
 		     param={};
 		     param.id=weiPageId;
 		     param.content=JSON.stringify(components);
	 		 $("#main-container").mask("正在保存数据...");
	 		 obz.ajaxJson(obz.ctx+"/weipage/save", param, function(resp){
				$("#main-container").unmask();
				if(resp.code != 200){
					obz.error(resp.msg);
					return false;
				}else {
					obz.msg("保存成功！");
				}
			});
 		   
 	  }
 	    
    }
    
    function preview(){
   	     var id= $("#weipage_id").val();
       	 window.open(basePath+"/weipage/preview?id="+id,"width=500,height=700,toolbar=no,menubar=no");
    }

    //读数据？？
     param={};
     param.id=weiPageId;
	 $("#main-container").mask("正在读取数据...");
	 obz.ajaxJson(obz.ctx+"/weipage/getContentById", param, function(resp){
		$("#main-container").unmask();
		if(resp.code != 200){
			obz.error(resp.msg);
			return false;
		}
		if(resp.data){
			if(resp.data.products){
				for(i=0;i<resp.data.products.length;i++){
					goods=resp.data.products[i];
					g_productMap["p"+goods.id]=goods;
				}
			}
			components=JSON.parse(resp.data.content);
			for(i=0;i<components.length;i++){
				
				 maxIndex++; //
				 components[i].index=maxIndex;//!
				 
				 if(components[i].type=="goods"){
					 if(!components[i].goodsList){
						 components[i].goodsList=[];
		    		 }
				 }else if(components[i].type=="activity"){
					 if(!components[i].goodsList){
						 components[i].goodsList=[];
		    		 }
				 }
		    	 componenthtml= getComponentHtml(components[i].type,components[i].data);	
		    	 $mobilebox.append(getEditComponentHtml(components[i],componenthtml));
		    	  //bind event........
		         $mobilebox.find('.editor-compnent').click(function(){
		        	 $this=$(this);
		        	 return editorCompnentClickHandle($this);
		         });
		         $mobilebox.find('.editor-compnent').hover(editorCompnentMouseOverHandle,editorCompnentMouseOutHandle);
		         
		         $(".delete").click(editorCompnentMouseDeleteHandle);
		    	 
		    }
	    }
	});
    
   
     
     
     