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
package com.dbumama.market.web.core.config;

import java.util.HashMap;
import java.util.Map;

import com.dbumama.market.web.core.freemarker.method.AbbreviateMethod;
import com.dbumama.market.web.core.freemarker.method.CurrencyMethod;
import com.dbumama.market.web.core.freemarker.method.MessageMethod;
import com.dbumama.market.web.core.freemarker.tag.ShiroTags;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.render.FreeMarkerRender;
import com.weixin.sdk.kit.WxSdkPropKit;

import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.StandardCompress;

/**
 * @author wangjun
 * 2017年7月10日
 */
public class WxmFreekConfig extends WxmWebConfigAdapter implements WxmFreemarkerConfig{

	@Override
	public void configFreemarker() {
		Configuration cf = FreeMarkerRender.getConfiguration();
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("webctx", JFinal.me().getContextPath());
		map.put("locale", PropKit.get("locale"));
		if(Wxmall.isConfiged()){
			map.put("wx_domain", WxSdkPropKit.get("wx_domain", "http://localhost"));
	    	map.put("img_domain", WxSdkPropKit.get("img_domain", "http://localhost/admin"));
	    	map.put("admin_domain", WxSdkPropKit.get("admin_domain", "http://localhost/admin"));			
		}
    	map.put("compress", StandardCompress.INSTANCE);
    	try {
			cf.setSharedVaribles(map);
		} catch (TemplateModelException e) {
			throw new RuntimeException(e.getMessage());
		}
    	
    	cf.setDefaultEncoding(PropKit.get("template.encoding"));
    	cf.setURLEscapingCharset(PropKit.get("url_escaping_charset"));
    	cf.setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX);
    	cf.setWhitespaceStripping(true);
    	cf.setClassicCompatible(true);
    	cf.setSharedVariable("currency", new CurrencyMethod());
    	cf.setSharedVariable("abbreviate", new AbbreviateMethod());
    	cf.setSharedVariable("message", new MessageMethod());
    	cf.setSharedVariable("shiro", new ShiroTags());
    	cf.setServletContextForTemplateLoading(JFinal.me().getServletContext(), PropKit.get("template.loader_path"));		
	}

}
