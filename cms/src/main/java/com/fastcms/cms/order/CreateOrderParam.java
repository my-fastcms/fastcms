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
import com.fastcms.common.utils.ReflectUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 创建订单参数
 * @author： wjun_java@163.com
 * @date： 2022/03/31
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class CreateOrderParam implements Serializable {

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
     * 订单逻辑处理类
     */
    String orderTypeClass = "com.fastcms.cms.order.DefaultFastcmsOrderService";

    /**
     * json扩展参数
     */
    String jsonExt;

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

    public String getOrderTypeClass() {
        return orderTypeClass;
    }

    public void setOrderTypeClass(String orderTypeClass) {
        this.orderTypeClass = orderTypeClass;
    }

    public String getJsonExt() {
        if (StringUtils.isBlank(jsonExt)) {
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

        return jsonExt;
    }

    public void setJsonExt(String jsonExt) {
        this.jsonExt = jsonExt;
    }

}
