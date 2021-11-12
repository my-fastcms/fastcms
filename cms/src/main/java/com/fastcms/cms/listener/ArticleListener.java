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
package com.fastcms.cms.listener;

import com.fastcms.cms.service.IArticleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/16
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class ArticleListener implements ApplicationListener<ApplicationStartedEvent> {

	private Log log = LogFactory.getLog(ArticleListener.class.getName());

	@Autowired
	private IArticleService articleService;

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		log.info("articleListener articleService:" + articleService);
	}

}
