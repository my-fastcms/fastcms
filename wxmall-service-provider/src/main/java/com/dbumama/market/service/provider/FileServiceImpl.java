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
package com.dbumama.market.service.provider;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.message.MessageKit;
import com.dbumama.market.service.api.file.FileService;
import com.dbumama.market.service.utils.AttachmentUtils;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;

import net.coobird.thumbnailator.Thumbnails;

/**
 * @author wangjun
 * 2017年7月12日
 */
@Service("fileService")
public class FileServiceImpl implements FileService{
	
	/* (non-Javadoc)
	 * @see com.dbumama.market.service.api.file.FileService#upload(java.io.File)
	 */
	@Override
	public String upload(File file, Long sellerId, Long groupId) throws Exception{
		//文件处理器
		final String fileHandleType = PropKit.get("file_handle_type");
		
		String imgUrl = "";
		
		if(StrKit.isBlank(fileHandleType))//未配置文件处理器的情况
			imgUrl = processFile(file);
		else //默认情况
			imgUrl = processFile(file);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("seller_id", sellerId);
		jsonObj.put("group_id", groupId);
		jsonObj.put("img_url", processPath(imgUrl));
		MessageKit.sendMessage("file_upload", jsonObj);
		return imgUrl;
	}
	
	private String processFile(File file) throws Exception{
		File compressFile = null;
        if(file.length() > 200 * 1024){ //超过200Kb的图片进行压缩
        	compressFile = new File(file.getPath());
            //对文件进行压缩
            try {
				Thumbnails.of(file)
				.scale(1f)
				//.size(160, 160)
				//.rotate(90)
				//.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("watermark.png")), 0.5f)
				.outputQuality(0.5f)
				.toFile(compressFile);
			} catch (IOException e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
        }
        return compressFile == null ? AttachmentUtils.moveFile(file) : AttachmentUtils.moveFile(compressFile);  
	}
	
	/**
	 * windows下面图片路径会为反斜杠，此方法替换反斜杠为斜杆，linux下不存在此问题
	 * @param imgUrl
	 * @return
	 */
	private String processPath(String imgUrl){
		return imgUrl.replaceAll("\\\\", "/");
	}

}

