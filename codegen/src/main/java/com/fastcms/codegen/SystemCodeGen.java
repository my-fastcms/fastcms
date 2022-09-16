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
package com.fastcms.codegen;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/10
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class SystemCodeGen extends AbstractCodeGen {

    @Override
    String getOutputDir() {
        return "/service";
    }

    @Override
    protected String getModelName() {
        return null;
    }

    @Override
    String[] getTableNames() {
        return new String[] { "user", "role", "permission", "resource", "department", "config", "attachment", "user_tag", "user_openid", "order", "order_item", "order_invoice", "payment_record", "user_amount", "user_amount_payout", "user_amount_statement" };
    }

    public static void main(String[] args) throws Exception {
        SystemCodeGen systemServiceGen = new SystemCodeGen();
        systemServiceGen.gen();
    }

}
