  <#if productList??>
   <ul class="sc-goods-list clearfix size-3 card list">
        <!-- 大图  -->
        
            <!-- 列表 -->
            <#list productList as item>
                <li class="goods-card card">
                    <a href="${item.detailLink}" class="link js-goods clearfix">
                       <div class="photo-block">
                           <img class="goods-photo js-goods-lazy" src="${item.img}">
                       </div>
                       <div class="info">
                            <p class="goods-title">${item.name}</p>
                            <p class="goods-price"><em>￥${item.price}</em></p>
                            <p class="goods-price-taobao"></p>
                                <div class="goods-buy btn1"></div>
                       </div>
                    </a>
                </li>
        </#list> 
    </ul>
    </#if>