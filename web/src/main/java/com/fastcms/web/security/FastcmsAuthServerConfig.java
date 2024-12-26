package com.fastcms.web.security;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

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
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http, FastcmsJwtTokenCustomizer jwtTokenCustomizer) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
        authorizationServerConfigurer.oidc(Customizer.withDefaults());
        authorizationServerConfigurer.authorizationEndpoint(a -> a.authorizationRequestConverter
                (new FastcmsOAuth2AuthorizationCodeRequestAuthenticationConverter(tokenManager)));

        RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

        http.setSharedObject(OAuth2TokenCustomizer.class, jwtTokenCustomizer);

        http
                .requestMatcher(endpointsMatcher)
                .authorizeRequests(authorize -> authorize.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .exceptionHandling(exceptions ->
                        exceptions.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/fastcms.html#/login"))
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .apply(authorizationServerConfigurer);

        return http.build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId("nexfly-client")
                .clientId("nexfly-client")
                .clientSecret("{noop}secret")
                .clientName("fastcms助手")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:6000/nexfly-auth/login/oauth2/code/nexfly-client")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public FastcmsJwtTokenCustomizer jwtTokenCustomizer() {
        return new FastcmsJwtTokenCustomizer();
    }

}
