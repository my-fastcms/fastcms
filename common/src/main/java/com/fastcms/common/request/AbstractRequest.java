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

package com.fastcms.common.request;

import com.fastcms.common.http.Header;
import com.fastcms.common.http.HttpRestResult;
import com.fastcms.common.http.Query;
import com.fastcms.common.utils.HttpUtils;
import com.fastcms.common.utils.JacksonUtils;
import com.fastcms.common.utils.ReflectUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2022/3/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class AbstractRequest implements Serializable {

    protected transient Header header = Header.EMPTY;
    protected transient Query query = Query.EMPTY;
    protected transient Map<String, String> params = new HashMap<>();

    public Map<String, String> buildParams() {
        if (CollectionUtils.isNotEmpty(params.values())) {
            return params;
        }
        Field[] allFields = ReflectUtils.getFields(this.getClass());
        for (Field field : allFields) {
            String fieldValue = ReflectUtils.getFieldValue(this, field);
            if (StringUtils.isNotBlank(fieldValue)) {
                params.put(field.getName(), fieldValue);
            }
        }
        return params;
    }

    public AbstractRequest addHeader(String key, String value) {
        header.addParam(key, value);
        return this;
    }

    public AbstractRequest addParams(String key, Object value) {
        query.addParam(key, value);
        return this;
    }

    public HttpRestResult get(Type resultType) throws Exception {
        return HttpUtils.get(getRequestUrl(), header, query.initParams(buildParams()), resultType);
    }

    public HttpRestResult get() throws Exception {
        return get(getResult());
    }

    public HttpRestResult post(Type resultType) throws Exception {
        return HttpUtils.post(getRequestUrl(), header, query, buildParams(), resultType);
    }

    public HttpRestResult post() throws Exception {
        return post(getResult());
    }

    public HttpRestResult postJson() throws Exception {
        return HttpUtils.postJson(getRequestUrl(), header, query, JacksonUtils.toJson(buildParams()), getResult());
    }

    public HttpRestResult postForm() throws Exception {
        return HttpUtils.postForm(getRequestUrl(), header, query, buildParams(), getResult());
    }

    /**
     * 请求的url地址
     * @return
     */
    protected abstract String getRequestUrl();

    /**
     * 返回结果集
     * @return
     */
    protected abstract Type getResult();

}
