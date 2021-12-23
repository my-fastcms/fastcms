package com.fastcms.wechat.autoconfigure;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import me.chanjar.weixin.common.session.StandardSessionManager;
import me.chanjar.weixin.common.session.WxSessionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({WxMaService.class})
public class WechatAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(WxMaService.class)
    public WxMaService wxMaService() {
        return new WxMaServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(WxSessionManager.class)
    public WxSessionManager wxSessionManager() {
        return new StandardSessionManager();
    }

}
