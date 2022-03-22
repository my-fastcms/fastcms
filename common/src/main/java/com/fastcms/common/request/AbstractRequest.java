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
import com.fastcms.common.http.Query;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.utils.HttpUtils;
import com.fastcms.common.utils.JacksonUtils;
import com.fastcms.common.utils.ReflectUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    protected transient Map<String, String> data = new HashMap<>();

    protected AbstractRequest() {
        buildParams();
    }

    protected void buildParams() {
        Field[] allFields = ReflectUtils.getFields(this.getClass());
        for (Field field : allFields) {
            field.setAccessible(true);
            Object obj;
            try {
                obj = field.get(this);
                if(obj != null) {
                    int mod = field.getModifiers();
                    if(!"serialVersionUID".equals(field.getName()) && !Modifier.isTransient(mod)) {
                        if (obj instanceof List == true) {
                            List v = (List) obj;
                            obj = v.stream().collect(Collectors.joining(","));
                        }

                        HeaderField annotation = field.getAnnotation(HeaderField.class);
                        if (annotation != null) {
                            header.addParam(annotation.format() ? field.getName().replaceAll("_", "-") : field.getName(), obj.toString());
                        } else {
                            query.addParam(field.getName(), obj);
                            data.put(field.getName(), (String) obj);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public RestResult get() throws Exception {
        return HttpUtils.get(getRequestUrl(), header, query, RestResult.class);
    }

    public RestResult post() throws Exception {
        return HttpUtils.post(getRequestUrl(), header, query, data, RestResult.class);
    }

    public RestResult postJson() throws Exception {
        return HttpUtils.postJson(getRequestUrl(), header, query, JacksonUtils.toJson(data), RestResult.class);
    }

    public RestResult postForm() throws Exception {
        return HttpUtils.postForm(getRequestUrl(), header, query, data, RestResult.class);
    }

    protected abstract String getRequestUrl();

}
