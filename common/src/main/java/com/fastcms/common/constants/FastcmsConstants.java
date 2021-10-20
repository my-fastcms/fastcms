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
package com.fastcms.common.constants;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/17
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface FastcmsConstants {

    String version = "0.0.1";

    String VISIT_TO = "visit";

    String STATIC_PATH_KEY = "spath";

    String ADMIN_MAPPING = "admin";

    String UCENTER_MAPPING = "ucenter";

    String API_MAPPING = "api";

    String DEV_MODE = "dev";

    String STATIC_RESOURCE_PATH = "/static";

    String USER_ID = "user_id";

    String OPEN_ID = "openid";

    /**
     * 内置超级管理员角色id值,不可变
     */
    Long ADMIN_ROLE_ID = 1L;

    /**
     * 内置超级用户,不可变
     */
    Long ADMIN_USER_ID = 1L;

    /**
     * 当前被使用的网站模板
     */
    String TEMPLATE_ENABLE_ID = "enable_template_id";

    /**
     * 系统模块id值
     */
    String SYSTEM_MODULE_ID = "system-core";
    String PLUGIN_MODULE_ID = "module-plugin";
    String CMS_MODULE_ID = "cms-plugin";
    String MALL_MODULE_ID = "mall-plugin";

    String PERMISSION_MODULE = "module";
    String PERMISSION_MENU = "menu";
    String PERMISSION_TAB = "tab";
    String PERMISSION_OPTION = "option";

    /**
     * 文件服务器域名
     */
    String FILE_DOMAIN = "file_domain";

    /**
     * 系统配置相关key
     */
    String WEBSITE_TITLE_KEY = "website_title";
    String WEBSITE_SUB_TITLE_KEY = "website_sub_title";
    String WEBSITE_NAME = "website_name";
    String WEBSITE_DOMAIN = "website_domain";

    /**
     * 微信配置相关
     */

    /**
     * 小程序名称
     */
    String WECHAT_MINI_NAME = "wechat_mini_name";
    /**
     * 小程序appId
     */
    String WECHAT_MINI_APP_ID = "wechat_mini_app_id";
    /**
     * 小程序appSecret
     */
    String WECHAT_MINI_APP_SECRET = "wechat_mini_app_secret";

    /**
     * 公众号名称
     */
    String WECHAT_MP_NAME = "wechat_mp_name";
    /**
     * 公众号appId
     */
    String WECHAT_MP_APP_ID = "wechat_mp_app_id";
    /**
     * 公众号appSecret
     */
    String WECHAT_MP_APP_SECRET = "wechat_mp_app_secret";

    /**
     * 微信对公支付商户号
     */
    String WECHAT_MCH_ID = "wechat_mch_id";

    /**
     * 微信对公支付商户密钥
     */
    String WECHAT_MCH_SECRET = "wechat_mch_secret";

    /**
     * 异步支付回调地址
     */
    String WECHAT_PAY_NOTIFYURL = "wechat_pay_notifyUrl";

    /**
     * 同步回调地址，支付完成后展示的页面
     */
    String WECHAT_PAY_RETURNURL = "wechat_pay_returnUrl";

    /**
     * 微信支付签名类型
     */
    String WECHAT_PAY_SIGN_TYPE = "wechat_pay_sign_type";

}
