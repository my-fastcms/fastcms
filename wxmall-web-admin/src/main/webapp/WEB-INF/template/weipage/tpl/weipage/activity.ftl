<#if productList??>
 <div class="mobile-design">
<div class="ump-limitdiscount">  
<#list productList as item>
        <div class="ump-limitdiscount-good">
          <div class="good-time-info">
            <div class="good-image" style="background-image: url(${item.img});"></div>
            <div class="good-image-mask">
              <div class="mask-icon"></div>
            </div>
            <div class="discount-countdown">
              <div class="ump-type-text">限时折扣</div>
              <div class="js-countdown clearfix">
                <span class="countdown-title">距离折扣结束还剩</span>
                <span class="countdown-time timer"><span class="countdown-number countdown-day">D</span><span class="countdown-colon">:</span><span class="countdown-number countdown-hour">H</span><span class="countdown-colon">:</span><span class="countdown-number countdown-minute">M</span><span class="countdown-colon">:</span><span class="countdown-number countdown-second">S</span></span>
                <input id="db_end_timestamp" type="hidden" value="${item.expiresIn }"/>
              </div>
            </div>
          </div>
          <div class="good-detail clearfix">
            <div class="good-title overflow-to-ellipsis">
              <span class="discount-type">${item.zekou}折</span>
              <span class="title-text">${item.name}</span>
            </div>
            <div class="price">
              <span class="discount-price"><span class="yen">￥</span>${item.zekouPrice}</span>
              <span class="original-price"><span class="yen">￥</span>${item.price}</span>
            </div>
            <div class="good-detail-right">
              <a class="js-btn-good-link" href="${item.detailLink}">立即查看</a>
              <div class="stock overflow-to-ellipsis" style="display: none;">剩余：xxx件</div>
            </div>
          </div>
        </div>
</#list>
    </div>
  </div>
  </#if>