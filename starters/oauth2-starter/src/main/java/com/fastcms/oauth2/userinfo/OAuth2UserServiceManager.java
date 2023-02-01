package com.fastcms.oauth2.userinfo;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class OAuth2UserServiceManager {

    private static final Map<String, OAuth2UserService> userServicesMap = Collections.synchronizedMap(new HashMap<>());

    public static void addOAuth2UserService(String registrationId, OAuth2UserService oAuth2UserService) {
        userServicesMap.put(registrationId ,oAuth2UserService);
    }

    public static void removeOAuth2UserService(String registrationId) {
        userServicesMap.remove(registrationId);
    }

    public static Boolean hasOAuth2UserService(String registrationId) {
        return userServicesMap.containsKey(registrationId);
    }

    public static Map<String, OAuth2UserService> getUserServicesMap() {
        return userServicesMap;
    }

    public static OAuth2UserService getOAuth2UserService(String registrationId) {
        return userServicesMap.get(registrationId);
    }

}
