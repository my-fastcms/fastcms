# HttpSecurity 构建 SecurityFilterChain的全过程

### 构建代码

  ``` java
      @Bean
      SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          http.formLogin().loginPage("/fastcms.html");
          http.authorizeRequests().antMatchers("/fastcms/**").authenticated();
          http.csrf().disable().cors()
                  .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                  .and().authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
          );
          http.headers().cacheControl();
          http.headers().frameOptions().disable();
          http.addFilterBefore(new JwtAuthTokenFilter(tokenManager), UsernamePasswordAuthenticationFilter.class);
          return http.build();
      }
  ```
  
### HttpSecurity
- HttpSecurity implements SecurityBuilder
  
  ``` java
     public final class HttpSecurity extends AbstractConfiguredSecurityBuilder<DefaultSecurityFilterChain, HttpSecurity>
     implements SecurityBuilder<DefaultSecurityFilterChain>, HttpSecurityBuilder<HttpSecurity> 
  ``` 
  ``` java
    public interface SecurityBuilder<O> {
	    O build() throws Exception;
    }
  ```  


### AbstractConfiguredSecurityBuilder
- AbstractConfiguredSecurityBuilder extends AbstractSecurityBuilder
  ``` java
    public abstract class AbstractSecurityBuilder<O> implements SecurityBuilder<O> {
        @Override
        public final O build() throws Exception {
            if (this.building.compareAndSet(false, true)) {
                // doBuild()方法由子类实现
                this.object = doBuild();
                return this.object;
            }
            throw new AlreadyBuiltException("This object has already been built");
        }
    }
  ```

  ``` java
    private final LinkedHashMap<Class<? extends SecurityConfigurer<O, B>>, List<SecurityConfigurer<O, B>>> configurers = new LinkedHashMap<>();
  
    @Override
	protected final O doBuild() throws Exception {
		synchronized (this.configurers) {
			this.buildState = BuildState.INITIALIZING;
			beforeInit();
			init();
			this.buildState = BuildState.CONFIGURING;
			beforeConfigure();
			configure();
			this.buildState = BuildState.BUILDING;
			O result = performBuild();
			this.buildState = BuildState.BUILT;
			return result;
		}
	}
  
    // 划重点，此处由子类扩展
    protected abstract O performBuild() throws Exception;
  
  ```

### HttpSecurity 构建 SecurityFilterChain

  ``` java
    @Override
	protected DefaultSecurityFilterChain performBuild() {
		ExpressionUrlAuthorizationConfigurer<?> expressionConfigurer = getConfigurer(
				ExpressionUrlAuthorizationConfigurer.class);
		AuthorizeHttpRequestsConfigurer<?> httpConfigurer = getConfigurer(AuthorizeHttpRequestsConfigurer.class);
		boolean oneConfigurerPresent = expressionConfigurer == null ^ httpConfigurer == null;
		Assert.state((expressionConfigurer == null && httpConfigurer == null) || oneConfigurerPresent,
				"authorizeHttpRequests cannot be used in conjunction with authorizeRequests. Please select just one.");
		this.filters.sort(OrderComparator.INSTANCE);
		List<Filter> sortedFilters = new ArrayList<>(this.filters.size());
		for (Filter filter : this.filters) {
			sortedFilters.add(((OrderedFilter) filter).filter);
		}
		return new DefaultSecurityFilterChain(this.requestMatcher, sortedFilters);
	}
  ```
  

### HttpSecurity构建过程中如何添加配置？

  ``` java
    public interface SecurityConfigurer<O, B extends SecurityBuilder<O>> {
        /**
        初始化配置，参数传递的是构建器
        **/
        void init(B builder) throws Exception;
        /**
        配置方法，参数传递的是构建器
        **/
        void configure(B builder) throws Exception;
    }
  ```

  ``` java
    /**
      AbstractConfiguredSecurityBuilder类给构建器添加配置
    **/
    private <C extends SecurityConfigurer<O, B>> void add(C configurer) {
		Assert.notNull(configurer, "configurer cannot be null");
		Class<? extends SecurityConfigurer<O, B>> clazz = (Class<? extends SecurityConfigurer<O, B>>) configurer
				.getClass();
		synchronized (this.configurers) {
			if (this.buildState.isConfigured()) {
				throw new IllegalStateException("Cannot apply " + configurer + " to already built object");
			}
			List<SecurityConfigurer<O, B>> configs = null;
			if (this.allowConfigurersOfSameType) {
				configs = this.configurers.get(clazz);
			}
			configs = (configs != null) ? configs : new ArrayList<>(1);
			configs.add(configurer);
			this.configurers.put(clazz, configs);
			if (this.buildState.isInitializing()) {
				this.configurersAddedInInitializing.add(configurer);
			}
		}
	}
  ```

### HttpSecurity构建过程中如何扩展？
- 从以上代码结构可以看出来，整个构建过程使用到了经典的设计模式 ==》 构建者模式
- 构建过程中，只要给HttpSecurity添加扩展配置，即可自定义扩展，比如添加自定义过滤器filter
- 调用AbstractConfiguredSecurityBuilder类提供的公共方法apply()

  ``` java
      public <C extends SecurityConfigurerAdapter<O, B>> C apply(C configurer) throws Exception {
          configurer.addObjectPostProcessor(this.objectPostProcessor);
          configurer.setBuilder((B) this);
          add(configurer);
          return configurer;
      }
  ```