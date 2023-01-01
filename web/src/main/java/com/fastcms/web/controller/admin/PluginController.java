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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.common.auth.ActionTypes;
import com.fastcms.common.auth.Secured;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.DirUtils;
import com.fastcms.common.utils.FileUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.plugin.PluginBase;
import com.fastcms.plugin.PluginManagerService;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.I18nUtils;
import org.apache.commons.lang.StringUtils;
import org.pf4j.Plugin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.file.Paths;

import static com.fastcms.plugin.PluginManagerService.PluginI18n.*;
import static com.fastcms.service.IResourceService.ResourceI18n.*;

/**
 * 插件管理
 * @author： wjun_java@163.com
 * @date： 2021/4/22
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/plugin")
public class PluginController {

    @Resource
    private PluginManagerService pluginManagerService;

    /**
     * 插件列表
     * @param pluginId  插件id
     * @param provider  插件作者
     * @return
     */
    @GetMapping("list")
    @Secured(name = RESOURCE_NAME_PLUGIN_LIST, resource = "plugin:list", action = ActionTypes.READ)
    public RestResult<Page<PluginManagerService.PluginVo>> list(PageModel page, String pluginId, String provider) {
        PluginManagerService.PluginResult pluginResult = pluginManagerService.getPluginList(page.getPageNum().intValue(), page.getPageSize().intValue(), pluginId, provider);
        return RestResultUtils.success(new Page<PluginManagerService.PluginVo>(page.getPageNum(), page.getPageSize(), pluginResult.getTotal())
                .setRecords(pluginResult.getPluginVoList()));
    }

    /**
     * 上传插件
     * @param file
     * @return
     */
    @PostMapping("install")
    @Secured(name = RESOURCE_NAME_PLUGIN_INSTALL, resource = "plugin:install", action = ActionTypes.WRITE)
    public Object install(@RequestParam("file") MultipartFile file) throws Exception {

        if (ApplicationUtils.isDevelopment()) {
            return RestResultUtils.failed(I18nUtils.getMessage(PLUGIN_DEV_NOT_ALLOW_INSTALL));
        }

        String suffixName = FileUtils.getSuffix(file.getOriginalFilename());

        //检查文件格式是否合法
        if(StringUtils.isEmpty(suffixName)) {
            return RestResultUtils.failed(I18nUtils.getMessage(PLUGIN_UPLOAD_FILE_TYPE_NOT_ALLOW));
        }
        if(!".jar".equalsIgnoreCase(suffixName) && !".zip".equalsIgnoreCase(suffixName)) {
            return RestResultUtils.failed(I18nUtils.getMessage(PLUGIN_UPLOAD_FILE_TYPE_NOT_ALLOW));
        }

        File uploadFile = new File(DirUtils.getPluginDir(), file.getOriginalFilename());
        try {
            file.transferTo(uploadFile);
            pluginManagerService.installPlugin(Paths.get(uploadFile.getPath()));
            return RestResultUtils.success();
        } catch (Exception e) {
            if(e instanceof FastcmsException == false) {
                e.printStackTrace();
            }
            if(uploadFile != null) {
                uploadFile.delete();
                //如果是zip包，删除解压后的文件目录
                org.apache.commons.io.FileUtils.deleteDirectory(Paths.get(DirUtils.getPluginDir() + file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(StrUtils.DOT))).toFile());
            }
            return RestResultUtils.failed(e.getMessage());
        }
    }

    /**
     * 卸载插件
     * @param pluginId  插件id
     * @return
     */
    @PostMapping("unInstall/{pluginId}")
    @Secured(name = RESOURCE_NAME_PLUGIN_UNINSTALL, resource = "plugin:unInstall", action = ActionTypes.WRITE)
    public Object unInstall(@PathVariable(name = "pluginId") String pluginId) throws Exception {

        if (ApplicationUtils.isDevelopment()) {
            return RestResultUtils.failed(I18nUtils.getMessage(PLUGIN_DEV_NOT_ALLOW_UNINSTALL));
        }

        pluginManagerService.unInstallPlugin(pluginId);
        return RestResultUtils.success();

    }

    /**
     * 插件配置界面
     * @param pluginId
     * @return
     */
    @GetMapping("config/url/{pluginId}")
    @Secured(name = RESOURCE_NAME_PLUGIN_CONFIG, resource = "plugin:config/url", action = ActionTypes.WRITE)
    public RestResult<String> getPluginConfigUrl(@PathVariable("pluginId") String pluginId) {
        Plugin plugin = pluginManagerService.getPluginManager().getPlugin(pluginId).getPlugin();
        if(plugin instanceof PluginBase) {
            PluginBase pluginBase = (PluginBase) plugin;
            return RestResultUtils.success(pluginBase.getConfigUrl());
        }
        return RestResultUtils.failed();
    }

}
