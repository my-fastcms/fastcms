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
package com.fastcms.core.captcha;

import java.io.Serializable;

/**
 * @author： wjun_java@163.com
 * @date： 2021/11/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsCaptcha implements Serializable {

    /**
     * 验证码
     * 修复 https://gitee.com/xjd2020/fastcms/issues/I4WNWS
     */
//    private String verCode;
    /**
     * 验证码cache key
     */
    private String codeUuid;
    /**
     * 验证码图片
     */
    private String image;

    public FastcmsCaptcha(String codeUuid, String image) {
        this.codeUuid = codeUuid;
        this.image = image;
    }

    public String getCodeUuid() {
        return codeUuid;
    }

    public void setCodeUuid(String codeUuid) {
        this.codeUuid = codeUuid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
