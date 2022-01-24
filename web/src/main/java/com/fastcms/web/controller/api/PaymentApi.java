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
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

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
     * @param platform
     * @param articleId
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "{platform}/toQrPay.jpg", produces = "image/jpeg;charset=UTF-8")
    public byte[] toQrPay(@PathVariable("platform") String platform, Long articleId) throws IOException {
        Article article = articleService.getById(articleId);
        BigDecimal price = (BigDecimal) article.getExtFields().get("price");
        //获取对应的支付账户操作工具（可根据账户id）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(MatrixToImageWriter.writeInfoToJpgBuff(payServiceManager.getQrPay(new FastcmsPayOrder(platform, WxTransactionType.NATIVE.getType(), "订单title", "摘要", null == price ? BigDecimal.valueOf(0.01) : price, System.currentTimeMillis()+""))), "JPEG", baos);
        return baos.toByteArray();
    }

    /**
     * 扫码支付 | 返回图片url
     * @param platform
     * @param articleId
     * @return
     */
    @RequestMapping(value = "{platform}/getQrPay.jpg")
    public String getQrPay(@PathVariable("platform") String platform, Long articleId) {
        //获取对应的支付账户操作工具（可根据账户id）
        Article article = articleService.getById(articleId);
        BigDecimal price = (BigDecimal) article.getExtFields().get("price");
        return payServiceManager.getQrPay(new FastcmsPayOrder(platform, WxTransactionType.NATIVE.getType(), "订单title", "摘要", null == price ? BigDecimal.valueOf(0.01) : price, System.currentTimeMillis()+""));
    }

}
