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

import org.pf4j.util.FileUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/19
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
public class DefaultTemplateFinder implements TemplateFinder {

	public static final String PROPERTIES_FILE_NAME = "_template.properties";

	public static final String TEMPLATE_ID = "template.id";
	public static final String TEMPLATE_NAME = "template.name";
	public static final String TEMPLATE_PATH = "template.path";
	public static final String TEMPLATE_I18N = "template.i18n";
	public static final String TEMPLATE_VERSION = "template.version";
	public static final String TEMPLATE_PROVIDER = "template.provider";
	public static final String TEMPLATE_DESCRIPTION = "template.description";

	@Override
	public Template find(Path templatePath) {
		Properties properties = readProperties(templatePath);
		if(properties == null) return null;
		Template template = createTemplate(properties);
		template.setTemplatePath(templatePath);
		return template;
	}

	protected Properties readProperties(Path templatePath) {
		Path propertiesPath = getPropertiesPath(templatePath, PROPERTIES_FILE_NAME);
		if (propertiesPath == null) {
			throw new RuntimeException("Cannot find the properties path");
		}

		Properties properties = new Properties();
		try {
			if (Files.notExists(propertiesPath)) {
				return null;
			}

			try (InputStream input = Files.newInputStream(propertiesPath)) {
				properties.load(input);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} finally {
			FileUtils.closePath(propertiesPath);
		}

		return properties;
	}

	protected Path getPropertiesPath(Path templatePath, String propertiesFileName) {
		if (Files.isDirectory(templatePath)) {
			return templatePath.resolve(Paths.get(propertiesFileName));
		} else {
			throw new RuntimeException("templatePath is error:" + templatePath);
		}
	}

	protected Template createTemplate(Properties properties) {
		Template template = new Template();
		template.setId(properties.getProperty(TEMPLATE_ID));
		template.setName(properties.getProperty(TEMPLATE_NAME));
		template.setPath(properties.getProperty(TEMPLATE_PATH));
		template.setI18n(properties.getProperty(TEMPLATE_I18N));
		template.setVersion(properties.getProperty(TEMPLATE_VERSION));
		template.setProvider(properties.getProperty(TEMPLATE_PROVIDER));
		template.setDescription(properties.getProperty(TEMPLATE_DESCRIPTION));
		return template;
	}

}
