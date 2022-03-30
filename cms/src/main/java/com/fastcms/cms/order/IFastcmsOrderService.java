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
package com.fastcms.cms.order;

import com.alibaba.fastjson.JSONObject;
import com.egzosn.pay.common.bean.PayMessage;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.utils.ReflectUtils;
import com.fastcms.entity.Order;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author： wjun_java@163.com
 * @date： 2022/02/10
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface IFastcmsOrderService {

    /**
     * 创建订单
     * @param createOrderParam 文章id集合
     * @return
     */
    default Long createOrder(CreateOrderParam createOrderParam) throws FastcmsException {
        return 0l;
    }

    /**
     * 支付回调处理
     * @param payMessage
     * @throws FastcmsException
     */
    void payBackOrder(PayMessage payMessage) throws FastcmsException;

    /**
     * 订单支付完成后，对订单进行业务扩展处理
     * @param order
     */
    default void processOrder(Order order) throws FastcmsException {

    }

    /**
     * 创建订单
     */
    class CreateOrderParam implements Serializable {

        /**
         * 订单产品
         */
        ProductParam[] products;
        /**
         * 收货地址
         */
        Long addressId;
        /**
         * 买家留言
         */
        String buyerMsg;

        /**
         * json扩展参数
         */
        String jsonExt;

        public CreateOrderParam() {
            final JSONObject jsonObject = new JSONObject();
            Field[] allFields = ReflectUtils.getFields(this.getClass());
            for (Field field : allFields) {
                if (field.getAnnotation(JsonExtField.class) != null) {
                    String value = ReflectUtils.getFieldValue(this, field);
                    if (StringUtils.isNotBlank(value)) {
                        jsonObject.put(field.getName(), value);
                    }
                }
            }
            setJsonExt(jsonObject.toJSONString());
        }

        public ProductParam[] getProducts() {
            return products;
        }

        public void setProducts(ProductParam[] products) {
            this.products = products;
        }

        public Long getAddressId() {
            return addressId;
        }

        public void setAddressId(Long addressId) {
            this.addressId = addressId;
        }

        public String getBuyerMsg() {
            return buyerMsg;
        }

        public void setBuyerMsg(String buyerMsg) {
            this.buyerMsg = buyerMsg;
        }

        public String getJsonExt() {
            return jsonExt;
        }

        public void setJsonExt(String jsonExt) {
            this.jsonExt = jsonExt;
        }

    }

    /**
     * 产品
     */
    class ProductParam implements Serializable {
        /**
         * 产品id
         */
        Long id;
        /**
         * 产品类型
         */
        String type;
        /**
         * 产品数量
         */
        Long num;
        /**
         * 产品sku
         */
        String sku;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getNum() {
            return num;
        }

        public void setNum(Long num) {
            this.num = num;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }
    }

}
