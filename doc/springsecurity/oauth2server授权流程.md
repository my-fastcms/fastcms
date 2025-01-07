# Fastcms作为 oauth2 server 账号提供者，授权登录第三方系统全过程

### 系统
#### 1.fastcms作为授权服务器，假设地址为http://localhost:8080
#### 2.nexfly作为第三方授权客户端系统，假设地址为http://127.0.0.1:6000

### 详细步骤解析

#### 1. 第三方客户端发起授权请求
- 请求地址：http://127.0.0.1:6000/nexfly-auth/oauth2/authorization/nexfly-client?accessToken=Bearer%20eyJhbGciOiJIUzI1NiJ9.eyJhdXRoIjoiMSIsInVzZXJJZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTczNDUwNTA5OX0.jt4WB4jzinhGpABCJUu5kSz5H5lXx1p7Qt46Z2l6zX8
- 触发的 Filter：OAuth2AuthorizationRequestRedirectFilter
---

#### 2. 跳转到授权服务器授权页面
- 请求地址：http://localhost:8080/oauth2/authorize?response_type=code&client_id=nexfly-client&scope=openid%20profile&state=Uwh8VaLLkeNByD7Llme2x25s2imohDZmhI3h0-t0Yho%3D&redirect_uri=http://127.0.0.1:6000/nexfly-auth/login/oauth2/code/nexfly-client&nonce=AQknWo0m9jocK9jflQ5fvf9S93flQRBUbraY9RG6zy0&accessToken=Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdXRoIjoiMSIsInVzZXJJZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTczNDUwNTA5OX0.jt4WB4jzinhGpABCJUu5kSz5H5lXx1p7Qt46Z2l6zX8
- 触发的 Filter：OAuth2AuthorizationEndpointFilter
  - OAuth2AuthorizationCodeRequestAuthenticationProvider 
  - OAuth2AuthorizationConsentAuthenticationProvider
---

#### 3. 用户同意授权并返回授权码code并重定向到第三方客户端回调地址
- 请求地址：http://127.0.0.1:6000/nexfly-auth/login/oauth2/code/nexfly-client?code=WUEWXNHZGC8wCByhxNrLoGKfmwS19TMYy3a6woK6IbXG8rsLtFRl38BfJF3MapvcjXtUU7Rp2rj8ixRa-y3n3sFFC8uMIOqjcqtriqavgqG0FIShDdk9e33xGxSay8O6&state=bCInDhPqrEpXve-6SJCzUUjeKqfmEXyXkVBjiITNmxk%3D
- 触发的 Filter：OAuth2LoginAuthenticationFilter
  - OidcAuthorizationCodeAuthenticationProvider
    - OAuth2LoginAuthenticationProvider
    - DefaultAuthorizationCodeTokenResponseClient.getTokenResponse()，用code换取token，请求第四步接口
    - DefaultOAuth2UserService.loadUser()，获取授权用户信息，请求第五步接口
---

#### 4. 第三方系统用授权码交换access_token
- 请求地址：http://localhost:8080/oauth2/token
- 服务器端触发的 Filter：OAuth2TokenEndpointFilter
    - OAuth2AuthorizationCodeAuthenticationProvider
---

#### 5. 第三方系统请求用户信息
- 请求地址：http://localhost:8080/userinfo
- 触发的 Filter：
    > BearerTokenAuthenticationFilter
      -> OidcUserInfoEndpointFilter
    - OidcUserInfoAuthenticationProvider
---
