# EnableWebSecurity
- 全局启用SpringSecurity的注解

```java
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Documented
    @Import({ WebSecurityConfiguration.class, SpringWebMvcImportSelector.class, OAuth2ImportSelector.class,
            HttpSecurityConfiguration.class })
    @EnableGlobalAuthentication
    @Configuration
    public @interface EnableWebSecurity {
    
        /**
         * Controls debugging support for Spring Security. Default is false.
         * @return if true, enables debug support with Spring Security
         */
        boolean debug() default false;
    
    }
```

# HttpSecurityConfiguration自动装配 HttpSecurity

# WebSecurityConfiguration自动装配 WebSecurity

# WebSecurity构建FilterChainProxy处理SpringSecurity的所有请求