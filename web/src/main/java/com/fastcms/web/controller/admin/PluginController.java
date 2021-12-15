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
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.FileUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.core.utils.DirUtils;
import com.fastcms.plugin.PluginManagerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;

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

    @Autowired
    private PluginManagerService pluginService;

    /**
     * 插件列表
     * @param pluginId  插件id
     * @param provider  插件作者
     * @return
     */
    @GetMapping("list")
    public RestResult<Page<PluginManagerService.PluginVo>> list(PageModel page, String pluginId, String provider) {
        PluginManagerService.PluginResult pluginResult = pluginService.getPluginList(page.getPageNum().intValue(), page.getPageSize().intValue(), pluginId, provider);
        return RestResultUtils.success(new Page<PluginManagerService.PluginVo>(page.getPageNum(), page.getPageSize(), pluginResult.getTotal())
                .setRecords(pluginResult.getPluginVoList()));
    }

    /**
     * 上传插件
     * @param file
     * @return
     */
    @PostMapping("install")
    public Object install(@RequestParam("file") MultipartFile file) {

        String suffixName = FileUtils.getSuffix(file.getOriginalFilename());

        //检查文件格式是否合法
        if(StringUtils.isEmpty(suffixName)) {
            return RestResultUtils.failed("文件格式不合格，请上传jar或zip文件");
        }
        if(!".jar".equalsIgnoreCase(suffixName) && !".zip".equalsIgnoreCase(suffixName)) {
            return RestResultUtils.failed("文件格式不合格，请上传jar或zip文件");
        }

        File uploadFile = new File(DirUtils.getPluginDir(), file.getOriginalFilename());
        try {
            file.transferTo(uploadFile);
            pluginService.installPlugin(Paths.get(uploadFile.getPath()));
            return RestResultUtils.success();
        } catch (Exception e) {
            e.printStackTrace();
            if(uploadFile != null) {
                uploadFile.delete();
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
    public Object unInstall(@PathVariable(name = "pluginId") String pluginId) {

        try {
            pluginService.unInstallPlugin(pluginId);
            return RestResultUtils.success();
        } catch (Exception e) {
            e.printStackTrace();
            return RestResultUtils.failed(e.getMessage());
        }

    }

}
