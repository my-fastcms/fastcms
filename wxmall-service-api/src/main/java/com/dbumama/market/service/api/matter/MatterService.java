package com.dbumama.market.service.api.matter;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;

public interface MatterService {
	
	public List<MatterResultDto> getMatter(String media_id) throws MatterException;
	
	public Page<MatterResultDto> list(MatterListParamDto matterListParamDto) throws MatterException;

	/**
	 * 保存图文到微信
	 * @param MatterData
	 * @param mediaId
	 * @return
	 * @throws MatterException
	 */
    public String save2Weixin(String MatterData,String mediaId) throws MatterException;
}
