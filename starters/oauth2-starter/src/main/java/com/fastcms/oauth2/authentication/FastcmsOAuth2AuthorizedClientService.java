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
package com.fastcms.oauth2.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientId;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义OAuth2授权信息存储服务
 * 支持在插件中扩展存储服务
 * @author： wjun_java@163.com
 * @date： 2022/02/03
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsOAuth2AuthorizedClientService implements OAuth2AuthorizedClientService {

    private final ClientRegistrationRepository clientRegistrationRepository;

    private final Map<String, OAuth2AuthorizedClientService> authorizedClientServices;

    /**
     * Constructs an {@code InMemoryOAuth2AuthorizedClientService} using the provided
     * parameters.
     * @param clientRegistrationRepository the repository of client registrations
     */
    public FastcmsOAuth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        Assert.notNull(clientRegistrationRepository, "clientRegistrationRepository cannot be null");
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authorizedClientServices = new ConcurrentHashMap<>();
    }

    /**
     * Constructs an {@code InMemoryOAuth2AuthorizedClientService} using the provided
     * parameters.
     * @param clientRegistrationRepository the repository of client registrations
     * @param authorizedClients the initial {@code Map} of authorized client(s) keyed by
     * {@link OAuth2AuthorizedClientId}
     * @since 5.2
     */
    public FastcmsOAuth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository,
                                                 Map<OAuth2AuthorizedClientId, OAuth2AuthorizedClient> authorizedClients) {
        Assert.notNull(clientRegistrationRepository, "clientRegistrationRepository cannot be null");
        Assert.notEmpty(authorizedClients, "authorizedClients cannot be empty");
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.authorizedClientServices = new ConcurrentHashMap<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId,
                                                                     String principalName) {
        Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");
        ClientRegistration registration = this.clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
        if (registration == null) {
            return null;
        }

        OAuth2AuthorizedClientService oAuth2AuthorizedClientService = this.authorizedClientServices.get(clientRegistrationId);
        if (oAuth2AuthorizedClientService != null) {
            return oAuth2AuthorizedClientService.loadAuthorizedClient(clientRegistrationId, principalName);
        }

        return null;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        Assert.notNull(authorizedClient, "authorizedClient cannot be null");
        Assert.notNull(principal, "principal cannot be null");
        OAuth2AuthorizedClientService oAuth2AuthorizedClientService = this.authorizedClientServices.get(authorizedClient.getClientRegistration().getRegistrationId());

        if (oAuth2AuthorizedClientService != null) {
            oAuth2AuthorizedClientService.saveAuthorizedClient(authorizedClient, principal);
        }

    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
        Assert.hasText(principalName, "principalName cannot be empty");
        ClientRegistration registration = this.clientRegistrationRepository.findByRegistrationId(clientRegistrationId);
        if (registration != null) {
            OAuth2AuthorizedClientService oAuth2AuthorizedClientService = this.authorizedClientServices.get(clientRegistrationId);
            if (oAuth2AuthorizedClientService != null) {
                oAuth2AuthorizedClientService.removeAuthorizedClient(clientRegistrationId, principalName);
            }
        }
    }

}
