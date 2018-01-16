package com.dbumama.market.service.api.matter;

import com.dbumama.market.service.api.common.AbstractResultDto;
/**
 * 图文信息dto
 * @author drs
 *
 */
@SuppressWarnings("serial")
public class MatterResultDto extends AbstractResultDto{
	private String media_id;
	// 标题
	private String title;
	// 图文消息的封面图片素材id（必须是永久mediaID）
	private String thumb_media_id;
	// 作者
	private String author;
	// 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空
	private String digest;
	// 是否显示封面，0为false，即不显示，1为true，即显示
	private boolean show_cover_pic;
	// 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS
	private String content;
	// 图文消息的原文地址，即点击“阅读原文”后的URL
	private String content_source_url;

	private String imgUrl;
    
	private String updateTime;
	
	private String url;  //图文页的URL 
	
	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumb_media_id() {
		return thumb_media_id;
	}

	public void setThumb_media_id(String thumb_media_id) {
		this.thumb_media_id = thumb_media_id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public boolean isShow_cover_pic() {
		return show_cover_pic;
	}

	public void setShow_cover_pic(boolean show_cover_pic) {
		this.show_cover_pic = show_cover_pic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent_source_url() {
		return content_source_url;
	}

	public void setContent_source_url(String content_source_url) {
		this.content_source_url = content_source_url;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

}
