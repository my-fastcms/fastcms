 <div class="component">
 <ul class="custom-nav-4 clearfix">
<#list adList as item>
<li>
<a href="<#if item.adHref??>${item.adHref}<#else>javascript: void(0);</#if>" target="_blank">
    <span class="nav-img-wap">
        <img src="${item.imgPath}">
    </span>
<span class="title">${item.title}</span>
</a>
</li>
         </#list>
</ul>
</div>