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
package com.fastcms.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

/**
 * @author： wjun_java@163.com
 * @date： 2022/10/2
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@SpringBootTest
public class TestI18n {

	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;

	@Test
	public void test() {
		String message = messageSource.getMessage("fastcms.message.test", null, Locale.getDefault());
		System.out.println(message);

		String message1 = messageSource.getMessage("cms.test", null, Locale.ENGLISH);
		System.out.println(message1);

	}

}
