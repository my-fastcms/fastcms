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
package com.fastcms.core.directive;

import com.fastcms.utils.RequestUtils;
import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/27
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public abstract class BaseDirective implements TemplateDirectiveModel {

    protected static final String PARAM_COUNT = "count";
    protected static final String PARAM_ORDER_BY = "orderBy";
    protected static final Long DEFAULT_ID = 0l;

    private static final String DATA_KEY = "data";


    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);
        TemplateModel tm = builder.build().wrap(doExecute(env, params));
        env.setVariable(DATA_KEY, tm);
        if (body != null) {
            body.render(env.getOut());
        }
    }

    public abstract Object doExecute(Environment env, Map params) throws TemplateModelException;

    protected Long getLong(String key, Map params, Long defaultValue) {
        SimpleNumber simpleNumber = null;
        try {
            simpleNumber = (SimpleNumber) params.get(key);
        } catch (Exception e) {
            return defaultValue;
        }
        return simpleNumber == null ? defaultValue : simpleNumber.getAsNumber() == null ? defaultValue : simpleNumber.getAsNumber().longValue();
    }

    protected Long getLong(String key, Map params) {
        return getLong(key, params, null);
    }

    protected Integer getInt(String key, Map params, Integer defaultValue) {
        SimpleNumber simpleNumber = null;
        try {
            simpleNumber = (SimpleNumber) params.get(key);
        } catch (Exception e) {
            return defaultValue;
        }

        return simpleNumber == null ? defaultValue : simpleNumber.getAsNumber() == null ? defaultValue : simpleNumber.getAsNumber().intValue();
    }

    protected Integer getInt(String key, Map params) {
        return getInt(key, params, 0);
    }

    protected Double getDouble(String key, Map params, Double defaultValue) {
        SimpleNumber simpleNumber = null;
        try {
            simpleNumber = (SimpleNumber) params.get(key);
        } catch (Exception e) {
            return defaultValue;
        }
        return simpleNumber == null ? defaultValue : simpleNumber.getAsNumber() == null ? defaultValue : simpleNumber.getAsNumber().doubleValue();
    }

    protected Double getDouble(String key, Map params) {
        return getDouble(key, params, null);
    }

    protected Float getFloat(String key, Map params, Float defaultValue) {
        SimpleNumber simpleNumber = null;
        try {
            simpleNumber = (SimpleNumber) params.get(key);
        } catch (Exception e) {
            return defaultValue;
        }
        return simpleNumber == null ? defaultValue : simpleNumber.getAsNumber() == null ? defaultValue : simpleNumber.getAsNumber().floatValue();
    }

    protected Float getFloat(String key, Map params) {
        return getFloat(key, params, null);
    }

    protected String getStr(String key, Map params, String defaultValue) {
        SimpleScalar simpleScalar = null;
        try {
            simpleScalar = (SimpleScalar) params.get(key);
        } catch (Exception e) {
            return defaultValue;
        }
        return simpleScalar== null ? defaultValue : simpleScalar.getAsString() == null ? defaultValue : simpleScalar.getAsString() ;
    }

    protected String getStr(String key, Map params) {
        return getStr(key, params, "");
    }

    protected Boolean getBoolean(String key, Map params) {
        if(params.get(key) instanceof SimpleScalar) {
            SimpleScalar simpleScalar = null;
            try {
                simpleScalar = (SimpleScalar) params.get(key);
            } catch (Exception e) {
                return null;
            }
            if(StringUtils.isEmpty(simpleScalar.getAsString())) {
                return false;
            }

            if("true".equals(simpleScalar.getAsString())) {
                return true;
            }

            if("false".equals(simpleScalar.getAsString())) {
                return false;
            }
        }
        TemplateBooleanModel templateBooleanModel = (TemplateBooleanModel) params.get(key);
        try {
            return templateBooleanModel.getAsBoolean();
        } catch (TemplateModelException e) {
            return false;
        }
    }

    protected LocalDate getDate(String key, Map params) {
        SimpleScalar simpleScalar = null;
        try {
            simpleScalar = (SimpleScalar) params.get(key);
        } catch (Exception e) {
            return null;
        }
        return simpleScalar == null ? null : simpleScalar.getAsString() == null ? null : LocalDate.parse(simpleScalar.getAsString());
    }

    protected LocalDateTime getDateTime(String key, Map params) {
        SimpleScalar simpleScalar = null;
        try {
            simpleScalar = (SimpleScalar) params.get(key);
        } catch (Exception e) {
            return null;
        }
        return simpleScalar == null ? null : simpleScalar.getAsString() == null ? null : LocalDateTime.parse(simpleScalar.getAsString());
    }

    protected Boolean hasKey(String key, Map params) {
        return params.containsKey(key);
    }

    protected HttpServletRequest getRequest() {
        return RequestUtils.getRequest();
    }

}
