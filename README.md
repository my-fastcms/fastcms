一直以来找不到合适的开源微信商城，本人最终自主研发，基于jfinal web框架开发出微信商城，微信分销商城，并经过客户锤炼，源码拿来即可对项目进行实施，节省大部分时间去陪老婆。。。创业维艰，哄好老婆也很重要。。。

  _

**_WxMall 1.0 系统简介_** 

JFinalMall商城系统专门针对微信服务号开发的一套微信商城，微信分销商城，支持商品多规格，支持按地区设置邮费，支持限时打折，订单返现，满减送，满包邮，支持订单打印，订单打印模板自定义，发货单打印，发货单打印模板自定义，支持抽奖，签到等互动功能。是在JFinal web框架上搭建的一个Java项目。代码规范遵循淘宝分布式框架dubbox协议规范，方便分布式部署，数据库采用mysql，管理端跟微信端前后端分离

  _**WxMall 1.0演示地址：**_  
http://admin.dbumama.com/admin
体验账号：wxmall
密码：123456

微信商城请扫二维码查看演示（注：演示为企业版）
![wxmall微商城](http://git.oschina.net/uploads/images/2017/0316/100537_42e4b940_471938.jpeg "在这里输入图片标题")

微信小程序商城请扫二维码
![wxmall小程序](https://git.oschina.net/uploads/images/2017/0824/102442_8a96a2b3_471938.jpeg "wxmall-weapp.jpg")

官方网站：[http://www.dbumama.com](http://)

 **_WxMall 1.0 功能简介_** 

功能列表
【商品】：店铺基本资料，店铺发货地址，我的文件管理
【商品】：商品发布、分类、规格、运费。
【订单】：订单查询、批量打印、批量发货、退款、快递单模板、发货单模板。
【分销】：分销商、佣金、分销商等级。
【营销】：限时打折，订单返现，满减送，满包邮。
【互动】：签到，抽奖，奖品，红包。
【会员】：微信公众号粉丝管理。
【设置】：公众号信息设置，支付配置。
JFinalMall 1.0 项目结构
商城系统采用Maven管理，包括以下6大模块：
wxmall-service-api ：dubbox的接口规范，支持rpc远程调用。
wxmall-service-common ：工具类，所有工具类都提取出来写在这个项目中。
wxmall-service-provider ：核心业务项目。主要是Service处理业务逻辑。
wxmall-model ：数据模型，与数据库表字段对应的实体类。
wxmall-web-admin ：PC后台管理端。
wxmall-web-mobile ：公众号微信端。

WxMall 1.0 技术选型
核心框架：JFinal 3.1，spring 4.0+
数据库：mysql 5.6 + 
JS框架：jquery-2.1.4，Bootstrap 3.6，jquery weui微信移动框架（微信前端开发的瑞士军刀）

运行项目配置说明
1、具备运行环境：JDK 1.7+、Maven 3.0+、MySQL 5.6+、Eclipse mars 
2、git下载项目后，在Eclipse 左侧空白处右击->import-import-> Existing Maven Projects
