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
package com.fastcms.oauth2.registration;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 覆写 Spring Security的InMemoryClientRegistrationRepository
 * 增加支持在插件中动态注册 ClientRegistration
 * @author： wjun_java@163.com
 * @date： 2022/03/02
 * @description：
 * @modifiedBy：
 * @version: 1.0
 * @see org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
 */
public final class FastcmsInMemoryClientRegistrationRepository implements ClientRegistrationRepository, Iterable<ClientRegistration> {

    private Map<String, ClientRegistration> registrations;

    /**
     * Constructs an {@code InMemoryClientRegistrationRepository} using the provided
     * parameters.
     * @param registrations the client registration(s)
     */
    public FastcmsInMemoryClientRegistrationRepository(ClientRegistration... registrations) {
        this(Arrays.asList(registrations));
    }

    /**
     * Constructs an {@code InMemoryClientRegistrationRepository} using the provided
     * parameters.
     * @param registrations the client registration(s)
     */
    public FastcmsInMemoryClientRegistrationRepository(List<ClientRegistration> registrations) {
        this(createRegistrationsMap(registrations));
    }

    private static Map<String, ClientRegistration> createRegistrationsMap(List<ClientRegistration> registrations) {
        return toConcurrentMap(registrations);
    }

    private static Map<String, ClientRegistration> toConcurrentMap(List<ClientRegistration> registrations) {
        ConcurrentHashMap<String, ClientRegistration> result = new ConcurrentHashMap<>();
        for (ClientRegistration registration : registrations) {
            Assert.state(!result.containsKey(registration.getRegistrationId()),
                    () -> String.format("Duplicate key %s", registration.getRegistrationId()));
            result.put(registration.getRegistrationId(), registration);
        }
        return result;
    }

    /**
     * Constructs an {@code InMemoryClientRegistrationRepository} using the provided
     * {@code Map} of {@link ClientRegistration#getRegistrationId() registration id} to
     * {@link ClientRegistration}.
     * @param registrations the {@code Map} of client registration(s)
     * @since 5.2
     */
    public FastcmsInMemoryClientRegistrationRepository(Map<String, ClientRegistration> registrations) {
        this.registrations = registrations;
    }

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {
        Assert.hasText(registrationId, "registrationId cannot be empty");
        return this.registrations.get(registrationId);
    }

    /**
     * Returns an {@code Iterator} of {@link ClientRegistration}.
     * @return an {@code Iterator<ClientRegistration>}
     */
    @Override
    public Iterator<ClientRegistration> iterator() {
        return this.registrations.values().iterator();
    }

    /**
     * 添加client
     * @param clientRegistration
     */
    public void addClientRegistration(ClientRegistration clientRegistration) {
        if (hasRegistration(clientRegistration.getRegistrationId())) {
            removeClientRegistration(clientRegistration.getRegistrationId());
        }
        this.registrations.put(clientRegistration.getRegistrationId(), clientRegistration);
    }

    /**
     * 移除client
     * @param registrationId
     */
    public void removeClientRegistration(String registrationId) {
        this.registrations.remove(registrationId);
    }

    /**
     * 是否存在某个登录客户端
     * @param registrationId
     * @return
     */
    public Boolean hasRegistration(String registrationId) {
        return findByRegistrationId(registrationId) != null;
    }

}
