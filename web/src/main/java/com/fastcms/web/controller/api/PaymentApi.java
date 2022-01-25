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
package com.fastcms.web.controller.api;

import com.egzosn.pay.common.util.MatrixToImageWriter;
import com.egzosn.pay.wx.bean.WxTransactionType;
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.payment.PayServiceManager;
import com.fastcms.payment.bean.FastcmsPayOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 支付
 * @author： wjun_java@163.com
 * @date： 2022/1/23
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.API_MAPPING + "/pay")
public class PaymentApi {

    @Autowired
    private PayServiceManager payServiceManager;

    @Autowired
    private IArticleService articleService;

    /**
     * 扫码支付 | 返回图片流
     * @param platform 支付平台
     * @param articleId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "{platform}/toQrPay.jpg", produces = "image/jpeg;charset=UTF-8")
    public byte[] toQrPay(@PathVariable("platform") String platform, @RequestParam("articleId") Long articleId) throws IOException {
        Article article = articleService.getById(articleId);
        BigDecimal price = new BigDecimal((String) article.getExtFields().get("price"));
        //获取对应的支付账户操作工具（可根据账户id）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(MatrixToImageWriter.writeInfoToJpgBuff(payServiceManager.getQrPay(new FastcmsPayOrder(platform, WxTransactionType.NATIVE.getType(), article.getTitle(), article.getSummary(), null == price ? BigDecimal.valueOf(0.01) : price, System.currentTimeMillis()+""))), "JPEG", baos);
        return baos.toByteArray();
    }

    /**
     * 扫码支付 | 返回图片url
     * @param platform
     * @param articleId
     * @return
     */
    @RequestMapping(value = "{platform}/getQrPay")
    public String getQrPay(@PathVariable("platform") String platform, @RequestParam("articleId") Long articleId) {
        //获取对应的支付账户操作工具（可根据账户id）
        Article article = articleService.getById(articleId);
        BigDecimal price = new BigDecimal((String) article.getExtFields().get("price"));
        return payServiceManager.getQrPay(new FastcmsPayOrder(platform, WxTransactionType.NATIVE.getType(), article.getTitle(), article.getSummary(), null == price ? BigDecimal.valueOf(0.01) : price, System.currentTimeMillis()+""));
    }

    /**
     * 公众号支付
     * @param platform
     * @param articleId
     * @param openid
     * @return
     */
    @RequestMapping(value = "{platform}/jsapi" )
    public Map toPay(@PathVariable("platform") String platform, @RequestParam("articleId") Long articleId, @RequestParam("openid") String openid) {

        Article article = articleService.getById(articleId);
        BigDecimal price = new BigDecimal((String) article.getExtFields().get("price"));

        FastcmsPayOrder order = new FastcmsPayOrder(platform, WxTransactionType.JSAPI.getType(), article.getTitle(), article.getSummary(), null == price ? BigDecimal.valueOf(0.01) : price, System.currentTimeMillis()+"");
        order.setOpenid(openid);

        Map orderInfo = payServiceManager.getOrderInfo(order);
        orderInfo.put("code", 0);

        return orderInfo;
    }

    /**
     * app支持 | 获取支付预订单信息
     * @param platform
     * @param articleId
     * @return
     */
    @RequestMapping("{platform}/app")
    public Map<String, Object> app(@PathVariable("platform") String platform, @RequestParam("articleId") Long articleId) {
        Article article = articleService.getById(articleId);
        BigDecimal price = new BigDecimal((String) article.getExtFields().get("price"));

        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        FastcmsPayOrder order = new FastcmsPayOrder(platform, WxTransactionType.APP.getType(), article.getTitle(), article.getSummary(), price, UUID.randomUUID().toString().replace("-", ""));
        data.put("orderInfo", payServiceManager.app(order));
        return data;
    }

    /**
     * 刷卡付 | pos主动扫码付款(条码付)
     * @param platform        支付平台
     * @param articleId       文章id
     * @param authCode        授权码，条码等
     * @return 支付结果
     */
    @RequestMapping(value = "{platform}/microPay")
    public Map<String, Object> microPay(@PathVariable("platform") String platform, @RequestParam("articleId") Long articleId, @RequestParam("authCode") String authCode) {

        Article article = articleService.getById(articleId);
        BigDecimal price = new BigDecimal((String) article.getExtFields().get("price"));

        //获取对应的支付账户操作工具（可根据账户id）
        //条码付
        FastcmsPayOrder order = new FastcmsPayOrder(platform, WxTransactionType.MICROPAY.getType(), article.getTitle(), article.getSummary(), null == price ? BigDecimal.valueOf(0.01) : price, UUID.randomUUID().toString().replace("-", ""));
        //设置授权码，条码等
        order.setAuthCode(authCode);
        //支付结果
        Map<String, Object> params = payServiceManager.microPay(order);
        //校验
        if (payServiceManager.verify(platform, params)) {
            //支付校验通过后的处理
            //......业务逻辑处理块........
        }
        //这里开发者自行处理
        return params;
    }

    /**
     * 刷脸付
     * @param platform      支付平台
     * @articleId           文章id
     * @param authCode      人脸凭证
     * @param openid        用户在商户 appid下的唯一标识
     * @return 支付结果
     */
    @RequestMapping(value = "{platform}/facePay")
    public Map<String, Object> facePay(@PathVariable("platform") String platform, @RequestParam("articleId") Long articleId, @RequestParam("authCode") String authCode, @RequestParam("openid") String openid)  {

        Article article = articleService.getById(articleId);
        BigDecimal price = new BigDecimal((String) article.getExtFields().get("price"));

        //获取对应的支付账户操作工具（可根据账户id）
        FastcmsPayOrder order = new FastcmsPayOrder(platform, WxTransactionType.FACEPAY.getType(), article.getTitle(), article.getSummary(), null == price ? BigDecimal.valueOf(0.01) : price, UUID.randomUUID().toString().replace("-", ""));
        //设置人脸凭证
        order.setAuthCode(authCode);
        //用户在商户 appid下的唯一标识
        order.setOpenid(openid);
        //支付结果
        Map<String, Object> params = payServiceManager.microPay(order);
        //校验
        if (payServiceManager.verify(platform, params)) {
            //支付校验通过后的处理
            //......业务逻辑处理块........
        }
        //这里开发者自行处理
        return params;
    }

}
