package com.dbumama.market.web.admin.matter.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.service.api.matter.MatterException;
import com.dbumama.market.service.api.matter.MatterListParamDto;
import com.dbumama.market.service.api.matter.MatterResultDto;
import com.dbumama.market.service.api.matter.MatterService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.api.MediaApi;
import com.weixin.sdk.api.MediaApi.MediaType;

@RouteBind(path = "matter")
public class MatterController extends BaseController {
	@BY_NAME
	private MatterService matterService;

	public void index() {
		render("/matter/matter_index.html");
	}

	public void set() {
		String media_id = getPara("media_id");
		if (StrKit.notBlank(media_id)) {
			// List<MatterResultDto>
			// matterResultDtos=matterService.getMatter(getAccecssToken(),
			// media_id);
			// setAttr("matterResultDtos",matterResultDtos);
			setAttr("media_id", media_id);
		}
		render("/matter/matter_set.html");
	}

	public void getMattterDetailList() {
		List<MatterResultDto> matterResultDtos = matterService.getMatter(getPara("media_id"));
		rendSuccessJson(matterResultDtos);
	}

	public void list() {
		MatterListParamDto dto = new MatterListParamDto(getSellerId(), getPageNo());
		try {
			rendSuccessJson(matterService.list(dto));
		} catch (MatterException e) {
			rendFailedJson(e.getMessage());
		}
	}

	public void uploadImg() {
		if ("config".equals(getPara("action"))) {
			renderJsp("/resources/ueditor/jsp/config.json");
			return;
		}
		if ("listimage".equals(getPara("action"))) {
			JSONArray jsonArray = new JSONArray();
			setAttr("state", "SUCCESS");
			int pageNo = getParaToInt("start") / getParaToInt("size") + 1;
			ApiResult result = MediaApi.batchGetMaterial(MediaType.IMAGE, pageNo, getParaToInt("size"));
			if (result != null && result.isSucceed()) {
				JSONObject json1 = JSONObject.parseObject(result.getJson());
				JSONArray jarr = JSONArray.parseArray(json1.getString("item"));
				if (jarr == null || jarr.size() <= 0)
					throw new MatterException("没有图片返回");
				for (Iterator<?> iterator = jarr.iterator(); iterator.hasNext();) {
					JSONObject job = (JSONObject) iterator.next();
					JSONObject json = new JSONObject();
					json.put("url", job.getString("url"));
					jsonArray.add(json);
				}
			}
			setAttr("list", jsonArray);
			setAttr("total", result.getInt("total_count"));
			setAttr("start", getParaToInt("start"));
			renderJson(new String[] { "state", "list", "total", "start" });

		} else {
			String filePath = "/" + getSellerId();
			List<UploadFile> uFile = getFiles(PropKit.get("upload_file_path") + filePath);
			for (UploadFile uf : uFile) {
				ApiResult result = MediaApi.uploadImg(uf.getFile());
				String fileName = uf.getFileName();
				String[] typeArr = fileName.split("\\.");
				setAttr("url", result.getStr("url"));
				setAttr("title", fileName);
				setAttr("original", uf.getOriginalFileName());
				setAttr("type", "." + typeArr[1]);
				setAttr("size", uf.getFile().length());
				setAttr("state", "SUCCESS");
			}
			renderJson(new String[] { "original", "url", "original", "title", "state" });
		}

	}

	public void uploadFile() {
		String filePath = "/" + getSellerId();
		List<UploadFile> uFile = getFiles(PropKit.get("upload_file_path") + filePath);
		HashMap<String, String> map = new HashMap<String, String>();
		for (UploadFile uf : uFile) {
			ApiResult result = MediaApi.addMaterial(uf.getFile());
			map.put("media_id", result.getStr("media_id"));
			map.put("baseurl", getImgBase64(result.getStr("url")));
		}
		renderJson(map);
	}

	public void save() {
//		try {
//			String matterData = getPara("MaterialDetailList");
//			String mediaId = getPara("media_id");
//			String media_id = matterService.save2Weixin(matterData, mediaId);
//			rendSuccessJson(media_id);
//		} catch (MatterException e) {
//			rendFailedJson(e.getMessage());
//		}
		rendFailedJson("体验账号不可编辑素材");
	}

	public void del() {
//		try {
//			String media_id = getPara("media_id");
//			ApiResult result = MediaApi.delMaterial(media_id);
//			rendSuccessJson(result);
//		} catch (MatterException e) {
//			rendFailedJson(e.getMessage());
//		}
		rendFailedJson("体验账号不可删除素材");
	}

	private String getImgBase64(final String url) {
		String result = "";
		// 获取公众号二维码的base64图片数据
		OkHttpClient okHttpClient = new OkHttpClient();
		Request request = new Request.Builder().url(url)
				.addHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116Safari/537.36")
				.build();
		try {
			Response okResponse = okHttpClient.newCall(request).execute();
			Base64 base64 = new Base64();
			result = "data:image/jpg;base64," + base64.encodeAsString(okResponse.body().bytes());
		} catch (IOException e) {
			e.printStackTrace();
			result = null;
		}
		return result;
	}
}
