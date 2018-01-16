/**
 * Copyright (c) 广州点步信息科技有限公司 2016-2017, wjun_java@163.com.
 *
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.gnu.org/licenses/lgpl-3.0.txt
 *	    http://www.dbumama.com
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dbumama.market.web.core.listener;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.message.Message;
import com.dbumama.market.message.MessageListener;
import com.dbumama.market.message.annotation.Listener;
import com.dbumama.market.model.SellerImages;

/**
 * @author wangjun
 * 2017年7月12日
 */
@Listener(action = {"file_upload"})
public class FileUploadListener implements MessageListener{

	/* (non-Javadoc)
	 * @see com.dbumama.market.message.MessageListener#onMessage(com.dbumama.market.message.Message)
	 */
	@Override
	public void onMessage(Message message) {
		JSONObject jsonObj = message.getData();
		SellerImages sellerImages = new SellerImages();
		sellerImages.setSellerId(jsonObj.getLong("seller_id"));
		sellerImages.setImgGroupId(jsonObj.getLong("group_id"));
		sellerImages.setImgPath(jsonObj.getString("img_url"));
		sellerImages.setActive(1);
		sellerImages.setCreated(new Date());
		sellerImages.setUpdated(new Date());
		sellerImages.save();
	}

}

