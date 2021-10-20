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
 * @date： 2021/4/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class MallPluginCodeGen extends PluginCodeGen {

	@Override
	String getPluginPath() {
		return "fastcms-mall-plugin";
	}

	@Override
	String[] getPluginTableNames() {
		return new String[] {
				"product", "product_category", "product_comment", "product_image",
				"product_sku_item", "product_spec", "product_spec_value",
				"postage_template", "postage_template_set",
				"cart", "order", "order_item", "order_delivery", "order_invoice",
				"delivery_address", "express_comp"};
	}

	@Override
	String getMapperXmlOutputDir() {
		return System.getProperty("user.dir") + PLUGIN_DIR + getPluginPath();
	}

	@Override
	protected String getModelName() {
		return "mall";
	}

	public static void main(String[] args) throws Exception {
		new MallPluginCodeGen().genCode();
	}

}
