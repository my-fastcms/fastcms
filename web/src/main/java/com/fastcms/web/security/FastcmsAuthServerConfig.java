package com.fastcms.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.time.Duration;

/**
 * @author wangjun
 * http://localhost:8080/oauth2/authorize?response_type=code&client_id=nexfly-client&scope=profile&redirect_uri=http://127.0.0.1:6000/nexfly-auth/login/oauth2/code/nexfly-client&accessToken=eyJhbGciOiJIUzI1NiJ9.eyJhdXRoIjoiMSIsInVzZXJJZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTczMzgxMTczM30.nmQpbi4G14-Q-IiRtvtj0KOCvHeTrddK97Pf-W3Pvjg
 *
 * @see org.springframework.security.oauth2.server.authorization.web.OAuth2AuthorizationEndpointFilter
 * @see org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationProvider
 * @see org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationConsentAuthenticationProvider
 **/
@Configuration(proxyBeanMethods = false)
public class FastcmsAuthServerConfig {

    @Autowired
    private DelegatingTokenManager tokenManager;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
//        authorizationServerConfigurer.oidc(Customizer.withDefaults());
        authorizationServerConfigurer.authorizationEndpoint(a -> a.authorizationRequestConverter
                (new FastcmsOAuth2AuthorizationCodeRequestAuthenticationConverter(tokenManager)));

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        http
                .requestMatcher(endpointsMatcher)
                .authorizeRequests(authorize -> authorize.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .exceptionHandling(exceptions ->
                        exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/fastcms.html#/login"))
                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .opaqueToken(Customizer.withDefaults())
//                )
                .apply(authorizationServerConfigurer);

        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId("nexfly-client")
                .clientId("nexfly-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:6000/nexfly-auth/login/oauth2/code/nexfly-client")
//                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE) // 设置为引用令牌
                        .accessTokenTimeToLive(Duration.ofHours(1))     // 令牌有效期
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

}
