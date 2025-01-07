package com.fastcms.web.security;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fastcms.common.utils.SnowFlake;
import com.fastcms.core.auth.FastcmsUserDetails;
import com.fastcms.entity.User;
import com.fastcms.entity.UserServerOpenid;
import com.fastcms.service.IUserServerOpenidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author wangjun
 **/
public class FastcmsJwtTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

    @Autowired
    private IUserServerOpenidService userServerOpenidService;

    @Override
    public void customize(JwtEncodingContext context) {
        RegisteredClient registeredClient = context.getRegisteredClient();
        Set<String> authorizedScopes = context.getAuthorizedScopes();
        User user = ((FastcmsUserDetails) context.getPrincipal().getPrincipal()).getUser();
        context.getClaims().claim(StandardClaimNames.NAME, user.getUserName());

        if (authorizedScopes.contains(OidcScopes.OPENID)) {
            context.getClaims().claim(StandardClaimNames.SUB, getOpenid(registeredClient.getClientId(), user.getUserName()));
        }

        if (authorizedScopes.contains(OidcScopes.PROFILE)) {
            context.getClaims().claim(StandardClaimNames.NICKNAME, user.getNickName());
            context.getClaims().claim(StandardClaimNames.PICTURE, user.getAvatar());
        }

        if (authorizedScopes.contains(OidcScopes.EMAIL)) {
            context.getClaims().claim(StandardClaimNames.EMAIL, user.getEmail());
        }

        if (authorizedScopes.contains(OidcScopes.PHONE)) {
            context.getClaims().claim(StandardClaimNames.PHONE_NUMBER, user.getMobile());
        }

    }

    private synchronized String getOpenid(String clientId, String username) {
        UserServerOpenid openid = userServerOpenidService.getOne(Wrappers.<UserServerOpenid>lambdaQuery().eq(UserServerOpenid::getClientId, clientId).eq(UserServerOpenid::getSub, username));
        if (openid == null) {
            openid = new UserServerOpenid();
            openid.setOpenid(String.valueOf(new SnowFlake(1, 1).genNextId()));
            openid.setClientId(clientId);
            openid.setSub(username);
            openid.setCreated(LocalDateTime.now());
            openid.setUpdated(LocalDateTime.now());
            userServerOpenidService.save(openid);
        }
        return openid.getOpenid();
    }

}
