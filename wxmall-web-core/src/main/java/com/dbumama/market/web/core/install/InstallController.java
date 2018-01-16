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
package com.dbumama.market.web.core.install;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.dbumama.market.model._MappingKit;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;


@RouteBind(path = "/install")
@Before(InstallInterceptor.class)
public class InstallController extends Controller {
	
	private static final Log log = Log.getLog(InstallController.class);

	public void index() {
		render("step1.html");
	}

	public void step2() {
		String db_host = getPara("db_host");
		String db_host_port = getPara("db_host_port");
		db_host_port = StringUtils.isNotBlank(db_host_port) ? db_host_port.trim() : "3306";
		String db_name = getPara("db_name");
		String db_user = getPara("db_user");
		String db_password = getPara("db_password");

		if (!StrKit.notBlank(db_host,db_host_port, db_name, db_user)) {
			setAttr("error", "请填写完成的数据库连接信息");
			render("step2.html");
			return;
		}

		InstallUtils.init(db_host,db_host_port,db_name, db_user, db_password);

		try {
			List<String> tableList = InstallUtils.getTableList();
			if (null != tableList && tableList.size() > 0) {//说明安装过
				redirect("/install/step3");
				return;
			}
		} catch (Exception e) { // db config error
			e.printStackTrace();
			redirect("/install/step2_error");
			return;
		}

		try {

			InstallUtils.createWxmallDatabase();

			List<String> tableList = InstallUtils.getTableList();
			if (tableList !=null && tableList.size() > 0) {
				redirect("/install/step3");
				return;
			}

		} catch (Exception e) {
			log.error("InstallController step2 is erro", e);
		}

		redirect("/install/step2_error");
	}

	public void step2_error() {
		render("step2_error.html");
	}

	public void step3() throws SQLException {
		InstallUtils.createDbProperties();
		InstallUtils.createWxmallProperties();
		
		//reload db plugin
		ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(InstallUtils.createDruidPlugin());
		activeRecordPlugin.setShowSql(JFinal.me().getConstants().getDevMode());
		activeRecordPlugin.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		_MappingKit.mapping(activeRecordPlugin);
		activeRecordPlugin.start();
		
		render("step3.html");
	}
	
	public void finish(){
		render("finished.html");
	}

}
