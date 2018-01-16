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
package com.dbumama.market.service.provider;

import org.springframework.stereotype.Service;

import com.dbumama.market.message.MessageKit;
import com.dbumama.market.model.SellerUser;
import com.dbumama.market.model.Weapp;
import com.dbumama.market.service.api.authuser.WeappService;
import com.dbumama.market.service.api.exception.MarketBaseException;
import com.dbumama.market.service.base.BaseServiceImpl;
import com.jfinal.plugin.activerecord.Model;

/**
 * @author wangjun
 * 2017年7月27日
 */
@Service("weappService")
public class WeappServiceImpl extends BaseServiceImpl<Weapp> implements WeappService{

	private static final Weapp webappDao = new Weapp().dao();
	private static final SellerUser sellerUserDao = new SellerUser().dao();
	
	/* (non-Javadoc)
	 * @see com.dbumama.market.service.api.authuser.WeappService#getWeapp()
	 */
	@Override
	public Weapp getWeapp() {
		return webappDao.findFirst("select * from " + Weapp.table);
	}

	/* (non-Javadoc)
	 * @see com.dbumama.market.service.api.authuser.WeappService#save(com.dbumama.market.model.Weapp)
	 */
	@Override
	public Long save(Weapp weapp, Long sellerId) {
		//if(!JFinal.me().getConstants().getDevMode()) throw new MarketBaseException("体验环境不可更改配置");
		
		if(weapp == null) throw new MarketBaseException("weapp is null");
		if(sellerId == null) throw new MarketBaseException("sellerId is null");
		
		SellerUser user = sellerUserDao.findById(sellerId);
		if(user == null || !"admin".equals(user.getPhone())) throw new MarketBaseException("你没有权限执行该操作");
		
		weapp.setSellerId(sellerId);
		if(weapp.getId() == null) {
			weapp.save();
		}
		else weapp.update();
		
		MessageKit.sendMessage("weapp_config", weapp);
		return weapp.getId();
	}

	/* (non-Javadoc)
	 * @see com.dbumama.market.service.base.BaseServiceImpl#getModel()
	 */
	@Override
	protected Model<Weapp> getModel() {
		return webappDao;
	}

}
