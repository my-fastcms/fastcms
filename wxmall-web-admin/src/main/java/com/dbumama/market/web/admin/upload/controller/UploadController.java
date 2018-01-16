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
package com.dbumama.market.web.admin.upload.controller;

import java.util.List;

import com.dbumama.market.service.api.file.FileService;
import com.dbumama.market.service.api.product.ImageGroupResultDto;
import com.dbumama.market.service.api.product.ImageGroupService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.upload.UploadFile;

/**
 * @author wangjun
 * 2017年7月11日
 */
@RouteBind(path="upload")
public class UploadController extends BaseController{

	@BY_NAME
	private FileService fileService;
	@BY_NAME
	private ImageGroupService imageGroupService;
	
	/**
    * 选择图片界面
    */
    public void addImage(){
    	List<ImageGroupResultDto> resultDto=imageGroupService.getGroup(getSellerId());
        setAttr("imageGroups", resultDto);
    	setAttr("model", getPara("model"));
    	render("/upload/list_image.html");
    }
    
    public void addUploadImage(){
    	setAttr("model", getPara("model"));
    	setAttr("groupId", getPara("groupId"));
    	render("/upload/upload_image.html");
    }

	public void index() {
		List<UploadFile> uFile = getFiles();
		if (uFile == null) {
			rendFailedJson("没有图片");
			return;
		}
		for (UploadFile uploadFile : uFile) {
			try {
				fileService.upload(uploadFile.getFile(), getSellerId(), getParaToLong("groupId"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		rendSuccessJson();
	}

	/**
	 * 浏览
	 *//*
	public void browser() {
		rendSuccessJson(imageGroupService.pageImages(getSellerId(), null, getPageNo(), getPageSize()));
	}*/
	
}

