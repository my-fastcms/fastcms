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
package com.fastcms.web.controller.admin;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.permission.AdminMenu;
import com.fastcms.core.response.Response;
import com.fastcms.core.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/plugin")
@AdminMenu(name = "插件", icon = "<i class=\"nav-icon fas fa-plug\"></i>", sort = 3)
public class PluginController {

//    @Autowired
//    private PluginManagerService pluginService;

    @AdminMenu(name = "插件管理", sort = 1)
    @RequestMapping("list")
    public String index(Model model) {
//        model.addAttribute("plugins", pluginService.getInstallPlugins());
        return "admin/plugin/list";
    }

    @AdminMenu(name = "安装", sort = 2)
    @RequestMapping("install")
    public String install() {
        return "admin/plugin/install";
    }

    @PostMapping("doInstall")
    public ResponseEntity doInstall(@RequestParam("file") MultipartFile file) {

        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //检查文件格式是否合法
        if(StringUtils.isEmpty(suffixName)) {
            return Response.fail("文件格式不合格，请上传jar或zip文件");
        }
        if(!"jar".equalsIgnoreCase(suffixName) && !"zip".equalsIgnoreCase(suffixName)) {
            return Response.fail("文件格式不合格，请上传jar或zip文件");
        }

        File uploadFile = new File(FileUtils.getPluginDir(), file.getOriginalFilename());
        try {
            file.transferTo(uploadFile);
//            pluginService.installPlugin(Paths.get(uploadFile.getPath()));
            return Response.success();
        } catch (Exception e) {
            log.error(e.getMessage());
            if(uploadFile != null) {
                uploadFile.delete();
            }
            return Response.fail(e.getMessage());
        }
    }

    @PostMapping("doUnInstall")
    public ResponseEntity doUnInstall(@RequestParam(name = "pluginId") String pluginId) {

        try {
//            pluginService.unInstallPlugin(pluginId);
            return Response.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail(e.getMessage());
        }

    }

}
