 	<div class="swiper-container custom-swiper-wrap" data-space-between='10' data-pagination='.swiper-pagination' data-autoplay="1000">
		<div class="swiper-wrapper">
		<#list adList as item>
		 <div class="swiper-slide">
                      <a href="<#if item.adHref??>${item.adHref}<#else>javascript: void(0);</#if>"  target="_blank"> <img src="${item.imgPath}" height="300px"></a>
                </div>
         </#list>
	    </div>
	</div>