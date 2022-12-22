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

    String API_PREFIX_MAPPING = "/fastcms/api";

    String ADMIN_MAPPING = API_PREFIX_MAPPING + "/admin";

    String API_MAPPING = API_PREFIX_MAPPING + "/client";

    String PLUGIN_MAPPING = "/fastcms/plugin";

    String DEV_MODE = "dev";

    String WEB_CODE_CACHE_NAME = "web_code_cache";

    String ENCODE = "UTF-8";

    String CREATE_USER_ID = "create_user_id";

    String TEMPLATE_STATIC = "/static/";

    /**
     * 数据启用状态
     */
    Integer STATUS_NORMAL = 1;

    /**
     * 数据禁用删除状态
     */
    Integer STATUS_DEL = 0;

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
     * 系统配置相关key
     */
    String WEBSITE_TITLE_KEY = "website_title";
    String WEBSITE_SEO = "website_seo";
    String WEBSITE_DOMAIN = "public_website_domain";

    /**
     * 微信配置相关
     */

    String WECHAT_MP_APP_ID = "wechate_mp_appid";
    String WECHAT_MP_APP_SECRET = "wechat_mp_secret";
    String WECHAT_MP_APP_TOKEN = "wechat_mp_token";
    String WECHAT_MP_APP_AESKEY = "wechat_mp_aeskey";

    String WECHAT_MINIAPP_APP_ID = "wechat_miniapp_appid";
    String WECHAT_MINIAPP_APP_SECRET = "wechat_miniapp_secret";
    String WECHAT_MINIAPP_APP_TOKEN = "wechat_miniapp_token";
    String WECHAT_MINIAPP_APP_AESKEY = "wechat_miniapp_aeskey";

    /**
     * ================
     * Server info 相关
     * ================
     */
    //服务器ip地址
    String SERVER_IP = "serverIp";
    //服务器端口
    String SERVER_PORT = "serverPort";

    /**
     * ================
     * email 配置相关
     * ================
     */
    String EMAIL_HOST = "email_host";

    String EMAIL_PORT = "email_port";

    String EMAIL_USERNAME = "email_username";

    String EMAIL_PASSWORD = "email_password";

    /**
     * ================
     * Jwt 配置相关
     * ================
     */

    String JWT_SECRET = "jwt_secret";
    String JWT_EXPIRE = "jwt_expire";

    /**
     * ================
     * 系统密码配置相关
     * ================
     */

    /**
     * 密码最短长度
     */
    String PWD_MIN_LENGTH = "pwd_min_length";

    /**
     * I18N KEY
     */
    String FASTCMS_SYSTEM_NO_DATA = "fastcms.system.no.data";
    String FASTCMS_SYSTEM_ERROR = "fastcms.system.error";
    String FASTCMS_SYSTEM_SAVE_ERROR = "fastcms.system.save.error";
    String FASTCMS_SYSTEM_UPDATE_ERROR = "fastcms.system.update.error";
    String FASTCMS_SYSTEM_REQUEST_PARAMS_ERROR = "fastcms.system.request.params.error";
    String FASTCMS_SYSTEM_REQUEST_ERROR = "fastcms.system.request.error";

    /**
     * The constants in exception directory.
     */
    class Exception {

        public static final int DESERIALIZE_ERROR_CODE = 101;

        public static final int SERIALIZE_ERROR_CODE = 100;
    }

}
