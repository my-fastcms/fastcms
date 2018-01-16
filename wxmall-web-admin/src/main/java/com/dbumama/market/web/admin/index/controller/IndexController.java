/**
 * 文件名:IndexController.java
 * 版本信息:1.0
 * 日期:2015-5-10
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.web.admin.index.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.model.Lottery;
import com.dbumama.market.model.Prize;
import com.dbumama.market.model.PrizeType;
import com.dbumama.market.model.Product;
import com.dbumama.market.model.Qiandao;
import com.dbumama.market.model.SellerUser;
import com.dbumama.market.service.api.user.PhoneCodeService;
import com.dbumama.market.service.api.user.SellerUserService;
import com.dbumama.market.service.api.user.UserException;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseController;
import com.dbumama.market.web.core.plugin.shiro.ShiroKit;
import com.dbumama.market.web.core.plugin.shiro.ShiroMethod;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.dbumama.market.web.core.utils.IpKit;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import cn.dreampie.captcha.CaptchaRender;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-5-10
 */
@RouteBind(path="/")
public class IndexController extends BaseController{

	@BY_NAME
	private PhoneCodeService phoneCodeService;
	@BY_NAME
	private SellerUserService sellerUserService;
	
	final int loginFailureLockCount = 9;

	public void index(){
		
		Long qiandaoCount = Db.queryLong("select count(*) from " + Qiandao.table + " where active=1 and seller_id=? ", getSellerId());
		setAttr("qiandaoCount", qiandaoCount);
		
		Long LotteryCount = Db.queryLong("select count(*) from " + Lottery.table + " where active=1 and seller_id=? ", getSellerId());
		setAttr("lotteryCount", LotteryCount);
		
		Long prizeCount = Db.queryLong("select count(*) from " + Prize.table + " p "
				+ "left join " +PrizeType.table+ " t on p.prize_type_id=t.id where p.active=1 and p.seller_id=? and t.active=1 ", getSellerId());
		setAttr("prizeCount", prizeCount);
		
		Long buyerCount = Db.queryLong("select count(*) from " + BuyerUser.table + " where active=1 and seller_id=? ", getSellerId());
		setAttr("buyerCount", buyerCount == null ? 0L : buyerCount);
		
		Long orderNum=Db.queryLong("select count(id) as num from t_order where seller_id=?",getSellerId());
		setAttr("orderNum", orderNum == null ? 0L : orderNum);
		
		BigDecimal totalIncome=Db.queryBigDecimal("SELECT sum(o.pay_fee) AS total_income FROM t_order o "+
		                         "  WHERE date_format(o.created, '%Y-%m') = date_format(now(), '%Y-%m') AND o.payment_status = 2");
		setAttr("totalIncome", totalIncome == null ? new BigDecimal(0) : totalIncome);
		
		Long productCount = Db.queryLong("select count(*) from " + Product.table + " where active=1 and is_marketable=1 and seller_id=? ", getSellerId());
		setAttr("productCount", productCount == null ? 0L : productCount);	
		render("index.html");
	}
	
	public void chartData(){
		//一个月内每天的已支付订单总数统计
		String sql="select DATE_FORMAT(created,'%Y-%m-%d') as dt, count(id) as numCount, To_Days(DATE_FORMAT(NOW(),'%Y-%m-%d')) - To_Days(DATE_FORMAT(created,'%Y-%m-%d')) as dc " 
					+ " from t_order where payment_status=2 group by DATE_FORMAT(created,'%Y-%m-%d') HAVING dc<=30";
		List<Record> records=Db.find(sql);
		JSONArray array=new JSONArray();
		for (Record record : records) {
			if(record.getLong("numCount") != null && record.getStr("dt") != null){
				String dataStr = record.getStr("dt");
				JSONObject json=new JSONObject();
				json.put("incomeNum", record.getLong("numCount"));
				json.put("year", dataStr.split("-")[0]);
				json.put("month", dataStr.split("-")[1]);
				json.put("day", dataStr.split("-")[2]);
				array.add(json);
			}
		}
		//一个月内每天的总订单总数统计
		String sql2="select DATE_FORMAT(created,'%Y-%m-%d') as dt, count(id) as numCount, To_Days(DATE_FORMAT(NOW(),'%Y-%m-%d')) - To_Days(DATE_FORMAT(created,'%Y-%m-%d')) as dc " 
				+ " from t_order group by DATE_FORMAT(created,'%Y-%m-%d') HAVING dc<=30";
		List<Record> records2=Db.find(sql2);
		JSONArray array2=new JSONArray();
		for (Record record : records2) {
			if(record.getLong("numCount") != null && record.getStr("dt") != null){
				String dataStr = record.getStr("dt");
				JSONObject json=new JSONObject();
				json.put("incomeNum", record.getLong("numCount"));
				json.put("year", dataStr.split("-")[0]);
				json.put("month", dataStr.split("-")[1]);
				json.put("day", dataStr.split("-")[2]);
				array2.add(json);
			}
		}
		
		Map<String, JSONArray> dataMap = new HashMap<String, JSONArray>();
		dataMap.put("data1", array);
		dataMap.put("data2", array2);
		rendSuccessJson(dataMap);
	}
	
	public void login(){
		render("login.html");
	}
	
	public void logout(){
		Subject currentUser = SecurityUtils.getSubject();
		if (SecurityUtils.getSubject().getSession() != null) {
			currentUser.logout();
		}
		redirect("/");
	}
	
	public void auth(){
		final String phone = getPara("username");
		final String password = getPara("password");
		final String captchaToken = getPara("captchaToken");
		
		//check 验证码
		if(!ShiroKit.doCaptcha("captcha", captchaToken)){
			rendFailedJson("验证码错误");
			return;
		}
		
		SellerUser admin = sellerUserService.findByPhone(phone);		
		// 开始验证
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(phone, DigestUtils.md5Hex(password));
		token.setRememberMe(false);
		
		try {
			subject.login(token);
		} catch (UnknownAccountException ue) {
			token.clear();
			rendFailedJson("登录失败！无效的账号或密码。");
			return;
		} catch (IncorrectCredentialsException ie) {
			token.clear();
			int loginFailureCount = admin.getInt("admi_login_failure_count")==null?0:admin.getInt("admi_login_failure_count") + 1;
			if (loginFailureCount >= loginFailureLockCount) {
				admin.setActive(0);
				admin.setUpdated(new Date());
				admin.update();
			}
			admin.set("admi_login_failure_count", loginFailureCount);
			admin.update();
			if (loginFailureLockCount - loginFailureCount <= 6) {
				rendFailedJson("若连续" + loginFailureLockCount + "次密码输入错误,您的账号将被锁定,还有" + (loginFailureLockCount - admin.getInt("admi_login_failure_count")) + " 次机会。");
				return;
			} else {
				rendFailedJson("您的用户名或密码错误!");
				return;
			}
		} catch(LockedAccountException le){
			token.clear();
			rendFailedJson("用户被锁定!");
			return;
		} catch (RuntimeException re) {
			re.printStackTrace();
			token.clear();
			rendFailedJson("登录失败");
			return;
		}
			
		// 登录成功后
		if(ShiroMethod.authenticated()) {
			admin.setLoginTime(new Date());
			admin.setLoginIp(getRequest().getRemoteAddr());
			admin.set("admi_login_failure_count", 0);
			admin.update();
			rendSuccessJson();
		} 
	}
	
	public void register(){
		render("register.html");
	}
	
	/*@Clear
	public void doRegister(){
		final String phone = getPara("phone");
		final String password = getPara("password");
		final String confirmPwd = getPara("confirmPwd");
		final String captchaToken = getPara("captchaToken");
		final String code = getPara("phoneCode");
		if(StrKit.isBlank(phone)){
			rendFailedJson("手机号码不能为空");
			return;
		}
		if(StrKit.isBlank(password)){
			rendFailedJson("密码不能为空");
			return;
		}
		if(StrKit.isBlank(confirmPwd)){
			rendFailedJson("确认密码不能为空");
			return;
		}
		if(StrKit.isBlank(code)){
			rendFailedJson("手机验证码不能为空");
			return;
		}
		
		if(!password.equals(confirmPwd)){
			rendFailedJson("两次输入的密码不一样");
			return;
		}
		
		//check 验证码
		if(!ShiroKit.doCaptcha("captcha", captchaToken)){
			rendFailedJson("验证码错误");
			return;
		}
		
		//check 手机验证码
		UserCode userCode = phoneCodeService.getVerifyUserCode(phone, code);
		if(userCode == null){
			rendFailedJson("手机验证码错误");
			return;
		}
		//检查验证码是否过期 30分钟后过期
		Integer expires_in = 1800;
		Long expiredTime = userCode.getUpdated().getTime() + ((expires_in -5) * 1000);
		if(expiredTime == null || expiredTime < System.currentTimeMillis()){
			rendFailedJson("验证码已过期");
			return;
		}
		
		SellerUser sellerUser = new SellerUser();
		sellerUser.setPhone(phone);
		sellerUser.setPassword(DigestUtils.md5Hex(password));
		sellerUser.setActive(1);
		sellerUser.setStartDate(DateTimeUtil.nowDate());
		String endDateStr = DateTimeUtil.getNextDateTimeString(15);
		sellerUser.setEndDate(DateTimeUtil.toDate(endDateStr));//试用15天
		sellerUser.setCreated(new Date());
		sellerUser.setUpdated(new Date());
		sellerUser.save();
		rendSuccessJson();
	}*/
	
	public void sendCode(){
		final String phone = getPara("phone");
		if(StringUtils.isEmpty(phone)){
			rendFailedJson("手机号码为空");
			return;
		}
		try {
			rendSuccessJson(phoneCodeService.getCode(phone, IpKit.getRealIp(getRequest())));
		} catch (UserException e) {
			rendFailedJson(e.getMessage());
		} 
	}
	
	@Clear
    public void captcha() {
        int width = 0, height = 0, minnum = 0, maxnum = 0, fontsize = 0;
        CaptchaRender captcha = new CaptchaRender();
        if (isParaExists("width")) {
            width = getParaToInt("width");
        }
        if (isParaExists("height")) {
            height = getParaToInt("height");
        }
        if (width > 0 && height > 0)
            captcha.setImgSize(width, height);
        if (isParaExists("minnum")) {
            minnum = getParaToInt("minnum");
        }
        if (isParaExists("maxnum")) {
            maxnum = getParaToInt("maxnum");
        }
        if (minnum > 0 && maxnum > 0)
            captcha.setFontNum(minnum, maxnum);
        if (isParaExists("fontsize")) {
            fontsize = getParaToInt("fontsize");
        }
        if (fontsize > 0)
            captcha.setFontSize(fontsize, fontsize);
        // 干扰线数量 默认0
        captcha.setLineNum(1);
        // 噪点数量 默认50
        captcha.setArtifactNum(10);
        // 使用字符 去掉0和o 避免难以确认
        captcha.setCode("123456789");
        //验证码在session里的名字 默认 captcha,创建时间为：名字_time
        // captcha.setCaptchaName("captcha");
        //验证码颜色 默认黑色
        // captcha.setDrawColor(new Color(255,0,0));
        //背景干扰物颜色  默认灰
        // captcha.setDrawBgColor(new Color(0,0,0));
        //背景色+透明度 前三位数字是rgb色，第四个数字是透明度  默认透明
        // captcha.setBgColor(new Color(225, 225, 0, 100));
        //滤镜特效 默认随机特效 //曲面Curves //大理石纹Marble //弯折Double //颤动Wobble //扩散Diffuse
        captcha.setFilter(CaptchaRender.FilterFactory.Curves);
        // 随机色 默认黑验证码 灰背景元素
        captcha.setRandomColor(true);
        render(captcha);
    }
	
	public void help(){
		render("help.html");
	}
	
}
