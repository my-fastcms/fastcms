 <#if productList??> 
   <ul class="sc-goods-list clearfix size-1 card pic">
        <!-- 小图  -->
<#list productList as item>
                <li class="goods-card small-pic card">
                    <a href="${item.detailLink}" class="link js-goods clearfix">
                        <div class="photo-block">
                            <img class="goods-photo js-goods-lazy" src="${item.img}">
                        </div>
                            <div class="info clearfix info-no-title">
                                    <p class="goods-title">${item.name}</p>
                                    <p class="goods-price"><em>￥${item.price}</em></p>
                                    <p class="goods-price-taobao"></p>
                            </div>
                            <div class="goods-buy btn1">
                            </div>
                    </a>
                </li>
  </#list> 
    </ul>
    </#if>