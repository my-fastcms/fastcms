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
package com.fastcms.payment;

import com.egzosn.pay.common.api.PayMessageInterceptor;
import com.egzosn.pay.common.api.PayService;
import com.egzosn.pay.common.bean.*;
import com.fastcms.payment.bean.FastcmsPayOrder;
import com.fastcms.payment.bean.FastcmsQueryOrder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * 商户支付服务
 * @author： wjun_java@163.com
 * @date： 2021/6/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface PayServiceManager {

    /**
     * 回调校验
     *
     * @param platform 商户支付平台类型
     * @param params    回调回来的参数集
     * @return 签名校验 true通过
     */

    boolean verify(String platform, Map<String, Object> params);

    /**
     * 将请求参数或者请求流转化为 Map
     *
     * @param platform    商户支付平台类型
     * @param parameterMap 请求参数
     * @param is           请求流
     * @return 获得回调的请求参数
     */
    Map<String, Object> getParameter2Map(String platform, Map<String, String[]> parameterMap, InputStream is);

    /**
     * 将请求参数或者请求流转化为 Map
     *
     * @param platform  商户支付平台类型
     * @param request   通知请求
     * @return 获得回调的请求参数
     */
    NoticeParams getNoticeParams(String platform, NoticeRequest request);

    /**
     * 跳到支付页面
     * 针对实时支付,即时付款
     *
     * @param payOrder 商户支付订单信息
     * @return 跳到支付页面
     */
    String toPay(FastcmsPayOrder payOrder);

    /**
     * 获取支付预订单信息
     *
     * @param payOrder 商户支付订单信息
     * @return 支付预订单信息
     */
    Map<String, Object> app(FastcmsPayOrder payOrder);

    /**
     * 获取支付预订单信息
     *
     * @param payOrder 商户支付订单信息
     * @return 支付预订单信息
     */
    Map<String, Object> getOrderInfo(FastcmsPayOrder payOrder);

    /**
     * 刷卡付,pos主动扫码付款(条码付)
     * 刷脸付
     *
     * @param payOrder 商户支付订单信息
     * @return 支付结果
     */
    Map<String, Object> microPay(FastcmsPayOrder payOrder);

    /**
     * 获取二维码图像
     * 二维码支付
     *
     * @param payOrder 商户支付订单信息
     * @return 二维码图像
     * @throws IOException IOException
     */
    byte[] toQrPay(FastcmsPayOrder payOrder) throws IOException;

    /**
     * 获取二维码信息
     * 二维码支付
     *
     * @param payOrder 商户支付订单信息
     * @return 二维码信息
     */
    String getQrPay(FastcmsPayOrder payOrder);

    /**
     * 支付回调地址
     * 方式二
     *
     * @param platform    商户支付平台类型
     * @param parameterMap 请求参数
     * @param is           请求流
     * @return 支付是否成功
     * @throws IOException IOException
     *                     拦截器相关增加， 详情查看{@link PayService#addPayMessageInterceptor(PayMessageInterceptor)}
     *                     <p>
     *                     业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看{@link PayService#setPayMessageHandler(com.egzosn.pay.common.api.PayMessageHandler)}
     *                     </p>
     *                     如果未设置 {@link com.egzosn.pay.common.api.PayMessageHandler} 那么会使用默认的 {@link com.egzosn.pay.common.api.DefaultPayMessageHandler}
     */
    String payBack(String platform, Map<String, String[]> parameterMap, InputStream is) throws IOException;

    /**
     * 支付回调地址
     * 方式二
     *
     * @param platform  商户支付平台类型
     * @param request   请求参数
     * @return 支付是否成功
     *                     拦截器相关增加， 详情查看{@link com.egzosn.pay.common.api.PayService#addPayMessageInterceptor(PayMessageInterceptor)}
     *                     <p>
     *                     业务处理在对应的PayMessageHandler里面处理，在哪里设置PayMessageHandler，详情查看{@link com.egzosn.pay.common.api.PayService#setPayMessageHandler(com.egzosn.pay.common.api.PayMessageHandler)}
     *                     </p>
     *                     如果未设置 {@link com.egzosn.pay.common.api.PayMessageHandler} 那么会使用默认的 {@link com.egzosn.pay.common.api.DefaultPayMessageHandler}
     */
    String payBack(String platform, NoticeRequest request);

    /**
     * 查询
     *
     * @param order 订单的请求体
     * @return 返回查询回来的结果集，支付方原值返回
     */
    Map<String, Object> query(FastcmsPayOrder order);

    /**
     * 交易关闭接口
     *
     * @param order 订单的请求体
     * @return 返回支付方交易关闭后的结果
     */
    Map<String, Object> close(FastcmsPayOrder order);


    /**
     * 申请退款接口
     *
     * @param platform 支付平台类型
     * @param order     订单的请求体
     * @return 返回支付方申请退款后的结果
     */

    RefundResult refund(String platform, RefundOrder order);

    /**
     * 查询退款
     *
     * @param platform 支付平台类型
     * @param order     订单的请求体
     * @return 返回支付方查询退款后的结果
     */
    Map<String, Object> refundQuery(String platform, RefundOrder order);

    /**
     * 下载对账单
     *
     * @param order 订单的请求体
     * @return 返回支付方下载对账单的结果
     */
    Map<String, Object> downloadBill(FastcmsQueryOrder order);


    /**
     * 转账
     *
     * @param platform 支付平台类型
     * @param order     转账订单
     * @return 对应的转账结果
     */
    Map<String, Object> transfer(String platform, TransferOrder order);

    /**
     * 转账查询
     *
     * @param platform 支付平台类型
     * @param outNo     商户转账订单号
     * @param tradeNo   支付平台转账订单号
     * @return 对应的转账订单
     */
    Map<String, Object> transferQuery(String platform, String outNo, String tradeNo);

    /**
     * 创建消息
     *
     * @param platform 支付平台类型
     * @param message   支付平台返回的消息
     * @return 支付消息对象
     */
    PayMessage createMessage(String platform, Map<String, Object> message);


    /**
     * 获取payService具体调用类引用
     *
     * @param platform       支付平台类型
     * @param payServiceClass payService类
     * @param <T>             支付服务类引用
     * @return 具体调用类引用
     */
    <T extends PayService> T cast(String platform, Class<T> payServiceClass);

}
