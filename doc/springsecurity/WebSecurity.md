# WebSecurity 构建 FilterChainProxy的全过程

### WebSecurity
- webSecurity同样实现SecurityBuilder，可见同样是一个对象构建器

  ``` java
      protected Filter performBuild() throws Exception {

          Assert.state(!this.securityFilterChainBuilders.isEmpty(),
                  () -> "At least one SecurityBuilder<? extends SecurityFilterChain> needs to be specified. "
                          + "Typically this is done by exposing a SecurityFilterChain bean. "
                          + "More advanced users can invoke " + WebSecurity.class.getSimpleName()
                          + ".addSecurityFilterChainBuilder directly");
          int chainSize = this.ignoredRequests.size() + this.securityFilterChainBuilders.size();
          List<SecurityFilterChain> securityFilterChains = new ArrayList<>(chainSize);
          List<RequestMatcherEntry<List<WebInvocationPrivilegeEvaluator>>> requestMatcherPrivilegeEvaluatorsEntries = new ArrayList<>();
          for (RequestMatcher ignoredRequest : this.ignoredRequests) {
              WebSecurity.this.logger.warn("You are asking Spring Security to ignore " + ignoredRequest
                      + ". This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead.");
  
              // 可以直接new SecurityFilterChain
              SecurityFilterChain securityFilterChain = new DefaultSecurityFilterChain(ignoredRequest);
              securityFilterChains.add(securityFilterChain);
              requestMatcherPrivilegeEvaluatorsEntries
                  .add(getRequestMatcherPrivilegeEvaluatorsEntry(securityFilterChain));
          }
  
          // 重点： 从securityBuilder中构建SecurityFilterChain
  
          for (SecurityBuilder<? extends SecurityFilterChain> securityFilterChainBuilder : this.securityFilterChainBuilders) {
              SecurityFilterChain securityFilterChain = securityFilterChainBuilder.build();
              securityFilterChains.add(securityFilterChain);
              requestMatcherPrivilegeEvaluatorsEntries
                  .add(getRequestMatcherPrivilegeEvaluatorsEntry(securityFilterChain));
          }
          if (this.privilegeEvaluator == null) {
              this.privilegeEvaluator = new RequestMatcherDelegatingWebInvocationPrivilegeEvaluator(
                      requestMatcherPrivilegeEvaluatorsEntries);
          }
          FilterChainProxy filterChainProxy = new FilterChainProxy(securityFilterChains);
          if (this.httpFirewall != null) {
              filterChainProxy.setFirewall(this.httpFirewall);
          }
          if (this.requestRejectedHandler != null) {
              filterChainProxy.setRequestRejectedHandler(this.requestRejectedHandler);
          }
          else if (!this.observationRegistry.isNoop()) {
              CompositeRequestRejectedHandler requestRejectedHandler = new CompositeRequestRejectedHandler(
                      new ObservationMarkingRequestRejectedHandler(this.observationRegistry),
                      new HttpStatusRequestRejectedHandler());
              filterChainProxy.setRequestRejectedHandler(requestRejectedHandler);
          }
          filterChainProxy.setFilterChainDecorator(getFilterChainDecorator());
          filterChainProxy.afterPropertiesSet();
  
          Filter result = filterChainProxy;
          if (this.debugEnabled) {
              this.logger.warn("\n\n" + "********************************************************************\n"
                      + "**********        Security debugging is enabled.       *************\n"
                      + "**********    This may include sensitive information.  *************\n"
                      + "**********      Do not use in a production system!     *************\n"
                      + "********************************************************************\n\n");
              result = new DebugFilter(filterChainProxy);
          }
  
          this.postBuildAction.run();
          return result;
      }
  ```
