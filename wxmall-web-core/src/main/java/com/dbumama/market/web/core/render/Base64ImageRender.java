package com.dbumama.market.web.core.render;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.codec.binary.Base64;

import com.jfinal.render.Render;
import com.jfinal.render.RenderException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * 返回base64格式的图片
 * @author wangjun
 *
 */
public class Base64ImageRender extends Render {
	
    private static final String CONTENT_TYPE = "text/html;charset=" + getEncoding();
    OkHttpClient okHttpClient = new OkHttpClient();
    //图片地址
    private String url;

	public Base64ImageRender(String url) {
    	this.url = url;
    }

    @Override
    public void render() {
    	Request request = new Request.Builder().
                url(getUrl())
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116Safari/537.36")
                .build();
    	PrintWriter writer = null;
        try {
        	Response okResponse = okHttpClient.newCall(request).execute();
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType(CONTENT_TYPE);
            writer = response.getWriter();
            Base64 base64 = new Base64();
            final String result = "data:image/jpg;base64," + base64.encodeAsString(okResponse.body().bytes());
            writer.write(result);
            writer.flush();
        } catch (IOException e) {
            throw new RenderException(e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
