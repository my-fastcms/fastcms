# fastcms

![输入图片说明](./doc/images/fastcms.png "屏幕截图.png")

### 介绍
- fastcms内置一套完整的CMS建站插件，但是fastcms并不止是建站。
- fastcms完全融入微信生态，是一切微信营销插件的基石。
- fastcms基于SpringBoot进行插件式开发，具有极强的扩展性，让你彻底从臃肿的项目中解脱出来。
- fastcms除了基础体系之外的所有功能都可以动态插拔

### 文档
- 在项目的doc目录下面有你想要的文档

### 开源协议
- apache协议，代码100%完全开源

### 后端技术
- SpringBoot作为底层框架
- mybatis-plus代码生成，可快速生成模块，插件的骨架代码。
- 使用spring security jwt进行登录验证，权限控制。
- 前后端分离技术
- 完美的数据权限解决方案。
- 集成pf4j插件框架。
- 基于pf4j实现springmvc controller的动态插拔。
- 基于pf4j实现mybatis核心组件的动态插拔，完美兼容mybatis-plus。
- 基于pf4j实现freemarker自定义标签的动态插拔。
- 基于pf4j实现springmvc拦截器的动态插拔。
- 支持插件之间的通信。
- lombock 使代码更简洁

### 前端技术
- vue3
- elementui-plus
- typescript
- vite

### 核心功能
- 完整的用户权限管理
- 插件管理，支持插件的在线安装，卸载，无需重启服务器，安装即可使用
- CMS管理

### 内置模块
1. CMS网站模块

- 支持网站用户中心

- 支持文章内容管理

- 支持网站模板管理

- 支持模板的安装卸载

- 支持模板在线编辑

- 完整的自定义模板标签
    * 文章标签（articleTag）
    * 文章列表标签（articleListTag）
    * 文章分页标签（articlePageTag）
    * 文章分类列表标签（categoryListTag）
    * 文章评论列表标签（commentPageTag）
    * 上一篇文章标签（prevArticleTag）
    * 下一篇文章标签（nextArticleTag）
    * 菜单标签（menuTag）
    * SEO标签（seoTag）

2. 支付模块

- 微信支付

3. 微信模块

- 接入微信公众号
- 接入微信小程序

4. 插件模块

- 支持插件化开发

### 运行环境
1. jdk1.8+
2. apache maven 3.6+
3. spring-boot 2.4.0+





