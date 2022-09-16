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
package com.fastcms.cms.task;

import com.fastcms.cms.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/16
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Component
public class ArticleViewCountUpdateTask {

	@Autowired
	private IArticleService articleService;

	private static Map<Long, AtomicLong> countsMap = new ConcurrentHashMap<>();

	public static void recordCount(Long id) {
		AtomicLong count = countsMap.get(id);
		if (count == null) {
			count = new AtomicLong(0);
			countsMap.put(id, count);
		}
		count.getAndIncrement();
	}

	@Scheduled(cron = "0 */1 * * * ?")
	public void run() {

		if (countsMap.isEmpty()) {
			return;
		}

		Map<Long, AtomicLong> articleViews = new HashMap<>(countsMap);
		countsMap.clear();

		for (Map.Entry<Long, AtomicLong> entry : articleViews.entrySet()) {
			articleService.updateViewCount(entry.getKey(), entry.getValue().get());
		}
	}

}
