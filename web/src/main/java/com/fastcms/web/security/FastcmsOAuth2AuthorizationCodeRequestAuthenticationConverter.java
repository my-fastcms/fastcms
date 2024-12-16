package com.fastcms.web.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.keygen.Base64StringKeyGenerator;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationResponseType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author wangjun
 * @see org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeRequestAuthenticationConverter
 **/
public class FastcmsOAuth2AuthorizationCodeRequestAuthenticationConverter implements AuthenticationConverter {

    private final TokenManager tokenManager;

    public FastcmsOAuth2AuthorizationCodeRequestAuthenticationConverter(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    private static final String DEFAULT_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-4.1.2.1";
    private static final String PKCE_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc7636#section-4.4.1";
    private static final Authentication ANONYMOUS_AUTHENTICATION = new AnonymousAuthenticationToken(
            "anonymous", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
    private static final RequestMatcher OIDC_REQUEST_MATCHER = createOidcRequestMatcher();

    private static final StringKeyGenerator DEFAULT_STATE_GENERATOR = new Base64StringKeyGenerator(Base64.getUrlEncoder());

    @Override
    public Authentication convert(HttpServletRequest request) {
        if (!"GET".equals(request.getMethod()) && !OIDC_REQUEST_MATCHER.matches(request)) {
            return null;
        }

        // 由于是 jwt无状态认证，需要在这里设置用户认证信息
        final String jwt = tokenManager.resolveToken(request);
        try {
            tokenManager.validateToken(jwt);
            Authentication authentication = this.tokenManager.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {

        }

        MultiValueMap<String, String> parameters = getParameters(request);

        // response_type (REQUIRED)
        String responseType = request.getParameter(OAuth2ParameterNames.RESPONSE_TYPE);
        if (!StringUtils.hasText(responseType) ||
                parameters.get(OAuth2ParameterNames.RESPONSE_TYPE).size() != 1) {
            throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.RESPONSE_TYPE);
        } else if (!responseType.equals(OAuth2AuthorizationResponseType.CODE.getValue())) {
            throwError(OAuth2ErrorCodes.UNSUPPORTED_RESPONSE_TYPE, OAuth2ParameterNames.RESPONSE_TYPE);
        }

        String authorizationUri = request.getRequestURL().toString();

        // client_id (REQUIRED)
        String clientId = parameters.getFirst(OAuth2ParameterNames.CLIENT_ID);
        if (!StringUtils.hasText(clientId) ||
                parameters.get(OAuth2ParameterNames.CLIENT_ID).size() != 1) {
            throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.CLIENT_ID);
        }

        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if (principal == null) {
            principal = ANONYMOUS_AUTHENTICATION;
        }

        // redirect_uri (OPTIONAL)
        String redirectUri = parameters.getFirst(OAuth2ParameterNames.REDIRECT_URI);
        if (StringUtils.hasText(redirectUri) &&
                parameters.get(OAuth2ParameterNames.REDIRECT_URI).size() != 1) {
            throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.REDIRECT_URI);
        }

        // scope (OPTIONAL)
        Set<String> scopes = null;
        String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
        if (StringUtils.hasText(scope) &&
                parameters.get(OAuth2ParameterNames.SCOPE).size() != 1) {
            throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.SCOPE);
        }
        if (StringUtils.hasText(scope)) {
            scopes = new HashSet<>(
                    Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
        }

        // state (RECOMMENDED)
        String state = parameters.getFirst(OAuth2ParameterNames.STATE);
        if (StringUtils.hasText(state) &&
                parameters.get(OAuth2ParameterNames.STATE).size() != 1) {
            throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.STATE);
        }

        // code_challenge (REQUIRED for public clients) - RFC 7636 (PKCE)
        String codeChallenge = parameters.getFirst(PkceParameterNames.CODE_CHALLENGE);
        if (StringUtils.hasText(codeChallenge) &&
                parameters.get(PkceParameterNames.CODE_CHALLENGE).size() != 1) {
            throwError(OAuth2ErrorCodes.INVALID_REQUEST, PkceParameterNames.CODE_CHALLENGE, PKCE_ERROR_URI);
        }

        // code_challenge_method (OPTIONAL for public clients) - RFC 7636 (PKCE)
        String codeChallengeMethod = parameters.getFirst(PkceParameterNames.CODE_CHALLENGE_METHOD);
        if (StringUtils.hasText(codeChallengeMethod) &&
                parameters.get(PkceParameterNames.CODE_CHALLENGE_METHOD).size() != 1) {
            throwError(OAuth2ErrorCodes.INVALID_REQUEST, PkceParameterNames.CODE_CHALLENGE_METHOD, PKCE_ERROR_URI);
        }

        Map<String, Object> additionalParameters = new HashMap<>();
        parameters.forEach((key, value) -> {
            if (!key.equals(OAuth2ParameterNames.RESPONSE_TYPE) &&
                    !key.equals(OAuth2ParameterNames.CLIENT_ID) &&
                    !key.equals(OAuth2ParameterNames.REDIRECT_URI) &&
                    !key.equals(OAuth2ParameterNames.SCOPE) &&
                    !key.equals(OAuth2ParameterNames.STATE)) {
                additionalParameters.put(key, value.get(0));
            }
        });

        return new OAuth2AuthorizationCodeRequestAuthenticationToken(authorizationUri, clientId, principal,
                redirectUri, state, scopes, additionalParameters);
    }

    private static RequestMatcher createOidcRequestMatcher() {
        RequestMatcher postMethodMatcher = request -> "POST".equals(request.getMethod());
        RequestMatcher responseTypeParameterMatcher = request ->
                request.getParameter(OAuth2ParameterNames.RESPONSE_TYPE) != null;
        RequestMatcher openidScopeMatcher = request -> {
            String scope = request.getParameter(OAuth2ParameterNames.SCOPE);
            return StringUtils.hasText(scope) && scope.contains(OidcScopes.OPENID);
        };
        return new AndRequestMatcher(
                postMethodMatcher, responseTypeParameterMatcher, openidScopeMatcher);
    }

    private static void throwError(String errorCode, String parameterName) {
        throwError(errorCode, parameterName, DEFAULT_ERROR_URI);
    }

    private static void throwError(String errorCode, String parameterName, String errorUri) {
        OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName, errorUri);
        throw new OAuth2AuthorizationCodeRequestAuthenticationException(error, null);
    }

    MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            if (values.length > 0) {
                for (String value : values) {
                    parameters.add(key, value);
                }
            }
        });
        return parameters;
    }

}
