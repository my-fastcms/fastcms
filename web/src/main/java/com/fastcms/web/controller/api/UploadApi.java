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

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.utils.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传
 * @author： wjun_java@163.com
 * @date： 2021/6/25
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.API_MAPPING + "/upload")
public class UploadApi {

	/**
	 * 上传
	 * @param file
	 * @return
	 */
	@PostMapping
	public Object upload(@RequestParam("file") MultipartFile file) {
		String newFilePath = FileUtils.newFileName(file.getOriginalFilename());
		File uploadFile = new File(FileUtils.getUploadDir(), newFilePath);
		try {
			if (!uploadFile.getParentFile().exists()) {
				uploadFile.getParentFile().mkdirs();
			}
			file.transferTo(uploadFile);
			long fileSize = uploadFile.length();
			if(fileSize > 1024 * 1024 * 5) {
				uploadFile.delete();
				return RestResultUtils.failed("文件超过上传限制5MB");
			}

			return RestResultUtils.success(newFilePath.replace("\\", "/"));
		} catch (IOException e) {
			e.printStackTrace();
			if(uploadFile != null) {
				uploadFile.delete();
			}
			return RestResultUtils.failed(e.getMessage());
		}
	}

}
