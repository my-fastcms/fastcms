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

package com.fastcms.common.exception;

/**
 * 支持国际化自定义异常信息
 * @author： wjun_java@163.com
 * @date： 2022/12/09
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class I18nFastcmsException extends FastcmsException {

    private String i18nKey;

    private String[] params;

    public I18nFastcmsException(final int errCode, final String i18nKey) {
        this.i18nKey = i18nKey;
        this.errCode = errCode;
    }

    public I18nFastcmsException(final String i18nKey) {
        this(INVALID_PARAM, i18nKey);
    }

    public I18nFastcmsException(final String i18nKey, final String ... params) {
        this(INVALID_PARAM, i18nKey);
        this.params = params;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

}
