<#if productList??>
<ul class="sc-goods-list clearfix size-0 card pic">
        <!-- 大图  -->
<#list productList as item>
                <li class="goods-card big-pic card ">
                    <a href="${item.detailLink}" class="link js-goods clearfix">
                        <div class="photo-block">
                            <img class="goods-photo js-goods-lazy" src="${item.img}">
                        </div>
                            <div class="info clearfix info-title info-price btn1">
                                    <p class="goods-title">${item.name}</p>
                                    <p class="goods-sub-title c-black hide"></p>
                                    <p class="goods-price"><em>￥${item.price}</em></p>
                                    <p class="goods-price-taobao"></p>
                            </div>
                            <div class="goods-buy btn1 "></div>
                    </a>
                </li>
</#list>           
    </ul>
</#if>