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
package com.fastcms.core.template;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.utils.DirUtils;
import freemarker.cache.TemplateLoader;
import freemarker.ext.jsp.TaglibFactory;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/23
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class FastcmsTemplateFreeMarkerConfigurer extends FreeMarkerConfigurationFactory
        implements FastcmsTemplateFreeMarkerConfig, InitializingBean, ResourceLoaderAware, ServletContextAware, EnvironmentAware {

    @Nullable
    private Configuration configuration;

    @Nullable
    private Environment environment;

    /**
     * Set a preconfigured Configuration to use for the FreeMarker web config, e.g. a
     * shared one for web and email usage, set up via FreeMarkerConfigurationFactoryBean.
     * If this is not set, FreeMarkerConfigurationFactory's properties (inherited by
     * this class) have to be specified.
     * @see org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Initialize the {@link TaglibFactory} for the given ServletContext.
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
    }


    /**
     * Initialize FreeMarkerConfigurationFactory's Configuration
     * if not overridden by a preconfigured FreeMarker Configuration.
     * <p>Sets up a ClassTemplateLoader to use for loading Spring macros.
     * @see #createConfiguration
     * @see #setConfiguration
     */
    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        if (this.configuration == null) {
            this.configuration = createConfiguration();
            this.configuration.setSetting("number_format", "0");

            String[] activeProfiles = environment.getActiveProfiles();
            String profile = activeProfiles == null || activeProfiles.length <=0 ? FastcmsConstants.DEV_MODE : activeProfiles[0];

            if(FastcmsConstants.DEV_MODE.equals(profile)) {
                this.configuration.setDirectoryForTemplateLoading(new File(DirUtils.getTemplateDir()));
            }else {
                this.configuration.setDirectoryForTemplateLoading(new File("./htmls"));
            }
        }
    }

    /**
     * This implementation registers an additional ClassTemplateLoader
     * for the Spring-provided macros, added to the end of the list.
     */
    @Override
    protected void postProcessTemplateLoaders(List<TemplateLoader> templateLoaders) {

    }

    /**
     * Return the Configuration object wrapped by this bean.
     */
    @Override
    public Configuration getConfiguration() {
        Assert.state(this.configuration != null, "No Configuration available");
        return this.configuration;
    }

    @Override
    protected void postProcessConfiguration(Configuration config) throws IOException, TemplateException {
        Properties properties = new Properties();
        properties.put("default_encoding", "UTF-8");
        properties.put("locale", "zh_CN");
        properties.put("url_escaping_charset", "UTF-8");
        config.setSettings(properties);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

}
