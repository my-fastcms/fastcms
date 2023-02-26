/**
 * Copyright (c) 广州小橘灯信息科技有限公司 2016-2017, wjun_java@163.com.
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * http://www.xjd2020.com
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fastcms.oauth2.endpoint;

import com.fastcms.oauth2.registration.FastcmsOAuth2ClientRegistration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.AbstractOAuth2AuthorizationGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * spring security 内置AbstractOAuth2AuthorizationGrantRequestEntityConverter为包级别，改成public
 * @author： wjun_java@163.com
 * @date： 2022/01/30
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractOAuth2AuthorizationGrantRequestEntityConverter<T extends AbstractOAuth2AuthorizationGrantRequest>
        implements Converter<T, RequestEntity<?>>, FastcmsOAuth2ClientRegistration, InitializingBean {

    // @formatter:off
    private Converter<T, HttpHeaders> headersConverter =
            (authorizationGrantRequest) -> OAuth2AuthorizationGrantRequestEntityUtils
                    .getTokenRequestHeaders(authorizationGrantRequest.getClientRegistration());
    // @formatter:on

    private Converter<T, MultiValueMap<String, String>> parametersConverter = this::createParameters;

    @Override
    public RequestEntity<?> convert(T authorizationGrantRequest) {
        HttpHeaders headers = getHeadersConverter().convert(authorizationGrantRequest);
        MultiValueMap<String, String> parameters = getParametersConverter().convert(authorizationGrantRequest);
        URI uri = UriComponentsBuilder
                .fromUriString(authorizationGrantRequest.getClientRegistration().getProviderDetails().getTokenUri())
                .build().toUri();
        return new RequestEntity<>(parameters, headers, HttpMethod.POST, uri);
    }

    /**
     * Returns a {@link MultiValueMap} of the parameters used in the OAuth 2.0 Access
     * Token Request body.
     * @param authorizationGrantRequest the authorization grant request
     * @return a {@link MultiValueMap} of the parameters used in the OAuth 2.0 Access
     * Token Request body
     */
    public abstract MultiValueMap<String, String> createParameters(T authorizationGrantRequest);

    /**
     * Returns the {@link Converter} used for converting the
     * {@link AbstractOAuth2AuthorizationGrantRequest} instance to a {@link HttpHeaders}
     * used in the OAuth 2.0 Access Token Request headers.
     * @return the {@link Converter} used for converting the
     * {@link OAuth2AuthorizationCodeGrantRequest} to {@link HttpHeaders}
     */
    final Converter<T, HttpHeaders> getHeadersConverter() {
        return this.headersConverter;
    }

    /**
     * Sets the {@link Converter} used for converting the
     * {@link AbstractOAuth2AuthorizationGrantRequest} instance to a {@link HttpHeaders}
     * used in the OAuth 2.0 Access Token Request headers.
     * @param headersConverter the {@link Converter} used for converting the
     * {@link OAuth2AuthorizationCodeGrantRequest} to {@link HttpHeaders}
     */
    public final void setHeadersConverter(Converter<T, HttpHeaders> headersConverter) {
        Assert.notNull(headersConverter, "headersConverter cannot be null");
        this.headersConverter = headersConverter;
    }

    /**
     * Add (compose) the provided {@code headersConverter} to the current
     * {@link Converter} used for converting the
     * {@link AbstractOAuth2AuthorizationGrantRequest} instance to a {@link HttpHeaders}
     * used in the OAuth 2.0 Access Token Request headers.
     * @param headersConverter the {@link Converter} to add (compose) to the current
     * {@link Converter} used for converting the
     * {@link OAuth2AuthorizationCodeGrantRequest} to a {@link HttpHeaders}
     */
    public final void addHeadersConverter(Converter<T, HttpHeaders> headersConverter) {
        Assert.notNull(headersConverter, "headersConverter cannot be null");
        Converter<T, HttpHeaders> currentHeadersConverter = this.headersConverter;
        this.headersConverter = (authorizationGrantRequest) -> {
            // Append headers using a Composite Converter
            HttpHeaders headers = currentHeadersConverter.convert(authorizationGrantRequest);
            if (headers == null) {
                headers = new HttpHeaders();
            }
            HttpHeaders headersToAdd = headersConverter.convert(authorizationGrantRequest);
            if (headersToAdd != null) {
                headers.addAll(headersToAdd);
            }
            return headers;
        };
    }

    /**
     * Returns the {@link Converter} used for converting the
     * {@link AbstractOAuth2AuthorizationGrantRequest} instance to a {@link MultiValueMap}
     * of the parameters used in the OAuth 2.0 Access Token Request body.
     * @return the {@link Converter} used for converting the
     * {@link OAuth2AuthorizationCodeGrantRequest} to a {@link MultiValueMap} of the
     * parameters
     */
    final Converter<T, MultiValueMap<String, String>> getParametersConverter() {
        return this.parametersConverter;
    }

    /**
     * Sets the {@link Converter} used for converting the
     * {@link AbstractOAuth2AuthorizationGrantRequest} instance to a {@link MultiValueMap}
     * of the parameters used in the OAuth 2.0 Access Token Request body.
     * @param parametersConverter the {@link Converter} used for converting the
     * {@link OAuth2AuthorizationCodeGrantRequest} to a {@link MultiValueMap} of the
     * parameters
     */
    public final void setParametersConverter(Converter<T, MultiValueMap<String, String>> parametersConverter) {
        Assert.notNull(parametersConverter, "parametersConverter cannot be null");
        this.parametersConverter = parametersConverter;
    }

    /**
     * Add (compose) the provided {@code parametersConverter} to the current
     * {@link Converter} used for converting the
     * {@link AbstractOAuth2AuthorizationGrantRequest} instance to a {@link MultiValueMap}
     * of the parameters used in the OAuth 2.0 Access Token Request body.
     * @param parametersConverter the {@link Converter} to add (compose) to the current
     * {@link Converter} used for converting the
     * {@link OAuth2AuthorizationCodeGrantRequest} to a {@link MultiValueMap} of the
     * parameters
     */
    public final void addParametersConverter(Converter<T, MultiValueMap<String, String>> parametersConverter) {
        Assert.notNull(parametersConverter, "parametersConverter cannot be null");
        Converter<T, MultiValueMap<String, String>> currentParametersConverter = this.parametersConverter;
        this.parametersConverter = (authorizationGrantRequest) -> {
            // Append parameters using a Composite Converter
            MultiValueMap<String, String> parameters = currentParametersConverter.convert(authorizationGrantRequest);
            if (parameters == null) {
                parameters = new LinkedMultiValueMap<>();
            }
            MultiValueMap<String, String> parametersToAdd = parametersConverter.convert(authorizationGrantRequest);
            if (parametersToAdd != null) {
                parameters.addAll(parametersToAdd);
            }
            return parameters;
        };
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        OAuth2AuthorizationGrantRequestEntityConverterManager.addRequestEntityConverter(getRegistrationId(), this);
    }

}
