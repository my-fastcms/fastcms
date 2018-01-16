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
package com.dbumama.market.web.core.interceptor;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.service.api.user.UserService;
import com.dbumama.market.service.constants.Constants;
import com.dbumama.market.web.core.utils.IpKit;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.kit.ParaMap;
import com.weixin.sdk.kit.WxSdkPropKit;

/**
 * @author wangjun
 *
 */
public class MobileSessionInterceptor extends AbstractMobileInterceptor {

	public Logger logger = Logger.getLogger(getClass());
	
	final static String SNSSCOPE_BASE = "snsapi_base";
	
	final static String SNSSCOPE_USERINFO = "snsapi_userinfo";
	
	/** 微信oath2授权地址;通过该地址拿到用户授权code码 **/
	static final String oauth_url = "https://open.weixin.qq.com/connect/oauth2/authorize";
	
	/** 根据code获取openId以及access_token **/
	static final String oauth_url_code = "https://api.weixin.qq.com/sns/oauth2/access_token";
	
	/** 刷新网页授权token，获取更长时效的token值（可选）,该接口拿到的值可以延长用户授权 **/
	static final String oauth_url_refreshtoken = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
	
	/** 授权后获取用户信息 无需用户关注公众号 scope=snsapi_userinfo **/
	static final String oauth_url_getuserinfo = "https://api.weixin.qq.com/sns/userinfo";
	
	/**
	 * 检验授权凭证（access_token）是否有效
	 */
	static final String oauth_url_token_check = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
	
	UserService userService = (UserService) ctx.getBean("userService");
	
	/* (non-Javadoc)
	 * @see com.dbumama.market.web.core.interceptor.AbstractMobileInterceptor#doIntercept(com.jfinal.aop.Invocation)
	 */
	@Override
	public void doIntercept(Invocation ai) {
		Controller controller = ai.getController();
		HttpServletRequest request = controller.getRequest();
		String uri = request.getRequestURI();
//		String userAgent = request.getHeader("User-Agent")==null ? "" : request.getHeader("User-Agent").toLowerCase();
//        boolean isMobile = userAgent.matches(".*(android|avantgo|bada\\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\\/|plucker|pocket|psp|symbian|treo|up\\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino).*")||userAgent.substring(0,4).matches("1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\\-(n|u)|c55\\/|capi|ccwa|cdm\\-|cell|chtm|cldc|cmd\\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\\-s|devi|dica|dmob|do(c|p)o|ds(12|\\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\\-|_)|g1 u|g560|gene|gf\\-5|g\\-mo|go(\\.w|od)|gr(ad|un)|haie|hcit|hd\\-(m|p|t)|hei\\-|hi(pt|ta)|hp( i|ip)|hs\\-c|ht(c(\\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\\-(20|go|ma)|i230|iac( |\\-|\\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\\/)|klon|kpt |kwc\\-|kyo(c|k)|le(no|xi)|lg( g|\\/(k|l|u)|50|54|e\\-|e\\/|\\-[a-w])|libw|lynx|m1\\-w|m3ga|m50\\/|ma(te|ui|xo)|mc(01|21|ca)|m\\-cr|me(di|rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\\-2|po(ck|rt|se)|prox|psio|pt\\-g|qa\\-a|qc(07|12|21|32|60|\\-[2-7]|i\\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\\-|oo|p\\-)|sdk\\/|se(c(\\-|0|1)|47|mc|nd|ri)|sgh\\-|shar|sie(\\-|m)|sk\\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\\-|v\\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\\-|tdg\\-|tel(i|m)|tim\\-|t\\-mo|to(pl|sh)|ts(70|m\\-|m3|m5)|tx\\-9|up(\\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|xda(\\-|2|g)|yas\\-|your|zeto|zte\\-");
		//手机端：Mozilla/5.0 (Linux; U; Android 4.4.4; zh-cn; HM NOTE 1LTE Build/KTU84P)
		//PC端：Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36
        //PC端处理
        //开发模式下，PC端进行调试 
		
    	if(JFinal.me().getConstants().getDevMode()){
    		//直接获取配置文件的openId，本地PC端调试用
        	if(ai.getController().getSession().getAttribute(Constants.BUYER_USER_IN_SESSION) == null){
        		BuyerUser buyer = userService.findByOpenId(PropKit.get("open_id"));
        		if(buyer == null) throw new RuntimeException("开发环境下，测试用户不存在");
                ai.getController().getSession().setAttribute(Constants.BUYER_USER_IN_SESSION, buyer);
        	}	
    	}
        
        //处理授权回调的情况不检查session
        if (!"/callback".equals(uri)) {
        	checkSession(ai);
        }else {
        	String rUrl = request.getParameter("rUrl");
            rUrl = StringUtils.isBlank("rUrl") ? "/index" : rUrl;
            
            //code只能使用一次，5分钟未被使用自动过期
            final String code = controller.getPara("code");
            final String error = request.getParameter("error");
            final String error_description = request.getParameter("error");
            if (StringUtils.isNotBlank(error)) {
            	controller.renderHtml(error_description);
            } else if(StrKit.isBlank(code)){
            	controller.renderHtml("code is null;您取消了授权");
            } else {
            	String result = HttpKit.post(oauth_url_code, genOauthMap(code, getRedirectUrl(request, rUrl)), "");
                ApiResult jsonObj = new ApiResult(result);
                if(jsonObj.getErrorCode() != null)
                	throw new RuntimeException(jsonObj.getErrorMsg());
                
                /*{
            	   "access_token":"ACCESS_TOKEN",
            	   "expires_in":7200,
            	   "refresh_token":"REFRESH_TOKEN",
            	   "openid":"OPENID",
            	   "scope":"SCOPE",
            	   "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
            	}*/
                
                final String openId = jsonObj.getStr("openid");
                String accessToken = jsonObj.getStr("access_token");
//                String refreshToken = jsonObj.getStr("refresh_token");
//                String scope = jsonObj.getStr("scope");
                
                //从接口拿用户信息
        		String userResult = HttpKit.get(oauth_url_getuserinfo, 
        				ParaMap.create("access_token", accessToken).put("openid", openId).put("lang", "zh_CN").getData());
        		logger.debug("=====================userResult：" + userResult);
        		ApiResult userInfo = new ApiResult(userResult);
                if(userInfo.getErrorCode() != null){
                	throw new RuntimeException(userInfo.getErrorMsg());
                }
                
                BuyerUser member = userService.saveOrUpdate(WxSdkPropKit.getLong("seller_id"), openId, userResult, IpKit.getRealIp(request));
                request.getSession().setAttribute(Constants.BUYER_USER_IN_SESSION, member);
                controller.redirect(rUrl);
            }
        }
	}
	
	protected void checkSession(Invocation ai) {
    	Controller controller = ai.getController();
        HttpSession session = controller.getSession();
        
        //1，检查本地服务器用户session
        //2，检查微信服务器用户授权是否过期
        if(session.getAttribute(Constants.BUYER_USER_IN_SESSION) == null){
        	try {
        		//去授权，默认都采用base方式
        		gotoOauth(controller, SNSSCOPE_USERINFO);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
        }else {
        	ai.invoke();
        }
    }
	
	@SuppressWarnings("deprecation")
	protected static String getRedirectUrl(HttpServletRequest request, String rUrl) {
		String mainUrl = "http://" + request.getServerName();//微信只支持80端口
        return mainUrl + "/callback" + "?rUrl=" + (StringUtils.isNotBlank(rUrl) ? URLEncoder.encode(rUrl) : "");
    }

	/**
	 * 1、以snsapi_base为scope发起的网页授权，是用来获取进入页面的用户的openid的，
	 * 		并且是静默授权并自动跳转到回调页的。用户感知的就是直接进入了回调页（往往是业务页面）
	 * 2、以snsapi_userinfo为scope发起的网页授权，是用来获取用户的基本信息的。
	 * 		但这种授权需要用户手动同意，并且由于用户同意过，所以无须关注，就可在授权后获取该用户的基本信息。
	 * 3、用户管理类接口中的“获取用户基本信息接口”，是在用户和公众号产生消息交互或关注后事件推送后，才能根据用户OpenID来获取用户基本信息。
	 * 		这个接口，包括其他微信接口，都是需要该用户（即openid）关注了公众号后，才能调用成功的。
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE&component_appid=component_appid#wechat_redirect
	 * @param controller
	 * @param appKey
	 * @param scope
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	private void gotoOauth(Controller controller, String scope) throws IOException {
        String mainUrl = "http://" + controller.getRequest().getServerName();
        String rurl = mainUrl + controller.getRequest().getServletPath();
        if (controller.getRequest().getQueryString() != null) {
            rurl += "?" + controller.getRequest().getQueryString();
        }
        logger.debug("=======================rurl:" + rurl);
        String callbackUrl = getRedirectUrl(controller.getRequest(), rurl);
        String _url = oauth_url + "?appid=" + WxSdkPropKit.get("wx_app_id") + "&redirect_uri=" + URLEncoder.encode(callbackUrl) + "&response_type=code&scope="+scope+"&state=STATE#wechat_redirect";
        controller.redirect(_url);
    }
    
    @SuppressWarnings("deprecation")
	final static String decode(String s) {
    	 return StrKit.isBlank(s) ? null : URLDecoder.decode(s);
    }

    public Map<String, String> genOauthMap(String code, String rUrl) {
        Map<String, String> props = new HashMap<String, String>();
        props.put("code", code);
        props.put("appid", WxSdkPropKit.get("wx_app_id"));
        props.put("secret", WxSdkPropKit.get("wx_app_secret"));
        props.put("redirect_uri", rUrl);
        props.put("view", "web");
        props.put("grant_type", "authorization_code");
        return props;
    }
	
}
