package com.fastcms.web.security;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * @author wangjun
 **/
public interface FastcmsRegisteredClientRepository extends RegisteredClientRepository {

    void remove(String clientId);

}
